<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDSHopDongCanThuTien"
        :header='headerTemplate'        
        :animationSettings='animationSettings'
        showCloseIcon=true width='80%'
        :target='target'
        :open="dialogOpen"
        :close="dialogClose"
        :isModal="true"
    >
            <div class="">                
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="javascript: void(0)" @click="onSearch" title="Tìm kiếm">
                                <span class="icon one-search"></span> Tìm kiếm
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="onAccept" title="Chấp nhận">
                                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="close" title="Thoát">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title" style="font-weight: 600!important">Thông tin tìm kiếm</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">
                                        <div class="check-action">
                                            <input type="checkbox" checked class="check" v-model="params.loaiHd_Checked">
                                            <span class="name">Loại HĐ/PL</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="">
                                            <select2
                                                :options = "params.loaiHd_Options"
                                                v-model = "params.loaiHd_Selected"
                                                :disabled = "!params.loaiHd_Checked"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">
                                        <div class="check-action">
                                            <input type="checkbox" checked class="check" v-model="params.dichVu_Checked">
                                            <span class="name">Dịch vụ</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="">
                                            <select2
                                                :options = "params.dichVu_Options"
                                                v-model = "params.dichVu_Selected"
                                                :disabled = "!params.dichVu_Checked"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">
                                        <div class="check-action">
                                            <input type="checkbox" checked class="check" v-model="params.loaiHinh_Checked">
                                            <span class="name">Loại hình</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="">
                                            <select2
                                                :options = "params.loaiHinh_Options"
                                                v-model = "params.loaiHinh_Selected"
                                                :disabled = "!params.loaiHinh_Checked"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">
                                        <div class="check-action">
                                            <input type="checkbox" checked class="check" v-model="params.trangThaiHD_Checked">
                                            <span class="name">Trạng thái HĐ</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="">
                                            <select2
                                                :options = "params.trangThaiHD_Options"
                                                v-model = "params.trangThaiHD_Selected"
                                                :disabled = "!params.trangThaiHD_Checked"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key">
                                        Mã giao dịch
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control font-weight-bold" v-model="params.maGD" :disabled="true">
                                    </div>
                                </div>
                                
                                <div class="info-row">
                                    <div class="key">
                                        Số máy/Acc
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control font-weight-bold" v-model="params.soMayAcc" :disabled="true">
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                        
                    </div>
                    <div class="box-form">
                        <div class="legend-title" style="font-weight: 600!important">Danh sách hợp đồng thuê bao</div>
                        <DataGrid v-bind:columns="[
                            {fieldName: 'NGAY_YC', headerText: 'Ngày yêu cầu', allowFiltering: true, allowSorting: false, width: 130, freeze:'left'},
                            {fieldName: 'MA_GD', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting: false, width: 130, freeze:'left'},
                            {fieldName: 'MA_KH', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: false, width: 150, freeze:'left'},
                            {fieldName: 'TEN_KH', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false, width: 150},
                            {fieldName: 'TONGTIEN', headerText: 'Tổng tiền', allowFiltering: true, allowSorting: false, width: 110, cssClass: 'text-danger', format: 'N0', type: 'number'},
                            {fieldName: 'DIACHI_KH', headerText: 'Địa chỉ KH', allowFiltering: true, allowSorting: false, width: 120},
                            {fieldName: 'NHANVIEN_LHD', headerText: 'Người lập HĐ/PL', allowFiltering: true, allowSorting: false, width: 120},
                            {fieldName: 'DONVI_LHD', headerText: 'Đơn vị YC', allowFiltering: true, allowSorting: false, width: 120}                                            
                        ]"
                            v-bind:dataSource="hopDong_grid.items"
                            dataKeyField="MA_GD"
                            :showFilter="true"                    
                            :allowPaging = true
                            :allowResizing = true
                            :enablePagingServer = false
                            @selectedRowChanged="onRowSelected"
                            :key="hopDong_grid.key"
                        >
                        </DataGrid>                        
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<script>
import API from '../../api/ThayDoiKenhThuAPI.js'
import CommonsAPI from '../../api/Commons.js'
import { mapActions, mapGetters } from 'vuex'
import moment from 'moment'
import Vue from 'vue'

export default {
    components: {},
    name: "PopupDSHopDongCanThuTien",

    props: {},

    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter'])
    },

    data() {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            params: {
                loaiHd_Checked: false,
                loaiHd_Options: [],
                loaiHd_Selected: null,
                dichVu_Checked: false,
                dichVu_Options: [],
                dichVu_Selected: null,
                loaiHinh_Checked: false,
                loaiHinh_Options: [],
                loaiHinh_Selected: null,
                trangThaiHD_Checked: true,
                trangThaiHD_Options: [],
                trangThaiHD_Selected: null,
                maGD: '',
                soMayAcc: ''
            },
            common_params: {
                loaiHd_Id: 0,
                dichVu_Id: 0
            },            
            hopDong_grid: {
                items: [],
                selected: {},
                key: 1
            },
            headerTemplate: function () {
                return  { template : Vue.component('headerTp', {
                        template: '<div class="title" style="font-size: 20px;"><span class="icon one-notepad"></span> Danh sách hợp đồng cần thu tiền</div>',
                        data () { return { data: {data: {}}}; }
                    })
                }
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),

        async initData() {
            await this.getDSLoaiHopDong();
            await this.getDSTrangThaiHD();
            if (this.common_params.loaiHd_Id !== 0) {
                this.params.loaiHd_Selected = this.common_params.loaiHd_Id;
                this.params.loaiHd_Checked = true;
            } else 
                this.params.loaiHd_Checked = false;
            this.params.trangThaiHD_Selected = 1; //TrangThaiHD: 1 - MOI

            if (this.danhSachDichVuVienThongGetter === undefined || this.danhSachDichVuVienThongGetter.length === 0) {
                await this.getDanhSachDichVuVienThong();                    
            }
            this.danhSachDichVuVienThongGetter.forEach(item => {                
                this.params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });                
            });
            this.params.dichVu_Selected = this.params.dichVu_Options[0].id;            
        },

        openDialog () {
            this.$refs.dlgDSHopDongCanThuTien.show();
        },

        close() {
            this.params.loaiHd_Checked = false;
            this.params.loaiHd_Selected = null;
            this.params.dichVu_Checked = false;
            this.params.dichVu_Selected = null;
            this.params.loaiHinh_Checked = false;
            this.params.loaiHinh_Selected = null;
            this.params.trangThaiHD_Checked = true;
            this.params.trangThaiHD_Selected = null;
            this.params.maGD = '';
            this.params.soMayAcc = '';
            this.hopDong_grid.items = [];
            this.hopDong_grid.selected = {};
            this.$refs.dlgDSHopDongCanThuTien.hide();
        },

        async dialogOpen() {
            await this.initData()
        },

        dialogClose() {        
        },

        onAccept() {
            if (this.hopDong_grid.items.length <= 0) {
                this.$toast.error('Chưa có dữ liệu trên lưới!');
            } else {
                this.$emit('accept', this.hopDong_grid.selected.MA_GD);
                this.close();
            }
        },

        onRowSelected(e) {
            if (e !== null) {
                this.hopDong_grid.selected = e;
                this.params.maGD = e.MA_GD;
                this.params.soMayAcc = e.MA_TB;
            }            
        },

        async onSearch() {
            await this.getDSHopDongCanThanhToan();
            if (this.hopDong_grid.items.length <= 0)
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
        },       

        async getDSLoaiHopDong() {
            try {
                this.loading(true);                                
                this.params.loaiHd_Options = [];
                let response = await API.getDSLoaiHopDong(this.axios);                
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.loaiHd_Options.push({ id: item.LOAIHD_ID, text: item.TEN_LOAIHD });
                    });
                    this.params.loaiHd_Selected = this.params.loaiHd_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getLoaiHinhTB(id) {
            try {
                this.loading(true);
                this.params.loaiHinh_Options = [];
                let response = await CommonsAPI.getDanhSachLoaiHinhThueBao(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.loaiHinh_Options.push({ id: item.LOAITB_ID, text: item.TEN_LOAIHINH });
                    });
                    this.params.loaiHinh_Selected = this.params.loaiHinh_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSTrangThaiHD() {
            try {
                this.loading(true);
                this.params.trangThaiHD_Options = [];
                let response = await API.getDSTrangThaiHD(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.trangThaiHD_Options.push({ id: item.TTHD_ID, text: item.TRANGTHAI_HD });
                    });
                    this.params.trangThaiHD_Selected = this.params.trangThaiHD_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSHopDongCanThanhToan() {
            try {
                this.loading(true);
                this.hopDong_grid.items = [];
                let apiParams = {
                    dichVuVTId: this.params.dichVu_Checked ? this.params.dichVu_Selected : null,
                    donViId: this.$auth.getDonViID(),
                    loaiHDId: this.params.loaiHd_Checked ? this.params.loaiHd_Selected : null,
                    loaiTBId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : null,
                    nhanVienId: this.$auth.getNhanVienID(),
                    tthdId: this.params.trangThaiHD_Checked ? this.params.trangThaiHD_Selected : null
                };
                let response = await API.getDSHopDongCanThanhToan(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.hopDong_grid.key++;
                    this.hopDong_grid.items = response.data.data.map((item) => ({
                        ...item,
                        TONGTIEN: Number(item.TONGTIEN)
                    }));
                }                
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.dichVu_Selected': async function(newVal, oldVal) {
            if (newVal !== null) {
                await this.getLoaiHinhTB(newVal);
                await this.getDSHopDongCanThanhToan();
            }
        },
        'params.loaiHd_Selected': async function(newVal, oldVal) {
            if (newVal !== null)
                await this.getDSHopDongCanThanhToan();
        }        
    }
}
</script>
