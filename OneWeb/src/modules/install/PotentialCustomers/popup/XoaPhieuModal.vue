<template>
    <b-modal
    centered
    ref="popupXoaPhieu"
    size="sm"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
   <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-notepad"></span> VNPT Hải Phòng</div>
                    <div class="close -ap icon-close" @click="hideModal" data-dismiss="modal">
                    </div>
                </div>
                
                <div class="popup-body">
                    <div class="box-form">
                        <div class="center">
                            <div class="inline f30 text-main one-circle-question vcenter"></div> Bạn có muốn xoá phiếu giao việc của nhân viên này không ?
                        </div>
                    </div>
                    <div class="group-buttons -bottom center">
                        <button class="btn btn-main minw100" @click="conFirmModal">Đồng ý</button>
                        <button class="btn btn-second minw100" @click="hideModal">Không đồng ý</button>
                    </div>
                </div>
            </div>
  </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from '../api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
Vue.use(DialogPlugin);
export default {
    name:'GiaoPhieuKhTiemNangModal',
    components: {
        breadcrumb,LoaiHopDong,DichVuVienThong,TrangThaiHD,LOAI_DV
    },
    
    data: function () {
    return {}
    },
    props:{
        dataDelete:{
            type: Object,
            default:null
        },
    },
    methods:{
        handleShowModal(){
           
        },
        showModal() {
            this.$refs["popupXoaPhieu"].show()
        },
        hideModal() {
            this.$refs["popupXoaPhieu"].hide();
        },
        conFirmModal(){
            this.$refs["popupXoaPhieu"].hide();
            this.$emit("acceptModal", this.dataDelete);
        }
    }
}
</script>