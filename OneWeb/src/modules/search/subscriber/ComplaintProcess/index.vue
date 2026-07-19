<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
import dummy from "./dummy";
import { DataManager, Predicate, Query } from "@syncfusion/ej2-data";
import monthSelectPlugin from "flatpickr/dist/plugins/monthSelect/index.js";
import Vue from "vue";
import PopupGuiSms from "@/modules/install/UpdateInstallProgress/popups/PopupGuiSms.vue";
import PopupNhapTienTrinhKhieuNai from "./popups/PopupNhapTienTrinhKhieuNai";
import PopupThayDoiThongTinKhieuNai from "./popups/PopupThayDoiThongTinKhieuNai";
import PopupThayDoiThongTinKhieuNaiV2 from "./popups/PopupThayDoiThongTinKhieuNaiV2";
import PopupChonNVSMS from "./popups/PopupChonNVSMS";
import XLSX from "xlsx";
var SMSBnt = Vue.component("SMSBnt", {
  template: "<button class='btn' v-on:click='onClickBnt'>sms</button>",
  data() {
    return {
      data: {},
    };
  },
  methods: {
    onClickBnt() {
      const parentForm = this.$parent.$parent.$parent;
      parentForm.onClickSendSMS(this.data);
    },
  },
});
var EditBnt = Vue.component("EditBnt", {
  template:
    "<button class='btn' v-on:click='onClickBnt'><i class='fa fa-ellipsis-h'></i></button>",
  data() {
    return {
      data: {},
    };
  },
  methods: {
    onClickBnt() {
      const parentForm = this.$parent.$parent.$parent;
      parentForm.showPopupNhapTienTrinhKhieuNai(this.data);
      // parentForm.onClickSendSMS(this.data)
    },
  },
});
var DeleteBnt = Vue.component("DeleteBnt", {
  template:
    "<button class='btn' v-on:click='onClickBnt'><i class='fa fa-close'></i></button>",
  data() {
    return {
      data: {},
    };
  },
  methods: {
    async onClickBnt() {
      try {
        this.loading(true);
        const response = await api.sp_delete_tientrinh_kn(this.axios, {
          vtientrinhkn_id: this.data.khieunai_id,
        });
        if (
          (response.data.error === 200 || response.data.error === "200") &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.$root.$toast.success("Xóa thành công");
          parentForm.get_ds_tientrinhxuly();
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading(false);
      }
    },
  },
});
export default {
  components: {
    breadcrumb,
    PopupGuiSms,
    PopupNhapTienTrinhKhieuNai,
    PopupThayDoiThongTinKhieuNai,
    PopupThayDoiThongTinKhieuNaiV2,
    PopupChonNVSMS,
  },
  name: "ComplaintProcess",
  props: {
    isPopup: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.loadData();
    // this.addKeyDownEvent()
  },
  destroyed() {},
  computed: {
    enableThayDoiTTBnt() {
      return this.gridboxDSPYC.selected !== null;
    },
    enableXuatFileBnt() {
      return this.gridboxDSPYC.data.length > 0;
    },
    enableXuatExcelBnt() {
      return this.gridboxDSPYC.data.length > 0;
    },
    enableNhanTinBnt() {
      return this.gridboxDSPYC.selected !== null;
    },
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    openedPopup: function (val, oldval) {
      if (val) {
        this.removeKeyDownEvent();
      } else {
        this.addKeyDownEvent();
      }
    },
    "gridboxDSPYC.selected": {
      handler: function (val, oldval) {
        if (val) {
          this.TTTB = {
            SoMay: val.ma_tb,
            TenTB: val.ten_tb,
            DiaChiTB: val.diachi_tb,
            NoiDungKn: val.nd_khieunai,
            NoiDungGQ: val.noidung_gq,
          };
          this.get_ds_tientrinhxuly();
        } else {
          this.gridboxDSTTXL.data = [];
          this.gridboxDSTTXL.selected = null;
          this.TTTB = {
            SoMay: "",
            TenTB: "",
            DiaChiTB: "",
            NoiDungKn: "",
            NoiDungGQ: "",
          };
        }
      },
      deep: true,
    },
    "kyHoaDon.value": {
      handler: function (val, oldval) {
        this.get_chuky();
      },
      deep: true,
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "CẬP NHẬT TIẾN TRÌNH KHIẾU NẠI",
        list: [],
      },
      enableTiemKiemBnt: true,
      popupNhapTienTrinhKhieuNaiData: { noidung: "" },
      smsProp: { noiDung: "", maTB: "" },
      chuky: { data: [], selected: null },
      DonViXuly: {
        selected: null,
        checkbox: false,
        search: "",
        data: [],
        fields: {
          dataSource: [],
          id: "donvi_id",
          text: "ten_dv",
          parentID: "donvi_cha_id",
          hasChildren: "hasChild",
          selected: "isSelected",
        },
        // fields: { dataSource: dummy.dataSource, id: 'id', parentID: 'pid', text: 'name', hasChildren: 'hasChild', selected: 'isSelected' }
      },
      kyHoaDon: {
        value: null,
        dateconfig: {
          plugins: [
            new monthSelectPlugin({
              shorthand: true,
              dateFormat: "m/Y",
            }),
          ],
        },
      },
      TTTK: {
        loaiKN: { data: [], selected: null },
        trangThai: { data: [], selected: null },
        isEnabledDate: false,
        fromDate: moment(new Date()).format("DD/MM/YYYY"),
        toDate: moment(new Date()).format("DD/MM/YYYY"),
        dateconfig: {
          altFormat: "d/m/Y",
          altInput: true,
          dateFormat: "d/m/Y",
          allowInput: true,
        },
      },
      TTTB: { SoMay: "", TenTB: "", DiaChiTB: "", NoiDungKn: "", NoiDungGQ: "" },
      gridboxDSTTXL: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "nguoi_cn",
            headerText: "Người CN",
            allowFiltering: true,
            allowSorting: false,
            width: "100",
            freeze: "Left",
          },
          {
            fieldName: "ngay_cn",
            headerText: "Ngày CN",
            allowFiltering: true,
            allowSorting: false,
            width: "100",
            freeze: "Left",
          },
          {
            fieldName: "donvi_cn",
            headerText: "Đơn vị",
            allowFiltering: true,
            allowSorting: false,
            width: "100",
          },
          {
            fieldName: "noidung",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "SMS",
            headerText: "SMS",
            allowFiltering: false,
            allowSorting: false,
            width: "70",
            template: () => {
              return {
                template: SMSBnt,
              };
            },
          },
          {
            fieldName: "EDIT",
            headerText: "Sửa",
            allowFiltering: false,
            allowSorting: false,
            width: "70",
            template: () => {
              return {
                template: EditBnt,
              };
            },
          },
          {
            fieldName: "DELETE",
            headerText: "Xóa",
            allowFiltering: false,
            allowSorting: false,
            width: "70",
            template: () => {
              return {
                template: DeleteBnt,
              };
            },
          },
        ],
      },
      gridboxDSPYC: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "ma_kn",
            headerText: "Mã khiếu nại",
            allowFiltering: true,
            allowSorting: false,
            width: "120",
            freeze: "Left",
          },
          {
            fieldName: "ngay_tn",
            headerText: "Ngày tiếp nhận",
            allowFiltering: true,
            allowSorting: false,
            width: "120",
            freeze: "Left",
          },
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            allowSorting: false,
            width: "120",
            freeze: "Left",
          },
          {
            fieldName: "loaihinh_tb",
            headerText: "Loại hình",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên TB",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "diachi_tb",
            headerText: "Địa chỉ TB",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "trangthai_kn",
            headerText: "Trạng thái KN",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "nguoi_kn",
            headerText: "Người KN",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "nd_khieunai",
            headerText: "Nội dung KN",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "tenchude",
            headerText: "Chủ đề khiếu nại",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "nhanvien_tn",
            headerText: "Người tiếp nhận",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "donvi_tn",
            headerText: "Đơn vị tiếp nhận",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "dienthoai_lh",
            headerText: "Điện thoại liên hệ của người khiếu nại",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "nhanvien_gq",
            headerText: "Người giải quyết",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ngay_gq",
            headerText: "Ngày giải quyết",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "congvan_xl",
            headerText: "Số CV Xlý",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "nguyennhan_kn",
            headerText: "Nguyên nhân",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "noidung_gq",
            headerText: "Nội dung GQ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "tien_giamcuoc",
            headerText: "Tiền giảm cước",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "tenphong",
            headerText: "Đơn vị xử lý",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_kv",
            headerText: "Khu vực",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "tennv_tc",
            headerText: "Nhân viên TC",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },
      LoaiDV: { data: [], selected: null },
      openedPopup: false,
    };
  },
  methods: {
    addKeyDownEvent() {
      window.addEventListener("keydown", this.onF3KeyDown);
    },
    removeKeyDownEvent() {
      window.removeEventListener("keydown", this.onF3KeyDown);
    },
    onF3KeyDown(event) {
      if (event.keyCode === 114) {
        event.preventDefault();
        this.search();
      }
    },
    GetData(response) {
      console.log(response);
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    GetDataObj(response) {
      console.log(response);
      if (
        (response.data.error === 200 || response.data.error === "200") &&
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        throw new Error(response.data.message);
      }
    },
    onClickSendSMS(data) {
      this.smsProp.maTB = this.gridboxDSPYC.selected.ma_tb;
      this.smsProp.noiDung = data.noidung;
      this.$refs.guiSmsRef.onInit();
      this.$refs.popupGuiSmsRef.show();
    },
    onClosePopupGuiSMS() {
      this.$refs.popupGuiSmsRef.hide();
    },
    showPopupNhapTienTrinhKhieuNai(data) {
      this.popupNhapTienTrinhKhieuNaiData = data;
      this.$refs.popupNhapTienTrinhKhieuNai.show();
    },
    onSuccessPopupNhapTienTrinhKhieuNai() {
      this.get_ds_tientrinhxuly();
      this.$refs.popupNhapTienTrinhKhieuNai.hide();
    },
    onSuccessPopupThayDoiThongTinKhieuNai() {
      this.$refs.popupThayDoiThongTinKhieuNai.hide();
    },
    onSuccessPopupThayDoiThongTinKhieuNaiV2() {
      this.$refs.popupThayDoiThongTinKhieuNaiV2.hide();
    },
    onSuccessPopupChonNVSMS() {
      this.$refs.popupChonNVSMS.hide();
    },
    async loadData() {
      await this.getKyHoaDon();
      await this.get_CHECK_CHON_TIENTRINH_KN();
      await this.get_CB_TTTK_LoaiKN();
      await this.get_CB_TTTK_TrangThai();
      await this.getDonViTreeData();
      await this.get_CB_LoaiDV();
    },
    async get_CHECK_CHON_TIENTRINH_KN() {
      this.DonViXuly.checkbox = false;
      try {
        this.loading = true;
        const data = this.GetData(
          await api.sp_lay_ds_thamso_md(this.axios, { kieu_id: -1 })
        );
        const fItem = data.find((item) => item.ma_ts === "CHECK_CHON_TIENTRINH_KN");
        if (fItem && fItem.ten_ts === "1") {
          this.DonViXuly.checkbox = true;
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async get_CB_TTTK_LoaiKN() {
      this.TTTK.loaiKN = { data: [], selected: null };
      try {
        this.loading = true;
        this.TTTK.loaiKN.data = this.GetData(
          await api.sp_tt_loai_kn(this.axios, {
            p_type: 1,
          })
        );
        if (this.TTTK.loaiKN.data.length > 0) {
          this.TTTK.loaiKN.selected = this.TTTK.loaiKN.data[0];
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async get_CB_TTTK_TrangThai() {
      this.TTTK.trangThai = { data: [], selected: null };
      try {
        this.loading = true;
        this.TTTK.trangThai.data = this.GetData(
          await api.sp_nap_cbo_trangthai_bh(this.axios, {})
        );
        if (this.TTTK.trangThai.data.length > 0) {
          this.TTTK.trangThai.selected = this.TTTK.trangThai.data[0];
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async get_CB_LoaiDV() {
      this.LoaiDV = { data: [], selected: null };
      try {
        this.loading = true;
        this.LoaiDV.data = this.GetData(await api.sp_nap_cbo_loai_dv_bh(this.axios, {}));
        this.LoaiDV.selected = 2;
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async get_ds_tientrinhxuly() {
      if (this.gridboxDSPYC.selected && this.gridboxDSPYC.selected.khieunai_id) {
        this.gridboxDSTTXL.selected = null;
        try {
          this.loading = true;
          this.gridboxDSTTXL.data = this.GetData(
            await api.sp_lay_ds_tientrinh_kn(this.axios, {
              vkhieunai_id: this.gridboxDSPYC.selected.khieunai_id,
            })
          ); //524706 })) //
        } catch (err) {
          this.gridboxDSTTXL.data = [];
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    async get_chuky() {
      this.chuky = { data: [], selected: null };
      if (this.kyHoaDon.value) {
        try {
          this.loading = true;
          this.chuky.data = this.GetData(
            await api.sp_ds_kycuoc(this.axios, {
              p_namthang: moment(this.kyHoaDon.value, "MM/YYYY").format("YYYYMM"),
            })
          );
          if (this.chuky.data.length > 0) {
            this.chuky.selected = this.chuky.data[0].chuky;
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },

    async get_DSPYC() {
      if (this.DonViXuly.selected) {
        this.gridboxDSPYC.selected = null;
        try {
          this.loading = true;
          const params = {
            vloai_id: this.TTTK.loaiKN.selected.loai_id,
            vloaidv_id: this.LoaiDV.selected,
            vtrangthai_id: this.TTTK.trangThai.selected.ttbh_id,
            vtungay: this.TTTK.isEnabledDate ? this.TTTK.fromDate : "0",
            vdenngay: this.TTTK.isEnabledDate ? this.TTTK.toDate : "0",
            vkyhoadon: moment(this.kyHoaDon.value, "MM/YYYY").format("YYYYMM"),
            vdonvi_id: this.DonViXuly.selected,
            vnhanvien_id: this.$root.token.getNguoiDungID(),
          };
          if (this.DonViXuly.checkbox) {
            this.gridboxDSPYC.data = this.GetData(
              await api.ds_khieunai_tientrinh(this.axios, params)
            );
          } else {
            this.gridboxDSPYC.data = this.GetData(
              await api.ds_khieunai_tientrinh_v2(this.axios, params)
            );
          }
          if (this.gridboxDSPYC.data.length === 0) {
            this.$toast.warning("Không tìm thấy dữ liệu");
          }
        } catch (err) {
          this.gridboxDSPYC.data = [];
          console.error(err);
        } finally {
          this.loading = false;
        }
      } else {
        this.$toast.error("Chưa chọn đơn vị");
      }
    },
    changeDataSourceDonViXuly(dataSource) {
      this.DonViXuly.fields = {
        dataSource: dataSource,
        id: "donvi_id",
        text: "ten_dv",
        parentID: "donvi_cha_id",
        hasChildren: "hasChild",
        selected: "isSelected",
      };
    },
    async getDonViTreeData() {
      this.DonViXuly.selected = null;
      try {
        this.loading = true;
        let donviIDCHA = "-1";
        const nguoiDungID = this.$root.token.getNguoiDungID();
        const user_map = this.GetDataObj(
          await api.fn_tt_nguoidung(this.axios, { param: `${nguoiDungID}`, type: 3 })
        );
        if (user_map == null || user_map == "-1" || user_map == "") {
          donviIDCHA = this.GetDataObj(
            await api.fn_tt_donvi(this.axios, {
              param: `${this.$root.token.getDonViID()}`,
              type: 5,
            })
          );
          if (donviIDCHA == "-1") donviIDCHA = this.$root.token.getDonViID();
        } else {
          donviIDCHA = this.GetDataObj(
            await api.sp_lay_donvi_id_theo_ma_nd(this.axios, {
              v_ma_nd: user_map,
            })
          );
        }
        const dsDv = this.GetData(
          await api.sp_giaosim_get_treeview(this.axios, {
            p_donvicha_id: donviIDCHA,
          })
        );
        this.DonViXuly.data = this.createTreeDonVi(dsDv, [donviIDCHA.toString()]);
        this.DonViXuly.data.sort((a, b) => {
          return a.ten_dv.localeCompare(b.ten_dv);
        });
        this.changeDataSourceDonViXuly(this.DonViXuly.data);
        setTimeout(() => {
          this.$refs.tree.expandAll();
        }, 100);
      } catch (err) {
        this.DonViXuly.data = [];
        this.changeDataSourceDonViXuly(this.DonViXuly.data);
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async getKyHoaDon() {
      this.kyHoaDon.value = moment()
        .subtract(1, "months")
        .startOf("month")
        .format("MM/YYYY");
    },
    createTreeDonVi(dsDonVi, selectedIdxs = []) {
      let tree = [];
      let donviChaArray = dsDonVi.map((item) => {
        return item["donvi_cha_id"];
      });

      let donviArray = dsDonVi.map((item) => item["donvi_id"]);
      dsDonVi.forEach((item) => {
        const isSelected = selectedIdxs.includes(item.donvi_id.toString());
        if (donviChaArray.includes(item["donvi_id"])) {
          if (!donviArray.includes(item["donvi_cha_id"])) {
            delete item.donvi_cha_id;
          }
          tree.push({ ...item, hasChild: true, expanded: false, isSelected: isSelected });
        } else {
          if (item.unitlevel == 1) delete item.donvi_cha_id;
          tree.push({ ...item, isSelected: isSelected });
        }
      });
      this.DonViXuly.selected = dsDonVi
        .filter((item) => item.isSelected)
        .map((item) => item["donvi_id"])
        .join(",");
      return tree;
    },
    async searchDonVi() {
      let predicate;
      let predicats = [];
      let _array = [];
      let _filter = [];
      if (this.DonViXuly.search.trim() !== "") {
        predicate = new Predicate(
          "ten_dv",
          "contains",
          this.DonViXuly.search.trim(),
          true
        );
        let filteredList = new DataManager(this.DonViXuly.data).executeLocal(
          new Query().where(predicate)
        );
        for (let j = 0; j < filteredList.length; j++) {
          _filter.push(filteredList[j]["donvi_id"]);
          let filters = this.getFilterItems(filteredList[j], this.DonViXuly.data);
          for (let i = 0; i < filters.length; i++) {
            if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
              _array.push(filters[i]);
              predicats.push(new Predicate("donvi_id", "equal", filters[i], false));
            }
          }
        }
        if (predicats.length === 0) {
          this.changeDataSourceDonViXuly([]);
        } else {
          let query = new Query().where(Predicate.or(predicats));
          let newList = new DataManager(this.DonViXuly.data).executeLocal(query);
          this.changeDataSourceDonViXuly(newList);
        }
      } else {
        this.changeDataSourceDonViXuly(this.DonViXuly.data);
      }
      setTimeout(() => {
        this.$refs.tree.expandAll();
      }, 100);
    },
    getFilterItems(fList, list) {
      let nodes = [];
      nodes.push(fList["donvi_id"]);
      let query2 = new Query().where("donvi_id", "equal", fList["donvi_cha_id"], false);
      let fList1 = new DataManager(list).executeLocal(query2);
      if (fList1.length !== 0) {
        let pNode = this.getFilterItems(fList1[0], list);
        for (let i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) === -1 && pNode[i] != null) {
            nodes.push(pNode[i]);
          }
        }
        return nodes;
      }
      return nodes;
    },
    DonViXulySortTree() {
      const data = this.DonViXuly.data.sort((a, b) => (a.ten_dv < b.ten_dv ? -1 : 1));
      this.changeDataSourceDonViXuly(data);
      this.$refs.tree.dataBind();
    },
    DonViXulyExpandedNodes() {},
    DonViXulyNodeSelected() {
      if (!this.DonViXuly.checkbox) {
        const treeObj = document.getElementById("treeviewComplaintProcess")
          .ej2_instances[0];
        if (treeObj) {
          this.DonViXuly.selected = treeObj.selectedNodes.join(",");
        }
      }
    },
    DonViXulyNodeChecked() {
      if (this.DonViXuly.checkbox) {
        const treeObj = document.getElementById("treeviewComplaintProcess")
          .ej2_instances[0];
        if (treeObj) {
          this.DonViXuly.selected = treeObj.checkedNodes.join(",");
        }
      }
    },
    DonViXulyNodeClicked() {},
    selectedItemsChangedGridboxDSTTXL(items) {
      if (items.length > 0) {
        this.gridboxDSTTXL.selected = items[0];
      }
    },
    selectedItemsChangedGridboxDSPYC(items) {
      if (items.length > 0) this.gridboxDSPYC.selected = items[0];
    },
    TiemKiemBnt() {
      this.get_DSPYC();
    },
    async ThayDoiTTBnt() {
      // this.$toast.info('>< sẽ tích hợp sau')
      try {
        this.loading = true;
        const data = this.GetData(
          await api.sp_lay_ds_thamso_md(this.axios, { kieu_id: -1 })
        );
        const fItem = data.find((item) => item.ma_ts === "THAY_DOI_KN");
        if (fItem && fItem.ten_ts === "1") {
          this.$refs.popupThayDoiThongTinKhieuNaiV2.show();
        } else {
          this.$refs.popupThayDoiThongTinKhieuNai.show();
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    XuatExcelBnt() {
      if (this.gridboxDSPYC.data.length > 0) {
        let exportData = [];
        let exportHeader = this.gridboxDSPYC.columns.map((item) => item.headerText);
        this.gridboxDSPYC.data.forEach((row) => {
          const r = {};
          this.gridboxDSPYC.columns.forEach((column) => {
            r[column.headerText] = row[column.fieldName] || "";
          });
          exportData.push(r);
        });

        let data = XLSX.utils.json_to_sheet(exportData, { header: exportHeader });
        let wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, data, "TS");
        // export Excel file
        XLSX.writeFile(wb, "export.xlsx");
      } else {
        this.$toast.error("Không có dữ liệu để xuất");
      }
    },
    XuatFileBnt() {},
    NhanTinBnt() {
      this.$refs.popupChonNVSMS.show();
    },
    clearData() {},
  },
};
</script>
