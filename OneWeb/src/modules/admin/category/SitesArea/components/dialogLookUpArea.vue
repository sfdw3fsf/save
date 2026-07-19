<template>
    <b-modal
      size="xl"
      dialog-class="dialog-content"
      body-class="body-content"
      content-class="popup-box"
      v-model="visible"
      hide-footer
      hide-header
    >
      <div style="margin: -1rem" v-if="visible">
       <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">Tra cứu khu vực theo địa danh</div>
            <div class="close -ap icon-close" data-dismiss="modal" @click="show(false)">
            </div>
          </div>
        </div>
        <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a @click="tsbtnTraCuu_Click">
                                <span class="icon one-file-search"></span> Tra cứu
                            </a>
                        </li>
                        <li>
                            <a @click="tsbtnChapNhan_Click">
                                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                            </a>
                        </li>
                    </ul>
        </div>
        <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin địa danh</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tỉnh/Tp</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <vue-select v-model="cboTinhThanh.EditValue"
                                              :required="false" :labelWidth="'0'" :disable="false"
                                              :options="cboTinhThanh.DataSource" :valueField="'TINH_ID'"
                                              :labelField="'TENTINH'" :checkbox="false" @input="cboTinhThanh_EditValueChanged"></vue-select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w86">Quận/Huyện</div>
                                    <div class="value dropdown">
                                    <multi-column-combo-box :options="danhSachQuan" :keyField="'QUAN_ID'" :valueField="'TEN_QUAN'" v-model="cboQuan.EditValue" @change="cboQuan_EditValueChanged"/>
                                </div>
                                </div>
                                <div class="info-row pt-1">
                                    <div class="key w86">Phường/Xã</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <multi-column-combo-box 
                                              :options="cboPhuong.DataSource" 
                                              :keyField="'PHUONG_ID'" 
                                              :valueField="'TEN_PHUONG'" 
                                              :columnOptions="cboPhuong.columnOptions"
                                              v-model="cboPhuong.EditValue" 
                                              @change="cboPhuong_EditValueChanged"
                                            />
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Đường/Phố</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" :disabled='!chkPho.Enabled' v-model="chkPho.Checked"/>
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                           <vue-select v-model="cboDuongPho.EditValue"
                                              :required="false" :labelWidth="'0'" :disable="!chkPho.Checked"
                                              :options="cboDuongPho.DataSource" :valueField="'pho_id'"
                                              :labelField="'ten_pho'" :checkbox="false"></vue-select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w80">Ngõ/Ấp</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" :disabled='!chkAp.Enabled' v-model="chkAp.Checked"/>
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                           <vue-select v-model="cboNgo.EditValue"
                                              :required="false" :labelWidth="'0'" :disable="!chkAp.Checked"
                                              :options="cboNgo.DataSource" :valueField="'pho_id'"
                                              :labelField="'ten_pho'" :checkbox="false"></vue-select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w80">Khu</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" :disabled='!chkKhu.Enabled' v-model="chkKhu.Checked"/>
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <vue-select v-model="cboKhu.EditValue"
                                              :required="false" :labelWidth="'0'" :disable="!chkKhu.Checked"
                                              :options="cboKhu.DataSource" :valueField="'pho_id'"
                                              :labelField="'ten_pho'" :checkbox="false"></vue-select>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w80">Đặc điểm</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" :disabled='!chkDacDiem.Enabled' v-model="chkDacDiem.Checked"/>
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                           <vue-select v-model="cboDacDiem.EditValue"
                                              :required="false" :labelWidth="'0'" :disable="!chkDacDiem.Checked"
                                              :options="cboDacDiem.DataSource" :valueField="'DACDIEM_ID'"
                                              :labelField="'DACDIEM'" :checkbox="false"></vue-select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách khu vực</div>
                        <div class="table-content">
                            <DataGrid
                              ref="dgvDanhSachKhuVuc"
                              :columns="columnDSKV"
                              :dataSource="danhSachKhuVuc"
                              :enable-paging-server="false"
                              :allowPaging="true"
                              :showFilter="true"
                            >
                            </DataGrid>
                         </div>
                     </div>
        </div>
      </div>
  </b-modal>
</template>
<script>
import gridView from "@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue"
import treegridView from "@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue"
import breadcrumb from "@/components/breadcrumb"
import moment from "moment"
import DatePicker from "vue2-datepicker"
import "vue2-datepicker/locale/vi.js"
import "vue2-datepicker/index.css"
import { NHOMPHO } from '@/constants.js'
import { Filter, Page } from '@syncfusion/ej2-vue-grids'
import api from '@/modules/admin/category/SitesArea/SitesAreaAPI.js'
import MultiColumnComboBox from "../components/MultiColumnComboBox.vue"

export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    moment,
    DatePicker,
    MultiColumnComboBox
  },
  data: () => ({
    pageSettings: { pageSize: 5 },
    selectionOptions: { type: 'Multiple', checkBoxOnly: true },
    visible: false,
    tsbtnThoat: { Enabled: true },
    tsbtnTraCuu: { Enabled: true },
    tsbtnChapNhan: { Enabled: true },
    dgvKhuVuc: { DataSource: [], Selection: null },
    gridviewKhuVuc: { DataSource: [], Selection: null },
    cboPhuong: { DataSource: [], EditValue: null, Enabled: true ,
      columnOptions: [
        {
            id: 1,
            type: 'text',
            field: 'TEN_PHUONG',
            headerText: 'Tên phường/xã'
          },
          {
            id: 2,
            type: 'text',
            field: 'PHUONG_ID',
            headerText: 'Mã phường'
          }
      ]
    },
    columnDSKV: [
      {
        fieldName: 'ma_kv',
        headerText: 'Mã khu vực'
      },
      {
        fieldName: 'ten_kv',
        headerText: 'Tên khu vực'
      },
      {
        fieldName: 'dacdiem',
        headerText: 'Đặc điểm'
      }
    ],
    cboTinhThanh: { DataSource: [], EditValue: null, Enabled: true },
    cboQuan: { DataSource: [], EditValue: 4, Enabled: true },
    cboKhu: { DataSource: [], EditValue: null, Enabled: true },
    cboNgo: { DataSource: [], EditValue: null, Enabled: true },
    cboDuongPho: { DataSource: [], EditValue: null, Enabled: true },
    cboDacDiem: { DataSource: [], EditValue: null, Enabled: true },
    chkKhu: { Checked: false, Enabled: true },
    chkAp: { Checked: false, Enabled: true },
    chkPho: { Checked: false, Enabled: true },
    chkDacDiem: { Checked: false, Enabled: true },
    // -------------------------------
    khuvuc_id: null,
    chapnhan: false,
    quan_id: null,
    phuong_id: null,
    pho_id: null,
    ap_id: null,
    khu_id: null,
    dacdiem_id: null,
    ktra_load: false,
    bangts: null,
    tt_nd: null
  }),
  computed: {
    danhSachQuan () {
      return this.cboQuan.DataSource
    },
    selectedQuan () {
      const quan = this.cboQuan.DataSource.find(item => item.QUAN_ID === this.cboQuan.EditValue)
      return quan ? quan.TEN_QUAN : ''
    },
    danhSachKhuVuc () {
      return this.dgvKhuVuc.DataSource
    }
  },
  async mounted () {
    this.loadData()
  },
  methods: {
    show (visible) {
      this.visible = visible
    },
    async loadData () {
      this.tt_nd = await this.$root.token.getThongTinNguoiDung()

      api.layTinh_diadanh().then(ds => {
        this.cboTinhThanh.DataSource = ds
        this.cboTinhThanh.EditValue =
          this.tt_nd && this.tt_nd.phanvung_id ? this.tt_nd.phanvung_id : null
        this.cboTinhThanh_EditValueChanged()
      })

      this.ktra_load = false
    },
    async cboTinhThanh_EditValueChanged () {
      if (this.cboTinhThanh.DataSource.length > 0) {
        if (!this.cboTinhThanh.EditValue) return
        const tinh_id = Number(this.cboTinhThanh.EditValue)
        if (tinh_id != 0) {
          this.cboQuan.DataSource = await api.lay_ds_quan(tinh_id)
          this.cboQuan.EditValue = this.cboQuan.DataSource[0] ? this.cboQuan.DataSource[0].QUAN_ID : null
          this.cboQuan_EditValueChanged()
        }
      }
    },
    async cboQuan_EditValueChanged () {
      this.cboDuongPho.DataSource = []
      this.cboNgo.DataSource = []
      this.cboKhu.DataSource = []
      if (this.cboQuan.EditValue && this.cboQuan.DataSource.length > 0) {
        const quan_id = parseInt(this.cboQuan.EditValue)
        if (quan_id) {
          this.cboPhuong.DataSource = await api.lay_ds_phuong(quan_id)
          this.cboPhuong.EditValue = this.cboPhuong.DataSource[0] ? this.cboPhuong.DataSource[0].PHUONG_ID : null
          this.cboPhuong_EditValueChanged()
        } else { this.cboPhuong.DataSource = [] }
      } else {
        this.cboPhuong.DataSource = []
      }
    },
    async cboPhuong_EditValueChanged () {
      if (this.cboPhuong.DataSource.length !== 0) {
        if (!this.cboPhuong.EditValue) { return }
        const phuong_id = parseInt(this.cboPhuong.EditValue.toString())

        if (phuong_id) {
          this.cboDuongPho.DataSource = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.DUONGPHO)
          if (this.chkPho.Checked) {
            this.cboDuongPho.EditValue = this.cboDuongPho.DataSource[0] ? this.cboDuongPho.DataSource[0].pho_id : null
            if (this.cboDuongPho.DataSource.length == 0) { this.chkPho.Checked = false }
          }

          this.cboNgo.DataSource = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.AP)
          if (this.chkAp.Checked) {
            this.cboNgo.EditValue = this.cboNgo.DataSource[0] ? this.cboNgo.DataSource[0].pho_id : null
            if (this.cboNgo.DataSource.length === 0) { this.chkAp.Checked = false }
          }

          this.cboKhu.DataSource = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.KHU)
          if (this.chkKhu.Checked) {
            this.cboKhu.EditValue = this.cboKhu.DataSource[0] ? this.cboKhu.DataSource[0].pho_id : null
            if (this.cboKhu.DataSource.length === 0) { this.chkKhu.Checked = false }
          }

          this.cboDacDiem.DataSource = await api.Lay_DS_DacDiem(parseInt(this.cboDuongPho.EditValue), phuong_id)
          if (this.chkDacDiem.Checked) {
            this.cboDacDiem.EditValue = this.cboDacDiem.DataSource[0] ? this.cboDacDiem.DataSource[0].DACDIEM_ID : null
            if (this.cboDacDiem.DataSource.length === 0) { this.chkDacDiem.Checked = false }
          }
        }
      } else {
        this.cboDuongPho.DataSource = []
        this.cboNgo.DataSource = []
        this.cboKhu.DataSource = []
      }
    },
    tsbtnTraCuu_Click () {
      this.HienThi_DanhSach_KhuVuc()
    },
    async HienThi_DanhSach_KhuVuc () {
      this.phuong_id = parseInt(this.cboPhuong.EditValue)
      this.pho_id = parseInt(this.cboDuongPho.EditValue)
      if (this.chkDacDiem.Checked) {
        this.dacdiem_id = parseInt(this.cboDacDiem.EditValue)
      } else {
        this.dacdiem_id = 0
      }
      this.dgvKhuVuc.DataSource = await api.Lay_DS_KhuVuc_PX(this.phuong_id, this.pho_id, this.ap_id, this.khu_id, this.dacdiem_id)
    },
    tsbtnChapNhan_Click () {
      this.chapnhan = true
      this.$emit('onAccept', this.chapnhan, this.$refs.dgvDanhSachKhuVuc.getSelectedRecords());
      this.show(false)
    }
  },
  watch: {
    chkPho: {
      handler: function (newValue) {
        if (newValue.Checked) {
          this.cboDuongPho.EditValue = this.cboDuongPho.DataSource[0] ? this.cboDuongPho.DataSource[0].pho_id : null
        } else {
          this.cboDuongPho.EditValue = null
        }
      },
      deep: true
    },
    chkAp: {
      handler: function (newValue) {
        if (newValue.Checked) {
          this.cboNgo.EditValue = this.cboNgo.DataSource[0] ? this.cboNgo.DataSource[0].pho_id : null
        } else {
          this.cboNgo.EditValue = null
        }
      },
      deep: true
    },
    chkKhu: {
      handler: function (newValue) {
        if (newValue.Checked) {
          this.cboKhu.EditValue = this.cboKhu.DataSource[0] ? this.cboKhu.DataSource[0].pho_id : null
        } else {
          this.cboKhu.EditValue = null
        }
      },
      deep: true
    },
    chkDacDiem: {
      handler: function (newValue) {
        if (newValue.Checked) {
          this.cboDacDiem.EditValue = this.cboDacDiem.DataSource[0] ? this.cboDacDiem.DataSource[0].DACDIEM_ID : null
        } else {
          this.cboDacDiem.EditValue = null
        }
      },
      deep: true
    }
  },
  provide: {
    grid: [Filter, Page]
  }
}
</script>
