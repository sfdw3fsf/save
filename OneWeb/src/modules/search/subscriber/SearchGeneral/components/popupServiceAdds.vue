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
                                          <ejs-grid ref="grid" :dataSource='ds_dichvuGTGT.list' :locale='vi-VN' :allowSorting='true' :allowFiltering='true' 
                                          :allowPaging='true' :pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' :gridLines='1' :rowSelected="rowselect" 
                                          :rowDeselected="rowdeselect" height='400'>
                                            <e-columns>
                                                <e-column field='STT' headerText='STT' headerTextAlign="center" textAlign='Center' width=60></e-column>
                                                <e-column field='MA_DVGT' headerText='Mã dịch vụ' headerTextAlign="center" textAlign='Left' width=120></e-column>
                                                <e-column field='TEN_DVGT' headerText='Tên dịch vụ' headerTextAlign="center" width=250></e-column>
                                                <e-column type = 'checkbox' field='DANGKY' headerText='Đăng ký' headerTextAlign="center" textAlign= 'Center' displayAsCheckBox='true' width=50 format= 'C2'></e-column>
                                                <e-column field='CUOC_LD' headerText='Cước lắp đặt' headerTextAlign="center" textAlign = right width=105 ></e-column>
                                                <e-column field='VAT_LD' headerText='Vat lắp đặt' headerTextAlign="center" textAlign = right width=105></e-column>
                                                <e-column field='TIEN' headerText='Tổng tiền' headerTextAlign="center" textAlign = right width=105></e-column>
                                                <e-column field='CUOC_SD' headerText='Phí duy trì' headerTextAlign="center" textAlign = right width=105></e-column>
                                                <e-column field='VAT_SD' headerText='Vat duy trì' headerTextAlign="center" textAlign = right width=105></e-column>
                                                <e-column field='NOIDUNG' headerText='Nội dung/Số lượng' headerTextAlign="center" width=105></e-column>
                                                <e-column field='' headerText='Số lượng' headerTextAlign="center" textAlign = right width=100></e-column>
                                                <e-column field='BLOCK_TIEP' headerText='Block' headerTextAlign="center" textAlign = right width=105></e-column>
                                                <e-column field='GIA_BLOCK_TIEP' headerText='Giá Block' headerTextAlign="center" textAlign = right width=105></e-column>
                                                <e-column field='VAT_BLOCK_TIEP' headerText='Vat Block' headerTextAlign="center" textAlign = right width=105></e-column>
                                                <e-column field='GHICHU' headerText='Ghi chú' headerTextAlign="center" width=200></e-column>
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
                                            <input type="text" class="form-control tright text fw6" value="0">
                                        </td>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td>
                                            <input type="text" class="form-control tright text fw6" v-model="phiduytri">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control tright text fw6" v-model="vatduytri">
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
    components: { API,required, minLength, integer, maxValue,DatePicker,L10n, setCulture ,GridPlugin, Page, Sort, Filter, GridComponent, Grid},
    props: [ 'dulieu' ],
    lines: 'Both',
    watch: {
        
    },
    data() {
        return {
            initialSort: {
            columns: [{ field: 'MA_DVGT', direction: 'Ascending' },
                { field: 'TEN_DVGT', direction: 'Descending' }]
            },

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
        }
    },
    provide: {
      grid: [Sort, Page, Filter]
    },

    
    async mounted() {
        this.$refs.grid.gridLines = 'Both';
        this.loaitb_id = this.dulieu._loaitb_id?this.dulieu._loaitb_id:0;
        this.hdtb_id = this.dulieu._hdtb_id?this.dulieu._hdtb_id:0;
        this.linhvuc_id = this.dulieu._linhvuc_id?this.dulieu._linhvuc_id:0;
        this.tocdo_id = this.dulieu._tocdo_id?this.dulieu._tocdo_id:0; 
        this.GetDsDVGT();
    },
    computed: {
       
    },
    methods: {

        formatPrice(value) {
            let val = (value/1).toFixed(0).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        
        rowselect()
        {
            var grid= this.$refs.grid;
            this.rowchon0 = this.rowchon;
            this.rowchon = grid.getSelectedRecords();
            this.phiduytri = 0;
            this.vatduytri = 0;
            this.giablock = 0;
            this.vatblock= 0;
            var self= this
            this.rowchon.forEach(function(item){
                self.ds_dichvuGTGT.list.filter(x=>x.DICHVUGT_ID == item.DICHVUGT_ID)[0].DANGKY=1;
                self.phiduytri = self.phiduytri + (item.CUOC_SD ==""? 0: item.CUOC_SD);
                self.vatduytri = self.vatduytri + (item.VAT_SD ==""? 0: item.VAT_SD);
                self.giablock = self.giablock + (item.GIA_BLOCK_TIEP ==""? 0: item.GIA_BLOCK_TIEP);
                self.vatblock = self.vatblock + (item.VAT_BLOCK_TIEP == ""? 0: item.VAT_BLOCK_TIEP); 
            })
        },
        rowdeselect()
        {
            var grid= this.$refs.grid;
            this.rowchon0 = this.rowchon;
            this.rowchon = grid.getSelectedRecords();
            for (var item of this.rowchon0)
            {
                if (this.rowchon.filter(x => x.DICHVUGT_ID == item.DICHVUGT_ID).length<=0)
                {
                    this.ds_dichvuGTGT.list.filter(x=>x.DICHVUGT_ID == item.DICHVUGT_ID)[0].DANGKY=0;
                    this.phiduytri = this.phiduytri - (item.CUOC_SD ==""? 0: item.CUOC_SD);
                    this.vatduytri = this.vatduytri - (item.VAT_SD ==""? 0: item.VAT_SD);
                    this.giablock = this.giablock - (item.GIA_BLOCK_TIEP ==""? 0: item.GIA_BLOCK_TIEP);
                    this.vatblock = this.vatblock - (item.VAT_BLOCK_TIEP == ""? 0: item.VAT_BLOCK_TIEP); 
                }
            }
        },
        closeForm() {
            this.dichvudangky.forEach(function(item1){
                if (item1.DANGKY == "1")
                {
                    item1.HUY = 1;
                    item1.DANGKY = 0;
                }
            }) 
            var self= this
            this.ds_dichvuGTGT.list.filter(x=>x.DANGKY == 1).forEach(function(item1){
                self.dichvudangky.filter(x=>x.DICHVUGT_ID == item1.DICHVUGT_ID)[0].DANGKY=1;
                self.dichvudangky.filter(x=>x.DICHVUGT_ID == item1.DICHVUGT_ID)[0].HUY=0;
            })  
            console.log(this.dichvudangky);
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
            var inputstring = "LOAITB_ID:"+ this.loaitb_id + ",HDTB_ID:"+ this.hdtb_id +",LINHVUC_ID:"+ this.linhvuc_id +",I_TOCDO_ID:"+ this.tocdo_id ;
            console.log(inputstring);
            var input = {};
            input.ds_para =  inputstring;//JSON.stringify({"LOAITB_ID":58,"HDTB_ID":123,"LINHVUC_ID":123,"I_TOCDO_ID":321});
            //input.ds_para =  JSON.stringify(inputstring);
            console.log(input);
            this.loading(true);
            await API.fn_lay_ds_dvgt_macdinh(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                   this.ds_dichvuGTGT.list = JSON.parse(response.data.data).RESULT.DS_TB_DICHVU;
                   this.dichvudangky = JSON.parse(response.data.data).RESULT.DS_TB_DICHVU;
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách dịch vụ gia tăng!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
    }
}
</script>