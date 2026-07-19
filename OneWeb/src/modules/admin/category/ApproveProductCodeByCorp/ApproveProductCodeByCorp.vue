<template src="./ApproveProductCodeByCorp.html"></template>
<script>
import API from './ApproveProductCodeByCorpAPI';
import moment from 'moment';
import XLSX from 'xlsx';
import breadcrumb from '@/components/breadcrumb';
import popupProductDetail from './popup/popupProductDetail.vue';
import { GridPlugin, Group } from "@syncfusion/ej2-vue-grids";

export default {
    name: "ApproveProductCodeByCorp",
    components: {
        breadcrumb,
        popupProductDetail
    },
    provide: {
        grid: [Group]
    },
    data() {
        return {
            DmChung: [],
            dsSpdv: [],
            dsPtPhatTrien: [],
            dsDonVi: [],
            dsTrangThai: [],
            SanPhamSelected: null,
            lydo_koduyet: '',
            searchInfo: {
                nhomspdv_id: 0,
                phuongthuc_id: 0,
                donvi_id: 0,
                trangthai_id: 0,
                tag: 2,
                ma_spdv: '',
                ten_spdv: ''
            },
            dgSanPham: {
                dataSource: [],
                selectionOptions: { type: 'Single', persistSelection: false },
                groupOptions: {
                    columns: ['nhomgiaiphap'],
                    captionTemplate: '<span class="groupItems"> ha ha ha ${headerText} - ${key }</span>'
                },
                columns: [
                    { fieldName: "nhomgiaiphap", headerText: "Nhóm giải pháp", allowFiltering: true, isGroupedColumn: true },
                    { fieldName: "dv_chuquan", headerText: "Đơn vị chủ quản", allowFiltering: true },
                    { fieldName: "ngay_yc", headerText: "Mã định danh", allowFiltering: true },
                    { fieldName: "ten_spdv", headerText: "Tên nội bộ", allowFiltering: true },
                    { fieldName: "ten_viettat", headerText: "Tên viết tắt", allowFiltering: true },
                    { fieldName: "ngaydm_td", headerText: "Ngày GTM", allowFiltering: true },
                    { fieldName: "trangthai_spdv", headerText: "Trạng thái", allowFiltering: true }
                ]
            },
            dgLichSu: {
                dataSource: [],
                selectionOptions: { type: 'Single', persistSelection: false },
                columns: [
                    { fieldName: "ma_spdv", headerText: "Mã SPDV", allowFiltering: true },
                    { fieldName: "ma_spdv_dk", headerText: "Mã SPDV DK", allowFiltering: true },
                    { fieldName: "ten_spdv", headerText: "Tên SPDV", allowFiltering: true },
                    { fieldName: "ten_viettat", headerText: "Tên viết tắt", allowFiltering: true },
                    { fieldName: "trangthai_spdv", headerText: "Trạng thái SPDV", allowFiltering: true },
                    { fieldName: "dv_chuquan", headerText: "ĐV chủ quản", allowFiltering: true },
                    { fieldName: "ngay_gtm", headerText: "Ngày GTM", allowFiltering: true },
                    { fieldName: "ngaydm_cty", headerText: "Ngày DM TCT", allowFiltering: true },
                    { fieldName: "ngaydm_td", headerText: "Ngày DM TĐ", allowFiltering: true },
                    { fieldName: "yeucau_kt", headerText: "Yêu cầu KT", allowFiltering: true },
                    { fieldName: "giatri_manglai", headerText: "Giá trị mang lại", allowFiltering: true },
                    { fieldName: "hientrang_gtm", headerText: "Hiện trạng GTM", allowFiltering: true },
                    { fieldName: "thaotac", headerText: "Thao tác", allowFiltering: true },
                ]
            },
        }
    },
    methods: {
        async layDanhMucChung() {
            let data = {};
            //init danh mục
            // this.dsSpdv.push({ nhomspdv_id: 0, ten_nhomspdv: "-Chọn tất cả-" });
            API.getDanhMucChung(this.axios, data)
                .then((response) => {
                    this.DmChung = response.data.data;
                    //nhóm SPDV
                    let items = [];
                    items.push({ nhomspdv_id: 0, ten_nhomspdv: "-Chọn tất cả-" });
                    this.DmChung.NHOM_SPDV.forEach(function (item) {
                        items.push({
                            nhomspdv_id: item.nhomspdv_id,
                            ten_nhomspdv: item.ten_nhomspdv,
                        });
                    });
                    this.dsSpdv.push(...items);
                    //PT phát triển
                    items = [];
                    items.push({ phuongthuc_id: 0, ten_phuongthuc: "-Chọn tất cả-" });
                    this.DmChung.PHUONGTHUC_PT.forEach(function (item) {
                        items.push({
                            phuongthuc_id: item.phuongthuc_id,
                            ten_phuongthuc: item.ten_phuongthuc,
                        });
                    });
                    this.dsPtPhatTrien.push(...items);
                    //đơn vị chủ quản
                    items = [];
                    items.push({ donvi_id: 0, ten_dv: "-Chọn tất cả-" });
                    this.DmChung.DONVI.forEach(function (item) {
                        items.push({
                            donvi_id: item.donvi_id,
                            ten_dv: item.ten_dv,
                        });
                    });
                    this.dsDonVi.push(...items);
                    this.searchInfo.donvi_id = this.dsDonVi[0].donvi_id;
                    //trạng thái
                    items = [];
                    items.push({ trangthai_id: 0, trangthai_spdv: "-Chọn tất cả-" });
                    this.DmChung.TRANGTHAI.forEach(function (item) {
                        items.push({
                            trangthai_id: item.trangthai_id,
                            trangthai_spdv: item.trangthai_spdv,
                        });
                    });
                    this.dsTrangThai.push(...items);
                })
                .catch((error) => {
                    // console.log(error);
                });
        },
        async timKiem() {
            this.$root.loading(true);
            this.dgSanPham.dataSource = [];
            let donvi_id_1 = (this.searchInfo.donvi_id == 0) ? "" : this.searchInfo.donvi_id;
            // let data = { "tag": this.searchInfo.tag, "nhomspdv_id": this.searchInfo.nhomspdv_id, "phuongthucpt_id": this.searchInfo.phuongthuc_id, "donvi_id": this.searchInfo.donvi_id, "trangthai_id": this.searchInfo.trangthai_id, "ma_spdv": this.searchInfo.ma_spdv, "ten_spdv": this.searchInfo.ten_spdv };
            let data = { "tag": 2, "nhomspdv_id": this.searchInfo.nhomspdv_id, "phuongthuc_id": this.searchInfo.phuongthuc_id, "donvi_id": donvi_id_1, "trangthai_id": this.searchInfo.trangthai_id, "ma_spdv": "", "ten_spdv": "" };
            API.getDsSanPham(this.axios, data)
                .then((response) => {
                    this.$root.loading(false);
                    if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0) {
                        this.dgSanPham.dataSource = response.data.data;
                    }
                })
                .catch((error) => {
                    this.dgSanPham.dataSource = [];
                    this.$root.loading(false);
                });
        },
        async xemThongTinSanPham() {
            this.$bvModal.show('popupProductDetail');
        },
        async pheDuyet_Tct() {
            if (this.SanPhamSelected == undefined) {
                this.$toast.warning("Vui lòng chọn sản phẩm");
                return;
            }
            let data = { "spdv_id": this.SanPhamSelected.spdv_id };
            API.pheDuyetCapTct(this.axios, data)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        this.$toast.success(response.data.message);
                        this.timKiem();
                    }
                    else {
                        this.$toast.warning(response.data.message);

                    }
                })
                .catch((error) => {
                    this.$toast.show('Trạng thái: ' + response.data.data)
                });
        },
        async khongPheDuyet_Tct() {
            if (this.SanPhamSelected == undefined) {
                this.$toast.warning("Vui lòng chọn sản phẩm");
                return;
            }
            this.$bvModal.show('popupKoDuyet');
        },
        async handleKoDuyet_Hidden() {
            this.lydo_koduyet = '';
        },
        async handleKoDuyet_Ok() {
            let data = { "spdv_id": this.SanPhamSelected.spdv_id, "lydo": this.lydo_koduyet };
            API.tuChoiDuyetCapTct(this.axios, data)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        this.$toast.success(response.data.message);
                        this.timKiem();
                    }
                    else {
                        this.$toast.warning(response.data.message);

                    }
                })
                .catch((error) => {
                    // this.$toast.info(response.message);
                });
        },
        async guiTapDoanDuyet() {
            if (this.SanPhamSelected == undefined) {
                this.$toast.warning("Vui lòng chọn sản phẩm");
                return;
            }
            let data = { "spdv_id": this.SanPhamSelected.spdv_id };
            API.guiPheDuyetCapTapDoan(this.axios, data)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        this.$toast.success(response.data.message);
                    }
                    else {
                        this.$toast.warning(response.data.message);

                    }
                })
                .catch((error) => {
                    this.$toast.warning(response.data.message);
                });
        },
        async xemLichSuSanPham() {
            if (this.SanPhamSelected == undefined) {
                this.$toast.warning("Vui lòng chọn sản phẩm");
                return;
            }
            this.dgLichSu.dataSource = [];
            this.$bvModal.show("popupProductHistory");
            let data = { "spdv_id": this.SanPhamSelected.spdv_id };
            API.getLichSuSanPham(this.axios, data)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        this.dgLichSu.dataSource = response.data.data;
                    }
                })
                .catch((error) => {
                    this.$toast.warning(response.data.message);
                });
        },
        async onDgSanPham_RowChanged(row) {
            this.SanPhamSelected = row;
        }
    },
    async mounted() {
        await this.layDanhMucChung();
    },
    async created() {

    },
    computed: {
        ds_sanpham_list: {
            get() {
                var data_list = this.dgSanPham.dataSource.map((e, index) => {
                    // if (index > 0)
                    // {
                    //     this.dgSanPham.dataSource[index].nhomgiaiphap = `${index + 1}`;

                    // }
                    // else {

                    // }
                    // this.dgSanPham.dataSource[index].nhomgiaiphap = `${index + 1}: Hướng giao ${e.nhomgiaiphap}`;
                    return e;
                });
                return data_list;
            }
        }
    },
}
</script>