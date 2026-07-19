<template src="./QuanLyNhanVienNopTien.html"></template>
<style src="./QuanLyNhanVienNopTien.scss" scoped></style>
<script>
import { mapActions, mapState } from "vuex";
import TableWithSearch from "../components/TableWithSearch.vue";
import moment from "moment";
import KyCuoc from '@/components/KyCuoc'

export default {
    components: { TableWithSearch },
    name: "QuanLyNhanVienNopTien",
    mounted() { },
    computed: {
        ...mapState("quanlynhanviennoptien", [
            "danhSachDonVi",
            "nhomHinhThuc",
            "hinhThucThanhToan",
            "kieuNop",
            "danhSachThuNgan",
            "danhSachNopTien",
            "danhSachDaiLyNopNH",
            "nhanVienNopTien"
        ]),
        userName: function () {
            return this.$auth.getUserName();
        },
        maNhanVien: function () {
            return this.$auth.getMaNhanVien();
        },
        donViId: function () {
            return this.$auth.getDonViID();
        },
        nhanVienId: function () {
            return this.$auth.getNhanVienID();
        }
    },
    updated() {
        console.log(this.$auth.getMaNhanVien());
    },
    watch: {

        danhSachDaiLyNopNH: function (newVal, oldVal) { },
        "form.selectedHinhThuc.value": {
            handler: async function (value) {
                console.log({ value });
                await this.getHinhThucThanhToan({
                    nhomHTTT: value,
                    kieu: null
                });
            }
        },

        hinhThucThanhToan: async function (newValue) {

            this.listHTTT = [];
            if (newValue.length > 0) {
                newValue.forEach(item => {
                    this.listHTTT.push({
                        id: item.HTTT_ID,
                        text: item.HTTT
                    });
                });
            }
        },

    },
    data() {
        return {
            header: {
                title: "Quản lý nhân viên nộp tiền",
                list: [
                    {
                        name: "Quản lý đại lý",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Tra cứu nhân viên nộp tiền",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            fieldsRight: [
                {
                    fieldName: "TEN_DV",
                    headerText: "Nộp cho ĐV",
                    allowFiltering: true,
                    isGroupedColumn: true
                },
                {
                    fieldName: "NGAYNOP",
                    headerText: "Ngày nộp",
                    allowFiltering: true,
                    allowSorting: true,
                    textAlign: "right"
                },
                {
                    fieldName: "TONGNOP",
                    headerText: "Tổng nộp",
                    allowFiltering: true,
                    allowSorting: true,
                    format: 'N0', type: 'number', textAlign: 'Right'
                },
                {
                    fieldName: "TONGRUT",
                    headerText: "Tổng rút",
                    allowFiltering: true,
                    allowSorting: true,
                    format: 'N0', type: 'number', textAlign: 'Right'
                },
                {
                    fieldName: "TONGTIEN",
                    headerText: "Tổng tiền",
                    allowFiltering: true,
                    allowSorting: true,
                    format: 'N0', type: 'number', textAlign: 'Right'
                }
            ],
            fieldsBottom: [
                {
                    fieldName: "NGAYNOP",
                    headerText: "Ngày nộp",
                    allowFiltering: true,
                    allowSorting: true,
                    textAlign: "right"
                },
                {
                    fieldName: "TEN_NV",
                    headerText: "Người nộp",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_DV",
                    headerText: "Nộp cho ĐV",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "KIEUNOP",
                    headerText: "Kiểu nộp",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TIENNOP",
                    headerText: "Số tiền",
                    allowFiltering: true,
                    allowSorting: true,
                    format: 'N0', type: 'number', textAlign: 'Right'
                },
                {
                    fieldName: "HINHTHUC",
                    headerText: "Hình thức TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SO_DT",
                    headerText: "Điện thoại LH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người CN",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DONVI_LAP",
                    headerText: "Đơn vị lập",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGAYNOP",
                    headerText: "Ngày thực nhập",
                    allowFiltering: true,
                    textAlign: "right",
                    allowSorting: true
                }
            ],
            soNgaySua: 0,
            soNgayXoa: 0,
            keyLanNopTien: 0,
            keyChiTietNopTien: 0,
            selectedRowData: null,
            seletedRowIndex: 0,
            listNopNganHang: [],
            listNopTien: [],
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
            listDonVi: [],
            listHinhThuc: [],
            listHTTT: [],
            listKieuNop: [],
            ngay_ht: new Date(),
            classHandling: {
                classNhapMoi: "",
                classCapNhat: "disabled",
                classCancle: "disabled",
                classRemove: "disabled"
            },
            originalForm: {
                kyHoaDon: {
                    value: moment(new Date())
                        .subtract(1, "months")
                        .endOf("month")
                        .toDate(),
                    disabled: false
                },
                ngayNop: {
                    value: new Date(),
                    disabled: false
                },
                kyCuoc: {
                    value:
                        moment(new Date())
                            .subtract(1, "months")
                            .endOf("month")
                            .format("YYYYMM") + "01",
                    disabled: false
                },
                selectedDonVi: {
                    value: this.danhSachDonVi
                        ? this.danhSachDonVi[0].DONVI_ID
                        : null,
                    disabled: false
                },
                selectedHinhThuc: { value: null, disabled: false },
                selectedHinhThucThanhToan: { value: null, disabled: false },
                selectedKieuNop: { value: null, disabled: false },
                selectedThuNgan: { value: null, disabled: false },
                tienNop: { value: 0, disabled: false },
                maNhanVien: { value: this.maNhanVien, disabled: false },
                nguoiNop: { value: null, disabled: false },
                nopNganHangId: { value: null, disabled: false },
                soPhieu: { value: null, disabled: false },
                ghiChu: { value: null, disabled: false }
            },
            form: {
                kyHoaDon: {
                    value: moment(new Date())
                        .subtract(1, "months")
                        .endOf("month")
                        .toDate(),
                    disabled: false
                },
                maNhanVien: {
                    value: this.$auth.getMaNhanVien(),
                    disabled: false
                }
            }
        };
    },
    methods: {
        ...mapActions("quanlynhanviennoptien", [
            "getNhanVienNopTien",
            "getDanhSachDonViNhanVien",
            "getNhomHinhThuc",
            "getHinhThucThanhToan",
            "getKieuNop",
            "getDanhSachThuNgan",
            "getDanhSachNopTien",
            "getDanhSachDaiLyNopNH",
            "getDanhSachDaiLyNopNH",
            "addNhanVienNopTien",
            "deleteLanNopTien",
            "updateNhanVienNopTien",
            "clearData"
        ]),

        onKyHoaDonChange: function (args) {
            this.form.kyCuoc.value = moment(args.value).format("YYYYMM") + "01";
            console.log("Kyhoadon", this.form.kyCuoc.value);
            if (args.value === null) {
                this.$root.toastError("Vui lòng chọn Kỳ hóa đơn");
                return;
            }
        },

        selectedItemsChangedMoi(data) { },
        selectedRowDonViNop(data) {
            this.form.selectedDonVi.value = data.DONVI_ID;
        },

        selectedRowChangedMoi(data, index) {
            //console.log({ data, index });
            // this.seletedRowIndex = index;
            this.selectedRowData = data;
            if (data) {
                this.classHandling.classCapNhat = "";
                this.classHandling.classRemove = "";
                this.form.nopNganHangId.value = data.NOPNH_ID;
                this.form.soPhieu.value = data.SOPHIEU;
                // this.form.maNhanVien.value = data.MANV_TC;
                this.form.ngayNop.value = data.NGAYNOP;
                this.form.nguoiNop.value = data.TEN_NV;
                this.form.selectedHinhThucThanhToan.value = data.HTTT_ID;
                this.form.selectedHinhThuc.value = data.NHOM_HTTT_ID;
                this.form.selectedKieuNop.value = data.KIEUNOP_ID;
                this.form.tienNop.value = data.TIENNOP;
                this.form.selectedThuNgan.value = data.MA_TN;
                this.form.ghiChu.value = data.GHICHU;
            } else {
                this.classHandling.classCapNhat = "disabled";
                this.classHandling.classRemove = "disabled";
            }
        },

        cancleDataNhanVienNopTien() {
            this.classHandling = {
                classNhapMoi: "",
                classCapNhat: this.selectedRowData ? "" : "disabled",
                classCancle: "disabled",
                classRemove: "disabled"
            };
            this.form = JSON.parse(JSON.stringify(this.originalForm));
        },

        checkDuLieu() {
            if (!this.form.selectedHinhThucThanhToan.value) {
                this.$root.toastError("Bạn chưa chọn hình thức thanh toán!");
                this.$refs.selectedHinhThucThanhToan.focus();
                return false;
            }
            if (!this.form.selectedKieuNop.value) {
                this.$root.toastError("Bạn chưa chọn kiểu nộp!");
                this.$refs.selectedKieuNop.focus();
                return false;
            }
            if (this.form.tienNop.value === 0) {
                this.$root.toastError("Số tiền nộp phải khác 0!");
                this.$refs.tienNop.focusIn();
                return false;
            }
            if (!this.form.selectedThuNgan.value) {
                this.$root.toastError("Bạn chưa chọn thu quỹ!");
                this.$refs.selectedThuNgan.focus();
                return false;
            }
            if (!this.form.selectedDonVi.value) {
                this.$root.toastError("Bạn chưa chọn đơn vị!");
                this.$refs.selectedDonVi.focus();
                return false;
            }

            if (this.classHandling.classNhapMoi === "disabled") {

                const startDate = moment(this.ngay_ht).add(-this.soNgaySua + 1, 'days')
                const endDate = moment(this.ngay_ht).add(this.soNgaySua - 1, 'days')

                

                // if (!moment(this.form.ngayNop.value).isBetween(startDate, endDate, 'days', true)) {

                // // if (moment(this.ngay_ht).add(this.soNgaySua, 'days') < this.form.ngayNop.value || moment(this.ngay_ht).add(-this.soNgaySua, 'days') > this.form.ngayNop.value) {
                //     this.$root.toastError("Số ngày nộp bạn chỉ có thể sửa từ " + startDate.format("DD/MM/yyyy") + " đến " + endDate.format("DD/MM/yyyy") + "");
                //     this.$refs.ngayNop.focusIn()
                //     return false;
                // }

                // if (this.form.ngayNop.value > this.ngay_ht) {
                //     this.$root.toastError("Bạn không được nhập ngày nộp lớn hơn ngày hiện tại");
                //     this.$refs.ngayNop.focusIn()
                //     return false;
                // }

                // if (this.form.ngayNop.value < moment(this.ngay_ht).add(-this.soNgaySua, 'days')) {
                //     this.$root.toastError("Bạn không được lùi quá " + this.soNgaySua + " từ ngày hiện tại");
                //     this.$refs.ngayNop.focusIn()
                //     return false;
                // }

                // console.log(moment(new Date()).subtract(1, "months").endOf("month").format("YYYYMM"), moment(this.form.ngayNop.value).subtract(1, "months").endOf("month").format("YYYYMM"))

                // if (moment(new Date()).subtract(1, "months").endOf("month").format("YYYYMM") !=
                //     moment(this.form.ngayNop.value).subtract(1, "months").endOf("month").format("YYYYMM")) {
                // if (moment(new Date()).format("YYYYMM") !== moment(this.form.ngayNop.value).format("YYYYMM")) {
                //     this.$root.toastError("Ngày nộp phải trong tháng " + moment(new Date()).format("MM/YYYY"));
                //     this.$refs.ngayNop.focusIn()
                //     return false;
                // }

            }
            else {
                const startDate = moment(this.ngay_ht).add(-this.soNgaySua + 1, 'days')
                const endDate = moment(this.ngay_ht).add(this.soNgaySua - 1, 'days')
                

                // if (!moment(this.form.ngayNop.value).isBetween(startDate, endDate, 'days', true)) {
                // // if (moment(this.ngay_ht).add(this.soNgaySua, 'days') < this.form.ngayNop.value || moment(this.ngay_ht).add(-this.soNgaySua, 'days') > this.form.ngayNop.value) {
                //     // this.$root.toastError("Số ngày nộp bạn chỉ có thể sửa từ " + moment(this.ngay_ht).add(-this.soNgaySua, 'days').format("DD/MM/yyyy") + " đến " + moment(this.ngay_ht).add(this.soNgaySua, 'days').format("DD/MM/yyyy") + "");
                //     this.$toast.error("Số ngày nộp bạn chỉ có thể sửa từ " + startDate.format("DD/MM/yyyy") + " đến " + endDate.format("DD/MM/yyyy"))
                //     this.$refs.ngayNop.focusIn()
                //     return false;
                // }
                // if (this.form.ngayNop.value > moment(this.ngay_ht).add(this.soNgaySua, 'days')) {
                //     this.$root.toastError("Bạn không được nhập ngày nộp lớn hơn ngày hiện tại");
                //     this.$refs.ngayNop.focusIn()
                //     return false;
                // }


            }

            // let f = this.kieuNop.find((x) => x.KIEUMOP_ID === this.form.selectedKieuNop.value)
            let f = this.kieuNop.find(x => x.KIEUNOP_ID === this.form.selectedKieuNop.value)

            if (f === undefined) {
                this.$root.toastError("Không lấy được thông tin thiết lập tiền âm/dương");
                return false;
            } else if (f.LOAI === 1) {
                if (this.form.TIENNOP < 0) {
                    this.$root.toastError("Tiền của kiểu nộp " + f.KIEUNOP + " phải > 0");
                    return false;
                }
            } else {
                if (this.form.TIENNOP > 0) {
                    this.$root.toastError("Tiền của kiểu nộp " + f.KIEUNOP + " phải < 0");
                    return false;
                }
            }
            return true

            // if (this.listKieuNop.filter((item) => item.KIEUMOP_ID === this.form.selectedKieuNop.value).LOAI === 1) {
            //     if (this.form.TIENNOP < 0) {
            //         this.$root.toastError("Tiền của kiểu nộp " + this.listKieuNop.filter((item) => item.KIEUMOP_ID === this.form.selectedKieuNop.value).KIEUNOP + " phải >0");
            //         return false;
            //     }

            // } else
            //     if (this.listKieuNop.filter((item) => item.KIEUMOP_ID === this.form.selectedKieuNop.value).LOAI === 0) {
            //         if (this.form.TIENNOP > 0) {
            //             this.$root.toastError("Tiền của kiểu nộp " + this.listKieuNop.filter((item) => item.KIEUMOP_ID === this.form.selectedKieuNop.value).KIEUNOP + " phải <0");
            //             return false;
            //         }

            //     }
            //     else {
            //         this.$root.toastError("Không lấy được thông tin thiết lập tiền âm/dương của " + this.listKieuNop.filter((item) => item.KIEUMOP_ID === this.form.selectedKieuNop.value).KIEUNOP + "");
            //         return false;

            //     }



            return true;
        },

        async saveDataNhanVienNopTien() {
            try {
                if (!this.checkDuLieu()) {
                    return;
                }
                this.loading(true);
                if (moment(this.form.ngayNop.value).format("DD/MM/yyyy").toString() != 'Invalid date')
                    this.form.ngayNop.value = moment(this.form.ngayNop.value).format("DD/MM/yyyy").toString();

                let data = {
                    ngayNop: this.form.ngayNop.value,
                    donViId: this.form.selectedDonVi.value,
                    htttId: this.form.selectedHinhThucThanhToan.value,
                    kieuNopId: this.form.selectedKieuNop.value,
                    maTN: this.form.selectedThuNgan.value,
                    kyHoaDon: this.form.kyCuoc.value,
                    tienNop: this.form.tienNop.value,
                    maNVTC: this.form.maNhanVien.value,
                    nhanVienId: this.$auth.getNhanVienID(),
                    ghiChu: this.form.ghiChu.value,
                    nganHangId: 0,
                    donViNhanId: this.$auth.getDonViID(),
                };
                let response;

                if (this.classHandling.classNhapMoi === "disabled") {
                    response = await this.addNhanVienNopTien(data);
                } else {
                    data.nopNganHangId = this.form.nopNganHangId.value;
                    data.soPhieu = this.form.soPhieu.value;
                    response = await this.updateNhanVienNopTien(data);
                }
                if (
                    response.data &&
                    response.data.error_code === "BSS-00000000"
                ) {
                    this.getThongTin();
                    this.loading(false);
                    this.$root.toastSuccess("Cập nhật thành công");
                } else {
                    this.loading(false);
                    this.$root.toastError(response.message_detail);
                }


                this.classHandling = {
                    classNhapMoi: "",
                    classCapNhat: "",
                    classCancle: "",
                    classRemove: "disabled"
                };

                return response;
            } catch (e) {
                this.loading(false);
                console.log(e);
                //this.$root.toastError(e.data.message_detail);
            }
        },

        enterDataNhanVienNopTien() {
            console.log("ngay nop", this.form.ngayNop.value);
            this.cancleDataNhanVienNopTien();
            this.classHandling = {
                classNhapMoi: "disabled",
                classCapNhat: "",
                classCancle: "",
                classRemove: "disabled"
            };
            this.form.nguoiNop.value = this.$auth.getUserName();
            // this.form.maNhanVien.value = "AD4_KHDN";
        },
        async removeDataNhanVienNopTien() {
            if (this.selectedRowData.length === 0) {
                this.$root.toastError("Không lấy được thông tin nộp tiền để xóa. Bạn hãy thử lại");
                return;
            }
            // if (moment(this.ngay_ht).add(this.soNgayXoa, 'days') < this.form.ngayNop.value || moment(this.ngay_ht).add(-this.soNgayXoa, 'days') > this.form.ngayNop.value) {
            const startDate = moment(this.ngay_ht).add(-this.soNgayXoa + 1, 'days')
            const endDate = moment(this.ngay_ht).add(this.soNgayXoa - 1, 'days')
            
            // console.log(startDate, endDate, moment(this.form.ngayNop.value, 'DD/MM/YYYY'))

            if (!moment(this.form.ngayNop.value, 'DD/MM/YYYY').isBetween(startDate, endDate, 'days', true))
            {
                this.$root.toastError("Bạn chỉ có thể xóa phiếu này trong khoảng từ  " + startDate.format("DD/MM/yyyy") + " đến " +  endDate.format("DD/MM/yyyy") + "");
                return false;
            }



            await this.$bvModal
                .msgBoxConfirm(
                    `Bạn có muốn xóa bản ghi này không ?`,
                    {
                        title: "Thông báo",
                        size: "lg",
                        buttonSize: "lg",
                        okVariant: "primary",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy bỏ",
                        hideHeaderClose: false,
                        centered: true,
                        "modal-class": ["f18", "text-center"]
                    }
                )
                .then(async value => {
                    if (value) {
                        this.XoaPhieu();
                    } else {
                        return;
                    }
                })
                .catch(err => {
                    this.loading(false);
                });


        },
        async XoaPhieu() {
            try {

                this.loading(true);
                const data = {
                    donViId: this.form.selectedDonVi.value,
                    ngayNop: this.selectedRowData.NGAYNOP,
                    nopNHId: this.selectedRowData.NOPNH_ID,
                    nguoiCN: "Nguyễn Văn Công",
                    mayCN: null,
                    ipCN: null
                };
                const response = await this.deleteLanNopTien(data);
                if (
                    response.data &&
                    response.data.error_code === "BSS-00000000"
                ) {
                    this.getThongTin();
                    this.loading(false);
                    this.$root.toastSuccess("Xóa phiếu thành công");
                } else {
                    this.loading(false);
                    this.$root.toastError(response.message_detail);
                }
                return response;
            } catch (e) {
                console.log(e);
                this.loading(false);
                this.$root.toastError("Đã có lỗi xảy ra!");
            }
        },

        async getThongTin() {
            this.getDSNopNganHang();
            this.getDSNopTien();
        },

        async getDSNopNganHang() {
            this.listNopNganHang = [];
            this.loading(true);
            let data = {
                kyCuoc: this.form.kyCuoc.value,
                nhanVienId: this.nhanVienId,
                kieuDS: 2
            };
            const response = await this.getDanhSachNopTien(data);
            if (response.data.error_code === "BSS-00000000") {
                this.loading(false);
                this.listNopNganHang = response.data.data;
                this.keyChiTietNopTien++

            } else {
                this.loading(false);
                this.$root.toastError(
                    "Không có dữ liệu thỏa điều kiện các lần nộp tiền"
                );
            }
            return response;
        },
        getTSMDByTS: async function (tsmd) {
            try {
                var rs = await this.$root.context.get('/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh', {
                    pPhanVungid: this.$root.token.getPhanVungID(),
                    pMaThamSo: tsmd
                })
                if (rs.data && rs.data.length > 0) {
                    return Number(rs.data[0].TEN_TS)
                }
                return 0
            } catch (error) { }
        },
        async getDSNopTien() {
            this.listNopTien = [];
            this.loading(true);
            let data = {
                kyCuoc: this.form.kyCuoc.value,
                nhanVienId: this.nhanVienId,
                kieuDS: 1
            };
            const response = await this.getDanhSachNopTien(data);
            if (response.data.error_code === "BSS-00000000") {
                this.loading(false);
                // response.data.data.forEach(item => {
                //     item.TONGNOP = Number(item.TONGNOP).toLocaleString("en-GB");
                //     item.TONGRUT = Number(item.TONGRUT).toLocaleString("en-GB");
                //     item.TONGTIEN = Number(item.TONGTIEN).toLocaleString(
                //         "en-GB"
                //     );
                // });
                this.listNopTien = response.data.data;
                this.keyLanNopTien++
            } else {
                this.loading(false);
                this.$root.toastError("Không có dữ liệu thỏa điều kiện");
            }
            return response;
        },

        async fetchData() {
            this.loading(true);
            const data = {
                // donViId: 11,
                // nhanVienId: 12345,
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID()
            };
            const response = await this.getDanhSachDonViNhanVien(data);
            const listNhomHinhThuc = await this.getNhomHinhThuc();
            const listHinhThucThanhToan = await this.getHinhThucThanhToan();
            const listKieuNop = await this.getKieuNop();
            const listThuNgan = await this.getDanhSachThuNgan();
            console.log(listThuNgan);
            if (
                response.data.error_code === "BSS-00000000" &&
                listNhomHinhThuc.data.error_code === "BSS-00000000"
            ) {

                if (response.data.error_code === "BSS-00000000") {
                    this.originalForm.selectedDonVi.value = this.donViId;
                    this.originalForm.maNhanVien.value = this.maNhanVien;
                    this.originalForm.selectedHinhThuc.value = listNhomHinhThuc.data.data[0].NHOM_HTTT_ID;
                    this.originalForm.selectedHinhThucThanhToan.value = listHinhThucThanhToan.data.data[0].HTTT_ID;
                    this.originalForm.selectedKieuNop.value = listKieuNop.data.data[0].KIEUNOP_ID;
                    this.originalForm.selectedThuNgan.value = listThuNgan.data.data[0].MA_NV;
                    console.log(listThuNgan.data.data[0].MA_NV);
                } else {
                    this.$root.toastError(
                        "Không tìm thấy dữ liệu thỏa điều kiện!"
                    );
                }
            } else {
                this.loading(false);
                this.$root.toastError(response.data.message);
            }
            return response;
        }
    },

    async created() {
        this.clearData();
        this.form = JSON.parse(JSON.stringify(this.originalForm));
        await this.fetchData();
        this.form = JSON.parse(JSON.stringify(this.originalForm));
        this.form.maNhanVien.value = this.maNhanVien;

        if (this.danhSachDonVi !== undefined && this.danhSachDonVi.length > 0) {
            this.danhSachDonVi.forEach(item => {
                this.listDonVi.push({
                    id: item.DONVI_ID,
                    text: item.TEN_DV
                });
            });
        }

        if (this.nhomHinhThuc !== undefined && this.nhomHinhThuc.length > 0) {
            this.nhomHinhThuc.forEach(item => {
                this.listHinhThuc.push({
                    id: item.NHOM_HTTT_ID,
                    text: item.NHOM_HTTT
                });
            });
        }

        if (
            this.hinhThucThanhToan !== undefined &&
            this.hinhThucThanhToan.length > 0
        ) {
            this.form.selectedHinhThuc.value = this.hinhThucThanhToan[0].HTTT_ID
            this.hinhThucThanhToan.forEach(item => {
                this.listHTTT.push({
                    id: item.HTTT_ID,
                    text: item.HTTT
                });
            });
        }

        if (this.kieuNop !== undefined && this.kieuNop.length > 0) {
            this.form.selectedKieuNop.value = this.kieuNop[0].KIEUNOP_ID;
            this.kieuNop.forEach(item => {
                this.listKieuNop.push({
                    id: item.KIEUNOP_ID,
                    text: item.KIEUNOP
                });
            });
        }
        if (this.danhSachThuNgan !== undefined && this.danhSachThuNgan.length > 0) {
            this.form.selectedThuNgan.value = this.danhSachThuNgan[0].MA_NV;
            this.getThongTin();
        }

        this.soNgaySua = await this.getTSMDByTS('SUA_NOPTIEN')
        this.soNgayXoa = await this.getTSMDByTS('XOA_NOPTIEN')

        // this.getThongTin();
    }
};
</script>
