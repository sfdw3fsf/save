<template>
  <div style="height: 580px">
    <!-- <div class="popup-header">
        <div class="close -ap icon-close" @click="$bvModal.hide('popupSPDA')" data-dismiss="modal"></div>
      </div> -->
    <div class="list-actions-top" style="position: unset;">
      <ul class="list" style="position: unset;">
        <li @click="v_btnRef_Click">
          <a> <span class="icon one-search"></span>Làm mới </a>
        </li>
        <li @click="btnXacNhanOnClicked">
          <a> <span class="icon one-search"></span>Xác nhận </a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
      <div class="nav tabs tab-over">
        <a href="#dsda" data-toggle="tab" :class="{ active: tabSPDA == 1 }" v-if="layoutControlDuAn.visible">
          Danh sách dự án
        </a>
        <a href="#vtqlts" data-toggle="tab" :class="{ active: tabSPDA == 2 }" v-if="layoutControlDieuChinh.visible">
          Danh sách vật tư QLTS
        </a>
        <a href="#bbbg" data-toggle="tab" :class="{ active: tabSPDA == 3 }" v-if="layoutControlBBBG.visible">
          Biên bản bàn giao
        </a>
        <a href="#dsvt" data-toggle="tab" :class="{ active: tabSPDA == 4 }" v-if="layoutControlDS_Vattu.visible">
          Danh sách vật tư
        </a>
      </div>

      <div class="box-form tab-content">
        <div class="info-row col-sm-6 col-12" v-if="layoutMaPhieu.visible">
          <div class="key">
            Mã phiếu
          </div>
          <!-- <div class="value">
            <div class="input-group inline">
              <div class="input-text">
                <div class="select-custom">
                  <SelectExt v-model="cboMaPhieu.value" :dataSource="cboMaPhieu.list" dataTextField="MaPhieu" dataValueField="MaPhieu"  @change="cboMaPhieu_EditValueChanged" />
                </div>
              </div>
              <div class="input-addon">
                <button class="btn" @click="cboMaPhieu_ButtonClick">
                  <span class="nc-icon-glyph ui-1_bold-add f14"></span>
                </button>
              </div>
            </div>
          </div> -->
          <div class="value" style="display: flex">
            <SelectExt style="flex-grow: 1" :disabled="!cboMaPhieu.enabled" ref="cboMaPhieu" v-model="cboMaPhieu.value" :dataSource="cboMaPhieu.list" dataTextField="MaPhieu" dataValueField="MaPhieu" @select="cboMaPhieu_EditValueChanged" />
            <button class="btn" @click="cboMaPhieu_ButtonClick('1')">
              <span class="nc-icon-glyph ui-1_bold-add f14"></span>
            </button>
          </div>
        </div>
        <!-- TAB DS dự án -->
        <article class="tab-pane" :class="{ active: tabSPDA == 1 }" id="dsda" v-if="layoutControlDuAn.visible">
          <div class="list-actions-top" style="padding-right: 0px !important; padding-top: 15px !important;">
            <!-- <div class="col-sm-6 col-12"></div> -->
            <div class="col-sm-6 col-12">
              <div class="pull-right">
                <button class="btn btn-second pad5" @click="v_btnRef_Click">
                  <span class="one-reload f16"></span>
                </button>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">
                  Mã DV SD
                </div>
                <div class="value">
                  <ComboboxGrid v-if="cboMa_DV_SD.enabled" ref="cboMa_DV_SD" :columns="cboMa_DV_SD.header" :dataSource="cboMa_DV_SD.list" v-model.trim="cboMa_DV_SD.value" textField="TEN_DV" valueField="MA_DV_SD" :allowPaging="true" :enabledSelectFirstRow="false" />
                  <SelectExt v-else :disabled="!cboMa_DV_SD.enabled" ref="cboTinh" v-model="cboTinh.value" :dataSource="cboTinh.list" dataTextField="tentinh" dataValueField="tentat_ccbs" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Mã CSHT</div>
                <div class="value">
                  <div class="input-more-button -right">
                    <button class="btn btn-main" @click="cboIMS_CSHT.value = null">
                      <span class="-ap icon-close"></span>
                    </button>
                    <div>
                      <div @click="maCSHT.visible = !maCSHT.visible && cboIMS_CSHT.enabled">
                        <input class="form-control" @click="maCSHT.visible = !maCSHT.visible" v-if="cboIMS_CSHT.value" :disabled="true" v-model="cboIMS_CSHT.value" />
                        <input class="form-control" v-else :disabled="true" />
                      </div>
                      <div v-show="maCSHT.visible" class="box-form csht-popup">
                        <div class="info-row">
                          <div class="key w100">
                            Tìm mã CSHT
                          </div>
                          <div class="value">
                            <div style="display:flex;">
                              <SelectExt style="width: 200px; margin: 0 15px 0 0;" ref="cboTinh" v-model="cboTinh.value" :dataSource="cboTinh.list" dataTextField="tentinh" dataValueField="tentat_ccbs" />
                              <input class="form-control" style="flex-grow: 9" v-model="maCSHT.code" @keydown.enter="searchCsht" />
                            </div>
                          </div>
                        </div>
                        <div class="info-row">
                          <div class="key w100">
                            Tìm tên CSHT
                          </div>
                          <div class="value">
                            <input class="form-control" v-model="maCSHT.name" @keydown.enter="searchCsht" />
                          </div>
                        </div>
                        <div style="display: flex; margin: 10px 0;">
                          <div v-show="cboIMS_CSHT.list.length" style="width: 25%; padding: 0 0 20px 0; display: flex; align-items: center;">
                            <button class="btn btn-second" @click="cboIMS_CSHT.form.page - 1 < 1 ? 1 : cboIMS_CSHT.form.page--"><</button>
                            <div style="margin: 0 15px" type="number" min="1" max="1000" v-model="cboIMS_CSHT.form.page">Trang {{ cboIMS_CSHT.form.page }}</div>
                            <button class="btn btn-second" @click="cboIMS_CSHT.form.page++">></button>
                          </div>
                          <div style="flex-grow: 1"></div>
                          <button class="btn btn-main" style="float: right; background: let(--bs-blue); color: white; height: 35px; padding: 0 30px" @click="searchCsht">
                            Tìm kiếm
                          </button>
                        </div>
                        <DataGrid ref="cboIMS_CSHT" dataKeyField="ProjectID" :columns="cboIMS_CSHT.header" :dataSource="cboIMS_CSHT.list" :showFilter="false" :enabledSelectFirstRow="false" />
                      </div>
                    </div>
                  </div>
                </div>
                <!-- <div class="key w100">
                    Mã CSHT
                  </div>
                  <div class="value">
                    <ComboboxGrid ref="cboIMS_CSHT" :columns="cboIMS_CSHT.header" :dataSource="cboIMS_CSHT.list" v-model.trim="cboIMS_CSHT.value" textField="TEN_DV" valueField="ProjectID" :gridStyle="{right: 0}"/>
                  </div> -->
                <!-- <div class="input-group" style="padding-left: 5px;">
                        <div class="input-text">
                          <input type="text" class="form-control" v-model="cboIMS_CSHT" :disabled="!cboIMS_CSHT.enabled" @click="openPopupCSHT" />
                        </div>
                        <div class="input-addon">
                          <button class="btn" :disabled="!cboHD_XuatIMS.list.enabled" @click="clearCSHT">
                            <span class="nc-icon-glyph ui-1_bold-remove f14"></span>
                          </button>
                        </div>
                      </div> -->
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">
                  Mã DV QL
                </div>
                <div class="value">
                  <ComboboxGrid v-if="cboMa_DV_QL.enabled" ref="cboMa_DV_QL" :columns="cboMa_DV_QL.header" :dataSource="cboMa_DV_QL.list" v-model.trim="cboMa_DV_QL.value" textField="TEN_DV" valueField="MA_DV_QL" :allowPaging="true" :enabledSelectFirstRow="false" />
                  <SelectExt v-else :disabled="!cboMa_DV_QL.enabled" ref="cboTinh" v-model="cboTinh.value" :dataSource="cboTinh.list" dataTextField="tentinh" dataValueField="tentat_ccbs" />
                </div>
              </div>
            </div>
            <div class=" col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">
                  HĐ PO Đích
                </div>
                <div class="value">
                  <ComboboxGrid
                    v-if="cboHD_XuatIMS.enabled"
                    :enabled="cboHD_XuatIMS.enabled"
                    ref="cboHD_XuatIMS"
                    :columns="cboHD_XuatIMS.header"
                    :dataSource="cboHD_XuatIMS.list"
                    v-model.trim="cboHD_XuatIMS.value"
                    textField="ContractNumber"
                    valueField="ContractInfoID"
                    :gridStyle="{ right: 0 }"
                    :allowPaging="true"
                    :enabledSelectFirstRow="false"
                  />
                  <SelectExt v-else :disabled="!cboHD_XuatIMS.enabled" ref="cboTinh" v-model="cboTinh.value" :dataSource="cboTinh.list" dataTextField="tentinh" dataValueField="tentat_ccbs" />
                </div>
              </div>
            </div>
            <div class="table-content col-sm-12 col-12">
              <!-- @rowSelected="grcMa_PDARowSelected" -->
              <DataGrid ref="grcMa_PDA" dataKeyField="ProjectID" :columns="grcMa_PDA.header" :dataSource="grcMa_PDA.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="false" :enabledSelectFirstRow="false" />
            </div>
          </div>
        </article>

        <!-- TAB Vật tưu quản lý tài sản -->
        <article class="tab-pane" :class="{ active: tabSPDA == 2 }" id="vtqlts" v-if="layoutControlDieuChinh.visible">
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="box-form">
                <div class="row">
                  <div class=" col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">
                        Tìm kiếm theo
                      </div>
                      <div class="value">
                        <ejs-dropdownlist ref="cboLocDCDonVi" class="form-control" locale="vi" v-model="cboLocDCDonVi.value" :dataSource="cboLocDCDonVi.list" :fields="{ value: 'LOCDV_ID', text: 'LOC_DV' }" @change="cboLocDCDonVi_EditValueChanged"> </ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                  <div class=" col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w100">
                        Loại CT
                      </div>
                      <div class="value">
                        <ejs-dropdownlist ref="cboLocDCLoaiCT" class="form-control" locale="vi" v-model="cboLocDCLoaiCT.value" :dataSource="cboLocDCLoaiCT.list" :fields="{ value: 'LOAICT_ID', text: 'LOAI_CT' }" @change="cboLocDCLoaiCT_EditValueChanged"> </ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                  <div class="value w30 nowrap">
                    <button ref="btnCapNhatDieuChinh" class="btn btn-second" @click="btnCapNhatDieuChinh_Click"><span class="icon one-key normal"></span> Cập nhật vật tư</button>
                  </div>
                </div>

                <div class="table-content">
                  <DataGrid :columns="gridDieuChinhVT.header" ref="gridDieuChinhVT" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="true" :enabledSelectFirstRow="false" :dataSource="gridDieuChinhVT.list" @selectedItemsChanged="DieuChinhVT_CheckBox" />
                </div>
              </div>
            </div>
          </div>
        </article>

        <!-- TAB Biên bản bàn giao -->
        <article class="tab-pane" :class="{ active: tabSPDA == 3 }" id="bbbg" v-if="layoutControlBBBG.visible">
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="box-form">
                <div class="row">
                  <div class=" col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key">
                        Dự án
                      </div>
                      <div class="value">
                        <ComboboxGrid :enabled="cboDuanIMS.enabled" ref="cboDuanIMS" :columns="cboDuanIMS.header" :dataSource="cboDuanIMS.list" v-model.trim="cboDuanIMS.value" textField="ProjectName" valueField="ProjectID" :allowPaging="true" :enabledSelectFirstRow="false" />
                      </div>
                    </div>
                  </div>
                  <div class=" col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key">
                        HĐ PO
                      </div>
                      <div class="value">
                        <ComboboxGrid
                          :enabled="cboHDPOIMS.enabled"
                          ref="cboHDPOIMS"
                          :columns="cboHDPOIMS.header"
                          :dataSource="cboHDPOIMS.list"
                          v-model.trim="cboHDPOIMS.value"
                          textField="ContractName"
                          valueField="ContractInfoID"
                          :gridStyle="{ right: 0 }"
                          :allowPaging="true"
                          :enabledSelectFirstRow="false"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="table-content col-sm-6 col-12">
                  <DataGrid :columns="gridBienBanIMS.header" ref="gridBienBanIMS" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :dataSource="gridBienBanIMS.list" />
                </div>
              </div>
            </div>
          </div>
        </article>

        <!-- TAB Danh sách vật tư -->
        <article class="tab-pane" :class="{ active: tabSPDA == 4 }" id="dsvt" v-if="layoutControlDS_Vattu.visible">
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="box-form">
                <div class="info-row" style="text-align: right; padding-top: 5px; padding-left: 5px;">
                  <div class="value w30 nowrap">
                    <button ref="btnCapNhatVattu_ThuHoi" class="btn btn-second" @click="btnCapNhatVattu_ThuHoi_Click"><span class="icon one-key normal"></span> Cập nhật vật tư</button>
                  </div>
                </div>

                <div class="table-content">
                  <DataGrid :columns="gridDS_VT_THUHOI.header" ref="gridDS_VT_THUHOI" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="true" :enabledSelectFirstRow="false" @selectedItemsChanged="" :dataSource="gridDS_VT_THUHOI.list" />
                  <!-- {fieldName: 'VATTU_ID',  visible:false, isPrimaryKey:true}    
                                                dataKeyField="VATTU_ID"                         -->
                </div>
              </div>
            </div>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>
<script>
import { ChonBBBGTemplate, ChonPDATemplate, ChonCSHT_PDATemplate } from './components'
import api from './api'
export default {
  components: {},
  emits: ['grcMa_PDA_onDuAnClicked', 'onBtnXacNhanClickedListener', 'gridBienBanIMS_OnChonBBBG', 'btnCapNhatVattu_ThuHoi_Click'],
  props: {
    vnghiepvu_id: {
      type: [String, Number],
      default: 288
      // required: true
    },
    kieu_phieu_id: {
      type: [String, Number],
      default: null
      // required: true
    },
    mucdich_id: {
      type: [String, Number],
      default: null
      // required: true
    },
    tu_kho_id: {
      type: [String, Number],
      default: null
      // required: true
    },
    den_kho_id: {
      type: [String, Number],
      default: null
      // required: true
    },
    ma_pda: {
      type: [String, Number],
      default: null
      // required: true
    },
    kieugoi_id: {
      type: [String, Number],
      default: null
      // required: true
    },
    vinput_spda: {
      type: Object,
      default: () => {
        return {}
      },
      required: true
    },
    vma_md: {
      type: [String],
      default: () => {
        return {}
      },
      required: true
    }
  },
  data() {
    return {
      localLoading: 0,
      tabSPDA: 1,
      view: { valid: true },
      //
      maCSHT: { value: null, code: '', name: '', visible: false },
      // codeCSHT
      //
      layoutMaPhieu: { enabled: true, visible: false },
      layoutControlDuAn: { enabled: true, visible: false },
      layoutControlDS_Vattu: { enabled: true, visible: false },
      layoutControlDieuChinh: { enabled: true, visible: false },
      layoutControlBBBG: { enabled: true, visible: false },
      cboTinh: { enabled: true, list: [], value: null },
      cboMaPhieu: { enabled: true, header: [], value: null, list: [] },
      cboMa_DV_SD: { enabled: true, header: [{ fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true }], value: null, list: [] },
      cboMa_DV_QL: { enabled: true, header: [{ fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true }], value: null, list: [] },
      cboLocDCDonVi: {
        enabled: true,
        value: null,
        list: [
          { LOC_DV: '', LOCDV_ID: 0 },
          { LOC_DV: 'Nội bộ', LOCDV_ID: 1 },
          { LOC_DV: 'Khác đơn vị', LOCDV_ID: 2 }
        ]
      },
      cboDuanIMS: {
        enabled: true,
        header: [
          { fieldName: 'ProjectID', headerText: 'STT', allowFiltering: true },
          { fieldName: 'ProjectCode', headerText: 'Mã dự án', allowFiltering: true },
          { fieldName: 'ProjectName', headerText: 'Tên dự án', allowFiltering: true }
        ],
        value: null,
        list: []
      },
      cboLocDCLoaiCT: { enabled: true, value: null, list: [] },
      cboHDPOIMS: {
        enabled: true,
        header: [
          { fieldName: 'PO', headerText: 'PO', allowFiltering: true },
          { fieldName: 'ContractName', headerText: 'Tên hợp đồng', allowFiltering: true },
          { fieldName: 'ContractInfoID', headerText: 'ContractInfoID', allowFiltering: true },
          { fieldName: 'ContractNumber', headerText: 'Số hợp đồng', allowFiltering: true },
          { fieldName: 'ParentContractInfo', headerText: 'ParentContractInfo', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      cboHD_XuatIMS: {
        enabled: true,
        header: [
          { fieldName: 'PO', headerText: 'PO', allowFiltering: true },
          { fieldName: 'ContractName', headerText: 'Tên hợp đồng', allowFiltering: true },
          { width: 100, fieldName: 'ContractInfoID', headerText: 'ContractInfoID', allowFiltering: true },
          { fieldName: 'ContractNumber', headerText: 'Số hợp đồng', allowFiltering: true },
          { width: 100, fieldName: 'ParentContractInfo', headerText: 'ParentContractInfo', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      grcMa_PDA: {
        header: [
          { fieldName: '', headerText: '', template: ChonPDATemplate(this), textAlign: 'Center', width: 80 },
          { fieldName: 'ProjectCode', headerText: 'Mã dự án', allowFiltering: true },
          { fieldName: 'ProjectName', headerText: 'Tên dự án', allowFiltering: true },
          { fieldName: 'ProjectID', visible: false, isPrimaryKey: true }
        ],
        list: [],
        value: null
      },
      cboIMS_CSHT: {
        form: {
          page: 1
        },
        header: [
          { fieldName: '', headerText: '', template: ChonCSHT_PDATemplate(this), textAlign: 'Center', isPrimaryKey: true },
          { fieldName: 'ma_csht', headerText: 'Mã CSHT', allowFiltering: true },
          { fieldName: 'ten_csht', headerText: 'Tên CSHT', allowFiltering: true },
          { fieldName: 'muc_dich_su_dung', headerText: 'MĐ Sử dụng', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      gridDieuChinhVT: {
        header: [
          { fieldName: 'MAVTHH', headerText: 'Mã VT', allowFiltering: true },
          { fieldName: 'TENVTHH', headerText: 'Tên VT', allowFiltering: true },
          { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true },
          { fieldName: 'IDCHITIET', headerText: 'IDCHITIET', allowFiltering: true },
          { fieldName: 'QR_CODE', headerText: 'QR', allowFiltering: true },
          { fieldName: 'MACSHTCU', headerText: 'CSHT cũ', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      gridBienBanIMS: {
        header: [
          { fieldName: '', headerText: '', template: ChonBBBGTemplate(this), textAlign: 'Center', width: 80 },
          { fieldName: 'AcceptanceGeneralName', headerText: 'Biên bản bàn giao', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      gridDS_VT_THUHOI: {
        header: [
          { fieldName: 'MAVTHH', headerText: 'Mã VT', allowFiltering: true },
          { fieldName: 'TENVTHH', headerText: 'Tên VT', allowFiltering: true },
          { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true },
          { fieldName: 'IDCHITIET', headerText: 'IDCHITIET', allowFiltering: true },
          { fieldName: 'QR_CODE', headerText: 'QR', allowFiltering: true },
          { fieldName: 'MACSHTCU', headerText: 'CSHT cũ', allowFiltering: true }
        ],
        list: [],
        value: null
      },
      grcMa_PDA_CSHT: {
        header: [
          { fieldName: '', headerText: '', template: ChonCSHT_PDATemplate(this), textAlign: 'Center', isPrimaryKey: true },
          { fieldName: 'ma_csht', headerText: 'Mã CSHT', allowFiltering: true },
          { fieldName: 'ten_csht', headerText: 'Tên CSHT', allowFiltering: true },
          { fieldName: 'muc_dich_su_dung', headerText: 'MĐ Sử dụng', allowFiltering: true }
        ],
        list: [],
        value: null
      }
    }
  },
  computed: {},
  async mounted() {
    console.log('frmMa_PDA - mounted')
    console.log(this.vinput_spda)
    console.log(this.tu_kho_id)
    console.log(this.den_kho_id)

    // this..value = vinput_spda.ContractNumber
    await this.init()
    // await this.v_btnRef_Click()
    this.cboHD_XuatIMS.value = this.vinput_spda.cboHD_XuatIMS
    this.cboMa_DV_QL.value = this.vinput_spda.cboMa_DV_QL
    this.cboMa_DV_SD.value = this.vinput_spda.cboMa_DV_SD
    this.grcMa_PDA.value = this.vinput_spda.grcMa_PDA
    this.cboIMS_CSHT.value = this.vinput_spda.cboIMS_CSHT
    let index = this.grcMa_PDA.list.findIndex((e) => e.ProjectCode == this.vinput_spda.MA_PDA)
    console.log(this.grcMa_PDA.list)
    console.log(index)
    if (index > 0) {
      const toPage = parseInt(index / this.$refs.grcMa_PDA.pageSize)
      const toRow = index % this.$refs.grcMa_PDA.pageSize
      console.log(`toRow: ${toRow}`)
      setTimeout(() => {
        this.$refs.grcMa_PDA.$refs.pagination.gotoPage(toPage)
        setTimeout(() => {
          this.$refs.grcMa_PDA.selectRow(toRow, false)
        }, 200)
      }, 100)
      this.$refs.grcMa_PDA.grid.selectRow(index)
      this.btnChon_PDA_ButtonClick(this.grcMa_PDA.list[index])
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    /**
     * @see: async cboDuanIMS_EditValueChanged()
     */
    async 'cboDuanIMS.value'(value) {
      // try {
      if (this.cboDuanIMS.value == null || this.cboDuanIMS.value == '') {
        this.$toast.error('Chưa chọn dự án!')
        return
      }
      let ma_coso_hatang = ''
      if (this.kieugoi_id == 2) {
        let find_csht = await this.callApiWrapper(api.ma_co_so_ha_tang, { kho_id: this.den_kho_id })
        console.log('find_csht')
        console.log(find_csht)
        if (find_csht != null && find_csht.length > 0) {
          ma_coso_hatang = find_csht
        }
      } else {
        return
      }
      let dt_hdpo = []
      let data = await this.callApiWrapper(api.contract_info_by_project_code, {
        maDuan: this.cboDuanIMS.list.find((e) => e.ProjectID == this.cboDuanIMS.value).ProjectCode,
        maCsht: ma_coso_hatang
      })
      console.log('579 - api.contract_info_by_project_code')
      console.log(dt_hdpo)
      data = JSON.parse(data)
      if (Object.keys(data).length === 0) {
        this.cboHDPOIMS.list = []
        this.gridBienBanIMS.list = []
        this.$toast.info('Không có thông tin hợp đồng - PO')
        return
      } else {
        if (data.NewDataSet && data.NewDataSet.Table) {
          if (Array.isArray(data.NewDataSet.Table)) dt_hdpo = data.NewDataSet.Table
          else dt_hdpo = [data.NewDataSet.Table]
        }
      }
      dt_hdpo.unshift({
        ContractInfoID: '',
        ContractNumber: '',
        SignOffDate: '',
        PO: '',
        ContractName: '',
        ParentContractInfo: ''
      })
      this.cboHDPOIMS.list = dt_hdpo

      // } catch (e) {
      //   this.$toast.error(e.data.message)
      // } finally {
      // }
    },
    /**
     * @see: async cboHDPOIMS_EditValueChanged() {
     */
    async 'cboHDPOIMS.value'(value) {
      if (this.cboHDPOIMS.value == '' || this.cboHDPOIMS.value == null) {
        this.gridBienBanIMS.list = []
        return
      }
      let ma_coso_hatang = ''
      if (this.kieugoi_id == 2) {
        let find_csht = await this.callApiWrapper(api.ma_co_so_ha_tang, { kho_id: this.den_kho_id })
        console.log('find_csht')
        console.log(find_csht)
        if (find_csht != null && find_csht.length > 0) {
          ma_coso_hatang = find_csht
        }
      } else {
        return
      }
      let HDPOIMSFilter = this.cboHDPOIMS.list.filter((a) => a.ContractInfoID == this.cboHDPOIMS.value)
      let contractNumber = HDPOIMSFilter.length > 0 ? HDPOIMSFilter[0].ParentContractInfo : ''
      console.log('------> xem o day 573')
      console.log(this.cboHDPOIMS.list)
      console.log(HDPOIMSFilter)
      console.log(contractNumber)
      console.log(ma_coso_hatang)
      console.log(this.cboDuanIMS.list.find((e) => e.ProjectID == this.cboDuanIMS.value))
      let data = await this.callApiWrapper(api.asset_by_keys, {
        maDuan: this.cboDuanIMS.list.find((e) => e.ProjectID == this.cboDuanIMS.value).ProjectCode,
        contractNumber: contractNumber,
        poNumber: this.cboHDPOIMS.list.find((e) => e.ContractInfoID == this.cboHDPOIMS.value).ContractInfoID,
        maCsht: ma_coso_hatang
      })
      let dt_keys = []
      data = JSON.parse(data)
      if (Object.keys(data).length === 0) {
        this.gridBienBanIMS.list = []
        return
      } else {
        if (data.NewDataSet && data.NewDataSet.Table) {
          if (Array.isArray(data.NewDataSet.Table)) dt_keys = data.NewDataSet.Table
          else dt_keys = [data.NewDataSet.Table]
        }
      }
      console.log('dt_keys')
      console.log(dt_keys)
      this.bbbgiao = dt_keys
      this.gridBienBanIMS.list = dt_keys
    },
    /**
     * @see: cboHD_XuatIMS_EditValueChanged
     */
    'cboHD_XuatIMS.value'(value) {
      console.log('watcher - cboHD_XuatIMS.value')
      console.log(value)
      if (value != null && value.length) this.cboHD_XuatIMS.value = value.PO
    },
    /**
     * @see: private void cboLocDCDonVi_EditValueChanged(object sender, EventArgs e)
     */

    async cboLocDCDonVi_EditValueChanged() {
      if (this.cboLocDCDonVi.value == '0' || this.cboLocDCDonVi.value == null) {
        this.cboLocDCDonVi.list = []
        this.gridDieuChinhVT.list = this.dieuchuyen_clone
        return
      }
      this.cboLocDCLoaiCT.list = await this.getDanhMucLoaiChungTu(this.cboLocDCDonVi.value)
    },
    async 'cboLocDCLoaiCT.value'(value) {
      if (this.cboLocDCLoaiCT.value == null || this.cboLocDCLoaiCT.value == '') {
        return
      }
      this.gridDieuChinhVT.list = await this.callApiWrapper(api.kiem_tra_sl_qlts, {
        kieu: 0,
        data: this.dieuchuyen_clone,
        maPda: this.params.p_Ma_PDA,
        timKiem: this.cboLocDCDonVi.value,
        loaiCtId: this.cboLocDCLoaiCT.value
      })
    },
    async 'cboLocDCDonVi.value'(value) {
      if (this.cboLocDCDonVi.value == '0' || this.cboLocDCDonVi.value == null) {
        this.cboLocDCDonVi.list = []
        this.gridDieuChinhVT.list = this.dieuchuyen_clone
        return
      }
      this.cboLocDCLoaiCT.list = await this.callApiWrapper(api.danh_muc_loai_chung_tu_nx, { kieu: this.cboLocDCDonVi.value })
    },
    async 'cboIMS_CSHT.form.page'(val) {
      await this.searchCsht()
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
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
     * @see: private void cboNghiepVu_EditValueChanged(object sender, EventArgs e)
     */
    async init() {
      // this.grcMa_PDA.list.list = null
      // this.cboHDPOIMS.list.list = null
      // this.cboDuanIMS.list.list = null
      // this.gridBienBanIMS.list.list = null
      // this.gridDS_VT_THUHOI.list.list = null
      // this.gridDieuChinhVT.list.list = null
      // // cboIMS_CSHT.ResetText();
      // this.cboMa_DV_SD.list.value = null
      // this.cboMa_DV_QL.list.value = null
      // this.cboHD_XuatIMS.list.value = null
      // this.cboMaPhieu.list.list = null
      // this.cboMaPhieu.list.value = null
      // // duan_ims_clone.Clear();
      // // bbbgiao.Clear();
      // // dieuchuyen_clone.Clear();
      console.log('this.vnghiepvu_id')
      console.log(this.vnghiepvu_id)

      let nghiepvu_ = (await api.ds_ma_muc_dich_theo_muc_dich(this.axios, { mucdich_id: this.vnghiepvu_id })).data.data
      if (nghiepvu_.lenght == 0) return
      let ma_nghiepvu = nghiepvu_[0]
      console.log('ma_nghiepvu')
      console.log(ma_nghiepvu)

      if (ma_nghiepvu == 'NV.BBBGIMS') {
        this.layoutControlDuAn.visible = false
        this.layoutControlDieuChinh.visible = false
        this.layoutControlBBBG.visible = true
        this.layoutControlDS_Vattu.visible = false
        this.tabSPDA = 3
        this.layoutMaPhieu.visible = false
      } else if (ma_nghiepvu == 'NV.DUAN') {
        this.layoutMaPhieu.visible = false
        this.layoutControlDuAn.visible = true
        this.layoutControlDieuChinh.visible = false
        this.layoutControlBBBG.visible = false
        this.layoutControlDS_Vattu.visible = false
        this.tabSPDA = 1
        this.cboMa_DV_SD.enabled = false
        this.cboMa_DV_QL.enabled = false
        this.cboIMS_CSHT.enabled = false
        this.cboHD_XuatIMS.enabled = false
      } else if (ma_nghiepvu == 'NV.XUATIMS' || ma_nghiepvu == 'NV.ACAP' || ma_nghiepvu == 'NV.CHUYENNGUON') {
        this.layoutMaPhieu.visible = false
        this.layoutControlDuAn.visible = true
        this.layoutControlDieuChinh.visible = false
        this.layoutControlBBBG.visible = false
        this.layoutControlDS_Vattu.visible = false
        this.tabSPDA = 1
        this.cboMa_DV_SD.enabled = true
        this.cboMa_DV_QL.enabled = true
        this.cboIMS_CSHT.enabled = true
        this.cboHD_XuatIMS.enabled = true
      } else if (ma_nghiepvu == 'NV.THUHOI' || ma_nghiepvu == 'NV.THAYTHE') {
        this.layoutMaPhieu.visible = true
        this.layoutControlDuAn.visible = false
        this.layoutControlDieuChinh.visible = false
        this.layoutControlBBBG.visible = false
        this.layoutControlDS_Vattu.visible = true
        this.tabSPDA = 4

        // gridDS_VT_THUHOI.list.setAllGridView_SpaceToggleCheck(false, true, false)
      } else if (ma_nghiepvu == 'NV.DIEUCHUYEN') {
        this.layoutMaPhieu.visible = true
        this.layoutControlDuAn.visible = false
        this.layoutControlDieuChinh.visible = true
        this.layoutControlBBBG.visible = false
        this.layoutControlDS_Vattu.visible = false
        this.tabSPDA = 2
        // gridDieuChinhVT.list.setAllGridView_SpaceToggleCheck(false, true, false)
        // let filter_donvi = ChungTu.GET_DATA_SQL(@"select 0 locdv_id, '' loc_dv from dual
        //                                             union all
        //                                             select 1 locdv_id, 'Nội bộ' loc_dv from dual
        //                                             union all
        //                                               select 2 locdv_id, 'Kh·c đơn vị' loc_dv from dual");
        // bangts.HT_DS_GRIDLOOKUP(cboLocDCDonVi.list, filter_donvi, "LOC_DV", "LOCDV_ID", false, 1, 300, false, false);
      } else {
        this.$toast.warning('Nghiệp vụ này không có mã dự án!')
        this.$parent.hide()
        return
      }
      this.cboTinh.list = await this.callApiWrapper(await api.ds_tinh, {})
      if (this.tabSPDA == 1) {
        this.cboMa_DV_SD.list = await this.callApiWrapper(api.ds_don_vi_su_dung, { kieu: 0 })
        this.cboMa_DV_QL.list = await this.callApiWrapper(api.ds_don_vi_su_dung, { kieu: 1 })
      }

      if (this.tabSPDA == 1) {
        this.cboMa_DV_SD.list = await this.callApiWrapper(api.ds_don_vi_su_dung, { kieu: 0 })
        this.cboMa_DV_QL.list = await this.callApiWrapper(api.ds_don_vi_su_dung, { kieu: 1 })
      }
    },
    cboMaPhieu_EditValueChanged() {
      this.params.p_Ma_PDA = this.cboMaPhieu.value
    },

    async v_btnRef_Click() {
      // try {
      this.grcMa_PDA.list = []
      this.cboHDPOIMS.list = []
      this.cboDuanIMS.list = []
      this.gridBienBanIMS.list = []
      this.gridDS_VT_THUHOI.list = []
      this.duan_ims_clone = []
      this.bbbgiao = []
      this.dieuchuyen_clone = []
      if (this.vnghiepvu_id == null || this.vnghiepvu_id == '') {
        this.$toast.error('Chưa chọn nghiệp vụ!')
        return
      }
      let nghiepvu_ = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.vnghiepvu_id })
      if (nghiepvu_.length == 0) {
        return
      }

      let ma_nghiepvu = nghiepvu_[0]
      if (ma_nghiepvu == 'NV.DUAN' || ma_nghiepvu == 'NV.XUATIMS' || ma_nghiepvu == 'NV.ACAP' || ma_nghiepvu == 'NV.CHUYENNGUON') {
        let dt = await this.callApiWrapper(api.ds_ref_ims, {
          mucDichId: this.vnghiepvu_id,
          kieuGoiId: this.kieugoi_id,
          maPda: this.ma_pda,
          khoId: this.den_kho_id
        })
        console.log('786 - api.ds_ref_ims')
        console.log(dt)
        if (dt != null && dt != '') {
          let data = JSON.parse(dt)
          console.log(data)
          if ('NewDataSet' in data && 'Table' in data.NewDataSet) {
            this.grcMa_PDA.list = data.NewDataSet.Table
            console.log(this.grcMa_PDA.list, ' = this.grcMa_PDA.list')
          } else {
            this.grcMa_PDA.list = []
          }
        } else {
          this.grcMa_PDA.list = []
        }
      }
      if (ma_nghiepvu == 'NV.THUHOI' && this.kieugoi_id == 2) {
        if (this.kieu_phieu_id == null || this.kieu_phieu_id == '') {
          this.$toast.error('Bạn chưa chọn kiểu phiếu !')
          return
        }

        if (this.mucdich_id == null || this.mucdich_id == '') {
          this.$toast.error('Bạn chưa chọn mục đích !')
          return
        }
        if (this.mucdich_id == null || this.mucdich_id == '') {
          this.$toast.error('Bạn chưa chọn mục đích !')
          return
        }

        if (this.tu_kho_id == null || this.tu_kho_id == '' || this.tu_kho_id == 0) {
          this.$toast.error('Bạn chưa chọn từ kho !')
          return
        }
        if (this.tu_kho_id == null || this.tu_kho_id == '' || this.tu_kho_id == 0) {
          this.$toast.error('Bạn chưa chọn từ kho !')
          return
        }
        if (this.den_kho_id == null || this.den_kho_id == '' || this.den_kho_id == 0) {
          this.$toast.error('Bạn chưa chọn đến kho !')
          return
        }
        if (this.den_kho_id == null || this.den_kho_id == '' || this.den_kho_id == 0) {
          this.$toast.error('Bạn chưa chọn đến kho !')
          return
        }
        if (this.ma_pda == null || this.ma_pda == '') {
          this.$toast.error('Bạn chưa nhập số phiếu trên QLTS !')
          return
        }
        let dt = await this.callApiWrapper(api.ds_ref_ims, {
          mucDichId: this.vnghiepvu_id,
          kieuGoiId: this.kieugoi_id,
          maPda: this.ma_pda,
          khoId: this.den_kho_id
        })
        this.gridDS_VT_THUHOI.list = dt
        console.log(dt, ' = dt')
      }
      if (ma_nghiepvu == 'NV.DIEUCHUYEN') {
        if (this.ma_pda == null || this.ma_pda == '') {
          this.$toast.error('Bạn chưa nhập số phiếu trên QLTS !')
          return
        }
        this.cboLocDCDonVi.value = 0
        let dt = await this.callApiWrapper(api.ds_ref_ims, {
          mucDichId: this.vnghiepvu_id,
          kieuGoiId: this.kieugoi_id,
          maPda: this.ma_pda,
          khoId: this.den_kho_id
        })
        this.gridDS_VT_THUHOI.list = dt
        console.log(dt, ' = dt')
      }
      if (ma_nghiepvu == 'NV.DIEUCHUYEN') {
        if (this.ma_pda == null || this.ma_pda == '') {
          this.$toast.error('Bạn chưa nhập số phiếu trên QLTS !')
          return
        }
        this.cboLocDCDonVi.value = 0

        let dt = await this.callApiWrapper(api.ds_ref_ims, {
          mucDichId: this.vnghiepvu_id,
          kieuGoiId: this.kieugoi_id,
          maPda: this.ma_pda,
          khoId: this.den_kho_id
        })
        this.dieuchuyen_clone = dt
        this.gridDieuChinhVT.list = dt
      }

      if (ma_nghiepvu == 'NV.BBBGIMS') {
        if (this.kieu_phieu_id == null || this.kieu_phieu_id == '') {
          this.$toast.error('Bạn chưa chọn kiểu phiếu !')
          return
        }
        if (ma_nghiepvu == 'NV.BBBGIMS') {
          if (this.kieu_phieu_id == null || this.kieu_phieu_id == '') {
            this.$toast.error('Bạn chưa chọn kiểu phiếu !')
            return
          }

          if (this.mucdich_id == null || this.mucdich_id == '') {
            this.$toast.error('Bạn chưa chọn mục đích !')
            return
          }

          if (this.tu_kho_id == null || this.tu_kho_id == '' || this.tu_kho_id == 0) {
            this.$toast.error('Bạn chưa chọn từ kho !')
            return
          }

          if (this.den_kho_id == null || this.den_kho_id == '' || this.den_kho_id == 0) {
            this.$toast.error('Bạn chưa chọn đến kho !')
            return
          }
          if (this.kieugoi_id == 2) {
            let find_csht = await this.callApiWrapper(api.ma_co_so_ha_tang, { kho_id: this.den_kho_id })
            if (find_csht == null || find_csht.length == 0) {
              this.$toast.error('Kho đến không có mã CSHT ! Hãy kiểm tra lại !')
              return
            }
          }
          let dt = await this.callApiWrapper(api.ds_ref_ims, {
            mucDichId: this.vnghiepvu_id,
            kieuGoiId: this.kieugoi_id,
            maPda: this.ma_pda,
            khoId: this.den_kho_id
          })
          console.log('843 - api.ds_ref_ims')
          console.log(dt)
          if (dt != null && dt != '') {
            let data = JSON.parse(dt)
            console.log(data)
            if ('NewDataSet' in data && 'Table' in data.NewDataSet) {
              this.cboDuanIMS.list = data.NewDataSet.Table
              console.log(this.cboDuanIMS.list, ' = this.grcMa_PDA.list')
            } else {
              this.cboDuanIMS.list = []
            }
          } else {
            this.cboDuanIMS.list = []
          }
          this.duan_ims_clone = this.cboDuanIMS.list
        }
      }
      // } catch (e) {
      //   this.$toast.error('Đã có lỗi xảy ra vui lòng thử lại!')
      // } finally {
      // }
      //CSHT<article>
    },
    async btnXacNhanOnClicked() {
      let data = {}
      data.cboMa_DV_SD = this.cboMa_DV_SD.value
      data.cboIMS_CSHT = this.cboIMS_CSHT.value
      data.cboMa_DV_QL = this.cboMa_DV_QL.value
      data.cboHD_XuatIMS = this.cboHD_XuatIMS.value
      data.grcMa_PDA = this.grcMa_PDA.value

      if (this.cboHD_XuatIMS.value) {
        data.ContractNumber = this.cboHD_XuatIMS.list.find((e) => e.ContractInfoID == this.cboHD_XuatIMS.value).ContractNumber
      }
      if (this.tabSPDA == 1) {
        if (this.vma_md == '2.XCP') {
        } else {
          data.tag = 1
        }
      }
      this.$emit('onBtnXacNhanClickedListener', data)
    },
    // removeCSHT() {},
    toggleMaCSHT() {
      this.maCSHT.visible = !this.maCSHT.visible
    },
    searchCshtCode(e) {
      console.log('searchCshtCode')
      console.log(e)
    },
    searchCshtName(e) {
      console.log('searchCshtName')
      console.log(e)
    },
    cboIMS_CSHTFormPagePrevious() {
      this.cboIMS_CSHT.form.page = --this.cboIMS_CSHT.form.page < 0 ? 0 : this.cboIMS_CSHT.form.page--
    },
    cboIMS_CSHTFormPageNext() {
      this.cboIMS_CSHT.form.page++
    },
    async searchCsht() {
      // try {
      const res = await this.callApiWrapper(api.asset_get_station_by_code, {
        ma_tinh: `.${this.cboTinh.value ? this.cboTinh.value : this.$root.token.getMaTinh()}`,
        ma_csht: this.maCSHT.code.length == 0 ? '' : `.${this.maCSHT.code}`,
        name: this.maCSHT.name,
        rims_status: null,
        page_index: this.cboIMS_CSHT.form.page,
        page_size: 10
      })

      if (res) {
        const cshtData = res
        const csht = []
        for (let i = 0; i < cshtData.data.length; i++) {
          for (let j = 0; j < cshtData.data[i].json_station.length; j++) {
            csht.push(cshtData.data[i].json_station[j])
          }
        }
        if (csht.length) this.cboIMS_CSHT.list = csht
        else {
          this.cboIMS_CSHT.list = []
          this.cboIMS_CSHT.form.page = 1
        }
      }
      // } catch (error) {
      //   console.log(error.toString())
      //   this.$toast.error(error.toString())
      // }
    },
    maCshtSearchSubmitted(e) {
      console.log('maCshtSearchSubmitted')
      console.log(e)
    },
    async btnChon_PDA_ButtonClick(rowData) {
      console.log('btnChon_PDA_ButtonClick')
      console.log(rowData)
      this.grcMa_PDARowSelected(rowData)
    },
    async btnChon_CSHT_PDA_ButtonClick(rowData) {
      console.log('btnChon_CSHT_PDA_ButtonClick')
      console.log(rowData)
      this.cboIMS_CSHT.value = rowData.ma_csht
      // this.grcMa_PDARowSelected(rowData)
    },
    grcMa_PDARowSelected(rowData) {
      this.grcMa_PDA.value = rowData.ProjectID
      console.log('grcMa_PDARowSelected')
      console.log(rowData)
      this.$emit('grcMa_PDA_onDuAnClicked', rowData)
    },
    grcMa_PDA_CSHTRowSelected(rowData) {
      console.log('grcMa_PDA_CSHTRowSelected')
      console.log(rowData)
      this.cboIMS_CSHT.value = rowData.data.ma_csht
      this.toggleMaCSHT()
    },
    //Tab = 3
    btn_chonBBBG_ButtonClick(data) {
      console.log('btn_chonBBBG_ButtonClick')
      console.log(data)
      this.gridBienBanIMS_RowSelected(data)
    },
    gridBienBanIMS_RowSelected(data) {
      this.$emit('gridBienBanIMS_OnChonBBBG', { row: data, gridBienBanIMS: this.gridBienBanIMS.list, bbbgiao: this.bbbgiao, cboDuanIMS: this.cboDuanIMS, cboHDPOIMS: this.cboHDPOIMS })
    },
    async cboMaPhieu_ButtonClick(buttonTag) {
      try {
        if (buttonTag == '1') {
          let loi = ''

          // let nghiepvu_ = ChungTu.GET_DATA_SQL("select ma_md from {?DB12}.mucdich where mucdich_id = :vid", "vid",
          //                     Convert.ToInt32(cboNghiepVu.EditValue));
          let nghiepvu_ = (await api.ds_ma_muc_dich_theo_muc_dich(this.axios, { mucdich_id: this.vnghiepvu_id })).data.data
          if (nghiepvu_.length == 0) return

          let kq_api = await this.callApiWrapper(api.ds_phieu_nv, { mucdich_id: nghiepvu_[0] })
          //  DanhMuc.LAY_DS_PHIEU_NV(ttnd.tentat, nghiepvu_.Rows[0][0].ToString(), "", out loi);
          // kq_api = kq_api.replace("\\\\", "\\").replace("\\\"", "\"");
          // kq_api = kq_api.replace("\"{", "{").replace("}\"", "}");

          // if (kq_api.ToUpper().includes("DOCTYPE") || kq_api.ToUpper().includes("ERROR"))
          // {
          //   this.$toast.error(kq_api);
          //     return;
          // }

          // let myDeserializedClass = kq_api;

          // if (kq_api.ToUpper().includes("FALSE") || kq_api.ToUpper().includes("DOCTYPE"))
          // {
          //   this.$toast.error("" + myDeserializedClass.message);
          //     return;
          // }
          // let maphieu = myDeserializedClass.data.MaPhieu;

          // let z_mp = []
          // z_mp.Columns.Add("MARK");
          // z_mp.Columns.Add("MAPHIEU");
          // z_mp.Rows.Add(0, "");

          // for (let i = 0; i < maphieu.length; i++)
          // {
          //     z_mp.Rows.Add(i + 1, maphieu[i]);
          // }

          // bangts.HT_DS_GRIDLOOKUP(cboMaPhieu, z_mp, "MAPHIEU", "MAPHIEU", true, 1, 300, false, true);
        }
      } catch (ex) {
        this.$toast.warning(`Button ma phieu: ${ex}`)
      }
    },
    async cboMaPhieu_EditValueChanged() {
      if (this.cboMaPhieu.value == null) this.cboMaPhieu.value = ''
      else this.cboMa_PDA.value = this.cboMaPhieu.value
    },
    async btnCapNhatVattu_ThuHoi_Click() {
      this.$emit('btnCapNhatVattu_ThuHoi_Click', {})
      // try {
      // if (this.cboNghiepVu.value == null) {
      //   return
      // }
      // if (this.cboNghiepVu.value == '286' && kieugoi_id == 2) {
      //   if (this.tsbtnNhapMoi.enabled) {
      //     this.$toast.warning('Bạn chưa nhập mới không thể chọn mã csht =!')
      //     return
      //   }
      //   if (this.gridDS_VT_THUHOI.list == null) {
      //     this.$toast.warning('Không có dữ liệu vật tư tài sản!')
      //     // v_btnRef.Focus();
      //     return
      //   }
      //   if (this.gridDS_VT_THUHOI.list.length == 0) {
      //     this.$toast.warning('Không tìm thấy vật tư tài sản!')
      //     return
      //   }

      //   if (!KiemTraDuLieu(true, false, 'THUHOITS')) return

      //   // gridviewDS_VT_THUHOI.CloseEditor();
      //   // let ischeck = gridDS_VT_THUHOI.list;
      //   // ischeck.AcceptChanges();
      //   // let group_ =  ischeck.Select("ISCHECK = 's1'");
      //   let group_ = this.$refs.gridDS_VT_THUHOI.getSelectedRecords()

      //   if (group_.length == 0) {
      //     this.$toast.warning('Bạn chưa chọn dÚng vật tư n‡o !')
      //     return
      //   }

      //   if (!confirm(`Ban có chắc chắn muốn chọn ${group_.length} vật tư của phiếu ${cboMa_PDA.value} không?`)) return

      //   let loc_dachon = group_.map((e) => e)
      //   let js_ = JSON.stringify(loc_dachon)
      //   let loc_csht = await this.kiemTraLocCSHT({
      //     data: loc_dachon,
      //     khoId: this.cboDenKho.value
      //   })
      //   if (loc_csht.length > 0) {
      //     if (!confirm(`Kho ${cboDenKho.value} đang có mã csht khác ${loc_csht[0]}. Bạn có muốn tiếp tục không?`)) return
      //   }

      //   if (kieugoi_id == 2) {
      //     let t_ctct = []
      //     let t_ctbi = []
      //     for (let dr of loc_dachon) {
      //       if (dr.SERIAL != '') {
      //         let z = t_ctct.Where((l) => l.MA_VT == dr.MA_VT && l.DONGIA == dr.DONGIA).FirstOrDefault()
      //         if (z != null) {
      //           z.SL_NHAP = z.SL_NHAP + Convert.ToDecimal(dr.SOLUONG)
      //         } else {
      //           t_ctct.push({
      //             MA_VT: dr.MA_VT,
      //             DONGIA: Convert.ToDecimal(dr.DONGIA),
      //             SL_NHAP: Convert.ToDecimal(dr.SOLUONG),
      //             VMA_PDA: dr.MADUAN
      //           })
      //         }
      //         t_ctbi.push({
      //           MA_VT: dr.MA_VT,
      //           SOLUONG: 1,
      //           ID_CHITIET: dr.IDCHITIET,
      //           SERIAL: dr.SERIAL
      //         })
      //       } else {
      //         t_ctct.push({
      //           MA_VT: dr.MA_VT,
      //           DONGIA: Convert.ToDecimal(dr.DONGIA),
      //           SL_NHAP: Convert.ToDecimal(dr.SOLUONG),
      //           VMA_PDA: dr.MADUAN
      //         })

      //         t_ctbi.push({
      //           MA_VT: dr.MA_VT,
      //           SOLUONG: -1,
      //           ID_CHITIET: dr.IDCHITIET,
      //           SERIAL: ''
      //         })
      //       }
      //     }

      //     let json_ds_vt = JSON.stringify(t_ctct)
      //     let json_ds_tbi = JSON.stringify(t_ctbi)

      //     let ketqua_ = ChungTu.GET_VALUE_FUNC(
      //       '{?DB12}.sinh_chungtu_qlts',
      //       'vkieu',
      //       2,
      //       'vkho_nhan_id',
      //       cboDenKho.value,
      //       'vngay_ct',
      //       dtpNgayDK.DateTime,
      //       'vnd_giao',
      //       txtNoiDung.Text,
      //       'vnghiepvu_id',
      //       cboNghiepVu.value,
      //       'vma_pda',
      //       cboMa_PDA.Text,
      //       'vds_vt',
      //       json_ds_vt,
      //       'vds_chitiet',
      //       json_ds_tbi,
      //       'vmay_cn',
      //       ttnd.may_cn,
      //       'vip_cn',
      //       ttnd.ip,
      //       'vkho_giao_id',
      //       cboTuKho.value,
      //       'vloaict_id',
      //       1,
      //       'vdonvi_giao_id',
      //       ttnd.donvi_id,
      //       'vdonvi_nhan_id',
      //       ttnd.donvi_id,
      //       'vloaiphieu_id',
      //       cboKieuPhieu.value,
      //       'vnguoi_cn',
      //       ttnd.ma_nd,
      //       'vmucdich_id',
      //       cboMucDich.value
      //     )
      //     if (ketqua_.StartsWith('OK')) {
      //       this.$toast.success('Tạo chứng từ thành công')
      //       let vchungtu_tmp_id = Convert.ToInt64(ketqua_.Split('-')[1])
      //       cboTuKho.EditValueChanged -= cboTuKho_EditValueChanged
      //       Load_ds_chungtu()
      //       gridviewChungTu.ClearColumnsFilter()
      //       gridviewChungTu.FocusedRowHandle = GridControl.AutoFilterRowHandle
      //       ChucNang.FocusGridViewRow(gridviewChungTu, 'CHUNGTU_ID', vchungtu_tmp_id)
      //       cboTuKho.EditValueChanged += cboTuKho_EditValueChanged
      //     } else {
      //       this.$toast.error('Tạo chứng từ thất bại ' + ketqua_)
      //     }
      //   }
      // }
      // } catch (ex) {
      //   this.$toast.warning('Thu hồi tài sản: ' + ex)
      // }
    }
    // async btnCapNhatVattu_ThuHoi_Click() {
    //   try {
    //     if (this.cboNghiepVu.value == null || this.cboNghiepVu.value == '') {
    //       this.$toast.error('Chưa chọn nghiệp vụ')
    //       return
    //     }
    //     if (this.cboNghiepVu.value == '286' && this.kieugoi_id == 2) {
    //       if (this.tsbtnNhapMoi.enabled) {
    //         this.$toast.error('Bạn chưa nhập mới không thể chọn mã csht !')
    //         return
    //       }
    //       if (this.gridDS_VT_THUHOI.length == 0) {
    //         this.$toast.error('Không có dữ liệu vật tư tài sản !')
    //         return
    //       }
    //       if (this.DS_VT_THUHOI_Checked.length == 0) {
    //         this.$toast.error('Bạn chưa chọn dòng vật tư nào !')
    //         return
    //       }
    //       if (!(await this.KiemTraDuLieu(true, false, 'THUHOITS'))) {
    //         return
    //       }
    //       let loc_csht = await this.kiemTraLocCSHT({
    //         data: this.DS_VT_THUHOI_Checked,
    //         khoId: this.cboDenKho.value
    //       })
    //       if (loc_csht.length > 0) {
    //         if (!confirm(`Kho ${this.cboDenKho.value} đang có mã csht khác ${loc_csht[0].MA_CSHT}. Bạn có muốn tiếp tục không? `)) return
    //       }
    //       let t_ctct = []
    //       let t_ctbi = []
    //       for (let dr of this.DS_VT_THUHOI_Checked) {
    //         if (dr.SERIAL != '') {
    //           let z = t_ctct.filter((l) => l.MA_VT == dr.MA_VT && l.DONGIA == dr.DONGIA)
    //           if (z.length > 0) {
    //             z[0].SL_NHAP = parseInt(z[0].SL_NHAP) + parseInt(dr.SOLUONG)
    //           } else {
    //             t_ctct.push({
    //               MA_VT: dr.MA_VT,
    //               DONGIA: parseInt(dr.DONGIA),
    //               SL_NHAP: parseInt(dr.SOLUONG),
    //               VMA_PDA: dr.MADUAN
    //             })
    //           }
    //           t_ctbi.push({
    //             MA_VT: dr.MA_VT,
    //             SOLUONG: -1,
    //             ID_CHITIET: dr.IDCHITIET,
    //             SERIAL: dr.SERIAL
    //           })
    //         } else {
    //           t_ctct.push({
    //             MA_VT: dr.MA_VT,
    //             DONGIA: parseInt(dr.DONGIA),
    //             SL_NHAP: parseInt(dr.SOLUONG),
    //             VMA_PDA: dr.MADUAN
    //           })
    //           t_ctbi.push({
    //             MA_VT: dr.MA_VT,
    //             SOLUONG: -1,
    //             ID_CHITIET: dr.IDCHITIET,
    //             SERIAL: ''
    //           })
    //         }
    //       }
    //       let _ketqua = await this.SinhChungTuIMSBienBan({
    //         kieu: 2,
    //         khoNhanId: this.cboDenKho.value,
    //         ngayCt: getDateStringOrDefault(this.dtpNgayDK.value, 'DD/MM/YYYY hh:mm:ss'),
    //         ndGiao: this.txtNoiDung.value,
    //         nghiepVuId: this.cboNghiepVu.value,
    //         maPda: row.ProjectCode,
    //         maHd: null,
    //         maPo: null,
    //         bbBg: null,
    //         dsVt: ketqua_,
    //         dsChiTiet: [{}],
    //         nguoiCn: await this.$root.token.getUserName(),
    //         mayCn: await this.$root.token.getMachine(),
    //         ipCn: await this.$root.token.getIP(),
    //         khoGiaoId: this.cboTuKho.value,
    //         loaiCtId: 1,
    //         donViGiaoId: this.$root.token.getDonViID(),
    //         donViNhanId: this.$root.token.getDonViID(),
    //         loaiPhieuId: this.cboKieuPhieu.value,
    //         mucDichId: this.cboMucDich.value
    //       })
    //       if (_ketqua.startsWith('OK')) {
    //         this.$toast.success('Tạo chứng từ thành công')
    //         let vchungtu_tmp_id = parseInt(_ketqua.Split('-')[1])
    //         await this.Load_ds_chungtu()
    //       } else {
    //         this.$toast.error(`Tạo chứng từ thất bại: ${_ketqua}`)
    //       }
    //     }
    //   } catch (e) {
    //     this.$toast.error(e.data.message)
    //   }
    // }
  },
  created() {},
  destroyed() {}
}
</script>
<style scoped>
.csht-popup {
  width: 900px;
  position: absolute;
  right: 0px;
  z-index: 100;
}

.outside {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0px;
  right: 0px;
  background: #e9ecef;
  opacity: 0.5;
  z-index: 1;
}
</style>
