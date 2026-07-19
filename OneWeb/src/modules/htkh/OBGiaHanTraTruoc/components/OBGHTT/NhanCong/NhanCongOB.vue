<template>
    <div class="box-form">
        <div class="legend-title">
            Thông tin tra cứu
        </div>
        <div class="row">
            

            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key" style="width: 130px;">Tập OB Nhân Công</div>
                    <div class="value" style="padding-left: 8px;">
                            <select2 :options="cbbTapNhanCongOb.options" v-model="cbbTapNhanCongOb.value" />
                        </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key">Từ ngày</div>
                    <div class="value">
                        <vue-date format="DD/MM/YYYY" v-model="fromDate" type="date"/>
                        <!-- <ejs-datepicker format="DD/MM/YYYY" v-model="fromDate" start="Year" depth="Year" /> -->
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key">Đến ngày</div>
                    <div class="value">
                        <vue-date format="DD/MM/YYYY" v-model="toDate" type="date"/>
                        <!-- <ejs-datepicker format="DD/MM/YYYY" v-model="toDate" start="Year" depth="Year" /> -->
                    </div>
                </div>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-6">
                <div class="box-form" style="height: 400px;">
                    <div class="legend-title">
                        Thông tin KH
                        <div class="pull-right">
                            <Icon icon="vscode-icons:file-type-excel" width="24" height="24" />
                        </div>
                    </div>
                    <DataGrid :dataSource="gridThongTinKH.dataSources" :allowPaging="true" :columns="gridThongTinKH.columns"
                        :allowSorting="true" :enablePagingServer="false" panelDataHeight="200px"
                        @rowSelected="gridThongTinKH_onRowSelected" ref="gridThongTinKH" 
                        />
                </div>
            </div>
            <div class="col-6">
                <div class="box-form" style="height: 400px;">
                    <div class="legend-title">Cập nhật kết quả OB
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="info-row">
                                <div class="key">Tên KH </div>
                                <div class="value">
                                    <input type="text" v-model="currentCustomer.ten_kh" class="form-control"  disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="info-row">
                                <div class="key">SĐT KH</div>
                                <div class="value">
                                    <input type="text" class="form-control"  v-model="currentCustomer.so_dt"  disabled/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="info-row">
                        <div class="key">TT kết nối</div>
                        <div class="value" style="padding-left: 8px;">
                            <select2 :options="cbbTTKetNoi.options" v-model="cbbTTKetNoi.value" />
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key">Kết quả OB</div>
                        <div class="value" style="padding-left: 8px;">
                            <select2 :options="cbbKetQuaOB.options" v-model="cbbKetQuaOB.value" :disabled="!cbbKetQuaOB.options.length"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="info-row pull-right" >
                                <div class="key">
                                      
                                        <span class="name">Hẹn từ</span>
                                        <input type="checkbox" class="check" v-model='chkNgayHen' style="margin-left: 8px;">  
                                    
                                    </div>
                                <div class="value " >
                                    <date-picker  format="DD/MM/YYYY HH:mm A"
                                            v-model="dtpNgayHen"
                                            type="datetime" :disabled-date="disabledRangeFrom"
                                            :disabled="!chkNgayHen"
                                            >
                                            
                                            <template #icon-calendar>
                                                <span class="icon one-calendar"></span>
                                            </template>
                                    </date-picker>                                
                                </div>
                            </div>
                        </div>     
                        <div class="col-6">
                            <div class="info-row " >
                            <div class="key">
                                    <span class="name">Hẹn đến</span>
                                </div>
                            <div class="value" >
                                <date-picker  format="DD/MM/YYYY HH:mm A"
                                            v-model="dtpNgayDen"
                                            type="datetime" :disabled-date="disabledRangeTo"
                                            :disabled="!chkNgayHen"
                                            >
                                            <template #icon-calendar>
                                                <span class="icon one-calendar"></span>
                                            </template>
                                    </date-picker>
                            </div>
                            </div>
                        </div>     
                    </div>
                     <!-- Update không hài lòng về cước -->
                     <div class="info-row" v-show ="isShowChuDeKN">
                        <div class="key">Chủ đề KN</div>
                        <div class="value" style="padding-left: 8px;">
                            <select2 :options="cbbChuDeKN.options" v-model="cbbChuDeKN.value" :disabled="!cbbChuDeKN.options.length"/>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key">Ghi chú (*)</div>
                        <div class="value" style="padding-left: 8px;">
                            <textarea class="form-control" placeholder="Nhập thông tin ghi chú" rows="3" v-model="txtNote"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import selectqt from '@/components/qt_components/select/index.vue';
import { Icon } from '@iconify/vue2'
import DatePicker from 'vue2-datepicker'
import moment from 'moment'
import api from '../../../API';
import apiHelper from '../../../api.helper';

export default {
    name: 'NhanCongOB',
    components: {
        selectqt,
        Icon,
        DatePicker
    },
    props: {
       thuebao_id: 0,
       list_thuebao_selected:[],
    },

    data() {
        return {
            gridThongTinKH: {
                columns: [
                    { fieldName: 'ma_kh', headerText: 'Mã KH', width: '100px' },
                    { fieldName: 'ten_kh', headerText: 'Tên KH', width: '150px' },
                    { fieldName: 'sdt_kh', headerText: 'SĐT KH', width: '150px' },
                    { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', width: '150px' },
                    { fieldName: 'nv_th', headerText: 'Số TB mở chêm', width: '120px' }
                ],
                dataSources: []
            },
            cbbTTKetNoi: {
                options: [],
                value: 1
            },
            cbbKetQuaOB: {
                options: [],
                value: 1
            },


            cbbChuDeKN: {
                options: [],
                value: null
            },

            cbbTapNhanCongOb:{
                options: [],
                value: 0
            },

            txtNote: "",
            dtpNgayHen: new Date(),
            dtpNgayDen: new Date(moment().add(2, 'days')),
            fromDate: moment().subtract(30, 'days').format('DD/MM/YYYY'),
            toDate: moment().format('DD/MM/YYYY'),
            currentCustomer: {
                khachhang_id: 0,
                ma_kh: '',
                ten_kh: '',
                diachi_kh: '',
                thuebao_id: 0
            },
            chkNgayHen: false,
            isShowChuDeKN: false,

        }
    },
    methods: {
        async getListInfoCustomer() {
            try {
                this.clearData()

                if(!this.cbbTapNhanCongOb.value) return;
                this.loading(true)
                
                const res = await api.getListCustomerOBNhanCong(this.axios, {
                    vtungay: this.cpt_fromDate,
                    vdenngay: this.cpt_toDate,
                    vkieu: this.cbbTapNhanCongOb.value
                })
                this.gridThongTinKH.dataSources = apiHelper.getDataFromResponseApiReturnArray(res) || [];
                if(!this.gridThongTinKH.dataSources.length){
                    this.$toast.warning('Không tìm thấy thông tin khách hàng thoả mãn yêu cầu!')
                }

            } catch (error) {
                this.$toast.error('Lỗi tải dữ liệu khách hàng:', error)
                console.log(error);
            } finally {
                this.loading(false)
            }
        },

        gridThongTinKH_onRowSelected(row) {
            this.currentCustomer = row.data
            //emit event to data selected
            this.$emit('onRowSelected', row.data)
            //emit data tra cuu nhan cong OB
            this.$emit("tap-nhan-cong-change", {
                vkieu: this.cbbTapNhanCongOb.value,
                vtungay: this.cpt_fromDate,
                vdenngay: this.cpt_toDate
            });
        },
        async getOptionResultOB(vttkn_id) {
            let response = await api.getListResultOBConnect(this.axios, vttkn_id);
            this.cbbKetQuaOB.options = apiHelper.getDataFromResponseApiReturnArray(response).map((item) => {
                return {
                    id: item.kqob_id,
                    text: item.ketqua_ob
                }
            });
            if(!this.cbbKetQuaOB.options.length) this.cbbKetQuaOB.value = 0;
            else this.cbbKetQuaOB.value = this.cbbKetQuaOB.options[0].id;


        },
        async getOptionConnectOB() {
            let response = await api.getdsTTKetNoi(this.axios);
            this.cbbTTKetNoi.options = apiHelper.getDataFromResponseApiReturnArray(response).map((item) => {
                return {
                    id: item.ttkn_id,
                    text: item.trangthai_kn
                }
            });
        },

        async getListChuDeKN(){
            let response = await api.getListChuDeKN(this.axios);
            this.cbbChuDeKN.options = apiHelper.getDataFromResponseApiReturnArray(response).map((item) => {
                return {
                    id: item.chudekn_id,
                    text: item.tenchude
                }
            });
            if(this.cbbChuDeKN.options.length) this.cbbChuDeKN.value = this.cbbChuDeKN.options[0].id;
            
        },


        async getTapOBNhanCong(){
            try {
                let response = await api.getTapOBNhanCong(this.axios);
                this.cbbTapNhanCongOb.options = apiHelper.getDataFromResponseApiReturnArray(response).map((item) => {
                    return {
                        id: item.id,
                        text: item.tap_ob
                }
               
            });   
            if(this.cbbTapNhanCongOb.options.length)
                this.cbbTapNhanCongOb.value = this.cbbTapNhanCongOb.options[0].id;

            } catch (error) {
              console.log(error);  
            }
           
        },


        async initComponent() {
            //get by promise all
            await Promise.all([
                this.getListInfoCustomer(),
                this.getOptionConnectOB(),
                this.getTapOBNhanCong(),
            ]).then(() => {
                    this.getOptionResultOB(this.cbbTTKetNoi.value),
                    console.log('init success')
            })
        },
        
        handleValidateData(){
            if (!this.cbbTTKetNoi.value) {
                this.$toast.warning('Vui lòng chọn kết nối')
                return "Lỗi Validate Dữ liêu";
            }
            if(this.cbbTTKetNoi.value == 1 && !this.cbbKetQuaOB.value){
                this.$toast.warning('Vui lòng chọn kết quả OB')
                return "Lỗi Validate Dữ liêu";
            }
            if(this.txtNote == ""){
                this.$toast.warning('Vui lòng nhập ghi chú')
                return "Lỗi Validate Dữ liêu";
            }
            
            if((Number(this.cbbKetQuaOB.value) == 7 || Number(this.cbbKetQuaOB.value) == 9) && !this.chkNgayHen){
                this.$toast.warning('Vui lòng chọn hẹn từ')
                return "Lỗi Validate Dữ liêu";
            }
            return "ok"
        }, 


        async UpdateResultOB() {
            let strErr =  this.handleValidateData()
            
            if (strErr != "ok") return strErr;
            let  listTBSelected = this.list_thuebao_selected
            listTBSelected = _.union(listTBSelected, [Number(this.thuebao_id)]);
            console.log("listTBSelected", listTBSelected);
     
            try {
                let json_params= [];
             
                for (let i = 0; i < listTBSelected.length; i++) {
                    const thuebaoId = listTBSelected[i];
                    json_params.push({
                        KHACHHANG_ID: this.currentCustomer.khachhang_id,
                        TT_KETNOI: this.cbbTTKetNoi.value,
                        KQ_OB: this.cbbKetQuaOB.options.length ? this.cbbKetQuaOB.value : "",
                        NGAY_HEN: this.chkNgayHen  ? moment(this.dtpNgayHen,"DD/MM/YYYY HH:mm A" ).format('YYYYMMDD HH:mm') : "",
                        NGAYHEN_DEN: this.chkNgayHen ? moment(this.dtpNgayDen,"DD/MM/YYYY HH:mm A" ).format('YYYYMMDD HH:mm') : "", 
                        GHI_CHU: this.txtNote,
                        THANG_KT: this.cpt_thang_kt_dc ,
                        THUEBAO_ID: thuebaoId,
                        NHANVIEN_ID: this.$root.token.getNhanVienID(),
                    })
                    
                }
                let response = await api.updateOB_NhanCong(this.axios, { vjson_ghtt_ob: JSON.stringify(json_params) })
                if (!apiHelper.isResponseApiSuccess(response)) {
                    strErr = apiHelper.getSrtCodeError(response);
                }
            } catch (error) {
                console.log(error);
       
                strErr = "Có lỗi xảy ra:"
            }
            return {
                message: strErr,
                data:{
                    KHACHHANG_ID: this.currentCustomer.khachhang_id,
                    TT_KETNOI: this.cbbTTKetNoi.value,
                    KQ_OB: this.cbbKetQuaOB.value,
                    MA_TB: "",
                    LOAITB_ID: this.currentCustomer.LOAITB_ID,
                    NHANVIEN_ID: this.$root.token.getNhanVienID(),
                    NGAY_HEN: this.chkNgayHen  ? moment(this.dtpNgayHen,"DD/MM/YYYY HH:mm A" ).format('YYYYMMDD HH:mm') : "",
                    NGAYHEN_DEN: this.chkNgayHen ? moment(this.dtpNgayDen,"DD/MM/YYYY HH:mm A" ).format('YYYYMMDD HH:mm') : "", 
                    CHUDEKN_ID: this.cbbChuDeKN.value,
                    LIST_TB_SELECTED: listTBSelected,
                }
            }

        },
        clearData() {
            this.gridThongTinKH.dataSources = [];
            this.txtNote = "";
            this.currentCustomer = {
                khachhang_id: 0,
                ma_kh: '',
                ten_kh: '',
                diachi_kh: '',
            }
        },
        disabledRangeFrom(date){
            const currentDate = new Date();
            const thirtyDaysFromNow = new Date(currentDate);
            thirtyDaysFromNow.setDate(currentDate.getDate() + 30);
            return date <= currentDate || date > thirtyDaysFromNow;
        },
        disabledRangeTo(date){
            const currentDate = this.dtpNgayHen? new Date(moment(this.dtpNgayHen, "DD/MM/YYYY HH:mm A")) : new Date();
            const thirtyDaysFromNow = new Date(currentDate);
            thirtyDaysFromNow.setDate(currentDate.getDate() + 30);
            return date <= currentDate || date > thirtyDaysFromNow;
        }
    },
    watch: {
        //watch cbbTapNhanCongOb.value
        'cbbTapNhanCongOb.value': function (value) {

            let param = {
                vkieu: value,
                vtungay: this.cpt_fromDate,
                vdenngay: this.cpt_toDate
            }
         //emit data
         this.$emit("tap-nhan-cong-change", param);
        },

    },//computed
    computed:{
        cpt_thang_kt_dc(){
            console.log("this.currentCustomer.NGAY_KTDC, ", this.currentCustomer);
            if(typeof(this.currentCustomer.NGAY_KTDC) ==  "string") 
                return  this.currentCustomer.NGAY_KTDC?.length > 12 ? moment(this.currentCustomer.NGAY_KTDC, "DD/MM/YYYY HH:mm:ss").format('YYYYMM'):
                 moment(this.currentCustomer.NGAY_KTDC, "MM/DD/YYYY").format('YYYYMM');
            
            return moment(new Date()).format('YYYYMM');   
        },
        cpt_fromDate(){
            return moment(this.fromDate, "DD/MM/YYYY").format('YYYYMMDD');
        },
        
        cpt_toDate(){
            return moment(this.toDate, "DD/MM/YYYY").format('YYYYMMDD');
        },
       
       
    },
    watch: {
        'cbbTTKetNoi.value': function (value) {
            this.getOptionResultOB(value)
        },
        'cbbKetQuaOB.value': function (value) {
            this.isShowChuDeKN = false;
            this.chkNgayHen = false;
            // "KH không hài lòng về cước"
            if(Number(value) === 7 ) {
                this.chkNgayHen = true;
                this.isShowChuDeKN = true;
                this.getListChuDeKN();
            }
            //KH hen thanh toan / lien lac lai sau
            if(Number(value) === 9 ) {
                this.chkNgayHen = true;
            }

        },
    },

}
</script>
<style scoped>
    
</style>