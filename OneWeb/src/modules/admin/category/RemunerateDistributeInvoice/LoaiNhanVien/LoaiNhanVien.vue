<template>
  <div class="box-move-select-table">
    <div class="box-col box-form">
      <div class="legend-title">Loại nhân viên đã gán</div>
      <DataGrid
        class="table-result table-gachle"
        v-bind:columns="ds_dagan.header"
        v-bind:dataSource="ds_dagan.list"
        :visibleHeader="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="false"
        :showColumnCheckbox="true"
        :enabledSelectFirstRow="false"
        ref="dslnvdagan"
        @selectedItemsChanged="selectDaGan_click"
        @rowClicked="reset"
        panelDataHeight="200px"
      >
      </DataGrid>
    </div>
    <div class="actions">
      <button
        id="btnRight"
        class="btn"
        v-on:click="selectRight"
        @click.prevent="disabled"
        :class="{ disabled: btnRight }"
      >
        <span class="fa fa-angle-right"></span>
      </button>
      <button
        id="btnDbRight"
        class="btn"
        v-on:click="selectDoubleRight"
        @click.prevent="disabled"
        :class="{ disabled: btnDoubleRight }"
      >
        <span class="fa fa-angle-double-right"></span>
      </button>
      <button
        id="btnLeft"
        class="btn"
        v-on:click="selectLeft"
        @click.prevent="disabled"
        :class="{ disabled: btnLeft }"
      >
        <span class="fa fa-angle-left"></span>
      </button>
      <button
        id="btnDbLeft"
        class="btn"
        v-on:click="selectDoubleLeft"
        @click.prevent="disabled"
        :class="{ disabled: btnDoubleLeft }"
      >
        <span class="fa fa-angle-double-left"></span>
      </button>
    </div>
    <div class="box-col box-form">
      <div class="legend-title">Loại nhân viên chưa gán</div>
      <DataGrid
        class="table-result table-gachle"
        v-bind:columns="ds_chuagan.header"
        v-bind:dataSource="ds_chuagan.list"
        :visibleHeader="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="false"
        :showColumnCheckbox="true"
        :enabledSelectFirstRow="false"
        ref="dslnvchuagan"
        @selectedItemsChanged="selectChuaGan_click"
        @rowClicked="reset"
        panelDataHeight="200px"
      >
      </DataGrid>
    </div>
  </div>
</template>
<script>
import api from "../API.js";
import DataGrid from "@/components/Controls/DataGrid";

export default {
  name: "LoaiNhanVien",
  components: { DataGrid },
  props: {
    thulao_ck_id: {
      type: Number,
      Default: 0,
    },
  },
  data() {
    return {
      btnRight: false,
      btnLeft: false,
      btnDoubleRight: false,
      btnDoubleLeft: false,
      ds_lnv_dagan: {
        list: [],
        header: [
          {
            fieldName: "ten",
            headerText: "Loại nhân viên",
            allowFiltering: false,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_lnv_chuagan: {
        list: [],
        header: [
          {
            fieldName: "ten",
            headerText: "Loại nhân viên",
            allowFiltering: false,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      selected_dagan: [],
      selected_chuagan: [],
    };
  },
  computed: {
    ds_dagan: {
      get() {
        this.get_lnv_dagan();
        return this.ds_lnv_dagan;
      },
      set(value) {
        return (this.ds_lnv_dagan = value);
      },
    },
    ds_chuagan: {
      get() {
        this.get_lnv_chuagan();
        return this.ds_lnv_chuagan;
      },
      set(value) {
        return (this.ds_lnv_chuagan = value);
      },
    },
    cp_selected_dagan: {
      get() {
        return this.selected_dagan;
      },
      set(value) {
        value.length > 0 ? (this.btnRight = false) : (this.btnRight = true);
        return (this.selected_dagan = value);
      },
    },
    cp_selected_chuagan: {
      get() {
        return this.selected_chuagan;
      },
      set(value) {
        this.btnLeft = value.length > 0 ? false : true;
        return (this.selected_chuagan = value);
      },
    },
  },
  methods: {
    get_lnv_dagan: async function () {
      var input = {
        thulao_ck_id: this.thulao_ck_id,
        type: 1,
      };
      let data = this.GetData(await api.ds_loainhanvien(this.axios, input));
      this.ds_lnv_dagan.list = data;
      this.$emit("assignedItemsChanged", this.getSelectedValues(data));
    },
    get_lnv_chuagan: async function () {
      var input = {
        thulao_ck_id: this.thulao_ck_id,
        type: 0,
      };
      let data = this.GetData(await api.ds_loainhanvien(this.axios, input));
      this.ds_lnv_chuagan.list = data;
    },
    selectDaGan_click: function (val) {
      this.cp_selected_dagan = val;
    },
    selectChuaGan_click: function (val) {
      this.cp_selected_chuagan = val;
    },
    selectRight: function () {
      this.Loading = true;
      var dagan = this.ds_lnv_dagan.list;
      var chuagan = this.ds_lnv_chuagan.list;
      var selected = this.cp_selected_dagan;
      chuagan = chuagan
        .concat(selected)
        .sort((a, b) => parseFloat(b.id) - parseFloat(a.id));
      dagan = dagan
        .filter((item) => !selected.includes(item))
        .sort((a, b) => parseFloat(b.id) - parseFloat(a.id));
      this.ds_lnv_dagan.list = dagan;
      this.ds_lnv_chuagan.list = chuagan;
      this.cp_selected_dagan = [];
      this.cp_selected_chuagan = [];
      this.$emit("assignedItemsChanged", this.getSelectedValues(dagan));
      this.Loading = false;
    },
    selectLeft: function () {
      this.Loading = true;
      var dagan = this.ds_lnv_dagan.list;
      var chuagan = this.ds_lnv_chuagan.list;
      var selected = this.cp_selected_chuagan;
      dagan = dagan
        .concat(selected)
        .sort((a, b) => parseFloat(b.id) - parseFloat(a.id));
      chuagan = chuagan
        .filter((item) => !selected.includes(item))
        .sort((a, b) => parseFloat(b.id) - parseFloat(a.id));
      this.ds_lnv_dagan.list = dagan;
      this.ds_lnv_chuagan.list = chuagan;
      this.cp_selected_dagan = [];
      this.cp_selected_chuagan = [];
      this.$emit("assignedItemsChanged", this.getSelectedValues(dagan));
      this.Loading = false;
    },
    selectDoubleRight: function () {
      this.Loading = true;
      var dagan = this.ds_lnv_dagan.list;
      var chuagan = this.ds_lnv_chuagan.list;
      chuagan = chuagan
        .concat(dagan)
        .sort((a, b) => parseFloat(b.id) - parseFloat(a.id));
      dagan = [];
      this.ds_lnv_dagan.list = dagan;
      this.ds_lnv_chuagan.list = chuagan;
      this.cp_selected_dagan = [];
      this.cp_selected_chuagan = [];
      this.$emit("assignedItemsChanged", this.getSelectedValues(dagan));
      this.Loading = false;
    },
    selectDoubleLeft: function () {
      this.Loading = true;
      var dagan = this.ds_lnv_dagan.list;
      var chuagan = this.ds_lnv_chuagan.list;
      dagan = dagan
        .concat(chuagan)
        .sort((a, b) => parseFloat(b.id) - parseFloat(a.id));
      chuagan = [];
      this.ds_lnv_dagan.list = dagan;
      this.ds_lnv_chuagan.list = chuagan;
      this.cp_selected_dagan = [];
      this.cp_selected_chuagan = [];
      this.$emit("assignedItemsChanged", this.getSelectedValues(dagan));
      this.Loading = false;
    },
    getSelectedValues: function (selectedRecords) {
      let result = [];
      if (selectedRecords != null && selectedRecords.length > 0) {
        for (let i = 0; i < selectedRecords.length; i++) {
          const item = { LOAINV_ID: selectedRecords[i].loainv_id };
          result.push(item);
        }
      }
      return result;
    },
    GetData: function (response) {
      if (
        response.data.error === 200 ||
        response.data.error === "200" ||
        response.data.error === 204 ||
        response.data.error === "204"
      ) {
        return response.data.data;
      } else {
        // console.log(response.data.error_code);
        this.ShowError(response.data.error_code);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    disabled(event) {
      event.currentTarget.id.toString() == "btnRight"
        ? (this.btnRight = true)
        : (this.btnRight = false);
      event.currentTarget.id.toString() == "btnLeft"
        ? (this.btnLeft = true)
        : (this.btnLeft = false);
      event.currentTarget.id.toString() == "btnDbRight"
        ? (this.btnDoubleRight = true)
        : (this.btnDoubleRight = false);
      event.currentTarget.id.toString() == "btnDbLeft"
        ? (this.btnDoubleLeft = true)
        : (this.btnDoubleLeft = false);
    },
    reset(event) {
      this.btnRight = false;
      this.btnLeft = false;
      this.btnDoubleRight = false;
      this.btnDoubleLeft = false;
    },
  },
};
</script>

<style scoped>
.disabled {
  cursor: not-allowed;
  color: gray !important;
}
</style>