<template src="./UpdateLogThread.html"></template>
<style>
.e-grid .e-rowcell.bold{
    font-weight: bold;
}
.e-grid .e-rowcell.purple{
    font-weight: bold;
    color: purple;
}
.e-grid .e-rowcell.colred{
    font-weight: bold;
    color: red;
}
.e-grid .e-rowcell.blue{
    font-weight: bold;
    color: blue;
}
.khLonColor{
    color: red;
}
</style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import moment from "moment";
import treemenu from "@/components/Shared/treemenu";
import popupNhapTTXuLy from "./popupNhapTTXuLy.vue";
import ThongTinHenKhachHangBH from "./popups/ThongTinHenKHBH/ThongTinHenKhachHangModal.vue";
import ThongTinHenKhachHangTC from "./popups/ThongTinHenKHTC/ThongTinHenKhachHangModal.vue";
import { DichVuVienThong } from "@/modules/contract/setup/ChangeInternetSpeed/ThamSo";
export default {
  components: {
    breadcrumb,
    treemenu,
    popupNhapTTXuLy,
    ThongTinHenKhachHangBH,
    ThongTinHenKhachHangTC,
  },
  data: function () {
    return {
      dateconfig: {
        altFormat: "d/m/Y",
        altInput: true,
        dateFormat: "d/m/Y",
        allowInput: true,
      },
      header: {
        title: "Cập nhật thông tin xử lý tiến trình TC/ BH",
      },
       list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'}
          }
        ],
      donviID: 0,
      donviIDCHA: -1,
      VNPT_NET_UPLOAD_FILE_TIEN_TRINH: -1,
      vtrangthai_id: null,
      tag: 0,
      kieugoi_id: 0,
      txtMaTB: "",
      txtKieuLD: "",
      txtTenTB: "",
      txtKHLon: "",
      txtDiaChiLD: "",
      txtSoAo: "",
      txtGhiChu: "",
      tabActive: "thicong",
      tabVisible_TC: true,
      tabVisible_BH: true,
      loaikhlon_id: "0",
      hdkh_id: 0,
      params: {
        p_loainhom: 0,
        p_loainhom_options: [],
        p_dichvu: null,
        p_dichvu_options: [],
        p_trangthai: null,
        p_trangthai_options: [],
        p_loaihd_options: [],
        p_loaihd: null,
        ghiChu_TTXL: "",
        KQXL_ID: null,
      },
      TreeData: {},
      selectedNodes: [],
      fields: {
        dataSource: null,
        id: "donvi_id",
        parentID: "donvi_cha_id",
        text: "ten_dv",
        hasChildren: "hasChild",
      },
      isTimTheoNgay: false,
      tungay: new Date(),
      denngay: new Date(),
      today: new Date(),
      grcLSHen: [],
      gridBaoTon: [],
      gridTienTrinh: [],
      gridDanhSachTC: [],
      listItemDSTC: [],
      listItemDSBH: [],
      listItemThongTinXuLy: [],
      gridDanhSachBH: [],
      dtList: [],
      listItemDonViGoc: [],
      btnNhapMoi: false,
      iseditTTXL: 0, // 0 nhập mới. 1 edit
      btnXuatExcel: false,

      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      gridThongTinXuLy: {
        data: [],
        headers: [
          {
            fieldName: "KETQUA_XL",
            headerText: "Kết quả xử lý",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "left",
          },
          {
            fieldName: "GHICHU",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "left",
          },
          {
            fieldName: "MA_NV",
            headerText: "Người CN",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "left",
          },
          {
            fieldName: "NGAYGIAO",
            headerText: "Ngày CN",
            allowFiltering: true,
            type: "date",
            format: "dd/MM/yyyy HH:mm:ss",
            allowSorting: false,
            textAlign: "right",
          },
          {
            fieldName: "PHIEU_ID",
            headerText: "Sửa",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "Center",
            width: 90,
            template: function () {
              return {
                template: Vue.component("suaTemplate", {
                  template: `<a class="btn btn-huylydo lh14" @click="handleSua"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                  <span class="icon nc-icon-glyph ui-1_edit-74"></span></a>`,
                  data() {
                    return { data: {} };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    handleSua() {
                      this.parent.onClickSuaPhieu(this.data);
                    },
                  },
                }),
              };
            },
          },
          {
            fieldName: "PHIEU_ID",
            headerText: "Xóa",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "Center",
            width: 90,
            template: function () {
              return {
                template: Vue.component("xoaTemplate", {
                  template: `<a class="btn btn-huylydo lh14" @click="handleXoa"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                  <span class="-ap icon-close"></span></a>`,
                  data() {
                    return { data: {} };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    handleXoa() {
                      this.parent.onClickXoaPhieu(this.data);
                    },
                  },
                }),
              };
            },
          },
        ],
        selected: 0,
        selectItem: null,
        checked: [],
      },
    };
  },
  validations: {},
  created() {
    this.loadLoaiNhom();
    this.loadDichVu();
    this.NAP_CBO_TRANGTHAI();
    this.getDonViTheoND();
    if (
      this.$route.params &&
      this.$route.params.tag !== null &&
      this.$route.params.tag !== undefined
    ) {
      this.tag = this.$route.params.tag;
    }
    if (this.kieugoi_id == 0) {
      this.kieugoi_id = this.tag;
    } else {
      this.kieugoi_id = 1;
    }
  },
  computed: {
    timtheongay: {
      get() {
        return this.isTimTheoNgay;
      },
      set(value) {
        this.isTimTheoNgay = value;
      },
    },
  },
  methods: {
    //Lấy đơn vị theo người dùng
    async getDonViTheoND() {
      this.loading(true);
      let apiParams = {
        v_ma_nd: this.$root.token.getUserName(),
      };
      API.getDonViIDTheoND(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.donviIDCHA =
              response.data.data == 900 ? 0 : response.data.data;
            this.donviID = this.donviIDCHA;
            this.loadDonViGoc(this.donviIDCHA);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    //lấy tên đơn vị gốc
    async loadDonViGoc(donviid) {
      this.loading(true);
      API.getDonViTheoID(this.axios, { v_donvi_id: donviid })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({
                DONVI_ID: item.donvi_id,
                DONVI_CHA_ID: null,
                TEN_DV: item.ten_dv,
                hasChild: true,
              });
            });
            this.listItemDonViGoc = items;
            this.getDonVi(response.data.data[0].donvi_id.toString());
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    // danh sách các đơn vị con
    async getDonVi(donvicha) {
      this.loading(true);
      API.getDonvi(this.axios, donvicha)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.listItemDonViGoc.push(...response.data.data);
            let donViTree = this.createTreeDonVi(this.listItemDonViGoc);
            this.fields = {
              dataSource: donViTree,
              id: "DONVI_ID",
              parentID: "DONVI_CHA_ID",
              text: "TEN_DV",
              hasChildren: "hasChild",
            };
          }
        })
        .catch((error) => {
          this.$toast.error("er " + error.toString());
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    createTreeDonVi(dsDonVi) {
      let tree = [];
      let donviChaArray = dsDonVi.map((item) => item["DONVI_CHA_ID"]);
      let donviArray = dsDonVi.map((item) => item["DONVI_ID"]);
      dsDonVi.forEach((item) => {
        //check item la parent node
        if (donviChaArray.includes(item["DONVI_ID"])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item["DONVI_CHA_ID"])) {
            delete item.DONVI_CHA_ID;
          }
          tree.push({ ...item, hasChild: true, expanded: true });
        } else {
          tree.push(item);
        }
      });
      return tree;
    },
    loadLoaiNhom() {
      this.loading(true);
      API.getLoaiNhom(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.nhom_id, text: item.ten_nhom });
              });
              this.params.p_loainhom_options = items;
              this.params.p_loainhom = items[0].id;
              this.NAP_CBO_LOAI_HD_PL(items[0].id);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    onChangeLoaiNhom(agrs) {
      this.dtList = [];
      this.NAP_CBO_LOAI_HD_PL(agrs.id);
      if (agrs.id == 1) {
        this.tabActive = "thicong";
        /* this.tabVisible_TC = true;
        this.tabVisible_BH = false; */
      }
      if (agrs.id == 2) {
        this.tabActive = "baohong";
        /*  this.tabVisible_TC = false;
        this.tabVisible_BH = true; */
      }
    },
    NAP_CBO_LOAI_HD_PL(loainhom) {
      if (loainhom == 1) {
        API.getLoaiHD(this.axios)
          .then((response) => {
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined &&
              response.data.data.length > 0
            ) {
              let itemTatCa = [{ id: 0, text: "Tất cả" }];
              let items = [];
              response.data.data.forEach(function (item) {
                items.push({ id: item.LOAIHD_ID, text: item.TEN_LOAIHD });
              });
              let arr = [2, 9, 10, 13, 15, 17, 18, 20, 24];
              this.params.p_loaihd_options = [
                ...itemTatCa,
                ...items.filter((x) => !arr.includes(x.id)),
              ];
              this.params.p_loaihd_options.sort(
                (a, b) => parseFloat(a.id) - parseFloat(b.id)
              );
              this.params.p_loaihd = 0;
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
      }
      if (loainhom == 2) {
        let items = [{ id: 100, text: "Xử lý sự cố" }];
        this.params.p_loaihd_options = items;
        this.params.p_loaihd = 100;
      }
      if (loainhom == 3) {
        let items = [{ id: 100, text: "Bảo dưỡng dịch vụ" }];
        this.params.p_loaihd_options = items;
        this.params.p_loaihd = 100;
      }
    },
    loadDichVu() {
      this.loading(true);
      API.getDVVT(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.dichvuvt_id, text: item.ten_dvvt });
              });
              this.params.p_dichvu_options = items;
              this.params.p_dichvu = items[0].id;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    NAP_CBO_TRANGTHAI() {
      API.getTrangThai(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.trangthai_id, text: item.ten_nhom });
              });
              this.params.p_trangthai_options = items;
              this.params.p_trangthai = items[0].id;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async HienThiDanhSachHDTB() {
      /*  if(this.txtMaTB.trim() == ""){
        this.$toast.error("Vui lòng nhập Số máy/Acc !");
        this.$refs.inputMaTB.focus();
        return false;
      } */
      if (this.donviIDCHA == -1) {
        this.$toast.error("Chọn đơn vị muốn lấy báo cáo !");
        return false;
      }
      let ngaygiao = "";
      let denngay = "";
      if (this.isTimTheoNgay) {
        ngaygiao = moment(this.tungay).format("DD/MM/YYYY");
        denngay = moment(this.denngay).format("DD/MM/YYYY");
      }
      this.loading(true);
      await API.getDSPhieuHenTC(this.axios, {
        vma_gd: "0", //fix theo code cũ
        vloaihd_id: this.params.p_loaihd,
        vdichvuvt_id: this.params.p_dichvu,
        vngay_giao: ngaygiao ? ngaygiao : "0",
        vngaytra: denngay ? denngay : "0",
        vmatb: this.txtMaTB ? this.txtMaTB : "0",
        vnhanvien_id: this.$root.token.getNhanVienID(), //"0",
        vdonvi_id: this.donviID,
        vtrnagthai_idr: this.params.p_trangthai,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.dtList = response.data.data;
            this.HT_Datagrid();
            this.NAP_GRD_TIENTRINH_v2(1);
          } else {
            this.$toast.error(
              "Không tìm thấy thông tin thuê bao " + this.txtMaTB.trim()
            );
            this.gridDanhSachTC=[];
            this.txtKieuLD = "";
            this.txtTenTB = "";
            this.txtDiaChiLD = "";
            this.txtGhiChu = "";
            this.txtSoAo = "";
            this.txtKHLon = "";
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    async HienThiDanhSachBaoHong() {
      /* if(this.txtMaTB.trim() == ""){
        this.$toast.error("Vui lòng nhập Số máy/Acc !");
        this.$refs.inputMaTB.focus();
        return false;
      } */
      if (this.donviIDCHA == -1) {
        this.$toast.error("Chọn đơn vị muốn lấy báo cáo !");
        return false;
      }
      let ngaygiao = "";
      let denngay = "";
      if (this.isTimTheoNgay) {
        ngaygiao = moment(this.tungay).format("DD/MM/YYYY");
        denngay = moment(this.denngay).format("DD/MM/YYYY");
      }
      this.loading(true);
      await API.getDSPhieuHenBH(this.axios, {
        vdichvuvt_id: this.params.p_dichvu,
        vdonvi_id: this.donviID,
        vtrangthaibh_id: 1, //fix theo code cũ
        vngaybh_tu: ngaygiao,
        vngaybh_den: denngay,
        vngaynt_tu: "", //fix theo code cũ
        vngaynt_den: "",
        vma_tb: this.txtMaTB ? this.txtMaTB : "",
        vtrangthai_id: this.params.p_trangthai,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.dtList = response.data.data;
             this.HT_Datagrid();
            this.NAP_GRD_TIENTRINH_v2(2);
          } else {
            this.$toast.error(
              "Không tìm thấy thông tin thuê bao " + this.txtMaTB.trim()
            );
            this.gridDanhSachBH = [];
            this.txtKieuLD = "";
            this.txtTenTB = "";
            this.txtDiaChiLD = "";
            this.txtGhiChu = "";
            this.txtSoAo = "";
            this.txtKHLon = "";
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
     
    },
    btnTimKiem_Click() {
      this.grcLSHen = [];
      this.gridTienTrinh = [];
      this.gridBaoTon = [];
      if (this.params.p_loainhom == 1) {
        this.tabActive = "thicong";
        this.tabVisible_TC = true;
        this.tabVisible_BH = false;
        this.HienThiDanhSachHDTB();
      }
      if (this.params.p_loainhom == 2) {
        this.tabActive = "baohong";
        this.tabVisible_TC = false;
        this.tabVisible_BH = true;
        this.HienThiDanhSachBaoHong("");
      }
    },
    layds_ton_bh(vhdtb_id, vbaohong_id) {
      this.gridBaoTon = [];
      API.lay_ds_hen_kh(this.axios, {
        vkieu: 3,
        vhdtb_id: vhdtb_id,
        vbaohong_id: vbaohong_id,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.gridBaoTon = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    naplichsuhen(loai, vhdtb_id, vbaohong_id) {
      this.loading(true);
      this.grcLSHen = [];
      API.lay_ds_hen_kh(this.axios, {
        vkieu: loai,
        vhdtb_id: vhdtb_id,
        vbaohong_id: vbaohong_id,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.grcLSHen = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    loadDSTienTrinhBH(vbaohongid) {
      this.loading(true);
      this.gridTienTrinh = [];
      API.getDSTienTrinhBH(this.axios, { vbaohong_id: vbaohongid })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.gridTienTrinh = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    loadDSTienTrinhTC(hdtbId) {
      this.loading(true);
      this.gridTienTrinh = [];
      API.getDSTienTrinhTC(this.axios, hdtbId)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.gridTienTrinh = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    loadDSThongTinXL(vkieu, vphieuid) {
      this.gridThongTinXuLy.data = [];
      this.loading(true);
      API.getThongTinXLTienTrinh(this.axios, {
        vkieu: vkieu,
        vphieu_id: vphieuid,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.gridThongTinXuLy.data = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    HT_Datagrid() {
      if (this.params.p_loainhom == 1) {
        if (this.dtList.length > 0) {
          this.gridDanhSachTC = this.dtList;
        } else {
          this.gridDanhSachTC = [];
          this.txtMaTB = "";
          this.txtKieuLD = "";
          this.txtTenTB = "";
          this.txtDiaChiLD = "";
        }
      } else if (this.params.p_loainhom == 2 || this.params.p_loainhom == 3) {
        if (this.dtList.length > 0) {
          this.gridDanhSachBH = this.dtList;
        } else {
          this.gridDanhSachBH = [];
          this.txtMaTB = "";
          this.txtKieuLD = "";
          this.txtTenTB = "";
          this.txtDiaChiLD = "";
        }
      }
    },
    async NAP_GRD_TIENTRINH_v2(loai) {
      this.btnXuatExcel = false;
      if (loai == 2 || loai == 3) {
        if (this.listItemDSBH.BAOHONG_ID >= 0) {
          this.btnXuatExcel = true;
          this.hdkh_id = this.listItemDSBH.BAOHONG_ID;
          let vbaohong_id = this.listItemDSBH.BAOHONG_ID;
          let vthuebao_id = this.listItemDSBH.THUEBAO_ID;
          let vdichvuvt_id = this.listItemDSBH.DICHVUVT_ID;
          let vtrangthai_id = this.listItemDSBH.TRANGTHAI;
          let vphieu_id = this.listItemDSBH.PHIEU_ID;

          this.txtMaTB = this.listItemDSBH.MA_TB;
          this.txtKieuLD = this.listItemDSBH.TEN_KIEULD;
          this.txtTenTB = this.listItemDSBH.TEN_TB;
          this.txtDiaChiLD = this.listItemDSBH.DIACHI_LD;
          this.txtGhiChu = this.listItemDSBH.GHICHU_TB;
          var vdanhba_con = "";
          switch (vdichvuvt_id) {
            case DichVuVienThong.CO_DINH:
              vdanhba_con = 1;
              break;
            case DichVuVienThong.ADSL:
            case DichVuVienThong.MEGA_EYES:
              vdanhba_con = 2;
              break;
            case DichVuVienThong.TSL:
              vdanhba_con = 3;
              break;
          }
          //console.log("DBCON: " + vdanhba_con);
          await API.fn_tt_db_con(this.axios, { param: vthuebao_id, type: vdanhba_con })
            .then((response) => {
              if (
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000" &&
                response.data.data != -1
              ) {
                this.txtSoAo = response.data.data;
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {});
          this.naplichsuhen(2, 0, vbaohong_id);
          this.loadDSTienTrinhBH(vbaohong_id);
          this.layds_ton_bh(0, vbaohong_id);
          this.loadDSThongTinXL(2, vphieu_id);
          this.btnNhapMoi = true;
        } else {
          this.gridTienTrinh = [];
          this.gridThongTinXuLy.data = [];
          this.txtMaTB = "";
          this.txtKieuLD = "";
          this.txtTenTB = "";
          this.txtDiaChiLD = "";
          this.txtSoAo = "";
          this.txtGhiChu = "";
          this.btnNhapMoi = false;
        }
      } else {
        if (this.listItemDSTC.PHIEU_ID >= 0) {
          this.btnXuatExcel = true;
          let vphieu_id = this.listItemDSTC.PHIEU_ID;
          this.hdkh_id = this.listItemDSTC.HDKH_ID;
          let vhdtb_id = this.listItemDSTC.HDTB_ID;
          let vthuebao_id = this.listItemDSTC.THUEBAO_ID;
          let vdichvuvt_id = this.listItemDSTC.DICHVUVT_ID;
          let vkhlon_id = this.listItemDSTC.KHLON_ID;
          let vtrangthai_id = this.listItemDSTC.TRANGTHAI_HD;

          this.txtMaTB = this.listItemDSTC.MA_TB;
          this.txtKieuLD = this.listItemDSTC.TEN_KIEULD;
          this.txtTenTB = this.listItemDSTC.TEN_TB;
          this.txtDiaChiLD = this.listItemDSTC.DIACHI_LD;
          this.txtGhiChu = this.listItemDSTC.GHICHU_TB;
          this.txtSoAo = this.listItemDSTC.SO_AO;
          this.gridBaoTon = [];
          this.naplichsuhen(1, vhdtb_id, 0);
          this.loadDSTienTrinhTC(vhdtb_id);
          this.loadDSThongTinXL(1, vphieu_id);
          this.btnNhapMoi = true;
          this.loaikhlon_id = this.listItemDSTC.KHLON_ID.toString();
          await API.fn_tt_loai_khl(this.axios, { param: this.listItemDSTC.KHLON_ID, type: 1 })
            .then((response) => {
              if (
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000" &&
                response.data.data != -1
              ) {
                this.txtKHLon = response.data.data;
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {});

          await API.lay_ds_tientrinh_baoton(this.axios, vphieu_id)
            .then((response) => {
              if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined &&
                response.data.data.length > 0
              ) {
                let items = [];
                response.data.data.forEach(function (item) {
                  items.push({
                    NGAY_BT: item.ngay_bt,
                    LYDOTON_TC: item.lydoton_tc,
                    NGUOI_BT: item.nguoi_bt,
                    GHICHU_TON: item.ghichu_ton,
                  });
                });
                this.gridBaoTon = items;
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {});
          if (
            vdichvuvt_id == DichVuVienThong.ADSL ||
            vdichvuvt_id == DichVuVienThong.MEGA_EYES
          ) {
            await API.lay_ds_hdtb_adsl_theo_hdtb_id(this.axios, {
              in_hdtb_id: vhdtb_id,
            })
              .then((response) => {
                if (
                  response &&
                  response.data &&
                  response.data.data &&
                  response.data.error_code &&
                  response.data.error_code === "BSS-00000000" &&
                  response.data.data.length > 0
                ) {
                  this.txtSoAo = response.data.data[0]["ma_lt"];
                } else this.txtSoAo = "";
              })
              .catch((error) => {
                console.log(error);
              })
              .finally(() => {});
          } else if (vdichvuvt_id == DichVuVienThong.MEGAWAN) {
            await API.lay_ds_hdtb_mgwan_theo_hdtb_id(this.axios, {
              in_hdtb_id: vhdtb_id,
            })
              .then((response) => {
                if (
                  response &&
                  response.data &&
                  response.data.data &&
                  response.data.error_code &&
                  response.data.error_code === "BSS-00000000" &&
                  response.data.data.length > 0
                ) {
                  this.txtSoAo = response.data.data[0]["ma_lt"];
                } else this.txtSoAo = "";
              })
              .catch((error) => {
                console.log(error);
              })
              .finally(() => {});
          } else if (vdichvuvt_id == DichVuVienThong.TSL) {
            await API.lay_ds_hdtb_tsl_theo_hdtb_id(this.axios, {
              in_hdtb_id: vhdtb_id,
            })
              .then((response) => {
                if (
                  response &&
                  response.data &&
                  response.data.data &&
                  response.data.error_code &&
                  response.data.error_code === "BSS-00000000" &&
                  response.data.data.length > 0
                ) {
                  this.txtSoAo = response.data.data[0]["ma_lt"];
                } else this.txtSoAo = "";
              })
              .catch((error) => {
                console.log(error);
              })
              .finally(() => {});
          } else this.txtSoAo = "";
        } else {
          this.gridTienTrinh = [];
          this.gridThongTinXuLy.data = [];
          this.txtMaTB = "";
          this.txtKieuLD = "";
          this.txtTenTB = "";
          this.txtDiaChiLD = "";
          this.txtSoAo = "";
          this.txtGhiChu = "";
          this.btnNhapMoi = false;
        }
      }
    },
    gridviewDanhSachTC_FocusedRowChanged(data) {
      this.listItemDSTC = data;
      this.NAP_GRD_TIENTRINH_v2(1);
    },
    gridviewDanhSachBH_FocusedRowChanged(data) {
      this.listItemDSBH = data;
      this.NAP_GRD_TIENTRINH_v2(2);
    },
    onClickSuaPhieu(data) {
      this.listItemThongTinXuLy = data;
      this.iseditTTXL = 1;
      if (data.NHANVIEN_GIAO_ID == this.$root.token.getNhanVienID()) {
        this.$refs.popupnhapttxl.showModal();
      } else {
        this.$toast.error(
          "Bạn không thể thao tác trên tiến trình của người khác"
        );
      }
    },
    onClickXoaPhieu(data) {
      this.listItemThongTinXuLy = data;
      if (data.NHANVIEN_GIAO_ID == this.$root.token.getNhanVienID()) {
        this.$alert(
          "Bạn có thực sự muốn xóa cập nhật xử lý được chọn không ?",
          "Thông báo",
          {
            dangerouslyUseHTMLString: true,
            showCancelButton: true,
            confirmButtonText: "Đồng ý!",
            cancelButtonText: "Hủy bỏ!",
            showCloseButton: true,
            type: "warning",
          }
        ).then(async (v) => {
          if (!v) {
            return;
          }
          this.HT_DLG_XOA_TIENTRINH(
            data.PHIEU_ID,
            data.NHANVIEN_GIAO_ID,
            moment(data.NGAYGIAO).format("DD/MM/YYYY HH:mm:ss"),
            data.NHANVIEN_TH_ID,
            data.GHICHU,
            data.NHIEMVU_ID,
            data.KQXL_ID,
            this.params.p_loainhom
          );
        });
      } else {
        this.$toast.error(
          "Bạn không thể thao tác trên tiến trình của người khác"
        );
      }
    },
    btnNhapMoi_Click() {
      this.iseditTTXL = 0;
      this.$refs.popupnhapttxl.showModal();
    },
    //lấy dữ liệu từ popup
    onNoiDungXL(value) {
      this.loadDSThongTinXL(this.params.p_loainhom, this.listItemDSTC.PHIEU_ID);
    },
    txtMaTB_KeyUp() {
      this.btnTimKiem_Click();
    },
    /* txtMaTB_KeyUp() {
      if (this.txtMaTB.trim() == "") {
        this.$toast.error("Vui lòng nhập Số máy/Acc !");
        return false;
      }
      let ngaygiao = "";
      let denngay = "";
      let items = [];
      if (this.isTimTheoNgay) {
        ngaygiao = moment(this.tungay).format("DD/MM/YYYY");
        denngay = moment(this.denngay).format("DD/MM/YYYY");
      }
      this.loading(true);
      API.lay_ds_phieu_tientrinh_tc(this.axios, {
        ma_gd: this.txtMaTB,
        loaihd_id: this.params.p_loaihd,
        dichvuvt_id: this.params.p_dichvu,
        ngay_giao: ngaygiao,
        ngay_tra: denngay,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            response.data.data.forEach(function (item) {
              items.push({
                MA_TB: item.ma_tb,
                MA_TN: item.matb_tn,
                SO_AO: item.so_ao,
                TEN_TB: item.ten_tb,
                DIACHI_LD: item.diachi_ld,
                DONVI_NHAN: item.donvi_nhan,
                LOAIHINH_TB: item.loaihinh_tb,
                THOIGIAN_CK: item.thoigian_ck,
                CHITIEU_TG: item.chitieu_tg,
                NGAY_GIAO: item.ngaygiao,
                GIO_CONLAI: item.gio_conlai,
                GIOHEN_TU: item.giohen_tu,
                GIOHEN_DEN: item.giohen_den,
                NHANVIEN_HEN: item.nhanvien_hen,
                LYDOTON_TC: item.lydoton_tc,
                GHICHU_TON: item.ghichu_ton,
                GOI_KT: item.goi_kt,
                NV_GIAO: item.nv_giao,
                DONVI_GIAO: item.donvi_giao,
                TRANGTHAI_HD: item.trangthai_hd,
                SO_DT: "",
                NGUOI_CN: item.nguoi_cn,
                NGAY_CN: item.ngay_cn,
                LYDOTRA_ID: item.lydotra_id,
                NGAY_BT: item.ngay_bt,
                NHOMTON: item.nhomton,
                THONGTIN_DH: item.thongtin_dh,
                NGAY_DH: item.ngay_dh,
                TEN_KIEULD: item.ten_kieuld,
                SOPHIEU: item.sophieu,
                PHIEU_ID: item.phieu_id,
              });
            });
            this.dtList = items;
            this.HT_Datagrid();
          } else {
            this.$toast.error(
              "Không tìm thấy thông tin thuê bao " + this.txtMaTB
            );
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    }, */
    HT_DLG_XOA_TIENTRINH(
      phieuid,
      nhanviengiaoid,
      ngaygiao,
      nhanvienthid,
      ghichu,
      nhiemvuid,
      kqxlid,
      loai
    ) {
      this.loading(true);
      let apiParams = {
        vphieu_id: phieuid,
        vnhanvien_giao_id: nhanviengiaoid,
        vngaygiao: ngaygiao,
        vnhanvien_th_id: nhanvienthid,
        vghichu: ghichu,
        vnhiemvu_id: nhiemvuid,
        vkqxl_id: kqxlid,
        vkieu_cn: 2, //2=xoa
        vloai: loai,
      };
      API.xoa_xuly_tientrinh(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.$toast.success("Xóa thành công");
            this.NAP_GRD_TIENTRINH_v2(this.params.p_loainhom);
          } else {
            this.$toast.error(response.data.data);
            this.NAP_GRD_TIENTRINH_v2(this.params.p_loainhom);
          }
        })
        .catch((error) => {
          console.log(error);
          this.$toast.error(error.toString());
        })
        .finally(() => {
          this.loading(false);
        });
    },
    tsbtnXuatExcel_Click() {
      if (this.params.p_loainhom == 1) {
        if (this.gridDanhSachTC.length > 0) {
          let excelExportProperties = {
            fileName: "DanhSachPhieuThiCong.xlsx",
            dataSource: this.gridDanhSachTC,
          };
          this.$refs.gridtc.excelExport(excelExportProperties);
        } else {
          this.$toast.error("Không có dữ liệu để xuất Excel!");
        }
      } else {
        if (this.gridDanhSachBH.length > 0) {
          let excelExportProperties = {
            fileName: "DanhSachPhieuBaoHong.xlsx",
            dataSource: this.gridDanhSachBH,
          };
          this.$refs.gridbh.excelExport(excelExportProperties);
        } else {
          this.$toast.error("Không có dữ liệu để xuất Excel!");
        }
      }
    },
    tsbtnHenKH_Click() {
      if (this.params.p_loainhom == 1) {
        if (this.gridDanhSachTC.length > 0) {
          this.$refs.popupTTHenKHTC.handleShowModal()
         this.$refs.popupThongTinHenKhachHangTC.show();
        } else {
          this.$toast.error("Bạn chưa chọn phiếu yêu cầu!");
        }
      } else {
        if (this.gridDanhSachBH.length > 0) {
        this.$refs.popupThongTinHenKhachHangBH.showModal();
        } else {
          this.$toast.error("Bạn chưa chọn phiếu yêu cầu!");
        }
      }
    },
    overlayClick: function() {
        this.$refs.popupThongTinHenKhachHangTC.hide();
    },
    tvDONVI_AfterSelect(node) {
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      this.donviID = treeObj.selectedNodes[0];
      this.getDonVi(treeObj.selectedNodes[0]);
      this.selectedNodes = node.nodeData.DONVI_ID;
    },
  },
};
</script>
