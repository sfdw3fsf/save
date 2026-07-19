<template>
    <b-modal
    ref="popupChonTinh"
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
          <span class="icon one-notepad"></span> Chọn tỉnh bán chéo hợp đồng
        </div>
        <!-- <div class="close -ap icon-close" @click="hideModal"></div> -->
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-col box-form">
            <div class="info-row">
                <div class="key w120">Chọn tỉnh: </div>
                <div class="value">
                    <div class="select-custom" ref="ds_tinh">
                        <select2 :settings="{ dropdownParent: $refs['ds_tinh'] }" ref="cboTinh"
                            v-model="tinh_selected" class="select2"
                            :options="ds_tinhtc.map(e=> ({id: e.tinh_id, text: e.tentinh}))"
                            >
                        </select2>
                    </div>
                </div>
            </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
//WinUI.WinUIBanCheo.frmChonTinh
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
    name:'ChonTinhModal',
    props:{
        //No prop
    },
    components:{
        ActionTop
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
            ds_tinhtc:[],
            tinh_selected:null
        }
    },
    methods:{
        showModal() {
            this.$refs["popupChonTinh"].show()
        },
        hideModal() {
            this.$refs["popupChonTinh"].hide()
        },
        onActionClick(action){
            if(action.id=='chapnhan'){
                //calback
                this.$emit('accept',this.tinh_selected)
                this.hideModal()
            }
        },
        async handleShowModal(){
            this.ds_tinhtc=[]
            this.ds_tinhtc=await this.lay_ds_tinh_thicong_combobox()
            if(this.ds_tinhtc.length>0){
                this.tinh_selected=this.ds_tinhtc[0].tinh_id
            }
            
        },
        async lay_ds_tinh_thicong_combobox(){
            try{
                this.loading(true)
                let response = await this.axios.post('/web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox',{})
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
        }
    }


}
</script>