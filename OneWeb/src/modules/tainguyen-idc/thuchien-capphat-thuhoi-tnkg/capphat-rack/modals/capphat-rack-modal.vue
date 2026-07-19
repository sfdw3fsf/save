<template src="./capphat-rack-modal.html">
</template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';

export default {
    components: {
        DataGrid,
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
            selectedRack: null,
            dataGrid1Items: [],
            dataGrid1Columns: [
              {fieldName: 'RACK_ID', headerText: 'ID', width: 80, textAlign: 'Center'},
              {fieldName: 'TEN_RACK', headerText: 'Tên Rack', textAlign: 'Left'},
              {fieldName: 'TEN_LOAIRACK', headerText: 'Loại Rack', textAlign: 'Left'},
              {fieldName: 'MA_TS', headerText: 'Mã tài sản', width: 100, textAlign: 'Left'},
              {fieldName: 'TRANGTHAI_VH', headerText: 'Trạng thái vận hành', textAlign: 'Left'},
              {fieldName: 'TEN_QUYHOACH', headerText: 'Trạng thái theo quy hoạch', textAlign: 'Left'},
              {fieldName: 'TEN_CCDV', headerText: 'Trạng thái rack CCDV', textAlign: 'Left'},
              {fieldName: 'TEN_IDC', headerText: 'IDC', textAlign: 'Left'},
              {fieldName: 'TEN_MATSAN', headerText: 'Mặt sàn', textAlign: 'Left'},
              {fieldName: 'TEN_PHONG', headerText: 'Phòng/Zone', textAlign: 'Left'},
              {fieldName: 'VITRI_RACK', headerText: 'Vị trí Rack', width: 100, textAlign: 'Left'},
              {fieldName: 'SOHUU_RACK', headerText: 'Sở hữu', width: 100, textAlign: 'Left'},
            ],
        }
    },
    methods: {
        async dialogOpen() {
            try {
                this.$root.showLoading(true);
                await this.loadAvailableRacks();

                if (this.$refs.dialogCapPhatRack) {
                    this.$refs.dialogCapPhatRack.show();
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
            this.selectedRack = null;
            this.$refs.dialogCapPhatRack.hide();
        },
        loadAvailableRacks: async function () {
            try {
                let response = await this.$root.context.post('/web-ecms/thongtin-rack/get-available-racks');
                if (response && response.data) {
                    this.dataGrid1Items = response.data;
                }
            } catch (error) {
                console.error('Error loading available racks:', error);
                this.dataGrid1Items = [];
            }
        },
        grd1_RowSelected: function (args) {
            console.log('Rack selected:', args.data);
            this.selectedRack = args.data;
        },
        async onCapPhatRack() {
            if (!this.selectedRack) {
                this.$toast.warning('Vui lòng chọn rack để cấp phát');
                return;
            }

            if (!this.phieuThiCongId) {
                this.$toast.warning('Không có thông tin phiếu thi công');
                return;
            }

            let flag = await this.$confirm(
                "Bạn có muốn cấp phát rack này không ?",
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
                        idRack: this.selectedRack.RACK_ID
                    };

                    let response = await this.$root.context.post('/web-ecms/idc/thi-cong/cap-phat-rack/capphat-rack', requestBody);

                    if (response) {
                        this.$root.toastSuccess("Cấp phát rack thành công");
                        this.$emit('rackAllocated', this.selectedRack);
                        this.closeDialog();
                    } else {
                        if (response && response.message_detail) {
                            this.$root.toastError(response.message_detail);
                        } else {
                            this.$root.toastError("Cấp phát rack thất bại");
                        }
                    }
                } catch (err) {
                    if (err && err.response && err.response.data && err.response.data.message_detail) {
                        this.$root.toastError(err.response.data.message_detail);
                    } else {
                        this.$root.toastError("Cấp phát rack thất bại");
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
