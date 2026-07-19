<template>
    <div class="main-wrapper">
        <KBreadCrumb title="Thiết lập nhân viên nhận tin nhắn" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="grid-stack-box">
                <div class="box-col" style="width: calc(25% - 8px);">
                    <DanhSachDonVi ref="danhsachdonvi" @tvDonViChanged="tvDonViChanged" />
                </div>
                <div class="gutter gutter-horizontal" style="width: 16px;"></div>
                <div class="box-col" style="width: calc(75% - 8px);">
                    <div class="box-form">
                        <div class="legend-title">Thông tin thiết lập</div>
                        <div class="row">
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w100">Loại HĐ</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="checkLoaiHD" @change="changeCheckLoaiHD">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_loaihd">
                                            <select2 :settings="{ dropdownParent: $refs['ds_loaihd'] }" ref="cboLoaiHD"
                                                v-model="loaihd_selected" class="select2"
                                                :options="ds_loaihd.map(e=> ({id: e.LOAIHD_ID, text: e.TEN_LOAIHD}))"
                                                :disabled="!checkLoaiHD"
                                                @change="changeLoaiHD"
                                                >
                                            </select2>
                                        </div>  
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Nhóm quy trình</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_nhomqt">
                                            <select2 :settings="{ dropdownParent: $refs['ds_nhomqt'] }" ref="cboNhomQuyTrinh"
                                                v-model="nhomqt_selected" class="select2"
                                                :options="ds_nhomqt.map(e=> ({id: e.NHOM_QT_ID, text: e.TEN_NHOM}))"
                                                @change="changeNhomQT">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w100">Dịch vụ</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="checkDichVuVT" @change="changeCheckDichVuVT">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_dichvuvt">
                                            <select2 :settings="{ dropdownParent: $refs['ds_dichvuvt'] }" ref="cboDichVuTK"
                                                v-model="dichvuvt_selected" class="select2"
                                                :options="ds_dichvuvt.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                                                :disabled="!checkDichVuVT"
                                                @change="changeDichVuVT"
                                                >
                                            </select2>
                                        </div> 
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Quy trình</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="checkQuyTrinh" @change="changeCheckQuyTrinh">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_quytrinh">
                                            <select2 :settings="{ dropdownParent: $refs['ds_quytrinh'] }" ref="cboQuyTrinhTK"
                                                v-model="quytrinh_selected" class="select2"
                                                :options="ds_quytrinh.map(e=> ({id: e.QUYTRINH_ID, text: e.QUYTRINH}))"
                                                :disabled="!checkQuyTrinh"
                                                @change="changeQuyTrinh"
                                                >
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Hướng giao</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_huonggiao">
                                            <select2 :settings="{ dropdownParent: $refs['ds_huonggiao'] }"
                                                v-model="huonggiao_selected" class="select2"
                                                :options="ds_huonggiao.map(e=> ({id: e.huonggiao_id, text: e.ten_ht}))"
                                                @change="changeHuongGiao"
                                                >
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Đơn vị nhận</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_donvinhan">
                                            <select2 :settings="{ dropdownParent: $refs['ds_donvinhan'] }"
                                                v-model="donvinhan_selected" class="select2"
                                                :options="ds_donvinhan.map(e=> ({id: e.donvi_id, text: e.ten_dv}))"
                                                @change="changeDonViNhan"
                                                >
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="box-move-select-table">
                            <div class="box-col">
                                <div class="legend-title">Nhân viên đã gán</div>
                                <div class="info-row">
                                    <div class="key w100">Kiểu nhận tin</div>
                                    <div class="value">
                                       <ejs-multiselect id='multiselect'
                                            :dataSource="ds_kieucb"
                                            placeholder="Chọn kiểu nhận tin"
                                            mode="CheckBox"
                                            v-model="kieucb_selected"
                                            :allowFiltering="true"
                                            locale='vi-VN'
                                            class="select-custom"
                                            :filtering="onFilteringKieuCB"
                                            :fields="{text: 'kieu_cb', value: 'kieucb_id' }"
                                            :showSelectAll="true"
                                            selectAllText="Chọn tất cả"
                                            unSelectAllText="Chọn tất cả"/>
                                    </div>
                                </div>
                                <KDataGrid
                                    :columns="columns"
                                    :dataSource="dataSourcesGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
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
                            <div class="box-col">
                                <div class="legend-title">Nhân viên chưa gán</div>
                                <KDataGrid
                                    :columns="columns"
                                    :dataSource="dataSourcesChuaGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
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
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import API from './API'
import DanhSachDonVi from './DanhSachDonVi.vue'
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb.vue'
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { L10n } from '@syncfusion/ej2-base'
import { DataManager,Query,ODataV4Adaptor,Predicate} from "@syncfusion/ej2-data"
L10n.load({
  'vi-VN': {
    'dropdowns': {
      'noRecordsTemplate': "Không có dữ liệu",
      'actionFailureTemplate': ""
    }
  }
})
export default {
    name:'SetStaffToReceiveMessages',
    props:{
        _kieu_id:{
            type:Number,
            default:0
        },
        _hg_id:{
            type:Number,
            default:0
        }
    },
    components:{
        KBreadCrumb,
        ActionTop,
        KDataGrid,
        DanhSachDonVi
    },
    provide: {
        multiselect: [CheckBoxSelection]
    },
    data(){
        return {
            actions:[
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-save',
                    visible:true
                },
            ],
            kieu_id:0,
            hg_id:0,
            columns:[
                {
                    fieldName:'ma_nv',
                    headerText:'Mã NV',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ten_nv',
                    headerText:'Tên NV',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'ten_dv',
                    headerText:'Đơn vị',
                    allowFiltering:true,
                    width:100
                },
                {
                    fieldName:'so_dt',
                    headerText:'Số ĐT',
                    allowFiltering:true,
                    width:100
                }
            ],
            dataSourcesGan:[],
            dataSourcesChuaGan:[],
            checkDichVuVT:false,
            ds_dichvuvt:[],
            dichvuvt_selected:null,
            checkLoaiHD:false,
            ds_loaihd:[],
            loaihd_selected:null,
            ds_nhomqt:[],
            nhomqt_selected:null,
            checkQuyTrinh:false,
            ds_quytrinh:[],
            quytrinh_selected:null,
            ds_huonggiao:[],
            huonggiao_selected:null,
            kt_load:true,
            donvi_nhan_id:0,
            donvi_id:0,
            huonggiao_id:0,
            ds_donvinhan:[],
            donvinhan_selected:null,
            loaidv_nhan_id:0,
            ds_kieucb:[],
            kieucb_selected:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='capnhat'){
                this.CapNhat()
            }
        },
        async initDuLieu(){
            this.kieu_id=this._kieu_id
            this.hg_id=this._hg_id

            this.ds_nhomqt=await this.lay_danhsach_nhom_quytrinh()
            if(this.ds_nhomqt.length>0){
                this.nhomqt_selected=this.ds_nhomqt[0].NHOM_QT_ID
            }
            this.ds_loaihd=await this.lay_danhsach_loai_hd()
            if(this.ds_loaihd.length>0){
                this.loaihd_selected=this.ds_loaihd[0].LOAIHD_ID
            }
            this.ds_dichvuvt=await this.lay_danhsach_dichvu()
            if(this.ds_dichvuvt.length>0){
                this.dichvuvt_selected=this.ds_dichvuvt[0].DICHVUVT_ID
            }
            // dsKieuCB = bangts.GetSQL(string.Format(@"select 0 chon,kieucb_id,kieu_cb from {0}.kieu_cb", DatabaseConstants.DB10), "");
            this.ds_kieucb=await this.lay_ds_kieucb()
            // HienThi_CheckBox_Combobox(cboKieuCB, dsKieuCB, "kieucb_id", "kieu_cb", "chon", "1");

            //trong sự kiện nhomqt change
            await this.HienThiDsQuyTrinh()
            await this.LAY_DS_HUONGGIAO()
            await this.HienThi_GiaoDien()

            //Load ds donvinhan
            if(this.ds_huonggiao.length>0&&this.huonggiao_selected){
                await this.changeHuongGiao()
            }

            await this.$refs.danhsachdonvi.initDulieu()
            //Không có dữ liệu cây đơn vị load default, nếu có trigger trong hàm tvDonViChanged
            if(this.$refs.danhsachdonvi.danhsach_donvi.length==0){
                await this.HienThi_NhanVien_ChuaGan()
                await this.HienThi_NhanVien_DaGan()
            }
            //init đơn vị cây-->load : HienThi_NhanVien_ChuaGan, HienThi_NhanVien_DaGan
            this.kt_load=false

        },
        async HienThiDsQuyTrinh(){
            this.quytrinh_selected=null
            let loaihd_id=(this.checkLoaiHD&&this.loaihd_selected)?this.loaihd_selected:0
            let dichvuvt_id=(this.checkDichVuVT&&this.dichvuvt_selected)?this.dichvuvt_selected:0
            let result=await this.sp_lay_ds_quytrinh_timkiem_quytrinh(this.nhomqt_selected, loaihd_id, dichvuvt_id, 1)
            if(result){
                if(result.bang&&result.bang.data_json){
                    this.ds_quytrinh=result.bang.data_json
                    if(this.ds_quytrinh.length>0){
                        this.quytrinh_selected=this.ds_quytrinh[0].QUYTRINH_ID
                    }
                }else{
                    this.ds_quytrinh=[]
                }
            }else{
                this.ds_quytrinh=[]
            }
        },
        async LAY_DS_HUONGGIAO(){
            //dùng chung api bên frmHuongGiao.cs
            this.huonggiao_selected=null
            let vquytrinh_id=(this.checkQuyTrinh&&this.quytrinh_selected)?this.quytrinh_selected:0
            let vloaihd_id=(this.checkLoaiHD&&this.loaihd_selected)?this.loaihd_selected:0
            let vdichvuvt_id=(this.checkDichVuVT&&this.dichvuvt_selected)?this.dichvuvt_selected:0
            this.ds_huonggiao=await this.lay_danhsach_huonggiao(this.nhomqt_selected, vquytrinh_id, vloaihd_id, vdichvuvt_id)
            if(this.ds_huonggiao.length>0){
                this.huonggiao_selected=this.ds_huonggiao[0].huonggiao_id
            }
            if(!this.kt_load){
                await this.changeHuongGiao()
            }
        },
        async changeNhomQT(){
            await this.HienThiDsQuyTrinh()
            await this.LAY_DS_HUONGGIAO()
        },
        async changeLoaiHD(){
            await this.HienThiDsQuyTrinh()
            await this.LAY_DS_HUONGGIAO()
        },
        async changeDichVuVT(){
            await this.HienThiDsQuyTrinh()
            await this.LAY_DS_HUONGGIAO()
        },
        async changeQuyTrinh(){
            await this.LAY_DS_HUONGGIAO()
        },
        async changeCheckLoaiHD(){
            await this.HienThiDsQuyTrinh()
            await this.LAY_DS_HUONGGIAO()
        },
        async changeCheckDichVuVT(){
            await this.HienThiDsQuyTrinh()
            await this.LAY_DS_HUONGGIAO()
        },
        async changeCheckQuyTrinh(){
            await this.LAY_DS_HUONGGIAO()
        },
        async changeHuongGiao(){
            this.huonggiao_id=this.huonggiao_selected?this.huonggiao_selected:0
            this.donvinhan_selected=null
            this.donvi_nhan_id=0
            //fn_tt_huonggiao_ldv
            this.loaidv_nhan_id=Number(await this.fn_tt_huonggiao_ldv(this.huonggiao_id, 2, '0'))
            if(this.loaidv_nhan_id!=-1){
                this.ds_donvinhan=await this.sp_ht_tatca_vetinh_combobox_v2(this.huonggiao_id, this.loaidv_nhan_id)
                if(this.ds_donvinhan.length>0){
                    this.donvi_nhan_id=this.ds_donvinhan[0].donvi_id
                    this.donvinhan_selected=this.ds_donvinhan[0].donvi_id
                }
                
            }else{
                this.ds_donvinhan=[]
            }
            if(!this.kt_load){
                await this.HienThi_NhanVien_ChuaGan()
                await this.HienThi_NhanVien_DaGan()
            }
        },
        async changeDonViNhan(){
            this.donvi_nhan_id=this.donvinhan_selected?this.donvinhan_selected:0
            await this.HienThi_NhanVien_ChuaGan()
            await this.HienThi_NhanVien_DaGan()
        },
        async tvDonViChanged(donvi){
            console.log('tvDonViChanged', donvi)
            this.donvi_id=donvi.donvi_id
            //
            await this.HienThi_NhanVien_ChuaGan()
            await this.HienThi_NhanVien_DaGan()
        },
        async HienThi_NhanVien_ChuaGan(){
            this.dataSourcesChuaGan=await this.lay_ds_nhanvien_nhantin({
                donvi_id: this.donvi_id,
                huonggiao_id: this.huonggiao_id,
                donvi_nhan_id: this.donvi_nhan_id,
                kieu: 0
            })
        },
        async HienThi_NhanVien_DaGan(){
            //lay_ds_nhanvien_nhantin
            this.dataSourcesGan=await this.lay_ds_nhanvien_nhantin({
                donvi_id: this.donvi_id,
                huonggiao_id: this.huonggiao_id,
                donvi_nhan_id: this.donvi_nhan_id,
                kieu: 1
            })
        },
        async CapNhat(){
            console.log('kieucb_selected', this.kieucb_selected)
            if(!this.kieucb_selected||this.kieucb_selected.length==0){
                this.$toast.error('Hãy chọn Kiểu nhận tin')
                return
            }

            let dskieu_cb=this.kieucb_selected.map(x=>{
                return {
                    KIEUCB_ID:x
                }
            })
            let ds_nhanvien=this.dataSourcesGan.map(x=>{
                return {
                    NHANVIEN_ID:x.nhanvien_id
                }
            })

            let data={
                huonggiao_id:this.huonggiao_id,
                donvi_id:this.donvi_nhan_id,
                ds_nhanvien:JSON.stringify(ds_nhanvien), 
                ds_kieucb:JSON.stringify(dskieu_cb)
            }

            let result=await this.capnhat_nhantin_nv(data)
            if(result){
                this.$toast.success('Cập nhật thành công!')
            }



        },
        async HienThi_GiaoDien(){
            if(this.kieu_id==1){
                this.huonggiao_selected=this.hg_id
                if(this.ds_huonggiao.length<=0){
                    this.$toast.error('Nhóm quyền của bạn chưa được phân hướng giao số '+this.hg_id+'. Liên hệ admin để được hỗ trợ.')
                    return
                }
                await this.HienThi_DanhSach()
            }
        },
        async HienThi_DanhSach(){
            //hàm comment không có nội dung
        },
        gan(){
            let dataCheckedsChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
            if(dataCheckedsChuaGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsChuaGan.length;i++){
                this.dataSourcesGan.push(dataCheckedsChuaGan[i])
                const index=this.dataSourcesChuaGan.findIndex(x=>x.nhanvien_id==dataCheckedsChuaGan[i].nhanvien_id)
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
        boGan(){
            let dataCheckedsGan=this.$refs.gridDaGan.getSelectedRecords()
            if(dataCheckedsGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsGan.length;i++){
                this.dataSourcesChuaGan.push(dataCheckedsGan[i])
                const index=this.dataSourcesGan.findIndex(x=>x.nhanvien_id==dataCheckedsGan[i].nhanvien_id)
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
        onFilteringKieuCB(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('kieu_cb', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.ds_kieucb, query);
        },
        async lay_danhsach_nhom_quytrinh(){
            try{
                this.loading(true)
                let response=await API.lay_danhsach_nhom_quytrinh(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.NHOM_QT_ID - b.NHOM_QT_ID
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_danhsach_loai_hd(){
            try{
                this.loading(true)
                let response=await API.lay_danhsach_loai_hd(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.LOAIHD_ID - b.LOAIHD_ID
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_danhsach_dichvu(){
            try{
                this.loading(true)
                let response=await API.lay_danhsach_dichvu(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.DICHVUVT_ID - b.DICHVUVT_ID
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_lay_ds_quytrinh_timkiem_quytrinh(nhom_qt_id, loaihd_id, dichvuvt_id, kt_load){
            try{
                this.loading(true)
                let response=await API.sp_lay_ds_quytrinh_timkiem_quytrinh(this.axios, nhom_qt_id, loaihd_id, dichvuvt_id, kt_load)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else {
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async lay_danhsach_huonggiao(nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id){
            try{
                this.loading(true)
                let response=await API.lay_danhsach_huonggiao(this.axios, nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort((a,b)=>{
                        return a.huonggiao_id-b.huonggiao_id
                    }).map(x=>{
                        x.ten_ht=x.huonggiao_id+' - '+x.huonggiao
                        return x
                    })
                }else {
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async fn_tt_huonggiao_ldv(huonggiao_id, type, defaultValue){
            try{
                this.loading(true)
                let response = await API.fn_tt_huonggiao_ldv(this.axios, huonggiao_id, type)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                this.loading(false)
                return defaultValue
            }
        },
        async sp_ht_tatca_vetinh_combobox_v2(huonggiao_id, loaidv_dich_id){
            try{
                this.loading(true)
                let response=await API.sp_ht_tatca_vetinh_combobox_v2(this.axios, huonggiao_id, loaidv_dich_id)
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
        async lay_ds_kieucb(){
            try{
                this.loading(true)
                let response=await API.lay_ds_kieucb(this.axios)
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
        async lay_ds_nhanvien_nhantin(data){
            try{
                this.loading(true)
                let response=await API.lay_ds_nhanvien_nhantin(this.axios, data)
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
        async capnhat_nhantin_nv(data){
            try{
                this.loading(true)
                let response = await API.capnhat_nhantin_nv(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return true
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
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
  width: 100% !important;
}

.select2-container--default .select2-selection--single {
  border-color: #E0E0E0;
  outline: none;
}

.select2-dropdown {
  border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
  font-family: "FontAwesome";
  border: 0px;
  top: 0px;
  height: 1.428rem;
  bottom: 0px;
  line-height: 1.428rem;
  left: 5px;
  margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
  content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
  border: 0px;
  outline: none;
}
</style>