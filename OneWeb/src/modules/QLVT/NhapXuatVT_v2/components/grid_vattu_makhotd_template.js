export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div v-if="cboMaKhoTD.optimize">{{ cboMaKhoTD.value }}</div>
                <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboMaKhoTD.enabled" style="width: 100%;" v-model="cboMaKhoTD.value" :dataSource="cboMaKhoTD.list" dataTextField="MA_KHOTD" dataValueField="KHOTD_ID" @select="cboOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            cboMaKhoTD: {
              optimize: true,
              enabled: true,
              value: null,
              list: []
            }
          }
        },
        async mounted() {
          if (this.cboMaKhoTD.optimize) {
            this.cboMaKhoTD.value = parent.listMaKhoTD.find((e) => e.KHOTD_ID == this.data.MAKHOTD)?.MA_KHOTD
          } else {
            this.cboMaKhoTD.list = parent.listMaKhoTD
            this.cboMaKhoTD.value = this.data.MAKHOTD
          }
        },
        methods: {
          cboOnChanged(target) {
            let list = parent.gridVatTu.list.map((e, idx) => {
              if (e.CTCT_ID == this.data.CTCT_ID) e.MAKHOTD = target.id
              return e
            })
            parent.setLaiGridVatTu(list)
          }
        }
      }
    }
  }
}
