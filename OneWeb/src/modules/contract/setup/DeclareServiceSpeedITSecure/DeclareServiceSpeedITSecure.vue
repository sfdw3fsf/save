<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="page-content">
            <div class="box-form padt8 padb5">
                <div class="pull-right">
                    <div class="inline">
                        <div class="info-row marb0">
                            <div class="key fw6 w80">Tổng tiền</div>
                            <div class="value disabled">
                                <!-- <input type="text" v-model="input_tongtien" class="form-control red tright" value="0"> -->
                                <vue-numeric class="form-control bold red tright" separator=","  :value="input_tongtien" @change="e=>input_tongtien=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <ThongTinPhieu ref="thongtinphieu"
                :cty_newline="cty_newline"
                :sizeDataSource="dataSources.length"
                @callbackHuongGiaoTheoQuyTrinh="callbackHuongGiaoTheoQuyTrinh"
                @onEnterMaTB="onEnterMaTB"
                @changeDichVu="changeDichVu"
                @callbackStatusActionTop="callbackStatusActionTop"
                @callbackVisibleActionTop="callbackVisibleActionTop"
                @loadControl="loadControl"
                @callbackHienThiDanhSach="callbackHienThiDanhSach"
                @capNhatNDTH="capNhatNDTH"
                @capNhatPort="capNhatPort"
                @callPort="callPort"
                @clearDuLieu="clearDuLieuChangeQuyTrinh"
                />
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">
                        Danh sách thuê bao thay đổi tốc độ
                        <!-- <button class="btn btn-second btn-search marl10" @click="clickTimKiem">
                            <i class="fa" :class="{'fa-chevron-down':!expandedTK, 'fa-chevron-up':expandedTK}" aria-hidden="true"></i>
                            
                        </button> -->
                    </div>
                    <div class="pull-right">
                        <div class="list-checks">
                            <div class="item">
                                <div class="check-action">
                                    <input type="radio" class="check" name="loaiphieu" value="0" :checked="loaiphieu==0" @change="changeLoaiPhieu($event)" />
                                    <span class="name">Phiếu mới</span>
                                </div>
                            </div>
                            <div class="item">
                                <div class="check-action">
                                    <input type="radio" class="check" name="loaiphieu" value="1" :checked="loaiphieu==1" @change="changeLoaiPhieu($event)"/>
                                    <span class="name">Phiếu trả lại</span>
                                </div>
                            </div>
                            <div class="item bold red">{{title_tongso}}</div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <KDataGrid
                    ref="girdPhieu"
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    @onRowSelected="onSelectedRow"
                    @rowSelected="rowGridSelected"
                    :enabledSelectFirstRow="true"/>
            </div>
        </div>
        <Footer/>
        <!-- Modal -->
        <ThongTinHenKhachHangModal ref="thongTinHenKhachHangModal" :hdkh_id="hdkh_id" :quyenduoc_hen="1"/>
        <DuAnModal ref="duAnModal" :hdtb_id="hdtb_id"/>
        <ExportDataModal ref="exportDataModal" :data="dataSources"  file_name="danh-sach-thue-bao"/>
        <DoKiemModal ref="doKiem" :hdtb_id="hdtb_id"/>
        <ThayDoiKhuVucQuanLyThueBaoModal ref="thayDoiKhuVucQuanLyThueBaoModal" :data="dataPopupKhuVuc"/>
        <TonPhieuGiaoModal ref="tonPhieuGiaoModal" :phieu_id="phieu_id" :tthd_id="Number(tthd)"/>
        <GuiYCModal ref="guiYCModal"
            :hdtb_id="Number(hdtb_id)"
            :vphieu_id="Number(phieu_id)"
            :loai="1"
            :vip_giamsat="vip_gs"
        />
        <ChuyenToModal ref="chuyenToModal"
            :baohong_id="0"
            :phieu_id="phieu_id"
            :hdtb_id="hdtb_id"
            @success="successChuyenTo"/>
        <!-- <TraCuuThongSoKTModal ref="traCuuThongSoKTModal"/> -->
        <TraCuuThongSoKTModal ref="traCuuThongSoKTModal"
            :dichvuvt_id="dichvuvt_selected"
            :ma_tb="ma_tb"
            :kieu="1"
            :thuebao_id="thuebao_id"
        />
        <TraPhieuModal ref="traPhieuModal" :modelTraPhieu="modelTraPhieu" @traphieu_success="traphieu_success"/>
        <VatTuThueBao ref="vatTuThuebao" :inputData="inputDataFormVattuTB"/>
        <CapVatTuTB ref="capVatTuTB" :inputData="inputDataFormCapVattuTB"/>
        <InBienBan ref="inBienBan" :params="paramInBienBan"/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/mirana/ActionTop.vue'
import ThongTinPhieu from './ThongTinPhieu.vue'
import DeclareServiceSpeedITSecureAPI from './DeclareServiceSpeedITSecureAPI'
import ThongTinHenKhachHangModal from '../SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import DuAnModal from './popups/DuAn/DuAnModal.vue'
import {LoaiHinhTB, DichVuVienThong, DONGBO_TEST, KieuLapDat, TRANGTHAI_PORT} from './ThamSo'
import moment from 'moment'
import ExportDataModal from '../../profile/ExportData/ExportDataModal.vue'
import DoKiemModal from './popups/DoKiem/DoKiemModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import TonPhieuGiaoModal from './popups/TonPhieuGiao/TonPhieuGiaoModal.vue'
import GuiYCModal from './popups/GuiYC/GuiYCModal.vue'
import TraCuuThongSoKTModal from './popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import ChuyenToModal from '../../../install/UpdateSurveyResult/popups/ChuyenToModal.vue'
import TraPhieuModal from './popups/TraPhieuModal.vue'
import VatTuThueBao from '../ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import CapVatTuTB from '../../complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue'
import ChuyenTo from '../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
import InBienBan from '../../../print/InBienBan/InBienBan.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
//frmHTHSThayDoiTocDo 9+8+14;15;16
import { LoaiHopDong } from '@/const/enums'
export default {
    name:'DeclareServiceSpeedITSecure',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        ThongTinPhieu,
        ThongTinHenKhachHangModal,
        DuAnModal,
        ExportDataModal,
        DoKiemModal,
        ThayDoiKhuVucQuanLyThueBaoModal,
        TonPhieuGiaoModal,
        GuiYCModal,
        ChuyenToModal,
        TraCuuThongSoKTModal,
        TraPhieuModal,
        VatTuThueBao,
        CapVatTuTB,
        ChuyenTo,
        InBienBan,
        KDataGrid
    },
    data(){
        return{
            header: {
                title: "Thay đổi tốc độ ADSL",
                list: [],
            },
            //Giao phiếu(sau btn Hoàn công)
            actions:[
                {
                    id:'tsbtnNhapMoi',
                    name:'Nhập mới',
                    active:true,
                    visible:false,
                    icon_class:'one-file-plus',
                    type:1
                },
                {
                    id:'btnLayTTMoi',
                    name:'Lấy TT',
                    active: true,
                    visible:true,
                    icon_class:'one-file-attach',
                    tooltip:'Lấy thông tin mới',
                    type:1
                },
                {
                    id:'tsbtnChapNhan',
                    name:'Cập nhật',
                    active: true,
                    visible:true,
                    icon_class:'one-reload1',
                    type:1
                },
                {
                    id:'tsbtnGiaoViec',
                    name:'Giao việc',
                    active: true,
                    visible:true,
                    icon_class:'one-file-edit2',
                    tooltip:'Giao việc cho nhân viên thực hiện',
                    type:1
                },
                {
                    id:'tsbtnKichHoat',
                    name:'Kích hoạt',
                    active: false,
                    visible:true,
                    icon_class:'one-file-arrow-up',
                    tooltip:'Kích hoạt Visa/Vasc',
                    type:1
                },
                {
                    id:'tsbtnHoanCong',
                    name:'Hoàn công',
                    active: true,
                    visible:true,
                    icon_class:'one-hoancong',
                    tooltip:'Cập nhật kết quả',
                    type:1
                },
                //
                {
                    id:'tsbtnGiaoPhieu',
                    name:'Giao phiếu',
                    active:false,
                    visible:false,
                    icon_class:'one-file-arrow-right1',
                    tooltip:'Giao phiếu',
                    type:1
                },
                {
                    id:'btnPhieuGiaoSai',
                    name:'Trả phiếu',
                    active:true,
                    visible:true,
                    icon_class:'one-file-back',
                    type:1
                },
                {
                    id:'tsbtnHoanThienHS',
                    name:'Hoàn thiện',
                    active: false,
                    visible:true,
                    icon_class:'one-file-check',
                    tooltip:'Hoàn thiện hồ sơ',
                    type:1
                },
                {
                    id:'tsbtnXuatFile',
                    name:'Xuất file',
                    active: true,
                    visible:true,
                    icon_class:'one-download',
                    type:1
                },
                //Xuất phiếu:  Phiếu thi công, In Phiếu
                {
                    id:'xuatphieu',
                    name:'Xuất phiếu',
                    active: true,
                    visible:true,
                    icon_class:'one-xlsx-import',
                    type:1
                },
                //
                {
                    id:'tsbtnHen',
                    name:'Hẹn LĐ',
                    active: true,
                    visible:true,
                    icon_class:'one-henld',
                    tooltip:'Hẹn lắp đặt',
                    type:1
                },
                {
                    id:'tsbtnThongTinKT',
                    name:'Thông số KT',
                    active: true,
                    visible:true,
                    icon_class:'nc-icon-glyph ui-1_settings',
                    tooltip:'Thông số kỹ thuật cũ',
                    type:1
                },
                {
                    id:'tsbtnVatTu',
                    name:'Vật tư',
                    active:true,
                    visible:false,
                    icon_class:'one-vt',
                    type:1
                },
                {
                    id:'tsbtnVatTuMoi',
                    name:'Vật tư',
                    active:true,
                    visible:false,
                    icon_class:'one-vt',
                    type:1
                },
                {
                    id:'tsbtnDuAn',
                    name:'Dự án',
                    active: true,
                    visible:true,
                    icon_class:'one-clipart-edit',
                    type:1
                },
                {
                    id:'tsbtnKQ_DoKiem',
                    name:'Đo kiểm',
                    active: true,
                    visible:true,
                    icon_class:'nc-icon-glyph design_design',
                    type:1
                },
                {
                    id:'tsbtnSua_KenhThu',
                    name:'Sửa kênh thu',
                    active: true,
                    visible:true,
                    icon_class:'nc-icon-glyph ui-1_edit-74',
                    type:1
                },
                {
                    id:'tsbtnGiaoPhieu_Ton',
                    name:'Tồn phiếu',
                    active: true,
                    visible:true,
                    icon_class:'one-calendar-number',
                    tooltip:'Tồn phiếu',
                    type:1
                },
                {
                    id:'tsbtnChuyenTo',
                    name:'Chuyển tổ',
                    active: true,
                    visible:true,
                    icon_class:'one-change',
                    type:1
                },
                {
                    id:'tsbtnNVKT',
                    name:'Gán KV',
                    active: true,
                    visible:true,
                    icon_class:'-ap icon-pin',
                    tooltip:'Thay đổi khu vực quản lý thuê bao',
                    type:1
                },
                 {
                    id:'tsbtnCreatIssue',
                    name:'Gửi YC',
                    active: true,
                    visible:true,
                    icon_class:'one-mobile-plus',
                    tooltip:'Gửi yêu cầu IT360',
                    type:1
                },
            ],
            columns:[
                {
                    fieldName: "ma_gd",
                    headerText: "Mã GD",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Account",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ten_tb",
                    headerText: "Tên thuê bao",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "diachi_ld",
                    headerText: "Địa chỉ LĐ",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "trangthaiin",
                    headerText: "Trạng thái in",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ngay_kh",
                    headerText: "Ngày KH",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ngay_ht",
                    headerText: "Ngày HT",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "loaihinh_tb",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ip",
                    headerText: "IP",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ten_tucthi",
                    headerText: "Kiểu",
                    allowFiltering: true,
                    width:160
                },
                //
                {
                    fieldName: "ten_status",
                    headerText: "Kích hoạt",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "tenkieu",
                    headerText: "Kiểu trả",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "thangtra",
                    headerText: "Tháng trả",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ngaytra",
                    headerText: "Ngày trả",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "dv_giao",
                    headerText: "Đơn vị giao",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "dv_nhan",
                    headerText: "Đơn vị nhận",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ten_kv",
                    headerText: "Khu vực",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "giohen_tu",
                    headerText: "Hẹn từ",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "giohen_den",
                    headerText: "Hẹn đến",
                    allowFiltering: true,
                    width:160
                },
                {
                    fieldName: "ghichu",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    width:160
                },
            ],
            dataSources:[],
            rowFocusIndex:0,
            loaiphieu:0,
            //DANG_THI_CONG = 4,
            //param tag
            tthd:9,
            //THAY_DOI_TOCDO_ADSL = 8
            loai_hd:8,
            //param tag
            dsloaihd_id:"9",
            //param tag, mặc định để sài UR2.6.042  Khai báo thay đổi tốc độ dịch vụ CNTT - ATBMTT-TTDL
            dsdichvuvt_id:"14,15,16",
            input_tongtien:'0',
            hdkh_id:0,
            hdtb_id:0,
            port_id:0,
            vci_vpi_id:0,
            dichvuvt_id:0,
            loaitb_id:0,
            kieuld_id:0,
            phieu_id:0,
            in_tocdothuc_id:-1,
            tramtb_id:0,
            congnghe_id:0,
            thuebao_id:0,
            huonggiao_id:0,
            luong_id:0,
            ten_huonggiao:'',
            rowSelected:null,
            dataPopupKhuVuc:{
              quan_id:0,
              phuong_id:0,
              pho_id:0,
              ap_id:0,
              khu_id:0,
              dacdiem_id:0,
              khuvuc_id:0,
              donviql_id:0,
              hdtb_id:0
            },
            dichvuvt_selected:0,
            ma_tb:'',
            modelTraPhieu:{
                maGd: '',
                loaihdId: 0,
                dichvuvtId: 0,
                donviId: 0,
                matb: '',
                nhanvienId: 0,
                ngaygiao: '',
                ngaytra: '',
                kieugoiId: 0
            },
            inputDataFormVattuTB:{
                loaitb_id:0,
                phieu_id:0,
                loaihd_id:0,
                hdtb_id:0,
                thuebao_id:0,
                vsua_vattu:0,
                vdonvi_nhan_id:0,
                vngay_ht:'',
                vttph_id:0
            },
            inputDataFormCapVattuTB:{
                loaitb_id:0,
                phieu_id:0,
                loaihd_id:0,
                hdtb_id:0,
                thuebao_id:0,
                vdvvt_id:0,
                vma_tbcur:0,
                baohong_id:0,
                kieu_f:0,
                tagfrm:'0'
            },
            expandedTK:false,
            gridColumnsTK:[
                {
                    fieldName: "ma_gd",
                    headerText: "Mã GD",
                    allowFiltering: true,
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Account",
                    allowFiltering: true,
                },
                {
                    fieldName: "ten_tb",
                    headerText: "Tên TB",
                    allowFiltering: true,
                },
                {
                    fieldName: "diachi_ld",
                    headerText: "Địa chỉ LĐ",
                    allowFiltering: true,
                }
            ], 
            dataSourceTK:[],
            paramInBienBan:{},
            vip_gs:false,
            selectIndex:0,
            cty_newline: false,
            DS_CHUQUAN_THEO_QUYEN: null
        }
    },
    computed:{
        title_tongso(){
            if(this.dataSources.length==0){
                return ''
            }
            return 'Tổng số : '+this.dataSources.length+' thuê bao'
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='tsbtnNhapMoi'){
                this.clear()
            }else if(action.id=='btnLayTTMoi'){
                this.hienthi_danhsach()
            }else if(action.id=='tsbtnChapNhan'){
                this.capnhat()
            }else if(action.id=='tsbtnGiaoViec'){
                if(this.dataSources.length==0){
                    this.$toast.error('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
                    return
                }
                this.$refs.thongtinphieu.giaoPhieuNV()
            }else if(action.id=='tsbtnKichHoat'){
                //chưa rõ đúng sai
                if (this.$route.path === '/contract/ChangeSpeedFiberKTVT') { 
                this.kichhoat1();
                } else {
                    this.kichhoat();
                }
            }else if(action.id=='tsbtnHoanCong'){
                this.hoancong()
            }else if(action.id=='tsbtnGiaoPhieu'){
                //[UR2.3.001] - Giao phiếu (Đã làm) contract/handoverticket
                this.giaophieu()
            }else if(action.id=='btnPhieuGiaoSai'){
               // this.$toast.error('Tính năng hiện đang được xây dựng, thuộc [UR2.3.002] - Trả phiếu thi công')
                this.modelTraPhieu.maGd=this.$refs.thongtinphieu.input_account
                this.modelTraPhieu.loaihdId=8 //LoaiHopDong.THAY_DOI_TOCDO_ADSL
                //this.modelTraPhieu.dichvuvtId=4//DichVuVienThong.ADSL
                //chỉnh lại truyền theo dịch vụ cobobox
                this.modelTraPhieu.dichvuvtId=this.$refs.thongtinphieu.dichvu_vt_selected?this.$refs.thongtinphieu.dichvu_vt_selected:4
                this.modelTraPhieu.donviId=this.$root.token.getDonViID()
                this.modelTraPhieu.matb=this.$refs.thongtinphieu.input_account
                this.modelTraPhieu.nhanvienId=this.$root.token.getNhanVienID()
                this.modelTraPhieu.kieugoiId=3
                setTimeout(()=>{
                     this.$refs.traPhieuModal.showModal()
                }, 300)
            }else if(action.id=='tsbtnHoanThienHS'){
                //
                this.hoanthien_hoso()
            }else if(action.id=='tsbtnXuatFile'){
                this.xuat_file()
            }else if(action.id=='xuatphieu'){
                //this.$toast.error('Tính năng đang chờ tích hợp')
                this.xuatphieu()
            }else if(action.id=='tsbtnHen'){
                if(this.hdkh_id!=0){
                    this.$refs.thongTinHenKhachHangModal.showModal()
                }
            }else if(action.id=='tsbtnThongTinKT'){
                if(this.thuebao_id!=0){
                    this.ma_tb=this.$refs.thongtinphieu.input_account!=null?this.$refs.thongtinphieu.input_account.trim():''
                    this.$refs.traCuuThongSoKTModal.showModal()
                }
            }else if(action.id=='tsbtnVatTu'){
                if(this.phieu_id==0){
                    this.$toast.error('Bạn chưa chọn phiếu!')
                    return
                }
                //WinUI.WinUIDieuHanhThiCong.frmVatTuThueBao
                this.inputDataFormVattuTB.phieu_id=this.phieu_id
                this.inputDataFormVattuTB.hdtb_id=this.hdtb_id
                //LoaiHopDong.THAY_DOI_TOCDO_ADSL
                this.inputDataFormVattuTB.loaihd_id=8
                setTimeout(()=>{
                    this.$refs.vatTuThuebao.showModal()
                }, 500)
            }else if(action.id=='tsbtnVatTuMoi'){
                this.inputDataFormCapVattuTB.phieu_id=this.phieu_id
                this.inputDataFormCapVattuTB.loaihd_id=8 //LoaiHopDong.THAY_DOI_TOCDO_ADSL
                this.inputDataFormCapVattuTB.hdtb_id=this.hdtb_id
                this.inputDataFormCapVattuTB.loaitb_id=this.loaitb_id
                this.inputDataFormCapVattuTB.thuebao_id=this.thuebao_id
                this.inputDataFormCapVattuTB.baohong_id=this.baohong_id
                setTimeout(()=>{
                    this.$refs.capVatTuTB.openDialog()
                }, 500)
            }else if(action.id=='tsbtnDuAn'){
                //Bỏ sau này không dùng này nữa, confirm Hiếu
                this.$refs.duAnModal.showModal()
            }else if(action.id=='tsbtnKQ_DoKiem'){
                //UR2.4.052_044/BSS-1374
                if(this.hdtb_id!=0){
                    this.$refs.doKiem.showModal()
                }else{
                    this.$toast.error('Bạn chưa chọn phiếu!')
                }
            }else if(action.id=='tsbtnSua_KenhThu'){
                //UR3.5.021
                //https://onebss-dev.vnptit3.vn/#/qltn/ThayDoiKenhThu 
                this.$router.push({ name: 'ThayDoiKenhThu'})
            }else if(action.id=='tsbtnGiaoPhieu_Ton'){
                if(this.phieu_id==0){
                    this.$toast.error('Hãy chọn phiếu trước để nhập lý do tồn!')
                    return
                }
                //binding phieu_id, tthd_id
                this.$refs.tonPhieuGiaoModal.showModal()
            }else if(action.id=='tsbtnChuyenTo'){
                this.chuyenTo()
            }else if(action.id=='tsbtnNVKT'){
                this.dataPopupKhuVuc.hdtb_id=this.hdtb_id
                this.dataPopupKhuVuc.donviql_id=0
                this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
            }else if(action.id=='tsbtnCreatIssue'){
                this.vip_gs = false
                if(this.$refs.thongtinphieu.phailam('VIP_GIAMSAT_IT360')){
                    this.vip_gs=true
                }
                this.$refs.guiYCModal.showModal()
            }
        },
        clear(){
            this.hdtb_id = 0
            this.hdkh_id = 0
            this.phieu_id = 0
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=false
            this.$refs.thongtinphieu.clear()
            this.input_tongtien='0'
            this.in_tocdothuc_id=-1
        },
        xuat_file(){
            if(this.dataSources.length==0){
                this.$toast.error('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
                return
            }
            this.$refs.exportDataModal.showModal()
        },
        async xuatphieu(){
            await this.InPhieu()
        },
        async InPhieu(){
            let vhdkh_id=await this.fn_tt_hd_thuebao(this.hdtb_id, '-1')
            if(vhdkh_id=='-1'){
                this.$toast.error('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
                return
            }
            let dataChecks=this.$refs.girdPhieu.getSelectedRecords()
            if(dataChecks.length==0){
                this.$toast.error('Hãy chọn phiếu')
                return
            }
            var n_hdkh_id = ""
            var n_hdtb_id = ""
            var n_phieu_id = ""
            for(let i=0;i<dataChecks.length;i++){
                let item=dataChecks[i]
                n_hdkh_id += item.hdkh_id + ','
                n_hdtb_id += item.hdtb_id + ','
                n_phieu_id += item.phieu_id + ','
            }
            this.paramInBienBan.n_hdkh_id=n_hdkh_id.substring(0, n_hdkh_id.length-1)
            this.paramInBienBan.nvth_id=-1
            this.paramInBienBan.cv_thuchien=''
            this.paramInBienBan.n_phieu_id=n_phieu_id.substring(0, n_phieu_id.length-1)
            this.paramInBienBan.n_hdtb_id=n_hdtb_id.substring(0, n_hdtb_id.length-1)
            this.paramInBienBan.huonggiao_id = this.huonggiao_id
            this.paramInBienBan.tentram_tb = ''
            setTimeout(()=>{
                this.$refs.inBienBan.showModal()
            },500)
        },
        clickTimKiem(){
            this.expandedTK=!this.expandedTK
        },
        dataGrid_rowClicked(i, item){
            console.log('dataGrid_rowClicked',item)
            this.expandedTK=false
            const index=this.dataSources.findIndex(x=>x.phieu_id==item.phieu_id)
            if(index>-1){
                this.rowFocusIndex=index
            }else{
                this.rowFocusIndex=0
            }
            
        },
        clearDuLieuChangeQuyTrinh(){
            this.dataSourceTK=[]
            this.dataSources=[]
            this.clearDuLieu()
        },
        rowGridSelected(args){
            this.selectIndex=args.rowIndex
            console.log('selectIndex', this.selectIndex)
        },
        async chuyenTo(){
            let result = await this.sp_lay_ds_phieuvt_hdbh({
                    phieu_id:this.phieu_id,
                    baohong_id:"0",
                    hdtb_id:this.hdtb_id,
                    kieutbi:"0",
                    status:"0"
            })
            if(this.$refs.thongtinphieu.phailam('KIEMTRA_VT_CHUYENTO')&&result.length>0){
                this.$toast.error('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
                return
            }
            this.$refs.chuyenToModal.showModal()
        },
        async giaophieu(){
            let thongtinphieu=this.$refs.thongtinphieu.getThongTinPhieu()
            let huonggiao=await this.lay_huonggiao_theo_luong_id(this.luong_id)
            //trangthaiphieu 1,2 theo quy ước dev web
            if(huonggiao.length>0){
                //this.$toast.success('Chuyển form giao phiếu')
                let query={
                    vma_gd: thongtinphieu.input_magd,
                    vhuonggiao_id: huonggiao[0].huonggiao_id,
                    vtrangthaiphieu:1
                }
                console.log('query', query)
                let routeData = this.$router.resolve({name: 'HandoverTicket', query: query});
                window.open(routeData.href, '_blank')
            }else{
                this.$toast.error('Chưa có hướng giao được gán !')
            }
        },
        async capnhat(){
            let thongtinphieu=this.$refs.thongtinphieu.getThongTinPhieu()
            if(!(await this.KT_DuLieuNhap(thongtinphieu))){
                return
            }
            if(!this.$refs.thongtinphieu.phailam('GIAOVIEC') || this.cty_newline){
                await this.xoa_phieu(this.phieu_id)
                //tạo dữ liệu nhân viên
                let nhanvien_th=this.dulieu_nhanvien_th(thongtinphieu)
                await this.insert_giaophieu_nv(JSON.stringify(nhanvien_th))
            }
            //
            let result = await this.sp_update_tt_giaoviec({
                vnhanvien_hc_id: this.$root.token.getNhanVienID(),
                vngay_ht: thongtinphieu.ngay_ht,
                vhdtb_id: this.hdtb_id,
                vphieu_id: this.phieu_id,
                vnhanvien_gv_id: thongtinphieu.nguoi_gv_selected
            })
            if(result){
                this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
                this.$refs.girdPhieu.flagSelectedRowIndexes=[this.selectIndex]
                await this.hienthi_danhsach()
            }else{
                this.$toast.error('Đã xảy ra lỗi, cập nhật dữ liệu không thành công')
            }
        },
        //KT_DuLieuNhap
        async KT_DuLieuNhap(thongtinphieu){
            if(this.dataSources.length==0){
                this.$toast.error('Không có dữ liệu để Cập nhật !')
                return false
            }
            if(!thongtinphieu.checkBoxs.ngay_gv){
                this.$toast.error('Hãy chọn ngày giao việc !')
                return false
            }
            if(!thongtinphieu.checkBoxs.ngay_ht){
                this.$toast.error('Hãy nhập ngày hoàn thành!')
                return false
            }
            if(this.$refs.thongtinphieu.phailam('GIAOVIEC') && !this.cty_newline){
                if(thongtinphieu.ds_nhanvien_th.length<=0){
                    this.$toast.error('Hãy nhập nhân viên thực hiện  !')
                    return false
                }
            }
            if(moment(thongtinphieu.ngay_ht,'DD/MM/YYYY HH:mm:ss').isBefore(moment(thongtinphieu.ngay_gv,'DD/MM/YYYY HH:mm:ss'))){
                this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
                return false
            }
            let ngay_sys = this.rowSelected.thangnam_sy!=null?moment(this.rowSelected.thangnam_sy,'DD/MM/YYYY').format('DD/MM/YYYY'):moment(new Date()).format('DD/MM/YYYY')
            let ngay_tt = this.rowSelected.thangnam_tt!=null?moment(this.rowSelected.thangnam_tt,'DD/MM/YYYY').format('DD/MM/YYYY'):null
            let ngay_ht = moment(thongtinphieu.ngay_ht,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
            if(this.loaitb_id==LoaiHinhTB.INTERNET_FIBER_TINH_KHAC||this.loaitb_id==LoaiHinhTB.INTERNET_ADSL_TINH_KHAC||
                thongtinphieu.dichvu_vt_selected==DichVuVienThong.ANTOAN_BAOMAT_TT||thongtinphieu.dichvu_vt_selected==DichVuVienThong.DICHVU_CNTT||
                thongtinphieu.dichvu_vt_selected==DichVuVienThong.TRUNGTAM_DULIEU){
                    if(Number(moment(thongtinphieu.ngay_ht,'DD/MM/YYYY HH:mm:ss').format('YYYYMM'))<Number(moment(new Date()).format('YYYYMM'))){
                        this.$toast.error('Tháng hoàn thành không được nhỏ hơn tháng hiện tại !')
                        return false
                    }
                    //Nếu ngày thanh toán > ngày hoàn thành
                    if(!this.KiemTraDK_HoanThanh_1(ngay_ht, ngay_sys)){
                        this.$toast.error('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
                        return false
                    }
            }else{
                if(ngay_tt!=null&&ngay_ht!=null&&!this.KiemTraDK_HoanThanh_1(ngay_tt, ngay_ht)){
                    //Nếu ngày thanh toán > ngày hoàn thành
                    this.$toast.error('Thuê bao '+this.rowSelected.ma_tb+ ' có ngày thanh toán: '+ngay_tt+'\n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán')
                    return false
                }
                if(ngay_sys!=null&&ngay_ht!=null&&!this.KiemTraDK_HoanThanh_1(ngay_ht, ngay_sys)){
                    //Nếu ngày thanh toán > ngày hoàn thành
                    this.$toast.error('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
                    return false
                }
            }
            
            let ngay_gv = moment(thongtinphieu.ngay_gv,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
            if(!this.KiemTraDK_HoanThanh_1(ngay_gv, ngay_sys)){
                //Nếu ngày giao viêc > ngày hiện tại
                this.$toast.error('Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại')
                return false
            }
            //end
            return true
        },
        //dd/MM/yyyy
        KiemTraDK_HoanThanh_1(thangnam_tt, thangnam_sys){
            console.log('thangnam_tt', thangnam_tt)
            console.log('thangnam_sys', thangnam_sys)
            let m_thangnam_tt=moment(thangnam_tt,'DD/MM/YYYY')
            let m_thangnam_sys=moment(thangnam_sys,'DD/MM/YYYY')
            if(Number(m_thangnam_tt.format('YYYY'))<Number(m_thangnam_sys.format('YYYY'))){
                return true
            }else if(Number(m_thangnam_tt.format('YYYY'))==Number(m_thangnam_sys.format('YYYY'))){
                if(Number(m_thangnam_tt.format('M'))<Number(m_thangnam_sys.format('M'))){
                    return true
                }else if(Number(m_thangnam_tt.format('M'))==Number(m_thangnam_sys.format('M'))){
                    if(Number(m_thangnam_tt.format('D'))<=Number(m_thangnam_sys.format('D'))){
                        return true
                    }else{
                        return false
                    }
                }else{
                    return false
                }
            }else {
                return false
            }
        },
        dulieu_nhanvien_th(thongtinphieu){
            let data=[]
            var object={}
            object.phieu_id=this.phieu_id
            object.nhanvien_giao_id=this.$root.token.getNhanVienID()
            object.nhanvien_th_id=this.$root.token.getNhanVienID()
            object.ngaygiao=thongtinphieu.ngay_gv
            object.nhiemvu='Hoàn thiện hồ sơ'
            object.ghichu=''
            object.so_dt=''
            //không có dùng
            object.status=0
            object.nhiemvu_id=0
            object.kqxl_id=''
            data.push(object)
            return data
        },
        changeLoaiPhieu(event){
            this.clearDuLieu()
            this.loaiphieu=event.target.value 
            this.hienthi_danhsach()
        },
        changeDichVu(dichvuvt_id){
            this.dichvuvt_selected=dichvuvt_id
        },
        async kichhoat1(){
            this.$bvModal
            .msgBoxConfirm("Bạn có muốn cập nhật thông số port này lên Visa ?", {
            title: "Xác nhận",
            size: "sm",
            okTitle: "Có",
            cancelTitle: "Không",
            })
            .then(async (v) => {
            if (v) {
                this.loading(true);
                await this.kichhoat()
                this.loading(false);
            }
            })
            .catch((e) => {
            console.log(e);
            this.loading(false);
            });

        },
        kichhoat(){
            let thongtinphieu=this.$refs.thongtinphieu.getThongTinPhieu()
            let data={
                ngay_ht_gp: this.rowSelected.ngay_ht_gp!=null?this.rowSelected.ngay_ht_gp:'',
                hdtb_id: this.hdtb_id,
                loaitb_id: this.loaitb_id,
                kieuld_id: this.kieuld_id,
                ma_tb: this.rowSelected.ma_tb!=null?this.rowSelected.ma_tb:'',
                tocdo_id: this.rowSelected.tocdo_id!=null?this.rowSelected.tocdo_id:'',
                ip: this.rowSelected.ip!=null?this.rowSelected.ip:'',
                diachi_ip: thongtinphieu.input_ipv4_lan!=null?thongtinphieu.input_ipv4_lan.trim():'',
                ipv4_wan: thongtinphieu.input_ipv4_wan!=null?thongtinphieu.input_ipv4_wan.trim():'',
                ipv6_lan: thongtinphieu.input_ipv6_lan!=null?thongtinphieu.input_ipv6_lan.trim():'',
                muccuoc_id: this.rowSelected.muccuoc_id!=null?this.rowSelected.muccuoc_id:0,
                tucthi: this.rowSelected.tucthi!=null?this.rowSelected.tucthi.toString():'',
                dslam_id: thongtinphieu.dslam_selected!=null?thongtinphieu.dslam_selected:0,
                tocdo_cu_id: thongtinphieu.tocdocu_selected!=null?thongtinphieu.tocdocu_selected:0,
                thuebao_id: this.thuebao_id,
                tocdo_moi_id: thongtinphieu.tocdomoi_selected!=null?thongtinphieu.tocdomoi_selected:0,
                port: thongtinphieu.input_port!=null?thongtinphieu.input_port:'',
                vci: thongtinphieu.input_vci!=null?thongtinphieu.input_vci:'',
                vpi: thongtinphieu.input_vpi!=null?thongtinphieu.input_vpi:'',
                slot: thongtinphieu.input_slot!=null?thongtinphieu.input_slot:'',
                brasId: thongtinphieu.bras_selected!=null?thongtinphieu.bras_selected:0,
                kieutraid: this.rowSelected.kieutra_id!=null?this.rowSelected.kieutra_id:0,
                tenthangtra: this.rowSelected.tenthang!=null?this.rowSelected.tenthang:'',
                ngaytra: this.rowSelected.ngaytra!=null?this.rowSelected.ngaytra:'',
                port_id: this.port_id,
                system: thongtinphieu.input_system!=null?thongtinphieu.input_system:'',
                rack: thongtinphieu.input_rank!=null?thongtinphieu.input_rank.trim():'',
                chk_giuip: thongtinphieu.checkGiuIP.check,
                chk_tudong: thongtinphieu.checkTuDong.check,
                shelf:thongtinphieu.input_sheft,
                self01:thongtinphieu.input_self,
                self02:thongtinphieu.input_olt_slot,
                self03:thongtinphieu.input_olt_port,
                self04:thongtinphieu.input_onuid
            }
            this.kichhoat_thaydoi_tocdo(data)
        },
        async hoancong(){
            let thongtinphieu=this.$refs.thongtinphieu.getThongTinPhieu()
            if(!(await this.KiemTra_DL(thongtinphieu))){
                return
            }
            //
            let data={
                clngay_kh: this.rowSelected.ngay_kh!=null?this.rowSelected.ngay_kh:'',
                dichvuvt_id: thongtinphieu.dichvu_vt_selected,
                donvi_giao_id: this.$root.token.getDonViID(),
                hdtb_id: this.hdtb_id,
                huonggiao_id: this.huonggiao_id,
                loaihinhtb_id: this.loaitb_id,
                luong_id: this.luong_id,
                ngay_ht: moment(thongtinphieu.ngay_ht,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'),
                ngay_tt: this.rowSelected.thangnam_tt,
                phieu_id: this.phieu_id,
                tucthi: this.rowSelected.tucthi
            }
            let result = await this.fn_hoancong_hths_thaydoi_tocdo(data)
            if(result.code==1){
                this.$toast.success(result.message)
                await this.hienthi_danhsach()
            }else{
                this.$toast.error(result.message)
            }
            
        },
        async hoanthien_hoso(){
            let thongtinphieu=this.$refs.thongtinphieu.getThongTinPhieu()
            if(!(await this.KiemTra_DL(thongtinphieu))){
                return
            }
            let data={
                dichvvt_id: thongtinphieu.dichvu_vt_selected,
                donvi_giao_id: this.$root.token.getDonViID(),
                hdtb_id: this.hdtb_id,
                kieu_ngay: "0",
                loaitb_id: this.loaitb_id,
                luong_id: this.luong_id,
                matb: this.rowSelected.ma_tb!=null?this.rowSelected.ma_tb:'',
                ngay_ht: moment(thongtinphieu.ngay_ht,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'),
                ngay_tt: this.rowSelected.thangnam_tt,
                phieu_id: this.phieu_id,
                tucthi: this.rowSelected.tucthi
            }
            let result = await this.fn_hths_thaydoi_tocdo(data)
            if(result.code==1){
                this.$toast.success(result.message)
                await this.hienthi_danhsach()
            }else{
                this.$toast.error(result.message)
            }
            
        },
        KiemTra_DL(thongtinphieu){
            if(this.dataSources.length<=0){
                this.$toast.error('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
                return false
            }
            if(thongtinphieu.nguoi_gv_selected==null){
                this.$toast.error('Hãy nhập người giao việc')
                return false
            }
            if(!thongtinphieu.checkBoxs.ngay_gv||thongtinphieu.ngay_gv==null||thongtinphieu.ngay_gv==''){
                this.$toast.error('Hãy nhập ngày giao việc!')
                return false
            }
            if(!thongtinphieu.checkBoxs.ngay_ht||thongtinphieu.ngay_ht==null||thongtinphieu.ngay_ht==''){
                 this.$toast.error('Hãy nhập ngày hoàn thành!')
                 return false
            }
            if(thongtinphieu.ds_nhanvien_th.length<=0){
                 this.$toast.error('Hãy nhập nhân viên thực hiện!')
                 return false
            }
            return true
        },
        async successChuyenTo(){
            this.$refs.girdPhieu.flagSelectedRowIndexes=[this.selectIndex]
            await this.hienthi_danhsach()
        },
        async traphieu_success(value){
            await this.hienthi_danhsach()
        },
        callPort(data){
            this.port_id=data.port_id
            this.vci_vpi_id=data.vci_vpi_id
            this.congnghe_id=data.congnghe_id
            console.log("this.congnghe_id1",this.congnghe_id)
           
        },
        callbackHienThiDanhSach(){
            this.hienthi_danhsach()
        },
        async capNhatPort(){
            var vport_id = 0
            var vvci_vpi_id = 0
            let hd_adsl=await this.lay_ds_hdtb_adsl_theo_hdtb_id(this.hdtb_id)
            if(hd_adsl.length>0){
                vport_id=hd_adsl[0].port_id!=null?Number(hd_adsl[0].port_id):0
                vvci_vpi_id=hd_adsl[0].vci_vpi_id!=null?Number(hd_adsl[0].vci_vpi_id):0
            }
            if(vport_id!=0){
                //Có sự thay đổi port mới update trạng thái
                if (vport_id != this.port_id || vvci_vpi_id != this.vci_vpi_id){
                    //#region Cập nhật port cũ
                    //txtMaTB.Text.Trim(), vport_id, vvci_vpi_id, 1
                    let dsktradb=await this.sp_ktra_dk_port({
                        kieu: 1,
                        ma_tb: this.$refs.thongtinphieu.input_account.trim(),
                        port_id: vport_id,
                        vci_vpi_id: vvci_vpi_id
                    })
                    let dsktrahd=await this.sp_ktra_dk_port({
                        kieu: 2,
                        ma_tb: this.$refs.thongtinphieu.input_account.trim(),
                        port_id: vport_id,
                        vci_vpi_id: vvci_vpi_id
                    })
                    if(dsktradb.length==0&&dsktrahd.length==0){
                        //fn_capnhat_trangthai_port fn_capnhat_trangthai_portid fn_capnhat_ds_matb
                        await this.fn_capnhat_trangthai_port({
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id,
                            trangthai:TRANGTHAI_PORT.CHUA_SD
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:vport_id,
                            tt_port_id:TRANGTHAI_PORT.CHUA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:"",
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: vport_id,
                            trangthai: TRANGTHAI_PORT.CHUA_SD
                        })
                    }else if(dsktradb.length!=0&&dsktrahd.length==0){
                        var vds_matb=''
                        for(let i=0;i<dsktradb.length;i++){
                            vds_matb=vds_matb+dsktradb[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        if(vds_matb!=''){
                            vds_matb=vds_matb.substring(0, vds_matb.length-2)
                        }
                        await this.fn_capnhat_trangthai_port({
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id,
                            trangthai:TRANGTHAI_PORT.DA_SD
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:vport_id,
                            tt_port_id:TRANGTHAI_PORT.DA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:vds_matb,
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: vport_id,
                            trangthai: TRANGTHAI_PORT.DA_SD
                        })
                    }else if(dsktradb.length==0&&dsktrahd.length!=0){
                        var vds_matb=''
                        for(let i=0;i<dsktrahd.length;i++){
                            vds_matb=vds_matb+dsktrahd[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        if(vds_matb!=''){
                            vds_matb=vds_matb.substring(0, vds_matb.length-2)
                        }
                        await this.fn_capnhat_trangthai_port({
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id,
                            trangthai:TRANGTHAI_PORT.TAM_CAP
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:vport_id,
                            tt_port_id:TRANGTHAI_PORT.DA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:vds_matb,
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: vport_id,
                            trangthai: TRANGTHAI_PORT.DA_SD
                        })
                    }else if(dsktradb.length!=0&&dsktrahd.length!=0){
                        var vds_matb=''
                        for(let i=0;i<dsktradb.length;i++){
                            vds_matb=vds_matb+dsktradb[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        for(let i=0;i<dsktrahd.length;i++){
                            vds_matb=vds_matb+dsktrahd[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        if(vds_matb!=''){
                            vds_matb=vds_matb.substring(0, vds_matb.length-2)
                        }
                        await this.fn_capnhat_trangthai_port({
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id,
                            trangthai:TRANGTHAI_PORT.DA_SD
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:vport_id,
                            tt_port_id:TRANGTHAI_PORT.DA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:vds_matb,
                            port_id:vport_id,
                            vci_vpi_id:vvci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: vport_id,
                            trangthai: TRANGTHAI_PORT.DA_SD
                        })
                    }
                    
                    //#region Cập nhật port mới
                    let dsktradb_m=await this.sp_ktra_dk_port({
                        kieu: 1,
                        ma_tb: this.$refs.thongtinphieu.input_account.trim(),
                        port_id: this.port_id,
                        vci_vpi_id: this.vci_vpi_id
                    })
                    let dsktrahd_m=await this.sp_ktra_dk_port({
                        kieu: 2,
                        ma_tb: this.$refs.thongtinphieu.input_account.trim(),
                        port_id: this.port_id,
                        vci_vpi_id: this.vci_vpi_id
                    })
                    if(dsktradb_m.length==0&&dsktrahd_m.length==0){
                        await this.fn_capnhat_trangthai_port({
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id,
                            trangthai:TRANGTHAI_PORT.TAM_CAP
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:this.port_id,
                            tt_port_id:TRANGTHAI_PORT.DA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:this.$refs.thongtinphieu.input_account.trim(),
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: this.port_id,
                            trangthai: TRANGTHAI_PORT.DA_SD
                        })
                    }else if(dsktradb_m.length!=0&&dsktrahd_m.length==0){
                        var vds_matb=''
                        for(let i=0;i<dsktradb_m.length;i++){
                            vds_matb=vds_matb+dsktradb_m[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        vds_matb=vds_matb+this.$refs.thongtinphieu.input_account.trim()
                        await this.fn_capnhat_trangthai_port({
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id,
                            trangthai:TRANGTHAI_PORT.DA_SD
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:this.port_id,
                            tt_port_id:TRANGTHAI_PORT.DA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:vds_matb,
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: this.port_id,
                            trangthai: TRANGTHAI_PORT.DA_SD
                        })
                    }else if(dsktradb_m.length==0&&dsktrahd_m.length!=0){
                        var vds_matb=''
                        for(let i=0;i<dsktrahd_m.length;i++){
                            vds_matb=vds_matb+dsktrahd_m[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        vds_matb=vds_matb+this.$refs.thongtinphieu.input_account.trim()
                        await this.fn_capnhat_trangthai_port({
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id,
                            trangthai:TRANGTHAI_PORT.TAM_CAP
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:this.port_id,
                            tt_port_id:TRANGTHAI_PORT.DA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:vds_matb,
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: this.port_id,
                            trangthai: TRANGTHAI_PORT.DA_SD
                        })
                    }else if(dsktradb_m.length!=0&&dsktrahd_m.length!=0){
                        var vds_matb=''
                        for(let i=0;i<dsktradb_m.length;i++){
                            vds_matb=vds_matb+dsktradb_m[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        for(let i=0;i<dsktrahd_m.length;i++){
                            vds_matb=vds_matb+dsktrahd_m[i].ma_tb.toString()+'; '
                            if(vds_matb.length>3000){
                                break
                            }
                        }
                        
                        vds_matb=vds_matb+this.$refs.thongtinphieu.input_account.trim()
                        await this.fn_capnhat_trangthai_port({
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id,
                            trangthai:TRANGTHAI_PORT.DA_SD
                        })
                        await this.fn_capnhat_trangthai_portid({
                            port_id:this.port_id,
                            tt_port_id:TRANGTHAI_PORT.DA_SD,
                            may_cn:"localhost",
                            nguoi_cn:this.$root.token.getUserName()
                        })
                        await this.fn_capnhat_ds_matb({
                            ds_matb:vds_matb,
                            port_id:this.port_id,
                            vci_vpi_id:this.vci_vpi_id
                        })
                        await this.Capnhat_port_vatly({
                            port_id: this.port_id,
                            trangthai: TRANGTHAI_PORT.DA_SD
                        })
                    }
                }
            }else{
                //#region Cập nhật port mới
                //Kiểm tra port đang chọn
                let dsktradb_m=await this.sp_ktra_dk_port({
                    kieu: 1,
                    ma_tb: this.$refs.thongtinphieu.input_account.trim(),
                    port_id: this.port_id,
                    vci_vpi_id: this.vci_vpi_id
                })
                let dsktrahd_m=await this.sp_ktra_dk_port({
                    kieu: 2,
                    ma_tb: this.$refs.thongtinphieu.input_account.trim(),
                    port_id: this.port_id,
                    vci_vpi_id: this.vci_vpi_id
                })
                if(dsktradb_m.length==0&&dsktrahd_m.length==0){
                    await this.fn_capnhat_trangthai_port({
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id,
                        trangthai:TRANGTHAI_PORT.TAM_CAP
                    })
                    await this.fn_capnhat_trangthai_portid({
                        port_id:this.port_id,
                        tt_port_id:TRANGTHAI_PORT.DA_SD,
                        may_cn:"localhost",
                        nguoi_cn:this.$root.token.getUserName()
                    })
                    await this.fn_capnhat_ds_matb({
                        ds_matb:this.$refs.thongtinphieu.input_account.trim(),
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id
                    })
                    await this.Capnhat_port_vatly({
                        port_id: this.port_id,
                        trangthai: TRANGTHAI_PORT.DA_SD
                    })
                }else if(dsktradb_m.length!=0&&dsktrahd_m.length==0){
                    var vds_matb=''
                    for(let i=0;i<dsktradb_m.length;i++){
                        vds_matb=vds_matb+dsktradb_m[i].ma_tb.toString()+'; '
                        if(vds_matb.length>3000){
                            break
                        }
                    }
                    // if(vds_matb!=''){
                    //     vds_matb=vds_matb.substring(0, vds_matb.length-2)
                    // }
                    vds_matb=vds_matb+this.$refs.thongtinphieu.input_account.trim()
                    await this.fn_capnhat_trangthai_port({
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id,
                        trangthai:TRANGTHAI_PORT.DA_SD
                    })
                    await this.fn_capnhat_trangthai_portid({
                        port_id:this.port_id,
                        tt_port_id:TRANGTHAI_PORT.DA_SD,
                        may_cn:"localhost",
                        nguoi_cn:this.$root.token.getUserName()
                    })
                    await this.fn_capnhat_ds_matb({
                        ds_matb:vds_matb,
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id
                    })
                    await this.Capnhat_port_vatly({
                        port_id: this.port_id,
                        trangthai: TRANGTHAI_PORT.DA_SD
                    })
                }else if(dsktradb_m.length==0&&dsktrahd_m.length!=0){
                    var vds_matb=''
                    for(let i=0;i<dsktrahd_m.length;i++){
                        vds_matb=vds_matb+dsktrahd_m[i].ma_tb.toString()+'; '
                        if(vds_matb.length>3000){
                            break
                        }
                    }
                    // if(vds_matb!=''){
                    //     vds_matb=vds_matb.substring(0, vds_matb.length-2)
                    // }
                    vds_matb=vds_matb+this.$refs.thongtinphieu.input_account.trim()
                    await this.fn_capnhat_trangthai_port({
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id,
                        trangthai:TRANGTHAI_PORT.TAM_CAP
                    })
                    await this.fn_capnhat_trangthai_portid({
                        port_id:this.port_id,
                        tt_port_id:TRANGTHAI_PORT.DA_SD,
                        may_cn:"localhost",
                        nguoi_cn:this.$root.token.getUserName()
                    })
                    await this.fn_capnhat_ds_matb({
                        ds_matb:vds_matb,
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id
                    })
                    await this.Capnhat_port_vatly({
                        port_id: this.port_id,
                        trangthai: TRANGTHAI_PORT.DA_SD
                    })
                }else if(dsktradb_m.length!=0&&dsktrahd_m.length!=0){
                    var vds_matb=''
                    for(let i=0;i<dsktradb_m.length;i++){
                        vds_matb=vds_matb+dsktradb_m[i].ma_tb.toString()+'; '
                        if(vds_matb.length>3000){
                            break
                        }
                    }
                    for(let i=0;i<dsktrahd_m.length;i++){
                        vds_matb=vds_matb+dsktrahd_m[i].ma_tb.toString()+'; '
                        if(vds_matb.length>3000){
                            break
                        }
                    }
                    // if(vds_matb!=''){
                    //     vds_matb=vds_matb.substring(0, vds_matb.length-2)
                    // }
                    vds_matb=vds_matb+this.$refs.thongtinphieu.input_account.trim()
                    await this.fn_capnhat_trangthai_port({
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id,
                        trangthai:TRANGTHAI_PORT.DA_SD
                    })
                    await this.fn_capnhat_trangthai_portid({
                        port_id:this.port_id,
                        tt_port_id:TRANGTHAI_PORT.DA_SD,
                        may_cn:"localhost",
                        nguoi_cn:this.$root.token.getUserName()
                    })
                    await this.fn_capnhat_ds_matb({
                        ds_matb:vds_matb,
                        port_id:this.port_id,
                        vci_vpi_id:this.vci_vpi_id
                    })
                    await this.Capnhat_port_vatly({
                        port_id: this.port_id,
                        trangthai: TRANGTHAI_PORT.DA_SD
                    })
                }
            }
            //
            //lay_dslam_theo_port_id
            let dsdslam=await this.lay_dslam_theo_port_id(this.port_id)
            if(dsdslam.length>0){
                this.tramtb_id=dsdslam[0].donvi_id!=null?dsdslam[0].donvi_id:0
                if (this.congnghe_id === 0 || this.congnghe_id === undefined)
                    this.congnghe_id=dsdslam[0].congnghe_id!=null?dsdslam[0].congnghe_id:0
            }
            let resultUpdatePort=await this.capnhat_thongtin_port_int_second({
                vhdtb_id: this.hdtb_id,
                vport_id: this.port_id,
                vvci_vpi_id: this.vci_vpi_id,
                vbras_id: this.$refs.thongtinphieu.bras_selected!=null?this.$refs.thongtinphieu.bras_selected:0,
                vdslam_id: this.$refs.thongtinphieu.dslam_selected!=null?this.$refs.thongtinphieu.dslam_selected:0,
                vpassword: this.$refs.thongtinphieu.input_matkhau,
                vma_lt: '',
                vip: this.$refs.thongtinphieu.input_ipv4_lan,
                vtramtb_id: this.tramtb_id,
                vcongnghe_id: this.congnghe_id
            })
            if(resultUpdatePort.code==1){
                this.$toast.success(resultUpdatePort.message)
                this.$refs.girdPhieu.flagSelectedRowIndexes=[this.selectIndex]
                await this.hienthi_danhsach()
            }else{
                this.$toast.error(resultUpdatePort.message)
            }
        },
        async capNhatNDTH(noidung){
            if(this.phieu_id==0){
                return
            }
            let result = await this.capnhat_noidung_thuchien(this.phieu_id, noidung)
            if(result.code==1){
                this.$toast.success(result.message)
                this.$refs.girdPhieu.flagSelectedRowIndexes=[this.selectIndex]
                this.hienthi_danhsach()
                //focus row
            }else{
                this.$toast.error(result.message)
                this.$refs.thongtinphieu.focusNDTH()
            }
        },
        //onSelectedRow
        async onSelectedRow(data){
            if(!data){
                return
            }
            let item=Array.isArray(data)?data[0]:data
            console.log('onSelectedRow',item)
            this.rowSelected=item
            this.port_id = 0
            this.vci_vpi_id = 0
            this.in_tocdothuc_id=-1
            //
            this.hdtb_id=item.hdtb_id
            this.hdkh_id=item.hdkh_id
            this.dichvuvt_id=item.dichvuvt_id
            this.loaitb_id=item.loaitb_id
            this.kieuld_id=item.kieuld_id
            this.phieu_id=item.phieu_id
            //lấy tổng tiền
            let ds_tien=await this.lay_tongtien_theo_hdkh_id(this.hdkh_id)
            if(ds_tien.length>0){
                this.input_tongtien=ds_tien[0].tongtien.toString()
            }else{
                this.input_tongtien='0'
            }
            this.tramtb_id=0
            if(item.tramtb_id!=null&&item.tramtb_id.toString()!=''){
                this.tramtb_id=Number(item.tramtb_id)
            }else{
                this.tramtb_id=0
            }
            if(item.tocdothuc_id!=null&&item.tocdothuc_id.toString()!=''){
                this.in_tocdothuc_id=Number(item.tocdothuc_id)
            }else{
                this.in_tocdothuc_id=-1
            }
            this.thuebao_id=item.thuebao_id!=null?Number(item.thuebao_id):0
            //map khi lấy đưuocj dữ liệu -->loaitb_id
            //dùng bị trigger notifi dataGrid 
            // if(this.loaitb_id==LoaiHinhTB.INTERNET_1260||this.loaitb_id==LoaiHinhTB.INTERNET_ADSL||this.loaitb_id==LoaiHinhTB.INTERNET_FTTH){
            //     this.columns[this.columns.findIndex(x=>x.fieldName=='kichhoatvisa')].headerText='Kích hoạt Visa'
            // }else if(this.loaitb_id==LoaiHinhTB.INTERNET_MYTV){
            //     this.columns[this.columns.findIndex(x=>x.fieldName=='kichhoatvisa')].headerText='Kích hoạt Vasc'
            // }else if(this.loaitb_id==LoaiHinhTB.INTERNET_SGTV){
            //     this.columns[this.columns.findIndex(x=>x.fieldName=='kichhoatvisa')].headerText='Kích hoạt SaiGonTV'
            // }else{
            //     this.columns[this.columns.findIndex(x=>x.fieldName=='kichhoatvisa')].headerText='Kích hoạt'
            // }
            //init compoent ThongTinPhieu
            await this.$refs.thongtinphieu.onSelectedRow(item)
            // this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=true
            // this.actions[this.actions.findIndex(x=>x.id=='btnPhieuGiaoSai')].active=true
            // this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=true
            // this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=true
            // this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=true
            // Fix theo yeu cau chi Phuong
            if (item.dichvuvt_id == 4){         
                if (item.status == '0'){       
                    this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=true
                    this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=false
                    //this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=false
                }
                else {
                    this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=false
                    this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=true
                    //this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=true
                }
            } else {                
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=true
                //this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=true
            }
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=true
            this.actions[this.actions.findIndex(x=>x.id=='btnPhieuGiaoSai')].active=true
        },
        async onEnterMaTB(input){
            this.dataSources=[]
            this.dataSourceTK=[]
            let thongtinphieu=this.$refs.thongtinphieu.getThongTinPhieu()
            //LoaiHopDong.THAY_DOI_TOCDO_ADSL,=8
            let ds=await this.lay_ds_hopdong_huonggiao({
                in_tthd_id: this.tthd,
                in_huonggiao_id: this.huonggiao_id,  
                in_dichvuvt_id: thongtinphieu.dichvu_vt_selected,
                in_loai_id: this.loaiphieu,
                in_loaihd_id: this.loai_hd,
                in_ma_tb: input
            })
            if(ds.length>0){
                this.dataSources=ds
                this.dataSourceTK=ds
                this.rowFocusIndex=0
            }else{
                this.phieu_id=0
                this.clearDuLieu()
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='btnPhieuGiaoSai')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=false
                this.dataSourceTK=ds
                this.dataSources=[]
            }
        },
        async callbackHuongGiaoTheoQuyTrinh(huonggiao){
            if(huonggiao.length>0){
                this.luong_id=huonggiao[0].luong_id
                this.huonggiao_id=huonggiao[0].huonggiao_id
                this.ten_huonggiao=huonggiao[0].huonggiao
                this.header.title=this.ten_huonggiao
            }else{
                this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
                this.huonggiao_id=-1
                this.hienthi_danhsach()
                return
            }
            // HienThiGiaoDien(luong_id); thực hiện form ThongTinPhieu callback
            this.hienthi_danhsach()
        },
        callbackStatusActionTop(data){
            this.actions[this.actions.findIndex(x=>x.id==data.action_id)].active=data.value
        },
        callbackVisibleActionTop(data){
            this.actions[this.actions.findIndex(x=>x.id==data.action_id)].visible=data.value
        },
        loadControl(ds_control){
            for(let i=0;i<ds_control.length;i++){
                let control=ds_control[i]
                const index=this.actions.findIndex(x=>x.id==control.control_name)
                if(index>-1){
                    if(control.enable==1){
                        this.actions[index].visible=true
                    }else{
                        this.actions[index].visible=false
                    }
                }
            }
        },
        
        async hienthi_danhsach(){
            //LoaiHopDong.THAY_DOI_TOCDO_ADSL
            let thongtinphieu=this.$refs.thongtinphieu.getThongTinPhieu()
            if (this.DS_CHUQUAN_THEO_QUYEN == null || this.DS_CHUQUAN_THEO_QUYEN.length == 0) {
                // DeclareServiceSpeedITSecureAPI.lay_ds_hopdong_huonggiao(this.axios, {
                //     in_tthd_id: this.tthd,
                //     in_huonggiao_id: this.huonggiao_id,  
                //     in_dichvuvt_id: thongtinphieu.dichvu_vt_selected,
                //     in_loai_id: this.loaiphieu,
                //     in_loaihd_id: this.loai_hd,
                //     in_ma_tb: "0"
                // }).then(res => {
                // this.dataSources = res.data.data
                // })
                this.dataSources=await this.lay_ds_hopdong_huonggiao({
                    in_tthd_id: this.tthd,
                    in_huonggiao_id: this.huonggiao_id,  
                    in_dichvuvt_id: thongtinphieu.dichvu_vt_selected,
                    in_loai_id: this.loaiphieu,
                    in_loaihd_id: this.loai_hd,
                    in_ma_tb: "0"
                })
            } else {
                this.dataSources=await this.lay_ds_hopdong_huonggiao_chuquan({
                    "in_phanvung_id": null,
                    "in_dichvuvt_id": thongtinphieu.dichvu_vt_selected,
                    "in_huonggiao_id": this.huonggiao_id,
                    "in_loaihd_id": this.loai_hd,
                    "in_tthd_id": this.tthd,
                    "in_nhanvien_id": this.$root.token.getNhanVienID(),
                    "in_ma_tb": "0",
                    "in_loai_id": this.loaiphieu,
                    "vds_chuquan_theo_quyen": `(${this.DS_CHUQUAN_THEO_QUYEN})`
                })
                // DeclareServiceSpeedITSecureAPI.lay_ds_hopdong_huonggiao_chuquan(this.axios, {
                //     "in_phanvung_id": null,
                //     "in_dichvuvt_id": thongtinphieu.dichvu_vt_selected,
                //     "in_huonggiao_id": this.huonggiao_id,
                //     "in_loaihd_id": this.loai_hd,
                //     "in_tthd_id": LoaiHopDong.THAY_DOI_TOCDO_ADSL,
                //     "in_nhanvien_id": 0,
                //     "in_ma_tb": "0",
                //     "in_loai_id": this.loaiphieu,
                //     "vds_chuquan_theo_quyen": this.DS_CHUQUAN_THEO_QUYEN
                // }).then(res => {
                //     this.dataSources = res.data.data
                // })
            }
            // this.dataSources=await this.lay_ds_hopdong_huonggiao({
            //     in_tthd_id: this.tthd,
            //     in_huonggiao_id: this.huonggiao_id,  
            //     in_dichvuvt_id: thongtinphieu.dichvu_vt_selected,
            //     in_loai_id: this.loaiphieu,
            //     in_loaihd_id: this.loai_hd,
            //     in_ma_tb: "0"
            // })
            if(this.dataSources.length<=0){
                this.phieu_id=0
                this.clearDuLieu()
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='btnPhieuGiaoSai')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=false
                this.dataSources=[]
            }
            
        },
        async lay_ds_hopdong_huonggiao(data){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.lay_ds_hopdong_huonggiao(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                    // return response.data.data.map((x, index)=>{
                    //     if(this.loaitb_id==LoaiHinhTB.INTERNET_1260||this.loaitb_id==LoaiHinhTB.INTERNET_ADSL||this.loaitb_id==LoaiHinhTB.INTERNET_FTTH){
                    //         return Object.assign(x,{
                    //             id:index+1,
                    //             kichhoatvisa:'Kích hoạt Visa',
                    //             ngay_kh:x.ngay_kh!=null?moment(x.ngay_kh, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):'',
                    //             ngay_ht:x.ngay_ht!=null?moment(x.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
                    //         })
                    //     }else if(this.loaitb_id==LoaiHinhTB.INTERNET_MYTV){
                    //         return Object.assign(x,{
                    //             id:index+1,
                    //             kichhoatvisa:'Kích hoạt Vasc',
                    //             ngay_kh:x.ngay_kh!=null?moment(x.ngay_kh, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):'',
                    //             ngay_ht:x.ngay_ht!=null?moment(x.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
                    //         })
                    //     }else if(this.loaitb_id==LoaiHinhTB.INTERNET_SGTV){
                    //         return Object.assign(x,{
                    //             id:index+1,
                    //             kichhoatvisa:'Kích hoạt SaiGonTV',
                    //             ngay_kh:x.ngay_kh!=null?moment(x.ngay_kh, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):'',
                    //             ngay_ht:x.ngay_ht!=null?moment(x.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
                    //         })
                    //     }
                    //     return Object.assign(x,{
                    //         id:index+1,
                    //         ngay_kh:x.ngay_kh!=null?moment(x.ngay_kh, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):'',
                    //         ngay_ht:x.ngay_ht!=null?moment(x.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
                    //     })
                    // })
                    //return data.concat(data)
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_tongtien_theo_hdkh_id(hdkh_id){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.lay_tongtien_theo_hdkh_id(this.axios, hdkh_id)
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
        async xoa_phieu(phieu_id){
            try{
                this.loading(true)
                await DeclareServiceSpeedITSecureAPI.xoa_phieu(this.axios, phieu_id)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async insert_giaophieu_nv(data){
            try{
                this.loading(true)
                await DeclareServiceSpeedITSecureAPI.insert_giaophieu_nv(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async sp_update_tt_giaoviec(data){
            try{
                this.loading(true)
                let response=await DeclareServiceSpeedITSecureAPI.sp_update_tt_giaoviec(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    return false
                }
                
            }catch(e){
                this.loading(false)
                return false
            }
        },
        async fn_tt_hd_thuebao(hdtb_id, defaultValue){
            try{
                this.loading(true)
                let response=await DeclareServiceSpeedITSecureAPI.fn_tt_hd_thuebao(this.axios, hdtb_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return response.data.data
                }else{
                    return defaultValue
                }
                
            }catch(e){
                this.loading(false)
                return defaultValue
            }
        },
        async kichhoat_thaydoi_tocdo(data){
            try{
                this.loading(true)
                let response=await DeclareServiceSpeedITSecureAPI.kichhoat_thaydoi_tocdo(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Kích hoạt thành công!')
                    this.$refs.girdPhieu.flagSelectedRowIndexes=[this.selectIndex]
                    this.hienthi_danhsach()
                }else{
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi xảy ra')
                    }
                }
            }catch(e){
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra')
                }
                this.loading(false)
                return []
            }
        },
        async lay_huonggiao_theo_luong_id(luong_id){
            try{
                this.loading(true)
                let response=await DeclareServiceSpeedITSecureAPI.lay_huonggiao_theo_luong_id(this.axios, luong_id)
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
        async capnhat_noidung_thuchien(phieuId, noiDung){
            try{
                this.loading(true)
                let response=await DeclareServiceSpeedITSecureAPI.capnhat_noidung_thuchien(this.axios, phieuId, noiDung)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return {
                        code:1,
                        message:'Cập nhật thành công!'
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
                            message:'Đã xảy ra lỗi, cập nhật không thành công!'
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
                        message:'Đã xảy ra lỗi, cập nhật không thành công!'
                    }
                }
            }
        },
        async sp_lay_ds_phieuvt_hdbh(data){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.sp_lay_ds_phieuvt_hdbh(this.axios, data)
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
        async lay_ds_hdtb_adsl_theo_hdtb_id(hdtb_id){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.lay_ds_hdtb_adsl_theo_hdtb_id(this.axios, hdtb_id)
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
        async lay_dslam_theo_port_id(portId){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.lay_dslam_theo_port_id(this.axios, portId)
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
        async sp_ktra_dk_port(data){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.sp_ktra_dk_port(this.axios, data)
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
        async lay_ds_hopdong_huonggiao_chuquan(data){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.lay_ds_hopdong_huonggiao_chuquan(this.axios, data)
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
        
        async capnhat_thongtin_port_int_second(data){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.capnhat_thongtin_port_int_second(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='1'){
                        return {
                            code:1,
                            message:'Cập nhật thông tin port thành công'
                        }
                    }else{
                        return {
                            code:0,
                            message:response.data.data
                        }
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
                            message:'Đã xảy ra lỗi, cập nhật thông tin port không thành công!'
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
                        message:'Đã xảy ra lỗi, cập nhật thông tin port không thành công!'
                    }
                }
            }
        },
        async fn_capnhat_trangthai_port(data){
            try{
                this.loading(true)
                await DeclareServiceSpeedITSecureAPI.fn_capnhat_trangthai_port(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async fn_capnhat_trangthai_portid(data){
            try{
                this.loading(true)
                await DeclareServiceSpeedITSecureAPI.fn_capnhat_trangthai_portid(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async fn_capnhat_ds_matb(data){
            try{
                this.loading(true)
                await DeclareServiceSpeedITSecureAPI.fn_capnhat_ds_matb(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async Capnhat_port_vatly(data){
            try{
                this.loading(true)
                await DeclareServiceSpeedITSecureAPI.Capnhat_port_vatly(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async fn_hths_thaydoi_tocdo(data){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.fn_hths_thaydoi_tocdo(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=response.data.data
                    //hard do db, api trả về error_code không rõ ràng khi success
                    if(result&&result=='Hoàn thiện hồ sơ thành công !'){
                        return {
                            code:1,
                            message:'Hoàn thiện hồ sơ thành công !'
                        }
                    }else{
                        return {
                            code:0,
                            message:result
                        }
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
                            message:'Đã xảy ra lỗi, hoàn thiện hồ sơ không thành công !'
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
                        message:'Đã xảy ra lỗi, hoàn thiện hồ sơ không thành công !'
                    }
                }
            }
        },
        async fn_hoancong_hths_thaydoi_tocdo(data){
            try{
                this.loading(true)
                let response = await DeclareServiceSpeedITSecureAPI.fn_hoancong_hths_thaydoi_tocdo(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result=response.data.data
                    return {
                        code:1,
                        message:result
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
                            message:'Đã xảy ra lỗi, hoàn công không thành công !'
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
                        message:'Đã xảy ra lỗi, hoàn công không thành công !'
                    }
                }
            }
        },
        breadcrumbInit(){
            let menus=this.$root.context.getMenu()
            let endPath=this.$router.currentRoute.path.split('/').pop()
            const menu=menus.find(x=>x.url&&x.url.includes(endPath))
            if(menu){
                var listMenu=[]
                listMenu.push(menu)
                var menuPid=menus.find(x=>menu.p_id&&x.id==menu.p_id)
                while(menuPid){
                    listMenu.unshift(menuPid)
                    menuPid=menus.find(x=>menuPid.p_id&&x.id==menuPid.p_id)
                }
                this.header.list=listMenu.map(x=>{
                    return {
                        name: x.name, 
                        link: { name: "Ui.cards" },
                        active: x.url&&x.url.includes(endPath)
                    }
                })
                console.log('listMenu', listMenu)
            }
        },
        async initDuLieu(){
            //Khởi tạo tham số
            //this.header.list=[]
            console.log('initDuLieu')
            console.log(this.$route.query.tag)
            let tag=this.$route.query.tag
            if(tag&&tag!=''){
                let words=tag.split('+')
                if(words.length>0){
                    this.tthd=words[0]
                }
                if(words.length>1){
                    this.dsloaihd_id=words[1]
                }
                if(words.length>2){
                    this.dsdichvuvt_id=words[2].replaceAll(';',',')
                }
                if (words.length >= 4)
                {
                    if (words[3] == "1")
                        this.cty_newline = true;
                }
                console.log(`cty_newline ${this.cty_newline}`)
            }
            this.DS_CHUQUAN_THEO_QUYEN = await this.$root.token.getDsQuyenChuquan()
            console.log('DS_CHUQUAN_THEO_QUYEN', this.DS_CHUQUAN_THEO_QUYEN)
            //Khởi tạo breadcrumb
            this.breadcrumbInit()
            //
            if(this.$root.token.getMaTinh()!='HNI'){
                this.columns.splice(this.columns.findIndex(x=>x.fieldName=='dv_nhan'), 1)
                this.columns.splice(this.columns.findIndex(x=>x.fieldName=='ten_kv'), 1)
                this.columns.splice(this.columns.findIndex(x=>x.fieldName=='giohen_tu'), 1)
                this.columns.splice(this.columns.findIndex(x=>x.fieldName=='giohen_den'), 1)
                this.actions[this.actions.findIndex(x=>x.id=='tsbtnChuyenTo')].visible=false
            }
            //init combox thông tin phiếu
            await this.$refs.thongtinphieu.initDuLieu()
            //
            
        },
        async clearDuLieu(){
            this.hdkh_id=0
            this.hdtb_id=0
            this.$refs.thongtinphieu.clearDuLieu()
            this.input_tongtien='0'
            this.selectIndex=0
            //tsbtnHoanThienHS.Enabled = false;
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanThienHS')].active=false
        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }
}
</script>
<style scoped>
.disabled { 
    pointer-events: none;
    color:lightgray;
    border-color:lightgray;
}
.btn-search{
    background: #007bff !important;
    color: white !important;
    width: 50px !important;
    height: 20px !important;
    padding-top: 0px !important;
    padding-bottom: 20px !important;
}
.btn-search:hover{
    background: #71b5ff !important;
}
.page-content{
    top: 100px !important;
}
</style>