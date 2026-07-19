<template src="./khaibao_cong_console.html">
</template>

<script>
export default {
    props: {
        thongTinCard: {
            type: Object,
        },
        thongTinCong: {
            type: Object,
        }
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            loaiCongListItems: [],
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
        }
    },
    methods: {
        clearAll() {
            this.listPorts = [];
        },
        async dialogOpen() {
            try {
                this.loading(true);  // Bắt đầu trạng thái loading
                this.clearAll();

                // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
                if (this.$refs.dialogKhaiBaoCongConsole) {
                    this.$refs.dialogKhaiBaoCongConsole.show(); // Hiển thị dialog
                } else {
                    this.$toast.error("Dialog không tồn tại hoặc không được tham chiếu đúng");
                }
            } catch (e) {
                // Hiển thị thông báo lỗi nếu có vấn đề
                console.log('loi', e);
                if (e.data && e.data.message) {
                    this.$toast.error(e.data.message);
                } else {
                    this.$toast.error("Đã xảy ra lỗi khi mở dialog");
                }
            } finally {
                this.loading(false); // Tắt trạng thái loading
            }
        },
        closeDialog() {
            this.clearAll()
            this.$refs.dialogKhaiBaoCongConsole.hide();
        },

        getDmLoaiCong: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                    loaiDanhMuc: "HATANG_LOAICONG",
                    thamSo1: 1,
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
            let result = false;
            try {
                let rs = await this.$root.context.post("/web-ecms/chungloai-thietbi/khaibao-loaicong", {
                    id: args.id == 0 ? null : args.id,
                    phanloai: 3,
                    chungloaiThietBiId: args.chungloaiThietBiId,
                    loaicongId: args.loaicongId,
                    soCong: args.soCong,
                    batdauTu: args.batdauTu,
                    dinhdangTen: args.dinhdangTen,
                    mucdichsudungCongId: args.mucdichsudungCongId,
                    bangthongcongId: args.bangthongcongId,
                    thongtinModule: args.thongtinModule,
                    ketnoi: args.ketnoi,
                });
                result = rs.data.result;
            } catch (e) {
                if (!(e.response == null || e.response == undefined)) {
                    if (!(e.response.data == null || e.response.data == undefined)) {
                        this.$root.toastError(e.response.data.message_detail);
                    }
                }
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
                        let rs = await this.capNhatCong(this.thongTinCong);
                        if (rs) {

                            if (this.thongTinCong.id == 0) {
                                this.$root.toastSuccess("Khai báo Port thành công");
                            }
                            else {
                                this.$root.toastSuccess("Cập nhật Port thành công");
                            }
                            this.clearAll();

                            // 2. Đóng dialog
                            this.$refs.dialogKhaiBaoCongConsole.hide();

                            // 3. Gọi hàm từ màn cha để lấy lại danh sách cổng (gửi sự kiện cho cha)
                            
                            this.$emit('loadDsCong');
                        } else {
                            if (this.thongTinCong.id == 0) {
                                this.$root.toastError("Khai báo Port thất bại");
                            }
                            else {
                                this.$root.toastError("Cập nhật Port thất bại");
                            }
                        }

                    } finally {
                        this.$root.showLoading(false);
                    }
                }
            }
        },
        validateControls: function () {
            let msg = "";
            if (this.controls.txtSoCong.invalid) {
                msg = "Số cổng không được để trống.";
            }
            if (this.controls.selectLoaiCong.invalid) {
                if (msg != "") msg += "\n\r";
                msg += "Loại cổng không được để trống.";
            }
            if (this.controls.txtBatDauTu.invalid) {
                if (msg != "") msg += "\n\r";
                msg += "Cổng bắt đầu không được để trống.";
            }
            if (this.controls.txtDinhDangTen.invalid) {
                if (msg != "") msg += "\n\r";
                msg += "Định dạng tên không được để trống.";
            }
            if (msg != "") this.$root.toastError(msg);
            return msg == "";
        },
    },
    created: async function () {
        this.loaiCongListItems = await this.getDmLoaiCong();
    },
}
</script>
