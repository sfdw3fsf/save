<template>
  <div class="tab-pane active" id="tab2">
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w60">Giảm LĐ</div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              content="Tỷ lệ giảm tiền lắp đặt dịch vụ gia tăng"
              target="#tien_kmlddt"
            >
              <ejs-numerictextbox
                id="tien_kmlddt"
                format="n0"
                :showSpinButton="false"
                v-model="form.tyle_giamcuoc_ld"
                :value="form.tyle_giamcuoc_ld"
                class="form-control tright pr-2 fw6 red"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w90">Tiền giảm SD</div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              content="Giảm tiền sử dụng dịch vụ GT"
              target="#tien_kmlddt"
            >
              <ejs-numerictextbox
                id="tien_kmlddt"
                format="n0"
                :showSpinButton="false"
                v-model="form.tien_giamcuoc_sd"
                :value="form.tien_giamcuoc_sd"
                class="form-control tright pr-2 fw6 red"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w80">
            <div class="check-action">
              <input
                type="checkbox"
                class="check"
                v-model="form.isNgay"
              />
              <span class="name mr-1">Ngày KT</span>
            </div>
          </div>

          <date-picker
            style="width: 100%"
            :disabled="!form.isNgay"
            v-model="form.ngay_cn"
            placeholder="dd/mm/yyyy"
            :formatter="momentFormat"
            valueType="format"
          ></date-picker>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w60">Giảm SD </div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              content="Tỷ lệ giảm tiền sử dụng dịch vụ gia tăng"
              target="#tien_kmlddt"
            >
              <ejs-numerictextbox
                id="tien_kmlddt"
                format="n"
                :showSpinButton="false"
                v-model="form.tyle_giamcuoc_sd"
                @change="$emit('onChange')"
                :value="form.tyle_giamcuoc_sd"
                class="form-control tright pr-2 fw6 red"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w90">VAT giảm SD</div>
          <div class="value">
            <ejs-numerictextbox
              id="tien_kmlddt"
              format="n"
              :showSpinButton="false"
              v-model="form.vat_giamcuoc_sd"
              :value="form.vat_giamcuoc_sd"
              class="form-control tright pr-2 fw6 red"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w90">
            <div class="check-action">
              <input
                type="checkbox"
                class="check"
                v-model="form.kieu_km_dvgt"
              />
              <span class="name">Áp dụng từng DVGT {{ nhomDC }}</span>
            </div>
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
            ref="tabledanhSachDichVuGTDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachDichVuGTDaGan"
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
                field="ten_dvgt"
                id="Grid1"
                headerText="Dịch vụ đã gán"
                width="400"
                headerTextAlign="center"
                textAlign="Left"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="tyle_giamcuoc_ld"
                id="Grid1"
                headerText="Giảm LD"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                :filter="{ operator: 'contains' }"
              ></e-column>

              <e-column
                field="tyle_giamcuoc_sd"
                id="Grid1"
                headerText="Giảm SD"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="tien_giamcuoc_sd"
                id="Grid1"
                headerText="Tiền giảm SD"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="vat_giamcuoc_sd"
                id="Grid1"
                headerText="VAT giảm SD"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="ngay_cn"
                id="Grid1"
                headerText="Ngày KT"
                width="120"
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
            ref="tabledanhSachDichVuGTChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachDichVuGTChuaGan"
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
                field="ten_dvgt"
                id="Grid1"
                headerText="Dịch vụ chưa gán"
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
import DatePicker from "vue2-datepicker";
import * as moment from "moment";

export default {
  mixins: [mixin],
  components: { DatePicker },
  props: ["value", "form"],

  data: () => ({
    formatOptions: { type: "date", format: "DD/MM/YYYY" },
    selectionOptionsCheckboxMode: { checkboxMode: "ResetOnRowClick" },
    promotionsCurrentRecordDetailDaGan: null,

    momentFormat: {
      stringify: (date) => {
        return date ? moment(date).format("DD/MM/YYYY") : "";
      },
      parse: (value) => {
        return value ? moment(value, "DD/MM/YYYY").toDate() : null;
      },
    },
  }),
  mounted() {

  },
  computed: {
    nhomDC: {
      get() {
        return this.value;
      },
    },
    danhSachDichVuGTDaGan() {
      return this.$store.getters["promotionDetail/danhSachDichVuGTDaGan"];
    },
    danhSachDichVuGTChuaGan() {
      return this.$store.getters["promotionDetail/danhSachDichVuGTChuaGan"];
    },
  },
  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachDichVuGTDaGan",
      "setDanhSachDichVuGTChuaGan",
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tabledanhSachDichVuGTDaGan.getSelectedRecords();

      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachDichVuGTChuaGan];
        const oldDelivered = [...this.danhSachDichVuGTDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.dichvugt_id);

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.dichvugt_id)
        );
        this.setDanhSachDichVuGTChuaGan(newNotDelivered);
        this.setDanhSachDichVuGTDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan =
        this.$refs.tabledanhSachDichVuGTChuaGan.getSelectedRecords();
      if (
        this.form.kieu_km_dvgt &&
        (this.danhSachDichVuGTDaGan.length > 0 ||
          selectedrecordsChuaGan.length > 1)
      ) {
        this.$toast.error("Bạn chỉ được chọn 1 và chỉ 1 dịch vụ gia tăng");
        return;
      }
      if(this.$root.token.getMaTinh() != 'HCM'){
        if (this.form.tyle_giamcuoc_ld == 0) {
          this.$toast.error(
            "Hãy nhập số tỷ lệ giảm cước lắp đặt dịch vụ gia tăng!"
          );
          return;
        }

        if (this.form.tyle_giamcuoc_sd == 0) {
          this.$toast.error(
            "Hãy nhập số tỷ lệ giảm cước sử dụng dịch vụ gia tăng!"
          );
          return;
        }
        if (this.form.tien_giamcuoc_sd == 0) {
          this.$toast.error(
            "Hãy nhập số tiền giảm cước sử dụng dịch vụ gia tăng!"
          );
          return;
        }

        if (this.form.vat_giamcuoc_sd == 0) {
          this.$toast.error(
            "Hãy nhập số tiền vat giảm cước sử dụng dịch vụ gia tăng!"
          );
          return;
        }
      }


      if (selectedrecordsChuaGan.length == 0) {
        this.$toast.error("Hãy chọn dịch vụ gia tăng!");
        return;
      }
      if (!this.form.isNgay) {
        this.$toast.error("Hãy chọn ngày KT!");
        return;
      }
      // Bạn chỉ được chọn 1 và chỉ 1 dịch vụ gia tăng

      if (selectedrecordsChuaGan) {
        let selectedrecordsChuaGanNew = [];

        selectedrecordsChuaGanNew = selectedrecordsChuaGan.map((obj) => {
          obj.tyle_giamcuoc_ld = this.form.tyle_giamcuoc_ld;
          obj.tyle_giamcuoc_sd = this.form.tyle_giamcuoc_sd;
          obj.tien_giamcuoc_sd = this.form.tien_giamcuoc_sd;

          obj.vat_giamcuoc_sd = this.form.vat_giamcuoc_sd;
          obj.ngay_cn = this.form.ngay_cn;

          return obj;
        });
        const oldNotDelivered = [...this.danhSachDichVuGTDaGan];
        const oldDelivered = [...this.danhSachDichVuGTChuaGan];

        const newNotDelivered = [
          ...selectedrecordsChuaGanNew,
          ...oldNotDelivered,
        ];
        const arrDelete = selectedrecordsChuaGanNew.map(
          (obj) => obj.dichvugt_id
        );

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.dichvugt_id)
        );
        this.setDanhSachDichVuGTDaGan(newNotDelivered);
        this.setDanhSachDichVuGTChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [
        ...this.danhSachDichVuGTDaGan,
        ...this.danhSachDichVuGTChuaGan,
      ];
      this.setDanhSachDichVuGTChuaGan(temp);
      this.setDanhSachDichVuGTDaGan([]);
    },
    handDataToLeftight(e) {
      const temp = [
        ...this.danhSachDichVuGTChuaGan,
        ...this.danhSachDichVuGTDaGan,
      ];
      this.setDanhSachDichVuGTDaGan(temp);
      this.setDanhSachDichVuGTChuaGan([]);
    },
  },
};
</script>
<style>
</style>
