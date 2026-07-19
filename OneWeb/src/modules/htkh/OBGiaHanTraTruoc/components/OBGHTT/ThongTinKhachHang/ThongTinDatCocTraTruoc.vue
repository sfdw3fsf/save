<template>
    <div class="box-form">
        <div class="legend-title">
            Thông tin thuê bao khách hàng
        </div>
        <div class="nav tab-over tabs">
            <a href="#tb-tra-truoc-trong-ky" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 1"
                :class="tab_active == 1 ? 'minw0 active' : 'minw0'">D/S TB đặt cọc trả trước trong kỳ</a>

            <a href="#tb-tra-truoc-lech-ky" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 2"
                :class="tab_active == 2 ? 'minw0 active' : 'minw0'">DS TB đặt
                cọc trả trước lệch kỳ</a>
            <a href="#tb-tra-sau" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 3"
                :class="tab_active == 3 ? 'minw0 active' : 'minw0'">D/S TB trả sau có
                thể tham gia trả trước</a>
            <a href="#hddc-chua-thanh-toan" data-toggle="tab" style="min-width: 200px !important;" @click="tab_active = 4"
                :class="tab_active == 4 ? 'minw0 active' : 'minw0'">D/S HDDC chưa thanh toán</a>
        </div>
        <div class="tab-content">
            <div :class="tab_active == 1 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-truoc-trong-ky">
                <!-- D/S TB đặt cọc trả trước trong kỳ -->
                <DataGrid :dataSource="gridTBDC_TraTruoc.dataSources" :allowPaging="true" ref="refGridTBTraTruoc"
                    :columns="gridTBDC_TraTruoc.columns" :allowSorting="true" :enablePagingServer="false"
                    panelDataHeight="220px" :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB" 
                    @rowDeselected="onSelectRow_DSTB"
                    />
            </div>
            <div :class="tab_active == 2 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-truoc-lech-ky">
                <!-- DS TB đặt cọc trả trước lệch kỳ -->
                <DataGrid :dataSource="gridTBDC_LechKy.dataSources" :allowPaging="true" :columns="gridTBDC_LechKy.columns"
                    ref="refGridTBLechKy" :allowSorting="true" :enablePagingServer="false" panelDataHeight="220px"
                    :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB"
                    @rowDeselected="onSelectRow_DSTB"
                    @queryCellInfo="dsTB_LechKy_QueryCellInfo"
                    />
            </div>
            <div :class="tab_active == 3 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="tb-tra-sau">
                <!-- D/S TB trả sau có thể tham gia trả trước -->
                <DataGrid :dataSource="gridTB_TraSau.dataSources" :allowPaging="true" :columns="gridTB_TraSau.columns"
                    ref="refGridTBTraSau" :allowSorting="true" :enablePagingServer="false" panelDataHeight="220px"
                    :showColumnCheckbox="true" @rowSelected="onSelectRow_DSTB" 
                    @rowDeselected="onSelectRow_DSTB"
                    :enabledSelectFirstRow="false"
                    />
            </div>
            <div :class="tab_active == 4 ? 'tab-pane active fullh' : 'tab-pane fullh'" id="hddc-chua-thanh-toan">
                <!-- D/S HDDC chua thanh toan -->
                <HDChuaThanhToanComponent :khachhang_id="khachhang_id"/>
            </div>
        </div>
    </div>
</template>

<script>
import api from '../../../API';
import apiHelper from '../../../api.helper';
import HDChuaThanhToanComponent from './HDChuaThanhToanComponent.vue'
export default {
    components:{
        HDChuaThanhToanComponent
    },
    name: 'ThongTinDatCocTraTruoc',
    props: {
        thang_kt: {
            type: Number || String,
            default: ""
        },

        khachhang_id: {
            type: Number,
            default: 0,
            require: true
        },
        ma_tb: {
            type: String,
            default: 0
        }

    },

    data() {
        return {
            gridTBDC_TraTruoc: {
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
                    { fieldName: 'tu_van', headerText: 'Tư vấn', width: '120px' },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', width: '200px' }
                ],
                dataSources: []
            },
            gridTBDC_LechKy: {
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
                    { fieldName: 'tu_van', headerText: 'Tư vấn', width: '120px' },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', width: '200px' }
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
                    { fieldName: 'tu_van', headerText: 'Tư vấn', width: '120px' },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', width: '200px' }
                ],
                dataSources: []
            },
            
            tab_active: 1,

        }
    },
    methods: {

        
       
        async getListThueBao_TraTruoc() {

            try {
                if(!this.khachhang_id || !this.thang_kt) return;
                let listItemFocus = []
                this.gridTBDC_TraTruoc.dataSources = []
                console.log("this.thang_kt:", this.thang_kt);
                let res = await api.getListThueBaoDCTT(this.axios, this.khachhang_id, this.thang_kt);
                this.gridTBDC_TraTruoc.dataSources = apiHelper.getDataFromResponseApiReturnArray(res).map((item, index) => {
                    if (item.ma_tb == this.ma_tb) {
                        this.tab_active = 1;
                        listItemFocus.push(index)
                    }
                    return {
                        ...item,
                        cuoc_dc: item.cuoc_dc ? item.cuoc_dc.toLocaleString('vi-VN') : ''
                    }
                })

                this.$refs.refGridTBLechKy.flagSelectedRowIndexes = listItemFocus;
            } catch (error) {
                console.log(error);
            }
        },

        onSelectRow_DSTB() {
            const tbdc_selected = this.$refs.refGridTBTraTruoc.grid.getSelectedRecords();
            const tblechky_selected = this.$refs.refGridTBLechKy.grid.getSelectedRecords();
            const tbtrasau_selected = this.$refs.refGridTBTraSau.grid.getSelectedRecords();
            let list_item_selected = [].concat(tbdc_selected, tblechky_selected, tbtrasau_selected);
            if (list_item_selected.length > 0) {
                this.$emit("on-select-row", list_item_selected);
            }

        },


        async getListThueBao_LechKy() {

            try {
                if(!this.khachhang_id || !this.thang_kt) return;
                this.gridTBDC_LechKy.dataSources = []
                let listItemFocus = []
                let res = await api.getListThueBaoDCLK(this.axios, this.khachhang_id, this.thang_kt);
                this.gridTBDC_LechKy.dataSources = apiHelper.getDataFromResponseApiReturnArray(res).map((item, index) => {
                    if (item.ma_tb == this.ma_tb) {
                        this.tab_active = 2;
                        listItemFocus.push(index)
                    }
                    return {
                        ...item,
                        cuoc_dc: item.cuoc_dc ? item.cuoc_dc.toLocaleString('vi-VN') : ''
                    }
                })
                this.$refs.refGridTBLechKy.flagSelectedRowIndexes = listItemFocus;

            } catch (error) {
                console.log(error);
            }
        },
        async getListThueBao_TraSau() {

            try {
                if(!this.khachhang_id || !this.thang_kt) return;
                let listItemFocus = []

                this.gridTB_TraSau.dataSources = []

                let res = await api.getListThueBaoTS(this.axios, this.khachhang_id, this.thang_kt);
                this.gridTB_TraSau.dataSources = apiHelper.getDataFromResponseApiReturnArray(res).map((item, index) => {
                    if (item.ma_tb == this.ma_tb) {
                        this.tab_active = 3;
                        listItemFocus.push(index)
                    }
                    return {
                        ...item,
                        cuoc_dc: item.cuoc_dc ? item.cuoc_dc.toLocaleString('vi-VN') : ''
                    }
                })

                this.$refs.refGridTBTraSau.flagSelectedRowIndexes = listItemFocus;
            } catch (error) {
                console.log(error);
            }
        },
        dsTB_LechKy_QueryCellInfo(args){
           const {data} = args;

           console.log(data.ngayqua_hen);
           if(data && data.trangthaitb_id == 5){ 
            args.cell.style.color = 'red';
            args.cell.style.fontWeight = "bold";
           }
        },
        

    },
    watch: {

        async khachhang_id(val) {
            if (val) {
                this.loading(true)
                await Promise.all([
                    this.getListThueBao_TraTruoc(),
                    this.getListThueBao_LechKy(),
                    this.getListThueBao_TraSau(),
                ]).then(() => {
                    this.loading(false)



                    console.log('get danh sach TB success')
                }).catch(err => {
                    this.loading(false)
                    console.log(err)

                })

            }

        }

    },



}
</script>
<style scoped>

</style>