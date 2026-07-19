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
        }
    },
    methods: {
        onModalHidden() {
           this.data = null
        },
        async onModalShown() {
          this.$refs['ref-in-suco'].setData(this.data)
          setTimeout(() => { this.$refs['ref-in-suco'].generatePdf() }, 1000)
        },
        setData(data) {
          this.data = data
        },
     }
}
</script>

