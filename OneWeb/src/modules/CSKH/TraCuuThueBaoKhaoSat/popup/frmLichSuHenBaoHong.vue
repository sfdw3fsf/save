<template>
    <b-modal
        id="popup-frmLichSuHenBaoHong"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        centered 
        @show="onShow"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Lịch sử hẹn báo hỏng
                </div>
                <div class="close -ap icon-close" data-dismiss="modal" 
                    @click="$bvModal.hide('popup-frmLichSuHenBaoHong')">
                </div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="table-content">
                        <table-c
                            :columns="[
                                {
                                    fieldName: 'GIOHEN_TU',
                                    headerText: 'Ngày hẹn từ',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'GIOHEN_DEN',
                                    headerText: 'Ngày hẹn đến',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NGAY_CN',
                                    headerText: 'Ngày CN',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NGUOI_CN',
                                    headerText: 'Người CN',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NOIDUNG',
                                    headerText: 'Nội dung',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'LYDOHEN',
                                    headerText: 'Lý do hẹn',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'DV_HEN',
                                    headerText: 'ĐV hẹn',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'KIEU_HEN',
                                    headerText: 'Kiểu hẹn',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'KETQUA_XM',
                                    headerText: 'KQ xác minh',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NGUOI_XM',
                                    headerText: 'NV xác minh',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                            ]"
                            :dataSource="pLichSuHen"
                            :showFilter="true"
                            ref="gridLichSuHenBaoHong"
                        >
                        </table-c>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
export default {
    name: "frmLichSuHenBaoHong",
    props: {
        baoHongId: {
            type: Number,
            default: 0
        },
        hdtbId: {
            type: Number,
            default: 0
        },
        kieu: {
            type: Number,
            default: 0
        }
    },
    data() {
        return {
            pLichSuHen: []
        };
    },
    methods: {
        async onShow() {
            this.$root.showLoading(true);
            try {
                if (this.kieu == 2) {
                    this.pLichSuHen = await this.getLichSuHenBaoHong();
                }
                else {

                }
            } catch (e) {
            } finally {
                this.$root.showLoading(false);
            }
        },
        async getLichSuHenBaoHong() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/lich-su-hen-bao-hong",
                    {
                        baoHongId: this.baoHongId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getLichSuHenThiCong() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-hd-tb/lich-hen-thi-cong",
                    {
                        hdtbId: this.hdtbId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        }
    }
};
</script>
