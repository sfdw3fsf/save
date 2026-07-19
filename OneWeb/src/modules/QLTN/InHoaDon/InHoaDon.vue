<template src="./InHoaDon.html"></template>
<style src="./InHoaDon.scss"></style>
<script>
import xlsx from "xlsx";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
import { L10n } from "@syncfusion/ej2-base";
import KyCuoc from "./components/KyCuoc.vue";
import moment from "moment";
import SuaSeri from "./components/SuaSeri";
import EventBus from "@/utils/eventBus";

import {
    CBOInFile,
    LoiCollumns,
    HoaDonCollumns,
    ExcelCollumns,
    dataMock
} from "./common";
import ComboboxGrid from "../components/ComboboxGrid";
import API from "../api/InHoaDon";
import TraCuuThongTinThueBao from "../GachNo/components/TraCuuThongTinThueBao";
import { previewPrint } from "../../../utils/util";
L10n.load({
    "vi-VN": {
        dropdowns: {
            noRecordsTemplate: "Không có dữ liệu",
            actionFailureTemplate: ""
        }
    }
});
export default {
    props: {
        tag: {
            type: String,
            default: ""
        }
    },
    components: {
        appKyCuoc: KyCuoc,
        appCombobox: ComboboxGrid,
        appSuaSeri: SuaSeri,
        appTraCuuThongTinThueBao: TraCuuThongTinThueBao
    },
    provide: {
        multiselect: [CheckBoxSelection]
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            dsbaocao_id: "",
            vkyhoadon: "",
            pChuKyNo: "",
            value: moment(new Date()).subtract(1, "months").endOf("month").toDate(),
            vma_tt: "",
            vdsma_tt: "",
            vdsma_tt_in: "",
            vmanv_tc: "",
            load: false,
            _kt: 0,
            pKyCuoc: "",
            ds_docfile: [],
            dsHoaDon: [],
            //components
            chkInTrucTiep: true,
            mauInCollumn: CBOInFile,
            cboMauIn: [],
            cboMauInValue: "",
            txtMaTT: "",
            txtTenTT: "",
            txtDiaChiTT: "",
            chkQuan: false,
            cboQuanTT: [],
            cboQuanTTValue: [],
            chkAllMIG: "",
            txtMaIG: "",
            txtSoThung: 0,
            txtSeri: 0,
            txtSoSeri: 0,
            txtTongNo: 0,
            gcHoaDon: [],
            gcHoaDonItemsSelected: [],
            gcHoaDonCollumn: HoaDonCollumns,
            gcLoi: [],
            gcLoiCollumn: LoiCollumns,
            tabActived: "tabDanhSach",
            keyReset: 1
        };
    },
    watch: {
        chkAllMIG() {
            this.chkAllMIG_CheckedChanged();
        },
        cboQuanTTValue(nad) {
            console.log(nad);
        }
    },
    methods: {
        async openDialog() {
            this.$refs.dlgInHoaDon.show();
            if (!this.isNullEmpty(this.tag)) {
                let str = this.tag.toString();
                if (str.includes("ShowDialog")) {
                    console.log(str);
                    str = str.replace("ShowDialog", "");
                    console.log("replaced", str);
                    str = str.replace("+", "");
                    console.log("replaced", str);
                }
                if (!this.isNullEmpty(str))
                    this.dsbaocao_id = str.replaceAll(";", ",");
            } else {
                this.dsbaocao_id = "";
            }
            try {
                this.loading(true);
                let data = {
                    loaiHDId: null,
                    nhomBCId: 16,
                    dsBaoCaoId: this.dsbaocao_id,
                    dsBuocXLNId: null
                };
                let response = await API.getDSMauIn(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.cboMauIn = response.data.data;
                }
            } catch (error) {
                this.$toast.error(error.response.data.message_detail);
            } finally {
                this.loading(false);
            }
            this.funcGetDSQuan();
            this.load = true;
            if (!this.isNullEmpty(this.vdsma_tt)) {
                this.txtMaTT = this.vdsma_tt;
                this.txtMaTT_KeyPress();
            }
        },
        closeDialog() {
            this.reset();
            this.$refs.dlgInHoaDon.hide();
        },
        isNullEmpty(value) {
            return value === null || value === "";
        },

        handleKyCuoc(e) {
            this.pKyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
        },
        reset() {
            this.dsbaocao_id = "";
            this.vkyhoadon = "";
            this.vma_tt = "";
            this.vdsma_tt = "";
            this.vdsma_tt_in = "";
            this.vmanv_tc = "";
            this.load = false;
            this._kt = 0;
            this.ds_docfile = [];
            this.dsHoaDon = [];
            this.chkInTrucTiep = false;
            this.cboMauIn = [];
            this.cboMauInValue = "";
            this.txtMaTT = "";
            this.txtTenTT = "";
            this.txtDiaChiTT = "";
            this.chkQuan = false;
            this.cboQuanTT = [];
            this.cboQuanTTValue = [];
            this.chkAllMIG = "";
            this.txtMaIG = "";
            this.txtSoThung = 0;
            this.txtSeri = 0;
            this.txtSoSeri = 0;
            this.txtTongNo = 0;
            this.gcHoaDon = [];
            this.gcHoaDonItemsSelected = [];
            this.gcLoi = [];
        },
        mauInSelectedChanged(value) {
            console.log(value);
            this.cboMauInValue = value;
        },
        selectedTab(value) {
            switch (value) {
                case "tabDanhSach":
                    this.tabActived = "tabDanhSach";
                    break;
                case "tabLoi":
                    this.tabActived = "tabLoi";
                    break;
            }
        },
        btnLayMaTT() {
            this.$refs.dlgTraCuuTTTB.openDialog();
        },
        btnNhapExcel(e) {
            this.ds_docfile = [];
            this.txtMaTT = "";
            const files = e.target.files;
            this.maTT = [];
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$toast.error("File excel không đúng định dạng!");
            }
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
                    if (Object.keys(ws[0]).length != 2) {
                        this.$toast.error("File Excel nhập vào phải đúng định dạng! Chỉ có 2 trường theo MA_TT, STT!");
                        return;
                    }
                    for (const iterator of ExcelCollumns) {
                        if (!ws[0].hasOwnProperty(iterator.fieldName)) {
                            this.$toast.error(
                                "Tên cột trong file không đúng định dạng: " +
                                iterator.fieldName +
                                " (chỉ có 2 trường theo MA_TT, STT)!"
                            );
                            return;
                        }
                    }
                    if (ws.length > 0) {
                        ws.forEach(item => {
                            this.ds_docfile.push(item);
                        });
                    }
                } catch (e) {
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
                if (this.ds_docfile.length > 0) {
                    this.loadExcel(this.ds_docfile);
                }
            };
            fileReader.readAsBinaryString(files[0]);
            this.excelInputPath = files[0].name;
        },
        loadExcel(data) {
            let dsMaTT = data.map(item => item.MA_TT);
            this.txtMaTT = dsMaTT.join(",");
            this.txtMaTT_KeyPress();
        },
        async funcGetDSQuan() {
            try {
                this.loading(true);

                let response = await API.getDSQuan(this.axios, {});
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.cboQuanTT = response.data.data;
                    if (this.cboQuanTT.length > 0) {
                        this.cboQuanTTValue = this.cboQuanTT.map(
                            item => item.QUAN_ID
                        );
                    }
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        async txtMaTT_KeyPress() {
            this.keyReset += 1;
            try {
                this.vdsma_tt = "";
                if (this.isNullEmpty(this.pKyCuoc)) {
                    this.$toast.error("Chưa có chu kỳ tính cước!");
                    return;
                }
                let arrayStr = this.txtMaTT.trim().split(",");
                this.vdsma_tt = arrayStr.join(",");
                let ds_ttloi = this.txtMaTT.trim();
                ds_ttloi = ds_ttloi.replaceAll("'", "");
                let dsma_tt_loi = ds_ttloi.split(",");
                let ds_loi = [];
                try {
                    this.loading(true);
                    let data = {
                        kyCuoc: this.pKyCuoc,
                        ds: Array.isArray(dsma_tt_loi)
                            ? dsma_tt_loi
                            : new Array(dsma_tt_loi)
                    };
                    let response = await API.getKiemTraMaKHTT(this.axios, data);
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        if (
                            Array.isArray(response.data.data) &&
                            response.data.data.length > 0
                        ) {
                            for (const iterator of response.data.data) {
                                ds_loi.push(iterator);
                            }
                        }
                    }
                } catch (error) {
                    this.$toast.error(error.data.message_detail);
                } finally {
                    this.loading(false);
                }

                try {
                    this.loading(true);
                    let data = {
                        kyCuoc: this.pKyCuoc,
                        dsMaTT: dsma_tt_loi
                    };
                    let response = await API.getKiemTraInHD(this.axios, data);
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        if (
                            Array.isArray(response.data.data) &&
                            response.data.data.length > 0
                        ) {
                            for (const iterator of response.data.data) {
                                ds_loi.push(iterator);
                            }
                        }
                    }
                } catch (error) {
                    this.$toast.error(error.data.message_detail);
                } finally {
                    this.loading(false);
                }
                if (ds_loi.length > 0) {
                    this.gcLoi = ds_loi;
                    this.tabActived = "tabLoi";
                } else {
                    this.gcLoi = [];
                    this.tabActived = "tabDanhSach";
                }
                //cho tiep api
                if (this.ds_docfile.length > 0) {
                    let lst = this.ds_docfile.filter(
                        item => !this.isNullEmpty(item.MA_TT)
                    );
                    try {
                        this.loading(true);
                        let lstEx = lst.map(item => {
                            return { stt: item.STT, maTT: item.MA_TT };
                        });
                        let data = {
                            kyCuoc: this.pKyCuoc,
                            chuKyNo: this.pChuKyNo,
                            ds: lstEx
                        };
                        let response = await API.getDSInHoaDonTheoMaTT(
                            this.axios,
                            data
                        );
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            this.dsHoaDon = response.data.data;
                        }
                    } catch (error) {
                        this.$toast.error(error.data.message_detail);
                        this.dsHoaDon = [];
                    } finally {
                        this.loading(false);
                    }
                } else {
                    try {
                        this.loading(true);
                        let data = {
                            kyCuoc: this.pKyCuoc,
                            chuKyNo: this.pChuKyNo,
                            ds: Array.isArray(this.vdsma_tt)
                                ? this.vdsma_tt
                                : new Array(this.vdsma_tt)
                        };
                        let response = await API.getTTNoTheoMaTT(
                            this.axios,
                            data
                        );
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            this.dsHoaDon = response.data.data;
                        }
                    } catch (error) {
                        this.$toast.error(error.data.message_detail);
                        this.dsHoaDon = [];
                    } finally {
                        this.loading(false);
                    }
                }
                // this.HienThiDS(this.dsHoaDon);
            } catch (error) {
                console.log(error);
            } finally {
                this.HienThiDS(this.dsHoaDon);
            }
        },
        HienThiDS(data) {
            if (data.length > 0) {
                this.gcHoaDon = data;
            } else {
                this.gcHoaDon = [];
            }
        },
        selectedItemsChangedHoaDon(value) {
            this.gcHoaDonItemsSelected = value;
        },
        formatPrice(value) {
            return new Intl.NumberFormat("vi-VN", {
                style: "currency",
                currency: "VND"
            }).format(value);
        },
        selectedRowChangedHoaDon(value) {
            if (!this.isNullEmpty(value))
                try {
                    this.txtTenTT = value.TEN_TT;
                    this.txtDiaChiTT = value.DIACHI_TT;
                    this.txtSoThung = value.SO_THUNG;
                    this.txtSeri = value.SERI;
                    this.txtSoSeri = value.SO_SERI;
                    this.txtTongNo = value.TONGNO;// this.formatPrice(value.TONGNO);
                    this.txtMaIG = value.MAIN_GHEP;
                } catch (error) {
                    console.log(error);
                }
        },
        chkAllMIG_CheckedChanged() {
            if (this.chkAllMIG) {
                for (const iterator of this.dsHoaDon) {
                    if (iterator.MAIN_GHEP == this.txtMaIG.trim()) {
                        this.refs.gridHD.selectedValues.push(iterator.MA_TT);
                    } else {
                        const index = this.refs.gridHD.selectedValues.indexOf(
                            iterator
                        );
                        if (index > -1) {
                            this.refs.gridHD.selectedValues.splice(index, 1);
                        }
                    }
                }
            } else {
                for (const iterator of this.dsHoaDon) {
                    this.refs.gridHD.selectedValues.push(iterator.MA_TT);
                }
            }
        },
        txtMaIGEnter() {
            if (this.txtMaTT.trim() != "") {
                this.chkAllMIG_CheckedChanged();
            }
        },
        txtMaTTEnter() {
            this.ds_docfile = []
            if (!this.isNullEmpty(this.txtMaTT)) {
                this.txtMaTT_KeyPress();
            }
        },
        btnSuaSeri() {
            let lstHD = [];
            this.gcHoaDonItemsSelected.forEach(element => {
                this.gcHoaDon.forEach(item => {
                    if (item.MA_TT == element) {
                        lstHD.push(item);
                        return;
                    }
                });
            });
            if (lstHD.length == 0) {
                this.$toast.error("Bạn chưa chọn phiếu để sửa!");
                return;
            }
            this.$refs.dlgSuaSeriIHD.dataSeri.soThung = this.txtSoThung;
            this.$refs.dlgSuaSeriIHD.dataSeri.seri = this.txtSeri;
            this.$refs.dlgSuaSeriIHD.dataSeri.soSeri = this.txtSoSeri;
            this.$refs.dlgSuaSeriIHD.dialogOpen();
        },
        async updateEditSeri(data) {
            if (
                this.isNullEmpty(data.soThung) ||
                this.isNullEmpty(data.seri) ||
                this.isNullEmpty(data.soSeri)
            ) {
                this.$toast.error("Bạn chưa nhập Số thùng, Seri, Số Seri!");
                return;
            }
            if (!this.isNumeric(data.soThung)) {
                this.$toast.error("Dữ liệu số thùng phải là kiểu số !");
                return;
            }
            if (!this.isNumeric(data.soSeri)) {
                this.$toast.error("Dữ liệu Số seri phải là kiểu số !");
                return;
            }
            let so_thung = data.soThung;
            let seri = data.seri;
            let so_seri = data.soSeri;
            let lstHD = [];
            this.gcHoaDonItemsSelected.forEach(element => {
                this.gcHoaDon.forEach(item => {
                    if (item.MA_TT == element) {
                        lstHD.push(item);
                        return;
                    }
                });
            });
            let ma_tt = "";
            //call api
            try {
                this.loading(true);
                let dsData = [];
                let dsma_tt = [];
                lstHD.forEach(item => {
                    dsData.push({
                        maTT: item.MA_TT,
                        seri: item.SERI,
                        soSeri: item.SO_SERI,
                        soThung: item.SO_THUNG,
                        tienHD: item.TONGNO_HD,
                        htttId: item.HTTT_ID,
                        tongNo: null,
                        noPS: null,
                        noDK: null
                    });
                    dsma_tt.push(item.MA_TT);
                });
                let data = {
                    kyCuoc: this.pKyCuoc,
                    soThung: so_thung,
                    seri: seri,
                    soSeriDau: so_seri,
                    ds: dsData
                };
                let response = await API.putEditSeri(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.$refs.dlgSuaSeriIHD.dialogClose();
                    if (dsma_tt.length > 0) {

                        this.inHoaDon2(dsma_tt);
                    }
                    else { this.$toast.success("Cập nhật dữ liệu thành công!") }
                    //this.$toast.success("Cập nhật dữ liệu thành công!");
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
            // wa
            this.txtMaTT_KeyPress();
        },
        eventGetTTTB(data) {
            this.txtMaTT = data.MA_TT;
            this.$refs.inputMaTT.focus();
        },
        btnInHoaDon: async function () {
            //   await this.$refs.dlgAddSeriIHD.dialogOpen();
            let dem = 0;
            if (this.isNullEmpty(this.txtMaTT.trim()))
                return this.$toast.error("Bạn chưa nhập mã thanh toán!");
            let ds_ttloi = this.txtMaTT.trim();
            ds_ttloi = ds_ttloi.replaceAll("'", "");
            let dsma_tt_loi = ds_ttloi.split(",");
            let ds_loi = [];
            try {
                this.loading(true);
                let data = {
                    kyCuoc: this.pKyCuoc,
                    ds: Array.isArray(dsma_tt_loi)
                        ? dsma_tt_loi
                        : new Array(dsma_tt_loi)
                };
                let response = await API.getKiemTraMaKHTT(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (
                        Array.isArray(response.data.data) &&
                        response.data.data.length > 0
                    ) {
                        for (const iterator of response.data.data) {
                            ds_loi.push(iterator);
                        }
                    }
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }

            try {
                this.loading(true);
                let data = {
                    kyCuoc: this.pKyCuoc,
                    dsMaTT: dsma_tt_loi
                };
                let response = await API.getKiemTraInHD(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (
                        Array.isArray(response.data.data) &&
                        response.data.data.length > 0
                    ) {
                        for (const iterator of response.data.data) {
                            ds_loi.push(iterator);
                        }
                    }
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
            if (ds_loi.length > 0) {
                this.gcLoi = ds_loi;
                this.tabActived = "tabLoi";
                return;
            } else {
                this.gcLoi = [];
                this.tabActived = "tabDanhSach";
            }
            let lstChon = this.gcHoaDon.filter(e =>
                this.gcHoaDonItemsSelected.includes(e.MA_TT)
            );
            if (lstChon.length <= 0) {
                this.$toast.error("Bạn chưa chọn phiếu để in!");
                return;
            }
            if (lstChon.filter(e => e.TONGNO <= 0).length > 0) {
                this.$toast.error(
                    "Không được phép in hóa đơn khách hàng hết nợ hoặc dư có!"
                );
                return;
            }
            // //Cảnh báo khách hàng đã in có in lại không
            let dsma_tt = lstChon
                .filter(
                    e =>
                        !this.isNullEmpty(e.SO_SERI.toString()) &&
                        (e.SO_SERI.toString() != "0" ||
                            !this.isNullEmpty(e.SO_SERI.toString()))
                )
                .map(item => item.MA_TT);
            let kt_dain = "";
            if (dsma_tt.length > 0) {
                kt_dain = dsma_tt.join(",");
            }
            if (kt_dain != "") {
                await this.$bvModal
                    .msgBoxConfirm(
                        `Danh sách khách hàng :\r\n ${kt_dain} \r\n đã in hóa đơn! Bạn có thực sự muốn in lại không ?`,
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
                            this.$refs.dlgAddSeriIHD.dataSeri = {
                                soThung: this.txtSoThung,
                                seri: this.txtSeri,
                                soSeri: this.txtSoSeri
                            };
                            await this.$refs.dlgAddSeriIHD.dialogOpen();

                        } else {
                            return;
                        }
                    })
                    .catch(err => {
                        this.loading(false);
                    });
            }

        },
        async inHoaDon2(ds) {
            const body = {
                kyCuoc: this.pKyCuoc,
                chuKyNo: this.pChuKyNo,
                dsMaTT: ds,
                mauIn: this.cboMauInValue.BAOCAO_ID
            }
            //let response = await API.getInHoaDon(this.axios, body, { responseType: 'blob' });
            //previewPrint(response.data);
            const baseUrl = process.env.API;
            // if (this.inv_id === 30) {
            try{
                await this.axios({
                url:
                    `${baseUrl}/web-thuno/api/thu-no/in-hoa-don/in-hoa-don-khdn`,
                method: "POST",
                responseType: "blob",
                data: body
            }).then((response) => {
                
                previewPrint(response.data)
                //         this.noidung_in = response.data;
            })
            }
            catch (error) {
            this.$root.toastError("Báo cáo này chưa được thiết kế mẫu !");
            } finally {
                this.loading(false);
            }

            // }

        },
        addSeri(data) {
            //validate data
            if (
                this.isNullEmpty(data.soThung) ||
                this.isNullEmpty(data.soSeri) ||
                this.isNullEmpty(data.seri)
            ) {
                this.$toast.error("Bạn chưa nhập Số thùng, Seri, Số Seri!");
                return;
            }
            if (!this.isNumeric(data.soThung)) {
                this.$toast.error("Dữ liệu số thùng phải là kiểu số !");
                return;
            }
            if (!this.isNumeric(data.soSeri)) {
                this.$toast.error("Dữ liệu Số seri phải là kiểu số !");
                return;
            }
            let so_thung = parseFloat(data.soThung);
            let seri = data.seri;
            let so_seri = parseInt(data.soSeri);
            let lstChon = this.gcHoaDon.filter(e =>
                this.gcHoaDonItemsSelected.includes(e.MA_TT)
            );
            this.vdsma_tt_in = lstChon.map(e => e.MA_TT).join(",");
            if (this.isNullEmpty(this.pKyCuoc)) {
                this.$toast.error("Chưa có chu kỳ tính cước!");
                return;
            }
            this.inHoaDon(this.vdsma_tt_in, this.pKyCuoc, this.cboMauInValue);
            if (this._kt == 0) {
                this.updateEditSeri(data);
            }
            this.txtMaTT_KeyPress();
            this._kt = 0;
        },
        isNumeric(str) {
            return !isNaN(str) && !isNaN(parseFloat(str));
        },
        inHoaDon(dsMaTTIn, kycuoc, fileName) {
            this._kt = 0;
            if (fileName.TEN_FILE === "VT01") {
                this.inHoaDonVT01V2(dsMaTTIn, kycuoc);
            } else if (fileName.TEN_FILE === "BNTTx4") {
                this.inBienNhan4XV2(dsMaTTIn, kycuoc);
            } else if (fileName.TEN_FILE === "BNTTx2") {
                this.inBienNhan2XV2(dsMaTTIn, kycuoc);
            } else if (fileName.TEN_FILE == "TBCUOC") {

            } else if (fileName.TEN_FILE == "PHIEUCHI") {
            } else if (fileName.TEN_FILE == "VTEZPAY") {
                this._kt = 1;
            }
                        
            this.$refs.dlgAddSeriIHD.dialogClose();
        },
        async inHoaDonVT01V2(dsMaTT, kyHoaDon) {
            dsMaTT = dsMaTT.replace(/'/g, "");
            let invoiceRes = [];
            try {
                this.loading(true);
                let data = {
                    kyHoaDon: kyHoaDon,
                    dsMaTT: dsMaTT
                };
                let response = await API.getHoaDonVAT(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    invoiceRes = response.data.data;
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
            this.printInvoice(invoiceRes);
        },
        async inBienNhan4XV2(dsMaTT, kyHoaDon) {
            dsMaTT = dsMaTT.replace(/'/g, "");
            let invoiceRes = [];
            try {
                this.loading(true);
                let data = {
                    kieuIn: 1,
                    kyHoaDon: kyHoaDon,
                    dsMaTT: dsMaTT
                };
                let response = await API.getInBienNhan(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    invoiceRes = response.data.data;
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
            this.printInvoice(invoiceRes);
        },
        async inBienNhan2XV2(dsMaTT, kyHoaDon) {
            dsMaTT = dsMaTT.replace(/'/g, "");
            let invoiceRes = [];
            try {
                this.loading(true);
                let data = {
                    kieuIn: 0,
                    kyHoaDon: kyHoaDon,
                    dsMaTT: dsMaTT
                };
                let response = await API.getInBienNhan(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    invoiceRes = response.data.data;
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
            this.printInvoice(invoiceRes);
        },
        printInvoice(invoiceRes) {
            let strTemplate = "";
            invoiceRes.forEach(item => {
                item.forEach((e, i) => {
                    strTemplate += e.NOIDUNG + "\r\n";
                });
            });
            console.log(strTemplate);
            if (strTemplate) {
                if (this.chkInTrucTiep) {
                    this.downloadPdf(strTemplate);
                } else {
                    this.downloadText(strTemplate, "print.txt");
                }
            }
        },
        downloadText(text, fileName = null) {
            var element = document.createElement("a");
            let data = text;
            // text.forEach(item => {
            //     data += item + "\r\n";
            // });
            element.setAttribute(
                "href",
                "data:text/plain;charset=utf-8," + encodeURIComponent(data)
            );
            element.setAttribute(
                "download",
                fileName ? fileName : this.cboMauInValue.FILE_NAME
            );

            element.style.display = "none";
            document.body.appendChild(element);
            element.click();
            document.body.removeChild(element);
        },
        downloadPdf(text) {
            var iframe = document.getElementById("textfile").contentWindow;
            let data = text;
            // text.forEach(item => {
            //     data += item + "<br>";
            // });
            data = `<pre>${data}</pre>`;
            iframe.document.write(data);
            iframe.document.close(); //important!
            iframe.focus(); //IE fix
            iframe.print();
        },
        onKyHoaDonChange: function (args) {
            if (args.value != null) {
                this.pChuKyNo = moment(args.value).format("YYYYMM") + '01';
                this.gcHoaDon = [];
                this.txtTongNo = "";
                if (this.txtMaTT != "")
                    this.txtMaTTEnter();
            }

        },
    },

    mounted() { },
    created() {
        EventBus.$on("getTTTB", this.eventGetTTTB);
    },
    destroyed() {
        EventBus.$off("getTTTB", this.eventGetTTTB);
    }
};
</script>
