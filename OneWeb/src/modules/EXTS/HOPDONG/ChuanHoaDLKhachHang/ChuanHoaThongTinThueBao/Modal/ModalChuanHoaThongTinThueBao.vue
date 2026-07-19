<template src="./ModalChuanHoaThongTinThueBao.html"></template>
<script>

import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
import DatePicker from 'vue2-datepicker'
import Enum from "../../../ThayDoiThongTinHopDong/Enum"
import ModalChonDiaChi from '../../popupChonDiaChi'
import popupChonKhuVuc from '@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao'
import moment from 'moment'


export default {
    name: "ModalChuanHoaThongTinThueBao",
    props: ["modalId", "data"],
    components: {
        DatePicker, 
        ModalChonDiaChi, 
        popupChonKhuVuc
    },
    data: function () {
        return {
            nguoidung_id:null,
            chapnhan:null,
            //Địa chỉ thuê bao
            khutb_id:null,
            aptb_id:null,
            photb_id:null,
            dacdiemtb_id:null,
            phuongtb_id:null,
            quantb_id:null,
            tinhtb_id:null,
            hinhthuctt_id:null,
            sonhatb:null,
            diachi_tb:null,
            //Địa chỉ lắp đặt
            diachi_ld:null,
            khuld_id:null,
            apld_id:null,
            phold_id:null,
            dacdiemld_id:null,
            phuongld_id:null,
            quanld_id:null,
            tinhld_id:null,
            sonha_ld:null,
            thuebao_id:null,
            ma_tb:null,
            ten_tb:null,
            ghichu:null,
            doituong_id:null,
            email:null,
            loaitb_id:null,
            donvi_id:null,
            ngay_sn:null,
            ngayyeucau:null,
            hdtb_id:null,
            dsDVKID:null,
            kt_hdkh_id:false,//Biến kiểm tra xem đã đã lập hợp đồng ký lại chưa
            vetinh_id:null,
            mucuoctb_id:null,
            khuvuc_id:null,
            _khuvuckyHD:0,
            DONVIQL_HTTT:0,

            waterMark : 'Chọn ngày',
            dateVal : new Date(),
            dateFormat : 'dd/MM/yyyy',
            datetimeFormat: "dd/MM/yyyy HH:mm",

            diachi_tb_moi:null,
            //Địa chỉ lắp đặt
            diachi_ld_moi:null,
            khuld_id_moi:null,
            apld_id_moi:null,
            phold_id_moi:null,
            dacdiemld_id_moi:null,
            phuongld_id_moi:null,
            quanld_id_moi:null,
            tinhld_id_moi:null,
            sonha_ld_moi:null,
            ten_tb_moi:null,
            ghichu_moi:null,
            email_moi:null,
            ngay_sn_moi:null,
            ngayyeucau_moi:null,
            khuvuc_moi:null,
            tocdo_id:null,
            tocdo_id_moi:null,
            muccuoc_id:null,
            muccuoc_id_moi:null,
            dichvukhac:null,
            dichvukhac_moi:null,
            dichvuvt_id:null,

            dsDonVi:null,
            dsDoiTuong:null,
            dsMucCuocTB:null,
            dsLoaiHinhTB:null,
            dsTocDo:null,
            dsMucCuoc:null,
            dataSend:{},
            dataXacNhan:{}
        }
    },
    validations: {
        ten_tb_moi: {
            required,
            maxLength: maxLength(229)
        },
        ngay_sn_moi: {required},
        diachi_tb_moi: {required},
        diachi_ld_moi: {required},
        ghichu_moi: {
            maxLength: maxLength(500)
        }
    },

    created: async function () {
    },
    watch:{
    },
    computed: {
    },
    methods: {
        showModal: async function () {
            try {
                this.dataXacNhan = this.data
                this.khutb_id = this.data.khutb_id
                this.aptb_id = this.data.aptb_id
                this.photb_id = this.data.photb_id
                this.dacdiemtb_id = this.data.dacdiemtb_id
                this.phuongtb_id = this.data.phuongtb_id
                this.quantb_id = this.data.quantb_id
                this.tinhtb_id = this.data.tinhtb_id
                this.sonhatb = this.data.sonha_tb

                this.khuld_id = this.data.khuld_id
                this.apld_id = this.data.apld_id
                this.phold_id = this.data.phold_id
                this.dacdiemld_id = this.data.dacdiemld_id
                this.phuongld_id = this.data.phuongld_id
                this.quanld_id = this.data.quanld_id
                this.tinhld_id = this.data.tinhld_id
                this.sonhald = this.data.sonha_ld

                this.khuvuc_moi = this.data.khuvuc
                this.khuvuc = this.data.khuvuc
                this.khuvuc_id = this.data.khuvuc_id


                this.ma_tb = this.data.ma_tb
                this.dsDonVi = [{
                    donvi_id: this.data.donvi_id,
                    ten_dv: this.data.ten_dv
                }]
                this.donvi_id = this.data.donvi_id
                this.dsDoiTuong = [{
                    doituong_id: this.data.doituong_id,
                    ten_dt: this.data.ten_dt
                }]
                this.doituong_id = this.data.doituong_id
                this.dsLoaiHinhTB = [{
                    loaitb_id: this.data.loaitb_id,
                    loaihinh_tb: this.data.loaihinh_tb
                }]
                this.loaitb_id = this.data.loaitb_id
                this.ten_tb = this.data.ten_tb
                this.ten_tb_moi = this.data.ten_tb
                this.diachi_tb = this.data.diachi_tb
                this.diachi_tb_moi = this.data.diachi_tb
                this.diachi_ld = this.data.diachi_ld
                this.diachi_ld_moi = this.data.diachi_ld
                this.ngay_sn = this.data.ngay_sn
                this.ngay_sn_moi = this.data.ngay_sn
                this.ghichu = this.data.ghichu
                this.ghichu_moi = this.data.ghichu
                this.dsMucCuocTB = [{
                    mucuoctb_id: this.data.mucuoctb_id,
                    tenmuccuoc: this.data.tenmuccuoc,
                }]
                this.mucuoctb_id = this.data.mucuoctb_id
                this.dsTocDo = [{
                    tocdo_id: this.data.tocdo_id,
                    thuonghieu: this.data.thuonghieu
                }]

                this.dichvuvt_id = this.data.dichvuvt_id
                if (this.dichvuvt_id == Enum.DichVuVienThong.ADSL) {
                    this.tocdo_id = this.data.tocdo_id
                    this.tocdo_id_moi = this.data.tocdo_id
                    await this.getMucCuocAdsl()
                    if (this.dsMucCuoc.length > 0) {
                        this.muccuoc_id = this.dsMucCuoc[0].muccuoc_id
                        this.muccuoc_id_moi = this.dsMucCuoc[0].muccuoc_id
                    }
                }
                this.dichvukhac = this.data.ten_dvkhac
                this.dichvukhac_moi = this.data.ten_dvkhac
                await this.getDsThamSoMacDinh()
            } catch (ex) {
                console.log(ex)
                this.$toast.error("lỗi load dữ liệu:" + ex)
            }
        },
        getDsThamSoMacDinh: async function() {
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_thamso_macdinh')
            if (res.data.length > 0 && res.data.find(x => x.ma_ts == "DONVIQL_HTTT") != null && res.data.find(x => x.ma_ts == "DONVIQL_HTTT").ten_ts == 1) {
                this.DONVIQL_HTTT = 1
            }

            if (res.data.length > 0 && res.data.find(x => x.ma_ts == "KHUVUC_KYHD") != null && res.data.find(x => x.ma_ts == "KHUVUC_KYHD").ten_ts == 1) {
                this._khuvuckyHD = 1
            }
        },

        getMucCuocAdsl: async function() {
            let data = {
                table: 'MUCCUOC',
                bangChaId: null
            }
            let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
            this.dsMucCuoc = res.data
        },
        getDiaChiTB: async function(obj){
            this.tinhtb_id = obj.tinh_id
            this.quantb_id = obj.quan_id
            this.phuongtb_id = obj.phuong_id
            this.photb_id = obj.pho_id
            this.dacdiemtb_id = obj.dac_diem_id
            this.aptb_id = obj.ap_id
            this.khutb_id = obj.khu_id
            this.sonhatb = obj.so_nha
            this.diachi_tb_moi = obj.diachimoi
        },
        getDiaChiLD: async function(obj){
            let dataInput = {
                phuongId: obj.phuong_id,
                phoId: obj.pho_id,
                apId: obj.ap_id,
                khuId: obj.khu_id,
                kieu: 2,
                htttId: 0,
                donviql_httt: this.DONVIQL_HTTT
            }
            let res = await this.$root.context.get('/web-ccdv/ChuanHoaDuLieu/lay_ds_donvi_quanly', dataInput)
            if (res.data.length <= 0) {
                this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý')
                return
            } else if (res.data.length == 1) {
                this.donvi_id_moi = res.data[0].donvi_id
            } else {
                this.dataSend = {}
                this.dataSend.ds = res.data // de tam doi erp xong form doi lai ten bien sau
                this.$bvModal.show('popupDonViQL')
            }

            if (this.donvi_id_moi != this.donvi_id)
            {
                this.$toast.error("Thuê bao '" + this.ma_tb + "' được lắp tại " + this.dsDonVi[0].ten_dv + "\n Bạn không được thay đổi đơn vị lắp đặt của thuê bao này!")
                return
            }

            this.tinhld_id = obj.tinh_id
            this.quanld_id = obj.quan_id
            this.phuongld_id = obj.phuong_id
            this.dacdiemld_id = obj.dac_diem_id
            this.phold_id = obj.pho_id
            this.apld_id = obj.ap_id
            this.khuld_id = obj.khu_id
            this.sonha_ld = obj.so_nha
            this.diachi_ld_moi = obj.diachimoi
        },
        diaChiLDClick: async function(){
            this.dataSend = {}
            this.dataSend.tinh_id = this.tinhld_id
            this.dataSend.quan_id = this.quanld_id
            this.dataSend.phuong_id = this.phuongld_id
            this.dataSend.pho_id = this.phold_id
            this.dataSend.ap_id = this.apld_id
            this.dataSend.khu_id = this.khuld_id
            this.dataSend.dacdiem_id = this.dacdiemld_id
            this.dataSend.so_nha = this.sonhald
            this.dataSend.diachi = this.diachi_ld_moi
            this.dataSend.tieuDe = 'Địa chỉ lắp đặt'
            this.$bvModal.show('popupDiaChiLD')
        },
        diaChiTBClick: async function(){
            this.dataSend = {}
            this.dataSend.tinh_id = this.tinhtb_id
            this.dataSend.quan_id = this.quantb_id
            this.dataSend.phuong_id = this.phuongtb_id
            this.dataSend.pho_id = this.photb_id
            this.dataSend.ap_id = this.aptb_id
            this.dataSend.khu_id = this.khutb_id
            this.dataSend.dacdiem_id = this.dacdiemtb_id
            this.dataSend.so_nha = this.sonhatb
            this.dataSend.diachi = this.diachi_tb_moi
            this.dataSend.tieuDe = 'Địa chỉ thuê bao'
            this.$bvModal.show('popupDiaChiTB')
        },
        xacNhanDonViQL: async function(data){
            this.donvi_id_moi = this.donvi_id_moi//data.donvi_id_moi//de tam
            this.$bvModal.hide('popupDonViQL')
        },
        xacNhanKhuVuc: async function(val) {
            if (val != null) {
                if (this.khuvuc_id != 0) {
                    this.khuvuc_moi= val.ma_kv
                    this.khuvuc_id = val.khuvuc_id
                }
            }
            this.$bvModal.hide('popupChonKhuVuc')
        },
        khuVucClick: function () {
            let diaChi = {
                QUAN_ID: this.quanld_id,
                PHUONG_ID: this.phuongld_id,
                PHO_ID: this.phold_id,
                AP_ID: this.apld_id,
                KHU_ID: this.khuld_id,
                DACDIEM_ID: this.dacdiemld_id
            }
            this.dataSend = {
                diachi: diaChi, 
                khuvuc_id: this.khuvuc_id, 
                hdtb_id: 0,
                thuebao_id: this.thuebao_id, 
                donvi_id: this.donvi_id 
            }
            this.$bvModal.show('popupChonKhuVuc');
        },
        kiemTraDuLieu: async function() {
            this.$v.$touch()

            let errorMessage = []
            if (this.ten_tb_moi == null || this.ten_tb_moi == '')
                errorMessage.push("Bạn chưa nhập tên thuê bao mới!")
            else if (!this.$v.ten_tb_moi.maxLength)
                errorMessage.push("Trường tên thuê bao không được lớn hơn 50 ký tự!")
            if (this.ngay_sn_moi == null || this.ngay_sn_moi == '')
                errorMessage.push("Bạn chưa nhập ngày sinh của thuê bao!")
            if (this.diachi_ld_moi == null || this.diachi_ld_moi == '' || this.quanld_id == 0 || this.phuongld_id == 0)
                errorMessage.push("Bạn chưa chuẩn hóa địa chỉ lắp đặt thuê bao!")
            if (this.diachi_ld_moi == null || this.diachi_ld_moi == '' || this.quantb_id == 0 || this.phuongtb_id == 0)
                errorMessage.push("Bạn chưa chuẩn hóa địa chỉ thuê bao!")
            if (this._khuvuckyHD == 1 && (this.khuvuc_moi == "" || this.khuvuc_moi == null))
                errorMessage.push("Bạn chưa chuẩn hóa khu vực!")
            if (!this.$v.ghichu_moi.maxLength)
                errorMessage.push("Trường ghi chú không được lớn hơn 500 ký tự!")

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return false
            } else return true
        },

        chapNhanClick:async function() {
            if(!await this.kiemTraDuLieu()) return
            this.dataXacNhan.ten_tb = this.ten_tb_moi != null ? this.ten_tb_moi.trim() : this.ten_tb_moi
            this.dataXacNhan.ngay_sn = this.ngay_sn_moi
            this.dataXacNhan.ngay_sn1 = this.ngay_sn_moi && moment(this.ngay_sn_moi).format("DD/MM/YYYY")
            this.dataXacNhan.ten_kv = this.khuvuc_moi
            this.dataXacNhan.khuvuc_id = this.khuvuc_id
            this.dataXacNhan.ghichu = this.ghichu_moi != null ? this.ghichu_moi.trim() : this.ghichu_moi

            let dataDiaChi = {
                khu_id: this.khutb_id,
                ap_id: this.aptb_id,
                pho_id: this.photb_id,
                dacdiem_id: this.dacdiemtb_id,
                phuong_id: this.phuongtb_id,
                quan_id: this.quantb_id,
                tinh_id: this.tinhtb_id,
                sonha: this.sonhatb,
                tinhld_id: this.tinhld_id,
                quanld_id: this.quanld_id,
                phuongld_id: this.phuongld_id,
                dacdiemld_id: this.dacdiemld_id,
                phold_id: this.phold_id,
                apld_id: this.apld_id,
                khuld_id: this.khuld_id,
                sonha_ld: this.sonha_ld
            }

            //Địa chỉ thuê bao
            this.dataXacNhan.khutb_id = this.khutb_id
            this.dataXacNhan.aptb_id = this.aptb_id
            this.dataXacNhan.photb_id = this.photb_id
            this.dataXacNhan.dacdiemtb_id = this.dacdiemtb_id
            this.dataXacNhan.phuongtb_id = this.phuongtb_id
            this.dataXacNhan.quantb_id = this.quantb_id
            this.dataXacNhan.tinhtb_id = this.tinhtb_id
            this.dataXacNhan.sonha_tb = this.sonhatb
            //Địa chỉ lắp đặt
            this.dataXacNhan.tinhld_id = this.tinhld_id
            this.dataXacNhan.quanld_id = this.quanld_id
            this.dataXacNhan.phuongld_id = this.phuongld_id
            this.dataXacNhan.dacdiemld_id = this.dacdiemld_id
            this.dataXacNhan.phold_id = this.phold_id
            this.dataXacNhan.apld_id = this.apld_id
            this.dataXacNhan.khuld_id = this.khuld_id
            this.dataXacNhan.sonha_ld = this.sonha_ld

            this.dataXacNhan.dl_diachi = JSON.stringify(dataDiaChi)
            this.dataXacNhan.diachi_tb = this.diachi_tb_moi
            this.dataXacNhan.diachi_ld = this.diachi_ld_moi

            this.$emit('xacnhan',this.dataXacNhan)
            this.$bvModal.hide('popupChuanHoaThueBao')
        }
    },
    mounted:function(){
    }
};
</script>
<style>
  div#popupQLBe___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }
  .dashboard-header {
    z-index: 9 !important;
  }
  a.disabled {
    pointer-events: none;
    cursor: default;
  }
</style>
