<script>
import { GridComponent, ColumnDirective, ColumnsDirective, GridPlugin, Page, Sort, Filter } from "@syncfusion/ej2-vue-grids";
import api from '@/modules/contract/setup/CompleteProfileBuySamsungTV/Api.js';

export default {
    components: {
        'ejs-grid': GridComponent,
        'e-column': ColumnDirective,
        'e-columns': ColumnsDirective,
        api
    },
    data() {
        return {
            pageSettings: { pageSize: 5 },
            grdTab41: {
                dataSource: [],
                // value: 0,
            },
        }
    },
    provide: {
        grid: [Page]
    },
    created() {
        this.getGrdTab41()
    },
    methods: {
        async getGrdTab41() {
            try {
                let response = await api.getcboDichVuVT(this.axios);
                this.grdTab41.dataSource = response.data.data
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
        <ejs-grid id="grdTab41" :dataSource="grdTab41.dataSource" :allowPaging='true' :allowSorting='true'
            :allowFiltering='true' :SelectionType='true'>
            <e-columns>
                <e-column type='checkbox' width='20'></e-column>
                <e-column field='DICHVUVT_ID' headerText='Dịch vụ VT ID' textAlign='center' width=50></e-column>
                <e-column field='TEN_DVVT' headerText='Tên DVVT' textAlign='left' width=120></e-column>
            </e-columns>
        </ejs-grid>
    </div>
</template>