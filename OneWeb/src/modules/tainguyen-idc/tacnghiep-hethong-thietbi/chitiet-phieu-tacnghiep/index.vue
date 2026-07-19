<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import ModalChiTietThietBiTacNghiep from '../chitiet-thietbi-tacnghiep/index.vue';
export default {
    name: 'ModalChiTietPhieuTacNghiep',
    props: { modalID: null },
    components: {
        ModalChiTietThietBiTacNghiep
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
            this.$refs.ModalChiTietPhieuTacNghiep.hide(this.modalID)
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
            this.$refs.ModalChiTietPhieuTacNghiep.show();
        },

        resetCurrentItem: function () {
            this.currentItem = {
                MA_CONG_VIEC: '',
                DON_VI_THUC_HIEN: '',
                NGUOI_THUC_HIEN: '',
                LOAI_CONG_VIEC: '',
                NGUOI_PHOI_HOP: '',
                HANG_MUC_TRIEN_KHAI: '',
                THOI_DIEM_YEU_CAU: '',
                GIO_YEU_CAU: '',
                CAN_CU_PHIEU_YEU_CAU: '',
                TRANG_THAI_THUC_HIEN: '',
                MA_CONG_VIEC_CHA: '',
                THOI_DIEM_HOAN_THANH: ''
            };
        },

        loadInitialData: async function () {
            this.loadTrangThaiThucHienList();
            this.loadIDCList();
            this.loadMangThietBiList();
            this.loadHeThongList();
            this.loadLoaiThietBiList();
            this.loadThietBiIDCData();
        },

        loadTrangThaiThucHienList: function () {
            this.trangThaiThucHienList = [
                { ID: 'CHO_THUC_HIEN', TEN: 'Chờ thực hiện' },
                { ID: 'DANG_THUC_HIEN', TEN: 'Đang thực hiện' },
                { ID: 'DA_HOAN_THANH', TEN: 'Đã hoàn thành' },
                { ID: 'TAM_DUNG', TEN: 'Tạm dừng' },
                { ID: 'HUY_BO', TEN: 'Hủy bỏ' }
            ];
        },

        loadIDCList: function () {
            this.idcList = [
                { ID: 'IDC_NTL', TEN: 'IDC Nam Thăng Long' },
                { ID: 'IDC_HN_01', TEN: 'IDC Hà Nội 01' },
                { ID: 'IDC_HCM_01', TEN: 'IDC TP.HCM 01' },
                { ID: 'IDC_DN_01', TEN: 'IDC Đà Nẵng 01' }
            ];
        },

        loadMangThietBiList: function () {
            this.mangThietBiList = [
                { ID: 'HA_TANG_ME', TEN: 'Hạ tầng M&E' },
                { ID: 'MANG', TEN: 'Mạng' },
                { ID: 'PHU_TRO', TEN: 'Phụ trợ' },
                { ID: 'SERVER', TEN: 'Server' }
            ];
        },

        loadHeThongList: function () {
            this.heThongList = [
                { ID: 'HT_IDC_NTL', TEN: 'Hệ thống IDC NTL' },
                { ID: 'HT_DIEN', TEN: 'Hệ thống điện' },
                { ID: 'HT_LANH', TEN: 'Hệ thống lạnh' },
                { ID: 'HT_PCCC', TEN: 'Hệ thống PCCC' }
            ];
        },

        loadLoaiThietBiList: function () {
            this.loaiThietBiList = [
                { ID: 'UPS', TEN: 'UPS' },
                { ID: 'DIEU_HOA', TEN: 'Điều hòa' },
                { ID: 'SWITCH', TEN: 'Switch' },
                { ID: 'SERVER', TEN: 'Server' },
                { ID: 'FIREWALL', TEN: 'Firewall' }
            ];
        },

        loadThietBiIDCData: function () {
            // Fake data for Thiet bi IDC list
            this.thietBiIDCData = [
                {
                    ID_THIET_BI: '12345',
                    TEN_THIET_BI: 'UPS APC 20kVA',
                    SERIAL: 'APC20231001',
                    CONG_SUAT_TKE: '20kVA',
                    TOA_NHA: 'A1',
                    MAT_SAN: 'T1',
                    PHONG_ZONE: 'P001',
                    RACK: 'R01',
                    U: '42U',
                    TRANG_THAI_VAN_HANH: 'Hoạt động'
                },
                {
                    ID_THIET_BI: '12346',
                    TEN_THIET_BI: 'Switch Cisco 48 port',
                    SERIAL: 'CISCO48231002',
                    CONG_SUAT_TKE: '200W',
                    TOA_NHA: 'A1',
                    MAT_SAN: 'T1',
                    PHONG_ZONE: 'P002',
                    RACK: 'R02',
                    U: '1U',
                    TRANG_THAI_VAN_HANH: 'Hoạt động'
                },
            ];
        },

        // Simple action handlers - just console log
        onSave: function () {
            console.log('Save clicked');
        },

        onChuyenXuLy: function () {
            console.log('Chuyển xử lý clicked');
        },

        onTraPhieu: function () {
            console.log('Trả phiếu clicked');
        },

        onHoanThanh: function () {
            console.log('Hoàn thành clicked');
        },

        actionColumn(parent) {
            return function () {
                return {
                    template: {
                        template: `<div class="action-buttons gap-1">
                        <button class="btn btn-sm btn-outline-primary" title="Ghi thông tin lắp đặt" @click="onEdit()">
                          <i class="fa fa-edit"></i>
                        </button>
                      </div>`,
                        methods: {
                            onEdit: function () {
                                parent.$refs.ModalChiTietThietBiTacNghiep.show();
                            }
                        }
                    },

                };
            };
        }
    },
    data: function () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            currentMode: 'VIEW', // VIEW, ADD, EDIT
            item: null,
            currentItem: {
                MA_CONG_VIEC: '',
                DON_VI_THUC_HIEN: '',
                NGUOI_THUC_HIEN: '',
                LOAI_CONG_VIEC: '',
                NGUOI_PHOI_HOP: '',
                HANG_MUC_TRIEN_KHAI: '',
                THOI_DIEM_YEU_CAU: '',
                GIO_YEU_CAU: '',
                CAN_CU_PHIEU_YEU_CAU: '',
                TRANG_THAI_THUC_HIEN: '',
                MA_CONG_VIEC_CHA: '',
                THOI_DIEM_HOAN_THANH: ''
            },

            // Dropdown lists
            trangThaiThucHienList: [],
            idcList: [],
            mangThietBiList: [],
            heThongList: [],
            loaiThietBiList: [],

            // Filter data for device list
            filterData: {
                taiIDC: '',
                mangThietBi: '',
                heThong: '',
                loaiThietBi: ''
            },

            // Device list data
            thietBiIDCData: [],

            // Form controls
            controls: {
                txtMaCongViec: { enabled: false, invalid: false },
                txtDonViThucHien: { enabled: false, invalid: false },
                txtNguoiThucHien: { enabled: false, invalid: false },
                txtLoaiCongViec: { enabled: false, invalid: false },
                txtNguoiPhoiHop: { enabled: false, invalid: false },
                txtHangMucTrienKhai: { enabled: false, invalid: false },
                txtThoiDiemYeuCau: { enabled: false, invalid: false },
                txtGioYeuCau: { enabled: false, invalid: false },
                txtCanCuPhieuYeuCau: { enabled: false, invalid: false },
                selectTrangThaiThucHien: { enabled: false, invalid: false },
                txtMaCongViecCha: { enabled: false, invalid: false },
                txtThoiDiemHoanThanh: { enabled: false, invalid: false }
            }
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
