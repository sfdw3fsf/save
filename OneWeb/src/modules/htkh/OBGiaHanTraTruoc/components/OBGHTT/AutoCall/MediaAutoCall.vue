<template>
    <div class="box-form">
        <div class="row mt-3">
            <div class="col-6">
                <div class="box-form" style="height: 300px;">
                    <div class="legend-title">Thông tin khách hàng
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="info-row">
                                <div class="key">Tên KH</div>
                                <div class="value" style="padding-left: 12px; padding-right: 10px">
                                    <input type="text" v-model="currentCustomer.ten_kh" class="form-control" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="info-row">
                                <div class="key">Mã KH</div>
                                <div class="value">
                                    <input type="text" v-model="currentCustomer.ma_kh" class="form-control"
                                        disabled />
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="info-row">
                                <div class="key">SĐT</div>
                                <div class="value">
                                    <input type="text" v-model="currentCustomer.sdt_kh" class="form-control" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="info-row">
                                <div class="key">Loại Giấy Tờ</div>
                                <div class="value">
                                    <input type="text" v-model="currentCustomer.loai_gt" class="form-control"
                                        disabled />
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="info-row">
                                <div class="key">Số Giấy tờ</div>
                                <div class="value">
                                    <input type="text" v-model="currentCustomer.so_gt" class="form-control" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="info-row">
                            <div class="key">Đia chỉ LH</div>
                            <div class="value" style="padding-left: 10px;padding-right: 7px">
                                <textarea class="form-control" placeholder="Nhập thông tin ghi chú" rows="2" v-model="currentCustomer.diachi_kh" disabled></textarea>
                            </div>
                            <!-- <div class="value">
                                <input type="text" v-model="currentCustomer.diachi_kh" class="form-control" disabled />
                            </div> -->
                        </div>
                    </div>
                    <div class="row">
                        
                        <div class="col-6">
                            <div class="info-row">
                                <div class="key">Tháng KTDC</div>
                                <div class="value">
                                    <input type="text" v-model="cpt_thang_kt_dc" class="form-control" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-6">
                <div class="box-form" style="height: 300px;">
                    <div class="legend-title">Cập nhật kết quả OB
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
                                <!-- <vue-date format="DD/MM/YYYY HH:mm A" type="datetime" v-model="dtpNgayDen" id="dtpNgayBH" -->
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
import api from '../../../API';
import apiHelper from '../../../api.helper';
import moment from 'moment'
import _ from 'lodash'
export default {
    name: 'ThongTinKhachHangAutoCall',
    components: {
        selectqt,
        Icon,
        DatePicker
    },
    props: {
        list_thuebao_selected:[],
    },
    data() {
        return {
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
            dtpNgayHen: new Date(),
            dtpNgayDen: new Date(moment().add(2, 'days')),
            chkNgayHen: false,
            isShowChuDeKN: false,
            txtNote:"",
            currentCustomer: {
                campaign_id: '1',
                bot_campaign_name: '',
                user_ipcc: '1',
                auto_call: 1,
                SoThueBao: '',
                PHANVUNG_ID: '',
                MA_TINH: '',
                OB_ID: '',
                SDT_THANH_TOAN: '',
                THUEBAO_ID: '',
                MA_TB: '',
                LOAITB_ID: '',
                TOCDO_ID: '',
                KHACHHANG_ID: '',
                THANHTOAN_ID: '',
                TEN_KH: '',
                DIACHI_KH: '',
                NGAY_BDDC: '',
                NGAY_KTDC: '',
                CUOC_DC: '',
                SO_THANGDC: '',
                CHITIETKM_ID: '',
                TEN_CTKM: '',
                SL_DATCOC: '',
                MATB_PHU: '',
                diachi_kh:"",
                loai_gt:"",
                ma_kh:"",
                sdt_kh:"",
                so_gt:"",
                ten_kh:""
            }
        }
    },
    computed:{
        cpt_thang_kt_dc(){
            console.log("this.currentCustomer.NGAY_KTDC, ", this.currentCustomer.NGAY_KTDC);
            if(typeof(this.currentCustomer.NGAY_KTDC) ==  "string") 
                return  this.currentCustomer.NGAY_KTDC?.length > 12 ? moment(this.currentCustomer.NGAY_KTDC, "DD/MM/YYYY HH:mm:ss").format('YYYYMM'):
                 moment(this.currentCustomer.NGAY_KTDC, "MM/DD/YYYY").format('YYYYMM');
            
            return "";
          
           
        }
        
    },
    methods: {
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
        async initComponent(data) {
            this.currentCustomer = data;
            //get by promise all
            await Promise.all([
                this.getOptionConnectOB(),
                // this.getStatusOB()
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
            // if(this.txtNote == ""){
            //     this.$toast.warning('Vui lòng nhập ghi chú')
            //     return "Lỗi Validate Dữ liêu";
            // }

            if((Number(this.cbbKetQuaOB.value) == 7 || Number(this.cbbKetQuaOB.value) == 9) && !this.chkNgayHen){
                this.$toast.warning('Vui lòng chọn hẹn từ')
                return "Lỗi Validate Dữ liêu";
            }
            return "ok"
        }, 

        async UpdateResultOB() {
            let strErr =  this.handleValidateData()

            if (strErr != "ok") return strErr;
            let listTBSelected = this.list_thuebao_selected
            listTBSelected = _.union(listTBSelected, [Number(this.currentCustomer.THUEBAO_ID)]);
            console.log("listTBSelected", listTBSelected);
            
            try {
                 
      
                let json_params= [];
                for (let i = 0; i < listTBSelected.length; i++) {
                    const tbId = listTBSelected[i];
                    json_params.push({
                        KHACHHANG_ID: this.currentCustomer.KHACHHANG_ID,
                        TT_KETNOI: this.cbbTTKetNoi.value,
                        KQ_OB: this.cbbKetQuaOB.options.length ? this.cbbKetQuaOB.value : "",
                        NGAY_HEN: this.chkNgayHen  ? moment(this.dtpNgayHen,"DD/MM/YYYY HH:mm A" ).format('YYYYMMDD HH:mm') : "",
                        NGAYHEN_DEN: this.chkNgayHen ? moment(this.dtpNgayDen,"DD/MM/YYYY HH:mm A" ).format('YYYYMMDD HH:mm') : "", //udate sau
                        GHI_CHU: this.txtNote,
                        THANG_KT: this.cpt_thang_kt_dc ,
                        THUEBAO_ID: tbId,
                        NHANVIEN_ID: this.$root.token.getNhanVienID(),
                        OB_ID:  this.currentCustomer.OB_ID
                    }) 
                }
                let response = await api.updateOB(this.axios, {vjson_ghtt_ob: JSON.stringify(json_params)})  
                if(!apiHelper.isResponseApiSuccess(response)){
                    strErr = apiHelper.getSrtCodeError(response);
                }
            } catch (error) {
                console.log(error);
                strErr = "Có lỗi xảy ra:"
            }
            return {
                message: strErr,
                data:{
                    KHACHHANG_ID: this.currentCustomer.KHACHHANG_ID,
                    TT_KETNOI: this.cbbTTKetNoi.value,
                    KQ_OB: this.cbbKetQuaOB.value,
                    MA_TB: this.currentCustomer.MA_TB,
                    LOAITB_ID: this.currentCustomer.LOAITB_ID,
                    OB_ID:  this.currentCustomer.OB_ID,
                    NGAY_HEN: this.chkNgayHen  ? moment(this.dtpNgayHen,"DD/MM/YYYY HH:mm A").format('YYYYMMDD HH:mm') : "",
                    NGAYHEN_DEN: this.chkNgayHen ? moment(this.dtpNgayDen,"DD/MM/YYYY HH:mm A" ).format('YYYYMMDD HH:mm') : "", 
                    CHUDEKN_ID: this.cbbChuDeKN.value,
                    LIST_TB_SELECTED: listTBSelected,
                }
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
        'cbbTTKetNoi.value': async function (value) {
            if(!value) return;
            await this.getOptionResultOB(value)
            this.$emit('tt-ketnoi-change', value);
        },
        'cbbKetQuaOB.value': async function (value) {
            this.isShowChuDeKN = false;
            this.chkNgayHen = false;
            // "KH không hài lòng về cước"
            if(Number(value) === 7 ) {
                this.chkNgayHen = true;
                this.isShowChuDeKN = true;
                await this.getListChuDeKN();
            }
             //KH hen thanh toan / lien lac lai sau
            if(Number(value) === 9 ) {
                this.chkNgayHen = true;
            }

        },
    }
}
</script>
<style scoped>
    .value  .mx-datepicker{
        width: auto !important;
    }
    </style>