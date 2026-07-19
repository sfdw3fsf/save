<template src="./index.template.html"></template>
<style scoped src="./style.scss">

</style>
<script>
import API from '../api/CongNoAPI.js'
import CommonAPI from '../api/Commons.js'
import { mapActions, mapGetters } from "vuex"
import PopupSapXep from '../components/PopupSapXep/PopupSapXep.vue'
import PopupXuatDuLieuBC from '../TaoDuLieuXacNhanCongNo/PopupXuatDuLieuBC.vue'
// import Vue from "vue";
// import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
// Vue.use(DialogPlugin);

export default {
    components: { PopupSapXep, appModalXuatDuLieu: PopupXuatDuLieuBC },

    async created() {
        await this.initComboboxData();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhSachLanTaoCongNoGetter'
        ]),
        ...mapGetters("GanNhomCongNo", [
            "listDonViGetter",
            "listHTTTGetter",
            "listLoaiKHGetter",
            "listPLKHGetter",
            "listNVQuanLyGetter",
            "listNVThuCuocGetter"
        ])
    },

    data() {
        return {
            params: {
                lanTao_Options: [],
                lanTao_Selected: null,
                donVi_Checked: false,
                donVi_Options: [],
                donVi_Selected: -1,
                maTT_Checked: false,
                maTT: '',
                hinhThucTT_Checked: false,
                hinhThucTT_Options: [],
                hinhThucTT_Selected: null,
                nhomKH_Checked: false,
                nhomKH_Options: [
                    { id: -1, text: 'Tất cả' },
                    { id: 0, text: 'Cá nhân' },
                    { id: 1, text: 'Doanh nghiệp' }
                ],
                nhomKH_Selected: null,
                nhanVienQL_Checked: false,
                nhanVienQL_Options: [],
                nhanVienQL_Selected: -1,
                NVTC_Checked: false,
                NVTC_Options: [],
                NVTC_Selected: null,
                phanLoaiKH_Checked: false,
                phanLoaiKH_Options: [],
                phanLoaiKH_Selected: null,
                loaiKH_Checked: false,
                loaiKH_Options: [],
                loaiKH_Selected: null,
                soLuongTao: ''
            },
            congNo_grid: {
                items: [],
                key: 1,
                pageInfo: {
                    page: 0,
                    maxSize: 10,
                    totalElement: 0,
                    totalPages: 0,
                    currentRow: 0,
                    sort: null,
                    propertiesSort: null
                }
            },
            rowsSelected: [],
            exportParams: {},
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhSachLanTaoCongNo'
        ]),
        ...mapActions("GanNhomCongNo", [
            "getDonVi",
            "getHTTT",
            "getPhanLoaiKH",
            "getDanhSachKHLon",
            "getNVQuanLy",
            "getNVThuCuoc",
            "getDanhSachLoaiKH"
        ]),

        async onSearch() {
            this.resetPaging();
            await this.getDanhSachCongNoBaoCao();
            if (this.congNo_grid.items.length <= 0) {
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                this.params.soLuongTao = 0;
            }
        },

        async onFileExport() {
            if (this.congNo_grid.items.length <= 0)
                this.$toast.error('Chưa có dữ liệu để xuất file!');
            else
                this.$refs.dlgXuatDuLieuBC.openDialog();
        },

        onSort() {
            if (this.congNo_grid.items.length <= 0)
                return this.$toast.error('Chưa có dữ liệu để sắp xếp!');

            this.$refs.dlgSapXep.openDialog();
        },

        /* SAPXEP_ID: 1 - ASC; 2 - DESC */
        onSortProcess(items) {
            if (items.length > 0) {
                this.congNo_grid.items.sort(this.dynamicSortMultiple(items));
                this.congNo_grid.items = [...this.congNo_grid.items];
            };
        },

        dynamicSortMultiple(sortCmd) {
            return function (obj1, obj2) {
                let i = 0, result = 0, numberOfProperties = sortCmd.length;
                //try getting a different result from 0 (equal)
                //as long as we have extra properties to compare
                while (result === 0 && i < numberOfProperties) {
                    result = sortCmd[i].SAPXEP_ID === '1' ? obj1[sortCmd[i].FIELD].toString().localeCompare(obj2[sortCmd[i].FIELD].toString())
                        : obj2[sortCmd[i].FIELD].toString().localeCompare(obj1[sortCmd[i].FIELD].toString());
                    i++;
                }
                return result;
            }
        },

        async congNoGridpageChangeHandler(e) {
            this.congNo_grid.pageInfo.page = e.pageIndex;
            this.congNo_grid.pageInfo.maxSize = e.pageSize;
            await this.getDanhSachCongNoBaoCao();
        },

        resetPaging() {
            this.congNo_grid.items = [];
            this.congNo_grid.pageInfo = {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                currentRow: 0,
                sort: null,
                propertiesSort: null
            }
        },

        async initComboboxData() {
            this.params.nhomKH_Selected = -1;

            await this.getDanhSachLanTaoCongNo();
            if (this.danhSachLanTaoCongNoGetter != null && this.danhSachLanTaoCongNoGetter.length > 0) {
                console.log(this.danhSachLanTaoCongNoGetter);
                this.danhSachLanTaoCongNoGetter.forEach(item => {
                    this.params.lanTao_Options.push({ id: item.LANTAO_ID, text: item.KYHOADON });
                });
                this.params.lanTao_Selected = this.params.lanTao_Options[0].id;
            }

            if (this.listDonViGetter.length <= 0)
                await this.getDonVi();
            this.listDonViGetter.forEach(item => {
                this.params.donVi_Options.push({ id: item.DONVI_ID, text: item.TEN_DV });
            });
            this.params.donVi_Options.unshift({ id: -1, text: 'Tất cả' });
            if (this.params.donVi_Options.length > 0) {
                this.params.donVi_Selected = this.params.donVi_Options[0].id;
            }

            if (this.listHTTTGetter.length <= 0)
                await this.getHTTT();
            this.listHTTTGetter.forEach(item => {
                this.params.hinhThucTT_Options.push({ id: item.HTTT_ID, text: item.HTTT })
            });
            this.params.hinhThucTT_Options.unshift({ id: -1, text: 'Tất cả' });
            if (this.params.hinhThucTT_Options.length > 0) {
                this.params.hinhThucTT_Selected = this.params.hinhThucTT_Options[0].id;
            }
        },
        onSelectRowCheckbox(value) {
            this.rowsSelected = this.congNo_grid.items.filter((item) => value.includes(item.CONGNO_ID));
        },
        async getDanhSachCongNoBaoCao() {
            try {
                this.loading(true);
                this.congNo_grid.key++;
                this.params.soLuongTao = this.danhSachLanTaoCongNoGetter.filter((item) => this.params.lanTao_Selected == item.LANTAO_ID)[0].SOLUONG;

                let apiParams = {
                    donViId: this.params.donVi_Checked && this.params.donVi_Selected.toString() !== '-1' ? this.params.donVi_Selected : null,
                    nhanVienQLId: this.params.nhanVienQL_Checked && this.params.nhanVienQL_Selected.toString() !== '-1' ? this.params.nhanVienQL_Selected : null,
                    lanTaoId: this.params.lanTao_Selected,
                    tuyenThuId: this.params.NVTC_Checked && this.params.NVTC_Selected.toString() !== '-1' ? this.params.NVTC_Selected : null,
                    htttId: this.params.hinhThucTT_Checked && this.params.hinhThucTT_Selected.toString() !== '-1' ? this.params.hinhThucTT_Selected : null,
                    nhomKHId: this.params.nhomKH_Checked && this.params.nhomKH_Selected.toString() !== '-1' ? this.params.nhomKH_Selected : null,
                    phanLoaiKHId: this.params.phanLoaiKH_Checked && this.params.phanLoaiKH_Selected.toString() !== '-1' ? this.params.phanLoaiKH_Selected : null,
                    loaiKHId: this.params.loaiKH_Checked && this.params.loaiKH_Selected.toString() !== '-1' ? this.params.loaiKH_Selected : null,
                    maTT: this.params.maTT_Checked ? this.params.maTT : '',
                    daGan: null,
                    chiTiet: 1,
                    pageNum: this.congNo_grid.pageInfo.page + 1,
                    pageSize: this.congNo_grid.pageInfo.maxSize,
                    totalRow: 0,
                    total: this.params.soLuongTao,
                };
                this.exportParams = apiParams;
                let response = await API.getDanhSachCongNoBaoCao(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.congNo_grid.items = response.data.data;
                    this.congNo_grid.pageInfo = {
                        page: this.congNo_grid.pageInfo.page,
                        maxSize: this.congNo_grid.pageInfo.maxSize,
                        totalElement: this.params.soLuongTao,
                        totalPages: 20,
                        currentRow: 0
                    }
                } else this.resetPaging();
            }
            catch (error) {
                this.resetPaging();
                console.log(error)
                this.$toast.error(error.response.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.donVi_Selected': async function (newVal, oldVal) {
            await this.getNVQuanLy(newVal);
            this.params.nhanVienQL_Options = [];
            if (this.listNVQuanLyGetter.length > 0) {
                this.listNVQuanLyGetter.forEach(item => {
                    this.params.nhanVienQL_Options.push({ id: item.NHANVIEN_ID, text: item.GHEPTEN });
                });
                this.params.nhanVienQL_Selected = this.params.nhanVienQL_Options[0].id;
            }
        },

        'params.nhanVienQL_Selected': async function (newVal, oldVal) {
            await this.getNVThuCuoc(newVal);
            this.params.NVTC_Options = [];
            if (this.listNVThuCuocGetter.length > 0) {
                this.listNVThuCuocGetter.forEach(item => {
                    this.params.NVTC_Options.push({ id: item.TUYENTHU_ID, text: item.GHEPTUYEN });
                });
                this.params.NVTC_Selected = this.params.NVTC_Options[0].id;
            }
        },

        'params.nhomKH_Selected': async function (newVal, oldVal) {
            await this.getPhanLoaiKH(newVal);
            await this.getDanhSachLoaiKH(newVal);
            this.params.phanLoaiKH_Options = [];
            this.params.loaiKH_Options = [];

            if (this.listPLKHGetter.length > 0) {
                this.listPLKHGetter.forEach(item => {
                    this.params.phanLoaiKH_Options.push({ id: item.PHANLOAIKH_ID, text: item.MA_PLKH });
                });
                //Chuyển option Tất cả (id = null) về id = -1:
                let idx = this.params.phanLoaiKH_Options.findIndex(e => e.id === null);
                if (idx !== -1)
                    this.params.phanLoaiKH_Options[idx].id = -1;
                this.params.phanLoaiKH_Selected = this.params.phanLoaiKH_Options[0].id;
            }
            this.params.loaiKH_Options.unshift({ id: -1, text: 'Tất cả' });
            if (this.listLoaiKHGetter.length > 0) {
                this.listLoaiKHGetter.forEach(item => {
                    this.params.loaiKH_Options.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH });
                });
                this.params.loaiKH_Selected = this.params.loaiKH_Options[0].id;
            }
        },

        'params.nhomKH_Checked': async function (newVal, oldVal) {
            if (!newVal) {
                this.params.phanLoaiKH_Checked = false;
                this.params.loaiKH_Checked = false;
            }
        }
    }
}
</script>
