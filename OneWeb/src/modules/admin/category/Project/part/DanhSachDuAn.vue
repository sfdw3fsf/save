<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="title-main">Danh sách dự án</div>
    </div>
    <vue-card>
      <vue-table
        :config="config"
        :options="danhSachDuAn"
        :unique="'index'"
        :loading="isLoadingDsDuAn"
        v-model="currentRecord"
      />
    </vue-card>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";

export default {
  data() {
    return {
      currentRecord: null,
      isLoading: false,

      config: [
        {
          label: "Số hiệu công trình",
          filterAble: true,
          key: "sohieu_ct"
        },
        {
          label: "Mã công trình",
          filterAble: true,
          key: "ma_ct"
        }
      ]
    };
  },

  async created() {
    // await this.fetchDanhSachDuAn();
    await this.fetchListProjects(); //fix SQL injection
    console.log(this.danhSachDuAn);
  },

  computed: {
    ...mapGetters("mapProject", ["danhSachDuAn", "isLoadingDsDuAn"])
  },

  methods: {
    ...mapActions("mapProject", ["fetchDanhSachDuAn", 'fetchListProjects'])
  },
  watch: {
    danhSachDuAn(val) {
      this.currentRecord = val[0];
    },

    currentRecord(val) {
      console.log(val);
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
