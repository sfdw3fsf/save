<template>
    <b-modal id="modalCRUDHanhLang" ref="modalCRUDHanhLang" size="xl" hide-footer @hidden="onModalHidden"
        modal-class="modal-crud-hanhlang">
        <template #modal-header="{ close }">
            <h5 class="modal-title">
                 Quản lý Hành lang
            </h5>
            <button type="button" class="close" @click="close()">
                <span aria-hidden="true">&times;</span>
            </button>
        </template>

        <!-- Action buttons -->
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <b-link href="javascript:void(0)" @click="onAddNew" :disabled="!buttons.btnNhapMoi.enabled">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </b-link>
                </li>
                <li>
                    <b-link href="javascript:void(0)" @click="onSave"
                        :disabled="!buttons.btnGhiLai.enabled || !isFormValid">
                        <span class="icon one-save"></span>Ghi lại
                    </b-link>
                </li>
                <li>
                    <b-link href="javascript:void(0)" @click="onCancel" :disabled="!buttons.btnHuyBo.enabled">
                        <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Huỷ bỏ
                    </b-link>
                </li>
                <li>
                    <b-link href="javascript:void(0)" @click="onEdit" :disabled="!buttons.btnSua.enabled">
                        <span class="icon nc-icon-glyph ui-1_edit-72"></span>Sửa
                    </b-link>
                </li>
                <li>
                    <b-link href="javascript:void(0)" @click="onDelete" :disabled="!buttons.btnXoa.enabled">
                        <span class="icon one-trash"></span>Xóa
                    </b-link>
                </li>
            </ul>
        </div>

        <div class="p-3">
            <!-- Form thêm/sửa hành lang -->
            <div class="box-form">
                <div class="legend-title">
                    Thông tin hành lang
                </div>

                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w200">Tên hành lang <span class="required">*</span></div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.ten"
                                    placeholder="Nhập tên hành lang" maxlength="100"
                                    :readonly="!controls.txtTen.enabled" />
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Tên tắt <span class="required">*</span></div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.tenTat" placeholder="Nhập tên tắt"
                                    maxlength="50" :readonly="!controls.txtTenTat.enabled" />
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Ghi chú</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.ghiChu" placeholder="Nhập ghi chú"
                                    maxlength="200" :readonly="!controls.txtGhiChu.enabled" />
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w200">Loại nhiệt độ <span class="required">*</span></div>
                            <div class="value">
                                <select class="form-control" v-model="form.nong" :disabled="!controls.selNong.enabled">
                                    <option value="">-- Chọn loại --</option>
                                    <option :value="1">Nóng</option>
                                    <option :value="0">Lạnh</option>
                                </select>
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Hiệu lực</div>
                            <div class="value padt4">
                                <label for="checkSuDung" style="display: flex; align-items: center; cursor: pointer; margin: 0;">
                                    <input type="checkbox" id="checkSuDung" v-model="form.suDung" 
                                        :true-value="1" :false-value="0" :disabled="!controls.checkSuDung.enabled"
                                        style="cursor: pointer; margin-right: 8px;" />
                                    <span>Đang sử dụng</span>
                                </label>
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Mô tả</div>
                            <div class="value">
                                <textarea class="form-control" v-model="form.moTa" rows="3"
                                    placeholder="Nhập mô tả chi tiết" maxlength="500"
                                    :readonly="!controls.txtMoTa.enabled"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <hr />

            <!-- Danh sách hành lang -->
            <div class="box-form">
                <div class="legend-title">
                    Danh sách hành lang
                </div>
                <DataGrid v-bind:columns="[
                    { fieldName: 'id', headerText: 'ID', width: 50, textAlign: 'Center' },
                    { fieldName: 'ten', headerText: 'Tên hành lang', allowFiltering: true },
                    { fieldName: 'tenTat', headerText: 'Tên tắt', width: 120, allowFiltering: true },
                    { fieldName: 'loaiNhietDo', headerText: 'Loại', width: 100, textAlign: 'Center', allowFiltering: true },
                    { fieldName: 'ghiChu', headerText: 'Ghi chú', allowFiltering: true },
                    { fieldName: 'trangThai', headerText: 'Trạng thái', width: 100, textAlign: 'Center', allowFiltering: true }
                ]" :dataSource="gridDataSource" :showFilter="true" :allowPaging="true" :enablePagingServer="false"
                    :showColumnCheckbox="false" @rowSelected="onRowSelected" dataKeyField="id" ref="grdAisles"
                    :allowExcelExport="false">
                </DataGrid>
            </div>
        </div>
    </b-modal>
</template>

<script>
export default {
    name: 'ModalCRUDHanhLang',
    props: {
        roomId: {
            type: Number,
            required: true
        }
    },

    data() {
        return {
            loading: false,
            selectedAisle: null,
            form: {
                id: null,
                ten: '',
                tenTat: '',
                nong: '',
                ghiChu: '',
                moTa: '',
                suDung: 1
            },
            controls: {
                txtTen: { enabled: false },
                txtTenTat: { enabled: false },
                selNong: { enabled: false },
                txtGhiChu: { enabled: false },
                txtMoTa: { enabled: false },
                checkSuDung: { enabled: false }
            },
            buttons: {
                btnNhapMoi: { enabled: true },
                btnGhiLai: { enabled: false },
                btnHuyBo: { enabled: false },
                btnSua: { enabled: false },
                btnXoa: { enabled: false }
            },
            aisleList: []
        }
    },

    watch: {
        'form.id': function (value) {
            this.buttons.btnSua.enabled = value > 0 && !this.controls.txtTen.enabled
            this.buttons.btnXoa.enabled = value > 0 && !this.controls.txtTen.enabled
        },
        'form.suDung': function (newVal, oldVal) {
            console.log('🔄 form.suDung changed:', oldVal, '→', newVal, 'type:', typeof newVal)
        }
    },

    computed: {
        isFormValid() {
            return this.form.ten.trim() !== '' && this.form.tenTat.trim() !== '' && this.form.nong !== ''
        },

        gridDataSource() {
            return this.aisleList.map(aisle => ({
                id: aisle.id,
                ten: aisle.ten,
                tenTat: aisle.tenTat,
                loaiNhietDo: aisle.nong === 1 ? 'Hành lang nóng' : 'Hành lang lạnh',
                ghiChu: aisle.ghiChu || '',
                trangThai: aisle.suDung == 1 ? 'Đang sử dụng' : 'Không sử dụng',
                // Keep original data for editing
                _original: aisle
            }))
        }
    },

    methods: {
        async showModal() {
            this.$refs.modalCRUDHanhLang.show()
            this.resetForm()
            await this.loadAisleList()
        },

        closeModal() {
            this.$refs.modalCRUDHanhLang.hide()
        },

        onModalHidden() {
            this.resetForm()
            this.selectedAisle = null
            this.$emit('close')
        },

        enableControls(enabled) {
            this.controls.txtTen.enabled = enabled
            this.controls.txtTenTat.enabled = enabled
            this.controls.selNong.enabled = enabled
            this.controls.txtGhiChu.enabled = enabled
            this.controls.txtMoTa.enabled = enabled
            this.controls.checkSuDung.enabled = enabled

            this.buttons.btnGhiLai.enabled = enabled
            this.buttons.btnHuyBo.enabled = enabled
            this.buttons.btnNhapMoi.enabled = !enabled

            if (this.form.id > 0) {
                this.buttons.btnSua.enabled = !enabled
                this.buttons.btnXoa.enabled = !enabled
            }
        },

        setCurrentItem(aisle) {
            this.form.id = aisle.id
            this.form.ten = aisle.ten
            this.form.tenTat = aisle.tenTat
            this.form.nong = aisle.nong
            this.form.ghiChu = aisle.ghiChu || ''
            this.form.moTa = aisle.moTa || ''
            this.form.suDung = Number(aisle.suDung) || 0
            this.selectedAisle = aisle
            console.log('📋 setCurrentItem - suDung:', aisle.suDung, '→', this.form.suDung)
        },

        resetForm() {
            this.form = {
                id: null,
                ten: '',
                tenTat: '',
                nong: '',
                ghiChu: '',
                moTa: '',
                suDung: 1
            }
            this.selectedAisle = null
        },

        onAddNew() {
            this.enableControls(true)
            this.resetForm()
        },

        async onSave() {
            if (!this.isFormValid) {
                this.$toast.warning('Vui lòng điền đầy đủ thông tin bắt buộc!')
                return
            }

            await this.saveAisle()
        },

        onCancel() {
            // Load lại data từ danh sách nếu đang sửa
            if (this.form.id && this.form.id > 0) {
                const aisle = this.aisleList.find(a => a.id === this.form.id)
                if (aisle) {
                    this.setCurrentItem(aisle)
                } else {
                    this.resetForm()
                }
            } else {
                this.resetForm()
            }
            this.enableControls(false)
        },

        onEdit() {
            this.enableControls(true)
        },

        async onDelete() {
            if (!this.selectedAisle) {
                this.$toast.warning('Vui lòng chọn một hành lang để xóa!')
                return
            }

            if (this.selectedAisle.suDung === 1) {
                this.$toast.warning('Không thể xóa hành lang đang sử dụng!')
                return
            }

            await this.deleteAisle(this.selectedAisle)
        },

        onRowSelected(args) {
            if (args && args.data && args.data._original) {
                this.setCurrentItem(args.data._original)
            }
        },

        async loadAisleList() {
            try {
                this.loading = true

                // Gọi API lấy danh sách tất cả hành lang
                const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian-hanh-lang/get-ds-hanhlang-by-phong', {
                    id: this.roomId
                })

                if (rs.error_code === 'BSS-00000000' && rs.data) {
                    this.aisleList = rs.data.map((item) => ({
                        id: item.HANHLANG_ID || item.id,
                        ten: item.TEN || '',
                        tenTat: item.TENTAT || '',
                        nong: item.NONG || 0,
                        ghiChu: item.GHICHU || '',
                        moTa: item.MOTA || '',
                        suDung: item.SUDUNG
                    }))
                    console.log('✅ Aisle list loaded:', this.aisleList)
                } else {
                    this.aisleList = []
                }
            } catch (error) {
                console.error('❌ Error loading aisle list:', error)
                this.$toast.error('Lỗi khi tải danh sách hành lang')
            } finally {
                this.loading = false
            }
        },

        async saveAisle() {
            if (!this.isFormValid) {
                this.$toast.warning('Vui lòng điền đầy đủ thông tin bắt buộc!')
                return
            }

            try {
                this.$root.showLoading(true)
                
                const suDungValue = this.form.suDung === 1 || this.form.suDung === true ? 1 : 0
                
                const payload = {
                  hanhLangID: this.form.id,
                  phongID: this.roomId,
                  ten: this.form.ten.trim(),
                  tenTat: this.form.tenTat.trim(),
                  nong: this.form.nong,
                  ghiChu: this.form.ghiChu.trim(),
                  moTa: this.form.moTa.trim(),
                  suDung: suDungValue
                }
                
                const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian-hanh-lang/upsert-hanhlang', payload)
        
                if (rs.error_code === 'BSS-00000000') {
                  this.$toast.success(this.form.id ? 'Cập nhật hành lang thành công!' : 'Thêm hành lang mới thành công!')
                  await this.loadAisleList()
                  // Update current item with latest data
                  if (rs.data && rs.data.id) {
                    const updatedItem = this.aisleList.find(a => a.id === rs.data.id)
                    if (updatedItem) {
                      this.setCurrentItem(updatedItem)
                    }
                  }
                  this.enableControls(false)
                  this.$emit('refresh')
                } else {
                  this.$toast.error(rs.error_description || 'Có lỗi xảy ra!')
                }
            } catch (error) {
                console.error('❌ Error saving aisle:', error)
                this.$toast.error('Lỗi khi lưu hành lang!')
            } finally {
                this.$root.showLoading(false)
            }
        },

        async deleteAisle(aisle) {
            this.$confirm(`Xác nhận xóa hành lang "${aisle.ten}"?`, 'Xóa hành lang', {
                confirmButtonText: 'Xóa',
                cancelButtonText: 'Hủy',
                type: 'warning'
            })
                .then(async () => {
                    try {
                        this.$root.showLoading(true)

                        const index = this.aisleList.findIndex((a) => a.id === aisle.id)
                        if (index !== -1) {
                            this.aisleList.splice(index, 1)
                        }

                        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian-hanh-lang/delete-hanhlang', {
                          id: aisle.id
                        })
            
                        if (rs.error_code === 'BSS-00000000') {
                          this.$toast.success('Xóa hành lang thành công!')
                          await this.loadAisleList()
                          this.resetForm()
                          this.$emit('refresh')
                        } else {
                          this.$toast.error(rs.error_description || 'Có lỗi xảy ra!')
                        }
                    } catch (error) {
                        console.error('❌ Error deleting aisle:', error)
                        this.$toast.error('Lỗi khi xóa hành lang!')
                    } finally {
                        this.$root.showLoading(false)
                    }
                })
                .catch(() => {
                    // User cancelled
                })
        }
    }
}
</script>

<style>
.modal-crud-hanhlang .modal-body {
    padding: 0 !important;
}
</style>

<style scoped>
/* Modal specific styles - using b-modal so minimal custom styling needed */
.box-form {
    border-radius: 4px;
}

.form-actions {
    margin-top: 20px;
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    padding-top: 15px;
    border-top: 1px solid #e0e0e0;
}

.btn {
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.2s;
    display: inline-flex;
    align-items: center;
    gap: 5px;
}

.btn-primary {
    background: #2196f3;
    color: white;
}

.btn-primary:hover:not(:disabled) {
    background: #1976d2;
}

.btn-primary:disabled {
    background: #ccc;
    cursor: not-allowed;
}

.btn-secondary {
    background: #f5f5f5;
    color: #333;
    border: 1px solid #ddd;
}

.btn-secondary:hover {
    background: #e0e0e0;
}

.btn-sm {
    padding: 5px 10px;
    font-size: 12px;
}

.btn-info {
    background: #17a2b8;
    color: white;
}

.btn-info:hover {
    background: #138496;
}

.btn-danger {
    background: #dc3545;
    color: white;
}

.btn-danger:hover:not(:disabled) {
    background: #c82333;
}

.btn-danger:disabled {
    background: #ccc;
    cursor: not-allowed;
}

.table {
    width: 100%;
    margin-bottom: 0;
    border-collapse: collapse;
}

.table-bordered {
    border: 1px solid #dee2e6;
}

.table-bordered th,
.table-bordered td {
    border: 1px solid #dee2e6;
}

.table-hover tbody tr:hover {
    background-color: #f8f9fa;
}

.table thead th {
    background: #f8f9fa;
    color: #333;
    font-weight: 600;
    padding: 12px 8px;
    text-align: left;
    vertical-align: middle;
}

.table tbody td {
    padding: 10px 8px;
    vertical-align: middle;
}

.text-center {
    text-align: center;
}

.text-muted {
    color: #6c757d;
}

.small {
    font-size: 12px;
}

.badge {
    display: inline-block;
    padding: 4px 10px;
    border-radius: 12px;
    font-size: 12px;
    font-weight: 500;
}

.badge-success {
    background: #28a745;
    color: white;
}

.badge-warning {
    background: #ffc107;
    color: #333;
}

.badge-danger {
    background: #dc3545;
    color: white;
}

.badge-info {
    background: #17a2b8;
    color: white;
}

.badge-secondary {
    background: #6c757d;
    color: white;
}

.table-responsive {
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
}

.mt-3 {
    margin-top: 1rem;
}

.d-flex {
    display: flex;
}

.justify-content-end {
    justify-content: flex-end;
}

.ml-1 {
    margin-left: 0.25rem;
}
</style>
