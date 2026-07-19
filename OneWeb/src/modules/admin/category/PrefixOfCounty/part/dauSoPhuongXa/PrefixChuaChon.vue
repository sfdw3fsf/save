<template>
  <div class="box-col box-form">
    <!-- <div class="legend-title"></div>
    <div class="box-col box-form"> -->
      <div class="legend-title">Prefix chưa chọn</div>
      <!-- <vue-table
        :multiple="true"
        :config="configPrefixNotSelected"
        :options="danhSachPrefixChuaChon"
        :loading="isLoadingNotSelect"
        v-model="selectedNotPrefix"
        :is-check-all.sync="isCheckAllNotMaped"
        :unique="'index'"
      /> -->
      <DataGrid
        :columns="configPrefixNotSelected"
        :dataSource="danhSachPrefixChuaChon"
        :enable-paging-server="false"
        :showColumnCheckbox="true"
        :allowPaging="true"
        :loading="isLoadingNotSelect"
        :showFilter="true"
        @selectedItemsChanged="grid_selectedRowChanged"
      >
      </DataGrid>
    </div>
</template>
<script>
import {configPrefixNotSelected} from './../../constant'
import { mapActions, mapState } from 'vuex'
export default {
  data () {
    return {
      configPrefixNotSelected,
      selectedNotPrefix: null,
      isCheckAllNotMaped: false

    }
  },
  computed: {
    ...mapState('prefixOfCounty', ['danhSachPrefixChuaChon', 'dataNotSelect', 'danhSachPrefixDaChon', 'isLoadingNotSelect'])
  },
  watch: {
    selectedNotPrefix (newVal) {
      this.$emit('changeSelectedNotPrefix', newVal)
    }
  },
  methods:{
    grid_selectedRowChanged (item) {
      this.$emit('changeSelectedNotPrefix', item )
    }
  }
}
</script>
