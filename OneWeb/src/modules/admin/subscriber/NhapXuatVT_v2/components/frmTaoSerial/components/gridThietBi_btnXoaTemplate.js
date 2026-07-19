export default function(parent) {
  return function() {
    return {
      template: {
        template: `
          <div style="display: flex; justify-content: center">
            <button class="btn btn-main pad4 lh14 w60 btn-primary" @click="CotXoa_Click">
              <span class="-ap icon-close"></span>
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
            this.CotXoa_Click(this.data)
          }
        }
      }
    }
  }
}
