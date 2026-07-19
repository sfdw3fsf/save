<template>
    <div class="box-form">
        <div class="legend-title">Thông tin cước thuê bao</div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w120">
                        Cước thuê bao
                    </div>
                    <div class="value">
                        <input :value="subcriber.subcriberFee" @input="updateSubcriberFee" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (VNĐ)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        Cước trọn gói
                    </div>
                    <div class="value">
                        <input :value="subcriber.totalFee" @input="updateTotalFee" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (VNĐ)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        Ngày giảm
                    </div>
                    <div class="">
                        <div class="input-icon-right">
                            <!-- <input type="text" class="form-control"> -->
                            <!-- <span class="icon one-calendar"></span> -->
                            <!-- <date-picker :value="subcriber.dateDecrease"
                            @change="updateDateDecrease"
                            placeholder="dd/mm/yyyy"
                            :formatter="momentFormat"
                            valueType="format"></date-picker> -->
                            <vue-date v-bind:value="subcriber.dateDecrease"
                            format="DD/MM/YYYY"
                            v-on:input="updateDateDecrease"></vue-date>
                        </div>
                    </div>
                    <div class="value w80 padt7">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        Miễn phí IP tỉnh
                    </div>
                    <div class="value">
                        <input :value="subcriber.ipStaticFree" @input="updateIpStaticFree" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        Ngày giảm IP
                    </div>
                    <div class="">
                        <div class="input-icon-right">
                            <!-- <input type="text" class="form-control">
                            <span class="icon one-calendar"></span> -->
                            <!-- <date-picker :value="subcriber.dateDecreaseIp"
                            @change="updateDateDecreaseIp"
                            placeholder="dd/mm/yyyy"
                            :formatter="momentFormat"
                            valueType="format"></date-picker> -->
                            <vue-date v-bind:value="subcriber.dateDecreaseIp"
                            format="DD/MM/YYYY"
                            v-on:input="updateDateDecreaseIp"></vue-date>
                        </div>
                    </div>
                    <div class="value w80 padt7">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        Email thuê thêm
                    </div>
                    <div class="value">
                        <input :value="subcriber.emailQtyMoreRent" @input="updateEmailQtyMoreRent" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (Số lượng)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        Tỷ lệ giảm Email
                    </div>
                    <div class="value">
                        <input :value="subcriber.emailDecreaseRatio" @input="updateEmailDecreaseRatio" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (%)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        T.gian giảm Email
                    </div>
                    <div class="value">
                        <input :value="subcriber.emailDecreaseTime" @input="updateEmailDecreaseTime" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (Tháng)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        MB miễn phí
                    </div>
                    <div class="value">
                        <input :value="subcriber.capacityFree" @input="updateCapacityFree" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (Số lượng)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w120">
                        Tiền giảm
                    </div>
                    <div class="value">
                        <input :value="subcriber.discount" @input="updateDiscount" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (VNĐ)
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w130">
                        Cước khoán
                    </div>
                    <div class="value">
                        <input :value="subcriber.packageFee" @input="updatePackageFee" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (VNĐ)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Tỷ lệ giảm cước
                    </div>
                    <div class="value">
                        <input :value="subcriber.discountRatio" @input="updateDiscountRatio" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (%)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Thời gian giảm
                    </div>
                    <div class="value">
                        <input :value="subcriber.monthsApplyDiscount" @input="updateMonthsApplyDiscount" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (Tháng)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Tỷ lệ giảm IP
                    </div>
                    <div class="value">
                        <input :value="subcriber.ipDecreaseRatio" @input="updateIpDecreaseRatio" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (%)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        T.gian giảm IP
                    </div>
                    <div class="value">
                        <input :value="subcriber.monthsIpDecrease" @input="updateMonthsIpDecrease" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (Tháng)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Đơn giá 1 Email
                    </div>
                    <div class="value">
                        <input :value="subcriber.pricePerEmail" @input="updatePricePerEmail" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (VNĐ)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Ngày BĐ giảm Email
                    </div>
                    <div class="">
                        <div class="input-icon-right">
                            <!-- <input  type="text" class="form-control">
                            <span class="icon one-calendar"></span> -->
                            <!-- <date-picker :value="subcriber.decreaseEmailStartDate"
                            @change="updateDecreaseEmailStartDate"
                            placeholder="dd/mm/yyyy"
                            :formatter="momentFormat"
                            valueType="format"></date-picker> -->
                            <vue-date v-bind:value="subcriber.decreaseEmailStartDate"
                            format="DD/MM/YYYY"
                            v-on:input="updateDecreaseEmailStartDate"></vue-date>
                        </div>
                    </div>
                    <div class="value w80 padt7">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Cước 1 MB
                    </div>
                    <div class="value">
                        <input :value="subcriber.pricePerMB" @input="updatePricePerMB" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (VNĐ)
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Cước miễn phí
                    </div>
                    <div class="value">
                        <input :value="subcriber.freeFee" @input="updateFreeFee" type="text" class="form-control">
                    </div>
                    <div class="value w80 padt7">
                        (VNĐ)
                    </div>
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key w120">
                Ghi chú
            </div>
            <div class="value">
                <textarea :value="subcriber.note" @input="updateNote" name="" class="form-control" style="height: 100px;resize: none;"></textarea>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import DatePicker from 'vue2-datepicker'
import * as moment from 'moment'

export default {
  data () {
    return {
      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format('D/M/YYYY') : ''
        },
        parse: (value) => {
          return value ? moment(value, 'D/M/YYYY').toDate() : null
        }
      }
    }
  },
  computed: {
    subcriber () {
      return this.$store.state.changeFreeIPInfo.changeFreeIPInfoCommon.subcriberInfo
    }
  },
  methods: {
    ...mapActions('changeFreeIPInfoCommon', [
      'setSubcriberFee',
      'setTotalFee',
      'setIpStaticFree',
      'setDateDecrease',
      'setDateDecreaseIp',
      'setEmailQtyMoreRent',
      'setEmailDecreaseRatio',
      'setEmailDecreaseTime',
      'setCapacityFree',
      'setDiscount',
      'setPackageFee',
      'setDiscountRatio',
      'setMonthsApplyDiscount',
      'setIpDecreaseRatio',
      'setMonthsIpDecrease',
      'setPricePerEmail',
      'setDecreaseEmailStartDate',
      'setPricePerMB',
      'setFreeFee',
      'setNote'
    ]),
    updateSubcriberFee (e) {
      this.setSubcriberFee(e.target.value)
    },
    updateTotalFee (e) {
      this.setTotalFee(e.target.value)
    },
    updateIpStaticFree (e) {
      this.setIpStaticFree(e.target.value)
    },
    updateDateDecrease (value) {
      this.setDateDecrease(value)
    },
    updateDateDecreaseIp (value) {
      this.setDateDecreaseIp(value)
    },
    updateEmailQtyMoreRent (e) {
      this.setEmailQtyMoreRent(e.target.value)
    },
    updateEmailDecreaseRatio (e) {
      this.setEmailDecreaseRatio(e.target.value)
    },
    updateEmailDecreaseTime (e) {
      this.setEmailDecreaseTime(e.target.value)
    },
    updateCapacityFree (e) {
      this.setCapacityFree(e.target.value)
    },
    updateDiscount (e) {
      this.setDiscount(e.target.value)
    },
    updatePackageFee (e) {
      this.setPackageFee(e.target.value)
    },
    updateDiscountRatio (e) {
      this.setDiscountRatio(e.target.value)
    },
    updateMonthsApplyDiscount (e) {
      this.setMonthsApplyDiscount(e.target.value)
    },
    updateIpDecreaseRatio (e) {
      this.setIpDecreaseRatio(e.target.value)
    },
    updateMonthsIpDecrease (e) {
      this.setMonthsIpDecrease(e.target.value)
    },
    updatePricePerEmail (e) {
      this.setPricePerEmail(e.target.value)
    },
    updateDecreaseEmailStartDate (value) {
      this.setDecreaseEmailStartDate(value)
    },
    updatePricePerMB (e) {
      this.setPricePerMB(e.target.value)
    },
    updateFreeFee (e) {
      this.setFreeFee(e.target.value)
    },
    updateNote (e) {
      this.setNote(e.target.value)
    }
  },
  components: {
    DatePicker
  }
}
</script>

<style scoped>
.mx-datepicker{
    width: 100%;
}
</style>
