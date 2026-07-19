<template src="./index.html"></template>

<script>
import ModalChonDiaChiKH from "./ChonDiaChi/Modal"
import ModalChonDiaChiNDD from "./ChonDiaChi/Modal"
import ModalChonDiaChiHK from "./ChonDiaChi/Modal"
import ModalChonDiaChiTT from "./ChonDiaChi/Modal"
import ModalChonDiaChiTB from "./ChonDiaChi/Modal"
import ModalDanhSachBienDong from "./DanhSachBienDong/Modal"
import { minLength, maxLength, numeric, required } from 'vuelidate/lib/validators'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import DateTimeLib from "../../../../utils/DateTimeLib"
import Enum from "./Enum"
import ModalTracnghiemExt from "./TracNghiemExt"
import CongTacVien from "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
import NguoiGioiThieu from "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import ChiTietNganhNghe from '../ThayDoiThongTinKhuyenMai/ChiTietNganhNghe'
import ChangeCustomerInfor from '@/modules/contract/setup/ChangeCustomerInfor';

export default {
    props: [],
    components: {
        BssErrorMarker,
        BssRequiredMarker,
        ModalChonDiaChiKH,
        ChiTietNganhNghe,
        ModalChonDiaChiNDD,
        ModalChonDiaChiHK,
        ModalChonDiaChiTT,
        SearchContractModal,
        ModalChonDiaChiTB,
        ModalDanhSachBienDong,
        ModalTracnghiemExt,
        CongTacVien,
        NguoiGioiThieu,
        ChangeCustomerInfor
    },
    data: function () {
        return {
            objChiTietNganhNghe: null,
            // thông tin cũ của khách hàng
            ten_kh_cu: null,
            diachi_kh_cu: null,
            diachi_kh_id_cu: null,
            nguoi_dd_cu: null,
            so_dt_cu: null,
            chucdanh_dd_cu: null,
            hokhau_cu: null,
            ngay_hk_cu: null,
            noicap_hk_cu: null,
            ngay_sn_cu: null,
            loaigt_id_cu: null,
            so_gt_cu: null,
            ngaycap_cu: null,
            noicap_cu: null,
            so_fax_cu: null,
            khlon_id_cu: null,
            loaikh_id_cu: null,
            gioitinh_cu: null,
            dantoc_id_cu: null,
            quoctich_id_cu: null,
            nganhnghe_id_cu: null,
            diachi_ndd_cu: null,
            ngayhethan_gt_cu: null,
            mst_cu: null,
            nganhang_id_cu: null,
            stk_cu: null,
            tnc1_id_cu: null,
            tnc2_id_cu: null,
            tnc3_id_cu: null,
            dactrung_cu: null,
            ctv_id_cu: null,
            httthi_id_cu: null,
            nguoigt_id_cu: null,
            kieuhd_id_cu: null,
            // Thông tin cũ của thanh toán
            ten_tt_cu: null,
            diachi_ct_cu: null,

            dv_qhns_cu: null,
            machuong_cu: null,
            mn_kt_cu: null,
            ma_ndkt_cu: null,
            mn_nsnn_cu: null,
            tien_dm_cu: null,

            mst_tt_cu: null,
            stk_tt_cu: null,
            chu_tk_tt_cu: null,
            nganhang_tt_cu: null,
            httt_id_cu: null,

            ten_tb_cu: null,
            diachi_tb_cu: null,
            diachi_tb_id_cu: null,
            _maTBOld: null,
            _trangthaiTB: null,
            _loaiTbID: null,

            didong: false,
            //bien hddt
            Fkey: null,
            fkey_new: null,
            invToken: null,
            phieutt_id: null,
            objHoaDonDienTu: null,
            objHoaDonDienTuEdit: null,
            bdhddt_cha_id: 0,
            khoanmuc_dieuchinh: null,

            // Ngân hàng thanh toán
            DV_QHNS: null,
            MACHUONG: null,
            MN_KT: null,
            MA_NDKT: null,
            MN_NSNN: null,
            TIEN_DM: null,
            // biến hợp đồng, thuê bao
            hdkh_id: null,
            hdtt_id: null,
            hdtb_id: null,
            hdtb_id_cha: null,
            khachhang_id: null,
            thanhtoan_id: null,
            thuebao_id: null,
            // Ngành nghề
            TNC1_ID: 0,
            TNC2_ID: 0,
            TNC3_ID: 0,
            TNC4_ID: 0,
            // Địa chỉ khách hàng
            diachikh_id: null,
            dacdiemkh_id: null,
            apkh_id: null,
            khukh_id: null,
            phokh_id: null,
            phuongkh_id: null,
            quankh_id: null,
            tinhkh_id: null,
            sonhakh: null,
            // Địa chỉ Người đại diện
            dacdiemndd_id: null,
            phondd_id: null,
            apndd_id: null,
            khundd_id: null,
            phuongndd_id: null,
            quanndd_id: null,
            tinhndd_id: null,
            sonhandd: null,
            // Địa chỉ thuê bao
            diachitb_id: null,
            dacdiemtb_id: null,
            photb_id: null,
            aptb_id: null,
            khutb_id: null,
            phuongtb_id: null,
            quantb_id: null,
            tinhtb_id: null,
            sonhatb: null,
            // Địa chỉ lắp đặt
            diachild_id: null,
            dacdiemld_id: null,
            phold_id: null,
            apld_id: null,
            khuld_id: null,
            phuongld_id: null,
            quanld_id: null,
            tinhld_id: null,
            sonhald: null,
            lat_TB: 0,
            lng_TB: 0,
            lat_KH: 0,
            lng_KH: 0,
            lat_LD: 0,
            lng_LD: 0,

            maGiaoDich: null,
            lyDo: null,
            suaThongTinId: null,
            kieuHopDongId: null,
            isCheckCTV: false,
            congTacVien: null,
            ctv_id: null,
            hinhThucTiepThiId: null,
            isCheckNguoiGT: false,
            nguoiGT: null,
            nguoigt_id: null,

            nguoiLapHD: null,
            //thong tin khach hang
            maHopDong: null,
            maKH: null,
            ngayLapHD: null,
            ngayYeuCau: null,
            tenKhachHang: null,
            diaChiKhachHang: null,
            loaiGiayToId: null,
            soGiayTo: null,
            ngayCapGT: null,
            noiCapGT: null,
            ngayHetHan: null,
            ngaySinh: null,
            gioiTinh: 0,
            quocTichId: null,
            nganhNgheId: null,
            danTocId: null,
            loaiKhachHangId: null,
            khachHangLonId: null,
            nguoiDaiDien: null,
            chucVu: null,
            diaChiNDD: null,
            dienThoai: null,
            fax: null,
            hoKhau: null,
            noiCapHK: null,
            ngayCapHK: null,
            isCheckNH: false,
            nganHangId: null,
            taiKhoan: null,
            maSoThue: null,
            dacTrung: null,

            //thong tin thanh toan
            hinhThucThanhToanId: null,
            tenThanhToan: null,
            diaChiChungTu: null,
            taiKhoanTT: null,
            chuTaiKhoan: null,
            isCheckNganHangTT: false,
            nganHangThanhToanId: null,
            maSoThueTT: null,

            //thong tin thue bao
            maThueBao: null,
            soAo: null,
            dichVuVTId: null,
            loaiHinhId: null,
            tenThueBao: null,
            diaChiThueBao: null,
            ghiChu: "",

            dsThanhToan: null,
            dsThueBao: null,
            dsLoaiHinhTB: null,
            dsDichVuVT: null,
            dsKieuHopDong: null,
            dsHinhThucThanhToan: null,
            dsNganHang: null,
            dsKhachHangLon: null,
            dsLoaiKhacHang: null,
            dsDanToc: null,
            dsNganhNghe: null,
            dsQuocTich: null,
            dsLoaiGiayTo: null,
            dsHinhThucTiepThi: null,
            dsSuaThongTin: [],
            dataSend: {},

            khong_batbuoc_ctv: 0,
            batbuoc_gioithieu_lapmoi: 0,
            SUATT_HOPDONG_THEO_USER: 0,
            XUAT_HOADON: 0,
            quyen_sua_hd_hoanthien: 0,

            disableSuaThongTin: false,

            customError: {
                ngaySinh: false,
                dienThoai: false,
                diaChiKhachHang: false,
                diaChiThueBao: false
            },
            ChangeCustomerInfor_data : {},
            activeQuyTrinhXacMinhCapNhatTTLH: false // Neu flag = 1 (true) thi disable cac truong Ten khach hang, so giay to, dien thoai lien he
        }
    },
    validations: {
        maGiaoDich: { required },
        lyDo: { required },
        maHopDong: { required },
        maKH: { required },
        tenKhachHang: { required },
        diaChiKhachHang: { required },
        loaiGiayToId: { required },
        soGiayTo: { required },
        ngayCapGT: { required },
        ngaySinh: { required },
        nganhNgheId: { required },
        dienThoai: {
            required,
            maxLength: maxLength(10)
        },
        nguoiDaiDien: { required },
        hinhThucThanhToanId: { required },
        diaChiChungTu: { required },
        tenThanhToan: { required },
        tenThueBao: { required },
        diaChiThueBao: { required }
    },
    computed: {
        hasHDKH: function () {
            return !(this.hdkh_id == null || this.hdkh_id == undefined || this.hdkh_id == 0);
        },
    },
    mounted: async function () {
        this.initForm()
    },
    watch: {
        // isCheckCTV: function() {
        //     if (this.isCheckCTV) {
        //         this.$bvModal.show('popupCongTacVien')
        //     }
        // },
        // isCheckNguoiGT: function() {
        //     if (this.isCheckNguoiGT) {
        //         this.$bvModal.show('popupNguoiGioiThieu')
        //     }
        // },
    },
    methods: {
        //HPG-LD/00675069
        //HPG-LD/00576989
        initForm: async function () {
            this.maGiaoDich = null
            this.disableSuaThongTin = false
            this.ngayLapHD = this.getToDay()
            this.ngayYeuCau = this.getToDay()
            this.dsSuaThongTin = [{ thongtin_id: 1, thongtin: 'Thông tin khách hàng' },
            { thongtin_id: 2, thongtin: 'Thông tin thanh toán' },
            { thongtin_id: 3, thongtin: 'Thông tin thuê bao' },
            { thongtin_id: 4, thongtin: 'Thông tin cộng tác viên' },
            { thongtin_id: 5, thongtin: 'Thông tin người giới thiệu' }]
            this.suaThongTinId = 1
            await this.getKieuHopDong()
            await this.getHinhThucTiepThi()
            await this.getLoaiGiayTo()
            await this.getQuocTich()
            await this.getNganhNghe()
            await this.getLoaiKhachHang()
            await this.getLoaiKhachHangLon()
            await this.getNganHang()
            await this.getHinhThucTT()
            await this.getDichVuVT()
            await this.getDsThamSoMacDinh()
            await this.getQuyenSuaHopDong()
            await this.getActiveQuyTrinhXacMinhTTLH()
        },

        getToDay: function () {
            let now = new Date()
            let month = (now.getMonth() + 1)
            let day = now.getDate()
            if (month < 10)
                month = "0" + month
            if (day < 10)
                day = "0" + day
            return now.getFullYear() + '-' + month + '-' + day
        },

        getDateFormat: function (dateIn) {
            //let now = new Date()
            let month = (dateIn.getMonth() + 1)
            let day = dateIn.getDate()
            if (month < 10)
                month = "0" + month
            if (day < 10)
                day = "0" + day
            return dateIn.getFullYear() + '-' + month + '-' + day
        },

        getDsThamSoMacDinh: async function () {
            let data = {
                kieuId: 0
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_thamso_macdinh', data)
            this.khong_batbuoc_ctv = res.data.find(x => x.ma_ts == "KHONGBATBUOC_CTV") != null ? 1 : 0
            this.batbuoc_gioithieu_lapmoi = res.data.find(x => x.ma_ts == "BATBUOC_GIOITHIEU_LAPMOI") != null ? 1 : 0
            this.SUATT_HOPDONG_THEO_USER = res.data.find(x => x.ma_ts == "SUATT_HOPDONG_THEO_USER") != null ? 1 : 0
            this.XUAT_HOADON = res.data.find(x => x.ma_ts == "CHOPHEP_GDV_KO_XUAT_HOADON_KHI_THAYDOI_TT_HOPDONG") != null ? 1 : 0
        },

        getQuyenSuaHopDong: async function () {
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/check_coquyen')
            this.quyen_sua_hd_hoanthien = res.data.find(x => x.kq == 1) != null ? 1 : 0
        },

        getHinhThucTiepThi: async function () {
            let data = {
                table: 'HINHTHUC_TTHI',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsHinhThucTiepThi = res.data
            if (this.dsHinhThucTiepThi.length > 0) {
                this.hinhThucTiepThiId = this.dsHinhThucTiepThi[0].httthi_id
            }
        },

        getActiveQuyTrinhXacMinhTTLH: async function () {
            const body = { "ma_ts": "GUI_YEUCAU_PHEDUYET_TTTT_KH" };
            let res = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ds_thamso_md_mats', body);
            if (res && res.data && res.data.length > 0) {
                const data = res.data[0];
                if (data.ten_ts && data.ten_ts == "1") {
                    this.activeQuyTrinhXacMinhCapNhatTTLH = true;
                } else {
                    this.activeQuyTrinhXacMinhCapNhatTTLH = false;
                }
            } else {
                this.activeQuyTrinhXacMinhCapNhatTTLH = false;
            }
        },

        getKieuHopDong: async function () {
            let data = {
                table: 'KIEU_HD',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsKieuHopDong = res.data
            if (this.dsKieuHopDong.length > 0) {
                this.kieuHopDongId = this.dsKieuHopDong[0].kieuhd_id
            }
        },

        getLoaiGiayTo: async function () {
            let data = {
                table: 'LOAI_GT',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsLoaiGiayTo = res.data
            if (this.dsLoaiGiayTo.length > 0) {
                this.loaiGiayToId = this.dsLoaiGiayTo[0].loaigt_id
            }
        },

        getQuocTich: async function () {
            let data = {
                table: 'QUOCTICH',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsQuocTich = res.data
            if (this.dsQuocTich.length > 0) {
                this.quocTichId = await this.dsQuocTich[0].quoctich_id
                await this.getDanToc()
            }
        },

        getNganhNghe: async function () {
            let data = {
                table: 'NGANHNGHE',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsNganhNghe = res.data
            if (this.dsNganhNghe.length > 0) {
                this.nganhNgheId = this.dsNganhNghe[0].nganhnghe_id
            }
        },

        onChangeQuocTich: async function () {
            await this.getDanToc()
        },

        getDanToc: async function () {
            let data = {
                table: 'DANTOC',
                bangChaId: this.quocTichId
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsDanToc = res.data
            if (this.dsDanToc.length > 0) {
                this.danTocId = this.dsDanToc[0].dantoc_id
            }
        },

        getLoaiKhachHang: async function () {
            let data = {
                table: 'LOAI_KH',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsLoaiKhacHang = res.data
            if (this.dsLoaiKhacHang.length > 0) {
                this.loaiKhachHangId = this.dsLoaiKhacHang[0].loaikh_id
            }
        },

        getLoaiKhachHangLon: async function () {
            let data = {
                table: 'LOAI_KHL',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsKhachHangLon = res.data
            if (this.dsKhachHangLon.length > 0) {
                this.khachHangLonId = this.dsKhachHangLon[0].khlon_id
            }
        },

        getNganHang: async function () {
            let data = {
                table: 'NGANHANG',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsNganHang = res.data
            if (this.dsNganHang.length > 0) {
                this.nganHangId = this.dsNganHang[0].nganhang_id
            }
        },

        getHinhThucTT: async function () {
            let data = {
                table: 'HINHTHUC_TT',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsHinhThucThanhToan = res.data
            if (this.dsHinhThucThanhToan.length > 0) {
                this.hinhThucThanhToanId = this.dsHinhThucThanhToan[0].HTTT_ID
            }
        },

        getDichVuVT: async function () {
            let data = {
                table: 'DICHVU_VT',
                bangChaId: null
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsDichVuVT = res.data
            if (this.dsDichVuVT.length > 0) {
                this.dichVuVTId = await this.dsDichVuVT[0].dichvuvt_id
                await this.getLoaiHinhThueBao()
            }
        },

        onChangeDichVuVT: async function () {
            await this.getLoaiHinhThueBao()
        },

        getLoaiHinhThueBao: async function () {
            let data = {
                table: 'LOAIHINH_TB',
                bangChaId: this.dichVuVTId
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsLoaiHinhTB = res.data
            if (this.dsLoaiHinhTB.length > 0) {
                this.loaiHinhId = this.dsLoaiHinhTB[0].loaitb_id
            }
        },

        diaChiKhachHangClick: function () {
            this.dataSend.tinh_id = this.tinhkh_id
            this.dataSend.quan_id = this.quankh_id
            this.dataSend.phuong_id = this.phuongkh_id
            this.dataSend.dacdiem_id = this.dacdiemkh_id
            this.dataSend.pho_id = this.phokh_id
            this.dataSend.ap_id = this.apkh_id
            this.dataSend.khu_id = this.khukh_id
            this.dataSend.sonha = this.sonhakh
            this.dataSend.diachi = this.diaChiKhachHang
            this.$bvModal.show('popupChonDiaChiKH')
        },

        getDiaChiKhachHang: function (obj) {
            console.log('diachikhachhang', obj)
            this.tinhkh_id = obj.tinh_id
            this.quankh_id = obj.quan_id
            this.phuongkh_id = obj.phuong_id
            this.dacdiemkh_id = obj.dacdiem_id
            this.phokh_id = obj.pho_id
            this.apkh_id = obj.ap_id
            this.khukh_id = obj.khu_id
            this.sonhakh = obj.sonha
            this.diaChiKhachHang = obj.diachimoi
            this.diaChiNDD = obj.diachimoi
            this.hoKhau = obj.diachimoi
        },

        nhapMoiClick: function () {
            this.clean(1)
            this.clean(2)
            this.clean(3)
            this.dsThanhToan = null
            this.dsThueBao = null
        },

        resetValidate: function () {
            this.$v.$reset()
            this.customError.ngaySinh = false
            this.customError.dienThoai = false
            this.customError.diaChiKhachHang = false
        },

        ghiLaiClick: async function () {
            this.$root.showLoading(true);
            try {
                this.resetValidate()
                await this.capNhat()
            } finally {
                this.$root.showLoading(false);
            }
        },
        ycThayDoiThongTinClick: async function () {
            // if (this.hasHDKH) {
            //     this.$router.push({ path: '/contract/ChangeCustomerInfor', query: { tag: 0, hdkh_id: this.hdkh_id } });
            // }
            if(this.hasHDKH){
                this.ChangeCustomerInfor_data = { tag: 0, hdkh_id: this.hdkh_id };
                this.$bvModal.show('popupChangeCustomerInfor')
            }
        },
        closeChangeCustomerInfor: function() {
            this.$bvModal.hide('popupChangeCustomerInfor')
        },
        pdThayDoiThongTinClick: async function () {
            // if (this.hasHDKH) {
            //     this.$router.push({ path: '/contract/ChangeCustomerInfor', query: { tag: 1, hdkh_id: this.hdkh_id } });
            // }
            if(this.hasHDKH){
                this.ChangeCustomerInfor_data = { tag: 1, hdkh_id: this.hdkh_id };
                this.$bvModal.show('popupChangeCustomerInfor')
            }
        },

        diaChiNDDClick: function () {
            this.dataSend.tinh_id = this.tinhndd_id
            this.dataSend.quan_id = this.quanndd_id
            this.dataSend.phuong_id = this.phuongndd_id
            this.dataSend.dacdiem_id = this.dacdiemndd_id
            this.dataSend.pho_id = this.phondd_id
            this.dataSend.ap_id = this.apndd_id
            this.dataSend.khu_id = this.khundd_id
            this.dataSend.sonha = this.sonhandd
            this.dataSend.diachi = this.diaChiNDD
            this.$bvModal.show('popupChonDiaChiNDD')
        },

        getDiaChiNDD: function (obj) {
            this.tinhndd_id = obj.tinh_id
            this.quanndd_id = obj.quan_id
            this.phuongndd_id = obj.phuong_id
            this.dacdiemndd_id = obj.dacdiem_id
            this.phondd_id = obj.pho_id
            this.apndd_id = obj.ap_id
            this.khundd_id = obj.khu_id
            this.sonhandd = obj.sonha
            this.diaChiNDD = obj.diachimoi
        },

        hoKhauClick: function () {
            this.dataSend.tinh_id = null
            this.dataSend.quan_id = null
            this.dataSend.phuong_id = null
            this.dataSend.dacdiem_id = null
            this.dataSend.pho_id = null
            this.dataSend.ap_id = null
            this.dataSend.khu_id = null
            this.dataSend.sonha = null
            this.dataSend.diachi = this.hoKhau
            this.$bvModal.show('popupChonDiaChiHK')
        },

        getDiaChiHK: function (obj) {
            this.hoKhau = obj.diachimoi
        },

        gridDsThanhToan_rowSelected: async function (dataItem) {
            console.log('gridDsThanhToan_rowSelected', dataItem)
            this.clean(2)
            this.hdtt_id = dataItem.hdtt_id
            let ds_tttt = await this.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.hdtt_id)
            console.log(ds_tttt)
            if (ds_tttt != null) {
                if (ds_tttt.thantoan_id != "")
                    this.thanhtoan_id = ds_tttt.thantoan_id
                else
                    this.thanhtoan_id = 0
                this.tenThanhToan = this.chuanHoaTen(ds_tttt.ten_tt)
                this.ten_tt_cu = this.tenThanhToan
                this.diaChiChungTu = this.chuanHoaTen(ds_tttt.diachi_ct)
                this.diachi_ct_cu = this.diaChiChungTu
                this.DV_QHNS = ds_tttt.dv_qhns
                this.dv_qhns_cu = this.DV_QHNS
                this.MACHUONG = ds_tttt.machuong
                this.machuong_cu = this.MACHUONG
                this.MN_KT = ds_tttt.mn_kt
                this.mn_kt_cu = this.MN_KT
                this.MA_NDKT = ds_tttt.ma_ndkt
                this.ma_ndkt_cu = this.MA_NDKT
                this.MN_NSNN = ds_tttt.mn_nsnn
                this.mn_nsnn_cu = this.MN_NSNN
                if (ds_tttt.tien_dm != null)
                    this.TIEN_DM = ds_tttt.tien_dm
                else
                    this.TIEN_DM = 0
                this.tien_dm_cu = ds_tttt.tien_dm
                this.maSoThueTT = ds_tttt.mst
                this.mst_tt_cu = this.maSoThueTT
                this.taiKhoanTT = ds_tttt.stk
                this.stk_tt_cu = this.taiKhoanTT
                this.chuTaiKhoan = ds_tttt.chu_tk
                this.chu_tk_tt_cu = this.chuTaiKhoan
                if (ds_tttt.nganhang_id != "") {
                    this.isCheckNH = true
                    this.nganHangThanhToanId = ds_tttt.nganhang_id
                }
                else
                    this.isCheckNH = false
                this.nganhang_tt_cu = ds_tttt.nganhang_id
                this.hinhThucThanhToanId = ds_tttt.httt_id
                this.httt_id_cu = ds_tttt.httt_id
            }
        },

        LAY_DS_HOPDONG_TT_THEO_HDTT_ID: async function (hdttid) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdttId: hdttid//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_HOPDONG_TT_THEO_HDTT_ID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_hdtt_theo_hdttid', dataInput)
            if (res.data.length > 0)
                return res.data[0]
            else return null
        },

        diaChiChungTuClick: function () {
            // this.dataSend.tinh_id = this.tinhtt_id
            // this.dataSend.quan_id = this.quantt_id
            // this.dataSend.phuong_id = this.phuongtt_id
            // this.dataSend.dacdiem_id = this.dacdiemtt_id
            // this.dataSend.pho_id = this.phott_id
            // this.dataSend.ap_id = this.aptt_id
            // this.dataSend.khu_id = this.khutt_id
            // this.dataSend.sonha = this.sonhatt
            this.dataSend.tinh_id = null
            this.dataSend.quan_id = null
            this.dataSend.phuong_id = null
            this.dataSend.dacdiem_id = null
            this.dataSend.pho_id = null
            this.dataSend.ap_id = null
            this.dataSend.khu_id = null
            this.dataSend.sonha = null
            this.dataSend.diachi = this.diaChiChungTu
            this.$bvModal.show('popupChonDiaChiTT')
        },

        getDiaChiTT: function (obj) {
            // this.tinhtt_id = obj.tinh_id
            // this.quantt_id = obj.quan_id
            // this.phuongtt_id = obj.phuong_id
            // this.dacdiemtt_id = obj.dacdiem_id
            // this.phott_id = obj.pho_id
            // this.aptt_id = obj.ap_id
            // this.khutt_id = obj.khu_id
            // this.sonhatt = obj.sonha
            this.diaChiChungTu = obj.diachimoi
        },

        nganHangThanhToanClick: function () {
        },

        gridDsThueBao_rowSelected: async function (dataItem) {
            console.log('gridDsThueBao_rowSelected', dataItem)
            this.clean(3)
            this.hdtb_id = dataItem.hdtb_id
            let ds_tttb = await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id)
            console.log(ds_tttb)
            if (ds_tttb != null) {
                if (ds_tttb.thuebao_id != "")
                    this.thuebao_id = ds_tttb.thuebao_id
                else
                    this.thuebao_id = 0
                this.maThueBao = ds_tttb.ma_tb
                this.tenThueBao = this.chuanHoaTen(ds_tttb.ten_tb)
                this.ten_tb_cu = this.tenThueBao
                this.ghiChu = ds_tttb.ghichu
                this.diaChiThueBao = this.chuanHoaTen(ds_tttb.diachi_tb)
                this.diachi_tb_cu = this.diaChiThueBao
                this._trangthaiTB = 0
                if (ds_tttb.status != "")
                    this._trangthaiTB = ds_tttb.status
                this.dichVuVTId = ds_tttb.dichvuvt_id
                await this.onChangeDichVuVT()
                this.loaiHinhId = ds_tttb.loaitb_id
                this._loaiTbID = ds_tttb.loaitb_id
                if (this.dichVuVTId == Enum.DichVuVienThong.ADSL || this.dichVuVTId == Enum.DichVuVienThong.MEGA_EYES
                    || this.dichVuVTId == Enum.DichVuVienThong.MEGAWAN || this.dichVuVTId == Enum.DichVuVienThong.TSL) {
                    this.HienThiTTMoRong(ds_tttb)
                }
                await this.HienThiTTDiaChi(this.hdtb_id, 3)
            }
        },

        HienThiTTMoRong: function (data) {
            switch (this.dichVuVTId) {
                case Enum.DichVuVienThong.CO_DINH:
                    break
                case Enum.DichVuVienThong.IMS:
                    break
                case Enum.DichVuVienThong.DI_DONG:
                    break
                case Enum.DichVuVienThong.ADSL:
                    this.soAo = data.ma_lt != null ? data.ma_lt.split(",")[0].split(";")[0] : null
                    break
                case Enum.DichVuVienThong.MEGA_EYES:
                    this.soAo = data.ma_lt != null ? data.ma_lt.split(",")[0].split(";")[0] : null
                    break
                case Enum.DichVuVienThong.MEGAWAN:
                    this.soAo = data.ma_lt != null ? data.ma_lt.split(",")[0].split(";")[0] : null
                    break;
                case Enum.DichVuVienThong.TSL:
                    let soaodau = ""
                    let soaocuoi = ""
                    let dsSoAo = data.ma_lt != null ? data.ma_lt.split(",") : null
                    if (dsSoAo != null) {
                        for (let i = 0; i < dsSoAo.length; i++) {
                            if (dsSoAo[i].split(";")[1] == 1) // điểm đầu
                            {
                                soaodau = dsSoAo[i].split(";")[0]
                            } else soaocuoi = dsSoAo[i].split(";")[0]
                        }
                        this.soAo = soaodau + "," + soaocuoi
                    }
                    break
                case Enum.DichVuVienThong.GPHONE:
                    break
                default:
                    break
            }
        },

        LAY_DS_HOPDONG_TB_THEO_HDTB_ID: async function (hdttid) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdtbId: hdttid//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_HOPDONG_TB_THEO_HDTB_ID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_hdtb_theo_hdtbid', dataInput)
            if (!(res.data == null || res.data.length == 0))
                return res.data[0]
            else return null
        },

        maGiaoDichKeypress: async function (e) {
            if (e.keyCode == 13) {
                this.$root.showLoading(true);
                try {
                    await this.getThongTinHopDong()
                } finally {
                    this.$root.showLoading(false);
                }
            }
        },

        getThongTinHopDong: async function () {
            try {
                let ma_gd = '0'
                if (this.maGiaoDich != '') {
                    this.didong = false
                    this.Fkey = ""
                    this.fkey_new = ""
                    this.phieutt_id = 0
                    this.invToken = ""
                    ma_gd = this.maGiaoDich
                }
                let data = {
                    //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                    maGiaoDich: ma_gd,
                    loaiHdId: 0//,
                    //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_HOPDONG_THEO_MA_GD'
                }
                //console.log('data', data)
                let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_hopdong_theo_magiaodich', data)
                if (res.data != null && res.data.length > 0) {
                    if (res.data.length > 1) {
                        this.dataSend.dsBienDong = res.data
                        this.$bvModal.show('popupDanhSachBienDong')
                    } else {
                        await this.getGiaoDich(res.data[0])
                    }
                    // this.dsPhieuYc = rs.data
                    // this.tongphieu = rs.data.length
                } else {
                    let data = {
                        //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                        maThueBao: ma_gd,
                        //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DB_DIDONG_THEO_MATB'
                    }
                    //console.log('data', data)
                    let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_db_didong_theo_matb', data)
                    if (res.data.length > 0) {
                        await this.getGiaoDich(res.data[0])
                    } else {
                        this.clean(1)
                        this.clean(2)
                        this.clean(3)
                        this.dsThueBao = null
                        this.dsThanhToan = null
                        this.$toast.error("Không tìm thấy hợp đồng " + ma_gd)
                    }
                }

            } catch (error) {
                console.log(error)
                return null;
            } finally {
            }
        },

        getGiaoDich: async function (data) {
            console.log('chonGiaoDich', data)
            await this.clean(1)
            await this.clean(2)
            await this.clean(3)
            this.dsThanhToan = null
            this.dsThueBao = null
            if (this.didong == false) {
                if (await this.checkHopDong(data) == false) return
                await this.thietLapGiaoDien(data)
                await this.hienThiDuLieuKH(data)
            } else {
                this.suaThongTinId = 4
                this.disableSuaThongTin = true
                await this.hienThiTTDanhBaKH(data)
            }
        },

        checkHopDong: async function (data) {
            let loaihd_id = data.loaihd_id
          
            // Hung bo xung: loaihd_id != Enum.LoaiHopDong.GIAHAN,loaihd_id != Enum.LoaiHopDong.THAYDOI_DATCOC,loaihd_id != Enum.LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,  && loaihd_id != Enum.LoaiHopDong.THAY_TT_KHAC
            if (loaihd_id != Enum.LoaiHopDong.DAT_MOI && loaihd_id != Enum.LoaiHopDong.DI_CHUYEN
                && loaihd_id != Enum.LoaiHopDong.CHUYENDOI_LH && loaihd_id != Enum.LoaiHopDong.DANGKY_GOI_DADV 
                && loaihd_id != Enum.LoaiHopDong.DATCOC_MOI
                && loaihd_id != Enum.LoaiHopDong.THAY_DOI_DV && loaihd_id != Enum.LoaiHopDong.KHOIPHUC_SD
                && loaihd_id != Enum.LoaiHopDong.THAY_DOI_TOCDO_ADSL
                && loaihd_id != Enum.LoaiHopDong.GIAHAN
                && loaihd_id != Enum.LoaiHopDong.THAYDOI_DATCOC
                && loaihd_id != Enum.LoaiHopDong.THAYDOI_THONGSO_MEGAWAN
                && loaihd_id != Enum.LoaiHopDong.THAY_TT_KHAC
                ) {
                // this.$toast.error("Hợp đồng này không nằm trong danh sách các loại hợp đồng được phép sửa!")
                this.suaThongTinId = 4
                this.disableSuaThongTin = true
                await this.hienThiTTDanhBaKH(data)
                return false
            }
            let phanvung_id=this.$root.token.getPhanVungID();
            // cho phép chỉnh sửa với HNI, các vùng khác vẫn giữ nguyên
            if (loaihd_id == Enum.LoaiHopDong.THAY_TT_KHAC && phanvung_id != 21) {
                // this.$toast.error("Hợp đồng này không nằm trong danh sách các loại hợp đồng được phép sửa!")
                this.suaThongTinId = 4
                this.disableSuaThongTin = true
                await this.hienThiTTDanhBaKH(data)
                return false
            }
            if (this.SUATT_HOPDONG_THEO_USER == 1 && this.quyen_sua_hd_hoanthien == 0) {
                if (data.nhanvien_id != this.$root.token.getNhanVienID()) {
                    let dataInput = {
                        //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                        hdkhId: data.hdkh_id//,
                        //P_NHANVIEN_ID: this.$root.token.getNhanVienID(),
                        //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_CHECKQUYEN_SUAHOPDONG_NV'
                    }
                    //console.log('data', data)
                    let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/check_quyen_sua_hopdong_nhanvien', dataInput)
                    if (res.data != null && res.data.length <= 0) {
                        this.$toast.error("Bạn không được sửa thông tin hợp đồng do nhân viên khác tạo !")
                        return false
                    }
                }
            }
            else {
                let dataInput = {
                    //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                    donViId: data.donvi_id//,
                    //P_NHANVIEN_ID: this.$root.token.getNhanVienID(),
                    //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_CHECK_NHANVIEN_DONVI'
                }
                //console.log('data', data)
                let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/check_nhanvien_donvi', dataInput)
                if (res.data == null || res.data.length == 0) {
                    if (data.donvi_id != this.$root.token.getDonViID() && this.quyen_sua_hd_hoanthien == 0) {
                        this.$toast.error("Bạn không được sửa thông tin hợp đồng do đơn vị khác tạo !")
                        return false
                    }
                }
            }
        },

        thietLapGiaoDien: function (data) {
            //this.maGiaoDich = data.MA_GD
            let loaihd_id = data.loaihd_id
            //loaihd_id == Enum.LoaiHopDong.CHUYENDOI_LH : Phương báo đã trao đổi với Hiếu để bỏ điều kiện này
            // || loaihd_id == Enum.LoaiHopDong.DATCOC_MOI :Hùng bỏ điều kiện theo phiếu IT360-802850
            if (loaihd_id == Enum.LoaiHopDong.DANGKY_GOI_DADV 
                || loaihd_id == Enum.LoaiHopDong.THAY_DOI_TOCDO_ADSL) {
                this.suaThongTinId = 4
                this.disableSuaThongTin = true
            }
            else {
                this.suaThongTinId = 1
                this.disableSuaThongTin = false
            }
        },

        hienThiTTDanhBaKH: async function (data) {
            await this.hienThiDuLieuKH(data)
        },

        hienThiDuLieuKH: async function (data) {
            this.nguoiLapHD = data.nhanvien_id
            this.hdkh_id = data.hdkh_id != null ? data.hdkh_id : 0
            let ds_phieutt = await this.layThongTinPhieuThanhToan(this.hdkh_id)
            if (ds_phieutt != null && ds_phieutt.length > 0) {
                this.Fkey = ds_phieutt.hddt_fkey
                this.phieutt_id = ds_phieutt.phieutt_id
                this.invToken = ds_phieutt.kyhieu + ";" + ds_phieutt.seri + ";" + ds_phieutt.soseri
            }
            this.ngayYeuCau = data.ngay_yc != null ? data.ngay_yc.substr(0, 10) : null//data.ngay_yc
            this.ngayLapHD = data.ngaylap_hd != null ? data.ngaylap_hd.substr(0, 10) : null//data.ngaylap_hd
            //this.maGiaoDich = data.MA_GD
            this.maHopDong = data.ma_hd
            this.maKH = data.ma_kh
            this.khachhang_id = data.khachhang_id != "" && data.khachhang_id != null ? data.khachhang_id : 0
            if (data.loaikh_id != "" && data.loaikh_id != null) {
                this.loaiKhachHangId = data.loaikh_id
                this.loaikh_id_cu = data.loaikh_id
            }
            if (data.kieuhd_id != "" && data.kieuhd_id != null) {
                this.kieuHopDongId = data.kieuhd_id
                this.kieuhd_id_cu = data.kieuhd_id
            } else {
                this.kieuHopDongId = Enum.KIEU_HD.TAI_GD
                this.kieuhd_id_cu = null
            }

            this.tenKhachHang = this.chuanHoaTen(data.ten_kh)
            this.ten_kh_cu = this.tenKhachHang

            this.diaChiKhachHang = data.diachi_kh
            this.diachi_kh_cu = this.diaChiKhachHang

            await this.HienThiTTDiaChi(this.hdkh_id, 1)

            this.nguoiDaiDien = this.chuanHoaTen(data.nguoi_dd)
            this.nguoi_dd_cu = this.nguoiDaiDien

            this.chucVu = this.chuanHoaTen(data.chucdanh_dd)
            this.chucdanh_dd_cu = this.chucVu

            this.dienThoai = data.so_dt
            this.so_dt_cu = this.dienThoai

            this.fax = data.so_fax
            this.so_fax_cu = this.fax

            this.hoKhau = data.hokhau
            this.hokhau_cu = this.hoKhau

            this.noiCapHK = this.chuanHoaTen(data.noicap_hk)
            this.noicap_hk_cu = this.noiCapHK

            if (data.ngay_hk != null) {
                this.ngayCapHK = data.ngay_hk.substr(0, 10)
                this.ngay_hk_cu = this.ngayCapHK
            }
            else {
                this.ngayCapHK = ""
                this.ngay_hk_cu = ""
            }

            if (data.ngay_sn != null) {
                this.ngaySinh = data.ngay_sn.substr(0, 10)//DateTimeLib.toDateTimeObjectWithFormat(data.ngay_sn,'YYYY-MM-DD')
                this.ngay_sn_cu = this.ngaySinh
            }
            else {
                this.ngaySinh = ""
                this.ngay_sn_cu = ""
            }
            if (data.gioitinh != null) {
                if (data.gioitinh == 0) {
                    this.gioiTinh = 0
                    this.gioitinh_cu = "0"
                }
                else if (data.gioitinh == 1) {
                    this.gioiTinh = 1
                    this.gioitinh_cu = "1"
                }
            }
            else {
                this.gioiTinh = null
                this.gioitinh_cu = ""
            }

            if (data.quoctich_id != null) {
                this.quocTichId = data.quoctich_id
                this.quoctich_id_cu = data.quoctich_id
            }
            else
                this.quoctich_id_cu = ""

            if (data.dantoc_id != null) {
                this.danTocId = data.dantoc_id
                this.dantoc_id_cu = data.dantoc_id
            }
            else
                this.dantoc_id_cu = ""

            if (data.nganhnghe_id != null) {
                this.nganhNgheId = data.nganhnghe_id
                this.nganhnghe_id_cu = data.nganhnghe_id
            }
            else
                this.nganhnghe_id_cu = ""

            if (data.tnc1_id != null) {
                this.TNC1_ID = data.tnc1_id
                this.tnc1_id_cu = data.tnc1_id
            }
            else
                this.tnc1_id_cu = ""

            if (data.tnc2_id != null) {
                this.TNC2_ID = data.tnc2_id
                this.tnc2_id_cu = data.tnc2_id
            }
            else
                this.tnc2_id_cu = ""

            if (data.tnc3_id != null) {
                this.TNC3_ID = data.tnc3_id
                this.tnc3_id_cu = data.tnc3_id
            }
            else
                this.tnc3_id_cu = ""

            this.diaChiNDD = this.chuanHoaTen(data.diachi_ndd)
            this.diachi_ndd_cu = this.diaChiNDD

            if (data.ngayhethan_gt != null) {
                this.ngayHetHan = data.ngayhethan_gt.substr(0, 10)
                this.ngayhethan_gt_cu = this.ngayHetHan
            }
            else {
                this.ngayHetHan = ""
                this.ngayhethan_gt_cu = ""
            }
            this.maSoThue = data.mast
            this.mst_cu = this.maSoThue
            this.taiKhoan = data.stk
            this.stk_cu = this.taiKhoan
            if (data.nganhang_id != null) {
                this.nganHangId = data.nganhang_id
                this.isCheckNH = true
                this.nganhang_id_cu = data.nganhang_id
            }
            else
                this.nganhang_id_cu = ""
            this.soGiayTo = data.so_gt
            this.so_gt_cu = this.soGiayTo
            if (data.ngaycap != null) {
                this.ngayCapGT = data.ngaycap.substr(0, 10)
                this.ngaycap_cu = this.ngayCapGT
            }
            else {
                this.ngayCapGT = ""
                this.ngaycap_cu = ""
            }
            this.loaiGiayToId = data.loaigt_id
            if (data.loaigt_id != null)
                this.loaigt_id_cu = data.loaigt_id
            else
                this.loaigt_id_cu = ""
            this.noiCapGT = this.chuanHoaTen(data.noicap)
            this.noicap_cu = this.noiCapGT
            this.khachHangLonId = data.khlon_id
            if (data.khlon_id != null)
                this.khlon_id_cu = data.khlon_id
            this.dacTrung = data.dactrung
            this.dactrung_cu = this.dacTrung
            if (data.httthi_id != null) {
                this.hinhThucTiepThiId = data.httthi_id
                this.httthi_id_cu = data.httthi_id
            }
            else {
                this.hinhThucTiepThiId = 0
                this.httthi_id_cu = ""
            }

            if (this.didong == false) {
                await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
                await this.lay_ds_hdtb_theo_hdkh_id_v1(this.hdkh_id)
            } else {
                await this.LAY_DS_DBTT_THEO_KHID()
                await this.LAY_DS_DBTB_THEO_KHID()
            }

            if (data.ctv_id != null) {
                console.log('ctv', data)
                this.isCheckCTV = true
                this.ctv_id = data.ctv_id
                this.ctv_id_cu = data.ctv_id
                this.congTacVien = data.ten_ctv
                //console.log('ctv gan', this.congTacVien)
            }
            else {
                this.isCheckCTV = false
                this.ctv_id = -1
                this.ctv_id_cu = ""
                this.congTacVien = ""
            }

            if (data.nhanviengt_id != null) {
                this.isCheckNguoiGT = true
                this.nguoigt_id = data.nhanviengt_id
                this.nguoigt_id_cu = data.nhanviengt_id
                this.nguoiGT = data.ten_ngt
            }
            else {
                this.isCheckNguoiGT = false
                this.nguoigt_id = -1
                this.nguoigt_id_cu = ""
                this.nguoiGT = ""
            }

        },

        layDiaChiTheoHDKHID: async function (hdkhid) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdkhId: hdkhid//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_DIACHI_THEO_HDKH_ID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_hdkhid', dataInput)
            return res.data
        },

        layDiaChiTheoKHID: async function (khid) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                khachHangId: khid//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_DIACHI_THEO_DBKHID'
            }
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_khid', dataInput)
            //console.log('diachi', res.data)
            return res.data
        },

        layDiaChiTheoHDTBID: async function () {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdtbId: this.hdtb_id//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_DIACHI_THEO_HDTB_ID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_hdtbid', dataInput)
            return res.data
        },

        layThongTinPhieuThanhToan: async function (hdkhid) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdkhId: hdkhid//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_PHIEU_THANHTOAN'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_phieu_thanhtoan', dataInput)
            if (res.data.length > 0)
                return res.data[0]
            else return null
        },

        traCuuHoaDonDienTu: async function () {
            try {
                //let inv = "01GTKT0/002;AC/18E;89"
                let dataInput = {
                    phanvung_id: parseInt(this.$root.token.getPhanVungID()),
                    invToken: this.invToken
                }
                //console.log('data', data)
                let res = await this.$root.context.post('/app-com/hoadon_dientu/downloadInvNoPay', dataInput)
                this.objHoaDonDienTu = res.data.Content
                this.objHoaDonDienTuEdit = res.data.Content
            } catch (e) {
                //console.log(e.response)
                this.$toast.error("Lỗi khi lấy thông tin hóa đơn: " + e.response.data.message)
                this.objHoaDonDienTu = null
                this.objHoaDonDienTuEdit = null
                return false
            }

        },

        sinhFkeyHDDT: async function () {
            // let dataInput = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_SINH_FKEY'
            // }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/sinh_fkey')
            if (res.data.length > 0)
                return res.data[0].fkey
            else return null
        },

        dieuChinhHoaDonDienTu: async function (pattern, serial) {
            try {
                let fkey_new = await this.sinhFkeyHDDT()
                if (fkey_new == null || fkey_new == "") {
                    this.$toast.error("Có lỗi trong quá trình điều chỉnh hóa đơn: tỉnh này không sinh được fkey")
                    return
                }
                let dataInput = {
                    phanvung_id: parseInt(this.$root.token.getPhanVungID()),
                    pattern: pattern,
                    serial: serial,
                    fkey: this.Fkey,
                    //attachFile: "string",
                    convert: 0,
                    type: 4,
                    fkey_new: fkey_new,
                    cusCode: this.objHoaDonDienTuEdit.CusCode,
                    cusName: this.objHoaDonDienTuEdit.CusName,
                    cusAddress: this.objHoaDonDienTuEdit.CusAddress,
                    cusPhone: this.objHoaDonDienTuEdit.CusPhone,
                    cusTaxCode: this.objHoaDonDienTuEdit.CusTaxCode,
                    kind_of_Payment: this.objHoaDonDienTuEdit.Kind_of_Payment,
                    kindOfService: this.objHoaDonDienTuEdit.KindOfService,
                    vthem_extra: 0,
                    total: 0,
                    vatrate: 10,
                    vatamount: 0,
                    amount: 0,
                    amount_words: "",
                    ma_nv: this.$root.token.getMaNhanVien(),
                    products: [{
                        prodName: this.khoanmuc_dieuchinh,
                        prodUnit: "",
                        prodQuantity: 0,
                        prodPrice: 0,
                        amount: 0
                    }]
                }
                let res = await this.$root.context.post('/app-com/hoadon_dientu/adjustInvAction', dataInput)
                return res.data.response
            } catch (e) {
                let error = null
                switch (e.response.data.message) {
                    case "ERR:1":
                        error = "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng"
                        break
                    case "ERR:2":
                        error = "Hóa đơn cần điều chỉnh không tồn tại"
                        break
                    case "ERR:3":
                        error = "Dữ liệu xml đầu vào không đúng quy định"
                        break
                    case "ERR:5":
                        error = "Không phát hành được hóa đơn"
                        break
                    case "ERR:6":
                        error = "Dải hóa đơn cũ đã hết"
                        break
                    case "ERR:7":
                        error = "User name không phù hợp, không tìm thấy company tương ứng cho user"
                        break
                    case "ERR:8":
                        error = "Hóa đơn cần điều chỉnh đã bị thay thế. Không thể điều chỉnh được nữa."
                        break
                    case "ERR:9":
                        error = "Trạng thái hóa đơn không được điều chỉnh"
                        break
                    case "ERR:19":
                        error = "Pattern truyền vào không giống với hóa đơn cần điều chỉnh"
                        break
                    case "ERR:20":
                        error = "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp"
                        break
                    default:
                        break
                }
                this.$toast.error("Có lỗi trong quá trình điều chỉnh hóa đơn: " + error)
                return null
            }
        },

        thayTheHoaDonDienTu: async function (pattern, serial) {
            try {
                let fkey_new = await this.sinhFkeyHDDT()
                if (fkey_new == null || fkey_new == "") {
                    this.$toast.error("Có lỗi trong quá trình thay thế hóa đơn: tỉnh này không sinh được fkey")
                    return
                }
                let dataInput = {
                    phanvung_id: parseInt(this.$root.token.getPhanVungID()),
                    pattern: pattern,
                    serial: serial,
                    fkey: this.Fkey,
                    //attachFile: "string",
                    convert: 0,
                    type: 4,
                    fkey_new: fkey_new,
                    cusCode: this.objHoaDonDienTuEdit.CusCode,
                    cusName: this.objHoaDonDienTuEdit.CusName,
                    cusAddress: this.objHoaDonDienTuEdit.CusAddress,
                    cusPhone: this.objHoaDonDienTuEdit.CusPhone,
                    cusTaxCode: this.objHoaDonDienTuEdit.CusTaxCode,
                    kind_of_Payment: this.objHoaDonDienTuEdit.Kind_of_Payment,
                    kindOfService: this.objHoaDonDienTuEdit.KindOfService,
                    vthem_extra: 0,
                    total: this.objHoaDonDienTuEdit.Total,
                    vatrate: this.objHoaDonDienTuEdit.VAT_Rate,
                    vatamount: this.objHoaDonDienTuEdit.VAT_Amount,
                    amount: this.objHoaDonDienTuEdit.Amount,
                    amount_words: this.objHoaDonDienTuEdit.Amount_words,
                    ma_nv: this.$root.token.getMaNhanVien(),
                    products: this.objHoaDonDienTuEdit.Products.Product
                }
                let res = await this.$root.context.post('/app-com/hoadon_dientu/adjustInvAction', dataInput)
                return res.data.response
            } catch (e) {
                let error = null
                switch (e.response.data.message) {
                    case "ERR:1":
                        error = "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng"
                        break
                    case "ERR:2":
                        error = "Không tồn tại hóa đơn cần thay thế"
                        break
                    case "ERR:3":
                        error = "Dữ liệu xml đầu vào không đúng quy định"
                        break
                    case "ERR:5":
                        error = "Có lỗi trong quá trình thay thế hóa đơn"
                        break
                    case "ERR:6":
                        error = "Dải hóa đơn cũ đã hết"
                        break
                    case "ERR:7":
                        error = "User name không phù hợp, không tìm thấy company tương ứng cho user"
                        break
                    case "ERR:8":
                        error = "Hóa đơn đã được thay thế rồi. Không thể thay thế nữa."
                        break
                    case "ERR:9":
                        error = "Trạng thái hóa đơn không được thay thế"
                        break
                    case "ERR:20":
                        error = "Pattern và serial không phù hợp"
                        break
                    default:
                        break
                }
                this.$toast.error("Có lỗi trong quá trình thay thế hóa đơn: " + error)
                return null
            }
        },

        LAY_DS_HOPDONG_TT_THEO_HDKH_ID: async function (hdkhid) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdkhId: hdkhid//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_HOPDONG_TT_THEO_HDKH_ID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_hopdong_tt_theo_hdkh_id', dataInput)
            if (res.data.length > 0)
                this.dsThanhToan = res.data
        },

        lay_ds_hdtb_theo_hdkh_id_v1: async function (hdkhid) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdkhId: hdkhid//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_HOPDONG_TB_THEO_HDKH_ID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_hopdong_tb_theo_hdkh_id', dataInput)
            if (res.data.length > 0) {
                this.dsThueBao = res.data
            }
        },

        LAY_DS_DBTT_THEO_KHID: async function () {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                khachhang_id: this.khachhang_id//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_DBTT_THEO_KHID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_danhba_tt_theo_khachhang_id', dataInput)
            if (res.data.length > 0)
                this.dsThanhToan = res.data
        },

        LAY_DS_DBTB_THEO_KHID: async function () {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                khachhang_id: this.khachhang_id//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_DBTB_THEO_KHID'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_danhba_tb_theo_khachhang_id', dataInput)
            if (res.data.length > 0)
                this.dsThueBao = res.data
        },

        HienThiTTDiaChi: async function (hdkhid, kieu) {
            let res = null
            if (kieu == 1)
                res = !this.didong ? await this.layDiaChiTheoHDKHID(hdkhid) : await this.layDiaChiTheoKHID(hdkhid)
            else res = await this.layDiaChiTheoHDTBID()
            let data = res[0]
            console.log('diachi', data)
            if (data != null) {
                if (kieu == 1) {
                    this.diachikh_id = data.diachi_id
                    this.diachi_kh_id_cu = this.diachikh_id
                    this.tinhkh_id = data.tinh_id
                    this.quankh_id = data.quan_id
                    this.phuongkh_id = data.phuong_id
                    this.phokh_id = data.pho_id != null ? data.pho_id : 0
                    this.apkh_id = data.ap_id != null ? data.ap_id : 0
                    this.khukh_id = data.khu_id != null ? data.khu_id : 0
                    this.dacdiemkh_id = data.dacdiem_id != null ? data.dacdiem_id : 0
                    this.sonhakh = this.chuanHoaTen(data.sonha)
                    this.lat_KH = data.kinhdo
                    this.lng_KH = data.vido

                }

                if (kieu == 3) {
                    this.diachitb_id = data.diachi_id
                    this.diachi_tb_id_cu = this.diachitb_id
                    this.tinhtb_id = data.tinh_id
                    this.quantb_id = data.quan_id
                    this.phuongtb_id = data.phuong_id
                    this.photb_id = data.pho_id != null ? data.pho_id : 0
                    this.aptb_id = data.ap_id != null ? data.ap_id : 0
                    this.khutb_id = data.khu_id != null ? data.khu_id : 0
                    this.dacdiemtb_id = data.dacdiem_id != null ? data.dacdiem_id : 0
                    this.sonhatb = this.chuanHoaTen(data.sonha)
                    this.lat_TB = data.kinhdo
                    this.lng_TB = data.vido

                    this.diachild_id = data.diachild_id
                    this.tinhld_id = data.tinhld_id
                    this.quanld_id = data.quanld_id
                    this.phuongld_id = data.phuongld_id
                    this.phold_id = data.phold_id != null ? data.phold_id : 0
                    this.apld_id = data.apld_id != null ? data.apld_id : 0
                    this.khuld_id = data.khuld_id != null ? data.khuld_id : 0
                    this.dacdiemld_id = data.dacdiemld_id != null ? data.dacdiemld_id : 0
                    this.sonhald = this.chuanHoaTen(data.sonhald)
                    this.lat_LD = data.kinhdo_ld
                    this.lng_LD = data.vido_ld
                }
            }
        },

        diaChiThueBaoClick: function () {
            this.dataSend.tinh_id = this.tinhtb_id
            this.dataSend.quan_id = this.quantb_id
            this.dataSend.phuong_id = this.phuongtb_id
            this.dataSend.dacdiem_id = this.dacdiemtb_id
            this.dataSend.pho_id = this.photb_id
            this.dataSend.ap_id = this.aptb_id
            this.dataSend.khu_id = this.khutb_id
            this.dataSend.sonha = this.sonhatb
            this.dataSend.diachi = this.diaChiThueBao
            this.$bvModal.show('popupChonDiaChiTB')
        },

        getDiaChiTB: function (obj) {
            this.tinhtb_id = obj.tinh_id
            this.quantb_id = obj.quan_id
            this.phuongtb_id = obj.phuong_id
            this.dacdiemtb_id = obj.dacdiem_id
            this.photb_id = obj.pho_id
            this.aptb_id = obj.ap_id
            this.khutb_id = obj.khu_id
            this.sonhatb = obj.sonha
            this.diaChiThueBao = obj.diachimoi
        },

        nganhNgheClick: async function () {
            this.objChiTietNganhNghe = {}
            this.objChiTietNganhNghe.TNC1_ID = this.TNC1_ID
            this.objChiTietNganhNghe.TNC2_ID = this.TNC2_ID
            this.objChiTietNganhNghe.TNC3_ID = this.TNC3_ID
            this.objChiTietNganhNghe.TNC4_ID = this.TNC4_ID
            this.objChiTietNganhNghe.nganhnghe_id = this.nganhNgheId
            this.$bvModal.show('popupChiTietNganhNghe')
        },

        kiemTraNgaySinh: function () {
            let today = new Date()
            let birthDate = new Date(this.ngaySinh)
            let age = today.getFullYear() - birthDate.getFullYear()
            let m = today.getMonth() - birthDate.getMonth()
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--
            }
            return age
        },

        kiemTraDL_KhachHang: function () {
            this.$v.maHopDong.$touch()
            this.$v.maKH.$touch()
            this.$v.tenKhachHang.$touch()
            this.$v.diaChiKhachHang.$touch()
            this.$v.loaiGiayToId.$touch()
            this.$v.soGiayTo.$touch()
            this.$v.ngayCapGT.$touch()
            this.$v.nganhNgheId.$touch()
            this.$v.dienThoai.$touch()
            this.$v.nguoiDaiDien.$touch()
            let errorMessage = []
            if (this.tenKhachHang == null || this.tenKhachHang == '') {
                errorMessage.push("Chưa nhập tên khách hàng.")
            }

            if (this.ngaySinh != null && this.ngaySinh != '') {
                if (this.loaiKhachHangId == Enum.Loai_KH.TUNHAN_TRONGNUOC || this.loaiGiayToId == Enum.LoaiGiayTo.CHUNG_MINH_THU) {
                    let age = this.kiemTraNgaySinh()
                    if (age < 18) {
                        errorMessage.push("Khách hàng chưa đủ 18 tuổi để làm hợp đồng")
                        this.customError.ngaySinh = true
                    }
                }
            } else {
                errorMessage.push("Chưa nhập ngày sinh")
            }

            if (this.diaChiKhachHang == null || this.quankh_id == null || this.phuongkh_id == null) {
                errorMessage.push("Chưa nhập địa chỉ khách hàng")
                this.customError.diaChiKhachHang = true
            }

            if (this.nguoiDaiDien == null || this.nguoiDaiDien == "") {
                errorMessage.push("Chưa nhập tên người đại diện")
            }
            if (this.maKH == null || this.maKH == "") {
                errorMessage.push("Chưa nhập mã khách hàng")
            }
            if (this.maHopDong == null || this.maHopDong == "") {
                errorMessage.push("Chưa nhập mã hợp đồng")
            }
            if ((this.soGiayTo == "" || this.soGiayTo == null) && this.loaiGiayToId != 7) {
                errorMessage.push("Chưa nhập số giấy tờ")
            }
            if ((this.hoKhau == "" || this.hoKhau == null) && (this.soGiayTo == "" || this.soGiayTo == null)) {
                errorMessage.push("Hãy nhập hộ khẩu hoặc loại giấy tờ khác")
            }
            if (this.loaiKhachHangId == null) {
                errorMessage.push("Hãy chọn loại khách hàng")
            }
            if (this.ngayCapGT == null || this.ngayCapGT == "") {
                errorMessage.push("Hãy nhập ngày cấp giấy tờ")
            }
            if (this.TNC1_ID == 0 || this.TNC2_ID == 0 || this.TNC3_ID == 0) {
                errorMessage.push("Bạn chưa chọn chi tiết ngành nghề")
            }
            if (this.dienThoai == "" || this.dienThoai == null) {
                errorMessage.push("Hãy nhập số điện thoại liên hệ của khách hàng.")
            } else if (this.dienThoai.length > 11) {
                errorMessage.push("Số điện thoại liên hệ của khách hàng không được quá 11 ký tự.")
                this.customError.dienThoai = true
            }
            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else {
                return true
            }
        },

        KIEMTRA_HTTT: function () {
            try {
                let checkSuDungCu = this.dsHinhThucThanhToan.find(x => x.httt_id == this.httt_id_cu).sudung
                let checkSuDungMoi = this.dsHinhThucThanhToan.find(x => x.httt_id == this.hinhThucThanhToanId).sudung
                if ((checkSuDungCu == 1 && checkSuDungMoi == 0) || (checkSuDungCu == 0 && checkSuDungMoi == 0)) {
                    return "Bạn đang chọn hình thức thanh toán không được sử dụng. Bạn hãy chọn lại"
                } else {
                    return null
                }

            }
            catch (ex) {
                this.$toast.error("[PTTB] Lỗi khi kiểm tra HTTT " + ex)
            }
        },

        kiemTraChoPhepThuTaiNha: async function () {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                loaiKhachHangId: this.loaiKhachHangId//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_CHOPHEP_THUTAINHA'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/chophep_thutainha', dataInput)
            return res.data
        },

        kiemTraDL_ThanhToan: async function () {
            this.$v.hinhThucThanhToanId.$touch()
            this.$v.diaChiChungTu.$touch()
            this.$v.tenThanhToan.$touch()

            let errorMessage = []
            if (this.tenThanhToan == null || this.tenThanhToan == '') {
                errorMessage.push("Chưa nhập tên thanh toán.")
            }
            if (this.diaChiChungTu == "" || this.diaChiChungTu == null) {
                errorMessage.push("Chưa nhập địa chỉ chứng từ")
            }
            if (this.HTTTId == Enum.HinhThucThanhToan.UYNHIEM_THU
                || this.HTTTId == Enum.HinhThucThanhToan.UYNHIEM_CHI) {
                if (this.taiKhoan == "" || this.taiKhoan == null) {
                    errorMessage.push("Hãy nhập Tài khoản")
                }
                if (this.chuTaiKhoan == "" || this.chuTaiKhoan == null) {
                    errorMessage.push("Hãy nhập Chủ tài khoản")
                }
            }
            if (this.hinhThucThanhToanId == Enum.HinhThucThanhToan.THU_TAI_NHA) {
                let ds = await this.kiemTraChoPhepThuTaiNha()
                if (ds.length > 0)
                    if (ds[0].loai == Enum.KIEU_KH.DOANH_NGHIEP) {
                        errorMessage.push("Khách hàng doanh nghiệp không thể thu tại địa chỉ lắp đặt")
                    }
            }
            let kiemtraHTTT = this.KIEMTRA_HTTT()
            if (kiemtraHTTT != null && kiemtraHTTT != "") {
                errorMessage.push(kiemtraHTTT)
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else {
                return true
            }
        },

        kiemTraDL_ThueBao: function () {
            this.$v.diaChiThueBao.$touch()
            this.$v.tenThueBao.$touch()

            let errorMessage = []
            if (this.tenThueBao == null || this.tenThueBao == '') {
                errorMessage.push("Chưa nhập tên thuê bao.")
            }
            if (this.diaChiThueBao == "" || this.diaChiThueBao == null || this.quantb_id == 0
                || this.phuongtb_id == 0 || (this.photb_id == 0 && this.khutb_id == 0 && this.aptb_id == 0)) {
                errorMessage.push("Chưa nhập địa chỉ thuê bao")
                this.customError.diaChiThueBao = true
            }
            if (this._trangthaiTB != 0 && this._loaiTbID == Enum.LoaiHinhTB.INTERNET_MYTV) {
                errorMessage.push("Thuê bao này đã kích hoạt trên VASC. Bạn không thể sửa mã thuê bao.")
                this.maThueBao = this._maTBOld
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else {
                return true
            }
        },

        kiemTraDL_CTV: function () {
            // this.$v.diaChiThueBao.$touch()
            // this.$v.tenThueBao.$touch()

            let errorMessage = []
            if (this.khong_batbuoc_ctv == 0) {
                if (!this.isCheckCTV || this.ctv_id == -1 || this.congTacVien.Text == "") {
                    errorMessage.push("Bạn chưa chọn nhân viên phát triển")
                }

                if (this.hinhThucTiepThiId == null) {
                    errorMessage.push("Hãy chọn hình thức tiếp thị")
                }
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else {
                return true
            }
        },

        kiemTraDL_NGT: function () {
            // this.$v.diaChiThueBao.$touch()
            // this.$v.tenThueBao.$touch()

            let errorMessage = []
            if (this.khong_batbuoc_ctv == 0) {
                if (!this.isCheckCTV || this.ctv_id == -1 || this.congTacVien.Text == "") {
                    errorMessage.push("Bạn chưa chọn nhân viên phát triển")
                }

                if (this.hinhThucTiepThiId == null) {
                    errorMessage.push("Hãy chọn hình thức tiếp thị")
                }
            }
            if (this.batbuoc_gioithieu_lapmoi == 1) {
                if (!this.isCheckNguoiGT || this.nguoiGT == null || this.nguoiGT == "" || this.nguoigt_id == 0) {
                    errorMessage.push("Bạn chưa chọn người giới thiệu hợp đồng/phụ lục")
                }
            }
            if (this.SUATT_HOPDONG_THEO_USER == 1 && this.quyen_sua_hd_hoanthien == 0) {
                if (this.nguoiLapHD != this.$root.token.getNhanVienID()) {
                    errorMessage.push("Bạn không được sửa thông tin hợp đồng về người giới thiệu do nhân viên khác tạo")
                }
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else {
                return true
            }
        },

        kiemtra_capnhat_tt_hopdong: async function () {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdkhId: this.hdkh_id,
                hdtbId: this.hdtb_id,
                kieu: this.suaThongTinId,
                chan: 1//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_KIEMTRA_CAPNHAT_TT_HOPDONG'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/kiemtra_capnhat_thongtin_hopdong', dataInput)
            if (res.data != null && res.data.length > 0) {
                return res.data[0].kq
            }
            return null
        },

        kiemtra_capnhat_tt_hopdong_v2: async function () {
            let dataInput = {
                hdkhId: this.hdkh_id,
                hdtbId: this.hdtb_id,
                kieu: this.suaThongTinId,
                chan: 1
            }
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/kiemtra_capnhat_thongtin_hopdong_v2', dataInput)
            if (res.data != null) {
                return res.data
            }
            return null
        },

        layThongTinLoaHoaDonSeri: async function () {
            // let dataInput = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_TT_LOAIHOADON_SERI'
            // }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/thongtin_loaihoadon_seri')
            if (res.data.length > 0) {
                return res.data[0]
            }
            return null
        },

        OBJtoXML: function (obj) {
            let xml = ''
            for (let prop in obj) {
                xml += obj[prop] instanceof Array ? '' : "<" + prop + ">"
                if (obj[prop] instanceof Array) {
                    for (let array in obj[prop]) {
                        xml += "<" + prop + ">"
                        xml += this.OBJtoXML(new Object(obj[prop][array]))
                        xml += "</" + prop + ">"
                    }
                } else if (typeof obj[prop] == "object") {
                    xml += this.OBJtoXML(new Object(obj[prop]))
                } else {
                    xml += obj[prop]
                }
                xml += obj[prop] instanceof Array ? '' : "</" + prop + ">"
            }
            xml = xml.replace(/<\/?[0-9]{1,}>/g, '')
            return xml
        },

        themBienDongHoaDon: async function (kieuBienDong, trangThai, seriID, fkey, loaiHDID) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdkh_id: this.hdkh_id,
                ma_tt: this.objHoaDonDienTuEdit.CusCode,
                kieu_bd: kieuBienDong,
                //P_NGUOI_CN: this.$root.token.getUserName(),
                //P_MAY_CN: this.$root.token.getUserName(),
                thongtinhd_cu: this.OBJtoXML(this.objHoaDonDienTu),
                thongtinhd_moi: this.OBJtoXML(this.objHoaDonDienTuEdit),
                ghichu: "Thay đổi thông tin hợp đồng",
                phieutt_id: this.phieutt_id,
                tongtien: this.objHoaDonDienTu.Amount,
                loaihoadon_cu: this.objHoaDonDienTu.InvoicePattern,
                loaihoadon_moi: this.objHoaDonDienTuEdit.InvoicePattern,
                seri_cu: this.objHoaDonDienTu.SerialNo,
                seri_moi: this.objHoaDonDienTuEdit.SerialNo,
                so_hd_cu: this.objHoaDonDienTu.InvoiceNo,
                so_hd_moi: this.objHoaDonDienTuEdit.InvoiceNo,
                trangthai: trangThai,
                seri_moi_id: seriID,
                fkey: fkey,
                loaihoadon_id: loaiHDID//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_BIENDONG_HOADON'
            }
            //console.log('data', data)
            let res = await this.$root.context.post('/web-hopdong/ThayDoiHopDong/thembiendong_hoadon', dataInput)
            return res.data
        },

        themBienDongHoaDonChiTiet: async function (bdhddt_id, product, stt) {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                bdhddt_id: bdhddt_id,
                dongia: Math.abs(product.ProdPrice),
                donvitinh: product.ProdUnit,
                noidung: product.ProdName,
                soluong: Math.abs(parseInt(product.ProdQuantity)),
                stt: stt,
                thanhtien: Math.abs(product.Amount)//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_BIENDONG_HOADON_CT'
            }
            //console.log('data', data)
            await this.$root.context.post('/web-hopdong/ThayDoiHopDong/thembiendong_hoadon', dataInput)
        },

        checkHoaDonDieuChinh: async function () {
            let dataInput = {
                //P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
                hdkhId: this.hdkh_id//,
                //PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_CHECK_HD_DIEUCHINH'
            }
            //console.log('data', data)
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/check_hoadon_dieuchinh', dataInput)
            return res.data[0].kq
        },

        capNhat: async function () {
            try {
                this.khoanmuc_dieuchinh = ""
                if (this.hdkh_id == null || (this.diDong == true && this.khachhang_id == null)) {
                    this.$toast.error("Bạn chưa chọn hợp đồng")
                    this.$v.maGiaoDich.$touch()
                    return
                }
                if (this.lyDo == "" || this.lyDo == null) {
                    this.$toast.error("Bạn chưa nhập lý do hiệu chỉnh")
                    this.$v.lyDo.$touch()
                    return
                }
                let kiemTraDL
                if (this.suaThongTinId == 1) {
                    kiemTraDL = this.kiemTraDL_KhachHang()
                }
                if (this.suaThongTinId == 2) {
                    kiemTraDL = await this.kiemTraDL_ThanhToan()
                }
                if (this.suaThongTinId == 3) {
                    kiemTraDL = this.kiemTraDL_ThueBao()
                }
                if (this.suaThongTinId == 4) {
                    kiemTraDL = this.kiemTraDL_CTV()
                }
                if (this.suaThongTinId == 5) {
                    kiemTraDL = this.kiemTraDL_NGT()
                }
                if (!kiemTraDL) return

                if (this.suaThongTinId != 4) {
                    let phanvung_id = this.$root.token.getPhanVungID()
                    let kt
                    //HNI
                    if (phanvung_id == 21) {
                        kt = await this.kiemtra_capnhat_tt_hopdong_v2()
                    }
                    else {
                        kt = await this.kiemtra_capnhat_tt_hopdong()
                    }
                    if (kt != null) {
                        this.$toast.error(kt)
                        return
                    }
                    if (this.phieutt_id != 0 && this.Fkey != "" && this.invToken != "") {
                        if (this.suaThongTinId == 1 || this.suaThongTinId == 2) {
                            let traCuuHDDT = await this.traCuuHoaDonDienTu()
                            if (traCuuHDDT == false) {
                                return
                            }
                            let dc_tt_hddt = false
                            let vdc_tt = 0 // 0 : Không làm gì. 1: Điều chỉnh. 2 : Thay thế

                            if (this.suaThongTinId == 2 &&
                                (this.diaChiChungTu != this.objHoaDonDienTu.CusAddress
                                    || this.maSoThue != this.objHoaDonDienTu.CusTaxCode
                                    || this.tenThanhToan != this.objHoaDonDienTu.CusName
                                )
                            ) {
                                dc_tt_hddt = true
                            }
                            if (dc_tt_hddt == true) {
                                let tracnghiem = {
                                    title: '',
                                    caption: 'Chọn điều chỉnh hoặc thay thế',
                                    options: ['Điều chỉnh', 'Thay thế']
                                }
                                if (this.XUAT_HOADON == 1) {
                                    tracnghiem.options.push("Không xuất Hóa đơn và Biến động HDDT")
                                }
                                await this.$refs.ModalTracnghiemExt.show(tracnghiem)
                                    .then(result => {
                                        if (result.ok) {
                                            if (result.option == 0) {
                                                vdc_tt = 1
                                            } else if (result.option == 1) {
                                                vdc_tt = 2
                                            } else {
                                                vdc_tt = 0
                                            }
                                        }
                                    })
                                let h_sohoadon = ""
                                let h_pattern = ""
                                let h_seri = ""
                                let h_loaihoadon_id = 0
                                let h_seri_id = 0
                                let ds_hd_seri = await this.layThongTinLoaHoaDonSeri()
                                if (ds_hd_seri != null) {
                                    h_loaihoadon_id = ds_hd_seri.LOAIHOADON_ID
                                    h_pattern = ds_hd_seri.TEN_LOAIHD
                                    h_seri_id = ds_hd_seri.SERI_ID
                                    h_seri = ds_hd_seri.SERI
                                }
                                else {
                                    this.$toast.error("ERR: Không tìm thấy loại hóa đơn, seri !")
                                    return
                                }

                                if (vdc_tt == 1) // Điều chỉnh
                                {
                                    if (this.suaThongTinId == 2) {
                                        if (this.tenThanhToan != this.objHoaDonDienTu.CusName) {
                                            this.khoanmuc_dieuchinh += " tên,"
                                            this.objHoaDonDienTuEdit.CusName = this.tenThanhToan
                                        }
                                        if (this.diaChiChungTu != this.objHoaDonDienTu.CusAddress) {
                                            this.khoanmuc_dieuchinh += " địa chỉ,"
                                            this.objHoaDonDienTuEdit.CusAddress = this.diaChiChungTu
                                        }
                                        if (this.maSoThue != this.objHoaDonDienTu.CusTaxCode) {
                                            if (this.objHoaDonDienTu.CusTaxCode == "") {
                                                this.khoanmuc_dieuchinh += " mã số thuế,"
                                                this.objHoaDonDienTuEdit.CusTaxCode = this.maSoThue
                                            }
                                            else {
                                                this.$toast.error("Bạn không được điều chỉnh mã số thuế !")
                                                return
                                            }
                                        }
                                        this.khoanmuc_dieuchinh = this.khoanmuc_dieuchinh.substr(0, this.khoanmuc_dieuchinh.length - 1)
                                        this.khoanmuc_dieuchinh = "Điều chỉnh thông tin" + this.khoanmuc_dieuchinh + " khách hàng"
                                        this.objHoaDonDienTuEdit.Products = null
                                    }

                                    let kq = await this.dieuChinhHoaDonDienTu(h_pattern, h_seri)
                                    if (kq == null) {
                                        return
                                    }
                                    let result = kq.Split(':')
                                    if (result[0] == "OK") {
                                        let result1 = result[1].Split(';')
                                        this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
                                        this.objHoaDonDienTuEdit.SerialNo = result1[1]
                                        this.objHoaDonDienTuEdit.InvoiceNo = result1[2].Split('_')[1]
                                        let fkey_new = result1[2].Split('_')[0]
                                        let bdhddt_id = await this.themBienDongHoaDon(10, 3, h_seri_id, fkey_new, h_loaihoadon_id)
                                        let product = {
                                            ProdPrice: 0,
                                            ProdUnit: "",
                                            ProdName: khoanmuc_dieuchinh,
                                            ProdQuantity: 0,
                                            Amount: 0
                                        }
                                        await this.themBienDongHoaDonChiTiet(bdhddt_id, product, 1)
                                        this.$toast.success("Điều chỉnh hóa đơn thành công")
                                        this.invToken = null
                                        this.Fkey = null
                                        fkey_new = ""
                                        this.phieutt_id = 0
                                    }
                                    else {
                                        //this.$toast.error("Có lỗi trong quá trình điều chỉnh hóa đơn")
                                        return
                                    }
                                }
                                if (vdc_tt == 2) // Thay thế
                                {
                                    let kthddieuchinh = await this.checkHoaDonDieuChinh()
                                    if (kthddieuchinh > 0) {
                                        this.$toast.error("Hợp đồng đã có hóa đơn bị điều chỉnh. Bạn không thể thay thế")
                                        return
                                    }

                                    if (this.suaThongTinId == 2) {
                                        if (this.tenThanhToan != this.objHoaDonDienTu.CusName) {
                                            this.objHoaDonDienTuEdit.CusName = this.tenThanhToan
                                        }
                                        if (this.diaChiChungTu != this.objHoaDonDienTu.CusAddress) {
                                            this.objHoaDonDienTuEdit.CusAddress = this.diaChiChungTu
                                        }
                                        if (this.maSoThue != this.objHoaDonDienTu.CusTaxCode && this.objHoaDonDienTu.CusTaxCode == "") {
                                            this.objHoaDonDienTuEdit.CusTaxCode = this.maSoThue
                                        }
                                    }

                                    let kq = await this.thayTheHoaDonDienTu(h_pattern, h_seri)
                                    if (kq == null) {
                                        return
                                    }
                                    let result = kq.Split(':')
                                    if (result[0] == "OK") {
                                        let result1 = result[1].Split(';')
                                        this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
                                        this.objHoaDonDienTuEdit.SerialNo = result1[1]
                                        this.objHoaDonDienTuEdit.InvoiceNo = result1[2].Split('_')[1]
                                        let fkey_new = result1[2].Split('_')[0]
                                        let bdhddt_id = await this.themBienDongHoaDon(2, 2, h_seri_id, fkey_new, h_loaihoadon_id)
                                        for (let i = 0; i < this.objHoaDonDienTuEdit.Products.product.length; i++) {
                                            let product = this.objHoaDonDienTuEdit.Products.product[i]
                                            await this.themBienDongHoaDonChiTiet(bdhddt_id, product, i + 1)
                                        }
                                        this.$toast.success("Thay thế hóa đơn thành công")
                                        this.invToken = null
                                        this.Fkey = null
                                        fkey_new = ""
                                        this.phieutt_id = 0
                                    }
                                    else {
                                        //this.$toast.error("Có lỗi trong quá trình thay thế hóa đơn")
                                        return
                                    }
                                }
                            }
                        }
                    }

                    await this.capNhatThongTinHopDong()

                }
                else {
                    let phanvung_id = this.$root.token.getPhanVungID()
                    let kt
                    //HNI
                    if (phanvung_id == 21) {
                        kt = await this.kiemtra_capnhat_tt_hopdong_v2()
                    }
                    else {
                        kt = await this.kiemtra_capnhat_tt_hopdong()
                    }
                    if (kt != null) {
                        this.$toast.error(kt)
                        return
                    }
                    await this.capNhatThongTinHopDong()
                }
            } catch (ex) {
                this.$toast.error("Có lỗi : " + ex)
            }
        },

        capNhatThongTinHopDong: async function () {
            let dataInput = {
                ap_id: this.suaThongTinId == 1 ? this.apkh_id : this.aptb_id,
                chucdanh_dd: this.chucVu,
                chucdanh_dd_cu: this.chucdanh_dd_cu,
                chutk: this.chuTaiKhoan,
                ctv_id: this.suaThongTinId == 5 ? this.nguoigt_id : this.ctv_id,
                ctv_id_cu: this.ctv_id_cu,
                dacdiem_id: this.suaThongTinId == 1 ? this.dacdiemkh_id : this.dacdiemtb_id,
                dactrung: this.dacTrung,
                dactrung_cu: this.dactrung_cu,
                dantoc_id: this.danTocId,
                dantoc_id_cu: this.dantoc_id_cu,
                diachi: this.suaThongTinId == 1 ? this.diaChiKhachHang : this.diaChiThueBao,
                diachi_ct: this.diaChiChungTu,
                diachi_ct_cu: this.diachi_ct_cu,
                diachi_kh_cu: this.diachi_kh_cu,
                diachi_kh_id_cu: this.diachi_kh_id_cu,
                diachi_ndd: this.diaChiNDD,
                diachi_ndd_cu: this.diachi_ndd_cu,
                diachi_tb_cu: this.diachi_tb_cu,
                diachi_tb_id_cu: this.diachi_tb_id_cu,
                diachild_id: this.diachild_id,
                didong: this.didong ? 1 : 0,
                dv_qhns: this.DV_QHNS,
                dv_qhns_cu: this.dv_qhns_cu,
                ghichu: this.ghiChu,
                gioitinh: this.gioiTinh,
                gioitinh_cu: this.gioitinh_cu,
                hdkh_id: this.hdkh_id,
                hdtb_id: this.hdtb_id,
                hdtt_id: this.hdtt_id,
                hokhau: this.hoKhau,
                hokhau_cu: this.hokhau_cu,
                httt_id: this.hinhThucThanhToanId,
                httt_id_cu: this.httt_id_cu,
                httthi_id: this.hinhThucTiepThiId,
                httthi_id_cu: this.httthi_id_cu,
                khlon_id: this.khachHangLonId,
                khlon_id_cu: this.khlon_id_cu,
                khu_id: this.suaThongTinId == 1 ? this.khukh_id : this.khutb_id,
                kieu: parseInt(this.suaThongTinId),
                kieuhd_id: this.kieuHopDongId,
                kieuhd_id_cu: this.kieuhd_id_cu,
                kinhdo: this.suaThongTinId == 1 ? this.lat_KH : this.lat_TB,
                kinhdo_ld: this.lat_LD,
                loaigt_id: this.loaiGiayToId,
                loaigt_id_cu: this.loaigt_id_cu,
                loaikh_id: this.loaiKhachHangId,
                loaikh_id_cu: this.loaikh_id_cu,
                lydo: this.lyDo,
                ma_ndkt: this.MA_NDKT,
                ma_ndkt_cu: this.ma_ndkt_cu,
                machuong: this.MACHUONG,
                machuong_cu: this.machuong_cu,
                matb: this.maThueBao,
                mn_kt: this.MN_KT,
                mn_kt_cu: this.mn_kt_cu,
                mn_nsnn: this.MN_NSNN,
                mn_nsnn_cu: this.mn_nsnn_cu,
                mst: this.maSoThue,
                mst_cu: this.mst_cu,
                nganhang_id: this.nganHangId,
                nganhang_id_cu: this.nganhang_id_cu ? this.nganhang_id_cu : 0,
                nganhangtt_id: this.nganHangThanhToanId,
                nganhangtt_id_cu: this.nganhangtt_id_cu,
                nganhnghe_id: this.nganhNgheId,
                nganhnghe_id_cu: this.nganhnghe_id_cu,
                ngay_hk: this.ngayCapHK ? this.ngayCapHK.split('-').reverse().join('/') : null,
                ngay_hk_cu: this.ngay_hk_cu ? this.ngay_hk_cu.split('-').reverse().join('/') : null,
                ngay_sn: this.ngaySinh ? this.ngaySinh.split('-').reverse().join('/') : null,
                ngay_sn_cu: this.ngay_sn_cu ? this.ngay_sn_cu.split('-').reverse().join('/') : null,
                ngaycap: this.ngayCapGT ? this.ngayCapGT.split('-').reverse().join('/') : null,
                ngaycap_cu: this.ngaycap_cu ? this.ngaycap_cu.split('-').reverse().join('/') : null,
                ngayhethan_gt: this.ngayHetHan ? this.ngayHetHan.split('-').reverse().join('/') : null,
                ngayhethan_gt_cu: this.ngayhethan_gt_cu ? this.ngayhethan_gt_cu.split('-').reverse().join('/') : null,
                nguoi_dd: this.nguoiDaiDien,
                nguoi_dd_cu: this.nguoi_dd_cu,
                noicap: this.noiCapGT,
                noicap_cu: this.noicap_cu,
                noicap_hk: this.noiCapHK,
                noicap_hk_cu: this.noicap_hk_cu,
                pho_id: this.suaThongTinId == 1 ? this.phokh_id : this.photb_id,
                phuong_id: this.suaThongTinId == 1 ? this.phuongkh_id : this.phuongtb_id,
                quan_id: this.suaThongTinId == 1 ? this.quankh_id : this.quantb_id,
                quoctich_id: this.quocTichId,
                quoctich_id_cu: this.quoctich_id_cu,
                so_dt: this.dienThoai,
                so_dt_cu: this.so_dt_cu,
                so_fax: this.fax,
                so_fax_cu: this.so_fax_cu,
                so_gt: this.soGiayTo,
                so_gt_cu: this.so_gt_cu,
                sonha: this.suaThongTinId == 1 ? this.sonhakh : this.sonhatb,
                stk: this.taiKhoan,
                stk_cu: this.stk_cu,
                ten_kh: this.tenKhachHang,
                ten_kh_cu: this.ten_kh_cu,
                ten_tb: this.tenThueBao,
                ten_tb_cu: this.ten_tb_cu,
                ten_tt: this.tenThanhToan,
                ten_tt_cu: this.ten_tt_cu,
                thuebao_id: this.thuebao_id,
                tien_dm: this.TIEN_DM,
                tien_dm_cu: this.tien_dm_cu,
                tinh_id: this.suaThongTinId == 1 ? this.tinhkh_id : this.tinhtb_id,
                tnc1_id: this.TNC1_ID,
                tnc1_id_cu: this.tnc1_id_cu,
                tnc2_id: this.TNC2_ID,
                tnc2_id_cu: this.tnc2_id_cu,
                tnc3_id: this.TNC3_ID,
                tnc3_id_cu: this.tnc3_id_cu,
                vido: this.suaThongTinId == 1 ? this.lng_KH : this.lng_TB,
                vido_ld: this.lng_LD
            }
            //console.log('data', data)
            let res = await this.$root.context.post('/web-hopdong/ThayDoiHopDong/thaydoi_thongtin_hopdong', dataInput)
            if (res.error_code == 'BSS-00000000') {
                this.$toast.success("Cập nhật dữ liệu thành công")
            }
            //this.maGiaoDichKeypress()
        },

        clean: function (vkieu) {
            this.lyDo = null
            // this.congTacVien = null
            // this.ctv_id = null
            // this.nguoigt_id = null
            // this.nguoiGT = null
            if (vkieu == 1) {
                this.lat_TB = 0
                this.lng_TB = 0
                this.lat_KH = 0
                this.lng_KH = 0
                this.hdkh_id = null
                this.khachhang_id = null
                //this.danTocId = this.dsDanToc[0].DANTOC_ID
                this.quocTichId = this.dsQuocTich[0].QUOCTICH_ID
                this.maHopDong = ""
                this.maKH = ""
                this.tenKhachHang = ""
                this.diaChiKhachHang = ""
                this.tinhkh_id = null
                this.quankh_id = null
                this.phuongkh_id = null
                this.phokh_id = null
                this.apkh_id = null
                this.khukh_id = null
                this.dacdiemkh_id = null
                this.sonhakh = ""
                this.nguoiDaiDien = ""
                this.chucVu = ""
                this.diaChiNDD = ""
                this.dienThoai = ""
                this.fax = ""
                this.ngaySinh = null
                this.ngayYeuCau = this.getToDay()
                this.ngayLapHD = this.getToDay()
                this.soGiayTo = ""
                this.hoKhau = ""
                this.ngayCapGT = null
                this.noiCapGT = ""
                this.gioiTinh = 0
                this.loaiGiayToId = this.dsLoaiGiayTo[0].LOAIGT_ID
                this.hinhThucTiepThiId = this.dsHinhThucTiepThi[0].HTTTHI_ID
                this.dacTrung = ""
                this.ngayHetHan = null
                this.noiCapHK = ""
                this.taiKhoan = ""
                this.ngayCapHK = null
                this.maSoThue = ""
                this.TNC1_ID = 0
                this.TNC2_ID = 0
                this.TNC3_ID = 0
                this.TNC4_ID = 0
                this.nganhNgheId = 0
            }
            else if (vkieu == 2) {
                this.hdtt_id = null
                this.thanhtoan_id = null
                this.tenThanhToan = ""
                this.maSoThueTT = ""
                this.taiKhoanTT = ""
                this.chuTaiKhoan = ""
                this.diaChiChungTu = ""
                this.hinhThucThanhToanId = this.dsHinhThucThanhToan[0].HTTT_ID
                this.DV_QHNS = ""
                this.MACHUONG = ""
                this.MN_KT = ""
                this.MA_NDKT = ""
                this.MN_NSNN = ""
                this.TIEN_DM = 0
                //this.httt_id_cu = ""
            }
            else if (vkieu == 3) {
                this.hdtb_id = null
                this.thuebao_id = null
                this.hdtb_id_cha = null
                this.maThueBao = ""
                this.soAo = ""
                this.tenThueBao = ""
                this.ghiChu = ""
                this.diaChiThueBao = ""
                this.tinhtb_id = null
                this.quantb_id = null
                this.phuongtb_id = null
                this.photb_id = null
                this.aptb_id = null
                this.khutb_id = null
                this.dacdiemtb_id = null
                this.sonhatb = ""
            }
        },

        onChangeCTV: function () {
            if (this.isCheckCTV) {
                this.$bvModal.show('popupCongTacVien')
            }
        },
        onChangeNGT: function () {
            if (this.isCheckNguoiGT) {
                this.$bvModal.show('popupNguoiGioiThieu')
            }
        },

        xacNhanCongTacVien: function (val) {
            this.congTacVien = val.ten_ht
            this.ctv_id = val.nhanvien_id
            this.$bvModal.hide('popupCongTacVien')
        },

        xacNhanNguoiGioiThieu: function (val) {
            this.nguoiGT = val.ten_ht
            this.nguoigt_id = val.nhanvien_id
            this.$bvModal.hide('popupNguoiGioiThieu')
        },

        acceptSearchContract: async function (item) {
            this.maGiaoDich = item.ma_gd
            if (this.maGiaoDich && this.maGiaoDich.trim() != "")
                await this.getThongTinHopDong()
        },

        chonMaGiaoDich: function () {
            this.$refs.popupSearchContract.showModal()
        },

        getTNC(objTNC) {
            this.TNC1_ID = objTNC.TNC1_ID
            this.TNC2_ID = objTNC.TNC2_ID
            this.TNC3_ID = objTNC.TNC3_ID
            this.TNC4_ID = objTNC.TNC4_ID
            this.nganhNgheId = objTNC.nganhnghe_id
            //this.cbo_nganhngheloc.Value = this.nganhnghe_id
        },

        chuanHoaTen: function (ten) {
            if (ten != null) {
                let convertToArray = ten.toLowerCase().split(' ')
                let result = convertToArray.map(function (val) {
                    return val.replace(val.charAt(0), val.charAt(0).toUpperCase())
                })
                return result.join(' ')
            } else
                return ''
        }

    }
}
</script>

<style>
a.disable-btn {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
}
.modal-thaydoi-ttkh { width: 90% !important; max-width: 90% !important;}
.popup-body.popup-thaydoi-ttkh { padding: 0px;}
.popup-body.popup-thaydoi-ttkh .breadcrumb-top { display: none;}
.popup-body.popup-thaydoi-ttkh .page-content { padding: 20px; height: auto;}

</style>
