<template src="./index.html"></template>
<script>
import PopupPhieuHuy from './Modal'
export default {
    components: {
        PopupPhieuHuy
    },
    methods: {
        showPopup() {
            this.$refs.dlgPhieuHuy.showModal()
        }
    }
}
</script>