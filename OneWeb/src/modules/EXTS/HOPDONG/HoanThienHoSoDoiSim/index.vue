<template src="./index.html"></template>
<script>
import DateTimeLib from "../../../../utils/DateTimeLib";
import Enum from "../DongBoVisaVasc/enum";
import QRcode from "./Modal";
export default {
  components: {QRcode},
  watch: {
    "dataSelected.dichvu_id": async function () {
      await this.dichVu_onChange();
    },
    "dataSelected.loaihinh_id": async function () {
      await this.cbbLoaiHinh_Change();
    },
    'dataSelected.kieuld_id': async function(){
      this.cbbKieuLD_Change()
    }
  },
  data() {
    return {
      dsThueBao: [],
      dsHG: [],
      dsDVVT: [],
      dsKieuLD: [],
      dsLoaiHinh: [],
      checkEsim: [],
      dataSelected: {
        ma_gd: "",
        ngay_yc: DateTimeLib.toDateDisplay(new Date()),
        dichvu_id: -1,
        ngay_lap_hd: DateTimeLib.toDateDisplay(new Date()),
        kieuld_id: -1,
        loaihinh_id: -1,
        ma_kh: "",
        ten_kh: "",
        diachi_kh: "",
        ten_tbao: "",
        diachi_tbao: "",
        doituong_id: -1,
        ghichu: "",
        loaitb_id: -1,
        ma_tinh: "",
        ma_vung: "",
        userGP: "",
        user_neo: null,
        huonggiao_id: -1,
        ltb_adsl_id: -1,
        phuongld_id: -1,
        tocdo_id: -1,
        phold_id: -1,
        apld_id: -1,
        khuld_id: -1,
        trangbi_id: -1,
        ma_tbao: "",
        ma_hd: "",
        tien: "",
        vat: "",
        sim_moi: "",
        sim_cu: "",
        hdtb_id: -1,
        hdkh_id: -1,
        khachhang_id: -1,
        donvi_id: -1,
        phieu_id:-1,
        thuebao_id:-1
      },
      date: {
        dateVal: Date,
        dateFormat: "dd/MM/yyyy",
      },
      currentItem: {},
      isDisable: true,
      isDisableNhapMoi: false,
      isDisableCapNhat: false,
      isDisableHoanCong: true,
      isDisableInQRCode: true,
      isDisableKichHoat: false,
      ipCurrent:"",
      dongbo_gphone:false,
      open_sync_vina:false
    };
  },
  created: async function () {
   await this.initForm();
   this.dongbo_gphone =  this.KIEMTRA_THAM_SO_DONGBO('DONGBO_GPHONE');
   this.open_sync_vina =  this.KIEMTRA_THAM_SO_DONGBO('OPEN_SYNC_VINA');
  },
  methods: {
    initForm: async function () {
      try {
        this.$root.showLoading(true);
         await this.getIp()
        await this.lay_ds_QuyTrinh(Enum.LoaiHopDong.THAY_DOI_SO_SIM.toString(),
        Enum.DichVuVienThong.DI_DONG.toString(),Enum.TrangThaiHD.DA_THI_CONG_XONG.toString());
        if (this.dsQuyTrinh.length > 0) {
          await this.lay_ds_HuongGiao();
        } else {
          this.$root.toastError(
            "Chưa có quy trình đổi sim di động! Liên hệ Admin để xử lý"
          );
         // return;
        }
        await this.form_Load();

      } catch (error) {
        return null;
      } finally {
        this.$root.showLoading(false);
      }
    },
    form_Load: async function () {
      await this.lay_ds_dichvu_vt();
      await this.lay_thongtin_nguoidung();
      await this.HienThiDanhSach();
    },
    lay_ds_QuyTrinh: async function (dsloaihd_id,dsdichvuvt_id,dstthd_id) {
      try {
        let rs = await this.$root.context.post(
          "/web-hopdong/danhmuc/ds_quytrinh",
          {
            dsdichvuvt_id:dsdichvuvt_id,
            dsloaihd_id:dsloaihd_id,
            dstthd_id:dstthd_id,
            kieu: 0,
          }
        );
        this.dsQuyTrinh = rs.data;
        if (rs.data != null)
          this.dataSelected.quytrinh_id = rs.data[0].quytrinh_id;
      } catch (error) {
      } finally {
      }
    },
    lay_ds_HuongGiao: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/danhmuc/ds_huonggiao/" +
            this.dataSelected.quytrinh_id +
            "/" +
            Enum.TrangThaiHD.DA_THI_CONG_XONG
        );
        this.dsHG = rs.data;
        if (rs.data != null)
          this.dataSelected.huonggiao_id = rs.data[0].huonggiao_id;
      } catch (error) {
      } finally {
      }
    },
    HienThiDanhSach: async function () {
      await this.LAY_DS_HDTB_DD_HOANTHIEN();
    },
    LAY_DS_HDTB_DD_HOANTHIEN: async function () {
      try {
        this.$root.showLoading(true);
        let ma_gd = this.dataSelected.ma_gd;
        let ma_tb = this.dataSelected.ma_tbao;
        const params = new URLSearchParams();
         params.append('huonggiao_id', this.dataSelected.huonggiao_id);
         params.append('tthd_id', Enum.TrangThaiHD.DA_THI_CONG_XONG);
         params.append('ma_gd', ma_gd);
         params.append('ma_tb',ma_tb);
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-doisim/lay_hdtb_dd_hoanthien",params
        );

        this.dsThueBao = rs.data;
        console.log(rs)
        if(rs.data== null || rs.data.length == 0)
         this.Clear();
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    lay_thongtin_nguoidung: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung"
        );
        if (rs.data != null) {
          this.dataSelected.ma_tinh = rs.data[0].tentat;
          this.dataSelected.ma_vung = rs.data[0].mavung_cd;
          this.dataSelected.userGP = rs.data[0].user_gphone;
          this.dataSelected.user_neo = rs.data[0].user_neo;
        }
      } catch (error) {
        return null;
      } finally {
      }
    },
    lay_thamso_macdinh: async function (ma_ts) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_thamso_macdinh",
          {
            ma_ts: ma_ts,
          }
        );
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },

    gridthuebao_selectedRowChanged: async function (item) {
      console.log('item',item)
      if (item != null) {
        Object.assign(this.currentItem, item);
        this.dataSelected.hdtb_id = item.hdtb_id;
        await this.HienThiTTHopDongKH(item);
        await this.HienThiTTHopDongTB(item);
        if (item.nhanvien_th_id != null) {
          this.isDisableCapNhat = true;
          this.isDisableKichHoat = false;
        } else {
          this.isDisableCapNhat = false;
          this.isDisableKichHoat = true;
        }
        if (item.status == "5") {
          this.isDisableKichHoat = true;
          this.isDisableHoanCong = false;
        } else {
          this.isDisableKichHoat = false;
          this.isDisableHoanCong = true;
        }
      }
    },
    LAY_DS_HOPDONG_TB_THEO_HDKH_ID: async function (hdkh_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hopdong_tb_theo_id/" +hdkh_id );
          return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    HienThiTTHopDongKH: async function (item) {
      try {
        if (item != null) {
          this.dataSelected.hdkh_id = parseInt(item.hdkh_id);

          this.dataSelected.ma_gd = item.ma_gd;
          this.dataSelected.ma_hd = item.ma_hd;
          this.dataSelected.ma_kh = item.ma_kh;
          if (item.khachhang_id != null)
            this.dataSelected.khachhang_id = item.khachhang_id;
          this.dataSelected.ten_kh = this.chuanHoaTen(item.ten_kh);
          this.dataSelected.diachi_kh = this.chuanHoaTen(item.diachi_kh);

          this.dataSelected.ngay_lap_hd = DateTimeLib.toDateDisplay(
            new Date(item.ngaylap_hd)
          );
          if (item.ngay_yc != null)
            this.dataSelected.ngay_yc = DateTimeLib.toDateDisplay(
              new Date(item.ngay_yc)
            );
          this.dataSelected.ghichu = item.ghichu;
        } else {
        }
      } catch (exp) {
        console.log(exp);      }
    },
    HienThiTTHopDongTB: async function (item) {
      try {
        if (item != null) {
          this.dataSelected.hdtb_id = parseInt(item.hdtb_id);
          // this.dataSelected.hdtb_id = parseInt(item.hdtb_id)
          if (item.thuebao_id != null)
            this.dataSelected.thuebao_id = item.thuebao_id;
          this.dataSelected.ma_tbao = this.chuanHoaTen(item.ma_tb);
          this.dataSelected.ten_tbao = this.chuanHoaTen(item.ten_tb);
          this.dataSelected.diachi_tbao = this.chuanHoaTen(item.diachi_tb);

          this.dataSelected.dichvu_id = item.dichvuvt_id;
          this.dataSelected.loaihinh_id = item.loaitb_id;

          if (this.dataSelected.dichvu_id == 2)
            this.dataSelected.kieuld_id = item.kieuld_id;
          this.dataSelected.doituong_id = item.doituong_id;
          let vma_vung = "";
          if (this.dataSelected.dichvu_id == Enum.DichVuVienThong.GPHONE)
            vma_vung = this.dataSelected.ma_vung;
          else vma_vung = "84";
          if (this.dataSelected.dichvu_id == Enum.DichVuVienThong.DI_DONG)
            if (this.dataSelected.user_neo == null) {
              this.$root.toastError(
                "Người dùng chưa được gán user kết nối hệ thống vinaphone. Liên hệ Admin để gán quyền."
              );
              return;
            } else {
              if (
                this.dataSelected.kieuld_id == Enum.KieuLapDat.DOISIM_TRATRUOC
              ) {
                this.dataSelected.sim_cu = item.simcu;
              } else {
                 let ma_tbao = vma_vung + item.ma_tb
                 let dtThongTinTb = await this.ts_tracuu_laytt_tb(ma_tbao)
                  if (dtThongTinTb[0].so_msin != undefined && dtThongTinTb[0].so_msin.toString().length > 0)
                      this.dataSelected.sim_cu = dtThongTinTb[0].so_msin.toString();
              }
            }

          this.dataSelected.sim_moi = item.simcard;

          if (item.donvi_id != null) this.dataSelected.donvi_id = item.donvi_id;
          await this.HienThiCTTIEN_HD(
            await this.LAY_TIEN_HD_KM_THEO_HDTB(item.hdtb_id)
          );
        }
      } catch (error) {
        console.log(error);
      }
    },
    ts_tracuu_laytt_tb: async function (ma_tb) {
    try {
        let rs = await this.$root.context.post(
          "ccbs/tracuu/ts_tracuu_laytt_tb",
         {
            so_tb: ma_tb
          }
        );
        return rs.data;
      } catch (error) {
        return "";
      } finally {
      }
    },
    HienThiCTTIEN_HD: async function (data) {
      for (let i = 0; i < 2; i++) {
        if (i == 0) {
          this.dataSelected.tien = data[i].tien;
          this.dataSelected.vat = data[i].vat;
        }
        if (i == 1) {
        }
      }
    },
    LAY_TIEN_HD_KM_THEO_HDTB: async function (hdtb_id) {
       try {
        let rs = await this.$root.context.get(
          "/web-hopdong/hoanthien-hs-doisim/lay_tien_hd_km_theo_hdtb/"+hdtb_id);
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    gridthuebao_PageChanged: function (args) {},
    LayThongTin: async function () {
      await this.HienThiDanhSach();
    },
    lay_ds_dichvu_vt: async function () {
      let dk = await this.lay_dk_bang();
      console.log('dk',dk)
      let dsDichvu_vt = "2,10";
      if(dk != null){
      let lst = dk.split(",");
      dk = lst.filter((x) => x == "2" || x == "10").join(",");
      if (dk != "") dsDichvu_vt = dk;
      }
      console.log('dsDichvu_vt',dsDichvu_vt)
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_dichvu_vt",
          {
            dichvu_vt_id: dsDichvu_vt,
          }
        );
        this.dsDVVT = rs.data;
        if (rs.data != null)
          this.dataSelected.dichvu_id = rs.data[0].dichvuvt_id;
      } catch (error) {
      } finally {
      }
    },
    lay_dk_bang: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang_dichvuvt");
        return rs.data;
      } catch (error) {
        return "";
      } finally {
      }
    },
    get_ds_Kieu_ld: async function (loaihd_id, loaitb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_kieu_ld",
          { loai_hd_id: loaihd_id, loai_tbi_id: loaitb_id }
        );
        this.dsKieuLD = rs.data;
        if (rs.data != null) {
          this.dataSelected.kieuld_id = rs.data[0].kieuld_id;
        }
      } catch (error) {
      } finally {
      }
    },
    cbbLoaiHinh_Change: async function () {
      await this.get_ds_Kieu_ld(Enum.LoaiHopDong.THAY_DOI_SO_SIM,
                             this.dataSelected.loaihinh_id);
    },
    get_ds_LoaiHinh: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_loaihinh/" +
            this.dataSelected.dichvu_id
        );
        this.dsLoaiHinh = rs.data;
        if (rs.data != null) {
          this.dataSelected.loaihinh_id = rs.data[0].loaitb_id;
        }
      } catch (error) {
      } finally {
      }
    },
    cbbKieuLD_Change: async function(){
            if (this.dataSelected.kieuld_id == Enum.KieuLapDat.DOISIM_TRATRUOC)
            {
                this.dataSelected.doituong_id = Enum.DoiTuong.TU_NHAN;
                this.dataSelected.donvi_id = this.$root.context.user.getProperty('donvi_dl_id');
            }
            if (this.dataSelected.dichvu_id == "2")
            {
              await this.lay_ds_QuyTrinh(Enum.LoaiHopDong.THAY_DOI_SO_SIM,Enum.DichVuVienThong.DI_DONG,Enum.TrangThaiHD.DA_THI_CONG_XONG);
                if (this.dsQuyTrinh.length > 0) {
                    await this.lay_ds_HuongGiao();
                }
            }
            else
            {
              await this.lay_ds_QuyTrinh(Enum.LoaiHopDong.THAY_DOI_SO_SIM, Enum.DichVuVienThong.GPHONE,Enum.TrangThaiHD.DA_THI_CONG_XONG);
                if (this.dsQuyTrinh.length > 0) {
                    await this.lay_ds_HuongGiao();
                }
            }

            await this.HienThiDanhSach();
            await this.LayTienTheoKhoanMuc();
    },
    dichVu_onChange: async function () {
      await this.get_ds_LoaiHinh();
      await this.cbbKieuLD_Change();
    },
    Clear() {
     // Object.assign(this.$data, this.$options.data.call(this));
    this.dataSelected.ma_gd = '';
    this.dataSelected.ma_hd = '';
    this.dataSelected.ma_tbao = '';
    this.dataSelected.sim_moi = '';
    this.dataSelected.sim_cu = '';
    this.dataSelected.ten_tbao = '';
    this.dataSelected.diachi_tbao = '';
    this.dataSelected.tien = '';
    this.dataSelected.vat = '';
    this.dataSelected.ma_kh = '';
    this.dataSelected.ten_kh = '';
    this.dataSelected.diachi_kh = '';
    this.dataSelected.ghichu = '';
    },
    checkMapId: async function (map_id, no) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/check_map_id",
          {
            map_id: map_id,
            no: no,
          }
        );
        if (rs.data != null) {
          return rs.data[0].id;
        }
      } catch (error) {
        return 0;
      } finally {
      }
    },
    chuanHoaTen: function (ten) {
      if (ten != null) {
        let convertToArray = ten.toLowerCase().split(" ");
        let result = convertToArray.map(function (val) {
          return val.replace(val.charAt(0), val.charAt(0).toUpperCase());
        });
        return result.join(" ");
      } else return "";
    },
    ChuanHoa_XML: function (str) {
      let result = "";
      result = str.replace("&", "&amp");
      result = result.replace("<", "&lt");
      result = result.replace(">", "&gt");
      result = result.replace("'", "&apos");
      result = result.replace(String.fromCharCode(34), "&quot");
      return result;
    },
    LayTienTheoKhoanMuc: async function () {
      try {
        //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 14/09/2010
        if (this.dataSelected.dichvu_id != Enum.DichVuVienThong.ADSL) {
          //kh_cd = 0;
          this.dataSelected.ltb_adsl_id = 1;
        }
        let ds = [];
        ds = await this.LAY_TIEN_KHOANMUC_LD(
          Enum.LoaiHopDong.THAY_DOI_SO_SIM,
          DateTimeLib.toDateDisplay(new Date()),
          this.dataSelected.dichvu_id,
          this.dataSelected.loaihinh_id,
          this.dataSelected.kieuld_id,
          this.dataSelected.phuongld_id,
          this.dataSelected.tocdo_id,
          0,
          this.dataSelected.ltb_adsl_id,
          0,
          0,
          0,
          0,
          0,
          this.dataSelected.phold_id,
          this.dataSelected.apld_id,
          this.dataSelected.khuld_id,
          this.dataSelected.doituong_id,
          this.dataSelected.trangbi_id
        );
        if (ds.length > 0) {
          await this.HienThiTienLapDat(ds);
        }
      } catch (ex) {
        console.log(ex)
        this.$root.toastError("Có lỗi xảy ra trong quá trình thực hiện");
      }
    },

    LAY_TIEN_KHOANMUC_LD: async function (
      loaihd_id,
      ngay_yc,
      dichvuvt_id,
      loaitb_id,
      kieuld_id,
      phuongld_id,
      tocdo_id,
      muccuoc_id,
      ltb_adsl_id,
      ltb_adsl_moi_id,
      ltb_adsl_cu_id,
      tocdo_moi_id,
      tocdo_cu_id,
      kh_cd,
      phold_id,
      apld_id,
      khuld_id,
      doituong_id,
      trangbi_id
    ) {
      try {
        let rs = await this.$root.context.post(
          "/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld",
          {
            loaihd_id: loaihd_id,
            ngay_yc: ngay_yc,
            dichvuvt_id: dichvuvt_id,
            loaitb_id: loaitb_id,
            kieuld_id: kieuld_id,
            phuongld_id: phuongld_id,
            tocdo_id: tocdo_id,
            muccuoc_id: muccuoc_id,
            ltb_adsl_id: ltb_adsl_id,
            ltb_adsl_moi_id: ltb_adsl_moi_id,
            ltb_adsl_cu_id: ltb_adsl_cu_id,
            tocdo_moi_id: tocdo_moi_id,
            tocdo_cu_id: tocdo_cu_id,
            kh_cd: kh_cd,
            phold_id: phold_id,
            apld_id: apld_id,
            khuld_id: khuld_id,
            doituong_id: doituong_id,
            trangbi_id: trangbi_id,
          }
        );
        return rs.data;
      } catch (e) {
        return null;
      }
    },
    HienThiTienLapDat: async function (ds) {
      let kt_cd = true;
      let kt_lm = true;
      if (
        this.dataSelected.ma_tbao != "" ||
        this.dataSelected.ma_gd != ""   ||
        this.dataSelected.ma_hd != ""
      )
        if (ds.length > 0) {
          for (let i = 0; i < ds.length; i++) {
            if (
              ds[i]["khoanmuctt_id"] == Enum.KHOANMUC_TT.KMTT_CAIDAT) {
              //txtTienCD.Text = Convert.ToDecimal(ds.Tables[0].Rows[i]["tien"].ToString()).ToString(Format.FM_TIEN_VND);
              //txtVatcaidat.Text = Convert.ToDecimal(ds.Tables[0].Rows[i]["vat"].ToString()).ToString(Format.FM_TIEN_VND);
              kt_cd = false;
            } else if (
              ds[i]["khoanmuctt_id"] ==Enum.KHOANMUC_TT.KMTT_DOISIM) {
              this.dataSelected.tien = parseInt(ds[0]["tien"].toString()); //.toString(Format.FM_TIEN_VND);
              this.dataSelected.vat = parseInt(ds[0]["vat"].toString()); //.toString(Format.FM_TIEN_VND);
              kt_lm = false;
            }
          }
          if (kt_cd == true) {
          }
          if (kt_lm == true) {
            this.dataSelected.tien = "0";
            this.dataSelected.vat = "0";
          }
        } else {
          this.dataSelected.tien = "0";
          this.dataSelected.vat = "0";
          this.$root.toastError("Thông tin giá chưa được cập nhật");
        }
    },
    CapNhatThongTin: async function () {
      try {
        this.$root.showLoading(true);
        if (await this.CapNhat()) {
          await this.HienThiDanhSach();
          this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
        }
      } catch (ex) {
        this.$root.toastError("Có lỗi xảy ra trong quá trình cập nhật!");
        console.log(ex)
      } finally {
        this.$root.showLoading(false);
      }
    },
    CapNhat: async function () {
      let kt = true;
      try {
        this.dataSelected.phieu_id = this.currentItem.phieu_id;
        await this.Update_tt_giaoviec(
          this.dataSelected.hdtb_id,
          this.dataSelected.phieu_id
        );
      } catch (ex) {
        this.$root.toastError("Có lỗi xảy ra trong quá trình cập nhật!");
        console.log(ex)
        kt = false;
      }
      return kt;
    },
    Update_tt_giaoviec: async function (hdtb_id, phieu_id) {
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/hoanthien-hs-doisim/update_tt_giaoviec",{hdtb_id:hdtb_id,phieu_id:phieu_id});
        return true;
      } catch (error) {
        return false;
      } finally {
      }

    },
    tt_ts_doi_sim: async function (ma_tb, soSim_moi, ma_tinh) {
      try {
        let rs = await this.$root.context.post(
      "web-hopdong/hopdong/fn_didong",
        {
          vtype: 4,
          vhdtb_id: this.dataSelected.hdtb_id,
          vinput:
            {
            ghichu: "",
            imsi: soSim_moi,
          // ma_tinh: ma_tinh,
            so_tb: ma_tb,
          // userid: this.dataSelected.user_neo,
          // userip: this.ipCurrent
            }
          }
        );
        return rs.error_code == "BSS-00000000"  ? "OK": rs.message;
      } catch (error) {
        return "Có lỗi xảy ra";
      } finally {
      }
    },
    gphone_doisim_tb: async function (ma_tb, soSim_moi, ma_tinh) {
      try {
        let rs = await this.$root.context.post(
      "ccbs/gphone/gphone_doisim_tb",
        {
        ghichu: "",
        imsi: soSim_moi,
        so_tb: ma_tb,
        }
        );
        return  rs.error_code == "BSS-00000000" ? "OK": rs.message;
      } catch (error) {
        return "Có lỗi xảy ra";
      } finally {
      }
    },
    tt_ts_get_tb_esim: async function (matinh, sosimmoi, ma_tb) {
      try {
        let rs = await this.$root.context.post(
          "ccbs/executor/tt_ts_get_tb_esim",
        {
         so_msin_moi: sosimmoi,
          ma_tinh: matinh,
          so_tb: ma_tb
         }
        );
        return rs.data
      } catch (error) {
        return null;
      } finally {
      }
    },
    KICH_HOAT: async function () {
      try {
        this.$root.showLoading(true);
        const regexSimMoi = /^\d$/;
        if (regexSimMoi.test(this.dataSelected.sim_moi)) {
          this.$root.toastError(
            "Số sim mới phải là số và không chứa dấu cách!!"
          );
          return;
        }
        if (this.currentItem.dichvuvt_id == Enum.DichVuVienThong.GPHONE) {
          if (this.dongbo_gphone){
            if (this.dataSelected.userGP == "-1") {
              this.$root.toastError(
                "Người dùng chưa được gán user kết nối hệ thống gphone. Liên hệ Admin để gán quyền."
              );
              return;
            }

            this.$confirm(`Xác nhận thực hiện đổi sim?`, "Đổi sim", {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            })
              .then(async () => {
                let result = "";
                if (this.currentItem.dichvuvt_id == 10)
                  result = await this.gphone_doisim_tb(
                    this.dataSelected.ma_vung + this.dataSelected.ma_tbao,
                    this.dataSelected.sim_moi,
                    this.dataSelected.ma_tinh
                  );
                if (result == "OK") {
                  await this.CAPNHAT_NGAYKH_STATUS_V2(
                    this.dataSelected.hdtb_id
                  );
                  await this.CAPNHAT_NGAYKH_NGAY_HT(this.dataSelected.hdtb_id);
                  this.$root.toastSuccess("Đổi sim thành công", "Thành công");
                  await this.HienThiDanhSach();
                } else {
                  this.$root.toastError(result);
                  this.$root.showLoading(false);
                  return;
                }
              })
              .catch((e) => {
                console.log(e);
                this.$root.showLoading(false);
              });
          }
        } else if (this.currentItem.dichvuvt_id == Enum.DichVuVienThong.DI_DONG) {
          if (this.open_sync_vina) {
            if (this.dataSelected.user_neo == "") {
              this.$root.toastError(
                "Người dùng chưa được gán user kết nối hệ thống vinaphone. Liên hệ Admin để gán quyền."
              );
              return;
            }
            this.$confirm(`Xác nhận thực hiện đổi sim?`, "Đổi sim", {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            })
              .then(async () => {
                if (this.dataSelected.kieuld_id != Enum.KieuLapDat.DOISIM_TRATRUOC) {
                  // doi sim chinh
                  let result = "";
                  let ma_vung = "84";
                  this.checkEsim = await this.tt_ts_get_tb_esim(
                    this.dataSelected.ma_tinh,
                    this.dataSelected.sim_moi,
                    ma_vung + this.dataSelected.ma_tbao
                  );

                  // HTH 21/03/2021 Check thêm 1 lần sim mới trước khi đổi sim di động
                  if (this.checkEsim[0].kqua_chk == 1) {
                    this.$root.toastError(
                      "Số sim mới đã được sử dụng cho thuê bao: " +
                        this.checkEsim[0].ttin_add
                    );
                    return;
                  }
                  // HTH 21/03/2021
                  if (this.checkEsim[0].esim == 0)
                    result = await this.tt_ts_doi_sim_ccbs(
                      "84" + this.dataSelected.ma_tbao,
                      this.dataSelected.sim_moi,
                      this.dataSelected.ma_tinh,
                      this.dataSelected.user_neo,
                      "Đổi sim từ DHSX - " + this.dataSelected.ghichu
                    );
                  else
                    result = await this.tt_ts_doi_sim_ccbs(
                      "84" + this.dataSelected.ma_tbao,
                      this.dataSelected.sim_moi,
                      this.dataSelected.ma_tinh,
                      this.dataSelected.user_neo,
                      "Đổi sim từ DHSX - " + this.dataSelected.ghichu
                    );
                  if (result == "OK") {
                    await this.CAPNHAT_NGAYKH_STATUS_V2(
                      this.dataSelected.hdtb_id
                    );
                    await this.CAPNHAT_NGAYKH_NGAY_HT(
                      this.dataSelected.hdtb_id
                    );

                    // Cập nhật again - hàm trên hay đơ:
                    await this.CAPNHAT_STATUS_HDTB(
                      this.dataSelected.hdkh_id,
                      Enum.TRANGTHAI_DONGBO.DONGBO_CM
                    );

                    this.$root.toastSuccess("Đổi sim thành công!");
                    await this.HienThiDanhSach();
                    this.isDisableKichHoat = true;
                    this.isDisableHoanCong = false;
                    this.isDisableInQRCode = false;
                  } else {
                    // 15/05/2020
                    if (
                      result != null &&
                      result.includes("42000") &&
                      result.toLowerCase() !=
                        "".includes("subscriber not found")
                    ) {
                      this.$root.toastError(
                        "Thuê bao đã hủy trên hệ thống backend. Liên hệ ban KTNV để xử lý!"
                      );
                      this.$root.showLoading(false);
                      return;
                    } else {
                      this.$root.toastError(result);
                      return;
                    }
                    // end 15/05/2020
                  }
                } // đổi sim phụ
                else {
                  try {
                    let sotbphu = this.currentItem.sonmay_phu;
                    var ttsim = await this.multisim_doisim_ktsimmoi(
                      this.dataSelected.sim_moi,
                      this.$root.context.user.getProperty("tentat_ccbs"),
                      sotbphu
                    );
                    if (ttsim != null) {
                      if (ttsim.length == 0 || ttsim[0].sim_hop_le != "0") {
                        this.$root.toastError(
                          "Sim " + this.dataSelected.sim_moi + " không hợp lệ"
                        );
                        return;
                      }

                      let esim = ttsim[0].esim.toString();

                      var result = await this.multisim_doisim(
                        this.dataSelected.sim_moi,
                        this.$root.context.user.getProperty("tentat_ccbs"),
                        sotbphu,
                        this.dataSelected.user_neo,
                        ""
                      );
                      if (result == "OK") {
                        let final = await this.multisim_doisim_capnhat(
                          this.dataSelected.sim_moi,
                          this.$root.context.user.getProperty("tentat_ccbs"),
                          sotbphu,
                          this.dataSelected.user_neo,
                          esim
                        );
                        if (final == "OK") {
                          await this.CAPNHAT_NGAYKH_STATUS_V2(
                            this.dataSelected.hdtb_id
                          );
                          await this.CAPNHAT_NGAYKH_NGAY_HT(
                            this.dataSelected.hdtb_id
                          );

                          this.$root.toastSuccess("Đổi sim thành công!");
                          await this.HienThiDanhSach();
                          this.isDisableKichHoat = true;
                          this.isDisableHoanCong = false;
                          this.isDisableInQRCode = false;
                        } else {
                          this.$root.toastError(
                            "Có lỗi đổi sim trên ccbs: " + final
                          );
                        }
                      } else {
                        this.$root.toastError(
                          "Có lỗi đổi sim trên ccbs: " + result
                        );
                      }
                    }
                  } catch (ex) {
                    this.$root.toastError("Có lỗi xảy ra trong quá trình đổi sim! ");
                  }
                }
              })
              .catch((e) => { this.$root.showLoading(false);});
          }
        } else {
          this.$root.toastError("Chỉ kích hoạt đối với gphone và di động!");
          return;
        }
      } catch (ex) {
        this.$root.toastError("Có lỗi xảy ra trong quá trình kích hoạt!");
         console.log(ex)
      } finally {
        this.$root.showLoading(false);
      }
    },
    multisim_doisim: async function (sosimmoi, matinh, sotb, userneo, ghichu) {
     try {
        let rs = await this.$root.context.post(
          "ccbs/executor/multisim_doisim",
        {
         so_sim_moi: sosimmoi,
         ma_tinh: matinh,
          so_tb: sotb,
          ghi_chu:ghichu,
          userid: userneo
         }
        );
        return rs.data
      } catch (error) {
        return null;
      } finally {
      }
    },
    multisim_doisim_capnhat: async function (sosimmoi,matinh,sotb,userneo,esim) {
      try {
        let rs = await this.$root.context.post(
          "ccbs/executor/multisim_doisim_capnhat",
        {
         so_sim_moi: sosimmoi,
         ma_tinh: matinh,
          so_tb: sotb,
          userid: userneo,
          esim: esim
         }
        );
        return rs.data
      } catch (error) {
        return null;
      } finally {
      }
    },
    multisim_doisim_ktsimmoi: async function (sosimmoi, matinh, sotbSimphu) {
      try {
        let rs = await this.$root.context.post(
          "ccbs/executor/multisim_doisim_ktsimmoi",
        {
         so_sim_moi: sosimmoi,
         ma_tinh: matinh,
          so_tb: sotbSimphu
         }
        );
        return rs.data
      } catch (error) {
        return null;
      } finally {
      }
    },
    tt_ts_doi_sim_ccbs: async function ( ma_tb,soSim_moi,ma_tinh,userid,ghi_chu) {
      try {
        let rs = await this.$root.context.post(
          "web-hopdong/hopdong/fn_didong",
        {
          vtype: 4,
          vhdtb_id: this.dataSelected.hdtb_id,
          vinput:
            {
            ghichu: "",
            imsi: soSim_moi,
          // ma_tinh: ma_tinh,
            so_tb: ma_tb,
          // userid: this.dataSelected.user_neo,
          // userip: this.ipCurrent
            }
          }
        );
        return  rs.error_code == "BSS-00000000"  ? "OK": rs.message;

      } catch (error) {
        return "Có lỗi xảy ra";
      } finally {
      }
    },
    CAPNHAT_NGAYKH_STATUS_V2: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/tiepnhanhopdong/capnhat_ngaykh_status_v2",
          {
            hdtb_id: hdtb_id,
            ngay_kh: DateTimeLib.toDateDisplayWithFormat(
              new Date(),
              "DD/MM/yyyy HH:mm:ss"
            ),
            status: Enum.TRANGTHAI_DONGBO.DONGBO_CM,
          }
        );
        return true;
      } catch (error) {
        return false;
      } finally {
      }
    },
    CAPNHAT_NGAYKH_NGAY_HT: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/tiepnhanhopdong/capnhat_ngaykh_ngay_ht",
          {
            hdtb_id: hdtb_id,
            ngay_kh: DateTimeLib.toDateDisplayWithFormat(
              new Date(),
              "DD/MM/yyyy HH:mm:ss"
            ),
            ngay_ht: DateTimeLib.toDateDisplayWithFormat(
              new Date(),
              "DD/MM/yyyy HH:mm:ss"
            ),
          }
        );
      } catch (error) {
      } finally {
      }
    },
    CAPNHAT_STATUS_HDTB: async function (hdtb_id, status) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/capnhat_trangthai_hdtb",
          {
            hdtb_id: hdtb_id,
            status: status,
          }
        );
        return true;
      } catch (error) {
        return false;
      } finally {
      }
    },
    IN_QRCODE: async function () {
      try {
        if (this.dataSelected.kieuld_id != Enum.KieuLapDat.DOISIM_TRATRUOC) {
          if (this.checkEsim[0].esim == 1) {
            let soqr_code = "84" + this.dataSelected.ma_tbao;
             this.$refs.qr_code.setData(soqr_code);
             this.$root.$bvModal.show("popupTCTTTB");
            this.isDisableHoanCong = false;
          } else {
            this.$root.toastError("Số sim thực hiện không phải là Esim !");
            return;
          }
        } else {
          let sotbphu = this.currentItem.sonmay_phu;
          let soqr_code = sotbphu;
            this.$refs.qr_code.setData(soqr_code);
             this.$root.$bvModal.show("popupTCTTTB");
        }
      } catch (exp) {
        console.log(e)
        this.$root.toastError("Không thể thực hiện in QRCode ! ");
      } finally {
      }
    },
    HOAN_CONG: async function () {
      try {
        this.$root.showLoading(true);
        await this.CapNhat_TT_Phieu_HTHS(this.dataSelected.hdtb_id); // HTH
        if (this.dataSelected.kieuld_id == Enum.KieuLapDat.DOISIM_TRATRUOC) {
          await this.capnhat_tthd_by_hdtb(this.dataSelected.hdtb_id);
        } else {
          await this.HOANTHIEN_HS_THAYDOI_SOSIM(this.dataSelected.hdtb_id);
        }
        this.$root.toastSuccess("Hoàn thiện hồ sơ thành công!");
        this.Clear();
        await this.HienThiDanhSach();
        this.isDisableHoanCong = true;
      } catch (exp) {
        this.$root.toastError("Có lỗi xảy ra trong quá trình hoàn công!");
         console.log(ex)
      } finally {
        this.$root.showLoading(false);
      }
    },
    HOANTHIEN_HS_THAYDOI_SOSIM: async function (hdtb_id) {
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-doisim/hoanthien_hs_thaydoi_sosim/"+hdtb_id);
        return true;
      } catch (error) {
        return false;
      } finally {
      }
    },
    lay_loaihd_by_hdtb: async function (hdtb_id) {
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-doisim/lay_loaihd_by_hdtb/"+hdtb_id);
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    capnhat_tthd_by_hdtb: async function (hdtb_id) {
         try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-doisim/capnhat_tthd_by_hdtb/"+hdtb_id);
        return true
      } catch (error) {
        return false;
      } finally {
      }

    },
    capnhat_ttphieu_by_hdtb: async function (hdtb_id, status) {
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/hoanthien-hs-doisim/capnhat_ttphieu_by_hdtb",{hdtb_id:hdtb_id,status:status});
        return true
      } catch (error) {
        return false;
      } finally {
      }
    },
    CapNhat_TT_Phieu_HTHS: async function (hdtb_id) {
      let dt = await this.lay_loaihd_by_hdtb(hdtb_id);
      let isHD_CaiDatTBI = false;
      if (dt != null && dt.length > 0)
        isHD_CaiDatTBI = dt[0].loaihd_id == Enum.LoaiHopDong.VATTU_THUEBAO;
      let status = 0;
      if (isHD_CaiDatTBI) status = 1;
      await this.capnhat_ttphieu_by_hdtb(hdtb_id, status);
    },
    magd_keypress: async function (e) {
      console.log(e)
      if (e.key == "Enter") {
        if (this.dataSelected.ma_gd != "") {
          await this.HienThiDanhSach();
        }
      }
    },
    matb_keypress: async function (e) {
      if (e.key == "Enter") {
        if (this.dataSelected.ma_tbao != "") {
          await this.HienThiDanhSach();
        }
      }
    },
    getIp:async function(){
      try{
      const response = await fetch("https://jsonip.com/")
      const data = await response.json();
      this.ipCurrent = data.ip;
      }catch(e) {
        this.ipCurrent =""
      }
    },
    async KIEMTRA_THAM_SO_DONGBO(keyname) {
      try{
        let kq = await this.$root.context.get("web-ccdv/hoanthienhschuyenquyen/kiemtra_open_sync_vina", {
          keyname: keyname
        })
        if (kq.data && kq.data.length > 0) {
          return kq.data[0].KEY_VALUE
        } else {
          return false
        }
      }catch(e){
        return false;
      }
      },
  },
};
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
