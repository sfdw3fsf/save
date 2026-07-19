<template>
  <div class="row">
      <div class="col-sm-6 col-12">
        <div class="box-form">
            <div class="legend-title">Danh sách đơn vị đã gán</div>
            <div class="form-control text h-auto">
                <!-- <ejs-treeview
                id='department-added'
                :fields="fieldsAdded"></ejs-treeview> -->
                <div class="tree-plus tree-icon ovauto" style="height: 120px;">
                    <tree-view
                    :options="storesDataAdded"
                    :labelField="'name'"
                    :valueField="'id'"
                    @changeSelectedItem="onSelectAdded"></tree-view>
                </div>
            </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
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
                      <!-- <ejs-treeview
                      ref="treeviewToAdd"
                      id='department-to-add'
                      :fields="fieldsToAdd"
                      :selectedNodes="selectedNodesToAdd"></ejs-treeview> -->
                      <div class="tree-plus tree-icon ovauto" style="height: 120px;">
                          <tree-view
                          :options="storesDataToAdd"
                          :labelField="'name'"
                          :valueField="'id'"
                          @changeSelectedItem="onSelectToAdd"></tree-view>
                      </div>
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
      storesDataToAdd: [],
      selectedNodeToAdd: null,
      storesDataAdded: [],
      selectedNodeAdded: null
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
    onSelectToAdd: function (e) {
      this.selectedNodeToAdd = e
    },
    onSelectAdded: function (e) {
      this.selectedNodeAdded = e
    },
    loadData: async function () {
      try {
        await this.loadDepartmentsNotAdded({
          kho_id: this.getSelectedStore.kho_id
        })

        await this.loadDepartmentsAdded({
          kho_id: this.getSelectedStore.kho_id,
          phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    addDepartmentsToList: async function () {
      this.$root.showLoading(true)
      try {
        // const treeObj = document.getElementById('department-to-add').ej2_instances[0]
        if (this.selectedNodeToAdd) {
          const root = this.storesDataToAdd[0]
          const selectedIds = []
          const children = []
          this.getChilren(this.selectedNodeToAdd, children)
          const parents = []
          this.getParents(root, this.selectedNodeToAdd, parents)
          selectedIds.push(...children.map(i => i.id))
          selectedIds.push(...parents.map(i => i.id))

          const departments = this.getDepartmentsNotAdded.filter(i => selectedIds.includes(i.donvi_id.toString()))
          // this.addDepartmentsToDepartmentsAdded(departments)

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
        if (this.selectedNodeAdded) {
          const root = this.storesDataToAdd[0]
          const selectedIds = []
          const children = []
          this.getChilren(this.selectedNodeAdded, children)
          const parents = []
          this.getParents(root, this.selectedNodeAdded, parents)
          selectedIds.push(...children.map(i => i.id))
          selectedIds.push(...parents.map(i => i.id))
          const departments = this.getDepartmentsAdded.filter(i => selectedIds.includes(i.donvi_id.toString()))
          // this.removeDepartmentsFromDepartmentsAdded(departments)

          const upperCaseKeyData = this.upperCaseKey(departments)
          await this.deleteDepartmentsFromStore(upperCaseKeyData)
          await this.loadData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    }
  },
  mixins: [mixinStore],
  watch: {
    // getSelectedStore: async function () {
    //   this.$root.showLoading(true)
    //   await this.loadData()
    //   this.$root.showLoading(false)
    // },
    // getSelectedDepartment: async function () {
    //   this.$root.showLoading(true)
    //   await this.loadData()
    //   this.$root.showLoading(false)
    // },
    getDepartmentsAdded: function () {
      const nodes = this.buildTreeFromList(this.getDepartmentsAdded, 'ten_dv', 'donvi_id', 'donvi_cha_id')
      const roots = [{
        id: '-1',
        name: 'Root',
        children: nodes,
        isOpen: true
      }]
      this.storesDataAdded = roots
    },
    getDepartmentsNotAdded: function () {
      const nodes = this.buildTreeFromList(this.getDepartmentsNotAdded, 'ten_dv', 'donvi_id', 'donvi_cha_id')
      const roots = [{
        id: '-1',
        name: 'Root',
        children: nodes,
        isOpen: true
      }]
      this.storesDataToAdd = roots
    }
  }
}
</script>

<style scoped>
.form-control.text.h-auto {
    overflow-y: scroll;
}
</style>
