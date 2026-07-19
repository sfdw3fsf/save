<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgChiTietNoTB"
        :header='"Chi tiết nợ thông tin nợ thuê bao"'
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
                            <a href="javascript:void(0)" @click="close" title="Thoát">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>                        
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="grid-stack-box">
                            <ejs-splitter id='nested-splitter' width='100%' height='100%' :created="onCreate">
                                <e-panes>
                                    <e-pane content ='#leftPane' size ='70%'></e-pane>
                                    <e-pane content ='#rightPane' size ='30%'></e-pane>
                                </e-panes>
                            </ejs-splitter>
                            <div id="leftPane">
                                <DataGrid v-bind:columns="[
                                    {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-left'},
                                    {fieldName: 'TEN_DVVT', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false, width: 120},
                                    {fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'trangthai-col', format: 'N0', type: 'number'},                            
                                    {fieldName: 'NGAYKHOA', headerText: 'Ngày khóa', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-right'}
                                ]"
                                    v-bind:dataSource="thongTinNo_grid.items"                                        
                                    :showFilter="true"                    
                                    :allowPaging="true"
                                    :enablePagingServer="false"
                                    :aggregates="TTAggregatesColumns"
                                    @selectedRowChanged="onThongTinNoSelected"
                                    ref="thongTinNoGrid"
                                    :key="thongTinNo_grid.key"
                                >
                                </DataGrid>
                            </div>
                            <div id="rightPane">
                                <DataGrid v-bind:columns="[
                                        {fieldName: 'CKN', headerText: 'Tháng nợ', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-right'},                                            
                                        {fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'trangthai-col', format: 'N0', type: 'number'}
                                    ]"
                                    v-bind:dataSource="tongNo_Grid.items"                                        
                                    :showFilter="true"                    
                                    :allowPaging="true"
                                    :enablePagingServer="false"
                                    :aggregates="TNAggregatesColumns"
                                    ref="thangNoGrid"
                                    :key="tongNo_Grid.key"
                                >
                                </DataGrid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>        
    </ejs-dialog>
</template>
<style scoped src="../style.scss"></style>
<script>
import Vue from 'vue'
import API from '../../api/MoMayTheoDSAPI.js'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import {Splitter} from '@syncfusion/ej2-layouts'
Vue.use(SplitterPlugin)
export default {
    name: 'PopupChiTietNoTB',
    components: { },
    props: {
        maTT: {
            type: String,
            default: ''
        },
        kyCuoc: {
            type: String,
            default: '0'
        }
    },
    
    computed: {
        TTAggregatesColumns () {
            return [
                { field: 'MA_TB', type: 'Count', format: 'N0', footerTemplate: this.countTemplate },
                { field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
            ]
        },
        TNAggregatesColumns () { 
            return [{ field: 'CKN', type: 'Count', format: 'N0', footerTemplate: this.countTemplate }]
        },
    },
   
    data () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            params: {
                maTT: '',
                kyCuoc: ''
            },
            thongTinNo_grid: {
                items: [],
                selected: {},
                key: 1
            },
            tongNo_Grid: {
                items: [],
                key: 1
            },
            sumTemplate: function() {
                return { template: Vue.component('sumTemplate', {
                        template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
                    })
                }
            },
            countTemplate: function() {
                return { template: Vue.component('countTemplate', {
                        template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
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
        
        close() {
            this.params.maTT = '';
            this.params.kyCuoc = '';
            this.thongTinNo_grid.items = [];
            this.thongTinNo_grid.selected = {};
            this.tongNo_Grid.items = [];
            this.$refs.dlgChiTietNoTB.hide();
        },

        openDialog () {
          this.$refs.dlgChiTietNoTB.show();
        },

        async dialogOpen() {
            this.params.maTT = this.maTT;
            this.params.kyCuoc = this.kyCuoc;
            await this.getChiTietNoMaTBMaTT();
        },

        async onThongTinNoSelected(e) {
            this.thongTinNo_grid.selected = e;
            await this.getTongNoTTTheoChuKy();
        },
        
        async getChiTietNoMaTBMaTT() {
            try {
                this.loading(true);
                this.thongTinNo_grid.key++;
                this.thongTinNo_grid.items = [];
                let apiParams = {
                    kyCuoc: this.params.kyCuoc,
                    ma: this.params.maTT,
                    kieu: 1
                };
                let response = await API.getChiTietNoMaTBMaTT(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.thongTinNo_grid.items = response.data.data;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                console.log(error);            
            }
            finally {
                this.loading(false);
            }
        },

        async getTongNoTTTheoChuKy() {
            try {
                this.loading(true);
                this.tongNo_Grid.key++;
                this.tongNo_Grid.items = [];
                let apiParams = {
                    pKyCuoc: this.params.kyCuoc,
                    pMaTT: this.params.maTT,
                    pMaTB: this.thongTinNo_grid.selected.MA_TB
                };
                let response = await API.getTongNoTTTheoChuKy(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.tongNo_Grid.items = response.data.data;
                }
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                console.log(error);            
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {        
    }
}
</script>
