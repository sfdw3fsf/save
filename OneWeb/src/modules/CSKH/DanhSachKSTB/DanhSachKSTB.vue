<template src="./DanhSachKSTB.html"></template>
<style src="./DanhSachKSTB.scss"></style>
<script>
import moment from "moment";
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import DanhSachKSTB5NgayKLLApi from "./../api/DanhSachKSTB5NgayKLLApi";
import select2 from "@/components/Select2.vue";
import downloadexcel from "vue-json-excel";
import frmKhaoSatKH_KLL from "../KhaoSat/frmKhaoSatKH_KLL"
import {exportToXls} from "../utils/functions/cskhExportXLS.js"
import Vue from 'vue'
export default {
    name: "DanhSachKSTB",
    components: {
        datagrid: CustomDataGrid,
        appSelect2: select2,
        downloadexcel,
        appKSKhachHang: frmKhaoSatKH_KLL,
    },
    props: {
        loaiDanhSach: Number,
    },
    computed: {
        dataExcel() {
            return JSON.stringify(this.dsThueBao);
            //return Object.values(this.dsThueBao);
        },
        textWeek() {
            try {
                return this.weekValue[this.weekOptionsSelected - 1];
            } catch (e) {
                return "";
            }
        },
        ngayChot2() {
            try {
                return this.weekDate[this.weekOptionsSelected - 1];
            } catch (e) {
                return "";
            }
        },
        kieuId() {
            switch (this.loaiDanhSach) {
                case 0:
                    return 2;
                case 1:
                    return 0;
                case 2:
                    return 4;
            }
            return 0;
        },
        ctSuyHaoId() {
            return this.suyHaoId
        }
    },
    data() {
        return {
            loaiKhaoSat: {
                TB_5_NGAY_KLL: 0,
                TB_SUY_HAO_CAO: 1,
                MYTV_5_NGAY_KO_DN: 2,
            },
            headers: [
                {
                    title: "DANH SÁCH KHẢO SÁT THUÊ BAO FIBER KHÔNG PHÁT SINH LƯU LƯỢNG",
                    list: [
                        {
                            name: "Chăm sóc khách hàng",
                            link: { name: "Ui.buttons" },
                        },
                        {
                            name: "Danh sách khảo sát thuê bao Fiber không phát sinh lưu lượng",
                            link: { name: "Ui.buttons" },
                        },
                    ],
                },
                {
                    title: "DANH SÁCH KHẢO SÁT THUÊ BAO GPON SUY HAO CAO",
                    list: [
                        {
                            name: "Chăm sóc khách hàng",
                            link: { name: "Ui.buttons" },
                        },
                        {
                            name: "Danh sách khảo sát thuê bao GPON suy hao cao",
                            link: { name: "Ui.buttons" },
                        },
                    ],
                },
                {
                    title: "DANH SÁCH KHẢO SÁT THUÊ BAO MYTV KHÔNG ĐĂNG NHẬP",
                    list: [
                        {
                            name: "Chăm sóc khách hàng",
                            link: { name: "Ui.buttons" },
                        },
                        {
                            name: "Danh sách khảo sát thuê bao MyTV không đăng nhập",
                            link: { name: "Ui.buttons" },
                        },
                    ],
                },
            ],
            header: {
                title: "DANH SÁCH KHẢO SÁT THUÊ BAO FIBER KHÔNG PHÁT SINH LƯU LƯỢNG",
                list: [
                    //   { name: "Trang chủ", link: { name: "Ui.cards" } },
                    {
                        name: "Lập hợp đồng",
                        link: { name: "Ui.buttons" },
                    },
                    {
                        name: "Lắp đặt mới",
                        link: { name: "Ui.buttons" },
                    },
                ],
            },
            ngayChot: new Date(),
            today: new Date(),
            trangThaiSelected: -1,
            trangThaiOptions: [
                { id: -1, text: "---Tất cả---" },
                { id: 0, text: "Chưa khảo sát" },
                { id: 1, text: "Đã khảo sát" },
            ],
            tinhTrangSelected: this.loaiDanhSach, //fix cung bằng 0
            tinhTrangOptions: [
            ],
            phongBHSelected: 0,
            phongBHOptions: [{ id: 0, text: "---Tất cả---" }],
            khuVucSelected: 0,
            khuVucOptions: [{ id: 0, text: "---Tất cả---" }],
            tuyenThuSelected: 0,
            tuyenThuOptions: [{ id: 0, text: "---Tất cả---" }],
            showMode: "THEO_DIABAN", //'THEO_TOKT'  'THEO_PHONG_BH'
            dsThueBaoFields: [
                { fieldName: "TRANGTHAI_KS", headerText: "Trạng thái KS", allowFiltering: true, allowSorting: false, cellCreatedEventEnabled: true, freeze: "left",
                    callback: (e) => {
                        if (e == null) return "Chưa xác định"
                        if (e == 0) return "Chưa khảo sát"
                        if (e == 1) return "Đã khảo sát"
                        if (e == -1) return "Đang khảo sát"
                    }
                },
                { fieldName: "CT_SUYHAO_ID", isPrimaryKey: true, allowFiltering: true, allowSorting: false, visible: false},
                { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: false, freeze: "left"},
                { fieldName: "LOAIHINH_TB", headerText: "Loại hình", allowFiltering: true, allowSorting: false, freeze: "left"},
                { fieldName: "NGAY_CN", headerText: "Ngày cập nhật", allowFiltering: true, allowSorting: false, freeze: "left"},
                { fieldName: "DOANH_THU", headerText: "Doanh thu", allowFiltering: true, allowSorting: false},
                { fieldName: "TEN_TB", headerText: "Tên thuê bao", allowFiltering: true, allowSorting: false},
                { fieldName: "DIACHI_LD", headerText: "Địa chỉ lắp đặt", allowFiltering: true, allowSorting: false},
                { fieldName: "LOAI_KH", headerText: "Loại khách hàng", allowFiltering: true, allowSorting: false},
                { fieldName: "NVKD_QL", headerText: "NVKD quản lý địa bàn", allowFiltering: true, allowSorting: false},
                { fieldName: "NV_AM", headerText: "Nhân viên AM", allowFiltering: true, allowSorting: false},
                { fieldName: "TUYEN_THU", headerText: "Tuyến thu", allowFiltering: true, allowSorting: false},
                { fieldName: "DIABAN_CAP3", headerText: "Địa bàn cấp 3", allowFiltering: true, allowSorting: false},
                { fieldName: "TENPHONG", headerText: "Phòng bán hàng", allowFiltering: true, allowSorting: false},
                { fieldName: "TO_KT_QL", headerText: "Tổ kỹ thuật quản lý", allowFiltering: true, allowSorting: false},
                { fieldName: "DIENTHOAI_KH", headerText: "Điện thoại KH", allowFiltering: true, allowSorting: false},
                { fieldName: "NGUOI_CN", headerText: "Người CN", allowFiltering: true, allowSorting: false},
                { fieldName: "NGAY_HOI", headerText: "Ngày hỏi", allowFiltering: true, allowSorting: false},
                { fieldName: "TG_GOI_GD", headerText: "Tham gia gói gia đình", allowFiltering: true, allowSorting: false, template: this.TG_GOI_GD_Template, cssClass: "text-center", filterTemplate: this.filterTemplate,
                    callback: (e) => {
                        return e == 1 ? "Checked" : "Unchecked"
                    }
                },
                { fieldName: "TG_GOI_CUNG_MYTV", headerText: "Tham gia gói tích hợp cùng MyTV", allowFiltering: true, allowSorting: false, template: this.TG_GOI_CUNG_MYTV_Template, cssClass: "text-center", filterTemplate: this.filterTemplate,
                    callback: (e) => {
                        return e == 1 ? "Checked" : "Unchecked"
                    }
                },
                { fieldName: "DT_CAO", headerText: "Doanh thu trên 200k", allowFiltering: true, allowSorting: false, template: this.DT_CAO_Template, cssClass: "text-center", filterTemplate: this.filterTemplate,
                    callback: (e) => {
                        return e == 1 ? "Checked" : "Unchecked"
                    }
                },
            ],
            exportExcelFields: {
                "Trạng thái KS": "TRANGTHAI_KS_TEXT",
                "Số máy/ACC": "MA_TB",
                "Loại hình": "LOAIHINH_TB",
                "Ngày cập nhật": "NGAY_CN",
                "Doanh thu": "DOANH_THU",
                "Tên thuê bao": "TEN_TB",
                "Địa chỉ lắp đặt": "DIACHI_LD",
                "Loại khách hàng": "LOAI_KH",
                "NVKD quản lý địa bàn": "NVKD_QL",
                "Nhân viên AM": "NV_AM",
                "Tuyến thu": "TUYEN_THU",
                "Địa bàn cấp 3": "DIABAN_CAP3",
                "Phòng bán hàng": "TENPHONG",
                "Tổ kỹ thuật quản lý": "TO_KT_QL",
                "Điện thoại KH": {
                    field: "DIENTHOAI_KH",
                    callback: (value) => {
                        return `${value}`;
                    },
                },
                "Người CN": "NGUOI_CN",
                "Ngày hỏi": "NGAY_HOI",
                "Tham gia gói gia đình": {
                    field: "TG_GOI_GD",
                    callback: (value) => {
                        return value == 1 ? "Checked" : "Unchecked";
                    },
                },
                "Tham gia gói tích hợp cùng MyTV": {
                    field: "TG_GOI_CUNG_MYTV",
                    callback: (value) => {
                        return value == 1 ? "Checked" : "Unchecked";
                    },
                },
                "Doanh thu trên 200k": {
                    field: "DT_CAO",
                    callback: (value) => {
                        return value == 1 ? "Checked" : "Unchecked";
                    },
                },
            },
            dsThueBao: [],
            monthPicker: new Date(),
            weekOptions: [],
            weekValue: [],
            weekDate: [],
            weekOptionsSelected: 0,
            treeFields: "",
            tenDonViSearch: "",
            checkedDonVi: [],
            suyHaoId: 0,
            // columnCheckBoxTemplate: function () {
            //     return {
            //         template : Vue.component('columnTemplate', {
            //             template: {
            //                 template: '<div class="check-action"><input type="checkbox" class="check" checked /></div>',
            //                 data() {
            //                     return {
            //                         data: { }
            //                     }
            //                 },
            //             }
            //         }
            //     }
            // },
            TG_GOI_GD_Template: function () {
                return {
                    template : Vue.component('columnTemplate', {
                        template: `<div class="check-action">
                                    <input type="checkbox" class="check" v-model="data.TG_GOI_GD" disabled><span class="name" /></input>
                                </div>`,
                        data () {
                            return { data: {} };
                        },
                    })
                }
            },
            TG_GOI_CUNG_MYTV_Template: function () {
                return {
                    template : Vue.component('columnTemplate', {
                        template: `<div class="check-action">
                                    <input type="checkbox" class="check" v-model="data.TG_GOI_CUNG_MYTV" disabled><span class="name" /></input>
                                </div>`,
                        data () {
                            return { data: {} };
                        },
                    })
                }
            },
            DT_CAO_Template: function () {
                return {
                    template : Vue.component('columnTemplate', {
                        template: `<div class="check-action">
                                    <input type="checkbox" class="check" v-model="data.DT_CAO" disabled><span class="name" /></input>
                                </div>`,
                        data () {
                            return { data: {} };
                        },
                    })
                }
            },
            filterTemplate: function () {
                return {
                    template : Vue.component('filterTemplate', {
                        template: `<div class="check-action">
                                    <input type="checkbox" class="check" v-model="data.DT_CAO" ><span class="name" /></input>
                                </div>`,
                        data () {
                            return { data: {} };
                        },
                    })
                }
            },
        };
    },
    async created() {
        this.loading(true);
        try {
            let ds = await this.createCayDiaBan();
            this.treeFields = {
                dataSource: ds,
                id: "KHUVUC_ID",
                parentID: "KHUVUC_CHA_ID",
                text: "TEN_KV",
                hasChildren: "hasChild",
            };
        } catch (e) {
        } finally {
            this.loading(false);
        }
    },
    async mounted() {
        this.loading(true);
        if (this.onCheckExistThamSo("KHAOSAT_KLL_THEO_DIABAN")) {
            this.showMode = "THEO_DIABAN";
            //HT_CAY_DIABAN
        } else if (this.onCheckExistThamSo("KHAOSAT_KLL_THEO_TOKT")) {
            this.showMode = "THEO_TOKT";
            //HT_CAY_DONVI
        } else {
            this.showMode = "THEO_PHONG_BH";
            //HT_CBO_PHONG
            await this.onLoadDSPhongBH();
            await this.getDSLoaiKS();
        }

        // this.showMode = "THEO_DIABAN"; //@faledata
        if (this.loaiDanhSach == 1) {
            this.onMonthChange();
        }
        this.loading(false);
    },
    methods: {
        onTenDiaBanChange() {},
        onTenDiaBanSearch() {},
        async createCayDiaBan() {
            //@fakedata
            let data = {
                nhanVienId: this.$root.token.getNhanVienID(),
                loaiKvId: 4,
                loaiNvId: 52,
            };
            let ds = [];
            try {
                var rs = await this.$root.context.post(
                    "/web-cskh/api/don-vi/nhan-vien",
                    data
                );
                ds = rs.data || [];
                let dsDonViCha = ds.map((x) => x.KHUVUC_CHA_ID);
                ds.forEach((element) => {
                    if (dsDonViCha.includes(element.KHUVUC_ID)) {
                        element.hasChild = true;
                    }
                });
                this.treeFields.dataSource = ds;
            } catch (e) {
            } finally {
                return ds;
            }
        },
        onCheckExistThamSo(thamSo) {
            let result = false;
            this.loading(true);
            DanhSachKSTB5NgayKLLApi.getThamSoMacDinh(this.axios, {
                dsMaTs: thamSo,
            })
                .then((response) => {
                    result =
                        response.data.data && response.data.data.length > 0;
                    this.loading(false);
                })
                .catch((reject) => {
                    result = false;
                    this.loading(false);
                });
            return result;
        },
        async onLoadDSPhongBH() {
            let data = {
                loaiDichVuId: 2,
            };
            DanhSachKSTB5NgayKLLApi.getDsPhongBanHang(this.axios, data)
                .then((response) => {
                    this.phongBHOptions = [];
                    this.phongBHOptions.push({ id: 0, text: "---Tất cả---" });
                    response.data.data.forEach((element) => {
                        this.phongBHOptions.push({
                            id: element.DONVI_ID,
                            text: element.TEN_DV,
                        });
                    });
                    this.phongBHSelected = 0;
                })
                .catch((reject) => {
                    // this.$root.toastError(
                    //     "Không lấy đc danh sách phòng bán hàng: " +
                    //         reject.response.error || reject.response.message
                    // );
                });
            await  this.onLoadDSKhuVucTheoPhongBH();
            await this.onLoadDsTuyenThu();
        },
        async onLoadDSKhuVucTheoPhongBH() {
            this.loading(true);
            let data = {
                loaiDichVuId: 32,
                phongDichVuId: this.phongBHSelected,
            };
            DanhSachKSTB5NgayKLLApi.getDsPhongBanHang(this.axios, data)
                .then((response) => {
                    this.khuVucOptions = [];
                    this.khuVucOptions.push({ id: 0, text: "---Tất cả---" });
                    response.data.data.forEach((element) => {
                        this.khuVucOptions.push({
                            id: element.DONVI_ID,
                            text: element.TEN_DV,
                        });
                    });
                    this.khuVucSelected = 0;
                    this.loading(false);
                })
                .catch((reject) => {
                    // this.$root.toastError(
                    //     "Không lấy đc danh sách khu vực: " +
                    //         reject.response.error || reject.response.message
                    // );
                    this.loading(false);
                });
        },
        async onLoadDsTuyenThu() {
            this.loading(true);
            let postData = {
                khuVucId: this.khuVucSelected,
            };
            DanhSachKSTB5NgayKLLApi.getDSTuyenThu(this.axios, postData)
                .then((response) => {
                    this.tuyenThuOptions = [];
                    this.tuyenThuOptions.push({ id: 0, text: "---Tất cả---" });
                    response.data.data.forEach((element) => {
                        this.tuyenThuOptions.push({
                            id: element.TUYENTHU_ID,
                            text: element.MANV_TC + ' - ' + element.TENNV_TC,
                        });
                    });
                    this.tuyenThuSelected = 0;
                    this.loading(false);
                })
                .catch((reject) => {
                    // this.$root.toastError(
                    //     "Không lấy đc danh sách tuyến thu: " +
                    //         reject.response.error || reject.response.message
                    // );
                    this.loading(false);
                });
        },
        async onFetchData() {
            if (!this.ngayChot || this.ngaychot == '') {
                this.$root.toastError("Vui lòng chọn ngày chốt.");
                return;
            }

            if (this.showMode == "THEO_DIABAN") {
                if (this.checkedDonVi.length == 0) {
                    this.$root.toastError(
                        "Chưa chọn địa bàn.\r\nTick chọn địa bàn từ câu bên cột phải."
                    );
                    return;
                }
                await this.onGetDsTBSuyHaoTheoDiaBan();
            } else if (this.showMode == "THEO_TOKT") {
                if (this.checkedDonVi.length == 0) {
                    this.$root.toastError(
                        "Chưa chọn tổ kỹ thuật.\r\nTick chọn địa bàn từ câu bên cột phải."
                    );
                    return;
                }
            } else {
                await this.onGetDsTBSuyHaoTheoNVKD();
            }
        },
        async onGetDsTBSuyHaoTheoNVKD() {
            let data = {
                trangThai: this.trangThaiSelected,
                ngay:
                    this.loaiDanhSach == 1
                        ? this.ngayChot2
                        : moment(this.ngayChot).format("DD/MM/YYYY"),
                phongId: this.phongBHSelected,
                khuVucId: this.khuVucSelected,
                tuyenThuId: this.tuyenThuSelected,
                kieuId: this.loaiDanhSach,
                pageNo: 1,
                pageSize: 10000,
            };
            this.loading(true);
            try {
                let response =
                    await DanhSachKSTB5NgayKLLApi.getDsTBSuyHaoTheoNVKD(
                        this.axios,
                        data
                    );
                this.dsThueBao = response.data.data.data || [];
                this.dsThueBao.forEach((e) => {
                    e.DT_CAO = e.DOANH_THU > 200000 ? 1 : 0;
                    if (e.TRANGTHAI_KS == null) e.TRANGTHAI_KS_TEXT = "Chưa xác định";
                    else if (e.TRANGTHAI_KS == 0)
                        e.TRANGTHAI_KS_TEXT = "Chưa khảo sát";
                    else if (e.TRANGTHAI_KS == 1)
                        e.TRANGTHAI_KS_TEXT = "Đã khảo sát";
                    else if (e.TRANGTHAI_KS == -1)
                        e.TRANGTHAI_KS_TEXT = "Đang khảo sát";
                });
            } catch (e) {
                this.$root.toastError("Không lấy đc danh sách thuê bao: ");
            } finally {
                this.loading(false);
            }
        },
        grid_dataGridCellCreated(e) {
            switch (e.cellFieldName) {
                case "TRANGTHAI_KS":
                    switch (e.cellDataItem.TRANGTHAI_KS) {
                        case 0:
                            $(e.cellElement).css({background: '#D3D3D3'})
                            $(e.cellElement).text("Chưa khảo sát")
                            break
                        case 1:
                            $(e.cellElement).css({background: '#87CEFA'})
                            $(e.cellElement).text("Đã khảo sát")
                            break
                        case -1:
                            $(e.cellElement).css({background: '#B8F2E6'})
                            $(e.cellElement).text("Đang khảo sát")
                            break
                        case null:
                            $(e.cellElement).css({background: '#FFFFFF'})
                            $(e.cellElement).text("Chưa xác định")
                            break
                    }
                    break
                case "TG_GOI_GD":
                        e.cellAllowHtml = true;
                        $(e.cellElement).removeClass('text-right')
                        $(e.cellElement).addClass('center')
                        $(e.cellElement).empty()
                        $(e.cellElement).append(
                            '<div class="check-action">'
                            + (e.cellDataItem.TG_GOI_GD == 0 ?
                                '<input type="checkbox" class="check" disabled><span class="name"></span>' :
                                '<input type="checkbox" class="check" disabled checked><span class="name"></span>')
                            + '</div>'
                        );
                    break
                case "TG_GOI_CUNG_MYTV":
                        e.cellAllowHtml = true;
                        $(e.cellElement).removeClass('text-right')
                        $(e.cellElement).addClass('center')
                        $(e.cellElement).empty()
                        $(e.cellElement).append(
                            '<div class="check-action">'
                            + (e.cellDataItem.TG_GOI_CUNG_MYTV == 0 ?
                                '<input type="checkbox" class="check" disabled><span class="name"></span>' :
                                '<input type="checkbox" class="check" disabled checked><span class="name"></span>')
                            + '</div>'
                        );
                    break
                case "DT_CAO":
                        e.cellAllowHtml = true;
                        $(e.cellElement).removeClass('text-right')
                        $(e.cellElement).addClass('center')
                        $(e.cellElement).empty()
                        $(e.cellElement).append(
                            '<div class="check-action">'
                            + (e.cellDataItem.DT_CAO == 0 ?
                                '<input type="checkbox" class="check" disabled><span class="name"></span>' :
                                '<input type="checkbox" class="check" disabled checked><span class="name"></span>')
                            + '</div>'
                        );
                    break
            }

        },
        onGetDsTBSuyHaoTheoDiaBan() {
            let data = {
                trangThai: this.trangThaiSelected,
                ngay:
                    this.loaiDanhSach == 1
                        ? this.ngayChot2
                        : moment(this.ngayChot).format("DD/MM/YYYY"),
                dsDiaBanId: this.checkedDonVi.join(),
                pageNo: 1,
                pageSize: 10000,
            };
            this.loading(true);
            DanhSachKSTB5NgayKLLApi.getDsTBSuyHaoTheoDiaBan(this.axios, data)
                .then((response) => {
                    this.dsThueBao = response.data.data.data || [];
                    this.dsThueBao.forEach((e) => {
                        e.DT_CAO = e.DOANH_THU > 200000 ? 1 : 0;
                        e.TRANGTHAI_KS_TEXT =
                            e.TRANGTHAI_KS == 0
                                ? "Chưa khảo sát"
                                : "Đã khảo sát";
                    });
                    this.loading(false);
                })
                .catch((reject) => {
                    // this.$root.toastError(
                    //     "Không lấy đc danh sách thuê bao: " +
                    //         reject.response.error || reject.response.message
                    // );
                    this.loading(false);
                });
        },
        // onDsThueBaoRowChanged(items) {
        //     // console.log('getSelectedValues', this.$refs.gridDsThueBao.getSelectedRecords())
        //     // console.log('dataItem', dataItem)
        //     if (items && items.length > 0) {
        //         this.suyHaoId = items[items.length - 1]
        //     }
        //     else {
        //         this.suyHaoId = -1
        //     }
        // },
        onDsThueBaoRowChanged(item) {
            if (item) {
                this.suyHaoId = item.CT_SUYHAO_ID
            }
            else {
                this.suyHaoId = -1
            }
        },
        async onPhongBHChanged() {
            await Promise.all([
                this.onLoadDSKhuVucTheoPhongBH(),
                this.onLoadDsTuyenThu(),
            ]);
        },
        onMonthChange() {
            var friday = moment(this.monthPicker)
                .startOf("month")
                .day("Friday");
            if (friday.date() > 7) friday.add(7, "d");
            var month = friday.month();
            this.weekOptions = [];
            this.weekValue = [];
            this.weekDate = [];
            let index = 1;
            while (month === friday.month()) {
                this.weekDate.push(moment(friday).format("DD/MM/YYYY") + "");
                this.weekOptions.push({ text: index, value: index++ });
                let prevFriday = moment(friday).add(-7, "d");
                // prevFriday = prevFriday.add(-7, "d")
                // console.log('friday: ', friday.toString())
                // console.log('prevFriday: ', prevFriday.toString())
                this.weekValue.push(
                    prevFriday.format("DD/MM/YYYY") +
                        " -> " +
                        friday.format("DD/MM/YYYY")
                );
                friday.add(7, "d");
            }
            this.weekOptionsSelected = this.weekOptions[0].value;
        },
        nodeChecked(treeObj) {
            if (treeObj.action === "check") {
                let CheckedObjs = treeObj.data.filter(
                    (item) => item["isChecked"] === "true"
                );
                CheckedObjs.forEach((item) => this.checkedDonVi.push(item.id));
            } else if (treeObj.action === "uncheck") {
                let CheckedObjs = treeObj.data.filter(
                    (item) => item["isChecked"] !== "true"
                );
                CheckedObjs.forEach((item) => {
                    // console.log(this.checkedDonVi)
                    let index = this.checkedDonVi.findIndex(
                        (element) => element === item.id
                    );
                    if (index != -1)
                        //index = -1 khong co element trong arr
                        this.checkedDonVi.splice(
                            this.checkedDonVi.findIndex(
                                (element) => element === item.id
                            ),
                            1
                        );
                    // delete this.checkedDonVi[this.checkedDonVi.findIndex((element) => element === item.id)]
                });
                // console.log("end:",this.checkedDonVi)
            }
        },
        onStartDownload() {
            if (this.dsThueBao.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất Excel !')
                return
            }
        },
        openKhaoSat() {
            if (this.dsThueBao.length <= 0) {
                this.$root.toastError("Không có thuê bao để khảo sát.")
                return
            }
            if (this.suyHaoId < 0) {
                this.$root.tastError("Chưa lựa chọn thuê bao cần khảo sát!")
                return
            }
            let row = this.dsThueBao.find(x => x.CT_SUYHAO_ID == this.suyHaoId)
            let khaoSatMoi = false
            let rowIndex = -1
            if (row) {
                if (row.TRANGTHAI_KS == 0) {
                    row.TRANGTHAI_KS = -1
                    khaoSatMoi = true
                    rowIndex = this.$refs.gridDsThueBao.$refs.grid.getRowIndexByPrimaryKey(this.suyHaoId)
                    let cell = this.$refs.gridDsThueBao.$refs.grid.getCellFromIndex(rowIndex, 1)

                    if (cell) {
                        cell.style.backgroundColor = '#B8F2E6'
                        cell.innerHTML = "Đang khảo sát"
                    }
                }
            }
            this.$refs.frmKhaoSatKH_KLL.khaoSatMoi = khaoSatMoi
            this.$refs.frmKhaoSatKH_KLL.row = rowIndex
            this.$refs.frmKhaoSatKH_KLL.openDialog()
        },
        hideKhaoSat() {
            this.$refs.frmKhaoSatKH_KLL.closeDialog()
        },
        onGridDataBound(args) {
        },
        onGridQueryCellInfor(args) {
        },
        async onKhaoSatXong() {
            await this.onFetchData()
        },
        onExportExcel() {
            // let excelExportProperties = {
            //     fileName:"export.xlsx",
            //     dataSource: this.dsThueBao
            // };
            // this.$refs.gridDsThueBao.excelExport(excelExportProperties);
            if(this.dsThueBao.length <= 0){
               this.$root.toastError("Không có dữ liệu để xuất Excel!")
               return
            }
            exportToXls(this.dsThueBaoFields, this.dsThueBao, "danhsachthuebao.xlsx")
        },
        async getDSLoaiKS(){
            try {
                const response = await DanhSachKSTB5NgayKLLApi.layDSLoaiKS(this.axios)
               
                    if(response && response.data && response.data.error_code === 'BSS-00000000'){
                        this.tinhTrangOptions =  response.data.data?.map(e=>{
                            return { id: e.id_ks,text: e.ten_ks}
                        })    
                    }
            } catch (error) {
                console.log(error)
            }
           
        },
    },
};
</script>
