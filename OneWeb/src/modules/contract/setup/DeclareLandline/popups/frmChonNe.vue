<template>
    <b-modal
        id="frmChonNe"
        header-class="popup-header"
        body-class="none-padding"
        size="lg"
        hide-footer
        centered
        title="Chọn số ne"
    >
        <NE @change="getNE" :tramvt_id="tramvt_id" :host_id="host_id" />
    </b-modal>
</template>
<script>
import NE from "@/modules/contract/setup/DeclareLandline/popup/NE.vue";
export default {
    components: {
        NE
    },
    props: ['tramvt_id', 'host_id'],
    data() {
        return {
            options: []
        }
    },
    methods: {
        getNE(data) {
            this.$bvModal.hide('frmChonNe')
            this.$emit("NEchange", data)
        },
        async show() {
            
            this.$bvModal.show('frmChonNe')
        }
    }
}
</script>
<style>
    .none-padding {
        padding: 0 !important;
    }
</style>