<template src="./index.html">
</template>

<style scoped>
input[type=number] {
  text-align: right;
}

input[type=text] {
  text-align: left;
}
</style>

<script>
import moment from "moment";
import TraCuuDanhBaThueBao from './TraCuuDBThueBao';

export default {
  data: function () {
    return {
      dsThietBiTSL: [],
      dsLoaiKenh: [],
      dsVeTinh: [],
      dsHost: [],
      dsThietBiIMS: [],
      dsChuQuan: [],
      dsQuocTich: [],
      dsThoiHan: [],
      dsLoaiModem: [],
      dsTocDoKenh: [],
      dsDoiTac: [],
      pir_checked: false,
      dsLoaiNode: [],
      dsThanhToan: [],
      dsSuDungDVGT: [],
      dsKhuVuc: [],
      dsLoaiGT: [],
      dsNganhNghe: [],
      dsLoaiKhachHang: [],
      ngaylapHD: new Date(),
      isDisableNhapMoi: false,
      isDisableHuyBo: true,
      isDisableBtnGhiLai: true,
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
      dsThuocTinhHDTB_CNTT: [],
      thuebao: {
        khachhang_id: 0,
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
        donvi_id_dau: 0,
        donvi_id_cuoi: 0,
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
        tramtb_cu_id: 0,
        tramtb_id: 0,
        host_id: 0,
        host_id_cu: 0,

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
        donvi_cu_id: 0,
        vetinh_id: 0,
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
      popupComponent: null,
    }
  },

  created: function () {
    this.getDsTocDo();
    this.getDsDonViQL();
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
    this.getDsVetinh();
    this.getDsChuQuan();
    this.getDsTocDoKenh();
    this.getDsDoiTac();
    this.getDsLoaiKenh();
    this.getDsLoaiModem();
    this.getDsThoiHan();
    this.getDsThietBiTSL();
    this.getDsThietBiIMS();
    this.getDsGoiCuocDD();
    this.getDsKhuVuc();
    this.getDstramHost();
    this.onNhapMoi();
  },

  methods: {
    getDsKhuVuc: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/lay-danhmuc/KHUVUC`
        );
        this.dsKhuVuc = rs.data;
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
    getDsKhuVucThueBao: async function (pThuebaoID) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/cap-nhat-tram-thi-cong/lay-thongtin-khuvuc-thuebao/${pThuebaoID}`
        );
        if (rs.data.length > 0)
          this.thuebao.khuvuc_id = rs.data[0].KHUVUC_ID
        // this.khuvuc_tb = {
        //   khuvuc_id: rs.data[0].KHUVUC_ID,
        //   ma_kv: rs.data[0].MA_KV,
        //   ten_kv: rs.data[0].TEN_KV,
        //   loaikv_id: rs.data[0].LOAIKV_ID,
        // };
      } catch (error) { }
    },

    getDSThueBaoTheoKachHangID: async function (khachhang_id) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhba-thuebao-theo_khachhang/${khachhang_id}`
        );
        this.dsThueBao = rs.data;
        if (this.dsThueBao.length > 0) {
          this.tmp_ds = this.dsThueBao.filter((item) => item.MA_TB === this.matimkiem);
          if (this.tmp_ds.length > 0)
            await this.hienthi_thongtin_thuebao(this.tmp_ds[0]);
          else
            await this.hienthi_thongtin_thuebao(this.dsThueBao[0]);
        }
      } catch (error) { }
    },

    getDstramVT: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=5`
        );
        this.dsTramVT = rs.data;

        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },
    getDsVetinh: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=5`
        );
        this.dsVeTinh = rs.data;

        //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
      } catch (error) { }
    },

    getDstramHost: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=25`
        );
        this.dsHost = rs.data;

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
        this.dsKieuLD = rs.data;
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
        //this.$refs.cboKieuBanDo.focus();
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

    MaTBChange(...data) {
      //console.log(data);
      this.hienthi_thongtin_thuebao(data[1]);
      //this.dsThueBaoDD=this.dsThueBao.filter((item)=>item.THANHTOAN_ID===this.thanhtoan.thanhtoan_id);
    },
    hienthi_thongtin_thuebao: async function (data) {


      this.thuebao.thuebao_id = data.THUEBAO_ID;
      await this.getDsKhuVucThueBao(this.thuebao.thuebao_id);
      this.thuebao.khuvuc = this.khuvuc_tb.ten_kv;
      // this.thuebao.thuebao_id=data.THUEBAO_ID;

      this.thuebao.dichvuvt_id = data.DICHVUVT_ID;

      this.thuebao.loaitb_id = data.LOAITB_ID;
      this.hienthi_thongtin_danhba_con();
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
      this.thuebao.donvi_id_dau = data.DONVI_DAU_ID;
      this.thuebao.donvi_id_cuoi = data.DONVI_CUOI_ID;
      await this.getTramVThueBao(this.thuebao.thuebao_id);
      await this.getHostThueBao(this.thuebao.thuebao_id);


    },
    getDsKhachHang: async function (pMa,pDichVuVT) {
      try {
        this.loading(true);
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danh-ba-khach-hang-theo-ma-new?pMa=${pMa}&pDichVuVTID=${pDichVuVT}`
        );
        if (rs.data.length > 0) {
          this.thuebao.khachhang_id = rs.data[0].KHACHHANG_ID;
          await this.getDSThueBaoTheoKachHangID(this.thuebao.khachhang_id);

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
    maKH_keyPress: async function (event) {
      if (event.code == "Enter") {
        //let madg = this.khachhang.ma_gd == "" ? "0" : this.khachhang.ma_kh;
        let madg = this.thuebao.ma_tb;
        this.matimkiem = madg;
        // Object.assign(this.$data, this.initialState());
        await this.getDsKhachHang(madg,"");
      }
    },
    formatDate(value) {
      return value === null ? "" : moment(value).format("DD/MM/yyyy");
    },
    onNhapMoi: function () {
      Object.assign(this.$data, this.initialState());
      this.isDisableBtnGhiLai = false;
      this.isDisableHuyBo = false;
      this.isDisableNhapMoi = true;
    },
    onHuyBo: function () {
      Object.assign(this.$data, this.initialState());

      this.isDisableBtnGhiLai = true;
      this.isDisableHuyBo = true;
      this.isDisableNhapMoi = false;
    },
    getTramVThueBao: async function (thuebao_id) {
      try {
        let ketqua = 0
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/donvi-thuebao-theoloai-donvi?pThueBaoID=${thuebao_id}&pLoaiDV=5&pKieuDV=2`
        );
        if (rs.data.length > 0)
          ketqua = rs.data[0].DONVI_ID

        this.thuebao.tramtb_cu_id = ketqua;
        this.thuebao.tramtb_id = ketqua;

      } catch (error) { }
    },
    getHostThueBao: async function (thuebao_id) {
      try {
        let ketqua = 0
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/donvi-thuebao-theoloai-donvi?pThueBaoID=${thuebao_id}&pLoaiDV=6&pKieuDV=2`
        );
        if (rs.data.length > 0)
          ketqua = rs.data[0].DONVI_ID

        this.thuebao.host_id_cu = ketqua;
        this.thuebao.host_id = ketqua;

      } catch (error) { }
    },

    onGhiLai: async function () {
      this.loading(true);
      try {
        let _tramvt_moi_id = Number(this.thuebao.tramtb_id);
        if (_tramvt_moi_id == 0)
          _tramvt_moi_id = Number(this.thuebao.tramtb_cu_id);
        // if (this.thuebao.dichvuvt_id == 0 || this.thuebao.dichvuvt_id == 1 || this.thuebao.dichvuvt_id == 7 || this.thuebao.dichvuvt_id == 8)
        //   _tramvt_moi_id = Number(this.db_con.vetinh_id);
        let param = {
          dichvuvt_id: this.thuebao.dichvuvt_id,
          thuebao_id: this.thuebao.thuebao_id,
          ma_tb: this.thuebao.ma_tb,
          loaitb_id: this.thuebao.loaitb_id,
          donvi_id_tram_old: this.thuebao.tramtb_cu_id,
          donvi_dau_id: this.thuebao.donvi_id_dau,
          donvi_cuoi_id: this.thuebao.donvi_id_cuoi,
          tramtb_id_cu: this.thuebao.tramtb_cu_id,
          hostld_id_cu: Number(this.thuebao.host_id_cu),
          tramtb_id_moi: _tramvt_moi_id,
          hostld_id_moi: Number(this.thuebao.host_id),
          vetinh_id_moi: Number(this.db_con.vetinh_id),
          khuvuc_id: Number(this.thuebao.khuvuc_id),
          donvi_ql: Number(this.thuebao.donvi_id),
          donvi_ql_cuoi: Number(this.db_con.donvi_cuoi_id)
        }

        var capnhat = await this.$root.context.post(`/web-quantri/cap-nhat-tram-thi-cong/capnhat-tram-thicong`, param)
          .then((rec) => {
            if (rec.data[0].CODE_ERROR === 1) {
              this.$toast.success("Cập nhật thành công!");
            } else {
              this.$toast.error(rec.data[0].CODE_MESAGE);
            }
          });
      } catch (err) {
        this.$toast.error(err.message);
      } finally {
        this.loading(false);
      }
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

      let ds = this.dsTBMoRong[0];
      this.db_con.vetinh_id = ds.TRAMTB_ID;
      this.db_con.vetinh_cu_id = ds.TRAMTB_ID;
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
      if (this.thuebao.dichvuvt_id === 9)
        this.hienthi_thongtin_danhba_con_tsl();

      this.db_con.mc_tbgp_id = ds.MC_TBGP_ID;
      this.db_con.toado = ds.TOADO;
      this.loading(false);
    },
    initialState: function () {
      return {
        dsThueBao: [],
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
          tramtb_id: 0,
          host_id: 0,
        },

        db_con: {
          vetinh_id: 0,
          host_id: 0,
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
    onShowSearchAccount() {
      // this.$router.push({ name: "account" });
      this.popupComponent = TraCuuDanhBaThueBao;
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    async searchAccountClosed(val) {
      this.ClosePopup("popupComponents");
      if (val) {
        this.thuebao.ma_tb = val.ma_tb;
        this.thuebao.dichvuvt_id=val.dichvuvt_id;        
        await this.getDsKhachHang(this.thuebao.ma_tb,this.thuebao.dichvuvt_id);
      }

    },
  },
  watch: {
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
  }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

#table-scroll {
  height: 410px;
  overflow: auto;
  margin-top: 20px;
}
</style>
