export default function(parent) {
  return function() {
    return {
      template: {
        template: `<button class="btn btn-main pad4 lh14 w60 btn-primary"  @click="chonBBBG_ButtonClick">
          <span class="act -ap icon-tick"></span>
      </button>`,
        data() {
          return { data: {} }
        },
        computed: {},
        methods: {
          async chonBBBG_ButtonClick() {
            console.log(this.$parent.$parent.$parent, ' = parent')
            await parent.btn_chonBBBG_ButtonClick(this.data)
          }
        }
      }
    }
  }
}
