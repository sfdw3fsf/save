<template src="./index.template.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from 'moment'

export default {
  data() {
    return {
      isDisableSave: true,
      isDisableUpdate: false,
      dsTrangThaiU: [
            { ID: 1, TEN: 'Đã sử dụng' },
            { ID: 2, TEN: 'Chưa sử dụng [còn trống]' },
            { ID: 3, TEN: 'Đang lắp đặt' },
            { ID: 4, TEN: 'Hỏng' }
        ],
      dsSelected: [],
      dsThongTinU: [],
      form: {
        pIdU: 0,
        pIDRack: 0,
        pViTriU: 0,
        pGhiChu: "",
        pHieuLuc: 1,
        pTrangThaiU: null
      },
      selectionOptions: {
        type: 'Single',      // chỉ chọn 1 dòng
        mode: 'Row',         // chọn theo dòng
        checkboxOnly: false, // không cần checkbox
      }
    }
  },
  mounted: async function () {
    //console.log('dsTrangThaiU:', this.dsTrangThaiU);
    await this.getDanhSachU();
  },

  methods: {
    getDanhSachU: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/danhmuc/hangsanxuat/danhsach`
        );
        if (rs.data) {
          this.dsThongTinU = rs.data;
          this.dsThongTinU.forEach(item => {
            item.HIEU_LUC_TEXT = item.HIEU_LUC == 1 ? 'Hiệu lực' : 'Không hiệu lực'
          });
        }

      } catch (error) { }
    },
    validateData() {
      const row = this.dsThongTinU.filter((item) => item.MA_HSX == this.form.pMaHSX && item.ID != this.form.pId)

      if (row.length > 0) {
        this.$toast.error('Mã HSX đã tồn tại')
        return false
      }

      if (!this.form.pTen) {
        this.$toast.error('Bạn chưa nhập tên HSX')
        return false
      }
      if (!this.form.pMaHSX) {
        this.$toast.error('Bạn chưa nhập mã HSX')
        return false
      }

      return true
    },
    updateThongTinU: async function () {
      if (this.validateData())
        try {
          if (!this.isDisableSave || !this.isDisableUpdate) {
            const rs = await this.$root.context.post(
              `/web-ecms/danhmuc/hangsanxuat/capnhat`, this.form
            );
            if (rs.error == "200")
              this.$toast.success("Cập nhật thành công")
            await this.getDanhSachU();
          }
        } catch (error) { this.$toast.error("Có lỗi xảy ra") }
    },
    onSelectRowCheckbox(itemsCheck) {
      console.log('check', itemsCheck);
      this.dsSelected = itemsCheck;
    },
    onSelectRowChanged(selected) {
      this.isDisableSave = true,
        this.isDisableUpdate = false,
        this.form.pIdU = selected.data.ID;
      this.form.pTen = selected.data.TEN;
      this.form.pMaHSX = selected.data.MA_HSX;
      this.form.pGhiChu = selected.data.GHI_CHU;
      this.form.pHieuLuc = selected.data.HIEU_LUC;
    },
    editDanhSach() {
      this.isDisableSave = false
      this.isDisableUpdate = true
    },
    xuatExcel() {
      this.isDisableSave = false
      this.isDisableUpdate = true
    },
    btnSearchClick() {
      this.isDisableSave = false
      this.isDisableUpdate = true
    }
  },

  watch: {
  }

};

</script>