<template>
  <div
    class="box-form"
    :style="{ minHeight: formProps.isVisible ? '290px' : '90px' }"
  >
    <div class="legend-title">
      <div class="pull-left">
        <span
          class="icon fa cursor-pointer"
          :class="formProps.isVisible ? 'fa-angle-up' : 'fa-angle-down'"
          @click="formProps.isVisible = !formProps.isVisible"
        ></span>
        Thông tin khách hàng
      </div>
      <div class="pull-right">
        <vue-checkbox
          v-model="formProps.chkHen"
          :required="false"
          :labelWidth="'0'"
          label="Hẹn LĐ"
        >
        </vue-checkbox>
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="row" v-show="formProps.isVisible">
      <div class="col-sm-4 col-12">
        <vue-select
          v-model="formProps.cboLoaiHD"
          
          :labelWidth="'90'"
          :options="options.options_loaiHD"
          id="loaiHD"
          labelField="TEN_LOAIHD"
          valueField="LOAIHD_ID"
          label="Loại HĐ/PL"
        ></vue-select>
        <!-- <vue-input
          v-model="formProps.txtMaGD"
          
          :labelWidth="'90'"
          onkeypress="myscript(event,this)"
          :highlight="true"
          label="Mã giao dịch"
          :checkbox="false"
        >
        </vue-input> -->
        <div class="input-container">
          <div class="info-row">
            <div class="key w90">
              <span class="name">Mã giao dịch</span>
            </div>
            <div class="value">
              <input
                type="text"
                v-model="formProps.txtMaGD"
                v-on:keyup="myscript"
                class="form-control highlight"
              />
            </div>
          </div>
          <!---->
        </div>
        <vue-input
          v-model="formProps.txtTenKieuLD"
          
          :labelWidth="'90'"
          label="Kiểu lắp đặt"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtTenTB"
          
          :labelWidth="'90'"
          label="Tên thuê bao"
          :checkbox="false"
        >
        </vue-input>
      </div>
      <div class="col-sm-8 col-12">
        <vue-select
          v-model="formProps.cboQuyTrinh"
          
          :labelWidth="'90'"
          :options="options.options_quyTrinh"
          id="quyTrinh"
          :disable="formProps.cboLoaiHD ? false : true"
          labelField="quytrinh"
          valueField="quytrinh_id"
          label="Quy trình"
        ></vue-select>
        <vue-input
          v-model="formProps.txtMaTB"
          
          :labelWidth="'90'"
          :highlight="true"
          label="Account"
          :checkbox="false"
        >
        </vue-input>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboLoaiHinh"
              
              :labelWidth="'90'"
              :options="options.options_loaiHinh"
              id="loaiHinh"
              labelField="LOAIHINH_TB"
              valueField="LOAITB_ID"
              label="Loại hình"
            ></vue-select>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboTocDo"              
              :labelWidth="'90'"
              :options="options.options_tocDoKenh"
              id="tocDoKenh"
              labelField="item_tocDo"
              valueField="TOCDO_ID"
              label="Tốc độ kênh"
            ></vue-select>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboChuQuan"
              :required="false"
              :labelWidth="'90'"
              :options="options.options_chuQuan"
              id="chuQuan"
              labelField="TENCHUQUAN"
              valueField="CHUQUAN_ID"
              label="Chủ quản"
            ></vue-select>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              ref="doiTac"
              v-model="formProps.cboDoiTac"              
              :labelWidth="'90'"
              :options="options.options_doiTac"
              id="doiTac"
              labelField="ten_dt"
              valueField="doitac_id"
              label="Đối tác"
              :checkbox="true"
            ></vue-select>
          </div>
        </div>
      </div>
      <vue-input
        v-model="formProps.txtDiaChiTB"
        
        :labelWidth="'90'"
        label="Địa chỉ TB"
        :checkbox="false"
      >
      </vue-input>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  props: ["formProps", "options"],
  methods: {
    myscript(e) {
      if (e.which == 13) {
       this.$emit('on-enter')
      }
    },
  },
};
</script>
