<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>
<script>
import chonDiaChi from './ModalChonDiaChi'
export default {
  data() {
    return {
      isShowDiaChi: false,
      dsNhaCungCap: [],
      query: '',
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      dsSelected: [],
      rowIndex: 0,
      form: {
        pId: 0,
        pTen: '',
        pMaNCC: '',
        pDiaChi: '',
        pDienThoai: '',
        pEmail: '',
        pWebsite: '',
        pSoTaiKhoan: '',
        pNguoiDaiDien: '',
        pGhiChu: '',
        pHieuLuc: 1
      },
      getChiTietDiaChi: {
        tinhThanh_id: 0,
        quanHuyen_id: 0,
        phuongXa_id: 0,
        duongPho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacDiem_id: 0,
        soNha: ''
      }
    }
  },
  components: {
    chonDiaChi
  },
  mounted: async function() {
    await this.getDanhSach()
  },

  methods: {
    closeModalDiaChi(data) {
      if (data.isUpdated) this.form.pDiaChi = data.diaChi
      console.log('diachi', data)
      if (this.isShowDiaChi) {
        this.isShowDiaChi = !this.isShowDiaChi
      }
    },
    getDanhSach: async function() {
      try {
        const rs = await this.$root.context.get(`/web-ecms/danhmuc/nhacungcap/danhsach`)
        if (rs.data) {
          this.dsNhaCungCap = rs.data
          this.dsNhaCungCap.forEach((item) => {
            item.HIEU_LUC_TEXT = item.HIEU_LUC == 1 ? 'Hiệu lực' : 'Không hiệu lực'
          })
        }
      } catch (error) {}
    },
    setDanhSach: async function() {
      if (this.validateData())
        try {
          if (!this.isDisableSave || !this.isDisableUpdate) {
            const rs = await this.$root.context.post(`/web-ecms/danhmuc/nhacungcap/capnhat`, this.form)
            if (rs.error == '200') this.$toast.success('Cập nhật thành công')
            await this.getDanhSach()
          }
        } catch (error) {
          this.$toast.error('Có lỗi xảy ra')
        }
    },
    onDataBound() {
      // Khi DataGrid load xong, tự động chọn hàng đã chọn
      if (this.$refs.dsTienTrinhGrid) {
        setTimeout(() => {
          this.$refs.dsTienTrinhGrid.selectRow(this.rowIndex) // Auto click hang da chọn
        }, 100)
      }
    },
    editDanhSach() {
      this.isDisableDelete = true
      this.isDisableNew = true
      this.isDisableSave = false
      this.isDisableUpdate = true
    },
    deleteDanhSach: async function() {
      try {
        if (!this.isDisableDelete) {
          let msg = `Bạn có muốn xóa mẫu tin đã chọn không?`
          let flag = await this.$confirm(msg, 'Xác nhận', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning',
            dangerouslyUseHTMLString: true
          })
            .then((s) => true)
            .catch((e) => {
              return false
            })
          if (flag) {
            let count = 0
            for (let item of this.dsSelected) {
              const rs = await this.$root.context.post(`/web-ecms/danhmuc/nhacungcap/xoa/` + item)
              if (rs.error == '200') count++
            }

            this.$toast.success('Xóa phiếu thành công ' + count + ' phiếu')
          }
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        await this.getDanhSach()
      }
    },
    onSelectRowCheckbox(itemsCheck) {
      console.log('check', itemsCheck)
      this.dsSelected = itemsCheck
    },
    validateEmail(email) {
      var re = /\S+@\S+\.\S+/
      return re.test(email)
    },
    validateData() {
      const row = this.dsNhaCungCap.filter((item) => item.MA_NCC == this.form.pMaNCC && item.ID != this.form.pId)

      if (row.length > 0) {
        this.$toast.error('Max NCC đã tồn tại')
        return false
      }

      if (!this.form.pTen) {
        this.$toast.error('Bạn chưa nhập tên NCC')
        return false
      }
      if (!this.form.pMaNCC) {
        this.$toast.error('Bạn chưa nhập mã NCC')
        return false
      }
      if (!this.validateEmail(this.form.pEmail) && this.form.pEmail) {
        this.$toast.error('Địa chỉ Email chưa đúng')
        return false
      }
      if (this.form.pDienThoai.length < 10 && this.form.pDienThoai.length > 0) {
        this.$toast.error('Số điện thoại chưa đúng')
        return false
      }
      return true
    },
    onNew() {
      if (!this.isDisableNew) {
        ;(this.form.pId = 0),
          (this.form.pTen = ''),
          (this.form.pMaNCC = ''),
          (this.form.pDiaChi = ''),
          (this.form.pDienThoai = ''),
          (this.form.pEmail = ''),
          (this.form.pWebsite = ''),
          (this.form.pSoTaiKhoan = ''),
          (this.form.pNguoiDaiDien = ''),
          (this.form.pGhiChu = ''),
          (this.form.pHieuLuc = 1)
        this.isDisableNew = true
        this.isDisableDelete = true
        this.isDisableSave = false
        this.isDisableUpdate = true
      }
    },

    onSelectRowChanged(selected) {
      if (this.isDisableSave) this.rowIndex = selected.rowIndex
      ;(this.isDisableSave = true),
        (this.isDisableNew = false),
        (this.isDisableUpdate = false),
        (this.isDisableDelete = false),
        (this.form.pId = selected.data.ID)
      this.form.pTen = selected.data.TEN
      this.form.pMaNCC = selected.data.MA_NCC
      this.form.pDiaChi = selected.data.DIA_CHI
      this.form.pDienThoai = selected.data.DIEN_THOAI
      this.form.pEmail = selected.data.EMAIL
      this.form.pWebsite = selected.data.WEBSITE
      this.form.pGhiChu = selected.data.GHI_CHU
      this.form.pSoTaiKhoan = selected.data.SO_TAI_KHOAN
      this.form.pNguoiDaiDien = selected.data.NGUOI_DAI_DIEN
      this.form.pHieuLuc = selected.data.HIEU_LUC
    }
  }
}
</script>
