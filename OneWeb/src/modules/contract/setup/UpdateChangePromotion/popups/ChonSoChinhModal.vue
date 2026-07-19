<template>
  <b-modal
    ref="popupChonSoChinh"
    size="xl"
    hide-header
    hide-header-close
    no-close-on-backdrop
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span> {{title}}</div>
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
import {DichVuVienThong} from '../ThamSo'
export default {
    name:'ChonSoChinhModal',
    props:{
        dichvuvt_id:{
            type:Number,
            default:0
        },
        loaitb_id:{
            type:Number,
            default:0
        },
        id:{
            type:Number,
            default:0
        },
        loai:{
            type:Number,
            default:0
        },
        iid_sochinh:{
            type:Number,
            default:0
        },
        iloai:{
            type:Number,
            default:0
        },
        ma_tb:{
            type:String,
            default:''
        },
        kieu:{
            type:Number,
            default:0
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
                    field: "LOAIHINH_TB",
                    label: "Loại hình",
                    allowFilter: true
                },
                {
                    field: "MA_TB",
                    label: "Mã thuê bao",
                    allowFilter: true
                },
                {
                    field: "TRANGTHAI_TB",
                    label: "Trạng thái",
                    allowFilter: true
                },
                {
                    field: "TOCDO",
                    label: "Tốc độ",
                    allowFilter: true,
                    cssRows:{
                      fontWeight: 'bold'
                    }
                },
                {
                    field: "DIACHI_LD",
                    label: "Địa chỉ LĐ",
                    allowFilter: true
                }
            ],
            dataSources:[],
            rowSelectedId:-1,
            rowSelected:null
        }
    },
    computed:{
        title(){
            if(this.dichvuvt_id==DichVuVienThong.MEGAWAN){
                return 'Chọn hướng kết nối Megawan'
            }else if(this.dichvuvt_id==DichVuVienThong.CO_DINH||this.dichvuvt_id==DichVuVienThong.IMS){
                return 'Chọn số chính'
            }else if(this.dichvuvt_id==DichVuVienThong.HOINGHI_TRUYENHINH){
                return 'Chọn điểm cầu chính'
            }else {
                return ''
            }
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='chapnhan'){
                if(this.rowSelected==null){
                    this.$toast.error('Hãy chọn mã thuê bao!')
                    return
                }
                this.$emit('accept', this.rowSelected)
                this.hideModal()
            }
        },
        showModal() {
            this.$refs["popupChonSoChinh"].show()
        },
        hideModal() {
            this.$refs["popupChonSoChinh"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.rowSelectedId=-1
            this.rowSelected=null
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        onSelectedRow(item){
            this.rowSelected=item
        },
        async fn_lay_tt_load_dssochinh(data){
            try{
                this.loading(true)
                let response = await this.axios.post('/web-hopdong/lapdatmoi/fn_lay_tt_load_dssochinh',{
                    ds_para:data
                })
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async initDuLieu(){
            let result=await this.fn_lay_tt_load_dssochinh(JSON.stringify({
                VDICHVUVT_ID:this.dichvuvt_id,
                VID:this.id,
                VLOAI:this.loai,
                VLOAITB_ID:this.loaitb_id,
                VKIEU:this.kieu
            }))
            // let result=await this.fn_lay_tt_load_dssochinh(JSON.stringify({
            //     VDICHVUVT_ID:1,
            //     VID:4,
            //     VLOAI:1,
            //     VLOAITB_ID:1,
            //     VKIEU:0
            // }))
            if(result!=null&&result.ERROR_CODE==1){
                this.dataSources=result.RESULT.DS.map((x, index)=>Object.assign(x,{id:index+1}))
            }else if(result!=null&&result.ERROR_CODE==0){
                this.$toast.error(result.MESSAGE)
            }

            if(this.dataSources.length>0){
                this.rowSelectedId=this.dataSources[0].id
                this.onSelectedRow(this.dataSources[0])
            }
        }
    },
    watch:{
        dataSources(val){
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=val.length>0
        }
    }
}
</script>