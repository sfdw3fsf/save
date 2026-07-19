<template>
    <ul>
      <li v-for="(item, key) in options" v-bind:key="key" :class="(item.children ? 'haschild' : '') + (item.isOpen ? ' open' : '')">
        <a :class="(current == item[valueField] ? 'active' : '') + (parent ? 'parent' : '')" class="tree-link" @click="change($event, item)"> {{item[labelField]}} </a>
        <vue-tree-item-menu :current="current" v-on="$listeners" :options="item.children" :labelField="labelField" :valueField="valueField"  />
      </li>
    </ul>
</template>
<script>
export default {
  props: {
    options: Array,
    labelField: String,
    valueField: String,
    parent: Boolean,
    current: [String, Number]
  },
  name: 'vue-tree-item-menu',
  methods: {
    change(event, item) {
      if (event.offsetX < 0) {
        event.target.closest('li').classList.toggle('open')
      } else {
          this.$emit('change', item[this.valueField])
      }
    }
  }
}
</script>
<style scoped>
  .tree-link {
    cursor: pointer;
  }
  .tree-link.active {
    color: blue;
  }
  .parent {
      font-weight: bold;
      font-size: 16px;
  }
</style>
