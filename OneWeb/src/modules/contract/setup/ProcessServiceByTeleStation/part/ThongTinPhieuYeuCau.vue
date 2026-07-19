<template>
  <div class="col-sm-4 col-12">
    <div class="legend-title">Thông tin phiếu yêu cầu</div>
    <div class="info-row">
         <vue-select
          :labelWidth="'90'"
          :options="serviceList"
          required
          labelField="ten_dvvt"
          valueField="dichvuvt_id"
          label="Dịch vụ"
          v-model="form.dichvuvt_id"
        ></vue-select>
    </div>
    <div class="info-row">
         <vue-select
          :labelWidth="'90'"
          :options="processList"
          required
          labelField="quytrinh"
          valueField="quytrinh_id"
          label="Quy trình"
          v-model="form.tthd_id"
        ></vue-select>
    </div>
    <div class="info-row">
         <vue-select
          :labelWidth="'90'"
          :options="loaiHinhList"
          required
          labelField="NAME"
          valueField="ID"
          label="Loại hình"
          :checkbox="true"
        ></vue-select>
    </div>
    <div class="info-row">
         <vue-input
            ref="soMayAcc"
            label="Số máy/Acc"
            required
            :labelWidth="'90'"
            :validate="['required']"
            :checkbox="false"
            :highlight="true"
            v-model="form.ma_gd"
          >
          </vue-input>
    </div>
    <div class="info-row">
         <vue-select
          :labelWidth="'90'"
          :options="requestTypeList"
          required
          labelField="text"
          valueField="id"
          label="Kiểu YC"
        ></vue-select>
    </div>
    <div class="info-row">
        <div class="key w90">Thu hồi Tbị</div>
        <div class="value">
            <div class="list-checks mart7">
                <vue-radio label="Giao theo QĐ" value="1" name="rad1" defaultValue="1"></vue-radio>
                <vue-radio label="Giao ngay" value="2" name="rad1"></vue-radio>
            </div>
        </div>
    </div>
    <div class="info-row">
        <div class="input-more-button -right">
            <vue-textarea
              label="Nội dung TH"
              height="126"
              required
            ></vue-textarea>
            <button class="btn">
                <span class="nc-icon-glyph ui-1_edit-74"></span>
            </button>
        </div>
    </div>
    <div class="list-checks-ver red mart10">
        <div class="item marb10">
            <div class="check-action">
                <input type="checkbox" name="cb1" class="check">
                <span class="name">Bỏ qua lỗi khi kích hoạt để kích hoạt thuê bao tiếp theo</span>
            </div>
        </div>
        <div class="item">
            <div class="check-action">
                <input type="checkbox" name="cb1" class="check">
                <span class="name">Bỏ qua lỗi khi hoàn công để hoàn công thuê bao tiếp theo</span>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  props: ['formProps', 'optionsProps'],
  data: () => ({
    serviceList1: [{id: 1, name: 'dat'}, {id: 2, name: 'dat'}, {id: 3, name: 'dat'}],
    form: {
      ma_gd: null,
      nhanvien_id: null,
      dichvuvt_id: null,
      loaitb_id: null,
      tthd_id: null,
      huongiao_id: null,
      kieu_yc: null,
      loai_id: null
    }
  }),
  watch: {
    'form.tthd_id': {
      handler: function (after, before) {
        this.$store.dispatch('processServiceByTeleStation/getLoaiHinhList', after)
      },
      deep: true
    }
  },
  computed: {
    serviceList () {
      return this.$store.getters['processServiceByTeleStation/getServiceList'] || []
    },
    requestTypeList () {
      return this.$store.getters['processServiceByTeleStation/getRequestTypeList'] || []
    },
    processList () {
      return this.$store.getters['processServiceByTeleStation/getProcessList'] || []
    },
    loaiHinhList () {
      return this.$store.getters['processServiceByTeleStation/getLoaiHinhList'] || []
    }
  },
  mounted () {
    this.$store.dispatch('processServiceByTeleStation/getServiceList', {ds_dichvu_vt: '1,4'})
    this.$store.dispatch('processServiceByTeleStation/getRequestTypeList')
    this.$store.dispatch('processServiceByTeleStation/getProcessList', {dsloaihd_id: 7, dsdichvuvt_id: 4, dstthd_id: 1, kieu: 1})
  },
  methods: {
    ...mapActions('processServiceByTeleStation', [
      'getServiceList',
      'getRequestTypeList',
      'getProcessList',
      'getLoaiHinhList'
    ])
  }
}
</script>

<style>
</style>
