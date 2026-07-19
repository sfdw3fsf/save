<template>
  <b-modal
    ref="popupDanhSachLoi"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Danh sách lỗi
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title mart20">
            <div class="pull-left">Danh sách lỗi</div>
            <div class="clearfix"></div>
          </div>
          <k-table :columns="columns" :dataSources="dataSources" :rowSelectedIndex="1"/>
        </div>
      </div>

    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/ActionTop.vue'
import KTable from '@/components/KTable.vue'
export default {
    name:'DanhSachLoiModal',
    components:{
        ActionTop, KTable
    },
    props:{
        columns:{
            type: Array,
            default: ()=>[]
        },
        dataSources:{
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
            ]
           
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
        }
    }
}
</script>