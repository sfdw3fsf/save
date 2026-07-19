<template src="./index.template.html"></template>
<script>
import axios from 'axios'
import ModalImportExcel from '../components/Modals/ModalImportExcel'
const defaultItem = {
  id: 0,
  ten: '',
  mang_thiet_bi_id: 0,
  hieu_luc: 1,
  ghi_chu: ''
}
export default {
  components: {
    ModalImportExcel
  },
  props: {
    modalItemID: 0
  },
  data() {
    return {
      buttons: {
        btnNhapMoi: { enabled: true },
        btnGhiLai: { enabled: false },
        btnSua: { enabled: false },
        btnXoa: { enabled: false },
        btnHuyBo: { enabled: false }
      },
      originalName: '',
      listLoaiThietBiHeThong: [],
      currentItem: { ...defaultItem },
      controls: {
        txtID: { enabled: false, invalid: false },
        txtTen: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false },
        checkBoxHieuLuc: { enabled: false }
      },
      listMangThietBi: [],
      dsSelectedItems: [],
      tenExists: false,
      importConfig: {
        title: 'Import dữ liệu Loại thiết bị/hệ thống',
        templateFileName: 'Template_Import_LoaiThietBiHeThong.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên loại thiết bị/hệ thống' },
          { key: 'mang_thiet_bi_ten', label: 'Mảng thiết bị' },
          { key: 'hieu_luc', label: 'Hiệu lực (1 :Hiệu lực | 0 : không hiệu lực)' },
          { key: 'ghi_chu', label: 'Ghi chú' }
        ],
        gridColumns: [
          { key: 'ten', label: 'Tên loại thiết bị/hệ thống' },
          { key: 'mang_thiet_bi_ten', label: 'Mảng thiết bị' },
          { key: 'hieu_luc', label: 'Hiệu lực' },
          { key: 'hieu_luc', label: 'Hiệu lực (1 :Hiệu lực | 0 : không hiệu lực)' },
          { key: 'ghi_chu', label: 'Ghi chú' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên loại thiết bị/hệ thống không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên loại thiết bị/hệ thống không được vượt quá 100 ký tự' },
          { field: 'mang_thiet_bi_ten', required: true, message: 'Mảng thiết bị không được để trống' },
          { field: 'mang_thiet_bi_id', required: true, message: 'Mảng thiết bị không tồn tại' },
          { field: 'hieu_luc', required: true, message: 'Hiệu lực không được để trống' },
          {
            field: 'hieu_luc',
            type: 'number',
            enum: [0, 1],
            message: 'Hiệu lực chỉ nhận giá trị 0 và 1 (1 :Hiệu lực | 0 : không hiệu lực)  '
          },
          { field: 'ghi_chu', maxLength: 300, message: 'Ghi chú không được vượt quá 300 ký tự' }
        ],
        checkDataResult: true
      }
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      await this.fetchMangThietBi()
      await this.fetchLoaiThietBiHeThong()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    async fetchMangThietBi() {
      let rs = await this.$root.context.get('/web-ecms/danh-muc-loai-thiet-bi-he-thong/get-mang-thiet-bi-he-thong')
      // let rs = await axios.get('http://localhost:10990/danh-muc-loai-thiet-bi-he-thong/get-mang-thiet-bi-he-thong')
      return (this.listMangThietBi = rs.data || [])
    },
    async fetchLoaiThietBiHeThong() {
      // let rs = await axios.get('http://localhost:10990/danh-muc-loai-thiet-bi-he-thong/get-all')
      let rs = await this.$root.context.get('/web-ecms/danh-muc-loai-thiet-bi-he-thong/get-all')
      let rawList = rs.data || []
      this.listLoaiThietBiHeThong = rawList.map((item) => {
        const mang_thiet_bi = this.listMangThietBi.find((x) => x.mang_thiet_bi_id === item.mang_thiet_bi_id)
        return {
          ...item,
          mang_thiet_bi_ten: mang_thiet_bi ? mang_thiet_bi.mang_thiet_bi_ten : '',
          hieu_luc_text: item.hieu_luc == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        }
      })
    },
    async loadData() {
      this.$root.showLoading(true)
      await this.fetchMangThietBi()
      await this.fetchLoaiThietBiHeThong()
      this.$root.showLoading(false)
    },
    async addItem(args) {
      // let rs = await axios.post('http://localhost:10990/danh-muc-loai-thiet-bi-he-thong/insert-update', {
        let rs = await this.$root.context.post('/web-ecms/danh-muc-loai-thiet-bi-he-thong/insert-update', {
        ten: args.ten?.trim(),
        mang_thiet_bi_id: args.mang_thiet_bi_id,
        hieu_luc: args.hieu_luc,
        ghi_chu: args.ghi_chu?.trim()
      })
      return rs
    },
    async updateItem(args) {
      // let rs = await axios.post('http://localhost:10990/danh-muc-loai-thiet-bi-he-thong/insert-update', {
        let rs = await this.$root.context.post('/web-ecms/danh-muc-loai-thiet-bi-he-thong/insert-update', {
        id: args.id,
        ten: args.ten?.trim(),
        mang_thiet_bi_id: args.mang_thiet_bi_id,
        hieu_luc: args.hieu_luc,
        ghi_chu: args.ghi_chu?.trim()
      })
      return rs
    },
    async btnSaveClick() {
      if (this.validateControls()) {
        let flag = await this.$confirm('Có chắc bạn muốn lưu lại thiết bị/hệ thống đang chọn?', 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info'
        })
          .then(() => true)
          .catch(() => false)
        if (flag) {
          try {
            this.$root.showLoading(true)
            let rs = null
            if (this.currentItem.id == 0) {
              rs = await this.addItem(this.currentItem)
              if (rs) this.$root.toastSuccess('Thêm loại thiết bị hệ thống thành công')
            } else {
              rs = await this.updateItem(this.currentItem)
              if (rs) this.$root.toastSuccess('Cập nhật loại thiết bị hệ thống thành công')
            }
            this.enableControls(!rs)
            await this.loadData()
          } catch (e) {
            this.$root.toastError(rs.data.data.message)
          } finally {
            this.$root.showLoading(false)
            this.enableControls(false)
          }
        }
      }
    },
    async deleteItem(id) {
      try {
        const rs = await this.$root.context.post(`/web-ecms/danh-muc-loai-thiet-bi-he-thong/delete/${id}`)
        // const rs = await axios.delete(`https://api-dev-onebss.vnpt.vn/web-ecms/danh-muc-loai-thiet-bi-he-thong/delete/${id}`)
        // const rs = await axios.post(`http://localhost:10990/danh-muc-loai-thiet-bi-he-thong/delete/${id}`)
        return rs // dữ liệu trả về từ ApiResult
      } catch (error) {
        return error.response.data
      }
    },
    async addItems(items) {
      let successCount = 0
      let errorCount = 0

      for (let i = 0; i < items.length; i++) {
        const item = items[i]
        const rs = await this.addItem(item)
        if (rs.error == '200') {
          successCount++
        } else {
          errorCount++
        }
      }
      if (successCount > 0) this.$root.toastSuccess(`Lưu thành công ${successCount} loại thiết bị`)
      if (errorCount > 0) this.$root.toastError(`Bỏ qua ${errorCount} loại thiết bị`)
    },
    btnDeleteClick() {
      this.$confirm(`Bạn có chắc muốn xóa ${this.dsSelectedItems.length} loại thiết bị đã chọn?`, 'Xác nhận xóa', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then(async () => {
          try {
            this.$root.showLoading(true)
            let successCount = 0
            let errorCount = 0
            for (let i = 0; i < this.dsSelectedItems.length; i++) {
              const id = this.dsSelectedItems[i]
              const rs = await this.deleteItem(id)
              if (rs.error == '200') {
                successCount++
              } else {
                errorCount++
                if (rs.error_code === 'BSS-REF-EXISTS') {
                  this.$root.toastError(
                    `Loại thiết bị ID: ${id} đã có loại thông số thiết bị tham chiếu, không thể xoá`
                  )
                } else {
                  this.$root.toastError(rs?.message || `Xoá loại thiết bị ID: ${id} thất bại`)
                }
              }
            }
            if (successCount > 0) this.$root.toastSuccess(`Xóa thành công ${successCount} loại thiết bị`)
            if (errorCount > 0) this.$root.toastError(`Xóa thất bại ${errorCount} loại thiết bị`)

            await this.loadData()
            this.setDefaultItem()
            this.enableControls(false)
          } catch (e) {
            this.handleErrorResponse(e)
          } finally {
            this.$root.showLoading(false)
          }
        })
        .catch(() => {
          // Hủy xóa
        })
    },
    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)
        const validItems = []
        for (let i = 0; i < importData.length; i++) {
          const item = importData[i]
          // Chuẩn bị dữ liệu gửi API
          const importItem = {
            ten: item.ten?.trim(),
            mang_thiet_bi_id: item.mang_thiet_bi_id,
            ghi_chu: item.ghi_chu?.trim(),
            hieu_luc: item.hieu_luc
          }
          validItems.push(importItem)
        }
        // Gọi API lưu
        await this.addItems(validItems)

        // Reload data
        await this.loadData()
      } finally {
        this.$root.showLoading(false)
      }
    },
    processLoaiThietBiHeThongExcelData(dataRows) {
      return dataRows.map((row, index) => {
        const mang_thiet_bi_ten = (row[1] || '').trim().toUpperCase()
        const mang_thiet_bi = this.listMangThietBi.find(
          (x) => (x.mang_thiet_bi_ten || '').trim().toUpperCase() === mang_thiet_bi_ten
        )
        if (!mang_thiet_bi) {
          this.$root.toastError(` Không tìm thấy mảng thiết bị '${mang_thiet_bi_ten}' ở dòng ${index + 1}`)
        }
        return {
          ten: row[0] || '',
          mang_thiet_bi_id: mang_thiet_bi ? mang_thiet_bi.mang_thiet_bi_id : null,
          mang_thiet_bi_ten: mang_thiet_bi_ten,
          hieu_luc: row[2],
          ghi_chu: row[3] || ''
        }
      })
    },
    async handleCheckData(checkDataInfo) {
      try {
        const result = await this.validateDataWithServer(checkDataInfo.data)
        if (result.hasErrors) {
          this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
          this.$root.toastError('Dữ liệu có lỗi: ' + result.errorMessage)

          // emit cho modalImportExcel để hiển thị lỗi lên grid
          this.$refs.modalImportExcel.handleCheckDataResult({
            isValid: false,
            errors: result.errorMessage,
        })
              
        } else {
          this.importConfig.checkDataResult = false // Không có lỗi -> enable button "Ghi lại"
          this.$root.toastSuccess('Dữ liệu hợp lệ!')
        }
      } catch (error) {
        this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
        this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.message)
      }
    },
    async validateDataWithServer(dataList) {
      try {
        const payload = dataList.map((item) => {
          console.log(item)
          return {
            ten: item.ten?.trim(),
            mang_thiet_bi_id: item.mang_thiet_bi_id,
            ghi_chu: item.ghi_chu?.trim(),
            hieu_luc: item.hieu_luc
          }
        })
        // Gọi API backend
        const response = await this.$root.context.post(
          '/web-ecms/danh-muc-loai-thiet-bi-he-thong/validate-list-loai-thiet-bi',
          payload
        )
        // const response = await axios.post(
        //   'http://localhost:10990/danh-muc-loai-thiet-bi-he-thong/validate-list-loai-thiet-bi',
        //   payload
        // )

        const resData = response || {}
        return {
          hasErrors: resData.error !== '200',
          errorMessage: resData.message_detail || resData.message || ''
        }
      } catch (err) {
        return {
          hasErrors: true,
          errorMessage: err.response.data.message + '..'
        }
      }
    },
    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} loại thiết bị từ file Excel?`,
            'Xác nhận import',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning',
              dangerouslyUseHTMLString: true
            }
          )
            .then((s) => true)
            .catch((e) => false)

          if (confirmFlag) {
            await this.processImportData(result.data)
          }
        }
      } catch (error) {
        this.$root.toastError('Có lỗi khi import Excel: ' + error.message)
      }
    },
    btnExportExcel_Click: async function() {
      this.$refs.grdItems.excelExport({
        fileName: 'danh_muc_loai_thiet_bi.xlsx'
      })
    },
    btnNewClick() {
      this.enableControls(true)
      this.setDefaultItem()
    },
    btnCancelClick() {
      this.setDefaultItem()
      this.enableControls(false)
      this.tenExists = false
    },
    btnEditClick() {
      this.enableControls(true)
    },
    handleErrorResponse(error) {
      if (error?.response?.data) {
        this.$root.toastError(error.response.data.message_detail)
      }
    },
    getHieuLucText(value) {
      return ''
    },
    enableControls(args) {
      this.controls.txtTen.enabled = args
      this.controls.txtGhiChu.enabled = args
      this.controls.checkBoxHieuLuc.enabled = args
      this.buttons.btnGhiLai.enabled = args
      this.buttons.btnNhapMoi.enabled = !args
      this.buttons.btnHuyBo.enabled = args
      if (this.currentItem.id > 0) {
        this.buttons.btnSua.enabled = !args
      }
    },
    onSelectRowChanged(item) {
      if (!item || !item.data) return
      const data = item.data
      this.currentItem = {
        id: data.id,
        ten: data.ten,
        mang_thiet_bi_id: data.mang_thiet_bi_id,
        hieu_luc: data.hieu_luc,
        ghi_chu: data.ghi_chu
      }
      this.originalName = data.ten
      this.enableControls(false)
    },
    onSelectRowCheckbox(selectedItems) {
      this.dsSelectedItems = selectedItems || []
      this.buttons.btnXoa.enabled = this.dsSelectedItems.length > 0
    },
    setDefaultItem() {
      Object.assign(this.currentItem, defaultItem)
    },
    validateControls() {
      if (!this.currentItem.ten || this.currentItem.ten.trim() === '') {
        this.$root.toastError('Tên loại thiết bị không được để trống.')
        return false
      }
      if (!this.currentItem.mang_thiet_bi_id || this.currentItem.mang_thiet_bi_id === 0) {
        this.$root.toastError('Mảng thiết bị không được để trống.')
        return false
      }
      if (this.tenExists) {
        this.$root.toastError('Tên loại thiết bị đã tồn tại. Vui lòng chọn tên khác.')
        return false
      }
      return true
    },
    async checkTenLoaiThietBiBlur() {
      const newVal = this.currentItem.ten?.trim()

      if (!newVal) {
        this.tenExists = false
        return
      }

      if (this.currentItem.id > 0 && newVal === this.originalName) {
        this.tenExists = false
        return
      }

      try {
        const rs = await this.$root.context.get(
          `/web-ecms/danh-muc-loai-thiet-bi-he-thong/check-ten-loai-thiet-bi?ten=${encodeURIComponent(newVal)}`
        )
        this.tenExists = rs.data
        // const rs = await axios.get('http://localhost:10990/danh-muc-loai-thiet-bi-he-thong/check-ten-loai-thiet-bi', {
        //   params: { ten: newVal }
        // })
        // this.tenExists = rs.data.data >= 1
      } catch (e) {
        this.tenExists = false
        this.handleErrorResponse(e)
      }
    }
  }
}
</script>
