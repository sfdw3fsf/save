<template src='./ChangeModem.html'></template>
<style scoped src='./ChangeModem.scss'></style>
<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import Vue from "vue";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue"
import moment from 'moment'
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue';
import SearchAccountModal from './components/popups/SearchAccountModal.vue'
import ChiTietTienKM from './components/popups/ChiTietTienKM.vue'
import DataGrid2 from '@/components/Controls/DataGrid2'
import PopupNhapFileExcelVattuThuebao from "./components/popups/popup-nhapfile.vue";
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts';
import { ButtonPlugin } from '@syncfusion/ej2-vue-buttons';
import { TabPlugin } from '@syncfusion/ej2-vue-navigations';
import { ListBoxPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { L10n, setCulture } from '@syncfusion/ej2-base';
import ChiTietTienTheoKhoanMucModal from '../ChangeSubsType/popups/ChiTietTienTheoKhoanMucModalV2.vue'
Vue.use(ListBoxPlugin);
Vue.use(SplitterPlugin);
Vue.use(ButtonPlugin); 
Vue.use(TabPlugin);
L10n.load({
    'vi-VN': {
        'listbox': {
            'noRecordsTemplate': "", // provide your own text here 
        }
    }
});
import ModalChooseDevices from './components/popups/popupChooseDevices.vue';
import ModalBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices.vue";
import ModalTienKM from "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue";
import EventBus from '@/utils/eventBus';
import {
  DichVuVienThong,
  LoaiHopDong,
  LoaiHinhTB,
  KHOANMUC_TT,
  TrangThaiHD,
  TRANGTHAI_DONGBO,
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
        ModalBuyDevices,
        ModalTienKM,
        PopupNhapFileExcelVattuThuebao,
        ChiTietTienKM,
        ModalChooseDevices,
        CapVatTuTB,
        DataGrid2,
        ChiTietTienTheoKhoanMucModal
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
                title: "Thay thế vật tư - Đổi modem Wifi",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Thay thế vật tư",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            txtNgayYC: moment().format('DD/MM/YYYY'),
            txtNgayLapHD: moment().format('DD/MM/YYYY'),
            txtMaGD:"",
            labelFunctionTitle: "",
            txtMaHD:"",
            txtMaTB:"",
            txtTienKM:"",
            txtDiaChiKH:"",
            txtTongTien:"",
            txtTongVAT:"",
            str_vtemp:"",
            accept: false,
            tagForm:0,
            kt_lap_hd_thayday_khac_tram:false,
            ts_sinhma_gd_theo_donvi:false,
            boqua_kt_matb_lap_hdkhac:false,
            khong_hd_thaythe_tamdung:"",
            nhap_gc_hd: false,
            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnXoa: false,
            phieu_id: 0,
            tsbtnHuyBo: false,
            tsbtnGiaoPhieu: false,
            tsbtnHoanThienHS: false,
            dtpNgayYeuCau:true,
            dtpNgayLapHD: true,
            ma_tb:"",
            showTTHD:"",
            hdkh_id: 0,
            dsThueBao: [],
            dsThueBaoTmp: [],
            danhSach: [],
            data_thuebao: [],
            thueBaoChon: [],
            khachhang_id:0,
            headerText0: { text: 'Mua thiết bị' },
            headerText1: { text: 'Chọn thiết bị' },
            tocdotn_id:0,
            hdtb_id:0,
            thuebao_id: 0,
            txtGhiChu:"",
            txtSoAo:"",
            trangbi_id: 3,
            thietbi_dc_id: 0,
            tongtien: 0,
            vat: 0,
            phuongld_id: 0,
            phold_id: 0,
            apld_id: 0,
            khuld_id: 0,
            muccuoctn_id:0,
            doituong_id: 1,
            dtienld: 0,
            tagFrm: "",
            txtMAGD_TT:"",
            Tag: "1",
            quytrinh_id:0,
            huonggiao_tn:0,
            dvatld: 0,
            txtTenTB:"",
            serial_ump:"",
            txtDiaChiLD:"",
            txtDiaChiTB:"",
            txtSerilUMP:"",
            grvHD: true,
            grvBH:false,
            grvTB:false,
            IsReadonly:false,
            stbump: false,
            password: "",
            soao_dau: "",
            soao_cuoi: "",
            txtTenKH:"",
            donvi_id:0,
            txtMaKH:"",
            tdoi_tc_dc: false,
            tdoi_tt_stb: false,
            tongtien:0,
            TBI: {
                txtareaTBI:[],
                tong_tien: 0,
                vat: 0
            },
            dataTienKM: {
                ds:[],
            },
            tong:0,
            tongvat:0,
            checkHD: 'HD',
            checkBH: '',
            checkTB: '',
            matinh_hientai: this.$root.token.getMaTinh(),
            dataSelected: {
                dichvuvt_id:0,
                loaitb_id: "0",
                kieuld_id: 0,
                kieuld_id_luoi: 0,
                thietbidc_id:0,
                kt: "",
                ten_dvvt:""
            },
            nhanvien_id: this.$root.token.getNhanVienID(),
            TRANGTHAI_PH:{MOI:1},
            gan_thuebaoid_khac: false,
            dsCTM_TBI:[],
            dataDevice:{ds:[]},
            options: {
                lstDSDichvu: [],
                lstLoaiHinhTB: [],
                lstKieuYC: [],
                ds_tien_khoanmuc : [],
                dsThamso: [],
                dsHD_BH: [],
                dsTB: [],
                dsPhieu: [],
                dsHDTBDV:[],
                dsHDKH:[],
                TTDBMoRong:[],
                dsHDTB:[],
                dsHDTB_DD:[],
                dsHDTB_ADSL:[],
                dsHDTB_CD:[],
                dsHDTB_GP:[],
                dsHDTB_TSL:[],
                dsHDTB_MGWAN:[],
                dsHDTB_IMS:[],
                dsCTTHD:[],
            },
            modelVatTu: {},
            form: {
                phieu_id: '0',
                loaihd_id: '0',
                hdtb_id: '0',
                thuebao_id: '0',
            },
            tt_nd:{
                ngay_cn:this.$auth.getNgayCapNhat(),
                tentat:"",
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                //ten_dv:this.$auth.getTenDonVi()
            },
        };
    },
    mounted() {
        this.init();
        $( "input" ).each(function( index ) {    
            $('input').attr('name','input-'+index)
        });
    },
    computed: {
    },
    methods: {
        btnLayThietBi_Click() {
            try {
                this.dataDevice.ds = this.dsCTM_TBI;
                this.dataDevice._loaitb_id = this.dataSelected.loaitb_id;
                this.$refs.popupBuyDevices.show();
            } catch (error) {
                console.log(error);
            }
        },
        closeFormDevice(e) {
            this.$refs.popupBuyDevices.hide();
            if(e){
                this.dsCTM_TBI = [...e];
                this.HT_ThietBi_KM();
            }
        },
        closeFormChooseDevice(e){
            this.$refs.popupChooseDevices.hide();
            if(e){
                this.dsCTM_TBI = [...e];
                console.log(this.dsCTM_TBI)
                this.HT_ThietBi_KM();
            }
        },
         async HT_ThietBi() {
            try {
                let vsl_cha = -1,
                tien_tbi = 0,
                vat_tbi = 0,
                soluong = 1,
                tien_kmtbi = 0,
                vat_kmtbi = 0;
                let
                    dTyLe_KMTB = 0,
                    dtien_kmtbi_tmp = 0,
                    dvat_kmtbi_tmp = 0,
                    dtien_tbi_tmp = 0,
                    dvat_tbi_tmp = 0,
                    dtien_tgtb_tratruoc_tmp = 0,
                    dvat_tgtb_tratruoc_tmp = 0,
                    dtien_tgtb_tragop_tmp = 0,
                    dvat_tgtb_tragop_tmp = 0;
                let dsTBI = this.dsCTM_TBI.map(e => {
                    if(e['LOAI_TBI'])
                        return (e['LOAI_TBI'] + " - Số lượng: " +  e['SOLUONG']);
                    return (e['loai_tbi'] + " - Số lượng: " +  e['soluong']);
                });
                this.TBI.txtareaTBI = dsTBI;
            } catch (error) {
                console.log(error);
            }
        },
         async HT_ThietBi_KM() {
            try {
                let vsl_cha = -1,
                tien_tbi = 0,
                vat_tbi = 0,
                soluong = 1,
                tien_kmtbi = 0,
                vat_kmtbi = 0;
                let
                    dTyLe_KMTB = 0,
                    dtien_kmtbi_tmp = 0,
                    dvat_kmtbi_tmp = 0,
                    dtien_tbi_tmp = 0,
                    dvat_tbi_tmp = 0,
                    dtien_tgtb_tratruoc_tmp = 0,
                    dvat_tgtb_tratruoc_tmp = 0,
                    dtien_tgtb_tragop_tmp = 0,
                    dvat_tgtb_tragop_tmp = 0;
                let dsTBI = this.dsCTM_TBI.map(e => {
                    if(e['LOAI_TBI'])
                        return (e['LOAI_TBI'] + " - Số lượng: " +  e['SOLUONG']);
                    return (e['loai_tbi'] + " - Số lượng: " +  e['soluong']);
                });
                this.TBI.txtareaTBI = dsTBI;
                for (let i = 0; i < this.dsCTM_TBI.length; i++)
                {
                    // DataSet ds = bangts.TTCT_ThietBi(Convert.ToInt32(this.dsCTM_TBI.Tables[0].Rows[i]["loaitbi_id"]));
                    // string tenTbi = ds.Tables[0].Rows[0]["LOAI_TBI"].ToString() + " - Số lượng: " + this.dsCTM_TBI.Tables[0].Rows[i]["SOLUONG"].ToString();
                    // lsbDanhSachTBI.Items.Add(tenTbi);
                    this.dsCTM_TBI[i]["SOLUONG"] ? soluong = Number(this.dsCTM_TBI[i]["SOLUONG"]) : 1;
                    this.dsCTM_TBI[i]["soluong"] ? soluong = Number(this.dsCTM_TBI[i]["soluong"]) : 1;
                    // tinh tiền
                    if (this.dsCTM_TBI[i]["TIEN"])
                    {
                        dtien_tbi_tmp = Number(this.dsCTM_TBI[i]["TIEN"]);
                        tien_tbi = tien_tbi + dtien_tbi_tmp * soluong;
                    }
                    if (this.dsCTM_TBI[i]["tien"])
                    {
                        dtien_tbi_tmp = Number(this.dsCTM_TBI[i]["tien"]);
                        tien_tbi = tien_tbi + dtien_tbi_tmp * soluong;
                    }
                    if (this.dsCTM_TBI[i]["VAT"])
                    {
                        dvat_tbi_tmp = Number(this.dsCTM_TBI[i]["VAT"]);
                        vat_tbi = vat_tbi + dvat_tbi_tmp * soluong;
                    }
                    if (this.dsCTM_TBI[i]["vat"])
                    {
                        dvat_tbi_tmp = Number(this.dsCTM_TBI[i]["vat"]);
                        vat_tbi = vat_tbi + dvat_tbi_tmp * soluong;
                    }
                    dTyLe_KMTB = this.dsCTM_TBI[0]["tyle_km"] ? this.dsCTM_TBI[0]["tyle_km"] : 0;
                    dtien_kmtbi_tmp = this.dsCTM_TBI[0]["TIEN_KM"] ? this.dsCTM_TBI[0]["TIEN_KM"] : 0;
                    dvat_kmtbi_tmp = (this.dsCTM_TBI[0]["VAT_KM"]) ? this.dsCTM_TBI[0]["VAT_KM"] : 0;
                    if (dTyLe_KMTB > 0)
                    {
                        dtien_kmtbi_tmp = Math.Round(dtien_tbi_tmp * dTyLe_KMTB / 100);
                        dvat_kmtbi_tmp = Math.Round(dvat_tbi_tmp * dTyLe_KMTB / 100);
                    }
                    this.dsCTM_TBI[i]["TIEN_KM"] = dtien_kmtbi_tmp;
                    this.dsCTM_TBI[i]["VAT_KM"] = dvat_kmtbi_tmp;
                    tien_kmtbi += dtien_kmtbi_tmp * soluong;
                    vat_kmtbi += dvat_kmtbi_tmp * soluong;
                }
                console.log(tien_tbi)
                 console.log(vat_tbi)
                this.LayTongTien_HDTB_TB(tien_tbi, vat_tbi);
                this.HienThiTienKhoanMucMuaThietBi(tien_tbi, vat_tbi);
            } catch (error) {
                console.log(error);
            }
        },
        HienThiTienKhoanMucMuaThietBi(tien_tbi, vat_tbi){
            if (this.options.ds_tien_khoanmuc.length > 0)
            {
                for(let j = 0; j < this.options.ds_tien_khoanmuc.length; j++)
                {
                    if (this.options.ds_tien_khoanmuc[j]["khoanmuctt_id"] == '30')
                    {
                        this.options.ds_tien_khoanmuc[j]["tien"] =+ tien_tbi;
                        this.options.ds_tien_khoanmuc[j]["vat"] =+ vat_tbi;
                    }
                }
            }
        },
        LayTongTien_HDTB_TB(tien_tbi, vat_tbi){
            
            try
            {
                let dtongtien = 0, dtongvat = 0;
                console.log('tien_tbi',tien_tbi )
                console.log('vat_tbi',vat_tbi )
                console.log('txtTongTien',this.txtTongTien )
                console.log('txtTongVAT',this.txtTongVAT )
                dtongtien += tien_tbi + parseInt(this.txtTongTien);
                dtongvat += vat_tbi +  parseInt(this.txtTongVAT);
                console.log('LayTongTien_HDTB_TB',dtongtien )
                this.txtTongTien = dtongtien.toLocaleString('vi', {style : 'currency', currency : 'VND'});
                this.txtTongVAT = dtongvat.toLocaleString('vi', {style : 'currency', currency : 'VND'});
            }
            catch (ex)
            {
                this.$toast.error("" + ex);
            }
        },
        async LoadDSTBIHDTB() {
            try {
                // 
                // let dstbi = _hdtbServices.LayDSThietBi_HDTB(hdtb_id);
                let dsCTM_TBI = [];
                let res = await this.axios.post('/web-hopdong/thanhly/lay_ds_mua_tbi', {
                    hdtb_id: this.hdtb_id
                });
                let dstbi = res.data.data;
                if (dstbi.length > 0) {
                    dstbi.forEach(item => {
                        let row = {};
                        row = item;
                        row.loaitbi_id = Number(item.loaitbi_id);
                        row.loai_tbi = item.loai_tbi;
                        row.hdtb_id = this.hdtb_id;
                        row.soluong = item.soluong;
                        row.tien = Number(item.tien);
                        row.vat = Number(item.vat);
                        row.tien_km = Number(item.tien_km);
                        row.vat_km = Number(item.vat_km);
                        row.tien_tra = Number(item.tien_tra);
                        row.block_dau = Number(item.block_dau);
                        row.block_tiep = Number(item.block_tiep);
                        row.gia_block_tiep = Number(item.gia_block_tiep);
                        row.serial = item.serial;
                        row.sl_cha = Number(item.sl_cha);
                        row.tien_tragop = Number(item.tien_tragop);
                        row.tyle_vat = Number(item.tyle_vat);
                        row.tyle_vat_id = item.tyle_vat_id;
                        row.vat_block_tiep = Number(item.vat_block_tiep);
                        row.vat_tragop = Number(item.vat_tragop);
                        row.vat_tratruoc = Number(item.vat_tratruoc);
                        dsCTM_TBI.push(row);
                    });
                    this.dsCTM_TBI = dsCTM_TBI;
                }
            } catch (error) {
                console.log(error);
            }
        },
     
        OpenpopupTienKM() {
            this.dataTienKM = {
                ds: this.ds_tien_khoanmuc,
                cols: [
                {
                    fieldName: "tentat_kmtt",
                    headerText: "Khoản mục",
                    allowFiltering: false,
                    allowSorting: false,
                    isPrimaryKey: true,
                },
                {
                    fieldName: "tongtien",
                    textAlign: "right",
                    allowEditing: true,
                    editType: "numericedit",
                    format: "N",
                    customAttributes: { class: "tongtien" },
                    headerText: "Tổng tiền",
                    allowFiltering: false,
                    allowSorting: false,
                    edit: "tongtienParam",
                },
                {
                    fieldName: "tien",
                    textAlign: "right",
                    allowEditing: true,
                    editType: "numericedit",
                    format: "N",
                    customAttributes: { class: "cuoclapdat" },
                    headerText: "Tiền",
                    allowFiltering: false,
                    allowSorting: false,
                    edit: "tienParam",
                },
                {
                    fieldName: "vat",
                    textAlign: "right",
                    format: "N",
                    customAttributes: { class: "vatlapdat" },
                    headerText: "Vat",
                    allowFiltering: false,
                    allowSorting: false,
                },
                ],
                keycol: "khoanmuctt_id",
                allowExit: false,
                tieude: "Chi tiết tiền khoản mục",
                tieudebang: "Chi tiết khoản mục thanh toán",
            };
            this.dataTienKM.allowExit = true;
            if(this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length){
                this.ds_tien_khoanmuc.forEach(item => {
                    item['tongtien'] = item['tien'] + item['vat']
                })
            }
            // this.dataTienKM.cols = []
            this.$refs.popupTienKM.show();
        },
        hideModalChooseDevice(){
            this.$refs.popupChooseDevices.hide();
        },
        closeFormTienKM(e) {
            this.$refs.popupTienKM.hide();
            if(e && e.length){
                // this.LayTongTien_HDTB(e);
                let tongtien = 0, tongvat = 0;
                for(let i = 0; i < e.length; i++){
                    tongtien += Number(e[i]['tongtien']);
                    tongvat += Number(e[i]['vat']);
                }
                this.TBI.tong_tien = tongtien.toLocaleString('vi', {style : 'currency', currency : 'VND'});
                this.TBI.vat = tongvat.toLocaleString('vi', {style : 'currency', currency : 'VND'});
                }
        },
        hideModalTienKM() {
            this.$refs.popupTienKM.hide();
        },
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        },
        async init() {
            try {
                this.frmVatTuThueBao_Load();
            } catch (e) {
                this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
            }
        },
        showPopupSearchContract() {
            this.modalSearch = {
                loai_hd_id : LoaiHopDong.DAT_MOI,
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
                    "loaihd_id":LoaiHopDong.THAY_DAY_MAY,
                    "donvi_id":this.tt_nd.donvi_id,
                    "tthd_id":TrangThaiHD.MOI,
                    "nhanvien_id":this.tt_nd.nhanvien_id,
                    "donvi_dl_id":0
                }));
                await this.HienThiTTHopDongKH(dtList);
                this.HienThiTT_DanhBa(dtList[0].ma_tb);
            }
        },
        Nhapfile() {
            this.$bvModal.show("modal-nhapfileVattuThuebao");
        },
        //Click nút tìm theo số máy
        showPopupSearchAccount() {
            this.$refs.searchAccountModal.showModal();
        },
        async acceptSearchAccount(item) {
            this.txtMaTB = item.ma_tb;
        },
        //Click nút Tiền 
        showPopupTienKM() {
            //this.$bvModal.show('popup-ChiTietTienKM');
            this.$refs.chiTietTienTheoKhoanMucModal.showModal()
        },
        acceptTienKM(value){
            this.tongtien = value.tongtien;
            this.vat = value.tongvat;
            for (let i = 0; i < this.options.ds_tien_khoanmuc.length; i++)
            {   
                this.options.ds_tien_khoanmuc[i].tien = parseInt(value.tongtien)
                this.options.ds_tien_khoanmuc[i].vat = parseInt(value.tongvat)
            }
            this.txtTongTien = this.formatPrice(value.tongtien)
            this.txtTongVAT = this.formatPrice(value.tongvat)
            this.$bvModal.hide('popup-ChiTietTienKM')
        },
        acceptTienKM1(ds_tien_km){
            this.options.ds_tien_khoanmuc=ds_tien_km
            if (this.options.ds_tien_khoanmuc.length > 0){
                for(let i=0; i<this.options.ds_tien_khoanmuc.length;i++){
                    if(this.options.ds_tien_khoanmuc[i].khoanmuctt_id==KHOANMUC_TT.KMTT_THAYTHE_THUHOI){
                        this.dtienld=Number(this.options.ds_tien_khoanmuc[i].tien)
                        this.dvatld=Number(this.options.ds_tien_khoanmuc[i].vat)
                    }
                    this.LayTongTien_HDTB()
                }
            }
        },
        //Click nút thanh toán
        async btnThanhToan_OnClick(){
            let input= { 
                "vhdtb_id": this.hdtb_id, 
                "vthuebao_id": this.thuebao_id,
                "vkieu": 0
            }
                                
            let vtth = this.GetData(await api.lay_ds_vattu_thanhtoan_thaythe(this.axios,input));
            if (vtth.length > 0)
            {
                this.$toast.warning("Bạn phải xóa hết vật tư trong hợp đồng mới được thanh toán !");
                return;
            }
            this.$router.push({
                name: "Payment",
                params: { magd: this.txtMaGD}
            });
        },
        //Click nút vật tư
        async tsbtnVatTu_Click() {
            let phieu_id = 0;
            let input= {
                            type:1,
                            param: this.hdtb_id
                        }
            let s = this.GetData(await api.fn_tt_giaophieu(this.axios,input));
            if (s != "" && s != "-1")
                phieu_id = parseInt(s);
            if (phieu_id == 0)
                {
                    this.$toast.warning("Bạn phải ghi lại hợp đồng trước khi chọn vật tư");
                    return;
                }
            this.modelVatTu.phieu_id = phieu_id;
            this.modelVatTu.loaihd_id = LoaiHopDong.VATTU_THUEBAO;
            this.modelVatTu.hdtb_id = this.hdtb_id;
            this.modelVatTu.thuebao_id = this.thuebao_id;
            this.modelVatTu.vdvvt_id = this.dataSelected.dichvuvt_id;
            this.modelVatTu.loaitb_id = this.dataSelected.loaitb_id;
            this.modelVatTu.baohong_id = 0;
            this.modelVatTu.tagfrm = 1;
            this.openDialog('popupVatTuThueBao');
        },
         openDialog(obj) {
            this.$refs[obj].openDialog();
        },
        //Get danh sách dịch vụ theo người dùng
        async getDSDichVu() {
            try 
            {
                await api.getDSDichVu(this.axios)
                .then((res) => {
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        this.options.lstDSDichvu = res.data.data;
                         this.dataSelected.dichvuvt_id = this.options.lstDSDichvu[2].DICHVUVT_ID;
                         this.dataSelected.ten_dvvt =  this.options.lstDSDichvu[2].TEN_DVVT;
                        this.getLoaiHinhTB(this.dataSelected.dichvuvt_id);
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy danh sách dịch vụ: "+ err.message);
            }
        },
        //Lấy danh sách loại hình thuê bao
        async getLoaiHinhTB (dvvtid) {
            try 
            {
                await api.getLoaiHinhTB(this.axios, dvvtid)
                    .then((res) => {
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        this.dataSelected.ten_dvvt = this.options.lstDSDichvu.filter((item) => item.DICHVUVT_ID == dvvtid)[0].TEN_DVVT
                        this.options.lstLoaiHinhTB = res.data.data;
                        this.dataSelected.loaitb_id = this.options.lstLoaiHinhTB[0].ID.toString();
                        this.getDmKieuYC(this.dataSelected.loaitb_id);
                        this.getDsThietbiDC(this.dataSelected.loaitb_id);
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy danh sách loại hình: "+ err.message);
            }
        },
        //Lấy danh mục kiểu lắp đặt
        async getDmKieuYC (loaitb_id) {
            try 
            {
                await api.getDmKieuYC(this.axios,{
                    "loaihd_id": 13,"loaitb_id":loaitb_id
                }).then((res) => {
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        this.options.lstKieuYC = res.data.data;
                        this.dataSelected.kieuld_id = this.options.lstKieuYC[0].kieuld_id;
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy danh sách kiểu lắp đặt: "+ err.message);
            }
        },
        //Lấy danh mục loại thiết bị
        async getDsThietbiDC (loaitb_id) {
            try 
            {
                await api.getDsThietbiDC(this.axios,{
                    "loaitb_id": loaitb_id,
                    "kieu":1
                }).then((res) => {
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        this.options.lstLoaiTBDC = res.data.data;
                        this.dataSelected.thietbidc_id = this.options.lstLoaiTBDC[0].thietbidc_id;
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy danh sách thiết bị: "+ err.message);
            }
        },
        //Lấy tiền theo khoản mục
        async LaytientheoKM_Loaihd () {
            try 
            {
                await api.LaytientheoKM_Loaihd(this.axios,{
                    "loaihd_id": LoaiHopDong.THAY_DAY_MAY,
                }).then((res) => {
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        this.options.ds_tien_khoanmuc = res.data.data;
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy tiền theo khoản mục: "+ err.message);
            }
        },
        //Lấy danh sách tham số
        getThamSo () {
            try
            {
                api.getThamSo(this.axios,{
                "vkieu_id": -1
                }).then((res) => {
                   
                    if (res.data.error_code === "BSS-00000000" &&res.data.data !== undefined &&res.data.data.length > 0) 
                    {
                        this.options.dsThamso = res.data.data;
                        if (this.options.dsThamso.length > 0)
                        {
                            let str_vtemp = "KT_LAP_HD_THAYDAY_KHAC_TRAM";
                            let data_TS = this.options.dsThamso.filter((item) => item.ma_ts == str_vtemp);
                            if (data_TS.length > 0) {
                                if (data_TS[0].ten_ts.toString().trim() == "1") this.kt_lap_hd_thayday_khac_tram = true;
                            }
                            this.ts_sinhma_gd_theo_donvi = this.options.dsThamso.filter((item) => item.ma_ts == "SINH_MA_GD_THEO_DV").length > 0;
                            str_vtemp = "BOQUA_KT_MATB_LAP_HDKHAC";
                            data_TS = this.options.dsThamso.filter((item) => item.ma_ts == str_vtemp);
                          
                            if (data_TS.length > 0) {
                                if (data_TS[0].ten_ts.toString().trim() == "1")
                                    this.boqua_kt_matb_lap_hdkhac = true;
                            }
                            str_vtemp = "TAMDUNG_HD_THAYTHE";
                            data_TS = this.options.dsThamso.filter((item) => item.ma_ts == str_vtemp);
                            if (data_TS.length > 0) {
                                this.khong_hd_thaythe_tamdung = data_TS[0].ten_ts.toString().split(',');
                            }
                            str_vtemp = "CHOPHEP_THUEBAOID_KHAC_LOAIHD13";
                            data_TS = this.options.dsThamso.filter((item) => item.ma_ts == str_vtemp);
                            if (data_TS.length > 0) {
                                 if (data_TS[0].ten_ts.toString().trim() == "1")
                                    this.gan_thuebaoid_khac = true;
                            }
                        
                            str_vtemp = "NHAPGHICHU_HD_THAYTHE";
                            data_TS = this.options.dsThamso.filter((item) => item.ma_ts == str_vtemp);
                            if (data_TS.length > 0) {
                                if (data_TS[0].ten_ts.toString().trim() == "1")
                                    this.nhap_gc_hd = true;
                            }
                        }
                        if (this.tt_nd.tentat == "HNI" || this.tt_nd.tentat == "HYN") this.tsbtnFileExcel = true;
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy danh sách tham số: "+ err.message);
            }
        },
        // Tạo dữ liệu cho Dataset
        async TaoDuLieu(themmoi)
        {
            await this.TaoDuLieu_HDKH(themmoi);
            await this.TaoDuLieu_HDTB(themmoi);
            await this.TaoDuLieu_HDTBDV();
            await  this.TaoDuLieu_CT_TIENHD(true);
            this.TaoDuLieu_DSMUATBI(themmoi)
            //tạo dữ liệu cho các bảng con để thiết lập giao phiếu về các trạm - 6.11.2010
            if (parseInt(this.dataSelected.dichvuvt_id) == DichVuVienThong.DI_DONG)
                await this.TaoDuLieu_HDTB_DD(themmoi);
            if (parseInt(this.dataSelected.dichvuvt_id) == DichVuVienThong.ADSL)
                await this.TaoDuLieu_HDTB_ADSL(themmoi);
            if (parseInt(this.dataSelected.dichvuvt_id) == DichVuVienThong.CO_DINH)
                await this.TaoDuLieu_HDTB_CD(themmoi);
            if (parseInt(this.dataSelected.dichvuvt_id) == DichVuVienThong.GPHONE)
                await this.TaoDuLieu_HDTB_GP(themmoi);
            if (parseInt(this.dataSelected.dichvuvt_id) == DichVuVienThong.TSL)
                await this.TaoDuLieu_HDTB_TSL(themmoi);
            if (parseInt(this.dataSelected.dichvuvt_id) == DichVuVienThong.MEGAWAN)
                await this.TaoDuLieu_HDTB_MGWAN(themmoi);
            if (parseInt(this.dataSelected.dichvuvt_id) == DichVuVienThong.IMS)
                await this.TaoDuLieu_HDTB_IMS(themmoi);
        },
        //TaoDuLieu_HDTB_DV
        async TaoDuLieu_HDTBDV()
        {
            try 
            {
                this.options.dsHDTBDV = []
                await api.lay_ds_donvi_theo_tbid(this.axios,{"thueBaoId": this.thuebao_id}).then((res) => {
                    
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        let rowdata = []
                        for (let i = 0; i < res.data.data.length; i++)
                        {
                            let row = {}
                            row.HDTB_ID = this.hdtb_id;
                            row.DONVI_ID = res.data.data[i].donvi_id;
                            row.LOAIDV_ID = res.data.data[i].loaidv_id;
                            row.KIEUDV_ID = res.data.data[i].kieudv_id;
                            rowdata.push(row);
                        }
                        this.options.dsHDTBDV = rowdata;
                        
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi tạo dữ liệu hợp đồng thuê bao đơn vị: "+ err.message);
            }
        },
        //Tao du lieu cho bang HD_KHACHHANG
        async TaoDuLieu_HDKH(themmoi)
        {  
            this.options.dsHDKH = [];
            var rowHDKH = {};
            let loi_sinh_magd = false;
            if (themmoi == true)
                this.hdkh_id = await this.getKey("HD_KHACHHANG");
            rowHDKH.HDKH_ID = this.hdkh_id;
            if (themmoi == true)
            {
                if (!this.ts_sinhma_gd_theo_donvi){
                    this.txtMaGD = await this.lay_magd(LoaiHopDong.VATTU_THUEBAO);
                }
                else
                {
                    let input = {
                        "vdonvi_id": this.tt_nd.donvi_id,
                        "vloaihd_id": LoaiHopDong.VATTU_THUEBAO
                    }
                    let vmagd_temp = await this.lay_magd_v2(this.tt_nd.donvi_id,LoaiHopDong.VATTU_THUEBAO);
                    if (vmagd_temp.includes("ERROR:"))
                    {
                        this.$toast.warning(vmagd_temp.split(':')[1]);
                        loi_sinh_magd = true;
                    }
                    else
                    {
                        this.txtMaGD = vmagd_temp;
                    }
                }
                rowHDKH.MA_GD = this.txtMaGD;
                rowHDKH.MA_HD = this.txtMaHD;
            }
            else
            {
                rowHDKH.MA_GD = this.txtMaGD;
                rowHDKH.MA_HD = this.txtMaHD;
            }
            rowHDKH.MA_KH = this.txtMaKH;
            if (this.khachhang_id > 0)
                rowHDKH.KHACHHANG_ID = this.khachhang_id;
            rowHDKH.TEN_KH = this.txtTenKH;
            rowHDKH.DIACHI_KH = this.txtDiaChiKH;
            rowHDKH.NGAYLAP_HD = this.txtNgayLapHD;
            rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
            rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;
            rowHDKH.KHLON_ID = 1;
            rowHDKH.LOAIHD_ID = LoaiHopDong.VATTU_THUEBAO;
            rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;
            rowHDKH.MAY_CN = this.tt_nd.may_cn;
            rowHDKH.IP_CN = this.tt_nd.ip;
            rowHDKH.NGAY_YC = this.txtNgayYC;
            this.options.dsHDKH.push(rowHDKH);
        },
        //Tao du lieu cho bang HD_THUEBAO
        async TaoDuLieu_HDTB(themmoi)
        {
            this.options.dsHDTB=[];
             var rowHDTB = {};
            if (themmoi == true)
                this.hdtb_id = await this.getKey("HD_THUEBAO");
            rowHDTB.HDTB_ID = this.hdtb_id;
            rowHDTB.HDKH_ID = this.hdkh_id;
            if (this.thuebao_id > 0)
                rowHDTB.THUEBAO_ID = this.thuebao_id;
            rowHDTB.MA_TB = this.txtMaTB;
            rowHDTB.TEN_TB = this.txtTenTB;
            rowHDTB.DIACHI_TB = this.txtDiaChiTB;
            rowHDTB.DIACHI_LD = this.txtDiaChiLD;
            rowHDTB.GHICHU = this.txtGhiChu;
            rowHDTB.KIEULD_ID = this.dataSelected.kieuld_id;
            rowHDTB.DOITUONG_ID = this.doituong_id;
            rowHDTB.TTHD_ID = TrangThaiHD.MOI;
            rowHDTB.LOAITB_ID = this.dataSelected.loaitb_id;
            rowHDTB.DICHVUVT_ID = this.dataSelected.dichvuvt_id;
            rowHDTB.DONVI_ID = this.donvi_id;
            rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
            rowHDTB.MAY_CN = this.tt_nd.may_cn;
            rowHDTB.QUYTRINH_ID = this.quytrinh_id;      
            this.options.dsHDTB.push(rowHDTB); 
        },
        //Tao du lieu cho bang HDTB_DD
        async TaoDuLieu_HDTB_DD(themmoi)
        {
             this.options.dsHDTB_DD=[]
            let rowHDTBD_DD = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_db_dd_theo_tbid(this.axios, {"in_thuebao_id": this.thuebao_id}));
            rowHDTBD_DD = this.changeKeysToUpper(data[0]);
            rowHDTBD_DD.HDTB_ID = this.hdtb_id;
            this.options.dsHDTB_DD.push(rowHDTBD_DD);
            
        },
        //Tao du lieu cho bang HDTB_ADSL
        async TaoDuLieu_HDTB_ADSL(themmoi)
        {
            this.options.dsHDTB_ADSL=[]
            let  rowHDTB_ADSL = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_adsl(this.axios, {"thuebao_id": this.thuebao_id}));
            rowHDTB_ADSL = data.length>0?this.changeKeysToUpper(data[0]):rowHDTB_ADSL;
            if (this.tdoi_tc_dc)
            {
                if (rowHDTB_ADSL.THIETBIDC_ID != this.dataSelected.thietbidc_id.toString())
                {
                    rowHDTB_ADSL.THIETBIDC_ID = this.dataSelected.thietbidc_id.toString();
                }
            }
            console.log(rowHDTB_ADSL)
            rowHDTB_ADSL.SERIAL_STB = this.txtSerilUMP;
            rowHDTB_ADSL.HDTB_ID = this.hdtb_id;
            this.options.dsHDTB_ADSL.push(rowHDTB_ADSL);
        }, 
        //Tao du lieu cho bang HDTB_CD
        async TaoDuLieu_HDTB_CD(themmoi)
        { 
            this.options.dsHDTB_CD=[]
            let rowHDTB_CD = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_codinh(this.axios, {"thuebao_id": this.thuebao_id}));
            rowHDTB_CD = this.changeKeysToUpper(data[0]);
            rowHDTB_CD.HDTB_ID = this.hdtb_id;
            this.options.dsHDTB_CD.push(rowHDTB_CD);
        },
        //Tao du lieu cho bang HDTB_GP
        async TaoDuLieu_HDTB_GP(themmoi)
        {
            this.options.dsHDTB_GP=[]
            let rowHDTB_GP = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_gp(this.axios, {"thuebao_id": this.thuebao_id}));
            rowHDTB_GP = this.changeKeysToUpper(data[0]);
            rowHDTB_GP.HDTB_ID = this.hdtb_id;
            this.options.dsHDTB_GP.push(rowHDTB_GP);
        },
        //TaoDuLieu_HDTB_TSL
        async TaoDuLieu_HDTB_TSL(themmoi)
        {
             this.options.dsHDTB_TSL=[]
            let rowHDTB_TSL = {};
            let data = [];
            data = this.changeKeysToUpper(this.GetData(await api.Lay_ds_danhba_tsl(this.axios, {"thuebao_id": this.thuebao_id})));
            //data.foreach (row =>
            //{
                rowHDTB_TSL = this.changeKeysToUpper(data[0]);
                rowHDTB_TSL.HDTB_ID = this.hdtb_id;
                this.options.dsHDTB_TSL.push(rowHDTB_TSL);
            //});
        },
        //Tạo dữ liệu cho bảng CT_TIENHD
        async TaoDuLieu_CT_TIENHD(themmoi)
        {
             this.options.dsCTTHD=[]
            for (let i = 0; i < this.options.ds_tien_khoanmuc.length; i++)
            {   
                let rowCTTHD = {};
                rowCTTHD.HDTB_ID = this.hdtb_id;
                if (themmoi == true)
                {
                    let ct_tienhd_id  = await this.getKey("CT_TIENHD");
                    rowCTTHD.CT_TIENHD_ID = ct_tienhd_id;
                }
                rowCTTHD.KHOANMUCTT_ID = Number(this.options.ds_tien_khoanmuc[i].khoanmuctt_id);
                rowCTTHD.TIEN = this.options.ds_tien_khoanmuc[i].tien.toString().replace(/,/g, '');
                rowCTTHD.VAT = this.options.ds_tien_khoanmuc[i].vat.toString().replace(/,/g, ''); 
                rowCTTHD.ID = parseInt(this.options.ds_tien_khoanmuc[i].khoanmuctt_id);
                rowCTTHD.LOAI_ID = 1;
                this.options.dsCTTHD.push(rowCTTHD);
            }
           
        },
        async TaoDuLieu_DSMUATBI(themmoi)
        {
            for (let i = 0; i < this.dsCTM_TBI.length; i++)
            {   
                this.dsCTM_TBI[i].HDTB_ID = this.hdtb_id;
            }
        },
        //Tao du lieu cho bang HDTB_MGWAN
        async TaoDuLieu_HDTB_MGWAN(themmoi)
        {
            this.options.dsHDTB_MGWAN=[]
            let rowHDTBD_MGWAN = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_megawan(this.axios, {"thuebao_id": this.thuebao_id}));
            rowHDTBD_MGWAN = this.changeKeysToUpper(data[0]);
            rowHDTBD_MGWAN.HDTB_ID = this.hdtb_id;
            this.options.dsHDTB_MGWAN.push(rowHDTBD_MGWAN);
        },
            //Tao du lieu cho bang HDTB_IMS
        async TaoDuLieu_HDTB_IMS(themmoi)
        {
            this.options.dsHDTB_IMS=[]
            let rowHDTBD_IMS = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_ims(this.axios, {"thuebao_id": this.thuebao_id}));
            rowHDTBD_IMS = this.changeKeysToUpper(data[0]);
            rowHDTBD_IMS.HDTB_ID = this.hdtb_id;
            this.options.dsHDTB_IMS.push(rowHDTBD_IMS);
        },
        //HienThiTT_DanhBa
        async HienThiTT_DanhBa (txtMaTB){
            
            if (this.dataSelected.dichvuvt_id == 0){
                this.$toast.error("Bạn cần chọn dịch vụ để lấy thông tin!");
            }else{
               
                var input = {
                        "maTb": txtMaTB,
                        "dichVuVtId": this.dataSelected.dichvuvt_id,
                        "donViDlId": null
                }
                let data = [] ;
                let data_obj = this.GetData(await api.getTTDanhBa_MaTB(this.axios, input));
                data.push(data_obj);
               
                if (data[0] != null){
                        // Neu la tam dung => ko lap HD thay thecc 
                        if (this.khong_hd_thaythe_tamdung.includes(data[0].trangthaitb_id.toString()))
                        {
                            this.$toast.warning("Thuê bao " + this.txtMaTB.trim() + " đang ở trạng thái tạm dừng \n Không thể thực hiện thay đổi dịch vụ cho thuê bao này!");
                            return;
                        }
                        let ttph_3000 = this.GetData(await api.kiemtra_phieu_3000_ttph(this.axios, {thuebao_id: data[0].thuebao_id}));
                        if (ttph_3000 != "1")
                        {
                            this.$toast.warning(ttph_3000);
                            return;
                        }
                        this.thuebao_id = parseInt(data[0].thuebao_id.toString());
                        //"Load thông tin danh bạ vào form
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
                        this.dataSelected.dichvuvt_id = data[0].dichvuvt_id;
                        this.dataSelected.loaitb_id = data[0].loaitb_id.toString();
                        //this.dataSelected.kieuld_id = data[0].kieuld_id.toString();
                        let dataDC = this.GetData(await api.Lay_ds_diachi(this.axios,{
                                        "in_thuebao_id":this.thuebao_id
                                    }
                                ));
                        this.HienThiTTDiaChi(dataDC, 3);
                        this.HienThiTTDBMoRong(this.thuebao_id, this.dataSelected.dichvuvt_id);
                        await this.getDsThietbiDC(parseInt(data[0].loaitb_id));
                       
                        let thietbi = this.GetData(await api.lay_tt_tranggbi(this.axios,{
                                        "thuebao_id": data[0].thuebao_id,
                                        "hdtb_id": 0
                                    }
                                ));
                        this.dataSelected.thietbidc_id = thietbi.ret[0].thietbidc_id;
                        try
                        {
                            this.stbump = false;
                            this.password = "";
                            this.serial_ump = "";
                            this.txtSerilUMP = "";
                            this.IsReadonly = true;
                            if (this.dataSelected.loaitb_id == LoaiHinhTB.INTERNET_MYTV)
                            {
                                let kq = this.GetData(await api.Lay_tt_thuebao_mytv(this.axios,{
                                        "vthuebao_id": this.thuebao_id,
                                        "vhdtb_id": this.hdtb_id
                                    }
                                ));
                                
                                if (kq != null)
                                {
                                
                                    if (kq[0].thietbidc_id.toString() == "157")
                                    {
                                        this.password = kq[0].password.toString();
                                        this.IsReadonly = false;
                                        this.stbump = true;
                                        
                                    }
                                }
                            }
                        }
                        catch (ex)
                        {
                            this.$toast.warning("Có lỗi khi lấy thông tin STB UMP bạn hãy lấy lại thông tin nếu bạn muốn nhập serial UMP " + ex.message);
                            this.IsReadonly = true;
                        }   
                }else {
                    this.$toast.warning("Không tìm thấy thông tin về mã thuê bao " + this.txtMaTB.trim() + " đối với dịch vụ " + this.dataSelected.ten_dvvt);
                }
            }   
        },
        //HienThiTTDBMoRong
        async HienThiTTDBMoRong(thuebao_id,dichvuvt_id)
        {
            console.log(thuebao_id)
            console.log(dichvuvt_id)
            switch (dichvuvt_id)
            {
                case DichVuVienThong.ADSL:
                    this.options.TTDBMoRong = this.GetData(await api.Lay_ds_danhba_adsl(this.axios, {"thuebao_id":this.thuebao_id}));//1331480
                    break;
                case DichVuVienThong.MEGA_EYES:
                     this.options.TTDBMoRong = this.GetData(await api.Lay_ds_danhba_adsl(this.axios, {"thuebao_id":this.thuebao_id}));//1331480
                    break;
                case DichVuVienThong.MEGAWAN:
                     this.options.TTDBMoRong = this.GetData(await api.Lay_ds_danhba_megawan(this.axios, {"thuebao_id":this.thuebao_id}));//505232
                    break;
                case DichVuVienThong.TSL:
                     this.options.TTDBMoRong = this.GetData(await api.Lay_ds_danhba_tsl(this.axios, {"thuebao_id":this.thuebao_id}));//503815
                    break;
                case DichVuVienThong.IMS:
                     this.options.TTDBMoRong = this.GetData(await api.Lay_ds_danhba_ims(this.axios, {"thuebao_id":this.thuebao_id}));//503815
                    break;
                default:
                     this.options.TTDBMoRong = this.GetData(await api.Lay_ds_danhba_codinh(this.axios, {"thuebao_id":this.thuebao_id}));//18514
                    break;
            }
             this.dataSelected.thietbidc_id = 0;
            if (this.options.TTDBMoRong.length > 0)
            {
                this.tocdotn_id = 0;
                this.muccuoctn_id = 0;
                this.txtSoAo = "";
                switch (dichvuvt_id) 
                {
                    case DichVuVienThong.CO_DINH:
                        if (this.options.TTDBMoRong[0].trangbi_id.toString().trim() != "")
                            this.trangbi_id = parseInt(this.options.TTDBMoRong[0].trangbi_id.toString().trim());
                        else
                            this.trangbi_id = 3;
                        this.tocdotn_id = 0;
                        this.muccuoctn_id = 0;
                        break;
                    case DichVuVienThong.IMS:
                        if (this.options.TTDBMoRong[0].trangbi_id.toString().trim() != "")
                            this.trangbi_id = parseInt(this.options.TTDBMoRong[0].trangbi_id.toString().trim());
                        else
                            this.trangbi_id = 3;
                        this.tocdotn_id = 0;
                        this.muccuoctn_id = 0;
                        break;
                    case DichVuVienThong.ADSL:
                        if (this.options.TTDBMoRong[0].tocdo_id.toString().trim() != "")
                            this.tocdotn_id = parseInt(this.options.TTDBMoRong[0].tocdo_id.toString().trim());
                        if (this.loaitb_id != LoaiHinhTB.INTERNET_MYTV && this.loaitb_id != LoaiHinhTB.INTERNET_SGTV
                            && this.loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_ANALOG && this.loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_DIGITAL)
                        {
                            if (this.options.TTDBMoRong[0].muccuoc_id.toString().trim() != "")
                                this.muccuoctn_id = parseInt(this.options.TTDBMoRong[0].muccuoc_id.toString().trim());
                        }
                        else //MyTV và SGTV có muccuoc = 0 => trọn gói
                            this.muccuoctn_id = 0;
                        if (this.options.TTDBMoRong[0].trangbi_id.toString().trim() != "")
                            this.trangbi_id = parseInt(this.options.TTDBMoRong[0].trangbi_id.toString().trim());
                        else
                            this.trangbi_id = 3;
                        if (this.options.TTDBMoRong[0].thietbidc_id.toString() != "")
                        {
                            this.thietbi_dc_id = parseInt(this.options.TTDBMoRong[0].thietbidc_id.toString());
                        }
                        this.dataSelected.thietbidc_id = this.thietbi_dc_id;
                        this.txtSoAo = this.options.TTDBMoRong[0].ma_lt.toString();
                        break;
                    case DichVuVienThong.MEGA_EYES:
                        if (this.options.TTDBMoRong[0].tocdo_id.toString().trim() != "")
                            tocdotn_id = parseInt(this.options.TTDBMoRong[0].tocdo_id.toString().trim());
                        if (this.options.TTDBMoRong[0].trangbi_id.toString().trim() != "")
                            this.trangbi_id = parseInt(this.options.TTDBMoRong[0].trangbi_id.toString().trim());
                        else
                            this.trangbi_id = 3;
                        this.txtSoAo = this.options.TTDBMoRong[0].ma_lt.toString();
                        break;
                    case DichVuVienThong.MEGAWAN:
                        if (this.options.TTDBMoRong[0].tocdo_id.toString().trim() != "")
                            this.tocdotn_id = parseInt(this.options.TTDBMoRong[0].tocdo_id.toString().trim());
                        if (this.options.TTDBMoRong[0].trangbi_id.toString().trim() != "")
                            this.trangbi_id = parseInt(this.options.TTDBMoRong[0].trangbi_id.toString().trim());
                        else
                            this.trangbi_id = 3;
                        this.txtSoAo = this.options.TTDBMoRong[0].ma_lt.toString();
                        break;
                    case DichVuVienThong.TSL:
                        if (this.options.TTDBMoRong[0].tocdo_id.toString().trim() != "")
                            this.tocdotn_id = parseInt(this.options.TTDBMoRong[0].tocdo_id.toString().trim());
                        for (let i = 0; i < this.options.TTDBMoRong.length; i++)
                        {
                            if (this.options.TTDBMoRong[i].daucuoi_id.toString() == "1")
                                this.soao_dau = this.options.TTDBMoRong[i].ma_lt.toString();
                            else
                                this.soao_cuoi = this.options.TTDBMoRong[i].ma_lt.toString();
                        }
                        if (this.options.TTDBMoRong[0].trangbi_id.toString().trim() != "")
                             this.trangbi_id = parseInt(this.options.TTDBMoRong[0].trangbi_id.toString().trim());
                        else
                             this.trangbi_id = 3;
                        this.txtSoAo = this.soao_dau + "," + this.soao_cuoi;
                        break;
                }
            }
        },
        NullOrEmpty(value){
            if(value == undefined || value == null  || value == 'undefined' || value == 'null')
            return true;
            return false;
        },
        //HienThiTTDiaChi
        HienThiTTDiaChi(ds,kieu)
        {
            if (ds.length > 0)
            {
                if (kieu == 3)
                {
                    if (!this.NullOrEmpty(ds[0].phuongld_id))
                        this.phuongld_id = parseInt(ds[0].phuongld_id);
                    if (!this.NullOrEmpty(ds[0].phold_id))
                        this.phold_id = parseInt(ds[0].phold_id);
                    if (!this.NullOrEmpty(ds[0].apld_id))
                        this.apld_id = parseInt(ds[0].apld_id);
                    if (!this.NullOrEmpty(ds[0].khuld_id))
                        this.khuld_id = parseInt(ds[0].khuld_id);
                }
            }
        },
        //HienThiTTHopDongKH
        async HienThiTTHopDongKH(ds)
        {
           
            try
            {
                if (ds.length > 0)
                {
                    
                    this.hdkh_id = parseInt(ds[0].hdkh_id);
                    this.txtMaGD = ds[0].ma_gd.toString();
                    this.txtMaHD = ds[0].ma_hd.toString();
                    this.txtMaKH = ds[0].ma_kh.toString();
                    this.txtGhiChu= ds[0].ghichu;
                    this.so_gt = ds[0].so_gt;
                    this.so_dt = ds[0].so_dt;
                    if (ds[0].khachhang_id != "")
                        this.khachhang_id = parseInt(ds[0].khachhang_id.toString());
                    this.txtTenKH = this.ChuanHoaTen(ds[0].ten_kh.toString());
                    this.txtDiaChiKH = this.ChuanHoaTen(ds[0].diachi_kh.toString());
                    this.txtNgayLapHD = moment(ds[0].ngaylap_hd).format('DD/MM/YYYY');
                    if (ds[0].ngay_yc != "")
                        this.txtNgayYC = moment(ds[0].ngay_yc).format('DD/MM/YYYY');
                    this.dataSelected.dichvuvt_id = parseInt(ds[0].dichvuvt_id);
                    await this.getLoaiHinhTB(this.dataSelected.dichvuvt_id);
                    this.dataSelected.loaitb_id = parseInt(ds[0].loaitb_id);
                    await this.getDmKieuYC(this.dataSelected.loaitb_id);
                    this.dataSelected.kieuld_id = parseInt(ds[0].kieuld_id);
                    await this.getDsThietbiDC(parseInt(ds[0].loaitb_id));
                    this.SetButton(3);
                    this.HienThiDanhSacHDTB();
                }
                else
                {
                    this.options.dsPhieu = null;
                    this.$toast.error("Không tìm thấy thông tin hợp đồng ");
                    this.SetButton(0);
                }
            }
            catch (err)
            {
                this.$toast.error("Lỗi hiển thị danh sách hợp đồng khách hàng: "+ err.message);
            }
        },
        //HienThiDanhSacHDTB
        async HienThiDanhSacHDTB()
        {
            try
            {  
                let dtList = this.GetData(await api.Lay_ds_hopdong_tb_theo_hdkh_id(this.axios, {"in_hdkh_id": this.hdkh_id}));                
                
                if (dtList.length > 0)
                {
                    this.options.dsPhieu = dtList;
                }
                else
                {
                    this.options.dsPhieu = [];
                    this.SetButton(1);
                }
            }
            catch (err)
            {
                this.$toast.error("Lỗi hiển thị danh sách hợp đồng thuê bao: "+ err.message);
            }
        },
        //Nhấn nút hiện popup số máy
         async txtMaTB_KeyPress() {
            if (this.txtMaTB != "") {
                let ds = this.GetData(await api.Lay_ds_hopdong_theo_ma_tb(this.axios, 
                 {
                     "ma_tb":this.txtMaTB,//hpgmytv128442
                     "in_loaihd_id":LoaiHopDong.VATTU_THUEBAO,
                     "in_donvi_id":this.tt_nd.donvi_id,
                     "in_tthd_id":TrangThaiHD.MOI,
                     "in_nhanvien_id":this.tt_nd.nhanvien_id,
                     "in_donvi_dl_id":0,
                     "in_dichvuvt_id":this.dataSelected.dichvuvt_id,
                     "in_loaitb_id":0,
                }));
                if (ds.length > 0)
                {
                    this.thuebao_id = parseInt(ds[0].thuebao_id.toString());
                    this.hdtb_id = ds[0].hdtb_id;
                    this.hdkh_id= ds[0].hdkh_id;
                    await this.HienThiTTHopDongKH(ds);
                    this.HienThiTT_DanhBa(ds[0].ma_tb);
                }
                else
                {
                    this.HienThiTT_DanhBa(this.txtMaTB);
                }
            }
            
        },
        //Nhấn nút hiện popup mã giao dịch
        async txtMaGD_KeyPress()
        {
            if (this.txtMaGD != "")
                {
                    let dtList = this.GetData(await api.Lay_ds_hopdong_theo_ma_gd(this.axios, 
                        {
                            "ma_gd":this.txtMaGD,
                            "loaihd_id":LoaiHopDong.THAY_DAY_MAY,
                            "donvi_id":this.tt_nd.donvi_id,
                            "tthd_id":TrangThaiHD.MOI,
                            "nhanvien_id":this.tt_nd.nhanvien_id,
                            "donvi_dl_id":0
                        }));
                   await this.HienThiTTHopDongKH(dtList);
                   this.HienThiTT_DanhBa(dtList[0].ma_tb);
                }
                this.$refs.MaTB.focus();
        },
        //Load thông tin vật tư thuê bao
        frmVatTuThueBao_Load()
        {
            try
            {
                this.txtNgayYC = this.tt_nd.ngay_cn;
                this.SetButton(-1);
                this.LaytientheoKM_Loaihd();
                this.getDSDichVu();
                if (this.Tag != null)
                {
                    this.tagForm = this.Tag.toString();
                }
                else
                {
                    this.tagForm = "0";
                }
                if (this.tagForm == "2")
                {
                    this.labelFunctionTitle = "Cấp vật phẩm khuyến mãi cho khách hàng";
                }
                if (this.tagForm == "12")
                {
                    this.labelFunctionTitle = "Tạm ngưng, khôi phục, cấp vật tư bổ sung loại hình Cloud Camera";
                    this.dataSelected.dichvuvt_id = 12;
                }
                this.getThamSo();
            }
            catch (err)
            {
                
                this.$toast.error("Lỗi: "+ err.message);
            }
        },
        async LayTienTheoKhoanMuc() {
            try {
                await api.LayTienKhoanMuc(this.axios, {
                    "loaihd_id": LoaiHopDong.THAY_DAY_MAY,
                    "ngay_yc": this.txtNgayYC.toString(),
                    "dichvuvt_id": this.dataSelected.dichvuvt_id,
                    "loaitb_id": this.dataSelected.loaitb_id,
                    "kieuld_id": this.dataSelected.kieuld_id,
                    "phuongld_id": this.phuongld_id,
                    "tocdo_id": 0,
                    "muccuoc_id": 0,
                    "ltb_adsl_id": 1,
                    "ltb_adsl_moi_id": 0,
                    "ltb_adsl_cu_id": 0,
                    "tocdo_moi_id": 0,
                    "tocdo_cu_id": 0,
                    "kh_cd": 0,
                    "phold_id": this.phold_id,
                    "apld_id": this.apld_id,
                    "khuld_id": this.khuld_id,
                    "doituong_id": this.doituong_id,
                    "trangbi_id": this.trangbi_id
                }).then((res) => {
                    if (res.data.data.length > 0) {
                        this.HienThiTienLapDat(res.data.data);
                        this.LayTongTien_HDTB();
                    }
                });
            } catch (e) {
             console.log(e);
            }
        },
        HienThiTienLapDat(ds)
        {
            let kt_lm = true;
            if (ds.length > 0)
            {
                for (let i = 0; i < ds.length; i++)
                {
                    if (ds[i]["khoanmuctt_id"].toString().trim() == '30')
                    {
                        this.dtienld = this.formatPrice(ds[0].tien.toString());
                        this.dvatld = this.formatPrice(ds[0].vat.toString());
                        kt_lm = false;
                    }
                }
                if (kt_lm == true)
                {
                    this.dtienld = 0;
                    this.dvatld = 0;
                }
            }
            else
            {
                this.dtienld = 0;
                this.dvatld = 0;
                this.$toast.warning("Thông tin giá chưa được cập nhật");
            }
            for (let j = 0; j < this.options.ds_tien_khoanmuc.length; j++)
            {   
                if (this.options.ds_tien_khoanmuc[j].khoanmuctt_id.toString() == '30')
                {
                    this.options.ds_tien_khoanmuc[j].tien = this.dtienld;
                    this.options.ds_tien_khoanmuc[j].vat = this.dvatld;
                }
            }
        },
        LayTongTien_HDTB()
        {
            try
            {
                let dtongtien = 0;
                let dtongvat = 0;
                for (let i = 0; i < this.options.ds_tien_khoanmuc.length; i++)
                {
                    dtongtien += Number(this.options.ds_tien_khoanmuc[i].tien);
                    dtongvat += Number(this.options.ds_tien_khoanmuc[i].vat);
                }
                console.log('LayTongTien_HDTB ds_tien_khoanmuc', this.options.ds_tien_khoanmuc )
                console.log('LayTongTien_HDTB', dtongtien )
                this.txtTongTien = this.formatPrice(dtongtien);
                this.tongtien = parseInt(dtongtien);
                this.txtTongVAT = this.formatPrice(dtongvat);
                this.tongvat = parseInt(dtongvat);
                this.tong = parseInt(dtongtien) + parseInt(dtongvat);
            }
            catch (err)
            {
                this.$toast.error("Lỗi lấy tổng tiền: "+err.message);
            }
        },
        //Click nút nhập mới
        tsbtnNhapMoi_Click()
        {
            this.options.dsPhieu = [];
            this.dsCTM_TBI = []
            this.SetButton(1);
        },
        //Click nút ghi lại
        tsbtnGhiLai_Click()
        {
            this.CapNhat();
        },
        tsbtnHuyBo_Click()
        {
           this.options.dsPhieu = [];
            this.SetButton(0);
        },
        //Click nút thoát 
        tsbtnThoat_Click()
        {
            this.Close();
        },
        //Nhấn nút xóa
        tsbtnXoa_Click()
        {
            this.XoaHD();
        },
        //Xóa hợp đồng
        XoaHD()
        {
            try
            {
                this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa hợp đồng không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( v => {
                    if(v) {
                        api.Xoa_hdkh_v2(this.axios, {
                            "hdkh_id":this.hdkh_id,"loaihd_id":13
                        }).then((res) => {
                            if(res.data.error_code == 'BSS-00000000'){
                                this.$root.$toast.success("Xóa hợp đồng khách hàng thành công!");
                                this.SetButton(0); 
                                this.options.dsPhieu = []; 
                            }else{
                                this.$toast.error("Lỗi xóa hợp đồng khách hàng: "+ res.data.message_detail);
                            }
                        });
                    }
                });
            }
            catch (err)
            {
                this.$toast.error("Lỗi xóa hợp đồng khách hàng: "+ err.message);
            }
        },
        // Kiểm tra dữ liệu
        KiemTraDL_KhachHang()
        {
            if (this.txtTenKH == "")
            {
                this.$toast.warning("Hãy nhập tên khách hàng !");
                this.$refs.TenKH.focus();
                return false;
            }
            if (this.txtMaKH == "" && this.khachhang_id != 0)
            {
                this.$toast.warning("Kiểm tra lại mã khách hàng !");
                this.$refs.MaKH.focus();
                return false;
            }
            if (this.dtpNgayLapHD && !this.tsbtnNhapMoi)
            {
                if (this.txtNgayLapHD > this.tt_nd.ngay_cn)
                {
                    this.$toast.warning("Bạn không được chọn ngày lập HĐ lớn hơn ngày hiện tại");
                    return false;
                }
                if (this.txtNgayLapHD < this.tt_nd.ngay_cn -1 )
                {
                    this.$toast.warning("Bạn không được chọn ngày lập HĐ nhỏ hơn ngày hiện tại - 1");
                    return false;
                }
                if (this.txtNgayYC > this.tt_nd.ngay_cn)
                {
                    this.$toast.warning("Bạn không được chọn ngày yêu cầu lớn hơn ngày hiện tại");
                    return false;
                }
                if (this.txtNgayYC < this.tt_nd.ngay_cn-1)
                {
                    this.$toast.warning("Bạn không được chọn ngày yêu cầu nhỏ hơn ngày hiện tại - 1");
                    return false;
                }
            }
            return true;
        },
        HoanThienHS()
        {
            try
            {
                if (this.hdkh_id == 0 || this.hdtb_id == 0)
                {
                    this.$toast.warning("Chưa có thông tin phụ lục. Hãy kiểm tra lại!");
                    this.$refs.MaGD.focus();
                }else{
                    
                    let ht  = {};
                        ht.HDTB_ID = this.hdtb_id;
                        ht.THUEBAO_ID = this.thuebao_id;
                        ht.TSBTN_NHAPMOI = 0;
                        ht.KIEULD_ID_LUOI = this.dataSelected.kieuld_id;
                        ht.TAG_FORM = 1;
                        ht.DONVI_ID = this.tt_nd.donvi_id;
                        ht.NHANVIEN_ID = this.tt_nd.nhanvien_id;
                        ht.MA_ND = this.tt_nd.ma_nd;
                        ht.MAY_CN = this.tt_nd.ma_nd;
                        ht.IP_CN = this.tt_nd.ma_nd;
                        ht.Q_YS = 0;
                    this.$bvModal.msgBoxConfirm('Bạn thật sự muốn hoàn thiện không ?', {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                    }).then( async v => {
                        if(v) {
                                 let input= {
                                             "hdtb_id": this.hdtb_id,
                                             "thuebao_id": this.thuebao_id
                                            }
                                
                                let vtth = this.GetData(await api.lay_ds_vattu_th(this.axios,input));
                                if (vtth.length <= 0)
                                {
                                    this.$toast.warning("Bạn chưa thực hiện cấp/thu hồi vật tư");
                                    return;
                                }
                                api.Fn_hths_vattu_tb(this.axios, {
                                    "vds_para":JSON.stringify(ht)
                                }).then((res) => {
                                    if(res.data.data == 'ok'){
                                        this.$root.$toast.success('Hoàn thiện hồ sơ thành công');
                                        this.SetButton(0);
                                        this.options.dsPhieu = []
                                    }else{
                                        this.$toast.warning(res.data.data);
                                    }
                                });
                                this.SetButton(0);
                        }
                    });
                    
                }
            }
            catch (err)
            {
                 this.$toast.error("Lỗi hoàn thiện hợp đồng: "+ err.message);
            }
        },
        async dgrList_SelectionChanged(data)
        {
            let row = data.data;
            try
            {
                if (row)
                {
                    this.hdtb_id = row.hdtb_id;
                    this.NAP_DS_THAYTHE();
                    this.thuebao_id = row.thuebao_id;
                    this.txtMaTB = row.ma_tb.toString();
                    this.ma_tb = row.ma_tb.toString();
                    this.txtTenTB = row.ten_tb.toString();
                    this.txtDiaChiTB = this.ChuanHoaTen(row.diachi_tb.toString());
                    this.txtDiaChiLD = this.ChuanHoaTen(row.diachi_ld.toString());
                    this.dataSelected.loaitb_id = row.loaitb_id;
                    this.donvi_id = row.donvi_id;
                    this.doituong_id = row.doituong_id;
                    this.txtGhiChu = row.ghichu;
                    this.phieu_id = row.phieu_id;
                    this.dataSelected.thietbidc_id = 0;
                    this.dataSelected.kieuld_id_luoi = row.kieuld_id;
                    this.dataSelected.kieuld_id =  row.kieuld_id;
                    this.hienThiThongTinChiTiet()
                    let  ds_con = [];
                    if (this.dataSelected.dichvuvt_id == DichVuVienThong.IMS)
                    {
                        ds_con = this.GetData(await api.lay_ds_hopdong_thuebao_ims(this.axios,this.hdtb_id));
                        if (ds_con.length > 0)
                        {
                            if (ds_con[0].trangbi_id != ""){
                                this.trangbi_id = ds_con[0].trangbi_id;
                            }
                            else
                            {
                                this.trangbi_id = 3;
                            } 
                        }
                        else
                        {
                            this.trangbi_id = 3;
                        }
                    }
                    if (this.dataSelected.dichvuvt_id == DichVuVienThong.CO_DINH)
                    {
                        ds_con = this.GetData(await api.lay_ds_hopdong_thuebao_codinh(this.axios, this.hdtb_id));
                        if (ds_con.length > 0)
                        {
                            if (ds_con[0].trangbi_id != ""){
                                this.trangbi_id = ds_con[0].trangbi_id;
                            }
                            else
                            {
                                this.trangbi_id = 3;
                            } 
                        }
                        else
                        {
                            this.trangbi_id = 3;
                        }
                    }
                    if (this.dataSelected.dichvuvt_id == DichVuVienThong.ADSL)
                    {
                        ds_con = this.GetData(await api.lay_ds_hopdong_thuebao_adsl(this.axios, this.hdtb_id));
                        if (ds_con.length > 0)
                        {
                            if (ds_con[0].trangbi_id != ""){
                                this.trangbi_id = ds_con[0].trangbi_id;
                            }
                            else
                            {
                                this.trangbi_id = 3;
                            } 
                            if (ds_con[0].thietbidc_id != "")
                            {
                                this.thietbi_dc_id = ds_con[0].thietbidc_id;
                            }
                        }
                        else
                        {
                            this.trangbi_id = 3;
                        }
                    }
                    if (this.dataSelected.dichvuvt_id == DichVuVienThong.MEGAWAN)
                    {
                        ds_con = this.GetData(await api.lay_ds_hopdong_thuebao_mgwan(this.axios,this.hdtb_id));
                        if (ds_con.length > 0)
                        {
                            if (ds_con[0].trangbi_id != ""){
                                this.trangbi_id = ds_con[0].trangbi_id;
                            }
                            else
                            {
                                this.trangbi_id = 3;
                            } 
                        }
                        else
                        {
                            this.trangbi_id = 3;
                        }
                    }
                    if (this.dataSelected.dichvuvt_id == DichVuVienThong.TSL)
                    {
                        ds_con = this.GetData(await api.lay_ds_hdtb_tsl_theo_hdtb_id(this.axios, this.hdtb_id));
                        if (ds_con.length > 0)
                        {
                            if (ds_con[0].trangbi_id != ""){
                                this.trangbi_id = ds_con[0].trangbi_id;
                            }
                            else
                            {
                                this.trangbi_id = 3;
                            } 
                        }
                        else
                        {
                            this.trangbi_id = 3;
                        }
                    }
                    this.dataSelected.thietbidc_id = this.thietbi_dc_id;
                    let dataDC = this.GetData(await api.Lay_ds_diachi(this.axios,{
                                        "in_thuebao_id":this.thuebao_id
                                    }
                                ));
                    this.HienThiTTDiaChi(dataDC, 3);
                    this.options.ds_tien_khoanmuc = this.GetData(await api.Lay_tienhd_km_theo_hdtb(this.axios,{
                                       "hdtb_id":this.hdtb_id
                                    }
                                ));
                    for (let i = 0; i < this.options.ds_tien_khoanmuc.length; i++)
                    {
                        if (this.options.ds_tien_khoanmuc[i].khoanmuctt_id == KHOANMUC_TT.KMTT_THAYTHE_THUHOI)
                        {
                            this.dtienld = this.options.ds_tien_khoanmuc[i].tien;
                            this.dvatld = this.options.ds_tien_khoanmuc[i].vat;
                        }
                    }
                    await this.LoadDSTBIHDTB();
                    await this.HT_ThietBi();
                    await this.LayTongTien_HDTB();
                    try
                    {
                            this.stbump = false;
                            this.password = "";
                            this.serial_ump = "";
                            this.txtSerilUMP = "";
                            this.IsReadonly = true;
                            if (this.dataSelected.loaitb_id == LoaiHinhTB.INTERNET_MYTV)
                            {
                                let kq = this.GetData(await api.Lay_tt_thuebao_mytv(this.axios,{
                                        "vthuebao_id": this.thuebao_id,
                                        "vhdtb_id": this.hdtb_id
                                    }
                                ));
                                if (kq != null)
                                {
                                
                                    if (kq[0].thietbidc_id.toString() == "157")
                                    {
                                        this.password = kq[0].password.toString();
                                        this.txtSerilUMP = kq[1].toString();
                                        this.IsReadonly = false;
                                        this.stbump = true;
                                        
                                    }
                                }
                            }
                    }
                    catch (err)
                    {
                        this.$toast.error("Có lỗi khi lấy thông tin STB UMP bạn hãy lấy lại thông tin nếu muốn nhập serial UMP: "+ err.message);
                        this.IsReadonly = true;
                    }
                    this.SetButton(3);
                }
            }
            catch (err)
            {
                 this.$toast.error("Lỗi: "+err.message);
            }
        },
        //Kiểm tra trước khi view popup Thay thế hợp đồng, báo hỏng
        Kiemtrathaythe_Popup(){
            console.log(this.thuebao_id)
            console.log(this.tsbtnNhapMoi)
            if (this.thuebao_id == 0 || !this.tsbtnNhapMoi )
            {
                this.$toast.warning("Bạn chưa có thông tin thuê bao không thể thao tác tiếp");                  
            }
            else
            {
                this.showTTHD = "dropdown";
                var container = $("#move");
                container.show();
                if(this.checkHD == 'HD'){
                    this.NAP_DS_HD_BH();
                }else{
                    this.checkTB = 'TB';
                    this.checkHD = '';
                    this.checkBH = '';
                    this.grvTB = true;
                    this.grvBH = false;
                    this.grvHD = false;
                    this.frmLoad();
                }
                    
                
                
            }
           
        },
        CloseDropDown(){
            this.checkHD = 'HD';
            this.accept = false;
            this.grvTB = false;
            this.grvHD = true;
            this.grvBH = false;
            this.options.dsHD_BH = []
            this.options.dsTB = []
            var container = $("#move");
            container.hide();
            
        },
        //Kiểm tra dữ liệu thuê bao
        async KiemTraDL_ThueBao()
        {
            if (this.txtMaHD == "" && this.khachhang_id != 0)
            {
                this.$toast.warning("Hãy nhập mã hợp đồng !");
                this.$refs.MaHD.focus();
                return false;
            }
            if (this.txtTenTB == "")
            {
                this.$toast.warning("Hãy nhập tên thuê bao !");
                this.$refs.TenTB.focus();
                return false;
            }
            /*var kt_tb =  this.GetData(await api.kiemtra_db_khi_themmoi(this.axios, 
                {
                    "ma_tb":this.txtMaTB,
                    "hdkh_id":this.hdkh_id,
                    "dichvuvt_id":this.dataSelected.dichvuvt_id,
                    "loaitb_id":this.dataSelected.loaitb_id
                }
            ))
            if (kt_tb == '0')
            {
                this.$toast.warning("Thuê bao không tồn tại trong danh bạ (hoặc chưa Enter tại ô Mã TB). Không thể cập nhật!");
                return false;
            }*/
            // Nếu là dịch vụ này thì kiểm tra thay đổi tbi DC
            if (this.dataSelected.dichvuvt_id == DichVuVienThong.ADSL)
            {
                if (!this.tsbtnNhapMoi)
                {
                    // phai đúng kiểu LĐ mới được thay đổi loại TBI
                    if ( this.dataSelected.kieuld_id == KieuLapDat.TD_LOAITBI_DC)
                    {
                         console.log(555555555555)
                        console.log(this.dataSelected.thietbidc_id)
                        if (this.dataSelected.thietbidc_id == null ||
                            this.dataSelected.thietbidc_id.toString() == "" || this.dataSelected.thietbidc_id.toString() == "0")
                        {
                            this.$toast.warning("Bạn phải chọn loại thiết bị đầu cuối");
                            return false;
                        }
                        this.tdoi_tc_dc = true;
                    }
                    if ( this.dataSelected.kieuld_id == KieuLapDat.TD_THAYTHE_STB)
                    {
                        this.tdoi_tt_stb = true;
                    }
                }
                else
                {
                    if (this.dataSelected.kieuld_id_luoi.toString() == KieuLapDat.TD_LOAITBI_DC.toString())
                    {
                        if (this.dataSelected.thietbidc_id == null || 
                           this.dataSelected.thietbidc_id.toString() == "" || this.dataSelected.thietbidc_id.toString() == "0")
                        {
                            this.$toast.warning("Bạn phải chọn loại thiết bị đầu cuối");
                            return false;
                        }
                        this.tdoi_tc_dc = true;
                    }
                    if (this.dataSelected.kieuld_id_luoi == KieuLapDat.TD_THAYTHE_STB)
                    {
                        this.tdoi_tt_stb = true;
                    }
                }
                if (this.tdoi_tt_stb)
                {
                    let input = {
                        "vthuebao_id": this.thuebao_id,
                        "vhdtb_id": 0,
                        "vkieu": 1
                    };
                    var kt =  this.GetData(await api.kiemtrathaythestb(this.axios, input))
                    if (kt == null)
                    {
                        this.$toast.warning("Không có thông tin trả từ kiểm tra thay thế STB");
                        return false;
                    }
                    if (kt.toString() != "1")
                    {
                        this.$toast.warning("[KTSTB]: "+kt.toString());
                        return false;
                    }
                }
            }
            if (this.nhap_gc_hd)
            {
                if (this.txtGhiChu == "")
                {
                    this.$toast.warning("Bạn phải nhập thông tin ghi chú của hợp đồng");
                    return false;
                }
            }
            return true;
        },
    
        //Cập nhật dữ liệu
        async CapNhat()
        {
            try
            {
                if (!this.KiemTraDL_KhachHang()) return;
                if (!this.KiemTraDL_ThueBao()) return;
                if (this.tsbtnNhapMoi == false)
                {  
                        if (!this.boqua_kt_matb_lap_hdkhac)
                        {
                            let input_kt = {
                                "loaihd_id": LoaiHopDong.VATTU_THUEBAO,
                                "ma_gd": '0',
                                "dichvuvt_id": this.dataSelected.dichvuvt_id,
                                "ma_tb": this.txtMaTB
                            }
                            let kt = this.GetData(await api.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.axios, input_kt));
                            if (kt.length > 0) {
                                let tenLoaiHd = kt[0].ten_loaihd
                                let maGd = kt[0].ma_gd
                                let nguoiCn = kt[0].nguoi_cn
                                let tenDV = kt[0].ten_dv
                                let ngayYc = kt[0].ngay_yc
                                this.$root.$toast.warning(`Số máy/Acc: ${this.txtMaTB} đang được lập bởi hợp đồng (phụ lục) ${tenLoaiHd} có mã giao dịch ${maGd} do User ${nguoiCn} thuộc đơn vị ${tenDV} thực hiện vào ngày ${ngayYc}`)
                                return;
                            }
                        }
                        let input3 = {
                            "kieuLdId": this.dataSelected.kieuld_id,
                            "loaiHdId": LoaiHopDong.THAY_DAY_MAY,
                            "loaiTbId": this.dataSelected.loaitb_id,
                            "mucCuocId":  this.muccuoctn_id,
                            "tocDoId": this.tocdotn_id}
                        let dset = this.GetData(await api.LayHuongGiao_TiepNhan(this.axios, input3));
                        let huonggiao_tn = 0;
                        let quytrinh_id = 0;
                        if(dset !== null){
                             this.huonggiao_tn = dset.huonggiao_id;
                            this.quytrinh_id = dset.quytrinh_id;
                        }else{
                            this.$root.$toast.warning("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
                            return;
                        }
                        await this.TaoDuLieu(true);
                        await api.Fn_them_hopdong_vattu_tb_v2(this.axios, {
                            "vphanvung_id":this.$root.token.getPhanVungID(),
                            "vdshdkh":this.options.dsHDKH,
                            "vdshdtb":this.options.dsHDTB, 
                            "vdshdtbdd":this.options.dsHDTB_DD,
                            "vdshdtb_adsl":this.options.dsHDTB_ADSL, 
                            "vdshdtb_cd":this.options.dsHDTB_CD,
                            "vdshdtb_gp":this.options.dsHDTB_GP,
                            "vdshdtb_mgw":this.options.dsHDTB_MGWAN,
                            "vdshdtb_tsl":this.options.dsHDTB_TSL,
                            "vdshdtb_ims":this.options.dsHDTB_IMS,
                            "vhuonggiao_id":this.huonggiao_tn,
                            "vdshdtbdv":this.options.dsHDTBDV,
                            "vdsctthd":this.options.dsCTTHD,
                            "vdsctm_tbi": this.changeKeysToUpper(this.dsCTM_TBI),
                            "vds_thuebao":this.data_thuebao
                        }).then((res) => {
                            if(res && res.data && res.data.error_code && res.data.error_code == 'BSS-00000000' && res.data.data) {
                                this.$root.$toast.success("Thêm mới dữ liệu thành công!");
                                this.SetButton(3);
                                this.HienThiDanhSacHDTB();
                            }else{
                                this.$root.$toast.warning(res.data.message)
                            }
                        });   
                }
                else
                {
                    await this.TaoDuLieu(false);
                    let input = {};
                    await api.Fn_sua_hopdong_vattu_tb_v2(this.axios, {
                        "vdshdkh":this.options.dsHDKH,
                        "vdshdtb":this.options.dsHDTB,
                        "vdshdtbdd":this.options.dsHDTB_DD,
                        "vdshdtb_adsl":this.options.dsHDTB_ADSL,
                        "vdshdtb_cd":this.options.dsHDTB_CD,
                        "vdshdtb_gp":this.options.dsHDTB_GP,
                        "vdshdtb_mgw":this.options.dsHDTB_MGWAN,
                        "vdshdtb_tsl":this.options.dsHDTB_TSL,
                        "vdshdtb_ims":this.options.dsHDTB_IMS,
                        "tdoi_tc_dc":this.tdoi_tc_dc,
                        "vdshdtbdv":this.options.dsHDTBDV,
                        "vdsctthd":this.options.dsCTTHD,
                        "vhdtb_id": this.hdtb_id,
                        "vdsctm_tbi": this.changeKeysToUpper(this.dsCTM_TBI),
                        "vds_thuebao":this.data_thuebao
                    }).then((res) => {
                         if(res && res.data && res.data.error_code && res.data.error_code == 'BSS-00000000' && res.data.data) {
                            this.$root.$toast.success("Cập nhật dữ liệu thành công !");
                            this.SetButton(3);
                            this.HienThiDanhSacHDTB();
                        }else{
                            this.$root.$toast.warning(res.data.message)
                        }
                    });
                }
            }
            catch (err)
            {
                this.$toast.error("Lỗi cập nhật hợp đồng: "+err.message);
            }
        },
        
        changeCKHD() {
              this.checkHD = 'HD';
              this.checkBH = '';
              this.checkTB = '';
              this.grvHD = true;
              this.accept = false;
              this.grvBH = false;
              this.grvTB = false;
              this.NAP_DS_HD_BH();
        },
         changeCKBH() {
              this.checkHD = '';
              this.checkBH = 'BH';
              this.grvBH = true;
              this.accept = false;
              this.grvHD = false;
              this.grvTB = false;
              this.NAP_DS_HD_BH();
        },
         changeCKTB() {
              this.checkHD = '';
              this.checkBH = 'TB';
              this.accept = true;
              this.grvBH = false;
              this.grvHD = false;
              this.grvTB = true;
              this.frmLoad();
        },
        async gridHD_FocusedRowChanged(data) {
            try {
                if (data) {
                }
            } catch (err) {
                console.log(err);
            }
        },
         async gridBH_FocusedRowChanged(data) {
            try {
                if (data) {
                    let input = {};
                    
                }
            } catch (err) {
                console.log(err);
            }
        },
        async NAP_DS_THAYTHE()
        {
            try
            {
                if (this.hdtb_id == 0)
                {
                    return;
                }
                this.txtMAGD_TT = "";
                await api.Lay_ds_thaythe_vattu_tb(this.axios, {"vhdtb_id": this.hdtb_id}).then((res) => { 
 
                   if(res.data.data){
                        this.txtMAGD_TT = res.data.data[0].ma_chung;
                    }
                    
                });
               
            }
            catch (err)
            {
                
                this.$toast.error("Lỗi: "+err.message);
            }
        },
        gridBH_GridCellCreated:  function(args){
             let field = args.column.field
            if(field==="chon"){
                args.cell.innerHTML = '<button class="btn btn-primary pad4 w-auto lh14 h-auto" > <span class="-ap icon-plus3"></span> </button>';
            }
        },
        GoHD_Click() {
                    if (this.thuebao_id == 0 || this.hdtb_id == 0)
                    {
                        this.$toast.warning("Bạn chưa có thông tin thuê bao không thể thao tác tiếp");
                        return;
                    }
                    let input = {};
                    let tb_id = 0;
                    let hd_id = 0;
                    let bh_id = 0;
                    this.$bvModal.msgBoxConfirm("Bạn có muốn gỡ hợp đồng/báo hỏng thay thế khỏi hợp đồng không?", {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                            input = {
                                 "hdtb_id": this.hdtb_id
                            }
                            
                            await api.sp_delete_hdtb_sub_by_hdtb_id(this.axios, input).then((res) => {
                                this.$root.$toast.success("Gỡ hợp đồng thành công !");
                            });
                           
                            this.NAP_DS_THAYTHE()
                        }
                    });
            
        },
        gridDsHD_BHCommand(args,data) {
             if (data) {
                    let input = {};
                    let tb_id = 0;
                    let hd_id = 0;
                    let bh_id = 0;
                    if (!this.gan_thuebaoid_khac)
                    {
                        tb_id = data.thuebao_id;
                        if (tb_id != this.thuebao_id)
                        {
                            this.$toast.warning("Bạn đang chọn không đúng thuê bao (" + tb_id + "|" + this.thuebao_id + ")");
                            return;
                        }
                    }
                    this.$bvModal.msgBoxConfirm("Bạn có muốn thay thế vật tư cho hợp đồng " + data.ma_chung + " này không?", {
                            title: '',
                            size: 'sm',
                            okTitle: 'Đồng ý',
                            cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                                if(this.checkHD=='HD'){
                                    tb_id = data.thuebao_id;
                                    hd_id = data.hdtb_id;
                                    input = {
                                        "baohong_kn_id": 0,
                                        "hdtb_id": this.hdtb_id,
                                        "hdtb_kn_id":hd_id,
                                        "kieu": 1
                                    }
                                }else{
                                    tb_id = data.thuebao_id;
                                    bh_id = data.baohong_id;
                                    
                                    input = {
                                        "baohong_kn_id": bh_id,
                                        "hdtb_id": this.hdtb_id,
                                        "hdtb_kn_id":0,
                                        "kieu": 2
                                    }
                                }
                                
                                await api.Capnhat_hd_bh_vattu_tb(this.axios, input).then((res) => {
                                    this.$root.$toast.success("Cập nhật thành công !");
                                });
                                this.NAP_DS_THAYTHE()
                            }
                    });
             }
           
        },
        NAP_DS_HD_BH()
        {
            try
            {
                this.options.dsHD_BH = [];
                if (this.thuebao_id == 0)
                {
                    this.$toast.warning("Bạn chưa có thông tin thuê bao không thể tìm kiếm thông tin");
                }else{
                    let input = {}
                    if (this.checkHD == 'HD')
                    {
                         input = {
                            "vkieu": 0,
                            "vthuebao_id": this.thuebao_id,
                            "vhdtb_id": this.hdtb_id,
                            "vbaohong_id": 0
                        }
                    }
                    if (this.checkBH=='BH')
                    {
                         input = {
                            "vkieu": 1,
                            "vthuebao_id": this.thuebao_id,
                            "vhdtb_id": this.hdtb_id,
                            "vbaohong_id": 0
                        }
                    }
                   
                    api.Lay_ds_hd_bh(this.axios,input)
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000" &&
                            res.data.data !== undefined &&
                            res.data.data.length > 0
                        ) {
                            this.options.dsHD_BH = res.data.data;
                        }else{
                            this.$toast.error("Có lỗi nạp danh sách: "+res.data.message)
                        }
                    });
                }
            }
            catch (err)
            {
                this.$toast.error("Có lỗi nạp danh sách: "+err.message)
            }
        },
        //Hàm call api
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200'|| response.data.error === '0') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
        //Set button
        SetButton(kieu)
        {
            this.tsbtnNhapMoi = false;
            this.tsbtnGhiLai = false;
            this.tsbtnXoa = false;
            this.tsbtnHuyBo = false;
            this.tsbtnHoanThienHS = false;
            if (kieu == -1)//Bat dau
            {
                if (this.matinh_hientai == "TGG" && parseInt(this.tt_nd.ngay_cn.substring(0, 2)) == 1)
                {
                    this.dtpNgayYeuCau = true;
                    this.dtpNgayLapHD = true;
                }
                else
                {
                    this.dtpNgayYeuCau = false;
                    this.dtpNgayLapHD = false;
                }
                this.$refs.MaTB.focus();
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
            }
            if (kieu == 0)//Bat dau
            {
                this.$refs.MaTB.focus();
                this.tsbtnNhapMoi = true;
                this.Clear();
            }
            if (kieu == 1)//Them moi
            {
                this.$refs.MaTB.focus();
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
                this.Clear();
            }
            if (kieu == 2)//Huy
            {
                this.$refs.MaGD.focus();
                this.tsbtnNhapMoi = true;
                this.tsbtnXoa = true;
                this.Clear();
            }
            if (kieu == 3)//Edit
            {
                this.$refs.MaGD.focus();
                this.tsbtnNhapMoi = true;
                this.tsbtnXoa = true;
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
                this.tsbtnGiaoPhieu = true;
                this.tsbtnHoanThienHS = true;
                this.dtpNgayYeuCau = false;
                this.dtpNgayLapHD = false;
            }
        },
        //Clear
        Clear()
        {
            try
            {
                if (this.matinh_hientai == "TGG" && parseInt(this.tt_nd.ngay_cn.substring(0, 2)) == 1) 
                {
                    this.dtpNgayYeuCau = true;
                    this.dtpNgayLapHD = true;
                }
                else
                {
                    this.dtpNgayYeuCau = false;
                    this.dtpNgayLapHD = false;
                }
                this.hdkh_id = 0;
                this.txtMaGD = "";
                this.txtMaHD = "";
                this.txtMaKH = "";
                this.khachhang_id = 0;
                this.txtTenKH = "";
                this.txtDiaChiKH = "";
                this.txtNgayYC = this.tt_nd.ngay_cn;
                this.hdtb_id = 0;
                this.thuebao_id = 0;
                this.txtTenTB = "";
                this.txtDiaChiTB = "";
                this.txtDiaChiLD = "";
                this.dataSelected.kieuld_id = this.options.lstKieuYC[0].kieuld_id;
                this.$refs.MaTB.focus();
                this.txtGhiChu = "";
                this.txtSoAo = "";
                this.trangbi_id = 3;
                this.thietbi_dc_id = 0;
                this.phuongld_id = 0;
                this.phold_id = 0;
                this.apld_id = 0;
                this.khuld_id = 0;
                this.doituong_id = 1;
                this.txtSerilUMP = "";
                this.dsCTM_TBI = [];
                this.grvTB = false;
                this.txtMAGD_TT = "";
                this.data_thuebao = [];
                this.TBI.txtareaTBI = [];
                this.LayTienTheoKhoanMuc();
                console.log(1)
            }
            catch (err)
            {
             
                this.$toast.error("Lỗi: "+ err.message);
            }
        },
        cbDangKyTemplate(parent) {
            return function () {
                return { 
                    template : {
                        template: `<div class="check-action"><input type="checkbox" class="check" v-model="value"
                        @change="rowCheckedChanged"><span class="name"></span></div>`,                        
                        data () { 
                            return { 
                                data: {},
                                parent: parent,
                                value: null
                            }
                        },
                        methods: {
                            rowCheckedChanged() {  
                                let rowData = this.parent.options.dsTB.find(item => item.thuebao_id == this.data.thuebao_id)
                      
                                rowData.chon = this.value ? '1' : '0'   
                                EventBus.$emit('rowCheckedChanged', this.data);
                            }
                        },
                        mounted() {
                            this.value = this.data.chon == '1' ? true : false
                        }
                    }
                }
            }
        },
        cbGoiNangCapTemplate(parent) {
            return function () {
                return { 
                    template : {
                        template: `<div class="select-custom">
                            <SelectExt
                                v-model="value"
                                :settings="{dropdownParent: parent.$refs['ref-modal-content']}"
                                placeholder="[Chưa chọn giá trị]"
                                :dataSource="dataSource"
                                dataValueField="goi_id"
                                dataTextField="tengoi2" />
                        </div>`,                        
                        data () { 
                            return { 
                                data: {},
                                value: null,
                                parent: parent,
                                dataSource: []
                            }
                        },
                        methods: {
                            chonGoiNangCap() {    
                                // this.data.goidk_id = this.value             
                                EventBus.$emit('chonGoiNangCap', this.data);
                            }
                        },
                        watch: {
                            value(newVal) {
                                let rowData = this.parent.options.dsTB.find(item => item.thuebao_id == this.data.thuebao_id)
                                rowData.goidk_id = !newVal ? null : parseInt(newVal)
                                rowData.goi_dk = !newVal ? null : this.data.ds_goi_cuoc.find(item => item.goi_id == rowData.goidk_id).tengoi
                            }
                        },
                        mounted() {
                            this.dataSource = this.data.ds_goi_cuoc.map(item => { return {...item} })
                            this.value = this.data.goidk_id
                        }
                    }
                }
            }
        },
        txtDoanhThuDkTemplate(parent) {
            return function () {
                return { 
                    template : {
                        template: `<div class="value">
                            <input v-model="value" type="text" class="form-control text-right" @change="doanhThuDkChanged">
                        </div>`,                        
                        data () { 
                            return { 
                                data: {},
                                parent: parent,
                                value: null
                            }
                        },
                        methods: {
                            doanhThuDkChanged() {    
                                //this.data.doanhthu_dk = this.value   
                                let rowData = this.parent.options.dsTB.find(item => item.thuebao_id == this.data.thuebao_id)
                                rowData.doanhthu_dk = this.value        
                                EventBus.$emit('doanhThuDkChanged', this.data);
                            }
                        },
                        watch: {
                            value(newVal) {
                                this.value = !newVal ? 0 : parseInt(newVal)
                            }
                        },
                        mounted() {
                            this.value = this.data.doanhthu_dk
                        }
                    }
                }
            }
        },
        async hienThiThongTinChiTiet(){
            this.loading(true)
            try{
                await api.lay_ds_gui_chitiet(this.axios, {
                    "p_hdkh_id": this.hdkh_id,
                    "p_hdtb_id": this.hdtb_id,
                    "p_loai": 1
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        this.danhSach = response.data.data
                        this.data_thuebao = response.data.data;
                        this.grvTB = true;
                        this.grvHD = false;
                        this.grvBH = false;
                        this.checkTB = 'TB';
                        this.checkHD = '';
                        this.checkBH = '';
                        this.accept = true
                    }
                    else{
                        this.danhSach = []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async frmLoad(){
            this.loading(true)
            try{
                await api.lay_ds_dbtb_theo_khid_v3(this.axios, {
                    "in_khachhang_id": this.khachhang_id
                }).then(response => {
                    this.loading(false)
                    
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data != null) {
                        this.dsThueBaoTmp = response.data.data
                        if( this.dsThueBaoTmp.length > 0){
                             this.dsThueBaoTmp.forEach(ele => {
                                ele.ngay_sd = ele.ngay_sd != null ? moment(ele.ngay_sd, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY') : null
                            })
                            if(this.danhSach && this.danhSach.length>0) {
                                var ds = this.danhSach
                                this.dsThueBaoTmp.forEach(function (item) {
                                    let rec = ds.filter(x => x.THUEBAO_ID === item.thuebao_id)
                                    console.log(item)
                                    if (rec.length > 0) {
                                        item.chon = '1'
                                        item.goidk_id = rec[0].GOIDK_ID
                                        item.doanhthu_dk = rec[0].DOANHTHU_DK
                                    } else {
                                        item.chon = '0'
                                    }
                                });
                            } 
                        }
                       
                    }
                    else{
                        this.dsThueBaoTmp = []
                    }
                })
                for(var i = 0; i < this.dsThueBaoTmp.length; i ++){
                    const ele = this.dsThueBaoTmp[i]
                    const ds = await this.layDsGoiCuoc(ele.loaitb_id, ele.thuebao_id)
                    this.dsThueBaoTmp[i].ds_goi_cuoc = ds?ds.map(item => ({
                        ...item,
                        tengoi2: item.goi_id + ' - ' + item.tengoi
                    })):null
                }
                this.options.dsTB = this.dsThueBaoTmp
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        async layDsGoiCuoc(loaiTbId, thueBaoId){
            this.loading(true)
            try{
                return await api.lay_goi_cuoc_ban_tbi(this.axios, {
                    "vloaihinhtb_id": loaiTbId,
                    "vthuebao_id": thueBaoId
                }).then(response => {
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        return response.data.data
                    }
                    else{
                        return []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        chapNhan(){
            this.data_thuebao = []
            this.danhSach = this.options.dsTB.filter(x=> x.chon=='1')
            for(var i = 0; i < this.danhSach.length; i ++){
                if (this.khachhang_id > 0)
                {
                    if (this.danhSach[i]["goidk_id"] == null || this.danhSach[i]["goidk_id"].toString().trim() == "")
                    {
                        this.$toast.warning("Bạn cần chọn gói nâng cấp cho mã thuê bao " + this.danhSach[i]["ma_tb"].toString() + "!");
                        return;
                    }
                    if (this.danhSach[i]["doanhthu_dk"] == null || this.danhSach[i]["doanhthu_dk"].toString().trim() == "")
                    {
                        this.$toast.warning("Bạn cần nhập doanh thu dự kiến cho thuê bao" + this.danhSach[i]["ma_tb"].toString() + "!");
                        return;
                    }
                }
                let row_data = {}
                row_data.HDKH_ID = this.hdkh_id
                row_data.DOANHTHU = this.danhSach[i]["doanhthu"]
                row_data.THUEBAO_ID = this.danhSach[i]["thuebao_id"]
                row_data.MA_TB = this.danhSach[i]["ma_tb"]
                row_data.DOANHTHU_DK = this.danhSach[i]["doanhthu_dk"]
                row_data.GOIDK_ID = this.danhSach[i]["goidk_id"]
                row_data.TEN_GOI = this.danhSach[i]["goi_dk"]
                row_data.GOIHT_ID = this.danhSach[i]["goiht_id"]
                row_data.GOI_DK = this.danhSach[i]["goi_dk"]
                row_data.TENGOI_HT = this.danhSach[i]["tengoi_ht"]
                this.data_thuebao.push(row_data)
            }
            var container = $("#move");
            container.hide();
        },
        thoat(){
             this.CloseDropDown();
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
        async onChangeDichvu(row) {
              this.dataSelected.dichvuvt_id = row;
              await this.getLoaiHinhTB(row)
        },
        async onChangeLoaihinh(row) {
            await this.getDmKieuYC(row);
            await this.getDsThietbiDC(row);
        },
        async onChangeKieuLD(row) {
            await this.LayTienTheoKhoanMuc();
            if (this.dataSelected.kieuld_id == 11002 || this.dataSelected.kieuld_id == 11003)
                {
                    this.tsbtnVatTu = false;
                    this.tsbtnHoanThienHS = false;
                }
        },
        async onChangeLoaiTB(row) {
            this.dataSelected.thietbidc_id = row
        },
        async getKey(keyname){
            try{
                let response = await api.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async lay_magd(loaihd_id){
            try{
                let response = await api.lay_magd(this.axios, loaihd_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                return ''
            }
        },
        async lay_magd_v2(donvi_id,loaihd_id){
            try{
                let response = await api.lay_magd_v2(this.axios,{
                    "vdonvi_id": donvi_id,
                    "vloaihd_id": loaihd_id
                })
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                return ''
            }
        },
       
    },
    created: async function() {
        this.Tag = this.$route.query.Tag || "1" 
        this.tagFrm = this.Tag.toString()
    },
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