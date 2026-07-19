export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboMaKhoTD.enabled" style="width: 100%;" v-model="cboMaKhoTD.value" :dataSource="cboMaKhoTD.list" dataTextField="MA_KHOTD" dataValueField="KHOTD_ID" @select="cboOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            cboMaKhoTD: {
              enabled: true,
              value: null,
              list: []
            }
          }
        },
        async mounted() {
          this.cboMaKhoTD.list = parent.cboMaKhoTD.list.filter(e => e.LOAIKHO_TD_ID == this.data.LOAIKHO)
          this.cboMaKhoTD.value = this.data.MAKHOTD
        },
        methods: {
          cboOnChanged(target) {
            let list = parent.gridVatTu.list.map((e, idx) => {
              if(e.CTCT_ID == this.data.CTCT_ID) e.MAKHOTD = target.id
              return e
            })
            parent.setLaiGridVatTu(list)
          }
        }
      }
    }
  }
}
