<template>
  <b-modal
    ref="popupDanhSachLoi"
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
          <span class="icon one-notepad"></span> Danh sách lỗi gán NVTC
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="legend-title mart20">
            <div class="pull-left">Danh sách lỗi</div>
            <div class="clearfix"></div>
        </div>
        <KTableV3 :columns="columns" :dataSources="dataSources" id="id"/>
      </div>

    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'

export default {
    name:'DanhSachLoiModal',
    components:{
        ActionTop, KTableV3
    },
    props:{
        data:{
            type: Array,
            default: ()=>[]
        }
    },
    data(){
        return{
            actions:[
                {
                    id:'dong',
                    name:'Đóng',
                    active: true,
                    icon_class:'ui-1_circle-remove nc-icon-glyph'
                }
            ],
            columns:[
              {
                  field: "MA_XLN",
                  label: "Mã ND /Mã NVTC",
                  allowFilter: true
              },
              {
                  field: "LYDO",
                  label: "Lỗi",
                  allowFilter: true
              },
            ],
            dataSources:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='dong'){
                this.hideModal()
            }
        },
        showModal() {
            this.$refs["popupDanhSachLoi"].show()
        },
        hideModal() {
            this.$refs["popupDanhSachLoi"].hide()
        },
        handleShowModal(){
          setTimeout(()=>{
            this.initDulieu()
          },500)
        },
        async initDulieu(){
          this.dataSources=this.data.map((x, index)=>Object.assign(x, {id:index+1}))
        },

    }
}
</script>