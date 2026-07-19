<template src="./index.html"></template>
<script>
export default {
    data() {
        return {
            ds_XuLy: {
                list: [],
                header: [
                    {fieldName: 'agent_', headerText: 'Tỉnh', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'product_', headerText: 'Mã thuê bao', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'format_', headerText: 'Mã khách hàng', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'type_', headerText: 'Tên thuê bao', allowFiltering: true , allowHtml : true, width: 200, textAlign: 'center',},
                    {fieldName: 'user_', headerText: 'Loại hình', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'date_', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 300, textAlign: 'center',},
                    {fieldName: 'agent_', headerText: 'Số ảo', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'agent_', headerText: 'Dịch vụ', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'agent_', headerText: 'Số điện thoại', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'agent_', headerText: 'Số giấy tờ', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    {fieldName: 'agent_', headerText: 'Trạng thái', allowFiltering: true , allowHtml : true, width: 100, textAlign: 'center',},
                    
                ],
                selected: {},
            }
        }
    }
}
</script> 