<template src='./HTHSRegisMultisim.html'></template>

<style scoped src='./HTHSRegisMultisim.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue';
import moment from 'moment'
import SearchAccountModal from './components/popups/SearchAccountModal.vue'
import DataGrid2 from '@/components/Controls/DataGrid2'
import {
    DichVuVienThong,
    LoaiHopDong,
    LoaiHinhTB,
    KHOANMUC_TT,
    TrangThaiHD,
    TRANGTHAI_DONGBO,
    TRANGBI,
    KieuLapDat,
} from "./define/index.js";
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {
        VueElement,
        DataGridCustom,
        SearchContract,
        SearchAccountModal,
        DataGrid2
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...collums,
            header: {
                title: "Đăng ký dịch vụ Multisim",
                list: [{
                        name: "Hoàn thiện HS",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Hoàn thiện hồ sơ đăng ký Multisim",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            dsDichvuVT: [],
            dsLoaihinhTB: [],
            txtMaGD:"",
            txtMaHD: "",
            items: {
                ngayyeucau: moment().format('DD/MM/YYYY'),
                ngaylapHD: moment().format('DD/MM/YYYY'),
            },
            txtNgayYC: moment().format('DD/MM/YYYY'),
            dichvuvt_id: 0,
            doituong_id: 1,
            hdkh_id: 0,
            hdtb_id: 0,
            khachhang_id: 0,
            txtTongTienHD:0,
            thuebao_id: 0,
            txtSoSim1:"",
            dtpNgayLapHD:moment().format('DD/MM/YYYY'),
            dtpNgayYeuCau:moment().format('DD/MM/YYYY'),
            txtSoSimCu:"",
            somay: "",
            sosim_phu: "",
            kieuld_id: 0,
            donvi_id: 0,
            trangbi_id: TRANGBI.VIENTHONG_TRANGBI,
            tocdo_id: 0,
            ltb_adsl_id: 1,
            kh_cd: 0,
            ltb_adsl_cu_id: 0,
            ltb_adsl_moi_id: 0,
            phuongld_id: 0,
            phold_id: 0,
            apld_id: 0,
            khuld_id: 0,
            txtMaKH: "",
            txtTenKH: "",
            txtDiaChiKH: "",
            txtLoai: "",
            txtTinhTP: "",
            txtTenTB: "",
            txtDiaChiTB: "",
            txtTienLapMoi: "0",
            txtVatLapmoi: "0",
            txtGhiChu: "",
            txtMaTB: "",
            dtCTKM: [],
            ckeEsim1: {
                Checked: false
            },
            cboKieuLD: {
                SelectedValue: 0,
                Items: [],
                Text: ""
            },
            cboLoaiHinhTB:{
                SelectedValue: 0,
                Items: []
            },
            cboDichVuVT: {
                SelectedValue: 0,
                Items: []
            },
            ckeGoiDen: {
                Checked: false
            },
            ckeGoiDi: {
                Checked: false
            },
            thongTinSim:{
                State:"",
                Message:""
            },
            dgrSimPhu:{
                DataSource: []
            },
            dgrThueBao:{
                DataSource: []
            },
            tt_nd: {
                ngay_cn: this.$auth.getNgayCapNhat(),
                tentat: "",
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                tinh_id: this.$root.token.getPhanVungID()
            },

        };
    },
    mounted() {
        this.frmHTHSMultiSim_Load();
    },
    computed: {

    },
    methods: {
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        },
        //Loadform
        async frmHTHSMultiSim_Load() {
            this.getDanhsach_DichvuVT();
        },
        //Lấy dữ liệu combo dịch vụ VT
        async getDanhsach_DichvuVT() {
            let input = {
                vdichvuvt_id: "2,10"
            }
            let data = this.GetDataList(await api.lay_ds_dichvu(this.axios, input));
            this.dsDichvuVT.Items = data;
            this.cboDichVuVT.SelectedValue = data[0].dichvuvt_id;
            this.getDanhsach_LoaihinhTB()
        },
        //Lấy dữ liệu combo dịch vụ VT
        async getDanhsach_LoaihinhTB() {
            let input = {
                vdichvuvt_id: this.cboDichVuVT.SelectedValue
            }
            let data = this.GetDataList(await api.lay_loaihinh_tbdatmoi(this.axios, input));
            this.dsLoaihinhTB.Items = data;
            this.cboLoaiHinhTB.SelectedValue = data[0].loaitb_id;
            this.getDmKieuYC(this.cboLoaiHinhTB.SelectedValue);
        },
        //Lấy danh mục kiểu lắp đặt
        async getDmKieuYC (loaitb_id) {
            try
            {
                let input ={
                    "loaihd_id": 39,
                    "loaitb_id":loaitb_id
                }
                let data = this.GetDataList(await api.getDmKieuYC(this.axios, input));
                this.cboKieuLD.Items = data;
                this.cboKieuLD.SelectedValue = this.cboKieuLD.Items[0].kieuld_id;
                this.cboKieuLD.Text = this.cboKieuLD.Items[0].ten_kieuld
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy danh sách kiểu lắp đặt: "+ err.message);
            }
        },
        //Nhấn enter vào ô mã giao dịch
        async txtMaGD_KeyPress() {
            if (this.txtMaGD != "") {
                let ds = this.GetData(await api.Lay_ds_hopdong_theo_ma_gd(this.axios, {
                    "ma_gd": this.txtMaGD,
                    "loaihd_id": LoaiHopDong.DK_MULTISIM,
                    "donvi_id": this.tt_nd.donvi_id,
                    "tthd_id": TrangThaiHD.MOI,
                    "nhanvien_id": this.tt_nd.nhanvien_id,
                    "donvi_dl_id": 0
                }));
                if (ds[0].length <= 0)
                    ds = this.GetData(await api.lay_ds_hdtb_thaydoi_sosim(this.axios, {
                        in_ma_tb: "0",
                        in_loaihd_id: LoaiHopDong.THAY_DOI_SO_SIM,
                        in_donvi_id: this.tt_nd.donvi_id,
                        in_tthd_id_1: TrangThaiHD.MOI,
                        in_tthd_id_2: TrangThaiHD.THANH_TOAN,
                        in_dichvuvt_id: this.cboDichVuVT.SelectedValue
                    }));
                let dtList = ds;
                if (ds.length <= 0)
                    this.HienThiDanhSach();
                else
                    this.HienThiTTHopDongKH(ds);
                this.HienThiTTHopDongKH_Index(dtList, 0);
                let dshdtb = this.GetData(await api.Lay_ds_hopdong_tb_theo_hdkh_id(this.axios, {"in_hdkh_id": this.hdkh_id}));
                this.HienThiTTHopDongTB(dshdtb, 0);
            }
        },
        tsbtnLayDSPhieu_Click()
        {
            this.HienThiDanhSach();
        },
        //Hiển thị trạng thái
        async Hienthi_trangthai(somay) {
            let vma_vung = "";
            if (this.dichvuvt_id == DichVuVienThong.GPHONE)
                vma_vung = this.GetData(await api.fn_tt_tinh(this.axios, {
                    type: 8,
                    param: this.tt_nd.tinh_id
                }));
            else
                vma_vung = "84";
            let tttb = this.GetData(await api.ts_tracuu_laytt_tb(this.axios, {
                so_tb: vma_vung + somay
            }));

            if (tttb.length < 1) {
                this.$toast.warning("Tài khoản không có quyền truy cập CCBS!");
                return;
            }
            if (Object.keys(tttb[0]).length == 1) {
                if (tttb[0].hasOwnProperty("KHOSO")&& tttb[0].KHOSO == "ERR-001") {
                    this.$toast.warning(tttb[0]["KHOSO"].toString() + ": Không tìm thấy thông tin thuê bao");
                    return;
                }
                if (tttb[0].hasOwnProperty("VALUE")) {
                    this.$toast.warning(tttb[0]["VALUE"].toString());
                    return;
                }
            } else {
                this.ckeGoiDi.Checked = parseInt(tttb[0]["GOI_DI"]) == 1 ? true : false;
                this.ckeGoiDen.Checked = parseInt(tttb[0]["GOI_DEN"]) == 1 ? true : false;
                this.txtTinhTP = tttb[0]["MA_TINH"].toString();
                this.txtLoai = tttb[0]["LOAI_TB"].toString();
                this.txtSoSimCu = tttb[0]["IMSI"];
                this.ckeEsim1.Checked = parseInt(tttb[0]["ESIM"]) == 1 ? true : false;
                if (tttb[0]["TRA_SAU"].toString() == "1")
                {
                    if (this.dichvuvt_id == DichVuVienThong.GPHONE)
                        this.cboLoaiHinhTB.SelectedValue = LoaiHinhTB.GPHONE;
                    else
                        this.cboLoaiHinhTB.SelectedValue = LoaiHinhTB.DIDONGTRASAU;
                }
                else
                {
                    if (this.dichvuvt_id == DichVuVienThong.GPHONE)
                        this.cboLoaiHinhTB.SelectedValue = LoaiHinhTB.GPHONE_TRATRUOC;
                    else
                        this.cboLoaiHinhTB.SelectedValue = LoaiHinhTB.DIDONGTRATRUOC;
                }
            }
        },
        //Hiển thị thông tin hợp đồng thuê bao
        async HienThiTTHopDongTB(dtList,rows)
        {
            if (dtList.length > 0)
            {
                this.hdtb_id = parseInt(dtList[rows]["hdtb_id"]);
                if (dtList[rows]["thuebao_id"].toString() != "")
                    this.thuebao_id = parseInt(dtList[rows]["thuebao_id"].toString());
                this.txtMaTB = dtList[rows]["ma_tb"].toString();
                this.txtTenTB = this.ChuanHoaTen(dtList[rows]["ten_tb"].toString());
                this.txtDiaChiTB = this.ChuanHoaTen(dtList[rows]["diachi_tb"].toString());
                this.cboDichVuVT.SelectedValue = dtList[rows]["dichvuvt_id"];
                this.cboLoaiHinhTB.SelectedValue = parseInt(dtList[rows]["loaitb_id"].toString());
                this.cboKieuLD.SelectedValue = dtList[rows]["kieuld_id"];
                this.doituong_id = parseInt(dtList[rows]["doituong_id"]);
                this.txtTongTienHD = dtList[rows]["tongtien"];
                this.donvi_id = parseInt(dtList[rows]["donvi_id"]);
                let data_CTKM = this.GetData(await api.lay_tien_hd_km_theo_hdtb(this.axios, {
                    hdtb_id: this.hdtb_id
                }));
                this.dtCTKM = data_CTKM;
                this.kieuld_id = dtList[rows]["kieuld_id"];
                this.Hienthi_trangthai(this.txtMaTB);
                this.HienThiCTTIEN_HD(data_CTKM);
            }
        },
        //Hiển thị danh sách
        async HienThiDanhSach() {
            let ds = this.GetData(await api.lay_ds_hths_multisim(this.axios, {
                in_loaihd_id: LoaiHopDong.DK_MULTISIM,
                in_tthd_id: TrangThaiHD.MOI,
                in_nhanvien_id: this.tt_nd.nhanvien_id,
                in_dichvuvt_id: DichVuVienThong.DI_DONG
            }));
            if (ds.length > 0) {
                this.dgrThueBao.DataSource = ds;
            } else {
                this.dgrSimPhu.DataSource = [];
                this.dgrThueBao.DataSource = [];
                this.Clear();
            }
        },
        //Hiển thị chi tiết tiền hơph đồng
        HienThiCTTIEN_HD(ds)
        {
            let TienLapMoi = 0;
            let VatLapmoi = 0;
            for (let i = 0; i < ds.length; i++)
            {
                TienLapMoi += ds[i]["tien"];
                VatLapmoi += ds[i]["vat"];

            }
            this.txtTienLapMoi = this.formatPrice(TienLapMoi);
            this.txtVatLapmoi = this.formatPrice(VatLapmoi);

        },
        HienThiTTHopDongKH_Index(dtList,rows) {
            try
            {
                if (dtList.length > 0)
                {
                    this.hdkh_id = parseInt(dtList[rows]["hdkh_id"]);
                    this.txtMaGD = dtList[rows]["ma_gd"];
                    this.txtMaHD = dtList[rows]["ma_hd"];
                    this.txtMaKH = dtList[rows]["ma_kh"];
                    if (dtList[rows]["khachhang_id"] != "")
                        this.khachhang_id = parseInt(dtList[rows]["khachhang_id"]);
                    this.txtTenKH = this.ChuanHoaTen(dtList[rows]["ten_kh"]);
                    this.txtDiaChiKH = this.ChuanHoaTen(dtList[rows]["diachi_kh"]);
                    this.dtpNgayLapHD = dtList[rows]["ngaylap_hd"];
                    if (dtList[rows]["ngay_yc"] != "")
                        this.dtpNgayYeuCau = dtList[rows]["ngay_yc"];
                    else
                        this.dtpNgayYeuCau = this.tt_nd.ngay_cn;
                    this.txtGhiChu = dtList[rows]["ghichu"];
                }
            }
             catch (err)
            {
                this.$toast.error("Lỗi hiển thị thông tin hợp đồng khách hàng: "+err.message);
            }
        },
        HienThiTTHopDongKH(ds)
        {
            try
            {
                if (ds.length > 0)
                {
                    this.hdkh_id = parseInt(ds[0]["hdkh_id"]);
                    this.hdtb_id = parseInt(ds[0]["hdtb_id"]);
                    this.kieuld_id = parseInt(ds[0]["kieuld_id"]);
                    this.txtMaGD = ds[0]["ma_gd"];
                    this.txtMaHD = ds[0]["ma_hd"];
                    this.txtMaKH = ds[0]["ma_kh"];
                    if (ds[0]["khachhang_id"] != "")
                        this.khachhang_id = parseInt(ds[0]["khachhang_id"]);
                    this.txtTenKH = this.ChuanHoaTen(ds[0]["ten_kh"]);
                    this.txtDiaChiKH = this.ChuanHoaTen(ds[0]["diachi_kh"]);
                    this.txtTenTB = this.ChuanHoaTen(ds[0]["ten_tb"]);
                    this.txtDiaChiTB = this.ChuanHoaTen(ds[0]["diachi_tb"]);
                    this.txtMaTB = ds[0]["ma_tb"];
                    this.dtpNgayLapHD = ds[0]["ngaylap_hd"];
                    if (ds[0]["ngay_yc"] != "")
                        this.dtpNgayYeuCau = ds[0]["ngay_yc"];
                    else
                        this.dtpNgayYeuCau = this.tt_nd.ngay_cn;
                    this.cboLoaiHinhTB.SelectedValue = parseInt(ds[0]["loaitb_id"]);
                    this.cboKieuLD.SelectedValue = parseInt(ds[0]["kieuld_id"]);
                }

            }
             catch (err)
            {
                this.$toast.error("Lỗi hiển thị thông tin hợp đồng khách hàng: "+err.message);
            }
        },
        async tsbtnKichHoat_Click()
        {
            if (this.hdtb_id == 0)
            {
                this.$toast.warning("Chưa có hợp đồng nào!");
                return;
            }
            let input1 = {
                type: 3,
                param: this.hdtb_id,
                param1: 0
            }
             let conSimPhuChuaXuLyTruocKhiXuLy = this.GetData(await api.fn_tt_hdtb_multisim(this.axios, input1));
             if (conSimPhuChuaXuLyTruocKhiXuLy == "-1")
             {
                this.$toast.warning("Sim phụ đã được đăng ký thành công hết rồi");
                return;
             }
            if (parseInt(this.cboKieuLD.SelectedValue) == KieuLapDat.DK_MULTISIM)
                this.KhoiTaoSimPhu();
            if (parseInt(this.cboKieuLD.SelectedValue) == KieuLapDat.HUY_MULTISIM)
                this.HuySimPhu();
            // kiểm tra nếu sim đã xử lý hết thì cập nhật trang thái status = 5 : đã kích hoạt.
            let conSimPhuChuaXuLy = this.GetData(await api.fn_tt_hdtb_multisim(this.axios, input1));
            if (conSimPhuChuaXuLy == "-1")
            {
                let kichhoat  = this.GetData(await api.capnhat_hdtb_status(this.axios, {
                    hdtb_id: this.hdtb_id
                }));

                this.$root.$toast.success("Đã kích hoạt thành công!");
            }
            this.HienThiDanhSach();
        },
        async KhoiTaoSimPhu()
        {
            let somaychinh = this.grvSimPhu.DataSource[0].ma_tb;
            let dssimphudadk = [];
            for (let i = 0; i < this.grvSimPhu.DataSource.length; i++)
            {
                if (this.grvSimPhu.DataSource[i].trangthai_sim == "1"){
                    let simphu = this.grvSimPhu.DataSource[i].sosim_phu;
                    let simphu1 = "", simphu2 = "", simphu3 = "";
                    let soTTCoTheDK = this.LaySoTTSimPhuCoTheDK("84" + somaychinh);
                    switch (soTTCoTheDK)
                    {
                        case 1:
                            simphu1 = simphu;
                            break;
                        case 2:
                            simphu2 = simphu;
                            break;
                        case 3:
                            simphu3 = simphu;
                            break;
                        default:
                            return;
                    }
                    let result = []
                    try
                    {
                        let phuongthuc = this.LayThongTinPhuongThuc(somaychinh);
                        if (phuongthuc == null)
                        {
                            this.$toast.warning("Không lấy được phương thức từ hàm multisim_dichvu_kt_thuebao :" + somaychinh);
                            return;
                        }
                        result  = this.GetData(await api.multisim_dichvu_khoitaosim(this.axios, {
                                so_tb: "84" + somaychinh,
                                imsi1: simphu1,
                                imsi2: simphu2,
                                imsi3: simphu3,
                                kenh_th:this.$auth.getMaCCBS(),
                                phuong_thuc: phuongthuc}));
                    }
                    catch (err)
                    {
                        this.$toast.error("Có lỗi multisim_dichvu_khoitaosim " +err.message);
                        return;
                    }
                    if (result)
                    {

                        let capnhat  = this.GetData(await api.capnhat_hdtb_multisim_da_khoitao(this.axios, {
                            "hdtb_id": this.hdtb_id,
                            "simphu": simphu
                        }));
                    }
                    else
                    {
                        this.$toast.error("Có lỗi khởi tạo sim phụ : " +result.message);
                        return;
                    }
                }
            }
        },
        async HuySimPhu()
        {
            var somay = this.grvSimPhu.DataSource[0].ma_tb

            for (let i = 0; i < this.grvSimPhu.DataSource.length; i++)
            {
                if (this.grvSimPhu.DataSource[i].trangthai_sim == "1"){
                    var simphu = this.grvSimPhu.DataSource[i].sosim_phu;
                    var result = [];
                    try
                    {
                        result  = this.GetData(await api.multisim_dichvu_huysim(this.axios, {
                            "so_tb": "84" + somay,
                            "imsi": simphu,
                            "hdtb_id": this.hdtb_id
                        }));
                    }
                    catch (err)
                    {
                        this.$toast.error("Có lỗi multisim_dichvu_huysim " +err.message);
                        return;
                    }
                    if (result)
                    {
                        let capnhat  = this.GetData(await api.capnhat_hdtb_multisim_da_khoitao(this.axios, {
                            "hdtb_id": this.hdtb_id,
                            "simphu": simphu
                        }));
                    }
                    else
                    {
                        this.$toast.error("Có lỗi hủy sim phụ : " +result.message);
                        return;
                    }
                }

            }
        },
        async tsnbtnHoanThien_Click()
        {
            if (this.hdtb_id == 0)
            {
                this.$toast.warning("Chưa có hợp đồng nào ");
                return;
            }
            let input1 = {
                type: 3,
                param: this.hdtb_id,
                param1: 0
            }
            let conSimPhuChuaXuLy = this.GetData(await api.fn_tt_hdtb_multisim(this.axios, input1));
            if (conSimPhuChuaXuLy == "-1")
            {
                let capnhat_hdtb = this.GetData(await api.capnhat_hdtb_trangthaihd(this.axios,  {
                    "hdtb_id": this.hdtb_id
                }));
                let capnhat_ph = this.GetData(await api.capnhat_giaophieu_ttph(this.axios,  {
                    "hdtb_id": this.hdtb_id
                }));
                this.$root.$toast.success("Đã hoàn thiện thành công hợp đồng của số máy " + this.somay);
            }
            else
            {
                this.$toast.warning("Không được hoàn thiện khi còn sim phụ chưa đồng bộ lên ccbs : " + conSimPhuChuaXuLy);
                return;
            }
            this.HienThiDanhSach();
        },
        async LayThongTinPhuongThuc(somay)
        {
            let data  = this.GetData_CCBS(await api.multisim_dichvu_kt_thuebao(this.axios, {
                    so_tb: "84" + somay
                }));
            if (data)
                return data.result;
            else
                return null;
        },
        async LaySoTTSimPhuCoTheDK(somaychinh)
        {
            try
            {
                let dsSoTT = [ "1", "2", "3" ];
                let dtThongTinSimPhuDaDK  = this.GetData_CCBS(await api.multisim_dichvu_tt_thuebao(this.axios, {
                    so_tb: somaychinh //84918165456
                }));
                if (dtThongTinSimPhuDaDK != null && dtThongTinSimPhuDaDK.length > 0)
                {
                    if (dtThongTinSimPhuDaDK.length >= 3)
                        return -1;
                    for (let i = 0; i < dtThongTinSimPhuDaDK.length; i++)
                    {
                        let dtThongTinSomaySimphu  = this.GetData_CCBS(await api.multisim_doisim_ttsimcu(this.axios, {
                            so_sim_cu: dtThongTinSimPhuDaDK[i]["imsi"]//2220426087
                        }));
                        //"84812587017|17/10/2022|1|0|1|HUE|0"
                        let so_tb_phu = dtThongTinSomaySimphu.result.split("|")[0];
                        let dtSoTTSimphu  = this.GetData_CCBS(await api.multisim_tt_thuebao(this.axios, {
                            so_tb_phu: so_tb_phu //84812587017
                        }));
                        // "THUTU_IMSI": 1,
                        dsSoTT.splice(dtSoTTSimphu[0].indexOf(dtSoTTSimphu[0]["THUTU_IMSI"]), 1);
                    }

                }
                return parseInt(Math.min.apply(null, dsSoTT));
            }
            catch (err)
            {

                this.$toast.error(err.message);
            }


        },
        async KiemTraSoSimMoi(somay,soSim)
        {

            let vma_vung = "";

            vma_vung = "84";

            let sttSimMoi  = this.GetData_CCBS(await api.tt_ts_get_tb_esim(this.axios, {
                    so_tb: vma_vung + somay,
                    so_msin_moi : soSim
                }));

            this.ckeEsim1.Checked = parseInt(sttSimMoi[0]["ESIM"]) == 1 ? true : false;
            switch (parseInt(sttSimMoi[0]["KQUA_CHK"]))
            {
                case 0:
                    this.thongTinSim.State = 'ChuaKhoiTao';
                    this.thongTinSim.Message = "Sim hợp lệ";
                    break;
                case 1:
                    this.thongTinSim.State = 'KhongHopLe';
                    this.thongTinSim.Message = "Đã cấp cho số : " + sttSimMoi[0]["TTIN_ADD"].toString();
                    break;
                case 2:
                    this.thongTinSim.State = 'KhongHopLe';
                    this.thongTinSim.Message = "Sim không nằm trong dải sim hợp lệ của thuê bao";
                    break;
                case 3:
                    this.thongTinSim.State = 'KhongHopLe';
                    this.thongTinSim.Message = "Số sim mới không nằm trong kho sim";
                    break;
                case 4:
                    this.thongTinSim.Message = "Số sim mới chưa khởi tạo AC, fax yêu cầu về TTHTKH VINAPHONE để khởi tạo sim";
                    this.thongTinSim.State = 'KhongHopLe';
                    break;
                case 5:
                    this.thongTinSim.Message = "Số thuê bao chưa có ngày kích hoạt";
                    this.thongTinSim.State = 'KhongHopLe';
                    break;
                case 6:
                    this.thongTinSim.Message = "Sim đã bị hủy do CAN thuê bao";
                    this.thongTinSim.State = 'KhongHopLe';
                    break;
                case 7:
                    this.thongTinSim.Message = "Sim đã hủy do đã đổi sim";
                    this.thongTinSim.State = 'KhongHopLe';
                    break;
                case 8:
                    this.thongTinSim.Message = "Sim chưa được kích hoạt";
                    this.thongTinSim.State = 'KhongHopLe';
                    break;
            }
             this.$toast.warning(this.thongTinSim.Message);
        },
        KiemTraDL_KhachHang()
        {
            if (txtTenKH.trim() == "")
            {
                this.$toast.warning("Hãy nhập tên khách hàng !");
                this.txtTenKH.focus();
                return;
            }

            if (this.txtMaKH.trim() == "" && this.khachhang_id != 0)
            {
                this.$toast.warning("Kiểm tra lại mã khách hàng !");
                this.txtMaKH.focus();
                return;
            }
            if (txtSoSim1.trim() == "")
            {
                this.$toast.warning("Kiểm tra lại số Sim mới !");
                this.txtSoSim1.focus();
                return;
            }
        },
        async onChangeLoaihinh(row) {
            await this.getDmKieuYC(row);
        },
        async onChangeDichvuVT(row) {
            this.cboDichVuVT.SelectedValue = row;
            this.getDanhsach_LoaihinhTB()
        },
        async onChangeKieuLD(row){
            this.cboKieuLD.SelectedValue = row;
        },
        async txtMaTB_KeyPress() {
            if (this.txtMaTB != "") {
                this.HienThiTT_DanhBa(this.txtMaTB);
            }

        },
        //HienThiTT_DanhBa
        async HienThiTT_DanhBa (txtMaTB){

            if (this.cboDichVuVT.SelectedValue == 0){
                this.$toast.error("Bạn cần chọn dịch vụ để lấy thông tin!");
            }else{

                var input = {
                        "maTb": txtMaTB,
                        "dichVuVtId": this.cboDichVuVT.SelectedValue,
                        "donViDlId": null
                }
                let data = [] ;
                let data_obj = this.GetData(await api.getTTDanhBa_MaTB(this.axios, input));
                data.push(data_obj);

                if (data[0] != null){
                        this.thuebao_id = parseInt(data[0].thuebao_id.toString());
                        //Thông tin khách hàng
                        this.txtMaKH = data[0].ma_kh.toString();
                        this.txtMaHD = data[0].ma_hd.toString();
                        this.khachhang_id = data[0].khachhang_id.toString();
                        this.txtTenKH = data[0].ten_kh.toString();
                        this.txtDiaChiKH = this.ChuanHoaTen(data[0].diachi_kh.toString());
                        //Thông tin thuê bao
                        this.txtMaTB = data[0].ma_tb.toString();
                        this.txtTenTB = this.ChuanHoaTen(data[0].ten_tb.toString());
                        this.txtDiaChiTB = this.ChuanHoaTen(data[0].diachi_tb.toString());
                        this.txtDiaChiLD = this.ChuanHoaTen(data[0].diachi_ld.toString());
                        this.txtSoAo = data[0].ma_lt
                        this.donvi_id = parseInt(data[0].donviql_id);
                        this.doituong_id = parseInt(data[0].doituong_id);
                        this.cboDichVuVT.SelectedValue = data[0].dichvuvt_id.toString();
                        this.cboLoaiHinhTB.SelectedValue = data[0].loaitb_id.toString();
                }else {
                    this.$toast.warning("Không tìm thấy thông tin về mã thuê bao " + this.txtMaTB.trim() + " đối với dịch vụ " + this.dataSelected.ten_dvvt);
                }
            }
        },
        showPopupSearchContract() {
            this.modalSearch = {
                loai_hd_id : LoaiHopDong.DK_MULTISIM,
                trangthai_hd : TrangThaiHD.MOI
            }
            this.$refs.popupFrmTraCuuHopDong.$children[0].clearData();
            this.$refs.popupFrmTraCuuHopDong.$children[0].loadPopup();
            this.$refs.popupFrmTraCuuHopDong.show();
        },
        async acceptSearchContract(item) {
        this.txtMaGD = item.hopdong.ma_gd;
        this.$refs.popupFrmTraCuuHopDong.hide();
        if (this.txtMaGD != "")
            {
                let dtList = this.GetData(await api.Lay_ds_hopdong_theo_ma_gd(this.axios,
                {
                    "ma_gd":this.txtMaGD,
                    "loaihd_id":LoaiHopDong.DK_MULTISIM,
                    "donvi_id":this.tt_nd.donvi_id,
                    "tthd_id":TrangThaiHD.MOI,
                    "nhanvien_id":this.tt_nd.nhanvien_id,
                    "donvi_dl_id":0
                }));
                await this.HienThiTTHopDongKH(dtList);
                let dshdtb = this.GetData(await api.Lay_ds_hopdong_tb_theo_hdkh_id(this.axios, {"in_hdkh_id": this.hdkh_id}));
                this.HienThiTTHopDongTB(dshdtb, 0);
            }
        },
        dgrThueBaoSimPhu_SelectionChanged(row){
            this.txtSoSim1 = row.data.sosim_phu
        },
        async gridviewDSHopDong_SelectionChanged(row){
            this.hdtb_id = parseInt(row.data.hdtb_id);
            let somay = row.data.ma_tb;
            let input = {
                in_ma_tb: somay,
                in_loaihd_id: LoaiHopDong.DK_MULTISIM,
                in_donvi_id: this.tt_nd.donvi_id,
                in_tthd_id_1: TrangThaiHD.MOI,
                in_dichvuvt_id: DichVuVienThong.DI_DONG
            }
            let currentData =  this.GetData(await api.lay_ds_hdtb_multisim(this.axios, input));
            this.HienThiTTHopDongKH(currentData);
            this.HienThiTTHopDongTB(currentData, 0);
            currentData.forEach(function (obj) {
                        obj.ngay_dang_ky = moment(obj.ngay_yc).format('DD/MM/YYYY');
                    });
            this.dgrSimPhu.DataSource = currentData;
        },
        showPopupSearchAccount() {
            this.$refs.searchAccountModal.showModal();
        },
        async acceptSearchAccount(item) {
            this.txtMaTB = item.ma_tb;
        },
        changeKeysToUpper(obj) {
            var key, upKey;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) {
                    upKey = key.toUpperCase();
                    if (upKey !== key) {
                        obj[upKey] = obj[key];
                        delete(obj[key]);
                    }
                    // recurse
                    if (typeof obj[upKey] === "object") {
                        this.changeKeysToUpper(obj[upKey]);
                    }
                }
            }
            return obj;
        },
        Clear() {
            this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;
            this.hdkh_id = 0;
            this.txtTongTienHD = "0";
            this.txtMaGD = "";
            this.txtMaHD = "";
            this.txtMaKH = "";
            this.khachhang_id = 0;
            this.txtTenKH = "";
            this.txtDiaChiKH = "";
            this.dtpNgayLapHD = this.tt_nd.ngay_cn;
            this.dtpNgayYeuCau = tt_nd.ngay_cn;
            this.txtSoSim1 = "";
            this.somay = "";
            this.txtLoai = "";
            this.txtTinhTP = "";
            this.ckeEsim1.Checked = false;
            this.ckeGoiDen.Checked = false;
            this.ckeGoiDi.Checked = false;

            this.hdtb_id = 0;
            this.thuebao_id = 0;
            this.txtTenTB = "";
            this.txtDiaChiTB = "";
            //Clear Kiểu lắp đặt
            this.txtTienLapMoi = "0";
            this.txtMaTB.Focus();
            this.doituong_id = 1;
            this.txtGhiChu = "";
            this.txtMaTB = "";
            this.sosim_phu = "";
            this.dgrSimPhu = [];

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
        GetData_CCBS: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00004000') {
                return null
            } else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return null
        },
        //Chuẩn hóa tên
        ChuanHoaTen (ten) {
            if(ten!=null){
                let convertToArray = ten.toLowerCase().split(' ');
                let result = convertToArray.map(function(val) {
                        return val.replace(val.charAt(0), val.charAt(0).toUpperCase());
                        });
                return result.join(' ');
            } else return ''
        },
    },
    created: async function () {},
    watch: {

    }
};
</script>
<style>
.btn-plus {
    color: black !important;
    background-color: transparent !important;
    border-color: transparent !important;
}
.e-control {
}
.modal {
    padding-top: 50px !important;
}
</style>
