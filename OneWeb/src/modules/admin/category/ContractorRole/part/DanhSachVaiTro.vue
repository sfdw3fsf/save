<template>
  <div class="box-form">
   <!-- <vue-card label="Danh sách vai trò">
      <vue-table
        :config="config"
        :options="danhSachVaiTro"
        v-model="currentRecord"
        :loading="isLoading"
        :unique="'index'"
      />
    </vue-card> -->
    <DataGrid
        :columns="config"
        :dataSource="danhSachVaiTro"
        :enable-paging-server="false"
        :allowPaging="true"
        :loading="isLoading"
        :showFilter="true"
        @selectedRowChanged="grid_selectedRowChanged"
        @rowClicked="rowVaiTroClick"
        ref="danhSachVaiTro"
      >
    </DataGrid>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
import {config} from './../constant'
import Vue from 'vue'

export default {
  data () {
    return {
      config,
      currentRecord: null,
      oldIndex : null
    }
  },
  computed: {
    ...mapState('contractorRole', ['danhSachVaiTro', 'isLoading', 'dataItem', 'isShowModal'])
  },
  watch: {
    currentRecord (newVal) {
      this.$store.commit('contractorRole/SET_DATA_ITEM', newVal)
    },
    dataItem (newVal) {
      console.log('isShowModal', this.isShowModal)
      // if (this.danhSachVaiTro && this.danhSachVaiTro.length > 0) {
      //   this.currentRecord = this.danhSachVaiTro[0]
      // }
      // if (newVal && !newVal.vaitro_id) {
      //   this.currentRecord = null
      // }
    },
    isShowModal: {
      deep: true,
      immediate: true,
      handler () {
        this.handleShowModal()
      }
    },
    checkRecord: 'handleSubmitForm',
  },
  methods: {
    grid_selectedRowChanged (item) {
      this.$store.commit('contractorRole/SET_DATA_ITEM', item)
    },
    handleShowModal () {
      if (this.danhSachVaiTro && this.danhSachVaiTro.length > 0) {
        this.currentRecord = this.danhSachVaiTro[0]
      }
    },

    goToFirstPage() {
      this.$refs.danhSachVaiTro.$refs.pagination.gotoPage(0)
    },
    rowVaiTroClick(index, obj) {
      this.oldIndex = index
    },

    focusUpdateRow() {
      console.log('run in this', this.oldIndex)
      this.$refs.danhSachVaiTro.setCurrentSelectedRow(this.oldIndex)
      this.$refs.danhSachVaiTro.flagSelectedRowIndexes = [];
      this.$refs.danhSachVaiTro.flagSelectedRowIndexes.push(this.oldIndex);
    }
  }
}
</script>
