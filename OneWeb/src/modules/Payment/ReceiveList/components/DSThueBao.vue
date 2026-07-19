<template>
    <DataGrid
      v-bind:columns="config"
      v-bind:dataSource="DSThueBao"
      :allowResizing="false"
      :showFilter="true"
      panelDataHeight="360px"
    >
    </DataGrid>
</template>

<script>
export default {
    props: {
        DSThueBao: {
            type: Array,
            default: []
        }
    },
    computed: {
        config() {
            return [{
                    fieldName: 'ma_tb',
                    headerText: 'Số máy/Acc',
                    allowFiltering: true
                },
                {
                    fieldName: 'ten_tb',
                    headerText: 'Tên thuê bao',
                    allowFiltering: true
                },
                {
                    fieldName: 'ngay_ht',
                    headerText: 'Ngày HT',
                    allowFiltering: true
                },
                {
                    fieldName: 'tongtien',
                    headerText: 'Tổng tiền',
                    allowFiltering: true
                },
                {
                    fieldName: 'trangthai_hd',
                    headerText: 'Trạng thái HĐ',
                    allowFiltering: true
                }
            ]
        }
    }
}
</script>

<style>

</style>
