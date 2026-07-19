<template>
  <div class="box-form">
    <div class="legend-title">Thông tin thuê bao</div>

    <div class="row">
      <div class="col-sm-7 col-12">
        <div class="info-row">
          <div class="key">Account <span class="required">*</span></div>

          <div class="value">
            <div class="input-more-button">
              <button class="btn">
                <span class="-ap icon-more_horiz"></span>
              </button>
              <vue-input
                ref="account"
                label=""
                v-model="formProps.account"
                :labelWidth="'0'"
                :validate="['required']"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
        </div>
        <div class="info-row">
          <vue-input
            ref="tenThueBao"
            label="Tên thuê bao"
            required
            v-model="formProps.tenThueBao"
            :labelWidth="'110'"
            :validate="['required']"
            :checkbox="false"
          >
          </vue-input>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <vue-input
                ref="trangBiCu"
                label="Trang bị cũ"
                v-model="formProps.trangBiCu"
                :labelWidth="'110'"
                required
                :validate="['required']"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <vue-select
                v-model="formProps.trangBiMoi_id"
                :labelWidth="'120'"
                required
                :options="optionsProps.options_dsTrangBi"
                id="trangBiMoiId"
                labelField="LOAIHINH_TB"
                valueField="LOAITB_ID"
                label="Trang bị mới"
              ></vue-select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <vue-input
                ref="mucCuocThuBaoCu"
                label="Mức cước TB cũ"
                required
                v-model="formProps.mucCuocThuBaoCu"
                :labelWidth="'110'"
                :validate="['required']"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <vue-select
                v-model="formProps.mucCuocThuBaoMoi_id"
                :labelWidth="'120'"
                :options="optionsProps.options_dsMucCuocTB"
                id="trangBiMoiId"
                labelField="LOAIHINH_TB"
                valueField="LOAITB_ID"
                required
                label="Mức cước TB mới"
              ></vue-select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <vue-input
                ref="loaiThietBiCu"
                label="Loại thiết bị cũ"
                required
                v-model="formProps.loaiThietBiCu"
                :labelWidth="'110'"
                :validate="['required']"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <vue-select
                v-model="formProps.loaiThietBiMoi_id"
                :labelWidth="'120'"
                required
                :options="optionsProps.options_dsLoaiThietBi"
                id="trangBiMoiId"
                labelField="LOAIHINH_TB"
                valueField="LOAITB_ID"
                label="Loại thiết bị mới"
              ></vue-select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <vue-select
                v-model="formProps.dichvuvt_id"
                :labelWidth="'110'"
                required
                :options="dichVuVT"
                id="dichVuVT"
                labelField="TEN_DVVT"
                valueField="DICHVUVT_ID"
                label="Dịch vụ VT"
              ></vue-select>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <vue-select
                v-model="formProps.loaiTb_id"
                :labelWidth="'90'"
                :options="dsloaiHinhTB"
                id="loaiHinhTB"
                labelField="LOAIHINH_TB"
                required
                valueField="LOAITB_ID"
                label="Loại hình TB"
              ></vue-select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <vue-input
                ref="ghiChu"
                label="Ghi chú"
                required
                v-model="formProps.ghiChu"
                :labelWidth="'110'"
                :validate="['required']"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <vue-select
                v-model="formProps.kieuYeuCau_id"
                :labelWidth="'90'"
                :options="optionsProps.options_dsKieuYeucau"
                id="loaiHinhTB"
                required
                labelField="LOAIHINH_TB"
                valueField="LOAITB_ID"
                label="Kiểu yêu cầu"
              ></vue-select>
            </div>
          </div>
        </div>
        <div class="info-row">
          <vue-input
            ref="diaChiLD"
            label="Địa chỉ LĐ"
            v-model="formProps.diaChiLD"
            :labelWidth="'110'"
            required
            :validate="['required']"
            :checkbox="false"
          >
          </vue-input>
        </div>
        <div class="legend-title mart20">Chi tiết tiền theo khoản mục</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <vue-input
                ref="diaChiLD"
                label="Tiền"
                required
                v-model="formProps.tien"
                :labelWidth="'110'"
                :validate="['required']"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <vue-input
                ref="VAT"
                required
                label="Vat"
                v-model="formProps.VAT"
                :labelWidth="'110'"
                :validate="['required']"
                :checkbox="false"
              >
              </vue-input>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["formProps", "optionsProps","dichVuVT","dsloaiHinhTB"],
};
</script>

<style>
</style>