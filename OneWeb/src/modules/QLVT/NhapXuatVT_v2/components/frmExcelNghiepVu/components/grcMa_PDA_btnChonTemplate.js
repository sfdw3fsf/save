export default function(parent) {
  return function() {
    return {
      template: {
        template: `
          <div style="display: flex; justify-content: center">
            <button class="btn btn-main pad4 lh14 w60 btn-primary" style="background-color: blue !important" @click="btnOnClicked">
              <span class="fa fa-plus"></span>
            </button>
          </div>`,
        data() {
          return {
            data: {},
          }
        },
        computed: {
        },
        async mounted() {
        },
        methods: {
          async btnOnClicked() {
            parent.btnChon_PDA_ButtonClick(this.data)
          }
        }
      }
    }
  }
}
