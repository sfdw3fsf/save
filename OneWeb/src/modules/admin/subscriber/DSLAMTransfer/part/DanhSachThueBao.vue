<template>
  <vue-card label="Danh sách thuê bao">
<!--    <vue-table-->
<!--      :config="config"-->
<!--      :options="danhSachThueBao"-->
<!--      v-model="currentRecord"-->
<!--      :loading="isLoading"-->
<!--      :unique="'index'"-->
<!--      :multiple='true'-->
<!--      @input="onCheckBoxDeliver"-->
<!--    />-->
    <DataGridCustom ref="ds_phieubaohong" v-bind:columns="columns" v-bind:dataSource="danhSachThueBao"
                    :showColumnCheckbox="true" :showFilter="true" :enabledSelectFirstRow="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }" panelDataHeight="270"
                    @rowSelected="gridviewDanhSach_FocusedRowChanged" @selectedItemsChanged="gridviewDanhSach_SelectRow"
                    :allowPaging="true" :enablePagingServer="false" @queryCellInfo="gridviewDanhSach_queryCellInfo">
    </DataGridCustom>
  </vue-card>

</template>

<script>
import api from "../api";
import { TRANG_THAI_DEFAULT } from "../constants";
import { mapActions, mapState, mapGetters } from "vuex";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
export default {
  data() {
    return {
      currentRecord: null,

      config: [
        {
          label: "DSLAM",
          filterAble: true,
          key: "tendslam_cu"
        },
        {
          label: "DSLAM VDC cũ",
          filterAble: true,
          key: "dslam_vdc_cu"
        },
        {
          label: "DSLAM VDC mới",
          filterAble: true,
          key: "dslam_vdc_moi"
        },
        {
          label: "BRAS cũ",
          filterAble: true,
          key: "tenbras_cu"
        },
        {
          label: "BRAS mới",
          filterAble: true,
          key: "tenbras_moi"
        },
        {
          label: "Người CN",
          filterAble: true,
          key: "nguoi_cn"
        },
        {
          label: "Ngày CN",
          filterAble: true,
          key: "ngay_cn"
        }
      ],
      columns: [
        { fieldName: "tendslam_cu", headerText: "DSLAM", textAlign: "left", width: "150px" },
        { fieldName: "dslam_vdc_cu", headerText: "DSLAM VDC cũ", textAlign: "left", autoFit: "true" },
        { fieldName: "dslam_vdc_moi", headerText: "DSLAM VDC mới", textAlign: "left", autoFit: "true" },
        { fieldName: "tenbras_cu", headerText: "BRAS cũ", textAlign: "left", autoFit: "true" },
        { fieldName: "tenbras_moi", headerText: "BRAS mới", textAlign: "left", autoFit: "true" },
        { fieldName: "nguoi_cn", headerText: "Người CN", textAlign: "left", autoFit: "true" },
        { fieldName: "ngay_cn", headerText: "Ngày CN", textAlign: "left", autoFit: "true" },
      ]
    };
  },

  components: {
    DataGridCustom
  },

  async created() {
    await this.fetchDanhSachThueBao(TRANG_THAI_DEFAULT);
    this.currentRecord = this.danhSachThueBao ? this.danhSachThueBao[0] : null;
  },

  computed: {
    ...mapGetters("mapDslamTransfer", ["danhSachThueBao", "isLoading"])
  },

  methods: {
    ...mapActions("mapDslamTransfer", ["fetchDanhSachThueBao"]),
    // gridviewDanhSach_FocusedRowChanged(row) {
    //   this.currentRecord = row.data;
    // },

    gridviewDanhSach_SelectRow(row) {
      this.currentRecord = row;
    },
    // onGridDsCSKH_RowSelected(row) {
    //   console.log("row")
    //   console.log(row)
    // }
  },
  watch: {
    currentRecord(val) {
      this.$emit("currentRecord", val);
    }
  }
};
</script>
