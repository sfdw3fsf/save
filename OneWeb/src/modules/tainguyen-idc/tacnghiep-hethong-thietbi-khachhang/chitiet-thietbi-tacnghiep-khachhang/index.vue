<template src="./index.template.html"></template>
<script>
import Vue from 'vue'

export default {
    name: 'ModalChiThietThietBiTacNghiepKhachHang',
    props: { modalID: null },
    components: {
    },
    created: function () {
        this.loadInitialData();
    },
    validations: {

    },
    watch: {

    },
    computed: {

    },
    methods: {
        closeModal: function () {
            this.$refs.ModalChiThietThietBiTacNghiepKhachHang.hide(this.modalID)
        },
        show: function (item, mode) {
            if (item) {
                this.currentItem = { ...item };
                this.currentMode = mode;
            } else {
                this.resetCurrentItem();
                this.currentMode = mode;
            }
            this.item = item;
            this.$refs.ModalChiThietThietBiTacNghiepKhachHang.show();
        },

        loadInitialData: async function () {

        },

        resetCurrentItem: function () {
            this.currentItem = {
                id: 0,
                phamViIdcId: 0,
                mangThietBiId: 0,
                loaiThietBiId: 0,
                loaiKhachHangId: 0,
                heThongId: 0,
                donViQuanLy: '',
                nguoiQuanLy: '',
                lapDatTaiIdcId: 0,
                tenThietBi: '',
                toaNhaId: 0,
                matSanId: 0,
                dongSanPham: '',
                phongZoneId: 0,
                rack: '',
                cauHinhChiTiet: '',
                uTrenId: 0,
                uDuoiId: 0,
                serialNumber: '',
                thoiDiemLapDat: '',
                canCuLapDatId: 0,
                tagNumber: '',
                maTheTaiSan: '',
                trangThaiTaiSanId: 0,
                congSuatThietKe: 0,
                trangThaiVanHanhId: 0,
                icons: '',
                soCongNguonDauVao: 0,
                hangSanXuatId: 0,
                namSanXuat: new Date().getFullYear(),
                soCongNguonDauRa: 0,
                ghiChu: ''
            };
        },

        onSave: function () {
            console.log('Save clicked');
        },

        showModalChonRack: function () {

        }
    },
    data: function () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            currentMode: 'VIEW', // VIEW, ADD, EDIT
            item: null,
            currentItem: {
                id: 0,
                phamViIdcId: 0,
                mangThietBiId: 0,
                loaiThietBiId: 0,
                loaiKhachHangId: 0,
                heThongId: 0,
                donViQuanLy: '',
                nguoiQuanLy: '',
                lapDatTaiIdcId: 0,
                tenThietBi: '',
                toaNhaId: 0,
                matSanId: 0,
                dongSanPham: '',
                phongZoneId: 0,
                rack: '',
                cauHinhChiTiet: '',
                uTrenId: 0,
                uDuoiId: 0,
                serialNumber: '',
                thoiDiemLapDat: '',
                canCuLapDatId: 0,
                tagNumber: '',
                maTheTaiSan: '',
                trangThaiTaiSanId: 0,
                congSuatThietKe: 0,
                trangThaiVanHanhId: 0,
                icons: '',
                soCongNguonDauVao: 0,
                hangSanXuatId: 0,
                namSanXuat: new Date().getFullYear(),
                soCongNguonDauRa: 0,
                ghiChu: ''
            },
            controls: {
                selectPhamViIdc: { enabled: false, invalid: false },
                selectMangThietBi: { enabled: false, invalid: false },
                selectLoaiThietBi: { enabled: false, invalid: false },
                selectLoaiKhachHang: { enabled: false, invalid: false },
                selectHeThong: { enabled: false, invalid: false },
                txtDonViQuanLy: { enabled: false, invalid: false },
                txtNguoiQuanLy: { enabled: false, invalid: false },
                selectLapDatTaiIdc: { enabled: false, invalid: false },
                txtTenThietBi: { enabled: false, invalid: false },
                selectToaNha: { enabled: false, invalid: false },
                selectMatSan: { enabled: false, invalid: false },
                txtDongSanPham: { enabled: false, invalid: false },
                selectPhongZone: { enabled: false, invalid: false },
                txtRack: { enabled: false, invalid: false },
                txtCauHinhChiTiet: { enabled: false, invalid: false },
                selectUTren: { enabled: false, invalid: false },
                selectUDuoi: { enabled: false, invalid: false },
                txtSerialNumber: { enabled: false, invalid: false },
                txtThoiDiemLapDat: { enabled: false, invalid: false },
                selectCanCuLapDat: { enabled: false, invalid: false },
                txtTagNumber: { enabled: false, invalid: false },
                txtMaTheTaiSan: { enabled: false, invalid: false },
                selectTrangThaiTaiSan: { enabled: false, invalid: false },
                txtCongSuatThietKe: { enabled: false, invalid: false },
                selectTrangThaiVanHanh: { enabled: false, invalid: false },
                txtIcons: { enabled: false, invalid: false },
                txtSoCongNguonDauVao: { enabled: false, invalid: false },
                selectHangSanXuat: { enabled: false, invalid: false },
                txtNamSanXuat: { enabled: false, invalid: false },
                txtSoCongNguonDauRa: { enabled: false, invalid: false },
                txtGhiChu: { enabled: false, invalid: false }
            },
            phamViIdcList: [],
            mangThietBiList: [],
            loaiThietBiList: [],
            loaiKhachHangList: [],
            heThongList: [],
            lapDatTaiIdcList: [],
            toaNhaList: [],
            matSanList: [],
            phongZoneList: [],
            uTrenList: [],
            uDuoiList: [],
            canCuLapDatList: [],
            trangThaiTaiSanList: [],
            trangThaiVanHanhList: [],
            hangSanXuatList: [],
        }
    },
    mounted: function () { }
}
</script>
<style>
.popup-box {
    max-height: 90vh !important;
    overflow-y: auto;
}

.info-row .value .d-flex input:first-child {
    margin-right: 10px;
}

.text-muted {
    font-size: 0.875em;
    color: #6c757d !important;
    margin-top: 2px;
}
</style>
