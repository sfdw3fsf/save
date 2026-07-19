<template>
  <div :class="{ 'modal-content popup-box': type == 'modal' }">
    <breadcrumb v-if="type == 'view'" :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li v-for="(item, index) in menus" :class="{ disable: !item.enabled, 'btn-action': item.enabled }">
          <a @click.prevent="onMenuClicked(item)"><span :class="`${item.icon} icon`"></span>{{ item.name }}</a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Tìm kiếm</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="cboDonVi.enabled" />
                  <span class="name">Đơn vị</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!cboDonVi.enabled" v-model="cboDonVi.value" :dataSource="cboDonVi.list" dataTextField="ten_dv" dataValueField="donvi_id" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="cboLoaiNV.enabled" />
                  <span class="name">Loại NV</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!cboLoaiNV.enabled" v-model="cboLoaiNV.value" :dataSource="cboLoaiNV.list" dataTextField="TEN" dataValueField="LOAINV_ID" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w110">
                Mã NV
              </div>
              <div class="value">
                <input :disabled="!txtMaNV.enabled" type="text" class="form-control highlight" style="font-weight: bold" v-model="txtMaNV.value" @keyup.enter="onTimKiemSubmitted" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="cboLoaiKV.enabled" />
                  <span class="name">Loại khu vực</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!cboLoaiKV.enabled" v-model="cboLoaiKV.value" :dataSource="cboLoaiKV.list" dataTextField="LOAI_KV" dataValueField="LOAIKV_ID" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách nhân viên</div>
        <div class="table-content">
          <DataGrid v-bind:columns="gridNhanVien.header" v-bind:dataSource="gridNhanVien.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="gridNhanVien" @selectedRowChanged="gridNhanVienOnSelectedRowChanged" />
        </div>
      </div>
      <div class="box-move-select-table">
        <div class="box-col box-form">
          <div class="legend-title">Danh sách nghề nghiệp đã gán</div>
          <div class="grid-ext grid-ext-freeze">
            <DataGrid v-bind:columns="gridNgheNghiepDaGan.header" v-bind:dataSource="gridNgheNghiepDaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridNgheNghiepDaGan" />
          </div>
        </div>
        <div class="actions">
          <button class="btn" @click="moveToRight">
            <span class="-ap icon-chevron-thin-right"></span>
          </button>
          <button class="btn" @click="moveToLeft">
            <span class="-ap icon-chevron-thin-left"></span>
          </button>
        </div>
        <div class="box-col box-form">
          <div class="legend-title">Danh sách nghề nghiệp chưa gán</div>
          <div class="grid-ext grid-ext-freeze">
            <DataGrid v-bind:columns="gridNgheNghiepChuaGan.header" v-bind:dataSource="gridNgheNghiepChuaGan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" ref="gridNgheNghiepChuaGan" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'

export default {
  components: { breadcrumb },
  props: {
    type: {
      type: String,
      default: 'view'
    },
    vhuonggiao_id: {
      type: [String, Number],
      required: false,
      default: null
    }
  },
  async mounted() {
    await this.init()
  },
  data() {
    return {
      header: {
        title: 'Gán nhân viên với ngành nghề',
        list: [
          { name: '<<<', link: { name: 'Ui.cards' } },
        ]
      },
      menus: [
        {
          id: 'tim_kiem',
          name: 'Tìm kiếm',
          icon: 'icon one-search',
          enabled: true
        },
        {
          id: 'ghi_lai',
          name: 'Ghi lại',
          icon: 'icon one-save',
          enabled: true
        }
      ],
      txtMaNV: {
        enabled: true,
        value: null
      },
      cboDonVi: {
        enabled: false,
        list: [],
        value: null
      },
      cboLoaiNV: {
        enabled: false,
        list: [],
        value: null
      },
      cboLoaiKV: {
        enabled: false,
        list: [],
        value: null
      },
      gridNhanVien: {
        header: [
          { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, width: 200 },
          { fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowFiltering: true, allowHtml: true, width: 200 },
          { fieldName: 'ten', headerText: 'Loại nhân viên', allowFiltering: true, allowHtml: true, width: 200 }
        ],
        list: [],
        value: null
      },
      gridNgheNghiepDaGan: {
        header: [
          { fieldName: 'tennganhnghe', headerText: 'Nghành nghề', allowFiltering: true, width: 150 },
          { fieldName: 'manganh', headerText: 'Mã nghành nghề', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridNgheNghiepChuaGan: {
        header: [
          { fieldName: 'tennganhnghe', headerText: 'Nghành nghề', allowFiltering: true, width: 150 },
          { fieldName: 'manganh', headerText: 'Mã nghành nghề', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  watch: {
    async 'cboDonVi.enabled'(val) {
      if (!val) {
        this.cboDonVi.value = null
      }
    },
    async 'cboLoaiNV.enabled'(val) {
      if (!val) {
        this.cboLoaiNV.value = null
      }
    },
    async 'cboLoaiKV.enabled'(val) {
      if (!val) {
        this.cboLoaiKV.value = null
      }
    },
    async 'gridNhanVien.value'(val) {
      if (val) {
        let dataNgheNghiepDaGan = await api.sp_nhanvien_nganhnghe_ds_dagan(this.axios, {
          p_nhanvien_id: val.nhanvien_id
        })
        this.gridNgheNghiepDaGan.list = dataNgheNghiepDaGan.data.data && dataNgheNghiepDaGan.data.data.length > 0 ? dataNgheNghiepDaGan.data.data : []
        let dataNgheNghiepChuaGan = await api.sp_nhanvien_nganhnghe_ds_chuagan(this.axios, {
          p_nhanvien_id: val.nhanvien_id
        })
        this.gridNgheNghiepChuaGan.list = dataNgheNghiepChuaGan.data.data && dataNgheNghiepChuaGan.data.data.length > 0 ? dataNgheNghiepChuaGan.data.data : []
      } else {
        this.gridNgheNghiepDaGan.list = []
        this.gridNgheNghiepChuaGan.list = []
      }
    },
  },
  computed: {},
  methods: {
    async init() {
      await this.loadData()
      if (this.type == 'view') {
        this.initView()
      }
      if (this.type == 'modal') {
        this.initModal()
      }
    },
    async initView() {},
    async initModal() {},
    async loadData() {
      this.loading(true)
      let dataLoaiDonVi = await api.sp_ht_donvi_loaidonvi(this.axios, {
        donvi_id: 0,
        loaidv_id: 2
      })
      this.cboDonVi.list = dataLoaiDonVi.data.data && dataLoaiDonVi.data.data.length > 0 ? dataLoaiDonVi.data.data : []
      let dataLoaiNhanVien = await api.ADMIN_LOAI_NV(this.axios, {
        p_is_insert: 1,
        p_data: {
          LOAITB_ID: 120,
          PHANLOAI: 'VNPT TEST',
          PHANLOAI_ID: 0
        }
      })
      this.cboLoaiNV.list = dataLoaiNhanVien.data.data && dataLoaiNhanVien.data.data.length > 0 ? dataLoaiNhanVien.data.data : []
      let dataLoaiKhuVuc = await api.CSS_LOAI_KV(this.axios, {
        p_phanloai_id: 50
      })
      this.cboLoaiKV.list = dataLoaiKhuVuc.data.data && dataLoaiKhuVuc.data.data.length > 0 ? dataLoaiKhuVuc.data.data : []
      this.loading(false)
    },
    onMenuClicked(action) {
      switch (action.id) {
        case 'tim_kiem':
          this.onBtnTimKiemOnClicked()
          break
        case 'ghi_lai':
          this.onBtnGhiLaiOnClicked()
          break
      }
    },
    async onBtnTimKiemOnClicked() {
      await this.onTimKiemSubmitted()
    },
    async onBtnGhiLaiOnClicked() {
      this.loading(true)
      let data = await api.fn_nhanvien_nganhnghe_update(this.axios, {
        p_data: this.gridNgheNghiepDaGan.list.map((e) => {
          return {
            NGANHNGHE_ID: e.nganhnghe_id,
            NHANVIEN_ID: this.gridNhanVien.value.nhanvien_id
          }
        }),
        p_nhanvien_id: this.gridNhanVien.value.nhanvien_id
      })
      this.loading(false)
      if (data.data.data && data.data.data == 1) {
        this.$toast.success(`${data.data.message}`)
      } else {
        this.$toast.error(`${data.data.message}`)
      }
    },
    async onTimKiemSubmitted() {
      this.gridNhanVien.value = null
      this.loading(true)
      let data = await api.sp_ds_nhanvien_nganhnghe(this.axios, {
        p_donvi_id: this.cboDonVi.value,
        p_loaikhuvuc: this.cboLoaiKV.value,
        p_loainv_id: this.cboLoaiNV.value,
        p_ma_nv: this.txtMaNV.value
      })
      this.loading(false)
      this.gridNhanVien.list = data.data.data && data.data.data.length > 0 ? data.data.data : []
    },
    gridNhanVienOnSelectedRowChanged(e) {
      this.gridNhanVien.value = e
    },
    moveToRight() {
      const listNganhNghe = this.$refs.gridNgheNghiepDaGan.getSelectedRecords()
      if (listNganhNghe.length > 0) {
        this.gridNgheNghiepDaGan.list = this.gridNgheNghiepDaGan.list.filter((e) => listNganhNghe.find((x) => x.nganhnghe_id == e.nganhnghe_id) == null)
        this.gridNgheNghiepChuaGan.list.unshift(...listNganhNghe)
      }
    },
    moveToLeft() {
      const listNganhNghe = this.$refs.gridNgheNghiepChuaGan.getSelectedRecords()
      if (listNganhNghe.length > 0) {
        this.gridNgheNghiepChuaGan.list = this.gridNgheNghiepChuaGan.list.filter((e) => listNganhNghe.find((x) => x.nganhnghe_id == e.nganhnghe_id) == null)
        this.gridNgheNghiepDaGan.list.unshift(...listNganhNghe)
      }
    }
  }
}
</script>
<style scoped></style>
