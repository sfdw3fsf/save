<script>
import API from '../api'

export default {
  props: {
    dataGrid: []
  },
  data: () => ({
    dsKhuvucTable: {
      pageSize: 10,
      pageIndex: 0,
      totalRecords: 0,
      current: 0,
    },
    kvDaGanTable: {
      pageSize: 10,
      pageIndex: 0,
      totalRecords: 0,
      current: 0,
    },
    dsKvDaGan:[]
  }),
  watch: {
    'dataGrid'(newValue, oldValue) {
      if(Array.isArray(newValue) && newValue.length == 0) {
        this.dsKvDaGan = []
        this.$emit("kvDaGanSelected", null);
      }
    }
  },
  computed: {
    dsKvColumns: () => ([
      {
        fieldName: "ma_kv",
        headerText: "Mã KV",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_kv",
        headerText: "Tên khu vực",
        allowSorting: true,
        autoFit: "true",
      },
    ]),
    KvDaGanColumns: () => ([
       {
        fieldName: "loai_kv",
        headerText: "Loại khu vực",
        allowSorting: true,
        autoFit: "true",
      }
    ])
  },
  methods: {
    rowSelected(item) {
      const {khuvuc_id} = item.data;
      this.loadDsKvDaGan(khuvuc_id);
    },
    rowDeselected() {
      this.dsKvDaGan = []
      this.$emit("kvDaGanSelected", null);
    },
    pageChanged(args) {
      console.log(args)
      this.pageSize = args.pageSize;
      this.pageIndex = args.pageIndex;
      this.current = 0
    },
    setCurrent(data) {
      this.current = data.rowIndex;
    },
    async loadDsKvDaGan(khuvuc_id) {
      const response = await API.get_ds_loaiKV(this.axios, {vkhuvuc_id: khuvuc_id, vkieu: 1});
      if(response && response.status == 200 
        && response.data 
        && response.data.data 
        && response.data.data.length > 0) {
          this.dsKvDaGan = response.data.data;
      } else {
        this.dsKvDaGan = []
        this.$emit("kvDaGanSelected", null);
      }
    },
    KvDaGanSelected(item) {
      this.$emit("kvDaGanSelected", item.data);
    }
  }
};
</script>
<template>
  <div class="SearchAreaByPlaceName KhuVuc">
    <b-row>
      <b-col md="6">
        <div class="legend-title">Danh sách khu vực</div>
          <DataGrid
            v-bind:dataSource="dataGrid"
            :columns="dsKvColumns"
            :enablePagingServer="false"
            :allowPaging="true"
            :pageIndex="dsKhuvucTable.pageIndex"
            :pageSize="dsKhuvucTable.pageSize"
            ref="danh_sach_khu_vuc"
            :totalRecords="dsKhuvucTable.totalRecords"
            @rowSelected="rowSelected"
            @rowDeselected="rowDeselected"
            panelDataHeight="330"
          />
      </b-col>
      <b-col md="6">
        <div class="legend-title">Loại KV đã gán</div>
          <DataGrid
            v-bind:dataSource="dsKvDaGan"
            :columns="KvDaGanColumns"
            :enablePagingServer="false"
            :allowPaging="true"
            :pageIndex="kvDaGanTable.pageIndex"
            :pageSize="kvDaGanTable.pageSize"
            ref="danh_sach_khu_vuc"
            :totalRecords="kvDaGanTable.totalRecords"
            @rowSelected="KvDaGanSelected"
            panelDataHeight="330"
          />
      </b-col>
    </b-row>
  </div>
</template>
<style>
</style>