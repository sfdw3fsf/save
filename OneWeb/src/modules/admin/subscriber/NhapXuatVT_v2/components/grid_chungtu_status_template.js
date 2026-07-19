export default function(parent) {
  return function() {
    return {
      template: {
        template: `<span :class="icon" v-bind:style="{ color: colorText }"></span>`,
        data() {
          return {
            data: {},
            icon: '',
            colorText: ''
          }
        },
        computed: {
          parent() {
            return this.$parent.$parent.$parent
          }
        },
        async mounted() {
          this.icon = this.data.STATUS == '1' ? 'act -ap  icon-tick f30' : 'text-warning one-alert f20'
          this.colorText = this.data.STATUS == '1' ? 'green' : 'orange'
        },
        methods: {}
      }
    }
  }
}
