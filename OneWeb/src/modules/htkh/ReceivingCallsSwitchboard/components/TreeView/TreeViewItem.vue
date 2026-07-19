<template>
    <ul class="">
        <li v-for="(item, key) in temp_options"
            v-bind:key="key"
            :class="(item.children ? 'haschild' : '') + (item.isOpen ? ' open' : '')">
            <a @click.prevent="item.isOpen = !item.isOpen; changeSelectedItem($event, item)"
            class="tree-link"
            :class="selectedValue == item[valueField] ? 'selected highlight1' : ''">{{ item[labelField] }}</a>
            <template v-if="item.children && item.children.length > 0">
                <tree-view-item
                :selectedValue="selectedValue"
                v-on="$listeners"
                :options="item.children"
                :labelField="labelField"
                :valueField="valueField">
                </tree-view-item>
            </template>
        </li>
    </ul>
</template>

<script>
export default {
  name: 'tree-view-item',
  props: {
    options: Array,
    labelField: String,
    valueField: String,
    selectedValue: {
      default: null,
      type: [Number, String]
    }
  },
  data(){
    return {
      temp_selectedValue: null
    }
  },
  computed: {
    temp_options: function () {
      return this.options
    }
  },
  watch: {
    selectedValue: function () {
      let selected = this.temp_options.find(el => el[this.valueField] == this.selectedValue);
      if(selected && selected[this.valueField] != this.temp_selectedValue){
        this.$emit('changeSelectedItem', selected)
      }
    }
  },
  methods: {
    changeSelectedItem (event, item) {
      if (event.offsetX < 0) {
        event.target.closest('li').classList.toggle('open')
        this.$emit('isOpenChange', item)
      } else {
        // this.current = item.donvi_id
        this.temp_selectedValue = item[this.valueField]
        this.$emit('changeSelectedItem', item)
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
</style>
