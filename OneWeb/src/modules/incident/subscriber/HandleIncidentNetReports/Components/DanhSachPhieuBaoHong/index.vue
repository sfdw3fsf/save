<template>
    <div class="box-form">
        <div class="legend-title">Danh sách phiếu báo hỏng</div>
        <div class="pull-right">
            <div class="list-checks red">
                <div class="item" v-for="rdo in rdoPhieuRadio.options" :key="rdo.value">
                    <div class="check-action">
                        <input type="radio" class="check" :name="rdo.name" checked :value="rdo.value" v-model="rdoPhieuRadio.value">
                        <span class="name">{{ rdo.label }}</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w90">
                        <div class="check-action">
                            <input type="checkbox" class="check"  @change="chkTuNgay_CheckedChanged"
                                v-model="chkTuNgay" />
                            <span class="name">Từ ngày</span>
                        </div>
                    </div>
                    <div class="value">
                        <vue-date format="DD/MM/YYYY" type="datetime" v-model="dtpTuNgay" :disable="!chkTuNgay"></vue-date>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w90">
                        Đến ngày
                    </div>
                    <div class="value">
                        <vue-date format="DD/MM/YYYY" type="datetime" v-model="dtpDenNgay" :disable="!chkTuNgay"></vue-date>
                    </div>
                </div>
            </div>

        </div>
        <div class="table-content">
            <DataGrid ref="gridDSPhieuBaoHong" :columns="gridBaoHong.columns" :dataSource="gridBaoHong.dataSource"
                :showColumnCheckbox="false" :showFilter="true" :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                 @rowSelected="onRowGridBaoHongSelected" :allowPaging="true"
                :enablePagingServer="false" pageSize="50">
            </DataGrid>
        </div>
    </div>
</template>

<script>
import Vue from 'vue'
import api from '../../api.js'
import apiHelper from '../../api.helper.js'
import helper from '../../helper.js';
import moment from "moment";

const daysToSubtract = 120; //so ngay mặc định  get dữ liệu báo hỏng

export default {
    name: 'DanhSachPhieuBaoHong',
    props: {

       
    },
    data() {
        return {
            // dichvuvt_id: 0, 
            huonggiao_id: 0, 
            huonggiao_khdn_id: 0, 
            phieu_tu_vnp: 0,
           

            chkTuNgay: true,
            dtpTuNgay: moment(helper.getDayPast(daysToSubtract)).format("DD/MM/YYYY"),
            dtpDenNgay: moment(new Date()).format("DD/MM/YYYY"),
            gridBaoHong: {
                columns: [
                    { fieldName: "ISCHECK", headerText: "", textAlign: "left", width: "40px", template: this.getCheckboxTemplate(this, 'ISCHECK', 'gridDSPhieuBaoHong') },
                    { fieldName: "ma_tb", headerText: "Số máy/Acc", textAlign: "left", width: "150px" },
                    { fieldName: "ma_lt", headerText: "Số ảo", textAlign: "left", autoFit: "true" },
                    { fieldName: "loaihinh_tb", headerText: "Loại hình", textAlign: "left", autoFit: "true" },
                    { fieldName: "tentinh", headerText: "Tỉnh", textAlign: "left", autoFit: "true" },
                    { fieldName: "ma_bh", headerText: "Mã BH", textAlign: "left", autoFit: "true" },
                    { fieldName: "dienthoai_bh", headerText: "Điện thoại báo hỏng", textAlign: "left", autoFit: "true" },
                    { fieldName: "nguoi_bh", headerText: "Người báo hỏng", textAlign: "left", autoFit: "true" },
                    { fieldName: "ngay_bh", headerText: "Ngày báo hỏng", textAlign: "center", autoFit: "true" },
                    { fieldName: "ghichu_hong", headerText: "Ghi chú BH", textAlign: "left", autoFit: "true" },
                    { fieldName: "tt_giaotinh", headerText: "TT Giao Tỉnh", textAlign: "left", autoFit: "true", visible: true },
                    { fieldName: "ma_lt", headerText: "Mã ERP", textAlign: "left", autoFit: "true", visible: true },//check
                    { fieldName: "nvkt_db", headerText: "Mã BSS", textAlign: "left", autoFit: "true" }, //check
                    { fieldName: "ma_lt", headerText: "Mã VTT", textAlign: "left", autoFit: "true" },//check
                    { fieldName: "ten_tb", headerText: "Tên thuê bao", textAlign: "left", autoFit: "true" },
                    { fieldName: "diachi_ld", headerText: "Địa chỉ LĐ", textAlign: "left", autoFit: "true", visible: true },
                    { fieldName: "diachi_dau", headerText: "Địa chỉ LĐ (Điểm đầu)", textAlign: "left", autoFit: "true", visible: true },
                    { fieldName: "diachi_cuoi", headerText: "Địa chỉ LĐ (Điểm cuối)", textAlign: "left", autoFit: "true", visible: true },

                    { fieldName: "nd_giao", headerText: "Nguyên nhân", textAlign: "left", autoFit: "true", visible: true },
                    { fieldName: "ngaygiao", headerText: "Ngày giao", textAlign: "left", autoFit: "true" },

                ],
                dataSource: [],
                selected: null,
             
            },
            rdoPhieuRadio:{
                options:[{
                id: 'rdoPhieuMoi',
                label: 'Phiếu Mới',
                value: 1,
                name: 'rdoPBHSelected'
            },
            {
                id: 'rdoPhieuDaHoanThanh',
                label: 'Phiếu đã hoàn thàn',
                value: 4,
                name: 'rdoPBHSelected'
            }],
            value: 1 //check mới 1, hoàn thành 4 
            } ,
            lastSelectedItem: null,
          
        }
    },
    computed: {

        tuNgayInputCpt: {
            get() {
                if (this.chkTuNgay) {
                    if (this.dtpTuNgay)
                        return helper.strDDMMYYYY2YYYYMMDD(this.dtpTuNgay)
                }
                return 0;
            },
            set(newValue) {
                this.dtpTuNgay = newValue;
            }

        },
        denNgayInputCpt: {
            get() {
                if (this.chkTuNgay) {
                    if (this.dtpDenNgay)
                        if (this.dtpDenNgay)
                            return helper.strDDMMYYYY2YYYYMMDD(this.dtpDenNgay)
                }
                return 0
            },
            set(newValue) {
                this.dtpDenNgay = newValue;
            }
        }

    },

    methods: {
        onInitComponent(data){

            // this.dichvuvt_id = data.dichvuvt_id;
            this.huonggiao_id = data.huonggiao_id
            this.huonggiao_khdn_id = data.huonggiao_khdn_id
            this.phieu_tu_vnp = data.phieu_tu_vnp;
            console.log(data);
            this.getDanhSachBaoHong()
        },

        async getDanhSachBaoHong() {

            this.lastSelectedItem = null;
            let  nhanvien_id = this.$root.token.getNhanVienID();
            // nhanvien_id = 8556;

            try {
                this.loading(true)
                const downViGiaoId = 0; //hard code = 0
                const params = {
                    vdichvuvt_id: 0,
                    vttbh_id: this.rdoPhieuRadio.value, //1 mới, 4 hoàn thành
                    vnhanvien_id: nhanvien_id, //thong tin nguoi dung
                    vma_tb: 0, // hard code = 0
                    vhuonggiao_id: this.huonggiao_id,
                    vhuonggiao_khdn_id: this.huonggiao_khdn_id,
                    vdonvi_giao_id: downViGiaoId, 
                    vtungay: this.tuNgayInputCpt, 
                    vdenngay: this.denNgayInputCpt, 
                    vphieu_tu_vnp: this.phieu_tu_vnp,
                }

                const response = await api.getDanhSachBaoHongNet(this.axios, params);
                this.gridBaoHong.dataSource = apiHelper.getDataFromResponseApiReturnArray(response);

            } catch (ex) {
                console.log(ex)
                // this.$toast.error(ex)
            } finally {
                this.loading(false)
            }
        },

 

        //function KiemTraGiaoTinh
        async checkHoanThanhPhieu() {

            const response = await api.checkHoanThanhPhieuNet(this.axios);
            return response.data.data == 0

        },

        async chkTuNgay_CheckedChanged(val) {
            await this.getDanhSachBaoHong()
        },

        getCheckboxTemplate(parent, field, grid) {
            return function () {
                return {
                    template: {
                        template: `<div class="template_checkbox"> <input type="checkbox" @change="change" :checked="data[field] == 1" :id="checkBoxId"> </div>`,
                        data: function () {
                            return { data: {}, field }
                        },
                        methods: {
                            async change(event) {

                                const dataUpdate = parent.$refs[grid].dataSource[this.data.index];
                                dataUpdate[field] = event.target.checked ? '1' : '0';
                                 console.log("getCheckboxTemplate")

                                if (event.target.checked == 1) {
                                    
                                    const result = await parent.checkHoanThanhPhieu();
                                    
                                    if (result) {
                                       
                                        if(parent.lastSelectedItem){
                                          const checkBoxElement  =   document.getElementById(`id_${parent.lastSelectedItem.ma_bh}`);
                                            if(checkBoxElement) checkBoxElement.checked = false;
                                            
                                            //update lai data item truoc do
                                            for (let index = 0; index < parent.$refs[grid].dataSource.length; index++) {
                                                const element = parent.$refs[grid].dataSource[index];
                                                if(element.ma_bh == parent.lastSelectedItem.ma_bh){
                                                    element[field] = 0;
                                                    break;
                                                }
                                            }
                                        }
                                        
                                        parent.lastSelectedItem = this.data
                                    }
                                   
                                }else{
                                    if(parent.lastSelectedItem.ma_bh == this.data.ma_bh){
                                        parent.lastSelectedItem = null;  
                                        return;
                                    }
                                }
                            }
                        },
                        computed: {
                            checkBoxId() {
                                return `id_${this.data.ma_bh}`
                            }
                        }
                    }
                }
            }
        },

        onRowGridBaoHongSelected(row) {
            const baoHongItem  = row.data;
          
            if(!baoHongItem) return;
           

            console.log(baoHongItem);
            this.$emit('on-row-selected', baoHongItem)
            
        },
        ExportExcel() {

            if (!this.gridBaoHong.dataSource.length)
                return this.$toast.error("Không có dữ liệu để xuất Excel !");
                helper.exportExcel(this.gridBaoHong.dataSource, "Bao_Hong_NET");
        },
        


    },
    watch: {
        
        rdoPhieuRadio: {
            handler: function (value) {
                this.getDanhSachBaoHong();
                this.$emit('on-check-tinhtrang-phieu', this.rdoPhieuRadio.value)
            },
            deep: true
        },
        gridBaoHong:{
            handler: function (value) {
                // console.log(value.dataSource);
          
                
                this.$emit('get-danh-sach-bao-hong', value.dataSource)
            },
            deep: true
        }
    }


}
</script>