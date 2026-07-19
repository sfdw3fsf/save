<template>
  <div class="box-form" style="    height: 380px;">
    <div class="legend-title">Thông tin giao việc</div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <vue-date
          ref="ngayGV"
          v-model="formProps.ngayGV"
          :labelWidth="'100'"
          :required="true"
          label="Ngày GV"
          :checkbox="true"
        ></vue-date>
      </div>
      <div class="col-sm-6 col-12">
        <vue-date
          ref="ngayHT"
          v-model="formProps.ngayHT"
          :labelWidth="'100'"
          :required="true"
          label="Ngày HT"
          :checkbox="true"
        ></vue-date>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <vue-select
          v-model="formProps.nguoiGV"
          :required="true"
          :labelWidth="'100'"
          :options="options.nguoiGV"
          id="nguoiGV"
          labelField="ten_nv"
          valueField="nhanvien_id"
          label="Người GV"
        ></vue-select>
      </div>
      <div class="col-sm-6 col-12">
        <vue-input
          v-model="formProps.nhanVienYC"
          :labelWidth="'100'"
          label="Nhân viên YC"
          :checkbox="false"
        >
        </vue-input>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-12 col-12">
        <div class="info-row">
          <div class="key w100">Nội dung TH </div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="btnNoiDungTHClick()">
                <span class="nc-icon-glyph ui-1_edit-76 f16"></span>
              </button>
              <vue-input
                v-model="formProps.noiDungTH"
                :required="false"
                :labelWidth="'0'"
                label=""
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-12 col-12">
        <vue-select
          v-model="formProps.lyDoTra"
          ref="lyDoTra"
          :validate="['required']"
          :labelWidth="'100'"
          :options="options.lyDoTra"
          id="loaiHD"
          labelField="lydotra"
          valueField="lydotra_id"
          label="Lý do trả"
        ></vue-select>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-12 col-12">
        <vue-input
          v-model="formProps.noiDungTra"
          ref="noiDungTra"
          :validate="['required']"
          :labelWidth="'100'"
          label="Nội dung trả"
          :checkbox="false"
        >
        </vue-input>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-12 col-12">
        <div
          style="
            max-height: 115px;
            min-height: 115px !important;
            overflow: scroll;
          "
        >
          <vue-table
            ref="TTGV_DSNV"
            v-model="formProps.table_DSNV.currentRecord"
            :options="formProps.table_DSNV.options"
            :config="formProps.table_DSNV.config"
            :loading="formProps.table_DSNV.loading"
            :unique="'nhiemvu_id'"
            :multiple="false"
            @input="(e) => $emit('input', e)"
          >
          </vue-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import utilsJS from "../UtilsJS";
import api from "../InterDividePointInstallTSLAPI";
export default {
  components: {},
  props: ["formProps"],
  data: () => ({
    options: {
      nguoiGV: [],
      lyDoTra: [],
    },
  }),
  mounted() {
    this.getDanhSachNhanVienTheoDonVi();
    // this.getDanhSachLyDoTra();
    this.formProps.nguoiGV = this.$root.token.getNhanVienID();
    this.formProps.ngayGV = utilsJS.convertDateTimeToString(new Date());
    this.formProps.ngayHT = utilsJS.convertDateTimeToString(new Date());
  },
  watch: {
    "formProps.lyDoTra"(newValue, oldValue) {
      this.$emit("selectLyDoTra", newValue);
    },
    "formProps.loaiHinh"(newValue, oldValue) {
      if(newValue)
        this.getDanhSachLyDoTra();
    },
  },
  methods: {
    async getDanhSachNhanVienTheoDonVi() {
      this.options.nguoiGV = await this.$store.dispatch(
        "nhanVienTheoDonViCommon/getDanhSachNhanVienTheoDonVi"
      );
    },
    async getDanhSachLyDoTra() {
      const data = await this.$store.dispatch(
        "lyDoTraCommon/getDanhSachLyDoTra",
        { loaitb_id: this.formProps.loaiHinh }
      );

      this.options.lyDoTra = data || [];
    },
    btnNoiDungTHClick() {
      if (utilsJS.isNullOrEmpty(this.formProps.phieu_id)) {
        this.$toast.error("Vui lòng chọn một thuê bao để thực hiện");
        return false;
      }
      if (utilsJS.isNullOrEmpty(this.formProps.noiDungTH)) {
        this.$toast.error("Vui lòng nhập Nội dung TH");
        return false;
      }

      api
        .updateNoiDungTH(this.axios, {
          noidung: this.formProps.noiDungTH.trim(),
          phieu_id: this.formProps.phieu_id,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            this.$toast.success("Cập nhật Nội dung TH thành công");
            console.log(response.data);
            // this.$toast.error(response.data.message);
          } else {
            console.log(response.data);
            // this.$toast.error(response.data.message);
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
        })
        .catch((error) => {
          console.log(error.response);
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
  },
};
</script>