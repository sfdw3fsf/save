<template src="./AssignManagement.html"></template>
<style scoped src="./AssignManagement.scss"></style>

<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./api";
import gridview from "@/components/Shared/gridview";
import DataGridNV from "./components/DataGrid";
// CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
// JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";

export default {
  components: { breadcrumb, VueFlatPickr, gridview, API, DataGridNV },
  name: "AssignManagement",
  props: ["gan_hd", "vtimkiem_hdkh_id"],

  watch: {},

  created() {},
  mounted() {
    this.Load_start(); // Load khi khởi tạo
    this.init(); // Load các API khởi tạo.
    this.Load_form();
    this.$nextTick(() => this.$refs.focus.focus());

    //Xử lý popup
    if (this.gan_hd) {
      this.kieugoi_id = 1;
      this.txtMaGD = vtimkiem_hdkh_id;
      this.TimThongTinKH();
    }
  },

  data() {
    return {
      // Khai báo thuộc tính:
      Tab1: true,
      Tab2: false,
      Tab3: false,
      Tab4: false,
      Tab5: false,
      TabGIANTIEP: false,
      selectedOption: 1,
      hideTab: false,
      check_toanbo: false,
      check_all: false,
      Tag: "",
      txtMaGD: "",
      label_trangthai: "",
      check_load: 0,
      dvi_tt: -1,
      flag_gan: true,
      sotb_tam: "",
      kieugoi_id: 0,
      loainv_id: 0,
      donvi_id: 0,
      hdkhId: 0,
      thuebaoID: 0,
      khachhangID: 0,
      hdtbID: 0,
      donviql_id: 0,
      data_cskh: [],
      dt_Nhanvien_All: [],
      dt_HopDong_All: [],
      options: {
        cbx_dsloainv: [],
        cbx_dsdonvi: [],
      },
      ds_danhba_timkiem: {
        header: [
          {
            headerText: "Số máy ACC",
            fieldName: "ma_tb",
            allowSorting: false,
            width: "30",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Dịch vụ",
            fieldName: "ten_dvvt",
            allowSorting: false,
            width: "30",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Loại hình",
            fieldName: "loaihinh_tb",
            allowSorting: false,
            width: "30",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên thuê bao",
            fieldName: "ten_tb",
            allowSorting: false,
            width: "40",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Trạng thái TB",
            fieldName: "trangthai_tb",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Địa chỉ TB",
            fieldName: "diachi_tb",
            allowSorting: false,
            width: "100",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
      },

      ds_hopdong_thuebao_timkiem: {
        header: [
          {
            headerText: "Mã GD",
            fieldName: "maGd",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên loại HĐ",
            fieldName: "tenLoaiHd",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Ngày yêu cầu",
            fieldName: "ngayYc",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Trạng thái",
            fieldName: "trangThaiHd",
            allowSorting: false,
            textAlign: "center",
            width: "40",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Đơn vị lập HĐ/PL",
            fieldName: "tenDv",
            allowSorting: false,
            textAlign: "center",
            width: "60",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
      },

      ds_nhavien_chuagan: {
        header: [
          {
            headerText: "Mã NV",
            fieldName: "ma_nv",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tên NV",
            fieldName: "ten_nv",
            allowSorting: false,
            width: "70",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tỷ lệ",
            fieldName: "tyle",
            allowSorting: false,
            width: "40",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: true,
            type: "Number",
          },
          {
            headerText: "Tên đơn vị",
            fieldName: "ten_dv",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
        ],
        list: [],
        checked: [],
        select_chuagan: [],
        display_list: [],
      },
      ds_thuebao_main: {
        header: [
          {
            headerText: "Số máy/ACC",
            fieldName: "ma_tb",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Mã TN",
            fieldName: "matb_tn",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Dịch vụ",
            fieldName: "ten_dvvt",
            allowSorting: false,
            width: "70",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Loại hình",
            fieldName: "loaihinh_tb",
            allowSorting: false,
            width: "70",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên thuê bao",
            fieldName: "ten_tb",
            allowSorting: false,
            width: "70",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Kiểu lắp đặt",
            fieldName: "ten_kieuld",
            allowSorting: false,
            width: "70",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Địa chỉ lắp đặt",
            fieldName: "diachi_ld",
            allowSorting: false,
            width: "120",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Trạng thái",
            fieldName: "trangthai_hd",
            allowSorting: false,
            width: "70",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
        active: false,
      },

      ds_nhanvien_ptm: {
        header: [
          {
            headerText: "Mã NV",
            fieldName: "ma_nv",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tên NV",
            fieldName: "ten_nv",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tỷ lệ",
            fieldName: "tyle",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: true,
            type: "Number",
          },
          {
            headerText: "Đơn vị",
            fieldName: "ten_dv",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
        ],
        list: [],
        select_danggo: [],
        active: false,
      },

      ds_nhanvien_hotro: {
        header: [
          {
            headerText: "Mã NV",
            fieldName: "ma_nv",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên NV",
            fieldName: "ten_nv",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tỷ lệ",
            fieldName: "tyle",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: true,
            type: "Number",
          },
          {
            headerText: "Đơn vị",
            fieldName: "ten_dv",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
        select_danggo: [],
        active: false,
      },

      ds_nhanvien_hotro_kythuat: {
        header: [
          {
            headerText: "Mã NV",
            fieldName: "ma_nv",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên NV",
            fieldName: "ten_nv",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tỷ lệ",
            fieldName: "tyle",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: true,
            type: "Number",
          },
          {
            headerText: "Đơn vị",
            fieldName: "ten_dv",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
        select_danggo: [],
        active: false,
      },

      ds_nhanvien_duytri: {
        header: [
          {
            headerText: "Mã NV",
            fieldName: "ma_nv",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên NV",
            fieldName: "ten_nv",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tỷ lệ",
            fieldName: "tyle",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: true,
            type: "Number",
          },
          {
            headerText: "Đơn vị",
            fieldName: "ten_dv",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        select_danggo: [],
        list: [],
      },

      ds_nhanvien_cskh: {
        header: [
          {
            headerText: "Mã NV",
            fieldName: "ma_nv",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tên NV",
            fieldName: "ten_nv",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tỷ lệ",
            fieldName: "tyle",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: true,
            type: "Number",
          },
          {
            headerText: "Đơn vị",
            fieldName: "ten_dv",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
        ],
        list: [],
        select_danggo: [],
        active: false,
      },

      ds_nhanvien_giantiep: {
        header: [
          {
            headerText: "Mã NV",
            fieldName: "ma_nv",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tên NV",
            fieldName: "ten_nv",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
          {
            headerText: "Tỷ lệ",
            fieldName: "tyle",
            allowSorting: false,
            width: "50",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: true,
            type: "Number",
          },
          {
            headerText: "Đơn vị",
            fieldName: "ten_dv",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
            allowEditing: false,
          },
        ],
        list: [],
        select_danggo: [],
        active: false,
      },
    };
  },

  methods: {
    Load_start() {
      if (!this.gan_hd) {
        if (
          this.$route.query.tag != null &&
          this.$route.params.tag != "" &&
          this.$route.query.tag.length > 0
        ) {
          this.Tag = this.$route.query.tag;

          if (this.Tag == "1" || this.Tag == "2") {
            this.kieugoi_id = parseInt(this.Tag);
          } else if (this.Tag != "") {
            this.$toast.warning(
              "Tag này chưa được phân quyền! Liên hệ admin để xử lý."
            );
          }
        } else {
          this.$toast.warning(
            "Không tìm thấy thông tin tag! Liên hệ admin để xử lý."
          );
        }
      } else {
        this.kieugoi_id = 1;
        this.txtMaGD = vtimkiem_hdkh_id;
      }
    },

    Load_form() {
      this.SelectTab1();
      if (this.kieugoi_id == 1) {
        this.label_trangthai = "GÁN NHÂN VIÊN QUẢN LÝ THUÊ BAO HỢP ĐỒNG";
        this.check_toanbo = false;
      } else if (this.kieugoi_id == 2) {
        this.label_trangthai = "GÁN NHÂN VIÊN QUẢN LÝ THUÊ BAO DANH BẠ";
        this.check_toanbo = true;
      } else this.check_toanbo = false;
      this.clickable();
    },

    onSelectedRowChange_PTM(items) {
      this.ds_nhanvien_ptm.select_danggo = [];
      if (this.kieugoi_id == 1) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_ptm.list[index];
            if (
              item &&
              !this.ds_nhanvien_ptm.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "1"
              )
            ) {
              this.ds_nhanvien_ptm.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_ptm.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_ptm.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.hdtb_id == this.hdtbId &&
              nv.loai == "1"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_ptm.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_ptm.select_danggo.push(item);
          }
        }
      }
      if (this.kieugoi_id == 2) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_ptm.list[index];
            if (
              item &&
              !this.ds_nhanvien_ptm.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "1"
              )
            ) {
              this.ds_nhanvien_ptm.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_ptm.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_ptm.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.thuebao_id == this.thuebaoID &&
              nv.loai == "1"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_ptm.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_ptm.select_danggo.push(item);
          }
        }
      }
    },
    onSelectedRowChange_HOTRO(items) {
      this.ds_nhanvien_hotro.select_danggo = [];
      if (this.kieugoi_id == 1) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_hotro.list[index];
            if (
              item &&
              !this.ds_nhanvien_hotro.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "3"
              )
            ) {
              this.ds_nhanvien_hotro.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_hotro.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_hotro.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.hdtb_id == this.hdtbId &&
              nv.loai == "3"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_hotro.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_hotro.select_danggo.push(item);
          }
        }
      }
      if (this.kieugoi_id == 2) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_hotro.list[index];
            if (
              item &&
              !this.ds_nhanvien_hotro.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "3"
              )
            ) {
              this.ds_nhanvien_hotro.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_hotro.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_hotro.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.thuebao_id == this.thuebaoID &&
              nv.loai == "3"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_hotro.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_hotro.select_danggo.push(item);
          }
        }
      }
    },

    onSelectedRowChange_DUYTRI(items) {
      this.ds_nhanvien_duytri.select_danggo = [];
      if (this.kieugoi_id == 1) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_duytri.list[index];
            if (
              item &&
              !this.ds_nhanvien_duytri.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "2"
              )
            ) {
              this.ds_nhanvien_duytri.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_duytri.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_duytri.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.hdtb_id == this.hdtbId &&
              nv.loai == "2"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_duytri.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_duytri.select_danggo.push(item);
          }
        }
      }
      if (this.kieugoi_id == 2) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_duytri.list[index];
            if (
              item &&
              !this.ds_nhanvien_duytri.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "2"
              )
            ) {
              this.ds_nhanvien_duytri.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_duytri.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_duytri.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.thuebao_id == this.thuebaoID &&
              nv.loai == "2"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_duytri.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_duytri.select_danggo.push(item);
          }
        }
      }
    },

    // New
    onSelectedRowChange_CSKH(items) {
      this.ds_nhanvien_cskh.select_danggo = [];
      if (this.kieugoi_id == 1) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_cskh.list[index];
            if (
              item &&
              !this.ds_nhanvien_cskh.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
              )
            ) {
              this.ds_nhanvien_cskh.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_cskh.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_cskh.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.hdtb_id == this.hdtbId &&
              nv.loai == "5"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_cskh.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_cskh.select_danggo.push(item);
          }
        }
      }
      if (this.kieugoi_id == 2) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_cskh.list[index];
            if (
              item &&
              !this.ds_nhanvien_cskh.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
              )
            ) {
              this.ds_nhanvien_cskh.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_cskh.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_cskh.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.thuebao_id == this.thuebaoID &&
              nv.loai == "5"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_cskh.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_cskh.select_danggo.push(item);
          }
        }
      }
    },

    // New
    onSelectedRowChange_HOTRO_KYTHUAT(items) {
      this.ds_nhanvien_hotro_kythuat.select_danggo = [];
      if (this.kieugoi_id == 1) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_hotro_kythuat.list[index];
            if (
              item &&
              !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
              )
            ) {
              this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_hotro_kythuat.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_hotro_kythuat.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.hdtb_id == this.hdtbId &&
              nv.loai == "5"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
          }
        }
      }
      if (this.kieugoi_id == 2) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_hotro_kythuat.list[index];
            if (
              item &&
              !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
              )
            ) {
              this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_hotro_kythuat.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_hotro_kythuat.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.thuebao_id == this.thuebaoID &&
              nv.loai == "5"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
          }
        }
      }
    },

    onSelectedRowChange_GIANTIEP(items) {
      this.ds_nhanvien_giantiep.select_danggo = [];
      if (this.kieugoi_id == 1) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_giantiep.list[index];
            if (
              item &&
              !this.ds_nhanvien_giantiep.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "6"
              )
            ) {
              this.ds_nhanvien_giantiep.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_giantiep.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_giantiep.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.hdtb_id == this.hdtbId &&
              nv.loai == "6"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_giantiep.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_giantiep.select_danggo.push(item);
          }
        }
      }
      if (this.kieugoi_id == 2) {
        if (items.rowIndexes) {
          items.rowIndexes.forEach((index) => {
            var item = this.ds_nhanvien_giantiep.list[index];
            if (
              item &&
              !this.ds_nhanvien_giantiep.select_danggo.some(
                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "6"
              )
            ) {
              this.ds_nhanvien_giantiep.select_danggo.push(item);
            }
          });
        } else if (items.rowIndex >= 0) {
          this.ds_nhanvien_giantiep.list[items.rowIndex] = items.data;
          var item = this.ds_nhanvien_giantiep.list[items.rowIndex];
          this.dt_HopDong_All.forEach((nv) => {
            if (
              nv.ma_nv == item.ma_nv &&
              nv.thuebao_id == this.thuebaoID &&
              nv.loai == "6"
            ) {
              nv.tyle = item.tyle;
            }
          });
          if (
            !this.ds_nhanvien_giantiep.select_danggo.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhanvien_giantiep.select_danggo.push(item);
          }
        }
      }
    },

    onDeselectedRowChange_PTM(items) {
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhanvien_ptm.list[index];
          for (var i = 0; i < this.ds_nhanvien_ptm.list.length; i++) {
            if (this.ds_nhanvien_ptm.select_danggo[i].ma_nv == item.ma_nv) {
              this.ds_nhanvien_ptm.select_danggo.splice(i, 1);
              break;
            }
          }
        });
      }
    },

    onDeselectedRowChange_HOTRO(items) {
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhanvien_hotro.list[index];
          for (var i = 0; i < this.ds_nhanvien_hotro.list.length; i++) {
            if (this.ds_nhanvien_hotro.select_danggo[i].ma_nv == item.ma_nv) {
              this.ds_nhanvien_hotro.select_danggo.splice(i, 1);
              break;
            }
          }
        });
      }
    },

    onDeselectedRowChange_DUYTRI(items) {
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhanvien_duytri.list[index];
          for (var i = 0; i < this.ds_nhanvien_duytri.list.length; i++) {
            if (this.ds_nhanvien_duytri.select_danggo[i].ma_nv == item.ma_nv) {
              this.ds_nhanvien_duytri.select_danggo.splice(i, 1);
              break;
            }
          }
        });
      }
    },

    onDeselectedRowChange_GIANTIEP(items) {
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhanvien_giantiep.list[index];
          for (var i = 0; i < this.ds_nhanvien_giantiep.list.length; i++) {
            if (
              this.ds_nhanvien_giantiep.select_danggo[i].ma_nv == item.ma_nv
            ) {
              this.ds_nhanvien_giantiep.select_danggo.splice(i, 1);
              break;
            }
          }
        });
      }
    },

    onDeselectedRowChange_CSKH(items) {
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhanvien_cskh.list[index];
          for (var i = 0; i < this.ds_nhanvien_cskh.list.length; i++) {
            if (this.ds_nhanvien_cskh.select_danggo[i].ma_nv == item.ma_nv) {
              this.ds_nhanvien_cskh.select_danggo.splice(i, 1);
              break;
            }
          }
        });
      }
    },

    // NEW 
    onDeselectedRowChange_HOTRO_KYTHUAT(items) {
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhanvien_hotro_kythuat.list[index];
          for (var i = 0; i < this.ds_nhanvien_hotro_kythuat.list.length; i++) {
            if (this.ds_nhanvien_hotro_kythuat.select_danggo[i].ma_nv == item.ma_nv) {
              this.ds_nhanvien_hotro_kythuat.select_danggo.splice(i, 1);
              break;
            }
          }
        });
      }
    },

    onSelectedRowChange_NVCG(items) {
      this.ds_nhavien_chuagan.select_chuagan = [];
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhavien_chuagan.display_list[index];
          if (
            !this.ds_nhavien_chuagan.select_chuagan.some(
              (nv) => nv.ma_nv == item.ma_nv
            )
          ) {
            this.ds_nhavien_chuagan.select_chuagan.push(item);
          }
        });
      } else if (items.rowIndex >= 0) {
        this.ds_nhavien_chuagan.display_list[items.rowIndex] = items.data;
        var item = this.ds_nhavien_chuagan.display_list[items.rowIndex];
        if (
          !this.ds_nhavien_chuagan.select_chuagan.some(
            (nv) => nv.ma_nv == item.ma_nv
          )
        ) {
          this.ds_nhavien_chuagan.select_chuagan.push(item);
        }
      }
      // Xử lý khi chọn row trên lưới nhân viên chưa gán
    },

    onDeselectedRowChange_NVCG(items) {
      if (items.rowIndexes) {
        items.rowIndexes.forEach((index) => {
          var item = this.ds_nhavien_chuagan.display_list[index];
          for (var i = 0; i < this.ds_nhavien_chuagan.list.length; i++) {
            if (this.ds_nhavien_chuagan.select_chuagan[i].ma_nv == item.ma_nv) {
              this.ds_nhavien_chuagan.select_chuagan.splice(i, 1);
              break;
            }
          }
        });
      }
    },
    async ThongBao(msg, title) {
      if (!title) title = "Lỗi dữ liệu";
      await this.$bvModal.msgBoxOk(msg, {
        title: title,
        size: "sm",
        okTitle: "Đồng ý",
      });
    },
    SelectTab1() {
      this.Tab1 = true;
      this.Tab2 = false;
      this.Tab3 = false;
      this.Tab4 = false;
      this.Tab5 = false;
      this.TabGIANTIEP = false;
      this.ds_nhanvien_ptm.active = true;
      this.ds_nhanvien_hotro.active = false;
      this.ds_nhanvien_hotro_kythuat.active = false;
      this.ds_nhanvien_duytri.active = false;
      this.ds_nhanvien_cskh.active = false;
      this.ds_nhanvien_giantiep.active = false;
      this.ds_nhavien_chuagan.list = [];
      this.init();
    },

    SelectTab2() {
      this.Tab1 = false;
      this.Tab2 = true;
      this.Tab4 = false;
      this.Tab3 = false;
      this.Tab5 = false;
      this.TabGIANTIEP = false;
      this.ds_nhanvien_ptm.active = false;
      this.ds_nhanvien_hotro.active = false;
      this.ds_nhanvien_hotro_kythuat.active = false;
      this.ds_nhanvien_duytri.active = true;
      this.ds_nhanvien_cskh.active = false;
      this.ds_nhanvien_giantiep.active = false;
      this.ds_nhavien_chuagan.list = [];
      this.init();
    },

    SelectTab3() {
      this.Tab1 = false;
      this.Tab2 = false;
      this.Tab3 = true;
      this.Tab4 = false;
      this.Tab5 = false;
      this.TabGIANTIEP = false;
      this.ds_nhanvien_ptm.active = false;
      this.ds_nhanvien_hotro.active = true;
      this.ds_nhanvien_hotro_kythuat.active = false;
      this.ds_nhanvien_duytri.active = false;
      this.ds_nhanvien_cskh.active = false;
      this.ds_nhanvien_giantiep.active = false;
      this.ds_nhavien_chuagan.list = [];
      this.init();
    },

    async SelectTab4() {
      this.Tab1 = false;
      this.Tab2 = false;
      this.Tab3 = false;
      this.Tab4 = true;
      this.Tab5 = false;
      this.TabGIANTIEP = false;
      this.ds_nhanvien_ptm.active = false;
      this.ds_nhanvien_hotro.active = false;
      this.ds_nhanvien_hotro_kythuat.active = false;
      this.ds_nhanvien_duytri.active = false;
      this.ds_nhanvien_cskh.active = true;
      this.ds_nhanvien_giantiep.active = false;
      this.ds_nhavien_chuagan.list = [];

      if (this.kieugoi_id != 0) {
        var response = await API.ADMIN_LOAI_NV(this.axios);

        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          var data_lnv = response.data.data;
          this.options.cbx_dsloainv = data_lnv;
          var data_default;
          if (response.data.data.length) {
            data_default = data_lnv[0];
          }
          if (data_default && data_default.LOAINV_ID > 0) {
            this.loainv_id = data_default.LOAINV_ID;
            this.NAP_DS_DVI_LINE(-2, this.loainv_id);
          }
        } else {
          this.$toast.warning(
            "Có lỗi khi quá trình nạp dữ liệu từ DB. Liên hệ Admin để xử lý!"
          );
        }
      }
    },

    async SelectTab5() {
      this.Tab1 = false;
      this.Tab2 = false;
      this.Tab3 = false;
      this.Tab4 = false;
      this.Tab5 = true;
      this.TabGIANTIEP = false;
      this.ds_nhanvien_ptm.active = false;
      this.ds_nhanvien_hotro.active = false;
      this.ds_nhanvien_hotro_kythuat.active = true;
      this.ds_nhanvien_duytri.active = false;
      this.ds_nhanvien_cskh.active = false;
      this.ds_nhanvien_giantiep.active = false;
      this.ds_nhavien_chuagan.list = [];
      if(this.kieugoi_id != 0){

      }
    },

    async SelectTabGIANTIEP() {
      this.Tab1 = false;
      this.Tab2 = false;
      this.Tab3 = false;
      this.Tab4 = false;
      this.TabGIANTIEP = true;
      this.ds_nhanvien_ptm.active = false;
      this.ds_nhanvien_hotro.active = false;
      this.ds_nhanvien_hotro_kythuat.active = false;
      this.ds_nhanvien_duytri.active = false;
      this.ds_nhanvien_cskh.active = false;
      this.ds_nhanvien_giantiep.active = true;
      this.options.cbx_dsloainv = [];
      this.options.cbx_dsdonvi = [];
      this.ds_nhavien_chuagan.list = [];
      if (this.kieugoi_id != 0) {
        var response = await API.ADMIN_LOAI_NV(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          var data_lnv = response.data.data.filter(
            (item) => item.LOAINV_ID === 584 || item.LOAINV_ID === 587
          );
          this.options.cbx_dsloainv = data_lnv;
          if (
            response.data.data.filter((item) => item.LOAINV_ID === 584)
              .length === 0
          ) {
            data_lnv.push({ LOAINV_ID: 584, TEN: "Đại lý" });
          }
          if (
            response.data.data.filter((item) => item.LOAINV_ID === 584)
              .length === 0
          ) {
            data_lnv.push({
              LOAINV_ID: 587,
              TEN: "NV/Phòng BH quản lý Đại lý",
            });
          }
          this.options.cbx_dsloainv = data_lnv;

          var data_default;
          if (data_lnv.some((item) => item.LOAINV_ID === 584)) {
            data_default = data_lnv.find((item) => item.LOAINV_ID === 584);
          } else {
            data_default = data_lnv[0];
          }
          if (data_default && data_default.LOAINV_ID > 0) {
            this.loainv_id = data_default.LOAINV_ID;
            this.NAP_DS_DVI_LINE(-1, this.loainv_id);
          }
        } else {
          this.$toast.warning(
            "Có lỗi khi quá trình nạp dữ liệu từ DB. Liên hệ Admin để xử lý!"
          );
        }
      }
    },

    // Hiển thị thông tin khi tìm kiếm ra nhiều HĐ
    Hienthi_hdkh_popup(data) {
      var $ = document.querySelector.bind(document);
      $("#popupTCTTTB").style.display = "block";
      this.ds_hopdong_thuebao_timkiem.list = data;
    },

    // Close form tìm kiếm HD
    close_timkiem_hdtb() {
      var $ = document.querySelector.bind(document);
      $("#popupTCTTTB").style.display = "none";
    },

    clickable() {
      if (this.kieugoi_id == 1 || this.kieugoi_id == 2) {
        return false;
      } else return true;
    },

    TimThongTinKH() {
      if (this.txtMaGD == null || this.txtMaGD == "") {
        this.check_load = 0;
      } else {
        this.CLEAR();
        this.ds_hopdong_thuebao_timkiem.list = [];
        this.sotb_tam = "";
        if (this.kieugoi_id == 1) {
          // Load Hợp đồng
          this.loading(true);
          var api_hd = API.lay_ds_hopdong_theo_mathuebao(
            this.axios,
            this.txtMaGD
          ).then((response) => {
            if (
              response &&
              response.data &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data.length > 0
            ) {
              this.sotb_tam = this.txtMaGD;
              // Nếu nhiều HĐ thì cho hiển thị lưới tìm kiếm
              if (response.data.data.length > 1) {
                this.Hienthi_hdkh_popup(response.data.data);
              }
              // Nếu 1 HĐ
              else if (response.data.data.length === 1) {
                this.txtMaGD = response.data.data[0].maGd;
                this.hdkhId = response.data.data[0].hdkhId;
                this.khachHangId = response.data.data[0].khachHangId;
                this.LoadDS_HopDongTB_NhanVien(this.txtMaGD, this.hdkhId);
              }
              this.loading(false);
            } else if (response.data.error_code === "BSS-00000500") {
              this.check_load = 0;
              this.loading(false);
              this.$toast.error(
                "Request timed out after 30000ms. Liên hệ admin để xử lý!"
              );
            } else {
              this.check_load = 0;
              this.loading(false);
              this.$toast.warning("Không tìm thấy thông tin hợp đồng!");
            }
          });
        } else {
          // Load Danh bạ
          this.loading(true);
          var api_db = API.timkiem_ds_hopdong(this.axios, 2, this.txtMaGD).then(
            (response) => {
              if (
                response &&
                response.data &&
                response.data.error_code === "BSS-00000000" &&
                response.data.data.dt_hd_tb.length > 0
              ) {
                this.sotb_tam = this.txtMaGD;
                this.ds_danhba_timkiem.list = response.data.data.dt_hd_tb;
                this.flag_gan = false;
                this.check_load = 1;
                this.loading(false);
                if (this.ds_danhba_timkiem.list.length >= 1) {
                  if (
                    response.data.data.dt_hd &&
                    response.data.data.dt_hd.length > 0
                  ) {
                    this.dt_HopDong_All = response.data.data.dt_hd;
                  }
                  var index = 0;
                  for (var i = 0; i < this.ds_danhba_timkiem.list.length; i++) {
                    if (
                      this.ds_danhba_timkiem.list[i].ma_tb === this.sotb_tam
                    ) {
                      index = i;
                      break;
                    }
                  }
                  this.$refs.dbGridRef.setCurrentSelectedRow(index);
                }
              } else {
                this.loading(false);
                this.check_load = 0;
                this.$toast.warning("Không tìm thấy thông tin danh bạ!");
              }
            }
          );
        }
      }
    },

    // Lấy data khi chọn row trên lưới
    Select_ds_hopdong_thuebao_timkiem: function (items) {
      this.CLEAR();
      this.txtMaGD = items.maGd;
      this.hdkhId = items.hdkhId;
      this.khachHangId = items.khachHangId;
      this.LoadDS_HopDongTB_NhanVien(items.maGd, items.hdkhId);
    },

    Select_thuebao_danhba: function (items) {
      this.donviql_id = items.donvi_qlid;
      if (
        this.donviql_id !== 0 &&
        this.loainv_id !== 0 &&
        this.donviql_id !== null
      ) {
        if (this.Tab4 === true) {
          this.NAP_DS_DVI_LINE(-2, this.loainv_id);
        } else {
          this.NAP_DS_DVI_LINE(this.donviql_id, this.loainv_id);
        }
        this.thuebaoID = items.thuebao_id;
        this.khachHangId = items.khachhang_id;
        this.ds_nhanvien_ptm.list = [];
        this.ds_nhanvien_hotro.list = [];
        this.ds_nhanvien_duytri.list = [];
        this.ds_nhanvien_cskh.list = [];
        this.ds_nhanvien_giantiep.list = [];

        this.dt_HopDong_All.forEach((element) => {
          if (
            element.loai == "1" &&
            element.thuebao_id == this.thuebaoID &&
            parseInt(element.ischeck) >= 0 &&
            !this.ds_nhanvien_ptm.list.some(
              (item) =>
                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
            )
          ) {
            this.ds_nhanvien_ptm.list.push(element);
          } else if (
            element.loai == "3" &&
            element.thuebao_id == this.thuebaoID &&
            parseInt(element.ischeck) >= 0 &&
            !this.ds_nhanvien_hotro.list.some(
              (item) =>
                item.ma_nv == element.ma_nv && item.hdtb_id == this.thuebaoID
            )
          ) {
            this.ds_nhanvien_hotro.list.push(element);
          } else if (
            element.loai == "2" &&
            element.thuebao_id == this.thuebaoID &&
            parseInt(element.ischeck) >= 0 &&
            !this.ds_nhanvien_duytri.list.some(
              (item) =>
                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
            )
          ) {
            this.ds_nhanvien_duytri.list.push(element);
          } else if (
            element.loai == "6" &&
            element.thuebao_id == this.thuebaoID &&
            parseInt(element.ischeck) >= 0 &&
            !this.ds_nhanvien_giantiep.list.some(
              (item) =>
                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
            )
          ) {
            this.ds_nhanvien_giantiep.list.push(element);
          } else if (
            element.loai == "5" &&
            element.thuebao_id == this.thuebaoID &&
            parseInt(element.ischeck) >= 0 &&
            !this.ds_nhanvien_cskh.list.some(
              (item) =>
                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
            )
          ) {
            this.ds_nhanvien_cskh.list.push(element);
          }
        });
      } else {
        this.ds_nhavien_chuagan.list = [];
        this.options.cbx_dsdonvi = [];
      }
    },

    Select_thuebao_main: function (items) {
      this.donviql_id = items.donvi_qlid;
      if (
        this.donviql_id !== 0 &&
        this.loainv_id !== 0 &&
        this.donviql_id !== null
      ) {
        this.NAP_DS_DVI_LINE(this.donviql_id, this.loainv_id);
      } else {
        this.NAP_DS_DVI_LINE(-1, this.loainv_id);
      }

      this.hdtbId = items.hdtb_id;
      this.ds_nhanvien_ptm.list = [];
      this.ds_nhanvien_hotro.list = [];
      this.ds_nhanvien_duytri.list = [];
      this.ds_nhanvien_cskh.list = [];
      this.ds_nhanvien_giantiep.list = [];

      this.dt_HopDong_All.forEach((element) => {
        if (
          element.loai == "1" &&
          element.hdtb_id == this.hdtbId &&
          parseInt(element.ischeck) >= 0 &&
          !this.ds_nhanvien_ptm.list.some(
            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
          )
        ) {
          this.ds_nhanvien_ptm.list.push(element);
        } else if (
          element.loai == "3" &&
          element.hdtb_id == this.hdtbId &&
          parseInt(element.ischeck) >= 0 &&
          !this.ds_nhanvien_hotro.list.some(
            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
          )
        ) {
          this.ds_nhanvien_hotro.list.push(element);
        } else if (
          element.loai == "2" &&
          element.hdtb_id == this.hdtbId &&
          parseInt(element.ischeck) >= 0 &&
          !this.ds_nhanvien_duytri.list.some(
            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
          )
        ) {
          this.ds_nhanvien_duytri.list.push(element);
        } else if (
          element.loai == "6" &&
          element.hdtb_id == this.hdtbId &&
          parseInt(element.ischeck) >= 0 &&
          !this.ds_nhanvien_giantiep.list.some(
            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
          )
        ) {
          this.ds_nhanvien_giantiep.list.push(element);
        } else if (
          element.loai == "5" &&
          element.hdtb_id == this.hdtbId &&
          parseInt(element.ischeck) >= 0 &&
          !this.ds_nhanvien_cskh.list.some(
            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
          )
        ) {
          this.ds_nhanvien_cskh.list.push(element);
        }
      });
    },

    select_LoaiNV() {
      this.loading(true);
      if (this.check_load == 0) {
        if (this.Tab4 === true) {
          this.NAP_DS_DVI_LINE("-2", this.loainv_id);
        } else {
          this.NAP_DS_DVI_LINE(-1, this.loainv_id);
        }
      } else {
        if (this.Tab4 === true) {
          this.NAP_DS_DVI_LINE("-2", this.loainv_id);
        } else {
          this.NAP_DS_DVI_LINE(this.donviql_id, this.loainv_id);
        }
      }
      this.loading(false);
    },

    select_Donvi() {
      if (this.loainv_id == 449) {
        this.ds_nhavien_chuagan.list = this.dt_Nhanvien_All.filter(
          (item) => this.donvi_id == 0 || item.donvi_id == this.donvi_id
        );
      } else {
        if (this.Tab4 === true) {
          this.NAP_DS_NHANVIEN_CHUAGAN_V2(this.loainv_id, this.donvi_id);
        } else {
          var api6 = API.DS_NHANVIEN_CHUAGAN(
            this.axios,
            this.donvi_id,
            this.loainv_id
          ).then((response) => {
            if (
              response &&
              response.data &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data.length > 0
            ) {
              this.ds_nhavien_chuagan.list = response.data.data;
            }
          });
        }
      }
    },

    NAP_DS_DVI_LINE(loaidv, loainv) {
      this.CLEAR_COMBO();
      if (loaidv == null) {
        loaidv = -1;
      }
      var api2 = API.DS_LOAI_DV(this.axios, loaidv, loainv).then((response) => {
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          var data_dv = response.data.data;
          this.options.cbx_dsdonvi = data_dv;
          this.donvi_id = data_dv[0].donvi_id;
          if (this.Tab4 === true && this.loainv_id != 449) {
            this.NAP_DS_NHANVIEN_CHUAGAN_V2(this.loainv_id, this.donvi_id);
          } else {
            if (this.loainv_id != 0 && this.donvi_id != 0) {
              this.NAP_DS_NHANVIEN_CHUAGAN(this.donvi_id, this.loainv_id);
            }
          }
        }
      });
    },
    async NAP_DS_NHANVIEN_CHUAGAN_V2(loainv_id, donvi_line_id) {
      this.ds_nhavien_chuagan.list = [];
      var danhsach_gt = await API.sp_lay_nhanvien_chuagan_cskh(this.axios, {
        loainv_id,
        donvi_line_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.ds_nhavien_chuagan.list = response.data.data;
        }
      });
    },
    async NAP_DS_NHANVIEN_CHUAGAN(donvi, nhanvien) {
      // Load danh sách nhân viên chưa gán theo loại nhân viên, đơn vị
      try {
        this.loading(true);
        this.ds_nhavien_chuagan.list = [];
        if (this.loainv_id == 449) {
          var api_1 = await API.lay_ds_nhanvien_chuagan(this.axios).then(
            (response) => {
              if (
                response &&
                response.data &&
                response.data.error_code === "BSS-00000000" &&
                response.data.data.length > 0
              ) {
                this.ds_nhavien_chuagan.list = response.data.data;
                this.dt_Nhanvien_All = structuredClone(
                  this.ds_nhavien_chuagan.list
                );
                this.options.cbx_dsdonvi = [];
                this.options.cbx_dsdonvi.push({
                  donvi_id: 0,
                  ten_dv: "Tất cả",
                });
                response.data.data.forEach((item) => {
                  if (
                    !this.options.cbx_dsdonvi.some(
                      (dv) => dv.donvi_id == item.donvi_id
                    )
                  ) {
                    this.options.cbx_dsdonvi.push({
                      donvi_id: item.donvi_id,
                      ten_dv: item.ten_dv,
                    });
                  }
                });
                var data_default1 = response.data.data.filter(
                  (item) => item.donvi_id == 0
                );
                this.donvi_id = data_default1[0].donvi_id;
              }
            }
          );
        } else {
          this.loading(true);
          var api3 = await API.DS_NHANVIEN_CHUAGAN(
            this.axios,
            donvi,
            nhanvien
          ).then((response) => {
            if (
              response &&
              response.data &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data.length > 0
            ) {
              this.ds_nhavien_chuagan.list = response.data.data;
            }
          });
        }
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },

    LoadDS_HopDongTB_NhanVien(timkiem, hdkh_id) {
      this.check_load = 0;
      var api_loadhd = API.load_ds_hopdong_thuebao(this.axios, hdkh_id).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.error_code === "BSS-00000000"
          ) {
            if (
              response.data.data.ds_dbhd != null &&
              response.data.data.ds_dbhd !== []
            ) {
              this.check_load = 1;
              this.ds_thuebao_main.list = response.data.data.ds_dbhd;
            }
            if (this.ds_thuebao_main.list.length >= 1) {
              var index = 0;
              for (var i = 0; i < this.ds_thuebao_main.list.length; i++) {
                if (this.ds_thuebao_main.list[i].ma_tb === this.sotb_tam) {
                  index = i;
                  break;
                }
              }
              this.$refs.mainGridRef.setCurrentSelectedRow(index);
            } else {
              this.$toast.warning(
                "Không tìm thấy dữ liệu của số thuê bao bạn đã nhập"
              );
            }

            if (
              response.data.data.dt_hd &&
              response.data.data.dt_hd.length > 0
            ) {
              this.dt_HopDong_All = response.data.data.dt_hd;
            }
            this.flag_gan = false;
          }
        }
      );
    },

    async CapnhatDB() {
      var NHANVIEN_TH = this.$root.token.getNhanVienID();
      // Xử lý với loại truc tiep, gian tiep: Nếu chọn trực tiếp thì xóa hết các hđ ở tab gian tiếp và ngược lại:
      if (this.selectedOption === "1") {
        if (this.dt_HopDong_All.length > 0) {
          var items = [];
          this.dt_HopDong_All.forEach((item) => {
            if (item.loai != "6") {
              items.push(item);
            }
          });
          this.dt_HopDong_All = items;
        }
      }
      if (this.selectedOption === "2") {
        if (this.dt_HopDong_All.length > 0) {
          var items = [];
          this.dt_HopDong_All.forEach((item) => {
            if (item.loai === "6") {
              items.push(item);
            }
          });
          this.dt_HopDong_All = items;
        }
      }

      if (!this.dt_HopDong_All.length > 0) {
        this.$toast.warning("Không có dữ liệu gán nhân viên quản lý!");
        return;
      }

      this.$bvModal
        .msgBoxConfirm(
          "Bạn có muốn cập nhật thông tin nhân viên quản lý không?",
          {
            title: "",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then(async (v) => {
          if (!v) {
            return;
          }
          if (this.kieugoi_id == 1) {
            if (this.dt_HopDong_All.length > 0) {
              var danhSach = "";
              this.dt_HopDong_All.forEach((item) => {
                if (this.data_cskh.length > 0) {
                  if (
                    this.data_cskh.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    ).length > 0
                  ) {
                    var tenloai_nv1 = this.data_cskh.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    )[0].TEN;
                  } else tenloai_nv1 = "Không xác định";
                } else {
                  if (
                    this.options.cbx_dsloainv.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    ).length > 0
                  ) {
                    var tenloai_nv1 = this.options.cbx_dsloainv.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    )[0].TEN;
                  } else tenloai_nv1 = "Không xác định";
                }
                var jsonStr = `{\"HDTB_ID\":\"${item.hdtb_id}\",\"NHANVIEN_ID\":\"${item.nhanvien_id}\",\"LOAINV_ID\":\"${item.loainv_id}\",\"TYLE\":\"${item.tyle}\",\"LOAI\":\"${item.loai}\",\"MA_NV\":\"${item.ma_nv}\",\"TEN_NV\":\"${item.ten_nv}\",\"DONVI_ID\":\"${item.donvi_id}\",\"TEN_DV\":\"${item.ten_dv}\",\"LOAI_NV\":\"${tenloai_nv1}\",\"ISCHECK\":\"0\",\"KIEU\":\"${this.selectedOption}\"},`;
                danhSach = danhSach + jsonStr;
              });
              // bo ky tu , o cuoi
              danhSach = danhSach.substring(0, danhSach.length - 1);
              var body = {
                vkieu: 1,
                vid: this.hdkhId,
                vnhanvien_id_tt: parseInt(NHANVIEN_TH),
                vdanhsach: `[${danhSach}]`,
              };
              var result = await API.gan_nhanvienql_dbhd(this.axios, body);

              if (result.data.error_code === "BSS-00000500") {
                this.$toast.error(result.data.message_detail);
                return;
              }

              var thucthi = "";
              var thongbao_ = "";
              if (result.data.data) {
                thucthi = result.data.data;
              }
              if (thucthi) {
                thongbao_ = thucthi.split("|")[1];
              }

              if (thucthi.charAt(0) != "1") {
                this.$toast.warning(thucthi);
              } else {
                if (thongbao_ === "0") {
                  this.$toast.success("Cập nhật thông tin thành công");
                } else {
                  this.$toast.warning(
                    "Cập nhật thông tin: " +
                      thongbao_ +
                      " đã hoàn thành không bị tác động"
                  );
                }
                this.TimThongTinKH();
              }
            }
          }

          if (this.kieugoi_id == 2) {
            if (this.dt_HopDong_All.length > 0) {
              var danhSach = "";
              this.dt_HopDong_All.forEach((item) => {
                if (this.data_cskh.length > 0) {
                  if (
                    this.data_cskh.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    ).length > 0
                  ) {
                    var tenloai_nv = this.data_cskh.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    )[0].TEN;
                  } else tenloai_nv = "Không xác định";
                } else {
                  if (
                    this.options.cbx_dsloainv.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    ).length > 0
                  ) {
                    var tenloai_nv = this.options.cbx_dsloainv.filter(
                      (items) => items.LOAINV_ID == item.loainv_id
                    )[0].TEN;
                  } else tenloai_nv = "Không xác định";
                }
                var jsonStr = `{\"THUEBAO_ID\":\"${item.thuebao_id}\",\"NHANVIEN_ID\":\"${item.nhanvien_id}\",\"LOAINV_ID\":\"${item.loainv_id}\",\"TYLE\":\"${item.tyle}\",\"LOAI\":\"${item.loai}\",\"MA_NV\":\"${item.ma_nv}\",\"TEN_NV\":\"${item.ten_nv}\",\"DONVI_ID\":\"${item.donvi_id}\",\"TEN_DV\":\"${item.ten_dv}\",\"LOAI_NV\":\"${tenloai_nv}\",\"ISCHECK\":\"0\",\"KIEU\":\"${this.selectedOption}\"},`;
                danhSach = danhSach + jsonStr;
              });
              // bo ky tu , o cuoi
              danhSach = danhSach.substring(0, danhSach.length - 1);
              var body = {
                vkieu: 2,
                vid: this.thuebaoID,
                vnhanvien_id_tt: parseInt(NHANVIEN_TH),
                vdanhsach: `[${danhSach}]`,
              };

              var result = await API.gan_nhanvienql_dbhd(this.axios, body);

              if (result.data.error_code === "BSS-00000500") {
                this.$toast.error(result.data.message_detail);
                return;
              }
              var thucthi = "";
              var thongbao_ = "";
              if (result.data.data) {
                thucthi = result.data.data;
              }
              if (thucthi) {
                thongbao_ = thucthi.split("|")[1];
              }

              if (thucthi.charAt(0) != "1") {
                this.$toast.warning(thucthi);
              } else {
                if (thongbao_ === "0") {
                  this.$toast.success("Cập nhật thông tin thành công");
                } else {
                  this.$toast.warning(
                    "Cập nhật thông tin: " + thongbao_ + "  "
                  );
                }
                this.TimThongTinKH();
              }
            }
          }
        });
    },

    async Gan_click() {
      var tmp_ds_nhanvien = null;
      var vLoaiNV = "0";
      if (this.ds_nhanvien_ptm.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_ptm.list;
        vLoaiNV = "1";
      } else if (this.ds_nhanvien_hotro.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_hotro.list;
        vLoaiNV = "3";
      } else if (this.ds_nhanvien_duytri.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_duytri.list;
        vLoaiNV = "2";
      } else if (this.ds_nhanvien_giantiep.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_giantiep.list;
        vLoaiNV = "6";
      } else if (this.ds_nhanvien_cskh.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_cskh.list;
        vLoaiNV = "5";
      }else if(this.ds_nhanvien_hotro_kythuat.active){
        tmp_ds_nhanvien = this.ds_nhanvien_hotro_kythuat.list;
        vLoaiNV = "7";
      }
      // Kiểm tra HRM
      var kiemtra_hrm = true;
      if (this.ds_nhavien_chuagan.select_chuagan) {
        for (
          var i = 0;
          i < this.ds_nhavien_chuagan.select_chuagan.length;
          i++
        ) {
          var item = this.ds_nhavien_chuagan.select_chuagan[i];
          if (this.loainv_id == "449") {
            try {
              var response = await API.xacThucNhanVien(this.axios, item.ma_nv);
              if (
                response.data &&
                response.data.error_code === "BSS-00000000" &&
                response.data.data.ctHoSoNhanSu &&
                response.data.data.ctHoSoNhanSu.length > 0
              ) {
                var data_dv = response.data.data.ctHoSoNhanSu;
              } else {
                kiemtra_hrm = false;
                this.$toast.warning("Lỗi kiểm tra mã nhân viên: " + item.ma_nv);
                return;
              }
            } catch (error) {
                kiemtra_hrm = false;
                this.$toast.warning("Có lỗi xảy ra khi kiểm tra mã nhân viên: " + error);
                return;
            }
          }
        }
      }

      if (!kiemtra_hrm) return; //

      // if (vLoaiNV == "3" && this.loainv_id == 451) {
      //   this.$toast.warning(
      //     "Nhân viên phát triển hỗ trợ không thể chọn loại nhân viên (AM)"
      //   );
      //   return;
      // }

      if (this.kieugoi_id == 1) {
        if (this.ds_nhavien_chuagan.select_chuagan.length > 0) {
          this.ds_nhavien_chuagan.select_chuagan.forEach((item) => {
            if (
              !tmp_ds_nhanvien.some(
                (nv) =>
                  nv.ma_nv == item.ma_nv &&
                  nv.hdtb_id == this.hdtbId &&
                  nv.loai == vLoaiNV
              )
            ) {
              var new_item = structuredClone(item);
              new_item.hdtb_id = this.hdtbId;
              new_item.loai = vLoaiNV;
              tmp_ds_nhanvien.push(new_item);
            }
            if (
              !this.dt_HopDong_All ||
              !this.dt_HopDong_All.some(
                (nv) =>
                  nv.ma_nv == item.ma_nv &&
                  nv.hdtb_id == this.hdtbId &&
                  nv.loai == vLoaiNV
              )
            ) {
              var new_item = structuredClone(item);
              new_item.hdtb_id = this.hdtbId;
              new_item.loai = vLoaiNV;
              this.dt_HopDong_All.push(new_item);
            } else {
              for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                if (
                  this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                  this.dt_HopDong_All[i].hdtb_id == this.hdtbId
                ) {
                  this.dt_HopDong_All[i].ischeck = 1;
                }
              }
            }
          });
          this.ds_nhanvien_hotro_kythuat.select_danggo = [];
          this.ds_nhanvien_hotro.select_danggo = [];
          this.ds_nhanvien_duytri.select_danggo = [];
          this.ds_nhanvien_ptm.select_danggo = [];
          this.ds_nhanvien_giantiep.select_danggo = [];
          this.ds_nhanvien_cskh.select_danggo = [];
        } else {
          this.$toast.warning("Chưa chọn danh sách nhân viên quản lý!");
        }
      }

      if (this.kieugoi_id == 2) {
        if (this.ds_nhavien_chuagan.select_chuagan.length > 0) {
          this.ds_nhavien_chuagan.select_chuagan.forEach((item) => {
            if (
              !tmp_ds_nhanvien.some(
                (nv) =>
                  nv.ma_nv == item.ma_nv &&
                  nv.thuebao_id == this.thuebaoID &&
                  nv.loai == vLoaiNV
              )
            ) {
              var new_item = structuredClone(item);
              new_item.thuebao_id = this.thuebaoID;
              new_item.loai = vLoaiNV;
              tmp_ds_nhanvien.push(new_item);
            }
            if (
              !this.dt_HopDong_All ||
              !this.dt_HopDong_All.some(
                (nv) =>
                  nv.ma_nv == item.ma_nv &&
                  nv.thuebao_id == this.thuebaoID &&
                  nv.loai == vLoaiNV
              )
            ) {
              var new_item = structuredClone(item);
              new_item.thuebao_id = this.thuebaoID;
              new_item.loai = vLoaiNV;
              this.dt_HopDong_All.push(new_item);
            } else {
              for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                if (
                  this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                  this.dt_HopDong_All[i].thuebao_id == this.thuebaoID
                ) {
                  this.dt_HopDong_All[i].ischeck = 1;
                }
              }
            }
          });
          this.ds_nhanvien_hotro_kythuat.select_danggo = [];
          this.ds_nhanvien_hotro.select_danggo = [];
          this.ds_nhanvien_duytri.select_danggo = [];
          this.ds_nhanvien_ptm.select_danggo = [];
          this.ds_nhanvien_giantiep.select_danggo = [];
          this.ds_nhanvien_cskh.select_danggo = [];
        } else {
          this.$toast.warning("Chưa chọn danh sách nhân viên quản lý!");
        }
      }
    },
    Gogan_click() {
      var tmp_ds_nhanvien;
      var vLoaiNV = "0";
      if (this.ds_nhanvien_ptm.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_ptm;
        vLoaiNV = "1";
      } else if (this.ds_nhanvien_hotro.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_hotro;
        vLoaiNV = "3";
      } else if (this.ds_nhanvien_duytri.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_duytri;
        vLoaiNV = "2";
      } else if (this.ds_nhanvien_giantiep.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_giantiep;
        vLoaiNV = "6";
      } else if (this.ds_nhanvien_cskh.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_cskh;
        vLoaiNV = "5";
      } else if (this.ds_nhanvien_hotro_kythuat.active) {
        tmp_ds_nhanvien = this.ds_nhanvien_hotro_kythuat;
        vLoaiNV = "7";
      }

      if (tmp_ds_nhanvien.select_danggo.length > 0) {
        if (this.check_all == false) {
          tmp_ds_nhanvien.select_danggo.forEach((item) => {
            if (this.kieugoi_id == 2) {
              for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                if (
                  this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                  this.dt_HopDong_All[i].loai == vLoaiNV &&
                  this.dt_HopDong_All[i].thuebao_id == item.thuebao_id
                ) {
                  this.dt_HopDong_All.splice(i, 1);
                  break;
                }
              }
            }
            if (this.kieugoi_id == 1) {
              for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                if (
                  this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                  this.dt_HopDong_All[i].loai == vLoaiNV &&
                  this.dt_HopDong_All[i].hdtb_id == item.hdtb_id
                ) {
                  this.dt_HopDong_All.splice(i, 1);
                  break;
                }
              }
            }

            for (var i = 0; i < tmp_ds_nhanvien.list.length; i++) {
              if (
                tmp_ds_nhanvien.list[i].ma_nv == item.ma_nv &&
                tmp_ds_nhanvien.list[i].loai == vLoaiNV
              ) {
                tmp_ds_nhanvien.list.splice(i, 1);
                break;
              }
            }
          });
        } else {
          // Gỡ toàn bộ thuê bao thuộc HD + loại
          this.$bvModal
            .msgBoxConfirm(
              "Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?",
              {
                title: "",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              }
            )
            .then(async (v) => {
              if (!v) {
                return;
              }

              tmp_ds_nhanvien.select_danggo.forEach((item) => {
                for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                  if (
                    this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                    this.dt_HopDong_All[i].loai == vLoaiNV
                  ) {
                    this.dt_HopDong_All.splice(i, 1);
                    i = i - 1;
                  }
                }

                for (var i = 0; i < tmp_ds_nhanvien.list.length; i++) {
                  if (
                    tmp_ds_nhanvien.list[i].ma_nv == item.ma_nv &&
                    tmp_ds_nhanvien.list[i].loai == vLoaiNV
                  ) {
                    tmp_ds_nhanvien.list.splice(i, 1);
                    break;
                  }
                }
              });
            });
        }
      } else {
        this.$toast.warning("Bạn chưa check chọn dữ liệu để gỡ!");
      }
    },

    actionCompleteHandle(args) {
      // Xu ly danh sach filter
      if (args.rows && args.rows.length > 0) {
        this.ds_nhavien_chuagan.display_list = [];
        args.rows.forEach((row) => {
          this.ds_nhavien_chuagan.display_list.push(row.data);
        });
      }
    },

    CLEAR() {
      this.dt_HopDong_All = [];
      (this.ds_thuebao_main.list = []),
        (this.ds_nhanvien_ptm.list = []),
        (this.ds_nhanvien_hotro.list = []),
        (this.ds_nhanvien_hotro_kythuat.list = []),
        (this.ds_nhanvien_duytri.list = []),
        (this.ds_danhba_timkiem.list = []),
        (this.ds_nhanvien_giantiep.list = []),
        (this.ds_nhanvien_cskh.list = []),
        (this.flag_gan = true),
        (this.dvi_tt = -1);
    },

    // Set cho đơn vị:
    CLEAR_COMBO() {
      this.dvi_tt = -1;
      this.options.cbx_dsdonvi = [];
      this.ds_nhavien_chuagan.list = [];
    },

    // Nếu tích checkbox toàn bộ nhân viên
    check(e) {
      this.check_all = e.target.checked;
    },

    //Nếu tích chọn radio
    check_radio(e) {
      if (this.selectedOption === "1") {
        this.hideTab = false;
        this.SelectTab1();
      }
      if (this.selectedOption === "2") {
        this.hideTab = true;
        this.SelectTabGIANTIEP();
      }
    },

    async init() {
      if (this.kieugoi_id != 0) {
        var response = await API.ADMIN_LOAI_NV(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          // Xử lý loại  nhân viên all:
          this.data_cskh = response.data.data;

          var data_lnv = this.data_cskh.filter(
            (item) => item.LOAINV_ID >= 450 && item.LOAINV_ID <= 454
          );
          data_lnv.push({ LOAINV_ID: 449, TEN: "Khác" });
          this.options.cbx_dsloainv = data_lnv;

          // Xử lý nếu không có LOAINV_ID = 450
          var data_default;
          if (data_lnv.some((item) => item.LOAINV_ID === 450)) {
            data_default = data_lnv.find((item) => item.LOAINV_ID === 450);
          } else {
            data_default = data_lnv[0];
          }
          if (data_default && data_default.LOAINV_ID > 0) {
            this.loainv_id = data_default.LOAINV_ID;
            this.NAP_DS_DVI_LINE(-1, this.loainv_id);
          }
        } else {
          this.$toast.warning(
            "Có lỗi khi quá trình nạp dữ liệu từ DB. Liên hệ Admin để xử lý!"
          );
        }
      }
    },

    async sp_lay_ds_nhanvien_theo_manv(ls_manv){
      try {
        let response = await this.axios.post('/web-hopdong/tochuc_hoanthien_kenh_khdn/sp_lay_ds_nhanvien_theo_manv', {
          ls_manv: ls_manv
        });
        if (
            response &&
            response.data &&
            response.data.error_code === "BSS-00000000"
          ) {
            
          }
      } catch (error) {
        console.log(error);
      }
    }
  },
};
</script>