<template src="./index.template.html"></template>
<script>
const defaultItem = {
  id: 0,
  name: '',
  classify: null,
  code: '',
  note: null,
  validity: 1
}

export default {
  data() {
    return {
      buttons: {
        btnNhapMoi: {
          enabled: true
        },
        btnGhiLai: {
          enabled: false
        },
        btnSua: {
          enabled: false
        },
        btnXoa: {
          enabled: false
        },
        btnHuyBo: {
          enabled: false
        }
      },
      dataSource: [],
      dsPhanLoai: [],
      currentItem: {
        id: 0,
        name: '',
        classify: null,
        code: '',
        note: null,
        validity: 1
      },
      controls: {
        txtName: {
          enabled: false,
          invalid: false
        },
        checkBoxValidity: {
          enabled: false,
          invalid: false
        },
        comboboxClassify: {
          enabled: false
        },
        txtCode: {
          enabled: false,
          invalid: false
        },
        txtNote: {
          enabled: false
        }
      }
    }
  },
  computed: {
    // get component from ref which has value "gridItems"
    gridItems() {
      return this.$refs.gridItems
    }
  },
  watch: {
    'currentItem.id': function (value) {
      this.buttons.btnSua.enabled = value > 0
      this.buttons.btnXoa.enabled = value > 0
    },
    'currentItem.name': function (value) {
      this.controls.txtName.invalid = value == ''
    },
    'currentItem.code': function (value) {
      this.controls.txtCode.invalid = value == ''
    },
    'currentItem.classify': function (value) {
      this.controls.comboboxClassify.invalid = value == '' || value == null
    },
    'currentItem.validity': function (value) {
      if (value) {
        this.currentItem.validity = 1
      } else {
        this.currentItem.validity = 0
      }
    }
  },
  created: async function () {
    await this.load()
    this.dsPhanLoai = [
      { text: 'Thiết bị mạng', value: 'MANG' },
      { text: 'Thiết bị máy chủ', value: 'MAYCHU' },
      { text: 'Thiết bị phụ trợ và khác', value: 'KHAC' },
      { text: 'Thiết bị IDC', value: 'IDC' },
      { text: 'Thiết bị Tủ đĩa', value: 'TUDIA' }
    ]
  },
  methods: {
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    getHieuLucText(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getPhanLoaiText(value) {
      switch (value) {
        case 'MANG':
          return 'Mạng'
        case 'MAYCHU':
          return 'Máy chủ'
        default:
          return 'Khác'
      }
    },
    getColumnHieuLucTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC)
              }
            }
          }
        }
      }
    },
    async load() {
      try {
        this.$root.showLoading(true)
        await this.getList()
      } finally {
        this.$root.showLoading(false)
      }
    },
    async getList() {
      try {
        // call api here

        const rs = await this.$root.context.get('/web-ecms/danh-muc-loai-thiet-bi/get-all')

        if (rs.data && rs.data.length > 0) {
          this.dataSource = rs.data
          this.isDisableSave = false
          this.isDisableNew = false
          this.isDisableUpdate = false
          this.isDisableDelete = false
        } else {
          this.dataSource = []
          this.isDisableSave = true
          this.isDisableNew = false
          this.isDisableUpdate = true
          this.isDisableDelete = true
        }
      } catch (error) {
        this.dataSource = fakeData
      }
    },
    async addItem(args) {
      let rs = await this.$root.context.post('/web-ecms/danh-muc-loai-thiet-bi/create', {
        ten: args.name?.trim(),
        phanLoai: args.classify,
        ma: args.code?.trim(),
        ghiChu: args.note?.trim(),
        hieuLuc: args.validity
      })
      console.log(rs, 'Response from addItem')
      return rs.data
    },
    async updateItem(args) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/danh-muc-loai-thiet-bi/update', {
          id: args.id,
          ten: args.name.trim(),
          phanLoai: args.classify,
          ma: args.code.trim(),
          ghiChu: args.note.trim(),
          hieuLuc: args.validity
        })
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    async deleteItems(ids) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/danh-muc-loai-thiet-bi/delete-items', {
          id: ids
        })
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    enableControls(args) {
      this.controls.txtName.enabled = args
      this.controls.checkBoxValidity.enabled = args
      this.controls.comboboxClassify.enabled = args
      this.controls.txtCode.enabled = args
      this.controls.txtNote.enabled = args

      this.buttons.btnGhiLai.enabled = args
      this.buttons.btnNhapMoi.enabled = !args
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled
      if (this.currentItem.id > 0) {
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled
      }
    },
    setCurrentItem(item) {
      this.currentItem.id = item.ID
      this.currentItem.name = item.TEN
      this.currentItem.note = item.GHI_CHU
      this.currentItem.validity = item.HIEU_LUC
      // this.currentItem.classify = {
      //   text: this.getPhanLoaiText(item.PHANLOAI),
      //   value: item.PHANLOAI
      // }
      this.currentItem.classify = item.PHANLOAI
      this.currentItem.code = item.MA
    },
    setDefaultItem() {
      // Use object assign to keep the reference of currentItem and Vue reactive
      Object.assign(this.currentItem, defaultItem)
    },
    rowSelected(args) {
      if (args.data.length) {
        this.setCurrentItem(args.data[0])
      } else {
        this.setCurrentItem(args.data)
      }
    },
    rowUnselected(args) {
      // get all selected records from component has computed ref - DataGrid
      const items = this.gridItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        // if there are many records fill form with first item
        this.setCurrentItem(items[0])
      } else {
        this.setDefaultItem()
      }
    },
    validateControls() {
      let msg = ''
      if (this.controls.txtName.invalid) {
        msg = 'Tên loại thiết bị không được để trống.'
      }
      if (this.controls.comboboxClassify.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Phân loại loại thiết bị không được để trống.'
      }
      if (this.controls.txtCode.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Mã thiết bị không được để trống.'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    async btnSaveClick(args) {
      if (this.validateControls()) {
        let flag = await this.$confirm('Có chắc bạn muốn lưu lại loại thiết bị đang chọn?', 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => false)
        if (flag) {
          try {
            this.$root.showLoading(true)
            if (this.currentItem.id == 0) {
              let rs = await this.addItem(this.currentItem)
              if (rs) {
                this.$root.toastSuccess('Thêm loại thiết bị thành công')
              } 
              this.enableControls(!rs)
            } else {
              let rs = await this.updateItem(this.currentItem)
              if (rs) {
                this.$root.toastSuccess('Cập nhật loại thiết bị thành công')
              } 
              this.enableControls(!rs)
            }
          } catch (e) {
            console.log(e, 'Error while saving item')
            this.handleErrorResponse(e)
          } finally {
            this.$root.showLoading(false)
            this.enableControls(false)
            await this.load()
          }
        }
      }
    },
    btnNewClick() {
      this.enableControls(true)
      this.setDefaultItem()
    },
    btnCancelClick() {
      let list = this.gridItems.getSelectedRecords()
      let item = null
      if (!(list == null || list.length == 0)) {
        if (this.currentItem.id == 0) {
          item = list[0]
        } else {
          item = list.find((x) => x.ID == this.currentItem.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setDefaultItem()
      }
      this.enableControls(false)
    },
    btnEditClick() {
      this.enableControls(true)
    },
    async btnDeleteClick() {
      let flag = await this.$confirm('Có chắc bạn muốn xóa dữ liệu không?', 'Xác nhận thực hiện', {
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
        try {
          this.$root.showLoading(true)
          let rs = false
          let items = this.gridItems.getSelectedRecords()
          if (!(items == null || items == undefined)) {
            let ids = []
            items.forEach((item) => {
              ids.push(item.ID)
            })
            rs = await this.deleteItems(ids)
          }
          if (rs) {
            this.setDefaultItem()
            this.enableControls(false)
            this.$root.toastSuccess('Xóa loại thiết bị thành công')
          } else {
            this.$root.toastError('Xóa loại thiết bị thất bại')
          }
        } finally {
          this.$root.showLoading(false)
          await this.load()
        }
      }
    }
  }
}
</script>
<style scoped></style>
