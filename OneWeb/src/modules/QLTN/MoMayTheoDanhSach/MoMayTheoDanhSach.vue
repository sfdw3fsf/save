<template src="./MoMayTheoDanhSach.html"></template>
<style scoped src="./style.scss"></style>
<script>
import Calculator from '@/components/Calculator.vue'
import breadcrumb from '@/components/breadcrumb'
import KyCuoc from '@/components/KyCuoc'
import PopupDocFileKhoaMoTB from '../components/PopupDocFileKhoaMo/PopupDocFileKhoaMoTB.vue'
import PopupChiTietNoTB from './components/PopupChiTietNoTB.vue'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import CommonsAPI from '../api/Commons.js'
import MMAPI from '../api/MoMayTheoDSAPI.js'
import XuatFile from "../../CSKH/utils/XuatFile.vue"
import { mapActions, mapGetters } from 'vuex'
import { DichVuVienThong } from '@/utils/Enum'

export default {
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        Calculator,
        PopupDocFileKhoaMoTB,
        appModalXuatDuLieu: XuatFile,
        PopupChiTietNoTB
    },
    name: 'MoMayTheoDanhSach',

    async created () {
        EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);
        if (Object.keys(this.kyHoaDonHienTaiGetter).length <= 0)
            await this.getKyHoaDonHienTai();
        if (Object.keys(this.kyHoaDonHienTaiGetter).length > 0)
            this.thongtinKyHoaDon.kyHoaDonSelected = this.kyHoaDonHienTaiGetter.KYCUOC;

        if (this.danhSachDichVuVienThongGetter == null || this.danhSachDichVuVienThongGetter.length === 0)
            await this.getDanhSachDichVuVienThong();

        this.danhSachDichVuVienThongGetter.forEach(item => {
            this.params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
        });
        if (this.params.dichVu_Options.length > 0) {
            this.params.dichVu_Options.sort(this.compare);
            this.params.dichVu_Selected = this.params.dichVu_Options[0].id.toString();
        }

        await this.getDonViThanhToan();
        if (this.params.donVi_Options.length > 0) {
            let idx = this.params.donVi_Options.findIndex(e => e.id === 0); //id = 0: Tất cả
            if (idx !== -1) {
                const temp = this.params.donVi_Options[idx];
                this.params.donVi_Options.slice(idx, 1);
                this.params.donVi_Options.sort(this.compare);
                this.params.donVi_Options.unshift(temp);
            } else
                this.params.donVi_Options.sort(this.compare);
            this.params.donVi_Selected = this.params.donVi_Options[0].id;
        }

        await this.getLanMoTrongNgay(this.params.dichVu_Selected);
        this.setButton(-1);
    },

    destroyed () {
        EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    },

    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter', 'danhsachDonViGetter', 'kyHoaDonHienTaiGetter']),
    },

    data () {
        return {
            thongtinKyHoaDon: {
                kyHoaDonSelected : moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD').toString(),
                chuKySelected: null
            },
            params: {
                dichVu_Options: [],
                dichVu_Selected: null,
                loaiHinh_Options: [],
                loaiHinh_Selected: [],
                loaiHinh_Checked: false,
                donVi_Options: [],
                donVi_Selected: null,
                donVi_Checked: false,
                tienMo: 0,
                tienMoCalculator: false,
                kieuMo_Options: [
                    { id: 0, text: 'Mở theo từng số máy' },
                    { id: 1, text: 'Mở theo mã thanh toán' }
                ],
                kieuMo_Selected: 0,
                lanMo: 0,
                kieuNo_Options: [
                    { id: 0, text: 'Nợ đầu kỳ' },
                    { id: 5, text: 'Nợ phát sinh' },
                    { id: 1, text: 'Tổng nợ' }
                ],
                kieuNo_Selected: 1,
                ngayMo: new Date(),
                today: new Date(),
                timKiemBtn_Enable: false,
                taoDSBtn_Enable: false,
                xuatExcelBtn_Enable: false
            },
            display_params: {
                processTime: '00:00:00',
                number: 0,
                btnTitle: 'Đồng bộ'
            },
            TBMo_grid: {
                items: [],
                checked: []
            },
            TBKhongMo_grid: {
                items: [],
                checked: []
            },
            HDKhacKMM_Items: [],
            maTT: ''
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong', 'getDanhSachDonVi', 'getKyHoaDonHienTai']),

        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01'
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no
        },

        openDialog() {
            this.$refs.dlgDocFileKhoaMo.openDialog();
        },

        async onReadFile(items) {
            this.clear();
            if (items.length > 0) {
                await this.getDSMoMayTheoFile();
                if (this.TBMo_grid.items.length <= 0)
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                else
                    this.setButton(1);
            }
        },

        async onSearch() {
            let beginTime = new Date();
            this.TBKhongMo_grid.items = [];
            await this.getDSMoMay();

            let timeSpan = new Date() - beginTime;
            let seconds = timeSpan / 1000;
            let hours = parseInt( seconds / 3600 );
            seconds = seconds % 3600;
            let minutes = parseInt( seconds / 60 );
            seconds = Math.round(seconds % 60);
            this.display_params.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
            this.display_params.number = this.TBMo_grid.items.length;

            if (this.TBMo_grid.items.length <= 0) {
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                this.clear();
            }
            else
                this.setButton(1);
        },

        clear() {
            this.TBMo_grid.items = [];
            this.TBKhongMo_grid.items = [];
            this.display_params.processTime = '00:00:00';
            this.display_params.number = 0;
        },

        onFileExport() {
            if (this.TBMo_grid.items.length <= 0)
                this.$toast.error('Chưa có dữ liệu để xuất file!');
            else
                this.$bvModal.show("modal-xuat-file");
        },

        onLanMoInput(e) {
            this.params.lanMo = Math.max(0, parseInt(e.target.value) || 0);
        },

        onDSMoChecked(items) {
            this.TBMo_grid.checked = items;
        },

        onDSMoDoubleClicked(args) {
            this.maTT = args.rowData.MA_TT;
            this.$refs.dlgChiTietNoTB.openDialog();
        },

        onDSMoQueryCellInfo(args) {
            if (args.column.field === 'MA_TT') {
                if (args.data.TRANGTHAI === 1) {
                    args.cell.classList.add('hd-khac');
                    args.cell.setAttribute('title', 'Thuê bao đang lập hợp đồng/phụ lục khác. Hãy tra cứu chi tiết thi công để biết thêm chi tiết');
                } else {
                    args.cell.classList.remove('hd-khac');
                }
            }
        },

        onDSKhongMoChecked(items) {
            this.TBKhongMo_grid.checked = items;
        },

        onDSKhongMoDoubleClicked(args) {
            this.maTT = args.rowData.MA_TT;
            this.$refs.dlgChiTietNoTB.openDialog();
        },

        onRight() {
            if (this.TBMo_grid.items.length > 0 && this.TBMo_grid.checked.length > 0) {
                let arr = [];
                this.TBMo_grid.checked.map((val) => {
                    let idx = this.TBMo_grid.items.findIndex((v) => v.MA_TB === val);
                    if (idx >= 0) {
                        arr.push(this.TBMo_grid.items[idx]);
                        this.TBMo_grid.items = this.TBMo_grid.items.filter((v) => v.MA_TB !== val);
                    }
                });
                this.TBKhongMo_grid.items = this.TBKhongMo_grid.items.concat(arr);
                this.TBMo_grid.checked = [];
            }
        },

        onDoubleRight() {
            if (this.TBMo_grid.items.length > 0) {
                this.TBKhongMo_grid.items = this.TBKhongMo_grid.items.concat(this.TBMo_grid.items);
                this.TBMo_grid.items = [];
                this.TBMo_grid.checked = [];
            }
        },

        onLeft() {
            if (this.TBKhongMo_grid.items.length > 0 && this.TBKhongMo_grid.checked.length > 0) {
                let arr = [];
                this.TBKhongMo_grid.checked.map((val) => {
                    let idx = this.TBKhongMo_grid.items.findIndex((v) => v.MA_TB === val);
                    if (idx >= 0) {
                        arr.push(this.TBKhongMo_grid.items[idx]);
                        this.TBKhongMo_grid.items = this.TBKhongMo_grid.items.filter((v) => v.MA_TB !== val);
                    }
                });
                this.TBMo_grid.items = this.TBMo_grid.items.concat(arr);
                this.TBKhongMo_grid.checked = [];
            }
        },

        onDoubleLeft() {
            if (this.TBKhongMo_grid.items.length > 0) {
                this.TBMo_grid.items = this.TBMo_grid.items.concat(this.TBKhongMo_grid.items);
                this.TBKhongMo_grid.items = [];
                this.TBKhongMo_grid.checked = [];
            }
        },

        compare(a, b) {
            if (a.text < b.text)
                return -1;
            if (a.text > b.text)
                return 1;
            return 0;
        },

        setButton(kieu) {
            this.params.timKiemBtn_Enable = false;
            this.params.taoDSBtn_Enable = false;
            this.params.xuatExcelBtn_Enable = false;
            if (kieu === -1)
                this.params.timKiemBtn_Enable = true;
            else if (kieu === 0) {
                this.params.timKiemBtn_Enable = true;
                this.clear();
            } else { //kieu === 1
                this.params.timKiemBtn_Enable = true;
                this.params.taoDSBtn_Enable = true;
                this.params.xuatExcelBtn_Enable = true;
            }
        },

        async onCreate() {
            if (this.TBMo_grid.items.length <= 0)
                this.$toast.error('Không có dữ liệu để chuyển danh sách mở máy sang hợp đồng thay đổi dịch vụ!');
            else {
                try {
                    await this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn chuyển danh sách mở máy sang hợp đồng thay đổi dịch vụ?', {
                    "title": 'Thông báo',
                    "size": 'lg',
                    "buttonSize": 'lg',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Hủy bỏ',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18","text-center"],
                    })
                    .then(async(value) => {
                        if (value){
                            //Lọc danh sách không mở khóa:
                            await this.onLocDanhSachTB();

                            //Kiểm tra thuê bao có đang lập hợp đồng khác:
                            let rs = await this.onKiemTraTB();
                            if (!rs) return;

                            //Chuyển sang hợp đồng:
                            await this.updateChuyenDSKhoaMoSangHDKM();
                            await this.getLanMoTrongNgay(this.params.dichVu_Selected);
                            this.setButton(0);
                        }
                    })
                    .catch(err => {
                        // An error occurred
                        console.log(err);
                    })
                } catch (error) {
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!");
                }
            }
        },

        async onLocDanhSachTB() {
            if (this.TBKhongMo_grid.items.length > 0) {
                let ds = '';
                for (let item of this.TBKhongMo_grid.items) {
                    ds += item.MA_TB + ',';
                }
                if (ds !== '')
                    await this.updateLocDanhSach(ds.substring(0, ds.length - 1));
            }
        },

        async onKiemTraTB() {
            if (this.TBMo_grid.items.length > 0) {
                await this.getDSLapHopDongKhacKMM();
                if (this.HDKhacKMM_Items.length > 0) {
                    let isHDKMMKhac = false;
                    this.TBMo_grid.items.forEach(item => {
                        if (this.HDKhacKMM_Items.findIndex(e => e.THUEBAO_ID === item.THUEBAO_ID) !== -1) {
                            item.TRANGTHAI = 1;
                            isHDKMMKhac = true;
                        }
                    });
                    if (isHDKMMKhac) {
                        this.$toast.error("Có thuê bao đang lập hợp đồng/phụ lục khác!");
                        return false;
                    }
                }
            }
            return true;
        },

        onSync() {
            this.$router.push({
                name: 'TiepNhanThongTinKhoaMoNoCuoc',
                params: {
                    tag: '1+7+1;4;8;9;10;11;12',
                    dichvuID: this.params.dichVu_Selected,
                    kieuYCID: 0,
                    tthdID: 1,
                    quytrinhId: this.params.dichVu_Selected === DichVuVienThong.CO_DINH ? 10113 : this.params.dichVu_Selected === DichVuVienThong.ADSL ? 10116 : this.params.dichVu_Selected === DichVuVienThong.IMS ? 10115 : null
                }
            });
        },

        async getLoaiHinhTB(id) {
            try {
                this.loading(true);
                this.params.loaiHinh_Options = [];
                let response = await CommonsAPI.getDanhSachLoaiHinhThueBao(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.loaiHinh_Options.push({ id: item.LOAITB_ID, text: item.TEN_LOAIHINH });
                    });
                    this.params.loaiHinh_Options.sort(this.compare);
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDonViThanhToan() {
            try {
                this.loading(true);
                this.params.donVi_Options = [];
                let pvId = this.$auth.getPhanVungID() !== -1 ? this.$auth.getPhanVungID() : 26;
                let response = await MMAPI.getDonViThanhToan(this.axios, pvId);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.donVi_Options.push({ id: item.DONVI_ID, text: item.TEN_DV });
                    });
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getLanMoTrongNgay(id) {
            try {
                this.loading(true);
                this.params.lanMo = 0;
                let apiParams = {
                    pDichVuVTId: id,
                    pNgayMo: moment(new Date()).format('DD/MM/YYYY')
                }
                let response = await MMAPI.getLanMoTrongNgay(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.params.lanMo = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSMoMay() {
            try {
                this.loading(true);
                this.TBMo_grid.items = [];
                let apiParams = {
                    pKyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    pKieuNhac: this.params.kieuNo_Selected,
                    pKieuMo: this.params.kieuMo_Selected,
                    pDichVuVTId: this.params.dichVu_Selected,
                    pLoaiTBId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : null,
                    pDonViId: this.params.donVi_Checked ? this.params.donVi_Selected : 0,
                    pTienGHM: this.params.tienMo,
                    pLanMo: this.params.lanMo,
                    pMayCN: 'chinhtn',
                    pageNo: 0,
                    pageSize: 100000,
                    totalRow: 0
                }
                let response = await MMAPI.getDSMoMay(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data.data !== undefined) {
                    this.TBMo_grid.items = response.data.data.data;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSMoMayTheoFile() {
            try {
                this.loading(true);
                this.TBMo_grid.items = [];
                let apiParams = {
                    pKyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    pDichVuVTId: this.params.dichVu_Selected,
                    pLoaiTBId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : null,
                    pMayCN: this.$auth.getUserName()
                }
                let response = await MMAPI.getDSMoMayTheoFile(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.TBMo_grid.items = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSLapHopDongKhacKMM() {
            try {
                this.loading(true);
                this.HDKhacKMM_Items = [];
                let apiParams = {
                    pKieuYC: 2,
                    pDichVuVTId: this.params.dichVu_Selected,
                    pageNo: 0,
                    pageSize: 100000,
                    totalRow: 0
                }
                let response = await MMAPI.getDSLapHopDongKhacKMM(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data.data !== undefined) {
                    this.HDKhacKMM_Items = response.data.data.data
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        /* dsMaTB: String (maTB,maTB,maTB) */
        async updateLocDanhSach(dsMaTB) {
            try {
                this.loading(true);
                let apiBody = {
                    pMaND: this.$auth.getUserName() !== -1 ? this.$auth.getUserName() : 'admin',
                    pDSMaTB: dsMaTB
                };
                let response = await MMAPI.updateLocDanhSach(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data === true)
                    return true;
                else return false;
            }
            catch (error) {
                this.$toast.error(error.response.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async updateChuyenDSKhoaMoSangHDKM() {
            try {
                this.loading(true);
                let apiBody = {
                    pDichVuVTId: this.params.dichVu_Selected,
                    pLoaiTBTKId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : null,
                    pNhanVienId: this.$auth.getNhanVienID() !== -1 ? this.$auth.getNhanVienID() : 452,
                    pDonViId: this.$auth.getDonViID() !== -1 ? this.$auth.getDonViID() : 900,
                    pKieuYC: 0
                };
                let response = await MMAPI.updateChuyenDSKhoaMoSangHDKM(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data === '1') {
                    this.$toast.success('Chuyển thành công ' + this.TBMo_grid.items.length + ' thuê bao sang hợp đồng thay đổi dịch vụ');
                    return true;
                } else {
                    this.$toast.error(response.data.message_detail);
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
    },

    watch: {
        'params.dichVu_Selected': async function(newVal, oldVal) {
            if (Number(newVal) === DichVuVienThong.DI_DONG || Number(newVal) === DichVuVienThong.GPHONE)
                this.display_params.btnTitle = 'Hoàn thành';
            else
                this.display_params.btnTitle = 'Đồng bộ';

            await this.getLanMoTrongNgay(newVal);
            await this.getLoaiHinhTB(newVal);
            if (this.params.loaiHinh_Options.length > 0) {
                this.params.loaiHinh_Options.sort(this.compare);
                this.params.loaiHinh_Selected = this.params.loaiHinh_Options[0].id;
            }
        }
    }
}
</script>
