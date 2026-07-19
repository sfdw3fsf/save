<template src="./TraCuuKhachHangLon.html"></template>
<style src="./TraCuuKhachHangLon.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapGetters } from "vuex";
import TraCuuKhachHangLonApi from "../api/TraCuuKhachHangLonApi";
import KyCuoc from "../utils/KyCuoc.vue";
import CapNhapTTLH from "./popup/CapNhapTTLH.vue";
import ThemXoaMaTT from "./popup/ThemXoaMaTT.vue";
import DocFile from "./popup/DocFile.vue";
import CapNhapMaInGhep from "./popup/CapNhapMaInGhep.vue";
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import YesNoModal from "../utils/YesNoModal.vue";
import XuatFile from "@/modules/QLTN/KhoaMayTheoDanhSach_ver3/xuat-du-lieu";
import CSKHGrid from "../utils/CSKHGrid";
import {formatString, isNullOrEmpty} from "../utils/functions/validators.js"
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import BssRequiredMarker from '@/components/BssRequiredMarker'
import Vue from "vue"
export default {
    name: "TraCuuKhachHangLon",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        appCapNhapTTLH: CapNhapTTLH,
        appThemXoaMaTT: ThemXoaMaTT,
        appDocFile: DocFile,
        appCapNhapMaInGhep: CapNhapMaInGhep,
        datagrid: CustomDataGrid,
        appYesNoModal: YesNoModal,
        appModalXuatDuLieu: XuatFile,
        CSKHGrid,
        SearchAccount,
        BssRequiredMarker
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
            this.btnThemMaTTEnable = false;
            this.btnNhapExcelEnable = false;
            //tsbtnXuatFile.Visible = false;
            this.btnCapNhatMIGEnable = false;
            this.btnGoMIGEnable = false;
        } else {
            this.btnGhiLaiEnable = true;
            this.btnThemMaTTEnable = true;
            this.btnNhapExcelEnable = true;
            this.btnXuatFileEnable = true;
            this.btnCapNhatMIGEnable = true;
            this.btnGoMIGEnable = true;
        }
        this.$nextTick(() => this.$refs.maKH.focus());
    },
    data() {
        return {
            header: {
                title: "TRA CỨU KHÁCH HÀNG LỚN",
                list: [
                    //   { name: "Trang chủ", link: { name: "Ui.cards" } },
                    {
                        name: "Tra cứu",
                        link: { name: "Ui.buttons" },
                    },
                    {
                        name: "Tra cứu khách hàng lớn",
                        link: { name: "Ui.buttons" },
                    },
                ],
            },
            btnGhiLaiEnable: true,
            btnThemMaTTEnable: true,
            btnNhapExcelEnable: true,
            btnXuatFileEnable: true,
            btnCapNhatMIGEnable: true,
            btnGoMIGEnable: true,
            kieu: 0,
            tag: "1",
            maKH: "",
            maIG: "",
            tenKH: "",
            diaChiKH: "",
            loaiKH: "",
            maTT: "",
            tenTT: "",
            dsThanhToan: [],
            pKyCuoc: "",
            khachHang: null,
            dsLienHe: [],
            phieuThanhToan: null,
            isEnableKyCuoc: true,
            fieldDsThanhToan: [
                { fieldName: "MA_KH", headerText: "Mã KH", allowFiltering: true, allowSorting: false, },
                { fieldName: "MA_TT", headerText: "Mã TT", allowFiltering: true, allowSorting: false, },
                { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: false, },
                 { fieldName: "MATB_DD", headerText: "Mã đại diện", allowFiltering: true, allowSorting: false, },
                 { fieldName: "TEN_TT", headerText: "Tên thanh toán", allowFiltering: true, allowSorting: false, },
                 { fieldName: "NO_DK", headerText: "Nợ ĐK", allowFiltering: true, allowSorting: false, isSum: true, cellCssClass: 'no-dk', width: 200, },
                 { fieldName: "NO_PS", headerText: "Nợ PS", allowFiltering: true, allowSorting: false, isSum: true, cellCssClass: 'no-ps', width: 200, },
                 { fieldName: "TONG_NO", headerText: "Tổng nợ", allowFiltering: true, allowSorting: false, isSum: true, cellCssClass: 'no-tong', width: 200, },
                 { fieldName: "HINHTHUC_TT", headerText: "Hình thức TT", allowFiltering: true, allowSorting: false, },
                 { fieldName: "MAIN_GHEP", headerText: "Mã in ghép", allowFiltering: true, allowSorting: false, },
                 { fieldName: "DIACHI_TT", headerText: "Địa chỉ thanh toán", allowFiltering: true, allowSorting: false, isSumText: "Tổng cộng", },
                 { fieldName: "DIACHI_BC", headerText: "Địa chỉ BC", allowFiltering: true, allowSorting: false, },
                 { fieldName: "DIACHI_CT", headerText: "Địa chỉ chứng từ", allowFiltering: true, allowSorting: false, },
                 { fieldName: "DIACHI_IG", headerText: "Địa chỉ IG", allowFiltering: true, allowSorting: false, },
                 { fieldName: "MST", headerText: "MST", allowFiltering: true, allowSorting: false, },
                 { fieldName: "NVTC", headerText: "NVTC", allowFiltering: true, allowSorting: false, },
                 { fieldName: "GHICHU", headerText: "Ghi chú", allowFiltering: true, allowSorting: false, }
            ],
            fieldDsLienHe: [
                {
                    fieldName: "TEN_LH",
                    headerText: "Người liên hệ",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "DIACHI",
                    headerText: "Địa chỉ",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "DIENTHOAI",
                    headerText: "Điện thoại	",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "FAX",
                    headerText: "Số fax",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "EMAIL",
                    headerText: "Email",
                    allowFiltering: true,
                    allowSorting: false,
                },
            ],
            fielDsXuatFile: [
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "NO_DK",
                    headerText: "NO_DK",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "NO_PS",
                    headerText: "NO_PS",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "TONG_NO",
                    headerText: "TONG_NO",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "THANHTOAN_ID",
                    headerText: "THANHTOAN_ID",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MA_TT",
                    headerText: "MA_TT",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "TEN_TT",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MA_TB",
                    headerText: "MA_TB",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "DIACHI_TT",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "DIACHI_BC",
                    headerText: "DIACHI_BC",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MAIN_GHEP",
                    headerText: "MAIN_GHEP",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "GHICHU",
                    headerText: "GHICHU",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "HINHTHUC_TT",
                    headerText: "HINHTHUC_TT",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MATB_DD",
                    headerText: "MATB_DD",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "DIACHI_IG",
                    headerText: "DIACHI_IG",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MIG_ID",
                    headerText: "MIG_ID",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MA_KH",
                    headerText: "MA_KH",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "DIACHI_CT",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "MST",
                    headerText: "MST",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "TUYETTHU_ID",
                    headerText: "TUYETTHU_ID",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "NVTC",
                    headerText: "NVTC",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "CHON",
                    headerText: "CHON",
                    allowFiltering: true,
                    allowSorting: false,
                    isCheckbox: true
                },
            ],
            dataTest: [],
            dsMaTTLoi: [],
            pDsLienHe: [],
            fieldDsMaTTLoi: [
                {
                    fieldName: "MA_TT",
                    headerText: "Mã thanh toán",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "LYDO_LOI",
                    headerText: "Lý do lỗi",
                    allowFiltering: true,
                    allowSorting: false,
                },
            ],
            thanhToanId: 0,
            API_SUCCESS: "BSS-00000000",
            dgvThanhToan: {
                columns: [
                    { fieldName: "MA_KH", headerText: "Mã KH", allowFiltering: true, allowSorting: false, },
                    { fieldName: "MA_TT", headerText: "Mã TT", allowFiltering: true, allowSorting: false, },
                    { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: false, },
                    { fieldName: "MATB_DD", headerText: "Mã đại diện", allowFiltering: true, allowSorting: false, },
                    { fieldName: "TEN_TT", headerText: "Tên thanh toán", allowFiltering: true, allowSorting: false, },
                    { fieldName: "NO_DK", headerText: "Nợ ĐK", allowFiltering: true, allowSorting: false, format: 'N0', type: 'number', cssClass: 'no-dk', width: 200, },
                    { fieldName: "NO_PS", headerText: "Nợ PS", allowFiltering: true, allowSorting: false, format: 'N0', type: 'number', cssClass: 'no-ps', width: 200, },
                    { fieldName: "TONG_NO", headerText: "Tổng nợ", allowFiltering: true, allowSorting: false, format: 'N0', type: 'number', cssClass: 'no-tong', width: 200, },
                    { fieldName: "HINHTHUC_TT", headerText: "Hình thức TT", allowFiltering: true, allowSorting: false, },
                    { fieldName: "MAIN_GHEP", headerText: "Mã in ghép", allowFiltering: true, allowSorting: false, cssClass: 'ma-in-ghep',},
                    { fieldName: "DIACHI_TT", headerText: "Địa chỉ thanh toán", allowFiltering: true, allowSorting: false, isSumText: "Tổng cộng", },
                    { fieldName: "DIACHI_BC", headerText: "Địa chỉ BC", allowFiltering: true, allowSorting: false, },
                    { fieldName: "DIACHI_CT", headerText: "Địa chỉ chứng từ", allowFiltering: true, allowSorting: false, },
                    { fieldName: "DIACHI_IG", headerText: "Địa chỉ IG", allowFiltering: true, allowSorting: false, },
                    { fieldName: "MST", headerText: "MST", allowFiltering: true, allowSorting: false, },
                    { fieldName: "NVTC", headerText: "NVTC", allowFiltering: true, allowSorting: false, },
                    { fieldName: "GHICHU", headerText: "Ghi chú", allowFiltering: true, allowSorting: false, }
                ],
                dataSource: [],
                selectionOptions: { type: 'Multiple', checkboxOnly: true },
                selectedRecord: null,
            },

            sumTemplate: function() {
                return { template: Vue.component('sumTemplate', {
                    template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                    data: function () {return {data: {data: {}}};}
                })
                }
            },

            tongCongTemplate: function() {
                return { template: Vue.component('tongCongTemplate', {
                    template: `<input type="text" class="form-control tright" value="Tổng cộng" :disabled="true" style="margin: 7px 0px 7px 0px;text-align: right;" />`,
                    data: function () {return {data: {data: {}}};}
                })
                }
            },

            globalParams: {
                khachHangId: 0,
                maInGhep: '',
                maInGhepId: 0,
            },

            popupComponent: null,
        };
    },
    computed: {
        ...mapGetters("tracuukhachhanglon", [
            "dsThanhToanGetter",
            "dsLienHeGetter",
        ]),
        // khachHangId() {
        //     return this.khachHang ? this.khachHang.KHACHHANG_ID : 0;
        // },
        // maInGhep() {
        //     return this.phieuThanhToan ? this.phieuThanhToan.MAIN_GHEP : "";
        // },
        // maInGhepId() {
        //     return this.phieuThanhToan ? this.phieuThanhToan.MIG_ID : 0;
        // },
        LGAggregatesColumns () {
            return [
                { field: 'NO_DK', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
                { field: 'NO_PS', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
                { field: 'TONG_NO', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
                { field: 'DIACHI_TT', type: 'Sum', format: 'N0', footerTemplate: this.tongCongTemplate }
            ]
        },
        dsThuocTinh(){
            return this.fielDsXuatFile.map(item=>item.fieldName)
        }
    },
    watch: {},
    methods: {
        ...mapActions("tracuukhachhanglon", [
            "traCuuThanhToan",
            "getDsLienHe",
            "clearData",
        ]),
        handleKyCuoc(e) {
            this.pKyCuoc = moment(e.p_kyhoadon).format("MM/YYYY"); //+ e.p_chuky_no;
        },
        async fetchData() {
            if (!this.maKH.trim() && !this.maIG.trim()) {
                this.$root.toastError("Hãy nhập dữ liệu tìm kiếm")
                return
            }

            await this.traCuuKhachHang();
        },
        async traCuu() {
            this.dsThanhToan = [];
            let data = {
                khachHangId: this.globalParams.khachHangId,
                kyCuoc: this.pKyCuoc,
                pageNo: 1,
                pageSize: 10000,
            };
            await this.traCuuThanhToan(data);
        },
        async traCuuLienHe() {
            this.dsLienHe = [];
            TraCuuKhachHangLonApi.traCuuLienHe(
                this.axios,
                this.globalParams.khachHangId
            )
                .then((response) => {
                    console.log(response);
                    if (response.data.error_code === "BSS-00000000") {
                        if (
                            response.data.data &&
                            response.data.data.data &&
                            response.data.data.data.length > 0
                        ) {
                            this.dsLienHe = response.data.data.data;
                        } else {
                            this.$root.toastError(
                                "Không tìm thấy thông tin liên hệ!"
                            );
                        }
                    } else {
                        this.$root.toastError(response.data.message);
                    }
                })
                .catch((reject) => {
                    console.log(reject);
                    if (
                        reject.response &&
                        reject.response.data &&
                        reject.response.data.message
                    )
                        this.$root.toastError(reject.response.data.message);
                    else this.$root.toastError("Có lỗi xảy ra!");
                });
        },
        async traCuuKhacHangHandler() {
            await this.traCuu()
            await this.getDsLienHe(this.globalParams.khachHangId)
        },
        async traCuuKhachHang() {
            if (this.maKH.trim() || this.maIG.trim()) {
                try {
                    this.loading(true);
                    console.log('url', "/web-cskh/api/tra-cuu/khach-hang/" + (isNullOrEmpty(this.maKH) ? this.maIG.trim() : this.maKH.trim()))
                    let responseTraCuu = await this.$root.context.get("/web-cskh/api/tra-cuu/khach-hang/" + (isNullOrEmpty(this.maKH) ? this.maIG.trim() : this.maKH.trim()))
                    if (responseTraCuu.error_code === this.API_SUCCESS) {
                        let rs = responseTraCuu.data || []
                        if (rs.length > 0) {
                            this.globalParams.khachHangId = rs[0].KHACHHANG_ID;
                            this.maKH = rs[0].MA_KH;
                            //this.maIG = khachHang.
                            this.tenKH = rs[0].TEN_KH;
                            this.diaChiKH = rs[0].DIACHI_KH;
                            this.loaiKH = rs[0].LOAI_KHLON;

                            await this.traCuuKhacHangHandler();

                            this.$nextTick(() => {
                                if (this.dsThanhToanGetter.length > 0) {
                                    this.$refs.gridDsThanhToan.setCurrentSelectedRow(0)
                                }
                            })
                        }
                        else {
                            this.$root.toastError( "Không tìm thấy thông tin danh bạ!" );
                            let maKH = this.maKH
                            this.clear();
                            this.maKH = maKH
                            return
                        }
                    }
                    else {
                        this.$root.toastError( "Có lỗi xảy ra: " + responseTraCuu.message );
                    }
                }
                catch (e) {
                    console.log(e)
                    this.$root.toastError( "Có lỗi xảy ra: " + e.data ? e.data.message : e);
                }
                finally {
                    this.loading(false);
                }
            }

            // if (this.maKH) {
            //     TraCuuKhachHangLonApi.traCuuKHTheoMa(this.axios, this.maKH)
            //         .then(async (response) => {
            //             if (response.data.error_code === "BSS-00000000") {
            //                 if (
            //                     response.data.data &&
            //                     response.data.data.length > 0
            //                 ) {
            //                     this.khachHang = response.data.data[0];
            //                     this.maKH = this.khachHang.MA_KH;
            //                     //this.maIG = khachHang.
            //                     this.tenKH = this.khachHang.TEN_KH;
            //                     this.diaChiKH = this.khachHang.DIACHI_KH;
            //                     this.loaiKH = this.khachHang.LOAI_KHLON;
            //                 } else {
            //                     this.loading(false);
            //                     this.$root.toastError(
            //                         "Không tìm thấy thông tin danh bạ!"
            //                     );
            //                     this.clear();
            //                     return;
            //                 }

            //                 await Promise.all([
            //                     this.traCuu(),
            //                     this.getDsLienHe(
            //                         this.khachHang
            //                             ? this.khachHang.KHACHHANG_ID
            //                             : ""
            //                     ),
            //                 ]);
            //             } else {
            //                 this.$root.toastError(response.data.message);
            //             }
            //             this.loading(false);
            //         })
            //         .catch((reject) => {
            //             console.log(reject);
            //             if (
            //                 reject.response &&
            //                 reject.response.data &&
            //                 reject.response.data.message
            //             )
            //                 this.$root.toastError(reject.response.data.message);
            //             else this.$root.toastError("Có lỗi xảy ra!");
            //             this.loading(false);
            //         });
            // }
        },
        onPhieuThanhToanChange(dataItem) {
            if (dataItem != null && this.dgvThanhToan.selectedRecord == null) {
                this.dgvThanhToan.selectedRecord = dataItem
            }
        },
        onDgvThanhToanRowClicked(index, dataItem) {
            this.maTT = dataItem.MA_TT;
            this.tenTT = dataItem.TEN_TT;
            this.phieuThanhToan = dataItem;
            this.thanhToanId = dataItem.THANHTOAN_ID;
            this.maIG = dataItem.MAIN_GHEP
            this.dgvThanhToan.selectedRecord = dataItem
        },
        setDSMaTTLoi(ds) {
            // this.dsMaTTLoi = ds || [];
            // this.$bvModal.show("popup-matt-loi");
        },
        onMaKHKeyDown(event) {
            if (event.keyCode == 13) {
                this.traCuuKhachHang();
            }
        },
        clear() {
            // this.khachHang = null;
            this.maKH = "";
            //this.maIG = khachHang.
            this.tenKH = "";
            this.diaChiKH = "";
            this.loaiKH = "";
            this.pDsLienHe = []
            this.maTT = ''
            this.tenTT = ''
            this.globalParams = {
                khachHangId: 0,
                maInGhep: '',
                maInGhepId: 0,
            }
            this.clearData();
        },
        onGoMaIGClick() {
            if (this.maKH.trim() === "") {
                this.$root.toastError("Hãy tìm kiếm trước khi gỡ MIG!");
                return;
            }

            if (this.thanhToanId === 0) {
                this.$root.toastError("Bạn chưa chọn mã thanh toán!");
                return;
            }

            if (this.$refs.gridDsThanhToan.getSelectedRecords().length == 0) {
                this.$root.toastError("Bạn chưa chọn phiếu thanh toán")
                return
            }

            this.$bvModal.show("popup-confirmGoMaIG");
        },
        async onGoMaIGConfirm() {
            if (this.$refs.gridDsThanhToan.getSelectedRecords().length > 0) {
                try {
                    this.loading(true);
                    await this.$refs.gridDsThanhToan.getSelectedRecords().forEach(
                        async (element) => {
                            if (element) {
                                try {
                                    let responseDelete =
                                         await this.$root.context.get('/web-cskh/api/mig/go', {thanhToanId: element.THANHTOAN_ID})
                                    if ( responseDelete.error_code === "BSS-00000000" ) {
                                        this.$root.toastSuccess( `Cập nhật mã in ghép cho mã thanh toán ${element.THANHTOAN_ID} thành công! `);
                                        await this.traCuuKhacHangHandler();
                                    } else {
                                        this.$root.toastError( `Cập nhật mã in ghép cho mã thanh toán ${element.THANHTOAN_ID} thất bại! `);
                                    }
                                } catch (e) {
                                    this.$root.toastError( `Cập nhật mã in ghép cho mã thanh toán ${element.THANHTOAN_ID} thất bại! Có lỗi: ${e.message}` );
                                } finally {
                                }
                            }
                        }
                    );

                } catch (e) {
                } finally {
                    this.loading(false);
                }
            }

        },
        onCapNhapTTLHClicked() {
            if (this.globalParams.khachHangId == 0) {
                this.$root.toastError("Bạn hãy nhập mã thuê bao và Enter để tìm kiếm!")
                this.$nextTick(() => this.$refs.maKH.focus());
                this.clear()
                return
            }
            this.$refs.popupCapNhapTTLH.khachHangId = this.globalParams.khachHangId
            this.$refs.popupCapNhapTTLH.openDialog()
            //this.$bvModal.show("popup-capNhapTTLH")
        },
        onCapNhapMaInGhepClicked() {
            if (this.globalParams.khachHangId == 0) {
                this.$root.toastError("Bạn hãy nhập mã thuê bao và Enter để tìm kiếm!")
                this.$nextTick(() => this.$refs.maKH.focus());
                this.clear()
                return
            }
            this.$refs.popupCapNhapMaInGhep.khachHangId = this.globalParams.khachHangId
            this.$refs.popupCapNhapMaInGhep.maKH = this.maKH
            this.$refs.popupCapNhapMaInGhep.tucthi = 0
            this.$refs.popupCapNhapMaInGhep.openDialog()
        },
        onXuatFileClicked() {
            if (this.dsThanhToanGetter.length <= 0) {
                this.$root.toastError("Không có dữ liệu để xuất!")
                return
            }
            this.$bvModal.show('modal-xuat-dulieu')
        },
        onThemXoaTTClicked() {
            if (this.$refs.gridDsThanhToan.getSelectedRecords().length == 0) {
                this.$root.toastError("Bạn chưa chọn phiếu thanh toán")
                return
            }

            let vmig_id = ''
            let vma_ig = ''
            if (this.dsThanhToanGetter.length > 0) {
                vmig_id = this.dgvThanhToan.selectedRecord.MIG_ID
                vma_ig = this.dgvThanhToan.selectedRecord.MAIN_GHEP
            }

            if (this.checkIsNull(vmig_id)) {
                this.$root.toastError("Khách hàng không có mã in ghép")
                return
            }

            this.$refs.popupThemXoaMaTT.maInGhep = vma_ig
            this.$refs.popupThemXoaMaTT.maInGhepId = vmig_id ? vmig_id : 0
            this.$refs.popupThemXoaMaTT.openDialog()
        },
        onDSThanhToanQueryCellInfo(args) {
            if (args.column.field === 'NO_DK') {
                args.cell.classList.add('no-dk');
            }
            else if (args.column.field === 'NO_PS') {
                args.cell.classList.add('no-ps');
            }
            else if (args.column.field === 'TONG_NO') {
                args.cell.classList.add('no-tong');
            }
        },
        checkIsNull: function (value) {
            return value == undefined || value == null;
        },

        async onMaIGKeyPress()
        {
            await this.traCuuKhachHang()
        },

        onDocFileClicked()
        {
            this.$refs.popupDocFile.openDialog()
        },

        ShowSearchAccount() {
            this.popupComponent = SearchAccount;
            SearchAccount.data.isPopup = true;
            this.Popup("popupComponents");
            //this.$refs.SearchAccountComponent.show();
        },
        Popup(modalId) {
            this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
        },
        searchAccountClosed(val) {
            if(val){
              this.maKH = val.ma_kh;
            }
            this.ClosePopup("popupComponents");
        },
    },
};
</script>
