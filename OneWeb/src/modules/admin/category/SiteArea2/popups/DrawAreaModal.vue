<template>
    <!-- <b-modal
    ref="popupDrawArea"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  > -->
  <ejs-dialog
        :allowDragging="true"
        :animationSettings="animationSettings"
        :enableResize="false"
        :position="{X:'center', Y:'top'}"
        :visible="false"
        ref="popupDrawArea"
        width="75%"
        :isModal='true'
        :target="target"
        :open="handleShowModal">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
            <span class="icon one-notepad"></span>Khu vực {{tenTinhThanh}}
            </div>
            <div class="close -ap icon-close" @click="hideModal"></div>
        </div>
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="popup-body">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">Địa chỉ</div>
                            <div class="value">
                                <input type="text" :value="input_diachi" @change="e=>input_diachi=e.target.value" @keyup.enter="onEnterDiaChi"  class="form-control highlight">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">Vĩ độ</div>
                            <div class="value">
                                <input type="text" class="form-control" :value="position.lat" @change="e=>position.lat=e.target.value">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row marb0">
                            <div class="key w30 nowrap">Kinh độ</div>
                            <div class="value">
                                <input type="text" class="form-control" :value="position.lng" @change="e=>position.lng=e.target.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form">
            <BanDoVNPT ref="BanDo" :position="getPosition"></BanDoVNPT>
        </div>
    </div>
    <!-- Modal -->
    <HuongDanModal ref='huongDanModal'/>
    <!-- </b-modal> -->
  </ejs-dialog>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import BanDoVNPT from './BanDoVNPT'
import BanDo from './BanDo'
import HuongDanModal from './HuongDanModal.vue'
export default {
    name:'DrawAreaModal',
    props:{
        khuvuc_id:{
            type:Number,
            default:0
        },
        ten_kv:{
            type:String,
            default:''
        }
    },
    components:{
        ActionTop,
        BanDoVNPT,
        BanDo,
        HuongDanModal
    },
    data(){
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            actions:[
                {
                    id:'timkiem',
                    name:'Tìm kiếm',
                    active: true,
                    icon_class:'one-search'
                },
                // {
                //     id:'taovung',
                //     name:'Tạo vùng',
                //     active: true,
                //     icon_class:'one-design-per'
                // },
                // {
                //     id:'suavung',
                //     name:'Sửa vùng',
                //     active: true,
                //     icon_class:'one-edit'
                // },
                // {
                //     id:'xoavung',
                //     name:'Xóa vùng',
                //     active: true,
                //     icon_class:'one-trash'
                // },
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'naplai',
                    name:'Nạp lại',
                    active: true,
                    icon_class:'one-file-refresh'
                },
                {
                    id:'trogiup',
                    name:'Trợ giúp',
                    active: true,
                    icon_class:'one-circle-question'
                },
            ],
            tenTinhThanh:'HCM',
            position:{
                lat:'',
                lng:''
            },
            idModalChonToaDo: "idModalChonToaDo",
            diachi: null,
            getPosition: { lat: 10.82477099104633, lng: 106.63214587243222 },
            zoomDefault: 15,
            mapTypeId: 'ROADMAP',
            positionKetCuoi: {
                lat: null,
                lng: null
            },
            input_diachi:'',
            //Path load từ khu vực
            paths:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='timkiem'){
                if(this.position.lat.trim()==''||this.position.lng.trim()==''){
                    this.$toast.error('Hãy nhập thông tin tọa độ')
                    return
                }
                this.$refs.BanDo.searchLocation(this.position)
                // if(this.position.lat.trim()!=''&&this.position.lng.trim()!=''){
                    
                // }else if(this.input_diachi.trim()!=''){
                //     this.$refs.BanDo.searchAddress(this.input_diachi.trim())
                // }
                
            }else if(action.id=='capnhat'){
                // if(this.khuvuc_id==0){
                //     this.$toast.error('Hãy chọn khu vực!')
                //     return
                // }
                this.CapNhat()
            }else if(action.id=='taovung'){
                this.$refs.BanDo.TaoVung()
            }else if(action.id=='suavung'){
                this.$refs.BanDo.setSuaVung(true)
                //this.$refs.BanDo.test()
            }else if(action.id=='xoavung'){
                if(this.$refs.BanDo.polygonSelected==-1){
                    this.$toast.error('Bạn chưa chọn đối tượng cần xóa')
                    return
                }
                this.$refs.BanDo.xoaVung()
            }else if(action.id=='naplai'){
                this.napLai()
            }else if(action.id=='trogiup'){
                this.$refs.huongDanModal.showModal()
            }
        },
        async CapNhat(){
            console.log('polygonPaths', this.$refs.BanDo.polygonPaths())
            let data=this.$refs.BanDo.polygonPaths().map(x=>x.map(i=>{
                return {
                    LAT:i[1],
                    LNG:i[0]
                }
            }))
            console.log('CapNhat data', data)
            let result=await this.fn_capnhat_khoanvung_khuvuc({
                p_phanvung_id: this.$root.token.getPhanVungID(),
                p_khuvuc_id: this.khuvuc_id,
                p_data: JSON.stringify(data)
            })
            if(result){
                this.$toast.success('Cập nhật thành công')
            }
        },
        async napLai(){
            this.$refs.BanDo.CleanPolygons()

            this.paths=await this.fn_khoanvung_khuvuc(this.$root.token.getPhanVungID(), this.khuvuc_id)
            if(!this.paths[0][0].LAT||!this.paths[0][0].LNG){
                this.paths=[]
                this.$refs.BanDo.CleanPolygons()
                return
            }
            this.$refs.BanDo.setPolygonList(this.paths)
        },
        onEnterDiaChi(){
            if(this.input_diachi.trim()!=''){
                this.$refs.BanDo.searchAddress(this.input_diachi.trim())
            }else{
                this.$toast.error('Hãy nhập địa chỉ')
            }
        },
        showModal() {
            this.$refs["popupDrawArea"].show()
        },
        hideModal() {
            this.$refs["popupDrawArea"].hide()
        },
        handleShowModal(){
            setTimeout(()=>{
                this.initDuLieu()
            },500)
        },
        async initDuLieu(){
            this.$refs.BanDo.updateSizeMap()
            this.position={
                lat:'',
                lng:''
            }
            this.input_diachi=''
            this.paths=[]
            //this.$refs.BanDo.setSuaVung(false)
            if(this.ten_kv!=''){
                this.tenTinhThanh=this.ten_kv
            }else{
                if(this.$root.token.getPhanVungID()==21){
                    this.tenTinhThanh='Hà Nội'
                }else {
                    this.tenTinhThanh=this.$root.token.getMaTinh()
                }
            }

            this.paths=await this.fn_khoanvung_khuvuc(this.$root.token.getPhanVungID(), this.khuvuc_id)
            if(!this.paths[0][0].LAT||!this.paths[0][0].LNG){
                this.paths=[]
                this.$refs.BanDo.CleanPolygons()
                return
            }
            this.$refs.BanDo.setPolygonList(this.paths)
        },
        // dblclickMap(data){
        //     console.log('dblclickMap', data)
        // },
        async fn_capnhat_khoanvung_khuvuc(data){
            try{
                this.loading(true)
                let response=await this.axios.post('/web-quantri/khuvuc_diadanh/fn_capnhat_khoanvung_khuvuc',data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    if(response.data.data=='OK'){
                        return true
                    }
                    this.$toast.error(response.data.data)
                    return false
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công')
                    }
                    return false
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công')
                }
                return false
            }
        },
        async fn_khoanvung_khuvuc(phanvung_id, khuvuc_id){
            try{
                this.loading(true)
                let response=await this.axios.post('web-quantri/khuvuc_diadanh/fn_lay_khoanvung_khuvuc',{
                    p_phanvung_id: phanvung_id,
                    p_khuvuc_id: khuvuc_id
                })
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },

    },
}
</script>