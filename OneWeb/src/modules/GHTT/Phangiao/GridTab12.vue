<script>
    import { GridComponent } from "@syncfusion/ej2-vue-grids";

    export default {
        components: {
            'ejs-grid': GridComponent
        },
        data () {
            return {
            data: [
                { OrderID: 10248, CustomerID: 'VINET', Freight: 32.38 },
                { OrderID: 10249, CustomerID: 'TOMSP', Freight: 11.61 },
                { OrderID: 10250, CustomerID: 'HANAR', Freight: 65.83 },
                { OrderID: 10251, CustomerID: 'VICTE', Freight: 41.34 },
                { OrderID: 10252, CustomerID: 'SUPRD', Freight: 51.3 },
                { OrderID: 10253, CustomerID: 'HANAR', Freight: 58.17 },
                { OrderID: 10254, CustomerID: 'CHOPS', Freight: 22.98 },
                { OrderID: 10255, CustomerID: 'RICSU', Freight: 148.33 },
                { OrderID: 10256, CustomerID: 'WELLI', Freight: 13.97 }
             ]
            }   
        }
    }
</script>

<template>
    <div style="height: 260px;">
        <ejs-grid :dataSource="data"> </ejs-grid>
    </div>
</template>