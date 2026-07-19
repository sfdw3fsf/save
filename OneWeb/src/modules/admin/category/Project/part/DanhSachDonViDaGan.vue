<template>
  <div class="box-form">
    <div class="legend-title">Danh sách đơn vị đã gán</div>
    <DataGrid
      v-bind:columns="header"
      v-bind:dataSource="danhSachDonViDaGan"
      :enable-paging-server="false"
      :allowPaging="true"
      :showFilter="true"
      :showColumnCheckbox="true"
      @selectedItemsChanged="selectedItemsChanged"
    >
    </DataGrid>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import gridview from "@/components/Shared/gridview";

export default {
  name: "QuanLyDuAn",
  components: {
    DatePicker
  },

  props: {
    dataCurrent: Object
  },

  data() {
    return {
      header: [
        {
          fieldName: "loaidv_id",
          headerText: "Loại đơn vị",
          allowFiltering: true,
          width: 100,
          isGroupedColumn: true
        },
        {
          fieldName: "ma_dv",
          headerText: "Mã ĐV",
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowHtml: true,
          width: 120
        }
      ]
    };
  },

  computed: {
    ...mapGetters("mapProject", ["danhSachDonViDaGan"])
  },
  methods: {
    ...mapActions("mapProject", [
      "fetchDanhSachDonViDaGan",
      "setSelecteItemsDonViDaGan"
    ]),

    /* chọn data từ danh sách thuê bao */
    selectedItemsChanged(arr) {
      this.setSelecteItemsDonViDaGan(arr);
    },

    handleShowChild(index) {
      this.parentIndex = index;
      this.isShowChild = !this.isShowChild;
    },
    selectItem(item) {
      this.itemSelect = item;
    }
  },
  watch: {
    async dataCurrent(val) {
      const variables = {
        duan_id: val.DUAN_ID,
        kieu: 2,
        loaidv_id: 1
      };
      await this.fetchDanhSachDonViDaGan(variables);
    }
  }
};
</script>
<style scoped></style>
