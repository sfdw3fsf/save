<template src='./Service.html'></template>
<style scoped src='./Service.scss'></style>
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
  data: () => ({
    promotionsCurrentRecord: null,
    currentRecordServiceNotDeliver: null,
    currentRecordServiceDeliver: null,
    ServiceHeaderDeliver: [
      {
        label: "Dịch vụ đã gán",
        filterAble: true,
        key: "ten_dvvt",
      },
      {
        label: "Loại hình",
        filterAble: true,
        key: "loaihinh_tb",
      },
      {
        label: "Số lượng",
        filterAble: true,
        key: "soluong",
        tdClass:"text-right"
      },
      {
        label: "Mã ghép",
        filterAble: true,
        key: "ma_ghep",
      },
    ],
    ServiceHeaderNotDeliver: [
      {
        label: "Loại hình",
        filterAble: true,
        key: "loaihinh_tb",
      },
    ],
    tempServiceDeliver: [],
    tempServiceNotDeliver: [],
  }),
  computed: {
    danhSachDichVuKemDaGan() {
      return this.$store.getters["promotionCommon/danhSachDichVuKemDaGan"];
    },
    danhSachDichVuKemChuaGan() {
      return this.$store.getters["promotionCommon/danhSachDichVuKemChuaGan"];
    },
    listTelecomService() {
      return this.$store.getters["promotionCommon/listTelecomService"].map((e)=> ({
        id: e.ID,
        text: e.NAME
      }));
    },
    danhSachMaGhep() {
      return this.$store.getters["promotionCommon/danhSachMaGhep"];
    },
  },
  mounted() {},
  watch: {},
  methods: {
    ...mapMutations("promotionCommon", [
      "setDanhSachDichVuKemDaGan",
      "setDanhSachDichVuKemChuaGan",
    ]),
    onCheckBoxDeliver(e) {
      this.tempServiceDeliver = e;
    },
    onCheckBoxNotDeliver(e) {
      this.tempServiceNotDeliver = e;
    },

    onCheckBoxDeliverRemove() {
      if (this.tempServiceDeliver) {
        const oldNotDelivered = [...this.danhSachDichVuKemChuaGan];
        const oldDelivered = [...this.danhSachDichVuKemDaGan];

        const newNotDelivered = [
          ...oldNotDelivered,
          ...this.tempServiceDeliver,
        ];
        const arrDelete = this.tempServiceDeliver.map((obj) => obj.loaitb_id);

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.loaitb_id)
        );
        this.setDanhSachDichVuKemChuaGan(newNotDelivered);
        this.setDanhSachDichVuKemDaGan(res);

        this.tempServiceDeliver = [];
        this.tempServiceNotDeliver = [];
      }
    },
    onCheckBoxNotDeliverToDeliver() {
      // "loaihinh_tb":"CA-TAX",
      // 			"maghep_id":1,
      // 			"loaitb_id":55,
      // 			"soluong":0,
      // 			"ten_dvvt":"An toàn bảo mật thông tin",
      // 			"ma_ghep":"Cùng thanh toán"
      var ma_ghep = this.danhSachMaGhep.filter(
        (e) => e.maghep_id == this.value.maghep_id
      );
      
      
      var objDichVuVT = this.listTelecomService.filter(
        (e) => e.id == this.value.dichVuVT
      );

      var soluong = this.value.soLuong;
      let arrTemp = [];
      
      this.tempServiceNotDeliver.map((e) => {
        return arrTemp.push({
          ten_dvvt: objDichVuVT[0].text,
          soluong: soluong,
          loaihinh_tb: e.loaihinh_tb,
          loaitb_id: e.loaitb_id,
          ma_ghep: ma_ghep[0].ma_ghep,
          maghep_id: ma_ghep[0].maghep_id,
        });
      });

      if (this.danhSachDichVuKemChuaGan) {
        const oldNotDelivered = [...this.danhSachDichVuKemChuaGan];
        const oldDelivered = [...this.danhSachDichVuKemDaGan];

        const newDelivered = [...oldDelivered, ...arrTemp];

        const arrDelete = arrTemp.map((obj) => obj.loaitb_id);
        const newNotDelivered = oldNotDelivered.filter(
          (f) => !arrDelete.includes(f.loaitb_id)
        );
        this.setDanhSachDichVuKemChuaGan(newNotDelivered);
        this.setDanhSachDichVuKemDaGan(newDelivered);
        this.tempServiceDeliver = [];
        this.tempServiceNotDeliver = [];
      }
    },

    handDataRight() {
      console.log("handDataRight");
      const temp = [
        ...this.danhSachDichVuKemDaGan,
        ...this.danhSachDichVuKemChuaGan,
      ];
      this.setDanhSachDichVuKemChuaGan(temp);
      this.setDanhSachDichVuKemDaGan([]);
    },
    handDataLeft() {
      const temp = [
        ...this.danhSachDichVuKemChuaGan,
        ...this.danhSachDichVuKemDaGan,
      ];
      this.setDanhSachDichVuKemDaGan(temp);
      this.setDanhSachDichVuKemChuaGan([]);
    },
  },
};
</script>
