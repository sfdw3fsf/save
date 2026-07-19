export default function(parent) {
  return function() {
    return {
      template: {
        template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" style="background-color: blue !important" @click="btnXoaVT_ButtonClick">
        <span class="fa fa-minus"></span>
    </button>`,
        data: function() {
          return { data: {} }
        },
        methods: {
          async btnXoaVT_ButtonClick() {
            await this.$parent.$parent.$parent.btnXoaVT_ButtonClick(this.data)
          }
        }
      }
    }
  }
}