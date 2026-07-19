<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Đăng ký dịch vụ gia tăng</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal" @click.prevent="terminateForm">
        </div>
    </div>
    <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="closeForm">
                                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="table-content">
                            <ejs-grid ref="grid" :load='load' :dataSource='ds_dichvuGTGT.list' locale='vi-VN' :allowSorting='true' :allowResizing="true" :allowFiltering='true' :actionComplete="actionComplete" 
                            :allowPaging='false' :pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' height='400' :editSettings="{ allowEditing: true, mode: 'Normal' }">  
                            <!-- :rowSelected="rowselect" 
                            :rowDeselected="rowdeselect" -->
                                <e-columns>
                                    <e-column field='STT' headerText='STT' headerTextAlign="center" textAlign='Center' width=60 :allowEditing="false"></e-column>
                                    <e-column field='MA_DVGT' headerText='Mã dịch vụ' headerTextAlign="center" textAlign='Left' width=120 :allowEditing="false" :allowFiltering="true" :filter="{ operator: 'contains' }"></e-column>
                                    <e-column field='TEN_DVGT' headerText='Tên dịch vụ' headerTextAlign="center" minWidth='100' maxWidth='500' width=250 :allowEditing="false" :allowFiltering="true" :filter="{ operator: 'contains' }"></e-column>
                                    <!-- <e-column type = 'checkbox' field='DANGKY' headerText='Đăng ký' headerTextAlign="center" textAlign= 'Center' displayAsCheckBox='true' width=50 format= 'C2'></e-column> -->
                                    <e-column field="DANGKY" headerText="Đăng ký" width="100" editType="booleanedit" displayAsCheckBox="true" type="boolean" textAlign="Center" :allowEditing="true"></e-column>
                                    <e-column field='CUOC_LD' format='N' class='cuoclapdat' :customAttributes="{class: 'cuoclapdat'}" headerText='Cước lắp đặt' headerTextAlign="center" textAlign="right" width=105 :allowEditing="true" ></e-column>
                                    <e-column field='VAT_LD' format='N' class='vatlapdat' :customAttributes="{class: 'vatlapdat'}" headerText='Vat lắp đặt' headerTextAlign="center" textAlign="right" width=105 :allowEditing="true"></e-column>
                                    <e-column field='TIEN' format='N' class='tongtien' :customAttributes="{class: 'tongtien'}" headerText='Tổng tiền' headerTextAlign="center" textAlign="right" width=105 :allowEditing="true"></e-column>
                                    <e-column field='CUOC_SD' format='N' headerText='Phí duy trì' headerTextAlign="center" textAlign="right" width=105 :allowEditing="false"></e-column>
                                    <e-column field='VAT_SD' format='N' headerText='Vat duy trì' headerTextAlign="center" textAlign="right" width=105 :allowEditing="false"></e-column>
                                    <e-column field='NOIDUNG' format='N' headerText='Số lượng/Nội dung' headerTextAlign="center" width=105 :allowEditing="true"></e-column>
                                    <e-column field='SL_CHA' format='N' headerText='Sl cha' headerTextAlign="center" textAlign="right" width=100 :allowEditing="false"></e-column>
                                    <e-column field='BLOCK_TIEP' format='N' headerText='Block' headerTextAlign="center" textAlign="right" width=105 :allowEditing="false"></e-column>
                                    <e-column field='GIA_BLOCK_TIEP' format='N' headerText='Giá Block' headerTextAlign="center" textAlign="right" width=105 :allowEditing="false"></e-column>
                                    <e-column field='VAT_BLOCK_TIEP' format='N' headerText='Vat Block' headerTextAlign="center" textAlign="right" width=105 :allowEditing="false"></e-column>
                                    <e-column field='GHICHU' headerText='Ghi chú' headerTextAlign="center" width=200 allowEditing="true" :allowFiltering="true" :filter="{ operator: 'contains' }"></e-column>
                                </e-columns>
                                <e-aggregates>
                                    <e-aggregate>
                                        <e-columns>
                                            <e-column columnName="MA_DVGT" :customAttributes="{class: 'tien'}" type="Custom" :customAggregate="tongtienAggregateFn"></e-column>
                                            <e-column columnName="CUOC_SD" format='N' type="Custom" :customAggregate="tienAggregateFn"></e-column>
                                            <e-column columnName="VAT_SD" format='N' type="Custom" :customAggregate="vatAggregateFn"></e-column>
                                            <e-column columnName="GIA_BLOCK_TIEP" format='N' type="Custom" :customAggregate="giablkAggregateFn"></e-column>
                                            <e-column columnName="VAT_BLOCK_TIEP" format='N' type="Custom" :customAggregate="vatblkAggregateFn"></e-column>
                                        </e-columns>
                                    </e-aggregate>
                                </e-aggregates>
                            </ejs-grid> 
                        </div>
                       
                    </div>
                    
                </div>
            </div>
</template>
<style>
     input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
    .e-grid .e-gridheader .e-sortfilter .e-headercelldiv, .e-grid .e-gridheader .e-stackedheadercelldiv, .e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv 
    {
    padding: 0 5px 0 5px !important;
    }
    @import "@/node_modules/@syncfusion/ej2-vue-grids/styles/material.css";
    .e-grid .e-rowcell.cuoclapdat,.cuoclapdat { font-weight: bold; color:blue; }
    .e-grid .e-rowcell.vatlapdat,.vatlapdat { font-weight: bold; color:cadetblue; }
    .e-grid .e-rowcell.tongtien,.tongtien { font-weight: bold; color:red; }

    .e-grid .e-rowcell.vat,.e-grid .e-summarycell.vat, .vat { font-weight: bold; color:darkred; }
    .e-grid .e-rowcell.tien,.e-grid .e-summarycell.tien, .tien { font-weight: bold; color:red; }
    .e-input-group-icon { display:none !important; }
</style>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Page,Filter,GridComponent, Sort, Grid, Aggregate, Edit,Resize, Group, CommandColumn, ExcelExport, Freeze, Toolbar,  } from "@syncfusion/ej2-vue-grids";
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import API from './API'
import { Checkbox } from 'element-ui'

L10n.load({
    "vi-VN": {
        grid: {
            EmptyRecord: "Không có bản ghi nào để hiển thị ",
            Item: "Bản ghi",
            Items: "Bản ghi",
            FilterbarTitle: "",
        },
    },
});

export default {
    name: 'popupServiceAdds',
    components: { API,required, minLength, integer, maxValue,DatePicker,L10n, setCulture ,GridPlugin, Page, Sort, Filter, GridComponent, Grid, Aggregate },
    props: [ 'dulieu' ],
    lines: 'Both',
    watch: {
        'ds_dichvuGTGT.list': {
            handler: function() { this.$forceUpdate(); },
            deep:true,
        },
    },
    data() {
        return {
            initialSort: {
            columns: [{ field: 'MA_DVGT', direction: 'Ascending' },
                { field: 'TEN_DVGT', direction: 'Descending' }]
            },
            lvDvgt: [],
            ds_dichvuGTGT: {
                list: [],
                header: [],
                value: {},
                isEnabled: true,
                checked: []
            },
            success_code: "BSS-00000000",
            loaitb_id:"0",
            hdtb_id:"0",
            linhvuc_id:"0",
            tocdo_id:"0",
            dichvudangky: [],
            rowchon: [],
            rowchon0: [],
            phiduytri: 0,
            vatduytri: 0,
            giablock: 0,
            vatblock: 0,
            lapmoi: true,
        }
    },
    provide: {
      grid: [Sort,
            Filter,
            Resize,
            Group,
            Freeze,
            CommandColumn,
            ExcelExport,
            Edit,
            Aggregate,
            Toolbar,]
    },

    
    async mounted() {
        //this.$refs.grid.gridLines = 'Both';
        this.loaitb_id = this.dulieu._loaitb_id?this.dulieu._loaitb_id:0;
        this.hdtb_id = this.dulieu._hdtb_id?this.dulieu._hdtb_id:0;
        this.linhvuc_id = this.dulieu._linhvuc_id?this.dulieu._linhvuc_id:0;
        this.tocdo_id = this.dulieu._tocdo_id?this.dulieu._tocdo_id:0;
        this.lvDvgt = this.dulieu.lvDvgt;
        this.lapmoi = this.dulieu.lapmoi?this.dulieu.lapmoi:true;
        this.GetDsDVGT();
    },
    computed: {
        is_nv_vnpti() {
            return this.$root.token.getPhanVungID()==98?1:0
        },
    },
    methods: {
        load: function() {
            this.$refs.grid.ej2Instances.element.addEventListener('mouseup', function(e) {
                var instance = this.ej2_instances[0];
                if (e.target.classList.contains("e-rowcell")) {
                    if (instance.isEdit)
                        instance.endEdit();
                    let index = parseInt(e.target.getAttribute("Index"));
                    instance.selectRow(index);
                    instance.startEdit();
                }
            });
            this.$refs.grid.ej2Instances.element.addEventListener('change', function(e) {
                if (e.target.classList.contains("e-checkbox")) {
                    var instance = this.ej2_instances[0];
                    if (instance.isEdit)
                    {
                        instance.editModule.batchSave();
                        instance.endEdit();
                    }
                }
            });
        },
        actionComplete(args) {
            if (args.requestType === "save") {
                for(var item of this.ds_dichvuGTGT.list)
                    if(item.DICHVUGT_ID==args.data.DICHVUGT_ID) {
                        item.DANGKY=args.data.DANGKY?1:0;
                        break;
                    }
                var val = args.data;
                var oldval = args.rowData?args.rowData:args.previousData;
                if(!val.NOIDUNG) val.NOIDUNG = 1;
                else val.NOIDUNG = val.NOIDUNG*1;
                
                if(val.CUOC_LD != oldval.CUOC_LD)
                    val.VAT_LD = val.CUOC_LD?this.round(val.CUOC_LD*0.1):0;
                val.TIEN = (val.CUOC_LD*1) + (val.VAT_LD*1);
                
                var item = this.ds_dichvuGTGT.list.find(x=>x.DICHVUGT_ID==val.DICHVUGT_ID);
                if(item) {
                    item.TIEN = val.TIEN;
                    item.CUOC_LD = val.CUOC_LD;
                    item.VAT_LD = val.VAT_LD;
                    item.NOIDUNG = val.NOIDUNG;
                    item.DANGKY = val.DANGKY;
                    item.GHICHU = val.GHICHU;
                }
                this.$forceUpdate();
            }
            if (args.requestType === "refresh") { console.info(this.ds_dichvuGTGT.list); }
            // if (args.requestType === "beginEdit") { 
            //     if(args.rowData['serialreadonly']) args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'serial'].readOnly = true; 
            //     else args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'serial'].readOnly = false; 
            //     console.info(args.rowData['serialreadonly'],args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'serial'],args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'serial'].readOnly);
            //     if(args.rowData['tratruocreadonly']) {
            //         args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'tien_tratruoc'].readOnly = true; 
            //         args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'vat_tratruoc'].readOnly = true; 
            //     }
            //     else {
            //         args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'tien_tratruoc'].readOnly = false; 
            //         args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'vat_tratruoc'].readOnly = false; 
            //     }
            // }
        },
        tongtienAggregateFn: function(d) {
            return 'Tổng: '+ (this.TinhTongAggr(d,'CUOC_SD')*1+this.TinhTongAggr(d,'VAT_SD')*1+this.TinhTongBlockAggr(d,'GIA_BLOCK_TIEP')*1+this.TinhTongBlockAggr(d,'VAT_BLOCK_TIEP')*1).toLocaleString('en-US');
        },
        tienAggregateFn : function (d) {
            return this.TinhTongAggr(d,'CUOC_SD');
        },
        vatAggregateFn : function (d) {
            return this.TinhTongAggr(d,'VAT_SD');
        },
        giablkAggregateFn : function (d) {
            return this.TinhTongBlockAggr(d,'GIA_BLOCK_TIEP');
        },
        vatblkAggregateFn : function (d) {
            return this.TinhTongBlockAggr(d,'VAT_BLOCK_TIEP');
        },
        TinhTongAggr: function(d,e) {
            var fdata = d.result.filter((item) => item.DANGKY==1 );
            var total = 0;
            for(var item of fdata)
                total += (item[e]?item[e]*1:0)*(item['NOIDUNG']?item['NOIDUNG']*1:0);
            return total;
            // return (fdata && fdata.length>0)?fdata.map(x=>x[e]).reduce((a, b) => a + b ):0;
        },
        TinhTongBlockAggr: function(d,e) {
            var fdata = d.result.filter((item) => item.DANGKY==1 );
            var tien_tbi = 0;
            for(var item of fdata) {
                var block_dau = item['BLOCK_DAU']?item['BLOCK_DAU']*1:0;
                var vsl_cha = item['SL_CHA'];
                if(vsl_cha && vsl_cha!=-1)
                    if(item['BLOCK_TIEP'] && item[e])
                        if (vsl_cha > block_dau)
                        {
                            var vsl_conlai = vsl_cha - block_dau;

                            var t2 = vsl_conlai % item['BLOCK_TIEP'];
                            var vsl_bl = 0;
                            if (t2 > 0)
                                vsl_bl = vsl_conlai / item['BLOCK_TIEP'] + 1;
                            else
                                vsl_bl = vsl_conlai / item['BLOCK_TIEP'];

                            var tien_block = 0;
                            tien_block = vsl_bl * item[e];
                            //dtien_tbi_tmp += tien_block;
                            tien_tbi = tien_tbi + (tien_block * (item['NOIDUNG']?item['NOIDUNG']*1:0));
                        }
            }
            return tien_tbi;
            // return (fdata && fdata.length>0)?fdata.map(x=>x[e]).reduce((a, b) => a + b ):0;
        },
        formatPrice(value) {
            let val = (value/1).toFixed(0).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        
        rowselect()
        {
            // var grid= this.$refs.grid;
            // this.rowchon0 = this.rowchon;
            // this.rowchon = grid.getSelectedRecords();
            // this.phiduytri = 0;
            // this.vatduytri = 0;
            // this.giablock = 0;
            // this.vatblock= 0;
            // var self= this
            // this.rowchon.forEach(function(item){
            //     self.ds_dichvuGTGT.list.filter(x=>x.DICHVUGT_ID == item.DICHVUGT_ID)[0].DANGKY=1;
            //     self.phiduytri = self.phiduytri + (item.CUOC_SD ==""? 0: item.CUOC_SD);
            //     self.vatduytri = self.vatduytri + (item.VAT_SD ==""? 0: item.VAT_SD);
            //     self.giablock = self.giablock + (item.GIA_BLOCK_TIEP ==""? 0: item.GIA_BLOCK_TIEP);
            //     self.vatblock = self.vatblock + (item.VAT_BLOCK_TIEP == ""? 0: item.VAT_BLOCK_TIEP); 
            // })
        },
        rowdeselect()
        {
            // var grid= this.$refs.grid;
            // this.rowchon0 = this.rowchon;
            // this.rowchon = grid.getSelectedRecords();
            // for (var item of this.rowchon0)
            // {
            //     if (this.rowchon.filter(x => x.DICHVUGT_ID == item.DICHVUGT_ID).length<=0)
            //     {
            //         this.ds_dichvuGTGT.list.filter(x=>x.DICHVUGT_ID == item.DICHVUGT_ID)[0].DANGKY=0;
            //         this.phiduytri = this.phiduytri - (item.CUOC_SD ==""? 0: item.CUOC_SD);
            //         this.vatduytri = this.vatduytri - (item.VAT_SD ==""? 0: item.VAT_SD);
            //         this.giablock = this.giablock - (item.GIA_BLOCK_TIEP ==""? 0: item.GIA_BLOCK_TIEP);
            //         this.vatblock = this.vatblock - (item.VAT_BLOCK_TIEP == ""? 0: item.VAT_BLOCK_TIEP); 
            //     }
            // }
        },
        closeForm() {
            this.dichvudangky.forEach((item1)=>{
                if (item1.DANGKY == "1")
                {
                    
                    if(!this.lapmoi) item1.HUY = 1;
                    item1.DANGKY = 0;
                }
            }) 
            var self= this
            this.ds_dichvuGTGT.list.filter(x=>x.DANGKY == 1).forEach(function(item1){
                var item = self.dichvudangky.find(x=>x.DICHVUGT_ID == item1.DICHVUGT_ID);
                if(item) {
                    item.DANGKY = 1;
                    item.HUY = 0;
                    item.CUOC_LD = item1.CUOC_LD;
                    item.VAT_LD = item1.VAT_LD;
                    item.TIEN = item1.TIEN;
                    item.NOIDUNG = item1.NOIDUNG;
                    item.GHICHU = item1.GHICHU;
                }
                // self.dichvudangky.filter(x=>x.DICHVUGT_ID == item1.DICHVUGT_ID)[0].DANGKY=1;
                // self.dichvudangky.filter(x=>x.DICHVUGT_ID == item1.DICHVUGT_ID)[0].HUY=0;
            });
            this.$emit('form-close',this.dichvudangky);
        },

        ShowAlert(mesage,val)
        {
            if (val==0)
            {
                this.$toast.success(mesage);
            }
            else if (val==1)
            {
                this.$toast.warning(mesage);
            }
            else if (val==2)
            {
                this.$toast.error(mesage);
            }
        },

        GetDsDVGT: async function ()
        {
            //Lấy thông tin chi tiết thuê bao
            var inputstring = { "LOAITB_ID": this.loaitb_id, "HDTB_ID": this.hdtb_id, "LINHVUC_ID": this.linhvuc_id, "I_TOCDO_ID": this.tocdo_id };
            var input = {};
            input.ds_para =  JSON.stringify(inputstring);
            this.loading(true);
            await API.fn_lay_ds_dvgt_macdinh(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.ds_dichvuGTGT.list = JSON.parse(response.data.data).RESULT.DS_TB_DICHVU;
                    this.dichvudangky = JSON.parse(response.data.data).RESULT.DS_TB_DICHVU;
                    if(this.ds_dichvuGTGT.list && this.ds_dichvuGTGT.list.length>0 && this.lvDvgt && this.lvDvgt.length>0)
                        for(var item1 of this.lvDvgt) 
                            for(var item2 of this.ds_dichvuGTGT.list)
                                if(item1.dichvugt_id == item2.DICHVUGT_ID) {
                                    item2.DANGKY = item1.dangky;
                                    item2.CUOC_LD = item1.cuoc_ld;
                                    item2.VAT_LD = item1.vat_ld;
                                    item2.TIEN = item1.tien;
                                    item2.NOIDUNG = item1.noidung;
                                    item2.GHICHU = item1.ghichu;
                                    break;
                                }
                }
                else if (response.data.error==204) {
                    this.ShowAlert("Không có danh sách dịch vụ gia tăng cho loại thuê bao này!",2);
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách dịch vụ gia tăng!",2);
                }
            }
            ).finally(()=>{ this.loading(false); });
        },
        round(num) {
            if(this.is_nv_vnpti==1)
                return +(Math.round(num + "e+2")  + "e-2");
            else return Math.round(num);
        },
    }
}
</script>