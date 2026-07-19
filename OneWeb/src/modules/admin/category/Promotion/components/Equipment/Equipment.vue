<template src='./Equipment.html'></template>
<style scoped src='./Equipment.scss'></style>
<script>
import { mapActions, mapMutations } from "vuex";
import gridview from "../../../../../../components/Shared/gridview.vue";
import select3 from "@/components/form/VueSelect.vue";

export default {
  components: {
    gridview,
    select3,
  },
  props: ["value"],

  name: "Equipment",
  data: () => ({
    promotionsCurrentRecord: null,
    currentRecordEquipmentNotDeliver: null,
    currentRecordEquipmentDeliver: null,
    EquipmentHeaderDeliver: [
      {
        label: "Trang bị đã gán",
        filterAble: true,
        key: "tentrangbi",
      },
    ],
    EquipmentHeaderNotDeliver: [
      {
        label: "Trang bị chưa gán",
        filterAble: true,
        key: "tentrangbi",
      },
    ],
    tempEquipmentDeliver: [],
    tempEquipmentNotDeliver: [],
  }),
  computed: {
    danhSachTrangBiDaGan() {
      return this.$store.getters["promotionCommon/danhSachTrangBiDaGan"];
    },
    danhSachTrangBiChuaGan() {
      return this.$store.getters["promotionCommon/danhSachTrangBiChuaGan"];
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
      "setDanhSachTrangBiDaGan",
      "setDanhSachTrangBiChuaGan",
    ]),
    onCheckBoxDeliver(e) {
      this.tempEquipmentDeliver = e;
    },
    onCheckBoxNotDeliver(e) {
      this.tempEquipmentNotDeliver = e;
    },
    onCheckBoxDeliverRemove() {
      if (this.tempEquipmentDeliver) {
        const oldNotDelivered = [...this.danhSachTrangBiChuaGan];
        const oldDelivered = [...this.danhSachTrangBiDaGan];

        const newNotDelivered = [
          ...oldNotDelivered,
          ...this.tempEquipmentDeliver,
        ];
        const arrDelete = this.tempEquipmentDeliver.map(
          (obj) => obj.trangbi_id
        );

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.trangbi_id)
        );
        this.setDanhSachTrangBiChuaGan(newNotDelivered);
        this.setDanhSachTrangBiDaGan(res);

        this.tempEquipmentDeliver = [];
        this.tempEquipmentNotDeliver = [];
      }
    },
    onCheckBoxNotDeliverToDeliver() {
      if (this.tempEquipmentNotDeliver) {
        const oldNotDelivered = [...this.danhSachTrangBiChuaGan];
        const oldDelivered = [...this.danhSachTrangBiDaGan];

        const newDelivered = [...oldDelivered, ...this.tempEquipmentNotDeliver];

        const arrDelete = this.tempEquipmentNotDeliver.map(
          (obj) => obj.trangbi_id
        );
        const newNotDelivered = oldNotDelivered.filter(
          (f) => !arrDelete.includes(f.trangbi_id)
        );
        this.setDanhSachTrangBiChuaGan(newNotDelivered);
        this.setDanhSachTrangBiDaGan(newDelivered);
        this.tempEquipmentDeliver = [];
        this.tempEquipmentNotDeliver = [];
      }
    },
    handDataRight() {
      const temp = [
        ...this.danhSachTrangBiDaGan,
        ...this.danhSachTrangBiChuaGan,
      ];
      this.setDanhSachTrangBiChuaGan(temp);
      this.setDanhSachTrangBiDaGan([]);
    },
    handDataLeft() {
      const temp = [
        ...this.danhSachTrangBiChuaGan,
        ...this.danhSachTrangBiDaGan,
      ];
      this.setDanhSachTrangBiDaGan(temp);
      this.setDanhSachTrangBiChuaGan([]);
    },
  },
};
</script>
