<template src="./CapNhatDanhBa.html"></template>
<style scoped src="./CapNhatDanhBa.scss"></style>

<script>

import LoaiKhachHang from "./loaiKhachhang";
import chonDiaChi from "./ModalChonDiaChi";
import KhuyenMai from "./DanhSachKhuyenMai";
import moment from "moment";
import ChiTietNganhNghe from '@/modules/EXTS/HOPDONG/ThayDoiThongTinKhuyenMai/ChiTietNganhNghe';
import PopupTimKiemNhanVienThuCuoc from '@/modules/QLTN/GachNo/components/TimNhanVienThuCuoc.vue';
import Email from "@/modules/QLTN/ThayDoiTTKhachHang/components/email";
import TTLienHe from "@/modules/QLTN/ThayDoiTTKhachHang/components/modal-tt-lien-he";
import TraCuuVatTuThueBao from '@/modules/QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
import popupMaInGhep from '@/modules/QLTN/ThayDoiTTKhachHang/components/ma_in_ghep';
import popupToaNha from "@/modules/contract/setup/InstallInternet/popups/popup_Toanha.vue";
import SearchAccount from '@/modules/search/subscriber/SearchAccount';
import frmKhuVuc from '@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao'
export default {
  components: {
    chonDiaChi, Email, "thong-tin-lien-he": TTLienHe, TraCuuVatTuThueBao, popupToaNha,
    KhuyenMai, SearchAccount, PopupTimKiemNhanVienThuCuoc, frmKhuVuc,
    ChiTietNganhNghe, popupMaInGhep,
    "loai-khach-hang": LoaiKhachHang,

  },
  data: function () {
    return {
      header: {
        title: 'CẬP NHẬT DANH BẠ',
        list: [
          { name: 'Quản lý danh bạ', link: { name: 'Ui.cards' } },
          {
            name: 'Cập nhật danh bạ',
            link: { name: 'Ui.buttons' }
          }
        ]
      },

      popupComponentData: null,
      kyHoaDon: `${moment(new Date()).subtract(1, "months").endOf("month").format("YYYYMM")}01`,
      khachhang: {
        ma_hd: "",
        ngay_lap_hd: "",
        khachhang_id: 0,
        ma_kh: "",
        ten_kh: "",
        diachi_kh: "",
        diem_tn: "",
        loaigt_id: 0,
        so_gt: "",
        ngay_cap: "",
        noi_cap: "",
        ngay_hethan: "",
        ngay_sinh: "",
        gioi_tinh: "rdoNo",
        quoctich_id: 1,
        nganhnghe_id: 0,
        dantoc_id: 1,
        loaikh_id: 1,
        phanloai_kh: "",
        nguoi_dd: "",
        chuc_vu: "",
        diachi_ndd: "",
        dien_thoai: "",
        fax: "",
        ho_khau: "",
        noicap_hk: "",
        ngay_cap_hk: "",
        nganhang_id: 0,
        mst: "",
        so_tk: "",
        dac_trung: "",
        ghi_chu: "",
        diachi_id: 0,
        khlon_id: 0,
        tnc1_id: 0,
        tnc2_id: 0,
        tnc3_id: 0,
        tnc4_id: 0,
        ten_loaikh: "",
        ten_nganhnghe: "",
      },
      thanhtoan: {
        ma_tt: "",
        matb_dd: "",
        matt_ccbs: "",
        thanhtoan_id: 0,
        ten_tt: "",
        diachi_tt: "",
        diachi_ct: "",
        diachi_bc: "",
        mst: "",
        stk: "",
        chu_tk: "",
        email: "",
        so_dt: "",
        nganhang_id: "",
        httt_id: 0,
        donvi_id: 0,
        manv_ql: "",
        mig_id: "",
        main_ghep: "",
        diachi_id: 0,
        diachibc_id: 0,
        manv_tc: "",
        ma_tuyen: "",
        ghi_chu: "",
        tuyenthu_id: "",
        nhanvientc_id: 0,
        ma_dv_qhns: "",
        ma_ndkt: "",
        ma_chuong: "",
        ma_nguon_kt: "",
        ma_nguon_ngansach: "",
        tien_dm: 0,
      },
      ttdiachi_kh: {
        dia_chi: "",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
      },
      ttdiachi_tt: {
        dia_chi: "",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
      },

      ttdiachi_ct: {
        dia_chi: "",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
      },

      ttdiachi_bc: {
        dia_chi: "",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
      },
      ttdiachi_tb: {
        dia_chi: "",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
      },
      ttdiachi_ld: {
        dia_chi: "",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
      },

      thuebao: {
        thuebao_id: 0,
        loaihd_id: 0,
        trangthaitb_id: 0,
        trangthai_tb: "",
        dichvuvt_id: 0,
        loaitb_id: 0,
        kieuld_id: 0,
        khuvuc: "",
        khuvuc_id: 0,
        muccuoc_tb: 0,
        muccuoctb_id: 0,
        doituong_id: 0,
        ma_tb: "",
        ma_lt: "",
        thanhtoan_id: 0,
        ten_tb: "",
        ngay_sinh: "",
        diachi_tb: "",
        diachi_ld: "",
        donvi_id: 0,
        ma_dhvip: "",
        ghi_chu: "",
        sothang_ck: 0,
        ngay_kt_ck: "",
        ngay_sudung: "",
        cuoc_tb: 0,
        cuoc_dv: 0,
        ngay_sinh: "",
        diachi_id: 0,
        diachild_id: 0,
        chuyenmang: 0,
        nhanvien_am_id: 0,
      },
      date: {
        dateVal: Date,
        dateFormat: "dd/MM/yyyy",
      },
      khuvuc_tb: {
        khuvuc_id: 0,
        ma_kv: "",
        ten_kv: "",
        loaikv_id: 0,
      },

      db_con: {
        donvi_id: 0,
        bras_id: 0,
        dslam_id: 0,
        tocdo_id: 0,
        tocdothuc_id: 0,
        muccuoc_id: 0,
        chuquan_id: 0,
        loainode_id: 1,
        doitac_id: 0,
        tocdopir_id: 0,
        tocdocir_id: 0,
        loaikenh_id: 0,
        loaimd_id: 0,
        thoihan_id: 0,
        quoctich_id: 0,
        tocdokenh_id: 0,
        thietbid_id: 0,
        trangbi_id: 0,
        cuoc_dv: 0,
        username: "",
        password: "",
        slot: "",
        port: "",
        rack: "",
        shelf: "",
        vpi: "",
        vci: "",
        loaicap_id: 0,
        tucap_goc: "",
        doicap_goc: "",
        cap_goc: "",
        hopcap_ngon: "",
        tucap_ngon: "",
        doicap_ngon: "",
        cap_ngon: "",
        tendslam: "",
        tuyencap: "",
        lientu: "",
        culy: "",
        thietbi_id: 0,
        system: "",
        email: "",
        ipdslam: "",
        nasportid: "",
        portmdf: "",
        adslport: "",
        onupass: "",
        iptinh: "",
        matn: "",
        congnghe: "",
        diachi_mac: "",
        diachi_pop: "",
        soluong: 0,
        cuoc_tk: 0,
        cuoc_tc: 0,
        cuoc_ht: 0,
        cuoc_tbi: 0,
        cuoc_tkdt: 0,
        cuoc_tcdt: 0,
        cuoc_isp: 0,
        cuoc_nix: 0,
        cuoc_ip: 0,
        mahd_dt: "",
        diachi_dau: "",
        diachi_cuoi: "",
        donvi_id_dau: 0,
        donvi_id_cuoi: 0,
        site_id_dau: 0,
        site_id_cuoi: 0,
        khuvuc_id_dau: 0,
        khuvuc_id_cuoi: 0,
        khuvuc_dau: "",
        khuvuc_cuoi: "",
        huong_kn: "",
        huong_ut: "",
        vmp: 0,
        capngoai: 0,
        luonggiua: 0,
        mahd_dt: "",
        ne: "",
        sochinh: "",
        simcard: "",
        sodu_tk: 0,
        goicuoc_id: 0,
        madoicap: "",
        mc_tbgp_id: "",
        toado: "",
        cell1: "",
      },
      objChiTietNganhNghe: {},
      ma_timkiem: "",
      sudungdvgt: "",
      sudung_dvkhac_tt: "",
      sudung_dvkhac_tb: "",
      dsThanhToan: [],
      dsSuDungDVGT: [],
      dsLoaiGT: [],
      dsNganHang: [],
      dsQuocTich: [],
      dsDanToc: [],
      tmp_dsNganHang: [],
      loai_kh_tree: [],
      dsNganhNghe: [],
      tmp_dsQuoctich: [],
      tmp_dsDanToc: [],
      dsLoaiKhachHang: [],
      tmp_dsLoaiKH: [],
      nh_checked: false,
      nhtt_checked: false,
      qt_checked: false,
      dt_checked: false,
      ngaylapHD: new Date(),
      activetab: 1,
      dsKhachHang: [],
      dsThueBao: [],
      dsTTThanhToan: [],
      dsDonViQLDM: [],
      dsDonViQL: [],
      dsDonViQLTC: [],
      dsHTTT: [],
      dsBras: [],
      dsLoaiHD: [],
      dsDichVuVT: [],
      tmp_dsLoaiHinhTB: [],
      dsLoaiHinhTB: [],
      dsKieuLD: [],
      tmp_dsKieuLD: [],
      dsMucCuocTB: [],
      tmp_dsMucCuocTB: [],
      dsDoiTuong: [],
      dsTrangBi: [],
      dsTocDo: [],
      tmp_dsTocDo: [],
      dsMucCuoc: [],
      dsToThiCong: [],
      dsDslam: [],
      tmp_dsDslam: [],
      dsLoaiCap: [],
      dsThueBaoDD: [],
      dsTramVT: [],
      dsToTC: [],
      dsThuocTinhHDTB_CNTT: [],
      isShowDiaChiKH: false,
      isShowDiaChiNDD: false,
      isShowDiaChiHK: false,
      isShowDiaChiTT: false,
      isShowDiaChiBC: false,
      isShowDiaChiTB: false,
      isShowDiaChiCT: false,
      isShowDiaChiLD: false,
      isDisableBtnGhiLai: false,
      isDisableBtnThemTT: true,
      isDisableBtnXoaTT: true,
      isShowKhuyenMai: false,
      hideLoaiKH: false,
      showModalLoaiKH: false,
      isDisableNhapMoi: true,
      isDisableHuyBo: false,
      dsDiaChi: [],
      dsChuQuan: [],
      dsDoiTac: [],
      dsTocDoKenh: [],
      dsLoaiKenh: [],
      dsLoaiModem: [],
      dsThoiHan: [],
      dsGoiCuocDD: [],
      tmp_dsGoiCuocDD: [],
      dsThietBiIMS: [],
      doitac_checked: false,
      pir_checked: false,
      query: "",
      dsLoaiNode: [
        { LOAINODE_ID: 1, TEN_NODE: "Node chính" },
        { LOAINODE_ID: 2, TEN_NODE: "Node phụ" },
        { LOAINODE_ID: 3, TEN_NODE: "Node dự phòng cổng + tốc độ" },
        { LOAINODE_ID: 4, TEN_NODE: "Node dự phòng tốc độ" },
      ],
      tmp_temp: [],
      dsThietBiTSL: [],
      tmp_dsThietBi: [],
      dsTBMoRong: [],
      getChiTietDiaChi: {
        tinhThanh_id: 0,
        quanHuyen_id: 0,
        phuongXa_id: 0,
        duongPho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacDiem_id: 0,
        soNha: "",
      },
    };
  },

  created: async function () {
    this.getDsTocDo();
    this.getDsLoaiGT();
    this.getDsNganHang();
    this.getDsQuocTich();
    this.getDsDanToc();
    this.getDSLoaiKhachHang();
    this.getdsLoaiKH();
    this.getDsDonViQL();
    this.getDSHTTT();
    this.getDsBras();
    this.getDsLoaiHD();
    this.getDsDichVuVT();
    this.getDsLoaiHinhTB();
    this.getDsKieuLD();
    this.getDsMucCuocTB();
    this.getDsDoiTuongTB();
    this.getDsMucCuoc();
    this.getDsDslam();
    this.getDsTrangBi();
    this.getDsLoaiCap();
    this.getDstramVT();
    this.getDsToTC();
    this.getDsChuQuan();
    this.getDsTocDoKenh();
    this.getDsDoiTac();
    this.getDsLoaiKenh();
    this.getDsLoaiModem();
    this.getDsThoiHan();
    this.getDsThietBiTSL();
    this.getDsThietBiIMS();
    this.getDsGoiCuocDD();
    this.getDsNganhNghe();
  },
  methods: {
    hienthi_thongtin_thuebao: async function (data) {
      this.thuebao.thuebao_id = data.THUEBAO_ID;
      await this.getDsKhuVuc(this.thuebao.thuebao_id);
      this.thuebao.khuvuc = this.khuvuc_tb.ten_kv;
      this.thuebao.dichvuvt_id = data.DICHVUVT_ID;
      //this.dsLoaiHinhTB=this.tmp_dsLoaiHinhTB.filter((item)=>item.DICHVUVT_ID===this.thuebao.dichvuvt_id);
      this.thuebao.loaitb_id = data.LOAITB_ID;
      this.thuebao.diachi_tb = data.DIACHI_TB;
      this.thuebao.kieuld_id = data.KIEULD_ID;
      this.thuebao.muccuoctb_id = data.MUCUOCTB_ID;
      this.thuebao.ma_tb = data.MA_TB;
      this.thuebao.ten_tb = data.TEN_TB;
      this.thuebao.diachi_ld = data.DIACHI_LD;
      this.thuebao.trangthai_tb = data.TRANGTHAI_TB;
      this.thuebao.doituong_id = data.DOITUONG_ID;
      this.thuebao.donvi_id = data.DONVI_ID;
      this.thuebao.ngay_sinh = data.NGAY_SN;
      this.thuebao.ma_lt = data.MA_LT;
      this.thuebao.ma_dhvip = data.MA_DHVIP;
      this.thuebao.ghi_chu = data.GHICHU;
      this.thuebao.sothang_ck = data.SOTHANG_CK;
      this.thuebao.ngay_kt_ck = data.NGAY_KTCK;
      this.thuebao.ngay_sudung = data.NGAY_SD;
      this.thuebao.cuoc_tb = data.CUOC_TB;
      this.thuebao.cuoc_dv = data.CUOC_DV;
      this.thuebao.diachi_id = data.DIACHI_ID;
      this.thuebao.diachild_id = data.DIACHILD_ID;
      this.thuebao.muccuoc_tb = data.MUCUOC_TB;
      this.thuebao.chuyenmang = data.CHUYENMANG;
      this.thuebao.trangthaitb_id = data.TRANGTHAITB_ID;

      this.db_con.donvi_id_dau = data.DONVI_DAU_ID;
      this.db_con.donvi_id_cuoi = data.DONVI_CUOI_ID;

      if (this.thuebao.dichvuvt_id === 2 && this.thuebao.loaitb_id === 20)
        this.dsGoiCuocDD = this.tmp_dsGoiCuocDD.filter(
          (item) => item.LOAITB_ID === 20
        );

      if (this.thuebao.kieuld_id != 0)
        this.thuebao.loaihd_id = this.tmp_dsKieuLD.filter(
          (item) => item.KIEULD_ID === this.thuebao.kieuld_id
        )[0].LOAIHD_ID;
      if (this.thuebao.diachi_id > 0)
        await this.loadDiaChiTB(this.thuebao.diachi_id);
      if (this.thuebao.diachild_id > 0)
        await this.loadDiaChiLD(this.thuebao.diachild_id);

      this.getDsSuDungDVGT();
    },
    //  onclickTab: function (currentActivetab) {
    //     try {
    //         this.loading(true)

    //         this.activetab = currentActivetab;
    //         //this.hienThiDanhSachCatChuyen(currentActivetab, 0);
    //     } catch (error) {
    //         this.$root.toastError(' '+error);
    //     }
    //     finally {
    //         this.loading(false)
    //     }
    // },
    onCloseModalLoaiKH: function (data) {
      if (data.isUpdated) {
        this.khachhang.loaikh_id = data.loaikh_id;
        this.khachhang.ten_loaikh = data.ten_loaikh;
      }
      this.showModalLoaiKH = false;
    },
    clickComboLoaiKH: function (data) {
      //console.log(data);
      this.khachhang.loaikh_id = data.id;
      this.ngaySinhChanged();
      // this.buttons.btnThongTinKhac = (node.cate!="Cá nhân");
      // if(!this.buttons.btnThongTinKhac && this.khachhang.gioitinh==-1) this.khachhang.gioitinh = 0;



    },

    onChangeLoaiKH: function () {
      this.hideLoaiKH = !this.hideLoaiKH;
    },
    hienthi_thongtin_danhba_con_tsl: async function () {
      let ds = this.dsTBMoRong[1];
      this.db_con.tocdokenh_id = ds.TOCDO_ID;
      this.db_con.diachi_cuoi = ds.DIACHI;
      this.db_con.site_id_cuoi = ds.SITE_ID;
    },
    hienthi_thongtin_danhba_con: async function () {
      this.loading(true);
      await this.getDsThongTinMoRong();
      //console.log(this.dsTBMoRong);

      let ds = this.dsTBMoRong[0];
      if (this.dsTBMoRong.length > 0) {
        this.db_con.donvi_id = ds.TRAMTB_ID;
        this.db_con.loaicap_id = ds.LOAICAP_ID;
        this.db_con.tucap_goc = ds.TUCAP_GOC;
        this.db_con.doicap_goc = ds.DOICAP_GOC;
        this.db_con.cap_goc = ds.CAP_GOC;
        this.db_con.hopcap_ngon = ds.HOPCAP_P;
        this.db_con.tucap_ngon = ds.TUCAP_P;
        this.db_con.doicap_ngon = ds.DOICAP_P;
        this.db_con.cap_ngon = ds.CAP_P;
        this.db_con.tuyencap = ds.TUYENCAP;
        this.db_con.lientu = ds.LIENTU;
        this.db_con.culy = ds.CULY;
        this.db_con.ne = ds.SONE;

        this.db_con.bras_id = ds.BRAS_ID;
        this.db_con.username = ds.USERNAME;
        this.db_con.password = ds.PASSWORD;
        this.db_con.slot = ds.SLOT;
        this.db_con.port = ds.PORT;
        this.db_con.rack = ds.RACK;
        this.db_con.shelf = ds.SHELF;
        this.db_con.vpi = ds.VPI;
        this.db_con.vci = ds.VCI;
        this.db_con.tuyencap = ds.TUYENCAP;
        this.db_con.thietbi_id = ds.THIETBI_ID;
        this.db_con.tendslam = ds.TENDSLAM;
        this.db_con.system = ds.SYSTEM;
        this.db_con.email = ds.EMAIL;
        this.db_con.trangbi_id = ds.TRANGBI_ID;

        this.db_con.tocdo_id = ds.TOCDO_ID;
        this.db_con.tocdothuc_id = ds.TOCDOTHUC_ID;
        this.db_con.ipdslam = ds.IP_DSLAM;
        this.db_con.muccuoc_id = ds.MUCCUOC_ID;
        this.db_con.mahd_dt = ds.MAHD_DT;
        if (ds.SELFID01 != null)
          this.db_con.nasportid =
            ds.SYSTEM +
            "/" +
            ds.SELFID04 +
            "/" +
            ds.SELFID03 +
            "/" +
            ds.SELFID02 +
            "/" +
            ds.SELFID01;
        else this.db_con.nasportid = ds.SYSTEM;

        this.db_con.portmdf = ds.PORT_MDF;
        this.db_con.adslport = ds.ADSL_PORT;
        this.db_con.onupass = ds.ONU_PASSWORD;

        this.db_con.simcard = ds.SIMCARD;
        this.db_con.sodu_tk = ds.SODU_TK;
        this.db_con.goicuoc_id = ds.GOICUOC_ID;
        this.db_con.matn = ds.MATB_TN;
        this.db_con.chuquan_id = ds.CHUQUAN_ID;
        this.db_con.loaimd_id = ds.LOAIMD_ID;
        this.db_con.loaikenh_id = ds.LOAIKENH_ID;
        this.db_con.doitac_id = ds.DOITAC_ID;
        this.db_con.cuoc_tk = ds.CUOC_TK;
        this.db_con.cuoc_ht = ds.CUOC_HT;
        this.db_con.cuoc_tbi = ds.CUOC_TBI;
        this.db_con.cuoc_tkdt = ds.CUOC_TKDT;
        this.db_con.cuoc_tcdt = ds.CUOC_TCDT;
        this.db_con.cuoc_tc = ds.CUOC_TC;
        this.db_con.cuoc_ip = ds.CUOC_IP;
        this.db_con.cuoc_isp = ds.CUOC_ISP;
        this.db_con.cuoc_nix = ds.CUOC_NIX;
        this.db_con.huong_kn = ds.HUONG_KN;
        this.db_con.huong_ut = ds.HUONGLUONG_UUTIEN;
        this.db_con.vrfvfi = ds.VRF + "/" + ds.VFI;
        this.db_con.tocdopir_id = ds.TOCDO_PIR_ID;
        this.db_con.tocdonix_id = ds.TOCDO_NIX_ID;
        this.db_con.soluong = ds.SLTV_HTVC;
        this.db_con.iptinh = ds.IP;
        this.db_con.capngoai = ds.CAPNGOAI_VNPT;
        this.db_con.thietbi_id = ds.THIETBIDC_ID;
        //this.db_con.congnghe=ds;
        this.db_con.diachi_mac = ds.MAC_ADDR;

        this.db_con.diachi_dau = ds.DIACHI;
        this.db_con.diachi_pop = ds.DIACHI_POP;
        this.db_con.site_id_dau = ds.SITE_ID;
        //this.db_con.site_id=ds.SITE_ID;
        this.db_con.cell1 = ds.CELL1;



        this.db_con.mc_tbgp_id = ds.MC_TBGP_ID;
        this.db_con.toado = ds.TOADO;
      }
      if (this.thuebao.dichvuvt_id === 9)
        this.hienthi_thongtin_danhba_con_tsl();
      this.loading(false);
    },


    popupTimThongTinTT: function () {
      //alert("da den day");
      this.$bvModal.show("modal-loai-khach-hang");
    },
    onCloseModal() {
      this.$bvModal.hide("modal-loai-khach-hang");
    },

    closeModalKhuyenMai: function () {
      this.isShowKhuyenMai = !this.isShowKhuyenMai;
    },

    getDsLoaiGT: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_LOAI_GT`
        );
        this.dsLoaiGT = rs.data;
      } catch (error) { }
    },

    getNVTCProcess(val) {
      if (val != null) {
        this.thanhtoan.nhanvientc_id = val.rowData.NHANVIEN_ID;
        this.thanhtoan.manv_tc = val.maNVTC;
        this.thanhtoan.tuyenthu_id = val.rowData.TUYENTHU_ID;
        this.thanhtoan.ma_tuyen = val.rowData.MA_TUYENTHU;

      }
    },
    getDsNganhNghe: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/NGANHNGHE`
        );
        this.dsNganhNghe = rs.data;
      } catch (error) { }
    },
    getDsNganHang: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/NGANHANG`
        );
        this.dsNganHang = rs.data;
      } catch (error) { }
    },
    getDsGoiCuocDD: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/GOICUOC_DD`
        );
        this.tmp_dsGoiCuocDD = rs.data;
      } catch (error) { }
    },
    getDsQuocTich: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_QUOCTICH`
        );
        this.dsQuocTich = rs.data;
      } catch (error) { }
    },
    getDsDanToc: async function () {
      try {
        const rs = await this.$root.context.post(
          `/web-quantri/quan-ly-danh-ba/danh-muc-dan-toc/${this.khachhang.quoctich_id}`
        );
        this.dsDanToc = rs.data;
        // if (this.dsDanToc.length>0)
        //   this.khachhang.dantoc_id=this.khachhang.dantoc_id
      } catch (error) { }
    },
    getDsKhuVuc: async function (pThuebaoID) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/cap-nhat-tram-thi-cong/lay-thongtin-khuvuc-thuebao/${pThuebaoID}`
        );
        this.khuvuc_tb = {
          khuvuc_id: rs.data[0].KHUVUC_ID,
          ma_kv: rs.data[0].MA_KV,
          ten_kv: rs.data[0].TEN_KV,
          loaikv_id: rs.data[0].LOAIKV_ID,
        };
      } catch (error) { }
    },
    getDsDichVuKhacThanhToan: async function () {
      try {
        var i = 0;
        this.sudung_dvkhac_tt = "";
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-dich-vu-khac-theo-thanh-toan?pDanhBaId=${this.thanhtoan.thanhtoan_id}&pLoaiId=2`
        );
        if (rs.data.length > 0) this.tmp_temp = rs.data;

        for (i = 0; i < this.tmp_temp.length; i++) {
          this.sudung_dvkhac_tt =
            this.sudung_dvkhac_tt + this.tmp_temp[i].TEN_DVKHAC + "\n";
        }
      } catch (error) { }
    },
    getDsSuDungDVGT: async function () {
      let data = {
        hdtb_id: null,
        kieu_id: 0,
        loaitb_id: this.thuebao.loaitb_id,
        thuebao_id: this.thuebao.thuebao_id,
      };
      try {
        const rs = await this.$root.context.post(
          `/web-quantri/quan-ly-danh-ba/danhsach-sudung-dichvu-giatang-thuebao`,
          data
        );
        if (rs.data.length > 0) this.dsSuDungDVGT = rs.data;
        this.dsSuDungDVGT = this.dsSuDungDVGT.filter(
          (item) => item.SUDUNG === "Su dung"
        );
        var i = 0;
        for (i = 0; i < this.dsSuDungDVGT.length; i++) {
          this.sudungdvgt =
            this.sudungdvgt + this.dsSuDungDVGT[i].TEN_DVGT + "\n";
        }
      } catch (error) { }
    },
    getDsKhachHang: async function (pMa) {
      try {
        this.loading(true);
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danh-ba-khach-hang-theo-ma/${pMa}`
        );
        if (rs.data.length > 0) {
          this.dsKhachHang = rs.data;
          await this.hienthi_thongtin_khachhang();
          this.loading(false);
        } else {
          this.loading(false);
          this.$root.toastError(
            "Không tìm thấy thông tin. Bạn hãy nhập lại thông tin tìm kiếm"
          );
        }
      } catch (error) {
        this.loading(false);
      }
    },
    getDsThanhToan: async function (khachhang_id) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danh-sach-thanh-toan-theo-khach-hang-id/${khachhang_id}`
        );
        this.dsThanhToan = rs.data;
      } catch (error) { }
    },
    getDSThanhToanMaTT: async function (pMaTT) {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno//api/thu-no/tra-cuu-danh-ba/ma-thanh-toan?maTT=${pMaTT}`
        );
        this.dsTTThanhToan = rs.data;
      } catch (error) { }
    },

    getDSThueBaoTheoKachHangID: async function (khachhang_id) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhba-thuebao-theo_khachhang/${khachhang_id}`
        );
        this.dsThueBao = rs.data;
      } catch (error) { }
    },
    getDSLoaiKhachHang: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-loai-khach-hang/0`
        );
        this.dsLoaiKhachHang = rs.data;
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },

    getDstramVT: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=25`
        );
        this.dsTramVT = rs.data;

        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsToTC: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=5`
        );
        this.dsToTC = rs.data;

        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDSHTTT: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/HINHTHUC_TT`
        );
        this.dsHTTT = rs.data;
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsBras: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/danhmuc/quanlybras/danhsach`
        );
        this.dsBras = rs.data;
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },

    getDsLoaiHD: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_LOAI_HD`
        );
        this.dsLoaiHD = rs.data;
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsDichVuVT: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_DICHVU_VT`
        );
        this.dsDichVuVT = rs.data;
        this.dsDichVuVT = this.dsDichVuVT.filter((item) => item.SUDUNG === 1);
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsLoaiHinhTB: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB`
        );
        this.tmp_dsLoaiHinhTB = rs.data;
        //this.dsDichVuVT=this.dsDichVuVT.filter((item)=>item.SUDUNG===1);
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsKieuLD: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_KIEU_LD`
        );
        this.tmp_dsKieuLD = rs.data;
        //this.dsDichVuVT=this.dsDichVuVT.filter((item)=>item.SUDUNG===1);
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsMucCuocTB: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/MUCCUOC_TB`
        );
        this.tmp_dsMucCuocTB = rs.data;
        //this.dsDichVuVT=this.dsDichVuVT.filter((item)=>item.SUDUNG===1);
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsDoiTuongTB: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/DOITUONG`
        );
        this.dsDoiTuong = rs.data;
      } catch (error) { }
    },
    getDsTrangBi: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/TRANGBI`
        );
        this.dsTrangBi = rs.data;
      } catch (error) { }
    },

    getDsThietBiTSL: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAITBI_TSL`
        );
        this.tmp_dsThietBi = rs.data;
        this.dsThietBiTSL = this.tmp_dsThietBi.filter(
          (item) => item.DICHVUVT_ID === 9
        );
      } catch (error) { }
    },
    getDsThietBiIMS: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/THIETBI_IMS`
        );
        this.dsThietBiIMS = rs.data;
      } catch (error) { }
    },
    getDsLoaiCap: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAI_CAP`
        );
        this.dsLoaiCap = rs.data;
      } catch (error) { }
    },

    getDsMucCuoc: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/MUCCUOC`
        );
        this.dsMucCuoc = rs.data;
      } catch (error) { }
    },
    getDsTocDo: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/TOCDO_ADSL`
        );
        this.tmp_dsTocDo = rs.data;
        //this.dsDichVuVT=this.dsDichVuVT.filter((item)=>item.SUDUNG===1);
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsDslam: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/DSLAM`
        );
        this.tmp_dsDslam = rs.data;
      } catch (error) { }
    },
    getDsDonViQL: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/DONVI_QL`
        );
        this.dsDonViQL = rs.data;
        //this.dsDichVuVT=this.dsDichVuVT.filter((item)=>item.SUDUNG===1);
        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
        this.dsDonViQLDM = this.dsDonViQL.filter(
          (item) => item.LOAIDV_ID === 2
        );
        this.dsDonViQLTC = this.dsDonViQL.filter(
          (item) => item.LOAIDV_ID === 3
        );
      } catch (error) { }
    },
    getDsChuQuan: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/CHUQUAN`
        );
        this.dsChuQuan = rs.data;
      } catch (error) { }
    },
    getDsDoiTac: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/DOITAC`
        );
        this.dsDoiTac = rs.data;
      } catch (error) { }
    },
    getDsTocDoKenh: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/TOCDO_KENH`
        );
        this.dsTocDoKenh = rs.data;
      } catch (error) { }
    },
    getDsLoaiKenh: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAI_KENH`
        );
        this.dsLoaiKenh = rs.data;
      } catch (error) { }
    },
    getDsLoaiModem: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAI_MODEM`
        );
        this.dsLoaiModem = rs.data;
      } catch (error) { }
    },
    getDsThoiHan: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/THOIHAN`
        );
        this.dsThoiHan = rs.data;
      } catch (error) { }
    },
    getDsDiaChi: async function (diachi_id) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/thongtin-diachi/${diachi_id}`
        );
        if (rs.data.length > 0) this.dsDiaChi = rs.data;
      } catch (error) { }
    },

    getDsThongTinMoRong: async function () {
      let param = {
        p_dichvu_id: this.thuebao.dichvuvt_id,
        p_thuebao_id: this.thuebao.thuebao_id,
      };
      try {
        const rs = await this.$root.context.post(
          `/web-quantri/quan-ly-danh-ba/thong-tin-thue-bao-mo-rong`,
          param
        );
        this.dsTBMoRong = rs.data;
      } catch (error) { }
    },
    getDsThuocTinhHDTB_CNTT: async function (loaithuebao, thuebao_id) {
      try {
        this.loading(true);
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-thuoctinh-capnhat-danhba?ploaitbID=${loaithuebao}&pThueBaoID=${thuebao_id}&pTenBang=HDTB_CNTT&pOptList=00000`
        );
        this.dsThuocTinhHDTB_CNTT = rs.data;
        this.loading(false);
      } catch (error) {
        this.loading(false);
      }
    },

    maKH_keyPress: async function (event) {
      if (event.code == "Enter") {
        //let madg = this.khachhang.ma_gd == "" ? "0" : this.khachhang.ma_kh;
        let madg = this.khachhang.ma_kh;
        this.ma_timkiem = madg;
        // Object.assign(this.$data, this.initialState());
        await this.getDsKhachHang(madg);
      }
    },

    hienthi_thongtin_khachhang: async function () {
      this.isDisableBtnGhiLai = false;
      this.isDisableNhapMoi = false;
      this.isDisableBtnThemTT = false;
      this.isDisableBtnXoaTT = false;
      this.khachhang.ma_hd = this.dsKhachHang[0].MA_HD;
      this.khachhang.ngay_lap_hd = this.dsKhachHang[0].NGAYLAP_HD;
      this.khachhang.khachhang_id = this.dsKhachHang[0].KHACHHANG_ID;
      await this.getDsThanhToan(this.khachhang.khachhang_id);
      await this.getDSThueBaoTheoKachHangID(this.khachhang.khachhang_id);

      this.khachhang.ma_kh = this.dsKhachHang[0].MA_KH;
      this.khachhang.ten_kh = this.dsKhachHang[0].TEN_KH;
      this.khachhang.diachi_kh = this.dsKhachHang[0].DIACHI_KH;
      this.khachhang.diem_tn = this.dsKhachHang[0].DIEM_TN;
      this.khachhang.loaigt_id = this.dsKhachHang[0].LOAIGT_ID;
      this.khachhang.so_gt = this.dsKhachHang[0].SO_GT;
      this.khachhang.ngay_cap = this.dsKhachHang[0].NGAYCAP;
      this.khachhang.noi_cap = this.dsKhachHang[0].NOICAP;
      this.khachhang.ngay_hethan = this.dsKhachHang[0].NGAYHETHAN_GT;
      this.khachhang.ngay_sinh = this.dsKhachHang[0].NGAY_SN;
      this.khachhang.gioi_tinh =
        this.dsKhachHang[0].GIOITINH === 0
          ? "rdoF"
          : this.dsKhachHang[0].GIOITINH === 1
            ? "rdoM"
            : "rdoNo";

      this.khachhang.quoctich_id = this.dsKhachHang[0].QUOCTICH_ID;
      if (this.khachhang.quoctich_id > 0) this.qt_checked = true;
      this.khachhang.nganhnghe_id = this.dsKhachHang[0].NGANHNGHE_ID;
      this.khachhang.tnc1_id = this.dsKhachHang[0].TNC1_ID;
      this.khachhang.tnc2_id = this.dsKhachHang[0].TNC2_ID;
      this.khachhang.tnc3_id = this.dsKhachHang[0].TNC3_ID;
      this.khachhang.tnc4_id = this.dsKhachHang[0].TNC4_ID;
      this.khachhang.ten_nganhnghe = this.dsKhachHang[0].TENNGANHNGHE;
      this.khachhang.dantoc_id = this.dsKhachHang[0].DANTOC_ID;
      if (this.khachhang.dantoc_id > 0) this.dt_checked = true;
      this.khachhang.loaikh_id = this.dsKhachHang[0].LOAIKH_ID;
      this.khachhang.phanloai_kh = this.dsKhachHang[0].PHANLOAIKH_ID;
      this.khachhang.nguoi_dd = this.dsKhachHang[0].NGUOI_DD;
      this.khachhang.chuc_vu = this.dsKhachHang[0].CHUCDANH;
      this.khachhang.diachi_ndd = this.dsKhachHang[0].DIACHI_NDD;
      this.khachhang.dien_thoai = this.dsKhachHang[0].SO_DT;
      this.khachhang.so_fax = this.dsKhachHang[0].SO_FAX;
      this.khachhang.ho_khau = this.dsKhachHang[0].HOKHAU;
      this.khachhang.khlon_id = this.dsKhachHang[0].KHLON_ID;
      this.khachhang.noicap_hk = this.dsKhachHang[0].NOICAP_HK;
      this.khachhang.ngay_cap_hk = this.dsKhachHang[0].NGAY_HK;
      this.khachhang.nganhang_id = this.dsKhachHang[0].NGANHANG_ID;
      if (this.khachhang.nganhang_id > 0) this.nh_checked = true;
      this.khachhang.mst = this.dsKhachHang[0].MST;
      this.khachhang.so_tk = this.dsKhachHang[0].STK;
      this.khachhang.dac_trung = this.dsKhachHang[0].DACTRUNG;
      this.khachhang.ghi_chu = this.dsKhachHang[0].GHICHU;
      this.khachhang.diachi_id = this.dsKhachHang[0].DIACHI_ID;
      if (this.khachhang.diachi_id > 0)
        await this.loadDiaChiKH(this.khachhang.diachi_id);
      this.khachhang.ten_loaikh = this.dsKhachHang[0].TEN_LOAIKH;

      this.hienthi_thongtin_thanhtoan(this.dsThanhToan[0]);

      this.dsThueBaoDD = this.dsThueBao.filter(
        (item) => item.THANHTOAN_ID === this.thanhtoan.thanhtoan_id
      );

      this.tmp_temp = this.dsThueBao.filter(
        (item) => item.MA_TB === this.ma_timkiem
      );

      if (this.dsThueBao.length > 0) {
        if (this.tmp_temp.length > 0)
          this.hienthi_thongtin_thuebao(this.tmp_temp[0]);
        else this.hienthi_thongtin_thuebao(this.dsThueBao[0]);
      }

    },

    MaTTChange(...data) {
      //console.log(data);
      this.hienthi_thongtin_thanhtoan(data[1]);
      this.dsThueBaoDD = this.dsThueBao.filter(
        (item) => item.THANHTOAN_ID === this.thanhtoan.thanhtoan_id
      );
    },

    MaTBChange(...data) {
      let loaitb_cu=this.thuebao.loaitb_id;
      this.hienthi_thongtin_thuebao(data[1]);
      if (loaitb_cu== data[1].LOAITB_ID)        
          this.hienthi_thongtin_danhba_con();
    },
    NganhNghe_selectedChanged(dataItem) {
      if (dataItem) {
        this.khachhang.nganhnghe_id = dataItem.NGANHNGHE_ID;
      }
    },
    hienthi_thongtin_thanhtoan: async function (data) {
      this.thanhtoan.ma_tt = data.MA_TT;
      this.thanhtoan.matt_ccbs = "";
      this.thanhtoan.matb_dd = data.MATB_DD;
      this.thanhtoan.thanhtoan_id = data.THANHTOAN_ID;
      await this.getDsDichVuKhacThanhToan();
      this.thanhtoan.ten_tt = data.TEN_TT;
      this.thanhtoan.diachi_tt = data.DIACHI_TT;
      this.thanhtoan.diachi_ct = data.DIACHI_CT;
      this.thanhtoan.diachi_bc = data.DIACHI_BC;
      this.thanhtoan.mst = data.MST;
      this.thanhtoan.stk = data.STK;
      this.thanhtoan.chu_tk = data.CHU_TK;
      this.thanhtoan.email = data.EMAIL;
      this.thanhtoan.so_dt = data.SO_DT;
      this.thanhtoan.nganhang_id = data.NGANHANG_ID;
      if (this.thanhtoan.nganhang_id > 0) this.nhtt_checked = true;
      this.thanhtoan.httt_id = data.HTTT_ID;
      this.thanhtoan.donvi_id = data.DONVI_ID;
      this.thanhtoan.mig_id = data.MIG_ID;
      this.thanhtoan.main_ghep = data.MAIN_GHEP;
      this.thanhtoan.diachi_id = data.DIACHI_ID;
      this.thanhtoan.diachibc_id = data.DIACHIBC_ID;
      this.thanhtoan.manv_tc = data.MANV_TC;
      this.thanhtoan.ma_tuyen = data.MA_TUYEN;
      this.thanhtoan.tuyenthu_id = data.TUYENTHU_ID;
      this.thanhtoan.ghi_chu = data.GHICHU;
      this.thanhtoan.ma_dv_qhns = data.DV_QHNS;
      this.thanhtoan.ma_ndkt = data.MA_NDKT;
      this.thanhtoan.ma_chuong = data.MACHUONG;
      this.thanhtoan.ma_nguon_kt = data.MN_KT;
      this.thanhtoan.ma_nguon_ngansach = data.MN_NSNN;
      this.thanhtoan.tien_dm = data.TIEN_DM
      if (this.thanhtoan.diachi_id > 0) {
        await this.loadDiaChiTT(this.thanhtoan.diachi_id);
      }

      if (this.thanhtoan.diachibc_id > 0) {
        await this.loadDiaChiBC(this.thanhtoan.diachibc_id);
      }
    },

    getData: function (data) {
      return data.filter((item) => item.LOAITB_ID === this.thuebao.loaitb_id);
    },
    onCloseModalLKH: function (data) {
      this.khachhang.loaikh_id = data.loaikh_id;
    },
    closeModalDiaChi: async function (data) {
      //console.log(this.isShowDiaChiKH);
      if (this.isShowDiaChiKH) {
        this.isShowDiaChiKH = !this.isShowDiaChiKH;
        if (data.isUpdated) {
          this.query = data.diaChi;
          this.ttdiachi_kh.tinh_id = data.chiTietDiaChi.tinhThanh_id;
          this.ttdiachi_kh.quan_id = data.chiTietDiaChi.quanHuyen_id;
          this.ttdiachi_kh.phuong_id = data.chiTietDiaChi.phuongXa_id;
          this.ttdiachi_kh.pho_id = data.chiTietDiaChi.duongPho_id;
          this.ttdiachi_kh.ap_id = data.chiTietDiaChi.ap_id;
          this.ttdiachi_kh.khu_id = data.chiTietDiaChi.khu_id;
          this.ttdiachi_kh.dacdiem_id = data.chiTietDiaChi.dacDiem_id;
          this.ttdiachi_kh.sonha = data.chiTietDiaChi.soNha;
          this.ttdiachi_kh.dia_chi = data.diaChi;
          this.khachhang.diachi_kh = data.diaChi;
        }
      }
      if (this.isShowDiaChiNDD) {
        this.isShowDiaChiNDD = !this.isShowDiaChiNDD;
        if (data.isUpdated) {
          this.khachhang.diachi_ndd = data.diaChi;
        }

      }
      if (this.isShowDiaChiHK) {
        this.isShowDiaChiHK = !this.isShowDiaChiHK;
        if (data.isUpdated) {
          this.khachhang.ho_khau = data.diaChi;
        }
      }
      if (this.isShowDiaChiTT) {
        if (data.isUpdated) {
          this.query = data.diaChi;
          this.ttdiachi_tt.tinh_id = data.chiTietDiaChi.tinhThanh_id;
          this.ttdiachi_tt.quan_id = data.chiTietDiaChi.quanHuyen_id;
          this.ttdiachi_tt.phuong_id = data.chiTietDiaChi.phuongXa_id;
          this.ttdiachi_tt.pho_id = data.chiTietDiaChi.duongPho_id;
          this.ttdiachi_tt.ap_id = data.chiTietDiaChi.ap_id;
          this.ttdiachi_tt.khu_id = data.chiTietDiaChi.khu_id;
          this.ttdiachi_tt.dacdiem_id = data.chiTietDiaChi.dacDiem_id;
          this.ttdiachi_tt.sonha = data.chiTietDiaChi.soNha;
          this.ttdiachi_tt.dia_chi = data.diaChi;
          this.thanhtoan.diachi_tt = data.diaChi;
        }
        this.isShowDiaChiTT = !this.isShowDiaChiTT;
      }

      if (this.isShowDiaChiCT) {
        if (data.isUpdated) {
          this.query = data.diaChi;
          this.ttdiachi_ct.tinh_id = data.chiTietDiaChi.tinhThanh_id;
          this.ttdiachi_ct.quan_id = data.chiTietDiaChi.quanHuyen_id;
          this.ttdiachi_ct.phuong_id = data.chiTietDiaChi.phuongXa_id;
          this.ttdiachi_ct.pho_id = data.chiTietDiaChi.duongPho_id;
          this.ttdiachi_ct.ap_id = data.chiTietDiaChi.ap_id;
          this.ttdiachi_ct.khu_id = data.chiTietDiaChi.khu_id;
          this.ttdiachi_ct.dacdiem_id = data.chiTietDiaChi.dacDiem_id;
          this.ttdiachi_ct.sonha = data.chiTietDiaChi.soNha;
          this.ttdiachi_ct.dia_chi = data.diaChi;
          this.thanhtoan.diachi_ct = data.diaChi;
        }
        this.isShowDiaChiCT = !this.isShowDiaChiCT;
      }

      if (this.isShowDiaChiBC) {
        if (data.isUpdated) {
          this.query = data.diaChi;
          this.ttdiachi_bc.tinh_id = data.chiTietDiaChi.tinhThanh_id;
          this.ttdiachi_bc.quan_id = data.chiTietDiaChi.quanHuyen_id;
          this.ttdiachi_bc.phuong_id = data.chiTietDiaChi.phuongXa_id;
          this.ttdiachi_bc.pho_id = data.chiTietDiaChi.duongPho_id;
          this.ttdiachi_bc.ap_id = data.chiTietDiaChi.ap_id;
          this.ttdiachi_bc.khu_id = data.chiTietDiaChi.khu_id;
          this.ttdiachi_bc.dacdiem_id = data.chiTietDiaChi.dacDiem_id;
          this.ttdiachi_bc.sonha = data.chiTietDiaChi.soNha;
          this.ttdiachi_bc.dia_chi = data.diaChi;
          this.thanhtoan.diachi_bc = data.diaChi;
          //this.timKiem();
        }
        this.isShowDiaChiBC = !this.isShowDiaChiBC;
      }

      if (this.isShowDiaChiTB) {
        if (data.isUpdated) {
          this.query = data.diaChi;
          this.ttdiachi_tb.tinh_id = data.chiTietDiaChi.tinhThanh_id;
          this.ttdiachi_tb.quan_id = data.chiTietDiaChi.quanHuyen_id;
          this.ttdiachi_tb.phuong_id = data.chiTietDiaChi.phuongXa_id;
          this.ttdiachi_tb.pho_id = data.chiTietDiaChi.duongPho_id;
          this.ttdiachi_tb.ap_id = data.chiTietDiaChi.ap_id;
          this.ttdiachi_tb.khu_id = data.chiTietDiaChi.khu_id;
          this.ttdiachi_tb.dacdiem_id = data.chiTietDiaChi.dacDiem_id;
          this.ttdiachi_tb.sonha = data.chiTietDiaChi.soNha;
          this.ttdiachi_tb.dia_chi = data.diaChi;
          this.thuebao.diachi_tb = data.diaChi;
          //this.timKiem();
        }
        this.isShowDiaChiTB = !this.isShowDiaChiTB;
      }

      if (this.isShowDiaChiLD) {
        if (data.isUpdated) {
          this.query = data.diaChi;
          this.ttdiachi_ld.tinh_id = data.chiTietDiaChi.tinhThanh_id;
          this.ttdiachi_ld.quan_id = data.chiTietDiaChi.quanHuyen_id;
          this.ttdiachi_ld.phuong_id = data.chiTietDiaChi.phuongXa_id;
          this.ttdiachi_ld.pho_id = data.chiTietDiaChi.duongPho_id;
          this.ttdiachi_ld.ap_id = data.chiTietDiaChi.ap_id;
          this.ttdiachi_ld.khu_id = data.chiTietDiaChi.khu_id;
          this.ttdiachi_ld.dacdiem_id = data.chiTietDiaChi.dacDiem_id;
          this.ttdiachi_ld.sonha = data.chiTietDiaChi.soNha;
          this.ttdiachi_ld.dia_chi = data.diaChi;
          this.thuebao.diachi_ld = data.diaChi;
          //this.timKiem();
          await this.getKhuVUCDCLD(this.ttdiachi_ld);
        }
        this.isShowDiaChiLD = !this.isShowDiaChiLD;
      }
    },
    getKhuVUCDCLD: async function (diachi) {
      try {
        let dskhuvuc = [];
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/css/lay-thong-tin-khu-vuc-theo-dia-danh?quanId=${diachi.quan_id}&phuongId=${diachi.phuong_id}&phoId=${diachi.pho_id}&apId=${diachi.ap_id}&khuId=${diachi.khu_id}&dacDiemId=${diachi.dacdiem_id}`
        );
        dskhuvuc = rs.data;
        console.log(dskhuvuc)
        if (dskhuvuc) {
          this.thuebao.ma_kv = dskhuvuc[0].ma_kv
          this.thuebao.ten_kv = dskhuvuc[0].ma_kv
        }
        // if (this.dsDanToc.length>0)
        //   this.khachhang.dantoc_id=this.khachhang.dantoc_id
      } catch (error) { }
    },
    loadDiaChiKH: async function (diachi_id) {
      await this.getDsDiaChi(diachi_id);
      if (this.dsDiaChi.length > 0) {
        this.ttdiachi_kh.dia_chi = this.dsDiaChi[0].DIACHI;
        this.ttdiachi_kh.tinh_id = this.dsDiaChi[0].TINH_ID;
        this.ttdiachi_kh.quan_id = this.dsDiaChi[0].QUAN_ID;
        this.ttdiachi_kh.phuong_id = this.dsDiaChi[0].PHUONG_ID;
        this.ttdiachi_kh.pho_id = this.dsDiaChi[0].PHO_ID;
        this.ttdiachi_kh.ap_id = this.dsDiaChi[0].AP_ID;
        this.ttdiachi_kh.khu_id = this.dsDiaChi[0].KHU_ID;
        this.ttdiachi_kh.dacdiem_id = this.dsDiaChi[0].DACDIEM_ID;
        this.ttdiachi_kh.sonha = this.dsDiaChi[0].SONHA;
        this.query = this.dsDiaChi[0].DIACHI;
      }
    },
    loadDiaChiTT: async function (diachi_id) {
      await this.getDsDiaChi(diachi_id);
      if (this.dsDiaChi.length > 0) {
        this.ttdiachi_tt.dia_chi = this.dsDiaChi[0].DIACHI;
        this.ttdiachi_tt.tinh_id = this.dsDiaChi[0].TINH_ID;
        this.ttdiachi_tt.quan_id = this.dsDiaChi[0].QUAN_ID;
        this.ttdiachi_tt.phuong_id = this.dsDiaChi[0].PHUONG_ID;
        this.ttdiachi_tt.pho_id = this.dsDiaChi[0].PHO_ID;
        this.ttdiachi_tt.ap_id = this.dsDiaChi[0].AP_ID;
        this.ttdiachi_tt.khu_id = this.dsDiaChi[0].KHU_ID;
        this.ttdiachi_tt.dacdiem_id = this.dsDiaChi[0].DACDIEM_ID;
        this.ttdiachi_tt.sonha = this.dsDiaChi[0].SONHA;
        this.query = this.dsDiaChi[0].DIACHI;
      }
    },
    loadDiaChiBC: async function (diachi_id) {
      await this.getDsDiaChi(diachi_id);
      if (this.dsDiaChi.length > 0) {
        this.ttdiachi_bc.dia_chi = this.dsDiaChi[0].DIACHI;
        this.ttdiachi_bc.tinh_id = this.dsDiaChi[0].TINH_ID;
        this.ttdiachi_bc.quan_id = this.dsDiaChi[0].QUAN_ID;
        this.ttdiachi_bc.phuong_id = this.dsDiaChi[0].PHUONG_ID;
        this.ttdiachi_bc.pho_id = this.dsDiaChi[0].PHO_ID;
        this.ttdiachi_bc.ap_id = this.dsDiaChi[0].AP_ID;
        this.ttdiachi_bc.khu_id = this.dsDiaChi[0].KHU_ID;
        this.ttdiachi_bc.dacdiem_id = this.dsDiaChi[0].DACDIEM_ID;
        this.ttdiachi_bc.sonha = this.dsDiaChi[0].SONHA;
        this.query = this.dsDiaChi[0].DIACHI;
      }
    },
    loadDiaChiTB: async function (diachi_id) {
      await this.getDsDiaChi(diachi_id);
      if (this.dsDiaChi.length > 0) {
        this.ttdiachi_tb.dia_chi = this.dsDiaChi[0].DIACHI;
        this.ttdiachi_tb.tinh_id = this.dsDiaChi[0].TINH_ID;
        this.ttdiachi_tb.quan_id = this.dsDiaChi[0].QUAN_ID;
        this.ttdiachi_tb.phuong_id = this.dsDiaChi[0].PHUONG_ID;
        this.ttdiachi_tb.pho_id = this.dsDiaChi[0].PHO_ID;
        this.ttdiachi_tb.ap_id = this.dsDiaChi[0].AP_ID;
        this.ttdiachi_tb.khu_id = this.dsDiaChi[0].KHU_ID;
        this.ttdiachi_tb.dacdiem_id = this.dsDiaChi[0].DACDIEM_ID;
        this.ttdiachi_tb.sonha = this.dsDiaChi[0].SONHA;
        this.query = this.dsDiaChi[0].DIACHI;
      }
    },
    loadDiaChiLD: async function (diachi_id) {
      await this.getDsDiaChi(diachi_id);
      if (this.dsDiaChi.length > 0) {
        this.ttdiachi_ld.dia_chi = this.dsDiaChi[0].DIACHI;
        this.ttdiachi_ld.tinh_id = this.dsDiaChi[0].TINH_ID;
        this.ttdiachi_ld.quan_id = this.dsDiaChi[0].QUAN_ID;
        this.ttdiachi_ld.phuong_id = this.dsDiaChi[0].PHUONG_ID;
        this.ttdiachi_ld.pho_id = this.dsDiaChi[0].PHO_ID;
        this.ttdiachi_ld.ap_id = this.dsDiaChi[0].AP_ID;
        this.ttdiachi_ld.khu_id = this.dsDiaChi[0].KHU_ID;
        this.ttdiachi_ld.dacdiem_id = this.dsDiaChi[0].DACDIEM_ID;
        this.ttdiachi_ld.sonha = this.dsDiaChi[0].SONHA;
        this.query = this.dsDiaChi[0].DIACHI;
      }
    },
    onTTLienHe: function () {
      this.$bvModal.show("modal-ttlh");
    },
    onClickHideModalTTLH: function () {
      this.$bvModal.hide("modal-ttlh");
    },
    onTTToaNha: function () {

      let popup = this.$refs.popupToaNha;
      popup.txtMaTB = this.thuebao.ma_tb;
      this.$nextTick(() => {
        popup.show();
      });
    },
    onTTVatTu: function () {
      this.$refs.popupTraCuuVatTu.dialogOpen()
    },
    onTTEmail: function () {
      this.$bvModal.show("modal-email");
    },
    onClickHideModalEmail() {
      this.$bvModal.hide("modal-email");
    },
    onDiaChiNDD: function () {
      this.$toast.error(
        "Chức năng này tham chiếu đến mục 1.1.5 thuộc tài liệu UR2.1.001 – Tiếp nhận yêu cầu khảo sát lắp đặt."
      );
    },
    onMaInGhep: function () {
      if (!this.khachhang.khachhang_id || this.khachhang.khachhang_id == 0) {
        this.$toast.error("Chưa có thông tin về danh bạ khách hàng!");
        return;
      }

      this.$refs.dlgMaIG.openDialog()

    },
    closeMaInGhep(val) {
      console.log(val);
      this.thanhtoan.main_ghep = val;
      this.$refs.dlgMaIG.hideDialog()
    },

    onMaNhanVienThuCuoc: function () {

      if (this.thanhtoan.donvi_id > 0)
        this.$refs.dlgTimKiemNVTC.openDialog()

    },
    async chonNVTCHandler(e) {
      this.thanhtoan.nhanvientc_id = e.NHANVIEN_ID;
      this.thanhtoan.manv_tc = e.MANV_TC;
      this.thanhtoan.tuyenthu_id = e.TUYENTHU_ID;
      this.thanhtoan.ma_tuyen = e.MA_TUYENTHU;
    },

    onTraCuuMaKH() {
      this.$bvModal.show('popupSearchAccount');
    },
    acceptSearchAccount: async function (item) {
      let even = { code: 'Enter' }
      this.$bvModal.hide('popupSearchAccount')
      console.log(item)
      this.khachhang.ma_kh = item.ma_kh;
      if (this.khachhang.ma_kh != null)
        this.maKH_keyPress(even);
      // if(this.maKhachHang && this.maKhachHang.trim()!="")
      //     this.getThongTinHopDong()
    },
    onChiTietNganhNghe: function () {
      this.objChiTietNganhNghe = {}
      this.objChiTietNganhNghe.nganhnghe_id = this.khachhang.nganhnghe_id
      this.objChiTietNganhNghe.TNC1_ID = this.khachhang.tnc1_id
      this.objChiTietNganhNghe.TNC2_ID = this.khachhang.tnc2_id
      this.objChiTietNganhNghe.TNC3_ID = this.khachhang.tnc3_id
      this.objChiTietNganhNghe.TNC4_ID = this.khachhang.tnc4_id
      this.$bvModal.show("popupChiTietNganhNghe")
    },
    getTNC(objTNC) {
      this.khachhang.tnc1_id = objTNC.TNC1_ID
      this.khachhang.tnc2_id = objTNC.TNC2_ID
      this.khachhang.tnc3_id = objTNC.TNC3_ID
      this.khachhang.tnc4_id = objTNC.TNC4_ID
      this.khachhang.nganhnghe_id = objTNC.nganhnghe_id
    },


    onKhuVuc: function () {
      this.popupComponentData = { diachi: this.ttdiachi_tb, khuvuc_id: this.khuvuc_tb.khuvuc_id, hdtb_id: 0, thuebao_id: this.thuebao.thuebao_id, donvi_id: this.thuebao.donviql_id }

      this.$bvModal.show("frmKhuVuc");
    },
    khuVuc_Dong(val) {
      //console.log('da den day:', val);
      this.$bvModal.hide("frmKhuVuc");
    },
    khuVuc_ChapNhan(val) {
      console.log('khu vuc chap nhan: ', val);
      this.thuebao.khuvuc = val.ma_kv;
      this.thuebao.khuvuc_id = val.khuvuc_id;
      this.$bvModal.hide("frmKhuVuc");
    },


    checkValue: function () {

      let message = '';
      if (this.khachhang.ten_kh === '')
        message = 'Tên Khách hàng không được để trống';
      if (this.khachhang.ma_kh === '') {
        message = 'Mã Khách hàng không được để trống';
        this.$refs.r_makh.focus();
      }


      if (this.khachhang.diachi_kh === '') {
        message = 'Địa chỉ không được để trống';
        this.$refs.r_tenkh.focus();
      }

      if (this.khachhang.so_gt === '') {
        message = 'Số giấy tờ không được để trống';
        this.$refs.r_sogt.focus();
      }

      if (this.khachhang.loaigt_id < 1) {
        message = 'Bạn chưa chọn loại giấy tờ';
        //this.$refs.r_loaigt.focus();
      }
      if (this.thanhtoan.httt_id < 1) {
        message = 'Bạn chưa chọn hình thức thanh toán';
        this.$refs.r_hinhthuctt.focus();
      }
      if (this.thanhtoan.manv_tc === '') {
        message = 'Nhân viên thu cước không được để trống';
        this.$refs.r_manvtc.focus();
      }

      if (this.khachhang.ngay_cap === null) {
        message = 'Bạn chưa chọn ngày cấp giấy tờ';
        this.$refs.r_ngaycap.focus();
      }
      if (this.khachhang.ngay_sinh === null) {
        message = 'Bạn chưa chọn ngày sinh';
        this.$refs.r_ngaysinh.focus();
      }
      if (this.khachhang.khachhang_id === 0) {
        message = 'Bạn Chưa nhập thông tin tìm kiếm ';
        this.$refs.r_makh.focus();
      }
      return message;
    },
    initialState: function () {
      return {
        objChiTietNganhNghe: {},
        qt_checked: false,
        dt_checked: false,
        sudungdvgt: '',
        sudung_dvkhac_tt: '',
        sudung_dvkhac_tb: '',
        dsThanhToan: [],
        dsThueBao: [],
        khachhang: {
          ma_hd: "",
          ngay_lap_hd: "",
          khachhang_id: 0,
          ma_kh: "",
          ten_kh: "",
          diachi_kh: "",
          diem_tn: "",
          loaigt_id: 0,
          so_gt: "",
          ngay_cap: "",
          noi_cap: "",
          ngay_hethan: "",
          ngay_sinh: "",
          gioi_tinh: "rdoNo",
          quoctich_id: 1,
          nganhnghe_id: 0,
          dantoc_id: 1,
          loaikh_id: 0,
          phanloai_kh: "",
          nguoi_dd: "",
          chuc_vu: "",
          diachi_ndd: "",
          dien_thoai: "",
          fax: "",
          ho_khau: "",
          noicap_hk: "",
          ngay_cap_hk: "",
          nganhang_id: 0,
          mst: "",
          so_tk: "",
          dac_trung: "",
          ghi_chu: "",
          diachi_id: 0,
          khlon_id: 0,
          tnc1_id: 0,
          tnc2_id: 0,
          tnc3_id: 0,
          tnc4_id: 0,
          ten_loaikh: "",
        },
        thanhtoan: {
          ma_tt: "",
          matb_dd: "",
          matt_ccbs: "",
          thanhtoan_id: 0,
          ten_tt: "",
          diachi_tt: "",
          diachi_ct: "",
          diachi_bc: "",
          mst: "",
          stk: "",
          chu_tk: "",
          email: "",
          so_dt: "",
          nganhang_id: "",
          httt_id: 0,
          donvi_id: "",
          manv_ql: "",
          mig_id: "",
          main_ghep: "",
          diachi_id: 0,
          diachibc_id: 0,
          manv_tc: "",
          ma_tuyen: "",
          ghi_chu: "",
          tuyenthu_id: "",
          ma_dv_qhns: "",
          ma_ndkt: "",
          ma_chuong: "",
          ma_nguon_kt: "",
          ma_nguon_ngansach: "",
          tien_dm: 0,
        },
        ttdiachi_kh: {
          dia_chi: "",
          tinh_id: 0,
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          sonha: "",
        },
        ttdiachi_tt: {
          dia_chi: "",
          tinh_id: 0,
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          sonha: "",
        },

        ttdiachi_ct: {
          dia_chi: "",
          tinh_id: 0,
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          sonha: "",
        },

        ttdiachi_bc: {
          dia_chi: "",
          tinh_id: 0,
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          sonha: "",
        },
        ttdiachi_tb: {
          dia_chi: "",
          tinh_id: 0,
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          sonha: "",
        },
        ttdiachi_ld: {
          dia_chi: "",
          tinh_id: 0,
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          sonha: "",
        },

        thuebao: {
          thuebao_id: 0,
          loaihd_id: 0,
          trangthaitb_id: 0,
          trangthai_tb: "",
          dichvuvt_id: 0,
          loaitb_id: 0,
          kieuld_id: 0,
          khuvuc: "",
          khuvuc_id: 0,
          muccuoc_tb: 0,
          muccuoctb_id: 0,
          doituong_id: 0,
          ma_tb: "",
          ma_lt: "",
          thanhtoan_id: 0,
          ten_tb: "",
          ngay_sinh: "",
          diachi_tb: "",
          diachi_ld: "",
          donvi_id: 0,
          ma_dhvip: "",
          ghi_chu: "",
          sothang_ck: 0,
          ngay_kt_ck: "",
          ngay_sudung: "",
          cuoc_tb: 0,
          cuoc_dv: 0,
          ngay_sinh: "",
          diachi_id: 0,
          diachild_id: 0,
          chuyenmang: 0,
          nhanvien_am_id: 0,
        },
        khuvuc_tb: {
          khuvuc_id: 0,
          ma_kv: "",
          ten_kv: "",
          loaikv_id: 0,
        },

        db_con: {
          donvi_id: 0,
          bras_id: 0,
          dslam_id: 0,
          tocdo_id: 0,
          tocdothuc_id: 0,
          muccuoc_id: 0,
          chuquan_id: 0,
          loainode_id: 1,
          doitac_id: 0,
          tocdopir_id: 0,
          tocdocir_id: 0,
          loaikenh_id: 0,
          loaimd_id: 0,
          thoihan_id: 0,
          quoctich_id: 0,
          tocdokenh_id: 0,
          thietbid_id: 0,
          trangbi_id: 0,
          cuoc_dv: 0,
          username: "",
          password: "",
          slot: "",
          port: "",
          rack: "",
          shelf: "",
          vpi: "",
          vci: "",
          loaicap_id: 0,
          tucap_goc: "",
          doicap_goc: "",
          cap_goc: "",
          hopcap_ngon: "",
          tucap_ngon: "",
          doicap_ngon: "",
          cap_ngon: "",
          tendslam: "",
          tuyencap: "",
          lientu: "",
          culy: "",
          thietbi_id: 0,
          system: "",
          email: "",
          ipdslam: "",
          nasportid: "",
          portmdf: "",
          adslport: "",
          onupass: "",
          iptinh: "",
          matn: "",
          congnghe: "",
          diachi_mac: "",
          diachi_pop: "",
          soluong: 0,
          cuoc_tk: 0,
          cuoc_tc: 0,
          cuoc_ht: 0,
          cuoc_tbi: 0,
          cuoc_tkdt: 0,
          cuoc_tcdt: 0,
          cuoc_isp: 0,
          cuoc_nix: 0,
          cuoc_ip: 0,
          mahd_dt: "",
          diachi_dau: "",
          diachi_cuoi: "",
          donvi_id_dau: 0,
          donvi_id_cuoi: 0,
          site_id_dau: 0,
          site_id_cuoi: 0,
          khuvuc_id_dau: 0,
          khuvuc_id_cuoi: 0,
          khuvuc_dau: "",
          khuvuc_cuoi: "",
          huong_kn: "",
          huong_ut: "",
          vmp: 0,
          capngoai: 0,
          luonggiua: 0,
          mahd_dt: "",
          ne: "",
          sochinh: "",
          simcard: "",
          sodu_tk: 0,
          goicuoc_id: 0,
        },
      };
    },
    hienThiKhuyenMai: function () {
      this.isShowKhuyenMai = !this.isShowKhuyenMai;
    },
    onNhapMoi: function () {
      Object.assign(this.$data, this.initialState());
      this.isDisableBtnGhiLai = false;
      this.isDisableHuyBo = false;
      this.isDisableNhapMoi = true;
      this.isDisableBtnThemTT = true;
      this.isDisableBtnXoaTT = true;
      this.activetab = 1;
    },
    onHuyBo: function () {
      Object.assign(this.$data, this.initialState());

      this.isDisableBtnGhiLai = true;
      this.isDisableHuyBo = true;
      this.isDisableNhapMoi = false;
      this.isDisableBtnThemTT = true;
      this.isDisableBtnXoaTT = true;
      this.activetab = 1;
    },
    onGhiLai: async function () {
      let message = this.checkValue();
      let dsKhachHang = {
        CHUCDANH: this.khachhang.chuc_vu,
        DACTRUNG: this.khachhang.dac_trung,
        DANTOC_ID: this.khachhang.dantoc_id,
        DIACHI_KH: this.khachhang.diachi_kh,
        DIACHI_NDD: this.khachhang.diachi_ndd,
        GHICHU: this.khachhang.ghi_chu,
        GIOITINH:
          this.khachhang.gioi_tinh === "rdoF"
            ? 0
            : this.khachhang.gioi_tinh === "rdoM"
              ? 1
              : null,
        HOKHAU: this.khachhang.ho_khau,
        KHLON_ID: this.khachhang.khlon_id,
        LOAIGT_ID: this.khachhang.loaigt_id,
        LOAIKH_ID: this.khachhang.loaikh_id,
        MA_HD: this.khachhang.ma_hd,
        MA_KH: this.khachhang.ma_kh,
        MST: this.khachhang.mst,
        NGANHANG_ID: this.nh_checked ? this.khachhang.nganhang_id : null,
        NGANHNGHE_ID: this.khachhang.nganhnghe_id,
        NGAY_HK: this.formatDate(this.khachhang.ngay_cap_hk),
        NGAY_SN: this.formatDate(this.khachhang.ngay_sinh),
        NGAYCAP: this.formatDate(this.khachhang.ngay_cap),
        NGAYHETHAN_GT: this.formatDate(this.khachhang.ngay_hethan),
        NGAYLAP_HD: this.formatDate(this.khachhang.ngay_lap_hd),
        NGUOI_DD: this.khachhang.nguoi_dd,
        NOICAP: this.khachhang.noi_cap,
        NOICAP_HK: this.khachhang.noicap_hk,
        QUOCTICH_ID: this.khachhang.quoctich_id,
        SO_DT: this.khachhang.dien_thoai,
        SO_FAX: this.khachhang.fax,
        SO_GT: this.khachhang.so_gt,
        STK: this.khachhang.so_tk,
        TEN_KH: this.khachhang.ten_kh,
        TNC1_ID: this.khachhang.tnc1_id,
        TNC2_ID: this.khachhang.tnc2_id,
        TNC3_ID: this.khachhang.tnc3_id,
        TNC4_ID: this.khachhang.tnc4_id,
      },
        dsThanhToan = {
          CHU_TK: this.thanhtoan.chu_tk,
          DIACHI_BC: this.thanhtoan.diachi_bc,
          DIACHI_CT: this.thanhtoan.diachi_ct,
          DIACHI_TT: this.thanhtoan.diachi_tt,
          DONVI_ID: this.thanhtoan.donvi_id,
          DV_QHNS: this.thanhtoan.ma_dv_qhns,
          GHICHU: this.thanhtoan.ghi_chu,
          HTTT_ID: this.thanhtoan.httt_id,
          MA_NDKT: this.thanhtoan.ma_ndkt,
          MA_TT: this.thanhtoan.ma_tt,
          MACHUONG: this.thanhtoan.ma_chuong,
          MATB_DD: this.thanhtoan.matb_dd,
          MN_KT: this.thanhtoan.ma_nguon_kt,
          MN_NSNN: this.thanhtoan.ma_nguon_ngansach,
          MST: this.thanhtoan.mst,
          NGANHANG_ID: this.nhtt_checked ? this.thanhtoan.nganhang_id : null,
          STK: this.thanhtoan.stk,
          TEN_TT: this.thanhtoan.ten_tt,
          TUYENTHU_ID: this.thanhtoan.tuyenthu_id,
        },
        dsThueBao = {
          CHUYENMANG: this.thuebao.chuyenmang === true ? 1 : 0,
          CUOC_DV: this.thuebao.cuoc_dv,
          CUOC_TB: Number(this.thuebao.cuoc_tb),
          DIACHI_LD: this.thuebao.diachi_ld,
          DIACHI_TB: this.thuebao.diachi_tb,
          DICHVUVT_ID: this.thuebao.dichvuvt_id,
          DOITUONG_ID: this.thuebao.doituong_id,
          DONVI_ID: this.thuebao.donvi_id,
          LOAITB_ID: this.thuebao.loaitb_id,
          GHICHU: this.thuebao.ghi_chu,
          KIEULD_ID: this.thuebao.kieuld_id,
          MA_TB: this.thuebao.ma_tb,
          MUCUOC_TB: this.thuebao.muccuoc_tb,
          MUCCUOCTB_ID: this.thuebao.muccuoctb_id,
          NGAY_SD: this.formatDate(this.thuebao.ngay_sudung),
          NGAY_SN: this.formatDate(this.thuebao.ngay_sinh),
          TEN_TB: this.thuebao.ten_tb,
          TRANGTHAITB_ID: this.thuebao.trangthaitb_id,
        },
        dsThueBaoKhuVuc = {
          KHUVUC_ID: this.thuebao.khuvuc_id,
          LOAIKV_ID: 4,
        },
        dsThueBaoCon = {
          CELL1: this.db_con.cell1,
          CUOC_HT: Number(this.db_con.cuoc_ht),
          CUOC_TBI: Number(this.db_con.cuoc_tbi),
          CUOC_TC: this.db_con.cuoc_tc,
          CUOC_TCDT: this.db_con.cuoc_tcdt,
          CUOC_TK: this.db_con.cuoc_tk,
          CUOC_TKDT: this.db_con.cuoc_tkdt,
          DIACHI_CUOI: this.db_con.diachi_cuoi,
          DIACHI_DAU: this.db_con.diachi_dau,
          DIACHI_ID_CUOI: 0,
          DIACHI_ID_DAU: 0,
          DOITAC_ID: this.db_con.doitac_id,
          EMAIL: this.db_con.email,
          LOAIKENH_ID: this.db_con.loaikenh_id,
          MA_TB_CUOI: this.thuebao.ma_tb + "_02",
          MA_TB_DAU: this.thuebao.ma_tb + "_01",
          MAC_ADDR: this.db_con.diachi_mac,
          MADOICAP: this.db_con.madoicap,
          MAHD_DT: this.db_con.mahd_dt,
          MATB_TN: this.db_con.matn,
          MC_TBGP_ID: this.db_con.mc_tbgp_id,
          MUCCUOC_ID: this.db_con.muccuoc_id,
          PASSWORD: this.db_con.password,
          SIMCARD: this.db_con.simcard,
          SODU_TK: Number(this.db_con.sodu_tk),
          TOADO: this.db_con.toado,
          TOCDO_ID: this.db_con.tocdo_id,
          TOCDOTHUC_ID: this.db_con.tocdothuc_id,
          TRANGBI_ID: Number(this.db_con.trangbi_id),
          USERNAME: this.db_con.username,
        },
        dsDiaChiKH = {
          AP_ID: this.ttdiachi_kh.ap_id,
          BLOCK: "",
          DIACHI: this.ttdiachi_kh.dia_chi,
          DIACHI_ID: this.khachhang.diachi_id,
          DACDIEM_ID: this.ttdiachi_kh.dacdiem_id,
          KHU_ID: this.ttdiachi_kh.khu_id,
          MOTA: "",
          PHO_ID: this.ttdiachi_kh.pho_id,
          PHONG: "",
          PHUONG_ID: this.ttdiachi_kh.phuong_id,
          QUAN_ID: this.ttdiachi_kh.quan_id,
          SONHA: this.ttdiachi_kh.sonha,
          TANG: "",
          TINH_ID: this.ttdiachi_kh.tinh_id,
          KINHDO: null,
          VIDO: null,
        },
        dsDiaChiTT = {
          AP_ID: this.ttdiachi_tt.ap_id,
          BLOCK: "",
          DIACHI: this.ttdiachi_tt.dia_chi,
          DIACHI_ID: this.thanhtoan.diachi_id,
          DACDIEM_ID: this.ttdiachi_tt.dacdiem_id,
          KHU_ID: this.ttdiachi_tt.khu_id,
          MOTA: "",
          PHO_ID: this.ttdiachi_tt.pho_id,
          PHONG: "",
          PHUONG_ID: this.ttdiachi_tt.phuong_id,
          QUAN_ID: this.ttdiachi_tt.quan_id,
          SONHA: this.ttdiachi_tt.sonha,
          TANG: "",
          TINH_ID: this.ttdiachi_tt.tinh_id,
          KINHDO: null,
          VIDO: null,
        },
        dsDiaChiBC = {
          AP_ID: this.ttdiachi_bc.ap_id,
          BLOCK: "",
          DIACHI: this.ttdiachi_bc.dia_chi,
          DIACHI_ID: this.thanhtoan.diachibc_id,
          DACDIEM_ID: this.ttdiachi_bc.dacdiem_id,
          KHU_ID: this.ttdiachi_bc.khu_id,
          MOTA: "",
          PHO_ID: this.ttdiachi_bc.pho_id,
          PHONG: "",
          PHUONG_ID: this.ttdiachi_bc.phuong_id,
          QUAN_ID: this.ttdiachi_bc.quan_id,
          SONHA: this.ttdiachi_bc.sonha,
          TANG: "",
          TINH_ID: this.ttdiachi_bc.tinh_id,
          KINHDO: null,
          VIDO: null,
        },
        dsDiaChiTB = {
          AP_ID: this.ttdiachi_tb.ap_id,
          BLOCK: "",
          DIACHI: this.ttdiachi_tb.dia_chi,
          DIACHI_ID: this.thuebao.diachi_id,
          DACDIEM_ID: this.ttdiachi_tb.dacdiem_id,
          KHU_ID: this.ttdiachi_tb.khu_id,
          MOTA: "",
          PHO_ID: this.ttdiachi_tb.pho_id,
          PHONG: "",
          PHUONG_ID: this.ttdiachi_tb.phuong_id,
          QUAN_ID: this.ttdiachi_tb.quan_id,
          SONHA: this.ttdiachi_tb.sonha,
          TANG: "",
          TINH_ID: this.ttdiachi_tb.tinh_id,
          KINHDO: null,
          VIDO: null,
        },
        dsDiaChiLD = {
          AP_ID: this.ttdiachi_ld.ap_id,
          BLOCK: "",
          DIACHI: this.ttdiachi_ld.dia_chi,
          DIACHI_ID: this.thuebao.diachild_id,
          DACDIEM_ID: this.ttdiachi_ld.dacdiem_id,
          KHU_ID: this.ttdiachi_ld.khu_id,
          MOTA: "",
          PHO_ID: this.ttdiachi_ld.pho_id,
          PHONG: "",
          PHUONG_ID: this.ttdiachi_ld.phuong_id,
          QUAN_ID: this.ttdiachi_ld.quan_id,
          SONHA: this.ttdiachi_ld.sonha,
          TANG: "",
          TINH_ID: this.ttdiachi_ld.tinh_id,
          KINHDO: null,
          VIDO: null,
        };

      let dsCapNhatDanhBa = {
        dsKhachHang: dsKhachHang,
        dsThanhToan: dsThanhToan,
        dsThueBao: dsThueBao,
        dsThueBaoCon: dsThueBaoCon,
        dsThueBaoKhuVuc: dsThueBaoKhuVuc,
        dsDiaChiKH: dsDiaChiKH,
        dsDiaChiTT: dsDiaChiTT,
        dsDiaChiBC: dsDiaChiBC,
        dsDiaChiTB: dsDiaChiTB,
        dsDiaChiLD: dsDiaChiLD,
        dichVuVT_ID: this.thuebao.dichvuvt_id,
        khachHang_ID: this.khachhang.khachhang_id,
        thanhToan_ID: this.thanhtoan.thanhtoan_id,
        thueBao_ID: this.thuebao.thuebao_id,
        donViQL_ID: this.thanhtoan.donvi_id,
        donVi_ID: this.thuebao.donvi_id,
        kieuTao: 0,
        nhanVien_AM_ID: this.thuebao.nhanvien_am_id,
      };
      let dsdongbo = {
        thueBaoId: this.thuebao.thuebao_id,
        tenTB: this.thuebao.ten_tb,
        diaChi: this.thuebao.diachi_ld,
        doiTuongId: this.thuebao.doituong_id,
        telKH: this.khachhang.dien_thoai,
        gioiTinh: this.khachhang.gioi_tinh === "rdoF"
          ? 0
          : this.khachhang.gioi_tinh === "rdoM"
            ? 1
            : null,
      }
      this.loading(true);
      if (message === '') {
        try {
          var dongbo = await this.$root.context
            .post(`/web-thuno/api/thu-no/xu-ly-thue-bao/thay-doi-ttkh-visa`, dsdongbo)
          if (dongbo.error === "200")
            this.$toast.success("Cập nhật thành công visa");

        } catch (err) {
          //console.log(err.data);
          this.$toast.error(err.response.data.message_detail);
          //this.$toast.error('Lỗi tích hợp');

        }
        try {

          var capnhat = await this.$root.context
            .post(`/web-quantri/quan-ly-danh-ba/capnhat-danhba`, dsCapNhatDanhBa)
            .then((rec) => {

              if (rec.data !== null && rec.data.length > 0)
                if (rec.data[0].CODE_ERROR === "BSS-00000000") {
                  this.$toast.success("Cập nhật thành công danh bạ !");
                } else {
                  this.$toast.error(rec.data[0].CODE_MESSAGE);
                }
              else
                this.$toast.error(rec.message);

              this.loading(false);
            })
            ;
        } catch (err) {
          this.$toast.error('Có lỗi xảy ra');
          //this.$toast.error('loi');

        } finally {
          this.loading(false);
        }
      }
      else {
        this.$toast.error(message);
      }
    },
    formatDate(value) {
      return value === null ? "" : moment(value).format("DD/MM/yyyy");
    },
    ngaySinhChanged() {
      //if(this.khachhang.ngay_sinh) this.NgaySinhTB = this.NgaySinh;
      let khdn = this.tmp_dsLoaiKH.filter(x => x.LOAIKH_ID == this.khachhang.loaikh_id)[0].KHDN
      if (moment().diff(this.khachhang.ngay_sinh, 'years') < 18 && khdn == 0) {
        this.$toast.error('Khách hàng chưa đủ 18 tuổi để làm hợp đồng!');
        return false;
      } else if (moment().diff(this.thuebao.ngay_sinh, 'years') < 18 && khdn == 0) {
        this.$toast.error('Thuê bao chưa đủ 18 tuổi để làm hợp đồng!');
        return false;
      }
      return true;
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },


    getdsLoaiKH: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAI_KH`
        );
        this.tmp_dsLoaiKH = rs.data;
        this.loai_kh_tree = this.createTreeLoaiKH();
      } catch (error) { }
    },
    createTreeLoaiKH() {


      var listkieukh = [...new Set(this.tmp_dsLoaiKH.map(x => x.KIEU_KH))].map(x => ({ label: x, id: x }));

      for (var kieukh of listkieukh) {
        kieukh.children = [...new Set(this.tmp_dsLoaiKH.filter(x => x.KIEU_KH == kieukh.label).map(x => x.TEN_NHOM))].map(x => ({ label: x, id: x }));
        for (var nhomkh of kieukh.children) {
          nhomkh.children = this.tmp_dsLoaiKH.filter(x => x.KIEU_KH == kieukh.label && x.TEN_NHOM == nhomkh.label).map(x => ({ id: x.LOAIKH_ID, label: x.TEN_LOAIKH, cate: kieukh.label, subcate: nhomkh.label }));
        }
      }

      return listkieukh;

    },

    loaikhChanged(node, insid) {

      this.buttons.btnThongTinKhac = (node.cate != "Cá nhân");
      if (!this.buttons.btnThongTinKhac && this.khachhang.gioitinh == -1) this.khachhang.gioitinh = 0;
    },
    //------------end methods--------
  },
  computed: {
  },
  watch: {
    dt_checked: async function (newVal, oldVal) {
      if (newVal === false) this.dsDanToc = [];
      else {
        if (this.qt_checked === false) {
          this.$toats.error("Bạn chưa chọn quốc tịch");
          this.dt_checked = false;
        } else await this.getDsDanToc();
      }
    },
    "khachhang.quoctich_id": async function (newVal, oldVal) {
      await this.getDsDanToc();
    },
    "thuebao.dichvuvt_id": async function (newVal, oldVal) {
      this.dsLoaiHinhTB = this.tmp_dsLoaiHinhTB.filter(
        (item) => item.DICHVUVT_ID === newVal
      );
      //this.thuebao.loaitb_id=this.dsLoaiHinhTB[0].LOAITB_ID;
      this.dsMucCuocTB = this.tmp_dsMucCuocTB.filter(
        (item) => item.DICHVUVT_ID === newVal
      );
      //this.thuebao.muccuoctb_id=this.dsMucCuocTB[0].MUCUOCTB_ID;
    },
    "thuebao.loaihd_id": async function (newVal, oldVal) {
      // console.log(newVal);
      this.dsKieuLD = this.tmp_dsKieuLD.filter(
        (item) => item.LOAIHD_ID === newVal && item.SUDUNG === 1
      );
    },
    "thuebao.loaitb_id": async function (newVal, oldVal) {
      if (
        this.thuebao.dichvuvt_id === 15 ||
        this.thuebao.dichvuvt_id === 14 ||
        this.thuebao.dichvuvt_id === 13 ||
        this.thuebao.dichvuvt_id === 16
      ) {
        this.dsThuocTinhHDTB_CNTT = [];
        //console.log(newVal);
        await this.getDsThuocTinhHDTB_CNTT(newVal, this.thuebao.thuebao_id);
      } else {
        this.dsTocDo = [];
        this.dsTocDo = this.tmp_dsTocDo.filter(
          (item) => item.LOAITB_ID === newVal
        );
        this.hienthi_thongtin_danhba_con();
      }
    },

    "db_con.bras_id": async function (newVal, oldVal) {
      this.loading(true);
      this.dsDslam = this.tmp_dsDslam.filter((item) => item.BRAS_ID === newVal);
      this.loading(false);
    },

    isShowDiaChiKH: function (newVal, oldVal) {
      if (newVal === true)
        this.getChiTietDiaChi = {
          tinhThanh_id: this.ttdiachi_kh.tinh_id,
          quanHuyen_id: this.ttdiachi_kh.quan_id,
          phuongXa_id: this.ttdiachi_kh.phuong_id,
          duongPho_id: this.ttdiachi_kh.pho_id,
          ap_id: this.ttdiachi_kh.ap_id,
          khu_id: this.ttdiachi_kh.khu_id,
          dacDiem_id: this.ttdiachi_kh.dacdiem_id,
          soNha: this.ttdiachi_kh.sonha,
        };
      this.query = this.ttdiachi_kh.dia_chi;
    },
    isShowDiaChiNDD: function (newVal, oldVal) {
      if (newVal === true)
        this.getChiTietDiaChi = {
          tinhThanh_id: this.ttdiachi_kh.tinh_id,
          quanHuyen_id: this.ttdiachi_kh.quan_id,
          phuongXa_id: this.ttdiachi_kh.phuong_id,
          duongPho_id: this.ttdiachi_kh.pho_id,
          ap_id: this.ttdiachi_kh.ap_id,
          khu_id: this.ttdiachi_kh.khu_id,
          dacDiem_id: this.ttdiachi_kh.dacdiem_id,
          soNha: this.ttdiachi_kh.sonha,
        };
      this.query = this.ttdiachi_kh.dia_chi;
    },
    isShowDiaChiHK: function (newVal, oldVal) {
      if (newVal === true)
        this.getChiTietDiaChi = {
          tinhThanh_id: this.ttdiachi_kh.tinh_id,
          quanHuyen_id: this.ttdiachi_kh.quan_id,
          phuongXa_id: this.ttdiachi_kh.phuong_id,
          duongPho_id: this.ttdiachi_kh.pho_id,
          ap_id: this.ttdiachi_kh.ap_id,
          khu_id: this.ttdiachi_kh.khu_id,
          dacDiem_id: this.ttdiachi_kh.dacdiem_id,
          soNha: this.ttdiachi_kh.sonha,
        };
      this.query = this.ttdiachi_kh.dia_chi;
    },

    isShowDiaChiTT: async function (newVal, oldVal) {
      if (newVal === true)
        this.getChiTietDiaChi = {
          tinhThanh_id: this.ttdiachi_tt.tinh_id,
          quanHuyen_id: this.ttdiachi_tt.quan_id,
          phuongXa_id: this.ttdiachi_tt.phuong_id,
          duongPho_id: this.ttdiachi_tt.pho_id,
          ap_id: this.ttdiachi_tt.ap_id,
          khu_id: this.ttdiachi_tt.khu_id,
          dacDiem_id: this.ttdiachi_tt.dacdiem_id,
          soNha: this.ttdiachi_tt.sonha,
        };
      this.query = this.ttdiachi_tt.dia_chi;
    },
    isShowDiaChiBC: async function (newVal, oldVal) {
      if (newVal === true)
        this.getChiTietDiaChi = {
          tinhThanh_id: this.ttdiachi_bc.tinh_id,
          quanHuyen_id: this.ttdiachi_bc.quan_id,
          phuongXa_id: this.ttdiachi_bc.phuong_id,
          duongPho_id: this.ttdiachi_bc.pho_id,
          ap_id: this.ttdiachi_bc.ap_id,
          khu_id: this.ttdiachi_bc.khu_id,
          dacDiem_id: this.ttdiachi_bc.dacdiem_id,
          soNha: this.ttdiachi_bc.sonha,
        };
      this.query = this.ttdiachi_bc.dia_chi;
    },
    isShowDiaChiTB: async function (newVal, oldVal) {
      if (newVal === true)
        this.getChiTietDiaChi = {
          tinhThanh_id: this.ttdiachi_tb.tinh_id,
          quanHuyen_id: this.ttdiachi_tb.quan_id,
          phuongXa_id: this.ttdiachi_tb.phuong_id,
          duongPho_id: this.ttdiachi_tb.pho_id,
          ap_id: this.ttdiachi_tb.ap_id,
          khu_id: this.ttdiachi_tb.khu_id,
          dacDiem_id: this.ttdiachi_tb.dacdiem_id,
          soNha: this.ttdiachi_tb.sonha,
        };
      this.query = this.ttdiachi_tb.dia_chi;
    },
    isShowKhuyenMai: function (newVal, oldVal) {
      console.log(newVal);
    },
    isShowDiaChiLD: async function (newVal, oldVal) {
      if (newVal === true)
        this.getChiTietDiaChi = {
          tinhThanh_id: this.ttdiachi_ld.tinh_id,
          quanHuyen_id: this.ttdiachi_ld.quan_id,
          phuongXa_id: this.ttdiachi_ld.phuong_id,
          duongPho_id: this.ttdiachi_ld.pho_id,
          ap_id: this.ttdiachi_ld.ap_id,
          khu_id: this.ttdiachi_ld.khu_id,
          dacDiem_id: this.ttdiachi_ld.dacdiem_id,
          soNha: this.ttdiachi_ld.sonha,
        };
      this.query = this.ttdiachi_ld.dia_chi;
    },
  },

};


</script>
