<template>
    <div class="modal-content popup-box">
        
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#" @click.prevent="closeForm">
                        <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="table-content">
                        <ejs-grid ref="grid" :dataSource='ds_dichvuKhac.list' locale="vi-VN" :allowSorting='true' :allowFiltering='true' 
                        :allowPaging='true' :pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' :gridLines='1' :rowSelected="rowselect" 
                        :rowDeselected="rowdeselect" height='200'>
                        <e-columns>
                            <e-column field='ma_dvkhac' :filter="{ operator: 'contains' }" headerText='Mã dịch vụ' headerTextAlign="center" textAlign='Left' width=50></e-column>
                            <e-column field='ten_dvkhac' :filter="{ operator: 'contains' }" headerText='Tên dịch vụ' headerTextAlign="center" textAlign='Left' width=150></e-column>
                            <e-column field='dangky' headerText='Đăng ký' headerTextAlign="center" textAlign='center' displayAsCheckBox='true' type='checkbox' format='C2' width="50"></e-column>
                            <e-column field='trangthai' :filter="{ operator: 'contains' }" headerText='Trạng thái' headerTextAlign="center" textAlign = "Left" width=105 ></e-column>
                            </e-columns>
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
    .e-input-group-icon { display:none !important; }
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
    name: 'popupServiceOthers',
    components: { API,required, minLength, integer, maxValue,DatePicker, L10n, setCulture ,GridPlugin, Page, Sort, Filter, GridComponent, Grid},
    props: [ 'dulieu' ],
    watch: {
        
    },
    data() {
        return {
            initialSort: {
            columns: [{ field: 'ma_dvkhac', direction: 'Ascending' },
                { field: 'ten_dvkhac', direction: 'Descending' }]
            },

        ds_dichvuKhac: {
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
        loai_id: 0,
        loaitb_id: 0,
        thanhtoan_id: 0,
        }
    },
    provide: {
      grid: [Sort, Page, Filter]
    },
    async mounted() {
        this.loai_id = this.dulieu._loai_id?this.dulieu._loai_id:0;
        this.loaitb_id = this.dulieu._loaitb_id?this.dulieu._loaitb_id:0;
        this.thanhtoan_id = this.dulieu._thanhtoan_id?this.dulieu._thanhtoan_id:0;  
        this.GetDsDVKhac();
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.dichvudangky = this.ds_dichvuKhac.list.filter(x=> x.dangky==1);
            for(var i=0; i<this.dichvudangky.length;i++) {
                this.dichvudangky[i].KIEU_YC = 1;//KieuCaiDatDichVuKhac.KIEU_CD_THEM_MOI_DVKHAC;
                this.dichvudangky[i].HD_ID = i;
                this.dichvudangky[i].TTTH = 1;//TrangThaiCaiDat.DA_CAIDAT;
                if(this.loai_id==2) {
                    this.dichvudangky[i].STATUS = 1;//TRANGTHAI_DONGBO.DONGBO_LHD;
                }
            }
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
            var self= this
            this.rowchon.forEach(function(item){
                self.ds_dichvuKhac.list.filter(x=>x.dvkhac_id == item.dvkhac_id)[0].dangky=1;
            })
        },

        rowdeselect()
        {
            var grid= this.$refs.grid;
            this.rowchon0 = this.rowchon;
            this.rowchon = grid.getSelectedRecords();
            for (var item of this.rowchon0)
            {
                if (this.rowchon.filter(x => x.dvkhac_id == item.dvkhac_id).length<=0)
                {
                    this.ds_dichvuKhac.list.filter(x=>x.dvkhac_id == item.dvkhac_id)[0].dangky=0;
                }
            }
        },
        GetDsDVKhac: async function ()
        {
            //Lấy thông tin dịch vụ khác
            var input = {};
            input.vloaiid = this.loai_id;
            input.vloaitb_id = this.loaitb_id;
            input.vthanhtoan_id = this.thanhtoan_id;
            this.loading(true);
            await API.lay_ds_dichvu_khac(this.axios, input).then((response) => {
                this.loading(false);
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.ds_dichvuKhac.list = response.data.data;
                    if(this.dulieu.ds && this.dulieu.ds.length>0) {
                        var ds = this.dulieu.ds;
                        this.ds_dichvuKhac.list.forEach(function (item) {
                            if (ds.filter(x => x.dvkhac_id === item.dvkhac_id).length > 0) {
                                item.dangky = 1
                            } else {
                                item.dangky = 0
                            }
                        });
                    }
                }
                else if (response.data.error==204) {
                    this.ShowAlert("Không có dịch vụ khác cho loại thuê bao này!",2);
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách dịch vụ khác!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
    }
}
</script>