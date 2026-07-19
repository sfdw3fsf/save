<template src="./CompleteEquipmentPurchase.html"></template>
<style scoped src="./CompleteEquipmentPurchase.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'

import { LoaiHopDong, TrangThaiHD, DichVuVienThong } from '@/utils/Enum'
import { LoaiHinhTB } from '@/const/enums'

export default {
    name: 'CompleteEquipmentSale',
    components: {
        breadcrumb,
        ModalChonDiaChi,
        KDatePicker
    },
    data() {
        return {
            dateFormat: "DD/MM/YYYY HH:mm:ss",
            animationSettings: { effect: 'Zoom' },
            isPhieuMoi: 1,
            header: {
                title: "Danh sách mua thiết bị",
                list: [
                    //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
                    { name: 'Quản lý vật tư', link: { name: 'Ui.cards' } },
                    { name: 'Danh sách mua thiết bị', link: { name: 'Ui.cards' } },
                ]
            },
            dsVatTu: {
                list: [
                ],
                header: [ 
                    {fieldName: 'LOAI_TBI', headerText: 'Tên thiết bị', allowFiltering: true}, 
                    {fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true}, 
                ],
                value: {}
            },
            dsThueBao: {
                list: [
                ],
                header: [ 
                    {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true}, 
                    {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', allowFiltering: true}, 
                    {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true}, 
                    {fieldName: 'NGAY_YC', headerText: 'Ngày yêu cầu', allowFiltering: true}, 
                    {fieldName: 'NGAYGIAO', headerText: 'Ngày giao', allowFiltering: true}, 
                    {fieldName: 'MA_GD', headerText: 'Mã giao dịch', allowFiltering: true}, 
                    {fieldName: 'THOIGIAN_CK', headerText: 'Thời gian cam kết', allowFiltering: true}, 
                    {fieldName: 'DONVI_GIAO', headerText: 'Đơn vị giao', allowFiltering: true}, 
                    {fieldName: 'DONVI_NHAN', headerText: 'Đơn vị nhận', allowFiltering: true}, 
                    {fieldName: 'TTHT', headerText: 'Trạng thái hiện tại', allowFiltering: true}, 
                ],
                value: {}
            },
            ttKhachHang: {
                maGd: "",
                maTb: "",
                tenKieuLd: "",
                tenTb: "",
                diachiLd: ""
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
        }
    },
    computed: {
        tongChon(){
            return 0
        }
    },
    watch: {
        'isPhieuMoi': async function(newVal){
            await this.hienThiDanhSachHDTB()
        },
        'dsThueBao.list': function(newVal){
            this.dsThueBao.list.forEach(ele => {
                ele.NGAY_YC = ele.NGAY_YC != null ? moment(ele.NGAY_YC).format('DD/MM/YYYY HH:mm:ss') : null
                ele.NGAYGIAO = ele.NGAYGIAO != null ? moment(ele.NGAYGIAO).format('DD/MM/YYYY HH:mm:ss') : null
                ele.THOIGIAN_CK = ele.THOIGIAN_CK != null ? moment(ele.THOIGIAN_CK).format('DD/MM/YYYY HH:mm:ss') : null
            });
        },
    },
    async mounted(){
        await this.hienThiDanhSachHDTB()
    },
    methods: {
        async layTT(){
            await this.hienThiDanhSachHDTB()
        },
        async muaTbi(){
            if(this.isPhieuMoi == 0 || this.dsThueBao.list.length == 0)
            return

            await this.$bvModal.msgBoxConfirm('Bạn có xác nhận đã mua thiết bị cho thuê bao này?', {
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
                    "HDTB_ID":this.dsThueBao.value.HDTB_ID
                }
                await API.fn_hoancong_hd_frmhoancongmuatbi(this.axios, {
                    "ds_para": JSON.stringify(params)
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let res = JSON.parse(response.data.data)
                        if(res.ERROR_CODE == 1)
                            this.$toast.success('Hoàn công thành công!')
                        else
                            this.$toast.error('Hoàn công lỗi!' + res.MESSAGE)

                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi: ' + e.data.message_detail)
                this.loading(false)
            }
        },
        async selectedRowChanged(item){
            if(item == null){
                this.ttKhachHang.maGd = ""
                this.ttKhachHang.maTb = ""
                this.ttKhachHang.tenKieuLd = ""
                this.ttKhachHang.tenTb = ""
                this.ttKhachHang.diaChiLd = ""

                this.dsThueBao.value = {}
                return
            }

            await this.hienThiDanhSachHDTB_Phieu(0, item.HDTB_ID)

            this.ttKhachHang.maGd = item.MA_GD
            this.ttKhachHang.maTb = item.MA_TB
            this.ttKhachHang.tenKieuLd = item.TEN_KIEULD
            this.ttKhachHang.tenTb = item.TEN_TB
            this.ttKhachHang.diaChiLd = item.DIACHI_LD

            this.dsThueBao.value = {...item}
        },
        async hienThiDanhSachHDTB(){
            this.loading(true)
            try{
                let params = {
                    "NHANVIEN_ID": this.$root.token.getNhanVienID(),
                    "PHIEUMOI": this.isPhieuMoi == 1 ? 0 : 1
                }
                await API.fn_hienthidanhsachhdtb_frmhoancongmuatbi(this.axios, {
                    "ds_para": JSON.stringify(params)
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let res = JSON.parse(response.data.data)
                        // console.log(JSON.parse(res.RESULT.DTLIST))
                        if(res.ERROR_CODE == 1)
                            this.dsThueBao.list = JSON.parse(res.RESULT.DTLIST)
                        else
                            this.dsThueBao.list = []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi: ' + e.data.message_detail)
                this.loading(false)
            }
        },
        async hienThiDanhSachHDTB_Phieu(vhdkh_id, vhdtb_id){
            this.loading(true)
            try{
                let params = {
                    "HDTB_ID": vhdtb_id,
                    "HDKH_ID": vhdkh_id
                }
                await API.fn_hienthidanhsachdtb_phieu_frmhoancongmuatbi(this.axios, {
                    "ds_para": JSON.stringify(params)
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let res = JSON.parse(response.data.data)
                        // console.log(JSON.parse(res.RESULT.DTVTDAGAN)) 
                        if(res.ERROR_CODE == 1)
                            this.dsVatTu.list = JSON.parse(res.RESULT.DTVTDAGAN)
                        else
                            this.dsVatTu.list = []
                    }
                    else
                        throw response.data.message
                })
            } catch(e){
                this.$toast.error('Có lỗi: ' + e.data.message_detail)
                this.loading(false)
            }
        }
    }
}
</script>