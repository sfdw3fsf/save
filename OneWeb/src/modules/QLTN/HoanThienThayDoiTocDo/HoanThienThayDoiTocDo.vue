<template src="./HoanThienThayDoiTocDo.html"></template>
<style scoped src="../TraCuuPhieuTraTheoNgay/TraCuuPhieuTraTheoNgay.scss"></style>
<script>
import SearchContractModal from '@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue'
import { mapActions, mapGetters } from 'vuex'
import CommonsAPI from '../api/Commons.js'
import TCAPI from '../api/TiepNhanTTKhoaMoMayNoCuocAPI.js'
import NHAPI from '../api/NangHaGoiAPI.js'
import XuatFile from "../../CSKH/utils/XuatFile.vue"
import moment from 'moment'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import {Splitter} from '@syncfusion/ej2-layouts'
import Vue from 'vue'
Vue.use(SplitterPlugin)

export default {
    components: { appModalXuatDuLieu: XuatFile, SearchContractModal },
    name: 'HoanThienThayDoiTocDo',
    async created () {
        await this.initForm();               
    },

    mounted() {
        this.$refs.maTB.focus();
    },

    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter', 'danhsachDonViGetter'])        
    },

    data() {
        return {
            params: {
                dichVu_Options: [],
                dichVu_Selected: null,
                quyTrinh_Options: [],
                quyTrinh_Selected: null,
                kieuYC_Options: [],
                kieuYC_Selected: null,
                nguoiGV_Options: [],
                nguoiGV_Selected: null,
                tocDoMoi_Options: [],
                tocDoMoi_Selected: null,
                tocDoCu_Options: [],
                tocDoCu_Selected: null,
                today: new Date(),
                ngayHT_Checked: false,
                ngayHT: new Date(),                
                ngayGV_Checked: false,
                ngayGV: new Date(),
                tenThueBao: '',
                maTB: '',
                maGiaoDich: '',
                diaChiLD: '',
                //Kiểu lắp đặt: 14 - Hạ tốc độ duy trì; 15 - Nâng tốc độ
                kieuLapDat: 14,
                hoanThienBtn_Enable: false,
                kichHoatBtn_Enable: false,
                giaoPhieuBtn_Enable: false,
                hoanThienBtn_Visible: false,
                kichHoatBtn_Visible: false,
                giaoPhieuBtn_Visible: false,
                giaoViecBtn_Visible: false,
                tongSo: ''
            },            
            
            common_params: {
                tag: '1+8+4',
                //Trạng thái hoạt động: 1 - Mới; 4 - Đang thi công; 6 - Hoàn thành
                TTHD_Id: 4,
                dsLoaiHD_Id: [],
                dsDichVu_Id: [],
                //Loại thuê bao: 11 - Mega; 58 - Fiber
                quyTrinh_Id: 0,                        
                huongGiao_Id: 0,
                luong_Id: 0,
                HDTB_Id: 0,
                phieu_Id: 0,
                loaiTB_Id: 0,
                controls: [
                    'tsbtnKichHoat',
                    'tsbtnGiaoPhieu',
                    'tsbtnGiaoViec',
                    'tsbtnHoanThienHS'
                ]
            },

            nhanVienTH_Items: [],
            thueBao_grid: {
                items: [],
                selected: {},
                checked: [],
                exportColumns: []
            },
            thaoTac: {
                items: [],
                controls: []
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),

        onCreate(e) {
            this.splitterObj = new Splitter({
                paneSettings: [
                    { size: '50%', min: '20%' },
                    { size: '50%', min: '20%'}
                ],
                orientation: 'Vertical'
            });
            this.splitterObj.appendTo('#vertical_splitter');
        },

        loadCommonsParams(tag) {
            try {
                if (tag !== '') {
                    let tagArr = tag.split('+');
                    if (tagArr.length >= 1)
                        this.common_params.TTHD_Id = Number(tagArr[0]);                    
                    if (tagArr.length >= 2)
                        this.common_params.dsLoaiHD_Id = tagArr[1].split(';');
                    if (tagArr.length >= 3)
                        this.common_params.dsDichVu_Id = tagArr[2].split(';');
                } else {
                    this.common_params.TTHD_Id = 4;
                    this.common_params.dsLoaiHD_Id = ['1'];
                    this.common_params.dsDichVu_Id = ['1'];
                }                
            }
            catch (err) {
                this.common_params.TTHD_Id = 4;
                this.common_params.dsLoaiHD_Id = ['1'];
                this.common_params.dsDichVu_Id = ['9'];
                console.log(err);
            }
        },

        async initForm() {
            if (this.$route.params) {
                if (this.$route.params.tag != null)
                    this.common_params.tag = this.$route.params.tag;
                if (this.$route.params.quyTrinh_Id != null)
                    this.common_params.quyTrinh_Id = this.$route.params.quyTrinh_Id;
                if (this.$route.params.kieuLD_Id != null)
                    this.params.kieuLapDat = this.$route.params.kieuLD_Id;
                if (this.$route.params.TTHD_Id != null)
                    this.common_params.TTHD_Id = this.$route.params.TTHD_Id;
                if (this.$route.params.dsDichVu_Id != null)
                    this.common_params.dsDichVu_Id = this.$route.params.dsDichVu_Id;
                if (this.$route.params.dsLoaiHD_Id != null)
                    this.common_params.dsLoaiHD_Id = this.$route.params.dsLoaiHD_Id;
            }

            this.loadCommonsParams(this.common_params.tag);

            if (this.danhSachDichVuVienThongGetter == null || this.danhSachDichVuVienThongGetter.length === 0) {
                await this.getDanhSachDichVuVienThong();                    
            }
            this.danhSachDichVuVienThongGetter.forEach(item => {
                if (this.common_params.dsDichVu_Id.indexOf(item.DICHVUVT_ID.toString()) !== -1) {
                    this.params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
                }
            });                
            this.params.dichVu_Selected = this.params.dichVu_Options[0].id;

            await this.getDanhSachNhanVien();
            await this.getTocDo();
        },

        showTraCuuHopDongPhuLuc() {
            //UR2.1.008
            this.$refs.dlgTraCuuHopDongPhuLuc.showModal();
        },

        async acceptMaGiaoDich(args) {
            this.params.maGiaoDich = args.ma_gd;
            if (this.params.maGiaoDich !== '') {
                await this.getDSHopDongHuongGiao(this.params.maGiaoDich);
                if (this.thueBao_grid.items.length <= 0) {
                    this.clear();
                    this.params.tongSo = '';
                } else 
                    this.params.tongSo = `Tổng số: ${this.thueBao_grid.items.length} thuê bao`;
            }
        },

        onThueBaoChecked(items) {
            this.thueBao_grid.checked = [];
            if (items.length > 0)
                items.forEach(item => {
                    this.thueBao_grid.checked.push(this.thueBao_grid.items.find(e => e.MA_TB === item));
                });                
        },

        async onThueBaoSelected(args) {            
            this.thueBao_grid.selected = args.data;
            if (this.thueBao_grid.selected != null && Object.keys(this.thueBao_grid.selected).length > 0) {                
                this.common_params.HDTB_ID = this.thueBao_grid.selected.HDTB_ID;
                this.common_params.loaiTB_Id = this.thueBao_grid.selected.LOAITB_ID;
                this.common_params.phieu_Id = this.thueBao_grid.selected.PHIEU_ID;
                this.params.maTB = this.thueBao_grid.selected.MA_TB;
                this.params.tenThueBao = this.thueBao_grid.selected.TEN_TB;
                this.params.maGiaoDich = this.thueBao_grid.selected.MA_GD;
                this.params.diaChiLD = this.thueBao_grid.selected.DIACHI_LD;
                this.params.tocDoCu_Selected = this.thueBao_grid.selected.TOCDO_CU_ID;
                this.params.tocDoMoi_Selected = this.thueBao_grid.selected.TOCDO_ID;
                this.params.dichVu_Selected = this.thueBao_grid.selected.DICHVUVT_ID;
                await this.getDSKieuLapDat(this.common_params.loaiTB_Id);
                await this.getDSNhanVienTheoPhieu();
                this.params.kieuYC_Selected = this.params.kieuLapDat.toString();
                let status = this.thueBao_grid.selected.STATUS;
                this.checkCode(status, this.thueBao_grid.selected);
            }            
        },

        onNhanVienSelected(e) {

        },
        
        async onHienThiGiaoDien(luong_Id) {
            this.params.giaoViecBtn_Visible = false;
            this.params.hoanThienBtn_Visible = false;
            this.params.giaoPhieuBtn_Visible = false;
            this.params.kichHoatBtn_Visible = false;
            this.params.kichHoatBtn_Enable = false;

            await this.getDSThaoTacTheoLuong(luong_Id);
            if (this.thaoTac.items.length > 0) {
                await this.getDSThaoTacControl();
                if (this.thaoTac.controls.length > 0) {
                    this.thaoTac.items.forEach(item => {
                        let controlArr = this.thaoTac.controls.filter(e => e.THAOTAC_ID === item.THAOTAC_ID);                        
                        if (controlArr.length > 0) {                            
                            controlArr.forEach(elemt => {                       
                                let idx = this.common_params.controls.findIndex(e => e === elemt.CONTROL_NAME);                                
                                if (idx !== -1) {
                                    let initEnable = item.ENABLE === 1;
                                    this.displayControl(idx, initEnable);
                                }
                            });
                        }
                    });
                }
            }
        },

        displayControl(id, value) {
            switch(id) {
                case 0: 
                    this.params.kichHoatBtn_Visible = value;
                    break;
                case 1:
                    this.params.giaoPhieuBtn_Visible = value;
                    break;
                case 2:
                    this.params.giaoViecBtn_Visible = value;
                    break;
                case 3:
                    this.params.hoanThienBtn_Visible = value;
                    break;
                default: break;
            }
        },

        onGiaoViec() {
            // this.$toast.error('Chức năng không phát triển!');
        },

        onHoanThien() {
            // this.$toast.error('Chức năng không phát triển!');
        },

        async onLayThongTin(isBtnClick = true) {
            await this.getDSNangHaTocDoDuyTri();
            if (this.thueBao_grid.items.length > 0)
                this.params.tongSo = `Tổng số: ${this.thueBao_grid.items.length} thuê bao`;
            else {
                if (isBtnClick)
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                this.params.kichHoatBtn_Enable = false;
                this.params.hoanThienBtn_Enable = false;
                this.clear();
                this.params.tongSo = '';
            }                       
        },

        /* 1 - Mã giao dịch; 2 - Mã thuê bao */
        async onKeyPress(type) {
            let maTB = '';
            if (type === 1) {
                this.params.maGiaoDich = this.params.maGiaoDich.trim();
                maTB = this.params.maGiaoDich;
            } else {
                this.params.maTB = this.params.maTB.trim();
                maTB = this.params.maTB;
            }
            await this.getDSHopDongHuongGiao(maTB);
            if (this.thueBao_grid.items.length <= 0) {
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                this.clear();
                this.params.tongSo = '';
            } else
                this.params.tongSo = `Tổng số: ${this.thueBao_grid.items.length} thuê bao`;
        },               

        clear() {
            this.common_params.HDTB_Id = 0;
            this.common_params.phieu_Id = 0;
            this.params.maGiaoDich = '';
            this.params.maTB = '';
            this.params.tenThueBao = '';
            this.params.diaChiLD = '';
            this.params.hoanThienBtn_Enable = false;
            this.params.ngayHT_Checked = false;
            this.params.ngayGV_Checked = false;
            this.params.ngayGV = this.params.today;
            this.nhanVienTH_Items = [];
            this.$refs.maTB.focus();
        },

        async onCapNhat() {
            if (this.validateData(3)) {
                let ds = [];
                ds.push({
                    ghiChu: '',
                    hdtbid: null,
                    ngayGiao: moment(this.params.ngayGV).format('DD/MM/YYYY'),
                    nhanVienGiaoId: this.params.nguoiGV_Selected,
                    nhanVienTHId: this.$auth.getNhanVienID(),
                    nhiemVu: "Hoàn thiện hồ sơ",
                    nhienVuId: null,
                    phieuId: this.common_params.phieu_Id
                });
                let rs = await this.updateGiaoViec(ds);
                if (rs)
                    this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!');
                await this.onLayThongTin(false);
            }
        },

        async onXoaHopDong() {           
            if (this.validateData(1)) {
                let rs = true;
                for (let item of this.thueBao_grid.checked) {
                    rs = await this.deleteHopDongKhachHang(item.HDKH_ID);
                    if (!rs) break;
                }

                if (rs)
                    this.$toast.success('Xóa hợp đồng khách hàng thành công!');
                await this.onLayThongTin(false);
            }            
        },

        async onKichHoat() {
            if (this.validateData(2)) {
                let rs = true;
                for (let item of this.thueBao_grid.checked) {
                    rs = await this.updateHoanThienHoSoTDTDuyTri(item.HDTB_ID);
                    if (!rs) break;
                }

                if (rs)
                    this.$toast.success('Kích hoạt thành công!');
                await this.onLayThongTin(false);
            }            
        },

        onXuatFile() {
            if (this.thueBao_grid.items.length <= 0)
                this.$toast.error('Không có dữ liệu để thực hiện xuất file!');            
            else {
                this.thueBao_grid.exportColumns = [];
                let fields = Object.keys(this.thueBao_grid.items[0]);
                fields.forEach(item => {
                    this.thueBao_grid.exportColumns.push({
                        fieldName: item,
                        headerText: item,
                        allowFiltering: true,
                        allowSorting: false,
                        width: 120
                    });
                });
                this.$bvModal.show("modal-xuat-file");
            }
        },

        onGiaoPhieu() {

        },

        /* type: 1 - Xoa HD; 2 - Kich hoat; 3 - Cap nhat */
        validateData(type) {
            if (this.thueBao_grid.items.length <= 0) {
                this.$toast.error('Chưa có dữ liệu thuê bao để cập nhật!');
                return false;
            }           
            if (this.thueBao_grid.checked.length <= 0 && type !== 3) {
                this.$toast.error('Chưa chọn thuê bao để cập nhật!');
                return false;
            }

            if (type === 1) {
                let dsTB = this.thueBao_grid.checked.filter(item => item.TTHD_ID.toString() !== '1');
                if (dsTB.length > 0) {
                    this.$toast.error(`Trạng thái hợp đồng của thuê bao ${dsTB[0].MA_TB} không thỏa mãn điều kiện`);
                    return false;
                }
                dsTB = this.thueBao_grid.checked.filter(item => item.STATUS.toString() !== '' && item.STATUS.toString() !== '0');
                if (dsTB.length > 0) {
                    this.$toast.error(`Thuê bao ${dsTB[0].MA_TB} đã được kích hoạt. Bạn không được xóa!`);
                    return false;
                }                
            }
            else if (type === 2) {
                if (this.thueBao_grid.selected.NGAY_HT_GP === '') {
                    this.$toast.error('Bạn phải cập nhật ngày hoàn thành trước khi kích hoạt!');
                    return false;
                }
            }
            else {  //type === 3
                if (!this.params.ngayGV_Checked || (this.params.ngayGV_Checked && this.params.ngayGV === null)) {
                    this.$toast.error('Chưa chọn ngày giao việc!');
                    return false;
                }
                if (!this.params.ngayHT_Checked || (this.params.ngayHT_Checked && this.params.ngayHT === null)) {
                    this.$toast.error('Chưa chọn ngày hoàn thành!');
                    return false;
                }
                if (this.phaiLam('GIAOVIEC') && this.nhanVienTH_Items.length <= 0) {
                    this.$toast.error('Hãy nhập nhân viên thực hiện!');
                    return false;
                }
                if (this.params.ngayGV > this.params.ngayHT) {
                    this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!');
                    return false;
                }
                if (this.params.ngayHT > this.params.today) {
                    this.$toast.error('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại!');
                    return false;
                }
                if (this.params.ngayGV > this.params.today) {
                    this.$toast.error('Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại!');
                    return false;
                }
            }
            return true;
        },

        checkCode(status, item) {
            if (this.phaiLam('KICHHOAT_TD')) {
                if (item.NGAY_HT_GP !== '') {
                    this.params.ngayHT_Checked = true;
                    this.params.ngayHT = moment(item.NGAY_HT_GP, 'DD/MM/YYYY').toDate();
                    if (status === 5) { //5 - Đồng bộ CM
                        if (this.phaiLam('GIAOPHIEU')) {
                            this.params.kichHoatBtn_Enable = false;
                            this.params.giaoPhieuBtn_Enable = true;
                        } else 
                            this.params.kichHoatBtn_Enable = false;
                    } else {
                        this.params.kichHoatBtn_Enable = true;
                        this.params.giaoPhieuBtn_Enable = false;                        
                    }
                } else {
                    this.params.ngayHT_Checked = false;
                    this.params.ngayHT = this.params.today;
                    this.params.kichHoatBtn_Enable = false;
                    this.params.giaoPhieuBtn_Enable = false;
                }
            } else {
                if (this.phaiLam('GIAOPHIEU')) {
                    if (item.NGAY_HT_GP !== '') {
                        this.params.ngayHT_Checked = true;                        
                        this.params.ngayHT = moment(item.NGAY_HT_GP, 'DD/MM/YYYY').toDate();
                        this.params.kichHoatBtn_Enable = false;
                        this.params.giaoPhieuBtn_Enable = true;
                    } else {
                        this.params.ngayHT_Checked = false;
                        this.params.ngayHT = this.params.today;
                        this.params.kichHoatBtn_Enable = false;
                        this.params.giaoPhieuBtn_Enable = false;
                    }
                } else {
                    if (this.phaiLam('HOANCONG')) {
                        if (item.NGAY_HT_GP !== '') {
                            this.params.ngayHT_Checked = true;                        
                            this.params.ngayHT = moment(item.NGAY_HT_GP, 'DD/MM/YYYY').toDate();
                        } else {
                            this.params.ngayHT_Checked = false;
                            this.params.ngayHT = this.params.today;                            
                        }
                    }
                }
            }
            if (this.phaiLam('HOANTHIENHOSO')) {
                if (item.NGAY_HT_GP !== '') {
                    this.params.ngayHT_Checked = true;
                    this.params.hoanThienBtn_Enable = true;
                    this.params.ngayHT = moment(item.NGAY_HT_GP, 'DD/MM/YYYY').toDate();
                } else {
                    this.params.ngayHT_Checked = false;
                    this.params.ngayHT = this.params.today;
                    this.params.hoanThienBtn_Enable = false;                           
                }
            }
        },

        phaiLam(code) {
            let dr = this.thaoTac.items.filter(e => e.ENABLE === '1' && e.CODE === code);
            return dr.length > 0
        },

        async getDanhSachNhanVien() {
            try {
                this.loading(true);
                this.params.nguoiGV_Options = [];
                let donViId = this.$auth.getDonViID() !== -1 ? this.$auth.getDonViID() : 26;
                let response = await CommonsAPI.getDanhSachNhanVienTheoDonVi(this.axios, donViId);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {    
                    if (response.data.data.length > 0) {
                        response.data.data.forEach(item => {
                            this.params.nguoiGV_Options.push({ id: item.NHANVIEN_ID, text: item.TEN_NV })
                        });
                        this.params.nguoiGV_Selected = this.params.nguoiGV_Options[0].id;
                    }
                }                
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        async getTocDo() {
            try {
                this.loading(true);                
                let response = await CommonsAPI.getTocDoTheoLoaiThueBao(this.axios, '');
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {    
                    if (response.data.data.length > 0) {
                        response.data.data.forEach(item => {
                            this.params.tocDoCu_Options.push({ id: item.TOCDO_ID, text: item.THUONGHIEU });
                            this.params.tocDoMoi_Options.push({ id: item.TOCDO_ID, text: item.THUONGHIEU })
                        });
                        this.params.tocDoCu_Selected = this.params.tocDoCu_Options[0].id;
                        this.params.tocDoMoi_Selected = this.params.tocDoMoi_Options[0].id;
                    }
                }                
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        async getDSThaoTacTheoLuong(luongId) {
            try {
                this.loading(true);
                this.thaoTac.items = [];
                let response = await CommonsAPI.getDSThaoTacTheoLuong(this.axios, luongId);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {    
                    this.thaoTac.items = response.data.data;
                }                
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhSachQuyTrinh() {
            try {
                this.loading(true);
                this.params.quyTrinh_Options = [];
                let apiParams = {
                    dichVuVTId: this.params.dichVu_Selected,
                    kieuYC: 0,
                    loaiHDId: this.common_params.dsLoaiHD_Id[0],
                    TTHDId: this.common_params.TTHD_Id
                };
                let response = await TCAPI.getDanhSachQuyTrinh(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    if (response.data.data.length > 0) {
                        let ds = response.data.data.filter(e => e.QUYTRINH_ID === 143); //143: HATOCDO_DUYTRI
                        if (ds.length > 0) {                            
                            ds.forEach(item => {
                                this.params.quyTrinh_Options.push({ id: item.QUYTRINH_ID, text: item.QUYTRINH })
                            });                        
                            this.params.quyTrinh_Selected = this.params.quyTrinh_Options[0].id;
                        }
                    }
                }
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        async getHuongGiaoTheoQuyTrinh() {            
            try {
                this.loading(true);
                this.common_params.huongGiao_Id = 0;
                this.common_params.luong_Id = 0;
                let apiParams = {
                    quyTrinhId: this.params.quyTrinh_Selected,
                    tthdId: this.common_params.TTHD_Id
                };
                let response = await TCAPI.getHuongGiaoTheoQuyTrinh(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {          
                    this.common_params.huongGiao_Id = response.data.data[0].HUONGGIAO_ID;
                    this.common_params.luong_Id = response.data.data[0].LUONG_ID;
                    return true;
                } else {
                    this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này. Bạn hãy liên hệ Admin kiểm tra lại');
                    return false;
                }
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getDSKieuLapDat(loaiTB_Id) {
            try {
                this.loading(true);
                this.params.kieuYC_Options = [];
                let apiParams = {
                    loaiHDId: 8,    //8: Thay đổi tốc độ ADSL
                    loaiTBId: loaiTB_Id
                };
                let response = await NHAPI.getDSKieuLapDat(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.kieuYC_Options.push({ id: item.KIEULD_ID, text: item.TEN_KIEULD })
                    });                        
                    this.params.kieuYC_Selected = this.params.kieuYC_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        async getDSNangHaTocDoDuyTri() {            
            try {
                this.loading(true);
                this.thueBao_grid.items = [];
                let response = await NHAPI.getDSNangHaTocDoDuyTri(this.axios, this.params.kieuLapDat);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.thueBao_grid.items = response.data.data;
                }                
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        async getDSHopDongHuongGiao(maTB) {            
            try {
                this.loading(true);
                this.thueBao_grid.items = [];
                let apiParams = {
                    dichVuVTId: this.params.dichVu_Selected,
                    huongGiaoId: this.common_params.huongGiao_Id,
                    loai: 0,
                    loaiHDId: 8, //8 - Thay đổi tốc độ ADSL
                    maTB: maTB,
                    tthdId: this.common_params.TTHD_Id
                };
                let response = await NHAPI.getDSHopDongHuongGiao(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.thueBao_grid.items = response.data.data;
                }                
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        async getDSNhanVienTheoPhieu() {            
            try {
                this.loading(true);
                this.nhanVienTH_Items = [];
                let apiParams = {
                    donViQLId: null,
                    kieuGiao: 0,
                    loaiNVId: null,
                    nhanVienGiaoId: this.$auth.getNhanVienID() !== -1 ? this.$auth.getNhanVienID() : 0,
                    phieuId: this.thueBao_grid.selected.PHIEU_ID
                };
                let response = await NHAPI.getDSNhanVienTheoPhieu(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
                    this.nhanVienTH_Items = response.data.data;
                }                
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        },

        /* Return: true/false */
        async deleteHopDongKhachHang(hopDongKHId) {
            try {
                this.loading(true);                
                let apiParams = {
                    hopDongKHId: hopDongKHId,
                    loaiHdId: 8     //Thay doi toc do ADSL
                };
                let response = await TCAPI.deleteHopDongKhachHang(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000') {          
                    return true;
                } else {
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        /* Return: true/false */
        async updateHoanThienHoSoTDTDuyTri(id) {
            try {
                this.loading(true);
                let response = await TCAPI.updateHoanThienHoSoTDTDuyTri(this.axios, id);
                if (response.data.error_code === 'BSS-00000000') {          
                    return true;
                } else {
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        /* Return: true/false */
        async updateGiaoViec(ds) {
            try {
                this.loading(true);
                let apiBody = {
                    dsGiaoViecNV: ds,
                    quyTrinhId: this.params.quyTrinh_Selected,
                    tthdid: this.common_params.TTHD_Id
                };
                let response = await TCAPI.updateGiaoViec(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {          
                    return true;
                } else {
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getDSThaoTacControl() {
            try {
                this.loading(true);
                this.thaoTac.controls = [];
                let response = await TCAPI.getDSThaoTacControl(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {    
                    this.thaoTac.controls = response.data.data;
                }                
            }
            catch (error) {
                console.log(error);                
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.dichVu_Selected': async function(newVal, oldVal) {
            await this.getDanhSachQuyTrinh();
        },
        'params.quyTrinh_Selected': async function(newVal, oldVal) {
            if (newVal !== null) {
                let rs = await this.getHuongGiaoTheoQuyTrinh();
                if (rs)
                    await this.onHienThiGiaoDien(this.common_params.luong_Id);
                else
                    this.common_params.huongGiao_Id = -1;                
                await this.onLayThongTin(false);
            }
        }
    }
}
</script>