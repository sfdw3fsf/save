<template>
  <div class="row">
      <div class="col-sm-6 col-12">
        <div class="box-form">
            <div class="legend-title">Danh sách mục đích đã gán</div>
            <div class="form-control text h-auto">
                <!-- <ejs-treeview
                id='purposes-added'
                :fields="fieldsAdded"></ejs-treeview> -->
                <div class="tree-plus tree-icon ovauto" style="height: 200px;">
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
            <div class="legend-title">Danh sách mục đích</div>
            <div class="box-move-select-table">
                <div class="actions w40 padl0">
                    <button class="btn" @click="removePurposesFromList">
                        <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn" @click="addPurposesToList">
                        <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                </div>
                <div class="box-col" style="width:calc(100% - 40px);">
                    <div class="form-control text h-auto">
                      <!-- <ejs-treeview
                      id='purposes-to-add'
                      :fields="fieldsToAdd"></ejs-treeview> -->
                      <div class="tree-plus tree-icon ovauto" style="height: 200px;">
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
      storesDataAdded: [],
      selectedNodeAdded: null,
      selectedNodeToAdd: null
    }
  },
  mixins: [mixinStore],
  methods: {
    ...mapActions('purposesCommon', [
      'loadPurposeNotAdded',
      'loadPurposeAdded',
      'addPurposesToPurposesAdded'
    ]),
    ...mapActions('storesCommon', [
      'insertPurposesToStore',
      'deletePurposesFromStore'
    ]),
    onSelectToAdd: function (e) {
      this.selectedNodeToAdd = e
    },
    onSelectAdded: function (e) {
      this.selectedNodeAdded = e
    },
    removePurposesFromList: async function () {
      this.$root.showLoading(true)
      try {
        // const treeObj = document.getElementById('purposes-added').ej2_instances[0]
        if (this.selectedNodeAdded) {
          const root = this.storesDataAdded[0]
          const selectedIds = []
          const children = []
          this.getChilren(this.selectedNodeAdded, children)
          const parents = []
          this.getParents(root, this.selectedNodeAdded, parents)
          selectedIds.push(...children.map(i => i.id))
          selectedIds.push(...parents.map(i => i.id))
          const selectedElements = this.getPurposesAdded.filter(i => selectedIds.includes(i.mucdich_id.toString()))
          // this.removePurposesFromPurposesAdded(selectedElements)

          const elements = []
          for (let i = 0; i < selectedElements.length; i++) {
            elements.push({
              KHO_ID: this.getSelectedStore.kho_id,
              mucdich_id: selectedElements[i].mucdich_id
            })
          }
          await this.deletePurposesFromStore(elements)
          await this.loadData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    addPurposesToList: async function () {
      this.$root.showLoading(true)
      try {
        // const treeObj = document.getElementById('purposes-to-add').ej2_instances[0]
        if (this.selectedNodeToAdd) {
          const root = this.storesDataToAdd[0]
          const selectedIds = []
          const children = []
          this.getChilren(this.selectedNodeToAdd, children)
          const parents = []
          this.getParents(root, this.selectedNodeToAdd, parents)
          selectedIds.push(...children.map(i => i.id))
          selectedIds.push(...parents.map(i => i.id))
          const selectedElements = this.getPurposesNotAdded.filter(i => selectedIds.includes(i.mucdich_id.toString()))
          // this.addPurposesToPurposesAdded(selectedElements)

          const elements = []
          for (let i = 0; i < selectedElements.length; i++) {
            elements.push({
              KHO_ID: this.getSelectedStore.kho_id,
              mucdich_id: selectedElements[i].mucdich_id
            })
          }
          await this.insertPurposesToStore(elements)
          await this.loadData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    loadData: async function () {
      await this.loadPurposeNotAdded({
        kho_id: this.getSelectedStore.kho_id,
        phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
      })

      await this.loadPurposeAdded({
        kho_id: this.getSelectedStore.kho_id,
        phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
      })
    },
    reloadTreeViewNotAdd: function () {
      const nodes = this.buildTreeFromList(this.getPurposesNotAdded, 'mucdich', 'mucdich_id', 'mucdich_cha_id')
      const roots = [{
        id: '-1',
        name: 'Root',
        children: nodes,
        isOpen: true
      }]
      this.storesDataToAdd = roots
    },
    reloadTreeViewAdded: function () {
      const nodes = this.buildTreeFromList(this.getPurposesAdded, 'mucdich', 'mucdich_id', 'mucdich_cha_id')
      const roots = [{
        id: '-1',
        name: 'Root',
        children: nodes,
        isOpen: true
      }]
      this.storesDataAdded = roots
    }
  },
  computed: {
    ...mapGetters('storesCommon', [
      'getSelectedStore',
      'getSelectedDepartment'
    ]),
    ...mapGetters('purposesCommon', [
      'getPurposesNotAdded',
      'getPurposesAdded'
    ])
  },
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
    getPurposesNotAdded: function () {
      this.reloadTreeViewNotAdd()
      this.reloadTreeViewAdded()
    },
    getPurposesAdded: function () {
      this.reloadTreeViewNotAdd()
      this.reloadTreeViewAdded()
    }
  }
}
</script>

<style scoped>
.form-control.text.h-auto {
    overflow-y: scroll;
}
</style>
