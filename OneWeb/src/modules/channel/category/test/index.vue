<template>
  <div id="nhapxuatvt_v2">
    <breadcrumb />
    <div id="nhapxuatvt_v2_content">
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnNhapMoi.visible" :class="{ disabledInput: !tsbtnNhapMoi.enabled }" @click="tsbtnNhapMoi_Clicked">
            <a> <span class="icon one-file-plus"></span>{{ tsbtnNhapMoi.text }}</a>
          </li>
          <li v-if="tsbtnGhiLai.visible" :class="{ disabledInput: !tsbtnGhiLai.enabled }" @click="tsbtnGhiLai_Clicked">
            <a> <span class="icon one-save"></span>{{ tsbtnGhiLai.text }}</a>
          </li>
          <li v-if="tsbtnHuy.visible" :class="{ disabledInput: !tsbtnHuy.enabled }" @click="tsbtnHuy_Clicked">
            <a> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>{{ tsbtnHuy.text }}</a>
          </li>
          <li v-if="tsbtnXoa.visible" :class="{ disabledInput: !tsbtnXoa.enabled }" @click="tsbtnXoa_Clicked">
            <a> <span class="icon one-trash"></span>{{ tsbtnXoa.text }}</a>
          </li>
          <li v-if="tsbtnFileDinhKem.visible" :class="{ disabledInput: !tsbtnFileDinhKem.enabled }" @click="tsbtnFileDinhKem_Clicked">
            <a> <span class="icon one-eye"></span>{{ tsbtnFileDinhKem.text }}</a>
          </li>
        </ul>
      </div>
      <div id="nhapxuatvt_v2_main" class="page-content py-1">
        <div class="box-form">
          <div class="legend-title">
            Thông tin chung
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Dịch vu</div>
                <div class="value">
                  <SelectExt ref="cboDichVu" :disabled="!cboDichVu.enabled" v-model="cboDichVu.value" :dataSource="cboDichVu.list" dataTextField="ten_dvvt" dataValueField="dichvuvt_id" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Loại hình</div>
                <div class="value">
                  <SelectExt ref="cboLoaiHinh" :disabled="!cboLoaiHinh.enabled" v-model="cboLoaiHinh.value" :dataSource="cboLoaiHinh.list" dataTextField="loaihinh_tb" dataValueField="loaitb_id" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Mã thuê bao(*)</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click.prevent="showPopupSearchContract">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <!-- <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" :donvi_id="0" :nhanvien_id="0" :donvi_dl_id="donvi_dl_id" /> -->
                    <input :disabled="!txtMaTB.enabled" type="text" class="form-control highlight" v-model="txtMaTB.value" v-on:keyup.enter="txtMaTB_KeyUpEnter" />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Tên thuê bao</div>
                <div class="value">
                  <input :disabled="!txtTenTB.enabled" type="text" class="form-control" v-model="txtTenTB.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Mã HĐ lắp mới</div>
                <div class="value">
                  <input :disabled="!txtMaHDLM.enabled" type="text" class="form-control" v-model="txtMaHDLM.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Tên kênh</div>
                <div class="value">
                  <input :disabled="!txtTenKenh.enabled" type="text" class="form-control" v-model="txtTenKenh.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Tên KH(*)</div>
                <div class="value">
                  <input :disabled="!txtTenKH.enabled" type="text" class="form-control" v-model="txtTenKH.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Phân loại KH(*)</div>
                <div class="value">
                  <input :disabled="!txtPhanLoaiKH.enabled" type="text" class="form-control" v-model="txtPhanLoaiKH.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">VSAT_Type(*)</div>
                <div class="value">
                  <input :disabled="!txtVSAT_Type.enabled" type="text" class="form-control" v-model="txtVSAT_Type.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">VSAT_Ser(*)</div>
                <div class="value">
                  <input :disabled="!txtVSAT_Ser.enabled" type="text" class="form-control" v-model="txtVSAT_Ser.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Sat_ID(*)</div>
                <div class="value">
                  <input :disabled="!txtSAT_ID.enabled" type="text" class="form-control" v-model="txtSAT_ID.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Bw_Type</div>
                <div class="value">
                  <input :disabled="!txtBw_Type.enabled" type="text" class="form-control" v-model="txtBw_Type.value" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách khai báo thông tin phân đoạn
          </div>
          <div class="row">
            <DataGrid
              ref="gridPhanDoan"
              panelDataHeight="285px"
              v-bind:columns="gridPhanDoan.header"
              v-bind:dataSource="gridPhanDoan.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }"
              :allowPaging="true"
            />
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách khai báo thông tin kênh
          </div>
          <div class="row">
            <DataGrid
              ref="gridThongTinKenh"
              panelDataHeight="285px"
              v-bind:columns="gridThongTinKenh.header"
              v-bind:dataSource="gridThongTinKenh.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }"
              :allowPaging="true"
            />
          </div>
        </div>
      </div>
    </div>
    <!-- <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#nhapxuatvt_v2"
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
        <frmChungTu_TBao v-if="ejsDialog.name == 'frmChungTu_TBao'" ref="frmChungTu_TBao" v-bind="frmChungTu_TBao.input" />
      </div>
    </ejs-dialog> -->
  </div>
</template>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import moment from 'moment'
import api from './api'
export default {
  components: {
    breadcrumb
  },
  data() {
    return {
      tsbtnNhapMoi: { enabled: true, visible: true, text: 'Nhập mới' },
      tsbtnGhiLai: { enabled: true, visible: true, text: 'Ghi lại' },
      tsbtnHuy: { enabled: true, visible: true, text: 'Hủy' },
      tsbtnXoa: { enabled: true, visible: true, text: 'Xóa' },
      tsbtnFileDinhKem: { enabled: true, visible: true, text: 'File đính kèm' },
      cboDichVu: { enabled: true, value: null, list: [] },
      cboLoaiHinh: { enabled: true, value: null, list: [] },
      txtMaTB: { enabled: true, value: null },
      txtTenTB: { enabled: true, value: null },
      txtMaTB: { enabled: true, value: null },
      txtMaHDLM: { enabled: true, value: null },
      txtTenKenh: { enabled: true, value: null },
      txtTenKH: { enabled: true, value: null },
      txtPhanLoaiKH: { enabled: true, value: null },
      txtVSAT_Type: { enabled: true, value: null },
      txtVSAT_Ser: { enabled: true, value: null },
      txtSAT_ID: { enabled: true, value: null },
      txtBw_Type: { enabled: true, value: null },
      gridPhanDoan: {
        header: [
          { width: 160, fieldName: 'SOPHIEU', headerText: 'TT Phân đoạn', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { width: 100, fieldName: 'LOAI_CT', headerText: 'Loại phân đoạn', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { width: 100, fieldName: 'TRANGTHAI', headerText: 'Người xử lý', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SOPHIEUCHA', headerText: 'Sắp xếp phân đoạn', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_CT', headerText: 'Trạng thái', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYNHAN_TT', headerText: 'Thao tác', textAlign: 'Right', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridThongTinKenh: {
        header: [
          { width: 160, fieldName: 'SOPHIEU', headerText: 'Dịch vụ', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { width: 100, fieldName: 'LOAI_CT', headerText: 'Loại hình', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { width: 100, fieldName: 'TRANGTHAI', headerText: 'Mã thuê bao', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SOPHIEUCHA', headerText: 'Tên thuê bao', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_CT', headerText: 'Tên khách hàng', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYNHAN_TT', headerText: 'Tỉnh đầu', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYNHAN_TT', headerText: 'Tỉnh cuối', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYNHAN_TT', headerText: 'Đơn vị chủ trì', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYNHAN_TT', headerText: 'Đơn vị phối hợp', textAlign: 'Right', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  watch: {
    async 'cboDichVu.value'(val) {
      // try {
      //   this.loading(true)
      // let kqLayThongTinChung = await api.layThongTinChung(this.axios, {})
      let kq_dsLoaiHinh = await api.dsLoaiHinh(this.axios, { dichvuvt_id: 25 })
      if (kq_dsLoaiHinh.data.data && kq_dsLoaiHinh.data.data.length) {
        this.cboLoaiHinh.list = kq_dsLoaiHinh.data.data
      } else {
        this.cboLoaiHinh.list = []
      }

      // } catch (e) {
      // } finally {
      //   this.loading(false)
      // }
    }
  },
  methods: {
    async tsbtnNhapMoi_Clicked() {},
    async tsbtnGhiLai_Clicked() {},
    async tsbtnHuy_Clicked() {},
    async tsbtnXoa_Clicked() {},
    async tsbtnFileDinhKem_Clicked() {},
    async txtMaTB_KeyUpEnter() {}
  },
  async mounted() {
    // try {
    //   this.loading(true)
    // let kqLayThongTinChung = await api.layThongTinChung(this.axios, {})
    let kq_dsDichVu = await api.dsDichVu(this.axios, {})
    if (kq_dsDichVu.data.data && kq_dsDichVu.data.data.length) {
      this.cboDichVu.list = kq_dsDichVu.data.data
    } else {
      this.cboDichVu.list = []
    }

    // } catch (e) {
    // } finally {
    //   this.loading(false)
    // }
  }
}
</script>

<style lang="scss" scoped>
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}
.disabledInput > a {
  color: #666 !important;
}
</style>
