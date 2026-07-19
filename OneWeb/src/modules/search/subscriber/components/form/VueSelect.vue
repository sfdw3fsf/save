<template>
  <div class="input-container" :class="this.error.length > 0 ? 'error-input' : ''">
    <div class="info-row">
      <div v-if="checkbox" :class="'key' + ' w' + (labelWidth ? labelWidth : '80')">
        <div class="check-action">
          <input v-model="checkbox_value" type="checkbox" class="check" />
          <span class="name">{{ label }}</span>
          <span v-if="required" class="required">*</span>
        </div>
      </div>
      <div v-else :class="'key' + ' w' + (labelWidth ? labelWidth : '80')">
        {{ label }}
        <span v-if="required" class="required">*</span>
      </div>
      <div class="value">
        <div @click="showOption" class="select-custom">
          <input :disabled="disableBox" @input="searchItem" v-model="search_value" class="form-control" :placeholder="current_label" :list="id" :name="id" autocomplete="off">
          <div v-if="show_option && !disable" class="select_option" :id="id">
            <div class="select_option_item" v-bind:key="i"
              v-for="(item, i) in temp_options"
              @click="chooseItem(item)"
              :value="item[valueField]">{{item[labelField]}}</div>
          </div>
        </div>
      </div>
      <div v-if="show_option" class="outside" v-on:click="away()"></div>
    </div>
    <div v-if="this.error.length > 0" class="error">
        <div :class="'error-margin ' + ' w' + (labelWidth ? labelWidth : '80')"></div>
        <span>{{err_message}}</span>
    </div>
  </div>
</template>
<script>
export default {
  data: () => ({
    show_option: false,
    search_value: '',
    temp_options: [],
    checkbox_value: false,
    error: [],
    err_message: ''
  }),
  props: {
    label: String,
    options: Array,
    labelField: String,
    valueField: String,
    checkbox: Boolean,
    value: [String, Number],
    id: String,
    labelWidth: String,
    disable: Boolean,
    required: Boolean,
    validate: Array
  },
  computed: {
    disableBox () {
      if (!this.checkbox_value && this.checkbox || this.disable === true) {
        return true
      } else {
        return false
      }
    },
    current_label () {
      const type = typeof(this.value)
      var label = ''
      this.options.forEach(item => {
        if(type == 'string') {
          if (String(item[this.valueField]) === this.value) {
            label = item[this.labelField]
          }
        }

        if(type == 'number') {
          if (Number(item[this.valueField]) === this.value) {
            label = item[this.labelField]
          }
        }
        
      })

      return label
    }
  },
  mounted() {
    this.temp_options = this.options
  },
  watch: {
    checkbox_value (val) {
      this.reset()
    },
    options: {
      handler() {
        this.temp_options = this.options
      },
      deep: true
    },
    value() {
      this.checkValidate()
    }
  },
  methods: {
    reset() {
      this.search_value = null
      this.$emit('input', null)
    },
    chooseItem (item) {
      this.search_value = null
      this.$emit('input', item[this.valueField]) 
    },
    searchItem (e) {
      if(e.target && e.target.value) {
        this.temp_options = this.options.filter(option => {
          let text = e.target ? e.target.value : e
          text = text.toLowerCase()
          if( option[this.labelField])
            return option[this.labelField].toLowerCase().includes(text)
        })
      } else {
        this.temp_options = this.options
      }
    },
    checkValidate() {
      if(this.validate) {
        var err = []
        this.error = []
        this.validate.forEach(element => {
          if(element === 'required') {
            if(this.value == "" || this.value == null) {
              err.push('required')
              this.err_message = this.label + ' không thể bỏ trống'
            }
          }
        })
        this.error = err
      }
    },
    showOption () {
      if (!this.disableBox) {
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
.error {
    display: block;
    color: #f57f6c;
    font-size: 12px;
  }

  .error-margin {
    display: inline-block;
  }

  .error-input input {
    border: 1px solid #f57f6c;
    animation-name: bounce;
    animation-duration: .5s;
    animation-delay: 0.25s;
  }

  @keyframes bounce {
    0% {
      transform: translateX(0px);
      timing-function: ease-in;
    }
    37% {
      transform: translateX(5px);
      timing-function: ease-out;
    }
    55% {
      transform: translateX(-5px);
      timing-function: ease-in;
    }
    73% {
      transform: translateX(4px);
      timing-function: ease-out;
    }
    82% {
      transform: translateX(-4px);
      timing-function: ease-in;
    }
    91% {
      transform: translateX(2px);
      timing-function: ease-out;
    }
    96% {
      transform: translateX(-2px);
      timing-function: ease-in;
    }
    100% {
      transform: translateX(0px);
      timing-function: ease-in;
    }
  }
  .check-action {
    display: flex;
    align-items: center;
  }
  .select_option {
    max-height: 300px;
    height: fit-content;
    overflow: scroll;
    position: absolute;
    border: 1px solid #E0E0E0;
    z-index: 99999;
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
  .required {
    color: rgb(255, 0, 0);
  }
</style>
