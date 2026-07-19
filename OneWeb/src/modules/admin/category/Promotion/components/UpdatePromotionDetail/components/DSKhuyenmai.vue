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
            ref="tableDanhSachKhuyenmaiDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachKhuyenmaiDaGan"
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
                field="ten_km"
                id="Grid1"
                headerText="Danh sách KM đã gán"
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
            ref="tableDanhSachKhuyenmaiChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachKhuyenmaiChuaGan"
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
                field="ten_km"
                id="Grid1"
                headerText="Danh sách KM chưa gán"
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
    danhSachKhuyenmaiDaGan() {
      return this.$store.getters["promotionDetail/danhSachKhuyenmaiDaGan"];
    },
    danhSachKhuyenmaiChuaGan() {
      return this.$store.getters["promotionDetail/danhSachKhuyenmaiChuaGan"];
    },
  },

  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachKhuyenmaiDaGan",
      "setDanhSachKhuyenmaiChuaGan",
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachKhuyenmaiDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachKhuyenmaiChuaGan];
        const oldDelivered = [...this.danhSachKhuyenmaiDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.khuyenmai_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.khuyenmai_id));
        this.setDanhSachKhuyenmaiChuaGan(newNotDelivered);
        this.setDanhSachKhuyenmaiDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan =
        this.$refs.tableDanhSachKhuyenmaiChuaGan.getSelectedRecords();
      if (selectedrecordsChuaGan) {
        const oldNotDelivered = [...this.danhSachKhuyenmaiDaGan];
        const oldDelivered = [...this.danhSachKhuyenmaiChuaGan];

        const newNotDelivered = [...selectedrecordsChuaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsChuaGan.map((obj) => obj.khuyenmai_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.khuyenmai_id));
        this.setDanhSachKhuyenmaiDaGan(newNotDelivered);
        this.setDanhSachKhuyenmaiChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachKhuyenmaiDaGan,
        ...this.danhSachKhuyenmaiChuaGan,
      ];
      this.setDanhSachKhuyenmaiChuaGan(temp);
      this.setDanhSachKhuyenmaiDaGan([]);
    },
    handDataToLeftight(e) {
      const temp = [
        ...this.danhSachKhuyenmaiChuaGan,
        ...this.danhSachKhuyenmaiDaGan,
      ];
      this.setDanhSachKhuyenmaiDaGan(temp);
      this.setDanhSachKhuyenmaiChuaGan([]);
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