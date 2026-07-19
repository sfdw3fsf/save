<template src="./ClassifyService.html"></template>
<style scoped src="./ClassifyService.scss"></style>
<script>
import FormThongTin from './part/FormThongTin'
import api from './ClassifyServiceAPI.js'
export default {
  components: {
    FormThongTin
  },
  data () {
    return {
      header: {
        title: 'Phân loại dịch vụ',
        list: [// Dữ liệu -> Phát triển thuê bao -> Bảng giá – Khuyến mại – Gói cước -> Phân loại dịch vụ
          { name: 'Dữ liệu', link: { name: 'Ui.cards' }},
          { name: 'Phát triển thuê bao', link: { name: 'Ui.buttons' }},
          { name: 'Bảng giá – Khuyến mại – Gói cước', link: { name: 'Ui.buttons' }},
          { name: 'Phân loại dịch vụ', link: { name: 'Ui.buttons' }}
        ]
      },
      form: {
        buttonNhapMoi: true,
        buttonGhiLai: false,
        buttonHuy: false,
        buttonXoa: false,
        goToFirstRecord: 0
      },
      formInput: {
        theFirst_DichVuVTId: null,
        theFirst_LoaiHinhTBId: null,
        dichVuVTId: null,
        loaiHinhTBId: null,
        tenPhanLoai: null,
        idPhanLoai: null,
        isInsert: true
      },
      table: {
        currentRecord: null,
        data: null,
        loading: false,
        options: [],
        header: [{fieldName: 'PHANLOAI', headerText: 'Tên phân loại dịch vụ', allowFiltering: true, width: 'auto'}],
        config: [
          {
            label: 'Tên phân loại dịch vụ',
            filterAble: true,
            key: 'PHANLOAI'
          }
        ],
        all_data: [],
        isNhapMoi : false,
        isGhiLai : false,
        isHuy : false,
        isXoa : false
      }
    }
  },
  mounted () {},
  computed: {
    classObjectNhapMoi() {
      return {
        'disabled' : !this.form.buttonNhapMoi && this.formInput.loaiHinhTBId != null
      }
    },
    classObjectGhiLai() {
      return {
        'disabled' : !this.form.buttonGhiLai && this.formInput.loaiHinhTBId != null
      }
    },
    classObjectHuy() {
      return {
        'disabled' : !this.form.buttonHuy && this.formInput.loaiHinhTBId != null
      }
    },
    classObjectXoa() {
      return {
        'disabled' : !this.form.buttonXoa && this.formInput.loaiHinhTBId != null
      }
    }
  },
  watch: {
    'table.currentRecord' (newValue, oldValue) {
      console.log('new value is ', newValue)
      if (newValue != oldValue && newValue != undefined && newValue != null) {
        this.grdDanhSachPhanLoaiChanged(0,this.table.currentRecord);
      }
    },
  },
  methods: {
    async  getPhanLoaiDichVu (isFirstLoad) {
      this.table.data = await this.$store.dispatch('classifyServiceCommon/getPhanLoaiDichVu')
      if (isFirstLoad) {
        this.formInput.dichVuVTId = this.formInput.theFirst_DichVuVTId
      } else { this.filterPhanLoaiDichVu(this.formInput.loaiHinhTBId) }
    },
    filterPhanLoaiDichVu (loaiHinhTBId) {
      this.table.loading = true
      this.table.options = this.table.data.filter(x => x.LOAITB_ID == loaiHinhTBId)
      this.table.loading = false
      this.btnHuyOnClick();
    },
    grdDanhSachPhanLoaiChanged (index, obj) {
      console.log('obj is ', obj)
      if (obj != undefined && obj != null) {
        // this.formInput.loaiHinhTBId = obj.LOAITB_ID
        this.formInput.tenPhanLoai = obj.PHANLOAI
        this.formInput.idPhanLoai = obj.PHANLOAI_ID
        this.formInput.isInsert = false

        // this.form.buttonNhapMoi = true
        // this.form.buttonGhiLai = true
        // this.form.buttonHuy = true
        // this.form.buttonXoa = true
      }
    },
    btnNhapMoiOnClick () {
      console.log('run in this')
      this.form.buttonNhapMoi = false
      this.form.buttonGhiLai = true
      this.form.buttonHuy = true
      this.form.buttonXoa = false
      console.log('this.form.buttonNhapMoi is ', this.form.buttonNhapMoi)
      this.formInput.tenPhanLoai = ''
      this.formInput.idPhanLoai = 0
      this.formInput.isInsert = true
      this.$refs.formThongTin.focusTenPhanLoai()
      // console.log('ref is ', this.$refs.formThongTin)
      // this.$refs.formThongTin.$refs.tenPhanLoaiInput.focus()
    },
    btnGhiLaiOnClick () {
      if (this.formInput.loaiHinhTBId == null || this.formInput.loaiHinhTBId == '') {
        this.$toast.error('Vui lòng chọn Loại hình thuê bao')
        $('#loaiHinhTBId').focus()
        return false
      }
      if (this.formInput.tenPhanLoai == null || this.formInput.tenPhanLoai.trim() == '') {
        this.$toast.error('Vui lòng nhập Tên phân loại dịch vụ')
        $('#tenPhanLoai').focus()
        return false
      }

      let objPhanLoaiDV = {
        'p_data': [
          {
            'LOAITB_ID': this.formInput.loaiHinhTBId,
            'PHANLOAI': this.formInput.tenPhanLoai,
            'PHANLOAI_ID': this.formInput.idPhanLoai
          }
        ],
        'p_is_insert': this.formInput.isInsert ? 1 : 0
      }
      api.insertUpdatePhanLoaiDichVu(this.axios, objPhanLoaiDV)
        .then(response => {
          if (response.data.message == 'Success') {
            this.$toast.success((this.formInput.isInsert ? 'Thêm mới' : 'Cập nhật') + ' Phân loại dịch vụ thành công')
            if (this.formInput.isInsert) {
              this.resetForm()
              this.getPhanLoaiDichVu()
            } else {
              let idxData = this.table.data.findIndex(x => x.PHANLOAI_ID == this.formInput.idPhanLoai)
              let idxOpt = this.table.options.findIndex(x => x.PHANLOAI_ID == this.formInput.idPhanLoai)
              this.table.data[idxData].PHANLOAI = this.formInput.tenPhanLoai
              this.table.options[idxOpt].PHANLOAI = this.formInput.tenPhanLoai
            }
          } else {
            console.log(response.data)
            // this.$toast.error(response.data.message);
            this.$toast.error('Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!')
          }
        })
        .catch(error => {
          console.log(error.response)
          if (!error.response.data.message) { this.$toast.error(error.response.data.message) }
        })
    },
    btnHuyOnClick () {
      if(this.table.options.length == 0){
        this.resetForm();
      } else {
        this.table.currentRecord = this.table.options[0];
      }
      this.form.buttonNhapMoi = true
      this.form.buttonGhiLai = false
      this.form.buttonHuy = false
      this.form.buttonXoa = false
      this.formInput.tenPhanLoai = ''
    },
    btnXoaOnClick () {
      if (confirm('Bạn thật sự muốn xóa dữ liệu không?')) {
        api.deletePhanLoaiDichVu(this.axios, { 'p_phanloai_id': this.formInput.idPhanLoai })
          .then(response => {
            if (response.data.message == 'Success') {
              this.$toast.success('Xóa Phân loại dịch vụ thành công')
              let idxData = this.table.data.findIndex(x => x.PHANLOAI_ID == this.formInput.idPhanLoai)
              let idxOpt = this.table.options.findIndex(x => x.PHANLOAI_ID == this.formInput.idPhanLoai)
              this.table.data.splice(idxData, 1)
              this.table.options.splice(idxOpt, 1)
            } else {
              console.log(response.data)
              // this.$toast.error(response.data.message);
              this.$toast.error('Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!')
            }
          })
          .catch(error => {
            console.log(error.response)
            if (!error.response.data.message) { this.$toast.error(error.response.data.message) }
          })
      }
    },
    resetForm () {
      this.form.buttonNhapMoi = true
      this.form.buttonGhiLai = false
      this.form.buttonHuy = false
      this.form.buttonXoa = false
      
      this.table.currentRecord = null
      this.formInput.tenPhanLoai = null
      this.formInput.idPhanLoai = 0
      this.formInput.isInsert = true
    },
    updateFormProps (newValue) {
      this.formInput.dichVuVTId = newValue.dichVuVTId
      this.formInput.loaiHinhTBId = newValue.loaiHinhTBId
      this.formInput.tenPhanLoai = newValue.tenPhanLoai
      this.formInput.theFirst_DichVuVTId = newValue.theFirst_DichVuVTId
      this.formInput.theFirst_LoaiHinhTBId = newValue.theFirst_LoaiHinhTBId

    }
  }
}
</script>
