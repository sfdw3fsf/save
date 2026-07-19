<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
      
        <div class="page-content">
            <div class="box-form padt8 padb5">
                <div class="pull-right">
                    <div class="inline">
                        <div class="info-row marb0">
                            <div class="key fw6 w80">Tổng tiền</div>
                            <div class="value">
                                <input type="text" class="form-control red tright" v-model="input_tongtien">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <ThongTinPhieu ref="thongtinphieu" />
            <DanhSachThueBao ref="danhsachthuebao"/>
        </div>
        <Footer/>
    </div>   
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTable from '@/components/kylq_components/KTable.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThongTinPhieu from './ThongTinPhieu.vue'
import DanhSachThueBao from './DanhSachThueBao.vue'
export default {
    name:'CompleteChangeSpeedProfile',
    components:{
        breadcrumb,
        Footer,
        KTable,
        ActionTop,
        ThongTinPhieu,
        DanhSachThueBao
    },
    data(){
        return{
            header: {
                title: "Trạm Vt - thay đổi tốc độ Mytv",
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
                    id:'lay_tt',
                    name:'Lấy TT (F5)',
                    active: true,
                    icon_class:'one-file-attach'
                },
                {
                    id:'capnhat',
                    name:'Cập nhật (F9)',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'traphieu',
                    name:'Trả phiếu',
                    active: true,
                    icon_class:'one-file-back'
                },
                {
                    id:'hoanthien',
                    name:'Hoàn thiện',
                    active: true,
                    icon_class:'one-file-check'
                },
                {
                    id:'xuatfile',
                    name:'Xuất file',
                    active: true,
                    icon_class:'one-download'
                },
                {
                    id:'xuatphieu',
                    name:'Xuất phiếu',
                    active: true,
                    icon_class:'one-xlsx-import'
                },
                {
                    id:'henld',
                    name:'Hẹn LĐ',
                    active: true,
                    icon_class:'one-henld'
                },
                {
                    id:'thongsokt',
                    name:'Thông số KT',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_settings'
                },
                {
                    id:'tonphieu',
                    name:'Tồn phiếu',
                    active: true,
                    icon_class:'one-calendar-number'
                },
                {
                    id:'gankv',
                    name:'Gán KV',
                    active: true,
                    icon_class:'-ap icon-pin'
                },
            ],
            input_tongtien:'0'
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='lay_tt'){

            }else if(action.id=='capnhat'){

            }else if(action.id=='traphieu'){

            }else if(action.id=='hoanthien'){

            }else if(action.id=='xuatfile'){

            }else if(action.id=='xuatphieu'){

            }else if(action.id=='henld'){

            }else if(action.id=='thongsokt'){

            }else if(action.id=='tonphieu'){

            }else if(action.id=='gankv'){

            }
        }
    },
    mounted(){

    }

}
</script>
<style scoped>

</style>