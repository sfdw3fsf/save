<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title mart20">
                    <div class="pull-left">Thông tin cập nhật
                        <span class="red" style="margin-left: 20px !important;"> File gồm 2 trường: MA_TT, SO_DT</span>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="info-row">
                    <div class="key w90">Loại YC</div>
                    <div class="value">
                        <div class="select-custom" ref="ds_loai_yc">
                            <select2 :settings="{ dropdownParent: $refs['ds_loai_yc'] }"
                                v-model="loaiyc_selected" class="select2"
                                :options="ds_loai_yc.map(e=> ({id: e.loaiyc_id, text: e.loaiyc_id}))">
                            </select2>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w90">
                        Đường dẫn
                    </div>
                    <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="chooseFile">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <input id="file" ref="fileInput" type="file" @change="onChangeFile($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
                            <input type="text" v-model="file_full_path" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">
                    <div class="pull-left">Danh sách phiếu chờ cập nhật
                        <span class="red" style="margin-left: 20px !important;">Thông báo</span>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="nav tabs tab-over">
                    <a @change="changeTab(1)" href="#tabDs" :class="{active: tab_selected==1}" data-toggle="tab">
                        <span class="icon f20 nc-icon-glyph design_bullet-list-67"></span> Danh sách
                    </a>
                    <a @change="changeTab(1)" href="#tabDsLoi" :class="{active: tab_selected==2}" data-toggle="tab">
                        <span class="icon f20 one-alert text-warning"></span> Lỗi
                    </a>
                </div>
                <div class="tab-content">
                    <div id="tabDs" class="tab-pane" :class="{active: tab_selected==1}" >
                        <KDataGrid
                            :columns="columns"
                            :dataSource="dataSources"
                            :showColumnCheckbox="false"
                            :enable-paging-server="false"
                            :allowPaging="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="false"/>
                    </div>
                    <div id="tabDsLoi" class="tab-pane" :class="{active: tab_selected==2}" >
                        <KDataGrid
                            :columns="columnsError"
                            :dataSource="dataSourcesError"
                            :showColumnCheckbox="false"
                            :enable-paging-server="false"
                            :allowPaging="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="false"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'ThongBaoCuocTheoFile',
    components:{
        breadcrumb,
        ActionTop,
        KDataGrid
    },
    data(){
        return {
            header: {
                title: "Đọc file nhắn tin thông báo cước",
                list: [
                    { 
                        name: "Quản lý đại lý", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Thông báo cước theo file",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            actions:[
                {
                    id:'lay_ds',
                    name:'Lấy DS',
                    active: true,
                    visible:true,
                    icon_class:'design_bullet-list-67 nc-icon-glyph'
                },
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-save',
                    visible:true
                },
                {
                    id:'xuat_excel',
                    name:'Xuất Excel',
                    active: true,
                    icon_class:'one-excel',
                    visible:true
                },
            ],
            ds_loai_yc:[],
            loaiyc_selected:null,
            tab_selected:1,
            columns:[
                {
                    fieldName:'MA_TT',
                    headerText:'MA_TT',
                    allowFiltering:true
                },
                {
                    fieldName:'SO_DT',
                    headerText:'SO_DT',
                    allowFiltering:true
                },
            ],
            columnsError:[
                {
                    fieldName:'MA_TT',
                    headerText:'MA_TT',
                    allowFiltering:true
                },
                {
                    fieldName:'SO_DT',
                    headerText:'SO_DT',
                    allowFiltering:true
                },
                {
                    fieldName:'ERROR',
                    headerText:'Lỗi',
                    allowFiltering:true
                },
            ],
            dataSources:[],
            dataSourcesError:[],
            file_full_path:'',
            file:null

        }
    },
    methods:{
        onActionClick(action){

        },
        changeTab(value){
            this.tab_selected=value
        },
        onChangeFile(event){
            this.file_full_path = event.target.value.split('\\').pop()
            this.file=event.target.files[0]
            //this.readFileExcel()
        },
        chooseFile(){
            this.$refs.fileInput.value=null
            document.getElementById("file").click()
        },
        async initDuLieu(){
            
        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>