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
                    <!-- <table class="table-result table-gachle table-filter ">
                        <tr>
                    <td colspan="16">  -->
                    <ejs-grid ref="grid" :dataSource='ds_thietbimua.list' locale="vi-VN" :allowSorting='true'
                        :allowFiltering='true' :allowPaging='true'
                        :pageSettings='{ pageSize: 10, pageSizes: [10, 50, 100, 200] }' :gridLines='1' :rowSelected="rowselect"
                        :rowDeselected="rowdeselect" height='400'>
                        <e-columns>
                            <e-column field='MA_TBI' headerText='Mã thiết bị' headerTextAlign="center" textAlign='Left'
                                width=150></e-column>
                            <e-column field='LOAI_TBI' headerText='Tên dịch vụ' headerTextAlign="center"
                                textAlign='Left' width=250></e-column>
                            <e-column field='SOLUONG' format='N' headerText='Số lượng' headerTextAlign="center"
                                textAlign='Left' width=100></e-column>
                            <e-column type='checkbox' field='DANGKY' headerText='Mua' headerTextAlign="center"
                                textAlign='Center' displayAsCheckBox='true' width=50 format='C2'></e-column>
                            <e-column field='SERIAL' headerText='Serial' headerTextAlign="center" textAlign="Left"
                                width=150></e-column>
                            <e-column field='TIEN' format='N' headerText='Tiền' headerTextAlign="center"
                                textAlign="Right" width=150></e-column>
                            <e-column field='VAT' format='N' headerText='Vat' headerTextAlign="center" textAlign="Right"
                                width=150></e-column>
                            <e-column field='TIEN_TRATRUOC' format='N' headerText='Tiền trả trước'
                                headerTextAlign="center" textAlign="Right" width=150></e-column>
                            <e-column field='VAT_TRATRUOC' format='N' headerText='Vat trả trước'
                                headerTextAlign="center" textAlign="Right" width=150></e-column>
                            <e-column field='TIEN_KM' format='N' headerText='Tiền KM' headerTextAlign="center"
                                textAlign="Right" width=100></e-column>
                            <e-column field='VAT_KM' format='N' headerText='Vat KM' headerTextAlign="center"
                                textAlign="Right" width=100></e-column>
                            <e-column field='sl_cha' headerText='SL Cha' headerTextAlign="center" textAlign="Right"
                                width=100></e-column>
                            <e-column field='BLOCK_TIEP' format='N' headerText='Block' headerTextAlign="center"
                                textAlign="Right" width=100></e-column>
                            <e-column field='GIA_BLOCK_TIEP' format='N' headerText='Giá Block' headerTextAlign="center"
                                textAlign="Right" width=100></e-column>
                            <e-column field='VAT_BLOCK_TIEP' format='N' headerText='Vat Block' headerTextAlign="center"
                                textAlign="Right" width=100></e-column>
                        </e-columns>
                        <e-aggregates>
                            <e-aggregate>
                                <e-columns>
                                    <e-column columnName="tien" format='N' type="Custom"
                                        :customAggregate="tienAggregateFn"></e-column>
                                    <!-- </e-columns> -->
                                    <!-- </e-aggregate>
                            <e-aggregate> -->
                                    <!-- <e-columns> -->
                                    <e-column columnName="vat" format='N' type="Custom"
                                        :customAggregate="vatAggregateFn"></e-column>
                                    <e-column columnName="gia_block_tiep" format='N' type="Custom"
                                        :customAggregate="giablkAggregateFn"></e-column>
                                    <e-column columnName="vat_block_tiep" format='N' type="Custom"
                                        :customAggregate="vatblkAggregateFn"></e-column>

                                </e-columns>
                            </e-aggregate>
                        </e-aggregates>
                    </ejs-grid>
                </div>
            </div>
        </div>  </div>
</template>
<style>
input.disabled,
select.disabled,
.disabled input,
.disabled select,
.disabled /deep/ .select2-selection {
    background-color: #EEE;
    color: lightgray;
}

.e-grid .e-gridheader .e-sortfilter .e-headercelldiv,
.e-grid .e-gridheader .e-stackedheadercelldiv,
.e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv {
    padding: 0 5px 0 5px !important;
}
</style>
<script>
import Vue from "vue";
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Page, Filter, GridComponent, Sort, Grid, Aggregate } from "@syncfusion/ej2-vue-grids";
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
// import API from './API'
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
    name: 'popupBuyDevices',
    components: { required, minLength, integer, maxValue, DatePicker, L10n, setCulture, GridPlugin, Page, Sort, Filter, GridComponent, Grid, Aggregate },
    props: ['dulieu'],
    watch: {
        // 'ds_thietbimua.list': {
        //     handler: function() { this.$forceUpdate(); },
        //     deep:true,
        // },
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

            loaitb_id: 0,
            kieu: 0,
            tongtien: 0,
            tongvat: 0,
            giablock: 0,
            vatblock: 0,
        }
    },
    provide: {
        grid: [Sort, Page, Filter, Aggregate]
    },
    async mounted() {
        this.loaitb_id = this.dulieu._loaitb_id ? this.dulieu._loaitb_id : 0;
        this.kieu = this.dulieu.kieu ? this.dulieu.kieu : 0;
        this.GetDsTbi();
    },
    computed: {
    },
    methods: {
        tienAggregateFn: function (d) {
            return this.TinhTongAggr(d, 'tien');
        },
        vatAggregateFn: function (d) {
            return this.TinhTongAggr(d, 'vat');
        },
        giablkAggregateFn: function (d) {
            return this.TinhTongAggr(d, 'gia_block_tiep');
        },
        vatblkAggregateFn: function (d) {
            return this.TinhTongAggr(d, 'vat_block_tiep');
        },
        TinhTongAggr: function (d, e) {
            var fdata = d.result.filter((item) => item.DANGKY == 1);
            return (fdata && fdata.length > 0) ? fdata.map(x => x[e]).reduce((a, b) => a + b) : 0;
        },
        closeForm() {
            this.dichvudangky = this.ds_thietbimua.list.filter(x => x.DANGKY == 1);
            // console.log(this.ds_thietbimua.list, this.dichvudangky);
            this.$emit('form-close', this.dichvudangky);
        },
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        ShowAlert(mesage, val) {
            if (val == 0) {
                this.$toast.success(mesage);
            }
            else if (val == 1) {
                this.$toast.warning(mesage);
            }
            else if (val == 2) {
                this.$toast.error(mesage);
            }
        },
        rowselect() {
            var grid = this.$refs.grid;
            this.rowchon0 = this.rowchon;
            this.rowchon = grid.getSelectedRecords();
            // this.tongtien = 0;
            // this.tongvat = 0;
            // this.giablock = 0;
            // this.vatblock= 0; 
            for (var item of this.rowchon) {
                this.ds_thietbimua.list.filter(x => x.LOAITBI_ID == item.LOAITBI_ID)[0].DANGKY = 1;
                /* this.tongtien = this.tongtien + (item.tien ==""? 0: item.tien);
                this.tongvat = this.tongvat + (item.vat ==""? 0: item.vat); */
                // this.giablock = this.giablock + (item.gia_block_tiep ==""? 0: item.gia_block_tiep);
                // this.vatblock = this.vatblock + (item.vat_block_tiep == ""? 0: item.vat_block_tiep); 
            }
        },

        rowdeselect() {
            var grid = this.$refs.grid;
            this.rowchon0 = this.rowchon;
            this.rowchon = grid.getSelectedRecords();
            for (var item of this.rowchon0) {
                if (this.rowchon.filter(x => x.LOAITBI_ID == item.LOAITBI_ID).length <= 0) {
                    this.ds_thietbimua.list.filter(x => x.LOAITBI_ID == item.LOAITBI_ID)[0].DANGKY = 0;
                    /*  this.tongtien = this.tongtien - (item.tien ==""? 0: item.tien);
                    this.tongvat = this.tongvat - (item.vat ==""? 0: item.vat); */
                    // this.giablock = this.giablock - (item.gia_block_tiep ==""? 0: item.gia_block_tiep);
                    // this.vatblock = this.vatblock - (item.vat_block_tiep == ""? 0: item.vat_block_tiep); 
                }
            }
        },

        GetDsTbi: async function () {
            //Lấy danh sách thiết bị
            var input = {};
            input.vloaitb_id = this.loaitb_id;
            // this.loading(true);
            await this.axios.post('/web-ccdv/ban_thietbi_ttvt/lay_ds_chonthietbi', {
                vloaitb_id: this.loaitb_id,
                kieu: this.kieu
            }).then((response) => {
                // this.loading(false);
                if (response.data.error_code && response.data.error_code === this.success_code) {
                    this.ds_thietbimua.list = response.data.data;
                    if (this.dulieu.ds && this.dulieu.ds.length > 0) {
                        var ds = [...this.dulieu.ds];
                        this.ds_thietbimua.list.forEach(function (item) {
                            if (ds.filter(x => x.LOAITBI_ID === item.LOAITBI_ID).length > 0) {
                                item.DANGKY = 1
                            } else {
                                item.DANGKY = 0
                            }
                        });
                    }
                }
                else {
                    // this.ShowAlert("Lỗi lấy danh sách thiết bị!", 2);
                }
            }
            ).finally(() => { 
                // this.loading(false); 
            });
        },
    }
}
</script>