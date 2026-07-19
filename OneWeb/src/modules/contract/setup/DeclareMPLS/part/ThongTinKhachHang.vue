<template>
  <div class="box-form" :style="{ minHeight: visible ? '305px' : '91px' }">
    <div class="legend-title">
      <div class="pull-left">
        <span
          class="icon fa cursor-pointer"
          :class="visible ? 'fa-angle-up' : 'fa-angle-down'"
          @click="$emit('is-visible')"
        ></span>
        Thông tin khách hàng
      </div>
      <div class="pull-left ml-5" style="margin-top: -4px">
        <div class="info-row">
          <div class="value">
            <input
              type="checkbox"
              style="
                margin-right: 5px;
                margin-top: 3px;
                width: 19px;
                height: 19px;
              "
              :disabled="true"
              v-model="formProps.chkHen"
              class="check"
            />
          </div>
          <div class="key">
            <span class="name">Hẹn LĐ</span>
          </div>
        </div>

        <!-- <vue-checkbox
          v-model="formProps.chkHen"
          :disabled="true"
          :required="false"
          :labelWidth="'0'"
          label="Hẹn LĐ"
        >
        </vue-checkbox> -->
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="row" v-show="visible">
      <div class="col-sm-4 col-12">
        <vue-element labelWidth="90" label="Loại HĐ/PL">
          <ejs-dropdownlist
            :allowFiltering="true"
            v-model="formProps.cboLoaiHD"
            :dataSource="options.options_loaiHD"
            popupWidth="auto"
            :fields="{ value: 'LOAIHD_ID', text: 'TEN_LOAIHD' }"
          />
        </vue-element>

        <div class="input-container">
          <div class="info-row">
            <div class="key w90">
              <span class="name">Mã giao dịch<span class="required">*</span></span>
              
            </div>
            <div class="value">
              <input
                type="text"
                v-model="formProps.txtMaGD"
                v-on:keyup="myscript"
                class="form-control highlight text-black font-weight-bold"
              />
            </div>
          </div>
          <!---->
        </div>
        <vue-input
          v-model="formProps.txtTenKieuLD"
          :labelWidth="'90'"
          :disable="true"
          label="Kiểu lắp đặt"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtTenTB"
          :labelWidth="'90'"
          label="Tên thuê bao"
          :disable="true"
          class="vuetext-black vue-font-weight-bold"
          :checkbox="false"
        >
        </vue-input>
      </div>
      <div class="col-sm-8 col-12">
        <div class="input-container">
          <div class="info-row">
            <div class="key w90 w-full">
              <span class="name">Quy trình</span>
            </div>
            <div class="value">
              <ejs-dropdownlist
                id="quyTrinh"
                :allowFiltering="true"
                v-model="formProps.cboQuyTrinh"
                :dataSource="options.options_quyTrinh"
                :enabled="!formProps.cboLoaiHD ? false : true"
                :fields="{ value: 'quytrinh_id', text: 'quytrinh' }"
              />
            </div>
          </div>
        </div>

        <!-- <vue-element labelWidth="90" label="Quy trình">
          <ejs-dropdownlist
            id="quyTrinh"
            :allowFiltering="true"
            v-model="formProps.cboQuyTrinh"
            :dataSource="options.options_quyTrinh"
            :enabled="!formProps.cboLoaiHD ? false : true"
            popupWidth="auto"
            :fields="{ value: 'quytrinh_id', text: 'quytrinh' }"
          />
        </vue-element> -->

        <!-- <vue-input
          v-model="formProps.txtMaTB"
          :labelWidth="'90'"
          :highlight="true"
          label="Account"
          v-on:keyup="myAccount"
          :checkbox="false"
        >
        </vue-input> -->

        <div class="input-container">
          <div class="info-row">
            <div class="key w90">
              <span class="name">Account<span class="required">*</span></span>
            </div>
            <div class="value">
              <input
                type="text"
                v-model="formProps.txtMaTB"
                v-on:keyup="myAccount"
                class="form-control highlight text-black font-weight-bold"
              />
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-element labelWidth="90" label="Loại hình">
              <ejs-dropdownlist
                id="loaiHinh"
                :enabled="false"
                :allowFiltering="true"
                v-model="formProps.cboLoaiHinh"
                :dataSource="options.options_loaiHinh"
                popupWidth="auto"
                :fields="{ value: 'LOAITB_ID', text: 'LOAIHINH_TB' }"
              />
            </vue-element>
          </div>
          <div class="col-sm-6 col-12">
            <vue-element labelWidth="90" label="Tốc độ kênh">
              <ejs-dropdownlist
                id="tocDoKenh"
                :allowFiltering="true"
                :enabled="false"
                v-model="formProps.cboTocDo"
                :dataSource="options.options_tocDoKenh"
                popupWidth="auto"
                :fields="{ value: 'TOCDO_ID', text: 'item_tocDo' }"
              />
            </vue-element>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-element labelWidth="90" label="Chủ quản">
              <ejs-dropdownlist
                :allowFiltering="true"
                id="chuQuan"
                :enabled="false"
                v-model="formProps.cboChuQuan"
                :dataSource="options.options_chuQuan"
                popupWidth="auto"
                :fields="{ value: 'CHUQUAN_ID', text: 'TENCHUQUAN' }"
              />
            </vue-element>
          </div>
          <div class="col-sm-6 col-12 d-flex">
            <input
              type="checkbox"
              style="
                margin-right: 5px;
                margin-top: 3px;
                width: 19px;
                height: 19px;
              "
              :disabled="true"
              v-model="formProps.checkbox_value"
              class="check"
            />
            <vue-element labelWidth="68" class="w-full" label="Đối tác">
              <ejs-dropdownlist
                :allowFiltering="true"
                id="doiTac"
                v-model="formProps.cboDoiTac"
                :dataSource="options.options_doiTac"
                :enabled="formProps.checkbox_value"
                popupWidth="auto"
                :fields="{ value: 'doitac_id', text: 'ten_dt' }"
              />
            </vue-element>
            <!-- <vue-select
              ref="doiTac"
              v-model="formProps.cboDoiTac"
              :labelWidth="'90'"
              :options="options.options_doiTac"
              id="doiTac"
              v-if="isSelect"
              labelField="ten_dt"
              valueField="doitac_id"
              label="Đối tác"
              :checkbox="true"
            ></vue-select> -->
          </div>
        </div>
      </div>
      <div class="col-12">
        <vue-input
          v-model="formProps.txtDiaChiTB"
          :labelWidth="'90'"
          label="Địa chỉ TB"
          class="vue-text-black vue-font-weight-bold"
          :disable="true"
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
  props: ["formProps", "options", "visible"],
  data: function () {
    return {
      isSelect: true,
    };
  },
  watch: {},
  methods: {
    myscript(e) {
      if (e.which == 13) {
        this.$emit("on-enter");
      }
    },
    myAccount(e) {
      if (e.keyCode == 13) {
        this.$emit("my-account");
      }
    },
  },
};
</script>
