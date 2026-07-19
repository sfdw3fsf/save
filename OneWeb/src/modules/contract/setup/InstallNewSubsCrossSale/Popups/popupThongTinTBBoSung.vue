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
                <a href="#" @click.prevent="btn_Save_Click">
                    <span class="icon one-save"></span> Ghi lại
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="closeForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </a>
            </li>
            
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form" id="layoutControlGroup2" v-if="dulieu.dat_moi">
            <div class="legend-title">
                Thông tin giấy tờ
            </div>
            <div class="info-row">
                <div class="key">Loại giấy tờ</div>
                <div class="value">
                    <SelectExt class="select-custom" v-model="cboLoaiGiayTo.value" :dataSource="cboLoaiGiayTo.list" dataTextField="loai_gt" dataValueField="loaigt_id"></SelectExt>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Số giấy tờ</div>
                <div class="value">
                    <input type="text" :value="txtSoGiayTo" class="form-control" @change="e=>txtSoGiayTo=e.target.value">
                </div>
            </div>
            <div class="info-row">
                <div class="key">Ngày hết hạn giấy tờ</div>
                <div class="value">
                    <date-picker input-class="form-control" :format="datetimeFormat" :value-type="'format'" v-model="dtpNgayHetHanGT"></date-picker>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Chức danh</div>
                <div class="value">
                    <input type="text" :value="txtChucDanh" class="form-control" @change="e=>txtChucDanh=e.target.value">
                </div>
            </div>
        </div>
        <div class="box-form" id="layoutControlGroup3" v-if="!dulieu.dat_moi">
            <div class="legend-title">
                Thông tin
            </div>
            <div class="info-row">
                <div class="key">Cam kết chất lượng</div>
                <div class="value">
                    <textarea v-model="txtCamKet_CLG" class="form-control"></textarea>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Mã VNPT I</div>
                        <div class="value">
                            <input type="text" :value="txtMaVNPTI" class="form-control" @change="e=>txtMaVNPTI=e.target.value">
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-6" id="layoutControlItem3" v-if="dulieu.kiemtra_nhanvien_vti && dulieu.kiemtra_nhanvien_vti>0">
                    <div class="info-row">
                        <div class="key">Mã kênh đối tác</div>
                        <div class="value">
                            <input type="text" :value="txtMaKenhDT" class="form-control" @change="e=>txtMaKenhDT=e.target.value">
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-6" id="layoutControlItem4" v-if="dulieu.kiemtra_nhanvien_vti && dulieu.kiemtra_nhanvien_vti>0">
                    <div class="info-row">
                        <div class="value">
                            <input type="checkbox" v-model="chkIKenhToanTrinh" /> Kênh toàn trình
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form" id="layoutControlGroup4" v-if="dulieu.is_bancheo && dulieu.kiemtra_chuquan_kh">
            <div class="legend-title">
                Thông tin phân chia doanh thu giữa các đơn vị
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Doanh thu CQKH</div>
                        <div class="value">
                            <input type="number" min="0" max="100" :value="txtDTCQKH" @change="e=>txtDTCQKH=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số tháng thanh toán</div>
                        <div class="value">
                            <input type="number" :value="txtSoThang" @change="e=>txtSoThang=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Doanh thu CQHĐ</div>
                        <div class="value">
                            <input type="number" :value="txtDTCQTB" @change="e=>txtDTCQTB=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Doanh thu phát sinh</div>
                        <div class="value">
                            <input type="number" :value="txtDTPS" @change="e=>txtDTPS=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Doanh thu CQLĐ</div>
                        <div class="value">
                            <input type="number" :value="txtDTCQTC" @change="e=>txtDTCQTC=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số HĐ ban KHDN</div>
                        <div class="value">
                            <input type="number" :value="txtSoHD" @change="e=>txtSoHD=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- <ejs-dialog :enableResize='true' :allowDragging='true' :visible="false" ref="popupAssignSubManager" :position="{ X: 'center', Y: 0 }" 
    :header='"Gán nhân viên quản lý thuê bao"' showCloseIcon=true width='80%' height="800">
    <div>
        <popupAssignSubManager :dulieu="popupAssignSubManagerData"/>
    </div>
    </ejs-dialog> -->
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
// import breadcrumb from '@/components/breadcrumb'
// import VueFlatPickr from 'vue-flatpickr-component';
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
// import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
// import popupAssignSubManager from '../../InstallNewSubs/Popups/popupAssignSubManager.vue'
import API from '../../InstallNewSubs/Popups/API'
import moment from 'moment'
// import { Checkbox } from 'element-ui'
export default {
    name: 'popupThongTinTBBoSung',
    components: { API,DatePicker },
    props: [ 'dulieu' ],
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
            
            cboLoaiGiayTo: {
                list: [],
                value: 0,
            },
            txtSoGiayTo: '',
            dtpNgayHetHanGT: moment(new Date()).format("DD/MM/YYYY"),
            txtChucDanh: '',
            txtCamKet_CLG: '',
            txtMaVNPTI: '',
            txtMaKenhDT: '',
            chkIKenhToanTrinh: false,
            txtDTCQKH: 0,
            txtSoThang: 0,
            txtDTCQTB: 0,
            txtDTPS: 0,
            txtDTCQTC: 0,
            txtSoHD: '',

            ma_nhom: '',
            ds_hdtb_sub: [],
            cap_nhat: false,
        }
    },
    computed: {
        // KenhTrong: {
        //     get() { return this.isKenh; },
        //     set(value) { this.isKenh=2; }
        // },
      
    },
    dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
    },
    async mounted() {
        this.Load_du_lieu_giay_to();
        var dt = [];
        if(this.ds_hdtb_sub && this.ds_hdtb_sub.length>0) {
            dt = this.ds_hdtb_sub;
        }
        else if (this.dulieu.hdtb_id && this.dulieu.hdtb_id>0) {
            dt = await API.get_sp_lay_tt_hdtb_sub(this.axios,{params: {hdtb_id: this.dulieu.hdtb_id}}).then(response => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data;
                }
                else return [];
            }).catch(error => {return [];});
        }
        if(dt && dt.length>0) {
            this.txtCamKet_CLG = dt[0]["camket_clg"];
            if (dt[0]["ma_vnpti"])
                this.txtMaVNPTI = dt[0]["ma_vnpti"];
            if (dt[0]["ma_vnpti"])
                this.chkIKenhToanTrinh = dt[0]["kenh_toantrinh"] && dt[0]["kenh_toantrinh"] == "1" ? true : false;
            if (dt[0]["ma_kenhdt"])
                this.txtMaKenhDT = dt[0]["ma_kenhdt"];
            if (dt[0]["doanhthu_cqkh"])
                this.txtDTCQKH = dt[0]["doanhthu_cqkh"];
            else this.txtDTCQKH = 0;
            if (dt[0]["doanhthu_cqtv"])
                this.txtDTCQTB = dt[0]["doanhthu_cqtv"];
            else this.txtDTCQTB = 0;
            if (dt[0]["doanhthu_cqtc"])
                this.txtDTCQTC = dt[0]["doanhthu_cqtc"];
            else this.txtDTCQTC = 0;
            if (dt[0]["doanhthu_ps"])
                this.txtDTPS = dt[0]["doanhthu_ps"];
            else this.txtDTPS = 0;
            if (dt[0]["sothang_tt"])
                this.txtSoThang = dt[0]["sothang_tt"];
            else this.txtSoThang = 0;
            this.txtSoHD = dt[0]["so_vb_khdn"];
        }
    },
   
    methods: {
        async Load_du_lieu_giay_to() {
            await API.get_sp_lay_ds_loaigt(this.axios,{}).then(response => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.cboLoaiGiayTo.list = response.data.data;
                    this.cboLoaiGiayTo.value = this.cboLoaiGiayTo.list.length > 0? this.cboLoaiGiayTo.list[0].loaigt_id : 1;
                }
            });
            if(this.dulieu.hdtb_id && this.dulieu.hdtb_id>0)
                API.get_sp_lay_tt_hdtb_sub(this.axios,{params: {hdtb_id: this.dulieu.hdtb_id}}).then(response => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        var dt_hdtb_sub = response.data.data;
                        if(dt_hdtb_sub && dt_hdtb_sub.length>0) {
                            this.cboLoaiGiayTo.value = dt_hdtb_sub[0]["loaigt_id"];
                            this.txtSoGiayTo = dt_hdtb_sub[0]["so_gt"];
                            this.txtChucDanh = dt_hdtb_sub[0]["chucdanh"];
                            this.dtpNgayHetHanGT = moment((dt_hdtb_sub[0]["ngayhethan_gt"]+'').replace('T',''),this.datetimeFormatFull).isValid()?moment((dt_hdtb_sub[0]["ngayhethan_gt"]+'').replace('T',''),this.datetimeFormatFull).format("DD/MM/YYYY"):dt_hdtb_sub[0]["ngayhethan_gt"];
                            this.txtCamKet_CLG = dt_hdtb_sub[0]["camket_clg"];
                            this.ma_nhom = dt_hdtb_sub[0]["ma_nhom"];
                        }
                        else {
                            if(!this.cboLoaiGiayTo.value) this.cboLoaiGiayTo.value = 1;
                            this.txtSoGiayTo = "";
                            this.txtChucDanh = "";
                            this.dtpNgayHetHanGT = moment(new Date()).format("DD/MM/YYYY");
                            this.txtCamKet_CLG = "";
                            this.ma_nhom = "";
                        }
                    }
                });
        },
        async btn_Save_Click()
        {
            if (this.dulieu.dat_moi && (!this.cboLoaiGiayTo.value || this.cboLoaiGiayTo.value==0))
            {
                this.$toast.error("Bạn chưa chọn loại giấy tờ");
                return;
            }
            if (this.dulieu.dat_moi && (!this.txtSoGiayTo || (this.txtSoGiayTo+'').trim()==""))
            {
                this.$toast.error("Bạn chưa nhập số giấy tờ");
                return;
            }

            if (this.dulieu.dat_moi && !this.dtpNgayHetHanGT)
            {
                this.$toast.error("Bạn chưa nhập ngày hết hạn giấy tờ");
                return;
            }

            if (this.dulieu.dat_moi && (!this.txtChucDanh || (this.txtChucDanh+'').trim()==""))
            {
                this.$toast.error("Bạn chưa nhập chức danh chủ thể");
                return;
            }

            if (this.dulieu.is_bancheo && this.dulieu.kiemtra_chuquan_kh)
            {
                if (!this.txtDTCQKH || this.txtDTCQKH < 0)
                {
                    this.$toast.error("Doanh thu chủ quản của khách hàng phải lớn hơn hoặc bằng 0");
                    return;
                }
                if (!this.txtDTCQTB || this.txtDTCQTB < 0)
                {
                    this.$toast.error("Doanh thu chủ quản hợp đồng phải lớn hơn hoặc bằng 0");
                    return;
                }
                if (!this.txtDTCQTC || this.txtDTCQTC < 0)
                {
                    this.$toast.error("Doanh thu chủ quản lắp đặt phải lớn hơn hoặc bằng 0");
                    return;
                }
                if (this.txtDTCQKH*1 + this.txtDTCQTB*1 + this.txtDTCQTC*1 != 100)
                {
                    this.$toast.error("Tổng 3 loại doanh thu phải bằng 100%");
                    return;
                }
                if (!this.txtDTPS || this.txtDTPS <= 0)
                {
                    this.$toast.error("Doanh thu phát sinh phải lớn hơn 0");
                    return;
                }
                if (!this.txtSoThang || this.txtSoThang <= 0)
                {
                    this.$toast.error("Số tháng phải lớn hơn 0");
                    return;
                }
                if (!this.txtSoHD || (txtSoHD+'').trim() == '')
                {
                    this.$toast.error("Phải nhập số HĐ");
                    return;
                }
            }
            this.cap_nhat = false;
            try
            {
                this.ds_hdtb_sub = await this.TaoDuLieu();
                this.cap_nhat = true;
                if (this.ds_hdtb_sub[0]["hdtb_id"] && this.ds_hdtb_sub[0]["hdtb_id"] != "0")
                {
                    var kq_cn = await API.post_capnhat_hdtb_sub_ca(this.axios,{vhdtb_id: this.dulieu.hdtb_id, vdata: JSON.stringify(this.ds_hdtb_sub)}).then(response => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            return response.data.data;
                        }
                        else return response.data.message + response.data.message_detail;
                    }).catch(error => {
                        return error.data.message + error.data.message_detail;
                    });
                    if(kq_cn && kq_cn=="ok") this.$toast.success("Cập nhật thành công");
                    else this.$toast.error("Chưa cập nhật thành công: "+kq_cn);
                }
                this.closeForm();

            }
            catch { this.cap_nhat = false; }
        },
        async TaoDuLieu()
        {
            var ds = [];
            var row = {};
            if (!this.dulieu.nhapmoi)
                row.HDTB_ID = this.dulieu.hdtb_id;
            else
                row.HDTB_ID = 0;
            row.CAMKET_CLG = this.txtCamKet_CLG.Text;
            if (this.dulieu.dat_moi)
            {
                // Get_MST_KHTN(hdtb_id);
                row.LOAIGT_ID = this.cboLoaiGiayTo.value;
                row.SO_GT = this.txtSoGiayTo;
                row.CHUCDANH = this.txtChucDanh;
                row.NGAYHETHAN_GT = this.dtpNgayHetHanGT
                row.MST_KHTN = await API.get_mst_khtn(this.axios,{params:{"hdtb_id":this.dulieu.hdtb_id}}).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        return (response.data.data);
                    } else return null;
                }).catch(()=>{ return null; });//_vmst_khtn;
            }
            if (this.dulieu.is_bancheo && this.dulieu.kiemtra_chuquan_kh)
            {
                row.DOANHTHU_CQKH = this.txtDTCQKH;
                row.DOANHTHU_CQTB = this.txtDTCQTB;
                row.DOANHTHU_CQTC = this.txtDTCQTC;
                row.DOANHTHU_PS = this.txtDTPS;
                row.SOTHANG_TT = this.txtSoThang;
                row.SO_VB_KHDN = this.txtSoHD;
            }
            ds.push(row);
            return ds;
        },
        closeForm() {
            this.$emit('form-close',{cap_nhat:this.cap_nhat,ds_hdtb_sub:this.ds_hdtb_sub,vkenh_toantrinh:this.chkIKenhToanTrinh});
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
    }
}
</script>