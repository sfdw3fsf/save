<template>
    <div class="tree-plus tree-icon ovauto" style="height: 750px;">
              <tree-view
              :options="temp_storesData"
              :labelField="'name'"
              :valueField="'id'"></tree-view>
            </div>
</template>

<script>
import TreeView from './tree/TreeView.vue'
export default {
  components: {
    TreeView
  },
  data () {
    return {
      selectedStoreId: 0,
      temp_storesData: [{
        id: 1,
        isOpen: true,
        name: "Root",
        children: [{
          id: 2,
          isOpen: true,
          name: "Child"
        }]
      }],
      selectedValue: null
    }
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
</style>
