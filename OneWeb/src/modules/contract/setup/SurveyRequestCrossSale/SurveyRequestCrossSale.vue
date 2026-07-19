<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" />
        <div class="page-content">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-9 col-12"></div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row marb0">
                            <div class="key bold red nowrap">Chọn tỉnh thi công</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_tinhthicong">
                                    <select2 :settings="{ dropdownParent: $refs['ds_tinhthicong'] }" ref="cboTinhThiCong"
                                        v-model="tinhthicong_selected" class="select2"
                                        :options="ds_tinhthicong.map(e=> ({id: e.tinh_id, text: e.tentinh}))"
                                        @change="changeTinhThiCong">
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <ThongTinKhachHang ref="thongtinkhachhang"
                        @searchMaKH="searchMaKH"
                        @searchMaGD="searchMaGD"
                        @onBlurTenKH="onBlurTenKH"
                        @onEnterMaTB="onEnterMaTB"
                        @onBlurSoGT="onBlurSoGT"
                        @onChangeNgaySinhKH="onChangeNgaySinhKH"
                    />
                </div>
                <div class="col-sm-6 col-12">
                    <ThongTinThueBao ref="thongtinthuebao"
                        :tinh_tc="tinhthicong_selected"
                        @changeDichVu="changeDichVu"
                        @handleDonViThiCong="handleDonViThiCong"
                    />
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">Danh sách phiếu yêu cầu khảo sát</div>
                    <div class="pull-right">
                        <div class="list-checks">
                            <div class="item">
                                <div class="check-action">
                                    <input type="radio" class="check" :checked="loaiphieu==0" value="0" @change="changeLoaiPhieu">
                                    <span class="name bold red">Phiếu mới</span>
                                </div>
                            </div>
                            <div class="item">
                                <div class="check-action">
                                    <input type="radio" class="check" :checked="loaiphieu==1" value="1" @change="changeLoaiPhieu">
                                    <span class="name bold red">Phiếu đã chuyển</span>
                                </div>
                            </div>
                            <div class="item">
                                <div class="check-action">
                                    <input type="radio" class="check" :checked="loaiphieu==2" value="2" @change="changeLoaiPhieu">
                                    <span class="name bold red">Phiếu đã khảo sát</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"
                    :enabledSelectFirstRow="true"
                    @onRowSelected="onRowSelected"/>

            </div>
        </div>
        <!-- Modal -->
        <ThongTinHenKhachHangModal ref="thongTinHenKhachHangModal" :hdkh_id="hdkh_id" :quyenduoc_hen="1"/>

        <!-- test -->
        <ChangeMegawanInforModal ref="changeMegawanInforModal" :ma_tb="'fdsfdsf'"/>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ThongTinKhachHang from './ThongTinKhachHang.vue'
import ThongTinThueBao from './ThongTinThueBao.vue'
import {LoaiHopDong, TrangThaiHD, DichVuVienThong, LOAI_DV} from './ThamSo'
import SurveyRequestCrossSaleAPI from './SurveyRequestCrossSaleAPI'
import ThongTinHenKhachHangModal from '../SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import {createObjectHDKH, createObjectHDTB, createObjectHDTB_ADSL, createObjectHD_BANCHEO, createObjectHDTB_BANCHEO} from './Utils'
import moment from 'moment'
//test
import ChangeMegawanInforModal from '../../../search/subscriber/ChangeMegawanInfor/ChangeMegawanInforPopup/ChangeMegawanInforModal.vue'
export default {
    name:'SurveyRequestCrossSale',
    components:{
        breadcrumb,
        ActionTop,
        KDataGrid,
        ThongTinKhachHang,
        ThongTinThueBao,
        ThongTinHenKhachHangModal,
        ChangeMegawanInforModal
    },
    data(){
        return{
            header: {
                title: "Tiếp nhận yêu cầu khảo sát lắp đặt mới",
                list: [
                    { 
                        name: "Bán chéo", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Tiếp nhận yêu cầu khảo sát lắp đặt",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'layds',
                    name:'Lấy DS',
                    active: true,
                    icon_class:'one-file-attach1',
                    visible:true,
                    tooltip:'Lấy danh sách mới'
                },
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
                {
                    id:'xoa_hd',
                    name:'Xoá HĐ',
                    active: true,
                    icon_class:'one-trash',
                    visible:true,
                    tooltip:'Xoá hợp đồng'
                },
                // {
                //     id:'them_yc',
                //     name:'Thêm YC',
                //     active: true,
                //     icon_class:'one-mobile-plus',
                //     visible:true,
                //     tooltip:'Thêm yêu cầu'
                // },
                // {
                //     id:'xoa_yc',
                //     name:'Xoá YC',
                //     active: true,
                //     icon_class:'one-phone-remove',
                //     visible:true,
                //     tooltip:'Xoá yêu cầu'
                // },
                // {
                //     id:'hen_ld',
                //     name:'Hẹn LĐ',
                //     active: true,
                //     icon_class:'one-calendar-check',
                //     visible:true
                // },
                {
                    id:'chuyenthicong',
                    name:'Chuyển thi công',
                    active: false,
                    icon_class:'fa fa-check',
                    visible:true
                },
                //Bỏ nút này không sài, theo yêu cầu trên sheet
                // {
                //     id:'chuyennet',
                //     name:'Chuyển Net',
                //     active: false,
                //     icon_class:'one-change',
                //     visible:false
                // }
            ],
            columns:[
                {
                    fieldName:'ma_gd',
                    headerText:'Mã giao dịch',
                    allowFiltering:true
                },
                {
                    fieldName:'ten_tb',
                    headerText:'Tên thuê bao',
                    allowFiltering:true
                },
                {
                    fieldName:'diachi_ld',
                    headerText:'Địa chỉ lắp đặt',
                    allowFiltering:true
                },
                {
                    fieldName:'loaihinh_tb',
                    headerText:'Loại hình',
                    allowFiltering:true
                },
                {
                    fieldName:'soluong',
                    headerText:'Số lượng',
                    allowFiltering:true
                },
                {
                    fieldName:'tinh_tc',
                    headerText:'Tỉnh thi công',
                    allowFiltering:true
                },
                {
                    fieldName:'trangthai_hd',
                    headerText:'Trạng thái',
                    allowFiltering:true
                }
            ],
            dataSources:[],
            loaiphieu:0,
            loaihd_id:33,
            ds_tinhthicong:[],
            tinhthicong_selected:null,
            ts_kt_tthdkhdn:0,
            hdkh_id:0,
            hdtb_id:0,
            ds:[],
            trang_thai_bc:-1,
            trang_thai_hd:-1,
            thuebao_id:0,
            dichvuvt_id:0,
            loaihinh_tb:0,
            phieu_cha_id:0,
            khachhang_id:0,
            txtCuly:'0',
            ds_donvi_tinhtc:[],
            tinhtc_donvi_id:-1,
            dsHDKH:[],
            dsHDTB:[],
            huonggiaotn_id:0,
            quytrinh_id:0,
            dsHDTBDV:[],
            dsHDTB_ADSL:[],
            dsDiaChi:[],
            dsDiaChiHDKH:[],
            dsDiaChiHDTB:[],
            dsHDTB_KV:[],
            dsHDBANCHEO:[],
            dsHDTBBANCHEO:[],
            quytrinh_tc_tn:0,
            huonggiao_tc_tn:0,
            dsDiaChi_BanCheo:[],
            diachikh_id:0,
            diachild_id:0,
            diachitb_id:0,
            dsHDBC_ADSL:[],
            dsHDBCMGWAN:[],
            dsHDBCCD:[],
            dsHDBC_TSL:[],
            dsHDBCIMS:[],
            dsDK_DVGT_BC:[],
            dsHDBC_CNTT:[],

            hdkh_tc_id:0,
            dsTTTT:[],
            makh_web:'',
            isTruocBH: true



        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='layds'){
                //this.$refs.changeMegawanInforModal.showModal()
                this.HienThiDanhSacHDTB()
            }else if(action.id=='nhapmoi'){
                this.SetButton(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                this.SetButton(0)
            }else if(action.id=='xoa_hd'){
                if(this.dataSources.length==0){
                    return
                }
                this.$confirm('Bạn thật sự muốn xóa thông tin không ?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    this.xoaHD()
                })
            }else if(action.id=='them_yc'){
                this.themTB()
            }else if(action.id=='xoa_yc'){
                this.xoaTB()
            }else if(action.id=='hen_ld'){
                if(this.hdkh_id!=0){
                    this.$refs.thongTinHenKhachHangModal.showModal()
                }
            }else if(action.id=='chuyenthicong'){
                this.chuyenThiCong()
            }
        },
        SetButton(kieu){
            for(let i=0;i<this.actions.length;i++){
                if(this.actions[i].id!='layds'&&this.actions[i].id!='hen_ld'){
                    this.actions[i].active=false
                }
            }
            if(kieu==-1){
                //Bat dau
                this.$refs.thongtinkhachhang.focusTenKH()
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.tinhthicong_selected=null
            }else if(kieu==0){
                //Bắt đầu
                this.$refs.thongtinkhachhang.focusMaGD()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='chuyenthicong')].active=true
                this.Clear()
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=false
                this.tinhthicong_selected=null
            }else if(kieu==1){
                //Thêm mới
                this.$refs.thongtinkhachhang.focusTenKH()
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.tinhthicong_selected=null
                this.Clear()

            }else if(kieu==2){
                //Huỷ
                this.$refs.thongtinkhachhang.focusMaGD()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa_hd')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='chuyenthicong')].active=true
                this.Clear()
            }else if(kieu==3){
                this.$refs.thongtinkhachhang.focusMaGD()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa_hd')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='chuyenthicong')].active=true
                //this.actions[this.actions.findIndex(x=>x.id=='them_yc')].active=true
                //this.actions[this.actions.findIndex(x=>x.id=='xoa_yc')].active=true
            }
        },
        async Clear(){
            this.khachhang_id = 0
            this.hdkh_id = 0
            this.hdtb_id = 0
            this.thuebao_id = 0
            await this.$refs.thongtinkhachhang.Clear()
            await this.$refs.thongtinthuebao.Clear()
            this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
            this.txtCuly='0'
            

            // tinhtt_id = 0;
            // quantt_id = 0;
            // phuongtt_id = 0;
            // phott_id = 0;
            // aptt_id = 0;
            // sonhatt = "";
            
            
            
        },
        async HienThiTTHopDongTB(item){
            if(!item.hdtb_thicong){
                this.trang_thai_bc=1
            }else{
                this.trang_thai_bc=0
            }
            this.trang_thai_hd=item.tthd_id?item.tthd_id:0
            this.thuebao_id=item.thuebao_id?item.thuebao_id:0
            this.dichvuvt_id=item.dichvuvt_id?item.dichvuvt_id:0
            this.loaihinh_tb=item.loaitb_id?item.loaitb_id:0
            this.phieu_cha_id=Number(await this.fn_tt_giaophieu(this.hdtb_id, 0))
            console.log('phieu_cha_id', this.phieu_cha_id)
            await this.$refs.thongtinthuebao.HienThiTTHopDongTB(item)
            this.$refs.thongtinkhachhang.input_matb=item.ma_tb?item.ma_tb.toString():''
            this.$refs.thongtinkhachhang.input_ma_gd=item.ma_gd?item.ma_gd.toString():''
            let ds=await this.lay_ds_diachi_bc_theo_hdtb_id(this.hdtb_id)
            await this.HienThiTTDiaChi(ds,3)
            this.SetButton(3)
        },
        changeDichVu(dichvuvt_id){
            this.dichvuvt_id=dichvuvt_id
        },
        onBlurTenKH(ten_kh){
            this.$refs.thongtinthuebao.input_ten_tb=ten_kh
        },
        async searchMaGD(ma_gd){
            // let ds=await this.lay_ds_hopdong_theo_ma_gd({
            //     ma_gd: ma_gd,
            //     loaihd_id: this.loaihd_id,
            //     donvi_id: this.$root.token.getDonViID(),
            //     tthd_id: TrangThaiHD.MOI,
            //     nhanvien_id: this.$root.token.getNhanVienID(),
            //     donvi_dl_id: 0
            // })
            // await this.HienThiTTHopDongKH(ds)
            //Filter datagrid local
            this.hdkh_id = 0
            var tthd_id = 0
            if(this.loaiphieu==0){
                tthd_id = TrangThaiHD.MOI
            }else if(this.loaiphieu==1){
                tthd_id = TrangThaiHD.DANG_THI_CONG
            }else if(this.loaiphieu==2){
                tthd_id = TrangThaiHD.HOAN_THANH
            }

            //lay_ds_ks_bc
            let dsDanhSach=await this.lay_ds_ks_bc({
                in_hdkh_id: this.hdkh_id,
                in_tthd_id: tthd_id,
                vloaihd_id: this.loaihd_id,
                vtinhtc_id: 0,
                vnhanvien_id: this.$root.token.getNhanVienID()
            })
            let data=dsDanhSach.filter(x=>x.ma_gd==ma_gd)
            if(data.length>0){
                this.dataSources=data
            }else{
                this.$toast.error('Không có thông tin tìm kiếm')
            }

        },
        async HienThiTTHopDongKH(ds){
            if(ds.length>0){
                this.hdkh_id=ds[0].hdkh_id?ds[0].hdkh_id:0
                this.khachhang_id=ds[0].khachhang_id?ds[0].khachhang_id:0
                await this.$refs.thongtinkhachhang.HienThiTTHopDongKH(ds[0])
                let ds_dc=await this.lay_ds_diachi_theo_hdkh(this.hdkh_id)
                await this.HienThiTTDiaChi(ds_dc, 1)
                await this.HienThiDanhSacHDTB()
            }else{
                this.SetButton(0)
            }
        },  
        onEnterMaTB(ma_tb){

        },
        onChangeNgaySinhKH(value){
            this.$refs.thongtinthuebao.ngay_sinh=value
        },
        async onBlurSoGT(so_gt){
            //Kiểm tra số giấy tờ
            const regex = RegExp('^[a-zA-Z0-9]*$')
            if(!regex.test(so_gt)){
                this.$toast.error('Số giấy tờ không hợp lệ.Số giấy tờ gồm các ký tự từ 0->9a->z . : - /. Hãy kiểm tra lại')
                this.$refs.thongtinkhachhang.focusSoGT()
                return
            }

            let ds_maKH=await this.sp_lay_ds_makh_theo_sogt(so_gt, this.hdkh_id)
            if(ds_maKH.length==1){
                let makh=this.$refs.thongtinkhachhang.input_ma_kh.trim()
                if(makh!=ds_maKH[0].ma_kh){
                    let result = await this.confirm('Số giấy tờ ' + so_gt + ' đang được sử dụng bởi mã khách hàng: ' + ds_maKH[0].ma_kh+ '\nBạn có muốn sử dụng thông tin khách hàng cũ không?','Thông báo')
                    if(result==0) return

                    //Handle
                    //Lấy thông tin cũ của khách hàng khi chưa lập hợp đồng - 
                    if(this.hdkh_id==0){
                        this.$refs.thongtinkhachhang.input_ma_kh=ds_maKH[0].ma_kh
                        let result=await this.lay_thongtin_khachhang_nhanquyen(ds_maKH[0].ma_kh)
                        if(result.length>0){
                            await this.HienThiTT_DanhBa(result[0])
                        }else{
                            this.$toast.error('Không tìm thấy thông tin khách hàng')
                        }
                    }else{
                        this.$toast.error('Hợp đồng/phụ lục đã được lập, bạn không được sử dụng số giấy tờ của khách hàng khác!')
                    }
                }
            }else if(ds_maKH.length>1){
                let str=ds_maKH.map(x=>x.ma_kh).join(',')
                this.$toast.error('Số giấy tờ '+so_gt+ ' đang được sử dụng bởi các mã khách hàng: '+str+' .')
            }

        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        async xoaHD(){
            let result=await this.xoa_hdkh_v2(this.hdkh_id, this.loaihd_id)
            if(result){
                await this.Clear()
                await this.SetButton(0)
                await this.HienThiDanhSacHDTB()
            }
        },
        async xoaTB(){
            if(this.dataSources.length==0){
                return
            }
            if(this.dataSources.length==1){
                this.$toast.error('Hợp đồng này hiện đang có 1 thuê bao, bạn hãy thực hiện chức năng xóa hợp đồng !')
                return
            }
            this.$confirm('Bạn thật sự muốn xóa thuê bao không ?','Thông báo',{
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Huỷ'
            }).then(async() => {
                //handle
                let result=await this.xoa_hdtb(this.hdtb_id)
                if(result){
                    this.$toast.success('Xoá thuê bao thành công!')
                    await this.Clear()
                    await this.HienThiDanhSacHDTB()
                }
            })
        },
        async themTB(){
            if (!(await this.KiemTraDL_ThueBao())){
                return
            }
            this.quytrinh_tc_tn = 0
            this.huonggiao_tc_tn = 0
            let kieuld_id=this.$refs.thongtinthuebao.kieu_ld_selected?this.$refs.thongtinthuebao.kieu_ld_selected:0
            let rs_qt=await this.layhuonggiao_tiepnhan_bancheo_v2(this.tinhthicong_selected, LoaiHopDong.KHAOSATDATMOI, kieuld_id)
            if(rs_qt.length<=0){
                this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
                return
            }
            this.quytrinh_tc_tn = rs_qt[0].quytrinh_id
            this.huonggiao_tc_tn = rs_qt[0].huonggiao_id

            await this.TaoDuLieu_HDTB(true)
            // TaoDuLieu_THONGTIN_TT(themmoi);không dùng
            await this.TaoDuLieu_HDTBDV(true)
            await this.TaoDuLieu_HDTB_ADSL(true)
            await this.TaoDuLieu_DiaChi(true, 4)
            await this.TaoDuLieu_DiaChi_BanCheo(true, 4)
            await this.TaoDuLieu_HDBANCHEO(true)
            await this.CapNhat_HDTB_ID_HDTB_KV()

            let dataThemKS={
                huonggiao_id: this.huonggiaotn_id,
                js_diachi: JSON.stringify(this.dsDiaChi),
                js_diachi_hdtb: JSON.stringify(this.dsDiaChiHDTB),
                js_hd_thuebao: JSON.stringify(this.dsHDTB),
                js_hdtb_dv: JSON.stringify(this.dsHDTBDV)
            }
            console.log('dataThemKS', dataThemKS)
            let resultThemKS=await this.sp_themmoi_hopdong_ks_v2(dataThemKS)
            if(resultThemKS){
                let p_ds_para={
                    HD_BANCHEO:this.dsHDBANCHEO,
                    HDTB_BANCHEO:this.dsHDTBBANCHEO,
                    HDBC_MGWAN:this.dsHDBCMGWAN,
                    HDBC_ADSL:this.dsHDBC_ADSL,
                    HDBC_CD:this.dsHDBCCD,
                    DANGKY_DVGT_BC:this.dsDK_DVGT_BC,
                    HDBC_TSL:this.dsHDBC_TSL,
                    HDBC_IMS:this.dsHDBCIMS,
                    DIACHI_BANCHEO:this.dsDiaChi_BanCheo,
                    HDBC_CNTT:this.dsHDBC_CNTT
                }
                let p_tt_nd={
                    DONVI_ID:this.$root.token.getDonViID(),
                    NHANVIEN_ID:this.$root.token.getNhanVienID(),
                    NGUOI_CN:this.$root.token.getUserName(),
                    MAY_CN:'localhost',
                    IP_CN:'127.0.0.1'
                }
                let data={
                    p_themmoi:1,
                    p_ds_para:JSON.stringify(p_ds_para),
                    p_tt_nd:JSON.stringify(p_tt_nd)
                }
                let result=await this.fn_ins_update_hd_datmoi_bancheo_v2(data)
                if(result){
                    await this.HienThiDanhSacHDTB()
                }
            }
        },
        async chuyenThiCong(){
            if(this.dataSources.length<=0){
                this.$toast.error('Không có phiếu để chuyển thi công!')
                return
            }
            const rowFocus=this.dataSources.find(x=>x.hdtb_id==this.hdtb_id)
            if(!rowFocus){
                return
            }
            if(this.tinhthicong_selected!=rowFocus.tinhtc_id){
                this.$toast.error('Không đúng tỉnh thi công. Vui lòng đóng form và chọn lại tỉnh thi công!')
                return
            }

            if(this.tinhtc_donvi_id==-1){
                this.$toast.error('Địa chỉ thi công này chưa được gán Loại đơn vị Trạm thi công.')
                return
            }
            if(this.trang_thai_bc!=1){
                this.$toast.error('Hợp đồng đã chuyển tới tỉnh thi công')
                return
            }
            if(this.trang_thai_hd!=TrangThaiHD.MOI){
                this.$toast.error('Trạng thái hợp đồng đang không phải trạng thái mới')
                return
            }
            this.huonggiao_tc_tn = 0
            let kieuld_id=this.$refs.thongtinthuebao.kieu_ld_selected?this.$refs.thongtinthuebao.kieu_ld_selected:0
            let rs_qt=await this.layhuonggiao_tiepnhan_bancheo_v2(this.tinhthicong_selected, LoaiHopDong.KHAOSATDATMOI, kieuld_id)
            if(rs_qt.length<=0){
                this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
                return
            }
            this.huonggiao_tc_tn = rs_qt[0].huonggiao_id
            //Sử dụng hàm gom chuyển thi công
            let p_tt_nd={
                DONVI_ID:this.$root.token.getDonViID(),
                NHANVIEN_ID:this.$root.token.getNhanVienID(),
                NGUOI_CN:this.$root.token.getUserName(),
                MAY_CN:'localhost',
                IP_CN:'127.0.0.1'
            }
            let result=await this.fn_tao_dulieu_chuyenphieu_ks_bancheo({
                p_tinh_tc_id: this.tinhthicong_selected,
                p_hdtb_id: this.hdtb_id,
                p_huonggiao_tn_id: this.huonggiao_tc_tn,
                p_ds_para: this.tinhtc_donvi_id?JSON.stringify({tinhtc_donvi_id:this.tinhtc_donvi_id}):null,
                p_tt_nd: JSON.stringify(p_tt_nd)
            })
            if(result){
                this.$toast.success('Đã chuyển thành công hợp đồng')
                await this.HienThiDanhSacHDTB()
            }

        },
        async ghiDuLieu(){
            //
            if(this.tinhthicong_selected==null){
                this.$toast.error('Vui lòng chọn tỉnh thi công!')
                return
            }
            if (!(await this.KiemTraDL_KhachHang())){
                return
            }
            if (!(await this.KiemTraDL_ThueBao())){
                return
            }
            //
            let isInsert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?0:1
            if(isInsert==1){
                this.quytrinh_tc_tn = 0
                this.huonggiao_tc_tn = 0
                let kieuld_id=this.$refs.thongtinthuebao.kieu_ld_selected?this.$refs.thongtinthuebao.kieu_ld_selected:0
                let rs_qt=await this.layhuonggiao_tiepnhan_bancheo_v2(this.tinhthicong_selected, LoaiHopDong.KHAOSATDATMOI, kieuld_id)
                if(rs_qt.length<=0){
                    this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
                    return
                }
                this.quytrinh_tc_tn = rs_qt[0].quytrinh_id
                this.huonggiao_tc_tn = rs_qt[0].huonggiao_id
    
                await this.TaoDuLieu(true)

                // Hàm này tham chiếu sang hàm UR2.1.001
                let dataHDKS={
                    huonggiao_id: this.huonggiaotn_id,
                    js_diachi: JSON.stringify(this.dsDiaChi),
                    js_diachi_hdkh: JSON.stringify(this.dsDiaChiHDKH),
                    js_diachi_hdtb: JSON.stringify(this.dsDiaChiHDTB),
                    js_hd_khachhang: JSON.stringify(this.dsHDKH),
                    js_hd_thuebao: JSON.stringify(this.dsHDTB),
                    js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
                    js_hdtb_kv: JSON.stringify(this.dsHDTB_KV)
                }
                let resultThemHDKS=await this.sp_themmoi_hopdong_ks(dataHDKS)
                if(resultThemHDKS){
                    //
                    let p_ds_para={
                        HD_BANCHEO:this.dsHDBANCHEO,
                        HDTB_BANCHEO:this.dsHDTBBANCHEO,
                        HDBC_MGWAN:this.dsHDBCMGWAN,
                        HDBC_ADSL:this.dsHDBC_ADSL,
                        HDBC_CD:this.dsHDBCCD,
                        DANGKY_DVGT_BC:this.dsDK_DVGT_BC,
                        HDBC_TSL:this.dsHDBC_TSL,
                        HDBC_IMS:this.dsHDBCIMS,
                        DIACHI_BANCHEO:this.dsDiaChi_BanCheo,
                        HDBC_CNTT:this.dsHDBC_CNTT
                    }
                    let p_tt_nd={
                        DONVI_ID:this.$root.token.getDonViID(),
                        NHANVIEN_ID:this.$root.token.getNhanVienID(),
                        NGUOI_CN:this.$root.token.getUserName(),
                        MAY_CN:'localhost',
                        IP_CN:'127.0.0.1'
                    }
                    let data={
                        p_themmoi:1,
                        p_ds_para:JSON.stringify(p_ds_para),
                        p_tt_nd:JSON.stringify(p_tt_nd)
                    }
                    let result=await this.fn_ins_update_hd_datmoi_bancheo_v2(data)
                    if(result){
                        this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
                        await this.HienThiDanhSacHDTB()
                    }
                    
                }
            }else{
                this.quytrinh_tc_tn = 0
                this.huonggiao_tc_tn = 0
                let kieuld_id=this.$refs.thongtinthuebao.kieu_ld_selected?this.$refs.thongtinthuebao.kieu_ld_selected:0
                let rs_qt=await this.layhuonggiao_tiepnhan_bancheo_v2(this.tinhthicong_selected, LoaiHopDong.KHAOSATDATMOI, kieuld_id)
                if(rs_qt.length<=0){
                    this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
                    return
                }
                this.quytrinh_tc_tn = rs_qt[0].quytrinh_id
                this.huonggiao_tc_tn = rs_qt[0].huonggiao_id

                await this.TaoDuLieu(false)
                //sp_capnhat_hopdong_ks
                let dataHDKS={
                    huonggiao_id: this.huonggiaotn_id,
                    js_diachi: JSON.stringify(this.dsDiaChi),
                    js_diachi_hdkh: JSON.stringify(this.dsDiaChiHDKH),
                    js_diachi_hdtb: JSON.stringify(this.dsDiaChiHDTB),
                    js_hd_khachhang: JSON.stringify(this.dsHDKH),
                    js_hd_thuebao: JSON.stringify(this.dsHDTB),
                    js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
                    js_hdtb_kv: JSON.stringify(this.dsHDTB_KV)
                }
                let resultCapNhatHDKS=await this.sp_capnhat_hopdong_ks(dataHDKS)
                if(resultCapNhatHDKS){
                    let p_ds_para={
                        HD_BANCHEO:this.dsHDBANCHEO,
                        HDTB_BANCHEO:this.dsHDTBBANCHEO,
                        HDBC_MGWAN:this.dsHDBCMGWAN,
                        HDBC_ADSL:this.dsHDBC_ADSL,
                        HDBC_CD:this.dsHDBCCD,
                        DANGKY_DVGT_BC:this.dsDK_DVGT_BC,
                        HDBC_TSL:this.dsHDBC_TSL,
                        HDBC_IMS:this.dsHDBCIMS,
                        DIACHI_BANCHEO:this.dsDiaChi_BanCheo,
                        HDBC_CNTT:this.dsHDBC_CNTT
                    }
                    let p_tt_nd={
                        DONVI_ID:this.$root.token.getDonViID(),
                        NHANVIEN_ID:this.$root.token.getNhanVienID(),
                        NGUOI_CN:this.$root.token.getUserName(),
                        MAY_CN:'localhost',
                        IP_CN:'127.0.0.1'
                    }
                    let data={
                        p_themmoi:0,
                        p_ds_para:JSON.stringify(p_ds_para),
                        p_tt_nd:JSON.stringify(p_tt_nd)
                    }
                    let result=await this.fn_ins_update_hd_datmoi_bancheo_v2(data)
                    if(result){
                        this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
                        await this.HienThiDanhSacHDTB()
                    }
                }
            }


        },
        async TaoDuLieu(themmoi){
            await this.TaoDuLieu_HDKH(themmoi)
            await this.TaoDuLieu_HDTB(themmoi)
            // TaoDuLieu_THONGTIN_TT(themmoi);không dùng
            await this.TaoDuLieu_HDTBDV(themmoi)
            await this.TaoDuLieu_HDTB_ADSL(themmoi)
            await this.TaoDuLieu_DiaChi(themmoi, 4)
            await this.TaoDuLieu_DiaChi_BanCheo(themmoi, 4)
            await this.TaoDuLieu_HDBANCHEO(themmoi)
            await this.CapNhat_HDTB_ID_HDTB_KV()
        },
        async TaoDuLieu_HDKH(themmoi){
            this.dsHDKH=[]
            var object=createObjectHDKH()
            if(themmoi){
                this.hdkh_id = await this.getKey('HD_KHACHHANG')
            }
            var maGD=''
            if(themmoi){
                maGD=await this.lay_magd(this.loaihd_id)
                this.$refs.thongtinkhachhang.input_ma_gd=maGD
            }else{
                maGD=this.$refs.thongtinkhachhang.input_ma_gd.trim()
            }
            object.HDKH_ID=this.hdkh_id
            object.MA_GD=maGD
            if(this.khachhang_id!=0){
                object.KHACHHANG_ID=this.khachhang_id
                object.MA_KH=this.$refs.thongtinkhachhang.input_ma_kh.trim()
            }
            object.TEN_KH=this.$refs.thongtinkhachhang.input_tenkh.trim()
            object.DIACHI_KH=this.$refs.thongtinkhachhang.input_diachi_kh.trim()

            if(this.$refs.thongtinkhachhang.ngay_sinh&&this.$refs.thongtinkhachhang.ngay_sinh.trim()!=''){
                object.NGAY_SN=moment(this.$refs.thongtinkhachhang.ngay_sinh,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            }

            object.LOAIGT_ID = this.$refs.thongtinkhachhang.loai_gt_selected
            object.SO_GT = this.$refs.thongtinkhachhang.input_sogiayto.trim()
            if(this.$refs.thongtinkhachhang.ngay_cap&&this.$refs.thongtinkhachhang.ngay_cap.trim()!=''){
                object.NGAYCAP=moment(this.$refs.thongtinkhachhang.ngay_cap,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            }

            object.NOICAP = this.$refs.thongtinkhachhang.input_noicap.trim()
            object.DONVI_ID = this.$root.token.getDonViID()
            object.NHANVIEN_ID = this.$root.token.getNhanVienID()
            //khlon_id=1
            object.KHLON_ID = 1;
            object.LOAIHD_ID = this.loaihd_id

            object.NGUOI_CN = this.$root.token.getUserName()
            object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            object.MAY_CN='localhost'

            object.CTV_ID = this.$root.token.getNhanVienID()

            if(this.$refs.thongtinkhachhang.ngay_yc&&this.$refs.thongtinkhachhang.ngay_yc.trim()!=''){
                object.NGAYLAP_HD=moment(this.$refs.thongtinkhachhang.ngay_yc,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            }else{
                object.NGAYLAP_HD=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            }
            if(this.$refs.thongtinkhachhang.ngay_yc&&this.$refs.thongtinkhachhang.ngay_yc.trim()!=''){
                object.NGAY_YC=moment(this.$refs.thongtinkhachhang.ngay_yc,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            }else{
                object.NGAY_YC=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            }
            object.GIOITINH = this.$refs.thongtinkhachhang.radio_gioitinh==1?0:this.$refs.thongtinkhachhang.radio_gioitinh==2?1:null
            object.SO_DT= this.$refs.thongtinkhachhang.input_so_dt.trim()
            object.QUOCTICH_ID=this.$refs.thongtinkhachhang.quoctich_selected
            object.NGANHNGHE_ID=this.$refs.thongtinkhachhang.nganhnghe_selected
            if(this.$refs.thongtinkhachhang.ngay_hethan&&this.$refs.thongtinkhachhang.ngay_hethan.trim()!=''){
                object.NGAYHETHAN_GT=moment(this.$refs.thongtinkhachhang.ngay_hethan,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            }

            // //Hieutc thêm tạo dữ liệu tiểu ngành cấp 1,2,3 --- Ngày 03/12/2010
            object.TNC1_ID = this.$refs.thongtinkhachhang.dataTieuNganh.TNC1_ID
            object.TNC2_ID = this.$refs.thongtinkhachhang.dataTieuNganh.TNC2_ID
            object.TNC3_ID = this.$refs.thongtinkhachhang.dataTieuNganh.TNC3_ID
            object.TNC4_ID = this.$refs.thongtinkhachhang.dataTieuNganh.TNC4_ID

            this.dsHDKH.push(object)

        },
        async TaoDuLieu_HDTB(themmoi){
            this.dsHDTB=[]
            var object=createObjectHDTB()
            if(themmoi){
                this.hdtb_id= await this.getKey('HD_THUEBAO')
            }

            object.HDTB_ID = this.hdtb_id
            object.HDKH_ID = this.hdkh_id
            if (this.thuebao_id > 0){
                object.THUEBAO_ID = this.thuebao_id
            }

            object.MA_TB = this.$refs.thongtinkhachhang.input_matb.trim().toLowerCase()
            object.TEN_TB = this.$refs.thongtinthuebao.input_ten_tb.trim()
            object.DIACHI_TB = this.$refs.thongtinthuebao.input_diachi_tb.trim()
            object.GHICHU = this.$refs.thongtinthuebao.input_chi_tiet_yc.trim()
            object.KIEULD_ID = this.$refs.thongtinthuebao.kieu_ld_selected
            object.LOAITB_ID = this.$refs.thongtinthuebao.loaihinh_selected
            object.DONVI_ID = this.$refs.thongtinthuebao.donvi_tc_selected
            object.DOITUONG_ID = this.$refs.thongtinthuebao.doituong_selected
            object.TTHD_ID = TrangThaiHD.MOI
            object.DICHVUVT_ID = this.$refs.thongtinthuebao.dichvu_selected
            object.KIEU_TB = "0"
            object.NGUOI_CN=this.$root.token.getUserName()
            object.NGAY_CN=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            object.MAY_CN='localhost'
            if(this.$refs.thongtinthuebao.input_soluong.trim()!=''){
                object.SOLUONG=Number(this.$refs.thongtinthuebao.input_soluong.trim())
            }else{
                object.SOLUONG=0
            }
            object.DIACHI_LD = this.$refs.thongtinthuebao.input_diachi_ld.trim()
            if(this.$refs.thongtinthuebao.ngay_sinh&&this.$refs.thongtinthuebao.ngay_sinh.trim()!=''){
                object.NGAY_SN=moment(this.$refs.thongtinthuebao.ngay_sinh,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            }
            //dùng chung quy trình cho các loại hd tiếp nhận khảo sát
            let kieuld_id=this.$refs.thongtinthuebao.kieu_ld_selected?this.$refs.thongtinthuebao.kieu_ld_selected:0
            let dset=await this.sp_layhuonggiao_tiepnhan(this.loaihd_id, kieuld_id)
            if(dset.length>0){
                this.huonggiaotn_id=dset[0].huonggiao_id?dset[0].huonggiao_id:0
                this.quytrinh_id=dset[0].quytrinh_id?dset[0].quytrinh_id:0
            }
            object.QUYTRINH_ID=this.quytrinh_id
            this.dsHDTB.push(object)
        },
        async TaoDuLieu_HDTBDV(themmoi){
            this.dsHDTBDV=[]
            var object={}
            object.HDTB_ID = this.hdtb_id
            object.LOAIDV_ID = this.loaihd_id == LoaiHopDong.TIEPNHANHD?2:LOAI_DV.TRAM_VT
            object.DONVI_ID = this.$refs.thongtinthuebao.donvi_tc_selected?this.$refs.thongtinthuebao.donvi_tc_selected:0
            object.KIEUDV_ID = 2 
            this.dsHDTBDV.push(object)
        },
        async TaoDuLieu_HDTB_ADSL(themmoi){
            this.dsHDTB_ADSL = []
            var object=createObjectHDTB_ADSL()
            object.HDTB_ID = this.hdtb_id
            object.CULY = Number(this.txtCuly)
            this.dsHDTB_ADSL.push(object)
        },
        async TaoDuLieu_DiaChi(themmoi, kieu){
            this.dsDiaChi = []
            this.dsDiaChiHDKH = []
            this.dsDiaChiHDTB = []

            //
            if(kieu==1||kieu==4){
                var diachi={}

                this.diachikh_id=0
                if(themmoi){
                    this.diachikh_id = await this.getKey('DIACHI')
                }else{
                    this.diachikh_id=this.$refs.thongtinkhachhang.dataDiaChiKH.diachi_id
                }

                diachi.DIACHI_ID=this.diachikh_id
                diachi.TINH_ID=this.$refs.thongtinkhachhang.dataDiaChiKH.tinh_id
                diachi.QUAN_ID=this.$refs.thongtinkhachhang.dataDiaChiKH.quan_id
                diachi.PHUONG_ID=this.$refs.thongtinkhachhang.dataDiaChiKH.phuong_id
                diachi.PHO_ID=this.$refs.thongtinkhachhang.dataDiaChiKH.pho_id
                diachi.AP_ID=this.$refs.thongtinkhachhang.dataDiaChiKH.ap_id
                diachi.KHU_ID=this.$refs.thongtinkhachhang.dataDiaChiKH.khu_id
                diachi.SONHA=this.$refs.thongtinkhachhang.dataDiaChiKH.sonha
                diachi.DIACHI=this.$refs.thongtinkhachhang.dataDiaChiKH.diachi

                this.dsDiaChi.push(diachi)

                if(themmoi){
                    var diachiHDKH={}
                    diachiHDKH.DIACHI_ID=this.diachikh_id
                    diachiHDKH.HDKH_ID=this.hdkh_id
                    this.dsDiaChiHDKH.push(diachiHDKH)
                }

            }
            if(kieu==3||kieu==4){
                this.diachild_id=0
                this.diachitb_id=0
                if(themmoi){
                    this.diachild_id=await this.getKey('DIACHI')
                    this.diachitb_id=await this.getKey('DIACHI')
                }else{
                    this.diachild_id=this.$refs.thongtinthuebao.dataDiaChiLD.diachi_id
                    this.diachitb_id=this.$refs.thongtinthuebao.dataDiaChiTB.diachi_id
                }
                //Địa chỉ lắp đặt --> dataset dsdiachi
                var diachiLD={}
                diachiLD.DIACHI_ID=this.diachild_id
                diachiLD.TINH_ID=this.$refs.thongtinthuebao.dataDiaChiLD.tinh_id

                // diachiLD.QUAN_ID=this.$refs.thongtinthuebao.dataDiaChiLD.quan_id
                // diachiLD.PHUONG_ID=this.$refs.thongtinthuebao.dataDiaChiLD.phuong_id
                // diachiLD.PHO_ID=this.$refs.thongtinthuebao.dataDiaChiLD.pho_id
                // diachiLD.AP_ID=this.$refs.thongtinthuebao.dataDiaChiLD.ap_id
                // diachiLD.KHU_ID=this.$refs.thongtinthuebao.dataDiaChiLD.khu_id
                // diachiLD.SONHA=this.$refs.thongtinthuebao.dataDiaChiLD.sonha
                // diachiLD.DIACHI=this.$refs.thongtinthuebao.dataDiaChiLD.diachi

                this.dsDiaChi.push(diachiLD)

                //Địa chỉ thuê bao --> dataset dsdiachi
                var diachiTB={}
                diachiTB.DIACHI_ID=this.diachitb_id
                diachiTB.TINH_ID=this.$refs.thongtinthuebao.dataDiaChiTB.tinh_id

                // diachiTB.QUAN_ID=this.$refs.thongtinthuebao.dataDiaChiTB.quan_id
                // diachiTB.PHUONG_ID=this.$refs.thongtinthuebao.dataDiaChiTB.phuong_id
                // diachiTB.PHO_ID=this.$refs.thongtinthuebao.dataDiaChiTB.pho_id
                // diachiTB.AP_ID=this.$refs.thongtinthuebao.dataDiaChiTB.ap_id
                // diachiTB.KHU_ID=this.$refs.thongtinthuebao.dataDiaChiTB.khu_id
                // diachiTB.SONHA=this.$refs.thongtinthuebao.dataDiaChiTB.sonha
                // diachiTB.DIACHI=this.$refs.thongtinthuebao.dataDiaChiTB.diachi

                this.dsDiaChi.push(diachiTB)

                var diachiHDTB={}
                diachiHDTB.DIACHILD_ID=this.diachild_id
                //địa chỉ thuê bao
                diachiHDTB.DIACHI_ID=this.diachitb_id
                diachiHDTB.HDTB_ID=this.hdtb_id
                
                //Lat_TB, Lng_TB, Lat_LD, Lng_LD các giá trị không được gắn mặc định  0
                diachiHDTB.KINHDO = 0
                diachiHDTB.VIDO = 0
                diachiHDTB.KINHDO_LD = 0
                diachiHDTB.VIDO_LD = 0
                
                this.dsDiaChiHDTB.push(diachiHDTB)
            }

        },
        async CapNhat_HDTB_ID_HDTB_KV(){
            this.dsHDTB_KV=[]
            //không có xử lý với hàm này

        },
        async TaoDuLieu_HDBANCHEO(themmoi){
            this.dsHDBANCHEO=[]
            var object=createObjectHD_BANCHEO()
            object.HDTB_BAN=this.hdtb_id
            object.TINH_THICONG = this.tinhthicong_selected
            object.TINH_BAN = this.$root.token.getPhanVungID()
            object.THUEBAO_ID_BAN = this.thuebao_id
            object.TRANG_THAI = 0
            object.LOAIHD_BANCHEO = 1

            if(this.khachhang_id!=0){
                object.KHACHHANG_ID_BAN = this.khachhang_id
                let khachhangtc_id=await this.fn_lay_khachhangid_thicong(this.tinhthicong_selected, this.khachhang_id)
                if(khachhangtc_id!=0){
                    object.KHACHHANG_ID_TC = khachhangtc_id
                }
            }

            this.dsHDBANCHEO.push(object)

            // hop dong thue bao ban cheo
            this.dsHDTBBANCHEO=[]

            var object1=createObjectHDTB_BANCHEO()

            //Copy HDTB to HDTB_BanCheo
            let hdtb=this.dsHDTB[0]
            let keySources=Object.keys(hdtb)
            for(let i=0;i<keySources.length;i++){
                if(object1.hasOwnProperty(keySources[i])){
                    object1[keySources[i]]=hdtb[keySources[i]]
                }
            }

            object1.KIEULD_ID = this.$refs.thongtinthuebao.kieu_ld_selected
            object1.QUAN_ID = this.$refs.thongtinthuebao.dataDiaChiLD.quan_id
            object1.PHUONG_ID = this.$refs.thongtinthuebao.dataDiaChiLD.phuong_id
            object1.KHU_ID = this.$refs.thongtinthuebao.dataDiaChiLD.khu_id
            object1.AP_ID = this.$refs.thongtinthuebao.dataDiaChiLD.ap_id

            object1.QUYTRINH_ID = this.quytrinh_tc_tn
            object1.HUONGGIAO_TN_ID = this.huonggiao_tc_tn


            this.dsHDTBBANCHEO.push(object1)

        },
        async TaoDuLieu_DiaChi_BanCheo(themmoi, kieu){
            this.dsDiaChi_BanCheo=[]
            if(kieu==1||kieu==4){
                var rowDiaChiKH_BC={}
                rowDiaChiKH_BC.DIACHI_ID = this.diachikh_id
                rowDiaChiKH_BC.TINH_ID = this.$refs.thongtinkhachhang.dataDiaChiKH.tinh_id
                rowDiaChiKH_BC.QUAN_ID = this.$refs.thongtinkhachhang.dataDiaChiKH.quan_id
                rowDiaChiKH_BC.PHUONG_ID = this.$refs.thongtinkhachhang.dataDiaChiKH.phuong_id

                rowDiaChiKH_BC.PHO_ID = this.$refs.thongtinkhachhang.dataDiaChiKH.pho_id
                rowDiaChiKH_BC.AP_ID = this.$refs.thongtinkhachhang.dataDiaChiKH.ap_id
                rowDiaChiKH_BC.KHU_ID = this.$refs.thongtinkhachhang.dataDiaChiKH.khu_id

                rowDiaChiKH_BC.SONHA = this.$refs.thongtinkhachhang.dataDiaChiKH.sonha
                rowDiaChiKH_BC.DIACHI = this.$refs.thongtinkhachhang.dataDiaChiKH.diachi
                rowDiaChiKH_BC.LOAI_DC = 1

                this.dsDiaChi_BanCheo.push(rowDiaChiKH_BC)
            }
            if(kieu==3||kieu==4){
                var rowDiachiLD_BC={}
                rowDiachiLD_BC.DIACHI_ID = this.diachild_id
                rowDiachiLD_BC.TINH_ID = this.$refs.thongtinthuebao.dataDiaChiLD.tinh_id
                rowDiachiLD_BC.QUAN_ID = this.$refs.thongtinthuebao.dataDiaChiLD.quan_id
                rowDiachiLD_BC.PHUONG_ID = this.$refs.thongtinthuebao.dataDiaChiLD.phuong_id

                rowDiachiLD_BC.PHO_ID = this.$refs.thongtinthuebao.dataDiaChiLD.pho_id
                rowDiachiLD_BC.AP_ID = this.$refs.thongtinthuebao.dataDiaChiLD.ap_id
                rowDiachiLD_BC.KHU_ID = this.$refs.thongtinthuebao.dataDiaChiLD.khu_id

                rowDiachiLD_BC.SONHA = this.$refs.thongtinthuebao.dataDiaChiLD.sonha
                rowDiachiLD_BC.DIACHI = this.$refs.thongtinthuebao.dataDiaChiLD.diachi
                rowDiachiLD_BC.LOAI_DC = 4

                this.dsDiaChi_BanCheo.push(rowDiachiLD_BC)

                var rowDiaChiTB_BC={}
                rowDiaChiTB_BC.DIACHI_ID = this.diachitb_id
                rowDiaChiTB_BC.TINH_ID = this.$refs.thongtinthuebao.dataDiaChiTB.tinh_id
                rowDiaChiTB_BC.QUAN_ID = this.$refs.thongtinthuebao.dataDiaChiTB.quan_id
                rowDiaChiTB_BC.PHUONG_ID = this.$refs.thongtinthuebao.dataDiaChiTB.phuong_id

                rowDiaChiTB_BC.PHO_ID = this.$refs.thongtinthuebao.dataDiaChiTB.pho_id
                rowDiaChiTB_BC.AP_ID = this.$refs.thongtinthuebao.dataDiaChiTB.ap_id
                rowDiaChiTB_BC.KHU_ID = this.$refs.thongtinthuebao.dataDiaChiTB.khu_id

                rowDiaChiTB_BC.SONHA = this.$refs.thongtinthuebao.dataDiaChiTB.sonha
                rowDiaChiTB_BC.DIACHI = this.$refs.thongtinthuebao.dataDiaChiTB.diachi
                rowDiaChiTB_BC.LOAI_DC = 3

                this.dsDiaChi_BanCheo.push(rowDiaChiTB_BC)

            }
        },
        async TaoDuLieu_TinhTC(){
            this.hdkh_tc_id = await this.get_key_bancheo(this.tinhthicong_selected, 'HD_KHACHHANG')
            await this.TaoDuLieu_HDKH_TinhTC()
            // TaoDuLieu_HDBANCHEO_TinhTC();
            // TaoDuLieu_HDTB_TinhTC();
            await this.TaoDuLieu_THONGTIN_TT_TinhTC()
            // TaoDuLieu_HDTBDV_TinhTC();
            // TaoDuLieu_HDTB_ADSL_TinhTC();
            // TaoDuLieu_DiaChi_TinhTC();
            // //CapNhat_HDTB_ID_HDTB_KV();
            // tao_HDTB_HEN_TinhTC();

        },
        async TaoDuLieu_HDKH_TinhTC(){
            this.dsHDKH=[]
            var object=createObjectHDKH()
            //map_id


        },
        //Không có dùng
        async TaoDuLieu_THONGTIN_TT_TinhTC(){
            //dsTTTT = new THONGTIN_TT_DATA();
            this.dsTTTT=[]
        },
        async KiemTraDL_KhachHang(){
            if(this.$refs.thongtinkhachhang.input_so_dt.trim()==''){
                this.$toast.error('Bạn chưa nhập số điện thoại liên hệ của khách hàng !')
                this.$refs.thongtinkhachhang.focusSoDT()
                return false
            }
            if(this.$root.token.getMaTinh()=='HPG'){
                if(this.$refs.thongtinkhachhang.input_tenkh.trim()==''){
                    this.$toast.error('Hãy nhập tên khách hàng !')
                    this.$refs.thongtinkhachhang.focusTenKH()
                    return false
                }
                if(this.$refs.thongtinkhachhang.input_diachi_kh==''||this.$refs.thongtinkhachhang.dataDiaChiKH.quan_id==0||this.$refs.thongtinkhachhang.dataDiaChiKH.phuong_id==0){
                    this.$toast.error('Hãy nhập địa chỉ khách hàng!')
                    this.$refs.thongtinkhachhang.focusDiaChiKH()
                    return false
                }
                if(!this.$refs.thongtinkhachhang.nganhnghe_selected){
                    this.$toast.error('Bạn chưa nhập ngành nghề !')

                    return false
                }
                if(this.$refs.thongtinkhachhang.dataTieuNganh.TNC1_ID==0||this.$refs.thongtinkhachhang.dataTieuNganh.TNC2_ID==0||
                    this.$refs.thongtinkhachhang.dataTieuNganh.TNC3_ID==0||this.$refs.thongtinkhachhang.dataTieuNganh.TNC4_ID==0){
                        this.$toast.error('Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !')
                        return false
                }

            }
            if(this.$refs.thongtinkhachhang.input_ma_gd.trim()!=''){
                let result=await this.kiemtra_makh_mtt(this.$refs.thongtinkhachhang.input_ma_gd.trim(),3,this.loaihd_id)
                if(result==0){
                    this.$toast.error('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
                    this.$refs.thongtinkhachhang.focusMaGD()
                    return false
                }
            }
            return true
        },
        async KiemTraDL_ThueBao(){
            if(this.$refs.thongtinthuebao.input_ten_tb.trim()==''){
                this.$toast.error('Hãy nhập tên thuê bao !')
                this.$refs.thongtinthuebao.focusTenTB()
                return false
            }   
            if(((this.$refs.thongtinthuebao.input_diachi_ld==''||this.$refs.thongtinthuebao.dataDiaChiLD.quan_id==0||
                this.$refs.thongtinthuebao.dataDiaChiLD.phuong_id==0||(this.$refs.thongtinthuebao.dataDiaChiLD.pho_id+
                this.$refs.thongtinthuebao.dataDiaChiLD.ap_id+this.$refs.thongtinthuebao.dataDiaChiLD.khu_id)==0)&&!this.isTruocBH)||this.$refs.thongtinthuebao.input_diachi_ld==''){
                    this.$toast.error('Hãy nhập địa chỉ lắp đặt!')
                    this.$refs.thongtinthuebao.focusDiaChiLD()
                    return false

            }
            if(((this.$refs.thongtinthuebao.input_diachi_tb==''||this.$refs.thongtinthuebao.dataDiaChiTB.quan_id==0||
                this.$refs.thongtinthuebao.dataDiaChiTB.phuong_id==0||(this.$refs.thongtinthuebao.dataDiaChiTB.pho_id+
                this.$refs.thongtinthuebao.dataDiaChiTB.ap_id+this.$refs.thongtinthuebao.dataDiaChiTB.khu_id)==0)&&!this.isTruocBH)||this.$refs.thongtinthuebao.input_diachi_tb==''){
                    this.$toast.error('Hãy nhập địa chỉ thuê bao!')
                    this.$refs.thongtinthuebao.focusDiaChiTB()
                    return false
            }
            if(this.$refs.thongtinthuebao.donvi_tc_selected==null){
                this.$toast.error('Chưa có đơn vị thi công, bạn hãy kiểm tra lại!')
                return false
            }
            if(this.loaihd_id!=LoaiHopDong.KHAOSATDATMOI&&this.loaihd_id!=LoaiHopDong.TIEPNHANHD){
                if(this.$refs.thongtinkhachhang.input_matb==''){
                    this.$toast.error('Hãy nhập số máy/acc!')
                    return false
                }
            }

            // if (Convert.ToInt32(cboKieuLD.SelectedValue) == KieuLapDat.KHAOSATCDLH)
            // {
            //     var d = bangts.GetDataSql("select 1 from " + DatabaseConstants.DB2 + ".hd_thuebao where thuebao_id = " + thuebao_id + " and tthd_id not in (6,7)");
            //     if (d.Rows.Count > 0)
            //     {
            //         Message_Box.ShowWarning("Thuê bao đang có hợp đồng khác chưa hoàn thiện, bạn không thể lập hợp đồng!");
            //         txtMaTB.Focus();
            //         return false;
            //     }
            // }
            if(this.$refs.thongtinthuebao.loaihinh_selected==null||this.$refs.thongtinthuebao.loaihinh_selected==0){
                this.$toast.error('Phải chọn loại hình thuê bao để khảo sát.')
                return false
            }
            if(this.ds_donvi_tinhtc.length==0&&this.tinhtc_donvi_id==-1){
                this.$toast.error('Địa chỉ thi công này chưa được gán Loại đơn vị Trạm thi công.')
                return false
            }

            return true
        },
        async searchMaKH(ma_kh){
           this.makh_web=ma_kh
            let ds=await this.lay_thongtin_khachhang_nhanquyen(ma_kh)
            if(ds.length>0){
                await this.Clear()
                await this.HienThiTT_DanhBa(ds[0])
                this.$refs.thongtinkhachhang.focusMaKH()
                this.isTruocBH=false

            }else if(this.ts_kt_tthdkhdn==1){
                let ds=await this.lay_tt_khdn_theo_makh(ma_kh)
                if(ds.length>0){
                    await this.Clear()
                    await this.HienThiTT_DanhBa_KHDN(ds[0])
                    this.makh_web = ma_kh
                    this.isTruocBH = true
                }
            }else{
                //this.SetButton(0)
                this.$toast.error('Không tìm thấy thông tin khách hàng!')
            }
        },
        async HienThiTT_DanhBa_KHDN(item){
            this.khachhang_id=0
            await this.$refs.thongtinkhachhang.HienThiTT_DanhBa_KHDN(item)
            await this.$refs.thongtinthuebao.HienThiTT_DanhBa_KHDN(item)

            if(item.diachi_id){
                var diachi_web = await SurveyRequestCrossSaleAPI.post_lay_diachi_theo_diachi_id(this.axios, { vdiachi_id: item.diachi_id })
                .then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        return response.data.data
                    } else return []
                })
                .catch(() => {
                    return []
                })
               await this.HienThiTTDiaChi(diachi_web, 1)
            }
        },
        async HienThiTT_DanhBa(item){
            this.khachhang_id=item.khachhang_id?item.khachhang_id:0
            if (this.loaihd_id != LoaiHopDong.KHAOSATDATMOI){
                this.thuebao_id=item.thuebao_id?item.thuebao_id:0
            }else{
                this.thuebao_id=0
            }
            await this.$refs.thongtinkhachhang.HienThiTT_DanhBa(item)
            await this.$refs.thongtinthuebao.HienThiTT_DanhBa(item)

            let ds=await this.lay_ds_diachi_theo_khachhang(this.khachhang_id)

            await this.HienThiTTDiaChi(ds, 1)

        },
        async HienThiTTDiaChi(ds, kieu){
            console.log('HienThiTTDiaChi', ds)
            if(ds.length>0){
                if(kieu==1){
                    //Địa chỉ khách hàng
                    let item=ds[0]
                    Object.assign(this.$refs.thongtinkhachhang.dataDiaChiKH,{
                        diachi_id:item.diachi_id?item.diachi_id:0,
                        tinh_id:item.tinh_id?item.tinh_id:0,
                        quan_id:item.quan_id?item.quan_id:0,
                        phuong_id:item.phuong_id?item.phuong_id:0,
                        pho_id:item.pho_id?item.pho_id:0,
                        ap_id:item.ap_id?item.ap_id:0,
                        khu_id:item.khu_id?item.khu_id:0,
                        dacdiem_id:item.dacdiem_id?item.dacdiem_id:0,
                        sonha:item.sonha?item.sonha.toString():'',
                        diachi:item.diachi?item.diachi.toString():''
                    })
                }else if(kieu==2){
                    //Chưa rõ nơi dùng

                    // diachitt_id = Convert.ToInt64(ds.Tables[0].Rows[0]["diachi_id"]);
                    // tinhtt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["tinh_id"]);
                    // quantt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["quan_id"]);
                    // if (ds.Tables[0].Rows[0]["phuong_id"].ToString().Trim() != "")
                    //     phuongtt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["phuong_id"]);
                    // else
                    //     phuongtt_id = 0;

                    // if (ds.Tables[0].Rows[0]["pho_id"].ToString() != "")
                    //     phott_id = Convert.ToInt32(ds.Tables[0].Rows[0]["pho_id"]);
                    // else
                    //     phott_id = 0;
                    // if (ds.Tables[0].Rows[0]["ap_id"].ToString() != "")
                    //     aptt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["ap_id"]);
                    // else
                    //     aptt_id = 0;
                    // if (ds.Tables[0].Rows[0]["khu_id"].ToString().Trim() != "")
                    //     khutt_id = Convert.ToInt32(ds.Tables[0].Rows[0]["khu_id"]);
                    // else
                    //     khutt_id = 0;
                    // sonhatt = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[0]["sonha"].ToString());
                }else if(kieu==3){
                    let item=ds[0]
                    //Địa chỉ thuê bao
                    this.$refs.thongtinthuebao.dataDiaChiTB={
                        diachi_id:item.diachi_id?item.diachi_id:0,
                        tinh_id:item.tinh_id?item.tinh_id:0,
                        quan_id:item.quan_id?item.quan_id:0,
                        phuong_id:item.phuong_id?item.phuong_id:0,
                        pho_id:item.pho_id?item.pho_id:0,
                        ap_id:item.ap_id?item.ap_id:0,
                        khu_id:item.khu_id?item.khu_id:0,
                        dacdiem_id:0,
                        sonha:item.sonha?item.sonha.toString():'',
                        diachi:''
                    }
                    //Địa chỉ lắp đặt
                    this.$refs.thongtinthuebao.dataDiaChiLD={
                        diachi_id:item.diachild_id?item.diachild_id:0,
                        tinh_id:item.tinhld_id?item.tinhld_id:0,
                        quan_id:item.quanld_id?item.quanld_id:0,
                        phuong_id:item.phuongld_id?item.phuongld_id:0,
                        pho_id:item.phold_id?item.phold_id:0,
                        ap_id:item.apld_id?item.apld_id:0,
                        khu_id:item.khuld_id?item.khuld_id:0,
                        dacdiem_id:0,
                        sonha:item.sonha_ld?item.sonha_ld.toString():'',
                        diachi:''
                    }

                    this.ds_donvi_tinhtc=await this.lay_donvi_theo_loaidv_diachi_ld({
                        tinhthicong_id: this.tinhthicong_selected,
                        p_loaidv_id: LOAI_DV.TRAM_VT,
                        p_phuong_id: this.$refs.thongtinthuebao.dataDiaChiLD.phuong_id,
                        p_pho_id: this.$refs.thongtinthuebao.dataDiaChiLD.pho_id,
                        p_ap_id: this.$refs.thongtinthuebao.dataDiaChiLD.ap_id,
                        p_khu_id: this.$refs.thongtinthuebao.dataDiaChiLD.khu_id,
                        p_dacdiem_id: 0
                    })
                    if(this.ds_donvi_tinhtc.length>0){
                        this.$refs.thongtinthuebao.input_dvt_tc=this.ds_donvi_tinhtc[0].ten_dv?this.ds_donvi_tinhtc[0].ten_dv.toString():''
                        this.tinhtc_donvi_id=this.ds_donvi_tinhtc[0].donvi_id?this.ds_donvi_tinhtc[0].donvi_id:0
                    }
                }
            }
        },
        async handleDonViThiCong(){
            this.ds_donvi_tinhtc=await this.lay_donvi_theo_loaidv_diachi_ld({
                tinhthicong_id: this.tinhthicong_selected,
                p_loaidv_id: LOAI_DV.TRAM_VT,
                p_phuong_id: this.$refs.thongtinthuebao.dataDiaChiLD.phuong_id,
                p_pho_id: this.$refs.thongtinthuebao.dataDiaChiLD.pho_id,
                p_ap_id: this.$refs.thongtinthuebao.dataDiaChiLD.ap_id,
                p_khu_id: this.$refs.thongtinthuebao.dataDiaChiLD.khu_id,
                p_dacdiem_id: 0
            })
            if(this.ds_donvi_tinhtc.length>0){
                this.$refs.thongtinthuebao.input_dvt_tc=this.ds_donvi_tinhtc[0].ten_dv?this.ds_donvi_tinhtc[0].ten_dv.toString():''
                this.tinhtc_donvi_id=this.ds_donvi_tinhtc[0].donvi_id?this.ds_donvi_tinhtc[0].donvi_id:0
            }
        },
        changeMaKH(value){
            if (value != makh_web){
                this.makh_web = ""
                this.isTruocBH = false
            }
        },
        async onRowSelected(item){
            this.tinhthicong_selected=item.tinhtc_id?item.tinhtc_id:0
            await this.changeTinhThiCong()
            this.hdtb_id=item.hdtb_id?item.hdtb_id:0
            this.hdkh_id=item.hdkh_id?item.hdkh_id:0
            this.ds=await this.lay_ds_hopdong_tb_theo_hdtb_id(this.hdtb_id)
            console.log('ds',this.ds)
            await this.HienThiTTHopDongTB(item)
            let ds1=await this.lay_ds_hopdong_theo_ma_gd({
                ma_gd: item.ma_gd,
                loaihd_id: this.loaihd_id,
                donvi_id: this.$root.token.getDonViID(),
                //donvi_id:0,
                //tthd_id: TrangThaiHD.MOI,
                tthd_id:0,
                nhanvien_id: this.$root.token.getNhanVienID(),
                donvi_dl_id: 0
            })
            
            if(ds1.length>0){
                this.khachhang_id=ds1[0].khachhang_id?ds1[0].khachhang_id:0

                await this.$refs.thongtinkhachhang.HienThiTTKhachHang(ds1[0])

                let ds_dc=await this.lay_ds_diachi_theo_hdkh(this.hdkh_id)
                await this.HienThiTTDiaChi(ds_dc, 1)

                //Bỏ nút này không dùng, theo khảo sát sheet
                // if((this.loaiphieu==1||this.loaiphieu==2)&&(this.dichvuvt_id==DichVuVienThong.METRONET||
                //     this.dichvuvt_id==DichVuVienThong.METRONET||this.dichvuvt_id==DichVuVienThong.TSL)){

                //     this.actions[this.actions.findIndex(x=>x.id=='chuyennet')].visible=true
                //     //hard
                //     this.actions[this.actions.findIndex(x=>x.id=='chuyennet')].active=false
                //         //             string str = "select 1 from {?DB2}.giaophieu where hdtb_id = :vhdtb_id and huonggiao_id = 9999 and ttph_id in (1)";

                //         //             var dt_count = bancheo.GET_DATA_SQL(str, "vhdtb_id", hdtb_id).Rows.Count;
                //         //             if (dt_count <= 0)
                //         //                 tsbtnChuyenNet.Enabled = true;
                //         //             else
                //         //                 tsbtnChuyenNet.Enabled = false;

                // }else{
                //     this.actions[this.actions.findIndex(x=>x.id=='chuyennet')].visible=false
                // }
            }

        },
        async changeLoaiPhieu(event){
            this.loaiphieu=Number(event.target.value)
            //handle
            if(this.loaiphieu==0){
                //clear col
                this.dataSources=[]
                this.Clear()

                const indexKQKS=this.columns.findIndex(x=>x.fieldName=='ketquaks')
                if(indexKQKS>-1){
                    this.columns.splice(indexKQKS, 1)
                }
                const indexND=this.columns.findIndex(x=>x.fieldName=='noidung')
                if(indexND>-1){
                    this.columns.splice(indexND, 1)
                }


                for(let i=0;i<this.actions.length;i++){
                    if(this.actions[i].id!='layds'){
                        this.actions[i].visible=true
                    }
                }
                this.SetButton(-1)
                await this.HienThiDanhSacHDTB()
            }else if(this.loaiphieu==1){
                //
                //clear col
                this.dataSources=[]
                this.Clear()
                const indexKQKS=this.columns.findIndex(x=>x.fieldName=='ketquaks')
                if(indexKQKS>-1){
                    this.columns.splice(indexKQKS, 1)
                }
                const indexND=this.columns.findIndex(x=>x.fieldName=='noidung')
                if(indexND>-1){
                    this.columns.splice(indexND, 1)
                }

                await this.HienThiDanhSacHDTB()
                for(let i=0;i<this.actions.length;i++){
                    if(this.actions[i].id!='layds'){
                        this.actions[i].visible=false
                    }
                }
                //

            }else if(this.loaiphieu==2){
                this.columns.push({
                    fieldName:'ketquaks',
                    headerText:'Kết quả khảo sát',
                    allowFiltering:true
                })
                this.columns.push({
                    fieldName:'noidung',
                    headerText:'Nội dung',
                    allowFiltering:true
                })
                await this.HienThiDanhSacHDTB()
                for(let i=0;i<this.actions.length;i++){
                    if(this.actions[i].id!='layds'){
                        this.actions[i].visible=false
                    }
                }
            }
        },
        initHeader(){
            if(this.loaihd_id==LoaiHopDong.KHAOSATDATMOI){
                this.header.title='TIẾP NHẬN YÊU CẦU KHẢO SÁT LẮP ĐẶT MỚI'
            }else if(this.loaihd_id==LoaiHopDong.KHAOSATDC){
                this.header.title='TIẾP NHẬN YÊU CẦU KHẢO SÁT DỊCH CHUYỂN'
            }else if(this.loaihd_id==LoaiHopDong.KHAOSATCDLH){
                this.header.title='TIẾP NHẬN YÊU CẦU KHẢO SÁT THAY ĐỔI LOẠI HÌNH'
            }else if(this.loaihd_id==LoaiHopDong.TIEPNHANHD){
                this.header.title='TIẾP NHẬN YÊU CẦU KÝ HỢP ĐỒNG TẠI ĐỊA CHỈ KHÁCH HÀNG'
            }
        },
         async lay_thongtin_khachhang_nhanquyen(ma_kh){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_thongtin_khachhang_nhanquyen(this.axios, ma_kh)
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
        async lay_tt_khdn_theo_makh(ma_kh){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_tt_khdn_theo_makh(this.axios, ma_kh)
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
        async lay_ds_tinh_thicong_combobox(){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_tinh_thicong_combobox(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort((a,b)=>a.tinh_id-b.tinh_id)
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_thamso_md(kieu){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_thamso_md(this.axios, kieu)
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
        async lay_ds_ks_bc(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_ks_bc(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{ if(x.ketquaks&&x.ketquaks==1){ x.ketquaks='Đủ điều kiện' }else{ x.ketquaks='Không đủ điều kiện' } return x })
                }else{
                    return []
                }
            }catch(e){
                console.log(e)
                this.loading(false)
                return []
            }
        },
        async lay_ds_hopdong_tb_theo_hdtb_id(hdtb_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_hopdong_tb_theo_hdtb_id(this.axios, hdtb_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                console.log(e)
                this.loading(false)
                return []
            }
        },
        async fn_tt_giaophieu(hdtb_id, defaultValue){
            try{
                this.loading(true)
                let response=await SurveyRequestCrossSaleAPI.fn_tt_giaophieu(this.axios, hdtb_id)
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
        async lay_ds_hopdong_theo_ma_gd(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_hopdong_theo_ma_gd(this.axios, data)
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
        async lay_ds_diachi_theo_khachhang(khachhang_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_diachi_theo_khachhang(this.axios, khachhang_id)
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
        async lay_ds_diachi_theo_hdkh(hdkh_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_diachi_theo_hdkh(this.axios, hdkh_id)
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
        async lay_donvi_theo_loaidv_diachi_ld(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_donvi_theo_loaidv_diachi_ld(this.axios, data)
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
        async kiemtra_makh_mtt(ma_kh, kieu, loaihd_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.kiemtra_makh_mtt(this.axios, ma_kh, kieu, loaihd_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },
        async lay_ds_diachi_bc_theo_hdtb_id(hdtb_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.lay_ds_diachi_bc_theo_hdtb_id(this.axios, hdtb_id)
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
        async getKey(keyname){
            try{
                let response = await SurveyRequestCrossSaleAPI.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async get_key_bancheo(tinhthicong_id, keyname){
            try{
                let response = await SurveyRequestCrossSaleAPI.get_key_bancheo(this.axios, tinhthicong_id, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        //
        async lay_magd(loaihd_id){
            try{
                let response = await SurveyRequestCrossSaleAPI.lay_magd(this.axios, loaihd_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                return ''
            }
        },
        async sp_layhuonggiao_tiepnhan(loaihd_id, kieuld_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_layhuonggiao_tiepnhan(this.axios, loaihd_id, kieuld_id)
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
        async layhuonggiao_tiepnhan_bancheo_v2(tinhthicong_id, loaihd_id, kieuld_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.layhuonggiao_tiepnhan_bancheo_v2(this.axios, tinhthicong_id, loaihd_id, kieuld_id)
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
        async fn_lay_khachhangid_thicong(tinhthicong_id, khachhang_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.fn_lay_khachhangid_thicong(this.axios, tinhthicong_id, khachhang_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },
        async sp_capnhat_hopdong_ks(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_capnhat_hopdong_ks(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
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
        async sp_themmoi_hopdong_ks(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_themmoi_hopdong_ks(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
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
        async fn_ins_update_hd_datmoi_bancheo_v2(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.fn_ins_update_hd_datmoi_bancheo_v2(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    let result=JSON.parse(response.data.data)
                    if(result.ERROR_CODE==1){
                        return true
                    }else{
                        this.$toast.error(result.MESSAGE)
                        return false
                    }
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
        async xoa_hdtb(hdtb_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.xoa_hdtb(this.axios, hdtb_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá thuê bao không thành công')
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
                    this.$toast.error('Đã xảy ra lỗi, xoá thuê bao không thành công')
                }
                return false
            }
        },
        async xoa_hdkh_v2(hdkh_id, loaihd_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.xoa_hdkh_v2(this.axios, hdkh_id, loaihd_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
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
                    this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                }
                return false
            }
        },
        //Thêm YC (Thêm TB)
        async sp_themmoi_hopdong_ks_v2(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_themmoi_hopdong_ks_v2(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return true
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi')
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
                    this.$toast.error('Đã xảy ra lỗi')
                }
                return false
            }
        },
        async sp_lay_ds_makh_theo_sogt(so_gt, hdkh_id){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.sp_lay_ds_makh_theo_sogt(this.axios, so_gt, hdkh_id)
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
        async fn_tao_dulieu_chuyenphieu_ks_bancheo(data){
            try{
                this.loading(true)
                let response = await SurveyRequestCrossSaleAPI.fn_tao_dulieu_chuyenphieu_ks_bancheo(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    let result=JSON.parse(response.data.data)
                    if(result.ERROR_CODE==1){
                        return true
                    }else{
                        this.$toast.error(result.MESSAGE)
                        return false
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi')
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
                    this.$toast.error('Đã xảy ra lỗi')
                }
                return false
            }
        },
        async changeTinhThiCong(){
            // tinh = bancheo.tinh_thicong(int.Parse(cboTinh.EditValue.ToString()));
            this.$refs.thongtinthuebao.input_diachi_tb=''
            this.$refs.thongtinthuebao.input_diachi_ld=''
            this.$refs.thongtinthuebao.dataDiaChiTB={
              diachi_id:0,
              tinh_id:0,
              quan_id:0,
              phuong_id:0,
              pho_id:0,
              ap_id:0,
              khu_id:0,
              dacdiem_id:0,
              sonha:'',
              diachi:''
            }
            this.$refs.thongtinthuebao.dataDiaChiLD={
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                diachi:''
            }
            this.$refs.thongtinthuebao.input_dvt_tc=''
            this.$refs.thongtinthuebao.tinhtc_donvi_id=-1
        },
        async HienThiDanhSacHDTB(){
            this.hdkh_id = 0
            var tthd_id = 0
            if(this.loaiphieu==0){
                tthd_id = TrangThaiHD.MOI
            }else if(this.loaiphieu==1){
                tthd_id = TrangThaiHD.DANG_THI_CONG
            }else if(this.loaiphieu==2){
                tthd_id = TrangThaiHD.HOAN_THANH
            }

            //lay_ds_ks_bc
            let dsDanhSach=await this.lay_ds_ks_bc({
                in_hdkh_id: this.hdkh_id,
                in_tthd_id: tthd_id,
                vloaihd_id: this.loaihd_id,
                vtinhtc_id: 0,
                vnhanvien_id: this.$root.token.getNhanVienID()
            })

            if(dsDanhSach.length>0){
                //this.actions[this.actions.findIndex(x=>x.id=='xoa_yc')].active=true
                this.dataSources=dsDanhSach
                //focus row auto grid
            }else{
                this.dataSources=[]
                //this.actions[this.actions.findIndex(x=>x.id=='xoa_yc')].active=false
                this.Clear()
            }

        },
        async initDuLieu(){
            //Handle form trùng
            let endPath=this.$router.currentRoute.path.split('/').pop()
            if(endPath=='SurveyRequestCrossSale'){
                //Tiếp nhận yêu cầu khảo sát lắp đặt bán chéo
                this.loaihd_id=LoaiHopDong.KHAOSATDATMOI
            }
            this.initHeader()
            //Tỉnh thi công
            this.ds_tinhthicong=await this.lay_ds_tinh_thicong_combobox()
            // if(this.ds_tinhthicong.length>0){
            //     this.tinhthicong_selected=this.ds_tinhthicong[0].tinh_id
            // }
            
            await this.$refs.thongtinkhachhang.initDuLieu()
            await this.$refs.thongtinthuebao.initDuLieu()
            //khởi tạo tham số
            let ds=await this.lay_ds_thamso_md(0)
            if(ds.length>0){
                const index=ds.findIndex(x=>x.ma_ts=='THEM_TT_HDKHDN'&&x.ten_ts=='1')
                if(index>-1){
                    this.ts_kt_tthdkhdn=1
                }
            }
            //
            this.SetButton(-1)
            //
            await this.HienThiDanhSacHDTB()
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
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
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
</style>