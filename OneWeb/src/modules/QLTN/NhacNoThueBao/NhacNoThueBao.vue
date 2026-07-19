<template src="./NhacNoThueBao.html"></template>
<style src="./NhacNoThueBao.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import xlsx from "xlsx";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";

export default {
    name: "NhacNoThueBao",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        appModalXuatDuLieu: ModalXuatDuLieu
    },
    async created() {
        this.clearData();
        this.fetchData();
    },
    async mounted() {},
    data() {
        return {
            header: {
                title: "Nhắc nợ thuê bao",
                list: [
                    {
                        name: "Xử lý thuê bao",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Nhắc nợ thuê bao",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/y",
                    max: new Date()
                }
            },

            pKyCuoc: "",
            isTab: false,

            formChuaNhac: {
                listNhacNo: [],
                listKhongNhacNo: [],
                selectedDonVi: null,
                selectedDichVu: null,
                selectedLanTaoDichVu: null,
                soLuong: "0",
                tongTien: "0"
            },
            formDaNhac: {
                listDaNhacNo: [],
                selectedDonVi: null,
                selectedDichVu: null,
                selectedLanNhacNo: null,
                selectedTrangThai: 0,
                soLuong: 0,
                tongTien: 0
            },
            listTrangThai: [
                { value: 0, name: "Tất cả" },
                { value: 1, name: "Mới nhắc" },
                { value: 2, name: "Đã nhắc" }
            ],
            dsSelectCu: [],
            dsSelectMoi: [],
            listSelectDaNhac: [],
            fieldNhacNo: [
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã thanh toán",
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
                    fieldName: "MANV_TC",
                    headerText: "Mã NVTC",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "LANNHAC",
                    headerText: "Lần nhắc",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            fieldKhongNhacNo: [
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            fieldDaNhacNo: [
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã thanh toán",
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
                    fieldName: "MANV_TC",
                    headerText: "Mã NVTC",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "LANNHAC",
                    headerText: "Lần nhắc",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TRANGTHAI_TB",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    allowSorting: true
                }
            ]
        };
    },
    watch: {
        "formChuaNhac.selectedDichVu": {
            handler: async function(value) {
                console.log("value", value);
                await this.getLanTaoDuLieu({
                    pKyCuoc: this.pKyCuoc,
                    pDichVuVTId: value
                });
            }
        },
        "formChuaNhac.selectedLanTaoDichVu": {
            handler: async function(value) {
                console.log({ value });
                if (value) {
                    const item = this.listLanTaoDuLieuGetter.filter(
                        item => item.LANTAO_NN_ID === value
                    )[0];
                    this.formChuaNhac.soLuong = item.SOLUONG.toString();
                    this.formChuaNhac.tongTien = item.TONGTIEN.toString();
                    console.log({ soluong: this.formChuaNhac.soLuong });
                } else {
                    this.formChuaNhac.soLuong = "0";
                    this.formChuaNhac.tongTien = "0";
                }
            }
        },
        "formDaNhac.selectedLanNhacNo": {
            handler: async function(value) {
                console.log({ value });
                if (value) {
                    const item = this.listLanNhacNoGetter.filter(
                        item => item.GIAOPHIEU_NN_ID === value
                    )[0];
                    this.formDaNhac.soLuong = item.SOLUONG_TB.toString();
                    this.formDaNhac.tongTien = item.TONGTIEN.toString();
                } else {
                    this.formDaNhac.soLuong = 0;
                    this.formDaNhac.tongTien = 0;
                }
            }
        },
        "formDaNhac.selectedDichVu": {
            handler: async function(value) {
                console.log("value", value);
                await this.getLanNhacNo({
                    pKyCuoc: this.pKyCuoc,
                    pDichVuVTId: value
                });
            }
        },
        listDonViGetter: function(newValue) {
            if (newValue.length > 0) {
                this.formChuaNhac.selectedDonVi = -1;
                this.formDaNhac.selectedDonVi = -1;
            }
        },
        listDichVuVTGetter: async function(newValue) {
            if (newValue.length > 0) {
                this.formChuaNhac.selectedDichVu = newValue[0].DICHVUVT_ID;
                this.formDaNhac.selectedDichVu = newValue[0].DICHVUVT_ID;
            }
        },
        listLanTaoDuLieuGetter: function(newValue) {
            if (newValue.length > 0) {
                this.formChuaNhac.selectedLanTaoDichVu =
                    newValue[0].LANTAO_NN_ID;
                this.formChuaNhac.soLuong = newValue[0].SOLUONG;
            } else {
                this.formChuaNhac.selectedLanTaoDichVu = null;
            }
        },
        listLanNhacNoGetter: function(newValue) {
            if (newValue.length > 0) {
                this.formDaNhac.selectedLanNhacNo = newValue[0].GIAOPHIEU_NN_ID;
            } else {
                this.formDaNhac.selectedLanNhacNo = null;
            }
        }
    },
    computed: {
        ...mapGetters("NhacNoThueBao", [
            "listDonViGetter",
            "listDichVuVTGetter",
            "listLanTaoDuLieuGetter",
            "listLanNhacNoGetter"
        ]),
        dsThuocTinhNhacNo() {
            return this.fieldNhacNo.map(x => x.fieldName);
        },
        dsThuocTinhDaNhacNo() {
            return this.fieldDaNhacNo.map(x => x.fieldName);
        },
        tongTienChuaNhac: {
            get() {
                // let tongTien = 0;
                // this.formChuaNhac.listNhacNo.forEach(item => {
                //     tongTien += parseInt(item.TONGNO);
                // });
                let value = this.formChuaNhac.tongTien.toString().split(".");
                let decimal =
                    typeof value[1] !== "undefined" ? "." + value[1] : "";
                return Number(value[0]).toLocaleString("en-GB") + decimal;
            },

            set(newValue) {
                this.formChuaNhac.tongTien = newValue.replace(/,/g, "");
            }
        }
    },
    methods: {
        ...mapActions("NhacNoThueBao", [
            "getDonVi",
            "getDichVuVT",
            "getLanTaoDuLieu",
            "getLanNhacNo",
            "getListChuaNhacNo",
            "getListNhacNo",
            "getListDaNhacNo",
            "chuyenNhacNoAction",
            "huyNhacNoAction",
            "huyLanNhacAction",
            "capNhatNhacNoAction",
            "clearData"
        ]),

        handleKyCuoc(e) {
            this.pKyCuoc = moment(e.p_kyhoadon).format("yyyyMM") + "01";
            console.log(this.pKyCuoc);
            if (!this.isTab) {
                this.getLanTaoDuLieu({
                    pKyCuoc: this.pKyCuoc,
                    pDichVuVTId: this.formChuaNhac.selectedDichVu
                });
            } else {
                this.getLanNhacNo({
                    pKyCuoc: this.pKyCuoc,
                    pDichVuVTId: this.formDaNhac.selectedDichVu
                });
            }
        },

        async fetchData() {
            await this.getList();
        },

        async getList() {
            this.loading(true);
            let data = {
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID()
            };
            console.log(data);

            try {
                await Promise.all([this.getDonVi(data), this.getDichVuVT()]);
            } catch (e) {
                console.log(e);
                // this.$root.toastError(e.data.message);
            } finally {
                this.loading(false);
            }
        },

        getDanhSach() {
            // this.getDSNhacNo();
            if (!this.isTab) {
                this.getDSChuaNhacNo();
            } else {
                this.getDSDaNhacNo();
            }
        },

        async getDSNhacNo() {
            this.loading(true);

            let data = {
                kyCuoc: this.pKyCuoc,
                kieuNN: !this.isTab ? 2 : 1,
                lanTaoId: !this.isTab
                    ? this.formChuaNhac.selectedLanTaoDichVu
                    : this.formDaNhac.selectedLanNhacNo
            };
            console.log({ data });
            try {
                const response = await this.getListNhacNo(data);
                if (response.data.error_code === "BSS-00000000") {
                    if (!this.isTab) {
                        this.formChuaNhac.listNhacNo = response.data.data;
                    } else {
                        this.formDaNhac.listDaNhacNo = response.data.data;
                    }
                    this.$root.toastSuccess("Lấy danh sách thành công");
                }
                console.log({ res: response.data.data });
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        async getDSChuaNhacNo() {
            if (!this.formChuaNhac.selectedLanTaoDichVu) {
                this.$root.toastError("Chưa có lần tạo dữ liệu!");
                this.formChuaNhac.listNhacNo = [];
                return;
            }
            let data = {
                pKyCuoc: this.pKyCuoc,
                pLanTaoNNId: this.formChuaNhac.selectedLanTaoDichVu
            };
            try {
                this.loading(true);
                const response = await this.getListChuaNhacNo(data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length > 0
                ) {
                    this.formChuaNhac.listNhacNo = response.data.data;
                    this.formChuaNhac.listKhongNhacNo = [];

                    this.$root.toastSuccess("Lấy danh sách thành công");
                } else {
                    this.$root.toastError(
                        "Điều kiện không thỏa mãn để lấy danh sách"
                    );
                    this.formChuaNhac.listNhacNo = [];
                    this.formChuaNhac.listKhongNhacNo = [];
                }
                console.log({ res: response.data.data });
            } catch (e) {
                this.$root.toastError(
                    "Điều kiện không thỏa mãn để lấy danh sách"
                );
                console.log(e);
                // this.$root.toastError(e.data.message);
            } finally {
                this.loading(false);
            }
        },

        async getDSDaNhacNo() {
            if (!this.formDaNhac.selectedLanNhacNo) {
                this.$root.toastError("Chưa có dữ liệu lần nhắc!");
                this.formDaNhac.listDaNhacNo = [];
                return;
            }
            let data = {
                pKyCuoc: this.pKyCuoc,
                pTrangThai: this.formDaNhac.selectedTrangThai,
                pGiaoPhieuNNId: this.formDaNhac.selectedLanNhacNo
            };

            try {
                this.loading(true);
                const response = await this.getListDaNhacNo(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.formDaNhac.listDaNhacNo = response.data.data;
                    this.$root.toastSuccess("Lấy danh sách thành công");
                }
                console.log({ res: response.data.data });
            } catch (e) {
                console.log(e);
                // this.$root.toastError(e.data.message);
            } finally {
                this.loading(false);
            }
        },

        move() {
            let arr = [];
            this.dsSelectCu.map(val => {
                let idx = this.formChuaNhac.listNhacNo.findIndex(
                    v => v.MA_TB == val
                );
                if (idx >= 0) {
                    arr.push(this.formChuaNhac.listNhacNo[idx]);
                    this.formChuaNhac.listNhacNo = this.formChuaNhac.listNhacNo.filter(
                        v => v.MA_TB != val
                    );
                }
            });
            this.formChuaNhac.listKhongNhacNo = this.formChuaNhac.listKhongNhacNo.concat(
                arr
            );
            this.dsSelectMoi = [];
        },

        moveAll() {
            this.formChuaNhac.listKhongNhacNo = this.formChuaNhac.listKhongNhacNo.concat(
                this.formChuaNhac.listNhacNo
            );
            this.formChuaNhac.listNhacNo = [];
            this.dsSelectMoi = [];
        },

        unmove() {
            let arr = [];
            this.dsSelectMoi.map(val => {
                let idx = this.formChuaNhac.listKhongNhacNo.findIndex(
                    v => v.MA_TB == val
                );
                if (idx >= 0) {
                    arr.push(this.formChuaNhac.listKhongNhacNo[idx]);
                    this.formChuaNhac.listKhongNhacNo = this.formChuaNhac.listKhongNhacNo.filter(
                        v => v.MA_TB != val
                    );
                }
            });
            this.formChuaNhac.listNhacNo = this.formChuaNhac.listNhacNo.concat(
                arr
            );
            this.dsSelectCu = [];
        },

        unmoveAll() {
            this.formChuaNhac.listNhacNo = this.formChuaNhac.listNhacNo.concat(
                this.formChuaNhac.listKhongNhacNo
            );
            this.formChuaNhac.listKhongNhacNo = [];
            this.dsSelectCu = [];
        },

        selectedItemsChangedCu(data) {
            this.dsSelectCu = data;
        },

        selectedRowChangedCu(data) {
            console.log("dRowChanged", data);
        },

        selectedItemsChangedMoi(data) {
            this.dsSelectMoi = data;
        },

        selectedRowChangedMoi(data) {
            console.log("dRowChanged", data);
        },

        selectedItemsChangedDaNhac(data) {
            this.listSelectDaNhac = data;
            console.log("dRowChanged", data);
        },

        selectedRowChangedDaNhac(data) {
            console.log("dRowChanged", data);
        },

        nhacNo() {
            console.log({ dataselectcu: this.dsSelectCu });
            console.log({ listnhac: this.formChuaNhac.listNhacNo });

            if (this.formChuaNhac.listNhacNo.length === 0) {
                this.$root.toastError("Chưa có dữ liệu để chuyển!!");
                return;
            }
            this.$confirm(
                `Bạn có chắc chắn muốn chuyển danh sách sang nhắc nợ?`,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không"
                }
            )
                .then(() => {
                    this.chuyenNhacNo();
                })
                .catch(() => {});
        },

        async chuyenNhacNo() {
            let tb = [];
            this.formChuaNhac.listKhongNhacNo.forEach(item => {
                tb.push(item.MA_TB);
            });
            const data = {
                pLanTaoNNId: this.formChuaNhac.selectedLanTaoDichVu,
                pDSMaTBKhongNhac: tb.toString(),
                pKyCuoc: this.pKyCuoc,
                pLanTaoId: this.formChuaNhac.selectedLanTaoDichVu,
                pKieu: 0,
                pSoLuongTB: this.formChuaNhac.soLuong,
                pTongTien: this.formChuaNhac.tongTien,
                pNguoiCN: this.$auth.getUserName(),
                pMayCN: "chinhtn"
            };
            console.log({ data });
            try {
                this.loading(true);
                const response = await this.chuyenNhacNoAction(data);
                console.log({ response });
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Cập nhật danh sách nhắc nợ thành công."
                    );
                    await this.getDSNhacNo();
                    this.formChuaNhac.listKhongNhacNo = [];
                    // this.unmoveAll();
                } else {
                    this.$root.toastError(response.message);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        huyNhacNo() {
            if (this.formDaNhac.listDaNhacNo.length === 0) {
                this.$root.toastError("Chưa có dữ liệu để hủy!");
                return;
            }
            if (this.listSelectDaNhac.length === 0) {
                this.$root.toastError("Bạn chưa chọn thuê bao để hủy!");
                return;
            }
            if (this.formDaNhac.selectedTrangThai === 2) {
                this.$root.toastError(
                    `Thuê bao ${this.listSelectDaNhac.toString()} đã hoàn thành nhắc nợ. Bạn không thể hủy nhắc!`
                );
                return;
            }
            this.$confirm(
                `Bạn có muốn hủy nhắc ${this.listSelectDaNhac.length} phiếu không?`,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không"
                }
            )
                .then(() => {
                    this.huyNhacNoHandling();
                })
                .catch(() => {});
        },

        async huyNhacNoHandling() {
            const data = {
                pKyCuoc: this.pKyCuoc,
                pGiaoPhieuNNId: this.formDaNhac.selectedLanNhacNo,
                pDichVuVTId: this.formDaNhac.selectedDichVu,
                pDSMaTB: this.listSelectDaNhac.toString()
            };
            console.log({ data });
            try {
                this.loading(true);
                const response = await this.huyNhacNoAction(data);
                console.log({ response });
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Hủy danh sách nhắc nợ thành công."
                    );
                    await this.getDSDaNhacNo();
                } else {
                    this.$root.toastError(response.message);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        huyLanNhac() {
            if (!this.formDaNhac.selectedLanNhacNo) {
                this.$root.toastError("Không có lần nhắc để xóa.");
                return;
            }
            const ngayGiao = this.listLanNhacNoGetter.filter(
                item =>
                    item.GIAOPHIEU_NN_ID === this.formDaNhac.selectedLanNhacNo
            )[0].NGAYGIAO;
            console.log({ ngayGiao });
            this.$confirm(
                `Bạn có chắc chắn muốn xóa lần nhắc ${ngayGiao} ?`,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không"
                }
            )
                .then(() => {
                    this.huyLanNhacHandling();
                })
                .catch(() => {});
        },

        async huyLanNhacHandling() {
            const data = {
                pKyCuoc: this.pKyCuoc,
                pGiaoPhieuNNId: this.formDaNhac.selectedLanNhacNo
            };
            console.log({ data });
            try {
                this.loading(true);
                const response = await this.huyLanNhacAction(data);
                console.log({ response });
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess("Hủy lần nhắc nợ thành công.");
                    this.getLanNhacNo({
                        pKyCuoc: this.pKyCuoc,
                        pDichVuVTId: this.formDaNhac.selectedDichVu
                    });
                } else {
                    this.$root.toastError(response.message);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        capNhat() {
            if (!this.formDaNhac.selectedLanNhacNo) {
                this.$root.toastError("Không có lần nhắc để cập nhât!");
                return;
            }
            const ngayGiao = this.listLanNhacNoGetter.filter(
                item =>
                    item.GIAOPHIEU_NN_ID === this.formDaNhac.selectedLanNhacNo
            )[0].NGAYGIAO;
            this.$confirm(
                `Bạn có chắc chắn muốn cập nhật trạng thái hoàn thành cho lần nhắc ${ngayGiao} không?`,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không"
                }
            )
                .then(() => {
                    this.capNhatNhacNo();
                })
                .catch(() => {});
        },

        async capNhatNhacNo() {
            const data = {
                pKyCuoc: this.pKyCuoc,
                pTrangThai: this.formDaNhac.selectedTrangThai
            };
            console.log({ data });
            try {
                this.loading(true);
                const response = await this.capNhatNhacNoAction(data);
                console.log({ response });
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        "Cập nhật trạng thái lần nhắc nợ thành công."
                    );
                    // await this.getDSNhacNo();
                } else {
                    this.$root.toastError(response.message);
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        onExcelExport() {
            if (this.listThanhLyThueBaoGetter.length === 0) {
                this.$root.toastError("Chưa có danh sách để xuất excel!");
            } else {
                var wb = xlsx.utils.book_new();
                if (this.listThanhLyThueBaoGetter.length > 0) {
                    const dsIn = this.listThanhLyThueBaoGetter.filter(
                        itemNewVal =>
                            this.listIdMaTT.some(
                                itemOldVal => itemNewVal.MA_TB === itemOldVal
                            )
                    );
                    if (dsIn.length === 0) {
                        this.$root.toastError(
                            "Vui lòng chọn hàng phiếu muốn in!"
                        );
                    } else {
                        this.appendSheet(dsIn, 0, wb);
                    }
                }
                xlsx.writeFile(
                    wb,
                    `Export_${moment(new Date()).format(
                        "DD/MM/YYYY HH:mm:ss"
                    )}.xlsx`
                );
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

        showAlert() {
            this.$bvModal.show("modal-xuat-dulieu");
        }
    }
};
</script>
