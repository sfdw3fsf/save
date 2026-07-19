<template>
  <div class="box-move-select-table fullh">
    <div class="box-col">
      <k-table
        :columns="columnsGan"
        :dataSources="dataSourcesGan"
        @dataCheckeds="onDataCheckedsGan"
        :allowFilter="true"
        :allowCheckBox="true"
        :tableHeight="250"
      />
    </div>
    <div class="actions">
      <button class="btn" @click="boGan">
        <span class="fa fa-angle-right"></span>
      </button>

      <button class="btn" @click="gan">
        <span class="fa fa-angle-left"></span>
      </button>
    </div>
    <div class="box-col">
      <k-table
        :columns="columnsChuaGan"
        :dataSources="dataSourcesChuaGan"
        @dataCheckeds="onDataCheckedsChuaGan"
        :allowFilter="true"
        :allowCheckBox="true"
        :tableHeight="250"
      />
    </div>
  </div>
</template>
<script>
import KTable from "@/components/kylq_components/KTable.vue";
import MultiServicePackageAPI from "../MultiServicePackageAPI";
export default {
  name: "DoiTuongTB",
  props: {
    goi_id: {
      type: Number,
      default: 0,
    },
  },
  components: {
    KTable,
  },
  data() {
    return {
      columnsGan: [
        {
          field: "ten_dt",
          label: "Đối tượng gán",
          allowFilter: true,
        },
      ],
      dataSourcesGan: [],
      columnsChuaGan: [
        {
          field: "ten_dt",
          label: "Đối tượng chưa gán",
          allowFilter: true,
        },
      ],
      dataSourcesChuaGan: [],
      goitichhop_id: this.goi_id,
      dataCheckGan: [],
      dataCheckChuaGan: [],
    };
  },
  methods: {
    onDataCheckedsGan(data) {
      this.dataCheckGan = data;
    },
    onDataCheckedsChuaGan(data) {
      this.dataCheckChuaGan = data;
    },
    gan() {
      if (this.dataCheckChuaGan.length == 0) {
        return;
      }
      this.dataCheckChuaGan.forEach((item) => {
        this.dataSourcesGan.push(item);
        const index = this.dataSourcesChuaGan.findIndex(
          (x) => x.doituong_id == item.doituong_id
        );
        if (index > -1) {
          this.dataSourcesChuaGan.splice(index, 1);
        }
      });
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
    },
    boGan() {
      if (this.dataCheckGan.length == 0) {
        return;
      }
      this.dataCheckGan.forEach((item) => {
        this.dataSourcesChuaGan.push(item);
        const index = this.dataSourcesGan.findIndex(
          (x) => x.doituong_id == item.doituong_id
        );
        if (index > -1) {
          this.dataSourcesGan.splice(index, 1);
        }
      });
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
    },
    getDsDoiTuongTBGan() {
      return this.dataSourcesGan;
    },
    async initDulieu() {
      await this.sp_grv_doituong_tb(this.goitichhop_id, 0);
      await this.sp_grv_doituong_tb(this.goitichhop_id, 1);
    },
    async clearDuLieu() {
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
      this.dataSourcesGan = [];
      this.dataSourcesChuaGan = [];
      await this.sp_grv_doituong_tb(0, 0);
      await this.sp_grv_doituong_tb(0, 1);
    },
    async sp_grv_doituong_tb(goi_id, type) {
      try {
        this.loading(true);
        if (type == 0) {
          this.dataSourcesChuaGan = [];
        } else {
          this.dataSourcesGan = [];
        }
        let response = await MultiServicePackageAPI.sp_grv_doituong_tb(
          this.axios,
          goi_id,
          type
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          var dt = response.data.data;
          if (dt.length > 0) {
            dt = dt.map((e) => Object.assign(e, { goi_id: goi_id }));
          }
          if (type == 0) {
            this.dataSourcesChuaGan = dt;
          } else {
            this.dataSourcesGan = dt;
          }
        } else {
          if (type == 0) {
            this.dataSourcesChuaGan = [];
          } else {
            this.dataSourcesGan = [];
          }
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách đối tượng thuê bao");
      } finally {
        this.loading(false);
      }
    },
  },
  watch: {
    goi_id(newval, oldval) {
      if (newval) {
        this.goitichhop_id = newval;
        this.initDulieu();
      }
    },
  },
};
</script>