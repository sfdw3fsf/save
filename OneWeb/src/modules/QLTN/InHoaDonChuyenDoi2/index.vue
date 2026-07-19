<template src="./index.html"></template>
<script>
import InHoaDonChuyenDoi from '../InHoaDonChuyenDoi'
export default {
    components: {
        InHoaDonChuyenDoi
    },
    mounted() {

        this.$refs['btn-show-modal'].click()
    },
    methods: {
    },
}
</script>