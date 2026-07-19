<template>
    <div class="box-form">
        <div class="legend-title">
            <div class="title pull-left">Danh sách hồ sơ</div>
            <div class="pull-right">
                <button class="btn btn-main padt2 padb3 padl5 padr5" @click="btnReload_Click">
                    <span class="nc-icon-glyph arrows-1_refresh-69"></span>
                </button>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w80">Phân loại</div>
                    <div class="value">
                        <div class="select-custom">
                            <ejs-dropdownlist-tt id="v1_phan_loai"
                                locale="vi-VN" @change="cboPhanLoai_Changed"
                                v-model="model.v1_phan_loai"
                                :dataSource="v1_LstPhanLoai"
                                :allowFiltering="true" :filtering="onFilterV1PhanLoai"
                                :fields="{ value: 'ID', text: 'NAME' }"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w80">Trạng thái</div>
                    <div class="value">
                        <div class="select-custom">
                            <ejs-dropdownlist-tt id="v1_trang_thai"
                                locale="vi-VN" @change="cboTrangThai_Changed"
                                v-model="model.v1_trang_thai"
                                :dataSource="v1_LstTrangThai"
                                :allowFiltering="true" :filtering="onFilterV1TrangThai"
                                :fields="{ value: 'ID', text: 'NAME' }"
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-12">
                <div>
                    <div class="info-row">
                        <div class="key w80">Từ ngày</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <vue-date format="DD/MM/YYYY" type="datetime" v-model="model.v1_tu_ngay" id="v1_tu_ngay"></vue-date>
                            </div>
                        </div>
                    </div>          
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w80">Đến ngày</div>
                    <div class="value">
                        <div class="input-icon-right">
                            <vue-date format="DD/MM/YYYY" type="datetime" v-model="model.v1_den_ngay" id="v1_den_ngay"></vue-date>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-content" :style="model.v1_phan_loai == '0' ? 'display: none' : ''">
            <DataGrid
                  v-bind:columns="collumnsDSHoSo"
                  v-bind:dataSource="v1_dtDSHoSo"
                  :showColumnCheckbox="false"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                  panelDataHeight="300"
                  @rowSelected="gridViewTiepNhan_FocusedRowChanged"
                  :allowPaging="true" :enablePagingServer="false"
              ></DataGrid>  
        </div>
        <div class="table-content" :style="model.v1_phan_loai != '0' ? 'display: none' : ''">
            <DataGrid
                  v-bind:columns="collumnsDSHoSoDD"
                  v-bind:dataSource="v1_dtDSHoSo"
                  :showColumnCheckbox="false"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                  panelDataHeight="300"
                  @rowSelected="gridViewTiepNhan_FocusedRowChanged"
                  :allowPaging="true" :enablePagingServer="false"
              ></DataGrid>  
        </div>
    </div>
</template>
<script>
import { Query } from '@syncfusion/ej2-data'
import moment from "moment";
import DataGridCustom from "./components/DataGrid";
import collums from "./define/collums.js";

export default {
    name:'DanhSachHoSo',
    components:{
        DataGridCustom
    },
    data() {
        return {
            ...collums,
            model: {
                v1_phan_loai: "",
                v1_trang_thai: "",
                v1_tu_ngay: "",
                v1_den_ngay: ""
            },
            v1_LstPhanLoai: [
                {ID:"0", NAME: "Hồ sơ di động"},
                {ID:"1", NAME: "Hồ sơ mạng Fix"},
                {ID:"2", NAME: "Tư vấn CTV"},
                {ID:"3", NAME: "Trả trước - đổi tốc độ"},
            ],
            v1_LstTrangThai: [
                {ID:"1", NAME: "Phiếu mới"},
                {ID:"3", NAME: "Phiếu đã trả lại"},
                {ID:"2", NAME: "Phiếu đã tiếp nhận"},
                {ID:"4", NAME: "Phiếu đã hoàn thành"},
            ],
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            v1_dtDSHoSo: [],
        }
    },
    computed: {

    },
    methods: {
        onFilterV1PhanLoai: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.v1_LstPhanLoai, query);
        },
        onFilterV1TrangThai: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.v1_LstTrangThai, query);
        },
        btnReload_Click() {
            this.$emit('NapDanhSach');
        },
        cboPhanLoai_Changed() {
            this.$emit('NapDanhSach');
        },
        cboTrangThai_Changed() {
            this.$emit('NapDanhSach');
        },
        gridViewTiepNhan_FocusedRowChanged(data) {
            if (data.data) {
                this.$emit('gridViewTiepNhan_FocusedRowChanged', data.data);   
            }
        }
    },
    mounted() {
        this.model.v1_phan_loai = "0";
        this.model.v1_trang_thai = "1";
    },
}
</script>
<style>
    .info-row .value {
        padding-left: 0px !important;
    }
</style>