<template src="./CompleteEquipmentSale.html"></template>
<style scoped src="./CompleteEquipmentSale.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import GiaoPhieuNhanVien from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien'
import TraPhieu from '@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue'
import ChuyenTo from "@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";
import { LoaiHopDong, TrangThaiHD, DichVuVienThong } from '@/utils/Enum'
import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue"
import { LoaiHinhTB } from '@/const/enums'

export default {
    name: 'CompleteEquipmentSale',
    components: {
        breadcrumb,
        ModalChonDiaChi,
        KDatePicker,
        GiaoPhieuNhanVien,
        TraPhieu,
        CapVatTuTB,
        ChuyenTo
    },
    data() {
        return {
            dateFormat: "DD/MM/YYYY HH:mm:ss",
            animationSettings: { effect: 'Zoom' },
            header: {
                title: "Hoàn công bán thiết bị",
                list: [
                    //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
                    { name: 'Quản lý vật tư', link: { name: 'Ui.cards' } },
                    { name: 'Hoàn công bán thiết bị', link: { name: 'Ui.cards' } },
                ]
            },
            dsVatTu: {
                list: [
                ],
                header: [ 
                    {fieldName: 'loai_tbi', headerText: 'Tên thiết bị', allowFiltering: true}, 
                    {fieldName: 'soluong', headerText: 'Số lượng', allowFiltering: true}, 
                ],
                value: {}
            },
            dsGiaoViec: {
                list: [
                ],
                header: [ 
                    {fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true}, 
                    {fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true}, 
                    {fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true}, 
                    {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true}, 
                ],
                value: {}
            },
            dsThueBao: {
                list: [
                ],
                header: [ 
                    {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true}, 
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true}, 
                    {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true}, 
                    {fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true}, 
                    {fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true}, 
                    {fieldName: 'thoigian_ck', headerText: 'Thời gian cam kết', allowFiltering: true}, 
                    {fieldName: 'donvi_giao', headerText: 'Đơn vị giao', allowFiltering: true}, 
                    {fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận', allowFiltering: true}, 
                ],
                value: {}
            },
            modelVatTu: {},
            ttKhachHang: {
                maGd: "",
                maTb: "",
                tenKieuLd: "",
                tenTb: "",
                diachiLd: ""
            },
            ttGiaoViec: {
                phieuId: null,
                nguoiGiaoId: parseInt(this.$root.token.getNhanVienID()),
                ngayHt: null,
                ngayGiao: moment().format("DD/MM/YYYY HH:mm:ss"),
                noiDungGiao: "",
                noiDungTh: "",
                chkNgayGiao: true,
                chkNgayHt: false,
            },
            diaChi: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            tt_nd:{
                ngay_cn:this.$auth.getNgayCapNhat(),
                tentat:"",
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                //ten_dv:this.$auth.getTenDonVi()
            },
            lblLienHe: "Liên hệ : ",
            dsNhanVien: [],
            isPhieuMoi: true
        }
    },
    async mounted () {
        try{
            await this.layDanhMuc()
            await this.hienThiDanhSachHDTB()
        }
        catch(e){
            this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
        }
    },
    computed: {
        kieuPhieu(){
            return this.isPhieuMoi?0:1
        },
        loaiHopDong(){
            return LoaiHopDong.BAN_THIETBI
        },
        dichVuVt(){
            // Code cũ k thấy xử lý gì chỗ dsdichvuvt_id
            // let vdichvuvt_id = DichVuVienThong.ADSL
            // if(dsdichvuvt_id == DichVuVienThong.MEGA_EYES + "")
            //     vdichvuvt_id = DichVuVienThong.MEGA_EYES
            
            return DichVuVienThong.ADSL
        },
    },
    methods: {
        async tsbtnVatTu_Click() {
            var input = {
                        "maTb": this.ttKhachHang.maTb,
                        "dichVuVtId": 0,
                        "donViDlId": null
                }
            let data_obj = await API.getTTDanhBa_MaTB(this.axios, input);
            console.log(data_obj)
            this.modelVatTu.phieu_id = this.dsThueBao.value.phieu_id;
            this.modelVatTu.loaihd_id = LoaiHopDong.BAN_THIETBI;
            this.modelVatTu.hdtb_id = this.dsThueBao.value.hdtb_id; 
            this.modelVatTu.thuebao_id = data_obj.data.data.thuebao_id;
            this.modelVatTu.vdvvt_id = data_obj.data.data.dichvuvt_id;
            this.modelVatTu.loaitb_id = data_obj.data.data.loaitb_id;
            this.modelVatTu.baohong_id = 0;
            this.modelVatTu.tagfrm = 1;
            this.openDialog('popupVatTuThueBao');
        },
         openDialog(obj) {
            this.$refs[obj].openDialog();
        },
        async layDanhMuc(){
            this.loading(true)
            try{
                let params = {
                    vchuoi:
                        "NHANVIEN-donvi_id = " +
                        this.$root.token.getDonViID(),
                }
                await API.lay_danhmuc_thicong_internet(this.axios, params).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let dataDanhMuc = response.data.data  
                        this.dsNhanVien = dataDanhMuc.nhanvien
                    }
                    else{
                        this.$toast.error(response.data.message)
                    }
                })
            } catch(e){
                this.$toast.error(e.data.message)
                this.loading(false)
            }
        },
        async hienThiDanhSachHDTB(){
            this.loading(true)
            try{
                let params = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vtthd_id":"4",
                    "nhanvien_id": this.$root.token.getNhanVienID(),
                    "vma_gd": "0",
                    "vphieu_id": "0",
                    "vloaihd_id": LoaiHopDong.BAN_THIETBI,
                    "vkieu_id": this.kieuPhieu,
                    "donvi_nhan_id": this.$root.token.getDonViID() 
                }
                await API.lay_ds_hoancong_ban_tbi(this.axios, params).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.dsThueBao.list = response.data.data.rs1
                        if(this.dsThueBao.list.length > 0){

                        }
                        else{
                            this.dsGiaoViec.list = []
                            this.dsVatTu.list = []

                            this.ttKhachHang.maGd = ""
                            this.ttKhachHang.maTb = ""
                            this.ttKhachHang.tenKieuLd = ""
                            this.ttKhachHang.tenTb = ""
                            this.ttKhachHang.diaChiLd = ""
                            
                            this.ttGiaoViec.phieuId = null

                            this.dsThueBao.value = {}
                        }
                    }
                    else{
                        this.dsThueBao.list = []
                        this.dsGiaoViec.list = []
                        this.dsVatTu.list = []

                        this.ttKhachHang.maGd = ""
                        this.ttKhachHang.maTb = ""
                        this.ttKhachHang.tenKieuLd = ""
                        this.ttKhachHang.tenTb = ""
                        this.ttKhachHang.diaChiLd = ""
                        
                        this.ttGiaoViec.phieuId = null

                        this.dsThueBao.value = {}

                        this.$toast.error(response.data.message)
                    }
                })
            } catch(e){
                this.$toast.error(e.data.message)
                this.loading(false)
            }
        },
        async hienThiDanhSacHDTB_Phieu(vhdkh_id, vhdtb_id){
            this.loading(true)
            try{
                let params = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vhdkh_id": vhdkh_id,
                    "vhdtb_id": vhdtb_id, //4243040
                    "vloai":"2"
                }
                await API.lay_phuluc_vattu_capmienphi(this.axios, params).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.dsVatTu.list = response.data.data.rs1
                    }
                    else{
                        this.$toast.error(response.data.message)
                    }
                })
            } catch(e){
                this.$toast.error(e.data.message)
                this.loading(false)
            }
        },
        async selectedRowChanged(item){
            if(item == null){
                this.dsGiaoViec.list = []
                this.dsVatTu.list = []

                this.ttKhachHang.maGd = ""
                this.ttKhachHang.maTb = ""
                this.ttKhachHang.tenKieuLd = ""
                this.ttKhachHang.tenTb = ""
                this.ttKhachHang.diaChiLd = ""
                
                this.ttGiaoViec.phieuId = null

                this.dsThueBao.value = {}
                return
            }

            await this.hienThiDanhSacHDTB_Phieu(0, item.hdtb_id)
            await this.hienThiDSNV(item.phieu_id)

            this.ttKhachHang.maGd = item.ma_gd
            this.ttKhachHang.maTb = item.ma_tb

            this.ttKhachHang.tenKieuLd = item.ten_kieuld
            this.ttKhachHang.tenTb = item.ten_tb
            this.ttKhachHang.diaChiLd = item.diachi_ld
            
            this.ttGiaoViec.phieuId = item.phieu_id

            this.dsThueBao.value = {...item}
        },
        async hienThiDSNV(vphieu_id){
            this.loading(true)
            try{
                let params = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vphieu_id": vphieu_id,
                    "nhanvien_id": this.$root.token.getNhanVienID(),
                    "vkieu_id": "2"
                }
                await API.lay_ds_nhanvien_theo_phieu_id(this.axios, params).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.dsGiaoViec.list = response.data.data.rs1
                        if(this.dsGiaoViec.list.length > 0){
                            this.ttGiaoViec.chkNgayGiao = true
                            this.ttGiaoViec.ngayGiao = this.dsGiaoViec.list[0].ngaygiao
                            this.ttGiaoViec.nguoiGiaoId = this.dsGiaoViec.list[0].nhanvien_giao_id
                        }
                        else{
                            this.ttGiaoViec.chkNgayGiao = true
                            this.ttGiaoViec.ngayGiao = moment().format("DD/MM/YYYY HH:mm:ss")
                            this.ttGiaoViec.nguoiGiaoId = parseInt(this.$root.token.getNhanVienID())
                        }
                    }
                    else{
                        this.dsGiaoViec.list = []
                        this.ttGiaoViec.chkNgayGiao = true
                        this.ttGiaoViec.ngayGiao = moment().format("DD/MM/YYYY HH:mm:ss")
                        this.ttGiaoViec.nguoiGiaoId = parseInt(this.$root.token.getNhanVienID())
                        
                        this.$toast.error(response.data.message)
                    }
                })
            } catch(e){
                this.$toast.error(e.data.message)
                this.loading(false)
            }
        },
        async layTT(){
            try{
                await this.hienThiDanhSachHDTB()
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        giaoViec(){
            if(this.dsThueBao.list.length == 0){
                this.$toast.error('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
                return
            }

            if(this.ttGiaoViec.nguoiGiaoId == null){
                this.$toast.error('Hãy nhập nhân viên giao việc!')
                this.$refs.cboNguoiGV.focus()
                return
            }

            if(this.ttGiaoViec.chkNgayGiao == false || this.ttGiaoViec.ngayGiao == null){
                this.$toast.error('Hãy nhập ngày giao việc!')
                this.$refs.chkNgayGiao.focus()
                return
            }

            let curThueBao = this.dsThueBao.value
            if(curThueBao.loaitb_id != LoaiHinhTB.INTERNET_ADSL_TINH_KHAC && curThueBao.loaitb_id != LoaiHinhTB.INTERNET_FIBER_TINH_KHAC){
                if(this.ttGiaoViec.chkNgayHt == true && this.ttGiaoViec.ngayHt != null){
                    let giao = moment(this.ttGiaoViec.ngayGiao, this.dateFormat)
                    let hoanThanh = moment(this.ttGiaoViec.ngayHt, this. dateFormat)
                    if(hoanThanh.diff(giao) <= 0){
                        this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
                        return
                    }
                }
            }

            this.$refs.formgiaophieu.init()
            this.$refs.giaoPhieuNhanVien.show(true)
        },
        async giaoviec_success(){
            try{
                await this.hienThiDSNV(this.dsThueBao.value.phieu_id)
            } catch (e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        async hoanCong(){
            if(this.kieuPhieu != 0 || this.dsThueBao.list.length == 0){
                this.$toast.error('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
                return
            }

            await this.$bvModal.msgBoxConfirm('Bạn có muốn hoàn công mã giao dịch này?', {
                title: 'Thông báo',
                size: 'lg',
                okTitle: 'Yes',
                cancelTitle: 'No',
            }).then(async v => {
                if(!v) {
                return;
                }
                await this.thucHienHoanCong()
                await this.hienThiDanhSachHDTB()
            });
        },
        async thucHienHoanCong(){
            this.loading(true)
            try{
                let params = {
                    "vhdtb_id":this.dsThueBao.value.hdtb_id,
                    "vphieu_id":this.dsThueBao.value.phieu_id,
                    "vtthd_id": 6,
                    "vnhanvien_id":this.$root.token.getNhanVienID(),
                    "vmay_cn":this.$root.token.getMaNhanVien(), //getMachine(),
                    "vnguoi_cn":this.$root.token.getUserName()
                }
                await API.hoancong_bantbi(this.axios, params).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        //TODO lien he DB xme raise kieu gi
                        this.$toast.success('Hoàn công thành công!')
                    }
                    else{
                        this.$toast.error(response.data.message)
                    }
                })
            } catch(e){
                this.$toast.error(e.data.message)
                this.loading(false)
            }
        },
        chuyenTo(){
           this.$refs.popupChuyenTo.openDialog(this.dsThueBao.value.phieu_id, this.dsThueBao.value.hdtb_id)
        },
        traPhieu(){
            if(this.dsThueBao.list.length == 0){
                this.$toast.error('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
                return
            }
            this.$refs.popupTraPhieu.show(true)
        },
        capnhatTH(){

        },
        selectedRowGiaoViecChanged(item){
            if(item == null){
                this.ttGiaoViec.chkNgayGiao = true
                this.ttGiaoViec.ngayGiao = moment().format("DD/MM/YYYY HH:mm:ss")
                this.ttGiaoViec.nguoiGiaoId = parseInt(this.$root.token.getNhanVienID())
                return
            }
            this.ttGiaoViec.chkNgayGiao = true
            this.ttGiaoViec.ngayGiao = item.ngaygiao
            this.ttGiaoViec.nguoiGiaoId = item.nhanvien_giao_id
            //this.ttGiaoViec.noiDungGiao = item.str_nhiemvu
            //this.ttGiaoViec.noiDungTh = item.nhiemvu
        }
    }
};
</script>