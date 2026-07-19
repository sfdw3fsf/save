<template src='./Object.html'></template>
<style scoped src='./Object.scss'></style>
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
    currentRecordDoiTuongTBNotDeliver: null,
    currentRecordDoiTuongTBDeliver: null,
    DoiTuongTBHeaderDeliver: [
      {
        label: "Đối tượng đã gán",
        filterAble: true,
        key: "ten_dt",
      },
    ],
    DoiTuongTBHeaderNotDeliver: [
      {
        label: "Đối tượng chưa gán",
        filterAble: true,
        key: "ten_dt",
      },
    ],
    tempDoiTuongTBDeliver: [],
    tempDoiTuongTBNotDeliver: [],
  }),
  computed: {
    // danhSachUpdateField () {
    //   return this.$store.getters['qllvCommon/danhSachUpdateFieldGetter'].map((l) => { return {...l, ischecked: false} }) || []
    // }

    danhSachDoiTuongTBDaGan() {
      return this.$store.getters["promotionCommon/danhSachDoiTuongTBDaGan"];
    },
    danhSachDoiTuongTBChuaGan() {
      return this.$store.getters["promotionCommon/danhSachDoiTuongTBChuaGan"];
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
      "setDanhSachDoiTuongTBDaGan",
      "setDanhSachDoiTuongTBChuaGan",
    ]),
    onCheckBoxDeliver(e) {
      this.tempDoiTuongTBDeliver = e;
    },
    onCheckBoxNotDeliver(e) {
      this.tempDoiTuongTBNotDeliver = e;
    },
    onCheckBoxDeliverRemove() {
      if (this.tempDoiTuongTBDeliver) {
        const oldNotDelivered = [...this.danhSachDoiTuongTBChuaGan];
        const oldDelivered = [...this.danhSachDoiTuongTBDaGan];

        const newNotDelivered = [
          ...oldNotDelivered,
          ...this.tempDoiTuongTBDeliver,
        ];
        const arrDelete = this.tempDoiTuongTBDeliver.map(
          (obj) => obj.doituong_id
        );

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.doituong_id)
        );
        this.setDanhSachDoiTuongTBChuaGan(newNotDelivered);
        this.setDanhSachDoiTuongTBDaGan(res);

        this.tempDoiTuongTBDeliver = [];
        this.tempDoiTuongTBNotDeliver = [];
      }
    },
    onCheckBoxNotDeliverToDeliver() {
      if (this.danhSachDoiTuongTBChuaGan) {
        const oldNotDelivered = [...this.danhSachDoiTuongTBChuaGan];
        const oldDelivered = [...this.danhSachDoiTuongTBDaGan];

        const newDelivered = [
          ...oldDelivered,
          ...this.tempDoiTuongTBNotDeliver,
        ];

        const arrDelete = this.tempDoiTuongTBNotDeliver.map(
          (obj) => obj.doituong_id
        );
        const newNotDelivered = oldNotDelivered.filter(
          (f) => !arrDelete.includes(f.doituong_id)
        );
        this.setDanhSachDoiTuongTBChuaGan(newNotDelivered);
        this.setDanhSachDoiTuongTBDaGan(newDelivered);
        this.tempDoiTuongTBDeliver = [];
        this.tempDoiTuongTBNotDeliver = [];
      }
    },
    handDataRight() {
      const temp = [
        ...this.danhSachDoiTuongTBDaGan,
        ...this.danhSachDoiTuongTBChuaGan,
      ];
      this.setDanhSachDoiTuongTBChuaGan(temp);
      this.setDanhSachDoiTuongTBDaGan([]);
    },
    handDataLeft() {
      const temp = [
        ...this.danhSachDoiTuongTBChuaGan,
        ...this.danhSachDoiTuongTBDaGan,
      ];
      this.setDanhSachDoiTuongTBDaGan(temp);
      this.setDanhSachDoiTuongTBChuaGan([]);
    },
  },
};
</script>
