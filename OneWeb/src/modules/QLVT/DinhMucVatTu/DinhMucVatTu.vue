<template src="./DinhMucVatTu.html"></template>

<script>
import API from "../api/DinhMucVatTu.js";
import Vue from "vue";
import moment from "moment";
import {
  TreeGridPlugin,
  Page,
  Filter,
  Sort,
  Resize,
  Aggregate,
  Toolbar,
  Freeze,
  Edit,
} from "@syncfusion/ej2-vue-treegrid";
Vue.use(TreeGridPlugin);
import { L10n } from "@syncfusion/ej2-base";
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
      Item: "Bản ghi",
      Items: "Bản ghi",
    },
    pager: {
      currentPageInfo: "{0} trên {1} trang",
      totalItemsInfo: "({0} Bản ghi)",
      pagerDropDown: "Bản ghi trên trang",
    },
  },
});

export default {
  name: "FrmDinhMucVatTu",
  provide: {
    treegrid: [Freeze, Edit, Page, Filter, Sort, Resize, Aggregate, Toolbar],
  },
  data: function () {
    return {
      gParams: {
        KhoCgIndex: null,
        DviIndex: null,
        KhoCgWord: null,
        DviWord: null,
        cboDichVu: "",
        cboLoaiHinh: "",
        dtpDenNgay: new Date(),
        txtTenDinhMuc: "",
        txtGhiChu: "",
        dtDinhMucRowChanged: {},
        dtVatTuRowChanged: {},
        dtDonViRowChanged: {},
      },
      dtDinhMuc: [],
      dtVatTu: [],
      dtDonVi: [],
      cboDichVu: [],
      cboLoaiHinh: [],
      tabIndex: 0,
      TABS: {
        VATTU: 0,
        DONVI: 1,
      },
      enabled: {
        tsbtnNew: true,
        tsbtnSave: true,
        tsbtnCancel: true,
        tsbtnDel: true,
        dtpDenNgay: true,
      },
      isChangeCboLoaiHinh: false,
      editSettings: { allowEditing: true, mode: "Batch" },
      tvVatTu_Right: [
        // {
        //   TEN_VT: "11111111 - Một",
        //   DVI_TINH: "dcm",
        //   DINHMUC: 13242,
        //   children: [
        //     {
        //       TEN_VT: "2222 - Một",
        //       DVI_TINH: "dcm",
        //       DINHMUC: 413242,
        //     },
        //   ],
        // },
      ],
      tvDonvi_Right: [
        // {
        //   TEN_DV: "11111111 - Một",
        //   DINHMUC: 13242,
        //   children: [
        //     {
        //       TEN_DV: "2222 - Một",
        //       DVI_TINH: "dcm",
        //       DINHMUC: 413242,
        //     },
        //   ],
        // },
      ],
      selectionSettings: {
        type: "Multiple",
        checkboxOnly: true,
      },
      pageSettings: {
        pageSizes: ["10", "20", "50", "100", "All"],
        pageSize: 10,
      },

      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      listVatTuCGTree: [],
      value_GrvRight_Vattu: {
        pageNo: 1,
        pageSize: 10,
        totalRow: 1,
      },
    };
  },
  computed: {
    state: {
      get() {
        return this.enabled.tsbtnNew;
      },
    },
    cell_dinhmucID: {
      get() {
        return !this.gParams.dtDinhMucRowChanged.hasOwnProperty("DINHMUC_ID") ||
          !this.gParams.dtDinhMucRowChanged["DINHMUC_ID"]
          ? 0
          : parseInt(this.gParams.dtDinhMucRowChanged["DINHMUC_ID"]);
      },
    },
    cell_id: {
      get() {
        return !this.gParams.dtDinhMucRowChanged.hasOwnProperty("ID") ||
          !this.gParams.dtDinhMucRowChanged["ID"]
          ? 0
          : parseInt(this.gParams.dtDinhMucRowChanged["ID"]);
      },
    },
    cell_tendinhmuc: {
      get() {
        return !this.gParams.dtDinhMucRowChanged.hasOwnProperty("TEN_DM") ||
          !this.gParams.dtDinhMucRowChanged["TEN_DM"]
          ? ""
          : this.gParams.dtDinhMucRowChanged["TEN_DM"];
      },
    },
    cell_ngaylap: {
      get() {
        return !this.gParams.dtDinhMucRowChanged.hasOwnProperty("NGAYLAP") ||
          !this.gParams.dtDinhMucRowChanged["NGAYLAP"]
          ? ""
          : this.gParams.dtDinhMucRowChanged["NGAYLAP"];
      },
    },
    cell_ghichu: {
      get() {
        return !this.gParams.dtDinhMucRowChanged.hasOwnProperty("GHICHU") ||
          !this.gParams.dtDinhMucRowChanged["GHICHU"]
          ? ""
          : this.gParams.dtDinhMucRowChanged["GHICHU"];
      },
    },
    getTreeVTs() {
      return this.list_to_tree(this.tvVatTu_Right);
    },
    getTreeDVs() {
      return this.list_to_tree_dv(this.tvDonvi_Right);
    },
  },
  methods: {
    dataBound(args) {
      this.$refs.tvVatTu_Right.ej2Instances.grid.filterModule.filterOperators.startsWith =
        "contains";
    },
    dataBoundDV(args) {
      this.$refs.tvDonvi_Right.ej2Instances.grid.filterModule.filterOperators.startsWith =
        "contains";
    },
    // removeCssRule: function (file, rule) {
    //   var s = document.querySelector(`link[href = "${file}"]`).sheet;
    //   var i = [].slice
    //     .call(s.cssRules)
    //     .findIndex((v) => v.selectorText == rule);
    //   if (i > -1) s.deleteRule(i);
    // },
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    dateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    async load_CboDichVu() {
      try {
        this.$root.loading(true);
        let dsDV = await API.getDichVu(this.axios, {});
        this.cboDichVu = dsDV.data.data;
        if (this.cboDichVu.length)
          this.gParams.cboDichVu = this.cboDichVu[0].DICHVUVT_ID;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async cboDichVuChanged(value) {
      this.load_CboLoaiHinh();
    },
    async load_CboLoaiHinh() {
      try {
        this.$root.loading(true);
        let dsDV = await API.getLoaiHinhDV(this.axios, {
          dichVuId: this.gParams.cboDichVu,
        });
        this.cboLoaiHinh = dsDV.data.data;
        if (this.cboLoaiHinh.length)
          this.gParams.cboLoaiHinh = this.cboLoaiHinh[0].LOAITB_ID;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async cboLoaiHinhChanged(value) {
      this.isChangeCboLoaiHinh = true;
      await this.load_GridDinhMuc();
    },
    async load_GridDinhMuc() {
      try {
        this.$root.loading(true);
        let dsDV = await API.getDSDinhMuc(this.axios, {
          loaiTBId: this.gParams.cboLoaiHinh,
        });
        this.dtDinhMuc = dsDV.data.data;
        if (!this.dtDinhMuc.length) {
          this.dtVatTu = [];
          this.dtDonVi = [];
          this.tvVatTu_Right = [];
          this.tvDonvi_Right = [];
          this.gParams.txtTenDinhMuc = "";
          this.gParams.txtGhiChu = "";
          this.state_navigation(3);
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async load_GrvRight_Vattu() {
      try {
        this.$root.loading(true);
        let dsDV = await API.getTreeVTRight(this.axios, {
          dinhMucId: this.cell_dinhmucID,
          pageNo: this.value_GrvRight_Vattu.pageNo,
          pageSize: this.value_GrvRight_Vattu.pageSize,
          totalRow: 1,
        });
        
        if (dsDV.data.error_code == "BSS-00000000") {          
          this.tvVatTu_Right = dsDV.data.data
          this.value_GrvRight_Vattu.totalRow = parseInt(dsDV.data.data[0].TOTAL_ROWS);             
          console.log(this.tvVatTu_Right , " = tvVatTu_Right")
                   
        }
      } catch (error) {
        console.log(error);
      } finally {
        console.log("value_GrvRight_Vattu  = ", this.value_GrvRight_Vattu);
        this.$root.loading(false);
      }
    },
    async pageChanged_DSChuaGan(e) {
      console.log("Paging ", e);
      this.value_GrvRight_Vattu.pageNo = e.pageIndex + 1;
      this.value_GrvRight_Vattu.pageSize = e.pageSize;
      await this.load_GrvRight_Vattu();
    },
    gridDSChuaGan_selectedRowChanged(args) {
      console.log("gridDSChuaGan_selectedRowChanged = ", args);
    },
    async load_GrvLeft_Vattu() {
      try {
        this.$root.loading(true);
        let dsDV = await API.getDsVTLeft(this.axios, {
          dinhMucId: this.cell_dinhmucID,
        });
        this.dtVatTu = dsDV.data.data;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async load_GrvLeft_Donvi() {
      try {
        this.$root.loading(true);
        let dsDV = await API.getDsDonViLeft(this.axios, {
          dinhMucId: this.cell_dinhmucID,
        });
        this.dtDonVi = dsDV.data.data;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async load_GrvRight_Donvi() {
      try {
        this.$root.loading(true);
        let dsDV = await API.getDsDonViRight(this.axios, {
          dinhMucId: this.cell_dinhmucID,
        });
        this.tvDonvi_Right = dsDV.data.data;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    state_navigation(numb) {
      switch (numb) {
        case 0:
          this.enabled.tsbtnNew = true;
          this.enabled.tsbtnSave = true;
          this.enabled.tsbtnCancel = true;
          this.enabled.tsbtnDel = true;
          this.enabled.dtpDenNgay = true;
          break;
        case 1:
          this.enabled.tsbtnNew = false;
          this.enabled.tsbtnSave = true;
          this.enabled.tsbtnCancel = true;
          this.enabled.tsbtnDel = false;
          this.enabled.dtpDenNgay = false;
          break;
        case 2:
          this.enabled.tsbtnNew = true;
          this.enabled.tsbtnSave = true;
          this.enabled.tsbtnCancel = false;
          this.enabled.tsbtnDel = true;
          break;
        default:
          this.enabled.tsbtnNew = true;
          this.enabled.tsbtnSave = false;
          this.enabled.tsbtnCancel = false;
          this.enabled.tsbtnDel = false;
          break;
      }
    },
    async onDtDinhMucRowChanged(value) {
      if (this.isChangeCboLoaiHinh) {
        // tránh lặp 2 lần call API khi change loại hình
        this.isChangeCboLoaiHinh = false;
        return;
      }
      this.gParams.dtDinhMucRowChanged = value;
      if (value) {
        await this.load_GrvLeft_Vattu();
        await this.load_GrvRight_Vattu();
        await this.load_GrvLeft_Donvi();
        await this.load_GrvRight_Donvi();
        this.gParams.dtpDenNgay = this.stringToDate(this.cell_ngaylap);
        this.gParams.txtTenDinhMuc = this.cell_tendinhmuc;
        this.gParams.txtGhiChu = this.cell_ghichu;
        this.state_navigation(0);
      } else {
        this.dtVatTu = [];
        this.dtDonVi = [];
        this.tvVatTu_Right = [];
        this.tvDonvi_Right = [];
        this.gParams.txtTenDinhMuc = "";
        this.gParams.txtGhiChu = "";
        this.state_navigation(3);
      }
    },
    onDtVatTuItemsChanged(values) {},
    onDtVatTuRowChanged(value) {},
    onDtDonViItemsChanged(values) {},
    onDtDonViRowChanged(value) {},
    test() {
      let a = this.$refs.tvVatTu_Right.getSelectedRecords();
      console.log(a);
    },
    async grvVatTu_IsCheck_CellValueChanged(arg){      
      try{        
        if (arg.columnName == "DINHMUC" && arg.value > 0) {
          this.loading(true)
          let dsDV = await API.capNhatDinhMuc(this.axios, {
            dinhMuc: arg.value,
            ctmdId: arg.rowData.CTDM_ID,
            Id: arg.rowData.ID
          });
          await this.load_GrvLeft_Vattu();
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnVatTu_Left() {
      try {
        this.$root.loading(true);
        if (!this.tvVatTu_Right.length) {
          return this.$toast.error("Không có dữ liệu trên lưới !");
        }
        let ds = this.$refs.tvVatTu_Right.getSelectedRecords();
        if (ds.length) {
          let dsDMVT = [];
          ds.forEach((e) => {
            dsDMVT.push({
              VATTU_ID: e.VATTU_ID,
              DINHMUC: e.DINHMUC,
            });
          });
          const response = await API.putGanDinhMuc(this.axios, {
            dinhMucId: this.cell_dinhmucID,
            ds: dsDMVT,
          });
          if (response.data.data[0].CODE == "1") {
            this.$toast.success("Gán thành công");
            await this.load_GrvLeft_Vattu();
            await this.load_GrvRight_Vattu();
          } else {
            this.$toast.error("Gán vật tư không thành công!");
          }
        } else {
          this.$toast.error("Không có danh sách không thể gán");
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async btnVatTu_Right() {
      if (this.dtVatTu.length) {
        let dsVT = this.$refs.gridVatTu.getSelectedRecords();
        if (!dsVT.length) {
          return this.$toast.error("Bạn chưa chọn vật tư để thực hiện");
        }
        let dsVTDM = [];
        dsVT.forEach((e) => {
          dsVTDM.push(e.CTDM_ID);
        });
        await API.putGoDinhMuc(this.axios, {
          ds: dsVTDM,
        });
        this.$toast.success("Gỡ thành công!");
        await this.load_GrvLeft_Vattu();
        await this.load_GrvRight_Vattu();
      } else {
        this.$toast.error("Không có danh sách không thể gỡ");
      }
    },
    list_to_tree(list) {
      var list_origin = [];
      for (const i in list) {
        var VATTU_CHA_ID = list[i].VATTU_CHA_ID;
        var listNodeParent = list.filter(function (el) {
          return el.VATTU_ID == VATTU_CHA_ID;
        });
        if (listNodeParent.length == 0) {
          list_origin.push(list[i].VATTU_CHA_ID);
        }
      }
      var map = {},
        node,
        roots = [],
        i;
      for (i = 0; i < list.length; i += 1) {
        map[list[i].VATTU_ID] = i; // initialize the map
        list[i].children = []; // initialize the children
      }
      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (!list_origin.includes(node.VATTU_CHA_ID)) {
          list[map[node.VATTU_CHA_ID]].children.push(node);
        } else {
          roots.push(node);
        }
      }
      roots.forEach((item, index) => {
        item.STT = index;
      });
      return roots;
    },
    list_to_tree_dv(list) {
      var list_origin = [];
      for (const i in list) {
        var DONVI_CHA_ID = list[i].DONVI_CHA_ID;
        var listNodeParent = list.filter(function (el) {
          return el.DONVI_ID == DONVI_CHA_ID;
        });
        if (listNodeParent.length == 0) {
          list_origin.push(list[i].DONVI_CHA_ID);
        }
      }
      var map = {},
        node,
        roots = [],
        i;
      for (i = 0; i < list.length; i += 1) {
        map[list[i].DONVI_ID] = i; // initialize the map
        list[i].children = []; // initialize the children
      }
      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (!list_origin.includes(node.DONVI_CHA_ID)) {
          list[map[node.DONVI_CHA_ID]].children.push(node);
        } else {
          roots.push(node);
        }
      }
      roots.forEach((item, index) => {
        item.STT = index;
      });
      return roots;
    },
    listToTree(data, options) {
      options = options || {};
      var ID_KEY = options.idKey || "VATTU_ID";
      var PARENT_KEY = options.parentKey || "VATTU_CHA_ID";
      var CHILDREN_KEY = options.childrenKey || "children";

      var tree = [],
        childrenOf = {};
      var item, id, parentId;

      for (var i = 0, length = data.length; i < length; i++) {
        item = data[i];
        id = item[ID_KEY];
        parentId = item[PARENT_KEY] || 0;
        // every item may have children
        childrenOf[id] = childrenOf[id] || [];
        // init its children
        item[CHILDREN_KEY] = childrenOf[id];
        if (parentId != 0) {
          // init its parent's children object
          childrenOf[parentId] = childrenOf[parentId] || [];
          // push it into its parent's children object
          childrenOf[parentId].push(item);
        } else {
          tree.push(item);
        }
      }
      console.log(JSON.stringify(tree));
      return tree;
    },
    async btnDonVi_Left() {
      try {
        this.$root.loading(true);
        if (!this.tvDonvi_Right.length) {
          return this.$toast.error("Không có dữ liệu trên lưới !");
        }
        let ds = this.$refs.tvDonvi_Right.getSelectedRecords();
        if (ds.length) {
          let dsDV = [];
          ds.forEach((e) => {
            dsDV.push({
              DONVI_ID: e.DONVI_ID,
              DINHMUC: e.DINHMUC,
            });
          });
          let response = await API.putGanDinhMuc(this.axios, {
            dinhMucId: this.cell_dinhmucID,
            ds: dsDV,
          });
          console.log("response = ", response);
          if (response.data.data[0].CODE == "1") {
            this.$toast.success("Gán thành công");
            await this.load_GrvLeft_Donvi();
            await this.load_GrvRight_Donvi();
          } else {
            this.$toast.error("Gán đơn vị không thành công!");
          }
        } else {
          this.$toast.error("Không có danh sách không thể gán");
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.loading(false);
      }
    },
    async btnDonVi_Right() {
      if (this.dtDonVi.length) {
        let dsVT = this.$refs.gridDonVi.getSelectedRecords();
        if (!dsVT.length) {
          return this.$toast.error("Bạn chưa chọn đơn vị để thực hiện");
        }
        let dsVTDM = [];
        dsVT.forEach((e) => {
          dsVTDM.push(e.CTDM_ID);
        });
        let dsDV = await API.putGoDinhMuc(this.axios, {
          ds: dsVTDM,
        });
        this.$toast.success("Gỡ thành công!");
        await this.load_GrvLeft_Donvi();
        await this.load_GrvRight_Donvi();
      } else {
        this.$toast.error("Không có danh sách không thể gỡ");
      }
    },    
    onRowSelectingTreeGrid(args) {},
    async tsbtnLayTT() {
      await this.load_CboDichVu();
      await this.load_CboLoaiHinh();
      await this.load_GridDinhMuc();
    },
    tsbtnNew() {
      this.state_navigation(1);
      this.enabled.dtpDenNgay = false;
      this.gParams.txtTenDinhMuc = "";
      this.gParams.txtGhiChu = "";
    },
    async tsbtnXacNhan() {
      try {
        this.$root.loading(true);
        if (this.gParams.txtTenDinhMuc) {
          if (this.state == false) {
            let dsDV = await API.postThemDinhMuc(this.axios, {
              ten: this.gParams.txtTenDinhMuc,
              loaiTB: this.gParams.cboLoaiHinh,
              ghiChu: this.gParams.txtGhiChu,
            });
            this.$toast.success("Thêm mới định mức thành công!");
            await this.load_GridDinhMuc();
            await this.state_navigation(0);
            this.enabled.dtpDenNgay = true;
          } else {
            let dsDV = await API.putThemDinhMuc(this.axios, {
              ten: this.gParams.txtTenDinhMuc,
              dinhMucId: this.cell_dinhmucID,
              ghiChu: this.gParams.txtGhiChu,
            });
            this.$toast.success("Sửa thành công!");
            await this.load_GridDinhMuc();
            await this.state_navigation(0);
            this.enabled.dtpDenNgay = true;
          }
        } else {
          this.$refs.refTxtTenDinhMuc.focus();
          return this.$toast.error("Bạn phải nhập tên định mức!");
        }
      } catch (error) {
        log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
    tsbtnCancel() {
      this.gParams.txtTenDinhMuc = "";
      this.gParams.txtGhiChu = "";
      this.enabled.dtpDenNgay = true;
      this.state_navigation(3);
    },
    async tsbtnDel() {
      try {
        if (this.cell_dinhmucID) {
          this.$bvModal.msgBoxConfirm(
            `Bạn có muốn xóa định mức này?`,
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          ).then(async v => {
            if (v) {
                this.$root.loading(true);
                let dsDV = await API.deleteDinhMuc(this.axios, {
                dinhMucId: this.cell_dinhmucID,
                });
                this.$toast.success("Xóa định mức thành công!");
                await this.load_GridDinhMuc();
                this.state_navigation(0);
                this.enabled.dtpDenNgay = true;
            }
          });
        } else {
          this.$toast.error("Vui lòng thực hiện đúng bước!");
        }
      } catch (error) {
        console.log(error);
        this.$toast.error("Không thể xóa! " + error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
  },
  async mounted() {
    await this.load_CboDichVu();
  },
  created: function () {
    // Fix css breaking layout
    // this.removeCssRule("/static/css/custom.css", ".row");
    // this.removeCssRule("/static/css/custom.css", ".custom-select");
    // this.removeCssRule("/static/css/style.css", ".input-group");
    // this.removeCssRule("/static/css/style.css", ".form-control");
  },
};
</script>

<style src="./DinhMucVatTu.scss">
</style>
