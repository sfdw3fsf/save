export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboHeThong.enabled" :disabled="!cboHeThong.enabled" style="width: 100%;" v-model="cboHeThong.value" :dataSource="cboHeThong.list" dataTextField="tenHeThong" dataValueField="heThongId" @select="cboOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            cboHeThong: {
              enabled: false,
              value: null,
              list: []
            }
          }
        },
        mounted() {
          this.cboHeThong.list = parent.tabLKMK.DSHeThong
          this.cboHeThong.value = this.data.HETHONG_ID
          this.cboHeThong.enabled = parent.cboHeThong.enabled
        },
        methods: {
          cboOnChanged(event) {
            console.log('event')
            console.log(event)
            parent.tabLKMK.DSVatTu[parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)].HETHONG_ID = event.id
          }
        }
      }
    }
  }
}
