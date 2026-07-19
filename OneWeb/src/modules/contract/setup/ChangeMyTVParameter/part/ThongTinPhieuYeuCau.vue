<template>
  <div class="box-form">
    <div class="legend-title">Thông tin phiếu yêu cầu</div>
    <div class="row">
      <div class="col-sm-7 col-12">
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key">Mã giao dịch <span class="required">*</span></div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <vue-input
                    ref="maGiaoDich"
                    label=""
                    v-model="formProps.maGiaoDich"
                    :labelWidth="'0'"
                    :validate="['required']"
                    :checkbox="false"
                  >
                  </vue-input>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <vue-date
              ref="ngayYeuCau"
              v-model="formProps.ngayYeuCau"
              :labelWidth="'110'"
              required
              label="Ngày yêu cầu"
            ></vue-date>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <vue-input
              ref="maHopDong"
              label="Mã hợp đồng"
              required
              v-model="formProps.maHopDong"
              :labelWidth="'110'"
              :validate="['required']"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-5 col-12">
            <vue-date
              ref="ngayLapHD"
              required
              v-model="formProps.ngayLapHD"
              :labelWidth="'110'"
              label="Ngày lập HĐ"
            ></vue-date>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              ref="maKhachHang"
              required
              label="Mã khách hàng"
              v-model="formProps.maKhachHang"
              :labelWidth="'110'"
              :validate="['required']"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              ref="tenKhachHang"
              label="Tên KH"
              required
              v-model="formProps.tenKhachHang"
              :labelWidth="'80'"
              :validate="['required']"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="info-row">
          <vue-input
            ref="diaChiKH"
            required
            label="Địa chỉ KH"
            v-model="formProps.diaChiKH"
            :labelWidth="'110'"
            :validate="['required']"
            :checkbox="false"
          >
          </vue-input>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["formProps", "optionsProps"],
};
</script>

<style>
</style>