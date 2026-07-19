<template src="./index.template.html"></template>
<script>
export default {
  name: 'DanhMucPhanLoaiHaTang',
  data() {
    return {
      currentIdUpdate: 0,
      dsSelected: [],
      dsPhanLoaiHatang: [],
      form: {
        id: 0,
        ten: '',
        ghichu: '',
        hieuluc: 1
      },
      controls: {
        txtTen: {
          enabled: false,
        },
        txtGhiChu: {
          enabled: false,
        },
        checkHieuLuc: {
          enabled: false,
        },
      },
      buttons: {
        btnNhapMoi: {
          enabled: true,
        },
        btnGhiLai: {
          enabled: false,
        },
        btnHuyBo: {
          enabled: false,
        },
        btnSua: {
          enabled: false,
        },
        btnXoa: {
          enabled: false,
        },
      },
    }
  },
  mounted: async function () {
    await this.getDanhSach()
    this.disableControls()
  },
  methods: {
    getDanhSach: async function () {
      try {
        const rs = await this.$root.context.post(`/web-ecms/danhmuc-phanloai-hatang/get-all-phanloai-hatang`)
        if (rs.error == '200' && rs.error_code == 'BSS-00000000') this.dsPhanLoaiHatang = rs.data.data
        this.dsPhanLoaiHatang.forEach((item) => {
          item.HIEU_LUC_TEXT = item.HIEU_LUC == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        })

        setTimeout(() => {
          if (this.currentIdUpdate > 0) {
            const pageSize = this.$refs.dsPhanLoaiHatang.pageSize
            this.$refs.dsPhanLoaiHatang.selectRow(this.dsPhanLoaiHatang.findIndex(x => x.ID == this.currentIdUpdate) % pageSize)
            this.currentIdUpdate = 0
          }
        }, 100)
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
      }
    },
    checkForm: function () {
      var kq = true
      if (this.form.ten.trim().length == 0) {
        this.$root.toastError('Không được để trống tên')
        kq = false
      }
      return kq
    },
    setDanhSach: async function () { },
    updatePhanLoaiHatang: function () {
      if (this.$refs.dsPhanLoaiHatang) {
        // Vô hiệu hóa tất cả checkbox
        const checkboxes = this.$refs.dsPhanLoaiHatang.$el.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
          checkbox.disabled = true;
        });

        // Vô hiệu hóa tất cả sự kiện click trên các dòng
        const rows = this.$refs.dsPhanLoaiHatang.$el.querySelectorAll('tr');
        rows.forEach(row => {
          row.style.pointerEvents = 'none';
        });
      }
      const pageSize = this.$refs.dsPhanLoaiHatang.pageSize
      this.currentIdUpdate = this.form.id

      // Chọn dòng hiện tại
      this.$refs.dsPhanLoaiHatang.selectRow(
        this.dsPhanLoaiHatang.findIndex(x => x.ID == this.form.id) % pageSize
      )

      this.buttons.btnSua.enabled = false
      this.buttons.btnGhiLai.enabled = true
      this.buttons.btnNhapMoi.enabled = false
      this.buttons.btnHuyBo.enabled = true
      this.enableControls()
    },
    createPhanLoaiHatang: async function () {
      if (this.checkForm() && this.form.id == 0) {
        try {
          delete this.form.id
          this.form.hieuluc = this.form.hieuluc ? 1 : 0
          this.form.ten = this.form.ten.trim()
          this.form.ghichu = this.form.ghichu?.trim()
          const rs = await this.$root.context.post(`/web-ecms/danhmuc-phanloai-hatang/insert-phanloai-hatang`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Thêm mới thành công')
          }
          await this.getDanhSach()
          this.cancelePhanLoaiHatang()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      } else if (this.checkForm() && this.form.id != 0) {
        try {
          this.form.hieuluc = this.form.hieuluc ? 1 : 0
          this.form.ten = this.form.ten.trim()
          this.form.ghichu = this.form.ghichu?.trim()
          const rs = await this.$root.context.post(`/web-ecms/danhmuc-phanloai-hatang/update-phanloai-hatang`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Cập nhật thành công')
          }
          await this.getDanhSach()
          this.cancelePhanLoaiHatang()
        } catch (error) {
          console.log(error)
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      }
    }
    ,
    deleteDanhSach: async function () {
      try {
        if (this.dsSelected.length == 0) {
          this.$toast.error('Vui lòng chọn phân loại hạ tầng để xóa')
          return
        }
        if (this.buttons.btnXoa.enabled) {
          const confirmed = await this.$confirm('Bạn có chắc chắn muốn xóa không?', {
            title: 'Xác nhận xóa',
            type: 'warning'
          })

          if (!confirmed) return

          let count = 0
          for (let item of this.dsSelected) {
            const rs = await this.$root.context.post(`/web-ecms/danhmuc-phanloai-hatang/delete-phanloai-hatang`, { id: item })
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
    cancelePhanLoaiHatang: async function () {
      if (this.$refs.dsPhanLoaiHatang) {
        // Bật lại các sự kiện click khi hủy bỏ
        const rows = this.$refs.dsPhanLoaiHatang.$el.querySelectorAll('tr');
        rows.forEach(row => {
          row.style.pointerEvents = 'auto';
        });
      }

      this.disableControls()
      this.buttons.btnNhapMoi.enabled = true
      this.buttons.btnXoa.enabled = true
      this.buttons.btnGhiLai.enabled = false
      this.buttons.btnHuyBo.enabled = false
      this.buttons.btnSua.enabled = true
      await this.getDanhSach()
    },
    onNew() {
      if (this.buttons.btnNhapMoi.enabled) {
        this.form.id = 0
        this.form.ten = ''
        this.form.ghichu = ''
        this.form.hieuluc = 1
        this.buttons.btnNhapMoi.enabled = false
        this.buttons.btnXoa.enabled = true
        this.buttons.btnHuyBo.enabled = true
        this.buttons.btnGhiLai.enabled = true
        this.buttons.btnSua.enabled = false
        this.enableControls()
      }
    },

    onSelectRowChanged(selected) {
      this.form.id = selected.data.ID ?? selected.ID
      this.form.ten = selected.data.TEN ?? selected.TEN
      this.form.ghichu = selected.data.GHI_CHU ?? selected.GHI_CHU
      this.form.hieuluc = selected.data.HIEU_LUC ?? selected.HIEU_LUC
      this.buttons.btnXoa.enabled = true
      this.buttons.btnGhiLai.enabled = false
      this.buttons.btnSua.enabled = true
    },
    enableControls: function () {
      this.controls.txtTen.enabled = true
      this.controls.txtGhiChu.enabled = true
      this.controls.checkHieuLuc.enabled = true
    },
    disableControls: function () {
      this.controls.txtTen.enabled = false
      this.controls.txtGhiChu.enabled = false
      this.controls.checkHieuLuc.enabled = false
    },
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
