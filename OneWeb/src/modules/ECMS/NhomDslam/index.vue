<template src="./index.template.html"></template>

<script>
    import Modal from './Modal'

    export default {
        components: {Modal},
        data: function () {
            return {
                dsDsLam: [],
                selected: null
            }
        },
        created: function () {
            //console.log('data', this.getDsNhomDslam());
        },
        methods: {
            // getDsNhomDslam: async function () {
            //     this.$root.showLoading(true);
            //     try {
            //         var rs = await this.$root.context.get(
            //             '/web-ecms/danhmuc/NhomDslam/DanhSach'
            //         )
            //         this.dsDsLam = rs.data
            //     } catch (error) {
            //     } finally {
            //         this.$root.showLoading(false);
            //     }
            // },
            // grid1_selectedRowChanged(dataItem){
            //     console.log(dataItem);
            // },
        },
    };
</script>

