<template src="./index.template.html"></template>
<script>
import report from "./report"
export default {
    components: {report},
    data() {
        return {
          data: null,
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
           this.data = null
        },
        async onModalShown() {
          this.$refs['ref-in-phieu'].setData(this.data)
          setTimeout(() => { this.$refs['ref-in-phieu'].generatePdf() }, 1000)
        },
        setData(data) {
          this.data = data
        },
     }
}
</script>

