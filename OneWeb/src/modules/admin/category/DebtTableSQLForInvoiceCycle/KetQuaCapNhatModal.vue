<template>
  <b-modal
    ref="popupKetQuaCapNhat"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Kết quả cập nhật
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
          <KTableV3 :columns="columns" id="id" :dataSources="dataSources" />
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import DebtTableSQLForInvoiceCycleAPI from './DebtTableSQLForInvoiceCycleAPI'
export default {
    name:'KetQuaCapNhatModal',
    props:{
        tenbang:{
            type:String,
            default: ''
        }
    },
    components:{
        KTableV3
    },
    data(){
        return {
            columns:[
                {
                    field: "ten_bang",
                    label: "Tên bảng",
                    width:"50%",
                    allowFilter: true
                },
                {
                    field: "ten_truong",
                    label: "Tên trường",
                    width:"50%",
                    allowFilter: true
                }
            ],
            dataSources:[
                
            ],
        }
    },
    methods:{
        showModal() {
            this.$refs["popupKetQuaCapNhat"].show()
        },
        hideModal() {
            this.$refs["popupKetQuaCapNhat"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            setTimeout(()=>{
                this.sp_lay_ds_taobang_col(this.tenbang)
            },500)
           
        },
        async sp_lay_ds_taobang_col(tenbang){
            try{
                this.loading(true)
                let response=await DebtTableSQLForInvoiceCycleAPI.sp_lay_ds_taobang_col(this.axios,tenbang)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSources=response.data.data.map((x,index)=>Object.assign(x,{id:index+1}))
                }else{
                    this.dataSources=[]
                    //  {
                    //            id:1,
                    // phanvung_id: 28,
                    // ten_bang: "BANGPHIEUTRA_",
                    // ten_truong: "LUOTTHU_ID",
                    // module_id: null,
                    // thutu: null
                    // },
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