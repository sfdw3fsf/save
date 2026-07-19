<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgChonNhanVienXacNhan"
        :header='headerTemplate'        
        :animationSettings='animationSettings'
        showCloseIcon=true width='80%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
            <div class="">                
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="javascript: void(0)" @click="onAccept" title="Chấp nhận">
                                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="close" title="Thoát">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="row">
                        <ejs-splitter id='nested-splitter' width='100%' height='100%' :created="onCreate">
                            <e-panes>
                                <e-pane content ='#leftPane' size ='30%'></e-pane>
                                <e-pane content ='#rightPane' size ='70%'></e-pane>
                            </e-panes>
                        </ejs-splitter>

                        <div id="leftPane">
                            <div class="box-form">
                                <div class="legend-title" style="font-weight: 600!important">Danh sách đơn vị</div>
                                <div class="tree-plus" style="height: 453px; overflow:auto;">                                    
                                    <ejs-treeview 
                                        id='treeview' 
                                        :fields="treeViewData"                                            
                                        :nodeSelected="nodeSelected"
                                    >
                                    </ejs-treeview>
                                </div>
                            </div>
                        </div>

                        <div id="rightPane">
                            <div class="box-form">
                                <div class="legend-title" style="font-weight: 600!important">Danh sách nhân viên</div>
                                    <DataGrid v-bind:columns="[
                                        {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 60, cssClass: 'text-right', type: 'number'},
                                        {fieldName: 'MA_NV', headerText: 'Mã nhân viên', allowFiltering: true, allowSorting: false, width: 120},
                                        {fieldName: 'TEN_NV', headerText: 'Tên nhân viên', allowFiltering: true, allowSorting: false, width: 150},
                                        {fieldName: 'CHUCDANH', headerText: 'Chức danh', allowFiltering: true, allowSorting: false, width: 120},                                                
                                        {fieldName: 'VITRI', headerText: 'Vị trí', allowFiltering: true, allowSorting: false, width: 120},
                                        {fieldName: 'SO_DT', headerText: 'Điện thoại', allowFiltering: true, allowSorting: false, width: 120},
                                        {fieldName: 'DIACHI_NV', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false, width: 150}
                                    ]"
                                    v-bind:dataSource="nhanVien_Items"
                                    dataKeyField="MA_NV"
                                    :showFilter="true"
                                    :allowPaging="true"
                                    :enablePagingServer="false"
                                    ref="nhanVienGrid"
                                    @selectedRowChanged="selectedItemsChanged"
                                >
                                </DataGrid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<script>
import API from '../../api/CongNoAPI.js'
import Vue from 'vue';
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import {Splitter} from '@syncfusion/ej2-layouts'
Vue.use(SplitterPlugin)
Vue.use(TreeViewPlugin)

export default {
    components: {},
    name: "PopupChonNhanVienXacNhan",

    props: {
        // items: []
    },    

    computed: {      
        treeViewData () {            
            let donViCha = [...new Set(this.donViXNN_Items.map(e => e.DONVI_CHA_ID))];
            this.donViXNN_Items.forEach(element => {
                element.HASCHILD = false;
                if (element.UNITLEVEL === 0)
                    delete element.DONVI_CHA_ID;
                if (donViCha.includes(element.DONVI_ID))
                    element.HASCHILD = true;
            });
            return {
                dataSource: this.donViXNN_Items,
                id: 'DONVI_ID', 
                parentID: 'DONVI_CHA_ID', 
                text: 'TEN_DV', 
                hasChildren: 'HASCHILD'
            };
        }
    },

    data() {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            splitterObj : null,
            donViXNN_Items: [],
            nhanVien_Items: [],
            nhanVien_Selected: {},
            headerTemplate: function () {
                return  { template : Vue.component('headerTp', {
                        template: '<div class="title" style="font-size: 20px;"><span class="icon one-notepad"></span> Chọn nhân viên xử lý</div>',
                        data () { return { data: {data: {}}}; }
                    })
                }
            }
        }
    },

    methods: {
        onCreate(e) {
            this.splitterObj = new Splitter({
                paneSettings: [
                    { size: '50%', min: '20%' },
                    { size: '50%', min: '20%'}
                ],
                orientation: 'Vertical'
            });
            this.splitterObj.appendTo('#vertical_splitter');
        },

        async dialogOpen() {
            await this.getDanhSachDonViXacNhanNo();
        },

        dialogClose() {

        },

        openDialog () {
            this.$refs.dlgChonNhanVienXacNhan.show();
        },

        close() {            
            this.nhanVien_Items = [];
            this.nhanVien_Selected = {};
            this.donViXNN_Items = [];
            this.$refs.dlgChonNhanVienXacNhan.hide();
        },

        async nodeSelected(arg) {
            var treeObj = document.getElementById('treeview').ej2_instances[0];
            await this.getDanhSachNhanVienHRMTheoDonVi(treeObj.selectedNodes[0]);
        },

        selectedItemsChanged(e) {
            if (e !== null)
                this.nhanVien_Selected = e;
        },

        onAccept() {
            if (Object.keys(this.nhanVien_Selected).length > 0) {
                this.$emit('acceptChange', this.nhanVien_Selected);
                this.close();
            } else 
                this.$toast.error('Vui lòng chọn nhân viên xử lý');
        },

        async getDanhSachDonViXacNhanNo() {
            try {
                this.loading(true);
                this.donViXNN_Items = [];
                let response = await API.getDanhSachDonViXacNhanNo(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.donViXNN_Items = response.data.data;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhSachNhanVienHRMTheoDonVi(id) {
            try {
                this.loading(true);
                this.nhanVien_Items = [];
                let response = await API.getDanhSachNhanVienHRMTheoDonVi(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.nhanVien_Items = response.data.data;                    
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        }
    }
}
</script>
