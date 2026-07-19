<template src="./index.template.html"></template>
<script>
import report from "./report"
export default {
    components: {report},
    props:{
        modalId: String
    },
    data() {
        return {
          data: null,
          dsBaoCao: []
        }
    },
    methods: {
        onModalHidden() {
           this.dsBaoCao = []
           this.data = null
        },
        async onModalShown() {
          try {
              this.loading(true)
              await this.layDsBaoCao(this.data)
          } catch (err) {

          } finally {
              this.loading(false)
          }

          if (this.dsBaoCao.length == 0) {
            this.$root.toastError('Không có dữ liệu hiển thị báo cáo !')
            this.$bvModal.hide(this.modalId)
            return
          }

          this.$refs['ref-report'].setDataSource(this.dsBaoCao)
          setTimeout(() => { this.$refs['ref-report'].generatePdf() }, 1000)
        },
        setData(data) {
          this.data = data
        },
        async layDsBaoCao(data) {
          try {
              let response = await this.$root.context.post(`/web-suco/ketqua-xuly-suco/lay-ds-inphieu-suco`, data)

              if (response.error_code === 'BSS-00000000' && response.data != null) {
                  this.dsBaoCao = response.data
              } else {
                  this.dsBaoCao = []
              }
          } catch (err) {
              this.dsBaoCao = []
          }
        },
     }
}
</script>

