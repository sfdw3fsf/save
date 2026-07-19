export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboDongTB.enabled" :disabled="!cboDongTB.enabled" style="width: 100%;" v-model="cboDongTB.value" :dataSource="cboDongTB.list" dataTextField="tenDongTbi" dataValueField="dongTbiId" @select="cboOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            cboDongTB: {
              enabled: false,
              value: null,
              list: [],
            }
          }
        },
        mounted() {
          this.cboDongTB.list = parent.tabLKMK.DSDongTB
          this.cboDongTB.value = this.data.DONGTBI_ID
          this.cboDongTB.enabled = parent.cboDongTB.enabled
        },
        methods: {
          cboOnChanged(event) {
            parent.tabLKMK.DSVatTu[parent.tabLKMK.DSVatTu.findIndex((e) => this.data.CTCT_ID == e.CTCT_ID && this.data.LOHANG == e.LOHANG)].DONGTBI_ID = event.id
          }
        }
      }
    }
  }
}
