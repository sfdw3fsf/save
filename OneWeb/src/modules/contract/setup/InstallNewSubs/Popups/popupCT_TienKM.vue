<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>{{tieude}}</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="closeForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
            <li :class="{hidden:!btnThoat}">
                <a href="#" @click.prevent="terminateForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">{{tieudebang}}</div>
                    <ejs-treegrid ref="grid" id="treegrid" :dataSource='dulieutree' :editSettings="{ allowEditing: true, mode: 'Normal' }" :treeColumnIndex='1' childMapping='subitem' :allowFiltering='true' :actionComplete='actionComplete' :actionBegin="actionBegin" :cellSave="cellSave" :rowDataBound='rowDataBound'>
                        <e-columns>
                            <e-column field='khoanmuctt_id' :allowEditing="false" headerText='ID' :visible="false" textAlign='Left' width=200></e-column>
                            <e-column field='tentat_kmtt' :allowEditing="false" headerText='Khoản mục' textAlign='Left' width=200></e-column>
                            <!-- <e-column field='tongtien' :allowEditing="true" :editType="'numericedit'" :format="'N'" :customAttributes="{class:'tongtien'}" headerText='Tổng tiền' textAlign='Right' :edit="tongtienParam"></e-column>
                            <e-column field='tien' :allowEditing="true" :editType="'numericedit'" :format="'N'" :customAttributes="{class:'cuoclapdat'}" headerText='Tiền' textAlign='Right' :edit="tienParam"></e-column> -->
                            <!-- <e-column field='tongtien' :allowEditing="true" :editType="'numericedit'" :edit="{ params: { validateDecimalOnType: true, decimals: 6, format: 'N' } }" :format="'N'" :customAttributes="{class:'tongtien'}" headerText='Tổng tiền' textAlign='Right'></e-column> -->
                            <e-column field='tongtien' :allowEditing="true" :editType="'numericedit'" :format="is_nv_vnpti==1?'N2':'N'" :customAttributes="{class:'tongtien'}" headerText='Tổng tiền' textAlign='Right'></e-column>
                            <e-column field='tien' :allowEditing="true" :editType="'numericedit'" :format="is_nv_vnpti==1?'N2':'N'" :customAttributes="{class:'cuoclapdat'}" headerText='Tiền' textAlign='Right'></e-column>
                            <e-column field='vat' :allowEditing="false" :format="is_nv_vnpti==1?'N2':'N'" :customAttributes="{class:'vatlapdat'}" headerText='Vat' textAlign='Right'></e-column>
                            <e-column field='tyle_vat' :allowEditing="false" headerText='Tỷ lệ Vat (%)' textAlign='Right'></e-column>
                        </e-columns>
                        <e-aggregates>
                            <e-aggregate :showChildSummary='false'>
                                <e-columns>
                                    <e-column columnName="tentat_kmtt" type="Custom" :customAggregate="customTongTien" textAlign='Center' :footerTemplate='footerTong'></e-column>
                                    <e-column columnName="tongtien" type="Custom" format="C2"    :customAggregate="customTongTien" :footerTemplate='footerTongTien'></e-column>
                                    <e-column columnName="tien" type="Custom" format="C2" :customAggregate="customTien" :footerTemplate='footerTien'></e-column>
                                    <e-column columnName="vat" type="Custom" format="C2" :customAggregate="customVat" :footerTemplate='footerVat'></e-column>
                                    <!-- <e-column type="Sum" field="tongtien" :footerTemplate='footerSum'></e-column>
                                    <e-column type="Sum" field="tien" :footerTemplate='footerSum'></e-column>
                                    <e-column type="Sum" field="vat" :footerTemplate='footerSum'></e-column> -->
                                </e-columns>
                            </e-aggregate>
                        </e-aggregates>
                    </ejs-treegrid>
                    <!-- <div class="legend-title mart20">{{tieudebang}}</div>
                    <ejs-grid ref="grid" :dataSource="ds.list" locale="vi-VN" :allowSorting='true' :allowFiltering='true'
                        :allowPaging='true' :pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' :gridLines='1' :editSettings="{ allowEditing: true, mode: 'Normal' }" height="200">
                        <e-columns>
                            <e-column v-for="col in ds.cols" :key="col.fieldName" :field='col.fieldName' :allowEditing="col.allowEditing" :customAttributes="col.customAttributes" :headerText='col.headerText' :textAlign="col.textAlign" headerTextAlign="center" :editType="col.editType"  :format='col.format' :isPrimaryKey="col.isPrimaryKey"></e-column>
                        </e-columns>
                    </ejs-grid> -->
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>
    /deep/ .e-grid .e-rowcell.cuoclapdat,/deep/ .e-grid .cuoclapdat { font-weight: bold; color:blue; }
    /deep/ .e-grid .e-rowcell.vatlapdat,/deep/ .e-grid .vatlapdat { font-weight: bold; color:cadetblue; }
    /deep/ .e-grid .e-rowcell.tongtien,/deep/ .e-grid .tongtien { font-weight: bold; color:red; }
    /deep/ .e-grid .e-altrow.required,/deep/ .e-grid .required { background-color:#FFF9EB !important; }
</style>
<script>
import Vue from "vue";
import {
  Sort,
  Filter,
  Resize,
  Group,
  Freeze,
  CommandColumn,
  ExcelExport,
  Edit,
  Aggregate,
  Toolbar,
} from "@syncfusion/ej2-vue-grids";
import { TreeGridPlugin, Filter as TreeFilter, Edit as TreeEdit, Aggregate as TreeAggregate } from "@syncfusion/ej2-vue-treegrid";
import { NumericTextBox } from "@syncfusion/ej2-inputs";
let tongtienElem, tongtienObj, tienElem, tienObj;
export default {
    name: 'popupCT_TienKM',
    provide: {
        grid: [
            Sort,
            Filter,
            Resize,
            Group,
            Freeze,
            CommandColumn,
            ExcelExport,
            Edit,
            Aggregate,
            Toolbar,
        ],
        treegrid: [ TreeFilter,TreeEdit,TreeAggregate ],
    },
    props: [ 'dulieu' ],
    watch: {
        'dulieugrid': {
            handler: function(val,oldval) {
                if(!oldval || oldval.length<=0) return;
                for(var i=0;i<val.length;i++) {
                    var tyle_vat = 10;
                    if(val[i].tyle_vat || val[i].tyle_vat==0) tyle_vat = val[i].tyle_vat*1;
                    if(val[i].tongtien!=oldval[i].tongtien && val[i].vat==oldval[i].vat) {
                        val[i].vat = val[i].tongtien*tyle_vat/(100+tyle_vat);
                        val[i].tien = val[i].tongtien*1 - val[i].vat*1;
                        this.ds.list[i].vat = val[i].vat*1;
                        this.ds.list[i].tien = val[i].tien*1;
                        this.$forceUpdate();
                    } else if (val[i].tien!=oldval[i].tien && val[i].vat==oldval[i].vat) {
                        val[i].vat = val[i].tien*tyle_vat/100;
                        val[i].tongtien = val[i].tien*1+val[i].vat*1;
                        this.ds.list[i].vat = val[i].vat*1;
                        this.ds.list[i].tongtien = val[i].tongtien*1;
                        this.$forceUpdate();
                    }
                }
            },
            deep:true,
        },
        'data_set_km.KMGOC': {
            handler: function(val,oldval) {
                this.xlyThayDoiGrid(val,oldval);
            },
            deep:true,
        },
        'data_set_km.KM_CT': {
            handler: function(val,oldval) {
                this.xlyThayDoiGrid(val,oldval);
            },
            deep:true,
        },
        'data_set_km.TienKMTT': {
            handler: function(val,oldval) {
                this.xlyThayDoiGrid(val,oldval);
            },
            deep:true,
        },
        'dulieutree': {
            handler: function(oldval,val) {
                // console.log('dulieutree',val,oldval,this.checkdl);
                if(!oldval || oldval.length<=0) return;
                for(var i=0;i<val.length;i++) {
                    var tyle_vat = 10;
                    if(val[i].tyle_vat || val[i].tyle_vat==0) tyle_vat = val[i].tyle_vat*1;
                    // console.log('đk1',val[i].tongtien!=oldval[i].tongtien,val[i].tongtien,oldval[i].tongtien,'đk2',val[i].tien!=oldval[i].tien);
                    if(val[i].tongtien!=oldval[i].tongtien) { // && val[i].vat==oldval[i].vat
                        // console.log('tinh tong tien');
                        val[i].vat = val[i].tongtien*tyle_vat/(100+tyle_vat*1);
                        val[i].tien = val[i].tongtien*1 - val[i].vat*1;
                        // this.ds.list[i].vat = val[i].vat;
                        // this.ds.list[i].tien = val[i].tien;
                    } else if (val[i].tien!=oldval[i].tien) { // && val[i].vat==oldval[i].vat
                        // console.log('tinh tien');
                        val[i].vat = val[i].tien*tyle_vat/100;
                        val[i].tongtien = val[i].tien*1+val[i].vat*1;
                        // this.ds.list[i].vat = val[i].vat;
                        // this.ds.list[i].tongtien = val[i].tongtien;
                    }
                    if(val[i].subitem)
                        for(var j = 0; j<val[i].subitem.length;j++) {
                            tyle_vat = 10;
                            if(val[i].subitem[j].tyle_vat || val[i].subitem[j].tyle_vat==0) tyle_vat = val[i].subitem[j].tyle_vat;
                            if(val[i].subitem[j].tongtien!=oldval[i].subitem[j].tongtien && val[i].subitem[j].vat==oldval[i].subitem[j].vat) {
                                val[i].subitem[j].vat = val[i].subitem[j].tongtien*tyle_vat/(100+tyle_vat);
                                val[i].subitem[j].tien = val[i].subitem[j].tongtien*1 - val[i].subitem[j].vat*1;
                                // this.ds.list[i].vat = val[i].vat;
                                // this.ds.list[i].tien = val[i].tien;
                            } else if (val[i].subitem[j].tien!=oldval[i].subitem[j].tien && val[i].subitem[j].vat==oldval[i].subitem[j].vat) {
                                val[i].subitem[j].vat = val[i].subitem[j].tien*tyle_vat/100;
                                val[i].subitem[j].tongtien = val[i].subitem[j].tien*1+val[i].subitem[j].vat*1;
                                // this.ds.list[i].vat = val[i].vat;
                                // this.ds.list[i].tongtien = val[i].tongtien;
                            }
                        }
                }
                // console.log('dulieutree 2',val,oldval,this.checkdl,this.grcDanhSach.DataSource);
                // console.log('KMGOC',this.data_set_km.KMGOC,'KM_CT',this.data_set_km.KM_CT,'TienKMTT',this.data_set_km.TienKMTT);
                this.$forceUpdate();
            },
            deep:true,
        }
    },
    data() {
        return {
            footerTong: function () {
                return  { template : Vue.component('footerTemplate', {
                    template: `<span class='tong'>TỔNG</span>`,
                    data () {return { data: {}};}
                    })
                }
            },
            footerTongTien: function () {
                return  { template : Vue.component('footerTemplate', {
                    template: `<span class='tongtien'>{{data.Custom.toLocaleString('vi-VN')}}</span>`,
                    data () {return { data: {}};}
                    })
                }
            },
            footerTien: function () {
                return  { template : Vue.component('footerTemplate', {
                    template: `<span class='tien'>{{data.Custom.toLocaleString('vi-VN')}}</span>`,
                    data () {return { data: {}};}
                    })
                }
            },
            footerVat: function () {
                return  { template : Vue.component('footerTemplate', {
                    template: `<span class='vat'>{{data.Custom.toLocaleString('vi-VN')}}</span>`,
                    data () {return { data: {}};}
                    })
                }
            },
            cuoc_sodep: -1,
            checkdl: 0,
            data_set_km: {},
            m_dsTienCTKM: [],
            grcDanhSach: {
                DataSource: [],
            },
            grvKMTT_CT: {
                DataSource: [],
            },
            ds_tien_caidat: [],
            ds: {
                list: [],
                cols: [],
                keycol: "",
                value: {},
            },
            btnThoat: false,
            tieude: "popup",
            tieudebang: "datapopup",
            is_nv_vnpti: 0,
            
        }
    },
    async mounted() {
        this.ds.list = this.dulieu.ds;
        if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        this.ds.cols = this.dulieu.cols;
        this.ds.keycol = this.dulieu.keycol;
        if(this.dulieu.allowExit) this.btnThoat = this.dulieu.allowExit;
        if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        if(this.dulieu.tieude) this.tieude = this.dulieu.tieude;
        if(this.dulieu.tieudebang) this.tieudebang = this.dulieu.tieudebang;
        // Them tu day!
        if(this.dulieu.ds_tien_caidat) this.ds_tien_caidat = this.dulieu.ds_tien_caidat;
        if(this.dulieu.m_dsTienCTKM) this.m_dsTienCTKM = this.dulieu.m_dsTienCTKM;
        if(this.dulieu.cuoc_sodep) this.cuoc_sodep = this.dulieu.cuoc_sodep;
        if(this.dulieu.is_nv_vnpti) this.is_nv_vnpti = this.dulieu.is_nv_vnpti;
        await this.NAP_DS_KMTT();
        this.HT_TIEN_KMTT();
        this.checkdl++;
    },
    computed: {
        dulieugrid() { return JSON.parse(JSON.stringify(this.ds.list)); },
        dulieutree: { 
            get: function () {
                // console.log('get dulieutree',this.checkdl);
                var i = this.checkdl;
                var len = this.data_set_km?Object.keys(this.data_set_km).length:0;
                if(len<=0) return [];
                var ds = this.data_set_km.KMGOC?this.data_set_km.KMGOC:(this.data_set_km.TienKMTT?this.data_set_km.TienKMTT:[]);
                if(ds && ds.length>0) {
                    ds = [...ds];
                    if(this.data_set_km.KM_CT)
                    for(var item of ds) {
                        item.subitem = this.data_set_km.KM_CT.filter(x=>x.khoanmuctt_cha_id==item.khoanmuctt_id);
                        for(var sitem of item.subitem) {
                            sitem.tentat_kmtt = sitem.tentat_kmtt;
                        }
                    }
                }
                this.grcDanhSach.DataSource = JSON.parse(JSON.stringify(ds));
                return this.grcDanhSach.DataSource;
            },
            // set: function (val) {
            //     console.log('set dulieutree',val,this.checkdl);
            // }
        },
    },
    methods: {
        customTongTien: function(data) {
            var tongtien = 0;
            for(var item of data.result) if(!item.khoanmuctt_cha_id) tongtien += item.tongtien*1 && !isNaN(item.tongtien*1)?item.tongtien*1:0;
            return tongtien
        },
        customTien: function(data) {
            var tongtien = 0;
            for(var item of data.result) if(!item.khoanmuctt_cha_id) tongtien += item.tien*1 && !isNaN(item.tien*1)?item.tien*1:0;
            return tongtien
        },
        customVat: function(data) {
            var tongtien = 0;
            for(var item of data.result) if(!item.khoanmuctt_cha_id) tongtien += item.vat*1 && !isNaN(item.vat*1)?item.vat*1:0;
            return tongtien
        },
        rowDataBound: function(args) {
            if (args.data['flag'] == 1) {
                args.row.classList.add('required');
            // } else if (args.data['Freight'] < 80) {
            //     args.row.classList.add('below-80');
            // } else {
            //     args.row.classList.add('above-80');
            }
        },
        actionBegin(args) {
            if(args.requestType=='beginEdit' && args.rowData.flag==0) {
                args.cancel = true;
                return;
            }
            console.info(args.requestType,args);
        },
        cellSave(args) {
            console.info("Cell save",args);
        },
        actionComplete(args) {

            // if (args.requestType === 'beginEdit') {
            //     const editForm = args.form;
            //     // Detect the user's current language (locale) using navigator.language
            //     const userLocale = navigator.language || navigator.userLanguage;

            //     // Format a number with the user's locale
            //     const formattedNumber = (1/2).toLocaleString(userLocale);

            //     // Extract the decimal separator character
            //     const decimalSeparator = formattedNumber.replace(/\d+/g, '');
            //     // Lặp qua các trường trong form chỉnh sửa
            //     for (let i = 0; i < editForm.elements.length; i++) {
            //         const field = editForm.elements[i];
                    
            //         if (field.type === 'text' && field.name) {
            //         const columnName = field.name;
            //         // continue;
            //         const column = this.$refs.grid.ej2Instances.columnModel.find(x => x.field === columnName);
                    
            //             if (column && column.type=="number") {
            //                 // Lấy giá trị số gốc
            //                 const originalValue = args.rowData[columnName];
                            
            //                 // Định dạng lại giá trị số và đặt lại nó vào trường chỉnh sửa
            //                 field.value = new Intl.NumberFormat(userLocale,{
            //                             minimumFractionDigits: 0,  // Minimum number of decimal places
            //                             maximumFractionDigits: 10,  // Maximum number of decimal places
            //                         }).format(originalValue);

            //                 field.oninput = (c)=> {
            //                     if(field.value[field.value.length-1]!=decimalSeparator) {
            //                         const regexPattern = new RegExp(`[^\\d\\${decimalSeparator}]`, 'g');
            //                         console.info('before: ',decimalSeparator,field.value,field.value.replace(regexPattern, ''));
            //                         field.value = new Intl.NumberFormat(userLocale,{
            //                             minimumFractionDigits: 0,  // Minimum number of decimal places
            //                             maximumFractionDigits: 10,  // Maximum number of decimal places
            //                         }).format(field.value.replaceAll(regexPattern, ''));
            //                         console.info('after: ',field.value);
            //                     }
            //                 };
            //                 field.onchange = (c) => {
            //                     console.info('change:',c,field.value);
            //                 }

            //                 field.onblur = (c) => {
            //                     console.info('blur:',c,field.value);
            //                 }
            //             }
            //         }
            //     }
            // }
            // else if (args.requestType === 'endEdit') {
            //     console.info('endEdit',args);
            // }
            // console.info(args.requestType);
            if(args.requestType=='save') {
                console.info('save',args.data,args.rowData);
                this.xlyThayDoiRow(args.data,args.rowData);
                if(this.data_set_km.TienKMTT) {
                    this.data_set_km.TienKMTT[args.rowIndex]['tien'] = args.data['tien'];
                    this.data_set_km.TienKMTT[args.rowIndex]['tongtien'] = args.data['tongtien'];
                    this.data_set_km.TienKMTT[args.rowIndex]['vat'] = args.data['vat'];
                }
                else { 
                    // console.log(args,args.data,args.data['khoanmuctt_id'])
                    for(var item of this.data_set_km.KM_CT)
                        if(item['khoanmuctt_id'] == args.data['khoanmuctt_id']) {
                            item['tien'] = args.data['tien']*1;
                            item['tongtien'] = args.data['tongtien']*1;
                            item['vat'] = args.data['vat']*1;
                        }
                    for(var item of this.data_set_km.KMGOC) {
                        var subitems = this.data_set_km.KM_CT.filter(x=>x.khoanmuctt_cha_id==item['khoanmuctt_id']);
                        if(subitems && subitems.length>0) {
                            item['tien'] = 0;
                            item['tongtien'] = 0;
                            item['vat'] = 0;
                            for(var i=0; i<subitems.length; i++) {
                                item['tien'] += subitems[i].tien?subitems[i].tien*1:0;
                                item['tongtien'] += subitems[i].tongtien?subitems[i].tongtien*1:0;
                                item['vat'] += subitems[i].vat?subitems[i].vat*1:0;
                            }
                        }
                        else if(item['khoanmuctt_id'] == args.data['khoanmuctt_id']) {
                            item['tien'] = args.data['tien']*1;
                            item['tongtien'] = args.data['tongtien']*1;
                            item['vat'] = args.data['vat']*1;
                        }
                    }
                }
                this.checkdl++;
                // console.log('KMGOC',this.data_set_km.KMGOC,'KM_CT',this.data_set_km.KM_CT,'TienKMTT',this.data_set_km.TienKMTT);
                this.$forceUpdate();
            }
        },
        round(num) {
            if(this.is_nv_vnpti==1)
                return +(Math.round(num + "e+2")  + "e-2");
            else return Math.round(num);
        },
        xlyThayDoiRow(val,oldval) {
            var tyle_vat = 10;
            if(val.tyle_vat || val.tyle_vat==0) tyle_vat = val.tyle_vat*1;
            if(val.tongtien!=oldval.tongtien) { // && val.vat==oldval.vat
                val.vat = this.round(val.tongtien*tyle_vat/(100+tyle_vat));
                val.tien = val.tongtien*1 - val.vat*1;
                // this.ds.list[i].vat = val.vat;
                // this.ds.list[i].tien = val.tien;
            } else if (val.tien!=oldval.tien) { // && val.vat==oldval.vat
                val.vat = this.round(val.tien*tyle_vat/100);
                val.tongtien = val.tien*1+val.vat*1;
                // this.ds.list[i].vat = val.vat;
                // this.ds.list[i].tongtien = val.tongtien;
            }
            if(val.subitem && val.subitem.length>0) {
                // var totalTongTien = 0;
                // var totalTien = 0;
                // var totalVat = 0;
                for(var j = 0; j<val.subitem.length;j++) {
                    tyle_vat = 10;
                    if(val.subitem[j].tyle_vat || val.subitem[j].tyle_vat==0) tyle_vat = val.subitem[j].tyle_vat;
                    if(val.subitem[j].tongtien!=oldval.subitem[j].tongtien && val.subitem[j].vat==oldval.subitem[j].vat) {
                        val.subitem[j].vat = this.round(val.subitem[j].tongtien*tyle_vat/(100+tyle_vat));
                        val.subitem[j].tien = val.subitem[j].tongtien*1 - val.subitem[j].vat*1;
                        // this.ds.list[i].vat = val.vat;
                        // this.ds.list[i].tien = val.tien;
                    } else if (val.subitem[j].tien!=oldval.subitem[j].tien && val.subitem[j].vat==oldval.subitem[j].vat) {
                        val.subitem[j].vat = this.round(val.subitem[j].tien*tyle_vat/100);
                        val.subitem[j].tongtien = val.subitem[j].tien*1+val.subitem[j].vat*1;
                        // this.ds.list[i].vat = val.vat;
                        // this.ds.list[i].tongtien = val.tongtien;
                    }
                    // totalTongTien += val.subitem[j].tongtien?val.subitem[j].tongtien*1:0;
                    // totalTien += val.subitem[j].tien?val.subitem[j].tien*1:0;
                    // totalVat += val.subitem[j].vat?val.subitem[j].vat*1:0;
                }
                // val.tongtien = totalTongTien;
                // val.tien = totalTien;
                // val.vat = totalVat;
            }
        },
        // xlyThayDoiGrid(val,oldval) {
        //     console.log('dulieutree',val,oldval,this.checkdl);
        //     if(!oldval || oldval.length<=0) return;
        //     for(var i=0;i<val.length;i++) {
        //         var tyle_vat = 10;
        //         if(val[i].tyle_vat) tyle_vat = val[i].tyle_vat;
        //         console.log('đk1',val[i].tongtien!=oldval[i].tongtien,val[i].tongtien,oldval[i].tongtien,'đk2',val[i].tien!=oldval[i].tien);
        //         if(val[i].tongtien!=oldval[i].tongtien) { // && val[i].vat==oldval[i].vat
        //             console.log('tinh tong tien');
        //             val[i].vat = val[i].tongtien*tyle_vat/(100+tyle_vat);
        //             val[i].tien = val[i].tongtien - val[i].vat;
        //             // this.ds.list[i].vat = val[i].vat;
        //             // this.ds.list[i].tien = val[i].tien;
        //         } else if (val[i].tien!=oldval[i].tien) { // && val[i].vat==oldval[i].vat
        //             console.log('tinh tien');
        //             val[i].vat = val[i].tien*tyle_vat/100;
        //             val[i].tongtien = val[i].tien+val[i].vat;
        //             // this.ds.list[i].vat = val[i].vat;
        //             // this.ds.list[i].tongtien = val[i].tongtien;
        //         }
        //         if(val[i].subitem)
        //             for(var j = 0; j<val[i].subitem.length;j++) {
        //                 tyle_vat = 10;
        //                 if(val[i].subitem[j].tyle_vat) tyle_vat = val[i].subitem[j].tyle_vat;
        //                 if(val[i].subitem[j].tongtien!=oldval[i].subitem[j].tongtien && val[i].subitem[j].vat==oldval[i].subitem[j].vat) {
        //                     val[i].subitem[j].vat = val[i].subitem[j].tongtien*tyle_vat/(100+tyle_vat);
        //                     val[i].subitem[j].tien = val[i].subitem[j].tongtien - val[i].subitem[j].vat;
        //                     // this.ds.list[i].vat = val[i].vat;
        //                     // this.ds.list[i].tien = val[i].tien;
        //                 } else if (val[i].subitem[j].tien!=oldval[i].subitem[j].tien && val[i].subitem[j].vat==oldval[i].subitem[j].vat) {
        //                     val[i].subitem[j].vat = val[i].subitem[j].tien*tyle_vat/100;
        //                     val[i].subitem[j].tongtien = val[i].subitem[j].tien+val[i].subitem[j].vat;
        //                     // this.ds.list[i].vat = val[i].vat;
        //                     // this.ds.list[i].tongtien = val[i].tongtien;
        //                 }
        //             }
        //     }
        //     console.log('dulieutree 2',val,oldval,this.checkdl);
        //     this.$forceUpdate();
        // },
        closeForm() {
            document.getElementById("treegrid").ej2_instances[0].vueInstance.endEdit()
            var ds = [];
            if(this.data_set_km.TienKMTT)
                ds = this.data_set_km.TienKMTT;
            else ds = this.data_set_km.KMGOC.concat(this.data_set_km.KM_CT?this.data_set_km.KM_CT.filter(x=>x.khoanmuctt_id*1>=0):[]);
            this.$emit('form-close',ds); 
            // console.log('KMGOC',this.data_set_km.KMGOC,'KM_CT',this.data_set_km.KM_CT,'TienKMTT',this.data_set_km.TienKMTT);
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        rowChanged(item) {
            this.ds.value = item;
        },
        dataSourceChanged(state) {
            if (state.action === 'edit') state.endEdit();
        },
        async NAP_DS_KMTT() {
            this.grcDanhSach = [];
            var rs = await this.$root.context.post('web-hopdong/lapdatmoi/sp_lay_ds_tien_kmtt',{
                "vloaihd_id":this.dulieu.vloaihd_id,
                "vkieuld_id":this.dulieu.vkieuld_id,
                "vloaitb_id":this.dulieu.vloaitb_id,
            });
            var ds = [];
            if(rs && rs.data) ds = rs.data;
            if(ds && ds.length>0) {
                if(this.m_dsTienCTKM && this.m_dsTienCTKM.length>0) {
                    for(var dr of this.m_dsTienCTKM) {
                        if(dr['kieu']==1) {
                            var kt = ds.filter(x=>x.khoanmuctt_id==dr['khoanmuctt_id']);
                            if(kt && kt.length>0) {
                                ds.push({
                                    "khoanmuctt_id": "-"+dr["khoanmuctt_id"],
                                    "tentat_kmtt": dr["ten_ctkm"],
                                    "tongtien": dr['tien']*1+dr['vat']*1,
                                    "tien": dr['tien']*1,
                                    "vat": dr['vat']*1,
                                    "flag": 0,
                                    "tyle_vat": dr['tyle_vat']*1,
                                    "khoanmuctt_cha_id": dr['khoanmuctt_id'],
                                    "danhdau": 0,
                                    "id": dr['khoanmuctt_id'],
                                });
                            }
                        }
                    }
                }
                var dt_kmt = ds.filter(x=>x.khoanmuctt_cha_id==null);
                var dt_kmt_ct = ds.filter(x=>x.khoanmuctt_cha_id!=null);
                // console.log(ds,dt_kmt,dt_kmt_ct);
                if (dt_kmt && dt_kmt_ct && dt_kmt.length > 0 && dt_kmt_ct.length > 0)
                {
                    this.data_set_km.KMGOC = dt_kmt;
                    this.data_set_km.KM_CT = dt_kmt_ct;
                    this.grcDanhSach.DataSource = this.data_set_km.KMGOC;
                    // console.log(this.data_set_km);
                    // grcDanhSach.LevelTree.Nodes.Add("Khoản mục thanh toán", grvKMTT_CT);
                    // var dt_kmt_goc = dt_kmt.CopyToDataTable();
                    // dt_kmt_goc.TableName = "KMGOC";
                    // var dt_kmt_ct_ = dt_kmt_ct.CopyToDataTable();
                    // dt_kmt_ct_.TableName = "KM_CT";
                    // data_set_km.Tables.Add(dt_kmt_goc);
                    // data_set_km.Tables.Add(dt_kmt_ct_);
                    // data_set_km.Relations.Add("Khoản mục thanh toán", data_set_km.Tables["KMGOC"].Columns["ID"], data_set_km.Tables["KM_CT"].Columns["ID"]);
                    // grcDanhSach.DataSource = data_set_km.Tables["KMGOC"];
                    /* for (int i = 0; i < grvDanhSach.RowCount; i++)
                    {
                        int rowHandle = grvDanhSach.GetRowHandle(i);
                        grvDanhSach.ExpandMasterRow(rowHandle);
                    }  */
            
                }
                else
                {
                    this.data_set_km.TienKMTT = ds;
                    this.grcDanhSach.DataSource = ds;
                }

            }
        },
        HT_TIEN_KMTT() {
            try
            {
                // grvDanhSach.CellValueChanged -= grvDanhSach_CellValueChanged;
                var ds = this.grcDanhSach.DataSource;// as DataTable;
                if (!ds) return;
                // ds.AcceptChanges();
                for (var dr of ds)
                {
                    // DataRow dr = ds.Rows[i];
                    for (var j = 0; j < this.ds_tien_caidat.length; j++)
                    {
                        if (dr["khoanmuctt_id"] == this.ds_tien_caidat[j]["khoanmuctt_id"])
                        {
                            dr["tien"] = Math.abs(this.ds_tien_caidat[j]["tien"]*1);
                            dr["vat"] = Math.abs(this.ds_tien_caidat[j]["vat"]*1);
                            dr["tongtien"] = Math.abs(this.ds_tien_caidat[j]["tien"]*1) + Math.abs(this.ds_tien_caidat[j]["vat"]*1);
                        }
                    }
                }
                // grvDanhSach.CellValueChanged += grvDanhSach_CellValueChanged;
               //Nếu có nhiều dataset
                if (this.data_set_km && Object.keys(this.data_set_km).length>1)
                {
                    // grvKMTT_CT.CellValueChanged -= grvKMTT_CT_CellValueChanged;
                    var dsct = this.data_set_km["KM_CT"];
                    if (dsct == null||dsct.length==0) return;
                    // m_dsTienCTKM.AcceptChanges();
                    // dsct.AcceptChanges();
                    for (var dr of dsct)
                    {
                        // DataRow dr = dsct.Rows[i];
                        for (var j = 0; j < this.ds_tien_caidat.length; j++)
                        {
                            if (dr["khoanmuctt_id"] == this.ds_tien_caidat[j]["khoanmuctt_id"])
                            {
                                dr["tien"] = Math.abs(this.ds_tien_caidat[j]["tien"]*1);
                                dr["vat"] = Math.abs(this.ds_tien_caidat[j]["vat"]*1);
                                dr["tongtien"] = Math.abs(this.ds_tien_caidat[j]["tien"]*1) + Math.abs(this.ds_tien_caidat[j]["vat"]*1);
                            }
                        }
                        if (this.m_dsTienCTKM != null && this.m_dsTienCTKM.length > 0)
                        {
                            for (var drz of this.m_dsTienCTKM.filter(x=>x.ID==dr["khoanmuctt_id"] && x.kieu!=1))
                            {
                                dr["tien"] = Math.abs(drz["tien"]*1);
                                dr["vat"] = Math.abs(drz["vat"]*1);
                                dr["tongtien"] = Math.abs(drz["tien"]*1) + Math.abs(drz["vat"]*1);
                            }
                        }
                    }
                    // grvKMTT_CT.CellValueChanged += grvKMTT_CT_CellValueChanged;
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi : " + ex);
            }
        }
    }
}
</script>
