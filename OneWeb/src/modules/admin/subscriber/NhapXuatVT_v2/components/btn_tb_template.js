export default function(parent) {
  return function() {
    return {
      template: {
        template: `
        <button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnTB" @click="btnTB_ButtonClick">
          <span class="act -ap icon-chevron-thin-down"></span>
        </button>`,
        data() {
          return { data: {} }
        },
        computed: {
          btnTB: {
            get() {
              if (parseInt(this.data['SPVT'] || 0) > 0 || parseInt(this.data['SPBH'] || 0) > 0) {
                return true
              }
              return false // true =>test
            }
          }
        },
        mounted() {},
        methods: {
          async btnTB_ButtonClick() {
            console.log('this abc')
            console.log(parent)
            // await parent.btnTB_ButtonClick(this.data)
            await parent.btnTB_ButtonClick(this.data)
          }
        }
      }
    }
  }
}
