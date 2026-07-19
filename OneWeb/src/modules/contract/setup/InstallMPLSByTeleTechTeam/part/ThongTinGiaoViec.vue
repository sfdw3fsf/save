<template>
  <div class="box-form">
    <div class="legend-title">Thông tin giao việc</div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <!-- <vue-date
          ref="NgayGV"
          v-model="formProps.dtpNgayGV"
          :labelWidth="'90'"
          
          label="Ngày GV"
          :checkbox="true"
        ></vue-date> -->

        <div class="info-row">
          <div class="key w90">Ngày GV</div>
          <div class="value d-flex">
            <div class="value w20 padt7 pl-0">
              <div class="check-action">
                <input
                ref="chkNgayGV"
                  type="checkbox"
                  class="check"
                  :disabled="formProps.dischkNgayGV"
                  v-model="formProps.chkNgayGV"
                />
                <span class="name"></span>
              </div>
            </div>
            <date-picker
              style="width: 100%"
              v-model="formProps.dtpNgayGV"
              placeholder="dd/mm/yyyy"
              :disabled="formProps.disNgayGV"
              :type="'datetime'"
              :format="'hh:mm'"
              :time-picker-options="timePickerOptions"
              :formatter="momentFormat"
              valueType="format"
            ></date-picker>
          </div>
        </div>
      </div>
       <div class="col-sm-6 col-12">    

        <div class="info-row">
          <div class="key w90">Ngày BG</div>
          <div class="value d-flex">
            <div class="value w20 padt7 pl-0">
              <div class="check-action">
                <input
                  type="checkbox"
                  class="check"
                  :disabled="formProps.dischkNgayBG"
                  v-model="formProps.chkNgayBG"
                />
                <span class="name"></span>
              </div>
            </div>
            <date-picker
              style="width: 100%"
              v-model="formProps.dtpNgayBG"
              placeholder="dd/mm/yyyy"
              :disabled="formProps.disNgayBG"
              :type="'datetime'"
              :format="'hh:mm'"
              :time-picker-options="timePickerOptions"
              :formatter="momentFormat"
              valueType="format"
            ></date-picker>
          </div>
        </div>
      </div>
      <!-- <div class="col-sm-6 col-12">
        <vue-date
          ref="dtpNgayBG"
          v-model="formProps.dtpNgayBG"
          :labelWidth="'90'"
          
          label="Ngày HT"
          :checkbox="true"
        ></vue-date>
      </div> -->
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <vue-select
          v-model="formProps.cboNguoiGV"
          
          :labelWidth="'90'"
          :options="options.nguoiGV"
          id="nguoiGV"
          labelField="ten_nv"
          valueField="nhanvien_id"
          label="Người GV"
        ></vue-select>
      </div>
      <div class="col-sm-6 col-12">
        <vue-input
          v-model="formProps.txtNhanVien"
          :labelWidth="'90'"
          
          label="Nhân viên YC"
          :checkbox="false"
        >
        </vue-input>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-12 col-12">
        <div
          style="
            min-height: 140px !important;
            max-height: 140px !important;
            overflow: scroll;
          "
        >
          <vue-table
            ref="TTGV_DSNV"
            v-model="formProps.table_DSNV.currentRecord"
            :options="formProps.table_DSNV.options"
            :config="formProps.table_DSNV.config"
            :loading="formProps.table_DSNV.loading"
            :multiple="false"
          >
          </vue-table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import DatePicker from "vue2-datepicker";
import * as moment from "moment";

export default {
  components: { DatePicker },
  props: ["formProps", "options"],
  data: () => ({
    isDisabledNgayBG: true,
    timePickerOptions: {
      start: "00:00",
      step: "00:10",
      end: "23:50",
    },

    momentFormat: {
      stringify: (date) => {
        return date ? moment(date).format("DD/MM/YYYY hh:mm:ss") : "";
      },
      parse: (value) => {
        return value ? moment(value, "DD/MM/YYYY hh:mm:ss").toDate() : null;
      },
    },
  }),

  watch: {},
  computed: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>
