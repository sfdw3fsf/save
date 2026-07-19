<template>
  <b-modal
    ref="popupLenhLienQuan"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
    
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Câu lệnh liên quan
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
          <KTableV3 :columns="columns"
            :dataSources="dataSources"
            id="id"
            @onSelectedRow="onSelectedRow"
            @doubleClickRow="doubleClickRow"/>
      </div>
      <TaoLenhLienQuanModal ref="tao_lenh_lien_quan" :data="data" @refresh="refresh" />
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import DebtTableSQLForInvoiceCycleAPI from './DebtTableSQLForInvoiceCycleAPI'
import TaoLenhLienQuanModal from './TaoLenhLienQuanModal.vue'
export default {
    name:'LenhLienQuanModal',
    props:{
        tenbang:{
            type:String,
            default: ''
        },
        module_id:{
            type:Number,
            default: 4
        }
    },
    components:{
        KTableV3,
        TaoLenhLienQuanModal
    },
    data(){
        return {
            columns:[
                {
                    field: "thutu",
                    label: "Thứ tự",
                    allowFilter: true
                },
                {
                    field: "tenbang",
                    label: "Tên bảng",
                    allowFilter: true
                },
                {
                    field: "command",
                    label: "Câu lệnh",
                    allowFilter: true
                }
            ],
            dataSources:[
                
            ],
            data:{}
        }
    },
    methods:{
        showModal() {
            this.$refs["popupLenhLienQuan"].show()
        },
        hideModal() {
            this.$refs["popupLenhLienQuan"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            setTimeout(()=>{
                this.sp_lay_ds_taobang_cmd(this.module_id, this.tenbang)
            },500)
        },
        onSelectedRow(item){
            this.data=item
        },
        doubleClickRow(item){
            //hiển thị form tạo lệnh
            
            this.$nextTick(()=>{
                this.$refs.tao_lenh_lien_quan.showModal()
            })
        },
        refresh(){
            this.sp_lay_ds_taobang_cmd(this.module_id, this.tenbang)
        },
        async sp_lay_ds_taobang_cmd(module_id, tenbang){
            try{
                this.loading(true)
                this.dataSources=[]
                let response=await DebtTableSQLForInvoiceCycleAPI.sp_lay_ds_taobang_cmd(this.axios, module_id, tenbang)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSources=response.data.data.map((x, index)=>Object.assign(x,{id:index+1}))
                }else{
                    this.dataSources=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách')
            }finally{
                this.loading(false)
            }
        },

    }
}
</script>