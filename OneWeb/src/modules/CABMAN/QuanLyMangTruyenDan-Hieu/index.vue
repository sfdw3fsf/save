<template src="./index.template.html"></template>

<script>
    import ModalDsBe from './DanhSachBe/Modal'
    import ModalChuiCap from './ChuiCapQuaOngCong/Modal'
    import ModalDsCot from './DanhSachCot/Modal'
    import ModalTreoCap from './TreoCapLenCot/Modal'


    export default {
        components: {ModalDsBe, ModalChuiCap, ModalDsCot, ModalTreoCap},
        data: function () {
            return {
                dsDsLam: [],
                dataSend: {},
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
            xacNhan: async function (data) {
                console.log(data)
                // this.$root.showLoading(true);
                // try {
                //     var rs = await this.$root.context.get(
                //         '/web-ecms/danhmuc/NhomDslam/DanhSach'
                //     )
                //     this.dsDsLam = rs.data
                // } catch (error) {
                // } finally {
                //     this.$root.showLoading(false);
                // }
            },
        },
    };
</script>

