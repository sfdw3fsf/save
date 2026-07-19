<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" @onActionClick="onActionClick" />
        <div class="page-content">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="legend-title">Thông tin giao dịch</div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Mã giao dịch</div>
                                    <div class="value">
                                        <input type="text" :value="thongtin_giaodich.input_ma_gd" @change="e=>thongtin_giaodich.input_ma_gd=e.target.value" class="form-control highlight fw6">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w100">Loại HĐ/PL</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="thongtin_giaodich.loaihd_selected" @change="changeLoaiHD" class="form-control">
                                                <option v-for="item in thongtin_giaodich.ds_loaihd" :key="item.ID" :value="item.ID">
                                                    {{item.NAME}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Nhân viên YC</div>
                            <div class="value">
                                <input type="text" :value="thongtin_giaodich.input_nhanvien_yc" @change="e=>thongtin_giaodich.input_nhanvien_yc=e.target.value" class="form-control highlight fw6">
                            </div>
                        </div>
                        <div class="legend-title mart20">Thông tin thực hiện</div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key">Ngày GV</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="thongtin_thuchien.checkNgayGV" class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <KDatePicker v-model="thongtin_thuchien.ngay_gv"
                                                :disabled="!thongtin_thuchien.checkNgayGV"
                                                type="datetime"
                                                format="DD/MM/YYYY HH:mm:ss"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">Ngày HT</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="thongtin_thuchien.checkNgayHT" class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <KDatePicker v-model="thongtin_thuchien.ngay_ht"
                                                :disabled="!thongtin_thuchien.checkNgayHT"
                                                type="datetime"
                                                format="DD/MM/YYYY HH:mm:ss"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">Kết quả khảo sát</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="thongtin_thuchien.kq_khaosat_selected" class="form-control">
                                                <option v-for="item in thongtin_thuchien.ds_kq_khaosat" :key="item.ID" :value="item.ID">
                                                    {{item.NAME}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="info-row">
                                    <div class="key">Nội dung TH</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn" @click="capNhatNDTH">
                                                <span class="nc-icon-glyph ui-1_edit-76 f16"></span>
                                            </button>
                                            <input type="text" :value="thongtin_thuchien.input_noidung_th" @change="e=>thongtin_thuchien.input_noidung_th=e.target.value" class="form-control ">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w100">Người giao việc</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_nguoi_gv">
                                            <select2 :settings="{ dropdownParent: $refs['ds_nguoi_gv'] }" ref="cboNguoiGV"
                                                v-model="thongtin_thuchien.nhanvien_thicong_selected" class="select2"
                                                :options="thongtin_thuchien.ds_nhanvien_thicong.map(e=> ({id: e.ID, text: e.NAME}))">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Nội dung giao</div>
                                    <div class="value">
                                        <input type="text" :value="thongtin_thuchien.input_noidung_giao" @change="e=>thongtin_thuchien.input_noidung_giao=e.target.value" class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Cự ly</div>
                                    <div class="value">
                                        <input type="text"  @keypress="isNumber($event)" :disabled="!enabledCuLy" :value="thongtin_thuchien.input_culy" @change="e=>thongtin_thuchien.input_culy=e.target.value" class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Lý do tồn</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_lydoton">
                                            <select2 :settings="{ dropdownParent: $refs['ds_lydoton'] }" ref="cboLyDoTon"
                                                v-model="thongtin_thuchien.lydoton_selected" class="select2"
                                                :disabled="!enabledLyDoTon"
                                                :options="thongtin_thuchien.ds_lydoton.map(e=> ({id: e.ID, text: e.NAME}))">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Chi tiết KQKS</div>
                            <div class="value">
                                <textarea type="text" placeholder="Tốc độ, giá tiền, cam kết quốc tế, yêu cầu khác,..." style="height: 70px;resize: none;" :value="thongtin_thuchien.input_ghichu" @change="e=>thongtin_thuchien.input_ghichu=e.target.value" class="form-control "></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key">Tên thuê bao</div>
                            <div class="value">
                                <input type="text" :value="thongtin_thuchien.input_ten_tb" @change="e=>thongtin_thuchien.input_ten_tb=e.target.value" class="form-control fw6">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Địa chỉ LĐ</div>
                            <div class="value">
                                <input type="text" :value="thongtin_thuchien.input_diachi_ld" @change="e=>thongtin_thuchien.input_diachi_ld=e.target.value" class="form-control fw6">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Ghi chú TB</div>
                            <div class="value">
                                <input type="text" :value="thongtin_thuchien.input_ghichu_tb" @change="e=>thongtin_thuchien.input_ghichu_tb=e.target.value" class="form-control fw6">
                            </div>
                        </div>
                        <div class="legend-title mart20">Danh sách nhân viên thực hiện</div>
                        <KDataGrid
                            :columns="columnsNV"
                            :dataSource="dataSourcesNV"
                            :enable-paging-server="false"
                            :allowPaging="false"
                            :showFilter="true"
                            panelDataHeight="200px"
                            :showColumnCheckbox="false"
                            :enabledSelectFirstRow="true"/>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    Danh sách thuê bao
                </div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columnsDsThueBao"
                    :dataSource="dataSourcesDsThueBao"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    @selectedItemsChanged="gridThueBaoChanged"
                />

            </div>
        </div>
        <Footer/>
        <!-- Modal -->
        <ThongTinHenKhachHangModal ref="thongTinHenKhachHangModal" :hdkh_id="hdkh_id" :quyenduoc_hen="1"/>
        <ExportDataModal ref="exportDataModal" :data="dataSourcesDsThueBao" file_name="danh-sach-thue-bao" />
        <GiaoPhieuNVModal ref="giaoPhieuNhanVienModal"
            :phieu_id="phieu_id"
            :huonggiao_id="0"
            :ngaygiao="thongtin_thuchien.ngay_gv"
            :nguoigv_id="thongtin_thuchien.nhanvien_thicong_selected"
            :donvi_id="donvi_id"
            :hdtb_id="hdtb_id"
            :ma_tb="ma_tb"
            @giaoviec_success="giaoviec_success"/>
        <ChuyenToModal ref="chuyenToModal"
            :baohong_id="0"
            :phieu_id="phieu_id"
            :hdtb_id="hdtb_id"
            @success="successChuyenTo"/>
        <InBienBan ref="inBienBan" :params="paramInBienBan"/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './API'
import moment from 'moment'
import ThongTinHenKhachHangModal from '../../contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ChuyenToModal from './popups/ChuyenToModal.vue'
import giaophieunhanvien from "../../contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";
import GiaoPhieuNVModal from '../../contract/setup/DeclareServiceSpeedITSecure/popups/GiaoPhieuNVModal.vue'
import ExportDataModal from '../../contract/profile/ExportData/ExportDataModal.vue'
import InBienBan from '../../print/InBienBan/InBienBan.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
//WinUI.WinUIDieuHanhThiCong.frmCapNhatKQKS
export default {
    name:'UpdateSurveyResult',
    components:{
        Footer, breadcrumb, ActionTop,
        ThongTinHenKhachHangModal,
        giaophieunhanvien,
        GiaoPhieuNVModal,
        ChuyenToModal,
        ExportDataModal,
        InBienBan,
        KDataGrid,
        KDatePicker
    },
    data(){
        return {
            header: {
                title: "Cập nhật kết quả khảo sát",
                list: [
                    {   
                        name: "Cập nhật KQTC", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {   
                        name: "Thi công mới", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Cập nhật kết quả khảo sát",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'laytt',
                    name:'Lấy TT',
                    active: true,
                    icon_class:'one-file-attach'
                },
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'giaoviec',
                    name:'Giao việc',
                    active: true,
                    icon_class:'one-file-edit2'
                },
                {
                    id:'xuatphieu',
                    name:'Xuất phiếu',
                    active: true,
                    icon_class:'one-file-download'
                },
                {
                    id:'henld',
                    name:'Hẹn LĐ',
                    active: true,
                    icon_class:'one-henld'
                },
                {
                    id:'xuatfile',
                    name:'Xuất file',
                    active: true,
                    icon_class:'one-download'
                },
                {
                    id:'chuyento',
                    name:'Chuyển tổ',
                    active: true,
                    icon_class:'one-change'
                },
            ],
            columnsDsThueBao:[
                {
                    fieldName:'maTb',
                    headerText:'Số máy',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'maGd',
                    headerText:'Mã giao dịch',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'tenTb',
                    headerText:'Tên thuê bao',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'thuongHieu',
                    headerText:'Loại hình',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'tenKv',
                    headerText:'Khu vực',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'tenDV',
                    headerText:'Đơn vị nhận',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'diaChiLd',
                    headerText:'Địa chỉ LĐ',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ngayYc',
                    headerText:'Ngày YC',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'tenLoaiHd',
                    headerText:'Loại HĐ/PL',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'soDT',
                    headerText:'Số ĐT',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'luuIn',
                    headerText:'Trạng thái in',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ghiChu',
                    headerText:'Ghi chú',
                    allowFiltering:true,
                    width:120
                },

            ],
            dataSourcesDsThueBao:[],
            columnsNV:[
                {
                    fieldName: "tenNv",
                    headerText: "Tên nhân viên",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "soDT",
                    headerText: "Điện thoại",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "nhiemVu",
                    headerText: "Nhiệm vụ",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "ghiChu",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    width:100
                }
            ],
            dataSourcesNV:[],
            thongtin_giaodich:{
                input_ma_gd:'',
                ds_loaihd:[],
                loaihd_selected:null,
                input_nhanvien_yc:''
            },
            thongtin_thuchien:{
                checkNgayGV:false,
                checkNgayHT:false,
                ngay_gv: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                ngay_ht: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                ds_lydoton:[],
                lydoton_selected:null,
                
                ds_kq_khaosat:[],
                kq_khaosat_selected:null,
                ds_nhanvien_thicong:[],
                nhanvien_thicong_selected:0,

                input_noidung_giao:'',
                input_culy:'0',
                input_noidung_th:'',
                input_ghichu:'',
                input_ten_tb:'',
                input_diachi_ld:'',
                input_ghichu_tb:''

            },
            dateFormat: "dd/MM/yyyy HH:mm:ss",
            hdkh_id:0,
            phieu_id:0,
            hdtb_id:0,
            loaihd_id:0,
            kieuld_id:0,
            congviec_th:'',
            nhanvien_tc_id:-1,
            donvi_id:0,
            paramInBienBan:{},
            ma_tb:''

        }
    },
    computed:{
        enabledLyDoTon(){
          return this.thongtin_thuchien.kq_khaosat_selected!=null&&this.thongtin_thuchien.kq_khaosat_selected=='0'  
        },
        enabledCuLy(){
          return this.thongtin_thuchien.kq_khaosat_selected!=null&&this.thongtin_thuchien.kq_khaosat_selected=='0'?false:true 
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='laytt'){
                this.hienthi_ds_hdtb()
            }else if(action.id=='capnhat'){
                this.capnhat()
            }else if(action.id=='giaoviec'){
                if(this.dataSourcesDsThueBao.length==0){
                    this.$toast.error('Không có thuê bao để thực hiện. Bạn hãy kiễm tra lại !')
                    return
                }
                if(this.thongtin_thuchien.nhanvien_thicong_selected==null||this.thongtin_thuchien.nhanvien_thicong_selected==0){
                    this.$toast.error('Hãy chọn nhân viên giao việc!')
                    return
                }
                if(!this.thongtin_thuchien.checkNgayGV){
                    this.$toast.error('Hãy nhập ngày giao việc!')
                    return
                }
                this.$refs.giaoPhieuNhanVienModal.showModal()
            }else if(action.id=='xuatphieu'){
                this.xuatphieu()
            }else if(action.id=='henld'){
                if(this.hdkh_id!=0){
                    this.$refs.thongTinHenKhachHangModal.showModal()
                }
            }else if(action.id=='xuatfile'){
                if(this.dataSourcesDsThueBao.length<=0){
                   this.$toast.error('Không có dữ liệu để xuất')
                    return
                }
                this.$refs.exportDataModal.showModal()
            }else if(action.id=='chuyento'){
                this.$refs.chuyenToModal.showModal()
            }
        },
        clear(){
            this.hdkh_id=0
            this.hdtb_id=0
            this.phieu_id=0

            this.thongtin_giaodich.input_nhanvien_yc=''
            this.thongtin_giaodich.input_ma_gd=''
            this.thongtin_thuchien.input_ten_tb=''
            this.thongtin_thuchien.input_diachi_ld=''
            this.thongtin_thuchien.input_ghichu_tb=''
            this.thongtin_thuchien.input_noidung_th=''

            this.thongtin_thuchien.input_ghichu=''

            this.dataSourcesNV=[]
        },
        gridThueBaoChanged(data){
            if(this.dataSourcesDsThueBao.length<=0||data.length<=0){
                this.clear()
                return
            }
            this.onSelectedRow(data[0])
        },
        async onSelectedRow(item){
            this.hdkh_id=item.hdkhId!=null?item.hdkhId:0
            this.hdtb_id=item.hdtbId!=null?item.hdtbId:0
            this.phieu_id=item.phieuId!=null?item.phieuId:0
            this.loaihd_id=item.loaiHdId!=null?item.loaiHdId:0
            this.kieuld_id=item.kieuLdId!=null?item.kieuLdId:0
            this.ma_tb=item.maTb?item.maTb.ToString():''
            

            this.thongtin_giaodich.input_nhanvien_yc=await this.lay_thongtin_nhanvien(item.nhanVienThId!=null?item.nhanVienThId:0)
            this.thongtin_giaodich.input_ma_gd=item.maGd!=null?item.maGd:'';
            this.thongtin_thuchien.input_ten_tb=item.tenTb!=null?item.tenTb:''
            this.thongtin_thuchien.input_diachi_ld=item.diaChiLd!=null?item.diaChiLd:''
            this.thongtin_thuchien.input_ghichu_tb=item.ghiChu!=null?item.ghiChu:''
            this.thongtin_thuchien.input_noidung_th=item.ndThucHien!=null?item.ndThucHien:''

            this.thongtin_thuchien.input_ghichu=''

            let ds=await this.ds_giaoviec_nhanvien(this.phieu_id, 2)
            await this.hienthi_ds_nv(ds)
        },
        changeLoaiHD(){
            this.hienthi_ds_hdtb()
        },
        async giaoviec_success(){
            let ds=await this.ds_giaoviec_nhanvien(this.phieu_id, 2)
            await this.hienthi_ds_nv(ds)
        },
        capnhat(){
            if(!this.kiemtra_dulieu()){
                return
            }
            let data={
                phieuId: this.phieu_id,
                noiDung: this.thongtin_thuchien.input_noidung_th,
                nhanVienThId: this.$root.token.getNhanVienID(),
                ngayTh: this.thongtin_thuchien.ngay_ht,
                nhanVienGiaoId: this.thongtin_thuchien.nhanvien_thicong_selected!=null?this.thongtin_thuchien.nhanvien_thicong_selected:0,
                ngayGv: this.thongtin_thuchien.ngay_gv,
                ketQuaKs: this.thongtin_thuchien.kq_khaosat_selected!=null?Number(this.thongtin_thuchien.kq_khaosat_selected):0,
                hdtbId: this.hdtb_id,
                lyDoTonTcId: this.thongtin_thuchien.lydoton_selected!=null?this.thongtin_thuchien.lydoton_selected:0,
                ghiChu: this.thongtin_thuchien.input_ghichu.trim(),
                cuLy: Number(this.thongtin_thuchien.input_culy.trim())
            }
            this.capnhat_thongtin(data)

        },
        kiemtra_dulieu(){
            if(this.dataSourcesDsThueBao.length==0){
                this.$toast.error('Không có thông tin phiếu để cập nhật !')
                return
            }
            if(this.dataSourcesNV.length==0&&!this.thongtin_thuchien.checkNgayGV){
                this.$toast.error('Hãy chọn ngày giao việc !')
                return false
            }
            if(!this.thongtin_thuchien.checkNgayHT){
                this.$toast.error('Hãy nhập ngày thực hiện!')
                return false
            }
            return true
        },
        async xuatphieu(){
            let vhdkh_id=await this.fn_tt_hd_thuebao(this.hdtb_id, '-1')
            if(vhdkh_id=='-1'){
                this.$toast.error('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
                return
            }
            let dataCheckeds=this.$refs.gridDs.getSelectedRecords()
            if(dataCheckeds.length==0){
                this.$toast.error('Hãy chọn phiếu')
                return
            }

            var n_hdkh_id = ""
            var n_hdtb_id = ""
            var n_phieu_id = ""

            for(let i=0;i<dataCheckeds.length;i++){
                let item=dataCheckeds[i]
                n_hdkh_id += item.hdkh_id + ','
                n_hdtb_id += item.hdtb_id + ','
                n_phieu_id += item.phieu_id + ','
            }

            this.paramInBienBan.n_hdkh_id=n_hdkh_id.substring(0, n_hdkh_id.length-1)
            this.paramInBienBan.nvth_id=-1
            this.paramInBienBan.cv_thuchien=''
            this.paramInBienBan.n_phieu_id=n_phieu_id.substring(0, n_phieu_id.length-1)
            this.paramInBienBan.n_hdtb_id=n_hdtb_id.substring(0, n_hdtb_id.length-1)
            this.paramInBienBan.huonggiao_id = 0

            setTimeout(()=>{
                this.$refs.inBienBan.showModal()
            },500)
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
        successChuyenTo(){
            this.hienthi_ds_hdtb()
        },
        async lay_ds_loai_hd(){
            try{
                this.loading(true)
                let response=await API.lay_ds_loai_hd(this.axios)
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
        async lay_ds_ly_do_ton(){
            try{
                this.loading(true)
                let response=await API.lay_ds_ly_do_ton(this.axios)
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
        async lay_ds_ketqua_khaosat(){
            try{
                this.loading(true)
                let response=await API.lay_ds_ketqua_khaosat(this.axios)
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
        async fn_tt_hd_thuebao(data, defaultValue){
            try{
                this.loading(true)
                let response=await API.fn_tt_hd_thuebao(this.axios, data)
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
        async lay_ds_nhanvien_thicong(loainv_id){
            try{
                this.loading(true)
                let response=await API.lay_ds_nhanvien_thicong(this.axios, loainv_id)
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
        async ds_phieu_khao_sat_v2(data){
            try{
                this.loading(true)
                let response=await API.ds_phieu_khao_sat_v2(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    //return response.data.data.map((x,index)=>Object.assign(x,{id:index+1}))
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_thongtin_nhanvien(nhanvien_id){
            try{
                this.loading(true)
                let response=await API.lay_thongtin_nhanvien(this.axios, nhanvien_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    if(response.data.data.length>0){
                         let result=response.data.data[0]
                         return result.ma_nv+'-'+result.ten_nv+'-'+result.so_dt
                    }else{
                        return ''
                    }
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async capnhat_noidung_thuchien(phieuId, noiDung){
            try{
                this.loading(true)
                let response=await API.capnhat_noidung_thuchien(this.axios, phieuId, noiDung)
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
        async ds_giaoviec_nhanvien(phieuId, kieu){
            try{
                this.loading(true)
                let response=await API.ds_giaoviec_nhanvien(this.axios, phieuId, kieu)
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
        async capnhat_thongtin(data){
            try{
                this.loading(true)
                let response=await API.capnhat_thongtin(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật dữ liệu thành công!')
                    //
                    await this.hienthi_ds_hdtb()
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công!')
                }
            }
        },
        async capNhatNDTH(){
            if(this.phieu_id==0){
                return
            }
            let result = await this.capnhat_noidung_thuchien(this.phieu_id, this.thongtin_thuchien.input_noidung_th.trim())
            if(result){
                this.$toast.success('Cập nhật nội dung thực hiện thành công!')
                await this.hienthi_ds_hdtb()
                //focus row theo phiếu id

            }else{
                this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công!')
            }
        },
        async hienthi_ds_nv(ds){
            if(ds.length>0){
                this.dataSourcesNV=ds
                this.thongtin_thuchien.checkNgayGV=true
                this.thongtin_thuchien.ngay_gv=ds[0].ngayGiao!=null?ds[0].ngayGiao:''
                this.thongtin_thuchien.nhanvien_thicong_selected=ds[0].nhanVienGiaoId
                
                this.congviec_th=ds[0].nhiemVu!=null?ds[0].nhiemVu:''
                this.nhanvien_tc_id=ds[0].nhanVienThId!=null?ds[0].nhanVienThId:-1

            }else{
                this.dataSourcesNV=[]
                this.thongtin_thuchien.checkNgayGV=false
                this.thongtin_thuchien.ngay_gv=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
                this.thongtin_thuchien.nhanvien_thicong_selected=this.$root.token.getNhanVienID()
                this.congviec_th=''
                this.nhanvien_tc_id=-1
            }
        },
        async hienthi_ds_hdtb(){
            this.dataSourcesDsThueBao=await this.ds_phieu_khao_sat_v2({
                maGd: "0",
                ttHdId: 0,
                huongGiaoId: 0,
                loaiHdId: this.thongtin_giaodich.loaihd_selected!=null?this.thongtin_giaodich.loaihd_selected:0
            })
            // if(this.dataSourcesDsThueBao.length>0){
            //     //focus theo phiếu
            //     if(this.phieu_id!=0){
            //         const index=this.dataSourcesDsThueBao.findIndex(x=>x.phieuId==this.phieu_id)
            //         if(index>-1){
            //             this.rowSelectedIdThueBao=this.phieu_id
            //             this.onSelectedRow(this.dataSourcesDsThueBao[index])
            //         }else{
            //             this.rowSelectedIdThueBao=this.dataSourcesDsThueBao[0].phieuId
            //             this.onSelectedRow(this.dataSourcesDsThueBao[0])
            //         }
            //     }else{
            //         this.rowSelectedIdThueBao=this.dataSourcesDsThueBao[0].phieuId
            //         this.onSelectedRow(this.dataSourcesDsThueBao[0])
            //     }
                
            // }
        },
        async initDuLieu(){
            this.donvi_id=this.$root.token.getDonViID()
            this.thongtin_giaodich.ds_loaihd=await this.lay_ds_loai_hd()
            
            this.thongtin_thuchien.ds_lydoton=await this.lay_ds_ly_do_ton()
            this.thongtin_thuchien.ds_kq_khaosat=await this.lay_ds_ketqua_khaosat()
            if(this.thongtin_thuchien.ds_kq_khaosat.length>0){
                this.thongtin_thuchien.kq_khaosat_selected=this.thongtin_thuchien.ds_kq_khaosat[0].ID
            }
            this.thongtin_thuchien.ds_nhanvien_thicong=await this.lay_ds_nhanvien_thicong(0)
            this.thongtin_thuchien.nhanvien_thicong_selected=this.$root.token.getNhanVienID()

            if(this.thongtin_giaodich.ds_loaihd.length>0){
                this.thongtin_giaodich.loaihd_selected=this.thongtin_giaodich.ds_loaihd[0].ID
                //comment tạm
                await this.hienthi_ds_hdtb()
            }

        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }

}
</script>
<style>
::-webkit-input-placeholder {
   font-style: italic;
}
:-moz-placeholder {
   font-style: italic;  
}
::-moz-placeholder {
   font-style: italic;  
}
:-ms-input-placeholder {  
   font-style: italic; 
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>