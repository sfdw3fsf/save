<template src="./index.html"></template>
<script>
import Vue from 'vue'
import api from './api.js'

export default {
    name: 'ModalThuHoiDaiIP',
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
            gridKey: 0, // Thêm key để force re-render DataGrid
            animationSettings: {
                effect: 'Zoom',
                duration: 400,
                delay: 0
            },
            target: '.main-wrapper',
            DaiIPData: [],
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
                
                const response = await api.getDanhSachDiaChiDaiIPThuHoi(this.$root.context, this.searchParams);
                if (response.data && response.data.length > 0) {
                    this.DaiIPData = response.data;
                } else {
                    this.DaiIPData = [];
                    console.log('Không có dữ liệu dải IP để thu hồi');
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
            // Reset trạng thái trước khi mở modal
            this.resetSelectionState();
            await this.onSearch();
            this.$refs.ModalCapDaiIP.show();
        },

        onModalShow() {
            // Reset trạng thái chọn khi mở modal
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
                // Xử lý thu hồi nhiều dải IP
                await this.thuHoiNhieuDaiIP();
            } else if (this.selectedRowData) {
                // Xử lý thu hồi một dải IP (giữ nguyên logic cũ)
                await this.thuHoiMotDaiIP();
            } else {
                this.$toast.warning('Vui lòng chọn ít nhất một dải IP để thu hồi');
                return;
            }
        },

        // Method thu hồi một dải IP (logic cũ)
        async thuHoiMotDaiIP() {
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
                const response = await api.thuHoiDaiIP(this.$root.context, params);
                
                this.$toast.success('Thu hồi dải IP thành công');
                
                if (this.onSuccess && typeof this.onSuccess === 'function') {
                    this.onSuccess();
                }
                this.closeDialog();
            } catch (error) {
                console.error('Lỗi khi thu hồi dải IP:', error);
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi thu hồi dải IP');
                }
            } finally {
                this.$root.showLoading(false);
            }
        },

        // Method thu hồi nhiều dải IP
        async thuHoiNhieuDaiIP() {
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
                        
                        const response = await api.thuHoiDaiIP(this.$root.context, params);
                        
                        if (response.data[0].RESULT == 0) {
                            this.$toast.warning(response.data[0].MESSAGE);
                        } else {
                            successCount++;
                        }
                        
                    } catch (error) {
                        errorCount++;
                        console.error(`Lỗi khi thu hồi dải IP ${item.TEN_DAI_IP}:`, error);
                        
                        if (error.response) {
                            const status = error.response.status;
                            const message = error.response.data?.message || 'Lỗi server';
                            this.$toast.error(`Lỗi thu hồi ${item.TEN_DAI_IP}: ${status} - ${message}`);
                        } else if (error.request) {
                            this.$toast.error(`Lỗi thu hồi ${item.TEN_DAI_IP}: Không thể kết nối đến server`);
                        } else {
                            this.$toast.error(`Lỗi thu hồi ${item.TEN_DAI_IP}: Có lỗi xảy ra`);
                        }
                    }
                }
                
                if (successCount > 0) {
                    this.$toast.success(`Đã thu hồi thành công ${successCount} dải IP`);
                }
                
                if (errorCount > 0) {
                    this.$toast.error(`Có ${errorCount} dải IP thu hồi thất bại`);
                }
                
                if (this.onSuccess && typeof this.onSuccess === 'function') {
                    this.onSuccess();
                }
                this.closeDialog();
                
            } catch (error) {
                console.error('Lỗi khi thu hồi nhiều dải IP:', error);
                this.$toast.error('Có lỗi xảy ra khi thu hồi dải IP');
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
