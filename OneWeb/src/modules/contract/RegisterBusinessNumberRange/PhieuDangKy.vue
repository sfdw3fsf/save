<template>
<b-modal
    ref="popPhieuDangKy"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal">
  <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                <span class="icon one-notepad"></span> Phiếu đăng ký dãi số doanh nghiệp
                </div>
                 <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
                <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">PHIẾU ĐĂNG KÝ DẢI SỐ DOANH NGHIỆP</div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Hình thức hòa mạng</div>
                            <div class="value">
                              <input type='text' class='form-control highlight'  name="txtHinhThucHoaMang" v-model="txtHinhThucHoaMang" ref = "txtHinhThucHoaMang" :readonly="true">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Dải số thuê bao</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtDaiSoThueBao" v-model="txtDaiSoThueBao" ref = "txtDaiSoThueBao" :readonly="true">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Số lượng đăng ký</div>
                            <div class="value">
                              <input type='text' class='form-control highlight'  name="txtSoLuongDK" v-model="txtSoLuongDK" ref = "txtSoLuongDK" :readonly="true">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Loại dãi số</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtLoaiDaiSo" v-model="txtLoaiDaiSo" ref = "txtLoaiDaiSo" :readonly="true">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Cước cam kết</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtCuocCamket" v-model="txtCuocCamket" ref = "txtCuocCamket" :readonly="true">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Thời gian cam kết</div>
                            <div class="value">
                              <input type='text' class='form-control highlight'  name="txtThoigiancamket" v-model="txtThoigiancamket" ref = "txtThoigiancamket" :readonly="true">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Tiền hoàn trả HĐ</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtTienHoanTraHD" v-model="txtTienHoanTraHD" ref = "txtTienHoanTraHD">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Tên doanh nghiệp (*)</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtTenDoanhNghiep" v-model="txtTenDoanhNghiep" ref = "txtTenDoanhNghiep">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Địa chỉ (*)</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtDiaChiDN" v-model="txtDiaChiDN" ref = "txtDiaChiDN">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Tên viết tắt</div>
                            <div class="value">
                              <input type='text' class='form-control highlight'  name="txtTenViettat" v-model="txtTenViettat" ref = "txtTenViettat">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Người đăng ký (*)</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtNguoiDK" v-model="txtNguoiDK" ref = "txtNguoiDK">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Điện thoại (*)</div>
                            <!-- <div class="value">
                              <input type='number' class='form-control highlight' name="txtDienThoai" v-model="txtDienThoai" ref = "txtDienThoai"  @input="changeSDT" >
                            </div> -->
                            <div class="input-more-button">
                               <button class="btn" @click="clickNumberCode">
                                84
                                </button>
                                <input type="text" @keypress="isNumber($event)" v-model="txtDienThoai" class="form-control highlight"  @input="changeSDT">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Mã số thuế</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtMST" v-model="txtMST" ref = "txtMST">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Email(*)</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtEmail" v-model="txtEmail" ref = "txtEmail">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key" style="width:150px">Website</div>
                            <div class="value">
                              <input type='text' class='form-control highlight' name="txtWebsite" v-model="txtWebsite" ref = "txtWebsite">
                            </div>
                        </div>
                    </div>
                     
                </div>
            </div>
  </b-modal>
</template>

<script>
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
import API from './API'

export default {
    name:'popPhieuDangKy',
     props:{
         p_hinhthuchoamang:{
            type:String,
            default:""
        },
         daisotbGocSelected:{
            type:String,
            default:""
        },
         p_SoLuongDK:{
            type:String,
            default:""
        },
         LoaiDaiSoSelected:{
            type:String,
            default:""
        },
         CuocCamKetSelected:{
            type:String,
            default:""
        },
         TgianCamKetSelected:{
            type:String,
            default:""
        },

    },
    components:{
        ActionTop,
        KTable
    },
    data(){
        return{
            actions:[
                {
                    id:'dangky',
                    name:'Đăng ký',
                    active: true,
                    icon_class:'one-check'
                }
            ],
            data: [],
            txtHinhThucHoaMang:"",
            txtDaiSoThueBao:"",
            txtSoLuongDK:"",
            txtLoaiDaiSo:"",
            txtCuocCamket:"",
            txtThoigiancamket:"",
            txtTienHoanTraHD:"",
            txtTenDoanhNghiep:"",
            txtDiaChiDN:"",
            txtTenViettat:"",
            txtNguoiDK:"",
            txtDienThoai:"",
            txtMST:"",
            txtEmail:"",
            txtWebsite:"",           
        }
    },
    methods:{
         onActionClick(action){
            if(action.id=='dangky'){
              this.SaveThongtin()
            }
        },
        
        handleShowModal(){
            this.data= {}
                 this.txtHinhThucHoaMang=this.p_hinhthuchoamang;
                 this.txtDaiSoThueBao=this.daisotbGocSelected;
                 this.txtSoLuongDK=this.p_SoLuongDK;
                 this.txtLoaiDaiSo=this.LoaiDaiSoSelected;
                 this.txtCuocCamket=this.CuocCamKetSelected;
                 this.txtThoigiancamket=this.TgianCamKetSelected;
                 //this.txtDienThoai="84";
        },
         SaveThongtin(){
            let data = {
                dienthoai: this.txtDienThoai,
                daiso_tb: this.txtDaiSoThueBao,
                website: this.txtWebsite,
                khodaiso_id: this.txtLoaiDaiSo,
                ma_tinh: this.$root.token.getPhanVungID(),
                daiso_goc: this.txtDaiSoThueBao,
                ip: "",
                tenkh: this.txtTenDoanhNghiep,
                userid: "",
                mst: this.txtMST,
                tienck: this.txtTienHoanTraHD,
                diachi: this.txtDiaChiDN,
                tenviettat: this.txtTenViettat,
                tendangky: this.txtNguoiDK,
                tiendc: this.txtTienHoanTraHD,
                kieulay: "1",
                thangck: this.txtCuocCamket,
                soluong: this.txtSoLuongDK,
                email:this.txtEmail
                };
                this.KHOITAO_DS(data);
            },
            async KHOITAO_DS(data) {
            let res = await API.DangKyDaiSo(this.axios, data);
                if (res.data && res.data.error_code == "BSS-00000000") {
                this.$toast.success( "Đăng ký dải số doanh nghiệp thành công");
                } else {
                this.$toast.error("Có lỗi xảy ra!");
                }
            },
        showModal() {
            this.$refs["popPhieuDangKy"].show()
        },
        hideModal() {
            this.$refs["popPhieuDangKy"].hide()
        },
         changeSDT(){
               if (this.txtDienThoai.length > 9) {
            this.$toast.error( "Số thuê bao không đúng chuẩn, bạn hãy nhập lại!");
            this.$refs.txtDienThoai.focus();
                return false;
            }
         },
        clickNumberCode(){
            this.txtDienThoai='0'
        },
         isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                evt.preventDefault()
            } else {
                return true
            }

           
        },

        
    }
};
</script>

