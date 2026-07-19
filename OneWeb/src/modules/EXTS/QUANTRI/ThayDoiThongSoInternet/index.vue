<template src="./index.template.html"></template>
<script>
import ChiTietKetNoiVFI from './ChiTietKetNoiVFI'
import IP_WAN_LAN from './IP_LAN_WAN'
import BoSungVatTu from './BoSungVatTu'
import ThueBaoCungMa from './ThueBaoCungMa'
import ChonPort from './chon-port'
import ThayDoiSLCG from './ThayDoiSLCuocGoi'
import DsTBDoiCong from './DsTBDoiCong'
import ThayDoiSLTV from './ThayDoiSoLuongThanhVien'
import LogBNM from './LogBNM'
import ThueBaoCungCap from './ThueBaoCungCap'
//import ThongTinThueBao from './ThongTinThueBao'
import ThongTinThueBao from '@/modules/search/subscriber/SearchGeneral/popup/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import ThayDoiSLID from './ThayDoiSLID'
import TraCuuVDC from './TraCuuVDC'
import VTNService from './service-vtn'
import VASCService from './service-vasc'
import vdcService from './service-vdc'
import BNMService from './service-bnm'
import IMSService from './service-ims'
import ModalXuatDuLieu from '@/modules/ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue'
import XemThongTin from '@/modules/QLSC/modal-xem-thongtin/index.vue'
import CheckPort from '@/modules/search/subscriber/CheckPort'
import frmDoiCong_DS from '@/modules/ECMS/doicong-theodanhsach/index.vue'
export default {
    name:"ThayDoiThongSoInternet",
    components:{
        ChiTietKetNoiVFI,
        IP_WAN_LAN,
        BoSungVatTu,
        ChonPort,
        ThueBaoCungMa,
        ThayDoiSLCG,
        DsTBDoiCong,
        ThayDoiSLTV,
        LogBNM,
        ThueBaoCungCap,
        ThayDoiSLID,
        TraCuuVDC,
        ModalXuatDuLieu,
        XemThongTin,
        ThongTinThueBao,
        CheckPort,
        frmDoiCong_DS
    },
    props: ["outData"],
    data() {
        return {
            propData: null,
            IS_USING_CABMAN_V2: false,
            IS_USING_SERI_IMS: false,
            KIEULAP: {
                LAP_KHONG_SO: 0,
                LAP_KEM: 1,
                LAP_LINE_SAN: 2,
                THAYDOI_TSO: 3,
                PORT_THEO_CAP: 4,
            },
            TRANGTHAI_PORT: {
                CHUA_SD: 1,
                TAM_CAP: 2,
                DA_SD: 3,
                HONG: 4,
                DU_PHONG: 5,
                UPLINK: 7,
            },
            DONGBO_TEST: {
                TEST: false
            },
            TRANGTHAI_DONGBO: {
                DONGBO_CM: 5
            },
            KieuLapDat: {
                THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
                THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,
                THAYDOI_CONGNGHE_AON_GPON: 934,
                THAYDOI_CONGNGHE_GPON_AON: 935,
                THAYDOI_CONGNGHE_AON_XGSPON: 19581,
                THAYDOI_CONGNGHE_GPON_XGSPON: 19582,
                THAYDOI_CONGNGHE_XGSPON_AON: 19583,
                THAYDOI_CONGNGHE_XGSPON_GPON: 19584
            },
            LoaiHopDong: {
                DAT_MOI: 1,
                DI_CHUYEN: 3,
                CHUYENDOI_LH: 6,
                KHOIPHUC_SD: 30,
                THAY_DOI_TOCDO_ADSL: 8,
                THAYDOI_THONGSO_MEGAWAN: 5,
            },
            TrangThaiHD: {
                MOI: 1,
                THANH_TOAN: 2,
                DIEUHANH_THI_CONG: 3,
                DANG_THI_CONG: 4,
                DA_THI_CONG_XONG: 5,
                HOAN_THANH: 6,
                THOAITRA: 7,
                DA_LAY_DU_LIEU: 8,
                KHAIBAO_TONGDAI: 9,
                DANGCHO_HOANCONG: 10,
                DOISOAT_HOSO: 11,
                TTVT_XACMINH_NO: 14,
                THOAITRA_TMP: 17,
                TTVT_HOANCONG_DICHVU: 23,
                HTHS_BANCHEO: 26,
                NVKD_XACMINH: 27
            },
            LoaiHinhTB: {
                IMS_POTS: 62,
                IMS_SIP: 63,
                INTERNET_ADSL: 11,
                INTERNET_FTTH: 58,
                WIFI_FIBER: 79,
                INTERNET_MYTV: 61,
                MEGA: 11,
                IMS_SIPTRUNKING: 77
            },
            DichVuVienThong: {
                ADSL: 4,
                IMS: 11,
                BRCD_DOITAC: 22,
                IMS_DOITAC: 21,
                MEGAWAN: 8,
                METRONET: 7,
                MGW_DOITAC: 23,
                TSL: 9,
                TSL_DOITAC: 24
            },
            objIP_WAN_LAN:{},
            bras_cu_id:0,
            dslam_cu_id:0,
            dataThueBaoCungCap: {},
            dataThayDoiSLTV: {},
            trangthai_dl: 1, // Trạng thái dữ liệu: 0 - lấy trong hợp đồng/ phụ lục, 1 - Lấy trong danh bạ. Defaul lấy trong danh bạ.
            tramvt_id: 0,
            tramtb_id: 0,
            thuebao_id: 0,
            port_cu_id: 0,
            vci_vpi_cu_id: 0,
            port_moi_id: 0,
            vci_vpi_moi_id: 0,
            hdtb_id: 0,
            madoicap: 0,
            dslam_tinh_id: 0,
            thamso_dslam: null,
            sl_cuocgoi: null,
            passwordMegaVNN: null,
            ten_hs: null,
            kt_thaydoi: false,
            tdtt_db_id: 0,
            ketcuoi_moi_id: 0,
            vitri_moi_id: 0,
            dsThuocTinhXuatExcel: [],
            dsDataXuatExcel: [],

            tsbtnTraCuuVISA: {
                Enabled: true
            },
            tsbtnXemMatKhau: {
                Enabled: true
            },
            tsbtnCapNhat: {
                Enabled: true,
                Visible: true
            },
            tsbtnNhapMoi: {
                Enabled: true
            },
            tsbtnDongBoDL: {
                Enabled: true,
                Visible: true
            },
            tsbtnDoiIPWanLan: {
                Visible: false
            },
            tsbtnThayDoiSLID: {
                Visible: true
            },
            btnDoiMK_IMS: {
                Enabled: true,
            },
            cboBrasADSL: {
                DataSource: [],
                Value: null
            },
            cboBrasADSLMoi: {
                DataSource: [],
                Value: null
            },
            cboDichVuVT: {
                DataSource: [],
                Value: null
            },
            cboLoaihinhTB: {
                DataSource: [],
                Value: null
            },
            cboDSLAM: {
                DataSource: [],
                Value: null
            },
            cboDSLAMMoi: {
                DataSource: [],
                Value: null
            },
            txtONU_PASS_MOI: {
                Text: null
            },
            txtVCIADSLMoi: {
                Text: null
            },
            txtVPIADSLMoi: {
                Text: null
            },
            txtSlotADSLMoi: {
                Text: null
            },
            txtPortMoi: {
                Text: null
            },
            txtSystemMoi: {
                Text: null
            },
            txtRackMoi: {
                Text: null
            },
            txtShelfMoi: {
                Text: null
            },
            txtIpMoi: {
                Text: null
            },
            txtPortMDFMoi: {
                Text: null
            },
            txtSLIDMoi: {
                Text: null
            },
            txtSeriModemMoi: {
                Text: null
            },
            txtSoAo: {
                Text: null,
                Enabled: false,
                ReadOnly: true,
            },
            txtThongTinCTMoi: {
                Text: null
            },
            txtThongTinCTCu: {
                Text: null
            },
            txtGhiChu: {
                Text: null
            },
            txtTocDo: {
                Text: null
            },
            txtPortOLT_Cu: {
                Text: null
            },
            txtPortOLT_Moi: {
                Text: null
            },
            txtSlotOLT_Cu: {
                Text: null
            },
            txtSlotOLT_Moi: {
              Text: null
            },
            txtDslamTinhCu: {
                Text: null
            },
            txtDslamTinhMoi: {
                Text: null
            },
            txtONU_PASS_Cu: {
                Text: null
            },
            txtOnuPassAdsl: {
                Text: null
            },
            txtTenTB: {
                Text: null
            },
            txtDiaChiLD: {
                Text: null
            },
            txtSoMayTN: {
                Text: null
            },
            txtVCIADSL: {
                Text: null
            },
            txtVPIADSL: {
                Text: null
            },
            txtSlotADSL: {
                Text: null
            },
            txtPort: {
              Text: null
            },
            txtSystem: {
                Text: null
            },
            txtRack: {
                Text: null
            },
            txtShelf: {
                Text: null
            },
            txtIpCu: {
                Text: null
            },
            txtPortMDF: {
                Text: null
            },
            txtSelfid01: {
                Text: null
            },
            txtSelfid02: {
                Text: null
            },
            txtSelfid03: {
                Text: null
            },
            txtSelfid04: {
                Text: null
            },
            txtSLID: {
                Text: null,
                Enabled: true
            },
            txtSeriModem: {
                Text: null,
                Enabled: true
            },
            txtSoMayTNMoi: {
                Text: null
            },
            txtSelfid01Moi: {
                Text: null
            },
            txtSelfid02Moi: {
                Text: null
            },
            txtSelfid03Moi: {
                Text: null
            },
            txtSelfid04Moi: {
                Text: null
            },
            txtMaTB: {
                Text: null
            },
            txtMaTN: {
                Text: null
            },
            txtTrangThai: {
                Text: null
            },
            txtAdsl_Port_Cu: {
                Text: null
            },
            txtAdsl_Port_Moi: {
                Text: null
            },
            txtSubSlot: {
                Text: null
            },
            txtSubSlot_Moi: {
                Text: null
            },
            txtWANIP: {
                Text: null
            },
            txtLANIP: {
                Text: null
            },
            txtWANIP_Moi: {
                Text: null
            },
            txtLANIP_Moi: {
                Text: null
            },
            txtCongNghe_Moi: {
                id: null,
                Text: null
            },
            txtVrf: {
                Text: null
            },
            txtLoaiKenh: {
                Text: null
            },
            txtUsernameMoi: {
                Text: null
            },
            txtUsernameCu: {
                Text: null
            },
            txtDiaChiMacMoi: {
                Text: null
            },
            txtDiachiMacCu: {
                Text: null
            },
            txtMaDoiCap: {
                Text: null
            },
            txtPass_IMS: {
                Text: null
            },
            txtPass_IMS_Moi: {
                Text: null
            },
            txtCongNghe: {
                id: null,
                Text: null
            },
            txtBrasTinhCu: {
                Text: null
            },
            chkChuyenCacDV: {
                Visible: false,
                Checked: false,
                Text: null
            },
            chkSLID_CU: {
                Checked: false
            },
            chkSR_MD_CU: {
                Checked: false
            },
            chkSR_MD_MOI: {
                Checked: false
            },
            chkSLID_MOI: {
                Checked: false
            },
            chkBNM: {
                Checked: false
            },
            rdoDiemDauCuoi: {
                Value: 1,
                Visible: false
            },
            rdoDanhBaHopDong: {
                Value: 1,
                DanhBa: 1,
                HopDong: null
            },
            ktload: true,
            maTinh: null,
            ip: null,
            donvi_dl_id: null,
            matKhauView: null,
            moNut: false,
            isLoading: true,
            checkPortDialog: {
                isVisiable: false,
            },
            formADSLControl: {
                isDisable: true,
                icon: {
                    edit: "nc-icon-glyph ui-1_edit-76",
                    save: "one-check"
                },
                iconLabel: "nc-icon-glyph ui-1_edit-76"
            }
        }
    },
    watch: {
        "txtMaTB.Text": async function(value) {
            this.tsbtnXemMatKhau.Enabled = !!value;
            this.tsbtnTraCuuVISA.Enabled = !!value;
        },
        "rdoDiemDauCuoi.Value": async function(value) {
            this.Clear()
        },
        "rdoDanhBaHopDong.DanhBa": function(value) {

            value = parseInt(value)

            if (value == 1) {
                this.rdoDanhBaHopDong.HopDong = null
                this.rdoDanhBaHopDong.Value = 1
            }
        },
        "rdoDanhBaHopDong.HopDong": function(value) {

            value = parseInt(value)

            if (value == 2) {
                this.rdoDanhBaHopDong.DanhBa = null
                this.rdoDanhBaHopDong.Value = 2
            }
        },
        "rdoDanhBaHopDong.Value": function(value) {

            this.trangthai_dl = parseInt(value) == 1 ? 1 : 0

            if (!this.isLoading) {
                this.Clear();
            }
        },
        "cboBrasADSL.Value": async function(value) {
            this.cboDSLAM.DataSource = !value ? [] : await this.lay_ds_dslam_vdc_theo_bras(value)
        },
        "cboBrasADSLMoi.Value": async function(value) {
            this.cboDSLAMMoi.DataSource = !value ? [] : await this.lay_ds_dslam_vdc_theo_bras(value)
        },
        "cboDichVuVT.Value": async function(value) {

            let dsLoaihinhTB = await this.lay_ds_loaihinh_tb(value)

            if (dsLoaihinhTB.length > 0)
            {
                dsLoaihinhTB = dsLoaihinhTB.map(item => {
                    let newItem = {}
                    Object.keys(item).forEach(key => newItem[key.toLowerCase()] = item[key])
                    return newItem
                })
            }

            this.cboLoaihinhTB.DataSource = dsLoaihinhTB

            if (this.cboLoaihinhTB.DataSource.length > 0)
            {
                let cboLoaihinhTBValue = parseInt(this.cboLoaihinhTB.Value)

                if (!cboLoaihinhTBValue || !this.cboLoaihinhTB.DataSource.some(item => item.loaitb_id == cboLoaihinhTBValue)) {
                    this.cboLoaihinhTB.Value = this.cboLoaihinhTB.DataSource[0].loaitb_id
                }
            }

            let dichvuvt_id = parseInt(value)

            if (dichvuvt_id == this.DichVuVienThong.ADSL || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
            {
                this.txtSoAo.Enabled = true;
                this.txtSoAo.ReadOnly = false;
            }
            else
            {
                this.txtSoAo.Enabled = true;
                this.txtSoAo.ReadOnly = true;
            }

            if (dichvuvt_id == this.DichVuVienThong.TSL || dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
            {
                this.rdoDiemDauCuoi.Visible = true;
            }
            else
            {
                this.rdoDiemDauCuoi.Visible = false;
            }

            if (dichvuvt_id == this.DichVuVienThong.TSL ||
                dichvuvt_id == this.DichVuVienThong.MEGAWAN ||
                dichvuvt_id == this.DichVuVienThong.METRONET ||
                dichvuvt_id == this.DichVuVienThong.MGW_DOITAC ||
                dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
            {
                this.tsbtnCapNhat.Enabled = false;

                if (this.moNut)
                {
                    this.tsbtnDongBoDL.Visible = true;
                }
            }
            else
            {
                this.tsbtnCapNhat.Enabled = true;
            }
        }
    },
    async mounted() {
        try {
            this.isLoading = true
            await this.frmThayDoiTSInternet_Load()
        } finally {
            this.isLoading = false
        }
    },
    methods: {
        setPropData(data) {
            this.Clear()
            this.propData = data
        },
        async tsbtnDoiIPWanLan_Click() {

            let dichvuvt_id = parseInt(this.cboDichVuVT.Value)

            if (dichvuvt_id != this.DichVuVienThong.MEGAWAN && dichvuvt_id != this.DichVuVienThong.METRONET)
            {
                this.$root.toastError("Chức năng chỉ áp dụng cho thuê bao megawan, metronet !");
                return;

            }

            let result = null;

            if (dichvuvt_id == this.DichVuVienThong.MEGAWAN || dichvuvt_id == this.DichVuVienThong.METRONET)
            {
                let data = this.TaoDuLieu_MGWAN();
                result = await this.insert_tdtt_db_mgwan(data);

                if (result.kq == 0)
                {
                    this.$root.toastError("Cập nhật thông số trên dữ liệu viễn thông tỉnh thất bại.\n" + result.message)
                    return
                }
            }

            result = await this.capnhat_ipwan_iplan({
                thuebao_id: this.thuebao_id,
                lan_ip: this.txtLANIP_Moi.Text,
                wan_ip: this.txtWANIP_Moi.Text
            })

            if (result.kq == 0)
            {
                this.$root.toastError("Cập nhật thông số trên dữ liệu viễn thông tỉnh thất bại.\n" + result.message)
                return
            }

            this.$root.toastSuccess("Cập nhật thông số trên dữ liệu viễn thông tỉnh thành công !")
            this.kt_thaydoi = true;
            
            try {
                this.loading(true)
                await this.HienThiThongTin();
            } finally {
                this.loading(false)
            }
        },
        async capnhat_ipwan_iplan(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_ipwan_iplan`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async tsbtnResetONT_Click() {

            if (!this.thuebao_id)
            {
                this.$root.toastError("Vui lòng nhập mã thuê bao để sử dụng chức năng reset ONT");
                return;
            }

            try {
                this.loading(true)
                let kq = await this.reset_ont({ma_tb: this.txtMaTB.Text})

                if (kq.kq != 1)
                {
                    this.$root.toastError(kq.message);
                }
                else
                {
                    this.$root.toastSuccess("Đã reset ONT");
                }
            } finally {
                this.loading(false)
            }
        },
        async reset_ont(data) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/reset_ont`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async tsbtnCheckSerial_Click() {

            if (this.hdtb_id == 0)
            {
                this.$root.toastError("Vui lòng nhập mã thuê bao để tra cứu vật tư");
                return;
            }

            let kq = await this.kiemtra_vattu_bnm({hdtb_id: this.hdtb_id})

            if (kq.kq != 1)
            {
                this.$root.toastError(kq.message);
            }
            else
            {
                this.$root.toastSuccess("Thuê bao đã được cấp vật tư BNM");
            }
        },
        async kiemtra_vattu_bnm(data) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/kiemtra_vattu_bnm`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        tsbtnChiTietTB_Click() {
            this.$refs.formThongTinThueBao.showModal();
        },
        lblLoaiONU_Click() {
            this.$bvModal.show('thayDoiThongSoInternet_popupBoSungVatTu')
        },
        async lnkTempBNM_Cu_Click() {

            let kq = "";

            if (!this.port_cu_id && !this.vci_vpi_cu_id)
            {
                return
            }

            try {
                this.loading(true)
                let dsTS = await this.lay_ds_thamso_md_mats("USING_DEFINE_TEMP_BNM")
                let _define_temp_bnm = dsTS.length > 0 ? dsTS['ten_ts'] : 0;

                if (_define_temp_bnm == 1)
                {
                    return
                }

                let _dtBNM = null;

                if (this.trangthai_dl == 1)
                {
                    _dtBNM = await this.lay_tt_temp_bnm({
                        thuebao_id: this.thuebao_id,
                        hdtb_id: 0,
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id,
                    })
                }
                else
                {
                    _dtBNM = await this.lay_tt_temp_bnm({
                        thuebao_id: 0,
                        hdtb_id: this.hdtb_id,
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id,
                    })
                }

                let password_ftth = await vdcService.getPass(_dtBNM["account_ftth"], this);

                let pon = {};
                pon.acc_kh = _dtBNM["account_ftth"];
                pon.boSungMyTV = _dtBNM["bosung"];
                pon.brand = 0;
                pon.cardONU = _dtBNM["card_onu"];
                pon.description = _dtBNM["serial_num"];
                pon.enable_pass = _dtBNM["password_enable"];
                pon.frame = _dtBNM["frame"];
                pon.gem_ims_profile_dwn = !!_dtBNM["gem_ims_profile_dwn"] ? _dtBNM["gem_ims_profile_dwn"] : "";
                pon.gem_ims_profile_up = !!_dtBNM["gem_ims_profile_up"] ? _dtBNM["gem_ims_profile_up"] : "";
                pon.idLenh = _dtBNM["id"];
                pon.ims_description = !!_dtBNM["ims_description"] ? _dtBNM["ims_description"] : "";
                pon.ims_name = !!_dtBNM["ims_name"] ? _dtBNM["ims_name"] : "";
                pon.ims_servicename = !!_dtBNM["ims_servicename"] ? _dtBNM["ims_servicename"] : "";
                pon.inbound_ims_profile = !!_dtBNM["inbound_ims_profile"] ? _dtBNM["inbound_ims_profile"] : "";
                pon.ip = _dtBNM["ip_node"];
                pon.ipBras = !!_dtBNM["ipbras"] ? _dtBNM["ipbras"] : "";
                pon.ipVPN = "";
                pon.loai = _dtBNM["loai"];
                pon.ma_men = _dtBNM["serial_num"];
                pon.myTVDescription = !!_dtBNM["mytvdescription"] ? _dtBNM["mytvdescription"] : "";
                pon.mytvhd_dw = _dtBNM["mytv_bandwidth_profile"];
                pon.mytvhd_up = _dtBNM["mytv_shaper_profile"];
                pon.onu_type = _dtBNM["loai_onu_id"];
                pon.onuid = _dtBNM["port_mdf"];
                pon.onuport_mytv = _dtBNM["port_mytv"];
                pon.outbound_ims_profile = !!_dtBNM["outbound_ims_profile"] ? _dtBNM["outbound_ims_profile"] : "";
                pon.pass_kh = !password_ftth ? _dtBNM["password_ftth"] : password_ftth;
                pon.ponPort = _dtBNM["port"];
                pon.ponslotid = _dtBNM["slot"];
                pon.profileName = _dtBNM["profilename"];
                pon.queue0 = 0;
                pon.queue1 = 1;
                pon.srvConProfile = !!_dtBNM["srvconprofile"] ? _dtBNM["srvconprofile"] : "";
                pon.svlan = _dtBNM["vpi"];
                pon.svlan_tr069 = _dtBNM["vlan_tr069"];
                pon.tcon2_Profile = _dtBNM["tcon2_profile"];
                pon.telnetPass = _dtBNM["password_telnet"];
                pon.telnetUser = _dtBNM["user_telnet"];
                pon.traffic_Prf_Dw = _dtBNM["traffic_prf_dw"];
                pon.traffic_Prf_Up = _dtBNM["traffic_prf_up"];
                pon.vlan = _dtBNM["vci"];
                pon.vlan_vod = _dtBNM["vlan_vod"];
                pon.voip_password = !!_dtBNM["voip_password"] ? _dtBNM["voip_password"] : "";
                pon.voip_tconProfile = !!_dtBNM["voip_tconprofile"] ? _dtBNM["voip_tconprofile"] : "";
                pon.voip_vlan = !!_dtBNM["voip_vlan"] ? _dtBNM["voip_vlan"] : "";
                pon.voip_userid = !!_dtBNM["voip_userid"] ? _dtBNM["voip_userid"] : "";
                pon.voip_username = !!_dtBNM["voip_username"] ? _dtBNM["voip_username"] : "";
                pon.vtt = _dtBNM["ma_tinh"];
                pon.ponslotid = _dtBNM["slot"]
                pon.telnetPass = _dtBNM["password_telnet"]
                pon.vlenh_bnm_id = _dtBNM["lenh_bnm_id"]
                kq = await BNMService.Xem_Temp(pon, this)
            } finally {
                this.loading(false)
            }

            let modal = this.$refs['modal-xem-thongtin']
            modal.setTitle('Temp BNM')
            modal.setContent(kq)
            modal.show()
        },
        async lnkTempBNM_Moi_Click() {

            if (!this.port_moi_id && !this.vci_vpi_moi_id)
            {
                return
            }

            let _dtBNM = null

            if (this.trangthai_dl == 1)
            {
                _dtBNM = await this.lay_tt_temp_bnm({
                    thuebao_id: this.thuebao_id,
                    hdtb_id: 0,
                    port_id: this.port_moi_id,
                    vci_vpi_id: this.vci_vpi_moi_id,
                })
            }
            else
            {
                _dtBNM = await this.lay_tt_temp_bnm({
                    thuebao_id: 0,
                    hdtb_id: this.hdtb_id,
                    port_id: this.port_moi_id,
                    vci_vpi_id: this.vci_vpi_moi_id,
                })
            }

            let password_ftth = await vdcService.getPass(_dtBNM["account_ftth"], this);

            let pon = {};
            pon.acc_kh = _dtBNM["account_ftth"];
            pon.boSungMyTV = _dtBNM["bosung"];
            pon.brand = 0;
            pon.cardONU = _dtBNM["card_onu"];
            pon.description = _dtBNM["serial_num"];
            pon.enable_pass = _dtBNM["password_enable"];
            pon.frame = _dtBNM["frame"];
            pon.gem_ims_profile_dwn = !!_dtBNM["gem_ims_profile_dwn"] ? _dtBNM["gem_ims_profile_dwn"] : "";
            pon.gem_ims_profile_up = !!_dtBNM["gem_ims_profile_up"] ? _dtBNM["gem_ims_profile_up"] : "";
            pon.idLenh = _dtBNM["id"];
            pon.ims_description = !!_dtBNM["ims_description"] ? _dtBNM["ims_description"] : "";
            pon.ims_name = !!_dtBNM["ims_name"] ? _dtBNM["ims_name"] : "";
            pon.ims_servicename = !!_dtBNM["ims_servicename"] ? _dtBNM["ims_servicename"] : "";
            pon.inbound_ims_profile = !!_dtBNM["inbound_ims_profile"] ? _dtBNM["inbound_ims_profile"] : "";
            pon.ip = _dtBNM["ip_node"];
            pon.ipBras = !!_dtBNM["ipbras"] ? _dtBNM["ipbras"] : "";
            pon.ipVPN = "";
            pon.loai = _dtBNM["loai"];
            pon.ma_men = _dtBNM["serial_num"];
            pon.myTVDescription = !!_dtBNM["mytvdescription"] ? _dtBNM["mytvdescription"] : "";
            pon.mytvhd_dw = _dtBNM["mytv_bandwidth_profile"];
            pon.mytvhd_up = _dtBNM["mytv_shaper_profile"];
            pon.onu_type = _dtBNM["loai_onu_id"];
            pon.onuid = _dtBNM["port_mdf"];
            pon.onuport_mytv = _dtBNM["port_mytv"];
            pon.outbound_ims_profile = !!_dtBNM["outbound_ims_profile"] ? _dtBNM["outbound_ims_profile"] : "";
            pon.pass_kh = !password_ftth ? _dtBNM["password_ftth"] : password_ftth;
            pon.ponPort = _dtBNM["port"];
            pon.ponslotid = _dtBNM["slot"];
            pon.profileName = _dtBNM["profilename"];
            pon.queue0 = 0;
            pon.queue1 = 1;
            pon.srvConProfile = !!_dtBNM["srvconprofile"] ? _dtBNM["srvconprofile"] : "";
            pon.svlan = _dtBNM["vpi"];
            pon.svlan_tr069 = _dtBNM["vlan_tr069"];
            pon.tcon2_Profile = _dtBNM["tcon2_profile"];
            pon.telnetPass = _dtBNM["password_telnet"];
            pon.telnetUser = _dtBNM["user_telnet"];
            pon.traffic_Prf_Dw = _dtBNM["traffic_prf_dw"];
            pon.traffic_Prf_Up = _dtBNM["traffic_prf_up"];
            pon.vlan = _dtBNM["vci"];
            pon.vlan_vod = _dtBNM["vlan_vod"];
            pon.voip_password = !!_dtBNM["voip_password"] ? _dtBNM["voip_password"] : "";
            pon.voip_tconProfile = !!_dtBNM["voip_tconprofile"] ? _dtBNM["voip_tconprofile"] : "";
            pon.voip_vlan = !!_dtBNM["voip_vlan"] ? _dtBNM["voip_vlan"] : "";
            pon.voip_userid = !!_dtBNM["voip_userid"] ? _dtBNM["voip_userid"] : "";
            pon.voip_username = !!_dtBNM["voip_username"] ? _dtBNM["voip_username"] : "";
            pon.vtt = _dtBNM["ma_tinh"];
            pon.ponslotid = _dtBNM["slot"]
            pon.telnetPass = _dtBNM["password_telnet"]
            pon.vlenh_bnm_id = _dtBNM["lenh_bnm_id"]

            let kq = await BNMService.Xem_Temp(pon, this)
            let modal = this.$refs['modal-xem-thongtin']
            modal.setTitle('Temp BNM')
            modal.setContent(kq)
            modal.show()
        },
        async tsbtnTestAllPort_Click() {

            let vInput = this.txtMaTB.Text;
            let vdichvuvtID = parseInt(this.cboDichVuVT.Value)

            this.loading(true)

            try {
                let status = await this.getPortStatus(vInput, vdichvuvtID)

                if (!status)
                {
                    this.$root.toastError("Không tìm thấy thông tin")
                }
                else if (status == "UP")
                {
                    this.$root.toastSuccess('UP')
                }
                else
                {
                    this.$root.toastError(status)
                }
            } finally {
                this.loading(false)
            }
        },
        async getPortStatus(vInput, vdichvuvt_id) {
            try
            {
                let userAltInfo = await this.lay_tt_user_alt()

                let vuser_alt = !!userAltInfo && !!userAltInfo.user_alt ? userAltInfo.user_alt : ""
                let vpass_alt = !!userAltInfo && !!userAltInfo.pass_alt ? userAltInfo.pass_alt : ""

                if (!vuser_alt || !vpass_alt) {
                    return "";
                }

                //Đo kiểm trước khi nghiệm thu
                if (vdichvuvt_id != this.DichVuVienThong.ADSL)
                {
                    return "";
                }

                return await BNMService.GetPortStatus(vuser_alt, vpass_alt, vInput, 0, this);
            }
            catch (err)
            {
                return "ERR: " + err.toString();
            }
        },
        async lay_tt_user_alt() {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_user_alt`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async tsbtnDoKiem_Click() {

            if (!this.txtMaTB.Text) {
                this.$root.toastError('Bạn hãy nhập mã thuê bao!')
                this.$refs['ref-txt-matb'].focus()
                return
            }

            this.checkPortDialog.isVisiable = true
            this.$refs.checkPort.show() // Tham chiếu form UR2.8.018 – Test port - https://cntt.vnpt.vn/browse/BSS-1248
        },
        async checkPortOnClose() {
            this.checkPortDialog.isVisiable = false
        },
        tsbtnTraCuuVISA_Click() {
            if (this.tsbtnTraCuuVISA.Enabled)
            {
                this.$refs.traCuuVDC.show(this.txtMaTB.Text).then(result => { })
            }
        },
        async tsbtnXemMatKhau_Click() {

            if (!this.tsbtnXemMatKhau.Enabled)
            {
                return
            }

            try
            {
                this.matKhauView = await vdcService.getPass(this.txtMaTB.Text, this);
                this.$refs['ref-xem-mat-khau'].show()
            }
            catch(err)
            {
                this.$root.toastError(err)
            }
        },
        async tsbtnXuatExcel_Click()
        {
            if (!!this.txtMaTB.Text)
            {
                let dsExel = []

                try {
                    this.loading(true)

                    if (this.trangthai_dl == 1) //Nếu lấy dữ liệu trong danh bạ.
                    {
                        dsExel = await this.lay_danhba_theo_matb(this.txtMaTB.Text, this.cboDichVuVT.Value, 0)
                    }
                    else
                    {
                        dsExel = await this.lay_ds_hopdong_theo_ma_tb(this.txtMaTB.Text, 0, 0, 0, 0, 0, this.cboDichVuVT.Value, this.cboLoaihinhTB.Value);
                    }
                }
                finally
                {
                    this.loading(false)
                }

                if (dsExel.length > 0)
                {
                    this.dsThuocTinhXuatExcel = [
                        "STT","KHACHHANG_ID","MA_KH","TEN_KH","DIACHI_KH","SO_DT","LOAIKH_ID","NGAYLAP_HD","HOKHAU","NGAY_HK","NOICAP_HK","MAKH_CU","SO_FAX","EMAIL","WEBSITE",
                        "NGAY_SN","SO_GT","NGAYCAP","NOICAP","KHLON_ID","LOAIGT_ID","MA_HD","KH_KT3","HOKHAU","CHUCDANH","KY_HD","KIEUKH_ID","KETQUA_CH","KM_HD","KIEUKH_ID",
                        "KETQUA_CH","GIOITINH","DANTOC_ID","QUOCTICH_ID","NGANHNGHE_ID","DIACHI_NDD","NGAYHETHAN_GT","MST","STK","NGANHANG_ID","KY_HD","KIEUKH_ID","GIOITINH",
                        "DANTOC_ID","QUOCTICH_ID","GHICHU","THANHTOAN_ID","MA_TT","TEN_TT","DIACHI_TT","MST_TT","STK_TT","CHU_TK_TT","MATB_DD","HTTT_ID",
                        "NGANHANG_ID_TT","TUYENTHU_ID","NHANVIEN_ID","DONVI_ID","NGUOI_DD","TUYENTHU","DV_QHNS","MACHUONG","MN_KT","MA_NDKT","MN_NSNN","TIEN_DM","SO_DT SO_DT_TT",
                        "EMAIL_TT","DIACHI_CT","GHICHU_TT","DIACHI_BC","MIG_ID","MAIN_GHEP","CHU_TK","THUEBAO_ID","MA_TB","TEN_TB","DIACHI_TB","CUOC_TB","MUCUOCTB_ID",
                        "MUCUOC_TB","CUOC_DV","NGAY_SD","LOAITB_ID","TRANGTHAITB_ID","TRANGTHAI_TB","DOITUONG_ID","DICHVUVT_ID","DONVIQL_ID","EMAIL_TB","DIACHI_LD",
                        "NGAY_SN_TB","GHICHU_TB","DACTRUNG","TNC1_ID","TNC2_ID","TNC3_ID","TNC4_ID","NGAY_CAT","MANV_TC","MA_TUYEN","MA_LT","THUEBAO_CHA_ID",
                        "CHUQUAN_ID","VMP","TOCDO_ID","MUCCUOC_ID","CHUYENMANG_DB","PHANLOAIKH_ID","SOTHANG_CK","TEN_LOAIKH","TENNGANHNGHE","MA_PLKH"
                    ]

                    dsExel.forEach(item => {
                        Object.keys(item).forEach(key => item[key.toUpperCase()] = item[key])
                        item["NGAYLAP_HD"] = this.ddmmyyyy(item["NGAYLAP_HD"])
                        item["NGAY_HK"] = this.ddmmyyyy(item["NGAY_HK"])
                        item["NGAY_SN"] = this.ddmmyyyy(item["NGAY_SN"])
                        item["NGAYCAP"] = this.ddmmyyyy(item["NGAYCAP"])
                        item["NGAYHETHAN_GT"] = this.ddmmyyyy(item["NGAYHETHAN_GT"])
                        item["NGAY_SD"] = this.ddmmyyyy(item["NGAY_SD"])
                        item["NGAY_SN_TB"] = this.ddmmyyyy(item["NGAY_SN_TB"])
                        item["NGAY_SN_TB"] = this.ddmmyyyy(item["NGAY_SN_TB"])
                    })

                    this.dsDataXuatExcel = dsExel
                    this.$bvModal.show('modal-xuat-dulieu-thay-doi-thong-so-internet')
                }
                else
                {
                    this.$root.toastError("Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !");
                }
            }
            else
            {
                this.$root.toastError("Chưa có thông tin thuê bao cần xuất dữ liệu!.");
            }
        },
        async txtSoAo_KeyPress() {
            try {
                this.loading(true)
                this.hdtb_id = 0;
                await this.HienThiThongTin_SoAo();
            } finally {
                this.loading(false)
            }
        },
        async HienThiThongTin_SoAo() {

            let ds = [];
            let ds_adsl = [];

            if (this.trangthai_dl == 1) //Nếu lấy dữ liệu trong danh bạ.
            {
                if (this.maTinh == "TNH" || this.maTinh == "DNI")
                {
                    ds = await this.lay_db_adsl_malt_v2({
                        ma_lt: this.txtSoAo.Text,
                        donvi_dl_id: this.donvi_dl_id
                    })
                }
                else
                {
                    ds = await this.lay_db_adsl_malt({ma_lt: this.txtSoAo.Text})
                }

                if (ds.length > 0)
                {
                    this.cboDichVuVT.Value = ds[0]["dichvuvt_id"] + ""
                    this.thuebao_id = ds[0]["thuebao_id"]
                    this.txtMaTB.Text = ds[0]["ma_tb"]
                    this.txtTenTB.Text = ds[0]["ten_tb"]
                    this.txtDiaChiLD.Text = ds[0]["diachi_ld"]
                    this.cboLoaihinhTB.Value = ds[0]["loaitb_id"]
                    this.txtTrangThai.Text = ds[0]["trangthai_tb"]

                    ds_adsl = await this.lay_ds_db_adsl_theo_tbid(this.thuebao_id)
                    this.bras_cu_id = 0;
                    this.dslam_cu_id = 0;
                    this.port_cu_id = 0;
                    this.vci_vpi_cu_id = 0;

                    if (ds_adsl.length > 0)
                    {
                        this.txtSoMayTNMoi.Text = ds_adsl[0]["matb_tn"]
                        this.txtSoMayTN.Text = ds_adsl[0]["matb_tn"]
                        this.txtPortMDF.Text = ds_adsl[0]["port_mdf"]
                        this.txtVCIADSL.Text = ds_adsl[0]["vci"]
                        this.txtVPIADSL.Text = ds_adsl[0]["vpi"]
                        this.txtSlotADSL.Text = ds_adsl[0]["slot"]
                        this.txtPort.Text = ds_adsl[0]["port"]
                        this.txtMaTN.Text = "";
                        this.txtMaDoiCap.Text = "";

                        if (!!ds_adsl[0]["madoicap"])
                        {
                            this.madoicap = ds_adsl[0]["madoicap"]
                            this.txtMaDoiCap.Text = ds_adsl[0]["madoicap"]
                        }
                        else
                        {
                            this.madoicap = 0;
                        }

                        if (this.madoicap != 0)
                        {
                            let matn = await this.lay_somay_truycap(this.madoicap)

                            if (!!matn)
                            {
                                this.txtMaTN.Text = matn.somay
                            }
                        }

                        if (!!ds_adsl[0]["bras_id"])
                        {
                            this.cboBrasADSL.Value = ds_adsl[0]["bras_id"]
                            this.cboBrasADSLMoi.Value = ds_adsl[0]["bras_id"]
                            this.bras_cu_id = ds_adsl[0]["bras_id"]
                        }

                        if (!!ds_adsl[0]["dslam_id"])
                        {
                            this.cboDSLAM.Value = ds_adsl[0]["dslam_id"]
                            this.cboDSLAMMoi.Value = ds_adsl[0]["dslam_id"]
                            this.dslam_cu_id = ds_adsl[0]["dslam_id"]
                        }

                        this.txtSystem.Text = ds_adsl[0]["system"]
                        this.txtRack.Text = ds_adsl[0]["rack"]
                        this.txtShelf.Text = ds_adsl[0]["shelf"]
                        this.passwordMegaVNN = ds_adsl[0]["password"]

                        this.txtSelfid01.Text = ds_adsl[0]["selfid01"]
                        this.txtSelfid02.Text = ds_adsl[0]["selfid02"]
                        this.txtSelfid03.Text = ds_adsl[0]["selfid03"]
                        this.txtSelfid04.Text = ds_adsl[0]["selfid04"]

                        this.txtDslamTinhCu.Text = ds_adsl[0]["tendslam"]

                        let donViInfo = await this.lay_tramvt_theo_thuebao(this.thuebao_id, 2)
                        this.tramvt_id = !donViInfo ? 0 : donViInfo.donvi_id

                        if (!!ds_adsl[0]["tramtb_id"])
                        {
                            this.tramtb_id = ds_adsl[0]["tramtb_id"]
                        }

                        if (!!ds_adsl[0]["port_id"])
                        {
                            this.port_cu_id = ds_adsl[0]["port_id"]

                            let dslamtinh = await this.lay_tt_dslam_theo_port(this.port_cu_id)

                            if (!!dslamtinh)
                            {
                                this.txtIpCu.Text = !dslamtinh.ip ? "" : dslamtinh.ip;
                            }
                            else
                            {
                                this.txtIpCu.Text = "";
                            }

                            let portInfo = await this.lay_tt_vatly_theo_port_logic(this.port_cu_id)

                            if (!!portInfo)
                            {
                                this.txtSlotOLT_Cu.Text = portInfo["ten_card"]
                                this.txtPortOLT_Cu.Text = portInfo["vitri"]
                            }
                        }
                        else
                        {
                            this.port_cu_id = 0;
                            this.txtIpCu.Text = "";
                        }

                        this.vci_vpi_cu_id = !ds_adsl[0]["vci_vpi_id"] ? 0 : ds_adsl[0]["vci_vpi_id"]
                        this.SetButton(2);
                    }
                }
                else
                {
                    this.$root.toastError("Không tìm thấy thông tin về thuê bao: " + this.txtMaTB.Text + "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !");
                }
            }
            else  // Nếu lấy thông tin trong hợp đồng.
            {
                ds = await this.lay_hd_adsl_malt({ma_lt: this.txtSoAo.Text})

                if (ds.length > 0)
                {
                    this.txtMaTB.Text = ds[0]["ma_tb"]
                    this.txtTenTB.Text = ds[0]["ten_tb"]
                    this.txtDiaChiLD.Text = ds[0]["diachi_ld"]
                    this.hdtb_id = ds[0]["hdtb_id"]
                    this.cboDichVuVT.Value = ds[0]["dichvuvt_id"] + ""
                    this.cboLoaihinhTB.Value = ds[0]["loaitb_id"]
                }
                else
                {
                    this.$root.toastError("Không tìm thấy thông tin hợp đồng của thuê bao : " + this.txtMaTB.Text);
                    return;
                }

                ds_adsl = await this.lay_ds_hdtb_adsl_theo_hdtb_id(this.hdtb_id);

                if (ds_adsl.length > 0)
                {
                    this.txtSoMayTN.Text = ds_adsl[0]["matb_tn"]
                    this.txtSoMayTNMoi.Text = ds_adsl[0]["matb_tn"]
                    this.passwordMegaVNN = ds_adsl[0]["password"]
                    this.txtVCIADSL.Text = ds_adsl[0]["vci"]
                    this.txtVPIADSL.Text = ds_adsl[0]["vpi"]
                    this.txtSlotADSL.Text = ds_adsl[0]["slot"]
                    this.txtPort.Text = ds_adsl[0]["port"]

                    if (!!ds_adsl[0]["bras_id"])
                    {
                        this.cboBrasADSL.Value = ds_adsl[0]["bras_id"]
                        this.cboBrasADSLMoi.Value = ds_adsl[0]["bras_id"]
                        this.bras_cu_id = ds_adsl[0]["bras_id"]
                    }

                    if (!!ds_adsl[0]["dslam_id"])
                    {
                        this.cboDSLAM.Value = ds_adsl[0]["dslam_id"]
                        this.cboDSLAMMoi.Value = ds_adsl[0]["dslam_id"]
                        this.dslam_cu_id = ds_adsl[0]["dslam_id"]
                    }

                    this.txtSystem.Text = ds_adsl[0]["system"]
                    this.txtRack.Text = ds_adsl[0]["rack"]
                    this.txtShelf.Text = ds_adsl[0]["shelf"]
                    this.txtPortMDF.Text = ds_adsl[0]["port_mdf"]
                    this.txtSelfid01.Text = ds_adsl[0]["selfid01"]
                    this.txtSelfid02.Text = ds_adsl[0]["selfid02"]
                    this.txtSelfid03.Text = ds_adsl[0]["selfid03"]
                    this.txtSelfid04.Text = ds_adsl[0]["selfid04"]

                    let donViInfo = await this.lay_tramvt_theo_hdtb(this.hdtb_id, 2)
                    this.tramvt_id = !donViInfo ? 0 : donViInfo.donvi_id

                    if (!!ds_adsl[0]["tramtb_id"])
                    {
                        this.tramtb_id = ds_adsl[0]["tramtb_id"]
                    }

                    if (!!ds_adsl[0]["port_id"])
                    {
                        this.port_cu_id = ds_adsl[0]["port_id"]
                        let portInfo = await this.lay_tt_vatly_theo_port_logic(this.port_cu_id)

                        if (!!portInfo)
                        {
                            this.txtSlotOLT_Cu.Text = portInfo["ten_card"]
                            this.txtPortOLT_Cu.Text = portInfo["vitri"]
                        }

                        let dslamtinh = await this.lay_tt_dslam_theo_port(this.port_cu_id)

                        if (!!dslamtinh)
                        {
                            this.txtIpCu.Text = !dslamtinh.ip ? "" : dslamtinh.ip;
                        }
                        else
                        {
                            this.txtIpCu.Text = "";
                        }
                    }
                    else
                    {
                        this.port_cu_id = 0;
                        this.txtIpCu.Text = "";
                    }

                    this.vci_vpi_cu_id = !ds_adsl[0]["vci_vpi_id"] ? 0 : ds_adsl[0]["vci_vpi_id"]
                    this.SetButton(2);
                }
                else
                {
                    this.$root.toastError("Không tìm thấy thông tin về thuê bao: " + this.txtMaTB.Text + "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !");
                }
            }
        },
        async lay_hd_adsl_malt(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_hd_adsl_malt`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_db_adsl_malt(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_db_adsl_malt`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_db_adsl_malt_v2(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_db_adsl_malt_v2`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        tsbtnNhapMoi_Click() {
            if (this.tsbtnNhapMoi.Enabled)
            {
                this.SetButton(1)
            }
        },
        async frmThayDoiTSInternet_Load() {

            //this.propData = {"in_ma_tb":"canh479_cloudcam01","in_dichvuvt_id":"4","in_trangthai_dl":0}

            if (!!this.outData) {
                this.propData = this.outData
            }

            let phanvungId = this.$root.token.getPhanVungID()

            if (parseInt(phanvungId) == 32 /*LCU*/ 
                || parseInt(phanvungId) == 61 /*TNN*/
                || parseInt(phanvungId) == 44 /*QNI*/
                || parseInt(phanvungId) == 54 /*TGG*/) {
                this.chkBNM.Checked = true
            }

            if (!!this.propData)
            {
                if ([0, 1].includes(this.propData.in_trangthai_dl)) {
                    this.trangthai_dl = parseInt(this.propData.in_trangthai_dl)
                }

                if (!!this.propData.in_hdtb_id) {
                    this.hdtb_id = parseInt(this.propData.in_hdtb_id)
                }

                if (!!this.propData.in_ma_tb) {
                    this.txtMaTB.Text = this.propData.in_ma_tb
                }

                if (!!this.propData.in_ten_tb) {
                    this.txtTenTB.Text = this.propData.in_ten_tb
                }

                if (!!this.propData.in_diachi_ld) {
                    this.txtDiaChiLD.Text = this.propData.in_diachi_ld
                }

                if (!!this.propData.in_flagTSKT) {

                    let Tag = parseInt(this.propData.in_flagTSKT)

                    //Tag : + NULL : hiển thị 2 nút cấp mới, đồng bộ
                    //      + 1    : hiển thị nút đồng bộ
                    //      + 2    : hiển thị nút cấp mới
                    //      + 0    : không hiển thị nút nào

                    if (Tag == 1)
                    {
                        this.tsbtnCapNhat.Visible = true;
                        this.tsbtnDongBoDL.Visible = false;
                        this.moNut = true;
                    }
                    else if (Tag == 2)
                    {
                        this.tsbtnCapNhat.Visible = false;//đồng bộ lên cty dọc
                        this.tsbtnDongBoDL.Visible = true;
                    }
                    else if (Tag == 0)
                    {
                        this.tsbtnCapNhat.Visible = false;
                        this.tsbtnDongBoDL.Visible = false;
                    }
                }
                else
                {
                    this.tsbtnCapNhat.Visible = true;
                    this.tsbtnDongBoDL.Visible = true;
                }
            }

            this.donvi_dl_id = this.$root.context.user.getProperty('donvi_dl_id')
            this.maTinh = this.$root.token.getMaTinh()
            this.loading(true)

            try {
                this.ip = await this.getIp()

                this.cboBrasADSL.DataSource = await this.lay_ds_bras_adsl()
                this.cboBrasADSL.Value = this.cboBrasADSL.DataSource[0].bras_id

                this.cboBrasADSLMoi.DataSource = [...this.cboBrasADSL.DataSource]
                this.cboBrasADSLMoi.Value = this.cboBrasADSLMoi.DataSource[0].bras_id

                this.cboDichVuVT.DataSource = await this.lay_ds_dichvu_vt()
                this.cboDichVuVT.Value = this.cboDichVuVT.DataSource[0].dichvuvt_id + ""

                if (this.trangthai_dl == 1)
                {
                    if (!!this.propData)
                    {
                        let vdichvuvt_id = parseInt(this.propData.in_dichvuvt_id)
                        let vloaitb_id = parseInt(this.propData.in_loaitb_id)

                        if (vdichvuvt_id != -1 && vloaitb_id != -1)
                        {
                            this.cboDichVuVT.Value = vdichvuvt_id + "";
                            this.cboLoaihinhTB.Value = vloaitb_id;

                            try {
                                this.loading(true)
                                await this.HienThiThongTin();
                            } finally {
                                this.loading(false)
                            }
                        }
                        else
                        {
                            this.SetButton(0);
                        }
                    }
                    else
                    {
                        this.SetButton(0);
                    }
                }

                if (this.trangthai_dl == 0)
                {
                    this.rdoDanhBaHopDong.Value = 2

                    let vdichvuvt_id = -1
                    let vloaitb_id = -1

                    if (!!this.propData)
                    {
                        vdichvuvt_id = parseInt(this.propData.in_dichvuvt_id)
                        vloaitb_id = parseInt(this.propData.in_loaitb_id)
                    }

                    if (vdichvuvt_id != -1)
                    {
                        this.cboDichVuVT.Value = vdichvuvt_id + "";
                    }

                    if (vloaitb_id != -1)
                    {
                        this.cboLoaihinhTB.Value = vloaitb_id;
                    }

                    try {
                        this.loading(true)
                        await this.HienThiThongTin();
                    } finally {
                        this.loading(false)
                    }
                }

                let dsTS = await this.lay_ds_thamso_md_mats("USING_SERI_IMS")
                this.IS_USING_SERI_IMS = dsTS.length > 0

                dsTS = await this.lay_ds_thamso_md_mats("USING_CABMAN_2.0")
                this.IS_USING_CABMAN_V2 = dsTS.length > 0

                dsTS = await this.lay_ds_thamso_md_mats("CHUYEN_ALL_DV_BNM")
                let dongbo_bnm = -1

                if (dsTS.length > 0)
                {
                    dongbo_bnm = parseInt(dsTS[0]["ten_ts"])
                }

                this.chkChuyenCacDV.Visible = true

                if (dongbo_bnm == 1)
                {
                    this.chkChuyenCacDV.Checked = true
                    this.chkChuyenCacDV.Text = "Chuyển tất cả các dịch trên cổng";
                }
                else
                {
                    dsTS = await this.lay_ds_thamso_md_mats("CHUYEN_ALL_DV_CAP_BNM")

                    if (dsTS.length > 0)
                    {
                        dongbo_bnm = parseInt(dsTS[0]["ten_ts"])
                    }

                    if (dongbo_bnm == 1)
                    {
                        this.chkChuyenCacDV.Checked = true;
                        this.chkChuyenCacDV.Text = "Chuyển tất cả các dịch trên cùng cáp";
                        this.tsbtnDoiIPWanLan.Visible = true;
                    }
                    else
                    {
                        this.chkChuyenCacDV.Checked = false;
                        this.chkChuyenCacDV.Visible = false;
                    }
                }

                this.ktload = false

                let dsTS_HideSlid = await this.lay_ds_thamso_md_mats("HIDE_THAYDOISLTV")

                if (dsTS_HideSlid.length > 0)
                {
                    if (parseInt(dsTS_HideSlid[0]["ten_ts"]) == 1)
                    {
                        let _except_users = dsTS_HideSlid[0]["macdinh"]
                        let ma_nd = this.$root.token.getUserName().toLowerCase()

                        if (!!_except_users && _except_users.split(';').map(item => !item ? '' : item.toLowerCase()).includes(ma_nd))
                        {
                            this.tsbtnThayDoiSLID.Visible = true;
                        }
                        else
                        {
                            this.tsbtnThayDoiSLID.Visible = false;
                        }
                    }
                }
            }
            finally
            {
                this.loading(false)
            }
        },
        async txtMaTB_KeyPress() {

            this.hdtb_id = 0;
            this.thuebao_id = 0;

            try
            {
                this.loading(true)
                await this.HienThiThongTin();
            }
            finally
            {
                this.loading(false)
            }
        },
        async HienThiThongTin() {

            this.txtSlotOLT_Cu.Text = "";
            this.txtPortOLT_Cu.Text = "";
            this.txtSlotOLT_Moi.Text = "";
            this.txtPortOLT_Moi.Text = "";
            this.txtSubSlot.Text = "";
            this.txtSubSlot_Moi.Text = "";
            this.txtWANIP.Text = "";
            this.txtLANIP.Text = "";
            this.txtWANIP_Moi.Text = "";
            this.txtLANIP_Moi.Text = "";
            this.txtVrf.Text = "";
            this.txtLoaiKenh.Text = "";
            this.txtBrasTinhCu.Text = "";

            if (this.trangthai_dl == 1)
            {
                await this.HienThiThongTinDanhBa()
            }
            else
            {
                await this.HienThiThongTinHopDong()
            }
        },
        async HienThiThongTinHopDong() {

            let ds = []
            let ds_adsl = []

            if (this.maTinh == "HNI")
            {
                ds = await this.lay_ds_hopdong_theo_ma_tb_v2(this.txtMaTB.Text, 0, 0, 0, 0, 0, this.cboDichVuVT.Value);
            }
            else
            {
                ds = await this.lay_ds_hopdong_theo_ma_tb(this.txtMaTB.Text, 0, 0, 0, 0, 0, this.cboDichVuVT.Value, 0);
            }

            if (ds.length <= 0)
            {
                this.$root.toastError("Không tìm thấy thông tin hợp đồng của thuê bao : " + this.txtMaTB.Text)
                return
            }

            let kt = false;

            for (let i = 0; i < ds.length; i++)
            {
                if (ds[i]["tthd_id"] != this.TrangThaiHD.HOAN_THANH && ds[i]["tthd_id"] != this.TrangThaiHD.THOAITRA)
                {
                    if (ds[i]["loaihd_id"] == this.LoaiHopDong.DAT_MOI ||
                        ds[i]["loaihd_id"] == this.LoaiHopDong.DI_CHUYEN ||
                        ds[i]["loaihd_id"] == this.LoaiHopDong.CHUYENDOI_LH ||
                        ds[i]["loaihd_id"] == this.LoaiHopDong.KHOIPHUC_SD ||
                        ds[i]["loaihd_id"] == this.LoaiHopDong.THAY_DOI_TOCDO_ADSL ||
                        ds[i]["loaihd_id"] == this.LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
                    {
                        this.txtMaTB.Text = ds[i]["ma_tb"];
                        this.txtTenTB.Text = ds[i]["ten_tb"];
                        this.txtDiaChiLD.Text = ds[i]["diachi_ld"];
                        this.hdtb_id = ds[i]["hdtb_id"];
                        this.cboDichVuVT.Value = ds[i]["dichvuvt_id"] + "";
                        this.cboLoaihinhTB.Value = ds[i]["loaitb_id"];

                        if (ds[i]["loaihd_id"] == this.LoaiHopDong.THAY_DOI_TOCDO_ADSL &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_AON_GPON &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_GPON_AON &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_AON_XGSPON &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_GPON_XGSPON &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_XGSPON_AON &&
                            ds[i]["kieuld_id"] != this.KieuLapDat.THAYDOI_CONGNGHE_XGSPON_GPON)
                        {
                            this.$root.toastError("*Bạn chỉ được đổi port của thuê bao với loại HĐ/PL : \n   + Lắp đặt mới \n   + Di chuyển \n   + Chuyển đổi loại hình \n   + Khôi phục thuê bao thanh lý \n   + Đổi tốc độ Internet (Thay đổi công nghệ)");
                            return;
                        }

                        kt = true;

                        break;
                    }
                }
            }

            if (kt == false)
            {
                this.$root.toastError("Bạn không được đổi port của thuê bao có trạng thái hợp đồng là : Thoái trả hoặc Đã hoàn thành");
                return;
            }

            let dichvuvt_id = this.cboDichVuVT.Value

            if (dichvuvt_id == this.DichVuVienThong.ADSL || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
            {
                ds_adsl = await this.lay_ds_hdtb_adsl_theo_hdtb_id(this.hdtb_id);
            }
            else if (dichvuvt_id == this.DichVuVienThong.IMS || dichvuvt_id == this.DichVuVienThong.IMS_DOITAC)
            {
                ds_adsl = await this.lay_ds_hdtb_ims_theo_hdtb_id(this.hdtb_id);
            }
            else if (dichvuvt_id == this.DichVuVienThong.MEGAWAN || dichvuvt_id == this.DichVuVienThong.METRONET || dichvuvt_id == this.DichVuVienThong.MGW_DOITAC)
            {
                ds_adsl = await this.lay_ds_hdtb_con_theo_hdtbid(this.hdtb_id, dichvuvt_id);
            }
            else if (dichvuvt_id == this.DichVuVienThong.TSL || dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
            {
                ds_adsl = await this.lay_ds_hdtb_con_theo_hdtbid(this.hdtb_id, this.DichVuVienThong.TSL);
            }

            this.btnDoiMK_IMS.Enabled = false

            let vloaitb_id = this.cboLoaihinhTB.Value

            if (dichvuvt_id == this.DichVuVienThong.IMS)
            {
                if (vloaitb_id != this.LoaiHinhTB.IMS_POTS && vloaitb_id != this.LoaiHinhTB.IMS_SIP)
                {
                    let dauSoIms = await this.layDauSoIms()
                    let soMay = `${!dauSoIms ? '' : dauSoIms}${this.txtMaTB.Text}`

                    // Nếu khong phải IMS POTS và SIP thì gọi hàm cũ
                    let sub = await VTNService.LayThongTinThueBao(this, soMay);

                    if (sub == null)
                    {
                        this.txtPass_IMS.Text = "";
                        this.txtPass_IMS_Moi.Text = "";
                    }
                    else
                    {
                        this.txtPass_IMS.Text = sub.PASSWORD;
                        this.txtPass_IMS_Moi.Text = sub.PASSWORD;
                        this.btnDoiMK_IMS.Enabled = true;
                    }
                }
                else
                {
                    if (ds_adsl.length > 0)
                    {
                        if (!ds_adsl[0]["password"])
                        {
                            this.txtPass_IMS.Text = "";
                            this.txtPass_IMS_Moi.Text = "";
                        }
                        else
                        {

                            this.txtPass_IMS.Text = ds_adsl[0]["password"]
                            this.txtPass_IMS_Moi.Text = ds_adsl[0]["password"]
                            this.btnDoiMK_IMS.Enabled = true;
                        }
                    }
                    else
                    {
                        this.txtPass_IMS.Text = "";
                        this.txtPass_IMS_Moi.Text = "";
                    }
                }
            }
            else
            {
                this.txtPass_IMS.Text = "";
                this.txtPass_IMS_Moi.Text = "";
            }

            if (ds_adsl.length > 0)
            {
                if (dichvuvt_id == this.DichVuVienThong.ADSL || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
                {
                    this.txtUsernameMoi.Text = ds_adsl[0]["username"]
                    this.txtUsernameCu.Text = ds_adsl[0]["username"]

                    let tocDoInfo = await this.lay_tt_tocdo_dv_adsl(ds_adsl[0]["tocdo_id"])
                    this.txtTocDo.Text = !tocDoInfo ? null : tocDoInfo["thuonghieu"]
                }

                if (dichvuvt_id == this.DichVuVienThong.IMS || dichvuvt_id == this.DichVuVienThong.IMS_DOITAC)
                {
                    this.txtTocDo.Text = "";
                }

                if (dichvuvt_id == this.DichVuVienThong.MEGAWAN ||
                    dichvuvt_id == this.DichVuVienThong.METRONET ||
                    dichvuvt_id == this.DichVuVienThong.TSL ||
                    dichvuvt_id == this.DichVuVienThong.MGW_DOITAC ||
                    dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
                {
                    let tocDoInfo = await this.lay_tt_tocdo_dv_kenh(ds_adsl[0]["tocdo_id"])
                    this.txtTocDo.Text = !tocDoInfo ? null : tocDoInfo["tocdo"]
                }

                // Dịch vụ khác TSL
                if (dichvuvt_id != this.DichVuVienThong.TSL && dichvuvt_id != this.DichVuVienThong.TSL_DOITAC)
                {
                    if (dichvuvt_id == this.DichVuVienThong.ADSL || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
                    {
                        this.txtSoMayTN.Text = ds_adsl[0]["matb_tn"]
                        this.txtSoMayTNMoi.Text = ds_adsl[0]["matb_tn"]
                        this.passwordMegaVNN = ds_adsl[0]["password"]
                    }
                    else if (dichvuvt_id == this.DichVuVienThong.IMS || dichvuvt_id == this.DichVuVienThong.IMS_DOITAC)
                    {
                        this.txtSoMayTN.Text = "";
                        this.txtSoMayTNMoi.Text = "";
                        this.passwordMegaVNN = "";
                    }
                    else if (dichvuvt_id == this.DichVuVienThong.MEGAWAN ||
                             dichvuvt_id == this.DichVuVienThong.METRONET ||
                             dichvuvt_id == this.DichVuVienThong.MGW_DOITAC)
                    {
                        this.txtSoMayTN.Text = ds_adsl[0]["matb_tn"]
                        this.txtSoMayTNMoi.Text = ds_adsl[0]["matb_tn"]
                        this.passwordMegaVNN = "";
                    }

                    if (dichvuvt_id == this.DichVuVienThong.IMS)
                    {
                        this.sl_cuocgoi = ds_adsl[0]["sl_cuocgoi"]
                    }
                    else
                    {
                        this.sl_cuocgoi = "";
                    }

                    this.txtVCIADSL.Text = ds_adsl[0]["vci"]
                    this.txtVPIADSL.Text = ds_adsl[0]["vpi"]
                    this.txtSlotADSL.Text = ds_adsl[0]["slot"]
                    this.txtPort.Text = ds_adsl[0]["port"]

                    if (!!ds_adsl[0]["port_id"])
                    {
                        let portInfo = await this.lay_tt_vatly_theo_port_logic(ds_adsl[0]["port_id"])

                        if (!!portInfo)
                        {
                            this.txtSlotOLT_Cu.Text = portInfo["ten_card"]
                            this.txtPortOLT_Cu.Text = portInfo["vitri"]
                            this.txtSubSlot.Text = portInfo["sub_slot"]
                        }
                    }

                    if (dichvuvt_id != this.DichVuVienThong.MEGAWAN &&
                        dichvuvt_id != this.DichVuVienThong.METRONET &&
                        dichvuvt_id != this.DichVuVienThong.MGW_DOITAC)
                    {
                        if (!!ds_adsl[0]["bras_id"])
                        {
                            this.cboBrasADSL.Value = ds_adsl[0]["bras_id"]
                            this.cboBrasADSLMoi.Value = ds_adsl[0]["bras_id"]
                            this.bras_cu_id = ds_adsl[0]["bras_id"]
                        }

                        if (!!ds_adsl[0]["dslam_id"])
                        {
                            this.cboDSLAM.Value = ds_adsl[0]["dslam_id"]
                            this.cboDSLAMMoi.Value = ds_adsl[0]["dslam_id"]
                            this.dslam_cu_id = ds_adsl[0]["dslam_id"]
                        }
                    }
                    else
                    {
                        if (!!ds_adsl[0]["bras_id"])
                        {
                            this.cboBrasADSL.Value = ds_adsl[0]["bras_id"]
                            this.cboBrasADSLMoi.Value = ds_adsl[0]["bras_id"]
                            this.bras_cu_id = ds_adsl[0]["bras_id"]
                        }

                        if (!!ds_adsl[0]["dslam_cha_id"])
                        {
                            this.cboDSLAM.Value = ds_adsl[0]["dslam_cha_id"]
                            this.cboDSLAMMoi.Value = ds_adsl[0]["dslam_cha_id"]
                            this.dslam_cu_id = ds_adsl[0]["dslam_cha_id"]
                        }

                        if (!!ds_adsl[0]["dslam_id"])
                        {
                            this.dslam_tinh_id = ds_adsl[0]["dslam_id"]
                        }
                        else
                        {
                            this.dslam_tinh_id = 0
                        }

                        this.txtLANIP_Moi.Text = ds_adsl[0]["lan_ip"]
                        this.txtLANIP.Text = ds_adsl[0]["lan_ip"]
                        this.txtWANIP_Moi.Text = ds_adsl[0]["wan_ip"]
                        this.txtWANIP.Text = ds_adsl[0]["wan_ip"]
                        this.txtVrf.Text = ds_adsl[0]["vrf"]

                        let loaiKenhInfo = await this.lay_tt_loai_kenh(ds_adsl[0]["loaikenh_id"])
                        this.txtLoaiKenh.Text = !loaiKenhInfo ? null : loaiKenhInfo.loai_kenh
                    }

                    if (!!ds_adsl[0]["lay_sn"])
                    {
                        this.chkSLID_CU.Checked = false;
                        this.chkSR_MD_CU.Checked = false;
                        this.chkSR_MD_MOI.Checked = false;
                        this.chkSLID_MOI.Checked = false;

                        let lay_sn = ds_adsl[0]["lay_sn"] + '';

                        if (lay_sn == "1")
                        {
                            this.chkSR_MD_CU.Checked = true;
                            this.txtSeriModem.Enabled = true;
                            this.chkSLID_CU.Checked = false;
                            this.txtSLID.Enabled = false;
                        }
                        else
                        {
                            this.chkSLID_CU.Checked = true;
                            this.txtSLID.Enabled = true;
                            this.chkSR_MD_CU.Checked = false;
                            this.txtSeriModem.Enabled = false;
                        }
                    }
                    else
                    {
                        this.chkSR_MD_CU.Checked = false;
                        this.chkSLID_CU.Checked = false;
                        this.chkSR_MD_MOI.Checked = false;
                        this.chkSLID_MOI.Checked = false;
                        this.txtSeriModem.Enabled = true;
                        this.txtSLID.Enabled = true;
                    }

                    this.txtSystem.Text = ds_adsl[0]["system"]
                    this.txtRack.Text = ds_adsl[0]["rack"]
                    this.txtShelf.Text = ds_adsl[0]["shelf"]
                    this.txtPortMDF.Text = ds_adsl[0]["port_mdf"]
                    this.txtSoAo.Text = ds_adsl[0]["ma_lt"]
                    this.txtSelfid01.Text = ds_adsl[0]["selfid01"]
                    this.txtSelfid02.Text = ds_adsl[0]["selfid02"]
                    this.txtSelfid03.Text = ds_adsl[0]["selfid03"]
                    this.txtSelfid04.Text = ds_adsl[0]["selfid04"]

                    let donViInfo = await this.lay_tramvt_theo_hdtb(this.hdtb_id, 2)
                    this.tramvt_id = !donViInfo ? 0 : donViInfo.donvi_id

                    if (ds_adsl[0]["tramtb_id"])
                    {
                        this.tramtb_id = ds_adsl[0]["tramtb_id"];
                    }

                    if (!!ds_adsl[0]["port_id"])
                    {
                        this.port_cu_id = ds_adsl[0]["port_id"]

                        let dslamtinh = await this.lay_tt_dslam_theo_port(this.port_cu_id)

                        if (!!dslamtinh)
                        {
                            this.txtIpCu.Text = !dslamtinh.ip ? "" : dslamtinh.ip;
                            this.txtDslamTinhCu.Text = !dslamtinh.tendslam ? "" : dslamtinh.tendslam;
                            this.txtBrasTinhCu.Text = !dslamtinh.tenbras ? "" : dslamtinh.tenbras;
                        }
                        else
                        {
                            this.txtIpCu.Text = "";
                            this.txtDslamTinhCu.Text = "";
                        }
                    }
                    else
                    {
                        this.port_cu_id = 0;
                        this.txtIpCu.Text = "";
                        this.txtDslamTinhCu.Text = "";
                    }

                    this.vci_vpi_cu_id = !!ds_adsl[0]["vci_vpi_id"] ? ds_adsl[0]["vci_vpi_id"] : 0;

                    if (dichvuvt_id == this.DichVuVienThong.ADSL || (this.IS_USING_SERI_IMS && dichvuvt_id == this.DichVuVienThong.IMS))
                    {
                        this.txtSLID.Text = ds_adsl[0]["slid"]
                        this.txtSeriModem.Text = ds_adsl[0]["seri_md"]
                    }
                }
                else // Dịch vụ TSL
                {
                    let  daucuoi = parseInt(this.rdoDiemDauCuoi.Value)

                    for (let i = 0; i < ds_adsl.length; i++)
                    {
                        if (ds_adsl[i]["daucuoi_id"] == daucuoi)
                        {
                            this.txtDiaChiLD.Text = ds_adsl[i]["diachi"]
                            this.txtPortMDF.Text = ds_adsl[i]["port_mdf"]
                            this.txtAdsl_Port_Cu.Text = ds_adsl[i]["adsl_port"]
                            this.txtONU_PASS_Cu.Text = ds_adsl[i]["onu_password"]
                            this.txtVCIADSL.Text = ds_adsl[i]["vci"]
                            this.txtVPIADSL.Text = ds_adsl[i]["vpi"]
                            this.txtSlotADSL.Text = ds_adsl[i]["slot_tinh"]
                            this.txtPort.Text = ds_adsl[i]["port_tinh"]
                            this.txtMaTN.Text = ds_adsl[i]["ma_lt"]

                            if (!!ds_adsl[i]["port_id"])
                            {
                                let portInfo = await this.lay_tt_vatly_theo_port_logic(ds_adsl[i]["port_id"]);

                                if (!!portInfo)
                                {
                                    this.txtSlotOLT_Cu.Text = portInfo["ten_card"]
                                    this.txtPortOLT_Cu.Text = portInfo["vitri"]
                                    this.txtSubSlot.Text = portInfo["sub_slot"]
                                }
                            }

                            this.txtMaTN.Text = "";
                            this.txtSoMayTNMoi.Text = "";
                            this.txtSoMayTN.Text = "";

                            if (!!ds_adsl[i]["bras_tinh_id"])
                            {
                                this.cboBrasADSL.Value = ds_adsl[i]["bras_tinh_id"]
                                this.cboBrasADSLMoi.Value = ds_adsl[i]["bras_tinh_id"]
                                this.bras_cu_id = ds_adsl[i]["bras_tinh_id"]
                            }

                            if (!!ds_adsl[i]["dslam_cha_id"])
                            {
                                this.cboDSLAM.Value = ds_adsl[i]["dslam_cha_id"]
                                this.cboDSLAMMoi.Value = ds_adsl[i]["dslam_cha_id"]
                                this.dslam_cu_id = ds_adsl[i]["dslam_cha_id"];
                            }

                            this.dslam_tinh_id = !!ds_adsl[i]["dslam_tinh_id"] ? ds_adsl[i]["dslam_tinh_id"] : 0;
                            this.passwordMegaVNN = "";
                            this.txtSystem.Text = ds_adsl[i]["system"]
                            this.txtRack.Text = ds_adsl[i]["rack"]
                            this.txtShelf.Text = ds_adsl[i]["shelf"]
                            this.txtSelfid01.Text = ds_adsl[i]["selfid01"]
                            this.txtSelfid02.Text = ds_adsl[i]["selfid02"]
                            this.txtSelfid03.Text = ds_adsl[i]["selfid03"]
                            this.txtSelfid04.Text = ds_adsl[i]["selfid04"]

                            let donViInfo = await this.lay_tramvt_theo_hdtb(this.hdtb_id, daucuoi)
                            this.tramvt_id = !donViInfo ? 0 : donViInfo.donvi_id

                            if (ds_adsl[i]["tramtb_tinh_id"])
                            {
                                this.tramtb_id = ds_adsl[i]["tramtb_tinh_id"];
                            }

                            if (!!ds_adsl[i]["port_id"])
                            {
                                this.port_cu_id = ds_adsl[i]["port_id"]

                                let dslamtinh = await this.lay_tt_dslam_theo_port(this.port_cu_id)

                                if (!!dslamtinh && !!dslamtinh.ip)
                                {
                                    this.txtIpCu.Text = dslamtinh.ip;
                                }
                                else
                                {
                                    this.txtIpCu.Text = "";
                                }
                            }
                            else
                            {
                                this.port_cu_id = 0;
                                this.txtIpCu.Text = "";
                            }

                            this.vci_vpi_cu_id = !!ds_adsl[i]["vci_vpi_id"] ? ds_adsl[i]["vci_vpi_id"] : 0;
                        }
                    }
                }

                if (!!ds_adsl[0]["congnghe_id"])
                {
                    let congNgheInfo = await this.lay_tt_congnghe(ds_adsl[0]["congnghe_id"])
                    this.txtCongNghe.id = ds_adsl[0]["congnghe_id"];
                    this.txtCongNghe.Text = congNgheInfo.congnghe
                }
                else
                {
                    this.txtCongNghe.id = null;
                    this.txtCongNghe.Text = null
                }

                this.SetButton(2);
            }
            else
            {
                this.$root.toastError("Không tìm thấy thông tin về thuê bao: " + this.txtMaTB.Text + "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !");
            }
        },
        async HienThiThongTinDanhBa() {

            try {
                let ds = []
                let ds_adsl = []

                if (this.maTinh == "TNH" && this.maTinh == "DNI")
                {
                    let donvi_dl_id = this.$root.context.user.getProperty('donvi_dl_id')
                    ds = await this.lay_danhba_theo_matb(this.txtMaTB.Text, this.cboDichVuVT.Value, donvi_dl_id)
                }
                else if (this.maTinh == "HNI")
                {
                    ds = await this.lay_db_theo_matb(this.txtMaTB.Text, 0, 0)
                }
                else
                {
                    ds = await this.lay_danhba_theo_matb(this.txtMaTB.Text, this.cboDichVuVT.Value, 0)
                }

                if (ds.length <= 0)
                {
                    let maTbInput =  this.txtMaTB.Text
                    this.SetButton(0)
                    this.$root.toastError("Không tìm thấy thông tin về thuê bao: " + this.txtMaTB.Text + "\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !")
                    this.txtMaTB.Text = maTbInput
                    return
                }

                if (ds.length > 1)
                {
                    ds = ds.filter(x => x.ma_tb.toUpperCase() == this.txtMaTB.Text.toUpperCase())

                    // ds.forEach(item => {
                    //     Object.keys(item).forEach(key => item[key.toUpperCase()] = item[key])
                    // })

                    // this.loading(false)
                    // let result = await this.$refs.thuebaocungma.show(ds)
                    // ds = [result.itemSelected]
                    // this.loading(true)
                }

                this.txtMaTB.Text = ds[0]["ma_tb"]
                this.cboDichVuVT.Value = ds[0]["dichvuvt_id"] + ""
                this.thuebao_id = ds[0]["thuebao_id"]
                this.txtTenTB.Text = ds[0]["ten_tb"]
                this.txtDiaChiLD.Text = ds[0]["diachi_ld"]
                this.cboLoaihinhTB.Value = ds[0]["loaitb_id"]
                this.txtTrangThai.Text = ds[0]["trangthai_tb"]

                let dichvuvt_id = parseInt(this.cboDichVuVT.Value)

                if (dichvuvt_id == this.DichVuVienThong.ADSL || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
                {
                    ds_adsl = await this.lay_ds_db_adsl_theo_tbid(this.thuebao_id)
                }
                else if (dichvuvt_id == this.DichVuVienThong.IMS || dichvuvt_id == this.DichVuVienThong.IMS_DOITAC)
                {
                    ds_adsl = await this.lay_ds_db_ims_theo_tbid(this.thuebao_id)
                }
                else if (dichvuvt_id == this.DichVuVienThong.MEGAWAN ||
                        dichvuvt_id == this.DichVuVienThong.METRONET ||
                        dichvuvt_id == this.DichVuVienThong.MGW_DOITAC ||
                        dichvuvt_id == this.DichVuVienThong.TSL ||
                        dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
                {
                    ds_adsl = await this.lay_ds_db_con_theo_tbid(this.thuebao_id, dichvuvt_id)
                }

                this.btnDoiMK_IMS.Enabled = false

                let vloaitb_id = parseInt(this.cboLoaihinhTB.Value)

                if (dichvuvt_id == this.DichVuVienThong.IMS)
                {
                    if (vloaitb_id != this.LoaiHinhTB.IMS_POTS && vloaitb_id != this.LoaiHinhTB.IMS_SIP)
                    {
                        let dauSoIms = await this.layDauSoIms()
                        let soMay = `${!dauSoIms ? '' : dauSoIms}${this.txtMaTB.Text}`

                        // Nếu khong phải IMS POTS và SIP thì gọi hàm cũ
                        let sub = await VTNService.LayThongTinThueBao(this, soMay);

                        if (sub == null)
                        {
                            this.txtPass_IMS.Text = "";
                            this.txtPass_IMS_Moi.Text = "";
                        }
                        else
                        {
                            this.txtPass_IMS.Text = sub.PASSWORD
                            this.txtPass_IMS_Moi.Text = sub.PASSWORD
                            this.btnDoiMK_IMS.Enabled = true
                        }
                    }
                    else
                    {
                        if (ds_adsl.length > 0)
                        {
                            if (!ds_adsl[0]["password"])
                            {
                                this.txtPass_IMS.Text = "";
                                this.txtPass_IMS_Moi.Text = "";
                                this.btnDoiMK_IMS.Enabled = true;
                            }
                            else
                            {
                                this.txtPass_IMS.Text = ds_adsl[0]["password"]
                                this.txtPass_IMS_Moi.Text = ds_adsl[0]["password"]
                                this.btnDoiMK_IMS.Enabled = true;
                            }
                        }
                        else
                        {
                            this.txtPass_IMS.Text = "";
                            this.txtPass_IMS_Moi.Text = "";
                        }
                    }
                }
                else
                {
                    this.txtPass_IMS.Text = "";
                    this.txtPass_IMS_Moi.Text = "";
                }

                this.bras_cu_id = 0;
                this.dslam_cu_id = 0;
                this.port_cu_id = 0;
                this.vci_vpi_cu_id = 0;

                if (ds_adsl.length > 0)
                {
                    if (dichvuvt_id == this.DichVuVienThong.ADSL || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
                    {
                        let tocDoInfo = await this.lay_tt_tocdo_dv_adsl(ds_adsl[0]["tocdo_id"])
                        this.txtTocDo.Text = !tocDoInfo ? null : tocDoInfo["thuonghieu"]
                        this.txtUsernameMoi.Text = ds_adsl[0]["username"]
                        this.txtUsernameCu.Text = ds_adsl[0]["username"]
                        this.txtDiaChiMacMoi.Text = ds_adsl[0]["mac_addr"]
                        this.txtDiachiMacCu.Text = ds_adsl[0]["mac_addr"]
                    }

                    if (dichvuvt_id == this.DichVuVienThong.IMS || dichvuvt_id == this.DichVuVienThong.IMS_DOITAC)
                    {
                        this.txtTocDo.Text = "";
                    }

                    if (dichvuvt_id == this.DichVuVienThong.MEGAWAN ||
                            dichvuvt_id == this.DichVuVienThong.METRONET ||
                            dichvuvt_id == this.DichVuVienThong.TSL ||
                            dichvuvt_id == this.DichVuVienThong.MGW_DOITAC ||
                            dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
                    {
                        let tocDoInfo = await this.lay_tt_tocdo_dv_kenh(ds_adsl[0]["tocdo_id"])
                        this.txtTocDo.Text = !tocDoInfo ? null : tocDoInfo["tocdo"]
                    }

                    if (dichvuvt_id != this.DichVuVienThong.TSL && dichvuvt_id != this.DichVuVienThong.TSL_DOITAC)
                    {
                        this.txtSoMayTNMoi.Text = ds_adsl[0]["matb_tn"]
                        this.txtSoMayTN.Text = ds_adsl[0]["matb_tn"]
                        this.txtPortMDF.Text = ds_adsl[0]["port_mdf"]
                        this.txtAdsl_Port_Cu.Text = ds_adsl[0]["adsl_port"]

                        if (dichvuvt_id == this.DichVuVienThong.IMS)
                        {
                            this.sl_cuocgoi = ds_adsl[0]["sl_cuocgoi"]
                        }
                        else
                        {
                            this.sl_cuocgoi = "";
                        }

                        this.txtONU_PASS_Cu.Text = ds_adsl["onu_password"]
                        this.txtOnuPassAdsl.Text = ds_adsl[0]["onu_password_cu"]
                        this.txtVCIADSL.Text = ds_adsl[0]["vci"]
                        this.txtVPIADSL.Text = ds_adsl[0]["vpi"]
                        this.txtSlotADSL.Text = ds_adsl[0]["slot"]
                        this.txtPort.Text = ds_adsl[0]["port"]
                        this.txtSoAo.Text = ds_adsl[0]["ma_lt"]
                        this.txtMaDoiCap.Text = "";

                        if (!!ds_adsl[0]["madoicap"])
                        {
                            this.txtMaDoiCap.Text = ds_adsl[0]["madoicap"]
                            this.madoicap = parseInt(ds_adsl[0]["madoicap"])
                        }
                        else
                        {
                            this.madoicap = 0;
                        }

                        if (!!ds_adsl[0]["port_id"])
                        {
                            let portInfo = await this.lay_tt_vatly_theo_port_logic(ds_adsl[0]["port_id"])

                            if (!!portInfo)
                            {
                                this.txtSlotOLT_Cu.Text = portInfo["ten_card"]
                                this.txtPortOLT_Cu.Text = portInfo["vitri"]
                                this.txtSubSlot.Text = portInfo["sub_slot"]
                            }
                        }

                        this.thamso_dslam = ds_adsl[0]["thamso"]

                        this.txtMaTN.Text = "";

                        if (this.madoicap != 0)
                        {

                            let matn = await this.lay_somay_truycap(this.madoicap)

                            if (!!matn)
                            {
                                this.txtMaTN.Text = matn.somay
                            }
                        }

                        if (dichvuvt_id != this.DichVuVienThong.MEGAWAN &&
                            dichvuvt_id != this.DichVuVienThong.METRONET &&
                            dichvuvt_id != this.DichVuVienThong.MGW_DOITAC)
                        {
                            if (!!ds_adsl[0]["bras_id"])
                            {
                                this.cboBrasADSL.Value = ds_adsl[0]["bras_id"]
                                this.cboBrasADSLMoi.Value = ds_adsl[0]["bras_id"]
                                this.bras_cu_id = ds_adsl[0]["bras_id"]
                            }

                            if (!!ds_adsl[0]["dslam_id"])
                            {
                                this.cboDSLAM.Value = ds_adsl[0]["dslam_id"]
                                this.cboDSLAMMoi.Value = ds_adsl[0]["dslam_id"]
                                this.dslam_cu_id = ds_adsl[0]["dslam_id"]
                            }

                            if (!!ds_adsl[0]["dslam_tinh_id"])
                            {
                                this.dslam_tinh_id = ds_adsl[0]["dslam_tinh_id"]
                            }
                            else
                            {
                                this.dslam_tinh_id = 0;
                            }

                            this.passwordMegaVNN = ds_adsl[0]["password"]
                            this.txtDslamTinhCu.Text = ds_adsl[0]["tendslam"]

                            let dslamInfo = await this.lay_tt_dslam(this.dslam_tinh_id)

                            if (!!dslamInfo)
                            {
                                this.txtThongTinCTCu.Text = dslamInfo.ttct
                            }
                            else
                            {
                                this.txtThongTinCTCu.Text = "";
                            }
                        }
                        else
                        {
                            if (!!ds_adsl[0]["bras_id"])
                            {
                                this.cboBrasADSL.Value = ds_adsl[0]["bras_id"];
                                this.cboBrasADSLMoi.Value = ds_adsl[0]["bras_id"];
                                this.bras_cu_id = ds_adsl[0]["bras_id"]
                            }

                            if (!!ds_adsl[0]["dslam_cha_id"])
                            {
                                this.cboDSLAM.Value = ds_adsl[0]["dslam_cha_id"];
                                this.cboDSLAMMoi.Value = ds_adsl[0]["dslam_cha_id"];
                                this.dslam_cu_id = ds_adsl[0]["dslam_cha_id"]
                            }

                            if (!!ds_adsl[0]["dslam_id"])
                            {
                                this.dslam_tinh_id = ds_adsl[0]["dslam_id"]
                            }
                            else
                            {
                                this.dslam_tinh_id = 0;
                            }

                            this.passwordMegaVNN = "";
                            this.txtDslamTinhCu.Text = ds_adsl[0]["tendslam"];
                            this.txtVrf.Text = ds_adsl[0]["vrf"];

                            let loaiKenhInfo = await this.lay_tt_loai_kenh(ds_adsl[0]["loaikenh_id"])
                            this.txtLoaiKenh.Text = !loaiKenhInfo ? null : loaiKenhInfo.loai_kenh
                        }

                        if (dichvuvt_id == this.DichVuVienThong.MEGAWAN
                            || dichvuvt_id == this.DichVuVienThong.METRONET
                            || dichvuvt_id == this.DichVuVienThong.ADSL
                            || dichvuvt_id == this.DichVuVienThong.MGW_DOITAC
                            || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
                        {
                            this.txtLANIP_Moi.Text = ds_adsl[0]["lan_ip"];
                            this.txtLANIP.Text = ds_adsl[0]["lan_ip"];
                            this.txtWANIP_Moi.Text = ds_adsl[0]["wan_ip"];
                            this.txtWANIP.Text = ds_adsl[0]["wan_ip"];
                        }

                        if (!!ds_adsl[0]["lay_sn"])
                        {
                            this.chkSLID_CU.Checked = false;
                            this.chkSR_MD_CU.Checked = false;
                            this.chkSR_MD_MOI.Checked = false;
                            this.chkSLID_MOI.Checked = false;

                            let lay_sn = ds_adsl[0]["lay_sn"] + '';

                            if (lay_sn == "1")
                            {
                                this.chkSR_MD_CU.Checked = true;
                                this.txtSeriModem.Enabled = true;
                                this.chkSLID_CU.Checked = false;
                                this.txtSLID.Enabled = false;
                            }
                            else
                            {
                                this.chkSLID_CU.Checked = true;
                                this.txtSLID.Enabled = true;
                                this.chkSR_MD_CU.Checked = false;
                                this.txtSeriModem.Enabled = false;
                            }
                        }
                        else
                        {
                            this.chkSR_MD_CU.Checked = false;
                            this.chkSLID_CU.Checked = false;
                            this.chkSR_MD_MOI.Checked = false;
                            this.chkSLID_MOI.Checked = false;
                            this.txtSeriModem.Enabled = true;
                            this.txtSLID.Enabled = true;
                        }

                        this.txtSystem.Text = ds_adsl[0]["system"];
                        this.txtRack.Text = ds_adsl[0]["rack"];
                        this.txtShelf.Text = ds_adsl[0]["shelf"];

                        this.txtSelfid01.Text = ds_adsl[0]["selfid01"];
                        this.txtSelfid02.Text = ds_adsl[0]["selfid02"];
                        this.txtSelfid03.Text = ds_adsl[0]["selfid03"];
                        this.txtSelfid04.Text = ds_adsl[0]["selfid04"];

                        let donViInfo = await this.lay_tramvt_theo_thuebao(this.thuebao_id, 2)
                        this.tramvt_id = !donViInfo ? 0 : donViInfo.donvi_id

                        if (!!ds_adsl[0]["tramtb_id"])
                        {
                            this.tramtb_id = ds_adsl[0]["tramtb_id"]
                        }

                        if (!!ds_adsl[0]["port_id"])
                        {
                            this.port_cu_id = ds_adsl[0]["port_id"]

                            let dslamtinh = await this.lay_tt_dslam_theo_port(this.port_cu_id)

                            if (!!dslamtinh && !!dslamtinh.ip)
                            {
                                this.txtIpCu.Text = dslamtinh.ip;
                            }
                            else
                            {
                                this.txtIpCu.Text = "";
                            }

                            if (!!dslamtinh && !!dslamtinh.tenbras)
                            {
                                this.txtBrasTinhCu.Text = dslamtinh.tenbras;
                            }
                        }
                        else
                        {
                            this.port_cu_id = 0;
                            this.txtIpCu.Text = "";
                        }

                        if (!!ds_adsl[0]["vci_vpi_id"])
                        {
                            this.vci_vpi_cu_id = ds_adsl[0]["vci_vpi_id"]
                        }
                        else
                        {
                            this.vci_vpi_cu_id = 0;
                        }

                        if (dichvuvt_id == this.DichVuVienThong.ADSL || (this.IS_USING_SERI_IMS && dichvuvt_id == this.DichVuVienThong.IMS))
                        {
                            this.txtSLID.Text = ds_adsl[0]["slid"];
                            this.txtSeriModem.Text = ds_adsl[0]["seri_md"];
                        }
                    }
                    else // dịch vụ TSL
                    {
                        let daucuoi = parseInt(this.rdoDiemDauCuoi.Value)

                        for (let i = 0; i < ds_adsl.length; i++)
                        {
                            if (ds_adsl[i]["daucuoi_id"] == daucuoi)
                            {
                                this.txtDiaChiLD.Text = ds_adsl[i]["diachi"]
                                this.txtPortMDF.Text = ds_adsl[i]["port_mdf"];
                                this.txtAdsl_Port_Cu.Text = ds_adsl[i]["adsl_port"];
                                this.txtONU_PASS_Cu.Text = ds_adsl[i]["onu_password"];
                                this.txtOnuPassAdsl.Text = ds_adsl[i]["onu_password_cu"];
                                this.txtVCIADSL.Text = ds_adsl[i]["vci"];
                                this.txtVPIADSL.Text = ds_adsl[i]["vpi"];
                                this.txtSlotADSL.Text = ds_adsl[i]["slot_tinh"];
                                this.txtPort.Text = ds_adsl[i]["port_tinh"];

                                if (!!ds_adsl[i]["port_id"])
                                {
                                    let portInfo = await this.lay_tt_vatly_theo_port_logic(ds_adsl[i]["port_id"])

                                    if (!!portInfo)
                                    {
                                        this.txtSlotOLT_Cu.Text = portInfo["ten_card"];
                                        this.txtPortOLT_Cu.Text = portInfo["vitri"];
                                        this.txtSubSlot.Text = portInfo["sub_slot"];
                                    }
                                }

                                this.txtMaTN.Text = "";
                                this.txtSoMayTNMoi.Text = "";
                                this.txtSoMayTN.Text = "";

                                if (!!ds_adsl[i]["bras_tinh_id"])
                                {
                                    this.cboBrasADSL.Value = ds_adsl[i]["bras_tinh_id"];
                                    this.cboBrasADSLMoi.Value = ds_adsl[i]["bras_tinh_id"];
                                    this.bras_cu_id = ds_adsl[i]["bras_tinh_id"];
                                }

                                if (!!ds_adsl[i]["dslam_cha_id"])
                                {
                                    this.cboDSLAM.Value = ds_adsl[i]["dslam_cha_id"];
                                    this.cboDSLAMMoi.Value = ds_adsl[i]["dslam_cha_id"];
                                    this.dslam_cu_id = ds_adsl[i]["dslam_cha_id"]
                                }

                                if (!!ds_adsl[i]["dslam_tinh_id"])
                                {
                                    this.dslam_tinh_id = ds_adsl[i]["dslam_tinh_id"]
                                }
                                else
                                {
                                    this.dslam_tinh_id = 0;
                                }

                                this.passwordMegaVNN = "";
                                this.txtSystem.Text = ds_adsl[i]["system"];
                                this.txtRack.Text = ds_adsl[i]["rack"];
                                this.txtShelf.Text = ds_adsl[i]["shelf"];
                                this.txtSelfid01.Text = ds_adsl[i]["selfid01"];
                                this.txtSelfid02.Text = ds_adsl[i]["selfid02"];
                                this.txtSelfid03.Text = ds_adsl[i]["selfid03"];
                                this.txtSelfid04.Text = ds_adsl[i]["selfid04"];

                                let donViInfo = await this.lay_tramvt_theo_thuebao(this.thuebao_id, daucuoi)
                                this.tramvt_id = !donViInfo ? 0 : donViInfo.donvi_id

                                if (!!ds_adsl[i]["tramtb_tinh_id"])
                                {
                                    this.tramtb_id = ds_adsl[i]["tramtb_tinh_id"]
                                }

                                if (!!ds_adsl[i]["port_id"])
                                {
                                    this.port_cu_id = ds_adsl[i]["port_id"];
                                    let dslamtinh = await this.lay_tt_dslam_theo_port(this.port_cu_id)

                                    if (!!dslamtinh && !!dslamtinh.ip)
                                    {
                                        this.txtIpCu.Text = dslamtinh.ip;
                                    }
                                    else
                                    {
                                        this.txtIpCu.Text = "";
                                    }
                                }
                                else
                                {
                                    this.port_cu_id = 0;
                                    this.txtIpCu.Text = "";
                                }

                                if (!!ds_adsl[i]["vci_vpi_id"])
                                {
                                    this.vci_vpi_cu_id = ds_adsl[i]["vci_vpi_id"];
                                }
                                else
                                {
                                    this.vci_vpi_cu_id = 0;
                                }
                            }
                        }
                    }

                    if (!!ds_adsl[0]["congnghe_id"])
                    {
                        let congNgheInfo = await this.lay_tt_congnghe(ds_adsl[0]["congnghe_id"])
                        this.txtCongNghe.id = ds_adsl[0]["congnghe_id"];
                        this.txtCongNghe.Text = congNgheInfo.congnghe
                    }
                    else
                    {
                        this.txtCongNghe.id = null;
                        this.txtCongNghe.Text = null
                    }

                    this.SetButton(2);
                }
            } catch (err) {
                this.$root.toastError("Có lỗi xảy ra")
            }
        },
        async lay_ds_loaihinh_tb(dichVuId) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/doisim/lay_ds_loaihinh_tb?dichvu_id=${dichVuId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_dslam_vdc_theo_bras(bras_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_ds_dslam_vdc_theo_bras`, {bras_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async layDauSoIms() {
            let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_dauso_ims`)
            return response.data.DAUSO_IMS
        },
        async lay_ds_hopdong_theo_ma_tb(ma_tb, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id, dichvuvt_id, loaitb_id) {
            try {
                let param = {ma_tb, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id, dichvuvt_id, loaitb_id}
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_ds_hopdong_theo_ma_tb`, param)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_hopdong_theo_ma_tb_v2(ma_tb, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id, dichvuvt_id) {
            try {
                let param = {ma_tb, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id, dichvuvt_id}
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_ds_hopdong_theo_ma_tb_v2`, param)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_tt_congnghe(congnghe_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_congnghe?congnghe_id=${congnghe_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_dslam_theo_port(port_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_dslam_theo_port?port_id=${port_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tramvt_theo_hdtb(hdtb_id, kieudv_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_tramvt_theo_hdtb`, {hdtb_id, kieudv_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tramvt_theo_thuebao(thuebao_id, kieudv_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_tramvt_theo_thuebao`, {thuebao_id, kieudv_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_loai_kenh(loaikenh_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_loai_kenh`, {loaikenh_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_dslam(dslam_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_dslam?dslam_id=${dslam_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_somay_truycap(madoicap) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_somay_truycap?madoicap=${madoicap}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_vatly_theo_port_logic(port_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_vatly_theo_port_logic?port_id=${port_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_tocdo_dv_adsl(tocdo_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_tocdo_dv_adsl?tocdo_id=${tocdo_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_tocdo_dv_kenh(tocdo_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_tocdo_dv_kenh?tocdo_id=${tocdo_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                  return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_ds_db_con_theo_tbid(thuebao_id, dichvuvt_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_ds_db_con_theo_tbid`, {thuebao_id, dichvuvt_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_db_ims_theo_tbid(in_thuebao_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thutienphatsinh/lay_ds_db_ims_theo_tbid`, {in_thuebao_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_db_adsl_theo_tbid(in_thuebao_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thutienphatsinh/lay_ds_db_adsl_theo_tbid`, {in_thuebao_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_hdtb_con_theo_hdtbid(hdtb_id, dichvuvt_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_ds_hdtb_con_theo_hdtbid`, {hdtb_id, dichvuvt_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_tt_temp_bnm(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_tt_temp_bnm`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.length > 0 ? response.data[0] : null
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_ds_hopdong_thuebao_ims(hdtbId) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_ims?hdtbId=${hdtbId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_hdtb_adsl_theo_hdtb_id(in_hdtb_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thutienphatsinh/lay_ds_hdtb_adsl_theo_hdtb_id`, {in_hdtb_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_hdtb_ims_theo_hdtb_id(in_hdtb_id) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_ds_hdtb_ims_theo_hdtb_id`, {in_hdtb_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_db_theo_matb(ma_tb, dichvuvt_id, donvi_dl_id) {
          try {
            let response = await this.$root.context.post("/web-quantri/thaydoi-thongso-internet/lay_db_theo_matb", {ma_tb, dichvuvt_id, donvi_dl_id})
            return !response.data ? [] : response.data
          } catch (err) {
            throw ""
          }
        },
        async lay_danhba_theo_matb(ma_tb, dichvuvt_id, donvi_dl_id) {
          try {
            let response = await this.$root.context.post("/web-quantri/thaydoi-thongso-internet/lay_danhba_theo_matb", {ma_tb, dichvuvt_id, donvi_dl_id})
            return !response.data ? [] : response.data
          } catch (err) {
            throw ""
          }
        },
        SetButton(kieu) {

            this.tsbtnNhapMoi.Enabled = false;
            this.tsbtnDongBoDL.Enabled = false;

            if (kieu == 0) // LoadForm
            {
                this.Clear();
            }

            if (kieu == 1)// Nhập mới
            {
                this.tsbtnNhapMoi.Enabled = false;
                this.tsbtnDongBoDL.Enabled = false;
                this.Clear();
            }

            if (kieu == 2) // TK thanh cong
            {
                this.tsbtnDongBoDL.Enabled = true;
                this.tsbtnNhapMoi.Enabled = true;

                //Thông tin mới
                this.txtONU_PASS_MOI.Text = "";
                this.txtVCIADSLMoi.Text = "";
                this.txtVPIADSLMoi.Text = "";
                this.txtSlotADSLMoi.Text = "";
                this.txtPortMoi.Text = "";
                this.txtSystemMoi.Text = "";
                this.txtRackMoi.Text = "";
                this.txtShelfMoi.Text = "";
                this.txtIpMoi.Text = "";
                this.txtPortMDFMoi.Text = "";
                this.txtSLIDMoi.Text = "";
                this.txtSeriModemMoi.Text = "";
            }
        },
        Clear() {
            this.txtMaDoiCap.Text = ""
            this.txtSoAo.Text = "";
            this.txtThongTinCTMoi.Text = "";
            this.txtThongTinCTCu.Text = "";
            this.txtGhiChu.Text = "";
            this.txtTocDo.Text = "";
            this.txtPortOLT_Cu.Text = "";
            this.txtPortOLT_Moi.Text = "";
            this.txtSlotOLT_Cu.Text = "";
            this.txtSlotOLT_Moi.Text = "";
            this.txtDslamTinhCu.Text = "";
            this.txtDslamTinhMoi.Text = "";
            this.txtONU_PASS_Cu.Text = "";
            this.txtOnuPassAdsl.Text = "";
            this.txtMaTB.Text = "";
            this.txtTenTB.Text = "";
            this.txtDiaChiLD.Text = "";
            this.txtCongNghe.id = null;
            this.txtCongNghe.Text = "";

            this.cboDSLAMMoi.Value = null
            this.cboDSLAM.Value = null
            this.cboLoaihinhTB.Value = this.cboLoaihinhTB.DataSource.length > 0 ? this.cboLoaihinhTB.DataSource[0].loaitb_id : null;
            this.hdtb_id = 0;
            this.tramvt_id = 0;
            this.thuebao_id = 0;
            this.port_cu_id = 0;
            this.vci_vpi_cu_id = 0;
            this.port_moi_id = 0;
            this.vci_vpi_moi_id = 0;

            //Thông tin cũ
            this.txtSoMayTN.Text = "";
            this.txtVCIADSL.Text = "";
            this.txtVPIADSL.Text = "";
            this.txtSlotADSL.Text = "";
            this.txtPort.Text = "";
            this.txtSystem.Text = "";
            this.txtRack.Text = "";
            this.txtShelf.Text = "";
            this.txtIpCu.Text = "";
            this.txtPortMDF.Text = "";
            this.txtSelfid01.Text = "";
            this.txtSelfid02.Text = "";
            this.txtSelfid03.Text = "";
            this.txtSelfid04.Text = "";
            this.txtSLID.Text = "";
            this.txtSeriModem.Text = "";

            //Thông tin mới
            this.txtSoMayTNMoi.Text = "";
            this.txtVCIADSLMoi.Text = "";
            this.txtVPIADSLMoi.Text = "";
            this.txtSlotADSLMoi.Text = "";
            this.txtPortMoi.Text = "";
            this.txtSystemMoi.Text = "";
            this.txtRackMoi.Text = "";
            this.txtShelfMoi.Text = "";
            this.txtPortMDFMoi.Text = "";
            this.txtIpMoi.Text = "";
            this.txtSelfid01Moi.Text = "";
            this.txtSelfid02Moi.Text = "";
            this.txtSelfid03Moi.Text = "";
            this.txtSelfid04Moi.Text = "";
            this.txtSLIDMoi.Text = "";
            this.txtSeriModemMoi.Text = "";
            this.passwordMegaVNN = "";
            this.txtMaTN.Text = "";
            this.txtTrangThai.Text = "";
            this.txtAdsl_Port_Cu.Text = "";
            this.txtAdsl_Port_Moi.Text = "";

            this.$refs['ref-txt-matb'].focus();
        },
        async lay_ds_thamso_md_mats(ma_ts) {
            try {
                let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_thamso_md_mats?ma_ts=${ma_ts}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_dichvu_vt() {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_ds_dichvu_vt`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_ds_bras_adsl() {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_ds_bras_adsl`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async kiemtra_hoso_catchuyen(ma_tb) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/kiemtra_hoso_catchuyen?ma_tb=${ma_tb}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                throw 'Có lỗi xảy ra khi kiểm tra hồ sơ cắt chuyển'
            }
        },
        async tsbtnDongBoDL_Click(){

            if (!this.tsbtnDongBoDL.Enabled)
            {
                return
            }

            try {
                this.loading(true)

                let _ten_hs = await this.kiemtra_hoso_catchuyen(this.txtMaTB.Text);

                if (!!_ten_hs && !!_ten_hs.kq) {
                    this.ten_hs = _ten_hs.kq
                    this.$refs['ref-modal-xacnhan-dongbo'].show()
                }
                else
                {
                    try {
                        this.loading(true)
                        await this.Dong_Bo()
                    } finally {
                        this.loading(false)
                    }
                }
            } catch(err) {
                this.$root.toastError(err)
            } finally {
                this.loading(false)
            }
        },
        async okDongBo() {
            try {
                this.loading(true)
                await this.Dong_Bo()
            } finally {
                this.loading(false)
            }
        },
        async Dong_Bo() {

            let dichvuvt_id = parseInt(this.cboDichVuVT.Value)

            if (this.trangthai_dl == 0)
            {
                if (dichvuvt_id != this.DichVuVienThong.MEGAWAN &&
                    dichvuvt_id != this.DichVuVienThong.METRONET &&
                    dichvuvt_id != this.DichVuVienThong.TSL &&
                    dichvuvt_id != this.DichVuVienThong.IMS_DOITAC &&
                    dichvuvt_id != this.DichVuVienThong.BRCD_DOITAC &&
                    dichvuvt_id != this.DichVuVienThong.MGW_DOITAC &&
                    dichvuvt_id != this.DichVuVienThong.TSL_DOITAC)
                {
                    let hdtbInfo = await this.lay_tt_hd_thuebao(this.hdtb_id)

                    if (hdtbInfo.status != this.TRANGTHAI_DONGBO.DONGBO_CM)
                    {
                        this.$root.toastError("Hợp đồng của thuê bao : " + this.txtMaTB.Text + " chưa được kích hoạt. \n Bạn không thể thực hiện đổi cổng !");
                        return;
                    }
                }
            }

            if (this.port_moi_id == 0 || this.vci_vpi_moi_id == 0)
            {
                this.$root.toastError("Bạn hãy chọn Port mới.");
                return;
            }

            if (this.port_moi_id == this.port_cu_id && this.vci_vpi_moi_id == this.vci_vpi_cu_id)
            {
                this.$root.toastError("Bạn hãy chọn Port mới.");
                return;
            }

            let chuyen_all = 0;
            let _ds = []

            if (this.chkChuyenCacDV.Checked)
            {
                chuyen_all = 1;
                _ds = await this.lay_ds_tb_chungcong(
                    this.trangthai_dl == 0 ? this.hdtb_id : this.thuebao_id,
                    this.trangthai_dl == 0 ? 1 : 2,
                    this.port_cu_id,
                    this.vci_vpi_cu_id,
                    this.port_moi_id,
                    this.vci_vpi_moi_id
                )
            }

            //Kiểm tra port mới phải có loai_onu_id (Kích hoạt tự động BNM)
            let kiemtra_loai_onu = await this.lay_ds_thamso_md_mats("KIEMTRA_LOAI_ONU")

            if (kiemtra_loai_onu.length > 0)
            {
                let modemInfo = await this.lay_thongtin_modem(this.thuebao_id, this.hdtb_id, this.port_moi_id, this.vci_vpi_moi_id)

                if (!modemInfo || !modemInfo.loai_onu_id)
                {
                    this.$root.toastError("Thuê bao chưa khai báo thông tin loại modem (loai_onu_id)");
                    return;
                }
            }

            let loaihinh_tb = parseInt(this.cboLoaihinhTB.Value)

            if (loaihinh_tb == this.LoaiHinhTB.INTERNET_ADSL ||
                loaihinh_tb == this.LoaiHinhTB.INTERNET_FTTH ||
                loaihinh_tb == this.LoaiHinhTB.WIFI_FIBER)
            {
                this.loading(false)

                let confirmation = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn thực hiện đổi cổng Account " + this.txtMaTB.Text + " trên Visa hay không?", {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    footerClass: 'p-2',
                    hideHeaderClose: false,
                    centered: true
                })

                if (!confirmation)
                {
                    return
                }

                this.loading(true)

                let kieu_mega = 1;
                let ds_mega = [];

                if (this.trangthai_dl == 1)
                {
                    ds_mega = await this.lay_ds_db_adsl_theo_tbid(this.thuebao_id)
                }
                else if (this.trangthai_dl == 0)
                {
                    ds_mega = await this.lay_ds_hdtb_adsl_theo_hdtb_id(this.hdtb_id);
                }

                if (ds_mega.length > 0)
                {
                    let tocDoInfo = await this.lay_tt_tocdo_dv_adsl(ds_mega[0]["tocdo_id"])
                    kieu_mega = tocDoInfo["kieu_mega"]
                }

                if (this.DONGBO_TEST.TEST == false && kieu_mega != 0)
                {
                    let DoiCongAcccountVisa = await this.DoiCongAcccountVisa()

                    if (DoiCongAcccountVisa != true)
                    {
                        this.$root.toastError("Có lỗi trong quá trình đổi cổng Account " + this.txtMaTB.Text + " trên Visa !\n" + DoiCongAcccountVisa.message);
                        return;
                    }
                }
            }

            if (loaihinh_tb == this.LoaiHinhTB.INTERNET_MYTV)
            {
                if (!this.txtSystemMoi.Text)
                {
                    if (`${this.thamso_dslam}` != "4")
                    {
                        this.$root.toastError("Chưa có thông tin về System mới. Hãy kiểm tra lại !");
                        this.$refs['ref-txt-system-moi'].focus();
                        return;
                    }
                }

                if (!this.txtIpMoi.Text)
                {
                    this.$root.toastError("Chưa có thông tin về IP. Hãy kiểm tra lại !");
                    this.$refs['ref-txt-ip-moi'].focus();
                    return;
                }

                this.loading(false)

                let confirmation = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn thực hiện đổi cổng Account " + this.txtMaTB.Text + " không?", {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    footerClass: 'p-2',
                    hideHeaderClose: false,
                    centered: true
                })

                if (!confirmation)
                {
                    return
                }

                this.loading(true)

                if (this.DONGBO_TEST.TEST == false)
                {
                    let sPortDSL = "";
                    let shelf = "0";

                    if (this.maTinh == "BDG")
                    {
                        let portDslamInfo = await this.lay_tt_port_dslam(this.port_moi_id)
                        let vloaitbi_id = !portDslamInfo ? null : `${portDslamInfo.loaitbi_id}`

                        if (vloaitbi_id == "1")
                        {
                            let portCardDslInfo = await this.lay_tt_port_card_dsl(this.port_moi_id)
                            let slot_moi = portCardDslInfo.vitri
                            sPortDSL = `${this.txtIpMoi.Text}/${this.txtRackMoi.Text}/${slot_moi}/${this.txtPortMoi.Text}/${this.txtVPIADSLMoi.Text}`;
                        }
                        else if (vloaitbi_id == "3")
                        {
                            sPortDSL = `${this.txtIpMoi.Text}/${this.txtRackMoi.Text}/${this.txtSlotADSLMoi.Text}/${this.txtPortMoi.Text}/${this.txtVPIADSLMoi.Text}`;
                        }
                        else if (vloaitbi_id == "2")
                        {
                            let portSwitchInfo = await this.lay_tt_port_switch(this.port_moi_id)
                            let slot_moi = portSwitchInfo.vitri
                            sPortDSL = `${this.txtIpMoi.Text}/${this.txtRackMoi.Text}/${shelf}/${slot_moi}`;
                        }
                        else if (!vloaitbi_id)
                        {
                            this.$root.toastError("Không xác định được loại thiết bị của dslam tỉnh. Mời bạn chọn lại.");
                        }
                        else
                        {
                            this.$root.toastError("Dslam có loại thiết bị nằm ngoài danh mục kích hoạt.");
                        }
                    }
                    else
                    {
                        if (!!this.txtShelfMoi.Text)
                        {
                            shelf = this.txtShelfMoi.Text
                        }

                        let portDslamInfo = await this.lay_tt_port_dslam(this.port_moi_id)
                        let vloaitbi_id = !portDslamInfo ? null : `${portDslamInfo.loaitbi_id}`

                        if (vloaitbi_id == "3")
                        {
                            sPortDSL = `${this.txtIpMoi.Text}/${this.txtRackMoi.Text}/${this.txtSlotADSLMoi.Text}/${this.txtPortMoi.Text}/${this.txtVPIADSLMoi.Text}`;
                        }
                        else
                        {
                            sPortDSL = `${this.txtIpMoi.Text}/${shelf}/${this.txtSlotADSLMoi.Text}/${this.txtPortMoi.Text}`;
                        }
                    }

                    let sDeviceName = !this.txtSystemMoi.Text ? "" : this.txtSystemMoi.Text;
                    let result = await VASCService.UpdateDeviceInfo(this.txtMaTB.Text, sDeviceName, sPortDSL, this);

                    if (result != 0) // lỗi
                    {
                        this.$root.toastError("Có lỗi trong quá trình cập nhật thông tin cổng thuê bao " + this.txtMaTB.Text + " trên VASC !");
                        return;
                    }
                    else
                    {
                        this.$root.toastSuccess("Đổi cổng Account " + this.txtMaTB.Text + " trên VASC thành công !");
                    }
                }
            }

            this.kt_thaydoi = true;
            let result = null

            if (dichvuvt_id == this.DichVuVienThong.ADSL || dichvuvt_id == this.DichVuVienThong.BRCD_DOITAC)
            {
                let data = this.TaoDuLieu_ADSL_1();
                result = await this.insert_tdtt_db_adsl(data)
            }

            if (dichvuvt_id == this.DichVuVienThong.IMS || dichvuvt_id == this.DichVuVienThong.IMS_DOITAC)
            {
                let data = this.TaoDuLieu_IMS();
                result = await this.insert_tdtt_db_ims(data)
            }

            if (dichvuvt_id == this.DichVuVienThong.MEGAWAN ||
                dichvuvt_id == this.DichVuVienThong.METRONET ||
                dichvuvt_id == this.DichVuVienThong.MGW_DOITAC)
            {
                let data = this.TaoDuLieu_MGWAN();
                result = await this.insert_tdtt_db_mgwan(data)
            }

            if (dichvuvt_id == this.DichVuVienThong.TSL || dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
            {
                let data = this.TaoDuLieu_TSL();
                result = await this.insert_tdtt_db_tsl(data)
            }

            if (result.kq == 0)
            {
                this.$root.toastError("Có lỗi xảy ra khi thay đổi thông tin danh bạ.\n" + result.message)
                return
            }

            this.tdtt_db_id = result.tdtt_db_id

            // Cập nhật thông tin vào danh bạ hoặc hợp đồng.
            let vdaucuoi_id = 0;

            if (dichvuvt_id == this.DichVuVienThong.TSL || dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
            {
                vdaucuoi_id = parseInt(this.rdoDiemDauCuoi.Value)
            }

            let gui_lenh_bnm = this.chkBNM.Checked ? 1 : 0;

            if (this.chkChuyenCacDV.Checked && _ds.length > 0)
            {
                this.loading(false)
                await this.$refs['ds_tb_doicong'].show([..._ds])
                this.loading(true)
            }

            //let _dsdslam = await this.lay_tt_dslam_theo_port(this.port_moi_id);
            //let congnghe_id = !_dsdslam ? 0 : _dsdslam.congnghe_id;
            let congnghe_id = this.txtCongNghe_Moi.id != null ? this.txtCongNghe_Moi.id : this.txtCongNghe.id;

            let capnhat_doi_ts_internet = null

            if (dichvuvt_id == this.DichVuVienThong.TSL ||
                dichvuvt_id == this.DichVuVienThong.MEGAWAN ||
                dichvuvt_id == this.DichVuVienThong.METRONET ||
                dichvuvt_id == this.DichVuVienThong.MGW_DOITAC ||
                dichvuvt_id == this.DichVuVienThong.TSL_DOITAC)
            {
                let phanvungId = this.$root.token.getPhanVungID()
                let seri_md = this.chkSR_MD_MOI.Checked ? this.txtSeriModemMoi.Text : this.txtSeriModem.Text;
                if(phanvungId == 51 /*TBH*/){
                    seri_md = this.txtSeriModem.Text;
                }
                capnhat_doi_ts_internet = await this.capnhat_doi_ts_internet_v6({
                    thuebao_id: this.thuebao_id,
                    bras_id: this.cboBrasADSLMoi.Value,
                    dslam_id: this.cboDSLAMMoi.Value,
                    port_id: this.port_moi_id,
                    vci_vpi_id: this.vci_vpi_moi_id,
                    hdtb_id: this.hdtb_id,
                    trangthai_dl: this.trangthai_dl,
                    tramtb_id: this.tramtb_id,
                    slid: this.chkSLID_MOI.Checked ? this.txtSLIDMoi.Text : this.txtSLID.Text,
                    seri_md: seri_md,
                    tdtt_db_id: this.tdtt_db_id,
                    daucuoi_id: vdaucuoi_id,
                    congnghe_id: congnghe_id,
                })
            }
            else //Đừng động vào hàm v3 HYN, LĐG đang sử dụng kích hoạt thiết bị
            {
                let phanvungId = this.$root.token.getPhanVungID()
                let seri_md = this.chkSR_MD_MOI.Checked ? this.txtSeriModemMoi.Text : this.txtSeriModem.Text;
                if(phanvungId == 51 /*TBH*/){
                    seri_md = this.txtSeriModem.Text;
                }

                capnhat_doi_ts_internet = await this.capnhat_doi_ts_internet_v5({
                    thuebao_id: this.thuebao_id,
                    bras_id: this.cboBrasADSLMoi.Value,
                    dslam_id: this.cboDSLAMMoi.Value,
                    port_id: this.port_moi_id,
                    vci_vpi_id: this.vci_vpi_moi_id,
                    hdtb_id: this.hdtb_id,
                    trangthai_dl: this.trangthai_dl,
                    tramtb_id: this.tramtb_id,
                    slid: this.chkSLID_MOI.Checked ? this.txtSLIDMoi.Text : this.txtSLID.Text,
                    seri_md: seri_md,
                    tdtt_db_id: this.tdtt_db_id,
                    gui_lenh_bnm: gui_lenh_bnm,
                    chuyen_all: chuyen_all,
                    congnghe_id: congnghe_id
                })
            }

            if (!capnhat_doi_ts_internet || capnhat_doi_ts_internet.kq == 0) {
                this.$root.toastError("Có lỗi xảy ra khi cập nhật thông số Internet.\n" + capnhat_doi_ts_internet.message)
                return
            }

            //Kiểm tra xem có sự thay đổi port hay không
            let dsktradb = await this.ktra_dk_port({
                ma_tb: this.txtMaTB.Text,
                port_id: this.port_cu_id,
                vci_vpi_id: this.vci_vpi_cu_id,
                kieu: 1
            })

            let dsktrahd = await this.ktra_dk_port({
                ma_tb: this.txtMaTB.Text,
                port_id: this.port_cu_id,
                vci_vpi_id: this.vci_vpi_cu_id,
                kieu: 2
            })

            try
            {
                // Cập nhật port cũ
                if (dsktradb.length == 0 && dsktrahd.length == 0)
                {
                    //nếu ko có thuê bao nào sử dụng port thì update về chưa sử dụng
                    await this.capnhat_trangthai_port({
                        port_id: this.port_cu_id,
                        vci_vpi: this.vci_vpi_cu_id,
                        trangthai: this.TRANGTHAI_PORT.CHUA_SD
                    })

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.CHUA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id,
                        ds_matb: "",
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.CHUA_SD
                    })
                }
                else if (dsktradb.length != 0 && dsktrahd.length == 0)
                {
                    let vds_matb = "";

                    for (let i = 0; i < dsktradb.length; i++)
                    {
                        vds_matb = vds_matb + dsktradb[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    if (vds_matb != "")
                    {
                        vds_matb = vds_matb.substr(0, vds_matb.length - 2);
                    }

                    await this.capnhat_trangthai_port({
                        port_id: this.port_cu_id,
                        vci_vpi: this.vci_vpi_cu_id,
                        trangthai: this.TRANGTHAI_PORT.DA_SD
                    })

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id,
                        ds_matb: vds_matb,
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD
                    })
                }
                else if (dsktradb.length == 0 && dsktrahd.length != 0)
                {
                    let vds_matb = "";

                    for (let i = 0; i < dsktrahd.length; i++)
                    {
                        vds_matb = vds_matb + dsktrahd[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    if (vds_matb != "")
                    {
                        vds_matb = vds_matb.substr(0, vds_matb.length - 2);
                    }

                    await this.capnhat_trangthai_port({
                        port_id: this.port_cu_id,
                        vci_vpi: this.vci_vpi_cu_id,
                        trangthai: this.TRANGTHAI_PORT.TAM_CAP
                    })

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id,
                        ds_matb: vds_matb,
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD
                    })
                }
                else if (dsktradb.length != 0 && dsktrahd.length != 0)
                {
                    let vds_matb = "";

                    for (let i = 0; i < dsktradb.length; i++)
                    {
                        vds_matb = vds_matb + dsktradb[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    for (let i = 0; i < dsktrahd.length; i++)
                    {
                        vds_matb = vds_matb + dsktrahd[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    if (vds_matb != "")
                    {
                        vds_matb = vds_matb.substr(0, vds_matb.length - 2);
                    }

                    await this.capnhat_trangthai_port({
                        port_id: this.port_cu_id,
                        vci_vpi: this.vci_vpi_cu_id,
                        trangthai: this.TRANGTHAI_PORT.DA_SD
                    })

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id,
                        ds_matb: vds_matb,
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_cu_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD
                    })
                }

                // Cập nhật port mới
                // Kiểm tra port đang chọn
                dsktradb = await this.ktra_dk_port({
                    ma_tb: this.txtMaTB.Text,
                    port_id: this.port_moi_id,
                    vci_vpi_id: this.vci_vpi_moi_id,
                    kieu: 1
                })

                dsktrahd = await this.ktra_dk_port({
                    ma_tb: this.txtMaTB.Text,
                    port_id: this.port_moi_id,
                    vci_vpi_id: this.vci_vpi_moi_id,
                    kieu: 2
                })

                if (dsktradb.length == 0 && dsktrahd.length == 0)
                {
                    //nếu ko có thuê bao nào sử dụng port thì update về chưa sử dụng
                    if (this.trangthai_dl == 1)
                    {
                        await this.capnhat_trangthai_port({
                            port_id: this.port_moi_id,
                            vci_vpi: this.vci_vpi_moi_id,
                            trangthai: this.TRANGTHAI_PORT.DA_SD
                        })
                    }
                    else
                    {
                        await this.capnhat_trangthai_port({
                            port_id: this.port_moi_id,
                            vci_vpi: this.vci_vpi_moi_id,
                            trangthai: this.TRANGTHAI_PORT.TAM_CAP
                        })
                    }

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_moi_id,
                        vci_vpi_id: this.vci_vpi_moi_id,
                        ds_matb: this.txtMaTB.Text,
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD
                    })
                }
                else if (dsktradb.length != 0 && dsktrahd.length == 0)
                {
                    let vds_matb = "";

                    for (let i = 0; i < dsktradb.length; i++)
                    {
                        vds_matb = vds_matb + dsktradb[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    vds_matb = vds_matb + this.txtMaTB.Text;

                    await this.capnhat_trangthai_port({
                        port_id: this.port_moi_id,
                        vci_vpi: this.vci_vpi_moi_id,
                        trangthai: this.TRANGTHAI_PORT.DA_SD
                    })

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_moi_id,
                        vci_vpi_id: this.vci_vpi_moi_id,
                        ds_matb: vds_matb,
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD
                    })
                }
                else if (dsktradb.length == 0 && dsktrahd.length != 0)
                {
                    let vds_matb = "";

                    for (let i = 0; i < dsktrahd.length; i++)
                    {
                        vds_matb = vds_matb + dsktrahd[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    vds_matb = vds_matb + this.txtMaTB.Text;

                    if (this.trangthai_dl == 1)
                    {
                        await this.capnhat_trangthai_port({
                            port_id: this.port_moi_id,
                            vci_vpi: this.vci_vpi_moi_id,
                            trangthai: this.TRANGTHAI_PORT.DA_SD
                        })
                    }
                    else
                    {
                        await this.capnhat_trangthai_port({
                            port_id: this.port_moi_id,
                            vci_vpi: this.vci_vpi_moi_id,
                            trangthai: this.TRANGTHAI_PORT.TAM_CAP
                        })
                    }

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_moi_id,
                        vci_vpi_id: this.vci_vpi_moi_id,
                        ds_matb: vds_matb,
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD
                    })
                }
                else if (dsktradb.length != 0 && dsktrahd.length != 0)
                {
                    let vds_matb = "";

                    for (let i = 0; i < dsktradb.length; i++)
                    {
                        vds_matb = vds_matb + dsktradb[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    for (let i = 0; i < dsktrahd.length; i++)
                    {
                        vds_matb = vds_matb + dsktrahd[i]["ma_tb"] + "; ";

                        if (vds_matb.length > 3000)
                        {
                            break;
                        }
                    }

                    vds_matb = vds_matb + this.txtMaTB.Text;

                    await this.capnhat_trangthai_port({
                        port_id: this.port_moi_id,
                        vci_vpi: this.vci_vpi_moi_id,
                        trangthai: this.TRANGTHAI_PORT.DA_SD
                    })

                    await this.capnhat_trangthai_portid({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD,
                        ip: this.ip
                    })

                    await this.capnhat_ds_matb({
                        port_id: this.port_moi_id,
                        vci_vpi_id: this.vci_vpi_moi_id,
                        ds_matb: vds_matb,
                    })

                    await this.capnhat_port_vatly({
                        port_id: this.port_moi_id,
                        tt_port_id: this.TRANGTHAI_PORT.DA_SD
                    })
                }
            }
            catch (err)
            {
                this.$root.toastError("Lỗi trong quá trình thay đổi trạng thái port vật lý ! ");
            }

            if (this.ketcuoi_moi_id > 0 && this.vitri_moi_id > 0 && this.IS_USING_CABMAN_V2)
            {
                // Cấp cáp
                try
                {
                    await this.nhap_cap_thuebao({
                        kieu: this.trangthai_dl == 1 ? 0 : 1,
                        db_id: this.trangthai_dl == 1 ? this.thuebao_id : this.hdtb_id,
                        ketcuoi_id: this.ketcuoi_moi_id,
                        vitri: this.vitri_moi_id,
                        mat: 2,
                        vitri2: 0,
                        mat2: 2,
                        ip: this.ip,
                        lap_tb_cung_cap: 1,
                        daucuoi_id: vdaucuoi_id <= 0 ? 2 : vdaucuoi_id,
                        checkquyen: 0
                    });
                }
                catch (err)
                {
                    this.$root.toastError(err)
                }
            }

            this.$root.toastSuccess("Cập nhật thông số trên dữ liệu viễn thông tỉnh thành công !");
            this.kt_thaydoi = true;
            this.HienThiThongTin();
        },
        async nhap_cap_thuebao(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/nhap_cap_thuebao`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                throw 'Có lỗi trong quá trình nhập cáp cho thuê bao'
            }
        },
        async capnhat_port_vatly(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_port_vatly`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async getIp(){
            try{
                const response = await fetch("https://jsonip.com/")
                const data = await response.json();
                return data.ip;
            }catch(e) {
                return ""
            }
        },
        async capnhat_ds_matb(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_ds_matb`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async capnhat_trangthai_portid(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_trangthai_portid`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async capnhat_trangthai_port(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_trangthai_port`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async DoiCongAcccountVisa() {
            try
            {
                let account = this.txtMaTB.Text;
                let port = this.txtPortMoi.Text;
                let vci = this.txtVCIADSLMoi.Text;
                let vpi = this.txtVPIADSLMoi.Text;

                if (!vpi)
                {
                    vpi = "0"; // Fix cho TH quy hoạch 1 lớp
                }

                let slot = this.txtSlotADSLMoi.Text;

                let brasInfo = await this.lay_tt_bras(this.cboBrasADSLMoi.Value)
                let brasId = !brasInfo ? -1 : brasInfo.bras_vdc_id;

                let dslamId = parseInt(this.cboDSLAMMoi.Value);
                let dslamInfo = await this.lay_tt_dslam(dslamId)
                let dslam_vdc_Id = !dslamInfo ? -1 : dslamInfo.dslam_vdc_id;

                // Tham so them cho dang ky 7 tham so
                let systemId = this.txtSystemMoi.Text;
                let rackId = this.txtRackMoi.Text;
                let shelfId = this.txtShelfMoi.Text;

                // thêm cho dang ky 9 tham so
                let self01 = this.txtSelfid01Moi.Text;
                let self02 = this.txtSelfid02Moi.Text;
                let self03 = this.txtSelfid03Moi.Text;
                let self04 = this.txtSelfid04Moi.Text;

                // Kiem tra xem dang ky 4 tham so hay 7 tham so
                let thamso = !dslamInfo ? -1 : dslamInfo.thamso;

                if (thamso == 4)
                {
                    let DoiCongMegavnn4 = await vdcService.DoiCongMegavnn4(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, this)

                    if (DoiCongMegavnn4.hasError)
                    {
                        return DoiCongMegavnn4;
                    }
                    else
                    {
                        this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                        return true;
                    }
                }
                else if (thamso == 7)
                {
                    let DoiCongMegavnn7 = await vdcService.DoiCongMegavnn7(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, rackId, shelfId, this)

                    if (DoiCongMegavnn7.hasError) {
                        return DoiCongMegavnn7;
                    }
                    else
                    {
                        this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                        return true;
                    }
                }
                else if (thamso == 9)
                {
                    if (parseInt(this.cboLoaihinhTB.Value) == this.LoaiHinhTB.MEGA)
                    {
                        let DoiCongMegavnn9_Mega = await vdcService.DoiCongMegavnn9_Mega(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                        if (DoiCongMegavnn9_Mega.hasError) {
                            return DoiCongMegavnn9_Mega;
                        }
                        else
                        {
                            this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                            return true;
                        }
                    }
                    else
                    {
                        if (this.maTinh == "HTH")
                        {
                            if (dslam_vdc_Id == 42537 || dslam_vdc_Id == 40701)
                            {
                                let DoiCongMegavnn9_FE = await vdcService.DoiCongMegavnn9_FE(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                                if (DoiCongMegavnn9_FE.hasError)
                                {
                                    return DoiCongMegavnn9_FE;
                                }
                                else
                                {
                                    this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                    return true;
                                }
                            }
                            else
                            {
                                let DoiCongMegavnn9_ADSL = await vdcService.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                                if (DoiCongMegavnn9_ADSL.hasError) {
                                    return DoiCongMegavnn9_ADSL;
                                }
                                else
                                {
                                    this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                    return true;
                                }
                            }
                        }
                        else if (this.maTinh == "QBH")
                        {
                            if (dslamId == 4 || dslamId == 9)
                            {
                                let DoiCongMegavnn9_FE = await vdcService.DoiCongMegavnn9_FE(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                                if (DoiCongMegavnn9_FE.hasError)
                                {
                                    return DoiCongMegavnn9_FE;
                                }
                                else
                                {
                                    this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                    return true;
                                }
                            }
                            else
                            {
                                let DoiCongMegavnn9_ADSL = await vdcService.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                                if (DoiCongMegavnn9_ADSL.hasError) {
                                    return DoiCongMegavnn9_ADSL;
                                }
                                else
                                {
                                    this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                    return true;
                                }
                            }
                        }
                        else
                        {
                            let DoiCongMegavnn9_ADSL = await vdcService.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                            if (DoiCongMegavnn9_ADSL.hasError)
                            {
                                return DoiCongMegavnn9_ADSL;
                            }
                            else
                            {
                                this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                return true;
                            }
                        }
                    }

                }
                return true;
            }
            catch (err)
            {
                return {
                    hasError: false,
                    message: "Xảy ra lỗi không xác định"
                }
            }
        },
        async lay_tt_bras(bras_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_bras?bras_id=${bras_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async ktra_dk_port(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/ktra_dk_port`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async capnhat_doi_ts_internet_v5(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_doi_ts_internet_v5`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async capnhat_doi_ts_internet_v6(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_doi_ts_internet_v6`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        TaoDuLieu_TSL() {
          return {
            thuebao_id: this.thuebao_id,
            hdtb_id: this.hdtb_id,
            ma_tb: this.txtMaTB.Text,
            port_id: this.port_moi_id,
            port_id_cu: !this.port_cu_id ? null : this.port_cu_id,
            vci_vpi_id: this.vci_vpi_moi_id,
            vci_vpi_id_cu: !this.vci_vpi_cu_id ? null : this.vci_vpi_cu_id,
            trangthai_id: this.rdoDanhBaHopDong.Value,
            daucuoi_id: this.rdoDiemDauCuoi.Value,
            ip: this.ip,
            ghichu: this.txtGhiChu.Text,
          }
        },
        async insert_tdtt_db_tsl(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/insert_tdtt_db_tsl`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        TaoDuLieu_MGWAN() {
            return {
                thuebao_id: this.thuebao_id,
                hdtb_id: this.hdtb_id,
                ma_tb: this.txtMaTB.Text,
                port_id: this.port_moi_id,
                port_id_cu: !this.port_cu_id ? null : this.port_cu_id,
                vci_vpi_id: this.vci_vpi_moi_id,
                vci_vpi_id_cu: !this.vci_vpi_cu_id ? null : this.vci_vpi_cu_id,
                bras_id: this.cboBrasADSLMoi.Value,
                bras_id_cu: !this.bras_cu_id ? null : this.bras_cu_id,
                dslam_id: this.cboDSLAMMoi.Value,
                dslam_id_cu: !this.dslam_cu_id ? null : this.dslam_cu_id,
                trangthai_id: this.rdoDanhBaHopDong.Value,
                ip: this.ip,
                ghichu: this.txtGhiChu.Text,
                lan_ip_cu: this.txtLANIP.Text,
                lan_ip: this.txtLANIP_Moi.Text,
                wan_ip_cu: this.txtWANIP.Text,
                wan_ip: this.txtWANIP_Moi.Text,
            }
        },
        async insert_tdtt_db_mgwan(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/insert_tdtt_db_mgwan`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        TaoDuLieu_IMS() {
            return {
                thuebao_id: this.thuebao_id,
                ma_tb: this.txtMaTB.Text,
                port_id: this.port_moi_id,
                port_id_cu: !this.port_cu_id ? null : this.port_cu_id,
                vci_vpi_id: this.vci_vpi_moi_id,
                vci_vpi_id_cu: !this.vci_vpi_cu_id ? null : this.vci_vpi_cu_id,
                bras_id: this.cboBrasADSLMoi.Value,
                bras_id_cu: !this.bras_cu_id ? null : this.bras_cu_id,
                dslam_id: this.cboDSLAMMoi.Value,
                dslam_id_cu: !this.dslam_cu_id ? null : this.dslam_cu_id,
                trangthai_id: this.rdoDanhBaHopDong.Value,
                ip: this.ip,
                ghichu: this.txtGhiChu.Text,
            }
        },
        async insert_tdtt_db_ims(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/insert_tdtt_db_ims`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        TaoDuLieu_ADSL_1() {
            return {
                thuebao_id: this.thuebao_id,
                ma_tb: this.txtMaTB.Text,
                port_id: this.port_moi_id,
                port_id_cu: !this.port_cu_id ? null : this.port_cu_id,
                vci_vpi_id: this.vci_vpi_moi_id,
                vci_vpi_id_cu: !this.vci_vpi_cu_id ? null : this.vci_vpi_cu_id,
                bras_id: this.cboBrasADSLMoi.Value,
                bras_id_cu: !this.bras_cu_id ? null : this.bras_cu_id,
                dslam_id: this.cboDSLAMMoi.Value,
                dslam_id_cu: !this.dslam_cu_id ? null : this.dslam_cu_id,
                trangthai_id: this.rdoDanhBaHopDong.Value,
                ip: this.ip,
                slid_cu: this.txtSLID.Text,
                seri_md_cu: this.txtSeriModem.Text,
                slid_moi: this.txtSLIDMoi.Text,
                seri_md_moi: this.txtSeriModemMoi.Text,
                ghichu: "Đổi port mới : " + this.txtGhiChu.Text,
            }
        },
        TaoDuLieu_ADSL(SLID_MOI, SERI_MD_MOI){
            return {
                thuebao_id: this.thuebao_id,
                ma_tb: this.txtMaTB.Text,
                port_id: this.port_moi_id,
                port_id_cu: !this.port_cu_id ? null : this.port_cu_id,
                vci_vpi_id: this.vci_vpi_moi_id,
                vci_vpi_id_cu: !this.vci_vpi_cu_id ? null : this.vci_vpi_cu_id,
                bras_id: this.cboBrasADSLMoi.Value,
                bras_id_cu: !this.bras_cu_id ? null : this.bras_cu_id,
                dslam_id: this.cboDSLAMMoi.Value,
                dslam_id_cu: !this.dslam_cu_id ? null : this.dslam_cu_id,
                trangthai_id: this.rdoDanhBaHopDong.Value,
                ip: this.ip,
                slid_cu: this.txtSLID.Text,
                seri_md_cu: this.txtSeriModem.Text,
                slid_moi: SLID_MOI,
                seri_md_moi: SERI_MD_MOI,
                ghichu: "Thay đổi thông số Internet : " + this.txtGhiChu.Text,
            }
        },
        async insert_tdtt_db_adsl(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/insert_tdtt_db_adsl`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async doi_matkhau_ims(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/doi_matkhau_ims`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_port_switch(port_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_port_switch?port_id=${port_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_port_card_dsl(port_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_port_card_dsl?port_id=${port_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_port_dslam(port_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_port_dslam?port_id=${port_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_thongtin_modem(thuebao_id, hdtb_id, port_id, vci_vpi_id) {
            try {
                let param = {thuebao_id, hdtb_id, port_id, vci_vpi_id}
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/lay_thongtin_modem`, param)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_ds_tb_chungcong(db_id, loai, port_id, vci_vpi_id, port_id_moi, vci_vpi_id_moi) {
            try {
                let param = {db_id, loai, port_id, vci_vpi_id, port_id_moi, vci_vpi_id_moi}
                let response = await this.$root.context.get(`/web-quantri/ds-tb-doicong/lay-ds-tb-chungcong`, param)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async lay_tt_hd_thuebao(hdtb_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_hd_thuebao?hdtb_id=${hdtb_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_diachi_tb(thuebao_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_diachi_tb?thuebao_id=${thuebao_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_diachi_hdtb(hdtb_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_diachi_hdtb?hdtb_id=${hdtb_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async dongbo_bnm(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/dongbo_bnm`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async lay_tt_loaihinh_tb(loaitb_id) {
            try {
                let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_loaihinh_tb?loaitb_id=${loaitb_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async Dongbo_CongAcccountVisa() {

            let account = this.txtMaTB.Text;
            let port = this.txtPort.Text;
            let vci = this.txtVCIADSL.Text;
            let vpi = this.txtVPIADSL.Text;
            let slot = this.txtSlotADSL.Text;

            let brasInfo = await this.lay_tt_bras(this.cboBrasADSL.Value)
            let brasId = !brasInfo ? -1 : brasInfo.bras_vdc_id;

            let dslamInfo = await this.lay_tt_dslam(this.cboDSLAMMoi.Value)

            if (!dslamInfo || !dslamInfo.dslam_vdc_id)
            {
                throw "Không tìm thấy Dslam VDC. Liên hệ admin để xử lý !"
            }

            let dslam_vdc_Id = dslamInfo.dslam_vdc_id

            let systemId = this.txtSystem.Text;
            let rackId = this.txtRack.Text;
            let shelfId = this.txtShelf.Text;

            let self01 = this.txtSelfid01.Text;
            let self02 = this.txtSelfid02.Text;
            let self03 = this.txtSelfid03.Text;
            let self04 = this.txtSelfid04.Text;

            dslamInfo = await this.lay_tt_dslam(this.cboDSLAM.Value)
            let thamso = dslamInfo.thamso

            if (thamso == 4)
            {
                let DoiCongMegavnn4 = await vdcService.DoiCongMegavnn4(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, this)

                if (DoiCongMegavnn4.hasError)
                {
                    throw DoiCongMegavnn4.message
                }
                else
                {
                    this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                    return;
                }
            }
            else if (thamso == 7)
            {
                let DoiCongMegavnn7 = await vdcService.DoiCongMegavnn7(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, rackId, shelfId, this)

                if (DoiCongMegavnn7.hasError) {
                    throw DoiCongMegavnn7.message
                }
                else
                {
                    this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                    return;
                }
            }
            else if (thamso == 9)
            {
                if (parseInt(this.cboLoaihinhTB.Value) == this.LoaiHinhTB.MEGA)
                {
                    let DoiCongMegavnn9_Mega = await vdcService.DoiCongMegavnn9_Mega(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                    if (DoiCongMegavnn9_Mega.hasError) {
                        throw DoiCongMegavnn9_Mega.message
                    }
                    else
                    {
                        this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                        return;
                    }
                }
                else
                {
                    if (this.maTinh == "HTH")
                    {
                        if (dslam_vdc_Id == 42537 || dslam_vdc_Id == 40701)
                        {
                            let DoiCongMegavnn9_FE = await vdcService.DoiCongMegavnn9_FE(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                            if (DoiCongMegavnn9_FE.hasError)
                            {
                                throw DoiCongMegavnn9_FE.message
                            }
                            else
                            {
                                this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                return;
                            }
                        }
                        else
                        {
                            let DoiCongMegavnn9_ADSL = await vdcService.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                            if (DoiCongMegavnn9_ADSL.hasError) {
                                throw DoiCongMegavnn9_ADSL.message
                            }
                            else
                            {
                                this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                return;
                            }
                        }
                    }
                    else
                    {
                        let DoiCongMegavnn9_ADSL = await vdcService.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04, this)

                        if (DoiCongMegavnn9_ADSL.hasError) {
                            throw DoiCongMegavnn9_ADSL.message
                        }
                        else
                        {
                            this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                            return;
                        }
                    }
                }
            }
        },
        async tsbtnCapNhat_Click() {

            if (!this.tsbtnCapNhat.Enabled)
            {
                return
            }

            try {
                this.loading(true)

                if (this.trangthai_dl == 0)
                {
                    let hdtbInfo = await this.lay_tt_hd_thuebao(this.hdtb_id)
                    let status = hdtbInfo.status

                    if (status != this.TRANGTHAI_DONGBO.DONGBO_CM)
                    {
                        this.$root.toastError("Hợp đồng của thuê bao : " + this.txtMaTB.Text + " chưa được kích hoạt. \n Bạn không thể thực hiện đồng bộ port lên visa !");
                        return;
                    }
                }

                if (this.port_cu_id == 0)
                {
                    this.$root.toastError("Bạn hãy chọn Port cũ");
                    this.$refs['ref-txt-matb'].focus();
                    return;
                }

                let loaihinh_tb = parseInt(this.cboLoaihinhTB.Value)

                if (loaihinh_tb == this.LoaiHinhTB.INTERNET_ADSL
                    || loaihinh_tb == this.LoaiHinhTB.INTERNET_FTTH
                    || loaihinh_tb == this.LoaiHinhTB.WIFI_FIBER)
                {
                    this.loading(false)

                    let confirmation = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn thực hiện đồng bộ lại port cho Account " + this.txtMaTB.Text + " trên Visa hay không?", {
                        title: 'Thông báo',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                        footerClass: 'p-2',
                        hideHeaderClose: false,
                        centered: true
                    })

                    if (!confirmation)
                    {
                        return
                    }

                    this.loading(true)

                    let kieu_mega = 1;
                    let ds_mega = [];

                    if (this.trangthai_dl == 1)
                    {
                        ds_mega = await this.lay_ds_db_adsl_theo_tbid(this.thuebao_id)
                    }
                    else if (this.trangthai_dl == 0)
                    {
                        ds_mega = await this.lay_ds_hdtb_adsl_theo_hdtb_id(this.hdtb_id);
                    }

                    if (ds_mega.length > 0)
                    {
                        let tocDoInfo = await this.lay_tt_tocdo_dv_adsl(ds_mega[0]["tocdo_id"])
                        kieu_mega = tocDoInfo["kieu_mega"]
                    }

                    if (this.DONGBO_TEST.TEST == false && kieu_mega != 0)
                    {
                        try {
                            await this.Dongbo_CongAcccountVisa()
                            this.$root.toastSuccess("Quá trình đồng bộ lại port cho Account " + this.txtMaTB.Text + " trên Visa thành công !");
                        }
                        catch(err)
                        {
                            if (!!err)
                            {
                                this.$root.toastError(err)
                            }
                            else
                            {
                                this.$root.toastError("Có lỗi trong quá trình đồng bộ lại port cho Account " + this.txtMaTB.Text + " trên Visa !");
                            }
                            return;
                        }
                    }
                }

                if (loaihinh_tb == this.LoaiHinhTB.INTERNET_MYTV)
                {
                    if (!this.txtSystem.Text)
                    {
                        if (`${this.thamso_dslam}` != "4")
                        {
                            this.$root.toastError("Chưa có thông tin về System cũ. Hãy kiểm tra lại !");
                            this.$refs['ref-txt-system'].focus();
                            return;
                        }
                    }

                    if (!this.txtIpCu.Text)
                    {
                        this.$root.toastError("Chưa có thông tin về IP cũ. Hãy kiểm tra lại !");
                        this.$refs['ref-txt-ip-cu'].focus();
                        return;
                    }

                    this.loading(false)

                    let confirmation = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn thực hiện đổi cổng Account " + this.txtMaTB.Text + " không?", {
                        title: 'Thông báo',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                        footerClass: 'p-2',
                        hideHeaderClose: false,
                        centered: true
                    })

                    if (!confirmation)
                    {
                        return
                    }

                    this.loading(true)

                    if (this.DONGBO_TEST.TEST == false)
                    {
                        let sPortDSL = "";
                        let shelf = "0";

                        if (this.maTinh == "BDG")
                        {
                            let portDslamInfo = await this.lay_tt_port_dslam(this.port_cu_id)
                            let vloaitbi_id = !portDslamInfo ? null : `${portDslamInfo.loaitbi_id}`

                            if (vloaitbi_id == "1")
                            {
                                let portCardDslInfo = await this.lay_tt_port_card_dsl(this.port_cu_id)
                                let slot_cu = portCardDslInfo.vitri
                                sPortDSL = `${this.txtIpCu.Text}/${this.txtRack.Text}/${slot_cu}/${this.txtPort.Text}/${this.txtVPIADSL.Text}`;
                            }
                            else if (vloaitbi_id == "3")
                            {
                                sPortDSL = `${this.txtIpCu.Text}/${this.txtRack.Text}/${this.txtSlotADSL.Text}/${this.txtPort.Text}/${this.txtVPIADSL.Text}`
                            }
                            else if (vloaitbi_id == "2")
                            {
                                let portSwitchInfo = await this.lay_tt_port_switch(this.port_cu_id)
                                let slot_cu = portSwitchInfo.vitri
                                sPortDSL = `${this.txtIpCu.Text}/${this.txtRack.Text}/${shelf}/${slot_cu}`;
                            }
                            else if (!vloaitbi_id)
                            {
                                this.$root.toastError("Không xác định được loại thiết bị của dslam tỉnh. Mời bạn chọn lại.");
                            }
                            else
                            {
                                this.$root.toastError("Dslam có loại thiết bị nằm ngoài danh mục kích hoạt.");
                            }
                        }
                        else
                        {
                            if (!!this.txtShelfMoi.Text)
                            {
                                shelf = this.txtShelfMoi.Text;
                            }

                            let portDslamInfo = await this.lay_tt_port_dslam(this.port_cu_id)
                            let vloaitbi_id = !portDslamInfo ? null : `${portDslamInfo.loaitbi_id}`

                            if (vloaitbi_id == "3")
                            {
                                sPortDSL = `${this.txtIpCu.Text}/${shelf}/${this.txtSlotADSL.Text}/${this.txtPort.Text}/${this.txtVPIADSL.Text}`;
                            }
                            else
                            {
                                sPortDSL = `${this.txtIpCu.Text}/${shelf}/${this.txtSlotADSL.Text}/${this.txtPort.Text}`;
                            }
                        }

                        let sDeviceName = !this.txtSystem.Text ? "" : this.txtSystem.Text;
                        let result = await VASCService.UpdateDeviceInfo(this.txtMaTB.Text, sDeviceName, sPortDSL, this);

                        if (result != 0) //lỗi
                        {
                            this.$root.toastError("Có lỗi trong quá trình cập nhật cổng thuê bao " + this.txtMaTB.Text + " trên VASC !");
                            return;
                        }
                        else
                        {
                            this.$root.toastSuccess("Cập nhật thông tin cổng Account " + this.txtMaTB.Text + " trên VASC thành công !");
                        }
                    }
                }

                //dungpa - Đồng bộ thông số BNM
                //----Kiểm tra tham số đồng bộ porrt BNM
                let dsTS = await this.lay_ds_thamso_md_mats("DONGBO_PORT_BNM")
                let dongbo_bnm = -1;

                if (dsTS.length > 0)
                {
                    dongbo_bnm = dsTS[0]["ten_ts"];
                }

                if (dongbo_bnm == 1)
                {
                    await this.dongbo_bnm({
                        thuebao_id: this.thuebao_id,
                        hdtb_id: this.hdtb_id,
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id
                    })
                }

                this.kt_thaydoi = false;
            }
            finally {
                this.loading(false)
            }
        },
        tsbtnBNMHistory_Click(){
          this.$bvModal.show('thayDoiThongSoInternet_popupLogBNM')
        },
        tsbtnThongTinVRF_Click(){
          this.$bvModal.show("thayDoiThongSoInternet_popupChiTietKetNoiVFI")
        },
        async tsbtnChonIPLANWAN_Click(){

            if (!this.cboBrasADSLMoi.Value || (!this.hdtb_id && !this.thuebao_id))
            {
                return;
            }

            try {
                this.loading(true)
                let result = await this.kt_ip_wan_lan_v2({
                    hdtb_id: this.trangthai_dl == 1 ? this.thuebao_id : this.hdtb_id,
                    trangthai_dl: this.trangthai_dl
                })

                if (result.kq != "1")
                {
                    this.$root.toastError(result.kq);
                    return;
                }
            } finally {
                this.loading(false)
            }

            let brasInfo = this.cboBrasADSLMoi.DataSource.find(item => item.bras_id == parseInt(this.cboBrasADSLMoi.Value))

            this.objIP_WAN_LAN = {
                hdtb_id: this.trangthai_dl == 1 ? this.thuebao_id : this.hdtb_id,
                loaitb_id: parseInt(this.cboLoaihinhTB.Value),
                dichvuvt_id: parseInt(this.cboDichVuVT.Value),
                bras_id: !brasInfo ? 0 : brasInfo.bras_id,
                ma_tb: this.txtMaTB.Text,
                tenbras: !brasInfo ? null : brasInfo.tenbras,
                trangthaidl_id: this.trangthai_dl
            }

            this.$bvModal.show("thayDoiThongSoInternet_popupIP_WAN_LAN")
        },
        tsbtnCapPortGiuVCIVPI_Click() { // task bo sung : BSS-77693 - 27/12/2022
          // if (!this.txtMaTB.Text) {
          //   this.$root.toastError('Bạn hãy nhập mã thuê bao!')
          //   this.$refs['ref-txt-matb'].focus()
          //   return
          // }
          this.$refs.popupDoiCongDS.show()
        },
        async kt_ip_wan_lan_v2(data) {
            try {
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/kt_ip_wan_lan_v2`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        tsbtnThayDoiSLID_Click() {

          if (!this.txtMaTB.Text){
            this.$root.toastError("Hãy tìm kiếm thuê bao cần thay đổi!")
            return
          }

          let vthuebao_id = this.trangthai_dl == 1 ? this.thuebao_id : this.hdtb_id;

          this.$refs['popupThayDoiSLID'].show(this.txtSLID.Text, this.txtSeriModem.Text, this.trangthai_dl, vthuebao_id)
        },
        async btnChonPort_Click() {

            this.thamso_dslam = "-1";

            if (this.tramvt_id == 0)
            {
                this.$root.toastError("Chưa có thông tin về trạm viễn thông!");
                return;
            }

            let diachi_id = -1;

            if (parseInt(this.rdoDanhBaHopDong.Value) == 1)
            {
                let diaChiInfo = await this.lay_tt_diachi_tb(this.thuebao_id)
                diachi_id = diaChiInfo.diachild_id
            }
            else
            {
                let diaChiInfo = await this.lay_tt_diachi_hdtb(this.hdtb_id)
                diachi_id = !diaChiInfo ? null : diaChiInfo.diachild_id

                if (!diachi_id)
                {
                    let hdtbInfo = await this.lay_tt_hd_thuebao(this.hdtb_id)
                    let vthuebao_id = hdtbInfo.thuebao_id

                    diaChiInfo = await this.lay_tt_diachi_tb(vthuebao_id)
                    diachi_id = diaChiInfo.diachild_id
                }
            }

            let _port_cu_id = this.port_cu_id;
            let _kieu = this.KIEULAP.THAYDOI_TSO;
            let modal = this.$refs['ref-chon-port']

            modal.frmChonPort(
                this.tramvt_id,
                this.tramtb_id,
                _port_cu_id,
                _kieu,
                parseInt(this.cboDichVuVT.Value),
                parseInt(this.cboLoaihinhTB.Value),
                diachi_id,
                "-1",
                this.hdtb_id,
                this.thuebao_id,
                this.madoicap,
                this.tramtb_id,
                null,
                (this.txtCongNghe_Moi.id == null || this.txtCongNghe_Moi.id == 0) ? this.txtCongNghe.id : this.txtCongNghe_Moi.id
            )

            modal.show()
        },
        async onSelectPort(port) {
            console.log("port", port);
            this.txtPortMoi.Text = port.port
            this.txtVPIADSLMoi.Text = port.vpi
            this.txtVCIADSLMoi.Text = port.vci
            this.txtSlotADSLMoi.Text = port.slot
            this.txtRackMoi.Text = port.rack
            this.txtShelfMoi.Text = port.shelf
            this.txtSystemMoi.Text = port.system
            this.txtAdsl_Port_Moi.Text = port.vadsl_port;
            this.txtSelfid01Moi.Text = port.selfid01
            this.txtSelfid02Moi.Text = port.selfid02
            this.txtSelfid03Moi.Text = port.selfid03
            this.txtSelfid04Moi.Text = port.selfid04
            this.txtONU_PASS_MOI.Text = port.onu_password;
            this.txtDslamTinhMoi.Text = port.ten_dslam;
            this.txtCongNghe_Moi.id = port.congnghe_id;
            this.txtCongNghe_Moi.Text = port.congnghe;
            if (port.ip_dslam_tinh != "-1")
            {
                this.txtIpMoi.Text = port.ip_dslam_tinh;
            }
            else
            {
                this.txtIpMoi.Text = "";
            }

            this.thamso_dslam = port.thamso_dslam;
            this.txtPortMDFMoi.Text = port.port_mdf;
            this.port_moi_id = port.port_id;
            this.vci_vpi_moi_id = port.vci_vpi_id;
            this.tramtb_id = port.tramtb_id;
            this.cboBrasADSLMoi.Value = port.bras_id_vdc;
            this.cboDSLAMMoi.Value = port.dslam_id_vdc;
            this.$refs['ref-txt-port'].focus();

            let portInfo = await this.lay_tt_vatly_theo_port_logic(this.port_moi_id)

            if (!!portInfo)
            {
                this.txtSlotOLT_Moi.Text = portInfo["ten_card"]
                this.txtPortOLT_Moi.Text = portInfo["vitri"]
                this.txtSubSlot_Moi.Text = portInfo["sub_slot"]
            }

            this.txtThongTinCTMoi.Text = port.Thongtin_CT_Dslam;
            this.ketcuoi_moi_id = port.ketcuoi_id;
            this.vitri_moi_id = port.vitri;
        },
        async btnDoiMK_IMS_Click() {

            if (this.DONGBO_TEST.TEST)
            {
                return
            }

            try {
                if (this.maTinh == "THA" || this.maTinh == "LCI")
                {
                    if (this.txtPass_IMS_Moi.Text.length != 12)
                    {
                        this.$root.toastError("Password IMS phải đúng 12 ký tự !");
                        this.$refs['ref-txt-pass-ims-moi'].focus();
                        return;
                    }
                }

                let confirmation = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn thực hiện đổi mật khẩu " + this.txtMaTB.Text + " trên VTN hay không?", {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    footerClass: 'p-2',
                    hideHeaderClose: false,
                    centered: true
                })

                if (!confirmation)
                {
                    return
                }

                this.loading(true)

                let ims_vtn = false;
                let vloaitb_id = parseInt(this.cboLoaihinhTB.Value)

                if (vloaitb_id != this.LoaiHinhTB.IMS_POTS && vloaitb_id != this.LoaiHinhTB.IMS_SIP)
                {
                    let dauSoIms = await this.layDauSoIms()
                    let soMay = `${!dauSoIms ? '' : dauSoIms}${this.txtMaTB.Text}`
                    let kq = VTNService.setPasswordForRoot(this, soMay, this.txtPass_IMS_Moi.Text);

                    if (kq.substr(2, kq.length - 3) == "")
                    {
                        ims_vtn = true;
                    }
                    else
                    {
                        this.$root.toastError(kq);
                        return;
                    }
                }
                else
                {
                    let loaiHinhTbInfo = await this.lay_tt_loaihinh_tb(vloaitb_id)

                    if (!loaiHinhTbInfo || !loaiHinhTbInfo.ma_lhtb)
                    {
                        this.$root.toastError("[DHSX] Không lấy được mã loại hình");
                        return;
                    }

                    let loaitb_id_vtn = loaiHinhTbInfo.ma_lhtb;

                    try {
                        let dauSoIms = await this.layDauSoIms()

                        if (!dauSoIms) {
                            throw "[DHSX] Không có dữ liệu đầu số của tỉnh " + this.maTinh
                        }

                        let soMay = `84${dauSoIms}${this.txtMaTB.Text}`

                        let result = await IMSService.DoiPass(this, soMay, this.txtPass_IMS_Moi.Text, loaitb_id_vtn);

                        if (result.code != 0) { // lỗi
                            throw result.message
                        }

                        ims_vtn = true;
                    } catch(err) {
                        this.$root.toastError("[IMSSV] Có lỗi trong quá trình đổi pass trên IMS mới!\n" + err);
                        return;
                    }
                }

                if (ims_vtn)
                {
                    let result = await this.doi_matkhau_ims({
                        thuebao_id: this.thuebao_id,
                        ma_tb: this.txtMaTB.Text,
                        trangthai_id: this.rdoDanhBaHopDong.Value,
                        password_cu: this.txtPass_IMS.Text,
                        password: this.txtPass_IMS_Moi.Text,
                        ip: this.ip,
                        kieu: this.rdoDanhBaHopDong.Value
                    })

                    if (result.kq == 1)
                    {
                        this.$root.toastSuccess('Đổi mật khẩu thành công !')
                    }
                    else
                    {
                        this.$root.toastError('Đổi mật khẩu thất bại. \n' + result.message)
                    }
                }
            }
            finally
            {
                this.loading(false)
            }
        },
        btnThayDoiSLCG_click(){

            if (parseInt(this.cboLoaihinhTB.Value) != this.LoaiHinhTB.IMS_SIPTRUNKING) {
                this.$root.toastError("Loại hình thuê bao không thuộc Sip Trunking!!!");
                return;
            }

            if (this.trangthai_dl == 1)
            {
                if (!!this.thuebao_id)
                {
                    let dataSoLuongCG = {
                        somay: this.txtMaTB.Text,
                        ttthuebao: this.txtTrangThai.Text,
                        tentb: this.txtTenTB.Text,
                        diachiLd: this.txtDiaChiLD.Text,
                        slCg: this.sl_cuocgoi,
                        trangthai: this.trangthai_dl,
                        vthuebao_id: this.thuebao_id,
                    }

                    this.$refs.thaydoisoluongcuocgoi.show(dataSoLuongCG).then(result => {
                      this.sl_cuocgoi = result.data
                    })
                }
                else
                {
                    this.$root.toastError("Chưa có thông tin thuê bao để thay đổi");
                }
            }
            else
            {
                if (!!this.hdtb_id)
                {
                    let dataSoLuongCG = {
                        somay: this.txtMaTB.Text,
                        ttthuebao: this.txtTrangThai.Text,
                        tentb: this.txtTenTB.Text,
                        diachiLd: this.txtDiaChiLD.Text,
                        slCg: this.sl_cuocgoi,
                        trangthai: this.trangthai_dl,
                        vhdtb_id: this.hdtb_id,
                    }

                    this.$refs.thaydoisoluongcuocgoi.show(dataSoLuongCG).then(result => { })
                }
                else
                {
                    this.$root.toastError("Chưa có thông tin thuê bao để thay đổi");
                }
            }
        },
        tsbThayDoiSLTV_Click() {
            this.dataThayDoiSLTV = {thuebao_id: this.thuebao_id, ma_tb: this.txtMaTB.Text, trangthaitb: this.txtTrangThai.Text, tentb: this.txtTenTB.Text, diachi_ld: this.txtDiaChiLD.Text}
            this.$bvModal.show('thayDoiThongSoInternet_popupThayDoiSLTV');
        },
        tsbtnTBCungDoiCap_Click() {

            let dichvuvt_id = parseInt(this.cboDichVuVT.Value)

            if (!this.thuebao_id || !dichvuvt_id)
            {
                return;
            }

            if (this.madoicap == null || typeof this.madoicap == "undefined")
            {
                return
            }

            this.dataThueBaoCungCap = {
                thuebao_id: this.trangthai_dl == 1 ? this.thuebao_id : -1,
                hdtb_id: this.trangthai_dl == 1 ? -1 : this.hdtb_id,
                dichvuvt_id: dichvuvt_id,
                madoicap: this.madoicap
            }

            this.$bvModal.show('thayDoiThongSoInternet_popupThueBaoCungCap');
        },
        async chapNhanTBCungDoiCap(data) {
            this.Clear();
            this.txtSoAo.Text = "";
            this.$refs['ref-txt-matb'].focus();
            this.txtMaTB.Text = data.ma_tb;
            this.cboDichVuVT.Value = data.dichvuvt_id + "";
            this.thuebao_id = data.thuebao_id;
            this.hdtb_id = data.hdtb_id;

            try {
                this.loading(true)
                await this.HienThiThongTin();
            } finally {
                this.loading(false)
            }
        },
        /***
         * Ghi lại biến động thay đổi thông tin
         * @param SLID
         * @param SRMD
         * @constructor
         */
        async GhiBienDong_TDTT(SLID, SRMD) {
            try {
                this.loading(true)

                let dichvuvt_id = parseInt(this.cboDichVuVT.Value)
                let result = null
                let data = null

                if (dichvuvt_id == this.DichVuVienThong.ADSL) {
                    data = this.TaoDuLieu_ADSL(SLID, SRMD);
                    result = await this.insert_tdtt_db_adsl(data)
                }

                if (dichvuvt_id == this.DichVuVienThong.IMS) {
                    data = this.TaoDuLieu_IMS();
                    result = await this.insert_tdtt_db_ims(data)
                }

                if (!!result)
                {
                    if (result.kq == 0)
                    {
                        this.$root.toastError("Có lỗi xảy ra khi thay đổi thông tin danh bạ.\n" + result.message)
                    }
                    else
                    {
                        this.tdtt_db_id = result.tdtt_db_id
                    }
                }
            } finally {
                this.loading(false)
            }
        },
        async CAPNHAT_TS_KHAC(vthuebao_id, trangthai_dl, vslid, vseri_md){
          try {
            this.loading(true)
            await this.$root.context.post("web-quantri/thaydoi-thongso-internet/capnhat-ts-khac",{
              THUEBAO_ID:vthuebao_id,
              TRANGTHAI_DL:trangthai_dl,
              SLID:vslid,
              SERI_MD:vseri_md
            })
          }catch (e){
            console.log(e)
          }finally {
            this.loading(false)
          }
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
        ddmmyyyy(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y}`
        },
        btnUpdateADSLPort(e){
            let maTb = this.txtMaTB.Text;
            let formStatus = this.formADSLControl.isDisable;
            this.formADSLControl.isDisable = !this.formADSLControl.isDisable;
            this.formADSLControl.iconLabel = formStatus ? this.formADSLControl.icon.save : this.formADSLControl.icon.edit;
            let adslPort = this.txtAdsl_Port_Cu.Text;
    
            if(!formStatus){
                console.log("maTb",maTb);
                 if (maTb == null  || typeof maTb === "undefined" || maTb == ""){
                     this.$root.toastError("Không tìm thấy thuê bao");
                 }else{    
                     this.updateADSLPort(maTb, adslPort);
                 }
            }
        },
        async updateADSLPort(matb, adslPort) {
            try {
              //
                this.loading(true)
                //let maTb = txtMaTB.Text;
                let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/capnhat_adsl_port`, {
                    ma_tb: matb,
                    adsl_port: adslPort
                });
               // console.log(response.data);
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    if( response.data.kq === 1){
                        this.$root.toastSuccess("Cập nhật thành công!");
                    }else{
                        this.$root.toastError(response.data.message);
                    }
                    
                }
                //this.$root.toast
                
                this.loading(false)
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
    }
}
</script>
<style>
    .frm-thay-doi-thong-so-internet .disable-btn:hover {
        background-color: white !important;
    }

    .frm-thay-doi-thong-so-internet .disable-btn a {
        color: #6c757d !important;
    }

    .frm-thay-doi-thong-so-internet .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .frm-thay-doi-thong-so-internet .list-actions-top .list li {
        padding-right: 5px;
    }

    .frm-thay-doi-thong-so-internet .check-action:focus-within {
        border-width: 1px;
        border-color: #80bdff;
        -webkit-box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
        box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
    }

    .frm-thay-doi-thong-so-internet .menu-item-tb-cung-cap-2,
    .frm-thay-doi-thong-so-internet .menu-item-thay-doi-sltv-2,
    .frm-thay-doi-thong-so-internet .menu-item-thay-doi-slcg-2,
    .frm-thay-doi-thong-so-internet .menu-item-thong-tin-vrf-2 {
        display: none !important;
    }

    @media (max-width: 1350px) {
        .frm-thay-doi-thong-so-internet .menu-item-tb-cung-cap-1 {
            display: none !important;
        }

        .frm-thay-doi-thong-so-internet .menu-item-tb-cung-cap-2 {
            display: block !important;
        }
    }

    @media (max-width: 1500px) {
        .frm-thay-doi-thong-so-internet .menu-item-thay-doi-sltv-1 {
            display: none !important;
        }

        .frm-thay-doi-thong-so-internet .menu-item-thay-doi-sltv-2 {
            display: block !important;
        }
    }

    @media (max-width: 1650px) {
        .frm-thay-doi-thong-so-internet .menu-item-thay-doi-slcg-1 {
            display: none !important;
        }

        .frm-thay-doi-thong-so-internet .menu-item-thay-doi-slcg-2 {
            display: block !important;
        }
    }

    @media (max-width: 1800px) {
        .frm-thay-doi-thong-so-internet .menu-item-thong-tin-vrf-1 {
            display: none !important;
        }

        .frm-thay-doi-thong-so-internet .menu-item-thong-tin-vrf-2 {
            display: block !important;
        }
    }
</style>
