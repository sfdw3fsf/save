<template>
    <ejs-dialog :header="'Chi tiết khoản mục cho chi tiết khuyến mãi: ' + form.ten_ctkm" 
        showCloseIcon="true"  
        :target="'.main-wrapper'"
        header-class="popup-header" :visible="false" ref="popupThemChiTietKm"
        :enableResize='true' :resizeHandles='resizeHandles' :allowDragging="draggable"
        :position="{ X: 'center', Y: 'top' }"
        width="80%" id="popupThemChiTietKm">
        <div class="popup-content">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a title="Chấp nhận" ref="tsbtnNhapMoi" @click="tsbtnChapnhan_Click"> <span
                                class="icon one-file-attach"></span> Ghi lại </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">
                        Thông tin chi tiết khuyến mãi
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">ID_CT khuyến mãi</div>
                                <div class="value">
                                    <input v-model="form.chitietkm_id" type="number" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Tên CTKM</div>
                                <div class="value">
                                    <input v-model="form.ten_ctkm" type="text" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Tiền SD</div>
                                <div class="value">
                                    <input v-model="form.tien_sd" type="text" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Tiền TB</div>
                                <div class="value">
                                    <input v-model="form.tien_tb" type="number" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Tỷ lệ SD</div>
                                <div class="value">
                                    <input v-model="form.tyle_sd" type="number" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Tỷ lệ TB</div>
                                <div class="value">
                                    <input v-model="form.tyle_tb" type="number" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Cước TB</div>
                                <div class="value">
                                    <input v-model="form.cuoc_tb" type="number" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Cước SD</div>
                                <div class="value">
                                    <input v-model="form.cuoc_sd" type="number" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Tiền đặt cọc</div>
                                <div class="value">
                                    <input v-model="form.tien_ck" type="number" :format="formatPrice" class="form-control disabled"  />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w120 nowrap">Tiền trừ dần</div>
                                <div class="value">
                                    <input v-model="form.tien_td" type="number" :format="formatPrice" class="form-control disabled" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                     <div class="legend-title">
                        <div class="pull-left">
                            Chi tiết khoản mục
                        </div>
                        <div class="pull-right">
                            <button class="btn btn-success btn-sm float-right mb-2" @click="onToolbarClick">+</button>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="grid-ext">
                        <ejs-grid :selectedRowIndex="0" :dataBound='dataBound' @queryCellInfo='queryCellInfo' :actionComplete='onActionComplete' @rowSelected="rowSelected" @commandClick="handleCommandClick"
                                ref='grid' id="Grid2" :dataSource='dataItems'
                            :editSettings='editSettings' filterTemplate="<span></span>" :filterSettings="{
                                showFilterBarOperator: false,
                                mode: 'Immediate',
                                immediateModeDelay: 500, 
                            }" :allowFiltering="true"  :autoFit='true' :allowResizing='true'
                            :allowTextWrap='true' :textWrapSettings='wrapSettings' 
                            height='283' clipMode="EllipsisWithTooltip" gridLines="Both">
                            <e-columns>
                                <e-column field='khoanmuctc_id' headerText='ID khoản mục' :allowEditing='false' width="80"
                                    textAlign='center' :allowFiltering="true" :filter="{ operator: 'contains' }"
                                    ></e-column>
                                <e-column field='ten_km' headerText='Khoản mục' textAlign='left' width="200"
                                    editType='dropdownedit' :allowFiltering="false" :filter="{ operator: 'contains' }"
                                    :edit='kqxlParams'></e-column>
                               <e-column field='tien_km' headerText='Tiền khoản mục' textAlign='center' :allowFiltering="true"
                                    :filter="{ operator: 'contains' }" width="auto"></e-column>
                                <e-column field='cuoc_km' headerText='Cước khoản mục' textAlign='center' :allowFiltering="true"
                                    :filter="{ operator: 'contains' }" width="auto"></e-column>
                                <e-column field='tyle_km' headerText='Tỷ lệ khoản mục' textAlign='center' :allowFiltering="true"
                                    :filter="{ operator: 'contains' }" width="auto"></e-column>
                                <e-column field='tien_td' headerText='Tiền trừ dần khoản mục' textAlign='center' :allowFiltering="true"
                                    :filter="{ operator: 'contains' }" width="auto"></e-column>
                                <e-column headerText='Hành động' width="120" :commands='commands'></e-column>
                            </e-columns>
                        </ejs-grid> 
                         <Pagination ref="pagination" :pageSize="10" :pageIndex="0" :totalRecords="dataSource.length"
	
                            @pageChanged="pagination_PageChanged"></Pagination>
                    </div>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<script>
import Vue from "vue";
import { GridPlugin, Toolbar, Filter, Edit, CommandColumn } from "@syncfusion/ej2-vue-grids";
import { DropDownList } from "@syncfusion/ej2-dropdowns";
import { getComponent } from "@syncfusion/ej2-base"
import { Query } from '@syncfusion/ej2-data';
import { DialogPlugin } from '@syncfusion/ej2-vue-popups';
import moment from "moment";
import Pagination from '@/components/Controls/Pagination/index.vue'
let countryElem, countryObj;
var grid;
Vue.use(GridPlugin, DialogPlugin);
export default {
    components: {
        Pagination
    },
    computed: {
        dataItems: function () {
            return this.dataSource.slice(this.pageIndexData * this.pageSizeData, this.pageIndexData * this.pageSizeData + this.pageSizeData)
        }
    }, 
    data() {
        return {
            resizeHandles: ['All'],
            draggable: true,
            wrapSettings: { wrapMode: 'Content' },
            vctkm_id: 0,
            vkhuyenmai_id: 0,
            sua: false,
            tu_ngay: "",
            den_ngay: "",
            vdichvuvt_id: 0,
            vloaitb_id: 0,
            rowIndex: 0,
            dataSource: [],
            toolbar: ['Add'],
            dmKhoanMuc: [],
            editParams: {
                params: {
                    format: 'dd/MM/yyyy HH:mm:ss', // specify your desired date format here
                },
            }, 
            commands: [
                { type: 'Edit', buttonOption: { cssClass: 'e-flat e-green-button', iconCss: 'e-edit e-icons',tooltipText: 'Sửa' } },
                { type: 'Delete', buttonOption: { cssClass: 'e-flat e-green-button', iconCss: 'e-delete e-icons',tooltipText: 'Xóa dòng' } },
                { type: 'Save', buttonOption: { cssClass: 'e-flat e-green-button', iconCss: 'e-update e-icons' ,tooltipText: 'Lưu'} },
                { type: 'Cancel', buttonOption: { cssClass: 'e-flat e-green-button', iconCss: 'e-cancel-icon e-icons',tooltipText: 'Hủy' } }],
            kqxlParams: {
                edit: true,
                create: () => {
                    countryElem = document.createElement('input');
                    return countryElem;
                },
                read: () => {
                    return countryObj.text;
                },
                destroy: () => {
                    countryObj.destroy();
                },
                write: (args) => {
                    grid = new getComponent("Grid2", "grid");
                    var rowData = args.rowData;
                    var rowIndex = grid.getRowInfo(args.row).rowIndex;
                    
                    console.log(args.rowData[args.column.field])
                    countryObj = new DropDownList({
                        dataSource: this.dmKhoanMuc,
                        value: args.rowData['khoanmuctc_id'],
                        fields: { value: 'khoanmuctc_id', text: 'ten_km' },
                        placeholder: 'Chọn',
                        allowFiltering: true,
                        floatLabelType: 'Never',
                        change: (item) => {
                            console.log( this.dataSource)
                            console.log(item.value)
                            console.log(rowIndex)
                            console.log(countryObj)
                            this.dataSource[rowIndex].khoanmuctc_id = item.value;
                            this.dataSource[rowIndex]["ten_km"] = countryObj.text;
                            console.log(111111)
                            console.log(this.dataSource);
                            grid.refresh();
                            this.$nextTick(() => {
                                  setTimeout(() => {
                                    grid.selectRow(0);
                                    grid.startEdit(0, 0);
                                }, 100);
                            });
                            
                        }
                    });
                    countryObj.appendTo(countryElem);
                }
            },
            form: {
                "khoanmuctc_id": "",
                "ten_km": "",
                "tien_km": "",
                "chon": "",
                "cuoc_km": "",
                "tyle_km": "",
                "tien_td_km": ""
            },
            editSettings: { allowEditing: true, allowAdding: true, allowDeleting: false, mode: "Normal" },
            pageSizeData: 10,
            pageIndexData: 0,
        }
    },
    provide: {
        grid: [Edit, Toolbar, CommandColumn]
    },
    methods: {
         queryCellInfo: function(args) {
            if (args.column.headerText == 'Hành động' && args.cell.querySelector('.e-flat')) {
                setTimeout(() => {
                    this.$refs.grid.ej2Instances.element.querySelectorAll('.e-save-cancel.e-savebutton').forEach(function(element) {
                        element.setAttribute('title', 'Lưu');
                    });
                    this.$refs.grid.ej2Instances.element.querySelectorAll('.e-editbutton').forEach(function(element) {
                        element.setAttribute('title', 'Sửa');
                    });
                    this.$refs.grid.ej2Instances.element.querySelectorAll('.e-cancelbutton').forEach(function(element) {
                        element.setAttribute('title', 'Hủy');
                    });
                    this.$refs.grid.ej2Instances.element.querySelectorAll('.e-deletebutton').forEach(function(element) {
                        element.setAttribute('title', 'Xóa');
                    });
                    
                    // args.cell.querySelector('.e-cancel-icon').setAttribute('title', 'Hủy');
                }, 100);
            }
        },
        async handleCommandClick(args) {
            console.log(334343)
            console.log(args)
            if (args.commandColumn.type == 'Delete') {
                 console.log(334343)
                 const result = await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa khoản mục này ra khỏi danh sách ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                });
                console.log(22222222)
                if (result) {
                     console.log(333333)
                    this.dataSource.splice(this.rowIndex, 1);
                    this.$refs.grid.refresh();
                }
                 console.log(55555)
            }
        },
        async onActionComplete(args) {
            console.log(args.requestType)
            if (args.requestType == "edit") {
                this.sua = true
            }
            if (args.requestType == "save") {
                console.log(1111111111)
                 console.log(args.data)
                 console.log(args.data.khoanmuctc_id)
                if (args.data.khoanmuctc_id !== undefined && args.data.khoanmuctc_id !== null){
                    console.log(2222222)
                    const newKhoanmuctcId = args.data.khoanmuctc_id;
                    const isDuplicate = this.dataSource.filter(item => item.khoanmuctc_id === newKhoanmuctcId);
                    if (isDuplicate&&isDuplicate.length > 1) {
                        this.$toast.warning("Khoản mục đã tồn tại. Vui lòng chọn khoản mục khác.");
                        setTimeout(() => {
                                    grid.selectRow(0);
                                    grid.startEdit(0, 0);
                        }, 100);
                    } else {
                        this.dataSource[args.rowIndex]["khoanmuctc_id"] = newKhoanmuctcId;
                    }
                }
                if (args.data.tien_km) 
                    this.dataSource[args.rowIndex]["tien_km"] = args.data.tien_km
                if (args.data.cuoc_km)
                    this.dataSource[args.rowIndex]["cuoc_km"] = args.data.cuoc_km
                if (args.data.tyle_km)
                    this.dataSource[args.rowIndex]["tyle_km"] = args.data.tyle_km
                if (args.data.tien_td)
                    this.dataSource[args.rowIndex]["tien_td"] = args.data.tien_td
                console.log(this.dataSource)
                grid.refresh();
            }
        },
        onToolbarClick(args) {
            grid = new getComponent("Grid2", "grid");
            let newRow = { 
                khoanmuctc_id: 0,
                ten_km: "",
                tien_km: 0,
                cuoc_km: 0,
                tyle_km: 0,
                tien_td: 0
            };
            this.dataSource.unshift(newRow);
            if(this.dataSource.length > 0){
                setTimeout(() => {
                    grid.selectRow(0);
                    grid.startEdit(0, 0);
                }, 100);
            }
            
  
        },
        rowSelected(args) {
            this.rowIndex = args.rowIndex;
        },
        getTotalRecords: function () {
            let result = this.dataSource == null ? 0 : this.dataSource.length
            return result
        },
	
        pagination_PageChanged: function (args) {
	
            this.pageSizeData = args.pageSize
	
            this.pageIndexData = args.pageIndex
	
            this.$emit('pageChanged', args)
        },
        dataBound: function () {
            console.log("dataBound")
	
            this.$refs.grid.autoFitColumns(['khoanmuctc']);
	
        },
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        },
        async tsbtnChapnhan_Click() {
            try {
                 this.$bvModal.msgBoxConfirm('Bạn thật sự muốn cập nhật chi tiết khoản không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                        var ob = this.dataSource.map(item => ({ ...item }));
                        console.log(44444444)
                        console.log(ob)
                        if(ob && ob.length > 0){
                            const sum = ob.reduce((accumulator, currentValue) => {
                            accumulator.tien_km += currentValue.tien_km;
                            accumulator.cuoc_km += currentValue.cuoc_km;
                            accumulator.tien_td += currentValue.tien_td;
                            accumulator.tyle_km += currentValue.tyle_km;
                            return accumulator;
                            }, { tien_km: 0, cuoc_km: 0, tien_td: 0, tyle_km: 0 });
                            console.log(sum) 
                            if((this.form.tien_sd > 0 || this.form.tien_tb > 0) && this.form.tien_td > 0){
                                console.log('t1111')
                                if (this.form.tien_sd > 0){
                                    if(sum.tien_km != this.form.tien_sd && sum.tien_td > this.form.tien_td){
                                        this.$toast.warning("Tổng [Tiền khoản mục] = [Tiền SD] hoặc [Tiền TB] của chi tiết khuyến mại; \n Hoặc: Tổng [Tiền trừ dần khoản mục] <= [Tiền trừ dần] của chi tiết khuyến mại ");
                                        return;
                                    }
                                } else if (this.form.tien_tb > 0){
                                    if(sum.tien_km != this.form.tien_tb && sum.tien_td > this.form.tien_td){
                                        this.$toast.warning("Tổng [Tiền khoản mục] = [Tiền SD] hoặc [Tiền TB] của chi tiết khuyến mại; \n Hoặc: Tổng [Tiền trừ dần khoản mục] <= [Tiền trừ dần] của chi tiết khuyến mại ");
                                        return;
                                    }
                                }
                                
                            }else{
                                console.log('t11112222')
                                if(sum.tien_km != this.form.tien_sd && sum.tien_km != this.form.tien_tb){
                                    this.$toast.warning("Tổng [Tiền khoản mục] = [Tiền SD] hoặc [Tiền TB] của chi tiết khuyến mại");
                                    return;
                                } 
                                if(sum.tien_td > this.form.tien_td){
                                    this.$toast.warning("Tổng [Tiền trừ dần khoản mục] <= [Tiền trừ dần] của chi tiết khuyến mại");
                                    return;
                                }
                            }
                            console.log('t1111222233333')
                            if(sum.cuoc_km != this.form.cuoc_sd && sum.cuoc_km != this.form.cuoc_tb){
                                    this.$toast.warning("Tổng [Cước khoản mục] = [Cước SD] hoặc [Cước TB] của chi tiết khuyến mại");
                                    return;
                            }
                            if(sum.tyle_km != this.form.tyle_sd && sum.tyle_km != this.form.tyle_tb){
                                    this.$toast.warning("Tổng [Tỷ lệ khoản mục]= [Tỷ lệ SD] hoặc [Tỷ lệ TB] của chi tiết khuyến mại");
                                    return;
                            }
                            
                        }
                        let khoanmuctcIds = {}; 
                        for (const item of ob) {
                            const khoanmuctc_id = item.khoanmuctc_id;
                            if(khoanmuctc_id == 0){
                                this.$toast.error("Khoản mục không thể để trống");
                                return;
                            }
                            if (khoanmuctcIds[khoanmuctc_id]) {
                                this.$toast.error("Có khoản mục trùng nhau trong danh sách.");
                                return;
                            }
                            khoanmuctcIds[khoanmuctc_id] = true;
                        }
                        let data = await this.axios.post("web-quantri/khuyenmai/capnhat_chitiet_km_khoanmuc_tc", {
                            vchitietkm_id: this.vctkm_id,
                            vjson_ct_khoanmuc: JSON.stringify(ob)
                        });
                        if (data.data.data == "ok") {
                            this.$toast.success("Lưu thành công");
                        } else {
                            this.$toast.error(data.data.data);
                        }
                    }
                });
            } catch (error) {
                this.$toast.error("Lưu không thành công")
            }
        },
        tsbtnHuyBo_Click() {
            this.$refs.popupThemChiTietKm.hide();
        },
        showDialog() {
            this.init()
        },
        async init() {
            try {
                this.loading(true)
                let data = await this.axios.get("web-quantri/khuyenmai/lay_ctkm_theo_khuyenmai_id", {
                    params: {
                        vchitietkm_id: this.vctkm_id,
                        vloaitb_id: 0
                    }
                })
                this.dataSource = data.data.data
                let ct_km = await this.axios.post("web-hopdong/laydulieu/sp_tt_ct_khuyenmai", {
                    "p_param": this.vkhuyenmai_id, 
                    "p_type":1
                })
                let item = ct_km.data.data.find((x) => x.chitietkm_id == this.vctkm_id)
                if (item) {
                    this.form = item
                }
                data = await this.axios.get("web-quantri/khuyenmai/lay_ctkm_theo_khuyenmai_id", {
                    params: {
                        vchitietkm_id: 1,
                        vloaitb_id: 0
                    }
                })
                this.dmKhoanMuc = data.data.data
                this.$refs.popupThemChiTietKm.show();
                this.loading(false)
            } catch (error) {
                console.log(error)
            }
        }
    }
}
</script>
<style>
.error {
    border-color: red !important;
}
.e-btn.e-flat,
.e-css.e-btn.e-flat {
    background-color: #198754;
    border-color: #198754;
}
.e-toolbar .e-tbar-btn-text,
.e-toolbar .e-toolbar-items .e-toolbar-item .e-tbar-btn-text {
    display: none !important;
}
</style>
  
  