<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template src="./DoiSoatCA.html"></template>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import api from './PopUpApi'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi/ModalChonDiaChi.vue";
import ca from "@/modules/contract/complete_profile/CompleteProfileITVAS/API/caFuntions.js";

export default {
  components: {
    moment,
    DatePicker,
    api,
    ModalChonDiaChi
  },
  name: 'DoiSoatCA',
  data() {
    return {
      rerender: true,
      Loading: false,
      dateFormat: "DD/MM/YYYY",
      datetimeFormat: "DD/MM/YYYY hh:mm A",
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: {
      },
      tt_nd: {
        nhanvien_id:  this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        ten_dv: "",
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName(),
      },
      diachiLD: {
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
      controls: {
        dtpCMSNgayBD: null,
        dtpCMSNgayKT: null,
        cboKieuLD: {
          list: [],
          value: 0
        },
        cboDichVuVT: {
          list: [],
          value: 0
        },
        cboLoaiHinhThueBao: {
          list: [],
          value: 0
        },
        cboLoaiGT: {
          list: [],
          value: 0
        },
        cboGoiCuoc: {
          list: [],
          value: 0
        },
        cboMucDichCTS: {
          list: [],
          value: 0
        },
        cboLoaiHinhThueBao: {
          list: [],
          value: 0
        },

        grcThueBao: {
          headers: [
            {fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true},
            {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true},
            {fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true},
            // {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true},
          ],
          list: [],
        },
        grcThucTheCMS: {
          headers: [
            {fieldName: 'username', headerText: 'Mã thuê bao', allowFiltering: true},
            {fieldName: 'ee_name', headerText: 'Tên chủ thể', allowFiltering: true},
            {fieldName: 'uid_prefix', headerText: 'Prefix', allowFiltering: true},
            // {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'ee_name', headerText: 'Tên chủ thể', allowFiltering: true},
            {fieldName: 'email', headerText: 'Email', allowFiltering: true},
            {fieldName: 'subject_dn', headerText: 'Subject DN', allowFiltering: true},
            {fieldName: 'st', headerText: 'Tỉnh', allowFiltering: true},
          ],
          list: [],
        },
        grcChungThuSoCMS: {
          headers: [
            {fieldName: 'id', headerText: 'ID Chứng thư', allowFiltering: true},
            {fieldName: 'serial_number', headerText: 'Serial number', allowFiltering: true},
            {fieldName: 'overtime', headerText: 'Số tháng nợ', allowFiltering: true},
            // {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'time_validity', headerText: 'Số tháng', allowFiltering: true},
            {fieldName: 'status_name', headerText: 'Trạng thái', allowFiltering: true},
            {fieldName: 'request_config_name', headerText: 'Loại chứng thư', allowFiltering: true},
            {fieldName: 'certificate_profile_name', headerText: 'Gói cước (CMS)', allowFiltering: true},
            {fieldName: 'subject_dn', headerText: 'Subject DN', allowFiltering: true},
            {fieldName: 'subject_email', headerText: 'Email CTS', allowFiltering: true},
            {fieldName: 'targets', headerText: 'Mục đích', allowFiltering: true},
            {fieldName: 'rc_type_name', headerText: 'Kiểu cấp CTS', allowFiltering: true},
            {fieldName: 'start_time', headerText: 'Ngày BĐ', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'end_time', headerText: 'Ngày KT', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'reason', headerText: 'Lý do', allowFiltering: true},
          ],
          list: [],
        },

      }
    }
  },
  created: async function () {

  },
  mounted: function () {

  },
  watch: {

  },
  methods: {

    dialogClose() {},
    openDialog(data) {
      if(!data){
        return;
      }
      this.rerender = true;
      this.state =  {
        in_ma_gd: data.in_ma_gd,
        isKHDN: data.isKHDN,
        tinh_id: data.tinh_id,
        TopMost: data.TopMost,
        loaihd_id: data.loaihd_id,
        kieuld_id: data.kieuld_id,
        hdtb_id : data.hdtb_id
      };
      console.log("openDialog data", this.state);
      this.state.caHH = []
      this.controls = {};
      this.controls = {
        dtpCMSNgayBD: null,
        dtpCMSNgayKT: null,
        cboKieuLD: {
          list: [],
          value: 0
        },
        cboDichVuVT: {
          list: [],
          value: 0
        },
        cboLoaiHinhThueBao: {
          list: [],
          value: 0
        },
        cboLoaiGT: {
          list: [],
          value: 0
        },
        cboGoiCuoc: {
          list: [],
          value: 0
        },
        cboMucDichCTS: {
          list: [],
          value: 0
        },
        cboLoaiHinhThueBao: {
          list: [],
          value: 0
        },

        grcThueBao: {
          headers: [
            {fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true},
            {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true},
            {fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true},
            // {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true},
          ],
          list: [],
        },
        grcThucTheCMS: {
          headers: [
            {fieldName: 'username', headerText: 'Mã thuê bao', allowFiltering: true},
            {fieldName: 'ee_name', headerText: 'Tên chủ thể', allowFiltering: true},
            {fieldName: 'uid_prefix', headerText: 'Prefix', allowFiltering: true},
            // {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'ee_name', headerText: 'Tên chủ thể', allowFiltering: true},
            {fieldName: 'email', headerText: 'Email', allowFiltering: true},
            {fieldName: 'subject_dn', headerText: 'Subject DN', allowFiltering: true},
            {fieldName: 'st', headerText: 'Tỉnh', allowFiltering: true},
          ],
          list: [],
        },
        grcChungThuSoCMS: {
          headers: [
            {fieldName: 'id', headerText: 'ID Chứng thư', allowFiltering: true},
            {fieldName: 'serial_number', headerText: 'Serial number', allowFiltering: true},
            {fieldName: 'overtime', headerText: 'Số tháng nợ', allowFiltering: true},
            // {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'time_validity', headerText: 'Số tháng', allowFiltering: true},
            {fieldName: 'status_name', headerText: 'Trạng thái', allowFiltering: true},
            {fieldName: 'request_config_name', headerText: 'Loại chứng thư', allowFiltering: true},
            {fieldName: 'certificate_profile_name', headerText: 'Gói cước (CMS)', allowFiltering: true},
            {fieldName: 'subject_dn', headerText: 'Subject DN', allowFiltering: true},
            {fieldName: 'subject_email', headerText: 'Email CTS', allowFiltering: true},
            {fieldName: 'targets', headerText: 'Mục đích', allowFiltering: true},
            {fieldName: 'rc_type_name', headerText: 'Kiểu cấp CTS', allowFiltering: true},
            {fieldName: 'start_time', headerText: 'Ngày BĐ', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'end_time', headerText: 'Ngày KT', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'reason', headerText: 'Lý do', allowFiltering: true},
          ],
          list: [],
        },

      };
      this.$refs.dialogDoiSoatCA.show();
      this.pageLoad();
    },
    hideDialog () {
      this.$refs.dialogDoiSoatCA.hide()
      this.rerender = false;
    },

    async pageLoad(){
      try{
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
        let data = [];
        //LoadCboTocDo();
        data = this.GetData(await api.sp_lay_tocdo_doisoatca(this.axios, {}))
        if(data && data.length > 0){
          this.controls.cboGoiCuoc.list = data.map((x) => {return {id: x.tocdo_id, text: x.thuonghieu}});
          this.controls.cboGoiCuoc.value = this.controls.cboGoiCuoc.list[0].id
        }

        // LoadCboLoaiGT();
        data = this.GetData(await api.sp_lay_loaigt_doisoatca(this.axios, {}))
        if(data && data.length > 0){
          this.controls.cboLoaiGT.list = data.map((x) => {return {id: x.loaigt_id, text: x.loai_gt}});
          if (this.controls.cboLoaiGT.list && this.controls.cboLoaiGT.list.length > 0) {
            this.controls.cboLoaiGT.value = this.controls.cboLoaiGT.list[0].id
          }

        }

        // LoadCboDV();
        data = this.GetData(await api.sp_lay_dichvuvt_doisoatca(this.axios, {}))
        if(data && data.length > 0){
          this.controls.cboDichVuVT.list = data.map((x) => {return {id: x.dichvuvt_id, text: x.ten_dvvt}});
          if (this.controls.cboDichVuVT.list && this.controls.cboDichVuVT.list.length > 0) {
            this.controls.cboDichVuVT.value = this.controls.cboDichVuVT.list[0].id
          }
        }

        // LoadCboMucDich();
        data = this.GetData(await api.sp_lay_mucdich_doisoatca(this.axios, {}))
        if(data && data.length > 0){
          this.controls.cboMucDichCTS.list = data.map((x) => {return {id: x.mucdich_ca_id, text: x.mucdich_sd}});
          if (this.controls.cboMucDichCTS.list && this.controls.cboMucDichCTS.list.length > 0) {
            this.controls.cboMucDichCTS.value = this.controls.cboMucDichCTS.list[0].id
          }
        }

        // LoadCboKieuLD();
        data = this.GetData(await api.sp_lay_kieuld_doisoatca(this.axios, {}))
        if(data && data.length > 0){
          this.controls.cboKieuLD.list = data.map((x) => {return {id: x.kieuld_id, text: x.ten_kieuld}});
          if (this.controls.cboKieuLD.list && this.controls.cboKieuLD.list.length > 0) {
            this.controls.cboKieuLD.value = this.controls.cboKieuLD.list[0].id
          }
        }

        // LoadCboLoaiHinhTB();
        data = this.GetData(await api.sp_lay_loaitb_doisoatca(this.axios, {}))
        if(data && data.length > 0){
          this.controls.cboLoaiHinhThueBao.list = data.map((x) => {return {id: x.kieuld_id, text: x.ten_kieuld}});
          if (this.controls.cboLoaiHinhThueBao.list && this.controls.cboLoaiHinhThueBao.list.length > 0) {
            this.controls.cboLoaiHinhThueBao.value = this.controls.cboLoaiHinhThueBao.list[0].id
          }
        }

        this.controls.txtMaGD = this.state.in_ma_gd;
        if (this.controls.txtMaGD && this.controls.txtMaGD != "")
        {
            await this.HienThiThongTinHDTB();
        }
      }catch(ex){
        commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async HienThiThongTinHDTB(){
      try{
        if (!this.controls.txtMaGD || this.controls.txtMaGD == ""){
          return;
        }
        this.loading(true)
        let dt = []
        let req = {}
        if (this.state.loaihd_id == 24)
        {
            dt = [];
            //TODO: chưa có api
        }
        else {
          req = {
              "in_hdtb_id": this.state.hdtb_id,
              "in_ma_gd": this.controls.txtMaGD,
              "in_loaihd_id": this.state.loaihd_id,
              "in_donvi_id": 0,
              "in_tthd_id": 0,
              "in_nhanvien_id": 0,
              "in_donvi_dl_id": 0
          };
          console.log(req);
          dt = this.GetData(await api.lay_ds_hopdong_xemcms(this.axios, req));
        }
        if (this.state.loaihd_id == 24)
        {
            this.state.kieuld_id = Number(this.controls.cboKieuLD.value);
        }
        let row_init = dt.filter(x => x.kieuld_id == this.state.kieuld_id);
        if(row_init && row_init.length > 0){
          this.controls.grcThueBao.list = row_init;
          let danhsachHopDongThueBao = row_init[0];
          this.state.danhsachHopDongThueBao = danhsachHopDongThueBao;
          //thong tin khach hang
          this.controls.txtMaGD = danhsachHopDongThueBao.ma_gd;
          this.controls.txtMaKH = danhsachHopDongThueBao.ma_kh;
          this.controls.txtTenKH = danhsachHopDongThueBao.ten_kh;
          this.controls.dtpNgayYeuCau = moment(danhsachHopDongThueBao.ngay_yc, "YYYY-MM-DD HH:mm:ss").format(this.datetimeFormat);
          this.controls.dtpNgayLapHD = moment(danhsachHopDongThueBao.ngaylap_hd, "YYYY-MM-DD HH:mm:ss").format(this.datetimeFormat);
          this.controls.txtMaHD = danhsachHopDongThueBao.ma_hd;
          this.controls.txtDiaChiKH = danhsachHopDongThueBao.diachi_kh;
          //end thong tin khach hang

          //thong tin thue bao
          this.controls.txtMaTB = danhsachHopDongThueBao.ma_tb;
          this.controls.txtTenTB = danhsachHopDongThueBao.ten_tb;
          this.controls.txtDiaChiLD = danhsachHopDongThueBao.diachi_ld;
          this.controls.txtTrangThaiTB = danhsachHopDongThueBao.trangthai_hd;

          this.LoadThongTinHDTBCNTT(danhsachHopDongThueBao.hdtb_id);
          this.LoadThongTinHDTB_SUB(danhsachHopDongThueBao.hdtb_id);

          var data = await commonFn.lay_ds_diachi_theo_hdtbid(this.axios, danhsachHopDongThueBao.hdtb_id);
          if(data && data.length > 0){
            this.diachiLD.DIACHI = data[0].diaChi;
            this.diachiLD.TINH_ID = data[0].tinhLdId;
            this.diachiLD.QUAN_ID = data[0].quanLdId;
            this.diachiLD.PHUONG_ID = data[0].phuongLdId;
            this.diachiLD.PHO_ID = data[0].phoId;
            this.diachiLD.SO_NHA = data[0].soNhaLd;
            this.diachiLD.DIACHI_ID = data[0].diaChiLdId;
            this.diachiLD.KHU_ID = data[0].khuLdId;
            this.diachiLD.DAC_DIEM_ID = data[0].dacDiemLdId;
            this.diachiLD.Lng = data[0].kinhDoLd;
            this.diachiLD.Lat = data[0].viDoLd;
          }

        }
        else
        {
            this.$toast.error("Không tìm được hợp đồng");
        }
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async LoadThongTinHDTBCNTT(hdtb_id){
      let dt = this.GetData(await api.lay_ds_hdtb_cntt_theo_hdtb_id(this.axios, {in_hdtb_id: hdtb_id}))
      if(!dt || dt.length <= 0){
        this.$toast.error("Không lấy được thông tin hợp đồng CNTT");
        return;
      }

      var dshdtb_cntt = dt[0];
      this.controls.txtSerial = dshdtb_cntt.serial;
      this.controls.txtSDT = dshdtb_cntt.so_dt;
      this.controls.txtMST = dshdtb_cntt.mst;
      this.controls.cboMucDichCTS.value = dshdtb_cntt.mucdich_ids;
      this.controls.dtpNgayBD = moment(dshdtb_cntt.ngay_bd, "YYYY-MM-DD 00:00:00").format(this.dateFormat);
      this.controls.dtpNgayKT = moment(dshdtb_cntt.ngay_kt, "YYYY-MM-DD 00:00:00").format(this.dateFormat);
      this.controls.txtCTSID = dshdtb_cntt.cts_id;
      this.controls.txtUserName = dshdtb_cntt.username;
      this.controls.txtThucTheID = dshdtb_cntt.ee_id;
      this.controls.txtSoThangSD = dshdtb_cntt.so_thang;
      this.controls.txtEmail = dshdtb_cntt.email;
      this.controls.txtCTSID = dshdtb_cntt.cts_id;
      this.controls.cboGoiCuoc.value = dshdtb_cntt.tocdo_id;
    },
    async LoadThongTinHDTB_SUB(hdtb_id){
      let req = {
        hdtb_id: hdtb_id
      }
      let dt = this.GetData(await api.sp_lay_hdtbsub_doisoatca(this.axios, req));
      if(!dt || dt.length <= 0){
        this.$toast.error("Không lấy được thông tin HDTB_SUB");
        return;
      }

      let hdtb_sub = dt[0];
      this.controls.txtSoGT = hdtb_sub.so_gt;
      this.controls.cboLoaiGT.value = hdtb_sub.loaigt_id;
      this.controls.dtpNgayHetHan = moment(hdtb_sub.ngayhethan_gt, "YYYY-MM-DD HH:mm:ss").format(this.datetimeFormat);
      this.controls.txtChucDanh = hdtb_sub.chucdanh;
    },
    async tsbDoiSoat_Click(){
      if (!this.controls.txtUserName || this.controls.txtUserName == "" || this.controls.txtUserName != this.controls.txtCMSUserName)
      {
          this.$toast.error("Username không khớp hoặc không có giá trị");
          return false;
      }
      if (!this.controls.txtSerial || this.controls.txtSerial == "" || this.controls.txtSerial != this.controls.txtCMSSerial)
      {
          this.$toast.error("Serial không khớp hoặc không có giá trị");
          return false;
      }
      if (!this.controls.txtCTSID || this.controls.txtCTSID == "" || this.controls.txtCTSID != this.controls.txtIDChungThuSoCMS)
      {
          this.$toast.error("Không khớp ID chứng thư số");
          return false;
      }
      if (!this.controls.txtThucTheID || this.controls.txtThucTheID == "" || this.controls.txtThucTheID != this.controls.txtIDThucTheCMS)
      {
          this.$toast.error("Không khớp ID thực thể hoặc ID thực thể rỗng");
          return false;
      }
      if (!this.controls.dtpNgayBD || this.controls.dtpNgayBD == "" || this.controls.dtpNgayBD != this.controls.dtpCMSNgayBD)
      {
        console.log(this.controls.dtpNgayBD, this.controls.dtpCMSNgayBD)
          this.$toast.error("Ngày bắt đầu không khớp");
          return false;
      }
      if (!this.controls.dtpNgayKT || this.controls.dtpNgayKT == "" || this.controls.dtpNgayKT != this.controls.dtpCMSNgayKT)
      {
        console.log(this.controls.dtpNgayKT, this.controls.dtpCMSNgayKT)
          this.$toast.error("Ngày kết thúc không khớp");
          return false;
      }
      if (!this.controls.txtTenTB || this.controls.txtTenTB == "" || this.controls.txtTenTB != this.controls.txtCMSTenChuThe)
      {
          this.$toast.error("Không khớp tên chủ thể, hoặc tên chủ thể rỗng");
          return false;
      }

      this.$toast.success("Dữ liệu đối soát đã khớp");
    },
    async btnCapNhatHD_Click(){
      if(this.controls.grcThucTheCMS.list.length == 0 || this.controls.grcChungThuSoCMS.list.length == 0)
      {
        return this.$toast.error("Không có Thông tin thực thể hoặc Thông tin chứng thư số để cập nhật")
      }
      try{

        let req = {
          vhdtb_id: this.state.danhsachHopDongThueBao.hdtb_id,
          vcts_id: this.controls.txtIDChungThuSoCMS,
          vee_id: this.controls.txtIDThucTheCMS,
          vusername: this.controls.txtCMSUserName,
          vserial: this.controls.txtCMSSerial,
          vcert_id: this.controls.txtIDChungThuSoCMS,
          vemail: this.controls.txtCMSEmail,
          vmst: this.controls.txtCMSMaSoThue,
          vso_dt: this.controls.txtCMSSoDT
        }
        this.loading(true)
        let kq = this.GetData(await api.fn_capnhat_chuanhoa_xemcms(this.axios, req));
        if (kq != "ok")
        {
            this.$toast.error(kq);
        }
        else
        {
          this.$toast.success("Cập nhật thành công!");
        }
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async gdvThuTheCMS_FocusedRowChanged(e){
      try{
        if (!e) return;
        let row = e;
        if (this.state.caHH && this.state.caHH.meta_data){
          this.controls.grcChungThuSoCMS.list = this.state.caHH.meta_data.list.find(x => x.ee_id == row.ee_id).cert_list;
        }

        // let grid = this.$refs.gdvThuTheCMS;
        // let seletedRows = grid.getSelectedRecords();
        // let row = seletedRows[0];

        this.controls.txtCMSUserName = row.username;
        this.controls.txtCMSTenChuThe = row.ee_name;
        this.controls.txtCMSLoaiGT = row.uid_prefix;
        if (this.controls.txtCMSLoaiGT && this.controls.txtCMSLoaiGT.toLowerCase() === "mst")
        {
          this.controls.txtCMSMaSoThue = row.uid_subfix;
        }
        else
        {
          this.controls.txtCMSSoGT = row.uid_subfix;;
        }
        this.controls.txtIDThucTheCMS = row.ee_id;
        this.controls.txtCMSSoDT = row.contact_phone;
        this.controls.txtCMSDiaChi = row.l + ", " + row.st;

      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async gdvChungThuSoCMS_FocusedRowChanged(e){
      try{
        console.log("gdvChungThuSoCMS_FocusedRowChanged", e)
        if (!e) return;
        //let grid = this.$refs.gdvThuTheCMS;
        //let seletedRows = grid.getSelectedRecords();
        let row = e;
        this.controls.txtCMSSerial = row.serial_number;
        this.controls.txtCMSEmail = row.subject_email;
        this.controls.txtCMSSoThang = row.time_validity;
        this.controls.dtpCMSNgayBD = moment(row.start_time, "YYYY-MM-DDTHH:mm:ss").format(this.dateFormat);
        this.controls.dtpCMSNgayKT = moment(row.end_time, "YYYY-MM-DDTHH:mm:ss").format(this.dateFormat);
        this.controls.txtGoiCuocCMS = row.certificate_profile_name;
        this.controls.txtMucDichCMS = row.targets;
        this.controls.txtCMSDoDaiKhoa = row.keylength;
        this.controls.txtIDChungThuSoCMS = row.id;
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async txtMaGD_KeyPress(){
      this.HienThiThongTinHDTB();
    },
    async txtCMSEmail_KeyPress(){
      let serial = this.controls.txtCMSSerial ? this.controls.txtCMSSerial : "";
      let ten_chu_the = this.controls.txtCMSTenChuThe ? this.controls.txtCMSTenChuThe : "";
      let email = this.controls.txtCMSEmail ? this.controls.txtCMSEmail : "";
      let username = this.controls.txtCMSUserName ? this.controls.txtCMSUserName : "";
      let mst = this.controls.txtCMSMaSoThue ? this.controls.txtCMSMaSoThue : "";

      this.CMSTimKiemThueBaoHienHuu(serial, ten_chu_the, email, username, mst);
    },
    async txtCMSTenChuThe_KeyPress(){
      let serial = this.controls.txtCMSSerial ? this.controls.txtCMSSerial : "";
      let ten_chu_the = this.controls.txtCMSTenChuThe ? this.controls.txtCMSTenChuThe : "";
      let email = this.controls.txtCMSEmail ? this.controls.txtCMSEmail : "";
      let username = this.controls.txtCMSUserName ? this.controls.txtCMSUserName : "";
      let mst = this.controls.txtCMSMaSoThue ? this.controls.txtCMSMaSoThue : "";

      this.CMSTimKiemThueBaoHienHuu(serial, ten_chu_the, email, username, mst);
    },
    async txtCMSSerial_KeyPress(){
       let serial = this.controls.txtCMSSerial ? this.controls.txtCMSSerial : "";
      let ten_chu_the = this.controls.txtCMSTenChuThe ? this.controls.txtCMSTenChuThe : "";
      let email = this.controls.txtCMSEmail ? this.controls.txtCMSEmail : "";
      let username = this.controls.txtCMSUserName ? this.controls.txtCMSUserName : "";
      let mst = this.controls.txtCMSMaSoThue ? this.controls.txtCMSMaSoThue : "";

      this.CMSTimKiemThueBaoHienHuu(serial, ten_chu_the, email, username, mst);
    },
    async txtCMSMaSoThue_KeyPress(){
      let serial = this.controls.txtCMSSerial ? this.controls.txtCMSSerial : "";
      let ten_chu_the = this.controls.txtCMSTenChuThe ? this.controls.txtCMSTenChuThe : "";
      let email = this.controls.txtCMSEmail ? this.controls.txtCMSEmail : "";
      let username = this.controls.txtCMSUserName ? this.controls.txtCMSUserName : "";
      let mst = this.controls.txtCMSMaSoThue ? this.controls.txtCMSMaSoThue : "";

      this.CMSTimKiemThueBaoHienHuu(serial, ten_chu_the, email, username, mst);
    },
    async txtCMSUserName_KeyPress(){
      let serial = this.controls.txtCMSSerial ? this.controls.txtCMSSerial : "";
      let ten_chu_the = this.controls.txtCMSTenChuThe ? this.controls.txtCMSTenChuThe : "";
      let email = this.controls.txtCMSEmail ? this.controls.txtCMSEmail : "";
      let username = this.controls.txtCMSUserName ? this.controls.txtCMSUserName : "";
      let mst = this.controls.txtCMSMaSoThue ? this.controls.txtCMSMaSoThue : "";

      this.CMSTimKiemThueBaoHienHuu(serial, ten_chu_the, email, username, mst);
    },
    async CMSTimKiemThueBaoHienHuu(serial, ten_chu_the, email, username, mst){
      try{
        this.loading(true)
        let dt_chungchi_ssl = this.GetData(await api.sp_lay_ds_chungchi_ssl(this.axios, {params: {chungchi_id: 1}}));
        if(!dt_chungchi_ssl || dt_chungchi_ssl.length <= 0){
            throw 'Không lấy được chứng chỉ SSL!';
        }

        let chung_chi_ssl_b64 = dt_chungchi_ssl[0]["command"];

        /* CAServices cas = new CAServices(CAConstanst.CALayThongTinCAHienHuuEndpoint, x509);
        caHH = cas.LayThongTinCAHienHuu(serial, ten_chu_the, email, username, mst);

        Invoke(new MethodInvoker(delegate()
        {
            progressbar.Properties.Stopped = true;
            grcChungThuSoCMS.DataSource = null;
            grcThucTheCMS.DataSource = caHH.meta_data.list;
            layoutControlGroup4.Enabled = true;
            gdvThuTheCMS.BestFitColumns();
        })); */
        let data = await ca.LayThongTinCAHienHuu(this, serial, ten_chu_the, email, username, mst);
        if (data.status === "ERROR") {
          throw data.description;
        }
        this.state.caHH = data;
        this.controls.grcChungThuSoCMS.list = [];
        this.controls.grcThucTheCMS.list = this.state.caHH.meta_data.list;
      }catch(ex){
        console.log("ex", ex)
        commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    getDiaChi(data, datatype) {
      this[datatype].DIACHI = data.diachimoi;
      this[datatype].TINH_ID = data.tinh_id;
      this[datatype].QUAN_ID = data.quan_id;
      this[datatype].PHUONG_ID = data.phuong_id;
      this[datatype].PHO_ID = data.pho_id;
      this[datatype].AP_ID = data.ap_id;
      this[datatype].KHU_ID = data.khu_id;
      this[datatype].DAC_DIEM_ID = data.dac_diem_id;
      this[datatype].SO_NHA = data.so_nha;
      this[datatype].Lat = data.lat;
      this[datatype].Lng = data.lng;
      console.log(data, this[datatype]);
    },
    async getDiaChiLD(data) {
      this.getDiaChi(data, "diachiLD");
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '204' || response.data.errorCode === 0) {
        return response.data.data
      } else {
        console.log(response.data.error_code)
        console.log(response.data.faultString)
        if(response.data && response.data.faultString){
          throw response.data.faultString;
        }
      }
      return null
    },

    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    LowerCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toLowerCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
    UpperCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toUpperCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },

    btnClear_info() {
      this.controls.txtCMSUserName = '';
      this.controls.txtCMSMaSoThue = '';
      this.controls.txtCMSSerial = '';
      this.controls.txtCMSTenChuThe = '';
      this.controls.txtCMSSoDT = '';
      this.controls.txtCMSEmail = '';
    }
  },
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
