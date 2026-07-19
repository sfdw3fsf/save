<template src="./GiaoSanLuongNVTC.html"></template>

<script>
import Vue from "vue";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import API from "../api/GiaoSanLuongNVTC";
import { Workbook } from "@syncfusion/ej2-excel-export/index";
import { ExcelCollumns, Collumns } from "./common";
import xlsx from "xlsx";

Vue.use(DialogPlugin);
export default Vue.extend({
    name: "GiaoSanLuongNVTC",
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            cboThang: [],
            cboThangValue: "12",
            cboNam: [],
            cboNamValue: "",
            ckKhoa: false,
            ckKhoaEnabled: false,
            excelInputData: [],
            excelInputPath: "",
            dt: [],
            upload: false,
            check: 0,
            gridViewControll: [],
            gridControllCollumn: [],
            showFilter: true,
            collumns: Collumns
        };
    },
    watch: {
        async cboThangValue() {
            this.check = 0;
            this.LoadDataAndKhoaTLTC();
        },
        async cboNamValue() {
            this.check = 0;
            this.LoadDataAndKhoaTLTC();
        }
    },
    methods: {
        async khoaChanged(value) {
            if (this.ckKhoa) {
                try {
                    this.loading(true);
                    let data = {
                        pNam: this.cboNamValue,
                        pThang: this.cboThangValue,
                        pKhoa: 1
                    };
                    let response = await API.putCapNhatKhoa(this.axios, data);
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        this.$toast.success(
                            "Đã khóa dữ liệu tháng " +
                                this.cboThangValue +
                                "/" +
                                this.cboNamValue
                        );
                    }
                } catch (error) {
                    this.$toast.error(error.data.message_detail);
                } finally {
                    this.loading(false);
                }
            } else {
                try {
                    this.loading(true);
                    let data = {
                        pNam: this.cboNamValue,
                        pThang: this.cboThangValue,
                        pKhoa: 0
                    };
                    let response = await API.putCapNhatKhoa(this.axios, data);
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        this.$toast.success(
                            "Mở khóa dữ liệu tháng " +
                                this.cboThangValue +
                                "/" +
                                this.cboNamValue
                        );
                    }
                } catch (error) {
                    this.$toast.error(error.data.message_detail);
                } finally {
                    this.loading(false);
                }
            }
            this.check = 1;
        },
        async openDialog() {
            this.$refs.dlgGiaoSanLuongNVTC.show();
            try {
                this.loading(true);

                let response = await API.getNam(this.axios, {});
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.cboNam = response.data.data;
                }
                response = await API.getThang(this.axios, {});
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.cboThang = response.data.data;
                }
                response = await API.getQLVTNhanVienChot(this.axios, {});
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.length > 0) {
                        this.ckKhoaEnabled = true;
                    }
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
            if (this.cboNamValue == this.cboNam[0].TEN_NAM) {
                this.LoadDataAndKhoaTLTC();
            } else this.cboNamValue = this.cboNam[0].TEN_NAM;
            this.cboThangValue = this.cboThang[0].TEN_THANG;
        },
        formatMoney(n, currency) {
            try {
                return currency
                    ? currency
                    : "" +
                          n.toFixed(2).replace(/./g, function(c, i, a) {
                              return i > 0 &&
                                  c !== "." &&
                                  (a.length - i) % 3 === 0
                                  ? "," + c
                                  : c;
                          });
            } catch (error) {
                return n;
            }
        },
        numberFormat(number) {
            if (!number) {
                return number;
            }

            number = `${number}`;

            let decimal = "";

            if (number.includes(".")) {
                decimal = "." + number.split(".")[1];
                number = number.split(".")[0];
            }

            let group = [];

            while (number.length > 3) {
                group.push(number.substr(number.length - 3));
                number = number.substr(0, number.length - 3);
            }

            if (number.length > 0) {
                group.push(number);
            }

            return group.reverse().join() + decimal;
        },
        gridViewControll_QueryCellInfo(args) {
            if (args.column.field === "DIEMPTTB_QD_GIAO") {
                args.cell.innerText = this.numberFormat(
                    args.data.DIEMPTTB_QD_GIAO
                );
            }
            if (args.column.field === "DIEMPTTB_QD_TH") {
                args.cell.innerText = this.numberFormat(
                    args.data.DIEMPTTB_QD_TH
                );
            }
            if (args.column.field === "TB_FIBER_MOI_GIAO") {
                args.cell.innerText = this.numberFormat(
                    args.data.TB_FIBER_MOI_GIAO
                );
            }
            if (args.column.field === "TB_FIBER_MOI_GIAO") {
                args.cell.innerText = this.numberFormat(
                    args.data.TB_FIBER_MOI_GIAO
                );
            }
            if (args.column.field === "TB_FIBER_MOI_TH") {
                args.cell.innerText = this.numberFormat(
                    args.data.TB_FIBER_MOI_TH
                );
            }
            if (args.column.field === "TB_GOI_GIA_DINH_VP_GIAO") {
                args.cell.innerText = this.numberFormat(
                    args.data.TB_GOI_GIA_DINH_VP_GIAO
                );
            }
            if (args.column.field === "TB_GOI_GIA_DINH_VP_TH") {
                args.cell.innerText = this.numberFormat(
                    args.data.TB_GOI_GIA_DINH_VP_TH
                );
            }
            if (args.column.field === "CHITIEU_THTN") {
                args.cell.innerText = this.numberFormat(args.data.CHITIEU_THTN);
            }
            if (args.column.field === "HCL") {
                args.cell.innerText = this.numberFormat(args.data.HCL);
            }
        },
        closeDialog() {
            this.$refs.dlgGiaoSanLuongNVTC.hide();
            this.reset();
        },
        isNullEmpty(value) {
            return value === null || value === "";
        },
        async LoadDataAndKhoaTLTC() {
            this.loadData();
            try {
                this.loading(true);
                let data = {
                    pNam: this.cboNamValue,
                    pThang: this.cboThangValue
                };
                let response = await API.getKhoaTLTC(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.check = 0;
                    if (
                        response.data.data.length > 0 &&
                        response.data.data[0].KHOA == 1
                    ) {
                        this.ckKhoa = true;
                    } else {
                        this.ckKhoa = false;
                    }
                }
            } catch (error) {
                this.$toast.error(error.response.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        btnExcelExample() {
            let borders = {
                color: "#00000000",
                lineStyle: "thin"
            };
            let columns = [
                { index: 1, width: 100 },
                { index: 2, width: 100 },
                { index: 3, width: 100 },
                { index: 4, width: 100 },
                { index: 5, width: 130 },
                { index: 6, width: 130 },
                { index: 7, width: 130 },
                { index: 8, width: 130 },
                { index: 9, width: 130 },
                { index: 10, width: 130 },
                { index: 11, width: 130 },
                { index: 12, width: 130 }
            ];
            let rows = [
                {
                    index: 1,
                    cells: [
                        {
                            index: 1,
                            value: "TENPHONG",
                            // colSpan: 18,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 2,
                            value: "TEN_KV",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 3,
                            value: "MANV_TC",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 4,
                            value: "TENNV_TC",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 5,
                            value: "DIEMPTTB_QD_GIAO",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 6,
                            value: "DIEMPTTB_QD_TH",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 7,
                            value: "TB_FIBER_MOI_GIAO",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 8,
                            value: "TB_FIBER_MOI_TH",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 9,
                            value: "TB_GOI_GIA_DINH_VP_GIAO",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 10,
                            value: "TB_GOI_GIA_DINH_VP_TH",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 11,
                            value: "CHITIEU_THTN",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        },
                        {
                            index: 12,
                            value: "HCL",
                            // colSpan: 3,
                            style: {
                                bold: true
                                // borders: borders
                            }
                        }
                    ]
                }
            ];
            let worksheets = [{ columns, rows }];
            worksheets[0].name = "file";
            let workbook = new Workbook({ worksheets }, "xlsx");
            workbook.save("filemau.xlsx");
        },
        reset() {
            this.excelInputData = [];
            this.excelInputPath = "";
            this.gridViewControll = [];
            this.gridControllCollumn = [];
        },
        resetAll() {
            this.cboThang = [];
            this.cboThangValue = "12";
            this.cboNam = [];
            this.ckKhoa = false;
            this.ckKhoaEnabled = false;
            this.excelInputData = [];
            this.excelInputPath = "";
            this.dt = [];
            this.upload = false;
            this.check = 0;
            this.gridViewControll = [];
            this.gridControllCollumn = [];
            this.showFilter = true;
        },
        btnExcelInput(e) {
            console.log(e);
            const files = e.target.files;
            this.maTT = [];
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$toast.error("File excel không đúng định dạng!");
            }
            this.reset();
            const fileReader = new FileReader();
            fileReader.onload = async ev => {
                try {
                    this.maTT_Items = [];
                    const data = ev.target.result;
                    const workbook = xlsx.read(data, { type: "binary" });
                    const wsname = workbook.SheetNames[0];
                    const ws = xlsx.utils.sheet_to_json(
                        workbook.Sheets[wsname],
                        {
                            header: 0,
                            defval: ""
                        }
                    );

                    //Validate input file:
                    if (Object.keys(ws[0]).length <= 1) {
                        this.$toast.error("File excel không đúng định dạng!");
                        return;
                    }
                    for (const iterator of ExcelCollumns) {
                        if (!ws[0].hasOwnProperty(iterator.fieldName)) {
                            this.$toast.error(
                                "Tên cột trong file không đúng định dạng: " +
                                    iterator.fieldName
                            );
                            return;
                        }
                    }

                    if (ws.length > 0) {
                        ws.forEach(item => {
                            this.excelInputData.push(item);
                        });
                    }
                } catch (e) {
                    console.log(e);
                    this.removeInputData();
                    this.reset();
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
                if (this.excelInputData.length > 0) {
                    for (const [i, dataRow] of this.excelInputData.entries()) {
                        if (dataRow["TENPHONG"].length >= 300) {
                            this.$toast.error(
                                `Dòng ${i + 1} TENPHONG có độ dài quá lớn!`
                            );
                            return;
                        } else if (dataRow["TENPHONG"].length == 0) {
                            this.$toast.error(
                                `Dòng ${i + 1} TENPHONG không được trống!`
                            );
                            return;
                        }
                        if (dataRow["TEN_KV"].length >= 300) {
                            this.$toast.error(
                                `Dòng ${i + 1} TEN_KV có độ dài quá lớn!`
                            );
                            return;
                        } else if (dataRow["TEN_KV"].length == 0) {
                            this.$toast.error(
                                `Dòng ${i + 1} TEN_KV không được trống!`
                            );
                            return;
                        }
                        if (dataRow["MANV_TC"].length >= 300) {
                            this.$toast.error(
                                `Dòng ${i + 1} MANV_TC có độ dài quá lớn!`
                            );
                            return;
                        } else if (dataRow["MANV_TC"].length == 0) {
                            this.$toast.error(
                                `Dòng ${i + 1} MANV_TC không được trống!`
                            );
                            return;
                        }
                        if (dataRow["TENNV_TC"].length >= 300) {
                            this.$toast.error(
                                `Dòng ${i + 1} TENNV_TC có độ dài quá lớn!`
                            );
                            return;
                        }
                        if (
                            dataRow["DIEMPTTB_QD_GIAO"].toString().length >= 13
                        ) {
                            this.$toast.error(
                                `Dòng ${i + 1} DIEMPTTB_QD_GIAO ${
                                    dataRow["DIEMPTTB_QD_GIAO"]
                                } quá lớn!`
                            );
                            return;
                        }
                        if (dataRow["DIEMPTTB_QD_TH"].toString().length >= 13) {
                            this.$toast.error(
                                `Dòng ${i + 1} DIEMPTTB_QD_TH  ${
                                    dataRow["DIEMPTTB_QD_TH"]
                                } quá lớn!`
                            );
                            return;
                        }
                        if (
                            dataRow["TB_FIBER_MOI_GIAO"].toString().length >= 13
                        ) {
                            this.$toast.error(
                                `Dòng ${i + 1} TB_FIBER_MOI_GIAO  ${
                                    dataRow["TB_FIBER_MOI_GIAO"]
                                } quá lớn!`
                            );
                            return;
                        }
                        if (
                            dataRow["TB_FIBER_MOI_TH"].toString().length >= 13
                        ) {
                            this.$toast.error(
                                `Dòng ${i + 1} TB_FIBER_MOI_TH  ${
                                    dataRow["TB_FIBER_MOI_TH"]
                                } quá lớn!`
                            );
                            return;
                        }
                        if (
                            dataRow["TB_GOI_GIA_DINH_VP_GIAO"].toString()
                                .length >= 11
                        ) {
                            this.$toast.error(
                                `Dòng ${i + 1} TB_GOI_GIA_DINH_VP_GIAO  ${
                                    dataRow["TB_GOI_GIA_DINH_VP_GIAO"]
                                } quá lớn!`
                            );
                            return;
                        }
                        if (
                            dataRow["TB_GOI_GIA_DINH_VP_TH"].toString()
                                .length >= 11
                        ) {
                            this.$toast.error(
                                `Dòng ${i + 1} TB_GOI_GIA_DINH_VP_TH  ${
                                    dataRow["TB_GOI_GIA_DINH_VP_TH"]
                                } quá lớn!`
                            );
                            return;
                        }
                        if (dataRow["CHITIEU_THTN"].toString().length >= 13) {
                            this.$toast.error(
                                `Dòng ${i + 1} CHITIEU_THTN  ${
                                    dataRow["CHITIEU_THTN"]
                                } quá lớn!`
                            );
                            return;
                        }
                        if (dataRow["HCL"].toString().length >= 13) {
                            this.$toast.error(
                                `Dòng ${i + 1} HCL  ${dataRow["HCL"]} quá lớn!`
                            );
                            return;
                        }
                    }
                    this.loadExcel(this.excelInputData);
                    this.upload = true;
                }
            };
            fileReader.readAsBinaryString(files[0]);
            this.excelInputPath = files[0].name;
        },
        async loadData() {
            try {
                this.loading(true);
                let data = {
                    pNam: this.cboNamValue,
                    pThang: this.cboThangValue
                };
                let response = await API.getDSTLTC(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    // if (response.data.data.length <= 0) {
                    //   this.gridViewControll = [];
                    //   return;
                    // }
                    this.gridViewControll = response.data.data;
                    this.gridControllCollumn = this.collumns;
                    // for (const iterator of response.data.data) {
                    //     for (const key in iterator) {
                    //         this.gridControllCollumn.push({
                    //             fieldName: key,
                    //             headerText: key,
                    //             allowFiltering: true
                    //         });
                    //     }
                    //     break;
                    // }
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        loadExcel(dataExcel) {
            this.gridViewControll = dataExcel;
            this.gridControllCollumn = [];
            this.dt = dataExcel;
            for (const iterator of dataExcel) {
                for (const key in iterator) {
                    this.gridControllCollumn.push({
                        fieldName: key,
                        headerText: key,
                        allowFiltering: true
                    });
                }
                break;
            }
        },
        removeInputData() {
            this.$refs.fileInput.value = null;
        },
        async btnHoanThanh() {
            if (this.ckKhoa == false) {
                if (this.upload == true) {
                    if (!this.isNullEmpty(this.dt)) {
                        if (this.dt.length > 0) {
                            this.capNhat(this.dt);
                            this.upload = false;
                        } else {
                            this.$toast.error("Không có dữ liệu để cập nhật!");
                        }
                    }
                } else {
                    this.$toast.error("Không có dữ liệu để cập nhật!");
                }
            } else {
                this.$toast.error(
                    "Dữ liệu tháng đã được khóa bạn không thể cập nhật!"
                );
            }
        },
        toLowerCaseKeyOfObject: function(object) {
            let obj = {};
            for (const key in object) {
                const keyLowerkey = key.toLowerCase();
                obj[keyLowerkey] = object[key];
            }
            return obj;
        },
        async capNhat(dataPost) {
            let dataDS = {
                pNam: this.cboNamValue,
                pThang: this.cboThangValue
            };
            let dataMatch = [];
            console.log(dataPost);
            for (const iterator of dataPost) {
                dataMatch.push(this.toLowerCaseKeyOfObject(iterator));
            }
            console.log(dataMatch);
            let response = await API.getDSTLTC(this.axios, dataDS);
            if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined
            ) {
                if (response.data.data.length > 0) {
                    await this.$bvModal
                        .msgBoxConfirm(
                            `Tháng ${this.cboThangValue}/${this.cboNamValue} đã có dữ liệu bạn có muốn cập nhật lại hay không?`,
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
                                try {
                                    this.loading(true);
                                    let data = {
                                        pNam: this.cboNamValue,
                                        pThang: this.cboThangValue,
                                        pKieu: 0,
                                        pData: dataMatch
                                    };
                                    let response = await API.postCapNhatGiaoSL(
                                        this.axios,
                                        data
                                    );
                                    if (
                                        response.data.error_code ===
                                            "BSS-00000000" &&
                                        response.data.data !== undefined
                                    ) {
                                        this.$toast.success(
                                            "Cập nhập thành công!"
                                        );
                                    }
                                } catch (error) {
                                    this.$toast.error(
                                        error.data.message_detail
                                    );
                                } finally {
                                    this.loading(false);
                                }
                            }
                        })
                        .catch(err => {
                            this.loading(false);
                        });
                } else {
                    try {
                        this.loading(true);
                        let data = {
                            pNam: this.cboNamValue,
                            pThang: this.cboThangValue,
                            pKieu: 1,
                            pData: dataMatch
                        };
                        let response = await API.postCapNhatGiaoSL(
                            this.axios,
                            data
                        );
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            this.$toast.success("Thêm mới thành công!");
                        }
                    } catch (error) {
                        this.$toast.error(error.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                }
                this.excelInputPath = "";
                this.loadData();
            }
        },
        async btnXoa() {
            if (this.ckKhoa == false) {
                let data = {
                    pNam: this.cboNamValue,
                    pThang: this.cboThangValue
                };
                let response = await API.getDSTLTC(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.length > 0) {
                        await this.$bvModal
                            .msgBoxConfirm(
                                `Bạn có chắc chắn xóa dữ liệu tháng  ${this.cboThangValue}/${this.cboNamValue} không?`,
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
                                    try {
                                        this.loading(true);
                                        let data = {
                                            pNam: this.cboNamValue,
                                            pThang: this.cboThangValue
                                        };
                                        let response = await API.postXoaGiaoSL(
                                            this.axios,
                                            data
                                        );
                                        if (
                                            response.data.error_code ===
                                                "BSS-00000000" &&
                                            response.data.data !== undefined
                                        ) {
                                            this.$toast.success(
                                                "Xóa dữ liệu thành công!"
                                            );
                                            this.loadData();
                                        }
                                    } catch (error) {
                                        this.$toast.error(
                                            error.data.message_detail
                                        );
                                    } finally {
                                        this.loading(false);
                                    }
                                }
                            })
                            .catch(err => {
                                this.loading(false);
                            });
                    } else {
                        this.$toast.error("Không có dữ liệu xóa!");
                    }
                }
            }
        }
    },
    async created() {}
});
</script>

<style src="./GiaoSanLuongNVTC.scss"></style>
