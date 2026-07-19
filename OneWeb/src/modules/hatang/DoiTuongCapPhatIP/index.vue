<template src="./index.template.html"></template>

<script>
export default {
  data: function() {
    return {
      dataItems: [],
      dsSelected: [],
      currentItem: {
        id: 0,
        ten: '',
        tenDB: '',
        ghiChu: null,
        hieuLuc: 1
      },
      controls: {
        txtName: {
          enabled: false,
          invalid: false
        },
        checBoxHieuLuc: {
          enabled: false
        },
        txtTenDB: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false
        }
      },
      buttons: {
        btnNhapMoi: {
          enabled: true
        },
        btnGhiLai: {
          enabled: false
        },
        btnHuyBo: {
          enabled: false
        },
        btnSua: {
          enabled: false
        },
        btnXoa: {
          enabled: false
        }
      }
    }
  },
  computed: {
    gridItems: function() {
      return this.$refs.gridItems
    }
  },
  watch: {
    'currentItem.id': function(value) {
      this.buttons.btnSua.enabled = value > 0
      this.buttons.btnXoa.enabled = value > 0
    },
    'currentItem.ten': function(value) {
      this.controls.txtName.invalid = value == ''
    },
    'currentItem.tenDB': function(value) {
      this.controls.txtTenDB.invalid = value == ''
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dataItems = await this.getItems()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getHieuLucText: function(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getColumnHieuLucTemplate: function() {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC)
              }
            }
          }
        }
      }
    },
    getItems: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/doituong-capphat-ip/danhsach')
        result = rs.data
        if (result) {
          this.setCurrentItemDefault()
        }
      } catch {}
      return result
    },
    updateItem: async function(args) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/doituong-capphat-ip/capnhat', {
          pId: args.id == 0 ? null : args.id,
          pTen: args.ten,
          pTenDB: args.tenDB,
          pGhiChu: args.ghiChu,
          pHieuLuc: args.hieuLuc
        })
        result = rs.data.result
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    deleteItem: async function(id) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/doituong-capphat-ip/xoa', {
          id: id
        })
        result = rs.data.result
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    enableControls: function(args) {
      this.controls.txtName.enabled = args
      this.controls.checBoxHieuLuc.enabled = args
      this.controls.txtTenDB.enabled = args
      this.controls.txtGhiChu.enabled = args

      this.buttons.btnGhiLai.enabled = args
      this.buttons.btnNhapMoi.enabled = !args
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled
      if (this.currentItem.id > 0) this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled
    },
    setCurrentItem: function(item) {
      this.currentItem.id = item.ID
      this.currentItem.ten = item.TEN
      this.currentItem.tenDB = item.TEN_DB
      this.currentItem.ghiChu = item.GHI_CHU
      this.currentItem.hieuLuc = item.HIEU_LUC
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        ID: 0,
        TEN: '',
        TEN_DB: '',
        GHI_CHU: null,
        HIEU_LUC: 1
      })
    },
    validateControls: function() {
      let msg = ''
      if (this.controls.txtName.invalid) {
        msg = 'Tên không được để trống.'
      }
      if (this.controls.txtTenDB.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Tên trong DB không được để trống.'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    gridItems_RowSelected: async function(args) {
      this.setCurrentItem(args.data)
    },
    gridItems_RowDeselected: async function(args) {
      let items = this.gridItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0])
      } else {
        this.setCurrentItemDefault()
      }
    },
    btnGhiLai_Click: async function(args) {
      if (this.validateControls()) {
        let flag = await this.$confirm('Có chắc bạn muốn lưu lại đối tượng cấp phát IP đang chọn?', 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => {
            return false
          })
        if (flag) {
          try {
            this.$root.showLoading(true)
            let rs = await this.updateItem(this.currentItem)
            if (rs) {
              if (this.currentItem.id === 0) {
                this.$root.toastSuccess('Thêm đối tượng cấp phát IP thành công')
              } else {
                this.$root.toastSuccess('Cập nhật đối tượng cấp phát IP thành công')
              }
              this.dataItems = await this.getItems()
            } else {
              if (this.currentItem.id === 0) {
                this.$root.toastError('Thêm đối tượng cấp phát IP thất bại')
              } else {
                this.$root.toastError('Cập nhật đối tượng cấp phát IP thất bại')
              }
            }
            this.enableControls(!rs)
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },
    btnNhapMoi_Click: async function(args) {
      this.enableControls(true)
      this.setCurrentItemDefault()
    },
    btnHuyBo_Click: async function(args) {
      let items = this.gridItems.getSelectedRecords()
      let item = null
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => x.ID == this.currentItem.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
    },
    btnSua_Click: async function(args) {
      this.enableControls(true)
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },
    btnXoa_Click: async function(args) {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      let flag = await this.$confirm('Có chắc bạn muốn xóa đối tượng cấp phát IP đang chọn?', 'Xác nhận thực hiện', {
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
          let promises = []
          for (let item of this.dsSelected) {
            promises.push(await this.deleteItem(item.ID))
          }
          let res = await Promise.all(promises)
          let ok = res.filter((x) => x === true)

          if (ok.length > 0) {
            this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục`)
          } 
          this.dataItems = await this.getItems()
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false)
        }
      }
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
