export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboChatLuong.enabled" style="width: 100%;" v-model="cboChatLuong.value" :dataSource="cboChatLuong.list" dataTextField="chatLuong" dataValueField="chatLuongId" @select="cboOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            cboChatLuong: {
              enabled: true,
              value: null,
              list: []
            }
          }
        },
        mounted() {
          this.cboChatLuong.list = parent.cboChatLuong.list
          this.cboChatLuong.value = this.data.CHATLUONG_ID
        },
        methods: {
          async cboOnChanged(event) {
            console.log('event')
            console.log(event)
            let index = parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)
            parent.tabLKMK.DSVatTu[index].CHATLUONG_ID = event.id
          }
        }
      }
    }
  }
}
