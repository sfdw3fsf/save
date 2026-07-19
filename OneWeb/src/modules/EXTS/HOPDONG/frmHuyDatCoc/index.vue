<template src="./index.template.html"></template>
<script>
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import NguoiGioiThieu from "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
import ThongTinEmail from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue";
import ThongTinLienHe from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue";
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
// import FrmThanhToan from '../ThayDoiDatCoc/ThanhToanHopDong'
import ccbsService from './service/ccbs.js'
import invoiceService from './service/invoice.js'
import convert from 'xml-js';
import Vue from "vue";
import { NumericTextBoxPlugin } from "@syncfusion/ej2-vue-inputs";
import DateTimeLib from '@/utils/DateTimeLib';
Vue.use(NumericTextBoxPlugin);

const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')

export default {
    components: {
        BssErrorMarker,
        BssRequiredMarker,
        SearchContractModal,
        SearchAccount,
        NguoiGioiThieu,
        ThongTinEmail,
        ThongTinLienHe,
        // FrmThanhToan
    },
    computed: {
        HDDT_THONGTU_78() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'HDDT_THONGTU_78' && thamSo.ten_ts == '1' }) ? 1 : 0
        },
        TINHCUOC_NGAY() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'TINHCUOC_NGAY' && thamSo.ten_ts == '1' }) ? 1 : 0
        },
        dscheck_thoai() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'TINHTIEN_THOAI_MOI' }) ? 1 : 0
        },
        _KHONG_VUOTQUA_TIENTHOAI() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'KHONG_VUOTQUA_TIENTHOAI' }) ? 1 : 0
        },
        _KHONG_SUA_TIENTHOAI() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == '_KHONG_SUA_TIENTHOAI' }) ? 1 : 0
        },
        check_chuahoa_cntt() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'CHECK_CHUANHOA_CNTT' }) ? 1 : 0
        },
        _MACDINH_THOAIDC_LUITHANG() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'MACDINH_THOAIDC_LUITHANG' }) ? 1 : 0
        },
        ts_sinhma_gd_theo_donvi() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'SINH_MA_GD_THEO_DV' })
        },
        huy_nhieu_tb() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'HUY_DATCOC_NHIEU_TB' })
        },
        batbuoc_gioithieu_thaydoi_datcoc() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'BATBUOC_GIOITHIEU_THAYDOI_DATCOC' && thamSo.ten_ts == '1' }) ? 1 : 0
        },
        huy_datcoc_theothang() {
            return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'HUY_DATCOC_THEOTHANG' })
        },
        IS_DICHVU_CNTT: function () {
            return [7,8,9,13,14,15,16].includes(this.dichvuvt_id)
            // return (this.dichvuvt_id == 13 || 
            //         this.dichvuvt_id == 14 || 
            //         this.dichvuvt_id == 15 || 
            //         this.dichvuvt_id == 16);
        }
    },
    data() {
        return {
            userComponentModule: null,
            txtMaGDTT: "",
            popupComponentEvts: {
                paymentcreated: this.paymentcreated
            },
            DATCOC: {
                MA_DATCOC_GOICUOC: "CCBS_TRATRUOC_GOICUOC", // HTH thêm trả trước gói cước DD
                MA_CAMKET_CHONSO: "CCBS_CAMKET_CHONSO",     // HTH thêm trả trước cam ket chon so DD
                MA_DATCOC_TRATRUOC: "CCBS_TRATRUOC_CUOC_TB",
                MA_TAMTHU_CUOCNONG: "CCBS_TAMTHU_CUOCNONG", // HYN mã thêm trả trước tạm thu"
            },
            LoaiHinhTB: {
                DIDONGTRASAU: 20
            },
            TRANGTHAI_DONGBO: {
                OPEN_SYNC_VINA_TRASAU: false,
                DONGBO_CM: 5
            },
            TrangThaiHD: {
                MOI: 1
            },
            LoaiHopDong: {
                THAYDOI_DATCOC: 32,
                DATCOC_MOI: 31
            },
            KHOANMUC_TT: {
                KMTT_DATCOC: 11 //Tiền đặt cọc
            },
            DichVuVienThong: {
                ADSL: 4,
                DICHVU_CNTT: 15,
                ANTOAN_BAOMAT_TT: 14,
                TRUNGTAM_DULIEU: 16,
                HOINGHI_TRUYENHINH: 13
            },
            cboLoaiHinhTB: {
                DataSource: [],
                Value: null
            },
            cboKieuLD: {
                DataSource: [],
                Value: null
            },
            cboDichVuVT: {
                DataSource: [],
                Value: null
            },
            cboGoiCuocAdsl: {
                Text: null,
                Value: null
            },
            dgvDatCoc: {
                DataSource: [],
                EnabledSelectFirstRow: true
            },
            dgvDanhSach: {
                DataSource: []
            },
            txtTienThoaiTra: {
                Text: "0",
                Enabled: true,
                hasError: false,
                allowChange: true
            },
            txtMaGD: {
                Text: null
            },
            dtpNgayThoai: {
                Value: null,
                hasError: false
            },
            dtpThangThoaiTra: {
                Value: null,
                ActiveEvent: true
            },
            txtMaKH: {
                Text: null
            },
            txtTenKH: {
                Text: null
            },
            txtDiaChiKH: {
                Text: null
            },
            txtMaTB: {
                Text: null,
                hasError: false
            },
            dtpNgayYeuCau: {
                Value: null
            },
            dtpNgayLHD: {
                Value: null
            },
            txtGhiChu: {
                Text: ""
            },
            txtNguoiGT: {
                Text: null,
                hasError: false
            },
            chkNguoiGT: {
                Checked: false,
                ActiveEvent: true
            },
            tsbtnNhapMoi: {
                Enabled: true
            },
            tsbtnGhiLai: {
                Enabled: true
            },
            tsbtnHuyBo: {
                Enabled: true
            },
            tsbtnXoa: {
                Enabled: true
            },
            tsbtnThanhToan: {
                Enabled: true
            },
            tsbtnCapNhatDB: {
                Enabled: true
            },
            dsHDKH: {
                HD_KHACHHANG: [],
                XML: null
            },
            dsHDTB: {
                HD_THUEBAO: [],
                XML: null
            },
            hdtb_dc: {
                HDTB_DATCOC: [],
                XML: null
            },
            dsCTTHD: {
                CT_TIENHD: [],
                XML: null
            },
            ma_kh: null,
            ten_kh: null,
            khachhang_id: 0,
            khlon_id: 0,
            nguoigt_id: 0,
            hdkh_id: 0,
            dichvuvt_id: 0,
            thuebao_id: 0,
            dsThamSoMacDinh: [],
            ten_tb: null,
            diachi_ld: null,
            loaitb_id: 0,
            donvi_id: 0,
            doituong_id: 0,
            nhieu_tb: false,
            loi_sinh_magd: false,
            hdtb_id: 0,
            diachi_kh: null,
            thuebao_thoaitra_id: 0,
            rkm_id: 0,
            nhom_datcoc_id_cu: 0,
            thang_bd_cu: 0,
            thang_kt_cu: 0,
            thuebao_dc_id: 0,
            CT_TIENHD_ID: 0,
            hdtbDuocChon: null,
            datCocDuocChon: null,
            tthd_id: 0,
            ttdc_id: 0,
            cuoc_dc_cu: 0,
            tien_td_cu: 0,
            _INTngay_yc: 0,
            _thoaitra: 0,
            _tienthoai: 0,
            tongtien_hddt: 0,
            tongvat_hddt: 0,
            tong_hddt: 0,
            tbc: "Không đồng",
            ngayDC: {
                BD: null,
                KT: null,
            },
            PHAN_BO: 0
        }
    },
    watch: {
        "cboDichVuVT.Value": async function () {
            await this.cboDichVuVT_SelectedIndexChanged()
        },
        "cboLoaiHinhTB.Value": function () {
            this.cboLoaiHinhTB_SelectedValueChanged()
        },
        "chkNguoiGT.Checked": function () {
            this.chkNguoiGT_CheckedChanged()
        },
        "dtpThangThoaiTra.Value": async function () {
            await this.dtpThangThoaiTra_ValueChanged()
        },
        "txtNguoiGT.Text": function (value) {
            if (!!value) {
                this.txtNguoiGT.hasError = false
            }
        },
        "txtMaTB.Text": function (value) {
            if (!!value) {
                this.txtMaTB.hasError = false
            }
        },
        "txtTienThoaiTra.Text": function (value) {
            if (!!value) {
                this.txtTienThoaiTra.hasError = false
            }
        },
        "dtpNgayThoai.Value": async function (value) {
            await this.dtpNgayThoai_change();
            if (!!value) {
                this.dtpNgayThoai.hasError = false
            }
        },
        tthd_id(value) {
            this.tsbtnThanhToan.Enabled = parseInt(value) == 1
            this.tsbtnCapNhatDB.Enabled = parseInt(value) == 2
        }
    },
    async mounted() {
        this.customizeVueNumeric()
        await this.frmThayDoiDatCoc_Load()
    },
    methods: {
        async loadKieuLD() {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ds_kieu_ld`)
                this.cboKieuLD.DataSource = !response.data ? [] : response.data
            } catch (err) {
                this.cboKieuLD.DataSource = []
            }

            if (this.cboKieuLD.DataSource.length > 0) {
                this.cboKieuLD.Value = this.cboKieuLD.DataSource[0].kieuld_id + ""
            }
        },
        setDgvDatCocDataSource(dataSource) {
            this.datCocDuocChon = null
            this.dgvDatCoc.DataSource = dataSource
        },
        Clear() {
            this.dsHDKH.HD_KHACHHANG = []
            this.dsHDKH.XML = null
            this.dsHDTB.HD_THUEBAO = []
            this.dsHDTB.XML = null
            this.hdtb_dc.HDTB_DATCOC = []
            this.hdtb_dc.XML = null
            this.dsCTTHD.CT_TIENHD = []
            this.dsCTTHD.XML = null
            this.ma_kh = null
            this.ten_kh = null
            this.khachhang_id = 0
            this.khlon_id = 0
            this.nguoigt_id = 0
            this.hdkh_id = 0
            this.dichvuvt_id = 0
            this.thuebao_id = 0
            this.ten_tb = null
            this.diachi_ld = null
            this.loaitb_id = 0
            this.donvi_id = 0
            this.doituong_id = 0
            this.nhieu_tb = false
            this.loi_sinh_magd = false
            this.hdtb_id = 0
            this.diachi_kh = null
            this.thuebao_thoaitra_id = 0
            this.rkm_id = 0
            this.nhom_datcoc_id_cu = 0
            this.thang_bd_cu = 0
            this.thang_kt_cu = 0
            this.thuebao_dc_id = 0
            this.CT_TIENHD_ID = 0
            this.hdtbDuocChon = null
            this.tthd_id = 0
            this.ttdc_id = 0
            this.cuoc_dc_cu = 0
            this.tien_td_cu = 0
            this._INTngay_yc = 0
            this._thoaitra = 0
            this._tienthoai = 0
            this.tongtien_hddt = 0
            this.tongvat_hddt = 0
            this.tong_hddt = 0
            this.tbc = "Không đồng"
            this.cboKieuLD.Value = this.cboKieuLD.DataSource[0].kieuld_id
            this.cboDichVuVT.Value = this.cboDichVuVT.DataSource[0].DICHVUVT_ID
            this.setDgvDatCocDataSource([])
            this.dgvDatCoc.EnabledSelectFirstRow = true
            this.dgvDanhSach.DataSource = []
            this.txtTienThoaiTra.Text = 0
            this.txtTienThoaiTra.Enabled = true
            this.txtTienThoaiTra.hasError = false
            this.txtTienThoaiTra.allowChange = true
            this.txtMaGD.Text = ""
            this.dtpNgayThoai.Value = null
            this.dtpNgayThoai.hasError = false
            this.dtpThangThoaiTra.ActiveEvent = false
            this.dtpThangThoaiTra.Value = new Date()
            this.txtMaKH.Text = ""
            this.txtTenKH.Text = ""
            this.txtDiaChiKH.Text = ""
            this.txtMaTB.Text = ""
            this.txtMaTB.hasError = false
            this.dtpNgayLHD.Value = null
            this.txtGhiChu.Text = ""
            this.txtNguoiGT.Text = ""
            this.txtNguoiGT.hasError = false
            this.chkNguoiGT.Checked = false
            this.chkNguoiGT.ActiveEvent = true
        },
        resetValidation() {
            this.txtNguoiGT.hasError = false
            this.txtMaTB.hasError = false
            this.txtTienThoaiTra.hasError = false
            this.dtpNgayThoai.hasError = false
        },
        customizeVueNumeric() {
            let component = this.$refs.txtTienThoaiTra

            component.formatOld = component.format.bind(component)
            component.format = (function (value) {
                let formattedValue = this.formatOld(value)
                let beforeDot = formattedValue.split(".")[0]
                let afterDot = formattedValue.split(".")[1]
                let decimalValue = parseFloat("0." + afterDot)

                if (decimalValue == 0) {
                    return beforeDot
                }

                return beforeDot + "." + `${decimalValue}`.substr(2)
            }).bind(component)

            component.onFocusHandlerOld = component.onFocusHandler.bind(component)
            component.onFocusHandler = (function (e) {

                this.onFocusHandlerOld(e)

                if (!!this.amount) {
                    let beforeDot = this.amount.split(".")[0]
                    let afterDot = this.amount.split(".")[1]
                    let decimalValue = parseFloat("0." + afterDot)

                    if (decimalValue == 0) {
                        this.amount = beforeDot
                    } else {
                        this.amount = beforeDot + "." + `${decimalValue}`.substr(2)
                    }
                }
            }).bind(component)
        },
        async frmThayDoiDatCoc_Load() {
            let ngayCn = await this.lay_ngay_cn();
            this.dtpNgayYeuCau.Value = new Date(ngayCn)
            this.SetButton(-1)

            await this.HT_DichVuVT_Combobox_LM()
            await this.loadKieuLD()
            await this.LAY_DS_THAMSO_MD()

            this.dtpThangThoaiTra.ActiveEvent = false

            if (this._MACDINH_THOAIDC_LUITHANG == 0) {
                this.dtpThangThoaiTra.Value = new Date()
            } else {
                let thangThoaiTraTmp = new Date()
                thangThoaiTraTmp.setDate(1)
                thangThoaiTraTmp.setMonth(thangThoaiTraTmp.getMonth() - 1)
                this.dtpThangThoaiTra.Value = thangThoaiTraTmp
            }

            if (this._KHONG_SUA_TIENTHOAI == 1) {
                this.txtTienThoaiTra.Enabled = false
            } else {
                this.txtTienThoaiTra.Enabled = true
            }
        },
        async LAY_DS_THAMSO_MD() {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ds_thamso_md`)
                this.dsThamSoMacDinh = !response.data ? [] : response.data
            } catch (err) {
                this.dsThamSoMacDinh = []
            }
        },
        async HT_DichVuVT_Combobox_LM() {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_dichvu_vt`)
                this.cboDichVuVT.DataSource = !response.data ? [] : response.data
            } catch (err) {
                this.cboDichVuVT.DataSource = []
            }

            if (this.cboDichVuVT.DataSource.length > 0) {
                this.cboDichVuVT.Value = this.cboDichVuVT.DataSource[0].DICHVUVT_ID + ""
            }
        },
        btnLayMaGD_Click() {
            this.$refs.popupSearchContract.showModal()
        },
        async acceptSearchContract(item) {
            this.txtMaGD.Text = !item || !item.ma_gd ? null : item.ma_gd
            this.$refs.refMaGD.focus()

            if (!!this.txtMaGD.Text) {
                await this.txtMaGD_KeyPress()
            }
        },
        async txtMaGD_KeyPress() {

            if (!this.txtMaGD.Text) {
                return
            }

            this.resetValidation()

            try {
                this.loading(true)
                let thongTinHopDong = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.txtMaGD.Text)
                await this.HienThiTTHopDongKH(thongTinHopDong)
            } finally {
                this.loading(false)
            }
        },
        async getDsHDChuaHoanThien(thueBaoID) {
            try {
                let response = await this.$root.context.post("/web-hopdong/ThayDoiDatCoc/getDsHDChuaHoanThien", { id: thueBaoID })

                return response.data;
            } catch (err) {
                return []
            }
        },
        async LAY_DS_HOPDONG_THEO_MA_GD(maGiaoDich) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_hopdong_theo_ma_gd`, { ma_gd: maGiaoDich })

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async HienThiTTHopDongKH(thongTinHopDong) {

            if (!thongTinHopDong) {
                this.dgvDanhSach.DataSource = []
                this.SetButton(0)
                return
            }

            this.hdkh_id = parseInt(thongTinHopDong["HDKH_ID"])
            this.txtMaGD.Text = thongTinHopDong["MA_GD"]
            this.txtMaKH.Text = this.ma_kh = thongTinHopDong["MA_KH"]
            this.khachhang_id = !thongTinHopDong["KHACHHANG_ID"] ? 0 : parseInt(thongTinHopDong["KHACHHANG_ID"])
            this.khlon_id = parseInt(thongTinHopDong["KHLON_ID"])
            this.txtTenKH.Text = thongTinHopDong["TEN_KH"]
            this.txtDiaChiKH.Text = thongTinHopDong["DIACHI_KH"]
            this.ten_kh = thongTinHopDong["TEN_KH"]
            this.diachi_kh = thongTinHopDong["DIACHI_KH"]
            this.txtGhiChu.Text = !thongTinHopDong["GHICHU"] ? '' : thongTinHopDong["GHICHU"]

            if (!!thongTinHopDong["NGAY_YC"]) {
                this.dtpNgayYeuCau.Value = new Date(thongTinHopDong["NGAY_YC"])
            }

            if (!!thongTinHopDong["NHANVIENGT_ID"]) {
                this.chkNguoiGT.ActiveEvent = false
                this.chkNguoiGT.Checked = true
                this.nguoigt_id = parseInt(thongTinHopDong["NHANVIENGT_ID"])
                this.txtNguoiGT.Text = await this.layTenNhanVienTheoId(this.nguoigt_id)
            } else {
                this.txtNguoiGT.Text = ""
                this.chkNguoiGT.Checked = false
            }

            this.SetButton(3)
            await this.HienThiDanhSacHDTB()
        },
        async HienThiDanhSacHDTB() {

            this.dgvDanhSach.DataSource = await this.Lay_DS_HDTB_THEO_HDKH(this.hdkh_id)

            if (this.dgvDanhSach.DataSource.length == 0) {
                this.SetButton(1)
            }
        },
        async Lay_DS_HDTB_THEO_HDKH(hdkh_id) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_hdtb_theo_hdkh?hdkh_id=${hdkh_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    let rootKey = this.ddmmyyyyhhmiss(new Date())
                    response.data.forEach((item, index) => item.KEY = `${rootKey}${index + 1}`)
                    return response.data
                }

                throw { response: { data: response } }
            } catch (err) {
                return []
            }
        },
        async layTenNhanVienTheoId(nhanVienId) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ten_nv_theo_id?nhanvien_id=${nhanVienId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.TEN_NV
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async HienThiTT_DanhBa(ma_tb) {

            let ngayCn = await this.lay_ngay_cn();
            let date = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");

            if (this._MACDINH_THOAIDC_LUITHANG == 1) {
                date.setDate(1)
                date.setMonth(date.getMonth() - 1)
            }

            this.dtpThangThoaiTra.Value = date
            if (this.TINHCUOC_NGAY == 1) {
                this.dtpNgayThoai.Value = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                this.dtpThangThoaiTra.Value = this.dtpNgayThoai.Value;
            }
            this.dichvuvt_id = parseInt(this.cboDichVuVT.Value)

            let thongTinDanhBa = await this.LAY_DANHBA_THEO_MATB({
                ma_tb: ma_tb,
                dichvuvt_id: this.dichvuvt_id
            })

            if (!thongTinDanhBa) {
                let dichVu = this.cboDichVuVT.DataSource.find(dichVu => dichVu.DICHVUVT_ID == this.dichvuvt_id)
                this.$root.toastError(`Không tìm thấy thông tin về mã thuê bao ${this.txtMaTB.Text} đối với dịch vụ ${dichVu.TEN_DVVT}`)
                return
            }
            else {
                if (thongTinDanhBa.TRANGTHAITB_ID === 7) {
                    this.$root.toastError(`Mã thuê bao ${this.txtMaTB.Text} này đang ở trạng thái thanh lý.`);
                    return;
                }
            }

            this.thuebao_id = parseInt(thongTinDanhBa["THUEBAO_ID"])
            this.txtMaKH.Text = this.ma_kh = thongTinDanhBa["MA_KH"]

            if (!thongTinDanhBa["NGAYLAP_HD"]) {
                this.dtpNgayLHD.Value = new Date()
            } else {
                this.dtpNgayLHD.Value = new Date(thongTinDanhBa.NGAYLAP_HD)
            }

            this.khachhang_id = parseInt(thongTinDanhBa["KHACHHANG_ID"])
            this.ten_kh = thongTinDanhBa["TEN_KH"]
            this.diachi_kh = thongTinDanhBa["DIACHI_KH"]
            this.txtTenKH.Text = thongTinDanhBa["TEN_TB"]
            this.txtDiaChiKH.Text = thongTinDanhBa["DIACHI_KH"]
            this.txtMaTB.Text = thongTinDanhBa["MA_TB"]
            this.ten_tb = thongTinDanhBa["TEN_TB"]
            this.diachi_ld = thongTinDanhBa["DIACHI_LD"]
            this.loaitb_id = parseInt(thongTinDanhBa["LOAITB_ID"])
            this.cboLoaiHinhTB.Value = this.loaitb_id + ""
            this.donvi_id = parseInt(thongTinDanhBa["DONVIQL_ID"])
            this.doituong_id = parseInt(thongTinDanhBa["DOITUONG_ID"])
            this.khlon_id = parseInt(thongTinDanhBa["KHLON_ID"])

            if (parseInt(this.cboDichVuVT.Value) == this.DichVuVienThong.ADSL) {
                this.cboGoiCuocAdsl.Value = thongTinDanhBa["TOCDO_ID"] + ""
            }

            let dt = []

            if (this.huy_nhieu_tb) {

                dt = await this.LAY_DS_HOPDONG_TBAO(ma_tb)

                if (dt.length > 1) {
                    this.$root.toastError("Có thuê bao cùng hợp đồng, sẽ hủy đặt cọc cả 2 thuê bao")
                    this.nhieu_tb = true
                }
            }

            this.hdtb_id = 0

            if (this.nhieu_tb) {
                await this.HienThiTT_Datcoc_2(dt)
            } else {
                this.thuebao_thoaitra_id = 0
                this.rkm_id = 0
                this.dgvDatCoc.EnabledSelectFirstRow = (this.dgvDanhSach.DataSource == null || this.dgvDanhSach.DataSource == undefined || this.dgvDanhSach.DataSource.length == 0)
                await this.HienThiTT_Datcoc()
            }
        },
        async LAY_DS_HOPDONG_TBAO(ma_tb) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/lay_ds_hopdong_tbao`, { ma_tb })
            return !response.data ? [] : response.data
        },
        async LAY_DANHBA_THEO_MATB(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_danhba_theo_matb`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async HienThiTT_Datcoc() {
            let dataSource = await this.lay_ds_datcoc_theo_tbid({
                thuebao_id: this.thuebao_id,
                thang_ht: this.yyyymm(new Date())
            })
            this.setDgvDatCocDataSource(dataSource)
        },
        async HienThiTT_Datcoc_2(dt) {

            let tmp = []
            let dtDatCoc = []

            for (let i = 0; i < dt.length; i++) {

                tmp = await this.lay_ds_datcoc_theo_tbid({
                    thuebao_id: dt[i]["thuebao_id"],
                    thang_ht: this.yyyymm(new Date())
                })

                if (tmp.length > 0) {
                    for (let j = 0; j < tmp.length; j++) {
                        tmp[j]["MA_TB"] = dt[i]["ma_tb"]
                        dtDatCoc.push({ ...tmp[j] })
                    }
                }
            }

            this.setDgvDatCocDataSource(dtDatCoc)
        },
        async lay_ds_datcoc_theo_tbid(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_ds_datcoc_theo_tbid`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    let rootKey = this.ddmmyyyyhhmiss(new Date())
                    response.data.forEach((item, index) => {
                        item.KEY = `${rootKey}${index + 1}`
                        item.CUOC_DC_HT = this.numberFormat(item.CUOC_DC)
                        item.TIEN_TD_HT = this.numberFormat(item.TIEN_TD)
                        item.TIEN_TB_HT = this.numberFormat(item.TIEN_TB)
                        item.TIEN_SD_HT = this.numberFormat(item.TIEN_SD)
                        item.TIEN_THOAI_HT = this.numberFormat(item.TIEN_THOAI)
                    })
                    return response.data
                }

                throw { response: { data: response } }
            } catch (err) {
                return []
            }
        },
        bntLayMaKh_Click() {
            this.$refs['popupSearchAccount'].show()
        },
        acceptSearchAccount(item) {

            if (!!item && !!item.ma_tb) {
                this.txtMaTB.Text = item.ma_tb
                this.$refs.txtMaTB.focus()
                this.txtMaTB_KeyPress()
            }

            this.$refs['popupSearchAccount'].hide()
        },
        async txtMaTB_KeyPress() {

            if (!this.txtMaTB.Text) {
                return
            }

            this.resetValidation()

            let thongTinHopDong = null

            try {
                this.loading(true)
                //let ngayCn = await this.lay_ngay_cn();
                //this.dtpNgayThoai.Value = new Date(ngayCn);

                thongTinHopDong = await this.LAY_DS_HOPDONG_THEO_MA_TB({
                    ma_tb: this.txtMaTB.Text,
                    dichvuvt_id: this.dichvuvt_id
                })

                if (!!thongTinHopDong) {
                    await this.HienThiTTHopDongKH(thongTinHopDong)
                } else {
                    await this.HienThiTT_DanhBa(this.txtMaTB.Text)
                }
            } finally {
                this.loading(false)
            }
        },
        async LAY_DS_HOPDONG_THEO_MA_TB(data) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_hopdong_theo_ma_tb`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async TaoDuLieu(themmoi) {

            await this.TaoDuLieu_HDKH(themmoi)

            if (this.loi_sinh_magd) {
                return
            }

            if (this.nhieu_tb) {
                await this.TaoDuLieu_HDTB_v2(themmoi)
                await this.TaoDuLieu_HDTB_DATCOC_v2(themmoi)
                await this.TaoDuLieu_CT_TIENHD_v2(themmoi)
            } else {
                await this.TaoDuLieu_HDTB(themmoi)
                await this.TaoDuLieu_HDTB_DATCOC(themmoi)
                await this.TaoDuLieu_CT_TIENHD(themmoi)
            }
        },
        async TaoDuLieu_HDKH(themmoi) {

            if (themmoi == true) {

                let vmagd_temp = await this.Lay_Ma_GD()

                if (vmagd_temp.includes("ERROR:")) {
                    this.$root.toastError(vmagd_temp.split(':')[1])
                    this.loi_sinh_magd = true
                    return
                }

                this.txtMaGD.Text = vmagd_temp
                this.hdkh_id = await this.GetKey('HD_KHACHHANG')
            }

            let khachhang_id = this.khachhang_id > 0 ? this.khachhang_id : null
            let ngay_yc = this.ddmmyyyy(this.dtpNgayYeuCau.Value)
            let nhanviengt_id = this.chkNguoiGT.Checked && !!this.txtNguoiGT.Text ? this.nguoigt_id : ""

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")
            let node = doc.createElement("item")

            node.setAttribute("hdkh_id", this.hdkh_id)
            node.setAttribute("ma_gd", this.txtMaGD.Text)
            node.setAttribute("ma_kh", this.ma_kh)
            node.setAttribute("khachhang_id", khachhang_id)
            node.setAttribute("khlon_id", this.khlon_id)
            node.setAttribute("ten_kh", this.ten_kh)
            node.setAttribute("diachi_kh", this.diachi_kh)
            node.setAttribute("ngaylap_hd", this.ddmmyyyy(this.dtpNgayLHD.Value))
            node.setAttribute("ngay_yc", ngay_yc)
            node.setAttribute("ghichu", this.txtGhiChu.Text)
            node.setAttribute("donvi_id", this.$root.token.getDonViID())
            node.setAttribute("nhanvien_id", this.$root.token.getNhanVienID())
            node.setAttribute("loaihd_id", this.LoaiHopDong.THAYDOI_DATCOC)
            node.setAttribute("nguoi_cn", this.$root.token.getUserName())
            node.setAttribute("may_cn", this.$root.token.getUserName())
            node.setAttribute("ip_cn", this.$root.token.getUserName())
            node.setAttribute("nhanviengt_id", nhanviengt_id)

            root.appendChild(node)
            doc.appendChild(root)
            this.dsHDKH.XML = (new XMLSerializer()).serializeToString(doc)
        },
        async GetKey(key_name) {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/get_key`, { key_name })
            return response.data[0].key
        },
        async Lay_Ma_GD() {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/sinh_ma_gd`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.MA_GD
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async TaoDuLieu_HDTB(themmoi) {

            if (themmoi == true) {
                this.hdtb_id = await this.GetKey("HD_THUEBAO")
            }

            let thuebao_id = this.thuebao_id > 0 ? this.thuebao_id : null
            let kieuld_id = parseInt(this.cboKieuLD.Value)

            let dset = await this.LayHuongGiao_TiepNhan({
                loaihd_id: this.LoaiHopDong.THAYDOI_DATCOC,
                kieuld_id: kieuld_id
            })

            let huonggiao_id = 0
            let quytrinh_id = 0

            if (dset.length > 0) {
                huonggiao_id = parseInt(dset[0]["huonggiao_id"]);
                quytrinh_id = parseInt(dset[0]["quytrinh_id"]);
            }

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")
            let node = doc.createElement("item")

            node.setAttribute("hdtb_id", this.hdtb_id)
            node.setAttribute("hdkh_id", this.hdkh_id)
            node.setAttribute("thuebao_id", thuebao_id)
            node.setAttribute("ma_tb", this.txtMaTB.Text)
            node.setAttribute("ten_tb", this.ten_tb)
            node.setAttribute("diachi_ld", this.diachi_ld)
            node.setAttribute("diachi_tb", this.diachi_ld)
            node.setAttribute("kieuld_id", kieuld_id)
            node.setAttribute("doituong_id", this.doituong_id)
            node.setAttribute("tthd_id", this.TrangThaiHD.MOI)
            node.setAttribute("loaitb_id", this.loaitb_id)
            node.setAttribute("dichvuvt_id", this.dichvuvt_id)
            node.setAttribute("donvi_id", this.donvi_id)
            node.setAttribute("nguoi_cn", this.$root.token.getUserName())
            node.setAttribute("may_cn", this.$root.token.getUserName())
            node.setAttribute("ghichu", this.txtGhiChu.Text)
            node.setAttribute("quytrinh_id", quytrinh_id)
            node.setAttribute("huonggiao_id", huonggiao_id)
            node.setAttribute("ngay_thoai", this.ddmmyyyy(this.dtpNgayThoai.Value))

            root.appendChild(node)
            doc.appendChild(root)
            this.dsHDTB.XML = (new XMLSerializer()).serializeToString(doc)
        },
        async LayHuongGiao_TiepNhan(data) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/lay_huonggiao_tiepnhan`, data)
            return !response.data ? [] : response.data
        },
        async TaoDuLieu_HDTB_v2(themmoi) {

            this.dsHDTB.HD_THUEBAO = []

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            console.log(this.dgvDatCoc.DataSource)

            for (let i = 0; i < this.dgvDatCoc.DataSource.length; i++) {

                let r = this.dgvDatCoc.DataSource[i]

                // let ds = await this.LAY_DANHBA_THEO_MATB({ ma_tb: r["MA_TB"], dichvuvt_id: this.dichvuvt_id })
                let ds = await this.LAY_DANHBA_THEO_MATB({ ma_tb: r["MA_TB"], dichvuvt_id: r.DICHVUVT_ID })

                if (themmoi == true) {
                    this.hdtb_id = await this.GetKey("HD_THUEBAO")
                }

                this.dsHDTB.HD_THUEBAO.push({ hdtb_id: this.hdtb_id })

                let thuebao_id = this.thuebao_id > 0 ? r["THUEBAO_ID"] : null
                let kieuld_id = parseInt(this.cboKieuLD.Value)

                let dset = await this.LayHuongGiao_TiepNhan({
                    loaihd_id: this.LoaiHopDong.THAYDOI_DATCOC,
                    kieuld_id: kieuld_id
                })

                let huonggiao_id = 0
                let quytrinh_id = 0

                if (dset.length > 0) {
                    huonggiao_id = parseInt(dset[0]["huonggiao_id"]);
                    quytrinh_id = parseInt(dset[0]["quytrinh_id"]);
                }

                let node = doc.createElement("item")
                node.setAttribute("hdtb_id", this.hdtb_id)
                node.setAttribute("hdkh_id", this.hdkh_id)
                node.setAttribute("thuebao_id", thuebao_id)
                node.setAttribute("ma_tb", r["MA_TB"])
                node.setAttribute("ten_tb", ds["TEN_TB"])
                node.setAttribute("diachi_ld", ds["DIACHI_LD"])
                node.setAttribute("diachi_tb", ds["DIACHI_LD"])
                node.setAttribute("kieuld_id", kieuld_id)
                node.setAttribute("doituong_id", parseInt(ds["DOITUONG_ID"]))
                node.setAttribute("tthd_id", this.TrangThaiHD.MOI)
                node.setAttribute("loaitb_id", parseInt(ds["LOAITB_ID"]))
                node.setAttribute("dichvuvt_id", this.dichvuvt_id)
                node.setAttribute("donvi_id", parseInt(ds["DONVIQL_ID"]))
                node.setAttribute("nguoi_cn", this.$root.token.getUserName())
                node.setAttribute("may_cn", this.$root.token.getUserName())
                node.setAttribute("ghichu", "")
                node.setAttribute("quytrinh_id", quytrinh_id)
                node.setAttribute("huonggiao_id", huonggiao_id)
                node.setAttribute("ngay_thoai", this.ddmmyyyy(this.dtpNgayThoai.Value))

                root.appendChild(node)
                
            }

            doc.appendChild(root)
            this.dsHDTB.XML = (new XMLSerializer()).serializeToString(doc)
        },
        async TaoDuLieu_HDTB_DATCOC(themmoi) {

            if (themmoi == true) {
                this.thuebao_dc_id = await this.GetKey("THUEBAO_DC_ID")
            }

            let hdtb_id = !themmoi ? this.hdtb_id : null

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")
            let node = doc.createElement("item")

            node.setAttribute("thuebao_dc_id", this.thuebao_dc_id)
            node.setAttribute("thuebao_thoaitra_id", this.thuebao_thoaitra_id)
            node.setAttribute("rkm_id", this.rkm_id)
            node.setAttribute("hdtb_id", this.hdtb_id)
            node.setAttribute("nhom_datcoc_id", this.nhom_datcoc_id_cu == null ? "" : this.nhom_datcoc_id_cu)
            node.setAttribute("thang_bd", this.thang_bd_cu)
            node.setAttribute("thang_kt", this.thang_kt_cu)
            node.setAttribute("thang_bd_mg", 0)
            node.setAttribute("thang_kt_mg", 0)
            node.setAttribute("ngay_dk", this.ddmmyyyy(this.dtpNgayYeuCau.Value))
            node.setAttribute("ghichu", this.txtGhiChu.Text)
            node.setAttribute("thang_thoaitra", this.yyyymm(this.dtpThangThoaiTra.Value))
            if (this.TINHCUOC_NGAY == 1)
                node.setAttribute("ngay_thoai", this.ddmmyyyy(this.dtpNgayThoai.Value));

            root.appendChild(node)
            doc.appendChild(root)
            this.hdtb_dc.XML = (new XMLSerializer()).serializeToString(doc)
        },
        async TaoDuLieu_HDTB_DATCOC_v2(themmoi) {

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            for (let i = 0; i < this.dgvDatCoc.DataSource.length; i++) {

                let r = this.dgvDatCoc.DataSource[i]

                if (themmoi == true) {
                    this.thuebao_dc_id = await this.GetKey("THUEBAO_DC_ID")
                }

                let node = doc.createElement("item")

                node.setAttribute("thuebao_dc_id", this.thuebao_dc_id)
                node.setAttribute("thuebao_thoaitra_id", parseInt(r["THUEBAO_DC_ID"]))
                node.setAttribute("rkm_id", parseInt(r["RKM_ID"]))
                console.log(this.dsHDTB.HD_THUEBAO[i]["hdtb_id"])
                node.setAttribute("hdtb_id", parseInt(this.dsHDTB.HD_THUEBAO[i]["hdtb_id"]))
                node.setAttribute("nhom_datcoc_id", r["NHOM_DATCOC_ID"] == null ? "" : parseInt(r["NHOM_DATCOC_ID"]))
                node.setAttribute("thang_bd", parseInt(r["THANG_BD"]))
                node.setAttribute("thang_kt", parseInt(r["THANG_KT"]))
                node.setAttribute("thang_bd_mg", 0)
                node.setAttribute("thang_kt_mg", 0)
                node.setAttribute("ngay_dk", this.ddmmyyyy(this.dtpNgayYeuCau.Value))
                node.setAttribute("ghichu", this.txtGhiChu.Text)
                node.setAttribute("thang_thoaitra", this.yyyymm(this.dtpThangThoaiTra.Value))
                if (this.TINHCUOC_NGAY == 1)
                    node.setAttribute("ngay_thoai", this.ddmmyyyy(this.dtpNgayThoai.Value));

                root.appendChild(node)
            }

            doc.appendChild(root)
            this.hdtb_dc.XML = (new XMLSerializer()).serializeToString(doc)
        },
        async TaoDuLieu_CT_TIENHD(themmoi) {

            if (themmoi == true) {
                this.CT_TIENHD_ID = await this.GetKey("CT_TIENHD")
            }

            let tien = Math.round(parseFloat(this.txtTienThoaiTra.Text) / 1.1)
            let vat = parseFloat(this.txtTienThoaiTra.Text) - tien

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")
            let node = doc.createElement("item")

            node.setAttribute("hdtb_id", this.hdtb_id)
            node.setAttribute("ct_tienhd_id", this.CT_TIENHD_ID)
            node.setAttribute("khoanmuctt_id", this.KHOANMUC_TT.KMTT_DATCOC)
            node.setAttribute("id", this.KHOANMUC_TT.KMTT_DATCOC)
            node.setAttribute("loai_id", 1)
            node.setAttribute("tien", -tien)
            node.setAttribute("vat", -vat)

            root.appendChild(node)
            doc.appendChild(root)
            this.dsCTTHD.XML = (new XMLSerializer()).serializeToString(doc)
        },
        async TaoDuLieu_CT_TIENHD_v2(themmoi) {

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            for (let i = 0; i < this.dgvDatCoc.DataSource.length; i++) {

                let r = this.dgvDatCoc.DataSource[i]

                
                if (themmoi == true) {
                    this.CT_TIENHD_ID = await this.GetKey("CT_TIENHD");
                }
                let tienthoai = 0;
                if (this.TINHCUOC_NGAY == 0)
                    tienthoai = await this.Lay_tienthoai_datcoc_v2({
                        rkm_id: this.rkm_id,
                        thuebao_dc_id: parseInt(r["THUEBAO_DC_ID"]),
                        thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value)
                    });
                else
                    tienthoai = await this.Lay_tienthoai_datcoc_v4({
                        rkm_id: this.rkm_id,
                        thuebao_dc_id: parseInt(r["THUEBAO_DC_ID"]),
                        thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value),
                        ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.Value)
                    });


                let tien = Math.round(tienthoai / 1.1)
                let vat = tienthoai - tien;

                let node = doc.createElement("item")
                node.setAttribute("hdtb_id", parseInt(this.dsHDTB.HD_THUEBAO[i]["hdtb_id"]))
                node.setAttribute("ct_tienhd_id", this.CT_TIENHD_ID)
                node.setAttribute("khoanmuctt_id", this.KHOANMUC_TT.KMTT_DATCOC)
                node.setAttribute("id", this.KHOANMUC_TT.KMTT_DATCOC)
                node.setAttribute("loai_id", 1)
                node.setAttribute("tien", -tien)
                node.setAttribute("vat", -vat)

                root.appendChild(node)
            }

            doc.appendChild(root)
            this.dsCTTHD.XML = (new XMLSerializer()).serializeToString(doc)
        },
        async Lay_tienthoai_datcoc_v2(data) {
            console.log('Lay_tienthoai_datcoc_v2')
            try {
                let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_tienthoai_datcoc`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    // console.log('response.data', response.data)
                    // this.txtTienThoaiTra.Text = response.data.TIENTHOAI
                    // console.log(this.txtTienThoaiTra.Text)
                    return response.data.TIENTHOAI
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async Lay_tienthoai_datcoc_v4(data) {
            console.log('Lay_tienthoai_datcoc_v4')
            try {
                if (this.IS_DICHVU_CNTT) {
                    return await this.Lay_tienthoai_datcoc_v2(data);
                }
                else {
                    let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_tienthoai_datcoc_v4`, data)

                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        // this.txtTienThoaiTra.Text = response.data.TIENTHOAI
                        // console.log(this.txtTienThoaiTra.Text)
                        return response.data.TIENTHOAI
                    }
                }
                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async cboDichVuVT_SelectedIndexChanged() {

            if (!this.cboDichVuVT.Value) {
                this.cboLoaiHinhTB.Value = null
                this.cboLoaiHinhTB.DataSource = []
                this.cboGoiCuocAdsl.Text = null
                this.cboGoiCuocAdsl.Value = null
                return
            }

            await this.HT_LoaiHinh_TB_DatMoi_Combobox(this.cboLoaiHinhTB, this.cboDichVuVT.Value)

            if (parseInt(this.cboDichVuVT.Value) != this.DichVuVienThong.ADSL) {
                this.cboGoiCuocAdsl.Text = null
                this.cboGoiCuocAdsl.Value = null
            }
        },
        async HT_LoaiHinh_TB_DatMoi_Combobox(cbo, dichVuId) {

            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_loaihinh_tb?dichvu_vt_id=${dichVuId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    cbo.DataSource = response.data
                } else {
                    throw { response: { data: response } }
                }
            } catch (err) {
                cbo.DataSource = []
            }

            if (cbo.DataSource.length > 0) {

                let hasValue = !!cbo.Value && cbo.DataSource.some(item => item.LOAITB_ID == parseInt(cbo.Value))

                if (!hasValue) {
                    cbo.Value = cbo.DataSource[0].LOAITB_ID + ""
                }
            }
        },
        async LAY_DS_THAMSO_MD_MATS(ma_ts) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ds_thamso_md_mats`, { ma_ts })
                return !response.data ? [] : response.data
            } catch (err) {
                return []
            }
        },
        async get_check_chuahoa_cntt(data) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/check_chuahoa_cntt`, data)
                return !response.data ? false : parseInt(response.data[0].KQ) == 1
            } catch (err) {
                return false
            }
        },
        async Kiemtra_Dulieu(themmoi) {

            let errorMessage = []
            let ngayCn = await this.lay_ngay_cn();

            if (!this.datCocDuocChon) {
                errorMessage.push('Không có thông tin đặt cọc cũ !')
            }

            let thanght = parseInt(this.yyyymm(DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss")))
            let phanVungId = this.$root.token.getPhanVungID()

            if (!(this.nhom_datcoc_id_cu === 2 && phanVungId === 26/*HPG*/)) {
                if (this.thang_kt_cu < thanght && this.thang_bd_cu > 0) {
                    errorMessage.push('Đặt cọc đã hết hạn không thay đổi được !')
                }
            }

            if (this.ttdc_id == 1) {
                errorMessage.push("Đặt cọc đã thoái trả không thay đổi được !");
            }

            if (this._thoaitra == 0) {
                errorMessage.push("Bạn không được thực hiện thoái trả đặt cọc đối với nhóm đặt cọc này. !");
            }

            //thực hiện kiểm tra có đủ điều kiện để thay đổi đặt cọc
            //áp dụng cho HGG; thực hiện tham số cho từng tỉnh
            //thamso_md :   HUY_DATCOC_THEOTHANG
            let dscheck_thamso = await this.LAY_DS_THAMSO_MD_MATS("HUY_DATCOC_THEOTHANG");

            if (dscheck_thamso.length > 0 && this._INTngay_yc != thanght) {
                errorMessage.push("Đặt cọc đã hết hạn không thay đổi được!. Chỉ được thay đổi trong tháng đặt cọc.");
            }

            if (!this.txtTienThoaiTra.Text && parseFloat(this.txtTienThoaiTra.Text) != 0) {
                errorMessage.push("Tiền thoái trả không được để trống !");
                this.txtTienThoaiTra.hasError = true
            }

            if (!!this.txtTienThoaiTra.Text && parseInt(this.txtTienThoaiTra.Text) > this._tienthoai && this._KHONG_VUOTQUA_TIENTHOAI > 0) {
                errorMessage.push("Không được nhập lớn hơn số tiền thoái. Hãy nhập lại.!");
                this.txtTienThoaiTra.hasError = true
            }

            if (!!this.txtTienThoaiTra.Text) {
                if (parseFloat(this.txtTienThoaiTra.Text) < 0) {
                    errorMessage.push("Tiền thoái phải nhập > 0");
                    this.txtTienThoaiTra.hasError = true
                }
            }

            if (!this.txtMaTB.Text) {
                errorMessage.push("Bạn chưa nhập số máy/account !");
                this.$refs.txtMaTB.focus();
                this.txtMaTB.hasError = true
            }

            let thoaitra = parseInt(this.yyyymm(this.dtpThangThoaiTra.Value))

            let date = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
            date.setDate(1)
            date.setMonth(date.getMonth() - 1)
            let thangtruoc = parseInt(this.yyyymm(date))

            if (!this.IS_DICHVU_CNTT) {

                if (thoaitra < thangtruoc) {
                    errorMessage.push("Tháng thoái trả không được nhỏ hơn tháng trước !");
                }
            }

            if (!(this.nhom_datcoc_id_cu == 2 && phanVungId == 26/*HPG*/)) {
                //Phuong trao doi voi Dung: cái này chỉ áp với cntt, mgwan, tsl thôi
                if (thoaitra > this.thang_kt_cu && this.thang_bd_cu > 0 && (this.IS_DICHVU_CNTT || this.dichvuvt_id == 7 || this.dichvuvt_id == 8 || this.dichvuvt_id == 9)) {
                    errorMessage.push("Tháng thoái trả không được lớn hơn tháng kết thúc đặt cọc cũ !");
                }
            }
            //#begin tinh cuoc ngay
            if (this.cboKieuLD.Value != 553) {

            }
            //#end tinh cuoc ngay
            if (themmoi) {
                let ck = await this.getDsHDChuaHoanThien(this.thuebao_id);
                if (ck.length > 0) {
                    this.$root.toastError("Thuê bao đã được có hợp đồng: " + ck[0].TEN_LOAIHD + " chưa hoàn thiện, bạn không thể lập hợp đồng!");
                    return false;
                }
            }

            let dichVuId = parseInt(this.cboDichVuVT.Value)

            if (dichVuId == this.DichVuVienThong.DICHVU_CNTT
                || dichVuId == this.DichVuVienThong.ANTOAN_BAOMAT_TT
                || dichVuId == this.DichVuVienThong.TRUNGTAM_DULIEU
                || dichVuId == this.DichVuVienThong.HOINGHI_TRUYENHINH) {
                // Nếu là thuê bao cần chuẩn hóa --> ko cho thực hiện hợp đồng. cần phải Đặt mới (chon kieu ld = chuan hoa) hoặc làm hợp đồng Tạm dừng/Thanh lý
                if (this.check_chuahoa_cntt) {
                    let check_chuahoa_cntt = await this.get_check_chuahoa_cntt({
                        thuebao_id: this.thuebao_id,
                        loaihd_id: this.LoaiHopDong.DATCOC_MOI
                    })

                    if (check_chuahoa_cntt) {
                        this.$root.toastError("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.")
                        return false;
                    }
                }
            }

            if (this.batbuoc_gioithieu_thaydoi_datcoc == 1) {
                if (!this.chkNguoiGT.Checked || !this.txtNguoiGT.Text || this.nguoigt_id == 0) {
                    errorMessage.push("Bạn chưa chọn người giới thiệu hợp đồng/phụ lục");
                    this.$refs.txtNguoiGT.focus();
                    this.txtNguoiGT.hasError = true
                }
            }

            if (!(this.TINHCUOC_NGAY == 0 || this.IS_DICHVU_CNTT)) {
                let c1 = await this.kiemTraNgayThoai(this.rkm_id, this.thuebao_thoaitra_id, DateTimeLib.toDateDisplay(this.dtpNgayThoai.Value), parseInt(this.yyyymm(this.dtpNgayThoai.Value)));
                if (c1 == null) {
                    this.$root.toastError("Kiểm tra tính hợp lệ của ngày thoái trên máy chủ bị thất bại.");
                    return false;
                }
                else {
                    if (c1.toString().toUpperCase().trim() != "OK") {
                        this.$root.toastError("Kiểm tra ngày thoái trên máy chủ không hợp lệ: " + c1.toString());
                        return false;
                    }
                }
                if (!this.dtpNgayThoai.Value) {
                    errorMessage.push("Bạn chưa nhập ngày thoái.");
                    this.dtpNgayThoai.hasError = true
                }

                if (this.dtpNgayThoai.Value == null || this.dtpNgayThoai.Value == undefined) {
                    this.$root.toastError("Phải nhập ngày thoái");
                    return false;
                }

                if (this.yyyymm(this.dtpNgayThoai.Value) != this.yyyymm(this.dtpThangThoaiTra.Value)) {
                    this.$root.toastError("Ngày thoái phải nằm trong tháng thoái");
                    return false;
                }
                let currentDate = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                // if (parseInt(this.yyyymm(this.dtpThangThoaiTra.Value)) < parseInt(this.yyyymm(currentDate))) {
                //     this.$root.toastError("Tháng thoái phải lớn hơn hoặc bằng tháng hiện tại");
                //     return false;
                // }
                // if (parseInt(this.yyyymmdd(this.dtpNgayThoai.Value)) < parseInt(this.yyyymmdd(currentDate))) {
                //     this.$root.toastError("Ngày thoái phải lớn hơn hoặc bằng ngày hiện tại");
                //     return false;
                // }

                // if (parseInt(this.yyyymm(this.dtpNgayThoai.Value)) != parseInt(this.yyyymm(currentDate))) {
                //     this.$root.toastError("Ngày thoái phải thuộc trong tháng hiện tại!");
                //     return false;
                // }
                // if (this.dtpNgayThoai.Value < this.ngayDC.BD) {
                //     this.$root.toastError("Ngày thoái phải lớn hơn hoặc bằng ngày bắt đầu đặt cọc");
                //     return false;
                // }

                if (!(this.txtTienThoaiTra.Text == null || this.txtTienThoaiTra.Text == undefined) && this._tienthoai < this.txtTienThoaiTra.Text) {
                    this.$root.toastError("Tiền thoái trả đang lớn hơn định mức thoái (" + this._tienthoai + "đ)");
                    return false;
                }
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return false
            }
            //console.log("this.ngayDC", this.ngayDC);
            //return false;

            return true;
        },
        async kiemTraNgayThoai(rkm_id_thoai, thuebao_dc_id, ngay_thoai, thang_thoai) {
            try {
                let response = await this.$root.context.post("/web-hopdong/ThayDoiDatCocHuy/kiemtra-ngay-thoai", {
                    rkm_id_thoai: rkm_id_thoai,
                    thuebao_dc_id: thuebao_dc_id,
                    ngay_thoai: ngay_thoai,
                    thang_thoai: thang_thoai
                })
                return !response.data ? null : response.data.rs
            } catch (err) {
                return null
            }
        },
        async lay_ngay_cn() {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ngay_cn`)
                return !response.data ? null : response.data.ngay_cn
            } catch (err) {
                return null
            }
        },
        async dtpThangThoaiTra_ValueChanged() {

            if (!this.dtpThangThoaiTra.ActiveEvent) {
                this.dtpThangThoaiTra.ActiveEvent = true
                return
            }

            if (!this.txtTienThoaiTra.allowChange) {
                this.txtTienThoaiTra.allowChange = true
                return
            }
            let ngayCn = await this.lay_ngay_cn();
            let thanght = parseInt(this.yyyymm(DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss")))

            console.log('this.thang_bd_cu', this.thang_bd_cu, thanght)

            if (this.PHAN_BO == 1 && this.IS_DICHVU_CNTT && parseInt(this.thang_bd_cu) < thanght) {
                console.log('this.PHAN_BO', this.PHAN_BO)
                this._tienthoai = 0;
                this.txtTienThoaiTra.Text = this._tienthoai;
            }
            else {
                if (this.dscheck_thoai > 0) {
                    if (!(this.TINHCUOC_NGAY == 1 && !this.IS_DICHVU_CNTT))
                        this._tienthoai = await this.Lay_tienthoai_datcoc_v2({
                            rkm_id: this.rkm_id,
                            thuebao_dc_id: this.thuebao_thoaitra_id,
                            thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value)
                        });
                    else
                        this._tienthoai = await this.Lay_tienthoai_datcoc_v4({
                            rkm_id: this.rkm_id,
                            thuebao_dc_id: this.thuebao_thoaitra_id,
                            thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value),
                            ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.Value)
                        });

                    this.txtTienThoaiTra.Text = this._tienthoai
                } else {
                    if (!(this.TINHCUOC_NGAY == 1 && !this.IS_DICHVU_CNTT))
                        await this.tinhtien_thoai();
                    else {
                        this._tienthoai = await this.Lay_tienthoai_datcoc_v4({
                            rkm_id: this.rkm_id,
                            thuebao_dc_id: this.thuebao_thoaitra_id,
                            thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value),
                            ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.Value)
                        });

                        this.txtTienThoaiTra.Text = this._tienthoai
                    }
                }
            }
        },
        async dtpNgayThoai_change(args) {
            console.log('dtpNgayThoai_change')
            if (this.TINHCUOC_NGAY == 1) {
                this._tienthoai = await this.Lay_tienthoai_datcoc_v4({
                    rkm_id: this.rkm_id,
                    thuebao_dc_id: this.thuebao_thoaitra_id,
                    thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value),
                    ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.Value)
                });
                console.log('this._tienthoai', this._tienthoai)
                this.txtTienThoaiTra.Text = this._tienthoai;
                if (this.dtpNgayThoai.Value != null)
                    this.dtpThangThoaiTra.Value = this.dtpNgayThoai.Value;
            }
        },
        async trangthai_hd() {
            return await this.lay_trangthai_hd(this.tthd_id)
        },
        async lay_trangthai_hd(tthd_id) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_trangthai_hd?tthd_id=${tthd_id}`)
                return response.data != null ? response.data.TRANGTHAI_HD : "";
            } catch (err) {
                return null
            }
        },
        cboLoaiHinhTB_SelectedValueChanged() {
            this.dichvuvt_id = !this.cboDichVuVT.Value ? 0 : parseInt(this.cboDichVuVT.Value)
        },
        async dgvDatCoc_SelectionChanged(r) {

            if (!r) {
                this.thang_bd_cu = this.thang_kt_cu = this.nhom_datcoc_id_cu = this.ttdc_id = 0
                this.cuoc_dc_cu = this.tien_td_cu = 0
                this.datCocDuocChon = null
                return
            }

            // if (!!this.datCocDuocChon && this.datCocDuocChon.KEY == r.KEY) {
            //     return
            // }

            this.datCocDuocChon = r
            this.thang_bd_cu = parseInt(r["THANG_BD"]);
            this.thang_kt_cu = parseInt(r["THANG_KT"]);
            this._INTngay_yc = parseInt(r["NGAY_YC"])
            this.nhom_datcoc_id_cu = r["NHOM_DATCOC_ID"] == null ? null : parseInt(r["NHOM_DATCOC_ID"]);
            this.cuoc_dc_cu = parseInt(r["CUOC_DC"]);
            this.tien_td_cu = parseInt(r["TIEN_TD"]);
            this.thuebao_thoaitra_id = parseInt(r["THUEBAO_DC_ID"]);
            this._thoaitra = parseInt(r["THOAITRA"]);
            this.rkm_id = parseInt(r["RKM_ID"]);
            this.ngayDC.BD = DateTimeLib.toDateObject(r["NGAY_BDDC"]);
            this.ngayDC.KT = DateTimeLib.toDateObject(r["NGAY_KTDC"]);
            this.PHAN_BO = parseInt(r["PHAN_BO"]);

            // if (!(r["NGAY_THOAI"] == null || r["NGAY_THOAI"] == undefined)) {
            //     this.dtpNgayThoai.Value = DateTimeLib.toDateObject(r["NGAY_THOAI"]);
            // }
            // else {
            //     //this.dtpNgayThoai.Value = new Date();
            // }

            // this.ngayDC.BD = new Date();
            // this.ngayDC.KT = DateTimeLib.toDateObject(r["NGAY_KT"]);
            // if (this.ngayDC.KT == null) {
            //     let t1 = r["THANG_KT"].toString().substr(0, 4);
            //     let t2 = r["THANG_KT"].toString().substr(4, 2);
            //     let t3 = new Date(t1, t2 - 1, 1);
            //     t3.setMonth(t3.getMonth() + 1);
            //     t3.setDate(t3.getDate() - 1);
            //     this.ngayDC.KT = t3;
            // }

            if (!!r["TTDC_ID"]) {
                this.ttdc_id = !r["TTDC_ID"] ? 0 : parseInt(r["TTDC_ID"]);
            }

            if (!this.txtTienThoaiTra.allowChange) {
                this.txtTienThoaiTra.allowChange = true
                return
            }
            let ngayCn = await this.lay_ngay_cn();
            let thanght = parseInt(this.yyyymm(DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss")))

            if (this.PHAN_BO == 1 && this.IS_DICHVU_CNTT && parseInt(this.thang_bd_cu) < thanght) {
                this._tienthoai = 0;
                this.txtTienThoaiTra.Text = this._tienthoai;
            }
            else {
                if (this.dscheck_thoai > 0) {

                    if (!(this.TINHCUOC_NGAY == 1 && !this.IS_DICHVU_CNTT))
                        this._tienthoai = await this.Lay_tienthoai_datcoc_v2({
                            rkm_id: this.rkm_id,
                            thuebao_dc_id: this.thuebao_thoaitra_id,
                            thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value)
                        });
                    else
                        this._tienthoai = await this.Lay_tienthoai_datcoc_v4({
                            rkm_id: this.rkm_id,
                            thuebao_dc_id: this.thuebao_thoaitra_id,
                            thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value),
                            ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.Value)
                        });

                    this.txtTienThoaiTra.Text = this._tienthoai
                } else {
                    if (!(this.TINHCUOC_NGAY == 1 && !this.IS_DICHVU_CNTT))
                        await this.tinhtien_thoai();
                    else {
                        this._tienthoai = await this.Lay_tienthoai_datcoc_v4({
                            rkm_id: this.rkm_id,
                            thuebao_dc_id: this.thuebao_thoaitra_id,
                            thang_thoai: this.yyyymm(this.dtpThangThoaiTra.Value),
                            ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.Value)
                        });

                        this.txtTienThoaiTra.Text = this._tienthoai
                    }
                }
            }
        },
        async tinhtien_thoai() {

            let tienthoai = 0;

            if (this.thang_bd_cu != 0) {

                let thang_thoai = parseInt(this.yyyymm(this.dtpThangThoaiTra.Value))
                let sothang = (await this.tinhSoThang(thang_thoai, this.thang_kt_cu)) - 1;
                let sothangcu = await this.tinhSoThang(this.thang_bd_cu, this.thang_kt_cu);
                let tientd = this.tien_td_cu;

                if (this.tien_td_cu == 0) {
                    tientd = Math.round(this.cuoc_dc_cu / sothangcu)
                }

                if (sothang >= sothangcu) {
                    sothang = sothangcu;
                    tienthoai = this.cuoc_dc_cu;
                } else {
                    tienthoai = (tientd * sothang);
                }

                if (tienthoai > this.cuoc_dc_cu) {
                    tienthoai = this.cuoc_dc_cu;
                }
            }
            else {
                tienthoai = this.cuoc_dc_cu;
            }

            if (tienthoai < 0) {
                tienthoai = 0
            }

            this.txtTienThoaiTra.Text = tienthoai;
            this._tienthoai = tienthoai;
        },
        async tinhSoThang(thangBd, thangKt) {
            try {
                let data = {
                    thang_bd: thangBd,
                    thang_kt: thangKt
                }
                let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/tinh_sothang`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.SOTHANG
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async dgvDanhSach_SelectionChanged(dr) {

            if (!dr) {
                this.hdtbDuocChon = null
                return
            }

            if (!!this.hdtbDuocChon && this.hdtbDuocChon.KEY == dr.KEY) {
                return
            }

            this.hdtbDuocChon = dr

            this.loaitb_id = parseInt(dr["LOAITB_ID"])
            this.dichvuvt_id = parseInt(dr["DICHVUVT_ID"])
            this.doituong_id = parseInt(dr["DOITUONG_ID"])
            this.hdtb_id = parseInt(dr["HDTB_ID"])
            this.CT_TIENHD_ID = parseInt(dr["CT_TIENHD_ID"])
            this.thuebao_id = parseInt(dr["THUEBAO_ID"])
            this.tthd_id = parseInt(dr["TTHD_ID"])
            this.cboDichVuVT.Value = this.dichvuvt_id + ""
            this.cboLoaiHinhTB.Value = this.loaitb_id + ""
            this.cboKieuLD.Value = parseInt(dr["KIEULD_ID"]) + ""
            this.txtMaTB.Text = dr["MA_TB"]

            if (parseInt(this.cboDichVuVT.Value) == this.DichVuVienThong.ADSL) {
                let tocDoId = await this.layTocDoCuaTbao(this.thuebao_id)
                let thongTinTocDo = await this.layThongTinTocDo(tocDoId)
                this.cboGoiCuocAdsl.Text = !thongTinTocDo ? null : thongTinTocDo.THUONGHIEU
            }

            this.thuebao_thoaitra_id = !dr["THUEBAO_THOAITRA_ID"] ? 0 : parseInt(dr["THUEBAO_THOAITRA_ID"])
            this.rkm_id = !dr["RKM_ID"] ? 0 : parseInt(dr["RKM_ID"])

            this.dtpThangThoaiTra.ActiveEvent = false

            if (!!dr["THANG_THOAITRA_SHOW"] && dr["THANG_THOAITRA_SHOW"] != "/") {
                let thang = dr["THANG_THOAITRA_SHOW"].split('/')[0]
                let nam = dr["THANG_THOAITRA_SHOW"].split('/')[1]
                this.dtpThangThoaiTra.Value = new Date(parseInt(nam), parseInt(thang) - 1, 1)
            } else {
                this.dtpThangThoaiTra.Value = null
            }
            if (!(this.IS_DICHVU_CNTT || this.TINHCUOC_NGAY == 0))
                this.dtpNgayThoai.Value = !dr["NGAY_THOAI"] ? null : this.ddmmyyyyToDate(dr["NGAY_THOAI"])
            this.ten_tb = dr["TEN_TB"]
            this.diachi_ld = dr["DIACHI_TB"]
            this.txtTienThoaiTra.Text = Math.abs(dr["TIENTHOAI"])
            this._tienthoai = this.txtTienThoaiTra.Text

            this.txtTienThoaiTra.allowChange = false
            this.dgvDatCoc.EnabledSelectFirstRow = false
            await this.HienThiTT_Datcoc()

            this.SetButton(3)
        },
        dgvDatCoc_DataBound() {
            if (!!this.thuebao_thoaitra_id || !!this.rkm_id) {
                setTimeout(() => { this.FindInGrid() }, 1000)
            }
        },
        FindInGrid() {
            let datCoc = this.dgvDatCoc.DataSource.find(item => {
                return item.THUEBAO_DC_ID == this.thuebao_thoaitra_id && item.RKM_ID == this.rkm_id
            })

            if (!!datCoc) {
                this.$refs.dgvDatCoc.selectRowByRowData(datCoc.KEY)
            }
        },
        async layThongTinTocDo(tocDoId) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_tt_tocdo_internet?tocdo_id=${tocDoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        async layTocDoCuaTbao(thueBaoId) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_tocdo_cua_tbao?thuebao_id=${thueBaoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.TOCDO_ID
                }

                throw { response: { data: response } }
            } catch (err) {
                return null
            }
        },
        chkNguoiGT_CheckedChanged() {

            if (!this.chkNguoiGT.ActiveEvent) {
                this.chkNguoiGT.ActiveEvent = true
                return
            }

            if (this.chkNguoiGT.Checked) {
                this.$refs.popupNguoiGioiThieu.show()
            } else {
                this.nguoigt_id = 0
                this.txtNguoiGT.Text = ""
                this.txtNguoiGT.hasError = false
            }
        },
        xacNhanNguoiGioiThieu(val) {

            if (!!val) {
                this.txtNguoiGT.Text = !val.ten_ht ? "" : val.ten_ht
                this.nguoigt_id = !val.nhanvien_id ? "" : val.nhanvien_id
                this.chkNguoiGT.Checked = !!this.nguoigt_id
            }

            this.$refs.popupNguoiGioiThieu.hide()
        },
        tsbtnNhapMoi_Click() {
            if (this.tsbtnNhapMoi.Enabled) {
                this.SetButton(1)
            }
        },
        async tsbtnGhiLai_Click() {

            if (!this.tsbtnGhiLai.Enabled) {
                return
            }

            try {
                this.loading(true)
                await this.CapNhat()
            } finally {
                this.loading(false)
            }
        },
        async CapNhat() {
            if (this.tsbtnNhapMoi.Enabled == false) {
                let Kiemtra_Dulieu = await this.Kiemtra_Dulieu(true)

                if (!Kiemtra_Dulieu) {
                    return
                }

                await this.TaoDuLieu(true);

                if (this.loi_sinh_magd) {
                    this.loi_sinh_magd = false;
                    return;
                }

                let result = await this.ThemMoi_HopDong({
                    thuebao_id: this.thuebao_id,
                    hd_khachhang: this.dsHDKH.XML,
                    hd_thuebao: this.dsHDTB.XML,
                    hdtb_datcoc: this.hdtb_dc.XML,
                    ct_tienhd: this.dsCTTHD.XML
                });

                if (!!result.err) {
                    this.$root.toastError(result.err)
                    return
                }

                this.$root.toastSuccess("Thêm mới dữ liệu thành công!");
                await this.HienThiDanhSacHDTB()
            }
            else {
                let Kiemtra_Dulieu = await this.Kiemtra_Dulieu(false)

                if (!Kiemtra_Dulieu) {
                    return
                }

                await this.TaoDuLieu(false);

                let result = await this.CapNhat_HopDong({
                    hd_khachhang: this.dsHDKH.XML,
                    hd_thuebao: this.dsHDTB.XML,
                    hdtb_datcoc: this.hdtb_dc.XML,
                    ct_tienhd: this.dsCTTHD.XML
                });

                if (!!result.err) {
                    this.$root.toastError(result.err)
                    return
                }

                this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
                await this.HienThiDanhSacHDTB()
            }
        },
        async ThemMoi_HopDong(data) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/themmoi_hopdong`, data)
            return response.data[0]
        },
        async CapNhat_HopDong(data) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/capnhat_hopdong`, data)
            return response.data[0]
        },
        tsbtnHuyBo_Click() {
            if (this.tsbtnHuyBo.Enabled) {
                this.SetButton(0)
            }
        },
        async tsbtnXoa_Click() {

            if (!this.tsbtnXoa.Enabled) {
                return
            }

            if (!this.hdkh_id) {
                this.$root.toastError('Chưa chọn hợp đồng để xóa')
                return
            }

            if (this.tthd_id > 1) {
                let tthd = await this.trangthai_hd()
                this.$root.toastError("Hợp đồng " + tthd + " bạn không thể xóa!");
                return;
            }

            let confirmation = await this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xóa hợp đồng?", {
                title: 'Thông báo',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
                footerClass: 'p-2',
                hideHeaderClose: false,
                centered: true
            })

            if (!confirmation) {
                return
            }

            try {
                this.loading(true)
                await this.XOA_HDKH_V2(this.hdkh_id)
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess("Xóa hợp đồng khách hàng thành công!");
            this.dgvDanhSach.DataSource = [];
            this.SetButton(0);
        },
        async XOA_HDKH_V2(hdkh_id) {
            try {
                let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/xoa_hdkh`, { hdkh_id: hdkh_id })

                if (response.error_code !== 'BSS-00000000') {
                    throw { response: { data: response } }
                }
            } catch (err) {
                let responseData = err.response.data
                let message = []

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async tsbtnThanhToan_Click() {

            if (!this.tsbtnThanhToan.Enabled) {
                return
            }

            if (this.tthd_id > 1) {
                let tthd = await this.trangthai_hd()
                this.$root.toastError(`Hợp đồng ${tthd.TRANGTHAI_HD} !`)
                return
            }

            this.loading(true)
            this.userComponentModule = thanhToan
            this.txtMaGDTT = this.txtMaGD.Text

            // await this.$refs.frmThanhToan.show(this.txtMaGD.Text)
            // await this.frmThayDoiDatCoc_V2_Activated()
        },
        open_thanhToanPopUp() {
            this.loading(false)
        },
        paymentcreated() {
            this.$refs.thanhtoanDialog.show(true)
        },
        async close_thanhToanPopUp() {
            this.userComponentModule = null
            await this.frmThayDoiDatCoc_V2_Activated()
        },
        async frmThayDoiDatCoc_V2_Activated() {
            if (!!this.txtMaGD.Text) {
                await this.txtMaGD_KeyPress()
            }
        },
        async lay_tt_khuyenmai(khuyenmai_id) {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_khuyenmai`, { khuyenmai_id })
            return response.data
        },
        async CAPNHAT_STATUS_HDTB(hdtb_id, status) {
            await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/capnhat_status_hdtb`, { hdtb_id, status })
        },
        async getTsVTTHDDT() {
            let rs = false;
            try {
                let rp = await this.$root.context.post("/web-hopdong/ThayDoiDatCocHuy/getTsVTTHDDT");
                if (rp.data != null) {
                    rs = (rp.data.ten_ts == 1);
                }
            }
            catch (e) {

            }
            return rs;
        },
        async getTsNguoiDungVTT() {
            let rs = false;
            try {
                let rp = await this.$root.context.post("/web-hopdong/ThayDoiDatCocHuy/getTsNguoiDungVTT");
                if (rp.data != null) {
                    rs = (rp.data.giatri == 1);
                }
            }
            catch (e) {
            }
            return rs;
        },
        async KichHoat_Huy_GoicuocDiDong() {

            let so_tb = "84" + this.txtMaTB.Text;
            let ghichu = this.txtGhiChu.Text;
            let id_package_data = "";
            let ktra = "";
            let kieuhuy = "0";

            let goicuoc_hl = await ccbsService.ts_prepayment_laytt_goicuoc_hieuluc(this, so_tb);

            if (goicuoc_hl.length > 0) {
                id_package_data = goicuoc_hl[0]["ID"];
            }

            let dt = await ccbsService.ts_prepayment_huy(this, ghichu, kieuhuy, so_tb, id_package_data);

            if (dt.length > 0) {
                let key = Object.keys(dt[0])[0]
                ktra = dt[0][key] + ""
            }

            if (ktra == "1") {
                this.$root.toastSuccess("Hủy đặt cọc trả trước gói cước thành công!");
                await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM);
            }

            return ktra;
        },
        async KichHoat_Huy_Camketchonso() {

            let ktra = "";
            let so_tb = "84" + this.txtMaTB.Text;
            let ghichu = this.txtGhiChu.Text;

            let check_tb = await ccbsService.ts_tttien_ckchonso_checktb(this, so_tb);

            if (check_tb.IssSuccessed) {
                let dt1 = await ccbsService.ts_tttien_ckchonso_laykieuso(this, so_tb);

                if (dt1.length > 0 && dt1[0]["kieuso_id"] != undefined) {
                    if (!!dt1[0]["kieuso_id"] && !!dt1[0]["id"]) {
                        {
                            let ccbs_huy = await ccbsService.ts_tttien_ckchonso_huy(ghichu, so_tb, dt1[0]["id"]);

                            if (ccbs_huy.IssSuccessed) {
                                ktra = "1";
                                this.$root.toastSuccess("Hủy đăng ký cam kết chọn số thành công!");
                                await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM);
                            }
                            else {
                                this.$root.toastError(ccbs_huy.Message);
                            }
                        }
                    }
                }
                else {
                    this.$root.toastError("Số thuê bao không thõa mãn điều kiện hủy cam kết chọn số!");
                }
            }
            else {
                this.$root.toastError(check_tb.Message);
            }

            return ktra;
        },
        async Huy_TraTruocDiDong() {

            let ktra = "";
            let sotb = "84" + this.txtMaTB.Text;
            let dt = await ccbsService.ts_dongtruoc_cuoctb_huy(sotb, "0");

            if (dt.length > 0) {
                let key = Object.keys(dt[0])[0]
                ktra = dt[0][key] + ""

                if (ktra == "1") {
                    await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM);
                }
            }
            else {
                ktra = "0";
            }

            return ktra;
        },
        async layKyCuocHienHanh() {
            let response = await this.$root.context.get(`/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh`)
            return response.data[0].KYCUOC + ""
        },
        async lay_tt_khachhang(data) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_khachhang`, data)
            return response.data
        },
        async ThoaiTra_TamThuCuocNong() {

            let ktra = false;

            let sotb = "84" + this.txtMaTB.Text;
            let tien_dongtruoc = this.txtTienThoaiTra.Text;
            let ghi_chu = this.txtGhiChu.Text;
            let ma_tinh_cu = "0";
            let httt_id = "19";
            let ngaynhap = this.ddmmyyyy(this.dtpNgayYeuCau.Value);
            let ma_tn = this.$root.token.getUserName();
            let phieu_id = "";

            let s = await this.layKyCuocHienHanh() // ex: 20210401
            let yyyy = s.substr(0, 4)
            let month = s.substr(4, 2)
            let chukyno = month + yyyy;

            let ds_tt = await this.lay_tt_khachhang({
                khachhang_id: this.khachhang_id,
                ma_tb: this.txtMaTB.Text
            })

            let ma_kh = ds_tt[0]["ma_tt_neo"];

            //chi tra
            let result = await ccbsService.ts_tamthu_cuocnong_them_phieu(tien_dongtruoc, ma_kh, ghi_chu, phieu_id, ma_tn, "1", ma_tinh_cu, sotb, chukyno, httt_id, ngaynhap, "");

            if (result.IssSuccessed) {
                ktra = true;
                await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM);
            }
            else {
                ktra = false;
            }

            return ktra;
        },
        async LAY_TT_HDDT_THOAITRA_DC(vhdkh_id, vkieu) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_hddt_thoaitra_dc`, { hdkh_id: vhdkh_id, kieu: vkieu })
            return response.data
        },
        async lay_tt_hoadon(phieutt_id) {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_hoadon`, { phieutt_id })
            return response.data
        },
        async lay_tt_bd_hddt(hdkh_id) {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_bd_hddt`, { hdkh_id })
            return response.data
        },
        async lay_tt_loai_hd() {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_loai_hd`)
            return response.data
        },
        async lay_tt_loai_hd_tt78(ten_loaihd, seri) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_loai_hd_tt78`, { ten_loaihd, seri })
            return response.data
        },
        async lay_tt_loai_hd_vtt() {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_loai_hd_vtt`)
            return response.data
        },
        async lay_tt_loai_hd_tt78_vtt(ten_loaihd, seri) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_loai_hd_tt78_vtt`, { ten_loaihd, seri })
            return response.data
        },
        async GetInvoiceXml_DieuChinh(i_kieu_dc, i_ten_kh, i_ma_gd, i_mst, i_diachi_ct, i_stk, i_so_dt, i_httt, i_dt, obj) {

            let str = '';
            let sinh_fkey = await this.$root.context.get('/web-ccdv/thanhtoan/sinh_fkey?kieu=1');
            let fkey_new = sinh_fkey.data;

            if (!fkey_new) {
                throw 'Có lỗi trong lúc sinh fkey'
            }

            str += "<AdjustInv>";
            str += "<key>" + fkey_new + "</key>";
            str += "<CusCode>" + i_ma_gd + "</CusCode>";
            str += "<CusName><![CDATA[" + obj.CusName + "]]></CusName>";
            str += "<CusAddress><![CDATA[" + obj.CusAddress + "]]></CusAddress>";
            str += "<CusPhone>" + obj.CusPhone + "</CusPhone>";
            str += "<CusTaxCode><![CDATA[" + obj.CusTaxCode + "]]></CusTaxCode>";
            str += "<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>";
            str += "<KindOfService></KindOfService>";
            let type = i_kieu_dc == 1 ? "2" : i_kieu_dc == 2 ? "3" : "4";
            str += "<Type>" + type + "</Type>";
            str += "<Products>";

            for (let i = 0; i < i_dt.length; i++) {
                if (i_dt[i]["prodname"] != "") {
                    str += "<Product>";
                    str += "<ProdName>" + i_dt[i]["prodname"] + "</ProdName>";
                    str += "<ProdUnit>" + i_dt[i]["produnit"] + "</ProdUnit>";
                    str += "<ProdQuantity>" + i_dt[i]["prodquantity"] + "</ProdQuantity>";
                    str += "<ProdPrice>" + Math.abs(parseFloat(i_dt[i]["prodprice"])) + "</ProdPrice>";
                    str += "<Amount>" + Math.abs(parseFloat(i_dt[i]["amount"])) + "</Amount>";

                    if (this.HDDT_THONGTU_78 == 1) {
                        str += "<Total>" + Math.abs(parseFloat(i_dt[i]["amount"])) + "</Total>";
                        str += "<VATAmount>" + Math.abs(parseFloat(i_dt[i]["vat"])) + "</VATAmount>";
                    }

                    str += "</Product>";

                    this.tongtien_hddt += parseFloat(i_dt[i]["tien"]);
                    this.tongvat_hddt += parseFloat(i_dt[i]["vat"]);
                    this.tong_hddt += parseFloat(i_dt[i]["tongtien"]);
                }
            }

            this.tong_hddt = Math.abs(this.tong_hddt);
            this.tongvat_hddt = Math.abs(this.tongvat_hddt);
            this.tongtien_hddt = Math.abs(this.tongtien_hddt);

            str += "</Products>";
            str += "<Total>" + this.tongtien_hddt + "</Total>";
            str += "<VATRate>10</VATRate>";
            str += "<VATAmount>" + this.tongvat_hddt + "</VATAmount>";
            str += "<Amount>" + this.tong_hddt + "</Amount>";

            let TienBangChu = "";

            let getMoneyTotext = await this.$root.context.post('/web-tracuu/tracuu_th/fn_doisosangchu', { money: this.tong_hddt });

            if (getMoneyTotext) {
                TienBangChu = getMoneyTotext.data;
            }

            this.tbc = TienBangChu

            str += "<AmountInWords>" + TienBangChu + "</AmountInWords>";
            str += "<Teller>" + this.$root.token.getUserName() + "</Teller>";
            str += "</AdjustInv>";
            return str;
        },
        async lay_tt_user(nguoidung_id) {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_user`, { nguoidung_id })
            return response.data[0]
        },
        async luu_bd_hddt(data) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/luu_bd_hddt`, data)
            return response.data[0]
        },
        async Log_Action(data) {
            let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/log_action`, data)
            return response.data
        },
        TaoDuLieuBDCT(tb_hddt) {
            let dsBDHDDTCT = [];
            for (let i = 0; i < tb_hddt.length; i++) {
                if (!!tb_hddt[i]["prodname"]) {
                    let row = {};
                    row.DONGIA = Math.abs(parseFloat(tb_hddt[i]["prodprice"]));
                    row.DONVITINH = tb_hddt[i]["produnit"].toString().trim();
                    row.NOIDUNG = tb_hddt[i]["prodname"].toString().trim();
                    row.SOLUONG = parseFloat(tb_hddt[i]["prodquantity"]);
                    row.STT = i + 1;
                    row.THANHTIEN = Math.abs(parseFloat(tb_hddt[i]["amount"]));
                    dsBDHDDTCT.push(row);
                }
            }
            return dsBDHDDTCT;
        },
        async dongbo_hddt_tudong(vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, product) {

            // Điều chỉnh hóa đơn
            if (!vifkey) {
                return "ERR: Không có thông tin Fkey cần điều chỉnh";
            }

            let invToken = null

            try {
                let vtt = false;
                let ds_token = await this.lay_tt_hoadon(vphieutt_id)

                if (ds_token.length > 0) {
                    invToken = ds_token[0]["ten_loaihd"] + ";" + ds_token[0]["seri"] + ";" + ds_token[0]["soseri"]
                }
                else {
                    return "ERR: Không tìm thấy hóa đơn cần điều chỉnh !";
                }

                let bdhddt_cha_id = 0;
                let ds_bd_cha = await this.lay_tt_bd_hddt(vhdkh_id)

                if (ds_bd_cha.length > 0 && !!ds_bd_cha[0]["bdhddt_id"]) {
                    bdhddt_cha_id = parseInt(ds_bd_cha[0]["bdhddt_id"]);
                }

                let objHoaDonDienTu = null

                vtt = await this.getTsVTTHDDT();
                if (vtt == true) {
                    vtt = await this.getTsNguoiDungVTT();
                }
                let xml = "";
                if (vtt == true) {
                    xml = await invoiceService.downloadInvNoPay_VTT(this, invToken);
                }
                else {
                    xml = await invoiceService.downloadInvNoPay_V2(this, invToken);
                }

                if (xml.startsWith("ERR:")) {
                    if (vtt == true) {
                        return "ERR: downloadInvNoPay_VTT - " + xml.substr("ERR:".length);
                    }
                    else {
                        return "ERR: downloadInvNoPay - " + xml.substr("ERR:".length);
                    }
                }
                else {
                    xml = "<Content>" + xml + "</Content>"

                    try {
                        objHoaDonDienTu = JSON.parse(convert.xml2json(xml, { compact: true }));
                    } catch (e) {
                        throw 'Lỗi phân tích nội dung hóa đơn: ' + xml
                    }

                    try {
                        objHoaDonDienTu.CusName = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.Ten
                        objHoaDonDienTu.CusAddress = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.DChi
                        objHoaDonDienTu.CusPhone = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.SDThoai
                        objHoaDonDienTu.CusTaxCode = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.MST
                        objHoaDonDienTu.InvoicePattern = objHoaDonDienTu.Content.HDon.DLHDon.TTChung.KHMSHDon
                        objHoaDonDienTu.SerialNo = objHoaDonDienTu.Content.HDon.DLHDon.TTChung.KHMSHDon
                        objHoaDonDienTu.InvoiceNo = objHoaDonDienTu.Content.HDon.DLHDon.TTChung.SHDon
                    } catch (e1) {
                        try {
                            objHoaDonDienTu.CusName = objHoaDonDienTu.Content.Invoice.Content.CusName
                            objHoaDonDienTu.CusAddress = objHoaDonDienTu.Content.Invoice.Content.CusAddress
                            objHoaDonDienTu.CusPhone = objHoaDonDienTu.Content.Invoice.Content.CusPhone
                            objHoaDonDienTu.CusTaxCode = objHoaDonDienTu.Content.Invoice.Content.CusTaxCode
                            objHoaDonDienTu.InvoicePattern = objHoaDonDienTu.Content.Invoice.Content.InvoicePattern
                            objHoaDonDienTu.SerialNo = objHoaDonDienTu.Content.Invoice.Content.SerialNo
                            objHoaDonDienTu.InvoiceNo = objHoaDonDienTu.Content.Invoice.Content.InvoiceNo
                        } catch (e2) {
                            throw 'Lỗi phân tích nội dung hóa đơn: ' + xml
                        }
                    }

                    objHoaDonDienTu.CusName = !objHoaDonDienTu.CusName ? "" : (!objHoaDonDienTu.CusName._text ? "" : objHoaDonDienTu.CusName._text)
                    objHoaDonDienTu.CusAddress = !objHoaDonDienTu.CusAddress ? "" : (!objHoaDonDienTu.CusAddress._text ? "" : objHoaDonDienTu.CusAddress._text)
                    objHoaDonDienTu.CusPhone = !objHoaDonDienTu.CusPhone ? "" : (!objHoaDonDienTu.CusPhone._text ? "" : objHoaDonDienTu.CusPhone._text)
                    objHoaDonDienTu.CusTaxCode = !objHoaDonDienTu.CusTaxCode ? "" : (!objHoaDonDienTu.CusTaxCode._text ? "" : objHoaDonDienTu.CusTaxCode._text)
                    objHoaDonDienTu.InvoicePattern = !objHoaDonDienTu.InvoicePattern ? "" : (!objHoaDonDienTu.InvoicePattern._text ? "" : objHoaDonDienTu.InvoicePattern._text)
                    objHoaDonDienTu.SerialNo = !objHoaDonDienTu.SerialNo ? "" : (!objHoaDonDienTu.SerialNo._text ? "" : objHoaDonDienTu.SerialNo._text)
                    objHoaDonDienTu.InvoiceNo = !objHoaDonDienTu.InvoiceNo ? "" : (!objHoaDonDienTu.InvoiceNo._text ? "" : objHoaDonDienTu.InvoiceNo._text)
                }

                this.tongtien_hddt = 0;
                this.tongvat_hddt = 0;
                this.tong_hddt = 0;

                let sohoadon = "";
                let pattern = "";
                let seri = "";
                let loaihoadon_id = 0;
                let seri_id = 0;

                let ds_hd_seri = null
                let tt78 = this.HDDT_THONGTU_78 == 1
                if(vtt == true) {
                    if (tt78) {
                        ds_hd_seri = await this.lay_tt_loai_hd_tt78_vtt(ds_token[0]["ten_loaihd"], ds_token[0]["seri"])
                    } else {
                        ds_hd_seri = await this.lay_tt_loai_hd_vtt()
                    }
                }
                else {
                    if (tt78) {
                        ds_hd_seri = await this.lay_tt_loai_hd_tt78(ds_token[0]["ten_loaihd"], ds_token[0]["seri"])
                    } else {
                        ds_hd_seri = await this.lay_tt_loai_hd()
                    }
                }

                if (ds_hd_seri.length > 0) {
                    loaihoadon_id = parseInt(ds_hd_seri[0]["loaihoadon_id"]);
                    pattern = ds_hd_seri[0]["ten_loaihd"];
                    seri_id = parseInt(ds_hd_seri[0]["seri_id"]);
                    seri = ds_hd_seri[0]["seri"];
                }
                else {
                    return "ERR: Không tìm thấy loại hóa đơn, seri !";
                }

                // Kiểm tra hóa đơn hiện tại có đang ở trạng thái Đang sử dụng không ?
                let kq_kt = ""
                if(vtt == true) {
                    if (tt78) {
                        kq_kt = await invoiceService.listInvByCus_TT78_VTT(this, vma_gd, "", "");
                    } else {
                        kq_kt = await invoiceService.listInvByCus_V2_VTT(this, vma_gd, "", "", pattern);
                    }
                }
                else {
                    if (tt78) {
                        kq_kt = await invoiceService.listInvByCus_TT78(this, vma_gd, "", "");
                    } else {
                        kq_kt = await invoiceService.listInvByCus_V2(this, vma_gd, "", "", pattern);
                    }
                }
                if (kq_kt.startsWith("ERR")) {
                    return "ERR: listInvByCus - " + kq_kt.substr("ERR:".length);
                }
                else {
                    if (!tt78) {
                        let item_kt_ls = JSON.parse(convert.xml2json(kq_kt));

                        if (item_kt_ls && item_kt_ls.length > 0) {

                            let item_kt = item_kt_ls.find(e => e['invToken'] == invToken)

                            if (item_kt.status != 1) {
                                return "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể điều chỉnh !";
                            }
                        }
                    }
                }

                //kichhoat:
                let kq = ""
                let tt_nd = await this.lay_tt_user(this.$root.token.getNguoiDungID())
                let GetInvoiceXml_DieuChinh = await this.GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product, objHoaDonDienTu)

                if(vtt == true) {
                    if (tt78) {
                        kq = await invoiceService.AdjustInvoiceAction_TT78_VTT(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern, "11");
                    } else {
                        kq = await invoiceService.adjustInv_V2_VTT(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern);
                    }
                }
                else {
                    if (tt78) {
                        kq = await invoiceService.AdjustInvoiceAction_TT78(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern, "11");
                    } else {
                        kq = await invoiceService.adjustInv_V2(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern);
                    }
                }

                if (kq.startsWith("ERR:")) {
                    return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq.substr("ERR:".length);
                }

                let result = kq.split(':');

                if (result[0] == "OK") {
                    try {
                        let result1 = result[1].toString().split(';');
                        pattern = result1[0].toString();
                        seri = result1[1].toString();
                        sohoadon = result1[2].toString().split('_')[1].toString();
                        let fkey_new = result1[2].toString().split('_')[0].toString();
                        invToken = pattern + ";" + seri + ";" + sohoadon;

                        let row = {}
                        row.BDHDDT_ID = await this.GetKey("BDHDDT_ID");
                        row.MA_TT = vma_gd;

                        if (vkieu == 1) // Điều chỉnh tăng
                            row.KIEU_BD = 8;
                        else if (vkieu == 2) // Điều chỉnh giảm
                            row.KIEU_BD = 9;
                        else if (vkieu == 3) // Điều chỉnh thông tin
                            row.KIEU_BD = 10;
                        else
                            row.KIEU_BD = 1;

                        row.TONGTIEN = this.tong_hddt;
                        row.NGUOI_CN = this.$root.token.getUserName();
                        row.MAY_CN = this.$root.token.getUserName();
                        row.NHANVIEN_ID = this.$root.token.getNhanVienID();
                        row.DONVI_ID = this.$root.token.getDonViID();
                        row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB";
                        row.HDKH_ID = vhdkh_id;
                        row.PHIEUTT_ID = vphieutt_id;
                        row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
                        row.LOAIHOADON_MOI = pattern;
                        row.SERI_CU = objHoaDonDienTu.SerialNo;
                        row.SERI_MOI = seri;
                        row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
                        row.SO_HD_MOI = sohoadon;
                        row.SERI_MOI_ID = seri_id;
                        row.TRANGTHAI = 1;
                        row.BDHDDT_CHA_ID = bdhddt_cha_id;
                        row.TEN_TT = objHoaDonDienTu.CusName;
                        row.DIACHI_TT = objHoaDonDienTu.CusAddress;
                        row.SDT = objHoaDonDienTu.CusPhone;
                        row.MST = objHoaDonDienTu.CusTaxCode;
                        row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                        row.TIEN = this.tongtien_hddt;
                        row.TIENBANGCHU = this.tbc;
                        row.TYLE_VAT = 10;
                        row.VAT = this.tongvat_hddt;

                        let chitiet = this.TaoDuLieuBDCT(product);
                        row.BDCT = JSON.stringify(chitiet)

                        row.HDDT_FKEY = fkey_new
                        row.LOAIHOADON_ID = loaihoadon_id

                        let row1 = {}
                        Object.keys(row).forEach(x => row1[x.toLowerCase()] = row[x])
                        await this.luu_bd_hddt(row1)

                        return "OK";
                    }
                    catch (ex) {
                        await this.Log_Action({
                            user_name: this.$root.token.getUserName(),
                            computer_name: this.$root.token.getUserName(),
                            action_name: "chucnang_dieuchinh",
                            key_parameter: "ma_gd = " + vma_gd + ", ngay_cn = " + this.ddmmyyyy(new Date()) + ", Err : " + ex,
                            server_name: this.$root.token.getUserName(),
                            note: "chucnang_dieuchinh",
                        })

                        return "OK";
                    }
                }
            }
            catch (ex) {
                return "ERR: " + ex;
            }
        },
        async HOANTHIEN_HS_THAYDOI_DATCOC(hdkh_id, hdtb_id, ngay_thoai) {
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/hoanthien_hs_thaydoi_datcoc`, { hdkh_id, hdtb_id, ngay_thoai })
            return response.data[0]
        },
        async KT_Trangthai_thanhtoan_hd(hdkh_id) {
            try {
                let data = {
                    LOAI: 'PHIEUTT_HD',
                    KHOA: 'HDKH_ID',
                    GIATRI: hdkh_id
                }
                let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet', data)
                if (rs.data.length === 0) return false
                for (let index = 0; index < rs.data.length; index++) {
                    const e = rs.data[index]
                    if (e.TRANGTHAI === null || e.TRANGTHAI.toString() === '' || e.TRANGTHAI.toString() === '0') {
                        return false
                    }
                }
                return true
            } catch (error) {
                return false
            }
        },
        async tsbtnCapNhatDB_Click() {

            if (!this.tsbtnCapNhatDB.Enabled) {
                return
            }

            this.loading(true)

            if (this.tthd_id != 2) {
                let tthd = await this.trangthai_hd()
                this.$root.toastError("Hợp đồng " + tthd + " không thể hoàn thiện!");
                this.loading(false)
                return;
            }

            //datnt 22/06/2023
            let rs = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/fn_kiemtra_thaydoi_datcoc`, { "vhdkh_id": this.hdkh_id, "vkieuld_id": this.cboKieuLD.Value })
            let str_kt = rs.data ? rs.data : rs.message_detail
            if (str_kt != "1") {
                this.$toast.warning(`${str_kt}`);
                return;
            }

            let ckHDKH = await this.KT_Trangthai_thanhtoan_hd(this.hdkh_id);
            if (!ckHDKH) {
                this.$root.toastError("Hợp đồng đã thanh toán nhưng chưa thu được tiền không thể hoàn thiện!");
                this.loading(false)
                return;
            }

            let dong_bo_ccbs = true; // fix 20/12
            let ma_km = "";

            if (this.TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
                // Hủy đặt cọc gói cước di động HTH
                if (parseInt(this.cboLoaiHinhTB.Value) == this.LoaiHinhTB.DIDONGTRASAU) {
                    let r = this.datCocDuocChon;

                    if (!!r["KHUYENMAI_ID"]) {
                        let ds = await this.lay_tt_khuyenmai(r["KHUYENMAI_ID"])
                        ma_km = ds[0]["ma_km"]
                    }
                    else {
                        this.$root.toastError("Không lấy được mã đặt cọc gói cước trong chi tiết khuyến mãi!");
                        this.loading(false)
                        return;
                    }

                    //HTH thoái tiền trả trước gói cước dd trả sau - GDV thoái trong ngày
                    // admin thoái: nếu mà > tháng hiệu lực thì thấy cập nhật time_end về cuối tháng, còn nếu chưa hiệu lực/tháng đầu tiên có hiệu lực thì hủy luôn
                    if (ma_km == this.DATCOC.MA_DATCOC_GOICUOC) {
                        this.loading(false)

                        // Kiểu hủy ko HDDT
                        let confirmation = await this.$bvModal.msgBoxConfirm("Xóa bản ghi, khách hàng sẽ bị hủy tiền trả trước còn lại. Bạn chắc chắn không?", {
                            title: 'Thông báo',
                            size: 'sm',
                            okTitle: 'Đồng ý',
                            cancelTitle: 'Hủy',
                            footerClass: 'p-2',
                            hideHeaderClose: false,
                            centered: true
                        })

                        if (!confirmation) {
                            return
                        }

                        this.loading(true)

                        let ktra = await this.KichHoat_Huy_GoicuocDiDong();

                        if (ktra != "1") {
                            this.$root.toastError("Hủy trả trước gói cước không thành công, chi tiết: " + ktra + " !!!");
                            dong_bo_ccbs = false;
                            this.loading(false)
                            return;
                        }
                    }

                    if (ma_km == this.DATCOC.MA_CAMKET_CHONSO) {
                        this.loading(false)

                        // Kiểu hủy ko HDDT
                        let confirmation = await this.$bvModal.msgBoxConfirm("Xóa bản ghi, khách hàng sẽ bị hủy tiền trả trước còn lại. Bạn chắc chắn không?", {
                            title: 'Thông báo',
                            size: 'sm',
                            okTitle: 'Đồng ý',
                            cancelTitle: 'Hủy',
                            footerClass: 'p-2',
                            hideHeaderClose: false,
                            centered: true
                        })

                        if (!confirmation) {
                            return
                        }

                        this.loading(true)

                        let ktra = await this.KichHoat_Huy_Camketchonso();

                        if (ktra != "1") {
                            this.$root.toastError("Hủy trả trước gói cước không thành công, chi tiết: " + ktra + " !!!");
                            dong_bo_ccbs = false;
                            this.loading(false)
                            return;
                        }
                    }

                    if (ma_km == this.DATCOC.MA_DATCOC_TRATRUOC) {
                        this.loading(false)

                        // Kiểu hủy ko HDDT
                        let confirmation = await this.$bvModal.msgBoxConfirm("Xóa bản ghi, khách hàng sẽ bị hủy tiền trả trước còn lại. Bạn chắc chắn không?", {
                            title: 'Thông báo',
                            size: 'sm',
                            okTitle: 'Đồng ý',
                            cancelTitle: 'Hủy',
                            footerClass: 'p-2',
                            hideHeaderClose: false,
                            centered: true
                        })

                        if (!confirmation) {
                            return
                        }

                        this.loading(true)

                        let ktra = await this.Huy_TraTruocDiDong();

                        if (ktra != "1") {
                            this.$root.toastError("Hủy trả trước không thành công, chi tiết: " + ktra + " !!!");
                            dong_bo_ccbs = false;
                            this.loading(false)
                            return;
                        }
                    }

                    if (ma_km == this.DATCOC.MA_TAMTHU_CUOCNONG) {
                        this.loading(false)

                        // Kiểu hủy ko HDDT
                        let confirmation = await this.$bvModal.msgBoxConfirm("Xóa bản ghi, sẽ chi trả lại tiền cho khách hàng. Bạn chắc chắn không?", {
                            title: 'Thông báo',
                            size: 'sm',
                            okTitle: 'Đồng ý',
                            cancelTitle: 'Hủy',
                            footerClass: 'p-2',
                            hideHeaderClose: false,
                            centered: true
                        })

                        if (!confirmation) {
                            return
                        }

                        this.loading(true)

                        let ThoaiTra_TamThuCuocNong = await this.ThoaiTra_TamThuCuocNong()

                        if (!ThoaiTra_TamThuCuocNong) {
                            this.$root.toastError("Chi thu cước nóng không thành công !!!");
                            dong_bo_ccbs = false;
                            this.loading(false)
                            return;
                        }
                    }
                }

                if (!dong_bo_ccbs) {
                    this.$root.toastError("Bạn phải kích hoạt thuê bao lên VNP trước khi hoàn thiện !");
                    this.loading(false)
                    return;
                }
            }

            let ds = await this.LAY_TT_HDDT_THOAITRA_DC(this.hdkh_id, 0);

            if (!!ds && !!ds["dtProduct"] && ds["dtProduct"].length > 0) {
                //Nếu có tiền thoái thì kiểm tra thông tin hóa đơn cũ để điều chỉnh
                if (!!ds["dtThongTin"] && ds["dtThongTin"].length > 0) {
                    let vthaotac = 2;
                    let vkieu = 2;
                    let dtThongTin = ds["dtThongTin"];
                    let dtProduct = ds["dtProduct"];
                    let vifkey = dtThongTin[0]["hddt_fkey"];
                    let vphieutt_id = parseInt(dtThongTin[0]["phieutt_id"]);
                    let vhdkh_id = parseInt(dtThongTin[0]["hdkh_id"]);
                    let vma_gd = dtThongTin[0]["ma_gd"];
                    let vten_kh = dtThongTin[0]["ten_kh"];
                    let vdiachi_kh = dtThongTin[0]["diachi_kh"];
                    let vmst = dtThongTin[0]["mst"]
                    let vten_nh = dtThongTin[0]["ten_nh"]
                    let vstk = dtThongTin[0]["stk"]
                    let vemail = dtThongTin[0]["email"]
                    let vfax = dtThongTin[0]["so_fax"]
                    let vsodt = dtThongTin[0]["so_dt"]
                    let vndd = dtThongTin[0]["nguoi_dd"]
                    let vloai_kh = dtThongTin[0]["loai_kh"]
                    let vhttt = dtThongTin[0]["httt"]

                    //Xuất hóa đơn điều chỉnh giảm
                    let s = await this.dongbo_hddt_tudong(vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, dtProduct);

                    if (s != "OK") {
                        this.$root.toastError("Phát hành hóa đơn điều chỉnh thất bại!" + s);
                        this.loading(false)
                        return;
                    }
                }
                else {
                    this.loading(false)

                    let confirmation = await this.$bvModal.msgBoxConfirm("Không tìm thấy thông tin hóa đơn điện tử cũ để điều chỉnh. Bạn có muốn tiếp tục?", {
                        title: 'Thông báo',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                        footerClass: 'p-2',
                        hideHeaderClose: false,
                        centered: true
                    })

                    if (!confirmation) {
                        return
                    }

                    this.loading(true)
                }
            }

            let kq = await this.HOANTHIEN_HS_THAYDOI_DATCOC(this.hdkh_id, this.hdtb_id, this.ddmmyyyy(this.dtpNgayThoai.Value));

            if (!!kq.err) {
                this.$root.toastError(`Cập nhật dữ liệu thất bại.\n${kq.err}`);
                this.loading(false)
                return
            }

            this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
            this.dgvDanhSach.DataSource = [];
            this.SetButton(0);
            this.loading(false)
        },
        tsbtnEmail_Click() {
            if (!!this.hdkh_id) {
                this.$refs['ref-thongtin-email'].show()
            }
        },
        tsbtnThongTinLH_Click() {
            if (!!this.hdkh_id) {
                this.$refs['ref-thongtin-lienhe'].show()
            }
        },
        SetButton(kieu) {

            this.tsbtnNhapMoi.Enabled = false;
            this.tsbtnGhiLai.Enabled = false;
            this.tsbtnXoa.Enabled = false;
            this.tsbtnHuyBo.Enabled = false;

            if (kieu == -1) //Bat dau
            {
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }

            if (kieu == 0)//Bat dau
            {
                this.$refs.txtMaTB.focus();
                this.tsbtnNhapMoi.Enabled = true;
                this.Clear();
            }

            if (kieu == 1)//Them moi
            {
                this.$refs.txtMaTB.focus();
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
                this.Clear();
            }

            if (kieu == 2)//Huy
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.Clear();
            }

            if (kieu == 3)//Edit
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }
        },
        numberFormat(number) {

            if (!number) {
                return number
            }

            number = `${number}`

            let decimal = ''

            if (number.includes('.')) {
                number = number.split('.')[0]
                decimal = '.' + number.split('.')[1]
            }

            let group = []

            while (number.length > 3) {
                group.push(number.substr(number.length - 3))
                number = number.substr(0, number.length - 3)
            }

            if (number.length > 0) {
                group.push(number)
            }

            return group.reverse().join() + decimal
        },
        getDateElements(date) {

            date = new Date(date)

            let day = date.getDate()
            day = day > 9 ? day : '0' + day

            let month = date.getMonth() + 1
            month = month > 9 ? month : '0' + month

            let hour = date.getHours()
            hour = hour > 9 ? hour : '0' + hour

            let min = date.getMinutes()
            min = min > 9 ? min : '0' + min

            let second = date.getSeconds()
            second = second > 9 ? second : '0' + second

            return {
                y: date.getFullYear(),
                M: month,
                d: day,
                h: hour,
                m: min,
                s: second
            }
        },
        yyyymm(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.y}${date.M}`
        },
        yyyymmdd(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.y}${date.M}${date.d}`
        },
        ddmmyyyyhhmiss(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.d}${date.M}${date.y}${date.h}${date.m}${date.s}`
        },
        ddmmyyyy(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y}`
        },
        ddmmyyyyToDate(dateStr) {

            if (!dateStr) {
                return null
            }

            let arr = dateStr.trim().split('/')
            let dd = arr[0]
            let mm = arr[1]
            let yyyy = arr[2]
            return new Date(`${yyyy}-${mm}-${dd}`)
        },
        async test (p_hdkh_id) {
            let ds = await this.LAY_TT_HDDT_THOAITRA_DC(p_hdkh_id, 0);


            if (!!ds && !!ds["dtProduct"] && ds["dtProduct"].length > 0) {
                //Nếu có tiền thoái thì kiểm tra thông tin hóa đơn cũ để điều chỉnh
                if (!!ds["dtThongTin"] && ds["dtThongTin"].length > 0) {
                    let vthaotac = 2;
                    let vkieu = 2;
                    let dtThongTin = ds["dtThongTin"];
                    let dtProduct = ds["dtProduct"];

                    console.log(dtThongTin)
                    let vifkey = dtThongTin[0]["hddt_fkey"];
                    let vphieutt_id = parseInt(dtThongTin[0]["phieutt_id"]);
                    let vhdkh_id = parseInt(dtThongTin[0]["hdkh_id"]);
                    let vma_gd = dtThongTin[0]["ma_gd"];
                    let vten_kh = dtThongTin[0]["ten_kh"];
                    let vdiachi_kh = dtThongTin[0]["diachi_kh"];
                    let vmst = dtThongTin[0]["mst"]
                    let vten_nh = dtThongTin[0]["ten_nh"]
                    let vstk = dtThongTin[0]["stk"]
                    let vemail = dtThongTin[0]["email"]
                    let vfax = dtThongTin[0]["so_fax"]
                    let vsodt = dtThongTin[0]["so_dt"]
                    let vndd = dtThongTin[0]["nguoi_dd"]
                    let vloai_kh = dtThongTin[0]["loai_kh"]
                    let vhttt = dtThongTin[0]["httt"]

                    //Xuất hóa đơn điều chỉnh giảm
                    let s = await this.dongbo_hddt_tudong_test(vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, dtProduct);

                    if (s != "OK") {
                        this.$root.toastError("Phát hành hóa đơn điều chỉnh thất bại!" + s);
                        this.loading(false)
                        return;
                    }
                }
                else {
                    this.loading(false)

                    let confirmation = await this.$bvModal.msgBoxConfirm("Không tìm thấy thông tin hóa đơn điện tử cũ để điều chỉnh. Bạn có muốn tiếp tục?", {
                        title: 'Thông báo',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                        footerClass: 'p-2',
                        hideHeaderClose: false,
                        centered: true
                    })

                    if (!confirmation) {
                        return
                    }

                    this.loading(true)
                }
            }
        },
        async dongbo_hddt_tudong_test(vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, product) {

            // Điều chỉnh hóa đơn
            if (!vifkey) {
                return "ERR: Không có thông tin Fkey cần điều chỉnh";
            }

            let invToken = null

            try {
                let vtt = false;
                let ds_token = await this.lay_tt_hoadon(vphieutt_id)

                if (ds_token.length > 0) {
                    invToken = ds_token[0]["ten_loaihd"] + ";" + ds_token[0]["seri"] + ";" + ds_token[0]["soseri"]
                }
                else {
                    return "ERR: Không tìm thấy hóa đơn cần điều chỉnh !";
                }

                let bdhddt_cha_id = 0;
                let ds_bd_cha = await this.lay_tt_bd_hddt(vhdkh_id)

                if (ds_bd_cha.length > 0 && !!ds_bd_cha[0]["bdhddt_id"]) {
                    bdhddt_cha_id = parseInt(ds_bd_cha[0]["bdhddt_id"]);
                }

                let objHoaDonDienTu = null

                vtt = await this.getTsVTTHDDT();
                if (vtt == true) {
                    vtt = await this.getTsNguoiDungVTT();
                }
                let xml = "";
                if (vtt == true) {
                    xml = await invoiceService.downloadInvNoPay_VTT(this, invToken);
                }
                else {
                    xml = await invoiceService.downloadInvNoPay_V2(this, invToken);
                }

                if (xml.startsWith("ERR:")) {
                    if (vtt == true) {
                        return "ERR: downloadInvNoPay_VTT - " + xml.substr("ERR:".length);
                    }
                    else {
                        return "ERR: downloadInvNoPay - " + xml.substr("ERR:".length);
                    }
                }
                else {
                    xml = "<Content>" + xml + "</Content>"

                    try {
                        objHoaDonDienTu = JSON.parse(convert.xml2json(xml, { compact: true }));
                    } catch (e) {
                        throw 'Lỗi phân tích nội dung hóa đơn: ' + xml
                    }

                    try {
                        objHoaDonDienTu.CusName = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.Ten
                        objHoaDonDienTu.CusAddress = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.DChi
                        objHoaDonDienTu.CusPhone = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.SDThoai
                        objHoaDonDienTu.CusTaxCode = objHoaDonDienTu.Content.HDon.DLHDon.NDHDon.NMua.MST
                        objHoaDonDienTu.InvoicePattern = objHoaDonDienTu.Content.HDon.DLHDon.TTChung.KHMSHDon
                        objHoaDonDienTu.SerialNo = objHoaDonDienTu.Content.HDon.DLHDon.TTChung.KHMSHDon
                        objHoaDonDienTu.InvoiceNo = objHoaDonDienTu.Content.HDon.DLHDon.TTChung.SHDon
                    } catch (e1) {
                        try {
                            objHoaDonDienTu.CusName = objHoaDonDienTu.Content.Invoice.Content.CusName
                            objHoaDonDienTu.CusAddress = objHoaDonDienTu.Content.Invoice.Content.CusAddress
                            objHoaDonDienTu.CusPhone = objHoaDonDienTu.Content.Invoice.Content.CusPhone
                            objHoaDonDienTu.CusTaxCode = objHoaDonDienTu.Content.Invoice.Content.CusTaxCode
                            objHoaDonDienTu.InvoicePattern = objHoaDonDienTu.Content.Invoice.Content.InvoicePattern
                            objHoaDonDienTu.SerialNo = objHoaDonDienTu.Content.Invoice.Content.SerialNo
                            objHoaDonDienTu.InvoiceNo = objHoaDonDienTu.Content.Invoice.Content.InvoiceNo
                        } catch (e2) {
                            throw 'Lỗi phân tích nội dung hóa đơn: ' + xml
                        }
                    }

                    objHoaDonDienTu.CusName = !objHoaDonDienTu.CusName ? "" : (!objHoaDonDienTu.CusName._text ? "" : objHoaDonDienTu.CusName._text)
                    objHoaDonDienTu.CusAddress = !objHoaDonDienTu.CusAddress ? "" : (!objHoaDonDienTu.CusAddress._text ? "" : objHoaDonDienTu.CusAddress._text)
                    objHoaDonDienTu.CusPhone = !objHoaDonDienTu.CusPhone ? "" : (!objHoaDonDienTu.CusPhone._text ? "" : objHoaDonDienTu.CusPhone._text)
                    objHoaDonDienTu.CusTaxCode = !objHoaDonDienTu.CusTaxCode ? "" : (!objHoaDonDienTu.CusTaxCode._text ? "" : objHoaDonDienTu.CusTaxCode._text)
                    objHoaDonDienTu.InvoicePattern = !objHoaDonDienTu.InvoicePattern ? "" : (!objHoaDonDienTu.InvoicePattern._text ? "" : objHoaDonDienTu.InvoicePattern._text)
                    objHoaDonDienTu.SerialNo = !objHoaDonDienTu.SerialNo ? "" : (!objHoaDonDienTu.SerialNo._text ? "" : objHoaDonDienTu.SerialNo._text)
                    objHoaDonDienTu.InvoiceNo = !objHoaDonDienTu.InvoiceNo ? "" : (!objHoaDonDienTu.InvoiceNo._text ? "" : objHoaDonDienTu.InvoiceNo._text)
                }

                this.tongtien_hddt = 0;
                this.tongvat_hddt = 0;
                this.tong_hddt = 0;

                let sohoadon = "";
                let pattern = "";
                let seri = "";
                let loaihoadon_id = 0;
                let seri_id = 0;

                let ds_hd_seri = null
                let tt78 = this.HDDT_THONGTU_78 == 1
                if(vtt == true) {
                    if (tt78) {
                        ds_hd_seri = await this.lay_tt_loai_hd_tt78_vtt(ds_token[0]["ten_loaihd"], ds_token[0]["seri"])
                    } else {
                        ds_hd_seri = await this.lay_tt_loai_hd_vtt()
                    }
                }
                else {
                    if (tt78) {
                        ds_hd_seri = await this.lay_tt_loai_hd_tt78(ds_token[0]["ten_loaihd"], ds_token[0]["seri"])
                    } else {
                        ds_hd_seri = await this.lay_tt_loai_hd()
                    }
                }

                if (ds_hd_seri.length > 0) {
                    loaihoadon_id = parseInt(ds_hd_seri[0]["loaihoadon_id"]);
                    pattern = ds_hd_seri[0]["ten_loaihd"];
                    seri_id = parseInt(ds_hd_seri[0]["seri_id"]);
                    seri = ds_hd_seri[0]["seri"];
                }
                else {
                    return "ERR: Không tìm thấy loại hóa đơn, seri !";
                }

                // Kiểm tra hóa đơn hiện tại có đang ở trạng thái Đang sử dụng không ?
                let kq_kt = ""
                if(vtt == true) {
                    if (tt78) {
                        kq_kt = await invoiceService.listInvByCus_TT78_VTT(this, vma_gd, "", "");
                    } else {
                        kq_kt = await invoiceService.listInvByCus_V2_VTT(this, vma_gd, "", "", pattern);
                    }
                }
                else {
                    if (tt78) {
                        kq_kt = await invoiceService.listInvByCus_TT78(this, vma_gd, "", "");
                    } else {
                        kq_kt = await invoiceService.listInvByCus_V2(this, vma_gd, "", "", pattern);
                    }
                }
                if (kq_kt.startsWith("ERR")) {
                    return "ERR: listInvByCus - " + kq_kt.substr("ERR:".length);
                }
                else {
                    if (!tt78) {
                        let item_kt_ls = JSON.parse(convert.xml2json(kq_kt));

                        if (item_kt_ls && item_kt_ls.length > 0) {

                            let item_kt = item_kt_ls.find(e => e['invToken'] == invToken)

                            if (item_kt.status != 1) {
                                return "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể điều chỉnh !";
                            }
                        }
                    }
                }

                //kichhoat:
                let kq = ""
                let tt_nd = await this.lay_tt_user(this.$root.token.getNguoiDungID())
                let GetInvoiceXml_DieuChinh = await this.GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product, objHoaDonDienTu)
                console.log(objHoaDonDienTu)
                console.log(GetInvoiceXml_DieuChinh)
                console.log(vifkey)

                // if(vtt == true) {
                //     if (tt78) {
                //         kq = await invoiceService.AdjustInvoiceAction_TT78_VTT(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern, "11");
                //     } else {
                //         kq = await invoiceService.adjustInv_V2_VTT(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern);
                //     }
                // }
                // else {
                //     if (tt78) {
                //         kq = await invoiceService.AdjustInvoiceAction_TT78(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern, "11");
                //     } else {
                //         kq = await invoiceService.adjustInv_V2(this, GetInvoiceXml_DieuChinh, tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, seri, pattern);
                //     }
                // }

                // if (kq.startsWith("ERR:")) {
                //     return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq.substr("ERR:".length);
                // }

                // let result = kq.split(':');
                let result = ['OK']

                if (result[0] == "OK") {
                    try {
                        let result1 = result[1].toString().split(';');
                        pattern = result1[0].toString();
                        seri = result1[1].toString();
                        sohoadon = result1[2].toString().split('_')[1].toString();
                        let fkey_new = result1[2].toString().split('_')[0].toString();
                        invToken = pattern + ";" + seri + ";" + sohoadon;

                        let row = {}
                        row.BDHDDT_ID = await this.GetKey("BDHDDT_ID");
                        row.MA_TT = vma_gd;

                        if (vkieu == 1) // Điều chỉnh tăng
                            row.KIEU_BD = 8;
                        else if (vkieu == 2) // Điều chỉnh giảm
                            row.KIEU_BD = 9;
                        else if (vkieu == 3) // Điều chỉnh thông tin
                            row.KIEU_BD = 10;
                        else
                            row.KIEU_BD = 1;

                        row.TONGTIEN = this.tong_hddt;
                        row.NGUOI_CN = this.$root.token.getUserName();
                        row.MAY_CN = this.$root.token.getUserName();
                        row.NHANVIEN_ID = this.$root.token.getNhanVienID();
                        row.DONVI_ID = this.$root.token.getDonViID();
                        row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB";
                        row.HDKH_ID = vhdkh_id;
                        row.PHIEUTT_ID = vphieutt_id;
                        row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
                        row.LOAIHOADON_MOI = pattern;
                        row.SERI_CU = objHoaDonDienTu.SerialNo;
                        row.SERI_MOI = seri;
                        row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
                        row.SO_HD_MOI = sohoadon;
                        row.SERI_MOI_ID = seri_id;
                        row.TRANGTHAI = 1;
                        row.BDHDDT_CHA_ID = bdhddt_cha_id;
                        row.TEN_TT = objHoaDonDienTu.CusName;
                        row.DIACHI_TT = objHoaDonDienTu.CusAddress;
                        row.SDT = objHoaDonDienTu.CusPhone;
                        row.MST = objHoaDonDienTu.CusTaxCode;
                        row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                        row.TIEN = this.tongtien_hddt;
                        row.TIENBANGCHU = this.tbc;
                        row.TYLE_VAT = 10;
                        row.VAT = this.tongvat_hddt;

                        let chitiet = this.TaoDuLieuBDCT(product);
                        row.BDCT = JSON.stringify(chitiet)

                        row.HDDT_FKEY = fkey_new
                        row.LOAIHOADON_ID = loaihoadon_id

                        let row1 = {}
                        Object.keys(row).forEach(x => row1[x.toLowerCase()] = row[x])

                        console.log(row1)
                        // await this.luu_bd_hddt(row1)

                        return "OK";
                    }
                    catch (ex) {
                        await this.Log_Action({
                            user_name: this.$root.token.getUserName(),
                            computer_name: this.$root.token.getUserName(),
                            action_name: "chucnang_dieuchinh",
                            key_parameter: "ma_gd = " + vma_gd + ", ngay_cn = " + this.ddmmyyyy(new Date()) + ", Err : " + ex,
                            server_name: this.$root.token.getUserName(),
                            note: "chucnang_dieuchinh",
                        })

                        return "OK";
                    }
                }
                
            }
            catch (ex) {
                return "ERR: " + ex;
            }
        },
    }
}
</script>
<style>
@media (min-width: 1200px) {

    #id-frm-thaydoi-datcoc-huy-email .modal-xl,
    #id-frm-thaydoi-datcoc-huy-lienhe .modal-xl {
        max-width: 90% !important;
    }
}

.frm-thaydoi-datcoc-huy .gutter {
    background-color: transparent;
}

.frm-thaydoi-datcoc-huy #boxLeft {
    width: 100% !important;
}

.frm-thaydoi-datcoc-huy .disable-btn:hover {
    background-color: white !important;
}

.frm-thaydoi-datcoc-huy .disable-btn a {
    color: #6c757d !important;
}

.frm-thaydoi-datcoc-huy .disable-btn a:hover {
    cursor: default;
    color: #6c757d;
}

.frm-thaydoi-datcoc-huy .e-control.e-numerictextbox.e-lib.e-input {
    text-align: right !important;
    padding-right: 5px !important;
}

.frm-thaydoi-datcoc-huy.modal-body .page-content {
    position: inherit !important;
}

.popupComponentPayment .page-content {
    padding: 8px 16px 16px 16px !important;
    top: 90px !important;
    bottom: 40px !important;
}

.dialogObjHoSoKhachHang .page-content {
    top: 65px !important;
}

.dialogObjHoSoKhachHang .page-content .grid-stack-box #boxLeft {
    margin-right: 10px;
    width: calc(20% - 5px);
}

.dialogObjHoSoKhachHang .page-content .grid-stack-box #boxMid {
    margin-right: 10px;
    width: calc(40% - 10px);
}

.dialogObjHoSoKhachHang .page-content .grid-stack-box #boxRight {
    width: calc(40% - 5px);
}

.popup-body.control-ngt .modal-content.popup-box .popup-header {
    display: none;
}

.dis-thoaitra {
    text-align: right;
    display: block;
    line-height: 175%;
}
</style>
