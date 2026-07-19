<template>
    <ul>
      <li v-for="(item, key) in options" v-bind:key="key" :class="(item.children ? 'haschild' : '') + (item.isOpen ? ' open' : '')">
        <a :class="current == item[valueField] ? 'active' : ''" class="tree-link" @click="change($event, item)"> {{item[labelField]}} </a>
        <vue-tree-item :current="current" v-on="$listeners" :options="item.children" :labelField="labelField" :valueField="valueField"  />
      </li>
    </ul>
</template>
<script>
export default {
  props: {
    options: Array,
    labelField: String,
    valueField: String,
    current: [String, Number]
  },
  name: 'vue-tree-item',
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
<style>
  .tree-link {
    cursor: pointer;
  }
  .tree-link.active {
    color: blue;
  }
</style>
