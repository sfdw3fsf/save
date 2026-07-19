<template src="./InUyNhiem.html"></template>
<style src="./InUyNhiem.scss"></style>
<script>
import { HeaderInPhieuUyNhiem, DSHoaDonCollumn } from "./common";
import moment from "moment";
import KyCuoc from "./components/KyCuoc";
import breadcrumb from "@/components/breadcrumb";
import API from "../api/InPhieuUyNhiem";
import moneyToText from "./utils/moneyToText";
import { previewPrint, downloadText } from "../../../utils/util";
export default {
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc
    },
    data() {
        return {
            header: HeaderInPhieuUyNhiem,
            kyCuoc: "",
            txtTongNo: "",
            txtTenTT: "",
            txtMaTT: "",
            txtDiaChiTT: "",
            cboHinhThucTT: "",
            cboHinhThucTTValue: "",
            txtMST: "",
            txtSoTK: "",
            txtNganHang: "",
            gridHoaDon: [],
            gridHoaDonItemsSelected: [],
            gridHoaDonCollumn: DSHoaDonCollumn,
            chkNganHang: "chkNganHang",
            chkKhoBac: "chkKhoBac",
            chkChecked: "chkNganHang",
            chkInTrucTiep: false,
            //khaibao
            dsbaocao_id: "",
            vkyhoadon: "",
            vma_tt: "",
            vdsma_tt: "",
            vdsma_tt_in: "",
            vmanv_tc: "",
            load: false,
            iframe: {
                visibility: "hidden"
            }
            //
        };
    },
    watch: {
        async chkChecked() {
            await this.danhSach();
        },
        cboHinhThucTTValue() {
            if (this.load) {
                this.danhSach();
            }
        }
    },
    computed: {},
    methods: {
        handleKyCuoc(e) {
            this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
        },
        rowClickSelected(...e) {
            if (e) {
                let dataRow = e[1];
                // console.log(dataRow);
                if (!this.isNullEmpty(dataRow)) {
                    this.txtMaTT = dataRow.MA_TT;
                    this.txtTenTT = dataRow.TEN_TT;
                    this.txtDiaChiTT = dataRow.DIACHI_TT;
                    this.txtMST = dataRow.MST;
                    this.txtSoTK = dataRow.STK;
                    this.txtNganHang = dataRow.TEN_NH;
                    this.txtTongNo = dataRow.TONGNO;
                }
            }
        },
        isNullEmpty(value) {
            return value === null || value === "";
        },
        async danhSach() {
            try {
                let vloai_nh = 0;
                let vdshttt_id = null;
                if (this.chkChecked == this.chkKhoBac) {
                    vloai_nh = 2; // 2: Kho bạc
                } else {
                    vloai_nh = 1; // 1: Ngân hàng
                }
                vdshttt_id = this.cboHinhThucTTValue;
                this.vdsma_tt = this.txtMaTT.trim();
                if (this.vdsma_tt == "") {
                    this.vdsma_tt = null;
                }
                try {
                    this.loading(true);
                    let data = {
                        kyCuoc: this.kyCuoc,
                        loaiNH: vloai_nh,
                        dsHTTTId: vdshttt_id,
                        dsMaTT: this.vdsma_tt
                    };
                    let response = await API.getDSHoaDonTheoTT(
                        this.axios,
                        data
                    );
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        this.gridHoaDon = response.data.data;
                        if (this.gridHoaDon.length <= 0) {
                            this.$toast.error("Không tìm thấy thông tin!");
                        } else {
                            this.$toast.success("Lấy danh sách thành công!");
                        }
                    }
                } catch (error) {
                    this.$toast.error(error.data.message_detail);
                } finally {
                    this.loading(false);
                }
            } catch (error) {
                console.log(error);
            }
        },
        async btnDanhSach() {
            await this.danhSach();
        },
        searchDSFLMaTT() {
            if (!this.isNullEmpty(this.txtMaTT)) {
                this.danhSach();
            }
        },
        selectedItemsChangedHoaDon(data) {
            this.gridHoaDonItemsSelected = data;
        },
        btnIn() {
            if (this.isNullEmpty(this.kyCuoc)) {
                this.$toast.error("Chưa có chu kỳ tính cước!");
                return;
            }
            let lstHD = [];
            this.gridHoaDonItemsSelected.forEach(element => {
                this.gridHoaDon.forEach(item => {
                    if (item.MA_TT == element) {
                        lstHD.push(item);
                        return;
                    }
                });
            });
            if (lstHD.length <= 0) {
                this.$toast.error("Bạn chưa chọn phiếu để in!");
                return;
            }
            this.vdsma_tt_in = "";
            this.vdsma_tt_in = this.gridHoaDonItemsSelected.join(",");
            let chuKy = this.kyCuoc.substring(this.kyCuoc.length - 2);
            if (this.isNullEmpty(chuKy)) {
                this.$toast.error("Chưa có chu kỳ tính cước!");
                return;
            }
            if (this.cboHinhThucTTValue == 3) {
                if (this.chkChecked == this.chkKhoBac) {
                    this.inKhoBac(this.vdsma_tt_in, this.kyCuoc);
                } else {
                    this.inNganHang(this.vdsma_tt_in, this.kyCuoc);
                }
            }
        },
        space: function(count) {
            return "".padStart(count);
        },
        textAlign: function(str, length) {
            var spaceCount = length - str.length,
                padleftCount = spaceCount / 2,
                padRightCount = spaceCount - padleftCount;
            if (padleftCount > 0) {
                str = str.padStart(padleftCount, " ");
            }
            if (padRightCount > 0) {
                str = str.padEnd(padRightCount, " ");
            }
            return str;
        },
        subString: function(str, length) {
            //  if (str == null) return null;
            str = str.trim();
            if (str.length > length) {
                str = String.subString(str, length);
            }
            return str;
        },
        handleString(chuoi, _length) {
            let kq = "";
            let _chuoi = "";
            try {
                if (chuoi.length <= _length) {
                    _chuoi = chuoi;

                    for (let i = 0; i < _length - chuoi.Length; i++) {
                        _chuoi += " ";
                    }
                } else {
                    _chuoi = "";
                    if (chuoi == null || chuoi == "") return "";
                    for (let i = 0; i < _length; i++) {
                        _chuoi += chuoi[i];
                    }
                }
                kq = _chuoi;
            } catch (ex) {
                this.$toast.error(ex);
            }
            return kq;
        },
        async inKhoBac(dsma_tt, kyHoaDon) {
            let vloai_nh = null;
            if (this.chkChecked == this.chkKhoBac) {
                vloai_nh = 2; // 2: Kho bạc
            } else {
                vloai_nh = 1; // 1: Ngân hàng
            }
            let vdshttt_id = this.cboHinhThucTTValue;
            try {
                this.loading(true);
                let data = {
                    typePrint: this.chkInTrucTiep == true ? "pdf" : "text",
                    kyCuoc: this.kyCuoc,
                    loaiNH: vloai_nh,
                    dsHTTTId: vdshttt_id,
                    kieuId: 1,
                    dsMaTT: dsma_tt
                };
                const baseUrl = process.env.API;
                await this.axios({
                    url: `${baseUrl}/web-thuno/api/thu-no/in-uy-nhiem/in-phieu`,
                    method: "POST",
                    responseType: "blob",
                    data: data
                }).then(response => {
                    if (this.chkInTrucTiep) {
                        previewPrint(response.data);
                    } else {
                        downloadText(response.data, "InKhoBac.txt");
                    }
                });
            } catch (error) {
                let res = await JSON.parse(await error.data.text());
                this.$toast.error(res.message_detail);
            } finally {
                this.loading(false);
            }
        },
        download(fileName, text) {
            var element = document.createElement("a");
            let data = "";
            text.forEach(item => {
                data += item + "\n";
            });
            element.setAttribute(
                "href",
                "data:text/plain;charset=utf-8," + encodeURIComponent(data)
            );
            element.setAttribute("download", fileName);

            element.style.display = "none";
            document.body.appendChild(element);
            element.click();
            document.body.removeChild(element);
        },
        print(text) {
            var iframe = document.getElementById("textfile").contentWindow;
            let data = "";
            text.forEach(item => {
                data += item + "<br>";
            });
            data = `<pre>${data}</pre>`;
            iframe.document.write(data);
            iframe.document.close(); //important!
            setTimeout(() => {
                iframe.focus(); //IE fix
                iframe.print();
            }, 500);
        },
        async inNganHang(dsma_tt, kyHoaDon) {
            let vloai_nh = null;
            if (this.chkChecked == this.chkKhoBac) {
                vloai_nh = 2; // 2: Kho bạc
            } else {
                vloai_nh = 1; // 1: Ngân hàng
            }
            let vdshttt_id = this.cboHinhThucTTValue;
            try {
                this.loading(true);
                let data = {
                    typePrint: this.chkInTrucTiep == true ? "pdf" : "text",
                    kyCuoc: this.kyCuoc,
                    loaiNH: vloai_nh,
                    dsHTTTId: vdshttt_id,
                    kieuId: 1,
                    dsMaTT: dsma_tt
                };
                const baseUrl = process.env.API;
                await this.axios({
                    url: `${baseUrl}/web-thuno/api/thu-no/in-uy-nhiem/in-phieu`,
                    method: "POST",
                    responseType: "blob",
                    data: data
                }).then(response => {
                    if (this.chkInTrucTiep) {
                        previewPrint(response.data);
                    } else {
                        downloadText(response.data, "InUyNhiem.txt");
                    }
                });
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        spaceTextNganHang() {
            /*  try {
                let iDC = 0,
                    iDongMayIn = 66,
                    Align = -4,
                    iKCN = 0,
                    iInTenThuQuy = 1,
                    iInDungThue = 1,
                    iLDKT = 0;
                let sDonViThucHien = "",
                    sFormat = "";
                if (iDongMayIn != 66) iDongMayIn = 66;
                iDC = iDC - 1;
                let chuoi_in_trai = "";
                let chuoi_in_phai = "";
                let dsmatt = dsma_tt.split(",");
                let _tong = 60;
                let sChuoiCanIn = [];
                for (
                    let index = 0;
                    index < _tong * (dsmatt.Length + 1);
                    index++
                ) {
                    sChuoiCanIn.push("");
                }
                let vdshttt_id = "";
                let vloai_nh = 0;
                let kh_cach = 9;
                let canchuan = 60;
                for (let j = 0; j < dsmatt.length; j++) {
                    this.vma_tt = dsmatt[j];
                    if (this.chkChecked == this.chkKhoBac) {
                        vloai_nh = 2; // 2: Kho bạc
                    } else {
                        vloai_nh = 1; // 1: Ngân hàng
                    }
                    vdshttt_id = this.cboHinhThucTTValue;

                    let dsThongTinKhachHang = []; //cho api
                    try {
                        this.loading(true);
                        let payload = {
                            pKyCuoc: this.kyCuoc,
                            pLoaiNH: vloai_nh,
                            pDsHTTTId: vdshttt_id,
                            pDsMaTT: this.vma_tt
                        };
                        let response = await API.getTTNoHTTT(
                            this.axios,
                            payload
                        );
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            dsThongTinKhachHang = response.data.data;
                        }
                    } catch (error) {
                        this.$toast.error(error.data.message_detail);
                    }
                    if (dsThongTinKhachHang.length <= 0) return;
                    let dbCongThueVAT = 0,
                        dbTongCong = 0,
                        dbCuocVienThong_KhongThue = 0;
                    if (dsThongTinKhachHang.length > 0) {
                        dbCongThueVAT = dsThongTinKhachHang[0].THUE;
                        dbTongCong = dsThongTinKhachHang[0].TONGNO;
                        dbCuocVienThong_KhongThue =
                            dsThongTinKhachHang[0].NOGOC;
                    }
                    let dbCongThueVAT1 = 0,
                        dbTongCong1 = 0,
                        dbCuocVienThong_KhongThue1 = 0;
                    if (dsThongTinKhachHang.length >= 2) {
                        dbCongThueVAT1 = dsThongTinKhachHang[0].THUE;
                        dbTongCong1 = dsThongTinKhachHang[0].TONGNO;
                        dbCuocVienThong_KhongThue1 =
                            dsThongTinKhachHang[0].NOGOC;
                    }
                    let dtList = [];
                    try {
                        this.loading(true);
                        let response = await API.getThamSoMD(this.axios, {
                            pKieuId: 1
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            dtList = response.data.data;
                        }
                    } catch (error) {
                        this.$toast.error(error.data.message_detail);
                    }
                    let ten_dvql = "";
                    let mst_dvql = "";
                    let stk_dvql = "";
                    dtList.forEach(item => {
                        if (item.MA_TS == "TEN_DVC0") {
                            ten_dvql = item.GIATRI == null ? "" : item.GIATRI;
                        }
                        if (item.MA_TS == "TAIKHOAN_DVTT") {
                            stk_dvql = item.GIATRI == null ? "" : item.GIATRI;
                        }
                        if (item.MA_TS == "MST_DVTT") {
                            mst_dvql = item.GIATRI == null ? "" : item.GIATRI;
                        }
                    });

                    chuoi_in_trai = this.space(55 + Align) + "170/10-2014";
                    sChuoiCanIn[2 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(33 + Align) +
                        new Date().getDate() +
                        this.space(10 + Align) +
                        (new Date().getMonth() + 1) +
                        this.space(10 + Align) +
                        new Date().getFullYear();

                    sChuoiCanIn[4 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(22 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TEN_TT, 35);
                    sChuoiCanIn[6 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, 65) +
                        this.space(kh_cach - 6) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(40 + Align) +
                        this.handleString(dsThongTinKhachHang[0].STK, 14);

                    sChuoiCanIn[7 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;
                    chuoi_in_trai =
                        this.space(15 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TEN_NH, 20) +
                        this.space(15 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TENTINH, 20);

                    sChuoiCanIn[8 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach + 10) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(20 + Align) +
                        this.subString("tên DV bán hàng : " + ten_dvql, 46);
                    sChuoiCanIn[10 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach + 10) +
                        chuoi_in_trai;

                    chuoi_in_trai = this.space(40 + Align) + "1226659985"; //this.handleString(stk_dvql, 46);
                    sChuoiCanIn[11 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(15 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TEN_NH, 20) +
                        this.space(12 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TENTINH, 15) +
                        this.space(5 + Align) +
                        "0071003954801";
                    sChuoiCanIn[13 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, 80) +
                        this.space(kh_cach) +
                        this.handleString(chuoi_in_trai, 15);

                    chuoi_in_trai =
                        this.space(25 + Align) +
                        this.handleString(dsThongTinKhachHang[0].MA_TT, 15) +
                        this.space(5 + Align) +
                        "01" +
                        this.space(6 + Align) +
                        "12" +
                        this.space(6 + Align) +
                        "2014";
                    sChuoiCanIn[14 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(30 + Align) +
                        " 1 GBC tháng " +
                        this.space(5 + Align) +
                        "09/2014";
                    sChuoiCanIn[15 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    if (dbTongCong < 0) {
                        dbTongCong = dbTongCong * -1;
                    }
                    let DocTien = moneyToText(dbTongCong);
                    let TienBangChu = DocTien;
                    let s = "",
                        s1 = "",
                        s2 = "";

                    s = TienBangChu;
                    s1 = "";
                    s2 = "";
                    s1 = s;
                    let iViTri = s.indexOf(" ", 0);
                    while (iViTri > 0) {
                        if (iViTri > 20) {
                            s1 = s.substring(0, iViTri);
                            s2 = s.substring(iViTri);
                            break;
                        }
                        iViTri = s.indexOf(" ", iViTri + 1);
                    }
                    chuoi_in_trai = this.space(25 + Align) + s1;
                    sChuoiCanIn[17 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;
                    if (s2 != "") {
                        chuoi_in_trai =
                            this.space(15 + Align) +
                            s2 +
                            this.space(10 + Align) +
                            dbTongCong;
                        sChuoiCanIn[17 + _tong * j + iDC] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    } else {
                        chuoi_in_trai = this.space(45 + Align) + dbTongCong;
                        sChuoiCanIn[17 + _tong * j + iDC] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    }
                    chuoi_in_trai = this.space(55 + Align) + "102100000092025"; //CatChuoi(stk_dvql, 46);
                    sChuoiCanIn[18 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai = this.space(25 + Align) + s1;
                    sChuoiCanIn[24 + _tong * j + iDC] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;
                    if (s2 != "") {
                        chuoi_in_trai =
                            this.space(15 + Align) +
                            s2 +
                            this.space(30 + Align) +
                            dbTongCong;
                        sChuoiCanIn[25 + _tong * j + iDC] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    } else {
                        chuoi_in_trai = this.space(45 + Align) + dbTongCong;
                        sChuoiCanIn[20 + _tong * j + iDC] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    }
                }
                if (this.chkInTrucTiep) {
                    this.print(sChuoiCanIn);
                } else {
                    this.download("In", sChuoiCanIn);
                }
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            } */
        },
        spaceTextKhoBac() {
            /*  try {
                let iKCD = 0, //KhoangCachDoc
                    iKCN = 0, //KhoangCachNgang
                    iSoDong = 66,
                    Align = -4,
                    iDongMayIn,
                    iInTenThuQuy = 1,
                    iInDungThue = 1,
                    iLDKT = 0;
                let sDonViThucHien = "",
                    sFormat = "";
                if (iDongMayIn != 0) iDongMayIn = 66;
                iKCD = iKCD - 1;
                let chuoi_in_trai = "";
                let chuoi_in_phai = "";
                let dsmatt = dsma_tt.split(",");
                let _tong = 60;
                let sChuoiCanIn = [];
                for (
                    let index = 0;
                    index < _tong * (dsmatt.Length + 1);
                    index++
                ) {
                    sChuoiCanIn.push("");
                }
                let vdshttt_id = "";
                let vloai_nh = 0;
                let kh_cach = 9;
                let canchuan = 60;
                for (let j = 0; j < dsmatt.length; j++) {
                    this.vma_tt = dsmatt[j];
                    if (this.chkChecked == this.chkKhoBac) {
                        vloai_nh = 2; // 2: Kho bạc
                    } else {
                        vloai_nh = 1; // 1: Ngân hàng
                    }
                    vdshttt_id = this.cboHinhThucTTValue;

                    let dsThongTinKhachHang = []; //cho api
                    try {
                        this.loading(true);
                        let payload = {
                            pKyCuoc: this.kyCuoc,
                            pLoaiNH: vloai_nh,
                            pDsHTTTId: vdshttt_id,
                            pDsMaTT: this.vma_tt
                        };
                        let response = await API.getTTNoHTTT(
                            this.axios,
                            payload
                        );
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            dsThongTinKhachHang = response.data.data;
                            // if (dsThongTinKhachHang.length == 0) {
                            //     dsThongTinKhachHang = [
                            //         {
                            //             MA_TT: "HPG-05-004572",
                            //             TEN_TT:
                            //                 "Trường Cao Đẳng Nghề Giao Thông Vận Tải Trung Ương 2",
                            //             DIACHI_TT:
                            //                 "- Chân Cầu Treo - Xã Hồng Thái - Huyện An Dương",
                            //             STK: 0,
                            //             MST: null,
                            //             TEN_NH: "Kho Bạc Hải An",
                            //             TENTINH: "Hải Phòng",
                            //             NOGOC: "198000",
                            //             THUE: "19800",
                            //             TONGNO: "217800"
                            //         }
                            //     ];
                            // }
                        }
                    } catch (error) {
                        this.$toast.error(error.data.message_detail);
                    }
                    if (dsThongTinKhachHang.length <= 0) return;
                    let dbCongThueVAT = 0,
                        dbTongCong = 0,
                        dbCuocVienThong_KhongThue = 0;
                    if (dsThongTinKhachHang.length > 0) {
                        dbCongThueVAT = dsThongTinKhachHang[0].THUE;
                        dbTongCong = dsThongTinKhachHang[0].TONGNO;
                        dbCuocVienThong_KhongThue =
                            dsThongTinKhachHang[0].NOGOC;
                    }
                    let dbCongThueVAT1 = 0,
                        dbTongCong1 = 0,
                        dbCuocVienThong_KhongThue1 = 0;
                    if (dsThongTinKhachHang.length >= 2) {
                        dbCongThueVAT1 = dsThongTinKhachHang[0].THUE;
                        dbTongCong1 = dsThongTinKhachHang[0].TONGNO;
                        dbCuocVienThong_KhongThue1 =
                            dsThongTinKhachHang[0].NOGOC;
                    }
                    let dtList = [];
                    try {
                        this.loading(true);
                        let response = await API.getThamSoMD(this.axios, {
                            pKieuId: 1
                        });
                        if (
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data !== undefined
                        ) {
                            dtList = response.data.data;
                        }
                    } catch (error) {
                        this.$toast.error(error.data.message_detail);
                    }
                    let ten_dvql = "";
                    let mst_dvql = "";
                    let stk_dvql = "";
                    dtList.forEach(item => {
                        if (item.MA_TS == "TEN_DVC0") {
                            ten_dvql = item.GIATRI == null ? "" : item.GIATRI;
                        }
                        if (item.MA_TS == "TAIKHOAN_DVTT") {
                            stk_dvql = item.GIATRI == null ? "" : item.GIATRI;
                        }
                        if (item.MA_TS == "MST_DVTT") {
                            mst_dvql = item.GIATRI == null ? "" : item.GIATRI;
                        }
                    });

                    chuoi_in_trai =
                        this.space(50 + Align) +
                        this.handleString(dsThongTinKhachHang[0].MA_TT, 15) +
                        this.space(5 + Align) +
                        " 140";

                    sChuoiCanIn[2 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(25 + Align) +
                        new Date().getDay() +
                        this.space(10 + Align) +
                        new Date().getMonth() +
                        this.space(10 + Align) +
                        new Date().getFullYear();

                    sChuoiCanIn[4 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(18 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TEN_TT, 35) +
                        this.space(8 + Align) +
                        this.handleString(dsThongTinKhachHang[0].STK, 14);

                    sChuoiCanIn[7 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, 65) +
                        this.space(kh_cach - 6) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(20 + Align) +
                        this.handleString(dsThongTinKhachHang[0].STK, 14) +
                        this.space(8 + Align) +
                        this.handleString(stk_dvql, 20);

                    sChuoiCanIn[8 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(20 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TEN_NH, 20) +
                        this.space(10 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TENTINH, 20);

                    sChuoiCanIn[9 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;
                    chuoi_in_trai =
                        this.space(20 + Align) +
                        this.subString("tt kinh doanh : " + ten_dvql, 46);

                    sChuoiCanIn[11 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;
                    chuoi_in_trai =
                        this.space(25 + Align) +
                        this.handleString(stk_dvql, 46);

                    sChuoiCanIn[12 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(20 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TEN_NH, 30) +
                        this.space(10 + Align) +
                        this.handleString(dsThongTinKhachHang[0].TENTINH, 20);

                    sChuoiCanIn[13 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        this.handleString(chuoi_in_trai, 15);

                    chuoi_in_trai =
                        this.space(25 + Align) +
                        this.handleString(dsThongTinKhachHang[0].MA_TT, 15) +
                        this.space(10 + Align) +
                        "01" +
                        this.space(9 + Align) +
                        "12" +
                        this.space(9 + Align) +
                        "2014";

                    sChuoiCanIn[14 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai =
                        this.space(30 + Align) +
                        " Giấy báo cước Viễn Thông TP/HCM " +
                        this.space(8 + Align) +
                        "09/2014";
                    sChuoiCanIn[15 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    if (dbTongCong < 0) {
                        dbTongCong = dbTongCong * -1;
                    }
                    let DocTien = moneyToText(dbTongCong);
                    let TienBangChu = DocTien;
                    let s = "",
                        s1 = "",
                        s2 = "";

                    s = TienBangChu;
                    s1 = "";
                    s2 = "";
                    s1 = s;
                    let iViTri = s.indexOf(" ", 0);
                    while (iViTri > 0) {
                        if (iViTri > 20) {
                            s1 = s.substring(0, iViTri);
                            s2 = s.substring(iViTri);
                            break;
                        }
                        iViTri = s.indexOf(" ", iViTri + 1);
                    }
                    chuoi_in_trai = this.space(25 + Align) + s1;
                    sChuoiCanIn[16 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;
                    if (s2 != "") {
                        chuoi_in_trai =
                            this.space(15 + Align) +
                            s2 +
                            this.space(10 + Align) +
                            dbTongCong;
                        sChuoiCanIn[17 + _tong * j + iKCD] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    } else {
                        chuoi_in_trai = this.space(40 + Align) + dbTongCong;
                        sChuoiCanIn[17 + _tong * j + iKCD] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    }
                    chuoi_in_trai =
                        this.space(55 + Align) + this.subString(stk_dvql, 46);
                    sChuoiCanIn[18 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;

                    chuoi_in_trai = this.space(25 + Align) + s1;
                    sChuoiCanIn[19 + _tong * j + iKCD] =
                        this.handleString(chuoi_in_trai, canchuan) +
                        this.space(kh_cach) +
                        chuoi_in_trai;
                    if (s2 != "") {
                        chuoi_in_trai =
                            this.space(15 + Align) +
                            s2 +
                            this.space(30 + Align) +
                            dbTongCong;
                        sChuoiCanIn[20 + _tong * j + iKCD] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    } else {
                        chuoi_in_trai = this.space(40 + Align) + dbTongCong;
                        sChuoiCanIn[20 + _tong * j + iKCD] =
                            this.handleString(chuoi_in_trai, canchuan) +
                            this.space(kh_cach) +
                            chuoi_in_trai;
                    }
                }
                if (this.chkInTrucTiep) {
                    this.print(sChuoiCanIn);
                } else {
                    this.download("In", sChuoiCanIn);
                }
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            } */
        }
    },
    async mounted() {
        try {
            this.loading(true);
            let response = await API.getHTT(this.axios, 3);
            if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined
            ) {
                this.cboHinhThucTT = response.data.data;
                this.cboHinhThucTTValue = this.cboHinhThucTT[0].HTTT_ID;
            }
        } catch (error) {
            this.$toast.error(error.data.message_detail);
        } finally {
            this.loading(false);
        }
        this.load = true;
    }
};
</script>
