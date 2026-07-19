<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Phân quyền tham số mặc định - đơn vị</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Quản lý người dùng</a></li>
        <li class="breadcrumb-item active">Phân quyền tham số mặc định - Đơn vị</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:void(0);" @click.prevent="CapNhat"> <span class="icon one-save"></span>Ghi lại </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Danh sách tham số mặc định</div>
        <DataGrid
          v-bind:columns="DsThamSo.headers2"
          v-bind:dataSource="DsThamSo.data"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          :allowPaging="true"
          dataKeyField="ma_ts"
          :showFilter="true"
          ref="refDsThamSo"
          :enablePersistence="false"
          @actionBegin="actionBegin_ThamSo"
          @rowClicked="ChangedParam"
        >
        </DataGrid>
      </div>
      <div class="box-move-select-table">
        <div class="box-col box-form">
          <div class="legend-title">Danh sách đơn vị đã gán</div>
          <DataGrid
            v-bind:columns="DsDonViDaGan.headers2"
            v-bind:dataSource="DsDonViDaGan.data"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            ref="refDsDonViDaGan"
            :showFilter="true"
            :enablePersistence="false"
            :enabledSelectFirstRow="false"
            @actionBegin="actionBegin_DaGan"
            @selectedItemsChanged="select_selectedItemsChangedDagan"
            @rowClicked="rowClicked_DaGan"
          >
          </DataGrid>
          <!-- ref="refDsDonViDaGan" -->
        </div>
        <div class="actions">
          <button class="btn" @click="MoveRight">
            <span class="fa fa-angle-right"></span>
          </button>
          <button class="btn" @click="MoveLeft">
            <span class="fa fa-angle-left"></span>
          </button>
        </div>
        <div class="box-col box-form">
          <div class="legend-title">Danh sách đơn vị chưa gán</div>
          <div class="info-row">
            <div class="key w70">Loại đơn vị</div>
            <div class="value">
              <Select2 v-model="LoaiDonviId" :options="DmLoaiDv" />
            </div>
          </div>

          <DataGrid
            v-bind:columns="DsDonViChuaGan.headers2"
            v-bind:dataSource="DsDonViChuaGan.data"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="refDsDonViChuaGan"
            :enabledSelectFirstRow="false"
            @selectedItemsChanged="select_selectedItemsChangedChuagan"
          >
          </DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
// import DataGridC from '@/components/Shared/DataGrid/index.vue'
// import gridView from '@/components/Shared/gridview.vue'
// import breadcrumb from '@/components/breadcrumb'
import XLSX from 'xlsx'
import moment from 'moment'
import api from './Api'
export default {
  components: {
    // breadcrumb,
    // gridView,
    // DataGridC,
    XLSX,
    moment
  },
  name: 'AssignPermissionUnit',
  created() {
    this.Loading = true
    try {
      Promise.all([this.getDsThamSo(), this.getDmLoaiDonVi()])
        .then((responses) => {
          console.log(responses)
          this.getDsDonViDaGan(this.LoaiDonviId)
        })
        .finally((x) => {
          this.Loading = false
        })
    } catch (ex) {
      this.Loading = false
    } finally {
      this.Loading = false
    }
  },
  watch: {
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    },
    LoaiDonviId: async function (val, oldval) {
      this.Loading = true
      try {
        this.DsDonViDaGan.data = []
        this.DsDonViChuaGan.data = []
        await this.getDsDonViDaGan(val)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    }
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          {
            name: ' Quản lý người dùng',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Phân quyền tham số mặc định - Đơn vị',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      DsThamSo: {
        headers: [
          { name: 'ma_ts', text: 'Mã tham số', value: '' },
          { name: 'ten_ts', text: 'Tên tham số', value: '' },
          { name: 'ghichu', text: 'Ghi chú', value: '' }
        ],
        headers2: [
          {
            fieldName: 'ma_ts',
            headerText: 'Mã tham số',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'ten_ts',
            headerText: 'Tên tham số',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItems: []
      },
      DsDonViDaGan: {
        headers: [
          { name: 'donvi_id', text: 'ID' },
          { name: 'ma_dv', text: 'Mã đơn vị' },
          { name: 'ten_dv', text: 'Tên đơn vị' },
          { name: 'ten_dv_cha', text: 'Đơn vị cha' }
        ],
        headers2: [
          {
            fieldName: 'donvi_id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: true,
            width: 70,
            textAlign: 'center'
          },
          {
            fieldName: 'ma_dv',
            headerText: 'Mã đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv_cha',
            headerText: 'Đơn vị cha',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItems: []
      },
      DsDonViChuaGan: {
        headers: [
          { name: 'donvi_id', text: 'ID' },
          { name: 'ma_dv', text: 'Mã đơn vị' },
          { name: 'ten_dv', text: 'Tên đơn vị' },
          { name: 'ten_dv_cha', text: 'Đơn vị cha' }
        ],
        headers2: [
          {
            fieldName: 'donvi_id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: true,
            width: 70,
            textAlign: 'center'
          },
          {
            fieldName: 'ma_dv',
            headerText: 'Mã đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv_cha',
            headerText: 'Đơn vị cha',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: []
      },
      DmLoaiDv: [],
      LoaiDonviId: '',
      MaThamSo: ''
    }
  },
  methods: {
    getDmLoaiDonVi: async function () {
      var input = {}
      let data = this.GetData(await api.getDmLoaiDonVi(this.axios, input))
      this.DmLoaiDv = data.map((x) => ({
        id: x.LOAIDV_ID,
        text: x.TEN_LOAIDV == null ? '' : x.TEN_LOAIDV
      }))
      this.LoaiDonviId = this.DmLoaiDv[0].id
    },
    getDsThamSo: async function () {
      var input = {}
      this.DsThamSo.data = this.GetData(await api.getDsThamSo(this.axios, input))
      this.MaThamSo = this.DsThamSo.data[0].ma_ts
    },
    getDsDonViDaGan: async function (val) {
      if (this.MaThamSo.length > 0 && val > 0) {
        var input = {
          p_ma_ts: this.MaThamSo,
          p_loaidv_id: val,
          p_page_num: 1,
          p_page_num2: 1,
          p_page_sz: 4000,
          p_page_sz2: 4000
        }
        let data = this.GetData(await api.getDsDonViDaGan(this.axios, input))
        // console.log('data', data.ds, data.ds2)
        this.DsDonViDaGan.data = data.ds
        this.DsDonViChuaGan.data = data.ds2
        /*
        data.ds.forEach(function (item) {
          if (data.ds2.filter((x) => x.donvi_id == item.donvi_id).length > 0) {
            console.log('Dữ liệu trùng:', item)
          }
        })
        */
      }
    },
    select_selectedItemsChangedDagan: function (data) {
      this.DsDonViDaGan.selectedItems = data
    },
    select_selectedItemsChangedChuagan: function (data) {
      this.DsDonViChuaGan.selectedItems = data
    },
    getDsDonViChuaGan: async function () {},
    CapNhat: async function () {
      if (this.DsDonViDaGan.data.length === 0) {
        // this.ShowAlert(`Chưa có danh sách đã gán để cập nhật`)
        // return
      }
      let pdata = []
      let mats = this.MaThamSo
      this.DsDonViDaGan.data.forEach(function (item) {
        let newItem = { donvi_id: item.donvi_id, giatri: mats }
        pdata.push(newItem)
      })
      var input = {
        p_data: pdata,
        p_ma_ts: this.MaThamSo
      }
      try {
        this.Loading = true
        var response = await api.capNhat(this.axios, input)
        if (response.data.error === 200 || (response.data.error === '200' && response.data.error_code === 'BSS-00000000')) {
          this.ShowAlert(`Cập nhật thành công !`)
          try {
            Promise.all([this.getDsDonViDaGan(this.LoaiDonviId), this.getDsDonViChuaGan()])
              .then((responses) => {})
              .finally((x) => {
                this.Loading = false
              })
          } catch (ex) {
            this.Loading = false
          }
        } else {
          console.log(response.data.error_code)
          this.ShowError(`Cập nhật không thành công, Lỗi : ${response.data.error_code} !`)
        }
      } catch (err) {
        console.log('🚀 ~ file: index.vue ~ line 346 ~ err', err)
      } finally {
        this.Loading = false
      }
    },
    MoveRight: function () {
      var self = this
      var selecteds = this.DsDonViDaGan.selectedItems
      if (selecteds.length > 0) {
        this.DsDonViDaGan.checked = []
        selecteds.forEach(function (item) {
          if (self.DsDonViChuaGan.data.filter((x) => x.donvi_id === item.donvi_id).length === 0) {
            // self.DsDonViChuaGan.data.push(item)
            self.DsDonViChuaGan.data.unshift(item)
          }
        })
      } else {
        this.ShowError('Bạn chưa chọn đơn vị để hủy gán !')
      }
      self.DsDonViDaGan.data = self.DsDonViDaGan.data.filter((y) => selecteds.filter((x) => x.donvi_id === y.donvi_id).length === 0)
      let a = this.DsDonViDaGan.data.slice(0)
      this.DsDonViDaGan.data = a.slice(0)
      // this.$refs.refDsDonViDaGan.refeshFilter(selecteds)
    },
    MoveLeft: function () {
      var self = this
      var selecteds = this.DsDonViChuaGan.selectedItems
      if (selecteds.length > 0) {
        this.DsDonViChuaGan.data.checked = []
        selecteds.forEach(function (item) {
          if (self.DsDonViDaGan.data.filter((x) => x.donvi_id === item.donvi_id).length == 0) {
            item.ischecked = false
            // self.DsDonViDaGan.data.push(item)
            self.DsDonViDaGan.data.unshift(item)
          }
        })
        // this.$refs.refDsDonViDaGan.refeshFilter()
      } else {
        this.ShowError('Bạn chưa chọn đơn vị để gán !')
      }
      this.DsDonViChuaGan.data = this.DsDonViChuaGan.data.filter((y) => selecteds.filter((x) => x.donvi_id === y.donvi_id).length === 0)
    },
    ChangedParam: async function (i, item) {
      if (!item) {
        console.log(`Chưa có tham số `)
      }
      this.Loading = true
      this.MaThamSo = item.ma_ts
      try {
        await this.getDsDonViDaGan(this.LoaiDonviId)
        this.Loading = false
      } catch (ex) {
        console.log(item)
        this.Loading = false
      }
    },
    rowClicked_DaGan(i, item) {
      if (!item) {
        console.log(`Chưa có tham số `)
      }
    },
    actionBegin_ThamSo: function () {
      // this.$refs.refDsThamSo.grid.ej2Instances.query.addParams(
      //   "$filter",
      //   "ma_ts eq 1"
      // );
    },
    actionBegin_DaGan: function () {
      // this.$refs.refDsDonViDaGan.grid.ej2Instances.query.addParams(
      //   "$filter",
      //   "ma_dv eq 1"
      // );
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
<style>
</style>
