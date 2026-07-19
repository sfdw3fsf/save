<template src="./index.html"></template>

<script>
import InterfaceComponent from './TabInterface/index.vue' // import component Interface
import PowerComponent from './TabPower/index.vue' // import component Power
import ConsoleComponent from './TabConsole/index.vue' // import component TabConsole

export default {
  components: {
    InterfaceComponent,
    PowerComponent,
    ConsoleComponent
  },
  data: function() {
    return {
      currentItem: {
        id: 0,
        modelThietBi: '',
        hangSanXuatId: 0,
        tenHangSanXuat: '',
        chieuCao: '',
        thuocChungLoaiId: 0,
        tenChungLoaiCha: '',
        congSuatDinhDanh: '',
        ghiChu: '',
        hieuLuc: 1,
        fulldepth: 1,
        soLineCard: 0,
        soPowerModule: 0,
        soControlCard: 0,
        phanLoai: null
      },
      tab_index: 1, // Chỉ số tab đang chọn

      chungLoaiTbItemList: [],
      historyChangesList: [],
      // thuocChungLoaiTbList: [],

      selectNhaSanXuatList: [],
      controls: {
        txtModelThietBi: {
          enabled: false,
          invalid: false
        },
        chkHieuLuc: {
          enabled: false
        },
        chkFulldepth: {
          enabled: false
        },
        selectNhaSanXuat: {
          enabled: false,
          invalid: false
        },
        selectChungLoaiTb: {
          enabled: false,
          invalid: false
        },
        selectVRF: {
          enabled: false,
          invalid: false
        },
        txtChieuCao: {
          enabled: false,
          invalid: false
        },
        txtCongSuatDinhDanh: {
          enabled: false,
          invalid: false
        },
        txtSoLineCard: {
          enabled: true,
          invalid: false
        },
        txtTongSoPort: {
          enabled: true,
          invalid: false
        },

        txtGhiChu: {
          enabled: false
        }
      },
      buttons: {
        btnAddNew: {
          enabled: true
        },
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        },
        btnEdit: {
          enabled: false
        },
        btnDelete: {
          enabled: false
        }
      },
      currentMode: 'ADD'
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    grdItems_haTangMang: function() {
      return this.$refs.grdItems_haTangMang
    },
    grdItems_subnet: function() {
      return this.$refs.grdItems_subnet
    },
    grdItems_history: function() {
      return this.$refs.grdItems_history
    }
  },
  watch: {
    'currentItem.id': function(value) {
      this.buttons.btnEdit.enabled = value > 0
      this.buttons.btnDelete.enabled = value > 0
    },
    'currentItem.ten': function(value) {
      this.controls.txtModelThietBi.invalid = value == ''
    },
    'currentItem.hangSanXuatId': function(value) {
      this.controls.selectNhaSanXuat.invalid = value == 0
    },
    'currentItem.thuocChungLoaiId': function(value) {
      this.controls.selectChungLoaiTb.invalid = value == 0
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.selectNhaSanXuatList = await this.getDmHangSx()
      // this.thuocChungLoaiTbList = await this.getDmChungLoaiThietBi();
      this.chungLoaiTbItemList = await this.getDsChungLoaiThietBi()
      this.setCurrentItemDefault()
      this.enableControls(true)
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getChungLoaiThietBiChaText: function(value) {
      let result = this.chungLoaiTbItemList.find((x) => x.ID == value)
      return result != null ? result.MODEL : ''
    },
    getChungLoaiThietBiChaColumnTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getChungLoaiThietBiChaText(this.data.CHUNGLOAITHIETBI_ID)
              }
            }
          }
        }
      }
    },
    getHangSanXuatText: function(value) {
      let result = this.selectNhaSanXuatList.find((x) => x.ID == value)
      return result != null ? result.TEN : ''
    },
    getHangSanXuatColumnTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getHangSanXuatText(this.data.HANG_SX_ID)
              }
            }
          }
        }
      }
    },
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
    getDsChungLoaiThietBi: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/chungloai-thietbi/lay-ds-chungloai-thietbi')
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    loadHistoryChanges: async function() {
      try {
        this.$root.showLoading(true)
        this.historyChangesList = await this.getDsHistory()
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDsHistory: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get(
          `/web-ecms/chungloai-thietbi/history-changes?chungLoaiThietBiId=${this.currentItem.id}`
        )
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },

    getDmHangSx: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc/hatang_mang', {
          loaiDanhMuc: 'HATANG_HANGSX',
          thamSo1: null,
          thamSo2: null
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    getDmChungLoaiThietBi: async function() {
      // let result = [];
      // try {
      //   let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
      //     loaiDanhMuc: "HATANG_CHUNGLOAI_THIETBI",
      //     thamSo1: null,
      //     thamSo2: null
      //   });
      //   result = rs.data;
      // } catch (e) {
      //   if (!(e.response == null || e.response == undefined)) {
      //     if (!(e.response.data == null || e.response.data == undefined)) {
      //       this.$root.toastError(e.response.data.message_detail);
      //     }
      //   }
      // }
      // return result;
    },

    updateItem: async function(args) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/chungloai-thietbi/capnhat-chungloai-thietbi', {
          id: args.id == 0 ? null : args.id,
          model: args.modelThietBi?.trim(),
          nhasx_id: args.hangSanXuatId,
          chungloai_id: args.thuocChungLoaiId,
          chieucao: args.chieuCao,
          cs_dinhdanh: args.congSuatDinhDanh,
          ghi_chu: args.ghiChu?.trim(),
          hieu_luc: args.hieuLuc,
          fulldepth: args.fulldepth,
          so_line_card: args.soLineCard,
          so_power: args.soPowerModule,
          so_control_card: args.soControlCard
        })
        result = rs.data
      } catch (e) {
        throw e
      }
      return result
    },
    deleteItem: async function(id) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/chungloai-thietbi/xoa-chungloai-thietbi', { id: id })
        result = rs.data.result
      } catch (e) {
        // if (!(e.response == null || e.response == undefined)) {
        //   if (!(e.response.data == null || e.response.data == undefined)) {
        //     this.$root.toastError(e.response.data.message_detail);
        //   }
        // }
        console.warn(e.response.data.message_detail)
        throw e
      }
      return result
    },
    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnDelete.enabled = !args

      if (this.currentItem.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },
    setCurrentItem: async function(item) {
      this.currentItem.id = item.ID
      this.currentItem.modelThietBi = item.MODEL
      this.currentItem.hangSanXuatId = item.HANG_SX_ID
      this.currentItem.tenHangSanXuat = item.TENHANGSANXUAT
      this.currentItem.chieuCao = item.CHIEUCAO
      this.currentItem.thuocChungLoaiId = item.CHUNGLOAITHIETBI_ID
      this.currentItem.tenChungLoaiCha = item.THIETBICON
      this.currentItem.congSuatDinhDanh = item.CS_DINHDANH
      this.currentItem.ghiChu = item.GHI_CHU
      this.currentItem.hieuLuc = item.HIEU_LUC
      this.currentItem.fulldepth = item.FULLDEPTH
      this.currentItem.soLineCard = item.SO_LINECARD ?? 0
      this.currentItem.soPowerModule = item.SO_POWER ?? 0
      this.currentItem.soControlCard = item.SO_CONTROLCARD ?? 0
      this.currentItem.phanLoai = item.PHANLOAI ?? null
      // if (this.currentItem.vrfId != null) {
      //   this.vrfSelectionList = await this.getVrfItemsCat(this.currentItem.vrfId);
      // }
      // else {
      //   this.vrfSelectionList = [];
      // }
      // if (this.currentItem.zoneId != null) {
      //   this.zoneSelectionList = await this.getZoneItemsCat(this.currentItem.zoneId);
      // }
      // else {
      //   this.zoneSelectionList = [];
      // }
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        ID: null,
        MODEL: '',
        HIEU_LUC: 1,
        FULLDEPTH: 1,
        HANGSANXUAT_ID: null,
        CHIEUCAO: 0,
        CS_DINHDANH: 0,
        GHI_CHU: '',
        SO_LINECARD: 0,
        SO_POWER: 0,
        SO_CONTROLCARD: 0,
        CHUNGLOAITHIETBI_ID: null
      })

      // this.currentItem.id = item.ID;
      // this.currentItem.modelThietBi = item.MODEL;
      // this.currentItem.hangSanXuatId = item.HANGSANXUAT_ID;
      // this.currentItem.tenHangSanXuat = item.TENHANGSANXUAT;
      // this.currentItem.chieuCao = item.CHIEUCAO;
      // this.currentItem.thuocChungLoaiId = item.CHUNGLOAITHIETBI_ID;
      // this.currentItem.tenChungLoaiCha = item.THIETBICON;
      // this.currentItem.congSuatDinhDanh = item.CS_DINHDANH;
      // this.currentItem.ghiChu = item.GHI_CHU;
      // this.currentItem.hieuLuc = item.HIEU_LUC;
      // this.currentItem.fulldepth = item.FULLDEPTH;
      // this.currentItem.soLineCard = item.SO_LINECARD ?? 0;
      // this.currentItem.soPowerModule = item.SO_POWER ?? 0;
      // this.currentItem.soControlCard = item.SO_CONTROLCARD ?? 0;
    },
    validateControls: function() {
      let msg = ''
      if (this.currentItem.modelThietBi == null || this.currentItem.modelThietBi == '') {
        msg = 'Model thiết bị không được để trống.'
      }
      if (this.currentItem.hangSanXuatId == null || this.currentItem.hangSanXuatId == 0) {
        if (msg != '') msg += '\n\r'
        msg += 'Nhà sản xuất không được để trống.'
      }

      // Validate công suất định danh
      const cs = Number(this.currentItem.congSuatDinhDanh)
      if (isNaN(cs) || !Number.isInteger(cs)) {
        if (msg !== '') msg += '\n\r'
        msg += 'Công suất định danh chỉ được nhập số nguyên (đơn vị W).'
      } else if (cs < 0) {
        if (msg !== '') msg += '\n\r'
        msg += 'Công suất định danh không được nhỏ hơn 0.'
      } else if (cs > 9999) {
        if (msg !== '') msg += '\n\r'
        msg += 'Công suất định danh tối đa là 9999 (W).'
      }
      // Validate chiều cao (U)
      const chieuCao = Number(this.currentItem.chieuCao)
      if (isNaN(chieuCao) || !Number.isInteger(chieuCao)) {
        if (msg !== '') msg += '\n\r'
        msg += 'Chiều cao (U) chỉ được nhập số nguyên.'
      } else if (chieuCao < 0) {
        if (msg !== '') msg += '\n\r'
        msg += 'Chiều cao (U) không được nhỏ hơn 0.'
      } else if (chieuCao > 99) {
        if (msg !== '') msg += '\n\r'
        msg += 'Chiều cao (U) tối đa là 99.'
      }

      if (msg != '') {
        this.$root.toastError(msg)
        return false
      }
      return true
    },
    grdItems_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
        await this.loadHistoryChanges()
        this.currentMode = 'VIEW'
      } finally {
        this.$root.showLoading(false)
      }
    },
    // grdItems_RowDeselected: async function () {
    //   let items = this.grdItems.getSelectedRecords();
    //   console.log('items', items);
    //   if (!(items == null || items.length == 0)) {
    //     try {
    //       this.$root.showLoading(true);
    //       this.setCurrentItem(items[0]);

    //     } finally {
    //       this.$root.showLoading(false);
    //     }
    //   } else {
    //     this.setCurrentItemDefault();
    //   }
    // },
    onAddNew: async function() {
      this.setCurrentItemDefault()
      this.historyChangesList = []
      this.currentMode = 'ADD'
    },
    onSave: async function() {
      if (this.validateControls()) {
        let flag = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', {
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
              console.log(this.currentItem)
              if (this.currentItem.id == 0) {
                this.$root.toastSuccess('Thêm thiết bị thành công')
              } else {
                this.$root.toastSuccess('Cập nhật thiết bị thành công')
              }
              this.chungLoaiTbItemList = await this.getDsChungLoaiThietBi()
            } else {
              if (this.currentItem.id == 0) {
                this.$root.toastError('Thêm thiết bị thất bại')
              } else {
                this.$root.toastError('Cập nhật thiết bị thất bại')
              }
            }
          } catch (err) {
            // if (this.currentItem.id == 0) {
            //     this.$root.toastError("Thêm thiết bị thất bại");
            //   }
            //   else {
            //     this.$root.toastError("Cập nhật thiết bị thất bại");
            //   }
            console.log(err)
            this.$root.toastError(err.response.data.message_detail)
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },
    onCancel: async function(args) {
      let items = this.grdItems.getSelectedRecords()
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
    onEdit: async function(args) {
      // this.enableControls(true);
      this.currentMode = 'EDIT'
    },
    onDelete: async function(args) {
      // let items = this.grdItems.getSelectedRecords();
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý',
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
          let rs = await this.deleteItem(this.currentItem.id)
          if (rs) {
            this.setCurrentItemDefault()
            this.chungLoaiTbItemList = await this.getDsChungLoaiThietBi()
            // this.thuocChungLoaiTbList = await this.getDmChungLoaiThietBi();
            this.enableControls(false)
            this.$root.toastSuccess('Xóa thiết bị thành công')
          } else {
            this.$root.toastError('Xóa thiết bị thất bại')
          }
        } catch (err) {
          this.$root.toastError(err.response.data.message_detail)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    onChangeHangSanXuat: async function() {
      if (this.currentItem.hangSanXuatId != 0) {
        // try {
        //   this.$root.showLoading(true);
        //   this.vrfSelectionList = await this.getVrfItemsCat(this.currentItem.idcId);
        //   this.currentItem.vrfId = 0;
        //   this.currentItem.zoneId = 0;
        //   this.currentItem.thietBiId = 0;
        // } finally {
        //   this.$root.showLoading(false);
        // }
      }
    },
    isShowCardTab: function(phanLoai) {
      console.log('hêh', phanLoai)
      if (this.currentMode == 'ADD') return true
      if (this.currentItem.phanLoai == null) return true
      if (this.currentMode.phanLoai == phanLoai) return true
      return false
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
