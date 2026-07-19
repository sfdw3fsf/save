<template>
  <!-- popupMa_PDA -->
  <div v-if="view.valid">
    <div class="modal-content popup-box">
      <div class="list-actions-top" style="padding-right: 0px !important; padding-top: 15px !important;">
        <div class="col-sm-6 col-12">
          <div class="info-row" v-if="layoutMaPhieu.visible">
            <div class="key">
              Mã phiếu
            </div>
            <div class="value">
              <div class="input-group inline">
                <div class="input-text">
                  <div class="select-custom">
                    <SelectExt ref="cboMaPhieu" :disabled="!cboMaPhieu.enabled" v-model="cboMaPhieu.value" :dataSource="cboMaPhieu.list" dataTextField="MaPhieu" dataValueField="MaPhieu" />
                  </div>
                </div>
                <div class="input-addon">
                  <button class="btn" @click="cboMaPhieu_ButtonClick">
                    <span class="nc-icon-glyph ui-1_bold-add f14"></span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="pull-right">
            <button class="btn btn-second pad5" @click="v_btnRef_Click">
              <span class="one-reload f16"></span>
            </button>
          </div>
        </div>
      </div>
      <div class="popup-body">
        <div class="nav tabs tab-over">
          <a href="#dsda" data-toggle="tab" :class="{ active: tabSPDA == 1 }" @click="tabSPDA = 1" v-if="layoutControlDuAn.visible">
            Danh sách dự án
          </a>
          <a href="#vtqlts" data-toggle="tab" :class="{ active: tabSPDA == 2 }" @click="tabSPDA = 2" v-if="layoutControlDieuChinh.visible">
            Danh sách vật tư QLTS
          </a>
          <a href="#bbbg" data-toggle="tab" :class="{ active: tabSPDA == 3 }" @click="tabSPDA = 3" v-if="layoutControlBBBG.visible">
            Biên bản bàn giao
          </a>
          <a href="#dsvt" data-toggle="tab" :class="{ active: tabSPDA == 4 }" @click="tabSPDA = 4" v-if="layoutControlDS_Vattu.visible">
            Danh sách vật tư
          </a>
        </div>

        <div class="box-form tab-content">
          <!-- TAB DS dự án -->
          <div class="tab-pane" :class="{ active: tabSPDA == 1 }" id="dsda" v-if="layoutControlDuAn.visible">
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="box-form">
                  <div class="row">
                    <div class=" col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w90">
                          Mã DV SD
                        </div>
                        <div class="value">
                          <SelectExt ref="cboMaPhieu" :disabled="!cboMa_DV_SD.enabled" v-model="cboMa_DV_SD.value" :dataSource="cboMa_DV_SD.list" dataTextField="TEN_DV" dataValueField="MA_DV_SD" />
                        </div>
                      </div>
                    </div>
                    <div class=" col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w90">
                          Mã CSHT
                        </div>
                        <div class="input-group" style="padding-left: 5px;">
                          <div class="input-text">
                            <input type="text" class="form-control" v-model="params.p_IMS_CSHT" :disabled="!cboHD_XuatIMS.enabled" @click="openPopupCSHT" />
                          </div>
                          <div class="input-addon">
                            <button class="btn" :disabled="!cboHD_XuatIMS.enabled" @click="clearCSHT">
                              <span class="nc-icon-glyph ui-1_bold-remove f14"></span>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w90">
                          Mã DV QL
                        </div>
                        <div class="value">
                          <SelectExt ref="cboMa_DV_QL" :disabled="!cboMa_DV_QL.enabled" v-model="cboMa_DV_QL.value" :dataSource="cboMa_DV_QL.list" dataTextField="TEN_DV" dataValueField="MA_DV_QL" />
                        </div>
                      </div>
                    </div>
                    <div class=" col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w90">
                          HĐ PO
                        </div>

                        <div class="value" style="position: relative; z-index: 999;">
                          <div class="select-custom" ref="title-hdpo">
                            <input type="text" class="form-control" :value="params.p_HD_XuatIMS" readonly style="cursor:default; background-color: #fff !important;" />
                          </div>
                          <div ref="table-hdpo" style="z-index: 99999; background-color: white; border: 1px solid #ddd; width: 100%;" :style="{ display: showDHPO }">
                            <div style="position: absolute; z-index: 999; max-width: 800px; overflow: auto; border: 1px solid #ddd; margin-bottom: 5px; ">
                              <DataGrid
                                ref="tbNhaThau"
                                class="table-result table-gachle"
                                :columns="cboHD_XuatIMS.header"
                                :dataSource="cboHD_XuatIMS.list"
                                :showFilter="true"
                                dataKeyField="ProjectCode"
                                @selectedRowChanged="cboHD_XuatIMS_EditValueChanged"
                                :allowPaging="true"
                                :enablePagingServer="false"
                                :enabledSelectFirstRow="false"
                              >
                              </DataGrid>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="table-content col-sm-12 col-12">
                    <DataGrid :columns="grcMa_PDA.header" ref="gridDSMa_PDA" :showFilter="true" :allowPaging="true" :enablePagingServer="false" dataKeyField="ProjectID" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :dataSource="grcMa_PDA.list" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="tab-pane" :class="{ active: tabSPDA == 2 }" id="vtqlts" v-if="layoutControlDieuChinh.visible">
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="box-form">
                  <div class="row">
                    <div class=" col-sm-4 col-12">
                      <div class="info-row">
                        <div class="key w90">
                          Tìm kiếm theo
                        </div>
                        <div class="value">
                          <SelectExt ref="cboLocDCDonVi" :disabled="!cboLocDCDonVi.enabled" v-model="cboLocDCDonVi.value" :dataSource="cboLocDCDonVi.list" dataTextField="LOC_DV" dataValueField="LOCDV_ID" />
                        </div>
                      </div>
                    </div>
                    <div class=" col-sm-4 col-12">
                      <div class="info-row">
                        <div class="key w90">
                          Loại CT
                        </div>
                        <div class="value">
                          <SelectExt ref="cboLocDCLoaiCT" :disabled="!cboLocDCLoaiCT.enabled" v-model="cboLocDCLoaiCT.value" :dataSource="cboLocDCLoaiCT.list" dataTextField="LOAI_CT" dataValueField="LOAICT_ID" />
                        </div>
                      </div>
                    </div>
                    <div class="value w30 nowrap">
                      <button class="btn btn-second" @click="btnCapNhatDieuChinh_Click"><span class="icon one-key normal"></span> Cập nhật vật tư</button>
                    </div>
                  </div>

                  <div class="table-content">
                    <DataGrid :columns="gridDieuChinhVT.header" ref="gridDSMa_PDA" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="true" :enabledSelectFirstRow="false" :dataSource="gridDieuChinhVT" @selectedItemsChanged="DieuChinhVT_CheckBox" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="tab-pane" :class="{ active: tabSPDA == 3 }" id="bbbg" v-if="layoutControlBBBG.visible">
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
                          <SelectExt ref="cboDuanIMS" :disabled="!cboDuanIMS.enabled" v-model="cboDuanIMS.value" :dataSource="cboDuanIMS.list" dataTextField="ProjectCode" dataValueField="ProjectCode" />
                        </div>
                      </div>
                    </div>
                    <div class=" col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key">
                          HĐ PO
                        </div>
                        <div class="input-group">
                          <div class="input-text">
                            <div class="select-custom">
                              <SelectExt ref="cboHDPOIMS" :disabled="!cboHDPOIMS.enabled" v-model="cboHDPOIMS.value" :dataSource="cboHDPOIMS.list" dataTextField="ContractNumber" dataValueField="ContractInfoID" />
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="table-content col-sm-6 col-12">
                    <DataGrid :columns="gridBienBanIMS.header" ref="gridDSMa_PDA" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :dataSource="gridBienBanIMS.list" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="tab-pane" :class="{ active: tabSPDA == 4 }" id="dsvt" v-if="layoutControlDS_Vattu.visible">
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="box-form">
                  <div class="info-row" style="text-align: right; padding-top: 5px; padding-left: 5px;">
                    <div class="value w30 nowrap">
                      <button class="btn btn-second" @click="btnCapNhatVattu_ThuHoi_Click"><span class="icon one-key normal"></span> Cập nhật vật tư</button>
                    </div>
                  </div>

                  <div class="table-content">
                    <DataGrid :columns="gridDS_VT_THUHOI.header" ref="gridDS_VT_THUHOI" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="true" :enabledSelectFirstRow="false" @selectedItemsChanged="DS_VT_THUHOI_CheckBox" :dataSource="gridDS_VT_THUHOI.list" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api'
export default {
  components: {},
  props: {
    vnghiepvu_id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      tabSPDA: 1,
      view: { valid: true },
      layoutMaPhieu: { enabled: true, visible: false },
      layoutControlDuAn: { enabled: true, visible: false },
      layoutControlDS_Vattu: { enabled: true, visible: false },
      layoutControlDieuChinh: { enabled: true, visible: false },
      layoutControlBBBG: { enabled: true, visible: false },
      cboMaPhieu: { enabled: true, value: null, list: [] },
      cboMa_DV_SD: { enabled: true, value: null, list: [] },
      cboMa_DV_QL: { enabled: true, value: null, list: [] },
      cboLocDCDonVi: {
        enabled: true,
        value: null,
        list: [
          { LOC_DV: '', LOCDV_ID: 0 },
          { LOC_DV: 'Nội bộ', LOCDV_ID: 1 },
          { LOC_DV: 'Khác đơn vị', LOCDV_ID: 2 }
        ]
      },
      cboDuanIMS: { enabled: true, value: null, list: [] },
      cboLocDCLoaiCT: { enabled: true, value: null, list: [] },
      cboHDPOIMS: { enabled: true, value: null, list: [] },
      cboHD_XuatIMS: {
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
      grcMa_PDA: {
        header: [
          // { fieldName: '', headerText: '', template: ChonPDATemplate, textAlign: 'Center', width: 80 },
          { fieldName: 'ProjectCode', headerText: 'Mã dự án', allowFiltering: true },
          { fieldName: 'ProjectName', headerText: 'Tên dự án', allowFiltering: true },
          { fieldName: 'ProjectID', visible: false, isPrimaryKey: true }
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
          [
            // { fieldName: '', headerText: '', template: ChonBBBGTemplate, textAlign: 'Center', width: 80 },
            { fieldName: 'ACCEPTANCEGENERALNAME', headerText: 'Biên bản bàn giao', allowFiltering: true }
          ]
        ],
        list: [],
        value: null
      },
      gridDS_VT_THUHOI: {
        header: [
          [
            { fieldName: 'MAVTHH', headerText: 'Mã VT', allowFiltering: true },
            { fieldName: 'TENVTHH', headerText: 'Tên VT', allowFiltering: true },
            { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true },
            { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true },
            { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true },
            { fieldName: 'IDCHITIET', headerText: 'IDCHITIET', allowFiltering: true },
            { fieldName: 'QR_CODE', headerText: 'QR', allowFiltering: true },
            { fieldName: 'MACSHTCU', headerText: 'CSHT cũ', allowFiltering: true }
          ]
        ],
        list: [],
        value: null
      }
    }
  },
  computed: {},
  async mounted() {
    console.log('mounted')
    await this.init()
  },
  watch: {},
  methods: {
    /**
     * @see: private void cboNghiepVu_EditValueChanged(object sender, EventArgs e)
     */
    async init() {
      this.grcMa_PDA.list = null
      this.cboHDPOIMS.list = null
      this.cboDuanIMS.list = null
      this.gridBienBanIMS.list = null
      this.gridDS_VT_THUHOI.list = null
      this.gridDieuChinhVT.list = null
      // cboIMS_CSHT.ResetText();
      this.cboMa_DV_SD.value = null
      this.cboMa_DV_QL.value = null
      this.cboHD_XuatIMS.value = null
      this.cboMaPhieu.list = null
      this.cboMaPhieu.value = null
      // duan_ims_clone.Clear();
      // bbbgiao.Clear();
      // dieuchuyen_clone.Clear();
      this.loading(true)
      console.log('this.vnghiepvu_id')
      console.log(this.vnghiepvu_id)
      let nghiepvu_ = (await api.ds_ma_muc_dich_theo_muc_dich(this.axios, { mucdich_id: this.vnghiepvu_id })).data.data
      this.loading(false)
      if (nghiepvu_.lenght == 0) return
      let ma_nghiepvu = nghiepvu_[0]

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

        // gridDS_VT_THUHOI.setAllGridView_SpaceToggleCheck(false, true, false)
      } else if (ma_nghiepvu == 'NV.DIEUCHUYEN') {
        this.layoutMaPhieu.visible = true
        this.layoutControlDuAn.visible = false
        this.layoutControlDieuChinh.visible = true
        this.layoutControlBBBG.visible = false
        this.layoutControlDS_Vattu.visible = false
        this.tabSPDA = 2

        // gridDieuChinhVT.setAllGridView_SpaceToggleCheck(false, true, false)
        // var filter_donvi = ChungTu.GET_DATA_SQL(@"select 0 locdv_id, '' loc_dv from dual
        //                                             union all
        //                                             select 1 locdv_id, 'Nội bộ' loc_dv from dual
        //                                             union all
        //                                               select 2 locdv_id, 'Kh·c đơn vị' loc_dv from dual");
        // bangts.HT_DS_GRIDLOOKUP(cboLocDCDonVi, filter_donvi, "LOC_DV", "LOCDV_ID", false, 1, 300, false, false);
      } else {
        this.view.valid = false
        console.log('this.view.valid')

        this.$parent.hide()
        this.$toast.warning('Nghiệp vụ này không có mã dự án!')
      }
    },
    cboMaPhieu_EditValueChanged() {
      this.params.p_Ma_PDA = this.params.p_MaPhieu
    },
    async cboHD_XuatIMS_EditValueChanged(row) {
      this.params.p_HD_XuatIMS = row.PO
    },
    async cboLocDCDonVi_EditValueChanged() {
      if (this.params.p_LocDCDonVi == '0' || this.params.p_LocDCDonVi == null) {
        this.cboLocDCDonVi = []
        this.gridDieuChinhVT = this.dieuchuyen_clone
        return
      }
      this.cboLocDCLoaiCT = await this.getDanhMucLoaiChungTu(this.params.p_LocDCDonVi)
    },
    async cboDuanIMS_EditValueChanged() {
      try {
        this.loading(true)
        if (this.params.p_DuanIMS == null || this.params.p_DuanIMS == '') {
          this.$toast.error('Chưa chọn dự án!')
          return
        }
        let ma_coso_hatang = ''
        if (this.kieugoi_id == 2) {
          let find_csht = await this.getMaCoSoHaTang({ khoId: this.cboDenKho.value })
          if (find_csht != null && find_csht.length > 0) {
            ma_coso_hatang = find_csht[0]
          }
        } else {
          return
        }

        let dt_hdpo = await this.getContractInfo({
          maDuan: this.params.p_DuanIMS,
          maCsht: ma_coso_hatang
        })

        if (dt_hdpo == null || dt_hdpo == '') {
          this.$toast.error('Không có thông tin hợp đồng - PO')
          this.cboHDPOIMS = []
          this.gridBienBanIMS = []
          return
        }
        dt_hdpo.push({
          PO: '',
          ContractName: '',
          SignOffDate: '',
          ContractInfoID: '',
          ParentContractInfo: '',
          ContractNumber: ''
        })
        this.cboHDPOIMS = dt_hdpo
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async cboLocDCLoaiCT_EditValueChanged() {
      if (this.params.p_LocDCLoaiCT == null || this.params.p_LocDCLoaiCT == '') {
        return
      }
      this.gridDieuChinhVT = await this.kiemTraSoLuongQLTS({
        kieu: 0,
        data: this.dieuchuyen_clone,
        maPda: this.params.p_Ma_PDA,
        timKiem: this.params.p_LocDCDonVi,
        loaiCtId: this.params.p_LocDCLoaiCT
      })
    },
    async cboHDPOIMS_EditValueChanged() {
      if (this.params.p_HDPOIMS == '' || this.params.p_HDPOIMS == null) {
        this.gridBienBanIMS = []
        return
      }
      let ma_coso_hatang = ''
      if (this.kieugoi_id == 2) {
        let find_csht = await this.getMaCoSoHaTang({ khoId: this.cboDenKho.value })
        if (find_csht != null && find_csht.length > 0) {
          ma_coso_hatang = find_csht[0]
        }
      } else {
        return
      }
      let HDPOIMSFilter = this.cboHDPOIMS.filter((a) => a.ContractInfoID == this.params.p_HDPOIMS)
      let contractNumber = HDPOIMSFilter.length > 0 ? HDPOIMSFilter[0].ParentContractInfo : ''

      let dt_keys = await this.getAssetByKeys({
        maDuan: this.params.p_DuanIMS,
        contractNumber: contractNumber,
        poNumber: this.params.p_HDPOIMS,
        maCsht: ma_coso_hatang
      })

      if (dt_keys == null || dt_keys == '') {
        this.gridBienBanIMS = []
        return
      }
      this.bbbgiao = dt_keys
      this.gridBienBanIMS = dt_keys
    },
    async v_btnRef_Click() {
      try {
        this.loading(true)
        this.grcMa_PDA = []
        this.cboHDPOIMS = []
        this.cboDuanIMS = []
        this.gridBienBanIMS = []
        this.gridDS_VT_THUHOI = []
        this.duan_ims_clone = []
        this.bbbgiao = []
        this.dieuchuyen_clone = []
        if (this.cboNghiepVu.value == null || this.cboNghiepVu.value == '') {
          this.$toast.error('Chưa chọn nghiệp vụ!')
          return
        }
        let nghiepvu_ = await this.getMaMucDichTheoID({ mucDichId: this.cboNghiepVu.value })

        if (nghiepvu_.length == 0) {
          return
        }

        let ma_nghiepvu = nghiepvu_[0]
        if (ma_nghiepvu == 'NV.DUAN' || ma_nghiepvu == 'NV.XUATIMS' || ma_nghiepvu == 'NV.ACAP' || ma_nghiepvu == 'NV.CHUYENNGUON') {
          let dt = await this.getDSRefIMS({
            mucDichId: this.cboNghiepVu.value,
            kieuGoiId: this.kieugoi_id,
            maPda: this.cboMa_PDA.value,
            khoId: this.cboDenKho.value
          })
          if (dt != null && dt != '') {
            let data = JSON.parse(dt)
            if ('NewDataSet' in data && 'Table' in data.NewDataSet) {
              this.grcMa_PDA = data.NewDataSet.Table
              console.log(this.grcMa_PDA, ' = this.grcMa_PDA')
            } else {
              this.grcMa_PDA = []
            }
          } else {
            this.grcMa_PDA = []
          }
        }
        if (ma_nghiepvu == 'NV.THUHOI' && this.kieugoi_id == 2) {
          if (this.cboKieuPhieu.value == null || this.cboKieuPhieu.value == '') {
            this.$toast.error('Bạn chưa chọn kiểu phiếu !')
            return
          }

          if (this.cboMucDich.value == null || this.cboMucDich.value == '') {
            this.$toast.error('Bạn chưa chọn mục đích !')
            return
          }

          if (this.cboTuKho.value == null || this.cboTuKho.value == '' || this.cboTuKho.value == 0) {
            this.$toast.error('Bạn chưa chọn từ kho !')
            return
          }

          if (this.cboDenKho.value == null || this.cboDenKho.value == '' || this.cboDenKho.value == 0) {
            this.$toast.error('Bạn chưa chọn đến kho !')
            return
          }

          if (this.cboMa_PDA.value == null || this.cboMa_PDA.value == '') {
            this.$toast.error('Bạn chưa nhập số phiếu trên QLTS !')
            return
          }

          let dt = await this.getDSRefIMS({
            mucDichId: this.cboNghiepVu.value,
            kieuGoiId: this.kieugoi_id,
            maPda: this.cboMa_PDA.value,
            khoId: this.cboDenKho.value
          })
          this.gridDS_VT_THUHOI = dt
          console.log(dt, ' = dt')
        }
        if (ma_nghiepvu == 'NV.DIEUCHUYEN') {
          if (this.cboMa_PDA.value == null || this.cboMa_PDA.value == '') {
            this.$toast.error('Bạn chưa nhập số phiếu trên QLTS !')
            return
          }
          this.params.p_LocDCDonVi = 0

          let dt = await this.getDSRefIMS({
            mucDichId: this.cboNghiepVu.value,
            kieuGoiId: this.kieugoi_id,
            maPda: this.cboMa_PDA.value,
            khoId: this.cboDenKho.value
          })
          this.dieuchuyen_clone = dt
          this.gridDieuChinhVT = dt
        }

        if (ma_nghiepvu == 'NV.BBBGIMS') {
          if (this.cboKieuPhieu.value == null || this.cboKieuPhieu.value == '') {
            this.$toast.error('Bạn chưa chọn kiểu phiếu !')
            return
          }

          if (this.cboMucDich.value == null || this.cboMucDich.value == '') {
            this.$toast.error('Bạn chưa chọn mục đích !')
            return
          }

          if (this.cboTuKho.value == null || this.cboTuKho.value == '' || this.cboTuKho.value == 0) {
            this.$toast.error('Bạn chưa chọn từ kho !')
            return
          }

          if (this.cboDenKho.value == null || this.cboDenKho.value == '' || this.cboDenKho.value == 0) {
            this.$toast.error('Bạn chưa chọn đến kho !')
            return
          }
          if (this.kieugoi_id == 2) {
            let find_csht = await this.getMaCoSoHaTang({ khoId: this.cboDenKho.value })
            if (find_csht.length == 0) {
              this.$toast.error('Kho đến không có mã CSHT ! Hãy kiểm tra lại !')
              return
            }
          }
          let dt = await this.getDSRefIMS({
            mucDichId: this.cboNghiepVu.value,
            kieuGoiId: this.kieugoi_id,
            maPda: this.cboMa_PDA.value,
            khoId: this.cboDenKho.value
          })
          if (dt != null && dt != '') {
            let data = JSON.parse(dt)
            if ('NewDataSet' in data && 'Table' in data.NewDataSet) {
              this.cboDuanIMS = data.NewDataSet.Table
              console.log(this.cboDuanIMS, ' = this.grcMa_PDA')
            } else {
              this.cboDuanIMS = []
            }
          } else {
            this.cboDuanIMS = []
          }
          this.duan_ims_clone = this.cboDuanIMS
        }
      } catch (e) {
        this.$toast.error('Đã có lỗi xảy ra vui lòng thử lại!')
      } finally {
        this.loading(false)
      }
    },
    async cboMaPhieu_ButtonClick() {
      try {
        this.loading(true)
        if (this.cboMucDich.value == null || this.cboMucDich.value == '') {
          this.$toast.error('Chưa chọn mục đích!')
          return
        }
        let data = await this.getDSPhieuNV(this.cboMucDich.value)
        if (data == 'null') {
          this.cboMaPhieu = []
        } else {
          this.cboMaPhieu = JSON.parse(data)
        }
      } catch (e) {
        this.cboMaPhieu = []
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    }
  },
  created() {},
  destroyed() {}
}
</script>
<style scoped></style>
