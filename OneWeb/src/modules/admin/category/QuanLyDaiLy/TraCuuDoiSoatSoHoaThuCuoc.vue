<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">
                    Thông tin tìm kiếm
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w100">Loại dữ liệu</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select  class="form-control" >
                                        <option>
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" >
                                    <span class="name">Xuất file khi lấy dữ liệu</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="legend-title" style="margin-top: 10px!important;">
                    <div class="pull-left">Kết quả tìm kiếm
                        <span class="red" style="margin-left: 20px !important;">SL: </span>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="info-row">
                    <textarea rows="20" class="form-control" style="resize: none;"></textarea>
                </div>
            </div>
            
        </div>
        
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
    name:'TraCuuDoiSoatSoHoaThuCuoc',
    components:{
        breadcrumb,
        ActionTop
    },
    data(){
        return {
            header: {
                title: "Đối soát dữ liệu số hoá thu cước",
                list: [
                    { 
                        name: "Tra cứu", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Quản lý thu nợ", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Tra cứu thu nợ", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Tra cứu, đối soát số hoá thu cước",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            actions:[
                {
                    id:'laydulieu',
                    name:'Lấy dữ liệu',
                    active: true,
                    visible: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'xuatfile',
                    name:'Xuất File',
                    active: true,
                    icon_class:'one-excel',
                    visible:true
                },
            ],
        }
    },
    methods:{
        onActionClick(action){

        }
    }
}
</script>