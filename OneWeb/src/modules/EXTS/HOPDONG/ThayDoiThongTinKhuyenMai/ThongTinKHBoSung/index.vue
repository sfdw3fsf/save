<template src="./index.html">
</template>
<script>
import AssignManagement from './AssignManagement/AssignManagement'
export default {
  name: "ThongTinKHBoSung",
  components:{AssignManagement},
  props:{
    id: String,
    objTTKHBoSung: {
      hdkh_id:Number,
      nhapmoi:Boolean,
      vkieukh_id_:Number,
      mast:String,
      ts_kt_mstmns_kh:Number,
      ts_kt_tthdkhdn:Number,
      dichvuvt_id:Number,
      vloaikh_id: Number,
      chuquan_id:Number
    }
  },
  data: function (){
    return {
      hdkh_id:null,
      nhapmoi:false,
      vkieukh_id_:0,
      mast:"",
      ts_kt_mstmns_kh:0,
      ts_kt_tthdkhdn:0,
      dichvuvt_id:null,
      khachhang_id: 0,
      vsohd_tusinh:"",
      vma_kh: "",
      nhanvien_vnpt_i: false,
      is_bancheo: false,
      ds_hdkh_sub:[],
      vloaikh_id: 0,
      chuquan_id: -1,
      istracuu: false,
      text: null, //this.Form.Text: tiêu đề form

      dateFormat: 'dd/MM/yyyy',
      layoutControlItem1: { // Số phòng
        Visibility:true,
      },
      layoutControlItem5:{ // Mã ngân sách
        Visibility:true,
      },
      layoutControlItem22:{ // Chủ quản
        Visibility:true,
      },
      layoutControlItem25:{ // Kênh bán
        Visibility:true,
      },
      layoutControlItem14:{ // Số hợp đồng
        Visibility:true,
      },
      txt_So_Phong: null,
      txtMaNSach: null,
      layoutControlGroup2:{
        Visibility:true,
      },
      // layoutControlGroup2: variable
      txt_Nguoi_GT: null,
      txt_ChucVu_GT:null,
      txt_SoGiay_GT:null,
      dtp_NgayCap_GT:null,

      layoutControlGroup3:{
        Visibility:true,
      },
      // layoutControlGroup3 : variable
      txtSoHD_KHDN:null,
      txtSoHD_Giay:null,
      chkKenhTrong:false,
      chkKenhNgoai:false,
      chk:false,
      cboLoaiKenh:1,
      cboKenhID:-1,
      cboKhoi: null,
      isEnableCboKhoi: false,
      dsKhoi: [],
      dsKenhThu:[],
      dsChuQuan:[],
      layoutHD_KHDN:{
        Visibility:false,
      },
      // layoutHD_KHDN: variable
      txtSoHD:null,
      txtSoPL:null,
      spGiaTriHD:null,
      dtpNgayKy:null,
      txtGiayUQ:null,
      cboLoaiHD:null,
      cboTinhTrangHD:null,
      category: {
        layoutControlGroup2: 0,
        layoutControlGroup3: 0,
        layoutHD_KHDN: 0,
      },
      dsKenhID:[],
      dsLoaiHD:[],
      dsTinhTrangHD:[],

      KIEU_KH:{
        CA_NHAN:0,
        DOANH_NGHIEP:1,
      },

      isEnabledCboLoaiKenh:true,
      isEnabledCboKenhID:true,
      cboKenhThu: null,
      isEnableCboKenhThu:true,
      cboChuQuan: null,
      isEnableCboChuQuan:false,

      popupAssignSubManagerData:[],
      popupComponentName: '',
      popupComponent: null,

      //input form GanNVQL
      vtimkiem_hdkh_id:null,
      gan_hd:true,
    }
  },
  watch:{
    chkKenhTrong:async function (val) {
      // this.chkKenhNgoai = !val
      if (val) {
        this.dsKenhID = []
        this.isEnabledCboLoaiKenh = false
        this.isEnabledCboKenhID = false
      } else {
        this.isEnabledCboLoaiKenh = true
        this.isEnabledCboKenhID = true
        this.dsKenhID = await this.HT_DS_NhanVien_Theo_NhomLNV_Combobox(-1, 6)
      }
    },
    chkKenhNgoai: function (val){
      this.chkKenhTrong = !val
    },
    chk: function (val) {
      if (val) {
        this.chkKenhNgoai = true
        this.chkKenhTrong = false
      } else {
        this.chkKenhTrong = true
        this.chkKenhNgoai = false
      }
    }
  },
  methods:{
    async onShownModal() {
      try {
        this.loading(true);
        this.hdkh_id = parseInt(this.objTTKHBoSung.hdkh_id+'');
        this.khachhang_id = parseInt(this.objTTKHBoSung.khachhang_id+'');
        this.nhapmoi = this.objTTKHBoSung.nhapmoi;
        this.vkieukh_id_ = parseInt(this.objTTKHBoSung.vkieukh_id_+'');
        this.mast = this.objTTKHBoSung.mast;
        this.ts_kt_mstmns_kh = parseInt(this.objTTKHBoSung.ts_kt_mstmns_kh+'');
        this.ts_kt_tthdkhdn = parseInt(this.objTTKHBoSung.ts_kt_tthdkhdn+'');
        this.dichvuvt_id = parseInt(this.objTTKHBoSung.dichvuvt_id+'');
        this.vloaikh_id = parseInt(this.objTTKHBoSung.vloaikh_id+'');
        let kiemtra_nv_vn = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_co_thuoc_vnpt_i_khong?vnhanvien_id=${this.$auth.getNhanVienID()}`)
        if(kiemtra_nv_vn.data.result == "OK"){
          this.nhanvien_vnpt_i = true;
        }
        console.log("this.nhanvien_vnpt_i: " + this.nhanvien_vnpt_i)
        if (!this.is_bancheo)
        {
          this.layoutControlItem22.Visibility = false;
        }
        else
        {
          this.dsChuQuan = await this.lay_ds_chuquan();
          this.cboChuQuan = this.dsChuQuan.length > 0 ? this.dsChuQuan[0].chuquan_id : null
          isEnableCboChuQuan = true;;
        }
        this.dsKenhID = await this.HT_DS_NhanVien_Theo_NhomLNV_Combobox(-1, 6);
        this.dsKenhThu = await this.lay_ds_kenhban();
        this.cboKenhThu = this.dsKenhThu.length > 0 ? this.dsKenhThu[1].kenhban_id : null
        if (this.vloaikh_id || this.vloaikh_id == 0)
          this.dsKhoi = await this.lay_ds_khoi_loaikh(this.vloaikh_id, 1);
        this.isEnableCboKhoi = true;
        this.cboKhoi = this.dsKhoi.length > 0 ? this.dsKhoi[0].khoi_id : null;
        if (this.ts_kt_tthdkhdn == 1) {
          this.layoutHD_KHDN.Visibility = true;
          this.layoutControlGroup2.Visibility = false;
          this.layoutControlItem1.Visibility = false;
          this.layoutControlItem5.Visibility = false;
          this.layoutControlItem14.Visibility = false;
          this.text = 'Thông tin hợp đồng';
          await this.NAP_COMBO();
        }
        if (this.vkieukh_id_ == 0){
          console.log("this.vkieukh_id_:",this.vkieukh_id_)
          this.layoutControlGroup3.Visibility = false;
        }
        if (!this.nhapmoi){
          this.dichvuvt_id = 0;
          this.loaihinhtb_id = 0;
          let dt =[];
          if(!this.istracuu){
            dt = await this.LaythongtinHDKH_SUB();
          }
          else{
            dt = await this.layDsDbkhTheoKhId(this.khachhang_id);
          }
          //  dt = await this.LaythongtinHDKH_SUB()
          if (dt.length > 0){
            dt = dt[0];
            this.txt_So_Phong = dt.so_phong;
            this.txt_Nguoi_GT = dt.nguoi_gt;
            this.txt_SoGiay_GT = dt.sogiay_gt;
            this.txt_ChucVu_GT = dt.chucvu_gt;
            this.txtSoHD_KHDN = dt.so_hd_khdn;; 
            this.vsohd_tusinh = dt.so_hd_khdn;
            this.txtSoHD_Giay = dt.so_pl_khdn;
            if (dt.kenh_pt)
              if (dt.kenh_pt.toString() == "1") {
                this.chkKenhTrong = true;
                this.chk = 0;
              } else {
                this.chkKenhNgoai = true;
                this.chk = 1;
              }
            if (dt.loaikenh)
              this.cboLoaiKenh = parseInt(dt.loaikenh.toString(), 10);
            if (dt.kenh_id)
              this.cboKenhID = parseInt(dt.kenh_id.toString(), 10);
            if (dt.kenhban_id)
              this.cboKenhThu = parseInt(dt.kenhban_id.toString(), 10);
            if (dt.chuquan_id)
              this.cboChuQuan = parseInt(dt.chuquan_id.toString(), 10);
            if (dt.khoi_id)
              this.cboKhoi = parseInt(dt.khoi_id.toString(), 10);
            if (dt.ngaycap_gt)
              this.dtp_NgayCap_GT = new Date(this.ddmmyyyyhhmissToyyyymmddhhmiss(dt.ngaycap_gt));
            this.txtMaNSach = dt.ma_ns;
            if (this.ts_kt_tthdkhdn == 1){
              this.txtSoHD = dt.so_hd_khdn;
              this.txtSoPL = dt.so_pl_khdn;
              if (dt.giatri_hd)
                this.spGiaTriHD = parseInt(dt.giatri_hd.toString(), 10);
              else this.spGiaTriHD = 0;
              if (dt.ngay_ky_hd)
                this.dtpNgayKy = new Date(this.ddmmyyyyhhmissToyyyymmddhhmiss(dt.ngay_ky_hd));
              this.txtGiayUQ = dt.giay_uq;
              this.cboLoaiHD = -1;
              if (dt.loaihdkh_id)
                this.cboLoaiHD = dt.loaihdkh_id;
              this.cboTinhTrangHD = -1;
              if (dt.tinhtranghd_id)
                this.cboTinhTrangHD = dt.tinhtranghd_id;
            }
          }
        }else {
          let dt =[];
          if (this.ds_hdkh_sub.length > 0){
            dt = this.ds_hdkh_sub;
          }
          else if (this.khachhang_id > 0){
            dt = await this.layDsDbkhTheoKhId(this.khachhang_id);
          }
          //  dt = await this.LaythongtinHDKH_SUB()
          if (dt.length > 0){
            dt = dt[0];
            this.txt_So_Phong = dt.so_phong;
            this.txt_Nguoi_GT = dt.nguoi_gt;
            this.txt_SoGiay_GT = dt.sogiay_gt;
            this.txt_ChucVu_GT = dt.chucvu_gt;
            this.txtSoHD_KHDN = dt.so_hd_khdn; 
            this.vsohd_tusinh = dt.so_hd_khdn;
            this.txtSoHD_Giay = dt.so_pl_khdn;
            if (dt.kenh_pt){
              if (dt.kenh_pt.toString() == "1") {
                this.chkKenhTrong = true;
                this.chk = 0;
              } else {
                this.chkKenhNgoai = true;
                this.chk = 1;
              }
            }
            if (dt.loaikenh)
              this.cboLoaiKenh = parseInt(dt.loaikenh.toString(), 10);
            if (dt.kenh_id)
              this.cboKenhID = parseInt(dt.kenh_id.toString(), 10);
            // if (dt.kenhban_id)
            //   this.cboKenhThu = parseInt(dt.kenhban_id.toString(), 10)
            if (dt.chuquan_id)
              this.cboChuQuan = parseInt(dt.chuquan_id.toString(), 10);
            if (dt.khoi_id)
              this.cboKhoi = parseInt(dt.khoi_id.toString(), 10);
            if (dt.ngaycap_gt)
              this.dtp_NgayCap_GT = new Date(this.ddmmyyyyhhmissToyyyymmddhhmiss(dt.ngaycap_gt));
            this.txtMaNSach = dt.ma_ns;
            if (this.ts_kt_tthdkhdn == 1){
              this.txtSoHD = dt.so_hd_khdn;
              this.txtSoPL = dt.so_pl_khdn;
              if (dt.giatri_hd)
                this.spGiaTriHD = parseInt(dt.giatri_hd.toString(), 10);
              else this.spGiaTriHD = 0;
              if (dt.ngay_ky_hd)
                this.dtpNgayKy = new Date(this.ddmmyyyyhhmissToyyyymmddhhmiss(dt.ngay_ky_hd));
              this.txtGiayUQ = dt.giay_uq;
              this.cboLoaiHD = -1;
              if (dt.loaihdkh_id)
                this.cboLoaiHD = dt.loaihdkh_id;
              this.cboTinhTrangHD = -1;
              if (dt.tinhtranghd_id)
                this.cboTinhTrangHD = dt.tinhtranghd_id;
            }
          }else{
            if (this.vkieukh_id_ ==1){
              this.txtSoHD_KHDN = await this.sinh_so_hd_khdn(1);
              this.vsohd_tusinh = this.txtSoHD_KHDN;
            }
          }
        }
      } catch (e) {

      }finally {
        this.loading(false);
      }
    },
    onHiddenModal(){
      this.clear()
    },
    async btn_Save_Click() {
      try {
        this.loading(true)
        if ((this.txt_So_Phong+"").length > 5) {
          this.$root.toastError("Số phòng không được 5 chữ số")
          this.$refs.txt_So_Phong.focus()
          return
        }
        if (this.ts_kt_tthdkhdn == 1 && !this.nhanvien_vnpt_i) { // Tham số kiểm tra bộ 3 Số HĐ, MA-KH, Ngày ký không được trùng
          this.$refs.txtSoHD.focus()
          if ((this.txtSoHD + "").toString() == "") {
            this.$root.toastError("Bạn chưa nhập thông tin Số hợp đồng")
            this.$refs.txtSoHD.focus()
            return
          }
          if (!this.dtpNgayKy) {
            this.$root.toastError("Bạn chưa nhập thông tin Ngày ký")
            this.$refs.dtpNgayKy.focus()
            return
          }
          if (!this.cboLoaiHD && (this.cboLoaiHD + "").toString() == "-1") {
            this.$root.toastError("Bạn chưa nhập thông tin Loại hợp đồng")
            this.$refs.cboLoaiHD.focus()
            return
          }
          if (!this.cboTinhTrangHD && (this.cboTinhTrangHD + "").toString() == "-1") {
            this.$root.toastError("Bạn chưa nhập thông tin Tình trạng")
            this.$refs.cboTinhTrangHD.focus()
            return
          }
          if (!this.vma_kh) {
            this.$root.toastError("Chưa có thông tin Mã khách hàng")
            this.$refs.txtSoPL.focus()
            return
          }
          let ngaycap = "-1";
          if (this.dtpNgayKy)
            ngaycap = this.ddmmyyyy(this.dtpNgayKy)
          if (this.chkKenhNgoai && (this.cboKenhID + "").toString() == "-1") {
            this.$root.toastError("Hãy chọn kênh ID trước khi cập nhật")
            return
          }
          let kt = await this.kiemtra_thongtin_sub_khdn(this.hdkh_id,(this.txtSoHD+"").toString().trim(),this.vma_kh, ngaycap)
          if ((kt.data+"").toString() != "1"){
            this.$root.toastError(kt.data)
            return
          }
        }
        if (this.dsKhoi.length > 0 && this.cboKhoi) {
          let khoi = this.dsKhoi.find(x => x.khoi_id == this.cboKhoi)
          if (!khoi) {
            this.$root.toastError("Chưa chọn thông tin khối")
            return
          }
        }
        if (!this.nhapmoi){
          console.log("22")
          if (this.vkieukh_id_ == this.KIEU_KH.DOANH_NGHIEP){ //Khách hàng doanh nghiệp
            let mans = this.txtMaNSach
            if (this.ts_kt_mstmns_kh == 1 && mans == "" && this.mast == ""){
              console.log("33")
              let kq = "0";
              kq= await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_hdkh_baohieuqt?p_hdkh_id=${this.hdkh_id}`)
              if(parseInt(kq.toString(), 10)){
                this.$root.toastError("Bạn phải nhập mã số thuế hoặc mã ngân sách")
                return
              }
            }
            if (mans != "" && mans != null)
            {
              if (mans.length != 7){
                this.$root.toastError("Mã ngân sách phải đủ 7 ký tự số. \nBạn hãy kiểm tra lại!")
                return
              }
              let ArrCheck = [1, 2, 3, 7, 8, 9]
              if (mans.substring(0,1) != "1"
                && mans.substring(0,1) != "2"
                && mans.substring(0,1) != "3"
                && mans.substring(0,1) != "7"
                && mans.substring(0,1) != "8"
                && mans.substring(0,1) != "9"){
                this.$root.toastError("Mã ngân sách phải bắt đầu là 1/2/3/7/8/9. \nBạn hãy kiểm tra lại!")
                return
              }
            }
          }
          let vso_hd = this.txtSoHD_Giay
          let vkq = await this.kiemtra_so_hd_khdn(vso_hd)
          if (vkq != "OK"){
            this.$root.toastError(vkq)
            return
          }
          this.ds_hdkh_sub = [this.TaoDuLieu()]
          await this.$root.context.post("web-hopdong/thaydoithongtinkhuyenmai/xoa_themmoi_hdkh_sub",this.ds_hdkh_sub[0])
          this.$root.toastSuccess("Cập nhật thành công")
        }else{
          this.ds_hdkh_sub = [this.TaoDuLieu()]
          this.$bvModal.hide(this.id)
        }
        this.$emit("dsHDKH_SUB",this.ds_hdkh_sub)
      }catch (e) {
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    cboLoaiKenh_EditValueChanged(){
      if (this.chkKenhTrong){
        this.dsKenhID = []
      }
    },
    tsbtnGanNVQL_Click(){
      if (this.nhapmoi){
        this.$root.toastError("Chỉ có thể gán khi đã có hợp đồng khách hàng")
        return
      }
      this.$refs.AssignManagement.vtimkiem_hdkh_id = this.hdkh_id;
      this.$refs.AssignManagement.gan_hd = true;
      // this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupAssignSubManager.vue');
      this.$bvModal.show("AssignManagement")
      // this.$refs.popupAssignSubManager.show();
      // this.$root.toastError("Trỏ tới frmGanNVQL_TB form Gán nhân viên quản lý thuê bao: BA chưa mô tả")
      // var frm = new frmGanNVQL_TB();
      // frm.vtimkiem_hdkh_id = HDKH_ID;
      // frm.gan_hd = true;
      // frm.ShowDialog();
    },
    popupClosed: async function(data) {},
    async HT_DS_NhanVien_Theo_NhomLNV_Combobox(donvi_id, loainhom_nv_id){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/ds_nhanvien_theo_nhomlvn",{
        donvi_id:donvi_id,
        loainhom_nv_id:loainhom_nv_id
      })
      return rs.data
    },
    async NAP_COMBO(){
      try {
        await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/lay_ds_loai_hdkh")
          .then(res =>{
            this.dsLoaiHD = res.data
            if (this.dsLoaiHD && this.dsLoaiHD.length > 0)
              this.cboLoaiHD = this.dsLoaiHD[0].loaihdkh_id
          }).catch(e => {throw e})
        await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/lay_ds_tinhtrang_hd")
          .then(res =>{
            this.dsTinhTrangHD = res.data
            if (this.dsTinhTrangHD && this.dsTinhTrangHD.length > 0)
              this.cboTinhTrangHD = this.dsTinhTrangHD[0].tinhtranghd_id
          }).catch(e => {throw e})
      }catch (e) {

      }
    },
    TaoDuLieu(){
      let row = {}
      row.CHUCVU_GT = this.txt_ChucVu_GT
      row.SO_HD_KHDN = this.vsohd_tusinh
      row.SO_PL_KHDN = this.txtSoHD_Giay
      if (!this.nhapmoi)
        row.HDKH_ID = this.hdkh_id
      else row.HDKH_ID = 0
      if (this.dtp_NgayCap_GT)
        row.NGAYCAP_GT = this.ddmmyyyy(this.dtp_NgayCap_GT)
      row.NGUOI_GT = this.txt_Nguoi_GT
      if (this.txt_So_Phong)
        row.SO_PHONG = parseInt(this.txt_So_Phong+'',10)
      if (this.cboKenhThu) {
        row.KENHBAN_ID = parseInt(this.cboKenhThu+'',10)
      }
      row.SOGIAY_GT = this.txt_SoGiay_GT
      row.MA_NS = this.txtMaNSach
      if (this.vkieukh_id_ == 1){
        if (this.chkKenhTrong){
          row.KENH_PT = 1
          row.LOAIKENH = null
          row.KENH_ID = this.$auth.getNhanVienID()
        }else {
          row.KENH_PT = 2
          if (this.cboLoaiKenh)
            row.LOAIKENH = parseInt(this.cboLoaiKenh+'',10)
          if (this.cboKenhID)
            row.KENH_ID = parseInt(this.cboKenhID+'',10)
        }
        if (this.cboKhoi) {
          // row.KHOI_ID = this.cboKhoi
          row.KHOI_ID = parseInt(this.cboKhoi+'',10)
        }
      }
      if (this.ts_kt_tthdkhdn == 1){
        row.SO_HD_KHDN = this.txtSoHD
        row.SO_PL_KHDN = this.txtSoPL
        row.MA_KHDN = this.vma_kh
        row.GIATRI_HD = parseInt(this.spGiaTriHD+'',10)
        if (this.dtpNgayKy)
          row.NGAY_KY_HD = this.ddmmyyyy(this.dtpNgayKy)
        row.GIAY_UQ = this.txtGiayUQ
        if (this.cboLoaiHD && this.cboLoaiHD+'' != "-1")
          row.LOAIHDKH_ID = parseInt(this.cboLoaiHD+'',10)
        if (this.cboTinhTrangHD && this.cboTinhTrangHD+'' != "-1")
          row.TINHTRANGHD_ID = parseInt(this.cboTinhTrangHD+'',10)
      }
      if (this.is_bancheo &&this.cboChuQuan+'' != "-1")
      {
        chuquan_id = parseInt(this.cboChuQuan+'',10);
        row.CHUQUAN_ID = parseInt(this.cboChuQuan+'',10)
      }
      return row
    },
    async LaythongtinHDKH_SUB(){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/lay_thongtin_hdkh_sub", {hdkh_id: this.hdkh_id})
      return  rs.data
    },
    async layDsDbkhTheoKhId(khachhang_id) {
      let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_thongtin_dbkh_sub?khachhang_id=${khachhang_id}`)
      return response.data
    },
    async kiemtra_thongtin_sub_khdn(HDKH_ID, SO_HD,MA_KHDN,NGAY_KY){
      let rs = await this.$root.context.post("web-hopdong/thaydoithongtinkhuyenmai/kiemtra_thongtin_sub_khdn",{
        HDKH_ID:HDKH_ID,
        SO_HD:SO_HD,
        MA_KHDN:MA_KHDN,
        NGAY_KY:NGAY_KY,
      })
      return rs.data
    },
    async sinh_so_hd_khdn(kieu){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/sinh_so_hd_khdn",{
        kieu:kieu
      })
      return rs.data
    },
    async kiemtra_so_hd_khdn(so_hd){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/kiemtra_so_hd_khdn",{
        so_hd:so_hd
      })
      return rs.data
    },
    async lay_ds_khoi_loaikh(loaikh_id, kieu){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/lay_ds_khoi_loaikh",{
        loaikh_id:loaikh_id,
        kieu:kieu
      })
      return rs.data
    },
    async lay_ds_kenhban(){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/lay_ds_kenhban")
      return rs.data
    },
    async lay_ds_chuquan(){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/lay_ds_chuquan")
      return rs.data
    },
    ddmmyyyyhhmissToyyyymmddhhmiss(data){
      let datePath = data.split(' ')[0]
      let timePath = data.split(' ')[1] ? data.split(' ')[1] : ""
      let newDatePath = datePath.split('/').reverse().join('-');

      return newDatePath + ' ' + timePath
    },
    ddmmyyyy(date) {

      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y}`
    },
    getDateElements(date) {

      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
      }
    },
    clear() {
      this.hdkh_id = null
      this.nhapmoi = false
      this.vkieukh_id_ = 0
      this.mast = ""
      this.ts_kt_mstmns_kh = 0
      this.ts_kt_tthdkhdn = 0
      this.dichvuvt_id = null
      this.khachhang_id = 0
      this.vsohd_tusinh = ""
      this.vma_kh = ""
      this.nhanvien_vnpt_i = false
      this.is_bancheo = false
      this.ds_hdkh_sub = []
      this.vloaikh_id = 0
      this.text = null //this.Form.Text: tiêu đề form

      this.dateFormat = 'dd/MM/yyyy',
      this.layoutControlItem1 = { // Số phòng
        Visibility:true,
      }
      this.layoutControlItem5 = { // Mã ngân sách
        Visibility:true,
      }
      this.layoutControlItem14 = { // Số hợp đồng
        Visibility:true,
      }
      this.txt_So_Phong = null
      this.txtMaNSach = null
      this.layoutControlGroup2 = {
        Visibility:true
      }
      // layoutControlGroup2: variable
      this.txt_Nguoi_GT = null
      this.txt_ChucVu_GT = null
      this.txt_SoGiay_GT = null
      this.dtp_NgayCap_GT = null

      this.layoutControlGroup3 = {
        Visibility:true
      }
      // layoutControlGroup3 : variable
      this.txtSoHD_KHDN = null
      this.txtSoHD_Giay = null
      this.chkKenhTrong = false
      this.chkKenhNgoai = false
      this.chk = false
      this.cboLoaiKenh = 1
      this.cboKenhID = -1
      this.cboKhoi = null
      this.isEnableCboKhoi = false
      this.dsKhoi = []
      this.dsKenhThu=[],
      this.dsChuQuan=[],
      this.layoutHD_KHDN = {
        Visibility:false
      }
      // layoutHD_KHDN: variable
      this.txtSoHD = null
      this.txtSoPL = null
      this.spGiaTriHD = null
      this.dtpNgayKy = null
      this.txtGiayUQ = null
      this.cboLoaiHD = null
      this.cboTinhTrangHD = null
      this.category = {
          layoutControlGroup2: 0,
          layoutControlGroup3: 0,
          layoutHD_KHDN: 0,
      }
      this.dsKenhID = []
      this.dsLoaiHD = []
      this.dsTinhTrangHD = []

      this.KIEU_KH = {
        CA_NHAN:0,
        DOANH_NGHIEP:1,
      }

      this.isEnabledCboLoaiKenh = true
      this.isEnabledCboKenhID = true

      this.popupAssignSubManagerData = []
      this.popupComponentName = ''
      this.popupComponent = null

      //input form GanNVQL
      this.vtimkiem_hdkh_id = null
      this.gan_hd = true
    }

  }
}
</script>

<style>
.thongtin-khachhang-bosung .select2-container .select2-selection--single .select2-selection__rendered {
  white-space: initial;
  height: 28px;
}
</style>
