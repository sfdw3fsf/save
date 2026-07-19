<template>
    <div>
        <button ref="btn-show-modal" class="btn btn-sm btn-info" hidden>Show modal</button>
        <modal ref="popupTraCuuSeri"/>
    </div>
</template>
<script>
import modal from './TraCuuSeriHoaDonThanhToan.vue'
export default {
    components: { modal },
    mounted() {
        this.$refs["popupTraCuuSeri"].openDialog();
    },
}
</script>
