<template src="./KhoaMayTheoSoMayACCMoiXacMinh.html"></template>
<style src="./KhoaMayTheoSoMayACCMoiXacMinh.scss" scoped></style>
<script>
import xlsx from "xlsx";
import { mapActions, mapState } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import moment from "moment";
import { TrangThaiTB, DichVuVienThong } from "@/const/enums";
import Vue from "vue";
import PopupDocFile from "./popup-docfile.vue";

export default {
    components: { appKyCuoc: KyCuoc, PopupDocFile },
    name: "QuanLyThuTuIn",
    mounted() {},
    watch: {
        "thongTinTimKiem.selectedDonVi": {
            handler: async function(value) {
                await this.getNVQuanLy(value);
            }
        },
        "thongTinTimKiem.selectedNhomKH": {
            handler: async function(value) {
                await this.getPhanLoaiKH(value);
            }
        },
        listDVVT: async function(newValue) {},
        listQuyTrinh: async function(newValue) {
            this.selectedQuyTrinh =
                newValue.length > 0 && newValue[0].QUYTRINH_ID;
        }
    },
    computed: {
        ...mapState("KhoaMoMayTheoSoMayACCMoiXacMinh", [
            "listDVVT",
            "listLoaiHinh",
            "listLyDoKhoa",
            "listQuyTrinh"
        ])
    },
    data() {
        return {
            animationSettings: { effect: "Zoom" },
            target: ".main-wrapper",
            selectedDichVu: 1,
            dichVuVTText: "cố định",
            selectedLoaiHinhTB: 1,

            listChieuKhoa: [],
            selectedChieuKhoa: 1,
            kyHienHanh: "",
            listLyDo: [],
            selectedLyDo: null,

            selectedQuyTrinh: null,
            maTB: "",
            trangThaiTB: "",
            tenTB: "",
            diemTinNhiem: "",
            maTT: "",
            tenTT: "",
            thueBaoId: "",
            checkThuHoi: false,
            trangThaiTBId: "",
            headerText: "",
            tongNo: 0,
            listTB: [],
            trangThai: 0
        };
    },

    methods: {
        ...mapActions("KhoaMoMayTheoSoMayACCMoiXacMinh", [
            "getDichVuVienThong",
            "getDanhSachLoaiHinhThueBao",
            "getDanhSachLyDoKhoa",
            "getQuyTrinh",
            "getThongTinTheoMaTB",
            "getDiemTinNhiem",
            "getDanhBaTheoMaThueBao",
            "checkTongNoTB",
            "kiemTraTB",
            "getKyCuocHienHanh",
            "capNhatDanhSach",
            "checkTBMyTVChinhPhu",
            "checkKhieuNai",
            "checkTBLapHD",
            "checkThueBaoTrongDSDacBiet",
            "getDSMoMayTheoMaTB",
            "getDiemTinNhiemTheoNhanVien"
        ]),
        openDialog() {
            this.$refs.dlgKhoaMoMayMoiXacMinh.show();
            this.headerText =
                "<div class='title'>" +
                " <span class='icon one-notepad'></span> Tiếp nhận yêu cầu khoá máy " +
                this.dichVuVTText +
                "</div>";
        },

        dialogOpen() {
            this.getList();
            this.getKyCuoc();
        },

        async getKyCuoc() {
            const response = await this.getKyCuocHienHanh();
            this.kyHienHanh = response.data.data[0].KYCUOC;
            console.log(response.data.data[0].KYCUOC);
        },

        async getList() {
            // this.loading(true);
            const data = {
                dichVuVTId: this.selectedDichVu,
                khoaMay: true
            };
            try {
                await Promise.all([
                    this.getDichVuVienThong(),
                    this.getDanhSachLoaiHinhThueBao(this.selectedDichVu),
                    this.getDanhSachLyDoKhoa(),
                    this.getQuyTrinh(data)
                ]);
                this.createListChieuKhoa();

                if (
                    this.listLyDoKhoa !== undefined &&
                    this.listLyDoKhoa.length > 0
                ) {
                    this.listLyDoKhoa.forEach(item => {
                        this.listLyDo.push({
                            id: item.LYDOTRA_ID,
                            text: item.NOIDUNG
                        });
                    });
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        async checkLapHopDong() {
            const response = await this.checkTBLapHD({
                maTB: this.maTB,
                dichVuVTId: this.selectedDichVu
            });
            console.log({ response });
            if (response.data.error_code !== "BSS-00000000") {
                this.$root.toastError(response.data.message_detail);
                this.selectedDichVu = 1;
                this.selectedLoaiHinhTB = 1;
                this.selectedChieuKhoa = 1;
                this.listLyDo = [];
                this.selectedLyDo = null;
                this.selectedQuyTrinh = null;
                // this.maTB = "";
                this.trangThaiTB = "";
                this.tenTB = "";
                this.diemTinNhiem = "";
                this.maTT = "";
                this.tenTT = "";
                this.thueBaoId = "";
                this.checkThuHoi = false;
                this.trangThaiTBId = "";
                await this.getList();
                this.getKyCuoc();
                this.dichVuVTText = "cố định";
                this.trangThai = 0;
            }
            return response;
        },

        async checkTBDacBiet() {
            const response = await this.checkThueBaoTrongDSDacBiet({
                pThueBaoId: this.thueBaoId,
                pKyCuoc: this.kyHienHanh
            });
            console.log({ response });
            if (response.data.data.error_code !== "BSS-00000000") {
                this.trangThai = response.data.data[0].TRANG_THAI;
                if (this.trangThai !== 0) {
                    this.$root.toastError(response.data.data[0].THONG_BAO);
                }
            }
        },

        async getDSMoMaytheoMaThueBao() {
            const response = await this.getDSMoMayTheoMaTB({
                maTB: this.maTB,
                dichVuVTId: this.selectedDichVu
            });
            console.log({ response });
            if (response.data.error_code !== "BSS-00000000") {
                this.$root.toastError(response.data.message_detail);
            }
        },

        async getDiemTinNhiemTheoNV() {
            const response = await this.getDiemTinNhiemTheoNhanVien({
                pNhanVienId: this.$auth.getNhanVienID(),
                pPhanVungId: this.$auth.getPhanVungID()
            });
            console.log({ response });
            if (response.data.error_code !== "BSS-00000000") {
            }
        },

        async sendData(data) {
            console.log({ data });
            this.selectedDichVu = data.DICHVUVT_ID;
            this.dichVuVTText = data.DICHVU_VT.toLowerCase();
            this.checkLapHopDong();
            this.headerText =
                "<div class='title'>" +
                " <span class='icon one-notepad'></span> Tiếp nhận yêu cầu khoá máy " +
                this.dichVuVTText +
                "</div>";
            this.trangThaiTB = data.TRANGTHAI_TB;
            this.tenTB = data.TEN_TB;
            this.maTB = data.MA_TB;
            this.tenTT = data.TEN_TT;
            this.maTT = data.MA_TT;
            this.thueBaoId = data.THUEBAO_ID;
            this.checkTBDacBiet();
            // this.getDSMoMaytheoMaThueBao();
            this.trangThaiTBId = data.TRANGTHAITB_ID;

            await this.getDanhSachLoaiHinhThueBao(this.selectedDichVu);
            await this.getDiemTinNhiemAction(data.KHACHHANG_ID);
            await this.getQuyTrinh({
                dichVuVTId: this.selectedDichVu,
                khoaMay: true
            });
            this.selectedLoaiHinhTB = data.LOAITB_ID;

            if (
                this.selectedLoaiHinhTB == 14 ||
                this.selectedLoaiHinhTB == 16 ||
                this.selectedLoaiHinhTB == 15 ||
                this.selectedLoaiHinhTB == 17
            ) {
                this.getISDNTheoThueBao(this.thueBaoId);
            }
        },

        async getThongTin() {
            try {
                this.trangThai = 0;
                const response = await this.getDanhBaTheoMaThueBao({
                    maTB: this.maTB
                });

                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length === 0
                ) {
                    this.$root.toastError(
                        "Không tìm thấy thông tin thuê bao vừa nhập!"
                    );
                    this.close();
                } else {
                    if (response.data.data.length > 1) {
                        this.listTB = response.data.data;
                        this.$bvModal.show("modal-docfile");
                    } else {
                        const data = response.data.data[0];
                        this.selectedDichVu = data.DICHVUVT_ID;
                        this.dichVuVTText = data.DICHVU_VT.toLowerCase();
                        this.checkLapHopDong();
                        // this.getDSMoMaytheoMaThueBao();
                        this.headerText =
                            "<div class='title'>" +
                            " <span class='icon one-notepad'></span> Tiếp nhận yêu cầu khoá máy " +
                            this.dichVuVTText +
                            "</div>";
                        this.trangThaiTB = data.TRANGTHAI_TB;
                        this.tenTB = data.TEN_TB;
                        this.maTB = data.MA_TB;
                        this.tenTT = data.TEN_TT;
                        this.maTT = data.MA_TT;
                        this.thueBaoId = data.THUEBAO_ID;
                        this.checkTBDacBiet();
                        this.trangThaiTBId = data.TRANGTHAITB_ID;

                        await this.getDanhSachLoaiHinhThueBao(
                            this.selectedDichVu
                        );
                        await this.getDiemTinNhiemAction(data.KHACHHANG_ID);
                        await this.getQuyTrinh({
                            dichVuVTId: this.selectedDichVu,
                            khoaMay: true
                        });
                        this.selectedLoaiHinhTB = data.LOAITB_ID;

                        if (
                            this.selectedLoaiHinhTB == 14 ||
                            this.selectedLoaiHinhTB == 16 ||
                            this.selectedLoaiHinhTB == 15 ||
                            this.selectedLoaiHinhTB == 17
                        ) {
                            this.getISDNTheoThueBao(this.thueBaoId);
                        }
                    }
                }
                console.log(response.data.data);
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        async getISDNTheoThueBao(thuebao_id) {
            try {
                let response = await this.getThongTinTheoMaTB({
                    pThueBaoId: thuebao_id
                });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined &&
                    response.data.data.length > 0
                ) {
                    console.log(response.data.data);
                }
            } catch (error) {
                console.log(error);
            } finally {
            }
        },

        async getDiemTinNhiemAction(data) {
            const response = await this.getDiemTinNhiem({
                // pNhanVienId: data
                pKyCuoc: this.kyHienHanh,
                pKhachHangId: data
            });
            if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined &&
                response.data.data.length > 0
            ) {
                this.diemTinNhiem = response.data.data[0].DIEMTINNHIEM;
            }
        },

        capNhat() {
            if (this.thueBaoId == "") {
                this.$root.toastError(
                    "Ấn Enter để tìm kiếm thông tin thuê bao theo Số máy"
                );
                return;
            }
            // this.getDiemTinNhiemTheoNV();

            // const response = this.checkLapHopDong();
            // if (response.data.error_code !== "BSS-00000000") {
            //     return;
            // }

            setTimeout(() => {
                this.kiemTra();
                // this.validateData();
            }, 200);
        },

        async checkTongNo() {
            try {
                let response = await this.checkTongNoTB({
                    pKyHoaDon: this.kyHienHanh,
                    pMaTB: this.maTB,
                    pMaTT: "",
                    pKieu: 1
                });
                if (response.data.error_code === "BSS-00000000") {
                    if (parseInt(response.data.data[0].TONGNO) <= 0) {
                        // this.$confirm(
                        //     `Thuê bao  ${this.maTB} đã thanh toán hết nợ. Bạn có đồng ý tiếp tục?`,
                        //     "Thông báo",
                        //     {
                        //         confirmButtonText: "Có",
                        //         cancelButtonText: "Không"
                        //     }
                        // )
                        //     .then(() => {
                        //         this.kiemtraKhieuNai();
                        //     })
                        //     .catch(() => {});
                        this.$root.toastError(
                            "Thuê bao không có nợ không thể khóa máy"
                        );
                        return;
                    } else {
                        this.kiemtraKhieuNai();
                    }
                    this.tongNo = response.data.data[0].TONGNO;
                    console.log(response.data.data[0].TONGNO);
                }
            } catch (error) {
                console.log(error);
            } finally {
            }
        },

        async kiemTra() {
            let data = {
                pThueBaoId: this.thueBaoId,
                pMaTB: this.maTB,
                pMaTT: this.maTT,
                pNguoiDungId: this.$auth.getNguoiDungID(),
                pKieu: 1,
                pThamSoMD: "KHOAMAY_TB_KHONG_XULY",
                pTrangThai: this.trangThaiTBId,
                pKyCuoc: this.kyHienHanh
            };

            try {
                let response = await this.kiemTraTB(data);
                console.log({ trangthai: this.trangThai });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    this.trangThai == 0
                ) {
                    this.validateData();
                    return;
                } else if (response.data.error_code !== "BSS-00000000") {
                    if (this.trangThai == 1 || this.trangThai == 2) {
                        if (
                            response.data.message_detail.includes("Đối tượng")
                        ) {
                            this.$confirm(
                                response.data.message_detail.replace(
                                    /^[ ]+|[ ]+$/g,
                                    ""
                                ) + ". Bạn có muốn tiếp tục?",
                                "Thông báo",
                                {
                                    confirmButtonText: "Có",
                                    cancelButtonText: "Không"
                                }
                            )
                                .then(() => {
                                    this.validateData();
                                })
                                .catch(() => {});
                        } else {
                            this.$root.toastError(response.data.message_detail);
                            return;
                        }
                    } else {
                        if (this.trangThai == 0 && !response.data.message_detail.includes("thuê bao đặc biệt tháng")) {
                            this.$root.toastError(response.data.message_detail);
                            return;
                        }
                        this.validateData();
                    }
                }
            } catch (error) {
                this.$root.toastError(error.data.message_detail);
            } finally {
            }
        },

        async chuyenKhoaMoSangHHKM() {
            // let data = {
            //     ds: [
            //         {
            //             thueBaoId: this.thueBaoId,
            //             lyDoKhoaMo: this.selectedLyDo
            //         }
            //     ],
            //     kieuYC: 1,
            //     chieuKhoa: this.selectedChieuKhoa,
            //     loaiTBId: [this.selectedLoaiHinhTB],
            //     dichVuVTId: this.selectedDichVu,
            //     donViId: this.$auth.getDonViID(),
            //     nhanVienId: this.$auth.getNhanVienID()
            // };

            const text = this.listLyDoKhoa.filter(
                item => item.LYDOTRA_ID == this.selectedLyDo
            );
            console.log({ text });
            const textLyDo = text[0].NOIDUNG;
            let data = {
                pDSTB: [
                    {
                        pCachKhoa: 0,
                        pDichVuVTId: this.selectedDichVu,
                        pDonViId: this.$auth.getDonViID(),
                        pKieuKhoa: this.selectedChieuKhoa,
                        pKieuYC: 1,
                        pLoaiTBId: this.selectedLoaiHinhTB,
                        pLyDoKhoa: textLyDo,
                        pMaTB: this.maTB,
                        pMaTT: this.maTT,
                        pMayCN: "",
                        pNgayCN: moment(new Date()).format("DD/MM/yyyy"),
                        pNgayKhoa: moment(new Date()).format("DD/MM/yyyy"),
                        pNguoiCN: this.$auth.getUserName(),
                        pNhanVienId: this.$auth.getNhanVienID(),
                        pPhanVungId: this.$auth.getPhanVungID(),
                        pThuHoi: this.checkThuHoi ? 1 : 0,
                        pThueBaoId: this.thueBaoId,
                        pTienNo: this.tongNo
                    }
                ]
            };

            try {
                let response = await this.capNhatDanhSach(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess(
                        `Chuyển thành công thuê bao có mã ${this.maTB} sang hợp đồng thay đổi dịch vụ`
                    );
                    console.log(response.data.data);
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (error) {
                console.log(error);
                this.$root.toastError(error.data.message_detail);
            } finally {
            }
        },

        showAlert() {
            this.$router.push({ name: "GiaoPhieuDieuHanhThiCong" });
        },

        async validateData() {
            if (this.maTB === "") {
                this.$root.toastError("Vui lòng nhập mã thuê bao để tra cứu");
                return false;
            }

            if (!this.selectedLyDo) {
                this.$root.toastError("Vui lòng chọn lý do khóa!");
                return false;
            }

            if (this.trangThai == 3) {
                this.$root.toastError(
                    "Thuê bao " + this.maTB + " đang thuộc danh sách khất nợ! "
                );
                return;
            }
            if (this.trangThai == 4) {
                this.$root.toastError(
                    "Thuê bao " + this.maTB + " đang thuộc danh sách treo nợ! "
                );
                return;
            }

            if (this.trangThaiTBId != "1") {
                const chieuKhoa = this.listChieuKhoa.filter(
                    item => item.id == this.selectedChieuKhoa
                );
                console.log({ chieuKhoa });
                this.$root.toastError(
                    `Bạn đang chọn chiều khóa:  ${chieuKhoa[0].text}, chỉ được chuyển hợp đồng đối với những thuê bao có trạng thái hoạt động bình thường!\nBạn hãy chọn thuê bao khác!`
                );
                return false;
            } else if (
                this.trangThaiTBId != "1" &&
                this.trangThaiTBId != "2" &&
                this.trangThaiTBId != "3"
            ) {
                const chieuKhoa = this.listChieuKhoa.filter(
                    item => item.id == this.selectedChieuKhoa
                );

                this.$root.toastError(
                    `Bạn đang chọn chiều khóa:  ${chieuKhoa[0].text}, chỉ được chuyển hợp đồng đối với những thuê bao có trạng thái hoạt động bình thường!\nBạn hãy chọn thuê bao khác!`
                );
                return;
            }
            if (!this.checkThuHoi) {
                this.$confirm(
                    `Bạn chắc chắn không thu hồi thiết bị của khách hàng?`,
                    "Thông báo",
                    {
                        confirmButtonText: "Có",
                        cancelButtonText: "Không"
                    }
                )
                    .then(() => {
                        this.kiemtraMyTVChinhPhu();
                    })
                    .catch(() => {});
            } else {
                this.kiemtraMyTVChinhPhu();
            }
        },

        async kiemtraMyTVChinhPhu() {
            let data = {
                pMaTB: this.maTB,
                pKieu: 1
            };

            try {
                let response = await this.checkTBMyTVChinhPhu(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.checkTongNo();
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (error) {
                console.log(error);
            } finally {
            }
        },

        async kiemtraKhieuNai() {
            let data = {
                pThueBaoId: this.thueBaoId
            };

            try {
                let response = await this.checkKhieuNai(data);
                if (response.data.error_code !== "BSS-00000000") {
                    this.$confirm(
                        `Thuê bao ${this.thueBaoId} đang có khiếu nại chưa xử lý xong. Bạn có đồng ý tiếp tục?`,
                        "Thông báo",
                        {
                            confirmButtonText: "Có",
                            cancelButtonText: "Không"
                        }
                    )
                        .then(() => {
                            this.chuyenKhoaMoSangHHKM();
                        })
                        .catch(() => {});
                } else {
                    this.chuyenKhoaMoSangHHKM();
                }
            } catch (error) {
                console.log(error);
            } finally {
            }
        },

        async close() {
            this.selectedDichVu = 1;
            this.selectedLoaiHinhTB = 1;
            this.selectedChieuKhoa = 1;
            this.listLyDo = [];
            this.selectedLyDo = null;
            this.selectedQuyTrinh = null;
            this.maTB = "";
            this.trangThaiTB = "";
            this.tenTB = "";
            this.diemTinNhiem = "";
            this.maTT = "";
            this.tenTT = "";
            this.thueBaoId = "";
            this.checkThuHoi = false;
            this.trangThaiTBId = "";
            await this.getList();
            this.getKyCuoc();
            this.dichVuVTText = "cố định";
            this.trangThai = 0;
        },

        createListChieuKhoa() {
            this.listChieuKhoa = [];
            if (
                this.selectedDichVu == DichVuVienThong.CO_DINH ||
                this.selectedDichVu == DichVuVienThong.DI_DONG ||
                this.selectedDichVu == DichVuVienThong.GPHONE ||
                this.selectedDichVu == DichVuVienThong.IMS
            ) {
                this.listChieuKhoa.push({
                    id: "1",
                    text: "Khóa 1 chiều do nợ cước"
                });
                this.listChieuKhoa.push({
                    id: "2",
                    text: "Khóa 2 chiều do nợ cước"
                });
            } else {
                this.listChieuKhoa.push({
                    id: "2",
                    text: "Khóa 2 chiều do nợ cước"
                });
            }
            console.log({ listchieukhoa: this.listChieuKhoa });
        }
    },

    created() {
        this.getList();
        // this.getDiemTinNhiemAction();
    }
};
</script>
