<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" />
        <div class="page-content">
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="box-form">
                        <div class="legend-title">Danh sách khu vực</div>
                        <div class="info-row">
                            <div class="key w100">
                                <div class="check-action">
                                    <input type="checkbox" :disabled="true" class="check">
                                    <span class="name">Loại KV</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select :disabled="true" v-model="loai_kv_selected" class="form-control">
                                        <option v-for="item in danhsach_loai_kv" :key="item.loaikv_id" :value="item.loaikv_id">{{item.loai_kv}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <KTableV3 :columns="columnsKhuVuc" :dataSources="dataSourcesKhuVuc" id="khuvuc_id" :rowSelectedId="rowSelectedIdKhuVuc" @onSelectedRow="selectedRowKV" :allowFilter="true" tableHeight="600px"/>
                        <!-- <k-table :columns="columnsKhuVuc" :dataSources="dataSourcesKhuVuc" @onSelectedRow="selectedRowKV" :allowFilter="true" :tableHeight="600" :rowSelectedIndex="1"/> -->
                    </div>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Nhân viên đã gán</div>
                            <KTableV3 :columns="columnsNV_Gan" :dataSources="dataSourcesNV_Gan" id="nhanvien_id" @dataCheckeds="onDataCheckedsNVGan" :allowCheckBox="true" :allowFilter="true" tableHeight="636px"/>
                            <!-- <k-table :columns="columnsNV_Gan" :dataSources="dataSourcesNV_Gan" @dataCheckeds="onDataCheckedsNVGan" :allowCheckBox="true" :allowFilter="true" :tableHeight="636" :rowSelectedIndex="1"/> -->
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
                            <div class="legend-title">Nhân viên chưa gán</div>
                            <div class="info-row">
                                <div class="key w70">Loại NV</div>
                                <div class="value">
                                    <ejs-dropdownlist 
                                        :allowFiltering="true"
                                        v-model="loai_nv_selected"
                                        @change="changeLoaiNV"
                                        :filtering="onFilteringLoaiNV"
                                        :dataSource="danhsach_loai_nv"
                                        :fields="{text: 'ten', value: 'loainv_id'}" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w70">Mã NV</div>
                                        <div class="value">
                                            <input v-model="input_ma_nv" @keyup.enter="onEnterMaNV" type="text" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w70">Mã ĐV</div>
                                        <div class="value">
                                            <input v-model="input_ma_dv" @keyup.enter="onEnterMaDV" type="text" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w70">Đơn vị</div>
                                <div class="value">
                                    <ejs-dropdownlist 
                                        :allowFiltering="true"
                                        v-model="donvi_selected"
                                        :filtering="onFilteringDonVi"
                                        @change="changeDV"
                                        :dataSource="danhsach_donvi"
                                        :fields="{text: 'ten_dv', value: 'donvi_id'}" />

                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w70">Nhiệm vụ</div>
                                <div class="value">
                                    <ejs-dropdownlist 
                                        :allowFiltering="true"
                                        v-model="nhiemvu_selected"
                                        :filtering="onFilteringNhiemVu"
                                        :dataSource="danhsach_nhiemvu"
                                        :fields="{text: 'name', value: 'id'}" />
                                </div>
                            </div>
                            <KTableV3 :columns="columnsNV_ChuaGan" :dataSources="dataSourcesNV_ChuaGan" id="nhanvien_id" @dataCheckeds="onDataCheckedsNVChuaGan" :allowCheckBox="true" :allowFilter="true" tableHeight="490px"/>
                            <!-- <k-table :columns="columnsNV_ChuaGan" :dataSources="dataSourcesNV_ChuaGan" @dataCheckeds="onDataCheckedsNVChuaGan" :allowCheckBox="true" :allowFilter="true" :tableHeight="490" :rowSelectedIndex="1"/> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Footer/>
        <!-- Modal -->
        <CapNhatNhanVienPopup ref="capnhatnhanvien" />
        <DSCacKhuVucPopup ref="dscackhuvuc" :ma_nv="input_ma_nv" @accept="acceptKhuVuc"/>
        <ViewHistoryModal ref="viewHistoryModal" :data="lich_su"/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTable from '@/components/kylq_components/KTable.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import CapNhatNhanVienPopup from '../StaffLocation/CapNhatNhanVienPopup.vue'
import DSCacKhuVucPopup from '../StaffLocation/DSCacKhuVucPopup.vue'
import AssignTechStaffLocationAPI from './AssignTechStaffLocationAPI'
import ViewHistoryModal from '../StaffLocation/ViewHistoryModal.vue'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
export default {
    name:'AssignTechStaffLocation',
    components:{
        breadcrumb,
        Footer,
        KTable,
        KTableV3,
        ActionTop,
        CapNhatNhanVienPopup,
        DSCacKhuVucPopup,
        ViewHistoryModal
    },
    data(){
        return{
            header: {
                title: "Gán nhân viên - khu vực",
                list: [
                    { 
                        name: "Hợp đồng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Cấu hình, đồng bộ",
                        link: { name: "Ui.buttons" },
                        active: false
                    },
                    {
                        name: "Gán địa bàn - NVKT",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'timkhuvuc',
                    name:'Tìm khu vực',
                    active: true,
                    icon_class:'one-map-location'
                },
                {
                    id:'gan_nvql',
                    name:'Gán NVQL',
                    active: true,
                    icon_class:'one-users'
                },
                {
                    id:'lichsu_capnhat',
                    name:'Lịch sử cập nhật',
                    active: false,
                    icon_class:'one-reload'
                },
            ],

            columnsKhuVuc:[
                {
                    field: "stt",
                    label: "STT",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ma_kv",
                    label: "Mã KV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_kv",
                    label: "Tên KV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ma_kv_cha",
                    label: "Ma KV cha",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_kv_cha",
                    label: "Tên KV cha",
                    allowFilter: true,
                    allowSorting:true
                },
            ],
            dataSourcesKhuVuc:[

            ],
            columnsNV_Gan:[
                {
                    field: "loai_nv",
                    label: "Loại NV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ma_nv",
                    label: "Mã NV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_nv",
                    label: "Tên nhân viên",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_nhiemvu",
                    label: "Nhiệm vụ",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_dv",
                    label: "Đơn vị",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "so_dt",
                    label: "Số ĐT",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "email",
                    label: "Email",
                    allowFilter: true,
                    allowSorting:true
                },

            ],
            dataSourcesNV_Gan:[

            ],
            columnsNV_ChuaGan:[
                {
                    field: "ma_nv",
                    label: "Mã NV",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_nv",
                    label: "Tên nhân viên",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_dv",
                    label: "Đơn vị",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "so_dt",
                    label: "Số ĐT",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "email",
                    label: "Email",
                    allowFilter: true,
                    allowSorting:true
                },

            ],
            dataSourcesNV_ChuaGan:[

            ],
            danhsach_loai_nv:[],
            loai_nv_selected:-1,
            danhsach_loai_kv:[],
            //mặc định và cố định Địa bàn cấp 3 với loaikv_id=13
            loai_kv_selected:13,
            danhsach_donvi:[],
            donvi_selected:null,
            //create ds nhiem vu
            danhsach_nhiemvu:[
                {
                    id:1,
                    name:'Lắp đặt + xử lý sự cố dịch vụ'
                },
                {
                    id:2,
                    name:'Lắp đặt dịch vụ'
                },
                {
                    id:3,
                    name:'Xử lý sự cố dịch vụ'
                },
                {
                    id:4,
                    name:'Điều hành địa bàn'
                }
            ],
            nhiemvu_selected:1,
            khuvuc_id_selected:-1,
            dataCheckedGan:[],
            dataCheckedChuaGan:[],
            input_ma_nv:'',
            input_ma_dv:'',
            lich_su:[],
            isChangeFirstDonvi:true,
            isChangeFirstLoaiNV:true,
            rowSelectedIdKhuVuc:-1,
            loai_form:0,
            loai_kvs:[4, 13]



        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='gan_nvql'){
                this.$refs.capnhatnhanvien.showModal()
            }else if(action.id=='timkhuvuc'){
                this.$refs.dscackhuvuc.showModal()
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='lichsu_capnhat'){
                this.lichSuCapNhat()
            }
        },
        async ghiDuLieu(){
            if(this.dataSourcesKhuVuc.length==0){
                this.$toast.error('Chưa có khu vực để gán NV!')
                return
            }
            if(this.loai_nv_selected==-1){
                this.$toast.error('Chưa có loại nhân viên')
                return
            }
            if(this.khuvuc_id_selected==-1){
                this.$toast.error('Bạn hãy chọn Khu vực để cập nhật thông tin')
                return
            }
            let nhanvien_nhiemvu=this.getNhanVienNhiemVu()
            let nhanvien_khuvuc=this.getNhanVienKhuVuc()

            let nguoi_cn=this.$root.token.getUserName()
            let ip_cn=await this.$root.token.getIP()
            let may_cn=await this.$root.token.getMachine()

            let data={
                khuvuc_id: this.khuvuc_id_selected, 
                loaikv_id: this.loai_kv_selected,
                loainv_id: this.loai_nv_selected,
                nhiemvu: this.nhiemvu_selected,
                may_cn: may_cn?may_cn.toString():'localhost',
                nguoi_cn: nguoi_cn,
                ip_cn: ip_cn?ip_cn.toString():'127.0.0.1',
                loai_form:  this.loai_form,
                danhsach_dagan: JSON.stringify(nhanvien_nhiemvu),
                danhsach_khuvuc_nv: JSON.stringify(nhanvien_khuvuc)
            }
            console.log('data', data)
            this.sp_ghilai_gan_nv_khuvuc(data)
        },
        getNhanVienNhiemVu(){
            return this.dataSourcesNV_Gan.map(item=>{
                return {
                    NHANVIEN_ID:item.nhanvien_id,
                    LOAINV_ID:item.loainv_id,
                    NHIEMVU:item.nhiemvu!=null?item.nhiemvu:null
                }
            })
        },
        getNhanVienKhuVuc(){
            var list=[]
            for(let i=0;i<this.loai_kvs.length;i++){
                list=list.concat(this.dataSourcesNV_Gan.map(item=>{
                    return {
                        KHUVUC_ID:this.khuvuc_id_selected,
                        NHANVIEN_ID:item.nhanvien_id,
                        LOAINV_ID:item.loainv_id,
                        LOAIKV_ID:this.loai_kvs[i],
                        NHIEMVU:item.nhiemvu!=null?item.nhiemvu:null
                    }
                }))
            }
            return list
        },
        onFilteringDonVi(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_dv', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_donvi, query);
        },
        onFilteringNhiemVu(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('name', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_nhiemvu, query);
        },
        onFilteringLoaiNV(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_loai_nv, query);
        },
        async selectedRowKV(item){
            this.khuvuc_id_selected=item.khuvuc_id
            await this.sp_hienthi_nhanvien_theo_kv(1)
            await this.sp_hienthi_nhanvien_theo_kv(2)
        },
        async changeLoaiNV(){
            if(this.isChangeFirstLoaiNV){
                this.isChangeFirstLoaiNV=false
                return
            }
            await this.sp_hienthi_nhanvien_theo_kv(1)
            await this.sp_hienthi_nhanvien_theo_kv(2)
        },
        async changeDV(){
            if(this.isChangeFirstDonvi){
                this.isChangeFirstDonvi=false
                return
            }
            await this.sp_hienthi_nhanvien_theo_kv(1)
            await this.sp_hienthi_nhanvien_theo_kv(2)
        },
        acceptKhuVuc(item){
            console.log('item', item)
            //không sử lý với form này
        },
        onDataCheckedsNVGan(data){
            this.dataCheckedGan=data
        },
        onDataCheckedsNVChuaGan(data){
            this.dataCheckedChuaGan=data
        },
        onEnterMaNV(){
            this.sp_hienthi_nhanvien_theo_kv(2)
        },
        onEnterMaDV(){
            this.sp_hienthi_nhanvien_theo_kv(2)
        },
        boGan(){
            if(this.dataCheckedGan.length==0){
                return
            }
            this.dataCheckedGan.forEach((item)=>{
                this.dataSourcesNV_ChuaGan.push({
                    ma_nv:item.ma_nv,
                    ten_nv:item.ten_nv,
                    ten_dv:item.ten_dv,
                    nhanvien_id:item.nhanvien_id,
                    so_dt:item.so_dt,
                    email: item.email
                })
                const index = this.dataSourcesNV_Gan.indexOf(item)
                if(index>-1){
                    this.dataSourcesNV_Gan.splice(index,1)
                }
            })
            this.dataCheckedGan=[]
        },
        boGanAll(){
            if(this.dataSourcesNV_Gan.length==0){
                return
            }
            this.dataSourcesNV_Gan.forEach((item)=>{
                this.dataSourcesNV_ChuaGan.push({
                    ma_nv:item.ma_nv,
                    ten_nv:item.ten_nv,
                    ten_dv:item.ten_dv,
                    nhanvien_id:item.nhanvien_id,
                    so_dt:item.so_dt,
                    email: item.email
                })
            })
            this.dataSourcesNV_Gan=[]
        },
        gan(){
            if(this.dataCheckedChuaGan.length==0){
                return
            }
            
            let nhiemvu=this.danhsach_nhiemvu.find(x=>x.id==this.nhiemvu_selected).name
            let loai_nv=this.danhsach_loai_nv.find(x=>x.loainv_id==this.loai_nv_selected).ten
            this.dataCheckedChuaGan.forEach((item)=>{
                this.dataSourcesNV_Gan.push({
                    ma_nv:item.ma_nv,
                    ten_nv:item.ten_nv,
                    ten_dv:item.ten_dv,
                    nhanvien_id:item.nhanvien_id,
                    nhiemvu:this.nhiemvu_selected,
                    ten_nhiemvu: nhiemvu,
                    loainv_id: this.loai_nv_selected,
                    loai_nv: loai_nv,
                    so_dt:item.so_dt,
                    email: item.email
                })

                const index = this.dataSourcesNV_ChuaGan.indexOf(item)
                if(index>-1){
                    this.dataSourcesNV_ChuaGan.splice(index,1)
                }
                
            })

            this.dataCheckedChuaGan=[]

        },
        ganAll(){
            if(this.dataSourcesNV_ChuaGan.length==0){
                return
            }
            let nhiemvu=this.danhsach_nhiemvu.find(x=>x.id==this.nhiemvu_selected).name
            let loai_nv=this.danhsach_loai_nv.find(x=>x.loainv_id==this.loai_nv_selected).ten
            this.dataSourcesNV_ChuaGan.forEach((item)=>{
                this.dataSourcesNV_Gan.push({
                    ma_nv:item.ma_nv,
                    ten_nv:item.ten_nv,
                    ten_dv:item.ten_dv,
                    nhanvien_id:item.nhanvien_id,
                    nhiemvu:this.nhiemvu_selected,
                    ten_nhiemvu: nhiemvu,
                    loainv_id: this.loai_nv_selected,
                    loai_nv: loai_nv,
                    so_dt:item.so_dt,
                    email: item.email
                })
            }) 
            this.dataSourcesNV_ChuaGan=[]
        },
        async lichSuCapNhat(){
            try{
                this.loading(true)
                let response=await AssignTechStaffLocationAPI.lay_ds_thongtin_log_khuvuc(this.axios, this.khuvuc_id_selected)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.lich_su=response.data.data.map((item,index)=>{
                      return Object.assign(item, {id:index})
                    })
                }
                this.$nextTick(()=>{
                    this.$refs.viewHistoryModal.showModal()
                })
            }catch(e){
                this.loading(false)
            }
        },
        async sp_lay_loainv_nhomlv(){
            try{
                this.danhsach_loai_nv=[]
                let response=await AssignTechStaffLocationAPI.sp_lay_loainv_nhomlv(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_loai_nv=response.data.data
                    if(this.danhsach_loai_nv.length>0){
                        //default 51: Nhân viên quản lý kỹ thuật
                        const index =this.danhsach_loai_nv.findIndex(x=>x.loainv_id==51)
                        if(index>-1){
                            this.loai_nv_selected=this.danhsach_loai_nv[index].loainv_id
                        }else{
                            this.loai_nv_selected=this.danhsach_loai_nv[0].loainv_id
                        }
                        
                    }
                }else{
                    this.danhsach_loai_nv=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách loại nhân viên')
            }
        },
        async sp_lay_ds_thamso_md_mats(){
            try{
                let response=await AssignTechStaffLocationAPI.sp_lay_ds_thamso_md_mats(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data.length>0){
                        this.actions[this.actions.findIndex(x=>x.id=='lichsu_capnhat')].active=true
                    }
                }else{
                    this.actions[this.actions.findIndex(x=>x.id=='lichsu_capnhat')].active=false
                }
            }catch(e){
                this.actions[this.actions.findIndex(x=>x.id=='lichsu_capnhat')].active=false
            }
        },
        async sp_lay_ds_loai_kv(){
            try{
                this.danhsach_loai_kv=[]
                let response=await AssignTechStaffLocationAPI.sp_lay_ds_loai_kv(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_loai_kv=response.data.data
                }else{
                    this.danhsach_loai_kv=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách loại khu vực')
            }
        },
        async sp_lay_ds_donvi_theo_nhomlnv(){
            try{
                this.danhsach_donvi=[]
                let response=await AssignTechStaffLocationAPI.sp_lay_ds_donvi_theo_nhomlnv(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_donvi=response.data.data
                    if(this.danhsach_donvi.length>0){
                        this.donvi_selected=this.danhsach_donvi[0].donvi_id
                    }
                    console.log('donvi_selected', this.donvi_selected)
                    
                }else{
                    this.danhsach_donvi=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách đơn vị')
            }
        },
        async sp_lay_ds_khuvuc_theo_loaikv(data){
            try{
                this.loading(true)
                this.dataSourcesKhuVuc=[]
                let response=await AssignTechStaffLocationAPI.sp_lay_ds_khuvuc_theo_loaikv(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                   
                    this.dataSourcesKhuVuc=response.data.data
                    if(this.dataSourcesKhuVuc.length>0){
                        this.khuvuc_id_selected=this.dataSourcesKhuVuc[0].khuvuc_id
                        this.rowSelectedIdKhuVuc=this.dataSourcesKhuVuc[0].khuvuc_id
                        await this.sp_hienthi_nhanvien_theo_kv(1)
                        await this.sp_hienthi_nhanvien_theo_kv(2)
                    }

                }else{
                    this.dataSourcesKhuVuc=[]
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Không load được danh sách đơn vị')
            }
        },

        //loai=1: Đã gán, loai=2 chưa gán
        async sp_hienthi_nhanvien_theo_kv(loai){
            try{
                let data={
                    loai:loai,
                    loaikv_id: this.loai_kv_selected,
                    khuvuc_id: this.khuvuc_id_selected,
                    loainv_id: this.loai_nv_selected,
                    donvi_id: this.donvi_selected,
                    loaiform:this.loai_form
                }
                if(loai==1){
                    this.dataSourcesNV_Gan=[]
                }else{
                     this.dataSourcesNV_ChuaGan=[]
                }
                this.loading(true)
                let response=await AssignTechStaffLocationAPI.sp_hienthi_nhanvien_theo_kv(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(loai==1){
                        this.dataSourcesNV_Gan=response.data.data
                    }else{
                        this.dataSourcesNV_ChuaGan=response.data.data.filter((item)=>{
                            var result=true;
                            if(item.ma_nv){
                                result=result&&item.ma_nv.toString().toLowerCase().indexOf(this.input_ma_nv.trim().toLowerCase())>-1
                            }
                            if(item.ma_dv){
                                result=result&&item.ma_dv.toString().toLowerCase().indexOf(this.input_ma_dv.trim().toLowerCase())>-1
                            }
                            return result
                        })
                    }
                }else{
                    if(loai==1){
                        this.dataSourcesNV_Gan=[]
                    }else{
                        this.dataSourcesNV_ChuaGan=[]
                    }
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Không load được danh sách nhân viên')
            }
        },
        async sp_ghilai_gan_nv_khuvuc(data){
            try{
                this.loading(true)
                let response=await AssignTechStaffLocationAPI.sp_ghilai_gan_nv_khuvuc(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật dữ liệu thành công')
                    await this.sp_hienthi_nhanvien_theo_kv(1)
                    await this.sp_hienthi_nhanvien_theo_kv(2)
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi không thể cập nhật được dữ liệu')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi không thể cập nhật được dữ liệu')
                }
            }
        },
        async initDuLieu(){
            //Khởi tạo tag form
            let tag=this.$route.query.tag
            console.log('initDuLieu tag', tag)
            if(tag&&tag!=''){
                let words=tag.split(':')
                if(words.length>0){
                    this.loai_form=Number(words[0])
                }
                if(words.length>1){
                    this.loai_kvs=words[1].split('+').map(x=>Number(x))
                }
                console.log('loai_form', this.loai_form)
                console.log('loai_kvs', this.loai_kvs)
            }
            //
            await this.sp_lay_ds_thamso_md_mats()
            await this.sp_lay_loainv_nhomlv()
            await this.sp_lay_ds_loai_kv()
            await this.sp_lay_ds_donvi_theo_nhomlnv()

            let ma_tinh=this.$root.token.getMaTinh()
            let donvi_id=this.$root.token.getDonViID()
            let nhanvien_id=this.$root.token.getNhanVienID()
            
            let data={
                loaikv_id: this.loai_kv_selected,
                tentat: ma_tinh,
                donvi_id: donvi_id,
                nhanvien_id: nhanvien_id
            }
            await this.sp_lay_ds_khuvuc_theo_loaikv(data)
        }

    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },200)
    }
}
</script>