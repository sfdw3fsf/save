<template>
  <ul>
    <li
      v-for="(item, key) in options"
      v-bind:key="key"
      :class="(item.children ? 'haschild' : '') + (item.isOpen ? ' open' : '')"
    >
      <a
        :class="selectedValue === item[valueField] ? 'selected highlight1' : ''"
        class="tree-link"
        @click.prevent="changeSelectedItem($event, item)"
      >
        <span class="icon nc-icon-glyph files_single-content-02 text-main" />
        {{ item[labelField] }}
      </a>
      <template v-if="item.children && item.children.length > 0">
        <treeview-item-staff
          :selectedValue="selectedValue"
          v-on="$listeners"
          :options="item.children"
          :labelField="labelField"
          :valueField="valueField"
        />
      </template>
    </li>
  </ul>
</template>
<script>
export default {
  props: {
    options: Array,
    labelField: String,
    valueField: String,
    selectedValue: {
      default: null,
      type: [Number, String]
    }
  },
  name: 'treeview-item-staff',
  methods: {
    changeSelectedItem (event, item) {
      if (event.offsetX < 0) {
        event.target.closest('li').classList.toggle('open')
      } else {
        this.current = item.donvi_id
        this.$emit('changeSelectedItem', item)
      }
    }
  }
}
</script>
<style>
.tree-link {
  cursor: pointer;
}
.tree-link.active {
  color: blue;
}
</style>
