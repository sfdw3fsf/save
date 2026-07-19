<template>
  <div id="tra-cuu-theo-so-lo-phieu-psis">
    <div>
      <breadcrumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnLayDS.visible" :class="{ disabledInput: !tsbtnLayDS.enabled }" @click="tsbtnLayDS_Clicked">
            <a> <span class="icon one-refresh"></span>{{ tsbtnLayDS.text }}</a>
          </li>
          <li v-if="tsbtnHuy.visible" :class="{ disabledInput: !tsbtnHuy.enabled }" @click="tsbtnHuy_Clicked">
            <a> <span class="icon one-remove-outline"></span>{{ tsbtnHuy.text }}</a>
          </li>
          <li v-if="tsbtnXuatFile.visible" :class="{ disabledInput: !tsbtnXuatFile.enabled }" @click="tsbtnXuatFile_Clicked">
            <a> <span class="icon one-excel"></span>{{ tsbtnXuatFile.text }}</a>
          </li>
        </ul>
      </div>
      <div class="popup-body page-content">
        <div class="row pb-2" style="min-height: 320px;">
          <div class="col-sm-6 col-12">
            <div class="box-form" style="height: 100%;">
              <div class="legend-title">
                Thông tin báo giá
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">Mã báo giá(*)</div>
                    <div class="value">
                      <input :disabled="!txtMaBaoGia.enabled" type="text" class="form-control" v-model="txtMaBaoGia.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">Tên báo giá(*)</div>
                    <div class="value">
                      <input :disabled="!txtTenBaoGia.enabled" type="text" class="form-control" v-model="txtTenBaoGia.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Hạn phê duyệt(*)</div>
                    <div class="value m-0 p-0">
                      <vue-date :disabled="!dpHanPheDuyet.enabled" v-model="dpHanPheDuyet.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Hạn lắp đặt</div>
                    <div class="value m-0 p-0">
                      <vue-date :disabled="!dpHanLapDat.enabled" v-model="dpHanLapDat.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Hiệu lực từ(*)</div>
                    <div class="value m-0 p-0">
                      <vue-date :disabled="!dpHieuLucTu.enabled" v-model="dpHanLapDat.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Đến ngày</div>
                    <div class="value m-0 p-0">
                      <vue-date :disabled="!dpHieuLucDen.enabled" v-model="dpHieuLucDen.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Loại báo giá(*)</div>
                    <div class="value">
                      <SelectExt ref="cboLoaiBaoGia" v-model="cboLoaiBaoGia.value" :dataSource="cboLoaiBaoGia.list" dataTextField="MA_CT" dataValueField="DUAN_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Dịch vụ nhóm(*)</div>
                    <div class="value">
                      <input :disabled="!txtDichVuNhom.enabled" type="text" class="form-control" v-model="txtDichVuNhom.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Nguồn báo giá</div>
                    <div class="value">
                      <input :disabled="!txtNguonBaoGia.enabled" type="text" class="form-control" v-model="txtNguonBaoGia.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Mã nguồn BG</div>
                    <div class="value">
                      <input :disabled="!txtMaNguonBG.enabled" type="text" class="form-control" v-model="txtMaNguonBG.value" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form" style="height: 100%;">
              <div class="legend-title">
                Thông tin khách hàng
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">Mã KH(*)</div>
                    <div class="value" style="display: flex;">
                      <button class="btn btn-outline-primary mr-2" @click.prevent="showPopupSearchContract">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input :disabled="!txtMaKH.enabled" type="text" class="form-control" v-model="txtMaKH.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">Tên KH(*)</div>
                    <div class="value">
                      <input :disabled="!txtTenKH.enabled" type="text" class="form-control" v-model="txtTenKH.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">Địa chỉ KH</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiKH.enabled" type="text" class="form-control" v-model="txtDiaChiKH.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Mã số thuế(*)</div>
                    <div class="value">
                      <input :disabled="!txtMaSoThue.enabled" type="text" class="form-control" v-model="txtMaSoThue.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Người liên hệ</div>
                    <div class="value">
                      <input :disabled="!txtNguoiLienHe.enabled" type="text" class="form-control" v-model="txtNguoiLienHe.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Số điện thoại</div>
                    <div class="value">
                      <input :disabled="!txtSoDienThoai.enabled" type="text" class="form-control" v-model="txtSoDienThoai.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Email</div>
                    <div class="value">
                      <input :disabled="!txtEmail.enabled" type="text" class="form-control" v-model="txtEmail.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w140">Ghi chú</div>
                    <div class="value">
                      <input :disabled="!txtGhiChu.enabled" type="text" class="form-control" v-model="txtGhiChu.value" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Chi tiết khoản mục
          </div>
          <div class="row">
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridKhoanMuc"
              panelDataHeight="285px"
              v-bind:columns="gridKhoanMuc.header"
              v-bind:dataSource="gridKhoanMuc.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @rowSelected="gridKhoanMuc_rowSelected"
              @selectedItemsChanged="gridKhoanMuc_selectedItemsChanged"
            />
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Thông tin đối thủ cạnh tranh
          </div>
          <div class="row">
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridDoiThu"
              panelDataHeight="150px"
              v-bind:columns="gridDoiThu.header"
              v-bind:dataSource="gridDoiThu.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @rowSelected="gridDoiThu_rowSelected"
              @selectedItemsChanged="gridDoiThu_selectedItemsChanged"
            />
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Thẩm định và phê duyệt
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w140">Ý kiến</div>
                <div class="value">
                  <textarea :disabled="!txtYKien.enabled" class="form-control" v-model="txtYKien.value" style="height: 50px;"></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách báo giá
          </div>
          <div class="row">
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridBaoGia"
              panelDataHeight="285px"
              v-bind:columns="gridBaoGia.header"
              v-bind:dataSource="gridBaoGia.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @rowSelected="gridBaoGia_rowSelected"
              @selectedItemsChanged="gridBaoGia_selectedItemsChanged"
            />
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#tra-cuu-theo-so-lo-phieu-psis"
      :enableResize="ejsDialog.config.enableResize"
      :resizeHandles="['All']"
      :header="ejsDialog.header"
      showCloseIcon="true"
      :width="ejsDialog.config.width"
      :height="ejsDialog.config.height"
      :minHeight="ejsDialog.config.minHeight"
      :allowDragging="ejsDialog.config.allowDragging"
      :position="ejsDialog.config.position"
      :animationSettings="ejsDialog.config.animationSettings"
      @beforeOpen="ejsDialogBeforeOpen"
      @beforeClose="ejsDialogBeforeClose"
    >
      <div v-if="ejsDialog.name" :key="uuidv4()" style="overflow-y: scroll;">
        <frmPhieuBaoHanhPSIS v-if="ejsDialog.name == 'frmPhieuBaoHanhPSIS'" ref="frmPhieuBaoHanhPSIS" v-bind="frmPhieuBaoHanhPSIS.input" />
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import Vue from 'vue'
import XLSX from 'xlsx'
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import moment from 'moment'
import api from './api'
export default {
  components: {
    breadcrumb,
    frmPhieuBaoHanhPSIS: () => import('@/modules/QLVT/PhieuBaoHanhPSIS')
  },
  data() {
    return {
      ejsDialog: {
        header: 'Tiêu đề',
        name: null,
        visible: false,
        config: {
          width: '95%',
          height: 'auto',
          minHeight: '150',
          enableResize: true,
          allowDragging: true,
          position: { X: 'center', Y: 'center' },
          animationSettings: { effect: 'Fade' }
        }
      },
      frmPhieuBaoHanhPSIS: { visible: false, input: {} },
      header: {
        title: 'Báo cáo kế hoạch tiếp cận khách hàng',
        list: []
      },
      tsbtnLayDS: { enabled: true, visible: true, text: 'Lấy DS' },
      tsbtnHuy: { enabled: true, visible: true, text: 'Hủy' },
      tsbtnXuatFile: { enabled: true, visible: true, text: 'Xuất file' },
      txtMaBaoGia: { enabled: true, value: '' },
      txtTenBaoGia: { enabled: true, value: '' },
      dpHanPheDuyet: { enabled: true, value: '' },
      dpHanLapDat: { enabled: true, value: '' },
      dpHieuLucTu: { enabled: true, value: '' },
      dpHieuLucDen: { enabled: true, value: '' },
      cboLoaiBaoGia: { enabled: true, value: '', list: [] },
      txtDichVuNhom: { enabled: true, value: '' },
      txtNguonBaoGia: { enabled: true, value: '' },
      txtMaNguonBG: { enabled: true, value: '' },
      txtMaKH: { enabled: true, value: '' },
      txtTenKH: { enabled: true, value: '' },
      txtDiaChiKH: { enabled: true, value: '' },
      txtMaSoThue: { enabled: true, value: '' },
      txtNguoiLienHe: { enabled: true, value: '' },
      txtSoDienThoai: { enabled: true, value: '' },
      txtEmail: { enabled: true, value: '' },
      txtGhiChu: { enabled: true, value: '' },
      txtYKien: { enabled: false, value: '' },
      gridKhoanMuc: {
        header: [
          { fieldName: 'a', headerText: 'Khoản mục', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'Hạng mục', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'a', headerText: 'Mô tả', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'ĐVT', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'a', headerText: 'Tiền thiết bị', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'Cước lắp đặt', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'a', headerText: 'Phí duy trì', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'Số lượng', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'a', headerText: 'CK tối đa', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'CK', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'a', headerText: 'Thành tiền', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'Tổng tiền', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'a', headerText: 'VAT', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'Tổng thanh toán', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'thao_tac', headerText: 'Thao tác', allowFiltering: false, allowSorting: false, template: this.gridKhoanMuc_template_ThaoTac(this) }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridDoiThu: {
        header: [
          { fieldName: 'stt', headerText: 'STT', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Tên đối thủ cạnh tranh', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Dịch vụ', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Loại hình', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Mô tả', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Báo giá VND', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'thao_tac', headerText: 'Thao tác', allowFiltering: false, allowSorting: false, template: this.gridKhoanMuc_template_ThaoTac(this) }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridBaoGia: {
        header: [
          { fieldName: 'stt', headerText: 'Mã báo giá', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Khách hàng', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Ngày báo giá', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Loại báo giá', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Đơn vị báo giá', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Hiệu lực báo giá', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Tình trạng báo giá', textAlign: 'Right', allowFiltering: false, allowSorting: false }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  methods: {
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        height: 'auto',
        position: { X: 'center', Y: 'center' }
      }
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    gridKhoanMuc_template_ThaoTac(parent) {
      return function () {
        return {
          template: {
            template: `<div style="display: flex; justify-content: center"><button>1</button><button>2</button></div>`,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    async gridKhoanMuc_selectedItemsChanged(e) {
      console.log('gridKhoanMuc_selectedItemsChanged')
      console.log(e)
      this.gridKhoanMuc.selected = e
    },
    async gridKhoanMuc_rowSelected(e) {
      this.gridKhoanMuc.value = null
      if(e.data) {
        this.gridKhoanMuc.value = e.data
      }
    },
    async gridDoiThu_selectedItemsChanged(e) {
      console.log('gridDoiThu_selectedItemsChanged')
      console.log(e)
      this.gridDoiThu.selected = e
    },
    async gridDoiThu_rowSelected(e) {
      this.gridDoiThu.value = null
      if(e.data) {
        this.gridDoiThu.value = e.data
      }
    },
    async gridBaoGia_selectedItemsChanged(e) {
      console.log('gridBaoGia_selectedItemsChanged')
      console.log(e)
      this.gridBaoGia.selected = e
    },
    async gridBaoGia_rowSelected(e) {
      this.gridBaoGia.value = null
      if(e.data) {
        this.gridBaoGia.value = e.data
      }
    },
    async txtSoLo_KeyUpEnter() {
      
    },
    async tsbtnLayDS_Clicked() {
      // this.ejsDialog.name = 'frmPhieuBaoHanhPSIS'
      // this.ejsDialog.header = 'Tra cứu'
      // this.$refs.ejsDialog.show()
    },
    async tsbtnHuy_Clicked() {
    },
    async tsbtnXuatFile_Clicked() {
      // this.exportExcelByCols(this.gridKeHoach.selected, this.gridKeHoach.header)
    },
    ConvertKeysToUpperCase(obj) {
      console.log(obj)
      var output = {}
      for (let i in obj) {
        if (Object.prototype.toString.apply(obj[i]) === '[object Object]') {
          output[i.toUpperCase()] = ConvertKeysToUpperCase(obj[i])
        } else if (Object.prototype.toString.apply(obj[i]) === '[object Array]') {
          output[i.toUpperCase()] = []
          output[i.toUpperCase()].push(ConvertKeysToUpperCase(obj[i][0]))
        } else {
          output[i.toUpperCase()] = obj[i]
        }
      }
      console.log(output)
      return output
    },
    exportExcelByCols(inputArray, arrayFields) {
      let exportData = []
      exportData = inputArray.map((e) => {
        let exportEntity = {}
        arrayFields.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
    }
  },
  async mounted() {
    this.gridBaoGia.list = [
      { a: '1', b: '2' },
      { a: '2', b: '2' },
      { a: '3', b: '2' }
    ]
  }
}
</script>

<style>
#tra-cuu-theo-so-lo-phieu-psis {
  width: 100%;
  height: 100%;
}
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}
.disabledInput > a {
  color: #666 !important;
}
</style>
