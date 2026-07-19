<template src="./template.html"></template>
<script>
import {
  TRANGTHAI_DONGBO,
  TrangThaiHD,
  LoaiHopDong,
  LOAI_DV,
} from "./define/index.js";
import collums from "./define/collums.js";
import MODEL from "./define/model.js";
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import NE from "./popup/NE.vue";
import giaophieunhanvien from "./popup/giaophieunhanvien.vue";
import MapComponent from "./popup/map.vue";
import { FormValidator } from "@syncfusion/ej2-vue-inputs";
import traphieu from "../ReturnInstallTicket/components/traphieu.vue";
import DataGridCustom from "./components/DataGrid";
import moment from "moment"
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import Gankhuvuc from '@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao'
import daunoithuebao from './popup/daunoithuebao'
import ChuyenTo from '../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
export default {
  components: {
    NE,
    MapComponent,
    VueElement,
    traphieu,
    giaophieunhanvien,
    DataGridCustom,
    ModalChonToaDo,
    Gankhuvuc,
    daunoithuebao,
    ChuyenTo
  },
  data() {
    return {
      ...collums,
      target: '.main-wrapper',
      model: {},
      showModalTraphieu: false,
      showModalGiaoviec: false,
      thongso_html: "",
      loadingTB: false,
      dateVisiable: {
        chkNgayBG: false,
        dtpNgayBG: false,
        chkNgayBGEnable: true,
        chkNgayGV: false,
        chkNgayGVEnable: true,
        dtpNgayGV: false,
      },
      visiable: {
        giaophieu: false,
        kichhoat: false,
        huykichhoat: false,
      },
      header: {
        title: "Hoàn công cố định",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      map_position: {
        lat: null,
        lng: null,
      },
      map_info: {
        dia_chi: "",
        title: ""
      },
      old_index: 0,
      default_index: 0,
      dsgv: [],
      modeMap: 1,
      dsQuyTrinh: [],
      huonggiao_id: 0,
      luong_id: 0,
      khong_hoancong_chua_thanhtoan: 0,
      showModal: false,
      showModalMap: false,
      timkiem_checkbox: false,
      button: {
        kieu: "0",
      },
      global: {
        dichvuvt_id: 1,
        tthd_id: TrangThaiHD.DANG_THI_CONG,
        //tthd_id: 9,
        dsloaihd_id: '',
        dsdichvuvt_id: '',
        dichvuvt_id: 0
      },
      options: {
        vetinh: [],
        dsThuebao: [],
        dsNhanvien: [],
        dsDGVT: [],
        quytrinh: [],
        loaidonvi: [
          { id: "6", text: "Host" },
          { id: "25", text: "Trạm vệ tinh" },
        ],
      },
      using_cabman: false,
      formObj: "",
    };
  },
  async mounted() {
    let str = "";
    let Tag = this.$route.query.tag
    if (Tag) {
      str = Tag;
      let words = str.split(' ');
      if (words.length >= 1)
        this.global.tthd_id = words[0]
      if (words.length >= 2) {
        this.global.dsloaihd_id = words[1];
        this.global.dsloaihd_id = this.global.dsloaihd_id.replaceAll(';', ',');
      }
      if (words.length >= 3) {
        this.global.dsdichvuvt_id = words[2];
        this.dsdichvuvt_id = this.global.dsdichvuvt_id.replaceAll(';', ',');
      }
      if (this.global.dsdichvuvt_id == "") {
        this.global.dsdichvuvt_id = "1";
      }
    }
    else {
      this.global.tthd_id = 4;
      this.global.dsloaihd_id = "1";
      this.global.dsdichvuvt_id = "1";
    }
    try {
      this.loading(true);
      await api
        .getComboBox(this.axios, {
          chuoi:
            "KENHTHU|KIEU_HD|LOAI_HD|LOAI_CAP|NHANVIEN-donvi_id = " + this.$root.token.getDonViID() + "|QUYTRINH",
        })
        .then((res) => {
          let data = res.data.data ? res.data.data : [];
          for (let index in data) {
            this.$set(this.options, index, data[index]);
          }

          if (this.options.quytrinh) {
            this.dsQuyTrinh = this.options.quytrinh;
          }
          this.setDefaultCombobox();
          this.loading(false);
        });

      //lấy danh sách loại đơn vị
      //let loaidonvi = await api.getComboBoxLoaiDV(this.axios)
      //this.options.loaidonvi = loaidonvi.data ? loaidonvi.data.data : [];
    } catch (err) {
      this.loading(false);
    }
    this.init()
    this.initValidate();
  },
  computed: {
    list_quytrinh() {
      let dsdv = this.global.dsdichvuvt_id.split(',')
      return this.options.quytrinh.filter((item) => {
        return item.loaihd_id == this.loaihd_id && item.tthd_id == this.global.tthd_id && dsdv.includes(String(item.dichvuvt_id));
      });
    },
    list_quytrinh_unique() {
      return [...new Map(this.list_quytrinh.map((item) => [item["quytrinh"], item])).values()
      ]
    },
    TRAMVT_ID: {
      get() {
        return String(this.model.TRAMVT_ID);
      },
      set(val) {
        this.$set(this.model, "TRAMVT_ID", val);
      },
    },
    tongtien: {
      get() {
        if (this.model.tongtien) {
          return this.numberWithCommas(this.model.tongtien);
        } else {
          return 0;
        }
      },
      set(val) {
        this.$set(this.model, "tongtien", val);
      },
    },
    nhanvien_id: {
      get() {
        return Number(this.model.nhanvien_id);
      },
      set(val) {
        this.$set(this.model, "nhanvien_id", val);
      },
    },
    kieuhd_id: {
      get() {
        return Number(this.model.kieuhd_id);
      },
      set(val) {
        this.$set(this.model, "kieuhd_id", val);
      },
    },
    loaihd_id: {
      get() {
        return Number(this.model.loaihd_id);
      },
      set(val) {
        if (val)
          this.$set(this.model, "loaihd_id", val);
      },
    },
    kenhthu_id: {
      get() {
        return Number(this.model.KENHTHU_ID);
      },
      set(val) {
        this.$set(this.model, "KENHTHU_ID", val);
      },
    },
    loaicap_id: {
      get() {
        return Number(this.model.loaicap_id);
      },
      set(val) {
        this.$set(this.model, "loaicap_id", val);
      },
    },
  },
  methods: {
    HienThiGiaoDien() {
      this.$refs.tsbtnKQ_DoKiem.style.display = 'none';
      this.$refs.tsbtnDuAn.style.display = 'none';
      this.$refs.tsbtnHoanCong.style.display = 'none';
      this.$refs.tsbtnGiaoPhieu.style.display = 'none';
      this.$refs.btnPhieuTC.style.display = 'none';
      this.$refs.tsbtnTraPhieu.style.display = 'none';
      this.$refs.tsbtnVatTu.style.display = 'none';
      this.$refs.tsbtnVatTuMoi.style.display = 'none';
      this.$refs.tsbtnGiaoPhieu.style.display = 'none';
      this.$refs.tsbtnHoanThienHS.style.display = 'none';

      //this.$refs.btnChonNe.style.display = 'none';
      //this.$refs.btnChonNe_TuDong.style.display = 'none';

      this.$refs.txtTuCapGoc.readOnly = true
      this.$refs.txtDoiCapGoc.readOnly = true
      this.$refs.txtCapGoc.readOnly = true;
      this.$refs.txtHopCapNgon.readOnly = true;
      this.$refs.txtTuCapNgon.readOnly = true;
      this.$refs.txtDoiCapNgon.readOnly = true;
      this.$refs.txtCapNgon.readOnly = true;
      this.$refs.txtTuyenCap.readOnly = true;
      this.$refs.txtLienTu.readOnly = true;
      this.$refs.txtSoMetDay.readOnly = true;

      this.$refs.tsbtnKichHoat.style.display = 'none';
      this.$refs.tsbtnHoanThienHS.style.display = 'none';
      this.$refs.tsbtnSua_KenhThu.style.display = 'none';
      this.$refs.tsbtnGiaoViec_KBB.style.display = 'none';

      try {
        let thaotac = this.list_quytrinh.filter(item => item.luong_id == this.luong_id)
        thaotac.forEach(item => {
          if (this.$refs[item['control_name']]) {
            let ref = this.$refs[item['control_name']]
            let type = ref.nodeName
            if (ref._props) {
              if (item.enable == 0)
                ref._props.disabled = true
              else
                ref._props.disabled = false
            }
            if (type == 'LI') {
              if (item.enable == 0) {
                ref.style.display = 'none';
              } else {
                ref.style.display = 'inline-block';
              }
            }
            if (type == 'A') {
              if (item.enable == 0) {
                ref.style.display = 'none';
              } else {
                ref.style.display = 'inline-block';
              }
            }
            if (type == 'INPUT') {
              if (item.enable == 0) {
                ref.readOnly = true;
              } else {
                ref.readOnly = false;
                ref.classList.add('highlight')
              }
            }
          }
        });
      } catch (e) {
        console.log(e)
      }
    },
    phailam(code) {
      return this.axios
        .post("/web-hopdong/thaydoi_thongso_megawan/phailam", {
          luong_id: this.luong_id,
          code: code,
        })
        .then((response) => {
          return response.data && response.data.data ? true : false;
        });
    },
    Visiable(name, status) {
      this.$refs[name].style.display = status ? null : 'none';
    },
    Disable(name) {
      this.$refs[name].classList.add('non-ative')
    },
    Disable(name) {
      this.$refs[name].classList.add('non-ative')
    },
    Enable(name, status) {
      if (status) {
        this.$refs[name].style.display = null
        this.$refs[name].classList.remove('non-ative')
      } else {
        this.$refs[name].classList.add('non-ative')
      }
    },
    DateDisable(name) {
      this.$refs[name].disabled = true
    },
    tsbtnVatTuMoi_Click() {

    },
    tsbtnGiaoViec_KBB_Click() {

    },
    tsbtnHoanThienHS_Click() {

    },
    tsbtnHoanThanh_Click() {

    },
    tsbtnSua_KenhThu_Click() {

    },
    tsbtnVatTu_Click() {

    },
    tsbtnDuAn_Click() {

    },
    tsbtnKQ_DoKiem_Click() {

    },

    cboQuyTrinh_change() {
      this.HienThiGiaoDien()
    },
    chapnhan_gankhuvuc() {
      this.getDanhsachThuebao();
      this.$bvModal.hide('gankhuvuc')
    },
    daunoi() {
      let thongTinDauNoi = {
        choPhepDoiTramTbi: false,
        tramTbiId: this.model.tramtb_id,
        chonTramTbiTheoTB: false,
        thueBaoId: this.model.hdtb_id,
        kieu: 1
      }
      this.$refs.daunoithuebao.setData(thongTinDauNoi)
      this.$refs.daunoithuebao.show()
    },
    async gankhuvuc() {
      this.$bvModal.show('gankhuvuc')
    },
    async giaophieu() {
      if (!this.visiable.giaophieu) {
        return false;
      }
      this.$router.push('/contract/handoverticket?vdichvuvt_id=1&vma_gd=' + this.model.ma_gd)
    },
    async XacNhanToaDo(value) {
      try {
        if (!this.model.hdtb_id) {
          return false;
        }
        await api.fn_capnhat_toado_hdtb(this.axios, { "lat": value.lat, "lng": value.lng, "hdtb_id": this.model.hdtb_id, "mode": this.modeMap })
        this.$toast.success("Cập nhật tọa độ thành công!")
      } catch (error) {
        this.$toast.error(error.message ? error.message : 'Đã có lỗi xảy ra')
      }
    },
    async hoancong() {
      try {
        let data = []
        this.dsgv.forEach(item => {
          data.push({
            HDTB_CHA_ID: item.hdtb_cha_id,
            HDTB_ID: item.hdtb_id,
            PHIEU_ID: item.phieu_id,
            HDKH_ID: item.hdkh_id,
            THUEBAO_CHA_ID: item.thuebao_cha_id
          })
        })
        let sochinh = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "GIAOVIEC",
        });
        let dt = [{
          "HDTB_ID": this.model.hdtb_id, "HDKH_ID": this.model.hdkh_id, "THUEBAO_CHA_ID": this.model.thuebao_cha_id, "LUONG_ID": this.luong_id, "SOCHINH": Number(sochinh.data.data ? sochinh.data.data : 0),
          "LOAIHD_ID": this.model.loaihd_id, "LOAITB_ID": this.model.loaitb_id, "NHANVIEN_ID": this.$root.token.getNhanVienID(), "NGUOI_GV": this.model.nguoigv_id, "NGAY_CN": moment().format('DD/MM/YYYY HH:mm:ss'),
          "PHIEU_ID": this.model.phieu_id, "DONVI_ID": this.$root.token.getDonViID(), "MAY_CN": this.$root.token.getMaNhanVien(), "NGUOI_CN": this.$root.token.getUserName(), "IP_CN": this.$root.token.getUserName(), "SO_DT": this.model.so_dt,
          "HUONGGIAO_ID": this.huonggiao_id, "HDTB_CHA_ID": this.model.hdtb_cha_id, "MA_TB": this.model.ma_tb, "NE_ID": this.model.ne_id, "NE_ID_CU": this.model.ne_cu_id, "TRAMVETINH_ID": this.model.TRAMVETINH_ID,
          "TTHD_ID": this.global.tthd_id, "TRAMTB_ID": this.model.tramtb_id, "NE": this.model.ne, "DOICAPNGON": this.model.doicap_p, "DOICAPGOC": this.model.doicapgoc, "HOPCAPNGON": this.model.hopcap_p, "TUCAPNGON": this.model.tucap_p, "TUYENCAP": this.model.tuyencap,
          "MADOICAP": this.model.madoicap ? this.model.madoicap : 0, "LIENTU": this.model.lientu, "CAPGOC": this.model.cap_goc, "CAPNGON": this.model.cap_p, "TUCAPGOC": this.model.tucap_goc, "LOAICAP": this.model.loai_cap, "SOMETDAY": this.model.culy, "STATUS": this.model.status, "TEN_DV": this.model.ten_dv
        }]

        let params = {
          ds_in: JSON.stringify(data),
          dt: JSON.stringify(dt),
          "ngay_bg": this.model.ngay_ht_gp.length > 12 ? this.model.ngay_ht_gp.length : (this.model.ngay_ht_gp + ' 00:00:00'), "ngay_gv": this.model.ngaygiao.length > 12 ? this.model.ngaygiao : (this.model.ngaygiao + ' 00:00:00'), "key_parameter": "xx"
        }
        let hoancong = await api.fn_thuchien_hoancong_hoancongcodich(this.axios, params)
        this.$toast.success((hoancong.data && hoancong.data.data != '1') ? hoancong.data.data : "Hoàn công thành công")
        this.getDanhsachThuebao()
      } catch (e) {
        console.log(e)
        this.$toast.error("Hoàn công thất bại")
      }
    },
    giaoviec_success() {
      this.getDanhsachThuebao();
    },
    getNE(value) {
      if (value) {
        this.model.ne = value.sone
        this.model.ne_id = value.ne_id
        this.model.tramtb_id = value.tramtb_id
        this.clickNE()
        this.showModal = false;
      }
    },
    changeNgayGV() {
      this.dateVisiable.dtpNgayGV = this.dateVisiable.chkNgayGV;
    },
    changeNgayBG() {
      this.dateVisiable.dtpNgayBG = this.dateVisiable.chkNgayBG;
    },
    async getTongtien() {
      let data = [];
      if (this.model.hdkh_id) {
        try {
          data = await api.lay_tongtien_theo_hdkh_id(this.axios, {
            hdkh_id: this.model.hdkh_id,
          });
        } catch (err) {
          this.$set(this.model, "tongtien", "");
        }
      }
      if (data.data && data.data.data.length > 0) {
        this.$set(
          this.model,
          "tongtien",
          data.data.data[0].tongtien ? data.data.data[0].tongtien : ""
        );
      }
    },
    async getThongtinLH() {
      let data = [];
      if (this.model.ctv_id) {
        try {
          data = await api.fn_lay_thongtin_lh_lapdat(this.axios, {
            hdkh_id: this.model.hdkh_id,
          });
        } catch (err) {
          this.$set(this.model, "thongtin_lh", "");
        }
      }
      if (data.data && data.data.data.length > 0) {
        this.$set(
          this.model,
          "thongtin_lh",
          data.data.data ? data.data.data : ""
        );
      }
    },
    async getCTV() {
      let data = [];
      if (this.model.ctv_id) {
        try {
          data = await api.sp_lay_thongtin_nhanvien_dv(this.axios, {
            nhanvien_id: this.model.ctv_id,
          });
        } catch (err) {
          this.$set(this.model, "ten_ctv", "");
        }
      }
      if (data.data && data.data.data.length > 0) {
        this.$set(
          this.model,
          "ten_ctv",
          data.data.data[0].nhanvien ? data.data.data[0].nhanvien : ""
        );
      }
    },
    async getNhanvienYC() {
      let data = [];
      if (this.model.nhanvien_id) {
        try {
          data = await api.sp_lay_thongtin_nhanvien_dv(this.axios, {
            nhanvien_id: this.model.nhanvien_id,
          });
        } catch (err) {
          this.$set(this.model, "ten_nhanvien_yc", "");
        }
      }
      if (data.data && data.data.data.length > 0) {
        this.$set(
          this.model,
          "ten_nhanvien_yc",
          data.data.data[0].nhanvien ? data.data.data[0].nhanvien : ""
        );
      }
    },
    setDefaultCombobox() {
      return false;
      if (this.options.loai_hd) {
        this.$set(this, "loaihd_id", this.options.loai_hd[0]["loaihd_id"]);
      }
      if (this.options.nhanvien.length > 0) {
        let nhanvienID = this.$root.token.getNhanVienID()
        let check = this.options.nhanvien.filter(item => item.nhanvien_id == nhanvienID)
        if (check.length > 0) {
          this.$set(
            this.model,
            "nguoigv_id",
            Number(nhanvienID)
          );
        } else {
          this.$set(
            this.model,
            "nguoigv_id",
            Number(this.options.nhanvien[0]["nhanvien_id"])
          );
        }
      }
      if (this.options.loai_cap.length > 0) {
        this.$set(this, "loaicap_id", this.options.loai_cap[0]["loaicap_id"]);
      }
      if (this.options.kieu_hd.length > 0) {
        //this.$set(this, "kieuhd_id", this.options.kieu_hd[0]["kieuhd_id"]);
      }
      if (this.options.kenhthu.length > 0) {
        //this.$set(this, "kenhthu_id", this.options.kenhthu[0]["kenhthu_id"]);
      }
    },
    resetModel() {
      for (let index in MODEL) {
        this.$set(this.model, index, MODEL[index]);
      }

      this.options.dsNhanvien = [];
      this.options.dsDGVT = [];
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
    async openMapTB(dia_chi, type) {
      let data = {
        lat: null,
        lng: null,
      };
      this.map_info = {
        dia_chi: dia_chi,
        title: dia_chi
      }
      if (type == "diachi_tb") {
        this.modeMap = 2;
        let kinhdo = await api.kiemtra_dulieu(this.axios, {
          inDk: "where hdtb_id = " + this.model.hdtb_id,
          inIdNeo: "kinhdo",
          inTable: "css.diachi_hdtb",
        });
        if (kinhdo.data.data) {
          data.lng = Number(kinhdo.data.data) > 0 ? Number(kinhdo.data.data) : null;
        }

        let vido = await api.kiemtra_dulieu(this.axios, {
          inDk: "where hdtb_id = " + this.model.hdtb_id,
          inIdNeo: "vido",
          inTable: "css.diachi_hdtb",
        });
        if (vido.data.data) {
          data.lat = Number(vido.data.data) > 0 ? Number(vido.data.data) : null;
        }
      } else {
        this.modeMap = 1;
        let kinhdo = await api.kiemtra_dulieu(this.axios, {
          inDk: "where hdtb_id = " + this.model.hdtb_id,
          inIdNeo: "kinhdo_ld",
          inTable: "css.diachi_hdtb",
        });
        if (kinhdo.data.data) {
          data.lng = Number(kinhdo.data.data) > 0 ? Number(kinhdo.data.data) : null;
        }

        let vido = await api.kiemtra_dulieu(this.axios, {
          inDk: "where hdtb_id = " + this.model.hdtb_id,
          inIdNeo: "vido_ld",
          inTable: "css.diachi_hdtb",
        });
        if (vido.data.data) {
          data.lat = Number(vido.data.data) > 0 ? Number(vido.data.data) : null;
        }
      }

      this.map_position = data;

      this.$bvModal.show('ModalChonToaDo')
    },
    preventSubmit() {
      return false;
    },
    initValidate() {
      var options = {
        rules: {
          capgoc: { required: true },
          tucap_goc: { required: true },
          doicap_goc: { required: true },
          doicap_p: { required: true },
          cap_p: { required: true },
          culy: { required: true },
        },
        customPlacement: function (inputElement, errorElement) {
          return [];
        },
        validationComplete: function (args) {
          console.log(args);
          if (args.status === "success") {
            if (args.element.parentNode.classList.contains("validate-error"))
              args.element.parentNode.classList.remove("validate-error");
          } else {
            if (!args.element.parentNode.classList.contains("validate-error"))
              args.element.parentNode.classList.add("validate-error");
          }
        },
      };
      // defines FormValidator to validate the TimePicker
      this.formObj = new FormValidator("#form-element", options);
    },
    async getDVGT() {
      let data = await api.getDsDV(this.axios, { hdtb_id: this.model.hdtb_id });
      this.options.dsDGVT = data.data.data ? data.data.data : [];
    },
    async init() {
      try {
        //Lấy tham số mặc định
        let thamsoMD = await api.getThamSoMacDinh(this.axios, {
          ds_ma_ts: '["KHONGHOANCONG_CHUA_THANHTOAN","USING_CABMAN_2.0"]',
        });
        if (thamsoMD.length > 0) {
          if (thamsoMD[0].ten_ts && thamsoMD[0].ten_ts == "1") {
            this.khong_hoancong_chua_thanhtoan = 1;
          }
        }
        //Lấy danh sách combobox
        //await this.getComboBox();
        //await this.getDanhsachThuebao();
      } catch (e) {
        console.log(e);
        this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
      }
    },
    async getDSNV() {
      let data = await api.getDsNV(this.axios, {
        phieu_id: Number(this.model.phieu_id),
        nhanvien_id: Number(this.model.nhanvien_id),
        kieu_id: 3,
      });
      this.options.dsNhanvien = data.data.data ? data.data.data : [];
      if (this.options.dsNhanvien.length > 0) {
        this.dateVisiable.chkNgayGV = true;
        this.dateVisiable.chkNgayGVEnable = true;
        this.model.ngaygiao = this.options.dsNhanvien[0].ngaygiao ? this.options.dsNhanvien[0].ngaygiao : new Date().toLocaleDateString('en-GB');
      }
    },
    async huykichhoat() {
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn hủy kích hoạt Số máy ' + this.model.ma_tb + ' không?', {
        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
      }).then(async v => {
        if (!v) {
          return false
        }
      });
      if (!this.visiable.huykichhoat) return false;
      this.loading(true);
      try {
        await api.capnhat_huykh_status(this.axios, {
          hdtb_id: this.model.hdtb_id,
          ma_tb: this.model.ma_tb,
          ngay_kh: new Date().toLocaleDateString('en-GB'),
          status: TRANGTHAI_DONGBO.CHUADONGBO,
          nguoi_kh: this.$root.token.getUserName(),
          ip_kh: "x"
        });

        let huykh = await api.fn_lay_tt_huy_kh_codinh(this.axios, {
          ds_para:
            '{"LOAIHD_ID":' +
            this.model.loaihd_id +
            ',"DICHVUVT_ID":' +
            this.global.dichvuvt_id +
            ',"LOAITB_ID":' +
            this.model.loaitb_id +
            ',"THUEBAO_ID":' +
            this.model.thuebao_id +
            ',"DIACHILD_ID":' +
            null +
            "} ",
        });
        if (huykh.data.data) {
          let parse = JSON.parse(huykh.data.data)
          this.$toast.success(parse.MESSAGE ? parse.MESSAGE : 'Hủy kích hoạt thành công!');
        } else {
          this.$toast.success("Hủy kích hoạt thành công. !");
        }
        await this.getDanhsachThuebao();
      } catch (err) {
        console.log(err)
        this.$toast.error(
          'Hủy kích hoạt không thành công. !nLỗi tổng đài trả về: " ' +
          ((err.data && err.data.message) ? err.data.message : '') +
          '"'
        );
      }
      this.loading(false);
    },
    async kichhoat() {
      if (!this.visiable.kichhoat) return false;
      this.loading(true);
      try {
        let ds_in = [];
        this.dsgv.forEach((item) => {
          ds_in.push({
            HDTB_CHA_ID: item.hdtb_cha_id,
            HDTB_ID: item.hdtb_id,
            PHIEU_ID: this.model.phieu_id,
            HDKH_ID: this.model.hdkh_id,
            THUEBAO_CHA_ID: item.hdtb_cha_id,
          });
        });
        let kichhoat = await api.kichhoat(this.axios, {
          hdtb_id: this.model.hdtb_id,
          loaitb_id: this.model.loaitb_id,
          luong_id: this.luong_id,
          sochinh: 1,
          thangsau: "202111",
          ngay_ht: this.model.ngay_ht_gp,
          ds_in: JSON.stringify(ds_in),
          nguoi_cn: this.model.nhanvien_id,
          ip_cn: this.model.nhanvien_id,
        });

        let status = await api.updateStatus(this.axios, {
          hdtb_id: this.model.hdtb_id,
          luong_id: this.luong_id,
          thangsau: "202111",
          ngay_ht: this.model.ngay_ht_gp + ' 00:00:00',
          nguoi_cn: this.model.nhanvien_id,
          ip_cn: this.model.nhanvien_id,
        });

        this.loading(false);
        await this.getDanhsachThuebao();
        this.$toast.success("Kích hoạt thành công. !");
      } catch (err) {
        console.log(err);
        this.$toast.error(
          'Kích hoạt không thành công. !nLỗi tổng đài trả về: " ' +
          (err.data && err.data.message ? err.data.message : "") +
          '"'
        );
        this.loading(false);
      }
    },
    async getComboBox() {
      this.loading(true);
      try {
        await api
          .getComboBox(this.axios, {
            chuoi:
              "KENHTHU|KIEU_HD|LOAI_HD|LOAI_CAP|NHANVIEN-donvi_id = " + this.$root.token.getDonViID() + "|QUYTRINH",
          })
          .then((res) => {
            let data = res.data.data ? res.data.data : [];
            for (let index in data) {
              this.$set(this.options, index, data[index]);
            }

            if (this.options.quytrinh) {
              this.dsQuyTrinh = this.options.quytrinh;
            }
            this.setDefaultCombobox();
          });

        //lấy danh sách loại đơn vị
        //let loaidonvi = await api.getComboBoxLoaiDV(this.axios)
        //this.options.loaidonvi = loaidonvi.data ? loaidonvi.data.data : [];
      } catch (err) {
        this.loading(false);
      }
      this.loading(false);
    },
    async updateNDTH() {
      await api
        .updateNDTH(this.axios, {
          phieu_id: this.model.phieu_id,
          noidung: this.model.nd_thuchien,
        })
        .catch((err) => {
          this.$toast.error('Có lỗi: " ' + err.data.message + '"');
        })
        .then((res) => {
          this.getDanhsachThuebao();
          this.$toast.success("Lưu thành công");
        });
    },
    async clickNE() {
      try {
        this.axios
          .post("/web-ccdv/khaibaocodinh/fn_chon_ne_hc_codinh", {
            phanvung_id: this.$root.token.getPhanVungID(),
            luong_id: this.luong_id,
            tramtb_id: this.model.tramtb_id,
            ne_id: this.model.ne_id,
            hdtb_id: this.model.hdtb_id,
            ma_tb: this.model.ma_tb,
            ne: this.model.ne
          })
          .then((res) => {
            this.$toast.success("Cập nhập ne thành công")
            this.getDanhsachThuebao();
          });
      } catch (e) {
        this.$toast.error("Cập nhập ne lỗi")
      }
    },
    async Netudong() {
      try {
        let check = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "CAPNHAT_NE",
        });
        let chonne = check.data.data ? check.data.data : 0
        let data = { "TRAMVT_ID": this.model.tramtb_id, "HOST_ID": 1, "HDTB_ID": this.HOST_ID, "MA_TB": this.model.ma_tb, "CAPNHAT_NE": chonne }
        let giaone = await this.axios.post('web-ccdv/khaibaocodinh/fn_chon_ne_tudong_hc_codinh', { "ds_param": JSON.stringify(data) })
        if (giaone.data.data) {
          let capnhat = JSON.parse(giaone.data.data)
          this.$toast.success(capnhat['MESSAGE'] ? String(capnhat['MESSAGE']) : 'Lưu Ne tự động thành công')
        }
      } catch (e) {
        this.$toast.error(e.data ? e.data.message : 'Có lỗi xảy ra')
      }
    },
    async getThongSoKT() {
      let data = await api.getThongSoKT(this.axios, {
        hdtb_id: this.model.hdtb_id,
      });
      this.thongso_html = data.data ? data.data.data : [];
    },
    select_selectedItemsChangedDagan(value) {
      this.dsgv = value;
    },
    async focusItem(data) {
      this.loading(true);
      this.resetModel();
      this.setDefaultCombobox();
      this.thongso_html = "";
      if (data.data) {
        let keys = ["ngaygiao", "ngay_ht_gp"];
        for (let row in data.data) {
          if (keys.includes(row)) {
            let date = data.data[row];
            if (date) {
              if (date.includes(".0")) {
                date = new Date(date.replace(".0", "")).toLocaleDateString('en-GB');
              } else {
                let arr = date.split(" ");
                if (arr.length > 0) {
                  date = arr[0];
                }
              }
              console.log(date);
            }
            if (date) {
              this.$set(this.model, row, date);
            } else {
              console.log(new Date().toLocaleDateString())
              this.$set(this.model, row, new Date().toLocaleDateString());
            }
          } else {
            this.$set(this.model, row, data.data[row]);
          }
        }
        //lấy danh sách vệ tinh
        let dsVetinh = await api.getDsVetinh(this.axios, {
          donvi_id: 0,
          loaidv_ql: 5,
          loaidv_id: 25,
        });
        this.$set(
          this.options,
          "vetinh",
          dsVetinh.data ? dsVetinh.data.data : []
        );

        await this.getDSNV();
        await this.getDVGT();
        await this.hienthiTThopdong();
        await this.getCTV();
        await this.getThongtinLH();
        await this.getNhanvienYC();
        await this.getTongtien();
        await this.getThongSoKT();
      }

      this.loading(false);
    },
    resetForm() {
      let item = [
        'ma_gd',
        'ma_tb',
        'ma_tb_cu',
        'ten_tb',
        'ten_kv',
        'ten_kieuld',
        'so_dt',
        'phanloaikh',
        'diachi_kh',
        'diachi_ld',
        'ngaygiao',
        'ngay_ht_gp',
        'nd_giao',
        'nd_thuchien',
        'ghichu',
        'tg_thueso',
        'so_truot',
        'noidung_tb',
        'tongtien',
        'diachi_tb',
        'NHANVIEN',
        'NV_TIEPTHI',
        'thongtin_lh'
      ]
      item.forEach(i => this.$set(this.model, i, ''))

      this.options.dsDGVT = []
      this.options.dsNhanvien = []
      this.thongso_html = []
    },
    async getDanhsachThuebao(enter = false, type = '') {
      if (!this.huonggiao_id)
        return false;
      this.loading(true);
      this.loadingTB = true;
      let timKiem = ''
      if (type) {
        timKiem = type == 'ma_gd' ? this.model.ma_gd : this.model.ma_tb
      }

      let data = {
        phanvung_id: this.$root.token.getPhanVungID(),
        dichvuvt_id: this.global.dichvuvt_id,
        tthd_id: this.global.tthd_id,
        //tthd_id: 1,
        nhanvien_id: this.$root.token.getNhanVienID(),
        //nhanvien_id: 7009,
        ma_gd: timKiem,
        phieu_id: 0,
        loaihd_id: this.model.loaihd_id,
        caidat_dv: 0,
        kieu_id: 0,
        huonggiao_id: this.huonggiao_id,
        //huonggiao_id: 12,
        loai_id: Number(this.button.kieu),
        loaidv_id: this.model.loaidv_id ? this.model.loaidv_id : 0
      };
      try {
        this.resetForm()
        let index = this.$refs.daganThueBao.$refs.grid.getSelectedRowIndexes();
        let old_index = 0;
        if (index.length > 0) {
          old_index = index[0];
        }
        if (this.timkiem_checkbox) {
          data = {
            "dichvuvt_id": this.global.dichvuvt_id, "tthd_id": this.global.tthd_id, "nhanvien_id": this.$root.token.getNhanVienID(), "ma_gd": "",
            "phieu_id": 0, "loaihd_id": this.model.loaihd_id, "caidat_dv": 0, "kieu_id": 0, "huonggiao_id": this.huonggiao_id,
            "loai_id": Number(this.button.kieu), "loaidv_id": this.model.loaidv_id ? this.model.loaidv_id : 0, "donvi_tk_id": this.model.donvi_tk_id ? this.model.donvi_tk_id : 0
          }
          await api.getDsTBDV(this.axios, data).then((res) => {
            this.options.dsThuebao = res.data.data ? res.data.data : [];
          });
        } else {
          await api.getDsTB(this.axios, data).then((res) => {
            this.options.dsThuebao = res.data.data ? res.data.data : [];
          });
        }

        if (this.options.dsThuebao.length <= 0) {
          this.loading(false);
        }
        if (this.options.dsThuebao.length > 0) {
          if (old_index && old_index <= this.options.dsThuebao.length) {
            this.$refs.daganThueBao.setCurrentSelectedRow(old_index);
          } else {
            this.loading(false);
          }
        } else {
          this.loading(false);
        }
        console.log('không tắt loading')
      } catch (error) {
        this.loading(false);
        this.$toast.error(
          error.data ? error.data.message : "Lấy danh sách thất bại"
        );
      }
      this.loadingTB = false;
    },
    async hienthiTThopdong() {
      try {

        this.dateVisiable = {
          chkNgayBG: false,
          dtpNgayBG: false,
          chkNgayBGEnable: true,
          chkNgayGV: false,
          chkNgayGVEnable: true,
          dtpNgayGV: false,
        };

        let params = {
          HDKH_ID: this.model.hdkh_id,
          NHANVIEN_ID: this.model.nhanvien_id,
          LYDOTRA_CON_ID: this.model.lydotra_con_id,
          NE_ID: this.model.ne,
          HDTB_ID: this.model.hdtb_id,
          PHANLOAIKH_ID: this.model.phanloaikh_id,
          MADOICAP: this.model.madoicap,
          HIENTHI_HOST: 0,
          LOAITB_ID: this.button.kieu,
        };
        let check = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "HIENTHI_HOST",
        });
        if (check.data.data == "1") {
          params["HIENTHI_HOST"] = 1;
        }
        let data = {
          ds_para: JSON.stringify(params),
        };
        api.hienthiTThopdong(this.axios, data).then((res) => {
          if (res.data.data) {
            let data = JSON.parse(res.data.data);
            if (data.RESULT) {
              for (let index in data.RESULT) {
                if (index == "KENHTHU_ID") {
                  if (!Number(data.RESULT[index])) {
                    //this.kenhthu_id = this.options.kenhthu[0].kenhthu_id;
                  } else {
                    this.$set(this.model, index, Number(data.RESULT[index]));
                  }
                } else {
                  this.$set(this.model, index, data.RESULT[index]);
                }
              }
            }
          }
        });

        let check_kh = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "KICHHOAT_TD",
        });
        if (check_kh.data && check_kh.data.data == "1") {
          if (this.model.ngay_ht_gp) {
            if (this.model.status != TRANGTHAI_DONGBO.DONGBO_SERVICE) {
              this.Enable("tsbtnGiaoPhieu", true)
              this.Enable("tsbtnKichHoat", false)
            } else {
              this.Enable("tsbtnGiaoPhieu", false)
              this.Enable("tsbtnKichHoat", true)
            }
          } else {
            this.Enable("tsbtnGiaoPhieu", false)
            this.Enable("tsbtnKichHoat", true)
          }
        }

        if (await this.phailam("TSBTNHUYKH")) {
          this.Visiable("tsbtnHuyKH", true)
        } else {
          this.Visiable("tsbtnHuyKH", false)
        }

        if (await this.phailam("TSBTNTHONGTINLH")) {
          this.Visiable("tsbtnThongTinLH", true)
        } else {
          this.Visiable("tsbtnThongTinLH", false)
        }

        if (this.model.status) {
          if (this.model.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
            this.Visiable("tsbtnHuyKH", true)
          } else {
            this.Visiable("tsbtnHuyKH", true)
          }
        }

        if (this.phailam("KICHHOAT_TD")) {
          if (this.model["ngay_ht_gp"]) {
            if (this.model.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
              this.Enable("tsbtnGiaoPhieu", true)
              this.Enable("tsbtnKichHoat", false)
            }
            else {
              this.Enable("tsbtnGiaoPhieu", false)
              this.Enable("tsbtnKichHoat", true)
            }
          }
          else {
            this.Enable("tsbtnGiaoPhieu", false)
            this.Enable("tsbtnKichHoat", true)
          }
        }

        //Nếu phải giao phiếu
        if (this.phailam("GIAOPHIEU")) {
          if (this.model["ngay_ht_gp"]) {
            if (this.phailam("KICHHOAT_TD")) {
              if (this.model.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
              this.Enable("tsbtnGiaoPhieu", true)
              this.Enable("tsbtnKichHoat", false)
              } else {
                this.Enable("tsbtnGiaoPhieu", false)
                this.Enable("tsbtnKichHoat", true)
              }
            }
            else
              this.Enable("tsbtnGiaoPhieu", true)
          }
          else {
             this.Enable("tsbtnGiaoPhieu", false)
                this.Enable("tsbtnKichHoat", false)
          }
        }

        let check_giaoviec = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "GIAOVIEC",
        });
        this.dateVisiable.dtpNgayBG = true;
        this.dateVisiable.chkNgayBG = true;

        if (check_giaoviec.data && check_giaoviec.data.data == "1") {
          this.dateVisiable.chkNgayBG = true;
          this.dateVisiable.dtpNgayBG = false;
          this.dateVisiable.chkNgayBGEnable = false;

          this.dateVisiable.chkNgayGV = true;
          this.dateVisiable.chkNgayGVEnable = false;
          this.dateVisiable.dtpNgayGV = false;
        }

        let check_giaophieu = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "GIAOPHIEU",
        });
        if (check_giaophieu.data && check_giaophieu.data.data == "1") {
          if (this.model.ngay_ht_gp) {
            let check_kh = await api.phailam(this.axios, {
              luong_id: this.luong_id,
              code: "KICHHOAT_TD",
            });
            if (check_kh.data && check_kh.data.data == "1") {
              if (this.model.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
                this.visiable.giaophieu = true;
                this.visiable.kichhoat = false;
              } else {
                this.visiable.giaophieu = false;
                this.visiable.kichhoat = true;
              }
            } else {
              this.visiable.giaophieu = true;
            }
          } else {
            this.visiable.giaophieu = false;
            this.visiable.kichhoat = false;
          }
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Lấy thông tin thuê bao thất bại");
      }
    },
    async chuyento() {
      this.loading(true);
      await api
        .chuyento(this.axios, {
          phieu_id: this.model.phieu_id,
          baohong_id: 0,
          hdtb_id: this.model.hdtb_id,
          kieutbi: 1,
          status: this.model.status,
        })
        .then((res) => {
          if (res.data.data.length > 0) {
            this.$toast.error(
              "Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ"
            );
            return false
          }
          this.$refs.popupChuyenTo.openDialog(this.model.phieu_id, this.model.hdtb_id)
        });
      this.loading(false);
    },
    async giaoviec() {
      if (!this.model.nguoigv_id) {
        this.$toast.error("Chưa chọn người giao việc")
        return false
      }
      this.$refs.formgiaphieu.init()
      this.$refs.giaophieunhanvien.show()
      return false
      try {
        await api.sp_delete_giaophieu_nv(this.axios, {
          phieu_id: this.model.phieu_id,
        });
        await api.sp_insert_giaophieu_nv(this.axios, {
          json_giaophieu_nv: JSON.stringify([
            {
              PHANVUNG_ID: this.$root.token.getPhanVungID(),
              PHIEU_ID: this.model.phieu_id,
              NHANVIEN_GIAO_ID: this.model.nguoigv_id,
              NGAYGIAO: this.model.ngaygiao,
              NHANVIEN_TH_ID: this.model.nhanvien_id,
              NHIEMVU: "Kéo dây, Cài đặt, Khai báo thông số",
              GHICHU: "Kéo dây, Cài đặt, Khai báo thông số",
              SO_DT: this.model.so_dt,
            },
          ]),
        });
        await this.getDSNV();
        this.$toast.success("Giao việc thành công!");
      } catch (e) {
        this.$toast.error(e.data ? e.data.message : "Giao việc thất bại!");
      }
    },
    async giaoviectudong() {
      let sochinh = await api.phailam(this.axios, {
        luong_id: this.luong_id,
        code: "GIAOVIEC",
      });
      let giaoviec_nvql_cap = await api.phailam(this.axios, {
        luong_id: this.luong_id,
        code: "GIAOVIEC_NVQL_CAP",
      });
      let ds_phieu = [];
      this.dsgv.forEach((item) => ds_phieu.push({ ID: item.phieu_id }));
      let param = {
        TTHD_ID: this.model.tth_id,
        LOAITB_ID: this.model.loaitb_id,
        NGUOIGV_ID: this.model.nguoigv_id,
        NGAYGIAO: this.model.ngaygiao,
        DS_PHIEU_ID: ds_phieu,
        PHIEU_ID: this.model.phieu_id,
        NHANVIEN_ID: this.$root.token.getNhanVienID(),
        HDTB_ID: this.model.hdtb_id,
        SOCHINH: sochinh.data.data,
        GIAOVIEC_NVQL_CAP: giaoviec_nvql_cap.data.data,
      };
      await api.fn_tudong_giaoviec_hc_codinh(this.axios, {
        ds_para: JSON.stringify(param),
      });
    },
    async capnhat() {
      this.loading(true);
      try {
        let ktgiaoviec = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "GIAOVIEC",
        });
        if (ktgiaoviec.data && ktgiaoviec.data.data == "1") {
          await this.giaoviectudong();
        }

        let data = {
          ds_in: [],
          dt: [],
          ngay_bg: "",
          ngay_gv: "",
        };
        let sochin =
          this.model.hdtb_cha_id || this.model.thuebao_cha_id ? 0 : 1;
        data.dt.push({
          HDTB_ID: this.model.hdtb_id,
          HDKH_ID: this.model.hdkh_id,
          THUEBAO_CHA_ID: this.model.thuebao_cha_id,
          MA_TB: this.model.ma_tb,
          LUONG_ID: this.luong_id,
          SOCHINH: sochin,
          LOAITB_ID: this.model.loaitb_id,
          LOAIHD_ID: this.model.loaihd_id,
          TRAMVETINH_ID: this.model.tramtb_id,
          TRAMTB_ID: this.model.tramtb_id,
          NE_ID: this.model.ne_id,
          NE: this.model.ne,
          TTHD_ID: this.global.tthd_id,
          DOICAPNGON: this.model.doicap_p,
          DOICAPGOC: this.model.doicap_goc,
          HOPCAPNGON: this.model.hopcap_p,
          TUCAPNGON: this.model.tucap_p,
          TUYENCAP: this.model.tuyencap,
          LIENTU: this.model.lientu,
          CAPGOC: this.model.cap_goc,
          NE_ID_CU: this.model.ne_cu_id,
          CAPNGON: this.model.cap_p,
          TUCAPGOC: this.model.tucap_goc,
          LOAICAP: this.model.loaicap_id,
          SOMETDAY: this.model.culy,
          NHANVIEN_ID: this.model.nhanvien_id,
          NGUOI_GV: this.model.nguoigv_id,
          STATUS: this.model.status,
          PHIEU_ID: this.model.phieu_id,
        });
        this.dsgv.forEach((item) => {
          data.ds_in.push({
            HDTB_CHA_ID: item.hdtb_cha_id,
            HDTB_ID: item.hdtb_id,
            PHIEU_ID: this.model.phieu_id,
            HDKH_ID: this.model.hdkh_id,
            THUEBAO_CHA_ID: item.hdtb_cha_id,
          });
        });
        data.dt = JSON.stringify(data.dt);
        data.ds_in = JSON.stringify(data.ds_in);
        data.ngay_bg = this.model.ngay_ht_gp.split(" ")[0];
        data.ngay_gv = this.model.ngaygiao.split(" ")[0];
        await api.capnhat(this.axios, data);
        this.$toast.success("Cập nhật thành công!");
        this.getDanhsachThuebao();
      } catch (err) {
        this.$toast.error(
          "Cập nhập thất bại. '" + (err.data ? err.data.message : "") + "'"
        );
        this.loading(false);
      }
      this.loading(false);
    },
  },
  watch: {
    timkiem_checkbox() {
      this.getDanhsachThuebao();
    },
    loaihd_id(val) {
      // api
      //   .getComboBoxQuytrinh(this.axios, {
      //     dsloaihd_id: this.global.dsloaihd_id,
      //     tthd_id: this.global.tthd_id,
      //     kieu: 0,
      //     dichvuvt_id: this.global.dsdichvuvt_id,
      //   })
      //   .then((res) => {
      //     this.options.quytrinh = res.data.data ? res.data.data : [];
      //     if (
      //       this.options.quytrinh.length > 0 &&
      //       this.options.quytrinh[0]["quytrinh_id"]
      //     ) {
      //       console.log(this.options.quytrinh[0]);
      //       this.$set(
      //         this.model,
      //         "quytrinh",
      //         this.options.quytrinh[0]["quytrinh_id"]
      //       );
      //     }
      //   });
    },
    async "model.quytrinh"(val) {
      let dt = this.dsQuyTrinh.filter(
        (item) => val == item.quytrinh_id && item.tthd_id == this.global.tthd_id
      );


      if (dt.length > 0) {
        this.luong_id = dt[0].luong_id;
        this.huonggiao_id = dt[0].huonggiao_id;
        this.header.title = dt[0].huonggiao
        if (!this.loadingTB)
          console.log(this.huonggiao_id)
        this.getDanhsachThuebao();
      } else {
        this.options.dsNhanvien = []
      }
    },
    async "model.phanloaikh_id"(val) {
      let data = {};
      if (val) {
        try {
          data = await api.map_id(this.axios, {
            id_neo: "ma_plkh",
            in_table: "phanloai_kh",
            in_dk: "where phanloaikh_id=" + val,
          });
        } catch (err) {
          this.$set(this.model, "phanloaikh", "");
        }
      }
      this.$set(this.model, "phanloaikh", data.data ? data.data.data : "");
    },
    "model.loaidv_id"(val) {
      if (val) {
        api.getDsDonvi(this.axios, { loaidv_id: val }).then((res) => {
          this.$set(this.options, "donvi", res.data.data ? res.data.data : []);
          if (this.options.donvi && this.options.donvi.length > 0)
            this.$set(
              this.model,
              "donvi_tk_id",
              this.options.donvi[0].donvi_id
            );
        });
      }
    },
    "model.donvi_tk_id"(val) {
      this.getDanhsachThuebao();
    },
    "button.kieu"(val) {
      if (!this.loadingTB) this.getDanhsachThuebao();
    },
    "model.laythongtinhoancong"(val) {
      if (val == 1) {
        this.axios
          .post("web-hopdong/thanhly/thanh_ly_map_id", {
            id_neo: "ma_plkh",
            in_table: "phanloai_kh",
            in_dk: "where phanloaikh_id=" + this.model.phanloaikh_id,
          })
          .then((res) => {
            console.log(res);
          });

        if (!this.model.kieuld_id || !this.model.madoicap) {
          this.$bvModal.msgBoxConfirm('Không tìm thấy thông tin!', {
            title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
          })
        }

        this.axios
          .post("web-ccdv/khaibaocodinh/lay_thongso_thicong", {
            ma_tb: this.model.ma_tb,
            madoicap: this.model.madoicap,
            kieu: 0,
          })
          .then((res) => {
            if (res.data.data.length > 0) {
              for (let index in res.data.data[0]) {
                this.$set(this.model, index, res.data.data[0][index]);
              }
            }
          });
      }
    },
  },
};
</script>
<style>
.btn.btn-chon-ne {
  padding: 2px 2px !important;
}
.space-between {
  width: 10px;
}
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  min-width: 720px !important;
}
.popup-box {
  background: #fafafa38;
}
.thongtinKT {
  padding: 10px;
  border: 1px solid #0176ff;
  margin: auto;
  width: 96%;
  margin-top: 5px;
  margin-bottom: 5px;
}

.non-ative a {
  color: #d3d3d3 !important;
}
.traphieu .page-content {
  position: unset;
}

.traphieu-content {
  width: 1200px;
}

.tongtien_input {
  color: #bb2f2f !important;
  font-weight: bold !important;
  text-align: right !important;
  text-indent: 10px !important;
}

.ne-input,
.laythongso {
  color: #832e2e !important;
  font-weight: bold !important;
  text-align: right !important;
}

.magiaodich_input input {
  font-weight: bold !important;
}

.ngaygiao {
  color: #bb2f2f !important;
}

.donvigiao {
  color: #406de9 !important;
}
</style>
