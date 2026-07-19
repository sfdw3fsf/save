<template>
    <div class="box-col box-form">
        <div>
            <div class="legend-title">
                Thông tin khách hàng
            </div>
            <div class="info-row">
                <div class="key w120">Mã khách hàng</div>
                <div class="value">
                    <input type="text" class="form-control bold" v-model="model.v4_ma_khach_hang" id="v4_ma_khach_hang" :disabled="true">
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Tên khách hàng</div>
                <div class="value">
                    <input type="text" class="form-control bold" v-model="model.v4_ten_khach_hang" id="v4_ten_khach_hang" :disabled="true">
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Địa chỉ KH</div>
                <div class="value">
                    <input type="text" class="form-control bold" v-model="model.v4_dia_chi_khach_hang" id="v4_dia_chi_khach_hang" :disabled="true">
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">
                Thông tin tiền cước sử dụng
            </div>
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="info-row">
                        <div class="key w130">
                            <div class="check-action">
                                <input type="checkbox" class="check" id="v4_chb_doi_tac" @change="chkDoiTac_CheckedChanged">
                                <span class="name">Đối tác</span>
                            </div>
                        </div>
                        <div class="value">
                            <div class="select-custom">
                                <ejs-dropdownlist id="v4_doi_tac"
                                    locale="vi-VN" :enabled="v4_enable_doi_tac"
                                    v-model="model.v4_doi_tac"
                                    :dataSource="v4_LstDoiTac"
                                    :allowFiltering="true" :filtering="onFilterV4DoiTac"
                                    :fields="{ value: 'ID', text: 'NAME' }"
                                />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="info-row">
                        <div class="key w130">Cước TKĐT</div>
                        <div class="value">
                            <input type="text" class="form-control bold" v-model="model.v4_cuoc_tkdt" id="v4_cuoc_tkdt" :disabled="!v4_enable_doi_tac" 
                                @blur="txtCuocTKDT_Leave" @keydown="checkNumber"
                                style="text-align: right !important; padding-right: 5px">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="info-row">
                        <div class="key w130">
                            Mã hợp đồng ĐT
                        </div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="model.v4_ma_hop_dong_dt" id="v4_ma_hop_dong_dt" :disabled="!v4_enable_doi_tac">
                        </div>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="info-row">
                        <div class="key w130">Cước TCĐT</div>
                        <div class="value">
                            <input type="text" class="form-control bold" v-model="model.v4_cuoc_tcdt" id="v4_cuoc_tcdt" :disabled="!v4_enable_doi_tac" 
                                @blur="txtCuocTCDT_Leave" @keydown="checkNumber"
                                style="text-align: right !important; padding-right: 5px">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="info-row">
                        <div class="key w130">
                            Cước TC (No VAT)
                        </div>
                        <div class="value">
                            <input type="text" class="form-control bold" v-model="model.v4_cuoc_tc_no_vat" id="v4_cuoc_tc_no_vat" 
                                @blur="txtCuocTC_Leave" @keydown="checkNumber"
                                style="text-align: right !important; padding-right: 5px">
                        </div>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="info-row">
                        <div class="key w130">Cước TK (No VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control bold" v-model="model.v4_cuoc_tk_no_vat" id="v4_cuoc_tk_no_vat" 
                                @blur="txtCuocTK_Leave" @keydown="checkNumber"
                                style="text-align: right !important; padding-right: 5px">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="info-row">
                        <div class="key w130">
                            Cước HT (No VAT)
                        </div>
                        <div class="value">
                            <input type="text" class="form-control bold" v-model="model.v4_cuoc_ht_no_vat" id="v4_cuoc_ht_no_vat" 
                                @blur="txtCuocHT_Leave" @keydown="checkNumber"
                                style="text-align: right !important; padding-right: 5px">
                        </div>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="info-row">
                        <div class="key w130">Cước T.Bị (No VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control bold" v-model="model.v4_cuoc_tbi_no_vat" id="v4_cuoc_tbi_no_vat" 
                                @blur="txtCuocTBI_Leave" @keydown="checkNumber"
                                style="text-align: right !important; padding-right: 5px">
                        </div>
                    </div>
                </div>
            </div>
        
        </div>
        <div class="box-form">
            <div class="legend-title">Chi tiết tiền theo khoản mục</div>
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="info-row">
                        <div class="key">Tổng tiền</div>
                        <div class="value">
                            <div class="input-more-button">
                                <button class="btn" @click.prevent="popupTienKM">
                                    <span class="-ap icon-more_horiz"></span>
                                </button>
                                <input type="text" class="form-control bold red tright"  v-model="model.v4_tien" readonly />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="info-row">
                        <div class="key w100">Tổng VAT</div>
                        <div class="value">
                            <input type="text" class="form-control bold red tright"  v-model="model.v4_vat" readonly />
                        </div>
                    </div>
                </div>
            </div>    
        </div>
    </div>
</template>
<script>
import { Query } from '@syncfusion/ej2-data'
export default {
    name:'ThongTinKhachHang',
    components:{

    },
    data() {
        return {
            model: {
                v4_ma_khach_hang: "",
                v4_ten_khach_hang: "",
                v4_dia_chi_khach_hang: "",
                v4_cuoc_tkdt: "",
                v4_ma_hop_dong_dt: "",
                v4_cuoc_tcdt: "",
                v4_cuoc_tc_no_vat: "0",
                v4_cuoc_tk_no_vat: "0",
                v4_cuoc_ht_no_vat: "0",
                v4_cuoc_tbi_no_vat: "0",
                v4_tien: "0",
                v4_vat: "0",
                v4_tong_tien: "0",
                v4_doi_tac: "",
            },
            v4_LstDoiTac: [],
            v4_enable_doi_tac: false,
        }
    },
    computed: {

    },
    methods: {
        onFilterV4DoiTac: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.v4_LstDoiTac, query);
        },
        popupTienKM() {
            this.$emit('popupTienKM');
        },
        chkDoiTac_CheckedChanged() {
            this.$emit('chkDoiTac_CheckedChanged');
        },
        formatNumber(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        },
        Unformat_tien(s){
            s = s.toLowerCase().trim();
            let ketqua = "";
            for (let i = 0; i < s.length; i++) {
                if (String(s[i]) != "," && String(s[i]) != "." && String(s[i]) != " ") {
                    ketqua = ketqua + s[i];
                }
            }
            return ketqua.trim();
        },
        checkNumber(event) {
            if ((event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 8 || event.keyCode == 46) {
                return true;
            } else {
                event.preventDefault();    
            }
        },
        txtCuocTKDT_Leave() {
            if (String(this.model.v4_cuoc_tkdt).trim() != "") {
                let s = String(this.model.v4_cuoc_tkdt).trim();
                s = this.Unformat_tien(s);
                if (!isNaN(s)) {
                    this.model.v4_cuoc_tkdt = this.formatNumber(s);
                } else {
                    this.$toast.error("Dữ liệu nhập phải là kiểu số");
                    this.model.v4_cuoc_tkdt = "0";
                    $("#v4_cuoc_tkdt").focus();
                    return;
                }
            }
        },
        txtCuocTCDT_Leave() {
            if (String(this.model.v4_cuoc_tcdt).trim() != "") {
                let s = String(this.model.v4_cuoc_tcdt).trim();
                s = this.Unformat_tien(s);
                if (!isNaN(s)) {
                    this.model.v4_cuoc_tcdt = this.formatNumber(s);
                } else {
                    this.$toast.error("Dữ liệu nhập phải là kiểu số");
                    this.model.v4_cuoc_tcdt = "0";
                    $("#v4_cuoc_tcdt").focus();
                    return;
                }
            }
        },
        txtCuocTK_Leave() {
            if (String(this.model.v4_cuoc_tk_no_vat).trim() != "") {
                let s = String(this.model.v4_cuoc_tk_no_vat).trim();
                s = this.Unformat_tien(s);
                if (!isNaN(s)) {
                    this.model.v4_cuoc_tk_no_vat = this.formatNumber(s);
                } else {
                    this.$toast.error("Dữ liệu nhập phải là kiểu số");
                    this.model.v4_cuoc_tk_no_vat = "0";
                    $("#v4_cuoc_tk_no_vat").focus();
                    return;
                }
            }
        },
        txtCuocTC_Leave() {
            if (String(this.model.v4_cuoc_tc_no_vat).trim() != "") {
                let s = String(this.model.v4_cuoc_tc_no_vat).trim();
                s = this.Unformat_tien(s);
                if (!isNaN(s)) {
                    this.model.v4_cuoc_tc_no_vat = this.formatNumber(s);
                } else {
                    this.$toast.error("Dữ liệu nhập phải là kiểu số");
                    this.model.v4_cuoc_tc_no_vat = "0";
                    $("#v4_cuoc_tc_no_vat").focus();
                    return;
                }
            }
        },
        txtCuocHT_Leave() {
            if (String(this.model.v4_cuoc_ht_no_vat).trim() != "") {
                let s = String(this.model.v4_cuoc_ht_no_vat).trim();
                s = this.Unformat_tien(s);
                if (!isNaN(s)) {
                    this.model.v4_cuoc_ht_no_vat = this.formatNumber(s);
                } else {
                    this.$toast.error("Dữ liệu nhập phải là kiểu số");
                    this.model.v4_cuoc_ht_no_vat = "0";
                    $("#v4_cuoc_ht_no_vat").focus();
                    return;
                }
            }
        },
        txtCuocTBI_Leave() {
            if (String(this.model.v4_cuoc_tbi_no_vat).trim() != "") {
                let s = String(this.model.v4_cuoc_tbi_no_vat).trim();
                s = this.Unformat_tien(s);
                if (!isNaN(s)) {
                    this.model.v4_cuoc_tbi_no_vat = this.formatNumber(s);
                } else {
                    this.$toast.error("Dữ liệu nhập phải là kiểu số");
                    this.model.v4_cuoc_tbi_no_vat = "0";
                    $("#v4_cuoc_tbi_no_vat").focus();
                    return;
                }
            }
        },
    },
    mounted() {
        
    },
}
</script>