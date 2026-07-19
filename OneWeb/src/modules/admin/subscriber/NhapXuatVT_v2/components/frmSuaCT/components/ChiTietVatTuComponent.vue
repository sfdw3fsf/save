<template src="./ChiTietVatTu.html"></template>

<script>
import api from './api.js'
import _ from 'lodash'
export default {
  name: 'ChiTietVatTuComponent',
  emits: ['onEditChiTietVatTu'],
  props: {
    vkieuphieu_id: {
      type: [String, Number],
      default: '0'
    },
    vchungtu_id: {
      type: [String, Number],
      default: '0'
    },
    isAllowEdit: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      gridVatTu: {
        header: [
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
          { width: 120, fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { width: 300, fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { width: 70, fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
          { width: 60, fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true },
          { width: 120, fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true },
          { width: 350, headerText: 'Loại kho', template: this.gridVatTuCboLoaiKho(this), allowFiltering: false },
          { headerText: 'Mã kho TD', template: this.gridVatTuCboMaKhoTD(this), allowFiltering: false },
          { headerText: 'Chất lượng', template: this.gridVatTuCboChatLuong(this), allowFiltering: false },
          { headerText: 'TK_Kho', fieldName: 'TK_KHO', allowEditing: true, allowFiltering: true },
          { headerText: 'TK_DOIUNG', fieldName: 'TK_DOIUNG', allowEditing: true, allowFiltering: true },
          { headerText: 'Loại thiết bị', fieldName: 'LOAI_TBI', allowEditing: this.isAllowEdit, allowFiltering: true },
          { headerText: 'Ghi chú HĐ', fieldName: 'GHICHU_HD', allowEditing: this.isAllowEdit, allowFiltering: true, customAttributes: { class: 'thoai-test' } },
          { fieldName: 'VATTU_ID', visible: false, isPrimaryKey: true }
        ],
        dataSources: [],
        selectingItem: null
      },
      txtTKDUAll: { enabled: true, value: null },
      txtTK_Kho_All: { enabled: true, value: null },

      cboChatLuong: {
        list: []
      },
      cboLoaiKho: {
        list: []
      },
      cboMaKhoTD: {
        list: []
      }
    }
  },
  computed: {
    // isEditGhiChu(){
    //   return this.vkieuphieu_id == 2;
    // }
  },
  watch: {
    vchungtu_id(oldVal, newVal) {
      if (oldVal != newVal) {
        this.getDanhSachVaTu()
      }
    },
    'gridVatTu.dataSources'(val) {
      console.log('watcher gridVatTu.dataSources')
      if (val.filter((e) => e.ID_GEN == null || e.ID_GEN == '').length) {
        this.gridVatTu.dataSources = this.gridVatTu.dataSources.map((e) => {
          if (e.ID_GEN == null) e.ID_GEN = this.uuidv4()
          return e
        })
      }
      this.$emit('onEditChiTietVatTu', val)
    },
    async vkieuphieu_id(value) {
      if (value) {
        await this.getComboLoaiKho(value)
        await this.getDanhSachVaTu()
      }
    },
    isAllowEdit(value) {
      //checking
      if (!value) this.$refs.gridChiTietVatTu.grid.refresh()
    },
    'gridVatTu.list'(val) {}
  },

  methods: {
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    reset() {},
    async getDanhSachVaTu() {
      try {
        const response = await api.getDSVaTuHCM(this.axios, { chungtu_id: this.vchungtu_id })
        if (response.data.data) this.gridVatTu.dataSources = response.data.data

        if (!response?.data?.data.length) this.$toast.warning('Không có vật tư!')
      } catch (error) {
        console.log(error)
      }
    },
    getComboLoaiKho(vkieuphieu_id) {
      if (vkieuphieu_id) {
        // get all ds loai kho
        api
          .getDSLoaiKhoTheoMucDich(this.axios, vkieuphieu_id)
          .then((rsLoaiKho) => {
            if (rsLoaiKho.data.data) {
              this.cboLoaiKho.list = rsLoaiKho.data.data
            }
          })
          .catch((err) => {
            console.log(err)
          })
        // get all ds ma kho
        api
          .getDSMaKhoTD(this.axios)
          .then((rsMKTD) => {
            if (rsMKTD.data.data) {
              this.cboMaKhoTD.list = rsMKTD.data.data
            }
          })
          .catch((err) => {
            console.log(err)
          })

        // get all ds ma kho
        api
          .getDSChatLuong(this.axios)
          .then((rsDSCL) => {
            if (rsDSCL.data.data) {
              this.cboChatLuong.list = rsDSCL.data.data
            }
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    setLaiGridVatTu(val) {
      this.gridVatTu.dataSources = val
    },
    onGridVatTuCellSaved(args) {
      if (args) {
        if (args.columnName == 'TK_KHO') {
          this.gridVatTu.dataSources.forEach((e) => {
            if ((e.MA_VT = args.rowData.MA_VT)) {
              e.TK_KHO = args.value
            }
          })
        }
        if (args.columnName == 'TK_DOIUNG') {
          this.gridVatTu.dataSources.forEach((e) => {
            if ((e.MA_VT = args.rowData.MA_VT)) {
              e.TK_DOIUNG = args.value
            }
          })
        }
        if (args.columnName == 'LOAI_TBI') {
          this.gridVatTu.dataSources.forEach((e) => {
            if ((e.MA_VT = args.rowData.MA_VT)) {
              e.LOAI_TBI = args.value
            }
          })
        }
        if (args.columnName == 'GHICHU_HD') {
          this.gridVatTu.dataSources.forEach((e) => {
            if ((e.MA_VT = args.rowData.MA_VT)) {
              e.GHICHU_HD = args.value
            }
          })
        }

        //emit function
        this.$emit('onEditChiTietVatTu', this.gridVatTu.dataSources)
      }
    },
    gridVatTuCboLoaiKho(parent) {
      return function() {
        return {
          template: {
            template: `
                    <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                      <SelectExt style="width: 100%;" v-model="cboLoaiKho.value" :dataSource="cboLoaiKho.list" dataTextField="tenLk" dataValueField="loaiKhoTdId" @select="cboOnChanged"/>
                    </div>
                  `,
            data() {
              return {
                data: {},
                cboLoaiKho: {
                  value: null,
                  list: []
                }
              }
            },
            computed: {},
            async mounted() {
              this.cboLoaiKho.value = this.data.LOAIKHO
              this.cboLoaiKho.list = parent.cboLoaiKho.list
            },
            methods: {
              cboOnChanged(arg) {
                let record = parent.gridVatTu.dataSources.find((e) => e.ID_GEN == this.data.ID_GEN)
                record.LOAIKHO = this.cboLoaiKho.value
                record.ISUPDATE = '1'
                // // console.log('cboOnChanged')

                // let list = parent.$refs.gridChiTietVatTu.dataSource.map((e, idx) => {
                //   if (this.data.index == idx) {
                //     if (e.LOAIKHO != arg.id) {
                //       e.LOAIKHO = arg.id
                //       e.ISUPDATE = '1'
                //     }
                //   }
                //   return e
                // })
                // //update lai gridChiTietVatTu
                // parent.setLaiGridVatTu(list)
              }
            }
          }
        }
      }
    },
    gridVatTuCboChatLuong(parent) {
      return function() {
        return {
          template: {
            template: `
                    <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                      <SelectExt style="width: 100%;" v-model="cboChatLuong.value" :dataSource="cboChatLuong.list" dataTextField="chatLuong" dataValueField="chatLuongId" @select="cboOnChanged"/>
                    </div>
                  `,
            data() {
              return {
                data: {},
                cboChatLuong: {
                  value: null,
                  list: []
                }
              }
            },
            mounted() {
              // console.log('mounted')
              // console.log(parent)
              this.cboChatLuong.list = parent.cboChatLuong.list
              this.cboChatLuong.value = this.data.CHATLUONG_ID
            },
            methods: {
              async cboOnChanged(arg) {
                parent.gridVatTu.dataSources.find((e) => e.ID_GEN == this.data.ID_GEN).CHATLUONG_ID = this.cboChatLuong.value
                // parent.setLaiGridVatTu(list)
              }
            }
          }
        }
      }
    },
    gridVatTuCboMaKhoTD(parent) {
      return function() {
        return {
          template: {
            template: `
                    <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                      <SelectExt style="width: 100%;" v-model="cboMaKhoTD.value" :dataSource="cboMaKhoTD.list" dataTextField="MA_KHOTD" dataValueField="KHOTD_ID" @select="cboOnChanged"/>
                    </div>
                  `,
            data() {
              return {
                data: {},
                cboMaKhoTD: {
                  value: null,
                  list: []
                }
              }
            },
            async mounted() {
              const dataIndex = parent.$refs.gridChiTietVatTu.dataSource[this.data.index]
              this.cboMaKhoTD.list = parent.cboMaKhoTD.list.filter((e) => e.LOAIKHO_TD_ID == dataIndex.LOAIKHO)
              this.cboMaKhoTD.value = this.data.MAKHOTD
            },
            methods: {
              cboOnChanged(arg) {
                let record = parent.gridVatTu.dataSources.find((e) => e.ID_GEN == this.data.ID_GEN)
                record.MAKHOTD = this.cboMaKhoTD.value
                record.ISUPDATE = '1'
                // let list = parent.$refs.gridChiTietVatTu.dataSource.map((e, idx) => {
                //   if (this.data.index == idx) {
                //     if (e.MAKHOTD != arg.id) {
                //       e.MAKHOTD = arg.id
                //       e.ISUPDATE = '1'
                //     }
                //   }
                //   return e
                // })
                //update lai gridChiTietVatTu
                // parent.setLaiGridVatTu(list)
                // parent.tabLKMK.DSVatTu[parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)].MAKHOTD = event.id
              }
            }
          }
        }
      }
    },
    txtTK_DoiUng_Enter() {
      if (_.isNull(this.txtTKDUAll.value) || _.isEmpty(this.txtTKDUAll.value)) return this.$toast.error('Chưa nhập tài khoản đối ứng !')

      this.gridVatTu.dataSources = this.gridVatTu.dataSources.map((e) => {
        e.TK_DOIUNG = parseInt(this.txtTKDUAll.value)
        return e
      })
    },
    txtTK_Kho_Enter() {
      if (_.isNull(this.txtTK_Kho_All.value) || _.isEmpty(this.txtTK_Kho_All.value)) return this.$toast.error('Chưa nhập tài khoản kho !')

      this.gridVatTu.dataSources = this.gridVatTu.dataSources.map((e) => {
        e.TK_KHO = parseInt(this.txtTK_Kho_All.value)
        return e
      })

      // this.setLaiGridVatTu(list)
    }
  }
}
</script>
<style scoped></style>
