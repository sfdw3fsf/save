<template src="./template.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';
import api from "../../../contract/SyncData/SyncFixSubscribersToSwitchboard/api/index";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import {
  DichVuVienThong,
  LOAI_DV,
  LoaiHopDong,
  TRANGTHAI_DONGBO,
  TRANGTHAI_PH,
  TrangThaiHD
} from "../../complete_profile/CompleteTransferFile/Enum";
import moment from "moment";

export default {
  name: "SyncFixSubscribersToSwitchboard",
  components: {breadcrumb, GiaoPhieuView},
  data() {
    return {
      header: {
        title: 'Đồng bộ thuê bao Cố định lên Tổng đài',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {name: 'Lắp đặt mới', link: {name: 'Ui.cards'}},
        ]
      },
      waterMark: "__/__/____",
      dateVal: new Date().toLocaleString('en-GB', {dateStyle: 'short'}),
      dateFormat: "DD/MM/YYYY",

      //KHai bao
      phieu_id: null,
      loaihd_id: null,
      hdtb_id: null,
      tb_mytv: "",
      tb_mega: "",
      tb_cd: "",
      madoicap: "",

      loaitb_id: 0,
      dichvuvt_id: 0,
      kieuld_id: 0,
      tthd_id: 0,
      status: 0,
      async_status: false,

      goi_id: 0,
      cd: "",
      gp: "",
      dd: "",
      mega: "",
      mytv: "",
      ftth: "",
      dvnhan_id: 0,

      _dichvuvt_id: 0,
      luong_id: 0,
      quytrinh_id: 0,

      donvi_id: 0,
      tramtb_id: 0,
      nIndex: null,
      kt_load: false,
      huonggiao_id: 0,
      dsloaihd_id: "",
      dsdichvuvt_id: "",

      //UI
      cboDichVuVT: {
        option: [],
        value: null,
      },
      cboQuyTrinh: {
        option: [],
        value: null,
      },
      rdoDaKH: false,
      rdoChuaKH: true,
      txtGhiChu: '',
      dtpNgayThucHien: new Date().toLocaleString('en-GB', {dateStyle: 'short'}),
      txtTenKH: '',
      dtpNgayYC: new Date().toLocaleString('en-GB', {dateStyle: 'short'}),
      txtDiaChiKH: '',
      txtMaKH: '',
      txtMaGD: '',
      cboLoaiHD: {
        option: [],
        value: null,
      },
      lvwDVGT: [],
      txtMaTB_Cu: '',
      chkLoaiTB: false,
      cboLoaiHinhTB: {
        option: [],
        value: null,
      },
      txtSoMayTN: '',
      txtMaTB: '',
      cboKieuLD: {
        option: [],
        value: null,
      },
      dgvLydohuy: [],
      dgvThueBao: [],
      pnlDoiDV: true,
      pnlThanhLy: false,
      rowSelected: null,
      listRowSelected: [],
      tsbtnHoanCong: false,
      tsbtnGiaoPhieu: false,
      tsbtnKichHoat: false,
      tsbtnHuyKH: false,
      dtThaoTac: [],
      dtControl: [],
      dsDK_DVGT: [],
      selectedIds: [],

      dialogComponent: null,
      dialogEvts: {
        'close': this.dlgClose
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': function(){
          console.log("form-close")
        },
      },
      state: {

      },
      position: { X: 'center', Y: 'top' }, 
      giaoPhieuViewDialog: {
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1,
        }
      },
      animationSettings: { effect: 'None' },
    }
  },
  computed: {
    donViId() {
      return +this.$root.token.getDonViID()
    },
    nhanvienId() {
      return +this.$root.token.getNhanVienID()
    },
    username() {
      return this.$root.token.getUserName()
    },
    donViDLId() {
      return this.$root.token.getDonViDuLieuID()
    },
    phanvungId() {
      return this.$root.token.getPhanVungID()
    }
  },
  async created() {
    // await this.getListDichVuVT();
    // await this.getListLoaiHD();
    // await this.getListLoaiTB();

    await this.frmDongBoCoDinh_Load();
  },
  methods: {
    //NEW
    async HT_DichVuVT_Combobox(cbo, dsdichvuvt_id) {
      try {
        cbo.option = []
        const response = await api.getDichVuVT(this.axios)
        const data = await response.data
        let dsdichvuvt = dsdichvuvt_id.split(',')
        if (data.error_code === 'BSS-00000000') {
          // cbo.option = data.data
          for (let i of data.data) {
            for (let j of dsdichvuvt) {
              if (i.DICHVUVT_ID !== null) {
                if (j === i.DICHVUVT_ID.toString()) {
                  cbo.option.push(i)
                }
              }
            }
          }
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].DICHVUVT_ID
            this.dichvuvt_id = cbo.value
          }
        } else {
          console.log('getDichVuVT')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        await this.cboDichVuVT_SelectedValueChanged();
      }
    },
    async HT_Loai_HD_Combobox(cbo, dsloaihd_id) {
      try {
        cbo.option = []
        const response = await api.getLoaiHD(this.axios)
        const data = await response.data
        let dsloaihd = dsloaihd_id.split(',')
        if (data.error_code === 'BSS-00000000') {
          // cbo.option = data.data
          for (let i of data.data) {
            for (let j of dsloaihd) {
              if (i.loaihd_id !== null) {
                if (j === i.loaihd_id.toString()) {
                  cbo.option.push(i)
                }
              }
            }
          }
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].loaihd_id
            this.loaihd_id = cbo.value
          }
        } else {
          console.log('getLoaiHD')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        await this.cboLoaiHD_SelectedValueChanged()
      }
    },
    async LAY_DS_KIEU_LD() {
      try {
        this.cboKieuLD.option = []
        let post = {
          "vloaihd_id": this.loaihd_id,
          "vloaitb_id": 0
        }
        const response = await api.getKieuYeuCau(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          this.cboKieuLD.option = data.data
          if (this.cboKieuLD.option.length > 0) {
            this.cboKieuLD.value = this.cboKieuLD.option[0].kieuld_id
          }
        } else {
          console.log('getKieuYeuCau')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_LoaiHinh_TB_Combobox(cbo, dsdichvuvt_id) {
      try {
        cbo.option = []
        const response = await api.getLoaiHinhTB(this.axios)
        const data = await response.data
        let dsdichvuvt = dsdichvuvt_id.split(',')
        if (data.error_code === 'BSS-00000000') {
          // cbo.option = data.data
          for (let i of data.data) {
            for (let j of dsdichvuvt) {
              if (j === i.LOAITB_ID.toString()) {
                cbo.option.push(i)
              }
            }
          }
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].LOAITB_ID
          }
        } else {
          console.log('getLoaiHinhTB')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_QuyTrinh_GDV_KH_Combobox(cbo, dichvuvt_id, loaihd_id) {
      try {
        cbo.option = []
        let post = {
          "ds_dichvu_vt": dichvuvt_id,
          "ds_loai_hd": loaihd_id
        }
        const response = await api.getQuyTrinh(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].quytrinh_id
          }
        } else {
          console.log('getQuyTrinh')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        // await this.cboQuyTrinh_SelectedValueChanged();
      }
    },
    async Lay_DS_ThaoTac() {
      try {
        let post = {
          "luong_id": this.luong_id
        }
        const response = await api.lay_luong_thaotac(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data != null) {
            this.dtThaoTac = data.data;
          }
        } else {
          console.log('lay_luong_thaotac')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async Lay_Control_ThaoTac(thaotac_id) {
      try {
        let post = {
          "thaotac_id": thaotac_id
        }
        const response = await api.lay_control(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data != null) {
            this.dtControl = data.data;
          }
        } else {
          console.log('lay_control')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async LAY_DS_DANGKY_DVGT(hdtb_id) {
      try {
        let post = {
          "vhdtb_id": hdtb_id
          // "vhdtb_id": 1216269
        }
        const response = await api.getDsDVGT(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data != null) {
            this.lvwDVGT = data.data;
          }
        } else {
          console.log('getDsDVGT')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async Lay_DS_LyDoHuy_HDTB(hdtb_id) {
      try {
        let post = {
          "hdtb_id": hdtb_id
          // "hdtb_id": 2338486
        }
        const response = await api.getDsHuy(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data != null) {
            this.dgvLydohuy = data.data;
          }
        } else {
          console.log('getDsHuy')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async LAY_DS_SUDUNG_DVGT(in_thuebao_id) {
      try {
        let post = {
          "in_thuebao_id": in_thuebao_id
        }
        const response = await api.LAY_DS_SUDUNG_DVGT(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data != null) {
            for (let i of data.data) {
              if (data.data.dichvugt_id === 621) {
                // Message_Box.ShowTB("Số máy " + txtMaTB.Text + " là số trượt, liên hệ bộ phận giao tiếp tiếp tổng đài để kích hoạt.");
                this.$root.$toast.error("Số máy " + this.txtMaTB + " là số trượt, liên hệ bộ phận giao tiếp tiếp tổng đài để kích hoạt.")
                //return;
              }
            }
          }
        } else {
          console.log('LAY_DS_SUDUNG_DVGT')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async SP_DONG_BO_TONGDAI_CD(in_thuebao_id, kieu_yc) {
      try {
        let post = {
          "luong_id": this.luong_id,
          "dvgt_id": 0,
          "kieuyc": kieu_yc,
          "matb": this.txtMaTB,
          "dongbo": 1,
          "thuebao_id": in_thuebao_id,
          "loaihd_id": TRANGTHAI_DONGBO.DONGBO_SERVICE,
          "hdtb_id": this.hdtb_id,
          "vip_kh": "",
          "vnguoi_kh": ""
        }
        const response = await api.SP_DONG_BO_TONGDAI_CD(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data !== null) {
            console.log(data.data.outtext);
            if (data.data.outtext === "0") {
              this.$root.$toast.success("Kích hoạt tổng đài thành công !");
            } else {
              this.$root.$toast.error("Kích hoạt tổng đài không thành công. !\nLỗi tổng đài trả về: " + data.data.outtext);
            }
          }
        } else {
          console.log('SP_DONG_BO_TONGDAI_CD')
          this.$root.$toast.error(data.message_detail);
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
        this.$root.$toast.error(e.message());
      } finally {
      }
    },

    async frmDongBoCoDinh_Load() {
      let str = "";
      const tag = this.$route.query.tag
      console.log(tag)
      try {
        try {
          if (tag !== null && tag !== '' && this.$route.query) {
            str = tag.toString();
            let words = str.split('+');
            if (words.length >= 1)
              this.tthd_id = Number(words[0]);
            if (words.length >= 2) {
              this.dsloaihd_id = words[1];
              //this.dsloaihd_id = this.dsloaihd_id.replace(';', ',');
              this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',');
            }
            if (words.length >= 3) {
              this.dsdichvuvt_id = words[2];
              this.dsdichvuvt_id = this.dsdichvuvt_id.replace(';', ',');
            }
          } else {
            this.tthd_id = 2;
            this.dsloaihd_id = "1";
            this.dsdichvuvt_id = "1";
          }
        } catch (e) {
          this.tthd_id = 2;
          this.dsloaihd_id = "4,7,14";
          this.dsdichvuvt_id = "4";
        }
        // console.log(this.dsdichvuvt_id, this.dsloaihd_id, this.tthd_id)
        await this.HT_DichVuVT_Combobox(this.cboDichVuVT, this.dsdichvuvt_id);
        await this.HT_Loai_HD_Combobox(this.cboLoaiHD, this.dsloaihd_id);
        await this.HT_Quytrinh(this.dichvuvt_id, this.loaihd_id);

        await this.Lay_TrangThai_HD();
        await this.HienThiDanhSachTB();
        // this.BeginInvoke(new MethodInvoker(Hienthi_Mau_DataGridView));
        this.kt_load = true;
      } catch (e) {
        this.$root.$toast.error("Có lỗi : " + e)
      }
    },

    async Lay_TrangThai_HD() {
      if (this.rdoChuaKH === true) {
        // console.log(Number(this.cboLoaiHD.value) , LoaiHopDong.CHAMDUT_SD, this.cboLoaiHD.value === LoaiHopDong.CHAMDUT_SD)
        if (Number(this.cboLoaiHD.value) === LoaiHopDong.CHAMDUT_SD) {
          this.tthd_id = TrangThaiHD.MOI;
        } else {
          this.tthd_id = TrangThaiHD.THANH_TOAN;
        }

        this.status = TRANGTHAI_DONGBO.CHUADONGBO;
      } else if (this.rdoDaKH === true) {
        this.status = TRANGTHAI_DONGBO.DONGBO_SERVICE;//status = 5
        if (this.cboLoaiHD.value === LoaiHopDong.CHAMDUT_SD) {
          this.tthd_id = TrangThaiHD.MOI;
        } else {
          this.tthd_id = TrangThaiHD.THANH_TOAN;
        }
      }
    },

    async HienThiDanhSachTB() {
      try {
        let vloaitb_id = 0;
        if (this.chkLoaiTB)
          vloaitb_id = this.cboLoaiHinhTB.value;
        this.dgvThueBao = []
        // let post = {
        //   "vloaihd_id": this.cboLoaiHD.value,
        //   "vtthd_id": this.tthd_id,
        //   "vstatus": this.status,
        //   "vnhanvien_id": this.nhanvienId,
        //   "vdonvi_id": this.donViId,
        //   "vdichvuvt_id": DichVuVienThong.CO_DINH,
        //   "vloaitb_id": vloaitb_id,
        //   "vquytrinh_id": this.quytrinh_id
        // }
        let post = {
          "vloaihd_id": Number(this.cboLoaiHD.value),
          "vtthd_id": this.tthd_id,
          "vstatus": this.status,
          "vnhanvien_id": this.nhanvienId,
          "vdonvi_id": this.donViId,
          "vdichvuvt_id": DichVuVienThong.CO_DINH,
          "vloaitb_id": vloaitb_id,
          "vquytrinh_id": this.quytrinh_id
        }
        const response = await api.LAY_DS_HDTB_GD_KICHHOAT_CD(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          this.dgvThueBao = data.data
          if (this.dgvThueBao.length > 0) {
            this.rowSelected = this.dgvThueBao[0];
            this.tsbtnKichHoat = !this.rdoDaKH;
            this.tsbtnHoanCong = this.rdoDaKH;
            // dgvThueBao.AutoGenerateColumns = false;
            // dgvThueBao.DataSource = ds.Tables[0];
            // cmUlt.Hienthi_Mau_DataGridView(dgvThueBao, 0, 0);
            // HienThiAnhTrangThai();
          }
        } else {
          this.txtMaGD = "";
          this.dtpNgayYC = new Date().toLocaleString('en-GB', {dateStyle: 'short'});
          this.txtMaKH = "";
          this.txtTenKH = "";
          this.txtDiaChiKH = "";
          this.txtMaTB_Cu = "";
          this.txtGhiChu = "";
          this.dgvThueBao = [];
          this.tsbtnKichHoat = false;
          this.tsbtnHoanCong = false;
          this.tsbtnGiaoPhieu = false;
          this.Clear();
          // this.$toast.success("Có lỗi: " + ex.ToString());
        }
      } catch (e) {
        this.$toast.error("Có lỗi: " + e.toString());
      } finally {
      }
    },

    async rdoChuaKH_CheckedChanged() {
      this.loading(true)
      this.rdoChuaKH = !this.rdoChuaKH
      this.rdoDaKH = !this.rdoChuaKH
      await this.Lay_TrangThai_HD();
      await this.HienThiDanhSachTB();
      this.tsbtnHoanCong = !this.rdoChuaKH;
      await this.HienThiHuyKH();
      this.loading(false)
    },
    async rdoDaKH_CheckedChanged() {
      this.loading(true)
      this.rdoDaKH = !this.rdoDaKH
      this.rdoChuaKH = !this.rdoDaKH
      await this.Lay_TrangThai_HD();
      await this.HienThiDanhSachTB();
      this.tsbtnKichHoat = !this.rdoDaKH;
      await this.HienThiHuyKH();
      this.loading(false)
    },

    async HienThiHuyKH() {
      this.tsbtnHuyKH = false;

      if (this.dgvThueBao.length > 0) {
        if (this.rdoChuaKH === true)//Nếu kick vào chưa kích hoạt thì ẩn nút
          this.tsbtnHuyKH = false;
        else {
          //if (cboLoaiHD.SelectedValue.ToString() == LoaiHopDong.CHAMDUT_SD.ToString())
          this.tsbtnHuyKH = true;
        }
      }
    },

    async cboDichVuVT_SelectedValueChanged() {
      if (this.cboDichVuVT.option.length > 0) {
        this.dichvuvt_id = this.cboDichVuVT.value;
        await this.HT_Quytrinh(this.dichvuvt_id, this.loaihd_id);
        await this.HT_LoaiHinh_TB_Combobox(this.cboLoaiHinhTB, this.dsdichvuvt_id);
      }
    },
    async cboLoaiHD_SelectedValueChanged() {
      if (this.cboLoaiHD.option.length > 0) {
        if (this.cboLoaiHD.value == null) return;
        this.loaihd_id = this.cboLoaiHD.value;
        await this.LAY_DS_KIEU_LD();
        if (+this.cboLoaiHD.value === LoaiHopDong.THAY_DOI_DV) {
          this.pnlDoiDV = true;
          this.pnlThanhLy = false;
        } else if (+this.cboLoaiHD.value === LoaiHopDong.DOISO_TB) {
          this.pnlDoiDV = false;
          this.pnlThanhLy = false;
        } else if (+this.cboLoaiHD.value === LoaiHopDong.CHAMDUT_SD) {
          this.pnlDoiDV = false;
          this.pnlThanhLy = true;
        }
        await this.HT_Quytrinh(this.dichvuvt_id, this.loaihd_id);
      }
    },

    async HT_Quytrinh(dichvuvt_id, loaihd_id) {
      await this.HT_QuyTrinh_GDV_KH_Combobox(this.cboQuyTrinh, dichvuvt_id, loaihd_id);
      await this.cboQuyTrinh_SelectedValueChanged()
    },
    async cboQuyTrinh_SelectedValueChanged() {
      if (this.cboQuyTrinh.option.length > 0) {
        if (this.cboQuyTrinh.value == null) return;
        this.quytrinh_id = this.cboQuyTrinh.value;

        try {
          let post = {
            quytrinh_id: this.quytrinh_id,
            tthd_id: 1
          }
          const response = await api.lay_huonggiao_theo_quytrinh(this.axios, post)
          const data = await response.data
          if (data.error_code === 'BSS-00000000') {
            if (data.data != null) {
              if (data.data.length > 0) {
                this.huonggiao_id = data.data[0].huonggiao_id;
                this.luong_id = data.data[0].luong_id;
              } else {
                this.$root.$toast.error("Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại")
                this.huonggiao_id = -1;
                await this.HienThiDanhSachTB();
                return;
              }
            }
            await this.HienThiGiaoDien(this.quytrinh_id);
            if (this.kt_load) {
              await this.Lay_TrangThai_HD();
              await this.HienThiDanhSachTB();
            }
          } else {
            console.log('lay_huonggiao_theo_quytrinh')
          }
        } catch (e) {
          console.log('Lấy danh sách không thành công')
        } finally {
        }
      }
    },

    async HienThiGiaoDien(quytrinh_id) {
      // await this.lay_luong_thaotac();
      this.tsbtnHoanCong = false;
      this.tsbtnGiaoPhieu = false;
      try {
        await this.Lay_DS_ThaoTac();
        // Lay ds Thao tac
        if (this.dtThaoTac.length > 0) {
          for (let i of this.dtThaoTac) {
            await this.Lay_Control_ThaoTac(i.thaotac_id)
            // Lay ds Control
            if (this.dtControl.length > 0) {
              for (let j = 0; j < this.dtControl.length; j++) {
                let control_name = this.dtControl[j].control_name;
                if (i.enable === 1) {
                  if (control_name === 'tsbtnGiaoPhieu') {
                    this.tsbtnGiaoPhieu = true
                  }
                  if (control_name === 'tsbtnKichHoat') {
                    this.tsbtnKichHoat = true
                  }
                  if (control_name === 'tsbtnHoanCong') {
                    this.tsbtnHoanCong = true
                  }
                  if (control_name === 'tsbtnGiaoViec') {
                    this.tsbtnGiaoPhieu = true
                  }
                }
              }
            }
          }
        }
      } catch (e) {
        this.$root.$toast.error("Có lỗi: " + e.toString())
      }
    },

    async tlstpRefresh_Click() {
      await this.Lay_TrangThai_HD();
      await this.HienThiDanhSachTB();
    },
    async tsbtnKichHoat_Click() {
      this.loading(true)
      await this.DongBo();
      this.loading(false)
    },
    async tsbtnGiaoPhieu_Click() {
      if(this.txtMaGD == ""){
        this.$toast.error("Bạn chưa chọn phiếu!");
        return;
      }
      try{ 
        var huongGiao = await commonFn.lay_huonggiao_theo_luong_id(
          this.axios,
          this.luong_id
        );
        this.loading(false)
        if (!huongGiao) {
          this.$toast.error("Không tìm thấy hướng giao");
          return;
        }
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: huongGiao.huonggiao_id*1,
          vloai_hd: this.loaihd_id,
          vdichvuvt_id: this.dichvuvt_id,
          vma_gd: this.txt,
          vquytrinh: this.quytrinh_id,
          vtrangthaiphieu: 1,
        };
        this.GiaoPhieuView_OnClick()

      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async giaophieu_success(){
      this.HienThiDanhSachTB();
      console.log('giaophieu_success - HienThiDanhSachTB')
    },
    async tsbtnHoanCong_Click() {
      this.listRowSelected = this.selectedIds
      this.loading(true)
      await this.Hoan_Cong();
      this.loading(false)
    },
    async HienThiDVGT(hdtb_id) {
      await this.LAY_DS_DANGKY_DVGT(hdtb_id);
    },
    PHAILAM: function (code) {
      code = code.trim().toUpperCase()
      if (this.dtThaoTac.filter((x) => x.enable === 1 && x.code === code).length > 0) {
        return true;
      }
      return false;
    },
    Clear() {
      this.dtpNgayThucHien = new Date().toLocaleString('en-GB', {dateStyle: 'short'});
      this.lvwDVGT = [];
      this.dgvLydohuy = [];
      this.txtMaTB_Cu = "";
    },
    async Hienthi_Mau_DataGridView() {

    },
    async DongBo() {
      // console.log(this.huonggiao_id, this.hdtb_id)
      this.loaitb_id = this.rowSelected.LOAITB_ID;
      this.kieuld_id = this.rowSelected.KIEULD_ID;
      this.hdtb_id = this.rowSelected.HDTB_ID;
      this.loaihd_id = this.cboLoaiHD.value;

      let in_thuebao_id = this.rowSelected.THUEBAO_ID;
      if (in_thuebao_id !== null) {
        await this.LAY_DS_SUDUNG_DVGT(in_thuebao_id);
      }
      if (this.cboLoaiHD.value.toString() === "4") {
        await this.SP_DONG_BO_TONGDAI_CD(in_thuebao_id, 0);
      } else if (this.cboLoaiHD.value.toString() === "7") {
        await this.SP_DONG_BO_TONGDAI_CD(in_thuebao_id, 1);
      }

      await this.HienThiDanhSachTB();
    },
    async Hoan_Cong() {
      // this.dgvThueBao = this.listRowSelected
      let date = new Date()
      //---------------------Hợp đồng thay đổi dịch vụ------------------------------------//
      if (this.cboLoaiHD.value.toString() === LoaiHopDong.THAY_DOI_DV.toString()) {
        if (this.PHAILAM("HOANCONG")) {
          let count = 0;
          this.dsDK_DVGT = [];
          await this.LAY_DS_DANGKY_DVGT(this.hdtb_id);
          this.dsDK_DVGT = this.lvwDVGT


          if (this.PHAILAM("TUDONG_HTHS")) {
            // let vkyhoadon = moment(date).format('YYYYMM')
            // let kt_taobang = await this.KT_TaoDB_Thang(vkyhoadon);
            // if (kt_taobang.toString() === "0") {
            //   this.$root.$toast.error("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !")
            //   return;
            // }

            let thangsau = moment(date).add(1, 'M').format('YYYYMM');
            let dbt = await this.KT_DBThang_DaChot(thangsau);
            if (dbt !== "0") {
              // Message_Box.ShowTB(dbt);
              this.$root.$toast.error(dbt)
              return;
            }
          }
          count = this.dsDK_DVGT.length

          if (count === 0) {
            this.$root.$toast.error("Hãy chọn dịch vụ đăng ký bạn đã cài đặt ")
            this.tsbtnHoanCong = false;
            return;
          }
          await this.CapNhat_HopDong(this.dsDK_DVGT);
          if (this.PHAILAM("CAPNHAT_NGAYHT_HC")) {
            let nextMonth = moment(date).add(1, 'M')
            let thangsau = moment(nextMonth).format('YYYYMM')
            let dbt = await this.KT_DBThang_DaChot(thangsau);
            if (dbt !== "0") {
              this.$root.$toast.error(dbt)
              return;
            }
            //Cập nhật ngày hoàn thành
            await this.CAPNHAT_NGAYHT(this.hdtb_id, date);
            //
          }
          let str = "Cập nhật dữ liệu thành công !";
          let dsphieu = [];
          let hg_phieu_id = 0;
          let donvi_nhan_id = 0;
          let tthd_dich_id = 0;
          try {
            let post = {
              "vhuonggiao_id": this.huonggiao_id,
              "vhdtb_id": this.hdtb_id
            }
            const response = await api.Lay_DonVi_GiaoPhieu(this.axios, post)
            const data = await response.data
            if (data.error_code === 'BSS-00000000') {
              if (data.data.length > 0) {
                dsphieu = data.data
              }
            } else {
              console.log('Lay_DonVi_GiaoPhieu')
            }
          } catch (e) {
            console.log('Lấy danh sách không thành công')
          } finally {
            if (dsphieu.length > 0) {
              hg_phieu_id = dsphieu[0].huonggiao_id;
              donvi_nhan_id = dsphieu[0].donvi_id;
              tthd_dich_id = dsphieu[0].tthd_id;
            } else {
              this.$root.$toast.error("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !")
            }
          }
          await this.HOANTHANH_HOANCONG(this.hdtb_id, this.phieu_id, tthd_dich_id);

          if (this.PHAILAM("TUDONG_HTHS")) {
            await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId);
            await this.HOANTHIEN_HS_THAYDOI_DV(this.hdtb_id);
            if (this.PHAILAM("TUDONG_THUHOI_KHI_HOANCONG")) {
              await this.GIAOPHIEU_TD_HOST_THUHOI_1(this.hdtb_id, this.phieu_id, this.donViId, this.nhanvienId, 0);
            }
            await this.CapNhat_TT_Phieu_HTHS(this.hdtb_id);
            str = "Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !";
          } else {
            await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId);
          }

          if (this.PHAILAM("CAPNHAT_NGAYKH_HC")) {
            await this.CAPNHAT_NGAYKH(this.hdtb_id);
          }
          this.$root.$toast.success(str)
        }
      }
      //--------------------------END----------------------------------------------------//

      //--------------------------THANH LY----------------------------------------------------//
      if (this.cboLoaiHD.value.toString() === LoaiHopDong.CHAMDUT_SD.toString()) {
        if (this.PHAILAM("HOANCONG")) {
          if (this.PHAILAM("TUDONG_HTHS")) {
            // let vkyhoadon = moment(date).format('YYYYMM')
            // let kt_taobang = await this.KT_TaoDB_Thang(vkyhoadon);
            // if (kt_taobang === 0) {
            //   this.$root.$toast.error("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !")
            //   return;
            // }
          }

          let dsphieu = [];
          let hg_phieu_id = 0;
          let donvi_nhan_id = 0;
          let tthd_dich_id = 0;
          let str = "Cập nhật dữ liệu thành công !";
          let vthuebao_id = 0;
          for (let i = 0; i < this.listRowSelected.length; i++) {
            this.listRowSelected[i].CHON = "1";
            this.hdtb_id = this.listRowSelected[i].HDTB_ID;
            this.phieu_id = this.listRowSelected[i].PHIEU_ID;
            try {
              let post = {
                "vhuonggiao_id": this.huonggiao_id,
                "vhdtb_id": this.hdtb_id
              }
              const response = await api.Lay_DonVi_GiaoPhieu(this.axios, post)
              const data = await response.data
              // console.log(response)
              if (data.error_code === 'BSS-00000000') {
                if (data.data != null) {
                  if (data.data.length > 0) {
                    dsphieu = data.data
                  }
                }
              } else {
                console.log('Lay_DonVi_GiaoPhieu')
              }
            } catch (e) {
              console.log('Lấy danh sách không thành công')
            } finally {
              if (dsphieu.length > 0) {
                hg_phieu_id = dsphieu[0].huonggiao_id;
                donvi_nhan_id = dsphieu[0].donvi_id;
                tthd_dich_id = dsphieu[0].tthd_id;
              } else {
                this.$root.$toast.error("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !")
                return;
              }
            }

            if (!await this.KiemTra_Phieu_DaGiao(this.hdtb_id, hg_phieu_id)) {
              this.$root.$toast.error("Thuê bao " + this.txtMaTB + " đã hoàn công rồi. Bạn hãy kiểm tra lại")
            } else {
              if (this.PHAILAM("CAPNHAT_NGAYHT_HC")) {
                let nextMonth = moment(date).add(1, 'M')
                let thangsau = moment(nextMonth).format('YYYYMM')
                let dbt = await this.KT_DBThang_DaChot(thangsau);
                if (dbt !== "0") {
                  this.$root.$toast.error(dbt)
                  return;
                }
                await this.CAPNHAT_NGAYHT(this.hdtb_id, date);

              }
              if (this.PHAILAM("CAPNHAT_NGAYKH_HC")) {
                await this.CAPNHAT_NGAYKH_STATUS(this.hdtb_id, date, TRANGTHAI_DONGBO.DONGBO_SERVICE)
              }
              await this.CapNhat_TT_Phieu_HC(this.hdtb_id, TRANGTHAI_PH.DAGIAO);
              str = "Cập nhật dữ liệu thành công !";
              try {
                const response = await api.map_id(this.axios, {
                  id_neo: "thuebao_id",
                  in_table: "hd_thuebao",
                  in_dk: `where hdtb_id =${this.hdtb_id}`
                })
                const data = await response.data
                if (data.error_code === 'BSS-00000000') {
                  this.txtMaNVTC = data.data
                } else {
                  console.log('map_id')
                }
              } catch (e) {
                console.log('Lấy danh sách không thành công')
              } finally {

              }


              if (this.PHAILAM("TUDONG_HTHS")) {
                await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId);
                await this.HOANTHIEN_HS_CHAMDUT_SD(this.hdtb_id);

                await this.CapNhat_TT_Phieu_GPhone(this.hdtb_id);
                if (this.PHAILAM("TUDONG_THUHOI_KHI_HOANCONG")) {
                  await this.GIAOPHIEU_TD_HOST_THUHOI_1(this.hdtb_id, this.phieu_id, this.donViId, this.nhanvienId, 0);
                }
                str = "Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !";
              } else {
                await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId);
              }
            }
          }
          // }
          this.$root.$toast.success(str)

        }
      }
      //--------------------------END----------------------------------------------------//

      //--------------------------Hợp đồng thay đổi dịch vụ----------------------------------------------------//
      if (this.cboLoaiHD.value.toString() === LoaiHopDong.DOISO_TB.toString()) {
        if (this.PHAILAM("HOANCONG")) {
          if (this.PHAILAM("TUDONG_HTHS")) {
            let kt_taobang = await this.KT_TaoDB_Thang(moment(date).format('YYYYMM'));
            if (kt_taobang === 0) {
              this.$root.$toast.error("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !")
              return;
            }

            let hg_phieu_id = 0;
            let donvi_nhan_id = 0;
            let tthd_dich_id = 0;
            let str = "";
            let dsphieu = [];
            this.hdtb_id = this.rowSelected.hdtb_id
            this.phieu_id = this.rowSelected.phieu_id
            try {
              let post = {
                "vhuonggiao_id": this.huonggiao_id,
                "vhdtb_id": this.hdtb_id
              }
              const response = await api.Lay_DonVi_GiaoPhieu(this.axios, post)
              const data = await response.data
              console.log(response)
              if (data.error_code === 'BSS-00000000') {
                if (data.data != null) {
                  if (data.data.length > 0) {
                    dsphieu = data.data
                  }
                }
              }
            } catch (e) {
              console.log('Lay_DonVi_GiaoPhieu')
            } finally {
              if (dsphieu.length > 0) {
                hg_phieu_id = dsphieu[0].huonggiao_id;
                donvi_nhan_id = dsphieu[0].donvi_id;
                tthd_dich_id = dsphieu[0].tthd_id;
              } else {
                this.$root.$toast.error("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !")
                return;
              }
            }

            if (!await this.KiemTra_Phieu_DaGiao(this.hdtb_id, hg_phieu_id)) {
              this.$root.$toast.error("Thuê bao " + this.txtMaTB + " đã hoàn công rồi. Bạn hãy kiểm tra lại")
            } else {
              if (this.PHAILAM("CAPNHAT_NGAYHT_HC")) {
                let nextMonth = moment(date).add(1, 'M')
                let thangsau = moment(nextMonth).format('YYYYMM')
                let dbt = await this.KT_DBThang_DaChot(thangsau);
                if (dbt !== "0") {
                  // Message_Box.ShowTB(dbt);
                  this.$root.$toast.error(dbt)
                  return;
                }
                await this.CAPNHAT_NGAYHT(this.hdtb_id, date);
              }
              if (this.PHAILAM("CAPNHAT_NGAYKH_HC")) {
                await this.CAPNHAT_NGAYKH_STATUS(this.hdtb_id, date, TRANGTHAI_DONGBO.DONGBO_SERVICE)
              }

              await this.CapNhat_TT_Phieu_HC(this.hdtb_id, TRANGTHAI_PH.DAGIAO);
              str = "Cập nhật dữ liệu thành công !";

              if (this.PHAILAM("TUDONG_HTHS")) {

                await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId);
                await this.HOANTHIEN_HS_THAYDOI_MATB(this.hdtb_id);

                await this.CapNhat_TT_Phieu_GPhone(this.hdtb_id);
                if (this.PHAILAM("TUDONG_THUHOI_KHI_HOANCONG")) {
                  await this.GIAOPHIEU_TD_HOST_THUHOI_1(this.hdtb_id, this.phieu_id, this.donViId, this.nhanvienId, 0);
                }
                str = "Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !";
              } else {
                await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId);
              }
            }
          }

        }
      }
      //--------------------------END----------------------------------------------------//

      await this.HienThiDanhSachTB();
    },
    async KiemTra_DVCD(dichvugt_id) {
      for (let i = 0; i < this.lvwDVGT.length; i++) {
        if (this.lvwDVGT[i].Checked && this.lvwDVGT[i].SubItems[0] === dichvugt_id)
          return true;
      }
      return false;
    },
    async KT_TaoDB_Thang(vkyhoadon) {
      try {
        let post = {
          "vkyhoadon": vkyhoadon
        }
        const response = await api.KT_TaoDB_Thang(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data) {
            return Number(data.data)
          }
        } else {
          console.log('KT_TaoDB_Thang')
          return -1;
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
        return -1;
      } finally {
      }
    },
    async KT_DBThang_DaChot(vngay_cn) {
      try {
        let post = {
          "vngay_cn": vngay_cn
        }
        const response = await api.KT_DBThang_DaChot(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {
          if (data.data) {
            return data.data
          }
        } else {
          console.log('KT_DBThang_DaChot')
          return -1;
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
        return -1;
      } finally {
      }
    },
    async CapNhat_HopDong(dsDK_DVGT) {
      try {
        let post = {
          "js_dangky_dvgt": dsDK_DVGT
        }
        const response = await api.SP_UPDATE_DANGKY_DVGT(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('SP_UPDATE_DANGKY_DVGT')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async CAPNHAT_NGAYHT(hdtb_id, ngay_cn) {
      try {
        let post = {
          "ngay_ht": ngay_cn,
          "hdtb_id": hdtb_id
        }
        const response = await api.CAPNHAT_NGAYHT(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('CAPNHAT_NGAYHT')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HOANTHANH_HOANCONG(hdtb_id, phieu_id, tthd_dich_id) {
      try {
        let post = {
          "hdtb_id": hdtb_id,
          "phieu_id": phieu_id,
          "tthd_dich_id": tthd_dich_id
        }
        const response = await api.HOANTHANH_HOANCONG(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('HOANTHANH_HOANCONG')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, donvi_id_giao, donvi_id_nhan, nhanvien_id) {
      try {
        let post = {
          "vhdtb_id": hdtb_id,
          "vphieu_id": phieu_id,
          "vhuonggiao_id": hg_phieu_id,
          "vdonvi_giao_id": donvi_id_giao,
          "vdonvi_nhan_id": donvi_id_nhan,
          "vnhanvien_id": nhanvien_id

        }
        const response = await api.GIAOPHIEU_TD_KHI_HC(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('GIAOPHIEU_TD_KHI_HC')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HOANTHIEN_HS_THAYDOI_DV(hdtb_id) {
      try {
        let post = {
          "vhdtb_id": hdtb_id
        }
        const response = await api.HOANTHIEN_HS_THAYDOI_DV(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('HOANTHIEN_HS_THAYDOI_DV')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async GIAOPHIEU_TD_HOST_THUHOI_1(hdtb_id, phieu_id, donvi_id, nhanvien_id, kieungay) {
      try {
        let post = {
          "hdtb_id": hdtb_id,
          "phieu_id": phieu_id,
          "donvi_giao_id": donvi_id,
          "nhanvien_id": nhanvien_id,
          "kieu_ngay": kieungay
        }
        const response = await api.GIAOPHIEU_TD_HOST_THUHOI_1(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('GIAOPHIEU_TD_HOST_THUHOI_1')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async CapNhat_TT_Phieu_HTHS(hdtb_id) {
      try {
        let post = {
          "hdtb_id": hdtb_id
        }

        const response = await api.CapNhat_TT_Phieu_HTHS(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('CapNhat_TT_Phieu_HTHS')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async CAPNHAT_NGAYKH(hdtb_id) {
      try {
        let post = {
          "hdtb_id": hdtb_id
        }

        const response = await api.SP_CAPNHAT_NGAYKH(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('SP_CAPNHAT_NGAYKH')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async CAPNHAT_NGAYKH_STATUS(vhdtb_id, vngay_kh, vstatus) {
      try {
        let post = {
          "vhdtb_id": vhdtb_id,
          "vngay_kh": vngay_kh,
          "vstatus": vstatus
        }

        const response = await api.CAPNHAT_NGAYKH_STATUS(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('CAPNHAT_NGAYKH_STATUS')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async CapNhat_TT_Phieu_HC(hdtb_id, vttph_id) {
      try {
        let post = {
          "vttph_id": vttph_id,
          "vhdtb_id": hdtb_id
        }

        const response = await api.CAPNHAT_TT_PHIEU_HC(this.axios, post)
        const data = await response.data
        console.log(response)
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('CAPNHAT_TT_PHIEU_HC')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HOANTHIEN_HS_CHAMDUT_SD(hdtb_id) {
      try {
        let post = {
          "vhdtb_id": hdtb_id
        }
        const response = await api.HOANTHIEN_HS_CHAMDUT_SD(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000') {

        } else {
          console.log('HOANTHIEN_HS_CHAMDUT_SD')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async CapNhat_TT_Phieu_GPhone(hdtb_id) {

    },
    async HOANTHIEN_HS_THAYDOI_MATB(hdtb_id) {

    },
    async KiemTra_Phieu_DaGiao(hdtb_id, hg_phieu_id) {
      return true;
    },
    getColumnTemplateChonSuCoCpn(parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="check-action">
              <input
                type="checkbox"
                class="check"
                v-model="parent.selectedIds"
                :value="data"
              />
              <span class="name"></span>
              </div>
            `,
            created() {
            },
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    async dgvThueBao_SelectionChanged(dataKeys) {
      console.log('dgvThueBao_SelectionChanged')
      this.listRowSelected = dataKeys;
      console.log(dataKeys)
    },
    async rowSelectedDataGrid(item) {
      if (item.data && item) {
        this.rowSelected = item.data;
        if (this.dgvThueBao.length > 0) {
          this.txtMaGD = this.rowSelected.MA_GD.toString();
          if (this.rowSelected.NGAY_YC.toString() !== "") {
            this.dtpNgayYC = new Date(this.rowSelected.NGAY_YC.toString()).toLocaleString('en-GB', {dateStyle: 'short'});
          }
          this.hdtb_id = this.rowSelected.HDTB_ID;

          this.txtMaKH = this.rowSelected.MA_KH;
          this.txtTenKH = this.rowSelected.TEN_KH;
          this.txtDiaChiKH = this.rowSelected.DIACHI_KH;
          this.txtMaTB = this.rowSelected.MA_TB;
          this.status = this.rowSelected.STATUS;
          this.txtGhiChu = this.rowSelected.GHICHU;

          try {
            let post = {
              "loaidv_id": LOAI_DV.TRAM_VT,
              "hdtb_id": this.hdtb_id,
              "kieudv_id": 2
            }
            const response = await api.dgvThueBao_Selection(this.axios, post)
            const data = await response.data
            if (data.error_code === 'BSS-00000000') {
              if (data.data != null) {
                if (data.data.length > 0) {
                  let res = data.data[0];
                  this.donvi_id = res.donvi_id;
                }
              }
            }
          } catch (e) {
            console.log('Lấy danh sách không thành công')
          } finally {
          }
          this.loaitb_id = this.rowSelected.LOAITB_ID;
          // console.log(this.PHAILAM("HOANCONG"))
          //Kiểm tra nếu bộ phận giao dịch viên được phép hoàn công, hoàn thiện hồ sơ không?
          if (this.status === TRANGTHAI_DONGBO.DONGBO_SERVICE) {
            if (this.PHAILAM("HOANCONG")) {
              this.tsbtnHoanCong = true;
              this.tsbtnGiaoPhieu = false;
            } else {
              this.tsbtnHoanCong = false;
              this.tsbtnGiaoPhieu = true;
            }
          } else {
            this.tsbtnHoanCong = false;
            this.tsbtnGiaoPhieu = false;
          }

          await this.LAY_DS_KIEU_LD;

          if (+this.cboLoaiHD.value === LoaiHopDong.THAY_DOI_DV) {
            await this.HienThiDVGT(this.hdtb_id);
          } else if (+this.cboLoaiHD.value === LoaiHopDong.DOISO_TB) {
            this.txtMaTB_Cu = this.rowSelected.MATB_CU;
          } else if (+this.cboLoaiHD.value === LoaiHopDong.CHAMDUT_SD) {
            await this.Lay_DS_LyDoHuy_HDTB(this.hdtb_id);
            this.phieu_id = this.rowSelected.PHIEU_ID;
          }
          if (this.tthd_id === TrangThaiHD.THANH_TOAN) {
            // phieu_id = Convert.ToInt32(ds.Tables[0].Rows[nIndex]["phieu_id"].ToString());
            this.phieu_id = this.rowSelected.PHIEU_ID;
          }
          await this.HienThiHuyKH();
        }
      }
    },
    async selectedRowChangedDataGrid(dataItem) {
      console.log('selectedRowChangedDataGrid')
      console.log(dataItem)
    },
    async rowClickedDataGrid(index, data) {
      console.log('rowClickedDataGrid')
      console.log(index, data)

    },
    overlayClick(){
      this.$refs.generalDialog.hide();
    },
    async dlgClose(e){
      console.log("dlgClose", e);
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
  }
}
</script>

<style scoped>
input[readonly] {
  background-color: white;
}
</style>
