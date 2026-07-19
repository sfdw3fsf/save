<template src="./inGBC.html"></template>
<style src="./style.scss" scoped>
</style>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>
import moment from "moment";
import Calculator from "@/components/Calculator";
import ModalExport from "./components/modal-export";

const mustANumber = (value) => {
  const str = value.toString().replaceAll(",", "");
  if (str.trim().length == 0) return false;
  if (!isNaN(str)) return true;
  if (typeof str != "string") return false;
  return !isNaN(str) && !isNaN(parseFloat(str));
};

export default {
  components: {
    Calculator,
    ModalExport,
  },
  filters: {
    numberCustom: function (value) {
      const strVal = value.toString().replaceAll(",", "");
      // console.log(mustANumber(strVal))
      if (mustANumber(value)) {
        if (strVal.indexOf(".") != strVal.length - 1) {
          let valNum = parseFloat(strVal);
          return valNum.toLocaleString("en-En", { maximumFractionDigits: 10 });
        }
      } else {
        return "";
      }
      return strVal;
    },
  },
  data: function () {
    return {
      seri: "",
      soSeri: 0,
      soSeriTxt: 0,
      isCalculatorShow: false,
      kyCuoc: `${moment(new Date()).subtract(1, "months").endOf("month").format("MM/YYYY")}`,
      kyHoaDon: `${moment(new Date()).subtract(1, "months").endOf("month").format("YYYYMM")}`,
      chuKy: "01",
      loaiPhieu: 1,
      timeCreate: "",
      dsData: [],
      columns: [
        {
          headerText: "STT_1",
          fieldName: "STT_1",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "KYHOADON",
          fieldName: "KYHOADON",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "MA_TT",
          fieldName: "MA_TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "SODAIDIEN",
          fieldName: "SODAIDIEN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "TEN_TT",
          fieldName: "TEN_TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "DIACHI_TT",
          fieldName: "DIACHI_TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "HTTT",
          fieldName: "HTTT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "MANV_TC",
          fieldName: "MANV_TC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "MST",
          fieldName: "MST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "QUAN_ID",
          fieldName: "QUAN_ID",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "MA_PHAT_HD",
          fieldName: "MA_PHAT_HD",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "DK_GOC",
          fieldName: "DK_GOC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "DK_THUE",
          fieldName: "DK_THUE",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT1_ND",
          fieldName: "CT1_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT1_ST",
          fieldName: "CT1_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT2_ND",
          fieldName: "CT2_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT2_ST",
          fieldName: "CT2_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT3_ND",
          fieldName: "CT3_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT3_ST",
          fieldName: "CT3_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT4_ND",
          fieldName: "CT4_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT4_ST",
          fieldName: "CT4_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT5_ND",
          fieldName: "CT5_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT5_ST",
          fieldName: "CT5_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT6_ND",
          fieldName: "CT6_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT6_ST",
          fieldName: "CT6_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT7_ND",
          fieldName: "CT7_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT7_ST",
          fieldName: "CT7_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT8_ND",
          fieldName: "CT8_ND",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CT8_ST",
          fieldName: "CT8_ST",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "KHD_TRA",
          fieldName: "KHD_TRA",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CUOC_PS",
          fieldName: "CUOC_PS",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "THUE_PS",
          fieldName: "THUE_PS",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "CUOC",
          fieldName: "CUOC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "THUE",
          fieldName: "THUE",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "TONGCONG",
          fieldName: "TONGCONG",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "NGAY_TT",
          fieldName: "NGAY_TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "SERI",
          fieldName: "SERI",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "SOQUYEN",
          fieldName: "SOQUYEN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "SOSERI",
          fieldName: "SOSERI",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "DANGKHOA",
          fieldName: "DANGKHOA",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "GIAMTRU",
          fieldName: "GIAMTRU",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "STT",
          fieldName: "STT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "DOTGIAO_ID",
          fieldName: "DOTGIAO_ID",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "THUTU_IN",
          fieldName: "THUTU_IN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "DAILY_ID",
          fieldName: "DAILY_ID",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "TO_IN",
          fieldName: "TO_IN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          headerText: "TEN_TO",
          fieldName: "TEN_TO",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
    };
  },
  validations: {},
  watch: {
    soSeri: function () {
      this.soSeriTxt = this.soSeri.toLocaleString("en-En", { maximumFractionDigits: 10 });
    },
  },
  created: async function () { },
  async mounted() { },
  methods: {
    onClickTaoDl() {
      if (this.validateTaoDl()) {
        this.$bvModal
          .msgBoxConfirm(`Bạn thật sự muốn tạo dữ liệu kỳ cước ${this.kyCuoc}?`, {
            title: "Thông báo",
            size: "sm",
            buttonSize: "md",
            okVariant: "primary",
            headerClass: "p-2 pb-0 border-bottom-0",
            footerClass: "p-2 pt-0 border-top-0 justify-content-center",
            centered: true,
            noCloseOnBackdrop: true,
            okTitle: "Đồng ý",
            cancelTitle: "Không đồng ý",
          })
          .then((value) => {
            value && this.taoDuLieuGBC();
          });
      }
    },
    validateTaoDl() {
      if (!this.seri && (this.soSeriTxt.length == 0 || this.soSeriTxt.length == undefined)) {
        this.$toast.error("Trường seri bắt buộc nhập dữ liệu! \n\r Trường số seri bắt buộc nhập dữ liệu!");
        return false;
      }
      if (!this.seri) {
        this.$toast.error("Trường seri bắt buộc nhập dữ liệu!");
        return false;
      }
      if (this.soSeriTxt.length == 0 || this.soSeriTxt.length == undefined) {
        this.$toast.error("Trường số seri bắt buộc nhập dữ liệu!");
        return false;
      }
      return true;
    },
    onClickXuatFile: async function () {
      this.$bvModal
        .msgBoxConfirm(`Bạn chắc chắn muốn xuất dữ liệu phiếu thu?`, {
          title: "Thông báo",
          size: "sm",
          buttonSize: "md",
          okVariant: "primary",
          headerClass: "p-2 pb-0 border-bottom-0",
          footerClass: "p-2 pt-0 border-top-0 justify-content-center",
          centered: true,
          noCloseOnBackdrop: true,
          okTitle: "Đồng ý",
          cancelTitle: "Không đồng ý",
        })
        .then((value) => {
          if (value) {
            this.getDataExport();
            this.$bvModal.show("modal-export");
          }
        });
    },
    taoDuLieuGBC: async function () {
      const body = {
        kyCuoc: `${this.kyHoaDon}${this.chuKy}`,
        seri: this.seri,
        soSeri: this.soSeriTxt.replaceAll(",", ""),
      };
      console.log(body);
      this.timeCreate = "";
      this.$root.showLoading(true);
      let begin = new Date();
      try {
        var rs = await this.$root.context.post(`/web-thuno/api/thu-no/bao-cao/giay-bao-cuoc`, body);
        let end = new Date();
        this.$root.showLoading(false);
        this.timeCreate = moment.utc(end - begin).format("HH:mm:ss");
        if (rs.data[0].CODE_ERROR == "BSS-00000000")
          this.$toast.success(rs.data[0].CODE_MESSAGE);
        else
          this.$toast.error(rs.data[0].CODE_MESSAGE);
      } catch (error) {
        let end = new Date();
        this.$root.showLoading(false);
        this.timeCreate = moment.utc(end - begin).format("HH:mm:ss");
      }
    },
    onModalShown: async function () {
      console.log('test');
    },
    onModalHidden: async function () {
      console.log('test');
    },
    getDataExport: async function () {
      const param = {
        kyCuoc: `${this.kyHoaDon}${this.chuKy}`,
        loai: this.loaiPhieu
        // page: "1",
        // size: 1000
      }
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/bao-cao/ds-in-phieu-thu-hoa-don",
          param
        );
        console.log(rs)
        this.dsData = rs.data.data;
      } catch (error) { }
    }
  },
};
</script>
