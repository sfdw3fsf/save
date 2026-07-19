<template>
    <b-modal id="modalCRUDCot" ref="modalCRUDCot" size="xl" hide-footer @hidden="onModalHidden"
        modal-class="modal-crud-cot">
        <template #modal-header="{ close }">
            <h5 class="modal-title">
                Quản lý Cột
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
            <!-- Form thêm/sửa cột -->
            <div class="box-form">
                <div class="legend-title">
                    Thông tin cột
                </div>

                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w200">Tên cột <span class="required">*</span></div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.ten" placeholder="Nhập tên cột"
                                    maxlength="100" :readonly="!controls.txtTen.enabled" />
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Tên tắt</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.tenTat" placeholder="Nhập tên tắt"
                                    maxlength="20" :readonly="!controls.txtTenTat.enabled" />
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Chiều dài (cm)</div>
                            <div class="value">
                                <input type="number" class="form-control" v-model.number="form.chieuDai"
                                    placeholder="Nhập chiều dài" min="0" step="1"
                                    :readonly="!controls.txtChieuDai.enabled" />
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Chiều rộng (cm)</div>
                            <div class="value">
                                <input type="number" class="form-control" v-model.number="form.chieuRong"
                                    placeholder="Nhập chiều rộng" min="0" step="1"
                                    :readonly="!controls.txtChieuRong.enabled" />
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w200">Ghi chú</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.ghiChu" placeholder="Nhập ghi chú"
                                    maxlength="500" :readonly="!controls.txtGhiChu.enabled" />
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w200">Mô tả</div>
                            <div class="value">
                                <textarea class="form-control" v-model="form.moTa" rows="5"
                                    placeholder="Nhập mô tả chi tiết" maxlength="200"
                                    :readonly="!controls.txtMoTa.enabled"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <hr />

            <!-- Danh sách cột -->
            <div class="box-form">
                <div class="legend-title">
                    Danh sách cột
                </div>
                <DataGrid v-bind:columns="[
                    { fieldName: 'id', headerText: 'ID', width: 50, textAlign: 'Center' },
                    { fieldName: 'ten', headerText: 'Tên cột', allowFiltering: true },
                    { fieldName: 'tenTat', headerText: 'Tên tắt', width: 100, allowFiltering: true },
                    { fieldName: 'chieuDai', headerText: 'Chiều dài (cm)', width: 120, textAlign: 'Right', allowFiltering: true },
                    { fieldName: 'chieuRong', headerText: 'Chiều rộng (cm)', width: 130, textAlign: 'Right', allowFiltering: true },
                    { fieldName: 'ghiChu', headerText: 'Ghi chú', allowFiltering: true }
                ]" :dataSource="gridDataSource" :showFilter="true" :allowPaging="true" :enablePagingServer="false"
                    :showColumnCheckbox="false" @rowSelected="onRowSelected" dataKeyField="id" ref="grdColumns"
                    :allowExcelExport="false">
                </DataGrid>
            </div>
        </div>
    </b-modal>
</template>

<script>
export default {
    name: 'ModalCRUDCot',
    props: {
        roomId: {
            type: Number,
            required: true
        }
    },

    data() {
        return {
            loading: false,
            selectedColumn: null,
            form: {
                id: null,
                ten: '',
                tenTat: '',
                chieuDai: null,
                chieuRong: null,
                ghiChu: '',
                moTa: ''
            },
            controls: {
                txtTen: { enabled: false },
                txtTenTat: { enabled: false },
                txtChieuDai: { enabled: false },
                txtChieuRong: { enabled: false },
                txtGhiChu: { enabled: false },
                txtMoTa: { enabled: false }
            },
            buttons: {
                btnNhapMoi: { enabled: true },
                btnGhiLai: { enabled: false },
                btnHuyBo: { enabled: false },
                btnSua: { enabled: false },
                btnXoa: { enabled: false }
            },
            columnList: []
        }
    },

    watch: {
        'form.id': function (value) {
            this.buttons.btnSua.enabled = value > 0 && !this.controls.txtTen.enabled
            this.buttons.btnXoa.enabled = value > 0 && !this.controls.txtTen.enabled
        }
    },

    computed: {
        isFormValid() {
            return this.form.ten.trim() !== ''
        },

        gridDataSource() {
            return this.columnList.map(column => ({
                id: column.id,
                ten: column.ten,
                tenTat: column.tenTat || '',
                chieuDai: column.chieuDai ? column.chieuDai.toLocaleString('vi-VN') : '',
                chieuRong: column.chieuRong ? column.chieuRong.toLocaleString('vi-VN') : '',
                ghiChu: column.ghiChu || '',
                _original: column
            }))
        }
    },

    methods: {
        async showModal() {
            this.$refs.modalCRUDCot.show()
            this.resetForm()
            await this.loadColumnList()
        },

        closeModal() {
            this.$refs.modalCRUDCot.hide()
        },

        onModalHidden() {
            this.resetForm()
            this.selectedColumn = null
            this.$emit('close')
        },

        enableControls(enabled) {
            this.controls.txtTen.enabled = enabled
            this.controls.txtTenTat.enabled = enabled
            this.controls.txtChieuDai.enabled = enabled
            this.controls.txtChieuRong.enabled = enabled
            this.controls.txtGhiChu.enabled = enabled
            this.controls.txtMoTa.enabled = enabled

            this.buttons.btnGhiLai.enabled = enabled
            this.buttons.btnHuyBo.enabled = enabled
            this.buttons.btnNhapMoi.enabled = !enabled

            if (this.form.id > 0) {
                this.buttons.btnSua.enabled = !enabled
                this.buttons.btnXoa.enabled = !enabled
            }
        },

        setCurrentItem(column) {
            this.form.id = column.id
            this.form.ten = column.ten
            this.form.tenTat = column.tenTat || ''
            this.form.chieuDai = column.chieuDai
            this.form.chieuRong = column.chieuRong
            this.form.ghiChu = column.ghiChu || ''
            this.form.moTa = column.moTa || ''
            this.selectedColumn = column
        },

        resetForm() {
            this.form = {
                id: null,
                ten: '',
                tenTat: '',
                chieuDai: null,
                chieuRong: null,
                ghiChu: '',
                moTa: ''
            }
            this.selectedColumn = null
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

            await this.saveColumn()
        },

        onCancel() {
            if (this.form.id && this.form.id > 0) {
                const column = this.columnList.find(c => c.id === this.form.id)
                if (column) {
                    this.setCurrentItem(column)
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
            if (!this.selectedColumn) {
                this.$toast.warning('Vui lòng chọn một cột để xóa!')
                return
            }

            await this.deleteColumn(this.selectedColumn)
        },

        onRowSelected(args) {
            if (args && args.data && args.data._original) {
                this.setCurrentItem(args.data._original)
            }
        },

        async loadColumnList() {
            try {
                this.loading = true

                const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/get-ds-doituong-khonggian', {
                    loaiDoiTuong: 'COT',
                    doiTuongChaId: this.roomId
                })

                if (rs.error_code === 'BSS-00000000' && rs.data.result) {
                    this.columnList = rs.data.result.map((item) => ({
                        id: item.ID || item.id,
                        ten: item.TEN || '',
                        tenTat: item.TENTAT || '',
                        chieuDai: item.CHIEUDAI || 0,
                        chieuRong: item.CHIEURONG || 0,
                        ghiChu: item.GHICHU || '',
                        moTa: item.MOTA || ''
                    }))
                    console.log('✅ Column list loaded:', this.columnList)
                } else {
                    this.columnList = []
                }
            } catch (error) {
                console.error('❌ Error loading column list:', error)
                this.$toast.error('Lỗi khi tải danh sách cột')
            } finally {
                this.loading = false
            }
        },

        async saveColumn() {
            if (!this.isFormValid) {
                this.$toast.warning('Vui lòng điền đầy đủ thông tin bắt buộc!')
                return
            }

            try {
                this.$root.showLoading(true)

                const payload = {
                    loaiDoiTuong: 'COT',
                    doiTuongId: this.form.id,
                    doiTuongChaId: this.roomId,
                    ten: this.form.ten.trim(),
                    tenTat: this.form.tenTat.trim(),
                    chieuDai: this.form.chieuDai,
                    chieuRong: this.form.chieuRong,
                    ghiChu: this.form.ghiChu.trim(),
                    moTa: this.form.moTa.trim()
                }

                const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/upsert-doituong-khonggian', payload)

                if (rs.error_code === 'BSS-00000000' && rs.data.result !== -1 ) {
                    this.$toast.success(this.form.id ? 'Cập nhật cột thành công!' : 'Thêm cột mới thành công!')
                    await this.loadColumnList()
                    if (rs.data && rs.data.result && rs.data.result.id) {
                        const updatedItem = this.columnList.find(c => c.id === rs.data.result.id)
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
                console.error('❌ Error saving column:', error)
                this.$toast.error('Lỗi khi lưu cột!')
            } finally {
                this.$root.showLoading(false)
            }
        },

        async deleteColumn(column) {
            this.$confirm(`Xác nhận xóa cột "${column.ten}"?`, 'Xóa cột', {
                confirmButtonText: 'Xóa',
                cancelButtonText: 'Hủy',
                type: 'warning'
            })
                .then(async () => {
                    try {
                        this.$root.showLoading(true)

                        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/delete-doituong-khonggian', {
                            loaiDoiTuong: 'COT',
                            doiTuongId: column.id
                        })

                        if (rs.error_code === 'BSS-00000000' && rs.data.result !== -1) {
                            this.$toast.success('Xóa cột thành công!')
                            await this.loadColumnList()
                            this.resetForm()
                            this.$emit('refresh')
                        } else {
                            this.$toast.error(rs.error_description || 'Có lỗi xảy ra!')
                        }
                    } catch (error) {
                        console.error('❌ Error deleting column:', error)
                        this.$toast.error('Lỗi khi xóa cột!')
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
.modal-crud-cot .modal-body {
    padding: 0 !important;
}
</style>

<style scoped>
.box-form {
    border-radius: 4px;
}
</style>
