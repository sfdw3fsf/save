<template src="./UpdateResultCoordinationTicket.html"></template>
<style scoped src="./UpdateResultCoordinationTicket.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import { TRANGTHAI_PH } from '@/utils/Enum'


export default {
    name: 'UpdateResultCoordinationTicket',
    components: { 
        breadcrumb, 
        KDatePicker,
        DataGridCustom
    },
    data: function() {
        return {
            header: {
                title: 'Xử lý phiếu xác minh, phối hợp',
                list: [
                    { name: 'Phát triển thuê bao', link: {name: 'Ui.cards'} },
                    { name: 'Cập nhật kết quả xử lý phiếu phối hợp',link: {name: 'Ui.buttons'} }
                ]
            },
            showNutCapNhat: false,
            showNgayDk: false,
            dateFormat: "DD/MM/YYYY HH:mm:ss",
            dateFormatDk: "DD/MM/YYYY",
            options: {
                dsTrangThai: [],
                dsKetQuaXl: []
            },
            frm: {
                ttThueBao: {
                    maTb: "",
                    tenTb: "",
                    maGd: "",
                    diaChiLd: "",
                    ngayYc: "",
                    trangThai: 0,
                    ngayGiao: "",
                    nguoiGiao: "",
                    huongGiao: "",
                    noiDungGiao: ""
                },
                ttXuLy: {
                    ketQuaXl: 0,
                    ngayDk: "",
                    noiDung: ""
                }
            },
            dsPhieu: {
                list: [],
                header: [ 
                    {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true}, 
                    {fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true}, 
                    {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true}, 
                    {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true}, 
                    {fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true}, 
                    {fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true}, 
                    {fieldName: 'nhanvien_giao', headerText: 'Người giao', allowFiltering: true}, 
                    {fieldName: 'huonggiao', headerText: 'Hướng giao', allowFiltering: true}, 
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true}, 
                    {fieldName: 'lydo_ton', headerText: 'Lý do tồn', allowFiltering: true}, 
                ],
                value: {}
            },
        }
    },
    async mounted(){
        try{
            this.frm.ngayDk = moment().format(this.dateFormatDk)
            let promises = []
            promises.push(this.LoadTrangThaiXL())
            promises.push(this.LoadKetQuaXL())
            await Promise.all(promises)
        } catch(e){
            this.$toast.error('Có lỗi xảy ra trong quá trình lấy dữ liệu ' + e)
        }
    },
    methods: {
        async timKiem(){
            try{
                await this.HienThiDsPhieuPHGiaoDenNhanVien()
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        async capNhat(){
            if(this.dsPhieu.list.length == 0){
                this.$toast.error('Không có thông tin phiếu cập nhật!')
                return
            }

            try{
                await this.CapNhatKetQuaXL()
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        async LoadTrangThaiXL(){
            this.loading(true)
            try{
                await API.lay_danhmuc_trangthai_ph(this.axios)
                .then(response => {
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.options.dsTrangThai = response.data.data
                        if(response.data.data.length > 0)
                            this.frm.ttThueBao.trangThai = response.data.data[0].id
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        async LoadKetQuaXL(){
            this.loading(true)
            try{
                await API.lay_kq_xuly_ph(this.axios)
                .then(response => {
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.options.dsKetQuaXl = response.data.data
                        if(response.data.data.length > 0){
                            this.frm.ttXuLy.ketQuaXl = response.data.data[0].id
                            if(response.data.data[0].id == 129)
                                this.showNgayDk = true
                            else
                                this.showNgayDk = false
                        }
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        async HienThiDsPhieuPHGiaoDenNhanVien(){
            this.loading(true)
            try{
                let params = {
                    "loai": this.frm.ttThueBao.trangThai,
                    "nhanvien_id": this.$root.token.getNhanVienID()
                }
                await API.lay_danhsach_phieu_ph(this.axios, params)
                .then(response => {
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.dsPhieu.list = response.data.data
                    }
                    else {
                        this.dsPhieu.list = []
                        this.Clear()
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        Clear(){
            this.frm.ttThueBao.maTb = ""
            this.frm.ttThueBao.tenTb = ""
            this.frm.ttThueBao.maGd = ""
            this.frm.ttThueBao.diaChiLd = ""
            this.frm.ttThueBao.ngayYc = ""
            this.frm.ttThueBao.ngayGiao = ""
            this.frm.ttThueBao.nguoiGiao = ""
            this.frm.ttThueBao.huongGiao = ""
            this.frm.ttThueBao.noiDungGiao = ""

            this.frm.ttXuLy.ngayDk = ""
            this.frm.ttXuLy.noiDung = ""
            this.frm.ttXuLy.ketQuaXl = this.options.dsKetQuaXl.length > 0 ? this.options.dsKetQuaXl[0].id : 0
        },
        async CapNhatKetQuaXL(){
            this.loading(true)
            try{
                if(this.frm.ttXuLy.ketQuaXl == 129){
                    let ngayDk = moment(this.frm.ttXuLy.ngayDk, this.dateFormatDk)
                    let ngayHt = moment()
                    if(ngayDk.diff(ngayHt) < 0){
                        this.$toast.error('Ngày dự kiến không được nhỏ hơn ngày hiện tại!')
                        this.$refs.dtNgayDk.focus()
                        return
                    }
                    if(this.frm.ttXuLy.noiDung == ""){
                        this.$toast.error('Hãy nhập nội dung xử lý!')
                        this.$refs.txtNoiDung.focus()
                        return
                    }
                }
                let params = {
                    "phieu_id": this.dsPhieu.value.phieu_id,
                    "nhanvien_th_id":  this.$root.token.getNhanVienID(),
                    "ttph_id": TRANGTHAI_PH.DATHUCHIEN,
                    "noidung_th": this.frm.ttXuLy.noiDung,
                    "ngay_dkht": this.frm.ttXuLy.ngayDk,
                    "kqxl_id": this.frm.ttXuLy.ketQuaXl,
                    "may_cn": this.$root.token.getMaNhanVien(), //getMachine(),
                    "nguoi_cn": this.$root.token.getUserName(),
                    "vip_cn": this.$root.token.getUserName(), //getIP(),
                }
                await API.capnhat_xl_phieu_ph(this.axios, params)
                .then(async response => {
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.$toast.succes('Cập nhật xử lý phiếu thành công!')
                        await this.HienThiDsPhieuPHGiaoDenNhanVien()
                    }
                    else {
                        this.$toast.error(response.data.message_detail)
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        selectedRowChanged(data){
            if(data === null)
                return false
            this.dsPhieu.value = {...data}
            this.frm.ttThueBao.maTb = data.ma_tb
            this.frm.ttThueBao.tenTb = data.ten_tb
            this.frm.ttThueBao.maGd = data.ma_gd
            this.frm.ttThueBao.diaChiLd = data.diachi_ld
            this.frm.ttThueBao.ngayYc = data.ngay_yc
            this.frm.ttThueBao.ngayGiao = data.ngaygiao
            this.frm.ttThueBao.nguoiGiao = data.nhanvien_giao
            this.frm.ttThueBao.huongGiao = data.huonggiao
            this.frm.ttThueBao.noiDungGiao = data.nd_giao
            
            if(this.frm.ttThueBao.trangThai == 2){
                this.showNutCapNhat = false
                this.frm.ttXuLy.noiDung = data.nd_thuchien
                this.frm.ttXuLy.ketQuaXl = data.kqxl_id
                if(data.kqxl_id == 129){
                    this.frm.ttXuLuy.ngayDk = data.ngay_dkht
                }
            }
            else {
                this.showNutCapNhat = true
                this.frm.ttXuLy.noiDung = ""
                this.frm.ngayDk = moment().format(this.dateFormatDk)
            }
        },
        async cboTrangThai_EditValueChanged(){
            try{
                await this.HienThiDsPhieuPHGiaoDenNhanVien()
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
        },
        cboKetQua_XL_EditValueChanged(){
            if(this.frm.ttXuLy.ketQuaXl == 129)
                this.showNgayDk = true
            else
                this.showNgayDk = false
        }
    }
}
</script>