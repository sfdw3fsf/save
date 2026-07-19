<template src="./index.template.html"></template>
<script>
import leftPane from "./dando-diemdau";
import centerPane from "./dando-diemtrunggian";
import rightPane from "./dando-diemcuoi";
import popupChonThueBao from "./chon-thuebao";
import popupChonCap from "./chon-cap";
import popupQuyHoachSoiCap from "./quyhoach-soicap";
import QLCap from "../BanDoMangCap/modal/QLCapExt";
import TraCuuLyLichDayMay from "../../ECMS/tracuu-lylich-daymay";
import gianDo from "./gian-do";
import DiagramLib from "../../../utils/DiagramLib";
import { required, minLength, minValue } from "vuelidate/lib/validators";
import { Page, CommandColumn } from "@syncfusion/ej2-vue-grids";

export default {
  provide: {
    grid: [Page, CommandColumn],
  },
  components: {
    popupChonThueBao,
    popupChonCap,
    popupQuyHoachSoiCap,
    leftPane,
    centerPane,
    rightPane,
    gianDo,
    QLCap,
    TraCuuLyLichDayMay,
  },
  data: function () {
    return {
      inputMaThueBaoText: "",
      kieu: 0,
      dsThueBao: [],
      thongTinTB: null,
      dsSoi: [],
      thongTinCap: null,
      thongTinCapFromChonCap: null,
      SoiCapSelected: Number,
      danDoDau: [],
      danDoCuoi: [],
      dsCapTrungGian: [],
      kyHieuCap: "",
      dsCaps: [],
      dsSoiQuyHoach: [],
      dsSoiQuyHoachSelected: [],
      dsNodes: [],
      dsConnectors: [],
      gianDoRefresh: false,
      isEditCap: false,
    };
  },
  computed: {
    getEnabledInLyLich: function () {
      let rs = false;
      if (this.thongTinTB != null) {
        if (this.thongTinTB.IS_THUEBAO == 1) rs = true;
      }
      return rs;
    },
    getEnabledDsSoi: function () {
      return this.thongTinCap != null;
    },
    getThongTinCap: function () {
      if (this.thongTinCap != null) {
        let msg = "";
        if (this.thongTinCap.THONGTIN != undefined)
          msg =
            "<b>" +
            this.thongTinCap.THONGTIN.toString().replace("\r\n", " | ") +
            "</b>";
        else
          msg =
            "<b>" +
            this.thongTinCap.KYHIEU +
            "</b> | " +
            this.thongTinCap.CHIEUDAI +
            "m";
        return "<span class='text-primary'>Thông tin cáp: " + msg + ".</span>";
      } else {
        return "<span class='text-danger italic'>Chưa có thông tin cáp.</span>";
      }
    },
    hasCap: function () {
      return this.thongTinCap != null;
    },
    getThongTinSoi: function () {
      if (this.dsSoi == null || this.dsSoi.length == 0) {
        return "<span class='red'>Cáp chưa được quy hoạch sợi cáp hoặc các sợi cáp quy hoạch đã được cấp hết cho các thuê bao khác.</span>";
      } else {
        return (
          "<span class='text-success'>Còn " +
          this.dsSoi.length +
          " sợi cáp quy hoạch rỗi chưa cấp cho thuê bao.</span>"
        );
      }
    },
    maThueBao: function () {
      let rs = this.inputMaThueBaoText;
      if (rs != null) {
        if (rs.includes("|")) {
          rs = rs.substring(rs.indexOf("|") + 1);
        }
      }
      return rs;
    },
    kieuExt: function () {
      let rs = 0;
      if (this.inputMaThueBaoText.includes("|")) {
        rs = 2;
      }
      return rs;
    },
  },
  watch: {
    dsCapTrungGian: function (value) {
      this.veGianDo();
    },
  },
  validations: {
    inputMaThueBaoText: {
      required,
      minLength: minLength(4),
    },
    SoiCapSelected: {
      minValue: minValue(1),
    },
  },
  // created: async function(){
  //   await this.showFormDialog(499559, 0);
  // },
  methods: {
    showFormDialog: async function(gt_id, gt_kieu){
      this.kieu = gt_kieu;
      this.inputMaThueBaoText = (this.kieu == 0 ? "THUEBAO_ID|" : "HDTB_ID|") + gt_id;
      await this.btnShowModalChonThueBao_Click();
    },
    btnInLyLich_Click: function () {
      this.$bvModal.show("modalLyLichDayMay");
    },
    modalLyLichDayMay_Shown: function () {
      this.$refs.formLyLichDayMay.bindData(this.thongTinTB.MA_TB, this.kieu);
    },
    showModalChonThueBao: function () {
      this.popupChonThueBao_selectedRowChanged(
        this.dsThueBao == null || this.dsThueBao.length == 0
          ? null
          : this.dsThueBao[0]
      );
      this.$bvModal.show("modal-ChonThueBao");
    },
    btnShowModalChonThueBao_Click: async function () {
      this.$v.inputMaThueBaoText.$touch();
      if (!this.$v.inputMaThueBaoText.$error) {
        this.$root.showLoading(true);
        try {
          this.dsThueBao = await this.getDsThueBao();
          this.showModalChonThueBao();
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    inputMaThueBao_enter: async function () {
      this.$v.inputMaThueBaoText.$touch();
      if (!this.$v.inputMaThueBaoText.$error) {
        this.$root.showLoading(true);
        try {
          this.dsThueBao = await this.getDsThueBao();
          if (!(this.dsThueBao == null || this.dsThueBao.length == 0)) {
            if (this.dsThueBao.length == 1) {
              this.thongTinTB = this.dsThueBao[0];
              await this.chonThueBao();
            } else {
              this.showModalChonThueBao();
            }
          } else {
            this.$root.toastError(
              "Không tìm thấy dữ liệu tra cứu thuê bao, vui lòng thử lại."
            );
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.$root.toastError(
          "Không tìm thấy dữ liệu tra cứu thuê bao, vui lòng thử lại."
        );
      }
    },
    popupChonThueBao_ok: async function () {
      this.$root.showLoading(true);
      try {
        await this.chonThueBao();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    chonThueBao: async function () {
      if (this.thongTinTB != null) {
        if (this.thongTinTB.DICHVUVT_ID == 9) {
          if (
            this.thongTinTB.MA_TB.toString().endsWith("_01") ||
            this.thongTinTB.MA_TB.toString().endsWith("_02")
          )
            this.inputMaThueBaoText = this.thongTinTB.MA_TB.substring(
              0,
              this.thongTinTB.MA_TB.length - 3
            );
        } else {
          this.inputMaThueBaoText = this.thongTinTB.MA_TB;
        }
        //$("#inputMaThueBao").val(this.thongTinTB.MA_TB);
        $("#inputTenThueBao").val(this.thongTinTB.TEN_TB);
        $("#inputTenLoaiHinh").val(this.thongTinTB.LOAIHINH_TB);
        $("#inputTrangThai").val(this.thongTinTB.TRANGTHAI_TB);
        $("#inputDiaChiLapDat").val(this.thongTinTB.DIACHI_LD);
        $("#inputToQuanLy").val(this.thongTinTB.TEN_TOQL);
        $("#inputTramThietBi").val(this.thongTinTB.TEN_TRAMTBI);
        this.danDoDau = await this.getDanDoDau();
        this.danDoCuoi = await this.getDanDoCuoi();
        this.dsCapTrungGian = await this.getDsCapTrungGian();
      } else {
        this.inputMaThueBaoText = "";
        //$("#inputMaThueBao").val("");
        $("#inputTenThueBao").val("");
        $("#inputTenLoaiHinh").val("");
        $("#inputTrangThai").val("");
        $("#inputDiaChiLapDat").val("");
        $("#inputToQuanLy").val("");
        $("#inputTramThietBi").val("");
      }
    },
    popupChonThueBao_selectedRowChanged: function (dataItem) {
      this.thongTinTB = dataItem;
    },
    selectKyHieuCap_enter: async function () {
      this.$root.showLoading(true);
      try {
        this.thongTinCap = null;
        this.dsSoi = null;
        this.kyHieuCap = $("#selectKyHieuCap").val();
        if (!(this.kyHieuCap == null || this.kyHieuCap.length == 0)) {
          this.dsCaps = await this.getDsCapTheoKyHieu(this.kyHieuCap);
          if (!(this.dsCaps == null || this.dsCaps.length == 0)) {
            if (this.dsCaps.length == 1) {
              this.thongTinCap = this.dsCaps[0];
              this.dsSoi = await this.getDsSoiTheoCap(this.thongTinCap.CAP_ID);
              showModal = false;
            } else {
              this.$bvModal.show("modal-ChonCap");
            }
          } else {
            this.$root.toastError("Không tìm thấy thông tin cáp.");
          }
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    chonCap: async function () {
      this.thongTinCap = this.thongTinCapFromChonCap;
      if (this.thongTinCap != null) {
        $("#selectKyHieuCap").val(this.thongTinCap.KYHIEU);
        this.dsSoi = await this.getDsSoiTheoCap(this.thongTinCap.CAP_ID);
      } else {
        $("#selectKyHieuCap").val();
        this.dsSoi = null;
      }
    },
    popupChonCap_selectedRowChanged: function (dataItem) {
      this.thongTinCapFromChonCap = dataItem;
    },
    btnReloadSoi_Click: async function () {
      this.$root.showLoading(true);
      try {
        if (this.thongTinCap != null)
          this.dsSoi = await this.getDsSoiTheoCap(this.thongTinCap.CAP_ID);
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDsSoiTheoCap: async function (capID) {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsSoiTheoCap",
        { id: capID }
      );
      return rs.data;
    },
    getDsCapTheoKyHieu: async function (kyHieuCap) {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsCapTheoKyHieu",
        { kyHieu: kyHieuCap }
      );
      return rs.data;
    },
    getDsThueBao: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsThueBaoTheoMaTB",
        {
          maThueBao: this.maThueBao,
          kieu: parseInt(this.kieu) + parseInt(this.kieuExt),
        }
      );
      return rs.data;
    },
    getDsCapTrungGian: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/captrunggian/layDsCapTrungGianTheoThueBao",
        {
          thueBaoID: this.thongTinTB.THUEBAO_ID,
          kieu: this.thongTinTB.IS_THUEBAO == 0 ? 1 : 0,
        }
      );
      return rs.data;
    },
    getDanDoDau: async function () {
      let rs = await this.$root.context.post(
        this.thongTinTB.IS_THUEBAO == 0
          ? "/web-cabman/captrunggian/layThongTinCapTheoThueBaoHopDong"
          : "/web-cabman/captrunggian/layThongTinCapTheoThueBaoDanhBa",
        {
          id: this.thongTinTB.THUEBAO_ID,
          dauCuoiID: 1,
        }
      );
      if (!(rs.data == null || rs.data.length == 0)) {
        let danDoRutGon = await this.$root.context.post(
          "/web-cabman/captrunggian/layDanDoRutGonKCTheoKCID",
          {
            ketCuoiID: rs.data[0].KETCUOI_ID,
            mat: 2,
            viTri: rs.data[0].VITRI,
          }
        );
        return danDoRutGon.data;
      }
      return null;
    },
    getDanDoCuoi: async function () {
      let rs = await this.$root.context.post(
        this.thongTinTB.IS_THUEBAO == 0
          ? "/web-cabman/captrunggian/layThongTinCapTheoThueBaoHopDong"
          : "/web-cabman/captrunggian/layThongTinCapTheoThueBaoDanhBa",
        {
          id: this.thongTinTB.THUEBAO_ID,
          dauCuoiID: 2,
        }
      );
      if (!(rs.data == null || rs.data.length == 0)) {
        let danDoRutGon = await this.$root.context.post(
          "/web-cabman/captrunggian/layDanDoRutGonKCTheoKCID",
          {
            ketCuoiID: rs.data[0].KETCUOI_ID,
            mat: 2,
            viTri: rs.data[0].VITRI,
          }
        );
        return danDoRutGon.data;
      }
      return null;
    },
    nhapCapTrungGianThueBao: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/captrunggian/nhapCapTrungGianThueBao",
        {
          db_id: this.thongTinTB.THUEBAO_ID,
          cap_id: this.thongTinCap.CAP_ID,
          soi: $("#selectSoiCap").val(),
        }
      );
      return rs.data;
    },
    buttonXacNhan_Click: async function () {
      this.SoiCapSelected = $("#selectSoiCap").val();
      this.$v.SoiCapSelected.$touch();
      if (!this.$v.SoiCapSelected.$error) {
        if (
          !(this.thongTinTB == null || this.thongTinTB.THUEBAO_ID == undefined)
        ) {
          this.$root.showLoading(true);
          try {
            let result = await this.nhapCapTrungGianThueBao();
            if (result.result == true) {
              this.dsSoi = this.dsSoi.filter((item) => {
                return item.SOI != this.SoiCapSelected;
              });
              this.dsCapTrungGian = await this.getDsCapTrungGian();
              this.$root.toastSuccess(
                "Nhập cáp trung gian cho thuê bao thành công"
              );
            }
          } catch (error) {
          } finally {
            this.$root.showLoading(false);
          }
        } else {
          this.$root.toastError("Vui lòng chọn thuê bao trước khi xác nhận");
        }
      }
    },
    gridDsCapTrungGian_commandClick: async function (args) {
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn thật sự muốn muốn giải phóng thuê bao trên sợi cáp này không?",
          {
            title: "Xác nhận hành động",
            size: "sm",
            centered: true,
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then(async (value) => {
          if (value) {
            await this.giaiPhongSoiCap(args.rowData.CAP_ID, args.rowData.SOI);
          }
        });
    },
    giaiPhongSoiCap: async function (capID, soi) {
      this.$root.showLoading(true);
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/captrunggian/giaiPhongSoiCapTheoThueBao",
          {
            capID: capID,
            soi: soi,
            thueBaoID: this.thongTinTB.THUEBAO_ID,
            kieu: this.thongTinTB.IS_THUEBAO == 0 ? 0 : 1,
          }
        );
        if (rs != null) {
          if (rs.data.result == true) {
            this.dsCapTrungGian = this.dsCapTrungGian.filter((item) => {
              return !(item.CAP_ID == capID && item.SOI == soi);
            });
            this.$root.toastSuccess(
              "Giải phóng thuê bao trên sợi cáp thành công"
            );
          } else {
            this.$root.toastError(
              "Giải phóng thuê bao trên sợi cáp bị thất bại, vui lòng kiểm tra lại"
            );
          }
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDsSoiQuyHoach: async function () {
      if (this.thongTinCap != null) {
        let rs = await this.$root.context.post(
          "/web-cabman/captrunggian/layDsQuyHoachSoiTheoCap",
          {
            id: this.thongTinCap.CAP_ID,
          }
        );
        return rs.data;
      }
      return null;
    },
    panelChonSoiCap_Click: async function () {
      await this.chonSoiCapDeQuyHoach();
    },
    chonSoiCapDeQuyHoach: async function () {
      if (this.thongTinCap != null) {
        this.dsSoiQuyHoach = await this.getDsSoiQuyHoach();
        // if (!(this.dsSoiQuyHoach == null || this.dsSoiQuyHoach.length == 0)) {
        //   this.dsSoiQuyHoach = this.dsSoiQuyHoach.filter((item) => {
        //     return item.READONLY != "s1";
        //   });
        // }
        this.$bvModal.show("modal-QuyHoachSoiCap");
      } else
        this.$root.toastError("Vui lòng chọn cáp để mở cửa sổ quy hoạch sợi");
    },
    // popupQuyHoachSoiCap_selectedItemsChanged: async function (dataItems) {
    //   this.dsSoiQuyHoachSelected = dataItems;
    // },
    popupQuyHoachSoiCap_Ok: async function () {
      this.dsSoiQuyHoachSelected = this.$refs.controlPopupQuyHoachSoiCap.dsSoi;
      await this.quyHoachSoiCap();
    },
    quyHoachSoiCap: async function () {
      this.$root.showLoading(true);
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/captrunggian/quyHoachSoiTheoCap",
          {
            capID: this.thongTinCap.CAP_ID,
            soi:
              this.dsSoiQuyHoachSelected != null
                ? this.dsSoiQuyHoachSelected
                : [],
          }
        );
        if (rs != null) {
          if (rs.data.result == true) {
            this.dsSoi = await this.getDsSoiTheoCap(this.thongTinCap.CAP_ID);
            this.$root.toastSuccess("Quy hoạch sợi cáp thành công");
          } else {
            this.$root.toastError(
              "Quy hoạch sợi cáp bị thất bại, vui lòng kiểm tra lại"
            );
          }
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    veGianDo: function () {
      let nodes = [];
      let connectors = [];
      for (let i = 0; i < this.dsCapTrungGian.length; i++) {
        const capTrungGian = this.dsCapTrungGian[i];
        let lopmc_id = capTrungGian.LOPMC_ID;

        let icon_n = capTrungGian.ICON_N;
        let ten_n = capTrungGian.TEN_N;
        if (ten_n == "") {
          if (lopmc_id == "1" || lopmc_id == "4") {
            ten_n = "Cáp chưa có trạm nguồn";
          } else {
            ten_n = "Cáp chưa có kết cuối nguồn";
          }
          icon_n = "ADD";
        }

        let nodeN = this.taoNode(
          capTrungGian.NGUON_ID + i.toString(),
          ten_n,
          icon_n
        );
        nodeN.offsetX = 100;
        nodeN.offsetY = 150 * (i + 1);
        nodes.push(nodeN);

        let icon_d = capTrungGian.ICON_D;
        let ten_d = capTrungGian.TEN_D;
        if (ten_d == "") {
          if (lopmc_id == "3" || lopmc_id == "4") {
            ten_d = "Cáp chưa có trạm đích";
          } else {
            ten_d = "Cáp chưa có kết cuối đích";
          }
          icon_d = "ADD";
        }

        let nodeD = this.taoNode(
          capTrungGian.DICH_ID + i.toString(),
          ten_d,
          icon_d
        );
        nodeD.offsetX = 500;
        nodeD.offsetY = 150 * (i + 1);
        nodes.push(nodeD);

        connectors.push(
          this.taoKetNoi(
            capTrungGian.STYLE,
            capTrungGian.CAP_ID + i.toString(),
            nodeN,
            nodeD,
            capTrungGian.TEN_CAP
          )
        );
      }
      this.dsNodes = nodes;
      this.dsConnectors = connectors;
    },
    taoNode: function (nodeID, text, stringStyle) {
      text =
        "<div style='position: absolute; top: -60px; left: -40px;'><div class='text-nowrap' style='padding: 7px 10px; border: 1px solid #E0E0E0; background-color: #F5F5F5; display: inline-table; border-radius: 5px;'>" +
        text +
        "</div></div>";
      let ports = [];
      ports.push(DiagramLib.taoPort(nodeID + "_port_1", DiagramLib.PORT_TRAI));
      ports.push(DiagramLib.taoPort(nodeID + "_port_2", DiagramLib.PORT_PHAI));
      let annotations = [];
      annotations.push({ template: text, style: { textWrapping: "NoWrap" } });
      let node = DiagramLib.taoNodeTheoStyle(
        stringStyle,
        nodeID,
        ports,
        annotations
      );
      return node;
    },
    taoKetNoi: function (stringStyle, id, nodeSource, nodeTarget, text) {
      let sourceID = nodeSource.id;
      let sourcePortID = nodeSource.ports[1].id;
      let targetID = nodeTarget.id;
      let targetPortID = nodeTarget.ports[0].id;
      //let style = { strokeWidth: 4, strokeColor: "#0176FF", fill: "#0176FF", strokeDashArray: "20" };
      let annotations = [
        {
          height: 25,
          width: 120,
          content: text,
          margin: { top: 20 },
          style: {
            textWrapping: "NoWrap",
            fill: "#F5F5F5",
            strokeWidth: 1,
            strokeColor: "#E0E0E0",
          },
        },
      ];
      let ketNoi = DiagramLib.taoKetNoiTheoStyle(
        stringStyle,
        id,
        sourceID,
        targetID,
        sourcePortID,
        targetPortID,
        annotations
      );
      ketNoi.targetDecorator = {
        shape: "None",
      };
      return ketNoi;
    },
    refreshGianDo: function () {
      this.gianDoRefresh = !this.gianDoRefresh;
    },
    buttonQuyHoachCap_Click: async function () {
      // this.isEditCap = true;
      // this.$bvModal.show("popupQLCap");
      //await this.selectKyHieuCap_enter();
      await this.chonSoiCapDeQuyHoach();
    },
    buttonXemCap_Click: async function () {
      await this.$refs.popupQLCap
        .suaCap({ cap_id: this.thongTinCap.CAP_ID })
        .then(async (result) => {
          if (result.ok) {
            $("#selectKyHieuCap").val(result.result.kyhieu);
            await this.selectKyHieuCap_enter();
          } else {
          }
        });
    },
  },
};
</script>
<style>
.bss-modal .page-content {
  position: relative;
  top: 0;
}
.bss-modal .vue-html2pdf .pdf-preview {
  position: initial !important;
  transform: initial !important;
  width: 100% !important;
}
.vue-html2pdf .pdf-preview button {
  display: none !important;
}
</style>
