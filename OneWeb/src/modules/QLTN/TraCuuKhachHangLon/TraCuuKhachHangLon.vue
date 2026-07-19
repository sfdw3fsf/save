<template src="./TraCuuKhachHangLon.html"></template>
<style src="./TraCuuKhachHangLon.scss"></style>
<script>
import moment from "moment";
import { mapActions, mapGetters } from "vuex";
import TraCuuKhachHangLonApi from "../api/TraCuuKhachHangLonApi";
import KyCuoc from "../../QLTN/components/KyCuoc.vue";
import xlsx from "xlsx";
import Vue from "vue";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";

export default {
    name: "TraCuuKhachHangLon",
    components: {
        appKyCuoc: KyCuoc
    },
    async created() {
        this.clearData();
    },
    async mounted() {
        if (this.tag) {
            this.kieu = Number.parseInt(this.tag);
        } else {
            this.kieu = 0;
        }

        if (this.kieu == 0) {
            //Tra cứu
            this.btnGhiLaiEnable = false;
        } else {
            this.btnXuatFileEnable = true;
        }
        this.$nextTick(() => this.$refs.maKH.focus());
    },
    data() {
        return {
            header: {
                title: "TRA CỨU KHÁCH HÀNG LỚN",
                list: [
                    {
                        name: "Tra cứu",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Quản lý thu nợ",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Tra cứu khác",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Tra cứu khách hàng lớn",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            btnThemMaTTEnable: true,
            btnXuatFileEnable: true,
            kieu: 0,
            tag: "1",
            maKH: "",
            tenKH: "",
            diaChiKH: "",
            loaiKH: "",
            maTT: "",
            maIG: "",
            tenTT: "",
            dsThanhToan: [],
            khachHang: null,
            checkKyHoaDon: false,
            popupComponent: null,

            kyHoaDon: moment(new Date())
                .subtract(1, "months")
                .endOf("month")
                .toDate(),
            pKyCuoc:
                moment(this.kyHoaDon)
                    .subtract(1, "months")
                    .format("YYYYMM") + "01",
            config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                },
                max_kyhoadon: moment(new Date())
                    .subtract(1, "months")
                    .endOf("month")
                    .toDate()
            },
            fieldDsThanhToan: [
                {
                    fieldName: "MA_KH",
                    headerText: "Mã KH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MATB_DD",
                    headerText: "Mã đại diện",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NO_DK",
                    headerText: "Nợ ĐK",
                    allowFiltering: true,
                    allowSorting: true,
                    isSum: true,
                    cssClass: "text-right",
                    type: "number",
                    format: "N0"
                },
                {
                    fieldName: "NO_PS",
                    headerText: "Nợ PS",
                    allowFiltering: true,
                    allowSorting: true,
                    isSum: true,
                    cssClass: "text-right",
                    type: "number",
                    format: "N0"
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                    allowSorting: true,
                    isSum: true,
                    cssClass: "text-right",
                    type: "number",
                    format: "N0"
                },
                {
                    fieldName: "HINHTHUC_TT",
                    headerText: "Hình thức TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MIG_ID",
                    headerText: "Mã in ghép",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "Địa chỉ thanh toán",
                    allowFiltering: true,
                    allowSorting: true,
                    isSumText: "Tổng cộng"
                },
                {
                    fieldName: "DIACHI_BC",
                    headerText: "Địa chỉ BC",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "Địa chỉ chứng từ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_IG",
                    headerText: "Địa chỉ IG",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MST",
                    headerText: "MST",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "NVTC",
                    headerText: "NVTC",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            fieldDsLienHe: [
                {
                    fieldName: "TEN_LH",
                    headerText: "Người liên hệ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI",
                    headerText: "Địa chỉ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIENTHOAI",
                    headerText: "Điện thoại	",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "FAX",
                    headerText: "Số fax",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "EMAIL",
                    headerText: "Email",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            customTemplate: function() {
                return {
                    template: Vue.component("footerTemplate", {
                        template: `<input type="text" class="form-control tright" v-model="data.Custom" disabled style="margin: 7px 0px 7px 0px;" />`,
                        data() {
                            return { data: { data: {} } };
                        }
                    })
                };
            },
            sumTemplate: function() {
                return {
                    template: Vue.component("sumTemplate", {
                        template: `<span>{{data.Sum}}</span>`,
                        data: function() {
                            return { data: { data: {} } };
                        }
                    })
                };
            },
            sumTotalTemplate: function() {
                return {
                    template: Vue.component("sumTotalTemplate", {
                        template: `<span>Tổng nợ</span>`,
                        data: function() {
                            return { data: { data: {} } };
                        }
                    })
                };
            },
            isCountSelected: 1,
            isTotal: 0,
            totalElementDsThanhToan: 0
        };
    },
    updated: function() {},
    computed: {
        ...mapGetters("TraCuuKhachHangLon", [
            "danhSachThanhToanGetter",
            "danhSachLienHeGetter"
        ]),
        khachHangId() {
            return this.khachHang ? this.khachHang.KHACHHANG_ID : 0;
        },
        PTAggregatesColumns() {
            return [
                {
                    field: "MA_KH",
                    type: "Custom",
                    footerTemplate: this.customTemplate,
                    customAggregateFn: this.customAggregate
                },
                {
                    field: "NO_DK",
                    type: "Sum",
                    format: "N0",
                    footerTemplate: this.sumTemplate
                },
                {
                    field: "NO_PS",
                    type: "Sum",
                    format: "N0",
                    footerTemplate: this.sumTemplate
                },
                {
                    field: "TONGNO",
                    type: "Sum",
                    format: "N0",
                    footerTemplate: this.sumTemplate
                },
                {
                    field: "DIACHI_TT",
                    type: "Custom",
                    footerTemplate: this.sumTotalTemplate
                }
            ];
        }
    },
    watch: {
        danhSachThanhToanGetter: {
            handler: function(value) {
                this.isTotal = value.length;
            }
        }
    },
    methods: {
        ...mapActions("TraCuuKhachHangLon", [
            "traCuuKHThanhToan",
            "getDanhSachLienHe",
            "getDSThanhToanTheoMaIG",
            "clearData"
        ]),
        gridChanged: async function(args) {
            console.log({ args });
            this.traCuu(args.pageIndex, args.pageSize);
        },
        // handleKyCuoc(e) {
        // 	this.pKyCuoc = moment(e.p_kyhoadon).format("yyyyMM") + e.p_chuky_no;
        // },
        customAggregate() {
            return `${this.isCountSelected}/${this.isTotal}`;
        },

        onKyHoaDonChange: function(args) {
            this.pKyCuoc = moment(args.value).format("YYYYMM") + "01";
            if (args.value === null) {
                this.$root.toastError("Vui lòng chọn Kỳ hóa đơn");
                return;
            }
        },
        async fetchData() {
            await this.traCuuKhachHang();
        },

        async traCuu(page, size) {
            this.dsThanhToan = [];
            let data = {
                pPhanVungId: this.$auth.getPhanVungID(),
                pKhachHangId: this.khachHang ? this.khachHang.KHACHHANG_ID : "",
                pKyCuoc: this.pKyCuoc,
                pageNo: page ? page : 0,
                pageSize: size ? size : 10
            };
            const res = await this.getDSThanhToanTheoMaIG(data);
            if (res.data.error_code == "BSS-00000000") {
                this.totalElementDsThanhToan = res.data.data.totalElement;
            }
            console.log({ res: res });
        },

        async traCuuKhachHang() {
            if (this.maKH === "" && this.maIG === "") {
                this.$root.toastError(
                    "Vui lòng nhập mã khách hàng hoặc mã in ghép!"
                );
                this.$refs.maKH.focus();
                return;
            }
            this.loading(true);
            try {
                let response;
                if (this.maKH !== "") {
                    const dataMaKH = {
                        pPhanVungId: this.$auth.getPhanVungID(),
                        pMaKH: this.maKH
                        // pMaKH: "HPG-000000642",
                    };
                    response = await TraCuuKhachHangLonApi.traCuuKhachHangTheoMa(
                        this.axios,
                        dataMaKH
                    );
                } else {
                    const dataMaIG = {
                        pPhanVungId: this.$auth.getPhanVungID(),
                        pMainGhep: this.maIG
                        // pMainGhep: "QTO1.0.00002",
                    };
                    response = await TraCuuKhachHangLonApi.traCuuKhachHangTheoMaInGhep(
                        this.axios,
                        dataMaIG
                    );
                }

                if (response.data.error_code === "BSS-00000000") {
                    if (response.data.data && response.data.data.length > 0) {
                        this.khachHang = response.data.data[0];
                        this.maKH = this.khachHang.MA_KH;
                        this.tenKH = this.khachHang.TEN_KH;
                        this.diaChiKH = this.khachHang.DIACHI_KH;
                        this.loaiKH = this.khachHang.LOAI_KHLON;
                        this.maTT = this.khachHang.MA_TT;
                        await Promise.all([
                            this.traCuu(0, 10),
                            this.getDanhSachLienHe(
                                this.khachHang
                                    ? this.khachHang.KHACHHANG_ID
                                    : ""
                            )
                        ]);
                    } else {
                        this.$root.toastError(
                            "Không tìm thấy thông tin danh bạ!"
                        );
                        this.clearData();
                        this.khachHang = null;
                        this.maKH = "";
                        this.tenKH = "";
                        this.diaChiKH = "";
                        this.loaiKH = "";
                        this.maTT = "";
                    }
                } else {
                    // this.$root.toastError(response.data.data.message);
                }
            } catch (e) {
                console.log(e);
                this.$root.toastError(e.data.message);
            } finally {
                this.loading(false);
            }
        },

        onPhieuThanhToanChange(dataItem) {
            this.listIdMaTT = dataItem;
            this.isCountSelected = dataItem.length;
            this.$refs.dsKHLon.refreshAggregates();
        },

        // onPhieuThanhToanRowChange(dataItem) {
        // 	// this.listIdMaTT = dataItem;
        // 	console.log(dataItem);
        // },

        onExcelExport() {
            if (this.danhSachThanhToanGetter.length === 0) {
                this.$toast.error("Chưa có danh sách để xuất excel!");
            } else {
                var wb = xlsx.utils.book_new();
                if (this.danhSachThanhToanGetter.length > 0) {
                    const dsIn = this.danhSachThanhToanGetter.filter(
                        itemNewVal =>
                            this.listIdMaTT.some(
                                itemOldVal => itemNewVal.MA_TB === itemOldVal
                            )
                    );
                    if (dsIn.length === 0) {
                        this.$toast.error("Vui lòng chọn hàng phiếu muốn in!");
                    } else {
                        this.appendSheet(dsIn, 0, wb);
                    }
                }
                xlsx.writeFile(wb, "Danh-sach-phieu-thanh-toan.xlsx");
            }
        },

        appendSheet(items, type, workbook) {
            this.loading(true);
            try {
                let sheetTitle = "Danh sách phiếu";
                var ds = xlsx.utils.json_to_sheet(items);

                xlsx.utils.book_append_sheet(workbook, ds, sheetTitle);
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },

        onShowSearchAccount() {
            // this.$router.push({ name: "account" });
            this.popupComponent = SearchAccount;
            this.Popup("popupComponents");
        },
        Popup(modalId) {
            this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
        },
        searchAccountClosed(val) {
            if(val) {
                this.maKH = val.ma_kh;
                this.traCuuKhachHang();
            }
            this.ClosePopup("popupComponents");
        },
        setDSMaTTLoi(ds) {
            this.dsMaTTLoi = ds || [];
            this.$bvModal.show("popup-matt-loi");
        }
    }
};
</script>
