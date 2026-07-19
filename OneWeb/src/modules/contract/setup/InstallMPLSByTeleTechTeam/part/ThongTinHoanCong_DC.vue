<template>
  <div>
    <div class="title-bg-main padt0 padb0">
      <div class="list-checks">
        <div class="item vcenter">
          <span class="title">Điểm cuối</span>
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
            ref="DCref"
            @click="setDisplayTable()"
          >
            <span class="fa fa-angle-down" ref="childrenDCref"></span>
          </button>
          <div
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
        <div class="item red">Liên hệ: {{ formProps.lblLienHe_Cuoi }}</div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSoAoCuoi"
              
              :labelWidth="'95'"
              :highlight="true"
              label="Số ảo"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboThietBiCuoi"
              
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
              v-model="formProps.txtSvlan_Cuoi"
              :disable="formProps.isTxtSvlan_Cuoi"
              
              :labelWidth="'95'"
              label="Svlan"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCvlan_Cuoi"
              :disable="formProps.isTxtCvlan_Cuoi"
              
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
              v-model="formProps.txtAdsl_cap_Cuoi"
              :disable="formProps.isTxtAdsl_cap_Cuoi"
              
              :labelWidth="'95'"
              label="Adsl cáp"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtAdsl_port_Cuoi"
              :disable="formProps.isTxtAdsl_port_Cuoi"

              
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
              v-model="formProps.txtNasport_id_Cuoi"
              :disable="formProps.isTxtNasport_id_Cuoi"
              
              :labelWidth="'95'"
              label="Nasport ID"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtVrf_Cuoi"
              :disable="formProps.isTxtVrf_Cuoi"
              
              :labelWidth="'70'"
              label="Vrf/Vfi"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-input
          v-model="formProps.txtIpDslam_Cuoi"
              :disable="formProps.isTxtIpDslam_Cuoi"
          
          :labelWidth="'95'"
          label="Ip Dslam"
          :checkbox="false"
        >
        </vue-input>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTbi_Cuoi"
              :disable="formProps.isTxtTbi_Cuoi"
              
              :labelWidth="'95'"
              :highlight="true"
              label="TB t.dẫn"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtIpwan_Cuoi"
              :disable="formProps.isTxtIpwan_Cuoi"
              
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
              v-model="formProps.txtTimeSlot_Cuoi"
              :disable="formProps.isTxtTimeSlot_Cuoi"
              
              :labelWidth="'95'"
              :highlight="true"
              label="TimeSlot"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtLink_Cuoi"
              :disable="formProps.isTxtLink_Cuoi"
              
              :labelWidth="'70'"
              :highlight="true"
              label="Link"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-input
          v-model="formProps.txtDiaChiCuoi"
          
          :labelWidth="'95'"
          label="Địa chỉ mới"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtLyDoTra_Cuoi"
          
          :labelWidth="'95'"
          label="Lý do trả"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtSoSoiQuang"
          
          :labelWidth="'95'"
          label="Số sợi quang"
          :checkbox="false"
        >
        </vue-input>
      </div>
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSerial_Cuoi"
              :disabled="formProps.isTxtSerial_Cuoi"
              
              :labelWidth="'90'"
              label="Serial"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboLoaiCap_Cuoi"
              
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
              v-model="formProps.txtCapGoc_Cuoi"
              :disable="formProps.isTxtCapGoc_Cuoi"
              
              :labelWidth="'90'"
              label="Cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCapNgon_Cuoi"
              :disable="formProps.isTxtCapNgon_Cuoi"
              
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
              v-model="formProps.txtTuCapGoc_Cuoi"
              :disable="formProps.isTxtTuCapGoc_Cuoi"
              
              :labelWidth="'90'"
              label="Tủ cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuCapNgon_Cuoi"
              :disable="formProps.isTxtTuCapNgon_Cuoi"
              
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
              v-model="formProps.txtDoiCapGoc_Cuoi"
              :disable="formProps.isTxttDoiCapGoc_Cuoi"
              
              :labelWidth="'90'"
              label="Đôi cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtDoiCapNgon_Cuoi"
              :disable="formProps.isTxtDoiCapNgon_Cuoi"
              
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
              v-model="formProps.txtLienTu_Cuoi"
              :disable="formProps.isTxtLienTu_Cuoi"
              
              :labelWidth="'90'"
              label="Liên tủ"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtHopCapNgon_Cuoi"
              :disable="formProps.isTxtHopCapNgon_Cuoi"
              
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
              v-model="formProps.txtSoMetDay_Cuoi"
              :disable="formProps.isTxtSoMetDay_Cuoi"
              
              :labelWidth="'90'"
              label="Cự ly"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuyenCap_Cuoi"
              :disable="formProps.isTxtTuyenCap_Cuoi"
              
              :labelWidth="'100'"
              label="Tuyến cáp"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-select
          v-model="formProps.cboDonViCuoi"
          
          :labelWidth="'90'"
          :options="options.options_donVi"
          id="donVi"
          labelField="ten_dv"
          valueField="donvi_id"
          label="Đơn vị"
        ></vue-select>
        <vue-input
          v-model="formProps.txtDiaChiCuoi_Cu"
          
          :labelWidth="'90'"
          label="Địa chỉ cũ"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtNoiDungTra_Cuoi"
          
          :labelWidth="'90'"
          label="Nội dung trả"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtGhiChu"
          
          :labelWidth="'90'"
          label="Ghi chú"
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
  data: () => ({}),
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
      alert("btnNDTHOnClick");
    },
    setDisplayTable() {
      this.$emit("display-table");
    },
  },
};
</script>