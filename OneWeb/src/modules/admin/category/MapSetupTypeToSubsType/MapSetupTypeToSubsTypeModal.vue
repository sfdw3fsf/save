<template>
  <b-modal
    ref="popupMapSetupTypeToSubsType"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal">

    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cập nhật kiểu lắp đặt - loại
          hình thuê bao
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div v-if="password.hasPermission">
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="popup-body">
            <div class="box-form">
            <div class="row">
                <div class="col-sm-5 col-12">
                <div class="list-checks">
                    <div class="item">
                    <div class="check-action" title="Áp dụng cho loại hình của thuê bao hiện tại" >
                        <input type="radio" class="check" :checked="vkieu==2" name="kieuld" value="2" @change="changeKieu($event)"/>
                        <span class="name">KIEU_LD_LHTB</span>
                    </div>
                    </div>
                    <div class="item">
                    <div class="check-action" title="Áp dụng cho thuê bao có sẵn, kèm">
                        <input type="radio" class="check" :checked="vkieu==1" name="kieuld" value="1" @change="changeKieu($event)"/>
                        <span class="name">KIEULD_LHTB</span>
                    </div>
                    </div>
                    <div class="item">
                    <div class="check-action" title="Loại hình mới: Áp dụng cho chuyển đổi loại hình">
                        <input type="radio" class="check" :checked="vkieu==3" name="kieuld" value="3" @change="changeKieu($event)"/>
                        <span class="name">KIEULD_LHTB_DICH</span>
                    </div>
                    </div>
                </div>
                </div>
                <div class="col-sm-3 col-12">
                <div class="info-row marb0">
                    <div class="key w30 nowrap">Loại hợp đồng</div>
                    <div class="value">
                        <div class="select-custom" ref="ds_loai_hd">
                            <select2 :settings="{ dropdownParent: $refs['ds_loai_hd'] }" ref="cboLoaiHD"
                                v-model="loai_hd_selected" class="select2"
                                :options="danhsach_loai_hd.map(e=> ({id: e.loaihd_id, text: e.ten_loaihd}))"
                                @change="changeLoaiHD"
                            >
                            </select2>
                        </div>  
                    </div>
                </div>
                </div>
                <div class="col-sm-1 col-12"></div>
                <div class="col-sm-3 col-12">
                <div class="info-row marb0">
                    <div class="key w30 nowrap">Dịch vụ</div>
                    <div class="value">
                        <div class="select-custom" ref="ds_loai_hd">
                            <select2 :settings="{ dropdownParent: $refs['ds_loai_hd'] }" ref="cboLoaiHD"
                                v-model="dichvu_vt_selected" class="select2"
                                :options="danhsach_dichvu_vt.map(e=> ({id: e.dichvuvt_id, text: e.ten_dvvt}))"
                                @change="changeDichVuVT"
                            >
                            </select2>
                        </div>  
                    </div>
                </div>
                </div>
            </div>
            </div>
            <div class="row">
            <div class="col-sm-5 col-12">
                <div class="box-form">
                    <div class="legend-title">Danh sách kiểu lắp đặt</div>
                    <DataGrid
                        :columns="columnsKieuLapDat"
                        :dataSource="dataSourcesKieuLapDat"
                        :showColumnCheckbox="false"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="true"
                        panelDataHeight="400px"
                        @selectedItemsChanged="gridDsChanged"/>
                </div>
            </div>
            <div class="col-sm-7 col-12">
                <div class="box-move-select-table">
                <div class="box-col box-form">
                    <div class="legend-title">Loại hình đã gán</div>
                    <DataGrid
                        :columns="columnsLoaiHinh"
                        :dataSource="dataSourcesGan"
                        :showColumnCheckbox="true"
                        :enable-paging-server="false"
                        :allowPaging="false"
                        :showFilter="true"
                        ref="gridDaGan"
                        :enabledSelectFirstRow="false"
                        panelDataHeight="400px"/>
                </div>
                <div class="actions">
                    <button class="btn" @click="boGan">
                    <span class="fa fa-angle-right"></span>
                    </button>
                    <button class="btn" @click="boGanAll">
                    <span class="fa fa-angle-double-right"></span>
                    </button>
                    <button class="btn" @click="gan">
                    <span class="fa fa-angle-left"></span>
                    </button>
                    <button class="btn" @click="ganAll">
                    <span class="fa fa-angle-double-left"></span>
                    </button>
                </div>
                <div class="box-col box-form">
                    <div class="legend-title">Loại hình chưa gán</div>
                    <DataGrid
                        :columns="columnsLoaiHinh"
                        :dataSource="dataSourcesChuaGan"
                        :showColumnCheckbox="true"
                        :enable-paging-server="false"
                        :allowPaging="false"
                        :showFilter="true"
                        ref="gridChuaGan"
                        :enabledSelectFirstRow="false"
                        panelDataHeight="400px"/>
                </div>
                </div>
            </div>
            </div>
        </div>
      </div>
      <password-view style="padding: 10px;" v-else @onPasswordSubmittedSuccessListener="password.hasPermission=true" />
    </div>

  </b-modal>
</template>
<script>
import PasswordView from '@/modules/admin/category/GrantUserPermission/components/password-view.vue'
import GrantUserPermissionAPI from '@/modules/admin/category/GrantUserPermission/GrantUserPermissionAPI'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import MapSetupTypeToSubsTypeAPI from './MapSetupTypeToSubsTypeAPI'
export default {
    name:'MapSetupTypeToSubsTypeModal',
    components:{
        ActionTop,
        PasswordView
    },
    data(){
        return{
            password: {
                hasPermission: false,
                value: ''
            },
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
            ],
            columnsKieuLapDat:[
                {
                    fieldName:'kieuld_id',
                    headerText:'ID',
                    allowFiltering:true,
                    width:80
                },
                {
                    fieldName:'ten_kieuld',
                    headerText:'Kiểu lắp đặt',
                    allowFiltering:true
                }
            ],
            dataSourcesKieuLapDat:[],
            columnsLoaiHinh:[
                {
                    fieldName:'loaihinh_tb',
                    headerText:'Loại hình đã gán',
                    allowFiltering:true
                },
            ],
            dataSourcesGan:[],
            dataSourcesChuaGan:[],
            danhsach_loai_hd:[],
            loai_hd_selected:null,
            vkieu:1,
            kieuld_id_selected:null,
            danhsach_dichvu_vt:[],
            dichvu_vt_selected:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
                this.ghiLai()
            }
        },
        showModal() {
            this.$refs["popupMapSetupTypeToSubsType"].show()
        },
        hideModal() {
            this.$refs["popupMapSetupTypeToSubsType"].hide()
        },
        handleShowModal(){
            this.dataSourcesKieuLapDat=[]
            this.dataSourcesGan=[]
            this.dataSourcesChuaGan=[]
            this.danhsach_loai_hd=[]
            this.loai_hd_selected=null
            this.vkieu=2
            this.kieuld_id_selected=null
            this.danhsach_dichvu_vt=[]
            this.dichvu_vt_selected=null

            setTimeout(()=>{
                this.initDulieu()
            }, 500)
        },
        ghiLai(){
            if(this.kieuld_id_selected==null){
                this.$toast.error('Bạn chưa chọn kiểu lắp đặt để gán loại hình !')
                return
            }

            //Theo mô tả trong code desktop với đặc tả api
            //json_ds_kieuld_lhtb: kieu=1
            //json_ds_kieu_ld_lhtb: kieu=2
            //json_ds_kieu_lhtb_dich: kieu=3
            //trên giao diện lable radio hơi lạ

            //kieu=1
            let ds_kieuld_lhtb=this.getData(1)
            //kieu=2
            let ds_kieu_ld_lhtb=this.getData(2)
            //kieu=3
            let ds_kieu_lhtb_dich=this.getData(3)

            

            let data={
                vkieu: parseInt(this.vkieu),
                kieuld_id: this.kieuld_id_selected,
                json_ds_kieu_ld_lhtb: ds_kieu_ld_lhtb,
                json_ds_kieuld_lhtb: ds_kieuld_lhtb,
                json_ds_kieu_lhtb_dich: ds_kieu_lhtb_dich,
            }

            console.log('data', data)

            this.capnhat_kieuld_lhtb(data)



        },
        getData(value){
            if(parseInt(this.vkieu)==value){
                var result=[]
                this.dataSourcesGan.forEach((item)=>{
                    result.push({
                        kieuld_id:this.kieuld_id_selected,
                        loaitb_id:item.loaitb_id
                    })
                })
                return result
            }else{
                return []
            }
            
        },
        changeKieu(event){
            this.vkieu=event.target.value
            console.log('vkieu',event.target.value)
            this.hienthi_dl()
        },
        gridDsChanged(data){
            if(this.dataSourcesKieuLapDat.length<=0||data.length<=0){
                this.kieuld_id_selected=0
                return
            }
            this.selectedRowKieuLd(data[0])
        },
        selectedRowKieuLd(item){
            this.kieuld_id_selected=item.kieuld_id
            this.hienthi_dl()
        },
        boGan(){
            let dataCheckedsLHGan=this.$refs.gridDaGan.getSelectedRecords()
            if(dataCheckedsLHGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsLHGan.length;i++){
                this.dataSourcesChuaGan.push(dataCheckedsLHGan[i])
                const index=this.dataSourcesGan.findIndex(x=>x.loaitb_id==dataCheckedsLHGan[i].loaitb_id)
                if(index>-1){
                    this.dataSourcesGan.splice(index,1)
                }
            }
            this.dataSourcesGan=[].concat(this.dataSourcesGan)
            this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        boGanAll(){
            if(this.dataSourcesGan.length==0){
                return
            }
            this.dataSourcesChuaGan=this.dataSourcesChuaGan.concat(this.dataSourcesGan)
            this.dataSourcesGan=[]
        },
        gan(){
            let dataCheckedsLHChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
            if(dataCheckedsLHChuaGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsLHChuaGan.length;i++){
                this.dataSourcesGan.push(dataCheckedsLHChuaGan[i])
                const index=this.dataSourcesChuaGan.findIndex(x=>x.loaitb_id==dataCheckedsLHChuaGan[i].loaitb_id)
                if(index>-1){
                    this.dataSourcesChuaGan.splice(index,1)
                }
            }
            this.dataSourcesGan=[].concat(this.dataSourcesGan)
            this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        ganAll(){
            if(this.dataSourcesChuaGan.length==0){
                return
            }
            this.dataSourcesGan=this.dataSourcesGan.concat(this.dataSourcesChuaGan)
            this.dataSourcesChuaGan=[]
        },
        async changeLoaiHD(){
            this.dataSourcesKieuLapDat=await this.lay_kieuld_tb(this.loai_hd_selected)
            //this.lay_kieuld_tb(this.loai_hd_selected)
        },
        changeDichVuVT(){
            this.hienthi_dl()
        },
        async lay_danhsach_dichvuvt(){
            try{
                this.loading(true)
                let response = await MapSetupTypeToSubsTypeAPI.lay_ds_dichvuvt(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.dichvuvt_id - b.dichvuvt_id
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_loai_hd(){
            try{
                this.loading(true)
                let response = await MapSetupTypeToSubsTypeAPI.lay_loai_hd(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.loaihd_id - b.loaihd_id
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async initDulieu(){
            this.danhsach_dichvu_vt=await this.lay_danhsach_dichvuvt()
            if(this.danhsach_dichvu_vt.length>0){
                this.dichvu_vt_selected=this.danhsach_dichvu_vt[0].dichvuvt_id
            }
            this.danhsach_loai_hd=await this.lay_loai_hd()
            if(this.danhsach_loai_hd.length>0){
                this.loai_hd_selected=this.danhsach_loai_hd[0].loaihd_id
                this.dataSourcesKieuLapDat=await this.lay_kieuld_tb(this.loai_hd_selected)
            }
        },
        async lay_kieuld_tb(loaihd_id){
            try{
                this.loading(true)
                let response = await MapSetupTypeToSubsTypeAPI.lay_kieuld_tb(this.axios, loaihd_id)
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
        },

        async lay_kieuld_lhtb(data){
            try{
                this.loading(true)
                let response = await MapSetupTypeToSubsTypeAPI.lay_kieuld_lhtb(this.axios, data)
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
        },
        async capnhat_kieuld_lhtb(data){
            try{
                this.loading(true)
                let response=await MapSetupTypeToSubsTypeAPI.capnhat_kieuld_lhtb(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success('Cập nhật dữ liệu thành công')
                        await this.hienthi_dl()
                    }else{
                        this.$toast.error(response.data.data)
                    }
                    
                }else{
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi xảy ra không cập nhật được dữ liệu')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra không cập nhật được dữ liệu')
                }
            }
        },
        async hienthi_dl(){
            if(this.kieuld_id_selected==null||this.dichvu_vt_selected==null){
                return
            }
            this.dataSourcesGan=await this.lay_kieuld_lhtb({
                vloai: 1,
                vkieuld_id: this.kieuld_id_selected,
                vkieu: this.vkieu,
                vdichvuvt_id: this.dichvu_vt_selected
            })
            this.dataSourcesChuaGan=await this.lay_kieuld_lhtb({
                vloai: 2,
                vkieuld_id: this.kieuld_id_selected,
                vkieu: this.vkieu,
                vdichvuvt_id: this.dichvu_vt_selected
            })
        },
        async onPasswordSubmitted() {
            if (this.password.value.length == 0) {
                this.$toast.warning('Vui lòng nhập mật khẩu!')
                return;
            }
            try {
                var data = await GrantUserPermissionAPI.kiemtra_quyen_chucnang(this.axios, { password: this.password.value })
                if (data.data.data && data.data.data == 1) {
                this.password.hasPermission = true
                } else {
                this.$toast.error('Mật khẩu không đúng. Vui lòng thử lại!')
                }
            } catch (e) {
                this.$toast.error(`Đã xảy ra lỗi: ${e}`)
            }
        }
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>