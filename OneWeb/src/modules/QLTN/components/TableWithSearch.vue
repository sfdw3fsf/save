<template>
  <div :class="[boxForm && 'box-form']">
    <div class="legend-title" v-if="showHeader">{{ this.header }}</div>
    <div class="table-content">
      <b-table
        :items="displayItemsGetter"
        :fields="fields"
        show-empty
        selectable
        select-mode="single"
        class="table-result table-gachle"
        ref="selectableTable"
        @row-selected="onRowSelected"
      >
        <template #head(isCheck)="slotData">
          <b-form-checkbox
            @change="onSelectAll"
            :checked="isSelectAll"
          ></b-form-checkbox>
        </template>
        <template #top-row="data">
          <b-th v-for="(item, index) in fields" :key="index">
            <div class="input-icon-right" v-if="fields[index].isSearch">
              <input
                type="text"
                class="form-control"
                @input="onChangeText(fields[index].key, $event.target.value)"
              />
              <span class="icon nc-icon-outline ui-1_zoom"></span>
            </div>
            <div v-else></div>
          </b-th>
        </template>
        <template #empty="scope" v-if="displayEmptyText">
          <div class="text-center my-2">Không có bản ghi nào để hiển thị</div>
        </template>
        <template #emptyfiltered="scope" v-if="displayEmptyText">
          <div class="text-center my-2">Không có bản ghi nào để hiển thị</div>
        </template>
        <template #cell(selectedColumn)="{ rowSelected }">
          <template v-if="rowSelected">
            <span aria-hidden="true" class="fa fa-play text-main"></span>
          </template>
          <template v-else>
            <span aria-hidden="true">&nbsp;</span>
          </template>
        </template>
        <template v-slot:cell(isCheck)="row">
          <b-form-checkbox
            v-model="row.item.isCheck"
            :key="row.index"
          ></b-form-checkbox>
        </template>
      </b-table>
    </div>
    <div class="tc-pagination" v-if="showPagination">
      <button
        @click="firstPage()"
        :disabled="this.pageInfo.page == 0"
        class="btn one-arrow-left-forward"
      ></button>
      <button
        @click="previousPage()"
        :disabled="this.pageInfo.page == 0"
        class="btn fa fa-angle-double-left"
      ></button>
      <button
        @click="doSelectNearby(false)"
        class="btn fa fa-angle-left"
      ></button>
      <span class="text">Bản ghi</span>
      <div class="dropdown inline">
        <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">{{ getSelectedRow }}</span>
          <!-- <span class="text-main">{{ getSelectedRow }}</span
          > -->
          /{{ this.pageInfo.totalElement }}
        </div>
        <div class="dropdown-menu">
          <a href="#" class="dropdown-item">10</a>
          <a href="#" class="dropdown-item">20</a>
        </div>
      </div>
      <button
        @click="doSelectNearby(true)"
        class="btn fa fa-angle-right"
      ></button>
      <button
        @click="nextPage()"
        :disabled="this.pageInfo.page >= this.pageInfo.totalPages - 1"
        class="btn fa fa-angle-double-right"
      ></button>
      <button
        @click="lastPage()"
        :disabled="this.pageInfo.page >= this.pageInfo.totalPages - 1"
        class="btn one-arrow-right-forward"
      ></button>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    fields: Array,
    itemsAssign: Array,
    selectMode: String,
    header: String,
    showPagination: {
      type: Boolean,
      default: true,
    },
    showHeader: {
      type: Boolean,
      default: true,
    },
    pageIndex: {
      type: Number,
      default: 0,
    },
    pageSize: {
      type: Number,
      default: 10,
    },
    selectedRow: {
      type: Number,
      default: 0,
    },
    getDataFunc: {
      type: Function,
    },
    onRowSelectedFunc: {
      type: Function,
    },
    boxForm: {
      type: Boolean,
      default: true,
    },
  },
  updated() {
    if (this.currentSelectedRow == 0) {
      this.$refs.selectableTable.selectRow(this.currentSelectedRow);
    }
  },
  async mounted() {
    if (this.itemsAssign && this.itemsAssign.length > 0)
      this.items = this.itemsAssign;
    // else {
    //   if (this.getDataFunc) await this.fetchData();
    // }
    this.currentSelectedRow = 0;
  },
  computed: {
    getSelectedRow: function () {
      console.log("pageSize", this.pageInfo.maxSize);
      console.log("page", this.pageInfo.page);
      console.log("currentSelectedRow", this.currentSelectedRow);
      return (
        this.pageInfo.maxSize * this.pageInfo.page + this.currentSelectedRow + 1
      );
    },
    displayItemsGetter() {
      let displayItems = this.itemsAssign || this.items;
      if (this.searchArr.length > 0) {
        for (var i = 0; i < this.searchArr.length; i++) {
          let search = this.searchArr[i];
          displayItems = displayItems.filter((x) => {
            const itemValue = x[search.searchField] + "" || "";
            return (
              itemValue
                .toLowerCase()
                .indexOf(search.searchValue.toLowerCase()) > -1
            );
          });
        }
      }
      return displayItems;
    },
  },
  data() {
    return {
      items: [],
      isSelectAll: false,
      searchArr: [],
      displayEmptyText: true,
      currentPageSize: this.pageSize,
      currentPageIndex: this.pageIndex,
      currentSelectedRow: this.selectedRow,
      pageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
    };
  },
  watch: {
    // isSelectAll: {
    //       handler: function (value) {
    //           console.log('isSelectAll', this.isSelectAll)
    //       }
    //   }
    currentSelectedRow: {
      handler: function (value) {
        console.log("currentSelectedRow", value);
        this.$refs.selectableTable.selectRow(value);
      },
    },
  },
  methods: {
    onSelectAll() {
      this.isSelectAll = !this.isSelectAll;
      this.items.forEach((element) => {
        element.isCheck = this.isSelectAll;
      });
    },
    async onRowSelected(item) {
      console.log("onRowSelected");
      if (this.onRowSelectedFunc) await this.onRowSelectedFunc(item);
    },
    async fetchData() {
      let response = await this.getDataFunc(
        this.pageInfo.page + 1,
        this.pageInfo.maxSize
      );
      console.log(response.data.error_code);
      console.log(response.data);

      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.data !== undefined &&
          response.data.data.data.length > 0
        ) {
          this.items = response.data.data.data;

          if (this.items) {
            this.items.forEach((element) => {
              element.isCheck = false;
            });
          }
          this.pageInfo = {
            page: response.data.data.page,
            maxSize: response.data.data.maxSize,
            totalElement: response.data.data.totalElement,
            totalPages: response.data.data.totalPages,
            sort: response.data.data.sort,
            propertiesSort: response.data.data.propertiesSort,
          };
        } else {
          this.items = [];
          this.pageInfo = {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
          };
          this.currentSelectedRow = 0;
        }
      } else {
        console.log(respone.data.message);
      }
    },
    onChangeText(key, value) {
      this.searchArr = this.searchArr.filter((x) => x.searchField !== key);
      if (value) {
        this.searchArr.push({
          searchField: key,
          searchValue: value,
        });
      }
    },
    onClearSearch() {
      this.displayItems = this.items;
    },
    filterChange: function () {
      if (this.currentSelectedRow > -1) this.setCurrentSelectedRow(0);
    },
    doSelectNearby: async function (next) {
      console.log("doSelectNearby", this.currentSelectedRow);
      if (next) {
        if (this.currentSelectedRow < this.pageSize - 1) {
          this.currentSelectedRow += 1;
        } else {
          if (this.pageInfo.page < this.pageInfo.totalPages)
            await this.nextPage();
        }
      } else {
        if (this.currentSelectedRow > 0) {
          this.currentSelectedRow -= 1;
        } else {
          if (this.pageInfo.page > 0) await this.previousPage();
        }
      }
    },
    getCurrentSelectedRow: function () {},
    setCurrentSelectedRow: function (value) {
      //   if (this.currentSelectedRow == value) {
      //     if (value > this.pageRowCount - 1) value = this.pageRowCount - 1;
      //   } else {
      //     if (value > this.pageRowCount - 1 || value < 0) {
      //       if (this.currentSelectedRow == -1) {
      //         if (value > this.currentSelectedRow) value = 0;
      //         else value = this.pageRowCount - 1;
      //       } else value = -1;
      //     }
      //   }
      //   this.currentSelectedRow = value;
      //   this.$emit(
      //     "selectedRowChanged",
      //     !(this.dataItems == null || this.dataItems.length == 0)
      //       ? this.currentSelectedRow > -1
      //         ? this.dataItems[this.currentSelectedRow]
      //         : null
      //       : null
      //   );
    },
    setPageSize: async function (value) {
      this.pageInfo.maxSize = value;
      await this.fetchData();
    },
    nextPage: async function () {
      this.pageInfo.page += 1;
      await this.fetchData();
      this.currentSelectedRow = 0;
    },
    lastPage: async function () {
      this.pageInfo.page = this.pageInfo.totalPages;
      await this.fetchData();
      this.currentSelectedRow = 0;
    },
    previousPage: async function () {
      this.pageInfo.page -= 1;
      await this.fetchData();
      this.currentSelectedRow = this.displayItemsGetter.length - 1;
    },
    firstPage: async function () {
      this.pageInfo.page = 1;
      await this.fetchData();
      this.currentSelectedRow = this.displayItemsGetter.length - 1;
    },
  },
};
</script>
