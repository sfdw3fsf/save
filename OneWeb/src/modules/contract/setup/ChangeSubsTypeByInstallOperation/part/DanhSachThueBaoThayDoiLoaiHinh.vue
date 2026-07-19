<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="title-main">Danh sách thuê bao thay đổi loại hình</div>
    </div>

    <vue-card>
      <vue-table
        :config="config"
        :options="dsThueBaoThayDoiLoaiHinh"
        :unique="'index'"
        :loading="isLoading"
        v-model="currentRecord"
      />
    </vue-card>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import { DATA_CURRENT } from "../constants";

export default {
  data() {
    return {
      currentRecord: null,
      listThueBao: [],

      config: [
        {
          label: "Số máy/Acc",
          filterAble: true,
          key: "ma_tb"
        },
        {
          label: "Tên thuê bao",
          filterAble: true,
          key: "ten_tb"
        },
        {
          label: "Địa chỉ lắp đặt",
          filterAble: true,
          key: "diachi_ld"
        },
        {
          label: "Ngày bàn giao",
          filterAble: true,
          key: "ngay_ht"
        }
      ]
    };
  },

  async created() {},

  computed: {
    ...mapGetters("mapChangeSubsTypeByInstallOperation", [
      "dsThueBaoThayDoiLoaiHinh",
      "isLoading"
    ])
  },

  methods: {
    ...mapActions("", [""])
  },
  watch: {
    //HPG-LH/00542901
    dsThueBaoThayDoiLoaiHinh(val) {
      if (Object.keys(val[0]).length) {
        this.currentRecord = val[0];
      } else {
        this.currentRecord = DATA_CURRENT;
      }
    },
    currentRecord(val) {
      this.$emit("currentRecord", val);
    }
  }
};
</script>
<style scoped>
.legend-title {
  display: flex;
  justify-content: space-between;
}

.title-sub {
  color: #d32f2f !important;
}
</style>
