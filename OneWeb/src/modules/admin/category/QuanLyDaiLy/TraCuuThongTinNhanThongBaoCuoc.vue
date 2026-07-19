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
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="key w100">Mã TT</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Email</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Từ ngày</div>
                            <div class="value w20 padt7">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkTuNgay">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <k-date-picker v-model="tu_ngay" :disabled="!checkTuNgay"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w100">Số ĐT</div>
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Trạng thái</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Đến ngày</div>
                            <div class="value w20 padt7">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkDenNgay">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <k-date-picker v-model="den_ngay" :disabled="!checkDenNgay"/>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách thông báo cước</div>
                <KDataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"/>
            </div>
        </div>
        <!-- Modal -->
        <ChiTietTrangThaiSMSModal ref="chiTietTrangThaiSMSModal"/>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import ChiTietTrangThaiSMSModal from './popups/ChiTietTrangThaiSMSModal.vue'
export default {
    name:'TraCuuThongTinNhanThongBaoCuoc',
    components:{
        breadcrumb,
        ActionTop,
        KDataGrid,
        KDatePicker,
        ChiTietTrangThaiSMSModal
    },
    data(){
        return {
            header: {
                title: "Tra cứu thông tin nhận thông báo cước",
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
                        name: "Tra cứu thông tin nhận thông báo cước",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            actions:[
                {
                    id:'timkiem',
                    name:'Tìm kiếm',
                    active: true,
                    visible: true,
                    icon_class:'one-search'
                },
                {
                    id:'xuat_excel',
                    name:'Xuất Excel',
                    active: true,
                    icon_class:'one-excel',
                    visible:true
                },
                {
                    id:'trangthai_sms',
                    name:'Trạng thái SMS',
                    active: true,
                    icon_class:'one-money',
                    visible:true
                },
            ],
            den_ngay:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            tu_ngay:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            checkTuNgay:false,
            checkDenNgay:false,
            columns:[
                {
                    fieldName:'STT',
                    headerText:'STT',
                    allowFiltering:true
                },
                {
                    fieldName:'kenh_gui',
                    headerText:'Kênh gửi TB',
                    allowFiltering:true
                },
                {
                    fieldName:'loaitin',
                    headerText:'Loại tin',
                    allowFiltering:true
                },
                {
                    fieldName:'thoigian',
                    headerText:'Thời gian',
                    allowFiltering:true
                },
                {
                    fieldName:'ma_tt',
                    headerText:'Mã TT',
                    allowFiltering:true
                },
                {
                    fieldName:'email',
                    headerText:'Số ĐT/Email',
                    allowFiltering:true
                },
                {
                    fieldName:'trangthai',
                    headerText:'Trạng thái',
                    allowFiltering:true
                },
            ],
            dataSources:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='timkiem'){

            }else if(action.id=='xuat_excel'){

            }else if(action.id=='trangthai_sms'){
                this.$refs.chiTietTrangThaiSMSModal.showModal()
            }
        }
    }
}
</script>