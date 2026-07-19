<template>
  <div class="row">
    <div class="col-sm-8">
        <div class="info-row">
            <div class="key w90">
                Kỳ hóa đơn<span class="required"></span>
            </div>
            <div class="value">
              <ejs-datepicker :placeholder="'Chọn kỳ hóa đơn'"
                id="p_kyhoadon_com"
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
                :disabled="isDisabled"
                :width="'100%'"
                :style="isTextBold ? 'font-weight:bold': ''"
                :class="{'redcolor' : isTextRed}"
                :created="onCreated"
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
                    :class="{'form-control': true, 'w-100': true, 'redcolor' : isTextRed}"
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
import CommonsAPI from '@/modules/QLTN/api/Commons'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
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
    },
    isTextRed: {
      type: Boolean,
      default: true
    }
  },
  async created () {
    if (this.value === null || this.value === undefined) {
      // await this.getKyHoaDonHienTai()
      // if (this.kyHoaDonHienTai !== undefined) {
      //   this.p_kyhoadon = moment(this.kyHoaDonHienTai.KYCUOC, 'YYYYMMDD').toDate()
      // }
      this.p_kyhoadon = moment(new Date()).subtract(1, 'months').startOf('month').toDate()
    } else this.p_kyhoadon = moment(this.value, 'YYYYMMDD').toDate()
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
      p_chuky_no_options: [{ 'CHUKY': '01' }],
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
    updateChuKyHoaDon: function (pKyHoaDon) {
      // this.loading(true)
      this.p_chuky_no_options = [{ 'CHUKY': '01' }]
      this.p_chuky_no = '01'

      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined &&
                response.data.data.length > 0) {
              this.p_chuky_no_options = response.data.data
              this.p_chuky_no = this.p_chuky_no_options[0].CHUKY
              EventBus.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no })
            }
          }
        )
        .catch(function (err) {
          console.log(err)
        })
        .finally(() => {
        // this.loading(false)
        })
    },
    /*
    getKyCuocHienHanh: function(){
      CommonsAPI.getKyCuocHienHanh(this.axios)
      .then(
        (res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.p_kyhoadon = moment(res.data.data[0].KYCUOC, 'YYYYMMDD').toDate()
          }
        }
      )
    }
    */
    onCreated (e) {
      // console.log(e)
      if (this.isDisabled && this.isTextRed) {
        let obj = document.getElementById('p_kyhoadon_com').ej2_instances[0]
        if (obj !== undefined) {
          obj.element.parentElement.classList.remove('e-disabled')
          obj.element.classList.remove('e-disabled')
          obj.element.disabled = false
        }
      }
    }
  },
  watch: {
    /*
    p_chuky_no : {
      handler: function(value) {
        EventBus.$emit('chukycuocChange', { p_kyhoadon : this.p_kyhoadon, p_chuky_no : this.p_chuky_no})
      }
    }  ,
    */
    kycuoc: {
      handler: function (newValue, oldValue) {
        let val = moment(newValue, 'YYYYMMDD')
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
