<template>
  <div class="tab-pane active" id="tab4">
    <div class="box-move-select-table">
      <div class="box-col box-form">
        <!-- <div class="legend-title">Loại hình đã gán</div> -->
        <div
          class="grid-ext grid-ext-freeze"
          style="height: 232px; overflow: hidden"
        >
          <ejs-grid
            height="110"
            ref="tableDanhSachToaNhaDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachToaNhaDaGan"
            :allowPaging="true"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowSelection="true"
            :selectionSettings="selectionOptionsCheckboxMode"
            :pagerTemplate="pagerTemplate"
            gridLines="Both"
          >
            <e-columns>
              <e-column
                template="<span class='fa selected-flag'></span>"
                :allowFiltering="false"
                filterTemplate="<span></span>"
                width="10"
                maxWidth="10"
                minWidth="10"
                textAlign="center"
                :customAttributes="{ class: 'column-selected-flag' }"
                :freeze="'left'"
                :allowResizing="false"
                clipMode="Ellipsis"
              ></e-column>
              <e-column
                type="checkbox"
                width="30"
                minWidth="30"
                maxWidth="30"
              ></e-column>
              <e-column
                field="ten_toanha"
                id="Grid1"
                headerText="Tòa nhà đã gán"
                width="300"
                headerTextAlign="center"
                textAlign="Left"
                :filter="{ operator: 'contains' }"
              ></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>

      <div class="actions">
        <button class="btn" @click="handCheckBoxDaGanRemove">
          <span class="fa fa-angle-right"></span>
        </button>
        <button class="btn" @click="handDataToRight">
          <span class="fa fa-angle-double-right"></span>
        </button>
        <button class="btn" @click="handCheckBoxDaChuaRemove">
          <span class="fa fa-angle-left"></span>
        </button>
        <button class="btn" @click="handDataToLeftight">
          <span class="fa fa-angle-double-left"></span>
        </button>
      </div>
      <div class="box-col box-form">
        <!-- <div class="legend-title">Loại hình đã gán</div> -->
        <div
          class="grid-ext grid-ext-freeze"
          style="height: 232px; overflow: hidden"
        >
          <ejs-grid
            height="110"
            ref="tableDanhSachToaNhaChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachToaNhaChuaGan"
            :allowPaging="true"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowSelection="true"
            :selectionSettings="selectionOptionsCheckboxMode"
            :pagerTemplate="pagerTemplate"
            gridLines="Both"
          >
            <e-columns>
              <e-column
                template="<span class='fa selected-flag'></span>"
                :allowFiltering="false"
                filterTemplate="<span></span>"
                width="10"
                maxWidth="10"
                minWidth="10"
                textAlign="center"
                :customAttributes="{ class: 'column-selected-flag' }"
                :freeze="'left'"
                :allowResizing="false"
                clipMode="Ellipsis"
              ></e-column>
              <e-column
                type="checkbox"
                width="30"
                minWidth="30"
                maxWidth="30"
              ></e-column>
              <e-column
                field="ten_toanha"
                id="Grid1"
                headerText="Tòa nhà chưa gán"
                width="300"
                headerTextAlign="center"
                textAlign="Left"
                :filter="{ operator: 'contains' }"
              ></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin } from "./mixins/index";
import { mapActions, mapMutations } from "vuex";

export default {
  mixins: [mixin],
  data: () => ({
    selectionOptionsCheckboxMode: { checkboxMode: "ResetOnRowClick" },
    tempDaGan: [],
    tempChuaGan: [],
  }),

  computed: {
    danhSachToaNhaDaGan() {
      return this.$store.getters["promotionDetail/danhSachToaNhaDaGan"];
    },
    danhSachToaNhaChuaGan() {
      return this.$store.getters["promotionDetail/danhSachToaNhaChuaGan"];
    },
  },

  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachToaNhaDaGan",
      "setDanhSachToaNhaChuaGan",
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachToaNhaDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachToaNhaChuaGan];
        const oldDelivered = [...this.danhSachToaNhaDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.toanha_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.toanha_id));
        this.setDanhSachToaNhaChuaGan(newNotDelivered);
        this.setDanhSachToaNhaDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan =
        this.$refs.tableDanhSachToaNhaChuaGan.getSelectedRecords();
      if (selectedrecordsChuaGan) {
        const oldNotDelivered = [...this.danhSachToaNhaDaGan];
        const oldDelivered = [...this.danhSachToaNhaChuaGan];

        const newNotDelivered = [...selectedrecordsChuaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsChuaGan.map((obj) => obj.toanha_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.toanha_id));
        this.setDanhSachToaNhaDaGan(newNotDelivered);
        this.setDanhSachToaNhaChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachToaNhaDaGan,
        ...this.danhSachToaNhaChuaGan,
      ];
      this.setDanhSachToaNhaChuaGan(temp);
      this.setDanhSachToaNhaDaGan([]);
    },
    handDataToLeftight(e) {
      const temp = [
        ...this.danhSachToaNhaChuaGan,
        ...this.danhSachToaNhaDaGan,
      ];
      this.setDanhSachToaNhaDaGan(temp);
      this.setDanhSachToaNhaChuaGan([]);
    },
  },
};
</script>

<style>
#Grid1.e-grid .e-gridheader .e-columnheader,
#Grid3.e-grid .e-gridheader .e-columnheader {
  display: none;
}
</style> 