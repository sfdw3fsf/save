<template>
    <b-modal
    ref="popupChonTinhTSL"
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
          <span class="icon one-notepad"></span> Chọn tỉnh TSL
        </div>
        <!-- <div class="close -ap icon-close" @click="hideModal"></div> -->
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-col box-form">
            <div class="info-row">
                <div class="key w120">Chọn tỉnh đầu: </div>
                <div class="value">
                    <div class="select-custom" ref="ds_tinhdau">
                        <select2 :settings="{ dropdownParent: $refs['ds_tinhdau'] }" ref="cboTinhDau"
                            v-model="tinhdau_selected" class="select2"
                            :options="ds_tinhtc.map(e=> ({id: e.tinh_id, text: e.tentinh}))"
                            >
                        </select2>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Chọn tỉnh cuối: </div>
                <div class="value">
                    <div class="select-custom" ref="ds_tinhcuoi">
                        <select2 :settings="{ dropdownParent: $refs['ds_tinhcuoi'] }" ref="cboTinhCuoi"
                            v-model="tinhcuoi_selected" class="select2"
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
//WinUI.WinUIBanCheo.frmChonTinhTSL
//https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=2073130074&range=D851
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
    name:'ChonTinhTSLModal',
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
            tinhdau_selected:null,
            tinhcuoi_selected:null
        }
    },
    methods:{
        showModal() {
            this.$refs["popupChonTinhTSL"].show()
        },
        hideModal() {
            //this.$emit('accept', null)
            this.$refs["popupChonTinhTSL"].hide()
        },
        onActionClick(action){
            if(action.id=='chapnhan'){
                //calback
                this.$emit('accept',{
                    tinh_dau:this.tinhdau_selected,
                    tinh_cuoi:this.tinhcuoi_selected
                })
                this.hideModal()
                // tinh_dau = fr.tinhdau;
                // tinh_cuoi = fr.tinhcuoi;
            }
        },
        async handleShowModal(){
            this.ds_tinhtc=[]
            this.ds_tinhtc=await this.lay_ds_tinh_thicong_combobox()
            if(this.ds_tinhtc.length>0){
                this.tinhdau_selected=this.ds_tinhtc[0].tinh_id
                this.tinhcuoi_selected=this.ds_tinhtc[0].tinh_id
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