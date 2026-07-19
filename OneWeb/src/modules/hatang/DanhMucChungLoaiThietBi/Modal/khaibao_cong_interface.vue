<template src="./khaibao_cong_interface.html">
</template>

<script>
export default {
    props: {
        thongTinCard: {
            type: Object,
        },
        thongTinCong: {
            type: Object,
        },
        fromTab: {
            type: String,
            default: "interface",
        },
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            loaiCongListItems: [],
            bangThongListItems: [],
            thongTinModuleListItems: [],
            ketNoiListItems: [],
            mucDichSdCongListItems: [],
            controls: {
                txtSoCong: {
                    enabled: false,
                    invalid: false,
                },
                selectLoaiCong: {
                    enabled: false,
                    invalid: false,
                },
                txtBatDauTu: {
                    enabled: false,
                    invalid: false
                },
                txtDinhDangTen: {
                    enabled: false,
                    invalid: false
                },
            },
            interfacePortType: {
                id: null,
                loaiCongId: null,
                soCong: 0,
                batDauTu: 0,
                dinhDangTen: '',
                mucDichId: null,
                bangThongId: null,
                thongTinModuleId: null,
                ketNoiId: null,
            },
            phanLoai: null,
        }
    },
    methods: {
        clearAll() {
            this.listPorts = [];
        },
        async dialogOpen(phanLoai) {
            console.log('dialogOpen', phanLoai);
            try {
                this.loading(true);  // Bắt đầu trạng thái loading
                // this.clearAll();
                // await this.InitForm();
                this.phanLoai = phanLoai;

                // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
                if (this.$refs.dialogKhaiBaoCongInterface) {
                    this.$refs.dialogKhaiBaoCongInterface.show(); // Hiển thị dialog
                } else {
                    this.$toast.error("Dialog không tồn tại hoặc không được tham chiếu đúng");
                }
            } catch (e) {
                // Hiển thị thông báo lỗi nếu có vấn đề
                // console.log('loi', e);
                if (e.data && e.data.message) {
                    this.$toast.error(e.data.message);
                } else {
                    this.$toast.error("Đã xảy ra lỗi khi mở dialog");
                }
            } finally {
                this.loading(false); // Tắt trạng thái loading
            }
        },
        async dialogUpdateOpen(data, phanLoai) {
            try {
                this.loading(true);

                this.interfacePortType = {
                    id: data.ID,
                    loaiCongId: data.loaicong_id,
                    soCong: data.so_cong,
                    batDauTu: data.bat_dau,
                    dinhDangTen: data.dinhdang_ten,
                    mucDichId: data.mucdichsudung_cong_id,
                    bangThongId: data.bangthongcong_id,
                    thongTinModuleId: data.thongtinmodule_id,
                    ketNoiId: data.ketnoi
                };
                this.phanLoai = phanLoai;

                // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
                if (this.$refs.dialogKhaiBaoCongInterface) {
                    this.$refs.dialogKhaiBaoCongInterface.show(); // Hiển thị dialog
                } else {
                    this.$toast.error("Dialog không tồn tại hoặc không được tham chiếu đúng");
                }
            } catch (e) {
                // Hiển thị thông báo lỗi nếu có vấn đề
                // console.log('loi', e);
                if (e.data && e.data.message) {
                    this.$toast.error(e.data.message);
                } else {
                    this.$toast.error("Đã xảy ra lỗi khi mở dialog");
                }
            } finally {
                this.loading(false); // Tắt trạng thái loading
            }
        },
        setDataProps() {
            // this.isChonNhanVien = this.p_chonNhanVien
            // this.DSTinhThanh.ID = this.p_phanVungSDID ?  this.p_phanVungSDID : null           
            // this.tenDonVi = this.p_tenDonVi ? this.p_tenDonVi : ""              
        },
        closeDialog() {
            // this.clearAll()
            this.resetInterfacePortType();
            this.$refs.dialogKhaiBaoCongInterface.hide();
        },
        async InitForm() {
            // Lấy danh sách port theo line card thiết bị
            // if (this.thongTinCard && this.thongTinCard.id) {
            //     this.listPorts = await this.getDsPortTheoLineCardThietBi(this.thongTinCard.id);
            // } else {
            //     this.$toast.error("Thông tin card không hợp lệ.");
            // }
            // this.setDataProps(); // Gọi hàm này để thiết lập các thuộc tính cần thiết khác
        },

        rowSelected_Port: async function (args) {
            try {
                this.donViId = args.data.DONVI_ID;
                this.tenDonVi = args.data.TEN_DV;
                if (this.isChonNhanVien) {
                    this.tenNhanVien = ""
                    this.nhanVienId = null
                    this.DSNhanVien = await this.getDSNhanVien(this.donViId);
                }
            } catch (e) {
                this.$toast.error(e.data.message)
            } finally {
                this.loading(false)
            }
        },

        chiTietClick: async function (name, args) {
            console.log('args:', args); // Kiểm tra đối tượng args

        },

        XacNhan() {
            if (this.donViId == null) {
                this.$toast.error("Chưa chọn đơn vị")
                return
            }
            let data = {
                DONVI_ID: this.donViId,
                TEN_DV: this.tenDonVi,
            }
            if (this.isChonNhanVien) {
                if (this.nhanVienId == null) {
                    this.$toast.error("Chưa chọn nhân viên")
                    return
                }
                data.TEN_NV = this.tenNhanVien,
                    data.NHANVIEN_ID = this.nhanVienId
            }
            this.$emit('xacNhanChon', data);
            this.$refs.dialogChonDVNV.hide();
        },

        getDmLoaiCong: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                    loaiDanhMuc: "HATANG_LOAICONG",
                    thamSo1: this.fromTab == "interface" ? 1 : (this.fromTab == "power" ? 2 : 3),
                    thamSo2: null
                });
                result = rs.data;
            } catch (e) {
                if (!(e.response == null || e.response == undefined)) {
                    if (!(e.response.data == null || e.response.data == undefined)) {
                        this.$root.toastError(e.response.data.message_detail);
                    }
                }
            }
            return result;
        },
        getDmThongTinModule: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                    loaiDanhMuc: "HATANG_THONGTIN_MODULE",
                    thamSo1: null,
                    thamSo2: null
                });
                result = rs.data;
            } catch (e) {
                if (!(e.response == null || e.response == undefined)) {
                    if (!(e.response.data == null || e.response.data == undefined)) {
                        this.$root.toastError(e.response.data.message_detail);
                    }
                }
            }
            return result;
        },
        getDmBangThongCong: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                    loaiDanhMuc: "HATANG_BANGTHONG_CONG",
                    thamSo1: null,
                    thamSo2: null
                });
                result = rs.data;
            } catch (e) {
                if (!(e.response == null || e.response == undefined)) {
                    if (!(e.response.data == null || e.response.data == undefined)) {
                        this.$root.toastError(e.response.data.message_detail);
                    }
                }
            }
            return result;
        },
        getDmKetNoi: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                    loaiDanhMuc: "HATANG_KETNOI",
                    thamSo1: null,
                    thamSo2: null
                });
                result = rs.data;
            } catch (e) {
                if (!(e.response == null || e.response == undefined)) {
                    if (!(e.response.data == null || e.response.data == undefined)) {
                        this.$root.toastError(e.response.data.message_detail);
                    }
                }
            }
            return result;
        },
        getDmMucDichSDCong: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                    loaiDanhMuc: "HATANG_MUCDICH_SD_CONG",
                    thamSo1: null,
                    thamSo2: null
                });
                result = rs.data;
            } catch (e) {
                if (!(e.response == null || e.response == undefined)) {
                    if (!(e.response.data == null || e.response.data == undefined)) {
                        this.$root.toastError(e.response.data.message_detail);
                    }
                }
            }
            return result;
        },
        capNhatCong: async function (args) {
            let result = null;
            try {
                let result = await this.$root.context.post("/web-ecms/chungloai-thietbi/khaibao-loaicong", {
                    id: args.id,
                    phanloai: this.phanLoai,
                    chungloaiThietBiId: this.thongTinCard.id,
                    loaicongId: args.loaiCongId,
                    soCong: args.soCong,
                    batdauTu: args.batDauTu,
                    dinhdangTen: args.dinhDangTen,
                    mucdichsudungCongId: args.mucDichId,
                    bangthongcongId: args.bangThongId,
                    thongtinModule: args.thongTinModuleId,
                    ketnoi: args.ketNoiId,
                });
            } catch (e) {
                // if (!(e.response == null || e.response == undefined)) {
                //     if (!(e.response.data == null || e.response.data == undefined)) {
                //         this.$root.toastError(e.response.data.message_detail);
                //     }
                // }
                throw e;
            }
            return result;
        },
        onSave: async function () {
            if (this.validateControls()) {
                let flag = await this.$confirm(
                    "Bạn có muốn lưu thông tin này không ?",
                    "Xác nhận",
                    {
                        confirmButtonText: "Có",
                        cancelButtonText: "Không",
                        type: "info",
                        dangerouslyUseHTMLString: true,
                    }
                )
                    .then((s) => true)
                    .catch((e) => {
                        return false;
                    });
                if (flag) {
                    try {
                        this.$root.showLoading(true);
                        let rs = await this.capNhatCong(this.interfacePortType);
                        if (this.interfacePortType.id == null || this.interfacePortType.id == 0) {
                            this.$root.toastSuccess("Khai báo thành công");
                        }
                        else {
                            this.$root.toastSuccess("Cập nhật thành công");
                        }
                        // this.clearAll();

                        // 2. Đóng dialog
                        // this.$refs.dialogKhaiBaoCongInterface.hide();

                        // 3. Gọi hàm từ màn cha để lấy lại danh sách cổng (gửi sự kiện cho cha)

                        if (this.phanLoai == 1) {
                            this.$emit('loadDsCongInterface');
                        }
                        else if (this.phanLoai == 2) {
                            this.$emit('loadDsCongPower');
                        }
                        else if (this.phanLoai == 3) {
                            this.$emit('loadDsCongConsole');
                        }
                        // this.$emit('saveInterfacePortType', this.interfacePortType);
                        // this.$root.toastSuccess("Khai báo thành công");
                    } catch (err) {
                        if (this.interfacePortType.id == null || this.interfacePortType.id == 0) {
                            this.$root.toastError("Khai báo thất bại");
                        }
                        else {
                            this.$root.toastError("Cập nhật thất bại");
                        }
                    }
                    finally {
                        this.resetInterfacePortType();
                        this.$refs.dialogKhaiBaoCongInterface.hide();
                        this.$root.showLoading(false);
                    }
                }
            }
        },
        resetInterfacePortType: function () {
            this.interfacePortType = {
                id: null,
                loaiCongId: null,
                soCong: 0,
                batDauTu: 0,
                dinhDangTen: '',
                mucDichId: null,
                bangThongId: null,
                thongTinModuleId: null,
                ketNoiId: null,
            };
            this.phanLoai = null;
        },
        validateControls: function () {
            let msg = "";
            if (this.interfacePortType.loaiCongId == null) {
                msg = "Loại cổng không được để trống.";
            }
            if (this.interfacePortType.soCong == null) {
                if (msg != "") msg += "\n\r";
                msg += "Số cổng không được để trống.";
            } else {
                if (Number(this.interfacePortType.soCong) < 0) {
                    if (msg != "") msg += "\n\r";
                    msg += "Số cổng không được nhỏ hơn 0.";
                }
            }
            if (this.interfacePortType.batDauTu == null) {
                if (msg != "") msg += "\n\r";
                msg += "Cổng bắt đầu không được để trống.";
            } else {
                if (Number(this.interfacePortType.batDauTu) < 0) {
                    if (msg != "") msg += "\n\r";
                    msg += "Cổng bắt đầu không được nhỏ hơn 0.";
                }
            }
            if (this.interfacePortType.dinhDangTen == null || this.interfacePortType.dinhDangTen.trim() == "") {
                if (msg != "") msg += "\n\r";
                msg += "Định dạng tên không được để trống.";
            }

            if (msg == "") return true;

            this.$root.toastError(msg);
            return false;
        },
    },
    created: async function () {
        this.loaiCongListItems = await this.getDmLoaiCong();
        this.thongTinModuleListItems = await this.getDmThongTinModule();
        this.bangThongListItems = await this.getDmBangThongCong();
        this.ketNoiListItems = await this.getDmKetNoi();
        this.mucDichSdCongListItems = await this.getDmMucDichSDCong();
    },
}
</script>
