<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title">
        <span class="icon one-notepad"></span> Cập nhật khoản mục thanh toán cho
        loại hình thuê bao
      </div>
      <div
        class="close -ap icon-close"
        @click="closeModal"
        data-dismiss="modal"
      ></div>
    </div>
    <div class="list-actions-top">
      <ul class="list mount">
        <li>
          <a
            class="mount"
            @click="btnGhiLaiOnClick"
            :class="
              !formProps.dichVuVTId || !formProps.loaiHinhTBId ? 'disabled' : ''
            "
          >
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin dịch vụ VT và loại hình TB</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Dịch vụ VT<span class="red"> *</span></div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    class="combobox"
                    v-model="formProps.dichVuVTId"
                    :dataSource="dichVuVT"
                    :fields="{ text: 'TEN_DVVT', value: 'DICHVUVT_ID' }"
                    @filtering="
                      onFilteringDropDownList($event, dichVuVT, 'TEN_DVVT')
                    "
                    :allowFiltering="true"
                  >
                  </ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Loại hình TB<span class="red"> *</span></div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    class="combobox"
                    v-model="formProps.loaiHinhTBId"
                    :dataSource="dsloaiHinhTB"
                    :enabled="formProps.loaiHinhTBId ? true :  false"
                    :fields="{ text: 'LOAIHINH_TB', value: 'LOAITB_ID' }"
                    @filtering="
                      onFilteringDropDownList(
                        $event,
                        dsloaiHinhTB,
                        'LOAIHINH_TB'
                      )
                    "
                    :allowFiltering="true"
                  >
                  </ejs-dropdownlist>
                </div>
              </div>
            </div>
            <!-- <vue-select
              v-model="formProps.loaiHinhTBId"
              :required="true"
              :labelWidth="'90'"
              :disable="formProps.loaiHinhTBId ? false : true"
              :options="dsloaiHinhTB"
              id="loaiHinhTB"
              labelField="LOAIHINH_TB"
              valueField="LOAITB_ID"
              label="Loại hình TB"
            ></vue-select> -->
          </div>
        </div>
      </div>
      <div class="box-move-select-table">
        <div class="box-col box-form">
          <div class="legend-title">Bao gồm các hình thức TT</div>
          <div class="table-content">
            <VueTables
              v-model="table_hinhthucTT.currentRecord"
              :options="table_hinhthucTT.options"
              :config="table_hinhthucTT.config"
              :loading="table_hinhthucTT.loading"
              :unique="'ID'"
              @input="onDataCheckBoxTable1"
              :multiple="true"
            ></VueTables>
          </div>
        </div>
        <div class="actions">
          <button
            class="btn"
            @click="onCheckBoxTable1Remove"
            :disabled="
              this.isCheckTable1.length == 0 ||
              this.isCheckTable1.length == null
                ? true
                : false
            "
          >
            <span class="fa fa-angle-right"></span>
          </button>
          <button
            class="btn"
            @click="onAllTable1toTable2"
            :disabled="this.table_hinhthucTT.options == 0 ? true : false"
          >
            <span class="fa fa-angle-double-right"></span>
          </button>
          <button
            class="btn"
            @click="onCheckBoxTable2ToTable1"
            :disabled="
              this.isCheckTable2.length == 0 ||
              this.isCheckTable2.length == null
                ? true
                : false
            "
          >
            <span class="fa fa-angle-left"></span>
          </button>
          <button
            class="btn"
            @click="onAllTable2toTable1"
            :disabled="
              this.table_danhSachhinhthucTT.options == 0 ? true : false
            "
          >
            <span class="fa fa-angle-double-left"></span>
          </button>
        </div>
        <div class="box-col box-form">
          <div class="legend-title">Danh sách hình thức TT</div>
          <div class="table-content">
            <VueTables
              v-model="table_danhSachhinhthucTT.currentRecord"
              :options="table_danhSachhinhthucTT.options"
              :config="table_danhSachhinhthucTT.config"
              :loading="table_danhSachhinhthucTT.loading"
              :unique="'ID'"
              :multiple="true"
              @input="onDataCheckBoxTable2"
            ></VueTables>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { createLogger } from "vuex";
import api from "../MapPaymentAccountToSubsTypeAPI";
import VueTables from "./VueTable.vue";
import { Query } from "@syncfusion/ej2-data";

export default {
  data() {
    return {
      isButton: false,
      formProps: {
        dichVuVTId: "",
        loaiHinhTBId: "",
      },
      dsloaiHinhTB: [],
      isCheckTable2: [],
      isCheckTable1: [],
      table_hinhthucTT: {
        currentRecord: null,
        data: null,
        loading: false,
        options: [],
        config: [
          {
            label: "ID",
            filterAble: true,
            key: "khoanmuctt_id",
            width: "90px",
          },
          {
            label: "Tên khoản mục TT",
            filterAble: true,
            key: "tenkhoanmuc",
          },
        ],
        all_data: [],
      },
      table_danhSachhinhthucTT: {
        currentRecord: null,
        data: null,
        loading: false,
        options: [],
        config: [
          {
            label: "ID",
            filterAble: true,
            key: "khoanmuctt_id",
            width: "90px",
          },
          {
            label: "Tên khoản mục TT",
            filterAble: true,
            key: "tenkhoanmuc",
          },
        ],
        all_data: [],
      },
    };
  },
  components: { VueTables },
  created() {
    this.$root.showLoading(true);
    Promise.all([
      this.$store.dispatch("dvvtCommon/getDanhSachDichVuVienThong"),
      this.$store.dispatch("lhtbCommon/getDanhSachLoaiHinhTB"),
    ]).then((values) => {
      this.$root.showLoading(false);
    });
  },
  mounted() {
    this.$root.$on("bv::modal::shown", (bvEvent, modalId) => {
      var els = document.getElementsByClassName("modal-content");
      Array.prototype.forEach.call(els, function (el) {
        // Do stuff here
        el.removeAttribute("tabindex");
      });
    });
  },
  computed: {
    dichVuVT() {
      return this.$store.state.dvvt.dvvtCommon.danhSachDichVuVienThong;
    },
  },
  watch: {
    "formProps.loaiHinhTBId"(newValue, oldValue) {
      if (newValue) this.layDuLieuKhoanMuc(newValue);
    },
    "formProps.dichVuVTId"(newValue, oldValue) {
      this.table_hinhthucTT.options = [];
      this.table_danhSachhinhthucTT.options = [];
      const dataLHTB =
        this.$store.getters["lhtbCommon/danhSachLoaiHinhThueBaoGetter"];
      if (dataLHTB.length > 0) {
        this.dsloaiHinhTB = dataLHTB.filter((e) => e.DICHVUVT_ID == newValue);
        this.formProps.loaiHinhTBId =
          this.dsloaiHinhTB.length > 0 && this.dsloaiHinhTB[0]
            ? this.dsloaiHinhTB[0].LOAITB_ID
            : null;
      }
    },
    dichVuVT() {
      this.formProps.dichVuVTId =
        this.dichVuVT.length > 0 && this.dichVuVT[0]
          ? this.dichVuVT[0].DICHVUVT_ID
          : null;
    },
  },
  methods: {
    btnGhiLaiOnClick() {
      let dataMap = this.table_hinhthucTT.options.map(
        (obj) => obj.khoanmuctt_id
      );
      const data = {
        loaitb_id: this.formProps.loaiHinhTBId,
        khoanmuctt_id_arr: `[${dataMap}]`,
      };

      this.postDuLieuKhoanMuc(data);
    },
    onFilteringDropDownList(e, dataSource, name) {
      var query = new Query();
      query =
        e.text !== "" ? query.where(name, "contains", e.text, true) : query;
      e.updateData(dataSource, query);
    },
    onAllTable2toTable1() {
      this.table_hinhthucTT.options = [
        ...this.table_hinhthucTT.options,

        ...this.table_danhSachhinhthucTT.options,
      ];
      this.table_danhSachhinhthucTT.options = [];
      this.isCheckTable1 = [];
      this.isCheckTable2 = [];
    },
    onAllTable1toTable2() {
      this.table_danhSachhinhthucTT.options = [
        ...this.table_hinhthucTT.options,
        ...this.table_danhSachhinhthucTT.options,
      ];
      this.table_hinhthucTT.options = [];
      this.isCheckTable1 = [];
      this.isCheckTable2 = [];
    },
    closeModal() {
      this.$emit("form-close");
    },
    layDuLieuKhoanMuc(id) {
      this.table_hinhthucTT.loading = true;
      this.table_danhSachhinhthucTT.loading = true;

      const data = {
        loaitb_id: id,
      };
      api
        .layDanhSachKMTTLHTB(this.axios, data)
        .then((response) => {
          this.table_hinhthucTT.options = response.data.data.gan;
          this.table_danhSachhinhthucTT.options = response.data.data.chua_gan;
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        })
        .finally(() => {
          this.table_hinhthucTT.loading = false;
          this.table_danhSachhinhthucTT.loading = false;
        });
    },
    postDuLieuKhoanMuc(data) {
      api
        .spGhiLaiKMTTLHTB(this.axios, data)
        .then((response) => {
          if (response.data.message == "Success") {
            this.$toast.success("Cập nhật dịch vụ thành công");
            this.$store.dispatch("dvvtCommon/getDanhSachDichVuVienThong");
            this.$store.dispatch("lhtbCommon/getDanhSachLoaiHinhTB");
            this.layDuLieuKhoanMuc(this.formProps.loaiHinhTBId)
            this.table_danhSachhinhthucTT.options = [];
            this.table_hinhthucTT.options = [];
            this.isCheckTable2 = [];
          }
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    onCheckBoxTable1Remove() {
      if (this.isCheckTable1) {
        this.table_danhSachhinhthucTT.options.unshift(...this.isCheckTable1);
        const arrDelete = this.isCheckTable1.map((obj) => obj.khoanmuctt_id);
        const res = this.table_hinhthucTT.options.filter(
          (f) => !arrDelete.includes(f.khoanmuctt_id)
        );
        this.table_hinhthucTT.options = res;
        this.isCheckTable1 = [];
      }
    },
    onCheckBoxTable2ToTable1() {
      if (this.isCheckTable2) {
        this.table_hinhthucTT.options.unshift(...this.isCheckTable2);
        const arrDelete = this.isCheckTable2.map((obj) => obj.khoanmuctt_id);
        const res = this.table_danhSachhinhthucTT.options.filter(
          (f) => !arrDelete.includes(f.khoanmuctt_id)
        );
        this.table_danhSachhinhthucTT.options = res;
        this.isCheckTable2 = [];
      }
    },

    onDataCheckBoxTable2(check) {
      this.isCheckTable2 = check;
    },
    onDataCheckBoxTable1(check) {
      this.isCheckTable1 = check;
    },
  },
};
</script>


<style scoped>
.mount {
  cursor: pointer !important;
}

.mount:hover {
  cursor: pointer;
}
</style>