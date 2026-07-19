<template>
    <div class="box-form">
        <div class="legend-title">
            Thông tin thuê bao khách hàng
        </div>
        <div class="nav tab-over tabs">
      
            <a v-show="tap_ob_id == 1" href="#tb-mo-chem" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 1"
                :class="tab_active == 1 ? 'minw0 active' : 'minw0'">TB Mở chêm</a>
                
            <a v-show="tap_ob_id == 2" href="#tb-mo-chem" data-toggle="tab" style="min-width: 200px !important;"  @click="tab_active = 1"
                :class="tab_active == 1 ? 'minw0 active' : 'minw0'">Kết quả phiếu CSKH
            </a>

            <a href="#tb-tra-thang-hien-tai" data-toggle="tab" style="min-width: 200px !important;"  @click="tab_active = 2"
                :class="tab_active == 2 ? 'minw0 active' : 'minw0'">TB KTĐC tại tháng hiện tại</a>

            <a href="#tb-tra-thang-ke-tiep" data-toggle="tab" style="min-width: 200px !important;"  @click="tab_active = 3"
                :class="tab_active == 3 ? 'minw0 active' : 'minw0'">TB KTĐC tại tháng kế tiếp</a>

            <a href="#tb-tra-sau" data-toggle="tab" style="min-width: 200px !important;"  @click="tab_active = 4"
                :class="tab_active == 4 ? 'minw0 active' : 'minw0'">D/S TB trả sau có
                thể tham gia trả trước</a>
            <a href="#hddc-chua-thanh-toan" data-toggle="tab" style="min-width: 200px !important;"  @click="tab_active = 5"
                :class="tab_active == 5 ? 'minw0 active' : 'minw0'">D/S HDDC chưa thanh toán</a>

        </div>
        <div class="tab-content">
            <div :class="tab_active == 1 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-mo-chem">
                <!-- D/S TB mở chêm -->
                <DataGrid :dataSource="gridTBDC_MoChem.dataSources" :allowPaging="true" ref="refGridTBMoChem" v-show="tap_ob_id == 1"
                    :columns="gridTBDC_MoChem.columns" :allowSorting="true" :enablePagingServer="false"
                    panelDataHeight="220px" :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB" />

                 <DataGrid :dataSource="gridTB_KetQua_CSKH_NVKT.dataSources" :allowPaging="true" ref="refGridgKetQuaCSKHNVKT" v-show="tap_ob_id == 2"
                    :columns="gridTB_KetQua_CSKH_NVKT.columns" :allowSorting="true" :enablePagingServer="false"
                    panelDataHeight="220px" :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB" /> 

            </div>

            <div :class="tab_active == 2 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-thang-hien-tai">
                <!-- DS TB tháng hiện tại -->
                <DataGrid :dataSource="gridTB_HienTai.dataSources" :allowPaging="true" :columns="gridTB_HienTai.columns"
                    ref="refGridTBHienTai" :allowSorting="true" :enablePagingServer="false" panelDataHeight="220px"
                    :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB" />
            </div>
            <div :class="tab_active == 3 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-thang-ke-tiep">
                <!-- DS TB tháng kế tiếp-->
                <DataGrid :dataSource="gridTB_KeTiep.dataSources" :allowPaging="true" :columns="gridTB_KeTiep.columns"
                    ref="refGridTBKeTiep" :allowSorting="true" :enablePagingServer="false" panelDataHeight="220px"
                    :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB" />
            </div>



            <div :class="tab_active == 4 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-sau">
                <!-- D/S TB trả sau có thể tham gia trả trước -->
                <DataGrid :dataSource="gridTB_TraSau.dataSources" :allowPaging="true" :columns="gridTB_TraSau.columns"
                    ref="refGridTBTraSau" :allowSorting="true" :enablePagingServer="false" panelDataHeight="220px"
                    :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB" />
            </div>
            <div :class="tab_active == 5 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="hddc-chua-thanh-toan">
                <!-- D/S HDDC chua thanh toan -->
                <HDChuaThanhToanComponent :khachhang_id="khachhang_id"/>
            </div>
        </div>
    </div>
</template>

<script>
import api from '../../../API';
import apiHelper from '../../../api.helper';
import HDChuaThanhToanComponent from '../ThongTinKhachHang/HDChuaThanhToanComponent.vue'
import moment from 'moment'
export default {
    components:{
        HDChuaThanhToanComponent
    },
    name: 'ThongTinTBKhachHang',
    props: {
        

        khachhang_id: {
            type: Number,
            default: 0,
            require: true
        },

        tracuuOB: {
            type: Object,
            default(){
              return{ 
                vkieu: 1,
                vtungay: 0,
                vdenngay: 0} 
            }
        },
     

    },

    data() {
        return {
            gridTBDC_MoChem: {
                columns: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', width: '130px' },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', width: '150px' },
                    { fieldName: 'ma_tt', headerText: 'Mã TT', width: '150px' },
                    { fieldName: 'so_dt', headerText: 'SĐT TT', width: '100px' },
                    { fieldName: 'diachi', headerText: 'Địa chỉ TB', width: '100px' },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', width: '120px' },
                    { fieldName: 'loai_chem', headerText: 'Loại chêm', width: '120px' },
                    { fieldName: 'ngay_chem', headerText: 'Ngày chêm', width: '120px' },
                    { fieldName: 'ngay_mo_chem', headerText: 'Ngày mở chêm', width: '120px' },
                    { fieldName: 'ma_hd_cht', headerText: 'Mã HĐ chưa HT', width: '120px' },

                ],
                dataSources: []
            },
            gridTB_HienTai: {
                columns: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', width: '130px' },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', width: '150px' },
                    { fieldName: 'so_dt', headerText: 'SĐT KH', width: '100px' },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', width: '120px' },
                    { fieldName: 'tocdo', headerText: 'Tên tốc độ', width: '120px' },

                    { fieldName: 'chi_tiet_tt', headerText: 'Chi tiết TT', width: '120px' },
                    { fieldName: 'sl_datcoc', headerText: 'Số tháng', width: '120px' },
                    { fieldName: 'ngay_bddc', headerText: 'Ngày BDĐC', width: '120px' },
                    { fieldName: 'ngay_ktdc', headerText: 'Ngày KTĐC', width: '120px' },
                    { fieldName: 'cuoc_ts', headerText: 'Cước TS', width: '120px' },
                    { fieldName: 'tt_gia_han', headerText: 'TT gia hạn', width: '120px' },
                    { fieldName: 'khuyennghi', headerText: 'Khuyến nghị', width: '120px' },
                    { fieldName: 'tu_van', headerText: 'Tư vấn', width: '120px' }
                ],
                dataSources: []
            },
            gridTB_KeTiep: {
                columns: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', width: '130px' },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', width: '150px' },
                    { fieldName: 'so_dt', headerText: 'SĐT KH', width: '100px' },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', width: '120px' },
                    { fieldName: 'tocdo', headerText: 'Tên tốc độ', width: '120px' },

                    { fieldName: 'chi_tiet_tt', headerText: 'Chi tiết TT', width: '120px' },
                    { fieldName: 'sl_datcoc', headerText: 'Số tháng', width: '120px' },
                    { fieldName: 'ngay_bddc', headerText: 'Ngày BDĐC', width: '120px' },
                    { fieldName: 'ngay_ktdc', headerText: 'Ngày KTĐC', width: '120px' },
                    { fieldName: 'cuoc_ts', headerText: 'Cước TS', width: '120px' },
                    { fieldName: 'tt_gia_han', headerText: 'TT gia hạn', width: '120px' },
                    { fieldName: 'khuyennghi', headerText: 'Khuyến nghị', width: '120px' },
                    { fieldName: 'tu_van', headerText: 'Tư vấn', width: '120px' }
                ],
                dataSources: []
            },
            gridTB_TraSau: {
                columns: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', width: '130px' },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', width: '150px' },
                    { fieldName: 'so_dt', headerText: 'SĐT KH', width: '100px' },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', width: '120px' },
                    { fieldName: 'tocdo', headerText: 'Tên tốc độ', width: '120px' },

                    { fieldName: 'chi_tiet_tt', headerText: 'Chi tiết TT', width: '120px' },
                    { fieldName: 'sl_datcoc', headerText: 'Số tháng', width: '120px' },
                    { fieldName: 'ngay_bddc', headerText: 'Ngày BDĐC', width: '120px' },
                    { fieldName: 'ngay_ktdc', headerText: 'Ngày KTĐC', width: '120px' },
                    { fieldName: 'cuoc_ts', headerText: 'Cước TS', width: '120px' },
                    { fieldName: 'tt_gia_han', headerText: 'TT gia hạn', width: '120px' },
                    { fieldName: 'khuyennghi', headerText: 'Khuyến nghị', width: '120px' },
                    { fieldName: 'tu_van', headerText: 'Tư vấn', width: '120px' }
                ],
                dataSources: []
            },
            gridTB_KetQua_CSKH_NVKT: {
                columns: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', width: '130px' },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', width: '150px' },
                    { fieldName: 'so_dt', headerText: 'SĐT KH', width: '100px' },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', width: '120px' },
                    { fieldName: 'ketqua_ob', headerText: 'KQ phản hồi của NVKT', width: '200px' },
                    { fieldName: 'ghi_chu', headerText: 'Ghi chú', width: '120px' },
                    { fieldName: 'ngay_dieu', headerText: 'Ngày điều phiêu', width: '120px' },
                    { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', width: '120px' },
                    { fieldName: 'nhanvien_th', headerText: 'Nhân viên TH', width: '120px' },

                ],
                dataSources: []
            },
            tab_active: 1,

            tap_ob_id: 1,
            thang_kt: moment().format('YYYYMM').toString() ,

        }
    },
    methods: {
       

        onSelectRow_DSTB() {
            const tbdc_selected = this.$refs.refGridTBMoChem.grid.getSelectedRecords();
            const tblechky_selected = this.$refs.refGridTBHienTai.grid.getSelectedRecords();
            const tbtrasau_selected = this.$refs.refGridTBTraSau.grid.getSelectedRecords();
            const csnv_selected = this.$refs.refGridgKetQuaCSKHNVKT.grid.getSelectedRecords();

            let list_item_selected = [].concat(tbdc_selected, tblechky_selected, tbtrasau_selected, csnv_selected);
            if (list_item_selected.length > 0) {
                this.$emit("on-select-row", list_item_selected);
            }

        },
        
        
        async getListThueBao_HienTai(){
            try {
            if(!this.khachhang_id || !this.thang_kt) return;
            this.gridTB_HienTai.dataSources = []
            let res = await api.getListThueBaoDCTT(this.axios, this.khachhang_id, this.thang_kt);
            this.gridTB_HienTai.dataSources = apiHelper.getDataFromResponseApiReturnArray(res);
          
            

            } catch (error) {
                console.log(error);
            }
        },


        async getListThueBao_KeTiep() {

            try {
                if(!this.khachhang_id || !this.thang_kt) return;
                this.gridTB_KeTiep.dataSources = []
                let res = await api.getListThueBaoDCLK(this.axios, this.khachhang_id, this.thang_kt);
                this.gridTB_KeTiep.dataSources = apiHelper.getDataFromResponseApiReturnArray(res)
              
            } catch (error) {
                console.log(error);
            }
        },
        async getListThueBao_TraSau() {

            try {
                if(!this.khachhang_id || !this.thang_kt) return;

                this.gridTB_TraSau.dataSources = []

                let res = await api.getListThueBaoTS(this.axios, this.khachhang_id, this.thang_kt);
                this.gridTB_TraSau.dataSources = apiHelper.getDataFromResponseApiReturnArray(res);
                
         
            } catch (error) {
                console.log(error);
            }
        },

        async getListResult_OB_NhanCong() {

            try {
                if(!this.tracuuOB?.vkieu || !this.khachhang_id  ) return;

                let param ={
                    vkhachhang_id: this.khachhang_id, 
                    vtungay: this.tracuuOB.vtungay,
                    vdenngay: this.tracuuOB.vdenngay, 
                    vkieu: this.tracuuOB.vkieu
                }
                
                let res = await api.getDanSachTB_NhanCong(this.axios, param);
                if(this.tracuuOB.vkieu == 1){
                    this.gridTBDC_MoChem.dataSources = []
                    this.gridTBDC_MoChem.dataSources = apiHelper.getDataFromResponseApiReturnArray(res);
               
                }
                if(this.tracuuOB.vkieu == 2){
                    this.gridTB_KetQua_CSKH_NVKT.dataSources = []
                    this.gridTB_KetQua_CSKH_NVKT.dataSources = apiHelper.getDataFromResponseApiReturnArray(res);
                    
                }
                
            } catch (error) {
                console.log(error);
            }
        },  
        

    },
    watch: {

        async khachhang_id(val) {
            if (val) {
                this.loading(true)
                await Promise.all([
                    this.getListResult_OB_NhanCong(),  //tab CKSH & Mo Chem
                    this.getListThueBao_HienTai(),
                    this.getListThueBao_KeTiep(),
                    this.getListThueBao_TraSau(),
                ]).then(() => {
                    this.loading(false)

                    if(this.gridTB_KetQua_CSKH_NVKT.dataSources.length > 0) return this.tab_active = 1;
                    if(this.gridTBDC_MoChem.dataSources.length > 0) return this.tab_active = 1;
                    if(this.gridTB_HienTai.dataSources.length > 0) return this.tab_active = 2;
                    if(this.gridTB_KeTiep.dataSources.length > 0) return this.tab_active = 3;
                    if(this.gridTB_TraSau.dataSources.length > 0) return this.tab_active = 4;
                    console.log('get danh sach TB success')
                }).catch(err => {
                    this.loading(false)
                    console.log(err)
                })

            }


        },
        async tracuuOB(value) {
            if(value.vkieu){
                this.tap_ob_id = value.vkieu;
            }
            //todo

        }


    },



}
</script>