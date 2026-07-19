<template>
  <div class="box-form-top">
    <div class="title-bg-main">
      <div class="list-checks">
        <div class="item vcenter">
          <span class="title">Thông tin điểm đầu - cuối</span>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12" style="padding: 5px 18px">
        <div class="legend-title">Điểm đầu</div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <!-- <vue-select
              v-model="formProps.diemDau_donVi"
              :labelWidth="'100'"
              :options="formProps.optionsDonVi"
              id="loaiHD"
              labelField="ten_dv"
              valueField="donvi_id"
              label="Đơn vị"
            ></vue-select> -->
            <div class="input-container">
              <div class="info-row">
                <div class="key w100">Đơn vị</div>
                <div class="value">
                  <div class="select-custom">
                    <ejs-dropdownlist
                      class="combobox"
                popupWidth="auto"

                      v-model="formProps.diemDau_donVi"
                      :dataSource="formProps.optionsDonVi"
                      :noRecordsTemplate="noRecordsTemplate"
                      :fields="{ text: 'ten_dv', value: 'donvi_id' }"
                      @filtering="
                        onFilteringDropDownList(
                          $event,
                          formProps.optionsDonVi,
                          'ten_dv'
                        )
                      "
                      :allowFiltering="true"
                    >
                    </ejs-dropdownlist>
                    <!---->
                  </div>
                </div>
                <!---->
              </div>
              <!---->
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <vue-input
              v-model="formProps.diemDau_soAo"
              :labelWidth="'100'"
              :highlight="true"
              label="Số ảo"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <vue-input
              v-model="formProps.diemDau_diaChiMoi"
              :labelWidth="'100'"
              label="Địa chỉ mới"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12" style="padding: 5px 18px">
        <div class="legend-title">Điểm cuối</div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <!-- <vue-select
              v-model="formProps.diemCuoi_donVi"
              :labelWidth="'100'"
              :options="formProps.optionsDonVi"
              id="loaiHD"
              labelField="ten_dv"
              valueField="donvi_id"
              label="Đơn vị"
            ></vue-select> -->
            <div class="input-container">
              <div class="info-row">
                <div class="key w100">Đơn vị</div>
                <div class="value">
                  <div class="select-custom">
                    <ejs-dropdownlist
                      class="combobox"
                popupWidth="auto"

                      v-model="formProps.diemCuoi_donVi"
                      :dataSource="formProps.optionsDonVi"
                      :noRecordsTemplate="noRecordsTemplate"
                      :fields="{ text: 'ten_dv', value: 'donvi_id' }"
                      @filtering="
                        onFilteringDropDownList(
                          $event,
                          formProps.optionsDonVi,
                          'ten_dv'
                        )
                      "
                      :allowFiltering="true"
                    >
                    </ejs-dropdownlist>
                    <!---->
                  </div>
                </div>
                <!---->
              </div>
              <!---->
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.diemCuoi_soAo"
              :labelWidth="'100'"
              :highlight="true"
              label="Số ảo"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <vue-input
              v-model="formProps.diemCuoi_diaChiMoi"
              :labelWidth="'100'"
              label="Địa chỉ mới"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Query } from "@syncfusion/ej2-data";

export default {
  components: {},
  data() {
    return {
      noRecordsTemplate: "Không có dữ liệu!",
    };
  },
  props: ["formProps"],
  methods: {
    onFilteringDropDownList(e, dataSource, name) {
      var query = new Query();
      query =
        e.text !== "" ? query.where(name, "contains", e.text, true) : query;
      e.updateData(dataSource, query);
    },
  },
};
</script>

<style scoped>
.box-form-top {
  background: #ffffff;
  padding-bottom: 16px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  margin-bottom: 8px;

  box-shadow: 0px 4px 4px rgb(177 177 177 / 15%);
}

.title-bg-main {
  border-radius: 0px !important;
}
</style>