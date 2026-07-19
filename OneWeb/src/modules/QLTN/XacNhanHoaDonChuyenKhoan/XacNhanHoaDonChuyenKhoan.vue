<template src="./XacNhanHoaDonChuyenKhoan.html"></template>
<style src="./XacNhanHoaDonChuyenKhoan.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import xlsx from "xlsx";
import PopupDocFile from "./popup-docfile.vue";
import ComboboxGrid from "../components/ComboboxGrid";

export default {
    name: "XacNhanHoaDonChuyenKhoan",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        PopupDocFile,
        ComboboxGrid
    },
    async created() {
        this.clearData();
        this.fetchData();
    },
    async mounted() {},
    data() {
        return {
            header: {
                title: "Xác nhận hóa đơn chuyển khoản",
                list: [
                    {
                        name: "Quản lý đại lý",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Xác nhận hóa đơn chuyển khoản",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
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
            idNVTC: null,
            idLuotGiao: null,
            tabNumber: 1,
            searchString: "",
            ngayNhanHoaDon: new Date(),
            checkNgayNhan: false,
            selectedRow: null,
            listHoaDon: [],
            idLyDo: "0",
            kyCuocHienHanh: "",
            fieldLanTao: [
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true,
                    allowSorting: true,
                    isSumSelected: true
                },
                {
                    fieldName: "NGAY_TT",
                    headerText: "Ngày trả",
                    allowFiltering: true,
                    allowSorting: true,
                    cellCssClass: "text-right"
                },
                {
                    fieldName: "SOPHIEU",
                    headerText: "Số phiếu",
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
                    fieldName: "MATB_DD",
                    headerText: "Mã đại diện",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "LANTRA",
                    headerText: "Lần trả",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TIENTRA",
                    headerText: "Tiền trả",
                    allowFiltering: true,
                    allowSorting: true,
                    isSum: true
                },
                {
                    fieldName: "SOHOADON",
                    headerText: "Số hóa đơn",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên Thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "Địa chỉ thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGUOIGACH",
                    headerText: "Người gạch",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "CHUNGTU",
                    headerText: "Số chứng từ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "GHICHU_TRA",
                    headerText: "Ghi chú trả",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGAYNHAN",
                    headerText: "Ngày nhận HĐ",
                    allowFiltering: true,
                    allowSorting: true,
                    allowHtml: true
                },
                {
                    fieldName: "LYDOTRA_ID",
                    headerText: "Lý do",
                    allowFiltering: true,
                    allowSorting: true,
                    allowHtml: true,
                    width: "10%"
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    allowSorting: true,
                    allowHtml: true
                },
                {
                    fieldName: "NVTC",
                    headerText: "Nhân viên thu cước",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DTCC",
                    headerText: "ĐTCC",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "NGAYNGANHANG",
                    headerText: "Ngày ngân hàng",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_NH",
                    headerText: "Ngân hàng",
                    allowFiltering: true,
                    allowSorting: true
                }
            ]
        };
    },
    updated: function() {},
    computed: {
        ...mapGetters("XacNhanHoaDonChuyenKhoan", [
            "listNVTCGetter",
            "listLuotGiaoGetter",
            "listHoaDonGetter",
            "listLyDoTraGetter"
        ])
    },
    watch: {
        tabNumber: {
            handler: async function(value) {
                this.getHoaDon();
            }
        },
        idNVTC: {
            handler: async function(value) {
                this.getLuotGiaoAction();
            }
        },
        listHoaDonGetter: function(newValue) {
            console.log({ newValue });
            const data = newValue.map(item => {
                let opt = `<select id="combo${item.GIAOPHIEU_ID}" style="width:200px;" class="form-control">`;
                this.listLyDoTraGetter.forEach(el => {
                    if (el.LYDOTRA_ID == item.LYDOTRA_ID) {
                        opt += `<option selected="selected" value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`;
                    } else {
                        opt += `<option value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`;
                    }
                });
                opt += `</select>`;

                let ngayNhan = ``;
                //xử lý lại datetime
                if (item.NGAYNHAN != "" && item.NGAYNHAN) {
                    let timeString = item.NGAYNHAN;
                    const date = timeString.substring(0, 2);
                    const month = timeString.substring(3, 5);
                    const year = timeString.substring(6, 10);
                    const ngayNhanDate = year + "-" + month + "-" + date;
                    ngayNhan = `<input type="date" id="ngayNhan${item.GIAOPHIEU_ID}" name="ngayNhan" value="${ngayNhanDate}">`;
                } else {
                    ngayNhan = `<input type="date" id="ngayNhan${item.GIAOPHIEU_ID}" name="ngayNhan">`;
                }

                return {
                    ...item,
                    NGAYNHAN: ngayNhan,
                    LYDOTRA_ID: opt,
                    GHICHU: `<input type="text" style="width:200px;" id="ghichu${item.GIAOPHIEU_ID}" class="form-control" value="${item.GHICHU}">`
                };
            });
            this.listHoaDon = data;
        },
        listLyDoTraGetter: function(newValue) {
            // this.idLyDo = newValue.length > 0 && newValue[0].LYDOTRA_ID;
        },
        idLyDo: function(newValue) {
            console.log({ newValue });
            if (this.listLyDoTraGetter.length > 0 && this.idLyDo !== "0") {
                this.$refs.fileInput.click();
                this.close();
            }
        }
    },
    methods: {
        ...mapActions("XacNhanHoaDonChuyenKhoan", [
            "getNVTC",
            "getLuotGiao",
            "getListHoaDon",
            "getLyDoTra",
            "capNhatTraPhieuChuyenKhoan",
            "capNhatHuyTraPhieuChuyenKhoan",
            "capNhatNgayTraPhieu",
            "huyHoaDonPhatDuoc",
            "getKyCuocHienHanh",
            "clearData"
        ]),

        async fetchData() {
            await this.getNVTC();
            this.getLuotGiaoAction();
            this.getLyDoTra();
        },

        async getLuotGiaoAction() {
            const kyCuocHienHanh = await this.getKyCuocHienHanh();
            console.log({ kyCuocHienHanh });
            const body = {
                pChuKyNo: kyCuocHienHanh.data.data[0].KYCUOC, //20210301,
                // pChuKyNo: `20210301`, //20210301,
                pNVTCId: this.idNVTC, //"3789,12901,4512",
                pKieu: 0, //this.$root.token.getPhanVungID() == 26 ? 5 : 1, // HP 5 else 1
                pLanTaoId: null
            };
            this.getLuotGiao(body);
        },

        async getHoaDon() {
            try {
                this.loading(true);
                const data = {
                    giaoPhieuId: this.idLuotGiao, // 1: 670020 2:670021 3:670022
                    kieu: this.tabNumber
                };
                const response = await this.getListHoaDon(data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    this.listHoaDonGetter.length > 0
                ) {
                    console.log({ response });
                    this.$root.toastSuccess(
                        "Lấy danh sách hóa đơn thành công!"
                    );
                    this.$refs.grid.setCurrentSelectedRow(0);
                } else {
                    this.$root.toastError("Không có dữ liệu thỏa điều kiện");
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        async huyTraHoaDon() {
            try {
                if (
                    this.listHoaDon.length === 0 ||
                    this.listIdMaTT.length === 0
                ) {
                    this.$root.toastError(
                        "Bạn chưa chọn bản ghi hoặc chưa có dữ liệu tại lưới"
                    );
                    return;
                }

                let dsTraPhieu = [];
                const listTraPhieu = this.listHoaDon.filter(itemNewVal =>
                    this.listIdMaTT.some(
                        itemOldVal => itemNewVal.PHIEUTRA_ID === itemOldVal
                    )
                );
                listTraPhieu.forEach(item => {
                    const data = {
                        giaoPhieuId: item.GIAOPHIEU_ID,
                        phieuTraId: item.PHIEUTRA_ID
                    };
                    dsTraPhieu.push(data);
                });
                console.log({ dsTraPhieu });
                this.loading(true);
                const data = {
                    dsTraPhieu: dsTraPhieu,
                    trangThaiHDId: 3
                };
                const response = await this.capNhatHuyTraPhieuChuyenKhoan(data);
                if (response.data.error_code === "BSS-00000000") {
                    console.log({ response });
                    this.getHoaDon();
                    this.$root.toastSuccess("Hủy trả thành công!");
                } else {
                    this.$root.toastError("Đã có lỗi xảy ra");
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        async capNhatTraPhieu() {
            try {
                if (
                    this.listHoaDon.length === 0 ||
                    this.listIdMaTT.length === 0
                ) {
                    this.$root.toastError(
                        "Bạn chưa chọn bản ghi hoặc chưa có dữ liệu tại lưới"
                    );
                    return;
                }

                let dsTraPhieu = [];
                const listTraPhieu = this.listHoaDon.filter(itemNewVal =>
                    this.listIdMaTT.some(
                        itemOldVal => itemNewVal.PHIEUTRA_ID === itemOldVal
                    )
                );
                listTraPhieu.forEach(item => {
                    if (
                        document.getElementById(`combo${item.GIAOPHIEU_ID}`)
                            .value === 0 ||
                        document.getElementById(`ghichu${item.GIAOPHIEU_ID}`)
                            .value === ""
                    ) {
                        this.$root.toastError(
                            "Bạn chưa chọn ghi chú hoặc lý do trả phiếu"
                        );
                        return;
                    }
                    const data = {
                        giaoPhieuId: item.GIAOPHIEU_ID,
                        phieuTraId: item.PHIEUTRA_ID,
                        lyDoTraId: document.getElementById(
                            `combo${item.GIAOPHIEU_ID}`
                        ).value,
                        ghiChu: document.getElementById(
                            `ghichu${item.GIAOPHIEU_ID}`
                        ).value
                    };
                    dsTraPhieu.push(data);
                });
                console.log({ dsTraPhieu });

                this.loading(true);
                const data = {
                    dsTraPhieu: dsTraPhieu,
                    kieu: 3
                };
                const response = await this.capNhatTraPhieuChuyenKhoan(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.getHoaDon();
                    console.log({ response });
                    this.$root.toastSuccess("Trả hóa đơn thành công!");
                } else {
                    this.$root.toastError("Đã có lỗi xảy ra");
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        async huyHoaDon() {
            try {
                if (
                    this.listHoaDon.length === 0 ||
                    this.listIdMaTT.length === 0
                ) {
                    this.$root.toastError(
                        "Bạn chưa chọn bản ghi hoặc chưa có dữ liệu tại lưới"
                    );
                    return;
                }
                this.loading(true);
                const data = {
                    dsPhieuId: this.listIdMaTT
                };
                const response = await this.huyHoaDonPhatDuoc(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.getHoaDon();
                    console.log({ response });
                    this.$root.toastSuccess("Hủy hóa đơn thành công!");
                } else {
                    this.$root.toastError("Đã có lỗi xảy ra");
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        async capNhat() {
            try {
                if (
                    this.listHoaDon.length === 0 ||
                    this.listIdMaTT.length === 0
                ) {
                    this.$root.toastError(
                        "Bạn chưa chọn bản ghi hoặc chưa có dữ liệu tại lưới"
                    );
                    return;
                }

                // if (!this.checkNgayNhan) {
                //     this.$root.toastError(
                //         "Vui lòng chọn ngày nhận trước khi cập nhật"
                //     );
                //     return;
                // }
                let dsTraPhieu = [];
                const listTraPhieu = this.listHoaDon.filter(itemNewVal =>
                    this.listIdMaTT.some(
                        itemOldVal => itemNewVal.PHIEUTRA_ID === itemOldVal
                    )
                );
                listTraPhieu.forEach(item => {
                    const ngayNhan = document.getElementById(
                        `ngayNhan${item.GIAOPHIEU_ID}`
                    ).value;
                    console.log({ ngayNhan });
                    const year = ngayNhan.substring(0, 4);
                    const month = ngayNhan.substring(5, 7);
                    const date = ngayNhan.substring(8, 10);
                    const data = {
                        giaoPhieuId: item.GIAOPHIEU_ID,
                        phieuTraId: item.PHIEUTRA_ID,
                        lyDoTraId: document.getElementById(
                            `combo${item.GIAOPHIEU_ID}`
                        ).value,
                        ghiChu: document.getElementById(
                            `ghichu${item.GIAOPHIEU_ID}`
                        ).value,
                        ngay: date + "/" + month + "/" + year
                    };
                    dsTraPhieu.push(data);
                });
                console.log({ dsTraPhieu });
                this.loading(true);
                const data = {
                    dsTraPhieu: dsTraPhieu,
                    kieu: this.tabNumber - 1
                };
                const response = await this.capNhatNgayTraPhieu(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.getHoaDon();
                    console.log({ response });
                    this.$root.toastSuccess("Cập nhật thành công!");
                } else {
                    this.$root.toastError("Đã có lỗi xảy ra");
                }
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        },

        onPhieuThanhToanChange(dataItem) {
            console.log(dataItem);
            this.listIdMaTT = dataItem;
        },

        onPhieuThanhToanRowChange(dataItem) {
            this.selectedRow = dataItem;
        },

        NVTC_selectedChanged(dataItem) {
            if (dataItem) {
                this.idNVTC = dataItem.NHANVIEN_ID;
                console.log({ dataItem });
            }
        },

        luotgiao_selectedChanged(dataItem) {
            if (dataItem) {
                this.idLuotGiao = dataItem.GIAOPHIEU_ID;
                console.log({ dataItem });
            }
        },
        docFile() {
            this.$bvModal.show("modal-docfile");
            this.idLyDo = "0";
        },
        close() {
            this.$bvModal.hide("modal-docfile");
        },
        async sendData(data) {
            this.$root.toastSuccess("Thành công!");
        },

        searchHoaDon() {
            if (this.searchString === "") {
                this.listHoaDon = this.listHoaDonGetter;
            } else {
                this.listHoaDon = this.listHoaDon.filter(
                    item => item.MA_TT === this.searchString
                );
            }
        },

        onExcelExport() {
            if (this.listHoaDon.length === 0) {
                this.$root.toastError("Chưa có danh sách để xuất excel!");
            } else {
                var wb = xlsx.utils.book_new();
                if (this.listHoaDon.length > 0) {
                    const dsIn = this.listHoaDon.filter(itemNewVal =>
                        this.listIdMaTT.some(
                            itemOldVal => itemNewVal.PHIEUTRA_ID === itemOldVal
                        )
                    );
                    if (dsIn.length === 0) {
                        this.$root.toastError(
                            "Vui lòng chọn chọn phiếu muốn xuất excel!"
                        );
                        return;
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

        async onChangeFile(e) {
            const files = e.target.files;
            this.duongDan = e.target.files[0].name;
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$root.toastError(
                    "File excel không đúng định dạng!"
                );
            }
            const fileReader = new FileReader();
            fileReader.onload = async ev => {
                try {
                    this.dsLoi = [];
                    this.dsLanTao = [];
                    const data = ev.target.result;
                    const XLSX = xlsx;
                    const workbook = XLSX.read(data, {
                        type: "binary"
                    });
                    const wsname = workbook.SheetNames[0];
                    const ws = XLSX.utils.sheet_to_json(
                        workbook.Sheets[wsname]
                    );
                    const lydotra = this.listLyDoTraGetter.filter(
                        item => item.LYDOTRA_ID === this.idLyDo
                    );
                    console.log({ ws });
                    const excellist = [];
                    let dsDanhSach = [];
                    let dsMaTT = [];
                    let dsDuplicateMaTT = [];
                    // Edit data
                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                        dsMaTT.push(ws[i].MA_TT);
                        dsDanhSach.push({
                            STT: ws[i].STT,
                            PHIEUTRA_ID: ws[i].PHIEUTRA_ID,
                            GIAOPHIEU_ID: ws[i].GIAOPHIEU_ID,
                            NGAY_TT: ws[i].NGAY_TT,
                            SOPHIEU: ws[i].SOPHIEU,
                            diaChiTT: ws[i].DIACHI_TT,
                            MA_TT: ws[i].MA_TT,
                            MATB_DD: ws[i].MATB_DD,
                            LANTRA: ws[i].LANTRA,
                            TIENTRA: ws[i].TIENTRA,
                            SOHOADON: ws[i].SOHOADON,
                            TEN_TT: ws[i].TEN_TT,
                            DIACHI_TT: ws[i].DIACHI_TT,
                            NGUOIGACH: ws[i].NGUOIGACH,
                            CHUNGTU: ws[i].CHUNGTU,
                            GHICHU_TRA: ws[i].GHICHU_TRA,
                            LYDOTRA_ID: this.idLyDo,
                            LYDO_TRA: lydotra[0].NOIDUNG,
                            GHICHU: ws[i].GHICHU,
                            NVTC: ws[i].NVTC,
                            DTCC: ws[i].DTCC,
                            NGAYNGANHANG: ws[i].NGAYNGANHANG,
                            TEN_NH: ws[i].TEN_NH
                        });
                    }
                    console.log({ dsDanhSach });
                    this.listHoaDon = dsDanhSach;
                    // dsMaTT.forEach((element) => {
                    // 	if (
                    // 		dsMaTT.indexOf(element) != dsMaTT.lastIndexOf(element) &&
                    // 		!dsDuplicateMaTT.includes(element)
                    // 	)
                    // 		dsDuplicateMaTT.push(element);
                    // });
                    // dsDuplicateMaTT.forEach((element) => {
                    // 	this.dsLoi.push({
                    // 		MA_TT: element,
                    // 		GHICHU: "Thanh toán bị lặp lại",
                    // 	});
                    // });
                } catch (e) {
                    return this.$root.toastError(
                        "Kiểm tra dữ liệu file đầu vào!"
                    );
                }
            };
            fileReader.readAsBinaryString(files[0]);
        }
    }
};
</script>
