export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <button class="btn" @click.prevent="onBtnDeleteClickedListener" style="background: #C2E6FC;">
                     <span class="-ap icon-close" style="color: red;"></span>
                  </button>
                </div>
              `,
        data() {
          return {
            data: {}
          }
        },
        async mounted() {},
        methods: {
          onBtnDeleteClickedListener() {
            parent.gridViewThietBi_ShownEditor(this.data)
          }
        }
      }
    }
  }
}
