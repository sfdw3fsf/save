 
<template src="./ConvertFixedToCrossSellingIMS.html"></template> 
<style scoped src="./ConvertFixedToCrossSellingIMS.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import '@/assets/vendor/jquery/split'
import api from './ConvertFixedToCrossSellingIMSAPI'
// import ModalChonDiaChi from '../../../contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import ModalDiaChiBC from './Popups'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ThongTinLienHe from "../../../contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import { required, minValue, maxValue} from 'vuelidate/lib/validators'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import DataGrid2 from '@/components/Controls/DataGrid2'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import { DichVuVienThong, TRANGBI, DUNG_THU, LoaiHopDong, TrangThaiHD, LoaiHinhTB, ThoiHan, KHOANMUC_TT, KieuCaiDatDVGT, THUESO, TRANGTHAI_DONGBO, KieuLapDat, LOAI_DV, Loai_KH, HINHTHUC_TC, KIEU_HD, KIEU_KH, PHANLOAI_KH, Loai_NV, LOAISO, LOAI_KV,
} from "../../../contract/setup/InstallNewSubs/Enums";
import {Reset_Thongso_Thuebao} from './Utils';
import ThongSoCoDinhModal from '../../../contract/setup/ChangeSubsTypeCrossSale/popups/ThongSoCoDinhModal.vue';
import { tracuuThueBaoChuaNhapCap } from '../../../CABMAN/store/TraCuuThueBaoChuaNhapCap'
//import EventBus from '@/utils/eventBus'
export default {
    components: {
        breadcrumb,
        gridview,
        SearchContractModal,
        DatePicker,
        // ModalChonDiaChi,
        ModalDiaChiBC,
        BssErrorMarker,
        BssRequiredMarker,
        ThongTinLienHe,
        ThongSoCoDinhModal
    },
    name: 'ConvertFixedToCrossSellingIMS',
    validations: {
        diachiKH: {
            DIACHI: {
                required
            },
        },
        diachiHK: {
            DIACHI: {
                required
            },
        },
        diachiNDD: {
            DIACHI: {
                required
            },
        },
        diachiTT: {
            DIACHI: {
                required
            },
        },
        diachiCT: {
            DIACHI: {
                required
            },
        },
        diachiBC: {
            DIACHI: {
                required
            },
        },
        diachiTB: {
            DIACHI: {
                required
            },
        },
        diachiLD: {
            DIACHI: {
                required
            },
        },
        diachiKN: {
            DIACHI: {
                required
            },
        },
        diachiDau: {
            DIACHI: {
                required
            },
        },
        diachiCuoi: {
            DIACHI: {
                required
            },
        },
    },
    mounted() {
        this.buttonSet = 1;
        Split(['#boxLeft', '#boxRight'], {
            sizes: [50, 50],
            gutterSize: 16,
            onDragEnd: function (sizes) {},
        });
        this.state.txt_KhuVucEnabled = true;
        this.Loading = true;
        var ds_dichvu_vt = api.get_dichvu_vt(this.axios, {params: {p_dichvu_vt_id: DichVuVienThong.CO_DINH + "," + DichVuVienThong.IMS}}).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.dichvu_vt.list = response.data.data;
                    this.listbox.dichvu_vt_moi.list = response.data.data;
                }
            }
        );
        var ds_loaihinh_tb = api.get_loaihinh_tb(this.axios, {"loai": 0, "dichvuvt_id": DichVuVienThong.CO_DINH}).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.loaihinh_tb.list = response.data.data;
                    this.listbox.loaihinh_tb.value = this.listbox.loaihinh_tb.list[0].loaitb_id * 1;
                }
            }
        );
        var ds_loaihinh_tb_ims = api.get_loaihinh_tb(this.axios, {"loai": 0, "dichvuvt_id": DichVuVienThong.IMS}).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.loaihinh_tb_moi.list = response.data.data;
                    this.listbox.loaihinh_tb_moi.value = this.listbox.loaihinh_tb_moi.list[0].loaitb_id * 1;
                }
            }
        );
        var ds_trangbi = api.get_trangbi(this.axios).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                    this.listbox.trangbi.list = response.data.data;
            }
        );
        var ds_chitietdv_ims = api.get_chitiet_dv_ims(this.axios, {params: {p_kieu: 0}}).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.ht_chitietdv_ims.list = response.data.data;
                    this.listbox.ht_chitietdv_ims.value = this.listbox.ht_chitietdv_ims.list[0].ctdv_id * 1;
                }
            }
        );
        var ds_thietbi_ims = api.get_thietbi_ims(this.axios).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.thietbi_ims.list = response.data.data;
                    this.listbox.thietbi_ims.value = this.listbox.thietbi_ims.list[0].thietbi_id * 1;
                }
            }
        );
        var ds_hinhthuc_tc = api.get_hinhthuc_tc(this.axios, {
            "p_param": 2,
            "p_param1":  3,
            "p_type": 1 
        }).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.hinhthuc_tc.list = response.data.data;
                    this.listbox.hinhthuc_tc.value = this.listbox.hinhthuc_tc.list[0].httc_id;
                }
            }
        );
        var ds_doituong = api.get_doituong(this.axios, {}).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.doituong.list = response.data.data;
                    this.listbox.doituong.value = this.listbox.doituong.list[0].doituong_id;
                }
            }
        );
        var ds_donvi_ql = api.get_donvi_ql(this.axios, {
            'loaidv_id': LOAI_DV.DONVIQL_LD
        }).then(
            (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                {
                    this.listbox.donvi_ql.list = response.data.data;
                    if (this.listbox.donvi_ql.list && this.listbox.donvi_ql.list.length > 0) 
                        this.listbox.donvi_ql.value = this.listbox.donvi_ql.list[0].donvi_id;
                }
            }
        );
        Promise.all([ds_dichvu_vt, ds_loaihinh_tb, ds_loaihinh_tb, ds_loaihinh_tb_ims, ds_trangbi, ds_chitietdv_ims, 
                    ds_thietbi_ims, ds_hinhthuc_tc, ds_doituong, ds_donvi_ql]).then(() => { 
            this.kt_load = false;
            this.LoaiHinhTB_moi_Changed();
            this.LayTienTheoKhoanMuc();
            
        }).catch(() => {}).then(() => {
        }).finally(() => {
            this.loading(false);
        });
        // Lấy các tham số mặc định
        var ds_thamso_md = api.get_ds_thamso_md(this.axios,{params:{'p_kieu_id':0}}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.thamsomacdinh = response.data.data.reduce(function(result, item, index, array) {
            result[item['ma_ts']] = item['ten_ts']; //a, b, c
            return result;
          }, {})//.map(x => ({ [x['ma_ts']]:x['ten_ts'] })); 
          if(typeof this.thamsomacdinh['TUSINH_PASS_IMS'] !== 'undefined')
            this.state.tusinh_pass_ims = parseInt(this.thamsomacdinh['TUSINH_PASS_IMS']);
          else
            this.state.tusinh_pass_ims = 0;
          if(typeof this.thamsomacdinh['KHUVUC_THEO_KETCUOI'] !== 'undefined')
            this.state.KHUVUC_THEO_KETCUOI = parseInt(this.thamsomacdinh['KHUVUC_THEO_KETCUOI']);
          else
            this.state.KHUVUC_THEO_KETCUOI = 0;
          if(typeof this.thamsomacdinh['MACDINH_MATKHAU_IMS'] !== 'undefined')
            this.state.matkhau_md_ims = parseInt(this.thamsomacdinh['MACDINH_MATKHAU_IMS']);
          else
            this.state.matkhau_md_ims = "";
          if(typeof this.thamsomacdinh['KIEMTRA_IMS_CD_CDLH'] !== 'undefined' && this.thamsomacdinh['KIEMTRA_IMS_CD_CDLH'] == 1)
            this.state.kt_ims = true;
          else
            this.state.kt_ims = false;
          if(typeof this.thamsomacdinh['KIEMTRA_KHOSO_CD'] !== 'undefined' && this.thamsomacdinh['KIEMTRA_KHOSO_CD'] == 1)
            this.state.kt_khoso_cd = true;
          else
            this.state.kt_khoso_cd = false;
          if(typeof this.thamsomacdinh['KHONG_HIENTHI_TRACUU_DB_CDLH'] !== 'undefined')
            this.state.khong_ht_tracuudb = parseInt(this.thamsomacdinh['KHONG_HIENTHI_TRACUU_DB_CDLH']);
          else
            this.state.khong_ht_tracuudb = 0;
          if(typeof this.thamsomacdinh['SINH_MA_GD_THEO_DV'] !== 'undefined')
            this.state.ts_sinhma_gd_theo_donvi = true;
          else
            this.state.ts_sinhma_gd_theo_donvi = false;
          if(typeof this.thamsomacdinh['IP_SIPTRUNKING'] !== 'undefined')
            this.state.phainhap_ip_siptrunking = true;
          else
            this.state.phainhap_ip_siptrunking = false;
          if(typeof this.thamsomacdinh['THU_LAY_KHUVUC_THEO_DIADANH'] !== 'undefined')
            this.state.__THU_LAY_KHUVUC_THEO_DIADANH = true;
          else
            this.state.__THU_LAY_KHUVUC_THEO_DIADANH = false;
          if(typeof this.thamsomacdinh['KHONGCHO_CHON_KHUVUC'] !== 'undefined')
            this.state.txt_KhuVucEnabled = false;
          else
            this.state.txt_KhuVucEnabled = true;
        }
      });
        if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
            this.Tag = this.$route.query.tag ;
        } 
        if (this.Tag != null && this.Tag.toString() != "")
        {
            var str = this.Tag.toString();
            var words = str.split('+');
            if (words.length >= 1)
                this.iKenhTN = words[0]*1;
            else
                this.iKenhTN = 1;
        }
        else
        {
            this.iKenhTN = 1;
        }
    },
    computed: {
        kieu_ld_kieu() {
            try {
                return this.listbox.kieu_ld.list.filter(x => x.kieuld_id == this.listbox.kieu_ld.value)[0]['kieu'] * 1;
            } catch (ex) {
                return 0;
            }
        },
    },
    watch: {
        'listbox.trunk.checkbox_value': function () {
            this.listbox.hinhthuc_tc.enabled = (this.listbox.trunk.checkbox_value == false);
        },
        'listbox.donvi_ql.value': function () {
            if(!this.listbox.donvi_ql.value) return;
            this.listbox.tram_tc.list = [];
            this.listbox.tram_tc.value = 0;
            api.get_tram_tc(this.axios, {
                "p_loai_dv": LOAI_DV.TRAM_VT,
                "p_donviql_id": this.listbox.donvi_ql.value
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.listbox.tram_tc.list = response.data.data;
                    if (this.listbox.tram_tc && this.listbox.tram_tc.list.length > 0) this.listbox.tram_tc.value = this.listbox.tram_tc.list[0].donvi_id;
                }
            });
        },
        'listbox.dichvu_vt_moi.value': function () {
            this.cboDichVuVTMoi_SelectedValueChanged();
        },
        'listbox.trunk.value': function () {
            var tmp = this.listbox.trunk.list.filter(x => x.trunkid == this.listbox.trunk.value)
            var httc_id = tmp && tmp.length > 0 ? tmp[0]["loai"].toString() : '';
            if (!httc_id)
                this.$toast.warning("Thuê bao đại diện đường trunk chưa có thông tin về hình thức tính cước !");
            else
                this.listbox.hinhthuc_tc.value = httc_id * 1;
        },
        'diachiLD.DIACHI': function(){
            this.$refs.txtDiaChiLD.focus();
            this.LayTienTheoKhoanMuc();
            this.HT_NV_KinhDoanh_Theo_DiaBan();
        },
        'listbox.tram_tc.value': function() {
            if (!this.state.__THU_LAY_KHUVUC_THEO_DIADANH)
                return;
            try{
                if(this.diachiLD.DIACHI.trim() == "")
                    return;
                var pdata = {
                    "p_loaikv_id": LOAI_KV.KHUVUC_DIABAN,
                    "p_quan_id": this.diachiLD.QUAN_ID == null ? 0 : this.diachiLD.QUAN_ID,
                    "p_phuong_id": this.diachiLD.PHUONG_ID == null ? 0 : this.diachiLD.PHUONG_ID,
                    "p_pho_id": this.diachiLD.PHO_ID == null ? 0 : this.diachiLD.PHO_ID == null,
                    "p_ap_id": this.diachiLD.AP_ID == null ? 0 : this.diachiLD.AP_ID,
                    "p_khu_id": this.diachiLD.KHU_ID == null ? 0 : this.diachiLD.KHU_ID,
                    "p_dacdiem_id": this.diachiLD.DACDIEM_ID == null ? 0 : this.diachiLD.DACDIEM_ID,
                    "p_tramtc_id": this.listbox.tram_tc.value
                };
                api.get_ds_khuvuc_theo_diadanh(this.axios, pdata).then(
                    (response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                        {
                            var dulieu = JSON.parse(response.data.data);
                            if(dulieu['RESULT'] == 0)
                            {
                                this.thuebao.khuvuc = dulieu['MA_KV'];
                                this.thuebao.khuvuc_id = dulieu['KHUVUC_ID'];
                                this.$toast.success("Khu vực tự động chọn chính xác theo trạm thi công");
                            }
                            if(dulieu['RESULT'] > 0)
                            {
                                var tram = this.listbox.tram_tc.list.filter(x=>x.donvi_id == dulieu['TRAMTC_ID']);
                                this.thuebao.khuvuc = dulieu['MA_KV'];
                                this.thuebao.khuvuc_id = dulieu['KHUVUC_ID'];
                                if(dulieu['RESULT'] == 2)
                                    this.$toast.success("Khu vực tự động chọn, trạm thi công không được xét");
                                if(dulieu['RESULT'] == 1)
                                    this.$toast.success("Khu vực tự động chọn với trạm thi công " + tram[0].ten_dv);
                                if(dulieu['RESULT'] == 3)
                                    this.$toast.success("Khu vực được chọn lại về " + this.listbox.tram_tc.value);
                            }
                            if(dulieu['RESULT'] < 0)
                            {
                                this.thuebao.khuvuc = "";
                                this.thuebao.khuvuc_id = -1;
                                this.$toast.success("Không tồn tại khu vực nào đối với địa chỉ lắp đặt trên");
                            }
                        }
                    }
                );
            }
            catch(ex){
                this.$toast.error('' + ex);
            }
        },
        'buttonSet': function (oldval, newval) {
            switch (newval) {
                case -1:
                    this.filterbox.ctv.ischecked = false;
                    this.filterbox.nguoigt.ischecked = false;
                    //TODO clear Mã đại diện
                    break;
                case 0:
                    //TODO clear form - Clear function 18265
                    break;
                case 1:
                    this.filterbox.ctv.ischecked = false;
                    this.filterbox.nguoigt.ischecked = false;
                    //TODO clear Mã đại diện
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        },
    },
    data() {
        return {
            header: {
                title: 'Chuyển đổi từ cố định sang IMS bán chéo',
                list: [
                    //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
                    {
                        name: 'Chuyển đổi từ cố định sang IMS bán chéo',
                        link: {
                            name: 'Ui.cards'
                        }
                    },
                    //{ name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
                ]
            },
            may_cn: "localhost", //this.$root.token.getMachine(),
            ip_cn: this.$root.token.getIP(),
            nguoi_cn: this.$root.token.getNguoiDungID(),
            tinh: {
                tinhthicong_id: 0,
                thuebao_tc_id: 0,
            },
            kt_load: true,
            buttonSet: null,
            buttons: {
                tsbtnXoaTB: false,
                tsbtnThemTB: false,
                tsbtnThemTT: false,
                tsbtnXoaTT: false,
                btnChonSM: true,
                chkLapDatBS: false,
                btnThongTinKhac: false,
            },
            khachhang: {
                makh: '',
                sogt: '',
                magd: '',
                mahd: '',
                khachhang_id: 0,
                khachhang_tc_id: 0,
                tenkh: '',
                nguoidd: '',
                dienthoai: '',
                fax: '',
                chucvu: '',
                noicaphk: '',
                noicap: '',
                gioitinh: -1,
                masothue: '',
                ghichu: '',
                dactrung: '',
                PHANLOAIKH_ID: -1,
                hdkh_id: 0,
                plkh: '',
                nhom_plkh: 0,
            },
            thanhtoan: {
                matt: '',
                tentt: '',
                thanhtoan_id: 0,
                masothue: '',
                sodienthoai: '',
                email: '',
                chutk: '',
                tuyenthu_id: 0,
                matuyen: '',
                manvtc: '',
                tuyenthu_httt: 0,
                nhanvientc_id: 0,
                ghichu: '',
                hdmig_id: 0,
                mig_id: 0,
                mainghep: '',
                matb_dd: 0,
                manvql: '',
            },
            maduan: '',
            filterbox: {
                ctv: {
                    value: '',
                    ischecked: false,
                    ma_ctv: '',
                },
                nguoigt: {
                    value: '',
                    ischecked: false,
                    nguoigt_id: '',
                },
                khuyenmai: {
                    list: [],
                    value: '',
                    ischecked: false,
                },
                tratruoc: {
                    list: [],
                    value: '',
                    ischecked: false,
                },
            },
            tongtienhd: 0,
            popupComponent: null,
            popupComponentData: null,
            popupComponentName: '',
            popupComponentEvents: {
                'form-close': 'popupClosed',
                'acceptChangeCTV': 'popupClosed',
                'acceptChangeNGT': 'popupClosed',
                'PopUpCongTacVien_Thoat': 'ctvClosed',
                'PopUpNguoiGioiThieu_Thoat': 'ngtClosed',
            },
            // Thông tin phiếu yêu cầu
            dshd: [],
            NgayYeuCau: moment(new Date()).format("DD/MM/YYYY"),
            dateFormat: "DD/MM/YYYY",
            // Thông tin khách hàng
            NgayLapHD: moment(new Date()).format("DD/MM/YYYY"),
            diachiKH: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            // Loại hình thuê bao mới
            txtSoLuong: 0,
            ip_ims: "",
            // Chi tiết tiền khoảng mục
            dtienld: 0,
            dichvu: {
                dtien_dv: 0,
                dvat_dv: 0,
            },
            thietbi: {
                tien_tbi: 0,
                vat_tbi: 0,
                tien_kmtbi: 0,
                vat_kmtbi: 0,
                tien_tgtb_tragop: 0,
                vat_tgtb_tragop: 0,
                tien_tgtb_tratruoc: 0,
                vat_tgtb_tratruoc: 0,
                dtien_kmtb: 0,
                dvat_kmtb: 0,
                dtientbi: 0,
                dvattbi: 0,
            },
            // Dịch vụ cộng thêm
            lvDvgt: {
                list: [],
                value: null
            },
            dvgt: {
                tien_dv: 0,
                vat_dv: 0,
                tien_km_dvgt: 0,
                vat_km_dvgt: 0,
            },
            // Mua thiết bị
            lvMuaThietbi: {
                list: [],
                value: null
            },
            dsCTM_TBI: [],
            // Danh sách thuê bao
            lvwThueBao: [],
            // Chung
            state: {},
            ma_tb: "",
            thamsomacdinh: {},
            dsDBTB: [],
            dsDBTBBC:[],
            iKenhTN: 1,
            dsHDTB_KV: [],
            dsHDKH: [],
            dsHDTT: [],
            dsHDTB: [],
            dsTTTT: [],
            dsCTTHD: [],
            dsDiaChi: [],
            dsHDTB_IMS: [],
            dsHDTB_CD: [],
            dsDiaChiHDKH: [],
            dsDiaChiHDTT: [],
            dsDiaChiHDTB: [],
            dsHDTBDV: [],
            dsDK_DVGT: [],
            dsDK_DVGT_BC: [],
            dsHDBC: [],
            dsHDTBBC: [],
            dsHDBCCD: [],
            dsHDBCIMS: [],
            dsDK_DVGT_BC: [],
            dsDiaChi_BanCheo: [],
            dsThongSoTC: [],
            dsThongSoBan: [],
            hdtb_id: null,
            vchuquan_id: 0,
            vchuquanld_id: 0,
            vkieuhd_id: 0,
            quytrinh_id: 0,
            quytrinh_tc_tn: 0,
            huonggiao_tc_tn: 0,
            nganhnghe_id: 0,
            hdtt_id: 0,
            diachikh_id: 0,
            diachitt_id: 0,
            diachitb_id: 0,
            diachild_id: 0,
            diachibc_id: 0,
            loi_sinh_magd: false,
            listbox: {
                // Thông tin phiếu yêu cầu
                kieu_ld: {
                    list: [],
                    value: KieuLapDat.DM_CODINH,
                },
                // Loại hình thuê bao cũ                
                dichvu_vt: {
                    list: [],
                    value: DichVuVienThong.CO_DINH,
                },
                loaihinh_tb: {
                    list: [],
                    value: 0,
                },
                trangbi: {
                    list: [],
                    valuewan: TRANGBI.VIENTHONG_TRANGBI,
                    valuetsl: 0,
                    valuecd: 3,
                    valueims: 3,
                    valueadsl: 0,
                    valuedd: 0,
                    valuegp: 0,
                },
                // Loại hình thuê bao mới
                dichvu_vt_moi: {
                    list: [],
                    value: DichVuVienThong.IMS,
                },
                loaihinh_tb_moi: {
                    list: [],
                    value: 0,
                },
                muccuoc_tb: {
                    list: [],
                    value: 0,
                },
                ht_chitietdv_ims: {
                    list: [],
                    value: 0,
                },
                thietbi_ims: {
                    list: [],
                    value: 0,
                },
                trunk: {
                    list: [],
                    value: 0,
                    checkbox_value: false,
                },
                hinhthuc_tc: {
                    list: [],
                    value: 0,
                    valueims: 0,
                    enabled: true
                },
                // Thông tin thuê bao
                doituong: {
                    list: [],
                    value: "",
                },
                donvi_ql: {
                    list: [],
                    value: 0,
                    valueql: 0
                },
                tram_tc: {
                    list: [],
                    value: 0
                },
                kieu_hd: {
                    list: [],
                    value: 0,
                },
                nganhngheloc: {
                    list: [],
                    value: 0,
                },
                loai_kh: {
                    list: [],
                    value: null,
                },
            },
            thongso_ban: Reset_Thongso_Thuebao(),
            thongso_tc: Reset_Thongso_Thuebao(),
            // Modal thong so thi cong
            khachhang_id: 0,
            hdkh_id: 0,
            //Modal thong so thi cong
            thuebao: {
                codinh: {
                    somay: '',
                },
                tentb: '',
                // Thông tin thuê bao
                khuvuc: '',
                khuvuc_id: 0,
                ghichu: '',
            },
            thuebaocd: {
                matn: '',
                sothang_thueso: 0,
                noidung_thueso: '',
                kieu_thueso: 0,
                madoicap: null,
                cuocdoitac: 0,
                ma_vnpt: -1,
            },
            // Thông tin thuê bao
            diachiTB: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            diachiLD: {
                DIACHI: '',
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            diachiNDD: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            diachiTT: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            diachiBC: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            controls: { 
                lblThietBiIMS: {Visible: true },
                cboThietBiIMS: {Visible: true },
                lbSoLuong: {Visible: true },
                txtSoLuong: {Visible: true },
                txtIp_IMS: {Visible: true },
                cboChiTietDVMoi: {Visible: true }, 
            },
            ChiTietDV_IMS: {
                didong: 1, // Triển khai trên nền di động
                internet: 2, // Triển khai trên nền internet
                duongrieng: 3, // Sử dụng đường riêng
            },
        }    
    },
    methods: {
        // Search theo Mã giao dịch
        async MaGDChanged() {
            this.dshd = await this.LAY_DS_HOPDONG_THEO_MA_GD();
            console.log("Dshd" , this.dshd);
            if(this.dshd.length <= 0){
                 this.$toast.warning('Mã giao dịch này không tồn tại!');
                return;
            }
           
            this.HienThiTTHopDongKH(this.dshd, 1);
        },
        // Search Mã thuê bao
        async txtMaTB_KeyPress(){ 
            this.Loading = true;
            var ma_tb = this.thuebao.codinh.somay.trim();
            try{
                if(ma_tb != "")
                {
                    await api.get_tinh_thicong(this.axios, { 
                            "matb": ma_tb
                        }).then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                                this.tinh.tinhthicong_id = response.data.data[0].tinh_thicong;
                            }
                        }
                    );
                    await api.get_thuebao_id_theo_matb(this.axios, { 
                            "matb": ma_tb,
                            "tinhthicong_id": this.tinh.tinhthicong_id
                        }).then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                                this.tinh.thuebao_tc_id = response.data.data;
                            }
                        }
                    );
                    var ds = [];
                    var test = {
                        "dichVuVtId": this.listbox.dichvu_vt_moi.value*1,
                        "donViDlId": 0,
                        "loaiHdId": LoaiHopDong.CHUYENDOI_LH,
                        "loaiTbId": 0,
                        "maTb": ma_tb,
                        "ttHdId": TrangThaiHD.MOI
                    };
                    await api.get_ds_hopdong_theo_ma_tb(this.axios, { 
                            "dichVuVtId": this.listbox.dichvu_vt_moi.value*1,
                            "donViDlId": 0,
                            "loaiHdId": LoaiHopDong.CHUYENDOI_LH,
                            "loaiTbId": 0,
                            "maTb": ma_tb,
                            "ttHdId": TrangThaiHD.MOI
                        }).then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                                ds = response.data.data;
                            }
                        }
                    );
                    this.clear();
                    await this.LAY_DS_DVGT_MACDINH(this.listbox.loaihinh_tb_moi.value * 1);
                    this.thuebao.codinh.somay = ma_tb;
                    if(ds.length>0) {
                        await this.HienThiTTHopDongKH(ds, 2); 
                    } 
                    else {
                        await this.HienThiTT_DanhBa(ma_tb);                        
                        try{
                            console.log(this.thuebao);
                            var dskv = [];
                            this.thuebao.khuvuc = "";
                            await api.lay_khuvuc_theo_danhba(this.axios, {"vhddb_id": this.thuebao.thuebao_id}).then(
                            (response) => {
                                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                                    dskv = response.data.data;
                                }
                            });
                            if(dskv.length > 0)
                            {
                                this.thuebao.khuvuc = dskv[0].ma_kv?dskv[0].ma_kv:dskv[0].MA_KV;
                                this.thuebao.khuvuc_id = dskv[0].khuvuc_id?dskv[0].khuvuc_id:dskv[0].KHUVUC_ID;                   
                            }
                        }catch(ex){
                            // kiểm tra
                            // this.$toast.warning(''+ ex);
                            this.$toast.warning("Thuê bao chưa có khu vực quản lý, liên hệ với admin để xử lý !!!");
                            this.Loading = false;
                        } 
                    }
        
                    this.Loading = false; 
                }
            } catch(ex){
                this.$toast.error(''+ ex);
                this.Loading = false;
            }
        },
        async SaveClicked() {
             
            this.Loading = true;
            try {
              var isvalid = true;
              document.getElementsByTagName('input').forEach((item)=>{
                if(isvalid && !item.validity.valid) {
                  isvalid = false;
                  this.$toast.error(item.getAttribute('title'));
                  item.focus();
                }
              });
              if(!isvalid) return;
              if (this.tinh.tinhthicong_id > 0 && (this.thongso_ban.muccuoctb_id == 0 || this.thongso_tc.muccuoctb_id == 0))
              {
                this.$toast.warning("Bạn chưa chọn mức cước cho thuê bao bán và thi công. Ấn vào kiểu lắp đặt để lấy thông tin");
                return;
              }
              await this.CapNhat();
            } catch(ex) { this.$toast.error(''+ex); }
            finally { this.Loading = false; }
            //this.Loading = false;
        },
        tsbtnNhapMoi_Click(){
            this.buttonSet=1;
            this.clear();
            this.khachhang.magd = "";
        },
        async CapNhat() {
            this.loading(true); 
            try{ 
                var themmoi = this.buttonSet==1;
            
                if (await this.KiemTraDL_KhachHang() == false) return;
                if (await this.KiemTraDL_ThueBao() == false) return;
                if (await this.KIEMTRA_CDLH() == false) // Dũng PA thêm
                    return;
                this.ma_tb = this.thuebao.codinh.somay;
                let param =  {
                    tinhthicong_id: this.tinh.tinhthicong_id,
                    loaihd_id: LoaiHopDong.CHUYENDOI_LH,
                    loaitb_id:  this.listbox.loaihinh_tb_moi.value,
                    tocdo_id: -1,
                    muccuoc_id: -1,
                    kieuld_id: this.thongso_tc.kieuld_id
                }
                // Lấy hướng giao, quy trình tại tỉnh lắp đặt
                var rs_qt = await api.LayHuongGiao_TiepNhan(this.axios,param);
                if (!rs_qt.data || !rs_qt.data.data || rs_qt.data.data.length <= 0)
                {
                    this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý");
                    return;
                }
                this.quytrinh_tc_tn = 0;
                this.huonggiao_tc_tn = 0;
                this.quytrinh_tc_tn = rs_qt.data.data[0].quytrinh_id;
                this.huonggiao_tc_tn = rs_qt.data.data[0].huonggiao_id;
                // this.luong_id = Convert.ToInt32(ds.Tables[0].Rows[0]["luong_id"].ToString());
                
                let input1 = {
                    kieuLdId: this.listbox.kieu_ld.value,
                    loaiHdId: LoaiHopDong.CHUYENDOI_LH,
                    loaiTbId: this.listbox.loaihinh_tb_moi.value,
                    mucCuocId: this.thongso_ban.muccuoc_id,
                    tocDoId: -1,
                }
                rs_qt = await api.lay_huonggiao_tiepnhan_v3(this.axios,input1);
                if (!rs_qt.data || !rs_qt.data.data || rs_qt.data.data.length <= 0)
                {
                    this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Bán. Liên hệ Admin để xử lý");
                    return;
                }
                this.quytrinh_id = rs_qt.data.data.quytrinh_id;

                
                if(await this.TaoDuLieu(themmoi, 1) == false)
                    return;
                if(await this.TaoDuLieuBC(themmoi) == false)
                    return;
                console.log('TaoDuLieu done');
                this.dsThongSoTC.push(Object.assign({}, this.thongso_tc));
                var pdata = {
                    "themmoi": themmoi == true ? 1 : 0, 
                    "dichvu_id_moi": this.listbox.dichvu_vt_moi.value,
                    "dichvu_id":  this.listbox.dichvu_vt.value,
                    "loaitb_id": (themmoi == true ? this.listbox.loaihinh_tb_moi.value : this.listbox.loaihinh_tb.value),
                    "loaihinhtbmoi_id": this.listbox.loaihinh_tb_moi.value,
                    "ma_tb": this.ma_tb,
                    "thuebao_id": this.thuebao.thuebao_id,
                    "matn_adsl": this.thuebaocd.matn,
                    "kieuld_id": this.listbox.kieu_ld.value,
                    "khachhang_id": this.khachhang.khachhang_id,
                    "ma_gd": this.khachhang.magd,
                    "loaihd_id": LoaiHopDong.CHUYENDOI_LH,
                    "hdtb_id": this.hdtb_id,
                    "hdkh_id": this.khachhang.hdkh_id,
                    "ma_nd": this.$root.token.getUserName(),
                    "may_cn": this.may_cn,
                    "ip_cn": this.ip_cn,
                    "tinh_tc_id": this.tinh.tinhthicong_id,
                    "js_hd_thuebao": this.UpperCasePropertyList(this.dsHDTB),
                    "js_hdtb_ims": this.UpperCasePropertyList(this.dsHDTB_IMS),
                    "js_thongtin_tt": this.UpperCasePropertyList(this.dsTTTT),
                    "js_ct_tienhd": this.UpperCasePropertyList(this.dsCTTHD),
                    "js_ct_mua_tbi": this.UpperCasePropertyList(this.dsCTM_TBI),
                    "js_diachi": this.UpperCasePropertyList(this.dsDiaChi),
                    "js_diachi_hdtb": this.UpperCasePropertyList(this.dsDiaChiHDTB),
                    "js_hdtb_dv": this.UpperCasePropertyList(this.dsHDTBDV),
                    "js_dangky_dvgt": this.UpperCasePropertyList(this.dsDK_DVGT),
                    "js_hdtb_kv": this.UpperCasePropertyList(this.dsHDTB_KV),
                    "js_hdtb_cd": this.UpperCasePropertyList(this.dsHDTB_CD),
                    "js_hd_khachhang": this.UpperCasePropertyList(this.dsHDKH),
                    "js_hd_thanhtoan": this.UpperCasePropertyList(this.dsHDTT),
                    "js_diachi_hdkh": this.UpperCasePropertyList(this.dsDiaChiHDKH),
                    "js_diachi_hdtt": this.UpperCasePropertyList(this.dsDiaChiHDTT),
                    "js_thongso_tc": this.UpperCasePropertyList(this.dsThongSoTC),
                    "js_hd_bancheo": this.UpperCasePropertyList(this.dsHDBC),
                    "js_hdtb_bancheo": this.UpperCasePropertyList(this.dsHDTBBC),
                    "js_hdbc_mgwan": null,
                    "js_hdbc_adsl": null,
                    "js_hdbc_cd": this.UpperCasePropertyList(this.dsHDBCCD),
                    "js_dangky_dvgt_bc": this.UpperCasePropertyList(this.dsDK_DVGT_BC),
                    "js_hdbc_tsl": null,
                    "js_hdbc_ims": this.UpperCasePropertyList(this.dsHDBCIMS),
                    "js_lydohuy_hdtb_bc": null,
                    "js_diachi_bancheo": this.UpperCasePropertyList(this.dsDiaChi_BanCheo)
                };
                console.log('pdata', pdata);
                var themthanhcong = false;
                var apidata = {};
                await api.capnhat_themmoi(this.axios, {"p_ds_para":JSON.stringify(pdata)}).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        apidata = JSON.parse(response.data.data.js_returnds);
                        if(apidata.ERROR_CODE!=1) {
                            this.$toast.error(apidata.MESSAGE); 
                            return;
                        } 
                        this.$toast.success("Cập nhật thành công!"); 
                        themthanhcong = true;
                    }
                });
                if(themthanhcong== true)
                    await this.HienThiTTHopDongKH(await this.LAY_DS_HOPDONG_THEO_MA_GD(), 1);
                this.loading(false); 
            }
            finally{
                this.loading(false);
            }
        },
        tsbtnXoa_Click() {
            console.log(this.khachhang.hdkh_id);
            this.$bvModal.msgBoxConfirm('Xác nhận hành động', {
                title: 'Bạn thật sự muốn xóa hợp đồng không ?',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if (v) {
                    try {
                        var DS_HDTB = this.lvwThueBao.map(x => ({
                            "HDTB_ID": x.hdtb_id,
                            "LOAITB_ID": x.loaitb_id
                        }));
                        var pdata = {
                            "vhdkh_id": this.khachhang.hdkh_id,
                            "vloaihd_id": LoaiHopDong.CHUYENDOI_LH
                        }
                        this.loading(true);
                        await api.xoa_hdkh_v2(this.axios, pdata).then((response) => {
                            this.loading(false);
                            if (response && response.data &&  response.data.error_code && response.data.error_code === 'BSS-00000000') {
                                //var data = this.LowerCasePropertyList([JSON.parse(response.data.data)])[0];
                                this.$toast.success('Đã xóa hợp đồng chuyển đổi loại hình thành công trên hệ thống của viễn thông tỉnh');
                                this.clear();
                                this.khachhang.magd = "";
                                this.buttonSet = 0;
                            }
                        });
                    } catch (ex) {
                        this.$toast.error('' + ex);
                        this.loading(false);
                    }
                }
            });
        },
        ChuyenTrang(page,param) {
          this.$router.push({ name: page, params: param })
        },
        async tsbtnThemTB_Click() { 
            this.loading(true); 
            try{
                if(await this.TaoDuLieu(true, 2) == false)
                    return;
    
                var pdata = {
                    "dichvu_id_moi": this.listbox.dichvu_vt_moi.value,
                    "dichvu_id": this.listbox.dichvu_vt.value,
                    "loaitb_id": this.listbox.loaihinh_tb.value, 
                    "loaitb_id_moi": this.listbox.loaihinh_tb_moi.value,
                    "ma_tb": this.ma_tb,
                    "thuebao_id": this.thuebao.thuebao_id,
                    "matn_adsl": this.thuebaocd.matn,
                    "kieuld_id": this.listbox.kieu_ld.value,
                    "khachhang_id": this.khachhang.khachhang_id,
                    "ma_gd": this.khachhang.magd,
                    "loaihd_id": LoaiHopDong.CHUYENDOI_LH,
                    "hdkh_id": this.khachhang.hdkh_id, 
                    "js_hd_thuebao": this.UpperCasePropertyList(this.dsHDTB),
                    "js_hdtb_ims": this.UpperCasePropertyList(this.dsHDTB_IMS),
                    "js_thongtin_tt": this.UpperCasePropertyList(this.dsTTTT),
                    "js_ct_tienhd": this.UpperCasePropertyList(this.dsCTTHD),
                    "js_ct_mua_tbi": this.UpperCasePropertyList(this.dsCTM_TBI),
                    "js_diachi": this.UpperCasePropertyList(this.dsDiaChi),
                    "js_diachi_hdtb": this.UpperCasePropertyList(this.dsDiaChiHDTB),
                    "js_hdtb_dv": this.UpperCasePropertyList(this.dsHDTBDV),
                    "js_dangky_dvgt": this.UpperCasePropertyList(this.dsDK_DVGT),
                    "js_hdtb_kv": this.UpperCasePropertyList(this.dsHDTB_KV),
                    "js_hdtb_cd": this.UpperCasePropertyList(this.dsHDTB_CD),
                    "js_hd_khachhang": this.UpperCasePropertyList(this.dsHDKH),
                    "js_hd_thanhtoan": this.UpperCasePropertyList(this.dsHDTT),
                    "js_diachi_hdkh": this.UpperCasePropertyList(this.dsDiaChiHDKH),
                    "js_diachi_hdtt": this.UpperCasePropertyList(this.dsDiaChiHDTT)
                };        
                console.log('pdata', pdata);
                var themthanhcong = false;
                await api.themhdthuebao(this.axios, {"p_ds_para":JSON.stringify(pdata)}).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        var apidata = JSON.parse(response.data.data.p_js_returnds);
                        console.log("API.post_fn_chuyendoicdims_themhdthuebao", apidata);
                        if(apidata['ERROR_CODE']!=1) {
                            this.$toast.error(apidata['MESSAGE']); 
                            return;
                        } 
                        this.$toast.success("Thêm mới thuê bao thành công!"); 
                        var themthanhcong = true;
                    }
                });
                if(themthanhcong== true)
                    await this.HT_TT();
                this.loading(false); 
            }
            finally{
                this.loading(false);
            }
            
        }, 
        tsbtnXoaTB_Click() { 
            if(!this.hdtb_id || this.hdtb_id <= 0){
                this.$toast.error("Bạn chưa chọn thuê bao!"); 
                return;
            }
            this.$bvModal.msgBoxConfirm('Xác nhận hành động', {
                title: 'Bạn thật sự muốn xóa thuê bao không ?',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if (v) { 
                    try { 
                        this.loading(true);
                        await this.XOA_HDTB(this.hdtb_id);
                        await this.HT_TT(); 
                        this.loading(false);
                    } catch (ex) {
                        this.$toast.error('' + ex);
                    }
                }
            });
        },
        async XOA_HDTB(hdtb_id)
        { 
            await api.xoa_hdtb(this.axios, {params: {"hdtbId": hdtb_id}}).then((response) => {
                if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                     this.$toast.success("Xóa thuê bao thành công!");
                }
                else
                {
                    this.$toast.error("Xóa thất bại!");
                }
            }); 
        },
        async TaoDuLieu(themmoi, loai) {
            this.ma_tb = this.thuebao.codinh.somay;
            console.log(222222)
            console.log(this.thuebao)
            if(loai == 1)
                await this.TaoDuLieu_HDKH(themmoi);
            await this.TaoDuLieu_HDTT(themmoi);
            await this.TaoDuLieu_HDTB(themmoi);
            await this.LoadDL_KV(this.thuebao.khuvuc_id);
            await this.TaoDuLieu_THONGTIN_TT(themmoi);
            await this.TaoDuLieu_CT_TIENHD(true);
            await this.TaoDuLieu_DiaChi(themmoi, 4);
            await this.TaoDuLieu_HDTBDV(themmoi);
            for (var i = 0; i < this.dsCTM_TBI.length; i++) {
                this.dsCTM_TBI[i]["hdtb_id"] = this.hdtb_id;
            }
            if(await this.TaoDuLieu_HDTB_CON(themmoi) == false)
                return false;
            await this.TaoDuLieu_DKDV(); //nhapt
            return true;
        },
        async TaoDuLieuBC(themmoi){
            await this.TaoDuLieu_HDTBBC(themmoi);
            console.log("TaoDuLieu_HDTBBC DONE");
            await this.TaoDuLieu_DiaChi_BanCheo(themmoi, 4);
            console.log("TaoDuLieu_DiaChi_BanCheo DONE");
            await this.TaoDuLieu_HDTB_CON_BC(themmoi);
            console.log("TaoDuLieu_HDTB_CON_BC DONE");
            await this.TaoDuLieu_DKDV_BC();
            console.log("TaoDuLieu_DKDV_BC DONE");
        },
        async TaoDuLieu_HDTBBC(themmoi){
            this.dsHDBC = [];
            this.dsHDTBBC = [];
            var dulieu = [];
            var rowHDBC = {};
            var rowHDTBBC = {};
            console.log(this.tinh.tinhthicong_id);
            console.log(this.tinh.thuebao_tc_id);
            await api.bc_db_thuebao_id(this.axios, {
                "tinhthicong_id": this.tinh.tinhthicong_id,
                "thuebao_id": this.tinh.thuebao_tc_id
            }).then(
                (response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                    {
                        dulieu = response.data.data;
                    }
                }
            );
            rowHDBC.HDTB_BAN = this.hdtb_id;
            rowHDBC.TINH_THICONG = this.tinh.tinhthicong_id;
            rowHDBC.TINH_BAN = this.$root.token.getPhanVungID();
            rowHDBC.TRANG_THAI = 0;
            rowHDBC.LOAIHD_BANCHEO = 1;
            rowHDBC.THUEBAO_ID_BAN = this.thuebao.thuebao_id;
            rowHDBC.THUEBAO_ID_THICONG = this.tinh.thuebao_tc_id;
            var temp_thanhtoan_id_ban = 0;
            await api.post_tt_db_thuebao(this.axios, {
              "param": this.thuebao.thuebao_id,
              "type": 1
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  temp_thanhtoan_id_ban = response.data.data * 1; 
                }
            });
            rowHDBC.THANHTOAN_ID_BAN = temp_thanhtoan_id_ban;
            rowHDBC.KHACHHANG_ID_BAN = this.khachhang.khachhang_id;
            rowHDBC.THANHTOAN_ID_TC = dulieu[0]["thanhtoan_id"];
            rowHDBC.KHACHHANG_ID_TC = dulieu[0]["khachhang_id"];
            this.dsHDBC.push(Object.assign({}, rowHDBC));
            await api.get_danhba_theo_thuebao_id_bc(this.axios, { 
                        //"phanvung_id": this.$root.token.getPhanVungID(),
                        "tinhthicong_id": this.tinh.tinhthicong_id,
                        "thuebao_id": this.tinh.thuebao_tc_id,
                        "dichvuvt_id": this.listbox.dichvu_vt.value,
                        "donvi_dl_id": 0,
                        "loaihd_bancheo": 2
                    }).then(
                    (response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                            rowHDTBBC = response.data.data[0];
                        }
                    }
                );
            rowHDTBBC = this.UpperCasePropertyList([rowHDTBBC])[0];
            rowHDTBBC.HDTB_ID = this.hdtb_id;
            rowHDTBBC.HDKH_ID = this.khachhang.hdkh_id;
            rowHDTBBC.TTHD_ID = 1;
            rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn;// không cần
            rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn;// không cần
            rowHDTBBC.LOAITB_ID = this.listbox.loaihinh_tb_moi.value;
            rowHDTBBC.DICHVUVT_ID = this.listbox.dichvu_vt_moi.value;
            rowHDTBBC.KIEULD_ID = this.thongso_tc.kieuld_id;
            rowHDTBBC.DONVI_ID = this.$root.token.getDonViID();
            rowHDTBBC.NGUOI_CN = this.nguoi_cn;
            rowHDTBBC.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
            rowHDTBBC.MAY_CN = this.may_cn;
            rowHDTBBC.IP_CN = this.ip_cn;
            rowHDTBBC.GHICHU = this.thuebao.ghichu;
            rowHDTBBC.MUCUOCTB_ID = this.thongso_tc.muccuoctb_id;
            rowHDTBBC.TEN_TB = this.thuebao.tentb ? this.thuebao.tentb.trim() : null;
            rowHDTBBC.DOITUONG_ID = this.thongso_tc.doituong_id;
                rowHDTBBC.NGAY_SN_TB = moment(rowHDTBBC.NGAY_SN_TB, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                rowHDTBBC.NGAYLAP_HD = moment(rowHDTBBC.NGAYLAP_HD, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                rowHDTBBC.NGAY_HK = moment(rowHDTBBC.NGAY_HK, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                rowHDTBBC.NGAY_SN = moment(rowHDTBBC.NGAY_SN, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                rowHDTBBC.NGAY_SD = moment(rowHDTBBC.NGAY_SD, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                rowHDTBBC.NGAYCAP = moment(rowHDTBBC.NGAYCAP, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
            if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.ISDN2B_CD
                   || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.ISDN2B_CQ
                   || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.ISDN30B_CD
                   || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.ISDN30B_CQ
                   || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.TRUNGKE_2M
                   || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.TRUNGKE_THUONG
                   || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.TRUNGKE_TUONGTU)
            {
                rowHDTBBC.MUCUOC_TB = this.thongso_tc.muccuoc_id;
            }
            else
            {
                var s = "";
                await api.post_tt_muccuoc_tb(this.axios, {
                "param": this.thongso_tc.muccuoctb_id,
                "type": 1
                }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        s = response.data.data; 
                    }
                });
                if (s != "-1")
                    rowHDTBBC.MUCUOC_TB = s *1;
            }
            var status = "";
            if (this.listbox.dichvu_vt.value == DichVuVienThong.ADSL || this.listbox.dichvu_vt.value == DichVuVienThong.DI_DONG)
                status = "0";
            // if (bangts.KT_ThaoTac(luong_id, "STATUS_TONGDAI_CD")) // không cần kiểm tra do đã kiểm tra dưới db
            //     status = "0";
            rowHDTBBC.STATUS = status;
            this.dsHDTBBC.push(Object.assign({}, rowHDTBBC));
        },
        async TaoDuLieu_DiaChi_BanCheo(themmoi, kieu){            
            this.dsDiaChi_BanCheo = [];
            if (kieu == 1 || kieu == 4)
            {
                var rowDiaChiKH_BC = {};
                rowDiaChiKH_BC.DIACHI_ID = this.diachikh_id;
                rowDiaChiKH_BC.TINH_ID = this.diachiKH.TINH_ID;
                rowDiaChiKH_BC.QUAN_ID = this.diachiKH.QUAN_ID;
                rowDiaChiKH_BC.PHUONG_ID = this.diachiKH.PHUONG_ID;
                if (this.diachiKH.PHO_ID != 0)
                    rowDiaChiKH_BC.PHO_ID = this.diachiKH.PHO_ID;
                //if (apkh_id != 0)
                //    rowDiaChiKH_BC.AP_ID = apkh_id;
                //if (khukh_id != 0)
                //    rowDiaChiKH_BC.KHU_ID = khukh_id;
                //if (dacdiemkh_id != 0)
                //    rowDiaChiKH_BC.DACDIEM_ID = dacdiemkh_id;
                rowDiaChiKH_BC.SONHA = this.diachiKH.SONHA ? this.diachiKH.SONHA.trim() : '';
                rowDiaChiKH_BC.DIACHI = this.diachiKH.DIACHI ? this.diachiKH.DIACHI.trim() : '';
                rowDiaChiKH_BC.LOAI_DC = 1;
                this.dsDiaChi_BanCheo.push(Object.assign({}, rowDiaChiKH_BC));
            }
            if (kieu == 2 || kieu == 4)
            {
                var rowDiaChiTT_BC = {};
                //DiachiTT
                rowDiaChiTT_BC.DIACHI_ID = this.diachitt_id;
                rowDiaChiTT_BC.TINH_ID = this.diachiTT.TINH_ID;
                rowDiaChiTT_BC.QUAN_ID = this.diachiTT.QUAN_ID;
                rowDiaChiTT_BC.PHUONG_ID = this.diachiTT.PHUONG_ID;
                if (this.diachiTT.PHO_ID != 0)
                    rowDiaChiTT_BC.PHO_ID = this.diachiTT.PHO_ID;
                //if (aptt_id != 0)
                //    rowDiaChiTT_BC.AP_ID = aptt_id;
                //if (khutt_id != 0)
                //    rowDiaChiTT_BC.KHU_ID = khutt_id;
                //if (dacdiemtt_id != 0)
                //    rowDiaChiTT_BC.DACDIEM_ID = dacdiemtt_id;
                rowDiaChiTT_BC.SONHA = this.diachiTT.SONHA ? this.diachiTT.SONHA.trim() : '';
                rowDiaChiTT_BC.DIACHI = "";
                rowDiaChiTT_BC.LOAI_DC = 2;
                this.dsDiaChi_BanCheo.push(Object.assign({}, rowDiaChiTT_BC));
            }
            if (kieu == 3 || kieu == 4)
            {
                var rowDiaChiTB_BC = {};
                var rowDiachiLD_BC = {};
                //Địa chỉ lắp đặt --> dataset dsdiachi
                rowDiachiLD_BC.DIACHI_ID = this.diachild_id;
                rowDiachiLD_BC.TINH_ID = this.diachiLD.TINH_ID;
                rowDiachiLD_BC.QUAN_ID = this.diachiLD.QUAN_ID;
                rowDiachiLD_BC.PHUONG_ID = this.diachiLD.PHUONG_ID;
                if (this.diachiLD.PHO_ID != 0)
                    rowDiachiLD_BC.PHO_ID = this.diachiLD.PHO_ID;
                if (this.diachiLD.AP_ID != 0)
                    rowDiachiLD_BC.AP_ID = this.diachiLD.AP_ID;
                if (this.diachiLD.KHU_ID != 0)
                    rowDiachiLD_BC.KHU_ID = this.diachiLD.KHU_ID;
                if (this.diachiLD.DACDIEM_ID != 0)
                    rowDiachiLD_BC.DACDIEM_ID = this.diachiLD.DACDIEM_ID;
                rowDiachiLD_BC.SONHA = this.diachiLD.SONHA ? this.diachiLD.SONHA.trim() : '';
                rowDiachiLD_BC.DIACHI = this.diachiLD.DIACHI ? this.diachiLD.DIACHI.trim() : '';
                rowDiachiLD_BC.LOAI_DC = 4;
                this.dsDiaChi_BanCheo.push(Object.assign({}, rowDiachiLD_BC));
                //Địa chỉ thuê bao --> dataset dsdiachi
                rowDiaChiTB_BC.DIACHI_ID = this.diachitb_id;
                rowDiaChiTB_BC.TINH_ID = this.diachiTB.TINH_ID;
                rowDiaChiTB_BC.QUAN_ID = this.diachiTB.QUAN_ID;
                rowDiaChiTB_BC.PHUONG_ID = this.diachiTB.PHUONG_ID;
                if (this.diachiTB.PHO_ID != 0)
                    rowDiaChiTB_BC.PHO_ID = this.diachiTB.PHO_ID;
                if (this.diachiTB.AP_ID != 0)
                    rowDiaChiTB_BC.AP_ID = this.diachiTB.AP_ID;
                if (this.diachiTB.KHU_ID != 0)
                    rowDiaChiTB_BC.KHU_ID = this.diachiTB.KHU_ID;
                if (this.diachiTB.DACDIEM_ID != 0)
                    rowDiaChiTB_BC.DACDIEM_ID = this.diachiTB.DACDIEM_ID;
                rowDiaChiTB_BC.SONHA = this.diachiTB.SONHA ? this.diachiTB.SONHA.trim() : '';
                rowDiaChiTB_BC.DIACHI = this.diachiTB.DIACHI ? this.diachiTB.DIACHI.trim() : '';
                rowDiaChiTB_BC.LOAI_DC = 3;
                this.dsDiaChi_BanCheo.push(Object.assign({}, rowDiaChiTB_BC));
            }
        },
        async TaoDuLieu_HDTB_CON_BC(themmoi){
            this.dsHDBCIMS = [];
            this.dsHDBCCD = [];
            var rowHDBCIMS = {};
            var rowHDBC_CD = {};
            if(this.listbox.dichvu_vt_moi.value * 1 == DichVuVienThong.IMS)
            {
                console.log('TaoDuLieu_HDTB_CON_BC IMS')
                rowHDBCIMS.HDTB_ID = this.hdtb_id;;
                rowHDBCIMS.THIETBI_ID = this.listbox.thietbi_ims.value;
                if (this.vchuquan_id != 0)
                    rowHDBCIMS.CHUQUAN_ID = this.vchuquan_id;
                else
                    rowHDBCIMS.CHUQUAN_ID = null;
                if (this.vchuquanld_id != 0)
                    rowHDBCIMS.CHUQUANLD_ID = this.vchuquanld_id;
                else
                    rowHDBCIMS.CHUQUANLD_ID = null;
                var kieu = 0;
                await api.post_tt_kieu_ld(this.axios, {
                    "param": this.thongso_tc.kieuld_id,
                    "type": 1
                }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.listbox.loaihinh_tb.value = response.data.data * 1; 
                    }
                });
                if (kieu == 1 || kieu == 2 || kieu == 4)
                {
                    rowHDBCIMS.MADOICAP = this.thongso_tc.ma_doicap*1;
                }
                else
                {
                    if(themmoi == true)
                    {
                        rowHDBCIMS.MADOICAP = await this.GetKey("MADOICAP");
                    }
                    else //TH còn lại sinh mã đôi cáp
                    {
                        console.log(111111111)
                        console.log(this.thongso_tc)
                        if(typeof this.thongso_tc.ma_doicap !== 'undefined')//if(this.thongso_tc.ma_doicap.Trim().length > 0)
                        {
                            // Trong trường hợp : thêm mới kiểu lắp trên đường có sẵn hoặc lắp kèm, sau đó ấn ghi lại chọn trên đường ko số
                            // => kiểm tra xem đã có thuê bao nào ăn vào mã đôi cáp này hay chưa
                            // Nếu có thuê bao ăn vào rồi thì pải sinh mã đôi cáp thong
                            var kt = 0;
                            
                            await api.kiemtra_thuebao_sudung_madoicap(this.axios,{params: {"maDoiCap": this.thongso_tc.ma_doicap}}).then((response) => {
                                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                                    kt = response.data.data; 
                                }
                            });
                            if(kt != 0)
                                rowHDBCIMS.MADOICAP = await this.GetKey("MADOICAP");
                            else
                                rowHDBCIMS.MADOICAP = this.thongso_tc.ma_doicap * 1;
                        }
                        else // Trường hợp trên text chưa có mã đôi cáp thì sinh mã đôi cáp khác
                            rowHDBCIMS.MADOICAP = await this.GetKey("MADOICAP");
                    }
                }
                if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.IMS_SIPTRUNKING)
                    rowHDBCIMS.SL_CUOCGOI = this.txtSoLuong * 1;
                else
                    rowHDBCIMS.SL_CUOCGOI = null;
                rowHDBCIMS.MATB_TN = this.thongso_tc.ma_tn?this.thongso_tc.ma_tn:''
                rowHDBCIMS.TRANGBI_ID = this.listbox.trangbi.valueims;
                var dauso_ims = "";
                await api.lay_dauso_ims(this.axios,{
                    "p_param": this.$root.token.getMaTinh(),
                    "p_param1":  this.$root.token.getMaTinh(),
                    "p_type": 1
                }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        dauso_ims = response.data.data[0].dauso_ims; 
                    }
                });
                
                rowHDBCIMS.USERNAME = "84" + dauso_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                var pass_ims = "";
                if (themmoi == true) {
                    pass_ims = "";
                    if(this.state.tusinh_pass_ims == 100)
                    {
                        if(this.state.matkhau_md_ims != "")
                            pass_ims = this.state.matkhau_md_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                        else{
                            if(this.state.tusinh_pass_ims == 0)    
                                pass_ims = "84" + dauso_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                            else
                                pass_ims = this.GetRandomText(11);
                        }
                    }
                    else if(this.state.tusinh_pass_ims == 50)
                    {
                        await api.sinh_pass_random(this.axios, {params: {"kieu": 1}}).then(
                            (response) => {
                                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                                {
                                    pass_ims = response.data.data;
                                }
                            }
                        );
                    }
                    else if(this.state.tusinh_pass_ims == 30)
                    {
                        pass_ims = this.SinhPassIMS();
                    }
                    else
                    {
                        if(this.state.matkhau_md_ims != "")
                            pass_ims = this.state.matkhau_md_ims;
                        else
                        {
                            if(this.state.tusinh_pass_ims == 0)    
                                pass_ims = "84" + dauso_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                            else
                                pass_ims = this.GetRandomText(11);
                        }
                    }
                    if (this.listbox.loaihinh_tb_moi.value * 1 == LoaiHinhTB.IMS_SIPTRUNKING) {
                        rowHDBCIMS.SL_CUOCGOI = this.txtSoLuong * 1;
                        rowHDBCIMS.CTDV_ID = this.listbox.ht_chitietdv_ims.value * 1;
                        if (rowHDBCIMS.CTDV_ID == 2)
                            rowHDBCIMS.IP = this.ip_ims;
                        if (this.listbox.trunk.checkbox_value == true)
                            rowHDBCIMS.TRUNKID = this.listbox.trunk.value;
                        rowHDBCIMS.HTTC_ID = this.listbox.hinhthuc_tc.value * 1;
                    }
                    rowHDBCIMS.PASSWORD = pass_ims;
                    
                }
                this.dsHDBCIMS.push(Object.assign({}, rowHDBCIMS));
            }
            else
            {
                 console.log('TaoDuLieu_HDTB_CON_BC CD')
                rowHDBC_CD.HDTB_ID = this.hdtb_id;
                if (themmoi == true)
                    rowHDBC_CD.MADOICAP = await this.GetKey("MADOICAP");
                else
                    rowHDBC_CD.MADOICAP = this.thuebaocd.madoicap * 1;
                rowHDBC_CD.TRANGBI_ID = this.listbox.trangbi.valueims * 1;
                if (this.vchuquan_id)
                    rowHDBC_CD.CHUQUAN_ID = this.vchuquan_id;
                else
                    rowHDBC_CD.CHUQUAN_ID = null;
                if (this.vchuquanld_id != 0)
                    rowHDBC_CD.CHUQUANLD_ID = this.vchuquanld_id;
                else
                    rowHDBC_CD.CHUQUANLD_ID = null;
                this.dsHDBCCD.push(Object.assign({}, rowHDBC_CD));
            }
        },
        async TaoDuLieu_DKDV_BC(themmoi){
            this.dsDK_DVGT_BC = [];
            if(!this.m_dsThueBao_DichVu)
              return;
            this.m_dsThueBao_DichVu = this.LowerCasePropertyList(this.m_dsThueBao_DichVu); 
            var kieu_dk_dvgt = 0;
            for (var dr of this.m_dsThueBao_DichVu) {
                if (dr["dangky"].toString() == "1")
                    kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
                else if (dr["huy"].toString() == "1")
                    kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
                else {
                    continue;
                }
                var rowHDTDDVBC = {};
                rowHDTDDVBC.HDTB_ID = this.hdtb_id;
                rowHDTDDVBC.NGAY_YC =  moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
                rowHDTDDVBC.DICHVUGT_ID = (dr["dichvugt_id"] ? dr["dichvugt_id"]*1 : dr["dichvugtid"]*1);
                rowHDTDDVBC.NOIDUNG = dr["noidung"];
                rowHDTDDVBC.KIEU_YC = kieu_dk_dvgt;
                rowHDTDDVBC.TTTH = 1;
                if (dr["cuoc_sd"].toString() != "")
                    rowHDTDDVBC.CUOC_SD = dr["cuoc_sd"].toString() * 1;
                else
                    rowHDTDDVBC.CUOC_SD = 0;
                if (dr["vat_sd"].toString() != "")
                    rowHDTDDVBC.VAT_SD = dr["vat_sd"].toString() * 1;
                else
                    rowHDTDDVBC.VAT_SD = 0;
                this.dsDK_DVGT_BC.push(Object.assign({}, rowHDTDDVBC));
            }
        },
        async TaoDuLieu_HDKH(themmoi) {
            console.log('TaoDuLieu_HDKH ThemMoi:', themmoi);
            var ds_dulieu = {};
            var pdata = {
                "THUEBAO_ID": this.thuebao.thuebao_id,
                "NHAPMOI": themmoi == true ? 1 : 2,
                "TRATRUOC": 0,
                "KHACHHANG_ID": this.khachhang.khachhang_id,
                "DONVI_ID": this.$root.token.getDonViID() * 1
            };
            console.log(pdata);
            await api.post_tt_taodulieu_hdkh_cdlhtb(this.axios, {
                'p_ds_para': JSON.stringify(pdata)
            }).then(
                (response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                    {
                        ds_dulieu = JSON.parse(response.data.data);
                        if(ds_dulieu['ERROR_CODE']!=1) {
                            this.$toast.error(ds_dulieu['MESSAGE']); 
                            return;
                        } 
                    }
                }
            );
            this.dsHDKH = [];
            var rowHDKH = {};
            var vmagd_temp = "";
            if(themmoi == true) {
               this.khachhang.hdkh_id = ds_dulieu.RESULT.HDKH_ID*1;
            } 
            rowHDKH = ds_dulieu.RESULT.LAY_DS_DBKH_THEO_KHID[0];
            rowHDKH.HDKH_ID = this.khachhang.hdkh_id;
            if(themmoi == true) {
                vmagd_temp = ds_dulieu.RESULT.MA_GD;
                if(vmagd_temp.trim() == "")
                {
                    this.$toast.error("Có lỗi khi sinh mã giao dịch");
                    this.loi_sinh_magd = true;
                } 
                this.khachhang.magd = vmagd_temp;
                rowHDKH.MA_GD = this.khachhang.magd;
                rowHDKH.MA_HD = this.khachhang.mahd;
                rowHDKH.NGAY_YC = moment(new Date()).format("DD/MM/YYYY HH:mm:ss"); //TODO can doi thanh lay sysdate tu server thong qua ngay_cn cua user login, la goi ham sysdate tu server
            }
            else {
                rowHDKH.MA_GD = this.khachhang.magd;
                rowHDKH.MA_HD = this.khachhang.mahd;
                rowHDKH.NGAY_YC = moment(new Date()).format("DD/MM/YYYY HH:mm:ss"); //TODO can doi thanh lay sysdate tu server thong qua ngay_cn cua user login, la goi ham sysdate tu server
            }
            rowHDKH.MA_KH = this.khachhang.makh;
            if(!this.khachhang.makh)
                this.khachhang.khachhang_id = 0;
            if(this.khachhang.khachhang_id>0) {
                rowHDKH.KHACHHANG_ID = this.khachhang.khachhang_id;
                rowHDKH.NGAYLAP_HD =  moment(rowHDKH.NGAYLAP_HD, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                rowHDKH.KHLON_ID = rowHDKH.KHLON_ID;
                rowHDKH.PHANLOAIKH_ID = rowHDKH.PHANLOAIKH_ID;
            }
            else {
                if(!this.khachhang.hdkh_id || this.khachhang.hdkh_id==0)
                    rowHDKH.NGAYLAP_HD = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");  
                else
                    rowHDKH.NGAYLAP_HD = moment(rowHDKH.NGAYLAP_HD, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                rowHDKH.KHLON_ID = 1;
            }
            if(rowHDKH.NGAY_HK)
                rowHDKH.NGAY_HK =  moment(rowHDKH.NGAY_HK, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
            rowHDKH.KHACHHANG_ID = this.khachhang.khachhang_id;
            rowHDKH.TEN_KH = this.khachhang.tenkh;
            rowHDKH.DIACHI_KH = this.diachiKH.DIACHI;
            rowHDKH.SO_DT = this.khachhang.dienthoai;
            rowHDKH.SO_GT = this.khachhang.so_gt;
            if(rowHDKH.NGAYCAP){
                rowHDKH.NGAYCAP = moment(rowHDKH.NGAYCAP, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
            }  
            
            if(rowHDKH.NGAY_SN)
                rowHDKH.NGAY_SN = moment(rowHDKH.NGAY_SN, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
            if(this.hdkh_cha_id>0)  {
                rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id;
            }
            rowHDKH.DONVI_ID = this.$root.token.getDonViID();
            rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID();
            rowHDKH.LOAIHD_ID = LoaiHopDong.CHUYENDOI_LH;
            rowHDKH.NGUOI_CN = this.nguoi_cn;
            rowHDKH.NGAY_CN =  moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
            rowHDKH.MAY_CN = this.may_cn; 
            rowHDKH.KENHTN_ID = this.iKenhTN;
            rowHDKH.MA_DUAN = this.maduan;
            if (this.filterbox.ctv.ischecked && this.filterbox.ctv.value) {
                rowHDKH.CTV_ID = this.filterbox.ctv.ma_ctv; //ctv_id;
            }
            if (this.filterbox.nguoigt.ischecked && this.filterbox.nguoigt.value) {
                rowHDKH.NHANVIENGT_ID = this.filterbox.nguoigt.nguoigt_id; //nguoigt_id;
            }
            this.dsHDKH.push(Object.assign({}, rowHDKH));
            console.log('TaoDuLieu_HDKH DONE ', this.dsHDKH );
        },
        async TaoDuLieu_HDTT(themmoi) {
            console.log('TaoDuLieu_HDTT');
            this.dsHDTT = [];
            var rowHDTT = {};
            await api.post_lay_ds_dbtt_theo_ttid(this.axios, {"vphanvung_id": this.$root.token.getPhanVungID(),"in_thanhtoan_id": this.thanhtoan.thanhtoan_id}).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    rowHDTT = response.data.data[0]; 
                }
            }); 
            rowHDTT = this.UpperCasePropertyList([rowHDTT])[0];
            if(themmoi == true) {
               this.hdtt_id = await this.GetKey("HD_THANHTOAN");
               this.hdtt_id = this.hdtt_id*1;
            }
            rowHDTT.HDTT_ID = this.hdtt_id;
            rowHDTT.HDKH_ID = this.khachhang.hdkh_id;
            rowHDTT.NGUOI_CN = this.nguoi_cn;
            rowHDTT.NGAY_CN =  moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
            rowHDTT.MAY_CN = this.may_cn;
            this.dsHDTT.push(Object.assign({}, rowHDTT));
            console.log('TaoDuLieu_HDTT DONE', this.dsHDTT);
        },
        async TaoDuLieu_HDTB(themmoi) {
            console.log('TaoDuLieu_HDTB');
            this.dsHDTB = [];
            var rowHDTB = {};
            if(themmoi == true) {
               this.hdtb_id = await this.GetKey("HD_THUEBAO");
               this.hdtb_id = this.hdtb_id*1;
            }
            rowHDTB.HDTB_ID = this.hdtb_id;
            rowHDTB.HDKH_ID = this.khachhang.hdkh_id;
            rowHDTB.HDTT_ID = this.hdtt_id;
            if (this.thuebao.thuebao_id > 0)
                rowHDTB.THUEBAO_ID = this.thuebao.thuebao_id;
            rowHDTB.MA_TB = this.ma_tb;
            rowHDTB.TEN_TB = this.thuebao.tentb ? this.thuebao.tentb.trim() : null;
            rowHDTB.DIACHI_TB = this.diachiTB.DIACHI;
            rowHDTB.DIACHI_LD = this.diachiLD.DIACHI;
            rowHDTB.GHICHU = this.thuebao.ghichu;
            rowHDTB.KIEULD_ID = this.listbox.kieu_ld.value;
            rowHDTB.LOAITB_ID = this.listbox.loaihinh_tb_moi.value;
            rowHDTB.DOITUONG_ID = this.thongso_ban.doituong_id;
            rowHDTB.DONVI_ID = this.listbox.donvi_ql.value;
            rowHDTB.TTHD_ID = TrangThaiHD.MOI;
            rowHDTB.DICHVUVT_ID = this.listbox.dichvu_vt_moi.value;
            rowHDTB.QUYTRINH_ID = this.quytrinh_id;
            rowHDTB.NGUOI_CN = this.nguoi_cn;
            rowHDTB.NGAY_CN =  moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
            rowHDTB.MAY_CN = this.may_cn;
            var status = "0";
            rowHDTB.STATUS = status;
            if(this.listbox.loaihinh_tb_moi.value * 1 == LoaiHinhTB.IMS_SIPTRUNKING && this.listbox.dichvu_vt_moi.value*1 == DichVuVienThong.IMS)
            {
                if(this.listbox.trunk.checkbox_value)
                {
                    if(this.listbox.trunk.value != 0)
                    {
                        var tmp = this.listbox.trunk.list.filter(x => x.trunkid == this.listbox.trunk.value)
                        var loai = tmp && tmp.length > 0 ? tmp[0]["loai"].toString() : '';
                        if(loai == "1")
                            rowHDTB.HDTB_CHA_ID = this.listbox.trunk.value * 1;
                        if(loai == "2")
                            rowHDTB.THUEBAO_CHA_ID = this.listbox.trunk.value * 1;
                    }
                }                
            }
            rowHDTB.MUCUOCTB_ID = this.listbox.muccuoc_tb.value;
            var s = "-1";
            await api.post_tt_muccuoc_tb(this.axios, {
            "param": this.listbox.muccuoc_tb.value,
            "type": 1
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    s = response.data.data; 
                }
            });
            if (s != "-1")
                rowHDTB.MUCUOC_TB = s *1;

            this.dsHDTB.push(Object.assign({}, rowHDTB));
            console.log('TaoDuLieu_HDTB DONE', this.dsHDTB);
        },
        async LoadDL_KV(_khuvuc_id) {
            console.log('LoadDL_KV');
            this.dsHDTB_KV = [];
            var dr = {};
            dr.HDTB_ID = this.hdtb_id;
            dr.KHUVUC_ID = _khuvuc_id;
            dr.LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN;
            dr.NGUOI_CN = this.nguoi_cn;
            dr.NGAY_CN =  moment(new Date()).format("DD/MM/YYYY");
            dr.MAY_CN = this.may_cn;
            this.dsHDTB_KV.push(dr);
            console.log('LoadDL_KV DONE', this.dsHDTB_KV);
        },
        async TaoDuLieu_THONGTIN_TT(themmoi) {
            this.dsTTTT = [];
            //code C# đang bị comment lại
        },
        async TaoDuLieu_CT_TIENHD(themmoi) {
            console.log('TaoDuLieu_CT_TIENHD: ' + themmoi);
            this.dsCTTHD = [];
            var rowCTTHD = {};
            //Inset, 4 loại tiền vào dabase
            //1: Tiền khoản mục (theo loại hợp đồng: đặt mới, chuyển quyền, chuyển dịch)
            //2: Tiền cài đặt (Áp dụng cho dịch vụ INTERNET,
            //3: tien dịch vụ
            //4: tiền mua thiết bị
            var soluong = 1;
            console.log('this.dsCTM_TBI', this.dsCTM_TBI);
            for (var i = 0; i < 3; i++) {
                //tiền lắp mới =1 + khuyến mại lắp mới
                if (i == 0) {
                    rowCTTHD = {};
                    rowCTTHD.HDTB_ID = this.hdtb_id;
                    if (themmoi == true) { 
                        rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                    }
                    // rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                    console.log("CT_TIENHD_ID: " + rowCTTHD.CT_TIENHD_ID);
                    var tien = 0; //Tiền LĐ chưa vat
                    var vat = 0; //Vat
                    rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_TDLOAIHINHTB;
                    var tienLd = this.dtienld;
                    vat = Math.round(tienLd / 11, 0);
                    tien = tienLd - vat;
                    rowCTTHD.TIEN = tien;
                    rowCTTHD.VAT = vat;
                    // end hoàng
                    var vat_tt = 0;
                    rowCTTHD.LOAI_ID = 1;
                    rowCTTHD.ID = KHOANMUC_TT.KMTT_TDLOAIHINHTB;
                    this.dsCTTHD.push(Object.assign({}, rowCTTHD));
                }
                if (i == 1) {
                    //Tiền mua thiết bị =5 + tiền khuyến mại thiết bị
                    for (var j = 0; j < this.dsCTM_TBI.length; j++) {
                        rowCTTHD = {};
                        rowCTTHD.HDTB_ID = this.hdtb_id*1;
                        //rowCTTHD.CT_TIENHD_ID = this.dsCTM_TBI[j]["soluong"]
                        if (themmoi == true) {
                            rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                        }
                        // rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                        rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_MUATHIETBI;
                        var tien = 0;
                        var vat = 0;
                        
                        soluong = this.dsCTM_TBI[j]["soluong"] * 1;
                        tien = this.dsCTM_TBI[j]["tien"] * soluong;
                        vat = this.dsCTM_TBI[j]["vat"] * soluong;
                        rowCTTHD.TIEN = tien;
                        rowCTTHD.VAT = vat;
                        rowCTTHD.ID = this.dsCTM_TBI[j]["loaitbi_id"] * 1;
                        var tien_tt = 0;
                        tien_tt = tien;
                        rowCTTHD.TIEN = tien_tt;
                        var vat_tt = 0;
                        rowCTTHD.LOAI_ID = 3;
                        this.dsCTTHD.push(Object.assign({}, rowCTTHD));
                    }
                    // for (var k = 0; k < this.dsCTM_TBI.length; k++) {
                    //     rowCTTHD = {};
                    //     //rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow();
                    //     rowCTTHD.HDTB_ID = this.hdtb_id;
                    //     // if (themmoi == true) {
                    //     //     rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                    //     // }
                    //     rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                    //     rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_TRATRUOC_THIETBI;
                    //     var tien = 0;
                    //     var vat = 0; 
                    //     soluong = this.dsCTM_TBI[k]["soluong"] * 1;
                    //     tien = this.dsCTM_TBI[k]["tien"] * soluong;
                    //     vat = this.dsCTM_TBI[k]["vat"] * soluong;
                    //     rowCTTHD.TIEN = tien;
                    //     rowCTTHD.VAT = vat;
                    //     rowCTTHD.ID = this.dsCTM_TBI[k]["loaitbi_id"] * 1;
                    //     var tien_tt = 0;
                    //     tien_tt = tien;
                    //     rowCTTHD.TIEN = tien_tt;
                    //     var vat_tt = 0;
                    //     rowCTTHD.LOAI_ID = 3;
                    //     this.dsCTTHD.push(Object.assign({}, rowCTTHD));
                    // }
                }
                //nhapt thêm gvgt 18082017
                if (i == 2) {
                    rowCTTHD = {};
                    rowCTTHD.HDTB_ID = this.hdtb_id;
                    if (themmoi == true) {
                        rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                    }
                    // rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
                    var tien = 0; //Tiền LĐ chưa vat
                    var vat = 0; //Vat
                    rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU;
                    var tienDV = this.dichvu.dtien_dv * 1 + this.dichvu.dvat_dv * 1;
                    vat = Math.round(tienDV / 11, 0);
                    tien = tienDV - vat;
                    rowCTTHD.TIEN = tien;
                    rowCTTHD.VAT = vat;
                    // end hoàng
                    rowCTTHD.LOAI_ID = 1;
                    rowCTTHD.ID = KHOANMUC_TT.KMTT_DICHVU;
                    this.dsCTTHD.push(Object.assign({}, rowCTTHD));
                }
            }
            console.log('TaoDuLieu_CT_TIENHD DONE', this.dsCTTHD);
        },
        async TaoDuLieu_DiaChi(themmoi, kieu) {
            console.log('TaoDuLieu_DiaChi');
            this.dsDiaChi = [];
            this.dsDiaChiHDKH = [];
            this.dsDiaChiHDTT = [];
            this.dsDiaChiHDTB = [];
            
            if (kieu == 1 || kieu == 4) {
                var rowDiaChiKH = {}; 
                if (themmoi){
                    this.diachikh_id = (await this.GetKey("DIACHI"))*1; 
                } 
                
                   
                rowDiaChiKH.DIACHI_ID = this.diachikh_id;
                if (this.diachiKH.TINH_ID && this.diachiKH.TINH_ID != 0)
                    rowDiaChiKH.TINH_ID = this.diachiKH.TINH_ID;
                if (this.diachiKH.QUAN_ID && this.diachiKH.QUAN_ID != 0)
                    rowDiaChiKH.QUAN_ID = this.diachiKH.QUAN_ID;
                if (this.diachiKH.PHUONG_ID && this.diachiKH.PHUONG_ID != 0)
                    rowDiaChiKH.PHUONG_ID = this.diachiKH.PHUONG_ID;
                if (this.diachiKH.PHO_ID && this.diachiKH.PHO_ID != 0)
                    rowDiaChiKH.PHO_ID = this.diachiKH.PHO_ID;
                 
                rowDiaChiKH.SONHA = this.diachiKH.SONHA ? this.diachiKH.SONHA.trim() : '';
                rowDiaChiKH.DIACHI = this.diachiKH.DIACHI ? this.diachiKH.DIACHI.trim() : '';
                
                this.dsDiaChi.push(rowDiaChiKH);
                if (themmoi == true){
                    var rowDiaChiHDKH = {};
                    rowDiaChiHDKH.DIACHI_ID = this.diachikh_id;
                    rowDiaChiHDKH.HDKH_ID = this.khachhang.hdkh_id; 
                    this.dsDiaChiHDKH.push(rowDiaChiHDKH); 
                } 
            }
            if (kieu == 2 || kieu == 4) {
                var rowDiaChiTT = {}; 
                if (themmoi){
                    this.diachitt_id = (await this.GetKey("DIACHI"))*1; 
                }
 
                //DiachiTT 
                rowDiaChiTT.DIACHI_ID = this.diachitt_id;
                if(!this.diachiTT) this.diachiTT = this.diachiKH;
                rowDiaChiTT.TINH_ID = this.diachiTT.TINH_ID;
                rowDiaChiTT.QUAN_ID = this.diachiTT.QUAN_ID;
                rowDiaChiTT.PHUONG_ID = this.diachiTT.PHUONG_ID;
                if (this.diachiTT.PHO_ID && this.diachiTT.PHO_ID != 0)
                    rowDiaChiTT.PHO_ID = this.diachiTT.PHO_ID;
                rowDiaChiTT.SONHA = this.diachiTT.SONHA ? this.diachiTT.SONHA.trim() : '';
                rowDiaChiTT.DIACHI = " ";
                this.dsDiaChi.push(rowDiaChiTT);
                //if(!this.diachiBC) this.diachiBC = this.diachiKH;
                if (themmoi){
                    var rowDiaChiHDTT = {};
                    rowDiaChiHDTT.DIACHI_ID = this.diachitt_id; 
                    rowDiaChiHDTT.HDTT_ID = this.hdtt_id;
                    this.dsDiaChiHDTT.push(rowDiaChiHDTT);
                } 
            }
            if (kieu == 3 || kieu == 4) {
                //Create by Hiếu  - 14/7/2010 - Địa chỉ lắp đặt & địa chỉ thuê bao
                var rowDiaChiTB = {};
                var rowDiachiLD = {}; 
                if (themmoi)
                {
                    this.diachitb_id = (await this.GetKey("DIACHI"))*1;
                    this.diachild_id = (await this.GetKey("DIACHI"))*1;
                }
                //Địa chỉ lắp đặt --> dataset dsdiachi
                rowDiachiLD.DIACHI_ID = this.diachild_id;
                rowDiachiLD.TINH_ID = this.diachiLD.TINH_ID;
                rowDiachiLD.QUAN_ID = this.diachiLD.QUAN_ID;
                rowDiachiLD.PHUONG_ID = this.diachiLD.PHUONG_ID;
                // if (this.diachiLD.PHO_ID && this.diachiLD.PHO_ID != 0)
                //     rowDiachiLD.PHO_ID = this.diachiLD.PHO_ID;
                // if (this.diachiLD.AP_ID && this.diachiLD.AP_ID != 0)
                //     rowDiachiLD.AP_ID = this.diachiLD.AP_ID;
                // if (this.diachiLD.KHU_ID && this.diachiLD.KHU_ID != 0)
                //     rowDiachiLD.KHU_ID = this.diachiLD.KHU_ID;
                // if (this.diachiLD.DACDIEM_ID && this.diachiLD.DACDIEM_ID != 0)
                //     rowDiachiLD.DACDIEM_ID = this.diachiLD.DACDIEM_ID;
                
                rowDiachiLD.SONHA = this.diachiLD.SONHA ? this.diachiLD.SONHA.trim() : '';
                rowDiachiLD.DIACHI = this.diachiLD.DIACHI ? this.diachiLD.DIACHI.trim() : '';
                
                // Kieun v
                // rowDiachiLD.BLOCK = this.diachiLD.BLOCK ? this.diachiLD.BLOCK.trim() : '';
                // rowDiachiLD.TANG = this.diachiLD.TANG ? this.diachiLD.TANG.trim() : '';
                // rowDiachiLD.PHONG = this.diachiLD.PHONG ? this.diachiLD.PHONG.trim() : '';
                // rowDiachiLD.MOTA = this.diachiLD.MOTA ? this.diachiLD.MOTA.trim() : ''; 
                this.dsDiaChi.push(rowDiachiLD);
                //Địa chỉ thuê bao --> dataset dsdiachi
                rowDiaChiTB.DIACHI_ID = this.diachitb_id;
                rowDiaChiTB.TINH_ID = this.diachiTB.TINH_ID;
                rowDiaChiTB.QUAN_ID = this.diachiTB.QUAN_ID;
                rowDiaChiTB.PHUONG_ID = this.diachiTB.PHUONG_ID;
                // if (this.diachiTB.PHO_ID && this.diachiTB.PHO_ID != 0)
                //     rowDiaChiTB.PHO_ID = this.diachiTB.PHO_ID;
                // if (this.diachiTB.AP_ID && this.diachiTB.AP_ID != 0)
                //     rowDiaChiTB.AP_ID = this.diachiTB.AP_ID;
                // if (this.diachiTB.KHU_ID && this.diachiTB.KHU_ID != 0)
                //     rowDiaChiTB.KHU_ID = this.diachiTB.KHU_ID;
                // if (this.diachiTB.DACDIEM_ID && this.diachiTB.DACDIEM_ID != 0)
                //     rowDiaChiTB.DACDIEM_ID = this.diachiTB.DACDIEM_ID;
                
                rowDiaChiTB.SONHA = this.diachiTB.SONHA ? this.diachiTB.SONHA.trim() : '';
                rowDiaChiTB.DIACHI = this.diachiTB.DIACHI ? this.diachiTB.DIACHI.trim() : '';
                // Kieu nv
                // rowDiaChiTB.BLOCK = this.diachiTB.BLOCK ? this.diachiTB.BLOCK.trim() : '';
                // rowDiaChiTB.TANG = this.diachiTB.TANG ? this.diachiTB.TANG.trim() : '';
                // rowDiaChiTB.PHONG = this.diachiTB.PHONG ? this.diachiTB.PHONG.trim() : '';
                // rowDiaChiTB.MOTA = this.diachiTB.MOTA ? this.diachiTB.MOTA.trim() : ''; 
                
                this.dsDiaChi.push(rowDiaChiTB);
                if (themmoi)
                {
                    var rowDiaChiHDTB = {};
                    rowDiaChiHDTB.DIACHI_ID = this.diachitb_id;
                    rowDiaChiHDTB.HDTB_ID = this.hdtb_id;
                    rowDiaChiHDTB.DIACHILD_ID = this.diachild_id;
                    rowDiaChiHDTB.KINHDO = this.diachiTB.Lng;
                    rowDiaChiHDTB.VIDO = this.diachiTB.Lat;
                    rowDiaChiHDTB.KINHDO_LD = this.diachiLD.Lng;
                    rowDiaChiHDTB.VIDO_LD = this.diachiLD.Lat;
                    this.dsDiaChiHDTB.push(rowDiaChiHDTB);
                }
            }
            console.log('TaoDuLieu_DiaChi DONE', this.dsDiaChi);
        },
        async TaoDuLieu_HDTBDV(themmoi) {
            console.log('TaoDuLieu_HDTBDV');
            this.dsHDTBDV = [];
            var dtTemp = [];
            var row = {};
            if(themmoi == true)
            {
                await api.lay_ds_dbtb_dv(this.axios, {
                    "thuebao_id": this.thuebao.thuebao_id
                }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        dtTemp = response.data.data; 
                    }
                });
            }
            else
            {
                await api.lay_ds_hdtb_dv(this.axios, {
                    "hdtb_id": this.hdtb_id
                }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        dtTemp = response.data.data; 
                    }
                });
            }
            console.log(dtTemp);
            for (var i = 0; i < dtTemp.length; i++)
            {
                if(dtTemp[i]["loaidv_id"] != LOAI_DV.DAI_BR && dtTemp[i]["loaidv_id"] != LOAI_DV.TRAM_CAP_PORT)
                {
                    if(dtTemp[i]["loaidv_id"] == LOAI_DV.TRAM_VT)
                    {
                        row.HDTB_ID = this.hdtb_id;
                        row.DONVI_ID = this.listbox.tram_tc.value;
                        row.LOAIDV_ID = dtTemp[i]["loaidv_id"];
                        row.KIEUDV_ID = 2;
                    }
                    else
                    {
                        row.HDTB_ID = this.hdtb_id;
                        row.DONVI_ID = dtTemp[i]["donvi_id"];
                        row.LOAIDV_ID = dtTemp[i]["loaidv_id"];
                        row.KIEUDV_ID = 2;
                    }
                    this.dsHDTBDV.push(Object.assign({}, row));
                }
            }
            // không cần do quytrinh_id, ... lúc insert đã kiểm tra dưới db
            console.log('TaoDuLieu_HDTBDV DONE', this.dsHDTBDV);
        },
        async TaoDuLieu_HDTB_CON(themmoi) {
            console.log('TaoDuLieu_HDTB_CON');
            this.dsHDTB_IMS = [];
            this.dsHDTB_CD = [];
            var rowHDTB_IMS = {};
            var rowHDTB_CD = {};
            if (this.listbox.dichvu_vt_moi.value * 1 == DichVuVienThong.IMS) {
                rowHDTB_IMS.HDTB_ID = this.hdtb_id;
                rowHDTB_IMS.THIETBI_ID = this.listbox.thietbi_ims.value;
                if (this.vchuquan_id != 0)
                    rowHDTB_IMS.CHUQUAN_ID = this.vchuquan_id;
                else
                    rowHDTB_IMS.CHUQUAN_ID = null;
                if (this.vchuquanld_id != 0)
                    rowHDTB_IMS.CHUQUANLD_ID = this.vchuquanld_id;
                else
                    rowHDTB_IMS.CHUQUANLD_ID = null;
                // hoangpkn : 26/08/2016
                var kieu = this.kieu_ld_kieu;
                if ([1, 2, 3, 4].includes(kieu * 1)) {
                    if (!this.txtMaDoiCap_ADSL || this.txtMaDoiCap_ADSL.length == 0) {
                        this.$toast.warning("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập");
                        return false;
                    }
                    this.thuebaocd.madoicap = this.txtMaDoiCap_ADSL*1;
                    rowHDTB_IMS.MADOICAP = this.thuebaocd.madoicap * 1;
                } 
                else //TH còn lại sinh mã đôi cáp
                {
                    if (themmoi == true) {
                        rowHDTB_IMS.MADOICAP = await this.GetKey("MADOICAP"); 
                    } else
                        rowHDTB_IMS.MADOICAP = this.thuebaocd.madoicap * 1;
                }
                // end hoang
                if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.IMS_SIPTRUNKING)
                    rowHDTB_IMS.SL_CUOCGOI = this.txtSoLuong * 1;
                else
                    rowHDTB_IMS.SL_CUOCGOI = null;
                rowHDTB_IMS.MATB_TN = this.thuebaocd.matn;
                rowHDTB_IMS.TRANGBI_ID = this.listbox.trangbi.valueims;
                var dauso_ims = "";
                await api.lay_dauso_ims(this.axios,{
                    "p_param": this.$root.token.getMaTinh(),
                    "p_param1":  this.$root.token.getMaTinh(),
                    "p_type": 1
                }).then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        dauso_ims = response.data.data[0].dauso_ims; 
                    }
                });
                rowHDTB_IMS.USERNAME = "84" + dauso_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                var pass_ims = "";
                if (themmoi == true) {
                    pass_ims = "";
                    if(this.state.tusinh_pass_ims == 100)
                    {
                        if(this.state.matkhau_md_ims != "")
                            pass_ims = this.state.matkhau_md_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                        else{
                            if(this.state.tusinh_pass_ims == 0)    
                                pass_ims = "84" + dauso_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                            else
                                pass_ims = this.GetRandomText(11);
                        }
                    }
                    else if(this.state.tusinh_pass_ims == 50)
                    {
                        await api.sinh_pass_random(this.axios, {params: {"kieu": 1}}).then(
                            (response) => {
                                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                                {
                                    pass_ims = response.data.data;
                                }
                            }
                        );
                    }
                    else if(this.state.tusinh_pass_ims == 30)
                    {
                        pass_ims = this.SinhPassIMS();
                    }
                    else
                    {
                        if(this.state.matkhau_md_ims != "")
                            pass_ims = this.state.matkhau_md_ims;
                        else
                        {
                            if(this.state.tusinh_pass_ims == 0)    
                                pass_ims = "84" + dauso_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                            else
                                pass_ims = this.GetRandomText(11);
                        }
                    }
                }
                if (this.listbox.loaihinh_tb_moi.value * 1 == LoaiHinhTB.IMS_SIPTRUNKING) {
                    rowHDTB_IMS.SL_CUOCGOI = this.txtSoLuong * 1;
                    rowHDTB_IMS.CTDV_ID = this.listbox.ht_chitietdv_ims.value * 1;
                    if (rowHDTB_IMS.CTDV_ID == 2)
                        rowHDTB_IMS.IP = this.ip_ims;
                    if (this.listbox.trunk.checkbox_value == true)
                        rowHDTB_IMS.TRUNKID = this.listbox.trunk.value;
                    rowHDTB_IMS.HTTC_ID = this.listbox.hinhthuc_tc.value * 1;
                }
                rowHDTB_IMS.PASSWORD = pass_ims;
                this.dsHDTB_IMS.push(Object.assign({}, rowHDTB_IMS));
                //TODO: UPDATE
                // if (themmoi == false)
                // {
                //     dsHDTB_IMS.AcceptChanges();
                //     dsHDTB_IMS.HDTB_IMS.Rows[0].SetModified();
                // }
            } else {
                rowHDTB_CD.HDTB_ID = this.hdtb_id;
                if (themmoi == true)
                    rowHDTB_CD.MADOICAP = await this.GetKey("MADOICAP");
                else
                    rowHDTB_CD.MADOICAP = this.thuebaocd.madoicap * 1;
                rowHDTB_CD.TRANGBI_ID = this.listbox.trangbi.valueims * 1;
                if (this.vchuquan_id)
                    rowHDTB_CD.CHUQUAN_ID = this.vchuquan_id;
                else
                    rowHDTB_CD.CHUQUAN_ID = null;
                if (this.vchuquanld_id != 0)
                    rowHDTB_CD.CHUQUANLD_ID = this.vchuquanld_id;
                else
                    rowHDTB_CD.CHUQUANLD_ID = null;
                this.dsHDTB_CD.push(Object.assign({}, rowHDTB_CD));
                //TODO: ....
                // if (themmoi == false)
                // {
                //     dsHDTB_CD.AcceptChanges();
                //     dsHDTB_CD.HDTB_CD.Rows[0].SetModified();
                // }
            }
            console.log('TaoDuLieu_HDTB_CON DONE', this.dsHDTB_CD);
        },
        async TaoDuLieu_DKDV() {
            this.dsDK_DVGT = [];
            if(!this.m_dsThueBao_DichVu)
              return;
            console.log('TaoDuLieu_DKDV');
            this.m_dsThueBao_DichVu = this.LowerCasePropertyList(this.m_dsThueBao_DichVu); 
            var kieu_dk_dvgt = 0;
            for (var dr of this.m_dsThueBao_DichVu) {
                if (dr["dangky"].toString() == "1")
                    kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
                else if (dr["huy"].toString() == "1")
                    kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
                else {
                    continue;
                }
                var rowHDTDDV = {};
                rowHDTDDV.HDTB_ID = this.hdtb_id;
                rowHDTDDV.NGAY_YC =  moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
                rowHDTDDV.DICHVUGT_ID = (dr["dichvugt_id"] ? dr["dichvugt_id"]*1 : dr["dichvugtid"]*1);
                rowHDTDDV.NOIDUNG = dr["noidung"];
                rowHDTDDV.KIEU_YC = kieu_dk_dvgt;
                rowHDTDDV.TTTH = 1;
                this.dsDK_DVGT.push(Object.assign({}, rowHDTDDV));
            }
            console.log('TaoDuLieu_DKDV DONE', this.dsDK_DVGT);
        },
        async KiemTraDL_KhachHang() {
           
          return true
        },
        async KiemTraDL_ThueBao() {
            if (this.khachhang.mahd == "" && this.khachhang.khachhang_id != 0)
            { 
                this.$toast.error("Hãy nhập mã hợp đồng !");
                this.$refs.txtMaHD.focus();
                return false;
            }
            if (this.listbox.muccuoc_tb.list.length <= 0)
            { 
                this.$toast.error("Hãy nhập mức cước thuê bao!");
                return false;
            }
            if (this.thuebao.tentb == "")
            { 
                this.$toast.error("Hãy nhập tên thuê bao!");
                this.$refs.txtTenTB.focus();
                return false;
            }
            // if (this.kt_khoso_cd && this.kt_khoso_cd == true)
            // {
            //     if (this.listbox.dichvu_vt_moi.value*1 == DichVuVienThong.CO_DINH
            //         && this.listbox.dichvu_vt.value*1 == DichVuVienThong.IMS)
            //     {
            //         // Chuyển đổi từ IMS -> Cố định. cần kiểm tra xem số IMS đấy có trong kho số cố định không ? Nếu ko --> ko cho làm
            //         var kt_cd = bangts.kiemtra_cdlh_ims_cd(Convert.ToInt32(cboLoaiHinhTBMoi.SelectedValue), txtMaTB.Text.Trim());
            //         if (kt_cd == false)
            //         {
            //             this.$toast.error("Số máy IMS không có trong kho số Cố định. Bạn không được thực hiện !"); //=> chuyển kiểm tra trong package
            //             return false;
            //         }
            //     }
            // }
            if (this.listbox.loaihinh_tb_moi.value*1 == LoaiHinhTB.IMS_SIPTRUNKING)
            {
                if (this.txtSoLuong.toString().trim() == "" || this.txtSoLuong.toString().trim() == "0")
                {
                    this.$toast.error("Bạn chưa nhập số lượng cuộc gọi cho thuê bao Sip trunking!");
                    this.$refs.txtSoLuong.focus();
                    return false;
                }
                if (this.CheckIsNumber(this.txtSoLuong.toString().trim()) == false)
                {
                    this.$toast.error("Số lượng cuộc gọi phải là kiểu số!");
                    this.$refs.txtSoLuong.focus();
                    return false;
                }
               
                if (this.listbox.ht_chitietdv_ims.value*1 == 0)
                {
                    this.$toast.error("Hãy chọn chi tiết DV");
                    //this.$refs.cboChiTietDVMoi.focus();
                    return false;
                }
                if (this.listbox.ht_chitietdv_ims.value*1 == this.ChiTietDV_IMS.internet)
                {
                    if (this.state.phainhap_ip_siptrunking && this.state.phainhap_ip_siptrunking == true && this.ip_ims.trim() == "")
                    {
                        this.$toast.error("Hãy nhập địa chỉ IP !");
                        this.$refs.txtIp_IMS.focus();
                        //txtIp_IMS.BackColor = Color.Azure;
                        return false;
                    }
                }
            }
            // hoangpkn : 31/10/2013
            // thêm kiểm tra gói trước khi lập phụ lục thay đổi LHTB
            // if (Convert.ToInt32(cboDichVuVT.SelectedValue) == DichVuVienThong.CO_DINH || Convert.ToInt32(cboDichVuVT.SelectedValue) == DichVuVienThong.IMS)
            // {
            //     string kq = "";
            //     int vtd_id = -1;
            //     int vmc_id = 0;
            //     kq = laphd.Kiemtra_goidadv_cd_ims(thuebao_id, Convert.ToInt32(cboLoaiHinhTBMoi.SelectedValue), vtd_id, vmc_id); //=> chuyển db check
            //     if (kq != "1")
            //     {
            //         Message_Box.ShowWarning(kq);
            //         return false;
            //     }
            // }
            var kieu =  await this.MAP_ID(this.listbox.kieu_ld.value*1);
            if ((kieu == 1 || kieu == 2) && this.thuebaocd.matn.trim().length == 0)
            { 
                this.$toast.error("Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!");
                this.$refs.txtMaTN_ADSL.focus();
                return false;
            }
            // end hoàng thêm
            //SonNMP: Không có khu vực không có tạo hợp đồng
            // if (dsHDTB_KV == null || dsHDTB_KV.HDTB_KV.Rows.Count == 0 || dsHDTB_KV.HDTB_KV.Rows[0]["khuvuc_id"].ToString() == "0")
            // {
            //     Message_Box.ShowWarning("Cần chọn khu vực");
            //     txt_KhuVuc.Focus();
            //     return false;
            // }
            return true;
        },
        async KIEMTRA_CDLH() {
            return true;
        },
        async MAP_ID(in_dk){
            var data = 0;
            await api.post_tt_kieu_ld(this.axios, {
              "param": in_dk,
              "type": 1
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                   data = response.data.data * 1; 
                }
            });
            return data;
        },
        // Popups
        tsbtnThongTinLH_Click() {
            this.$refs.popupThongTinLienHe.show(); 
        },
        ShowPopupCTV() {
            if (this.filterbox.ctv.ischecked) {
                this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien');
                this.popupComponentName = "popupCTV";
                this.Popup('popupComponents');
            }
        },
        ShowPopupNguoiGT() {
            if (this.filterbox.nguoigt.ischecked) {
                this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu');
                this.popupComponentName = "popupNGT";
                this.Popup('popupComponents');
            }
        },
        popupDVCongThem() {
            this.popupComponent = () => import('../../../contract/setup/InstallNewSubs/Popups/popupServiceAdds');
            this.popupComponentName = "popupServiceAdds";
            var listGVGT = [];
            if(this.m_dsThueBao_DichVu)
                listGVGT = this.m_dsThueBao_DichVu.filter(x=>x.dangky);
            this.popupComponentData = {_loaitb_id:this.listbox.loaihinh_tb_moi.value,_hdtb_id:this.hdtb_id,_linhvuc_id:0,_tocdo_id:this.tocdo_id,lvDvgt: listGVGT}
            this.Popup('popupComponents');
        },
        popupMuaTB() {
            this.popupComponent = () => import('../../../contract/setup/InstallNewSubs/Popups/popupBuyDevices');
            this.popupComponentName = "popupBuyDevices";
            this.popupComponentData = {_loaitb_id:this.listbox.loaihinh_tb.value }
            if(this.dsCTM_TBI && this.dsCTM_TBI.length>0) this.popupComponentData.ds = this.dsCTM_TBI;
            this.Popup('popupComponents');
        },
        popupMaTN() {
            var dulieu = {};
            //var kieu = this.listbox.kieu_ld.value;
            var kieu = this.kieu_ld_kieu;
            switch (kieu * 1) {
                case 2://Lắp mới trên đường có sẵn
                    if (this.khachhang.khachhang_id && this.khachhang.khachhang_id > 0) {
                        dulieu.khachhang_id = this.khachhang.khachhang_id;
                        dulieu.kieuld_id = this.listbox.kieu_ld.value;
                        dulieu.loaitb_id = this.listbox.loaihinh_tb.value;
                        dulieu.dichvuvt_id = this.listbox.dichvu_vt.value;
                        this.popupComponentData = dulieu;
                        this.popupComponent = () => import('../../../contract/setup/InstallNewSubs/Popups/popupDSTBCungDoiCap');
                        this.popupComponentName = "popupDSTBCungDoiCap";
                    } else {
                        this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !");
                        return;
                    }
                    break;
                case 1://Lắp kèm
                    if (this.khachhang.hdkh_id && this.khachhang.hdkh_id > 0) {
                        dulieu.hdkh_id = this.khachhang.hdkh_id;
                        dulieu.vkhachhang_id = this.khachhang.khachhang_id;
                        dulieu.vkieuld_id = this.listbox.kieu_ld.value;
                        // dulieu.loaitb_id = this.listbox.loaihinh_tb.value;
                        this.popupComponentData = dulieu;
                        this.popupComponent = () => import('../../../contract/setup/InstallNewSubs/Popups/popupDSTBLapKem');
                        this.popupComponentName = "popupDSTBLapKem";
                    } else {
                        this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !");
                        return;
                    }
                    break;
                case 3://Lắp kèm thuê bao quang đang chuyển đổi
                    dulieu.hdkh_id = this.khachhang.hdkh_id;
                    dulieu.vkhachhang_id = this.khachhang.khachhang_id;
                    dulieu.vkieuld_id = this.listbox.kieu_ld.value;
                    // dulieu.loaitb_id = this.listbox.loaihinh_tb.value;
                    this.popupComponentData = dulieu;
                    this.popupComponent = () => import('../../../contract/setup/InstallNewSubs/Popups/popupDSTBLapKem');
                    this.popupComponentName = "popupDSTBLapKem";
                    break;
                case 4:// Lắp trên đường có sẵn của khách hàng khác
                    dulieu.loaitb_id = this.listbox.loaihinh_tb.value;
                    dulieu.kieuld_id = this.listbox.kieu_ld.value;
                    this.popupComponentData = dulieu;
                    this.popupComponent = () => import('../../../contract/setup/InstallNewSubs/Popups/popupDSTBCungDoiCap_KH_Khac');
                    this.popupComponentName = "popupDSTBCungDoiCap_KH_Khac";
                    break;
            }
            this.Popup('popupComponents');
        },
        popupChonKhuVuc(){
            this.popupComponent = ()=>import('../../../contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao');
            this.popupComponentName = "popupChonKhuVuc";
            var data = {diachi:this.diachiLD, khuvuc_id: this.thuebao.khuvuc_id, hdtb_id: this.hdtb_id,thuebao_id: this.thuebao.thuebao_id, donvi_id: this.listbox.donvi_ql.value };
            // console.log('popupChonKhuVuc DATA', data);
            this.popupComponentData = data;
            //if(this.dsCTM_TBI && this.dsCTM_TBI.length>0) this.popupComponentData.ds = this.dsCTM_TBI;
            this.Popup('popupComponents');
        },
        // Thông tin phiếu yêu cầu
        showPopupSearchContract() {
            this.$refs.popupSearchContract.showModal(); 
        },
        acceptSearchContract(item) {
            this.khachhang.magd = item.ma_gd;
            if (this.khachhang.magd && this.khachhang.magd.trim() != "")
                this.MaGDChanged();
        },
        // Loại hình thuê bao cũ
        async cboDichVuVT_SelectedValueChanged(){             
            // console.log("Dich vu vien thong cu thay doi");
            if (this.listbox.dichvu_vt.value.toString() == DichVuVienThong.CO_DINH.toString())
                this.listbox.dichvu_vt_moi.value = DichVuVienThong.IMS;
            else
                this.listbox.dichvu_vt_moi.value = DichVuVienThong.CO_DINH;
            this.state.dichvuvt_id = this.listbox.dichvu_vt.value*1; 
            // console.log(this.state);  
            var temp_dichvu_tv = this.listbox.loaihinh_tb.list;
            this.listbox.loaihinh_tb.list = this.listbox.loaihinh_tb_moi.list;
            this.listbox.loaihinh_tb_moi.list = temp_dichvu_tv;
            this.listbox.loaihinh_tb.value = this.listbox.loaihinh_tb.list[0].loaitb_id * 1;
            this.listbox.loaihinh_tb_moi.value = this.listbox.loaihinh_tb_moi.list[0].loaitb_id * 1;          
        },
        // Kiểu lắp đặt thay đổi
        async cboKieuLD_SelectedValueChanged(){   
            // if(this.listbox.kieu_ld.list && this.listbox.kieu_ld.list.length > 0)
            // {
            //     if(this.listbox.kieu_ld.value*1 == 1) return;
            //     if(!this.kt_load)
            //     {
            //         await this.LayTienTheoKhoanMuc();
            //     }
            //     var loaitb_id = this.listbox.loaihinh_tb.value*1;
            //     console.log(11111111)
            //     console.log( this.listbox.loaihinh_tb_moi.value)
            //     var loaitb_id_moi = this.listbox.loaihinh_tb_moi.value*1;
                
            //     var dichvu_vt = this.listbox.dichvu_vt.value*1;
            //     if((dichvu_vt == DichVuVienThong.CO_DINH || dichvu_vt == DichVuVienThong.IMS) && this.tinh.tinhthicong_id != 0)
            //     {
            //         this.khachhang_id = this.khachhang.khachhang_tc_id;
            //         this.hdkh_id = this.khachhang.hdkh_id;
            //         this.$refs.thongSoCoDinhModal.showModal(this.tinh.tinhthicong_id,loaitb_id, loaitb_id_moi, 0, 0, LoaiHopDong.CHUYENDOI_LH)
            //     }              
            // }                    
        },
        async PopupThongSo(){
            console.log('PopupThongSo kaakak')
            if(this.listbox.kieu_ld.list && this.listbox.kieu_ld.list.length > 0)
            {
                if(this.listbox.kieu_ld.value*1 == 1) return;
                if(!this.kt_load)
                {
                    await this.LayTienTheoKhoanMuc();
                }
                var loaitb_id = this.listbox.loaihinh_tb.value*1;
                var loaitb_id_moi = this.listbox.loaihinh_tb_moi.value*1;
                
                var dichvu_vt = this.listbox.dichvu_vt.value*1;
                if((dichvu_vt == DichVuVienThong.CO_DINH || dichvu_vt == DichVuVienThong.IMS) && this.tinh.tinhthicong_id != 0)
                {
                    this.khachhang_id = this.khachhang.khachhang_tc_id;
                    this.hdkh_id = this.khachhang.hdkh_id;
                    this.$refs.thongSoCoDinhModal.showModal(this.tinh.tinhthicong_id,loaitb_id, loaitb_id_moi, 0, 0, LoaiHopDong.CHUYENDOI_LH)
                }              
            }
        },
        acceptThongSoCoDinh(data){
            console.log('acceptThongSoCoDinh', data)
            Object.assign(this.thongso_ban, data.thongso_ban);
            Object.assign(this.thongso_tc, data.thongso_tc);
            if(this.thongso_tc.ma_tn){
                this.thuebaocd.matn=this.thongso_tc.ma_tn
            }
            //  console.log('thongso_ban', this.thongso_ban);
            //  console.log('thongso_tc', this.thongso_tc);
        },
        ShowSearchAccount() {
          this.popupComponent = ()=>import('@/modules/search/subscriber/SearchAccount');
          this.popupComponentName = "SearchAccount";
          try {
            this.popupComponent.data.isPopup = true;
          } catch(ex) {}
          this.Popup('popupComponents');
        },
        // Loại hình thuê bao mới
        async LoaiHinhTB_moi_Changed() {
            this.Loading = true;
            var loaihinhtbmoi_id = this.listbox.loaihinh_tb_moi.value * 1;
            if (this.kt_load == false) {
                var loaihinhtbcu_id = this.listbox.loaihinh_tb.value * 1;
                var ds_kieuld_chuyendoi = api.get_ds_kieuld_chuyendoi(this.axios, {
                    "vloaihd_id": LoaiHopDong.CHUYENDOI_LH,
                    "vkieu": 2,
                    "vloaitb_id": loaihinhtbmoi_id
                }).then(
                    (response) => {
                        this.loading(false);
                        this.listbox.kieu_ld.list = response.data.data.filter(x=>x.kieuld_id>0);
                        
                        if(this.listbox.kieu_ld.list && this.listbox.kieu_ld.list.length > 0){
                          this.listbox.kieu_ld.value = this.listbox.kieu_ld.list[0].kieuld_id*1;  
                        }
                    });
                
                await this.LoadMucCuocTB();
                if (loaihinhtbmoi_id == LoaiHinhTB.IMS_SIPTRUNKING) {
                    this.controls.txtSoLuong.enabled = true;
                    await this.HT_DS_TRUNK_IMS();
                }
            }
            await this.LayTienTheoKhoanMuc();
            await this.LAY_DS_DVGT_MACDINH(this.listbox.loaihinh_tb_moi.value * 1);
            this.Loading = false;
        },
        // Thông tin thuê bao
        getDiaChiTB: function (data) {
            this.getDiaChi(data, 'diachiTB');
            if (!this.diachiLD.DIACHI || (this.diachiLD.DIACHI == this.diachiKH.DIACHI && this.diachiLD.DIACHI != this.diachiTB.DIACHI))
                this.diachiLD = Object.assign({}, this.diachiTB);
        },
        getDiaChiLD: function (data) { 
            this.getDiaChi(data, 'diachiLD');
        },
        // Danh sách thuê bao
        dgrThueBao_SelectionChanged(idx, item) {
            console.log('dgrThueBao_SelectionChanged', item);
            try {
                if (idx >= 0 && this.dshd && this.dshd.length > 0) {
                    this.hdtb_id = item.hdtb_id
                    var ds = this.dshd.filter(x=>x.hdtb_id*1 == this.hdtb_id*1); 
                    this.HienThiTTHopDongTB(ds); 
                }
                else{
                    this.khachhang.magd = "";
                }
            } catch (ex) {
                this.$toast.error('' + ex);
            }finally{
              this.loading(false);
            }
        },
        // Chung
        Popup(modalId) {
            this.$bvModal.show(modalId);
        },
        // Popup thoát
        ctvClosed(){
            this.ClosePopup('popupComponents');
        },
        ngtClosed(){
            this.ClosePopup('popupComponents');
        },
        // Popup Chấp nhận -> thoát
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
        },
        async popupClosed(val) {
         console.log('popupClosed', val);
          this.loading(true);
          try {
            switch(this.popupComponentName) {
                case "SearchAccount":
                    console.log('popupClosed SearchAccount', val);
                    if(val.ma_kh) this.khachhang.makh = val.ma_kh;
                    if(val.ma_tt) this.thanhtoan.matt = val.ma_tt;
                    if(val.ma_tb) this.thuebao.codinh.somay = val.ma_tb;
                    break;
                case "popupServiceAdds":
                    this.m_dsThueBao_DichVu = this.LowerCasePropertyList(val);
                    this.HT_DichVuGT();
                    break;
                case "popupBuyDevices":
                    this.dsCTM_TBI = val;
                    this.HT_ThietBi();
                    break;
                case "popupDSTBLapKem":
                    this.thuebaocd.matn = val;
                    break;
                case "popupDSTBCungDoiCap":
                    this.thuebaocd.matn = val;
                    break;
                case "popupDSTBCungDoiCap_KH_Khac":
                    this.thuebaocd.matn = val;
                    break;
                case "popupChonKhuVuc":
                    if(this.diachiLD.QUAN_ID != 0 && this.diachiLD.PHUONG_ID != 0)
                    {
                        this.thuebao.khuvuc = val.ma_kv;
                        this.dsHDTB_KV = val.hdtb_kv_data;
                        this.thuebao.khuvuc_id = val.khuvuc_id;
                    }
                    else
                    {
                        this.$toast.warning("Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!");
                        this.thuebao.khuvuc = '';
                        this.dsHDTB_KV = [];
                        this.thuebao.khuvuc_id = 0;
                    }
                    break;
                case "popupCTV":
                    this.filterbox.ctv.value = val.ten_ht;
                    this.filterbox.ctv.ma_ctv = val.nhanvien_id;
                    break;
                case "popupNGT":
                    this.filterbox.nguoigt.value = val.ten_ht;
                    this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id;
                    break;
            }
          } catch(ex) { this.$toast.error(''+ex); }
          finally {
            this.ClosePopup('popupComponents');
            this.loading(false);
          }
        },
        async HT_DichVuGT() { 
            this.lvDvgt.list = [];
            if(!this.m_dsThueBao_DichVu) return;
            this.m_dsThueBao_DichVu = this.LowerCasePropertyList(this.m_dsThueBao_DichVu);
            var kt = true;
            this.dvgt.tien_dv = 0;
            this.dvgt.vat_dv = 0; 
           
            var str = "";
            for (var i = 0; i < this.m_dsThueBao_DichVu.length; i++) {
                if (this.m_dsThueBao_DichVu[i]["huy"] == "1" || this.m_dsThueBao_DichVu[i]["dangky"] == "1") {
                    str = this.m_dsThueBao_DichVu[i]["ten_dvgt"];
                    if (this.m_dsThueBao_DichVu[i]["dangky"] == "1" && this.m_dsThueBao_DichVu[i]["huy"] == "0") {
                        kt = false;
                        if (this.m_dsThueBao_DichVu[i]["cuoc_ld"]) {
                            this.dvgt.tien_dv = this.dvgt.tien_dv + this.m_dsThueBao_DichVu[i]["cuoc_ld"]*1;
                        }
                        if (this.m_dsThueBao_DichVu[i]["vat_ld"]) {
                            this.dvgt.vat_dv = this.dvgt.vat_dv + this.m_dsThueBao_DichVu[i]["vat_ld"]*1;
                        }
                        str = str + " (ĐK)"; 
                    }
                    if (this.m_dsThueBao_DichVu[i]["huy"] == "1")
                        str = str + " (Hủy)";
                    this.lvDvgt.list.push(str);
                }
            }
            if (kt == true) {
                this.dvgt.tien_dv = 0;
                this.dvgt.vat_dv = 0;
            }
            // console.log('this.m_dsThueBao_DichVu', this.m_dsThueBao_DichVu);
            // console.log('HT_DichVuGT', this.lvDvgt.list);
            // console.log('this.dvgt.tien_dv', this.dvgt.tien_dv);
            // console.log('this.dvgt.vat_dv', this.dvgt.vat_dv);
            this.HienThiTienDichVu(this.dvgt.tien_dv, this.dvgt.vat_dv); 
        },
        HienThiTienDichVu(tien_dv,vat_dv) {
            this.dichvu.dtien_dv = tien_dv;
            this.dichvu.dvat_dv = vat_dv;
            this.LayTongTien_HDTB();
        },
        async LayTongTien_HDTB() {
            try {
                this.tongtienhd = this.dtienld+this.dichvu.dtien_dv+this.dichvu.dvat_dv+this.thietbi.dtientbi + this.thietbi.dvattbi
                 
            } catch (ex) {
                this.$toast.error("" + ex);
                throw ex;
            }
        },
        async HT_ThietBi() { 
            try {
                // this.dsTRAGOP_TBI_HDTB = [];
                var kt = true; //Kiểm tra xem biến 
                this.thietbi.tien_tbi = 0;
                this.thietbi.vat_tbi = 0;
                var soluong = 1;
                var str = '';
                this.lvMuaThietbi.list = [];
                console.log('this.dsCTM_TBI', this.dsCTM_TBI)
                for (var i = 0; i < this.dsCTM_TBI.length; i++) {
                    var apidata = {};
                    await api.get_loaitb_id(this.axios, {
                        "loaitbi_id": this.dsCTM_TBI[i]["loaitbi_id"]
                    }).then(
                        (response) => {
                            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                                apidata = response.data.data;
                            }
                        }
                    );
                    if (apidata[0].loai_tbi) str = apidata[0].loai_tbi;
                    this.lvMuaThietbi.list.push(str);
                    //Hiếu bổ sung thêm tiền mua thiết bị ngày 14/09/2010
                    //Hiếu bổ sung code tính tiền mua thiết bị
                    kt = false;
                    soluong = this.dsCTM_TBI[i]["soluong"];
                    if (this.dsCTM_TBI[i]["tien"]) {
                        var dtien_tbi_tmp = this.dsCTM_TBI[i]["tien"];
                        this.thietbi.tien_tbi = this.thietbi.tien_tbi + dtien_tbi_tmp * soluong;
                    }
                    if (this.dsCTM_TBI[i]["vat"]) {
                        var dvat_tbi_tmp = this.dsCTM_TBI[i]["vat"];
                        this.thietbi.vat_tbi = this.thietbi.vat_tbi + dvat_tbi_tmp * soluong;
                    }
                }
                if (kt) {
                    this.thietbi.tien_tbi = 0;
                    this.thietbi.vat_tbi = 0;
                }
                this.HienThiTienMuaThietBi();
            } catch (ex) {
                this.$toast.warning("" + ex);
                throw ex;
            }
        },
        async HienThiTienMuaThietBi() {
            //thietbi.dtientbi+thietbi.dvattbi
            this.thietbi.dtientbi = this.thietbi.tien_tbi;
            this.thietbi.dvattbi = this.thietbi.vat_tbi;
            this.LayTongTien_HDTB();
        },
        async LoadMucCuocTB() {
            this.Loading = true;
            var pdata = {
                "vapld_id": 0,
                "vdoituong_id": this.listbox.doituong.value?this.listbox.doituong.value:this.listbox.doituong.list[0].doituong_id,
                "vkhuld_id": 0,
                "vloaitb_id": this.listbox.loaihinh_tb_moi.value,
                "vmuccuoc_id": 0,
                "vngay": this.NgayYeuCau,
                "vphold_id": this.diachiLD.PHO_ID?this.diachiLD.PHO_ID:0,
                "vphuongld_id": this.diachiLD.PHUONG_ID?this.diachiLD.PHUONG_ID:0,
                "vtocdo_id": 0,
            };
            api.get_mucuoc_tb(this.axios, pdata).then((response) => {
                this.listbox.muccuoc_tb.list = [];
                this.listbox.muccuoc_tb.value = 0;
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.listbox.muccuoc_tb.list = response.data.data;
                    if(this.listbox.muccuoc_tb.list && this.listbox.muccuoc_tb.list.length>0) this.listbox.muccuoc_tb.value = this.listbox.muccuoc_tb.list[0].mucuoctb_id;
                }
            });
            this.Loading = false;
        },
        async HT_DS_TRUNK_IMS() {
            var pdata = {
                "khachhang_id": this.khachhang.khachhang_id,
                "hdkh_id": this.khachhang.hdkh_id
            }
            await api.get_ds_tb_chinh_ims(this.axios, pdata).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.listbox.trunk.list = response.data.data;
                    //TODO:
                    // console.log("post_ht_ds_tb_chinh_ims", response.data.data);
                } else {
                    this.$toast.error("Lỗi khi lấy thông tin đường trunk !");
                }
            });
        },
        async LayTienTheoKhoanMuc() {
            if (!this.listbox.loaihinh_tb_moi.value) return;
            if (!this.listbox.kieu_ld.value) return;
            //UR 2.1.4.068
            var pdata = {
                "apld_id": this.diachiLD.AP_ID,
                "dichvuvt_id": this.listbox.dichvu_vt_moi.value,
                "doituong_id": this.listbox.doituong.value,
                "kh_cd": 0,
                "khuld_id": this.diachiLD.KHU_ID,
                "kieuld_id": this.listbox.kieu_ld.value,
                "loaihd_id": LoaiHopDong.CHUYENDOI_LH,
                "loaitb_id": this.listbox.loaihinh_tb_moi.value,
                "muccuoc_id": 0,
                "ngay_yc": this.NgayYeuCau,
                "phold_id": this.diachiLD.PHO_ID,
                "phuongld_id": this.diachiLD.PHUONG_ID,
                "ltb_adsl_cu_id": 0,
                "ltb_adsl_id": 0,
                "ltb_adsl_moi_id": 0,
                "tocdo_cu_id": 0,
                "tocdo_id": 0,
                "tocdo_moi_id": 0,
                "trangbi_id": this.listbox.trangbi.valueims //BUG can xem lai cho nay khi khong dung cho co dinh
            }
            //post_lay_tien_khoanmuc_ld_v2
            await api.get_ds_tien_khoanmuc_ld(this.axios, pdata).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    var ds = response.data.data;
                    // console.log('lay_tien_khoanmuc_ld', ds);
                    if (ds.length > 0) {
                        this.HienThiTienLapDat(ds);
                    } else {
                        this.dtienld = 0;
                        this.dichvu.dtien_dv = 0;
                    }
                }
            }); 
        },
        async HienThiTienLapDat(ds) { 
            var kt_lm = true;
            if (ds[0]['tien'] != -1)
            {
                for (var i = 0; i < ds.length; i++) {
                    if (ds[i].khoanmuctt_id == KHOANMUC_TT.KMTT_TDLOAIHINHTB) {
                        kt_lm = false;
                        this.dtienld = ds[i].tien + ds[0].vat; 
                    }  
                    if (ds[i].khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
                        kt_lm = false;
                        this.dichvu.dtien_dv = ds[i].tien + ds[0].vat; 
                    }
                }
                if(kt_lm == true)
                {
                    this.dtienld = 0;
                    this.dichvu.dtien_dv = 0;
                }
                this.LayTongTien_HDTB();
            }
            else
            {
                this.dtienld = 0;
                this.dichvu.dtien_dv = 0;
                this.$toast.warning("Thông tin giá chưa được cập nhật");
            }
        },
        async LAY_DS_DVGT_MACDINH(loaitb_id) {  
            var hdtb_id = this.hdtb_id ? this.hdtb_id : 0
            this.m_dsThueBao_DichVu = await this.LAY_DS_THUEBAO_DICHVU(hdtb_id, 0, loaitb_id, 1);
            this.HT_DichVuGT(); 
        },
        async LAY_DS_THUEBAO_DICHVU(hdtb_id, thuebao_id, loaitb_id, kieu_id){
            // if(!this.hdtb_id || this.hdtb_id <= 0 || !loaitb_id || loaitb_id <=0) {
            //     return [];
            // }
                
            var ds = [];
            await api.get_ds_thuebao_dichvu(this.axios, { 
                "phanvung_id": this.$root.token.getPhanVungID(),
                "tinhthicong_id": this.tinh.tinhthicong_id,
                "hdtb_id": hdtb_id,
                "thuebao_id": 0,
                "loaitb_id": loaitb_id,
                "kieu_id": kieu_id
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                   ds = response.data.data;
                }
            });
            return ds;
        },
        async cboDichVuVTMoi_SelectedValueChanged(){
            if (this.listbox.dichvu_vt_moi.value.toString() == DichVuVienThong.IMS.toString())
            {
                this.controls.lblThietBiIMS.Visible = true;
                this.controls.cboThietBiIMS.Visible = true;
                this.controls.lbSoLuong.Visible = true;
                this.controls.txtSoLuong.Visible = true;
                this.controls.txtIp_IMS.Visible = true;
                this.controls.cboChiTietDVMoi.Visible = true;
            }
            else
            {
                this.controls.lblThietBiIMS.Visible = false;
                this.controls.cboThietBiIMS.Visible = false;
                this.controls.lbSoLuong.Visible = false;
                this.controls.txtSoLuong.Visible = false;
                this.controls.txtIp_IMS.Visible = false;
                this.controls.cboChiTietDVMoi.Visible = false;
            }
            this.LoaiHinhTB_moi_Changed();
        },
        LowerCasePropertyList(obj) {
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toLowerCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                console.log(item);
                return item;
            });
        },
        async LAY_DS_HOPDONG_THEO_MA_GD(){
            console.log("Vào test: " + this.khachhang.magd);
            if (!this.khachhang.magd) return;
            var ds_hopdong = [];
            var pdata = {
                "in_donvi_dl_id": 0,
                "in_donvi_id": this.$root.token.getDonViID() * 1,
                "in_loaihd_id": LoaiHopDong.CHUYENDOI_LH,
                "in_ma_gd": this.khachhang.magd,
                "in_nhanvien_id": this.$root.token.getNhanVienID()*1,
                "in_tthd_id": TrangThaiHD.MOI,
            };
            console.log(pdata);
            await api.get_ds_hopdong_theo_ma_gd(this.axios, pdata).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds_hopdong = response.data.data;
                    }  
                });
            return ds_hopdong;
        },
        async HT_NV_KinhDoanh_Theo_DiaBan(){
            try{
                this.Loading = true;
                var ds = [];
                var pdata = {
                    "quan_id": this.diachiLD.QUAN_ID,
                    "phuong_id": this.diachiLD.PHUONG_ID,
                    "pho_id": this.diachiLD.PHO_ID,
                    "ap_id": this.diachiLD.AP_ID,
                    "khu_id": this.diachiLD.KHU_ID,
                    "donvi_ql_id": this.listbox.donvi_ql.value,
                    "dacdiem_id": this.diachiLD.DACDIEM_ID
                };
                await api.get_ds_nv_kinhdoanh_theo_diaban(this.axios, pdata).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        // this.thuebao.khuvuc = response.data.data[0].ten_kv;
                        // this.thuebao.khuvuc_id = response.data.data[0].khuvuc_id;
                        ds = response.data.data;
                    }  
                });
                if(ds.length > 1)
                {
                    this.popupComponent = ()=>import('../../../contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao');
                    this.popupComponentName = "popupChonKhuVuc";
                    var data = {diachi:this.diachiLD, khuvuc_id: this.thuebao.khuvuc_id, hdtb_id: this.hdtb_id,thuebao_id: this.thuebao.thuebao_id, donvi_id: this.listbox.donvi_ql.value };
                    // console.log('popupChonKhuVuc DATA', data);
                    this.popupComponentData = data;
                    //if(this.dsCTM_TBI && this.dsCTM_TBI.length>0) this.popupComponentData.ds = this.dsCTM_TBI;
                    this.Popup('popupComponents');
                }
                else if (ds.length > 0)
                {
                    this.thuebao.khuvuc = ds[0].ten_kv;
                    this.thuebao.khuvuc_id = ds[0].khuvuc_id;
                    this.dsHDTB_KV = [];
                    var dr = {};
                    dr.HDTB_ID = this.hdtb_id;
                    dr.KHUVUC_ID = this.thuebao.khuvuc_id;
                    dr.LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN;
                    dr.NGUOI_CN = this.nguoi_cn;
                    dr.NGAY_CN =  moment(new Date()).format("DD/MM/YYYY");
                    dr.MAY_CN = this.may_cn;
                    this.dsHDTB_KV.push(dr);
                }
                else
                {
                    this.thuebao.khuvuc = '';
                    this.thuebao.khuvuc_id = 0;
                }
                this.Loading = false;
            }catch (ex) {
                        this.$toast.error('' + ex);
                        this.Loading = false;
                    }
        },
        async HienThiTTHopDongKH(ds, loai) {
            console.log('HienThiTTHopDongKH', ds);
            this.dshd = ds;
            try {
                this.Loading = true;
                if (ds && ds.length > 0) {
                    this.buttonSet = 3;
                    // this.selectedRow = ds[0];
                    this.thuebao.thuebao_id = ds[0]["thuebao_id"];
                    this.khachhang.hdkh_id = ds[0]["hdkh_id"];
                    this.hdtb_id = ds[0]["hdtb_id"];
                    if(loai == 1)
                    {
                        this.NgayYeuCau = moment(ds[0]["ngay_yc"], "YYYY-MM-DD HH:mm:Ss").format("DD/MM/YYYY");
                        this.NgayLapHD = moment(ds[0]["ngaylap_hd"], "YYYY-MM-DD HH:mm:Ss").format("DD/MM/YYYY");
                    }
                    else
                    {
                        this.NgayYeuCau = moment(ds[0]["ngay_yc"], "DD/MM/YYYY HH:mm:Ss").format("DD/MM/YYYY");
                        this.NgayLapHD = moment(ds[0]["ngaylap_hd"], "DD/MM/YYYY HH:mm:Ss").format("DD/MM/YYYY");
                    }
                   
                    this.khachhang.magd = ds[0]["ma_gd"];
                    this.khachhang.mahd = ds[0]["ma_hd"];
                    this.khachhang.makh = ds[0]["ma_kh"];
                    this.hdtt_id = ds[0]["hdtt_id"]; 
                    if (ds[0]["khachhang_id"])
                    {
                        this.khachhang.khachhang_id = ds[0]["khachhang_id"];
                        await api.get_khachhang_id_thicong(this.axios, { 
                                "p_khachhang_id": this.khachhang.khachhang_id,
                                "p_tinhthicong_id": this.tinh.tinhthicong_id
                            }).then(
                            (response) => {
                                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                                    this.khachhang.khachhang_tc_id = response.data.data;
                                }
                            }
                        );
                    }
                    else
                    {
                        this.khachhang.khachhang_id = 0;
                        this.khachhang.khachhang_tc_id = 0;
                    }
                    
                    if (ds[0]["loaikh_id"] && ds[0]["loaikh_id"])
                        this.listbox.loai_kh.value = ds[0]["loaikh_id"];
                    if (ds[0]["kieuhd_id"])
                        this.listbox.kieu_hd.value = ds[0]["kieuhd_id"];
                    else
                        this.listbox.kieu_hd.value = KIEU_HD.TAI_GD;
                    this.vkieuhd_id = this.listbox.kieu_hd.value;
                    this.listbox.loaihinh_tb_moi.value = ds[0]["loaitb_id"];
                    this.khachhang.tenkh = this.ChuanHoaTen(ds[0]["ten_kh"]);
                    this.diachiKH.DIACHI = ds[0]["diachi_kh"];
 
                    this.khachhang.dienthoai = ds[0]["so_dt"];
                    this.maduan = ds[0]["ma_duan"];
                    await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_HDKH_ID(this.khachhang.hdkh_id), 1);  
                    await this.HienThiTTHopDongTT(await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.khachhang.hdkh_id));
                    await this.HienThiTTHopDongTB(await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.khachhang.hdkh_id));// thiếu Địa chỉ LĐ
                    await this.HT_TT();
                    await this.LoadMucCuocTB();                     
                    this.LayTienTheoKhoanMuc(); 
                    var dskv = [];
                    this.thuebao.khuvuc = "";
                    await api.lay_khuvuc_theo_hopdong(this.axios, {params: {"hdtb_id": this.hdtb_id}}).then(
                    (response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            dskv = response.data.data;
                        }
                    });
                    if(dskv.length > 0)
                    {
                        this.thuebao.khuvuc = dskv[0].ma_kv?dskv[0].ma_kv:dskv[0].MA_KV;
                        this.thuebao.khuvuc_id = dskv[0].khuvuc_id?dskv[0].khuvuc_id:dskv[0].KHUVUC_ID;                   
                    }
                    if (this.lvwThueBao.length > 1)
                        this.buttons.tsbtnXoaTB = true;
                    else
                        this.buttons.tsbtnXoaTB = false;
                } 
                else {
                    this.buttonSet = 1;
                }
            } catch (exp) {
                this.$toast.error('' + exp);
                throw exp;
            } finally {
                this.Loading = false;
            }
        },
        async HT_TT(){
            await api.lay_ds_hdtb_doi_lh_tb(this.axios, {"hdkh_id": this.khachhang.hdkh_id})
                .then(
                    (response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                            this.HienThiDanhSacHDTB(response.data.data);
                        }
                        else
                        {
                            this.HienThiDanhSacHDTB([]);
                        }
                    }
            ); 
        },
        HienThiDanhSacHDTB(ds) {
            var tongtienhd = 0;
            var tongtientb = 0; 
            this.lvwThueBao = [];
            
            var i = 1;
            for (var myRow of ds) {
                var item = {
                    stt: i++
                };
                tongtientb = myRow['tongtien'];
                item.tongtien = myRow["tongtien"];
                item.hdtb_id = myRow["hdtb_id"];
                item.ma_gd = myRow["ma_gd"];
                item.ma_tt = myRow["ma_tt"];
                item.ma_tb = myRow["ma_tb"];
                item.ten_tb = this.ChuanHoaTen(myRow["ten_tb"]);
                item.diachi_ld = this.ChuanHoaTen(myRow["diachi_tb"]);
                item.ten_dvvt = myRow["ten_dvvt"];
                item.loaihinh_tb = myRow["loaihinh_tb"];
                item.manv_tc = myRow["manv_tc"];
                item.trangthai_dongbo = myRow["trangthai_dongbo"];
                item.dichvuvt_id = myRow["dichvuvt_id"];
                item.hdtb_cha_id = myRow["hdtb_cha_id"];
                item.loaitb_id = myRow["loaitb_id"]; 
                item.trangthai_vina = myRow["trangthai_vina"];
                
                tongtienhd = tongtienhd + tongtientb;
                this.lvwThueBao.push(item);
            }
            this.tongtienhd = tongtienhd;
            if (i >= 2) {
                this.buttons.tsbtnThemTT = true;
                this.buttons.tsbtnXoaTB = (i != 2);
            } else {
                this.buttons.tsbtnXoaTB = false;
                this.buttons.tsbtnThemTB = false;
            }
            console.log("HienThiDanhSacHDTB lvwThueBao");
            console.log(ds, this.lvwThueBao);
        },
        async LAY_DS_DIACHI_THEO_HDKH_ID(hdkh_id){
            var ds = [];
            await api.get_ds_diachi_theo_hdkh(this.axios, {params: {"hdKhId": hdkh_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async LAY_DS_HOPDONG_TT_THEO_HDKH_ID(hdkh_id){
            var ds = [];
            await api.get_ds_hopdong_tt_theo_hdkh(this.axios, {params: {"hdkhId": hdkh_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === '0') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id){
            var ds = [];
            await api.get_ds_hopdong_tb_theo_hdkh(this.axios, {params: {"hdkhId": hdkh_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === '0') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },  
        async HienThiTT_DanhBa(ma_tb) { 
            var ds = [];
            var ds_tbhd_pl = [];
            await api.get_ds_danhba_theo_matb(this.axios, {"dichVuVtId":this.listbox.dichvu_vt.value,"donViDlId": 0,"maTb":ma_tb})
                .then(
                    (response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                            ds.push(Object.assign({}, response.data.data));// response.data.data => {}
                        }
                    }
            ); 
            this.dsDBTB = ds;
            if(ds && ds.length>0) {
                await api.get_tbhd_pl(this.axios, {"dichVuVtId":this.listbox.dichvu_vt_moi.value,"loaiHdId": LoaiHopDong.CHUYENDOI_LH,"maGd": 0,"maTb":ma_tb})
                    .then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                                ds_tbhd_pl.push(response.data.data);// response.data.data => {}
                            }
                        }
                );
                if(ds_tbhd_pl.length>0) {
                    this.$toast.error("Thuê bao có mã: " + ma_tb + " đã được tạo hợp đồng " + ds_tbhd_pl[0]['ten_loaihd'] + " mã giao dịch: " + ds_tbhd_pl[0]['ma_gd'] + " vào ngày " + ds_tbhd_pl[0]['ngay_yc'] + 'trạng thái hợp đồng: ' + ds_tbhd_pl[0]['trangthai_hd'] + ". \n Bạn không thể thực hiện đổi chuyển đổi cho thuê bao này");
                    return;
                }                    
                if(ds[0]['trangthaitb_id'] != 1)
                {
                    this.$toast.error("Thuê bao có mã: " + ma_tb + " đang ở trạng thái: " + ds[0]['trangthai_tb'] + "\n Bạn không thể thực hiện đổi chuyển đổi cho thuê bao này");
                    return;
                }
                if(ds[0]['ky_hd'] == 0)
                {
                    this.$toast.warning("Khách hàng có mã thuê bao : " + ma_tb + " chưa ký lại hợp đồng gốc \n Đề nghị ký lại hợp đồng gốc");
                    return;
                }
                console.log(444)
                console.log(ds)
                this.thuebao.tbhd_pl = ds_tbhd_pl.length;
                this.NgayYeuCau = moment(new Date()).format("DD/MM/YYYY");
                if(ds[0]['ngaylap_hd']) 
                    this.NgayLapHD = moment(ds[0]['ngaylap_hd'], 'YYYY-MM-DD HH:mm:Ss').format("DD/MM/YYYY");
                this.khachhang.makh = ds[0]['ma_kh'];
                this.khachhang.mahd = ds[0]['ma_hd'];
                if(ds[0]['khachhang_id'] != "")
                {
                    this.khachhang.khachhang_id = ds[0]['khachhang_id'];
                    await api.get_khachhang_id_thicong(this.axios, { 
                                "p_khachhang_id": this.khachhang.khachhang_id,
                                "p_tinhthicong_id": this.tinh.tinhthicong_id
                            }).then(
                            (response) => {
                                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                                    this.khachhang.khachhang_tc_id = response.data.data;
                                }
                            }
                        );
                }
                this.khachhang.tenkh = this.ChuanHoaTen(ds[0]['ten_kh']);
                
                this.diachiKH.DIACHI = this.ChuanHoaTen(ds[0]['diachi_kh']);
                this.khachhang.so_gt = ds[0]["so_gt"];
                this.khachhang.dienthoai = ds[0]['so_dt']; 
                if (!ds[0]["diachi_ndd"] || ds[0]['diachi_ndd'].length==0)
                    this.diachiNDD.DIACHI = this.ChuanHoaTen(ds[0]["diachi_kh"]);
                else this.diachiNDD.DIACHI = this.ChuanHoaTen(ds[0]["diachi_ndd"]);
                console.log(22222)
                if (ds[0]["nganhnghe_id"])
                {
                    this.listbox.nganhngheloc.value = ds[0]["nganhnghe_id"];
                    this.nganhnghe_id = ds[0]["nganhnghe_id"];
                } 
                //Thông tin thuê bao
                this.thuebao.tentb = ds[0]["ten_tb"]; 
                this.thuebao.thuebao_id = ds[0]["thuebao_id"];
                this.thanhtoan.thanhtoan_id = ds[0]["thanhtoan_id"];
                 console.log(11111111111)
                console.log( ds[0])
                this.state.loaitb_id =  ds[0]["loaitb_id"].toString()*1;
                this.listbox.loaihinh_tb.value = this.state.loaitb_id;
                this.listbox.doituong.value = ds[0]["doituong_id"].toString()*1;
                this.listbox.donvi_ql.value = this.state.donviql_id = ds[0]["donviql_id"].toString()*1;
 
                if (this.listbox.tram_tc && this.listbox.tram_tc.list.length > 0) 
                    this.listbox.tram_tc.value = ds[0]["donvi_id"].toString()*1;
                this.thuebao.ghichu = ds[0]["ghichu_tb"];
                 
                this.diachiLD.DIACHI = this.ChuanHoaTen(ds[0]["diachi_ld"]);
                this.diachiTB.DIACHI = this.ChuanHoaTen(ds[0]["diachi_tb"]);
                await api.get_thuebao_id_thicong(this.axios, { 
                        "vtinh_id": this.tinh.tinhthicong_id,
                        "vthuebao_id": this.thuebao.thuebao_id
                    }).then(
                    (response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                            this.tinh.thuebao_tc_id = response.data.data[0].thuebao_kn_id;
                        }
                    }
                );
                await api.get_danhba_theo_thuebao_id_bc(this.axios, { 
                        //"phanvung_id": this.$root.token.getPhanVungID(),
                        "tinhthicong_id": this.tinh.tinhthicong_id,
                        "thuebao_id": this.tinh.thuebao_tc_id,
                        "dichvuvt_id": this.listbox.dichvu_vt.value,
                        "donvi_dl_id": 0,
                        "loaihd_bancheo": 2
                    }).then(
                    (response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {  
                            this.dsDBTBBC = response.data.data;
                        }
                    }
                );
                if(this.dsDBTBBC.length == 0)
                    this.$toast.error("Không lấy được thông tin thuê bao tỉnh thi công, liên hệ admin để xử lý.");
                await this.gan_thongso_theodanhba(1, this.listbox.dichvu_vt.value, this.thuebao.thuebao_id);
                await this.gan_thongso_theodanhba(2, this.listbox.dichvu_vt.value, this.tinh.thuebao_tc_id);
                await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBKHID(this.khachhang.khachhang_id), 1);
                await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTTID(this.thanhtoan.thanhtoan_id), 2);
                await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao.thuebao_id), 3); 
                this.HienThiTTMoRong_DB(this.thuebao.thuebao_id, this.listbox.dichvu_vt.value);
                await this.LoadMucCuocTB();
                this.HienThiThongTinKhuVucDanhBa(this.thuebao.thuebao_id); 
                if (this.state.khong_ht_tracuudb == 0)
                {
                    //TODO:
                    // frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(dichvuvt_id, txtMaTB.Text);
                    // frmTCCTDB.ShowDialog();
                }
            }
            else {
                //this.thuebao.tbhd_pl = 1;
                //this.buttons.chkLapDatBS = false;
                 
                var dv = this.listbox.dichvu_vt.list.filter(x=>x.dichvuvt_id == this.listbox.dichvu_vt.value)[0];
                var ten_dvvt = dv.ten_dvvt;
                this.$toast.error("Không tìm thấy thông tin về thuê bao " + this.thuebao.codinh.somay + " đối với dịch vụ " + ten_dvvt);
            }
        },
        async gan_thongso_theodanhba(vkieu, vdichvu, vthuebaoid){
            if(vkieu == 1)
                this.thongso_ban = Reset_Thongso_Thuebao();
            else
                this.thongso_tc = Reset_Thongso_Thuebao();
            if(vdichvu == 1)
            {
                if(vkieu == 1)
                {
                    var ds_codinh = [];
                    await api.get_ds_danhba_codinh(this.axios, {params: {thueBaoId: vthuebaoid}}).then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                            {
                                ds_codinh = response.data.data;
                            }
                        }
                    );   
                    if(ds_codinh.length > 0)
                    {
                        this.thongso_ban.trangbi_id = ds_codinh[0].trangbi_id;
                        this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"];
                        this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"];
                    }
                }  
                else
                {
                    var ds_codinh = [];
                    await api.get_ds_danhba_codinh_bc(this.axios, {
                        "phanvung_id": this.$root.token.getPhanVungID(),
                        "tinhtc_id": this.tinh.tinhthicong_id,
                        "thuebao_id": vthuebaoid
                    }).then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                            {
                                ds_codinh = response.data.data;
                            }
                        }
                    );
                    if(ds_codinh.length > 0)
                    {
                        this.thongso_tc.trangbi_id = ds_codinh[0].trangbi_id;
                        if(this.dsDBTBBC.length > 0)
                        {
                            this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"];
                            this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"];
                        }
                    }
                }           
            }
            if(vdichvu == 11)
            {
                if(vkieu == 1)
                {
                    var ds_codinh = [];
                    await api.get_ds_danhba_ims(this.axios, {params: {thueBaoId: vthuebaoid}}).then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                            {
                                ds_codinh = response.data.data;
                            }
                        }
                    );  
                    if(ds_codinh.length > 0)
                    {
                        this.thongso_ban.trangbi_id = ds_codinh[0].trangbi_id;
                        this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"];
                        this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"];
                    }
                    
                }  
                else
                {
                    var ds_codinh = [];
                    await api.get_ds_danhba_ims_bc(this.axios, {
                        "phanvung_id": this.$root.token.getPhanVungID(),
                        "tinhtc_id": this.tinh.tinhthicong_id,
                        "thuebao_id": vthuebaoid
                    }).then(
                        (response) => {
                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                            {
                                ds_codinh = response.data.data;
                            }
                        }
                    );
                    if(ds_codinh.length > 0)
                    {
                        this.thongso_tc.trangbi_id = ds_codinh[0].trangbi_id;
                        if(this.dsDBTBBC.length > 0)
                        {
                            this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"];
                            this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"];
                        }
                    }
                }
            }
        },
        HienThiTTDiaChi(ds, kieu) { 
            ds = this.LowerCasePropertyList(ds);
            console.log("HienThiTTDiaChi: kieu= " + kieu, ds);
            if (ds.length > 0) {
                switch (kieu) {
                    case 1:
                        this.diachikh_id = ds[0]['diachi_id'] ? ds[0]['diachi_id'] : ds[0]['diachiid'];
                        this.diachiKH.TINH_ID = ds[0]["tinh_id"] ? ds[0]["tinh_id"] : ds[0]["tinhid"];
                        this.diachiKH.QUAN_ID = ds[0]["quan_id"] ? ds[0]["quan_id"] : ds[0]["quanid"];
                        this.diachiKH.PHUONG_ID = ds[0]["phuong_id"] ? ds[0]["phuong_id"] : ds[0]["phuongid"];
                        if (ds[0]["pho_id"] || ds[0]["phoid"])
                            this.diachiKH.PHO_ID = ds[0]["pho_id"] ? ds[0]["pho_id"] : ds[0]["phoid"];
                        else
                            this.diachiKH.PHO_ID = 0;
                        if (ds[0]["ap_id"] || ds[0]["apid"])
                            this.diachiKH.AP_ID = ds[0]["ap_id"] ? ds[0]["ap_id"] : ds[0]["apid"];
                        else
                            this.diachiKH.AP_ID = 0;
                        if (ds[0]["khu_id"] || ds[0]["khuid"])
                            this.diachiKH.KHU_ID = ds[0]["khu_id"] ? ds[0]["khu_id"] : ds[0]["khuid"];
                        else
                            this.diachiKH.KHU_ID = 0;
                        if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
                            this.diachiKH.DACDIEM_ID = ds[0]["dacdiem_id"] ? ds[0]["dacdiem_id"] : ds[0]["dacdiemid"];
                        else
                            this.diachiKH.DACDIEM_ID = 0;
                        this.diachiKH.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
                        if (ds[0]["vido"] && ds[0]['vido'])
                            this.diachiKH.Lat = ds[0]["vido"];
                        else
                            this.diachiKH.Lat = 0;
                        if (ds[0]["kinhdo"] && ds[0]['kinhdo'])
                            this.diachiKH.Lng = ds[0]["kinhdo"];
                        else
                            this.diachiKH.Lng = 0;
                        break;
                    case 2:
                        this.diachitt_id = ds[0]['diachi_id'] ? ds[0]['diachi_id'] : ds[0]['diachiid'];
                        this.diachiTT.TINH_ID = ds[0]["tinh_id"] ? ds[0]["tinh_id"] : ds[0]["tinhid"];
                        this.diachiTT.QUAN_ID = ds[0]["quan_id"] ? ds[0]["quan_id"] : ds[0]["quanid"];
                        this.diachiTT.PHUONG_ID = ds[0]["phuong_id"] ? ds[0]["phuong_id"] : ds[0]["phuongid"];
                        if (ds[0]["pho_id"] || ds[0]["phoid"])
                            this.diachiTT.PHO_ID = ds[0]["pho_id"] ? ds[0]["pho_id"]: ds[0]["phoid"];
                        else
                            this.diachiTT.PHO_ID = 0;
                        if (ds[0]["ap_id"] && ds[0]["apid"])
                            this.diachiTT.AP_ID = ds[0]["ap_id"] ? ds[0]["ap_id"] : ds[0]["apid"];
                        else
                            this.diachiTT.AP_ID = 0;
                        if (ds[0]["khu_id"] || ds[0]["khuid"])
                            this.diachiTT.KHU_ID = ds[0]["khu_id"] ? ds[0]["khu_id"] : ds[0]["khuid"];
                        else
                            this.diachiTT.KHU_ID = 0;
                        if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
                            this.diachiTT.DACDIEM_ID = ds[0]["dacdiem_id"] ? ds[0]["dacdiem_id"] : ds[0]["dacdiemid"];
                        else
                            this.diachiTT.DACDIEM_ID = 0;
                        this.diachiTT.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
                        if (ds[0]["vido"] && ds[0]['vido'])
                            this.diachiTT.Lat = ds[0]["vido"];
                        else
                            this.diachiTT.Lat = 0;
                        if (ds[0]["kinhdo"] && ds[0]['kinhdo'])
                            this.diachiTT.Lng = ds[0]["kinhdo"];
                        else
                            this.diachiTT.Lng = 0;
                        if (ds[0]["diachibc_id"] || ds[0]["diachibcid"])
                            this.diachibc_id = ds[0]["diachibc_id"] ? ds[0]["diachibc_id"] : ds[0]["diachibcid"];
                        else this.diachibc_id = 0;
                        if (ds[0]["tinhbc_id"] || ds[0]["tinhbcid"])
                            this.diachiBC.TINH_ID = ds[0]["tinhbc_id"] ? ds[0]["tinhbc_id"] : ds[0]["tinhbcid"];
                        else this.diachiBC.TINH_ID = 0;
                        if (ds[0]["quanbc_id"] || ds[0]["quanbcid"])
                            this.diachiBC.QUAN_ID = ds[0]["quanbc_id"] ? ds[0]["quanbc_id"] : ds[0]["quanbcid"];
                        else this.diachiBC.QUAN_ID = 0;
                        if (ds[0]["phuongbc_id"] || ds[0]["phuongbcid"])
                            this.diachiBC.PHUONG_ID = ds[0]["phuongbc_id"] ? ds[0]["phuongbc_id"] : ds[0]["phuongbcid"];
                        else this.diachiBC.PHUONG_ID = 0;
                        if (ds[0]["phobc_id"] || ds[0]["phobcid"])
                            this.diachiBC.PHO_ID = ds[0]["phobc_id"] ? ds[0]["phobc_id"] : ds[0]["phobcid"];
                        else
                            this.diachiBC.PHO_ID = 0;
                        if (ds[0]["apbc_id"] || ds[0]["apbcid"])
                            this.diachiBC.AP_ID = ds[0]["apbc_id"] ? ds[0]["apbc_id"] : ds[0]["apbcid"];
                        else
                            this.diachiBC.AP_ID = 0;
                        if (ds[0]["khubc_id"] || ds[0]["khubcid"])
                            this.diachiBC.KHU_ID = ds[0]["khubc_id"] ? ds[0]["khubc_id"] : ds[0]["khubcid"];
                        else
                            this.diachiBC.KHU_ID = 0;
                        if (ds[0]["dacdiembc_id"] || ds[0]["dacdiembcid"])
                            this.diachiBC.DACDIEM_ID = ds[0]["dacdiembc_id"] ? ds[0]["dacdiembc_id"] : ds[0]["dacdiembcid"];
                        else
                            this.diachiBC.DACDIEM_ID = 0;
                        if (ds[0]["sonha_bc"] || ds[0]["sonhabc"])
                            this.diachiBC.SO_NHA = this.ChuanHoaTen(ds[0]["sonha_bc"] ? ds[0]["sonha_bc"] : ds[0]["sonhabc"]);
                        else this.diachiBC.SO_NHA = '';
                        if (ds[0]["vido_bc"] || ds[0]['vidobc'])
                            this.diachiBC.Lat = ds[0]["vido_bc"] ? ds[0]["vido_bc"] : ds[0]["vidobc"];
                        else
                            this.diachiBC.Lat = 0;
                        if (ds[0]["kinhdo_bc"] || ds[0]['kinhdo_bc'])
                            this.diachiBC.Lng = ds[0]["kinhdo_bc"] ?  ds[0]['kinhdo_bc'] :  ds[0]['kinhdobc'];
                        else
                            this.diachiBC.Lng = 0;
                        break;
                    case 3:
                        this.diachitb_id = ds[0]['diachi_id'] ? ds[0]['diachi_id'] : ds[0]['diachiid'];
                        this.diachild_id = ds[0]['diachild_id'] ? ds[0]['diachild_id'] : ds[0]['diachildid'];
                        this.diachiTB.TINH_ID = ds[0]["tinh_id"] ? ds[0]["tinh_id"] : ds[0]["tinhid"];
                        this.diachiTB.QUAN_ID = ds[0]["quan_id"] ? ds[0]["quan_id"] :  ds[0]["quanid"];
                        this.diachiTB.PHUONG_ID = ds[0]["phuong_id"] ? ds[0]["phuong_id"] : ds[0]["phuongid"];
                        if (ds[0]["pho_id"] || ds[0]["phoid"])
                            this.diachiTB.PHO_ID = ds[0]["pho_id"] ? ds[0]["pho_id"] : ds[0]["phoid"];
                        else
                            this.diachiTB.PHO_ID = 0;
                        if (ds[0]["ap_id"] || ds[0]["apid"])
                            this.diachiTB.AP_ID = ds[0]["ap_id"] ? ds[0]["ap_id"] : ds[0]["apid"];
                        else
                            this.diachiTB.AP_ID = 0;
                        if (ds[0]["khu_id"] || ds[0]["khuid"])
                            this.diachiTB.KHU_ID = ds[0]["khu_id"] ? ds[0]["khu_id"] : ds[0]["khuid"];
                        else
                            this.diachiTB.KHU_ID = 0;
                        if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
                            this.diachiTB.DACDIEM_ID = ds[0]["dacdiem_id"] ? ds[0]["dacdiem_id"] : ds[0]["dacdiemid"];
                        else
                            this.diachiTB.DACDIEM_ID = 0;
                        this.diachiTB.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
                        if (ds[0]["vido"] && ds[0]['vido'])
                            this.diachiTB.Lat = ds[0]["vido"];
                        else
                            this.diachiTB.Lat = 0;
                        if (ds[0]["kinhdo"] && ds[0]['kinhdo'])
                            this.diachiTB.Lng = ds[0]["kinhdo"];
                        else
                            this.diachiTB.Lng = 0;
                        //Địa chỉ lắp đặt
                        if (ds[0]["tinhld_id"] || ds[0]["tinhldid"])
                            this.diachiLD.TINH_ID = ds[0]["tinhld_id"] ? ds[0]["tinhld_id"] : ds[0]["tinhldid"];
                        else 
                            this.diachiLD.TINH_ID = 0;
                        if (ds[0]["quanld_id"] || ds[0]["quanld_id"])
                            this.diachiLD.QUAN_ID = ds[0]["quanld_id"] ? ds[0]["quanld_id"] : ds[0]["quanldid"];
                        else this.diachiLD.QUAN_ID = 0;
                        if (ds[0]["phuongld_id"] || ds[0]["phuongldid"])
                            this.diachiLD.PHUONG_ID = ds[0]["phuongld_id"] ? ds[0]["phuongld_id"] : ds[0]["phuongldid"];
                        else this.diachiLD.PHUONG_ID = 0;
                        if (ds[0]["phold_id"] || ds[0]["pholdid"])
                            this.diachiLD.PHO_ID = ds[0]["phold_id"] ? ds[0]["phold_id"] : ds[0]["pholdid"];
                        else
                            this.diachiLD.PHO_ID = 0;
                        if (ds[0]["apld_id"] || ds[0]["apldid"])
                            this.diachiLD.AP_ID = ds[0]["apld_id"] ? ds[0]["apld_id"] : ds[0]["apldid"];
                        else
                            this.diachiLD.AP_ID = 0;
                        if (ds[0]["khuld_id"] || ds[0]["khuldid"])
                            this.diachiLD.KHU_ID = ds[0]["khuld_id"] ? ds[0]["khuld_id"] : ds[0]["khuldid"];
                        else
                            this.diachiLD.KHU_ID = 0;
                        if (ds[0]["dacdiemld_id"] || ds[0]["dacdiemldid"])
                            this.diachiLD.DACDIEM_ID = ds[0]["dacdiemld_id"] ? ds[0]["dacdiemld_id"] : ds[0]["dacdiemldid"];
                        else
                            this.diachiLD.DACDIEM_ID = 0;
                        if (ds[0]["sonha_ld"] || ds[0]["sonhald"])
                            this.diachiLD.SO_NHA = this.ChuanHoaTen(ds[0]["sonha_ld"] ? ds[0]["sonha_ld"] : ds[0]["sonhald"]);
                        else this.diachiLD.SO_NHA = '';
                        if (ds[0]["vido_ld"] || ds[0]['vidold'])
                            this.diachiLD.Lat = ds[0]["vido_ld"] ? ds[0]["vido_ld"] : ds[0]["vidold"];
                        else
                            this.diachiLD.Lat = 0;
                        if (ds[0]["kinhdo_ld"] || ds[0]['kinhdo_ld'])
                            this.diachiLD.Lng = ds[0]["kinhdo_ld"] ? ds[0]['kinhdo_ld'] : ds[0]['kinhdold'];
                        else
                            this.diachiLD.Lng = 0;
                        if (this.listbox.kieu_ld.value == KieuLapDat.TACH_VLAN) {
                            this.diachiTB.DIACHI = ds[0]["diachi"];
                            this.diachiLD.DIACHI = ds[0]["diachi_ld"] ? ds[0]["diachi_ld"] : ds[0]["diachild"];
                        }
                        // if (ds[0]["diachiid"]) this.diachitb_id = ds[0]["diachiid"];
                        // if (ds[0]['diachildid']) this.diachild_id = ds[0]['diachildid'];
                        // if (ds[0]["tinhid"]) this.diachiTB.TINH_ID = ds[0]["tinhid"];
                        // if (ds[0]["quanid"]) this.diachiTB.QUAN_ID = ds[0]["quanid"];
                        // if (ds[0]["phuongid"]) this.diachiTB.PHUONG_ID = ds[0]["phuongid"];
                        // if (ds[0]["phoid"])
                        //     this.diachiTB.PHO_ID = ds[0]["phoid"];
                        // else
                        //     this.diachiTB.PHO_ID = 0;
                        // if (ds[0]["apid"])
                        //     this.diachiTB.AP_ID = ds[0]["apid"];
                        // else
                        //     this.diachiTB.AP_ID = 0;
                        // if (ds[0]["khuid"])
                        //     this.diachiTB.KHU_ID = ds[0]["khuid"];
                        // else
                        //     this.diachiTB.KHU_ID = 0;
                        // if (ds[0]["dacdiemid"])
                        //     this.diachiTB.DACDIEM_ID = ds[0]["dacdiemid"];
                        // else
                        //     this.diachiTB.DACDIEM_ID = 0;
                      
                        console.log(this.diachiLD, ds[0]);
                        // TODO LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(phuongld_id, phold_id, apld_id, khuld_id, dacdiemld_id); LINE 15387
                        break;
                }
            }
        },
        async HienThiTTHopDongTT(ds) {
            console.log('HienThiTTHopDongTT', ds);
          
            if (ds.length > 0) {
                this.hdtt_id = ds[0]["hdtt_id"]*1;
                this.thanhtoan.thanhtoan_id = this.thanhtoan_id = ds[0]["thanhtoan_id"]*1;
                
                this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_HDTT_ID(this.hdtt_id), 2);
            }
        },
        async LAY_DS_DIACHI_THEO_HDTT_ID(hdtt_id){
            var ds = [];
            await api.get_ds_diachi_theo_hdtt(this.axios, {params: {"hdttId": hdtt_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async HienThiTTHopDongTB(ds) {
            console.log('HienThiTTHopDongTB', ds);
            if (ds.length <= 0) return;
            var data = ds[0];
            await this.Gan_thongso_bancheo_hdtb(this.hdtb_id);
            this.thuebao.codinh.somay = data.ma_tb;
            this.thuebao.tentb = this.ChuanHoaTen(data.ten_tb);
            this.diachiLD.DIACHI = this.ChuanHoaTen(data.diachi_ld);
            this.diachiTB.DIACHI = this.ChuanHoaTen(data.diachi_tb); 
            await api.post_tt_db_thuebao(this.axios, {
              "param": data.thuebao_id*1,
              "type": 8
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.listbox.loaihinh_tb.value = response.data.data * 1; 
                }
            });
 
            
            this.listbox.kieu_ld.value = data.kieuld_id * 1;
            this.listbox.doituong.value = data.doituong_id * 1;
            this.listbox.donvi_ql.value = data.donviql_id * 1;
            this.thuebao.ghichu = data.ghichu;
              
            await api.get_ds_diachi_theo_hdtb_id(this.axios, {
                params: {
                    hdtbId: this.hdtb_id
                }
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.HienThiTTDiaChi(response.data.data, 3);
                }
            });
            await this.HT_ThietBi();
            this.m_dsThueBao_DichVu = await this.LAY_DS_THUEBAO_DICHVU(this.hdtb_id, 0, this.listbox.loaihinh_tb_moi.value, 0);
             
            await this.HT_DichVuGT();
  
            //LayTongTien_HDTB(tchopdong.LAY_TIEN_HD_KM_THEO_HDTB(hdtb_id));
            await api.get_tien_hd_km_theo_hdtb(this.axios, {
                    hdtb_id: this.hdtb_id
            }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                   this.ds_tien_khoanmuc = response.data.data;
                }
            }); 
            await this.LayTongTien_HDTB();
            await this.HienThiTTMoRong_DB(this.thuebao.thuebao_id, this.listbox.dichvu_vt.value);
             
            await this.HienThiTTMoRong_HD(this.hdtb_id, this.listbox.dichvu_vt_moi.value);
            await this.HienThiThongTinKhuVucHopDong(this.thuebao.thuebao_id);
            if (this.lvwThueBao.length > 1)
                this.buttons.tsbtnXoaTB = true;
            else
                this.buttons.tsbtnXoaTB = false;
        },
        async Gan_thongso_bancheo_hdtb(vhdtb_id){
            var ds = [];
            await api.lay_thongso_ban_thicong(this.axios, {params: {"hdtbid": vhdtb_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            if(ds.length > 0)
            {
                this.thongso_ban.muccuoc_id = ds[0].muccuoc_ban;
                this.thongso_ban.muccuoctb_id = ds[0].muccuoctb_ban;
                this.thongso_ban.kieuld_id = ds[0].kieuld_ban;
                this.thongso_ban.doituong_id = ds[0].doituong_ban;
                this.thongso_ban.kieutra_id = ds[0].kieutra_ban;
                this.thongso_tc.muccuoc_id = ds[0].muccuoc_tc;
                this.thongso_tc.muccuoctb_id = ds[0].muccuoctb_tc;
                this.thongso_tc.kieuld_id = ds[0].kieuld_tc;
                this.thongso_tc.doituong_id = ds[0].doituong_tc;
                this.thongso_tc.kieutra_id = ds[0].kieutra_tc;
            }           
        },
        async HienThiThongTinKhuVucHopDong(thuebao_id){
            try{
                var ds = [];
                this.thuebao.khuvuc = "";
                await api.lay_khuvuc_theo_hopdong(this.axios, {params: {"hdtb_id": thuebao_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                });
                if(ds.length > 0)
                {
                    this.thuebao.khuvuc = ds[0].ma_kv?ds[0].ma_kv:ds[0].MA_KV;
                    this.thuebao.khuvuc_id = ds[0].khuvuc_id?ds[0].khuvuc_id:ds[0].KHUVUC_ID;                   
                }
            }catch(ex){
                this.$toast.error('' + ex);
            }
        },
        async LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id){
            var ds = [];
            await api.lay_ds_hopdong_thuebao_ims(this.axios, {params: {"hdtbId": hdtb_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id){
            var ds = [];
            await api.lay_ds_hopdong_thuebao_codinh(this.axios, {params: {"hdtbId": hdtb_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async HienThiTTMoRong_HD(hdtb_id, dichvuvt_id){ 
            this.txtSoLuong = "";
            var ds = [];
            switch (dichvuvt_id)
            {
                case DichVuVienThong.IMS:
                    ds = await this.LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id);
                    break;
                case DichVuVienThong.CO_DINH:
                    ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
                    break;
                default:
                    ds = [];
                    break;
            }
            if(!ds || ds.length <= 0) return;
            switch (dichvuvt_id)
            {
                case DichVuVienThong.IMS:
                    if (ds[0]["trangbi_id"]) 
                        this.listbox.trangbi.valueims = ds[0]["trangbi_id"]*1;
                    if (ds[0]["madoicap"])
                    {
                        this.thuebaocd.madoicap = ds[0]["madoicap"]*1;
                        this.txtMaDoiCap_ADSL = this.thuebaocd.madoicap;
                    }
                    else
                    {
                        this.thuebaocd.madoicap = 0;
                        this.txtMaDoiCap_ADSL = "";
                    }
                    this.thuebaocd.matn = ds[0]["matb_tn"];
                    this.pass_ims = ds[0]["password"];
                    this.txtSoLuong = ds[0]["sl_cuocgoi"];
                    this.ip_ims  = ds[0]["ip"];
                    if (ds[0]["ctdv_id"])
                        this.listbox.ht_chitietdv_ims.value =ds[0]["ctdv_id"]*1;
                    else
                        this.listbox.ht_chitietdv_ims.value = 0;
                    if (ds[0]["chuquan_id"])
                        this.vchuquan_id = ds[0]["chuquan_id"]*1;
                    else
                        this.vchuquan_id = 0;
                    if (ds[0]["chuquanld_id"])
                        this.vchuquanld_id =  ds[0]["chuquanld_id"];
                    else
                        this.vchuquanld_id = 0;
 
                    try
                    { 
                        this.listbox.hinhthuc_tc.value = ds[0]["HTTC_ID"]*1;
                        //cboHinhThucTC_IMS.ItemIndex = cboHinhThucTC_IMS.Properties.GetDataSourceRowIndex("HTTC_ID", ds.Tables[0].Rows[0]["HTTC_ID"]);
                    }
                    catch (ex)
                    { 
                    }
                    break;
                case DichVuVienThong.CO_DINH:
                    if (ds[0]["chuquan_id"])
                        this.vchuquan_id = ds[0]["chuquan_id"]*1;
                    else
                        this.vchuquan_id = 0;
                    if (ds[0]["chuquanld_id"])
                        this.vchuquanld_id = ds[0]["chuquanld_id"]*1;
                    else
                        this.vchuquanld_id = 0;
                    break;
                default:
                    break;
            }
        },
        async LAY_DS_DIACHI_THEO_DBKHID(khachhang_id){
            var ds = [];
            await api.get_ds_diachi_theo_dbkh(this.axios, {params: {"khachHangId": khachhang_id}}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async LAY_DS_DIACHI_THEO_DBTTID(thanhtoan_id){
            var ds = [];
            await api.get_ds_diachi_theo_dbttid(this.axios, {params: { "dbttid": thanhtoan_id }} ).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async LAY_DS_DIACHI_THEO_DBTBID(thuebao_id){
            var ds = [];
            await api.get_ds_diachi_theo_thuebao_id(this.axios, {"thueBaoId": thuebao_id}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                }); 
            return ds;
        },
        async HienThiTTMoRong_DB(thuebao_id, dichvuvt_id){
            var ds = [];
            
            switch (dichvuvt_id)
            {
                case DichVuVienThong.CO_DINH:
                    await api.get_ds_danhba_codinh(this.axios, {params: {thueBaoId: thuebao_id}}
                    ).then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            ds = response.data.data;
                        }
                    });  
                    break;
                case DichVuVienThong.IMS:
                    await api.get_ds_danhba_ims(this.axios, {
                       thueBaoId: thuebao_id 
                    }).then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            ds = response.data.data;
                        }
                    });  
                    break;
                default:
                    ds = [];
                    break;
            }
            if (ds && ds.length > 0)
            {
                switch (dichvuvt_id*1)
                {
                    case DichVuVienThong.CO_DINH:
                        if (ds[0]["donvi_id"])
                            this.listbox.vetinhcd.value =  ds[0]["donvi_id"]*1;
                        if (ds[0]["tramtb_id"])
                            this.listbox.tramtb_ims.value =  ds[0]["tramtb_id"]*1;
                        if (ds[0]["trangbi_id"])
                            this.listbox.trangbi.valuecd =  ds[0]["trangbi_id"]*1;
                        if (ds[0]["chuquan_id"])
                            this.vchuquan_id =  ds[0]["chuquan_id"]*1;
                        else 
                            this.vchuquan_id = 0;
                        if (ds[0]["chuquanld_id"])
                            this.vchuquanld_id =  ds[0]["chuquanld_id"]*1;
                        else 
                            this.vchuquanld_id = 0; 
                        break;
                    case DichVuVienThong.IMS:
                         if (ds[0]["chuquan_id"])
                            this.vchuquan_id =  ds[0]["chuquan_id"]*1;
                        else 
                            this.vchuquan_id = 0;
                        if (ds[0]["chuquanld_id"])
                            this.vchuquanld_id =  ds[0]["chuquanld_id"]*1;
                        else 
                            this.vchuquanld_id = 0; 
                        break;
                    default:
                        break;
                }
            }
        },
        async HienThiThongTinKhuVucDanhBa(thuebao_id){
             try{
                var ds = [];
                this.thuebao.khuvuc = "";
                await api.lay_khuvuc_theo_danhba(this.axios, {"vhddb_id": thuebao_id}).then(
                (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        ds = response.data.data;
                    }
                });
                if(ds.length > 0)
                {
                    this.thuebao.khuvuc = ds[0].ma_kv
                    this.thuebao.khuvuc_id = ds[0].khuvuc_id              
                }
            }catch(ex){
                this.$toast.error('' + ex);
            }
        },
        getDiaChi: function (data, datatype) { 
            this[datatype].DIACHI = data.diachimoi
            this[datatype].TINH_ID = data.tinh_id
            this[datatype].QUAN_ID = data.quan_id
            this[datatype].PHUONG_ID = data.phuong_id
            this[datatype].PHO_ID = data.pho_id
            this[datatype].AP_ID = data.ap_id
            this[datatype].KHU_ID = data.khu_id
            this[datatype].DAC_DIEM_ID = data.dacdiem_id
            this[datatype].SO_NHA = data.so_nha
            this[datatype].Lat = data.lat
            this[datatype].Lng = data.lng
            console.log('getDiaChi: ' + datatype,  this[datatype]);
        },
        clear()
        {
            this.thongso_ban = Reset_Thongso_Thuebao();
            this.thongso_tc = Reset_Thongso_Thuebao();
            this.maduan = "";
            this.vchuquan_id = 0;
            this.vchuquanld_id = 0;
            this.pass_ims = "";
            //txtMaDoiCap_ADSL.Text = "";
            this.thuebaocd.matn = "";
            this.thuebao.codinh.somay = "";
            this.ip_ims = "";
            this.listbox.ht_chitietdv_ims.value = 0;
            //doituong_id = 1;//Defaul: tư nhân 
            // this.khachhang.magd = "";
            this.khachhang.mahd = ""; 
            this.khachhang.makh = "";
            this.khachhang.dienthoai = "";
            this.diachiKH.DIACHI = "";
            this.diachiTB.DIACHI = "";
            this.diachiLD.DIACHI = "";
            this.lvwThueBao = [];
            this.thuebao.ghichu = "";
            this.khachhang.tenkh = "";
            this.khachhang.diachiKH = "";
            //dptNgayYC.Value = tt_nd.ngay_cn;
            //dtpNgayLapHD.Value = tt_nd.ngay_cn;
            this.hdtb_id = null;
            this.thuebao.thuebao_id = 0;
             
            this.thuebao.tentb = "";
            this.thuebao.diachiLD = "";
             
            this.thuebao.diachiTB = "";
             
            //this.listbox.loaihinh_tb_moi.value = 0
            this.ghichu = "";
            this.ma_tb = "";
            //Hiếu bổ sung clear form ngày 04/09/2010
            this.lvwThueBao = [];
            ///TuấnNA thêm code clear các biến trên form -- Ngày 26/09/2010
            this.tongtienhd = 0;
            //Lam khởi tạo lại các biến lấy tiền 17/09/2010
            this.trangbi_id = 3;
            this.tien_tb = 0;
            this.vat_tb = 0;
            this.tien_dv = 0;
            this.vat_dv = 0;
            this.dsCTM_TBI = [];
            this.HienThiTienDichVu(this.tien_dv, this.vat_dv);
            this.HT_ThietBi();
            this.LayTienTheoKhoanMuc();
            this.LAY_DS_DVGT_MACDINH(this.listbox.loaihinh_tb_moi.value);
            //cbo_TramTC.EditValue = cbo_DonViQuanLy.EditValue = null;
            this.thuebao.khuvuc = "";
            this.thuebao.khuvuc_id = 0; 
            
        },
        ChuanHoaTen(ten) {
            if (!ten) return '';
            var ten_new = '';
            var str_con = ten.trim();
            var str_xl = "";
            var k = 0;
            var found = true;
            if (str_con.length < 1) return;
            str_con = str_con.replaceAll(/\s+/g, ' ');
            try {
                while (found) {
                    k = str_con.indexOf(' ');
                    if (k > 0) {
                        str_xl = str_con.substring(0, k);
                        str_con = str_con.substring(k + 1);
                        str_xl = this.ChuyenChuHoa(str_xl);
                        if (ten_new != "")
                            ten_new += " " + str_xl;
                        else ten_new = str_xl;
                        found = true;
                    } else found = false;
                }
                if (str_con != "") {
                    str_xl = str_con;
                    str_xl = this.ChuyenChuHoa(str_xl);
                    if (ten_new != "")
                        ten_new = ten_new + " " + str_xl;
                    else
                        ten_new = str_xl;
                }
                return ten_new;
            } catch (e) {
                return ten;
            }
        },
        ChuyenChuHoa (str) {
            let kq = ''
            let regex = /[A-Z][A-Z]/g
            if (regex.test(str)) kq = str.toUpperCase()
            else {
                if (str[0] === '(') {
                kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase()
                } else {
                kq = str[0].toUpperCase() + str.substring(1).toLowerCase()
                }
            }
            return kq   
        },
        CheckIsNumber(str1){
            return (isNaN(str1) == false);
        },
        UpperCasePropertyList(obj) {
            if(!obj) return []
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
        },
        GetRandomText(length){ 
            const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'; 
            let result = ' ';
            const charactersLength = characters.length;
            for ( let i = 0; i < length; i++ ) {
                result += characters.charAt(Math.floor(Math.random() * charactersLength));
            } 
            return result;
        },
        async SinhPassIMS(){
            var vloai = this.listbox.loaihinh_tb_moi.value;
            var vmatinh = this.$root.token.getMaTinh().toUpperCase();
            let result = '';
            if(vloai == 62)
            {
                result = result + vmatinh + "123";
                return result;
            } 
            else if(vloai == 63)
            {
                result = result + vmatinh + "123456";
                return result;
            }
            else
            {
                if(this.state.matkhau_md_ims != "")
                    return this.state.matkhau_md_ims;
                else
                {
                    var dauso_ims = "";
                    await api.lay_dauso_ims(this.axios,{
                        "p_param": this.$root.token.getMaTinh(),
                        "p_param1":  this.$root.token.getMaTinh(),
                        "p_type": 1
                    }).then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            dauso_ims = response.data.data[0].dauso_ims; 
                        }
                    });
                    if(this.state.tusinh_pass_ims == 0)
                        return "84" + dauso_ims + this.thuebao.codinh.somay.trim().toUpperCase();
                    else
                        this.GetRandomText(11);
                }
            }
        },
        async GetKey(keyname){
            var key = 0;
            await api.post_get_keys(this.axios, {"keyname": keyname}).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    key= response.data.data;  
                }
            });
            return key;
        },
    }
}
 
</script>