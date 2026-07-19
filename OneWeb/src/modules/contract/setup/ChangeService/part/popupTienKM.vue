<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="closeForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
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
                    <ejs-grid ref="grid" :dataSource="ds.list" locale="vi-VN" :allowSorting='true' :allowFiltering='true'
                        :allowPaging='true' :pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' :gridLines='1' :editSettings="{ allowEditing: true, mode: 'Normal' }" height="200">
                        <e-columns>
                            <e-column v-for="col in ds.cols" :key="col.fieldName" :field='col.fieldName' :allowEditing="col.allowEditing" :customAttributes="col.customAttributes" :headerText='col.headerText' :textAlign="col.textAlign" headerTextAlign="center" :editType="col.editType"  :format='col.format' :isPrimaryKey="col.isPrimaryKey"></e-column>
                        </e-columns>
                    </ejs-grid>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>
    /deep/ .e-grid .e-rowcell.cuoclapdat,.cuoclapdat { font-weight: bold; color:blue; }
    /deep/ .e-grid .e-rowcell.vatlapdat,.vatlapdat { font-weight: bold; color:cadetblue; }
    /deep/ .e-grid .e-rowcell.tongtien,.tongtien { font-weight: bold; color:red; }
</style>
<script>
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
export default {
    name: 'popupTienKM',
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
    },
    props: [ 'dulieu' ],
    watch: {
        'dulieugrid': {
            handler: function(val,oldval) {
                if(!oldval || oldval.length<=0) return;
                for(var i=0;i<val.length;i++) {
                    var tyle_vat = 10;
                    if(val[i].tyle_vat) tyle_vat = val[i].tyle_vat;
                    if(val[i].tongtien!=oldval[i].tongtien && val[i].vat==oldval[i].vat) {
                        val[i].vat = val[i].tongtien*tyle_vat/(100+tyle_vat);
                        val[i].tien = val[i].tongtien - val[i].vat;
                        this.ds.list[i].vat = val[i].vat;
                        this.ds.list[i].tien = val[i].tien;
                        this.$forceUpdate();
                    } else if (val[i].tien!=oldval[i].tien && val[i].vat==oldval[i].vat) {
                        val[i].vat = val[i].tien*tyle_vat/100;
                        val[i].tongtien = val[i].tien+val[i].vat;
                        this.ds.list[i].vat = val[i].vat;
                        this.ds.list[i].tongtien = val[i].tongtien;
                        this.$forceUpdate();
                    }
                }
            },
            deep:true,
        }
    },
    data() {
        return {
            ds: {
                list: [],
                cols: [],
                keycol: "",
                value: {},
            },
            btnThoat: false,
            tieude: "popup",
            tieudebang: "datapopup",
        }
    },
    mounted() {
        this.ds.list = this.dulieu.ds;
        if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        this.ds.cols = this.dulieu.cols;
        this.ds.keycol = this.dulieu.keycol;
        if(this.dulieu.allowExit) this.btnThoat = this.dulieu.allowExit;
        if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        if(this.dulieu.tieude) this.tieude = this.dulieu.tieude;
        if(this.dulieu.tieudebang) this.tieudebang = this.dulieu.tieudebang;
    },
    computed: {
        dulieugrid() { return JSON.parse(JSON.stringify(this.ds.list)); }
    },
    methods: {
        closeForm() {
            this.$emit('form-close',this.ds.list);
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
    }
}
</script>
