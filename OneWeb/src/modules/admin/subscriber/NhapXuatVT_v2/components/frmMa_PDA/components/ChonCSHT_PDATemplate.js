export default function(parent) {
  return function() {
    return {
      template: {
        template: `
        <button class="btn btn-main pad4 lh14 w60 btn-primary"  @click="ChonPDA_ButtonClick">
          <span class="act -ap icon-tick"></span>
        </button>`,
        data() {
          return { data: {} }
        },
        computed: {},
        methods: {
          async ChonPDA_ButtonClick() {
            console.log(parent, ' = parent')
            await parent.btnChon_CSHT_PDA_ButtonClick(this.data)
          }
        }
      }
    }
  }
}