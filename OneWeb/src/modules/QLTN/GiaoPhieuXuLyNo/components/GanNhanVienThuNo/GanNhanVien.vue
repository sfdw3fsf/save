<template src="./GanNhanVien.html"></template>

<script>
import { Loai_NV } from "../../../../../utils/Enum";
import API from "../../../api/GiaoPhieuXuLyNo";
import xlsx from "xlsx";
export default {
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            dtTuyenThu: [],
            dtDotGiao: [],
            dt: [],
            nhanvien_id_cu: "",
            nhanvien_id_moi: "",
            manv_tc_cu: "",
            manv_tc_moi: "",
            kyhoadon: "",
            load: true,
            dt_docfile: [],
            tuyenthu_id_moi: 0,
            tuyenthu_id_cu: 0,
            kycuoc: "",
            vthangS: "",
            vthangD: "",
            vloaiphieu: 0,
            dtPhieuD: [],
            dtPhieuS: [],
            kt_load: true,
            dtDotLay: [],
            lantao_id: 0,
            timkiem: "",
            loainvxl_id: 0,
            dtNhanVienXLN: [],

            cboNhanVienTC: [],
            cboNhanVienTCValue: "",
            chkChuaGan: true,
            gridDSKGan: [],
            gridDSKGanSelected: [],
            gridViewLoi: [],
            tabSelected: "tabDanhSach",
            docFile: false
        };
    },
    watch: {
        chkChuaGan() {
            this.showListData();
        }
    },
    methods: {
        async loadForm() {
            try {
                this.loading(true);
                let data = {
                    pLoaiNVXLId: this.loainvxl_id
                };
                let response = await API.getNhanVienXLN(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.dtNhanVienXLN = this.cboNhanVienTC =
                        response.data.data;
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        showListData() {
            if (this.chkChuaGan) {
                if (
                    this.dtDotLay.filter(item => item.NHANVIEN_ID == 0).length >
                    0
                ) {
                    this.dt = this.dtDotLay.filter(
                        item => item.NHANVIEN_ID == 0
                    );
                }
            } else {
                this.dt = this.dtDotLay;
            }
            this.gridDSKGan = this.dt;
        },
        isNullEmpty(value) {
            return value === null || value === "";
        },
        async update() {
            let dsxln_id1 = "";
            let nhanvien_id = 0;
            if (this.isNullEmpty(this.cboNhanVienTCValue)) {
                this.$toast.error("Bạn chưa chọn nhân viên!");
                return;
            }
            nhanvien_id = this.cboNhanVienTCValue;
            let listSL = [];
            this.gridDSKGanSelected.forEach(element => {
                this.gridDSKGan.forEach(item => {
                    if (item.MA_XLN == element) {
                        listSL.push(item);
                        return;
                    }
                });
            });
            for (const item of listSL) {
                dsxln_id1 += item.XULYNO_ID + ",";
            }
            dsxln_id1 = dsxln_id1.substring(0, dsxln_id1.length - 1);
            try {
                this.loading(true);
                let data = {
                    pNhanVienId: nhanvien_id,
                    pLoaiNhanVien: Loai_NV.NHANVIEN_TN,
                    pDSXLNId: dsxln_id1
                };
                let response = await API.putGanNhanVienXuLyNo(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    for (const item of this.dtDotLay) {
                        if (this.gridDSKGanSelected.includes(item.MA_XLN)) {
                            item.NHANVIEN_ID = nhanvien_id;
                        }
                    }
                    this.$toast.success("Cập nhật dữ liệu thành công!");
                    this.showListData();
                }
            } catch (error) {
                console.log(error);
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },

        openDialog() {
            this.loadForm();
            this.$refs.dlgGanNhanVienTN.show();
            this.showListData();
        },
        closeDialog() {
            this.$refs.dlgGanNhanVienTN.hide();
        },
        reset() {
            this.dtTuyenThu = [];
            this.dtDotGiao = [];
            this.dt = [];
            this.nhanvien_id_cu = "";
            this.nhanvien_id_moi = "";
            this.manv_tc_cu = "";
            this.manv_tc_moi = "";
            this.kyhoadon = "";
            this.load = true;
            this.dt_docfile = [];
            this.tuyenthu_id_moi = 0;
            this.tuyenthu_id_cu = 0;
            this.kycuoc = "";
            this.vthangS = "";
            this.vthangD = "";
            this.vloaiphieu = 0;
            this.dtPhieuD = [];
            this.dtPhieuS = [];
            this.kt_load = true;
            this.dtDotLay = [];
            this.lantao_id = 0;
            this.timkiem = "";
            this.loainvxl_id = 0;
            this.dtNhanVienXLN = [];
            this.$emit("closed");
        },
        selectedItemsChangedDSKHGan(value) {
            this.gridDSKGanSelected = value;
        },
        async btnGhiLai() {
            await this.update();
            this.closeDialog();
        },
        selectedTab(value) {
            this.tabSelected = value;
        },
        btnReadExcel(e) {
            const files = e.target.files;
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
                        workbook.Sheets[wsname]
                    );

                    //Validate input file:
                    if (Object.keys(ws[0]).length <= 1) {
                        this.$toast.error("File excel không đúng định dạng!");
                        return;
                    }
                    let ExcelCollumns = ["MA_XLN", "MANV_XL"];
                    for (const iterator of ExcelCollumns) {
                        if (!ws[0].hasOwnProperty(iterator)) {
                            this.$toast.error(
                                "Tên cột trong file không đúng định dạng: " +
                                    iterator +
                                    " (chỉ có 2 trường theo MA_XLN, MANV_XL)!"
                            );
                            return;
                        }
                    }
                    if (ws.length > 0) {
                        ws.forEach(item => {
                            this.dt_docfile.push(item);
                        });
                        let kt_matb = "";
                        for (const item of this.dt_docfile) {
                            if (!kt_matb.includes(item.MA_XLN)) {
                                kt_matb += item.MA_XLN + ",";
                            } else {
                                this.$toast.error(
                                    "Mã xử lý nợ : " +
                                        item.MA_XLN +
                                        " xuất hiện nhiều lần trong danh sách!"
                                );
                                return;
                            }
                        }
                    }
                } catch (e) {
                    console.log(e);
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
                if (this.dt_docfile.length > 0) {
                    this.$toast.success(
                        `Có ${this.dt_docfile.length} thành phần từ file`
                    );
                }
            };
            fileReader.readAsBinaryString(files[0]);
            //this.excelInputPath = files[0].name;
            this.XuLyFileGan();
        },
        async XuLyFileGan() {
            let ds = [];
            let dt_loi = [];
            for (const item of this.dt_docfile) {
                let vxln_id = this.dtPhieuFile.filter(
                    e => e.MA_XLN == item.MA_XLN
                );
                let vnv_id = this.dtNhanVienXLN.filter(
                    e => e.MA_NV == item.MANV_XL
                );
                let vDaGanNV = dtPhieuFile.filter(
                    e => e.MA_XLN == item.MA_XLN && e.NHANVIEN_ID != 0
                );
                if (
                    vxln_id.length > 0 &&
                    vnv_id.length > 0 &&
                    vDaGanNV.length <= 0
                ) {
                    // tìm thấy cả 2 giá trị
                    ds.push({
                        XULYNO_ID: vxln_id[0].XULYNO_ID,
                        NHANVIEN_ID: vnv_id[0].NHANVIEN_ID
                    });
                } else if (
                    vxln_id.length > 0 &&
                    vnv_id.length > 0 &&
                    vDaGanNV.length > 0
                ) {
                    //Đã gán nv xử lý
                    dt_loi.push({
                        MA_XLN: item.MA_XLN,
                        MANV_XL: item.MANV_XL,
                        TRANGTHAI: "Mã XLN đã được gán NV xử lý"
                    });
                } else if (vxln_id.length > 0 && vnv_id.length <= 0) {
                    dt_loi.push({
                        MA_XLN: item.MA_XLN,
                        MANV_XL: item.MANV_XL,
                        TRANGTHAI: "Không tìm thấy nhân viên xử lý"
                    });
                } else if (vxln_id.length <= 0 && vnv_id.length > 0) {
                    dt_loi.push({
                        MA_XLN: item.MA_XLN,
                        MANV_XL: item.MANV_XL,
                        TRANGTHAI: "Không tìm thấy mã xử lý nợ"
                    });
                }
            }
            if (ds.length > 0) {
                try {
                    this.loading(true);
                    let data = {
                        pKieu: Loai_NV.NHANVIEN_TN,
                        pDS: ds
                    };
                    let response = await API.putGanNhanVienXuLyNoTuFile(
                        this.axios,
                        data
                    );
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        this.$toast.success(
                            "Gán nhân viên Xử lý nợ theo file thành công!"
                        );
                        this.docFile = true;
                        this.hienThiDS(ds);
                        this.docFile = false;
                    }
                } catch (error) {
                    this.$toast.error(error.data.message_detail);
                } finally {
                    this.loading(false);
                }
            } else if (dt_loi.length > 0) {
                this.tabSelected = "tabLoi";
                this.gridViewLoi = dt_loi;
            }
        },
        hienThiDS(dtLoad) {
            let dt = [];
            if (this.chkChuaGan) {
                if (!this.docFile) {
                    if (
                        this.dtDotLay.filter(item => item.NHANVIEN_ID == 0)
                            .length > 0
                    )
                        dt = this.dtDotLay.filter(
                            item => item.NHANVIEN_ID == 0
                        );
                } else {
                    if (
                        this.dtPhieuFile.filter(item => item.NHANVIEN_ID == 0)
                            .length > 0
                    )
                        dt = this.dtPhieuFile.filter(
                            item => item.NHANVIEN_ID == 0
                        );
                    else dt = this.dtPhieuFile;
                }
            } else {
                if (!this.docFile) dt = this.dtDotLay;
                else dt = this.dtPhieuFile;
            }

            for (let index = 0; index < dtLoad.length; index++) {
                let vxulyno_id = dt.filter(
                    e => e.XULYNO_ID == dtLoad[index].XULYNO_ID
                );
                if (vxulyno_id.length > 0) {
                    let vnv_id = this.dtNhanVienXLN.filter(
                        e => e.NHANVIEN_ID == dtLoad[index].NHANVIEN_ID
                    );
                    if (vnv_id.length > 0) {
                        let indexDT = dt.findIndex(
                            e => e.MA_XLN == vxulyno_id[0].MA_XLN
                        );
                        dt[indexDT].MANV_TC = vnv_id[0].TEN_HIENTHI;
                    }
                }
            }
            this.gridDSKGan = dt;
        }
    }
};
</script>

<style></style>
