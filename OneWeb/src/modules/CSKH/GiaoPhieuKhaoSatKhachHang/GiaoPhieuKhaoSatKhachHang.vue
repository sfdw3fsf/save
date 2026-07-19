<template src="./GiaoPhieuKhaoSatKhachHang.html"></template>
<style src="./GiaoPhieuKhaoSatKhachHang.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import { mapActions, mapState } from "vuex";
import moment from "moment";
import select2 from "@/components/Select2.vue";
import downloadexcel from "vue-json-excel";
import ComboboxGrid from "../../QLTN/components/ComboboxGrid";
import API from "../api/GiaoPhieuKhaoSatKhachHangApi";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
import { L10n } from "@syncfusion/ej2-base";
import NhanVienQuanLy from "../DanhSachPhieuKhaoSatOutbound/popup/NhanVienQuanLy.vue";
// import CapNhapTTLH from "../TraCuuKhachHangLon/popup/CapNhapTTLH.vue";
// import ThayDoiTTKhachHang from "./popup/modal-tt-lien-he/index.vue";
import ThongTinLienHe from "../../contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import Vue from "vue";

L10n.load({
  "vi-VN": {
    dropdowns: {
      noRecordsTemplate: "Không có dữ liệu",
      actionFailureTemplate: "",
    },
  },
});
const TRANGTHAI_PH_KS = {
  MOI: 1,
  HOANTHANH: 6,
};
export default {
  components: {
    breadcrumb,
    downloadexcel,
    appSelect2: select2,
    appComboboxGrid: ComboboxGrid,
    appNhanVienQuanLy: NhanVienQuanLy,
    ThongTinLienHe,
    // appThayDoiTTKhachHang: ThayDoiTTKhachHang,
  },
  name: "GiaoPhieuKhaoSatKhachHang",
  provide: {
    multiselect: [CheckBoxSelection],
  },
  data() {
    return {
      header: {
        title: "GIAO PHIẾU KHẢO SÁT KHÁCH HÀNG",
        list: [
          { name: "Chăm sóc khách hàng", link: "/cskh" },
          {
            name: "Giao phiếu khảo sát khách hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      fields: {
        dataSource: null,
        id: "DONVI_ID",
        parentID: "DONVI_CHA_ID",
        text: "TEN_DV",
        hasChildren: "hasChild",
      },
      pCboNguoiGiao: false,
      pCboNguoiNhan: false,
      pCboNguoiGiaoSelected: 0,
      pCboNguoiGiaoSelected_text: "",
      pCboNguoiNhanSelected: 0,
      pTxtTuPhieuDG: 0,
      pTxtDenPhieuDG: 0,
      pTxtGiaoTuNgay: new Date(),
      pTxtGiaoDenNgay: new Date(),
      pCboChonNgay: false,
      pIsTabGiaoPhieu: true,
      pLuotGiaoPhieuSelected: {},
      today: new Date(),

      pCboLoaiNhomSelected: 0,
      pCboLoaiHDSelected: 0,
      pCboLoaiHDOptions: [],
      pCboTrangThaiPhieuSelected: 1,
      pTxtTenTB: "",
      pTxtDiaChiLD: "",
      pTxtMaGD: "",
      pTxtMaTB: "",
      pTxtSoAo: "",
      pTxtKieuLD: "",
      pTxtLoaiKH: "",
      pTxtGhiChu: "",
      pTxtSoPhieuTu: 0,
      pTxtSoPhieuDen: 0,
      pTxtGhiChuGP: "",
      pTxtTuNgay: new Date(),
      pTxtDenNgay: new Date(),
      pCboDonViSelected: -1,
      pCboDichVuSelected: [],

      btnGiaoPhieuEnabled: true,
      btnHuyGiaoEnabled: true,
      btnXoaPhieuEnabled: true,
      dsThongTinGiaoKhaoSatItemSelecteds: [],
      dsLuotGiaoKhaoSatItemSelecteds: [],
      dsPhieuDaGiaoKhaoSatItemSelecteds: [],
      vid: -1,
      vloai: -1,
      dlgkhachHangId: 0,
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
      pCboNguoiGiaoSelected_GiaoPhieu: 0,
      pCboNguoiNhanSelected_GiaoPhieu: 0,
    };
  },
  computed: {
    ...mapState("cskhCommon", ["cayDonVi"]),
    ...mapState("giaophieukhaosatkhachhang", [
      "DsNhanVienGiao",
      "DsNhanVienNhan",
      "DsLuotGiaoKhaoSat",
      "DsPhieuDaGiaoKhaoSat",
      "DsDichVu",
      "DsThongTinGiaoKhaoSat",
      "DsLoaiHopDong",
    ]),
    treeDonViFields() {
      return {
        dataSource: this.cayDonVi,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "hasChild",
      };
    },
    check_AggregatesColumns(){
      return [{field: "STT",type: "Custom",footerTemplate: this.customTemplate,customAggregateFn: this.phieuksAggregate}];
    }
  },
  async created() {
    this.clearData();
    try {
      this.loading(true);
      await Promise.all([
        this.getCayDonViData(),
        this.getDsNhanVienGiao({
          nhanVienId: this.$auth.getNhanVienID(),
        }),
        this.getDsNhanVienNhan({ loaiNvId: 95 }),
        this.getDsDichVu(),
      ]);
      if (this.DsNhanVienGiao.length > 0) {
        let nv = this.DsNhanVienGiao.filter(
          (x) => x.NHANVIEN_ID == this.$auth.getNhanVienID()
        );
        console.log("nhân viên", nv);
        if (nv && nv.length > 0) {
          this.pCboNguoiGiaoSelected = nv[0].NHANVIEN_ID;
          this.pCboNguoiGiaoSelected_text = nv[0].TEN_HT;
          this.pCboNguoiGiaoSelected_GiaoPhieu = nv[0].NHANVIEN_ID;
          //   this.$refs.cboDsNhanVienGiao.dataGrid_selectedRowChanged(nv[0]);
        }
      }

      // if (this.DsNhanVienNhan.length > 0)
      //   this.pCboNguoiNhanSelected = this.DsNhanVienNhan[0].NHANVIEN_ID;
      this.pIsTabGiaoPhieu = true;
      this.pCboLoaiNhomSelected = 1;
      this.pCboDichVuSelected = this.DsDichVu.map((x) => x.DICHVUVT_ID);
      this.btnGiaoPhieuEnabled = true;
      this.btnHuyGiaoEnabled = false;
      this.btnXoaPhieuEnabled = false;
    } catch (e) {
      console.log(e);
    } finally {
      this.loading(false);
    }
  },
  async mounted() {},
  methods: {
    ...mapActions("cskhCommon", ["getCayDonViData"]),
    ...mapActions("giaophieukhaosatkhachhang", [
      "clearData",
      "clearGridData",
      "getDsNhanVienGiao",
      "getDsNhanVienNhan",
      "getDsLuotGiaoKhaoSat",
      "getDsPhieuDaGiaoKhaoSat",
      "getDsDichVu",
      "getDsThongTinGiaoKhaoSat",
      "getDsLoaiHopDong",
    ]),
    phieuksAggregate(){
      return `${this.dsThongTinGiaoKhaoSatItemSelecteds.length}/${this.DsThongTinGiaoKhaoSat.length}`;
    },
    selectedItemsChangedThongTinGiaoKhaoSat(value) {
      this.dsThongTinGiaoKhaoSatItemSelecteds = value;
      let dataGridObj = this.$refs.gridThongTinGiaoKhaoSat.$refs.grid.ej2Instances;
      dataGridObj.aggregateModule.refresh();
    },
    selectedItemsChangedDsLuotGiaoKhaoSat(value) {
      this.dsLuotGiaoKhaoSatItemSelecteds = value;
    },
    selectedItemsChangedDsPhieuDaGiaoKhaoSat(value) {
      this.dsPhieuDaGiaoKhaoSatItemSelecteds = value;
    },
    onDsNhanVienGiaoChanged(dataItem) {
      if (dataItem) {
        this.pCboNguoiGiaoSelected = dataItem.NHANVIEN_ID;
      }
    },
    onDsNhanVienNhanChanged(dataItem) {
      if (dataItem) this.pCboNguoiNhanSelected = dataItem.NHANVIEN_ID;
    },
    onDsNhanVienNhanChanged_TabGiaoPhieu(dataItem){
      if (dataItem) this.pCboNguoiNhanSelected_GiaoPhieu = dataItem.NHANVIEN_ID;
    },
    async gridLuotGiaoKhaoSat_selectedRowChanged(dataItem) {
      this.pLuotGiaoPhieuSelected = dataItem;
      if (dataItem) {
        try {
          this.loading(true);
          await this.getDsPhieuDaGiaoKhaoSat({
            kieuKs: 1, //api mac dinh 1
            giaoPhieuKsId: dataItem.GIAOPHIEU_KS_ID,
            loai: dataItem.LOAI,
            trangthai: 0, //source c# set = 0 cho ca 2 loai
            pageNo: 1,
            pageSize: 10000,
          });
        } catch (e) {
          console.log(e);
        } finally {
          this.loading(false);
        }
      }
    },
    async gridPhieuDaGiaoKhaoSat_selectedRowChanged(index,dataItem) {
      if (dataItem) {
        this.dlgkhachHangId = dataItem.KHACHHANG_ID;
        if (this.pLuotGiaoPhieuSelected.LOAI == 1) {
          // nhóm thi công
          this.vid = dataItem.HDKH_ID;
          this.vloai = 2;
        } else if (this.pLuotGiaoPhieuSelected.LOAI == 2) {
          // báo hỏng
          this.vid = dataItem.KHACHHANG_ID;
          this.vloai = 1;
        }
      } else {
        this.dlgkhachHangId = 0
        this.vid = -1
        this.vloai = -1
      }
    },
    async onNhanVienQuanLyClicked() {
      if (this.pIsTabGiaoPhieu) {
        if (this.DsThongTinGiaoKhaoSat.length == 0) {
          this.$root.toastError("Không có dữ liệu hiển thị !");
          return;
        }
      } else {
        if (this.DsPhieuDaGiaoKhaoSat.length == 0) {
          this.$root.toastError("Không có dữ liệu hiển thị !");
          return;
        }
      }
      this.$refs.popupNVQL.vid = this.vid;
      this.$refs.popupNVQL.vloai = this.vloai;
      this.$refs.popupNVQL.openDialog();
    },
    async onTimKiemClicked() {
      if (this.pIsTabGiaoPhieu) {
        if (this.pCboDonViSelected == -1) {
          this.$root.toastError("Chưa chọn đơn vị!");
          return;
        }
        let queryThongTinGiaoKhaoSat = {
          pageNo: 1,
          pageSize: 100000,
          nhom: this.pCboLoaiNhomSelected,
          loaiHdId: this.pCboLoaiHDSelected,
          dichVuVtId: this.pCboDichVuSelected.join(),
          donViId: this.pCboDonViSelected,
          tuNgay: moment(this.pTxtTuNgay).format("DD/MM/YYYY"),
          denNgay: moment(this.pTxtDenNgay).format("DD/MM/YYYY"),
          khaoSat: this.pCboTrangThaiPhieuSelected,
        };

        try {
          this.$root.showLoading(true);
          await this.getDsThongTinGiaoKhaoSat(queryThongTinGiaoKhaoSat);
        } catch (e) {
          console.log(e);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        let queryLuotGiaoKS = {
          pageNo: 1,
          pageSize: 10000,
          nguoiGiaoId: this.pCboNguoiGiao ? this.pCboNguoiGiaoSelected : 0,
          nguoiNhanId: this.pCboNguoiNhan ? this.pCboNguoiNhanSelected : 0,
          tuNgay: this.pCboChonNgay
            ? moment(this.pTxtGiaoTuNgay).format("DD/MM/YYYY")
            : "",
          denNgay: this.pCboChonNgay
            ? moment(this.pTxtGiaoDenNgay).format("DD/MM/YYYY")
            : "",
        };
        try {
          this.loading(true);
          await this.getDsLuotGiaoKhaoSat(queryLuotGiaoKS);
        } catch (e) {
          console.log(e);
        } finally {
          this.loading(false);
        }
      }
    },
    async onGiaoPhieuClicked() {
      if (this.pCboNguoiGiaoSelected_GiaoPhieu == 0) {
        this.$root.toastError("Chưa chọn người giao");
        return;
      }
      if (this.pCboNguoiNhanSelected_GiaoPhieu == 0) {
        this.$root.toastError("Chưa chọn người nhận");
        return;
      }
      this.kiemTraPhieuVaCheckPhieuChuaGiao();

      const dsSelected_PhieuGiao = this.$refs.gridThongTinGiaoKhaoSat.getSelectedRecords() || [];
      if (dsSelected_PhieuGiao.length <= 0) {
        this.$root.toastError("Bạn chưa chọn phiếu để giao !");
        return;
      }
    //   let dsPhieuGiao = this.DsThongTinGiaoKhaoSat.filter((x) =>
    //     this.dsThongTinGiaoKhaoSatItemSelecteds.includes(x.STT)
    //   );
      let dsPhieuGiao = dsSelected_PhieuGiao;
      let dsThueBao = [];
      dsPhieuGiao.forEach((element) => {
        dsThueBao.push({
          HDKH_ID: element.HDKH_ID,
          HDTB_ID: element.HDTB_ID,
          BAOHONG_ID: element.BAOHONG_ID,
          DIENTHOAI_LH: element.SO_DT,
          LANGIAO: element.LAN_KS,
        });
      });
      let dataGiaoPhieu = {
        nhom: this.pCboLoaiNhomSelected,
        dsThueBao: dsThueBao,
        nguoiGiaoId: this.pCboNguoiGiaoSelected_GiaoPhieu,
        nguoiNhanId: this.pCboNguoiNhanSelected_GiaoPhieu,
        ghiChu: this.pTxtGhiChuGP,
      };
      try {
        this.loading(true);
        let resposne = await this.$root.context.post(
          "/web-cskh/api/giao-phieu-ks-kh/giao-phieu",
          dataGiaoPhieu
        );
        if (resposne.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Giao phiếu khảo sát thành công !");
          await this.onTimKiemClicked();
        } else {
          this.$root.toastError(
            "Giao phiếu khảo sát thất bại: " + resposne.message_detail
          );
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi " + e.response.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onHuyGiaoClicked() {
      if (this.DsLuotGiaoKhaoSat.length > 0) {
        let dsHuy = this.$refs.gridLuotGiaoKhaoSat.getSelectedRecords() || [];

        // let dsHuy = this.DsLuotGiaoKhaoSat.filter((x) =>
        //   this.dsLuotGiaoKhaoSatItemSelecteds.includes(x.GIAOPHIEU_KS_ID)
        // );
        console.log(dsHuy);
        if (dsHuy.length > 0) {
          let dsHuyData = {
            dsGiaoPhieuKsId: dsHuy.map((x) => x.GIAOPHIEU_KS_ID).join(),
            nhanVienIdHuy: this.$auth.getNhanVienID(),
          };
          try {
            this.loading(true);
            let resposne = await this.$root.context.post(
              "/web-cskh/api/giao-phieu-ks-kh/huy",
              dsHuyData
            );
            if (resposne.error_code === "BSS-00000000") {
              this.$root.toastSuccess("Hủy lượt giao thành công !");
              await this.onTimKiemClicked();
            } else {
              this.$root.toastError(
                "Hủy lượt giao thất bại: " + resposne.message_detail
              );
            }
          } catch (e) {
            console.log(e);
            this.$root.toastError("Có lỗi " + e.response.data.message);
          } finally {
            this.loading(false);
          }
        } else {
          this.$root.toastError("Chưa chọn lượt giao để hủy !");
        }
      } else {
        this.$root.toastError("Không có lượt giao để hủy !");
      }
    },
    async onXoaPhieuClicked() {
      this.kiemTraPhieuVaCheckPhieu();
      if (this.DsPhieuDaGiaoKhaoSat.length == 0) {
        this.$root.toastError("Không có phiếu để xóa !");
        return;
      }

      if (
        this.pLuotGiaoPhieuSelected &&
        (this.pLuotGiaoPhieuSelected.LOAI == 1 ||
          this.pLuotGiaoPhieuSelected.LOAI == 2)
      ) {

        const dsSelected_DaGiaoKS = this.$refs.gridPhieuDaGiaoKhaoSat.getSelectedRecords() || [];
        if (dsSelected_DaGiaoKS.length <= 0) {
          this.$root.toastError("Bạn chưa chọn phiếu để xóa !");
          return;
        }
        let dsPhieuXoa = dsSelected_DaGiaoKS;
        // let dsPhieuXoa = this.DsPhieuDaGiaoKhaoSat.filter((x) =>
        //   this.dsPhieuDaGiaoKhaoSatItemSelecteds.includes(x.STT)
        // );
        let dsId =
          this.pLuotGiaoPhieuSelected.LOAI == 1
            ? dsPhieuXoa.map((x) => x.HDKH_ID)
            : dsPhieuXoa.map((x) => x.BAOHONG_ID);
        let xoaData = {
          giaoPhieuKsId: this.pLuotGiaoPhieuSelected.GIAOPHIEU_KS_ID,
          nguoiGiaoId: this.pCboNguoiGiaoSelected,
          loai: this.pLuotGiaoPhieuSelected.LOAI,
          dsId: dsId.join(),
          nhanVienIdhuy: this.$auth.getNhanVienID(),
        };
        try {
          this.loading(true);
          let resposne = await this.$root.context.post(
            "/web-cskh/api/giao-phieu-ks-kh/xoa",
            xoaData
          );
          if (resposne.error_code === "BSS-00000000") {
            this.$root.toastSuccess("Xóa phiếu thành công !");
            this.DsPhieuDaGiaoKhaoSat = [];
            await this.onTimKiemClicked();
          } else {
            this.$root.toastError(
              "Xóa phiếu thất bại: " + resposne.message_detail
            );
          }
        } catch (e) {
          console.log(e);
          this.$root.toastError("Có lỗi " + e.response.data.message);
        } finally {
          this.loading(false);
        }
      }
    },
    kiemTraPhieuVaCheckPhieuChuaGiao() {
      if (this.pTxtSoPhieuTu == 0 || this.pTxtSoPhieuDen == 0) return;
      if (this.pTxtSoPhieuTu > this.DsThongTinGiaoKhaoSat.length) {
        this.$root.toastError(
          '"Số phiếu từ" không được lớn hơn số lượng bản ghi trên lưới!'
        );
        return;
      }
      if (this.pTxtDenPhieuDG > this.DsThongTinGiaoKhaoSat.length) {
        this.$root.toastError(
          '"Số phiếu đến" không được lớn hơn số lượng bản ghi trên lưới!'
        );
        return;
      }
      if (this.pTxtTuPhieuDG > this.pTxtDenPhieuDG) {
        this.$root.toastError(
          '"Số phiếu từ" không được lớn hơn "Số phiếu đến"!'
        );
        return;
      }

      this.dsThongTinGiaoKhaoSatItemSelecteds = [];
      for (var i = this.pTxtTuPhieuDG; i <= this.pTxtDenPhieuDG; i++) {
        this.dsThongTinGiaoKhaoSatItemSelecteds.push(
          this.DsPhieuDaGiaoKhaoSat[i - 1].STT
        );
      }
    },
    kiemTraPhieuVaCheckPhieu() {
      if (this.pTxtTuPhieuDG == 0 || this.pTxtDenPhieuDG == 0) return;
      if (this.pTxtTuPhieuDG > this.DsPhieuDaGiaoKhaoSat.length) {
        this.$root.toastError(
          '"Số phiếu từ" không được lớn hơn số lượng bản ghi trên lưới!'
        );
        return;
      }
      if (this.pTxtDenPhieuDG > this.DsPhieuDaGiaoKhaoSat.length) {
        this.$root.toastError(
          '"Số phiếu đến" không được lớn hơn số lượng bản ghi trên lưới!'
        );
        return;
      }
      if (this.pTxtTuPhieuDG > this.pTxtDenPhieuDG) {
        this.$root.toastError(
          '"Số phiếu từ" không được lớn hơn "Số phiếu đến"!'
        );
        return;
      }

      this.dsPhieuDaGiaoKhaoSatItemSelecteds = [];
      for (var i = this.pTxtTuPhieuDG; i <= this.pTxtDenPhieuDG; i++) {
        this.dsPhieuDaGiaoKhaoSatItemSelecteds.push(
          this.DsPhieuDaGiaoKhaoSat[i - 1].STT
        );
      }
    },
    onSoPhieuTuKeyDown(event) {
      if (event.keyCode == 13) {
        this.kiemTraPhieuVaCheckPhieu();
      }
    },
    onSoPhieuDenKeyDown(event) {
      if (event.keyCode == 13) {
        this.kiemTraPhieuVaCheckPhieu();
      }
    },
    onDonViSelected(data) {
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      this.pCboDonViSelected = treeObj.selectedNodes[0];
    },
    gridThongTinGiaoKhaoSat_selectedRowChanged(index,dataItem) {
      this.dlgkhachHangId = "";
      if (dataItem) {
        this.dlgkhachHangId = dataItem.KHACHHANG_ID;
        this.pTxtMaGD = dataItem.MA_GD;
        this.pTxtMaTB = dataItem.MA_TB;
        this.pTxtTenTB = dataItem.TEN_TB;
        this.pTxtDiaChiLD = dataItem.DIACHI_LD;
        this.pTxtKieuLD = dataItem.TEN_KIEULD;
        this.pTxtLoaiKH = dataItem.TEN_LKHL;
        this.pTxtSoAo = dataItem.MA_LT;
        this.pCboDichVuSelected = [];
        this.pCboDichVuSelected.push(dataItem.DICHVUVT_ID);
        if (this.pCboLoaiNhomSelected == 1) {
          // nhóm thi công
          this.vid = dataItem.HDKH_ID;
          this.vloai = 2;
        } else if (this.pCboLoaiNhomSelected == 2) {
          // báo hỏng
          this.vid = dataItem.KHACHHANG_ID;
          this.vloai = 1;
        }
      } else {
        this.dlgkhachHangId = 0
        this.vid = -1
        this.vloai = -1
      }
    },
    onLienHeClicked() {
      if (this.pIsTabGiaoPhieu) {
        if (this.DsThongTinGiaoKhaoSat.length == 0) {
          this.$root.toastError("Không có dữ liệu hiển thị !");
          return;
        }
        console.log(this.dlgkhachHangId);
        // this.$refs.dlgTTLH.khachHangId = this.dlgkhachHangId;
        // this.$refs.dlgTTLH.onShow();
        this.$refs.thongtinlienhe.show()
      } else {
        if (this.DsPhieuDaGiaoKhaoSat.length == 0) {
          this.$root.toastError("Không có dữ liệu hiển thị !");
          return;
        }
        console.log(this.dlgkhachHangId);
        // this.$refs.dlgTTLH.khachHangId = this.dlgkhachHangId;
        // this.$refs.dlgTTLH.onShow();
        this.$refs.thongtinlienhe.show()
      }
    },
  },
  watch: {
    pIsTabGiaoPhieu: {
      handler: function (value) {
        this.clearGridData();

        this.vid = -1;
        this.vloai = -1;
        if (value) {
          this.btnGiaoPhieuEnabled = true;
          this.btnHuyGiaoEnabled = false;
          this.btnXoaPhieuEnabled = false;
        } else {
          this.btnGiaoPhieuEnabled = false;
          this.btnHuyGiaoEnabled = true;
          this.btnXoaPhieuEnabled = true;
        }
      },
    },
    pCboLoaiNhomSelected: {
      handler: async function (value) {
        try {
          this.$root.showLoading(true);
          await this.getDsLoaiHopDong({ nhomId: value });
          if (this.DsLoaiHopDong.length > 0) {
            this.pCboLoaiHDSelected = this.DsLoaiHopDong[0].LOAIHD_ID;
          }
        } catch (e) {
        } finally {
          this.$root.showLoading(false);
        }
      },
    },
  },
};
</script>
