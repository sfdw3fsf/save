export default function(parent, mode) {
  return function() {
    return {
      template: {
        template: `
        <input type="checkbox" id="vehicle1" name="vehicle1" v-model="ckb.value" style="display:flex; justify-content: center; height: 20px; width: 20px;z-index: 9999" @change="ckbChange">
              `,
        data() {
          return {
            data: {},
            ckb: {
              value: null
            }
          }
        },
        async mounted() {
          this.ckb.value = this.data.SELECTED
        },
        methods: {
          ckbChange(e) {
            if(mode == 'row')
              parent.gridVatTuCheckboxSelected(this.data, this.ckb.value)
            
            if(mode == 'header')
            parent.gridVatTuCheckboxTitleSelected(this.ckb.value)
          } 
        }
      }
    }
  }
}
