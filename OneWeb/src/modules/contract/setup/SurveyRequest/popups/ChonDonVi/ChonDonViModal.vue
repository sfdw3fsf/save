<template>
  <b-modal
    ref="popupDonVi"
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
          <span class="icon one-notepad"></span>Chọn đơn vị quản lý
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Danh sách đơn vị</div>
            <div class="pull-right red">Lựa chọn tổ thi công, đơn vị quản lý</div>
            <div class="clearfix"></div>
          </div>
          <KTableV3 :columns="columns"
                :dataSources="dataSources"
                :rowSelectedId="rowSelectedId"
                id="id"
                :isShowPagination="false"
                @onSelectedRow="onSelectedRow"
                :allowFilter="false"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
    name:'ChonDonViModal',
    props:{
        data:{
            type:Array,
            default:()=>[]
        }
    },
    components:{
        KTableV3,
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                }
            ],
            rowSelectedId:-1,
//             #   {
// #       "donvi_id": 1,
// #       "ten_dv": "Trung tâm Điều hành Thông tin",
// #       "donviql_id": 1,
// #       "ten_dvql": "Trung tâm Điều hành Thông tin"
// #     }
            columns:[
                {
                    field:'donviql_id',
                    label:'ID',
                    allowFilter:false,
                    cssRows:{
                        color:'blue',
                        fontWeight: 'bold',
                        textAlign: 'center'
                    }
                },
                {
                    field:'ten_dvql',
                    label:'Đơn vị',
                    allowFilter:false,
                    cssRows:{
                        color:'red',
                        fontWeight: 'bold',
                        textAlign: 'center'
                    }
                },
                {
                    field:'ten_dv',
                    label:'Tổ thi công',
                    allowFilter:false,
                    cssRows:{
                        textAlign: 'center'
                    }
                },
            ],
            dataSources:[],
            rowSelected:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                this.$emit('accept',this.rowSelected)
                this.hideModal()
            }
        },
        showModal() {
            this.$refs["popupDonVi"].show()
        },
        hideModal() {
            this.$refs["popupDonVi"].hide()
        },
        handleShowModal(){
            setTimeout(()=>{
                this.initDuLieu()
            },500)
        },
        initDuLieu(){
            this.actions[0].active=true
            this.dataSources=this.data.map((x,index)=>Object.assign(x,{id:index+1}))
            if(this.dataSources.length>0){
                this.rowSelectedId=this.dataSources[0].id
                this.onSelectedRow(this.dataSources[0])
            }else{
                this.actions[0].active=false
            }
        },
        onSelectedRow(item){
            this.rowSelected=item
        }
    }
}
</script>