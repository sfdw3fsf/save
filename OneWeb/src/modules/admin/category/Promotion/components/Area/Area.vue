<template src='./Area.html'></template>
<style scoped src='./Area.scss'></style>
<script>
import { mapActions, mapMutations } from "vuex";
import gridview from "../../../../../../components/Shared/gridview.vue";
import select3 from "@/components/form/VueSelect.vue";

export default {
  components: {
    gridview,
    select3,
  },

  name: "InstallationType",
  data: () => ({
    currentRecordAreaNotDeliver: null,
    currentRecordAreaDeliver: null,
    areaHeaderDeliver: [
      {
        label: "Khu vực đã gán",
        filterAble: true,
        key: "ten_kv",
      },
    ],
    areaHeaderNotDeliver: [
      {
        label: "Khu vực chưa gán",
        filterAble: true,
        key: "ten_kv",
      },
    ],
    tempAreaDeliver: [],
    tempAreaNotDeliver: [],
  }),
  computed: {
    danhSachKhuVucDaGan() {
      return this.$store.getters["promotionCommon/danhSachKhuVucDaGan"];
    },
    danhSachKhuVucChuaGan() {
      return this.$store.getters["promotionCommon/danhSachKhuVucChuaGan"];
    },
    listPromotion() {
      this.promotionsCurrentRecord =
        this.$store.getters["promotionCommon/listPromotion"][0];
      return this.$store.getters["promotionCommon/listPromotion"];
    },
  },
  mounted() {},
  watch: {
    promotionsCurrentRecord(newValue, oldValue) {
      this.$emit("handTablePromotions", newValue);
    },
  },
  methods: {
    ...mapActions("promotionCommon", ["getListDispatchNo"]),

    ...mapMutations("promotionCommon", [
      "setDanhSachKhuVucDaGan",
      "setDanhSachKhuVucChuaGan",
    ]),
    onCheckBoxDeliver(e) {
      this.tempAreaDeliver = e;
    },
    onCheckBoxNotDeliver(e) {
      this.tempAreaNotDeliver = e;
    },

    onCheckBoxDeliverRemove() {
      if (this.tempAreaDeliver) {
        const oldNotDelivered = [...this.danhSachKhuVucChuaGan];
        const oldDelivered = [...this.danhSachKhuVucDaGan];

        const newNotDelivered = [...oldNotDelivered, ...this.tempAreaDeliver];
        const arrDelete = this.tempAreaDeliver.map((obj) => obj.khuvuc_id);

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.khuvuc_id)
        );
        this.setDanhSachKhuVucChuaGan(newNotDelivered);
        this.setDanhSachKhuVucDaGan(res);
        this.tempAreaDeliver = [];
        this.tempAreaNotDeliver = [];
      }
    },
    onCheckBoxNotDeliverToDeliver() {
      if (this.danhSachKhuVucChuaGan) {
        const oldNotDelivered = [...this.danhSachKhuVucChuaGan];
        const oldDelivered = [...this.danhSachKhuVucDaGan];

        const newDelivered = [...oldDelivered, ...this.tempAreaNotDeliver];

        const arrDelete = this.tempAreaNotDeliver.map((obj) => obj.khuvuc_id);
        const newNotDelivered = oldNotDelivered.filter(
          (f) => !arrDelete.includes(f.khuvuc_id)
        );
        this.setDanhSachKhuVucChuaGan(newNotDelivered);
        this.setDanhSachKhuVucDaGan(newDelivered);
        this.tempAreaDeliver = [];
        this.tempAreaNotDeliver = [];
      }
    },
    handDataRight() {
      const temp = [...this.danhSachKhuVucDaGan, ...this.danhSachKhuVucChuaGan];
      this.setDanhSachKhuVucChuaGan(temp);
      this.setDanhSachKhuVucDaGan([]);
    },
    handDataLeft() {
      const temp = [...this.danhSachKhuVucChuaGan, ...this.danhSachKhuVucDaGan];
      this.setDanhSachKhuVucDaGan(temp);
      this.setDanhSachKhuVucChuaGan([]);
    },
  },
};
</script>
