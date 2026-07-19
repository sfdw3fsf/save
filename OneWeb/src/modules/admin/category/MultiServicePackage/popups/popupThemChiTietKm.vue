<template>
    <ejs-dialog :enableResize='true' :resizeHandles='resizeHandles' :allowDragging="draggable"
        :header="'Chi tiết khoản mục tính cước: ' + form.ten_goi" showCloseIcon="true" :target="'.main-wrapper'"
        header-class="popup-header" :visible="false" ref="popupThemChiTietKm" :position="{ X: 'center', Y: 'top' }"
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
                        Thông tin gói đa dịch vụ
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">ID thành phần</div>
                                <div class="value">
                                    <input v-model="form.rg_id" type="number" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">Loại hình</div>
                                <div class="value">
                                    <input v-model="form.loaihinh_tb" type="text" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">Tốc độ</div>
                                <div class="value">
                                    <input v-model="form.thuonghieu" type="text" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">Giảm TB</div>
                                <div class="value">
                                    <input v-model="form.giamcuoc_tb" type="number" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">Tiền gói</div>
                                <div class="value">
                                    <input v-model="form.tien_goi" type="number" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">Giảm SD</div>
                                <div class="value">
                                    <input v-model="form.giamcuoc_sd" type="number" class="form-control" />
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
                    <div class="row">
                        <div class="col-sm-12 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">ID nhóm</div>
                                <div class="value">
                                    <input readonly :value="nhom_goi_id" type="number" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">Ngày hiệu lực</div>
                                <div class="value">
                                    <ejs-datepicker format='dd/MM/yyyy' v-model="ngay_bd"
                                        :placeholder="''"></ejs-datepicker>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w100 nowrap">Ngày hết hiệu lực</div>
                                <div class="value">
                                    <ejs-datepicker format='dd/MM/yyyy' v-model="ngay_kt"
                                        :placeholder="''"></ejs-datepicker>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="grid-ext">
                        <ejs-grid :selectedRowIndex="0" :dataBound='dataBound' @queryCellInfo='queryCellInfo' @rowSelected="rowSelected"
                            :actionComplete='onActionComplete' ref='grid' id="Grid1" :dataSource='dataItems' :autoFit='true'
                            :allowResizing='true' :allowTextWrap='true' :textWrapSettings='wrapSettings' height='315px'
                            :editSettings='editSettings' filterTemplate="<span></span>" :filterSettings="{
                                showFilterBarOperator: false,
                                mode: 'Immediate',
                                immediateModeDelay: 500,
                            }" :allowFiltering="true" clipMode="EllipsisWithTooltip" gridLines="Both">
                            <e-columns>
                                <e-column field='KHOANMUCTC_ID' headerText='ID khoản mục' :allowEditing='false'
                                    textAlign='center' :allowFiltering="true" :filter="{ operator: 'contains' }"
                                    width="auto"></e-column>
                                <e-column field='KHOANMUCTC' headerText='Khoản mục' textAlign='center'
                                    editType='dropdownedit' width="400" :allowFiltering="true"
                                    :filter="{ operator: 'contains' }" :edit='kqxlParams'></e-column>
                                <e-column field='NHOM_GOI_ID' headerText='Nhóm gói' textAlign='center' :allowEditing="false"
                                    :allowFiltering="true" :filter="{ operator: 'contains' }" width="auto"></e-column>
                                <e-column field='TIEN_KM' format="N0" headerText='Tiền' textAlign='center'
                                    :allowFiltering="true" :filter="{ operator: 'contains' }" width="auto"></e-column>
                                <e-column field='NGAY_BD' headerText='Ngày bắt đầu' textAlign='center'
                                    :allowFiltering="true" format='dd/MM/yyyy' :allowEditing="false"
                                    :filter="{ operator: 'contains' }" width="auto"></e-column>
                                <e-column field='NGAY_KT' headerText='Ngày kết thúc' textAlign='center'
                                    :allowEditing="false" :allowFiltering="true" format='dd/MM/yyyy'
                                    :edit="editParams" :filter="{ operator: 'contains' }" width=150></e-column>
                                <e-column headerText='Thao tác' width=120 :commands='commands'></e-column>
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
import Pagination from '@/components/Controls/Pagination/index.vue'
import { DropDownList } from "@syncfusion/ej2-dropdowns";
import { getComponent } from "@syncfusion/ej2-base"
import { Query } from '@syncfusion/ej2-data';
import moment from "moment";
let countryElem, countryObj;
var grid;
Vue.use(GridPlugin);
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
            vgoi_id: 0,
            vrg_id: 0,
            tu_ngay: "",
            den_ngay: "",
            nhom_goi_id: "",
            ngay_bd: "",
            ngay_kt: "",
            vdichvuvt_id: 0,
            vloaitb_id: 0,
            dataSource: [],
            toolbar: ['Add'],
            wrapSettings: { wrapMode: 'Content' },
            selected: {
                NHOM_GOI_ID: ""
            },
            rowIndex: 0,
            dmKhoanMuc: [],
            editParams: {
                params: {
                    format: 'dd/MM/yyyy', // specify your desired date format here
                },
            },
            commands: [
                { type: 'Edit', buttonOption: { cssClass: 'e-flat', iconCss: 'e-edit e-icons' } },
                { type: 'Delete', buttonOption: { cssClass: 'e-flat', iconCss: 'e-delete e-icons' } },
                { type: 'Save', buttonOption: { cssClass: 'e-flat', iconCss: 'e-update e-icons' } },
                { type: 'Cancel', buttonOption: { cssClass: 'e-flat', iconCss: 'e-cancel-icon e-icons' } }],
            kqxlParams: {
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
                    grid = new getComponent("Grid1", "grid");
                    var rowData = args.rowData;
                    var rowIndex = grid.getRowInfo(args.row).rowIndex;
                    console.log(args.rowData[args.column.field])
                    countryObj = new DropDownList({
                        dataSource: this.dmKhoanMuc,
                        value: args.rowData['KHOANMUCTC_ID'],
                        fields: { value: 'khoanmuctc_id', text: 'ten_km_id' },
                        placeholder: 'Chọn',
                        floatLabelType: 'Never',
                        change: (item) => {
                            this.dataSource[rowIndex]["KHOANMUCTC_ID"] = item.value;
                            this.dataSource[rowIndex]["KHOANMUCTC"] = countryObj.text;
                        }
                    });
                    countryObj.appendTo(countryElem);
                }
            },
            form: {
                "sl_tu": "",
                "muccuoc_id": "",
                "tocdo_id": "",
                "chon": "",
                "tien_goi": "",
                "tenmuc": "",
                "tg_den": "",
                "chunhom": "",
                "goi_id_datg": "",
                "giamcuoc_sd": "",
                "cuoc_sd": "",
                "dongbo_dl": "",
                "dichvuvt_id": "",
                "rg_id": "",
                "giamcuoc_tb": "",
                "loaihinh_tb": "",
                "ten_goi": "",
                "thuonghieu": "",
                "loaitb_id": "",
                "sl_den": "",
                "cuoc_tb": "",
                "tg_tu": "",
                "vat_goi": "",
            },
            editSettings: { allowEditing: true, allowAdding: true, allowDeleting: true, mode: "Normal", allowEditOnDblClick: false },
            pageSizeData: 10,
            pageIndexData: 0,
        }
    },
    watch: {
        ngay_bd: function (val) {
            grid = new getComponent("Grid1", "grid");
            this.dataSource.map(item => item["NGAY_BD"] = moment(val).isValid() ? moment(val).format("DD/MM/YYYY") : val);
            grid.refresh();
        },
        ngay_kt: function (val) {
            grid = new getComponent("Grid1", "grid");
            this.dataSource.map(item => item["NGAY_KT"] = moment(val).isValid() ?  moment(val).format("DD/MM/YYYY") : val);
            grid.refresh();
        }
    },
    provide: {
        grid: [Edit, Toolbar, CommandColumn]
    },
    methods: {
        queryCellInfo: function(args) {
            if (args.column.headerText == 'Thao tác' && args.cell.querySelector('.e-flat')) {
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
        getTotalRecords: function () {
            let result = this.dataSource == null ? 0 : this.dataSource.length
            return result
        },
        pagination_PageChanged: function (args) {
            this.pageSizeData = args.pageSize
            this.pageIndexData = args.pageIndex
            this.$emit('pageChanged', args)
        },
        rowSelected(args) {
            this.selected = args.data;
            this.rowIndex = args.rowIndex;
        },
        dataBound: function () {
            console.log("dataBound")
            this.$refs.grid.autoFitColumns(['KHOANMUCTC']);
        },
        onActionComplete(args) {
            if (args.requestType == "save") {
                this.dataSource[args.rowIndex]["TIEN_KM"] = args.data.TIEN_KM
                this.dataSource[args.rowIndex]["NGAY_BD"] = args.data.NGAY_BD
                this.dataSource[args.rowIndex]["NGAY_KT"] = args.data.NGAY_KT
                // this.dataSource[args.rowIndex]["KHOANMUCTC_ID"] = args.data.KHOANMUCTC_ID
                // this.dataSource[args.rowIndex]["KHOANMUCTC"] = this.dmKhoanMuc.find(x => x.khoanmuctc_id == args.data.KHOANMUCTC_ID) ? this.dmKhoanMuc.find(x => x.khoanmuctc_id == args.data.KHOANMUCTC_ID).ten_km : ''
                grid.refresh();
            }
            if (args.requestType == "delete") {
                this.dataSource.splice(this.rowIndex, 1);
                grid.refresh();
            }
        },
        onToolbarClick(args) {
            grid = new getComponent("Grid1", "grid");
            let newRow = {
                KHOANMUCTC_ID: 0,
                KHOANMUCTC: "",
                NHOM_GOI_ID: this.nhom_goi_id,
                TIEN_KM: 0,
                NGAY_BD: moment(this.ngay_bd).isValid() ? moment(this.ngay_bd).format("DD/MM/YYYY") : this.ngay_bd,
                NGAY_KT: moment(this.ngay_kt).isValid() ? moment(this.ngay_kt).format("DD/MM/YYYY") : this.ngay_kt
            };
            this.dataSource.unshift(newRow);
            // grid.refresh();
            setTimeout(() => {
                grid.selectRow(0);
                grid.startEdit(0, 0);
            }, 100);
            // grid.selectRow(this.dataSource.length);
            // grid.startEdit(this.dataSource.length - 1);
        },
        async tsbtnChapnhan_Click() {
            this.loading(true)
            if (this.dataSource.length > 0) {
                if (this.ngay_bd == "" || this.ngay_kt == "") {
                    this.$toast.error("Vui lòng nhập ngày bắt đầu và ngày kết thúc")
                    this.loading(false)
                    return
                }
                if (this.dataSource.some(x => !x.KHOANMUCTC_ID)) {
                    this.$toast.error("Vui lòng nhập chi tiết khoản mục")
                    this.loading(false)
                    return
                }
                try {
                    var ob = this.dataSource.map(item => ({
                        ...item,
                        GOI_ID: this.vgoi_id,
                        RG_ID: this.vrg_id,
                        NHOM_GOI_ID: this.nhom_goi_id,
                        NGAY_BD: moment(this.ngay_bd).isValid() ? moment(this.ngay_bd).format("DD/MM/YYYY") : this.ngay_bd,
                        NGAY_KT: moment(this.ngay_bd).isValid()  ? moment(this.ngay_kt).format("DD/MM/YYYY") : this.ngay_kt,
                    }))
                    let objectData = {
                        vkieu: 0,
                        vds: JSON.stringify(ob)
                    }
                    let data = await this.axios.post("web-hopdong/goi-da-dichvu/sp_capnhat_goi_dadv_kmtc",
                        objectData)
                    if (data.data.data == "1") {
                        this.$toast.success("Lưu thành công")
                    } else {
                        this.$toast.error(data.data.data)
                    }
                } catch (error) {
                    this.$toast.error("Lưu không thành công")
                }
            } else {
                this.$confirm('Bạn có chắc chắn muốn xóa khoản mục này?', 'Xác nhận', {
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy',
                    type: 'warning'
                }).then(async () => {
                    try {
                        var ob = [{
                            GOI_ID: this.vgoi_id,
                            RG_ID: this.vrg_id,
                        }]
                        let objectData = {
                            vkieu: 1,
                            vds: JSON.stringify(ob)
                        }
                        let data = await this.axios.post("web-hopdong/goi-da-dichvu/sp_capnhat_goi_dadv_kmtc",
                            objectData)
                        if (data.data.data == "1") {
                            this.$toast.success("Lưu thành công")
                        } else {
                            this.$toast.error(data.data.data)
                        }
                    } catch (error) {
                        this.$toast.error("Lưu không thành công")
                    }
                    this.loading(false)
                }).catch(() => {
                    this.loading(false)
                });
            }
            this.loading(false)
        },
        tsbtnHuyBo_Click() {
            this.$refs.popupThemChiTietKm.hide();
        },
        showDialog() {
            this.init()
        },
        async getDanhsach() {
            return new Promise(async (rs, rj) => {
                try {
                    let data = await this.axios.get("web-hopdong/goi-da-dichvu/sp_ds_goi_dadv_kmtc", {
                        params: {
                            vgoi_id: this.vgoi_id,
                            vrg_id: this.vrg_id
                        }
                    })
                    let temp = data.data.data
                    this.dataSource = temp.map(obj => {
                        return Object.entries(obj).reduce((acc, [key, value]) => {
                            acc[key.toUpperCase()] = value;
                            return acc;
                        }, {});
                    });
                    this.dataSource = this.dataSource.map(item => ({
                        ...item,
                        KHOANMUCTC: this.dmKhoanMuc.find(x => x.khoanmuctc_id == item.KHOANMUCTC_ID) ? this.dmKhoanMuc.find(x => x.khoanmuctc_id == item.KHOANMUCTC_ID).ten_km : '',
                    }))
                    if (this.dataSource.length > 0) {
                        this.ngay_bd = moment(this.dataSource[0].NGAY_BD).isValid() ? moment(this.dataSource[0].NGAY_BD).format("DD/MM/YYYY") : this.dataSource[0].NGAY_BD
                        this.ngay_kt = moment(this.dataSource[0].NGAY_KT).isValid() ? moment(this.dataSource[0].NGAY_KT).format("DD/MM/YYYY") : this.dataSource[0].NGAY_KT
                        this.nhom_goi_id = this.dataSource[0].NHOM_GOI_ID
                    }
                } catch (error) {
                    console.log(error)
                }
                rs(true)
            })
        },
        async init() {
            try {
                this.loading(true)
                let data = await this.axios.get("web-hopdong/goi-da-dichvu/sp_tt_goidadv_theo_rg_id", {
                    params: {
                        vrg_id: this.vrg_id
                    }
                })
                if (data.data.data.length > 0) {
                    this.form = data.data.data[0]
                }
                data = await this.axios.get("web-hopdong/goi-da-dichvu/sp_ds_khoanmuctc_id", {
                    params: {
                        vdichvuvt_id: this.form.dichvuvt_id,
                        vloaitb_id: this.form.loaitb_id
                    }
                })
                this.dmKhoanMuc = data.data.data.map(item => ({
                    ...item,
                    ten_km_id: item.khoanmuctc_id + "_" + item.ten_km
                }))
                await this.getDanhsach()
                console.log(this.dataSource)
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
  
  