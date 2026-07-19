<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import api from "./api";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "PopupChonNVSMS",
  props: {
    isPopup: {
      type: Boolean,
      default: false,
    },
    inputData: {
      type: Object,
      default: () => {
        return { khieunai_id: 1000, ma_tb: "lam_test" };
      },
    },
  },
  mounted() {
    this.loadData();
  },
  data() {
    return {
      header: {
        title: "Chọn nhân viên nhắn tin SMS",
        list: [],
      },
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
      },
      TableDsNhanVien: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "stt",
            headerText: "STT",
            allowFiltering: true,
            allowSorting: false,
            width: 80,
          },
          {
            fieldName: "ma_nv",
            headerText: "Mã NV",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_nv",
            headerText: "Tên NV",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "chucdanh",
            headerText: "Chức danh",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },
    };
  },
  watch: {
    "DonViXuly.selected": {
      handler: function (val, oldval) {
        this.getDataTableDsNhanVien();
      },
      deep: true,
    },
  },
  computed: {},
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
    async loadData() {
      await this.getDonViTreeData();
    },
    async getDataTableDsNhanVien() {
      this.TableDsNhanVien.selected = null;
      if (this.DonViXuly.selected) {
        try {
          this.loading(true);
          this.TableDsNhanVien.data = this.GetData(
            await api.lay_nv_giao_phkh_v2(this.axios, {
              vdonvi_id: this.DonViXuly.selected,
              vloaidv_nhan_id: 0,
              vhuonggiao_id: 0,
            })
          );
        } catch (err) {
          this.TableDsNhanVien.data = [];
          console.error(err);
        } finally {
          this.loading(false);
        }
      } else {
        this.TableDsNhanVien.data = [];
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
        this.loading(true);
        const donViId = this.$root.token.getDonViID();
        const dsDv = this.GetData(
          await api.sp_chonnv_sms_treeview(this.axios, {
            nguoidung_id: this.$root.token.getNguoiDungID(),
            donvi_id: donViId,
          })
        );

        this.DonViXuly.data = this.createTreeDonVi(dsDv.returnds, [donViId.toString()]);
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
        this.loading(false);
      }
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
        const treeObj = document.getElementById("treeviewChonNVSMS").ej2_instances[0];
        if (treeObj) {
          this.DonViXuly.selected = treeObj.selectedNodes.join(",");
        }
      }
    },
    DonViXulyNodeChecked() {
      if (this.DonViXuly.checkbox) {
        const treeObj = document.getElementById("treeviewChonNVSMS").ej2_instances[0];
        if (treeObj) {
          this.DonViXuly.selected = treeObj.checkedNodes.join(",");
        }
      }
    },
    DonViXulyNodeClicked() {},
    selectedItemsChangedTableDsNhanVien(items) {
      this.TableDsNhanVien.selected = items;
    },
    async NhanTinBnt() {
      // if (!(this.inputData && this.inputData.nd_khieunai && this.inputData.nd_khieunai.toString().length > 0)) {
      //   this.$root.$toast.warning('Bạn chưa nhập nội dung tin nhắn')
      //   return
      // }
      if (!(this.TableDsNhanVien.selected && this.TableDsNhanVien.selected.length > 0)) {
        this.$root.$toast.warning("Bạn chọn nhân viên nhận tin nhắn");
        return;
      }
      try {
        this.loading(true);
        const dsNhan = this.TableDsNhanVien.selected.map((item) => {
          return {
            khieunai_id: this.inputData.khieunai_id,
            ma_tb: this.inputData.ma_tb,
            noidung: "", //this.inputData.nd_khieunai,
            so_dt: item.so_dt,
          };
        });
        const response = await api.sp_chonnv_sms_nhantin(this.axios, {
          p_json_dsnhan: JSON.stringify(dsNhan),
          vnguoi_cn: this.$root.token.getUserName(),
          vmay_cn: await this.$root.token.getMachine(),
          vip_cn: await this.$root.token.getIP(),
        });
        if (
          (response.data.error === 200 || response.data.error === "200") &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data === "OK"
        ) {
          this.$root.$toast.success("Gửi tin nhắn thành công");
          this.$emit("onSuccess");
          // this.getDSKhieuNai()
        } else {
          this.$root.$toast.error("Nhắn tin không thành công");
        }
      } catch (e) {
        console.log(e);
        this.$root.$toast.error("Nhắn tin không thành công");
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
