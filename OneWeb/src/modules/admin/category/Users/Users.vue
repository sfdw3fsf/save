<template>
    <div class="main-wrapper">
        <KBreadCrumb title="Cập nhật thông tin người dùng" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="grid-stack-box">
                <Split>
                    <SplitArea :size="25" :minSize="100">
                        <DanhSachDonVi ref="danhsachdonvi" @tvDonViChanged="tvDonViChanged" @clearThongTin="clearThongTin"/>
                    </SplitArea>
                    <SplitArea :size="75" :minSize="300">
                        <div class="box-form">
                            <div class="legend-title">
                                <span @click="clickShowHideTT" class="fa icon" :class="{'fa-angle-up':expadedThongTin, 'fa-angle-down':!expadedThongTin,}"></span>
                                Thông tin người dùng
                            </div>
                            <transition name="slide">
                                <div class="row" v-show="expadedThongTin">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w100">Nhóm ND</div>
                                            <div class="value">
                                                <div class="select-custom" ref="ds_nhomnd">
                                                    <select2 :settings="{ dropdownParent: $refs['ds_nhomnd'] }" ref="cboNhomND"
                                                        v-model="nhomnd_selected" class="select2"
                                                        :options="danhsach_nhomnd.map(e=> ({id: e.NHOM_ND_ID, text: e.TEN_NHOM}))">
                                                    </select2>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w100">Tên đăng nhập</div>
                                            <div class="value">
                                                <input type="text" ref="inputTenDangNhap" @keyup.enter="onEnterTenDN" :value="thongtinnguoidung.ten_dangnhap" @change="e=>thongtinnguoidung.ten_dangnhap=e.target.value" class="form-control highlight">
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w100">Tên đầy đủ</div>
                                            <div class="value">
                                                <input type="text" ref="inputTenDayDu" :value="thongtinnguoidung.ten_daydu" @change="e=>thongtinnguoidung.ten_daydu=e.target.value" class="form-control">
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w100">Nhân viên</div>
                                            <div class="value">
                                                <div class="select-custom" ref="ds_nhanvien">
                                                    <select2 :settings="{ dropdownParent: $refs['ds_nhanvien'] }" ref="cboNhanVien"
                                                        v-model="nhanvien_selected" class="select2"
                                                        :options="danhsach_nhanvien.map(e=> ({id: e.nhanvien_id, text: e.ten_ht}))"
                                                        @change="changeNhanVien"
                                                    >
                                                    </select2>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w100">Tỉnh/TP</div>
                                            <div class="value w20 padt7">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="thongtinnguoidung.check_tinh" class="check" >
                                                    <span class="name"></span>
                                                </div>
                                            </div>
                                            <div class="value">
                                                <div class="select-custom" ref="ds_tinh">
                                                    <select2 :settings="{ dropdownParent: $refs['ds_tinh'] }" ref="cboTinh"
                                                        v-model="tinh_selected" class="select2"
                                                        :options="danhsach_tinh.map(e=> ({id: e.TINH_ID, text: e.TENTINH}))"
                                                        @change="changeTinh"
                                                        :disabled="!thongtinnguoidung.check_tinh"
                                                    >
                                                    </select2>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w100">Quận</div>
                                            <div class="value">
                                                <div class="select-custom" ref="ds_quan">
                                                    <select2 :settings="{ dropdownParent: $refs['ds_quan'] }" ref="cboQuan"
                                                        v-model="quan_selected" class="select2"
                                                        :options="danhsach_quan.map(e=> ({id: e.ID, text: e.NAME}))"
                                                        :disabled="!thongtinnguoidung.check_tinh"
                                                    >
                                                    </select2>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w100">User xem BC</div>

                                            <div class="value">
                                                <input type="text" :value="thongtinnguoidung.user_bc" @change="e=>thongtinnguoidung.user_bc=e.target.value" class="form-control">
                                            </div>
                                            <div class="value nowrap w30 padt7">
                                                <div class="list-checks">
                                                    <div class="item">
                                                        <div class="check-action">
                                                            <input type="checkbox" v-model="thongtinnguoidung.check_quantri" class="check">
                                                            <span class="name">Quản trị</span>
                                                        </div>
                                                    </div>
                                                    <div class="item">
                                                        <div class="check-action">
                                                            <input type="checkbox" v-model="thongtinnguoidung.check_enable" class="check">
                                                            <span class="name">Enable</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w90">User NEO</div>
                                            <div class="value">
                                                <div class="input-icon-right">
                                                    <input type="text" readonly :value="thongtinnguoidung.user_neo" @change="e=>thongtinnguoidung.user_neo=e.target.value" class="form-control highlight">
                                                    <!-- <span class="icon -ap icon-more_hori"></span> -->
                                                    <span @click="openPoupUserNEO" class="icon -ap icon-more_horiz"></span>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="row">
                                            <div class="col-sm-6 col-12">
                                                <div class="info-row">
                                                    <div class="key w90">User HDDT</div>
                                                    <div class="value">
                                                        <input type="text" :value="thongtinnguoidung.user_hddt" @change="e=>thongtinnguoidung.user_hddt=e.target.value" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-6 col-12">
                                                <div class="info-row">
                                                    <div class="key w90">Pass HDDT</div>
                                                    <div class="value">
                                                        <input type="password" :value="thongtinnguoidung.pass_hddt" @change="e=>thongtinnguoidung.pass_hddt=e.target.value" class="form-control ">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-12 col-12">
                                                <div class="info-row">
                                                    <div class="key w90">Điểm giao dịch VASC</div>
                                                    <div class="value">
                                                        <input type="text" :value="thongtinnguoidung.user_vasc1" @change="e=>thongtinnguoidung.user_vasc1=e.target.value" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-6 col-12">

                                                <!-- <div class="info-row">
                                                    <div class="key w90">User VASC</div>
                                                    <div class="value">
                                                        <div class="row">
                                                            <div class="col-sm-7 col-12">
                                                                <input type="text" :value="thongtinnguoidung.user_vasc" @change="e=>thongtinnguoidung.user_vasc=e.target.value" class="form-control">
                                                            </div>
                                                            <div class="col-sm-5 col-12">
                                                                <input type="text" :value="thongtinnguoidung.user_vasc1" @change="e=>thongtinnguoidung.user_vasc1=e.target.value" class="form-control">
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div> -->
                                            </div>
                                            <div class="col-sm-6 col-12">
                                                <!-- <div class="info-row">
                                                    <div class="key w90">Pass VASC</div>
                                                    <div class="value">
                                                        <input type="password" :value="thongtinnguoidung.pass_vasc" @change="e=>thongtinnguoidung.pass_vasc=e.target.value" class="form-control">
                                                    </div>
                                                </div> -->
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-6 col-12">
                                                <!-- <div class="info-row">
                                                    <div class="key w90">User VISA</div>
                                                    <div class="value">
                                                        <input type="text" :value="thongtinnguoidung.user_visa" @change="e=>thongtinnguoidung.user_visa=e.target.value" class="form-control">
                                                    </div>
                                                </div> -->
                                            </div>
                                            <div class="col-sm-6 col-12">
                                                <!-- <div class="info-row">
                                                    <div class="key w90">Pass VISA</div>
                                                    <div class="value">
                                                        <input type="password" :value="thongtinnguoidung.pass_visa" @change="e=>thongtinnguoidung.pass_visa=e.target.value" class="form-control">
                                                    </div>
                                                </div> -->
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-6 col-12">
                                                <div class="info-row">
                                                    <div class="key w90">User VTN</div>
                                                    <div class="value">
                                                        <input type="text" :value="thongtinnguoidung.user_vtn" @change="e=>thongtinnguoidung.user_vtn=e.target.value" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-6 col-12">
                                                <div class="info-row">
                                                    <div class="key w90">Pass VTN</div>
                                                    <div class="value">
                                                        <input type="password" :value="thongtinnguoidung.pass_vtn" @change="e=>thongtinnguoidung.pass_vtn=e.target.value" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-6 col-12">
                                                <div class="info-row">
                                                    <div class="key w90">User CAdmin</div>
                                                    <div class="value">
                                                        <input type="text" :value="thongtinnguoidung.user_cadmin" @change="e=>thongtinnguoidung.user_cadmin=e.target.value" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-6 col-12">
                                                <div class="info-row">
                                                    <div class="key w90">Pass CAdmin</div>
                                                    <div class="value">
                                                        <input type="password" :value="thongtinnguoidung.pass_cadmin" @change="e=>thongtinnguoidung.pass_cadmin=e.target.value" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w90">Ghi chú</div>
                                            <div class="value">
                                                <input type="text" :value="thongtinnguoidung.ghichu" @change="e=>thongtinnguoidung.ghichu=e.target.value" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </transition>
                        </div>
                        <ThuNgan ref="thuNgan" @expadedThuNgan="expadedThuNgan"/>
                        <div class="box-form">
                            <div class="legend-title">
                                <span @click="clickShowHideDsND" class="fa icon" :class="{'fa-angle-up':expadedDsNguoiDung, 'fa-angle-down':!expadedDsNguoiDung,}"></span>
                                Danh sách người dùng
                            </div>
                            <transition name="slide">
                                <KTableV3
                                    :columns="columns"
                                    :dataSources="dataSources"
                                    v-show="expadedDsNguoiDung"
                                    @onSelectedRow="onSelectedRow"
                                    id="nguoidung_id"                                    
                                    :rowSelectedId="rowSelectedId"
                                    :allowFilter="true"
                                    :tableHeight="tableHeight"/>
                            </transition>

                        </div>
                    </SplitArea>
                </Split>
                <!-- <div class="box-col" style="width: calc(25% - 8px);">
                    <DanhSachDonVi ref="danhsachdonvi" @tvDonViChanged="tvDonViChanged" @clearThongTin="clearThongTin"/>
                </div>
                <div class="gutter gutter-horizontal" style="width: 16px;"></div> -->
                <!-- <div class="box-col" style="width: calc(75% - 8px);">

                </div> -->
            </div>
        </div>
        <!-- Modal -->
        <AssignCollectDebtStaffModal ref="assignCollectDebtStaffModal" :p_nguoidung_id="Number(nguoidung_id)" :p_ma_nd="thongtinnguoidung.ten_dangnhap"/>
        <GanNguoiDungModal ref="ganNguoiDungModal" :danhsach="danhsach_nguoidung_vnp" :nguoidung_id="nguoidung_id" @onAccept="onAcceptFormUserNeo"/>
        <NguoiDungIPCCModal ref="nguoiDungIPCCModal" :p_nguoidung_id="Number(nguoidung_id)" :p_ma_nd="thongtinnguoidung.ten_dangnhap" :p_ten_nd="thongtinnguoidung.ten_daydu" />
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import DanhSachDonVi from './DanhSachDonVi.vue'
import AssignCollectDebtStaffModal from '../AssignCollectDebtStaff/AssignCollectDebtStaffModal.vue'
import UserAPI from './UserAPI'
import ThuNgan from './ThuNgan.vue'
import { mapActions } from "vuex"
import GanNguoiDungModal from './GanNguoiDungModal.vue'
import moment from 'moment'
import NguoiDungIPCCModal from './NguoiDungIPCCModal.vue'
export default {
    name:'Users',
    components:{
        Footer,
        KBreadCrumb,
        ActionTop,
        KTableV3,
        DanhSachDonVi,
        ThuNgan,
        AssignCollectDebtStaffModal,
        GanNguoiDungModal,
        NguoiDungIPCCModal
    },
    computed:{
        tableHeight(){
            if(this.dataSources.length==0){
                return 'auto'
            }
            var height=200
            if(!this.expadedThuNgan1){
                height+=200
            }
            return height+'px'
        }
    },
    data(){
        return {
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus',
                    visible:true
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save',
                    visible:true
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove',
                    visible:true
                },
                // {
                //     id:'xoa',
                //     name:'Xoá',
                //     active: true,
                //     icon_class:'one-trash'
                // },
                {
                    id:'reset_mk',
                    name:'Reset MK',
                    active: true,
                    icon_class:'one-sync',
                    visible:true
                },
                {
                    id:'nvtc',
                    name:'NVTC',
                    active: true,
                    icon_class:'one-sitemap-user',
                    visible:true
                },
                {
                    id:'nd_ipcc',
                    name:'User IPCC',
                    active: true,
                    icon_class:'icon one-folder-add',
                    visible:true
                },
                {
                    id:'dangnhap',
                    name:'Đăng nhập',
                    active: true,
                    icon_class:'nc-icon-glyph users_single-02',
                    visible:true
                },
            ],
            danhsach_nhomnd:[],
            nhomnd_selected:null,
            danhsach_tinh:[],
            tinh_selected:null,
            danhsach_quan:[],
            quan_selected:null,
            thongtinnguoidung:{
                ten_dangnhap:'',
                ten_daydu:'',
                check_tinh:false,
                user_bc:'',
                check_quantri:false,
                check_enable:false,
                user_neo:'',
                user_hddt:'',
                pass_hddt:'',
                user_vasc:'',
                user_vasc1:'',
                pass_vasc:'',
                user_visa:'',
                pass_visa:'',
                user_vtn:'',
                pass_vtn:'',
                user_cadmin:'',
                pass_cadmin:'',
                ghichu:''
            },
            columns:[
                {
                    field:'ma_nd',
                    label:'Mã người dùng',
                    allowFilter:true
                },
                {
                    field:'ma_nv',
                    label:'Mã nhân viên',
                    allowFilter:true
                },
                 {
                    field:'ma_dv',
                    label:'Mã đơn vị',
                    allowFilter:true
                },
                {
                    field:'ten_nd',
                    label:'Tên người dùng',
                    allowFilter:true
                },
                {
                    field:'ten_nhom',
                    label:'Nhóm người dùng',
                    allowFilter:true
                }
            ],
            dataSources:[],
            nguoidung_selected:null,
            danhsach_nhanvien:[],
            nhanvien_selected:null,
            DEFAULT_PASSWORD:'',
            donviFocus:null,
            danhsach_nguoidung_vnp:[],
            rowSelectedId:-1,
            expadedThongTin:true,
            expadedDsNguoiDung:true,
            nguoidung_id:0,
            nhanvien_id:0,
            donvi_id:0,
            login_btn:false,
            expadedThuNgan1:true,
            loadLoiVNP:false
        }
    },
    methods:{
        ...mapActions("user", ["clearCurrentUser"]),
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                if(this.nguoidung_selected!=null){
                    this.onSelectedRow(this.nguoidung_selected)
                }else{
                    this.setActiveActions(0)
                }
            }else if(action.id=='reset_mk'){
                this.clickResetMK()
            }else if(action.id=='nvtc'){
                if(this.nguoidung_id<=0){
                    this.$toast.error('Hãy tìm kiếm người dùng trước khi gán NVTC')
                    return
                }
                // frmGanNVTC nvtc = new frmGanNVTC();
                // nvtc.nguoidung_id = nguoidung_id;
                // nvtc.ma_nd = txtTenDN.Text.Trim();
                this.$refs.assignCollectDebtStaffModal.showModal()
            }
            else if(action.id=='nd_ipcc'){
                console.log( this.$refs.nguoiDungIPCCModal)                
                this.$refs['nguoiDungIPCCModal'].showModal()                 
            }
            else if(action.id=='dangnhap'){
                this.dangNhapNhanClick()
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                if(item.id!='nvtc'&&item.id!='dangnhap'&&item.id!='reset_mk'&&item.id!='nd_ipcc'){
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
                //clear userneo thêm mới
                this.hienthivnp(0)
                // txtMatKhau.Text = CustomEncryption.Encrypt(LOGIN_PARAM.DEFAULT_PASSWORD);
                // txtMatKhauNL.Text = CustomEncryption.Encrypt(LOGIN_PARAM.DEFAULT_PASSWORD);

            }else if(kieu==2){//Huỷ
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                // this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                this.clear()
            }else if(kieu==3){//edit
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                //this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
            // else if(kieu==4){//edit
            // this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
            //     this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            //     this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            //     //this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            // }
        },
        async clear(){
            this.thongtinnguoidung.ten_daydu=''
            this.thongtinnguoidung.ghichu=''
            // txtMatKhau.Text = "";
            // txtMatKhauNL.Text = "";
            this.thongtinnguoidung.ten_dangnhap=''
            this.thongtinnguoidung.user_neo=''
            this.thongtinnguoidung.user_vasc=''
            this.thongtinnguoidung.user_vasc1=''
            this.thongtinnguoidung.user_visa=''
            this.thongtinnguoidung.user_bc=''
            this.thongtinnguoidung.pass_vasc=''
            this.thongtinnguoidung.user_hddt=''
            this.thongtinnguoidung.pass_hddt=''
            this.thongtinnguoidung.pass_visa=''
            this.thongtinnguoidung.pass_vtn=''
            this.thongtinnguoidung.user_cadmin=''
            this.thongtinnguoidung.pass_cadmin=''
            this.thongtinnguoidung.check_tinh=false
            this.thongtinnguoidung.check_quantri=false
            this.thongtinnguoidung.check_enable=false
            this.nhanvien_selected=null
            await this.$refs.thuNgan.hienthi_ds_thungan(-1)
        },
        async changeTinh(){
            this.quan_selected=null
            this.danhsach_quan=await this.lay_danhsach_quan_theo_tinh(this.tinh_selected)
        },
        clickShowHideTT(){
            this.expadedThongTin=!this.expadedThongTin
        },
        clickShowHideDsND(){
            this.expadedDsNguoiDung=!this.expadedDsNguoiDung
        },
        expadedThuNgan(isExpaded){
            this.expadedThuNgan1=isExpaded
        },
        async tvDonViChanged(donvi){
            this.donviFocus=donvi
            //load nhân viên kieu=0
            await this.sp_get_nhanvien_donvi(donvi.donvi_id!=-1?donvi.donvi_id:0, 0)
            //load combox donvi bên thu gân

            //load danh sách
            await this.hienthi_ds_nguoidung_donvi()
        },
        async onEnterTenDN(){
            if(this.thongtinnguoidung.ten_dangnhap.trim()==''){
                return
            }
            this.loading(true)
            this.nhanvien_id=Number(await this.lay_nhanvien_id_theo_mand(this.thongtinnguoidung.ten_dangnhap.trim(), '-1'))
            this.nguoidung_id=Number(await this.lay_nguoidung_id_theo_mand(this.thongtinnguoidung.ten_dangnhap.trim(), '-1'))
            this.donvi_id=Number(await this.lay_donvi_id_theo_nhanvien_id(this.nhanvien_id, '-1'))
            this.loading(false)

            console.log('onEnterTenDN', this.donvi_id)

            //
            this.$refs.danhsachdonvi.findFocusDonViId(this.donvi_id)



        },
        changeNhanVien(){
            let nv=this.danhsach_nhanvien.find(x=>x.nhanvien_id==this.nhanvien_selected)
            if(nv!=undefined){
                this.thongtinnguoidung.ten_daydu=nv.ten_nv
            }
        },
        dangNhapNhanClick(){
            if(this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true){
                // var ma_nd = gridviewDanhSach.GetFocusedRowCellValue("MA_ND");
                //     var mk = gridviewDanhSach.GetFocusedRowCellValue("MATKHAU");
                //     if (ma_nd == null || ma_nd == DBNull.Value || ma_nd == "")
                //     {
                //         Message_Box.ShowWarning("Không lấy được mã người dùng để đăng nhập");
                //         return;
                //     }
                if(this.nguoidung_selected==null){
                    return
                }
                let ma_nd=this.nguoidung_selected.ma_nd
                let matkhau=this.nguoidung_selected.matkhau
                if(ma_nd==null||ma_nd==''){
                    this.$toast.error('Không lấy được mã người dùng để đăng nhập')
                    return
                }

                this.$confirm('Bạn có muốn đăng nhập vào user ' + ma_nd + ' không?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(async() => {
                    //Kiểm tra xem có được quyền đăng nhập nhanh không
                    //this.dvgt_delete(this.dichvugt_id)
                    let result=await this.check_quyen_login(ma_nd,1)
                    if(result=="1"){
                        this.logout()

                    }else if(result=="0"){
                        this.$toast.error('User: ' + ma_nd + 'bạn không được phép đăng nhập')
                    }else{
                        this.$toast.error('Có lỗi trong quá trình lấy thông tin user')

                    }
                }).catch(()=>{})
            }else{
                this.$toast.error('Không thể đăng nhập trong khi đang nhập mới')
            }
        },
        async check_quyen_login(ma_nd, kieu){
            try{
                this.loading(true)
                let response=await UserAPI.fn_lay_quyen_login(this.axios, ma_nd, kieu)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return "3"
                }
            }catch(e){
                return "3"
            }finally{
                this.loading(false)
            }
        },
        async fn_lay_quyen_login(ma_nd, kieu){
            try{
                this.loading(true)
                let response=await UserAPI.fn_lay_quyen_login(this.axios, ma_nd, kieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return "0"
                }
            }catch(e){
                this.loading(false)
                return "0"
            }
        },
        async sp_kiemtratontaind(ma_nd){
            try{
                this.loading(true)
                let response=await UserAPI.sp_kiemtratontaind(this.axios, ma_nd)
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
        async logout() {
			await this.$store.dispatch("user/clearCurrentUser").then((response) => {
				console.log(response);
				this.$router.push({name:"Login"});
			});
		},
        async clickResetMK(){
            if(this.nguoidung_selected==null|this.dataSources.length==0){
                return
            }
            let nguoi_cn=this.$root.token.getUserName()
            let ip_cn=await this.$root.token.getIP()
            let newPassword = this.generateRandomPassword(10);
            console.log("newPassword: ", newPassword);
            let data={
                nguoidung_id:this.nguoidung_selected.nguoidung_id,
                mk:newPassword,//this.DEFAULT_PASSWORD,
                ma_nd: this.nguoidung_selected.ma_nd,
                may_cn:ip_cn?ip_cn.toString():'127.0.0.1',
                nguoi_cn:nguoi_cn
            }
            this.$confirm('Bạn thật sự muốn đặt lại mật khẩu mặc định không?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
            }).then(() => {
                this.sp_nguoidung_resetmk(data)
            })
        },
        openPoupUserNEO(){
            //handle show popup UserNEO
            this.$refs.ganNguoiDungModal.showModal()
        },
        onAcceptFormUserNeo(data){
            this.danhsach_nguoidung_vnp=[].concat(data)
            if(this.danhsach_nguoidung_vnp.length>0){
                let user_neo_arr=this.danhsach_nguoidung_vnp.map(x=>{
                    return x.user_vnp
                })
                this.thongtinnguoidung.user_neo=user_neo_arr.toString()
            }else{
                this.thongtinnguoidung.user_neo=''
            }
        },
        async clearThongTin(){
            this.clear()
            this.dataSources=[]
            this.danhsach_nhanvien=[]
            await this.hienthivnp(0)
            await this.$refs.thuNgan.hienthi_ds_thungan(-1)
        },
        async ghiDuLieu(){
            var is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            if(is_insert==0&&this.nguoidung_selected==null){
                return
            }
            //check dữ liệu
            if(!(await this.kiemtra_dulieu(is_insert))){
                return
            }
            //tạo dữ liệu
            //js_nguoidung, js_nguoidung_tn, is_insert
            let js_nguoidung=this.getDLNguoiDung(is_insert)
            let js_nguoidung_tn=this.getDsThuNgan(is_insert)
            let dtvnp2=this.getUserVNP()

            this.sp_nguoidung_update(JSON.stringify(js_nguoidung), JSON.stringify(js_nguoidung_tn), JSON.stringify(dtvnp2), is_insert)


        },
        async kiemtra_dulieu(is_insert){

            if(this.thongtinnguoidung.ten_daydu.trim()==''){
                this.$toast.error('Tên người dùng trống. Bạn hãy kiểm tra lại!')
                this.$nextTick(()=>{
                    this.$refs.inputTenDayDu.focus()
                })
                return false
            }
            if(this.thongtinnguoidung.ten_dangnhap.trim()==''){
                this.$toast.error('Tên đăng nhập trống. Bạn hãy kiểm tra lại!')
                this.$nextTick(()=>{
                    this.$refs.inputTenDangNhap.focus()
                })
                return false
            }

            const regex = RegExp('^[a-zA-Z0-9*.*-*_]*$')
            if(!regex.test(this.thongtinnguoidung.ten_dangnhap.trim())){
                this.$toast.error('Tên đăng nhập chỉ bao gồm chữ, số và có thể có các ký tự: -, _, .')

                this.$nextTick(()=>{
                    this.$refs.inputTenDangNhap.focus()
                })
                return false
            }
            //
            if(is_insert==1){
                //check trùng ma_nd
                let check=await this.sp_kiemtratontaind(this.thongtinnguoidung.ten_dangnhap.trim())
                if(check.length>0&&check[0].sl>0){
                    this.$toast.error('Mã người dùng đã tồn tại trong hệ thống ! Bạn hãy chọn mã người dùng khác.')
                    this.$nextTick(()=>{
                        this.$refs.inputTenDangNhap.focus()
                    })
                    return false
                }
            }
            return true
        },
        generateRandomPassword(length) {
          const charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()_+";
          let password = "";
          let hasNumber = false;
          let hasSpecialChar = false;

          // Tạo một ký tự số ngẫu nhiên và thêm vào mật khẩu
          const randomNumber = Math.floor(Math.random() * 10);
          password += randomNumber.toString();
          hasNumber = true;

          // Tạo một ký tự đặc biệt ngẫu nhiên và thêm vào mật khẩu
          const randomSpecialChar = "!@#$%&*()_+";
          const randomSpecialCharIndex = Math.floor(Math.random() * randomSpecialChar.length);
          password += randomSpecialChar[randomSpecialCharIndex];
          hasSpecialChar = true;

          // Tạo các ký tự còn lại cho đến khi đạt đến độ dài mong muốn
          for (let i = 0; i < length - 2; i++) {
              const randomIndex = Math.floor(Math.random() * charset.length);
              password += charset[randomIndex];
          }

          // Trộn mật khẩu để đảm bảo tính ngẫu nhiên
          password = password.split('').sort(() => Math.random() - 0.5).join('');

          // Kiểm tra nếu đã có số và ký tự đặc biệt, nếu không, gọi lại hàm để tạo lại mật khẩu
          if (!hasNumber || !hasSpecialChar) {
              return generateRandomPassword(length);
          }

          return password;
        },
        getDLNguoiDung(is_insert){
            //
            var user_ne=''
            if(this.thongtinnguoidung.user_neo==''){
                user_ne=''
            }else{
                let arr=this.thongtinnguoidung.user_neo.split(',')
                user_ne=arr.length>0?arr[0]:''
            }

            let data=[{
                NGUOIDUNG_ID:is_insert==1?0:this.nguoidung_selected.nguoidung_id,
                TEN_ND:this.thongtinnguoidung.ten_daydu.trim(),
                MA_ND:this.thongtinnguoidung.ten_dangnhap.trim(),
                //mã hoá MATKHAU
                MATKHAU:is_insert==1?this.DEFAULT_PASSWORD:this.nguoidung_selected.matkhau,
                TRANGTHAI:this.thongtinnguoidung.check_enable?1:0,
                NGAY_LOGIN:this.thongtinnguoidung.check_enable?moment(new Date()).format('DD/MM/YYYY HH:mm:ss'):null,
                QUANTRI:this.thongtinnguoidung.check_quantri?1:0,
                GHICHU:this.thongtinnguoidung.ghichu.trim(),
                NHOM_ND_ID:this.nhomnd_selected,
                NHANVIEN_ID:this.nhanvien_selected!=null?this.nhanvien_selected:0,
                USER_NEO:user_ne,
                //USER_VASC:this.thongtinnguoidung.user_vasc.trim(),
               // USER_VISA:this.thongtinnguoidung.user_visa.trim(),
                USER_VTN:this.thongtinnguoidung.user_vtn.trim(),
                USER_MAP:this.thongtinnguoidung.user_bc.trim(),
                //mã hoá PASS_VASC
                //PASS_VASC:this.thongtinnguoidung.pass_vasc.trim(),
                DIEMGIAODICH_VASC:this.thongtinnguoidung.user_vasc1.trim(),
                //mã hoá PASS_VASC
                //PASS_VISA:this.thongtinnguoidung.pass_visa.trim(),
                //mã hoá PASS_VTN
                //PASS_VTN:this.thongtinnguoidung.pass_vtn.trim(),
                USER_HDDT:this.thongtinnguoidung.user_hddt.trim(),
                PASS_HDDT:this.thongtinnguoidung.pass_hddt.trim(),
                USER_HDDT_TD:this.thongtinnguoidung.user_cadmin.trim(),
                PASS_HDDT_TD:this.thongtinnguoidung.pass_cadmin.trim(),
                QUAN_ID:(this.thongtinnguoidung.check_tinh&&this.quan_selected!=null)?this.quan_selected:null

            }]
            return data
        },
        getUserVNP(){
            return this.danhsach_nguoidung_vnp.map(item=>{
                return {
                    NGUOIDUNG_ID:this.nguoidung_selected!=null?this.nguoidung_selected.nguoidung_id:0,
                    USER_VNP:item.user_vnp,
                    DONGBO:item.dongbo
                }
            })
        },
        getDsThuNgan(is_insert){
            //"js_nguoidung_tn":"[{\"NGUOIDUNG_ID\": 5932,\"NGUOIDUNG_TN_ID\": 2447}]",
            let danhsach=this.$refs.thuNgan.getDsThuNganGan()
            return danhsach.map(item=>{
                return {
                    NGUOIDUNG_ID:is_insert==1?0:this.nguoidung_selected.nguoidung_id,
                    NGUOIDUNG_TN_ID:item.nguoidung_id
                }
            })
        },
        async onSelectedRow(item){
            this.nguoidung_selected=item
            this.nguoidung_id=item.nguoidung_id!=null?item.nguoidung_id:-1

            //init lên form thông tin người dùng
            this.nhanvien_selected=item.nhanvien_id

            this.thongtinnguoidung.ten_daydu=item.ten_nd!=null?item.ten_nd:''
            this.thongtinnguoidung.ten_dangnhap=item.ma_nd!=null?item.ma_nd:''
            this.thongtinnguoidung.ghichu=item.ghichu!=null?item.ghichu:''
            //     txtMatKhau.Text = gridviewDanhSach.GetRowCellValue(FocusedRowHandle, "MATKHAU").ToString();
            //     txtMatKhauNL.Text = gridviewDanhSach.GetRowCellValue(FocusedRowHandle, "MATKHAU").ToString();
            this.nhomnd_selected=item.nhom_nd_id

            this.thongtinnguoidung.user_hddt=item.user_hddt!=null?item.user_hddt:''
            this.thongtinnguoidung.pass_hddt=item.pass_hddt!=null?item.pass_hddt:''

            //this.thongtinnguoidung.user_vasc=item.user_vasc!=null?item.user_vasc:''
            this.thongtinnguoidung.user_vasc1=item.diemgiaodich_vasc!=null?item.diemgiaodich_vasc:''

            // this.thongtinnguoidung.user_visa=item.user_visa!=null?item.user_visa:''
            // this.thongtinnguoidung.pass_visa=item.pass_visa!=null?item.pass_visa:''
            this.thongtinnguoidung.user_vtn=item.user_vtn!=null?item.user_vtn:''

            this.thongtinnguoidung.user_bc=item.user_map!=null?item.user_map:''
            this.thongtinnguoidung.user_cadmin=item.user_hddt_td!=null?item.user_hddt_td:''
            this.thongtinnguoidung.pass_cadmin=item.pass_hddt_td!=null?item.pass_hddt_td:''

            //sau check lại pass
            //txtPassVasc.Text = CustomEncryption.Decrypt(gridviewDanhSach.GetRowCellValue(FocusedRowHandle, "PASS_VASC").ToString());
            ///this.thongtinnguoidung.pass_vasc=item.pass_vasc!=null?item.pass_vasc:''
            //txtPassVISA.Text = CustomEncryption.Decrypt(gridviewDanhSach.GetRowCellValue(FocusedRowHandle, "PASS_VISA").ToString());
            //this.thongtinnguoidung.pass_visa=item.pass_visa!=null?item.pass_visa:''
            //txtPassVTN.Text = CustomEncryption.Decrypt(gridviewDanhSach.GetRowCellValue(FocusedRowHandle, "PASS_VTN").ToString());
            //this.thongtinnguoidung.pass_vtn=item.pass_vtn!=null?item.pass_vtn:''


            if(item.quan_id){
                this.thongtinnguoidung.check_tinh=true
                this.quan_selected=item.quan_id.toString()
            }else{
                this.quan_selected=null
                this.thongtinnguoidung.check_tinh=false
            }

            if(item.quantri!=null&&item.quantri==1){
                this.thongtinnguoidung.check_quantri=true
            }else{
                this.thongtinnguoidung.check_quantri=false
            }
            if(item.trangthai!=null&&item.trangthai==1){
                this.thongtinnguoidung.check_enable=true
            }else{
                this.thongtinnguoidung.check_enable=false
            }

            //Hiển UserNeo VNP, thu ngân
            this.loadLoiVNP=false
            await this.hienthivnp(this.nguoidung_id)
            if(!this.loadLoiVNP){
                this.thongtinnguoidung.user_neo=item.user_neo?item.user_neo.toString():''
            }else{
                this.$toast.error('Đã xảy ra lỗi load người dùng VNP vui lòng nhập lại!')
                this.nguoidung_selected=null
                this.nguoidung_id=-1
                this.setActiveActions(0)
                return
            }


            this.$refs.thuNgan.loiLoadThuNgan=false
            await this.$refs.thuNgan.hienthi_ds_thungan(this.nguoidung_id)
            if(!this.$refs.thuNgan.loiLoadThuNgan){
                this.setActiveActions(3)
            }else{
                //Load thu ngân lỗi clear thông báo lỗi không load thực hiện thao tác
                this.nguoidung_selected=null
                this.nguoidung_id=-1
                this.setActiveActions(0)
                this.$toast.error('Đã xảy ra lỗi load thu ngân vui lòng nhập lại!')
            }

        },
        async lay_danhsach_nhomnd(){
            try{
                this.loading(true)
                let response=await UserAPI.lay_danhsach_nhomnd(this.axios)
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
        async lay_nhanvien_id_theo_mand(ma_nd, defaultValue){
            try{
                let response = await UserAPI.lay_nhanvien_id_theo_mand(this.axios, ma_nd)
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
                let response = await UserAPI.lay_nguoidung_id_theo_mand(this.axios, ma_nd)
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
                let response = await UserAPI.lay_donvi_id_theo_nhanvien_id(this.axios, nhanvien_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async lay_danhsach_tinh(){
            try{
                this.loading(true)
                let response=await UserAPI.lay_danhsach_tinh(this.axios)
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
        async lay_danhsach_quan_theo_tinh(tinh_id){
            try{
                this.loading(true)
                let response=await UserAPI.lay_danhsach_quan_theo_tinh(this.axios, tinh_id)
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
        async lay_danhsach_nguoidung_donvi(donvi_id){
            try{
                this.loading(true)
                let response=await UserAPI.lay_danhsach_nguoidung_donvi(this.axios, donvi_id)
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
        async hienthi_ds_nguoidung_donvi(){
            let donvi_id=this.donviFocus!=null?this.donviFocus.donvi_id:0
            //this.dataSources=[]
            this.dataSources=await this.lay_danhsach_nguoidung_donvi(donvi_id)
            if(this.dataSources.length>0){
                //init focus row
                if(this.nguoidung_id!=0){
                    const index=this.dataSources.findIndex(x=>x.nguoidung_id==this.nguoidung_id)
                    if(index>-1){
                        this.rowSelectedId=this.dataSources[index].nguoidung_id
                        this.onSelectedRow(this.dataSources[index])
                    }else{
                        this.rowSelectedId=this.dataSources[0].nguoidung_id
                        this.onSelectedRow(this.dataSources[0])
                    }
                }else{
                    this.rowSelectedId=this.dataSources[0].nguoidung_id
                    this.onSelectedRow(this.dataSources[0])
                }

            }else{
                this.nguoidung_selected=null
                //init dữ liệu thu ngân không có người dùng
                await this.$refs.thuNgan.hienthi_ds_thungan(-1)
                this.setActiveActions(0)
            }
        },
        async sp_get_nhanvien_donvi(donvi_id, kieu){
            try{
                this.loading(true)
                this.danhsach_nhanvien=[]
                let response=await UserAPI.sp_get_nhanvien_donvi(this.axios, donvi_id, kieu)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_nhanvien=response.data.data.map(item=>{
                        return Object.assign(item,{
                            ten_ht:item.ma_nv+' - '+item.ten_nv
                        })
                    })
                }else{
                    this.danhsach_nhanvien=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách nhân viên')
            }finally{
                this.loading(false)
            }
        },
        async sp_nguoidung_resetmk(data){
            try{
                this.loading(true)
                let response=await UserAPI.sp_nguoidung_resetmk(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&& response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success('Mật khẩu đã được thay đổi về mặc định!')
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, đặt lại mật khẩu không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, đặt lại mật khẩu không thành công')
                }
            }
        },
        async sp_nguoidung_update(js_nguoidung, js_nguoidung_tn, dtvnp2, is_insert){
            try{
                this.loading(true)
                let response = await UserAPI.sp_nguoidung_update(this.axios, js_nguoidung, js_nguoidung_tn, dtvnp2, is_insert)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success(is_insert==1?'Thêm người dùng thành công !':'Cập nhật thông tin người dùng thành công!')
                        //hiển thị danh sách
                        await this.hienthi_ds_nguoidung_donvi()
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error(is_insert==1?'Đã xảy ra lỗi, tạo người dùng không thành công':'Đã xảy ra lỗi, cập nhật thông tin người dùng không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error(is_insert==1?'Đã xảy ra lỗi, tạo người dùng không thành công':'Đã xảy ra lỗi, cập nhật thông tin người dùng không thành công')
                }
            }
        },
        async hienthivnp(nguoidung_id){
            this.danhsach_nguoidung_vnp=await this.sp_nguoidung_vnp(nguoidung_id)
            if(this.danhsach_nguoidung_vnp.length>0){
                // let user_neo_arr=this.danhsach_nguoidung_vnp.map(x=>{
                //     return x.user_vnp
                // })
                //this.thongtinnguoidung.user_neo=user_neo_arr.toString()
            }else{
                //this.thongtinnguoidung.user_neo=''
            }
        },
        async sp_nguoidung_vnp(nguoidung_id){
            try{
                this.loading(true)
                let response=await UserAPI.sp_nguoidung_vnp(this.axios, nguoidung_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response.data.error_code != 'BSS-00000204'){
                        this.loadLoiVNP=true
                    }
                    return []
                }
            }catch(e){
                this.loading(false)
                this.loadLoiVNP=true
                return []
            }
        },
        async initDuLieu(){
            this.danhsach_nhomnd=await this.lay_danhsach_nhomnd()
            if(this.danhsach_nhomnd.length>0){
                this.nhomnd_selected=this.danhsach_nhomnd[0].NHOM_ND_ID
            }

            this.danhsach_tinh=await this.lay_danhsach_tinh()
            if(this.danhsach_tinh.length>0){
                //lấy tỉnh theo token người dùng
                this.tinh_selected=Number(this.$root.token.getPhanVungID())
            }

            if(this.tinh_selected!=null){
                this.danhsach_quan=await this.lay_danhsach_quan_theo_tinh(this.tinh_selected)
            }
            //
            this.login_btn=false
            let ma_nd=this.$root.token.getUserName()
            let checkLogin=await this.fn_lay_quyen_login(ma_nd, 0)
            if(checkLogin&&checkLogin.toString()=='1'){
                this.login_btn=true
            }
            this.actions[this.actions.findIndex(x=>x.id=='dangnhap')].visible=this.login_btn

            await this.$refs.thuNgan.initDuLieu()
            await this.$refs.danhsachdonvi.initDulieu()

            //init default password
            //let maTinh=this.$root.token.getMaTinh().toString().toLowerCase()
            //this.DEFAULT_PASSWORD='vnpt'+maTinh+'@123'
            let newpass = this.generateRandomPassword(10)
            this.DEFAULT_PASSWORD= newpass
        },
        // async check_Ma_ND(){
        // console.log(this.thongtinnguoidung.ten_dangnhap)
        // if(this.thongtinnguoidung.ten_dangnhap.trim() == ''|| this.thongtinnguoidung.ten_dangnhap == null || this.thongtinnguoidung.ten_dangnhap == undefined)
        //     this.$toast.warning('Nhân viên chưa có người dùng')
        // // else this.$toast.success(`Thành công`)
        // }
    },
    mounted(){
        this.setActiveActions(-1)
        setTimeout(()=>{
            this.initDuLieu()
        },200)
    },
    watch: {
        'thongtinnguoidung.check_enable': {
            async handler(value){
                let check = false
                let kq = await UserAPI.lay_nv_theo_ndid(this.axios, {
                    "p_nguoidung_id":this.nguoidung_id
                })
                if(kq["data"] && kq["data"]["error_code"] == "BSS-00000000"){
                    check = true
                    this.thongtinnguoidung.check_enable = value
                }else{
                    this.thongtinnguoidung.check_enable = false
                    check = false
                }
                if(check == false && value == false){
                    this.$toast.warning(`${kq["data"]["message"]}`)
                }
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

.slide-enter-active {
   -moz-transition-duration: 0.3s;
   -webkit-transition-duration: 0.3s;
   -o-transition-duration: 0.3s;
   transition-duration: 0.3s;
   -moz-transition-timing-function: ease-in;
   -webkit-transition-timing-function: ease-in;
   -o-transition-timing-function: ease-in;
   transition-timing-function: ease-in;
}

.slide-leave-active {
   -moz-transition-duration: 0.3s;
   -webkit-transition-duration: 0.3s;
   -o-transition-duration: 0.3s;
   transition-duration: 0.3s;
   -moz-transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
   -webkit-transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
   -o-transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
   transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
}

.slide-enter-to, .slide-leave {
   max-height: 100px;
   overflow: hidden;
}

.slide-enter, .slide-leave-to {
   overflow: hidden;
   max-height: 0;
}
</style>
