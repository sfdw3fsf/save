<script>
import api from '../api'

export default {
  props: {
    dataGrid: [],
  },
  data: () => ({
    pageSize: 10,
    pageIndex: 0,
    totalRecords: 0,
    current: 0,
    loaidv_id: 0,
    options: {
      loaidv: []
    }
  }),
  watch: {
    'dataGrid'(newValue, oldValue) {
      if(Array.isArray(newValue) && newValue.length == 0) {
        this.$emit("DvSelected", null);
      }
    },
  },
  async mounted () {
    await this.loadLoaiDonVi();
  },
  methods: {
      rowSelected({data}) {
        this.$emit("DvSelected", data);
      },
      rowDeselected() {
        this.$emit("DvSelected", null);
      },
      pageChanged(args) {
        this.pageSize = args.pageSize;
        this.pageIndex = args.pageIndex;
        this.current = 0
      },
      setCurrent(data) {
        this.current = data.rowIndex;
      },
      async loadLoaiDonVi() {
        const resononseLoaiDV= await api.get_ds_LoaiDonvi(this.axios);
        if(resononseLoaiDV && resononseLoaiDV.status == 200 && resononseLoaiDV.data && resononseLoaiDV.data.data) {
          this.options.loaidv = resononseLoaiDV.data.data;
          const [loaidonvi] = this.options.loaidv;
          this.loaidv_id = loaidonvi['loaidv_id'];
          this.changeLoaiDv();
        } else {
          this.options.loaidv = []
        }
      },
      changeLoaiDv() {
        this.$emit('changeLoaiDv', this.loaidv_id)
      }
  },
  computed: {
    columns: () => ([
      {
        fieldName: "ten_dv",
        headerText: "Đơn vị",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dvql",
        headerText: "Đơn vị QL",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "tinh",
        headerText: "Tỉnh",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "quan",
        headerText: "Quận/Huyện",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "phuong",
        headerText: "Phường",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "pho",
        headerText: "Phố",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ap",
        headerText: "Ấp",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "khu",
        headerText: "Khu",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "dacdiem",
        headerText: "Đặc điểm",
        allowSorting: true,
        autoFit: "true",
      },
    ]),
  }
}
</script>

<template>
    <div class="SearchAreaByPlaceName DonVi">
      <div class="info-row">
        <div class="key w100">Loại đơn vị</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
              v-model="loaidv_id" 
              dataTextField="ten_loaidv"
              dataValueField="loaidv_id"
              :dataSource="options.loaidv"
              @change="changeLoaiDv"> 
            </SelectExt>                                  
          </div>
        </div>
      </div>
      <DataGrid
        v-bind:dataSource="dataGrid"
        :columns="columns"
        :enablePagingServer="false"
        :allowPaging="true"
        :pageIndex="pageIndex"
        :pageSize="pageSize"
        ref="danh_sach_don_vi"
        :totalRecords="totalRecords"
        @pageChanged="pageChanged"
        @rowSelected="rowSelected"
        @rowDeselected="rowDeselected"
        panelDataHeight="330"
      />
    </div>
</template>