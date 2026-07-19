<template>
  <b-modal
    ref="popupChonKhuyenMai"
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
            <div class="title"><span class="icon one-notepad"></span>{{title}}</div>
            <div class="close -ap icon-close" @click="hideModal">
            </div>
        </div>
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">Thông tin tìm kiếm</div>
                <div class="info-row">
                    <div class="key w140">{{label1}}</div>
                    <div class="value">
                        <input type="text" @keypress.enter="onEnter" :value="input_khuyenmai_tratruoc" @change="e=>input_khuyenmai_tratruoc=e.target.value"  class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w140">
                        <div class="check-action">
                            <input type="checkbox" v-model="checkSoCV" @change="changeCheckSoCV" class="check">
                            <span class="name">Số công văn</span>
                        </div>
                    </div>
                    <div class="value">
                        <multiselect
                            :options="ds_socv"
                            placeholder="Chọn số công văn"
                            @input="changeSoCV"
                            :showLabels="false"
                            :allowEmpty="false"
                            :value="socv_o_selected"
                            :disabled="!checkSoCV"
                            label="ten_cv"
                            track-by="congvan_id">
                            <template #noOptions>
                                Không tìm thấy thông tin
                            </template>
                            <template #noResult>
                                Không tìm thấy thông tin
                            </template>
                        </multiselect>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w140">
                                <div class="check-action">
                                    <input type="checkbox" v-model="checkThoiGianCamKet"  @change="changeCheckThoiGianCamKet" class="check">
                                    <span class="name">Thời gian cam kết</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="input-number-custom">
                                    <input type="number" :disabled="!checkThoiGianCamKet" :value="input_thang" @change="e=>input_thang=e.target.value" class="form-control">
                                </div>
                            </div>
                            <div class="value w30 padt7">
                                (Tháng)
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w140">
                                <div class="check-action">
                                    <input type="checkbox" v-model="checkNhomCV" class="check">
                                    <span class="name">Nhóm theo số công văn</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
            <div class="box-form">
                <div class="legend-title">{{textCaption}}</div>
                <KTableV3 :columns="columns"
                    :dataSources="dataSources"
                    @onSelectedRow="onSelectedRow"
                    :rowSelectedId="rowSelectedId"
                    id="khuyenmai_id"
                    :allowFilter="true"/>
                
            </div>
            
        </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/mirana/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
export default {
    name:'ChonKhuyenMaiModal',
    props:{
        loai:{
            type:Number,
            default:1 //1: Chọn khuyến mãi, 2 chọn trả trước
        },
        data:{
            type:Array,
            default:()=>[]
        }
    }, 
    components:{
        ActionTop,
        KTableV3
    },
    data(){
        return {
            actions:[
                {
                    id:'timkiem',
                    name:'Tìm kiếm',
                    active: true,
                    icon_class:'one-search',
                    type:1
                },
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: false,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08',
                    type:1
                }

            ],
            dataSources:[],
            rowSelectedId:-1,
            input_khuyenmai_tratruoc:'',
            rowSelected:null,
            _dsKm:'',
            checkSoCV:false,
            socv_selected:null,
            ds_socv:[],
            checkThoiGianCamKet:false,
            input_thang:'0',
            checkNhomCV:false

        }
    },
    computed:{
        title(){
            return this.loai==1?'Chọn khuyến mại':'Chọn trả trước'
        },
        textCaption(){
            return this.loai==1?'Danh sách khuyến mại':'Danh sách trả trước'
        },
        label1(){
            return this.loai==1?'Tên khuyến mại:':'Tên trả trước:'
        },
        socv_o_selected(){
            const socv=this.ds_socv.find(x=>x.congvan_id==this.socv_selected)
            return socv!=undefined?socv:null
        },
        columns(){
            if(this.checkNhomCV){
                return [
                    {
                        field: "ten_km",
                        label: "Tên khuyến mại",
                        allowFilter: true
                    },
                    {
                        field: "congvan_id",
                        label: "Số công văn",
                        allowFilter: true
                    }
                ]

            }else{
                return [
                    {
                        field: "ten_km",
                        label: "Tên khuyến mại",
                        allowFilter: true
                    }
                ]
            }
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='timkiem'){
                this.hienthi_khuyenmai()
            }else if(action.id=='chapnhan'){
                
                this.$emit('accept', this.rowSelected.khuyenmai_id)
                this.hideModal()
            }
        },
        showModal() {
            this.$refs["popupChonKhuyenMai"].show()
        },
        hideModal() {
            this.$refs["popupChonKhuyenMai"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.rowSelectedId=-1
            this._dsKm=''
            this.input_khuyenmai_tratruoc=''
            this.checkSoCV=false
            this.socv_selected=null
            this.ds_socv=[]
            this.checkThoiGianCamKet=false
            this.input_thang='0'
            this.checkNhomCV=false
            this.actions[this.actions.findIndex(x=>x.id=='chapnhan')].active=false
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        onSelectedRow(item){
            this.rowSelected=item
        },
        changeSoCV(data){
            this.socv_selected=data.congvan_id
            //
            this.hienthi_khuyenmai()

        },
        onEnter(){
            // if(this.input_khuyenmai_tratruoc.trim()==''){
            //     return
            // }
            // this.hienthi_khuyenmai()
        },
        changeCheckSoCV(){
            this.hienthi_khuyenmai()
        },
        changeCheckThoiGianCamKet(){
            this.hienthi_khuyenmai()
        },
        async hienthi_khuyenmai(){
            var so_cv = -1
            if(this.checkSoCV){
                so_cv=this.socv_selected!=null?Number(this.socv_selected):-1
            }
            let so_thang = this.checkThoiGianCamKet?Number(this.input_thang): -1
            this.dataSources=[]
            this.dataSources=await this.sp_lay_km_theo_danh_sach({
                ds: this._dsKm.join(','),
                so_cv: so_cv,
                sothang: so_thang,
                ten: this.input_khuyenmai_tratruoc.trim()
            })
            if(this.dataSources.length>0){
                this.rowSelectedId=this.dataSources[0].khuyenmai_id
                this.onSelectedRow(this.dataSources[0])
            }
        },
        async lay_congvan_theo_ds_khuyenmai(data){
            try {
                this.loading(true)
                let response = await this.axios.post("/web-hopdong/lapdatmoi/sp_lay_cong_van_theo_danh_sach_km",data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e) {
                this.loading(false)
                return []
            }
        },
        async sp_lay_km_theo_danh_sach(data){
            try {
                this.loading(true)
                let response = await this.axios.post("/web-hopdong/lapdatmoi/sp_lay_km_theo_danh_sach",data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e) {
                this.loading(false)
                return []
            }
        },
        async initDuLieu(){
            //
            this._dsKm=this.data.map(x=>x.khuyenmai_id)
            this.ds_socv=await this.lay_congvan_theo_ds_khuyenmai({
                ds_km_id:this._dsKm.join(',')
            })
            if(this.ds_socv.length>0){
                this.socv_selected=this.ds_socv[0].congvan_id
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