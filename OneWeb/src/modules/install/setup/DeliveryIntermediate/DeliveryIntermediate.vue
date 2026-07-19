<template src='./DeliveryIntermediate.html'></template>
<style scoped src='./DeliveryIntermediate.scss'></style>
<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue';
import DataGrid2 from '@/components/Controls/DataGrid2';
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
// import ModalSearchContract from './components/SearchContract/Modal.vue'
import moment from 'moment'
import {
  TrangThaiHD,
  DichVuVienThong,
  LoaiHopDong,
  KIEU_HD,
  TRANGTHAI_DONGBO,
  LoaiHinhTB,
  LOAI_DV,
} from "@/const/enums";

import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {
        VueElement,
        SearchContract,
        DataGridCustom,
        DataGrid2,
        // ModalSearchContract
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
                title: "Giao phiếu điểm chia trung gian cấu hình TSL",
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
            txtMaGD: "",
            hdkh_id: "",
            khachhang_id: "",
            tthd_id: "",
            txtDTLienLac: "",
            txtTenKH: "",
            txtDiachiKH: "",
            txtNgayYC: "",
            txtNguoiNhan: "",
            txtLoaiHD: "",
            txtNguoilap_HD: "",
            txtMa_TB: "",
            hdtb_id: "",
            loaiHdId:0,
            thuebao_id: "",
            tthd_id: "",
            txtGhiChu: "",
            huonggiao_id:"",
            cboDichVuVT: "",
            txtKieuLD: "",
            dichvuvt_id: "",
            diachi_ld_dau: "",
            diachi_ld_cuoi: "",
            tsbtnHuyGiao: true,
            tsbtnTimKiem: true,
            tsbtnGiaoPhieu: true,
            ctmsGiaoPhieu: true,
            ctmsHuyPhieu: true,
            phieu_id: "",
            isTimTheoQuyTrinh: false,
            isTimTheoDichVu: false,
            isTimTheoLoaiHD: false,
            success_code: "BSS-00000000",
            ngaygiao_tk: new Date(),
            ngay_yc: new Date(),
            model: {},
            DIEUHANH_THICONG: 1,
            HUONG_LAPMOI_CD: 202,
            HUONG_LAPMOI_FTTH: 206,
            HUONG_LAPMOI_MYTV: 207,
            txtNoidungGiao: "",
            listbox: {
                thamso: {
                    list: [],
                    value: {},
                },
                loaihd: {
                    list: [],
                    value: {},
                },
                dichvu: {
                    list: [],
                    value: {},
                },
                quytrinh: {
                    list: [],
                    value: {},
                },
                trangthaiphieu: {
                    list: [{
                        id: 1,
                        text: "Chưa giao"
                    }, {
                        id: 2,
                        text: "Đã giao"
                    }],
                    value: {},
                },
                donvinhan: {
                    list: [],
                    value: {},
                },
                huonggiao: {
                    list: [],
                    value: {},
                },
                ds_phieuyeucau: {
                    list: [],
                    header: [{
                            fieldName: 'ma_tb',
                            headerText: 'Số máy/Acc',
                            allowFiltering: true,
                            width: 150
                        },
                        {
                            fieldName: 'ten_tb',
                            headerText: 'Tên thuê bao',
                            allowFiltering: true,
                            width: 200
                        },
                        {
                            fieldName: 'diachi_tb',
                            headerText: 'Địa chỉ lắp đặt',
                            allowFiltering: true,
                            allowHtml: true,
                            width: 300
                        },
                        {
                            fieldName: 'ttyc',
                            headerText: 'Thông tin yêu cầu',
                            allowFiltering: true,
                            allowHtml: true,
                            width: 300
                        },
                        {
                            fieldName: 'sophieu',
                            headerText: 'Số QĐ',
                            allowFiltering: true,
                            allowHtml: true,
                            width: 150
                        },
                        {
                            fieldName: 'donvinhan',
                            headerText: 'Đơn vị nhận',
                            allowFiltering: true,
                            allowHtml: true
                        },
                        {
                            fieldName: '',
                            headerText: 'Nội dung trả',
                            allowFiltering: true,
                            allowHtml: true
                        },
                    ],
                    value: {},
                    isEnabled: true,
                    checked: []
                },
            },
            dataSelected: {
                dichvuvt_id: 0,
                tocdo_id: 0,
                donvi_dau_id: 0,
                donvi_cuoi_id: 0,
                donvi_giaodiem_id: 0,
                huonggiao_id: 0,
            },
            nhanvien_id: this.$root.token.getNhanVienID(),
            txtMaGD: "",
            isLoad: true,
            TRANGTHAI_PH:{MOI:1},
            TrangThaiHD:
            {
            HOAN_THANH: 6,
            THOAITRA: 7,
            THOAITRA_TMP: 17
            },
            DichVuVienThong: {
                TSL:9,
            },
             modalSearch: {},
            options: {
                lstDichvu: [],
                lstTocdokenh: [],
                lstTocTCDau: [],
                lstTocTCCuoi: [],
                lstGiaoDiemChia: [],
                lstDsHDMaThuebao: [],
                DsHopDongTB: [],
                lstDsHuonggiao: [],
                DsTientrinhXuly: [],
                PhieuDuocChon: [],
            },
        };
    },
    mounted() {
        this.init();
        this.ngay_yc = null;
        this.ngaygiao_tk = null;
        this.LoadHuongGiao();
    },
    computed: {

    },
    validations: {
        MaTB: {
            required,
            minLength: minLength(1)
        },
        DK_tungay: {
            required,
            minLength: minLength(10)
        },
        DK_denngay: {
            required,
            minLength: minLength(10)
        },
        listbox: {
            goidadichvu: {
                idx: {
                    required,
                    minLength: minLength(1)
                }
            }
        }
    },
    methods: {
        ShowAlert(mesage, val) {
            if (val == 0) {
                this.$toast.success(mesage);
            } else if (val == 1) {
                this.$toast.warning(mesage);
            } else if (val == 2) {
                this.$toast.error(mesage);
            }
        },
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        },
        async init() {
            this.listbox.trangthaiphieu.value.id = 1;
            try {
                await this.getDsDichvu();
                await this.getDsTocdokenh();
                await this.getDsToThiCongDau();
                await this.getDsToThiCongCuoi();
                await this.getDsToThiCongCuoi();
                await this.getDsGiaoDiemChia();
                await this.getLoaiHD();
                await this.LoadHuongGiao();
            } catch (e) {
                console.log(e);
                this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
            }
        },
        //Get danh sách dịch vụ theo người dùng
       async getDsDichvu() {
            this.loading(true);
            try {
                await  api.getDSDichVu(this.axios).then((res) => {
                    this.options.lstDichvu = res.data.data ? res.data.data : [];
                    if (this.options.lstDichvu) {
                        this.dataSelected.dichvuvt_id = this.options.lstDichvu[2]["DICHVUVT_ID"];
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        async getLoaiHD() {
            this.loading(true);
            try {
                var input = {};
                api.CSS_LOAI_HD(this.axios, input).then((response) => {
                    if (response.data.error_code && response.data.error_code === this.success_code) {
                        this.listbox.loaihd.list = response.data.data.filter(x => x.LOAIHD_ID > 0).map(x => ({
                            id: x.LOAIHD_ID,
                            text: x.TEN_LOAIHD
                        }));
                        this.listbox.loaihd.value.LOAIHD_ID = this.listbox.loaihd.list[0]["id"];
                    } else {
                        this.listbox.loaihd.list = [];
                        this.ShowAlert("Không tìm thấy danh sách loại hợp đồng!", 1);
                    }
                });
            } catch (e) {
                this.ShowAlert("Lỗi lấy danh sách loại hợp đồng", 2);
                this.loading(false);
            }
            this.loading(false);
        },

        //Load hướng giao
        async LoadHuongGiao() {
            try {
                await api.sp_lay_ds_huonggiao(this.axios, {
                    "nguoidung_id": this.$root.token.getNguoiDungID(),
                    "nhom_qt_id": 1,
                    "quytrinh_id": 0,
                    "loaihd_id": 0,
                    "dichvuvt_id": 0
                }).then((res) => {
                    this.options.lstDsHuonggiao = res.data.data ? res.data.data : [];
                    if (this.options.lstDsHuonggiao) {
                        this.dataSelected.huonggiao_id = 5555;
                    }

                });

            } catch (e) {
                this.ShowAlert("Lỗi lấy danh sách hướng giao", 2);
                this.loading(false);
            }

            //this.HienThi_DanhSach();
        },

        async getDsTocdokenh() {
            this.loading(true);
            try {
                await api.getDsTocdokenh(this.axios, {}).then((res) => {
                    this.options.lstTocdokenh = res.data.data ? res.data.data : [];
                    if (this.options.lstTocdokenh) {
                        this.dataSelected.tocdo_id = this.options.lstTocdokenh[0]["TOCDO_ID"];
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        async getDsToThiCongDau() {
            this.loading(true);
            try {
                await api.getDsToThiCongDau(this.axios, {
                    "phanvung_id": 26,
                    "loaidv_id": 5
                }).then((res) => {
                    this.options.lstTocTCDau = res.data.data ? res.data.data : [];
                    if (this.options.lstTocTCDau) {
                        this.dataSelected.donvi_dau_id = this.options.lstTocTCDau[0]["donvi_id"];
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        async getDsToThiCongCuoi() {
            this.loading(true);
            try {
                await api.getDsToThiCongCuoi(this.axios, {
                    "phanvung_id": 26,
                    "loaidv_id": 5
                }).then((res) => {
                    this.options.lstTocTCCuoi = res.data.data ? res.data.data : [];
                    if (this.options.lstTocTCCuoi) {
                        this.dataSelected.donvi_cuoi_id = this.options.lstTocTCCuoi[0]["donvi_id"];
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        async getDsGiaoDiemChia() {
            this.loading(true);
            try {
                await api.getDsGiaoDiemChia(this.axios, {
                    "phanvung_id": 26,
                    "loaidv_id": 5
                }).then((res) => {
                    this.options.lstGiaoDiemChia = res.data.data ? res.data.data : [];
                    if (this.options.lstGiaoDiemChia) {
                        this.dataSelected.donvi_giaodiem_id = this.options.lstGiaoDiemChia[0]["donvi_id"];
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        showPopupSearchContract() {
        this.modalSearch = {
            loai_hd_id : LoaiHopDong.DAT_MOI,
            trangthai_hd : TrangThaiHD.MOI
        }
        this.$refs.popupFrmTraCuuHopDong.show();
        },
        acceptSearchContract(item) {
            console.log(item.hopdong)
        this.txtMaGD = item.hopdong.ma_gd;
        this.$refs.popupFrmTraCuuHopDong.hide();
        this.onMaGDEnter()
        },
        async Timkiem() {
            this.loading(true);
            try {
                await api.getDsHDTheoMaTB(this.axios, {
                    "ma_tb": this.txtMaGD
                }).then((res) => {
                    this.options.lstDsHDMaThuebao = res.data.data ? res.data.data : [];
                    if (this.options.lstDsHDMaThuebao.length > 0) {
                        this.txtMaGD = this.options.lstDsHDMaThuebao[0].ma_gd;
                        this.hdkh_id = this.options.lstDsHDMaThuebao[0].hdkh_id;
                        this.khachhang_id = this.options.lstDsHDMaThuebao[0].khachhang_id;
                        this.tthd_id = this.options.lstDsHDMaThuebao[0].tthd_id;
                        this.txtDTLienLac = this.options.lstDsHDMaThuebao[0].so_dt;
                        this.txtNguoilap_HD = this.options.lstDsHDMaThuebao[0].ten_nv;
                        this.txtTenKH = this.options.lstDsHDMaThuebao[0].ten_kh;
                        this.txtDiachiKH = this.options.lstDsHDMaThuebao[0].diachi_kh;
                        this.txtNgayYC = moment(this.options.lstDsHDMaThuebao[0].ngay_yc).format('DD/MM/YYYY');
                        this.txtNguoiNhan = this.options.lstDsHDMaThuebao[0].ten_nv;
                        this.txtLoaiHD = this.options.lstDsHDMaThuebao[0].ten_loaihd;
                    }
                    this.get_ds_hopdongthuebao(this.hdkh_id);
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        async onMaGDEnter() {
            this.hdkh_id= 0;
            this.tthd_id = 0;
            this.txtDTLienLac = "";
            this.txtTenKH = "";
            this.txtDiachiKH = "";
            this.txtNgayYC = moment().format('DD/MM/YYYY');
            this.txtNguoiNhan = "";
            this.txtLoaiHD = "";
            this.options.DsTientrinhXuly=[]
            this.loading(true);
            try {
                await api.getDsHDTheoMaTB(this.axios, {
                    "ma_tb": this.txtMaGD
                }).then((res) => {
                   
                    this.options.lstDsHDMaThuebao = res.data.data ? res.data.data : [];
                     
                    if (this.options.lstDsHDMaThuebao.length > 0) {
                        this.txtMaGD = this.options.lstDsHDMaThuebao[0].ma_gd;
                        this.hdkh_id = this.options.lstDsHDMaThuebao[0].hdkh_id;
                        this.khachhang_id = this.options.lstDsHDMaThuebao[0].khachhang_id;
                        this.tthd_id = this.options.lstDsHDMaThuebao[0].tthd_id;
                        this.txtDTLienLac = this.options.lstDsHDMaThuebao[0].so_dt;
                        this.txtTenKH = this.options.lstDsHDMaThuebao[0].ten_kh;
                        this.txtDiachiKH = this.options.lstDsHDMaThuebao[0].diachi_kh;
                        this.txtNgayYC = moment(this.options.lstDsHDMaThuebao[0].ngay_yc).format('DD/MM/YYYY');
                        this.txtNguoiNhan = this.options.lstDsHDMaThuebao[0].ten_nv;
                        this.txtLoaiHD = this.options.lstDsHDMaThuebao[0].ten_loaihd;
                        this.get_ds_hopdongthuebao(this.hdkh_id);
                        this.refreshTB();
                    }else{
                        this.$toast.warning("Không tìm thấy hợp đồng.");
                    }
                    
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        async get_ds_hopdongthuebao(hdkh_id) {
            try {
                var input = {
                    "hdkhId": hdkh_id
                };
                await api.get_ds_hopdongthuebao(this.axios, input)
                    .then((response) => {
                        if (response.data.data.length > 0) {
                            this.options.DsHopDongTB = response.data.data;
                            this.txtMa_TB =  this.options.DsHopDongTB[0].ma_tb;
                            this.hdtb_id = this.options.DsHopDongTB[0].hdtb_id;
                            this.thuebao_id = this.options.DsHopDongTB[0].thuebao_id;
                            this.tthd_id = this.options.DsHopDongTB[0].tthd_id;
                            this.phieu_id = this.options.DsHopDongTB[0].phieu_id;
                            this.ttph_id = this.options.DsHopDongTB[0].ttph_id;
                            this.txtGhiChu = this.options.DsHopDongTB[0].ghichu;
                            this.huonggiao_id = this.options.DsHopDongTB[0].huonggiao_id;
                            this.dsdichvukenh = this.options.DsHopDongTB[0].ten_dvvt;
                            this.txtKieuLD = this.options.DsHopDongTB[0].ten_kieuld;
                            this.dataSelected.dichvuvt_id = this.options.DsHopDongTB[0].dichvuvt_id;
                            this.diachi_ld_dau = this.options.DsHopDongTB[0].diachi_ld;
                            this.diachi_ld_cuoi = this.options.DsHopDongTB[0].diachi_tb;
                            this.dataSelected.donvi_dau_id = this.options.DsHopDongTB[0].donvi_dau_id;
                            this.dataSelected.donvi_cuoi_id = this.options.DsHopDongTB[0].donvi_cuoi_id;
                            
                        }else {
                                this.options.DsHopDongTB = []
                            }
                    })
            } catch (err) {
                console.log(err)
                this.$toast.error("Không tìm thấy hợp đồng");
                this.loading(false);
            }
        },
        onDataBounddsthuebao() {
            this.$refs['dsthuebao'].selectRow(0)
        },
        async get_ds_tientrinhxuly(hdtbId) {
             this.options.DsTientrinhXuly = []
            try {
                await api.get_ds_tientrinhxuly(this.axios, {
                        "hdtb_id": hdtbId
                    })
                    .then((response) => {
                        this.options.DsTientrinhXuly = response.data.data
                    })
            } catch (err) {
                this.$toast.error("Không tìm thấy phiếu");
                this.loading(false);
            }
        },
        refreshTB() {
            this.txtMa_TB = "";
            this.hdtb_id = "";
            this.thuebao_id = "";
            this.tthd_id = "";
            this.txtGhiChu = "";
            this.dsdichvukenh = "";
            this.txtKieuLD = "";
            this.dataSelected.dichvuvt_id = this.options.lstDichvu[0]["dichvuvt_id"];
            this.diachi_ld_dau = "";
            this.diachi_ld_cuoi = "";
            this.dataSelected.donvi_dau_id = this.options.lstTocTCDau[0]["donvi_id"];
            this.dataSelected.donvi_cuoi_id = this.options.lstTocTCCuoi[0]["donvi_id"];
        },
        async fnGiaophieu() {
            try {
                var dagiao =this.options.DsTientrinhXuly.filter(item => item.huonggiao_id.toString() == "5555");
          
                if (dagiao.length > 0){
                    this.$toast.warning("Đã tồn tại phiếu giao điểm chia trung gian TSL");
                }
                else 
                {
                     if (this.options.PhieuDuocChon.length <= 0)
                    {
                        this.$toast.warning("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
                    }
                    else {
                        if (this.tthd_id == this.TrangThaiHD.HOAN_THANH || this.tthd_id == this.TrangThaiHD.THOAITRA || this.tthd_id == this.TrangThaiHD.THOAITRA_TMP)
                        {
                            this.$toast.warning("Trạng thái hợp đồng không thỏa mãn điều kiện giao !");
                        }
                        else if (this.dataSelected.dichvuvt_id != this.DichVuVienThong.TSL)
                        {
                            this.$toast.warning("Không được thực hiện với dịch vụ khác TSL !");
                        }
                        else {
                            if(this.txtNoidungGiao == null){
                                 this.$toast.warning("Vui lòng nhập nội dung giao");
                            }else{
                                await api.giaophieu_diemchia_trunggian_tsl(this.axios, {
                                "vhdtb_id":  this.hdtb_id,
                                "vnhanvien_id": this.nhanvien_id,
                                "vnoidung_giao": this.txtNoidungGiao,
                                "vdonvi_nhan_id": this.dataSelected.donvi_giaodiem_id,
                                "vmay_cn": this.$root.token.getMaNhanVien(),
                                "vnguoi_cn":this.$root.token.getUserName(),
                                "vip_cn": this.$root.token.getUserName()
                                }).then((res) => {
                                    console.log(res)
                                    this.loading(false)
                                    this.$root.$toast.success('Giao phiếu thành công !')
                                    this.get_ds_hopdongthuebao(this.hdkh_id);
                                    this.gridDSTB_FocusedRowChanged();
                                    this.get_ds_tientrinhxuly(this.hdtb_id);
                                });
                            }
                            
                        }
                    }
                }
               
            } catch (err) {
                console.log(err)
                this.$toast.error("Lỗi không thể thực hiện: " + err);
            }
        },
         async fnHuyphieu() {
            try {
                 if (this.options.PhieuDuocChon.length <= 0)
                {
                    this.$toast.warning("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
                }
                else
                {
                    if (this.ttph_id != this.TRANGTHAI_PH.MOI)
                    {
                        this.$toast.warning("Trạng thái phiếu khác Mới. Bạn không thể hủy !");
                    }
                    else if (this.dataSelected.dichvuvt_id != this.DichVuVienThong.TSL)
                    {
                        this.$toast.warning("Không được thực hiện với dịch vụ khác TSL !");
                    }
                    else if (this.huonggiao_id != 5555)
                    {
                        this.$toast.warning("Chỉ được hủy giao với hướng giao 'Giao điểm chia trung gian cấu hình' !");
                    } else {
                        await api.huyphieu_diemchia_trunggian_tsl(this.axios, {
                        "vphieu_id": this.phieu_id,
                        "vnhanvien_id": this.nhanvien_id,
                        "vmay_cn": this.$root.token.getMaNhanVien(),
                        "vnguoi_cn":this.$root.token.getUserName(),
                        "vip_cn": this.$root.token.getUserName()
                        }).then((res) => {
                            this.loading(false)
                            this.$root.$toast.success('Hủy phiếu thành công !')
                            this.get_ds_hopdongthuebao(this.hdkh_id);
                            this.gridDSTB_FocusedRowChanged();
                            this.get_ds_tientrinhxuly(this.hdtb_id);
                        });
                    }
                }
                
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err);
            }
        },
        setValueFromRowSelect(row) {
            console.log(row)
            this.txtMa_TB = row.ma_tb;
            this.hdtb_id = row.hdtb_id;
            this.thuebao_id = row.thuebao_id;
            this.tthd_id = row.tthd_id;
            this.txtGhiChu = row.ghichu;
            this.huonggiao_id = row.huonggiao_id;
            this.dsdichvukenh = row.ten_dvvt;
            this.txtKieuLD = row.ten_kieuld;
            this.dataSelected.dichvuvt_id = row.dichvuvt_id;
            this.diachi_ld_dau = row.diachi_ld;
            this.diachi_ld_cuoi = row.diachi_tb;
            this.dataSelected.donvi_dau_id = row.donvi_dau_id;
            this.dataSelected.donvi_cuoi_id = row.donvi_cuoi_id;
            this.options.PhieuDuocChon.push(row); 
            this.get_ds_tientrinhxuly(this.hdtb_id);
        },
        async gridDSTB_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data;
                    this.setValueFromRowSelect(row);
                }
            } catch (err) {
                console.log(err);
            }
        },
        setValueFromRowSelect_Phieu(row) {
            this.phieu_id = row.phieu_id;
            this.ttph_id = row.ttph_id;
            this.huonggiao_id = row.huonggiao_id;
            this.txtNoidungGiao = row.nd_giao;
            this.dataSelected.donvi_giaodiem_id = row.dvi_nhan_id
        },
        async gridDSPhieu_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data;
                    this.setValueFromRowSelect_Phieu(row);
                }
            } catch (err) {
                console.log(err);
            }
        },
    },
    watch: {}
};
</script>