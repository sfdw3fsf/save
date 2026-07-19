<script>
import { GridComponent, ColumnDirective, ColumnsDirective, GridPlugin, Page, Sort, Filter } from "@syncfusion/ej2-vue-grids";
import api from '@/modules/contract/setup/CompleteProfileBuySamsungTV/Api.js';


export default {
    components: {
        'ejs-grid': GridComponent,
        'e-column': ColumnDirective,
        'e-columns': ColumnsDirective,
        
    },

    data() {
        return {
            // data: [
            //     { OrderID: 10248, CustomerID: 'THUAN', Freight: 32.38 },
            //     { OrderID: 10249, CustomerID: 'DUNG', Freight: 11.61 },
            //     { OrderID: 10250, CustomerID: 'HANAR', Freight: 65.83 },
            //     { OrderID: 10251, CustomerID: 'VICTE', Freight: 41.34 },
            //     { OrderID: 10252, CustomerID: 'SUPRD', Freight: 51.3 },
            //     { OrderID: 10253, CustomerID: 'HANAR', Freight: 58.17 },
            //     { OrderID: 10254, CustomerID: 'CHOPS', Freight: 22.98 },
            //     { OrderID: 10255, CustomerID: 'RICSU', Freight: 148.33 },
            //     { OrderID: 10256, CustomerID: 'WELLI', Freight: 13.97 },
            //     { OrderID: 10248, CustomerID: 'THUAN', Freight: 32.38 },
            //     { OrderID: 10249, CustomerID: 'DUNG', Freight: 11.61 },
            //     { OrderID: 10250, CustomerID: 'HANAR', Freight: 65.83 },
            //     { OrderID: 10251, CustomerID: 'VICTE', Freight: 41.34 },
            //     { OrderID: 10252, CustomerID: 'SUPRD', Freight: 51.3 },
            //     { OrderID: 10253, CustomerID: 'HANAR', Freight: 58.17 },
            //     { OrderID: 10254, CustomerID: 'CHOPS', Freight: 22.98 },
            //     { OrderID: 10255, CustomerID: 'RICSU', Freight: 148.33 },
            //     { OrderID: 10256, CustomerID: 'WELLI', Freight: 13.97 }
            // ]

            pageSettings: { pageSize: 5 },
            grdTab11: {
                dataSource: [],
                value: 0,
            },
        };
    },
    provide: {
        grid: [Page, Sort, Filter]
    },
    created() {
        this.getGrdTab11()
    },
    methods: {
        async getGrdTab11() {
            try {
                let response = await api.getcboDichVuVT(this.axios);
                this.grdTab11.dataSource = response.data.data
                console.log(response.data)
            } catch (e) {
                console.log(e)
            }
        },
    }
}
</script>

<template>
    <div>
        <ejs-grid id="grdTab11" :dataSource="grdTab11.dataSource" :allowPaging='true' :allowSorting='true' :allowFiltering='true' :SelectionType='true'>
            <e-columns>
                <e-column type='checkbox' width='50'></e-column>
                <e-column field='DICHVUVT_ID' headerText='Dịch vụ VT ID' textAlign='center' width=90></e-column>
                <e-column field='TEN_DVVT' headerText='Tên DVVT' textAlign='center' width=120></e-column>
            </e-columns>
        </ejs-grid>
    </div>
</template>

<style>
    @import "@syncfusion/ej2-base/styles/material.css";
    @import "@syncfusion/ej2-buttons/styles/material.css";
    @import "@syncfusion/ej2-calendars/styles/material.css";
    @import "@syncfusion/ej2-dropdowns/styles/material.css";
    @import "@syncfusion/ej2-inputs/styles/material.css";
    @import "@syncfusion/ej2-navigations/styles/material.css";
    @import "@syncfusion/ej2-popups/styles/material.css";
    @import "@syncfusion/ej2-splitbuttons/styles/material.css";
    @import "@syncfusion/ej2-vue-grids/styles/material.css";
</style>