<template src="./index.template.html"></template>
<script>
export default {
  name: 'DanhMucTinhTrangHoatDong',
  data() {
    return {
      currentIdUpdate: 0,
      dsSelected: [],
      dsTinhTrangHoatDong: [],
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
  mounted: async function() {
    await this.getDanhSach()
    this.disableControls()
  },

  methods: {
    getDanhSach: async function() {
      try {
        const rs = await this.$root.context.post(`/web-ecms/danhmuc-tinh-trang-hoat-dong/get-all-tinh-trang-hoat-dong`)
        if (rs.error == '200' && rs.error_code == 'BSS-00000000') this.dsTinhTrangHoatDong = rs.data.data
        this.dsTinhTrangHoatDong.forEach((item) => {
          item.HIEU_LUC_TEXT = item.HIEU_LUC == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        })

        setTimeout(() => {
          if (this.currentIdUpdate > 0) {
            const pageSize = this.$refs.dsTinhTrangHoatDong.pageSize
            this.$refs.dsTinhTrangHoatDong.selectRow(this.dsTinhTrangHoatDong.findIndex(x => x.ID == this.currentIdUpdate) % pageSize)
            this.currentIdUpdate = 0
          }
        }, 100)
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
      }
    },
    checkForm: function() {
      var kq = true
      if (this.form.ten.trim().length == 0) {
        this.$root.toastError('Không được để trống tên')
        kq = false
      }
      return kq
    },
    updateTinhTrangHoatDong: function() {
      if (this.$refs.dsTinhTrangHoatDong) {
        // Vô hiệu hóa tất cả checkbox
        const checkboxes = this.$refs.dsTinhTrangHoatDong.$el.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
          checkbox.disabled = true;
        });

        // Vô hiệu hóa tất cả sự kiện click trên các dòng
        const rows = this.$refs.dsTinhTrangHoatDong.$el.querySelectorAll('tr');
        rows.forEach(row => {
          row.style.pointerEvents = 'none';
        });
      }
      const pageSize = this.$refs.dsTinhTrangHoatDong.pageSize
      this.currentIdUpdate = this.form.id

      // Chọn dòng hiện tại
      this.$refs.dsTinhTrangHoatDong.selectRow(
        this.dsTinhTrangHoatDong.findIndex(x => x.ID == this.form.id) % pageSize
      )

      this.buttons.btnSua.enabled = false
      this.buttons.btnGhiLai.enabled = true
      this.buttons.btnNhapMoi.enabled = false
      this.buttons.btnHuyBo.enabled = true
      this.enableControls()
    },
    createTinhTrangHoatDong: async function() {
      if (this.checkForm() && this.form.id == 0) {
        try {
          delete this.form.id
          this.form.hieuluc = this.form.hieuluc ? 1 : 0
          this.form.ten = this.form.ten.trim()
          this.form.ghichu = this.form.ghichu.trim()
          const rs = await this.$root.context.post(`/web-ecms/danhmuc-tinh-trang-hoat-dong/insert-tinh-trang-hoat-dong`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Thêm mới thành công')
          }
          await this.getDanhSach()
          this.canceleTinhTrangHoatDong()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      } else if (this.checkForm() && this.form.id != 0) {
        try {
          this.form.hieuluc = this.form.hieuluc ? 1 : 0
          this.form.ten = this.form.ten.trim()
          this.form.ghichu = this.form.ghichu.trim()
          const rs = await this.$root.context.post(`/web-ecms/danhmuc-tinh-trang-hoat-dong/update-tinh-trang-hoat-dong`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Cập nhật thành công')
          }
          await this.getDanhSach()
          this.canceleTinhTrangHoatDong()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      }
    },
    deleteDanhSach: async function() {
      try {
        if (this.dsSelected.length == 0) {
          this.$toast.error('Vui lòng chọn tình trạng hoạt động để xóa')
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
            const rs = await this.$root.context.post(`/web-ecms/danhmuc-tinh-trang-hoat-dong/delete-tinh-trang-hoat-dong`, { id: item })
            if (rs.error == '200' && rs.error_code == 'BSS-00000000') count++
          }

          this.$toast.success('Xóa tình trạng hoạt động thành công ' + count + ' tình trạng hoạt động')
        }
      } catch (error) {
        if (error.message_detail) this.$toast.error(error.message_detail)
      } finally {
        await this.getDanhSach()
      }
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },
    canceleTinhTrangHoatDong: async function () {
      if (this.$refs.dsTinhTrangHoatDong) {
        // Bật lại các sự kiện click khi hủy bỏ
        const rows = this.$refs.dsTinhTrangHoatDong.$el.querySelectorAll('tr');
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