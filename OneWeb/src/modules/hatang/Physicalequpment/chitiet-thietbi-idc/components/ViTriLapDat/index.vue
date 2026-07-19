<template src="./index.html"></template>

<script>
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
import breadcrumb from '@/components/breadcrumb'
import { createUpdateViTriLapDat, getAllInitialData, getViTriLapDatThietBi } from './api';

export default {
    name: "ViTriLapDat",
    components: {
        breadcrumb,
        Select2Ext
    },
    props: {
        currentItem: {
            type: Object,
            required: true
        },
        currentMode: {
            type: String,
            required: true
        },
        controls: {
            type: Object,
            required: true
        }
    },
    data: function () {
        return {
            dsDonViIDC: [],
            dsIDC: [],
            dsToaNha: [],
            dsMatSan: [],
            dsPhongZone: [],
            dsDonViIDCHienThi: [],
            dsIDCHienThi: [],
            dsToaNhaHienThi: [],
            dsMatSanHienThi: [],
            dsPhongZoneHienThi: [],
            viTriLapDat: {
                donViQLyId: null,
                idcId: null,
                toaNhaId: null,
                matSanId: null,
                phongId: null,
                idcRackId: null,
                slRackUnit: null,
                kieuLapDat: null,
                upUnitId: null,
                downUnitId: null,
                matRackUnit: null,
                canhRackUnit: null
            },
        }
    },
    watch: {
        "viTriLapDat.donViQLyId": function(newDVId) {
            this.dsIDCHienThi = this.dsIDC.filter(e => e.ID_DV == newDVId)
                                    .map(e => {
                                        return {
                                            id: e.ID_IDC,
                                            text: e.TEN_IDC
                                        }
                                    })
            this.dsToaNhaHienThi = [];
            this.dsMatSanHienThi = [];
            this.dsPhongZoneHienThi = [];
        },
        "viTriLapDat.idcId": function(newIDCId) {
            this.dsToaNhaHienThi = this.dsToaNha.filter(e => e.ID_IDC == newIDCId)
                                    .map(e => {
                                        return {
                                            id: e.ID_TOANHA,
                                            text: e.TEN_TOANHA
                                        }
                                    })
            this.dsMatSanHienThi = [];
            this.dsPhongZoneHienThi = [];
        },
        "viTriLapDat.toaNhaId": function(newToaNhaId) {
            this.dsMatSanHienThi = this.dsMatSan.filter(e => e.TOANHA_ID == newToaNhaId)
                                    .map(e => {
                                        return {
                                            id: e.ID,
                                            text: e.TEN
                                        }
                                    })
            this.dsPhongZoneHienThi = [];
        },
        "viTriLapDat.matSanId": function(newMatSanId) {
            this.dsPhongZoneHienThi = this.dsPhongZone.filter(e => e.ID_MATSAN == newMatSanId)
                                        .map(e => {
                                            return {
                                                id: e.ID,
                                                text: e.TEN
                                            }
                                        })
        }
    },
    methods: {
        loadInitialData: async function () {
            this.$root.showLoading(true);
            var result = await getAllInitialData(this.axios);
            if (result.error) {
                this.$root.toastError(result.error + " Vui lòng tải lại trang!");
                return;
            } else {
                this.dsDonViIDC = result.dsDonViIDC;
                this.dsIDC = result.dsIDC;
                this.dsToaNha = result.dsToaNha;
                this.dsMatSan = result.dsMatSan;
                this.dsPhongZone = result.dsPhongZone;
            }
            this.dsDonViIDCHienThi = this.dsDonViIDC.map(e => {
                                            return {
                                                id: e.id,
                                                text: e.ten,
                                            }
                                        });

            if (this.currentMode === "VIEW" || "EDIT") {
                if (this.currentItem.donViQuanLyId) {
                    result = await getViTriLapDatThietBi(this.axios, this.currentItem.id);
                    if (result.error) {
                        this.$root.toastError(error + " Vui lòng tải lại trang!");
                        return;
                    }
                    this.viTriLapDat.donViQLyId = this.currentItem.donViQuanLyId;
                    this.viTriLapDat.idcId = result.idc_id;
                    this.viTriLapDat.toaNhaId = result.toanha_id;
                    this.viTriLapDat.matSanId = result.matsan_id;
                    this.viTriLapDat.phongId = result.phong_id;
                    this.viTriLapDat.idcRackId = result.idc_rack_id;
                    this.viTriLapDat.slRackUnit = result.sl_rackunit;
                    this.viTriLapDat.kieuLapDat = result.kieu_ld;
                    this.viTriLapDat.upUnitId = result.up_unit_id;
                    this.viTriLapDat.downUnitId = result.down_unit_id;
                    this.viTriLapDat.matRackUnit = result.mat_rackunit;
                    this.viTriLapDat.canhRackUnit = result.canh_rackunit;
                }

                if (this.currentItem.donViQuanLyId) {
                    this.dsIDCHienThi = this.dsIDC.filter(e => e.ID_DV == this.viTriLapDat.donViQLyId)
                                    .map(e => {
                                        return {
                                            id: e.ID_IDC,
                                            text: e.TEN_IDC,
                                        }
                                    })
                    this.dsToaNhaHienThi = this.dsToaNha.filter(e => e.ID_IDC == this.viTriLapDat.idcId)
                                            .map(e => {
                                                return {
                                                    id: e.ID_TOANHA,
                                                    text: e.TEN_TOANHA,
                                                }
                                            })
                    this.dsMatSanHienThi = this.dsMatSan.filter(e => e.TOANHA_ID == this.viTriLapDat.toaNhaId)
                                            .map(e => {
                                                return {
                                                    id: e.ID,
                                                    text: e.TEN,
                                                }
                                            })
                    this.dsPhongZoneHienThi = this.dsPhongZone.filter(e => e.ID_MATSAN == this.viTriLapDat.matSanId)
                                            .map(e => {
                                                return {
                                                    id: e.ID,
                                                    text: e.TEN,
                                                }
                                            })
                }
            } else if (this.currentMode === "ADD") {
                if (this.currentItem.donViQuanLyId) {
                    this.viTriLapDat.donViQLyId = this.currentItem.donViQuanLyId;
                    this.dsIDCHienThi = this.dsIDC.filter(e => e.ID_DV == this.viTriLapDat.donViQLyId)
                                            .map(e => {
                                                return {
                                                    id: e.ID_IDC,
                                                    text: e.TEN_IDC,
                                                }
                                            })
                }
            }
            this.$root.showLoading(false);
        },
        validateViTriLapDat: function() {
            if (!this.viTriLapDat.donViQLyId
                || !this.viTriLapDat.idcId
                || !this.viTriLapDat.toaNhaId
                || !this.viTriLapDat.matSanId
            ) {
                this.$root.toastError("Bạn chưa chọn đủ các thông tin cần thiết ở tab 'Vị trí lắp đặt'!");
                return false;
            }
            return true;
        },
        onSaveViTriLapDat: async function () {
            this.$root.showLoading(true);
            var data = {
                thietBiId: this.currentItem.id,
                ...this.viTriLapDat
            };
            var result = await createUpdateViTriLapDat(this.axios, data);
            this.$root.showLoading(false);
            if (!result.error) {
                if (this.currentMode === "ADD") {
                    this.$toast.success("Thêm vị trí lắp đặt thành công!");
                } else if (this.currentMode === "EDIT") {
                    this.$toast.success("Cập nhật vị trí lắp đặt thành công!");
                }
            } else {
                this.$root.toastError(result.error);
            }
        },
    },
}
</script>

<style lang="css" scoped>
.main-body {
    padding-left: 4vw;
    padding-right: 4vw;
}

.bg-button {
    background-color: #035FCC;
}
</style>
