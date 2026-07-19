export default function(parent) {
  return function() {
    return {
      template: {
        template: `
            <div class="input-group-prepend w-100">
            <div class="input-group-text  w-100 d-flex justify-content-between">
                <div>Tổng:</div>
                <div class="tright pr-2">{{data.Sum}}</div>
            </div>
            </div>
            `,
        data() {
          return {
            data: {}
          }
        },
        computed: {},
        async mounted() {},
        methods: {
          async btnOnClicked() {
            this.CotXoa_Click(this.data)
          }
        }
      }
    }
  }
}
