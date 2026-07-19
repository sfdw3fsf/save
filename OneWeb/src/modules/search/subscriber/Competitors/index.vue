<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
import dummy from "./dummy";
import { DataManager, Predicate, Query } from "@syncfusion/ej2-data";
import PopupLichSuThayDoiDuLieu from "./popups/PopupLichSuThayDoiDuLieu";
export default {
  components: { breadcrumb, PopupLichSuThayDoiDuLieu },
  name: "Competitors",
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
    enableLichSuBnt() {
      return (
        this.DanhSachKhuVuc.selected != null && this.gridboxChiTietKhuVuc.selected != null
      );
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
    "DanhSachKhuVuc.selected": {
      handler: function (val, oldval) {
        if (val) {
          this.get_ds_chitietkhuvuc();
        } else {
          this.SetButton(0);
        }
      },
      deep: true,
    },
    "gridboxChiTietKhuVuc.selected": {
      handler: function (val, oldval) {
        if (val) {
          this.CNTTTK.NoiDung = val.noidung;
          this.CNTTTK.DeXuatGiaiPhap = val.giaiphap;
          const trangThaiItem = this.CNTTTK.TrangThai.data.find(
            (x) => x.ma_tt == val.trang_thai
          );
          const doiThuItem = this.CNTTTK.DoiThu.data.find(
            (x) => x.chuquan_id == val.chuquan_id
          );
          this.CNTTTK.TrangThai.selected = trangThaiItem ? trangThaiItem.ma_tt : null;
          this.CNTTTK.DoiThu.selected = doiThuItem ? doiThuItem.chuquan_id : null;
          this.SetButton(3);
        } else {
          this.$refs.gridboxChiTietKhuVuc.grid.clearSelection();
        }
      },
      deep: true,
    },
    bntMode: function (val, oldVal) {
      this.enableNhapMoiBnt = false;
      this.enableGhiLaiBnt = false;
      this.enableXoaBnt = false;
      this.enableHuyBnt = false;
      if (val === -1) {
        //Bat dau
        this.enableGhiLaiBnt = true;
        this.enableHuyBnt = true;
      }

      if (val === 0) {
        //Bat dau
        this.enableNhapMoiBnt = true;
        this.clearData();
      }

      if (val === 1) {
        //Them moi
        this.enableGhiLaiBnt = true;
        this.enableHuyBnt = true;
        this.vmode = "A";
        this.clearData();
      }

      if (val === 2) {
        //Huy
        if (oldVal === 1) {
          this.bntMode = 1;
        } else if (oldVal === 3) {
          this.gridboxChiTietKhuVuc.selected = JSON.parse(
            JSON.stringify(this.gridboxChiTietKhuVuc.selected)
          );
          this.bntMode = 3;
        }
      }

      if (val === 3) {
        //Edit
        this.enableNhapMoiBnt = true;
        this.enableXoaBnt = true;
        this.enableGhiLaiBnt = true;
        this.enableHuyBnt = true;
        this.vmode = "U";
      }
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "Thông tin chi tiết địa bàn",
        list: [],
      },
      vmode: "",
      enableNhapMoiBnt: false,
      enableGhiLaiBnt: false,
      enableHuyBnt: false,
      enableXoaBnt: false,
      bntMode: -1,
      DanhSachKhuVuc: {
        selected: null,
        checkbox: false,
        search: "",
        data: [],
        fields: {
          dataSource: [],
          id: "khuvuc_id",
          text: "ten_kv",
          parentID: "khuvuc_cha_id",
          hasChildren: "hasChild",
        },
        // fields: { dataSource: dummy.dataSource, id: 'id', parentID: 'pid', text: 'name', hasChildren: 'hasChild', selected: 'isSelected' }
      },
      CNTTTK: {
        DoiThu: { data: [], selected: null },
        TrangThai: { data: [], selected: null },
        NoiDung: "",
        DeXuatGiaiPhap: "",
      },
      gridboxChiTietKhuVuc: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "tenchuquan",
            headerText: "Đối thủ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_trang_thai",
            headerText: "Trạng thái",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "noidung",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "giaiphap",
            headerText: "Giải pháp",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_kv",
            headerText: "Khu vực",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },
      openedPopup: false,
    };
  },
  methods: {
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
    async loadData() {
      await this.getDonViTreeData();
      await this.get_CB_CNTTTK_TrangThai();
      await this.get_CB_CNTTTK_DoiThu();
    },
    async get_CB_CNTTTK_TrangThai() {
      this.CNTTTK.TrangThai.selected = null;
      try {
        this.loading = true;
        this.CNTTTK.TrangThai.data = this.GetData(
          await api.sp_lay_trangthai_doithu_canhtranh(this.axios, {})
        );
        if (this.CNTTTK.TrangThai.data.length > 0) {
          this.CNTTTK.TrangThai.selected = this.CNTTTK.TrangThai.data[0].ma_tt;
        }
      } catch (err) {
        this.CNTTTK.TrangThai.data = [];
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async get_CB_CNTTTK_DoiThu() {
      this.CNTTTK.DoiThu.selected = null;
      try {
        this.loading = true;
        this.CNTTTK.DoiThu.data = this.GetData(
          await api.sp_lay_ds_doithu_canhtranh(this.axios, {})
        );
        if (this.CNTTTK.DoiThu.data.length > 0) {
          this.CNTTTK.DoiThu.selected = this.CNTTTK.DoiThu.data[0].chuquan_id;
        }
      } catch (err) {
        this.CNTTTK.DoiThu.data = [];
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async getDonViTreeData() {
      this.DanhSachKhuVuc.selected = null;
      try {
        this.loading = true;
        const adminData = this.GetData(
          await api.sp_ds_nguoidung_quantri(this.axios, {
            nguoidung_id: this.$root.token.getNguoiDungID(),
          })
        );
        let dsDv = [];
        if (adminData.length > 0) {
          //is Admin
          dsDv = this.GetData(await api.sp_lay_ds_khuvuc_treeview(this.axios, {}));
        } else {
          dsDv = this.GetData(
            await api.sp_lay_ds_khuvuc_nhanvien_id_treeview(this.axios, {
              nhanvien_id: this.$root.token.getNhanVienID(),
            })
          );
        }

        this.DanhSachKhuVuc.data = this.createTreeDonVi(dsDv);
        this.DanhSachKhuVuc.data.sort((a, b) => {
          return a.ten_kv.localeCompare(b.ten_kv);
        });
        this.DanhSachKhuVucChangedDataSource(this.DanhSachKhuVuc.data);
        setTimeout(() => {
          this.$refs.tree.expandAll();
        }, 100);
      } catch (err) {
        this.DanhSachKhuVucChangedDataSource([]);
        this.DanhSachKhuVuc.data = [];
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    createTreeDonVi(dsDonVi) {
      let tree = [];
      let donviChaArray = dsDonVi.map((item) => {
        return item["khuvuc_cha_id"];
      });
      let donviArray = dsDonVi.map((item) => item["khuvuc_id"]);
      dsDonVi.forEach((item) => {
        if (donviChaArray.includes(item["khuvuc_id"])) {
          if (!donviArray.includes(item["khuvuc_cha_id"])) {
            delete item.khuvuc_cha_id;
          }
          tree.push({ ...item, hasChild: true, expanded: false });
        } else {
          if (item.unitlevel == 1) delete item.khuvuc_cha_id;
          tree.push(item);
        }
      });
      return tree;
    },
    async searchDonVi() {
      let predicate;
      let predicats = [];
      let _array = [];
      let _filter = [];
      if (this.DanhSachKhuVuc.search.trim() !== "") {
        predicate = new Predicate(
          "ten_kv",
          "contains",
          this.DanhSachKhuVuc.search.trim(),
          true
        );
        let filteredList = new DataManager(this.DanhSachKhuVuc.data).executeLocal(
          new Query().where(predicate)
        );
        for (let j = 0; j < filteredList.length; j++) {
          _filter.push(filteredList[j]["khuvuc_id"]);
          let filters = this.getFilterItems(filteredList[j], this.DanhSachKhuVuc.data);
          for (let i = 0; i < filters.length; i++) {
            if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
              _array.push(filters[i]);
              predicats.push(new Predicate("khuvuc_id", "equal", filters[i], false));
            }
          }
        }
        if (predicats.length === 0) {
          this.DanhSachKhuVucChangedDataSource([]);
        } else {
          let query = new Query().where(Predicate.or(predicats));
          let newList = new DataManager(this.DanhSachKhuVuc.data).executeLocal(query);
          this.DanhSachKhuVucChangedDataSource(newList);
        }
      } else {
        this.DanhSachKhuVucChangedDataSource(this.DanhSachKhuVuc.data);
      }
      setTimeout(() => {
        this.$refs.tree.expandAll();
      }, 100);
    },
    getFilterItems(fList, list) {
      let nodes = [];
      nodes.push(fList["khuvuc_id"]);
      let query2 = new Query().where("khuvuc_id", "equal", fList["khuvuc_cha_id"], false);
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
    DanhSachKhuVucSortTree() {
      const data = this.DanhSachKhuVuc.data.sort((a, b) =>
        a.ten_kv < b.ten_kv ? -1 : 1
      );
      this.DanhSachKhuVucChangedDataSource(data);
      this.$refs.tree.dataBind();
    },
    DanhSachKhuVucChangedDataSource(data) {
      this.DanhSachKhuVuc.fields = {
        dataSource: data,
        id: "khuvuc_id",
        text: "ten_kv",
        parentID: "khuvuc_cha_id",
        hasChildren: "hasChild",
      };
    },
    DanhSachKhuVucExpandedNodes() {},
    DanhSachKhuVucNodeSelected() {
      if (!this.DanhSachKhuVuc.checkbox) {
        const treeObj = document.getElementById("treeview").ej2_instances[0];
        if (treeObj) {
          this.DanhSachKhuVuc.selected = treeObj.selectedNodes.join(",");
        }
      }
    },
    DanhSachKhuVucNodeChecked() {
      if (!this.DanhSachKhuVuc.checkbox) {
        const treeObj = document.getElementById("treeview").ej2_instances[0];
        if (treeObj) {
          this.DanhSachKhuVuc.selected = treeObj.checkedNodes.join(",");
        }
      }
    },
    DanhSachKhuVucNodeClicked() {},
    selectedItemsChangedGridboxChiTietKhuVuc(items) {
      if (items.length > 0) {
        this.gridboxChiTietKhuVuc.selected = items[0];
      }
    },
    async get_ds_chitietkhuvuc() {
      if (this.DanhSachKhuVuc.selected) {
        this.gridboxChiTietKhuVuc.selected = null;
        try {
          this.loading = true;
          const params = {
            khuvuc_id: this.DanhSachKhuVuc.selected,
          };
          this.gridboxChiTietKhuVuc.data = this.GetData(
            await api.sp_lay_ds_chitiet_doithu_canhtranh(this.axios, params)
          );
          if (this.gridboxChiTietKhuVuc.data.length > 0) {
            this.SetButton(3);
          } else {
            this.SetButton(1);
          }
        } catch (err) {
          this.gridboxChiTietKhuVuc.data = [];
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    async capnhat_ct_khuvuc() {
      if (
        this.DanhSachKhuVuc.selected != null &&
        this.CNTTTK.DoiThu.selected != null &&
        this.CNTTTK.NoiDung.length > 0
      ) {
        try {
          this.loading = true;
          const params = {
            vchitietkv_id: this.gridboxChiTietKhuVuc.selected
              ? this.gridboxChiTietKhuVuc.selected.chitietkv_id
              : 0,
            vkhuvuc_id: this.DanhSachKhuVuc.selected,
            vchuquan_id: this.CNTTTK.DoiThu.selected,
            vnoidung: this.CNTTTK.NoiDung,
            vgiaiphap: this.CNTTTK.DeXuatGiaiPhap,
            vtrang_thai: this.CNTTTK.TrangThai.selected,
            vngay_cn: moment().format("DD/MM/YYYY HH:mm:ss"),
            vmay_cn: await this.$root.token.getMachine(),
            vnguoi_cn: this.$root.token.getUserName(),
            vip_cn: await this.$root.token.getIP(),
            vnguoidung_id: this.$root.token.getNguoiDungID(),
            vmode: this.vmode,
          };
          const response = await api.capnhat_ct_khuvuc(this.axios, params);
          console.log(response);
          if (
            (response.data.error === 200 || response.data.error === "200") &&
            response.data.error_code === "BSS-00000000" &&
            response.data.data === "1"
          ) {
            this.$root.$toast.success("Cập nhật dữ liệu thành công");
            this.get_ds_chitietkhuvuc();
          } else {
            this.$root.$toast.error(response.data.data);
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      } else {
        this.$root.$toast.error("Cần nhập thông tin cập nhật tiến trình triển khai");
        this.$refs.CNTTTKNoiDung.focus();
      }
    },
    async xoa_ct_khuvuc() {
      try {
        this.loading = true;
        const params = {
          vchitietkv_id: this.gridboxChiTietKhuVuc.selected.chitietkv_id,
          vkhuvuc_id: this.DanhSachKhuVuc.selected,
          vchuquan_id: this.CNTTTK.DoiThu.selected,
          vnoidung: "",
          vgiaiphap: "",
          vtrang_thai: "",
          vngay_cn: moment().format("DD/MM/YYYY HH:mm:ss"),
          vmay_cn: await this.$root.token.getMachine(),
          vnguoi_cn: this.$root.token.getUserName(),
          vip_cn: await this.$root.token.getIP(),
          vnguoidung_id: this.$root.token.getNguoiDungID(),
          vmode: "D",
        };
        const response = await api.capnhat_ct_khuvuc(this.axios, params);
        console.log(response);
        if (
          (response.data.error === 200 || response.data.error === "200") &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data === "1"
        ) {
          this.$root.$toast.success("Xóa dữ liệu thành công");
          this.get_ds_chitietkhuvuc();
        } else {
          this.$root.$toast.info(response.data.data);
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    openPopUpLichSu() {
      this.$refs.modalLichSuThayDoiDuLieu.showModal(
        this.DanhSachKhuVuc.selected,
        this.gridboxChiTietKhuVuc.selected.chuquan_id
      );
    },
    SetButton(mode) {
      this.bntMode = mode;
    },
    NhapMoiBnt() {
      this.clearData();
      this.SetButton(1);
    },
    GhiLaiBnt() {
      this.capnhat_ct_khuvuc();
    },
    HuyBnt() {
      this.SetButton(2);
    },
    XoaBnt() {
      if (
        this.gridboxChiTietKhuVuc.selected &&
        this.DanhSachKhuVuc.selected != null &&
        this.CNTTTK.DoiThu.selected != null
      ) {
        this.$bvModal
          .msgBoxConfirm(
            `Bạn thật sự muốn xóa dữ liệu ${this.gridboxChiTietKhuVuc.selected.tenchuquan} không?`,
            {
              title: "Thông báo",
              size: "sm",
              centered: true,
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (yes) => {
            if (yes) {
              this.xoa_ct_khuvuc();
            }
          });
      } else {
        this.$root.$toast.error("Bạn chưa chọn dữ liệu để xóa");
      }
    },
    LichSuBnt() {
      this.openPopUpLichSu();
    },
    clearData() {
      this.gridboxChiTietKhuVuc.selected = null;

      this.CNTTTK.NoiDung = "";
      this.CNTTTK.DeXuatGiaiPhap = "";
    },
  },
};
</script>
