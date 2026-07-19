<template src='./frmVatTuTrongKhoV2.html'></template>
<script>
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import select2 from "@/components/Select2.vue";
import frmVatTuTrongKhoV2API from "../api/frmVatTuTrongKhoV2API";

export default {
  name: "frmVatTuTrongKhoV2",
  // props: {
  //   sources: Array,
  // },

  components: { breadcrumb, appSelect2: select2 },
  mounted() {},
  provide: {},
  computed: {},
  data() {
    return {
      vchungtu_id: 0,
      vkho_id: 0,
      xacnhan: false,
      isVisibleChonLoHang: true,
      isVisibleXacNhan: false,
      isVisibleHuyBo: false,
      isTab: false,

      enabledTukho: true,
      enabledDenNgay: true,

      txtKhoId: 11279,
      txtDenNgay: "13/12/2021",
      cboKho: [],

      grvTonKho: [],
      selectedGrvTonKho: [],

      grvDaGan: [],
      selectedGrvDaGan: [],

      grvVatTu: [],
      selectedGrvVatTu: [],

      grvVTGroup: [],
      selectedGrvVTGroup: [],

      dsVatTu: [],
      dsGan: [],
      dsDaGan: [],
      dateFormat: "dd/MM/yyyy",
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
    };
  },
  methods: {
    async onCreate() {
      var res = await frmVatTuTrongKhoV2API.layDSKho(
        this.axios,
        this.$auth.getNhanVienID()
      );

      this.cboKho = res.data.data;
      this.enabledTukho = false;
      this.enabledDenNgay = false;
      this.NAP_DS_TONKHO_GROUP();
      this.vkho_id = this.txtKhoId;
    },

    async NAP_DS_TONKHO_GROUP() {
      this.grvTonKho = [];
      var res = await frmVatTuTrongKhoV2API.getDSVatTuTonKhoV2(this.axios, {
        khoId: this.txtKhoId,
        ngayCT: this.txtDenNgay,
        chungTuId: this.vchungtu_id,
      });
      this.grvTonKho = res.data.data;
      this.dsVatTu = this.grvTonKho;
    },

    huyBo() {
      this.grvDaGan = [];
      this.grvVTGroup = [];
      this.dsGan = [];
      this.dsVatTu = [];
      this.isTab = false;
    },

    changeTab(data) {
      if (data == 1) {
        this.isVisibleXacNhan = true;
        this.isVisibleChonLoHang = false;
        this.isVisibleHuyBo = true;
        this.isTab = true;
      } else {
        this.isVisibleXacNhan = false;
        this.isVisibleChonLoHang = true;
        this.isVisibleHuyBo = false;
        this.isTab = false;
      }
    },

    async onClickChonLoHang() {
      this.dsVatTu = [];
      this.dsGan = [];
      const dsTonKhoDaChon =
        this.$refs.selectedGrvTonKho.getSelectedRecords() || [];

      if (dsTonKhoDaChon.length == 0) {
        this.$toast.error("Không có dữ liệu tồn kho không thể chọn lô");
        return;
      }

      dsTonKhoDaChon.forEach((item) => {
        if (item.SL_TON < item.SL_XUAT) {
          this.$toast.error("Số lượng xuất phải nhỏ hơn số lượng tồn");
          return;
        }
      });
      this.grvVTGroup = dsTonKhoDaChon;
      this.changeTab(1);
    },

    async onChangeVTGroup(data) {
      if (data) {
        this.selectedGrvVTGroup = data;
        this.NAP_DS_DAGAN();
        this.NAP_DS_CHITIET_TT();
      } else {
        this.grvDaGan = [];
        this.grvVatTu = [];
      }
    },

    NAP_DS_DAGAN() {
      this.grvDaGan = [];

      if (this.dsGan.length > 0) {
        this.grvDaGan = this.dsGan.filter(
          (x) =>
            x.VATTU_ID.toString() == this.selectedGrvVTGroup.VATTU_ID.toString()
        );
      }
    },

    NAP_DS_CHITIET_TT() {
      this.grvVatTu = [];

      let loc = [];
      if (this.dsGan.length > 0) {
        this.dsGan.forEach((element) => {
          if (element.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID) {
            loc.push(element.LOHANG);
          }
        });
      }

      let ds = [];
      if (loc.length > 0) {
        this.grvVatTu = this.grvTonKho.filter(
          (x) =>
            x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID &&
            !loc.includes(x.LOHANG)
        );
      } else {
        this.grvVatTu = this.grvTonKho.filter(
          (x) => x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID
        );
      }
    },

    onClickHuyBo() {
      this.huyBo();
    },

    moveRight() {
      if (this.grvDaGan.length == 0) {
        this.$toast.error("Không có dữ liệu để thao tác");
        return;
      }

      const dsDaGanDaChon = this.$refs.selectedDaGan.getSelectedRecords() || [];

      if (dsDaGanDaChon.length == 0) {
        this.$toast.error("Bạn chưa chọn danh sách lô hàng để gỡ");
        return;
      }

      let rowToDelete = [];
      let rowToDeleteVatTu = [];

      if (this.dsGan.length > 0) {
        rowToDelete = dsDaGanDaChon.filter(
          (x) => x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID
        );
      }

      if (this.dsVatTu.length > 0) {
        this.dsVatTu.forEach((x) => {
          if (x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID) {
            rowToDelete.forEach((element) => {
              if (
                element.LOHANG == x.LOHANG &&
                element.VATTU_ID == x.VATTU_ID
              ) {
                rowToDeleteVatTu.push(x);
              }
            });
          }
        });
      }

      rowToDelete.forEach((element) => {
        this.dsGan = this.dsGan.filter(
          (x) => x.VATTU_ID != element.VATTU_ID || element.LOHANG != x.LOHANG
        );
      });

      rowToDeleteVatTu.forEach((element) => {
        this.dsVatTu = this.dsVatTu.filter(
          (x) => x.VATTU_ID != element.VATTU_ID || element.LOHANG != x.LOHANG
        );
      });

      this.NAP_DS_DAGAN();
      this.NAP_DS_CHITIET_TT();
    },

    moveLeft() {
      if (this.grvVatTu.length == 0) {
        this.$toast.error("Không có dữ liệu để thao tác");
        return;
      }

      const dsChuaGanDaChon =
        this.$refs.selectedVatTu.getSelectedRecords() || [];
      if (dsChuaGanDaChon.length == 0) {
        this.$toast.error("Bạn chưa chọn danh sách lô hàng để gán");
        return;
      }
      this.dsGan = [...this.dsGan, ...dsChuaGanDaChon];
      this.dsVatTu = [...this.dsVatTu, ...dsChuaGanDaChon];
      this.NAP_DS_DAGAN();
      this.NAP_DS_CHITIET_TT();
    },

    onClickXacNhan() {
      if (this.dsGan.length == 0) {
        this.$toast.error("Chưa có danh sách gán không thể xác nhận");
        return;
      }

      if (this.grvVTGroup.length == 0) {
        this.$toast.error("Chưa có danh sách tồn kho không thể xác nhận");
        return;
      }

      let vatTuIds = [];

      this.grvVTGroup.forEach((element) => {
        vatTuIds.push(element.VATTU_ID);
      });
      if (vatTuIds.length == vatTuIds.map().length) {
        this.$toast.error("Có vật tư chưa gán lô bạn hãy kiểm tra lại");
        return;
      }

      this.grvVTGroup.forEach((element) => {
        let sum = 0;
        this.dsGan.forEach((x) => {
          if (x.VATTU_ID == element.VATTU_ID) {
            sum += x.SOLUONG;
          }
        });
        if (element.SOLUONG > sum) {
          this.$toast.error(
            "Có vật tư chưa đủ số lượng của lô " +
              element.MA_VT +
              " có số lượng " +
              sum +
              "/" +
              element.SOLUONG
          );
          return;
        }
      });

      //TODO: làm tiếp nghe
      // Đức nối NhapXuatV2
      this.xacnhan = true
      this.$refs.popupVatTuTrongKhoV2.hide()
      this.$parent.XacNhan_frmVatTuTrongKho_V2()
      

    },

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    frmVatTuTrongKho_V2_Load(){
      this.onCreate();
    },
    OpenDiaLog(){
      this.$refs.popupVatTuTrongKhoV2.show()
    }
  },
  created: async function () {
    // this.onCreate();
  },
  watch: {},
  destroyed() {},
};
</script>
<style>
</style>