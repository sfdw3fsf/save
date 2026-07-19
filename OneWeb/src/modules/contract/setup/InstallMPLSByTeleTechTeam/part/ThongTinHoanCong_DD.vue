<template>
  <div>
    <div class="title-bg-main padt0 padb0">
      <div class="list-checks">
        <div class="item vcenter">
          <span class="title">Điểm đầu</span>
        </div>
        <div class="item vcenter">Chọn port</div>
        <div class="item vcenter">
          <button
            class="btn btn-second button-not-bg"
            @click="btnTDongOnClick()"
          >
            <span class="-ap icon-check"></span> T. động
          </button>
        </div>
        <div class="item vcenter">
          <button
            class="btn btn-second button-not-bg"
            @click="btnNCongOnClick()"
          >
            <span class="-ap icon-check"></span> N. công
          </button>
        </div>
        <div class="item vcenter dropdown">
          <button
            class="btn btn-main h31"
            ref="DDref"
            @click="setDisplayTable()"
          >
            <span ref="childrenDDref" class="fa fa-angle-down"></span>
          </button>
          <div
            ref="childrenDDrefTable"
            :class="
              formProps.isDisplayTable
                ? 'dropdown-menu padt0 d-block'
                : 'dropdown-menu padt0'
            "
            style="width: 520px"
          >
            <vue-table
              ref="vueTable"
              v-model="formProps.table.currentRecord"
              :options="formProps.table.options"
              :config="formProps.table.config"
              :loading="formProps.table.loading"
              :unique="'PHANLOAI_ID'"
              :multiple="false"
            ></vue-table>
          </div>
        </div>
        <div class="item red">Liên hệ: {{ formProps.lblLienHe_Dau }}</div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSoAoDau"
              :labelWidth="'80'"
              :highlight="true"
              label="Số ảo"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboThietBiDau"
              :labelWidth="'70'"
              :options="options.options_thietBi"
              id="loaiHinh"
              labelField="loaihinh_tb"
              valueField="loaitb_id"
              label="Thiết bị"
            ></vue-select>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSvlan_Dau"
              :disable="formProps.isTxtSvlan_Dau"
              :labelWidth="'80'"
              label="Svlan"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCvlan_Dau"
              :disable="formProps.isTxtCvlan_Dau"
              :labelWidth="'70'"
              label="Cvlan"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtAdsl_cap_Dau"
              :disable="formProps.isTxtAdsl_cap_Dau"
              :labelWidth="'80'"
              label="Adsl cáp"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtAdsl_port_Dau"
              :disable="formProps.isTxtAdsl_port_Dau"
              :labelWidth="'70'"
              label="Adsl port"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtNasport_id_Dau"
              :disable="formProps.isTxtNasport_id_Dau"
              :labelWidth="'80'"
              label="Nasport ID"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtVrf_Dau"
              :disable="formProps.isTxtVrf_Dau"
              :labelWidth="'70'"
              label="Vrf/Vfi"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-input
          v-model="formProps.txtIpDslam_Dau"
          :disable="formProps.isTxtIpDslam_Dau"
          :labelWidth="'80'"
          label="Ip Dslam"
          :checkbox="false"
        >
        </vue-input>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTBi_Dau"
              :disable="formProps.isTxtTBi_Dau"
              :labelWidth="'80'"
              :highlight="true"
              label="TB t.dẫn"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtIpwan_Dau"
              :disable="formProps.isTxtIpwan_Dau"
              :labelWidth="'70'"
              label="Ip Wan"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTimeSlot_Dau"
              :disable="formProps.isTxtTimeSlot_Dau"
              :labelWidth="'80'"
              :highlight="true"
              label="TimeSlot"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtLink_Dau"
              :disable="formProps.isTxtLink_Dau"
              :labelWidth="'70'"
              :highlight="true"
              label="Link"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-input
          v-model="formProps.txtDiaChiDau"
          :labelWidth="'80'"
          label="Địa chỉ mới"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtLyDoTra_Dau"
          :labelWidth="'80'"
          label="Lý do trả"
          :checkbox="false"
        >
        </vue-input>

        <div class="info-row">
          <div class="key w80">ND TH <span class="required">*</span></div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="btnNDTHOnClick()">
                <span class="nc-icon-glyph ui-1_edit-76 f20"></span>
              </button>
              <vue-input
                v-model="formProps.txtNoiDungTH"
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
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSerial_Dau"
              :disable="formProps.TxtSerial_Dau"
              :labelWidth="'90'"
              label="Serial"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboLoaiCap_Dau"
              :labelWidth="'100'"
              :options="options.options_loaiCap"
              id="loaiHinh"
              labelField="LOAICAP"
              valueField="LOAICAP_ID"
              label="Loại cáp"
            ></vue-select>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCapGoc_Dau"
              :disable="formProps.isTxtCapGoc_Dau"
              :labelWidth="'90'"
              label="Cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCapNgon_Dau"
              :disable="formProps.isTxtCapNgon_Dau"
              :labelWidth="'100'"
              label="Cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuCapGoc_Dau"
              :disable="formProps.isTxtTuCapGoc_Dau"
              :labelWidth="'90'"
              label="Tủ cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuCapNgon_Dau"
              :disable="formProps.isTxtTuCapNgon_Dau"
              :labelWidth="'100'"
              label="Tủ cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtDoiCapGoc_Dau"
              :disable="formProps.isTxtDoiCapGoc_Dau"
              :labelWidth="'90'"
              label="Đôi cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtDoiCapNgon_Dau"
              :disable="formProps.isTxtDoiCapNgon_Dau"
              :labelWidth="'100'"
              label="Đôi cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtLienTu_Dau"
              :disable="formProps.isTxtLienTu_Dau"
              :labelWidth="'90'"
              label="Liên tủ"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtHopCapNgon_Dau"
              :disable="formProps.isTxtHopCapNgon_Dau"
              :labelWidth="'100'"
              label="Hộp cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSoMetDay_Dau"
              :disabled="formProps.TxtSoMetDay_Dau"
              :labelWidth="'90'"
              label="Cự ly"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuyenCap_Dau"
              :disabled="formProps.TxtTuyenCap_Dau"
              :labelWidth="'100'"
              label="Tuyến cáp"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-select
          v-model="formProps.cboDonViDau"
          :labelWidth="'90'"
          :options="options.options_donVi"
          id="donVi"
          labelField="ten_dv"
          valueField="donvi_id"
          label="Đơn vị"
        ></vue-select>
        <vue-input
          v-model="formProps.txtDiaChiDau_Cu"
          :labelWidth="'90'"
          label="Địa chỉ cũ"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtNoiDungTra_Dau"
          :labelWidth="'90'"
          label="Nội dung trả"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtNDGiao"
          :labelWidth="'90'"
          label="ND giao"
          :checkbox="false"
        >
        </vue-input>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  props: ["formProps", "options"],

  watch: {},
  computed: {},
  created() {},
  mounted() {},
  methods: {
    btnTDongOnClick() {
      alert("btnTDongOnClick");
    },
    btnNCongOnClick() {
      alert("btnNCongOnClick");
    },
    btnNDTHOnClick() {
      this.$emit("btnNDTHOnClick");
    },
    setDisplayTable() {
      console.log("setDisplayTableDD");
      this.$emit("display-table");
    },
  },
};
</script>
