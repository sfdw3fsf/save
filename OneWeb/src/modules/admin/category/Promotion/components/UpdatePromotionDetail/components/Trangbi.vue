<template>
  <div class="tab-pane active" id="tab8">
    <div class="box-move-select-table">
      <div class="box-col box-form">
        <!-- <div class="legend-title">Loại hình đã gán</div> -->
        <div
          class="grid-ext grid-ext-freeze"
          style="height: 232px; overflow: hidden"
        >
          <ejs-grid
            height="110"
            ref="tableDanhSachTrangbiDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachTrangbiDaGan"
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
                field="tentrangbi"
                id="Grid1"
                headerText="Trang bị đã gán"
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
            ref="tableDanhSachTrangbiChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachTrangbiChuaGan"
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
                field="tentrangbi"
                id="Grid1"
                headerText="Trang bị chưa gán"
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
    danhSachTrangbiDaGan() {
      return this.$store.getters["promotionDetail/danhSachTrangbiDaGan"];
    },
    danhSachTrangbiChuaGan() {
      return this.$store.getters["promotionDetail/danhSachTrangbiChuaGan"];
    },
  },

  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachTrangbiDaGan",
      "setDanhSachTrangbiChuaGan",
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachTrangbiDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachTrangbiChuaGan];
        const oldDelivered = [...this.danhSachTrangbiDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.trangbi_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.trangbi_id));
        this.setDanhSachTrangbiChuaGan(newNotDelivered);
        this.setDanhSachTrangbiDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan =
        this.$refs.tableDanhSachTrangbiChuaGan.getSelectedRecords();
      if (selectedrecordsChuaGan) {
        const oldNotDelivered = [...this.danhSachTrangbiDaGan];
        const oldDelivered = [...this.danhSachTrangbiChuaGan];

        const newNotDelivered = [...selectedrecordsChuaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsChuaGan.map((obj) => obj.trangbi_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.trangbi_id));
        this.setDanhSachTrangbiDaGan(newNotDelivered);
        this.setDanhSachTrangbiChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachTrangbiDaGan,
        ...this.danhSachTrangbiChuaGan,
      ];
      this.setDanhSachTrangbiChuaGan(temp);
      this.setDanhSachTrangbiDaGan([]);
    },
    handDataToLeftight(e) {
      const temp = [
        ...this.danhSachTrangbiChuaGan,
        ...this.danhSachTrangbiDaGan,
      ];
      this.setDanhSachTrangbiDaGan(temp);
      this.setDanhSachTrangbiChuaGan([]);
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