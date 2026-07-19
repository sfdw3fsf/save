<template>
    <div class="main-wrapper">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a ref="tsbtnXemSoLieu" @click="openDialog(1)">
                        <span class="icon one-save"></span> Show popup
                    </a>
                </li>
                <li>
                    <a ref="tsbtnXemSoLieu" @click="openDialog(2)">
                        <span class="icon one-save"></span> Show popup
                    </a>
                </li>
            </ul>
            <PopupSegment ref="popupSegment" />
        </div>
    </div>
</template>
<script>
import PopupSegment from '@/modules/channel/Segment/popupSegment.vue'
export default {
    components: {
        PopupSegment
    },
    methods: {
        openDialog(type) {
            this.$refs.popupSegment.showDialog({
                "hdtb_id": "596657",
                "loai_phandoan" : "1",
                "ghichu" : (type == 1 ? "1212" : "12")
            }, 2, 97, 'edit')
        },
    }
}
</script>