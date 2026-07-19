<template>
<div class="modal-content popup-box">
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
                            <table class="table-result table-gachle table-filter ">
                                <tr>
                                    <td colspan="16"> 
                                         <ejs-grid ref="grid" :dataSource='ds_thietbimua.list' locale="vi-VN" :allowSorting='true' :allowFiltering='true' 
                                        :allowPaging='true' :pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' :gridLines='1' :rowSelected="rowselect" 
                                        :rowDeselected="rowdeselect" height='400'>
                                        <e-columns>
                                            <e-column field='ma_tbi' headerText='Mã thiết bị' headerTextAlign="center" textAlign='Left' width=150></e-column>
                                            <e-column field='loai_tbi' headerText='Tên dịch vụ' headerTextAlign="center" textAlign='Left' width=250></e-column>
                                            <e-column field='soluong' headerText='Số lượng' headerTextAlign="center" textAlign='Left' width=100></e-column>
                                            <e-column type = 'checkbox' field='dangky' headerText='Mua' headerTextAlign="center" textAlign= 'Center' displayAsCheckBox='true' width=50 format= 'C2'></e-column>
                                            <e-column field='serial' headerText='Serial' headerTextAlign="center" textAlign = "Left" width=150 ></e-column>
                                            <e-column field='tien' headerText='Tiền' headerTextAlign="center" textAlign = "Right" width=150 ></e-column>
                                            <e-column field='vat' headerText='Vat' headerTextAlign="center" textAlign = "Right" width=150 ></e-column>
                                            <e-column field='tien_tratruoc' headerText='Tiền trả trước' headerTextAlign="center" textAlign = "Right" width=150 ></e-column>
                                            <e-column field='vat_tratruoc' headerText='Vat trả trước' headerTextAlign="center" textAlign = "Right" width=150 ></e-column>
                                            <e-column field='tien_km' headerText='Tiền KM' headerTextAlign="center" textAlign = "Right" width=100 ></e-column>
                                            <e-column field='vat_km' headerText='Vat KM' headerTextAlign="center" textAlign = "Right" width=100 ></e-column>
                                            <e-column field='sl_cha' headerText='SL Cha' headerTextAlign="center" textAlign = "Right" width=100 ></e-column>
                                            <e-column field='block_tiep' headerText='Block' headerTextAlign="center" textAlign = "Right" width=100 ></e-column>
                                            <e-column field='gia_block_tiep' headerText='Giá Block' headerTextAlign="center" textAlign = "Right" width=100 ></e-column>
                                            <e-column field='vat_block_tiep' headerText='Vat Block' headerTextAlign="center" textAlign = "Right" width=100 ></e-column>
                                            </e-columns>
                                        </ejs-grid> 
                                        
                                    </td>
                                </tr>
                                <tr class="tfoot">
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td>
                                        </td>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td>
                                            <input type="text" class="form-control tright text fw6" v-model="tongtien">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control tright text fw6" v-model="tongvat">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td>
                                            <input type="text" class="form-control tright text fw6" v-model="giablock">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control tright text fw6" v-model="vatblock">
                                        </td>
                                        <td></td>
                                    </tr>
                            </table>
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
</style>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Page,Filter,GridComponent, Sort, Grid  } from "@syncfusion/ej2-vue-grids";
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import API from './API'
import { Checkbox } from 'element-ui'
export default {
    name: 'popupBuyDevices',
    components: { API,required, minLength, integer, maxValue,DatePicker, L10n, setCulture ,GridPlugin, Page, Sort, Filter, GridComponent, Grid },
    props: [ 'dulieu' ],
    watch: {
        
    },
    data() {
        return {
            initialSort: {
            columns: [{ field: 'ma_tbi', direction: 'Ascending' },
                { field: 'loai_tbi', direction: 'Descending' }]
            },
        ds_thietbimua: {
            list: [],
            header: [],
            value: {},
            isEnabled: true,
            checked: []
        },
        success_code: "BSS-00000000",
        dichvudangky: [],
        rowchon: [],
        rowchon0: [],

        loaitb_id:0,
        tongtien: 0,
        tongvat: 0,
        giablock: 0,
        vatblock: 0,
        }
    },
    provide: {
      grid: [Sort, Page, Filter]
    },
    async mounted() {
        this.loaitb_id = this.dulieu._loaitb_id?this.dulieu._loaitb_id:0; 
        this.GetDsTbiMua();
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.dichvudangky = this.ds_thietbimua.list.filter(x=> x.dangky==1);
            console.log(this.dichvudangky);
            this.$emit('form-close',this.dichvudangky);
        },
        formatPrice(value) {
            let val = (value/1).toFixed(0).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
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
        rowselect()
        {
            var grid= this.$refs.grid;
            this.rowchon0 = this.rowchon;
            this.rowchon = grid.getSelectedRecords();
            this.tongtien = 0;
            this.tongvat = 0;
            this.giablock = 0;
            this.vatblock= 0; 
            for (var item of this.rowchon)
            {
                this.ds_thietbimua.list.filter(x=>x.loaitbi_id == item.loaitbi_id)[0].dangky=1;
                /* this.tongtien = this.tongtien + (item.tien ==""? 0: item.tien);
                this.tongvat = this.tongvat + (item.vat ==""? 0: item.vat); */
                this.giablock = this.giablock + (item.gia_block_tiep ==""? 0: item.gia_block_tiep);
                this.vatblock = this.vatblock + (item.vat_block_tiep == ""? 0: item.vat_block_tiep); 
            }
        },

        rowdeselect()
        {
            var grid= this.$refs.grid;
            this.rowchon0 = this.rowchon;
            this.rowchon = grid.getSelectedRecords();
            for (var item of this.rowchon0)
            {
                if (this.rowchon.filter(x => x.loaitbi_id == item.loaitbi_id).length<=0)
                {
                    this.ds_thietbimua.list.filter(x=>x.loaitbi_id == item.loaitbi_id)[0].dangky=0;
                   /*  this.tongtien = this.tongtien - (item.tien ==""? 0: item.tien);
                    this.tongvat = this.tongvat - (item.vat ==""? 0: item.vat); */
                    this.giablock = this.giablock - (item.gia_block_tiep ==""? 0: item.gia_block_tiep);
                    this.vatblock = this.vatblock - (item.vat_block_tiep == ""? 0: item.vat_block_tiep); 
                }
            }
        },
        
        GetDsTbiMua: async function ()
        {
            //Lấy danh sách thiết bị
            var input = {};
            input.vloaitb_id = this.loaitb_id; 
            this.loading(true);
            await API.lay_ds_thietbi(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.ds_thietbimua.list = response.data.data;
                     if(this.dulieu.ds && this.dulieu.ds.length>0) {
                        var ds = this.dulieu.ds;
                        this.ds_thietbimua.list.forEach(function (item) {
                            if (ds.filter(x => x.loaitbi_id === item.loaitbi_id).length > 0) {
                                item.dangky = 1
                            } else {
                                item.dangky = 0
                            }
                        });
                    } 
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách thiết bị!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
    }
}
</script>