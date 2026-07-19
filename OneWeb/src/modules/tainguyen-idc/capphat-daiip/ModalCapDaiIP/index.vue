<template src="./index.html"></template>
<script>
import Vue from 'vue'
import api from './api.js'

export default {
    name: 'ModalCapDaiIP',
    props: { 
        thongTinCongViec: Object,
        onSuccess: Function
    },
    components: {},
    data() {
        return {
            selectedRowData: null,
            selectedParentData: null,
            dsSelected: [], // Thêm mảng để lưu các dải IP được chọn
            mode: 'view',
            animationSettings: {
                effect: 'Zoom',
                duration: 400,
                delay: 0
            },
            target: '.main-wrapper',
            DaiIPData: [],
            gridKey: 0, // Thêm key để force re-render DataGrid
            searchParams: {
                tenDaiIp: null,
                tenDonviQl: null,
                tenSubnet: null,
                phieutcId: null,
            }
        }
    },
    computed: {
        hasSelectedItems() {
            return (this.dsSelected && this.dsSelected.length > 0) || this.selectedRowData;
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
                const response = await api.getDanhSachDiaChiDaiIPCapPhat(this.$root.context, this.searchParams);
                if (response.data && response.data.length > 0) {
                    this.DaiIPData = response.data;
                } else {
                    this.DaiIPData = [];
                    console.log('Không có dữ liệu dải IP để cấp phát');
                }
            } catch (error) {
                console.error('Lỗi khi gọi API load danh sách dải IP:', error);
                this.DaiIPData = [];
                
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi tải danh sách dải IP');
                }
            } finally {
                this.$root.showLoading(false);
            }
        },
        
        closeDialog: function () {
            this.resetSelectionState();
            this.$refs.ModalCapDaiIP.hide();
        },
        
        async openDialog() {
            this.resetSelectionState();
            await this.onSearch();
            this.$refs.ModalCapDaiIP.show();
        },

        onModalShow() {
            this.resetSelectionState();
            this.onSearch();
        },

        onRowSelected(args) {
            this.selectedRowData = args.data;
            console.log('Row selected:', this.selectedRowData);
        },

        // Thêm method để xử lý khi checkbox được chọn/bỏ chọn
        onSelectRowCheckbox(itemsCheck) {
            this.dsSelected = itemsCheck;
        },

        async onAccept() {
            // Kiểm tra nếu có dải IP được chọn qua checkbox
            if (this.dsSelected && this.dsSelected.length > 0) {
                // Xử lý cấp phát nhiều dải IP
                await this.capPhatNhieuDaiIP();
            } else if (this.selectedRowData) {
                // Xử lý cấp phát một dải IP (giữ nguyên logic cũ)
                await this.capPhatMotDaiIP();
            } else {
                this.$toast.warning('Vui lòng chọn ít nhất một dải IP để cấp phát');
                return;
            }
        },

        // Method cấp phát một dải IP (logic cũ)
        async capPhatMotDaiIP() {
            try {
                const rowData = this.selectedRowData;
                console.log('rowData: ', rowData);
                
                const params = {
                    ipstoreId: rowData.DAI_IP_ID,
                    nhanvienId: this.thongTinCongViec.NHANVIEN_TH_ID,
                    phieutcId: this.thongTinCongViec.PHIEUTC_ID,
                    trangThaiId: null,
                    ghiChu: null,
                    moTa: null,
                    noiDung: null,
                    thicongipstoreId: null,
                    ipID: null,
                    thietbiMangId: 0,
                };
                console.log('params: ', params);
                
                this.$root.showLoading(true);
                const response = await api.capPhatDaiIP(this.$root.context, params);
                
                if (this.onSuccess && typeof this.onSuccess === 'function') {
                    this.onSuccess();
                }
                this.closeDialog();
            } catch (error) {
                console.error('Lỗi khi cấp phát dải IP:', error);
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi cấp phát dải IP');
                }
            } finally {
                this.$root.showLoading(false);
            }
        },

        // Method cấp phát nhiều dải IP
        async capPhatNhieuDaiIP() {
            try {
                this.$root.showLoading(true);
                
                let successCount = 0;
                let errorCount = 0;
                
                for (let item of this.dsSelected) {
                    try {
                        const params = {
                            ipstoreId: item.DAI_IP_ID,
                            nhanvienId: this.thongTinCongViec.NHANVIEN_TH_ID,
                            phieutcId: this.thongTinCongViec.PHIEUTC_ID,
                            trangThaiId: null,
                            ghiChu: null,
                            moTa: null,
                            noiDung: null,
                            thicongipstoreId: null,
                            ipID: null,
                            thietbiMangId: 0,
                        };
                        
                        const response = await api.capPhatDaiIP(this.$root.context, params);
                        
                        if (response.data[0].RESULT == 0) {
                            this.$toast.warning(response.data[0].MESSAGE);
                        } else {
                            successCount++;
                        }
                        
                    } catch (error) {
                        errorCount++;
                        console.error(`Lỗi khi cấp phát dải IP ${item.TEN_DAI_IP}:`, error);
                        
                        if (error.response) {
                            const status = error.response.status;
                            const message = error.response.data?.message || 'Lỗi server';
                            this.$toast.error(`Lỗi cấp phát ${item.TEN_DAI_IP}: ${status} - ${message}`);
                        } else if (error.request) {
                            this.$toast.error(`Lỗi cấp phát ${item.TEN_DAI_IP}: Không thể kết nối đến server`);
                        } else {
                            this.$toast.error(`Lỗi cấp phát ${item.TEN_DAI_IP}: Có lỗi xảy ra`);
                        }
                    }
                }
                
                if (successCount > 0) {
                    this.$toast.success(`Đã cấp phát thành công ${successCount} dải IP`);
                }
                
                if (errorCount > 0) {
                    this.$toast.error(`Có ${errorCount} dải IP cấp phát thất bại`);
                }
                
                if (this.onSuccess && typeof this.onSuccess === 'function') {
                    this.onSuccess();
                }
                this.closeDialog();
                
            } catch (error) {
                console.error('Lỗi khi cấp phát nhiều dải IP:', error);
                this.$toast.error('Có lỗi xảy ra khi cấp phát dải IP');
            } finally {
                this.$root.showLoading(false);
            }
        },

        // Thêm method để reset trạng thái chọn
        resetSelectionState() {
            this.selectedRowData = null;
            this.dsSelected = [];
            
            // Force re-render DataGrid bằng cách thay đổi key
            this.gridKey++;
        }
    },
}
</script>
