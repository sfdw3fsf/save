<template>
    <DataGrid
      v-bind:columns="config"
      v-bind:dataSource="danhSachBK"
      :showFilter="true"
      :showColumnCheckbox="true"
      :allowResizing="false"
      @selectedItemsChanged="selectedItemsChanged"
    >
    </DataGrid>
</template>

<script>
export default {
    props: {
        danhSachBK: {
            type: Array,
            default: []
        }
    },
    computed: {
        config() {
            return [{
                    fieldName: 'tt',
                    headerText: 'STT',
                    allowFiltering: true
                },
                {
                    fieldName: 'bangke_id',
                    headerText: 'Mã bảng kê',
                    allowFiltering: true
                },
                {
                    fieldName: 'ngay_cn',
                    headerText: 'Ngày lập',
                    allowFiltering: true
                },
                {
                    fieldName: 'nguoi_cn',
                    headerText: 'Người lập',
                    allowFiltering: true
                },
                {
                    fieldName: 'ten_nv_nop',
                    headerText: 'Người nộp',
                    allowFiltering: true
                },
                {
                    fieldName: 'tongtien',
                    headerText: 'Tổng tiền',
                    allowFiltering: true
                },
                {
                    fieldName: 'trangthai_bk',
                    headerText: 'Trạng thái',
                    allowFiltering: true
                },
            ]
        }
    },
    methods: {
        selectedItemsChanged(items) {
            this.$emit("onchangeRowDSBK", items);
        },
        rowSelected(item) {

        }
    }
}
</script>

<style>

</style>
