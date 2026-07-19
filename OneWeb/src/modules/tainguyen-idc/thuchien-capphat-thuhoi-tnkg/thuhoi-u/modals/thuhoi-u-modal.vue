<template src="./thuhoi-u-modal.html">
</template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import SelectExt from '@/components/Controls/SelectExt/index.vue';

export default {
    components: {
        DataGrid,
        SelectExt,
    },
    props: {
        phieuThiCongId: {
            type: Number,
            default: null,
        },
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            position: { X: 'center', Y: '50' },
            selectedU: null,
            selectedUs: [],
            selectedRackId: null,
            rackOptions: [],
            selectedRackInfo: null,
            dataGrid1Items: [],
            dataGrid1Columns: [
                {fieldName: 'ID_U', headerText: 'ID U', textAlign: 'Left', width: 80},
                {fieldName: 'TEN_U', headerText: 'Tên U', textAlign: 'Left', width: 120},
                {fieldName: 'VITRI_U', headerText: 'Vị trí U', textAlign: 'Left', width: 120},
                {fieldName: 'TRANGTHAI_SD', headerText: 'Trạng thái SD', textAlign: 'Left', width: 130},
                {fieldName: 'MOTA', headerText: 'Mô tả', textAlign: 'Left', width: 200}
            ],
        }
    },
    methods: {
        async dialogOpen() {
            try {
                this.$root.showLoading(true);
                await this.loadRackOptions();

                if (this.$refs.dialogThuHoiU) {
                    this.$refs.dialogThuHoiU.show();
                } else {
                    this.$toast.error("Dialog không tồn tại hoặc không được tham chiếu đúng");
                }
            } catch (e) {
                if (e.data && e.data.message) {
                    this.$toast.error(e.data.message);
                } else {
                    this.$toast.error("Đã xảy ra lỗi khi mở dialog");
                }
            } finally {
                this.$root.showLoading(false);
            }
        },
        closeDialog() {
            this.selectedU = null;
            this.selectedUs = [];
            this.selectedRackId = null;
            this.selectedRackInfo = null;
            this.dataGrid1Items = [];
            this.$refs.dialogThuHoiU.hide();
        },
        loadRackOptions: async function () {
            try {
                let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/ds-rack-sansang-thuhoi-u/${this.phieuThiCongId}`);
                if (response && response.data) {
                    this.rackOptions = response.data.map(item => ({
                        ...item,
                        TEN_RACK: item.TEN_RACK || `Rack ${item.ID_RACK}` // Handle null TEN_RACK
                    }));
                }
            } catch (error) {
                console.error('Error loading rack options:', error);
                this.rackOptions = [];
            }
        },
        loadAvailableUs: async function (rackId) {
            if (!rackId) {
                this.dataGrid1Items = [];
                return;
            }
            try {
                let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/ds-u-sansang-thuhoi?idPhieuThiCong=${this.phieuThiCongId}&rackId=${rackId}`);
                if (response && response.data) {
                    this.dataGrid1Items = response.data;
                }
            } catch (error) {
                console.error('Error loading available Us:', error);
                this.dataGrid1Items = [];
            }
        },
        onRackChange: function(selectedValue) {
            this.selectedRackId = selectedValue;
            this.selectedU = null;
            this.selectedUs = [];
            
            if (selectedValue) {
                const selectedRack = this.rackOptions.find(option => option.ID_RACK == selectedValue);
                if (selectedRack) {
                    this.selectedRackInfo = selectedRack;
                }
                this.loadAvailableUs(selectedValue);
            } else {
                this.selectedRackInfo = null;
                this.dataGrid1Items = [];
            }
        },
        grd1_RowSelected: function (args) {
            console.log('U selected:', args.data);
            
            // Handle both single item and array (Select All case)
            const itemsToAdd = Array.isArray(args.data) ? args.data : [args.data];
            
            itemsToAdd.forEach(item => {
                // Add to selected list if not already present
                const index = this.selectedUs.findIndex(existingItem => existingItem.ID_U === item.ID_U);
                if (index === -1) {
                    this.selectedUs.push(item);
                }
            });
            
            // Set selectedU to first item (for single selection compatibility)
            if (!Array.isArray(args.data)) {
                this.selectedU = args.data;
            } else if (itemsToAdd.length > 0) {
                this.selectedU = itemsToAdd[0];
            }
            
            console.log('Selected Us:', this.selectedUs);
        },
        grdItems_RowDeselected: function (args) {
            console.log('U deselected:', args.data);
            
            // Handle both single item and array (Deselect All case)
            const itemsToRemove = Array.isArray(args.data) ? args.data : [args.data];
            
            itemsToRemove.forEach(item => {
                // Remove from selected list
                const index = this.selectedUs.findIndex(existingItem => existingItem.ID_U === item.ID_U);
                if (index > -1) {
                    this.selectedUs.splice(index, 1);
                }
            });
            
            console.log('Selected Us after deselection:', this.selectedUs);
        },
        async onThuHoiU() {
            if (!this.selectedUs || this.selectedUs.length === 0) {
                this.$toast.warning('Vui lòng chọn ít nhất một U để thu hồi');
                return;
            }

            if (!this.phieuThiCongId) {
                this.$toast.warning('Không có thông tin phiếu thi công');
                return;
            }

            const message = this.selectedUs.length === 1 
                ? "Bạn có muốn thu hồi U này không ?"
                : `Bạn có muốn thu hồi ${this.selectedUs.length} U đã chọn không ?`;

            let flag = await this.$confirm(
                message,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không",
                    type: "info",
                    dangerouslyUseHTMLString: true,
                }
            )
                .then((s) => true)
                .catch((e) => {
                    return false;
                });
            
            if (flag) {
                try {
                    this.$root.showLoading(true);
                    const requestBody = {
                        idPhieuThiCong: this.phieuThiCongId,
                        idRack: this.selectedRackId,
                        listOfIdUnit: this.selectedUs.map(u => u.ID_U).join(',')
                    };

                    let response = await this.$root.context.post('/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/thuhoi-u', requestBody);
                    
                    if (response) {
                        const successMessage = this.selectedUs.length === 1 
                            ? "Thu hồi U thành công"
                            : `Thu hồi ${this.selectedUs.length} U thành công`;
                        this.$root.toastSuccess(successMessage);
                        this.$emit('uAllocated', this.selectedUs);
                        this.closeDialog();
                    } else {
                        if (response && response.message_detail) {
                            this.$root.toastError(response.message_detail);
                        } else {
                            this.$root.toastError("Thu hồi U thất bại");
                        }
                    }
                } catch (err) {
                    if (err && err.response && err.response.data && err.response.data.message_detail) {
                        this.$root.toastError(err.response.data.message_detail);
                    } else {
                        this.$root.toastError("Thu hồi U thất bại");
                    }
                } finally {
                    this.$root.showLoading(false);
                }
            }
        },
    },
    created: async function () {
        
    },
}
</script>