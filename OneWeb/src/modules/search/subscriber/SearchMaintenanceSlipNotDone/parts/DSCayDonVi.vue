<template>
  <div class="form-control text fullh">
    <div class="tree-plus tree-icon ovauto  fullh">
      <tree-view
        :options="temp_storesData"
        :labelField="'name'"
        :valueField="'id'"
        @changeSelectedItem="onSelect"
        @isOpenChange="isOpenChange"
        :selectedValue="selectedValue"
      ></tree-view>
    </div>
  </div>
</template>

<script>
import TreeView from "@/components/Shared/TreeView/TreeView.vue"
import { mapGetters, mapActions } from "vuex"

export default ({
  components: {
    TreeView
  },
  async created () {
    await this.handleLayDSCayDonVi()
    // console.log("this.dsDonVi ==> ", this.temp_storesData)
  },
  data () {
    return {
      selectedStoreId: 0,
      temp_storesData: [],
      selectedValue: null
    }
  },
  computed: {
    ...mapGetters('SearchMaintenanceSlipNotDone', [
      'getDSCayDonVi'
    ])
  },
  methods: {
    ...mapActions("SearchMaintenanceSlipNotDone", [
      "layDSCayDonvi",
      "setSelectedDonVi"
    ]),
    async handleLayDSCayDonVi () {
      try {
        // await this.layDSCayDonvi()
        const stores = await this.layDSCayDonvi()
        this.$emit('onSelect', stores.ls[0].donvi_id)
        const nodes = this.buildTreeFromList(stores.ls, 'ten_dv', 'donvi_id', 'donvi_cha_id')
        const roots = [{
          id: '-1',
          name: stores.tendv_cha,
          isOpen: true,
          children: nodes
        }]
        this.temp_storesData = Array.from(roots)
      } catch (error) {
        console.error(error)
      }
    },
    // demo () {
    //   const stores = []
    //   const nodes = this.buildTreeFromList(stores, 'ten_kho', 'kho_id', 'kho_cha_id')
    //   console.log(nodes)
    // },
    isOpenChange: function (e) {
    },
    buildTreeFromList: function (
      originalList,
      fieldName,
      fieldId,
      fieldParentId
    ) {
      // remove duplicate element
      const list = []
      // console.log("originalList===>", originalList)
      for (let i = 0; i < originalList.length; i++) {
        const item = originalList[i]
        // console.log("item===>", item)
        if (!list.find(l => l.id.toString() === item[fieldId].toString())) {
          list.push({
            name:
              item[fieldName] === undefined || item[fieldName] === null
                ? ""
                : item[fieldName].toString(),
            id:
              item[fieldId] === undefined || item[fieldId] === null
                ? ""
                : item[fieldId].toString(),
            pid:
              item[fieldParentId] === undefined || item[fieldParentId] === null
                ? ""
                : item[fieldParentId].toString(),
            children: []
          })
        }
      }
      const map = {}
      let node
      const roots = []
      for (let i = 0; i < list.length; i += 1) {
        map[list[i].id] = i // initialize the map
        // list[i].children = [] // initialize the children
      }
      for (let i = 0; i < list.length; i += 1) {
        node = list[i]
        if (node.pid && node.pid !== "-1" && list[map[node.pid]]) {
          // if you have dangling branches check that map[node.parentId] exists
          if (!list[map[node.pid]].children) {
            list[map[node.pid]].children = []
          }
          list[map[node.pid]].children.push(node)
        } else {
          roots.push(node)
        }
      }
      return roots
    },
    onSelect: function (e) {
      const selectedDonVi = this.getDSCayDonVi.find(i => i.donvi_id.toString() === e.id)
      // console.log("selectedDonVi====>", selectedDonVi)
      this.setSelectedDonVi(selectedDonVi)
      this.$emit('onSelect', selectedDonVi)
    }
  }
})
</script>
