<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" />
        
        <div class="page-content">
            <div class="row">
                <DanhSachKho ref="danhsachkho" @focusedNodeChanged="focusedNodeChanged"/>
                <div class="col-sm-9 col-12">
                    <div class="box-col" style="height: 900px;">
                        <div class="box-form" >
                            <div class="legend-title">Thông tin kho biên lai</div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">Mã kho <k-required-marker/></div>
                                        <div class="value">
                                            <input type="text" :value="input_makho" @change="e=>input_makho=e.target.value" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w70">Tên kho <k-required-marker/></div>
                                        <div class="value">
                                            <input type="text" :value="input_tenkho" @change="e=>input_tenkho=e.target.value" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">Loại kho <k-required-marker/></div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="loaikho_selected"
                                                :filtering="onFilteringLoaiKho"
                                                :dataSource="danhsach_loaikho"
                                                :fields="{ text: 'LOAI_KHO', value: 'LOAIKHO_ID' }" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w70">Kho cha <k-required-marker/></div>
                                        <div class="value">
                                          
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="kho_selected"
                                                :filtering="onFilteringKho"
                                                :dataSource="danhsach_kho"
                                                :fields="{ text: 'ten_kho', value: 'kho_id' }" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key">Đơn vị chủ quản <k-required-marker/></div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="donvi_chuquan_selected"
                                                :filtering="onFilteringDonViCQ"
                                                :dataSource="danhsach_donvi_chuquan"
                                                :fields="{ text: 'TEN_DV', value: 'DONVI_ID' }" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w70">Số ĐT</div>
                                        <div class="value">
                                            <div class="input-more-button">
                                                <button class="btn" @click="clickNumberCode">
                                                    84
                                                </button>
                                                <input type="text" @keypress="isNumber($event)" :value="input_sdt" @change="e=>input_sdt=e.target.value" class="form-control ">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key">Ghi chú</div>
                                <div class="value">
                                    <textarea name="" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control" style="height: 100px;resize: none;"></textarea>
                                </div>
                            </div>
                            <div class="check-action">
                                <input type="checkbox" v-model="checkbox_hoatdong" class="check">
                                <span class="name">Hoạt động</span>
                            </div>
                        </div>
                        
                        <div class="box-move-select-table">
                            <div class="box-col box-form">
                                <div class="legend-title">Nhân viên chưa gán</div>
                                <div class="info-row">
                                    <div class="key w50">Đơn vị</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_donvi">
                                            <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_donvi'] }"
                                                v-model="donvi_selected" class="select2"
                                                @change="changeDonVi"
                                                :options="danhsach_donvi.map(e=> ({id: e.DONVI_ID, text: e.TEN_DV}))">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                                <DataGrid 
                                    :columns="columns" 
                                    :dataSource="dataSourcesNV_ChuaGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    panelDataHeight='380px'
                                    :showFilter="true"
                                    :enabledSelectFirstRow="false"
                                    @selectedItemsChanged="dataCheckedsChuaGan"
                                />
                            </div>
                            <div class="actions">
                                <button class="btn" @click="gan">
                                    <span class="-ap icon-chevron-thin-right"></span>
                                </button>
                                <button class="btn" @click="goGan">
                                    <span class="-ap icon-chevron-thin-left"></span>
                                </button>
                            </div>
                            <div class="box-col box-form">
                                <div class="legend-title">Nhân viên đã gán</div>
                                <DataGrid 
                                    :columns="columns" 
                                    :dataSource="dataSourcesNV_Gan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    panelDataHeight='380px'
                                    :showFilter="true"
                                    :enabledSelectFirstRow="false"
                                    @selectedItemsChanged="dataCheckedsGan"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <Footer/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ManageReceiptStoreAPI from './ManageReceiptStoreAPI'
import DanhSachKho from './DanhSachKho.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
export default {
    name:'ManageReceiptStore',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        DanhSachKho,
        KRequiredMarker
    },
    data(){
        return {
            header: {
                title: "Quản lý kho biên lai",
                list: [
                    {
                        name: "Quản lý biên lai thu tiền", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Quản lý kho biên lai",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'lammoi',
                    name:'Làm mới',
                    active: true,
                    icon_class:'one-reload'
                },
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Hủy',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xóa',
                    active: true,
                    icon_class:'one-trash'
                }
            ],
            danhsach_loaikho:[],
            loaikho_selected:null,
            danhsach_donvi_chuquan:[],
            donvi_chuquan_selected:null,
            danhsach_donvi:[],
            donvi_selected:null,
            danhsach_kho:[],
            kho_selected:null,

            input_ghichu:'',
            checkbox_hoatdong:false,
            input_sdt:'',
            input_makho:'',
            input_tenkho:'',
            dataSourcesNV_ChuaGan:[],
            columns:[
                {
                  fieldName: 'ten_dv',
                  headerText: '',
                  allowFiltering: true,
                  allowSorting: true,
                  isGroupedColumn: true
                },
                {
                  fieldName: 'ma_nv',
                  headerText: 'Mã nhân viên',
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: "ten_nv",
                  headerText: "Tên nhân viên",
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                }
            ],
            dataSourcesNV_Gan:[],
            itemNodeSelected:null,
            dataCheckGan:[],
            dataCheckChuaGan:[],
            tk_id:0,
            dvi_id:0
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='lammoi'){
                this.setActiveActions(0)
                this.clear()
                this.initDuLieu()

            }else if(action.id=='nhapmoi'){
                this.setActiveActions(1)
                if(this.itemNodeSelected!=null){
                    this.kho_selected=this.itemNodeSelected.kho_id
                }
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                if(!this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active){
                    this.setActiveActions(0)
                }
                if(this.itemNodeSelected!=null){
                    this.focusedNodeChanged(this.itemNodeSelected)
                }else{
                    this.setActiveActions(0)
                }
            }else if(action.id=='xoa'){
                if(this.itemNodeSelected==null){
                    this.$alert('Hãy chọn kho để thực hiện thao tác xoá!', '', {
                        dangerouslyUseHTMLString: true,
                        confirmButtonText: 'OK',
                        type: 'warning'
                    })
                    return
                }

                this.$confirm('Bạn có muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    this.sp_xoa_kho_bienlai(this.itemNodeSelected.kho_id)
                })
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                if(item.id!='lammoi'){
                    item.active=false
                }  
            })
            if(kieu==-1){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            }else if(kieu==0){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.clear()

            }else if(kieu==2){//Huỷ
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                this.clear()
            }else if(kieu==3){//edit
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        clear(){
            this.input_makho=''
            this.input_tenkho=''
            this.input_ghichu=''
            this.donvi_chuquan_selected=null
            this.kho_selected=null
            this.loaikho_selected=null
            this.input_sdt=''
            this.checkbox_hoatdong=false
            this.dataSourcesNV_Gan=[]
            this.dataSourcesNV_ChuaGan=[]

        },
        clickNumberCode(){
            this.input_sdt='0'
        },
        focusedNodeChanged(data){
            //console.log('data', data)
            //handle load nhân viên gán chưa gán
            this.itemNodeSelected=data

            if(!this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active){
                if(data.id==0){
                    this.kho_selected=null
                }else{
                    this.kho_selected=data.kho_id
                }
            }else{
                this.tk_id=-1
                this.input_tenkho=data.ten_kho_that?data.ten_kho_that.toString():''
                this.input_ghichu=data.ghichu?data.ghichu.toString():''
                this.checkbox_hoatdong=(data.trangthai!=null&&data.trangthai==1)?true:false

                if(data.so_dt!=null){
                    this.input_sdt=data.so_dt.toString()
                }else{
                    this.input_sdt=''
                }
                this.tk_id=data.tonkho?data.tonkho:0

                

                this.loaikho_selected=data.loaikho_id
                var vmakho=data.ma_kho?data.ma_kho.toString():''
                var tiento=''
                if(this.loaikho_selected!=null){
                    
                    var kt=this.danhsach_loaikho.filter((x)=>(x.LOAIKHO_ID==this.loaikho_selected&&x.PREFIX==1))
                    if(kt.length>0){
                        tiento = kt[0].MA_LOAI.toString()
                        if(data.ma_kho.split('.').length>1){
                            if (data.ma_kho.split('.')[0] != tiento){
                                tiento = data.ma_kho.split('.')[0]
                            }
                            vmakho = "";
                            for (i = 0; i < data.ma_kho.split('.').length; i++)
                            {
                                if (i == 0 || data.ma_kho.split('.')[i].toString() == "")
                                    continue;
                                if(i==(data.ma_kho.split('.').Length-1)){
                                    vmakho += data.ma_kho.split('.')[i]
                                }else{
                                    vmakho += data.ma_kho.split('.')[i] + "."
                                }
                                
                            }
                        }
                    }

                }
                this.input_makho=vmakho

                const index=this.danhsach_kho.findIndex(x=>x.kho_id==data.kho_cha_id)
                if(index>-1){
                    this.kho_selected=data.kho_cha_id
                }else{
                    this.kho_selected=null
                }
                this.donvi_chuquan_selected=data.donvi_id?Number(data.donvi_id):0
                console.log('donvi_chuquan_selected', this.donvi_chuquan_selected)
                this.dvi_id=data.donvi_id?data.donvi_id:0

                //this.donvi_selected=data.donvi_id
                this.nap_ds_kho_nv(data.kho_id)
                

                this.setActiveActions(3)
            }

        },
        async changeDonVi(){
            //get ds nhân viên gán đơn vị kho
            this.nap_ds_kho_nv(this.itemNodeSelected!=null?this.itemNodeSelected.kho_id:0)
        },
        onFilteringKho(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_kho', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_kho, query);
        },
        onFilteringLoaiKho(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('LOAI_KHO', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_loaikho, query);
        },
        onFilteringDonViCQ(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('TEN_DV', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_donvi_chuquan, query);
        },
        dataCheckedsGan(data){
            this.dataCheckGan=data
        },
        dataCheckedsChuaGan(data){
            this.dataCheckChuaGan=data
        },
        gan(){
            if(this.dataCheckChuaGan.length==0||this.itemNodeSelected==null){
                return
            }
            let ds_nhanvien=[]
            this.dataCheckChuaGan.forEach((item)=>{
                ds_nhanvien.push({
                    quyen_kg: item.quyen_kg,
                    donvi_id: item.donvi_id,
                    quyen_kn: item.quyen_kn,
                    nhanvien_id: item.nhanvien_id
                })
            })
            this.sp_capnhat_nhanvien_kho(1, this.itemNodeSelected.kho_id, ds_nhanvien)
        },
        goGan(){
            if(this.dataCheckGan.length==0||this.itemNodeSelected==null){
                return
            }
            let ds_nhanvien=[]
            this.dataCheckGan.forEach((item)=>{
                ds_nhanvien.push({
                    quyen_kg: item.quyen_kg,
                    donvi_id: item.donvi_id,
                    quyen_kn: item.quyen_kn,
                    nhanvien_id: item.nhanvien_id
                })
            })
            this.sp_capnhat_nhanvien_kho(0, this.itemNodeSelected.kho_id, ds_nhanvien)
        },
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                evt.preventDefault()
            } else {
                return true
            }
        },
        ghiDuLieu(){
            let is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            if(!this.kiemtra_dulieu(is_insert)){
                return
            }
            let data={
                kho_id: is_insert==1?null:this.itemNodeSelected.kho_id,
                kho_cha_id: this.kho_selected,
                trangthai: this.checkbox_hoatdong?1:0,
                ma_kho: this.input_makho.trim(),
                dongbo: 0,
                ghichu: this.input_ghichu.toString().trim(),
                donvi_id: this.donvi_chuquan_selected,
                ma_dv_fmis: "",
                chungtu: 0,
                ma_tk: 0,
                thukho_id: 0,
                ma_csht: "",
                thutu: 0,
                ten_kho: this.input_tenkho.trim(),
                so_dt: (this.input_sdt.trim()!=''&&this.input_sdt.trim()!='0')?Number(this.input_sdt):null,
                tonkho: 0,
                loaikho_id: this.loaikho_selected
            }

            this.sp_capnhat_kho_bienlai(data, is_insert)
            
        },
        kiemtra_dulieu(is_insert){
            if(this.input_makho.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập mã kho!')
                return false
            }
            if(this.$refs.danhsachkho.checkMaKhoTonTai(this.input_makho.toString())&&is_insert==1){
                this.$toast.error('Mã kho đã tồn tại!')
                return false
                
            }else if(is_insert==0&&this.itemNodeSelected!=null&&this.input_makho.trim()!=this.itemNodeSelected.ma_kho){
                //chế độ update không đươc phép thay đổi mã kho
                this.$toast.error('Mã kho đã tồn tại!')
                return false
            }
            if(this.input_tenkho.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập tên kho!')
                return false
            }
            if(this.loaikho_selected==null){
                this.$toast.error('Bạn phải chọn loại kho!')
                return false
            }
            if(this.donvi_chuquan_selected==null){
                this.$toast.error('Bạn phải chọn đơn vị!')
                return false
            }
            if(this.kho_selected==null){
                this.$toast.error('Bạn phải chọn kho cha!')
                return false
            }

            return true
        },
        async nap_ds_kho_nv(kho_id){
            //danh sách nhân viên chưa gán
            await this.sp_ds_kho_nv_chuagan(this.donvi_selected!=null?this.donvi_selected:-1, kho_id)
            //danh sách nhân viên gán 
            await this.sp_ds_kho_nv_gan(kho_id)
        },
        async lay_danhsach_loai_kho(){
            try{
                this.loading(true)
                let response=await ManageReceiptStoreAPI.lay_danhsach_loai_kho(this.axios)
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
        //danh sach đơn vị vs đơn vị chủ quản
        async lay_danhsach_donvi_chuquan(){
            try{
                this.loading(true)
                let response=await ManageReceiptStoreAPI.lay_danhsach_donvi_chuquan(this.axios)
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
        async sp_ds_kho(kho_id){
            try{
                this.loading(true)
                let response=await ManageReceiptStoreAPI.sp_ds_kho(this.axios, kho_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(item=>Object.assign(item,{
                        ten_kho:item.ma_kho+" - "+item.ten_kho
                    }))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_ds_kho_nv_chuagan(donvi_id, kho_id){
            try{
                this.loading(true)
                this.dataSourcesNV_ChuaGan=[]
                let response=await ManageReceiptStoreAPI.sp_ds_kho_nv(this.axios, donvi_id, kho_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesNV_ChuaGan=response.data.data
                }else{
                    this.dataSourcesNV_ChuaGan=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi khi lấy danh sách kho nhân viên chưa gán')
                }
            }finally{
                this.loading(false)
            }
        },
        async sp_ds_kho_nv_gan(kho_id){
            try{
                this.loading(true)
                this.dataSourcesNV_Gan=[]
                let response=await ManageReceiptStoreAPI.sp_ds_kho_nv_da_gan(this.axios, kho_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesNV_Gan=response.data.data
                }else{
                    this.dataSourcesNV_Gan=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi khi lấy danh sách kho nhân viên gán')
                }
            }finally{
                this.loading(false)
            }
        },
        async sp_capnhat_nhanvien_kho(action, kho_id, ds_nhanvien){
            try{
                this.loading(true)
                let response=await ManageReceiptStoreAPI.sp_capnhat_nhanvien_kho(this.axios, action, kho_id, ds_nhanvien)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='1'){
                        if(action==1){
                            this.dataCheckChuaGan=[]
                        }else{
                            this.dataCheckGan=[]
                        }
                        this.nap_ds_kho_nv(kho_id)
                    }else{
                        this.$toast.error(response.data.data)
                    }

                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error(action==1?'Đã có lỗi xảy ra khi gán nhân viên':'Đã có lỗi xảy ra khi xoá nhân viên')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error(action==1?'Đã có lỗi xảy ra khi gán nhân viên':'Đã có lỗi xảy ra khi xoá nhân viên')
                }
            }
        },
        async sp_xoa_kho_bienlai(kho_id){
            try{
                this.loading(true)
                let response = await ManageReceiptStoreAPI.sp_xoa_kho_bienlai(this.axios, kho_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success('Xóa dữ liệu kho thành công !')
                        this.itemNodeSelected=null
                        this.setActiveActions(0)
                        //refresh tree view
                        this.$refs.danhsachkho.deleteKho(kho_id)
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá kho không thành công!')
                    }
                }
            }catch(e){
                //console.log(e)
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xoá kho không thành công!')
                }
            }
        },
        //kieu==1: insert, 0 update
        async sp_capnhat_kho_bienlai(data, kieu){
            try{
                this.loading(true)
                let response = await ManageReceiptStoreAPI.sp_capnhat_kho_bienlai(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success(kieu==1?'Thêm kho thành công !':'Cập nhật dữ liệu kho thành công')
                        this.setActiveActions(3)
                        //refresh tree view
                        if(this.itemNodeSelected!=null){
                            this.$refs.danhsachkho.findFocusTreeViewAfterInit(this.itemNodeSelected.kho_id)
                        }
                    }else{
                        this.$toast.error(response.data.data)
                    }

                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, thực hiện thao tác không thành công!')
                    }
                }
            }catch(e){
                //console.log(e)
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, thực hiện thao tác không thành công!')
                }
            }
        },

        async initDuLieu(){
            this.danhsach_loaikho=await this.lay_danhsach_loai_kho()
            if(this.danhsach_loaikho.length>0){
                //Kho biên lai 
                this.loaikho_selected=199
            }

            let result=await this.lay_danhsach_donvi_chuquan()
            this.danhsach_donvi_chuquan=result
            this.danhsach_donvi=result
            if(this.danhsach_donvi_chuquan.length>0){
                this.donvi_chuquan_selected=this.danhsach_donvi_chuquan[0].DONVI_ID
            }
            if(this.danhsach_donvi.length>0){
                this.donvi_selected=this.danhsach_donvi[0].DONVI_ID
                await this.changeDonVi()
            }

            this.danhsach_kho=await this.sp_ds_kho(-1)
            if(this.danhsach_kho.length>0){
                this.kho_selected=this.danhsach_kho[0].kho_id
            }

            await this.$refs.danhsachkho.initDanhSachKho()
        }
    },
    mounted(){
        this.setActiveActions(0)
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }
}
</script>
