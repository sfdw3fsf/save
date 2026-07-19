<template src="./ModalChonDiaChi.html"></template>
<script>
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import {debounce} from '@/utils/util'
export default {
  name: 'ModalChonDiaChi',
  props: ['modalId', 'data', 'isStrictMode', 'clearAfterClose'],
  created: async function () {
    this.dsTinh = []
    this.diachi.tinh_id = null
  },
  components: { ModalChonToaDo },
  computed: {
    f_toanha () {
      return this.data.F_TOANHA !== undefined && this.data.F_TOANHA !== null ? this.data.F_TOANHA : false
    },
    f_nhom_loainha_id () {
      return this.diachi.nhom_loainha_id
    },
    f_so_tang () {
      return this.diachi.so_tang
    },
    f_dien_tich () {
      return this.diachi.dien_tich
    },
    f_so_phong () {
      return this.diachi.so_phong
    },
    f_loainha : function () {
      return this.diachi.nhom_loainha_id !== null &&
        this.diachi.so_tang !== null  && this.diachi.so_tang !== '' && 
        this.diachi.dien_tich !== null && this.diachi.dien_tich !== '' && 
        this.diachi.so_phong !== null && this.diachi.so_phong !== ''
    },
    thaydoiDiaChi: function () {
      if (this.tentinh != '') {
        this.diachi.diachimoi = this.tentinh
        this.diachi.diachimoi = this.tenquan + ', ' + this.diachi.diachimoi
        this.diachi.diachimoi = this.tenphuong + ', ' + this.diachi.diachimoi
        if (this.isPho && this.diachi.pho_id != 0) { this.diachi.diachimoi = this.tenpho + ', ' + this.diachi.diachimoi }
        if (this.isAp && this.diachi.ap_id != 0) { this.diachi.diachimoi = this.tenap + ', ' + this.diachi.diachimoi }
        if (this.isKhu && this.diachi.khu_id != 0) { this.diachi.diachimoi = this.tenkhu + ', ' + this.diachi.diachimoi }
        if (this.diachi.so_nha != null && this.diachi.so_nha != '') { this.diachi.diachimoi = this.diachi.so_nha + ', ' + this.diachi.diachimoi }
        return this.diachi.diachimoi
      }
      return ''
    },
    getPosition () {
      return {
        lat: parseFloat(this.diachi.lat),
        lng: parseFloat(this.diachi.lng)
      }
    }
  },
  watch: {
    // f_loainha(newValue) {
    //   if (newValue) {
    //      this.getLoaiNha()
    //   }
    // }
    f_nhom_loainha_id: function(newVal, oldVal){
        if (this.f_loainha) {
          this.getLoaiNha()
        }
     },
    f_so_tang: function(newVal, oldVal){
        if (this.f_loainha) {
          this.getLoaiNha()
        }
     },
     f_dien_tich: function(newVal, oldVal){
        if (this.f_loainha) {
          this.getLoaiNha()
        }
     },
     f_so_phong: function(newVal, oldVal){
        if (this.f_loainha) {
          this.getLoaiNha()
        }
     }
  },
  methods: {
    onModalHidden () {
      if (this.clearAfterClose) {
        this.dsTinh = []
        this.diachi.tinh_id = null
        this.diachi.quan_id = null
        this.diachi.phuong_id = null
        this.diachi.pho_id = null
        this.diachi.ap_id = null
        this.diachi.khu_id = null
        this.diachi.dacdiem_id = null
        this.diachi.so_nha = ''
        this.diachi.diachimoi = ''
        this.diachi.daydiachi = ''
        this.diachi.nhom_loainha_id = null
        this.diachi.so_tang = null
        this.diachi.dien_tich = null
        this.diachi.so_phong = null
        this.diachi.ten_loainha = null
        this.diachi.block = null
        this.diachi.tang = null
        this.diachi.phong = null
        this.diachi.thongtin_them = null
        this.isAp = false
        this.isPho = false
        this.isKhu = false
        this.isDacDiem = false
        this.dsNhomLoaiNha = []
        this.dsTenLoaiNha = []
      }
      console.log('Dòng 112', this.data)
    },
    UpperCasePropertyList (obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[ upper ] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    LowerCasePropertyList (obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[ upper ] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    focusMyElement: async function () {
      await this.getTinh()
      
      console.log('🚀 ~ file: ModalChonDiaChi.vue:85 ~ this.data:', this.data)
      if (this.data) {
        if (this.data.F_TOANHA) {
          await this.getNhomLoaiNha()
        }
        var tsdiachi = Object.assign({}, this.data)
        tsdiachi = this.LowerCasePropertyList([tsdiachi])[0]
        console.log('🚀 ~ file: ModalChonDiaChi.vue:88 ~ tsdiachi:', tsdiachi)
        this.isDacDiem = tsdiachi.phaichondacdiem
        this.phaichondacdiem = tsdiachi.phaichondacdiem
        this.diachi.daydiachi = tsdiachi.diachi
        if (tsdiachi.tinh_id && tsdiachi.tinh_id !== 0) {
          this.diachi.tinh_id = tsdiachi.tinh_id
          let tinh = this.dsTinh.filter(
            (x) => Number(x.TINH_ID) === Number(this.diachi.tinh_id)
          )
          if (tinh && tinh.length > 0) {
            this.tentinh = tinh[0].TENTINH
          }
          this.diachi.quan_id = tsdiachi.quan_id
          await this.getQuan()
          this.diachi.phuong_id = tsdiachi.phuong_id
          await this.getPhuong()
          if (tsdiachi.phuong_id) {
            if (tsdiachi.pho_id && tsdiachi.pho_id !== 0) {
              this.diachi.pho_id = tsdiachi.pho_id
              await this.onChangePho()
              this.isPho = true
            }
            if (tsdiachi.ap_id && tsdiachi.ap_id !== 0) {
              this.diachi.ap_id = tsdiachi.ap_id
              this.isAp = true
            }

            if (tsdiachi.khu_id && tsdiachi.khu_id !== 0) {
              this.diachi.khu_id = tsdiachi.khu_id
              this.isKhu = true
            }
            await this.getPhoApKhu()
          }
          this.diachi.so_nha = tsdiachi.so_nha
          this.diachi.daydiachi = tsdiachi.diachi
          this.diachi.diachimoi = tsdiachi.diachi
        } else {
          if (this.dsTinh && this.dsTinh.length > 0) { this.diachi.tinh_id = this.$root.token.getPhanVungID() ? this.$root.token.getPhanVungID() : this.dsTinh[0].TINH_ID }
          let tinh = this.dsTinh.filter(
            (x) => Number(x.TINH_ID) === Number(this.diachi.tinh_id)
          )
          if (tinh && tinh.length > 0) {
            this.tentinh = tinh[0].TENTINH
          }
          await this.getQuan()
        }
        console.log('focusMyElement', this.data)
        if (this.data.TEN_LOAINHA) {
          this.diachi.ten_loainha = Number(this.data.TEN_LOAINHA)
          this.diachi.nhom_loainha_id = Number(this.data.NHOM_LOAINHA_ID)
          this.diachi.dien_tich = this.data.DIEN_TICH
          this.diachi.so_tang = this.data.SO_TANG
          this.diachi.so_phong = this.data.SO_PHONG
          this.diachi.block = this.data.BLOCK
          this.diachi.tang = this.data.TANG
          this.diachi.phong = this.data.PHONG
        }
      } else {
        if (this.dsTinh && this.dsTinh.length > 0) { this.diachi.tinh_id = this.$root.token.getPhanVungID() ? this.$root.token.getPhanVungID() : this.dsTinh[0].TINH_ID }
        let tinh = this.dsTinh.filter(
          (x) => Number(x.TINH_ID) === Number(this.diachi.tinh_id)
        )
        if (tinh && tinh.length > 0) {
          this.tentinh = tinh[0].TENTINH
        }
        await this.getQuan()
      }
    },

    getTinh: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get('/web-hopdong/lapdatmoi/sp_lay_ds_tinhthanh')
        this.dsTinh = this.UpperCasePropertyList(rs.data)
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getNhomLoaiNha: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_lay_ds_nhomloainha')
        let lst = [{ma_nhomln: null, ghichu: null, nhomln_id: -1, nhom_ln: '--'}]
        lst = [...lst, ...rs.data]
        this.dsNhomLoaiNha = this.UpperCasePropertyList(lst)
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadDSLoaiNha () {
      try {
        this.$root.showLoading(true)

        
        let data = {
          nhomln_id: this.diachi.nhom_loainha_id,
          dientich: this.diachi.dien_tich,
          sotang: this.diachi.so_tang,
          sophong: this.diachi.so_phong
        }
        let postData = {
          p_data : JSON.stringify(data)
        }
        this.diachi.ten_loainha = null
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_lay_ds_loainha', postData)
        this.dsTenLoaiNha = this.UpperCasePropertyList(rs.data)
        if (this.data.TEN_LOAINHA) {
          this.diachi.ten_loainha = Number(this.data.TEN_LOAINHA)
        }
        
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getLoaiNha: debounce(async function () {
      await this.loadDSLoaiNha()
    }, 1000),
    getQuan: async function () {
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
    getPhuong: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_phuong_xa/' + this.diachi.quan_id
        )
        this.dsPhuong = rs.data
        if (this.diachi.phuong_id && this.dsPhuong && this.dsPhuong.length > 0) {
          var listphuong = this.dsPhuong.filter(
            x => x.PHUONG_ID == this.diachi.phuong_id)
          if (listphuong && listphuong.length > 0) { this.tenphuong = listphuong[0].TEN_PHUONG } else if (this.dsPhuong && this.dsPhuong.length > 0) {
            this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID
            this.tenphuong = this.dsPhuong[0].TEN_PHUONG
          }
        } else if ((!this.diachi.phuong_id || this.diachi.phuong_id <= 0) && this.dsPhuong && this.dsPhuong.length > 0) {
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
    getPhoApKhu: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/' +
            this.diachi.phuong_id
        )
        console.log('🚀 ~ file: ModalChonDiaChi.vue:205 ~ rs', rs)
        if (rs.data != null) {
          this.dsPho = rs.data.dsPho
          if (this.diachi.pho_id && this.dsPho) {
            let pho = this.dsPho.filter((x) => x.PHO_ID === this.diachi.pho_id)
            console.log('🚀 ~ file: ModalChonDiaChi.vue:210 ~ pho', pho)
            if (pho && pho.length > 0) {
              this.tenpho = pho[0].TEN_PHO
            }
            // this.tenpho = this.dsPho.filter(
            //   (x) => x.PHO_ID == this.diachi.pho_id
            // )[0].TEN_PHO
          }
          this.dsAp = rs.data.dsAp
          if (this.diachi.ap_id && this.dsAp) {
            let ap = this.dsAp.filter((x) => x.PHO_ID === this.diachi.ap_id)
            if (ap && ap.length > 0) {
              this.tenap = ap[0].TEN_PHO
            }
            // this.tenap = this.dsAp.filter(
            //   (x) => x.PHO_ID === this.diachi.ap_id
            // )[0].TEN_PHO
          }
          this.dsKhu = rs.data.dsKhu
          console.log('🚀 ~ file: ModalChonDiaChi.vue:219 ~ this.dsKhu', this.dsKhu)
          if (this.diachi.khu_id && this.dsKhu) {
            let khu = this.dsKhu.filter((x) => x.PHO_ID === this.diachi.khu_id)
            if (khu && khu.length > 0) {
              this.tenkhu = khu[0].TEN_PHO
            }
            // this.tenkhu = this.dsKhu.filter(
            //   (x) => x.PHO_ID === this.diachi.khu_id
            // )[0].TEN_PHO
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: ModalChonDiaChi.vue:228 ~ error', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDacDiem: async function () {
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
        console.log('🚀 ~ file: ModalChonDiaChi.vue:268 ~ this.dsDacDiem:', this.dsDacDiem)
        if (this.dsDacDiem && this.dsDacDiem.length > 0) {
          this.isDacDiem = true
          if (!this.diachi.dacdiem_id || this.diachi.dacdiem_id <= 0) this.diachi.dacdiem_id = this.dsDacDiem[0].DACDIEM_ID
        } else {
          this.isDacDiem = false
          this.diachi.dacdiem_id = 0
        }
        console.log('🚀 ~ file: ModalChonDiaChi.vue:274 ~ this.isDacDiem:', this.isDacDiem)
      } catch (error) {
        console.log('🚀 ~ file: ModalChonDiaChi.vue:276 ~ error:', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    saveDiachi: async function () {
      try {
        this.$root.showLoading(true)
      } catch (error) {
        this.$toast.error('Lưu ảnh thất bại')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onChangeTinh: async function () {
      await this.getQuan()
      this.tentinh = this.dsTinh.filter(
        (x) => x.TINH_ID == this.diachi.tinh_id
      )[0].TENTINH
    },
    onChangeQuan: async function () {
      await this.getPhuong()
      this.tenquan = this.dsQuan.filter(
        (x) => x.QUAN_ID == this.diachi.quan_id
      )[0].TEN_QUAN
    },
    onChangePhuong: async function () {
      await this.getPhoApKhu()
      var listphuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id)
      if (listphuong && listphuong.length > 0) { this.tenphuong = listphuong[0].TEN_PHUONG }
    },
    onChangePho: async function () {
      await this.getDacDiem()
      this.tenpho = this.dsPho.filter(
        (x) => x.PHO_ID == this.diachi.pho_id
      )[0].TEN_PHO
    },
    onChangeKhu: async function () {
      await this.getDacDiem()
      this.tenkhu = this.dsKhu.filter(
        (x) => x.PHO_ID == this.diachi.khu_id
      )[0].TEN_PHO
    },
    onChangeAp: async function () {
      await this.getDacDiem()
      this.tenap = this.dsAp.filter(
        (x) => x.PHO_ID == this.diachi.ap_id
      )[0].TEN_PHO
    },
    chapnhan: async function () {
      if (!this.isPho) this.diachi.pho_id = 0
      if (!this.isAp) this.diachi.ap_id = 0
      if (!this.isKhu) this.diachi.khu_id = 0

      if (
        this.isStrictMode &&
        ((!this.isPho && !this.isAp && !this.isKhu) ||
          (this.isPho && this.tenpho == '') ||
          (this.isAp && this.tenap == '') ||
          (this.isKhu && this.tenkhu == ''))
      ) {
        this.$toast.warning('Hãy chọn Phố / Ấp / Khu')
        return
      }
      if (!this.phaichondacdiem) {
        console.log('🚀 ~ file: ModalChonDiaChi.vue:344 ~ this.phaichondacdiem:', this.phaichondacdiem)
        if (this.dsDacDiem && this.dsDacDiem.length > 0) {
          console.log('🚀 ~ file: ModalChonDiaChi.vue:340 ~ this.isDacDiem:', this.isDacDiem)
          console.log('🚀 ~ file: ModalChonDiaChi.vue:340 ~ this.isDacDiem:', this.dsDacDiem)
          if (this.isDacDiem && this.diachi.dacdiem_id <= 0 && (this.dsDacDiem.length > 1 || (this.dsDacDiem.length == 1 && this.dsDacDiem[0].DACDIEM_ID > 0))) {
            this.$toast.warning('Hãy chọn đặc điểm!')
            return
          }
          if (!this.isDacDiem || (this.isDacDiem && this.diachi.dacdiem_id <= 0)) {
            var cfr = await this.$bvModal.msgBoxConfirm(`Bạn thật sự không muốn chọn đặc điểm?`, {
              title: 'Thông báo',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            }).then(v => {
              console.log('🚀 ~ file: ModalChonDiaChi.vue:350 ~ v:', v)
              return v
            })
            console.log('🚀 ~ file: ModalChonDiaChi.vue:355 ~ cfr:', cfr)
            if (!cfr) {
              return
            } else {
              this.diachi.dacdiem_id = 0
            }
          }
        }
        console.log('🚀 ~ file: ModalChonDiaChi.vue:3445 ~ this.phaichondacdiem:', this.phaichondacdiem)
        
      } else {
        console.log('🚀 ~ file: ModalChonDiaChi.vue:375 ~ his.dsDacDiem:', this.dsDacDiem)
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
        }
      }

      if (this.data.F_TOANHA) {
        let errors = []
        if (this.diachi.nhom_loainha_id !== null) {
          if (this.diachi.ten_loainha === null || this.diachi.ten_loainha === -1) {
            errors.push('Chưa nhập đủ thông tin loại nhà khách hàng.')
          }
          if (this.diachi.block === null) {
            errors.push('Chưa nhập đủ thông tin Block(lô/ toà) khách hàng.')
          }
          if (this.diachi.tang === null) {
            errors.push('Chưa nhập đủ thông tin Tầng(lầu) khách hàng.')
          }
          if (this.diachi.phong === null) {
            errors.push('Chưa nhập đủ thông tin Số căn hộ/sạp/phòng khách hàng.')
          }
          if (errors.length > 0) {
            this.$toast.error(errors.join('\r\n'))
            return false
          }
        }        
      }
      let retData = {...this.diachi}
      console.log('retData', retData)
      this.$emit('xacnhan', retData)
      this.$bvModal.hide(this.modalId)
    },
    XacNhanToaDo (position) {
      this.diachi.lat = position.lat
      this.diachi.lng = position.lng
    }
  },
  data: function () {
    return {
      diachi: {
        diachi_id: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null,
        so_nha: '',
        daydiachi: '',
        diachimoi: '',
        lat: 0,
        lng: 0,
        nhom_loainha_id: null,
        so_tang: null,
        dien_tich: null,
        so_phong: null,
        ten_loainha: null,
        block: null,
        tang: null,
        phong: null,
        thongtin_them: null
      },
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
      dsPho: [],
      dsAp: [],
      dsKhu: [],
      dsDacDiem: [],
      dsLoaiNha: [],
      dsNhomLoaiNha: [],
      dsTenLoaiNha: [],
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
      phaichondacdiem: false
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
