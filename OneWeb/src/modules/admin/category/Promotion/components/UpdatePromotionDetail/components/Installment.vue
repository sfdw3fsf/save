<template>
  <div class="tab-pane active" id="tab5">
    <div class="info-row">
      <div class="key w80">Nhóm Tbị</div>
      <div class="value">
        <div class="select-custom">
          <ejs-dropdownlist
            :dataSource="danhSachNhomTB"
            class="combobox"
            v-model="form.nhomtb_id"
            :fields="{ text: 'NHOM_TBI', value: 'NHOM_TBI_ID' }"
            @filtering="
              onFilteringDropDownList($event, danhSachNhomTB, 'NHOM_TBI')
            "
            :allowFiltering="true"
          >
          </ejs-dropdownlist>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w80">Kiểu bắt đầu</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                ref="cbotgtb_LoaiBD"
                class="combobox"
                popupWidth="250px"
                v-model="form.kieu_batdau"
                :dataSource="danhSachKieubatdau"
                :fields="{ text: 'name', value: 'id' }"
                @filtering="
                  onFilteringDropDownList($event, danhSachKieubatdau, 'name')
                "
                :allowFiltering="true"
              >
              </ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w70">Tháng BĐ</div>
          <div class="value">
            <ejs-numerictextbox
              id="thang_bdtg"
              ref="txttgtb_ThangBDTG"
              format="n0"
              :showSpinButton="false"
              v-model="form.thang_bdtg"
              :value="form.thang_bdtg"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w70">Số tháng</div>
          <div class="value">
            <ejs-numerictextbox
              id="sothang_tg"
              ref="txttgtb_SoThangTG"
              format="n0"
              :showSpinButton="false"
              v-model="form.sothang_tg"
              :value="form.sothang_tg"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
    </div>

    <div class="box-move-select-table">
      <div class="box-col box-form">
        <!-- <div class="legend-title">Loại hình đã gán</div> -->
        <div
          class="grid-ext grid-ext-freeze"
          style="height: 232px; overflow: hidden"
        >
          <ejs-grid
            height="110"
            ref="tableDanhSachTraGopThietBiChiTietDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachTraGopThietBiChiTietDaGan"
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
                field="loai_tbi"
                id="Grid1"
                headerText="Thiết bị đã gán"
                width="120"
                headerTextAlign="center"
                textAlign="Left"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="thang_bdtg"
                id="Grid1"
                headerText="Kiểu bắt đầu"
                width="120"
                format="n0"
                type="number"
                textAlign="center"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="sothang_tg"
                id="Grid1"
                headerText="Tháng.."
                width="120"
                format="n0"
                type="number"
                textAlign="center"
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
            ref="tabledanhSachTraGopThietBiChiTietChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachTraGopThietBiChiTietChuaGan"
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
                field="loai_tbi"
                id="Grid1"
                headerText="Thiết bị chưa gán"
                width="120"
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
  props: ["form"],

  computed: {
    danhSachNhomTB() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachNhomTB"])
      );
    },
    danhSachTraGopThietBiChiTietDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters[
            "promotionDetail/danhSachTraGopThietBiChiTietDaGan"
          ]
        )
      );
    },
    danhSachTraGopThietBiChiTietChuaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters[
            "promotionDetail/danhSachTraGopThietBiChiTietChuaGan"
          ]
        )
      );
    },
    danhSachKieubatdau() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachKieubatdau"]
        )
      );
    },
  },
  mounted() {},
  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachTraGopThietBiDaGan",
      "setDanhSachTraGopThietBiChuaGan",
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachTraGopThietBiChiTietDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachTraGopThietBiChiTietChuaGan];
        const oldDelivered = [...this.danhSachTraGopThietBiChiTietDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.loai_tbi);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.loai_tbi));
        this.setDanhSachTraGopThietBiChuaGan(newNotDelivered);
        this.setDanhSachTraGopThietBiDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let lvwtgtb_TbiDaGan = this.danhSachTraGopThietBiChiTietDaGan;        

      if (lvwtgtb_TbiDaGan.length > 0)
      {
          this.$toast.error("Bạn phải xóa danh sách đã gán trước khi gán lại!");
          return;
      }
      if (!this.form.kieu_batdau)
      {
          this.$toast.error("Hãy nhập kiểu bắt đầu trả góp!");
          this.$refs.cbotgtb_LoaiBD.focusIn();
          return;
      }
      if (!this.form.sothang_tg)
      {this.form.sothang_tg = 0
          // this.$toast.error("Hãy nhập số tháng trả góp!");
          // this.$refs.txttgtb_SoThangTG.focusIn();
          // return;
      }

      let i = this.danhSachKieubatdau.findIndex(x => x.id == this.form.kieu_batdau);
      if(i == 0){
        if (!this.form.thang_bdtg)
        {this.form.thang_bdtg = 0;
            // this.$toast.error("Hãy nhập tháng bắt đầu trả góp!");
            // this.$refs.txttgtb_ThangBDTG.focusIn();
            // return;
        }
      }

      let selectedrecordsChuaGan =
        this.$refs.tabledanhSachTraGopThietBiChiTietChuaGan.getSelectedRecords();        
      if (selectedrecordsChuaGan) {
        let selectedrecordsChuaGanNew = [];

        selectedrecordsChuaGanNew = selectedrecordsChuaGan.map((obj) => {
          obj.loaitbi_id = obj.loaitbi_id;
          obj.loai_bdtg = this.form.kieu_batdau == 0 ? "1": "3";
          obj.thang_bdtg = this.form.thang_bdtg;
          obj.sothang_tg = this.form.sothang_tg; 

          return obj;
        });
        const oldNotDelivered = [...this.danhSachTraGopThietBiChiTietDaGan];
        const oldDelivered = [...this.danhSachTraGopThietBiChiTietChuaGan];

        const newNotDelivered = [
          ...selectedrecordsChuaGanNew,
          ...oldNotDelivered,
        ];
        const arrDelete = selectedrecordsChuaGanNew.map((obj) => obj.loai_tbi);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.loai_tbi));
        this.setDanhSachTraGopThietBiDaGan(newNotDelivered);
        this.setDanhSachTraGopThietBiChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachTraGopThietBiChiTietDaGan,
        ...this.danhSachTraGopThietBiChiTietChuaGan,
      ];
      this.setDanhSachTraGopThietBiChuaGan(temp);
      this.setDanhSachTraGopThietBiDaGan([]);
    },
    handDataToLeftight(e) {
       let lvwtgtb_TbiDaGan = this.danhSachTraGopThietBiChiTietDaGan;        

        if (lvwtgtb_TbiDaGan.length > 0)
        {
            this.$toast.error("Bạn phải xóa danh sách đã gán trước khi gán lại!");
            return;
        }
        if (!this.form.kieu_batdau)
        {
            this.$toast.error("Hãy nhập kiểu bắt đầu trả góp!");
            this.$refs.cbotgtb_LoaiBD.focusIn();
            return;
        }
        if (!this.form.sothang_tg)
        {
            this.$toast.error("Hãy nhập số tháng trả góp!");
            this.$refs.txttgtb_SoThangTG.focusIn();
            return;
        }

        let i = this.danhSachKieubatdau.findIndex(x => x.id == this.form.kieu_batdau);
        if(i == 0){
          if (!this.form.thang_bdtg)
          {
              this.$toast.error("Hãy nhập tháng bắt đầu trả góp!");
              this.$refs.txttgtb_ThangBDTG.focusIn();
              return;
          }
        }

      const newData = danhSachTraGopThietBiChiTietChuaGan.map((obj) => {
        obj.loaitbi_id = obj.loaitbi_id;
        obj.loai_bdtg = this.form.kieu_batdau == 0 ? "1": "3";
        obj.thang_bdtg = this.form.thang_bdtg;
        obj.sothang_tg = this.form.sothang_tg; 

        return obj;
      });
      const temp = [...newData, ...this.danhSachTraGopThietBiChiTietDaGan];
      this.setDanhSachTraGopThietBiDaGan(temp);
      this.setDanhSachTraGopThietBiChuaGan([]);
    },
  },
};
</script>

<style>
</style>