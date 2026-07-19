<template src="./ModalChonDiaChi.html"></template>
<script>

  export default {
    name: 'ModalChonDiaChi',
    props: ['modalId', 'data', 'isStrictMode', 'clearAfterClose', 'showThongTinThem', 'showThongTinLoaiNha'],
    created: async function() {
      this.dsTinh = []
      this.diachi.tinh_id = null
    },
    computed: {},
    watch: {},
    data: function() {
      return {
        diachi: {
          diachi_id: null,
          tinh_id: null,
          quan_id: null,
          phuong_id: null,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dac_diem_id: 0,
          so_nha: '',
          daydiachi: '',
          diachimoi: '',
          block: '',
          tang: '',
          phong: '',
          mota: '',
          lat: 0,
          lng: 0,
          nhomln_id: 0,
          loainha_id: 0,
          sotang: 0,
          sophong: 0,
          dientich: 0,
          tmp_tinh: null
        },
        dsTinh: [],
        dsQuan: [],
        dsPhuong: [],
        dsPho: [],
        dsAp: [],
        dsKhu: [],
        dsDacDiem: [],
        dsNhomLN: [],
        dsLoaiNha: [],
        tenloainha: 0,
        isPho: false,
        isAp: false,
        isKhu: false,
        isDacDiem: false,
        tentinh: '',
        tenquan: '',
        tenphuong: '',
        tenpho: '',
        tenap: '',
        tenkhu: '',
        isEnableThongTinThem: false,
        nvkd_batbuoc_nhap_loainha: false
      }
    },
    methods: {
      onModalHidden() {
        this.diachi.tinh_id = null
      },
      UpperCasePropertyList(obj) {
        return obj.map(function(item) {
          for (var key in item) {
            var upper = key.toUpperCase()
            // check if it already wasn't uppercase
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
      },
      LowerCasePropertyList(obj) {
        return obj.map(function(item) {
          for (var key in item) {
            var upper = key.toLowerCase()
            // check if it already wasn't uppercase
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
      },
      async IS_CHON_DIACHI_CHITIET() {

        let isExistMaTS = async (axios, params) => axios.get('/web-cabman/danhmuc/isExistMaTS', { params: params })
        let data = this.GetData(await isExistMaTS(this.axios, { ma_ts: 'IS_CHON_DIACHI_CHITIET' }))
        if (data) {
          return data.result
        } else {
          return false
        }
      },
      GetData: function(response) {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
          return null
        } else {
          this.$root.$toast.error('Lấy dữ liệu không thành công')
        }
        return null
      },
      focusMyElement: async function() {
        await this.getTinh()
        /*if (await this.IS_CHON_DIACHI_CHITIET()) {
          this.isEnableThongTinThem = true
        }
        if (this.data) {
          var tsdiachi = Object.assign({}, this.data)
          tsdiachi = this.LowerCasePropertyList([tsdiachi])[0]
          this.diachi.daydiachi = tsdiachi.diachi
          if (tsdiachi.tinh_id && tsdiachi.tinh_id != 0) {
            this.diachi.tinh_id = tsdiachi.tinh_id
            if (this.diachi.tinh_id == 98) this.$toast.warning('Tỉnh quốc tế chỉ được chọn để lưu địa chỉ quốc tế  hoặc địa chỉ không xác định trên biển')
            this.tentinh = this.dsTinh.filter(
              (x) => x.TINH_ID == this.diachi.tinh_id
            )[0].TENTINH

            this.diachi.quan_id = tsdiachi.quan_id
            await this.getQuan()

            this.diachi.phuong_id = tsdiachi.phuong_id
            await this.getPhuong()
            if (tsdiachi.phuong_id) {
              if (tsdiachi.pho_id && tsdiachi.pho_id > 0) {
                this.diachi.pho_id = tsdiachi.pho_id
                this.isPho = true
              } else {
                this.diachi.pho_id = 0
                this.isPho = false
              }

              if (tsdiachi.ap_id && tsdiachi.ap_id > 0) {
                this.diachi.ap_id = tsdiachi.ap_id
                this.isAp = true
              } else {
                this.diachi.ap_id = 0
                this.isAp = false
              }

              if (tsdiachi.khu_id && tsdiachi.khu_id > 0) {
                this.diachi.khu_id = tsdiachi.khu_id
                this.isKhu = true
              } else {
                this.diachi.khu_id = 0
                this.isKhu = false
              }
              await this.getPhoApKhu()
              await this.getDacDiem(false)
            }
            if (tsdiachi.dacdiem_id && tsdiachi.dacdiem_id > 0) {
              this.diachi.dacdiem_id = tsdiachi.dacdiem_id
              this.isDacDiem = true
            } else {
              this.diachi.dacdiem_id = 0
              this.isDacDiem = false
            }
            this.diachi.so_nha = tsdiachi.so_nha
            this.diachi.daydiachi = tsdiachi.diachi
            this.diachi.diachimoi = tsdiachi.diachi

            this.diachi.block = this.data.block ? this.data.block : ''
            this.diachi.tang = this.data.tang ? this.data.tang : ''
            this.diachi.phong = this.data.phong ? this.data.phong : ''
            this.diachi.mota = this.data.mota ? this.data.mota : ''

            await this.getNhomLoaiNha()
            this.diachi.sotang = tsdiachi.sotang ? tsdiachi.sotang * 1 : 0
            this.diachi.sophong = tsdiachi.sophong ? tsdiachi.sophong * 1 : 0
            this.diachi.dientich = tsdiachi.dientich ? tsdiachi.dientich * 1 : 0
            this.diachi.loainha_id = tsdiachi.loainha_id ? tsdiachi.loainha_id * 1 : 0
            if (tsdiachi.nhomln_id) this.diachi.nhomln_id = tsdiachi.nhomln_id * 1
            this.getLoaiNha()
          } else {
            if (this.dsTinh && this.dsTinh.length > 0) {
              this.diachi.tinh_id = this.diachi.tinh_id ? this.diachi.tinh_id : (this.$root.token.getPhanVungID() ? this.$root.token.getPhanVungID() : this.dsTinh[0].TINH_ID)
            }
            if (this.diachi.tinh_id == 98) this.$toast.warning('Tỉnh quốc tế chỉ được chọn để lưu địa chỉ quốc tế  hoặc địa chỉ không xác định trên biển')
            this.tentinh = this.dsTinh.filter(
              (x) => x.TINH_ID == this.diachi.tinh_id
            )[0].TENTINH
            await this.getQuan()
            await this.getNhomLoaiNha()
            this.diachi.sotang = 0
            this.diachi.sophong = 0
            this.diachi.dientich = 0
            this.diachi.loainha_id = 0
            this.getLoaiNha()
          }
        } else {
          if (this.dsTinh && this.dsTinh.length > 0) {
            this.diachi.tinh_id = this.$root.token.getPhanVungID() ? this.$root.token.getPhanVungID() : this.dsTinh[0].TINH_ID
          }
          this.tentinh = this.dsTinh.filter(
            (x) => x.TINH_ID == this.diachi.tinh_id
          )[0].TENTINH
          await this.getQuan()
          await this.getNhomLoaiNha()
          this.diachi.sotang = 0
          this.diachi.sophong = 0
          this.diachi.dientich = 0
          this.diachi.loainha_id = 0
          this.getLoaiNha()
        }*/
      },

      getTinh: async function() {
        try {
          this.$root.showLoading(true)
          var rs = await this.$root.context.get(
            '/web-quantri/danhmuc-chung/CSS_TINH'
            //"/web-cabman/bando_tuyencot/lay_thongtin_tinh"
          )

          this.dsTinh = rs.data.map((item) => {
            return {
              id: item.TINH_ID +'-'+ item.TENTAT,
              text: item.TENTINH
            }
          });

        } catch (error) {
          console.log(error)
        } finally {
          this.$root.showLoading(false)
        }
      },
      getQuan: async function() {
        try {
          this.$root.showLoading(true)
          var rs = await this.$root.context.get(
            '/web-cabman/bando_tuyencot/lay_ds_quan_huyen/' + this.diachi.tinh_id
          )
          this.dsQuan = rs.data
          if (this.diachi.quan_id && this.dsQuan) {
            this.tenquan = this.dsQuan.filter(
              (x) => x.QUAN_ID == this.diachi.quan_id
            )[0].TEN_QUAN
          } else if ((!this.diachi.quan_id || this.diachi.quan_id <= 0) && this.dsQuan) {
            this.diachi.quan_id = this.dsQuan[0].QUAN_ID
            this.tenquan = this.dsQuan[0].TEN_QUAN
          }
          this.onChangeQuan()
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      },
      getPhuong: async function() {
        try {
          this.$root.showLoading(true)
          var rs = await this.$root.context.get(
            '/web-cabman/bando_tuyencot/lay_ds_phuong_xa/' + this.diachi.quan_id
          )
          this.dsPhuong = rs.data
          if (this.diachi.phuong_id && this.dsPhuong) {
            var listphuong = this.dsPhuong.filter(
              x => x.PHUONG_ID == this.diachi.phuong_id)
            if (listphuong && listphuong.length > 0) {
              this.tenphuong = listphuong[0].TEN_PHUONG
            } else if (this.dsPhuong && this.dsPhuong.length > 0) {
              this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID
              this.tenphuong = this.dsPhuong[0].TEN_PHUONG
            }
          } else if ((!this.diachi.phuong_id || this.diachi.phuong_id <= 0) && this.dsPhuong) {
            this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID
            this.tenphuong = this.dsPhuong[0].TEN_PHUONG
          }
          this.onChangePhuong()
        } catch (error) {
          console.log(error)

        } finally {
          this.$root.showLoading(false)
        }
      },
      getPhoApKhu: async function() {
        try {
          this.$root.showLoading(true)
          var rs = await this.$root.context.get(
            '/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/' +
            this.diachi.phuong_id
          )
          if (rs.data != null) {
            this.dsPho = rs.data.dsPho
            if (this.diachi.pho_id && this.diachi.pho_id > 0 && this.dsPho) {
              this.tenpho = this.dsPho.find(
                (x) => x.PHO_ID == this.diachi.pho_id
              ) ? this.dsPho.find(
                (x) => x.PHO_ID == this.diachi.pho_id
              ).TEN_PHO : ''
            }
            this.dsAp = rs.data.dsAp
            if (this.diachi.ap_id && this.diachi.ap_id > 0 && this.dsAp) {
              this.tenap = this.dsAp.find(
                (x) => x.PHO_ID == this.diachi.ap_id
              ) ? this.dsAp.find(
                (x) => x.PHO_ID == this.diachi.ap_id
              ).TEN_PHO : ''
            }
            this.dsKhu = rs.data.dsKhu
            if (this.diachi.khu_id && this.diachi.khu_id > 0 && this.dsKhu) {
              this.tenkhu = this.dsKhu.find(
                (x) => x.PHO_ID == this.diachi.khu_id
              ) ? this.dsKhu.find(
                (x) => x.PHO_ID == this.diachi.khu_id
              ).TEN_PHO : ''
            }
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      },
      getDacDiem: async function(auto = true) {
        try {
          this.$root.showLoading(true)
          var rs = await this.$root.context.get(
            '/web-cabman/bando_tuyencot/lay_ds_dacdiem',
            {
              phuong_id: this.diachi.phuong_id == null ? 0 : this.diachi.phuong_id,
              pho_id: this.diachi.pho_id == null ? 0 : this.diachi.pho_id,
              ap_id: this.diachi.ap_id == null ? 0 : this.diachi.ap_id,
              khu_id: this.diachi.khu_id == null ? 0 : this.diachi.khu_id
            }
          )
          this.dsDacDiem = rs.data
          if (this.dsDacDiem && this.dsDacDiem.length > 0 && auto) {
            this.isDacDiem = true
            if (!this.diachi.dacdiem_id || this.diachi.dacdiem_id <= 0) this.diachi.dacdiem_id = this.dsDacDiem[0].DACDIEM_ID
          } else {
            this.isDacDiem = false
            this.diachi.dacdiem_id = 0
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      },
      getNhomLoaiNha: async function() {
        try {
          this.$root.showLoading(true)
          var rs = await this.$root.context.get(
            '/web-hopdong/lapdatmoi/sp_nhom_loainha_lay_ds',
            {}
          )
          this.dsNhomLN = rs.data
          if (this.dsNhomLN && this.dsNhomLN.length > 0) {
            this.dsNhomLN = [{ ma_nhomln: 'NULL', nhomln_id: 0, nhom_ln: '-' }, ...this.dsNhomLN]
            if (!this.diachi.nhomln_id || this.diachi.nhomln_id <= 0) this.diachi.nhomln_id = this.dsNhomLN[0].nhomln_id
          } else {
            this.diachi.nhomln_id = 0
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }

      },
      NhomLoaiNha_Changed: async function() {
        this.diachi.dientich = 0
        this.diachi.sotang = 0
        this.diachi.sophong = 0
        this.getLoaiNha()
      },
      getLoaiNha: async function() {
        try {
          if (!this.diachi.nhomln_id || this.diachi.nhomln_id <= 0 || !this.diachi.dientich || this.diachi.dientich <= 0 || !this.diachi.sotang || this.diachi.sotang <= 0 || !this.diachi.sophong || this.diachi.sophong <= 0) {
            this.dsLoaiNha = []
            this.diachi.loainha_id = 0
            return
          }
          this.$root.showLoading(true)
          var rs = await this.$root.context.post(
            '/web-hopdong/lapdatmoi/sp_lay_loainha_theo_dieukien',
            {
              p_nhomln_id: this.diachi.nhomln_id,
              p_dientich: this.diachi.dientich,
              p_sotang: this.diachi.sotang,
              p_sophong: this.diachi.sophong
            }
          )
          this.dsLoaiNha = rs.data
          if (this.dsLoaiNha && this.dsLoaiNha.length > 0) {
            if (!this.diachi.loainha_id || this.diachi.loainha_id <= 0 || !this.dsLoaiNha.find(x => x.loainha_id == this.diachi.loainha_id)) this.diachi.loainha_id = this.dsLoaiNha[0].loainha_id
          } else {
            this.diachi.loainha_id = 0
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }

      },
      saveDiachi: async function() {
        try {
          this.$root.showLoading(true)
        } catch (error) {
          this.$toast.error('Lưu ảnh thất bại')
        } finally {
          this.$root.showLoading(false)
        }
      },
      onChangeTinh: async function() {
        if (this.diachi.tinh_id == 98) this.$toast.warning('Tỉnh quốc tế chỉ được chọn để lưu địa chỉ quốc tế  hoặc địa chỉ không xác định trên biển')
        await this.getQuan()
        this.tentinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )[0].TENTINH
      },
      onChangeQuan: async function() {
        await this.getPhuong()
        this.tenquan = this.dsQuan.filter(
          (x) => x.QUAN_ID == this.diachi.quan_id
        )[0].TEN_QUAN
      },
      onChangePhuong: async function() {
        await this.getPhoApKhu()
        var listphuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id)
        if (listphuong && listphuong.length > 0) {
          this.tenphuong = listphuong[0].TEN_PHUONG
        }
      },
      onChangePho: async function() {
        await this.getDacDiem()
        this.tenpho = this.dsPho.filter(
          (x) => x.PHO_ID == this.diachi.pho_id
        )[0].TEN_PHO
      },
      onChangeKhu: async function() {
        await this.getDacDiem()
        this.tenkhu = this.dsKhu.filter(
          (x) => x.PHO_ID == this.diachi.khu_id
        )[0].TEN_PHO
      },
      onChangeAp: async function() {
        await this.getDacDiem()
        this.tenap = this.dsAp.filter(
          (x) => x.PHO_ID == this.diachi.ap_id
        )[0].TEN_PHO
      },
      chapnhan: async function() {
        this.$emit('xacnhan', this.diachi)
        this.$bvModal.hide(this.modalId)
      },
      XacNhanToaDo(position) {
        this.diachi.lat = position.lat
        this.diachi.lng = position.lng
      }
    }
  }
</script>
<style>
  div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }

  .dashboard-header {
    z-index: 9 !important;
  }

  a.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>
