<template src="./index.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb';
import KDataGrid from '@/components/kylq_components/KDataGrid';
import { getLsThayDoi } from './api';

export default {
    name: "LichSuThayDoi",
    components: {
        breadcrumb,
        KDataGrid
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
            lsThayDoiGrid: {
                columns: [
                    {
                        fieldName: "STT",
                        headerText: "STT",
                        width: "110",
                        textAlign: "Center"
                    },
                    {
                        fieldName: "HANHDONG",
                        headerText: "Tên tác động",
                        textAlign: "Center",
                        width: "160"
                    },
                    {
                        fieldName: "TENCOT",
                        headerText: "Trường thông tin thay đổi",
                        textAlign: "Center",
                        width: "260"
                    },
                    {
                        fieldName: "GIATRI_CU",
                        headerText: "Giá trị cũ",
                        textAlign: "Center",
                    },
                    {
                        fieldName: "GIATRI_MOI",
                        headerText: "Giá trị mới",
                        textAlign: "Center",
                    },
                    {
                        fieldName: "NGUOI_CN",
                        headerText: "Người thực hiện",
                        textAlign: "Center",
                    },
                    {
                        fieldName: "NGAY_CN",
                        headerText: "Thời điểm",
                        textAlign: "Center",
                    },

                ],
                dataSource: [],
            }
        }
    },
    methods: {
        loadInitialData: async function () {
            if (!this.currentItem || !this.currentItem.id) {
                return;
            }
            this.$root.showLoading(true);
            var result = await getLsThayDoi(this.axios, this.currentItem.id);
            if (result.error) {
                this.$root.toastError(result.error + " Vui lòng tải lại trang!");
                return;
            } else {
                this.lsThayDoiGrid.dataSource = result.data.map((item, index) => {
                    return {
                        STT: index + 1,
                        ...item
                    }
                });
            }
            this.$root.showLoading(false);
        },
    }
}
</script>

<style lang="css" scoped>
.history-label {
    background-color: rgba(0, 0, 0, 0.04);
    width: 210px;
    text-align: center;
    font-weight: 500;
    padding: 8px;
    margin-bottom: 12px;
}

.data-grid {
    margin-bottom: 5vh;
}
</style>
