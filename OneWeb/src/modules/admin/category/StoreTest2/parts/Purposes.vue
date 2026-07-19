<template>
  <div class="row">
      <div class="col-sm-6 col-12">
        <div class="box-form">
            <div class="legend-title">Danh sách mục đích đã gán</div>
            <div class="form-control text h-auto" style="height: 300px;">
              <ejs-treeview id="purposesDataAddedTreeView"
              :fields="storesDataAdded"
              :showCheckBox='true'
              :nodeChecked='onSelectAdded'></ejs-treeview>
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
                      <ejs-treeview id="purposesDataToAddTreeView"
                      :fields="storesDataToAdd"
                      :showCheckBox='true'
                      :nodeChecked='onSelectToAdd'></ejs-treeview>
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
      storesDataToAdd: { dataSource: [], id: 'id', text: 'name', child: 'children' },
      storesDataAdded: { dataSource: [], id: 'id', text: 'name', child: 'children' },
      selectedIdsToAdd: [],
      selectedIdsAdded: []
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
      const treeObj = document.getElementById('purposesDataToAddTreeView').ej2_instances[0]
      this.selectedIdsToAdd = this.getPurposesIds(treeObj.checkedNodes, this.storesDataToAdd.dataSource[0])
    },
    onSelectAdded: function (e) {
      const treeObj = document.getElementById('purposesDataAddedTreeView').ej2_instances[0]
      this.selectedIdsAdded = this.getPurposesIds(treeObj.checkedNodes, this.storesDataAdded.dataSource[0])
    },
    getPurposesIds: function (selectedTreeIds, purposeRootNode) {
      const idsCollection = []
      selectedTreeIds.forEach((id) => {
        if (!idsCollection.includes(id)) {
          idsCollection.push(id)
        }

        const node = this.getNodeById(purposeRootNode, id)
        if (node) {
          const parents = []
          this.getParents(purposeRootNode, node, parents)
          parents.forEach(j => {
            if (!idsCollection.includes(j.id) && j.id !== -1) idsCollection.push(j.id)
          })
        }
      })
      return idsCollection
    },
    removePurposesFromList: async function () {
      this.$root.showLoading(true)
      try {
        if (this.selectedIdsAdded.length > 0) {
          const selectedElements = this.getPurposesAdded.filter(i => this.selectedIdsAdded.includes(i.mucdich_id.toString()))
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
        if (this.selectedIdsToAdd.length > 0) {
          const selectedElements = this.getPurposesNotAdded.filter(i => this.selectedIdsToAdd.includes(i.mucdich_id.toString()))
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
      this.storesDataToAdd = this.formatTreeView(this.getPurposesNotAdded)
    },
    reloadTreeViewAdded: function () {
      this.storesDataAdded = this.formatTreeView(this.getPurposesAdded)
    },
    formatTreeView: function (originalList) {
      const originalListIncludedRoot = originalList.map((i) => {
        return {
          mucdich_cha_id: i.mucdich_cha_id === null ? -1 : i.mucdich_cha_id,
          mucdich_id: i.mucdich_id,
          mucdich: i.mucdich,
          unitlevel: i.unitlevel,
          ma_md: i.ma_md
        }
      })

      const nodes = this.buildTreeFromList(originalListIncludedRoot, 'mucdich', 'mucdich_id', 'mucdich_cha_id')
      const root = [{
        id: -1,
        name: 'Root',
        children: nodes,
        expanded: true
      }]
      return { dataSource: root, id: 'id', text: 'name', child: 'children' }
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
    height: 300px !important;
}
</style>
