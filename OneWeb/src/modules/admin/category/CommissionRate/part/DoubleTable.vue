<template>
  <div class="box-move-select-table">
    <div class="box-col box-form">
      <div class="legend-title">{{ item.title_1 }}</div>
      <div class="table-content" style="height: 200px">
        <Table
          v-if="table_1"
          :options="table_1"
          multiple
          :config="item.config"
          :loading="loading"
          ref="childComponent1"
          :unique="unique"
          @input="inputCheckTable1"
        />
      </div>
    </div>
    <div class="actions">
      <button
        class="btn"
        @click="onCheckTable1toTable2"
        :disabled="table_1_check.length == 0 ? true : false"
      >
        <span class="fa fa-angle-right"></span>
      </button>
      <button
        class="btn"
        :disabled="table_1.length == 0 ? true : false"
        @click="onAllTable1toTable2"
      >
        <span class="fa fa-angle-double-right"></span>
      </button>
      <button
        class="btn"
        @click="onCheckTable2toTable1"
        :disabled="table_2_check.length == 0 ? true : false"
      >
        <span class="fa fa-angle-left"></span>
      </button>
      <button
        class="btn"
        :disabled="table_2.length == 0 ? true : false"
        @click="onAllTable2toTable1"
      >
        <span class="fa fa-angle-double-left"></span>
      </button>
    </div>
    <div class="box-col box-form">
      <div class="legend-title">{{ item.title_2 }}</div>
      <div class="table-content" style="height: 200px">
        <Table
          v-if="table_2"
          :options="table_2"
          multiple
          ref="childComponent2"
          :loading="loading"
          :config="item.config"
          :unique="unique"
          @input="inputCheckTable2"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {
    Table: () => import("./Table.vue"),
  },
  props: {
    item: Object,
    table_1: Array,
    table_2: Array,
    unique: String,
    loading: Boolean,
  },
  data: function () {
    return {
      table_1_check: [],
      table_2_check: [],
    };
  },
  methods: {
    inputCheckTable1(data) {
      this.table_1_check = data;
    },
    inputCheckTable2(data) {
      this.table_2_check = data;
    },
    onAllTable2toTable1() {
      this.$emit("update:table_1", [...this.table_2, ...this.table_1]);
      this.$emit("update:table_2", []);
    },
    onAllTable1toTable2() {
      this.$emit("update:table_2", [...this.table_1, ...this.table_2]);
      this.$emit("update:table_1", []);
    },
    onCheckTable2toTable1() {
      let result = this.table_2.filter((el) =>
        this.table_2_check.includes(el[this.unique])
      );
      this.$emit("update:table_1", [...result, ...this.table_1]);

      let resultDelete = this.table_2.filter(
        (el) => this.table_2_check.indexOf(el[this.unique]) == -1
      );
      this.$emit("update:table_2", resultDelete);
      this.table_2_check = [];
    },
    onCheckTable1toTable2() {
      let result = this.table_1.filter((el) =>
        this.table_1_check.includes(el[this.unique])
      );
      this.$emit("update:table_2", [...result, ...this.table_2]);

      let resultDelete = this.table_1.filter(
        (el) => this.table_1_check.indexOf(el[this.unique]) == -1
      );
      this.$emit("update:table_1", resultDelete);
      this.table_1_check = [];
    },
    getFields(input, field) {
      var output = [];
      for (var i = 0; i < input.length; ++i) {
        output.push(input[i][field]);
      }
      return output;
    },
  },
};
</script>

<style>
</style>
