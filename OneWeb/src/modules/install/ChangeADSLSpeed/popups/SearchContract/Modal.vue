<template>
    <b-modal
    ref="popupSearchContract"
    size="sm"
    body-class="p-0"
    hide-header
    hide-header-close
    @show="handleShowModal"
    hide-footer>
    <div class="modal-content popup-box">
        <div class="popup-body">
            <SearchContract ref="SearchContract"/>
            
        </div>
    </div>
        
    </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import SearchContract from '../../../../search/subscriber/SearchContract/SearchContract.vue'
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV, TrangThaiTB, LoaiHinhTB} from "@/const/enums";
Vue.use(DialogPlugin);
export default {  
    components: {
        LoaiHopDong,
        DichVuVienThong,
        TrangThaiHD,
        LOAI_DV,
        TrangThaiTB,
        LoaiHinhTB,
        SearchContract,
    },
    data: function () {
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs['popupSearchContract'].show()
        },
        hideModal() {
            this.$refs['popupSearchContract'].hide()
        },
    }
}
</script>