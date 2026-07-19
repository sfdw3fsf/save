<template src="./ManagementAgent.html"> </template>

<script>
import api from "./api";
import moment from "moment";
import TableDSDaiLy from "./components/TableDSDaiLy";
import TableLichSuThaoTacTB from "./components/TableLichSuThaoTacTB";
import PopupDSThongTinThanhVienDangKy from "./components/PopupDSThongTinThanhVienDangKy";
import EventBus from "@/utils/eventBus";
import { actionTypes } from "./index";
import { required } from "vuelidate/lib/validators";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import { BTab, BTabs } from "bootstrap-vue";
const defaultHopDongDaiLy = {
  ELOADMSISDN: "",
  CREATEDATE: moment(new Date()).format("DD/MM/YYYY"),
  AGENTID: "",
  MA_DAI_LY: "",
  TEN_DAI_LY: "",
  SO_IMSI: "",
  STATUS: "",
  ELOAD_NHANTIEN: "",
  PROVINCEID: null,
  KHU_VUC: null,
  PHAM_VI: null,
  LOAI_GT: null,
  SO_GT: "",
  DAI_DIEN: "",
  NGAY_CAP: "",
  NOI_CAP: "",
  NGAY_SINH: "",
  CHUC_VU: "",
  NGAY_KY_HD: "",
  SO_HD: "",
  ADDRESS: "",
  SO_GPKD: "",
  TEL: "",
  MAIL: "",
  COMMENTS: "",
  FAX: "",
  WEBSITE: ""
};
export default {
  name: "ManagementAgent",
  components: {
    TableDSDaiLy,
    TableLichSuThaoTacTB,
    DatePicker,
    BTab,
    BTabs,
    PopupDoiMatKhau: () => import("./components/PopupDoiMatKhau"),
    PopupDSThongTinThanhVienDangKy,
    PopupPhanQuyenThaoTac: () => import("./components/PopupPhanQuyenThaoTac"),
    PopupResetMatKhau: () => import("./components/PopupResetMatKhau")
  },
  data: () => {
    return {
      activeTab: 0,
      agent_id: "",
      currentDaiLyOnAction: null,
      showModal: {
        doiMatKhau: false,
        thongTinThanhVienDangKy: false,
        phanQuyenThaoTac: false,
        resetMatKhau: false
      },
      selectedHopDongDaiLy: null,
      hopDongDaiLy: { ...defaultHopDongDaiLy },
      dsMemberTrangThai: [],
      dsDaiLy: [],
      dsTinhThanh: [],
      dsKhuVuc: [],
      dsPhamVi: [],
      dsLoaiGiayTo: []
    };
  },
  validations: {
    hopDongDaiLy: {
      ELOADMSISDN: {
        required
      },
      PROVINCEID: {
        required
      },
      KHU_VUC: {
        required
      },
      PHAM_VI: {
        required
      },
      NGAY_SINH: {
        required
      },
      CHUC_VU: {
        required
      },
      ADDRESS: {
        required
      },
      SO_IMSI: {
        required
      },
      LOAI_GT: {
        required
      },
      SO_GT: {
        required
      },
      NGAY_CAP: {
        required
      },
      NOI_CAP: {
        required
      },
      NGAY_KY_HD: {
        required
      },
      SO_HD: {
        required
      }
    }
  },
  computed: {
    isEditing() {
      return Boolean(this.selectedHopDongDaiLy);
    }
  },
  watch: {
    selectedHopDongDaiLy: {
      handler(newValue) {
        if (newValue) Object.assign(this.hopDongDaiLy, newValue);
      },
      immediate: true
    }
  },
  methods: {
    async getMemberTrangThai() {
      const response = await api.getDanhMucCCB(this.$root.axios, {
        ten_ds: "MEMBER_TRANGTHAI"
      });
      const { data, message } = response.data;
      if (message === "Success") {
        this.dsMemberTrangThai = [...data];
      }
    },
    async getTinhThanh() {
      const response = await api.getDanhMucCCB(this.$root.axios, {
        ten_ds: "TINH_NEOS"
      });
      const { data, message } = response.data;
      if (message === "Success") {
        this.dsTinhThanh = [...data];
        this.hopDongDaiLy.PROVINCEID = this.dsTinhThanh[0]["TINH_TT_NEO"];
      }
    },
    async getKhuVuc() {
      const response = await api.getDanhMucCCB(this.$root.axios, {
        ten_ds: "KHUVUC_NEO"
      });
      const { data, message } = response.data;
      if (message === "Success") {
        this.dsKhuVuc = [...data];
        this.hopDongDaiLy.KHU_VUC = this.dsKhuVuc[0]["KHUVUC_ID"];
      }
    },
    async getPhamVi() {
      const response = await api.getDanhMucCCB(this.$root.axios, {
        ten_ds: "PHAMVI_NEO"
      });
      const { data, message } = response.data;
      if (message === "Success") {
        this.dsPhamVi = [...data];
        this.hopDongDaiLy.PHAM_VI = this.dsPhamVi[0]["PHAMVI_ID"];
      }
    },
    async getLoaiGiayTo() {
      const response = await api.getDanhMucCCB(this.$root.axios, {
        ten_ds: "LOAI_GT_NEO"
      });
      const { data, message } = response.data;
      if (message === "Success") {
        this.dsLoaiGiayTo = [...data];
        this.hopDongDaiLy.LOAI_GT = this.dsLoaiGiayTo[0]["LOAIGT_ID_NEO"];
      }
    },
    async onResetPassword() {
      this.showModal.resetMatKhau = !this.showModal.resetMatKhau;
    },
    async onChangePassword() {
      this.showModal.doiMatKhau = !this.showModal.doiMatKhau;
    },
    async onChangePermission() {
      this.showModal.phanQuyenThaoTac = !this.showModal.phanQuyenThaoTac;
    },
    async onShowDSThanhVienDangKy() {
      this.showModal.thongTinThanhVienDangKy = !this.showModal
        .thongTinThanhVienDangKy;
    },
    async cancelSelect() {
      this.selectedHopDongDaiLy = null;
      this.hopDongDaiLy = { ...defaultHopDongDaiLy };
    },
    async taoDaiLy() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      const {
        AGENTID,
        TEN_DAI_LY,
        KHU_VUC,
        DAI_DIEN,
        CHUC_VU,
        NGAY_SINH,
        LOAI_GT,
        SO_GT,
        NGAY_CAP,
        NOI_CAP,
        WEBSITE,
        ELOADMSISDN,
        SO_GPKD,
        SO_IMSI,
        SO_HD,
        NGAY_KY_HD,
        PROVINCEID,
        ADDRESS,
        FAX,
        MAIL,
        COMMENTS,
        PHAM_VI
      } = this.hopDongDaiLy;
      const payload = {
        agent_id: AGENTID,
        ten_dai_ly: TEN_DAI_LY,
        khu_vuc: KHU_VUC,
        dai_dien: DAI_DIEN,
        chuc_vu: CHUC_VU,
        ngay_sinh: NGAY_SINH,
        loai_gt: LOAI_GT,
        so_giay_to: SO_GT,
        ngay_cap: NGAY_CAP,
        noi_cap: NOI_CAP,
        website: WEBSITE,
        eload_msisdn: ELOADMSISDN,
        so_gpkd: SO_GPKD,
        imsi: SO_IMSI,
        so_hd: SO_HD,
        ngay_ky_hd: NGAY_KY_HD,
        province: PROVINCEID,
        dia_chi: ADDRESS,
        fax: FAX,
        email: MAIL,
        comment: COMMENTS,
        pham_vi: PHAM_VI,
        question: "",
        hd_web: "",
        user_web: "",
        ma_dai_ly: "",
        dienthoai_lh: ""
      };

      const response = await api.taoDaiLy(this.$root.axios, payload);
      const { result, error_code, message } = response.data;
      if (error_code === "BSS-00000000") {
        this.$toast.success(message, "title");
      } else this.$toast.warning(message, "title");
    },
    async search() {
      try {
        this.$root.showLoading(true);
        this.activeTab = 0;
        const payload = {
          so_tb: this.hopDongDaiLy.ELOADMSISDN
        };
        const responseDaiLyInfo = await api.getDaiLyInfo(
          this.$root.axios,
          payload
        );
        const { data: searchResults } = responseDaiLyInfo.data;
        this.dsDaiLy = searchResults;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.showLoading(false);
      }
    },
    onClickResetPassword() {
      EventBus.$emit("dai-ly-action", {
        row: this.selectedHopDongDaiLy,
        type: actionTypes.RESET_PASWORD
      });
    }
  },
  async mounted() {
    this.$root.showLoading(true);
    await this.getTinhThanh();
    await this.getKhuVuc();
    await this.getPhamVi();
    await this.getLoaiGiayTo();
    await this.getMemberTrangThai();

    this.$root.showLoading(false);

    // Event bus
    EventBus.$on("dai-ly-action", e => {
      const { row, type } = e;
      this.currentDaiLyOnAction = { ...row };
      switch (type) {
        case actionTypes.RESET_PASWORD:
          this.onResetPassword();
          break;
        case actionTypes.CHANGE_PASSWORD:
          this.onChangePassword();
          break;
        case actionTypes.CHANGE_PERMISSION:
          this.onChangePermission();
          break;
        case actionTypes.DS_THONG_TIN_DANG_KY:
          this.onShowDSThanhVienDangKy();
          break;
      }
    });
  },
  destroyed() {
    EventBus.$off("dai-ly-action");
  }
};
</script>

<style lang="css">
.management-agent .input-icon-right .mx-datepicker {
  width: 100% !important;
}
</style>
