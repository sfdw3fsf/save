<template>
  <date-picker :disabled="disabled" v-model="model" :value-type="format" :format="format" :type="type" @input="onInput" @blur="onBlur" @input-error="onInputError" @clear="onClear" />
</template>
<script>
/**
 * @author datnt
 */
import DatePicker from 'vue2-datepicker'
import moment from 'moment'
export default {
  components: {
    DatePicker
  },
  props: {
    value: String,
    disabled: {
      type: Boolean,
      default: false
    },
    format: {
      type: String,
      default: 'DD/MM/YYYY' //DD/MM/YYYY HH:mm:ss
    },
    type: {
      type: String,
      default: 'date' //date|datetime|year|month|time|week
    },
    disabled: {
      type: Boolean,
      default: false
    },
    notifyInputError: {
      type: Boolean,
      default: false
    },
    notifyErrorMessage: {
      type: String,
      default: 'Vui lòng nhập ngày DD/MM/YYYY',
      required: false
    }
  },
  data() {
    return {
      model: this.value
    }
  },
  computed: {},
  async mounted() {},
  watch: {
    value(val) {
      this.model = val
    }
  },
  methods: {
    onInput(e) {
      if (e == null) return
      let date = moment(e, this.format)
      let ipHH = date.hours()
      let ipMM = date.minutes()
      let ipSS = date.seconds()
      let now = moment()
      let hh = now.hours()
      let mm = now.minutes()
      let ss = now.seconds()
      date.set({ hour: ipHH ? ipHH : hh, minute: ipMM ? ipMM : mm, second: ipSS ? ipSS : ss })
      this.model = date.format(this.format)
      this.$emit('input', this.model)
    },
    onBlur(e) {},
    onInputError(e) {
      if (moment(e, 'DD/MM/YYYY', true).isValid()) {
        let date = moment(e, 'DD/MM/YYYY')
        let hh = moment().hours() // Number
        let mm = moment().minutes()
        let ss = moment().seconds() // Number
        let now = moment()
        date.set({ hour: now.hours(), minute: now.minutes(), second: now.seconds() })
        this.model = date.format(this.format)
        this.$emit('input', this.model)
      } else {
        if (this.notifyInputError) this.$toast.info(this.notifyErrorMessage)
      }
    },
    onClear() {
      this.model = null
      this.$emit('input', this.model)
    }
  }
}
</script>
