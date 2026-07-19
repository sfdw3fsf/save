<template>
    <b-modal
        ref="dlgBangGiaKenhTSL"
        size="xl"
        body-class="p-0"
        scrollable
        title="Danh sách bảng giá kênh TSL tính chi phí cho TC QLDA"
        hide-footer>
        <frmBangGiaKenhTSL 
            ref="frmBangGiaKenhTSL">
        </frmBangGiaKenhTSL>
    </b-modal>
</template>
<script>
import BangGiaKenhTSL from '../../BangGiaKenhTSL/main.vue'
export default {
    name:'PopupBangGiaKenhTSL',
    components:{
        frmBangGiaKenhTSL: BangGiaKenhTSL
    },
    
    methods:{
        showModal() {
            this.$refs['dlgBangGiaKenhTSL'].show();
        },
        
        hideModal() {
            this.$refs['dlgBangGiaKenhTSL'].hide();
        }
    }
}
</script>
