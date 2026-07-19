<template>
  <div class="input-container" :class="this.error.length > 0 ? 'error-input' : ''">
    <div class="info-row">
      <template v-if="label">
        <div v-if="checkbox" :class="'key' + ' w' + (labelWidth ? labelWidth : '80')">
          <div class="check-action">
            <input v-model="checkbox_value" type="checkbox" class="check" />
            <span class="name">{{ label }}</span>
            <span class="required" v-if="required">*</span>
          </div>
        </div>
        <div :class="'key' + ' w' + (labelWidth ? labelWidth : '80')" v-else-if="radio">
          <div class="check-action">
            <input type="radio" class="check" />
            <span class="name">{{ label }}</span>
            <span class="required" v-if="required">*</span>
          </div>
        </div>
        <div :class="'key' + ' w' + (labelWidth ? labelWidth : '80')" v-else>
          <span class="name">{{ label }}</span>
          <span class="required" v-if="required">*</span>
        </div>
      </template>
      <div class="value">
          <input
            :disabled="disableInput"
            v-model="current_value"
            type="text"
            @keyup="checkValidate()"
            class="form-control"
            :class="highlight ? 'highlight' : ''"
            v-on:keyup.enter="$emit('enter', $event.target.value)"
          />
      </div>
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
    checkbox_value: false,
    error: [],
    err_message: ''
  }),
  props: {
    label: String,
    checkbox: Boolean,
    radio: Boolean,
    value: [String, Number],
    labelWidth: String,
    required: Boolean,
    disable: Boolean,
    highlight: Boolean,
    validate: Array
  },
  computed: {
    current_value: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit("input", val);
      }
    },
    disableInput() {
      if ((!this.checkbox_value && this.checkbox) || this.disable === true) {
        return true;
      } else {
        return false;
      }
    }
  },
  methods: {
    checkValidate() {
      if(this.validate) {
        var err = []
        this.error = []
        this.validate.forEach(element => {
          if(element === 'required') {
            if(this.current_value == "" || this.current_value == null)
              err.push('required')
              this.err_message = this.label + ' không thể bỏ trống'
          }
          if(element.includes('min:')) {
            let condit = element.split(':')
            if(condit.length > 1) {
              if(Number(this.current_value) < Number(condit[1])) {
                err.push('min')
                this.err_message = this.label + ' phải lớn hơn ' + Number(condit[1])
              }
            }
          }
          if(element.includes('max:')) {
            let condit = element.split(':')
            if(condit.length > 1) {
              if(Number(this.current_value) > Number(condit[1])) {
                this.compare.max = Number(condit[1])
                err.push('max')
                this.err_message = this.label + ' phải nhỏ hơn ' + Number(condit[1])
              }
            }
          }
        })
        this.error = err
      }
    }
  }
};
</script>

<style scoped>
.check-action {
  display: flex;
  align-items: center;
}
.key {
  white-space: nowrap;
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
