<template>
    <div class="box-form">
        <div class="legend-title">Thông tin hệ số thu cước róc</div>
        <div class="info-row">
            <vue-input
                v-model="timeTPS"
                :labelWidth="'100'"
                label="Thời điểm TPS"
                :checkbox="false">
            </vue-input>
        </div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w100">Ngày thu từ</div>
                    <div class="value">
                        <input
                            type="number"
                            class="form-control text fw6"
                            v-model="dateFromNum">
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w100">Ngày thu đến</div>
                    <div class="value">
                        <input
                            type="number"
                            class="form-control text fw6"
                            v-model="dateToNum">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <vue-input
                        v-model="factor"
                        :labelWidth="'100'"
                        label="Hệ số"
                        :checkbox="false">
                    </vue-input>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w100">Kiểu TCR</div>
                    <div style="position: relative;">
                        <div @click="toggleFeeTypesPopup">
                            <vue-input v-model="selectedFeeTypeName" class="-search-fee-types-custom"></vue-input>
                        </div>
                        <div v-show="isShowFeeTypesPopup" class="box-form fee-types-popup">
                            <div class="info-row">
                                <DataGrid
                                    :columns="feeTypesTable.columns"
                                    :dataSource="getFeeTypes"
                                    :enable-paging-server="false"
                                    :allowPaging="false"
                                    :loading="feeTypesTable.isLoading"
                                    :showFilter="false"
                                    :enabledSelectFirstRow="true"
                                    @selectedRowChanged="onSelectedFeeType"
                                ></DataGrid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="info-row">
            <vue-input
                v-model="note"
                :labelWidth="'100'"
                label="Ghi chú"
                :checkbox="false">
            </vue-input>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      timeTPS: null,
      dateFromNum: 0,
      dateToNum: 0,
      factor: 0,
      selectedFeeTypeId: null,
      note: null,
      feeTypesTable: {
        columns: [{
          headerText: 'Kiểu TCR',
          fieldName: 'kieu_tcr',
          textAlign: 'Left'
        }, {
          headerText: 'Ghi chú',
          fieldName: 'ghichu',
          textAlign: 'Left'
        }],
        isLoading: true
      },
      isShowFeeTypesPopup: false,
      selectedFeeTypeName: ''
    }
  },
  props: {
    feeFactor: Object
  },
  watch: {
    feeFactor: function () {
      this.timeTPS = this.feeFactor.thoidiem_tps
      this.dateFromNum = this.feeFactor.ngaythu_tu
      this.dateToNum = this.feeFactor.ngaythu_den
      this.factor = this.feeFactor.heso_tcr
      this.selectedFeeTypeId = this.feeFactor.kieu_tcr_id
      this.note = this.feeFactor.ghichu
    },
    getFeeTypes: function (newValue, oldValue) {
      if (newValue.length > 0) {
        this.selectedFeeTypeId = newValue[0].kieu_tcr_id
      }
    }
  },
  computed: {
    ...mapGetters('feeCollectionFactorCommon', [
      'getFeeList',
      'getFeeTypes'
    ])
  },
  methods: {
    onSelectedFeeType: function (e) {
      console.log('onSelectedFeeType', e)
      this.selectedFeeTypeId = e.kieu_tcr_id
      this.selectedFeeTypeName = e.kieu_tcr
    },
    toggleFeeTypesPopup: function () {
      this.isShowFeeTypesPopup = !this.isShowFeeTypesPopup
    }
  }
}
</script>

<style scoped>
.-search-fee-types-custom .info-row  .value{
  padding-left: 0;
}

.fee-types-popup{
  position: absolute;
  z-index: 1000;
}
</style>
