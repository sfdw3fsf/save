<template src='./index.html'>
</template>

<script>
export default {
    props: {
        modalShow: Boolean,
        lsThueBao: Array
    },
    methods: {
        closeModal: function() {
            this.$emit('closeModal');
        },
        grid_selectedRowChanged: function() {
            
        }
    }
}
</script>
