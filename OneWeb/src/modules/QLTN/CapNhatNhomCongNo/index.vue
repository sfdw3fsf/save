<template src="./index.html"></template>
<script>
import modal from '../components/CapNhatNhomCongNo/CapNhatNhomCongNo'
export default {
    components: {modal},
    mounted() {
        this.$refs['btn-show-modal'].click()
    }
    
}
</script>


