export default function(parent) {
  return function() {
    return {
      template: {
        template: `<vue-date v-model="dtpNgayBH.value" format="DD/MM/YYYY" type="datetime"  @input="onInput"/>`,
        data: function() {
          return { data: {}, dtpNgayBH: { enabled: true, value: null } }
        },
        methods: {
          onInput(value) {
            console.log('onInput')
            console.log(this.data)
            console.log(value)
            console.log(parent)
            parent.gridVatTu.list.find(e => e.ID_GEN == this.data.ID_GEN).HAN_BH = value
          }
        },
        async mounted() {
          // console.log('NgayBHTemplate mounted')
          // console.log(parent)
          this.dtpNgayBH.value = this.data.HAN_BH
        }
      }
    }
  }
}
