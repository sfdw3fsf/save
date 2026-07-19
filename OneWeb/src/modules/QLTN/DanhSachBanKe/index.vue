<template src="./index.html"></template>
<style src="./style.scss" scoped></style>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import TreeMenu from "./components/tree-menu";
import OrderModal from "./components/order-modal";
import GroupModal from "./components/group-modal";
import RunSqlModal from "./components/run-sql-modal";
import SqlModal from "./components/modal-sql";

const nest = (items, GROUP_ID = 0, link = "OWNER_ID") =>
  items.filter((item) => item[link] === GROUP_ID).map((item) =>
   ({ ...item, nodes: nest(items, item.GROUP_ID) }));

const nest2 = (items, key = 0, link = "parentId") =>
  items.filter((item) => item[link] === key).map((item) =>
   ({ ...item, nodes: nest2(items, item.key) }));

export default {
  components: { 
    breadcrumb,
    "tree-menu": TreeMenu,
    "order-modal" : OrderModal,
    "group-modal" : GroupModal,
    "run-sql-modal" : RunSqlModal,
    "sql-modal" : SqlModal
  },
  data: function () {
    return {
      fields: { dataSource: [], id: "key", text: "label", child: "nodes" },
      header: {
        title: "DANH SÁCH BẢN KÊ",
        list: [
          { name: "Hóa đơn", link: { name: "Ui.cards" } },
          {
            name: "Danh sách bản kê",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      idPhienBan: "",
      dsPhienBan: [],
      rootLst: [],
      nodeSelected: null,
      itemSelected: {},
      groupsData:[],
      dataTho:[],
      chidNodes:[],
      parentNode:[],
      isShowIdSt: true,
      isShowSql: false,
      isShowNhomDk: true,
      isShowDk: false,
      isShowOrder: false,
      isShowNhomDl: true,
      isShowDl: true,
      isShowGrp: false,
      isShowNhomSql: true,
      isRootNode: true,
      conditionLst: [],
      orderLst:[],
      groupLst: [],
      runSqlLst:[],
      globalTitle :"",
      globalDescription:"",
      conditionSelected: {},
      mdCondition: {
        showSetting: true,
        showSql: false,
        contreeView: true
      }
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    this.getDsPhienBan();
  },
  async mounted() {},
  methods: {
    onToggleIdSt() {

    },
    onChangeVersion() {
      this.chidNodes =[],
      this.parentNode =[],
      this.getRootLst();
    },
    onClickTree(data) {
      console.log("data", data);
    },
    onClickSQL() {
      this.$bvModal.show("modal-string-editor");
    },
    setTextGlobal(title, description) {
      this.globalTitle = title;
      this.globalDescription = description;
    },
    onShowModalCondition() {
      this.$bvModal.show('modal-condition');
      this.showDetailConds(this.conditionLst[0]);
    },
    showDetailConds(item){
      // console.log(item)
      this.conditionSelected = {...item, SQL_CONVERT: item.C_SQL.length > 0 ? item.C_SQL.split('\n') : []}
      console.log(this.conditionSelected)
    },
    hoanViLen() {
      const idCur = this.conditionSelected.index;
      console.log(idCur)
      if(idCur > 0) {
        const tmp = this.conditionLst[idCur];
         this.conditionLst[idCur] = this.conditionLst[Number(idCur) -1];
         this.conditionLst[idCur].index = idCur;
         this.conditionLst[Number(idCur) - 1] = tmp;
         this.conditionLst[Number(idCur) - 1].index = Number(idCur) - 1;
         let magic = JSON.parse(JSON.stringify(this.conditionLst));
         this.conditionLst = magic;
         this.conditionSelected = this.conditionLst[Number(idCur) - 1];
        //  console.log( this.conditionLst)
      }
    },
    hoanViXuong() {
      const idCur = this.conditionSelected.index;
      const nextCur = Number(idCur) + 1;
      console.log(idCur)
      if(idCur < this.conditionLst.length) {
        const tmp = this.conditionLst[idCur];
         this.conditionLst[idCur] = this.conditionLst[nextCur];
         this.conditionLst[idCur].index = idCur;
         this.conditionLst[nextCur] = tmp;
         this.conditionLst[nextCur].index = nextCur;
         let magic = JSON.parse(JSON.stringify(this.conditionLst));
         this.conditionLst = magic;
         this.conditionSelected = this.conditionLst[nextCur];
        //  console.log( this.conditionLst)
      }
    },
    onAddCondition() {
      this.conditionLst.push({
        "PHANVUNG_ID": "28",
        "C_TYPE": "2",
        "OPERATOR": "",
        "C_LABEL": "Tên điều kiện",
        "C_SQL": "",
        "REPLACE_STRING": "",
        "C_ORDER": "",
        "FIELD_DATATYPE": "0",
        "C_ID": this.conditionLst.length + 1,
        "DEFAULT_VALUE": "",
        "C_FIELD": "",
        "INV_ID": this.itemSelected.INV_ID,
        "VER_ID": this.idPhienBan,
        "C_SQL_OLD": "",
        "index": this.conditionLst.length,
        "SQL_CONVERT": []
      })
    },

    onRemoveCondition() {
      const index = this.conditionSelected.index - 1;
      const cId = this.conditionSelected.C_ID;
      this.conditionLst = this.conditionLst.filter(item=> item.C_ID != cId);
      this.conditionSelected = this.conditionLst[index];
    },
    nodeClicked:async function(args) {
      this.globalTitle = '';
      this.globalDescription='';
      var treeObj = document.getElementById("treeview").ej2_instances[0];
      this.nodeSelected = treeObj.getNode(args.node);
      this.isRootNode =  !this.nodeSelected.parentID;
      this.parentNode =[];
      const nodeTho = this.dataTho.filter(item=>item.key == this.nodeSelected.id)[0];
      this.parentNode.push(nodeTho.key);
      this.getAllParentNode(nodeTho);
      this.itemSelected = this.dataTho.filter(item=>item.key == this.nodeSelected.id)[0].data;
      if(this.isRootNode) {
        this.itemSelected.SQL_CONVERT = this.itemSelected.INV_SQL ? this.itemSelected.INV_SQL.split(`\n`) : [];
        this.conditionLst = await this.getConditionLst(this.itemSelected.INV_ID);
        this.orderLst = await this.getOrderLst(this.itemSelected.INV_ID);
        this.groupLst = this.dataTho.filter(item=>item.invId == this.itemSelected.INV_ID);
        this.runSqlLst = await this.getRunSqlLst(this.itemSelected.INV_ID);
      } else {
        this.itemSelected.SQL_CONVERT = this.itemSelected.GROUP_SQL ? this.itemSelected.GROUP_SQL.split(`\n`) : [];
      }
      console.log(this.itemSelected)
    },
    onThem : async function() {
      if (!this.nodeSelected || this.nodeSelected.parentID == null) {
         const listRootNode = this.dataTho.filter(item=> item.parentId == 0);
        const nextId = this.getId(listRootNode) + 1;
        console.log(nextId)
        this.dataTho.push({
          key: `p-${nextId}`,
          id: nextId,
          label: `${nextId} Ten ban ke`,
          nodes: [],
          parentId: 0
        });
      } else {
        const parentId = this.nodeSelected.parentID;
        const rootNode = this.getRootNode(this.dataTho, parentId);
        this.getListNodeOfRoot(this.dataTho, rootNode.key);
        const nextId = this.getId(this.chidNodes) + 1;
        this.dataTho.push({
            id: nextId,
            label: `${nextId} ten nhom`,
            key: `${parentId}-${nextId}`,
            parentId: parentId,
            expanded: true
        })
      }
      this.dataTho.forEach(element => {
        if(this.parentNode.includes(element.key)) {
          element.expanded = true;
        }
      });
      let cloneData = JSON.parse(JSON.stringify(nest2(this.dataTho)));
      this.fields = { dataSource: cloneData, id: "key", text: "label", child: "nodes" };
    },
    onXoaNode() {
      if(this.checkIsLeaf(this.nodeSelected)) {
        this.dataTho = this.dataTho.filter(item=> item.key != this.nodeSelected.id);
        this.dataTho.forEach(element => {
          if(this.parentNode.includes(element.key)) {
            element.expanded = true;
          }
        });
        let cloneData = JSON.parse(JSON.stringify(nest2(this.dataTho)));
        this.fields = { dataSource: cloneData, id: "key", text: "label", child: "nodes" };
      } else {
        this.$toast.error("Bạn phải xóa các nhóm dữ liệu con trước")
      }
    },
    onAddChildNode() {
      const parentId = this.nodeSelected.parentID;
      const rootNode = parentId == null ? {key :this.nodeSelected.id} :this.getRootNode(this.dataTho, parentId);
      this.getListNodeOfRoot(this.dataTho, rootNode.key);
      const nextId = this.getId(this.chidNodes) + 1;
      this.dataTho.push({
          id: nextId,
          label: `${nextId} ten nhom`,
          key: `${this.nodeSelected.id}-${nextId}`,
          parentId: this.nodeSelected.id,
          expanded: true
      })

      this.dataTho.forEach(element => {
        if(this.parentNode.includes(element.key)) {
          element.expanded = true;
        }
      });
      let cloneData = JSON.parse(JSON.stringify(nest2(this.dataTho)));
      this.fields = { dataSource: cloneData, id: "key", text: "label", child: "nodes" };
    },
    checkIsLeaf(node) {
      return this.dataTho.filter(item=> item.parentId == node.id).length == 0;
    },
    getRootNode(list, parentId) {
      const parentNode = list.filter(item=>item.key==parentId);
      if(parentNode[0].parentId == 0) return parentNode[0];
      return this.getRootNode(list, parentNode[0].parentId);
    },
    getAllParentNode(nodeTho) {
      // this.parentNode.push(nodeTho);
      if(nodeTho.parentId == 0) return ;
      const parentNode = this.dataTho.filter(item=>item.key == nodeTho.parentId);
       parentNode.length > 0 && this.parentNode.push(parentNode[0].key);
      return this.getAllParentNode(parentNode[0]);
    },
    getListNodeOfRoot(list, rootId) {
      // console.log('rootId', rootId)
      const node = list.filter(item=>item.parentId==rootId);
      // console.log('Node', node)
      if(node.length == 0) return  this.chidNodes;
      this.chidNodes = this.chidNodes.concat(node);
      for (let index = 0; index < node.length; index++) {
        const element = node[index];
        this.getListNodeOfRoot(list, element.key);
      }
    },
    getId(list) {
      const currentIds = list.map((item) => item.id).sort((a, b) => a - b);
      if (currentIds.length == 0) return 0;
      const findElement = (arr) => {
        for (let i = 1; i < arr.length; i++) {
          if (arr[i] - arr[i - 1] > 1) {
            return arr[i - 1];
          }
        }
        return false;
      };
      return findElement(currentIds) ? Number(findElement(currentIds)) : Number(currentIds[currentIds.length - 1]);
    },
    getDsPhienBan: async function () {
      try {
        const rs = await this.$root.context.get("/web-thuno/api/thu-no/bao-cao/version");
        this.dsPhienBan = rs.data;
      } catch (error) {}
    },
    getRootLst: async function () {
      try {
        const rs = await this.$root.context.get(`/web-thuno/api/thu-no/bao-cao/inv-list/${this.idPhienBan}`);
        this.dataTho =  rs.data.map(item=> ({
            data : {
              ...item
            },
            id: item.INV_ID,
            label: item.INV_ID + " " + item.INV_NAME,
            key: `p-${item.INV_ID}`,
            parentId: 0,
            nodes: [], 
            expanded: false
        }))
        this.rootLst = await Promise.all(
          rs.data.map(async (item) => ({
            id: item.INV_ID,
            label: item.INV_ID + " " + item.INV_NAME,
            key: `p-${item.INV_ID}`,
            ...item,
            nodes: await this.getRootGroupLst(item.INV_ID),
          }))
        );
        this.fields = { dataSource: nest2(this.dataTho), id: "key", text: "label", child: "nodes", expandedNodes:["p-1"] };
      } catch (error) {}
    },
    getRootGroupLst: async function (invId) {
      const param = {
        invId: invId,
        verId: this.idPhienBan,
      };
      try {
        const rs = await this.$root.context.get(`/web-thuno/api/thu-no/bao-cao/arlg-groups`, param);
        // console.log(invId, rs.data)
        const tmpData = rs.data.map(item=> ({
          data : {
            ...item
          },
          id: item.GROUP_ID,
          label: item.GROUP_ID + " " + item.GROUP_NAME,
          key: `c-${invId}-${item.GROUP_ID}`,
          parentId: item.OWNER_ID == 0 ? `p-${invId}` : `c-${invId}-${item.OWNER_ID}`,
          nodes: [],
          expanded: false,
          invId: invId
        }))
        if(tmpData.length > 0)
          Array.prototype.push.apply(this.dataTho,tmpData); 
        return nest(
          rs.data.map((item) => ({
            id: item.GROUP_ID,
            label: item.GROUP_ID + " " + item.GROUP_NAME,
            key: `c-${invId}-${item.GROUP_ID}`,
            ...item,
          }))
        );
      } catch (error) {}
    },
    getRootGroupLstId: async function (invId) {
      const param = {
        invId: invId,
        verId: this.idPhienBan,
      };
      try {
        const rs = await this.$root.context.get(`/web-thuno/api/thu-no/bao-cao/arlg-groups`, param);
        return rs.data.map((item) =>  ({id: item.GROUP_ID}))
      } catch (error) {}
    },
    getConditionLst: async function (invId) {
      const param = {
        invId: invId,
        verId: this.idPhienBan,
      };
      try {
        const rs = await this.$root.context.get(`/web-thuno/api/thu-no/bao-cao/condition`, param);
        return rs.data.map((item, index)=> ({...item, index: index}));
      } catch (error) {}
    },
    getOrderLst: async function (invId) {
      const param = {
        invId: invId,
        verId: this.idPhienBan,
      };
      try {
        const rs = await this.$root.context.get(`/web-thuno/api/thu-no/bao-cao/data-order`, param);
        return rs.data.sort((a, b) => parseInt(a.ORDER_ID) - parseInt(b.ORDER_ID)).map((item, index)=>({...item, index}));
      } catch (error) {}
    },
    getRunSqlLst: async function (invId) {
      const param = {
        invId: invId,
        verId: this.idPhienBan,
      };
      try {
        const rs = await this.$root.context.get(`/web-thuno/api/thu-no/bao-cao/data-run-sql`, param);
        return rs.data.sort((a, b) => parseInt(a.SQL_ID) - parseInt(b.SQL_ID)).map((item, index)=>({...item, index}));
      } catch (error) {}
    },
  },
};
</script>
