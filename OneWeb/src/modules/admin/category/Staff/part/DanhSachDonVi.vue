<template>
  <div class="col-sm-2 col-12">
    <div class="box-form">
      <div class="legend-title">Danh sách đơn vị</div>
      <div class="tree-plus" style="height: 1015px; overflow: auto">
        <treeview-staff :options="dataSource" labelField="ten_dv" valueField="donvi_id" @changeSelectedItem="change"/>
      </div>
    </div>
  </div>
</template>

<script>
import TreeviewStaff from './treeview/TreeviewStaff.vue'

export default {
  name: 'DanhSachDonVi',
  components: {
    TreeviewStaff
  },
  data: () => ({}),
  computed: {},
  props: {
    dataSource: Array
  },
  methods: {
    change (data) {
      this.$emit('changeSelectedItem', data)
    }
  }
}
</script>
