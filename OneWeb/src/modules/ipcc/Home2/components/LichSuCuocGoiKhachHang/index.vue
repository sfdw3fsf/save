<template>
    <div class="">
        <DataGrid class="table-result table-gachle"
                :columns="ds_XuLy.header"
                :dataSource="ds_XuLy.list"
                :showFilter="true"
                ref="tbCongDoan"
                :enabledSelectFirstRow="true"
                @rowSelected=""
                dataKeyField="" :showColumnCheckbox="false"
                :allowPaging="true"
                :enablePagingServer="false" />
    </div>
</template>

<script>
export default  {
    data() {
        return {
            ds_XuLy: {
                list: [],
                header: [
                    {fieldName: 'row_stt', headerText: 'STT', allowFiltering: true, width: 50, textAlign: 'center'},
                    {fieldName: 'id_', headerText: 'ID', allowFiltering: true , allowHtml : true, width: 50, textAlign: 'center', isPrimaryKey: true},
                    {fieldName: 'agent_', headerText: 'Tỉnh', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'product_', headerText: 'File', allowFiltering: true , allowHtml : true, width: 200, textAlign: 'center',},
                    {fieldName: 'format_', headerText: 'Kiểu file', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'type_', headerText: 'Kiểu xuất', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'user_', headerText: 'Người t.hiện', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'date_', headerText: 'Ngày t.hiện', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                ],
                selected: {},
            }
        }
    }
}
</script>
