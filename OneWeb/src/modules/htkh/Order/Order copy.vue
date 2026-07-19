<template src="./Order.html"></template>

<style scoped src="./Order.scss"></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue';
import SearchAccountModal from './components/popups/SearchAccountModal.vue'
import SearchAccountModal_KH from './components/popups/SearchAccountModal_KH.vue'
import DataGrid2 from '@/components/Controls/DataGrid2'
//import ModalChonDiaChi from './components/popups/ChonDiaChi'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import ThayDoiKhuVucQuanLyThueBaoModal from "@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue";
import ChonDonViModal from '@/modules/contract/setup/SurveyRequest/popups/ChonDonVi/ChonDonViModal.vue'
import ThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
import {
    required,
    maxLength,
    minLength,
    numeric
} from "vuelidate/lib/validators"
import {
    DichVuVienThong,
    LoaiHopDong,
    LoaiHinhTB,
    KHOANMUC_TT,
    Loai_NV,
    LOAI_DV,
    LOAI_KV,
    TrangThaiHD,
    TRANGTHAI_DONGBO,
    KieuLapDat,
} from "./define/index.js";
export default {
    components: {
        VueElement,
        DataGridCustom,
        SearchContract,
        SearchAccountModal_KH,
        SearchAccountModal,
        ModalChonDiaChi,
        ThongTinHenKhachHang,
        ThayDoiKhuVucQuanLyThueBaoModal,
        ChonDonViModal,
        DataGrid2
    },
    name: 'Order',

    async created() {
        this.SetButton(-1);
        this.listGioitinh = [{
                id: 0,
                ten: "Nam",
            },
            {
                id: 1,
                ten: "Nữ",
            },
            {
                id: -1,
                ten: "Khác",
            }
        ];
        this.cbbGioitinh = this.listGioitinh[0].id;
    },
    data() {
        return {
            cbo_NganHang: {
                items: [],
                selected: null
            },
            dateFormat: 'dd/MM/yyyy',
            formData: {
                thuebao_id: 0,
                dichvuvt_id: 0,
                ma_tb: ''
            },
            ...collums,
            header: {
                title: "Đài HTKH tiếp nhận đơn hàng",
                list: [{
                        name: "HTKH",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Đài HTKH tiếp nhận đơn hàng",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            btn_Enabled: {
                nhapMoi: true,
                ghiLai: false,
                xoa: false,
                huy: false,
                ganquyen: false,
                ganloaisanpham: false,
                duyet: false
            },
            listGioitinh: [],
            lstDSDichvu: [],
            lstLoaiHinhTB: [],
            lstLoaiKH: [],
            lstKieuYC: [],
            lstDoituong: [],
            lstDonvixuly: [],
            donvi_kd_id: 0,
            lstKhuvuc: [],
            lstDaiVT: [],
            lstNhanVien: [],
            lstLoaiGT: [],
            DSPhieu: [],
            listQuoctich: [],
            gviewDanhSach: [],
            txtMaGD: "",
            txtMaHD: "",
            khuvuc_id: 0,
            khuvuc: {},
            dsHDTB_KV: [],
            dtpNgayYC: moment().format('DD/MM/YYYY'),
            txtNgayYC: moment().format('DD/MM/YYYY'),
            txtNgayCap: moment().format('DD/MM/YYYY'),
            dichvuvt_id: 0,
            doituong_id: 1,
            txtSoGT: "",
            txtNoiCap: "",
            cboNhanVien: 0,
            txtGhiChuTB:"",
            hdkh_id: 0,
            hdtb_id: 0,
            khachhang_id: 0,
            txtTongTienHD: 0,
            thuebao_id: 0,
            txtSoSim1: "",
            dtpNgayLapHD: moment().format('DD/MM/YYYY'),
            dtpNgayYeuCau: moment().format('DD/MM/YYYY'),
            txtNgayHetHan: moment().format('DD/MM/YYYY'),
            txtNgaySN: moment().format('DD/MM/YYYY'),
            txtSoSimCu: "",
            somay: "",
            sosim_phu: "",
            txtKhuVuc: "",
            kieuld_id: 0,
            donvi_id: 0,
            tocdo_id: 0,
            txtCuly: "0",
            ltb_adsl_id: 1,
            TramTC_ID: 0,
            kh_cd: 0,
            ltb_adsl_cu_id: 0,
            ltb_adsl_moi_id: 0,
            dsDonviXL: [],
            tinhkh_id: 0,
            quankh_id: 0,
            khuvuc:{
                DiaDanh:{},
                DiemGD_ID: -1,
                TramTC_ID: -1,
                KhuVuc_ID: -1,
                Ma_KV:null ,
                Ten_KV: null,
                LoaiKV_ID: LOAI_KV.KHUVUC_DIABAN
            },
            khuvucDanhBa: {},
            txtSoDT: "",
            phuongkh_id: 0,
            phokh_id: 0,
            apkh_id: 0,
            sonhakh: "",
            loai_nv_xuly: Loai_NV.NHANVIEN_KINHDOANH,
            txtMaKH: "",
            khlon_id: 1,
            txtTenKH: "",
            txtDiaChiKH: "",
            txtLoai: "",
            txtTinhTP: "",
            kt_diachi:false,
            txtTenTB: "",
            txtSoLuong: "",
            cboDoituongTB: 1,
            phieu_id:0,
            phieu_cha_id:0,
            huonggiaotn_id:20001, //--ttt
            quytrinh_id:0,
            txtDiaChiTB: "",
            txtTienLapMoi: "0",
            txtVatLapmoi: "0",
            txtGhiChu: "",
            txtMaTB: "",
            txtSDT: "",
            txtMST: "",
            khutb_id: 0,
            _CHUYEN_DONVI_TIEPNHAN_ONLINE: false,
            cboDichvuvt_id: 0,
            cboLoaiHinhTB: 0,
            cboKieuYC: 0,
            cboLoaiKH: 0,
            DoiTuong: {
                TU_NHAN: 1
            },
            cbbGioitinh: 0,
            blocktb: "",
            tangtb: "",
            diachild_obj:{},
            diachild: {},
            sophongtb: "",
            txtDiaChiLDMoi:"",
            motadctb: "",
            khuld_id: 0,
            blockld: "",
            tangld: "",
            sophongld: "",
            motadcld: "",
            cbbDoituong: 0,
            cboDonvixuly: 0,
            cboGiayToKH: 0,
            cboQuocTich: 0,
            dtKhuvuc: 0,
            dtTenDVVT: "",
            tinhtt_id: 0,
            quantt_id: 0,
            txtDiaChiLDCu:"",
            dataPopupKhuVuc:{quan_id: 0,phuong_id:0, pho_id:0,ap_id: 0,dacdiem_id: 0,khuvuc_id: 0,donviql_id:0,hdtb_id:0, thuebao_id: 0},
            phuongtt_id: 0,
            phott_id: 0,
            aptt_id: 0,
            sonhatt: "",
            tinhtb_id: 0,
            quantb_id: 0,
            phuongtb_id: 0,
            photb_id: 0,
            lblUC3: {
                Text: "",
                Visibility: true
            },
            aptb_id: 0,
            sonhatb: "",
            tinhld_id: 0,
            quanld_id: 0,
            phuongld_id: 0,
            phold_id: 0,
            cboDaiVT: 0,
            apld_id: 0,
            sonhald: "",
            loaihd_id: 0,
            diachi_id: 0,
            tinh_id: 0,
            quan_id: 0,
            phuong_id: 0,
            dacdiem_id: 0,
            pho_id: 0,
            ap_id: 0,
            khu_id: 0,
            longitude: -1,
            latitude: -1,
            soNha: null,
            dsHDKH: [],
            dsHDTB: [],
            dsHDTBDV: [],
            dsDiaChi: [],
            dsDiaChiHDKH: [],
            dsDiaChiHDTB: [],
            Tag: 0,
            diadanh: {
                diachi_id: 0,Tinh_id: 0, Quan_id : 0, Phuong_id : 0, DacDiem_id : 0, Pho_id : 0,Ap_id : 0, Khu_id : 0,
                Longitude : -1, Latitude: -1,
                SoNha: null
            },
            diadanhTemp:{
                diachi_id: 0,Tinh_id: 0, Quan_id : 0, Phuong_id : 0, DacDiem_id : 0, Pho_id : 0,Ap_id : 0, Khu_id : 0,
                Longitude : -1, Latitude: -1,
                SoNha: null
            },
            LOC_NVDIADANH_NVPX: false,
            LoaiDonViXL: LOAI_DV.DONVIQL_TT,
            lay_nvql_theo_thuebao_id: false,
            dtCTKM: [],
            diachi_obj: {},
            items: {
                diachi: "",
                ma_nvql: "",
                ngay_sn: moment().format('DD/MM/YYYY'),
                ngay_cap: moment().format('DD/MM/YYYY'),
            },
            tsbtnHoanThanh: {
                Enabled: true
            },
            tsbtnNhapMoi: {
                Enabled: true
            },
            tsbtnXoa: {
                Enabled: true
            },
            tsbtnHuyBo: {
                Enabled: true
            },
            tsbtnGhiLai: {
                Enabled: true
            },
            tsbtnXoaTB: {
                Enabled: true
            },
            tsbtnThemTB: {
                Enabled: true
            },
             tsbtnHenLD: {
                Enabled: true
            },
             rdoGTNam: {
                Enabled: true
            },
             rdoGTNu: {
                Enabled: true
            },
            rdoGTNone: {
                Enabled: true
            },
            cboDaiVT:{
                Enabled: true
            },
            tt_nd: {
                ngay_cn: this.$auth.getNgayCapNhat(),
                tentat: "",
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                ma_tinh:  this.$root.token.getMaTinh(),
                //ten_dv:this.$auth.getTenDonVi()
            },
            grid_Kenhban: {
                items: [],
                selected: {}
            },
        }
    },
    mounted() {
        this.khuvucDanhBa = this.khuvuc;
        this.khuvucDanhBa.DiaDanh = this.diadanh;
        this.getTTDanhmuc();
      
        if (this.$route.query.Tag && this.$route.query.Tag !== '') {
            this.Tag = this.$route.query.Tag.replaceAll(' ', '+')
        }

        this.frmTiepNhanYC_18001166_Load();
        this.HienThiDanhSacHDTB("");
        this.getDmKieuYC()

    },
    methods: {
        async frmTiepNhanYC_18001166_Load() {

            if (this.loaihd_id == 0 && this.Tag != null)
                if (this.Tag != "")
                    this.loaihd_id = this.Tag;
            this.dtpNgayYC = this.tt_nd.ngay_cn;
            let params = {
                "p_phanvung_id": 0,
                "p_ds_para": "{\"chuoi\":\"DOITUONG|DICHVU_VT|LOAIHINH_TB|LOAI_GT|LOAI_KH|DANTOC|NGANHNGHE|QUOCTICH\"}"
            }
            let ds = this.GetData(await api.Tiepnhanyc_18001166_load(this.axios, params));
            let ds_json = JSON.parse(ds.result_pOut)

             let chk1 = ds_json.DS_THAMSO_MD.filter(x => x.MA_TS === 'CHUYEN_DONVI_TIEPNHAN_ONLINE')
            if (chk1.length > 0) {
                if (chk1[0].TEN_TS === '1') {
                    this._CHUYEN_DONVI_TIEPNHAN_ONLINE = true
                }
            }
             if (this._CHUYEN_DONVI_TIEPNHAN_ONLINE)
                    this.LoaiDonViXL = 109;
            
            this.lstDaiVT = this.GetData(await api.sp_lay_ds_donvi_ldv(this.axios, {"loaidv_id":this.LoaiDonViXL}));
           
             let chk2 = ds_json.DS_THAMSO_MD.filter(x => x.MA_TS === 'LAY_NVQL_THEO_THUEBAO_ID')
            if (chk2.length > 0) {
                if (chk2[0].TEN_TS === '1') {
                    this.lay_nvql_theo_thuebao_id = true
                }
            }

            let chk3 = ds_json.DS_THAMSO_MD.filter(x => x.MA_TS === 'KT_DIACHI_TNYC')
            if (chk3.length > 0) {
                if (chk3[0].TEN_TS === '1') {
                    this.kt_diachi = true
                }
            }
            let chk = ds_json.DS_THAMSO_MD.filter(x => x.MA_TS === 'LOC_NVDIADANH_NVPX')
            if (chk.length > 0) {
                if (chk[0].TEN_TS === '1') {
                    this.LOC_NVDIADANH_NVPX = true
                }
            }
            this.cboDaiVT.Enabled = false;
        },
        //Hiển thị danh sách hợp đồng thuê bao theo mã giao dịch
        async HienThiDanhSacHDTB(vma_gd)
        {
            let hdkh_id = 0;
            let params = {
                "p_hdkh_id": hdkh_id,
                "p_tthd_id": TrangThaiHD.MOI,
                "p_loaihd_id": this.loaihd_id,
                "p_nhanvien_id": this.tt_nd.nhanvien_id,
                "p_ma_gd": vma_gd
            }
            let dsDanhSach = this.GetData(await api.lay_ds_hdtb_tiepnhan_ks(this.axios, params));
            
            this.gviewDanhSach = dsDanhSach;

            if (dsDanhSach.length > 0)
            {
                this.tsbtnHoanThanh.Enabled = true;
                if (dsDanhSach.length == 1)
                    this.tsbtnXoaTB.Enabled = false;
                else
                    this.tsbtnXoaTB.Enabled = true;
            }
            else
            {
                this.gviewDanhSach = []
            }

        },
        //Láya thông tin danh mục
        async getTTDanhmuc() {
            let rsTTDM = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_cacloai_danhmuc');
            this.lstDoituong = rsTTDM.data.dsDoiTuong;
            this.cboDoituongTB = this.lstDoituong[0].doituong_id;
            this.lstLoaiKH = rsTTDM.data.dsLoaiKH;
            this.cboLoaiKH = this.lstLoaiKH[0].loaikh_id;
            this.lstDSDichvu = rsTTDM.data.dsDichVuVT;
            this.cboDichVuVT = this.lstDSDichvu[0].dichvuvt_id;
            this.dtTenDVVT = this.lstDSDichvu[0].ten_dvvt;
            this.lstLoaiGT = rsTTDM.data.dsLoaiGT;
            this.cboGiayToKH = this.lstLoaiGT[0].loaigt_id;
            this.listQuoctich = rsTTDM.data.dsQuocTich;
            this.cboQuocTich = this.listQuoctich[0].quoctich_id;

            this.getLoaiHinhTB(this.cboDichVuVT);
        },
        //Lấy danh sách loại hình thuê bao
        async getLoaiHinhTB(dvvtid) {
            try {
                await api.getLoaiHinhTB(this.axios, dvvtid)
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000" &&
                            res.data.data !== undefined &&
                            res.data.data.length > 0
                        ) {
                            this.lstLoaiHinhTB = res.data.data;
                            this.cboLoaiHinhTB = this.lstLoaiHinhTB[0].ID;
                            this.getDmKieuYC(this.cboLoaiHinhTB);
                        }
                    });
            } catch (err) {
                this.$toast.error("Lỗi lấy danh sách loại hình: " + err.message);
            }
        },
        //Lấy danh mục kiểu lắp đặt
        async getDmKieuYC(loaitb_id) {
            try {
                await api.lay_ds_tiepnhan_yc(this.axios, {}).then((res) => {
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        this.lstKieuYC = res.data.data;
                        this.cboKieuYC = this.lstKieuYC[0].kieuld_id;
                    }
                });
            } catch (err) {
                this.$toast.error("Lỗi lấy danh sách kiểu lắp đặt: " + err.message);
            }
        },
        //Show popup chọn mã giao dịch 
        showPopupSearchContract() {
            this.modalSearch = {
                loai_hd_id: LoaiHopDong.DAT_MOI,
                trangthai_hd: TrangThaiHD.MOI
            }
            this.$refs.popupFrmTraCuuHopDong.$children[0].clearData();
            this.$refs.popupFrmTraCuuHopDong.$children[0].loadPopup();
            this.$refs.popupFrmTraCuuHopDong.show();
        },
        //Click để mở popup chọn ấp, phường, xã và địa chỉ
        txtDiaChi_ButtonClick() {
            this.diachi_obj.diachi = this.txtDiaChiKH
            this.diachi_obj.tinh_id = this.diadanh.Tinh_id
            this.diachi_obj.quan_id = this.diadanh.Quan_id
            this.diachi_obj.phuong_id = this.diadanh.Phuong_id
            this.diachi_obj.pho_id = this.diadanh.Pho_id
            this.diachi_obj.ap_id = this.diadanh.Ap_id
            this.diachi_obj.khu_id = this.diadanh.Khu_id
            this.diachi_obj.so_nha = this.diadanh.SoNha;
            console.log(this.diachi)
            this.$bvModal.show('popupDiaChi_KH')
        },
        //Lấy địa chỉ
        getDiaChi(data) {
              console.log('Địa chỉ khách hàng')
            console.log(data)
            this.txtDiaChiKH = data.diachimoi;
            this.diadanhTemp.Tinh_id = data.tinh_id;
            this.diadanhTemp.Quan_id = data.quan_id;
            this.diadanhTemp.Phuong_id = data.phuong_id;
            this.diadanhTemp.Pho_id = data.pho_id;
            this.diadanhTemp.Ap_id = data.ap_id;
            this.diadanhTemp.Khu_id = data.khu_id;
            this.diadanhTemp.SoNha = data.dacdiem_id;
            if (this.diadanhTemp.Tinh_id != this.diadanh.Tinh_id || this.diadanhTemp.Quan_id != this.diadanh.Quan_id || this.diadanhTemp.Phuong_id != this.diadanh.Phuong_id
                    || this.diadanhTemp.Pho_id != this.diadanh.Pho_id || this.diadanhTemp.Ap_id != this.diadanh.Ap_id || this.diadanhTemp.Khu_id != this.diadanh.Khu_id)
                {
                    this.khuvuc.DiaDanh = this.diadanh = this.diadanhTemp;
                    this.LayDV_QuanLy();
                }
        },
        //Click chọn địa chỉ mới
        txtDiaChiLDMoi_ButtonClick()
        {
            console.log('txtDiaChiLDMoi_ButtonClick ')
            let diachild = this.diadanh;
            // diachild_obj.tinh_id = diachild.Tinh_id;
            // diachild_obj.quan_id = diachild.Quan_id;
            // diachild_obj.phuong_id = diachild.Phuong_id;
            // diachild_obj.pho_id = diachild.Pho_id;
            // diachild_obj.khu_id = diachild.Khu_id;
            // diachild_obj.ap_id = diachild.Ap_id;
            // diachild_obj.sonha = diachild.SoNha;
            // diachild_obj.diachi = this.txtDiaChiLDMoi;
            this.$bvModal.show('popupDiaChi_LD')
        },
        //Lấy địa chỉ
        getDiaChi_ld(data) {
                let diachild = {};
                diachild.Tinh_id = data.tinh_id;
                diachild.Quan_id = data.quan_id;
                diachild.Phuong_id = data.phuong_id;
                diachild.Pho_id = data.pho_id;
                diachild.Ap_id = data.ap_id;
                diachild.Khu_id = data.khu_id;
                diachild.SoNha = data.sonha;
                this.txtDiaChiLDMoi = data.diachimoi;
                console.log('Địa chỉ lắp đặt')
                console.log(diachild)
                if (this.diachild.Tinh_id == 21)
                {
                    this.khuvuc.DiaDanh = this.diadanh = diachild;
                    this.LayDV_QuanLy();
                }
        },
        //Lấy danh sách đơn vị quản lý 
        async LayDV_QuanLy() {
            let params = {
                "vphuong_id": this.diadanh.Phuong_id,
                "vpho_id": this.diadanh.Pho_id,
                "vap_id": this.diadanh.Ap_id,
                "vkhu_id": this.diadanh.Khu_id,
                "vkieu": 1,
                "vloaikh_id": this.cboLoaiKH,
            }
            let ds = this.GetData(await api.lay_ds_donviql(this.axios, params));
            if (!ds) {
                let params = {
                    "vphuong_id": this.diadanh.Phuong_id,
                    "vpho_id": this.diadanh.Pho_id,
                    "vap_id": this.diadanh.Ap_id,
                    "vkhu_id": 0,
                    "vkieu": 1,
                    "vloaikh_id": this.cboLoaiKH,
                }
                ds = this.GetData(await api.lay_ds_donviql(this.axios, params));
            }
            if (!ds) {
                let params = {
                    "vphuong_id": this.diadanh.Phuong_id,
                    "vpho_id": this.diadanh.Pho_id,
                    "vap_id": 0,
                    "vkhu_id": 0,
                    "vkieu": 1,
                    "vloaikh_id": this.cboLoaiKH,
                }
                ds = this.GetData(await api.lay_ds_donviql(this.axios, params));
            }
            if (ds.length <= 0) {
                if (this.lstDaiVT.length != 1 && !this._CHUYEN_DONVI_TIEPNHAN_ONLINE) {
                    this.$toast.warning("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
                    return;
                }
            } else  {
                let donvi_ql_id = 0;
                if (ds.length > 1) {
                    this.dsDonviXL = ds
                    this.$refs.chonDonViModal.showModal()
                    
                }else{
                    donvi_ql_id = parseInt(ds[0]["donvi_id"]);
                }
                if (this.hdkh_id == 0) //Nếu chưa lập hợp đồng thì thay đổi luôn cả thông tin lắp đặt
                {   if (this._CHUYEN_DONVI_TIEPNHAN_ONLINE)
                        this.donvi_kd_id = donvi_ql_id;
                    else
                        this.cboDaiVT = donvi_ql_id;
                }
            }

            this.Lay_NV_BanHang_KV();
        },
        //Lấy nhân viên bán hàng khu vực
        async Lay_NV_BanHang_KV() {

            if (this.cboDaiVT != null)
                this.khuvuc.TramTC_ID = parseInt(this.cboDaiVT);
            if (!this.items) //Dữ liệu rỗng
                return;

            if (this.tt_nd.ma_tinh == "HCM"
                && this.thuebao_id > 0
                && this.kieu_ld in [13132, 13133, 13134, 13135, 13136, 13137, 13138, 13139, 13140, 13141, 13144, 13145, 13142, 1028, 1027, 1029, 1030, 1031, 1032, 1033, 1034])
            {
                this.LayNhanVienXuLyHCM();
                return;
            }
            let diadanhTemp = this.khuvucDanhBa.DiaDanh;
            if (this.tt_nd.ma_tinh  == "HCM")
                this.diadanhTemp.Khu_id = this.diadanhTemp.Khu_id - 1;

            let params = {
                        "p_loaikv_id": LOAI_KV.KHUVUC_DIABAN,
                        "p_quan_id": this.quan_id,
                        "p_phuong_id": this.phuong_id,
                        "p_pho_id": this.pho_id,
                        "p_ap_id": this.ap_id,
                        "p_khu_id": this.khu_id,
                        "p_dacdiem_id": this.dacdiem_id,
                        "p_tramtc_id": this.khuvuc.TramTC_ID
                    }
            let result = this.GetData(await api.thu_lay_khuvuc_theo_diadanh(this.axios, params));
            let result_json = JSON.parse(result);

            if (this.diadanhTemp.Tinh_id == this.diadanh.Tinh_id && this.diadanhTemp.Quan_id == this.diadanh.Quan_id && this.diadanhTemp.Phuong_id == this.diadanh.Phuong_id
                    && this.diadanhTemp.Pho_id == this.diadanh.Pho_id && this.diadanhTemp.Ap_id == this.diadanh.Ap_id && this.diadanhTemp.Khu_id == this.diadanh.Khu_id)
            {
                let khuvuc = this.khuvucDanhBa;
                this.txtKhuVuc = khuvuc.Ten_KV;
                await this.TaoDuLieu_HDTB_KV();
            }else if (result_json.RESULT >= 0) {
                    this.txtKhuVuc = khuvuc.Ten_KV;
                    await this.TaoDuLieu_HDTB_KV()
            } else {
                this.dsHDTB_KV = []
                this.lstNhanVien = [];
                this.cboNhanVien = "";
                this.txtKhuVuc = "";
                this.khuvuc_id = 0;
                this.$toast.warning("Địa danh chưa có khu vực: " + this.txtDiaChiKH);
                return;
            }
        },
        onChangeDonvixuly(){

        },
        //Lấy nhân viên xử lý khu vực HCM
        async LayNhanVienXuLyHCM()
        {
            if (this.thuebao_id <= 0)
                return;
            try
            {
                let loainv = 0;
                let params = {
                        "p_loaikv_id": LOAI_KV.KHUVUC_DIABAN,
                        "p_quan_id": this.quan_id,
                        "p_phuong_id": this.phuong_id,
                        "p_pho_id": this.pho_id,
                        "p_ap_id": this.ap_id,
                        "p_khu_id": this.khu_id,
                        "p_dacdiem_id": this.dacdiem_id,
                        "p_tramtc_id": this.khuvuc.TramTC_ID
                    }
                let result = this.GetData(await api.thu_lay_khuvuc_theo_diadanh(this.axios, params));
                let khuvuc = JSON.parse(result);
               
                let kieu_ld = parseInt(this.cboKieuYC);
                let row = this.lstLoaiKH.filter(x => x.LOAIKH_ID === this.cboLoaiKH)
                
                if (row == null)
                    return;

                if (row.loai == "1")
                {
                    if (this.cboKieuYC in [ 13132, 13133, 13135, 1027, 1028, 1030, 1031, 13137, 13138, 13139, 13145, 13140, 13141])
                        loainv = Loai_NV.NHANVIEN_TC; //AS1.2
                    else if (this.cboKieuYC in [ 13134, 13144, 1029, 1032, 1034, 13136, 1033]){
                        loainv = Loai_NV.NHANVIEN_AM; //AS2
                    }
                }
                if (row.loai == "0")
                    if (this.cboKieuYC in [ 13134, 13144, 13136, 1029, 1032, 1033, 1034])
                        loainv = Loai_NV.NHANVIEN_AM; //AS2
                    else if (this.cboKieuYC in [13132, 13133, 13135, 1027, 1028, 1030, 1031, 13137, 13138, 13139, 13145, 13140, 13141 ])
                        loainv = Loai_NV.NHANVIEN_TC; //QLĐB
                let params1 = {
                    "p_loai_nv": loainv,
                    "p_thuebao_id": this.thuebao_id
                }
                let ds = this.GetData(await api.laynhanvienxulydonhanghcm(this.axios, params1));
                lstNhanVien = ds;

                this.dsHDTB_KV = []

                let dr = {}
                dr.HDTB_ID = this.hdtb_id;
                dr.KHUVUC_ID = this.khuvuc.KhuVuc_ID;
                dr.LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN;
                dr.NGAY_CN = this.tt_nd.ngay_cn;
                dr.NGUOI_CN = this.tt_nd.ma_nd;
                dr.MAY_CN = this.tt_nd.may_cn;
                dr.IP_CN = this.tt_nd.ip;
                dsHDTB_KV.push(dr);
            }
            catch (err)
            {
                this.$toast.error("Lỗi lấy nhân viên xử lý HCM: " + err.message);
            }
        },
        //Nhấn chấp nhận chọn đơn vị
        acceptChonDonVi(data) {
            this.cboDaiVT = data.donvi_id;
        },
        //Nhấn enter mã giao dịch
        async txtMaGD_KeyPress() {
            if (this.txtMaGD != "") {
                let dtList = this.GetData(await api.Lay_ds_hopdong_theo_ma_gd(this.axios, {
                    "ma_gd": this.txtMaGD,
                    "loaihd_id": LoaiHopDong.THAY_DAY_MAY,
                    "donvi_id": this.tt_nd.donvi_id,
                    "tthd_id": TrangThaiHD.MOI,
                    "nhanvien_id": this.tt_nd.nhanvien_id,
                    "donvi_dl_id": 0
                }));
                await this.HienThiTTHopDongKH(dtList);
                this.HienThiTT_DanhBa(dtList[0].ma_tb);
            }
            this.$refs.MaTB.focus();
        },
        //Khi nhấn chấp nhận popup hợp đồng
        async acceptSearchContract(item) {
            this.txtMaGD = item.hopdong.ma_gd;
            this.$refs.popupFrmTraCuuHopDong.hide();
            if (this.txtMaGD != "") {
                if (this.txtMaGD != "")
                {
                    this.HienThiDanhSacHDTB(this.txtMaGD);
                }
            }
        },
        //Click chọn hợp đồng ở lưới
        gviewDanhSach_FocusedRowChanged(data) {
            let row = data.data
            if (row.length < 0) {
                this.tsbtnNhapMoi.Enabled = false;
                return;
            }
            this.HienThiTTHopDongTB(row);
        },
        //Hiển thị thông tin hợp đồng thuê bao
        async HienThiTTHopDongTB(data) {
            this.hdtb_id = parseInt(data.hdtb_id);
            if (data.thuebao_id != "")
                this.thuebao_id = parseInt(data.thuebao_id);
            else
                this.thuebao_id = 0;
            this.txtMaTB = data.ma_tb;
            this.txtMaGD = data.ma_gd;
            this.txtSoDT = data.so_dt;
            this.txtMaKH = data.ma_kh;
            this.txtTenKH = data.ten_kh;
            this.txtMST = data.mst;
            this.txtDiaChiKH = data.diachi_kh;

            this.hdkh_id = parseInt(data.hdkh_id);
            this.txtTenTB = data.ten_tb;
            let dataDC = this.GetData(await api.lay_ds_diachi_theo_hdkh_id(this.axios, {
                "hdkh_id": this.hdkh_id
            }));
            this.HienThiTTDiaChi(dataDC);

            this.dichvuvt_id = parseInt(data.dichvuvt_id);
            this.txtGhiChuTB = data.ghichu_tb;
            this.phieu_cha_id = this.GetData(await api.fn_tt_giaophieu(this.axios,  {
                "p_type": 1,
                "p_param": this.hdtb_id
            }));
            //this.phieu_cha_id = parseInt(checkdata.MAP_ID("phieu_id", DatabaseConstants.DB2 + ".giaophieu", "where hdtb_id = " + hdtb_id));
            let strDvi = data.donvi_id;
            if (!strDvi)
                this.cboDaiVT = parseInt(strDvi);
            this.cboDichVuVT = this.dichvuvt_id;
            this.cboLoaihinhTB = data.loaitb_id;
            this.cboKieuYC = parseInt(data.kieuld_id);
            this.txtSoLuong = data.soluong;
            this.cboDoituongTB = parseInt(data.doituong_id);
            this.txtDiaChiLDMoi = data.diachi_ld
            this.txtDiaChiLDCu = data.diachi_tb
            let input_NV = {
                "p_hdtb_id": this.hdtb_id,
                "p_inhd_db": 1
            }
            let dsNV = this.GetData(await api.lay_ds_khuvuc_theo_hd_db(this.axios, input_NV));

            this.txtKhuVuc = "";
            this.listNhanvien = [];
            if (dsNV != null && dsNV.length > 0) {
                this.txtKhuVuc = dsNV[0]["ten_kv"];
                this.khuvuc_id = parseInt(dsNV[0]["khuvuc_id"]);
                await this.TaoDuLieu_HDTB_KV()
            } else {
                this.txtKhuVuc = "";
                this.khuvuc_id = 0;
                this.txtDiaChi_ButtonClick(null, null);
            }
            this.SetButton(3);
        },
        //Nhấn enter mã khách hàng
        async txtMaKH_KeyPress() {
            if (this.txtMaKH != "") {
                let ds = this.GetData(await api.lay_db_theo_matb(this.axios, {
                    "p_ma_tb": this.txtMaKH,
                    "p_donvi_dl_id": 0,
                    "p_dichvuvt_id": 0
                }));
                if (ds.length > 0) {
                    this.Clear();
                    this.HienThiTT_DanhBa_dt(ds);
                    this.$refs.MaKH.focus();

                } else {
                    this.$toast.error("Không tìm thấy thông tin khách hàng!");
                }
            }
        },
        //Hiển thị thông tin danh bạ khi enter mã khách hàng
        async HienThiTT_DanhBa_dt(ds) {
            try {
                this.Clear();
                if (ds.length == 0)
                {
                    this.SetButton(0);
                    return;
                }

                if (ds.length > 0) {
                    this.txtMaKH = ds[0]["ma_kh"];
                    if (ds[0]["khachhang_id"] != "")
                        this.khachhang_id = parseInt(ds[0]["khachhang_id"]);
                    else
                        this.khachhang_id = 0;

                    this.txtTenKH = this.ChuanHoaTen(ds[0]["ten_kh"]);
                    
                    let input_kh = {
                        phanvung_id: 0,
                        ma_kh: this.txtMaKH
                    }
                    let uc3 = this.GetData(await api.kiemtrakhachhanguc3(this.axios, input_kh));

                    if (uc3 == "OK")
                    {
                        this.lblUC3.Text = "CT giảm hủy cho tập Thuê bao thuộc dự án thử nghiệm UC3; TG: 01/11-30/11; Cơ hội nhận ưu đãi: ĐTC(3+1), -50% HĐ tháng, x2 tốc độ, MyTV Chuẩn";
                    }
                    else
                        this.lblUC3.Visibility = false;
                    

                    this.txtDiaChiKH = this.ChuanHoaTen(ds[0]["diachi_kh"]);
                    this.txtMST = ds[0]["mst"];
                    this.txtDiaChiLDCu = ds[0]["diachi_ld"];
                    let input_dckh = {
                        phanvung_id: 0,
                        khachhang_id: this.khachhang_id
                    }
                    let data_dckh = this.GetData(await api.Lay_ds_diachi_khid(this.axios, input_dckh));
                    this.HienThiTTDiaChi(data_dckh);

                    if (parseInt(ds[0]["gioitinh"]) == 0)
                        this.$refs.rdoGTNam.checked = true;
                    else if (parseInt(ds[0]["gioitinh"]) == 1)
                        this.$refs.rdoGTNu.checked = true;
                    else
                        this.$refs.rdoGTNone.checked = true;

                    this.cboLoaiKH = parseInt(ds[0]["loaikh_id"]);
                    this.txtSoDT = ds[0]["so_dt"];

                    if (this.loaihd_id != LoaiHopDong.TIEPNHAN_DATMOI) {
                        this.thuebao_id = parseInt(ds[0]["thuebao_id"]);
                        this.txtMaTB = ds[0]["ma_tb"];
                        this.cboDichVuVT = ds[0]["dichvuvt_id"];
                        this.cboLoaihinhTB = ds[0]["loaitb_id"];
                    } else {
                        this.thuebao_id = 0;
                        this.txtMaTB = "";
                    }
                    this.cboDoituongTB = ds[0]["doituong_id"];

                    if (!this._CHUYEN_DONVI_TIEPNHAN_ONLINE)
                    {
                        let ds = this.GetData(await api.LAY_DONVI_THEO_LOAIDV_DIACHI_LD(this.axios, {
                            "p_loaddv_id": LOAI_DV.DONVIQL_TT,
                            "p_phuong_id": this.diadanh.Phuong_id,
                            "p_pho_id": 0,
                            "p_ap_id": 0,
                            "p_khu_id": 0,
                            "p_dacdiem_id": 0
                        }));
                        this.cboDaiVT = parseInt(ds[0]["donvi_id"]);
                    }
                    if (ds.length == 0)
                        this.txtDiaChi_ButtonClick(txtDiaChiKH, null);
                    else {
                        this.dsDonviXL = ds
                        this.$refs.chonDonViModal.showModal()
                    }

                    // //SonNMP
                    if (!this.diadanh)
                        //Nếu là tư vấn, mà lấy ra danh bạ, thì cưỡng ép chọn lại địa chỉ để chuẩn hóa địa chỉ
                        this.txtDiaChi_ButtonClick(null, null);
                    else if (this.thuebao_id > 0) {
                        //Ngược lại, lấy ra dữ liệu khu vực cũ, bảo lưu khu vực của thuê bao đang chọn

                        let dtKhuVuc = this.GetData(await api.lay_ct_khuvuc_thuebao(this.axios, {
                            "thuebao_id": this.thuebao_id
                        }));
                        if (dtKhuVuc.length > 0) {
                            this.khuvuc.Ten_KV = dtKhuVuc[0].ten_kv;
                            this.khuvuc.KhuVuc_ID = dtKhuVuc[0].khuvuc_id;
                            this.khuvuc.Ma_KV = dtKhuVuc[0].ma_kv;
                            this.khuvuc_id = dtKhuVuc[0].khuvuc_id;
                            this.txtKhuVuc = dtKhuVuc[0].ten_kv;

                            await this.TaoDuLieu_HDTB_KV()
                        }
                    }
                } else {
                    this.SetButton(0);
                }
            } catch (err) {
                this.$toast.error("Lỗi hiển thị thông tin danh bạ: " + err.message);
            }
        },
        //Tạo dữ liệu hợp đồng thuê bao khu vực
        async TaoDuLieu_HDTB_KV()
        {
            let dtNVKhuVuc = [];
            if (this.LOC_NVDIADANH_NVPX)
            {
                if (this.lay_nvql_theo_thuebao_id && this.thuebao_id > 0)
                {
                    
                    dtNVKhuVuc = this.GetData(await api.sp_lay_nhanvien_kv_theo_thuebao_id_v2(this.axios, {
                            "p_phanvung_id": 0,
                            "p_thuebao_id": this.thuebao_id
                        }));
                    if (dtNVKhuVuc.length == 0)
                    {
                        let dtNVKhuVuc = this.GetData(await api.lay_ds_nhanvien_khuvuc_lnv_v3(this.axios, {
                            "p_khuvuc_id": this.khuvuc_id,
                            "p_loainv_id": this.loai_nv_xuly,
                            "p_phuong_id": this.phuong_id,
                            "p_pho_id": this.pho_id,
                            "p_ap_id":  this.ap_id,
                            "p_khu_id": this.khu_id,
                            "p_dacdiem_id": this.dacDiem_id,
                            "p_donvi_id":  this._CHUYEN_DONVI_TIEPNHAN_ONLINE ? this.donvi_kd_id : parseInt(this.cboDaiVT)
                        }));
                    }
                }
                else
                {
                    dtNVKhuVuc = this.GetData(await api.lay_ds_nhanvien_khuvuc_lnv_v3(this.axios, {
                            "p_khuvuc_id": this.khuvuc_id,
                            "p_loainv_id": this.loai_nv_xuly,
                            "p_phuong_id": this.phuong_id,
                            "p_pho_id": this.pho_id,
                            "p_ap_id":  this.ap_id,
                            "p_khu_id": this.khu_id,
                            "p_dacdiem_id": this.dacDiem_id,
                            "p_donvi_id":  this._CHUYEN_DONVI_TIEPNHAN_ONLINE ? this.donvi_kd_id : parseInt(this.cboDaiVT)
                        }));
                }
            }
            else{
                    let input_kv = {
                        "p_khuvuc_id": this.khuvuc_id,
                        "p_loainv_id": parseInt(this.loai_nv_xuly)
                    }
                    dtNVKhuVuc = this.GetData(await api.lay_ds_nhanvien_khuvuc_lnv(this.axios, input_kv));
            }
            if (dtNVKhuVuc&&dtNVKhuVuc.length > 0)
            {
                this.$toast.warning("Chưa có nhân viên kinh doanh khu vực " + dtNVKhuVuc.ma_kv);
            }
            this.lstNhanVien = dtNVKhuVuc;
            this.dsHDTB_KV = []

           let dr = {};
            dr.HDTB_ID = this.hdtb_id;
            dr.KHUVUC_ID = this.khuvuc_id;
            dr.LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN;
            dr.NGAY_CN = this.tt_nd.ngay_cn;
            dr.NGUOI_CN = this.tt_nd.ma_nd;
            dr.MAY_CN = this.tt_nd.may_cn;
            dr.IP_CN = this.tt_nd.ip;
            this.dsHDTB_KV.push(dr);
        },
        //Hiển thị thông tin địa chỉ 
        HienThiTTDiaChi(ds) {
            if (ds&&ds.length > 0) {
                    this.diadanh.diachi_id = parseInt(ds[0]["diachi_id"]);
                    this.diadanh.Tinh_id = parseInt(ds[0]["tinh_id"]);
                    this.diadanh.Qinh_id = parseInt(ds[0]["quan_id"]);
                    if (ds[0]["phuong_id"])
                        this.diadanh.Phuong_id = parseInt(ds[0]["phuong_id"]);
                    if (ds[0]["pho_id"])
                        this.diadanh.Pho_id = parseInt(ds[0]["pho_id"]);
                    if (ds[0]["ap_id"])
                        this.ap_id = parseInt(ds[0]["ap_id"]);
                    if (ds[0]["khu_id"])
                        this.diadanh.Khu_id = parseInt(ds[0]["khu_id"]);
                    this.diadanh.SoNha = this.ChuanHoaTen(ds[0]["sonha"]);
            }
        },
        //Click button khu vực
        txtKhuvuc_ButtonClick() {
            this.dataPopupKhuVuc.quan_id=this.diadanh.Quan_id
            this.dataPopupKhuVuc.phuong_id=this.diadanh.Phuong_id
            this.dataPopupKhuVuc.pho_id=this.diadanh.Pho_id
            this.dataPopupKhuVuc.ap_id=this.diadanh.Ap_id
            this.dataPopupKhuVuc.khu_id=this.diadanh.Khu_id
            this.dataPopupKhuVuc.dacdiem_id=0
            this.dataPopupKhuVuc.khuvuc_id=this.khuvuc.KhuVuc_ID
            this.dataPopupKhuVuc.donviql_id = this.cboDaiVT
            //sửa cho hiển thị địa chỉ theo hợp đồng thuê bao trước
            if(this.hdtb_id!=0){
                this.dataPopupKhuVuc.hdtb_id=this.hdtb_id
            }else{
                this.dataPopupKhuVuc.thuebao_id=this.thuebao_id
            }
            this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
        },
        //show modal tìm mã khách hàng
        showTraCuuDanhBa() {
            this.$refs.searchAccountModal_KH.showModal()
        },
        //Khi đóng form tra cứu danh bạ
        formCloseSearchAccount(data) {
            this.txtMaKH = data.ma_kh
        },
        //Khi click chọn loại khách hàng
        cboLoaiKH_EditValueChanged()
        {
            if (this._CHUYEN_DONVI_TIEPNHAN_ONLINE)
                return;
            let dt = this.lstLoaiKH;
            if (this.cboLoaiKH == null)
                return;
            let dr = dt.filter(x => x.LOAIKH_ID === this.cboLoaiKH)
            if (dr == null)
                return;
            if (dr.loai == "1")
                this.loai_nv_xuly = Loai_NV.NHANVIEN_AM;
            if (dr.loai == "0")
                this.loai_nv_xuly = Loai_NV.NHANVIEN_KINHDOANH;
            this.LayDV_QuanLy();
        },
        //Hiển thị thông tin hợp đồng khách hàng
        async HienThiTTHopDongKH(ds) {

            try {
                if (ds.length > 0) {

                    this.hdkh_id = parseInt(ds[0].hdkh_id);
                    this.txtMaGD = ds[0].ma_gd;
                    this.txtMaHD = ds[0].ma_hd;
                    this.txtMaKH = ds[0].ma_kh;
                    this.txtGhiChu = ds[0].ghichu;
                    if (ds[0].khachhang_id != "")
                        this.khachhang_id = parseInt(ds[0].khachhang_id);
                    this.txtTenKH = this.ChuanHoaTen(ds[0].ten_kh);
                    this.txtDiaChiKH = this.ChuanHoaTen(ds[0].diachi_kh);
                    this.cboDichvuvt_id = parseInt(ds[0].dichvuvt_id);
                    await this.getLoaiHinhTB(this.dataSelected.dichvuvt_id);
                    this.cboLoaiHinhTB = parseInt(ds[0].loaitb_id);
                    await this.getDmKieuYC(this.dataSelected.loaitb_id);
                    this.cboKieuYC = parseInt(ds[0].kieuld_id);

                    this.SetButton(3);
                    this.HienThiDanhSacHDTB();
                } else {
                    this.DSPhieu = [];
                    this.$toast.error("Không tìm thấy thông tin hợp đồng ");
                    this.SetButton(0);
                }
            } catch (err) {
                this.$toast.error("Lỗi hiển thị danh sách hợp đồng khách hàng: " + err.message);
            }
        },
        //Hiển thị thông tin danh bạ theo mã thuê bao
        async HienThiTT_DanhBa(txtMaTB) {

            if (this.cboDichVuVT == 0) {
                this.$toast.error("Bạn cần chọn dịch vụ để lấy thông tin!");
                return;
            } else {

                var input = {
                    "maTb": txtMaTB,
                    "dichVuVtId": this.cboDichVuVT,
                    "donViDlId": null
                }
                let data = [];
                let data_obj = this.GetData(await api.getTTDanhBa_MaTB(this.axios, input));
                data.push(data_obj);

                if (data[0] != null) {
                    this.thuebao_id = parseInt(data[0].thuebao_id);
                    //Thông tin khách hàng
                    this.txtMaKH = data[0].ma_kh;
                    this.txtMaHD = data[0].ma_hd;
                    this.khachhang_id = data[0].khachhang_id;
                    this.txtTenKH = data[0].ten_kh;
                    this.txtDiaChiKH = this.ChuanHoaTen(data[0].diachi_kh);
                    //Thông tin thuê bao
                    this.txtMaTB = data[0].ma_tb;
                    this.txtTenTB = this.ChuanHoaTen(data[0].ten_tb);
                    this.txtDiaChiTB = this.ChuanHoaTen(data[0].diachi_tb);
                    this.txtDiaChiLD = this.ChuanHoaTen(data[0].diachi_ld);
                    this.txtSoAo = data[0].ma_lt;
                    this.txtSDT = data[0].so_dt;
                    this.txtMST = data[0].mst;
                    this.donvi_id = parseInt(data[0].donviql_id);
                    this.doituong_id = parseInt(data[0].doituong_id);
                    this.cboDichVuVT = data[0].dichvuvt_id;
                    this.cboLoaiHinhTB = data[0].loaitb_id;
                    this.cboLoaiKH = data[0].loaikh_id;
                    this.cbbGioitinh = data[0].gioitinh;
                } else {
                    this.$toast.warning("Không tìm thấy thông tin về mã thuê bao " + this.txtMaTB + " đối với dịch vụ " + this.dtTenDVVT);
                }
            }
        },
        //Click nút tìm theo số máy
        showPopupSearchAccount() {
            this.$refs.searchAccountModal.showModal();
        },
        //Chuẩn hóa tên
        ChuanHoaTen(ten) {
            if (ten != null) {
                let convertToArray = ten.toLowerCase().split(' ');
                let result = convertToArray.map(function (val) {
                    return val.replace(val.charAt(0), val.charAt(0).toUpperCase());
                });
                return result.join(' ');
            } else return ''
        },
        async acceptSearchAccount(item) {
            this.txtMaTB = item.ma_tb;
        },
        //Nhấn nút hiện popup số máy
        async txtMaTB_KeyPress() {
            
            if (this.txtMaTB != "") {
                let ds = this.GetData(await api.lay_db_theo_matb(this.axios, {
                    "p_ma_tb": this.txtMaTB,
                    "p_donvi_dl_id": 0,
                    "p_dichvuvt_id": 0
                }));
                if (ds.length > 0) {
                    this.Clear();
                    this.HienThiTT_DanhBa_dt(ds);
                    this.$refs.txtMaKH.focus();

                } else {
                    this.$toast.error("Không tìm thấy thông tin khách hàng!");
                }
            }

        },
        tlstpRefresh_Click()
        {
            this.HienThiDanhSacHDTB("");
        },
        //Kiểm tra dữ liệu khách hàng 
        async KiemTraDL_KhachHang()
        {
            if (this.txtSoDT == "")
            {
                this.$toast.warning("Bạn chưa nhập số điện thoại liên hệ của khách hàng ! ");
                this.$refs.SoDT.focus();
                return false;
            }
            if (this.txtTenKH == "")
            {
                this.$toast.warning("Hãy nhập tên khách hàng !");
                this.$refs.TenKH.focus();
                return false;
            }

            if (this.txtDiaChiKH == "")
            {
                this.$toast.warning("Hãy nhập địa chỉ khách hàng!");
                this.$refs.txtDiaChiKH.focus();
                return false;
            }

            if (this.cboKieuYC == "13133")
                if (this.txtDiaChiLDMoi.length == 0)
                    this.$toast.warning("Đơn hàng tiếp nhận yêu cầu chuyển dịch cần có địa chỉ lắp đặt mới");
            if (this.txtMaGD != "")
            {
                let laphd = this.GetData(await api.kiemtra_makh_mtt(this.axios,{
                    "p_phanvung_id": 0,
                    "p_ma_kh": this.txtMaKH,
                    "p_kieu": 3,
                    "p_loaihd_id": this.loaihd_id
                }));
                if (laphd == 0)
                {
                    this.$toast.warning("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                    this.$refs.MaGD.focus();
                    return false;
                }
            }

            if (this.kt_diachi)
            {
                if (this.quan_id == "")
                {
                    this.$toast.warning("Bạn hãy chọn lại địa chỉ cho khách hàng!");
                    return false;
                }
                if (this.quan_id == 0)
                {
                    this.$toast.warning("Bạn hãy chọn lại địa chỉ cho khách hàng!");
                    return false;
                }
                if (this.phuong_id == "")
                {
                    this.$toast.warning("Bạn hãy chọn lại địa chỉ cho khách hàng!");
                    return false;
                }
                if (this.phuong_id == 0)
                {
                    this.$toast.warning("Bạn hãy chọn lại địa chỉ cho khách hàng!");
                    return false;
                }
                if (this.pho_id == 0 && this.ap_id == 0 && this.khu_id == 0)
                {
                    this.$toast.warning("Bạn hãy chọn lại địa chỉ cho khách hàng!");
                    return false;
                }
            }
            return true;
        },
        //Nhấn nhập mới
        tsbtnNhapMoi_Click()
        {
            this.Clear();
             this.SetButton(1);
        },
        //Tạo dữ liệu 
        async TaoDuLieu(themmoi)
        {
            await this.TaoDuLieu_HDKH(themmoi);
            await this.TaoDuLieu_HDTB(themmoi);
            await this.TaoDuLieu_HDTBDV(themmoi);
            await this.TaoDuLieu_DiaChi(themmoi, 4);
        },
        //Tạo dữ liệu hợp đồng khách hàng
        async TaoDuLieu_HDKH(themmoi)
        {
            this.dsHDKH = [];
            if (themmoi == true)
                this.hdkh_id = this.GetData(await api.get_keys(this.axios, {"keyname":"HD_KHACHHANG"}));
            let rowHDKH = {}
            rowHDKH.HDKH_ID = this.hdkh_id;
            if (themmoi == true)
            { 
                this.txtMaGD = this.GetData(await api.sinh_magd(this.axios, {"loaihd_id":this.loaihd_id}));
                rowHDKH.MA_GD = this.txtMaGD;
            }
            else
            {
                rowHDKH.MA_GD = this.txtMaGD;
            }
            if (this.khachhang_id != 0 && this.khachhang_id != "")
            {
                rowHDKH.KHACHHANG_ID = this.khachhang_id;
                rowHDKH.MA_KH = this.txtMaKH;
            }
            else if (this.loaihd_id == LoaiHopDong.TUVAN_DV)
                rowHDKH.KHACHHANG_ID = 0;

            rowHDKH.TEN_KH = this.txtTenKH;
            rowHDKH.DIACHI_KH = this.txtDiaChiKH;
            rowHDKH.MST = this.txtMST;
            rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
            rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;
            rowHDKH.KHLON_ID = this.khlon_id;
            rowHDKH.LOAIHD_ID = this.loaihd_id;

            if (this.loaihd_id == LoaiHopDong.TUVAN_DV)
                rowHDKH.NGUOI_CN = "18001166";
            else
                rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;
            rowHDKH.MAY_CN = this.tt_nd.may_cn;
            rowHDKH.IP_CN = this.tt_nd.ip;
            rowHDKH.CTV_ID = this.tt_nd.nhanvien_id;

            rowHDKH.NGAYLAP_HD = this.tt_nd.ngay_cn;
            rowHDKH.NGAY_YC = this.tt_nd.ngay_cn;
            rowHDKH.KENHTN_ID = 2;//Kênh tiếp nhận Điện thoại viên TTKD
            if (this.rdoGTNam.Checked)
                rowHDKH.GIOITINH = 0;
            else if (this.rdoGTNu.Checked)
                rowHDKH.GIOITINH = 1;
            rowHDKH.SO_DT = this.txtSoDT;
            rowHDKH.NGANHNGHE_ID = 999;
            rowHDKH.TNC1_ID = 9991;
            rowHDKH.TNC2_ID = 99911;
            rowHDKH.TNC3_ID = 999111;
            rowHDKH.TNC4_ID = 0;
            console.log(rowHDKH)
            this.dsHDKH.push(rowHDKH);
        },
        //Tạo dữ liệu hợp đồng thuê bao
        async TaoDuLieu_HDTB(themmoi)
        {
            this.dsHDTB = [];
            if (themmoi == true)
            {
                this.hdtb_id = this.GetData(await api.get_keys(this.axios, {"keyname":"HD_THUEBAO"}));
            }

            let rowHDTB = {}
            rowHDTB.HDTB_ID = this.hdtb_id;
            rowHDTB.HDKH_ID = this.hdkh_id;
            if (this.thuebao_id > 0)
                rowHDTB.THUEBAO_ID = this.thuebao_id;
            if (this.thuebao_id > 0)
                rowHDTB.MA_TB = this.txtMaTB;
            else
                rowHDTB.MA_TB = this.txtMaTB.toLowerCase();
            rowHDTB.TEN_TB =this.txtTenKH;
            rowHDTB.DIACHI_TB =this.txtDiaChiKH;
            rowHDTB.GHICHU =this.txtGhiChuTB;
            rowHDTB.KIEULD_ID = parseInt(this.cboKieuYC);
            rowHDTB.LOAITB_ID = parseInt(this.cboLoaihinhTB);
            rowHDTB.DONVI_ID = parseInt(this.cboDaiVT);
            rowHDTB.DOITUONG_ID = parseInt(this.cboDoituongTB);
            rowHDTB.TTHD_ID = TrangThaiHD.MOI;
            rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
            rowHDTB.KIEU_TB = "0";
            rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
            rowHDTB.MAY_CN = this.tt_nd.may_cn;
            rowHDTB.IP_CN = this.tt_nd.ip;
            if (this.txtSoLuong != "")
                rowHDTB.SOLUONG = this.txtSoLuong;
            else
                rowHDTB.SOLUONG = 0;
            rowHDTB.GHICHU = this.txtGhiChuTB;
            if (this.cboKieuYC == "13133")
                rowHDTB.DIACHI_LD =this.txtDiaChiLDMoi;
            else
                rowHDTB.DIACHI_LD =this.txtDiaChiKH;
             let input_hg = {
                "loaihd_id": this.loaihd_id,
                "loaitb_id": parseInt(this.cboLoaiHinhTB),
                "tocdo_id": 0,
                "muccuoc_id": 0,
                "kieuld_id": this.cboKieuYC,
                "loaikenh_id": 0,
                "dichvuvt_id": this.cboDichVuVT}
            let dset = this.GetData(await api.layhuonggiao_tiepnhan_v2(this.axios, input_hg));

            if (dset&&dset.length > 0)
            {
                this.quytrinh_id = parseInt(dset[0]["quytrinh_id"]);
            }
            rowHDTB.QUYTRINH_ID = this.quytrinh_id;
            this.dsHDTB.push(rowHDTB);
        },
        TaoDuLieu_HDTBDV(themmoi)
        {
            this.dsHDTBDV = []
            let  row = {};
            row.HDTB_ID = this.hdtb_id;
            row.LOAIDV_ID = this.LoaiDonViXL;
            row.DONVI_ID = parseInt(this.cboDaiVT);
            row.KIEUDV_ID = 2;
            this.dsHDTBDV.push(row);
        },
        async TaoDuLieu_DiaChi(themmoi,kieu)
        {
            this.dsDiaChi = [];
            this.dsDiaChiHDKH = [];
            this.dsDiaChiHDTB = [];
            if (kieu == 1 || kieu == 4)
            {
                let rowDiaChiKH = {};
                if (themmoi == true)
                    this.diadanh.diachi_id = this.GetData(await api.get_keys(this.axios, {"keyname":"DIACHI"}));
                rowDiaChiKH.DIACHI_ID = this.diadanh.diachi_id;
                rowDiaChiKH.TINH_ID = this.diadanh.Tinh_id;
                rowDiaChiKH.QUAN_ID = this.diadanh.Quan_id;
                rowDiaChiKH.PHUONG_ID = this.diadanh.Phuong_id;
                rowDiaChiKH.PHO_ID = this.diadanh.Pho_id;
                rowDiaChiKH.AP_ID = this.diadanh.Ap_id;
                rowDiaChiKH.KHU_ID = this.diadanh.Khu_id;
                rowDiaChiKH.SONHA = this.diadanh.SoNha;
                rowDiaChiKH.DIACHI = this.txtDiaChiKH;
                this.dsDiaChi.push(rowDiaChiKH);

                //Phục vụ chuyển dịch
                if (themmoi == true)
                {
                    let  rowDiaChiHDKH = {};
                    rowDiaChiHDKH.DIACHI_ID = this.diadanh.diachi_id;
                    rowDiaChiHDKH.HDKH_ID = this.hdkh_id;
                    this.dsDiaChiHDKH.push(rowDiaChiHDKH);
                }
            }

            // foreach (dr in this.dsHDTB_KV)
            //     dr["hdtb_id"] = this.hdtb_id;
        },
        //Ghi lại thông tin kênh bán
        async tsbtnGhiLai_Click()
        {
            try
            {
                if (!this.KiemTraDL_KhachHang()) return;
                
                if (this.tsbtnNhapMoi.Enabled == false)
                {
                    console.log(this.tsbtnNhapMoi.Enabled)
                    await this.TaoDuLieu(true);
                    let input_tm = {
                        "p_huonggiao_id": this.huonggiaotn_id,
                        "p_js_hdkh":  JSON.stringify(this.dsHDKH),
                        "p_js_hdtb":  JSON.stringify(this.dsHDTB),
                        "p_js_diachi":  JSON.stringify(this.dsDiaChi),
                        "p_js_diachi_hdkh":  JSON.stringify(this.dsDiaChiHDKH),
                        "p_js_diachi_hdtb":  JSON.stringify(this.dsDiaChiHDTB),
                        "p_js_hdtb_dv":  JSON.stringify(this.dsHDTBDV),
                        "p_js_hdtb_kv":  JSON.stringify(this.dsHDTB_KV)
                    }
                    let tm = this.GetData(await api.sp_tiepnhanyc_themmoi_hopdong(this.axios,input_tm));
                    this.$root.$toast.success("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!");
                }
                else
                {
                    let input_tm = {
                        "p_huonggiao_id": this.huonggiaotn_id,
                        "p_js_hdkh":  JSON.stringify(this.dsHDKH),
                        "p_js_hdtb":  JSON.stringify(this.dsHDTB),
                        "p_js_diachi":  JSON.stringify(this.dsDiaChi),
                        "p_js_diachi_hdkh":  JSON.stringify(this.dsDiaChiHDKH),
                        "p_js_diachi_hdtb":  JSON.stringify(this.dsDiaChiHDTB),
                        "p_js_hdtb_dv":  JSON.stringify(this.dsHDTBDV),
                        "p_js_hdtb_kv":  JSON.stringify(this.dsHDTB_KV)
                    }
                    this.TaoDuLieu(false);
                    let ud = this.GetData(await api.sp_tiepnhanyc_capnhat_hopdong(this.axios, input_tm));
                    this.$root.$toast.success("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!");
                }

                this.HienThiDanhSacHDTB("");
            }
            catch (err)
            {
                this.$toast.error("Lỗi cập nhật hợp đồng: "+err.message);
            }
        },
        async onAcceptKhuVuc(data){
            this.khuvuc_selected=data
            this.input_khuvuc= this.khuvuc_selected.ma_kv
            this.$emit('onAcceptKhuVuc', this.khuvuc_selected)

            if (this.diadanh.Quan_id != 0 && this.diadanh.Phuong_id != 0)
                    {
                        this.khuvuc.KhuVuc_ID = data.khuvuc_id;
                        this.txtKhuVuc = "";

                        this.dsHDTB_KV = data.hdtb_kv_data;
                        this.txtKhuVuc = data.ma_kv;
                        if (this.khuvuc.KhuVuc_ID != 0)
                        {
                            let input_kv = {
                                "p_khuvuc_id": this.khuvuc_id,
                                "p_loainv_id": parseInt(this.loai_nv_xuly)
                            }
                            this.lstNhanVien = this.GetData(await api.lay_ds_nhanvien_khuvuc_lnv(this.axios, input_kv));
                        }
                        else
                            this.lstNhanVien = [];
                    }
                    else
                    {
                        this.$toast.warning("Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!");
                        this.txtKhuVuc = "";
                        this.khuvuc = {};
                        this.dsHDTB_KV = [];
                        this.lstNhanVien = [];
                    }
        },
        successUpdateThayDoiKhuVucQuanLy(){
            this.$emit('successUpdateThayDoiKhuVucQuanLy')
        },
        //Hủy thực hiện
        tsbtnHuyBo_Click()
        {
            this.Clear()
            this.SetButton(0);
        },
        async tsbtnXoa_Click()
        {
            try
            {
                this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thông tin không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                        let ds = this.GetData(await api.xoa_hdkh_v2(this.axios,  {
                            "phanvung_id": 0,
                            "vhdkh_id":this.hdkh_id,
                            "vloaihd_id": this.loaihd_id
                        }));
                        this.$root.$toast.success("Xóa hợp đồng khách hàng thành công!");
                        this.SetButton(0);
                        this.HienThiDanhSacHDTB("");
                    }
                });
            }
            catch (err)
            {
                this.$toast.error("Lỗi xóa hợp đồng khách hàng: "+ err.message);
            }
        },
        async tsbtnHoanThanh_Click()
        {
            try
            {
                if (this.txtMaGD)
                {
                    let nvkd = [];
                    if(this.lstNhanvien&&this.lstNhanvien.length >0){
                        this.lstNhanvien.foreach (row =>
                        {
                            nvkd.push(parseInt(row));
                        });
                    }
                    this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thông tin không ?', {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                    }).then( async  v => {
                        if(v) {
                            let ds = this.GetData(await api.hoanthiendonhang_18001166(this.axios,  {
                                "p_phanvung_id": 0,
                                "p_hdkh_id": this.hdkh_id,
                                "p_nhanvien_id": this.tt_nd.nhanvien_id,
                                "p_may_cn": this.tt_nd.may_cn,
                                "p_ip_cn": await this.$root.token.getIP(),
                                "p_nvkd_id": nvkd
                            }));
                            this.$root.$toast.success("Hoàn thiện hợp đồng thành công");
                            this.SetButton(0);
                            this.HienThiDanhSacHDTB("");
                        }
                    });
                   
                }
                this.Clear();
                this.HienThiDanhSacHDTB("");
            }
            catch (err)
            {
                this.$toast.error("Lỗi hoàn thành hợp đồng: "+ err.message);
            }
        },
        async onChangeDichvu(row) {
            this.cboDichVuVT = row;
            await this.getLoaiHinhTB(row)
        },
        async onChangeLoaihinh(row) {
            await this.getDmKieuYC(row);
        },
        GetDataList: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return []
        },
        GetData: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
                return null
            } else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return null
        },
        isNullOrEmpty: function (value) {
            return (
                value == undefined || value == null || value == ""
            );
        },
        Clear() {
            this.donvi_kd_id = 0;
            this.txtMaTB = "";
            this.txtKhuVuc = "";
            this.txtMaKH = "";
            this.khachhang_id = 0;
            this.txtNoiCap = "CABP";
            this.hdkh_id = 0;
            this.txtMaGD = "";
            this.txtTenKH = "";
            this.txtDiaChiKH = "";
            this.tinhkh_id = 0;
            this.quankh_id = 0;
            this.phuongkh_id = 0;
            this.phokh_id = 0;
            this.apkh_id = 0;
            this.sonhakh = "";
            this.txtNgaySN = "";
            this.txtDiaChiLDCu = "",
            this.txtSoLuong = 0,
            this.dtpNgayYC = this.tt_nd.ngay_cn;
            this.txtSoGT = "";
            this.txtNgayCap = "";
            this.txtNoiCap = "";
            this.cboGiayToKH = 0;
            this.khlon_id = 1;
            this.txtNgayHetHan = "";
            this.txtCuly = "0";
            this.tinhtt_id = 0;
            this.quantt_id = 0;
            this.phuongtt_id = 0;
            this.phott_id = 0;
            this.aptt_id = 0;
            this.sonhatt = "";
            this.cboQuocTich = 0;

            this.hdtb_id = 0;
            this.thuebao_id = 0;
            this.txtTenTB = "";
            this.txtMST = "";
            this.tinhtb_id = 0;
            this.quantb_id = 0;
            this.phuongtb_id = 0;
            this.photb_id = 0;
            this.aptb_id = 0;
            this.sonhatb = "";

            this.tinhld_id = 0;
            this.quanld_id = 0;
            this.phuongld_id = 0;
            this.phold_id = 0;
            this.apld_id = 0;
            this.sonhald = "";
            this.cboDoituongTB = this.DoiTuong.TU_NHAN;
            this.cboKieuYC = this.lstKieuYC[0].kieuld_id;
            this.txtGhiChuTB = "";
            this.txtSoDT = "";
            this.txtSoLuong = "1";
            this.tsbtnHoanThanh.Enabled = false;
            this.tsbtnGhiLai.Enabled = true;
            this.tsbtnXoaTB.Enabled = false;
            this.rdoGTNone.Checked = true;
        },
        SetButton(kieu) {
            this.tsbtnXoa.Enabled = false;
            if (kieu == -1) //Bat dau
            {
                this.cboDichVuVT = 0;
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }

            if (kieu == 0) //Bat dau
            {
                this.$refs.MaGD.focus();
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnGhiLai.Enabled = false;
            }

            if (kieu == 1) //Them moi
            {
                this.$refs.MaKH.focus();
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnNhapMoi.Enabled = false;
                this.tsbtnHuyBo.Enabled = true;
            }

            if (kieu == 2) //Huy
            {
                this.$refs.MaGD.focus();
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.Clear();
            }

            if (kieu == 3) //Edit
            {
                this.$refs.MaGD.focus();

                this.tsbtnHoanThanh.Enabled = true;
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }
        },
    }
}
</script>
<style>
.info-row .value{
    padding-left: 0px !important;
}
.pd-l0{
    padding-left: 0px !important;
}
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
</style>
