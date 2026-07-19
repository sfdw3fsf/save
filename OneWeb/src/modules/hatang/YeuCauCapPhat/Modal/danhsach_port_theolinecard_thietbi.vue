<template src="./danhsach_port_theolinecard_thietbi.html">
</template>

<script>
export default {
    props: {
        thongTinCard: {
            type: Object,
        }
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            listPorts: [],
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
                await this.InitForm();

                // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
                if (this.$refs.dialogDanhSachPortTheoLineCardThietBi) {
                    this.$refs.dialogDanhSachPortTheoLineCardThietBi.show(); // Hiển thị dialog
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

        setDataProps() {
            // this.isChonNhanVien = this.p_chonNhanVien
            // this.DSTinhThanh.ID = this.p_phanVungSDID ?  this.p_phanVungSDID : null           
            // this.tenDonVi = this.p_tenDonVi ? this.p_tenDonVi : ""              
        },
        closeDialog() {
            this.clearAll()
            this.$refs.dialogDanhSachPortTheoLineCardThietBi.hide();
        },
        async InitForm() {
            // Lấy danh sách port theo line card thiết bị
            if (this.thongTinCard && this.thongTinCard.id) {
                this.listPorts = await this.getDsPortTheoLineCardThietBi(this.thongTinCard.id);
            } else {
                this.$toast.error("Thông tin card không hợp lệ.");
            }
            this.setDataProps(); // Gọi hàm này để thiết lập các thuộc tính cần thiết khác
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

        getDsPortTheoLineCardThietBi: async function (id) {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/chungloai-thietbi/lay-ds-port-theo-linecard", { id: id });
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
    },
    created: async function () {
        // this.$refs.dialogChonDVNV.show();
    },
}
</script>
