<template>
  <div class="info-row">
    <div class="key fw6 col-sm-4">{{ label }}</div>
    <div class="value col-sm-4">
      <ejs-datepicker
        :placeholder="'Chọn kỳ hóa đơn'"
        v-model="p_kyhoadon"
        :value="value"
        :max="max_kyhoadon"
        :start="'Year'"
        :depth="'Year'"
        :format="'MM/y'"
        :change="onKyHoaDonChange"
        :showClearButton="false"
        :allowEdit="false"
        class="text-red"
      >
      </ejs-datepicker>
    </div>
    <div class="key fw6 col-sm-4 nowrap">Chu kỳ</div>
    <div class="value col-sm-4">
      <div class="select-custom">
        <b-form-select
          id="id-chuky-no"
          v-model="p_chuky_no"
          :value="p_kyhoadon"
          :options="p_chuky_no_options"
          value-field="CHUKY"
          text-field="CHUKY"
          @change="onChuKyNoChange()"
          class="form-control text-red"
        >
        </b-form-select>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import CommonsAPI from "@/modules/QLTN/api/Commons";
import moment from "moment";
import EventBus from "@/utils/eventBus";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";

Vue.use(DatePickerPlugin);

export default {
  name: "KyCuoc",
  props: {
    value: {
      type: Date,
      default: function () {
        return moment(new Date()).subtract(1, "months").endOf("month").toDate();
      },
    },
    label: {
      type: String,
      default: "Kỳ hóa đơn",
    },
  },
  mounted() {
    if (this.value) {
      this.p_kyhoadon = this.value;
    }
    this.updateChuKyHoaDon(
      moment(new Date()).subtract(1, "months").endOf("month").format("YYYYMM")
    );
  },
  computed: {},
  data() {
    return {
      p_kyhoadon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      // p_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').format('MM/YYYY'),
      p_chuky_no_options: [],
      p_chuky_no: null,
      max_kyhoadon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
    };
  },
  methods: {
    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$toast.error("Vui lòng chọn Kỳ hóa đơn");
        return;
      }
      this.updateChuKyHoaDon(moment(args.value).format("YYYYMM"));
    },
    onChuKyNoChange: function () {},
    updateChuKyHoaDon: function (pKyHoaDon) {
      // this.loading(true);
      this.p_chuky_no_options = [];
      this.p_chuky_no = null;

      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.p_chuky_no_options = response.data.data;
            this.p_chuky_no = this.p_chuky_no_options[0].CHUKY;
          }
        })
        .catch(function (err) {
          console.log(err);
        })
        .finally(() => {
          this.loading(false);
        });
    },
  },
  watch: {
    p_chuky_no: {
      handler: function (value) {
        this.$emit("handleChangeKyCuoc", {
          p_kyhoadon: this.p_kyhoadon,
          p_chuky_no: this.p_chuky_no,
        });
        EventBus.$emit("chukycuocChange", {
          p_kyhoadon: this.p_kyhoadon,
          p_chuky_no: this.p_chuky_no,
        });
      },
    } /* ,
    p_kyhoadon : {
      handler: function(value) {
        this.updateChuKyHoaDon(moment(value).format('YYYYMM'))
      }
    }
    */,
  },
};
</script>
<style>
.text-red {
  color: #d32f2f !important;
}
</style>
