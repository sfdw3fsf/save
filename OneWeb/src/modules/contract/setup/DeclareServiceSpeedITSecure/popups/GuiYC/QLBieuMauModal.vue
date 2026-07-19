<template>
    <b-modal
    ref="popupQLBieuMau"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Quản lý biểu mẫu
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">Danh sách biểu mẫu</div>
                <DataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"/>
            </div>
        </div>
    </div>
    </b-modal>
</template>
<script>
import API from './API'
export default {
    name:'QLBieuMauModal',
    data(){
        return {
            columns:[ 
                {fieldName: 'ten_loaihd', headerText: 'Loại HD', allowFiltering: true, customAttributes: { class: 'bold' }},
                {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true},
                {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true},
                {fieldName: 'url', headerText: 'Tên File', allowFiltering: true , allowHtml : true},
            ],
            dataSources:[]
        }
    },
    methods:{
        handleShowModal(){
            this.dataSources=[]
            setTimeout(()=>{
                this.initDuLieu()
            },500)
        },
        showModal() {
            this.$refs["popupQLBieuMau"].show()
        },
        hideModal() {
            this.$refs["popupQLBieuMau"].hide()
        },
        async initDuLieu(){
            this.dataSources=await this.sp_nap_ds_bieumau_frm_qlbieumau({})
        },
        async sp_nap_ds_bieumau_frm_qlbieumau(data){
            try{
                this.loading(true)
                let response = await API.sp_nap_ds_bieumau_frm_qlbieumau(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{
                        x.url=`<a target="_blank" href=${x.url}>${x.url}</a>`
                        return x
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
    }
}
</script>