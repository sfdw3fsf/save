<template>
  <div>
    <div class="input-icon-right">
      <input @keyup="search($event)" type="text" class="form-control" />
      <span class="icon nc-icon-outline ui-1_zoom"></span>
    </div>
    <div class="form-control text h-auto">
      <div class="tree-plus tree-icon" style="height: 400px; overflow: auto">
        <vue-tree-item :current="current" @change="setValue" :options="temp_options" :labelField="labelField" :valueField="valueField"  />
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      temp_options: [],
      current: null
    }
  },
  props: {
    options: Array,
    labelField: String,
    valueField: String
  },
  mounted() {
    this.temp_options = this.options
  },
  watch: {
    options: {
      handler(val) {
        this.temp_options = val
      },
      deep: true
    }
  },
  methods: {
    setValue(value) {
      this.current = value
      this.$emit('change', value)
    },
    search(text) {
      if(text) {
        this.temp_options = this.getObjects(this.options, text)
      } else {
        this.temp_options = this.options
      }
    },
    getObjects(array, event) {
        var result = [];
        array.forEach((a) => {
            var temp = [],
                o = {},
                found = false;

            if (a[this.labelField] && a[this.labelField].toLowerCase().includes(event.target.value.toLowerCase())) {
                o = a;
                found = true;
            }
            if (Array.isArray(a.children)) {
                temp = this.getObjects(a.children, event);
                if (temp.length) {
                    o.children = temp
                    o.isOpen = true
                    if (event.key === "Backspace" || event.key === "Delete") {
                      if(event.target.value.length < 2)
                        o.isOpen = false
                    }
                    found = true;
                }
            }
            if (found) {
                result.push(o);
            }
        });
        return result;
    }
  }
}
</script>
