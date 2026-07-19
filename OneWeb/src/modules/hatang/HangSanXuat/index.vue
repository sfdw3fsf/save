<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
export default {
  data() {
    return {
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      dsSelected: [],
      dsHangSanXuat: [],
      form: {
        pId: 0,
        pTen: "",
        pMaHSX: "",
        pGhiChu: "",
        pHieuLuc: 1
      },
      selectionOptions: {
        type: 'Single',      // chỉ chọn 1 dòng
        mode: 'Row',         // chọn theo dòng
        checkboxOnly: false, // không cần checkbox
      }
    }
  },
  mounted: async function () {
    await this.getDanhSach();
  },

  methods: {
    getDanhSach: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/danhmuc/hangsanxuat/danhsach`
        );
        if (rs.data) {
          this.dsHangSanXuat = rs.data;
          this.dsHangSanXuat.forEach(item => {
            item.HIEU_LUC_TEXT = item.HIEU_LUC == 1 ? 'Hiệu lực' : 'Không hiệu lực'
          });
        }

      } catch (error) { }
    },
    validateData() {
      const row = this.dsHangSanXuat.filter((item) => item.MA_HSX == this.form.pMaHSX && item.ID != this.form.pId)

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
    setDanhSach: async function () {
      if (this.validateData())
        try {
          if (!this.isDisableSave || !this.isDisableUpdate) {
            const rs = await this.$root.context.post(
              `/web-ecms/danhmuc/hangsanxuat/capnhat`, this.form
            );
            if (rs.error == "200")
              this.$toast.success("Cập nhật thành công")
            await this.getDanhSach();
          }
        } catch (error) { this.$toast.error("Có lỗi xảy ra") }
    },
    deleteDanhSach: async function () {
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
      if (flag)
        try {
          if (!this.isDisableDelete) {
            let count = 0;

            for (let item of this.dsSelected) {
              const rs = await this.$root.context.post(
                `/web-ecms/danhmuc/hangsanxuat/xoa/` + item
              );
              if (rs.error == "200")
                count++;
            }

            this.$toast.success("Xóa phiếu thành công " + count + " phiếu")

          }
        } catch (error) { this.$toast.error(error.response.data.message_detail) }
        finally {
          await this.getDanhSach();
        }
    },
    onSelectRowCheckbox(itemsCheck) {
      console.log('check', itemsCheck);
      this.dsSelected = itemsCheck;
    },
    onNew() {
      if (!this.isDisableNew) {
        this.form.pId = 0,
          this.form.pTen = "",
          this.form.pMaHSX = "",
          this.form.pGhiChu = "",
          this.form.pHieuLuc = 1
        this.isDisableNew = true;
        this.isDisableDelete = true;
        this.isDisableSave = false;
        this.isDisableUpdate = true;
      }
    },

    onSelectRowChanged(selected) {
      this.isDisableSave = true,
        this.isDisableNew = false,
        this.isDisableUpdate = false,
        this.isDisableDelete = false,

        this.form.pId = selected.data.ID;
      this.form.pTen = selected.data.TEN;
      this.form.pMaHSX = selected.data.MA_HSX;
      this.form.pGhiChu = selected.data.GHI_CHU;
      this.form.pHieuLuc = selected.data.HIEU_LUC;

    },
    editDanhSach() {
      this.isDisableDelete = true
      this.isDisableNew = true
      this.isDisableSave = false
      this.isDisableUpdate = true
    }

  },

  watch: {
  }

};

</script>