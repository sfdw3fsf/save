<template src="./index.html"></template>
<style scoped src="./style.scss">
</style>
<script>
import moment from "moment";
import SqlModal from "../modal-sql"

export default {
  name: "GroupModal",
  components: {'sql-modal':SqlModal},
  created: async function () {
    this.orderLst = JSON.parse(JSON.stringify(this.data)).map((item,index) => ({
      ...item.data,
      SQL_CONVERT: item.data.GROUP_SQL ? item.data.GROUP_SQL.split('\n') : [],
      index
    }));
    this.itemSelected = this.orderLst[0];
    console.log(this.orderLst);
  },
  props: ["data"],
  data() {
    return {
      orderLst: [],
      itemSelected: {},
      treeView: false,
      showSetting: true,
      showSql:false
    };
  },
  computed: {},
  methods: {
    showDetailConds(item) {
      this.itemSelected = item;
      console.log(this.itemSelected);
    },
    moveUp() {
      const idCur = this.itemSelected.index;
      if(idCur > 0) {
        const tmp = this.orderLst[idCur];
         this.orderLst[idCur] = this.orderLst[Number(idCur) -1];
         this.orderLst[idCur].index = idCur;
         this.orderLst[Number(idCur) - 1] = tmp;
         this.orderLst[Number(idCur) - 1].index = Number(idCur) - 1;
         let magic = JSON.parse(JSON.stringify(this.orderLst));
         this.orderLst = magic;
         this.itemSelected = this.orderLst[Number(idCur) - 1];
      }
    },
    moveDown() {
      const idCur = this.itemSelected.index;
      const nextCur = Number(idCur) + 1;
      if(idCur < this.orderLst.length) {
        const tmp = this.orderLst[idCur];
         this.orderLst[idCur] = this.orderLst[nextCur];
         this.orderLst[idCur].index = idCur;
         this.orderLst[nextCur] = tmp;
         this.orderLst[nextCur].index = nextCur;
         let magic = JSON.parse(JSON.stringify(this.orderLst));
         this.orderLst = magic;
         this.itemSelected = this.orderLst[nextCur];
      }
    },
    onAddItem() {
      const len =  this.orderLst.length;
      this.orderLst.push({
        "PHANVUNG_ID": "28",
        "DESCRIPTION": "Mô tả",
        "SELECTED": "0",
        "REPLACE_STRING": "Chuỗi thay thế",
        "ORDER_ID": Number(this.orderLst[len-1].ORDER_ID) + 1,
        "INV_ID": this.itemSelected.INV_ID,
        "VER_ID": this.itemSelected.VER_ID,
        "ORDER_LIST": "Ds trường ",
        "index": this.orderLst.length
      })
    },
    onRemoveItem() {
      const index = this.itemSelected.index - 1;
      const cId = this.itemSelected.ORDER_ID;
      this.orderLst = this.orderLst.filter(item=> item.ORDER_ID != cId);
      this.itemSelected = this.orderLst[index];
    },
  },
};
</script>