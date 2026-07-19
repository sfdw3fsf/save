<template>
  <div class="box-col box-form">
    <div class="legend-title">Prefix đã chọn</div>
    <!-- <vue-table
      :multiple="true"
      :config="configPrefixSelected"
      :options="danhSachPrefixDaChon"
      :loading="isLoadingSelect"
      v-model="selectedPrefix"
      :is-check-all.sync="isCheckAllNotPrefix"
      :unique="'index'"
    /> -->
          <DataGrid
        :columns="configPrefixSelected"
        :dataSource="danhSachPrefixDaChon"
        :enable-paging-server="false"
        :showColumnCheckbox="true"
        :allowPaging="true"
        :loading="isLoadingSelect"
        :showFilter="true"
        @selectedItemsChanged="grid_selectedRowChanged"
      >
      </DataGrid>
  </div>
</template>
<script>
import {configPrefixSelected} from './../../constant'
import { mapActions, mapState } from 'vuex'
export default {
  data () {
    return {
      configPrefixSelected,
      selectedNotPrefix: null,
      isCheckAllNotPrefix: false,
      selectedPrefix: null
    }
  },
  computed: {
    ...mapState('prefixOfCounty', ['danhSachPrefixDaChon', 'isLoadingSelect'])
  },
  watch: {
    selectedPrefix (newVal) {
      this.$emit('changeSelectedPrefix', newVal)
    }
  },
  methods: {
    grid_selectedRowChanged (item) {
      this.$emit('changeSelectedPrefix', item)
    }
  }
}
</script>
