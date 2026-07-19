<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" @onActionClick="onActionClick" />
        <div class="page-content">
            <div class="grid-stack-box">
                <div class="box-col box-form marb0" id="boxLeft" style="width: calc(50% - 8px);">
                    <div class="legend-title">Thông tin bảng giá TSL</div>
                    <div class="info-row">
                        <div class="key w70">Bảng giá</div>
                        <div class="value">
                            <input type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w70">Từ ngày</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <input type="text" class="form-control">
                                        <span class="icon one-calendar"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w70">Đến ngày</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <input type="text" class="form-control">
                                        <span class="icon one-calendar"></span>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="info-row">
                        <div class="key w70">Dịch vụ</div>
                        <div class="value">
                            <div class="select-custom">
                                <select name="" id="" class="form-control">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="legend-title mart20">Danh sách bảng giá TSL</div>
                    <KTableV3 :columns="columns"
                        :dataSources="dataSources"
                        id="id"
                        tableHeight="515px"
                        :rowSelectedId="rowSelectedId"
                        @onSelectedRow="onSelectedRow"
                        :allowFilter="true"/>
                </div>
                <div class="gutter gutter-horizontal" style="width: 16px;"></div>
                <div class="box-col" id="boxRight" style="width: calc(50% - 8px);height: 800px;">
                    <div class="nav tabs tab-over">
                        <a href="#tab1" data-toggle="tab" @click.prevent="changeTab(1)" :class="{active: currentTab==1}">Khoản mục - Đối tượng</a>
                        <a href="#tab2" data-toggle="tab" @click.prevent="changeTab(2)" :class="{active: currentTab==2}">Loại kênh - Loại hình - Tốc độ</a>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane" :class="{active: currentTab==1}" id="tab1" >
                            <KhoanMucDoiTuong ref="khoanMucDoiTuong"/>
                        </div>
                        <div class="tab-pane" :class="{active: currentTab==2}" id="tab2">
                            <LoaiKenhLoaiHinhTocDo ref="loaiKenhLoaiHinhTocDo"/>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        <Footer/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KhoanMucDoiTuong from './KhoanMucDoiTuong.vue'
import LoaiKenhLoaiHinhTocDo from './LoaiKenhLoaiHinhTocDo.vue'
export default {
    name:'FreightRateTSL',
    components:{
        breadcrumb,
        Footer,
        KTableV3,
        ActionTop,
        KhoanMucDoiTuong,
        LoaiKenhLoaiHinhTocDo
    },
    data(){
        return {
            header: {
                title: "MỨC CƯỚC TSL",
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
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Hủy',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xóa',
                    active: true,
                    icon_class:'one-trash'
                },
            ],
            currentTab:1,
            columns:[
                {
                    field:'banggia',
                    label:'Bảng giá',
                    allowFilter:true
                },
                {
                    field:'ngaybatdau',
                    label:'Ngày bắt đầu',
                    allowFilter:true
                },
                {
                    field:'ngayketthuc',
                    label:'Ngày kết thúc',
                    allowFilter:true
                },
            ],
            dataSources:[],
            rowSelectedId:-1
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){

            }else if(action.id=='ghilai'){

            }else if(action.id=='huy'){

            }else if(action.id=='xoa'){

            }
        },
        changeTab(tab){
            this.currentTab=tab
        },
        onSelectedRow(item){

        },


    }
}
</script>