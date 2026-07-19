<template src='./frmGanSeri_v2.html'></template>
<script>
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import select2 from "@/components/Select2.vue";
import frmGanSeri_V2API from "../api/frmGanSeri_V2API";

export default {
  components: { breadcrumb, appSelect2: select2 },
  mounted() {},
  provide: {},
  computed: {},
  data() {
    return {

      //Đức thêm vào
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      /////
      sources: [],
      soPhieu: "",
      chungTuId: null,
      maMD: "",
      xacnhan : false,

      txtSoPhieu: "",
      grvVatTu: [],
      grvChuaGan: [],
      grvDaGan: [],
      grvVatTuColumn: [],
      selectedDaGan: [],
      selectedChuaGan: [],
      gridViewVatTuKhongTinhTrang: [
        {
          fieldName: "LOHANG",
          headerText: "Lô Hàng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TEN_VT",
          headerText: "Tên vật tư",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DVI_TINH",
          headerText: "ĐVT",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DONGIA",
          headerText: "Đơn giá",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TIEN",
          headerText: "Thành tiền",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LOAITB_ID",
          headerText: "Loại thành tiền",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      gridViewVatTuCoTinhTrang: [
        {
          fieldName: "LOHANG",
          headerText: "Lô Hàng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TEN_VT",
          headerText: "Tên vật tư",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DVI_TINH",
          headerText: "ĐVT",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DONGIA",
          headerText: "Đơn giá",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TIEN",
          headerText: "Thành tiền",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LOAITB_ID",
          headerText: "Loại thành tiền",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TRANGTHAI_ID",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
    };
  },
  methods: {
    async fetchData() {
      this.loading(true);
      if (this.maMD == "N.TTCL") {
        this.grvVatTuColumn = this.gridViewVatTuCoTinhTrang;
      } else {
        this.grvVatTuColumn = this.gridViewVatTuKhongTinhTrang;
      }

      this.txtSoPhieu = this.soPhieu;
      let check = this.sources.filter((element) => element.LOAITBI_ID == 2);
      if (check.length == 0) {
        return;
      }
      this.grvVatTu = check;
      this.loading(false);
    },

    async gridVatTuSelectedRowChanged(data) {
      this.grvChuaGan = await frmGanSeri_V2API.getSeriDieuChinh(this.axios, {
        chungTuId: this.chungTuId,
        kieu: 0,
        ctctId: data[CTCT_ID],
      });

      this.grvDaGan = await frmGanSeri_V2API.getSeriDieuChinh(this.axios, {
        chungTuId: this.chungTuId,
        kieu: 1,
        ctctId: data[CTCT_ID],
      });
    },

    async onClickCapNhat() {
      this.xacnhan = true;
      this.Close()
    },

    async moveRight() {
      try {
        var kt = await NhapXuatVTAPI.capNhatSerrial(this.axios, {
          ds: this.selectedDaGan,
          kieu: 2,
        });
        this.$toast.success("Thành công!");
      } catch (error) {
        this.$toast.error(error.data.message_detail);
      }
      this.grvChuaGan.push(this.selectedDaGan);
    },
    async moveLeft() {
      try {
        var kt = await NhapXuatVTAPI.capNhatSerrial(this.axios, {
          ds: this.selectedChuaGan,
          kieu: 1,
        });
        this.$toast.success("Thành công!");
      } catch (error) {
        this.$toast.error(error.data.message_detail);
      }
      this.grvDaGan.push(this.selectedChuaGan);
    },

    async selectedItemsChangedDaGan(data) {
      this.selectedChuaGan = [];
      this.selectedDaGan = data;
    },
    async selectedItemsChangedChuaGan(data) {
      this.selectedDaGan = [];
      this.selectedChuaGan = data;
    },
    async Open(){
      this.loading(true)
      await this.fetchData()
      this.$refs.popupGanSeriV2.show();
      this.loading(false)
    },
    async Close() {
      this.$parent.XacNhan_frmGanSeri_v2()
      this.$refs.popupGanSeriV2.hide();
    },
  },
  created: async function () {},
  watch: {},
  destroyed() {},
};
</script>
<style>
</style>