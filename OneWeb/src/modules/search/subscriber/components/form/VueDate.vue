<template>
  <div class="input-container" :class="this.error.length > 0 ? 'error-input' : ''">
    <div class="info-row">
      <template v-if="label">
        <template v-if="checkbox">
          <div :class="'key' + ' w' + labelWidth">
            <div class="label-checkbox">
              <span class="name">{{ label }}
              </span>
              <span v-if="required" class="required">*</span>
            </div>
          </div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input v-model="checkbox_value" type="checkbox" class="check" />
              <span class="name"></span>
            </div>
          </div>
        </template>
        <div v-else :class="'key' + ' w' + labelWidth">{{ label }} <span v-if="required" class="required">*</span></div>
      </template>
      <div class="value">
          <date-picker
          :format="dateFormat"
          :value-type="dateFormat"
          :append-to-body="true"
          :time-title-format="dateFormat"
          v-model="time"
          :disabled="disable_value"
          :type="typeFormat">
            <template #icon-calendar>
              <span class="icon one-calendar"></span>
            </template>
          </date-picker>
      </div>
    </div>
    <div v-if="this.error.length > 0" class="error">
        <div :class="'error-margin ' + ' w' + (labelWidth ? labelWidth : '80')"></div>
        <span>{{err_message}}</span>
    </div>
  </div>
</template>
<script>
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/locale/vi.js';
import 'vue2-datepicker/index.css';
export default {
  data: () => ({
    checkbox_value: false,
    error: [],
    err_message: ''
  }),
  components: { DatePicker },
  props: {
    label: String,
    value: String,
    checkbox: Boolean,
    labelWidth: String,
    format: String, // default 'DD/MM/YYYY HH:mm:ss'
    type: String, // date|datetime|year|month|time|week : default 'datetime'
    required: Boolean,
    disable: Boolean,
    validate: Array
  },
  computed: {
    dateFormat() {
      return this.format ? this.format : 'DD/MM/YYYY HH:mm:ss'
    },
    typeFormat() {
      return this.type ? this.type : 'datetime'
    },
    time: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit("input", val);
      }
    },
    disable_value() {
      if((this.checkbox && !this.checkbox_value) || this.disable) {
        return true
      }
      return false
    }
  },
  methods: {
    checkValidate() {
      var err = []
      this.error = []
      if(this.validate) {
        this.validate.forEach(element => {
          if(element === 'required') {
            if(!this.time)
              err.push('required')
              this.err_message = this.label + ' không thể bỏ trống'
          }
        })
        this.error = err
      }
    }
  }
};
</script>
<style>
  .mx-datepicker {
    width: 100% !important;
  }
  .mx-icon-calendar {
    font-style: normal !important;
  }
  .required {
    color: red;
  }
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
</style>
