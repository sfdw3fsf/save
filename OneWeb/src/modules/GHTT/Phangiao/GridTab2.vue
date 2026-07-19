<script>
    import { GridComponent, GridPlugin, Page } from "@syncfusion/ej2-vue-grids";

    export default {
        components: {
            'ejs-grid': GridComponent
        },
        data () {
            return {
            data: [
                { OrderID: 10248, CustomerID: 'THUAN', Freight: 32.38 },
                { OrderID: 10249, CustomerID: 'DUNG', Freight: 11.61 },
                { OrderID: 10250, CustomerID: 'HANAR', Freight: 65.83 },
                { OrderID: 10251, CustomerID: 'VICTE', Freight: 41.34 },
                { OrderID: 10252, CustomerID: 'SUPRD', Freight: 51.3 },
                { OrderID: 10253, CustomerID: 'HANAR', Freight: 58.17 },
                { OrderID: 10254, CustomerID: 'CHOPS', Freight: 22.98 },
                { OrderID: 10255, CustomerID: 'RICSU', Freight: 148.33 },
                { OrderID: 10256, CustomerID: 'WELLI', Freight: 13.97 }
             ],
             pageSettings: { pageSize: 5 }
            }   
        },
        provide: {
            grid: [Page]
  }
    }
</script>

<template>
    <div >
        <!-- <ejs-grid
        :dataSource="data",
        allowScrolling : true,
		scrollSettings: { width: 400, height: 300}>
    </ejs-grid> -->
    <ejs-grid :dataSource="data"> </ejs-grid>
    </div>
</template>