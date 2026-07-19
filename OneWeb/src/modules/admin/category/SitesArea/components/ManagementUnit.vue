<template>
<div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title">Đơn vị quản lý</div>
                    <div class="close -ap icon-close" data-dismiss="modal" @click="$emit('form-close')">
                    </div>
                </div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a @click="tsbGhiLai_Click">
                                <span class="icon one-save"></span> Ghi lại
                            </a>
                        </li>
                        <li>
                            <a @click="tsbHuy_Click">
                                <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Huỷ
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin đơn vị</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Mã KV</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="txtMaKV.Text">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tên KV</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="txtTenKV.Text">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Loại đơn vị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <multi-column-combo-box 
                                      :options="cboLoaiDvi.DataSource" 
                                      :keyField="'loaidv_id'" 
                                      :valueField="'ten_loaidv'" 
                                      :columnOptions="cboLoaiDvi.columnOptions"
                                      v-model="cboLoaiDvi.EditValue" 
                                      @change="cboLoaiDvi_EditValueChanged"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-move-select-table">
                        <div class="box-col box-form">

                            <div class="legend-title">Danh sách đơn vị đã gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid ref="gridAssigned" :dataSource="gridDonvi_DaGan" :allowSorting='true'
                                :allowPaging='true' :pageSettings='pageSettings' :allowFiltering='true'
                                :selectionSettings='selectionOptions'>
                                <e-columns>
                                    <e-column type='checkbox' width='50'></e-column>
                                    <e-column type='text' field='ma_dvi' headerText='Mã đơn vị'></e-column>
                                    <e-column type='text' field='ten_dvi' headerText='Tên đơn vị'></e-column>
                                </e-columns>
                            </ejs-grid>
                            </div>
                            </div>
                        <div class="actions">
                            <button class="btn" @click="transferToUnassign(false)">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button class="btn" @click="transferToUnassign(true)">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button class="btn" @click="transferToAssign(false)">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button class="btn" @click="transferToAssign(true)">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">

                            <div class="legend-title">Danh sách đơn vị chưa gán</div>
                            <div class="table-content" style="height: 200px">
                                <ejs-grid ref="gridUnassigned" :dataSource="gridDonvi_ChuaGan" :allowSorting='true'
                                :allowPaging='true' :pageSettings='pageSettings' :allowFiltering='true'
                                :selectionSettings='selectionOptions'>
                                <e-columns>
                                    <e-column type='checkbox' width='50'></e-column>
                                    <e-column type='text' field='ma_dvi' headerText='Mã đơn vị'></e-column>
                                    <e-column type='text' field='ten_dvi' headerText='Tên đơn vị'></e-column>
                                </e-columns>
                            </ejs-grid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
</template>
<script>
import gridView from "@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue"
import treegridView from "@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue"
import breadcrumb from "@/components/breadcrumb"
import moment from "moment"
import DatePicker from "vue2-datepicker"
import "vue2-datepicker/locale/vi.js"
import "vue2-datepicker/index.css"
import { Filter, Page } from '@syncfusion/ej2-vue-grids'
import api from '@/modules/admin/category/SitesArea/SitesAreaAPI.js'
import MultiColumnComboBox from "../components/MultiColumnComboBox.vue"
export default {
  name: "ManagementUnit",
  props: ['in_khuvuc_id', 'ma_kv', 'ten_kv'],
  components: {
    breadcrumb,
    gridView,
    treegridView,
    moment,
    DatePicker,
    MultiColumnComboBox
  },
  data () {
    return {
      pageSettings: { pageSize: 5 },
      selectionOptions: { type: 'Multiple', checkBoxOnly: true },
      tsbGhiLai: { Enabled: true },
      tsbClose: { Enabled: true },
      tsbHuy: { Enabled: true },
      gridDsDonvi_DaGan: { DataSource: [] },
      gridDsDonvi_ChuaGan: { DataSource: [] },
      gridviewDSDaGan: [],
      gridviewDSChuaGan: [],
      gridView1: [],
      txtTenKV: { Text: '', Enabled: true },
      txtMaKV: { Text: '', Enabled: true },
      btnPreviewAll: { Enabled: true },
      btnPreview: { Enabled: true },
      btnNextAll: { Enabled: true },
      btnNext: { Enabled: true },
      cboLoaiDvi: { DataSource: [], EditValue: null,
        columnOptions: [
          {
            id: 1,
            type: 'text',
            field: 'loaidv_id',
            headerText: 'Mã loại DV'
          },
          {
            id: 2,
            type: 'text',
            field: 'ten_loaidv',
            headerText: 'Tên loại DV'
          }
        ] 
      },
      // ------------------
      khuvuc_id: null,
      dsList: [],
      dtList: [],
      dtListDaGan: [],
      dtListChuaGan: [],
      dsdonvi_dg_id: [],
      dsKhuVuc_DV: null,
      KhuVucKM: null,
      bangts: null,
      ttnd: null
    }
  },
  watch: {},
  created () {
  },
  computed: {
    gridDonvi_DaGan () {
      return this.gridDsDonvi_DaGan.DataSource
    },
    gridDonvi_ChuaGan () {
      return this.gridDsDonvi_ChuaGan.DataSource
    }
  },
  async mounted () {
    this.khuvuc_id = this.in_khuvuc_id
    this.txtMaKV.Text = this.ma_kv
    this.txtTenKV.Text = this.ten_kv

    this.cboLoaiDvi.DataSource = await api.sp_lay_dulieu_theo_dieukien('admin', 'loai_dvi', 'loaidv_id', 'ten_loaidv', '', 'loaidv_id')
    this.cboLoaiDvi.EditValue = this.cboLoaiDvi.DataSource[0].loaidv_id
    this.HienThiTTDonviKhuVuc()
  },
  methods: {
    async HienThiTTDonviKhuVuc () {
      this.dtList = []

      try {
        // VinhNH CR của HNI IT360-50247
        if (this.ttnd && this.ttnd.tentat && this.ttnd.tentat === "HNI") {
          this.dtListChuaGan = await api.Lay_ds_khuvuc_donvi_tructhuoc(0, this.khuvuc_id, parseInt(this.cboLoaiDvi.EditValue.toString()))
        } else {
          this.dtListChuaGan = await api.Lay_ds_khuvuc_donvi(0, this.khuvuc_id, parseInt(this.cboLoaiDvi.EditValue.toString()))
        }

        this.dtListChuaGan = this.dtListChuaGan.map(item => {
          return {
            ...item,
            chon: false
          }
        })

        this.gridDsDonvi_ChuaGan.DataSource = this.dtListChuaGan
      } catch (e) {
        this.gridDsDonvi_ChuaGan.DataSource = []
      }

      try {
        this.dtListDaGan = await api.Lay_ds_khuvuc_donvi(1, this.khuvuc_id, parseInt(this.cboLoaiDvi.EditValue.toString()))
        this.dtListDaGan = this.dtListDaGan.map(item => {
          return {
            ...item,
            chon: false
          }
        })
        this.gridDsDonvi_DaGan.DataSource = this.dtListDaGan
      } catch (e) {
        this.gridDsDonvi_DaGan.DataSource = []
      }
    },
    cboLoaiDvi_EditValueChanged () {
      this.dtListChuaGan = []
      this.dtListDaGan = []
      this.HienThiTTDonviKhuVuc()
    },
    tsbGhiLai_Click () {
      this.Capnhat()
    },
    async Capnhat () {
      let i = 0
      let j = 0
      // Thực hiện insert dữ liệu đã gạt sang grid Danh sách đã gán /khuvuc_diadanh/fn_khuvuc_dv_them
      while (i < this.gridDsDonvi_DaGan.DataSource.length) {
        if (parseInt(this.gridDsDonvi_DaGan.DataSource[i].flag_insert) === 1) {
          try {
            await this.Taodulieu(i, 1)
            api.fn_khuvuc_dv_them(this.dsKhuVuc_DV)
            delete this.gridDsDonvi_DaGan.DataSource[i].flag_insert
          } catch (e) { }
        }
        i++
      }
      // Thực hiện insert dữ liệu đã gạt sang grid Danh sách đã gán
      while (j < this.gridDsDonvi_ChuaGan.DataSource.length) {
        if (parseInt(this.gridDsDonvi_ChuaGan.DataSource[j].flag_delete) === 1) {
          api.DeleData(this.khuvuc_id, parseInt(this.cboLoaiDvi.EditValue.toString()), parseInt(this.gridDsDonvi_ChuaGan.DataSource[j].donvi_id.toString()))
          delete this.gridDsDonvi_ChuaGan.DataSource[i].flag_delete
        }
        j++
      }
      this.$toast.success("Cập nhật thành công!.")
    },
    async Taodulieu (i, kieu) {
      // kieu =1: bên đã gán
      // kieu=0: bên chưa gán
      this.dsKhuVuc_DV = null
      const datarow = {
        khuvuc_id: this.khuvuc_id,
        loaidv_id: parseInt(this.cboLoaiDvi.EditValue.toString()),
        donvi_id: ''
      }
      if (kieu === 0) { datarow.donvi_id = parseInt(this.gridDsDonvi_ChuaGan.DataSource[i].donvi_id.toString()) } else { datarow.donvi_id = parseInt(this.gridDsDonvi_DaGan.DataSource[i].donvi_id.toString()) }

      this.dsKhuVuc_DV = datarow
    },
    tsbHuy_Click () {
      this.HienThiTTDonviKhuVuc()
    },
    transferToAssign: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.gridDsDonvi_DaGan.DataSource = [...this.gridDsDonvi_ChuaGan.DataSource.map(item => {
            if (item.flag_delete) {
              delete item.flag_delete
              return item
            }
            return {
              ...item,
              flag_insert: 1
            }
          }), ...this.gridDsDonvi_DaGan.DataSource]
          this.gridDsDonvi_ChuaGan.DataSource = []
        } else {
          const data = this.$refs.gridUnassigned.getSelectedRecords()
          if (data && data.length > 0) {
            this.gridDsDonvi_ChuaGan.DataSource = this.gridDsDonvi_ChuaGan.DataSource.filter(item => !data.includes(item))
            this.gridDsDonvi_DaGan.DataSource = [...data.map(item => {
              if (item.flag_delete) {
                delete item.flag_delete
                return item
              }
              return {
                ...item,
                flag_insert: 1
              }
            }), ...this.gridDsDonvi_DaGan.DataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToUnassign: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.gridDsDonvi_ChuaGan.DataSource = [...this.gridDsDonvi_DaGan.DataSource.map(item => {
            if (item.flag_insert) {
              delete item.flag_insert
              return item
            }
            return {
              ...item,
              flag_delete: 1
            }
          }), ...this.gridDsDonvi_ChuaGan.DataSource]
          this.gridDsDonvi_DaGan.DataSource = []
        } else {
          const data = this.$refs.gridAssigned.getSelectedRecords()
          if (data && data.length > 0) {
            this.gridDsDonvi_DaGan.DataSource = this.gridDsDonvi_DaGan.DataSource.filter(item => !data.includes(item))
            this.gridDsDonvi_ChuaGan.DataSource = [...data.map(item => {
              if (item.flag_insert) {
                delete item.flag_insert
                return item
              }
              return {
                ...item,
                flag_delete: 1
              }
            }), ...this.gridDsDonvi_ChuaGan.DataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    }
  },
  provide: {
    grid: [Filter, Page]
  }
}
</script>
