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
                :showClearButton="false"
                :allowEdit="false"
                @change="onKyHoaDonChange"
                :readonly="isReadonly"
                :disabled="isDisabled"
                :width="'100%'"
                :style="isTextBold ? 'font-weight:bold': ''"
                :class="{'redcolor' : isTextRed}"
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
import CommonsAPI from '@/modules/QLTN/api/Commons'
import moment from 'moment'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'

// import {
//   THUNO_KEY
// } from '@/const/enums'
// import {getDataFromStorage, setDataToStorage} from '@/utils/util'
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
    let kyHT = this.getKyCuocHienTai() + '01'

    if (this.value === null || this.value === undefined) {
      this.p_kyhoadon = moment(kyHT, 'YYYYMMDD').toDate()
      // this.p_chuky_no_options = await this.getChuKyHienTai()
      // this.p_chuky_no = this.p_chuky_no_options.length > 0 ? this.p_chuky_no_options[0].CHUKY : null

      this.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no })
    } else {
      if (this.value === kyHT) {
        this.p_kyhoadon = moment(kyHT, 'YYYYMMDD').toDate()
        // this.p_chuky_no_options = await this.getChuKyHienTai()
        // this.p_chuky_no = this.p_chuky_no_options.length > 0 ? this.p_chuky_no_options[0].CHUKY : null

        this.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no })
      } else {
        this.p_kyhoadon = moment(this.value, 'YYYYMMDD').toDate()
        this.updateChuKyHoaDon(moment(this.p_kyhoadon).format('YYYYMM'))
      }
    }
  },
  computed: {
    kycuoc () {
      return this.value
    }
  },
  data () {
    return {
      p_kyhoadon: null,
      // p_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
      // p_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').format('MM/YYYY'),
      // p_chuky_no_options : [],
      p_chuky_no_options: [{CHUKY: '01'}],
      p_chuky_no: '01',
      max_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').toDate()
    }
  },
  methods: {
    getKyCuocHienTai () {
      // let ky_ht = getDataFromStorage(THUNO_KEY.KEY_KYCUOC_HT)
      // if (ky_ht === null) {
      //   ky_ht = moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMM')
      //   this.p_kyhoadon = moment(new Date()).subtract(1,'months').startOf('month').toDate()
      //   setDataToStorage(THUNO_KEY.KEY_KYCUOC_HT, ky_ht)
      // }
      // return ky_ht
      // this.p_kyhoadon = moment(new Date()).subtract(1, 'months').startOf('month').toDate()
      return moment(new Date()).subtract(1, 'months').startOf('month').format('YYYYMM')
    },
    async getChuKyHienTai () {
      // let chuky_ht = []
      // let chuky_ht_json = getDataFromStorage(THUNO_KEY.KEY_CHUKY_HT)
      // if (chuky_ht_json === null) {
      //   let ky_ht = this.getKyCuocHienTai()
      //   chuky_ht = await this.getChuKyNoTheoKyCuoc(ky_ht)
      //   setDataToStorage(THUNO_KEY.KEY_CHUKY_HT, JSON.stringify(chuky_ht))
      // } else {
      //   chuky_ht = JSON.parse(chuky_ht_json)
      // }

      // return chuky_ht
    },
    async onKyHoaDonChange (args) {
      console.log(args)
      if (this.isReadonly || this.isDisabled) return
      if (args.value === null) {
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }

      let kyHT = this.getKyCuocHienTai() + '01'
      if (moment(args.value).format('YYYYMM') + '01' === kyHT) {
        this.p_kyhoadon = moment(kyHT, 'YYYYMMDD').toDate()
        // this.p_chuky_no_options = await this.getChuKyHienTai()
        // this.p_chuky_no = this.p_chuky_no_options.length > 0 ? this.p_chuky_no_options[0].CHUKY : null

        // this.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no })
      }
      // await this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))
      this.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no })
    },
    onChuKyNoChange () {
      this.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no })
    },
    async updateChuKyHoaDon (pKyHoaDon) {
      this.loading(true)
      // this.p_chuky_no_options = []

      // this.p_chuky_no_options = await this.getChuKyNoTheoKyCuoc(pKyHoaDon)
      // this.p_chuky_no = this.p_chuky_no_options.length > 0 ? this.p_chuky_no_options[0].CHUKY : null

      this.$emit('chukycuocChange', { p_kyhoadon: this.p_kyhoadon, p_chuky_no: this.p_chuky_no })

      this.loading(false)

      /*

      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
      .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.p_chuky_no_options = response.data.data
                this.p_chuky_no = this.p_chuky_no_options[0].CHUKY
                //EventBus.$emit('chukycuocChange', { p_kyhoadon : this.p_kyhoadon, p_chuky_no : this.p_chuky_no})
                this.$emit("chukycuocChange", { p_kyhoadon : this.p_kyhoadon, p_chuky_no : this.p_chuky_no});
            }
          }
        )
      .catch (function (err) {
        console.log(err);
      })
      .finally(() => {
        //this.loading(false)
      })
      */
    },
    async getChuKyNoTheoKyCuoc (pKyHoaDon) {
      try {
        const res = await CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (error) {
        return []
      } finally {}
    }

  },
  watch: {
    kycuoc: {
      handler: function (newValue, oldValue) {
        let val = moment(newValue, 'YYYYMMDD')
        if (val.isValid()) this.p_kyhoadon = val.toDate()
        else this.p_kyhoadon = moment(new Date()).subtract(1, 'months').endOf('month').toDate()
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
