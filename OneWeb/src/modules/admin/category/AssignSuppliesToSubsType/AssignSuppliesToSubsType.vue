<template src="./AssignSuppliesToSubsType.html"></template>
<style scoped src="./AssignSuppliesToSubsType.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
import CustomDataGrid from "./CustomDataGrid/index.vue";
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data'
Vue.use(TreeViewPlugin);
Vue.use(DropDownListPlugin);

export default {
  components: { breadcrumb, CustomDataGrid,DataManager, Query, ODataAdaptor, Predicate,},
  data: function () {
    return {
       header: {
        title: "",
      },
       list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'}
          }
        ],
      params: {
        p_dsdichvu_options: [],
        p_dsdichvu: null,
        p_loaihinh_options: [],
        p_loaihinh: null,
        tenVatu: null,
        chk_nhanhVTDaGan: false,
        chkedVTDaGan: false,
        chk_nhanhVTChuaGan: false,
        chkedVTChuaGan: false,
      },
      columnsDaGan: [
        {
          field: "ten_vt",
          label: "Tên vật tư",
          allowFilter: true,
        },
      ],
      ds_vattudagan_table: [],
      ds_vattudagan:[],
      //allCheckedDaGan: false,
      chkAllDagan: false,
      listVatTuDaGanChk: [],
      listVatTuChuaGanChk: [],
      checkedNodes: null,
      loadding_dsdagan: 0,
      loadding_dschuagan: 0,
      filterTenVT: "",
      ds_vattuchuagan: [],
      vatTuTree: [],
      selectedNodes: [],
      expandedNodes:[-1],
      danhsachVatTuTree:[],
      VtuWord: "",
      VtuIndex: 0,
      VtuCache: [],
      VtuChaCache:[],
      fields: {
        dataSource: null,
        id: "vattu_id",
        parentID: "vattu_cha_id",
        text: "ten_vt",
        hasChildren: "hasChild",
      },
      selection: [],
      items: [
        {
          id: 1,
          name: "Root",
          children: [
            { id: 2, name: "Child #1" },
            { id: 3, name: "Child #2" },
            {
              id: 4,
              name: "Child #3",
              children: [
                { id: 5, name: "Grandchild #1" },
                { id: 6, name: "Grandchild #2" },
              ],
            },
          ],
        },
      ],
      getHeaderChon() {
        return function () {
          return {
            template: {
              template: `<label>Chọn</label>`,
            },
          };
        };
      },
    };
  },
  watch: {},
  computed: {},
  created() {
    this.loadDSDichVu();
  },
  methods: {
    async loadDSDichVu() {
      this.loading(true);
      API.getDSDichVu(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({
                  id: item.DICHVUVT_ID,
                  text: item.TEN_DVVT,
                });
              });
              this.params.p_dsdichvu_options = items.sort(function (a, b) {
                return a.id - b.id;
              });
              this.params.p_dsdichvu = items[0].id;
              this.loadLoaiHinh(items[0].id);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadLoaiHinh: function (dvvtid) {
      //reset combobox loai hinh
      this.loading(true);
      this.params.p_loaihinh_options = [];
      this.params.p_loaihinh = null;
      API.getLoaiHinhTB(this.axios, dvvtid)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.ID, text: item.NAME });
            });
            this.params.p_loaihinh_options = items;
            this.params.p_loaihinh = items[0].id;
            this.getDSVatTuChuaGan(items[0].id);
            this.getDSVatTuDaGan(items[0].id);
          } else {
            this.loading(false);
            this.loadding_dschuagan = 0;
            this.loadding_dsdagan = 0;
            this.ds_vattudagan_table = [];
            this.fields = {
              dataSource: null,
              id: "vattu_id",
              parentID: "vattu_cha_id",
              text: "ten_vt",
              hasChildren: "hasChild",
            };
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async getDSVatTuChuaGan(loaihinhtbid) {
      this.loading(true);
      this.loadding_dschuagan = 0;
      this.danhsachVatTuTree = [];
      this.fields = {
        dataSource: null,
        id: "vattu_id",
        parentID: "vattu_cha_id",
        text: "ten_vt",
        hasChildren: "hasChild",
      };
      let apiParams = {
        kieu: 0, //0 chưa gán, 1 đã gán
        loaitb_id: loaihinhtbid,
      };
      await API.getDSVTTheoLHTB(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.ds_vattuchuagan = response.data.data;
            this.vatTuTree = this.createTreeVatTu(response.data.data);
            this.danhsachVatTuTree = this.vatTuTree
            this.fields = {
              dataSource: this.vatTuTree,
              id: "vattu_id",
              parentID: "vattu_cha_id",
              text: "ten_vt",
              hasChildren: "hasChild",
            };
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loadding_dschuagan = 1;
          if (this.loadding_dschuagan + this.loadding_dsdagan == 2) {
            this.loading(false);
          }
        });
    },
    createTreeVatTu(dsVatTu) {
      let tree = [];
      let vattuChaArray = dsVatTu.map((item) => item["vattu_cha_id"]);
      let vattuArray = dsVatTu.map((item) => item["vattu_id"]);
      dsVatTu.forEach((item) => {
        //check item la parent node
        if (vattuChaArray.includes(item["vattu_id"])) {
          //check vattu_cha_id khong co trong ds vattu_id thi xoa vattu_cha_id
          //có một số vatu_cha_id =null cho hiện cùng cấp với root
          /*  if (!vattuArray.includes(item["vattu_cha_id"])) {
            delete item.vattu_cha_id;
          } */
          tree.push({ ...item, hasChild: true, expanded: false });
        } else {
          tree.push(item);
        }
      });
      return tree;
    },
    async getDSVatTuDaGan(loaitbid) {
      this.loading(true);
      this.loadding_dsdagan = 0;
      this.ds_vattudagan_table = [];
      this.ds_vattudagan = [];
      try {
        let apiParams = {
          // 0 chưa gán, 1 đã gán
          kieu: 1,
          loaitb_id: loaitbid,
        };
        const response = await API.getDSVTTheoLHTB(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          let items = [];
          response.data.data.forEach(function (item) {
            items.push({
              chon: 0,
              ten_vt: item.ten_vt,
              vattu_id: item.vattu_id,
            });
          });
          this.ds_vattudagan = items;
          this.ds_vattudagan_table = items;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loadding_dsdagan = 1;

        if (this.loadding_dschuagan + this.loadding_dsdagan == 2) {
          this.loading(false);
        }
      }
    },
    changeDataSource (data) {
      this.fields = {
        dataSource: data, id: 'vattu_id', text: 'ten_vt', parentID: 'vattu_cha_id', hasChildren: 'hasChild'
      }
    },
    async ganGoVatTuLHTB(kieu, loaihinhtb, dsvt) {
      this.loading(true);
      try {
        let apiParams = {
          kieu: kieu, // 1 gán, 0 gỡ
          loaitb_id: loaihinhtb,
          vattu_ids: dsvt,
        };
        const response = await API.ganGoVTLHTB(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (kieu == 1) {
            this.$toast.success("Gán thành công");
            this.chkAllDagan= false;
          } else {
            this.$toast.success("Gỡ thành công");
            this.chkAllDagan= false;
          }
          this.getDSVatTuChuaGan(this.params.p_loaihinh);
          this.getDSVatTuDaGan(this.params.p_loaihinh);
          this.listVatTuChuaGanChk = [];
          this.listVatTuDaGanChk = [];
        } else {
          this.$toast.error("Có lỗi trong quá trình thực hiện");
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    onDichVuChange: function (agrs) {
      this.loadLoaiHinh(agrs.id);
    },
    onLoaiHinhChange: function (agrs) {
      this.getDSVatTuChuaGan(agrs.id);
      this.getDSVatTuDaGan(agrs.id);
    },
    nodeChecked: function (args) {
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      let getNodeDetails = treeObj.getNode(args.node);
      if (getNodeDetails.parentID != null) {
        this.checkNodeVTChuaGan(args);
      } else {
        this.loading(true);
        setTimeout(
          function () {
            this.checkNodeVTChuaGan(args);
          }.bind(this),
          500
        );
      }
    },
    checkNodeVTChuaGan(args) {
      var treeObj = document.getElementById("treeview").ej2_instances[0];
      var checkedNode = [args.node];
      if (
        args.event.target.classList.contains("e-fullrow") ||
        args.event.key == "Enter"
      ) {
        var getNodeDetails = treeObj.getNode(args.node);
        if (getNodeDetails.isChecked == "true") {
          treeObj.uncheckAll(checkedNode);
        } else {
          treeObj.checkAll(checkedNode);
        }
      }
      this.listVatTuChuaGanChk = treeObj.checkedNodes.toString();
      this.loading(false);
    },
    onDataCheckedVatTu: function (dataKeys) {
      for (let i = 0; i < dataKeys.length; i++) {
        if (dataKeys[i].chon != "s1") {
          this.onVattuCheckedChange(dataKeys[i].vattu_id);
        }
      }
    },
    onVattuCheckedChange(vattu_id) {
      for (let r of this.ds_vattudagan_table) {
        if (r["vattu_id"] == vattu_id) {
          r["chon"] = "s1";
        }
      }
      this.ds_vattudagan_table = [...this.ds_vattudagan_table];
    },
    onDataDeSelectionVatTu(dataKeys) {
      if (!dataKeys.data) {
        return;
      }
      if (dataKeys.isInteracted) {
        this.onVattuChangeDeSelect(dataKeys.data.vattu_id);
      }
    },
    onVattuChangeDeSelect(data) {
      for (let r of this.ds_vattudagan_table) {
        if (r["vattu_id"] == data) {
          r["chon"] = 0;
        }
      }
      this.ds_vattudagan_table = [...this.ds_vattudagan_table];
    },
    async btnGoVatTu() {
      let vattuid = [];
      for (let i = 0; i < this.ds_vattudagan_table.length; i++) {
        if (this.ds_vattudagan_table[i].chon == "s1") {
          vattuid.push(this.ds_vattudagan_table[i].vattu_id);
        }
      }
      if (vattuid.length > 0) {
        this.ganGoVatTuLHTB(0, this.params.p_loaihinh, vattuid.toString());
      } else {
        this.$toast.error("Vui lòng chọn vật tư");
      }
      /* if (this.listVatTuDaGanChk != null && this.listVatTuDaGanChk.length > 0) {
          //this.ganGoVatTuLHTB(0, this.params.p_loaihinh,this.listVatTuDaGanChk);
        }else{
          this.$toast.error("Vui lòng chọn vật tư");
        } */
    },
    async chkAllDaGan_CheckedChanged() {
      let ds_vattu_filter = [];
      if(this.$refs.danhsach.currentFilter.value == undefined){
        ds_vattu_filter = this.ds_vattudagan_table;
      }else{
        ds_vattu_filter = this.ds_vattudagan_table.filter( item => item.ten_vt.toUpperCase().includes(this.$refs.danhsach.currentFilter.value.toUpperCase()))
      }
      if (this.chkAllDagan === true) {
        //checked
        if (ds_vattu_filter.length > 0) {
          this.ds_vattudagan_table = ds_vattu_filter;
          for (let i = 0; i < this.ds_vattudagan_table.length; i++) {
            this.ds_vattudagan_table[i].chon = "s1";
          }
          this.ds_vattudagan_table = [...this.ds_vattudagan_table];
        } else {
           this.$toast.error("Không có vật tư !");
        }
      } else {
        //uncheck
        this.ds_vattudagan_table = this.ds_vattudagan;
        //console.log(this.ds_vattudagan_table.length)
        if (this.ds_vattudagan_table.length > 0) {
          for (let i = 0; i < this.ds_vattudagan_table.length; i++) {
            this.ds_vattudagan_table[i].chon = 0;
          }
          this.ds_vattudagan_table = [...this.ds_vattudagan_table];
        } else {
          for (let i = 0; i < this.ds_vattudagan_table.length; i++) {
            this.ds_vattudagan_table[i].chon = 0;
          }
          this.ds_vattudagan_table = [...this.ds_vattudagan_table];
        }
      }
    },
    async btnGanVatTu() {
      if (
        this.listVatTuChuaGanChk != null &&
        this.listVatTuChuaGanChk.length > 0
      ) {
        this.ganGoVatTuLHTB(
          1,
          this.params.p_loaihinh,
          this.listVatTuChuaGanChk
        );
      } else {
        this.$toast.error("Hãy chọn ít nhất 1 vật tư trước khi gán");
      }
    },
    clickLamMoi() {
      this.getDSVatTuChuaGan(this.params.p_loaihinh);
      this.getDSVatTuDaGan(this.params.p_loaihinh);
    },
    clickHDSD() {
      this.$toast.info("Hướng dẫn sử dụng");
    },
    chkNhanhVTChuaGan() {
      this.loading(true);
      setTimeout(
        function () {
          this.chkVTChuaGan();
        }.bind(this),
        1000
      );
    },
    chkVTChuaGan() {
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      if (this.params.chk_nhanhVTChuaGan === true) {
        //checked
        treeObj.checkAll();
        this.params.chkedVTChuaGan = false;
      } else {
        //uncheck
        treeObj.uncheckAll();
        this.params.chkedVTChuaGan = true;
      }
      this.listVatTuChuaGanChk = treeObj.checkedNodes.toString();
      this.loading(false);
    },
    /* filterDSVT() {
      if (this.VtuWord == this.filterTenVT.trim()) {
        this.VtuIndex++;
        if (this.VtuIndex >= this.VtuCache.length) {
          this.VtuIndex = 0;
        }
        this.selectedNodes = [this.VtuCache[this.VtuIndex]];
        this.expandedNodes=[-1]
        let ps = this.getParents(this.VtuCache[this.VtuIndex]);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              this.expandedNodes.push(ps[i].vattu_id);
            }
          }
      } else {
        this.VtuIndex = 0;
        this.VtuWord = this.filterTenVT.trim();
        this.VtuCache = this.vatTuTree
          .filter((a) =>
            a.ten_vt
              .toString()
              .toLowerCase()
              .includes(this.filterTenVT.trim().toLowerCase())
          )
          .map((b) => b.vattu_id);
        this.VtuChaCache = this.vatTuTree
          .filter((a) =>
            a.ten_vt
              .toString()
              .toLowerCase()
              .includes(this.filterTenVT.trim().toLowerCase())
          )
          .map((b) => b.vattu_cha_id);  
        if (this.VtuCache.length) {
          this.selectedNodes = [this.VtuCache[0]];
          let ps = this.getParents(this.VtuChaCache[0]);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              this.expandedNodes.push(ps[i].vattu_id);
            }
          }
        } else {
          this.selectedNodes = ["0"];
          this.expandedNodes=[-1]
        }
      }
    }, */
    async filterDSVT () {
      try {
        this.loading(true)
        let predicate
        let predicats = []
        let _array = []
        let _filter = []
        if (this.filterTenVT.trim() !== '') {
          predicate = new Predicate('ten_vt', 'contains', this.filterTenVT.trim(), true)
          let filteredList = new DataManager(this.danhsachVatTuTree).executeLocal(new Query().where(predicate))
          for (let j = 0; j < filteredList.length; j++) {
            _filter.push(filteredList[j]['vattu_id'])
            let filters = this.getFilterItems(filteredList[j], this.danhsachVatTuTree)
            for (let i = 0; i < filters.length; i++) {
              if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
                _array.push(filters[i])
                predicats.push(new Predicate('vattu_id', 'equal', filters[i], false))
              }
            }
          }
          if (predicats.length === 0) {
            this.changeDataSource([])
          } else {
            let query = new Query().where(Predicate.or(predicats))
            let newList = new DataManager(this.danhsachVatTuTree).executeLocal(query)
            this.changeDataSource(newList)
            setTimeout(() => {
              this.$refs.treeObj.expandAll()
            }, 100)
          }
        } else {
          this.changeDataSource(this.danhsachVatTuTree)
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
     getParents: function (nodeParentID) {
      let rs = [];
      let parent = this.vatTuTree.find((x) => x.vattu_id == nodeParentID);
      if (parent != null) {
        rs.push(parent);
        if (parent.vattu_cha_id != null) {
          let ps = this.getParents(parent.vattu_cha_id);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              rs.push(ps[i]);
            }
          }
        }
      }
      return rs;
    },
    getFilterItems (fList, list) {
      let nodes = []
      nodes.push(fList['vattu_id'])
      let query2 = new Query().where('vattu_id', 'equal', fList['vattu_cha_id'], false)
      let fList1 = new DataManager(list).executeLocal(query2)
      if (fList1.length !== 0) {
        let pNode = this.getFilterItems(fList1[0], list)
        for (let i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) === -1 && pNode[i] != null) {
            nodes.push(pNode[i])
          }
        }
        return nodes
      }
      return nodes
    },
  },
};
</script>