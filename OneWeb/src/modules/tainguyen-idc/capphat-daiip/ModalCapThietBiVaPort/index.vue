<template src="./index.html"></template>
<script>
import Vue from 'vue'
import api from './api.js'

export default {
    name: 'ModalCapThietBiVaPort',
    props: { 
        thongtinIP: Object,
        thongTinCongViec: Object,
        onSuccess: Function
    },
    components: {},
    data() {
        return {
            selectedThietBiData: null,
            selectedPortData: null,
            mode: 'view',
            animationSettings: {
                effect: 'Zoom',
                duration: 400,
                delay: 0
            },
            target: '.main-wrapper',
            thietBiData: [],
            portData: [],
            searchParams: {
                tenThietBi: null,
                tenPort: null
            }
        }
    },
    mounted() {
        this.onSearchThietBi();
    },
    methods: {
        async onSearchThietBi() {
            try {
                this.$root.showLoading(true);
                const response = await api.getDanhSachThietBiCapPhat(this.$root.context, this.searchParams);
                if (response.data && response.data.length > 0) {
                    this.thietBiData = response.data;
                } else {
                    this.thietBiData = [];
                    console.log('Không có dữ liệu thiết bị để cấp phát');
                }
            } catch (error) {
                console.error('Lỗi khi gọi API load danh sách thiết bị:', error);
                this.thietBiData = [];
                
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi tải danh sách thiết bị');
                }
            } finally {
                this.$root.showLoading(false);
            }
        },

        async onSearchPort() {
            if (!this.selectedThietBiData) {
                this.$toast.warning('Vui lòng chọn thiết bị trước khi tìm kiếm port');
                return;
            }

            try {
                this.$root.showLoading(true);
                const searchParams = {
                    tenPort: this.searchParams.tenPort,
                    thietBiId: this.selectedThietBiData.THIETBI_ID
                };
                
                const response = await api.getDanhSachPortTheoThietBi(this.$root.context, searchParams);
                if (response.data && response.data.length > 0) {
                    this.portData = response.data;
                } else {
                    this.portData = [];
                    console.log('Không có dữ liệu port cho thiết bị được chọn');
                }
            } catch (error) {
                console.error('Lỗi khi gọi API load danh sách port:', error);
                this.portData = [];
                
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi tải danh sách port');
                }
            } finally {
                this.$root.showLoading(false);
            }
        },
        
        closeDialog: function () {
            this.$refs.ModalCapThietBiVaPort.hide();
        },
        
        async openDialog() {
            await this.onSearchThietBi();
            console.log('this.$refs:', this.$refs);
            console.log('ModalCapThietBiVaPort ref:', this.$refs.ModalCapThietBiVaPort);
            console.log('show method:', typeof this.$refs.ModalCapThietBiVaPort?.show);
            this.$refs.ModalCapThietBiVaPort.show();
        },

        onModalShow() {
            this.onSearchThietBi();
        },

        // Xử lý khi chọn row thiết bị
        onThietBiRowSelected(args) {
            this.selectedThietBiData = args.data;
            this.selectedPortData = null; // Reset port selection
            this.portData = []; // Clear port data
            console.log('Thiết bị được chọn:', this.selectedThietBiData);
            
            // Tự động load danh sách port khi chọn thiết bị
            this.onSearchPort();
        },

        // Xử lý khi chọn row port
        onPortRowSelected(args) {
            this.selectedPortData = args.data;
            console.log('Port được chọn:', this.selectedPortData);
        },

        // Xử lý khi click nút Chấp nhận
        async onAccept() {
            if (!this.selectedThietBiData) {
                this.$toast.warning('Vui lòng chọn một thiết bị để cấp phát');
                return;
            }

            if (!this.selectedPortData) {
                this.$toast.warning('Vui lòng chọn một port để cấp phát');
                return;
            }

            try {
                const params = {
                    ipId: this.thongtinIP.ID_IP || null,
                    portId: this.selectedPortData.ID_CONG,
                    thietbiId: this.selectedThietBiData.THIETBI_ID,
                    trangThaiCapPhat: 1,
                    phieutcId: this.thongTinCongViec.PHIEUTC_ID,
                    thicongipstoreId: this.thongtinIP.THICONGIPSTORE_ID,
                };

                this.$root.showLoading(true);
                const response = await api.capPhatThietBiVaPort(this.$root.context, params);
                if (this.onSuccess && typeof this.onSuccess === 'function') {
                    this.onSuccess();
                }
                this.closeDialog();
            } catch (error) {
                console.error('Lỗi khi cấp phát thiết bị và port:', error);
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi cấp phát thiết bị và port');
                }
            } finally {
                this.$root.showLoading(false);
            }
        }
    },
}
</script>
<style scoped>
    #capphat-modalCapThietBiVaPort {
        .popup-header {
            padding: 10px !important;
        }
        .dataGrids {
            padding: 0 7px !important;
        }
        .search-form {
            margin: 10px 0 !important;
        }
        .row-item-left {
            padding-right: 25px !important;
        }
        .row-item-right {
            padding-left: 30px !important;
        }
        .info-row-search {
            .key {
                width: 100px !important;
                padding: 0 !important;
                display: flex !important;
                align-items: center !important;
                justify-content: flex-start !important;
            }
            .value {
                flex: 1 !important;
            }
        }
    }
</style>
