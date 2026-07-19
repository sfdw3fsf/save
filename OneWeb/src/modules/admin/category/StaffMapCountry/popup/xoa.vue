<template>
    <ejs-dialog
    :allowDragging="true"
    :animationSettings="animationSettings"
    :enableResize="false"
    :position="{X:'center', Y:'top'}"
    :visible="false"
    ref="popupXoa"
    :showCloseIcon="false"
    width="70%"
    minHeight="200"
    :isModal='true'
    :target="target">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
                <span class="icon one-file-plus"></span> Xóa nhóm
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
                                    <div class="key">{{txtIDNhom.text}}:</div>
                                    <div class="value">
                                        <input class="input-group-name" type="text" v-model="txtIDNhom.value" placeholder="Nhập ID nhóm muốn xóa">
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
import api from '../api';
import apiHelper from '../api.helper';
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import { log } from 'pdfmake/build/pdfmake';
export default{
name: "xoa",
components: {
    ActionTop,
    apiHelper,
    api
},
data(){
    return {
        actions: [
            {
                id: "xoa",
                name: "Xác nhận",
                active: true,
                icon_class: "one-save"
            }
        ],
        txtIDNhom: {
            text: "ID nhóm",
            value: ""
        },
    }
},
created(){
    this.getDSNV()
},
methods:{
    onActionClick(action){
        switch(action.id){
            case "xoa":
                this.Xoa()
                break;
            default:
                break;
        }
    },
    async Xoa(){
        
        try{
            

            if(this.txtIDNhom.value == ""){
                this.$toast.error("Bạn phải nhập ID nhóm muốn xóa")
            }else{
                const confirm = await this.$confirm(
                'Xóa nhóm',
                'Bạn có chắc chắn muốn xóa',
                    {
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Hủy',
                        type: 'warning',
                    },
                );
                if(confirm){
                    let input = {
                        nhom_id: this.txtIDNhom.value
                    }
                    const response = await api.Xoa_nhom(this.axios,input)
                    let result = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(result == "OK"){
                        this.$toast.success("Xóa thành công")
                        this.$refs['popupXoa'].hide()
                        this.$emit('appect',"1")
                    }
                    else{
                        this.$toast.info("Xóa thất bại")
                    }
                }
            }
        }
        catch(e){
            console.log("Error",e);
            this.$toast.info("Nhập sai ID")
        }
    },
    showModal(){
        this.$refs['popupXoa'].show()
    },
    hideModal(){
        this.$refs['popupXoa'].hide()
    }
}
}

</script>