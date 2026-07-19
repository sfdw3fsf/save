<template>
  <b-modal
    ref="popupDanhSachThueBaoCungMa"
    size="lg"
    hide-header
    hide-header-close
    no-close-on-backdrop
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Danh sách thuê bao</div>
            <div class="close -ap icon-close" @click="hideModal">
            </div>
        </div>
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="popup-body">
            <div class="box-form">
                <KTableV3 :columns="columns"
                    :dataSources="dataSources"
                    @onSelectedRow="onSelectedRow"
                    :rowSelectedId="rowSelectedId"
                    id="id"
                    :allowFilter="true"/>
            </div>
        </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/mirana/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ChangeSubsTypeAPI from '../ChangeSubsTypeAPI'
export default {
    name:'DanhSachThueBaoCungMaModal',
    props:{
        data:{
            type:Array,
            default:()=>[]
        }
    },
    components:{
        ActionTop, KTableV3
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: false,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08',
                    type:1
                } 
            ],
            columns:[
                {
                    field: "ma_tb",
                    label: "Số máy/Acc",
                    allowFilter: true
                },
                {
                    field: "dichvu_vt",
                    label: "Dịch vụ",
                    allowFilter: true
                },
                {
                    field: "loaihinh_tb",
                    label: "Loại hình",
                    allowFilter: true
                }
            ],
            dataSources:[],
            rowSelectedId:-1,
            rowSelected:null,
            ds_dichvu:[],
            ds_loaihinh:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                this.$emit('accept', this.rowSelected)
                this.hideModal()
            }
        },
        showModal() {
            this.$refs["popupDanhSachThueBaoCungMa"].show()
        },
        hideModal() {
            this.$refs["popupDanhSachThueBaoCungMa"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.dichvu_vt=[]
            this.loaihinh_tb=[]
            this.rowSelectedId=-1
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        onSelectedRow(item){
            this.rowSelected=item
        },
        async lay_danhmuc_lapmoi_tt_chung_v8(chuoi){
            try{
                this.loading(true)
                let response = await ChangeSubsTypeAPI.lay_danhmuc_lapmoi_tt_chung_v8(this.axios, chuoi)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        getLoaiHinh(loaitb_id){
            if(!loaitb_id){
                return ''
            }
            let loaihinh=this.ds_loaihinh.find(x=>x.loaitb_id==loaitb_id.toString())
            return loaihinh!=undefined?loaihinh.loaihinh_tb:''
        },
        getDichVuVT(dichvuvt_id){
            if(!dichvuvt_id){
                return ''
            }
            let dichvu=this.ds_dichvu.find(x=>x.dichvuvt_id==dichvuvt_id.toString())
            return dichvu!=undefined?dichvu.ten_dvvt:''
        },
        async initDuLieu(){
            let result = await this.lay_danhmuc_lapmoi_tt_chung_v8('DICHVU_VT|LOAIHINH_TB')
            if(result){
                this.ds_dichvu=result.dichvu_vt
                this.ds_loaihinh=result.loaihinh_tb
            }
            this.dataSources=this.data.map((item, index)=>Object.assign(item,{
                id:index+1,
                loaihinh_tb:this.getLoaiHinh(item.loaitb_id),
                dichvu_vt:this.getDichVuVT(item.dichvuvt_id)
            }))
            //this.dataSources=this.data
            if(this.dataSources.length>0){
                this.rowSelectedId=this.dataSources[0].id
                this.onSelectedRow(this.dataSources[0])
                this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=true
            }
        }
    }
}
</script>