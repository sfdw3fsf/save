<template>
    <div>
        <ejs-dialog
        :allowDragging="true"
        :enableResize="true"
        :visible="false"
        :animationSettings="animationSettings"
        :position="{X:'center', Y:'top'}"
        ref="popupInfo"
        width="90%"
        :isModal='true'
        :target="target">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-file-plus"></span>Thông tin File
                </div>
                <div class="close -ap icon-close" style="cursor: pointer;" @click="hideModal"></div>
            </div>
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="popup-body">
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="box-form">
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Mã:</div>
                                        <div class="value">
                                            <input type="text" class="form-control" disabled v-model="txtMa">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Tên file:</div>
                                        <div class="value">
                                            <input type="text" class="form-control" disabled v-model="txtTenFile">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Blacklist theo:</div>
                                        <div class="value">
                                            <select2 disabled :options="cboLoai.list" v-model="cboLoaiValue"></select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Sử dụng:</div>
                                        <div class="value">
                                            <select2 disabled :options="cboSuDung.list" v-model="cboSuDungValue"></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Ngày cập nhật:</div>
                                        <div class="value">
                                            <input type="text" class="form-control" disabled v-model="txtNgayCN">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w130">Người cập nhật:</div>
                                        <div class="value">
                                            <input type="text" class="form-control" disabled v-model="txtNguoiCN">
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
    </div>
</template>


<script>
import api from '../api';
import apiHelper from '../api.helper';
import ActionTop from "@/components/kylq_components/ActionTop.vue";
export default{
name: "InfoImport",
components: {
    ActionTop,
    apiHelper,
    api
},
data(){
    return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        actions: [
            {
                id: "thoat",
                name: "Thoát",
                active: true,
                icon_class: "icon nc-icon-glyph ui-1_circle-remove"
            },
        ],
        cboLoai: {
            list: [
                {
                    id: "1",
                    text: "Mã khách hàng"
                },
                {
                    id: "2",
                    text: "Mã thanh toán"
                },
                {
                    id: "3",
                    text: "Mã thuê bao"
                },
            ]
        },
        cboLoaiValue: "",
        cboSuDung: {
            list: [
                {
                    id: "1",
                    text: "Còn sử dụng"
                },
                {
                    id: "0",
                    text: "Không còn sử dụng"
                },
            ]
        },
        cboSuDungValue: "",
        txtMa: "",
        txtTenFile: "",
        txtNgayCN: "",
        txtNguoiCN: ""
    }
    
},
watch:{
},
created(){
    // this.getDSNV()
},
methods:{
    onActionClick(action){
        switch(action.id){
            case "thoat":
                this.hideModal()
                break;
            default:
                break;
        }
    },
    showModal(){
        this.$refs['popupInfo'].show()
    },
    hideModal(){
        this.$refs['popupInfo'].hide()
    }
}
}

</script>