<template>
  <b-modal
    ref="popupTaoLenhHuongGiao"
    size="huge"
    hide-header
    hide-header-close
    hide-footer
    no-close-on-backdrop
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"> <span class="icon one-notepad"></span> Tạo lệnh hướng giao</div>
        <div class="close -ap icon-close" @click="closeForm"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
          <div class="row box-form">
            <div class="col-sm-3 col-12">
                <div class="legend-title">Nhập tham số</div>
                <div class="info-row">
                    <div class="key">
                        <div class="check-action">
                            <input type="checkbox" v-model="checkDichVuVT" class="check">
                            <span class="name">Dịch vụ</span>
                        </div>
                    </div>
                    <div class="value">
                        <div class="select-custom">
                            <select v-model="dichvuvt_selected" :disabled="!checkDichVuVT" class="form-control">
                                <option v-for="item in ds_dichvu" :key="item.DICHVUVT_ID" :value="item.DICHVUVT_ID">
                                    {{item.TEN_DVVT}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">
                        <div class="check-action">
                            <input type="checkbox" v-model="checkLoaiHD" class="check">
                            <span class="name">Loại HD</span>
                        </div>
                    </div>
                    <div class="value">
                        <div class="select-custom">
                            <select v-model="loaihd_selected" :disabled="!checkLoaiHD" class="form-control">
                                <option v-for="item in ds_loaihd" :key="item.LOAIHD_ID" :value="item.LOAIHD_ID">
                                    {{item.TEN_LOAIHD}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">
                        User giao
                    </div>
                    <!-- Đẩy lại cái dòng dưới -->
                    <div class="value">
                        <input type="text"  :value="input_user_giao"  @change="e=>input_user_giao=e.target.value" @keyup.enter="handleBlur" class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">
                        User ID
                    </div>
                    <div class="value">
                        <input type="text" :value="input_user_id"  @keypress="isNumber($event)" @change="e=>input_user_id=e.target.value" class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">
                        N.viên ID
                    </div>
                    <div class="value">
                        <input type="text" :value="input_nhanvien_id"  @keypress="isNumber($event)" @change="e=>input_nhanvien_id=e.target.value"  class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">
                        {{lableMaGDorMaTB}}
                    </div>
                    <div class="value">
                        <input type="text" :value="input_magd_matb" @change="e=>input_magd_matb=e.target.value"  class="form-control">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">
                        Đơn vị ID
                    </div>
                    <div class="value">
                        <input type="text" :value="input_donvi_id"  @keypress="isNumber($event)" @change="e=>input_donvi_id=e.target.value"  class="form-control">
                    </div>
                </div>
                <div class="legend-title">Đơn vị giao</div>
                <div class="tree-plus box-form" style="height: 480px;overflow: auto;">
                    <ejs-treeview  id='treeview'  ref="treeObj" 
                        @nodeClicked="nodeClicked"
                        :fields="danhmuc_donvi_tree" 
                        @nodeSelected="tvDonVi_FocusedNodeChanged" 
                        :allowMultiSelection='true'
                        :selectedNodes="selectedNodes"
                        :expandedNodes="expandedNodes"/>
                </div>

            </div>

            <div class="col-sm-9 col-12">
                <div class="info-row">
                    <div class="legend-title">
                        <span class="title red">{{quytrinh}}</span>
                    </div>
                    <textarea class="form-control" :value="input_thongtin_quytrinh" @change="e=>input_thongtin_quytrinh=e.target.value" style="height: 100px;resize: vertical;"></textarea>
                </div>
                
                <div class="info-row">
                    <div class="legend-title">
                        <span class="title red">{{tenhuong}}</span>
                    </div>
                    <textarea class="form-control" :value="input_dauvao" @change="e=>input_dauvao=e.target.value"  style="height: 250px;resize: vertical;"></textarea>
                </div>
                <div class="info-row">
                    <div class="legend-title">
                        <span class="title">Kết quả</span>
                    </div>
                    <textarea class="form-control" :value="input_ketqua" @change="e=>input_ketqua=e.target.value" style="height: 100px;resize: vertical;"></textarea>
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        Danh sách hợp đồng thuê bao chưa giao
                    </div>
                    <DataGrid
                        :columns="columns"
                        :dataSource="dataSources"
                        :showColumnCheckbox="false"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"/>
                </div>
            </div>
          </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './IntersectionDirectionAPI'
import moment from 'moment'
export default {
    name:'TaoLenhHuongGiaoModal',
    props:{
        huonggiao_id:{
            type:[Number, String],
            default:0
        },
        dichvuvt_id:{
            type:Number,
            default:0
        },
        tenhuong:{
            type:String,
            default:''
        },
        quytrinh:{
            type:String,
            default:''
        },
        nhom_qt_id:{
            type:[Number, String],
            default:0
        },
        phanvung_id:{
            type:[Number, String],
            default:0
        },
    },
    components:{
        KTableV3, ActionTop
    },
    computed:{
        lableMaGDorMaTB(){
            //NHOM_QT.GIAIQUYET_KN=3
            return this.nhom_qt_id==3?'Mã TB':'Mã GD'
        },
        danhmuc_donvi_tree(){
            let donvi_tree=this.createTreeDonVi(this.ds_donvi)
            return { dataSource: donvi_tree, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild' }
        }
    },
    data(){
        return{
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'sinhsql',
                    name:'Sinh SQL',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'run',
                    name:'Run',
                    active: true,
                    icon_class:'one-reload1'
                }
            ],
            columns:[
                {
                    fieldName: "TT",
                    headerText: "TT",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "CHON",
                    headerText: "CHON",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "MA_GD",
                    headerText: "MA_GD",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "HDTB_ID",
                    headerText: "HDTB_ID",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "MA_TB",
                    headerText: "MA_TB",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "TEN_TB",
                    headerText: "TEN_TB",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "DIACHI_TB",
                    headerText: "DIACHI_TB",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "HDKH_ID",
                    headerText: "HDKH_ID",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "ND_TRA_CON",
                    headerText: "ND_TRA_CON",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "DIACHI_LD",
                    headerText: "DIACHI_LD",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "DICHVUVT_ID",
                    headerText: "DICHVUVT_ID",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "PHIEU_ID",
                    headerText: "PHIEU_ID",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "TTPH_ID",
                    headerText: "TTPH_ID",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "DONVINHAN",
                    headerText: "DONVINHAN",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "DONVINHAN_ID",
                    headerText: "DONVINHAN_ID",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "ND_GIAO",
                    headerText: "ND_GIAO",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "TTHD_ID",
                    headerText: "TTHD_ID",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "SOPHIEU",
                    headerText: "SOPHIEU",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "NGAYGIAO",
                    headerText: "NGAYGIAO",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "TTYC",
                    headerText: "TTYC",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
                {
                    fieldName: "TEN_TTYC",
                    headerText: "TEN_TTYC",
                    allowFiltering: true,
                    allowSorting:true,
                    width:80
                },
            ],
            dataSources:[],
            ds_dichvu:[],
            dichvuvt_selected:null,
            ds_loaihd:[],
            loaihd_selected:null,
            checkDichVuVT:false,
            checkLoaiHD:false,
            input_thongtin_quytrinh:'',
            input_dauvao:'',
            ds_donvi:[],

            selectedNodes:[],
            expandedNodes:[],
            donvi_selected:null,
            input_user_giao:'',
            input_user_id:'',
            input_nhanvien_id:'',
            input_magd_matb:'',
            input_donvi_id:'',
            nhanvien_id:0,
            nguoidung_id:0,
            donvi_id:0,
            input_ketqua:'',

            //
            thongtin_quytrinh_old:'',
            dauvao_old:''
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
                this.capNhat()
            }else if(action.id=='sinhsql'){
                this.sinhSQL()
            }else if(action.id=='run'){
                this.runSQL()
            }
        },
        handleShowModal(){
            this.dataSources=[]
            this.ds_dichvu=[]
            this.dichvuvt_selected=null
            this.ds_loaihd=[]
            this.loaihd_selected=null
            this.checkDichVuVT=false
            this.checkLoaiHD=false
            this.input_thongtin_quytrinh=''
            this.input_dauvao=''
            this.ds_donvi=[]
            this.donvi_selected=null
            this.input_user_giao=''
            this.input_user_id=''
            this.input_nhanvien_id=''
            this.input_magd_matb=''
            this.input_donvi_id=''
            this.nhanvien_id=0
            this.nguoidung_id=0
            this.donvi_id=0
            this.input_ketqua=''
            this.thongtin_quytrinh_old=''
            this.dauvao_old=''
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        showModal() {
            this.$refs["popupTaoLenhHuongGiao"].show()
        },
        hideModal() {
            this.$refs["popupTaoLenhHuongGiao"].hide()
        },
        closeForm(){
            this.hideModal()
            // if(this.thongtin_quytrinh_old==this.input_thongtin_quytrinh.trim()&&this.dauvao_old==this.input_dauvao.trim()){
            //     this.hideModal()
            //     return
            // }
            // this.$confirm('Bạn có muốn ghi lại không ?','Thông báo',{
            //     confirmButtonText: 'Đồng ý',
            //     cancelButtonText: 'Thoát'
            // }).then(async () => {
            //     await this.capNhat()
            //     this.hideModal()
            // }).catch(()=> {
            //     this.hideModal()
            // })
        },
        async lay_danhsach_dichvu(){
            try{
                this.loading(true)
                let response = await API.lay_danhsach_dichvu(this.axios)
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
        async lay_danhsach_loai_hd(){
            try{
                this.loading(true)
                let response = await API.lay_danhsach_loai_hd(this.axios)
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
        async fn_quytrinh_get_command_by_huonggiao_id(huonggiao_id){
            try{
                this.loading(true)
                let response = await API.fn_quytrinh_get_command_by_huonggiao_id(this.axios, huonggiao_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async fn_huonggiao_get_command(huonggiao_id){
            try{
                this.loading(true)
                let response = await API.fn_huonggiao_get_command(this.axios, huonggiao_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async sp_upd_huonggiao_command(data){
            try{
                this.loading(true)
                let response = await API.sp_upd_huonggiao_command(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return {
                        code:1,
                        message:'Lưu dữ liệu thành công.'
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return {
                            code:0,
                            message:response.data.message
                        }
                    }else{
                        return {
                            code:0,
                            message:'Đã xảy ra lỗi'
                        }
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return {
                        code:0,
                        message:e.data.message
                    }
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return {
                        code:0,
                        message:e.response.data.message
                    }
                }else{
                    return {
                        code:0,
                        message:'Đã xảy ra lỗi'
                    }
                }
            }
        },
        async sp_giaosim_get_treeview(donvicha_id){
            try{
                this.loading(true)
                let response = await API.sp_giaosim_get_treeview(this.axios, donvicha_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{
                        if(x.unitlevel==0){
                            return Object.assign(x,{
                                donvi_cha_id:null
                            })
                        }else{
                            return x
                        }
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_nhanvien_id_theo_mand(ma_nd, defaultValue){
            try{
                let response = await API.lay_nhanvien_id_theo_mand(this.axios, ma_nd)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async lay_nguoidung_id_theo_mand(ma_nd, defaultValue){
            try{
                let response = await API.lay_nguoidung_id_theo_mand(this.axios, ma_nd)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async lay_donvi_id_theo_nhanvien_id(nhanvien_id, defaultValue){
            try{
                let response = await API.lay_donvi_id_theo_nhanvien_id(this.axios, nhanvien_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async sp_huonggiao_runsql(data){
            try{
                this.loading(true)
                let response = await API.sp_huonggiao_runsql(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi')
                    }
                    return null
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi')
                }
                return null
            }
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
        async handleBlur(){
            //input_user_giao
            if(this.input_user_giao.trim()==''){
                return
            }
            this.loading(true)

            this.nhanvien_id=Number(await this.lay_nhanvien_id_theo_mand(this.input_user_giao.trim().toLowerCase(), '0'))

            this.nguoidung_id=Number(await this.lay_nguoidung_id_theo_mand(this.input_user_giao.trim().toLowerCase(), '0'))
            this.input_user_id=this.nguoidung_id.toString()
            this.input_nhanvien_id=this.nhanvien_id.toString()
            this.donvi_id=Number(await this.lay_donvi_id_theo_nhanvien_id(this.nhanvien_id, '0'))
            this.loading(false)
            this.input_donvi_id=this.donvi_id.toString()

            let donvi=this.ds_donvi.find(x=>x.donvi_id==this.donvi_id)
            if(donvi!=undefined){
                this.FindByText(donvi)
            }
        },
        FindByText(donvi){
            //selected node focus
            this.selectedNodes=[donvi.donvi_id]
            //find array parent expandedNodes
            this.expandedNodes=[0]
            this.expandedNodes.push(donvi.donvi_id)
            var donvi_id=donvi.donvi_cha_id
            while(donvi_id!=null&&donvi_id!=-1){
                const index=this.ds_donvi.findIndex(x=>x.donvi_id==donvi_id)
                if(index>-1){
                    this.expandedNodes.push(donvi_id)
                    donvi_id=this.ds_donvi[index].donvi_cha_id
                }else{
                    donvi_id=-1
                }
            }
        },
        sinhSQL(){
            var  loaihd_id=0
            if(this.checkLoaiHD){
                loaihd_id=this.loaihd_selected!=null?this.loaihd_selected:0
            }
            var dichvuvt_id = 0
            if(this.checkDichVuVT){
                dichvuvt_id=this.dichvuvt_selected!=null?this.dichvuvt_selected:0
            }
            var ma_gd='0'
            if(this.input_magd_matb.trim()!=''){
                ma_gd=this.input_magd_matb.trim()
            }
            let donvi_id=Number(this.input_donvi_id.trim()!=''?this.input_donvi_id.trim():'0')
            var sql=this.input_dauvao.trim()
            // # db1: admin
            // # db2: css
            // # db3: qltn
            // # db4: tinhcuoc
            sql=sql.replaceAll('{?DB3}', 'qltn')
            sql=sql.replaceAll('{?DB2}', 'css')
            sql=sql.replaceAll('{?DB1}', 'admin')

            sql=sql.replaceAll(':huonggiao_id', this.huonggiao_id.toString())
            sql=sql.replaceAll(':dichvuvt_id', dichvuvt_id.toString())
            sql=sql.replaceAll(':nhanvien_id', this.nhanvien_id.toString())
            sql=sql.replaceAll(':nguoidung_id', this.nguoidung_id.toString())
            sql=sql.replaceAll(':loaihd_id', loaihd_id.toString())
            sql=sql.replaceAll(':donvi_id', donvi_id.toString())
            sql=sql.replaceAll(':ma_gd', ma_gd)

            //Bổ sung thêm replace phân vùng
            //sql=sql.replaceAll(':v_phanvung_id', this.$root.token.getPhanVungID())
            sql=sql.replaceAll(':v_phanvung_id', this.phanvung_id)

            // dùng cho khiếu nại
            //NHOM_QT.GIAIQUYET_KN
            if(this.nhom_qt_id==3){
                //     if (!cm.KiemTra_ThamSo_Registry("kyhoadon", " Kỳ hóa đơn"))
                //     {
                //         this.BeginInvoke(new MethodInvoker(this.Close));
                //         return;
                //     }
                //     if (!cm.KiemTra_ThamSo_Registry("chuky", " Chu kỳ"))
                //     {
                //         this.BeginInvoke(new MethodInvoker(this.Close));
                //         return;
                //     }

                //     kyhoadon = cm.RegRead("kyhoadon", "");
                //     kyhoadon = Convert.ToDateTime(kyhoadon).ToString("yyyyMM") + cm.RegRead("chuky", "");
                //     sql = sql.Replace(":ma_tb", ma_gd);
                //     sql = sql.Replace("{?DB4}", DatabaseConstants.DB4);
                //     sql = sql.Replace(":kyhoadon", kyhoadon);
                let kyhoadon=moment(new Date()).subtract(1,'M').format('YYYYMM')+'01'
                sql=sql.replaceAll(':ma_tb', ma_gd)
                sql=sql.replaceAll('{?DB4}', 'tinhcuoc')
                sql=sql.replaceAll(':kyhoadon', kyhoadon)

            }
            this.input_ketqua=sql
            this.runSQL()
        },
        async runSQL(){
            if(this.input_ketqua.trim()==''){
                return
            }
            if(this.input_nhanvien_id.trim()==''){
                this.$toast.error('Chưa nhập nhân viên')
                return
            }
            var  loaihd_id=0
            if(this.checkLoaiHD){
                loaihd_id=this.loaihd_selected!=null?this.loaihd_selected:0
            }
            var dichvuvt_id = 0
            if(this.checkDichVuVT){
                dichvuvt_id=this.dichvuvt_selected!=null?this.dichvuvt_selected:0
            }
            var ma_gd='0'
            if(this.input_magd_matb.trim()!=''){
                ma_gd=this.input_magd_matb.trim()
            }
            let donvi_id=Number(this.input_donvi_id.trim()!=''?this.input_donvi_id.trim():'0')

            let result = await this.sp_huonggiao_runsql({
                phanvung_id: this.phanvung_id,
                dichvuvt_id: dichvuvt_id,
                loaihd_id: loaihd_id,
                huonggiao_id: this.huonggiao_id,
                donvi_id: donvi_id,
                ma_gd: ma_gd,
                nhanvien_id: this.nhanvien_id
            })
            if(result){
                this.$toast.success('Thực thi câu lệnh SQL thành công')
                if(result.columns&&result.columns.length>0){
                    var cols=result.columns.map(x=>{
                        return {
                            fieldName: x.column_name,
                            headerText: x.column_label,
                            allowFiltering: true,
                            allowSorting:true,
                            width:80
                        }
                    })
                    this.columns=[].concat(cols)
                }
                if(result.data_json){
                    this.dataSources=[].concat(result.data_json)
                }
            }
            console.log('result', result)

        },
        async capNhat(){
            this.$toast.error('Tính năng không còn khả dụng, chuyển qua sử dụng SQL Tool')
            // let result = await this.sp_upd_huonggiao_command({
            //     huonggiao_id: this.huonggiao_id,
            //     dauvao: this.input_dauvao.trim(),
            //     quytrinh: this.input_thongtin_quytrinh.trim()
            // })
            // if(result.code==1){
            //     this.thongtin_quytrinh_old=this.input_thongtin_quytrinh.trim()
            //     this.dauvao_old=this.input_dauvao.trim()
            //     this.$toast.success(result.message)
            // }else{
            //     this.$toast.error(result.message)
            // }
        },
        createTreeDonVi(dsDonVi){
            //
            let tree = []
            let donviChaChaArray = dsDonVi.map((item) => {
                return item['donvi_cha_id']
            })
            let donviArray = dsDonVi.map(item => (item['donvi_id']))
            dsDonVi.forEach(item => {
                //check item la parent node
                if(donviChaChaArray.includes(item['donvi_id'])){
                    //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
                    if(!donviArray.includes(item['donvi_cha_id'])){
                        delete item.donvi_cha_id
                    }
                    tree.push( {...item, hasChild:true, expanded: false})
                }else{
                    tree.push(item)
                }
            })
            //console.log('tree', tree)
            return tree
        },
        nodeClicked(args){
            //console.log('nodeClicked',args.node.dataset.uid)
            //this.vattu_cha_selected= Number(args.node.dataset.uid)
        },
        tvDonVi_FocusedNodeChanged(node){
            let donvi_id=Number(node.nodeData.id)
            let donvi=this.ds_donvi.find(x=>x.donvi_id==donvi_id)
            if(donvi!=undefined){
                this.donvi_selected=donvi
                this.input_donvi_id=this.donvi_selected.donvi_id.toString()
            }else{
                this.donvi_selected=null
            }
        },

        async initDuLieu(){
            
            this.ds_dichvu=await this.lay_danhsach_dichvu()
            if(this.ds_dichvu.length>0){
                this.dichvuvt_selected=1
            }
            this.ds_loaihd=await this.lay_danhsach_loai_hd()
            if(this.ds_loaihd.length>0){
                this.loaihd_selected=1
            }

            this.thongtin_quytrinh_old=await this.fn_quytrinh_get_command_by_huonggiao_id(this.huonggiao_id)
            this.dauvao_old=await this.fn_huonggiao_get_command(this.huonggiao_id)
            this.input_thongtin_quytrinh=this.thongtin_quytrinh_old
            this.input_dauvao=this.dauvao_old

            this.ds_donvi=await this.sp_giaosim_get_treeview(0)
            setTimeout(()=>{
                this.expandedNodes=[0]
            },500)

        },
    }

}
</script>
<style>
@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
@media (min-width: 576px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
</style>