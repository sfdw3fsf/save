<template src="./index.html"></template>

<script>
import define from "./define/index.js";
import api from "./api/index.js";
import * as moment from 'moment';
import Vue from "vue";
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts';
import { ButtonPlugin } from '@syncfusion/ej2-vue-buttons';
import { TabPlugin } from '@syncfusion/ej2-vue-navigations';
import { ListBoxPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { required } from 'vuelidate/lib/validators'

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
// import TTKHLeft from './components/TTKHLeft.vue';
// import TTKHRight from './components/TTKHRight.vue';
// import MuaTBI from './components/MuaTBI.vue';
// import ChonTBI from './components/ChonTBI.vue';
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import ModalNormalizeCAinformation from "@/modules/contract/setup/NormalizeCAinformation/ModalNormalizeCAinformation.vue";
import ModalBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices.vue";
import ModalTienKM from "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue";
import PayMent from "@/modules/contract/setup/Payment/Payment.vue";
import Map from "./popup/map.vue";
import ModalChooseDevices from './popup/popupChooseDevices.vue';
import ModalChonToaDo from "@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo";
import ModalDinhKem from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonGrid.vue";

export default {
    provide: {
        
    },
    validations: {
        TTTT: {
            dich_vu: {
                required
            }
        }
    },
    components: {
        api,
        SearchContractModal,
        ModalNormalizeCAinformation,
        ModalBuyDevices,
        ModalTienKM,
        PayMent,
        Map,
        ModalChooseDevices,
        ModalChonToaDo,
        required,
        ModalDinhKem
    },
    props: ['Tag'],
    watch: {
        "TTTT.kieu_yc": function(value){
            this.cboLoaiLyDo_EditValueChanged(value);
        }
    },
    created() {
        // this.initialLoad = false;
        if (this.$route.query.tag != null && this.$route.query.tag.length > 0) {
            this.Tag = this.$route.query.tag
        }
        // debugger
        console.log('xxx ', this.$route.query.tag);
        // Gan vao theo tham so cua nghiep vu cua form
        if (this.Tag && this.Tag.length > 0) {
            let arr = this.Tag.split('-')
            // debugger
            if (arr.length >= 1) this.TTTT.kieu_yc = Number(arr[0]);
            // if (arr.length >= 2) this.quytrinh_id = Number(arr[1]);
            // if (arr.length >= 3) this.initDVVTArr = arr[2].split(',')
        }
        console.log('tag : ', this.tag);
        this.getDanhMucChung();
        this.initData();
        // this.LayDsBNM();
    },
    data() {
        return {
            ...define,
            checkboxTemplate: function () {
                return {
                    template: t
                }
            },
            filterBarMode: { filterBarMode: "Immediate" },
            currentRows: [],
            selectionOptions: { checkboxMode: 'ResetOnRowClick' },
            formatOptions: { type: 'date', format: 'dd/MM/yyyy' },
            contentTTKHLeft: function () {
                return {
                    template: TTKHLeft
                }
            },
            contentTTKHRight: function () {
                return {
                    template: TTKHRight
                }
            },
            headerText0: { text: 'Mua thiết bị' },
            headerText1: { text: 'Chọn thiết bị' },

            map: {
                lat: null,
                lng: null,
            },
            map_info: {
                dia_chi: "",
                title: ""
            },
            DS_ThueBao: [],
            a: { 
                Tag: 0,
                EditValue: 0
            },
            dtControl: [],
        }
    },
    methods: {
        cboLoaiLyDo_EditValueChanged(value){
            try {
                // alert(value)
                if(this.ds_dbkh && this.ds_dbkh.length){
                    this.HienThiQuyTrinhDonVi(this.ds_dbkh[0]['donvi_id']);
                }else{
                    this.HienThiQuyTrinhDonVi(0);
                }
            } catch (error) {
                console.log(error);
            }
        },
        XacNhanToaDo(data) {
            console.log(data);
            this.axios.post(
                "/web-thicong/thicong-internet/fn_capnhat_toado_thuebao_v2",
                {
                kieu: 1,
                db_id: this.form.hdtb_id,
                lat: data.lat,
                lng: data.lng,
                update_tb_cung_cap: 1,
                force_update: 1,
                daucuoi_id: 2,
                }
            );
        },
        async btnChonbando() {
            try {
                let long = await this.axios.post(
                "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb",
                {
                    // id_neo: "kinhdo_ld",
                    // in_table: "css.diachi_hdtb",
                    // in_dk: "where hdtb_id=" + this.hdtb_id,
                    type: 1,
                    param: this.hdtb_id
                }
                );

                let lat = await this.axios.post(
                "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb",
                {
                    // id_neo: "vido_ld",
                    // in_table: "css.diachi_hdtb",
                    // in_dk: "where hdtb_id=" + this.hdtb_id,
                    type: 2,
                    param: this.hdtb_id
                }
                );
                if (long.data.data != -1 && lat.data.data != -1)
                this.map = {
                    lng: Number(long.data.data),
                    lat: Number(lat.data.data),
                };

                this.$bvModal.show("ModalChonToaDo");
            } catch (err) {
                this.$toast.error("Có lỗi xảy ra");
            }
        },
        onlyNumber(e) {
            let keyCode = e.keyCode ? e.keyCode : e.which;
            if ((keyCode < 48 || keyCode > 57) && keyCode !== 46) { // 46 is dot
                e.preventDefault();
            }
        },
        OpenpopupTienKM() {
            // alert('');
            // let ds = [
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            //     {
            //         khoanmuctt: "test",
            //         tongtien: 0,
            //         tien: 0,
            //         vat: 0
            //     },
            // ]
            this.dataTienKM.ds = this.ds_tien_khoanmuc;
            this.dataTienKM.allowExit = true;
            console.log(this.ds_tien_khoanmuc);
            if(this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length){
                this.ds_tien_khoanmuc.forEach(item => {
                    item['tongtien'] = item['tien'] + item['vat']
                })
            }
            // this.dataTienKM.cols = []
            this.$refs.popupTienKM.show();
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
        closeFormDevice(e) {
            this.$refs.popupBuyDevices.hide();
            if(e){
                this.dsCTM_TBI = [...e];
                console.log('asdasdasdas ' ,e);
                this.HT_ThietBi();
            }
        },
        closeFormChooseDevice(e){
            this.$refs.popupChooseDevices.hide();
            if(e){
                this.dsCTM_TBI = [...e];
                console.log('asdasdasdas ' ,e);
                this.HT_ThietBi();
            }
        },
        closeFormDinhKem(e){
            this.$refs.popupDinhKem.hide();
            if(e){
                this.HT_ThueBao();
                this.DS_ThueBao = [...e];
            }
        },
        HT_ThueBao(){
            try {
                let tongtien = 0;
                this.TTDK.txtareaTB = []; // lsbDanhSachThueBao.Items.Clear();

                for(let i = 0; i < this.DS_ThueBao.length; i++)
                {
                    let tenTb = this.DS_ThueBao[i]["MA_TB"].toString() + " - Doanh thu(" + this.DS_ThueBao[i]["DOANHTHU"].toString() + 
                    "," + this.DS_ThueBao[i]["TENGOI_HT"].toString() + ") - Dự kiến:(" + this.DS_ThueBao[i]["DOANHTHU_DK"].toString() + "," 
                    + DS_ThueBao[i]["TEN_GOI"].toString() + ")";
                    this.TTDK.txtareaTB.push(tenTb);
                    tongtien += Number(this.DS_ThueBao[i]["DOANHTHU"]);
                }
                this.TTDK.tong_doanh_thu = tongtien.toString();
            } catch (error) {
                console.log(error);
            }
        },
        btnLayThietBi_Click() {
            try {
                // this.TTTT.kieu_yc = 90;
                // alert(this.TTTT.kieu_yc)
                if (Number(this.TTTT.kieu_yc == 49)) {
                    // frmCTMuaTBI f = new frmCTMuaTBI(dsCTM_TBI, Number(cboLoaiHinhTB.EditValue));
                    this.dataDevice.ds = this.dsCTM_TBI;
                    this.dataDevice._loaitb_id = this.TTTT.loai_hinh;
                    console.log('lololol ', this.dataDevice);
                    this.$refs.popupBuyDevices.show();
                }
                if (Number(this.TTTT.kieu_yc) == 90) {
                    
                    // frmChonTbi f = new frmChonTbi(dsCTM_TBI, Number(cboLoaiHinhTB.EditValue),1);
                    this.dataDevice.ds = this.dsCTM_TBI;
                    this.dataDevice['_loaitb_id'] = this.TTTT.loai_hinh;
                    this.dataDevice.kieu = 1;
                    this.$refs.popupChooseDevices.show();
                }
                if (Number(this.TTTT.kieu_yc) == 7001) {
                    // frmChonTbi f = new frmChonTbi(dsCTM_TBI, Number(cboLoaiHinhTB.EditValue),2);
                    this.dataDevice.ds = this.dsCTM_TBI;
                    this.dataDevice['_loaitb_id'] = this.TTTT.loai_hinh;
                    this.dataDevice.kieu = 2;
                    this.$refs.popupChooseDevices.show();
                }
            } catch (error) {
                console.log(error);
            }
        },
        acceptMaTB(e) {
            // console.log(e);
            this.TTTT.ma_tb = e.ma_tb;
        },
        async txtMaGD_KeyPress(e) {
            try {
                // alert(e);
                this.HienThiThongTinHDKH();
            } catch (error) {
                console.log(error);
            }
        },
        async txtMaTB_KeyPress(){
            // console.log(this.$v);
            // if(this.$v.TTTT.dich_vu.$invalid){
            //     this.$toast.warning("Chọn dịch vụ !!")
            // }else{
            //     this.HienThiTTDanhBaKH();
            // }
            this.HienThiTTDanhBaKH();
        },
        async changeDV(d) {
            try {
                // BSS-38484_002
                let resLoaiHinh = await this.axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB');
                // this.cbLoaiHinhCommon = [...resLoaiHinh.data.data];
                // this.cbLoaiHinh = [...this.cbLoaiHinhCommon];
                this.cbLoaiHinh = resLoaiHinh.data.data.filter(i => i.DICHVUVT_ID == d);
                this.TTTT.loai_hinh = this.cbLoaiHinh[0]['LOAITB_ID'];
            } catch (error) {
                console.log(error);
            }
        },
        changeKieuYC() {
            try {
                if (this.ds_dbkh && this.ds_dbkh.length > 0) {
                    this.HienThiQuyTrinhDonVi(this.ds_dbkh[0]['donvi_id']);
                }
                else {
                    this.HienThiQuyTrinhDonVi(0);
                }
            } catch (error) {
                console.log(error);
            }
        },
        selected(e) {
            console.log(e);
        },
        openPopup() {
            // console.log('xxx');
            // alert('e')
            this.$refs.popupBuyDevices.show();
        },
        hideModalBuyDevice() {
            // alert('close')
            this.$refs.popupBuyDevices.hide();
        },
        hideModalChooseDevice(){
            this.$refs.popupChooseDevices.hide();
        },
        OpenpopupModalNormalizeCAinformation() {
            this.$refs.popupModalNormalizeCAinformation.showModal();
        },
        acceptSearchContract(e) {
            try {
                // console.log('xxxx ', e);
                this.TTTT.ma_gd = e.ma_gd;
                this.HienThiThongTinHDKH();
            } catch (error) {
                console.log(error);
            }
        },
        openSearchContract() {
            this.$refs.popupSearchContract.showModal();
        },
        initData() {
            try {
                this.donvi_id = this.$root.token.getDonViID();
                this.nhanvien_id = this.$root.token.getNhanVienID();;
                console.log(this.nhanvien_id);
                this.form.ngay_yc = moment(new Date()).format("DD/MM/YYYY");
                this.ngay_yc = moment(new Date()).format("DD/MM/YYYY");

            } catch (error) {
                this.informError(error);
            }
        },
        clickButton(key) {
            if (!this.button[key]) {
                return false;
            }
            if (key == "nhapmoi") {
                // this.$refs.popupBuyDevices.show();
                this.tsbtnNhapMoi_Click();
            }
            if (key == "ghilai") {
                this.tsbtnGhiLai_Click();
            }
            if (key == "huy") {
                this.tsbtnHuy_Click();
            }
            if (key == "xoaHD") {
                this.tsbtnXoa_Click();
            }
            if (key == "thanhtoan") {
                this.btnThanhToan_Click();
            }
        },
        tsbtnNhapMoi_Click() {
            this.XoaForm();
            this.button.nhapmoi = false;
        },
        tsbtnGhiLai_Click() {
            this.capnhat();
        },
        tsbtnHuy_Click() {
            this.XoaForm();
        },
        tsbtnXoa_Click() {
            this.XoaHD();
        },
        btnThanhToan_Click() {
            // this.$refs.popupPayment.show();
            // this.$router.push('./SaleDevice_TTVT/Payment')
            if (!this.TTTT.ma_gd || this.TTTT.ma_gd.toString().trim() == '') {
                this.$toast.error('Chưa có thông tin mã giao dịch!')
                return
            }
            this.$router.push({
                name: './SaleDevice_TTVT/Payment',
                params: {
                    tag: 1,
                    magd: this.TTTT.ma_gd,
                    dichvuId: Number(this.TTTT.dich_vu),
                    loaihdId: 15
                }
            })
        },
        async XoaHD() {
            try {
                // if (!this.button.nhapmoi) {
                //     this.$toast.warning("Không đúng trạng thái không thể xoá");
                //     return;
                // }

                this.$confirm('Bạn có muốn xóa hợp đồng này không?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: "Không"
                }).then(async () => {
                    // kiem tra IP_CN UR2.6.042_010_007
                    let res = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', {
                        type: 2,
                        param: this.hdtb_id
                        // id_neo: "tthd_id",
                        // table: "css.hd_thuebao",
                        // dk: "where hdtb_id  = " + this.hdtb_id
                    });
                    if (res.data.data) {
                        this.axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2', {
                            hdkh_id: this.hdkh_id,
                            loaihd_id: 15
                        });
                        this.$toast.success("Xóa HD thành công!");
                        this.XoaForm();
                    } else {
                        this.$toast.warning("Xóa HD không thành công!");
                    }
                }).catch(() => {
                    return;
                })
            } catch (error) {
                console.log(error);
            }
        },
        async capnhat() {
            try {
                if (Number(this.TTTT.kieu_yc) == 49) {
                    // var dt_luong = bangts.LayHuongGiao_TiepNhan(LoaiHopDong.BAN_THIETBI, Number(cboLoaiHinhTB.EditValue), tocdo_id, muccuoc_id, 49);
                    let res_dt_luong = await this.axios.post('/web-hopdong/dichvu-ca/layhuonggiao_tiepnhan', {
                        vloaitb_id: this.TTTT.loai_hinh,
                        vtocdo_id: this.tocdo_id,
                        vmuccuoc_id: this.muccuoc_id,
                        vkieuld_id: 49,
                        vloaihd_id: 15 // LoaiHopDong.BAN_THIETBI
                    });
                    let dt_luong = [];
                    if(res_dt_luong.data.data && res_dt_luong.data.error == '200' && res_dt_luong.data.error_code == 'BSS-00000000'){
                        dt_luong = res_dt_luong.data.data;
                    }
                    let quytrinh_id = 0;
                    let huonggiao_id = 0;
                    if (dt_luong.length == 0) {
                        this.$toast.warning("Quy trình chưa được thiết lập, bạn vui lòng liên hệ Admin để được hỗ trợ!");
                        return;
                    }
                    quytrinh_id = Number(dt_luong[0]["quytrinh_id"]);
                    huonggiao_id = Number(dt_luong[0]["huonggiao_id"]);
                    let dl_hdkh = await this.TaoDuLieuHDKhachHang();
                    let dl_hdtb = await this.TaoDuLieuHDThueBao(dl_hdkh.HDKH_ID, quytrinh_id);
                    let dl_tienHD = await this.TaoDuLieu_CT_TIENHD(dl_hdtb.HDTB_ID);
                    let dl_ct_tbi = await this.TaoDuLieuCTThietBi(dl_hdtb.HDTB_ID);
                    let dl_hdtb_dv = await this.TaoDuLieu_HDTB_DV(dl_hdtb.HDTB_ID);
                    // var thucthi = tdan.GET_VALUE_FUNC("{?DB2}.CAPNHAT_HD_MUA_THIETBI",
                    //     "vthuebao_id", thuebao_id,
                    //     "vkhachhang_id", khachhang_id,
                    //     "vhdtb_id", dl_hdtb.HDTB_ID,
                    //     "vhuonggiao_id", huonggiao_id,
                    //     "vds_hdkh", JsonConvert.SerializeObject(dl_hdkh),
                    //     "vds_hdtb", JsonConvert.SerializeObject(dl_hdtb),
                    //     "vds_cttt", JsonConvert.SerializeObject(dl_tienHD),
                    //     "vds_ctmua_tbi", JsonConvert.SerializeObject(dl_ct_tbi),
                    //     "vds_hdtb_dv", JsonConvert.SerializeObject(dl_hdtb_dv),
                    //     "vnguoi_cn", tt_nd.nhanvien_id,
                    //     "vmay_cn", tt_nd.may_cn,
                    //     "vip_cn", tt_nd.ip,
                    //     "vkieu", _isNhapMoi ? 0 : 1
                    // );
                    let res_capnhat = await this.axios.post('/web-hopdong/dichvu-ca/capnhat_hd_mua_thietbi_ttvt', {
                        vthuebao_id: this.thuebao_id,
                        vkhachhang_id: this.khachhang_id,
                        vhdtb_id: dl_hdtb.HDTB_ID,
                        vhuonggiao_id: huonggiao_id,
                        vds_hdkh: [dl_hdkh], // JSON.stringify([dl_hdkh]),
                        vds_hdtb: [dl_hdtb], // JSON.stringify([dl_hdtb]),
                        vds_cttt: dl_tienHD, // JSON.stringify(dl_tienHD),
                        vds_ctmua_tbi: dl_ct_tbi, // JSON.stringify(dl_ct_tbi),
                        vds_hdtb_dv: dl_hdtb_dv, // JSON.stringify(dl_hdtb_dv),
                        vds_thuebao: this.DS_ThueBao, // JSON.stringify(this.DS_ThueBao),
                        vds_vattu: this.DS_VatTu, // JSON.stringify(this.DS_VatTu),
                        vkieu: this.button.nhapmoi ? 1 : 0,
                        vnguoi_cn: this.nhanvien_id,
                        vmay_cn: this.tt_nd ? this.tt_nd.may_cn : '',
                        vip_cn: this.ip ? this.ip : '1.1.1.1',
                    });
                    if (res_capnhat.data.data && res_capnhat.data.data.toString() == "ok") {
                        this.TTTT.ma_gd = dl_hdkh.MA_GD;
                        this.$toast.success("Thêm thành công");
                        await this.HienThiThongTinHDKH();
                    }
                    else {
                        this.$toast.error(res_capnhat.data.message_detail);
                    }
                }
                else {
                    // var dt_luong = bangts.LayHuongGiao_TiepNhan(LoaiHopDong.BAN_THIETBI, Convert.ToInt32(cboLoaiHinhTB.EditValue), tocdo_id, muccuoc_id, Convert.ToInt32(cboLoaiLyDo.EditValue));
                    let res_dt_luong = await this.axios.post('/web-hopdong/dichvu-ca/layhuonggiao_tiepnhan', {
                        vloaitb_id: this.TTTT.loai_hinh,
                        vtocdo_id: this.tocdo_id,
                        vmuccuoc_id: this.muccuoc_id,
                        vkieuld_id: this.TTTT.kieu_yc,
                        vloaihd_id: 15
                    });
                    let dt_luong = [];
                    if(res_dt_luong.data.data && res_dt_luong.data.error == '200' && res_dt_luong.data.error_code == 'BSS-00000000'){
                        dt_luong = res_dt_luong.data.data;
                    }
                    let quytrinh_id = 0;
                    let huonggiao_id = 0;
                    if (dt_luong.length == 0) {
                        this.$toast.warning("Quy trình chưa được thiết lập, bạn vui lòng liên hệ Admin để được hỗ trợ!");
                        return;
                    }
                    if (Number(this.TTTT.kieu_yc) == 7001) {
                        if (this.DS_ThueBao.length == 0) {
                            this.$toast.warning("Bạn chưa chọn danh sách thuê bao!");
                            return;
                        }
                    }

                    //HT_DS_KhuyenMai_Combobox(vdoituong_id, tocdo_id, muccuoc_id);
                    quytrinh_id = Number(dt_luong[0]["quytrinh_id"]);
                    huonggiao_id = Number(dt_luong[0]["huonggiao_id"]);
                    
                    let dl_hdkh = await this.TaoDuLieuHDKhachHang();
                    console.log(dl_hdkh);
                    for (let dr in this.DS_ThueBao) {
                        dr["hdkh_id"] = dl_hdkh.HDKH_ID;
                    }
                    let dl_hdtb = await this.TaoDuLieuHDThueBao(dl_hdkh.HDKH_ID, quytrinh_id);
                    console.log(dl_hdtb);
                    for (let dr in this.DS_VatTu) {
                        dr["hdtb_id"] = dl_hdtb.hdtb_id;
                    }
                    let dl_tienHD = await this.TaoDuLieu_CT_TIENHD(dl_hdtb.HDTB_ID);
                    let dl_ct_tbi = await this.TaoDuLieuCTThietBi(dl_hdtb.HDTB_ID);
                    let dl_hdtb_dv = await this.TaoDuLieu_HDTB_DV_TTVT(dl_hdtb.HDTB_ID);
                    if (Number(this.TTTT.kieu_yc) == 90) {
                        if (!dl_ct_tbi || !dl_ct_tbi.length) {
                            this.$toast.warning("Bạn chưa chọn chủng loại thiết bị cần mua!");
                            return;
                        }
                        if (!dl_tienHD || !dl_tienHD.length) {
                            this.$toast.warning("Hợp đồng bán thiết bị phải có tiền hợp đồng!");
                            return;
                        }
                    }
                    console.log('xx: ', dl_hdtb_dv)
                    let res_capnhat = await this.axios.post('/web-hopdong/dichvu-ca/capnhat_hd_mua_thietbi_ttvt', {
                        vthuebao_id: this.thuebao_id,
                        vkhachhang_id: this.khachhang_id,
                        vhdtb_id: dl_hdtb.HDTB_ID,
                        vhuonggiao_id: huonggiao_id,
                        vds_hdkh: [dl_hdkh], // JSON.stringify([dl_hdkh]),
                        vds_hdtb: [dl_hdtb], // JSON.stringify([dl_hdtb]),
                        vds_cttt: dl_tienHD, // JSON.stringify(dl_tienHD),
                        vds_ctmua_tbi: dl_ct_tbi, // JSON.stringify(dl_ct_tbi),
                        vds_hdtb_dv: dl_hdtb_dv, // JSON.stringify(dl_hdtb_dv),
                        vds_thuebao: this.DS_ThueBao, // JSON.stringify(this.DS_ThueBao),
                        vds_vattu: this.DS_VatTu, // JSON.stringify(this.DS_VatTu),
                        vkieu: this.button.nhapmoi ? 1 : 0,
                        vnguoi_cn: this.nhanvien_id,
                        vmay_cn: this.tt_nd ? this.tt_nd.may_cn : '',
                        vip_cn: this.ip ? this.ip : '1.1.1.1',
                    });
                    if (res_capnhat.data.error_code == 'BSS-00000000' && res_capnhat.data.data.toString() == "ok") {
                        this.TTTT.ma_gd = dl_hdkh.MA_GD;
                        this.$toast.success("Thêm thành công");
                        await this.HienThiThongTinHDKH();
                    }
                    else {
                        this.$toast.error(res_capnhat.data.message_detail);
                    }
                }

            }
            catch (exp) {
                this.$toast.error("Thất bại: " + exp);
                console.log(exp);
            }
        },
        TaoDuLieu_HDTB_DV_TTVT(in_hdtb_id)
        {
            try
            {
                console.log('lolololol: ', this.dtControl);
                let ds = []; // new List<vHDTB_DV>();
                for(let i = 0; i < this.dtControl.length; i++)
                {
                    let t = {};
                    t.DONVI_ID = Number(this.a.Tag ? this.a.Tag : 0);
                    t.HDTB_ID = in_hdtb_id;
                    t.LOAIDV_ID = Number(this.dtControl[i]['loaidv_dich_id'] ? this.dtControl[i]['loaidv_dich_id'] : 0);
                    t.KIEUDV_ID = 2;
                    ds.push(t);
                }
                return ds;
            }
            catch (ex)
            {
                console.log(ex);
            }
        },
        async TaoDuLieuHDKhachHang() {
            try {
                let vds_hdkh = {};
                if (!this.button.nhapmoi) {
                    // vds_hdkh.HDKH_ID = tdan.GET_KEYS("HD_KHACHHANG");
                    let res_key = await this.axios.post('/web-hopdong/khoiphucthanhly/get_keys', {
                        keyname: "HD_KHACHHANG"
                    });
                    vds_hdkh.HDKH_ID = await this.getKey("HD_KHACHHANG");// res_key.data.data;
                    // vds_hdkh.MA_GD = new LapHopDongFacade().Lay_Ma_GD(LoaiHopDong.BAN_THIETBI);
                    let res_magd = await this.axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', {
                        loaihd_id: 15
                    });
                    vds_hdkh.MA_GD = res_magd.data.data;
                }
                else {
                    vds_hdkh.HDKH_ID = this.hdkh_id;
                    vds_hdkh.MA_GD = this.TTTT.ma_gd;
                }
                vds_hdkh.KHACHHANG_ID = this.khachhang_id;
                vds_hdkh.LOAIHD_ID = 15;

                vds_hdkh.NGAY_YC = moment(new Date()).format("DD/MM/YYYY");
                vds_hdkh.NGAYLAP_HD = moment(new Date()).format("DD/MM/YYYY");
                vds_hdkh.NHANVIEN_ID = this.nhanvien_id;
                vds_hdkh.DONVI_ID = this.donvi_id;
                return vds_hdkh;
            } catch (error) {
                console.log(error);
            }
        },
        async TaoDuLieuHDThueBao(in_hdkh_id, quytrinh_id) {
            try {
                let vds_hdtb = {};
                if (!this.button.nhapmoi) {
                    // vds_hdtb.HDTB_ID = tdan.GET_KEYS("HD_THUEBAO");
                    // let res_key = await this.axios.post('/web-hopdong/khoiphucthanhly/get_keys', {
                    //     keyname: "HD_THUEBAO"
                    // });
                    vds_hdtb.HDTB_ID = await this.getKey("HD_THUEBAO");
                }
                else {
                    vds_hdtb.HDTB_ID = this.hdtb_id;
                }
                vds_hdtb.HDKH_ID = in_hdkh_id;
                vds_hdtb.KIEULD_ID = Number(this.TTTT.kieu_yc);
                vds_hdtb.QUYTRINH_ID = quytrinh_id;
                // vds_hdtb.ten_tb = this.TTTT.ten_tb;
                vds_hdtb.THUEBAO_ID = this.thuebao_id;
                vds_hdtb.TTHD_ID = 1; //TrangThaiHD.MOI;
                vds_hdtb.GHICHU = this.TTTT.ghi_chu;

                return vds_hdtb;
            }
            catch (ex) {
                console.log(ex);
            }
        },
        async getKey(key_name){
            try{
                let res_key = await this.axios.post('/web-hopdong/khoiphucthanhly/get_keys', {
                    keyname: key_name
                });
                let key = res_key.data.data;
                return key;
            }catch(error){

            }
        },
        async TaoDuLieu_CT_TIENHD(in_hdtb_id) {
            try {
                if (this.ds_tien_khoanmuc.length == 0) {
                    this.$toast.error("Chưa có khoản mục thanh toán");
                    return;
                }

                let vds = [];
                // let res_key = await this.axios.post('/web-hopdong/khoiphucthanhly/get_keys', {
                //     keyname: "CT_TIENHD"
                // });
                // let key = res_key.data.data;
                console.log(this.ds_tien_khoanmuc);
                for (let dr of this.ds_tien_khoanmuc) {
                    let key = await this.getKey('CT_TIENHD');
                    let cttien = {};
                    cttien.HDTB_ID = in_hdtb_id;
                    cttien.CT_TIENHD_ID = key;
                    // cttien.id = Number(dr["khoanmuctt_id"]);
                    cttien.KHOANMUCTT_ID = dr["khoanmuctt_id"] ? Number(dr["khoanmuctt_id"]) : 0;
                    cttien.TIEN = dr["tien"]? Number(dr["tien"]) : 0;
                    cttien.VAT = dr["vat"] ? Number(dr["vat"]) : 0;
                    cttien.LOAI_ID = 1;
                    vds.push(cttien);
                }
                return vds;
            } catch (error) {
                console.log(error);
            }
        },
        async TaoDuLieuCTThietBi(in_hdtb_id) {
            try {
                
                let vds_ctmua_tbi = [];
                if (this.dsCTM_TBI.length > 0) {
                    vds_ctmua_tbi = [...this.dsCTM_TBI];
                }
                
                vds_ctmua_tbi.forEach(element => {
                    element.HDTB_ID = in_hdtb_id;
                });
                
                console.log(vds_ctmua_tbi);
                return vds_ctmua_tbi;
            } catch (error) {
                console.log(error);
            }
        },
        async TaoDuLieu_HDTB_DV(in_hdtb_id) {
            try {
                console.log('lolololol: ', this.dtControl);
                let ds = [];
                for (let i = 0; i < this.dtControl.length; i++) {
                    let t = {};
                    t.donvi_id = Number(this.a.Tag);
                    t.hdtb_id = in_hdtb_id;
                    t.loaidv_id = Number(this.dtControl[i]['loaidv_dich_id'] ? this.dtControl[i]['loaidv_dich_id'] : 0);
                    t.kieudv_id = 2;
                    ds.push(t);
                }
                console.log(ds);
                return ds;
            } catch (error) {
                console.log(error);
            }
        },
        XoaForm() {
            this.TBI.txtareaTBI = [];
            this.TTDK.txtareaTB = [];
            this.txtareaVatTu = [];

            this.TTTT.ma_gd = "";
            this.TTTT.ma_tb = "";
            this.dsCTM_TBI = [];
            this.hdkh_i = 0;
            this.hdtb_id = 0;
            // this.loaitb_id = 0;
            this.thuebao_id = 0;
            this.khachhang_id = 0;
            this.doituong_id = 0;
            // this.dichvuvt_id = 0;
            // this.loaitbi_id = 0;
            // this.ct_tienhd_id = 0;
            // this.hdtt_id = 0;
            this.tocdo_id = 0;
            this.muccuoc_id = 0;
            // dtientbi = 0;
            // dvattbi = 0;
            // dtien_kmtb = 0;
            // dvat_kmtb = 0;
            // dtongtien = 0;
            // dtongvat = 0;
            // dtiendatcoc_km = 0;
            // dvatdatcoc_km = 0;
            // tien_tbi = 0;
            // vat_tbi = 0;
            // tien_kmtbi = 0;
            // vat_kmtbi = 0;
            // huonggiaotn_id = 0;//--ttt
            // VDSDiaChi diachiLD = new VDSDiaChi();

            this.TTTT.diachi_ld = null;
            this.TTTT.dia_chi = null;
            this.TTTT.ghi_chu = null;
            this.TTTT.ma_hd = null;
            this.TTTT.ma_kh = null;

            this.TTTT.ten_kh = null;
            this.TTTT.ten_tb = null;
            this.TBI.tong_tien = 0;
            // this.TongTienHD.Text = "0";
            this.TBI.vat = 0;
            this.TTTT.trang_thaitb = null;
            this.options.hopdong = [];
        },

        // frmBanThietBi_Load() {
        //     try {

        //     } catch (error) {
        //         console.log(error);
        //     }
        // },

        // GET THONG TIN NGUOI DUNG
        async GET_THONG_TIN_NGUOI_DUNG(){
            try {
                let res_ttnd = await this.axios.post('/quantri/user/thongtin_nguoidung2', {});
                this.tt_nd = res_ttnd.data.data;
                // console.log(this.tt_nd, this.nhanvien_id, this.donvi_id);
            } catch (error) {
                console.log('GET THONG TIN NGUOI DUNG: ', error);
            }
        },

        // GET DANH SÁCH DỊCH VỤ
        async GET_DANH_SACH_DV(){
            try {
                // BSS-38484_001
                let resDichVu = await this.axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT');
                this.cbDichVu = [...resDichVu.data.data];
                // this.TTTT.dich_vu = this.cbDichVu[0]['DICHVUVT_ID'];
            } catch (error) {
                console.log('GET DANH SÁCH DỊCH VỤ: ', error);
            }
        },
        // GET DANH SACH LOAI HINH
        async GET_DANH_SACH_LOAI_HINH(){
            try {
                // BSS-38484_002
                let resLoaiHinh = await this.axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB');
                // this.cbLoaiHinhCommon = [...resLoaiHinh.data.data];
                // this.cbLoaiHinh = [...this.cbLoaiHinhCommon];
                // this.cbLoaiHinh = resLoaiHinh.data.data.filter(i => i.LOAITB_ID == this.TTTT.dich_vu);
                this.cbLoaiHinh = resLoaiHinh.data.data;
                // this.TTTT.loai_hinh = this.cbLoaiHinh[0]['LOAITB_ID'];
            } catch (error) {
                console.log('GET DANH SACH LOAI HINH: ', error);
            }
        },
        // GET DANH SACH KIEU YC
        async GET_DANH_SACH_YEU_CAU(){
            try {
                // BSS-38484_003
                let resKieuYC = await this.axios.post('/web-ccdv/ds_yeucau_khaosat/lay_ds_kieu_ld', {
                    vloaihd_id: 15, // LoaiHopDong.BAN_THIETBI
                    vloaitb_id: this.TTTT.loai_hinh
                });
                this.cbKieuYC = [...resKieuYC.data.data];
                // if (this.cbKieuYC.map(e => e.kieuld_id).includes(this.Tag)) {
                //     this.TTTT.kieu_yc = this.tag;
                // } else {
                //     this.TTTT.kieu_yc = this.cbKieuYC[0]['kieuld_id'];
                // }
                console.log('tag ', this.tag);
            } catch (error) {
                console.log('get danh sach kieu yeu cau: ', error);
            }
        },
        async getDanhMucChung() {
            try {
                this.GET_THONG_TIN_NGUOI_DUNG();
                this.GET_DANH_SACH_DV();
                this.GET_DANH_SACH_LOAI_HINH();
                this.GET_DANH_SACH_YEU_CAU();
            } catch (error) {
                this.informError(error);
            }
        },

        informError(error) {
            // this.$toast.error("Đã có lỗi xảy ra !!");
            console.log(error);
        },
        // BSS-38484_006
        async HienThiThongTinHDKH() {
            try {
                if (this.TTTT.ma_gd) {
                    // UR2.1.007_008
                    // List<TraCuuHopDong> ds = _hdtbServices.TraCuuHopDongKH_V2(txtMaGD.Text, LoaiHopDong.BAN_THIETBI, tt_nd.donvi_id, TrangThaiHD.MOI, tt_nd.nhanvien_id, 0);
                    // UR2.4.113_023 khoiphucthanhly/lay_ds_thuebao_dichvu
                    let dsHD = await this.axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', {
                        ma_gd: this.TTTT.ma_gd,
                        loaihd_id: 15,
                        donvi_id: this.donvi_id,
                        tthd_id: 1,
                        nhanvien_id: this.nhanvien_id,
                        donvi_dl_id: 0,
                    });
                    let ds = dsHD.data.data;
                    if (ds.length > 0) {
                        this.hdkh_id = ds[0].hdkh_id;
                        // a.Long zem 11052016
                        //cboDichVuVT.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["dichvuvt_id"]);
                        this.TTTT.ma_gd = ds[0].ma_gd;
                        this.TTTT.ma_hd = ds[0].ma_hd;
                        this.TTTT.ma_kh = ds[0].ma_kh;
                        this.khachhang_id = ds[0].khachhang_id;
                        this.TTTT.kieu_yc = ds[0].kieuld_id;
                        this.TTTT.ten_kh = ds[0].ten_kh;
                        this.TTTT.ngay_yc = ds[0].ngay_yc ? ds[0].ngay_yc.slice(0, 10).split('-').reverse().join('/') : '';
                        this.form.ngay_yc = ds[0].ngaylap_hd ? ds[0].ngaylap_hd.slice(0, 10).split('-').reverse().join('/') : '';
                        this.doituong_id = ds[0].doituong_id;

                        // UR2.7.004_013_001
                        // List<TraCuuHDTBTheoHDKH> ds_hdtb = _hdtbServices.LayDanhSachHDTBTheoHDKH_V2(hdkh_id);
                        // POST: web-hopdong/khuyenmai_tocdo_internet/lay_ds_hopdong_tb_theo_hdkh_id
                        // {
                        //     "in_hdkh_id": 5161
                        // }
                        let TraCuuHDTBTheoHDKH = await this.axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_ds_hopdong_tb_theo_hdkh_id', {
                            in_hdkh_id: this.hdkh_id
                        });
                        let ds_hdtb = TraCuuHDTBTheoHDKH.data.data
                        console.log(ds_hdtb);
                        this.HienThiTTHopDongTB(ds_hdtb);

                        // UR2.1.078_002
                        this.LayCTTienKM();
                        if (Number(this.TTTT.kieu_yc) != 49) {
                            // BSS-38484_006 - 5
                            this.hienthi_thongtin_chitiet(this.hdkh_id, ds_hdtb[0].hdtb_id);
                        }
                    } else {
                        this.$toast.warning("Không tìm thấy mã giao dịch!");
                    }
                }
                else {
                    this.$toast.warning("Không tìm được hợp đồng");
                }
            } catch (error) {
                console.log(error);
            }
        },
        async hienthi_thongtin_chitiet(hdkh_id, hdtb_id) {
            try {
                // POST: /web-ccdv/ban_thietbi_ttvt/lay_ds_gui_chitiet
                // REQ:
                // {
                //     "p_hdkh_id": 5973058,
                //     "p_hdtb_id": 6694001,
                //     "p_loai": 1
                // }
                // DS_ThueBao = tdan.GET_DATA_PROC(DatabaseConstants.DB2 + ".lay_ds_gui_chitiet",
                // "vhdkh_id", hdkh_id,
                // "vhdtb_id", hdtb_id,
                // "vloai", 1
                // );

                // DS_VatTu = tdan.GET_DATA_PROC(DatabaseConstants.DB2 + ".lay_ds_gui_chitiet",
                //     "vhdkh_id", hdkh_id,
                //     "vhdtb_id", hdtb_id,
                //     "vloai", 2
                //     );
                let res = await this.axios.post('/web-ccdv/ban_thietbi_ttvt/lay_ds_gui_chitiet', {
                    p_hdkh_id: hdkh_id,
                    p_hdtb_id: hdtb_id,
                    p_loai: 1
                });
                if(res.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    this.DS_ThueBao = res.data.data;
                }else{
                    this.DS_ThueBao = [];
                }
                let res1 = await this.axios.post('/web-ccdv/ban_thietbi_ttvt/lay_ds_gui_chitiet', {
                    p_hdkh_id: hdkh_id,
                    p_hdtb_id: hdtb_id,
                    p_loai: 2
                });
                if(res.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    this.DS_VatTu = res1.data.data;
                }else{
                    this.DS_VatTu = [];
                }
                this.HT_ThueBao();
                //HT_VATTU();
            } catch (error) {
                console.log(error);
            }
        },
        HT_ThueBao() {
            try {
                // int soluong = 0;
                var tongtien = 0;
                this.TTDK.txtareaTB = [];

                for (let i = 0; i < this.DS_ThueBao.length; i++) {
                    let tenTb = this.DS_ThueBao.Rows[i]["MA_TB"].ToString() +
                        " - Doanh thu(" + this.currencyFormater(this.DS_ThueBao.Rows[i]["DOANHTHU"]).toString() + ","
                        + this.DS_ThueBao.Rows[i]["TENGOI_HT"].toString() + ") - Dự kiến:("
                        + this.currencyFormater(this.DS_ThueBao.Rows[i]["DOANHTHU_DK"]) + ","
                        + this.DS_ThueBao.Rows[i]["TEN_GOI"].toString() + ")";
                    this.TTDK.txtareaTB.push(tenTb);
                    tongtien += (this.DS_ThueBao.Rows[i]["DOANHTHU"]);
                }
                this.TTDK.tong_doanh_thu = tongtien.toLocaleString('vi', {style : 'currency', currency : 'VND'});
            } catch (error) {
                console.log(error);
            }
        },
        async HienThiTTHopDongTB(ds_hdtb) {
            try {
                if (ds_hdtb.length > 0) {
                    let tchd = ds_hdtb[0];
                    this.thuebao_id = tchd.thuebao_id;
                    this.hdtb_id = tchd.hdtb_id;
                    this.TTTT.ten_tb = tchd.ten_tb;
                    this.TTTT.ma_tb = tchd.ma_tb;
                    this.TTTT.ma_gd = tchd.ma_gd;
                    this.TTTT.diachi_ld = tchd.diachi_ld;
                    this.TTTT.dia_chi = tchd.diachi_tb;
                    this.TTTT.dich_vu = tchd.dichvuvt_id;
                    this.TTTT.ghichu = tchd.ghichu;
                    this.TTTT.loai_hinh = tchd.loaitb_id;
                    await this.LoadDSTBIHDTB();
                    await this.HT_ThietBi();
                    this.options.hopdong = ds_hdtb;

                    
                    // await this.HienThiTTDanhBaKH();
                    if (Number(this.TTTT.kieu_yc) != 49) {
                        this.set_donvi(this.thuebao_id);
                    }
                    this.button.nhapmoi = false;
                }
            } catch (error) {
                console.log(error);
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
                // tien_tgtb_tragop = 0;
                // vat_tgtb_tragop = 0;
                // tien_tgtb_tratruoc = 0;
                // vat_tgtb_tratruoc = 0;
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
                // lsbDanhSachTBI.Items.Clear();
                // console.log(' jkhdsjkhasdjkhasdjkh ',this.dsCTM_TBI);
                
                let dsTBI = this.dsCTM_TBI.map(e => {
                    if(e['LOAI_TBI'])
                        return (e['LOAI_TBI'] + " - Số lượng: " +  e['SOLUONG']);
                    return (e['loai_tbi'] + " - Số lượng: " +  e['soluong']);
                });
                this.TBI.txtareaTBI = dsTBI;
                console.log(dsTBI);

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

                    // ĐOẠN CODE NÀY CÓ THỂ SẼ DÙNG ĐỂ BUNG SUNG để mở rộng cho dịch vụ CNTT/GTGT
                    // if (vsl_cha != -1)
                    // {
                    //     if (dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString() != ""
                    //         && dsCTM_TBI.Tables[0].Rows[i]["gia_block_tiep"].ToString() != ""
                    //         && dsCTM_TBI.Tables[0].Rows[i]["gia_block_tiep"].ToString() != "0")
                    //     {
                    //         if (Convert.ToInt32(cboLoaiHinhTB.EditValue) == LoaiHinhTB.IOFFICE
                    //             || Convert.ToInt32(cboLoaiHinhTB.EditValue) == LoaiHinhTB.HNTH_NGN
                    //             || Convert.ToInt32(cboLoaiHinhTB.EditValue) == LoaiHinhTB.VNPT_MEETING)
                    //         {
                    //             if (vsl_cha > Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_dau"].ToString()))
                    //             {
                    //                 long vsl_conlai = vsl_cha - Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_dau"].ToString());
                    //                 long t2 = vsl_conlai % Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString());
                    //                 long vsl_bl = 0;
                    //                 if (t2 > 0)
                    //                     vsl_bl = vsl_conlai / Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString()) + 1;
                    //                 else
                    //                     vsl_bl = vsl_conlai / Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString());
                    //                 decimal tien_block = 0;
                    //                 tien_block = vsl_bl * Convert.ToDecimal(dsCTM_TBI.Tables[0].Rows[i]["gia_block_tiep"].ToString());
                    //                 dtien_tbi_tmp += tien_block;
                    //                 tien_tbi = tien_tbi + (tien_block * soluong);
                    //             }
                    //         }
                    //     }
                    //     if (dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString() != ""
                    //         && dsCTM_TBI.Tables[0].Rows[i]["vat_block_tiep"].ToString() != ""
                    //         && dsCTM_TBI.Tables[0].Rows[i]["vat_block_tiep"].ToString() != "0")
                    //     {
                    //         if (Convert.ToInt32(cboLoaiHinhTB.EditValue) == LoaiHinhTB.IOFFICE
                    //             || Convert.ToInt32(cboLoaiHinhTB.EditValue) == LoaiHinhTB.HNTH_NGN
                    //             || Convert.ToInt32(cboLoaiHinhTB.EditValue) == LoaiHinhTB.VNPT_MEETING)
                    //         {
                    //             if (vsl_cha > Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_dau"].ToString()))
                    //             {
                    //                 long vsl_conlai = vsl_cha - Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_dau"].ToString());
                    //                 long t3 = vsl_conlai % Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString());
                    //                 long rsl_bl = 0;
                    //                 if (t3 > 0)
                    //                     rsl_bl = vsl_conlai / Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString()) + 1;
                    //                 else
                    //                     rsl_bl = vsl_conlai / Convert.ToInt64(dsCTM_TBI.Tables[0].Rows[i]["block_tiep"].ToString());
                    //                 decimal vat_block = 0;
                    //                 vat_block = rsl_bl * Convert.ToDecimal(dsCTM_TBI.Tables[0].Rows[i]["vat_block_tiep"].ToString());
                    //                 dvat_tbi_tmp += vat_block;
                    //                 vat_tbi = vat_tbi + (vat_block * soluong);
                    //             }
                    //         }
                    //     }
                    // }

                    // DataTable dtTMP_KMTBI = bangts.LayDS_KM_ThietBi(ds_CTKM(), dsCTM_TBI.Tables[0].Rows[i]["loaitbi_id"].ToString());
                    // dtien_kmtbi_tmp = 0;
                    // dvat_kmtbi_tmp = 0;
                    // if (dtTMP_KMTBI.Rows.Count > 0)
                    // {
                    //     dtien_kmtbi_tmp = Convert.ToDecimal(dtTMP_KMTBI.Rows[0]["tien_km"]);
                    //     dvat_kmtbi_tmp = Convert.ToDecimal(dtTMP_KMTBI.Rows[0]["vat_km"]);
                    //     dTyLe_KMTB = Convert.ToDecimal(dtTMP_KMTBI.Rows[0]["tyle_km"]);
                    // }
                    // else // hoangpkn : 13/12/2018. Thêm đoạn else
                    // {
                    //     dtien_kmtbi_tmp = 0;
                    //     dvat_kmtbi_tmp = 0;
                    //     dTyLe_KMTB = 0;
                    // }

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
                this.LayTongTien_HDTB(tien_tbi, vat_tbi);
                this.HienThiTienKhoanMucMuaThietBi(tien_tbi, vat_tbi);

                // this.ds_tien_khoanmuc = [];
                // // UR2.1.078_002
                // let res = await this.axios.get('/web-ccdv/ban-thiet-bi/sp_lay_tienkhoanmuc_loaihd_banthietbi');
                // this.ds_tien_khoanmuc = res.data.data;
            } catch (error) {
                console.log(error);
            }
        },
        HienThiTienKhoanMucMuaThietBi(tien_tbi, vat_tbi){
            if (this.ds_tien_khoanmuc.length > 0)
            {
                for(let j = 0; j < this.ds_tien_khoanmuc.length; j++)
                {
                    if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"] == 52)
                    {
                        this.ds_tien_khoanmuc[j]["tien"] = tien_tbi;
                        this.ds_tien_khoanmuc[j]["vat"] = vat_tbi;
                    }
                }
            }
        },
        LayTongTien_HDTB(tien_tbi, vat_tbi){
            try
            {
                let dtongtien = 0, dtongvat = 0;
                dtongtien += tien_tbi;
                dtongvat += vat_tbi;
                this.TBI.tong_tien =dtongtien.toLocaleString('vi', {style : 'currency', currency : 'VND'});
                this.TBI.vat = dtongvat.toLocaleString('vi', {style : 'currency', currency : 'VND'});

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
                this.dsCTM_TBI = [];
                let res = await this.axios.post('/web-hopdong/thanhly/lay_ds_mua_tbi', {
                    hdtb_id: this.hdtb_id
                });
                let dstbi = res.data.data;
                if (dstbi.length > 0) {
                    // this.dsCTM_TBI = [...dstbi];
                    console.log(this.dsCTM_TBI);
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
                        this.dsCTM_TBI.push(item);
                    });
                }
            } catch (error) {
                console.log(error);
            }
        },

        // HienThiTTDanhBaKH 
        async HienThiTTDanhBaKH() {
            try {
                //local variable
                let vmuccuoc_id;
                let vtocdoc_id;
                this.ds_dbkh = [];

                // BSS-38484_005 --> UR2.11.009_003
                // this.ds_dbkh = _dbtbServices.TraCuuDanhBa_V2(txtMaTB.Text, Convert.ToInt32(cboDichVuVT.EditValue), 0);
                this.ds_dbkh = await this.TraCuuDanhBa_V2();

                if (this.ds_dbkh.length > 0) {
                    let trangthai_tb_id = this.ds_dbkh[0].trangthai_tb;
                    if (trangthai_tb_id == 7 || trangthai_tb_id == 8 || trangthai_tb_id == 9) {
                        this.$toast.warning("Không thể mua thêm thiết bị cho thuê bao đang dừng hoạt động!");
                        return;
                    }
                    //Thông tin khách hàng   
                    this.khachhang_id = this.ds_dbkh[0].khachhang_id;
                    this.TTTT.ma_hd = this.ds_dbkh[0].ma_hd;
                    this.TTTT.ma_kh = this.ds_dbkh[0].ma_kh;
                    this.TTTT.ten_kh = this.ds_dbkh[0].ten_kh;
                    this.thuebao_id = this.ds_dbkh[0].thuebao_id;
                    this.TTTT.ma_tb = this.ds_dbkh[0].ma_tb;
                    this.TTTT.ten_tb = this.ds_dbkh[0].ten_tb;
                    this.TTTT.dia_chi = this.ds_dbkh[0].diachi_tb;
                    this.TTTT.dich_vu = this.ds_dbkh[0].dichvuvt_id;
                    this.TTTT.diachi_ld = this.ds_dbkh[0].diachi_ld;
                    this.TTTT.trang_thaitb = this.ds_dbkh[0].trangthai_tb;
                    this.doituong_id = this.ds_dbkh[0].doituong_id;
                    // cboLoaiHinhTB.EditValue = this.ds_dbkh[0].LOAITB_ID;
                    this.TTTT.loai_hinh = this.ds_dbkh[0].loaitb_id;

                    // BSS-38484_005 --> UR2.1.035_026
                    let ds = await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_khuvuc_theo_thuebaoid', {
                        vthuebao_id: this.thuebao_id
                    })
                    let diachiLD = ds.data.data ? [...ds.data.data] : [];
                    this.diachiLD = diachiLD[0];
                    // BSS-38484_005 --> UR2.7.004_013_007
                    // LayTTDanhBaCNTT();
                    if(this.TTTT.dich_vu == 13 || this.TTTT.dich_vu == 14 || this.TTTT.dich_vu == 15 || this.TTTT.dich_vu == 16){
                        let LayTTDanhBaCNTT = await this.axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_ds_db_cntt_theo_tbid', {
                            in_thuebao_id: this.thuebao_id
                        });
                        if(LayTTDanhBaCNTT.data.data && LayTTDanhBaCNTT.data.error == '200' && LayTTDanhBaCNTT.data.error_code == 'BSS-00000000'){
                            this.tocdo_id = LayTTDanhBaCNTT.data.data.length ? LayTTDanhBaCNTT.data.data[0]['tocdo_id'] : 0;
                            this.muccuoc_id = LayTTDanhBaCNTT.data.data.length ? LayTTDanhBaCNTT.data.data[0]['muccuoc_id'] : 0;
                        }else{
                            this.tocdo_id = 0;
                            this.muccuoc_id = 0;
                        }
                    }
                    else{
                            this.tocdo_id = 0;
                            this.muccuoc_id = 0;
                        }
                    // else{
                    //     let LayTTDanhBaCNTT = await this.axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_ds_db_adsl_theo_tbid', {
                    //         in_thuebao_id: this.thuebao_id
                    //     });
                    //     if(LayTTDanhBaCNTT.data.data && LayTTDanhBaCNTT.data.error == '200' && LayTTDanhBaCNTT.data.error_code == 'BSS-00000000'){
                    //         this.tocdo_id = LayTTDanhBaCNTT.data.data.length ? LayTTDanhBaCNTT.data.data[0]['tocdo_id'] : 0;
                    //         this.muccuoc_id = LayTTDanhBaCNTT.data.data.length ? LayTTDanhBaCNTT.data.data[0]['muccuoc_id'] : 0;
                    //     }else{
                    //         this.tocdo_id = 0;
                    //         this.muccuoc_id = 0;
                    //     }
                    // }

                    // BSS--38484_005 --> UR2.1.040_014
                    await this.HienThiQuyTrinhDonVi(this.ds_dbkh[0].donvi_id);
                    // BSS--38484_005 --> UR2.1.008_001
                    this.LayCTTienKM();
                    // BSS-38484_005 --> UR2.8.012_049_03
                    await this.set_donvi(this.thuebao_id);
                }
                else {
                    this.$toast.warning("Nhập lại mã thuê bao tìm kiếm !");
                }
            } catch (error) {
                console.log(error);
            }
        },
        async set_donvi(thuebao_id) {
            try {
                if (thuebao_id > 0) {
                    // string sql = "select * from " + DatabaseConstants.DB2 + ".dbtb_dv  where thuebao_id=" + in_thuebao_id+" and loaidv_id=5 and kieudv_id=2";
                    // DataTable dt = tdan.GET_DATA_SQL(sql, "DanhSach");
                    // GET: web-tracuu/danhba/lay_tt_donvi_theo_thuebao?thuebaoId=182702
                    let res = await this.axios.get('/web-tracuu/danhba/lay_tt_donvi_theo_thuebao?thuebaoId=' + thuebao_id);
                    if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                        // if(this.$route.query.tag == 5){
                            // this.TTDV.tram_thicong = res.data.data
                            this.a.Tag = res.data.data
                        // }
                    }
                }
            } catch (error) {
                console.log(error);
            }
        },
        async LayCTTienKM() {
            try {
                if (this.ds_tien_khoanmuc.length > 0) {
                    this.ds_tien_khoanmuc = [];
                }

                // string sql = @"SELECT DISTINCT a.kieuld_id,c.khoanmuctt_id,0 tien, 0 vat, c.tyle_vat
                //                 FROM {?DB2}.kmtt_kieuld a,
                //                 {?DB2}.kieu_ld b,
                //                 {?DB2}.khoanmuc_tt c
                //                 WHERE a.kieuld_id = b.kieuld_id
                //                 AND a.khoanmuctt_id = c.khoanmuctt_id
                //                 AND b.loaihd_id = 15
                //                 ORDER BY c.khoanmuctt_id";
                // DataTable dt = tdan.GET_DATA_SQL(sql);
                let res = await this.axios.post('/web-hopdong/thaydoidichvu/sp_lay_ds_tien_khoanmuc', {
                    loaihd_id: 15
                });
                if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    res.data.data.forEach(item => {
                        if(item['khoanmuctt_id'] == 5 || item['khoanmuctt_id'] == 52){
                            item['tyle_vat'] = 10
                            item['khoanmuctt'] = 'Khuyến Mại T.bị';
                        } else if(item['khoanmuctt_id'] == 20){
                            item['tyle_vat'] = 10
                            item['khoanmuctt'] = 'Mua Thiết Bị';
                        }else{
                            item['tyle_vat'] = 10
                            item['khoanmuctt'] = '';
                        }
                    })
                    this.ds_tien_khoanmuc = res.data.data
                }
            } catch (error) {
                console.log(error);
            }
        },
        async HT_DonVi_TRAMTC_GridLookUp(loaidv_id, donvi_ql_id){
            try {
                let res = await this.axios.post('web-hopdong/dichvu-ca/sp_lay_ds_donvi_tramtc_banthietbi', {
                    "donviql_id": donvi_ql_id,
                    "loaidv_id": loaidv_id
                });
                if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    this.cbTram = res.data.data;
                }
            } catch (error) {
                
            }
        },
        async HT_DonVi_GridLookUp(loaidv_id){
            try {
                let res = await this.axios.post('web-hopdong/dichvu-ca/sp_lay_ds_donvi_theo_loaidv_banthietbi', {
                    "loaidv_id": loaidv_id
                });
                if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    this.cbTram = res.data.data;
                }
            } catch (error) {
                console.log(error);
            }
        },

        async HienThiQuyTrinhDonVi(dvId) {
            try {
                // if(this.TTTT.kieu_yc == 49){
                //     if (this.TTTT.dich_vu == this.DichVuVienThong.TRUNGTAM_DULIEU ||
                //         this.TTTT.dich_vu == this.DichVuVienThong.ANTOAN_BAOMAT_TT ||
                //         this.TTTT.dich_vu == this.DichVuVienThong.DICHVU_CNTT ||
                //         this.TTTT.dich_vu == this.DichVuVienThong.HOINGHI_TRUYENHINH)
                //     {

                //     }
                // }
                this.dtControl = [];
                if(this.TTTT.kieu_yc == 49){
                    let res = await this.axios.post('/web-hopdong/dichvu-ca/sp_ht_giaodien_donvi_theo_loaihd', {
                        "dichvuvt_id": this.TTTT.dich_vu,
                        "tocdo": this.tocdo_id,
                        "muccuoc": this.muccuoc_id,
                        "loaihinhtb_id": this.TTTT.loai_hinh,
                        "kieuld_id": this.TTTT.kieu_yc,
                        "loaidv_id": 4, // LOAI_DV.GIAODICH;
                        "loaihd_id": 15 // LoaiHopDong.BAN_THIETBI;
                    });
                    this.dtControl = res.data.data;
                    if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                        let tem = res.data.data;
                        // TRAM_CAP_PORT = 504;  || TRAM_VT = 5;  
                        for (let index = 0; index < tem.length; index++) {
                            if(tem[i]['loaidv_dich_id'] == 5 || tem[i]['loaidv_dich_id'] == 504){
                                let loaidv_id = 5, donvi_ql_id = dvId;
                                await this.HT_DonVi_TRAMTC_GridLookUp(loaidv_id, donvi_ql_id);
                                // this.cbTram.push({});
                            }else{
                                this.HT_DonVi_GridLookUp(tem[i]['loaidv_dich_id'])
                            }
                        }
                        this.cbTram = tem;
                    }
                }else{
                    
                    let res = await this.axios.post('/web-hopdong/hopdong/sp_lay_ds_quytrinh_ban_thietbi_ttvt_trangbi', {
                        kieuld_id: this.TTTT.kieu_yc
                    });
                    // this.cbTram = res.data.data;
                    if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                        this.dtControl = res.data.data;
                        for(let i = 0; i < this.dtControl.length; i++){
                            let res = await this.axios.post('/web-hopdong/hopdong/sp_lay_ds_donvi_cc_lv2_smp',{ 
                                donvi_id : this.donvi_id,//186, 
                                loaidv_dich_id : this.dtControl[i]['loaidv_dich_id']//5 
                            });
                            if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                                let dt = res.data.data;
                                if(!dt.length){
                                    let res = await this.axios.post('/web-hopdong/dichvu-ca/sp_lay_ds_donvi_theo_loaidv_banthietbi', {
                                        loaidv_id: this.TTTT.dich_vu
                                    });
                                    if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                                        dt = res.data.data;
                                    }
                                }
                                this.cbTram = dt;
                            }
                        }
                    }
                }
            } catch (error) {
                console.log(error);
            }
        },

        async TraCuuDanhBa_V2() {
            try {
                // this.loading(true);
                let res = await this.axios.post('/web-hopdong/thongtin_tratruoc_dn/lay_danhba_theo_matb', {
                    in_ma_tb: this.TTTT.ma_tb,
                    in_dichvuvt_id: this.TTTT.dich_vu ? this.TTTT.dich_vu : 0,
                    in_donvi_dl_id: 0
                });
                // this.loading(false);
                if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    return res.data.data;
                }else{
                    return []
                }
            } catch (error) {
                // this.loading(false);
                console.log(error);
            }
        },
        currencyFormater(c) {
            try {
                // Create our number formatter.
                var formatter = new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD',

                    // These options are needed to round to whole numbers if that's what you want.
                    //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                    //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
                });
                return formatter.format(c);
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
        async openMapTB(dia_chi, type) {
            let data = {
                lat: null,
                lng: null,
            };
            this.map_info = {
                dia_chi: dia_chi,
                title: dia_chi
            }
            if (type == "diachi_tb") {
                this.modeMap = 2;
                let kinhdo = await api.kiemtra_dulieu(this.axios, {
                    inDk: "where hdtb_id = " + this.hdtb_id,
                    inIdNeo: "kinhdo",
                    inTable: "css.diachi_hdtb",
                });
                if (kinhdo.data.data) {
                    data.lng = Number(kinhdo.data.data) > 0 ? Number(kinhdo.data.data) : null;
                }

                let vido = await api.kiemtra_dulieu(this.axios, {
                    inDk: "where hdtb_id = " + this.hdtb_id,
                    inIdNeo: "vido",
                    inTable: "css.diachi_hdtb",
                });
                if (vido.data.data) {
                    data.lat = Number(vido.data.data) > 0 ? Number(vido.data.data) : null;
                }
            } else {
                this.modeMap = 1;
                let kinhdo = await api.kiemtra_dulieu(this.axios, {
                    inDk: "where hdtb_id = " + this.hdtb_id,
                    inIdNeo: "kinhdo_ld",
                    inTable: "css.diachi_hdtb",
                });
                if (kinhdo.data.data) {
                    data.lng = Number(kinhdo.data.data) > 0 ? Number(kinhdo.data.data) : null;
                }

                let vido = await api.kiemtra_dulieu(this.axios, {
                    inDk: "where hdtb_id = " + this.hdtb_id,
                    inIdNeo: "vido_ld",
                    inTable: "css.diachi_hdtb",
                });
                if (vido.data.data) {
                    data.lat = Number(vido.data.data) > 0 ? Number(vido.data.data) : null;
                }
            }

            this.map = data;

            this.$bvModal.show('ModalChonToaDo')
        },
        btnDinhKemChiTiet_Click(){
            this.$refs.popupDinhKem.show();
        },
        formatPrice(value) {
            let val = (value/1).toFixed(2).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        }
    },
    mounted() {
        // this.handleTag()
        // console.log(this.$root.token.getMaTinh());
    }
}
</script>

<style>
.list a {
    cursor: pointer;
}
/* @import "../../../../../node_modules/@syncfusion/ej2-vue-layouts/styles/material.css"; */
/* @import "../../../../../node_modules/@syncfusion/ej2-vue-buttons/styles/material.css"; */

.e-splitter .e-split-bar.e-split-bar-horizontal {
    /* background: blue; */
    border: solid rgba(41 42 71 / 54%);
}

.e-splitter .e-split-bar.e-split-bar-horizontal .e-resize-handler {
    color: rgb(255, 255, 255);
    font-weight: bolder;
}

/* .e-splitter .e-split-bar.e-split-bar-horizontal .e-resize-handler {
    color: rgba(20, 27, 233, 0.54);
}
.e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-left::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-left::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-left::after, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-left::after, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-right::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-right::before, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-hover .e-navigate-arrow.e-arrow-right::after, .e-splitter .e-split-bar.e-split-bar-horizontal.e-split-bar-active .e-navigate-arrow.e-arrow-right::after {
    background-color: green;
} */

/* #app-split {
        text-align: center;
        margin: 45px auto;
    }

    .content {
        justify-content: center;
        text-align: center;
        align-items: center;
        padding: 9px;
    }

    .e-splitter {
        margin: 0 auto;
    }

    .e-pane {
        text-align: center;
        align-items: center;
        display: grid;
    }

    #collapse {
        margin-left: 10px;
    } */

@font-face {
    font-family: "e-listbox-icons";
    src: url(data:application/x-font-ttf;charset=utf-8;base64,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) format("truetype");
    font-weight: normal;
    font-style: normal;
}

.e-list-icons {
    font-family: "e-listbox-icons";
    speak: none;
    font-style: normal;
    font-weight: normal;
    font-variant: normal;
    text-transform: none;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

.e-list-touch:before {
    content: "\e700";
}

.e-list-reply-all:before {
    content: "\e701";
}

.e-list-reply:before {
    content: "\e702";
}

.e-list-save-all-attachments:before {
    content: "\e703";
}

.e-list-icon-save-as:before {
    content: "\e704";
}

.e-list-user-settings:before {
    content: "\e705";
}

.e-list-address-book:before {
    content: "\e706";
}

.e-list-delete:before {
    content: "\e707";
}

.e-list-forward:before {
    content: "\e708";
}

.e-list-undo:before {
    content: "\e709";
}

.e-listbox-wrapper:not(.e-list-template) .e-list-item {
    padding: 4px 16px;
    height: 30px;
}

.e-listbox-wrapper .e-list-item,
.e-listbox-container .e-list-item {
    background-color: #fff;
    border: none;
    /* padding: 2px 16px; */
    /* color: #212529; */
}
</style>