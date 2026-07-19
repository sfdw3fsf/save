<template>
  <b-modal
    ref="popupViewHistory"
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
          Lịch sử thay đổi dữ liệu
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <!-- <ActionTop :actions="actions" @onActionClick="onActionClick" /> -->

      <div class="popup-body">
        <!-- <div class="box-form">
          <div class="info-row">
            <div class="key w80">Số máy/Acc</div>
            <div class="value">
              <input type="text" v-model="input_ma_tb" @keyup.enter="onEnterMaTB" class="form-control" />
            </div>
          </div>
        </div> -->
        <!-- <div class="box-form"> -->
          <KTableV3 :columns="columns" :dataSources="dataSources" id="id"  @onSelectedRow="selectedRowDV" :allowFilter="true"/>
        <!-- </div> -->
      </div>

    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import StaffLocationAPI from './StaffLocationAPI'
export default {
    name:'ViewHistoryModal',
    props:{
      data:{
        type:Array,
        default: ()=>[]
      }
    },
    components:{
        KTableV3,
        ActionTop
    },
    data(){
        return{
            actions:[
                {
                    id:'lay_tt',
                    name:'Lấy TT (F5)',
                    active: true,
                    icon_class:'one-file-attach'
                },
                {
                    id:'close',
                    name:'Thoát',
                    active: true,
                    icon_class:'ui-1_circle-remove nc-icon-glyph'
                },

            ],
            columns:[
                {
                    field: "loai_kv",
                    label: "Loại KV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ma_kv",
                    label: "Mã KV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_kv",
                    label: "Khu vực",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "loai_nv",
                    label: "Loại nhân viên",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_nv",
                    label: "Nhân viên",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "thaotac",
                    label: "Thao tác",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "nguoi_cn",
                    label: "Người CN",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "may_cn",
                    label: "Máy CN",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ngay_cn",
                    label: "Thời gian CN",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "nhiemvu",
                    label: "Nhiệm vụ",
                    allowFilter: true,
                    allowSorting:true
                },
            ],
            dataSources:[],
            input_ma_tb:''
        }
    },
    methods:{
        onActionClick(action){
          if(action.id=='lay_tt'){
              if(this.input_ma_tb.trim()!=''){
                  this.lay_ds_log_bdtb_matb(this.input_ma_tb.trim())
              }
          }else if(action.id=='close'){
              this.hideModal()
          }
        },
        selectedRowDV(item){
          //this.rowSelected=item
        },
        handleShowModal(){
          this.input_ma_tb=''
          setTimeout(()=>{
            this.dataSources=this.data
          },500)
        },
        showModal() {
            this.$refs["popupViewHistory"].show()
        },
        hideModal() {
            this.$refs["popupViewHistory"].hide()
        },
        onEnterMaTB(){
          if(this.input_ma_tb.trim()!=''){
            this.lay_ds_log_bdtb_matb(this.input_ma_tb.trim())
          }
        },
        async lay_ds_log_bdtb_matb(ma_tb){
            try{
                this.loading(true)
                this.dataSources=[]
                let response = await StaffLocationAPI.lay_ds_log_bdtb_matb(this.axios,ma_tb)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSources=response.data.data
                }else{
                    this.dataSources=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách')
            }finally{
                this.loading(false)
            }
        }
    },
    watch:{
        data(val){
            this.dataSources=val
        }
    }
}
</script>