<template>
  <div class="tab-pane active" id="tab3">
    <div class="row">
      <div class="col-sm-5 col-12">
        <div class="info-row">
          <div class="key w70">Nhóm Tbị</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                :dataSource="danhSachNhomTB"
                class="combobox"
                popupWidth="480px"
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
      </div>
      <div class="col-sm-7 col-12">
        <div class="info-row">
          <div class="key w70">Nguồn</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                popupWidth="410px"
                ref="cboNguonKMTB"
                :dataSource="danhSachNguonKM"
                class="combobox"
                v-model="form.nguonkm_id"
                :fields="{ text: 'ten_nguon', value: 'nguonkm_id' }"
                @filtering="
                  onFilteringDropDownList($event, danhSachNguonKM, 'ten_nguon')
                "
                :allowFiltering="true"
              >
              </ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w70">Tặng trước</div>
          <div class="value">
            <ejs-numerictextbox
              id="tang_truoc"
              ref="txtTangTruoc"
              format="n"
              :showSpinButton="false"
              v-model="form.tang_truoc"
              :value="form.tang_truoc"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12" v-show="!form.tyle">
        <div class="info-row">
          <div class="key w70">Tiền KM</div>
          <div class="value">
            <ejs-numerictextbox
              id="tien_km"
              ref="txtKmaiTbi"
              format="n"
              @change="$emit('onChange')"
              :showSpinButton="false"
              v-model="form.tien_km"
              
              :value="form.tien_km"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
      <div class="col-12" :class="form.tyle ? 'col-sm-8' : 'col-sm-4'">
        <div class="info-row">
          <span v-show="!form.tyle">
            <div class="key w70">Vat KM</div>
            <div class="value">
              <ejs-numerictextbox
                id="vat_km"
                ref="txtVatKMTbi"
                format="n"
                :showSpinButton="false"
                v-model="form.vat_km"
                :value="form.vat_km"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
            </div>
          </span>

          <span style="display: contents" v-show="form.tyle">
            <div class="key w70">Tỷ lệ</div>
            <div class="value">
              <ejs-numerictextbox
                id="tyleKMTB"
                ref="txtTyLeKM"
                format="n1"
                :max="100"
                :showSpinButton="false"
                v-model="form.tyleKMTB"
                :value="form.tyleKMTB"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
              <!-- <input type="number" v-model="form.tyleKMTB" class="form-control highlight tright" /> -->
            </div>
          </span>
          <div class="value w30 padt7 nowrap">
            <div class="check-action red">
              <input type="checkbox" class="check" v-model="form.tyle" />
              <span class="name">Tỷ lệ</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w70">SL Tháng</div>
          <div class="value">
            <ejs-numerictextbox
              id="sl_thang"
              ref="txtSLTangTruoc"
              format="n"
              :showSpinButton="false"
              v-model="form.sl_thang"
              :value="form.sl_thang"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
      <div class="col-sm-8 col-12">
        <div class="info-row">
          <div class="key w70">Tháng BĐ</div>
          <div class="value">
            <ejs-numerictextbox
              id="thang_db"
              ref="txtThangBD"
              format="n"
              :showSpinButton="false"
              v-model="form.thang_db"
              :value="form.thang_db"
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
            ref="tableDanhSachKMTBDaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachKMTBDaGan"
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
                field="tien_km"
                id="Grid1"
                headerText="Tiền KM"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                format="n"
                type="number"
                :filter="{ operator: 'contains' }"
              ></e-column>

              <e-column
                field="vat_km"
                id="Grid1"
                headerText="Vat KM"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                format="n"
                type="number"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="tyle_km"
                id="Grid1"
                headerText="Tỷ lệ KM"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                format="n1"
                type="number"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="nguonkm_id"
                id="Grid1"
                headerText="Nguồn ID"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="tang_truoc"
                id="Grid1"
                headerText="Tặng trước"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                format="n"
                type="number"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="huong_tt"
                id="Grid1"
                headerText="SL tặng"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                format="n"
                type="number"
                :filter="{ operator: 'contains' }"
              ></e-column>
              <e-column
                field="thang_bd_tt"
                id="Grid1"
                headerText="BĐ tặng"
                width="120"
                headerTextAlign="center"
                textAlign="Right"
                format="n"
                type="number"
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
            ref="tableDanhSachKMTBChuaGan"
            clipMode="EllipsisWithTooltip"
            :dataSource="danhSachKMTBChuaGan"
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
  props: ["form"],
  data: () => ({
    selectionOptionsCheckboxMode: { checkboxMode: "ResetOnRowClick" },
  }),
  computed: {
    danhSachNhomTB() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachNhomTB"])
      );
    },
    danhSachNguonKM() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachNguonKM"])
      );
    },
    danhSachKMTBDaGan() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachKMTBDaGan"])
      );
    },
    danhSachKMTBChuaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachKMTBChuaGan"]
        )
      );
    },
  },
  mounted() {
 
  },
  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachKMTBDaGan",
      "setDanhSachKMTBChuaGan",
    ]),

    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tableDanhSachKMTBDaGan.getSelectedRecords();
      if (selectedrecordsDaGan) {
        const oldNotDelivered = [...this.danhSachKMTBChuaGan];
        const oldDelivered = [...this.danhSachKMTBDaGan];

        const newNotDelivered = [...selectedrecordsDaGan, ...oldNotDelivered];
        const arrDelete = selectedrecordsDaGan.map((obj) => obj.loai_tbi);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.loai_tbi));
        this.setDanhSachKMTBChuaGan(newNotDelivered);
        this.setDanhSachKMTBDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove() {
      let selectedrecordsChuaGan = this.$refs.tableDanhSachKMTBChuaGan.getSelectedRecords();
      if (selectedrecordsChuaGan.length == 0) {
        this.$toast.error("Bạn chưa chọn thiết bị!");
        return;
      }
      if (!this.form.nguonkm_id || this.form.nguonkm_id == 0) {
        this.$toast.error("Hãy nhập nguồn thiết bị!");
        return;
      }
      if (!this.form.tien_km && this.form.tyle == false) {
        this.$toast.error("Hãy nhập tiền khuyến mại thiết bị!");
        this.$refs.txtKmaiTbi.focus();
        return;
      }
      if (!this.form.tyleKMTB && this.form.tyle == true) {
        this.$toast.error("Hãy nhập tỷ lệ khuyến mại thiết bị!");
        this.$refs.txtTyLeKM.focus();
        return;
      }
      if (!this.form.vat_km && this.form.tyle == false) {
        this.$toast.error("Hãy nhập Vat khuyến mại thiết bị!");
        this.$refs.txtVatKMTbi.focus();
        return;
      }

      if(this.form.tang_truoc*1  > 0 ){
        if (!this.form.sl_thang) {
          this.$toast.error("Hãy nhập số tháng được tặng trước!");
          this.$refs.txtSLTangTruoc.focus();
          return;
        }
        if (!this.form.thang_db) {
          this.$toast.error("Hãy nhập tháng bắt đầu được tính tiền tặng trước!");
          this.$refs.txtThangBD.focus();
          return;
        }
      }

      if (selectedrecordsChuaGan) {
        let selectedrecordsChuaGanNew = [];

        selectedrecordsChuaGanNew = selectedrecordsChuaGan.map((obj) => {
          obj.nguonkm_id = this.form.nguonkm_id;
          obj.sl_thang = this.form.sl_thang ? this.form.sl_thang : 0;
          obj.tang_truoc = this.form.tang_truoc ? this.form.tang_truoc : 0;
          obj.huong_tt = obj.sl_thang;
          obj.thang_db = this.form.thang_db ? this.form.thang_db : 0;
          obj.thang_bd_tt = obj.thang_db;
          obj.tien_km = this.form.tien_km ? this.form.tien_km : 0;
          obj.vat_km = this.form.vat_km ? this.form.vat_km : 0; 
          obj.tyleKMTB = this.form.tyleKMTB ? this.form.tyleKMTB : 0;
          obj.tyle_km = obj.tyleKMTB;
          if(this.form.tyle == true){ 
            obj.tien_km = 0;
            obj.vat_km = 0;
          }
          else{
            obj.tyle_km = 0
          }          

          return obj;
        });
        const oldNotDelivered = [...this.danhSachKMTBDaGan];
        const oldDelivered = [...this.danhSachKMTBChuaGan];

        const newNotDelivered = [
          ...selectedrecordsChuaGanNew,
          ...oldNotDelivered,
        ];
        const arrDelete = selectedrecordsChuaGanNew.map((obj) => obj.loai_tbi);

        const res = oldDelivered.filter((f) => !arrDelete.includes(f.loai_tbi));
        this.setDanhSachKMTBDaGan(newNotDelivered);
        this.setDanhSachKMTBChuaGan(res);
      }
    },
    handDataToRight(e) {
      const temp = [...this.danhSachKMTBDaGan, ...this.danhSachKMTBChuaGan];
      this.setDanhSachKMTBChuaGan(temp);
      this.setDanhSachKMTBDaGan([]);
    },
    handDataToLeftight(e) {
      if (!this.form.nguonkm_id || this.form.nguonkm_id == 0) {
        this.$toast.error("Hãy nhập nguồn thiết bị!");
        return;
      }

      if (selectedrecordsChuaGan.length == 0) {
        this.$toast.error("Bạn chưa chọn thiết bị!");
        return;
      }
      if (this.form.nguonkm_id == 0) {
        this.$toast.error("Hãy nhập nguồn thiết bị!");
        return;
      }
       if (!this.form.tien_km && this.form.tyle == false) {
        this.$toast.error("Hãy nhập tiền khuyến mại thiết bị!");
        this.$refs.txtKmaiTbi.focus();
        return;
      }
      if (!this.form.tyleKMTB && this.form.tyle == true) {
        this.$toast.error("Hãy nhập tỷ lệ khuyến mại thiết bị!");
        this.$refs.txtTyLeKM.focus();
        return;
      }
      if (!this.form.vat_km && this.form.tyle == false) {
        this.$toast.error("Hãy nhập Vat khuyến mại thiết bị!");
        this.$refs.txtVatKMTbi.focus();
        return;
      }

      if(this.form.tang_truoc*1  > 0 ){
        if (!this.form.sl_thang) {
          this.$toast.error("Hãy nhập số tháng được tặng trước!");
          this.$refs.txtSLTangTruoc.focus();
          return;
        }
        if (!this.form.thang_db) {
          this.$toast.error("Hãy nhập tháng bắt đầu được tính tiền tặng trước!");
          this.$refs.txtThangBD.focus();
          return;
        }
      }

      const newKMTB = this.danhSachKMTBChuaGan.map((obj) => {
        obj.nguonkm_id = this.form.nguonkm_id;
        obj.sl_thang = this.form.sl_thang ? this.form.sl_thang : 0;
        obj.tang_truoc = this.form.tang_truoc ? this.form.tang_truoc : 0;
        obj.huong_tt = obj.sl_thang;
        obj.thang_db = this.form.thang_db ? this.form.thang_db : 0;
        obj.thang_bd_tt = obj.thang_db;
        obj.tien_km = this.form.tien_km ? this.form.tien_km : 0;
        obj.vat_km = this.form.vat_km ? this.form.vat_km : 0; 
        obj.tyleKMTB = this.form.tyleKMTB ? this.form.tyleKMTB : 0;
        obj.tyle_km = obj.tyleKMTB;
        if(this.form.tyle == true){ 
          obj.tien_km = 0;
          obj.vat_km = 0;
        }
        else{
          obj.tyle_km = 0
        } 

        return obj;
      });

      const temp = [...newKMTB, ...this.danhSachKMTBDaGan];
      this.setDanhSachKMTBDaGan(temp);
      this.setDanhSachKMTBChuaGan([]);
    },
  },
};
</script>

<style>
</style>