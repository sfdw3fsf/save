<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" />
        <div class="page-content">
            <a @click="showModal" class="btn btn-action">Show modal</a>
        </div>
        <Footer/>
        <!-- Modal -->
        <CapNhatGiaTriThuocTinhModal ref="capNhatGiaTriThuocTinhModal"/>

    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import CapNhatGiaTriThuocTinhModal from './CapNhatGiaTriThuocTinhModal.vue'

export default {
    name:'UpdateAttributeValues',
    components:{
        breadcrumb,
        Footer,
        ActionTop,
        CapNhatGiaTriThuocTinhModal
    },
    data(){
        return{
            header: {
                title: "Tiếp nhận yêu cầu khoá máy cố định",
                list: [
                    {
                        name: "Lập hợp đồng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Lắp đặt mới",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'danhsach',
                    name:'Danh sách',
                    active: true,
                    icon_class:'design_bullet-list-67 nc-icon-glyph'
                },
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'ui-1_check-circle-08 nc-icon-glyph'
                },
                {
                    id:'xuatexcel',
                    name:'Xuất Excel',
                    active: true,
                    icon_class:'one-excel'
                }
            ]
        }
    },
    methods:{
        showModal(){
            this.$refs.capNhatGiaTriThuocTinhModal.showModal()
        }
    }

}
</script>