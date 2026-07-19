<template>

<div class="modal-content popup-box">
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
                    <div class="box-form">
                        
                        <div class="info-row">
                            <div class="key">Số phòng</div>
                            <div class="value">
                                <input type="text" v-model="SoPhong" ref="SoPhong" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Mã ngân sách</div>
                            <div class="value">
                                <input type="text" v-model="MaNganSach" ref="MaNganSach" class="form-control" maxlength="7" @keypress="validateNumber">
                            </div>
                        </div>
                       
                    </div>
                    <div class="box-form">
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
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Loại kênh</div>
                                    <div class="value">
                                        <div class="select-custom">
                                        <select2 :options="LoaiKenh.list" v-model="LoaiKenh.value.id" > 
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
                                        <div class="select-custom">
                                           <select2 :options="KenhID.list" v-model="KenhID.value.id" > 
                                            <option disabled value="0">Select one</option> 
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-down">

                            </span>
                            Thông tin người giới thiệu
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key">Người giới thiệu</div>
                                    <div class="value">
                                        <input type="text" v-model="NguoiGioiThieu" ref="NguoiGioiThieu" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key">Chức vụ</div>
                                    <div class="value">
                                        <input type="text" v-model="ChucVu" ref="ChucVu" class="form-control">
                                    </div>
                                </div>
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
                                            <flat-pickr class="form-control" :config="dateconfig" v-model="NgayCap" />
                                            <span class="icon one-calendar"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>

                    <div class="box-form">
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
                                            <flat-pickr class="form-control" :config="dateconfig" v-model="NgayKyHD" />
                                            <span class="icon one-calendar"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key">Giấy ủy quyền</div>
                                    <div class="value">
                                        <input type="text" v-model="GiayUyQuyen" ref="GiayUyQuyen" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Loại HĐ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                        <select2 :options="LoaiHD.list" v-model="LoaiHD.value.id" > 
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
                                        <select2 :options="TinhTrangHD.list" v-model="TinhTrangHD.value.id" > 
                                            <option disabled value="0">Select one</option> 
                                        </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                 <ejs-dialog :enableResize='true' :allowDragging='true' :visible="false" ref="popupAssignSubManager" 
                :header='"Gán nhân viên quản lý thuê bao"' showCloseIcon=true width='80%' :target='toptarget1' height="800">
                <div>
                    <popupAssignSubManager :dulieu="popupAssignSubManagerData"/>
                </div>
                </ejs-dialog>

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
            DOANH_NGHIEP: 1,
            SoPhong: "",
            MaNganSach: "",
            SoHopDong: "",
            SoHopDongGiay:"",
            NguoiGioiThieu:"",
            ChucVu:"",
            SoGiayTo:"",
            SoHD: "",
            SoPhuLuc: "",
            GiaTriHD: "0",
            NgayKyHD: new Date(),
            NgayCap: new Date(),
            GiayUyQuyen: "",
            LoaiHD: {
                list: [],
                value: {},
            },
            TinhTrangHD: {
                list: [],
                value: {},
            },
            LoaiKenh: {
                list: [{id:1,text:"CTV"},{id:2,text:"Đại lý"}],
                value: {},
            },
            KenhID: {
                list: [],
                value: {},
            },
            isKenh: 1,
            //Dữ liệu truyền vào
            nhapmoi: false,
            hdkh_id: "0",
            vma_kh: "",
            vkieukh_id: 0,
            ts_kt_tthdkhdn: 0,
            hopdongkh: [],
            returnString: "", //dữ liệu trả ra
            //
            popupAssignSubManagerData: [],
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
        this.hdkh_id = "5125881";//this.dulieu._hdkh_id?this.dulieu._hdkh_id:0; 
        this.nhapmoi = false; //this.dulieu._nhapmoi?this.dulieu._nhapmoi:false; 
        this.vma_kh = "thangdm1"; //this.dulieu._vma_kh?this.dulieu._vma_kh:0;  
        this.vkieukh_id = 0; //this.dulieu._vkieukh_id?this.dulieu._vkieukh_id:0; 
        this.ts_kt_tthdkhdn = 1;//this.dulieu._ts_kt_tthdkhdn?this.dulieu._ts_kt_tthdkhdn:0;  
        this.LoadCombobox();
        this.LoadForm();
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
            await API.sp_ht_ds_nhanvien_theo_nhomlnv(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    console.log(response.data.data);
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
            if (this.nhapmoi)
            {
                var input = {};
                input.hdkh_id = this.hdkh_id;
                this.loading(true);
                await API.lay_hdkh_sub_theo_hdkh_id(this.axios, input).then((response) => {
                    this.loading(false);    
                    if(response.data.error_code && response.data.error_code === this.success_code) {
                        this.hopdongkh = response.data.data;
                        if (this.hopdongkh.length>0)
                        {
                            this.SoPhong = this.hopdongkh[0].SO_PHONG;
                            this.MaNganSach= this.hopdongkh[0].MA_NS;
                            this.SoHopDong= this.hopdongkh[0].SO_HD_KHDN;
                            this.SoHopDongGiay= "";
                            this.NguoiGioiThieu= this.hopdongkh[0].NGUOI_GT;
                            this.ChucVu= this.hopdongkh[0].CHUCVU_GT;
                            this.SoGiayTo= this.hopdongkh[0].SOGIAY_GT;
                            this.NgayCap=this.hopdongkh[0].NGAYCAP_GT;
                            this.LoaiKenh.value.id =1;
                            this.KenhID.value.id = this.hopdongkh[0].KENH_ID;
                            //Load thông tin khách hàng doanh nghiệp
                            if (this.ts_kt_tthdkhdn == this.DOANH_NGHIEP)
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
                                    this.LoaiHD.value.id = this.hopdongkh[0].LOAIHDKH_ID;
                                }
                                if (this.hopdongkh[0].TINHTRANGHD_ID !=null)
                                {
                                    this.TinhTrangHD.value.id = this.hopdongkh[0].TINHTRANGHD_ID;
                                }

                            }
                        }
                        else
                        {
                            if (this.vkieukh_id == 1)
                            {
                                var input = {};
                                input.vkieu = 1;
                                this.loading(true);
                                API.sinh_so_hd_khdn(this.axios, input).then((response) => {
                                    this.loading(false);    
                                    if(response.data.error_code && response.data.error_code === this.success_code) {
                                        this.SoHopDong = response.data.data;
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
                        this.ShowAlert("Lỗi lấy thông tin hợp đồng khách hàng!",2);
                    }
                }
                ).finally(()=>{  this.loading(false); });
                
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
                        if (this.hopdongkh.length>0)
                        {
                            this.SoPhong = this.hopdongkh[0].SO_PHONG;
                            this.MaNganSach= this.hopdongkh[0].MA_NS;
                            this.SoHopDong= this.hopdongkh[0].SO_HD_KHDN;
                            this.SoHopDongGiay= "";
                            this.NguoiGioiThieu= this.hopdongkh[0].NGUOI_GT;
                            this.ChucVu= this.hopdongkh[0].CHUCVU_GT;
                            this.SoGiayTo= this.hopdongkh[0].SOGIAY_GT;
                            this.NgayCap=this.hopdongkh[0].NGAYCAP_GT;
                            this.LoaiKenh.value.id =1;
                            this.KenhID.value.id = this.hopdongkh[0].KENH_ID;
                            //Load thông tin khách hàng doanh nghiệp
                            if (this.ts_kt_tthdkhdn == this.DOANH_NGHIEP)
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
                                    this.LoaiHD.value.id = this.hopdongkh[0].LOAIHDKH_ID;
                                }
                                if (this.hopdongkh[0].TINHTRANGHD_ID !=null)
                                {
                                    this.TinhTrangHD.value.id = this.hopdongkh[0].TINHTRANGHD_ID;
                                }

                            }
                        }
                    }
                    else
                    {
                        this.ShowAlert("Lỗi lấy thông tin hợp đồng khách hàng!",2);
                    }
                }
                ).finally(()=>{  this.loading(false); });
            }
        },

        btnSave: async function ()
        {

            if (this.SoHopDong =="")
            {
                this.ShowAlert("Bạn chưa nhập thông tin Số hợp đồng",1);
                this.$refs.SoHopDong.focus();
                return;
            }
            if (this.vma_kh =="")
            {
                this.ShowAlert("Chưa có thông tin Mã khách hàng",1);
                return;
            }

            if (!this.nhapmoi)
            {
                if (this.vkieukh_id == this.DOANH_NGHIEP)
                {
                    var temp = this.MaNganSach.substring(0,1);
                    if (this.MaNganSach.length < 7 || temp =="0" || temp =="4" || temp =="5" || temp =="6")
                    {
                        this.ShowAlert("Mã ngân sách phải đủ 7 ký tự số bắt đầu bằng 1,2,3,7,8,9",1);
                        this.$refs.MaNganSach.focus();
                        return;
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
                    NGAYCAP_GT: moment(this.NgayCap).format("DD/MM/YYYY"), 
                    MA_NS:this.MaNganSach, 
                    SO_HD_KHDN:this.SoHopDong, 
                    KENH_PT:this.isKenh, 
                    LOAIKENH:this.LoaiKenh.value.id, 
                    KENH_ID:this.KenhID.value.id, 
                }; 
                if (this.ts_kt_tthdkhdn == this.DOANH_NGHIEP) 
                { 
                    pdata.MA_KHDN = this.vma_kh; 
                    pdata.GIATRI_HD=this.GiaTriHD; 
                    pdata.NGAY_KY_HD=moment(this.NgayKyHD).format("DD/MM/YYYY"); 
                    pdata.GIAY_UQ=this.GiayUyQuyen; 
                    pdata.LOAIHDKH_ID=this.LoaiHD.value.id; 
                    pdata.TINHTRANGHD_ID=this.TinhTrangHD.value.id; 
                    pdata.SO_PL_KHDN=this.SoPhuLuc; 
                    pdata.LOAINV_CTV_HRM=''; 
                    pdata.LOAINV_NGT_HRM=''; 
                    pdata.KHOI_ID=''; 
                } 
                input.js_hdkh_sub = JSON.stringify(pdata);
                console.log(input);
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
                    NGAYCAP_GT: moment(this.NgayCap).format("DD/MM/YYYY"), 
                    MA_NS:this.MaNganSach, 
                    SO_HD_KHDN:this.SoHopDong, 
                    KENH_PT:this.isKenh, 
                    LOAIKENH:this.LoaiKenh.value.id, 
                    KENH_ID:this.KenhID.value.id, 
                }; 
                if (this.ts_kt_tthdkhdn == this.DOANH_NGHIEP) 
                { 
                    pdata.MA_KHDN = this.vma_kh; 
                    pdata.GIATRI_HD=this.GiaTriHD; 
                    pdata.NGAY_KY_HD=moment(this.NgayKyHD).format("DD/MM/YYYY"); 
                    pdata.GIAY_UQ=this.GiayUyQuyen; 
                    pdata.LOAIHDKH_ID=this.LoaiHD.value.id; 
                    pdata.TINHTRANGHD_ID=this.TinhTrangHD.value.id; 
                    pdata.SO_PL_KHDN=this.SoPhuLuc; 
                    pdata.LOAINV_CTV_HRM=''; 
                    pdata.LOAINV_NGT_HRM=''; 
                    pdata.KHOI_ID=''; 
                } 
                this.returnString = JSON.stringify(pdata);
            }
        },

        btnGanNVQL()
        {
            this.popupAssignSubManagerData.vtimkiem_hdkh_id = this.SoHopDong;
            this.$refs.popupAssignSubManager.show();
        }
       
      
    }
}
</script>