<template>
    <DataGrid
        v-bind:columns="columns"
        v-bind:dataSource="dataSource"
        :showColumnCheckbox="showColumnCheckbox"
        :showFilter="false"
        :allowPaging="false"
        ref="DataGridCustom"
        :enablePagingServer="true"
        :enabledSelectFirstRow="false"
        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
        @rowSelected="GridFocus"
        @recordDoubleClick="GridDoubleClick"
    ></DataGrid>
</template>
<script>
export default {
    data() {
        return {
            dataSource: []
        }
    },
    props: ['columns', 'showColumnCheckbox'],
    methods: {
        GridFocus(data) {
            this.$emit("rowSelected", data)
        },
        GridDoubleClick(data) {
            this.$emit("recordDoubleClick", data)
        },
        getSelectedRecords() {
            return this.$refs.DataGridCustom.getSelectedRecords()
        },
        getSelectedRowIndexes() {
            return this.$refs.DataGridCustom.$refs.grid.getSelectedRowIndexes()
        },
        setCurrentSelectedRow(item) {
            this.$refs.DataGridCustom.setCurrentSelectedRow(item)
            this.$refs.DataGridCustom.flagSelectedRowIndexes = [];
            this.$refs.DataGridCustom.flagSelectedRowIndexes.push(item);
        }
    }
}
</script>