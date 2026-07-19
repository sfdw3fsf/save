<template>
    <div class="row">
        <div class="col-sm-6  col-6">
            <div class="box-form" style="height: 300px;">
                <div class="legend-title">Thông tin khách hàng
                </div>
                <div class="row">
                    <vue-input labelWidth="100" label="Tên KH"  v-model="currentCustomer.ten_kh" :disable="true" />
                </div>
                <div class="row">
                    <div class="col-6 col-sm-6 ">
                        <vue-input labelWidth="100" label="Mã KH"  v-model="currentCustomer.ma_kh" :disable="true" />
                    </div>
                    <div class="col-sm-6 col-6">
                        <vue-input labelWidth="100" label="SĐT"  v-model="currentCustomer.sdt_kh" :disable="true" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-6">
                        <vue-input labelWidth="100" label="Loại Giấy Tờ"  v-model="currentCustomer.loai_gt" :disable="true" />
                    </div>
                    <div class="col-sm-6 col-6">
                        <vue-input labelWidth="100" label="Số Giấy tờ"  v-model="currentCustomer.so_gt" :disable="true" />
                    </div>
                </div>
                <div class="row">
                    <div class="info-row">
                        <div class="key w100">Đia chỉ LH</div>
                        <div class="value">
                            <textarea class="form-control" placeholder="Địa chỉ KH" rows="2"
                                v-model="currentCustomer.diachi_kh" disabled></textarea>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-sm-6 col-6">
            <div class="box-form" style="height: 300px;">
                <div class="legend-title"> {{ label_cap_nhat_OB }}
                </div>
                <div class="info-row">
                    <div class="key">TT kết nối</div>
                    <div class="value" style="padding-left: 8px;">
                        <select2 :options="cbbTTKetNoi.options" v-model="cbbTTKetNoi.value" :disabled="tab_active!=1"  />
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Kết quả OB</div>
                    <div class="value" style="padding-left: 8px;">
                        <select2 :options="cbbKetQuaOB.options" v-model="cbbKetQuaOB.value"
                            :disabled="!cbbKetQuaOB.options.length" />
                    </div>
                </div>
                <div class="row" v-show="cpt_show_ngay_hen">
                    <div class="col-6 col-sm-6">
                        <div class="info-row pull-right">
                            <div class="key">

                                <span class="name">Hẹn từ</span>
                                <input type="checkbox" class="check" v-model='chkNgayHen' style="margin-left: 8px;">

                            </div>
                            <div class="value ">
                                <date-picker format="DD/MM/YYYY HH:mm A" v-model="dtpNgayHen" type="datetime"
                                    :disabled-date="disabledRangeFrom" :disabled="!chkNgayHen">

                                    <template #icon-calendar>
                                        <span class="icon one-calendar"></span>
                                    </template>
                                </date-picker>
                            </div>
                        </div>
                    </div>
                    <div class="col-6 col-sm-6">
                        <div class="info-row right">
                            <div class="key">
                                <span class="name">Hẹn đến</span>
                            </div>
                            <div class="value pull-right">
                                <date-picker format="DD/MM/YYYY HH:mm A" v-model="dtpNgayDen" type="datetime"
                                    :disabled-date="disabledRangeTo" :disabled="!chkNgayHen">
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
                <div class="info-row" v-show="isShowChuDeKN">
                    <div class="key">Chủ đề KN</div>
                    <div class="value" style="padding-left: 8px;">
                        <select2 :options="cbbChuDeKN.options" v-model="cbbChuDeKN.value"
                            :disabled="!cbbChuDeKN.options.length" />
                    </div>
                </div>
                <div class="info-row" v-show="isKieuYeuCau">
                    <div class="key">Kiểu yêu cầu</div>
                    <div class="value" style="padding-left: 8px;">
                        <select2 :options="cbbKieuYC.options" v-model="cbbKieuYC.value"
                            :disabled="!cbbKieuYC.options.length" />
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Ghi chú (*)</div>
                    <div class="value" style="padding-left: 8px;">
                        <textarea class="form-control" placeholder="Nhập thông tin ghi chú" rows="3"
                           ref="txtNote" v-model="txtNote"></textarea>
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
import util from '../../../util'
import handleResultOBManger from '../../../ob.statery.pattern'

export default {
name: 'ThongTinKHAutoCall',
components: {
    selectqt,
    Icon,
    DatePicker
},
props: {
    // list_thuebao_selected:[],
    tab_active: {
        type: Number,
        default: 0
    },
    phanvung_kh_id: {
        type: Number || String,
        default: 0,
        require: true
    },
    khachhang_id: {
        type: Number || String,
        default: 0,
        require: true
    },
    ob_id: {
        type: Number || String,
        default: 0,
        require: true
    },
    user_ipcc: {
        type: String,
        default: ''
    }
},
async mounted() {
    await Promise.all([
        this.getOptionConnectOB(),
    
    ]).then(() => {
        this.getOptionResultOB(this.cbbTTKetNoi.value),
            console.log('init success')
    })
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
        cbbKieuYC: {
            options: [],
            value: null
        },
        listTempKetQuaOB: [],
        label_cap_nhat_OB: 'Cập nhật kết quả OB',
        dtpNgayHen: new Date(),
        dtpNgayDen: new Date(moment().add(2, 'days')),
        chkNgayHen: false,
        isShowChuDeKN: false,
        isKieuYeuCau: false,
        txtNote: "",
        currentCustomer: {
            auto_call: 1,
            SoThueBao: '',
            diachi_kh: "",
            loai_gt: "",
            ma_kh: "",
            sdt_kh: "",
            so_gt: "",
            ten_kh: ""
        }
    }

},
computed: {

    cpt_show_ngay_hen() {
        if (this.cbbKetQuaOB.value == 6 || this.cbbKetQuaOB.value == 7|| this.cbbKetQuaOB.value == 9) return true;
        return false;
    },
    kieu_tra_compt() {
        if (this.tab_active == 1) return 1;
        return 2;
    },
    phieu_cham_soc_kh_cpt() {

        if (this.cbbKetQuaOB.value == 8 || this.cbbKetQuaOB.value == 13) return 1;

        return 0;
    }
},
methods: {
    async getOptionResultOB(vttkn_id) {
        let response = await api.getListResultOBConnect(this.axios, vttkn_id);
        this.listTempKetQuaOB = apiHelper.getDataFromResponseApiReturnArray(response).map((item) => {
            return {
                id: item.kqob_id,
                text: item.ketqua_ob
            }
        });
        this.cbbKetQuaOB.options = this.listTempKetQuaOB;
        this.cbbKetQuaOB.value = this.cbbKetQuaOB.options[0]?.id;

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
    async getOptionYeuCau(){
        this.cbbKieuYC.value = 0;
        this.cbbKieuYC.options = [];
        let response = await api.getKieuYeuCau(this.axios);

        let allowedIds = new Set([13132, 13133, 13142]);
        this.cbbKieuYC.options = apiHelper.getDataFromResponseApiReturnArray(response).map((item) => {
            return {
                id: item.kieuld_id,
                text: item.ten_kieuld
            }
        }).filter(item => allowedIds.has(item.id));
        this.cbbKieuYC.value = this.cbbKieuYC.options[0]?.id;
    },


    async getListChuDeKN() {
        this.cbbChuDeKN.value  = 0;
        let response = await api.getListChuDeKN(this.axios);
        this.cbbChuDeKN.options = apiHelper.getDataFromResponseApiReturnArray(response).map((item) => {
            return {
                id: item.chudekn_id,
                text: item.tenchude
            }
        });
        if (this.cbbChuDeKN.options.length) this.cbbChuDeKN.value = this.cbbChuDeKN.options[0].id;

    },

    handleValidateData() {

        if (!this.cbbTTKetNoi.value) {
            this.$toast.warning('Vui lòng chọn kết nối')
            return false;
        }

        if (this.cbbTTKetNoi.value == 1 && !this.cbbKetQuaOB.value) {
            this.$toast.warning('Vui lòng chọn kết quả OB')
            return false;
        }
        if (!this.txtNote) {
            let allowedIds = new Set([6, 7, 8, 9, 10, 13]);
            //check this.cbbKetQuaOB.value  in (6, 7, 8, 9, 10, 13)
            if (allowedIds.has(Number(this.cbbKetQuaOB.value))) {
                this.$toast.warning('Vui lòng nhập ghi chú')
                this.$refs.txtNote.focus();
                return false;
            }
        }

        if ((Number(this.cbbKetQuaOB.value) == 7 || Number(this.cbbKetQuaOB.value) == 9) && !this.chkNgayHen) {
            this.$toast.warning('Vui lòng chọn hẹn từ')
            return false;
        }
         //check date 
         if(this.cpt_show_ngay_hen && this.chkNgayHen){
            if (!this.dtpNgayHen) {
                this.$toast.warning('Vui lòng chọn ngày hẹn')
                return false;
            }
            if (!this.dtpNgayDen) {
                this.$toast.warning('Vui lòng chọn ngày hẹn')
                return false;
            }
        }
       
        //
        if (this.$root.token.getPhanVungID() == 22 && this.cbbKetQuaOB.value == 13) {
            this.$toast.warning('Nghiệp vụ hiện tại chỉ hỗ trợ HCM')
            return false;
        }


        
        return true
    },

    async UpdateResultOB(listItemSelected) {
       

        if(listItemSelected.length == 0){
            this.$toast.warning('Không có thông tin khách hàng!')
            return;
        }
      
        console.log("listItemSelected:", listItemSelected);
        if (!this.handleValidateData()) return;   
        
        try {
            console.log("user_ipcc:", this.user_ipcc);

            let PHIEUCSKH_OB_ID = this.phieu_cham_soc_kh_cpt;
            //không gặp được khách hàng
            if (this.cbbTTKetNoi.value == 2) PHIEUCSKH_OB_ID = await this.getPhieuCSKH();
       
            const json_params = listItemSelected.map(item => ({
                PHANVUNG_ID: this.phanvung_kh_id,
                NGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                USER_NAME: this.$root.token.getUserName(), //user ipcc 
                TTKN_ID: this.cbbTTKetNoi.value,
                KQOB_ID: this.cbbKetQuaOB.options.length ? this.cbbKetQuaOB.value : "",
                HEN_TU: this.chkNgayHen ? moment(this.dtpNgayHen, "DD/MM/YYYY HH:mm A").format('YYYYMMDD HH:mm') : "",
                HEN_DEN: this.chkNgayHen ? moment(this.dtpNgayDen, "DD/MM/YYYY HH:mm A").format('YYYYMMDD HH:mm') : "",
                CHUDEKN_ID: this.isShowChuDeKN ? this.cbbChuDeKN.value : 0,
                GHI_CHU: this.txtNote,
                KIEUTRA: this.kieu_tra_compt, //1 tham gia trả trước, 2 trả sau
                SDT_TT: item.SDT_TT,
                KHACHHANG_ID: item.KHACHHANG_ID,
                THANG_KTDC: item.NGAY_KTDC ? moment(item.NGAY_KTDC, "DD/MM/YYYY").format('YYYYMM') : "",
                THUEBAO_ID: item.THUEBAO_ID,
                DIACHI_LD: item.DIACHI_LD,
                OB_ID: this.ob_id,
                PHIEUCSKH_OB: PHIEUCSKH_OB_ID,
                KIEULD_ID: this.cbbKieuYC.value || 0,
                USER_IPCC: this.user_ipcc
            }));

           
            const response = await api.updateOBV2(this.axios, JSON.stringify(json_params) )
            if (!apiHelper.isResponseApiSuccess(response)) {
                let strErr = apiHelper.getSrtCodeError(response);
                this.$toast.error(strErr)
                return;
            }
            this.$toast.success('Cập nhật kết quả OB thành công')
            // await this.handleUpdateUserIPCC(this.cbbKetQuaOB.value);
            //handle result OB
           
            this.handelResponseSaveOB(response, listItemSelected)

        } catch (error) {
            console.log(error);
             this.$toast.error(`Có lỗi xảy ra: ${error}`)
        }
    },
    disabledRangeFrom(date) {
        const currentDate = new Date();
        const thirtyDaysFromNow = new Date(currentDate);
        thirtyDaysFromNow.setDate(currentDate.getDate() + 30);
        return date <= currentDate || date > thirtyDaysFromNow;
    },
    disabledRangeTo(date) {
        const currentDate = this.dtpNgayHen ? new Date(moment(this.dtpNgayHen, "DD/MM/YYYY HH:mm A")) : new Date();
        const thirtyDaysFromNow = new Date(currentDate);
        thirtyDaysFromNow.setDate(currentDate.getDate() + 30);
        return date <= currentDate || date > thirtyDaysFromNow;
    },
    async getCustomerInfo(vphanvung_id, vkhachhang_id) {
        let response = await api.getInfoCustomer(this.axios, { vphanvung_id, vkhachhang_id })
        let data = apiHelper.getDataFromResponseApiReturnArray(response)
        if (data.length > 0) {
            return data[0]
        }
        return {}
    },
    async getPhieuCSKH() {
        try {
            let titePopup = 'Bạn có muốn tạo phiếu CSKH bảo dưỡng cho NVKT địa bàn không?'

            const confirm = await this.$confirm('Xác nhận', titePopup, {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
                type: 'info'
            })
            if (confirm) return 1;
        } catch (error) {
            console.log(error);  
        }  
         return 0;        
    },
    handelResponseSaveOB(response, listItemSelected ) {
        try {
        
            if (this.cbbTTKetNoi.value !== 1) return;

            //lay response của hàm auto gia hạn OB
            const autoGHData = apiHelper.getDataFromResponseApiReturnRaise(response); 

            //vd: {data: "MGD-202108-0000000001", errorCode: ERR-1, faultString: "Khong cung thue bao"}
            if(autoGHData.errorCode !== "ERR-0"){
                const message = autoGHData.faultString
                this.$toast.warning(message)
            }
            
        // let arrayMergeByKey = listItemSelected;
        
        const itemRes = util.getItemReturn(listItemSelected);
        const data = {
            MA_TB_KT: itemRes.MA_TB_KT || "",
            MA_TB: itemRes?.MA_TB || "",
            LOAITB_ID: itemRes?.LOAITB_ID,
            TT_KETNOI: this.cbbTTKetNoi.value,
            KQ_OB: this.cbbKetQuaOB.value,
            CHUDEKN_ID: this.cbbChuDeKN.value,
            OB_ID: this.ob_id,
            AUTO_GH_DATA: autoGHData || null,
            DICHVUVT_ID: itemRes?.DICHVUVT_ID,
        }
     
        const handleResultOB = handleResultOBManger.getResultOB(Number(this.cbbKetQuaOB.value));
        
        if(handleResultOB)  handleResultOB.processResult(this, data)
        
    } catch (error) {
            console.log(error);
        }  
    },
    async handleUpdateUserIPCC(ketQuaOBId) {   
              
        const allowedIds = new Set([ 6, 7, 8, 9, 10, 11, 12, 13]);

        if (!allowedIds.has(Number(ketQuaOBId))) return;
        const response = await api.updateUserIPCCState(this.axios, this.user_ipcc);
        if (response.data.errorCode != 0) {
            this.$toast.error(response.data.faultString)
        }    
    },
},
watch: {
    'cbbTTKetNoi.value': async function (value) {
        if (!value) return;
        await this.getOptionResultOB(value)
    },
    'cbbKetQuaOB.value': async function (value) {
        this.isShowChuDeKN = false;
        this.chkNgayHen = false;
        this.isKieuYeuCau = false;
        // "KH không hài lòng về cước"
        if (Number(value) === 7) {
            this.chkNgayHen = true;
            this.isShowChuDeKN = true;
            await this.getListChuDeKN();
        }
        //KH hen thanh toan / lien lac lai sau
        if (Number(value) === 9) {
            this.chkNgayHen = true;
        }
        if (Number(value) === 10) {
            this.isKieuYeuCau = true;
            await this.getOptionYeuCau()
        }
        this.$emit('ket-qua-ob-change', value);
    },
    tab_active: {
        handler: function (value) {
            if (!value) return;
            this.label_cap_nhat_OB = "Cập nhật kết quả OB";
            if (value == 1) {
                let allowedIds = new Set([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]);
                this.cbbKetQuaOB.options = this.listTempKetQuaOB.filter(item => allowedIds.has(item.id));
            }
            if (value == 2) {
                this.label_cap_nhat_OB = "Cập nhật kết quả OB và phản hồi";
                this.cbbTTKetNoi.value = 1; //gặp đươc khách hàng
                const allowedIds = new Set([6, 7, 13]);
                this.cbbKetQuaOB.options = this.listTempKetQuaOB.filter(item => allowedIds.has(item.id));
            }
            if (value == 3) {
                this.cbbTTKetNoi.value = 1; //gặp đươc khách hàng
            }
            if (this.cbbKetQuaOB.options.length) this.cbbKetQuaOB.value = this.cbbKetQuaOB.options[0]?.id;
        }
    },
    khachhang_id: {
        handler: function (value) {
            if (!value) return;
            this.getCustomerInfo(this.phanvung_id, value).then((data) => {
                this.currentCustomer = data;
            })
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
