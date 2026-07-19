<template src="./MoPhieuKhaoSat.html"></template>
<style src="./MoPhieuKhaoSat.scss"></style>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import MoPhieuKhaoSatApi from "../api/MoPhieuKhaoSatApi";
import { mapActions, mapState, mapGetters } from "vuex";
import select2 from "@/components/Select2.vue";
import YesNoModal from "../utils/YesNoModal.vue";
import { L10n } from "@syncfusion/ej2-base";
import {
  MultiSelectPlugin,
  CheckBoxSelection,
} from "@syncfusion/ej2-vue-dropdowns";
import Vue from "vue";
import ComboboxGrid from "../../QLTN/components/ComboboxGrid";

L10n.load({
  "vi-VN": {
    dropdowns: {
      noRecordsTemplate: "Không có dữ liệu",
      actionFailureTemplate: "",
    },
    "multi-select": {
      noRecordsTemplate: "Không có dữ liệu",
      overflowCountTemplate: "và ${count} giá trị khác..",
      totalCountTemplate: "${count} giá trị",
    },
  },
});
export default {
  provide: {
    multiselect: [CheckBoxSelection],
  },
  components: { breadcrumb, appSelect2: select2, appYesNoModal: YesNoModal, appComboboxGrid: ComboboxGrid },
  name: "MoPhieuKhaoSat",
  created() {},
  async mounted() {
    // this.loading(true);
    // await this.getCayDonViData({ pageSize: 10 });
    // this.loading(false);
    // this.fields.dataSource = this.cayDonViGetter;
    this.loading(true);
    await Promise.all([
      this.getCayDonViData(),
      this.getDsLoaiHopDong(),
      this.getDsDichVu(),
      this.getDsNhanVienGiao(),
      this.getDsNhanVienNhan(),
    ])
      .then(() => {
        this.loading(false);
      })
      .catch(() => {
        this.loading(false);
      });
  },
  destroyed() {},
  data() {
    return {
      fields: {
        dataSource: null,
        id: "DONVI_ID",
        parentID: "DONVI_CHA_ID",
        text: "TEN_DV",
        hasChildren: "hasChild",
      },
      dichVuFields: { value: "id", text: "text" },
      fieldsThueBaoMoPhieu: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "MA_LT",
          headerText: "Số ảo",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "LOAIHINH_TB",
          headerText: "Loại hình",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "NGAY_YC",
          headerText: "Ngày YC",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
          cssClass: "text-red",
        },
        {
          fieldName: "NGAY_HT",
          headerText: "Ngày HT",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "ROWID",
          headerText: "STT",
          allowFiltering: true,
          width: 70,
          allowSorting: false,
          cssClass: "text-blue",
          cellCssClass: "font-weight-bold",
        },
        {
          fieldName: "LAN_KS",
          headerText: "Lần gọi",
          allowFiltering: true,
          width: 130,
          allowSorting: false,
          cellCssClass: "font-weight-bold",
        },
        {
          fieldName: "TEN_TB",
          headerText: "Tên TB",
          allowFiltering: true,
          width: 130,
          allowSorting: false,
        },
        {
          fieldName: "DIACHI_LD",
          headerText: "Địa chỉ LĐ",
          allowFiltering: true,
          width: 130,
          allowSorting: false,
        },
        {
          fieldName: "TEN_DT",
          headerText: "Đối tượng",
          allowFiltering: true,
          width: 130,
          allowSorting: false,
        },
        {
          fieldName: "GOI_KT",
          headerText: "Gói KT",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "CHITIEU_TG",
          headerText: "Chỉ tiêu TG",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "DONVI_TN",
          headerText: "Đơn vị TN",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "NHANVIEN_TN",
          headerText: "Nhân viên TN",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "DONVI_QL",
          headerText: "Đơn vị QL",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "TRAM_VT",
          headerText: "Tổ TC",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "TRANGTHAI_HD",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "SO_DT",
          headerText: "Điện thoại KH",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày CN",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "TEN_KIEULD",
          headerText: "Kiểu YC",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "NGUOI_XL",
          headerText: "Người XL",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "LYDO_HONG",
          headerText: "Lý do hỏng",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "THOIGIAN_TH",
          headerText: "Thời gian TH",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "NGAY_HOI",
          headerText: "Ngày hỏi",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "NGUOI_TH",
          headerText: "NV thực hiện",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "TEN_LKHL",
          headerText: "Loại KH lớn",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "TEN_PLKH",
          headerText: "Phân loại KH",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
        {
          fieldName: "NHA_MANG",
          headerText: "Nhà mạng",
          allowFiltering: true,
          allowSorting: false,
          width: 130,
        },
      ],
      loaiNhomSelected: 1,
      dsLoaiHopDongSelected: "",
      dsLoaiHopDong: [],
      dsDichVu: [],
      dsDichVuSelected: [],
      tenTB: "",
      diaChiLD: "",
      maGD: "",
      maTB: "",
      soAo: "",
      kieuLD: "",
      loaiKHL: "",
      ghiChu: "",
      tuNgay: new Date(),
      denNgay: new Date(),
      today: new Date(),
      nguoiGiaoSelected: -1,
      nguoiNhanSelected: -1,
      soPhieuTu: 0,
      soPhieuDen: 0,
      henKSTuEnable: false,
      henKSDenEnable: false,
      henKSTu: null,
      henKSDen: null,
      ghiChuGP: "",
      dsNhanVienGiaoKS: [],
      dsNhanVienNhanKS: [],
      giaoPhieuKSId: -1,
      hdtbId: -1,
      hdkhId: -1,
      baoHongId: -1,
      ctSuyHaoId: -1,
      donViSelected: 0,
      phieuKSChecked: [],

      pageInfor: {
        pageNo: 1,
        pageSize: 10,
        totalRecords: 10,
      },
      dsThueBaoMoPhieu: [],
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" disabled style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
      tenDonViSearch:"",
      currentFindIndex: 0,
      selectedNodes: [],
    };
  },
  computed: {
    ...mapState("cskhCommon", ["cayDonVi"]),
    ...mapGetters("cskhCommon", [
      "cayDonViGetter",
      "dichVuVienThongEnumGetter",
    ]),
    ...mapGetters("mophieukhaosat", ["dsThueBaoMoPhieuGetter"]),
    treeDonViFields() {
      return {
        dataSource: this.cayDonVi,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "hasChild",
      };
    },
    thuebao_AggregatesColumns(){
      return [{field: "MA_TB",type: "Custom",footerTemplate: this.customTemplate,customAggregateFn: this.phieuksAggregate}];
    }
  },
  methods: {
    ...mapActions("cskhCommon", ["getCayDonViData"]),
    ...mapActions("mophieukhaosat", ["getDsThueBaoMoPhieu"]),
    phieuksAggregate(){
      return `${this.phieuKSChecked.length}/${this.dsThueBaoMoPhieu.length}`;
    },
    async getDsLoaiHopDong() {
      let data = {
        nhomId: this.loaiNhomSelected,
      };
      let response = await MoPhieuKhaoSatApi.getDsLoaiHopDong(this.axios, data);
      this.dsLoaiHopDong = [];
      if (response.data.error_code === "BSS-00000000") {
        if (response.data.data !== undefined) {
          // this.dsLoaiHopDong = response.data.data
          response.data.data.forEach((element) => {
            this.dsLoaiHopDong.push({
              text: element.TEN_LOAIHD,
              id: element.LOAIHD_ID,
            });
          });
        } else {
          this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
        }
        if (this.dsLoaiHopDong.length > 0)
          this.dsLoaiHopDongSelected = this.dsLoaiHopDong[0].id;
      } else {
        this.$root.toastError(respone.data.message);
      }
    },
    async getDsDichVu() {
      let response = await MoPhieuKhaoSatApi.getDsDichVu(this.axios);
      this.dsDichVu = [];
      if (response.data.error_code === "BSS-00000000") {
        if (response.data.data !== undefined) {
          response.data.data.forEach((element) => {
            this.dsDichVu.push({
              text: element.TEN_DVVT,
              id: element.DICHVUVT_ID,
            });
          });
          this.dsDichVuSelected = this.dsDichVu.map((x) => x.id);
        } else {
          this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
        }
      } else {
        this.$root.toastError(respone.data.message);
      }
    },
    async getDsNhanVienGiao() {
      let data = {
        nhanVienId: this.$auth.getNhanVienID(),
      };
      let response = await MoPhieuKhaoSatApi.getDsNhanVienGiao(
        this.axios,
        data
      );
      this.dsNhanVienGiaoKS = [];
      if (response.data.error_code === "BSS-00000000") {
        if (response.data.data !== undefined) {
          response.data.data.forEach((element) => {
            this.dsNhanVienGiaoKS.push({
              text: element.TEN_HT,
              id: element.NHANVIEN_ID,
            });
          });
          this.$nextTick(
            () => (this.nguoiGiaoSelected = this.$auth.getNhanVienID())
          );
        } else {
          this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
        }
      } else {
        this.$root.toastError(respone.data.message);
      }
    },
    async getDsNhanVienNhan() {
      let data = {
        loaiNvId: 95,
      };
      let response = await MoPhieuKhaoSatApi.getDsNhanVienNhan(
        this.axios,
        data
      );
      this.dsNhanVienNhanKS = [];
      if (response.data.error_code === "BSS-00000000") {
        if (response.data.data !== undefined) {
            this.dsNhanVienNhanKS = response.data.data;
        //   response.data.data.forEach((element) => {
        //     this.dsNhanVienNhanKS.push({
        //       text: element.TEN_NV,
        //       id: element.NHANVIEN_ID,
        //     });
        //   });

        //   this.$nextTick(() => {
        //     if (this.dsNhanVienNhanKS.length > 0) {
        //       console.log("zzz", this.dsNhanVienNhanKS[0].id);
        //       this.nguoiNhanSelected = this.dsNhanVienNhanKS[0].id;
        //     } else {
        //       this.nguoiNhanSelected = -1;
        //     }
        //   });
        } else {
          this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
        }
      } else {
        this.$root.toastError(respone.data.message);
      }
    },
    pagination_PageChanged: async function (args) {
      this.pageInfor.pageSize = args.pageSize;
      this.pageInfor.pageNo = args.pageIndex + 1;
      await this.fetchData();
      // try {
      //   this.loading(true)
      //   await this.getDsChiTietCapPaging(this.checkedDonVi.toString(), args.pageIndex, args.pageSize)
      // }catch (e){}
      // finally {
      //   this.loading(false)
      // }
      // this.$emit("pageChanged", args);
    },
    dateIsValid(date) {
      if (typeof date === 'object' && date !== null && typeof date.getTime === 'function' && !isNaN(date)) {
        return true;
      }
      return false;
    },
    async fetchData() {
        if(!this.dateIsValid(this.tuNgay)){
            this.$root.toastError("Từ ngày không đúng định dạng!");
            return;
        }
        if(!this.dateIsValid(this.denNgay)){
            this.$root.toastError("Đến ngày không đúng định dạng!");
            return;
        }
      this.loading(true);
      let response = null;
      try {
        this.items = [];
        let treeObj = document.getElementById("treeview").ej2_instances[0];
        let donViId = treeObj.selectedNodes[0];
        if (!donViId) {
          this.$root.toastError("Chọn đơn vị muốn lấy báo cáo !");
          return;
        }

        let postData = {
          nhom: this.loaiNhomSelected,
          loaiHdId: this.dsLoaiHopDongSelected,
          dichVuVtId: this.dsDichVuSelected.length == this.dsDichVu.length ? "" : this.dsDichVuSelected.join(),
          donViId: donViId,
          tuNgay: this.tuNgay ? moment(this.tuNgay).format("DD/MM/YYYY") : "",
          denNgay: this.denNgay ? moment(this.denNgay).format("DD/MM/YYYY") : "",
          pageNo: 1,
          pageSize: 10000,
        };
        //@fakedata
        // postData = {
        //         "nhom": 2,
        //         "loaiHdId": 1,
        //         "dichVuVtId": "4,3",
        //         "donViId": 35192,
        //         "tuNgay": "01/01/2021",
        //         "denNgay": "05/01/2021"
        // }

        let rs = await this.$root.context.post(
          "/web-cskh/api/mo-phieu-khao-sat/ds-thue-bao-mo-phieu?pageNo=1&pageSize=10000",
          postData
        );
        if (rs.error_code === "BSS-00000000") {
          this.dsThueBaoMoPhieu = rs.data.data || [];
          this.dsThueBaoMoPhieu.forEach((e, i) => (e.ROWID = i + 1));
          this.pageInfor.pageNo = rs.data.page;
          this.pageInfor.totalRecords = rs.data.totalElement;
          if(this.dsThueBaoMoPhieu.length > 0){
              this.recordClick({rowData:this.dsThueBaoMoPhieu[0]})
          }
        }
        console.log(postData);
        // await this.getDsThueBaoMoPhieu(postData);
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
      return response;
    },
    recordClick(data){
      let currentObj = data.rowData;
      this.maGD = currentObj.MA_GD;
      this.maTB = currentObj.MA_TB;
      this.tenTB = currentObj.TEN_TB;
      this.diaChiLD = currentObj.DIACHI_LD;
      this.kieuLD = currentObj.TEN_KIEULD;
      this.loaiKHL = currentObj.TEN_LKHL;
      this.giaoPhieuKSId = currentObj.GIAOPHIEU_KS_ID;
      this.nguoiNhanSelected = currentObj.NGUOINHAN_ID;
      if (this.loaiNhomSelected == 1) {
        this.hdtbId = currentObj.HDTB_ID;
        this.hdkhId = currentObj.HDKH_ID;
        this.baoHongId = 0;
        // let dichVuVTId = currentObj.DICHVUVT_ID;
        // switch (dichVuVTId) {
        //   case this.dichVuVienThongEnumGetter.ADSL:
        //   case this.dichVuVienThongEnumGetter.MEGA_EYES:
        //   //need api
        //   // ds = tchopdong.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
        //   // if (ds.Tables[0].Rows.Count > 0)
        //   //   txtSoAo.Text = ds.Tables[0].Rows[0]["ma_lt"].ToString();
        //   // else txtSoAo.Text = "";
        //   case this.dichVuVienThongEnumGetter.MEGAWAN:
        //   //need api
        //   // ds = tchopdong.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id);
        //   // if (ds.Tables[0].Rows.Count > 0)
        //   //   txtSoAo.Text = ds.Tables[0].Rows[0]["ma_lt"].ToString();
        //   // else txtSoAo.Text = "";
        //   case this.dichVuVienThongEnumGetter.TSL:
        //     //need api
        //     // ds = tchopdong.LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id);
        //     // if (ds.Tables[0].Rows.Count > 0)
        //     //   txtSoAo.Text = ds.Tables[0].Rows[0]["ma_lt"].ToString();
        //     // else txtSoAo.Text = "";
        //     this.soAo = currentObj.MA_LT;
        //     break;
        //   default:
        //     this.soAo = "";
        //   }
        this.soAo = currentObj.MA_LT;
      } else if (this.loaiNhomSelected == 2) {
        this.soAo = "";
        this.baoHongId = currentObj.BAOHONG_ID;
        this.hdkhId = 0;
      } else if (this.loaiNhomSelected == 3) {
        this.soAo = "";
        this.ctSuyHaoId = currentObj.CT_SUYHAO_ID;
        this.hdkhId = 0;
        this.baoHongId = 0;
      }
    },
    rowClicked(rowIndex) {
      let currentObj = this.$refs.gridThueBaoMoPhieu.dataItems[rowIndex];
      this.maGD = currentObj.MA_GD;
      this.maTB = currentObj.MA_TB;
      this.tenTB = currentObj.TEN_TB;
      this.diaChiLD = currentObj.DIACHI_LD;
      this.kieuLD = currentObj.TEN_KIEULD;
      this.loaiKHL = currentObj.TEN_LKHL;
      this.giaoPhieuKSId = currentObj.GIAOPHIEU_KS_ID;
      this.nguoiNhanSelected = currentObj.NGUOINHAN_ID;
      if (this.loaiNhomSelected == 1) {
        this.hdtbId = currentObj.HDTB_ID;
        this.hdkhId = currentObj.HDKH_ID;
        this.baoHongId = 0;
        // let dichVuVTId = currentObj.DICHVUVT_ID;
        // switch (dichVuVTId) {
        //   case this.dichVuVienThongEnumGetter.ADSL:
        //   case this.dichVuVienThongEnumGetter.MEGA_EYES:
        //   //need api
        //   // ds = tchopdong.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
        //   // if (ds.Tables[0].Rows.Count > 0)
        //   //   txtSoAo.Text = ds.Tables[0].Rows[0]["ma_lt"].ToString();
        //   // else txtSoAo.Text = "";
        //   case this.dichVuVienThongEnumGetter.MEGAWAN:
        //   //need api
        //   // ds = tchopdong.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id);
        //   // if (ds.Tables[0].Rows.Count > 0)
        //   //   txtSoAo.Text = ds.Tables[0].Rows[0]["ma_lt"].ToString();
        //   // else txtSoAo.Text = "";
        //   case this.dichVuVienThongEnumGetter.TSL:
        //     //need api
        //     // ds = tchopdong.LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id);
        //     // if (ds.Tables[0].Rows.Count > 0)
        //     //   txtSoAo.Text = ds.Tables[0].Rows[0]["ma_lt"].ToString();
        //     // else txtSoAo.Text = "";
        //     this.soAo = currentObj.MA_LT;
        //     break;
        //   default:
        //     this.soAo = "";
        //   }
        this.soAo = currentObj.MA_LT;
      } else if (this.loaiNhomSelected == 2) {
        this.soAo = "";
        this.baoHongId = currentObj.BAOHONG_ID;
        this.hdkhId = 0;
      } else if (this.loaiNhomSelected == 3) {
        this.soAo = "";
        this.ctSuyHaoId = currentObj.CT_SUYHAO_ID;
        this.hdkhId = 0;
        this.baoHongId = 0;
      }
    },
    update() {
      let selectedObjs = this.$refs.gridThueBaoMoPhieu.getSelectedRecords();
      if (selectedObjs.length == 0) {
        this.$root.toastError("Chưa chọn thuê bao!");
        return;
      }
      if (this.henKSTuEnable) {
        if (!this.henKSTu) {
          this.$root.toastError("Chưa chọn ngày hẹn KS từ!");
          return false;
        }
      }
      if (this.henKSDenEnable) {
        if (!this.henKSDen) {
          this.$root.toastError("Chưa chọn ngày hẹn KS đến!");
          return false;
        }
      }
      if (this.henKSTuEnable && this.henKSDenEnable) {
        if (this.henKSTu > this.henKSDen) {
          this.$root.toastError("Hẹn KS từ không thể lớn hơn Hẹn KS đến!");
          return false;
        }
      }
      this.$bvModal.show("updateConfirmPopup");
      // this.$refs.updateConfirmPopup.show();
    },
    async onUpdateConfirm() {
      console.log("onUpdateConfirm", this.nguoiNhanSelected);
      let selectedObjs = this.$refs.gridThueBaoMoPhieu.getSelectedRecords();
      let dsThueBao = [];
      selectedObjs.forEach((element) => {
        let thongTinThueBao = [];
        thongTinThueBao.push(element.HDKH_ID ? element.HDKH_ID : 0);
        thongTinThueBao.push(element.HDTB_ID ? element.HDTB_ID : 0);
        thongTinThueBao.push(element.BAOHONG_ID ? element.BAOHONG_ID : 0);
        thongTinThueBao.push(element.DIENTHOAI_KH ? element.DIENTHOAI_KH : " ");
        thongTinThueBao.push(
          (element.LANGIAO_MAX ? element.LANGIAO_MAX : 0) + 1
        );
        dsThueBao.push(thongTinThueBao.join());
      });
      let postData = {
        nhom: this.loaiNhomSelected,
        dsThueBao: dsThueBao.join("#"),
        nguoiGiaoId: this.nguoiGiaoSelected,
        nguoiNhanId:
          !this.nguoiNhanSelected || this.nguoiNhanSelected == ""
            ? -1
            : this.nguoiNhanSelected,
        ghiChu: this.ghiChuGP || "Tổ trưởng mở phiếu khảo sát",
        henKsTu: this.henKSTuEnable
          ? moment(this.henKSTu).format("DD/MM/YYYY HH:mm")
          : "",
        henKsDen: this.henKSDenEnable
          ? moment(this.henKSDen).format("DD/MM/YYYY HH:mm")
          : "",
        nhanVienCnId: this.$auth.getNhanVienID(),
        nguoiCn: this.$auth.getUserName(),
        // ipCn: "10.59.90.123",
      };

      MoPhieuKhaoSatApi.capNhapGiaoPhieuKS(this.axios, postData)
        .then(async (response) => {
          if (response.data.error_code === "BSS-00000000") {
            this.$root.toastSuccess("Cập nhật thành công!");
            await this.fetchData();
          } else {
            this.$root.toastError(response.data.message);
          }
        })
        .catch((reject) => {
          this.$root.toastError("Có lỗi xảy ra!: " + reject.data.data);
        });
    },
    onDonViSelect(item) {
      var treeObj = document.getElementById("treeview").ej2_instances[0];
      this.donViSelected = treeObj.selectedNodes;
    },
    selectedThueBaoMoPhieu(data) {
      this.phieuKSChecked = data;
      let dataGridObj = this.$refs.gridThueBaoMoPhieu.$refs.grid.ej2Instances;
      dataGridObj.aggregateModule.refresh();
    },
    onSoPhieuTuKeyPress() {},
    onSoPhieuDenKeyPress() {},
    onKiemTraVaCheckPhieu(event) {
      if (event.keyCode == 13) {
        if (this.soPhieuTu <= 0 || this.soPhieuDen <= 0){
          this.$root.toastError(
            '"Số phiếu từ" hoặc "Số phiếu đến" phải lớn hơn 0!'
          );
          return;
        }
        if (this.soPhieuTu > this.dsThueBaoMoPhieu.length) {
          this.$root.toastError(
            '"Số phiếu từ" không được lớn hơn số lượng bản ghi trên lưới!'
          );
          return;
        }
        if (this.soPhieuDen > this.dsThueBaoMoPhieu.length) {
          this.$root.toastError(
            '"Số phiếu đến" không được lớn hơn số lượng bản ghi trên lưới!'
          );
          return;
        }
        if (this.soPhieuTu > this.soPhieuDen) {
          this.$root.toastError(
            '"Số phiếu từ" không được lớn hơn "Số phiếu đến"!'
          );
          return;
        }
        if (this.henKSTuEnable && !this.henKSTu) {
          this.$root.toastError("Hãy chọn Ngày hẹn khảo sát từ");
          return;
        }
        if (this.henKSDenEnable && !this.henKSDen) {
          this.$root.toastError("Hãy chọn Ngày hẹn khảo sát đến");
          return;
        }
        let dsIds = [];
        for (let i = this.soPhieuTu; i <= this.soPhieuDen; i++) {
          let id = i - 1;
          dsIds.push(parseInt(id));
        }
        this.$refs.gridThueBaoMoPhieu.grid.selectRows(dsIds);
      }
    },
    onTenDonViSearch(event) {
      if (!this.isLoading && event.keyCode == 13) {
        if (this.tenDonViSearch) {
          let filterDonVi = this.treeDonViFields.dataSource
            .filter(x =>
              x.TEN_DV.toLowerCase().includes(
                this.tenDonViSearch.trim().toLowerCase()
              )
            )
            .sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));

          if (filterDonVi.length == 0) {
            this.$root.toastError('Không tìm thấy đơn vị phù hợp')
            return
          }

          let treeObj = document.getElementById("treeview").ej2_instances[0];

          if (this.currentFindIndex >= filterDonVi.length - 1)
            this.currentFindIndex = 0;

          this.selectedNodes = []
          treeObj.selectedNodes = [];
          if (filterDonVi[this.currentFindIndex]) {
            let donViId = filterDonVi[this.currentFindIndex].DONVI_ID;
            let dsDonViChaId = this.findParentOnTree(filterDonVi[this.currentFindIndex].DONVI_CHA_ID)
            console.log('dsDonViChaId', dsDonViChaId)

            this.$refs.treeObj.expandAll(dsDonViChaId)
            this.selectedNodes.push("" + donViId);
            this.$refs["treeObj"].ensureVisible("" + donViId);
            setTimeout(function() {
              // document.getElementById("treeview").ej2_instances[0].getElement("" + donViId).scrollIntoView({ behavior: "smooth" })
              this.scrollToTop
            }, 100)
            this.currentFindIndex++
          }

        }
      }
    },
    findParentOnTree(nodeParentId) {
      var dsParents = this.treeDonViFields.dataSource.filter(x => x.DONVI_ID == nodeParentId)
      var dsParentsId = []
      if (dsParents != null && dsParents.length > 0) {
        dsParentsId.push('' + dsParents[0].DONVI_ID)
        dsParentsId = dsParentsId.concat(this.findParentOnTree(dsParents[0].DONVI_CHA_ID))
      }
      return dsParentsId;
    },
    onTenDonViChange() {
      this.currentFindIndex = 0;
    },
    scrollToTop() {
      document.getElementById("txtTenDonViSearch").scrollIntoView({ behavior: "smooth" })
      // this.$refs.txtTenDonViSearch.scrollIntoView({ behavior: "smooth" })
    },
    onDsNhanVienNhanChanged(dataItem) {
      if (dataItem) this.nguoiNhanSelected = dataItem.NHANVIEN_ID;
    },
  },
  watch: {
    loaiNhomSelected: {
      handler: async function (value) {
        await this.getDsLoaiHopDong();
      },
    },
  },
};
</script>

