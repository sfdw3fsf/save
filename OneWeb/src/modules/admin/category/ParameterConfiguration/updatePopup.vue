<template>
        <ejs-dialog
        :allowDragging="true"
        :animationSettings="animationSettings"
        :enableResize="false"
        :position="{X:'center', Y:'center'}"
        :visible="false"
        ref="popupUpdate"
        :showCloseIcon="false"
        width="40%"
        minHeight="200"
        :isModal='true'
        :target="target">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-file-plus"></span> Cập nhật tham số hệ thống
                </div>
                <div class="close -ap icon-close" style="cursor: pointer;" @click="hideModal"></div>
            </div>
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="popup-body">
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="box-form">
                            <div class="row">
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key">Mã TS<span style="color:red">*</span></div>
                                        <div class="value">
                                            <input disabled type="text" class="form-control" v-model="txtMaTS" @change="(e)=>(txtMaTS = e.target.value)">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key">Tên TS<span style="color:red">*</span></div>
                                        <div class="value">
                                            <input type="text" class="form-control" v-model="txtTenTS" @change="(e)=>(txtTenTS = e.target.value)">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key">Ghi chú<span style="color:red">*</span></div>
                                        <div class="value">
                                            <input disabled type="text" class="form-control" v-model="txtGhiChu" @change="(e)=>(txtGhiChu = e.target.value)">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from 'moment';
import api from './api'
import apiHelper from "./api.helper"
export default {
    name: "updatePopup",
    components: {
        api,
        ActionTop,
        apiHelper,
        moment,
        breadcrumb,
    },
    data(){
        return {
            Loading: false,
            actions: [
                {
                    id: "capnhat",
                    name: "Cập nhật",
                    active: true,
                    icon_class: "one-save"
                }
            ],
            txtMaTS: "",
            txtTenTS: "",
            txtGhiChu: ""
        }
    },
    watch: {
        Loading: function(val) {
            this.$root.showLoading(val)
        }
    },
    created(){

    },
    methods: {
        onActionClick(action){
            switch(action.id){
                case "capnhat":
                    this.CapNhat()
                    break;
                default:
                    break;
            }
        },
        async CapNhat(){
            try{
                if(this.txtTenTS == ""){
                    this.$toast.error("Bạn phải nhập Tên tham số")
                }
                else{
                    let input = {
                         p_phanvung_id: this.$root.token.getPhanVungID(),
                         p_ma_ts: this.txtMaTS,
                         p_ten_ts : this.txtTenTS,
                    }
                    const response = await api.cap_nhat_tham_so(this.axios,input)
                    let data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(data == "ok"){
                        this.$toast.success("Cập nhật thành công")
                        this.$emit('appect',"Success")
                    }
                    else{
                        this.$toast.info("Cập nhật thất bại")
                    }
                }
            }
            catch(e){

            }
        },
        showModal(){
            this.$refs['popupUpdate'].show()
        },
        hideModal(){
            this.$refs['popupUpdate'].hide()
        }
    }
}
</script>