<template>
     <b-modal
    ref="popupDSTBLapKemBC"
    size="xl"
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
          <span class="icon one-notepad"></span> Chọn mã thuê bao lắp kèm
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-col box-form">
            <KDataGrid
              :columns="columns"
              :dataSource="dataSources"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :showColumnCheckbox="false"
              :enabledSelectFirstRow="true"
              @selectedItemsChanged="gridDanhSachChanged"/>
        </div>
      </div>
    </div>
  </b-modal>   
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import {DichVuVienThong} from '../ThamSo'
//WinUI.WinUIBanCheo.frmDSTBLapKemBC
//Link: https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=B6304
export default {
    name:'DSTBLapKemBCModal',
    components:{
        ActionTop,
        KDataGrid
    },
    props:{
        hdkh_id:{
            type:Number,
            default:0
        },
        kieuld_id:{
            type:Number,
            default:0
        },
        tinh_tc:{
            type:Number,
            default:0
        }
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'ui-1_check-circle-08 nc-icon-glyph'
                }
            ],
            columns:[
                {
                    fieldName:'ma_tb',
                    headerText:'Số máy/Acc',
                    allowFiltering:true,
                    width:70
                },
                {
                    fieldName:'loaihinh',
                    headerText:'Loại hình TB',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'madoicap',
                    headerText:'Mã đôi cáp',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ten_kieuld',
                    headerText:'Kiểu lắp đặt',
                    allowFiltering:true,
                    width:120
                }
            ],
            dataSources:[],
            rowSelected:null
        }
    },
    methods:{
        showModal() {
            this.$refs["popupDSTBLapKemBC"].show()
        },
        hideModal() {
            this.$refs["popupDSTBLapKemBC"].hide()
        },
        async onActionClick(action){
            if(action.id=='chapnhan'){
                if(!this.rowSelected){
                    this.$toast.error('Hãy chọn mã thuê bao!')
                    return
                }
                this.$emit('accept',{
                    ma_tn:this.rowSelected.ma_tb?this.rowSelected.ma_tb:'',
                    madoicap:this.rowSelected.madoicap?this.rowSelected.madoicap:''
                })
                this.hideModal()
            }
        },
        async handleShowModal(){
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false
            this.dataSources=[]
            this.rowSelected=null

            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
            
        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        gridDanhSachChanged(data){
          if(this.dataSources.length<=0||data.length<=0){
            this.rowSelected=null
            return
          }
          this.rowSelected=data[0]
        },
        async initDuLieu(){
            this.dataSources=await this.lay_ds_thuebao_tn_theo_hdkhid(this.hdkh_id)
        },
        async lay_ds_thuebao_tn_theo_hdkhid(hdkh_id){
            try{
                this.loading(true)
                let response = await this.axios.get('/web-bancheo/tracuu/lay_ds_thuebao_tn_theo_hdkhid?hdkh_id='+hdkh_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },

    },
    watch:{
      rowSelected(val){
        if(val!=null){
          this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=true
        }else{
          this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false
        }
      }
    }
}
</script>