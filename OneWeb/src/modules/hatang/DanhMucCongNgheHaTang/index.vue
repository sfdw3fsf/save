<template src="./index.html"></template>
<script>
export default {
  name: 'DanhMucCongNgheHaTang',
  data() {
    return {
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      dsSelected: [],
      dsCongNgheHaTang: [],
      loaiHaTangOptions: [],
            //     { id: 1, name: 'Hạ tầng mạng' },
            //     { id: 2, name: 'Hạ tầng ảo hóa' },
            //     { id: 3, name: 'Hạ tầng Smartcloud' },
            // ],
      form: {
        id: 0,
        ten: '',
        ghiChu: '',
        tenHaTang:'',
        haTang:'',
        hieuLuc: 1
      }
    }
  },
  mounted: async function() {
    await this.getDanhSach()
    await this.apiDmHaTang()

  },

  methods: {
    onLoaiHaTangChange() {
      this.haTang = this.form.loaiHaTang;

    },
    getDanhSach: async function() {
      try {
        const rs = await this.$root.context.post(`/web-ecms/hatang/hatang-congnghe/getall`)
        if (rs.error == '200' && rs.error_code == 'BSS-00000000') this.dsCongNgheHaTang = rs.data
        this.dsCongNgheHaTang.forEach((item) => {
          item.HIEU_LUC_TEXT = item.HIEU_LUC == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        })
      } catch (error) {}
    },
    apiDmHaTang: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/hatang-congnghe/dmHaTang");
        if (rs.error == '200' && rs.error_code == 'BSS-00000000') this.loaiHaTangOptions = rs.data

      } catch (error) {}
    },
    checkForm: function() {
      var kq = true
      if (this.form.ten.trim().length == 0) {
        this.$root.toastError('Không được để trống tên')
        kq = false
      }
      return kq
    },
    setDanhSach: async function() {},
    updateCongNgheHaTang: async function() {
      if (this.checkForm() && !this.isDisableUpdate) {
        try {
          this.form.hieuLuc = this.form.hieuLuc ? 1 : 0

          const rs = await this.$root.context.post(`/web-ecms/hatang/hatang-congnghe/update`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Cập nhật thành công')
          }
          await this.getDanhSach()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      }
    },
    createCongNgheHaTang: async function() {
      if (this.checkForm() && !this.isDisableSave) {
        try {
          delete this.form.id
          this.form.hieuLuc = this.form.hieuLuc ? 1 : 0
          const rs = await this.$root.context.post(`/web-ecms/hatang/hatang-congnghe/update`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Thêm mới thành công')
          }
          await this.getDanhSach()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      }
    },


    deleteDanhSach: async function() {
      try {
        if (!this.isDisableDelete) {
          let count = 0

          for (let item of this.dsSelected) {
            const rs = await this.$root.context.post(`/web-ecms/hatang/hatang-congnghe/delete`, { id: item })
            if (rs.error == '200' && rs.error_code == 'BSS-00000000') count++
          }

          this.$toast.success('Xóa phiếu thành công ' + count + ' phiếu')
        }
      } catch (error) {
        const errorMessage = error.response?.data?.message_detail || error.message_detail || error.message || 'Có lỗi xảy ra khi xóa phân loại hạ tầng'
        this.$toast.error(errorMessage)
      } finally {
        await this.getDanhSach()
      }
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },
    onNew() {
      if (!this.isDisableNew) {
        this.form.id = 0
        this.form.ten = ''
        this.form.ghiChu = ''
        this.form.hieuLuc = 1
        this.form.haTang=1
        this.isDisableNew = true
        this.isDisableDelete = true
        this.isDisableSave = false
        this.isDisableUpdate = true
      }
    },

    onSelectRowChanged(selected) {
      this.isDisableSave = true
      this.isDisableNew = false
      this.isDisableUpdate = false
      this.isDisableDelete = false
      this.form.id = selected.data.ID
      this.form.ten = selected.data.TEN
      this.form.ghiChu = selected.data.GHI_CHU
      this.form.hieuLuc = selected.data.HIEU_LUC
      this.form.tenHaTang = selected.data.TEN_HATANG
      this.form.haTang = selected.data.HATANG

    }
  },
  watch: {}
}
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
