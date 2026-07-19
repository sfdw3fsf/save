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
            ref="tableDanhSachLoaiHinhTBDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachLoaiHinhTBDaGan"
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
                field="tocdo"
                id="Grid1"
                headerText="Loại hình đã gán"
                width="550"
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
            ref="tableDanhSachLoaiHinhTBChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachLoaiHinhTBChuaGan"
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
                field="tocdo"
                id="Grid1"
                headerText="Loại hình chưa gán"
                width="550"
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
    danhSachLoaiHinhTBDaGan() {
      return this.$store.getters["promotionDetail/danhSachLoaiHinhTBDaGan"];
    },
    danhSachLoaiHinhTBChuaGan() {
      return this.$store.getters["promotionDetail/danhSachLoaiHinhTBChuaGan"];
    },
  },

  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachLoaiHinhTBDaGan",
      "setDanhSachLoaiHinhTBChuaGan",
      "setAllowEditPrice"
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachLoaiHinhTBDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachLoaiHinhTBChuaGan];
        const oldDelivered = [...this.danhSachLoaiHinhTBDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.tocdo_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.tocdo_id));
        this.setDanhSachLoaiHinhTBChuaGan(newNotDelivered);
        this.setDanhSachLoaiHinhTBDaGan(res);
        this.checkAllowEdit(res)
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan =
        this.$refs.tableDanhSachLoaiHinhTBChuaGan.getSelectedRecords();
      if (selectedrecordsChuaGan) {
        const oldNotDelivered = [...this.danhSachLoaiHinhTBDaGan];
        const oldDelivered = [...this.danhSachLoaiHinhTBChuaGan];

        const newNotDelivered = [...selectedrecordsChuaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsChuaGan.map((obj) => obj.tocdo_id);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.tocdo_id));
        this.setDanhSachLoaiHinhTBChuaGan(res);
        this.setDanhSachLoaiHinhTBDaGan(newNotDelivered);
        this.checkAllowEdit(newNotDelivered)
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachLoaiHinhTBDaGan,
        ...this.danhSachLoaiHinhTBChuaGan,
      ];
      this.setDanhSachLoaiHinhTBChuaGan(temp);
      this.setDanhSachLoaiHinhTBDaGan([]);
      this.checkAllowEdit([])
    },
    handDataToLeftight(e) {
      const temp = [
        ...this.danhSachLoaiHinhTBChuaGan,
        ...this.danhSachLoaiHinhTBDaGan,
      ];
      this.setDanhSachLoaiHinhTBChuaGan([]);
      this.setDanhSachLoaiHinhTBDaGan(temp);
      this.checkAllowEdit(temp)
    },
    checkAllowEdit(data) {
      let allowEditPrice = true
      let loaitb_brcd = [58, 61, 210, 222, 224]

      if (data && data.length > 0) {
        for (let km of data) {
          if (loaitb_brcd.includes(km.loaitb_id)) {
            allowEditPrice = false
            break
          }
        }
      }

      this.setAllowEditPrice(allowEditPrice)
    }
  },
};
</script>

<style>
#Grid1.e-grid .e-gridheader .e-columnheader,
#Grid3.e-grid .e-gridheader .e-columnheader {
  display: none;
}
</style>
