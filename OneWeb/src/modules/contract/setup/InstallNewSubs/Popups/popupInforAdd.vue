<template>

<div class="modal-content popup-box">
    <div class="popup-header"> 
        <div class="title"><span class="icon one-notepad"></span> Thông tin hợp đồng</div> 
        <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm"> 
        </div> 
    </div> 
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btnSave">
                    <span class="icon one-save"></span> Ghi lại
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnGanNVQL">
                    <span class="icon one-users"></span> Gán NV QL
                </a>
            </li>
            
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form" v-if="isbancheo">
            <div class="info-row">
                <div class="key">Số phòng</div>
                <div class="value">
                    <input type="number" v-model="SoPhong" ref="SoPhongBC" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key">Chủ quản</div>
                <div class="value">
                    <SelectExt class="select-custom" v-model="cboChuQuan.value" :dataSource="cboChuQuan.list" dataTextField="ten_tat" dataValueField="chuquan_id"></SelectExt>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Kênh bán</div>
                <div class="value">
                    <SelectExt class="select-custom" @change="v=>cbokenhBan.value=(cbokenhBan.value?cbokenhBan.value:v)" v-model="cboKenhThu.value" :dataSource="cboKenhThu.list" dataTextField="kenhban" dataValueField="kenhban_id"></SelectExt>
                </div>
            </div>
        </div>
        <div class="box-form" v-if="!isbancheo">
            
            <div class="info-row">
                <div class="key">Số phòng</div>
                <div class="value">
                    <input type="number" v-model="SoPhong" ref="SoPhong" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key">Mã ngân sách</div>
                <div class="value">
                    <input type="number" v-model="MaNganSach" ref="MaNganSach" class="form-control" maxlength="7" @keypress="validateNumber">
                </div>
            </div>            
            <div class="info-row">
                <div class="key">Kênh bán</div>
                <div class="value">
                    <SelectExt class="select-custom" v-model="cbokenhBan.value" :dataSource="cbokenhBan.list" dataTextField="kenhban" dataValueField="kenhban_id"></SelectExt>
                </div>
            </div>
                
            
        </div>
        <div class="box-form" v-if="vkieukh_id!=0">
            <div class="legend-title">
                <span class="icon fa fa-angle-down">

                </span>
                Thông tin bổ sung HD KHDN
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số hợp đồng</div>
                        <div class="value">
                            <input type="text" v-model="SoHopDong" ref="SoHopDong" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số HD giấy</div>
                        <div class="value">
                            <input type="text" v-model="SoHopDongGiay" ref="SoHopDongGiay"  class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key"></div>
                        <div class="value">
                            <div class="list-checks mart7">
                                <div class="item">
                                    <div class="check-action">
                                        <input type="radio" id="one" value= 1 v-model="isKenh">
                                        <span class="name">Kênh trong</span>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="check-action">
                                        <input type="radio" id="two" value= 2 v-model="isKenh">
                                        <span class="name">Kênh ngoài</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Khối</div>
                        <div class="value">
                            <SelectExt class="select-custom" v-model="cboKhoi.value" :dataSource="cboKhoi.list" dataTextField="tenkhoi" dataValueField="khoi_id"></SelectExt>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Loại kênh</div>
                        <div class="value">
                            <div class="select-custom" :class="{'disabled':isKenh==1}">
                            <select2 :options="LoaiKenh.list" v-model="LoaiKenh.value" > 
                                <option disabled value="0">Select one</option> 
                            </select2>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Kênh ID</div>
                        <div class="value">
                            <div class="select-custom" :class="{'disabled':isKenh==1}">
                                <select2 :options="KenhID.list" v-model="KenhID.value" > 
                                <option disabled value="0">Select one</option> 
                                </select2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form" v-if="ts_kt_tthdkhdn!=1">
            <div class="legend-title">
                <span class="icon fa fa-angle-down">

                </span>
                Thông tin người giới thiệu
            </div>
            <div class="info-row">
                <div class="key">Người giới thiệu</div>
                <div class="value">
                    <input type="text" v-model="NguoiGioiThieu" ref="NguoiGioiThieu" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key">Chức vụ</div>
                <div class="value">
                    <input type="text" v-model="ChucVu" ref="ChucVu" class="form-control">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số giấy tờ</div>
                        <div class="value">
                            <input type="text" v-model="SoGiayTo" ref="SoGiayTo" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Ngày cấp</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <date-picker input-class="form-control" :format="datetimeFormat" :value-type="'format'" v-model="NgayCap"></date-picker>
                                <!-- <flat-pickr class="form-control" :config="dateconfig" v-model="NgayCap" /> -->
                                <span class="icon one-calendar"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>

        <div class="box-form" v-if="ts_kt_tthdkhdn==1">
            <div class="legend-title">
                <span class="icon fa fa-angle-down">

                </span>
                Thông tin hợp đồng khách hàng doanh nghiệp
            </div>
                <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số HĐ</div>
                        <div class="value">
                            <input type="text" v-model="SoHD" ref="SoHD" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số phụ lục</div>
                        <div class="value">
                            <input type="text" v-model="SoPhuLuc" ref="SoPhuLuc" class="form-control">
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Giá trị HĐ</div>
                        <div class="value">
                            <input type="text" v-model="GiaTriHD" ref="GiaTriHD" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Ngày ký HĐ</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <date-picker input-class="form-control" :format="datetimeFormat"  :value-type="'format'" v-model="NgayKyHD"></date-picker>
                                <!-- <flat-pickr class="form-control" :config="dateconfig" v-model="NgayKyHD" /> -->
                                <span class="icon one-calendar"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Giấy ủy quyền</div>
                <div class="value">
                    <input type="text" v-model="GiayUyQuyen" ref="GiayUyQuyen" class="form-control">
                </div>
            </div>

            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Loại HĐ</div>
                        <div class="value">
                            <div class="select-custom">
                            <select2 :options="LoaiHD.list" v-model="LoaiHD.value" > 
                                <option disabled value="0">Select one</option> 
                            </select2>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Tình trạng HĐ</div>
                        <div class="value">
                            <div class="select-custom">
                            <select2 :options="TinhTrangHD.list" v-model="TinhTrangHD.value" > 
                                <option disabled value="0">Select one</option> 
                            </select2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <ejs-dialog :enableResize='true' :allowDragging='true' :visible="false" ref="popupAssignSubManager" :position="{ X: 'center', Y: 0 }" 
    :header='"Gán nhân viên quản lý thuê bao"' showCloseIcon=true width='80%' height="800">
    <div>
        <popupAssignSubManager :dulieu="popupAssignSubManagerData"/>
    </div>
    </ejs-dialog>
</div>
</template>
<style>
    .disabled, input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
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
import API from './API'
import moment from 'moment'
import { Checkbox } from 'element-ui'
export default {
    name: 'popupInforAdd',
    components: { API,required, minLength, integer, maxValue,DatePicker,VueFlatPickr,breadcrumb,popupAssignSubManager },
    props: [ 'dulieu', 'isbancheo' ],
    watch: {
        
    },
    data() {
        return {
            datetimeFormat: "DD/MM/YYYY",
            datetimeFormatFull: "YYYY-MM-DD HH:mm:ss",
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput:true,
            },
            success_code: "BSS-00000000",
            DOANH_NGHIEP: 1,
            SoPhong: "",
            MaNganSach: "",
            SoHopDong: "",
            SoHopDongGiay:"",
            NguoiGioiThieu:"",
            position: { X: 'center', Y: 'top' },
            ChucVu:"",
            SoGiayTo:"",
            SoHD: "",
            toptarget1: document.querySelector('body'),
            SoPhuLuc: "",
            GiaTriHD: "0",
            NgayKyHD: moment(new Date()).format("DD/MM/YYYY"),//moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
            NgayCap: moment(new Date()).format("DD/MM/YYYY"),//moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
            GiayUyQuyen: "",
            LoaiHD: {
                list: [],
                value: 0,
            },
            TinhTrangHD: {
                list: [],
                value: 0,
            },
            LoaiKenh: {
                list: [{id:1,text:"CTV"},{id:2,text:"Đại lý"}],
                value: 1,
            },
            KenhID: {
                list: [],
                value: 0,
            },
            isKenh: 1,
            //Dữ liệu truyền vào
            nhapmoi: false,
            hdkh_id: "0",
            vma_kh: "",
            vkieukh_id: 0,
            ts_kt_tthdkhdn: 0,
            phanvung_id: 0,
            hopdongkh: [],
            returnString: "", //dữ liệu trả ra
            popupAssignSubManagerData: {vtimkiem_hdkh_id:-1},
            cboKhoi: {
                list: [], value: 0,
            },
            cbokenhBan: {
                list: [], value: 0,
            },
            cboChuQuan: {
                list: [], value: 0,
            },
            cboKenhThu: {
                list: [], value: 0,
            }
        }
    },
    computed: {
        KenhTrong: {
            get() { return this.isKenh; },
            set(value) { this.isKenh=2; }
        },
      
    },
    dateConfig: {
            altInput: true,
            dateFormat: 'd/m/Y',
            altFormat: 'd/m/Y'
        },
    async mounted() {
        this.hdkh_id = this.dulieu._hdkh_id?this.dulieu._hdkh_id:0; //"5125881";
        this.nhapmoi = this.dulieu._nhapmoi?this.dulieu._nhapmoi:false; //false;
        this.vma_kh = this.dulieu._vma_kh?this.dulieu._vma_kh:0; //"thangdm1"; 
        this.vkieukh_id = this.dulieu._vkieukh_id?this.dulieu._vkieukh_id:0; //"0"; 
        //kiemtra don vi vnpt i 
        this.phanvung_id = this.$root.token.getPhanVungID();
        this.ts_kt_tthdkhdn = this.dulieu._ts_kt_tthdkhdn && this.phanvung_id != 98 ?this.dulieu._ts_kt_tthdkhdn :0; //1; 
        console.log(this.ts_kt_tthdkhdn);
        await this.LoadCombobox();
        await this.LoadCbKenhBan();
        await this.LoadChuQuan();
        await this.LoadForm();
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
        LoadCombobox: async function ()
        {
            var input = {};
            input.donvi_id = -1;
            input.loainhom_nv_id = 6;
            this.loading(true);
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_khoi_loaikh",{ "vloaikh_id": this.dulieu.loaikh, "vkieu": this.dulieu._vkieukh_id });
            if(rs.data) {
                this.cboKhoi.list = rs.data;
                if(this.cboKhoi.list && this.cboKhoi.list.length>0) 
                    this.cboKhoi.value = this.cboKhoi.list[0].khoi_id;
                else {
                    this.cboKhoi.list = [];
                    this.cboKhoi.value = 0;
                }
            }
            else {
                this.cboKhoi.list = [];
                this.cboKhoi.value = 0;
            }
            await API.sp_ht_ds_nhanvien_theo_nhomlnv(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.KenhID.list = response.data.data.map(x=>({id:x.nhanvien_id,text:x.ten_ht}));
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách kênh!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
            if (this.ts_kt_tthdkhdn ==1)
            {
                this.loading(true);
                await API.CSS_LOAI_HDKH(this.axios, {}).then((response) => {
                    this.loading(false);    
                    if(response.data.error_code && response.data.error_code === this.success_code) {
                        this.LoaiHD.list = response.data.data.map(x=>({id:x.LOAIHDKH_ID,text:x.TEN_LOAIHDKH}));
                    }
                    else
                    {
                        this.ShowAlert("Lỗi lấy danh sách loại hợp đồng khách hàng!",2);
                    }
                }
                ).finally(()=>{  this.loading(false); });
                //----------
                this.loading(true);
                await API.CSS_TINHTRANG_HD(this.axios, {}).then((response) => {
                    this.loading(false);    
                    if(response.data.error_code && response.data.error_code === this.success_code) {
                        this.TinhTrangHD.list = response.data.data.map(x=>({id:x.TINHTRANGHD_ID,text:x.TINHTRANG_HD}));
                    }
                    else
                    {
                        this.ShowAlert("Lỗi lấy danh sách tình trạng hợp đồng!",2);
                    }
                }
                ).finally(()=>{  this.loading(false); });
            }
        },
        LoadForm: async function ()
        {
            console.info('LoadForm',this.nhapmoi);
            try {
                if (this.nhapmoi)
                {
                    var input = {};
                    input.hdkh_id = this.hdkh_id;
                    this.loading(true);
                    if(this.hdkh_id && this.hdkh_id>0)
                    await API.lay_hdkh_sub_theo_hdkh_id(this.axios, input).then((response) => {
                        this.loading(false);    
                        if(response.data.error_code && response.data.error_code === this.success_code) {
                            this.hopdongkh = response.data.data;
                        }
                        // else
                        // {
                        //     this.ShowAlert("Chưa có thông tin hợp đồng khách hàng!",2);
                        // }
                    }
                    ).finally(()=>{  this.loading(false); });
                    this.loading(false); 
                    if(!(this.hopdongkh && this.hopdongkh.length>0) && this.dulieu.dsHDKH_Sub && this.dulieu.dsHDKH_Sub.length>0)
                        this.hopdongkh = this.dulieu.dsHDKH_Sub;
                    console.info('Load Form',this.hopdongkh, this.dulieu.dsHDKH_Sub);
                    if (this.hopdongkh && this.hopdongkh.length>0)
                    {
                        this.SoPhong = this.hopdongkh[0].SO_PHONG;
                        this.MaNganSach= this.hopdongkh[0].MA_NS;
                        this.SoHopDong= this.hopdongkh[0].SO_HD_KHDN;
                        this.SoHopDongGiay= this.hopdongkh[0].SO_PL_KHDN;
                        this.NguoiGioiThieu= this.hopdongkh[0].NGUOI_GT;
                        this.ChucVu= this.hopdongkh[0].CHUCVU_GT;
                        this.SoGiayTo= this.hopdongkh[0].SOGIAY_GT;
                        this.NgayCap=this.hopdongkh[0].NGAYCAP_GT;
                        this.LoaiKenh.value =this.hopdongkh[0].LOAIKENH;
                        this.KenhID.value = this.hopdongkh[0].KENH_ID;
                        this.isKenh = this.hopdongkh[0].KENH_PT;
                        this.cboKhoi.value = this.hopdongkh[0].KHOI_ID;
                        this.cbokenhBan.value = this.hopdongkh[0].KENHBAN_ID;
                        if(this.isbancheo) {
                            if(this.hopdongkh[0].KENHBAN_ID) this.cboKenhThu.value = this.hopdongkh[0].KENHBAN_ID;
                            if(this.hopdongkh[0].CHUQUAN_ID) this.cboChuQuan.value = this.hopdongkh[0].CHUQUAN_ID;
                        }
                        //Load thông tin khách hàng doanh nghiệp
                        if (this.ts_kt_tthdkhdn == 1)
                        {
                            this.SoHD = this.hopdongkh[0].SO_HD_KHDN;
                            this.SoPhuLuc = this.hopdongkh[0].SO_PL_KHDN;
                            this.GiaTriHD = this.hopdongkh[0].GIATRI_HD;
                            if (this.hopdongkh[0].NGAY_KY_HD !=null)
                            {
                                this.NgayKyHD = this.hopdongkh[0].NGAY_KY_HD;
                            }
                            this.GiayUyQuyen = this.hopdongkh[0].GIAY_UQ;
                            this.GiayUyQuyen = this.hopdongkh[0].GIAY_UQ;
                            if (this.hopdongkh[0].LOAIHDKH_ID !=null)
                            {
                                this.LoaiHD.value = this.hopdongkh[0].LOAIHDKH_ID;
                            }
                            if (this.hopdongkh[0].TINHTRANGHD_ID !=null)
                            {
                                this.TinhTrangHD.value = this.hopdongkh[0].TINHTRANGHD_ID;
                            }

                        }
                    }
                    else
                    {
                        if (this.vkieukh_id == 1)
                        {
                            input = {};
                            input.vkieu = 1;
                            this.loading(true);
                            API.sinh_so_hd_khdn(this.axios, input).then((response) => {
                                this.loading(false);    
                                if(response.data.error_code && response.data.error_code === this.success_code) {
                                    this.SoHopDong = response.data.data;
                                    if(!this.SoHD) this.SoHD = this.SoHopDong;
                                }
                                else
                                {
                                    this.ShowAlert("Lỗi sinh số hợp đồng!",2);
                                }
                            }
                            ).finally(()=>{  this.loading(false); });
                        }
                    }
                }
                else
                {
                    var input = {};
                    input.hdkh_id = this.hdkh_id;
                    this.loading(true);
                    await API.lay_hdkh_sub_theo_hdkh_id(this.axios, input).then((response) => {
                        this.loading(false);    
                        if(response.data.error_code && response.data.error_code === this.success_code) {
                            this.hopdongkh = response.data.data;
                            if (this.hopdongkh && this.hopdongkh.length>0)
                            {
                                this.SoPhong = this.hopdongkh[0].SO_PHONG;
                                this.MaNganSach= this.hopdongkh[0].MA_NS;
                                this.SoHopDong= this.hopdongkh[0].SO_HD_KHDN;
                                this.SoHopDongGiay= this.hopdongkh[0].SO_PL_KHDN;
                                this.NguoiGioiThieu= this.hopdongkh[0].NGUOI_GT;
                                this.ChucVu= this.hopdongkh[0].CHUCVU_GT;
                                this.SoGiayTo= this.hopdongkh[0].SOGIAY_GT;
                                this.NgayCap=this.hopdongkh[0].NGAYCAP_GT;
                                this.LoaiKenh.value =this.hopdongkh[0].LOAIKENH;
                                this.KenhID.value = this.hopdongkh[0].KENH_ID;
                                this.isKenh = this.hopdongkh[0].KENH_PT;
                                this.cboKhoi.value = this.hopdongkh[0].KHOI_ID;
                                this.cbokenhBan.value = this.hopdongkh[0].KENHBAN_ID;

                        if(this.isbancheo) {
                            if(this.hopdongkh[0].KENHBAN_ID) this.cboKenhThu.value = this.hopdongkh[0].KENHBAN_ID;
                            if(this.hopdongkh[0].CHUQUAN_ID) this.cboChuQuan.value = this.hopdongkh[0].CHUQUAN_ID;
                        }
                        
                                //Load thông tin khách hàng doanh nghiệp
                                if (this.ts_kt_tthdkhdn == 1)
                                {
                                    this.SoHD = this.hopdongkh[0].SO_HD_KHDN;
                                    this.SoPhuLuc = this.hopdongkh[0].SO_PL_KHDN;
                                    this.GiaTriHD = this.hopdongkh[0].GIATRI_HD;
                                    if (this.hopdongkh[0].NGAY_KY_HD !=null)
                                    {
                                        this.NgayKyHD = this.hopdongkh[0].NGAY_KY_HD;
                                    }
                                    this.GiayUyQuyen = this.hopdongkh[0].GIAY_UQ;
                                    this.GiayUyQuyen = this.hopdongkh[0].GIAY_UQ;
                                    if (this.hopdongkh[0].LOAIHDKH_ID !=null)
                                    {
                                        this.LoaiHD.value = this.hopdongkh[0].LOAIHDKH_ID;
                                    }
                                    if (this.hopdongkh[0].TINHTRANGHD_ID !=null)
                                    {
                                        this.TinhTrangHD.value = this.hopdongkh[0].TINHTRANGHD_ID;
                                    }

                                }
                            }
                        }
                        // else
                        // {
                        //     this.ShowAlert("Chưa có thông tin hợp đồng khách hàng!",2);
                        // }
                    }
                    ).finally(()=>{  this.loading(false); });
                }
            } catch(ex) { this.$toast.error("Lỗi lấy dữ liệu: "+ex);}
            finally { this.loading(false); }
        },

        btnSave: async function ()
        {
            if (this.ts_kt_tthdkhdn == 1) // Tham số kiểm tra bộ 3 Số HĐ, MA-KH, Ngày ký không được trùng
            {
                if (!this.SoHD)
                {
                    this.ShowAlert("Bạn chưa nhập thông tin Số hợp đồng",1);
                    // this.$refs.SoHopDong.focus();
                    return;
                }
                if(!this.NgayKyHD) {
                    this.ShowAlert("Bạn chưa nhập thông tin Ngày ký",1);
                    // this.$refs.SoHopDong.focus();
                    return;
                }
                if(!this.LoaiHD.value || this.LoaiHD.value<0) {
                    
                    this.ShowAlert("Bạn chưa nhập thông tin Loại hợp đồng",1);
                    return;
                }
                if(!this.TinhTrangHD.value || this.TinhTrangHD.value<0) {
                    
                    this.ShowAlert("Bạn chưa nhập thông tin Tình trạng",1);
                    return;
                }
                if(!this.SoPhuLuc) {

                    this.ShowAlert("Bạn chưa nhập số phụ lục",1);
                    return;
                }
                // if (this.vma_kh =="")
                // {
                //     this.ShowAlert("Chưa có thông tin Mã khách hàng",1);
                //     return;
                // }
                if (this.isKenh==2 && (!this.KenhID.value || this.KenhID.value == "-1"))
                {
                    this.ShowAlert("Hãy chọn kênh ID trước khi cập nhật",1);
                    return;
                }
            }
            if (!this.nhapmoi)
            {
                if (this.vkieukh_id == this.DOANH_NGHIEP)
                {
                    if (this.dulieu.ts_kt_mstmns_kh == 1 && !this.MaNganSach && !this.dulieu.mast)
                    {
                        this.ShowAlert("Bạn phải nhập mã số thuế hoặc mã ngân sách",1);
                        this.$refs.MaNganSach.focus();
                        return;
                    }
                    if(this.MaNganSach) {
                        var temp = this.MaNganSach.substring(0,1);
                        if (this.MaNganSach.length < 7 || temp =="0" || temp =="4" || temp =="5" || temp =="6")
                        {
                            this.ShowAlert("Mã ngân sách phải đủ 7 ký tự số bắt đầu bằng 1,2,3,7,8,9",1);
                            this.$refs.MaNganSach.focus();
                            return;
                        }
                    }
                }
                //Kiểm tra điều kiện số hợp đồng giấy
                var input = {};
                input.vso_hd = this.SoHopDongGiay;
                this.loading(true);
                await API.kiemtra_so_hd_khdn(this.axios, input).then((response) => {
                    this.loading(false);    
                    if(response.data.error_code && response.data.error_code === this.success_code) {
                        if (response.data.data != "OK")
                        {
                            this.ShowAlert(response.data.message ,0);
                            return;
                        }
                    }
                    else
                    {
                        this.ShowAlert("Lỗi lấy thông tin điều kiện hợp đồng giấy!",2);
                    }
                }
                ).finally(()=>{  this.loading(false); });
                //Cập nhật hợp đồng
                var input = {};
                var pdata = {
                    HDKH_ID:this.nhapmoi? 0 : this.hdkh_id,
                    SO_PHONG:this.SoPhong,
                    NGUOI_GT: this.NguoiGioiThieu,
                    CHUCVU_GT:this.ChucVu,
                    SOGIAY_GT:this.SoGiayTo,
                    NGAYCAP_GT: moment(this.NgayCap).isValid()?moment(this.NgayCap).format("DD/MM/YYYY"):this.NgayCap,//moment(this.NgayCap).format("DD/MM/YYYY"),
                    MA_NS:this.MaNganSach,
                    SO_HD_KHDN:this.SoHopDong,
                    SO_PL_KHDN: this.SoHopDongGiay,
                    KENH_PT:this.isKenh,
                    LOAIKENH:this.LoaiKenh.value,
                    KENH_ID:this.KenhID.value,
                    KHOI_ID:this.cboKhoi.value,
                    KENHBAN_ID:this.cbokenhBan.value,
                };
                if (this.ts_kt_tthdkhdn == 1)
                {
                    pdata.MA_KHDN = this.vma_kh;
                    pdata.GIATRI_HD=this.GiaTriHD;
                    pdata.NGAY_KY_HD=moment(this.NgayKyHD).isValid()?moment(this.NgayKyHD).format("DD/MM/YYYY"):this.NgayKyHD;
                    pdata.GIAY_UQ=this.GiayUyQuyen;
                    pdata.LOAIHDKH_ID=this.LoaiHD.value;
                    pdata.TINHTRANGHD_ID=this.TinhTrangHD.value;
                    pdata.SO_PL_KHDN=this.SoPhuLuc;
                    pdata.LOAINV_CTV_HRM='';
                    pdata.LOAINV_NGT_HRM='';
                    pdata.KHOI_ID=this.cboKhoi.value;
                    pdata.KENHBAN_ID=this.cbokenhBan.value;
                }
                if(this.isbancheo) {
                    if(this.cboKenhThu.value && this.cboKenhThu.value > 0)
                        pdata.KENHBAN_ID = this.cboKenhThu.value;
                    if(this.cboChuQuan.value && this.cboChuQuan.value>0)
                        pdata.CHUQUAN_ID = this.cboChuQuan.value;   
                }
                input.js_hdkh_sub = JSON.stringify(pdata);
                this.returnString = input.js_hdkh_sub;
                this.loading(true);
                await API.sp_insert_hdkh_sub(this.axios, input).then((response) => {
                    this.loading(false);    
                    if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.ShowAlert("Cập nhật thành công!",0);
                    }
                    else
                    {
                        this.ShowAlert("Lỗi cập nhật!",2);
                    }
                }
                ).finally(()=>{  this.loading(false); });
            }
            else //trả dữ liệu ra???
            {
                var pdata = { 
                    HDKH_ID:this.nhapmoi? 0 : this.hdkh_id, 
                    SO_PHONG:this.SoPhong, 
                    NGUOI_GT: this.NguoiGioiThieu, 
                    CHUCVU_GT:this.ChucVu, 
                    SOGIAY_GT:this.SoGiayTo, 
                    NGAYCAP_GT: moment(this.NgayCap).isValid()?moment(this.NgayCap).format("DD/MM/YYYY"):this.NgayCap,//moment(this.NgayCap).format("DD/MM/YYYY"), 
                    MA_NS:this.MaNganSach, 
                    SO_HD_KHDN:this.SoHopDong, 
                    SO_PL_KHDN: this.SoHopDongGiay,
                    KENH_PT:this.isKenh, 
                    LOAIKENH:this.LoaiKenh.value, 
                    KENH_ID:this.KenhID.value, 
                    KHOI_ID:this.cboKhoi.value,
                    KENHBAN_ID:this.cbokenhBan.value,
                }; 
                if (this.ts_kt_tthdkhdn == this.DOANH_NGHIEP) 
                { 
                    pdata.MA_KHDN = this.vma_kh; 
                    pdata.GIATRI_HD=this.GiaTriHD; 
                    pdata.NGAY_KY_HD=moment(this.NgayKyHD).isValid()?moment(this.NgayKyHD).format("DD/MM/YYYY"):this.NgayKyHD;
                    pdata.GIAY_UQ=this.GiayUyQuyen; 
                    pdata.LOAIHDKH_ID=this.LoaiHD.value; 
                    pdata.TINHTRANGHD_ID=this.TinhTrangHD.value; 
                    pdata.SO_PL_KHDN=this.SoPhuLuc; 
                    pdata.LOAINV_CTV_HRM=''; 
                    pdata.LOAINV_NGT_HRM=''; 
                    pdata.KHOI_ID=this.cboKhoi.value;
                    pdata.KENHBAN_ID = this.cbokenhBan.value;
                }
                if(this.isbancheo) {
                    if(this.cboKenhThu.value && this.cboKenhThu.value > 0)
                        pdata.KENHBAN_ID = this.cboKenhThu.value;
                    if(this.cboChuQuan.value && this.cboChuQuan.value>0)
                        pdata.CHUQUAN_ID = this.cboChuQuan.value;   
                }
                this.returnString = JSON.stringify(pdata);
            }
            this.closeForm();
        },

        btnGanNVQL()
        {
            if (this.nhapmoi) {
                this.$toast.error("Chỉ có thể gán khi đã có hợp đồng khách hàng");
                return;
            }
            this.popupAssignSubManagerData.vtimkiem_hdkh_id = this.SoHopDong?this.SoHopDong:this.hdkh_id;
            this.$refs.popupAssignSubManager.show();
        },
        LoadChuQuan: async function () {
            try {

                this.loading(true);
                var rs = await this.$root.context.post("/web-hopdong/laydulieu/sp_tt_chuquan",{ "p_type": 3 });
                if(rs.data) {
                    this.cboChuQuan.list = rs.data;
                    if(this.cboChuQuan.list && this.cboChuQuan.list.length>0) 
                        this.cboChuQuan.value = this.cboChuQuan.list[0].chuquan_id;
                    else {
                        this.cboChuQuan.list = [];
                        this.cboChuQuan.value = 0;
                    }
                    
                }

                else {
                    this.cboChuQuan.list = [];
                    this.cboChuQuan.value = 0;
                }
            } catch(ex) {
                this.$toast.error("Lỗi load thông tin chủ quản: "+ex);
            }
            finally { this.loading(false); }
        },
        LoadCbKenhBan: async function ()
        {
            var input = {};
            input.donvi_id = -1;
            input.loainhom_nv_id = 6;
            this.loading(true);
            try {
                var rs = await this.$root.context.post("/web-hopdong/laydulieu/sp_tt_kenhban",{ "p_type": 1 });
                if(rs.data) {
                    this.cbokenhBan.list = rs.data;
                    if(this.cbokenhBan.list && this.cbokenhBan.list.length>0) 
                        this.cbokenhBan.value = this.cbokenhBan.list[0].kenhban_id;
                    else {
                        this.cbokenhBan.list = [];
                        this.cbokenhBan.value = 0;
                    }
                    this.cboKenhThu.list = rs.data;
                    if(this.cboKenhThu.list && this.cboKenhThu.list.length>0) 
                        this.cboKenhThu.value = this.cboKenhThu.list[0].kenhban_id;
                    else {
                        this.cboKenhThu.list = [];
                        this.cboKenhThu.value = 0;
                    }
                }

                else {
                    this.cbokenhBan.list = [];
                    this.cbokenhBan.value = 0;
                    this.cboKenhThu.list = [];
                    this.cboKenhThu.value = 0;
                }
            } catch(ex) {
                this.$toast.error("Lỗi load thông tin kênh: "+ex);
            }
                finally { this.loading(false); }
        }
    }
}
</script>