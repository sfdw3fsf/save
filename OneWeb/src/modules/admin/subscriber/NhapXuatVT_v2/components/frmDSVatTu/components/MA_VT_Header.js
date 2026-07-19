export default function(parent) {
  return function() {
    return {
      template: {
        template: `
        <div class="check-action" @click.prevent="btnCheckAll_MA_VT($event)">
           <input type="checkbox" class="check">
           <span class="name">Mã vật tư</span>
       </div>`,
        data() {
          return { data: { checkAll: false } }
        },
        computed: {},
        methods: {
          btnCheckAll_MA_VT($event) {
            //   $event.stopPropagation()
            $event.preventDefault()
            // EventBus.emit('btnCheckAll_MA_VT',this.data)
          }
        }
      }
    }
  }
}