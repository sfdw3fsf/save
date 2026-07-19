export default function(parent) {
  return function() {
    return {
      template: {
        template: `
                <div v-if="cboLoaiKho.optimize">{{ cboLoaiKho.value }}</div>
                <div v-else style="width: 100%; display: flex; justify-content: center; align-items: center;">
                  <SelectExt :disabled="!cboLoaiKho.enabled" style="width: 100%;" v-model="cboLoaiKho.value" :dataSource="cboLoaiKho.list" dataTextField="tenLk" dataValueField="loaiKhoTdId" @select="cboOnChanged"/>
                </div>
              `,
        data() {
          return {
            data: {},
            cboLoaiKho: {
              optimize: true,
              enabled: true,
              value: null,
              list: []
            }
          }
        },
        watch: {},
        async mounted() {
          if (this.cboLoaiKho.optimize) {
            this.cboLoaiKho.value = parent.cboLoaiKho.list.find((e) => e.loaiKhoTdId == this.data.LOAIKHO)?.tenLk
          } else {
            this.cboLoaiKho.value = this.data.LOAIKHO
            this.cboLoaiKho.list = parent.cboLoaiKho.list
          }
        },
        methods: {
          cboOnChanged(kho) {
            let list = parent.gridVatTu.list.map((e, idx) => {
              if (e.CTCT_ID == this.data.CTCT_ID) e.LOAIKHO = kho.id
              return e
            })
            parent.setLaiGridVatTu(list)
          }
        }
      }
    }
  }
}
