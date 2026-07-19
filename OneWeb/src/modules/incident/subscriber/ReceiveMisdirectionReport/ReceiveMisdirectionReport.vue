<template src='./ReceiveMisdirectionReport.html'></template>

<style scoped src='./ReceiveMisdirectionReport.scss'></style>
<script>
import state from './define.js'
import api from './api/index.js';
import SearchAccountModal from './components/popups/SearchAccountModal.vue';
import {
  DichVuVienThong,  
  LOAI_DV,  
  TRANGTHAI_BH,
  LoaiHinhTB,
  TrangThaiHD
} from "./Enums";
export default {
    components: {
        SearchAccountModal
    },
    name: "FrmChuyenTo",
    props: ['baohong_id','vttbh_id','hdtb_id','vtthd_id','phieu_id'],
    mounted() {
        this.init();
    },
    
    data: function() {
        return {
            header: {
                title: "THAY ĐỔI ĐƠN VỊ XỬ LÝ SỰ CỐ",
                list: [{
                    name: "Phát triển thuê bao",
                    link: "/pttb",
                }, {
                    name: "Thay đổi đơn vị xử lý sự cố",
                    link: "/pttb/ct",
                }, ],
            },
             dateFormat: "DD/MM/YYYY",
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
            ...state,
        };
    },
    methods: {
        async init(){
            this.baohong_id = 85966;
            this.phieu_id = 56074;
            this.frmChuyenTo_Load();
        },
        async frmChuyenTo_Load(){
                if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
                    this.Tag = this.$route.query.tag
                }                
                if (this.Tag != "" && this.IS_USING_LACHUONG_BH)
                {
                    this.tag_hdbh = parseInt(this.Tag)
                }
                if (this.tag_hdbh != -1)
                {
                    this.layoutCT_DHXL.Text = "Thông tin phiếu yêu cầu"                 
                    this.header.title="THAY ĐỔI ĐƠN VỊ XỬ LÝ SỰ CỐ";
                    this.layout_DSLacHuong.Visibility = true;
                    this.layoutCT_DHXL.Visibility = false;
                    this.getDSDichvu()
                    this.getDSDVQL(LOAI_DV.DONVIQL_LD)
                    this.btnLayTTMoi.Visible = true;
                    this.NAP_DS_LACHUONG(0);
                    
                }
                else
                {
                    let loaidv_id = LOAI_DV.DONVIQL_LD
                    if (this.baohong_id > 0 && this.IS_USING_LACHUONG_BH)
                    {
                        loaidv_id += " , " + LOAI_DV.BH_QLDH;
                    }   
                   
                    if (this.baohong_id > 0)
                    {     
                        let  ds = [];
                        ds = this.GetData(await api.sp_load_form_phieu_bh_lachuong(this.axios,
                        {
                            "vbaohong_id":this.baohong_id,
                            "vhdtb_id":0,
                            "vphieu_id":this.phieu_id,
                            "vtthd_id":0
                        }
                        ));
                        console.log(ds)
                        if (ds){
                            if (ds.returnds_tram && ds.returnds_tram.length>0&& ds.returnds_tram.ttbh_id==TRANGTHAI_BH.DANG_DIEUHANH_XU_LY){
                                this.getDSDVQL(loaidv_id)                                
                            }
                            else {
                                this.cbx_donvi = ds.returnds_tram
                            }
                            this.HienThi_Thongtin_bh(ds.returnds);
                        }

                        this.layoutCT_DHXL.Text = "Chi tiết điều hành xử lý sự cố";
                        this.header.title = "THAY ĐỔI ĐƠN VỊ XỬ LÝ SỰ CỐ";
                    }
                    else if (this.hdtb_id > 0) 
                    {
                        this.getDSDVQL(loaidv_id)     
                        layoutCT_DHXL.Text = "Chi tiết điều hành thi công";
                        this.header.title  = "THAY ĐỔI ĐƠN VỊ THI CÔNG";
                        let input  = {
                                    "vbaohong_id": this.baohong_id,
                                    "vhdtb_id": 0,
                                    "vphieu_id": this.phieu_id,
                                    "vtthd_id": 0
                                    }
                        let ds =this.GetData(await api.sp_load_form_phieu_bh_lachuong(this.axios, input));  
                        if (ds){                            
                            this.HienThi_Thongtin_bh(ds.returnds);
                        }
                    }
                }  
        },
        SearchDanhBa() {
            this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data) {
            this.txtMaTB = data.ma_kh
        },
        async getDSDichvu(){

        },
         async txtMaTB_KeyPress(){
            let vma_tb = this.txtMaTB;
            var input = {
                        "maTb": vma_tb,
                        "dichVuVtId": 0,
                        "donViDlId": null
                }
                let data = [] ;
                let data_obj = this.GetData(await api.getTTDanhBa_MaTB(this.axios, input));
                data.push(data_obj);
                console.log(data)
            if (this.tag_hdbh != -1)
            {
                if(this.tag_hdbh==2)
                    this.NAP_DS_LACHUONG(data[0].thuebao_id);
            }
            else
            {
                if (this.baohong_id > 0)
                {
                    let input  = {
                                    "vbaohong_id": this.baohong_id,
                                    "vhdtb_id": 0,
                                    "vphieu_id": this.phieu_id,
                                    "vtthd_id": 0
                                    }
                    let ds =this.GetData(await api.sp_load_form_phieu_bh_lachuong(this.axios, input));
                    if (ds){                            
                        this.HienThi_Thongtin_bh(ds.returnds);
                    }
                }else if (this.hdtb_id > 0)
                {
                    let input  = {
                                    "vbaohong_id": 0,
                                    "vhdtb_id": this.hdtb_id,
                                    "vphieu_id": 0,
                                    "vtthd_id": TrangThaiHD.DANG_THI_CONG
                                    }
                    let ds =this.GetData(await api.sp_load_form_phieu_bh_lachuong(this.axios, input));                    
                    if (ds){                            
                        this.HienThi_Thongtin_bh(ds.returnds);
                    }
                }
            }
        },
        tsbtnCapNhat_Click()
        {
            if (this.tag_hdbh == -1)
                this.CapNhat();
            else
                this.NHAN_PHIEU_LACHUONG();
        },

        async NHAN_PHIEU_LACHUONG()
        {
             
            try
            {
                if (this.formTT.noidung == '')
                {
                    this.$toast.warning("Bạn chưa nhập nội dung chuyển tổ!");
                    this.$refs.NoiDung.focus();
                }else if (this.gvDanhSach.tableData.length == 0)
                {
                    this.$toast.warning("Không có danh sách để lựa chọn!");
                }else if (this.DanhSachLH_Select.length == 0)
                {
                    this.$toast.warning("Bạn chưa chọn danh sách để giao!");
                }else if (this.tag_hdbh == 1)
                {
                    this.$toast.warning("Thi công hiện đang trong quá trình xây dựng");
                }
                else if (this.tag_hdbh == 2)
                {
                    let input = {
                          "vds_phieu_id": JSON.stringify(this.DanhSachLH_Select),
                          "vdonvi_nhan_id": this.formTT.donvi_nhan_id,
                          "vnhanvien_id": this.tt_nd.nhanvien_id,
                          "vnoidung": this.formTT.noidung
                    }
                   
                    let thuchien = this.GetData(await api.chuyenphieu_lachuong_bh(this.axios, input));
       
                    if (thuchien.toString() == "1")
                    {
                        this.$root.$toast.success("Đã nhận phiếu thành công");
                        this.NAP_DS_LACHUONG(0);
                    }
                    else
                    {
                        this.$toast.warning("Có lỗi khi nhận phiếu lạc hướng " + thuchien.toString());
                    }
                }

            }
            catch (err)
            {
                console.log(err)
                this.$toast.error("Có lỗi khi nhận phiếu lạc hướng!");
            }
        },
        async CapNhat()
        {
            try
            {
                if (this.formTT.noidung.trim() == "")
                {
                    this.$toast.warning("Bạn chưa nhập nội dung chuyển tổ!");
                    this.$refs.NoiDung.focus();
                }
                let input = {
                        "vphieu_id":this.phieu_id,
                        "vttbh_id": this.vttbh_id,
                        "vdonvi_nhan_id": this.formTT.donvi_nhan_id,
                        "vnhanvien_id": this.tt_nd.nhanvien_id,
                        "vnoidung": this.formTT.noidung
                    }
                let thuchien = this.GetData(await api.chuyenphieu_baohong_v2(this.axios, input));
            }
            catch (err)
            {
                this.$toast.error("Có lỗi:"+err);
            }
        },
        async getDSDVQL(loaidv_id){
            try{
                await api.sp_lay_ds_donvi_theo_loaidv(this.axios,
                    {
                    "loaidv_id": loaidv_id
                    }).then(async response => {                
                        if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                            this.cbx_donviql = response.data.data;
                            this.formTT.donvi_ql_id = response.data.data[0].donvi_id;

                            if (this.tag_hdbh==-1)
                            {
                                let tramvt = this.GetData(await api.ht_donvicon_loaidv_combobox(this.axios, {
                                                "donvi_id":  this.formTT.donvi_ql_id,
                                                "loaidv_id": LOAI_DV.BH_DVXLDH
                                            }));
                                this.cbx_donvi = tramvt;
                                this.formTT.donvi_nhan_id = this.cbx_donvi[0].donvi_id
                            }
                            else
                            {
                                let tramvt = this.GetData(await api.ht_donvicon_loaidv_combobox(this.axios, {
                                                "donvi_id":  this.formTT.donvi_ql_id,
                                                "loaidv_id": LOAI_DV.VETINH
                                            }));
                                this.cbx_donvi = tramvt;
                                this.formTT.donvi_nhan_id = this.cbx_donvi[0].donvi_id;
                            }
                        }
                })            
            }
            catch(err){
                console.log(err)
            }
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        }, 
         async gridDanhSach__RowDeselected(data) {
            try
            {
                if (data) {
                    let row = data.data;
                    this.DanhSachLH_Select.splice(this.DanhSachLH_Select.indexOf(data.data), 1);
                }
                console.log(this.DanhSachLH_Select);
            }
            catch (err)
            {
                console.log(err);
            }
        },
        async gridDanhSach_FocusedRowChanged(data)
        {
            let row = data.data;
            if (row)
            {
                this.formTT.ngay_bh = row.NGAY_BH;
                this.txtMaTB = row.MA_TB;
                this.formTT.loaihinh_tb = row.LOAIHINH_TB;
                this.formTT.ten_tb = row.TEN_TB;
                this.formTT.diachi_ld = row.DIACHI_LD;
                this.formTT.ghichu = row.GHICHU;
                this.formTT.trangthai_bh = row.TRANGTHAI_BH;
                let dichvu = this.GetData(await api.getDSDichVu(this.axios,{}));
                this.formTT.ten_dvvt = dichvu.filter((item) => item.DICHVUVT_ID == row.DICHVUVT_ID)[0].TEN_DVVT;
                let obj = {};
                obj.BAOHONG_ID = row.BAOHONG_ID;
                obj.PHIEU_ID = row.PHIEU_ID;
                this.DanhSachLH_Select.push(obj);
                console.log(this.DanhSachLH_Select);
            }
        },
        async cboDonViQL_SelectedValueChanged()
        {
                let ttvt_id =this.formTT.donvi_ql_id;
                if (this.baohong_id > 0 && this.IS_USING_LACHUONG_BH)
                {
                    if (ttvt_id == 0 && this.tag_hdbh==-1)
                    {
                        let tramvt = this.GetData(await api.ht_donvicon_loaidv_combobox(this.axios, {
                                        "donvi_id": ttvt_id,
                                        "loaidv_id": LOAI_DV.BH_DVXLDH
                                    }));
                        this.cbx_donvi = tramvt;
                        this.formTT.donvi_nhan_id = this.cbx_donvi[0].donvi_id;
                    }
                    else
                    {
                        let tramvt = this.GetData(await api.ht_donvicon_loaidv_combobox(this.axios, {
                                        "donvi_id": ttvt_id,
                                        "loaidv_id": LOAI_DV.VETINH
                                    }));

                        this.cbx_donvi = tramvt;
                        this.formTT.donvi_nhan_id = this.cbx_donvi[0].donvi_id;
                    }
                }
                else
                {
                        let tramvt = this.GetData(await api.ht_donvicon_loaidv_combobox(this.axios, {
                                        "donvi_id": ttvt_id,
                                        "loaidv_id": LOAI_DV.VETINH
                                    }));

                        this.cbx_donvi = tramvt;
                        this.formTT.donvi_nhan_id = this.cbx_donvi[0].donvi_id;
                }
        },
        async HienThi_Thongtin_bh(dtBH){
            
            try{
                if (this.baohong_id > 0)
                {
                    if (dtBH.length > 0)
                    {
                        this.formTT.ngay_bh = dtBH[0].ngay_bh;
                        this.txtMaTB = dtBH[0].ma_tb.toString();
                        this.formTT.ten_dvvt = dtBH[0].ten_dvvt.toString();
                        this.formTT.loaihinh_tb = dtBH[0].loaihinh_tb.toString();
                        this.formTT.ten_tb = dtBH[0].tentb_db.toString();
                        this.formTT.diachi_ld = dtBH[0].diachild_db.toString();
                        this.formTT.ghichu = dtBH[0].ghichu_hong.toString();
                        this.formTT.trangthai_bh = dtBH[0].trangthai_bh.toString();
                        this.ttbh_id = dtBH[0].ttbh_id;
                        this.baohong_id = dtBH[0].baohong_id;
                        this.HienThi_DS_PhieuTH(this.baohong_id);
                    }
                }
                if (this.hdtb_id > 0)
                {
                    if (dtBH.length > 0)
                    {
                        this.formTT.ngay_bh = dtBH[0].ngay_yc;
                        this.txtMaTB= dtBH[0].ma_tb.toString();
                        this.formTT.ten_dvvt = dtBH[0].ten_dvvt.toString();
                        this.formTT.loaihinh_tb = dtBH[0].loaihinh_tb.toString();
                        this.formTT.ten_tb = dtBH[0].ten_tb.toString();
                        this.formTT.diachi_ld = dtBH[0].diachi_ld.toString();
                        this.formTT.ghichu = dtBH[0].ghichu.toString();
                        this.formTT.trangthai_bh = dtBH[0].trangthai_hd.toString();
                        this.ttbh_id = dtBH[0].tthd_id; 

                        this.HienThi_DS_PhieuTH(this.baohong_id);
                        let vdvn_id = dtBH[0].donvi_nhan_id.toString();
                        let vdonvi_ql_id = 0;
                         vdonvi_ql_id = this.GetData(await api.fn_lay_dvcha_theo_dvcon(this.axios, 
                        {
                            "vdonvi_id": vdvn_id,
                            "vloaidv_id": LOAI_DV.DONVIQL_LD
                        }));
                        
                        if (vdonvi_ql_id == 0)
                        {
                            this.$toast.warning("Không tìm thấy đơn vị quản lý!");
                        }else{
                            this.formTT.donvi_ql_id = vdonvi_ql_id;
                            this.formTT.donvi_nhan_id = vdvn_id;
                        }
                        
                    }
                    else
                    {
                        this.hdtb_id = 0;
                    }
                }           
            }
            catch(err){
                console.log(err)
                 return null
            }
        },
        async HienThi_DS_PhieuTH(vbaohong_id)
        {
            try
            {
                let Chitiet = this.GetData(await api.lay_danhsach_phieuth_bh(this.axios, 
                        {
                            "vbaohong_id": vbaohong_id
                        }
                        ));
                     console.log(Chitiet)
                this.gvChitiet.tableData = this.changeKeysToUpper(Chitiet);
                
                
            }
            catch (err)
            {
                console.log(err)
            }
        },
        async btnLayTTMoi_Click(){
            this.NAP_DS_LACHUONG(0)
        },
        async NAP_DS_LACHUONG(vthuebao_id){
           this.DanhSachLH_Select = []
            try{
                await api.lay_ds_phieu_bh_lachuong(this.axios,
                {
                    "vdichvuvt_id": 0,
                    "vthuebao_id": vthuebao_id,
                }
               
                ).then(response => {
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        this.gvDanhSach.tableData = this.changeKeysToUpper(response.data.data)
                    }
            })
            }
            catch(ex){
                console.log(ex)
            }
        },
        gridChitiet_FocusedRowChanged(data)
        {
            let row = data;
            this.formTT.nguoigiao = row.NV_GIAO;
            this.formTT.noinhan = row.DVN.toString();
            this.vdvtram_id = row.DONVI_NHAN_ID;
        },
        changeKeysToUpper(obj) {
            var key, upKey;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) {
                    upKey = key.toUpperCase();
                    if (upKey !== key) {
                        obj[upKey] = obj[key];
                        delete(obj[key]);
                    }
                    // recurse
                    if (typeof obj[upKey] === "object") {
                        this.changeKeysToUpper(obj[upKey]);
                    }
                }
            }
            return obj;
        },
    },
};
</script>