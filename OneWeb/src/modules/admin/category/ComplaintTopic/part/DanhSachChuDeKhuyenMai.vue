<template>
  <div class="box-form">
    <div class="box-col box-form danhSachChuDeKhuyenMai-wrap">
      <div class="legend-title">Danh sách chủ đề khiếu nại</div>
      <DataGrid
        :columns="config"
        :dataSource="danhSachKhuyenMai.ds"
        :enable-paging-server="false"
        :allowPaging="false"
        :loading="isLoading"
        :showFilter="true"
        @selectedRowChanged="grid_selectedRowChanged"
        :pageSettings="pageSettings"
        :pageSize="pageSize"
        :panelDataHeight="panelDataHeight"
      >
      </DataGrid>
    </div>
  </div>
</template>
<script>
import { config } from './../constant'

import { mapActions, mapState, mapGetters } from 'vuex'
export default {
  data () {
    return {
      config,
      selected: null,
      isCheck: false,
      pageSize: 10,
      pageIndex: 1,
      pageSettings: { pageSize: 10 },
      panelDataHeight : "320"
    }
  },
  computed: {
    ...mapState('complaintTopic', [
      'danhSachKhuyenMai',
      'isLoading',
      'dataItem',
      'totalRow'
    ])
  },
  watch: {
    selected (newVal) {
     if (this.danhSachKhuyenMai.ds && this.danhSachKhuyenMai.ds.length > 0) {
        this.selected = this.danhSachKhuyenMai.ds[0]
      }
      this.$store.commit('complaintTopic/SET_DATA_ITEM', newVal)
    }
  },
  methods: {
    ...mapActions('complaintTopic', ['layDanhSachKhuyenMai']),
    onChangePage (item) {
      
      this.pageSize = item.pageSize
      this.pageIndex = item.pageIndex + 1
      this.$emit('onChangePage',
        item
      )
    },
    grid_selectedRowChanged (item) {
      this.$store.commit('complaintTopic/SET_DATA_ITEM', item)
    }
  }
}
</script>
<style scoped>
>>> .table-result th {
  width: 60px;
}
>>> col:nth-child(2) {
  width: 45px !important;
}
/* >>> col:nth-child(3) {
  width: 300px !important;
} */
>>> .e-headercelldiv {
  padding: 0px !important;
}

</style>