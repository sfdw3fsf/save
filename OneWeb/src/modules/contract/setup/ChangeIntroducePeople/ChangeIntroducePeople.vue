<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        
        <div class="page-content">
            <ThongTinNguoiGioiThieu />
            <LichSuThayDoi />
        </div>
        <Footer/>
    </div>    
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTable from '@/components/kylq_components/KTable.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThongTinNguoiGioiThieu from './ThongTinNguoiGioiThieu.vue'
import LichSuThayDoi from './LichSuThayDoi.vue'
import EventBus from '@/utils/eventBus'

export default {
    name:'ChangeIntroducePeople',
    components:{
        breadcrumb,
        Footer,
        KTable,
        ActionTop,
        ThongTinNguoiGioiThieu,
        LichSuThayDoi
    },
    data(){
        return {
            header: {
                title: "Cập nhật thông tin giới thiệu",
                list: [
                    { name: "Lập hợp đồng", 
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
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
            ],
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
                EventBus.$emit('change_introduce_people',{action:'ghilai'})
            }else if(action.id=='huy'){
                EventBus.$emit('change_introduce_people',{action:'huy'})
            }
        }
    }
}
</script>
<style scoped>

</style>