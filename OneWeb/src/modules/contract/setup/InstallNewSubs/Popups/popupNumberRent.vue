<template>

<div class="modal-content popup-box">
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="btnChapNhan">
                                <span class="icon one-save"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="#" @click.prevent="btnThoat">
                                <span class="icon one-users"></span> Thoát
                            </a>
                        </li>
                       
                    </ul>
                </div>
                <div class="popup-body">
                   
                    <div class="box-form">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-down">

                            </span>
                            Thông tin thuê số
                        </div>
                       <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                <input type="radio" id="one" value="1" v-model="isTruotSo" @click="rdTruotSo_Clicked" />
                                <span class="name">Trượt số</span>
                                </div>
                            </div>
                            <div class="value">
                                <input type="text" v-model="TruotSo" ref="TruotSo" class="form-control" :class="{ disabled: !isSoTruot }" />
                            </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                            <div class="key">Thời gian thuê</div>
                            <div class="value">
                                <input type="text" v-model="ThoiGianThue" ref="ThoiGianThue" class="form-control" :class="{ disabled: !isThoiGianThue }" @keypress="validateNumber" />
                            </div>
                            </div>
                        </div>
                        </div>
                        <div class="row">
                        <div class="col-12">
                            <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                <input type="radio" id="one" value="2" v-model="isTruotSo" @click="rdThongBao_Clicked" />
                                <span class="name">Thông báo</span>
                                </div>
                            </div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="ThongBao" ref="ThongBao" :class="{ disabled: !isThongBao }" />
                            </div>
                            </div>
                        </div>
                        </div>

<!-- 
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Account</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight" v-model= "Account" ref= "Account" v-on:keyup.enter="onAccountEnter">
                                    </div>
                                </div>
                            </div>
                        </div>
 -->

                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-down">

                            </span>
                            Danh sách thuê bao
                        </div>
                        <div class="table-content" style="height: 200px;">
                            <DataGrid  class="table-result table-gachle"
                                v-bind:columns="ds_thuebao.header"                    
                                v-bind:dataSource="ds_thuebao.list"
                                :enable-paging-server="false"                                        
                                :allowPaging="true"
                                :showFilter="true"
                                :showColumnCheckbox="false"
                                ref="dsthuebao"
                                @selectedItemsChanged="selectedItems_click"
                                @selectedRowChanged="gridChanged"
                                @rowClicked="rowClicked" 
                            >
                            </DataGrid>  
                    </div>
                    </div>
                </div>
            </div>

</template>
<style>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
    .e-grid .e-gridheader .e-sortfilter .e-headercelldiv, .e-grid .e-gridheader .e-stackedheadercelldiv, .e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv 
    {
    padding: 0 5px 0 5px !important;
    }
    
</style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import popupAssignSubManager from './popupAssignSubManager.vue'
import moment from 'moment'

import API from './API'
import { Checkbox } from 'element-ui'
export default {
    name: 'popupServiceOthers',
    components: { API,required, minLength, integer, maxValue,DatePicker,VueFlatPickr,breadcrumb,popupAssignSubManager },
    props: [ 'dulieu' ],
    watch: {
        
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput:true,
            },
            toptarget:'#app',
            toptarget1:'#app .SearchGeneral',

            success_code: "BSS-00000000",

            ds_thuebao: {
                list: [],
                header: [ {fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, width: 100}, 
                            {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true},
                            ],
                value: {},
                isEnabled: true,
                checked: []
                },
                
            TruotSo: "",
            ThoiGianThue: "",
            ThongBao: "",
            LoaiHD: {
                list: [],
                value: {},
            },
            isTruotSo: 1,
            isThoiGianThue: true,
            isSoTruot: true,
            isThongBao: false,

            hdkh_id: "0",
            vma_kh: "",
            ts_kt_tthdkhdn: 0,
            matb: "",
            THUESO_DT_CU_SAU_DC: 540,
            THUESO_DT_CU_SAU_TL: 557,
            TRUOTSO : 1,
            THONGBAO : 2,
            TrangThaiHD_MOI: 1,
            dsthueso: [],

            //Dữ liệu truyền vào
            vkieuld_id: "",
            vkhachhang_id: "",
            //Dữ liệu trả ra
            vma_tb: "",
            vnhomso_id: "",
            vdonvi_id: "",
            noidung_thueso: "",
            sothang_thueso: 0,
            kieu_thueso: "",
        }
    },
    computed: {
        iTruotSo: {
            get() { return this.isTruotSo; },
            set(value) { this.isTruotSo=2; }
        },
    },
    dateConfig: {
            altInput: true,
            dateFormat: 'd/m/Y',
            altFormat: 'd/m/Y'
        },
    async mounted() {
        this.vkhachhang_id = this.dulieu.vkhachhang_id? this.dulieu.vkhachhang_id : ""; //439626; 
        this.vkieuld_id = this.dulieu.vkieuld_id? this.dulieu.vkieuld_id : ""; //540; 
        this.vma_tb = this.dulieu.vma_tb ? this.dulieu.vma_tb : "";
        var input = {};
        input.vkhachhang_id = this.vkhachhang_id;
        input.vkieuld_id = this.vkieuld_id;
        // console.log(input);
        this.loading(true);
        await API.lay_thuebao_thueso(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ds_thuebao.list = response.data.data; 
                if(this.vma_tb)
                {
                    var list = this.ds_thuebao.list.filter(x=>x['ma_tb']==this.vma_tb);
                    if(list && list.length>0)
                    {
                        this.gridChanged(list[0]);
                        //FIXME can select dong du lieu tim duoc!
                    }
                }
            }
            else
            {
                this.ShowAlert("Lỗi lấy danh sách loại hình dịch vụ!",2);
            }
        }
        ); 
    },
    methods: {
        closeForm() {
            this.$emit('form-close',this.returnString);
        },
        formatPrice(value) {
            let val = (value/1).toFixed(0).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        validateNumber: event => {
            let keyCode = event.keyCode;
            if (keyCode < 48 || keyCode > 57) {
                event.preventDefault();
            }
        },
        ShowAlert(mesage,val)
        {
            if (val==0)
            {
                this.$toast.success(mesage);
            }
            else if (val==1)
            {
                this.$toast.warning(mesage);
            }
            else if (val==2)
            {
                this.$toast.error(mesage);
            }
        },
        rdThongBao_Clicked()
        {
            this.ThoiGianThue = "";
            this.ThongBao = "";
            this.TruotSo = "";
            this.isThongBao = true;
            this.isSoTruot = false;
        },
        rdTruotSo_Clicked()
        {
            this.ThoiGianThue = "";
            this.ThongBao = "";
            this.TruotSo = "";
            this.isThongBao = false;
            this.isSoTruot = true;
        },
        gridChanged(obj)
        {
            if(!obj) { 
                return; 
            }
            this.matb = obj.ma_tb;
            this.vma_tb = obj.ma_tb;
            this.vnhomso_id = obj.nhomso_id;
            this.vdonvi_id = obj.donvi_id;
        },
        rowClicked(i,obj)
        {
            if(!obj) { 
                return; 
            }
            this.matb = obj.ma_tb;
            this.vma_tb = obj.ma_tb;
            this.vnhomso_id = obj.nhomso_id;
            this.vdonvi_id = obj.donvi_id;
        },
        selectedItems_click: async function()
        {
            var input = {};
            input.kieuld_id = this.THUESO_DT_CU_SAU_DC + "," + this.THUESO_DT_CU_SAU_TL; //"597,12"; 
            input.ma_tb = this.matb; //"3857063"; 
            input.trangthaihd_id = this.TrangThaiHD_MOI; //6; 
            // input.phanvung_id = 26; //Khong can vi xu ly o API roi
            this.loading(true);
            await API.sp_lay_thongtin_hdtb_thueso(this.axios, input).then((response) => {
                this.loading(false);
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.dsthueso = response.data.data; 
                    if (this.dsthueso.length>0)
                    {
                        if (this.dsthueso[0].sotruot !="")
                        {
                            this.ThoiGianThue = this.dsthueso[0].tg_thueso;
                            this.sotruot = this.dsthueso[0].sotruot;
                            this.ThongBao = "";
                        }
                        if (this.dsthueso[0].noidung_tb !="")
                        {
                            this.ThoiGianThue = this.dsthueso[0].tg_thueso;
                            this.sotruot = ""
                            this.ThongBao = this.dsthueso[0].noidung_tb;
                        }
                        if (this.dsthueso[0].sotruot =="" && this.dsthueso[0].noidung_tb =="")
                        {
                            this.ThoiGianThue = "";
                            this.sotruot = ""
                            this.ThongBao = "";
                        }
                    }
                    else
                    {
                        this.ThoiGianThue = "";
                        this.sotruot = ""
                        this.ThongBao = "";
                    }
                }
                else
                {
                    this.ShowAlert("Không tìm thấy thông tin thuê số",2);
                }
            }
            ); 
        },

        btnChapNhan()
        {
            if (this.ThoiGianThue == "")
            {
                this.ShowAlert("Bạn chưa nhập thời gian thuê số!",2);
                return;
            }
            if (this.isSoTruot && this.TruotSo == "")
            {
                this.ShowAlert("Bạn chưa nhập Số trượt!",2);
                return;
            }
            if (this.isThongBao && this.ThongBao == "")
            {
                this.ShowAlert("Bạn chưa nhập Thông báo!",2);
                return;
            }
            if (this.isThongBao)
            {
                this.noidung_thueso = this.ThongBao;
            }
            if (this.isSoTruot)
            {
                this.noidung_thueso = this.sotruot;
                this.kieu_thueso = this.TRUOTSO;
            }
            else
            {
                this.kieu_thueso = this.THONGBAO;
            }
            this.sothang_thueso = this.ThoiGianThue;

            //console.log(this.vma_tb,this.vnhomso_id,this.vdonvi_id,this.noidung_thueso,this.sothang_thueso,this.kieu_thueso);
            this.$emit('form-close',
            {
                "vma_tb":this.vma_tb,
                "vnhomso_id":this.vnhomso_id,
                "vdonvi_id":this.vdonvi_id,
                "noidung_thueso":this.noidung_thueso,
                "sothang_thueso":this.sothang_thueso,
                "kieu_thueso":this.kieu_thueso,
            });
            
        },
        btnThoat() {
            this.$emit('form-close',null);
        },
    }
}
</script>