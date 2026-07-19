export default function(parent) {
  return function() {
    return {
      template: {
        template: `<div>
        <input type="text" class="form-control" v-model="LOHANG" @click="onClick" readonly>
    </div>`,
        data() {
          return {
            data: {},
            LOHANG: ''
          }
        },
        created: async function() {
          this.LOHANG = this.data.LOHANG
        },
        methods: {
          onClick() {
            console.log('lohang click')
            console.log(this.data)
            console.log(parent)
            parent.frmChonLoHang.visible = true
            parent.$refs.frmChonLoHang.show()
          }
        }
      }
    }
  }
}