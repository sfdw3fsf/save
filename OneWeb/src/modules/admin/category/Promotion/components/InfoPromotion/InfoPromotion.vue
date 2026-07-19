<template src='./InfoPromotion.html'></template>
<style  src='./InfoPromotion.scss'></style>
<script>
import { mapActions } from "vuex";
// import gridview from '../../../../../../components/Shared/gridview.vue'
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";
import * as moment from "moment";
import select3 from "@/components/form/VueSelect.vue";
import DatePromotion from "../DatePromotion/DatePromotion.vue"; 

export default {
  components: {
    DatePicker,
    DatePromotion,
    select3,
  },
  props: ["value", "v","infoInstallationType"],
  name: "InfoPromotion",
  data: () => ({
    isDate: false,

    assignedType: [{ a: 1 }, { a: 5 }, { a: 4 }],
    unassignedType: [{ a: 2 }, { a: 5 }, { a: 4 }],
    promotionsHeader: [
      { text: "ID", fieldtype: "", name: "id" },
      { text: "Tên khuyến mãi", fieldtype: "", name: "name" },
      { text: "Số công văn", fieldtype: "", name: "num" },
    ],
    promotions: [
      { id: "123", name: "Đóng trước gói cước V59, V99", num: 1 },
      { id: "2", name: "Đóng trước gói cước V59, V99", num: 2 },
      { id: "3", name: "Đóng trước gói cước V59, V99", num: 3 },
      { id: "4", name: "Đóng trước gói cước V59, V99", num: 4 },
    ],
    momentFormat: {
      stringify: (date) => {
        return date ? moment(date).format("DD/MM/YYYY") : "";
      },
      parse: (value) => {
        return value ? moment(value, "DD/MM/YYYY").toDate() : null;
      },
    },
    data1: null,
    max: 3,
  }),

  mounted() {},
  computed: {
    checkKH() {
      return this.value.promotionType == 2 ? true : false;
    },
    listTelecomService() {
      return this.$store.getters["promotionCommon/listTelecomService"];
    },
    listDispatchNo() {
      return this.$store.getters["promotionCommon/listDispatchNo"].map((e) => ({
        id: e.congvan_id,
        text: e.ten_cv
      }));
    },
    listPromotionType() {
      return this.$store.getters["promotionCommon/listPromotionType"];
    },
    listKindOfPromtion() {
      return [
        { id: 0, name: "Khuyến mại" },
        { id: 1, name: "Đặt cọc" },
      ];
    },
    listAccompanyingPromotion() {
      let congvan_id = this.value.dispatchNumber;
      let kieuKM = this.value.kindOfPromtion;
      if(congvan_id && congvan_id > 0 && kieuKM == 2){

      }
      else
        congvan_id = "-1";
      let data = this.$store.getters[
        "promotionCommon/listAccompanyingPromotion"
      ].filter((el) => el.dichvuvt_id === +this.infoInstallationType.serviceTransportation2 && el.khuyenmai_id != 1 && el.congvan_id == congvan_id);
      console.log("listAccompanyingPromotion", data);
      return data;
    },
    listParticipationPromotion() {
      return this.$store.getters[
        "promotionCommon/listParticipationPromotion"
      ].filter((el) => el.dichvuvt_id === +this.infoInstallationType.serviceTransportation2).map((e) => ({
        id: e.khuyenmai_id,
        text: e.ten_km
      }));
    },
  },

  mounted() {},
  watch: {},
  methods: {
    ...mapActions("promotionCommon", ["getListTelecomService"]),
    onServiceTransportationChange() {
      this.$emit("onLoadListPromotion");
    },
    // testClick(){
    //   console.log("listTelecomService",this.listTelecomService);
    //   console.log("listDispatchNo",this.listDispatchNo);
    //   console.log("listPromotionType",this.listPromotionType);
      
    // },
    check() {
      console.log(this.value);
    },
    onDispatchNumberChange() {
      this.$emit("onLoadListPromotion");
    },
  },
};
</script>
