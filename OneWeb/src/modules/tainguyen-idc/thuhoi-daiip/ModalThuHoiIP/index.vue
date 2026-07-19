<template src="./index.html"></template>
<script>
import Vue from 'vue'
import api from './api.js'

export default {
    name: 'ModalThuHoiIP',
    props: { 
        thongTinCongViec: Object,
        onSuccess: Function
    },
    components: {},
    data() {
        return {
            selectedRowData: null,
            selectedParentData: null,
            mode: 'view',
            animationSettings: {
                effect: 'Zoom',
                duration: 400,
                delay: 0
            },
            target: '.main-wrapper',
            IPData: [],
            searchParams: {
                ipAddress: null,
                tenSubnet: null,
                phieutcId: null,
            }
        }
    },
    mounted() {
        this.onSearch();
    },
    methods: {
        async onSearch() {
            try {
                this.$root.showLoading(true);
                this.searchParams.phieutcId = this.thongTinCongViec?.PHIEUTC_ID || null;
                const response = await api.getDanhSachDiaChiIPThuHoi(this.$root.context, this.searchParams);
                if (response.data && response.data.length > 0) {
                    this.IPData = response.data.filter(item => 
                        item.THIETBI_ID && item.PORT_ID
                    );
                } else {
                    this.IPData = [];
                    console.log('Không có dữ liệu IP để cấp phát');
                }
            } catch (error) {
                console.error('Lỗi khi gọi API load danh sách địa chỉ IP:', error);
                this.IPData = [];
                
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi tải danh sách địa chỉ IP');
                }
            } finally {
                this.$root.showLoading(false);
            }
        },
        
        closeDialog: function () {
            this.$refs.ModalCapIP.hide();
        },
        
        async openDialog() {
            await this.onSearch();
            this.$refs.ModalCapIP.show();
        },

        onModalShow() {
            this.onSearch();
        },

        // Xử lý khi chọn row trong DataGrid
        onRowSelected(args) {
            this.selectedRowData = args.data;
            console.log('Row selected:', this.selectedRowData);
        },

        // Xử lý khi click nút Chấp nhận
        async onAccept() {
            if (!this.selectedRowData) {
                this.$toast.warning('Vui lòng chọn một IP để cấp phát');
                return;
            }

            try {
                const rowData = this.selectedRowData;
                const params = {
                    ipstoreId: rowData.IP_STORE_ID,
                    nhanvienId: this.thongTinCongViec.NHANVIEN_TH_ID,
                    phieutcId: this.thongTinCongViec.PHIEUTC_ID,
                    trangThaiId: null,
                    ghiChu: null,
                    moTa: null,
                    noiDung: null,
                    thicongipstoreId: null,
                    ipID: rowData.IP_ID,
                    thietbiMangId: 0,
                };
                
                this.$root.showLoading(true);
                const response = await api.thuHoiIP(this.$root.context, params);
                
                if (this.onSuccess && typeof this.onSuccess === 'function') {
                    this.onSuccess();
                }
                this.closeDialog();
            } catch (error) {
                console.error('Lỗi khi cấp phát IP:', error);
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi cấp phát IP');
                }
            } finally {
                this.$root.showLoading(false);
            }
        }
    },
}
</script>
