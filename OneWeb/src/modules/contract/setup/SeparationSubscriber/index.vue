<template src="./template.html"></template>
<script>
import define from "./define/index.js";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import ChonDiaChiModal from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi/ModalChonDiaChi";
import popupTraCuuThueBao from "./popups/popupTraCuuThueBao";
import frmTC_DBTB_KH from "./popups/frmTC_DBTB_KH.vue";
import PopupTimKiemNhanVienThuCuoc from "../InstallNewSubs/Popups/PopupTimKiemNhanVienThuCuoc";
import moment from "moment";
import ChiTietTienKM from "./popups/ChiTietTienKM.vue";
import ThongTinEmail from "../ChangeContractOwner/popup/ThongTinEmail.vue";
import frmNguoiGT from "./popups/frmNguoiGT.vue"
import {
  NHOM_LNV
} from "@/const/enums";

export default {
  components: {
    SearchContractModal,
    ChonDiaChiModal,
    popupTraCuuThueBao,
    PopupTimKiemNhanVienThuCuoc,
    frmTC_DBTB_KH,
    ChiTietTienKM,
    ThongTinEmail,
    frmNguoiGT
  },
  data() {
    return {
      ...define,
      ngay_cn: moment().format("DD/MM/YYYY"),
      nguoigt_id: 0,
      form: {
        dichvuvt_id: null,
        nhanvien_gt:"",
        loaitb_id: null,
        doituong_id: null,
        nganhang_id: null,
        httt_id: null,
        donvi_id: null,
        ma_gd: null,
        kieuld_id: null,
        tongtienhd: 0,
        tongtienvat: 0,
        tongtien: 0,
        ngay_gd: moment().format("DD/MM/YYYY"),
        ngaylap_hd: moment().format("DD/MM/YYYY"),
      },
      dataDiachikh: {},
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      },
      diachiTB: {},
      diachiTT: {},
      diachiBC: {},
      diachiCT: {},
      checkboxNGT: false,
      donvi_nguoi_gt_id:0,
      loainv_nguoi_gt_id:0
    };
  },
  mounted() {
    if(this.$route.query.hdkh_cha_id) {
      let hdkh_cha_id = this.$route.query.hdkh_cha_id
      this.LoadTuHopDongTuVan(hdkh_cha_id)
    }
    this.SetButton(-1);
    this.frmTachNhapTB_Load();
    if (this.$root.context.user.getProperty("DONVIQL_HTTT") == 1) {
      this.$set(this.thamso, "DONVIQL_HTTT", 1);
    }
    if (this.$root.context.user.getProperty("TUYENTHU_HTTT") == 1) {
      this.$set(this.thamso, "TUYENTHU_HTTT", 1);
    }
    if (this.$root.context.user.getProperty("PHANTAP_KH") == 1) {
      this.$set(this.thamso, "PHANTAP_KH", 1);
    }
    if (
      this.$root.context.user.getProperty("KHONG_HIENTHI_TRACUU_DB_TACHNHAP") ==
      1
    ) {
      this.$set(this.thamso, "KHONG_HIENTHI_TRACUU_DB_TACHNHAP", 1);
    }
    if (
      this.$root.context.user.getProperty("BATBUOC_GIOITHIEU_TACHNHAP") == 1
    ) {
      this.$set(this.thamso, "BATBUOC_GIOITHIEU_TACHNHAP", 1);
    }

    if (this.$root.context.user.getProperty("SINH_MA_GD_THEO_DV") == 1) {
      this.$set(this.thamso, "SINH_MA_GD_THEO_DV", 1);
    }

    this.$refs.txtMaTB.focus();
  },
  computed: {
    donvi_id: {
      get() {
        return String(this.form.donvi_id);
      },
      set(value) {
        this.$set(this.form, "donvi_id", value);
      },
    },
    doituong_id: {
      get() {
        return String(this.form.doituong_id);
      },
      set(value) {
        this.$set(this.form, "doituong_id", value);
      },
    },
  },
  watch: {
    "checkboxNGT"(val) {
      if(val) {
        let f = this.$refs.frmNguoiGT
        f.nhomlnv_id = NHOM_LNV.GIOITHIEU_HD;
        f.donvi_id = this.donvi_nguoi_gt_id;
        f.loainv_id = this.loainv_nguoi_gt_id;
        f.ctv_id = this.nguoigt_id;
        f.ShowDialog();
      }
    },
    "form.dichvuvt_id"(val) {
      console.log(val);
      if (val) {
        this.getLoaihinhTb();
      }
    },
    "form.loaitb_id"(val) {
      if (val) {
        this.getKieuYeucau();
      }
    },
    async "form.kieuld_id"(val) {
      await this.LayTienTheoKhoanMuc();
    },
  },
  methods: {
    chonNhanvien(f) {
      console.log(f)
      this.form.nhanvien_gt = f.ten_nv;
      this.nguoigt_id = f.nhanvien_id;
      this.loainv_nguoi_gt_id = f.loainv_id;
      this.donvi_nguoi_gt_id = f.donvi_id;
    },
    async LoadTuHopDongTuVan(hdkh_cha_id) {
      // this.hdkh_cha_id = hdkh_cha_id;
      // FacadeCommon fcd = new FacadeCommon();
      // string filter = string.Format("ma_tb is not null and khachhang_id in (select khachhang_id from {0}.hd_khachhang WHERE hdkh_id={1})"
      //     , DatabaseConstants.DB2
      //     , hdkh_cha_id);
      // DataTable dt = fcd.getData(DatabaseConstants.DB2, "db_thuebao", "ma_tb", "dichvuvt_id", filter, "").Tables[0];
      // if (dt.Rows.Count > 0)
      // {
      //     cboDichVuVT.SelectedValue = dt.Rows[0][1];
      //     txtMaTB.Text = dt.Rows[0][0].ToString();
      //     txtMaTB_KeyPress(null, new KeyPressEventArgs((char)13));
      // }
      // else
      // {
      //     Message_Box.Show("Khách hàng không có thuê bao");
      //     this.Close();
      // }

      try {
        let data = await this.axios.post("web-hopdong/thaydoidichvu/get_thongtin_tu_phieu_tuvan_hdkh",
        {
            "phdkh_cha_id": hdkh_cha_id
        })
        this.form.ma_tb = data.data.data[0].ma_tb
        this.layThongtin()
      } catch(err) {

      }

    },
    async ThongTinEmail_Click() {
      this.$refs.thongtinemail.show();
    },
    async tsbtnXoaTT_Click() {
      let kt = await this.axios.get(
        "web-hopdong/hopdong/kiemtra_hdtb_theo_hdtt",
        {
          params: {
            hdttId: this.form.hdtb_id,
          },
        }
      );

      if (kt.data && kt.data.data > 0) {
        this.$toast.error(
          "Thanh toán " +
            this.form.ma_tt +
            " đã có thuê bao! Hãy xóa thuê bao trước."
        );
      } else {
        this.$confirm(`Bạn thật sự muốn xóa thanh toán không ?`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }).then(async () => {
          let rs = await this.axios.post(
            "web-hopdong/hopdong/xoa_hopdong_thanhtoan?hdttId=" +
              this.form.hdtt_id
          );

          this.$toast.success("Xóa thanh toán thành công!");

          this.HienThiDS_HDTT_CBO();
          let dsHopdong = await this.axios.get(
            "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
            { params: { hdKhId: this.form.hdkh_id } }
          );
          this.HienThiDanhSacHDTT(dsHopdong.data ? dsHopdong.data.data : []);
        });
      }
    },
    async tsbtnThemTT_Click() {
      let rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
        keyname: "HD_THANHTOAN",
      });

      if (rs.data && rs.data.data) {
        let hdtt_id = rs.data.data;
        await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_insert_hd_thanhtoan",
          {
            js_hd_thanhtoan: JSON.stringify([
              {
                HDTT_ID: hdtt_id,
                HDKH_ID: this.form.hdkh_id,
                THANHTOAN_ID: this.form.thanhtoan_id,
                MA_TT: this.form.ma_tt,
                TEN_TT: this.form.ten_tt,
                DIACHI_TT: this.form.diachi_tt,
                DIACHI_BC: this.form.diachi_bc,
                MATB_DD: this.form.matb_dd,
                MST: this.form.mst,
                HTTT_ID: this.form.httt_id,
                STK: this.form.stk,
                NGANHANG_ID: this.form.nganhang_id,
                DONVI_ID: this.$root.token.getDonViID(),
                TUYENTHU_ID: this.form.tuyenthu_id,
                MAY_CN: this.$root.token.getMaNhanVien(),
                NGAY_CN: moment().format("DD/mm/yyyy hh:mm:ss"),
                NGUOI_CN: this.$root.token.getUserName(),
                DIACHI_CT: this.form.diachi_ct,
                GHICHU: null,
                DV_QHNS: null,
                MACHUONG: null,
                MN_KT: null,
                MA_NDKT: null,
                MN_NSNN: null,
                TIEN_DM: this.form.TIEN_DM,
              },
            ]),
          }
        );

        let rsDiachi = await this.axios.post(
          "/web-hopdong/khoiphucthanhly/get_keys",
          {
            keyname: "DIACHI",
          }
        );

        await this.axios.post("web-hopdong/tachnhapthuebao/sp_insert_diachi", {
          js_diachi: JSON.stringify([
            {
              DIACHI_ID: rsDiachi.data.data,
              TINH_ID: this.diachi.tinhtt_id,
              QUAN_ID: this.diachi.quantt_id,
              PHUONG_ID: this.diachi.phuongtt_id,
              PHO_ID: this.diachi.phott_id,
              AP_ID: this.diachi.aptt_id,
              KHU_ID: this.diachi.khutt_id,
              DACDIEM_ID: this.diachi.đaciemtt_id,
              HDTT_ID: hdtt_id,
            },
          ]),
        });

        this.HienThiDS_HDTT_CBO();
        let dsHopdong = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
          { params: { hdKhId: this.form.hdkh_id } }
        );
        this.HienThiDanhSacHDTT(dsHopdong.data ? dsHopdong.data.data : []);
      }
    },
    async HT_NVTC_TheoDC(diachi) {
      console.log(diachi);
      let data = null;
      if (this.thamso.TUYENTHU_HTTT == 1 && this.form.httt_id) {
        data = await this.axios.post(
          "web-hopdong/lapdatmoi/ht_nvtc_theodc_v2",
          {
            vap_id: diachi.vap_id ? diachi.vap_id : 0,
            vdacdiem_id: diachi.dacdiem_id ? diachi.dacdiem_id : 0,
            vdonvi_ql_id: diachi.donvi_ql_id ? diachi.donvi_ql_id : 0,
            vhttt_id: this.form.httt_id ? this.form.httt_id : 0,
            vkhu_id: diachi.khu_id ? diachi.khu_id : 0,
            vpho_id: diachi.pho_id ? diachi.pho_id : 0,
            vphuong_id: diachi.phuong_id ? diachi.phuong_id : 0,
            vphanvung_id: diachi.tinh_id ? diachi.tinh_id : 0,
          }
        );
      } else {
        data = await this.axios.post("web-hopdong/lapdatmoi/ht_nvtc_theodc", {
          vap_id: diachi.vap_id ? diachi.vap_id : 0,
          vdacdiem_id: diachi.dacdiem_id ? diachi.dacdiem_id : 0,
          vdonvi_ql_id: diachi.donvi_ql_id ? diachi.donvi_ql_id : 0,
          vkhu_id: diachi.khu_id ? diachi.khu_id : 0,
          vpho_id: diachi.pho_id ? diachi.pho_id : 0,
          vphuong_id: diachi.phuong_id ? diachi.phuong_id : 0,
          vphanvung_id: diachi.tinh_id ? diachi.tinh_id : 0,
        });
      }

      if (data.data && data.data.data && data.data.data.length > 0) {
        let value = data.data.data[0];
        this.$set(this.form, "nhanvientc_id", value.NHANVIEN_ID);
        this.$set(this.form, "ma_nv", value.maNVTC);
        this.$set(this.form, "tuyenthu_id", value.rowData.TUYENTHU_ID);
        this.$set(this.form, "matuyen", value.rowData.MA_TUYENTHU);
      }
    },
    getDiaChiKH(ds) {
      if (ds["diachi_id"]) this.diachi.diachikh_id = ds["diachi_id"];
      else this.diachi.diachikh_id = 0;

      if (ds["tinh_id"]) this.diachi.tinhkh_id = ds["tinh_id"];
      else this.diachi.tinhkh_id = 0;

      if (ds["quan_id"]) this.diachi.quankh_id = ds["quan_id"];
      else this.diachi.quankh_id = 0;

      if (ds["phuong_id"]) this.diachi.phuongkh_id = ds["phuong_id"];
      else this.diachi.phuongkh_id = 0;

      if (ds["pho_id"]) this.diachi.phokh_id = ds["pho_id"];
      else this.diachi.phokh_id = 0;

      if (ds["ap_id"]) this.diachi.apkh_id = ds["ap_id"];
      else this.diachi.apkh_id = 0;

      if (ds["khu_id"]) this.diachi.khukh_id = ds["khu_id"];
      else this.diachi.khukh_id = 0;

      if (ds["dacdiem_id"]) this.diachi.dacdiemkh_id = ds["dacdiem_id"];
      else this.diachi.dacdiemkh_id = 0;
      this.diachi.sonhakh = ds.sonha;
      this.form.diachi_kh = ds.diachimoi;
    },
    getDiaChiTT(ds) {
      console.log(ds);
      if (ds["diachi_id"]) this.diachi.diachitt_id = ds["diachi_id"];
      else this.diachi.diachitt_id = 0;

      if (ds["tinh_id"]) this.diachi.tinhtt_id = ds["tinh_id"];
      else this.diachi.tinhtt_id = 0;

      if (ds["quan_id"]) this.diachi.quantt_id = ds["quan_id"];
      else this.diachi.quantt_id = 0;

      if (ds["phuong_id"]) this.diachi.phuongtt_id = ds["phuong_id"];
      else this.diachi.phuongtt_id = 0;

      if (ds["pho_id"]) this.diachi.phott_id = ds["pho_id"];
      else this.diachi.phott_id = 0;

      if (ds["ap_id"]) this.diachi.aptt_id = ds["ap_id"];
      else this.diachi.aptt_id = 0;

      if (ds["khu_id"]) this.diachi.khutt_id = ds["khu_id"];
      else this.diachi.khutt_id = 0;

      if (ds["dacdiem_id"]) this.diachi.dacdiemtt_id = ds["dacdiem_id"];
      else this.diachi.dacdiemtt_id = 0;
      this.diachi.sonhatt = ds.sonha;
      this.form.diachi_tt = ds.diachimoi;

      this.HT_NVTC_TheoDC(ds);
    },
    getDiaChiCT(ds) {
      if (ds["diachi_id"]) this.diachi.diachict_id = ds["diachi_id"];
      else this.diachi.diachict_id = 0;

      if (ds["tinh_id"]) this.diachi.tinhct_id = ds["tinh_id"];
      else this.diachi.tinhct_id = 0;

      if (ds["quan_id"]) this.diachi.quanct_id = ds["quan_id"];
      else this.diachi.quanct_id = 0;

      if (ds["phuong_id"]) this.diachi.phuongct_id = ds["phuong_id"];
      else this.diachi.phuongct_id = 0;

      if (ds["pho_id"]) this.diachi.phoct_id = ds["pho_id"];
      else this.diachi.phoct_id = 0;

      if (ds["ap_id"]) this.diachi.apct_id = ds["ap_id"];
      else this.diachi.apct_id = 0;

      if (ds["khu_id"]) this.diachi.khuct_id = ds["khu_id"];
      else this.diachi.khuct_id = 0;

      if (ds["dacdiem_id"]) this.diachi.dacdiemct_id = ds["dacdiem_id"];
      else this.diachi.dacdiemct_id = 0;
      this.diachi.sonhact = ds.sonha;
      this.form.diachi_ct = ds.diachimoi;
    },
    getDiaChiBC(ds) {
      if (ds["diachi_id"]) this.diachi.diachibc_id = ds["diachi_id"];
      else this.diachi.diachibc_id = 0;

      if (ds["tinh_id"]) this.diachi.tinhbc_id = ds["tinh_id"];
      else this.diachi.tinhbc_id = 0;

      if (ds["quan_id"]) this.diachi.quanbc_id = ds["quan_id"];
      else this.diachi.quanbc_id = 0;

      if (ds["phuong_id"]) this.diachi.phuongbc_id = ds["phuong_id"];
      else this.diachi.phuongbc_id = 0;

      if (ds["pho_id"]) this.diachi.phobc_id = ds["pho_id"];
      else this.diachi.phobc_id = 0;

      if (ds["ap_id"]) this.diachi.apbc_id = ds["ap_id"];
      else this.diachi.apbc_id = 0;

      if (ds["khu_id"]) this.diachi.khubc_id = ds["khu_id"];
      else this.diachi.khubc_id = 0;

      if (ds["dacdiem_id"]) this.diachi.dacdiembc_id = ds["dacdiem_id"];
      else this.diachi.dacdiembc_id = 0;
      this.diachi.sonhabc = ds.sonha;
      this.form.diachi_bc = ds.diachimoi;

      this.HT_NVTC_TheoDC(ds);
    },
    getDiaChiTB(ds) {
      if (ds["diachi_id"]) this.diachi.diachitb_id = ds["diachi_id"];
      else this.diachi.diachitb_id = 0;

      if (ds["tinh_id"]) this.diachi.tinhtb_id = ds["tinh_id"];
      else this.diachi.tinhtb_id = 0;

      if (ds["quan_id"]) this.diachi.quantb_id = ds["quan_id"];
      else this.diachi.quantb_id = 0;

      if (ds["phuong_id"]) this.diachi.phuongtb_id = ds["phuong_id"];
      else this.diachi.phuongtb_id = 0;

      if (ds["pho_id"]) this.diachi.photb_id = ds["pho_id"];
      else this.diachi.photb_id = 0;

      if (ds["ap_id"]) this.diachi.aptb_id = ds["ap_id"];
      else this.diachi.aptb_id = 0;

      if (ds["khu_id"]) this.diachi.khutb_id = ds["khu_id"];
      else this.diachi.khutb_id = 0;

      if (ds["dacdiem_id"]) this.diachi.dacdiemtb_id = ds["dacdiem_id"];
      else this.diachi.dacdiemtb_id = 0;
      this.diachi.sonhatb = ds.sonha;
      this.form.diachi_tb = ds.diachimoi;
    },
    async ChiTietTienKM_OnClick() {
      this.$bvModal.show("popup-ChiTietTienKM");
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(",", ".");
      return val.toString().replace(/B(?=(d{3})+(?!d))/g, ",");
    },
    acceptTienKM(value) {
      this.form.tongtienhd = this.formatPrice(value.tong);
      this.form.tongtien = this.formatPrice(value.tongtien);
      this.form.tongtienvat = this.formatPrice(value.tongvat);
      this.$bvModal.hide("popup-ChiTietTienKM");
    },
    async ThemHDThueBao_DS(hdtb_id_ds, thuebao_id_ds, dichvuvt_id) {
      await this.HienThiTTDBMoRong(thuebao_id_ds, dichvuvt_id);
    },
    setMaTB(value) {
      if (value.ma_tb) this.$set(this.form, "ma_tb", value.ma_tb);
      if (value.dichvuvt_id)
        this.$set(this.form, "dichvuvt_id", value.dichvuvt_id);
    },
    async HienThiTTDBMoRong(thuebao_id, dichvuvt_id) {
      this.$set(this.form, "tocdo_id", 0);
      this.$set(this.form, "muccuoc_id", 0);
      this.$set(this.form, "trangbi_id", 0);
      let data = {};
      if (dichvuvt_id == 1) {
        //Cố định
        data = await this.axios.post(
          "web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid",
          {
            thuebao_id: thuebao_id,
          }
        );
      }
      if (dichvuvt_id == 4) {
        //ADSL
        data = await this.axios.post(
          "web-ccdv/xuly_nghiemthubaohong/lay_ds_db_adsl_theo_tbid",
          {
            thuebao_id: thuebao_id,
          }
        );
      }
      if (
        dichvuvt_id == 15 ||
        dichvuvt_id == 13 ||
        dichvuvt_id == 14 ||
        dichvuvt_id == 16
      ) {
        //ADSL
        data = await this.axios.post("web-hopdong/hopdong/lay_ds_danhba_cntt", {
          thuebao_id: thuebao_id,
        });
      }
      if (dichvuvt_id == 2) {
        //Di động
        data = await this.axios.post(
          "web-hopdong/tachnhapthuebao/lay_ds_db_dd_theo_tbid",
          {
            thuebao_id: thuebao_id,
          }
        );
      }
      if (dichvuvt_id == 8) {
        //MEGAWAN
        data = await this.axios.post(
          "web-hopdong/hopdong/lay_ds_danhba_megawan",
          {
            thuebao_id: thuebao_id,
          }
        );
      }
      if (dichvuvt_id == 9) {
        //TSL
        data = await this.axios.post("web-hopdong/hopdong/lay_ds_danhba_tsl", {
          thuebao_id: thuebao_id,
        });
      }
      if (dichvuvt_id == 10) {
        //Gphone
        data = await this.axios.post("web-hopdong/hopdong/lay_ds_danhba_gp", {
          thuebao_id: thuebao_id,
        });
      }
      if (dichvuvt_id == 12) {
        //Megaeys
        data = await this.axios.post(
          "web-hopdong/hopdong/lay_ds_danhba_megaeys",
          {
            thuebao_id: thuebao_id,
          }
        );
      }
      if (dichvuvt_id == 50) {
        //CNTT
        data = await this.axios.post("web-hopdong/hopdong/lay_ds_danhba_cntt", {
          thuebao_id: thuebao_id,
        });
      }

      if (data.data && data.data.data) {
        let rs = data.data.data;
        if (rs.length > 0) {
          if (rs[0].tocdo_ID) this.$set(this.form, "tocdo_id", rs[0].tocdo_ID);
          if (rs[0].muccuoc_ID)
            this.$set(this.form, "muccuoc_id", rs[0].muccuoc_ID);
          if (rs[0].trangbi_ID)
            this.$set(this.form, "trangbi_id", rs[0].trangbi_ID);
        }
      }
    },
    async matt_enter() {
      if (this.form.kieuld_id == 19) {
        this.$toast.error(
          "Bạn đang chọn kiểu yêu cầu là: tách thuê bao nMã thanh toán mới sẽ tự sinh! Bạn không được chọn!"
        );
        this.form.ma_tt = "";
      } else {
        let rs = await this.axios.post(
          "/web-hopdong/hopdong/kiemtra_makh_matt_magd",
          {
            maKh: this.form.ma_tt,
            loaiHdId: 10,
            kieu: 2,
          }
        );

        if (rs.data && rs.data.data == 1) {
          this.HienThiTT_DanhBa_KH_NhanTB(this.form.ma_tt, 2);
        } else {
          this.$toast.error(
            "Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.nBạn hãy kiểm tra lại!"
          );
        }
      }
    },
    async HienThiTT_DanhBa_KH_NhanTB(ma_tb, kieu) {
      let ds = [];
      if (kieu == 2) {
        let rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/lay_thongtin_kh_theo_ma_tt_v2",
          {
            ma_tt: ma_tb,
          }
        );

        ds = rs.data ? rs.data.data : [];
        if (ds.length > 0) {
          if (this.form.ma_kh_cu) {
            if (ds[0].ma_kh && ds[0].ma_kh != this.form.ma_kh_cu) {
              this.$toast.error(
                "Khách hàng nhận thuê bao tách ghép và khách hàng yêu cầu tách ghép không giống nhau !"
              );
              this.HienThiTT_DanhBa(this.form.ma_tb_cu);
              this.$set(this.form, "ma_tt", "");
            } else {
              let arr = [
                "ma_kh",
                "khachhang_id",
                "ten_kh",
                "diachi_kh",
                "thanhtoan_id",
                "matb_dd",
                "ten_tt",
                "diachi_tt",
                "diachi_bc",
                "main_ghep",
                "DV_QHNS",
                "MACHUONG",
                "MN_KT",
                "MA_NDKT",
                "MN_NSNN",
                "TIEN_DM",
                "mst_tt",
                "stk_tt",
                "nganhang_id",
                "httt_id",
                "donvi_id",
                "nhanvien_id",
                "tuyenthu_id",
                "diachi_ct",
                "ngaylap_hd",
              ];
              arr.forEach((item) =>
                this.$set(this.form, item, ds[0][item] ? ds[0][item] : 0)
              );
            }
          } else {
            this.$toast.error("Chưa có thông tin thuê bao tách ghép!");
          }
        } else {
          this.$set(this.form, "ma_tt", "");
          this.$toast.error("Không tìm thấy thông tin thanh toán!");
        }
      }
    },
    async tsbtnXoaTB_Click() {
      this.$confirm(`Bạn thật sự muốn xóa thuê bao không ?`, {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
      }).then(async () => {
        try {
          this.loading(true);
          let rs = await this.axios.post(
            "web-hopdong/khoiphucthanhly/xoa_hdtb",
            {
              hdtb_id: this.form.hdtb_id,
            }
          );
          this.$toast.success(
            "Xóa thuê bao trên dữ liệu viễn thông tỉnh thành công ! "
          );
          this.HT_DanhSachTB();
          let dsHopdongTB = await this.axios.get(
            "web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hopdong_tb_theo_id/" +
              this.form.hdkh_id
          );

          this.HienThiTTHopDongTB(
            dsHopdongTB.data ? dsHopdongTB.data.data : []
          );
          this.loading(false);
        } catch (err) {
          this.$toast.error(
            "Xóa thuê bao trên dữ liệu viễn thông tỉnh không thành công ! "
          );
        }
      });
    },
    async tsbtnThemTB_Click() {
      this.loading(true);
      let kiemtra = await this.axios.post(
        "web-hopdong/hopdong/fn_kt_tsbtnthemtb_tachnhaptb",
        {
          vthuebao_id: this.form.thuebao_id,
          vthanhtoan_id: this.form.thanhtoan_id,
          vma_tb: this.form.ma_tb,
        }
      );

      if (kiemtra.data && kiemtra.data.data != 1) {
        this.$toast.error(kiemtra.data.data);
      }

      this.ThemHDThueBao();
      this.loading(false);
    },
    async ThemHDThueBao() {
      try {
        let tienKM = [];
        this.ds_tien_khoanmuc.forEach((item) => {
          tienKM.push({
            KHOANMUCTT_ID: item.khoanmuctt_id,
            TIEN: item.tien,
            VAT: item.vat,
          });
        });

        let dset = await this.axios.post(
          "web-hopdong/hopdong/lay_huonggiao_tiepnhan",
          {
            kieuLdId: this.form.kieuld_id,
            loaiHdId: 10,
            loaiTbId: this.form.loaitb_id,
            mucCuocId: this.form.muccuoc_id ? this.form.muccuoc_id : 0,
            tocDoId: this.form.tocdo_id ? this.form.tocdo_id : 0,
          }
        );

        if (!dset.data.data) {
          this.$toast.error(
            "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
          );
          return false;
        }

        let quytrinh_id = dset.data.data ? dset.data.data.quytrinh_id : 0;
        let huonggiao_tn = dset.data.data ? dset.data.data.huonggiao_id : 0;

        let rs = await this.axios.post(
          "web-hopdong/hopdong/fn_themhdthuebao_tsbtnthemtb_tachnhaptb",
          {
            vds: JSON.stringify([
              {
                HUONGGIAO_TN: huonggiao_tn,
                QUYTRINH_ID: quytrinh_id,
                MA_TB: this.form.ma_tb,
                TEN_TB: this.form.ten_tb,
                DIACHI_TB: this.form.diachi_tb,
                HDKH_ID: this.form.hdkh_id,
                HDTT_ID: this.form.hdtt_id,
                DIACHI_LD: this.form.diachi_ld,
                DOITUONG_ID: this.form.doituong_id,
                NGAY_HT: moment().format("DD/mm/yyyy hh:mm:ss"),
                MAY_CN: this.$root.token.getMaNhanVien(),
                NGAY_CN: moment().format("DD/mm/yyyy hh:mm:ss"),
                NGUOI_CN: this.$root.token.getUserName(),
                IP_CN: this.$root.token.getUserName(),
                TOCDO_ID: this.form.tocdo_id,
                MUCCUOC_ID: this.form.muccuoc_id,
                KIEULD_ID: this.form.kieuld_id,
                GOICUOC_ID: this.form.goicuoc_id,
                DICHVUVT_ID: this.form.dichvuvt_id,
                DICHVUVT_ID_CBO: this.form.dichvuvt_id,
                DONVI_ID: this.$root.token.getDonViID(),
                LOAITB_ID: this.form.loaitb_id,
                TINHLD_ID: this.diachi.tinhld_id,
                QUANLD_ID: this.diachi.quanld_id,
                PHUONGLD_ID: this.diachi.phuongld_id,
                PHOLD_ID: this.diachi.phold_id,
                APLD_ID: this.diachi.apld_id,
                KHULD_ID: this.diachi.khuld_id,
                DACDIEMLD_ID: this.diachi.dacdiemld_id,
                SONHALD: this.diachi.sonha_ld,
                DIACHILD: this.diachi.diachi_ld,
                BLOCKLD: null,
                TANGLD: null,
                SOPHONGLD: null,
                MOTADCLD: null,
                TINHTB_ID: this.diachi.tinhtb_id,
                QUANTB_ID: this.diachi.quantb_id,
                PHUONGTB_ID: this.diachi.phuongtb_id,
                PHOTB_ID: this.diachi.photb_id,
                APTB_ID: this.diachi.aptb_id,
                KHUTB_ID: this.diachi.khutb_id,
                DACDIEMTB_ID: this.diachi.dacdiemtb_id,
                SONHATB: this.diachi.sonha_tb,
                DIACHITB: this.diachi.diachi_tb,
                BLOCKLD: null,
                TANGLD: null,
                SOPHONGLD: null,
                MOTADCLD: null,
              },
            ]),
            vds_tien_khoanmuc: JSON.stringify(tienKM),
          }
        );
        if (rs.data) {
          if (rs.data.data != 1) {
            this.$toast.error(rs.data.data);
          } else {
            this.$toast.success("Thành công");
          }
          this.HT_DanhSachTB();
          let dsHopdong = await this.axios.get(
            "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
            { params: { hdKhId: this.form.hdkh_id } }
          );
          this.HienThiDanhSacHDTT(dsHopdong.data ? dsHopdong.data.data : []);
          this.HienThiDS_HDTT_CBO();
        }
      } catch(err) {
        console.log(err)
      }
    },
    async select_thuebao(data) {
      this.loading(true);
      let rs = await this.axios.post(
        "web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id",
        {
          hdtb_id: data.data.hdtb_id,
        }
      );

      await this.HienThiTTHopDongTB(rs.data ? rs.data.data : []);
      this.loading(false);
    },
    async tsbtnXoa_Click() {
      this.$confirm(
        `Bạn thật sự muốn xóa hợp đồng không ?`,
        "Giản đồ mạng cáp",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      ).then(async () => {
        try {
          this.loading(true);
          await this.axios.post("web-hopdong/khoiphucthanhly/xoa_hdkh_v2", {
            hdkh_id: this.form.hdkh_id,
            loaihd_id: 10,
          });
          this.$toast.success("Xóa họp đồng thành công");
          this.SetButton(0);
          this.loading(false);
        } catch (err) {
          this.$toast.error("Xóa họp đồng không thành công");
        }
      });
    },
    async tsbtnGhiLai_Click() {
      // let rs = await this.axios.post("/web-hopdong/thanhly/thanh_ly_map_id", {
      //   id_neo: "thanhtoan_id",
      //   in_table: "db_thuebao",
      //   in_dk: "where thuebao_id=" + this.form.thuebao_id,
      // });
      // let vthanhtoan_id = rs.data.data
      // if(vthanhtoan_id != "-1") {
      // }
      this.CapNhat();
    },
    async popupClosed(val) {
      this.$bvModal.hide("popupComponents");
      if (val.ma_tt) {
        this.$set(this.form, "ma_tt", val.ma_tt);
        this.matt_enter();
      }
      console.log(val, "popupclose");
    },
    async CapNhat() {
      try {
        this.loading(true);
        await this.KiemtraDLTachNhap();
        await this.KiemTraTachNhap();
        let kieu = -1;
        if (!this.button.nhapmoi) {
          kieu = 0; //thêm mới
        } else {
          kieu = 1; //cập nhật
        }

        let ds_tb_hd = [];
        this.m_dsThueBao_DichVu.forEach((item) => {
          ds_tb_hd.push({
            DANGKY: item.dangKy,
            HUY: item.huy,
            DICHVUGT_ID: item.dichVuGtId,
            NOIDUNG: item.noiDung ? item.noiDung : "",
          });
        });

        let dset = await this.axios.post(
          "web-hopdong/hopdong/lay_huonggiao_tiepnhan",
          {
            kieuLdId: this.form.kieuld_id,
            loaiHdId: 10,
            loaiTbId: this.form.loaitb_id,
            mucCuocId: this.form.muccuoc_id ? this.form.muccuoc_id : 0,
            tocDoId: this.form.tocdo_id ? this.form.tocdo_id : 0,
          }
        );

        if (!dset.data.data) {
          this.$toast.error(
            "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
          );
          return false;
        }

        let quytrinh_id = dset.data.data ? dset.data.data.quytrinh_id : 0;
        let huonggiao_tn = dset.data.data ? dset.data.data.huonggiao_id : 0;

        let dataKiemtra = this.axios.post(
          "web-hopdong/hopdong/kiemtra_thuebao_cung_matt",
          {
            hdttId: this.form.hdtt_id,
            dichVuVtId: this.form.dichvuvt_id,
            maGd: this.form.ma_gd,
          }
        );

        let tienKM = [];
        this.ds_tien_khoanmuc.forEach((item) => {
          tienKM.push({
            KHOANMUCTT_ID: item.khoanmuctt_id,
            TIEN: item.tien,
            VAT: item.vat,
          });
        });

        let capnhat = await this.axios.post(
          "web-hopdong/tachnhapthuebao/capnhat_tachnhap_thuebao",
          {
            vkieu: kieu,
            vds: JSON.stringify([
              {
                MA_GD: this.form.ma_gd,
                HDTB_ID: this.form.hdtb_id,
                MA_TB: this.form.ma_tb,
                MA_HD: this.form.ma_hd,
                MA_KH: this.form.ma_kh,
                TEN_KH: this.form.ten_kh,
                DIACHI_KH: this.form.diachi_kh,
                DIACHI_TB: this.diachi.diachikh_id,
                KHACHHANG_ID: this.form.khachhang_id,
                NHANVIEN_ID: this.form.nhanvien_id,
                NHANVIENGT_ID: this.nguoigt_id,
                NGAY_YC: moment().format("DD/MM/YYYY"),
                HUONGGIAO_TN: huonggiao_tn,
                GIOITINH: this.form.gioitinh,
                LOAIKH_ID: this.form.loaikh_id,
                LOAIGT_ID: this.form.loaigt_id,
                NGAYCAP: moment(this.form.ngaycap).format("DD/MM/YYYY"),
                TEN_TB: this.form.ten_tb,
                QUYTRINH_ID: quytrinh_id,
                DIACHI_TB: this.form.diachi_tb,
                HDKH_ID: this.form.hdkh_id,
                NGAYLAP_HD: moment(this.form.ngaylap_hd).format("DD/MM/YYYY"),
                HDTT_ID: this.form.hdtt_id,
                MA_TT: this.form.ma_tt,
                THANHTOAN_ID: this.form.thanhtoan_id,
                TEN_TT: this.form.ten_tt,
                DIACHI_TT: this.form.diachi_tt,
                DIACHI_BC: this.form.diachi_bc,
                DIACHI_LD: this.form.diachi_ld,
                MATB_DD: this.form.matb_dd,
                DIACHI_ID: this.diachi.diachikh_id
                  ? this.diachi.diachikh_id
                  : 0,
                DIACHIKH_ID: this.diachi.diachikh_id
                  ? this.diachi.diachikh_id
                  : 0,
                DIACHITT_ID: this.diachi.diachitt_id
                  ? this.diachi.diachitt_id
                  : 0,
                DIACHIBC_ID: this.diachi.diachibc_id
                  ? this.diachi.diachibc_id
                  : 0,
                DIACHILD_ID: this.diachi.diachild_id
                  ? this.diachi.diachild_id
                  : 0,
                MST: this.form.mst,
                HTTT_ID: this.form.httt_id,
                STK: this.form.stk,
                NGANHANG_ID: this.form.nganhang_id,
                TUYENTHU_ID: this.form.tuyenthu_id,
                DIACHI_CT: this.form.diachi_ct,
                GHICHU: this.form.ghichu,
                DV_QHNS: this.form.DV_QHNS,
                MACHUONG: this.form.MACHUONG,
                MN_KT: this.form.MN_KT,
                MA_NDKT: this.form.MA_NDKT,
                MN_NSNN: this.form.MN_NSNN,
                TIEN_DM: this.form.TIEN_DM,
                THUEBAO_ID: this.form.thuebao_id,
                KIEULD_ID: this.form.kieuld_id,
                DOITUONG_ID: this.form.doituong_id,
                NGAY_HT: moment().format("DD/MM/YYYY"),
                MAY_CN: this.$root.token.getMaNhanVien(),
                NGAY_CN: moment().format("DD/MM/YYYY"),
                NGUOI_CN: this.$root.token.getNhanVienID(),
                IP_CN: "xxx",
                TOCDO_ID: this.form.tocdo_id,
                MUCCUOC_ID: this.form.muccuoc_id,
                KIEULD_ID: this.form.kieuld_id,
                GOICUOC_ID: this.form.goicuoc_id,
                DICHVUVT_ID: this.form.dichvuvt_id,
                DICHVUVT_ID_CBO: 1,
                DONVI_ID: this.$root.token.getDonViID(),
                DONVIQL_ID: this.form.donvi_id,
                LOAITB_ID: this.form.loaitb_id,
                TINHKH_ID: this.diachi.tinhkh_id,
                QUANKH_ID: this.diachi.quankh_id,
                PHUONGKH_ID: this.diachi.phuongkh_id,
                PHOKH_ID: this.diachi.phokh_id,
                APKH_ID: this.diachi.apkh_id,
                KHUKH_ID: this.diachi.khukh_id,
                DACDIEMKH_ID: this.diachi.dacdiemkh_id,
                SONHAKH: this.form.sonha_kh,
                DIACHIKH: this.diachi.diachi_kh,
                TINHTT_ID: this.diachi.tinhtt_id,
                QUANTT_ID: this.diachi.quantt_id,
                PHUONGTT_ID: this.diachi.phuongtt_id,
                PHOTT_ID: this.diachi.phott_id,
                APTT_ID: this.diachi.aptt_id,
                KHUTT_ID: this.diachi.khutt_id,
                DACDIEMTT_ID: this.diachi.dacdiemtt_id,
                SONHATT: this.form.sonha_tt,
                DIACHITT: this.diachi.diachi_tt,
                TINHLD_ID: this.diachi.tinhld_id,
                QUANLD_ID: this.diachi.quanld_id,
                PHUONGLD_ID: this.diachi.phuongld_id,
                PHOLD_ID: this.diachi.phott_id,
                APLD_ID: this.diachi.apld_id,
                KHULD_ID: this.diachi.khuld_id,
                DACDIEMLD_ID: this.diachi.dacdiemld_id,
                SONHALD: this.diachi.sonha_ld,
                DIACHILD: this.diachi_ld,
                BLOCKLD: null,
                TANGLD: null,
                SOPHONGLD: null,
                MOTADCLD: null,
                TINHTB_ID: this.diachi.tinhtb_id,
                QUANTB_ID: this.diachi.quantb_id,
                PHUONGTB_ID: this.diachi.phuongtb_id,
                PHOTB_ID: this.diachi.photb_id,
                APTB_ID: this.diachi.aptb_id,
                KHUTB_ID: this.diachi.khutb_id,
                DACDIEMTB_ID: this.diachi.dacdiemtb_id,
                SONHALD: this.form.sonha_ld,
                DIACHILD: this.diachi_ld,
                BLOCKLD: null,
                TANGLD: null,
                SOPHONGLD: null,
                MOTADCLD: null,
                TINHBC_ID: this.diachi.tinhbc_id,
                QUANBC_ID: this.diachi.quanbc_id,
                PHUONGBC_ID: this.diachi.phuongbc_id,
                PHOBC_ID: this.diachi.phobc_id,
                APBC_ID: this.diachi.apbc_id,
                KHUBC_ID: this.diachi.khubc_id,
                DACDIEMBC_ID: this.diachi.dacdiembc_id,
                SONHABC: this.diachi.sonha_bc,
                DIACHIBC: this.diachi.diachi_bc,
                NHANVIEN_QL_AM_ID: this.form.nhanvien_ql_am_id,
              },
            ]),
            vds_tien_khoanmuc: JSON.stringify(tienKM),
            vdstb_dichvu: JSON.stringify(ds_tb_hd),
          }
        );
        if (capnhat.data.data != 1) {
          this.$toast.success(capnhat.data.message);
        } else {
          this.$toast.success("Cập nhật thành công");
        }
        // this.HT_DanhSachTB();
        // let dsHopdong = await this.axios.get(
        //   "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
        //   { params: { hdKhId: this.form.hdkh_id } });
        // this.HienThiDanhSacHDTT(dsHopdong.data ? dsHopdong.data.data : []);
        // this.HienThiDS_HDTT_CBO();
        this.layThongtin();
        this.SetButton(3);
        this.loading(false);
      } catch (err) {
        console.log(err);
        this.loading(false);
        this.$toast.error(err.message);
      }
    },
    async HienThi_NVQL_AM_HTTT() {},
    async lvwThanhToan_SelectedIndexChanged(data) {
      if (data.data) {
        this.loading(true);
        let hdtt_id = data.data.hdtt_id;
        let dsHopdong = await this.axios.post(
          "web-hopdong/tachnhapthuebao/lay_ds_hopdong_tt_theo_hdtt_id",
          { hdtt_id: hdtt_id }
        );
        this.HienThiTTHopDongTT(dsHopdong.data ? dsHopdong.data.data : []);
        this.loading(false);
      }
    },
    async LAY_DS_SUDUNG_DVGT(thuebao_id) {
      let rs = await this.axios.post(
        "web-hopdong/hopdong/lay_ds_thuebao_dichvu",
        {
          hdtbId: 0,
          thueBaoId: thuebao_id,
          loaiTbId: this.form.loaitb_id,
          kieuId: 1,
        }
      );

      this.m_dsThueBao_DichVu = rs.data ? rs.data.data : [];
    },
    async KiemTraTachNhap() {
      let kieu = -1;
      if (this.form.kieuld_id == 19) {
        kieu = 1;
      }
      if (this.form.kieuld_id == 167) {
        kieu = 2;
      }
      try {
        let rs = this.axios.post(
          "web-hopdong/tachnhapthuebao/kiemtra_tachghep_matt",
          {
            thuebao_id: this.form.thuebao_id,
            thanhtoan_id_moi: this.form.thanhtoan_id,
            chan: 1,
            kieu: kieu,
          }
        );
      } catch (err) {
        this.$toast.error(err.message);
      }
    },
    async KiemtraDLTachNhap() {
      try {
        let kieu = 0;

        if (!this.button.themmoi) {
          kieu = 0;
        } else {
          kieu = 1;
        }

        let rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/fn_kiemtradl_tachnhaptb",
          {
            kieu: kieu,
            kieuld_id: this.form.kieuld_id,
            ma_tt: this.form.ma_tt ? this.form.ma_tt : "",
            ma_kh: this.form.ma_kh,
            thuebao_id: this.form.thuebao_id,
          }
        );

        if (rs.data.data != 1) {
          this.$toast.error(rs.data.data);
          return false;
        }
      } catch (err) {
        this.$toast.error("Đã có lỗi xảy ra! " + err.message);
        return false;
      }
    },
    ShowPopupChonMaTT() {
      this.popupComponentData = this.form.khachhang_id;
      this.popupComponent = () =>
        import("../InstallNewSubs/Popups/popupChonMaTT");
      this.popupComponentName = "popupChonMaTT";
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    clickButton(type) {
      if (!this.button[type]) {
        return false;
      }

      switch (type) {
        case "nhapmoi":
          this.SetButton(1);
          break;
        case "huybo":
          this.SetButton(0);
          break;
        case "HTHS":
          this.btnHTHS_Click();
          break;
        case "ghilai":
          this.tsbtnGhiLai_Click();
          break;
        case "xoa":
          this.tsbtnXoa_Click();
          break;
        case "thanhtoan":
          this.$router.push("/contract/Payment");
          break;
        case "xoatb":
          this.tsbtnXoaTB_Click();
          break;
        case "themtb":
          this.tsbtnThemTB_Click();
          break;
        case "themtt":
          this.tsbtnThemTT_Click();
          break;
        case "xoatt":
          this.tsbtnXoaTT_Click();
          break;
        case "themtb_ds":
          this.$refs.frmTC_DBTB_KH.showModal(
            this.form.khachhang_id,
            0,
            0,
            this.form.hdkh_id,
            0
          );
          break;
      }
    },
    async setDBTB(data) {
      if (this.form.dichvuvt_id == 2 && this.form.loaitb_id == 20) {
        let kt1 = await this.axios.post(
          "web-hopdong/hopdong/kiemtra_thuebao_cung_matt",
          {
            dichVuVtId: this.form.dichvuvt_id,
            hdttId: this.form.hdtt_id,
            maGd: this.form.ma_gd,
          }
        );

        if (kt1.data && kt1.data != 1) {
          this.$toast.error(
            "Đối với thuê bao di động, tất cả các thuê bao trong cùng hợp đồng phải cùng mã thanh toán"
          );
          return;
        }

        let kt2 = await this.axios.post(
          "web-hopdong/hopdong/kiemtra_hopdong_thuebao_dongbo_dulieu",
          {
            dichVuVtId: this.form.dichvuvt_id,
            loaiTbId: this.form.loaitb_id,
            maGd: this.form.ma_gd,
          }
        );

        if (kt2.data && kt2.data.data != 1) {
          this.$$toast.error(
            "Có lỗi xảy ra trong quá trình thêm thuê bao VinaPhone"
          );
          return;
        }
      }

      let ds_matb = [];
      data.forEach((item) => {
        ds_matb.push({ THUEBAO_ID: item.thuebao_id });
      });

      let ds_hd = [
        {
          TEN_TB: this.form.ten_tb,
          DIACHI_TB: this.form.diachi_tb,
          HDKH_ID: this.form.hdkh_id,
          HDTT_ID: this.form.hdtt_id,
          DIACHI_LD: this.form.diachi_ld,
          KIEULD_ID: this.form.kieuld_id,
          DOITUONG_ID: this.form.doituong_id,
          NGAY_HT: moment().format("DD/MM/yyyy hh:mm:ss"),
          MAY_CN: this.$root.token.getUserName(),
          NGAY_CN: moment().format("DD/MM/yyyy hh:mm:ss"),
          NGUOI_CN: this.$root.token.getMaNhanVien(),
          IP_CN: this.$root.token.getUserName(),
          TOCDO_ID: this.form.tocdo_id,
          MUCCUOC_ID: this.form.muccuoc_id,
          GOICUOC_ID: this.form.goicuoc_id,
          TINHLD_ID: this.diachi.tinhld_id,
          QUANLD_ID: this.diachi.quanld_id,
          PHUONGLD_ID: this.diachi.phuongld_id,
          PHOLD_ID: this.diachi.phold_id,
          APLD_ID: this.diachi.apld_id,
          KHULD_ID: this.diachi.khuld_id,
          DACDIEMLD_ID: this.diachi.dacdiemld_id,
          SONHALD: this.diachi.sonha_ld,
          TINHTB_ID: this.diachi.tinhtb_id,
          QUANTB_ID: this.diachi.quantb_id,
          PHUONGTB_ID: this.diachi.phuongtb_id,
          PHOTB_ID: this.diachi.photb_id,
          APTB_ID: this.diachi.aptb_id,
          KHUTB_ID: this.diachi.khutb_id,
          DACDIEMTB_ID: this.dacdiemtb_id,
          SONHATB: this.sonha_tb,
        },
      ];

      let rs = await this.axios.post(
        "web-hopdong/tachnhapthuebao/fn_themhdtb_ds_tachnhaptb",
        {
          ds_matb: JSON.stringify(ds_matb),
          ds_hd: JSON.stringify(ds_hd),
        }
      );

      if (rs.data) {
        if (rs.data.data != 1) {
          this.$toast.error(rs.data.data);
        } else {
          this.$toast.success("Thêm danh sách thuê bao thành công!");
        }
      }
    },
    Clear() {
      this.$set(this.form, "nhanvien_ql", "");
      this.$set(this.form, "nhanvien_ql_am_id", 0);
      this.$set(this.form, "pt_doanhthu", 0);
      this.$set(this.form, "pt_diem_tn", 0);
      this.$set(this.form, "vnganhnghe_id", 0);
      this.$set(this.form, "loaikh_id", "");
      this.$set(this.form, "quytrinh_id", "");
      this.$set(this.form, "ngaycap", "");
      this.$set(this.form, "trangbi_id", 3);
      this.$set(this.form, "doituong_id", 1);
      this.$set(this.form, "tongtien", "");
      this.$set(this.form, "hdkh_id", 0);
      this.$set(this.form, "ma_gd", "");
      this.$set(this.form, "ma_hd", "");
      this.$set(this.form, "ma_kh", "");
      this.$set(this.form, "khachhang_id", 0);
      this.$set(this.form, "ten_kh", "");
      this.$set(this.form, "diachi_kh", "");

      this.$set(this.diachi, "tinhkh_id", 0);
      this.$set(this.diachi, "quankh_id", 0);
      this.$set(this.diachi, "phuongkh_id", 0);
      this.$set(this.diachi, "apkh_id", 0);
      this.$set(this.diachi, "khukh_id", 0);
      this.$set(this.diachi, "dacdiemkh_id", 0);

      this.$set(this.form, "sonhakh", "");
      this.$set(this.form, "ngaylap_hd", moment().format("DD/MM/YYYY"));
      this.$set(this.form, "ma_tuyen", "");
      this.$set(this.form, "email", "");
      this.$set(this.form, "tuyenthu_id", 0);
      this.$set(this.form, "nhanvientc_id", 0);
      this.$set(this.form, "ma_tt", "");
      this.$set(this.form, "thanhtoan_id", null);
      this.$set(this.form, "ten_tt", "");
      this.$set(this.form, "diachi_tt", "");

      //diachi bc
      this.$set(this.diachi, "diachi_bc", "");
      this.$set(this.diachi, "tinhbc_id", 0);
      this.$set(this.diachi, "quanbc_id", 0);
      this.$set(this.diachi, "phuongbc_id", 0);
      this.$set(this.diachi, "phobc_id", 0);
      this.$set(this.diachi, "apbc_id", 0);
      this.$set(this.diachi, "khubc_id", 0);
      this.$set(this.diachi, "dacdiembc_id", 0);
      this.$set(this.diachi, "sonhabc", "");

      //diachi ct
      this.$set(this.diachi, "diachi_ct", "");
      this.$set(this.diachi, "tinhct_id", 0);
      this.$set(this.diachi, "phuongct_id", 0);
      this.$set(this.diachi, "phoct_id", 0);
      this.$set(this.diachi, "apct_id", 0);
      this.$set(this.diachi, "khuct_id", 0);
      this.$set(this.diachi, "dacdiemct_id", 0);
      this.$set(this.diachi, "sonhact", 0);
      this.$set(this.diachi, "quanct_id", 0);

      //
      this.$set(this.diachi, "tinhtt_id", 0);
      this.$set(this.diachi, "quantt_id", 0);
      this.$set(this.diachi, "phuongtt_id", 0);
      this.$set(this.diachi, "phott_id", 0);
      this.$set(this.diachi, "aptt_id", 0);
      this.$set(this.diachi, "khutt_id", 0);
      this.$set(this.diachi, "dacdiemtt_id", 0);
      this.$set(this.diachi, "sonhatt", "");

      this.$set(this.diachi, "tinhtb_id", 0);
      this.$set(this.diachi, "quantb_id", 0);
      this.$set(this.diachi, "phuongtb_id", 0);
      this.$set(this.diachi, "photb_id", 0);
      this.$set(this.diachi, "aptb_id", 0);
      this.$set(this.diachi, "khutb_id", 0);
      this.$set(this.diachi, "dacdiemtb_id", 0);
      this.$set(this.diachi, "sonhatb", "");

      this.$set(this.form, "mst", "");
      this.$set(this.form, "stk", "");
      this.$set(this.form, "httt", null);
      this.$set(this.form, "nganhang_id", null);

      this.$set(this.form, "hdtb_id", 0);
      this.$set(this.form, "thuebao_id", "");
      this.$set(this.form, "ten_tb", null);
      this.$set(this.form, "diachi_tb", null);

      this.$set(this.form, "dichvuvt_id", 1);

      this.$set(this.form, "kieuld_id", null);
      this.$set(this.form, "doituong_id", null);
      this.options.dsThuebao = [];
      this.options.dsHopdong = [];

      this.$set(this.form, "ngay_yc", "");
      this.$set(this.form, "thuebao_id", "");
      this.$set(this.form, "ten_tb", null);
      this.$set(this.form, "diachi_tb", null);

      this.$set(this.form, "dichvuvt_id", 1);

      this.$set(this.form, "kieuld_id", null);
      this.$set(this.form, "doituong_id", null);

      this.$set(this.form, "diachi_ld", "");
      this.$set(this.form, "diachi_ct", "");

      this.$set(this.form, "ma_nv", "");
      this.$set(this.form, "matb_dd", "");
      this.$set(this.form, "khachhang_id_dtg", 0);
      this.$set(this.form, "khachhang_id_nm", 0);

      this.$set(this.form, "ten_tt", "");
      this.$set(this.form, "ma_tt", "");
      this.$set(this.form, "diachi_tt", "");
      this.$set(this.form, "quoctich", "");
      this.$set(this.form, "ghichu", "");
      this.$set(this.form, "ten_nv_ql", "");
      this.$set(this.form, "diachi_bc", "");
      
      
      // this.$set(this.form, "ma_tb", "");
      this.$set(this.form, "diachi_tt", "");
      this.$set(this.form, "ltb_adsl_id", "");
      this.LayTienTheoKhoanMuc();

      this.$set(this.form, "DV_QHNS", "");
      this.$set(this.form, "MACHUONG", "");
      this.$set(this.form, "MN_KT", "");
      this.$set(this.form, "MA_NDKT", "");
      this.$set(this.form, "MN_NSNN", "");
      this.$set(this.form, "TIEN_DM", "");

      this.$set(this.form, "hdmig_id", "");
      this.$set(this.form, "mig_id", "");
      this.$set(this.form, "maig", "");
      this.$set(this.form, "madd_id", null);
    },
    async HienThiTTDiaChi(ds, kieu) {
      if (ds && ds.length > 0) {
        if (kieu == 1) {
          this.diachi.diachikh_id = ds[0]["diachi_id"];
          if (ds[0]["tinh_id"]) this.diachi.tinhkh_id = ds[0]["tinh_id"];
          else this.diachi.tinhkh_id = 0;
          this.diachi.quankh_id = ds[0]["quan_id"];
          this.diachi.phuongkh_id = ds[0]["phuong_id"];
          if (ds[0]["pho_id"]) this.diachi.phokh_id = ds[0]["pho_id"];
          else this.diachi.phokh_id = 0;

          if (ds[0]["ap_id"]) this.diachi.apkh_id = ds[0]["ap_id"];
          else this.diachi.apkh_id = 0;

          if (ds[0]["khu_id"]) this.diachi.khukh_id = ds[0]["khu_id"];
          else this.diachi.khukh_id = 0;

          if (ds[0]["dacdiem_id"])
            this.diachi.dacdiemkh_id = ds[0]["dacdiem_id"];
          else this.diachi.dacdiemkh_id = 0;
          this.diachi.sonhakh = ds[0]["sonha"];
          this.diachiTB = ds[0];
        }

        if (kieu == 2) {
          if (ds[0]["diachi_id"]) this.diachi.diachitt_id = ds[0]["diachi_id"];
          else this.diachi.diachitt_id = 0;

          if (ds[0]["tinh_id"]) this.diachi.tinhtt_id = ds[0]["tinh_id"];
          else this.diachi.tinhtt_id = 0;

          this.diachi.quantt_id = ds[0]["quan_id"];
          this.diachi.phuongtt_id = ds[0]["phuong_id"];
          if (ds[0]["pho_id"]) this.diachi.phott_id = ds[0]["pho_id"];
          else this.diachi.phott_id = 0;

          if (ds[0]["ap_id"]) this.diachi.aptt_id = ds[0]["ap_id"];
          else this.diachi.aptt_id = 0;

          if (ds[0]["khu_id"]) this.diachi.khutt_id = ds[0]["khu_id"];
          else this.diachi.khutt_id = 0;

          if (ds[0]["dacdiem_id"])
            this.diachi.dacdiemtt_id = ds[0]["dacdiem_id"];
          else this.diachi.dacdiemtt_id = 0;
          this.diachi.sonhatt = ds[0]["sonha"];

          //Địa chỉ báo cước
          if (ds[0]["diachi_id"]) this.diachi.diachibc_id = ds[0]["diachi_id"];
          else this.diachi.diachibc_id = 0;

          if (ds[0]["tinh_id"]) this.diachi.tinhbc_id = ds[0]["tinh_id"];
          else this.diachi.tinhbc_id = 0;

          if (ds[0]["quan_id"]) this.diachi.quanbc_id = ds[0]["quan_id"];
          else this.diachi.quanbc_id = 0;

          if (ds[0]["phuong_id"]) this.diachi.phuongbc_id = ds[0]["phuong_id"];
          else this.diachi.phuongbc_id = 0;

          if (ds[0]["pho_id"]) this.diachi.phobc_id = ds[0]["pho_id"];
          else this.diachi.phobc_id = 0;

          if (ds[0]["ap_id"]) this.diachi.apbc_id = ds[0]["ap_id"];
          else this.diachi.apbc_id = 0;

          if (ds[0]["khu_id"]) this.diachi.khubc_id = ds[0]["khu_id"];
          else this.diachi.khubc_id = 0;

          if (ds[0]["dacdiem_id"])
            this.diachi.dacdiembc_id = ds[0]["dacdiem_id"];
          else this.diachi.dacdiembc_id = 0;
          this.diachi.sonhabc = ds[0]["sonha"];
          this.diachiTT = ds[0];
          this.dataDiachikh = ds[0];
        }

        if (kieu == 3) {
          this.diachiHD = ds[0];
          this.diachi.diachitb_id = ds[0]["diachi_id"];
          if (ds[0]["tinh_id"]) this.diachi.tinhtb_id = ds[0]["tinh_id"];
          else this.diachi.tinhtb_id = 0;

          if (ds[0]["quan_id"]) this.diachi.quantb_id = ds[0]["quan_id"];
          else this.diachi.quantb_id = 0;

          if (ds[0]["phuong_id"]) this.diachi.phuongtb_id = ds[0]["phuong_id"];
          else this.diachi.phuongtb_id = 0;

          if (ds[0]["pho_id"]) this.diachi.photb_id = ds[0]["pho_id"];
          else this.diachi.photb_id = 0;

          if (ds[0]["ap_id"]) this.diachi.aptb_id = ds[0]["ap_id"];
          else this.diachi.aptb_id = 0;

          if (ds[0]["khu_id"]) this.diachi.khutb_id = ds[0]["khu_id"];
          else this.diachi.khutb_id = 0;

          if (ds[0]["dacdiem_id"])
            this.diachi.dacdiemtb_id = ds[0]["dacdiem_id"];
          else this.diachi.dacdiemtb_id = 0;
          this.diachi.sonhatb = ds[0]["sonha"];

          /// Tuấn thêm code lấy địa chỉ lắp đặt cho thuê bao -- Ngày 01/08/2010
          if (ds[0]["diachild_id"])
            this.diachi.diachild_id = ds[0]["diachild_id"];
          else this.diachi.diachild_id = 0;
          //====================================
          if (ds[0].block) this.diachi.blocktb = ds[0]["block"];
          if (ds[0].tang) this.diachi.tangtb = ds[0]["tang"];
          if (ds[0].phong) this.diachi.sophongtb = ds[0]["phong"];
          if (ds[0].mota) this.diachi.motadctb = ds[0]["mota"];
          //====================================

          if (ds[0]["tinhld_id"]) this.diachi.tinhld_id = ds[0]["tinhld_id"];
          else this.diachi.tinhld_id = 0;

          if (ds[0]["quanld_id"]) this.diachi.quanld_id = ds[0]["quanld_id"];
          else this.diachi.quanld_id = 0;

          if (ds[0]["phuongld_id"])
            this.diachi.phuongld_id = ds[0]["phuongld_id"];
          else this.diachi.phuongld_id = 0;

          if (ds[0]["phold_id"]) this.diachi.phold_id = ds[0]["phold_id"];
          else this.diachi.phold_id = 0;

          if (ds[0]["apld_id"]) this.diachi.apld_id = ds[0]["apld_id"];
          else this.diachi.apld_id = 0;

          if (ds[0]["khuld_id"]) this.diachi.khuld_id = ds[0]["khuld_id"];
          else this.diachi.khuld_id = 0;

          if (ds[0]["dacdiemld_id"])
            this.diachi.dacdiemld_id = ds[0]["dacdiemld_id"];
          else this.diachi.dacdiemld_id = 0;
          //====================================
          if (ds[0]["block_ld"]) this.diachi.blockld = ds[0]["block_ld"];
          if (ds[0]["tang_ld"]) this.diachi.tangld = ds[0]["tang_ld"];
          if (ds[0]["phong_ld"]) this.diachi.sophongld = ds[0]["phong_ld"];
          if (ds[0]["mota_ld"]) this.diachi.motadcld = ds[0]["mota_ld"];
          //====================================

          this.diachi.sonhald = ds[0]["sonha_ld"];
        }
      }
    },
    async HT_ThongTin_Thanhtoan(vthuebao_id, vkieu_yc) {
      let rs = await this.axios.post("/web-hopdong/thanhly/thanh_ly_map_id", {
        id_neo: "thanhtoan_id",
        in_table: "db_thuebao",
        in_dk: "where thuebao_id=" + vthuebao_id,
      });

      if (rs.data && rs.data.data && rs.data.data != "-1") {
        let vthanhtoan_id = rs.data.data;

        let rs_madaidien = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_thuebao_theo_thanhtoan_id",
          {
            params: { thanhToanId : vthanhtoan_id}
          }
        );

        console.log(rs_madaidien);

        let ds = await this.axios.post(
          "/web-hopdong/phattrienthuebao/lay_ds_dbtt_theo_ttid",
          {
            thanhtoan_id: vthanhtoan_id,
          }
        );

        if (ds.data && ds.data.data) {
          let ttthanhtoan = ds.data.data;
          if (ttthanhtoan.data && ttthanhtoan.data.length > 0) {
            ttthanhtoan.columns.forEach((value, key) => {
              if (value.column_name == "MA_TT") {
                this.$set(this.form, "ma_tt_cu", ttthanhtoan.data[0][key]);
              } else
                this.$set(
                  this.form,
                  value.column_name.toLowerCase(),
                  ttthanhtoan.data[0][key]
                );
            });
          }

          let thongtindc = await this.axios.get(
            "web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid",
            { params: { dbttid: this.form.thuebao_id } }
          );
          this.HienThiTTDiaChi(thongtindc.data ? thongtindc.data.data : [], 2);
          this.HienThi_NVQL_AM_DB_HD(1, this.form.thanhtoan_id);
        }
      }
    },
    async HienThi_NVQL_AM_DB_HD(kieu, id) {
      let data = await this.axios.post(
        "web-hopdong/lapdatmoi_bak/sp_lay_nhanvien_ql",
        {
          id: id,
          kieu: kieu,
        }
      );

      if (data.data && data.data.data && data.data.data.length > 0) {
        this.$set(
          this.form,
          "nhanvien_ql_am_id",
          data.data.data[0].nhanvien_id
        );
      }
    },
    async LayTongTien_HDTB() {
      this.$set(this.form, "tongtienhd", 0);
      this.$set(this.form, "tongtienvat", 0);
      this.ds_tien_khoanmuc.forEach((item) => {
        this.form.tongtienhd += Number(item.tien);
        this.form.tongtienvat += Number(item.vat);
      });
    },
    async HienThiTTHopDongTT(data) {
      if (data.length > 0) {
        let arr = [
          "hdtt_id",
          "ma_tt",
          "thanhtoan_id",
          "matb_dd",
          "ten_tt",
          "diachi_tt",
          "diachi_bc",
          "diachi_ct",
          "DV_QHNS",
          "MACHUONG",
          "MN_KT",
          "MA_NDKT",
          "MN_NSNN",
          "TIEN_DM",
          "hdmig_id",
          "mst",
          "stk",
          "nganhang_id",
          "ghichu",
          "donvi_id",
          "nhanvien_id",
          "ma_nv",
          "tuyenthu_id",
          "ma_tuyen",
        ];
        arr.forEach((item) =>
          this.$set(this.form, item, data[0][item] ? data[0][item] : 0)
        );
      }

      let thongtindc = await this.axios.get(
        "web-hopdong/hopdong/lay_ds_diachi_theo_hdtt",
        { params: { hdttId: this.form.hdtt_id } }
      );
      if (thongtindc.data && thongtindc.data.data) {
        let temp = thongtindc.data.data[0] ? thongtindc.data.data[0] : {};
        let diachiArr = [
          {
            tinh_id: temp.tinhId ? temp.tinhId : 0,
            quan_id: temp.quanId ? temp.quanId : 0,
            phuong_id: temp.phuongId ? temp.phuongId : 0,
            ap_id: temp.apId ? temp.apId : 0,
            pho_id: temp.phoId ? temp.phoId : 0,
            khu_id: temp.khuId ? temp.khuId : 0,
            diachi_id: temp.diaChiId ? temp.diaChiId : 0,
            sonha: temp.soNha ? temp.soNha : "",
            dacdiem_id: temp.dacDiemId ? temp.dacDiemId : 0,
          },
        ];
        this.HienThiTTDiaChi(diachiArr, 2);
      }

      this.HienThi_NVQL_AM_DB_HD(2, this.form.hdtt_id);
    },
    async HienThiTTHopDongTB(data) {
      if (data.length > 0) {
        let arr = [
          "hdtb_id",
          "dichvuvt_id",
          "thuebao_id",
          "ma_tb",
          "ten_tb",
          "diachi_ld",
          "diachi_tb",
          "loaitb_id",
          "kieuld_id",
          "doituong_id",
          "donvi_id",
          "status",
          "email",
          "quytrinh_id",
          "goicuoc_id",
          "hdtt_id",
        ];

        arr.forEach((item) => {
          if (item == "dichvuvt_id")
            this.$set(this.form, item, String(data[0][item]));
          else this.$set(this.form, item, data[0][item]);
        });

        let dataBD = await this.axios.post(
          "web-hopdong/khoiphucthanhly/lay_biendong_theo_hdtb_id",
          {
            hdtb_id: this.form.hdtb_id,
          }
        );

        if (dataBD.data && dataBD.data.data && dataBD.data.data.length > 0) {
          this.$set(this.form, "ten_tt", dataBD.data.data[0]["ten_tt"]);
          this.$set(this.form, "diachi_tt", dataBD.data.data[0]["diachi_tt"]);
        }

        let dsTien = await this.axios.post(
          "web-hopdong/khoiphucthanhly/lay_tienhd_km_theo_hdtb",
          {
            hdtb_id: this.form.hdtb_id,
          }
        );

        this.ds_tien_khoanmuc = dsTien.data ? dsTien.data.data : [];

        let thuebaoDv = await this.axios.post(
          "web-hopdong/hopdong/lay_ds_thuebao_dichvu",
          {
            hdtbId: this.form.hdtb_id,
            thueBaoId: this.form.thuebao_id,
            loaiTbId: this.form.loaitb_id,
            kieuId: 1,
          }
        );

        let thongtindc = await this.axios.post(
          "web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_hdtb_id",
          { in_hdtb_id: this.form.hdtb_id }
        );
        this.HienThiTTDiaChi(thongtindc.data ? thongtindc.data.data : [], 3);

        this.m_dsThueBao_DichVu = thuebaoDv.data ? thuebaoDv.data.data : [];

        this.LayTongTien_HDTB();
      }
    },
    async HienThiDanhSacHDTT(data) {
      this.options.dsHopdong = [];
      if (data && data.length > 0) {
        this.options.dsHopdong = data;
        this.button.xoatt = true;
      } else {
        this.button.xoatt = false;
      }
    },
    async btnThuebao_click() {
      if (this.form.ma_tb) {
        if (this.form.khachhang_id_dtg) {
          this.$refs.tracuuthuebao.showModal(
            this.form.khachhang_id_dtg,
            this.form.dichvuvt_id,
            this.form.ma_gd
          );
        } else {
          let data = await this.axios.post(
            "web-hopdong/khoiphucthanhly/lay_biendong_theo_hdtb_id",
            {
              hdtb_id: this.form.hdtb_id,
            }
          );
          if (data.data && data.data.data.length > 0) {
            this.$refs.tracuuthuebao.showModal(
              data.data.data[0].khachhang_id,
              this.form.dichvuvt_id,
              this.form.ma_gd
            );
          } else {
            this.$toast.error(
              "Chưa có thông tin về khách hàng Tách ghép có mã thuê bao: " +
                this.form.ma_tb +
                "nKiểm tra lại !"
            );
          }
        }
      } else {
        this.$toast.error("Bạn chưa nhập mã thuê bao!!!");
      }
    },
    openDiachi() {
      this.$refs.ChonDiaChiModal.showModal();
    },
    setMaGD(data) {
      if (data.ma_gd) this.form.ma_gd = data.ma_gd;
    },
    chon_magd() {
      this.$refs.modal_magd.showModal();
    },
    async magd_enter() {
      let rs = await this.axios.post(
        "web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd",
        {
          in_ma_gd: this.form.ma_gd,
          in_loaihd_id: 10,
          in_donvi_id: 0,
          in_tthd_id: 1,
          in_nhanvien_id: 0,
          in_donvi_dl_id: 0,
        }
      );

      this.HienThiTTHopDongKH(rs.data.data);
    },
    async frmTachNhapTB_Load() {
      try {
        let rs = null;
        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_lay_tienkhoanmuc_loaihd",
          { loaihd_id: 10 }
        );
        this.ds_tien_khoanmuc = rs.data ? rs.data.data : null;

        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_lay_dichvu_vt_theo_nd",
          { nguoidung_id: 0 }
        );
        this.options.dichvuVT = rs.data ? rs.data.data : [];
        if (this.options.dichvuVT.length > 0) {
          this.form.dichvuvt_id = this.options.dichvuVT[0].dichvuvt_id;
        }

        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_lay_doituong_tb",
          { nguoidung_id: this.$root.token.getNhanVienID() }
        );
        this.options.doituong = rs.data ? rs.data.data : [];
        if (this.options.doituong.length > 0) {
          this.doituong_id = this.options.doituong[0].doituong_id;
        }

        rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_NGANHANG");
        this.options.nganhang = rs.data ? rs.data.data : [];
        if (this.options.nganhang.length > 0) {
          this.form.nganhang_id = this.options.nganhang[0].NGANHANG_ID;
        }

        rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_HINHTHUC_TT");
        this.options.hinhthucTT = rs.data ? rs.data.data : [];
        if (this.options.hinhthucTT.length > 0) {
          this.form.httt_id = this.options.hinhthucTT[0].HTTT_ID;
        }

        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_ht_donvi_loaidv_combobox",
          { donvi_id: 0, loaidv_id: 2 }
        );
        this.options.loaiDV = rs.data ? rs.data.data : [];
        if (this.options.loaiDV.length > 0) {
          this.donvi_id = this.options.loaiDV[0].donvi_id;
        }
      } catch (err) {
        this.$toast.error("Đã có lỗi xảy ra");
      }
    },
    async getLoaihinhTb() {
      let rs = await this.axios.get(
        "web-quantri/danhmuc-chung/CSS_LOAIHINH_TB",
        { params: { dichvuvt_id: this.form.dichvuvt_id } }
      );
      let data = rs.data ? rs.data.data : [];
      this.options.loaihinhTB = data.filter(
        (item) => item.DICHVUVT_ID == this.form.dichvuvt_id
      );
      if (this.options.loaihinhTB.length > 0) {
        this.form.loaitb_id = this.options.loaihinhTB[0].LOAITB_ID;
      }
    },
    async getKieuYeucau() {
      let rs = await this.axios.post(
        "web-hopdong/khoiphucthanhly/lay_ds_kieu_ld",
        { loaitb_id: this.form.loaitb_id, loaihd_id: 10 }
      );
      this.options.kieuYC = rs.data ? rs.data.data : [];
      if (this.options.kieuYC.length > 0) {
        this.form.kieuld_id = this.options.kieuYC[0].kieuld_id;
      }
    },
    async HienThiDS_HDTT_CBO() {
      let rs = await this.axios.get("web-hopdong/hopdong/lay_ds_ma_thanhtoan", {
        params: {
          hdkhId: this.form.hdkh_id,
        },
      });
      if (rs.data && rs.data.data) {
        this.$set(this.options, "thanhtoan", rs.data.data);
        this.$set(this.form, "thanhtoan_id", this.options.thanhtoan[0].ID);
      }
    },
    async layThongtin() {
      let rs = await this.axios.post(
        "web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb",
        {
          ma_tb: this.form.ma_tb ? this.form.ma_tb : "",
          in_loaihd_id: 10,
          in_donvi_id: 0,
          in_tthd_id: 1,
          in_nhanvien_id: "",
          in_donvi_dl_id: 0,
          in_dichvuvt_id: this.form.dichvuvt_id,
          in_loaitb_id: this.form.loaitb_id ? this.form.loaitb_id : 0,
        }
      );

      if (rs.data && rs.data.data.length > 0)
        this.HienThiTTHopDongKH(rs.data.data);
      else this.HienThiTT_DanhBa(this.form.ma_tb);
    },
    async HienThiTT_DanhBa(ma_tb) {
      let data = await this.axios.post(
        "web-hopdong/hopdong/lay_danhba_theo_matb",
        {
          dichVuVtId: this.form.dichvuvt_id,
          maTb: ma_tb,
          donViDlId: 0,
        }
      );
      if (data.data && data.data.data) {
        let dsThuebao = data.data.data;
        if (!dsThuebao.ky_hd) {
          this.$toast.error(
            "Khách hàng có mã thuê bao: " +
              ma_tb +
              " chưa ký lại hợp đồng gốc nĐề nghị ký lại hợp đồng gốc"
          );
          return false;
        }

        if (dsThuebao.trangthaitb_id != 1 && dsThuebao.trangthaitb_id != 2) {
          this.$toast.error(
            "Thuê bao có mã: " +
              dsThuebao.ma_tb +
              " đang ở trạng thái: " +
              dsThuebao.trangthai_tb +
              "nBạn không thể thực hiện Tách nhập thuê bao cho thuê bao này"
          );
          return false;
        }

        if (this.form.khachhang_id) {
          if (dsThuebao.khachhang_id != this.form.khachhang_id) {
            if (this.form.hdkh_id) {
              this.$toast.error(
                "Mã thuê bao vừa nhập không thuộc khách hàng đang thực hiện tách nhập thuê bao. Bạn hãy kiểm tra lại !"
              );
            }
          } else {
            let arr = [];
            if (this.form.kieuld_id == 19) {
              arr = [
                "ma_kh",
                "ma_hd",
                "khachhang_id",
                "ten_kh",
                "diachi_kh",
                "ngaylap_hd",
                "loaikh_id",
                "ngaycap",
                "ngay_sn",
                "gioitinh",
              ];

              this.HT_ThongTin_Thanhtoan(dsThuebao.thuebao_id, 19);
            } else {
              arr = [
                "ma_kh",
                "ma_hd",
                "khachhang_id",
                "ten_kh",
                "diachi_kh",
                "ngaylap_hd",
                "loaikh_id",
                "gioitinh",
              ];
              this.HT_ThongTin_Thanhtoan(dsThuebao.thuebao_id, 167);
            }

            arr = arr.concat([
              "ma_tb",
              "ten_tb",
              "diachi_tb",
              "email_tb",
              "doituong_id",
              "diachi_tb",
              "email_tb",
              "doituong_id",
              "loaitb_id",
              "donviql_id",
              "diachi_ld",
              "thuebao_id",
            ]);

            if (this.form.dichvuvt_id == 2) {
              arr.push("goicuoc_id");
            }

            arr.forEach((item) => {
              this.$set(this.form, item, dsThuebao[item]);
            });

            if (this.form.ma_kh) {
              this.$set(this.form, "ma_kh_cu", this.form.ma_kh);
            }

            if (this.form.ma_tb) {
              this.$set(this.form, "ma_tb_cu", this.form.ma_tb);
            }

            console.log("lahdkashdhasjkl");

            let thongtindc = await this.axios.get(
              "web-hopdong/hopdong/lay_ds_diachi_theo_khachhang",
              { params: { khachHangId: this.form.khachhang_id } }
            );
            this.HienThiTTDiaChi(
              thongtindc.data ? thongtindc.data.data : [],
              1
            );
            this.HienThiTTDBMoRong(this.form.thuebao_id, this.form.dichvuvt_id);
            this.LAY_DS_SUDUNG_DVGT(this.form.thuebao_id);
          }
        } else {
          let arr = [];
          if (this.form.kieuld_id == 19) {
            arr = [
              "ma_kh",
              "ma_hd",
              "khachhang_id",
              "ten_kh",
              "diachi_kh",
              "ngaylap_hd",
              "loaikh_id",
              "ngaycap",
              "ngay_sn",
              "gioitinh",
            ];

            this.HT_ThongTin_Thanhtoan(dsThuebao.thuebao_id, 19);
          } else {
            arr = [
              "ma_kh",
              "ma_hd",
              "khachhang_id",
              "ten_kh",
              "diachi_kh",
              "ngaylap_hd",
              "loaikh_id",
              "gioitinh",
            ];
            this.HT_ThongTin_Thanhtoan(dsThuebao.thuebao_id, 167);
          }

          arr = arr.concat([
            "ma_tb",
            "ten_tb",
            "diachi_tb",
            "email_tb",
            "doituong_id",
            "diachi_tb",
            "email_tb",
            "doituong_id",
            "loaitb_id",
            "donviql_id",
            "diachi_ld",
            "thuebao_id",
          ]);

          if (this.form.dichvuvt_id == 2) {
            arr.push("goicuoc_id");
          }

          arr.forEach((item) => {
            this.$set(this.form, item, dsThuebao[item]);
          });

          if (this.form.ma_kh) {
            this.$set(this.form, "ma_kh_cu", this.form.ma_kh);
          }

          if (this.form.ma_tb) {
            this.$set(this.form, "ma_tb_cu", this.form.ma_tb);
          }

          let thongtindc = await this.axios.get(
            "web-hopdong/hopdong/lay_ds_diachi_theo_khachhang",
            { params: { khachHangId: this.form.khachhang_id } }
          );
          this.HienThiTTDiaChi(thongtindc.data ? thongtindc.data.data : [], 1);
          this.HienThiTTDBMoRong(this.form.thuebao_id, this.form.dichvuvt_id);
          this.LAY_DS_SUDUNG_DVGT(this.form.thuebao_id);
        }
        let thongtindcdbtt = await this.axios.get(
          "web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid",
          { params: { dbttid: this.form.thuebao_id } }
        );
        this.HienThiTTDiaChi(
          thongtindcdbtt.data ? thongtindcdbtt.data.data : [],
          3
        );
      } else {
        this.$toast.error(
          "Kiểm tra lại mã thuê bao tìm kiếm, dịch vụ viễn thông !"
        );
      }
    },
    async HienThi_NVQL_AM_HTTT(
      i_loaikh_id,
      i_phuong_id,
      i_pho_id,
      i_ap_id,
      i_khu_id,
      vhttt_id
    ) {
      this.axios.post("web-hopdong/tachnhapthuebao/lay_ds_donviql_v2", {
        phuong_id: i_phuong_id,
        pho_id: i_pho_id,
        ap_id: i_ap_id,
        khu_id: i_khu_id,
        kieu: 0,
        loaikh_id: i_loaikh_id,
        httt_id: vhttt_id,
      });
    },
    async HienThiTTHopDongKH(data) {
      if (data.length > 0) {
        let arr = [
          "hdkh_id",
          "ma_gd",
          "loaikh_id",
          "nganhnghe_id",
          "loaigt_id",
          "ngay_sn",
          "gioitinh",
          "ngaycap",
          "ma_hd",
          "ma_kh",
          "quoctich_id",
          "khachhang_id",
          "doanhthu",
          "dtn",
          "ten_kh",
          "diachi_kh",
          "ngaylap_hd",
          "ngay_yc",
        ];

        arr.forEach((item) => this.$set(this.form, item, data[0][item]));

        let dsHopdong = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
          { params: { hdKhId: this.form.hdkh_id } }
        );

        let thongtindc = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_diachi_theo_hdkh",
          { params: { hdKhId: this.form.hdkh_id } }
        );
        if (thongtindc.data && thongtindc.data.data) {
          let temp = thongtindc.data.data ? thongtindc.data.data[0] : {};
          let diachiArr = [
            {
              tinh_id: temp.tinhId,
              quan_id: temp.quanId,
              phuong_id: temp.phuongId,
              ap_id: temp.apId,
              pho_id: temp.phoId,
              khu_id: temp.khuId,
              diachi_id: temp.diaChiId,
              sonha: temp.soNha,
              dacdiem_id: temp.dacDiemId,
            },
          ];
          this.HienThiTTDiaChi(diachiArr, 1);
        }
        this.HienThiTTHopDongTT(dsHopdong.data ? dsHopdong.data.data : []);
        this.HienThiDS_HDTT_CBO();
        this.HienThiDanhSacHDTT(dsHopdong.data ? dsHopdong.data.data : []);
        //this.options.dsHopdong = dsHopdong.data ? dsHopdong.data.data : [];
        if (data[0] && data[0].nhanviengt_id) {
          let nhanviengt = await this.axios.post(
            "/web-hopdong/thanhly/thanh_ly_map_id",
            {
              id_neo: "ten_nv",
              in_table: "nhanvien",
              in_dk: "where nhanvien_id=" + data[0].nhanviengt_id,
            }
          );

          if (nhanviengt.data && nhanviengt.data.data) {
            this.$set(this.form, "nhanvien_gt", nhanviengt.data.data);
          }
        }
        let dsHopdongTB = await this.axios.get(
          "web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hopdong_tb_theo_id/" +
            this.form.hdkh_id
        );

        this.HienThiTTHopDongTB(dsHopdongTB.data ? dsHopdongTB.data.data : []);

        this.HT_DanhSachTB();
        this.SetButton(3);
      } else {
        this.SetButton(0);
      }
    },
    async HienThi_NVQL_AM_DB_HD(
      i_kieu,
      id // Kieu = 1 : danh ba, kieu = 2 : hop dong
    ) {
      try {
        let ds_tt = await this.axios.post(
          "web-hopdong/lapdatmoi/sp_lay_nhanvien_ql",
          {
            kieu: i_kieu,
            id: id,
          }
        );
        if (ds_tt.data && ds_tt.data.data && ds_tt.data.data.length > 0) {
          this.$set(this.form, "ten_nv_ql", ds_tt.data.data[0]["ten_nv"]);
          this.$set(
            this.form,
            "nhanvien_ql_am_id",
            ds_tt.data.data[0]["nhanvien_id"]
          );
        } else {
          this.$set(this.form, "ten_nv_ql", "");
          this.$set(this.form, "nhanvien_ql_am_id", 0);
        }
      } catch (err) {
        this.$toast.error("Có lỗi : " + err.message);
      }
    },
    async btnHTHS_Click() {
      let rs = await this.axios.post(
        "/hoanthien_hs_tachnhap_tbao/" + this.form.hdkh_id
      );
      this.SetButton(0);
      this.$toast.success("Đã hoàn thiện hồ sơ");
    },
    async HT_DanhSachTB() {
      let rs = await this.axios.get(
        "/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id",
        { params: { hdkhId: this.form.hdkh_id } }
      );
      this.options.dsThuebao = rs.data ? rs.data.data : [];
      let tongtien = 0;
      if (this.options.dsThuebao.length > 0) {
        this.options.dsThuebao.forEach((item) => {
          tongtien += Number(item.tongtien);
        });
        this.button.xoatb = true;
        this.button.themtt = true;
      } else {
        this.button.xoatb = false;
      }

      this.$set(this.form, "tongtienhd", tongtien);
    },
    LayTienTheoKhoanMuc() {
      if (!this.form.loaitb_id || !this.form.kieuld_id) {
        return false;
      }

      let ds = this.axios.post(
        "web-hopdong/tachnhapthuebao/sp_lay_tienkhoanmuc_loaihd",
        { loaihd_id: 10 }
      );
      if (ds.data && ds.data.data && ds.data.data.length > 0)
        this.HienThiTienLapDat(ds.data.data);
    },
    SetButton(kieu) {
      this.button.nhapmoi = false;
      this.button.ghilai = false;
      this.button.xoa = false;
      this.button.huybo = false;
      this.button.themtt = false;
      this.button.themtb = false;
      this.button.themtb_ds = false;
      this.button.thanhtoan = false;
      this.button.HTHS = false;
      this.button.inhd = false;
      this.button.giaophieu = false;
      this.button.xoatb = false;
      this.button.xoatt = false;
      this.button.inyc = false;
      this.options.thanhtoan = []
      

      if (kieu == -1) {
        //Bat dau
        this.button.ghilai = true;
        this.button.huybo = true;
        this.button.xoa = false;
        this.button.themtt = false;
      }

      if (kieu == 0) {
        //Bat dau

        this.button.nhapmoi = true;
        this.button.themtt = false;
        this.button.xoa = false;
        this.Clear();
      }

      if (kieu == 1) {
        //Them moi
        this.button.ghilai = true;
        this.button.huybo = true;
        this.button.themtt = false;
        this.button.xoa = false;
        this.Clear();
      }

      if (kieu == 2) {
        //Huy
        this.button.nhapmoi = true;
        this.button.xoa = true;
        this.Clear();
      }

      if (kieu == 3) {
        //Edit

        this.button.nhapmoi = true;
        this.button.xoa = true;
        this.button.ghilai = true;
        this.button.huybo = true;
        this.button.themtb = true;
        this.button.themtb_ds = true;
        this.button.thanhtoan = true;
        this.button.inyc = true;
        this.button.giaophieu = true;
        this.button.inhd = true;
      }
    },
    HienThiTienLapDat(data) {},
    btnTaoMIG_Click() {
      if (!this.form.hdkh_id || this.form.hdkh_id == 0) {
        this.$toast.error("Chưa có thông tin về hợp đồng khách hàng!");
        return;
      }

      var obj = {};
      obj.hdkh_id = this.form.hdkh_id;
      obj.khachhang_id = this.form.khachhang_id;
      obj.hdtt_id = this.form.hdtt_id;
      obj.thanhtoan_id = this.form.thanhtoan_id;
      obj.fmig_id = this.form.mig_id ? this.form.mig_id : 0;
      obj.fhdmig_id = this.form.hdmig_id ? this.form.hdmig_id : 0;
      obj.matt = this.form.ma_tt;
      obj.tentt = this.form.ten_tt;
      obj.ma_tt = this.form.ma_tt;
      obj.ten_tt = this.form.ten_tt;

      this.popupComponent = () =>
        import("../InstallNewSubs/Popups/popupChonMaInGhep");
      this.popupComponentName = "popupChonMaInGhep";
      this.popupComponentData = obj;
      this.Popup("popupComponents");
    },
    popupTimNVThuCuoc() {
      //this.$refs.refTimNVThuCuoc.openDialog()
      this.$refs.dlgTimKiemNVTC.openDialog();
      //this.$toast.error('Chức năng chưa được phát triển')
    },
    getNVTCProcess(value) {
      //this.params.quanHuyen_Selected = Number(value.quanId);
      //this.params.nhanVienXLMoi_Selected = Number(value.nhanVienTC_Id);
      this.$set(this.form, "nhanvientc_id", value.rowData.NHANVIEN_ID);
      this.$set(this.form, "ma_nv", value.maNVTC);
      this.$set(this.form, "tuyenthu_id", value.rowData.TUYENTHU_ID);
      this.$set(this.form, "matuyen", value.rowData.MA_TUYENTHU);
    },
  },
};
</script>
<style>
.non-ative a {
  color: #d3d3d3 !important;
}
</style>