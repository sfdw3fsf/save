<template>  
    <div class="modal-content popup-box">
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">Danh sách nhân viên được chi trả</div>
                <div class="table-content" style="height: 500px;">
                    <DataGridCustom
                        ref="ds_chitiet"
                        v-bind:columns="columnsDSLLCT"
                        v-bind:dataSource="ds_chitiet"
                        :showColumnCheckbox="false"
                        :showFilter="true"
                        :enabledSelectFirstRow="true"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                        panelDataHeight="360"
                        :allowPaging="true" :enablePagingServer="false"
                    ></DataGridCustom>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
export default {
    name:'DanhSachChiTiet',
    components:{
        DataGridCustom
    },
    props:{
        ds_chitiet:{
            type:Array,
            default: []
        },
    },
    data() {
        return {
            ...columns,
        }
    },
    methods: {
        
    },
}
</script>