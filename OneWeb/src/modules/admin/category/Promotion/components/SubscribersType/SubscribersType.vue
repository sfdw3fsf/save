<template src='./SubscribersType.html'></template>
<style scoped src='./SubscribersType.scss'></style>
<script>
import { mapActions, mapMutations } from "vuex";
import gridview from "../../../../../../components/Shared/gridview.vue";
import select3 from "@/components/form/VueSelect.vue"; 

export default {
  components: {
    gridview,
    select3,
  },
  props: ["value", "infoPromotion", "tabIndex"],

  name: "SubscribersType",
  data: () => ({
    currentRecordSubscribersTypeDeliver: null,
    currentRecordSubscribersTypeNotDeliver: null,
    currentRecordSubscribersTypeDeliver: null,
    
    tempSubscribersTypeDeliver: [],
    tempSubscribersTypeNotDeliver: [],   
  }),
  computed: {
    dsloaiHinhTB() {
      if(this.tabIndex==1) return [];
      if (!!this.value.serviceTransportation2) {
        this.value.subscribersType =
          this.$store.getters["lhtbCommon/danhSachLoaiHinhThueBaoGetter"]
            .length > 0
            ? this.$store.getters[
                "lhtbCommon/danhSachLoaiHinhThueBaoGetter"
              ].filter(
                (item) =>
                  item.DICHVUVT_ID == this.value.serviceTransportation2
              )[0].LOAITB_ID
            : "";
      }

      return this.$store.getters[
        "lhtbCommon/danhSachLoaiHinhThueBaoGetter"
      ].filter(
        (item) => item.DICHVUVT_ID == this.value.serviceTransportation2
      ).map((e)=>({
        id: e.LOAITB_ID,
        text: e.LOAIHINH_TB
      }));
    },

    listSubscribersTypeDeliver() {
      return this.$store.getters["promotionCommon/listSubscribersTypeDeliver"];
    },
    listSubscribersTypeNotDeliver() {
      return this.$store.getters["promotionCommon/listSubscribersTypeNotDeliver"];
    },

    subscribersTypeHeaderDeliver(){
      return [
        {
          label: this.tabIndex == 0 ? "Tốc độ đã gán" : "Loại hình đã gán",
          filterAble: true,
          key: "tocdo",
        },
      ]
    },
    subscribersTypeHeaderNotDeliver(){
      return [
        {
          label: this.tabIndex == 0 ? "Tốc độ chưa gán" : "Loại hình chưa gán",
          filterAble: true,
          key: "tocdo",
        },
      ]
    },
    listTelecomService() {

      return this.$store.getters["promotionCommon/listTelecomService"].map((e) => ({
        id: e.ID,
        text: e.NAME
      }));
    },
  },
  mounted() {
    console.log(this.value.serviceTransportation2);
  },
  watch: {
    dsloaiHinhTB(newValue, oldValue) {
      console.log("getListDispatchNo");
      console.log(newValue);
    },
    promotionsCurrentRecord(newValue, oldValue) {
      this.$emit("handTablePromotions", newValue);
    },
  },
  methods: {
    ...mapActions("promotionCommon", ["getListDispatchNo","getListTelecomService"]),

    ...mapMutations("promotionCommon", [
      "setDanhSachTocDoTheoTBDaGan",
      "setDanhSachTocDoTheoTBChuaGan",
    ]),
    onCheckBoxDeliver(e) {
      this.tempSubscribersTypeDeliver = e;
    },
    onCheckBoxNotDeliver(e) {
      this.tempSubscribersTypeNotDeliver = e;
    },

    onCheckBoxDeliverRemove() {
      if (this.tempSubscribersTypeDeliver) {
        const oldNotDelivered = [...this.listSubscribersTypeNotDeliver];
        const oldDelivered = [...this.listSubscribersTypeDeliver];

        const newNotDelivered = [
          ...oldNotDelivered,
          ...this.tempSubscribersTypeDeliver,
        ];
        const arrDelete = this.tempSubscribersTypeDeliver.map(
          (obj) => obj.tocdo_id
        );

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.tocdo_id));
        this.setDanhSachTocDoTheoTBChuaGan(newNotDelivered);
        this.setDanhSachTocDoTheoTBDaGan(res);

        this.tempSubscribersTypeDeliver = [];
        this.tempSubscribersTypeNotDeliver = [];
      }
    },
    onCheckBoxNotDeliverToDeliver() {
      if (this.listSubscribersTypeNotDeliver) {
        const oldNotDelivered = [...this.listSubscribersTypeNotDeliver];
        const oldDelivered = [...this.listSubscribersTypeDeliver];

        const newDelivered = [
          ...oldDelivered,
          ...this.tempSubscribersTypeNotDeliver,
        ];

        const arrDelete = this.tempSubscribersTypeNotDeliver.map(
          (obj) => { return obj.tocdo_id + '_' + obj.loaitb_id; }
        );
        const newNotDelivered = oldNotDelivered.filter(
          (f) => !arrDelete.includes(f.tocdo_id+ '_' + f.loaitb_id)
        );
         
        this.setDanhSachTocDoTheoTBChuaGan(newNotDelivered);
        this.setDanhSachTocDoTheoTBDaGan(newDelivered);
        this.tempSubscribersTypeDeliver = [];
        this.tempSubscribersTypeNotDeliver = [];
      }
    },

    handDataRight() {
      const temp = [
        ...this.listSubscribersTypeDeliver,
        ...this.listSubscribersTypeNotDeliver,
      ];
      this.setDanhSachTocDoTheoTBChuaGan(temp);
      this.setDanhSachTocDoTheoTBDaGan([]);
    },
    handDataLeft() {
      const temp = [
        ...this.listSubscribersTypeNotDeliver,
        ...this.listSubscribersTypeDeliver,
      ];
      this.setDanhSachTocDoTheoTBDaGan(temp);
      this.setDanhSachTocDoTheoTBChuaGan([]);
    },
  },
};
</script>