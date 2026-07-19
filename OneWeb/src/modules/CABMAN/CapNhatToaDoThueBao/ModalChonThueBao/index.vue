<template src="./index.html">
</template>

<script>
export default {
    props: {
        dataSource: Array
    },
    methods: {
        grid_selectedRowChanged: function(dataItem){
            this.$emit('selectedRowChanged', dataItem);
        }
    }
}
</script>
