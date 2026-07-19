<template src="./RecallUnusedPort.html"></template>
<style scoped src="./RecallUnusedPort.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import API from './API'
import FormSearch from "./FormSearch";
import EventBus from "@/utils/eventBus";
import {TrangThaiHD, LoaiHopDong, DichVuVienThong, LoaiHinhTB} from "@/const/enums";
import giaophieunhanvien from "@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";
import VatTuThueBao from "@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao";
import Const from './const';
import moment from "moment";
import {DBConstants} from "../../../const/enums";
import CapNhatDonVi from "./FormCapNhatDV/CapNhatDonVi.vue";
import NhapText from './FormNhapText/NhapText'
import ExportDataModal from "../../contract/profile/ExportData/ExportDataModal";
//them comment de tao merge request
export default {
  components: {
    breadcrumb,
    FormSearch,
    giaophieunhanvien,
    VatTuThueBao,
    CapNhatDonVi,
    NhapText,
    ExportDataModal
  },
  name: 'RecallUnusedPort',
  data() {
    return {
      header: {
        title: 'Host - Thu hồi cổng không sử dụng',
        list: [
          {name: 'Cập nhật KQTC', link: {name: 'Ui.cards'}},
          {name: 'Host - Thu hồi cổng không sử dụng', link: {name: 'Ui.cards'}},
        ]
      },
      target: '.main-wrapper',
      frmData: {
        customerInfo: {
          ma_gd: '',
          dvvt_id: DichVuVienThong.ADSL,
          loai_hd_id: 0,
          txtMaGD: '',
          txtSoMayAcc: '',
          txtSoMayAccMoi: '',
          txtTenTB: '',
          txtDiaChiLD: '',
          txtKieuLD: '',
          txtSoAo: '',
          lblTamDung_No: 'Tạm dừng theo yêu cầu/nợ cước',
        },
        hoanCongInfo: {
          txtGhiChu: '',
          txtNDGiao: '',
          txtNoiDungTH: '',
          nguoi_giao_viec: 0,
          ngay_giao_viec: '',
          ngay_gv_form: '',
          ngay_hoan_thanh: '',
          kieu_lap_dat: 0,
          chkPhanHoi: false,
          enableChkPhanHoi: true,
          chk_ko_thu_hoi: false,
          chk_ngay_gv: true,
          chk_ngay_th: false,
          chk_kieu_lap_dat: false
        },
      },
      tthd_id: 0,
      huonggiao_id: 0,
      vkieu_km: 0,
      hdtb_id: 0,
      loaihd_id: 0,
      phieu_id: 0,
      hdtb_cha_id: 0,
      thuebao_cha_id: 0,
      matb_link: '',
      tag: '',
      dsPhieu: [],
      dsThietBi: [],
      dsNhanVien: [],
      dsNguoiGiaoViec: [],
      dsKieuLD: [],
      cols: Const.cols,
      btnVisible: {
        tsbtnGiaoPhieu: true,
        tsbtnHoanThanh: true,
        btnCapNhatVT: true,
      },
      btnEnabled: {
        tsbtnHoanThanh: true
      },
      hostInfo: {
        txtHostLD: '',
        txtHostVL: '',
        txtHostLD_Moi: '',
        txtNE: '',
        loai_cap_id: 0,
        txtTuCapGoc: '',
        txtDoiCapGoc: '',
        txtCapGoc: '',
        txtHopCapNgon: '',
        txtTuCapNgon: '',
        txtDoiCapNgon: '',
        txtCapNgon: '',
        txtTuyenCap: '',
        txtLienTu: '',
        txtSoMetDay: 0,
        txtVeTinh: '',
      },
      current_phieu_id: -1,
      nhanvien_tc_id: -1,
      congviec_th: '',
      ma_tb_cur: '',
      thuebao_id: 0,
      loaitb_id: 0,
      lblThuHoi: 'Thu hồi thiết bị tại giao dịch',
      sochinh: false,
      nd_thuchien: '',
      ne_id_cu: 0,
      modelVatTu: {
        hdtb_id: 0,
        phieu_id: 0,
        loaihd_id: 0,
        thuebao_id: 0
      },
      modelCapNhatDV: {
        phieu_id: 0
      },
      fileNameExcel: '',
      currentItem: null
    }
  },
  created() {
    this.xuLyTag();

    window.addEventListener('keydown', (e) => {
      if (e.key === 'F5') {
        this.btnLayTT();
      } else if (e.key === 'F9') {
        this.CapNhat();
      }
    });
  },
  mounted() {
    EventBus.$on('loadedCombobox', () => this.frm_load())
    EventBus.$on('onEnterSoMayAcc', () => this.onEnterSoMayAcc())
    EventBus.$on('updateNDTH', () => this.updateNDTH())
    // this.frm_load();
  },
  destroyed() {
    EventBus.$off('loadedCombobox')
    EventBus.$off('onEnterSoMayAcc')
    EventBus.$off('updateNDTH')
  },
  watch: {
    'frmData.customerInfo.dvvt_id': async function (val) {
      await this.getDsPhieu();
    },
    'frmData.customerInfo.loai_hd_id': async function (val) {
      this.frmData.customerInfo.lblTamDung_No = '';
      await this.getDsPhieu();
    }
  },
  computed: {
    dsPhieuLength() {
      if (this.dsPhieu == null) {
        return 0;
      }
      return this.dsPhieu.length;
    }
  },
  methods: {
    async frm_load() {
      try {
        this.loading(true)

        let dsNguoiGiaoViec = this.getCbNguoiGiaoViec();
        let dsKieuLD = this.getCbKieuLapDat();
        let dsPhieu = this.getDsPhieu()
        await Promise.all([dsNguoiGiaoViec, dsKieuLD, dsPhieu]);

        this.loading(false)
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    reload() {
      this.frm_load();
    },

    clear() {
      this.ne_id_cu = 0;
      this.hostInfo = {
        ...this.hostInfo,
        txtHostLD: '',
        txtHostVL: '',
        txtHostLD_Moi: '',
        txtNE: '',
        txtTuCapGoc: '',
        txtDoiCapGoc: '',
        txtCapGoc: '',
        txtHopCapNgon: '',
        txtTuCapNgon: '',
        txtDoiCapNgon: '',
        txtCapNgon: '',
        txtTuyenCap: '',
        txtLienTu: '',
        txtSoMetDay: 0,
      }

      this.vkieu_km = 0;
      this.frmData.customerInfo = {
        ...this.frmData.customerInfo,
        ma_gd: '',
        txtMaGD: '',
        txtSoMayAcc: '',
        txtSoMayAccMoi: '',
        txtTenTB: '',
        txtDiaChiLD: '',
        txtKieuLD: '',
        txtSoAo: '',
      }

      this.frmData.hoanCongInfo = {
        ...this.frmData.hoanCongInfo,
        chk_ngay_th: false,
        ngay_hoan_thanh: moment().format('DD/MM/YYYY HH:mm a'),
        chk_ngay_gv: false,
        ngay_giao_viec: moment().format('DD/MM/YYYY HH:mm a'),
        txtGhiChu: '',
        txtNDGiao: ''
      }

      this.dsPhieu = this.dsNhanVien = this.dsThietBi = [];

      this.btnEnabled.tsbtnHoanThanh = false;

      this.hdtb_id = 0;
      this.loaihd_id = 0;
      this.phieu_id = 0;
      this.hdtb_cha_id = 0;
      this.thuebao_cha_id = 0;

      EventBus.$emit('clear');
    },

    xuLyTag() {
      //host: 9+3001
      if (this.$route.query.tag != null && this.$route.query.tag !== '' && this.$route.query.tag.length > 0) {
        this.tag = this.$route.query.tag;
      } else if (this.$route.params.tag != null && this.$route.params.tag !== '' && this.$route.params.tag.length > 0) {
        this.tag = this.$route.params.tag;
      }

      let str = "";
      try {
        let {tthd_id, tag} = this;
        if (tthd_id === 0) {
          if (tag.toString() !== '') {
            str = tag.toString();
            this.tthd_id = parseInt(str.substring(0, str.indexOf("+")));
            this.huonggiao_id = parseInt(str.substring(str.indexOf("+") + 1));
          } else {
            this.tthd_id = TrangThaiHD.DANG_THI_CONG;
          }
        }
      } catch (e) {
        this.tthd_id = 0;
        this.huonggiao_id = 0;
      }

      this.hienThiGiaoDien(this.tthd_id);
    },

    hienThiGiaoDien(tthd) {
      this.btnVisible.tsbtnGiaoPhieu = this.btnVisible.tsbtnHoanThanh = this.btnVisible.btnCapNhatVT = false;
      if (tthd === TrangThaiHD.KHAIBAO_TONGDAI) {
        this.btnVisible.tsbtnHoanThanh = true;
        this.header.title = "HOST - THU HỒI CỔNG KHÔNG SỬ DỤNG";
      } else if (tthd === TrangThaiHD.DIEUHANH_THI_CONG) {
        this.btnVisible.tsbtnHoanThanh = true;
        this.header.title = "ĐHTC - THU HỒI CỔNG KHÔNG SỬ DỤNG";
      }
      if (tthd === TrangThaiHD.DANG_THI_CONG) {
        this.btnVisible.tsbtnHoanThanh = true;
        this.btnVisible.btnCapNhatVT = true;
        this.header.title = "TỔ THI CÔNG - THU HỒI THIẾT BỊ ĐẦU CUỐI, DÂY THUÊ BAO";
      }
    },

    //get data
    async getDsPhieu() {
      this.loading(true);
      try {
        let {dvvt_id, loai_hd_id} = this.frmData.customerInfo;
        let hd_id = parseInt(loai_hd_id);
        if (dvvt_id > 0) {
          if (hd_id === LoaiHopDong.DAT_MOI) {
            this.dsPhieu = await this.getDsPhieuThuHoiDungThu();
          } else if (hd_id === LoaiHopDong.THAY_DOI_TOCDO_ADSL ||
            hd_id === LoaiHopDong.THAYDOI_THONGSO_MYTV) {
            this.dsPhieu = await this.getDsPhieuGiaoThuHoiTbi();
          } else {
            this.dsPhieu = await this.getDsPhieuGiaoTCKhiDC();
          }

          if (this.dsPhieu === undefined || this.dsPhieu === null || this.dsPhieuLength <= 0) {
            this.clear();
          } else {
            // this.setCurrentRow(this.getIndexLoad());
          }
        } else {
          this.clear();
        }
      } catch (err) {
        this.$toast.error(err.message);
      } finally {
        this.loading(false);
      }
    },

    async getDsPhieuThuHoiDungThu() {
      try {
        let rs = await API.lay_ds_phieu_thuhoi_dungthu(this.axios, this.getReqData());
        return rs.data.data;
      } catch (err) {
        this.$toast.error(err.message);
      }

      return [];
    },

    async getDsPhieuGiaoThuHoiTbi() {
      try {
        let rs = await API.lay_ds_phieu_giao_thuhoi_tbi(this.axios, this.getReqData());
        return rs.data.data;
      } catch (err) {
        this.$toast.error(err.message);
      }

      return [];
    },

    async getDsPhieuGiaoTCKhiDC() {
      try {
        let rs = await API.lay_ds_phieu_giao_td_khi_dc(this.axios, this.getReqData());
        return rs.data.data;
      } catch (err) {
        this.$toast.error(err.message);
      }

      return [];
    },

    getReqData() {
      let {ma_gd, dvvt_id, loai_hd_id} = this.frmData.customerInfo;
      return {
        vdichvuvt_id: dvvt_id,
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vma_gd: ma_gd != '' ? ma_gd : 0,
        vphieu_id: 0,
        vloaihd_id: loai_hd_id,
        vhuonggiao_id: this.huonggiao_id
      }
    },

    getIndexLoad() {
      {
        try {
          if (this.matb_link === '') return 0;
          let {dsPhieu} = this;
          if (dsPhieu <= 1) return 0;
          for (let i = 0; i < dsPhieu.length; i++) {
            if (dsPhieu[i].ma_tb.toString() === this.matb_link)
              return i;
          }
        } catch (e) {
          return 0;
        }
        return 0;
      }
    },

    getSelectedRecords() {
      return this.$refs.dsPhieu.getSelectedRecords();
    },

    setCurrentRow(i) {
      this.$refs.dsPhieu.setCurrentSelectedRow(i);
    },

    //data grid event
    selectedItemsChanged(items) {
      try {
        this.loading(true);
        if (items.length <= 0) {
          return;
        }

        let data = items[0];
        if (this.dsPhieuLength <= 0 && data.phieu_id > 0) {
          this.current_phieu_id = -1;
          this.currentItem = null;
          return;
        }

        if (this.current_phieu_id !== data.phieu_id) {
          this.currentItem = data;
          this.fillData(data);
        }
      } catch (e) {
      } finally {
        this.loading(false);
      }
    },

    async fillData(data) {
      if (data == null) {
        this.$toast.error('Chưa chọn phiếu');
        return;
      }
      this.current_phieu_id = data.phieu_id;
      this.nd_thuchien = '';
      await this.getDsNhanVienTheoPhieuId();
      await this.hienThiTTHopDongKH(data);
    },

    async getDsNhanVienTheoPhieuId() {
      try {
        let rs = await API.lay_dsnv_theo_phieu(this.axios, this.current_phieu_id);
        this.dsNhanVien = rs.data.data;

        if (this.dsNhanVien != null && this.dsNhanVien.length > 0) {
          let data = this.dsNhanVien[0];
          this.nhanvien_tc_id = data.nhanvien_th_id;
          this.congviec_th = data.nhiemvu;

          this.frmData.hoanCongInfo.chk_ngay_gv = true;
          this.frmData.hoanCongInfo.ngay_giao_viec = moment(data.ngaygiao, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm a');
          this.frmData.hoanCongInfo.nguoi_giao_viec = data.nhanvien_giao_id;
        } else {
          this.dsNhanVien = [];
          this.nhanvien_tc_id = -1;
          this.congviec_th = '';

          this.frmData.hoanCongInfo.chk_ngay_gv = false;
          this.frmData.hoanCongInfo.ngay_giao_viec = moment().format('DD/MM/YYYY HH:mm a');
          this.frmData.hoanCongInfo.nguoi_giao_viec = this.$root.token.getNhanVienID();
        }
        return rs.data.data;
      } catch (err) {
        this.$toast.error(err.message);
      }

      return [];
    },

    async hienThiTTHopDongKH(data) {
      console.log(data);
      this.btnEnabled.tsbtnHoanThanh = false;
      this.frmData.hoanCongInfo.chk_ko_thu_hoi = false;
      this.hdtb_cha_id = 0;
      this.ma_tb_cur = "";

      this.hdtb_id = data.hdtb_id;
      this.loaihd_id = data.loaihd_id;
      this.phieu_id = data.phieu_id;
      this.ma_tb_cur = data.ma_tb;
      this.thuebao_id = data.thuebao_id;
      this.loaitb_id = data.loaitb_id;

      await this.get_ds_thietbi_by_hdtb_id();

      let {customerInfo} = this.frmData
      this.frmData.customerInfo = {
        ...customerInfo,
        ma_gd: data.ma_gd,
        txtSoMayAcc: data.ma_tb,
        txtSoMayAccMoi: customerInfo.dvvt_id === DichVuVienThong.CO_DINH ? data.matb_moi : '',
        txtTenTB: Const.ChuanHoaTen(data.ten_tb),
        txtDiaChiLD: Const.ChuanHoaTen(data.diachi_ld),
        txtGhiChu: data.ghichu,
        txtKieuLD: data.ten_kieuld,
      }

      this.lblThuHoi = await this.viewThuHoi();

      if (data.hdtb_cha_id != null || data.thuebao_cha_id != null) {
        this.sochinh = false;
        if (data.hdtb_cha_id != null) {
          this.hdtb_cha_id = data.hdtb_cha_id;
        } else {
          this.thuebao_cha_id = data.thuebao_cha_id;
        }
      } else {
        this.sochinh = true;
      }

      //tt hoàn công
      let {hoanCongInfo} = this.frmData;
      if (data.ngay_ht_gp != null && data.ngay_ht_gp !== '') {
        hoanCongInfo.chk_ngay_th = true;
        hoanCongInfo.ngay_hoan_thanh = moment(data.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm a');
        this.btnEnabled.tsbtnHoanThanh = true;
      } else {
        hoanCongInfo.chk_ngay_th = false;
        hoanCongInfo.ngay_hoan_thanh = moment(new Date()).format('DD/MM/YYYY HH:mm a');
        this.btnEnabled.tsbtnHoanThanh = false;
      }
      hoanCongInfo.txtNDGiao = data.nd_giao;
      hoanCongInfo.txtNoiDungTH = data.nd_thuchien;
      this.nd_thuchien = data.nd_thuchien;

      //set du lieu cap
      this.setDuLieuCap(data);
      this.setKM(data);
    },

    async get_ds_thietbi_by_hdtb_id() {
      try {
        let rs = await API.get_ds_thietbi_by_hdtb_id(this.axios, this.hdtb_id);
        this.dsThietBi = rs.data != null && rs.data.data != null ? rs.data.data : [];
        return rs.data.data;
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    async viewThuHoi() {
      try {
        let rs = await API.lay_kqxl_theo_hdtb_id(this.axios, this.hdtb_id);
        if (rs.data != null && rs.data.data != null) {
          return rs.data.data;
        }

      } catch (err) {
        this.$toast.error(err.message);
      }

      return '';
    },

    async setDuLieuCap(data) {
      let {hostInfo} = this;
      this.ne_id_cu = 0;
      this.hostInfo = {
        ...hostInfo,
        txtHostLD: '',
        txtHostVL: '',
        txtHostLD_Moi: '',
        txtNE: '',
        loai_cap_id: data.loaicap_id,
        txtTuCapGoc: data.tucap_goc,
        txtDoiCapGoc: data.doicap_goc,
        txtCapGoc: data.cap_goc,
        txtHopCapNgon: data.hopcap_p,
        txtTuCapNgon: data.tucap_p,
        txtDoiCapNgon: data.doicap_p,
        txtCapNgon: data.cap_p,
        txtTuyenCap: data.tuyencap,
        txtLienTu: data.lientu,
        txtSoMetDay: data.culy,
        txtVeTinh: '',
      }

      let {dvvt_id} = this.frmData.customerInfo;
      if (dvvt_id === DichVuVienThong.CO_DINH) {
        if (data.ne_id != null) {
          this.ne_id_cu = data.ne_id;
          let ds_ne = await this.getNe();
          if (ds_ne != null) {
            this.hostInfo.txtVeTinh = ds_ne[0].ten_dv;
            this.hostInfo.txtNE = ds_ne[0].sone;
          }
        }
      } else if (dvvt_id === DichVuVienThong.ADSL) {
        if (data.dslam_id != null) {
          let s = '-1';
          try {
            let rs = this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dslam', {
              "param": data.dslam_id,
              "type": 4
            });

            s = rs.data.data;
          } catch (e) {
            console.log('setDuLieuCap: ' + e);
          }

          if (s != "-1")
            this.hostInfo.txtVeTinh = s;
          else
            this.hostInfo.txtVeTinh = "";
        }

        this.frmData.customerInfo.txtSoAo = data.ma_lt;
      } else if (dvvt_id === DichVuVienThong.MEGAWAN) {
        this.frmData.customerInfo.txtSoAo = data.ma_lt;
      }

      try {
        //Nếu có tham số này thì hiển thị thông tin host lắp đặt, host vật lý
        let dt = await this.lay_thongtin_host(this.hdtb_id, 1);
        if (dt != null && dt.length > 0) {
          this.hostInfo.txtHostLD = dt[0].hostld_cu;
          this.hostInfo.txtHostVL = dt[0].host_vl;
          this.hostInfo.txtHostLD_Moi = dt[0].hostld_moi;
        }
      } catch (e) {
      }
    },

    async setKM(data) {
      this.vkieu_km = 0;
      let {loai_hd_id} = this.frmData.customerInfo
      if (loai_hd_id === LoaiHopDong.THAY_DOI_DV) {
        let ds_quytrinh = await this.lay_kieuyc_theo_hdtb_id(this.hdtb_id);
        if (ds_quytrinh != null) {
          let qt = ds_quytrinh[0];
          if (qt.kieu == "0") {
            if (qt.kieu == "1")
              this.frmData.customerInfo.lblTamDung_No = "Đăng ký tạm dừng theo yêu cầu";
            else
              this.frmData.customerInfo.lblTamDung_No = "Hủy tạm dừng theo yêu cầu";
            this.vkieu_km = 0;
          } else {
            if (qt.kieu_yc == "1") {
              this.frmData.customerInfo.lblTamDung_No = "Đăng ký tạm dừng do nợ cước";
              this.vkieu_km = 1;
            } else {
              this.frmData.customerInfo.lblTamDung_No = "Hủy tạm dừng do nợ cước";
              this.vkieu_km = 0;
            }
          }
        } else {
          this.frmData.customerInfo.lblTamDung_No = "";
        }

        if (this.vkieu_km === 1) {
          this.frmData.hoanCongInfo.enableChkPhanHoi = true;
          this.frmData.hoanCongInfo.chkPhanHoi = false;
          this.frmData.hoanCongInfo.chk_ko_thu_hoi = false;
        } else {
          this.frmData.hoanCongInfo.enableChkPhanHoi = false;
          this.frmData.hoanCongInfo.chkPhanHoi = false;
          this.frmData.hoanCongInfo.chk_ko_thu_hoi = false;
        }
      } else {
        this.frmData.customerInfo.lblTamDung_No = "";
        this.frmData.hoanCongInfo.enableChkPhanHoi = false;
        this.frmData.hoanCongInfo.chkPhanHoi = false;
        this.frmData.hoanCongInfo.chk_ko_thu_hoi = false;
      }
    },

    //load combobox
    async getCbNguoiGiaoViec() {
      try {
        let rs = await API.danhmuc_nguoigiaoviec(this.axios, {})
        this.dsNguoiGiaoViec = rs.data.data == null ? [] : rs.data.data;
        if (this.dsNguoiGiaoViec.length > 0) {
          this.frmData.hoanCongInfo.nguoi_giao_viec = this.dsNguoiGiaoViec[0].ID;
        }
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    async getCbKieuLapDat() {
      try {
        let rs = await API.danhmuc_kieu_ld(this.axios, {});
        this.dsKieuLD = rs.data.data == null ? [] : rs.data.data;
        if (this.dsKieuLD.length > 0) {
          this.frmData.hoanCongInfo.kieu_lap_dat = this.dsKieuLD[0].kieuld_id;
        }
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    //button event
    async CapNhat() {
      try {
        if (!this.KiemtraDL()) return false;
        let vngay_th = moment(this.frmData.hoanCongInfo.ngay_hoan_thanh, 'DD/MM/YYYY HH:mm a').format('DD/MM/YYYY HH:mm:ss')
        if (this.currentItem && this.currentItem.ngay_ht) {
          let old_ngay_th = moment(this.currentItem.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          if (old_ngay_th === vngay_th) {
            this.$toast.error('Bạn chưa thay đổi ngày thực hiện')
            return false;
          }
        }

        let rs = await API.capnhat_phieu_thuhoi(this.axios, {
          vngay_th,
          vphieu_id: this.phieu_id
        });
        if (rs.data != null && rs.data.error === '200') {
          return true;
        }
      } catch (ex) {
        this.$toast.error('Cập nhật không thành công');
      }

      return false;
    },

    KiemtraDL() {
      if (this.dsPhieuLength <= 0) {
        this.$toast.error("Hiện tại chưa có phiếu thu hồi cổng");
        return false;
      }
      if (this.phieu_id === 0) {
        this.$toast.error("Bạn hãy chọn phiếu!");
        return false;
      }
      if (!this.frmData.hoanCongInfo.chk_ngay_th) {
        this.$toast.error("Hãy nhập ngày thực hiện!");
        return false;
      }

      return true;
    },

    async KIEMTRA_STB_2515(vkieu, vhdtb_id) {
      try {

        let user_name = this.ma_tb_cur;// tà

        if (user_name == "") {
          this.$root.$toast.error("Không lấy được acc mytv của hợp đồng (" + vhdtb_id + ")");
          return "0";
        }

        // var kq = tdan.GET_VALUE_FUNC("{?DB2}.KIEMTRA_STB_2515", "vkieu", vkieu, "vhdtb_id", vhdtb_id, "vserial", "");
        let kq = await this.KIEMTRA_STB_2515_API(vkieu, vhdtb_id, '');
        if (kq == null) {
          this.$root.$toast.error("Không lấy được kết quả kiểm tra STB");
          return "0";
        } else {
          if (vkieu == "3" || vkieu == "5") // THU HỒI
          {
            if (!kq.startsWith("OK")) //Nếu bat dau = OK
            {
              if (kq == "-2")
                return "-2";
              else {
                this.$root.$toast.error(kq);
                return kq;
              }
            } else {
              return kq;
            }
          } else {
            return "Lỗi ngoại lệ";
          }
        }
      } catch (ex) {
        console.log(ex.message);
      }
    },

    async THUHOI_STB_2515(boqua, vuser_name, kq, kieu, lydo) {
      if (boqua == "1") {
        let user_name = vuser_name;// tài khoản Mytv
        let stb_serial = kq.split('-')[2];
        let stb_serial_new = "";
        let stb_type = kq.split('-')[1];
        let date = moment(this.frmData.hoanCongInfo.ngay_hoan_thanh, 'YYYY-MM-DD HH:mm a').format('YYYYMMDD');
        let note = this.nd_thuchien;
        let isback = true;
        let resue = true;
        let notback_reason = "";
        let not_resuse_dame = true;
        let not_resue_reason = "";
        let tinhtrang = "";
        if (kieu == "5") //Khong thu hoi duoc thi = false
        {
          isback = false;
          notback_reason = lydo;
          resue = false;
          not_resuse_dame = false;
          not_resue_reason = "";
        }
        if (kieu == "3") //Thu hoi duoc
        {
          isback = true;
          tinhtrang = kq.split('-')[3];
          notback_reason = "";
          if (tinhtrang == "1")// tai sudung duoc
          {
            resue = true;
            not_resuse_dame = false;
          } else {
            resue = false; // Không tái sử dụng đc
            let rs = await this.$refs.popupNhapText.showModal()
            let {result, reason} = rs;

            if (result === 'ok') {// nếu là lý do khác
              not_resuse_dame = false;
              not_resue_reason = reason;
            } else if (result === 'notok') {// BỊ hỏng
              not_resuse_dame = true;
              not_resue_reason = "";
            } else if (result === 'abort') {
              return "0";
            } else {
              return "0";
            }
          }
        }


        try {
          let _result_STB = this.STB_PROM_Back(user_name, stb_serial, stb_serial_new, stb_type, date, note,
            isback, notback_reason, resue, not_resuse_dame, not_resue_reason);
          if (_result_STB == 'ok') {
            try {
              let kq_update = await this.sync_stb(this.hdtb_id);

              if (kq_update == '1') {
                this.$root.$toast.success("Hoàn công " + user_name + " tham gia chương trình trên VASC thành công");
              } else {
                this.$root.$toast.error("Thu hồi STB tham gia chương trình thành công.Có lỗi khi lưu vào dưới tỉnh." +
                  " Mã lỗi (" + this.hdtb_id + ")." +
                  " Bạn hãy liên hệ admin mã lỗi để có thể tiếp tục hoàn công");
                return "0";
              }
            } catch (ex) {
              this.$root.$toast.error("Thu hồi STB tham gia chương trình thành công.Có lỗi khi lưu vào dưới tỉnh. Mã lỗi (" + this.hdtb_id + ")." +
                ".Bạn hãy liên hệ admin mã lỗi để có thể tiếp tục hoàn công");
              return "0";
            }
          } else {

            this.$root.$toast.error("Thu hồi STB tham gia chương trình thất bại " + _result_STB + "  Mã (" + this.hdtb_id + "");
            return "0";
          }
        } catch (ex) {
          this.$root.$toast.error("Thu hồi STB tham gia chương trình thất bại " + ex.message);
          return "0";
        }

        return "1";
      } else {
        console.log('Bỏ qua không gọi VASC');
        return "1";
      }
    },

    btnLayTT() {
      this.getDsPhieu();
    },

    async btnHoanThanh() {
      try {
        this.loading(true);
        let vkieuld_id = 0;
        let {chkPhanHoi, kieu_lap_dat, chk_ko_thu_hoi} = this.frmData.hoanCongInfo;

        if (this.vkieu_km === 1 && !chkPhanHoi) {
          this.$toast.error('Bạn chưa chọn phản hồi của khách hàng !');
          return;
        }

        if (chkPhanHoi) {
          vkieuld_id = kieu_lap_dat;
        }

        let {dsThietBi} = this;
        if (chk_ko_thu_hoi) {
          if (dsThietBi != null && dsThietBi.length > 0) {
            this.$toast.error("Bạn hãy thực hiện xóa danh sách vật tư thu hồi");
            return;
          }

          if (chkPhanHoi) {
            if (kieu_lap_dat === 687 || kieu_lap_dat === 689) {
              this.$toast.error("Khách hàng không trả thiết bị. Bạn phải chọn Phản hồi khác !");
              return;
            }
          }
        } else {
          if (this.huonggiao_id !== 3001) {
            if (dsThietBi == null || dsThietBi.length <= 0) {
              this.$toast.error("Bạn hãy thực hiện nhập vật tư thu hồi");
              return;
            }
          }
        }

        let vthuhoi = -1;
        if (chk_ko_thu_hoi) {
          vthuhoi = 1;
        }
        let kq = "";
        let boqua = "0";
        let boham = "0";
        let {loaitb_id, hdtb_id, ma_tb_cur, nd_thuchien, hdtb_cha_id, thuebao_cha_id} = this;
        if (loaitb_id === LoaiHinhTB.INTERNET_MYTV && vthuhoi === -1) {//Nếu là mytvy và có thu hồi
          //Kiểm tra trước khi hoàn công
          //0 là bỏ qua, 1 là gọi kich hoạt
          kq = await this.KIEMTRA_STB_2515("3", hdtb_id); // =3  la kiểm tra có vật tư thu hồi
          if (kq.startsWith("OK")) {// Bắt đầu bằng chữ OK-stb_tye-serial
            boqua = "1"; // gọi hoàn công
          } else {// Nếu không phải in (1,2,3,4)
            if (kq === "-2") // -1 là bỏ qua
            {
              boqua = "0";
            } else {//ko phải -1 thì ở lại
              return;
            }
          }

          if (kq.startsWith("OKNOTOK")) {// trường hợp ko lấy đc vât tư sẽ bỏ qua
            boham = "1";
          }

          if (boham === "0") {
            let k = await this.THUHOI_STB_2515(boqua, ma_tb_cur, kq, "3", "");
            if (k !== "1") {
              return;
            }
          }
        } else if (loaitb_id === LoaiHinhTB.INTERNET_MYTV && vthuhoi === 1) {//Nếu là mytv và không có thu hồi
          //Kiểm tra trước khi hoàn công
          //0 là bỏ qua, 1 là gọi kich hoạt
          kq = await this.KIEMTRA_STB_2515("5", hdtb_id); // =3  la kiểm tra có vật tư thu hồi
          if (kq.startsWith("OK")) {// Bắt đầu bằng chữ OK-stb_tye-serial
            boqua = "1"; // gọi hoàn công
          } else {// Nếu không phải in (1,2,3,4)
            if (kq === "-2") // -1 là bỏ qua
            {
              boqua = "0";
            } else {//ko phải -1 thì ở lại
              return;
            }
          }

          if (nd_thuchien === "" && boqua === "1") {
            this.$toast.error("Bạn phải nhập lý do vào nội dung thực hiện");
            return;
          }

          if (kq.startsWith("OKNOTOK")) {// trường hợp ko lấy đc vât tư sẽ bỏ qua
            boham = "1";
          }

          if (boham === "0") {
            let k = await this.THUHOI_STB_2515(boqua, ma_tb_cur, kq, "5", nd_thuchien);
            if (k !== "1") {
              return;
            }
          }
        }

        let capNhat = await this.CapNhat();
        if (!capNhat) return;

        if (hdtb_cha_id !== 0) {
          for (let i = 0; i < this.dsPhieuLength; i++) {
            if (this.dsPhieu[i].hdtb_id === hdtb_cha_id
              || this.dsPhieu[i].thuebao_id === thuebao_cha_id) {
              this.$toast.error("Bạn phải hoàn thành số chính trước khi hoàn thành số phụ");
              return;
            }
          }
        }

        let rs = await API.capNhatPhieuThuHoiCong(this.axios, {
          phieuId: this.phieu_id,
          hdtbId: hdtb_id,
          trangThai: this.tthd_id,
          donViGiaoId: this.$root.token.getDonViID(),
          kieu_km: this.vkieu_km,
          kieuLdId: vkieuld_id,
          thuHoi: vthuhoi
        });

        if (rs.data != null && rs.data.error === '200') {
          if (this.tthd_id === TrangThaiHD.KHAIBAO_TONGDAI) {
            this.$toast.success("Thu hồi cổng thành công!");
          } else {
            this.$toast.success("Thu hồi dây thành công!");
          }

          this.clear();
          this.getDsPhieu();
        } else {
          this.$toast.error("Thu hồi không thành công!");
        }
      } catch (e) {
        console.log(e);
        this.$toast.error(e.message);
      } finally {
        this.loading(false);
      }
    },

    async btnCapNhat() {
      this.loading(true);
      let l_phieu_id = this.current_phieu_id;
      if (await this.CapNhat()) {
        this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
      } else {
        this.loading(false);
        return;
      }

      await this.getDsPhieu();
      this.setRowSelected(l_phieu_id);
      this.loading(false);
    },

    setRowSelected(l_phieu_id) {
      if (this.dsPhieuLength <= 0) {
        return;
      }

      for (let i = 0; i < this.dsPhieuLength; i++) {
        if (this.dsPhieu[i].phieu_id === l_phieu_id) {
          this.setCurrentRow(i);
          break;
        }
      }
    },

    async giaoviec_success() {
      // let l_phieu_id = this.current_phieu_id;
      // this.phieu_id = 0;
      // this.getDsPhieu();
      // this.setRowSelected(l_phieu_id);
      await this.getDsNhanVienTheoPhieuId();
    },

    async btnGiaoViec() {
      try {
        if (this.getSelectedRecords().length <= 0) {
          this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại!");
          return;
        }

        let {nguoi_giao_viec, chk_ngay_gv, ngay_giao_viec} = this.frmData.hoanCongInfo;
        console.log(nguoi_giao_viec);
        if (this.dsNguoiGiaoViec == null || this.dsNguoiGiaoViec.length <= 0 || nguoi_giao_viec === 0) {
          this.$toast.error("Hãy nhập nhân viên giao việc!");
          return;
        }
        if (!chk_ngay_gv || ngay_giao_viec == '') {
          this.$toast.error("Hãy nhập ngày giao việc!");
          return;
        }

        this.frmData.hoanCongInfo.ngay_gv_form = moment(ngay_giao_viec, 'DD/MM/YYYY HH:mm a').format('DD/MM/YYYY HH:mm:ss')

        this.$refs.formgiaphieu.init();
        this.$refs.giaophieunhanvien.show();

        // hoangpkn : 23/03/2015
        // bổ sung tự động giao việc cho số phụ ISDN
        if ((this.loaitb_id == LoaiHinhTB.ISDN2B_CD ||
          this.loaitb_id == LoaiHinhTB.ISDN2B_CQ ||
          this.loaitb_id == LoaiHinhTB.ISDN30B_CD ||
          this.loaitb_id == LoaiHinhTB.ISDN30B_CQ) && this.sochinh == true) {
          for (let item of this.dsPhieu) {
            if (item.hdtb_cha_id == this.hdtb_id) {
              this.TaoDuLieu_GPNV_ISDN(item.phieu_id);
            }
          }
        }

      } catch (e) {
        this.$toast.error("Có lỗi: " + e.message);
      }
    },

    capnhatdv_success() {
      this.getDsPhieu();
    },

    btnCapNhatVT() {
      if (this.phieu_id === 0) {
        this.$toast.error("Bạn chưa chọn phiếu!");
        return false;
      }

      this.modelVatTu = {
        phieu_id: this.phieu_id
      }

      this.$refs.popupCapNhatDonVi.showModal();
    },

    vattu_success() {
      this.get_ds_thietbi_by_hdtb_id();
    },

    btnVatTu() {
      if (this.getSelectedRecords().length > 0) {
        this.modelVatTu = {
          hdtb_id: this.hdtb_id,
          phieu_id: this.phieu_id,
          loaihd_id: this.loaihd_id,
          thuebao_id: this.thuebao_id
        }

        this.$refs.popupVatTuThueBao.showModal();
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },

    btnInPhieu() {
      this.$root.$toast.warning('Chưa tích hợp được API. Chờ đội Report');
    },

    btnXuatFile() {
      try {
        if (this.dsPhieuLength > 0) {
          this.$refs.frmLayDuLieuBC.showModal();
        } else {
          this.$root.$toast.error("Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !");
        }
      } catch (ex) {
        console.log(ex);
      }
    },

    async onEnterSoMayAcc() {
      let {txtSoMayAcc} = this.frmData.customerInfo;
      if (txtSoMayAcc != '') {
        try {
          this.loading(true);
          let data = await this.getDsPhieuGiaoTCKhiDC();
          if (data != null && data.length > 0) {
            this.dsPhieu = data;
            this.setCurrentRow(0);
          } else {
            this.clear();
          }
        } catch (e) {

        } finally {
          this.loading(false);
        }
      }
    },

    async getNe() {
      try {
        let rs = await API.lay_tt_ne_theo_id(this.axios, this.ne_id_cu);
        return this.getDataFromAPI(rs);
      } catch (e) {

      }

      return null;
    },

    async mapData(id_neo, table, dk) {
      try {
        let rs = await API.fnMapId(this.axios, {id_neo, table, dk});
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load duoc ky hoa don');
      }
    },

    getDataFromAPI(rs) {
      if (rs.data != null && rs.data.data != null && rs.data.data.length > 0) {
        return rs.data.data;
      }

      return null;
    },

    async lay_thongtin_host(hdtb_id, kieu) {
      try {
        let rs = await API.lay_thongtin_host(this.axios, hdtb_id, kieu);
        return this.getDataFromAPI(rs);
      } catch (e) {

      }

      return null;
    },

    async lay_kieuyc_theo_hdtb_id(hdtb_id) {
      try {
        let rs = await API.lay_kieuyc_theo_hdtb_id(this.axios, hdtb_id, kieu);
        return this.getDataFromAPI(rs);
      } catch (e) {

      }

      return null;
    },

    async updateNDTH() {
      try {
        if (this.phieu_id === 0) {
          this.$root.$toast.error('Bạn chưa chọn phiếu');
          return;
        }
        let rs = await API.capnhat_ndth_theo_phieu_id(this.axios, {
          vphieu_id: this.phieu_id,
          vnd_thuchien: this.frmData.hoanCongInfo.txtNoiDungTH,
        });

        if (rs.data.error === '200') {
          // await this.getDsPhieu();
          this.$root.$toast.success("Cập nhật thành công");
        } else {
          this.$root.$toast.error('Có lỗi: " ' + err.data.message + '"');
        }
      } catch (e) {
        this.$root.$toast.error('Có lỗi xảy ra');
      }
    },

    async TaoDuLieu_GPNV_ISDN(phieu_id) {
      try {
        let rs = await API.lay_dsnv_theo_phieu(this.axios, phieu_id);
        let dsNV = rs.data.data;

        let dsnhanviengp = [];
        for (let i = 0; i < dsNV.length; i++) {
          let obj = {
            PHANVUNG_ID: this.$root.token.getPhanVungID(),
            PHIEU_ID: phieu_id,
            NHANVIEN_GIAO_ID: dsNV[i].nhanvien_giao_id,
            NGAYGIAO: dsNV[i].ngaygiao,
            NHANVIEN_TH_ID: dsNV[i].nhanvien_th_id,
            NHIEMVU: dsNV[i].nhiemvu,
            GHICHU: dsNV[i].ghichu,
            SO_DT: dsNV[i].so_dt,
          }

          dsnhanviengp.push(obj);
        }

        if (dsnhanviengp.length > 0) {
          API.capnhat_giaophieunv_isdn(this.axios, {
            vphieu_id: phieu_id,
            vdsgiaophieunv: JSON.stringify(dsnhanviengp)
          })
        }
      } catch (e) {

      }
    },

    async KIEMTRA_STB_2515_API(vkieu, vhdtb_id, vserial) {
      try {
        let rs = await API.kiemtra_stb_tfof(this.axios, {
          vkieu,
          vhdtb_id,
          vserial
        });

        return this.getDataFromAPI(rs);
      } catch (e) {

      }

      return '-1';
    },

    async STB_PROM_Back(user_name, stb_serial, stb_serial_new, stb_type, date, note, isback, notback_reason, resue, not_resuse_dame, not_resue_reason) {
      try {
        let rs = await API.sTB_PROM_Back(this.axios, {
          "stbDTO": {
            "is_BACK": isback,
            "notback_REASON": notback_reason,
            "note": note,
            "notreuse_DAMAGE": not_resuse_dame,
            "notreuse_REASON": not_resue_reason,
            "program": '',
            "reuse": resue,
            "stb_DATE": date,
            "stb_SERIAL": stb_serial,
            "stb_SERIAL_NEW": stb_serial_new,
            "stb_TYPE": stb_type,
            "username": user_name
          }
        });

        return this.getDataFromAPI(rs);
      } catch (e) {

      }

      return '-1';
    },

    async sync_stb(vhdtb_id) {
      try {
        let rs = await API.sync_stb(this.axios, vhdtb_id);
        return this.getDataFromAPI(rs);
      } catch (e) {

      }

      return '-1';
    }
  }
}
</script>
