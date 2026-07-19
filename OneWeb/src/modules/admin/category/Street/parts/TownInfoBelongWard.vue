<template>
    <div class="box-form">
        <div class="legend-title">
            Thông tin phố thuộc phường
        </div>
        <div class="info-row">
            <div class="key w80 padt0">Quận/Huyện</div>
            <div class="value">
              <ejs-dropdownlist
                :allowFiltering="true"
                v-model="selectedDistrictId"
                :filtering="onFilteringDistrict"
                :placeholder="'Chọn quận/huyện'"
                :dataSource="getDistricts"
                :fields="{ text: 'TEN_QUAN', value: 'QUAN_ID' }"/>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import {Predicate, Query} from '@syncfusion/ej2-data'

export default {
  data () {
    return {
      selectedDistrictId: null
    }
  },
  computed: {
    ...mapGetters('streetCommon', [
      'getDistricts',
      'getSelectedDistrictId',
      'getSelectedTownAreaId',
      'getTownId'
    ])
  },
  methods: {
    ...mapActions('streetCommon', [
      'setDistricts',
      'setSelectedDistrict',
      'setWardsNotAdded'
    ]),
    onSelected (id) {
      this.setSelectedDistrict(id)
    },
    onFilteringDistrict: function (e) {
      const query = new Query()
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        const predicate = new Predicate('TEN_QUAN', 'contains', e.text.trim(), true)
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.getDistricts, query)
    }
  },
  created: async function () {
    this.$root.showLoading(true)
    await this.setDistricts()
    this.$root.showLoading(false)

    if (this.getDistricts.length > 0) {
      this.selectedDistrictId = this.getDistricts[0].QUAN_ID
      this.setSelectedDistrict(this.selectedDistrictId)
    }
  },
  watch: {
    getSelectedDistrictId: async function (newValue, oldValue) {
      this.selectedDistrictId = newValue
      this.$root.showLoading(true)
      await this.setWardsNotAdded({
        pho_id: this.getTownId,
        quan_id: newValue
      })
      this.$root.showLoading(false)
    },
    getTownId: async function (newValue, oldValue) {
      this.$root.showLoading(true)
      await this.setWardsNotAdded({
        pho_id: newValue,
        quan_id: this.getSelectedDistrictId
      })
      this.$root.showLoading(false)
    },
    getDistricts: function () {
      if (this.getDistricts.length > 0) {
        this.selectedDistrictId = this.getDistricts[0].QUAN_ID
        this.setSelectedDistrict(this.selectedDistrictId)
      }
    },
    selectedDistrictId: function (newValue, oldValue) {
      this.onSelected(newValue)
    }
  }
}
</script>
