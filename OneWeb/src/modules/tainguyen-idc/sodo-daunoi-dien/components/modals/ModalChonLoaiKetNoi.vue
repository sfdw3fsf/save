<template>
    <b-modal ref="modal" title="Tạo kết nối mới" size="lg" @ok="handleOk" @cancel="handleCancel" @hidden="handleCancel"
        ok-title="Tạo kết nối" cancel-title="Hủy" :ok-disabled="!isValid">
        <div v-if="sourceNode && targetNode">
            <!-- Thông tin thiết bị -->
            <div class="connection-info-section">
                <h6 class="section-title">Thông tin thiết bị</h6>
                <div class="row">
                    <div class="col-md-6">
                        <div class="device-info-card">
                            <div class="device-label">Thiết bị nguồn</div>
                            <div class="device-name">{{ sourceNode.addInfo.deviceName }}</div>
                            <div class="device-type">{{ getDeviceTypeName(sourceNode.addInfo.deviceType) }}</div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="device-info-card">
                            <div class="device-label">Thiết bị đích</div>
                            <div class="device-name">{{ targetNode.addInfo.deviceName }}</div>
                            <div class="device-type">{{ getDeviceTypeName(targetNode.addInfo.deviceType) }}</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Thông tin kết nối -->
            <div class="connection-details-section">
                <h6 class="section-title">Thông tin kết nối</h6>

                <!-- Loại kết nối -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="required">Loại kết nối:</label>
                            <select v-model="formData.loaiKetNoi" class="form-control">
                                <option value="">-- Chọn loại kết nối --</option>
                                <option value="DUONG_TRUNG_THE">Đường trung thế</option>
                                <option value="DUONG_HA_THE">Đường hạ thế</option>
                                <option value="NGUON_DU_PHONG">Nguồn dự phòng</option>
                                <option value="NGUON_UPS">Nguồn UPS</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <!-- Port nguồn -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Port nguồn:</label>
                            <SelectExt
                                v-model="formData.portNguonId"
                                dataTextField="TEN"
                                dataValueField="ID"
                                :dataSource="portNguonList"
                                placeholder="Chọn port nguồn"
                                @change="onPortNguonChange"
                            />
                        </div>
                    </div>

                    <!-- Port đích -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Port đích:</label>
                            <SelectExt
                                v-model="formData.portDichId"
                                dataTextField="TEN"
                                dataValueField="ID"
                                :dataSource="portDichList"
                                placeholder="Chọn port đích"
                                @change="onPortDichChange"
                            />
                        </div>
                    </div>
                </div>

                <div class="row">
                    <!-- Khoảng cách -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Khoảng cách (m):</label>
                            <input v-model.number="formData.khoangCach" type="number" class="form-control"
                                placeholder="Nhập khoảng cách" min="0" step="0.1" />
                        </div>
                    </div>

                    <!-- Trạng thái -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="required">Trạng thái:</label>
                            <select v-model="formData.trangThai" class="form-control">
                                <option value="HOATDONG">Hoạt động</option>
                                <option value="KHONGHOATDONG">Không hoạt động</option>
                                <option value="BAOTRI">Bảo trì</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>

<script>
import SelectExt from '@/components/Controls/SelectExt'

export default {
    name: 'ModalChonLoaiKetNoi',
    components: {
        SelectExt
    },
    props: {
        sourceNode: {
            type: Object,
            default: null
        },
        targetNode: {
            type: Object,
            default: null
        },
        tempConnectorId: {
            type: String,
            default: null
        }
    },
    data() {
        return {
            formData: {
                loaiKetNoi: 'NGUON_UPS',
                portNguonId: null,
                portNguon: '',
                portDichId: null,
                portDich: '',
                khoangCach: null,
                trangThai: 'HOATDONG',
                ghiChu: ''
            },
            portNguonList: [],
            portDichList: [],
            loadingPortNguon: false,
            loadingPortDich: false
        }
    },
    computed: {
        isValid() {
            return this.formData.loaiKetNoi && this.formData.trangThai
        }
    },
    watch: {
        sourceNode: {
            immediate: true,
            handler(newVal) {
                if (newVal) {
                    this.loadPortNguon()
                }
            }
        },
        targetNode: {
            immediate: true,
            handler(newVal) {
                if (newVal) {
                    this.loadPortDich()
                }
            }
        }
    },
    methods: {
        async loadPortNguon() {
            if (!this.sourceNode || !this.sourceNode.addInfo || !this.sourceNode.addInfo.deviceId) {
                return
            }

            try {
                this.loadingPortNguon = true
                const response = await this.$root.context.post(
                    '/web-ecms/sodo-ketnoi-dien/ds-port-thietbi-chua-ket-noi',
                    {
                        thietBiId: String(this.sourceNode.addInfo.deviceId).replace('device_', '')
                    }
                )

                if (response.error_code === 'BSS-00000000' && response.data) {
                    this.portNguonList = response.data || []
                } else {
                    this.portNguonList = []
                    console.warn('Không thể tải danh sách port nguồn:', response)
                }
            } catch (error) {
                console.error('Lỗi khi tải port nguồn:', error)
                this.portNguonList = []
                this.$toast.error('Không thể tải danh sách port nguồn')
            } finally {
                this.loadingPortNguon = false
            }
        },

        async loadPortDich() {
            if (!this.targetNode || !this.targetNode.addInfo || !this.targetNode.addInfo.deviceId) {
                return
            }

            try {
                this.loadingPortDich = true
                const response = await this.$root.context.post(
                    '/web-ecms/sodo-ketnoi-dien/ds-port-thietbi-chua-ket-noi',
                    {
                        thietBiId: String(this.targetNode.addInfo.deviceId).replace('device_', '')
                    }
                )

                if (response.error_code === 'BSS-00000000' && response.data) {
                    this.portDichList = response.data || []
                } else {
                    this.portDichList = []
                    console.warn('Không thể tải danh sách port đích:', response)
                }
            } catch (error) {
                console.error('Lỗi khi tải port đích:', error)
                this.portDichList = []
                this.$toast.error('Không thể tải danh sách port đích')
            } finally {
                this.loadingPortDich = false
            }
        },

        onPortNguonChange(value) {
            // Tìm port được chọn để lấy tên
            const selectedPort = this.portNguonList.find(p => p.ID === value)
            if (selectedPort) {
                this.formData.portNguon = selectedPort.TEN
            }
        },

        onPortDichChange(value) {
            // Tìm port được chọn để lấy tên
            const selectedPort = this.portDichList.find(p => p.ID === value)
            if (selectedPort) {
                this.formData.portDich = selectedPort.TEN
            }
        },

        show() {
            this.resetForm()
            this.loadPortNguon()
            this.loadPortDich()
            this.$refs.modal.show()
        },
        hide() {
            this.$refs.modal.hide()
        },
        resetForm() {
            this.formData = {
                loaiKetNoi: 'NGUON_UPS',
                portNguonId: null,
                portNguon: '',
                portDichId: null,
                portDich: '',
                khoangCach: null,
                trangThai: 'HOATDONG',
                ghiChu: ''
            }
        },
        async handleOk(bvModalEvt) {
            bvModalEvt.preventDefault()
            
            if (!this.isValid) {
                return
            }

            // Validate port đã chọn
            if (!this.formData.portNguonId || !this.formData.portDichId) {
                this.$toast.warning('Vui lòng chọn port nguồn và port đích')
                return
            }

            try {
                this.$root.showLoading(true)

                // Map loại kết nối sang ID
                const loaiKetNoiMap = {
                    'DUONG_TRUNG_THE': 1,
                    'DUONG_HA_THE': 2,
                    'NGUON_DU_PHONG': 3,
                    'NGUON_UPS': 4
                }

                // Map trạng thái sang ID
                const trangThaiMap = {
                    'HOATDONG': 1,
                    'KHONGHOATDONG': 2,
                    'BAOTRI': 3
                }

                // Gọi API tạo kết nối
                const response = await this.$root.context.post(
                    '/web-ecms/sodo-ketnoi-dien/ket-noi-dien-thiet-bi',
                    {
                        thietBiId: String(this.sourceNode.addInfo.deviceId).replace('device_', ''),
                        thietbiKetnoiId: String(this.targetNode.addInfo.deviceId).replace('device_', ''),
                        portThietbiId: this.formData.portNguonId,
                        portThietbiKetnoiId: this.formData.portDichId,
                        khoangCachKetNoi: this.formData.khoangCach || 0,
                        loaiKetNoiId: loaiKetNoiMap[this.formData.loaiKetNoi] || 4,
                        trangThaiId: trangThaiMap[this.formData.trangThai] || 1
                    }
                )

                if (response.error_code === 'BSS-00000000') {
                    this.$toast.success('Tạo kết nối thành công')
                    
                    // Emit event với thông tin kết nối đầy đủ
                    this.$emit('create-connection', {
                        sourceNode: this.sourceNode,
                        targetNode: this.targetNode,
                        connectionData: { 
                            ...this.formData,
                            apiResponse: response.data 
                        },
                        tempConnectorId: this.tempConnectorId
                    })

                    this.hide()
                } else {
                    this.$toast.error(response.message || 'Không thể tạo kết nối')
                }
            } catch (error) {
                console.error('Lỗi khi tạo kết nối:', error)
                this.$toast.error('Có lỗi xảy ra khi tạo kết nối')
            } finally {
                this.$root.showLoading(false)
            }
        },
        handleCancel() {
            this.$emit('cancel-connection', {
                tempConnectorId: this.tempConnectorId
            })
            this.resetForm()
        },
        getDeviceTypeName(type) {
            const names = {
                RPDU: 'rPDU',
                PDU: 'PDU / MPDU',
                RACK: 'Rack',
                UPS: 'UPS',
                ATS: 'ATS',
                TRANSFORMER: 'Trạm biến áp',
                GENERATOR: 'Nguồn điện',
                SWITCH: 'Tủ phân phối',
                DEFAULT: 'Khác'
            }
            return names[type] || type
        }
    }
}
</script>

<style scoped>
.connection-info-section,
.connection-details-section {
    margin-bottom: 20px;
}

.section-title {
    font-weight: 600;
    color: #333;
    margin-bottom: 15px;
    padding-bottom: 8px;
    border-bottom: 2px solid #e9ecef;
}

.device-info-card {
    background: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 6px;
    padding: 12px;
    text-align: center;
}

.device-label {
    font-size: 12px;
    color: #6c757d;
    text-transform: uppercase;
    margin-bottom: 5px;
}

.device-name {
    font-size: 16px;
    font-weight: 600;
    color: #2196F3;
    margin-bottom: 3px;
}

.device-type {
    font-size: 13px;
    color: #666;
}

.form-group label.required::after {
    content: ' *';
    color: #dc3545;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    font-weight: 500;
    color: #495057;
    margin-bottom: 5px;
}

.form-control:focus {
    border-color: #2196F3;
    box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.25);
}
</style>
