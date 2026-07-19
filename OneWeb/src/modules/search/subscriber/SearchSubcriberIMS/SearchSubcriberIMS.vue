<template src="./SearchSubcriberIMS.html"></template>
<style src="./SearchSubcriberIMS.scss"></style>
<script>
import SearchSubcriberIMSAPI from './SearchSubcriberIMSAPI'
import breadcrumb from '@/components/breadcrumb'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import PopupKeyWatcher from '@/mixins/popupKeyWatcher'
//
export default {
  components: { breadcrumb },
  name: 'SearchSubcriberIMS',
  mounted() {
    //load ds loại hình thuê bao
    SearchSubcriberIMSAPI.lay_loai_hinh_tb(this.axios, { vdichvuvt_id: 11 }).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.loai_hinh.list = response.data.data
      }
    })
  },
  created() {
    this.kw = new PopupKeyWatcher(this, window)
    this.kw.register(null, this.onF3Pressed)
  },
  destroyed() {
    if (this.kw) this.kw.clear()
  },
  data() {
    return {
      kw: null,
      header: {
        title: 'Tra cứu thông tin IMS',
        list: [
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      TxtSoMay: '',
      TxtTrangThai: '',
      TxtTenTB: '',
      TxtTTChanCat: '',
      TxtMatKhau: '',
      TxtDiaChi: '',
      TxtGiayTo: '',
      TxtSLCuocGoi: '',
      TxtTenThueBao: '',
      TxtNoiCap: '',
      NgayTao: '',
      NgayCap: '',
      NgaySN: '',
      NgayHetHan: '',
      ketquatimkiem: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, cssClass: 'text-right' },
          { fieldName: 'ma_dvgt', headerText: 'Mã DVGT', allowFiltering: true },
          { fieldName: 'ten_dvgt', headerText: 'Tên dịch vụ gia tăng', allowFiltering: true }
        ]
      },
      listbox: {
        loai_hinh: {
          list: [],
          value: 62,
          isEnabled: false
        },
        ketquatimkiem3: {
          list: [],
          header: [],
          value: {},
          isEnabled: true
        }
      },
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/yyyy',
          max: new Date()
        }
      }
    }
  },
  methods: {
    onF3Pressed(e) {
      //console.log(e.keyCode)
      if (e.keyCode == 114) {
        this.showModal()
        e.preventDefault()
      }
    },
    onF3SearchPressed(e) {
      //console.log(e.keyCode)
      if (e.keyCode == 114) {
        this.btnSearchClicked()
        e.preventDefault()
      }
    },
    showModal() {
      //console.log("this.$refs['my-modal'].visible = " + this.$refs['my-modal'].visible)
      if (!this.$refs['my-modal'].visible) {
        this.$refs['my-modal'].show()
        this.kw.register(this.$refs['my-modal'], this.onF3SearchPressed)
      }
    },
    hideModal() {
      this.$refs['my-modal'].hide()
    },
    btnSearchClicked() {
      this.Clear()
      if (this.TxtSoMay == '' || this.TxtSoMay == null) {
        this.$toast.error('Bạn chưa nhập số máy')
      } else {
        var pdata1 = {}
        pdata1.vma_tb = this.TxtSoMay.trim()
        SearchSubcriberIMSAPI.tra_cuu_thue_bao(this.axios, pdata1).then((response) => {
          if (response.data.data.length == 0) {
            this.$toast.error('Không tìm thấy thông tin về thuê bao')
          }
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.ketquatimkiem3.list = response.data.data
            this.TxtTrangThai = this.listbox.ketquatimkiem3.list[0].trangthai
            this.TxtMatKhau = this.listbox.ketquatimkiem3.list[0].password
            this.TxtTenTB = this.listbox.ketquatimkiem3.list[0].ten_tb
            this.TxtGiayTo = this.listbox.ketquatimkiem3.list[0].so_gt
            this.TxtNoiCap = this.listbox.ketquatimkiem3.list[0].noicap
            this.TxtDiaChi = this.listbox.ketquatimkiem3.list[0].diachi_ld
            this.NgayTao = this.listbox.ketquatimkiem3.list[0].ngaytao
            this.NgayCap = this.listbox.ketquatimkiem3.list[0].ngaycap
            this.NgaySN = this.listbox.ketquatimkiem3.list[0].ngay_sn
            this.NgayHetHan = this.listbox.ketquatimkiem3.list[0].ngayhethan_gt
            this.listbox.loai_hinh.value = this.listbox.ketquatimkiem3.list[0].loaitb_id
            var pdata = {}
            pdata.p_ds_id_vtn = '69,66,60'
            SearchSubcriberIMSAPI.tracuu(this.axios, pdata).then((response) => {
              if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.ketquatimkiem.list = response.data.data
              }
            })
          }
        })
      }
    },
    Clear() {
      this.ketquatimkiem.list = ''
      this.listbox.ketquatimkiem3.list = ''
      this.TxtTrangThai = ''
      this.TxtMatKhau = ''
      this.TxtTenTB = ''
      this.TxtGiayTo = ''
      this.TxtNoiCap = ''
      this.TxtDiaChi = ''
      this.NgayTao = ''
      this.NgayCap = ''
      this.NgaySN = ''
      this.NgayHetHan = ''
    }
  }
}
</script>
