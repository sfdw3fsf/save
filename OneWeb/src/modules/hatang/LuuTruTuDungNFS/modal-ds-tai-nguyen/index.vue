<template src="./index.html"></template>

<script>
export default {
    name: 'ModalNfsSmb',
    props: {
    },
    data: function () {
        return {
            animationSettings: { effect: "Zoom" },
            target: ".main-wrapper",
            dataSource: [],
            typeModal: '',
            hatangId: null,
        };
    },
    created: async function () {
        try {
            this.$root.showLoading(true);
        } finally {
            this.$root.showLoading(false);
        }
    },
    methods: {
        /**
         * API lấy danh sách máy ảo và lưu máy ảo.
         */
        apiDanhSachMayAo: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/get-source-vms/${this.hatangId}`);
                result = rs.data;
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
            }
            return result;
        },
        apiSaveVM: async function (id, linkIds) {
            try {
                await this.$root.context.post("/web-ecms/hatang/luutru-tudung-nfs/add-vms", { id: id, linkIds: linkIds });
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
                throw e;
            }
        },

        /**
         * API lấy danh sách hạ tầng ảo hóa/SmartCloud và lưu.
         */
        apiDanhSachHaTangAoHoaVaSmartCloud: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/get-source-v-smart-cloud/${this.hatangId}`);
                result = rs.data;
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
            }
            return result;
        },
        apiSaveHaTangAoHoaVaSmartCloud: async function (id, linkIds) {
            try {
                await this.$root.context.post("/web-ecms/hatang/luutru-tudung-nfs/add-virtual-infra-and-smart-cloud", { id: id, linkIds: linkIds });
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
                throw e;
            }
        },

        /**
         * API lấy danh sách tài nguyên NFS và lưu tài nguyên NFS.
         */
        apiDanhSachTaiNguyenNFS: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/get-source-nfs/${this.hatangId}`);
                result = rs.data;
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
            }
            return result;
        },
        apiSaveNFS: async function (id, linkIds) {
            try {
                await this.$root.context.post("/web-ecms/hatang/luutru-tudung-nfs/add-nfs", { id: id, linkIds: linkIds });
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
                throw e;
            }
        },

        /**
         * API lấy danh sách thiết bị và lưu thiết bị.
         */
        apiDanhSachDevice: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.get(`/web-ecms/hatang/luutru-tudung-nfs/get-source-devices/${this.hatangId}`);
                result = rs.data;
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
            }
            return result;
        },
        apiSaveDevice: async function (id, linkIds) {
            try {
                await this.$root.context.post("/web-ecms/hatang/luutru-tudung-nfs/add-devices", { id: id, linkIds: linkIds });
            } catch (e) {
                this.$root.toastError(e.response.data.message_detail);
                throw e;
            }
        },

        /**
         * Mở modal danh sách tài nguyên, modal dùng chung tùy thuộc vào typeModal
         * được truyền sang từ component cha.
         */
        open: async function (typeModal, hatangId) {
            this.typeModal = typeModal;
            this.hatangId = hatangId;
            switch (typeModal) {
                case 'VM':
                    this.dataSource = await this.apiDanhSachMayAo();
                    this.$refs.dialogNfsSmb.show();
                    break;
                case 'V_SMARTCLOUD':
                    this.dataSource = await this.apiDanhSachHaTangAoHoaVaSmartCloud();
                    this.$refs.dialogNfsSmb.show();
                    break;
                case 'NFS':
                    this.dataSource = await this.apiDanhSachTaiNguyenNFS();
                    this.$refs.dialogNfsSmb.show();
                    break;
                case 'DEVICE':
                    this.dataSource = await this.apiDanhSachDevice();
                    this.$refs.dialogNfsSmb.show();
                    break;
                default:
                    break;
            }
            document.addEventListener('keydown', this.handleEscKey);
        },

        /**
         * Xóa dữ liệu local và đóng modal.
         */
        close() {
            this.dataSource = [];
            this.typeModal = '';
            this.hatangId = null;
            // this.$refs.grdDialogItems.clearSelection();
            this.$refs.dialogNfsSmb.hide();
            document.removeEventListener('keydown', this.handleEscKey);
        },

        /**
         * Xử lý đóng modal bằng phím ESC.
         */
        handleEscKey: function (event) {
            if (event.key === 'Escape') {
                this.close();
            }
        },
        grdDialogItems_RowSelected: function (event) {

        },
        grdDialogItems_RowDeselected: function (event) {
            let items = this.$refs.grdDialogItems.getSelectedRecords();
        },

        /**
         * Lưu tất cả items đã chọn trong modal và emit sự kiện saveSuccess.
         */
        onSave: async function () {
            try {
                this.$root.showLoading(true);
                if (this.typeModal == 'VM') {
                    await this.apiSaveVM(this.hatangId, this.$refs.grdDialogItems.getSelectedRecords().map(item => item.ID));
                }
                else if (this.typeModal == 'V_SMARTCLOUD') {
                    let vSmartCloudLinkIds = [];
                    for (let item of this.$refs.grdDialogItems.getSelectedRecords()) {
                        vSmartCloudLinkIds.push(item.ID);
                        vSmartCloudLinkIds.push(item.TYPE);
                    }
                    await this.apiSaveHaTangAoHoaVaSmartCloud(this.hatangId, vSmartCloudLinkIds);
                }
                else if (this.typeModal == 'NFS') {
                    await this.apiSaveNFS(this.hatangId, this.$refs.grdDialogItems.getSelectedRecords().map(item => item.ID));
                }
                else if (this.typeModal == 'DEVICE') {
                    await this.apiSaveDevice(this.hatangId, this.$refs.grdDialogItems.getSelectedRecords().map(item => item.ID));
                }
                this.$emit('saveSuccess', this.typeModal);
                this.$root.toastSuccess("Lưu thành công");
                this.close();
            } catch (err) {

            } finally {
                this.$root.showLoading(false);
            }
        },

        /**
         * Lấy label
         */
        getHinhThucDauTuText: function (value) {
            if (value == 1) return 'Hợp tác';
            if (value == 2) return 'Đầu tư nội bộ';
            if (value == 3) return 'Thuê quản trị';
            return 'Khác';
        },
        getColumnHinhThucDauTuTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getHinhThucDauTuText(this.data.HINHTHUCDAUTU);
                            },
                        },
                    },
                };
            };
        },
        getLoaiKhachHangText: function (value) {
            if (value == 0) return 'Ngoài';
            if (value == 1) return 'Nội bộ';
            return 'Khác';
        },
        getColumnLoaiKhachHangTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getLoaiKhachHangText(this.data.HINHTHUCDAUTU);
                            },
                        },
                    },
                };
            };
        },
    },
};
</script>