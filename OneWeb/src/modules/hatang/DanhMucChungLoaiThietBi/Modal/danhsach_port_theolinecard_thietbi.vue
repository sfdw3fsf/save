<template src="./danhsach_port_theolinecard_thietbi.html">
</template>

<script>
export default {
    props: {
        thongTinCard: {
            type: Object,
        },
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            listPorts: [],
            deviceHeaderName: '',
            titleDialog: '',
        }
    },
    methods: {
        clearAll() {
            this.listPorts = [];
            this.deviceHeaderName = '';
            this.titleDialog = '';
        },
        async dialogOpen(phanLoai, idCard) {
            try {
                this.loading(true);
                this.clearAll();

                if (phanLoai == 1) {
                    this.deviceHeaderName = 'LineCard';
                    this.titleDialog = 'Danh sách Port theo LineCard của thiết bị';
                }
                else if (phanLoai == 2) {
                    this.deviceHeaderName = 'Module';
                    this.titleDialog = 'Danh sách Port theo Module của thiết bị';
                }
                else if (phanLoai == 3) {
                    this.deviceHeaderName = 'ControlCard';
                    this.titleDialog = 'Danh sách Port theo ControlCard của thiết bị';
                }

                // await this.InitForm();
                if (this.thongTinCard && this.thongTinCard.id) {
                    this.listPorts = await this.getDsPortTheoLineCardThietBi(phanLoai, idCard);
                } else {
                    this.$toast.error("Thông tin card không hợp lệ.");
                }

                // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
                if (this.$refs.dialogDanhSachPortTheoLineCardThietBi) {
                    this.$refs.dialogDanhSachPortTheoLineCardThietBi.show(); // Hiển thị dialog
                } else {
                    this.$toast.error("Dialog không tồn tại hoặc không được tham chiếu đúng");
                }
            } catch (e) {
                this.clearAll();
                console.warn(e.response.data.message_detail);
                if (e.response.data.message_detail) {
                    this.$toast.error(e.response.data.message_detail);
                } else {
                    this.$toast.error("Đã xảy ra lỗi khi mở dialog");
                }
            } finally {
                this.loading(false);
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
        // async InitForm() {
        //     // Lấy danh sách port theo line card thiết bị
        //     if (this.thongTinCard && this.thongTinCard.id) {
        //         this.listPorts = await this.getDsPortTheoLineCardThietBi();
        //     } else {
        //         this.$toast.error("Thông tin card không hợp lệ.");
        //     }
        //     // this.setDataProps(); // Gọi hàm này để thiết lập các thuộc tính cần thiết khác
        // },

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
        getDsPortTheoLineCardThietBi: async function (phanLoai, idCard) {
            let result = [];
            try {
                let rs = await this.$root.context.get(`/web-ecms/chungloai-thietbi/lay-ds-port-theo-linecard` +
                    `?chungLoaiThietBiId=${this.thongTinCard.id}&phanLoai=${phanLoai}`
                );
                result = rs.data;
                if (idCard) {
                    result = result.filter(port => port.CHUNGLOAITHIETBI_ID == idCard);
                }
            } catch (e) {
                console.warn(e.response.data.message_detail)
                throw e;
            }
            return result;
        },
        viewDetailsCard: function (data) {
            this.$router.push(`/ha-tang/chi-tiet-cong-thiet-bi/${data.ID}`)
        },
        getColumnDetailsTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<button class="btn btn-second" style="width: 100px" @click="detailsClick">
                      Chi tiết
                    </button>`,
                        methods: {
                            detailsClick: function () {
                                parent.viewDetailsCard(this.data);
                            },
                        },
                    },
                };
            };
        },
    },
    created: async function () {
        // this.$refs.dialogChonDVNV.show();
    },
}
</script>
