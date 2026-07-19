<template>
  <b-modal
    ref="popupDanhSachLoi"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Danh sách lỗi
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <KDataGrid
            :columns="columns"
            :dataSource="dataSources"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"/>
      </div>

    </div>
  </b-modal>
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'DanhSachLoiModal',
    components:{
        KDataGrid
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