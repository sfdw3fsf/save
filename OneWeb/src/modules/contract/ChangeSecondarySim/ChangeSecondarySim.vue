
<template src='./ChangeSecondarySim.html'></template>


<style scoped src='./ChangeSecondarySim.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import moment from 'moment'
// import SearchContractModal from './components/SearchContractModal.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";


export default {
    components: {
        VueElement,
        SearchContractModal,
        DataGridCustom,
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
                title: "thay đổi số sim phụ",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Lắp đặt mới",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            txtNoidung: "",

            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnXoa: false,
            tsbtnHuyBo: false,
            tsbtnCapNhatDB: false,
            hdkh_id: 0,
            txtMaGD: "",
            txtMaTB: "",
            khachhang_id: 0,
            txtTenTB: "",
            txtDiaChiTB: "",
            txtNgayYC: moment().format('DD/MM/YYYY'),
            txtSoSimMoi: "",
            txtSoSimCu: "",
            txtGhiChu: "",
            txtTongTienHD: "",
            txtVatLapmoi:"",
            tien: "", //Tiền
            vat: "", //Vat
            chkeSim: 0,
            txtNoiCap: "",
            txtNgayCap: "",
            kt_cd: true,
            kt_lm: true,
            txtSoGT: "",
            txtTinhTP:"",
            hdtb_id: 0,
            ct_tienhd_id: 0,
            thuebao_id: 0,
            ckeGoiDen:0,
            ckeGoiDi:0,
            dichvuvt_id: 0,
            txtSoSimMoi: "",
            txtSoSimCu: "",
            txtSoMayPhu: "",
            txtTienLapMoi: 0,
            txtLoaiTB:"",
            LoaiHinhTB: {
                DIDONGTRATRUOC: 21,
                DIDONGTRASAU: 20,
            },
            DichVuVienThong: {
                DI_DONG: 2,
            },
            LoaiHopDong: {
                CHUYENDOI_LH: 6,
                THAY_DOI_DV: 7,
                THUEBAO_TRATRUOC: 13,
                THAY_DOI_SO_SIM: 12
            },
            KieuLapDat: {
                DOISIM_TRATRUOC: 170,
            },
            TrangThaiHD: {
                MOI: 1,
                THANHTOAN: 2,
                HOAN_THANH: 6,
                THOAITRA: 7,
            },
            KHOANMUC_TT: {
                KMTT_DOISIM: 6,
                KMTT_CAIDAT: 9,
            },
            dataSelected: {
                chude_id: 0,
                mangthi_id: 0
            },
            txtMaGD: "",
            rowSelection: '',
            json: {},
            options: {
                DSHDmoi_DaThanhToan: [],
                DSHDbyMaGD: [],
                dsHDKH: {},
                dsHDKHs: [],
                dsHDTB: {},
                dsHDTBs: [],
                dsTDSoSim: {},
                dsTDSoSims: [],
                dsMultiSim: {},
                dsMultiSims: [],
                DBTraTruocData: {},
                DBTraTruocDatas: [],
                dsCTTHD: {},
                dsCTTHDs: [],
                Ttsimcu: [],
                dtTTSimChinh: [],
                dtThongTinThuebao: [],
                dtLichSuKH: [],
            },
        };
    },
    mounted() {
        this.init();
    },
    computed: {

    },
    methods: {
        async init() {
            try {
                this.SetButton(-1);
                this.HienThiDanhSach();
            } catch (e) {
                console.log(e);
                this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
            }
        },
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        },
        showPopupSearchContract() {
            this.$refs.popupSearchContract.showModal();
        },
        acceptSearchContract(item) {
           
            this.txtMaGD = item.ma_gd;
        },
        // Tạo dữ liệu cho Dataset
        TaoDuLieu(themmoi) {
            this.TaoDuLieu_HDKH(themmoi);
            this.TaoDuLieu_HDTB(themmoi);
            this.TaoDuLieu_TDSoSim(themmoi);
            this.TaoDuLieu_CT_TIENHD(true);
            this.TaoDuLieu_MultiSim(themmoi);
        },
        async getKeys (data) {
            let response = await api.getKeys(this.axios, data)
            try {
                return response.data.data
            } catch (error) {
                throw error
            }
        },
        //Tao du lieu cho bang HD_KHACHHANG
        async TaoDuLieu_HDKH(themmoi) {
            if (themmoi == true) {
                    this.options.dsHDKH.HDKH_ID = this.hdkh_id;
                    this.getMaGD();
                    this.options.dsHDKH.MA_GD = this.txtMaGD.toString();
                    if (this.khachhang_id > 0) {
                         this.options.dsHDKH.KHACHHANG_ID = this.khachhang_id;
                    }
                    this.options.dsHDKH.NGAYLAP_HD = this.dtpNgayLapHD;
                    this.options.dsHDKH.DIACHI_KH = this.txtDiaChiTB;
                    this.options.dsHDKH.DONVI_ID = this.donviId;
                    this.options.dsHDKH.NHANVIEN_ID = this.nhanvienId;
                    this.options.dsHDKH.KHLON_ID = 1;
                    this.options.dsHDKH.LOAIHD_ID = this.LoaiHopDong.THUEBAO_TRATRUOC;
                    this.options.dsHDKH.NGUOI_CN = this.$root.token.getUserName();
                    this.options.dsHDKH.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss');
                    this.options.dsHDKH.MAY_CN = this.$root.token.getMaNhanVien();
                    this.options.dsHDKH.NGAY_YC = this.txtNgayYC;
                    this.options.dsHDKHs.push(this.options.dsHDKH);
            }


        },
        //Tao du lieu cho bang HD_THUEBAO
        async TaoDuLieu_HDTB(themmoi) {
            if (themmoi == true) {
                    this.options.dsHDTB.HDTB_ID = this.hdtb_id;
                    this.options.dsHDTB.HDKH_ID = this.hdkh_id;
                    if (this.thuebao_id > 0) {
                        this.options.dsHDTB.THUEBAO_ID = this.thuebao_id;
                    }
                    this.options.dsHDTB.MA_TB = this.txtMaTB;
                    this.options.dsHDTB.TEN_TB = this.txtTenTB;
                    this.options.dsHDTB.DIACHI_TB = this.txtDiaChiTB;
                    this.options.dsHDTB.GHICHU = this.txtGhiChu;
                    this.options.dsHDTB.KIEULD_ID = this.KieuLapDat.DOISIM_TRATRUOC;
                    this.options.dsHDTB.DOITUONG_ID = this.doituong_id;
                    this.options.dsHDTB.TTHD_ID = this.TrangThaiHD.MOI;
                    this.options.dsHDTB.LOAITB_ID = this.loaitb_id;
                    this.options.dsHDTB.DICHVUVT_ID = this.dichvuvt_id;
                    this.options.dsHDTB.NGUOI_CN = this.$root.token.getUserName();
                    this.options.dsHDTB.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss');
                    this.options.dsHDTB.MAY_CN = this.$root.token.getMaNhanVien();
                    this.options.dsHDTB.QUYTRINH_ID = 513;
                    this.options.dsHDTBs.push(this.options.dsHDTB);
            }
        },
        // Tao du lieu cho bang HDTB_DD
        TaoDuLieu_TDSoSim(themmoi) {
            if (themmoi == true) {
                this.options.dsTDSoSim.HDTB_ID = this.hdtb_id;
                this.options.dsTDSoSim.SIMCARD = this.txtSoSimMoi;
                this.options.dsTDSoSims.push(this.options.dsTDSoSim);
            }
        },
        // Tao du lieu cho bang HDTB_MULTISIM
        TaoDuLieu_MultiSim(themmoi) {
            if (themmoi == true) {
                this.options.dsMultiSim.HDTB_ID = this.hdtb_id;
                this.options.dsMultiSim.SIMCARD = this.txtSoSimCu;
                this.options.dsMultiSim.DA_KHOITAO = 0;
                this.options.dsMultiSim.SOMAY_PHU = this.txtSoMayPhu;
                this.options.dsMultiSims.push(this.options.dsMultiSim);
            }
        },
        // Tạo dữ liệu cho bảng CT_TIENHD
        async TaoDuLieu_CT_TIENHD(themmoi) {
            this.options.dsMultiSim.SOMAY_PHU = this.txtSoMayPhu;
                if (themmoi == true) {
                        this.options.dsCTTHD.CT_TIENHD_ID =  this.ct_tienhd_id;
                        this.options.dsCTTHD.HDTB_ID =  this.hdtb_id;
                        this.options.dsCTTHD.KHOANMUCTT_ID =  this.KHOANMUC_TT.KMTT_DOISIM;
                        this.tien = this.formatPrice(this.txtTienLapMoi);
                        this.options.dsCTTHD.TIEN =  this.tien;
                        this.vat = this.formatPrice(this.txtVatLapmoi);
                        this.options.dsCTTHD.VAT =  0.1 * parseInt(this.tien);
                        this.options.dsCTTHD.LOAI_ID =  1;
                        this.options.dsCTTHD.ID =  this.KHOANMUC_TT.KMTT_DOISIM;
                        this.options.dsCTTHDs.push(this.options.dsCTTHD);
                }
        },
        //Sinh mã giao dịch
        async getMaGD() {
            await api.getMaGD(this.axios, {
                "loaihd_id": this.LoaiHopDong.THAY_DOI_SO_SIM
            }).then((res) => {
                this.txtMaGD = res.data.data;
            });
        },
        //Nhấn enter vào ô mã giao dịch
        async onMaGDEnter() {
            if (this.txtMaGD != "") {
                console.log({
                     "ma_gd": this.txtMaGD,
                     "loaihd_id": this.LoaiHopDong.THUEBAO_TRATRUOC,
                     "donvi_id": this.donviId,
                     "tthd_id": this.TrangThaiHD.MOI,
                     "nhanvien_id":  this.nhanvienId,
                     "donvi_dl_id": 0,
                });
                await api.getDsHDByMaGD(this.axios, {
                     "ma_gd": this.txtMaGD,
                     "loaihd_id": this.LoaiHopDong.THUEBAO_TRATRUOC,
                     "donvi_id": this.donviId,
                     "tthd_id": this.TrangThaiHD.MOI,
                     "nhanvien_id":  this.nhanvienId,
                     "donvi_dl_id": 0,
                }).then((res) => {
                    console.log( this.options.DSHDbyMaGD );
                    this.options.DSHDbyMaGD = res.data.data ? res.data.data : [];
                    this.HienThiTTHopDongKH(this.options.DSHDbyMaGD, 0);
                    this.HienThiTTHopDongTB(this.options.DSHDbyMaGD, 0);
                });
            }
        },
        // Hiển thị thông tin khách hàng lên form
        async HienThiTTHopDongKH(dtList, rows) {
            try {
               
                if (dtList.length > 0) {
                    this.hdkh_id = dtList[rows].hdkh_id;
                    this.txtMaGD = dtList[rows].ma_gd;
                    this.khachhang_id = dtList[rows].khachhang_id;
                    this.txtTenTB = dtList[rows].ten_kh;
                    this.txtDiaChiTB = dtList[rows].diachi_kh;
                    this.dtpNgayLapHD = dtList[rows].ngaylap_hd;
                    this.txtMaTB = dtList[rows].ma_tb;
                    this.loaitb_id = dtList[rows].loaitb_id;
                    this.doituong_id = dtList[rows].doituong_id;
                    this.hdtb_id = dtList[rows].hdtb_id;
                    console.log({
                        "p_ma_tb": this.txtMaTB,
                        "p_loaihd_id": this.LoaiHopDong.THUEBAO_TRATRUOC,
                        "p_donvi_id": this.donviId,
                        "p_tthd_id_1": this.TrangThaiHD.MOI,
                        "p_tthd_id_2": this.TrangThaiHD.THANHTOAN
                    });
                    await api.getDsPhieuYeucau(this.axios, {
                        "p_ma_tb": this.txtMaTB,
                        "p_loaihd_id": this.LoaiHopDong.THUEBAO_TRATRUOC,
                        "p_donvi_id": this.donviId,
                        "p_tthd_id_1": this.TrangThaiHD.MOI,
                        "p_tthd_id_2": this.TrangThaiHD.THANHTOAN
                    }).then((res) => {
                        if (res.data.data.length > 0) {            
                            this.txtSoSimMoi = res.data.data[0].sosim_moi;
                            this.txtSoSimCu = res.data.data[0].simcard;
                            this.txtSoMayPhu = res.data.data[0].somay_phu;
                        }
                        this.SetButton(3);
                    });
                } else {
                    this.SetButton(0);
                }
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        // Hiển thị thông tin thuê bao
        async HienThiTTHopDongTB(dtList, rows) {
            try {
                if (dtList.length > 0) {
                    this.hdtb_id = dtList[rows].hdtb_id;
                    this.thuebao_id = dtList[rows].thuebao_id;
                    this.dichvuvt_id = dtList[rows].dichvuvt_id;
                    this.loaitb_id = dtList[rows].loaitb_id;
                    this.doituong_id = dtList[rows].doituong_id;
                    this.txtMaTB = dtList[rows].ma_tb;
                    this.txtSoSimMoi = dtList[rows].sosim_moi;
                    this.txtTongTienHD = dtList[rows].tongtien;
                }
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        async HienThiDanhSach() {
            //Hiển thị hợp đồng mới nhập và hợp đồng đã thanh toán
            this.loading(true);
            try {
                 console.log({
                    "p_ma_tb": "0",
                    "p_loaihd_id": this.LoaiHopDong.THUEBAO_TRATRUOC,
                    "p_donvi_id": this.donviId,
                    "p_tthd_id_1": this.TrangThaiHD.MOI,
                    "p_tthd_id_2":this.TrangThaiHD.THANHTOAN
                });
                await api.getDsPhieuYeucau(this.axios, {
                    "p_ma_tb": "0",
                    "p_loaihd_id": this.LoaiHopDong.THUEBAO_TRATRUOC,
                    "p_donvi_id": this.donviId,
                    "p_tthd_id_1": this.TrangThaiHD.MOI,
                    "p_tthd_id_2":this.TrangThaiHD.THANHTOAN
                }).then((res) => {
                    this.options.DSHDmoi_DaThanhToan = res.data.data ? res.data.data : [];
                    if (this.options.DSHDmoi_DaThanhToan.length == 0) {
                        this.Clear();
                    }            
                });
            } catch (err) {
                console.log(err)
                this.loading(false);
            }
            this.loading(false);
        },
        
        txtSoSimMoi_KeyPress() {
            if (this.KiemTraSoSimMoi(this.txtSoMayPhu, this.txtSoSimMoi)) {
                this.$root.$toast.success("Sim mới hợp lệ!");
            } else {
                this.$toast.warning("Sim mới không hợp lệ");
            }
        },
        async CapNhat() {
            try {
;                this.KiemTraDL_KhachHang();
                if (this.tsbtnNhapMoi == true) {
                    this.hdkh_id = await this.getKeys({
                            "keyname": "HD_KHACHHANG",
                            "numblocksize": 1,
                            "numretry": 10
                        });
                    console.log( this.hdkh_id);
                    this.hdtb_id = await this.getKeys({
                            "keyname": "HD_THUEBAO",
                            "numblocksize": 1,
                            "numretry": 10
                        });
                    console.log( this.hdtb_id);
                    this.ct_tienhd_id = await this.getKeys({
                            "keyname": "CT_TIENHD",
                            "numblocksize": 1,
                            "numretry": 10
                        });
                     console.log( this.ct_tienhd_id);
                    console.log(JSON.stringify(this.options.dsHDKHs));
                    console.log(JSON.stringify(this.options.dsHDTBs));
                    console.log(JSON.stringify(this.options.dsTDSoSims));
                    console.log(JSON.stringify(this.options.dsCTTHDs));
                    console.log(JSON.stringify(this.options.dsMultiSims));
                    this.TaoDuLieu(true);
                    // await api.Themmoihopdong(this.axios, {
                    //     "p_js_hd_khachhang": JSON.stringify(this.options.dsHDKH),
                    //     "p_js_hd_thuebao": JSON.stringify(this.options.dsHDTB),
                    //     "p_js_hdtb_dd": JSON.stringify(this.options.dsTDSoSim),
                    //     "p_js_ct_tienhd": JSON.stringify(this.options.dsCTTHD),
                    //     "p_js_hdtb_multisim": JSON.stringify(this.options.dsMultiSim),
                    //     "p_huonggiao_id": 3071
                    // }).then((res) => {
                    //     console.log(res);
                    //     if(res.data.error_code="BSS-00000000")
                    //     {
                    //         this.SetButton(3);
                    //         this.$root.$toast.success('Đã thêm mới hợp đồng thành công.')
                    //         this.HienThiDanhSach();
                    //     }else{
                    //         this.$toast.error("Lỗi không thể thực hiện: " + res.data.message);
                    //     }
                    // });
                } else {
                    this.TaoDuLieu(false);
                    // await api.Capnhathopdong(this.axios, {
                    //     "p_js_hd_khachhang": JSON.stringify(fthis.options.dsHDKH),
                    //     "p_js_hd_thuebao": JSON.stringify(this.options.dsHDTB),
                    //     "p_js_hdtb_dd": JSON.stringify(this.options.dsTDSoSim),
                    //     "p_js_hdtb_multisim": JSON.stringify(this.options.dsMultiSim),
                    // }).then((res) => {
                    //     console.log(res);
                    //     if(res.data.error_code="BSS-00000000")
                    //     {
                    //         this.SetButton(3);
                    //         this.$root.$toast.success('Đã cập nhật hợp đồng thành công.')
                    //         this.HienThiDanhSach();
                    //     }else{
                    //         this.$toast.error("Lỗi không thể thực hiện: " + res.data.message);
                    //     }
                    // });
                }
               
            } catch (e) {
                console.log(e);
                this.$toast.error("Lỗi không thể thực hiện: " + e);
            }
        },
        //Button click
        async tsbtnGhiLai_Click() {
            this.CapNhat();
        },
        //Click nút thoát
        tsbtnThoat_Click() {
            this.Close();
        },
        //Click nút xóa
        tsbtnXoa_Click() {
             this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa hợp đồng không ?', {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                 await api.XoaHDKhachhang(this.axios, {
                   "vhdkh_id": this.hdkh_id
                }).then((res) => {
                    this.$root.$toast.success("Xóa hợp đồng khách hàng thành công!");
                    this.HienThiDanhSach();
                    this.SetButton(0);
                });
            });
        },
        //Click nhập mới
        tsbtnNhapMoi_Click() {
            this.NhapMoi();
        },
        //Nhập mới
        NhapMoi() {
            this.SetButton(1);
        },
        //Click hủy bỏ
        tsbtnHuyBo_Click() {
            this.SetButton(0);
        },
        //Nhấn số sim cũ
        async txtSoSimCu_KeyPress() {
            if (this.txtSoSimCu.trim() != "")
                {
                    this.LayThongTinThuebaoTuSimPhu(this.txtSoSimCu.trim());
                }
        },
         //Lấy thông tin thuê bao từ sim phụ
        async LayThongTinThuebaoTuSimPhu(sosimphu)
        {
            try
            {
                await api.multisim_doisim_ttsimcu(this.axios, {
                    "so_sim": sosimphu.toString()
                }).then(async (res) => {
                    console.log(res.data.data)
                    this.Ttsimcu = res.data.data;
                    if ( this.Ttsimcu != null)
                    {
                        await api.multisim_tt_thuebao(this.axios, {
                                "so_tb": this.Ttsimcu.so_tb_phu.toString()
                        }).then(async (res) => {
                            this.dtTTSimChinh = res.data.data;
                            if (this.dtTTSimChinh != null)
                            {
                                await api.ts_tracuu_laytt_tb(this.axios, {
                                        "so_tb": this.dtTTSimChinh[0].so_tb.toString()
                                }).then(async (res) => {
                                    this.dtThongTinThuebao = res.data.data;
                                    this.txtMaTB =  this.dtTTSimChinh[0].so_tb;
                                    this.txtSoMayPhu = this.Ttsimcu.so_tb_phu;
                                    this.txtTenTB = this.dtThongTinThuebao[0].TEN_KH;
                                    this.txtDiaChiTB = this.dtThongTinThuebao[0].DIACHI_KH;
                                    if ( this.txtTenTB == ""||this.txtTenTB == null)
                                    {
                                         await api.ts_tracuu_laytt_tb_ts(this.axios, {
                                            "so_tb": "84856334585"//this.txtMaTB
                                        }).then((res) => {
                                            this.dtLichSuKH = res.data.data;
                                            this.txtTenTB = this.dtLichSuKH[0].TEN_TB;
                                            this.txtDiaChiTB = this.dtLichSuKH[0].DIACHI;
                                        });
                                    }
                                });
                                this.txtTinhTP = this.dtThongTinThuebao[0].MA_TINH;
                                if (this.dtThongTinThuebao[0].TRA_SAU == 1)
                                {
                                    this.loaitb_id = 20;
                                    this.txtLoaiTB = "Trả sau";
                                }
                                else
                                {
                                     this.loaitb_id = 20;
                                     this.txtLoaiTB = "Trả trước";
                                }
                                this.ckeGoiDen =  this.dtThongTinThuebao[0].GOI_DEN == "1";
                                this.ckeGoiDi =  this.dtThongTinThuebao[0].GOI_DI == "1";
                               
                            }
                        });
                    }
                });
            }
            catch (err)
            {
                console.log(err);
                this.$toast.error("Có lỗi lấy thông tin sim phụ "+err.message);
            }
        },
        //Kiểm tra số sim mới
        async KiemTraSoSimMoi(somay,soSim)
        {
            try
            {
                await api.KiemTraSoSimMoi(this.axios, {
                    "so_msin": somay,
                    "so_tb": soSim
                    }).then((res) => {
                        console.log(res)
                        this.chkeSim = res.data.data[0].esim == "1";
                        return res.data.data[0].sim_hop_le == "0";
                });

            }
            catch (e )
            {
                this.$toast.warning("Có lỗi kiểm tra sim mới " +e);
                return false;
            }
            return true;
        },
        // Kiểm tra dữ liệu
        KiemTraDL_KhachHang() {
            if (this.txtTenTB == "") {
                this.$toast.warning("Vui lòng nhập tên khách hàng!")
            }

            if (this.txtSoSimMoi.Text == "") {
                this.$toast.warning("Kiểm tra lại số Sim mới !");
            }
        },
        //Khi click dòng trong grid danh sách phiếu mới
        async gridDSPhieu_FocusedRowChanged(data) {
            let i = data.rowIndex;
            try
            {
                if ((this.options.DSHDmoi_DaThanhToan.length > 0) && (data.rowIndex != null))
                {
                    this.hdtb_id = this.options.DSHDmoi_DaThanhToan[i].hdtb_id;
                    this.HienThiTTHopDongKH(this.options.DSHDmoi_DaThanhToan, i);
                    this.HienThiTTHopDongTB(this.options.DSHDmoi_DaThanhToan, i);
                    //Kiểm tra nếu hợp đồng đã thanh toán thì mới cho cập nhật vào danh bạ
                    if (this.options.DSHDmoi_DaThanhToan[i].tthd_id == 2)
                    {
                        SetButton(4);
                    }
                }
            }
            catch(e)
            {
               console.log(e);
            }
        },
        async LayTienTheoKhoanMuc() {
            try {
                await api.LayTienKhoanMuc(this.axios, {
                    "loaihd_id": this.LoaiHopDong.THAY_DOI_SO_SIM,
                    "ngay_yc": this.txtNgayYC.toString(),
                    "dichvuvt_id": this.DichVuVienThong.DI_DONG.toString(),
                    "loaitb_id": this.LoaiHinhTB.DIDONGTRASAU.toString(),
                    "kieuld_id": 168,
                    "phuongld_id": 0,
                    "tocdo_id": 0,
                    "muccuoc_id": 0,
                    "ltb_adsl_id": 1,
                    "ltb_adsl_moi_id": 0,
                    "ltb_adsl_cu_id": 0,
                    "tocdo_moi_id": 0,
                    "tocdo_cu_id": 0,
                    "kh_cd": 0,
                    "phold_id": 0,
                    "apld_id": 0,
                    "khuld_id": 0,
                    "doituong_id": 1,
                    "trangbi_id": 4
                }).then((res) => {
                    if (res.data.data.length > 0) {
                        this.HienThiTienLapDat(res.data.data);
                    }
                });
            } catch (e) {
             console.log(e);
            }
        },
        HienThiTienLapDat(ds) {
            if (ds[0].toString() != "-1") {
                ds.forEach(item => {
                    if (item.khoanmuctt_id.toString() == this.KHOANMUC_TT.KMTT_CAIDAT.toString()) {
                        this.kt_cd = false;
                    } else if (item.khoanmuctt_id == this.KHOANMUC_TT.KMTT_DOISIM.toString()) {
                        this.txtTienLapMoi = this.formatPrice(ds[0]["tien"].toString());
                        this.txtVatLapmoi = this.formatPrice(ds[0]["vat"].toString());
                        this.kt_lm = false;
                    }
                })
                if (this.kt_cd == true) {}
                if (this.kt_lm == true) {
                    this.txtTienLapMoi = "0";
                    this.txtVatLapmoi = "0";
                }
            } else {
                this.txtTienLapMoi = "0";
                this.txtVatLapmoi = "0";
                this.$toast.warning("Thông tin giá chưa được cập nhật");
            }
        },
        SetButton(kieu) {
            this.tsbtnNhapMoi = false;
            this.tsbtnGhiLai = false;
            this.tsbtnXoa = false;
            this.tsbtnHuyBo = false;
            this.tsbtnThanhToan = false;
            if (kieu == -1) //Bat dau
            {
                this.$refs.MaTB.focus();
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
                this.LayTienTheoKhoanMuc();
            }
            if (kieu == 0) //Bat dau
            {
                this.$refs.MaTB.focus();
                this.tsbtnNhapMoi = true;
                this.Clear();
            }

            if (kieu == 1) //Them moi
            {
                this.$refs.MaTB.focus();
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
                this.Clear();
                this.LayTienTheoKhoanMuc();
            }

            if (kieu == 2) //Huy
            {
                this.$refs.MaGD.focus();
                this.tsbtnNhapMoi = true;
                this.tsbtnXoa = true;
                this.Clear();
            }
            if (kieu == 3) //Edit 
            {
                this.$refs.MaGD.focus();
                this.tsbtnNhapMoi = true;
                this.tsbtnXoa = true;
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
            }
            if (kieu == 4) //Edit ( chưa thanh toán )
            {
                this.$refs.MaGD.focus();
                this.tsbtnNhapMoi = true;
                this.tsbtnXoa = true;
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
            }
        },
        Clear() {
             this.hdkh_id = 0;
             this.txtMaGD = "";
             this.khachhang_id = 0;
             this.txtTenTB = "";
             this.txtDiaChiTB = "";
             this.dtpNgayLapHD = moment().format('DD/MM/YYYY');
             this.txtSoSimMoi = "";
             this.txtSoSimCu = "";
             this.txtGhiChu = "";
             this.txtTongTienHD = "";
             this.txtNoiCap = "";
             this.txtNgayCap = "";
             this.txtSoGT = "";
             this.hdtb_id = 0;
             this.thuebao_id = 0;
             this.dichvuvt_id = this.DichVuVienThong.DI_DONG;
        },
        btnThanhToan_OnClick(){
            this.$router.push({
                name: "Payment",
                params: { magd: this.txtMaGD}
            });
        },
    },
    watch: {}
};
</script>
