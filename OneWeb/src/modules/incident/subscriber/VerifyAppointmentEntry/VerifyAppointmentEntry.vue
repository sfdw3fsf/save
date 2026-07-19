
<template src='./VerifyAppointmentEntry.html'></template>

<style scoped src='./VerifyAppointmentEntry.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import moment from 'moment'
import { mapActions,mapGetters } from 'vuex'
import frmLichSuBaoHong from "./popups/frmLichSuBaoHong.vue";
import frmLichSuHenBaoHong from "./popups/frmLichSuHenBaoHong.vue";
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
const DatabaseConstants = { DB2: "CSS",DB7:"BAOHONG",DB3:"QLTN" };
export default {
    components: {
        VueElement,
        frmLichSuBaoHong,
        frmLichSuHenBaoHong,
        DataGridCustom,
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY HH:mm",
            ...collums,
            header: {
                title: "Xác minh thông tin hẹn",
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
            phieu_id: 0,
            baohong_id: 0,
            hdtb_id: 0,
            thuchien: false,
            thuebao_id: 0,
            henbh_id: 0,
            rid:0,
            lblThongBao:'',
            txtGhichu: '',
            ckbSendSMS: true,
            rdoDongY: 1,
            rdoKH_KhongHen:0,
            rdoChuaXM: 1,
            kq_sms: "",
            loai_hen:0,
            rdoDaXM: 0,
            tag: 0,
            dtpGioHen_Tu: moment().format('DD/MM/YYYY'),
            dtpGioHen_Den: moment().format('DD/MM/YYYY'),
            Fn: {
                NhapMoi: false,
                GhiLai: false,
                HuyBo: false,
                Xoa: false,
            },
            dataSelected: {
                lydohen_id: 0,
            },
            btn:{
                btn_giuphieu: false,
                btn_huygiuphieu: false,
            },
            options: {
                DsLyDoHen: [],
                dtPhieuHen:[],
                dtPhieuchon: [],
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
            }
        };
    },
    mounted() {
        if (this.$route.query.tag && this.$route.query.tag !== '') {
            this.tag = this.$route.query.tag.replaceAll(' ', '+')
        }
        this.init();
    },
    computed:{
    },
    validations: {

    },
    methods: {
        async init() {
            try {
                if(this.$root.token.getMaTinh() == 'HCM'){
                    this.btn.btn_giuphieu = true
                    this.btn.btn_huygiuphieu = true
                }
                this.frmXacMinhHenKH_Load();
            } catch (e) {
                console.log(e);
            }
        },
        async tsbtnCapNhat_Click()
        {
            try
            {
                if(moment(this.dtpGioHen_Den,'DD/MM/YYYY HH:mm:ss').isBefore(moment(this.dtpGioHen_Tu,'DD/MM/YYYY HH:mm:ss'))){
                    this.$toast.error('Hẹn từ ngày không được lớn hơn hẹn đến ngày !')
                    return false
                }
                if(moment(this.dtpGioHen_Den,'DD/MM/YYYY HH:mm:ss').isBefore(new Date())){
                    this.$toast.error('Hẹn đến ngày không được nhỏ hơn ngày hiện tại !')
                    return false
                }
                if(this.tag.toString()== '0'){
                    let ttbh = "";
                    if (this.loai_hen == 1) {//báo hỏng
                        var input1 = {};
                        input1.type = 4;
                        input1.param = this.baohong_id;
                        ttbh = this.GetData(await api.fn_tt_baohong(this.axios, input1));
                    }else if (this.loai_hen == 2){// phản hồi KH
                        var input2 = {};
                        input2.type = 1;
                        input2.param=this.baohong_id;
                        ttbh = this.GetData(await api.fn_tt_khieunai(this.axios, input2));
                    }
                    if (ttbh == "6")
                    {
                        this.$toast.warning("Báo hỏng đã hoàn thành. Không thể cập nhật giờ hẹn!");
                        return;
                    }
                    else if (ttbh == "-1")//thêm trường hợp ngoại lệ cho chắc
                    {
                        this.$toast.warning("Báo hỏng không tồn tại");
                        return;
                    }
                    if (this.loai_hen == 1)//báo hỏng
                    {
                        var input3 = {};
                        input3.type= 3;
                        input3.param = this.baohong_id;
                        //Lấy ngày báo hỏng
                        let ngay_bh = this.GetData(await api.fn_tt_baohong(this.axios, input3));
                    
                        ngay_bh = moment(ngay_bh,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm');
                        if (new Date(this.dtpGioHen_Tu) <= new Date(ngay_bh) || new Date(this.dtpGioHen_Den) <= new Date(ngay_bh))
                        {   
                            this.$toast.warning("Ngày hẹn phải lớn hơn ngày báo hỏng ("+ngay_bh+")!");
                            return;
                        }
                        if (new Date(this.dtpGioHen_Tu) > new Date(this.dtpGioHen_Den))
                        {
                            this.$toast.warning("Dữ liệu ngày hẹn không chính xác!");
                            return;
                        }
                    }
                    else if (this.loai_hen == 2)
                    {
                        var input4 = {};
                        input4.type = 2;
                        input4.param = this.baohong_id;
                        
                        let ngay_bh = this.GetData(await api.fn_tt_khieunai(this.axios, input4));
                        
                        ngay_bh = moment(ngay_bh,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm');
                        if (new Date(this.dtpGioHen_Tu) <= new Date(ngay_bh) || new Date(this.dtpGioHen_Den) <= new Date(ngay_bh))
                        {
                            this.$toast.warning("Ngày hẹn phải lớn hơn ngày yêu cầu!");
                            return;
                        }
                        if (new Date(this.dtpGioHen_Tu) > new Date(this.dtpGioHen_Den))
                        {
                            this.$toast.warning("Dữ liệu ngày hẹn không chính xác!");
                            return;
                        }
                    }
                    if (!this.KIEMTRA_PHIEU("0", this.henbh_id, 0)){
                        return;
                    } 
                  
                         await api.Capnhat_xacminh_hen_baohong_v3(this.axios, {
                                "vhenbh_id":this.henbh_id,
                                "vbaohong_id":this.baohong_id,
                                "vketqua_xm":this.rdoDongY ==1 ? 1 : 2,
                                "vgiohen_tu": this.dtpGioHen_Tu,
                                "vgiohen_den": this.dtpGioHen_Den,
                                "vnhanvien_id":this.tt_nd.nhanvien_id,
                                "vnoidung":this.txtGhichu,
                                "vloai_hen":this.loai_hen,
                                "vlydoxm_id": this.dataSelected.lydohen_id,
                        }).then((res) => {
                        }).catch((ex) => { 
                            this.ckbSendSMS = false
                            this.$toast.error("Lỗi: "+ ex.data.message_detail);
                            return; 
                        });
                   
                    if (this.ckbSendSMS)
                    {
                        try
                        {
                            if (this.baohong_id <= 0)
                            {
                                this.$toast.warning("Không có báo hỏng để nhắn");
                            }
                            else
                            {
                                let input = {
                                                "vkieu":2,
                                                "vrid":this.baohong_id,
                                                "vma_tb": "test",
                                                "vsdt": "0911414010"
                                            }
                                let kq_ = this.GetData(await api.Send_sms_ld(this.axios,input));
                                if (kq_.toString() == "1")
                                {
                                    this.kq_sms = "1";
                                }
                            }
                        }
                        catch (err)
                        {
                            this.$toast.error( "Lỗi gửi tin nhắn: " + err.message);
                            return;
                        }
                         if (this.kq_sms == "1"){
                                this.$root.$toast.success("Cập nhật và nhắn cho khách hàng thành công");
                            }
                            else{
                                this.$root.$toast.success("Cập nhật thành công");
                            }
                    }
                    
                }else{
                        await api.capnhat_xacminh_hen_lapdat(this.axios, {
                                "vrid":this.rid,
                                "vhdtb_id":this.hdtb_id,
                                "vketqua_xm":this.rdoDongY ==1 ? 1 : 2,
                                "vgiohen_tu": this.dtpGioHen_Tu,
                                "vgiohen_den": this.dtpGioHen_Den,
                                "vnhanvien_id":this.tt_nd.nhanvien_id,
                                "vnoidung":this.txtGhichu,
                                "vlydoxm_id": this.dataSelected.lydohen_id,
                        }).then((res) => {
                            this.$root.$toast.success("Cập nhật thành công");
                        }).catch((ex) => { 
                            this.$toast.error("Lỗi: "+ ex.data.message_detail);
                            return; 
                        });
                }
                let ThongBao = this.GetData(await api.Lay_thongbao_xacminh_hen(this.axios, {}));
                this.lblThongBao = ThongBao;
                this.HienThiDanhSach();
            }
            catch (err)
            {
                console.log(err)
               this.$toast.error( "Lỗi: " + err.data.message_detail);
            }
        },
        btnLayTTMoi_Click()
        {
            this.HienThiDanhSach();
        },
        tsmGiuPhieu_Click()
        {
            if(this.tag.toString() == '0'){
                this.HUY_GIUPHIEU(1);
            }else{
                this.HUY_GIUPHIEU_LD(1);
            }
            
        },

        tsmHuyGiuPHieu_Click()
        {
            if(this.tag.toString() == '0'){
                this.HUY_GIUPHIEU(2);
            }else{
                this.HUY_GIUPHIEU_LD(2);
            }
        },

        async frmXacMinhHenKH_Load()
        {
            if(this.tag.toString() == '0'){
                this.header.title = "Xác minh thông tin hẹn báo hỏng"
            }else{
                this.header.title = "Xác minh thông tin hẹn lắp đặt"
            }
            let dtLDH = this.GetData(await api.Get_DS_Lydohen(this.axios, {"kieu_hen": 2}));
            this.dtpGioHen_Tu = moment(new Date((new Date()).getTime() - 7*24*60*60*1000)).format('DD/MM/YYYY HH:mm') ;
        
            this.dtpGioHen_Den =  moment(new Date()).format('DD/MM/YYYY HH:mm')
            this.options.DsLyDoHen = dtLDH;
            this.dataSelected.lydohen_id = this.options.DsLyDoHen[0].lydohen_id;

            let ThongBao = this.GetData(await api.Lay_thongbao_xacminh_hen(this.axios, {}));
            this.lblThongBao = ThongBao;

            this.HienThiDanhSach();

        },
        async ExportPhieuHen() {
            try {
                let excelExportProperties = {
                    fileName: "Danh sách phiếu nhập hẹn.xlsx",
                };
                this.$refs.DSPhieuhen.excelExport(excelExportProperties);
                this.$toast.success("Xuất Excel danh sách phiếu nhập hẹn thành công !");
            } catch (e) {
                console.log('ExportLSNo: ', e);
                this.$toast.error("Lỗi xuất Excel danh sách phiếu nhập hẹn: " + e.message);
            }
        },
        async HienThiDanhSach()
        {
            try
            {

                if(this.tag.toString() == '0'){
                    let PhieuHen = this.GetData(await api.Lay_ds_hen_baohong_xm(this.axios, 
                        { 
                            "vtrangthai":this.rdoChuaXM == 1 ? 1 : 6,
                            "vdonvi_id":this.tt_nd.donvi_id,
                        }
                    ));
                    this.options.dtPhieuHen = PhieuHen;
                }else{
                     let PhieuHen = this.GetData(await api.lay_ds_hen_xacminh_hopdong(this.axios, 
                        { 
                            "vtrangthai":this.rdoChuaXM == 1 ? 1 : 6,
                            "vdonvi_id":this.tt_nd.donvi_id,
                        }
                    ));
                    this.options.dtPhieuHen = PhieuHen;
                }
                
            }
            catch (err)
            {
                this.$toast.error( "Lỗi hiển thị danh sách phiếu hẹn: " + err.message);
            }
        },
        async HUY_GIUPHIEU_LD(kieu)
        {
            try
            {
                var dt = this.options.dtPhieuHen;
                if (dt == null || dt.length == 0)
                {
                    this.$toast.warning("Không có danh sách phiếu không thể thực hiện");
                    return;
                }
                if (this.options.dtPhieuchon.Length == 0)
                {
                    this.$toast.warning("Chưa chọn danh sách phiếu để giữ không thể thực hiện");
                    return;
                }
                await api.giuphieu_xacminh_hen_ld(this.axios, {
                       "vrid": this.rid,
                       "vkieu": kieu 
                }).then((res) => {
                    if (res.data.error === 200 || res.data.error === '200') {
                        this.$toast.success(kieu == 1 ? "Giữ phiếu thành công" : "Huỷ giữ phiếu thành công");
                        return res.data.data
                    } else {
                        this.$toast.error( "Lỗi: " + res.data.message);
                    }
                });
                this.HienThiDanhSach();
            }
            catch (err)
            {
                this.$toast.error( "Có lỗi khi thực hiện: " + err.message);
            }
        },
        async HUY_GIUPHIEU(kieu)
        {
            try
            {
                var dt = this.options.dtPhieuHen;
                if (dt == null || dt.length == 0)
                {
                    this.$toast.warning("Không có danh sách phiếu không thể thực hiện");
                    return;
                }
                if (this.options.dtPhieuchon.Length == 0)
                {
                    this.$toast.warning("Chưa chọn danh sách phiếu để giữ không thể thực hiện");
                    return;
                }
                if (!this.KIEMTRA_PHIEU(this.options.dtPhieuchon, 0, kieu)){
                    return;
                }
                if (kieu == 1)
                {
                    let kt = this.GetData(await api.Huy_giuphieu_xacminh_henkh(this.axios,{}));
                   if (kt.length > 0)
                    {
                        this.$toast.warning("Bạn đã giữ phiếu. Không thể giữ thêm!");
                        return;
                    }
                }
                await api.Huy_giuphieu_xacminh_henkh_v2(this.axios, {
                       "vdanh_sach": JSON.stringify(this.options.dtPhieuchon)
                }).then((res) => {
                    console.log(res)
                });
                this.$toast.success(kieu == 1 ? "Giữ phiếu thành công" : "Huỷ giữ phiếu thành công");
                this.HienThiDanhSach();
            }
            catch (err)
            {
                this.$toast.error( "Có lỗi khi thực hiện: " + err.message);
            }
        },
        async KIEMTRA_PHIEU(danhsach, p_id, kieu)
        {
            
                if (danhsach != "0")
                {
                    let ds_temp = danhsach;
                    if (danhsach == "-1")
                    {
                        var dt = this.options.dtPhieuHen;
                        if (dt == null || dt.length == 0)
                        {
                            this.$toast.warning("Không có danh sách phiếu không thể thực hiện");
                            return false;
                        }
                        if (this.options.dtPhieuchon.Length == 0)
                        {
                            this.$toast.warning("Chưa chọn danh sách phiếu để giữ không thể thực hiện");
                            return false;
                        }
                    }

                    let Kiemtra = this.GetData(await api.Kiemtra_phieu_xacminh_henkh(this.axios, 
                                {
                                    "vdanh_sach": JSON.stringify(this.options.dtPhieuchon),
                                    "vkieu": 0
                                }));
                    if (Kiemtra[0].tong != null)
                    {
                        this.$toast.warning("Trong danh sách có phiếu đã có người khác giữ bạn không thể thao tác");
                        return false;
                    }
                    return true;
                }
                if (p_id != 0)
                {
                     let Kiemtra = this.GetData(await api.Kiemtra_phieu_xacminh_henkh(this.axios, 
                                {
                                    "id": p_id,
                                    "loai_hen": 1
                                }));
                    if (Kiemtra[0].tong != null)
                    {
                        this.$toast.warning("Trong danh sách có phiếu đã có người khác giữ bạn không thể thao tác");
                       return false;
                    }
                    return true;
                }
                else
                    return false;

        },
        grvDSHen_BaoHong_FocusedRowChanged(data)
        {
            this.options.dtPhieuchon = [];
            let row = data.data;
            try
            {
                if(this.tag.toString()=='0'){
                    this.henbh_id = row.henbh_id.toString();
                    this.baohong_id = row.baohong_id.toString();
                    this.thuebao_id = row.thuebao_id.toString();
                    this.loai_hen = row.loai_hen.toString();
                }else{
                    this.rid = row.rid.toString();
                    this.hdtb_id = row.hdtb_id.toString();
                }
                if (row.giohen_tu.toString() != ""){
                    this.dtpGioHen_Tu = moment(row.giohen_tu).format('DD/MM/YYYY HH:mm');
                }
                if (row.giohen_den.toString() != ""){
                     this.dtpGioHen_Den = moment(row.giohen_den).format('DD/MM/YYYY HH:mm');
                }
                if (this.rdoChuaXM.toString() === "1")
                {
                    this.txtGhichu = row.noidung.toString();
                }
                else
                {
                    this.txtGhichu = row.ghichu;
                }
                if(row.ketqua_xm != null){
                    this.rdoDongY = row.ketqua_xm.toString() == 1;
                }
                this.options.dtPhieuchon.push(row);
            }
            catch (err)
            {
                console.log(err)
            }
        },

        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                console.log(response)
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
        rdoChuaXM_CheckedChanged()
        {
            this.rdoChuaXM = 1;
            this.rdoDaXM = 0;
            this.HienThiDanhSach();
        },
        rdoDaXM_CheckedChanged()
        {
            this.rdoChuaXM = 0;
            this.rdoDaXM = 1;
            this.HienThiDanhSach();
        },
        rdoDongY_CheckedChanged()
        {
            this.rdoKH_KhongHen = 0;
        },
        onChangeLyDoHen(){

        }
    },
    watch: {}
};
</script>
