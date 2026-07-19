<template src="./index.html"></template>
<script>
import modal from '../components/NhomCongNoNhanVien/NhomCongNoNhanVien'
export default {
    components: {modal},
    mounted() {
        this.$refs['btn-show-modal'].click()
    }
    
}
</script>


