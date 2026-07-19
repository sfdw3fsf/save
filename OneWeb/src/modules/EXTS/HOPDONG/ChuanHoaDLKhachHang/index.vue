<template src="./index.html"></template>

<script>
import { minLength, maxLength, numeric, required } from 'vuelidate/lib/validators'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import Enum from "../ThayDoiThongTinHopDong/Enum"
import ModalChuanHoaThongTinThanhToan from "./ChuanHoaThongTinThanhToan/Modal"
import ModalChuanHoaThongTinThueBao from "./ChuanHoaThongTinThueBao/Modal"
import CongTacVien from "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
import NguoiGioiThieu from "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import ChiTietNganhNghe from '../ThayDoiThongTinKhuyenMai/ChiTietNganhNghe'
import ModalChonDiaChi from './popupChonDiaChi'
import DateTimeLib from "../../../../utils/DateTimeLib"
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
import { previewPrint } from "@/utils/util"
import token from "@/plugins/token"
import moment from 'moment'
import axios from 'axios'
import TraCuuTBTheoMaTB from './TraCuuTBTheoMaTB'
export default {
    props: {
    },
    components: {
        BssErrorMarker,
        BssRequiredMarker,
        ModalChuanHoaThongTinThanhToan,
        ModalChuanHoaThongTinThueBao,
        CongTacVien,
        NguoiGioiThieu,
        SearchContractModal,
        ChiTietNganhNghe,
        ModalChonDiaChi,
        ComboboxGrid,
        SearchAccount,
        TraCuuTBTheoMaTB
    },
    data: function () {
        return {
            objChiTietNganhNghe: null,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: false,
            isDisableBtnXoaHD: false,
            isDisableBtnInHDTieuDe: false,
            isDisableBtnInHDTrang: false,
            isDisableBtnHoanThien: false,
            isDisableBtnThemTB: false,
            isDisableBtnXoaTB: false,
            isDisableBtnChamSocKH: false,
            isDisableBtnHuy: false,

            dsThanhToan: [],
            dsThueBao: [],
            dsHDTB_KV: [],
            dsLoaiHinhTB: null,
            dsDichVuVT: null,
            dsKieuHopDong: null,
            dsNganHang: null,
            dsKhachHangLon: null,
            dsLoaiKhacHang: null,
            dsDanToc: null,
            dsNganhNghe: null,
            dsQuocTich: null,
            dsLoaiGiayTo: null,

            dichvuvt_id: null,
            isCheckCTV: false,
            isCheckNGT: false,
            isCheckNH: false,
            isCheckQT: false,
            isCheckDT: false,
            ctv_id: null,
            congTacVien: null,
            nguoiGioiThieu: null,
            donvi_ctv_id: null,
            loainv_ctv_id: null,
            nguoigt_id: null,
            donvi_nguoi_gt_id: null,
            loainv_nguoi_gt_id: null,
            maThueBao: '',
            trangThaiDuLieu: null,
            maGiaoDich: null,
            ngayYeuCau: null,
            maHopDong: null,
            ngayLapHD: null,
            maKhachHang: null,
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
            danTocId: null,
            loaiKhachHangId: null,
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
            ghiChu: null,
            ds_HDKH: [],
            kiemtra: true,//kiemtra=1:Thue bao thuoc thanh toan,kiemtra=0:thue bao ko thuoc thanh toan
            strerrorVinaphone: "", //Biến lưu kết quả lỗi trả về khi update lên vinaphone
            ten_kh_Mytv: null,
            ngay_sn_Mytv: null,
            gioitinh_Mytv: null,
            diachi_kh_Mytv: null,
            diachi_ld_Mytv: null,
            so_gt_Mytv: null,
            email_Mytv: null,
            sofax_Mytv: null,
            matb_Mytv: null,
            txtPLKH: null,
            diachikh_id: null,
            tinhkh_id: null,
            quankh_id: null,
            phuongkh_id: null,
            phokh_id: null,
            dacdiemkh_id: null,
            apkh_id: null,
            khukh_id: null,
            sonhakh: null,
            trangthaitb_id: 1,
            tinhNDD_id: null,
            quanNDD_id: null,
            phuongNDD_id: null,
            phoNDD_id: null,
            dacdiemNDD_id: null,
            apNDD_id: null,
            khuNDD_id: null,
            sonhaNDD: null,
            hdtb_id: null,
            hdtt_id: null,
            hdkh_id: null,
            khachhang_id: null,
            // khlon_id:null,
            donviQL_id: null,
            kt_tbdd: null,//Biến kiểm tra xem trong hợp đồng có thuê bao di đồng không - HIeu 01.10.2010
            kt_kylai: null,// Biến kiểm tra xem khách hàng này đã ký lại hợp đồng chưa. False = chưa ký lại, (mục đích sử dụng cho việc chuẩn hóa thông tin
            kylai: null,//Biến kiểm tra xem khách hàng đã ký lại hợp đồng chưa, sử dụng cho việc thêm thuê bao mới vào hợp đồng
            TNC1_ID: 0,
            TNC2_ID: 0,
            TNC3_ID: 0,
            TNC4_ID: 0,
            nganhnghe_id: 0,
            quytrinh_id: 0,
            tongSoTT: 0,
            tongSoTB: 0,
            phantap_kh: false,
            pt_doanhthu: 0,
            pt_diem_tn: 0,
            kieukh_canhan: false,
            dataSend: null,
            dsHDKH: null,
            dsHDTT: null,
            dsDiaChi: null,
            dsDiaChi_HDTT: null,//Lưu thông tin quận, phường,phố của HD thanh toán
            dsDiaChiHDTT: null,
            dsDiaChi_HDTB: null,//Lưu thông tin quận, phường,phố của HD thuê bao
            dsDiaChiHDTB: null,
            dsHDTT_NV: null,
            dsDK_DVK: null,
            dsHDTB: null,

            dsThanhToanChon: null,
            dsThueBaoChon: null,

            waterMark: 'Chọn ngày',
            dateVal: new Date(),
            dateFormat: 'dd/MM/yyyy',
            datetimeFormat: "dd/MM/yyyy HH:mm",

            customError: {
                ngaySinh: false,
                dienThoai: false,
                maKH: false,
                diaChiKhachHang: false,
                quocTich: false,
                danToc: false,
                maGD: false
            },
            PHANLOAIKH_ID: -1,
            isShowPopupNganhNghe: true,  //Chặn/Bật popup Chi tiết ngành nghề khi thay đổi nganhnghe_id
            dataTraCuuTBTheoMaTB: {}
        }
    },
    validations: {
        // maGiaoDich: {required},
        maHopDong: { required },
        maKhachHang: { required },
        tenKhachHang: { required },
        diaChiKhachHang: { required },
        loaiGiayToId: { required },
        soGiayTo: { required },
        ngayCapGT: { required },
        ngaySinh: { required },
        nganhnghe_id: { required },
        dienThoai: {
            required,
            maxLength: maxLength(10)
        },
        nguoiDaiDien: { required }
    },
    computed: {
    },
    mounted: async function () {
        await this.initForm()
    },
    methods: {
        initForm: async function () {
            this.isShowPopupNganhNghe = false
            this.ngayLapHD = new Date()
            this.ngayYeuCau = new Date()
            await this.getLoaiGiayTo()
            await this.getQuocTich()
            await this.getNganhNghe()
            await this.getLoaiKhachHang()
            //    await this.getLoaiKhachHangLon()
            await this.getNganHang()
            await this.getDichVuVT()
            this.setButton(-1)
            //this.hienThiTT_DanhBa_KH()

            let elemts = this.$refs.nganhNgheCbo.$refs.grid.$el.querySelectorAll('.e-headercell');
            elemts.forEach(elment => {
                elment.style.display = 'none';
            });
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

        cboLKH_selectedChanged(args) {
            if (args)
                this.loaiKhachHangId = args.loaikh_id;
        },

        selectCboRow(loaikh_id) {
            let index = loaikh_id ? this.dsLoaiKhacHang.findIndex(e => Number(e.loaikh_id) === Number(loaikh_id)) : 0;
            this.$refs.cboLoaiKhachHang.$refs.grid.setCurrentSelectedRow(index !== -1 ? index : 0);
        },

        setNganhNgheCombobox(nganhnghe_id) {
            let index = 0;
            if (this.nganhnghe_id !== Number(nganhnghe_id)) {
                this.isShowPopupNganhNghe = false;
                if (nganhnghe_id)
                    index = this.dsNganhNghe.findIndex(e => Number(e.nganhnghe_id) === Number(nganhnghe_id));
                if (index !== -1)
                    this.$refs.nganhNgheCbo.$refs.grid.setCurrentSelectedRow(index);
                else
                    this.nganhnghe_id = nganhnghe_id;
            } else this.isShowPopupNganhNghe = true;
        },

        nhapMoiClick: async function () {
            this.setButton(1)
        },
        ghiLaiClick: async function () {
            await this.capNhat()
        },
        huyClick: async function () {
            this.setButton(0)
        },
        xoaHDClick: async function () {
            this.$bvModal.show('modalXoaHD')
        },
        inHopDongTieuDeClick: async function () {
            if (this.hdkh_id !== 0)
                await this.inHD(1);
        },
        inHopDongTrangClick: async function () {
            if (this.hdkh_id !== 0)
                await this.inHD(0);
        },
        async inHD(kieuIn) {
            try {
                this.$root.showLoading(true)
                let url = `/web-hopdong/ChuanHoaDuLieu/in-hd?maGD=${this.maGiaoDich}&loaiHDId=${Enum.LoaiHopDong.KY_LAI_HD_GOC}&HDKHId=${this.hdkh_id}&kieuIn=${kieuIn}`;
                let response = await this.getOriginal(url);
                if (response.data.type === 'application/json') {
                    let blob = new Blob([response.data])
                    let data = await blob.text()
                    let rs = JSON.parse(data)
                    if (rs.error_code != null && rs.message != null)
                        return this.$toast.error(rs.message);
                }
                previewPrint(response.data);
            } catch (error) {
                console.log(error)
                this.$toast.error("Có lỗi xảy ra khi in hóa đơn!");
            } finally {
                this.$root.showLoading(false)
            }
        },
        hoanThienClick: async function () {
            await this.HoanThienHS()
        },
        themTBClick: function () {
            this.dataTraCuuTBTheoMaTB = {
                hdkh_id: this.hdkh_id,
                ma_gd: this.maGiaoDich
            };
            this.$bvModal.show('popupTraCuuTBTheoMaTB');

            //frmTraCuuTBTheoMaTB obj = new frmTraCuuTBTheoMaTB();
            // obj.hdkh_id = hdkh_id;
            // obj.ma_gd = txtMaGD.Text.Trim();
            // obj.kt_tbdd = kt_tbdd;
            // obj.donviQL_id = donviQL_id;
            // obj.kylai = kylai;//Lấy giá trị biến ký lại
            // obj.ngayyeucau = dtpNgayYeuCau.Value;
            // obj.ShowDialog();
            // if (obj.chapnhan == true)
            // {
            //    await this.HienThiDanhSacHDTT();
            //    await this.HienThiDanhSacHDTB();
            // }
            //  this.$toast.error("Chức năng gọi form tham chiếu")
        },
        okTraCuuTBTheoMaTB: async function () {
            await this.HienThiDanhSacHDTT();
            await this.HienThiDanhSacHDTB();
        },
        xoaTBClick: async function () {
            this.$bvModal.show('modalXoaTB')
        },
        chamSocKHClick: async function () {
            this.$toast.error("Chức năng gọi form tham chiếu")
        },
        maGiaoDichClick: async function () {
            this.$refs.popupSearchContract.showModal()
        },
        maKhachHangClick: async function () {
            this.$bvModal.show('popupSearchAccount')
        },
        nganhNgheClick: async function () {
            this.objChiTietNganhNghe = {}
            this.objChiTietNganhNghe.TNC1_ID = this.TNC1_ID
            this.objChiTietNganhNghe.TNC2_ID = this.TNC2_ID
            this.objChiTietNganhNghe.TNC3_ID = this.TNC3_ID
            this.objChiTietNganhNghe.TNC4_ID = this.TNC4_ID
            this.objChiTietNganhNghe.nganhnghe_id = this.nganhnghe_id
            this.$bvModal.show('popupChiTietNganhNghe')
        },
        onChangeCTV: function () {
            if (this.isCheckCTV) {
                this.$bvModal.show('popupCongTacVien')
            }
        },
        onChangeNGT: function () {
            if (this.isCheckNGT) {
                this.$bvModal.show('popupNguoiGioiThieu')
            }
        },
        gridDsThanhToan_rowSelected: async function (dataList) {
            this.dsThanhToanChon = dataList
        },
        gridDsThanhToan_DoubleClick: async function (rowIndex, dataItem) {
            this.dataSend = dataItem
            this.dataSend.ma_tt = dataItem.ma_tt
            this.dataSend.ten_tt = dataItem.ten_tt
            this.dataSend.thanhtoan_id = dataItem.thanhtoan_id
            this.dataSend.donvi_id = dataItem.donvi_id
            this.dataSend.stk = dataItem.stk
            this.dataSend.chu_tk = dataItem.chu_tk
            this.dataSend.mst = dataItem.mst
            this.dataSend.ghichu = dataItem.ghichu
            this.dataSend.httt_id = dataItem.httt_id
            this.dataSend.nganhang_id = dataItem.nganhang_id
            this.dataSend.so_dt = dataItem.so_dt
            this.dataSend.email = dataItem.email
            this.dataSend.manv_tc = dataItem.manv_tc
            this.dataSend.ma_tuyen = dataItem.ma_tuyen
            this.dataSend.diachi_ct = dataItem.diachi_ct
            this.dataSend.matb_dd = dataItem.matb_dd

            //Địa chỉ thanh toán
            this.dataSend.diachi_tt = dataItem.diachi_tt != null && dataItem.diachi_tt !== ''
                && dataItem.phuong_id != null && dataItem.phuong_id !== 0
                && dataItem.quan_id != null && dataItem.quan_id !== 0
                && dataItem.tinh_id != null && dataItem.tinh_id
                ? dataItem.diachi_tt : this.diaChiKhachHang
            this.dataSend.khutt_id = dataItem.khu_id
            this.dataSend.aptt_id = dataItem.ap_id
            this.dataSend.phott_id = dataItem.pho_id
            this.dataSend.dacdiemtt_id = dataItem.dacdiem_id
            this.dataSend.phuongtt_id = dataItem.phuong_id != null && dataItem.phuong_id !== 0 ? dataItem.phuong_id : this.phuongkh_id
            this.dataSend.quantt_id = dataItem.quan_id != null && dataItem.quan_id !== 0 ? dataItem.quan_id : this.quankh_id
            this.dataSend.tinhtt_id = dataItem.tinh_id != null && dataItem.tinh_id !== 0 ? dataItem.tinh_id : this.tinhkh_id
            this.dataSend.sonhatt = dataItem.sonha != null && dataItem.diachi_tt !== '' ? dataItem.sonha : this.sonhakh

            //Địa chỉ chứng từ
            this.dataSend.diachi_ct = dataItem.diachi_ct != null && dataItem.diachi_ct !== '' ? dataItem.diachi_ct : this.diaChiKhachHang
            this.dataSend.tinhct_id = dataItem.tinhct_id != null && dataItem.tinhct_id !== 0 ? dataItem.tinhct_id : this.tinhkh_id
            this.dataSend.quanct_id = dataItem.quanct_id != null && dataItem.quanct_id !== 0 ? dataItem.quanct_id : this.quankh_id
            this.dataSend.phuongct_id = dataItem.phuongct_id != null && dataItem.phuongct_id ? dataItem.phuongct_id : this.phuongkh_id
            this.dataSend.dacdiemct_id = dataItem.dacdiemct_id != null ? dataItem.dacdiemct_id : 0
            this.dataSend.phoct_id = dataItem.phoct_id != null ? dataItem.phoct_id : 0
            this.dataSend.apct_id = dataItem.apct_id != null ? dataItem.apct_id : 0
            this.dataSend.khuct_id = dataItem.khuct_id != null ? dataItem.khuct_id : 0
            this.dataSend.sonha_ct = dataItem.sonha_ct != null && dataItem.sonha_ct !== '' ? dataItem.sonha_ct : this.sonhakh

            //Địa chỉ bc
            this.dataSend.diachi_bc = dataItem.diachi_bc
            this.dataSend.tinhbc_id = dataItem.tinhbc_id
            this.dataSend.quanbc_id = dataItem.quanbc_id
            this.dataSend.phuongbc_id = dataItem.phuongbc_id
            this.dataSend.dacdiembc_id = dataItem.dacdiembc_id
            this.dataSend.phobc_id = dataItem.phobc_id
            this.dataSend.apbc_id = dataItem.apbc_id
            this.dataSend.khubc_id = dataItem.khubc_id
            this.dataSend.sonha_bc = dataItem.sonha_bc != null && dataItem.sonha_bc !== '' ? dataItem.sonha_bc : this.sonhakh
            this.dataSend.tuyenthu_id = dataItem.tuyenthu_id
            //Kiểm tra xem đã có hdkh_id không, nếu chưa có thì chọn lại mã nhân viên thu cước để chuẩn hóa dữ liệu
            if (this.hdkh_id != 0) this.dataSend.kt_hdkh_id = true
            //Đẩy biến kiểm tra khách hàng đã ký lại hợp đồng gốc chưa sang biên form tra cứu thanh toán
            this.dataSend.kt_kylai = this.kt_kylai

            this.dataSend.DV_QHNS = dataItem.dv_qhns
            this.dataSend.MACHUONG = dataItem.machuong
            this.dataSend.MN_KT = dataItem.mn_kt
            this.dataSend.MA_NDKT = dataItem.ma_ndkt
            this.dataSend.MN_NSNN = dataItem.ma_nsnn
            this.dataSend.TIEN_DM = dataItem.tien_dm

            this.$bvModal.show('popupChuanHoaThanhToan')
        },
        gridDsThueBao_rowSelected: async function (dataList) {
            this.dsThueBaoChon = dataList
        },
        gridDsThueBao_DoubleClick: async function (rowIndex, dataItem) {
            try {
                this.dataSend = dataItem
                //Địa chỉ thuê bao
                this.dataSend.diachi_tb = dataItem.diachi_tb
                this.dataSend.khutb_id = dataItem.khutb_id
                this.dataSend.aptb_id = dataItem.aptb_id
                this.dataSend.photb_id = dataItem.photb_id
                this.dataSend.dacdiemtb_id = dataItem.dacdiemtb_id
                this.dataSend.phuongtb_id = dataItem.phuongtb_id
                this.dataSend.quantb_id = dataItem.quantb_id
                this.dataSend.tinhtb_id = dataItem.tinhtb_id
                this.dataSend.sonha_tb = dataItem.sonha_tb

                //Địa chỉ lắp đặt
                this.dataSend.diachi_ld = dataItem.diachi_ld
                this.dataSend.khuld_id = dataItem.khuld_id
                this.dataSend.apld_id = dataItem.apld_id
                this.dataSend.phold_id = dataItem.phold_id
                this.dataSend.dacdiemld_id = dataItem.dacdiemld_id
                this.dataSend.phuongld_id = dataItem.phuongld_id
                this.dataSend.quanld_id = dataItem.quanld_id
                this.dataSend.tinhld_id = dataItem.tinhld_id
                this.dataSend.sonha_ld = dataItem.sonha_ld
                this.dataSend.ngayyeucau = this.ngayYeuCau
                this.dataSend.hdtb_id = dataItem.id
                this.dataSend.dsDVKID = dataItem.dk_dvk_id
                this.dataSend.vetinh_id = dataItem.vetinh_id
                this.dataSend.mucuoctb_id = dataItem.mucuoctb_id
                this.dataSend.email = null
                this.dataSend.khuvuc = dataItem.ten_kv
                this.dataSend.khuvuc_id = dataItem.khuvuc_id
                if (this.hdkh_id != 0) this.dataSend.kt_hdkh_id = true//Nếu có hợp đồng khách hàng <-> đang lập hợp đồng ký lại
                //Lấy hình thức tt của mã tt
                let vma_tt = ""
                let httt_id = ""
                vma_tt = dataItem.matt
                for (let i = 0; i < this.dsThanhToan.length; i++) {
                    if (vma_tt == this.dsThanhToan[i].ma_tt)
                        httt_id = this.dsThanhToan[i].httt_id
                }
                if (httt_id == "") httt_id = Enum.HinhThucThanhToan.UYNHIEM_THU
                this.dataSend.hinhthuctt_id = httt_id
                if (this.dataSend.ngay_sn1)
                    this.dataSend.ngay_sn = this.dataSend.ngay_sn1 != null ? moment(this.dataSend.ngay_sn1, 'DD/MM/YYYY').toDate() : null
                else this.dataSend.ngay_sn = this.dataSend.ngay_sn != null ? moment(this.dataSend.ngay_sn).toDate() : null

                this.$bvModal.show('popupChuanHoaThueBao')
            } catch (ex) {
                console.log(ex)
                this.$toast.error("Có lỗi : " + ex)
            }
        },
        xacNhanChuanHoaThanhToan: async function (val) {
            let idx = this.dsThanhToan.findIndex(x => x.thanhtoan_id == val.thanhtoan_id)
            let dsTam = JSON.parse(JSON.stringify(this.dsThanhToan))
            dsTam[idx] = val
            this.dsThanhToan = dsTam
        },
        xacNhanChuanHoaThueBao: async function (val) {
            let idx = this.dsThueBao.findIndex(x => x.thuebao_id == val.thuebao_id)
            let dsTam = JSON.parse(JSON.stringify(this.dsThueBao))
            dsTam[idx] = val
            this.dsThueBao = dsTam
        },
        xacNhanCongTacVien: async function (val) {
            if (val) {
                this.congTacVien = val.ten_ht
                this.ctv_id = val.nhanvien_id
            }
            this.$bvModal.hide('popupCongTacVien')
        },

        xacNhanNguoiGioiThieu: async function (val) {
            if (val) {
                this.nguoiGioiThieu = val.ten_ht
                this.nguoigt_id = val.nhanvien_id
            }
            this.$bvModal.hide('popupNguoiGioiThieu')
        },

        onNganhNgheSelected(args) {
            if (args != null)
                this.nganhnghe_id = args.nganhnghe_id
            else this.nganhnghe_id = 0
        },

        diaChiKhachHangClick: function () {
            this.dataSend = {}
            this.dataSend.tinh_id = this.tinhkh_id
            this.dataSend.quan_id = this.quankh_id
            this.dataSend.phuong_id = this.phuongkh_id
            this.dataSend.pho_id = this.phokh_id
            this.dataSend.ap_id = this.apkh_id
            this.dataSend.khu_id = this.khukh_id
            this.dataSend.dacdiem_id = this.dacdiemkh_id
            this.dataSend.so_nha = this.sonhakh
            this.dataSend.diachi = this.diaChiKhachHang
            this.$bvModal.show('popupDiaChiKH')
        },

        diaChiNDDClick: function () {
            this.dataSend = {}
            this.dataSend.tinh_id = this.tinhNDD_id ? this.tinhNDD_id : this.tinhkh_id
            this.dataSend.quan_id = this.quanNDD_id ? this.quanNDD_id : this.quankh_id
            this.dataSend.phuong_id = this.phuongNDD_id ? this.phuongNDD_id : this.phuongkh_id
            this.dataSend.pho_id = this.phoNDD_id ? this.phoNDD_id : this.phokh_id
            this.dataSend.ap_id = this.apkh_id
            this.dataSend.khu_id = this.khukh_id
            this.dataSend.dacdiem_id = this.dacdiemkh_id
            this.dataSend.so_nha = this.sonhakh
            this.dataSend.diachi = this.diaChiNDD ? this.diaChiNDD : this.diaChiKhachHang
            this.$bvModal.show('popupDiaChiNDD')
        },

        hoKhauClick: function () {
            this.dataSend = {}
            this.dataSend.diachi = this.hoKhau
            this.$bvModal.show('popupDiaChiHK')
        },

        getDiaChiKH: async function (data) {
            this.tinhkh_id = data.tinh_id
            this.quankh_id = data.quan_id
            this.phuongkh_id = data.phuong_id
            this.phokh_id = data.pho_id
            this.apkh_id = data.ap_id
            this.khukh_id = data.khu_id
            this.dacdiemkh_id = data.dacdiem_id
            this.sonhakh = data.so_nha
            this.tinhNDD_id = data.tinh_id
            this.quanNDD_id = data.quan_id
            this.phuongNDD_id = data.phuong_id
            this.phoNDD_id = data.pho_id
            this.apNDD_id = data.ap_id
            this.khuNDD_id = data.khu_id
            this.dacdiemNDD_id = data.dacdiem_id
            this.sonhaNDD = data.so_nha
            this.diaChiKhachHang = data.diachimoi
            this.diaChiNDD = data.diachimoi
            this.hoKhau = data.diachimoi
            this.$refs['diaChiKhachHangInput'].focus();
        },

        getDiaChiNDD: async function (data) {
            this.tinhNDD_id = data.tinh_id
            this.quanNDD_id = data.quan_id
            this.phuongNDD_id = data.phuong_id
            this.phoNDD_id = data.pho_id
            this.apNDD_id = data.ap_id
            this.khuNDD_id = data.khu_id
            this.dacdiemNDD_id = data.dacdiem_id
            this.sonhaNDD = data.so_nha
            this.diaChiNDD = data.diachimoi
        },

        getDiaChiHK: async function (data) {
            this.hoKhau = data.diachimoi
        },

        getTNC(objTNC) {
            this.TNC1_ID = objTNC.TNC1_ID
            this.TNC2_ID = objTNC.TNC2_ID
            this.TNC3_ID = objTNC.TNC3_ID
            this.TNC4_ID = objTNC.TNC4_ID
            this.setNganhNgheCombobox(objTNC.nganhnghe_id)
        },

        acceptSearchContract: async function (item) {
            this.maGiaoDich = item.ma_gd
        },

        acceptSearchAccount: async function (item) {
            if (item != null) {
                this.maKhachHang = item.ma_kh
                this.$refs.maKHInput.focus()
            }
            this.$bvModal.hide('popupSearchAccount')
        },

        hienThiDanhSachDBTT: async function () {
            this.trangThaiDuLieu = "Trạng thái dữ liệu: Thông tin đang được lấy từ Danh bạ"
            let data = {
                khid: this.khachhang_id,
            }
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_dbtt_theo_khid', data)
            this.dsThanhToan = res != null && res.data != null ? res.data : []
            this.tongSoTT = this.dsThanhToan.length

            for (let i = 0; i < this.dsThanhToan.length; i++) {
                let thanhtoan_id = this.dsThanhToan[i].thanhtoan_id != null ? this.dsThanhToan[i].thanhtoan_id : 0

                let ds = await this.LAY_DS_DIACHI_THEO_DBTTID(thanhtoan_id)
                if (ds.length > 0) {
                    this.dsThanhToan[i].diachi_id = ds[0].diachi_id
                    this.dsThanhToan[i].dacdiem_id = ds[0].dacdiem_id
                    this.dsThanhToan[i].khu_id = ds[0].khu_id
                    this.dsThanhToan[i].ap_id = ds[0].ap_id
                    this.dsThanhToan[i].pho_id = ds[0].pho_id
                    this.dsThanhToan[i].phuong_id = ds[0].phuong_id
                    this.dsThanhToan[i].quan_id = ds[0].quan_id
                    this.dsThanhToan[i].tinh_id = ds[0].tinh_id
                    this.dsThanhToan[i].sonha = ds[0].sonha
                    this.dsThanhToan[i].diachibc_id = ds[0].diachibc_id
                    this.dsThanhToan[i].dacdiembc_id = ds[0].dacdiembc_id
                    this.dsThanhToan[i].khubc_id = ds[0].khubc_id
                    this.dsThanhToan[i].apbc_id = ds[0].apbc_id
                    this.dsThanhToan[i].phobc_id = ds[0].phobc_id
                    this.dsThanhToan[i].phuongbc_id = ds[0].phuongbc_id
                    this.dsThanhToan[i].quanbc_id = ds[0].quanbc_id
                    this.dsThanhToan[i].tinhbc_id = ds[0].tinhbc_id
                    this.dsThanhToan[i].sonha_bc = ds[0].sonha_bc
                }
            }
            this.dsThanhToan = [...this.dsThanhToan]
        },

        hienThiDanhSachDBTB: async function () {
            let data = {
                khachhang_id: this.khachhang_id,
            }
            let res = await this.$root.context.get('/web-hopdong/ChuanHoaDuLieu/lay_ds_danhba_tb_theo_khachhang_id', data)
            this.dsThueBao = res != null && res.data != null ? res.data : []
            this.tongSoTB = this.dsThueBao.length
            this.isDisableBtnXoaTB = true

            for (let i = 0; i < this.dsThueBao.length; i++) {
                let thuebao_id = this.dsThueBao[i].thuebao_id != null ? this.dsThueBao[i].thuebao_id : 0

                let ds = await this.LAY_DS_DIACHI_THEO_DBTBID(thuebao_id)
                if (ds.length > 0) {
                    this.dsThueBao[i].diachitb_id = ds[0].diachi_id
                    this.dsThueBao[i].dacdiemtb_id = ds[0].dacdiem_id
                    this.dsThueBao[i].khutb_id = ds[0].khu_id
                    this.dsThueBao[i].aptb_id = ds[0].ap_id
                    this.dsThueBao[i].photb_id = ds[0].pho_id
                    this.dsThueBao[i].phuongtb_id = ds[0].phuong_id
                    this.dsThueBao[i].quantb_id = ds[0].quan_id
                    this.dsThueBao[i].tinhtb_id = ds[0].tinh_id
                    this.dsThueBao[i].sonha_tb = ds[0].sonha
                    this.dsThueBao[i].diachild_id = ds[0].diachild_id
                    this.dsThueBao[i].dacdiemld_id = ds[0].dacdiemld_id
                    this.dsThueBao[i].khuld_id = ds[0].khuld_id
                    this.dsThueBao[i].apld_id = ds[0].apld_id
                    this.dsThueBao[i].phold_id = ds[0].phold_id
                    this.dsThueBao[i].phuongld_id = ds[0].phuongld_id
                    this.dsThueBao[i].quanld_id = ds[0].quanld_id
                    this.dsThueBao[i].tinhld_id = ds[0].tinhld_id
                    this.dsThueBao[i].sonha_ld = ds[0].sonha_ld
                }
            }

            this.dsThueBao = [...this.dsThueBao]
        },

        layDiaChiTheoHDKHID: async function (hdkhid) {
            let res = await this.$root.context.get(`/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_hdkhid?hdkhId=${hdkhid}`)
            return res.data
        },

        hienThiTTDiaChi: async function () {
            let res = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/lay-ds-diachi-theo-dbkhid?khachHangId=${this.khachhang_id}`)
            if (res.data != null && res.data.length > 0) {
                this.diachikh_id = res.data[0].diachi_id
                this.tinhkh_id = res.data[0].tinh_id != null ? res.data[0].tinh_id : null
                this.quankh_id = res.data[0].quan_id != null ? res.data[0].quan_id : null
                this.phuongkh_id = res.data[0].phuong_id != null ? res.data[0].phuong_id : null
                this.phokh_id = res.data[0].pho_id != null ? res.data[0].pho_id : null
                this.apkh_id = res.data[0].ap_id != null ? res.data[0].ap_id : null
                this.khukh_id = res.data[0].khu_id != null ? res.data[0].khu_id : null
                this.dacdiemkh_id = res.data[0].dacdiem_id != null ? res.data[0].dacdiem_id : null
                this.sonhakh = this.chuanHoaTen(res.data[0].sonha)
            }
        },

        maKhachHangKeypress: async function (e) {
            this.maKhachHang = this.maKhachHang.trim()
            await this.hienThiTT_DanhBa_KH(this.maKhachHang)
        },

        hienThiTT_DanhBa_KH: async function (maThueBao) {
            try {
                this.$root.showLoading(true)
                let data = {
                    maThueBao: maThueBao,
                    maGiaoDich: "0",
                    dichVuVTId: this.dichvuvt_id,
                    loaiHDId: Enum.LoaiHopDong.KY_LAI_HD_GOC,
                }
                let res = await this.$root.context.get('/web-hopdong/ChuanHoaDuLieu/lay_danhba_theo_mathuebao', data)
                if (res.data.kiemTra.length > 0)
                    return this.$toast.error("Số máy/Acc: " + data.maThueBao + " đang được lập bởi hợp đồng (phụ lục) " + res.data.kiemTra[0].ten_loaihd + " có mã giao dịch " + res.data.kiemTra[0].ma_gd + " do User " + res.data.kiemTra[0].nguoi_cn + " thuộc đơn vị " + res.data.kiemTra[0].ten_dv + " thực hiện vào ngày " + res.data.kiemTra[0].ngay_yc)

                if (res.data.danhBa.length > 0) {
                    let danhBa = res.data.danhBa[0]
                    this.setButton(1)
                    //Thông tin khách hàng
                    this.khachhang_id = danhBa.khachhang_id
                    this.maKhachHang = danhBa.ma_kh
                    this.tenKhachHang = danhBa.ten_kh
                    this.diaChiKhachHang = this.chuanHoaTen(danhBa.diachi_kh)
                    this.hoKhau = this.chuanHoaTen(danhBa.hokhau)
                    this.noiCapHK = this.chuanHoaTen(danhBa.noicap_hk)
                    this.maHopDong = danhBa.ma_hd
                    this.dacTrung = danhBa.dactrung
                    this.fax = danhBa.so_fax
                    this.dienThoai = danhBa.so_dt
                    if (danhBa.loaikh_id != "") {
                        this.selectCboRow(danhBa.loaikh_id);
                        this.PHANLOAIKH_ID = danhBa.phanloaikh_id
                    }

                    if (this.kieukh_canhan && (danhBa.nguoi_dd == null || danhBa.nguoi_dd.trim() == ""))
                        this.nguoiDaiDien = this.tenKhachHang
                    else
                        this.nguoiDaiDien = this.chuanHoaTen(danhBa.nguoi_dd)
                    this.maSoThue = danhBa.mst
                    this.taiKhoan = danhBa.stk ? danhBa.stk : null
                    this.ngayHetHan = danhBa.ngayhethan_gt !== null ? moment(danhBa.ngayhethan_gt, 'DD/MM/YYYY').toDate() : null
                    if (danhBa.diachi_ndd == null || danhBa.diachi_ndd.trim() == "")
                        this.diaChiNDD = this.diaChiKhachHang
                    else
                        this.diaChiNDD = danhBa.diachi_ndd
                    this.ghiChu = danhBa.ghichu
                    //Phần thanh toán
                    await this.hienThiDanhSachDBTT()

                    if (this.kieukh_canhan) {
                        if (danhBa.gioitinh != null)
                            this.gioiTinh = danhBa.gioitinh
                        else
                            this.gioiTinh = 0
                    }
                    else
                        this.gioiTinh = null
                    if (danhBa.quoctich_id != null) {
                        this.isCheckQT = true
                        this.quocTichId = danhBa.quoctich_id
                    } else {
                        this.isCheckQT = false
                        this.quocTichId = null
                    }
                    if (danhBa.dantoc_id != null) {
                        this.isCheckDT = true
                        this.danTocId = danhBa.dantoc_id
                    } else {
                        this.isCheckDT = false
                        this.danTocId = null
                    }
                    if (danhBa.nganhnghe_id != null)
                        this.setNganhNgheCombobox(danhBa.nganhnghe_id)
                    else
                        this.setNganhNgheCombobox(this.dsNganhNghe[0].nganhnghe_id)
                    if (danhBa.tnc1_id != null)
                        this.TNC1_ID = danhBa.tnc1_id
                    if (danhBa.tnc2_id != null)
                        this.TNC2_ID = danhBa.tnc2_id
                    if (danhBa.tnc3_id != null)
                        this.TNC3_ID = danhBa.tnc3_id
                    if (danhBa.tnc4_id != null)
                        this.TNC4_ID = danhBa.tnc4_id
                    if (danhBa.nganhang_id != null) {
                        this.isCheckNH = true
                        this.nganHangId = danhBa.nganhang_id
                    } else {
                        this.isCheckNH = false
                        this.nganHangId = null
                    }
                    //lấy thông tin địa chỉ
                    await this.hienThiTTDiaChi()
                    //kiểm tra KH đã kí HĐ chưa
                    if (Number(danhBa.ky_hd) === 1) {
                        this.kt_kylai = true//Đánh dấu đã ký lại hợp đồng gốc
                        this.kylai = true
                        this.$toast.error("Khách hàng này đã ký lại hợp đồng")
                    }
                    //Nếu khách hàng ký lại rồi thì ko sinh mã hợp đồng nữa & ngày lập hợp đồng giữ nguyên ko thay đổi

                    if (danhBa.ngaylap_hd)
                        this.ngayLapHD = moment(danhBa.ngaylap_hd, 'DD/MM/YYYY').toDate()
                    else
                        this.ngayLapHD = new Date()
                    if (danhBa.ngay_hk)
                        this.ngayCapHK = moment(danhBa.ngay_hk, 'DD/MM/YYYY').toDate()
                    else
                        this.ngayCapHK = null
                    if (danhBa.ngay_sn)
                        this.ngaySinh = moment(danhBa.ngay_sn, 'DD/MM/YYYY').toDate()
                    else
                        this.ngaySinh = null

                    if (danhBa.loaigt_id)
                        this.loaiGiayToId = danhBa.loaigt_id
                    if (danhBa.so_gt)
                        this.soGiayTo = danhBa.so_gt
                    else
                        this.soGiayTo = null
                    if (danhBa.ngaycap)
                        this.ngayCapGT = moment(danhBa.ngaycap, 'DD/MM/YYYY').toDate()
                    else
                        this.ngayCapGT = null
                    this.noiCapGT = this.chuanHoaTen(danhBa.noicap)
                    this.chucVu = this.chuanHoaTen(danhBa.chucdanh)

                    //Hiển thị thông tin thuê bao
                    await this.hienThiDanhSachDBTB()

                    this.trangthaitb_id = 1;
                    if (danhBa.trangthaitb_id != null && danhBa.trangthaitb_id != 1)
                        this.trangthaitb_id = danhBa.trangthaitb_id
                    if (this.trangthaitb_id != Enum.TrangThaiTB.THUONG) {
                        if (this.trangthaitb_id == Enum.TrangThaiTB.DOISO) {
                            this.$toast.error("Thuê bao đang trong tình trạng Đổi Số");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.KHOA_HAICHIEU_CUONGBUC) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Khóa 2 chiều do nợ cước");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.KHOA_HAICHIEU_YEUCAU) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Khóa 2 chiều do yêu cầu");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.KHOA_MOTCHIEU_CUONGBUC) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Khóa 1 chiều do nợ cước");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.KHOA_MOTCHIEU_YEUCAU) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Khóa 1 chiều do yêu cầu");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.TAMDUNG) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Tạm dừng");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.THANHLY) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Thanh lý");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.THANHLY_NO) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Thanh lý nợ");
                        }
                        if (this.trangthaitb_id == Enum.TrangThaiTB.MO) {
                            this.$toast.error("Thuê bao đang trong tình trạng : Mở");
                        }
                    }
                }
                else {
                    let dichVuVT = this.dsDichVuVT.find(x => x.dichvuvt_id === Number(this.dichvuvt_id)).ten_dvvt;
                    this.$toast.error(`Không tìm thấy thông tin về mã thuê bao: ${this.maThueBao} đối với dịch vụ: ${dichVuVT}`);
                    this.setButton(0);
                }
            }
            catch (e) {
                console.log(e)
            } finally {
                this.$root.showLoading(false)
            }
        },

        kiemtraDL_DVQL: async function () {
            let donviql_TT = ""
            let donviql_TB = ""
            for (let i = 0; i < dsThanhToan.length; i++) {
                donviql_TT = dsThanhToan[i].donvi_id
                for (let j = 0; j < dsThueBao.length; j++) {
                    donviql_TB = dsThueBao[j].donvi_id_tb
                    if (donviql_TT.trim() != donviql_TB.trim()) {
                        this.$toast.error("Dữ liệu về đơn vị quản lý không thống nhất. Bạn không thể cập nhật hợp đồng")
                        return false
                    }
                }
            }
            return true
        },

        capNhat: async function () {
            try {
                this.$root.showLoading(true)
                this.tenKhachHang = this.chuanHoaTen(this.tenKhachHang)
                this.ghiChu = this.chuanHoaTen(this.ghiChu)
                this.chucVu = this.chuanHoaTen(this.chucVu)
                this.nguoiDaiDien = this.chuanHoaTen(this.nguoiDaiDien)
                this.hoKhau = this.chuanHoaTen(this.hoKhau)

                if (!await this.kiemTraDL_KhachHang()) return
                if (!await this.kiemTraDL_ThanhToan()) return
                if (!await this.kiemTraDL_Thuebao()) return
                // //if (!KiemtraDL_DVQL()) return; //Tạm thời chưa kiểm tra vì đơn vị qly thanh toán & dvi lắp đặt có thể khác nhau (hd_thanhtoan & hd_thuebao)
                if (this.isDisableBtnNhapMoi == true) {
                    //Nếu chưa lập hợp đồng ký lại, chọn lại mã nhân viên thu cước để chuẩn hóa dữ liệu -
                    for (let i = 0; i < this.dsThueBao.length; i++) {
                        let kieuld_id = 0
                        let dataInput = {
                            loaiTBId: this.dsThueBao[i].loaitb_id,
                            loaiHDId: Enum.LoaiHopDong.KY_LAI_HD_GOC
                        }
                        let res = await this.$root.context.get('/web-hopdong/ChuanHoaDuLieu/lay_kieulapdat_huonggiao', dataInput)
                        kieuld_id = res.data.kieuld_id
                        if (kieuld_id == 0)
                            return this.$toast.error("Không tìm được kiểu lắp đặt của thuê bao " + this.dsThueBao[i].ma_tb)

                        this.quytrinh_id = res.data.quytrinh_id
                        if (this.quytrinh_id == 0 || this.quytrinh_id == null)
                            return this.$toast.error("Nghiệp vụ thuê bao " + this.dsThueBao[i].ma_tb + " chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")

                        this.dsThueBao[i].kieuld_id = kieuld_id
                        this.dsThueBao[i].quytrinh_id = this.quytrinh_id
                    }

                    await this.TaoDuLieu_HDKH(true)
                    await this.TaoDuLieu_HDTT(true)
                    console.log("bbb")
                    await this.TaoDuLieu_HDTB(true)
                    console.log("this.kiemtra: " + this.kiemtra)
                    if (this.kiemtra == false) {
                        this.kiemtra = true
                        return
                    }

                    await this.TaoDuLieu_DiaChiKH(true)
                    await this.CapNhat_HDTB_ID_HDTB_KV()
                    if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC('0')) return
                    if (await this.THEMMOI_HOPDONG_V2())
                        this.$toast.success("Cập nhật dữ liệu thành công!")
                    else
                        return this.$toast.error("Cập nhật dữ liệu thất bại!")

                    if (this.phantap_kh)
                        await this.THEMMOI_HDTT_NV()

                    await this.CapNhat_DiaChiBC()
                } else {
                    // kiểm tra xem thuê bao kí lại có thuộc phụ lục nào khác đang lập không
                    if (await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.maGiaoDich)) return
                    //
                    let loaiKH_old = this.dsLoaiKhacHang.find(e => Number(e.loaikh_id) === Number(this.ds_HDKH[0].loaikh_id));
                    let loaiKH_New = this.dsLoaiKhacHang.find(e => Number(e.loaikh_id) === Number(this.loaiKhachHangId));
                    if (loaiKH_old != null && loaiKH_New != null) {
                        if (loaiKH_old.loai !== loaiKH_New.loai)
                            return this.$toast.error(`Chức năng này không được thay đổi loại khách hàng Doanh nghiệp <-> Cá nhân. Để thay đổi hãy vào chức năng Chuyển quyền!`);
                    }

                    await this.TaoDuLieu_HDKH(false)
                    await this.TaoDuLieu_HDTT(false)
                    await this.TaoDuLieu_HDTB(false)
                    await this.TaoDuLieu_DiaChiKH(false)
                    await this.CapNhat_HDTB_ID_HDTB_KV()
                    if (await this.CAPNHAT_HOPDONG_V2())
                        this.$toast.success("Cập nhật dữ liệu thành công!")
                    else
                        return this.$toast.error("Cập nhật dữ liệu thất bại!")

                    if (this.phantap_kh)
                        await this.THEMMOI_HDTT_NV()

                    await this.CapNhat_DiaChiBC()
                }

                let data = {
                    maGiaoDich: this.maGiaoDich,
                    loaiHdId: Enum.LoaiHopDong.KY_LAI_HD_GOC
                }
                let res = await this.$root.context.post('/web-hopdong/ThayDoiHopDong/lay_ds_hopdong_theo_magiaodich_post', data)
                this.ds_HDKH = res.data
                await this.HienThiTTHopDongKH()

                if (this.ds_HDKH.length <= 0) {
                    //Trường hợp this.ds_HDKH.length > 0 thì đã gọi 2 hàm HienThiDanhSacHDTT(), HienThiDanhSacHDTB() bên trong hàm HienThiTTHopDongKH()
                    await this.HienThiDanhSacHDTT()
                    await this.HienThiDanhSacHDTB()
                }
                this.setButton(3)

            } catch (exp) {
                this.$toast.error("Có lỗi cập nhật thông tin: " + exp)
            } finally {
                this.$root.showLoading(false)
            }
        },

        CAPNHAT_HOPDONG_V2: async function () {
            let data = {
                dsHDKH: this.dsHDKH,
                dsHDTT: this.dsHDTT,
                dsHDTB: this.dsHDTB
            }
            let res = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/capnhat_hopdong', data)
            if (res.error_code !== 'BSS-00000000') return false

            data = {
                dsDiaChi: this.dsDiaChi,
                dsDiaChi_HDTT: this.dsDiaChi_HDTT,
                dsDiaChi_HDTB: this.dsDiaChi_HDTB
            }
            let res1 = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/capnhat_diachi', data)
            return res1.error_code === 'BSS-00000000'
        },

        CapNhat_HDTB_ID_HDTB_KV: async function () {
            for (let i = 0; i < this.dsThueBao.length; i++) {
                this.dsThueBao[i].HDTB_ID = this.hdtb_id
            }
        },

        KIEMTRA_MA_TB_LAP_HOPDONG_KHAC: async function (maGD) {
            let apiBody = {
                ma_tb: this.maThueBao,
                ma_gd: maGD,
                dichvuvt_id: this.dichvuvt_id,
                loaihd_id: Enum.LoaiHopDong.KY_LAI_HD_GOC
            }
            let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', apiBody)
            if (res.data != null && res.data.length > 0) {
                this.$toast.error(`Số máy/Acc: ${this.maThueBao} đang được lập bởi hợp đồng (phụ lục) ${res.data[0].ten_loaihd} có mã giao dịch ${res.data[0].ma_gd} do User ${res.data[0].nguoi_cn} thuộc đơn vị ${res.data[0].ten_dv} thực hiện vào ngày ${res.data[0].ngay_yc}`)
                return true
            } else
                return false
        },

        THEMMOI_HOPDONG_V2: async function () {
            let data = {
                dsHDKH: this.dsHDKH,
                dsHDTT: this.dsHDTT,
                dsHDTB: this.dsHDTB
            }
            let res = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/themmoi_hopdong', data)
            if (res.error_code !== 'BSS-00000000') return false

            data = {
                dsDiaChi: this.dsDiaChi,
                dsDiaChi_HDTT: this.dsDiaChi_HDTT,
                dsDiaChi_HDTB: this.dsDiaChi_HDTB
            }
            let res1 = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/themmoi_diachi', data)
            if (res1.error_code !== 'BSS-00000000') return false

            data = {
                dsDiaChiHDKH: this.dsDiaChiHDKH,
                dsDiaChiHDTT: this.dsDiaChiHDTT,
                dsDiaChiHDTB: this.dsDiaChiHDTB
            }
            let res2 = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/themmoi_diachi_khtbtt', data)
            return res2.error_code === 'BSS-00000000'
        },

        THEMMOI_HDTT_NV: async function () {
            let data = {
                dsHDTT_NV: this.dsHDTT_NV
            }
            let res = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/themmoi_hdtt_nv', data)
            if (res.error_code == 'BSS-00000000') {
                return true
            } else {
                return false
            }
        },

        CapNhat_DiaChiBC: async function () {
            for (let i = 0; i < this.dsThanhToan.length; i++) {
                let data = data = {
                    diachibc_id: this.dsThanhToan[i].diachibc_id != null ? this.dsThanhToan[i].diachibc_id : 0,
                    hdtt_id: this.hdtt_id,
                    pho_id: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].phobc_id != null ? this.dsThanhToan[i].phobc_id : 0,
                    phuong_id: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].phuongbc_id != null ? this.dsThanhToan[i].phuongbc_id : 0,
                    quan_id: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].quanbc_id != null ? this.dsThanhToan[i].quanbc_id : 0,
                    sonha: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].sonha_bc != null ? this.dsThanhToan[i].sonha_bc : '',
                    diachi: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].diachi_bc != null ? this.dsThanhToan[i].diachi_bc : '',
                    tinh_id: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].tinhbc_id != null ? this.dsThanhToan[i].tinhbc_id : 0,
                    ap_id: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].apbc_id != null ? this.dsThanhToan[i].apbc_id : 0,
                    khu_id: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].khubc_id != null ? this.dsThanhToan[i].khubc_id : 0,
                    dacdiem_id: this.dsThanhToan[i].diachi_bc !== '' && this.dsThanhToan[i].dacdiembc_id != null ? this.dsThanhToan[i].dacdiembc_id : 0
                }
                let res = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_diachi_bc_hdtt', data);
            }
        },

        kiemTraNgaySinh: async function () {
            let today = new Date()
            let birthDate = new Date(this.ngaySinh)
            let age = today.getFullYear() - birthDate.getFullYear()
            let m = today.getMonth() - birthDate.getMonth()
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--
            }
            return age
        },

        kiemTraDL_KhachHang: async function () {
            this.$v.$touch()
            let errorMessage = []
            let focusInput = []
            if (this.dsThanhToan.length <= 0)
                errorMessage.push("Không có dữ liệu thuê bao để thực hiện ký lại hợp đồng!")
            if (this.kylai) {
                errorMessage.push("Khách hàng này đã ký lại hợp đồng!")
                this.customError.maKH = true
            }
            // if (!this.$v.maGiaoDich.required) {
            //     errorMessage.push("Trường mã giao dịch bắt buộc nhập dữ liệu!")
            //     focusInput.push('maGiaoDichInput')
            // }
            if (this.maGiaoDich && this.maGiaoDich != "") {
                let input = {
                    kieu: 3,
                    ma_kh: this.maGiaoDich,
                    vloaihd_id: Enum.LoaiHopDong.KY_LAI_HD_GOC
                }
                let res = await this.$root.context.get('/web-hopdong/chuyendich/kiemtra_makh_mtt', input)
                let kq = res.data[0].rs
                if (kq == 0) {
                    errorMessage.push("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh!");
                    this.customError.maGD = true;
                    focusInput.push('maGiaoDichInput')
                }
            }
            //Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
            if (this.maKhachHang && this.maKhachHang != "") {
                let input = {
                    kieu: 1,
                    ma_kh: this.maKhachHang,
                    vloaihd_id: Enum.LoaiHopDong.KY_LAI_HD_GOC
                }
                let res = await this.$root.context.get('/web-hopdong/chuyendich/kiemtra_makh_mtt', input)
                let kq = res.data[0].rs
                if (kq == 0) {
                    errorMessage.push("Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh!");
                    this.customError.maKH = true;
                    focusInput.push('maKHInput')
                }
            }
            if (!this.$v.maHopDong.required) {
                errorMessage.push("Trường mã hợp đồng bắt buộc nhập dữ liệu!")
                focusInput.push('maHopDongInput')
            }
            if (!this.$v.maKhachHang.required) {
                errorMessage.push("Trường mã khách hàng bắt buộc nhập dữ liệu!")
                focusInput.push('maKHInput')
            }
            if (!this.$v.tenKhachHang.required) {
                errorMessage.push("Trường tên khách hàng bắt buộc nhập dữ liệu!")
                focusInput.push('tenKhachHangInput')
            }

            if (this.diaChiKhachHang == "" || this.diaChiKhachHang == null || this.tinhkh_id == 0 || this.quankh_id == 0 || this.phuongkh_id == 0) {
                errorMessage.push("Trường địa chỉ khách hàng chưa được chuẩn hóa!")
                this.customError.diaChiKhachHang = true
                focusInput.push('diaChiKhachHangInput')
            }
            if (this.soGiayTo == "" || this.soGiayTo == null) {
                errorMessage.push("Trường số giấy tờ bắt buộc nhập dữ liệu!")
                focusInput.push('soGiayToInput')
            }

            if (this.dienThoai == "" || this.dienThoai == null) {
                errorMessage.push("Trường số điện thoại liên hệ của khách hàng bắt buộc nhập dữ liệu!")
                focusInput.push('soDTInput')
            }

            this.dienThoai = this.dienThoai ? this.dienThoai.trim() : this.dienThoai;
            if (isNaN(this.dienThoai)) {
                errorMessage.push("Số điện thoại liên hệ của khách hàng phải là kiểu số!")
                this.customError.dienThoai = true
                focusInput.push('soDTInput')
            }

            if (this.nguoiDaiDien == "" || this.nguoiDaiDien == null) {
                errorMessage.push("Trường tên người đại diện bắt buộc nhập dữ liệu!")
                focusInput.push('nguoiDDInput')
            }
            if (this.ngayCapGT == null) {
                errorMessage.push("Trường ngày cấp giấy tờ bắt buộc nhập dữ liệu!")
            }
            if (this.maKhachHang == "" || this.maKhachHang == null) {
                errorMessage.push("Kiểm tra lại mã khách hàng")
            }

            if ((this.isCheckQT == true && this.quocTichId == null) || this.isCheckQT == false) {
                errorMessage.push("Chưa chọn Quốc tịch cho khách hàng!")
                this.customError.quocTich = true
            }
            if ((this.isCheckDT == true && this.danTocId == null) || this.isCheckDT == false) {
                errorMessage.push("Chưa chọn dữ liệu Dân tộc!")
                this.customError.danToc = true
            }
            if (this.ngaySinh != null) {
                if (this.kieukh_canhan)//Nếu là khách hàng cá nhân thì kiểm tra đã đủ 18 tuổi chưa
                {
                    let age = await this.kiemTraNgaySinh()
                    if (age < 18) {
                        errorMessage.push("Khách hàng chưa đủ 18 tuổi để ký lại hợp đồng")
                        this.customError.ngaySinh = true
                    }
                }
            }
            else {
                errorMessage.push("Trường ngày sinh của khách hàng bắt buộc nhập dữ liệu!")
            }
            if (this.isCheckNH == true && this.nganHangId == "") {
                errorMessage.push("Chưa chọn ngân hàng")
            }

            if (this.nganhnghe_id == 0) {
                errorMessage.push("Chưa chọn ngành nghề")
            }

            if (this.TNC1_ID == 0 || this.TNC2_ID == 0 || this.TNC3_ID == 0) {
                errorMessage.push("Chọn chi tiết ngành nghề!")
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                if (focusInput.length > 0)
                    this.$refs[focusInput[0]].focus();
                return false
            } else {
                return true
            }
        },

        kiemTraDL_ThanhToan: async function () {
            let errorMessage = []
            for (let i = 0; i < this.dsThanhToan.length; i++) {
                if (this.dsThanhToan.ten_tt) {
                    errorMessage.push("Bạn chưa nhập tên thanh toán cho mã thanh toán '" + this.dsThanhToan[i].ma_tt)
                }
                if (this.dsThanhToan[i].diachi_tt == "" || this.dsThanhToan[i].quan_id == "" || this.dsThanhToan[i].phuong_id == "" ||
                    this.dsThanhToan[i].quan_id == "0" || this.dsThanhToan[i].phuong_id == "0") {
                    errorMessage.push("Bạn chưa chuẩn hóa thông tin địa chỉ thanh toán cho mã thanh toán '" + this.dsThanhToan[i].ma_tt)
                }
                if (this.dsThanhToan[i].diachi_ct == "") {
                    errorMessage.push("Bạn chưa chuẩn hóa thông tin địa chỉ chứng từ cho mã thanh toán '" + this.dsThanhToan[i].ma_tt)
                }

                if (this.dsThanhToan[i].tuyenthu_id == "") {
                    errorMessage.push("Bạn chưa nhập tuyến thu cho mã thanh toán '" + this.dsThanhToan[i].ma_tt)
                }
                if (this.dsThanhToan[i].matb_dd == "") {
                    errorMessage.push("Bạn chưa nhập mã thuê bao đại diện cho mã thanh toán '" + this.dsThanhToan[i].ma_tt)
                }
            }
            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else {
                return true
            }
        },

        kiemTraDL_Thuebao: async function () {
            let errorMessage = []
            for (let i = 0; i < this.dsThueBao.length; i++) {
                if (this.dsThueBao[i].ten_tb == "") {
                    errorMessage.push("Bạn chưa nhập tên thuê bao cho mã thuê bao '" + this.dsThueBao[i].ma_tb + "'");
                }
                if (this.dsThueBao[i].diachi_ld == "" || this.dsThueBao[i].quanld_id == "" || this.dsThueBao[i].phuongld_id == "") {
                    errorMessage.push("Bạn chưa chuẩn hóa thông tin địa chỉ lắp đặt cho mã thuê bao '" + this.dsThueBao[i].ma_tb + "'");
                }
                if (this.dsThueBao[i].quanld_id == "0" || this.dsThueBao[i].phuongld_id == "0") {
                    errorMessage.push("Bạn chưa chuẩn hóa thông tin địa chỉ lắp đặt cho mã thuê bao '" + this.dsThueBao[i].ma_tb + "'");
                }
                if (this.dsThueBao[i].diachi_tb == "" || this.dsThueBao[i].quantb_id == "" || this.dsThueBao[i].phuongtb_id == "") {
                    errorMessage.push("Bạn chưa chuẩn hóa thông tin địa chỉ thuê bao cho mã thuê bao '" + this.dsThueBao[i].ma_tb + "'");
                }
                if (this.dsThueBao[i].quantb_id == "0" || this.dsThueBao[i].phuongtb_id == "0") {
                    errorMessage.push("Bạn chưa chuẩn hóa thông tin địa chỉ thuê bao cho mã thuê bao '" + this.dsThueBao[i].ma_tb + "'");
                }
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else {
                return true
            }
        },

        getKey: async function (keyname) {
            let id = 0
            let input = {
                keyname: keyname
            }
            let res = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', input)
            if (res.data.length > 0) {
                id = res.data[0].id
            }
            return id
        },

        TaoDuLieu_HDKH: async function (themmoi) {
            this.dsHDKH = []
            let rowHDKH = {}
            rowHDKH.chucdanh_dd = this.chucVu.trim()
            if (themmoi == true) {
                let input = {
                    loaihd_id: Enum.LoaiHopDong.KY_LAI_HD_GOC
                }
                let res = await this.$root.context.get('/web-hopdong/chuyendich/sinh_magd', input)
                this.maGiaoDich = res.data
                this.hdkh_id = await this.getKey('HD_KHACHHANG')
                rowHDKH.hdkh_id = this.hdkh_id
                rowHDKH.ma_gd = this.maGiaoDich
                rowHDKH.ma_hd = this.maHopDong
            } else {
                rowHDKH.hdkh_id = this.hdkh_id
                rowHDKH.ma_gd = this.maGiaoDich
                rowHDKH.ma_hd = this.maHopDong
            }
            rowHDKH.ma_kh = this.maKhachHang

            if (this.khachhang_id > 0) {
                rowHDKH.khachhang_id = this.khachhang_id
                rowHDKH.bosungtb_id = 1
            }
            else
                rowHDKH.bosungtb_id = 0

            rowHDKH.ten_kh = this.tenKhachHang
            rowHDKH.diachi_kh = this.diaChiKhachHang
            rowHDKH.nguoi_dd = this.nguoiDaiDien
            rowHDKH.so_dt = this.dienThoai
            rowHDKH.so_fax = this.fax
            rowHDKH.gioitinh = this.gioiTinh
            rowHDKH.nganhnghe_id = this.nganhnghe_id

            if (this.isCheckQT)
                rowHDKH.quoctich_id = this.quocTichId
            if (this.isCheckDT)
                rowHDKH.dantoc_id = this.danTocId
            if (this.isCheckNH)
                rowHDKH.nganhang_id = this.nganHangId

            rowHDKH.ngay_yc = this.ngayYeuCau != null ? moment(this.ngayYeuCau).format('DD/MM/YYYY') : null
            rowHDKH.diachi_ndd = this.diaChiNDD

            if (this.ngaySinh)
                rowHDKH.ngay_sn = moment(this.ngaySinh).format('DD/MM/YYYY')

            rowHDKH.ngaylap_hd = this.ngayLapHD != null ? moment(this.ngayLapHD).format('DD/MM/YYYY') : null
            rowHDKH.loaigt_id = this.loaiGiayToId
            rowHDKH.so_gt = this.soGiayTo
            if (this.ngayCapGT)
                rowHDKH.ngaycap = moment(this.ngayCapGT).format('DD/MM/YYYY')
            rowHDKH.noicap = this.noiCapGT
            rowHDKH.so_fax = this.fax
            // rowHDKH.khlon_id = this.khachHangLonId
            rowHDKH.khlon_id = null
            rowHDKH.loaihd_id = Enum.LoaiHopDong.KY_LAI_HD_GOC
            rowHDKH.hokhau = this.hoKhau
            rowHDKH.noicap_hk = this.noiCapHK

            if (this.ngayCapHK && moment(this.ngayCapHK).isValid()) {
                rowHDKH.ngay_hk = moment(this.ngayCapHK).format('DD/MM/YYYY')
            }

            rowHDKH.loaikh_id = this.loaiKhachHangId

            if (this.PHANLOAIKH_ID != -1 && this.PHANLOAIKH_ID != 0)
                rowHDKH.phanloaikh_id = this.PHANLOAIKH_ID

            rowHDKH.mst = this.maSoThue ? this.maSoThue.trim() : this.maSoThue
            rowHDKH.stk = this.taiKhoan ? this.taiKhoan.trim() : this.taiKhoan
            if (this.isCheckCTV && this.congTacVien)
                rowHDKH.ctv_id = this.ctv_id
            if (this.isCheckNGT && this.nguoiGioiThieu)
                rowHDKH.nhanviengt_id = this.nguoigt_id
            rowHDKH.ghichu = this.ghiChu
            rowHDKH.dactrung = this.dacTrung
            if (this.ngayHetHan)
                rowHDKH.ngayhethan = moment(this.ngayHetHan).format('DD/MM/YYYY')

            rowHDKH.tnc1_id = this.TNC1_ID
            rowHDKH.tnc2_id = this.TNC2_ID
            rowHDKH.tnc3_id = this.TNC3_ID
            rowHDKH.tnc4_id = this.TNC4_ID
            this.dsHDKH.push(rowHDKH)
        },

        TaoDuLieu_HDTT: async function (themmoi) {
            this.dsHDTT = []
            let rowHDTT = {}
            this.dsDiaChi_HDTT = []
            let rowDiaChi = {}
            this.dsDiaChiHDTT = []
            let rowDiaChiHDTT = {}
            this.dsHDTT_NV = []
            let row_hdtt_nv = {}
            let k_phuong_id = 0
            let k_pho_id = 0
            let k_ap_id = 0
            let k_khu_id = 0
            let k_dacdiem_id = 0

            for (let i = 0; i < this.dsThanhToan.length; i++) {
                rowHDTT = {}
                rowDiaChi = {}
                rowDiaChiHDTT = {}
                row_hdtt_nv = {}
                if (themmoi)
                    this.hdtt_id = await this.getKey('HD_THANHTOAN')
                else
                    this.hdtt_id = this.dsThanhToan[i].hdtt_id
                rowHDTT.hdtt_id = this.hdtt_id
                rowHDTT.hdkh_id = this.hdkh_id
                rowHDTT.ma_tt = this.dsThanhToan[i].ma_tt
                if (this.dsThanhToan[i].thanhtoan_id)
                    rowHDTT.thanhtoan_id = this.dsThanhToan[i].thanhtoan_id
                rowHDTT.ten_tt = this.dsThanhToan[i].ten_tt
                rowHDTT.diachi_tt = this.dsThanhToan[i].diachi_tt
                rowHDTT.diachi_ct = this.dsThanhToan[i].diachi_ct
                rowHDTT.diachi_bc = this.dsThanhToan[i].diachi_bc
                rowHDTT.matb_dd = this.dsThanhToan[i].matb_dd
                rowHDTT.mst = this.dsThanhToan[i].mst
                if (this.dsThanhToan[i].httt_id != null && this.dsThanhToan[i].httt_id.toString() != "") {
                    rowHDTT.httt_id = this.dsThanhToan[i].httt_id
                }
                if (this.dsThanhToan[i].donvi_id != null && this.dsThanhToan[i].donvi_id.toString() != "") {
                    rowHDTT.donvi_id = this.dsThanhToan[i].donvi_id
                }
                rowHDTT.stk = this.dsThanhToan[i].stk
                rowHDTT.chu_tk = this.dsThanhToan[i].chu_tk

                if (this.dsThanhToan[i].nganhang_id != null && this.dsThanhToan[i].nganhang_id.toString() != "") {
                    rowHDTT.nganhang_id = this.dsThanhToan[i].nganhang_id
                }
                rowHDTT.ghichu = this.dsThanhToan[i].ghichu
                rowHDTT.tuyenthu_id = this.dsThanhToan[i].tuyenthu_id

                //Một số thông tin liên quan đến kho bạc
                rowHDTT.dv_qhns = this.dsThanhToan[i].dv_qhns
                rowHDTT.machuong = this.dsThanhToan[i].machuong
                rowHDTT.mn_kt = this.dsThanhToan[i].mn_kt
                rowHDTT.ma_ndkt = this.dsThanhToan[i].ma_ndkt
                rowHDTT.mn_nsnn = this.dsThanhToan[i].mn_nsnn
                rowHDTT.tien_dm = this.dsThanhToan[i].tien_dm

                // - Tạo dữ liệu địa chỉ thanh toán --21/07/2010
                rowDiaChi.diachi_id = themmoi ? await this.getKey('DIACHI') : this.dsThanhToan[i].diachi_id

                rowDiaChi.quan_id = this.dsThanhToan[i].quan_id
                rowDiaChi.phuong_id = this.dsThanhToan[i].phuong_id
                k_phuong_id = this.dsThanhToan[i].phuong_id

                if (this.dsThanhToan[i].tinh_id)
                    rowDiaChi.tinh_id = this.dsThanhToan[i].tinh_id

                if (this.dsThanhToan[i].pho_id) {
                    rowDiaChi.pho_id = this.dsThanhToan[i].pho_id
                    k_pho_id = this.dsThanhToan[i].pho_id
                } else {
                    rowDiaChi.pho_id = 0
                    k_pho_id = 0
                }

                if (this.dsThanhToan[i].ap_id) {
                    rowDiaChi.ap_id = this.dsThanhToan[i].ap_id
                    k_ap_id = this.dsThanhToan[i].ap_id
                } else {
                    rowDiaChi.ap_id = 0
                    k_ap_id = 0
                }

                if (this.dsThanhToan[i].khu_id) {
                    rowDiaChi.khu_id = this.dsThanhToan[i].khu_id
                    k_khu_id = this.dsThanhToan[i].khu_id
                } else {
                    rowDiaChi.khu_id = 0
                    k_khu_id = 0
                }

                if (this.dsThanhToan[i].dacdiem_id) {
                    rowDiaChi.dacdiem_id = this.dsThanhToan[i].dacdiem_id
                    k_dacdiem_id = this.dsThanhToan[i].dacdiem_id
                } else {
                    rowDiaChi.dacdiem_id = 0
                    k_dacdiem_id = 0
                }
                rowDiaChi.sonha = this.dsThanhToan[i].sonha
                rowDiaChi.diachi = this.dsThanhToan[i].diachi_tt

                if (themmoi) {
                    rowDiaChiHDTT.diachi_id = rowDiaChi.diachi_id
                    rowDiaChiHDTT.hdtt_id = this.hdtt_id
                }

                if (this.phantap_kh) {
                    if (this.dsThanhToan[i].donvi_id && rowDiaChi.phuong_id != 0
                        && (rowDiaChi.pho_id != 0 || rowDiaChi.ap_id != 0 || rowDiaChi.khu_id != 0)) {
                        let input = {
                            ap_id: k_ap_id,
                            dacdiem_id: k_dacdiem_id,
                            diemtn: this.pt_diem_tn,
                            doanhthu: this.pt_doanhthu,
                            donviql_id: this.dsThanhToan[i].donvi_id,
                            khu_id: k_khu_id,
                            loaikh_id: this.loaiKhachHangId,
                            loainv_id: Enum.Loai_NV.NHANVIEN_AM,
                            nganhnghe_id: this.nganhnghe_id,
                            pho_id: k_pho_id,
                            phuong_id: k_phuong_id
                        }
                        let res = await this.$root.context.post('/web-hopdong/tachnhapthuebao/lay_ds_am_ptkh', input)
                        if (res.data.length > 0) {
                            row_hdtt_nv.hdtt_id = this.hdtt_id
                            row_hdtt_nv.nhanvien_id = res.data[0].nhanvien_id
                        }
                    }
                }
                console.log("rowHDTT: ", rowHDTT)
                this.dsHDTT.push(rowHDTT)
                this.dsDiaChi_HDTT.push(rowDiaChi)
                this.dsDiaChiHDTT.push(rowDiaChiHDTT)
                this.dsHDTT_NV.push(row_hdtt_nv)
            }
        },

        Lay_HDTT_ID_Theo_Daset: function (ds, thanhtoan_id) {
            let ihdtt_id = 0
            for (let i = 0; i < ds.length; i++) {
                if (ds[i].thanhtoan_id) {
                    if (ds[i].thanhtoan_id == thanhtoan_id)
                        ihdtt_id = ds[i].hdtt_id
                }
            }
            return ihdtt_id
        },

        TaoDuLieu_HDTB: async function (themmoi) {
            this.dsDK_DVK = []
            this.dsHDTB = []
            let rowHDTB = {}
            this.dsDiaChi_HDTB = []
            let rowDiaChiTB = {}
            let rowDiaChiLD = {}
            this.dsDiaChiHDTB = []
            let rowDiaChiHDTB = {}
            for (let i = 0; i < this.dsThueBao.length; i++) {
                rowHDTB = {}
                rowDiaChiTB = {}
                rowDiaChiLD = {}
                rowDiaChiHDTB = {}
                let thanhtoan_id = null
                thanhtoan_id = this.dsThueBao[i].thanhtoan_id
                rowHDTB.ma_tb = this.dsThueBao[i].ma_tb
                if (themmoi) {
                    this.hdtb_id = await this.getKey('HD_THUEBAO')
                    rowHDTB.hdtb_id = this.hdtb_id
                    this.hdtt_id = this.Lay_HDTT_ID_Theo_Daset(this.dsHDTT, thanhtoan_id)
                    if (this.hdtt_id == 0) {
                        this.kiemtra = false
                        this.$toast.error("Thuê bao '" + this.dsThueBao[i].ma_tb + "' không thuộc thanh toán nào trong danh sách")
                        break
                    }
                } else {
                    this.hdtb_id = this.dsThueBao[i].hdtb_id
                    rowHDTB.hdtb_id = this.hdtb_id
                    this.hdtt_id = this.dsThueBao[i].hdtt_id
                }
                rowHDTB.hdtt_id = this.hdtt_id

                rowHDTB.diachi_ld = this.dsThueBao[i].diachi_ld
                rowHDTB.diachi_tb = this.dsThueBao[i].diachi_tb
                rowHDTB.dichvuvt_id = this.dsThueBao[i].dichvuvt_id
                rowHDTB.doituong_id = this.dsThueBao[i].doituong_id
                rowHDTB.donvi_id = this.dsThueBao[i].donvi_id
                rowHDTB.hdkh_id = this.hdkh_id

                //Lấy ngày hoàn thành là ngày yêu cầu, nhưng không update vào danh bạ
                rowHDTB.ngay_ht = this.ngayYeuCau != null ? moment(this.ngayYeuCau).format('DD/MM/YYYY') : null
                //
                rowHDTB.loaitb_id = this.dsThueBao[i].loaitb_id
                rowHDTB.quytrinh_id = this.dsThueBao[i].quytrinh_id

                rowHDTB.kieuld_id = this.dsThueBao[i].kieuld_id
                rowHDTB.ten_tb = this.dsThueBao[i].ten_tb
                rowHDTB.thuebao_id = this.dsThueBao[i].thuebao_id
                rowHDTB.hdkh_id = this.hdkh_id
                rowHDTB.tthd_id = Enum.TrangThaiHD.MOI
                if (themmoi) {
                    if (this.kt_kylai == false)
                        rowHDTB.kieu_tb = "1"//Nếu chưa ký lại thì kiểu thuê bao là kí lại
                    else {
                        //Nếu ký lại rồi thì lấy kiểu thuê bao từ  datagridview xuống
                        rowHDTB.kieu_tb = this.dsThueBao[i].kieu_tb
                    }
                } else
                    rowHDTB.kieu_tb = this.dsThueBao[i].kieu_tb
                if (rowHDTB.kieu_tb == "1" || rowHDTB.kieu_tb == "2")
                    rowHDTB.tbhd_pl = 1//Nếu kiểu thuê bao là 1 hoặc 2 thì tbhd_pl=1: thuê bao thuộc hợp đồng
                else
                    rowHDTB.tbhd_pl = 0//Nếu kiểu thuê bao là 3 thì tbhd_pl=0: thuê bao thuộc phụ lục

                if (this.dsThueBao[i].ngay_sn1)
                    rowHDTB.ngay_sn = this.dsThueBao[i].ngay_sn1

                else
                    rowHDTB.ngay_sn = this.ngaySinh != null ? moment(this.ngaySinh).format('DD/MM/YYYY') : null//neu ko chuan hoa o frmChuahoaTTB thi se lay lay sinh/thanh lap cua kh

                rowHDTB.ghichu = this.dsThueBao[i].ghichu
                /// Add to list
                this.dsHDTB.push(rowHDTB)

                //Taọ dữ liệu địa chỉ cho HDTB
                // - Tạo dữ liệu địa chỉ thuê bao & địa chỉ lắp đặt --22/07/2010
                let idiachitb_id = 0
                let idiachild_id = 0
                if (themmoi == true) {
                    idiachitb_id = await this.getKey('DIACHI')
                    idiachild_id = await this.getKey('DIACHI')
                } else {
                    idiachitb_id = this.dsThueBao[i].diachitb_id
                    idiachild_id = this.dsThueBao[i].diachild_id
                }
                //add row d/c thuê bao
                rowDiaChiTB.diachi_id = idiachitb_id

                rowDiaChiTB.quan_id = this.dsThueBao[i].quantb_id
                rowDiaChiTB.phuong_id = this.dsThueBao[i].phuongtb_id
                if (this.dsThueBao[i].tinhtb_id)
                    rowDiaChiTB.tinh_id = this.dsThueBao[i].tinhtb_id

                if (this.dsThueBao[i].photb_id)
                    rowDiaChiTB.pho_id = this.dsThueBao[i].photb_id
                else
                    rowDiaChiTB.pho_id = 0;

                if (this.dsThueBao[i].aptb_id && this.dsThueBao[i].aptb_id != 0)
                    rowDiaChiTB.ap_id = this.dsThueBao[i].aptb_id

                if (this.dsThueBao[i].khutb_id && this.dsThueBao[i].khutb_id != 0)
                    rowDiaChiTB.khu_id = this.dsThueBao[i].khutb_id

                if (this.dsThueBao[i].dacdiemtb_id && this.dsThueBao[i].dacdiemtb_id != 0)
                    rowDiaChiTB.dacdiem_id = this.dsThueBao[i].dacdiemtb_id

                rowDiaChiTB.sonha = this.dsThueBao[i].sonha_tb
                rowDiaChiTB.diachi = this.dsThueBao[i].diachi_tb
                /// Add to list
                this.dsDiaChi_HDTB.push(rowDiaChiTB)

                //add row d/c Lắp đặt
                rowDiaChiLD.diachi_id = idiachild_id
                rowDiaChiLD.quan_id = this.dsThueBao[i].quanld_id
                rowDiaChiLD.phuong_id = this.dsThueBao[i].phuongld_id

                if (this.dsThueBao[i].tinhld_id)
                    rowDiaChiLD.tinh_id = this.dsThueBao[i].tinhld_id

                if (this.dsThueBao[i].phold_id)
                    rowDiaChiLD.pho_id = this.dsThueBao[i].phold_id
                else
                    rowDiaChiLD.pho_id = 0

                if (this.dsThueBao[i].apld_id && this.dsThueBao[i].apld_id != 0)
                    rowDiaChiLD.ap_id = this.dsThueBao[i].apld_id

                if (this.dsThueBao[i].khuld_id && this.dsThueBao[i].khuld_id != 0)
                    rowDiaChiLD.khu_id = this.dsThueBao[i].khuld_id

                if (this.dsThueBao[i].dacdiemld_id && this.dsThueBao[i].dacdiemld_id != 0)
                    rowDiaChiLD.dacdiem_id = this.dsThueBao[i].dacdiemld_id
                ///
                rowDiaChiLD.sonha = this.dsThueBao[i].sonha_ld
                rowDiaChiLD.diachi = this.dsThueBao[i].diachi_ld
                /// Add to list
                this.dsDiaChi_HDTB.push(rowDiaChiLD)

                if (themmoi) {
                    rowDiaChiHDTB = {}
                    rowDiaChiHDTB.diachi_id = idiachitb_id
                    rowDiaChiHDTB.diachild_id = idiachild_id
                    rowDiaChiHDTB.hdtb_id = this.hdtb_id
                    this.dsDiaChiHDTB.push(rowDiaChiHDTB)
                }
            }
        },

        TaoDuLieu_DiaChiKH: async function (themmoi) {
            this.dsDiaChi = []
            this.dsDiaChiHDKH = []
            let rowDiaChiKH = {}
            if (themmoi == true)
                this.diachikh_id = await this.getKey('DIACHI')
            rowDiaChiKH.diachi_id = this.diachikh_id
            rowDiaChiKH.tinh_id = this.tinhkh_id
            rowDiaChiKH.quan_id = this.quankh_id
            rowDiaChiKH.phuong_id = this.phuongkh_id
            if (this.phokh_id != 0)
                rowDiaChiKH.pho_id = this.phokh_id
            if (this.apkh_id != 0)
                rowDiaChiKH.ap_id = this.apkh_id
            if (this.khukh_id != 0)
                rowDiaChiKH.khu_id = this.khukh_id
            if (this.dacdiemkh_id != 0)
                rowDiaChiKH.dacdiem_id = this.dacdiemkh_id
            rowDiaChiKH.sonha = this.sonhakh
            rowDiaChiKH.diachi = this.diaChiKhachHang
            this.dsDiaChi.push(rowDiaChiKH)

            if (themmoi == true) {
                //Tạo dữ liệu dsDiaChiHDKH
                let rowDiaChiHDKH = {}
                rowDiaChiHDKH.diachi_id = this.diachikh_id
                rowDiaChiHDKH.hdkh_id = this.hdkh_id
                this.dsDiaChiHDKH.push(rowDiaChiHDKH)
            }
        },

        maThueBaoKeypress: async function () {
            this.maThueBao = this.maThueBao.trim();
            this.tinhkh_id = 0;
            this.quankh_id = 0;
            this.phuongkh_id = 0;
            this.phokh_id = 0;
            this.apkh_id = 0;
            this.khukh_id = 0;
            this.dacdiemkh_id = 0;

            if (this.maThueBao !== "") {
                let input = {
                    in_dichvuvt_id: this.dichvuvt_id,
                    in_donvi_dl_id: 0,
                    in_donvi_id: this.$root.token.getDonViID(),
                    in_loaihd_id: Enum.LoaiHopDong.KY_LAI_HD_GOC,
                    in_loaitb_id: 0,
                    ma_tb: this.maThueBao,
                    in_nhanvien_id: this.$root.token.getNhanVienID(),
                    in_tthd_id: Enum.TrangThaiHD.MOI
                }
                let res = await this.$root.context.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', input)
                if (res.data != null && res.data.length > 0) {
                    this.ds_HDKH = res.data
                    await this.HienThiTTHopDongKH()
                    this.trangthaitb_id = 1
                } else await this.hienThiTT_DanhBa_KH(this.maThueBao)
            }
        },

        HienThiTTHopDongKH: async function () {
            try {
                if (this.ds_HDKH.length > 0) {
                    this.setButton(3)
                    this.kt_kylai = true //Đánh dấu đã ký lại hợp đồng gốc vì lấy thông tin từ hợp đồng có nghĩa là dữ liệu đã được chuẩn hóa

                    this.hdkh_id = this.ds_HDKH[0].hdkh_id
                    this.maGiaoDich = this.ds_HDKH[0].ma_gd
                    this.maHopDong = this.ds_HDKH[0].ma_hd
                    this.maKhachHang = this.ds_HDKH[0].ma_kh
                    //Lấy giá trị cho biến kylai để thêm thuê bao
                    this.kylai = await this.KiemTra_KH_KyHD_Goc()
                    if (this.ds_HDKH[0].khachhang_id)
                        this.khachhang_id = this.ds_HDKH[0].khachhang_id
                    else
                        this.khachhang_id = 0
                    this.tenKhachHang = this.chuanHoaTen(this.ds_HDKH[0].ten_kh)
                    this.ten_kh_Mytv = this.ds_HDKH[0].ten_kh//Lấy tên KH để update vasc
                    this.diaChiKhachHang = this.chuanHoaTen(this.ds_HDKH[0].diachi_kh)
                    this.diachi_kh_Mytv = this.ds_HDKH[0].diachi_kh// Lấy địa chỉ khách hàng
                    let ds = await this.LAY_DS_DIACHI_THEO_HDKH_ID(this.hdkh_id);
                    this.HienThiTTDiaChi(ds, 1);
                    this.nguoiDaiDien = this.chuanHoaTen(this.ds_HDKH[0].nguoi_dd)
                    this.chucVu = this.chuanHoaTen(this.ds_HDKH[0].chucdanh_dd)
                    this.dienThoai = this.ds_HDKH[0].so_dt
                    this.fax = this.ds_HDKH[0].so_fax
                    this.dacTrung = this.ds_HDKH[0].dactrung

                    this.email_Mytv = this.ds_HDKH[0].email//Lấy email của khách hàng
                    this.sofax_Mytv = this.ds_HDKH[0].so_fax//Lấy số fax của khách hàng
                    this.hoKhau = this.ds_HDKH[0].hokhau
                    this.noiCapHK = this.chuanHoaTen(this.ds_HDKH[0].noicap_hk)
                    if (this.ds_HDKH[0].quoctich_id) {
                        this.isCheckQT = true
                        this.quocTichId = this.ds_HDKH[0].quoctich_id
                    } else {
                        this.isCheckQT = false
                        this.quocTichId = null
                    }
                    if (this.ds_HDKH[0].dantoc_id) {
                        this.isCheckDT = true
                        this.danTocId = this.ds_HDKH[0].dantoc_id
                    } else {
                        this.isCheckDT = false
                        this.danTocId = null
                    }
                    if (this.ds_HDKH[0].nganhnghe_id)
                        this.setNganhNgheCombobox(this.ds_HDKH[0].nganhnghe_id)
                    else
                        this.setNganhNgheCombobox(this.dsNganhNghe[0].nganhnghe_id)
                    // if (this.ds_HDKH[0].khlon_id)
                    //     this.khlon_id = this.ds_HDKH[0].khlon_id
                    this.TNC1_ID = this.ds_HDKH[0].TNC1_ID
                    this.TNC2_ID = this.ds_HDKH[0].TNC2_ID
                    this.TNC3_ID = this.ds_HDKH[0].TNC3_ID
                    this.TNC4_ID = this.ds_HDKH[0].TNC4_ID
                    this.gioiTinh = this.ds_HDKH[0].gioitinh
                    this.gioitinh_Mytv = this.ds_HDKH[0].gioitinh
                    if (this.ds_HDKH[0].ngay_hk)
                        this.ngayCapHK = moment(this.ds_HDKH[0].ngay_hk, 'YYYY-MM-DD HH:mm:ss').toDate()
                    else
                        this.ngayCapHK = null
                    if (this.ds_HDKH[0].loaikh_id)
                        // this.loaiKhachHangId = this.ds_HDKH[0].loaikh_id
                        this.selectCboRow(this.ds_HDKH[0].loaikh_id);
                    this.PHANLOAIKH_ID = this.ds_HDKH[0].phanloaikh_id
                    if (this.ds_HDKH[0].ngay_sn) {
                        this.ngay_sn_Mytv = moment(this.ds_HDKH[0].ngay_sn, 'YYYY-MM-DD HH:mm:ss').toDate()
                        this.ngaySinh = moment(this.ds_HDKH[0].ngay_sn, 'YYYY-MM-DD HH:mm:ss').toDate()
                    } else {
                        this.ngay_sn_Mytv = null
                        this.ngaySinh = null
                    }
                    this.ngayYeuCau = moment(this.ds_HDKH[0].ngay_yc, 'YYYY-MM-DD HH:mm:ss').toDate()
                    if (this.ds_HDKH[0].ngaylap_hd)
                        this.ngayLapHD = moment(this.ds_HDKH[0].ngaylap_hd, 'YYYY-MM-DD HH:mm:ss').toDate()
                    this.soGiayTo = this.ds_HDKH[0].so_gt
                    this.so_gt_Mytv = this.ds_HDKH[0].so_gt//Lấy số giấy tờ của khách hàng
                    if (this.ds_HDKH[0].ngaycap)
                        this.ngayCapGT = moment(this.ds_HDKH[0].ngaycap, 'YYYY-MM-DD HH:mm:ss').toDate()
                    else
                        this.ngayCapGT = null
                    this.loaiGiayToId = this.ds_HDKH[0].loaigt_id
                    this.noiCapGT = this.ds_HDKH[0].noicap

                    this.maSoThue = this.ds_HDKH[0].mst
                    this.diaChiNDD = this.ds_HDKH[0].diachi_ndd
                    if (this.ds_HDKH[0].nganhang_id) {
                        this.isCheckNH = true
                        this.nganHangId = this.ds_HDKH[0].nganhang_id
                    } else {
                        this.isCheckNH = false
                        this.nganHangId = null
                    }
                    this.taiKhoan = this.ds_HDKH[0].stk
                    this.ghiChu = this.ds_HDKH[0].ghichu
                    if (this.ds_HDKH[0].ngayhethan_gt)
                        this.ngayHetHan = moment(this.ds_HDKH[0].ngayhethan_gt, 'YYYY-MM-DD HH:mm:ss').toDate()
                    else
                        this.ngayHetHan = null
                    if (this.ds_HDKH[0].ctv_id) {
                        this.isCheckCTV = true
                        this.ctv_id = this.ds_HDKH[0].ctv_id
                        this.congTacVien = this.ds_HDKH[0].ten_ctv
                    } else {
                        this.isCheckCTV = false
                        this.ctv_id = null
                        this.congTacVien = null
                    }

                    if (this.ds_HDKH[0].nhanviengt_id) {
                        this.isCheckNGT = true
                        this.nguoigt_id = this.ds_HDKH[0].nhanviengt_id
                        this.nguoiGioiThieu = this.ds_HDKH[0].ten_ngt
                    } else {
                        this.isCheckNGT = false
                        this.nguoigt_id = null
                        this.nguoiGioiThieu = null
                    }
                    await this.HienThiDanhSacHDTT()
                    await this.HienThiDanhSacHDTB()
                }
                else {
                    this.$toast.error("Không tìm thấy thông tin về mã giao dịch '" + this.maGiaoDich + "'")
                    this.setButton(0)
                }
            } catch (ex) {
                console.log(ex)
            }
        },

        HienThiTTDiaChi(ds, kieu) {
            if (ds.length > 0 && kieu === 1) {
                this.tinhkh_id = ds[0].tinh_id ? ds[0].tinh_id : 0;
                this.quankh_id = ds[0].quan_id ? ds[0].quan_id : 0;
                this.phuongkh_id = ds[0].phuong_id ? ds[0].phuong_id : 0;
                this.dacdiemkh_id = ds[0].dacdiem_id ? ds[0].dacdiem_id : 0;
                this.phokh_id = ds[0].pho_id ? ds[0].pho_id : 0;
                this.apkh_id = ds[0].ap_id ? ds[0].ap_id : 0;
                this.khukh_id = ds[0].khu_id ? ds[0].khu_id : 0;
                this.sonhakh = ds[0].sonha;
            }
        },

        HienThiDanhSacHDTT: async function () {
            this.trangThaiDuLieu = "Trạng thái dữ liệu: Thông tin đang được lấy từ Hợp đồng"
            let res = await this.$root.context.get(`/web-hopdong/ThayDoiHopDong/lay_ds_hopdong_tt_theo_hdkh_id?hdkhId=${this.hdkh_id}`)
            this.dsThanhToan = res.data
            this.tongSoTT = this.dsThanhToan.length
        },

        HoanThienHS: async function () {
            try {
                this.$root.showLoading(true)
                let kt_mytv = true
                let flag_dd = false//Biến kiểm tra xem đồng bộ dữ liệu di động có thành công hay khôg
                let flag_mytv = false//Biến kiểm tra xem đồng bộ dữ liệu mytv có thành công hay khôg
                let status_tb = true
                let flag_visa = false
                let kt_adsl = true
                let flag_vtn = false
                let kt_vtn = true
                let stt_tb = 0
                if (this.hdkh_id != 0 && this.khachhang_id != 0) {
                    if (!Enum.DONGBO_TEST.TEST) {
                        //Đồng bộ dữ liệu lên Vinaphone, VDC, VASC, VTN
                        for (let i = 0; i < this.dsThanhToan.length; i++) {
                            for (let j = 0; j < this.dsThueBao.length; j++) {
                                //Đồng bộ dữ liệu lên VDC
                                if (this.dsThanhToan[i].hdtt_id == this.dsThueBao[j].hdtt_id
                                    && this.dsThueBao[j].dichvuvt_id == Enum.DichVuVienThong.ADSL
                                    && (this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL
                                        || this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH
                                        || this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER)) {
                                    let in_tocdo_id = await this.getTocDoId(this.dsThueBao[j].thuebao_id)
                                    let kieu_mega = await this.getKieuMega(in_tocdo_id)
                                    if (kieu_mega === 0)
                                        continue

                                    if (!(await this.ThayDoi_TTKH_Visa(j))) {
                                        flag_visa = false
                                        kt_adsl = false
                                        stt_tb = j//Lưu lại thứ tự row ds thuê bao bị lỗi
                                        break
                                    } else flag_visa = true
                                }
                                if (this.dsThanhToan[i].hdtt_id == this.dsThueBao[j].hdtt_id
                                    && this.dsThueBao[j].dichvuvt_id == Enum.DichVuVienThong.ADSL
                                    && this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) {
                                    if (await this.ChangeSubscriber(j) == 1) {
                                        kt_mytv = false
                                        flag_mytv = false//Nếu cập nhật thông tin khách hàng trên myportal không thành công thì gán cờ =false
                                        break
                                    }
                                    else flag_mytv = true//Cập nhật thuê bao mytv trên myportal thành công
                                }

                                //Nếu là thuê bao di động thì thực hiện đồng bộ lên vinaphone
                                if (Enum.TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
                                    //Thieu code
                                } else flag_dd = true

                                if (this.dsThanhToan[i].hdtt_id == this.dsThueBao[j].hdtt_id
                                    && this.dsThueBao[j].dichvuvt_id == Enum.DichVuVienThong.ADSL) {
                                    if (this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL
                                        || this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH
                                        || this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER) {
                                        let in_tocdo_id = await this.getTocDoId(this.dsThueBao[j].thuebao_id)
                                        let kieu_mega = await this.getKieuMega(in_tocdo_id)
                                        if (kieu_mega === 0)
                                            continue

                                        if (await this.Kiemtra_doituong_vdc(this.dsThueBao[j].hdtb_id, Enum.LoaiHinhTB.INTERNET_ADSL)) {
                                            if (!(await this.ThayDoi_DoiTuong_Visa(j))) {
                                                flag_visa = false
                                                break
                                            }
                                            else flag_visa = true
                                        }
                                    } else {
                                        if (await this.Kiemtra_doituong_vdc(this.dsThueBao[j].hdtb_id, Enum.LoaiHinhTB.INTERNET_MYTV)) {
                                            if (!(await this.ThayDoi_NhomKhachHang_UuTien_VASC(j))) {
                                                flag_mytv = false
                                                break
                                            } else flag_mytv = true
                                        }
                                    }
                                }

                                //Nếu là thuê bao ims thì thực hiện đồng bộ lên VTN
                                if (this.dsThanhToan[i].hdtt_id == this.dsThueBao[j].hdtt_id
                                    && this.dsThueBao[j].dichvuvt_id == Enum.DichVuVienThong.IMS) {
                                    let hdtb_ims = this.dsThueBao[j].hdtb_id
                                    let hdtt_ims = this.dsThanhToan[i].hdtt_id
                                    //Nếu là SIP POST bay luôn không cần check
                                    if (this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.IMS_POTS
                                        || this.dsThueBao[j].loaitb_id == Enum.LoaiHinhTB.IMS_SIP) {
                                        flag_vtn = true;
                                        kt_vtn = true;
                                    } else {
                                        if (await this.CapNhatThongTinThanhToan(hdtt_ims, hdtb_ims)) {
                                            if (await this.CapNhatThongTinThueBao(hdtb_ims))
                                                flag_vtn = true
                                            else flag_vtn = true //default true để cho qua kích hoạt nếu lỗi
                                        }
                                        else kt_vtn = true
                                    }
                                }
                            }
                        }

                        if (kt_mytv == false)
                            return
                        if (Enum.TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) // true => mở hàm
                            if (flag_dd == true)
                                this.$toast.success("Đồng bộ dữ liệu di động trên Vinaphone thành công!")
                        if (flag_mytv == true)
                            this.$toast.success("Đồng bộ dữ liệu Mytv trên Myportal thành công!")
                        if (flag_visa)
                            this.$toast.success("Đồng bộ dữ liệu trên Visa thành công!")
                        if (kt_adsl == false)
                            return this.$toast.error("Có lỗi trong quá trình đồng bộ thông tin thuê bao " + this.dsThueBao[stt_tb].ma_tb + " trên Visa!")
                        if (flag_vtn == true)
                            this.$toast.success("Đồng bộ dữ liệu trên VTN thành công!")
                        if (kt_vtn == false)
                            return this.$toast.error("Có lỗi trong quá trình đồng bộ thông tin thuê bao " + this.dsThueBao[stt_tb].ma_tb + " trên VTN!")
                    }
                    //Cập nhật ngày hoàn thành ngày thanh toán.
                    for (let j = 0; j < this.dsThueBao.length; j++) {
                        //Kiểm tra xem nếu chưa có hướng giao hoàn thiện hồ sơ thì không cho làm
                        let inputhuonggiaodich = {
                            quytrinh_id: this.dsThueBao[j].quytrinh_id,
                            thutu_id: 2
                        }
                        let huonggiaodich = await this.$root.context.post('/web-hopdong/thanhly/fn_lay_huonggiao_quytrinh', inputhuonggiaodich);
                        if (huonggiaodich.data == null || huonggiaodich.data == 0)
                            return this.$toast.error("Chưa định nghĩa quy trình hoàn thiện. Hãy liên hệ admin để được hỗ trợ!")

                        let phieu_id = await this.getPhieuId(this.dsThueBao[j].hdtb_id)
                        if (phieu_id === -1)
                            return this.$toast.error("Không tìm thấy phiếu tiếp nhận yêu cầu!")

                        await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/capnhat_ngay_hoanthien', {
                            hdtbId: this.dsThueBao[j].hdtb_id
                        });

                        await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/capnhat_nvth', {
                            hdtbId: this.dsThueBao[j].hdtb_id
                        });

                        await this.CapNhat_TT_Phieu_HC(this.dsThueBao[j].hdtb_id, Enum.TRANGTHAI_PH.MOI)
                        await this.CapNhat_TT_Phieu_HC(this.dsThueBao[j].hdtb_id, Enum.TRANGTHAI_PH.DAGIAO)
                        await this.giaoPhieuTdKhiHc({
                            hdtb_id: this.dsThueBao[j].hdtb_id,
                            phieu_id: phieu_id,
                            huonggiao_id: huonggiaodich.data,
                            may_cn: await this.getIp()
                        })
                    }

                    let capnhat_hoanthien_kylai_rp = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/capnhat_hoanthien_kylai', {
                        hdkhId: this.hdkh_id,
                        khachHangId: this.khachhang_id
                    });
                    if (capnhat_hoanthien_kylai_rp.data != null) {
                        this.$toast.success("Hoàn thiện dữ liệu vào danh bạ tỉnh thành công!");
                        this.setButton(0);
                    }
                    else {
                        this.$toast.error("Có lỗi trong quá trình cập nhật hoàn thiện ký lại.\n" + capnhat_hoanthien_kylai_rp.message + "\n" + capnhat_hoanthien_kylai_rp.message_detail);
                    }
                }
                else
                    this.$toast.error("Không có thông tin về hợp đồng!")
            } catch (e) {
                console.log(e)
            } finally {
                this.$root.showLoading(false)
            }
        },

        getIp: async function () {
            try {
                const response = await fetch("https://jsonip.com/")
                const data = await response.json()
                return data.ip
            } catch (e) {
                return ""
            }
        },

        getTocDoId: async function (thueBaoId) {
            try {
                let rs = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/lay_tocdo_id_adsl?thueBaoId=${thueBaoId}`)
                if (rs.error_code === '0' && rs.data !== null)
                    return rs.data
                else return -1
            } catch (e) {
                return -1
            }
        },

        getKieuMega: async function (tocDoId) {
            try {
                let rs = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/lay_kieu_mega?tocDoId=${tocDoId}`)
                if (rs.error_code === '0' && rs.data !== null)
                    return rs.data
                else return -1
            } catch (e) {
                return -1
            }
        },

        getPhieuId: async function (hdtbId) {
            try {
                let rs = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/lay_max_phieu_id?hdtbId=${hdtbId}`)
                if (rs.error_code === '0' && rs.data !== null)
                    return rs.data
                else return -1
            } catch (e) {
                return -1
            }
        },

        getDoiTuongIdVdc: async function (doiTuongId) {
            try {
                let rs = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/lay_doituong_id_vdc?doiTuongId=${doiTuongId}`)
                if (rs.error_code === '0' && rs.data !== null)
                    return rs.data
                else return -1
            } catch (e) {
                return -1
            }
        },

        giaoPhieuTdKhiHc: async function (data) {
            await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/giaophieu_td_khi_hc`, data)
        },

        CapNhat_TT_Phieu_HC: async function (hdtb_id, ttph_id) {
            try {
                let rs = await this.$root.context.post(
                    `/web-hopdong/dongbo-visa-vasc/capnhat_tt_phieu_hc?hdtb_id=${hdtb_id}&ttph_id=${ttph_id}`
                )
            } catch (error) {
                console.log(error)
            } finally {
            }
        },

        Kiemtra_doituong_vdc: async function (hdtb_id, loaihinh_id) {
            try {
                let rs = await this.$root.context.get(
                    "/web-hopdong/dongbo-visa-vasc/kiemtra_doituong_vdc",
                    {
                        hdtb_id: hdtb_id,
                        loaihinh_id: loaihinh_id,
                    }
                )
                return rs.data[0].rs == 1
            } catch (error) {
                console.log(error)
                return false
            } finally {
            }
        },

        ThayDoi_TTKH_Visa: async function (j) {
            try {
                let input = {
                    accountModel: {
                        accountName: this.dsThueBao[j].ma_tb,
                    },
                    customerModel: {
                        firstName: this.dsThueBao[j].ten_tb,
                        address: this.dsThueBao[j].diachi_ld,
                        phone: this.dienThoai.trim(),
                        email: ""
                    },
                    contract: ""
                }
                let res = await this.$root.context.post('/tichhop/visa-app/updateCustomer', input)
                return res.errorCode == 0
            }
            catch (ex) {
                this.$toast.error("Có lỗi xảy ra khi cập nhật thay đổi thông tin khách hàng Visa!")
                console.log(ex)
                return false
            }
        },

        ThayDoi_DoiTuong_Visa: async function (j) {
            try {
                let account = this.dsThueBao[j].ma_tb
                let cusType = await this.getDoiTuongIdVdc(this.dsThueBao[j].doituong_id)
                await this.$root.context.post('/tichhop/visa-app/changeCustomerType', {
                    customerModel: {
                        accountName: account,
                        customerType: cusType
                    },
                    note: "Thay doi loai khach hang qua WS ngay: " + moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
                })
                return true
            } catch (ex) {
                this.$toast.error("" + ex)
                console.log(ex)
                return false
            }
        },

        ThayDoi_NhomKhachHang_UuTien_VASC: async function (j) {
            try {
                let IPTVAccount = this.dsThueBao[j].ma_tb
                let sPromo_id = "0"
                if (this.dsThueBao[j].doituong_id == Enum.DoiTuong.Y_TE)
                    sPromo_id = "DT02"
                let rs = await this.$root.context.post('/tichhop/vasc/changePromotions', {
                    iptvaccount: IPTVAccount,
                    spromo_ID: sPromo_id
                })
                return rs.errorCode == 0
            } catch (ex) {
                this.$toast.error("Có lỗi xảy ra khi thay đổi nhóm khác hàng ưu tiên VASC!")
                console.log(ex)
                return false
            }
        },

        ChangeSubscriber: async function (j) {
            try {
                let Name = this.ten_kh_Mytv//tên KH
                let Birthday = this.ngay_sn_Mytv == null ? null : moment(this.ngay_sn_Mytv).format('DD/MM/YYYY');
                let Sex = ""//Giới tính : Nam --> 0, Nữ --> 1; không giới tính --> 0
                if (this.gioitinh_Mytv != "1")
                    Sex = "0"
                else
                    Sex = "1"
                this.diachi_ld_Mytv = this.dsThueBao[j].diachi_ld
                let FirstAdd = this.diachi_kh_Mytv //địa chỉ KH
                let SecondAdd = this.diachi_ld_Mytv//Dịa chỉ Lđặt
                let IDNo = this.so_gt_Mytv //Số GT khách hàng
                // nếu điện thoại lhệ> 8 -->tellno = "" & cellno = điện thoại liên hệ  và ngược lại
                let TelNo = ""
                let CellNo = ""
                if (this.dienThoai.Length > 8) {///Di dong
                    TelNo = ""
                    CellNo = this.dienThoai.trim()
                } else {
                    TelNo = this.dienThoai.trim()
                    CellNo = ""
                }
                let Email = this.email_Mytv//Email KH
                let YM = ""// trống
                let Website = ""//trống
                let FaxNo = this.sofax_Mytv//số Fax KH
                this.matb_Mytv = this.dsThueBao[j].ma_tb
                let IPTVAccount = this.matb_Mytv//Mã TB dsHDTB
                let input = {
                    areaCode: this.$root.token.getMaTinh(),
                    birthday: Birthday,
                    cellNo: CellNo,
                    email: Email,
                    faxNo: FaxNo,
                    firstAdd: FirstAdd,
                    idno: IDNo,
                    iptvaccount: IPTVAccount,
                    name: Name,
                    secondAdd: SecondAdd,
                    sex: Sex,
                    telNo: TelNo,
                    website: Website,
                    ym: YM
                }
                let res = await this.$root.context.post('/tichhop/vasc/changeSubscriber', input)
                if (res.errorCode == 0) {
                    await this.$root.context.post('/web-hopdong/khoiphucthanhly/capnhat_status_hdtb', {
                        hdtb_id: this.hdtb_id,
                        status: Enum.TRANGTHAI_DONGBO.DONGBO_CM
                    })
                    return 0
                } else return 1
            } catch (ex) {
                this.$toast.error(ex)
                console.log(ex)
                return 1
            }
        },

        CapNhatThongTinThanhToan: async function (hdtt_id, hdtb_id) {
            try {
                let drHDTT = await this.lay_ds_hopdong_tt_theo_hdtt_id(hdtt_id)
                let dsDVK = await this.lay_ds_dangky_dvk(hdtb_id, 2)
                let htph = Enum.HinhThucPhatHanhIMS.khongPhatHanh
                let id = ''
                for (let i = 0; i < dsDVK.length; i++) {
                    id = await this.lay_dvkhac_id_vtn(dsDVK[i].dvkhac_id)
                    if (id == Enum.HinhThucPhatHanhIMS.inThongBaoCuoc) {
                        htph = Enum.HinhThucPhatHanhIMS.inThongBaoCuoc
                        break;
                    }
                    if (id == Enum.HinhThucPhatHanhIMS.quaEmail)
                        htph = Enum.HinhThucPhatHanhIMS.quaEmail
                }

                let acc = await this.queryAccount(drHDTT[0].ma_tt)
                if (acc == null) return false

                let dr_dctt = await this.lay_ds_diachi_theo_hdtt(hdtt_id)
                let hdkh_id = drHDTT[0].hdkh_id
                let drHDKH = await this.lay_ds_hopdong_kh_theo_hdkh_id(hdkh_id)
                let dr_dckh = await this.lay_ds_diachi_theo_hdkh(hdkh_id)

                acc.contract_NO = drHDTT[0].ma_tt                           // số hợp đồng thông tin thanh toán = mã tt
                acc.account_CATEGORY = drHDKH[0].loaikh_id_vtn.toString()   // loại khách hàng
                acc.account_CODE = drHDTT[0].ma_tt                          // mã thanh toán
                acc.account_TYPE = "1";                                     // tính cước hay không tính cước
                acc.parent_ACCOUNT_CODE = drHDKH[0].ma_kh                   // mã khách hàng

                if (acc.account_CATEGORY == "1")
                    acc.bill_COMPANY = drHDKH[0].ten_kh                     // công ty
                else
                    acc.bill_COMPANY = ""                                   // công ty

                acc.bill_NAME_GENERATION = ""                               // chức danh
                acc.bill_LNAME = drHDTT[0].ten_tt                           // tên

                if (dr_dctt[0].diachi.length >= 152)
                    acc.bill_ADDRESS1 = dr_dctt[0].diachi.substring(0, 150) // địa chỉ thanh toán 1
                else
                    acc.bill_ADDRESS1 = dr_dctt[0].diachi

                acc.bill_COUNTRY_CODE = "1"                                 // mã nước
                acc.bill_CITY = this.$root.token.getMaTinh()                // mã tỉnh địa chỉ thanh toán
                acc.bill_STATE = dr_dctt[0].ma_quan                         // mã quận/ huyện
                acc.bill_COUNTY = dr_dctt[0].phuong_id                      // mã phường/xã
                acc.bill_UNIT = dr_dctt[0].pho_id                           // mã thôn/ khu
                acc.tin_NO = drHDKH[0].mst

                if (dr_dckh[0].diachi.Length > 152)
                    acc.cust_ADDRESS1 = dr_dckh[0].diachi.substring(0, 150)
                else
                    acc.cust_ADDRESS1 = dr_dckh[0].diachi

                acc.cust_CITY = this.$root.token.getMaTinh()                // mã tỉnh địa chỉ khách hàng
                acc.cust_STATE = dr_dckh[0].ma_quan                         // mã quận/ huyện
                acc.cust_COUNTY = dr_dckh[0].phuong_id                      // mã phường/xã
                acc.cust_UNIT = dr_dckh[0].pho_id                           // mã thôn/ khu
                acc.cust_EMAIL = drHDTT[0].email                            // email khách hàng
                acc.statement_TO_EMAIL = drHDTT[0].email                    // email dự phòng

                if (drHDTT[0].so_dt.Length > 20) {
                    acc.cust_PHONE1 = drHDTT[0].so_dt.substring(0, 20)      // điện thoại ban ngày
                    acc.cust_PHONE2 = drHDTT[0].so_dt.substring(0, 20)      // điện thoại ban đêm
                } else {
                    acc.cust_PHONE1 = drHDTT[0].so_dt                       // điện thoại ban ngày
                    acc.cust_PHONE2 = drHDTT[0].so_dt                       // điện thoại ban đêm
                }

                acc.bill_PERIOD = "M01"
                if (id != "0")
                    acc.bill_DISP_METH = htph;                              // hình thức phát hành
                acc.payment_METHOD = drHDTT[0].httt_id_vtn                  // hình thức thanh toán

                if (drHDTT[0].ghichu.Length > 240)
                    acc.remark = drHDTT[0].ghichu.substring(0, 240)
                else
                    acc.remark = drHDTT[0].ghichu

                await this.updateAccount(acc)
                return true
            } catch (ex) {
                console.log(ex)
                return false
            }
        },

        lay_ds_dangky_dvk: async function (in_hdtb_id) {
            let res = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dangky_dvk', {
                hd_id: in_hdtb_id,
                loaiid: 2
            })
            return (res.error_code === 'BSS-00000000' && res.data != null) ? res.data : []
        },

        CapNhatThongTinThueBao: async function (in_hdtb_id) {
            try {
                let drHDTB = await this.lay_ds_hopdong_tb_theo_hdtb_id(in_hdtb_id)
                let hdtt_id = drHDTB[0].hdtt_id
                let hdkh_id = drHDTB[0].hdkh_id
                let drHDTT = await this.lay_ds_hopdong_tt_theo_hdtt_id(hdtt_id)
                let drHDKH = await this.lay_ds_hopdong_kh_theo_hdkh_id(hdkh_id)
                let dr_dctb = await this.lay_ds_diachi_theo_hdtb(in_hdtb_id)
                let _dt = await this.lay_dauso_ims();
                let soMay = _dt != -1 ? _dt : '' + drHDTB[0].ma_tb
                let subs = await this.querySubscriber(soMay)
                let sub = {}
                for (let i = 0; i < subs.length; i++) {
                    if (subs[i].status == '1') {
                        sub = subs[i]
                        break;
                    }
                }

                sub.account_CODE = drHDTT[0].ma_tt
                sub.id_NO = drHDKH[0].so_gt
                sub.id_ISSUE_DATE = drHDKH[0].ngaycap != null && drHDKH[0].ngaycap != '' ? drHDKH[0].ngaycap.substring(0, 9) : ''
                sub.id_EXPIRE_DATE = drHDKH[0].ngayhethan_gt != null && drHDKH[0].ngayhethan_gt != '' ? drHDKH[0].ngayhethan_gt.substring(0, 9) : ''
                sub.birthday = drHDKH[0].ngay_sn != null && drHDKH[0].ngay_sn != '' ? drHDKH[0].ngay_sn.substring(0, 9) : ''
                sub.id_ISSUE_PLACE = drHDKH[0].noicap
                sub.country_CODE = '1'
                sub.address1 = dr_dctb[0].diachi
                sub.city = this.$root.token.getMaTinh(),
                    sub.state = dr_dctb[0].ma_quan
                sub.county = dr_dctb[0].phuong_id
                sub.unit = dr_dctb[0].pho_id
                sub.service_NAME_PRE = ''
                sub.service_FNAME = ''
                sub.service_MINIT = ''
                sub.service_LNAME = drHDTB[0].ten_tb
                await this.updateSubscriber(sub);
                return true
            } catch (ex) {
                console.log(ex)
                return false
            }
        },

        lay_ds_hopdong_tb_theo_hdtb_id: async function (hdtb_id) {
            let res = await this.$root.context.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id', {
                hdtb_id: hdtb_id
            })
            return (res.error_code === 'BSS-00000000' && res.data != null) ? res.data : []
        },

        lay_ds_hopdong_tt_theo_hdtt_id: async function (hdtt_id) {
            let res = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ds_hopdong_tt_theo_hdtt_id', {
                phanvung_id: this.$root.token.getPhanVungID(),
                hdtt_id: hdtt_id
            })
            return (res.error_code === 'BSS-00000000' && res.data != null) ? res.data : []
        },

        lay_ds_hopdong_kh_theo_hdkh_id: async function (hdkh_id) {
            let res = await this.$root.context.post('/web-hopdong/thanhly/lay_ds_hopdong_kh_theo_hdkh_id', {
                in_hdkh_id: hdkh_id
            })
            return (res.error_code === 'BSS-00000000' && res.data != null) ? res.data : []
        },

        lay_ds_diachi_theo_hdtb: async function (hdtb_id) {
            let res = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_diachi_theo_hdtb?hdtbId=${hdtb_id}`)
            return (res.error_code === 'BSS-00000000' && res.data != null) ? res.data : []
        },

        lay_ds_diachi_theo_hdtt: async function (hdtt_id) {
            let res = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_hdttid?hdttId=${hdtt_id}`)
            return (res.error_code === 'BSS-00000000' && res.data != null) ? res.data : []
        },

        lay_ds_diachi_theo_hdkh: async function (hdkh_id) {
            let res = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_diachi_theo_hdkh?hdKhId=${hdkh_id}`)
            return (res.error_code === 'BSS-00000000' && res.data != null) ? res.data : []
        },

        lay_dauso_ims: async function () {
            try {
                let rs = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/lay_dauso_ims`)
                if (rs.error_code === '0' && rs.data != null)
                    return rs.data
                else return -1
            } catch (e) {
                return -1
            }
        },

        lay_dvkhac_id_vtn: async function (dvKhacId) {
            try {
                let rs = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/lay_dvkhac_id_vtn?dvKhacId=${dvKhacId}`)
                if (rs.error_code === '0' && rs.data != null)
                    return rs.data.toString()
                else return '-1'
            } catch (e) {
                return '-1'
            }
        },

        queryAccount: async function (accountCode) {
            try {
                let rs = await this.$root.context.post(`/tichhop/vtn/queryAccount`, {
                    account_code: accountCode,
                    account_no: ''
                })
                if (Number(rs.errorCode) === 0 && rs.data != null)
                    return rs.data
                else return null
            } catch (e) {
                console.log(e)
                return null
            }
        },

        updateAccount: async function (acc) {
            try {
                let rs = await this.$root.context.post(`/tichhop/vtn/updateAccount`, {
                    accountObject: acc,
                    strReasonId: '5'
                })
                return Number(rs.errorCode) === 0
            } catch (e) {
                console.log(e)
                return false
            }
        },

        querySubscriber: async function (soMay) {
            try {
                let rs = await this.$root.context.post(`/tichhop/vtn/querySubscriber`, {
                    external_id: soMay
                })
                if (Number(rs.errorCode) === 0 && rs.data != null)
                    return rs.data
                else return []
            } catch (e) {
                console.log(e)
                return []
            }
        },

        updateSubscriber: async function (sub) {
            try {
                let rs = await this.$root.context.post(`/tichhop/vtn/updateSubscriber`, {
                    strReasonId: '5',
                    subscriberObject: sub
                })
                return Number(rs.errorCode) === 0
            } catch (e) {
                return false
            }
        },

        maGiaoDichKeypress: async function (e) {
            try {
                this.$root.showLoading(true);
                this.maGiaoDich = this.maGiaoDich.trim();
                if (this.maGiaoDich !== "") {
                    let data = {
                        maGiaoDich: this.maGiaoDich,
                        loaiHdId: 0
                    }
                    let res = await this.$root.context.post('/web-hopdong/ThayDoiHopDong/lay_ds_hopdong_theo_magiaodich_post', data)
                    this.ds_HDKH = res.data
                    await this.HienThiTTHopDongKH()
                    this.setButton(3)
                }
            } catch (err) {
                console.log(err)
            } finally {
                this.$root.showLoading(false);
            }
        },

        HienThiDanhSacHDTB: async function () {
            let res = await this.$root.context.get(`/web-hopdong/ThayDoiHopDong/lay_ds_hopdong_tb_theo_hdkh_id?hdkhId=${this.hdkh_id}`)
            this.dsThueBao = (res != null && res.data != null) ? res.data : []
            this.tongSoTB = this.dsThueBao.length

            for (let i = 0; i < this.dsThueBao.length; i++) {
                this.hdtb_id = this.dsThueBao[i].hdtb_id
                let thuebao_id = this.dsThueBao[i].thuebao_id != null ? this.dsThueBao[i].thuebao_id : 0

                let ds = await this.LAY_DS_DIACHI_THEO_HDTB_ID(this.hdtb_id)
                if (ds.length > 0) {
                    this.dsThueBao[i].diachitb_id = ds[0].diachi_id
                    this.dsThueBao[i].dacdiemtb_id = ds[0].dacdiem_id
                    this.dsThueBao[i].khutb_id = ds[0].khu_id
                    this.dsThueBao[i].aptb_id = ds[0].ap_id
                    this.dsThueBao[i].photb_id = ds[0].pho_id
                    this.dsThueBao[i].phuongtb_id = ds[0].phuong_id
                    this.dsThueBao[i].quantb_id = ds[0].quan_id
                    this.dsThueBao[i].tinhtb_id = ds[0].tinh_id
                    this.dsThueBao[i].sonha_tb = this.chuanHoaTen(ds[0].sonha)
                    this.dsThueBao[i].diachild_id = ds[0].diachild_id
                    this.dsThueBao[i].dacdiemld_id = ds[0].dacdiemld_id
                    this.dsThueBao[i].khuld_id = ds[0].khuld_id
                    this.dsThueBao[i].apld_id = ds[0].apld_id
                    this.dsThueBao[i].phold_id = ds[0].phold_id
                    this.dsThueBao[i].phuongld_id = ds[0].phuongld_id
                    this.dsThueBao[i].quanld_id = ds[0].quanld_id
                    this.dsThueBao[i].tinhld_id = ds[0].tinhld_id
                    this.dsThueBao[i].sonha_ld = this.chuanHoaTen(ds[0].sonha_ld)
                }
            }

            this.dsThueBao = [...this.dsThueBao]

            //Kiểm tra xem có thuê bao nào không, nếu có 1 hoăc ko có thuê bao nào thì không cho xóa thuê bao
            //từ 2 thuê bao trở lên thì cho xóa
            if (this.dsThueBao.length <= 1)
                this.isDisableBtnXoaTB = false
            else
                this.isDisableBtnXoaTB = true
        },

        XOA_HDTB_THEO_HDTT_ID: async function () {
            try {
                this.$root.showLoading(true);
                if ((this.dsThanhToanChon == null || this.dsThanhToanChon.length == 0)
                    && (this.dsThueBaoChon == null || this.dsThanhToanChon.dsThueBaoChon == 0)) {
                    this.$toast.error("Bạn chưa chọn thuê bao để xóa")
                    return
                }
                let tempt = true
                let kiemtra = 0//Khởi tạo =0,1: ký lại,2:Ghép mã
                for (let i = 0; i < this.dsThueBaoChon.length; i++) {
                    if ((this.dsThueBaoChon[i].kieu_tb == "2" && tempt == true) || (this.dsThueBaoChon[i].kieu_tb == "3" && tempt == true))//Kiểm tra xem có thuê bao nào ghép mã hoặc thuê bao thuộc phụ lục không
                    {
                        tempt = false
                    }
                    if (this.dsThueBaoChon[i].kieu_tb == "1") {
                        kiemtra = 1
                    }
                }

                if (kiemtra == 1 && tempt == false) {
                    this.$toast.error("Bạn phải xóa thuê bao ghép mã hoặc thuê bao thuộc phụ lục trước")
                    return
                }
                let data = {
                    hdttId: this.dsThueBaoChon[0].hdtt_id
                }
                let res = await this.$root.context.post('/web-hopdong/ChuanHoaDuLieu/xoa-hdtb-theo-hdtt-id', data)
                if (res.error_code == "BSS-00000000" && res.data) {
                    this.$toast.success("Xóa thuê bao thành công")
                }
                await this.HienThiDanhSacHDTT()
                await this.HienThiDanhSacHDTB()
            } catch (e) {
                console.log(e)
            } finally {
                this.$root.showLoading(false);
            }
        },

        XoaHDKH: async function () {
            try {
                let res = await this.$root.context.post(
                    "/web-hopdong/hopdong/xoa_hdkh_v2", {
                    vhdkh_id: this.hdkh_id,
                    vloaihd_id: Enum.LoaiHopDong.KY_LAI_HD_GOC
                }
                )
                this.$toast.success("Xóa thành công!")
                this.setButton(0)
            } catch (ex) {
                console.log(ex)
            }
        },

        KiemTra_KH_KyHD_Goc: async function () {
            let res = await this.$root.context.get(`/web-hopdong/ChuanHoaDuLieu/kiemtra_kyhopdong_goc?maKH=${this.maKhachHang}`)
            if (res.data.length > 0) {
                if (res.data[0].ky_hd == 1)
                    return true
                else return false
            } else return false
        },

        LAY_DS_DIACHI_THEO_HDKH_ID: async function (hdkh_id) {
            let res = await this.$root.context.get(`/web-hopdong/chuyendich/lay_ds_diachi_theo_hdkh_id?hdkh_id=${hdkh_id}`);
            return res != null && res.data != null ? res.data : [];
        },

        LAY_DS_DIACHI_THEO_HDTB_ID: async function (hdtb_id) {
            let res = await this.$root.context.get(`/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_hdtbid?hdtbId=${hdtb_id}`);
            return res != null && res.data != null ? res.data : [];
        },

        LAY_DS_DIACHI_THEO_DBTBID: async function (thuebao_id) {
            let res = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_diachi_theo_thuebao?thueBaoId=${thuebao_id}`);
            return res != null && res.data != null ? res.data : [];
        },

        LAY_DS_DIACHI_THEO_DBTTID: async function (thanhtoan_id) {
            let res = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid?dbttid=${thanhtoan_id}`);
            return res != null && res.data != null ? res.data : [];
        },

        getLoaiGiayTo: async function () {
            let data = {
                table: 'LOAI_GT',
                bangChaId: null
            }
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
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsNganhNghe = res.data
        },

        onChangeQuocTich: async function () {
            await this.getDanToc()
        },

        getDanToc: async function () {
            let data = {
                table: 'DANTOC',
                bangChaId: this.quocTichId
            }
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
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsLoaiKhacHang = res.data
        },

        getNganHang: async function () {
            let data = {
                table: 'NGANHANG',
                bangChaId: null
            }
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsNganHang = res.data
            if (this.dsNganHang.length > 0) {
                this.nganHangId = this.dsNganHang[0].nganhang_id
            }
        },

        getDichVuVT: async function () {
            let data = {
                table: 'DICHVU_VT',
                bangChaId: null
            }
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsDichVuVT = res.data
            if (this.dsDichVuVT.length > 0) {
                this.dichvuvt_id = this.dsDichVuVT[0].dichvuvt_id
            }
        },

        getDsThamSoMacDinh: async function () {
            let data = {
                kieuId: 0
            }
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_thamso_macdinh', data)
            this.phantap_kh = res.data.find(x => x.ma_ts == "PHANTAP_KH") != null && res.data.find(x => x.ma_ts == "PHANTAP_KH").ten_ts == 1
        },

        setButton: function (kieu) {
            this.isDisableBtnNhapMoi = true
            this.isDisableBtnGhiLai = true
            this.isDisableBtnXoaHD = true
            this.isDisableBtnHuy = true
            this.isDisableBtnThemTB = true
            this.isDisableBtnHoanThien = true
            this.isDisableBtnInHDTieuDe = true
            this.isDisableBtnInHDTrang = true
            // this.isDisableBtnXoaTB=true
            this.isDisableBtnChamSocKH = true

            if (kieu == -1) { //Bat dau
                this.$refs.maThueBaoInput.focus()
                this.isDisableBtnGhiLai = false
                this.isDisableBtnHuy = false
                this.isDisableBtnXoaTB = true
                this.isCheckCTV = false
                this.isCheckNGT = false
                this.nguoiGioiThieu = null
                this.congTacVien = null
            }

            if (kieu == 0) { //Bat dau
                this.$refs.maThueBaoInput.focus()
                this.isDisableBtnNhapMoi = false
                this.isDisableBtnXoaTB = true
                this.clear()
            }

            if (kieu == 1) { //Them moi
                this.$refs.maThueBaoInput.focus()
                this.isDisableBtnGhiLai = false
                this.isDisableBtnHuy = false
                this.isDisableBtnXoaTB = true
                this.isCheckCTV = false
                this.isCheckNGT = false
                this.nguoiGioiThieu = null
                this.congTacVien = null
                this.clear()
            }

            if (kieu == 2) { //Huy
                this.$refs.maGiaoDichInput.focus()
                this.isDisableBtnNhapMoi = false
                this.isDisableBtnXoaHD = false
                this.isDisableBtnXoaTB = true
                this.clear()
            }

            if (kieu == 3) { //Edit
                this.$refs.maGiaoDichInput.focus()
                this.isDisableBtnNhapMoi = false
                this.isDisableBtnXoaHD = false
                this.isDisableBtnGhiLai = false
                this.isDisableBtnHuy = false
                this.isDisableBtnThemTB = false
                this.isDisableBtnHoanThien = false
                this.isDisableBtnInHDTieuDe = false
                this.isDisableBtnInHDTrang = false
            }
        },

        clear: function () {
            this.pt_diem_tn = 0
            this.pt_doanhthu = 0
            this.strerrorVinaphone = null
            this.dacTrung = null
            this.kt_kylai = false
            this.khachhang_id = 0
            this.diachikh_id = 0
            this.tinhkh_id = 0
            this.quankh_id = 0
            this.phuongkh_id = 0
            this.dacdiemkh_id = 0
            this.phokh_id = 0
            this.apkh_id = 0
            this.khukh_id = 0
            this.sonhakh = null
            this.dsHDKH = []
            // this.khlon_id = null
            // this.dsDiaChi = null;
            // this.dsDiaChiHDKH = null;
            // this.dtHDTB = null;
            this.chucVu = null
            this.diaChiNDD = null
            this.diaChiKhachHang = null
            this.ctv_id = 0
            this.nguoigt_id = 0
            this.fax = null
            this.hoKhau = null
            this.maHopDong = null
            this.maKhachHang = null
            this.ngayLapHD = new Date()
            this.ngayYeuCau = new Date()
            this.maSoThue = null
            this.ngayCapGT = null
            this.ngayCapHK = null
            this.ngayHetHan = null
            this.ngaySinh = null
            this.nguoiDaiDien = null
            this.noiCapGT = null
            this.noiCapHK = null
            this.dienThoai = null
            this.soGiayTo = null
            this.taiKhoan = null
            this.tenKhachHang = null
            this.ghiChu = null
            this.maGiaoDich = null
            this.quocTichId = this.dsQuocTich[0].quoctich_id
            this.isCheckQT = false
            //this.danTocId = this.dsDanToc[0].DANTOC_ID
            this.isCheckDT = false
            this.selectCboRow(null);
            this.gioiTinh = 0
            this.loaiGiayToId = this.dsLoaiGiayTo[0].loaigt_id
            this.setNganhNgheCombobox(this.dsNganhNghe[0].nganhnghe_id)
            this.dsThueBao = []
            this.$nextTick(function () {
                this.$refs.maThueBaoInput.focus()
            })
            this.nganHangId = this.dsNganHang[0].nganhang_id
            this.isCheckNH = false
            this.dsThanhToan = []
            this.trangThaiDuLieu = null
            this.tongSoTT = 0
            this.tongSoTB = 0
            this.ten_kh_Mytv = null
            this.ngay_sn_Mytv = null
            this.gioitinh_Mytv = null
            this.diachi_kh_Mytv = null
            this.so_gt_Mytv = null
            this.email_Mytv = null
            this.sofax_Mytv = null
            this.matb_Mytv = null
            this.hdkh_id = null
            this.kt_tbdd = null
            this.kylai = null
            this.TNC1_ID = 0
            this.TNC2_ID = 0
            this.TNC3_ID = 0
            this.TNC4_ID = 0
            this.$v.$reset();
            this.customError = {
                ngaySinh: false,
                dienThoai: false,
                maKH: false,
                diaChiKhachHang: false,
                quocTich: false,
                danToc: false,
                maGD: false
            }
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
        },
        onChangeDichVuVT: async function () {

        },
        async getOriginal(url) {
            var instance = axios.create();
            instance.interceptors.request.use(request => {
                request.baseURL = process.env.API;
                request.headers = {
                    "Content-Type": "application/json",
                    Authorization: token.getAuthorization()
                };
                request.data = {};
                request.responseType = 'blob';
                return request;
            });
            var begin = Date.now();
            var r = await instance.get(url);
            if (process.env.NODE_ENV !== "production") {
                console.info(
                    "url: " + url + " (" + (Date.now() - begin) + "ms)"
                );
            }
            return r;
        }
    },
    // mounted: async function () {
    //     try {
    //         this.loading(true)
    //         this.initForm()
    //     } catch (error) {

    //     } finally {
    //         this.loading(false);
    //     }
    // },
    watch: {
        quocTichId: async function () {
            await this.getDanToc()
        },
        nganhnghe_id: function (newVal, oldVal) {
            if (this.isShowPopupNganhNghe) {
                let index = this.dsNganhNghe.findIndex(e => Number(e.nganhnghe_id) === Number(newVal));
                if (index > -1) {
                    this.TNC1_ID = 0;
                    this.TNC2_ID = 0;
                    this.TNC3_ID = 0;
                    this.TNC4_ID = 0;
                    this.nganhNgheClick();
                }
            } else this.isShowPopupNganhNghe = true;
        },
        loaiKhachHangId: function (newVal, oldVal) {
            let loaiKH = this.dsLoaiKhacHang.find(e => Number(e.loaikh_id) === Number(newVal));
            if (newVal && loaiKH != null) {
                this.txtPLKH = loaiKH.ma_loaikh;
            }
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

.modal .page-content {
    position: inherit;
}
</style>
