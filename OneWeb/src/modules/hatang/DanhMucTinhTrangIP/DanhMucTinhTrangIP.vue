<template src="./DanhMucTinhTrangIP.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import Vue from 'vue'
import api from './api.js'

export default Vue.extend({
  data() {
    return {
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: true,
      isDisableDelete: true,
      dsSelected: [],
      datasource: [],
      form: {
        pId: null,
        pTen: "",
        pGhiChu: "",
        pHieuLuc: 1
      },
    }
  },
  mounted: async function () {
    await this.getDanhSach();
  },
  methods: {
    getDanhSach: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/danhmuc/tinhtrang-ip/get-list-ip-status`
        );
        if (rs.data && rs.data.length > 0) {
          this.datasource = rs.data
          this.isDisableSave = false
          this.isDisableNew = false
          this.isDisableUpdate = false
          this.isDisableDelete = false
        } else {
          this.datasource = []
          this.isDisableSave = true
          this.isDisableNew = false
          this.isDisableUpdate = true
          this.isDisableDelete = true
        }

      } catch (error) {
        this.datasource = []
      }
    },
    validateData() {
      let errors = []
      if (this.form.pTen === null || this.form.pTen.trim() === '') {
        errors.push('Vui lòng nhập vào tên tình trạng.')
      } else {
        this.form.pTen = this.form.pTen.trim()
      }
      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      this.form.pHieuLuc = this.form.pHieuLuc === false ? 0 : 1
      return true
    },
    setDanhSach: async function () {
      try {
        if (!this.validateData()) return
        if (!this.isDisableSave || !this.isDisableUpdate) {

          try {
            console.log(this.form)
            const res = await api.capnhat(this.axios, { p_ds: JSON.stringify(this.form)})
            const data = await res.data

            if (data.error_code === 'BSS-00000000') {
              this.$toast.success("Cập nhật danh mục tình trạng IP thành công")
              await this.getDanhSach();
            } else {
              this.$toast.error(data.message_detail)
            }
          } catch (error) {
            console.log(error)
            if (error.data && error.data.message_detail) {
              this.$toast.error(error.data.message_detail)
            }
            return false
          }
        }
      } catch (error) { this.$toast.error("Có lỗi xảy ra") }
    },
    deleteDanhSach () {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      this.$confirm(
            'Bạn có muốn xoá các danh mục đã chọn ?',
            "Danh mục tình trạng IP",
            {
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
              await this.doDelete()
            })
    },
    async deleteItem (id) {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/delete-ip-status/` + id
        )
        if (rs.error === '200')
          return true
        return false
      } catch (error) {
        console.log(error)
        return false
      }
    },
    doDelete: async function () {
      let promises = []
      for (let item of this.dsSelected) {
        promises.push(await this.deleteItem(item))
      }
      let res = await Promise.all(promises)
      let ok = res.filter(x => x === true)

      console.log('OK', ok)

      if (ok.length > 0) {
        this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục` )
      } else {
        this.$toast.error(`Đã phát sinh lỗi khi xoá danh mục.` )
      }
      await this.getDanhSach();
    },
    onSelectRowCheckbox(itemsCheck) {
      console.log('check', itemsCheck);
      this.dsSelected = itemsCheck;
    },
    onNew() {
      if (!this.isDisableNew) {
        this.form.pId = null
        this.form.pTen = "",
        this.form.pGhiChu = ""
        this.form.pHieuLuc = 1
        this.isDisableNew = true;
        this.isDisableDelete = true;
        this.isDisableSave = false;
        this.isDisableUpdate = true;
      }
    },

    onSelectRowChanged(selected) {
      this.isDisableSave = true
      this.isDisableNew = false
      this.isDisableUpdate = false
      this.isDisableDelete = false

      this.form.pId = selected.data.ID;
      this.form.pTen = selected.data.TEN;
      this.form.pGhiChu = selected.data.GHI_CHU;
      this.form.pHieuLuc = selected.data.HIEU_LUC;
    }

  },
})
</script>
