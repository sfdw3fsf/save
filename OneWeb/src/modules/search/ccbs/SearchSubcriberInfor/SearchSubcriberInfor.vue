
<template src='./SearchSubcriberInfor.html'></template>

<style scoped src='./SearchSubcriberInfor.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import state from "./define.js";
import { currency } from "@/filters/currency";
import { TRANGTHAI_DONGBO } from "@/constants.js"
import DataGridCustom from "./components/DataGrid";
import frmDS_CapNhatDD from './popup/frmDS_CapNhatDD/index.vue'
//import ModalChonDiaChi from '@/modules/CABMAN/BanDoTuyenCot/ChonDiaChi/Modal'
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi";
import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'
import moment from 'moment'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {
        VueElement,
        DataGridCustom,
        frmDS_CapNhatDD,
        ModalChonDiaChi,
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...state,
            header: {
                title: "Tra cứu thông tin thuê bao",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Lắp đặt mới",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            Fn: {
                NhapMoi: false,
                GhiLai: false,
                HuyBo: false,
                Xoa: false,
            },
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            phanvung_id: this.$auth.getPhanVungID(),
            dataSelected: {
              
            },
            options: {
             
            },
        };
    },
    mounted() {
        this.init();
    },
    computed: {

    },
    validations: {

    },
    methods: {
        async init() {
            try {
              // Lay gia trị tag truyen tu url   
                if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
                this.Tag = this.$route.query.tag
                }
                if (this.Tag != null && this.Tag.length > 0) {
                    if (parseInt(this.Tag) == 999)
                    {
                        this.is_update = true
                        this.createdata()
                    }
                }
              this.loadYear()
              this.getUserNeo()
              
            } catch (e) {
                console.log(e);
            }
        },
        async TimThongTinTB(){
            this.layDichvuDangSuDung()
            this.layLichSuTB()
        },
        async TraCuu(){
            let search_term = this.formTT.ma_tb
            this.loading(true)
            this.Clear()
            this.formTT.ma_tb = search_term
            try {
                this.dtLichSuTb=null
                await api.ts_tracuu_laytt_tb(this.axios,
                {
                "so_tb": '84'+search_term
                }).then(response => {
                    console.log(response)
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        if (this.countKeys(response.data.data[0]) == 1)
                        {
                            this.$toast.error("Không tìm thấy thông tin yêu cầu !");
                            this.loading(false)
                            this.Clear()
                            return;
                        }
                        if (response.data.data[0].LOAI_TB == "GPhone")
                        {
                            this.$toast.error("Chức năng chỉ tra cứu với thuê bao di động !");
                            this.loading(false)
                            this.Clear()
                            return
                        }
                        this.dtLichSuTb = response.data.data[0]
                        let formData = response.data.data[0];
                        for (let key in formData) {                            
                            this.$set(this.formTT, key.toLowerCase(), formData[key]);
                        }
                        
                        if (response.data.data[0].GOI_DI == "1")
                        {
                            this.formTT.goi_di = true
                        }
                        else
                        {
                            this.formTT.goi_di = false;
                        }
                        if (response.data.data[0].GOI_DEN == "1")
                        {
                            this.formTT.goi_den = true
                        }
                        else
                        {
                            this.formTT.goi_den = false;
                        }
                        this.ts_tracuu_laytt_tb_ts(formData.TRA_SAU)
                    }
                })

                await api.app_tracuu_ls_dichvu3g(this.axios,
                {
                "so_tb": '84'+search_term
                }).then(response => {
                    console.log(response)
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                    
                    }
                })

                await api.ts_tracuu_laytt_tb_dv(this.axios,
                {
                "so_tb": '84'+search_term
                }).then(response => {
                    console.log(response)
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        this.lvDichvu = response.data.data
                    }
                })
                //await this.ts_subscriber_info()
                
                this.loading(false)
            } catch (error) {
                console.log(error)   
                this.$toast.error("Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n" + error);                    
                this.loading(false) 
            }
        },
        async ts_tracuu_laytt_tb_ts(tra_sau){
            await api.ts_tracuu_laytt_tb_ts(this.axios,
                {
                "so_tb": '84'+this.formTT.ma_tb,                
                }).then(response => {
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        let dtLichSuKH = response.data.data[0]
                        console.log(tra_sau)
                        if (tra_sau == "1"){
                            if (dtLichSuKH.TEN_TB && dtLichSuKH.TEN_TB.trim() != "")
                            {
                                this.formTT.ngay_sinh = dtLichSuKH.NGAY_SINH
                                this.formTT.phai = dtLichSuKH.PHAI
                                this.formTT.doituong = dtLichSuKH.DOITUONG
                                this.formTT.so_gt = dtLichSuKH.SO_GT
                                this.formTT.ngaycap_gt = dtLichSuKH.NGAYCAP_GT
                                this.formTT.ten_tb = dtLichSuKH.TEN_TB
                                this.formTT.diachi_chungtu = dtLichSuKH.DIACHI_CHUNGTU
                                this.formTT.diachi = dtLichSuKH.DIACHI
                                this.formTT.diachi_thuongtru = dtLichSuKH.DIACHI_THUONGTRU                                
                            } 
                        }
                        else
                        {                            
                            
                            this.formTT.ngay_sinh = this.dtLichSuTb.NGAY_SINH
                            this.formTT.so_gt = this.dtLichSuTb.SO_GT
                            this.formTT.ngaycap_gt = this.dtLichSuTb.NGAY_CAP
                            this.formTT.ten_tb = this.dtLichSuTb.TEN_KH
                            this.formTT.diachi_thuongtru = this.dtLichSuTb.DIACHI_KH    


                            // HTH 29/05/2020
                            if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA)
                            {
                                this.enableTTDongBo = true;
                            }
                            if (this.is_update)
                            {
                                //TODO: làm sau cùng, vì trạng thái đang khóa
                                this.isShowDCLD = true
                                this.lay_map_thamso_thuebao_id(this.formTT.ma_tb.trim())
                            }

                        }
                    }
                })
        },
        async layLichSuTB(){
            try {
                await api.tracuu_ls_tb(this.axios,
                {
                    "so_tb": '84'+this.formTT.ma_tb, 
                    "page_num": "1",                    
                    "page_rec": "5000"               
                }).then(response => {                    
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        this.gvLichSuTB.tableData = response.data.data
                    }
                })
            }
            catch(ex){
                console.log(ex)
            }
        },
        async layBienDongKH(){
            try {
                await api.ts_tracuu_ls_khbd(this.axios,
                {
                    "so_tb": '84'+this.formTT.ma_tb,                                    
                }).then(response => {                    
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        this.gvBienDongKH.tableData = response.data.data
                        for(var i in this.gvBienDongKH.tableData) this.gvBienDongKH.tableData[i].STT=(parseInt(i)+1).toString();
                    }
                })
            }
            catch(ex){
                console.log(ex)
            }
        },
        async layLichSu3G(){
            try {
                this.gvLichSu3G.tableData=[]
                await api.ts_tracuu_laytt_ls_3g(this.axios,
                {
                    "so_tb": '84'+this.formTT.ma_tb,                                    
                }).then(response => {                    
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        this.gvLichSu3G.tableData = response.data.data
                        for(var i in this.gvLichSu3G.tableData) this.gvLichSu3G.tableData[i].STT=(parseInt(i)+1).toString();
                    }
                })
            }
            catch(ex){
                console.log(ex)
            }
        },
        async layGoi3GTLai(){

        },
        async xemCuoc(){            
            
            try {
                let nam = this.formCuocNong.nam.substring(2)
                this.lvCuocNong={}
                if (this.formTT.ma_tb==""){
                    this.$toast.error("Không được để trống mã thuê bao !");
                    
                    return
                }
                              
                if (this.userNeo =="")
                {
                    this.$toast.error("Người dùng chưa được cấp quyền truy cập dữ liệu trên Vinaphone. Liên hệ admin !");
                    return
                }
                
                this.loading(true)
                await api.ts_tracuu_cuocnong(this.axios,
                {
                "so_tb": '84'+this.formTT.ma_tb, 
                thang:this.formCuocNong.thang,
                nam:nam
                }).then(response => {
                    this.loading(false)
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){                        
                        if (this.countKeys(response.data.data[0])!=1){
                            this.lvCuocNong = response.data.data[0]
                        }
                        else {
                            this.$toast.error("Số thuê bao 0" + this.formTT.ma_tb + " không có dữ liệu tra cứu cước nóng");
                            return
                        }

                    }
                    else
                    {
                        this.$toast.error("Không có dữ liệu cước trong khoảng thời gian tra cứu !");
                        return
                    }
                    
                    
                })
            }
            catch(ex){
                console.log(ex)
                this.loading(false)
            }
        },
        async getUserNeo() {
            try {
                this.loading(true);
                this.userNeo = '';
                let response = await api.getUserNeo(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {  
                    this.userNeo = response.data.data[0].USER_NEO;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },
        async layDichvuDangSuDung(){

        },
        async HienThiTTDiaChi(){
            try {
                await api.lay_ds_diachi_theo_dbtbid(this.axios,this.thuebao_id
                ).then(response => {                    
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        let item = response.data.data[0]
                        console.log('hien thi dia chi')
                        console.log(item)
                        this.diachi_ld.diachi = item.diachi_ld
                        this.diachi_ld.diachi_id = item.diachi_id
                        this.diachi_ld.diachitb_id = item.diachi_id
                        this.diachi_ld.diachild_id = item.diachild_id
                        if (item.tinhld_id && item.tinhld_id!="")
                            this.diachi_ld.tinh_id = item.tinhld_id
                        else 
                            this.diachi_ld.tinh_id = 0

                        if (item.quanld_id && item.quanld_id!="")
                            this.diachi_ld.quan_id = item.quanld_id
                        else 
                            this.diachi_ld.quan_id = 0

                        if (item.phuongld_id && item.phuongld_id!="")
                            this.diachi_ld.phuong_id = item.phuongld_id
                        else 
                            this.diachi_ld.phuong_id = 0

                        if (item.phold_id && item.phold_id!="")
                            this.diachi_ld.pho_id = item.phold_id
                        else 
                            this.diachi_ld.pho_id = 0
                        
                        if (item.apld_id && item.apld_id!="")
                            this.diachi_ld.ap_id = item.apld_id
                        else 
                            this.diachi_ld.ap_id = 0
                        
                        if (item.khuld_id && item.khuld_id!="")
                            this.diachi_ld.khu_id = item.khuld_id
                        else 
                            this.diachi_ld.khu_id = 0
                        
                        if (item.dacdiemld_id && item.dacdiemld_id!="")
                            this.diachi_ld.dacdiem_id = item.dacdiemld_id
                        else 
                            this.diachi_ld.dacdiem_id = 0
                        
                        if (item.kinhdo_ld && item.kinhdo_ld!="")
                            this.diachi_ld.kinhdo = item.kinhdo_ld
                        else 
                            this.diachi_ld.kinhdo = 0
                        
                        
                        if (item.vido_ld && item.vido_ld!="")
                            this.diachi_ld.vido = item.vido_ld
                        else 
                            this.diachi_ld.vido = 0
                        
                        if (item.block_ld && item.block_ld!="")
                            this.diachi_ld.block = item.block_ld
                        if (item.tang_ld && item.tang_ld!="")
                            this.diachi_ld.tang = item.tang_ld
                        if (item.phong_ld && item.phong_ld!="")
                            this.diachi_ld.phong = item.phong_ld
                        if (item.mota_ld && item.mota_ld!="")
                            this.diachi_ld.mota = item.mota_ld
                        if (item.sonha_ld && item.sonha_ld!="")
                            this.diachi_ld.so_nha = this.ChuanHoaTen(item.sonha_ld)
                        
                        this.diachi_ld.lat = this.diachi_ld.vido
                        this.diachi_ld.lng = this.diachi_ld.kinhdo
                    }
                })
            }
            catch(ex){
                console.log(ex)
            }
        },
        async lay_map_thamso_thuebao_id(ma_tb) { 
            console.log('lay_map_thamso_thuebao_id')
            try {                     
                let data = {"type": 17,"param": '84'+ma_tb };       
                await api.fn_tt_db_thuebao(this.axios, data).then((res) => {        
                if (res.data.data ) {                     
                    //todo: đang gán tạm tb id để lấy địa chỉ
                    
                    this.thuebao_id = res.data.data
                    //this.thuebao_id='1587282'
                    console.log(this.thuebao_id)
                    if (this.thuebao_id > 0){
                        this.HienThiTTDiaChi(this.thuebao_id)
                    }
                    else {
                        this.diachi_ld.diachi=""
                        this.thuebao_id = 0
                    }      
                }
                });
            } catch (error) {
                console.log(error)
            } 
        }, 
        async lay_ls_capnhat_tratruoc(thuebao_id) {
            if (!thuebao_id) return
            try {
                const rs = await this.$root.context.get(
                `web-tracuu/tracuu-tt-thuebao/lay_ls_capnhat_tratruoc?p_thuebao_id=${thuebao_id}`
                );
                  return rs.data;                
            } catch (error) {
                return []
            }
        },
        async thongbao_capnhat_tratruoc(thuebao_id) {
            if (!thuebao_id) return
            try {
                const rs = await this.$root.context.get(
                `web-tracuu/tracuu-tt-thuebao/thongbao_capnhat_tratruoc?p_thuebao_id=${thuebao_id}`
                );
                  return rs.data;                
            } catch (error) {
                return null
            }
        },
        async capnhat_tt_tratruoc(data) { 
            console.log('capnhat_tt_tratruoc')
            try {   
                await api.capnhat_tt_tratruoc(this.axios, data).then((res) => {        
                if (res.data && res.data.data ) {                     
                    console.log( res.data.data )
                    if (res.data.data == "OK")
                    {
                        if (this.isDongbo) {
                            //todo
                            //nếu là nhấn nút đồng bộ thì thông báo khác
                            this.$toast.success("Đồng bộ dữ liệu thành công!"); 
                            this.dtLichSuTb = null
                            this.enableTTDongBo = false
                            this.isDongbo = false
                        }
                        else {
                            this.$toast.success("Cập nhật địa chỉ thành công!"); 
                        }
                        this.diachi_ld.diachi = data.vdiachild;
                    }
                    else
                    {
                        this.$toast.error("Lỗi cập nhật địa chỉ: " + res.data.data);
                    }           
                }
                else {
                    this.$toast.error('Lỗi cập nhật địa chỉ!');
                }
                });
            } catch (error) {
                console.log(error) 
                
            } 
        }, 
        async a_vnp_dongbo_ddtt(data) { 
            console.log('a_vnp_dongbo_ddtt')
            try {   
                await api.a_vnp_dongbo_ddtt(this.axios, data).then((res) => {        
                    if (res.data && res.data.data ) {
                        if (res.data.data != "OK") {
                            this.$toast.error("Đồng bộ dữ liệu không thành công, chi tiết: " + res.data.data);                    
                            return;
                        }
                        else {
                            if (this.is_update) {
                                let data_tb = {"type": 17,"param": '84' + this.formTT.ma_tb };      
                                api.fn_tt_db_thuebao(this.axios, data_tb).then((res) => {        
                                    if (res.data.data ) {                     
                                        
                                        this.thuebao_id = res.data.data
                                        //this.thuebao_id='1587282'                                        
                                        if (this.thuebao_id > 0){
                                            this.HienThiTTDiaChi(this.thuebao_id)
                                            this.diachi_ld.lng = this.diachi_ld.kinhdo
                                            this.diachi_ld.lat = this.diachi_ld.vido
                                            this.isDongbo = true
                                            this.$bvModal.show('popupDiaChi_LD')
                                        }   
                                        else {
                                            this.isDongbo = false
                                        }        
                                    }
                                });
                                
                            }
                            else {                                
                                this.$toast.success("Đồng bộ dữ liệu thành công!"); 
                                this.dtLichSuTb = null
                                this.enableTTDongBo = false
                                this.isDongbo = false
                            }

                        }       
                    }
                
                });
            } catch (error) {
                console.log(error) 
                
            } 
        }, 
        async ts_subscriber_info() { 
            console.log('ts_subscriber_info')
            try {     
                let data = {"so_tb": '84'+this.formTT.ma_tb }
                await api.ts_subscriber_info(this.axios, data).then((res) => {    
                    console.log(res.data.data)    
                    if (res.data && res.data.data && res.data.data.length>0) {                     
                        let item = res.data.data[0]
                        console.log(item)
                        //if ((!item.MSISDN || item.MSISDN =='' ) && (!item.ID || item.ID =='' )) {
                            //todo: chưa thử với thuê bao có thông tin thật
                            let data_dongbo = {
                                "vid": item.ID,
                                "vmsisdn": item.MSISDN,
                                "vfullname": item.FULLNAME,
                                "vidnumber": item.IDNUMBER,
                                "vpassport": item.PASSPORT,
                                "vnationalityid": item.NATIONALITYID,
                                "vbirthday": item.BIRTHDAY,
                                "vgender": item.GENDER,
                                "vcompany": item.COMPANY,
                                "vjob": item.JOB,
                                "vemail": item.EMAIL,
                                "vagentid": item.AGENTID,
                                "vregisterdate": item.REGISTERDATE,
                                "vactivationtime": item.ACTIVATIONTIME,
                                "vregistermethodid": item.REGISTERMETHODID,
                                "vaddress": item.ADDRESS,
                                "vagentidfirst": item.AGENTIDFIRST,
                                "vmodifydate": item.MODIFYDATE,
                                "vnationality": item.NATIONALITY,
                                "vidnumbertype": item.IDNUMBERTYPE,
                                "vplaceofissue": item.PLACEOFISSUE,
                                "vcustomertype": item.CUSTOMERTYPE,
                                "vnote": item.NOTE,
                                "vplacedate": item.PLACEDATE,
                                "vaddress_company": item.ADDRESS_COMPANY,
                                "vso_giay_gt": item.SO_GIAY_GT,
                                "vngaycap_ggt": item.NGAYCAP_GGT,
                                "vchuc_vu": item.CHUC_VU,
                                "vngay_ky": item.NGAY_KY,
                                "vnguoi_ky": item.NGUOI_KY,
                                "vhuy": item.HUY,
                                "vvisa": item.VISA,
                                "vngayhh_visa": item.NGAYHH_VISA,
                                "vso_giay_kd": item.SO_GIAY_KD,
                                "vngay_cap_kd": item.NGAY_CAP_KD,
                                "vnoi_cap_kd": item.NOI_CAP_KD,
                                "vstatus": item.STATUS,
                                "vid_ws": item.ID_WS,
                                "vcustomer_use_id": item.CUSTOMER_USE_ID,
                                "vtransaction_location": item.TRANSACTION_LOCATION,
                                "vtransaction_phone": item.TRANSACTION_PHONE,
                                "vcontract_type": item.CONTRACT_TYPE,
                                "vcustomer_id": item.CUSTOMER_ID,
                                "vrepresent_id": item.REPRESENT_ID,
                                "vdienthoai_lh": item.DIENTHOAI_LH,
                                "vuser": this.userNeo,
                                "vmay_cn": this.$root.token.getNguoiDungID(),
                                "vgoi_di": item.GOI_DI,
                                "vgoi_den": item.GOI_DEN,
                                "vloai": item.LOAI,
                            } 
                            this.a_vnp_dongbo_ddtt(data_dongbo) 
                        //}
            
                    }
                });
            } catch (error) {
                console.log(error) 
                //return 0
            } 
        },
        onMaTBEnter(){
            this.TraCuu()
            //this.enableTTDongBo=true
        },
        tabClick(action){
            switch (action) {
                case 'lichsutb':
                    console.log('lichsutb')
                    this.layLichSuTB()
                    break;
                case 'biendongkh':
                    console.log('biendongkh')
                    this.layBienDongKH()
                    break;
                case 'lichsu3g':
                    console.log('lichsu3g')
                    this.layLichSu3G()
                    break;
                case 'goi3gtlai':
                    console.log('goi3gtlai')
                    this.layGoi3GTLai()
                    break;
            }
        },
        async txtDiaChiLD_ButtonClick(){
            console.log('isShowLSTB')
            console.log(this.thuebao_id)
            if (this.thuebao_id>0){
                this.dsLS = await this.lay_ls_capnhat_tratruoc(this.thuebao_id)
                if (this.dsLS && this.dsLS.length>0) {
                    this.hienThiLSTB()
                }
                else {
                    console.log(this.diachi_ld)
                    this.diachi_ld.lng = this.diachi_ld.kinhdo
                    this.diachi_ld.lat = this.diachi_ld.vido
                    this.$bvModal.show('popupDiaChi_LD')
                }
            }
        },
        async tsbtnDongBoHD_Click(){
            try {
                
                // if (!window.confirm("Bạn thật sự muốn đồng bộ thông tin thuê bao " + this.formTT.ma_tb + " từ CCBS về hệ thống ĐHSXKD?")) {
                //     return
                // }
                this.$bvModal.msgBoxConfirm("Bạn thật sự muốn đồng bộ thông tin thuê bao " + this.formTT.ma_tb + " từ CCBS về hệ thống ĐHSXKD?", {
                	title: '',
                	size: 'sm',
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Hủy',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}	
                    if (this.dtLichSuTb == null || this.countKeys(this.dtLichSuTb)==0)
                    {
                        this.$toast.error("Không có thông tin đồng bộ!");                    
                        return;
                    }
                    if (this.countKeys(this.dtLichSuTb)== 1)
                    {
                        this.$toast.error("Không tìm thấy thông tin yêu cầu !");                        
                        return;
                    }
                    if (this.dtLichSuTb.TRA_SAU == "1")
                    {
                        this.$toast.error("Chỉ thực hiện đồng bộ với thuê bao di động trả trước!");       
                        return;
                    }
                    if ("84" + this.formTT.ma_tb.trim() != this.dtLichSuTb.SO_TB)
                    {                        
                        this.$toast.error("Hãy thực hiện lại tìm kiếm thuê bao trước khi đồng bộ!");   
                        return;
                    }
                    else
                    {
                        await this.ts_subscriber_info()
                    }	
            	});

                
            }
            catch(ex){
                this.$toast.error("Có lỗi xảy ra trong quá trình đồng bộ dữ liệu về ĐHSXKD!" + ex);
            }
        },
        async btnTracuuTK_Click(){
            this.loading(true)
            this.formTT.tk_chinh=''
            this.formTT.tk_km=''
            this.formTT.tk_km1=''
            this.formTT.tk_km2=''
            try
            {
                await api.tracuu_taikhoan(this.axios,
                {
                "so_tb": '84'+this.formTT.ma_tb.trim()
                }).then(response => {
                    this.loading(false)
                    console.log(response)
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        let tk =  response.data.data.find((item) =>item.ID == "1");
                        if (tk) this.formTT.tk_chinh = currency(tk.VOLUME,'',0)
                        else this.formTT.tk_chinh = 0

                        tk =  response.data.data.find((item) =>item.ID == "2");
                        if (tk) this.formTT.tk_km = currency(tk.VOLUME,'',0)
                        else this.formTT.tk_km = 0

                        tk =  response.data.data.find((item) =>item.ID == "7");
                        if (tk) this.formTT.tk_km1 = currency(tk.VOLUME,'',0)
                        else this.formTT.tk_km1 = 0

                        tk =  response.data.data.find((item) =>item.ID == "162");
                        if (tk) this.formTT.tk_km2 = currency(tk.VOLUME,'',0)
                        else this.formTT.tk_km2 = 0
                    }
                })               
            }
            catch (ex)
            {
                this.loading(false)   
                this.formTT.tk_chinh=''
                this.formTT.tk_km=''
                this.formTT.tk_km1=''
                this.formTT.tk_km2=''
                this.$toast.error("Lỗi khi tra cứu thông tin tài khoản."+ex);
                
            }
        },
        async MAP_ID(field_name,table_name,condition) {             
            try {     
                let data = {"in_dk": condition,"in_id_neo": field_name,"in_table": table_name
                };      
                await api.lay_map_thamso(this.axios, data).then((res) => {        
                if (res.data.data ) {                    
                    return res.data.data            
                }
                else {
                    return ""
                }
                });
            } catch (error) {
                console.log(error) 
                return ""
            }
        }, 
        async getDiaChi_ld(data) {
            
            let thongbao = await this.thongbao_capnhat_tratruoc(this.thuebao_id)
            if (!thongbao) return
            if (!window.confirm(thongbao)) {
                return
            }
            console.log('getDiaChi_ld')
            console.log(this.diachi_ld)
            let dt_log=[]            
            // tinh
            let tentinh_cu = ''            
            let datax = {"type": 7, "param": this.diachi_ld.tinh_id};
            await api.fn_tt_tinh(this.axios, datax).then((res) => {        
                if (res.data.data ) { tentinh_cu= res.data.data }
                else { tentinh_cu = "" }
             });
            
            let tentinh_moi = ''            
            datax = {"type": 7, "param": data.tinh_id};       
            await api.fn_tt_tinh(this.axios, datax).then((res) => {        
                if (res.data.data ) { tentinh_moi= res.data.data }
                else { tentinh_moi = "" }
             });
            
            let dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "TINH_ID",
                giatri_moi : !data.tinh_id?0:data.tinh_id,
                giatri_cu : this.diachi_ld.tinh_id,
                text_moi : tentinh_moi,
                text_cu : tentinh_cu,
            }
            dt_log.push(dr)
            // quan
            let tenquan_cu = ''
            
            datax = {"type": 3, "param": this.diachi_ld.quan_id};      
            await api.fn_tt_quan(this.axios, datax).then((res) => {        
                if (res.data.data ) { tenquan_cu= res.data.data }
                else { tenquan_cu = "" }
             });
            let tenquan_moi = ''              
            datax = {"type": 3, "param": data.quan_id};      
            await api.fn_tt_quan(this.axios, datax).then((res) => {        
                if (res.data.data ) { tenquan_moi= res.data.data }
                else { tenquan_moi = "" }
             });

            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "QUAN_ID",
                giatri_moi : !data.quan_id?0:data.quan_id,
                giatri_cu : this.diachi_ld.quan_id,
                text_moi : tenquan_moi,
                text_cu : tenquan_cu,
            }
            dt_log.push(dr)

            // phuong 
            let tenphuong_cu = ''
            let tenphuong_moi = ''
            
            datax = {"type": 2, "param": this.diachi_ld.phuong_id, "param1": this.phanvung_id};   
            await api.fn_tt_phuong(this.axios, datax).then((res) => {        
                if (res.data.data ) { tenphuong_cu= res.data.data }
                else { tenphuong_cu = "" }
             });
            
            datax = {"type": 2, "param": data.phuong_id, "param1": this.phanvung_id};   
            await api.fn_tt_phuong(this.axios, datax).then((res) => {        
                if (res.data.data ) { tenphuong_moi= res.data.data }
                else { tenphuong_moi = "" }
             });

            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "PHUONG_ID",
                giatri_moi : !data.phuong_id?0:data.phuong_id,
                giatri_cu : this.diachi_ld.phuong_id,
                text_moi : tenphuong_moi,
                text_cu : tenphuong_cu,
            }
            dt_log.push(dr)

            // pho 
            let tenpho_cu = ''
            let tenpho_moi = ''
            
            datax = {"type": 2, "param": this.diachi_ld.pho_id, "param1": this.phanvung_id};      
            await api.fn_tt_pho(this.axios, datax).then((res) => {        
                if (res.data.data ) { tenpho_cu= res.data.data }
                else { tenpho_cu = "" }
             });            
            datax = {"type": 2, "param": data.pho_id, "param1": this.phanvung_id};      
            await api.fn_tt_pho(this.axios, datax).then((res) => {        
                if (res.data.data ) { tenpho_moi= res.data.data }
                else { tenpho_moi = "" }
             });            
            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "PHO_ID",
                giatri_moi : !data.pho_id?0:data.pho_id,
                giatri_cu : this.diachi_ld.pho_id,
                text_moi : tenpho_moi,
                text_cu : tenpho_cu,
            }
            dt_log.push(dr)

            //dac diem
            let dacdiem_cu = ''
            if (this.diachi_ld.dacdiem_id) {                                
                datax = {"type": 1, "param": this.diachi_ld.dacdiem_id};
                await api.fn_tt_dacdiem(this.axios, datax).then((res) => {        
                    if (res.data.data ) { dacdiem_cu= res.data.data }
                    else { dacdiem_cu = "" }
                });
            }
            let dacdiem_moi = ''
            if (data.dac_diem_id) {                                
                datax = {"type": 1, "param": data.dac_diem_id};
                await api.fn_tt_dacdiem(this.axios, datax).then((res) => {        
                    if (res.data.data ) { dacdiem_moi= res.data.data }
                    else { dacdiem_moi = "" }
                }); 
            }
            
            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "DACDIEM_ID",
                giatri_moi : !data.dac_diem_id?0:data.dac_diem_id,
                giatri_cu : this.diachi_ld.dacdiem_id,
                text_moi : !dacdiem_moi?'':dacdiem_moi,
                text_cu :!dacdiem_cu?'':dacdiem_cu,
            }
            dt_log.push(dr)
            //dia chi
            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "DIACHI",
                giatri_moi : !data.diachimoi?'':data.diachimoi,
                giatri_cu : !this.diachi_ld.diachi?'':this.diachi_ld.diachi,
                text_moi : !data.diachimoi?'':data.diachimoi,
                text_cu : !this.diachi_ld.diachi?'':this.diachi_ld.diachi,
            }
            dt_log.push(dr)

            //so nha
            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "SONHA",
                giatri_moi : !data.so_nha?'':data.so_nha,
                giatri_cu : !this.diachi_ld.so_nha?'':this.diachi_ld.so_nha,
                text_moi : !data.so_nha?'':data.so_nha,
                text_cu : !this.diachi_ld.so_nha?'':this.diachi_ld.so_nha,
            }
            dt_log.push(dr)

            //kinh do
            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "KINHDO",
                giatri_moi : !data.lng?'':data.lng,
                giatri_cu : !this.diachi_ld.kinhdo?'':this.diachi_ld.kinhdo,
                text_moi : !data.lng?'':data.lng,
                text_cu : !this.diachi_ld.kinhdo?'':this.diachi_ld.kinhdo,
            }
            dt_log.push(dr)

            //vi do
            dr = {
                schema_name : "css",
                table_name : "DIACHI",
                field_name : "VIDO",
                giatri_moi : !data.lat?'':data.lat,
                giatri_cu : !this.diachi_ld.vido?'':this.diachi_ld.vido,
                text_moi : !data.lat?'':data.lat,
                text_cu : !this.diachi_ld.vido?'':this.diachi_ld.vido,
            }
            dt_log.push(dr)

            let json_log = JSON.stringify(dt_log)
            console.log(json_log)       
            
            for (let key in data) {
                this.$set(this.diachi_ld, key, data[key])
            }

            let update_data = {
                "json_log": json_log,
                "vLat_LD": data.lat,
                "vLng_LD": data.lng,
                "vapld_id": data.ap_id,
                "vblockld": "",
                "vdacdiemld_id": !data.dac_diem_id?0:data.dac_diem_id,
                "vdiachi_id": !this.diachi_ld.diachi_id?0:!this.diachi_ld.diachi_id ,
                "vdiachild": !data.diachimoi?'':data.diachimoi,
                "vdiachild_id": this.diachi_ld.diachild_id,
                "vip_cn": "",
                "vkhuld_id": !data.khu_id?0:data.khu_id,
                "vmay_cn":"", 
                "vmotadcld": "",
                "vnguoi_cn": this.$root.token.getNguoiDungID(), //todo
                "vnhanvien_id": this.$root.token.getNhanVienID(), 
                "vphold_id": !data.pho_id?0:data.pho_id,
                "vphuongld_id":!data.phuong_id?0:data.phuong_id,
                "vquanld_id": !data.quan_id?0:data.quan_id,
                "vsonhald": !data.so_nha?'':data.so_nha,
                "vsophongld": "",
                "vtangld": "",
                "vthuebao_id": !this.thuebao_id?0:this.thuebao_id,
                "vtinhld_id": !data.tinh_id?0:data.tinh_id,
                }
            
            await this.capnhat_tt_tratruoc(update_data)
            
        },
        hienThiLSTB: function () {
            this.isShowLSTB = true;
        },
        closeModalLSTB: function () {
            this.isShowLSTB = false;
            //show model địa chỉ
            this.$bvModal.show('popupDiaChi_LD')
        },
        async loadYear(){
            let curYear = (new Date()).getFullYear()                     
            try {                
                await api.sp_lay_tt_nam(this.axios).then(response => {
                    console.log(response)
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        this.options.cbxNam = response.data.data
                    }                    
                })
            }
            catch(ex){
                console.log(ex)
                
            }
            this.formCuocNong.nam=curYear.toString()
        },
        loadMonth(year){            
            let vyear = parseInt(year)
            let curYear = (new Date()).getFullYear()
            let maxMonth=12
            console.log(vyear)
            console.log(curYear)
            if (vyear==curYear) maxMonth = (new Date()).getMonth()+1            
            this.options.cbxThang=[]
            
            for (let i=maxMonth;i>=1;i--){                
                this.options.cbxThang.push({thang:(i.toString().length==1?'0'+i.toString():i.toString())})
            }            
            this.formCuocNong.thang=this.options.cbxThang[0].thang
        },    
        countKeys(obj) {
            if (obj.__count__ !== undefined) { // Old FF
                return obj.__count__;
            }
            if (Object.keys) { // ES5 
                return Object.keys(obj).length;
            }
            // Everything else:
            var c = 0, p;
            for (p in obj) {
                if (obj.hasOwnProperty(p)) {
                    c += 1;
                }
            }
            return c;
        },
        ChuanHoaTen(ten) {
            if (!ten) return ''
            let tenNew = ''
            let strCon = ten.trim()
            let strXl = ''
            let k = 0
            let found = true
            if (strCon.length < 1) return
            strCon = strCon.replaceAll(/\s+/g, ' ')
            try {
                while (found) {
                k = strCon.indexOf(' ')
                if (k > 0) {
                    strXl = strCon.substring(0, k)
                    strCon = strCon.substring(k + 1)
                    strXl = this.ChuyenChuHoa(strXl)
                    if (tenNew !== '') {
                    tenNew += ' ' + strXl
                    } else tenNew = strXl
                    found = true
                } else found = false
                }
                if (strCon !== '') {
                strXl = strCon
                strXl = this.ChuyenChuHoa(strXl)
                if (tenNew !== '') {
                    tenNew = tenNew + ' ' + strXl
                } else {
                    tenNew = strXl
                }
                }
                return tenNew
            } catch (e) {
                return ten
            }
        },
        createdata(){

        },
        Clear(){
            this.lvCuocNong={}
            this.lvDichvu = []
            this.thuebao_id = 0
            this.isShowDCLD = false
            this.isShowLSTB=false
            this.dsLS=[]
            this.formTT = {
                ma_tb:'',
                loai_tb:'',
                ma_tinh:'',
                pin:'',
                puk:'',
                ngay_sinh:'',
                phai:'',
                doituong:'',
                so_msin:'',
                goi_di:false,
                goi_den:false,
                ngay_kh:'',
                pin2:'',
                puk2:'',
                so_gt:'',
                ngaycap_gt:'',
                ten_tb:'',
                diachi_chungtu:'',
                diachi:'',
                diachi_thuongtru:'',
                tk_chinh:'',
                tk_km:'',
                tk_km1:'',
                tk_km2:'',
            }
            this.diachi_ld ={
                diachi:"",
                diachitb_id:0,
                diachild_id:0,
                diachi_id:0,        
                tinh_id : 0,
                quan_id : 0,
                phuong_id : 0,
                pho_id : 0,
                ap_id : 0,
                khu_id : 0,
                dacdiem_id : 0,
                so_nha : null,        
                kinhdo:null,
                vido:null,
                lng:null,
                lat:null,
                block:null,
                tang:null,
                phong:null,
                mota:null,
            }
            this.gvLichSu3G.tableData=[]
            this.gvBienDongKH.tableData=[]
            this.gvLichSuTB.tableData=[]

        },

    },
    watch: {        
        'formCuocNong.nam': async function (newValue, oldValue) {
            this.loadMonth(newValue)
        },
    }
};
</script>
<style scoped>
 .btn-cuoc {
     padding:0;
 }
 .table-dv-content {
     max-height: 400px;
     overflow: scroll;
 }
</style>