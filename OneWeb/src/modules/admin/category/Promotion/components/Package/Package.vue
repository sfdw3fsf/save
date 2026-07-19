<template src='./Package.html'></template>
<style scoped src='./Package.scss'></style>
<script>
import { mapActions, mapMutations } from "vuex";
import gridview from "../../../../../../components/Shared/gridview.vue";
import select3 from "@/components/form/VueSelect.vue";

export default {
  components: {
    gridview,
    select3,
  },
  data: () => ({
    promotionsCurrentRecord: null,
    currentRecordPackageNotDeliver: null,
    currentRecordPackageDeliver: null,
    PackageHeaderDeliver: [
      {
        label: "Gói đã gán",
        filterAble: true,
        key: "ten_goi",
      },
    ],
    PackageHeaderNotDeliver: [
      {
        label: "Gói chưa gán",
        filterAble: true,
        key: "ten_goi",
      },
    ],
    tempPackageDeliver: [],
    tempPackageNotDeliver: [],
  }),
  computed: {
    danhSachGoiThamGiaDaGan() {
     
      return this.$store.getters["promotionCommon/danhSachGoiThamGiaDaGan"];
    },
    danhSachGoiThamGiaChuaGan() {
      return this.$store.getters["promotionCommon/danhSachGoiThamGiaChuaGan"];
    },
  },
  mounted() {},
  watch: {},
  methods: {
    ...mapActions("promotionCommon", ["getListDispatchNo"]),

    ...mapMutations("promotionCommon", [
      "setDanhSachGoiThamGiaDaGan",
      "setDanhSachGoiThamGiaChuaGan",
    ]),
    onCheckBoxDeliver(e) {
      this.tempPackageDeliver = e;
    },
    onCheckBoxNotDeliver(e) {
      this.tempPackageNotDeliver = e;
    },

    onCheckBoxDeliverRemove() {
      if (this.tempPackageDeliver) {
        const oldNotDelivered = [...this.danhSachGoiThamGiaChuaGan];
        const oldDelivered = [...this.danhSachGoiThamGiaDaGan];

        const newNotDelivered = [
          ...oldNotDelivered,
          ...this.tempPackageDeliver,
        ];
        const arrDelete = this.tempPackageDeliver.map((obj) => obj.goi_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.goi_id));
        this.setDanhSachGoiThamGiaChuaGan(newNotDelivered);
        this.setDanhSachGoiThamGiaDaGan(res);

        this.tempPackageDeliver = [];
        this.tempPackageNotDeliver = [];
      }
    },
    onCheckBoxNotDeliverToDeliver() {
      if (this.tempPackageNotDeliver) {
        const oldNotDelivered = [...this.danhSachGoiThamGiaChuaGan];
        const oldDelivered = [...this.danhSachGoiThamGiaDaGan];

        const newDelivered = [...oldDelivered, ...this.tempPackageNotDeliver];

        const arrDelete = this.tempPackageNotDeliver.map((obj) => obj.goi_id);
        const newNotDelivered = oldNotDelivered.filter(
          (f) => !arrDelete.includes(f.goi_id)
        );
        this.setDanhSachGoiThamGiaChuaGan(newNotDelivered);
        this.setDanhSachGoiThamGiaDaGan(newDelivered);
        this.tempPackageDeliver = [];
        this.tempPackageNotDeliver = [];
      }
    },
    handDataRight() {
      console.log("handDataRight");
      const temp = [
        ...this.danhSachGoiThamGiaDaGan,
        ...this.danhSachGoiThamGiaChuaGan,
      ];
      this.setDanhSachGoiThamGiaChuaGan(temp);
      this.setDanhSachGoiThamGiaDaGan([]);
    },
    handDataLeft() {
      const temp = [
        ...this.danhSachGoiThamGiaChuaGan,
        ...this.danhSachGoiThamGiaDaGan,
      ];
      this.setDanhSachGoiThamGiaDaGan(temp);
      this.setDanhSachGoiThamGiaChuaGan([]);
    },
  },
};
</script>
