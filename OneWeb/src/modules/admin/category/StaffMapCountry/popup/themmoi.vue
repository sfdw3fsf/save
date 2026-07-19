<template>
    <div>
        <ejs-dialog
        :allowDragging="true"
        :enableResize="true"
        :visible="false"
        :animationSettings="animationSettings"
        :position="{X:'center', Y:'top'}"
        ref="popupThemMoi"
        width="90%"
        :isModal='true'
        :target="target">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-file-plus"></span> Thêm nhóm mới
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
                                        <div class="key">{{txtTenNhom.text}}</div>
                                        <div class="value">
                                            <input class="input-group-name" v-on:keyup.enter="Them" ref="txtTenNhom" type="text" v-model="txtTenNhom.value" placeholder="Nhập tên nhóm">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key">{{txtMaNV.text}}</div>
                                        <div class="value">
                                            <input class="input-group-name" v-on:keyup.enter="getDSNV" ref="txtMaNV" type="text" v-model="txtMaNV.value" placeholder="Nhập mã nhân viên">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="box-form">
                            <div class="row">
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <DataGrid 
                                        :columns="DsNV.columns" :dataSource="DsNV.data"
                                        :showColumnCheckbox="true" :enablePagingServer="false" :allowPaging="true" :showFilter="true"
                                        panelDataHeight="200" :enabledSelectFirstRow="false" @selectedItemsChanged="select_selectedItemsChangedCDsNV"></DataGrid>
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
name: "themmoi",
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
                id: "them",
                name: "Xác nhận",
                active: true,
                icon_class: "one-save"
            },
            {
                id: "timkiem",
                name: "Tìm nhân viên",
                active: false,
                icon_class: "one-search"
            }
        ],
        txtTenNhom: {
            text: "Tên nhóm:",
            value: ""
        },
        txtMaNV: {
            text: "Mã nhân viên:",
            value: ""
        },
        DsNV: {
            columns: [
                    {
                        fieldName: 'nhanvien_id', headerText: "Nhân viên ID",allowFiltering: true, width: "120"
                    },
                    {
                        fieldName: 'ma_nv', headerText: "Mã nhân viên",allowFiltering: true, width: "130"
                    },
                    {
                        fieldName: 'ten_nv', headerText: "Tên nhân viên",allowFiltering: true, width: "140"
                    },
                    {
                        fieldName: 'email', headerText: "Email",allowFiltering: true
                    },
                    {
                        fieldName: 'so_dt', headerText: "Số điện thoại",allowFiltering: true, width: "140"
                    },
                    {
                        fieldName: 'tentinh', headerText: "Tên tỉnh",allowFiltering: true, width: "140"
                    },
                    {
                        fieldName: 'ten_dv', headerText: "Tên đơn vị",allowFiltering: true, width: "140"
                    },
                ],
                data: [],
                selectedItems: [],
        }
    }
},
watch:{
  'txtMaNV.value':{
    handler(value){
        if(value.trim() == ""){
            this.actions[1].active = false
        }
        else{
            this.actions[1].active = true
        }
    }
  }  
},
created(){
    // this.getDSNV()
},
methods:{
    onActionClick(action){
        switch(action.id){
            case "them":
                this.Them()
                break;
            case "timkiem":
                this.getDSNV()
                break;
            default:
                break;
        }
    },
    async Them(){
        try{
            if(this.txtTenNhom.value.trim() == "" || this.DsNV.selectedItems <= 0){
                if(this.txtTenNhom.value.trim() == ""){
                    this.$toast.error("Bạn phải nhập tên nhóm")
                    this.$refs.txtTenNhom.focus()
                }
                else if(this.DsNV.selectedItems <= 0){
                    this.$toast.error("Bạn phải chọn ít nhất 1 nhân viên")
                }
            }
            else{
                let stringData = ""
                
                var arr=[]
                for(var i=0;i<this.DsNV.selectedItems.length;i++){
                    // console.log(selectedItems[i]["nhanvien_id"]);
                    let data = {
                        NHANVIEN_ID: this.DsNV.selectedItems[i]["nhanvien_id"],
                        PHANVUNG_ID: this.DsNV.selectedItems[i]["tinh_id"]
                    }
                    // arr.push(this.DsNV.selectedItems[i]["nhanvien_id"]);
                    arr.push(JSON.parse(JSON.stringify(data)))
                }
                // console.log(arr);
                // const arr2 = arr.map((x) => `{\"NHANVIEN_ID\": ${x}}` )
                // console.log(arr2);
                // stringData =  "["+ arr2.toString() + "]"
                stringData = JSON.stringify(arr)
                let input = {
                    p_phanvung_id: this.$root.token.getPhanVungID(),
                    p_tennhom: this.txtTenNhom.value.trim(),
                    p_data: stringData
                }
                const response = await api.Them_nhom(this.axios,input)
                let result = apiHelper.getDataFromResponseApiReturnArray(response) || []
                if(result == "OK"){
                    this.$toast.success("Thêm mới thành công")
                    // this.getDSNV()
                    this.$emit('appect',this.txtTenNhom.value)
                    this.hideModal()
                }
                else {
                    this.$toast.info("Thêm mới thất bại")
                    this.txtMaNV.value = ""
                    this.txtTenNhom.value = ""
                    this.DsNV.data = []
                }
            }
        }
        catch(e){
            console.log("Error",e);
            this.$toast.info("Lỗi")
            
        }
    },
    async getDSNV(){
        
        try{
            this.Loading = true
            if(this.txtMaNV.value.trim() != ""){
                let data = {
                    ma_nv: this.txtMaNV.value.trim()
                }
                const response = await api.Lay_ds_nv_chuagan(this.axios,data)
                this.DsNV.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                if(this.DsNV.data.length > 0){
                    this.$toast.success("Tìm kiếm thành công")
                }
                else{
                    this.$toast.info("Không có dữ liệu")
                    this.DsNV.data = []

                }
                this.Loading = false
            }
            else{
                this.$toast.error("Chưa nhập mã nhân viên!")
                this.$refs.txtMaNV.focus()
            }
        }
        catch(e){
            console.log("Error",e);
        }
    },
    select_selectedItemsChangedCDsNV: function(selectedItems) {
        this.DsNV.selectedItems = selectedItems
    },
    showModal(){
        this.$refs['popupThemMoi'].show()
    },
    hideModal(){
        this.$refs['popupThemMoi'].hide()
        this.txtMaNV.value = ""
        this.txtTenNhom.value = ""
        this.DsNV.data = []
    }
}
}

</script>