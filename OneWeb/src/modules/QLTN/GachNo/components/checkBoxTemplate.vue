<template>
  <div>
    <b-form-checkbox
      v-model = "checked"
      class = "check"
      value = '1'
      unchecked-value = '0'
    >
      <span class="name"></span>
    </b-form-checkbox>
  </div>
</template>

<script>
  import Vue from 'vue'
  import EventBus from '@/utils/eventBus'

  export default Vue.extend({
    data: function () {
      return {
        checked: '0'
      }
    },
    computed : {
      getValue () {
        return this.isChecked
      },
    },
    props: {
      isChecked : {
        type: Boolean
      },
    },
    methods: {
      checkAll(e)
      {
        console.log(e)
        let checked = this.checked === '1' ? true : false
        EventBus.$emit('checkAll', checked)
      },
    },
    watch: {
      getValue : {
        handler: function(new_value, old_value) {
          console.log(new_value)
          this.checked = (new_value === true) ? '1' : '0'
        }
      },
      checked : {
        handler: function(new_value, old_value) {
          console.log(new_value)
          let checked = this.checked === '1' ? true : false
          EventBus.$emit('checkAll', checked)
        }
      },
    }
  })
</script>
