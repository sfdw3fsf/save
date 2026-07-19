<template>
  <div id="app">
    <div class="">
      <div class="legend-title">
        <div class="pull-left">DS Thuê bao</div>
        <div class="clearfix"></div>
      </div>
      <div
        class="table-content grid-ext grid-ext-freeze"
        style="height: 248px;"
      >
        <ejs-grid :dataSource="dsThueBao" @recordClick="recordClick">
          <e-columns>
            <e-column
              field="ma_tb"
              headerText="Số máy/Acc"
              textAlign="Center"
              width="60"
              :allowEditing="false"
            ></e-column>
          </e-columns>
        </ejs-grid>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { GridPlugin, Page, Edit } from "@syncfusion/ej2-vue-grids";
import { mapActions, mapGetters } from "vuex";
import mixinStore from "../mixin/index";

export default {
  data() {
    return {};
  },
  props: {
    dsThueBao: {
      type: Array,
      default: []
    }
  },
  provide: {
    grid: [Page, Edit]
  },
  async created() {},
  computed: {
    ...mapGetters("mapChangeService")
  },
  watch: {},
  mixins: [mixinStore],
  methods: {
    recordClick(arg) {
      var data = arg.rowData;
      this.$emit("recordClick", data);
    }
  }
};
</script>
<style scoped></style>
