<template>
  <div class="box-form">
      <div class="legend-title">Danh sách kho</div>
      <div class="select-custom">
          <div @click="toggleStoreListPopup"  id="inputkho">
            <vue-input v-model="selectedStoreName" class="-search-store-custom"></vue-input>
          </div>
          <div v-show="isShowStoreListPopup" class="box-form store-list-popup">
            <div class="info-row">
              <DataGrid
                  ref="grid_filter"
                  :columns="storeListTable.columns"
                  :dataSource="getStoresIncludeRoot"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :loading="storeListTable.isLoading"
                  :showFilter="true"
                  :enabledSelectFirstRow="false"
                  @selectedRowChanged="onStoreListTableSelectedRowChanged"
                  @pageChanged="onChangePage"
              ></DataGrid>
            </div>
          </div>
      </div>
      <div class="form-control text h-auto">
          <div class="tree-plus tree-icon ovauto" style="height: 750px;">
            <tree-view
            :options="temp_storesData"
            :labelField="'ma_kho_ten_kho'"
            :valueField="'id'"
            @changeSelectedItem="onSelect"
            @isOpenChange="isOpenChange"
            :selectedValue="selectedValue"></tree-view>
          </div>
      </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import mixinStore from '../mixin/index'
import TreeView from '../parts/tree/TreeView.vue'
export default {
components: {
  TreeView
},
data () {
  return {
    selectedStoreId: -1,
    originalStoresData: [],
    temp_storesData: [],
    selectedValue: null,
    isShowStoreListPopup: false,
    selectedStoreName: '',
    storeListTable: {
      columns: [{
        headerText: 'Mã',
        fieldName: 'ma_kho',
        textAlign: 'Left'
      }, {
        headerText: 'Tên',
        fieldName: 'ten_kho',
        textAlign: 'Left'
      }],
      isLoading: true
    }
  }
},
props: {
  storesData: Array
},
watch: {
  storesData: {
    handler (val) {
      console.log('storesData val', val)
      this.formatTreeStoreName(val)
      this.originalStoresData = JSON.parse(JSON.stringify(val))
      this.temp_storesData = val
      if (this.temp_storesData.length === 0) return
      this.temp_storesData[0].isOpen = true
    },
    deep: true
  },
  selectedStoreId: function (newValue, oldValue) {
    // expand parent nodes
    if (this.temp_storesData.length === 0) return

    let roots = []
    //roots = Array.from(this.originalStoresData)
    if (newValue === -1) {
      roots = Array.from(this.originalStoresData)
    } else {
      const copyOriginalArray = JSON.parse(JSON.stringify(this.originalStoresData))
      let selectedStoreNode = this.getNodeById(copyOriginalArray[0], newValue)
      const parentTree = this.getParentTreeSplit(copyOriginalArray[0], selectedStoreNode)
      
      roots = [{
        id: '-1',
        name: 'Root',
        ma_kho_ten_kho: 'Root',
        children: [parentTree]
      }]
    }

    this.temp_storesData = Array.from(roots)

    this.temp_storesData[0].isOpen = true
    this.expandNode(this.temp_storesData[0], newValue)

    const captureData = Array.from(this.temp_storesData)
    this.temp_storesData = []
    // let timeOut = setTimeout(() => {
    //   this.temp_storesData = captureData
    //   this.selectedValue = newValue.toString()

    //   // set selected store
    //   const selectedStore = this.getStoresIncludeRoot.find(i => i.kho_id.toString() === newValue.toString())
    //   console.log('selectedStore')
    //   console.log(selectedStore)
    //   this.setSelectedStore(selectedStore)
    //   this.$emit('onSelect', selectedStore)
    //   clearTimeout(timeOut)
    // }, 500)
    this.temp_storesData = captureData
    this.selectedValue = newValue.toString()

    // set selected store
    const selectedStore = this.getStoresIncludeRoot.find(i => i.kho_id.toString() === newValue.toString())
    console.log('selectedStore')
    console.log(selectedStore)
    this.setSelectedStore(selectedStore)
    this.$emit('onSelect', selectedStore)
  }
},
computed: {
  ...mapGetters('storesCommon', [
    'getStores',
    'getStoresIncludeRoot'
  ])
},
mixins: [mixinStore],
methods: {
  ...mapActions('storesCommon', [
    'setSelectedStore',
    'loadDepartmentsNotAdded',
    'loadDepartmentsAdded'
  ]),
  ...mapActions('purposesCommon', [
    'loadPurposeNotAdded',
    'loadPurposeAdded'
  ]),
  loadDepartmentsData: async function (selectedStore) {
    try {
      let kho_id = -1
      if (selectedStore && selectedStore.kho_id) kho_id = selectedStore.kho_id

      await this.loadDepartmentsNotAdded({
        kho_id: kho_id
      })

      await this.loadDepartmentsAdded({
        kho_id: kho_id
        // phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
      })
    } catch (error) {
      console.log(error)
      this.$toast.error(error.data.message_detail)
    }
  },
  loadEmployeesData: async function (selectedStore) {
    try {
      if (!selectedStore) return

      await this.loadPurposeNotAdded({
        kho_id: selectedStore.kho_id,
        phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
      })

      await this.loadPurposeAdded({
        kho_id: selectedStore.kho_id,
        phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
      })
    } catch (error) {
      console.log(error)
      this.$toast.error(error.toString())
    }
  },
  onSelect: async function (e) {
    const selectedStore = this.getStores.find(i => i.kho_id.toString() === e.id)
    await this.setSelectedStore(selectedStore)

    //this.$emit('onSelect', selectedStore)
  },
  getNodesExpands: function (storeId, parents) {
    const currentNode = this.getStores.find(i => i.kho_id.toString() === storeId.toString())
    if (currentNode.kho_cha_id) {
      parents.unshift(currentNode.kho_cha_id.toString())
      this.getNodesExpands(currentNode.kho_cha_id, parents)
    } else {
      parents.unshift('-1')
    }

    return parents
  },
  isOpenChange: function (e) {
  },
  toggleStoreListPopup: function (e) {
    this.isShowStoreListPopup = !this.isShowStoreListPopup
    let items = this.$refs.grid_filter.getSelectedRecords()
    if (!this.isShowStoreListPopup && (!items || items.length==0)){
      let roots = Array.from(this.originalStoresData)
      this.temp_storesData = Array.from(roots)
      this.temp_storesData[0].isOpen = true
      this.expandNode(this.temp_storesData[0], this.selectedStoreId)
    }
  },
  onStoreListTableSelectedRowChanged: function (e) {
    if (!e) { return }
    this.isShowStoreListPopup = false
    this.selectedStoreName = (e !== undefined && e !== null) ? e.ten_kho : ''
    this.selectedStoreId = e.kho_id
  },
  onChangePage: function () {},
  formatTreeStoreName: function (nodes) {
    for (let i = 0; i < nodes.length; i++) {
      if (nodes[i].data) {
        nodes[i].ma_kho_ten_kho = `${nodes[i].data.ma_kho} - ${nodes[i].data.ten_kho}`
      } else {
        nodes[i].ma_kho_ten_kho = nodes[i].name
      }
      this.formatTreeStoreName(nodes[i].children)
    }
  },
  setSelectedValue: function (value,list) {    
    this.selectedStoreId = value   
    this.originalStoresData = JSON.parse(JSON.stringify(list))
    const nodes = this.buildTreeFromList(list, 'ten_kho', 'kho_id', 'kho_cha_id')
    const roots = [{
      id: '-1',
      name: 'Root',
      children: nodes
    }]
    
    this.toggleStoreListPopup(null)
    
    setTimeout(function(){$("#inputkho").trigger("click");},500);
    
  },
},
mounted: function () {
  this.selectedValue = this.selectedStoreId.toString()
}
}
</script>

<style>
.-hide-label .info-row .key{
display: none;
}
.-hide-label .info-row .value{
padding-left: 0;
}
.-search-store-custom .info-row  .value{
padding-left: 0;
}

.store-list-popup{
position: absolute;
z-index: 1000;
}

.box-form {
  width: unset;
}
</style>
