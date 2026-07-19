<template src="./index.html"></template>
<style scoped src="./style.scss">
</style>
<script>
import moment from "moment";
import SqlModal from "../modal-sql";

export default {
  name: "RunSqlModal",
  components: { "sql-modal": SqlModal },
  created: async function () {
    this.dataLst = JSON.parse(JSON.stringify(this.data)).map((item, index) => ({
      ...item,
      SQL_CONVERT: item.SQL_CMD ? item.SQL_CMD.split("\n") : [],
      index,
    }));
    this.itemSelected = this.dataLst[0];
  },
  props: ["data"],
  data() {
    return {
      dataLst: [],
      itemSelected: {},
      treeView: true,
      showSetting: true,
      showAll: true,
      showSql: false,
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
      if (idCur > 0) {
        const tmp = this.dataLst[idCur];
        this.dataLst[idCur] = this.dataLst[Number(idCur) - 1];
        this.dataLst[idCur].index = idCur;
        this.dataLst[Number(idCur) - 1] = tmp;
        this.dataLst[Number(idCur) - 1].index = Number(idCur) - 1;
        let magic = JSON.parse(JSON.stringify(this.dataLst));
        this.dataLst = magic;
        this.itemSelected = this.dataLst[Number(idCur) - 1];
      }
    },
    moveDown() {
      const idCur = this.itemSelected.index;
      const nextCur = Number(idCur) + 1;
      if (idCur < this.dataLst.length) {
        const tmp = this.dataLst[idCur];
        this.dataLst[idCur] = this.dataLst[nextCur];
        this.dataLst[idCur].index = idCur;
        this.dataLst[nextCur] = tmp;
        this.dataLst[nextCur].index = nextCur;
        let magic = JSON.parse(JSON.stringify(this.dataLst));
        this.dataLst = magic;
        this.itemSelected = this.dataLst[nextCur];
      }
    },
    onAddItem() {
      const len = this.dataLst.length;
      this.dataLst.push({
        PHANVUNG_ID: "28",
        INV_ID: this.itemSelected.INV_ID,
        VER_ID: this.itemSelected.VER_ID,
        SQL_ID:len > 0 ? Number(this.dataLst[len - 1].SQL_ID) + 1 : 0,
        SQL_CMD: "sql",
        SQL_NAME: "Tên lệnh SQL",
        SQL_ORDER: "1",
        KEY_FIELD: "",
        index: this.dataLst.length,
      });
    },
    onRemoveItem() {
      const cId = this.itemSelected.SQL_ID;
      this.dataLst = this.dataLst.filter((item) => item.SQL_ID != cId);
      if(this.dataLst.length > 0)  this.itemSelected = this.dataLst[this.dataLst.length - 1];
    },
  },
};
</script>