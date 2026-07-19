<template>
    <tree-view-item
          :options="temp_options"
          :labelField="labelField"
          :valueField="valueField"
          :selectedValue="temp_selectedValue"
          @changeSelectedItem="changeSelectedItem"
          @isOpenChange="isOpenChange">
    </tree-view-item>
</template>

<script>
import TreeViewItem from './TreeViewItem.vue'
export default {
  components: {
    'tree-view-item': TreeViewItem
  },
  data () {
    return {
      temp_selectedValue: null
    }
  },
  props: {
    options: Array,
    labelField: String,
    valueField: String,
    selectedValue: {
      default: null,
      type: [Number, String]
    }
  },
  computed: {
    temp_options: function () {
      return this.options
    }
  },
  watch: {
    selectedValue: function () {
      this.temp_selectedValue = this.selectedValue
    }
  },
  methods: {
    changeSelectedItem (value) {
      this.temp_selectedValue = value.id
      this.$emit('changeSelectedItem', value)
    },
    isOpenChange (item) {
      this.$emit('isOpenChange', item)
    }
  }
}
</script>
