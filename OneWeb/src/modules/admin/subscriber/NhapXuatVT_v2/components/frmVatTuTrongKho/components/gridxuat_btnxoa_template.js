export default function(parent) {
  return function() {
    return {
      template: {
        template: `
          <button class="btn btn-main pad4 lh14 w60 btn-danger" style="display: flex; justify-content: center" @click="btnXoaVT_ButtonClick">
            <span class="fa fa-minus"></span>
          </button>`,
        data: function() {
          return { data: {} }
        },
        methods: {
          async btnXoaVT_ButtonClick() {
            await parent.gridXuatBtnXoaClicked(this.data)
          }
        }
      }
    }
  }
}