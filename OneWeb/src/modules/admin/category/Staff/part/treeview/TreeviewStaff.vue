<template>
<!--  <div>-->
<!--    <div class="input-icon-right">-->
<!--      <input @keyup="search($event)" type="text" class="form-control"/>-->
<!--      <span class="icon nc-icon-outline ui-1_zoom"></span>-->
<!--    </div>-->
    <treeview-item-staff
      :options="temp_options"
      :labelField="labelField"
      :valueField="valueField"
      :selectedValue="selectedValue"
      @changeSelectedItem="changeSelectedItem"
    />
<!--  </div>-->
</template>
<script>
import TreeviewItemStaff from './TreeviewItemStaff.vue'
export default {
  components: {
    TreeviewItemStaff
  },
  data () {
    return {
      temp_options: [],
      selectedValue: null
    }
  },
  props: {
    options: Array,
    labelField: String,
    valueField: String
  },
  mounted () {
    this.temp_options = this.options
  },
  watch: {
    options: {
      handler (val) {
        this.temp_options = val
        this.selectedValue = val ? val[0][this.valueField] : null
      },
      deep: true
    }
  },
  methods: {
    changeSelectedItem (value) {
      this.selectedValue = value.donvi_id
      this.$emit('changeSelectedItem', value)
    },
    search (text) {
      if (text) {
        this.temp_options = this.getObjects(this.options, text)
      } else {
        this.temp_options = this.options
      }
    },
    getObjects (array, event) {
      var result = []
      array.forEach((a) => {
        let temp = []
        let o = {}
        let found = false

        if (
          a[this.labelField] &&
          a[this.labelField]
            .toLowerCase()
            .includes(event.target.value.toLowerCase())
        ) {
          o = a
          found = true
        }
        if (Array.isArray(a.children)) {
          temp = this.getObjects(a.children, event)
          if (temp.length) {
            o.children = temp
            o.isOpen = true
            if (event.key === 'Backspace' || event.key === 'Delete') {
              if (event.target.value.length < 2) {
                o.isOpen = false
              }
            }
            found = true
          }
        }
        if (found) {
          result.push(o)
        }
      })
      return result
    }
  }
}
</script>
