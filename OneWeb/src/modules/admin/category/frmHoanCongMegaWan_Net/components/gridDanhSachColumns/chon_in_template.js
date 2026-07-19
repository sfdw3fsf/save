export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <input style="width: 20px;height: 20px;" :disabled="!chkChonIn.enabled" v-model="chkChonIn.value" type="checkbox" class="check" id="chkChonIn" @change="chkChonInOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            chkChonIn: {
              enabled: true,
              value: null
            }
          }
        },
        watch: {
          'chkChonIn.value'(val) {
            console.log('chkChonIn.value')

            let index = parent.gridDanhSach.list.findIndex(e => e.phieu_id == this.data.phieu_id)
            parent.gridDanhSach.list[index].selected = val
            console.log(index)
            console.log(this.data)
            console.log(val)
          }
        },
        mounted() {},
        methods: {
          chkChonInOnChanged(e) {
            console.log('chkChonInOnChanged')
            console.log(e)
          }
        }
      }
    }
  }
}
