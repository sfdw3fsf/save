<template>
    <KhoaMoPayTV ref="popupKMPTV" />
</template>
<script>
import KhoaMoPayTV from '@/modules/contract/setup/BlockOpenPayTV/KhoaMoPayTV.vue'
export default {
    name:'LockUnlockPayTV',
    components:{
        KhoaMoPayTV
    },
    mounted () {
        //this.$refs.popupKMPTV.showModal()
    },
    data() {
        return {

        }
    },
    methods: {

    }
}
</script>
