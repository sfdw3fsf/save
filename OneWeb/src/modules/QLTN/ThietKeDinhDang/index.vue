<template src="./index.template.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'

export default {
    components: { breadcrumb },
    name: 'ThietKeDinhDang',
    data() {
        return {
            header: {
                title: 'Cập nhật định dạng',
                list: [
                    { name: 'Hóa đơn', link: { name: 'Ui.cards' } },
                    { name: 'Cập nhật định dạng', link: { name: 'Ui.buttons' } },                                       
                ]
            },
        }
    },
    methods: {
        onNhapMoi() {

        },

        onGhiLai() {

        },

        onOpenXML() {

        },

        onExportXML() {
            
        }
    },
}
</script>
