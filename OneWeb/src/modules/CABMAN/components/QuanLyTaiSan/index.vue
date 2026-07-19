<template src="./index.html"></template>
<script>
import Modal from "./Modal"

export default {
    name: 'QuanLyTaiSan',
    components: { Modal },
    mounted () {
        this.$store.dispatch('setHeader', {
            title: 'Tiếp nhận yêu cầu khoá máy cố định',
            list: [
                { 
                    name: 'Lập hợp đồng',
                    link: { name: 'Ui.cards' } },
                {
                    name: 'Lắp đặt mới',
                    link: { name: 'Ui.buttons' }
                }
            ]
        })
    }
}
</script>
