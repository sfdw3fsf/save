<template>
  <div class="tab-pane active" id="tab6">
    <div class="box-move-select-table">
      <div class="box-col box-form">
        <!-- <div class="legend-title">Loại hình đã gán</div> -->
        <div
          class="grid-ext grid-ext-freeze"
          style="height: 232px; overflow: hidden"
        >
          <ejs-grid
            height="110"
            ref="tableDanhSachGoiThamGiaChiTietDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachGoiThamGiaChiTietDaGan"
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
                maxWidth="30"
                minWidth="30"
              ></e-column>
              <e-column
                field="ten_goi"
                id="Grid1"
                headerText="Gói đã gán"
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
            ref="tableDanhSachGoiThamGiaChiTietChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachGoiThamGiaChiTietChuaGan"
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
                maxWidth="30"
                minWidth="30"
              ></e-column>
              <e-column
                field="ten_goi"
                id="Grid1"
                headerText="Gói chưa gán"
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
    danhSachGoiThamGiaChiTietDaGan() {
      return this.$store.getters[
        "promotionDetail/danhSachGoiThamGiaChiTietDaGan"
      ];
    },
    danhSachGoiThamGiaChiTietChuaGan() {
      return this.$store.getters[
        "promotionDetail/danhSachGoiThamGiaChiTietChuaGan"
      ];
    },
  },
  methods: {
        ...mapMutations("promotionDetail", [
      "setDanhSachGoiThamGiaChiTietDaGan",
      "setDanhSachGoiThamGiaChiTietChuaGan",
    ]),
 
        handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachGoiThamGiaChiTietDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachGoiThamGiaChiTietChuaGan];
        const oldDelivered = [...this.danhSachGoiThamGiaChiTietDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.goi_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.goi_id));
        this.setDanhSachGoiThamGiaChiTietChuaGan(newNotDelivered);
        this.setDanhSachGoiThamGiaChiTietDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan =
        this.$refs.tableDanhSachGoiThamGiaChiTietChuaGan.getSelectedRecords();
      if (selectedrecordsChuaGan) {
        const oldNotDelivered = [...this.danhSachGoiThamGiaChiTietDaGan];
        const oldDelivered = [...this.danhSachGoiThamGiaChiTietChuaGan];

        const newNotDelivered = [...selectedrecordsChuaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsChuaGan.map((obj) => obj.goi_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.goi_id));
        this.setDanhSachGoiThamGiaChiTietDaGan(newNotDelivered);
        this.setDanhSachGoiThamGiaChiTietChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachGoiThamGiaChiTietDaGan,
        ...this.danhSachGoiThamGiaChiTietChuaGan,
      ];
      this.setDanhSachGoiThamGiaChiTietChuaGan(temp);
      this.setDanhSachGoiThamGiaChiTietDaGan([]);
    },
    handDataToLeftight(e) {
      const temp = [
        ...this.danhSachGoiThamGiaChiTietChuaGan,
        ...this.danhSachGoiThamGiaChiTietDaGan,
      ];
      this.setDanhSachGoiThamGiaChiTietDaGan(temp);
      this.setDanhSachGoiThamGiaChiTietChuaGan([]);
    },
  }
};
</script>

<style>
</style>