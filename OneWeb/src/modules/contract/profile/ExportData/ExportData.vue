<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions"/>
        <div class="page-content">
            <a @click="showModal" class="btn btn-action">Show modal</a>
        </div>
        <Footer/>
        <!-- Modal -->
        <ExportDataModal ref="exportDataModal" :data="dataSources"/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ExportDataModal from './ExportDataModal.vue'

export default {
    name:'ExportData',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        ExportDataModal
    },
    data(){
        return{
            header: {
                title: "Đồng bộ vật tư",
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
            ],
            dataSources:[]
        }
    },
    methods:{
        showModal(){
            if(this.dataSources.length==0){
                this.$toast.error('Không có dữ liệu Test')
                return
            }
            this.$refs.exportDataModal.showModal()
        },
        async initDuLieu(){
            this.dataSources=[]
            this.dataSources=await this.ds_phieu_khao_sat_v2({
                maGd: "0",
                ttHdId: 0,
                huongGiaoId: 0,
                loaiHdId: "33"
            })
        },
        async ds_phieu_khao_sat_v2(data){
            try{
                this.loading(true)
                let response=await this.axios.post('/web-thicong/thuebao/ds_phieu_khao_sat_v2', data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }
}
</script>