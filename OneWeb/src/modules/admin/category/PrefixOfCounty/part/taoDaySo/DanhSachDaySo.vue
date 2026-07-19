<template>
  <div class="box-form">
    <div class="legend-title">Danh sách đầu số</div>
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
        :loading="isLoadingNotSelect"
        :showFilter="true"
        @selectedItemsChanged="grid_selectedRowChanged"
      >
      </DataGrid>
  </div>
</template>
<script>
import { configPrefixSelected } from './../../constant'
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
    ...mapState('prefixOfCounty', ['danhSachPrefixDaChon', 'isLoadingSelect', 'paramFixedNumber'])
  },
  created () {},
  watch: {
    selectedPrefix (newVal) {
      // this.setDataSelect(newVal)
      this.$emit('changeDataSelect', newVal)
      if (newVal && newVal.length > 0) {
        this.layDaiSoHienTai(newVal[newVal.length - 1])
      }
    }
  },
  methods: {
    ...mapActions('prefixOfCounty', ['setDataSelect']),
    grid_selectedRowChanged (item) {
      this.$emit('changeDataSelect', item)
      if (item && item.length > 0) {
        this.layDaiSoHienTai(item[item.length - 1])
      }
    },
    layDaiSoHienTai (item) {
      this.$store.commit('prefixOfCounty/SET_PARAM_FIXED', {
        ...this.paramFixedNumber,
        prefix: item.prefix,
        suffix: item.suffix,
        length: item.length,
        ten_ls: item.ten_ls,
        tuso: item.tuso,
        denso: item.denso
      })
    }
  }
}
</script>
