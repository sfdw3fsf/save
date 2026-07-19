export default function(parent) {
  return function() {
    return {
      template: {
        template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" style="background-color: blue !important" @click="btnThemVT_ButtonClick">
        <span class="fa fa-plus"></span>
    </button>`,
        data: function() {
          return { data: {} }
        },
        methods: {
          async btnThemVT_ButtonClick() {
            // EventBus.emit('btnThemVT_ButtonClick',this.data)
            await parent.btnThemVT_ButtonClick(this.data)
          }
        }
      }
    }
  }
}
