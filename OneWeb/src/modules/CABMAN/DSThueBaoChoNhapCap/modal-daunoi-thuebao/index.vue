<template src="./index.template.html"></template>
<style>
    .hoso-dauchuyen-daunoi-thuebao .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
</style>
<script>
import DauNoiThueBao from '../../DauNoiThueBao/index.vue'
export default {
    components: { 
        DauNoiThueBao 
    },
    data() {
        return {
            data: null
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
            this.$emit('hidden')
        },
        setData(data) {
            this.data = data
        },
        onSuccessNhapCapThueBao() {
            this.$emit('change')
        }
    }
}
</script>
