<template src="./index.html"></template>
<script>
import Vue from 'vue'
import api from './api.js'

export default {
    name: 'ModalThuHoiThietBiVaPort',
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
            currentPortInfo: null
        }
    },
    computed: {
        isDetailViewMode() {
            return this.currentThietBiId && this.currentCongId;
        },
        canAccept() {
            if (this.isDetailViewMode) {
                return this.currentThietBiInfo && this.currentPortInfo;
            } else {
                return this.selectedThietBiData && this.selectedPortData;
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
                const searchParams = {
                    ...this.searchParams,
                    thietbiId: this.currentThietBiId
                };
                const response = await api.getDanhSachThietBiCapPhat(this.$root.context, searchParams);
                if (response.data && response.data.length > 0) {
                    this.thietBiData = response.data;
                    if (this.currentThietBiId && this.currentThietBiInfo) {
                        const currentThietBi = this.thietBiData.find(item => 
                            item.THIETBI_ID === this.currentThietBiId
                        );
                        if (currentThietBi) {
                            this.selectedThietBiData = currentThietBi;
                            await this.onSearchPort();
                        }
                    }
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
                    thietBiId: this.selectedThietBiData.THIETBI_ID,
                    congId: this.currentCongId
                };
                
                const response = await api.getDanhSachPortTheoThietBi(this.$root.context, searchParams);
                if (response.data && response.data.length > 0) {
                    this.portData = response.data;
                    
                    if (this.currentCongId && this.currentPortInfo) {
                        const currentPort = this.portData.find(item => 
                            item.ID_CONG === this.currentCongId
                        );
                        if (currentPort) {
                            this.selectedPortData = currentPort;
                        }
                    }
                } else {
                    this.portData = [];
                    console.log('Không có dữ liệu port cho thiết bị được chọn');
                }
            } catch (error) {
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
        
        async openDialog(params = {}) {
            this.currentThietBiId = params.currentThietBiId || null;
            this.currentCongId = params.currentCongId || null;
            
            if (this.thongtinIP) {
                this.currentThietBiInfo = {
                    THIETBI_ID: this.currentThietBiId,
                    TEN_THIETBI: this.thongtinIP.TEN_THIETBI || 'N/A'
                };
                
                this.currentPortInfo = {
                    ID_CONG: this.currentCongId,
                    TEN_PORT: this.thongtinIP.TEN_PORT || 'N/A'
                };
            }
            
            // Nếu có cả currentThietBiId và currentCongId thì tìm kiếm song song
            if (this.currentThietBiId && this.currentCongId) {
                await this.onSearchThietBiAndPortParallel();
            } else {
                await this.onSearchThietBi();
            }
            
            this.$refs.ModalCapThietBiVaPort.show();
        },

        onModalShow() {
            this.onSearchThietBi();
        },

        onThietBiRowSelected(args) {
            if (this.isDetailViewMode) {
                return;
            }
            
            this.selectedThietBiData = args.data;
            this.selectedPortData = null; // Reset port selection
            this.portData = []; // Clear port data
            this.onSearchPort();
        },

        onPortRowSelected(args) {
            if (this.isDetailViewMode) {
                return;
            }
            this.selectedPortData = args.data;
        },

        async onAccept() {
            try {
                const params = {
                    ipId: this.thongtinIP.ID_IP || null,
                    thicongipstoreId: this.thongtinIP.THICONGIPSTORE_ID || null,
                    phieutcId: this.thongTinCongViec.PHIEUTC_ID,
                };
                
                this.$root.showLoading(true);
                const response = await api.thuHoiThietBiVaPort(this.$root.context, params);
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
        },

        async onSearchThietBiAndPortParallel() {
            try {
                this.$root.showLoading(true);
                
                // Tạo các promise cho việc tìm kiếm song song
                const thietBiPromise = api.getDanhSachThietBiCapPhat(this.$root.context, {
                    ...this.searchParams,
                    thietbiId: this.currentThietBiId
                });
                
                const portPromise = api.getDanhSachPortTheoThietBi(this.$root.context, {
                    tenPort: this.searchParams.tenPort,
                    thietBiId: this.currentThietBiId,
                    congId: this.currentCongId
                });
                
                // Thực hiện cả hai API call song song
                const [thietBiResponse, portResponse] = await Promise.all([thietBiPromise, portPromise]);
                
                // Xử lý kết quả thiết bị
                if (thietBiResponse.data && thietBiResponse.data.length > 0) {
                    this.thietBiData = thietBiResponse.data;
                    
                    // Tìm và chọn thiết bị hiện tại
                    const currentThietBi = this.thietBiData.find(item => 
                        item.THIETBI_ID === this.currentThietBiId
                    );
                    if (currentThietBi) {
                        this.selectedThietBiData = currentThietBi;
                    }
                } else {
                    this.thietBiData = [];
                    console.log('Không có dữ liệu thiết bị để thu hồi');
                }
                
                // Xử lý kết quả port
                if (portResponse.data && portResponse.data.length > 0) {
                    this.portData = portResponse.data;
                    
                    // Tìm và chọn port hiện tại
                    const currentPort = this.portData.find(item => 
                        item.ID_CONG === this.currentCongId
                    );
                    if (currentPort) {
                        this.selectedPortData = currentPort;
                    }
                } else {
                    this.portData = [];
                    console.log('Không có dữ liệu port cho thiết bị được chọn');
                }
                
            } catch (error) {
                console.error('Lỗi khi gọi API song song:', error);
                this.thietBiData = [];
                this.portData = [];
                
                if (error.response) {
                    const status = error.response.status;
                    const message = error.response.data?.message || 'Lỗi server';
                    this.$toast.error(`Lỗi ${status}: ${message}`);
                } else if (error.request) {
                    this.$toast.error('Không thể kết nối đến server');
                } else {
                    this.$toast.error('Có lỗi xảy ra khi tải dữ liệu');
                }
            } finally {
                this.$root.showLoading(false);
            }
        }
    },
}
</script>
<style scoped>
    #thuhoi-modalCapThietBiVaPort {
        .popup-header {
            padding: 10px !important;
        }
    }
</style>
