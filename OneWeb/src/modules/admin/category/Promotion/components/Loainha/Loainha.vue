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
            ref="tableDanhSachLoainhaDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachLoainhaDaGan"
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
                field="ten_loainha"
                id="Grid1"
                headerText="Loại nhà đã gán"
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
            ref="tableDanhSachLoainhaChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachLoainhaChuaGan"
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
                field="ten_loainha"
                id="Grid1"
                headerText="Loại nhà chưa gán"
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
import { mixin } from "../mixins/index"; 
import { mapActions, mapMutations } from "vuex";

export default {
  mixins: [mixin],
  data: () => ({
    selectionOptionsCheckboxMode: { checkboxMode: "ResetOnRowClick" },
    tempDaGan: [],
    tempChuaGan: [],
  }),

  computed: {
    danhSachLoainhaDaGan() {
      return this.$store.getters["promotionCommon/danhSachLoainhaDaGan"];
    },
    danhSachLoainhaChuaGan() {
      return this.$store.getters["promotionCommon/danhSachLoainhaChuaGan"];
    },
  },

  methods: {
    ...mapMutations("promotionCommon", [
      "setDanhSachLoainhaDaGan",
      "setDanhSachLoainhaChuaGan",
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachLoainhaDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachLoainhaChuaGan];
        const oldDelivered = [...this.danhSachLoainhaDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.loainha_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.loainha_id));
        this.setDanhSachLoainhaChuaGan(newNotDelivered);
        this.setDanhSachLoainhaDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan =
        this.$refs.tableDanhSachLoainhaChuaGan.getSelectedRecords();
      if (selectedrecordsChuaGan) {
        const oldNotDelivered = [...this.danhSachLoainhaDaGan];
        const oldDelivered = [...this.danhSachLoainhaChuaGan];

        const newNotDelivered = [...selectedrecordsChuaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsChuaGan.map((obj) => obj.loainha_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.loainha_id));
        this.setDanhSachLoainhaDaGan(newNotDelivered);
        this.setDanhSachLoainhaChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachLoainhaDaGan,
        ...this.danhSachLoainhaChuaGan,
      ];
      this.setDanhSachLoainhaChuaGan(temp);
      this.setDanhSachLoainhaDaGan([]);
    },
    handDataToLeftight(e) {
      const temp = [
        ...this.danhSachLoainhaChuaGan,
        ...this.danhSachLoainhaDaGan,
      ];
      this.setDanhSachLoainhaDaGan(temp);
      this.setDanhSachLoainhaChuaGan([]);
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