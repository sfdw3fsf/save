<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions"/>
        <div class="page-content">
            <a @click="showModal" class="btn btn-action">Show modal</a>
        </div>
        <Footer/>
        <!-- Modal -->
        <UpdateItemModal ref="updateItemModal"/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import UpdateItemModal from './UpdateItemModal.vue'
export default {
    name:'UpdateItem',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        UpdateItemModal
    },
    data(){
        return{
            header: {
                title: "Cập nhật khoản mục",
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
            this.$refs.updateItemModal.showModal()
        }
    }
}
</script>