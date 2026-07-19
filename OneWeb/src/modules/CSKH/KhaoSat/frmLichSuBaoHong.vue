<template>
    <!-- <b-modal
        :id="id"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        centered
        @show="onShow"
    > -->
    <ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        ref="popup-frmLichSuBaoHong"
        :header="'Lịch sử báo hỏng'"
        showCloseIcon="true"
        width="70%"
        :visible="false"
        :open="dialogOpen"
        :close="closeDialog"
        :isModal="true"
    >
        <div class="modal-content popup-box">
            <!-- <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Lịch sử báo hỏng
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="$bvModal.hide(id)"
                ></div>
            </div> -->
            <div class="popup-body">
                <div class="box-form">
                    <div class="table-content">
                        <DataGrid3
                            :columns="[
                                {
                                    fieldName: 'LOAI_PHIEU',
                                    headerText: 'Loại phiếu',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'MA_TB',
                                    headerText: 'Số máy /Acc',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'TRANGTHAI_BH',
                                    headerText: 'Trạng thái',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NGAY_BH',
                                    headerText: 'Ngày báo hỏng',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NGAY_HT',
                                    headerText: 'Ngày nghiệm thu',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NGUOI_BH',
                                    headerText: 'Người báo hỏng',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'DIENTHOAI_LH',
                                    headerText: 'Số ĐT',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'DIENTHOAI_BH',
                                    headerText: 'Điện thoại BH',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'GHICHU_HONG',
                                    headerText: 'Ghi chú BH',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'TINH_TRANG',
                                    headerText: 'Tình trạng',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'PHANLOAI_KH',
                                    headerText: 'Phân loại KH',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'CHITIEU_TG',
                                    headerText: 'Chỉ tiêu TG',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'KHOAPHIEU',
                                    headerText: 'Nghiệm thu',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'GHICHU_HONG',
                                    headerText: 'Ghi chú',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'GHICHU_XL',
                                    headerText: 'Ghi chú XL',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NGUOI_CN',
                                    headerText: 'Người tạo',
                                    allowFiltering: true,
                                    allowSorting: false
                                },
                                {
                                    fieldName: 'NHANVIEN_KT',
                                    headerText: 'Nhân viên KT',
                                    allowFiltering: true,
                                    allowSorting: false
                                }
                            ]"
                            :dataSource="pLichSuBaoHong"
                            :showFilter="true"
                            ref="gridLichSuBaoHong"
                            :allowPaging="true"
                    :enablePagingServer="false"
                        >
                        </DataGrid3>
                    </div>
                </div>
            </div>
        </div>
    <!-- </b-modal> -->
    </ejs-dialog>
</template>
<script>
export default {
    name: "frmLichSuBaoHong",
    props: {
    },
    data() {
        return {
            pLichSuBaoHong: [],
            id: 'popup-frmLichSuBaoHong',
            thueBaoId: 0,
        };
    },
    methods: {
        async dialogOpen() {
            await this.onShow();
        },
        openDialog() {
            this.$refs["popup-frmLichSuBaoHong"].show();
        },
        closeDialog() {
            this.$refs["popup-frmLichSuBaoHong"].hide();
        },
        async onShow() {
            this.$root.showLoading(true);
            try {
                this.pLichSuBaoHong = await this.getLichSuBaoHongThueBao();
            } catch (e) {
            } finally {
                this.$root.showLoading(false);
            }
        },
        async getLichSuBaoHongThueBao() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/lich-su-bao-hong",
                    {
                        thueBaoId: this.thueBaoId,
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
