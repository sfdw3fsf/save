export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div v-if="cboChatLuong.optimize">{{ cboChatLuong.value }}</div>
                <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboChatLuong.enabled" style="width: 100%;" v-model="cboChatLuong.value" :dataSource="cboChatLuong.list" dataTextField="chatLuong" dataValueField="chatLuongId" @select="cboOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            cboChatLuong: {
              optimize: true,
              enabled: true,
              value: null,
              list: []
            }
          }
        },
        mounted() {
          // console.log('chatluong mounted')
          // console.log(this.data)
          // console.log(parent.cboChatLuong.list)
          if(this.cboChatLuong.optimize) {
            this.cboChatLuong.value =  parent.cboChatLuong.list.find(e => e.chatLuongId == this.data.CHATLUONG_ID)?.chatLuong
          } else {
            this.cboChatLuong.list = parent.cboChatLuong.list
            this.cboChatLuong.value = this.data.CHATLUONG_ID
          }
        },
        methods: {
          async cboOnChanged(event) {
            // console.log('event')
            // console.log(event)
            let index = parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)
            parent.tabLKMK.DSVatTu[index].CHATLUONG_ID = event.id
          }
        }
      }
    }
  }
}
