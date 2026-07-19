<template>
  <div class="row">
    <div class="col-sm-8">
        <div class="info-row">
            <div class="key w90">
                Kỳ hóa đơn<span class="required"></span>
            </div>
            <div class="value">
              <ejs-datepicker :placeholder="'Chọn kỳ hóa đơn'"
                v-model="p_kyhoadon"
                :value="p_kyhoadon"
                :max="max_kyhoadon"
                :start="'Year'"
                :depth="'Year'"
                :format="'MM/y'"
                :change="onKyHoaDonChange"
                :showClearButton="false"
                :allowEdit="false"
                :readonly="isReadonly"
                :enabled="!isDisabled"
                :width="'100%'"
                :style="isTextBold ? 'font-weight:bold': ''"
                class="redcolor"
              >
              </ejs-datepicker>
            </div>
      </div>
    </div>
    <div class="col-sm-4">
        <div class="info-row">
            <div class="key w60">
                Chu kỳ<span class="required"></span>
            </div>
            <div class="value">
                <div class="select-custom">
                  <b-form-select
                    id="id-chuky-no"
                    v-model="p_chuky_no"
                    :value="p_kyhoadon"
                    :options="p_chuky_no_options"
                    value-field="CHUKY"
                    text-field="CHUKY"
                    @change = "onChuKyNoChange()"
                     :style="isTextBold ? 'font-weight:bold': ''"
                    class="form-control redcolor w-100"
                    :readonly="isReadonly"
                    :disabled="isDisabled">
                  </b-form-select>
                </div>
            </div>
        </div>
    </div>

  </div>
</template>
<script>
import Vue from 'vue'
import { mapActions, mapState } from 'vuex'
import moment from 'moment'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'

Vue.use(DatePickerPlugin)

export default ({
  name: 'KyCuoc',
  props: {
    value: {
      type: String,
      required: false
    },
    isReadonly: {
      type: Boolean,
      default: false
    },
    isTextBold: {
      type: Boolean,
      default: false
    },
    isDisabled: {
      type: Boolean,
      default: false
    }
  },
  async created () {
    if (this.value === null || this.value === undefined) {
      await this.getKyHoaDonHienTai()
      console.log('this.kyHoaDonHienTai', this.kyHoaDonHienTai)
      if (this.kyHoaDonHienTai !== undefined) {
        this.p_kyhoadon = moment(this.kyHoaDonHienTai.KYCUOC, 'YYYYMMDD').toDate()
      }
    } else { this.p_kyhoadon = moment(this.value, 'YYYYMMDD').toDate() }
    this.updateChuKyHoaDon(moment(this.p_kyhoadon).format('YYYYMM'))
  },
  computed: {
    ...mapState('qltnCommon', [
      'kyHoaDonHienTai'
    ]),
    kycuoc () {
      return this.value
    }

  },
  data () {
    return {
      p_kyhoadon: null,
      // p_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
      // p_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').format('MM/YYYY'),
      p_chuky_no_options: [],
      p_chuky_no: null,
      max_kyhoadon: moment(new Date()).subtract(1, 'months').endOf('month').toDate()
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getKyHoaDonHienTai'
    ]),
    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }
      this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))
    },
    onChuKyNoChange: function () {
    },
    updateChuKyHoaDon: async function (pKyHoaDon) {
      this.p_chuky_no_options = []
      this.p_chuky_no = null
      let response = await this.$root.context.get('/web-thuno/api/thu-no/common/chu-ky-no/ky-cuoc/' + pKyHoaDon)
      if (response.error_code === 'BSS-00000000' && response.data != null) {
        this.p_chuky_no_options = response.data
        this.p_chuky_no = this.p_chuky_no_options[0].CHUKY
        this.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no})
      }
    }
  },
  watch: {
    kycuoc: {
      handler: function (new_value, old_value) {
        let val = moment(new_value, 'YYYYMMDD')
        if (val.isValid()) { this.p_kyhoadon = val.toDate() } else { this.p_kyhoadon = moment(new Date()).subtract(1, 'months').endOf('month').toDate() }
      }
    }
  }
})
</script>
<style>
  .redcolor {
    color: #d32f2f !important
  }
  .boldtext {
    font-weight: 600 !important
  }
</style>
