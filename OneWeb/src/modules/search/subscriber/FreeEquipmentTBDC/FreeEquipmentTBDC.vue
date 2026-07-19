<template src="./FreeEquipmentTBDC.html"></template>
<style scoped src="./FreeEquipmentTBDC.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import DataGrid2 from '@/components/Controls/DataGrid2'
import SearchContractModal from '@/modules/contract/setup/HandoverTicket/components/SearchContractModal'
import ModalNormalizeCAinformation from "@/modules/contract/setup/NormalizeCAinformation/ModalNormalizeCAinformation";
import popupBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices";
import popupTienKM from "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM";
import { LoaiHopDong, TrangThaiHD } from '@/utils/Enum'
import { LOAI_DV } from '@/const/enums'
import ModalCTThueBao from './components/popupCTThueBao'

export default {
    name: 'FreeEquipmentTBDC',
    components: { 
        breadcrumb, 
        DatePicker, 
        DataGrid2,
        SearchContractModal,
        ModalNormalizeCAinformation,
        popupBuyDevices,
        popupTienKM,
        ModalCTThueBao
    },
    data: function() {
        return {
            header: {
                title: 'Trang bị miễn phí TBDC',
                list: [
                    { name: 'Lập hợp đồng/Đổi tốc độ', link: {name: 'Ui.cards'} },
                    { name: 'Trang bị miễn phí TBDC',link: {name: 'Ui.buttons'} }
                ]
            },
            dateFormat: "DD/MM/YYYY",

            row_1: true,
			row_2: true,
			row_3: true,
            row_4: true,

            isNhapMoi: true,
            
            options: {
                dsDichVuVt: [],
                dsLoaiHinhTb: [],
                dsKieuLd: [],
            },

            ttKhachHang: {
                khachHangId: 0,
                thueBaoId: 0,
                hdkhId: 0,
                hdtbId: 0,
                donViId: 0,
                maGd: "",
                ngayYc: "",
                maHd: "",
                maTb: "",
                diaChiTb: "",
                dichVuVtId: 0,
                ghiChu: "",
                maKh: "",
                tenKh: "",
                tenTb: "",
                diaChiLd: "",
                loaiTbId: 0,
                trangThaiTb: "",
                kieuLdId: 7001,
                doiTuongId: 0,
                tocDoId: 0,
                mucCuocId: 0,
                ngayLapHd: moment(new Date()).format("DD/MM/YYYY"),
                ttDiaChi: {}
            },
            dsKhoanMuc: {
                list: [],
            },
            dsThietBi: {
                list: [],
            },
            dsThueBao: {
                list: [],
            },
            dsHopDong: {
                list: [],
                header: [ 
                    {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true}, 
                    {fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true}, 
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true}, 
                ],
                value: {}
            },
            layoutDV: [],
            popupTienKMDuLieu: {}
        };
    },
    computed: {
        dsLoaiHinhTbFiltered(){
            return this.options.dsLoaiHinhTb.filter(item => {
                return item.DICHVUVT_ID == this.ttKhachHang.dichVuVtId
            })
        },
        tongTienTb(){
            var tien = 0
            this.dsThietBi.list.forEach(thietBi => {
                tien += thietBi.soluong * thietBi.tien
            });
            return tien
        },
        tongVatTb(){
            var vat = 0
            this.dsThietBi.list.forEach(thietBi => {
                vat += thietBi.soluong * thietBi.vat
            });
            return vat
        },
        tongDoanhThu(){
            var doanhThu = 0
            this.dsThueBao.list.forEach(thueBao => {
                doanhThu += thueBao.doanhthu
            });
            return doanhThu
        },
        loaiHopDong(){
            return LoaiHopDong.BAN_THIETBI
        },
        trangThaiHd(){
            return TrangThaiHD.MOI
        },
    },
    async mounted(){
        try{
            var promises = []
            promises.push(this.layDichVuVt())
            promises.push(this.layLoaiHinhTb())
            promises.push(this.layKieuLd())
            await Promise.all(promises)
            if(this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0){
                this.ttKhachHang.kieuLdId = Number(this.$route.query.tag)
            } 
        } catch(e){
            console.log(e)
        }
    },
    methods: {
        async layDichVuVt(){
            this.loading(true)
            try{
                await API.css_dichvu_vt(this.axios).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.options.dsDichVuVt = response.data.data   
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async layLoaiHinhTb(){
            this.loading(true)
            try{
                await API.css_loaihinh_tb(this.axios).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.options.dsLoaiHinhTb = response.data.data   
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async layKieuLd(){
            this.loading(true)
            try{
                await API.lay_ds_kieu_ld(this.axios, {
                    "vloaihd_id": LoaiHopDong.BAN_THIETBI,
                    "vloaitb_id": this.ttKhachHang.loaiTbId
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.options.dsKieuLd = response.data.data   
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e.message)
                this.loading(false)
            }
        },
        async hienThiTTDanhBaKH(){
            this.loading(true)
            try{
                await this.layDanhBaTheoMaTb()
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async layDanhBaTheoMaTb(){
            this.loading(true)
            try{
                await API.lay_danhba_theo_matb(this.axios, {
                    "in_ma_tb": this.ttKhachHang.maTb,
                    "in_dichvuvt_id": this.ttKhachHang.dichVuVtId,
                    "in_donvi_dl_id": 0
                }).then(async response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        const ttKhachHang = response.data.data[0]
                        if (ttKhachHang.trangthai_tb_id == 7 || ttKhachHang.trangthai_tb_id == 8 || ttKhachHang.trangthai_tb_id == 9){
                            this.$toast.error('Không thể mua thêm thiết bị cho thuê bao đang dừng hoạt động!')
                            return;
                        }

                        this.ttKhachHang.khachHangId = ttKhachHang.khachhang_id
                        this.ttKhachHang.maHd = ttKhachHang.ma_hd
                        this.ttKhachHang.maKh = ttKhachHang.ma_kh
                        this.ttKhachHang.tenKh = ttKhachHang.ten_kh
                        this.ttKhachHang.thueBaoId = ttKhachHang.thuebao_id
                        this.ttKhachHang.maTb = ttKhachHang.ma_tb
                        this.ttKhachHang.tenTb = ttKhachHang.ten_tb
                        this.ttKhachHang.diaChiTb = ttKhachHang.diachi_tb
                        this.ttKhachHang.diaChiLd = ttKhachHang.diachi_ld
                        this.ttKhachHang.trangThaiTb = ttKhachHang.trangthai_tb
                        this.ttKhachHang.dichVuVtId = ttKhachHang.dichvuvt_id
                        this.ttKhachHang.loaiTbId = ttKhachHang.loaitb_id

                        this.ttKhachHang.donViId = ttKhachHang.donvi_id
                        this.ttKhachHang.ghiChu = ttKhachHang.ghichu
                        this.ttKhachHang.doiTuongId = ttKhachHang.doituong_id

                        var promises = []
                        promises.push(this.layDiaChiTheoThueBaoId(ttKhachHang.thuebao_id))
                        promises.push(this.layTTDanhBaCNTT())
                        promises.push(this.hienThiQuyTrinhDonVi(ttKhachHang.donvi_id))
                        promises.push(this.layCTTienKM())
                        // promises.push(this.setDonVi(ttKhachHang.thuebao_id))
                        await Promise.all(promises)
                        this.setDonVi(ttKhachHang.thuebao_id)
                    }
                    else {
                        this.$toast.error('Nhập lại mã thuê bao tìm kiếm !')
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async layDiaChiTheoThueBaoId(thuebao_id){
            this.loading(true)
            try{
                await API.lay_ds_diachi_theo_dbtbid(this.axios, {
                    "thuebao_id": thuebao_id
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        this.ttKhachHang.ttDiaChi = response.data.data[0]
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async layTTDanhBaCNTT(){
            if ([13,14,15,16].includes(this.ttKhachHang.dichVuVtId)){
                this.loading(true)
                try{
                    await API.lay_ds_db_cntt_theo_tbid(this.axios, {
                        "in_thuebao_id": this.ttKhachHang.thueBaoId
                    }).then(response => {
                        this.loading(false)
                        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                            this.ttKhachHang.tocDoId = response.data.data[0].tocdo_id;
                            this.ttKhachHang.mucCuocId = response.data.data[0].muccuoc_id;
                        }
                    })
                } catch(e){
                    this.$toast.error('Có lỗi ' + e)
                    this.loading(false)
                }
            }
        },
        async hienThiQuyTrinhDonVi(donvi_id){
            this.loading(true)
            try{
                const res = await API.sp_ht_giaodien_donvi_theo_loaihd(this.axios, {
                    "dichvuvt_id": this.ttKhachHang.dichVuVtId,
                    "tocdo": this.ttKhachHang.tocDoId,
                    "muccuoc": this.ttKhachHang.mucCuocId,
                    "loaihinhtb_id": this.ttKhachHang.loaiTbId,
                    "kieuld_id": this.ttKhachHang.kieuLdId,
                    "loaidv_id": 23, //LOAI_DV.GIAODICH,
                    "loaihd_id": LoaiHopDong.BAN_THIETBI
                })
                var layoutDV = []
                var dtControl = []
                if (res && res.data && res.data.error_code === 'BSS-00000000')
                    dtControl = res.data.data
                else
                    dtControl = []
                    
                for(let i = 0; i < dtControl.length; i++){
                    let res1 = await API.sp_lay_ds_donvi_tramtc_banthietbi(this.axios, {
                        "donviql_id": parseInt(this.$root.token.getDonViID()),
                        "loaidv_id": dtControl[i].loaidv_dich_id
                    })
                    let dt = []
                    if (res1 && res1.data && res1.data.error_code === 'BSS-00000000'){
                        let dv = res1.data.data.filter(x=>x.donvi_id != 0)
                        dt = dv.map(x=>({id:x.donvi_id,text:x.ten_dv}))
                        if(dt.length <= 0){
                            let res2 = await API.sp_lay_ds_donvi_theo_loaidv_banthietbi(this.axios, {
                                "loaidv_id": dtControl[i].loaidv_dich_id
                            })
                            if (res2 && res2.data && res2.data.error_code === 'BSS-00000000'){
                                dt = res2.data.data.map(x=>({id:x.donvi_id,text:x.ten_dv}))
                            }
                            else{
                                this.$toast.error(res2.data.message)
                            }
                        }
                    }
                    else{
                        this.$toast.error(res2.data.message)
                    }

                    layoutDV.push({
                        name: "cbo" + dtControl[i].loaidv_dich_id + "." + dtControl[i].thutu_id,
                        label: dtControl[i].ten_loaidv,
                        loaidv_id: dtControl[i].loaidv_dich_id,
                        options: dt,
                        value: dt.length > 0 ? dt[0].id : 0
                    })
                }
                this.layoutDV = layoutDV
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
            finally{
                this.loading(false)
            }
        },
        async layCTTienKM(){
            this.loading(true)
            try{
                // await API.sp_lay_ds_tien_khoanmuc(this.axios, {
                //     "loaihd_id": LoaiHopDong.BAN_THIETBI
                await API.lay_khoanmuc_tt_theo_loaihd(this.axios, {
                    "vloaihd_id": LoaiHopDong.BAN_THIETBI
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        this.dsKhoanMuc.list = response.data.data
                    }
                    else{
                        this.dsKhoanMuc.list = []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.dsKhoanMuc.list = []
                this.loading(false)
            }
        },
        async setDonVi(thuebao_id){
            this.loading(true)
            try{
                await API.lay_tt_donvi_theo_thuebao(this.axios, {
                    "thuebao_id": thuebao_id
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data != null) {
                        let donVi = this.layoutDV.filter(x => x.loaidv_id == 5)
                        if(donVi.length > 0){
                            let objIndex = this.layoutDV.findIndex((obj => obj.name == donVi[0].name))
                            this.layoutDV[objIndex].value = response.data.data
                        }
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async hienThiThongTinHDKH(){
            this.loading(true)
            try{
                const coDl = await this.layDsHopDongTheoMaGd()
                if(coDl){
                    await this.layDsHopDongTheoHdkh()
                    await this.hienThiTTHopDongTB()
                    await this.layCTTienKM()
                    if (this.ttKhachHang.kieuLdId != 49){
                        await this.hienThiThongTinChiTiet()
                    }  
                }      
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async layDsHopDongTheoMaGd(){
            this.loading(true)
            try{
                return await API.lay_ds_hopdong_theo_ma_gd(this.axios, {
                    "ma_gd": this.ttKhachHang.maGd,
                    "loaihd_id": LoaiHopDong.BAN_THIETBI,
                    "donvi_id": this.$root.token.getDonViID(),
                    "tthd_id": TrangThaiHD.MOI,
                    "nhanvien_id": this.$root.token.getNhanVienID(),
                    "donvi_dl_id": 0
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        const ttHopDong = response.data.data[0]

                        this.ttKhachHang.hdkhId = ttHopDong.hdkh_id
                        this.ttKhachHang.maGd = ttHopDong.ma_gd
                        this.ttKhachHang.maHd = ttHopDong.ma_hd
                        this.ttKhachHang.maKh = ttHopDong.ma_kh
                        this.ttKhachHang.khachHangId = ttHopDong.khachhang_id
                        this.ttKhachHang.kieuLdId = ttHopDong.kieuld_id
                        this.ttKhachHang.tenKh = ttHopDong.ten_kh
                        // this.ttKhachHang.diaChiTb = ttHopDong.diachi_kh
                        this.ttKhachHang.ngayYc = ttHopDong.ngay_yc
                        this.ttKhachHang.ngayLapHd = ttHopDong.ngaylap_hd
                        this.ttKhachHang.doiTuongId = ttHopDong.doituong_id
                        return true
                    }
                    else{
                        this.$toast.error('Không tìm được hợp đồng !')
                        return false
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
                return false
            }
        },
        async layDsHopDongTheoHdkh(){
            this.loading(true)
            try{
                await API.lay_ds_hopdong_tb_theo_hdkh_id(this.axios, {
                    "in_hdkh_id": this.ttKhachHang.hdkhId
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        this.dsHopDong.list = response.data.data
                    }
                    else{
                        this.dsHopDong.list = []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async hienThiTTHopDongTB(){
            if(this.dsHopDong.list.length > 0){
                const tchd = this.dsHopDong.list[0]
                this.ttKhachHang.thueBaoId = tchd.thuebao_id
                this.ttKhachHang.hdtbId = tchd.hdtb_id
                this.ttKhachHang.tenTb = tchd.ten_tb
                this.ttKhachHang.maTb = tchd.ma_tb
                this.ttKhachHang.maGd = tchd.ma_gd
                this.ttKhachHang.diaChiLd = tchd.diachi_ld
                this.ttKhachHang.diaChiTb = tchd.diachi_tb
                this.ttKhachHang.ghiChu = tchd.ghichu
                this.ttKhachHang.dichVuVtId = tchd.dichvuvt_id
                this.ttKhachHang.loaiTbId = tchd.loaitb_id

                await this.loadDSTBIHDTB()
                //await this.htThietBi() 

                if (this.ttKhachHang.kieuLdId != 49){
                    this.setDonVi(tchd.thuebao_id)
                }
                this.isNhapMoi = false
            }
        },
        async loadDSTBIHDTB(){ //line 1019 c#
            this.loading(true)
            try{
                await API.lay_ds_mua_tbi(this.axios, {
                    "hdtb_id": this.ttKhachHang.hdtbId
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        const dulieu = response.data.data.map(({loaitbi_id, ...rest}) => ({...rest, loaitbi_id: +loaitbi_id})) //api tra loaitbi_id kiêu string phải doi lai int
                        this.dsThietBi.list = dulieu
                    }
                    else{
                        this.dsThietBi.list = []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        async htThietBi(){//line 1051 c# //TODO

        },
        async hienThiThongTinChiTiet(){
            this.loading(true)
            try{
                await API.lay_ds_gui_chitiet(this.axios, {
                    "p_hdkh_id": this.ttKhachHang.hdkhId,
                    "p_hdtb_id": this.ttKhachHang.hdtbId,
                    "p_loai": 1
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        this.dsThueBao.list = response.data.data
                    }
                    else{
                        this.dsThueBao.list = []
                    }
                })

                await API.lay_ds_gui_chitiet(this.axios, {
                    "p_hdkh_id": this.ttKhachHang.hdkhId,
                    "p_hdtb_id": this.ttKhachHang.hdtbId,
                    "p_loai": 2
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        this.dsThietBi.list = response.data.data
                    }
                    else{
                        this.dsThietBi.list = []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
                this.loading(false)
            }
        },
        xoaForm(){
            this.dsThueBao.list = []
            this.dsThietBi.list = []
            this.dsHopDong.list = []
            this.dsKhoanMuc.list = []
            this.layoutDV = []

            this.ttKhachHang = {
                khachHangId: 0,
                thueBaoId: 0,
                hdkhId: 0,
                hdtbId: 0,
                donViId: 0,
                maGd: "",
                ngayYc: "",
                maHd: "",
                maTb: "",
                diaChiTb: "",
                dichVuVtId: 0,
                ghiChu: "",
                maKh: "",
                tenKh: "",
                tenTb: "",
                diaChiLd: "",
                loaiTbId: 0,
                trangThaiTb: "",
                kieuLdId: 7001,
                doiTuongId: 0,
                tocDoId: 0,
                mucCuocId: 0,
                ngayLapHd: "",
                ttDiaChi: {}
            }

            this.isNhapMoi = true;

        },
        timKiemTheoMaTb(){
            this.hienThiTTDanhBaKH()
        },
        timKiemTheoMaGd(){
            this.hienThiThongTinHDKH()
        },
        showFrmTimKiemHopDong(){
            this.$refs.ModalNormalizeCAinformation.showModal()
        },
        timthongtinHD(data){
            this.ttKhachHang.maTb = data.ma_tb
        },
        showFrmTraCuuHopDong(){
            this.$refs.searchContractModal.showModal()
        },
        acceptSearchContract(item){
            this.ttKhachHang.maGd = item.ma_gd
            this.hienThiThongTinHDKH()
        },
        btnLayThietBi_Click(){
            this.$bvModal.show('popupBuyDevices')
        },
        popupBuyDevicesClose(data){
            this.$bvModal.hide('popupBuyDevices')
            this.dsThietBi.list = data
        },
        btnDinhKemChiTiet_Click(){
            this.$bvModal.show('popupCTThueBao')
        },
        popupCTThueBaoClose(data){
            this.$bvModal.hide('popupCTThueBao')
            this.dsThueBao.list = data
        },
        async txtTongTien_ButtonClick(){
            var ds = [];
            await API.post_lay_ds_tien_kmtt(this.axios, { "vkieuld_id": this.ttKhachHang.kieuLdId, "vloaihd_id": LoaiHopDong.BAN_THIETBI, "vloaitb_id": this.ttKhachHang.loaiTbId }).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    ds = response.data.data;
                    if(this.dsKhoanMuc && this.dsKhoanMuc.length>0)
                    for(var item of ds) {
                        var items = this.dsKhoanMuc.filter(x=>x.khoanmuctt_id==item.khoanmuctt_id);
                        if(items && items.length>0) {
                        item.tien = items[0].tien;
                        item.vat  = items[0].vat;
                        item.tongtien = item.tien+item.vat;
                        }
                    }
                }
            });

            this.popupTienKMDuLieu = {"ds":ds,cols:[
                {fieldName: 'tentat_kmtt', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey:true},
                {fieldName: 'tongtien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'tongtien'},  headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit:"tongtienParam"},
                {fieldName: 'tien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'cuoclapdat'},  headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit:"tienParam"},
                {fieldName: 'vat', textAlign:'right', format:'N', customAttributes:{class: 'vatlapdat'}, headerText: 'Vat', allowFiltering: false, allowSorting: false},
                {fieldName: 'tyle_vat', textAlign:'right', format:'N', headerText: 'Tỷ lệ vat', allowFiltering: false, allowSorting: false},
            ],keycol: "khoanmuctt_id",allowExit:false,tieude:"Chi tiết tiền khoản mục",tieudebang:"Chi tiết khoản mục thanh toán",allowExit:true}
        
            this.$bvModal.show('popupTienKM')
        },
        popupTienKMClose(data){
            this.$bvModal.hide('popupTienKM')
            this.dsKhoanMuc.list = data
        },
        nhapMoi(){
            this.xoaForm()
            this.isNhapMoi = true
        },
        async ghiLai(){
            try
            {
                if (Number(this.ttKhachHang.kieuLdId) == 49)
                {
                    let res_dt_luong = await this.axios.post('/web-hopdong/dichvu-ca/layhuonggiao_tiepnhan', {
                        vloaitb_id: this.ttKhachHang.loaiTbId,
                        vtocdo_id: this.ttKhachHang.tocDoId,
                        vmuccuoc_id: this.ttKhachHang.mucCuocId,
                        vkieuld_id: 49
                    });
                    let dt_luong = res_dt_luong.data.data;
                    let quytrinh_id = 0;
                    let huonggiao_id = 0;
                    if (dt_luong.length == 0)
                    {
                        this.$toast.error("Quy trình chưa được thiết lập, bạn vui lòng liên hệ Admin để được hỗ trợ!");
                        return;
                    }
                    quytrinh_id = Number(dt_luong[0]["QUYTRINH_ID"]);
                    huonggiao_id = Number(dt_luong[0]["HUONGGIAO_ID"]);
                    let dl_hdkh = this.TaoDuLieuHDKhachHang();
                    let dl_hdtb = this.TaoDuLieuHDThueBao(dl_hdkh.HDKH_ID, quytrinh_id);
                    let dl_tienHD = this.TaoDuLieu_CT_TIENHD(dl_hdtb.hdtb_id);
                    let dl_ct_tbi = this.TaoDuLieuCTThietBi(dl_hdtb.hdtb_id);
                    let dl_hdtb_dv = this.TaoDuLieu_HDTB_DV(dl_hdtb.hdtb_id);
                    
                    let res_capnhat = await this.axios.post('web-ccdv/ban-thiet-bi/capnhat_hd_mua_thietbi', {
                        vthuebao_id: this.ttKhachHang.thueBaoId,
                        vkhachhang_id: this.ttKhachHang.khachHangId,
                        vhdtb_id: dl_hdtb.hdtb_id,
                        vhuonggiao_id: huonggiao_id,
                        vds_hdkh: JSON.stringify(dl_hdkh),
                        vds_hdtb: JSON.stringify(dl_hdtb),
                        vds_cttt: JSON.stringify(dl_tienHD),
                        vds_ctmua_tbi: JSON.stringify(dl_ct_tbi),
                        vds_hdtb_dv: JSON.stringify(dl_hdtb_dv),
                        vnguoi_cn: this.$root.token.getNhanVienID(),
                        vmay_cn: this.$root.token.getUserName(),
                        vip_cn: this.$root.token.getMaNhanVien(),
                        vkieu: this.isNhapMoi ? 0 : 1
                    });
                    if (res_capnhat.data.data.toString() == "ok")
                    {
                        this.ttKhachHang.maGd = dl_hdkh.MA_GD;
                        this.HienThiThongTinHDKH();
                        this.$toast.success("Thêm thành công");
                    }
                    else
                    {
                        this.$toast.error(res_capnhat.data.data.toString());
                    }
                }
                else
                {
                    let res_dt_luong = await this.axios.post('/web-hopdong/dichvu-ca/layhuonggiao_tiepnhan', {
                        vloaitb_id: this.ttKhachHang.loaiTbId,
                        vtocdo_id: this.ttKhachHang.tocDoId,
                        vmuccuoc_id: this.ttKhachHang.mucCuocId,
                        vkieuld_id: this.ttKhachHang.kieuLdId
                    });
                    let dt_luong = res_dt_luong.data.data;
                    let quytrinh_id = 0;
                    let huonggiao_id = 0;
                    if (dt_luong.length == 0)
                    {
                        this.$toast.error("Quy trình chưa được thiết lập, bạn vui lòng liên hệ Admin để được hỗ trợ!");
                        return;
                    }
                    if (Number(this.ttKhachHang.kieuLdId) == 7001)
                    {
                        if (this.dsThueBao.list.length == 0)
                        {
                            this.$toast.error("Bạn chưa chọn danh sách thuê bao!");
                            return;
                        }
                    }
                
                    quytrinh_id = Number(dt_luong[0]["quytrinh_id"]);
                    huonggiao_id = Number(dt_luong[0]["huonggiao_id"]);
                    let dl_hdkh = this.TaoDuLieuHDKhachHang();
                    for (let dr in this.dsThueBao.list)
                    {
                        dr["hdtb_id"] = dl_hdkh.hdtb_id;
                    }
                    let dl_hdtb = this.TaoDuLieuHDThueBao(dl_hdkh.hdtb_id, quytrinh_id);
                    for (let dr in this.dsThietBi.list)
                    {
                        dr["hdtb_id"] = dl_hdtb.hdtb_id;
                    }
                    let dl_tienHD = this.TaoDuLieu_CT_TIENHD(dl_hdtb.hdtb_id);
                    this.dl_ct_tbi = this.TaoDuLieuCTThietBi(dl_hdtb.hdtb_id);
                    this. dl_hdtb_dv = this.TaoDuLieu_HDTB_DV_TTVT(dl_hdtb.hdtb_id);
                    if (Number(cboLoaiLyDo.EditValue) == 90)
                    {
                        if (!dl_ct_tbi || !dl_ct_tbi.length)
                        {
                            this.$toast.error("Bạn chưa chọn chủng loại thiết bị cần mua!");
                            return;
                        }
                        if (!dl_tienHD || !dl_tienHD.length)
                        {
                            this.$toast.error("Hợp đồng bán thiết bị phải có tiền hợp đồng!");
                            return;
                        }
                    }
                    let res_capnhat = await this.axios.post('/web-hopdong/dichvu-ca/capnhat_hd_mua_thietbi_ttvt', {
                        vthuebao_id: this.ttKhachHang.thueBaoId,
                        vkhachhang_id: this.ttKhachHang.khachHangId,
                        vhdtb_id: dl_hdtb.HDTB_ID,
                        vhuonggiao_id: huonggiao_id,
                        vds_hdkh: JSON.stringify(dl_hdkh),
                        vds_hdtb: JSON.stringify(dl_hdtb),
                        vds_cttt: JSON.stringify(dl_tienHD),
                        vds_ctmua_tbi: JSON.stringify(dl_ct_tbi),
                        vds_hdtb_dv: JSON.stringify(dl_hdtb_dv),
                        vds_thuebao: JSON.stringify(this.dsThueBao.list),
                        vds_vattu: JSON.stringify(this.dsThietBi.list),
                        vnguoi_cn: this.$root.token.getNhanVienID(),
                        vmay_cn: this.$root.token.getUserName(),
                        vip_cn: this.$root.token.getMaNhanVien(),
                        vkieu: this.isNhapMoi ? 0 : 1
                    });
                    if (res_capnhat.data.data.toString() == "ok")
                    {
                        this.ttKhachHang.maGd = dl_hdkh.MA_GD;
                        this.HienThiThongTinHDKH();
                        this.$toast.success("Thêm thành công");
                    }
                    else
                    {
                        this.$toast.error(res_capnhat.data.data.toString());
                    }
                }

            }
            catch (exp)
            {
                this.$toast.error("Thất bại: " + exp);
                console.log(exp);
            }
        },
        async TaoDuLieuHDKhachHang(){
            try {
                let vds_hdkh = {};
                if (this.isNhapMoi)
                {
                    let res_key = await this.axios.post('/web-hopdong/khoiphucthanhly/get_keys', {
                        keyname: "HD_KHACHHANG"
                    });
                    vds_hdkh.hdkh_id = res_key.data.data;
                    let res_magd = await this.axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', {
                        loaihd_id: 15
                    });
                    vds_hdkh.ma_gd = res_magd.data.data;
                }
                else
                {
                    vds_hdkh.hdkh_id = this.ttKhachHang.hdkhId;
                    vds_hdkh.ma_gd = this.ttKhachHang.maGd;
                }
                vds_hdkh.khachhang_id = this.ttKhachHang.khachHangId;
                vds_hdkh.loaihd_id = LoaiHopDong.BAN_THIETBI;
                
                vds_hdkh.ngay_yc = moment(new Date()).format("DD/MM/YYYY");
                vds_hdkh.ngaylap_hd = moment(new Date()).format("DD/MM/YYYY");
                vds_hdkh.nhanvien_id = this.$root.token.getNhanVienID();
                vds_hdkh.donvi_id = this.$root.token.getDonViID();
                return vds_hdkh;
            } catch (error) {
                console.log(error);
            }
        },
        async TaoDuLieuHDThueBao( in_hdkh_id, quytrinh_id){
            try
            {
                let vds_hdtb = {};
                if (this.isNhapMoi)
                {
                    let res_key = await this.axios.post('/web-hopdong/khoiphucthanhly/get_keys', {
                        keyname: "HD_THUEBAO"
                    });
                    vds_hdtb.HDTB_ID = res_key.data.data;
                }
                else
                {
                    vds_hdtb.HDTB_ID = this.ttKhachHang.hdtbId;
                }
                vds_hdtb.hdkh_id = in_hdkh_id;
                vds_hdtb.kieuld_id = Number(this.ttKhachHang.kieuldId);
                vds_hdtb.quytrinh_id = quytrinh_id;
                vds_hdtb.ten_tb = this.ttKhachHang.tenTb;
                vds_hdtb.thuebao_id = this.ttKhachHang.thueBaoId;
                vds_hdtb.tthd_id = TrangThaiHD.MOI;
                vds_hdtb.ghichu = this.ttKhachHang.ghiChu;

                return vds_hdtb;
            }
            catch (ex)
            {
                console.log(ex);
            }
        },
        async TaoDuLieu_CT_TIENHD(in_hdtb_id){
            try {
                if (this.dsKhoanMuc.list.length == 0){
                    this.$toast.error("Chưa có khoản mục thanh toán");
                    return;
                }
                // if (Object.keys(this.dsKhoanMuc.list[0]).length == 0)
                // {
                //     this.$toast.error("Chưa có khoản mục thanh toán tiền");
                //     return;
                // }

                let vds = [];
                let res_key = await this.axios.post('/web-hopdong/khoiphucthanhly/get_keys', {
                        keyname: "CT_TIENHD"
                });
                let key = res_key.data.data;
                
                for(const dr in this.dsKhoanMuc.list)
                {
                    let cttien = {};
                    cttien.hdtb_id = in_hdtb_id;
                    cttien.ct_tienhd_id = key;
                    cttien.ID = Number(dr["khoanmuctt_id"]);
                    cttien.KHOANMUCTT_ID = Number(dr["khoanmuctt_id"]);
                    cttien.tien = Number(this.currencyFormater(dr["tien"]));
                    cttien.vat = Number(this.currencyFormater(dr["vat"]));
                    cttien.loai_id = 1;
                    vds.push(cttien);
                }
                return vds;
            } catch (error) {
                console.log(error);
            }
        },
        async TaoDuLieuCTThietBi(in_hdtb_id){
            try {
                let vds_ctmua_tbi = [];
                if (this.dsThietBi.list.length > 0)
                {
                    vds_ctmua_tbi = [...this.dsThietBi.list];
                }
                for (const item in vds_ctmua_tbi)
                {
                    item.hdtb_id = in_hdtb_id;
                }
                return vds_ctmua_tbi;
            } catch (error) {
                console.log(error);
            }
        },
        async TaoDuLieu_HDTB_DV(in_hdtb_id){
            try {
                let ds = [];
                for (let i = 0; i < this.layoutDV.length; i++)
                {
                    let t = {};
                    t.donvi_id = Number(this.layoutDV[i].value);
                    t.hdtb_id = in_hdtb_id;
                    t.loaidv_id = Number(this.layoutDV[i].loaidv_id);
                    t.kieudv_id = 2;
                    ds.push(t);
                }
                return ds;
            } catch (error) {
                console.log(error);
            }
        },
        huyBo(){
            this.xoaForm()
        },
        xoaHd(){
            this.XoaHD()
        },
        thanhToan(){
            if(this.ttKhachHang.maGd == "")
                return
                
            this.$router.push({ name: 'Payment', params: { "magd": this.ttKhachHang.maGd} } )
        },
        async XoaHD(){
            try {
                if (this.isNhapMoi)
                {
                    this.$toast.error("Không đúng trạng thái không thể xoá");
                    return;
                }
                
                this.$confirm('Bạn có muốn xóa hợp đồng này không?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: "Không"
                }).then(async () => {
                    // kiem tra IP_CN UR2.6.042_010_007
                    let res = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', {
                        param: this.ttKhachHang.hdtbId,
                        type: 2
                    });
                    if(res.data.data){
                        this.axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2', {
                            hdkh_id: this.ttKhachHang.hdkhId, 
                            loaihd_id: LoaiHopDong.BAN_THIETBI
                        });
                        this.$toast.success("Xóa HD thành công!");
                        this.xoaForm();
                    }else{
                        this.$toast.error("Xóa HD không thành công!");
                    }
                }).catch(() => {
                    return;
                })
            } catch (error) {
                console.log(error);
            }
        },
        cboLoaiLyDo_EditValueChanged(){
            if (this.ttKhachHang.kieuLdId == 7001)
            {
                //     layoutControlGroup5.Enabled = true;
                // // layoutControlGroup9.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
                // //  layoutControlGroup2.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
                // // tabbedControlGroup1.SelectedTabPageIndex = 1;
                //     layoutControlGroup9.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
                //     layoutControlGroup2.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
                // // layoutControlGroup5.Enabled = false;
                //     tabbedControlGroup1.SelectedTabPageIndex = 0;
            }
            else
            {
                // layoutControlGroup9.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
                // layoutControlGroup2.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
                // layoutControlGroup5.Enabled = false;
                // tabbedControlGroup1.SelectedTabPageIndex = 0;
            }
            if (this.ttKhachHang.donViId != 0)
            {
                this.hienThiQuyTrinhDonVi(this.ttKhachHang.donViId)
            }
            else
            {
                this.hienThiQuyTrinhDonVi(0)
            }
        }
    }
}
</script>