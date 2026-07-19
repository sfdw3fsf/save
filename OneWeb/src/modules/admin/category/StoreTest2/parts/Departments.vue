<template>
  <div class="row">
  <div class="grid-stack-box">
    <ejs-splitter id='splitter' class='splitterContent' ref="splitterObj" :separatorSize=1 width='100%'>
      <e-panes>
        <e-pane size="50%" min="20%" content='#content111'></e-pane>
        <e-pane size="50%" min="20%" content='#content112'></e-pane>
      </e-panes>
    </ejs-splitter>
  </div>

      <div id='content111'>
        <div class="box-form">
            <div class="legend-title">Danh sách đơn vị đã gán</div>
            <div class="form-control text h-auto" style="height: 200px;">
              <ejs-treeview id='storesDataAddedTreeView'
              :fields="storesDataAdded"
              :showCheckBox='true'
              :nodeChecked='storesDataAddedChecked'></ejs-treeview>
            </div>
        </div>
      </div>
      <div  id='content112'>
        <div class="box-form">
            <div class="legend-title">Danh sách đơn vị</div>
            <div class="box-move-select-table">
                <div class="actions w40 padl0">
                    <button class="btn" @click="removeDepartmentsFromList">
                        <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn" @click="addDepartmentsToList">
                        <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                </div>
                <div class="box-col" style="width:calc(100% - 40px);">
                    <div class="form-control text h-auto">
                      <ejs-treeview id='storesDataToAddTreeView'
                      :fields="storesDataToAdd"
                      :showCheckBox='true'
                      :nodeChecked='storesDataToAddChecked'></ejs-treeview>
                    </div>
                </div>
            </div>
        </div>
      </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import mixinStore from '../mixin/index'
import TreeView from './tree/TreeView.vue'

export default {
  components: {
    TreeView
  },
  data () {
    return {
      storesDataAdded: { dataSource: [], id: 'id', text: 'name', child: 'children' },
      selectedIdsAdded: [],
      storesDataToAdd: { dataSource: [], id: 'id', text: 'name', child: 'children' },
      selectedIdsToAdd: []
    }
  },
  computed: {
    ...mapGetters('storesCommon', [
      'getDepartmentsNotAdded',
      'getSelectedStore',
      'getSelectedDepartment',
      'getDepartmentsAdded',
      'getStores'
    ])
  },
  methods: {
    ...mapActions('storesCommon', [
      'loadDepartmentsNotAdded',
      'loadDepartmentsAdded',
      'addDepartmentsToDepartmentsAdded',
      'removeDepartmentsFromDepartmentsAdded',
      'insertDepartmentsToStore',
      'deleteDepartmentsFromStore'
    ]),
    onSelectAdded: function (e) {
      this.selectedNodeAdded = e
    },
    loadData: async function () {
      try {
        let kho_id = -1
        if (this.getSelectedStore && this.getSelectedStore.kho_id) kho_id = this.getSelectedStore.kho_id

        await this.loadDepartmentsNotAdded({
          kho_id: kho_id
        })

        await this.loadDepartmentsAdded({
          kho_id: kho_id
          // phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
      } catch (error) {
        console.log('loadData', error)
        this.$toast.error(error.data.message_detail)
      }
    },
    storesDataToAddChecked: function () {
      const treeObj = document.getElementById('storesDataToAddTreeView').ej2_instances[0]
      this.selectedIdsToAdd = this.getDepartmentIds(treeObj.checkedNodes, this.storesDataToAdd.dataSource[0])
    },
    storesDataAddedChecked: function () {
      const treeObj = document.getElementById('storesDataAddedTreeView').ej2_instances[0]
      this.selectedIdsAdded = this.getDepartmentIds(treeObj.checkedNodes, this.storesDataAdded.dataSource[0])
      console.log('this.selectedIdsAdded', this.selectedIdsAdded)
    },
    getDepartmentIds: function (selectedTreeIds, departmentRootNode) {
      const idsCollection = []
      selectedTreeIds.forEach((id) => {
        if (!idsCollection.includes(id)) {
          idsCollection.push(id)
        }

        const node = this.getNodeById(departmentRootNode, id)
        if (node) {
          const parents = []
          this.getParents(departmentRootNode, node, parents)
          parents.forEach(j => {
            if (!idsCollection.includes(j.id) && j.id !== -1) idsCollection.push(j.id)
          })
        }
      })
      return idsCollection
    },
    addDepartmentsToList: async function () {
      this.$root.showLoading(true)
      try {
        if (this.selectedIdsToAdd.length > 0) {
          const departments = this.getDepartmentsNotAdded.filter(i => this.selectedIdsToAdd.includes(i.donvi_id.toString()))

          const upperCaseKeyData = this.upperCaseKey(departments)
          await this.insertDepartmentsToStore(upperCaseKeyData)
          await this.loadData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    removeDepartmentsFromList: async function () {
      this.$root.showLoading(true)
      try {
        // const treeObj = document.getElementById('department-added').ej2_instances[0]
        if (this.selectedIdsAdded.length > 0) {
          const departments = this.getDepartmentsAdded.filter(i => this.selectedIdsAdded.includes(i.donvi_id.toString()))

          const upperCaseKeyData = this.upperCaseKey(departments)
          await this.deleteDepartmentsFromStore(upperCaseKeyData)
          await this.loadData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    formatTreeView: function (originalList) {
      const originalListIncludedRoot = originalList.map((i) => {
        return {
          donvi_cha_id: i.donvi_cha_id === null ? -1 : i.donvi_cha_id,
          donvi_id: i.donvi_id,
          ma_dv: i.ma_dv,
          ten_dv: i.ten_dv,
          unitlevel: i.unitlevel
        }
      })

      const nodes = this.buildTreeFromList(originalListIncludedRoot, 'ten_dv', 'donvi_id', 'donvi_cha_id')
      const root = [{
        id: -1,
        name: 'Root',
        children: nodes,
        expanded: true
      }]
      return { dataSource: root, id: 'id', text: 'name', child: 'children' }
    }
  },
  mixins: [mixinStore],
  watch: {
    getDepartmentsAdded: function (newValue, oldValue) {
      this.storesDataAdded = this.formatTreeView(newValue)
    },
    getDepartmentsNotAdded: function (newValue, oldValue) {
      this.storesDataToAdd = this.formatTreeView(newValue)
    }
  }
}
</script>

<style scoped>
.form-control.text.h-auto {
    overflow-y: scroll;
    height: 200px !important;
}
</style>
