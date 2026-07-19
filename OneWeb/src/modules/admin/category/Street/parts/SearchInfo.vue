<template>
    <div class="box-form">
        <div class="legend-title">Thông tin tìm kiếm</div>
        <div class="info-row">
            <div class="key w80 padt0">Quận/Huyện</div>
            <div class="value">
              <ejs-dropdownlist
                :allowFiltering="true"
                v-model="selectedDistrictId"
                :filtering="onFilteringDistrict"
                :placeholder="'Chọn quận/huyện'"
                :dataSource="getDistrictsAll"
                :fields="{ text: 'ten_quan', value: 'quan_id' }"/>
            </div>
        </div>
        <div class="info-row">
            <div class="key w80 padt0">Phường/Xã</div>
            <div class="value">
              <ejs-dropdownlist
                :allowFiltering="true"
                v-model="selectedWardId"
                :filtering="onFilteringWard"
                :placeholder="'Chọn nhóm phố'"
                :dataSource="getSearchWards"
                :fields="{ text: 'TEN_PHUONG', value: 'PHUONG_ID' }"/>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import {Predicate, Query} from '@syncfusion/ej2-data'
import api from "../api/index"

export default {
  data () {
    return {
      selectedDistrictId: 0,
      selectedWardId: 0,
      getDistrictsAll : [],
    }
  },
  computed: {
    ...mapGetters('streetCommon', [
      'getDistricts',
      'getSearchWards',
      'getSelectedTownAreaId',
      'getSelectedSearchDistrictId',
      'getCurrentSearchWardId'
    ])
  },
  methods: {
    ...mapActions('streetCommon', [
      'loadWardsByDistrict',
      'loadTownsByWard',
      'setCurrentSearchWardId',
      'setSelectedSearchDistrictId'
    ]),
    onSelectedDistrict: async function (id) {
      try{
        this.setSelectedSearchDistrictId(id)
        this.selectedWardId = 0;
        this.$root.showLoading(true)
        await this.loadWardsByDistrict({
          CATEGORY_NAME: '',
          CODE: '',
          ID: id,
          NAME: ''
        })

        await this.loadTownsByWard({
          "p_quan_id": id,
          "p_phuong_id": this.selectedWardId
        })
      }
      catch (e){

      }
      finally {
        this.$root.showLoading(false)
      }

    },
    onFilteringDistrict: function (e) {
      const query = new Query()
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        const predicate = new Predicate('ten_quan', 'contains', e.text.trim(), true)
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.getDistrictsAll, query)
    },
    onSelectedWard (id) {
      try {
        this.setCurrentSearchWardId(id)
        this.loadTownsByWard({
          "p_quan_id": this.selectedDistrictId,
          "p_phuong_id": id
        })
      } catch (error) {
        console.log(error)
      }
    },
    onFilteringWard: function (e) {
      const query = new Query()
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        const predicate = new Predicate('TEN_PHUONG', 'contains', e.text.trim(), true)
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.getSearchWards, query)
    }
  },
  watch: {
    getDistrictsAll: function () {
      if (this.getDistrictsAll.length > 0) {
        this.selectedDistrictId = this.getDistrictsAll[0].quan_id
      }
    },
    getSelectedSearchDistrictId: function (newValue, oldValue) {
      this.selectedDistrictId = newValue
    },
    selectedDistrictId: function (newValue, oldValue) {
      this.onSelectedDistrict(newValue)
    },
    selectedWardId: function (newValue, oldValue) {
      this.onSelectedWard(newValue)
    }
  },
  created: async function () {
    let reponseDistricts = await api.getDistrictsAll(this.axios,{});
    if(reponseDistricts && reponseDistricts.data && reponseDistricts.data.error_code && reponseDistricts.data.error_code == 'BSS-00000000' && reponseDistricts.data.data){
      this.getDistrictsAll = reponseDistricts.data.data
      if (this.getDistrictsAll.length > 0) {
        this.selectedDistrictId = this.getDistrictsAll[0].quan_id
      }
    }


  }
}
</script>
