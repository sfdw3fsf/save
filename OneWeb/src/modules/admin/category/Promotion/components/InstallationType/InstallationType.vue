<template src='./InstallationType.html'></template>
<style scoped src='./InstallationType.scss'></style>
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

  name: "InstallationType",
  data: () => ({
    assignedType: [{ a: 1 }, { a: 5 }, { a: 4 }],
    unassignedType: [{ a: 2 }, { a: 5 }, { a: 4 }],
    promotionsCurrentRecord: null,
    promotionsHeader: [
      { label: "ID", filterAble: true, key: "khuyenmai_id", width: "90px" },
      { label: "Tên khuyến mãi", filterAble: true, key: "ten_km" },
      { label: "Số công văn", filterAble: true, key: "socongvan" },
    ],
    promotions: [
      { id: "1", name: "Đóng trước gói cước V59, V99", num: 1 },
      { id: "2", name: "Đóng trước gói cước V59, V99", num: 2 },
      { id: "3", name: "Đóng trước gói cước V59, V99", num: 3 },
      { id: "4", name: "Đóng trước gói cước V59, V99", num: 4 },
    ],
    currentRecordContractTypeNotDeliver: null,
    currentRecordContractTypeDeliver: null,
    contractTypeHeaderDeliver: [
      {
        label: "Kiểu lắp đặt đã gán",
        filterAble: true,
        key: "ten_kieuld",
      },
    ],
    contractTypeHeaderNotDeliver: [
      {
        label: "Kiểu lắp đặt chưa gán",
        filterAble: true,
        key: "ten_kieuld",
      },
    ],
    tempContractTypeDeliver: [],
    tempContractTypeNotDeliver: [],
  }),
  computed: {
    // danhSachUpdateField () {
    //   return this.$store.getters['qllvCommon/danhSachUpdateFieldGetter'].map((l) => { return {...l, ischecked: false} }) || []
    // }
    listDispatchNo() {
      return this.$store.getters["promotionCommon/listDispatchNo"];
    },
    listContractType() {
      return this.$store.getters["promotionCommon/listContractType"].map((e) => ({
        id: e.ID,
        text: e.NAME
      }));
    },

    listContractTypeDeliver() {
      return this.$store.getters["promotionCommon/listContractTypeDeliver"];
    },
    listContractTypeNotDeliver() {
      return this.$store.getters["promotionCommon/listContractTypeNotDeliver"];
    },
    ListSoCongVan_TimKiem() {
      return this.$store.getters["promotionCommon/ListSoCongVan_TimKiem"];
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
      "setDataContractTypeDeliver",
      "setDataContractTypeNotDelivered",
    ]),
    onCheckBoxDeliver(e) {
      this.tempContractTypeDeliver = e;
    },
    onCheckBoxNotDeliver(e) {
      this.tempContractTypeNotDeliver = e;
    },

    onCheckBoxDeliverRemove() {
      if (this.tempContractTypeDeliver) {
        const oldNotDelivered = [...this.listContractTypeNotDeliver];
        const oldDelivered = [...this.listContractTypeDeliver];

        const newNotDelivered = [
          ...oldNotDelivered,
          ...this.tempContractTypeDeliver,
        ];
        const arrDelete = this.tempContractTypeDeliver.map(
          (obj) => obj.kieuld_id
        );

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.kieuld_id)
        );
        this.setDataContractTypeNotDelivered(newNotDelivered);
        this.setDataContractTypeDeliver(res);

        this.tempContractTypeDeliver = [];
        this.tempContractTypeNotDeliver = [];
      }
    },
    onCheckBoxNotDeliverToDeliver() {
      if (this.listContractTypeNotDeliver) {
        const oldNotDelivered = [...this.listContractTypeNotDeliver];
        const oldDelivered = [...this.listContractTypeDeliver];

        const newDelivered = [
          ...oldDelivered,
          ...this.tempContractTypeNotDeliver,
        ];

        const arrDelete = this.tempContractTypeNotDeliver.map(
          (obj) => obj.kieuld_id
        );
        const newNotDelivered = oldNotDelivered.filter(
          (f) => !arrDelete.includes(f.kieuld_id)
        );
        this.setDataContractTypeNotDelivered(newNotDelivered);
        this.setDataContractTypeDeliver(newDelivered);
        this.tempContractTypeDeliver = [];
        this.tempContractTypeNotDeliver = [];
      }
    },

    handDataRight() {
      const temp = [
        ...this.listContractTypeDeliver,
        ...this.listContractTypeNotDeliver,
      ];
      this.setDataContractTypeNotDelivered(temp);
      this.setDataContractTypeDeliver([]);
    },
    handDataLeft() {
      const temp = [
        ...this.listContractTypeNotDeliver,
        ...this.listContractTypeDeliver,
      ];
      this.setDataContractTypeDeliver(temp);
      this.setDataContractTypeNotDelivered([]);
    },
  },
};
</script>
