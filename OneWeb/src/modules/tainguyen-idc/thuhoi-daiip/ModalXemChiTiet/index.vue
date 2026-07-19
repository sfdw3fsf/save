<template src="./index.html"></template>
<script>
import Vue from 'vue'
import api from './api.js'

export default {
    name: 'ModalXemChiTiet',
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
            },
            currentThietBiId: null,
            currentCongId: null,
            currentThietBiInfo: null,
            currentPortInfo: null,
        }
    },
    computed: {
        isDetailViewMode() {
            return this.currentThietBiId && this.currentCongId;
        },
        canAccept() {
            return this.currentThietBiInfo && this.currentPortInfo;
        }
    },
    mounted() {},
    methods: {
        async onSearchThietBi() {
            try {
                this.$root.showLoading(true);
                if (!this.currentThietBiId) {
                    this.currentThietBiInfo = null;
                    return;
                }
                const searchParams = {
                    ...this.searchParams,
                    thietbiId: this.currentThietBiId
                };
                const response = await api.getDanhSachThietBiCapPhat(this.$root.context, searchParams);
                if (response.data && response.data.length > 0) {
                    this.thietBiData = response.data;
                    // Tìm thiết bị hiện tại để hiển thị chi tiết
                    if (this.currentThietBiId) {
                        const currentThietBi = this.thietBiData.find(item => 
                            item.THIETBI_ID === this.currentThietBiId
                        );
                        if (currentThietBi) {
                            this.selectedThietBiData = currentThietBi;
                            this.currentThietBiInfo = currentThietBi;
                            await this.onSearchPort();
                        } else {
                            this.currentThietBiInfo = null;
                        }
                    }
                } else {
                    this.thietBiData = [];
                    this.currentThietBiInfo = null;
                    console.log('Không có dữ liệu thiết bị');
                }
            } catch (error) {
                console.error('Lỗi khi gọi API load danh sách thiết bị:', error);
                this.thietBiData = [];
                this.currentThietBiInfo = null;
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
            if (!this.selectedThietBiData || !this.currentCongId) {
                this.currentPortInfo = null;
                return;
            }

            try {
                this.$root.showLoading(true);
                const searchParams = {
                    tenPort: this.searchParams.tenPort,
                    thietBiId: this.selectedThietBiData.THIETBI_ID,
                    congId: this.currentCongId
                };
                
                const response = await api.getDanhSachPortTheoThietBi(this.$root.context, searchParams);
                if (response.data && response.data.length > 0) {
                    this.portData = response.data;
                    
                    // Tìm port hiện tại để hiển thị chi tiết
                    if (this.currentCongId) {
                        const currentPort = this.portData.find(item => 
                            item.ID_CONG === this.currentCongId
                        );
                        if (currentPort) {
                            this.selectedPortData = currentPort;
                            this.currentPortInfo = currentPort;
                        } else {
                            this.currentPortInfo = null;
                        }
                    }
                } else {
                    this.portData = [];
                    this.currentPortInfo = null;
                    console.log('Không có dữ liệu port cho thiết bị');
                }
            } catch (error) {
                this.portData = [];
                this.currentPortInfo = null;
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
            this.$refs.ModalXemChiTiet.hide();
        },
        
        async openDialog(params = {}) {
            this.currentThietBiId = params.currentThietBiId || null;
            this.currentCongId = params.currentCongId || null;
            
            // Reset data
            this.currentThietBiInfo = null;
            this.currentPortInfo = null;
            this.selectedThietBiData = null;
            this.selectedPortData = null;
            
            // Chỉ gọi API khi có đủ thông tin
            if (this.currentThietBiId && this.currentCongId) {
                await this.onSearchThietBi();
            }
            
            this.$refs.ModalXemChiTiet.show();
        },
    },
}
</script>

<style scoped>
.detail-info {
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 5px;
  margin-top: 10px;
}

.info-row {
  display: flex;
  margin-bottom: 10px;
  align-items: center;
}

.info-row:last-child {
  margin-bottom: 0;
}

.info-row label {
  font-weight: 600;
  color: #495057;
  min-width: 150px;
  margin-right: 10px;
}

.info-row span {
  color: #212529;
  flex: 1;
}

.no-data {
  padding: 20px;
  text-align: center;
  color: #6c757d;
}

.legend-title {
  font-weight: 600;
  color: #495057;
  margin-bottom: 10px;
  padding-bottom: 5px;
  border-bottom: 2px solid #dee2e6;
}
</style>
