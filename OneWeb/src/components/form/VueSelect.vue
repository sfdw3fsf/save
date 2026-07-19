<template>
  <div class="info-row">
    <div v-if="checkbox" :class="'key' + ' w' + (labelWidth ? labelWidth : '80')">
      <div class="check-action">
        <input v-model="checkbox_value" type="checkbox" class="check" />
        <span class="name">{{ label }}</span>
      </div>
    </div>
    <div v-else :class="'key' + ' w' + (labelWidth ? labelWidth : '80')">{{ label }}</div>
    <div class="value">
      <div @click="showOption" class="select-custom">
        <input :disabled="disable" @input="searchItem" v-model="search_value" class="form-control" :placeholder="current_label" :list="id" :name="id" autocomplete="off">
        <div v-show="show_option" class="select_option" :id="id">
          <div class="select_option_item" v-bind:key="i"
            v-for="(item, i) in temp_options"
            @click="chooseItem(item)"
            :value="item[valueField] ? item[valueField] : item['id']">{{ item[labelField] ? item[labelField] : item["name"] }}</div>
        </div>
      </div>
    </div>
    <div v-if="show_option" class="outside" v-on:click="away()"></div>
  </div>
</template>
<script>
export default {
  data: () => ({
    show_option: false,
    current_label: '',
    current_value: '',
    search_value: '',
    temp_options: [],
    checkbox_value: false
  }),
  props: {
    label: String,
    options: Array,
    labelField: String,
    valueField: String,
    checkbox: Boolean,
    value: [String, Number],
    id: String,
    labelWidth: String
  },
  created () {
    this.current_value = this.value ? this.value : ''
    this.temp_options = this.options || []
  },
  computed: {
    option_length () {
      if (this.options) {
        return this.options.length
      }
      return 0
    },
    disable () {
      if (!this.checkbox_value && this.checkbox) {
        return true
      } else {
        return false
      }
    }
  },
  watch: {
    options: {
      deep: true,
      handler () {
        if (this.options.length > 0) {
          this.temp_options = this.options
        }
      }
    },
    value () {
      this.current_value = this.value ? this.value : ''
    },
    checkbox_value (val) {
      if (!val) {
        this.search_value = ''
        this.current_label = ''
      }
    },
    current_value (val) {
      this.options.forEach(item => {
        if (item[this.valueField] === this.current_value) {
          this.current_label = item[this.labelField] ? item[this.labelField] : ''
          this.$emit('input', String(this.current_value))
        }
      })
    },
    option_length () {
      this.options.forEach(item => {
        if (item[this.valueField] === this.current_value) {
          this.current_label = item[this.labelField] ? item[this.labelField] : ''
          this.$emit('input', String(this.current_value))
        }
      })
    }
  },
  methods: {
    chooseItem (item) {
      this.search_value = ''
      this.searchItem('')
      this.current_value = item[this.valueField] ? item[this.valueField] : item['name']
    },
    searchItem (e) {
      if (e.target && e.target.value) {
        this.temp_options = this.options.filter(option => {
          let text = e.target ? e.target.value : e
          text = text.toLowerCase()
          if (option[this.labelField]) { return option[this.labelField].toLowerCase().includes(text) }
        })
      }
    },
    showOption () {
      if (!this.disable) {
        this.show_option = !this.show_option
      }
    },
    away () {
      this.show_option = false
    }
  }
}
</script>
<style scoped>
  .select_option {
    max-height: 300px;
    height: fit-content;
    overflow: scroll;
    position: absolute;
    border: 1px solid #E0E0E0;
    z-index: 9999;
    width: 100%;
    background: #fff;
  }
  .select_option_item {
    padding: 10px 10px;
    border-bottom: 1px solid #f3f1f1;
  }
  .select_option_item:last-child {
    border: 0;
  }
  .select_option_item:hover {
    background: #BAE7FF;
  }
  ::placeholder {
  color: #000;
  opacity: 1; /* Firefox */
  }

  :-ms-input-placeholder { /* Internet Explorer 10-11 */
  color: #000;
  }

  ::-ms-input-placeholder { /* Microsoft Edge */
  color: #000;
  }

  .outside {
    width: 100vw;
    height: 100vh;
    position: fixed;
    top: 0px;
    left: 0px;
    background: #e9ecef;
    opacity: 0.5;
    z-index: 1;
  }
</style>
