<template src="./frmDiaChi.html"></template>
<script>

import API from '../../SearchSubsAccountAPI'

export default {
  name: 'ModalChonDiaChi',
  props: ['modalId', 'data'],
  created: async function() {
    this.dsTinh = []
    this.diachi.tinh_id = null
  },

  async mounted() {
    await this.lay_ds_tt_danhba()
    await this.focusMyElement()
  },

  computed: {
    thaydoiDiaChi: function() {

      if (this.tentinh != '') {
        this.diachi.diachimoi = this.tentinh
        this.diachi.diachimoi = this.tenquan + ', ' + this.diachi.diachimoi
        this.diachi.diachimoi = this.tenphuong + ', ' + this.diachi.diachimoi
        if (this.isPho && this.diachi.pho_id != 0)
          this.diachi.diachimoi = this.tenpho + ', ' + this.diachi.diachimoi
        if (this.isAp && this.diachi.ap_id != 0)
          this.diachi.diachimoi = this.tenap + ', ' + this.diachi.diachimoi
        if (this.isKhu && this.diachi.khu_id != 0)
          this.diachi.diachimoi = this.tenkhu + ', ' + this.diachi.diachimoi
        if (this.diachi.so_nha != null && this.diachi.so_nha != '')
          this.diachi.diachimoi = this.diachi.so_nha + ', ' + this.diachi.diachimoi
        return this.diachi.diachimoi
      }
      return ''
    }
  },
  watch: {
    isPho: async function(val) {
      await this.getDacDiem()
    },
    isAp: async function(val) {
      await this.getDacDiem()
    },
    isKhu: async function(val) {
      await this.getDacDiem()
    },
    'diachi.tinh_id': async function(val) {
      await this.onChangeTinh()
    },
    'diachi.quan_id': async function(val) {
      await this.onChangeQuan()
    },
    'diachi.phuong_id': async function(val) {
      await this.onChangePhuong()
    },
    'diachi.pho_id': async function(val) {
      await this.onChangePho()
    },
    'diachi.ap_id': async function(val) {
      await this.onChangeAp()
    },
    'diachi.khu_id': async function(val) {
      await this.onChangeKhu()
    }
  },
  methods: {
    onModalHidden() {
      this.dsTinh = []
      this.diachi.tinh_id = null
      this.diachi.quan_id = null
      this.diachi.phuong_id = null
      this.diachi.pho_id = null
      this.diachi.ap_id = null
      this.diachi.khu_id = null
      this.diachi.dacdiem_id = null
      this.diachi.so_nha = ''
    },
    focusMyElement: async function() {
      await this.getTinh()
      await this.getQuan()
      await this.getPhuong()


      // if (this.data.tinh_id != null) {
      //   // console.log(this.data)
      //   this.diachi.tinh_id = this.data.tinh_id
      //   this.tentinh = this.dsTinh.filter(
      //     (x) => x.tinh_id == this.diachi.tinh_id
      //   )[0].tentinh
      //   await this.getQuan()
      //   this.diachi.quan_id = this.data.quan_id
      //
      //   await this.getPhuong()
      //   this.diachi.phuong_id = this.data.phuong_id
      //
      //   await this.getPhoApKhu()
      //   if (this.data.phuong_id != null) {
      //     if (this.data.pho_id != null && this.data.pho_id != 0) {
      //       this.diachi.pho_id = this.data.pho_id
      //       this.isPho = true
      //     }
      //
      //     if (this.data.ap_id != null && this.data.ap_id != 0) {
      //       this.diachi.ap_id = this.data.ap_id
      //       this.isAp = true
      //     }
      //
      //     if (this.data.khu_id != null && this.data.khu_id != 0) {
      //       this.diachi.khu_id = this.data.khu_id
      //       this.isKhu = true
      //     }
      //     await this.getDacDiem()
      //     if (this.data.dacdiem_id != null && this.data.dacdiem_id != 0) {
      //       this.diachi.dacdiem_id = this.data.dacdiem_id
      //       this.isDacDiem = true
      //       this.isEnabledDacDiem = true
      //     }
      //   }
      //   this.diachi.so_nha = this.data.so_nha
      //   this.diachi.daydiachi = this.data.diachi
      //   this.diachi.diachimoi = this.data.diachi
      //
      //
      // } else {
      //   if (this.dsTinh != null && this.dsTinh.length > 0)
      //
      //     this.diachi.tinh_id = this.dsTinh[0].tinh_id
      //   this.tentinh = this.dsTinh.filter(
      //     (x) => x.tinh_id == this.diachi.tinh_id
      //   )[0].tentinh
      //   await this.getQuan()
      // }
    },
    async lay_ds_tt_danhba() {
      try {
        this.notification = ''
        this.danger_notify = true

        this.$root.showLoading(true)
        let rs = await API.post_fn_lay_tt_danhba(this.axios, {
          ds_para: JSON.stringify({
            KHACHHANG_ID: this.data.khachhang_id,
            THANHTOAN_ID: this.data.thanhtoan_id,
            THUEBAO_ID: this.data.thuebao_id,
            PHANLOAIKH_ID: this.data.phanloaikh_id ? this.data.phanloaikh_id : null,
            NHANVIEN_ID: this.data.phanloaikh_id ? this.data.phanloaikh_id : null,
            TUYENTHU_ID: this.data.phanloaikh_id ? this.data.phanloaikh_id : null
          })
        })

        let apiData = JSON.parse(rs.data.data)
        let diachiTB = apiData.RESULT.DS_DIACHI_TB[0]


        this.dataAPI_Diachi = {
          diachi_id: diachiTB.DIACHI_ID,
          diachild_id: diachiTB.DIACHILD_ID,
          tinh_id: diachiTB.TINH_ID,
          quan_id: diachiTB.QUAN_ID,
          phuong_id: diachiTB.PHUONG_ID,
          pho_id: diachiTB.PHO_ID,
          ap_id: diachiTB.AP_ID,
          khu_id: diachiTB.KHU_ID,
          dacdiem_id: diachiTB.DACDIEM_ID,
          so_nha: diachiTB.SONHA,
          daydiachi: diachiTB.DIACHI,
          diachimoi: diachiTB.DIACHI,

          tinhld_id: diachiTB.TINHLD_ID,
          quanld_id: diachiTB.QUANLD_ID,
          phuongld_id: diachiTB.PHUONGLD_ID
        }

        console.log(this.dataAPI_Diachi)

        if(diachiTB.TINHLD_ID == -1 || diachiTB.QUANLD_ID == -1 || diachiTB.PHUONGLD_ID == -1
          || !diachiTB.TINHLD_ID|| !diachiTB.QUANLD_ID || !diachiTB.PHUONGLD_ID ) {

          this.notification = 'Địa chỉ lắp đặt đang chưa được cập nhật, hãy ấn nút CN Địa chỉ LĐ hoặc Tự động CN!'
          this.danger_notify = false
        }

        if(diachiTB.TINHLID == -1 || diachiTB.QUAN_ID == -1 || diachiTB.PHUONG_ID == -1
          || !diachiTB.TINH_ID|| !diachiTB.QUAN_ID || !diachiTB.PHUONG_ID ) {

          this.notification = 'Địa chỉ đang chưa được cập nhật, hãy ấn nút CN Địa chỉ hoặc Tự động CN!'
        }

        this.thaydoiDiaChi = this.dataAPI_Diachi.daydiachi

      } catch (e) {
        console.log(e)
      } finally {
        this.$root.showLoading(false)
      }
    },

    getTinh: async function() {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-hopdong/lapdatmoi/sp_lay_ds_tinhthanh'
        )
        this.dsTinh = rs.data
        if (this.dataAPI_Diachi.tinh_id) {
          this.diachi.tinh_id = this.dataAPI_Diachi.tinh_id
          console.log('this.diachi.tinh_id')
          console.log(this.diachi.tinh_id)
        }

      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getQuan: async function() {
      try {
        if (!this.diachi.tinh_id) return

        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_quan_huyen/' + this.diachi.tinh_id
        )

        console.log(this.dataAPI_Diachi.quan_id)
        if (this.dataAPI_Diachi.quan_id) {
          this.diachi.quan_id = this.dataAPI_Diachi.quan_id
          console.log('this.diachi.quan_id')
          console.log(this.diachi.quan_id)
        }

        this.dsQuan = rs.data
        if (this.diachi.quan_id != null)
          this.tenquan = this.dsQuan.filter(
            (x) => x.QUAN_ID == this.diachi.quan_id
          )[0].TEN_QUAN

      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    getPhuong: async function() {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_phuong_xa/' + this.diachi.quan_id
        )
        //console.log(rs.data)
        this.dsPhuong = rs.data
        // if(this.diachi.phuong_id!=null)
        //     this.tenphuong = this.dsPhuong.filter(
        //         x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG

        if (this.dataAPI_Diachi.phuong_id) {
          this.diachi.phuong_id = this.dataAPI_Diachi.phuong_id
          console.log('this.diachi.phuong_id')
          console.log(this.diachi.phuong_id)
        }
      } catch (error) {
        console.log(error)

      } finally {
        this.$root.showLoading(false)
      }
    },
    getPhoApKhu: async function() {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/' +
          this.diachi.phuong_id
        )
        if (rs.data != null) {
          this.dsPho = rs.data.dsPho
          if (this.dsPho.length > 0) this.diachi.pho_id = this.dsPho[0].PHO_ID
          if (this.diachi.pho_id != null && this.dsPho.length > 0)
            this.tenpho = this.dsPho.filter(
              (x) => x.PHO_ID == this.diachi.pho_id
            )[0].TEN_PHO
          this.dsAp = rs.data.dsAp
          if (this.dsAp.length > 0) this.diachi.ap_id = this.dsAp[0].PHO_ID
          if (this.diachi.ap_id != null && this.dsAp.length > 0)
            this.tenap = this.dsAp.filter(
              (x) => x.PHO_ID == this.diachi.ap_id
            )[0].TEN_PHO
          this.dsKhu = rs.data.dsKhu
          if (this.dsKhu.length > 0) this.diachi.khu_id = this.dsKhu[0].PHO_ID
          if (this.diachi.khu_id != null && this.dsKhu.length > 0)
            this.tenkhu = this.dsKhu.filter(
              (x) => x.PHO_ID == this.diachi.khu_id
            )[0].TEN_PHO

          if (this.dataAPI_Diachi.pho_id) {
            this.diachi.pho_id = this.dataAPI_Diachi.pho_id
          }

          await this.getDacDiem()
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDacDiem: async function() {
      let ipho_id = 0, ikhu_id = 0, iap_id = 0, iphuong_id = 0
      if (this.diachi.phuong_id)
        iphuong_id = this.diachi.phuong_id
      if (this.dsPho.length > 0 && this.isPho) {
        if (this.diachi.pho_id)
          ipho_id = this.diachi.pho_id
      }
      if (this.dsAp.length > 0 && this.isAp) {
        if (this.diachi.ap_id)
          iap_id = this.diachi.ap_id
      }
      if (this.dsKhu.length > 0 && this.isKhu) {
        if (this.diachi.khu_id)
          ikhu_id = this.diachi.khu_id
      }
      if (iphuong_id == 0 && ipho_id == 0 && ikhu_id == 0 && iap_id == 0) {
        this.dsDacDiem = []
        this.isDacDiem = false
        this.isEnabledDacDiem = false
      } else {
        try {
          this.dsDacDiem = []
          this.$root.showLoading(true)
          let rs = await this.$root.context.get(
            '/web-cabman/bando_tuyencot/lay_ds_dacdiem',
            {
              phuong_id: iphuong_id,
              pho_id: ipho_id,
              ap_id: iap_id,
              khu_id: ikhu_id
            }
          )
          this.dsDacDiem = rs.data
          if (this.dsDacDiem.length > 0) {
            this.diachi.dacdiem_id = this.dsDacDiem[0].DACDIEM_ID
            this.isDacDiem = false
            this.isEnabledDacDiem = true
          } else {
            this.isDacDiem = false
            this.isEnabledDacDiem = false
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
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
      await this.getQuan()
      this.tentinh = this.dsTinh.filter(
        (x) => x.tinh_id == this.diachi.tinh_id
      )[0].tentinh
    },
    onChangeQuan: async function() {
      await this.getPhuong()
      this.tenquan = this.dsQuan.filter(
        (x) => x.QUAN_ID == this.diachi.quan_id
      )[0].TEN_QUAN
    },
    onChangePhuong: async function() {
      await this.getPhoApKhu()
      this.tenphuong = this.dsPhuong.filter(
        x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG
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
    chapnhan: function(type) {
      let v = { ...this.diachi }
      let diachi = 0
      if (type == 'diachi_id') {
        diachi = this.dataAPI_Diachi.diachi_id
      } else {
        diachi = this.dataAPI_Diachi.diachild_id
      }
      if (
        this.isStrictMode &&
        ((!this.isPho && !this.isAp && !this.isKhu) ||
          (this.isPho && this.tenpho == '') ||
          (this.isAp && this.tenap == '') ||
          (this.isKhu && this.tenkhu == ''))
      ) {
        this.$toast.error('Hãy chọn Phố / Ấp / Khu')
        return
      }
      if (!this.phaichondacdiem) {
        if (this.dsDacDiem.length > 0 && !this.isDacDiem) {
          this.$bvModal.msgBoxConfirm('Bạn thật sự không muốn chọn đặc điểm ?',
            {
              title: 'Thông báo',
              centered: true,
              size: 'sm',
              okTitle: 'Yes',
              cancelTitle: 'No'
            }).then((value) => {
            if (value) {
              // if (this.dsDacDiem.length > 0 && this.isDacDiem){
              //   if (!this.diachi.dacdiem_id){
              //     this.$root.toastError("Hãy chọn đặc điểm!")
              //     this.$refs.dacdiem.focus()
              //     return
              //   }
              // }
              // this.$emit("xacnhan", this.diachi);
              // this.$emit('xacnhan', v)
              // this.$bvModal.hide(this.modalId)

              this.UpdateDiachi(diachi)
            } else {
              return
            }
          })
        } else {
          // this.$emit("xacnhan", this.diachi);
          this.UpdateDiachi(diachi)
        }
      } else {
        if (this.dsDacDiem.length > 0) {
          if (!this.isDacDiem) {
            this.isDacDiem = true
            this.$root.toastError('Hãy chọn đặc điểm!')
            this.$refs.dacdiem.focus()
            return
          }
          if (!this.diachi.dacdiem_id) {
            this.$root.toastError('Hãy chọn đặc điểm!')
            this.$refs.dacdiem.focus()
            return
          }
          // this.$emit("xacnhan", this.diachi);
          // this.$emit('xacnhan', v)
          // this.$bvModal.hide(this.modalId)
        }
      }
    },

    async UpdateDiachi(diachi_id) {
      if (!this.diachi.tinh_id && !this.diachi.quan_id && !this.diachi.phuong_id) {
        return this.$toast.warning('Hãy nhập đủ thông tin')
      }
      try {
        this.loading(true)
        let rs = await API.api_capnhat_diachi_thuebao(this.axios, {
          diachi: this.thaydoiDiaChi,
          tinh_id: this.diachi.tinh_id,
          quan_id: this.diachi.quan_id,
          phuong_id: this.diachi.phuong_id,
          pho_id: this.diachi.pho_id,
          diachi_id: diachi_id
        })
        if (rs.data.data == 'OK') {
          await this.lay_ds_tt_danhba()
          return this.$toast.success('Cập nhật thành công')
        } else {
          return this.$toast.error(rs.data.data)
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async AutoUpdateDiachi() {
      try {
        this.loading(true)
        let rs = await API.api_capnhat_tinh_quan_phuong_id_danhba(this.axios, {
          thuebao_id: this.data.thuebao_id
        })
        if (rs.data.data == 'ok') {
          await this.lay_ds_tt_danhba()
          await this.focusMyElement()
          return this.$toast.success('Cập nhật thành công')
        } else {
          return this.$toast.error('Cập nhật thất bại, hãy cập nhật thủ công!')
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    }
  },
  data: function() {
    return {
      diachi: {
        diachi_id: 0,
        tinh_id: 26,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        so_nha: '',
        daydiachi: '',
        diachimoi: ''
      },
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
      dsPho: [],
      dsAp: [],
      dsKhu: [],
      dsDacDiem: [],
      isPho: false,
      isAp: false,
      isKhu: false,
      isDacDiem: false,
      isEnabledDacDiem: false,
      isStrictMode: true,
      tentinh: '',
      tenquan: '',
      tenphuong: '',
      tenpho: '',
      tenap: '',
      tenkhu: '',
      phaichondacdiem: false,
      dataAPI_Diachi: {
        diachild_id: 0,
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        so_nha: '',
        daydiachi: '',
        diachimoi: ''
      },

      notification: '',
      danger_notify: true,
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
