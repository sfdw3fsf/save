<template>
  <div>
    <div>
      <!-- <breadcrumb :header="header" /> -->
      <div class="list-actions-top">
        <ul class="list">
          <li @click="onClickTimKiem()">
            <a> <span class="icon one-search"></span>Tìm kiếm</a>
          </li>
          <li @click="tsbtnHoanThanh_Click()">
            <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Xác nhận</a>
          </li>
          <li @click="onClickHuy()">
            <a> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            Thông tin tra cứu đơn hàng
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Đơn vị
                </div>
                <div class="value">
                  <SelectExt ref="cboDonViNhan" locale="vi" v-model="cboDonViNhan.value" :dataSource="cboDonViNhan.list" dataTextField="TEN_DV" dataValueField="DONVI_ID" :allowFiltering="true" :filtering="filterDonVi" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w140">
                  Ngày đề nghị nhập
                </div>
                <div class="value">
                  <div class="input-icon-right">
                    <vue-date v-model="dtpTuNgay.value" :format="dateConfig.formatDay" type="datetime" />
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w140">
                  Đến ngày
                </div>
                <div class="value">
                  <div class="input-icon-right">
                    <vue-date v-model="dtpDenNgay.value" :format="dateConfig.formatDay" type="datetime" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách đơn hàng</div>
          <div class="table-content">
            <DataGrid ref="gridDonHang" :columns="gridDonHang.header" :dataSource="gridDonHang.list" :showFilter="true" dataKeyField="DONHANG_ID" :allowPaging="true" :enablePagingServer="false" @selectedRowChanged="gridViewDonHang_FocusedRowChanged" />
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách vật tư của đơn hàng</div>
          <div class="table-content">
            <!-- dataKeyField="ID_GEN" -->
            <DataGrid
              ref="gridVatTu"
              dataKeyField="ID_GEN"
              :columns="gridVatTuHeader"
              :dataSource="gridVatTu.list"
              :showFilter="true"
              :enabledSelectFirstRow="false"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              :editSettings="{ allowEditing: true, mode: 'Batch' }"
              :selectionSettings="{ enableToggle: false, checkboxOnly: true, persistSelection: true }"
              @cellSaved="gridVatTuCellSaved"
              @rowSelected="gridVatTuSelectedRowChanged"
              @selectedItemsChanged="gridVatTuSelectedItemsChanged"
              @beforeBatchSave="beforeBatchSave"
            />

            <!-- @selectedRowChanged="gridVatTuSelectedRowChanged" -->
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog :isModal="true" :enableResize="true" :resizeHandles="['All']" :visible="false" ref="frmChiaCuonCap" header="Chia cuộn cáp" showCloseIcon="true" width="98%" allowDragging="true" @close="frmChiaCuonCap.visible = false">
      <div>
        <frmChiaCuonCap v-if="frmChiaCuonCap.visible" v-bind="frmChiaCuonCap.input" @onBtnXacNhanClicked="frmChiaCuonCap_onBtnXacNhanClicked" />
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import Vue from 'vue'
import { GridPlugin, Group, Page, Filter, Sort, Resize } from '@syncfusion/ej2-vue-grids'
import frmChiaCuonCap from '../frmChiaCuonCap'
import { Query, Predicate } from '@syncfusion/ej2-data'
import api from './api'

Vue.use(GridPlugin)

export default {
  components: { frmChiaCuonCap, breadcrumb },
  emits: ['onBtnHoanThanhClicked'],
  props: {
    dt_vattu: {
      type: [Array],
      default() {
        return []
      },
      require: true
    },
    dt_cuoncap: {
      type: [Array],
      default() {
        return []
      },
      require: true
    }
  },
  data() {
    return {
      localLoading: 0,
      header: {
        title: 'ĐƠN HÀNG ĐÃ NGHIỆM THU',
        list: [
          {
            name: 'Quản lí vật tư',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Đơn hàng đã nghiệm thu',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      colChiaSL: { visible: true },
      colSLXuat: { visible: true },
      cboDonViNhan: { enabled: true, list: [], value: null },
      dtpTuNgay: { enabled: true, value: null },
      dtpDenNgay: { enabled: true, value: null },
      gridDonHang: {
        header: [
          { fieldName: 'MA_HD', headerText: 'Mã hợp đồng', allowFiltering: true },
          { fieldName: 'MA_DH', headerText: 'Mã đơn hàng', allowFiltering: true },
          { fieldName: 'TEN_KHO', headerText: 'Nhà cung cấp', allowFiltering: true },
          { fieldName: 'NGAY_NH', headerText: 'Ngày nhận hàng', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'NGAY_NK', headerText: 'Ngày nhập kho', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'NGAY_NT', headerText: 'Ngày nghiệm thu', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      gridVatTu: {
        header: [
          { fieldName: 'ID_GEN', headerText: '', visible: false, isPrimaryKey: true },
          { width: 150, fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true, allowSorting: true, freeze: 'Left' },
          { width: 200, fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, allowSorting: true, freeze: 'Left' },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true, allowSorting: true },
          { fieldName: 'SL_NT', headerText: 'Số lượng', allowFiltering: true, allowSorting: true },
          { fieldName: 'SL_KD', headerText: 'SL KD', allowFiltering: true, allowSorting: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, allowSorting: true },
          { fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true, allowSorting: true },
          { fieldName: 'LOAI_TBI', headerText: 'Loại TB', allowFiltering: true, allowSorting: true }
          // { fieldName: '', headerText: 'SL xuất', allowFiltering: true, type: 'number', format: 'N3' }
          // { allowEditing: true, fieldName: 'SL_XUAT', headerText: 'SL xuất', textAlign: 'Right', allowFiltering: true, type: 'number', editType: 'numericedit', format: 'N0' }
        ],
        list: [],
        value: null,
        selected: []
      },
      dtVatTu: [],
      params: {
        tuNgay: new Date('2017/02/05'),
        denNgay: new Date(),
        donViId: ''
      },
      frmChiaCuonCap: {
        visible: false,
        input: {
          sophieu: 0,
          vchungtu_id: 0,
          vma_md: 0,
          dt_vt: null
        }
      },
      thongTinNguoiDung: {
        donViId: this.$root.token.getDonViID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        phanVungId: this.$root.token.getPhanVungID()
      },
      dateConfig: {
        default: new Date(),
        formatDay: 'DD/MM/YYYY',
        waterMark: 'Chọn ngày'
      },

      filterOptions: {
        enableCaseSensitivity: false,
        mode: 'Immediate',
        ignoreAccent: true
      },
      taskParent: '',
      dt: [],
      XacNhan: false,
      ma_hd: '',
      ngay_ky: '',
      ngay_nhap: '',
      so_hd: '',
      vdonhang_id: '',
      kho_id: 0,
      quyen: false,
      is_cc: false,
      rowFocus: {},
      filterDonVi: function(e) {
        let query = new Query()
        let predicate

        if (e.text !== '') {
          predicate = new Predicate('TEN_DV', 'contains', e.text.trim(), true)
          query.where(predicate)
        }
        e.updateData(this.dataSource, query)
      }
    }
  },

  computed: {
    // ...mapState('tracuudonhangconhan', statePropertyName),
    // ...mapGetters('tracuudonhangconhan', getterName),
    gridVatTuHeader() {
      console.log('gridVatTuColumn')
      let header = this.gridVatTu.header
      if (this.colSLXuat.visible) header.push({ width: 140, fieldName: 'SL_XUAT', headerText: 'SL xuất', allowEditing: true, type: 'number', editType: 'numericedit', freeze: 'Right' })
      if (this.colChiaSL.visible) header.push({ width: 140, fieldName: 'CHIA_SL', headerText: 'Chia SL', allowEditing: true, type: 'number', editType: 'numericedit', freeze: 'Right' })
      // header.push({ allowEditing: true, fieldName: 'SL_XUAT', headerText: 'SL xuất', textAlign: 'Right', allowFiltering: true, type: 'number', editType: 'numericedit', format: 'N0' })
      console.log(header)
      return header
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  async mounted() {
    console.log('frmDonHangTT mounted')
    await this.loadDataDonHang()
  },
  methods: {
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    /**
     * @see: private void frmDonHangTT_Load(object sender, EventArgs e)
     */
    async loadDataDonHang() {
      this.gridVatTu.list = [] // them eo dung
      this.dtpTuNgay.value = moment(new Date().setDate(new Date().getDate() - 365)).format('DD/MM/YYYY')
      this.dtpDenNgay.value = moment().format('DD/MM/YYYY')
      this.cboDonViNhan.list = await this.callApiWrapper(api.lay_ds_donvi_kho, { nhanvien_id: this.$root.token.getNhanVienID() })
      console.log('this.cboDonViNhan.list')
      console.log(this.cboDonViNhan.list)

      if (this.cboDonViNhan.list.length > 0) this.cboDonViNhan.value = this.cboDonViNhan.list[0].DONVI_ID
      await this.Lay_ds_donhang()
      if (this.$root.token.getMaTinh() == 'HCM') this.quyen = true
      if (!this.quyen) this.colChiaSL.visible = false
    },
    /**
     * @see: private void Lay_ds_donhang()
     */
    async Lay_ds_donhang() {
      // try {
      if (this.cboDonViNhan.value == null) return
      let DSDonHang = await this.callApiWrapper(api.lay_ds_don_hang, {
        donViId: this.cboDonViNhan.value,
        tuNgay: this.dtpTuNgay.value,
        denNgay: this.dtpDenNgay.value
      })

      DSDonHang.forEach((e) => {
        e.NGAY_NH = e.NGAY_NH.replaceAll('-', '/').slice(0, 10)
        e.NGAY_NK = e.NGAY_NK.replaceAll('-', '/')
        e.NGAY_NT = e.NGAY_NT.replaceAll('-', '/')
      })
      this.gridDonHang.list = DSDonHang
      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    /**
     * @see: private void Lay_ds_vattu()
     */
    async Lay_ds_vattu() {
      // try {
      this.dtVatTu = await this.callApiWrapper(api.lay_ds_vattu_donhang, { donhang_id: this.vdonhang_id })
      if (this.dtVatTu.length > 0 && this.dtVatTu[0].SL_XUAT != null) {
        // gridViewVatTu.Columns.ColumnByFieldName("SL_KD").Visible = true;
        this.colSLXuat.visible = true
        this.dtVatTu = this.dtVatTu.map((e) => {
          e.SL_XUAT = '0'
          // if(this.colChiaSL.visible) e.CHIA_SL = 0
          return e
        })
      } else {
        this.colSLXuat.Visible = false
        // gridViewVatTu.Columns.ColumnByFieldName("SL_KD").Visible = false;
      }
      this.gridVatTu.list = this.dtVatTu.map((e) => {
        e.ID_GEN = this.uuidv4()
        return e
      })

      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    async onClickTimKiem() {
      try {
        this.loading(true)
        await this.Lay_ds_donhang()
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async tsbtnHoanThanh_Click() {
      console.log('tsbtnHoanThanh_Click')
      // try {

      // let a = this.$refs.gridVatTu.grid.getSelectedRecords()
      let a = this.gridVatTu.selected

      //check SL = 0
      let listSL = a.filter((e) => e.SL_XUAT == 0)
      if (listSL.length > 0) {
        this.$toast.warning(`${listSL.length} vật tư có SL = 0 không thể xuất\n${listSL.map((e) => `- Tên: ${e.TEN_VT}\n  Mã: ${e.MA_VT}`).join('\n')} `)
        return
      }
      //  this.gridVatTu.selected
      console.log(a)
      if (!a.length) {
        this.$toast.error('Chưa chọn vật tư nào!')
        this.XacNhan = false
        return
      }
      if (this.quyen) {
        let cuoncap = a.filter((e) => e.LOAITBI_ID != 2)
        console.log('cuoncap')
        console.log(cuoncap)
        if (cuoncap.length > 0) {
          let isBoxConfirm = false
          await this.$bvModal
            .msgBoxConfirm('Có thiết bị không phải Serial. Bạn có muốn chia cuộn cáp không ???', {
              title: 'Thông báo',
              centered: true,
              size: 'md'
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true
              } else {
                return
              }
            })

          if (isBoxConfirm) {
            let check_cuoncap = cuoncap.map((e) => e)
            for (const i in check_cuoncap) {
              if (check_cuoncap[i].CHIA_SL == 0 || check_cuoncap[i].CHIA_SL === undefined) {
                this.$toast.error('Bạn chưa chia số lượng - mã vật tư [' + check_cuoncap[i].MA_VT + ']')
                return
              }
            }
            this.$refs.gridVatTu.grid.clearSelection()
            this.frmChiaCuonCap.input = { dt_vattu: a, dt_cuoncap: check_cuoncap }
            this.frmChiaCuonCap.visible = true
            this.$refs.frmChiaCuonCap.show()
            return
            // f.dt_vattugoc
          }
        }
      }
      this.dt = a
      this.XacNhan = true
      this.frmChiaCuonCap_onBtnXacNhanClicked({
        dt: a,
        XacNhan: true
      })
      // this.Close()
      // } catch (e) {
      //   this.$toast.error(e)
      // } finally {
      //   this.loading(false)
      // }
    },
    async Close() {
      if (this.taskParent != '') {
        this.$parent.hide()
      }
    },
    async onClickHuy() {
      // try {
      //   if (this.rowFocus == {}) {
      //     return
      //   }
      //   this.loading(true)
      //   await this.selectDonHang(this.rowFocus)
      //   this.XacNhan = false
      //   this.loading(false)
      // } catch (e) {
      //   this.$toast.error(e.data.message)
      // } finally {
      //   this.loading(false)
      // }
      await this.loadDataDonHang()
    },
    async gridVatTuCellSaved(arg) {
      console.log('gridVatTuCellSaved')
      // console.log(e)
      // console.log(this.gridVatTu.list)
      let changes = this.$refs.gridVatTu.getBatchChanges()
      console.log(changes)
      // let row = changes.changedRecords.find((e) => e.ID_GEN == this.gridVatTu.value.ID_GEN)
      // this.gridVatTu.list = this.gridVatTu.list.map((e) => {
      //   if (e.ID_GEN == row.ID_GEN) return row
      //   else return e
      // })
      // let index = this.gridVatTu.list.findIndex((e) => e.ID_GEN == row.ID_GEN)
      // console.log('index')
      // console.log(index)
      // this.gridVatTu.list[index] = row
      // console.log('row')
      // console.log(row)
      // console.log(changes)
      // changes.changedRecords = this.gridVatTu.list.map(e => {
      //   e.CHIA_SL = e.CHIA_SL.toString()
      //   e.SL_XUAT = e.SL_XUAT.toString()
      //   return e
      // })
      // console.log(changes)
      // console.log(this.$refs.gridVatTu)
      // this.$refs.gridVatTu.batchUpdate({changedRecords: [row]})
      // this.$refs.gridVatTu.updateRow(index, row);
      this.$refs.gridVatTu.batchUpdate(changes)
      this.$refs.gridVatTu.grid.refreshColumns()

      // console.log(this.gridVatTu.list)
    },
    /**
     * @see: private void gridViewDonHang_FocusedRowChanged(object sender, DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs e)
     */
    async gridViewDonHang_FocusedRowChanged(e) {
      console.log('gridViewDonHang_FocusedRowChanged')
      console.log(e)
      this.gridDonHang.value = e
      // try {
      if (e) {
        this.vdonhang_id = this.gridDonHang.value.DONHANG_ID
        this.so_hd = this.gridDonHang.value.SO_HD
        this.ma_hd = this.gridDonHang.value.MA_HD
        this.ngay_ky = this.gridDonHang.value.NGAY_KY
        this.ngay_nhap = this.gridDonHang.value.NGAY_NK
        this.kho_id = this.gridDonHang.value.KHO_ID
        await this.Lay_ds_vattu()
      } else {
        this.gridVatTu.list = []
      }
      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    async gridVatTuSelectedRowChanged(e) {
      console.log('gridVatTuSelectedRowChanged')
      console.log(e)

      console.log(this.gridVatTu.value)
      if (this.colSLXuat.visible && (this.gridVatTu.value == null || (this.gridVatTu.value != null && this.gridVatTu.value.ID_GEN != e.data.ID_GEN))) {
        this.gridVatTu.value = e.data
        let row = this.gridVatTu.value
        if (row.SL_XUAT == 0) {
          row.SL_XUAT = row.SL_KD
          // let changes = this.$refs.gridVatTu.getBatchChanges()
          // changes.changedRecords = [row]

          // console.log(this.$refs.gridVatTu.grid)
          let primaryKeys = this.$refs.gridVatTu.grid.getPrimaryKeyFieldNames()
          // console.log(changes)
          // this.$refs.gridVatTu.batchUpdate(changes)
          console.log(row)
          this.$refs.gridVatTu.grid.setRowData(primaryKeys[0], row)
          this.$refs.gridVatTu.grid.refreshColumns()
        }
      }
    },
    async gridVatTuSelectedItemsChanged(e) {
      this.gridVatTu.selected = this.gridVatTu.list.filter((x) => e.includes(x.ID_GEN))
      console.log('gridVatTuSelectedItemsChanged')
      console.log(this.gridVatTu.selected)
      let edited = false
      if (e.length > 0) {
        this.gridVatTu.list.map((x) => {
          let row = x
          if (e.includes(x.ID_GEN)) {
            if (x.SL_KD != 0 && x.SL_XUAT == 0) {
              edited = true
              row.SL_XUAT = row.SL_KD
              let primaryKeys = this.$refs.gridVatTu.grid.getPrimaryKeyFieldNames()
              this.$refs.gridVatTu.grid.setRowData(primaryKeys[0], row)
            }
          }
          return x
        })
        if (edited == true) this.$refs.gridVatTu.grid.refreshColumns()

        // if (this.colSLXuat.visible && (this.gridVatTu.value == null || (this.gridVatTu.value != null && this.gridVatTu.value.ID_GEN != e.data.ID_GEN))) {
        //   this.gridVatTu.value = e.data
        //   let row = this.gridVatTu.value
        //   if (row.SL_XUAT == 0) {
        //     row.SL_XUAT = row.SL_KD
        //     // let changes = this.$refs.gridVatTu.getBatchChanges()
        //     // changes.changedRecords = [row]

        //     // console.log(this.$refs.gridVatTu.grid)
        //     let primaryKeys = this.$refs.gridVatTu.grid.getPrimaryKeyFieldNames()
        //     // console.log(changes)
        //     // this.$refs.gridVatTu.batchUpdate(changes)
        //     console.log(row)
        //     this.$refs.gridVatTu.grid.setRowData(primaryKeys[0], row)
        //     this.$refs.gridVatTu.grid.refreshColumns()
        //   }
        // }
      }
      // console.log('gridVatTuSelectedItemsChanged')
      // console.log(e)
      // console.log(this.$refs.gridVatTu.grid.getSelectedRecords())
      // console.log(this.gridVatTu.list)
      // let selectedData = Array.isArray(e) ? e : [e]
      // this.gridVatTu.selected = this.gridVatTu.list.filter((e) => selectedData.includes(e.GEN_ID))
    },
    beforeBatchSave(e) {
      console.log('beforeBatchSave')
      console.log(e)
    },
    //helper function
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    frmChiaCuonCap_onBtnXacNhanClicked(data) {
      console.log('frmChiaCuonCap_onBtnXacNhanClicked')
      console.log(data)
      let frmDonHangTTData = {}
      frmDonHangTTData.kho_id = this.kho_id
      frmDonHangTTData.XacNhan = true
      this.$refs.frmChiaCuonCap.hide()
      if (data.is_cc) {
        frmDonHangTTData.dt = data.chia
        frmDonHangTTData.is_cc = data.is_cc
      } else {
        frmDonHangTTData.dt = this.dt
      }
      this.$emit('onBtnXacNhanClicked', frmDonHangTTData)
    }
  }
}
</script>
<style scoped></style>
