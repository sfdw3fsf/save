<template src="./RevocationCACerCreatesError.html"></template>
<style scoped src="./RevocationCACerCreatesError.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import ModalTimKiemHDCA from './components/popupTimKiemHDCA'
import ModalCapNhatDv from './components/popupCapNhatDv'
import { LoaiHopDong, DichVuVienThong } from '@/utils/Enum'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';
import UploadEProfileContent from '@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue'

export default {
    name: 'RevocationCACerCreatesError',
    components: { 
        breadcrumb,
        ModalTimKiemHDCA,
        ModalCapNhatDv,
        GiaoPhieuView,
        UploadEProfileContent,
    },
    data() {
        return {
            header: {
                title: "Đăng ký huỷ chứng thư lỗi",
                list: [
                    //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
                    { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
                    { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
                ]
            },
            animationSettings: { effect: 'Zoom' },
            isShowUploadEProfile: false,
            isNhapMoi: false,
            dsLoaiHd: [],
            dsBienDong: {
                list: [],
                header: [
                     {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true}, 
                     {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true}, 
                     {fieldName: 'diachi_tb', headerText: 'Địa chỉ', allowFiltering: true}, 
                ],
                value: {}
            },
            frm: {
                loaiHdId: "",
                quyTrinhCu: "",
                tenKhachHang: "",
                maGd: "",
                maTbCu: "",
                maTbMoi: "",
                tenTb: "",
                diaChi: "",
                nguoiCn: "",
                serial: "",
                tenChuThe: "",
                userName: "",
                loaiCts: "",
                hinhThucSinhKhoa: "",
                goiCuoc: "",
                ngayBd: "",
                ngayKt: "",
                soThangSd: ""
            },
            hdtbIdCha: 0,
            tocDoId: 0,
            huongGiaoId: 0,
            quyTrinhId: 0,
            hdtbId: 0,
            hdkhId: 0,
            giaoPhieuViewDialog: {
                animationSettings: { effect: 'None' },
                isVisiable: false,
                input: {
                    vhuonggiao_id: null,
                    vloai_hd: null,
                    vdichvuvt_id: null,
                    vma_gd: null,
                    vquytrinh: null,
                    vtrangthaiphieu: 1,
                }
            },
        }
    },
    async mounted () {
        this.loading(true)
        try{
            await API.lay_loai_hd(this.axios)
            .then(response => {
                this.loading(false)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                    this.dsLoaiHd = response.data.data.filter(item => {
                        return [1,41].includes(item.LOAIHD_ID)
                    })
                    this.frm.loaiHdId = LoaiHopDong.DAT_MOI
                }
            })
        } catch(e){
            this.loading(false)
            this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
        }
    },
    methods: {
        nhapMoi(){
            this.xoaForm()
        },
        xoaForm(){
            this.hdtbIdCha = 0
            this.huongGiaoId = 0
            this.quyTrinhId = 0
            this.hdtbId = 0
            this.hdkhId = 0 

            Object.keys(this.frm).forEach(key => {
                if(key !== 'loaiHdId')
                    this.frm[key] = '';
            });

            this.dsBienDong.list = []
        },
        async hienThiThongTinHDTBCu(){
            this.loading(true)
            try{
                this.isNhapMoi = false
                let params = {
                    'vhdtb_id': this.hdtbIdCha
                }
                await API.lay_thongtin_hd_ca_biloi(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        let dt = response.data.data[0]

                        this.frm.maTbCu = dt.ma_tb
                        this.frm.diaChi = dt.diachi_tb
                        this.frm.tenChuThe = dt.ten_kh
                        this.frm.serial = dt.serial
                        this.frm.userName = dt.username
                        this.frm.loaiCts = dt.loai_cts
                        this.frm.hinhThucSinhKhoa = dt.ht_sinhkhoa
                        this.frm.goiCuoc = dt.thuonghieu
                        this.frm.ngayBd = dt.ngay_bd
                        this.frm.ngayKt = dt.ngay_kt
                        this.frm.soThangSd = dt.so_thang
                        this.frm.nguoiCn = dt.nguoi_cn
                        this.frm.tenKhachHang = dt.ten_kh
                        this.frm.quyTrinhCu = dt.quytrinh
                        this.frm.tenTb = dt.ten_tb //
                    
                        this.tocDoId = dt.tocdo_id
                    }
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        frmTimKiemHDCA(){
            this.$bvModal.show('popupTimKiemHDCA')
        },
        async ghiLai(){
            if(!this.hdtbIdCha){
                this.$toast.error('Không có hợp đồng để thực hiện!')
                return
            }
            this.loading(true)
            try{
                const coHuongGiao = await this.layHuongGiaoTiepNhan()
                if(coHuongGiao){
                    let params = {
                        "vhdtb_id_cu": this.hdtbIdCha,
                        "vhuonggiao_id": this.huongGiaoId,
                        "vmay_cn": this.$root.token.getMaNhanVien(),//getMachine(),
                        "vip_cn": this.$root.token.getUserName(), //getIP(),
                        "vnguoi_cn": this.$root.token.getUserName()
                    }
                    await API.capnhat_hd_thuhoi_ca_loi(this.axios, params)
                    .then(response => {
                        this.loading(false)
                        if (response && response.data && response.data.error_code === 'BSS-00000000') {
                            const kq = JSON.parse(response.data.data)
                            if (kq.message.toLowerCase() != 'ok'){
                                throw kq.message
                            }
                            this.hdtbId = kq.hdtb_id
                            this.hdkhId = kq.hdkh_id
                            this.$toast.success('Cập nhật hợp đồng thành công')
                            this.hienThiHopDongTBThuHoi()
                        }
                    })
                }
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình cập nhật dữ liệu ' + e)
            }
        },
        async layHuongGiaoTiepNhan(){
            this.loading(true)
            try{
                let params = {
                    'vkieuld_id': 13263,
                    'vtocdo_id': this.tocDoId,
                    'vmuccuoc_id': 1,
                    'vthutu_id': 1,
                    'vloaidv_nguon_id': 8,
                    'vloaihd_id': 4
                }
                return await API.lay_huonggiao_tiepnhan_dvcntt(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        let dt = response.data.data[0]
                        this.huongGiaoId = dt.huonggiao_id
                        this.quyTrinhId = dt.quytrinh_id
                        return true
                    }
                    else{
                        this.$toast.error('Quy trình chưa được thiết lập, vui lòng liên hệ quản trị viên')
                        return false
                    }
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                return fasle
            }
        },
        async hienThiHopDongTBThuHoi(){
            this.loading(true)
            try{
                let params = {
                    'vhdtb_id': this.hdtbId
                }
                await API.lay_hdtb_thuhoi_ca_loi(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        let dt = response.data.data[0]
                        
                        this.frm.diaChi = dt.diachi_tb
                        this.frm.tenChuThe = dt.ten_tb
                        this.frm.maGd = dt.ma_gd
                        this.frm.maTbMoi = dt.ma_tb
                        this.frm.nguoiCn = dt.nguoi_cn
                        this.frm.serial = dt.serial
                        this.frm.userName = dt.username
                        this.frm.loaiCts = dt.loai_cts
                        this.frm.hinhThucSinhKhoa = dt.ht_sinhkhoa
                        this.frm.goiCuoc = dt.thuonghieu
                        this.frm.ngayBd = dt.ngay_bd
                        this.frm.ngayKt = dt.ngay_kt
                        this.frm.soThangSd = dt.so_thang
                        this.frm.tenKhachHang = dt.ten_kh //
                        this.frm.tenTb = dt.ten_tb //

                        this.dsBienDong.list = response.data.data
                    }
                    else{
                        this.dsBienDong.list = []
                    }
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                return fasle
            }
        },
        xoaHd(){
            if(!this.hdkhId){
                this.$toast.error('Không có hợp đồng để thực hiện!')
                return
            }
            this.$bvModal.msgBoxConfirm('Bạn có chắc là sẽ xóa hợp đồng?', {
                title: 'Thông báo',
                size: 'md',
                okTitle: 'Yes',
                cancelTitle: 'No',
            }).then(async v => {
                if(!v) {
                return;
                }
                this.thucHienXoaHd()
            });
        },
        async thucHienXoaHd(){
            this.loading(true)
            try{
                let params = {
                    'hdkh_id': this.hdkhId,
                    'loaihd_id': LoaiHopDong.CHAMDUT_SD
                }
                await API.xoa_hdkh_v2(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.xoaForm()
                        this.$toast.success('Xóa hợp đồng thành công!')
                    }
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình xoá hợp đồng ' + e)
            }
        },
        async timKiemTheoMaGd(){
            if(this.frm.maGd == ''){
                this.$toast.error('Bạn chưa nhập thông tin tìm kiếm')
                return
            }
            this.loading(true)
            try{
                let params = {
                    'vma_gd': this.frm.maGd,
                    'vnguoi_cn': this.$root.token.getUserName()
                }
                await API.lay_hopdong_thuhoi_ca_loi_theoma_gd(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        let dt = response.data.data[0]

                        this.hdtbId = dt.hdtb_id
                        this.hdkhId = dt.hdkh_id
                        this.quyTrinhId = dt.quytrinh_id
                        this.huongGiaoId = dt.huonggiao_id
                       
                        this.frm.maGd = dt.ma_gd
                        this.hienThiHopDongTBThuHoi()
                    }
                    else
                        this.$toast.error('Không tìm được hợp đồng')
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        async timKiemTheoMaTb(){
            if(this.frm.maTbCu == ''){
                this.$toast.error('Bạn chưa nhập thông tin tìm kiếm')
                return
            }
            this.loading(true)
            try{
                let params = {
                    'vma_tb': this.frm.maTbCu,
                    'vnguoi_cn': this.$root.token.getUserName(),
                    'vloaihd_id': this.frm.loaiHdId
                }
                await API.lay_hd_ca_genloi_theo_matb(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        let dt = response.data.data[0]
                        this.hdtbIdCha = dt.hdtb_id
                        this.hienThiThongTinHDTBCu()
                    }
                    else
                        this.$toast.error('Không tìm được thông tin')
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        async giaoPhieu(){
           
            if(!this.hdkhId){
                this.$toast.error('Không có hợp đồng để thực hiện!')
                return
            }
            const kiemTra = await this.kiemTraSlFileCa()
            if(kiemTra){
                const huongGiao = await this.layHuongGiao()
                console.log(huongGiao)
                if(huongGiao !== 0){
                    this.giaoPhieuViewDialog.input = {
                        vhuonggiao_id:  parseInt(huongGiao), 
                        vloai_hd: LoaiHopDong.CHAMDUT_SD,
                        vdichvuvt_id: DichVuVienThong.ANTOAN_BAOMAT_TT,
                        vma_gd: this.frm.maGd,
                        vquytrinh: this.quyTrinhId,
                        vtrangthaiphieu: 1,
                    };
                    this.GiaoPhieuView_OnClick()
                }
            }
        },
        async onCloseGiaoPhieu(){
            this.loading(true)
            try{
                let params = {
                    'vma_gd': this.frm.maGd,
                    'vnguoi_cn': this.$root.token.getUserName()
                }
                await API.lay_hopdong_thuhoi_ca_loi_theoma_gd(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        let dt = response.data.data[0]

                        this.hdtbId = dt.hdtb_id
                        this.hdkhId = dt.hdkh_id
                        this.quyTrinhId = dt.quytrinh_id
                        this.huongGiaoId = dt.huonggiao_id
                       
                        this.frm.maGd = dt.ma_gd
                        this.hienThiHopDongTBThuHoi()
                    }
                    else                        
                        this.xoaForm()
                        //this.$toast.error('Không tìm được hợp đồng')
                })
            } catch(e) {
                this.loading(false)
                this.xoaForm()
                //this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        onCloseUpHs(){

        },
        async layHuongGiao(){
            this.loading(true)
            try{
                let params = {
                    'quytrinh_id': this.quyTrinhId,
                    'thutu_id': 2
                }
                return await API.fn_lay_huonggiao_quytrinh(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        return response.data.data
                    }
                    else{
                        this.$toast.error('Quy trình chưa được thiết lập, vui lòng liên hệ quản trị viên')
                        return
                    }
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                return
            }
        },
        async kiemTraSlFileCa(){
            this.loading(true)
            try{
                let params = {
                    'vhdkh_id': this.hdkhId,
                    'vhdtb_id': this.hdtbId,
                    'vkt': 1
                }
                return await API.kt_soluong_file_ca_v3(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.toLowerCase() === 'ok') {
                        return true
                    }
                    else{
                        this.$toast.error(response.data.data)
                        return false
                    }
                })
            } catch(e) {
                this.loading(false)
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                return false
            }
        },
        upHoSo(){
            if(this.dsBienDong.list.length > 0){
                try{
                    this.$refs.UploadEProfileContent.isPopup = true
                    this.$refs.UploadEProfileContent.txtMaGD_txtMa_TB = this.frm.maGd
                    this.$refs.UploadEProfileContent.txtMaGD_txtMa_TB_Enter()
                    this.$refs.UploadEProfileContent.listbox.ds_file_hs.value.ma_tb=this.frm.maTbMoi
                    this.$refs.UploadEProfileContent.onMaTBEnter()  
                }
                catch(e){
                    console.log('Mo form up load ho so loi ' + e)
                }
                finally{
                    this.$refs.UploadEProfile.show(true)
                }
            }
            else
                this.$toast.error('Bạn chưa chọn thuê bao nào')
        },
        capNhatDv(){
            if(!this.hdtbId){
                this.$toast.error('Chưa cập nhật hợp đồng!')
                return
            }
            this.$bvModal.show('popupCapNhatDv')
        },
        selectedRowChanged(obj){
            this.dsBienDong.value = {...obj}
        },
        async GiaoPhieuView_OnClick() {
            this.giaoPhieuViewDialog.isVisiable = true
            this.$refs.GiaoPhieuView.show()
        },
        async GiaoPhieuView_OnClose() {
            this.giaoPhieuViewDialog.isVisiable = false
        },
    }
}
</script>