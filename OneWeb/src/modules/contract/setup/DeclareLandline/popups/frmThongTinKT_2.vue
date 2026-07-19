<template>
    <TraCuuThongSoKTModal :thuebao_id="Number(thuebao_id)" :ma_tb="ma_tb" :dichvuvt_id="dichvuvt_id" :kieu="kieu"  ref="traCuuThongSoKTModal"/>
</template>
<script>
import TraCuuThongSoKTModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
export default {
    data() {
        return {
            thuebao_id: "",
            ma_tb: "",
            dichvuvt_id: 1,
            kieu : 0
        }
    },
    components: {
        TraCuuThongSoKTModal
    },
    methods: {
        show() {
            this.$nextTick(() => {
                this.$refs.traCuuThongSoKTModal.showModal()
            })
        }
    }
}
</script>