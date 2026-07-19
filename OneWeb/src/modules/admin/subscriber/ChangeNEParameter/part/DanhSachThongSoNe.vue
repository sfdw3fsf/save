<template>
  <div>
    <div class="legend-title mart20">Danh sách số NE</div>
    <!-- <DataGrid
      :columns="[
        {
          fieldName: 'sone',
          headerText: 'Số Ne',
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: 'ds_matb',
          headerText: 'Số máy',
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: 'port_mdf',
          headerText: 'Port MDF',
          allowFiltering: true,
          allowHtml: true,
          allowSorting: false,
        },
        {
          fieldName: 'toado_mdf',
          headerText: 'Toạ độ M...',
          allowFiltering: true,
          allowHtml: true,
          allowSorting: false,
        },
      ]"
      :dataSource="danhSachSoNe"
      :enable-paging-server="false"
      :allowPaging="true"
      :showFilter="true"
      @selectedRowChanged="grid_selectedRowChanged"
    >
    </DataGrid> -->
        <!-- <div class="legend-title">Danh sách số NE</div> -->
    <DataGrid
      :columns="config"
      :dataSource="danhSachSoNe"
      :enable-paging-server="false"
      :allowPaging="true"
      :showFilter="true"
      @selectedRowChanged="grid_selectedRowChanged"
    >
    </DataGrid>
    <!-- <vue-card label="Danh sách số NE">
      <vue-table
        :config="config"
        :options="danhSachSoNe"
        v-model="currentRecord"
        :loading="isLoading"
        :unique="'index'"
      />
    </vue-card> -->
  </div>
</template>
<script>
import { mapState } from 'vuex'
import {config} from './../constant'
export default {
  data () {
    return {
      config,
      currentRecord: null
    }
  },
  computed: {
    ...mapState('changeNEParameter', ['danhSachSoNe', 'isLoading'])
  },
  watch: {
    danhSachSoNe (val) {
      if (val && val.length > 0) {
        this.currentRecord = val[0]
      }
    }
    // currentRecord (val) {
    //   this.$store.commit('changeNEParameter/SET_ITEM_NE', val)
    // }
  },
  methods: {
    grid_selectedRowChanged (val) {
      this.$store.commit('changeNEParameter/SET_ITEM_NE', val)
    }
  }
}
</script>
