<template>
    <DataGrid
      v-bind:columns="config"
      v-bind:dataSource="DSHopDongDaGan"
      :showFilter="true"
      :showColumnCheckbox="true"
      allowPaging
      :allowResizing="false"
      @selectedItemsChanged="selectedItemsChanged"
    >
    </DataGrid>
</template>

<script>
export default {
    props: {
        DSHopDongDaGan: {
            type: Array,
            default: []
        }
    },
    computed: {
        config() {
            return [{
                    fieldName: 'ma_gd',
                    headerText: 'Mã GD',
                    allowFiltering: true
                },
                {
                    fieldName: 'ten_kh',
                    headerText: 'Tên khách hàng',
                    allowFiltering: true
                },
                {
                    fieldName: 'diachi_kh',
                    headerText: 'Địa chỉ',
                    allowFiltering: true
                },
                {
                    fieldName: 'tongtien',
                    headerText: 'Tổng tiền',
                    allowFiltering: true
                },
                {
                    fieldName: 'ngay_ht',
                    headerText: 'Ngày HT',
                    allowFiltering: true
                }
            ]
        }
    },
    methods: {
        selectedItemsChanged(items) {
            this.$emit("onchangeRowDSHopDong", items);
        },
        rowSelected(item) {

        }
    }
}
</script>

<style>

</style>
