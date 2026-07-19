<template>
    <div class="m-4">
        <button @click="$bvModal.show('dinh-muc')" class="btn btn-success">
            Định mức khuyến mại
        </button>
        <button @click="$bvModal.show('nguon-khuyen-mai')" class="btn btn-success">
           Nguồn KMLĐ
        </button>
        <b-modal
            id="dinh-muc"
            modal-class="popup-box"
            header-class="popup-header"
            body-class="p-0"
            size="xl"
            hide-footer
            title="Định mức khuyến mại">
            <DinhMucKhuyenMai />
        </b-modal>
        <b-modal
            id="nguon-khuyen-mai"
            modal-class="popup-box"
            header-class="popup-header"
            body-class="p-0"
            size="xl"
            hide-footer
            title="Nguồn KMLĐ">
          <NguonKhuyenMai />
        </b-modal>
    </div>
</template>
<script>
import DinhMucKhuyenMai from './popups/DinhMucKhuyenMai'
import NguonKhuyenMai from './popups/NguonKhuyenMai.vue'
export default {
    components: {
        DinhMucKhuyenMai,
        NguonKhuyenMai
    }
}
</script>
