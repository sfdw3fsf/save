<template src="./frmDangKyGoiDaDVLM.html"></template>
<script>
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import SearchContractModal from "@/modules/search/subscriber/SearchContract/SearchContract.vue";
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import searchAccount from "@/modules/search/subscriber/SearchAccount";
import QLCongVanModal from "@/modules/admin/category/MultiServicePackage/QLCongVanModal.vue";
import moment from "moment";
var rs;
import { DatabaseConstants, TrangThaiHD, LoaiHopDong, LoaiHinhTB } from "@/const/enums";
export default {
  data() {
    return {
      dtList: [],
      dtListTB: [],
      dtThueBao: [],
      dsHDKH: [],
      dichvuvt_id: 0,
      hdkh_id: 0,
      nhomtb_id: 0,
      hdtb_id: 0,
      donvi_id: 0,
      khachhang_id: 0,
      thuebao_id: 0,
      goi_id: 0,
      khlon_id: 0,
      ten_tb: "",
      diachi_ld: "",
      loaitb_id: 0,
      vloainha_id: "",
      doituong_id: 0,
      dtpNgayLHD: 0,
      huonggiaotn_id: 0,
      quytrinh_id: 0, //-- Huong giao yeu cau dang ky goi da dv
      modalSearch: {
        loaihd_id: 0,
        tthd_id: 0,
        ngay_yc: moment().format("DD/MM/YYYY"),
      },
      self: this,
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      dikem: false,
      userComponentModule: null,
      popupComponentEvts: {
        "form-close": this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        // paymentcreated: this.paymentcreated,
      },
      popupComponentName: "",
    };
  },
  components: {
    DataGridCustom,
    SearchContractModal,
    Select2Ext,
    VueDateExt,
    searchAccount,
    QLCongVanModal,
  },
  methods: {
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents_frmDKGoiDaDV");
      }
      try {
        this.Loading = true;
        switch (this.popupComponentName) {
          case "SearchAccount":
            console.log("SearchAccount", val);
            console.log(val.thuebao, "val.thuebao");
            if (val.thuebao)
              this.$refs.cboDichVuVT.value = Number(val.thuebao.dichvuvt_id);
            if (val.ma_tb) this.$refs.txtMaTB.value = val.ma_tb;
            if (this.$refs.txtMaTB.value) {
              this.searchData = false; // Ko bat popup nua
              this.txtMaTB_KeyPress_1();
            }
            break;
          default:
        }
      } catch (err) {
        console.log(err);
      } finally {
        this.ClosePopup("popupComponents_frmDKGoiDaDV");
        this.Loading = false;
      }
      // this.$emit('close', this.PhieuId)
      // this.$emit('closeChuyenTo', this.ChapNhan)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents_frmDKGoiDaDV");
      }
      try {
        this.Loading = true;
        switch (this.popupComponentName) {
          case "SearchAccount":
            console.log("SearchAccount", val);
            if (val.thuebao)
              this.$refs.cboDichVuVT.value = Number(val.thuebao.dichvuvt_id);
            if (val.ma_tb) this.$refs.txtMaTB.value = val.ma_tb;
            this.$refs.txtMaTB.focus();
            break;
        }
      } catch (err) {
        console.log(err);
      } finally {
        this.ClosePopup("popupComponents_frmDKGoiDaDV");
        this.Loading = false;
      }
    },
    async showDialog(ma_gd, vloainha_id = null) {
      this.loading(true);
      this.vloainha_id = vloainha_id;
      await this.frmDangKyGoiDaDVLM(ma_gd);
      await this.frmDangKyGoiDaDV_Load();
      this.$refs.frmDangKyGoiDaDVLM.show();
      this.loading(false);
    },
    frmDangKyGoiDaDVLM(
      ma_gd //SonNMP fix nhiều constructor 26/02/2018
    ) {
      //InitializeComponent();
      this.$refs.txtMaGD.value = ma_gd;
    },
    async frmDangKyGoiDaDV_Load() {
      return new Promise(async (resolve, reject) => {
        try {
          rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_DICHVU_VT");
          this.$refs.cboDichVuVT.dataSource = rs.data.data.filter((item) =>
            [1, 2, 4, 8, 9, 10, 11, 12, 13, 14, 15, 16].includes(item.DICHVUVT_ID)
          );
          this.$refs.cboDichVuVT.dataValueField = "DICHVUVT_ID";
          this.$refs.cboDichVuVT.dataTextField = "TEN_DVVT";
          if (this.$refs.cboDichVuVT.dataSource[0])
            this.$refs.cboDichVuVT.value = this.$refs.cboDichVuVT.dataSource[0].DICHVUVT_ID;

          rs = await this.axios.get("web-hopdong/hopdong/fn_lay_tt_goi_dadvlm", {
            params: {
              vloainha_id: this.vloainha_id,
            },
          });

          let dtGoi = rs.data.data;
          this.$refs.cboTenGoi_DV.HT_COMBOBOX(dtGoi, "ten_goi", "goi_id");

          rs = await this.axios.post("web-hopdong/lapdatmoi/sp_hieuluc_goidadv", {});
          this.$refs.cboHieuLuc.HT_COMBOBOX(
            rs.data ? rs.data.data : [],
            "maghep",
            "maghep_id"
          );

          rs = await this.axios.post("web-hopdong/khoiphucthanhly/lay_ds_kieu_ld", {
            loaihd_id: LoaiHopDong.DANGKY_GOI_DADV,
            loaitb_id: 0,
          });

          this.$refs.cboKieuLD.HT_COMBOBOX(rs.data.data, "ten_kieuld", "kieuld_id");
          // bangts.HT_COMBO_HIEULUC(cboHieuLuc);

          // bangts.HT_Kieu_LD_Combobox(cboKieuLD, 0, LoaiHopDong.DANGKY_GOI_DADV);

          this.SetButton(-1);
          if (this.$refs.txtMaGD.value != "") {
            rs = await this.axios.post("web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd", {
              in_ma_gd: this.$refs.txtMaGD.value,
              in_loaihd_id: LoaiHopDong.DAT_MOI,
              in_donvi_id: this.$root.token.getDonViID(),
              in_tthd_id: TrangThaiHD.MOI,
              in_nhanvien_id: this.$root.token.getNhanVienID(),
              in_donvi_dl_id: 0,
            });
            this.HienThiTTHopDongKH(rs.data.data);
          }
        } catch (err) {
          console.log(err);
          this.$toast.error("Có lỗi: " + err.message);
        }
        resolve(true);
      });
    },

    tsbtnNhapMoi_Click() {
      this.SetButton(1);
    },

    tsbtnGhiLai_Click() {
      this.CapNhat();
    },
    getColTemplateGhiChu(parent) {
      return function () {
        return {
          template: {
            template: `<input type="checkbox" v-model="model">`,
            data() {
              return {
                model: "",
              };
            },
            watch: {
              model: {
                handler() {
                  let index = parent.$refs.dgrListKH.dataSource.findIndex(
                    (item) =>
                      item.MA_TB == this.data.MA_TB && item.HDTB_ID == item.HDTB_ID
                  );
                  parent.$refs.dgrListKH.dataSource[index].CHKNHOM =
                    this.model == true ? 1 : 0;
                  console.log(parent.$refs.dgrListKH.dataSource);
                },
                deep: true,
              },
            },
            mounted() {
              this.model = this.data.CHKNHOM;
            },
          },
        };
      };
    },
    async LayDsThueBao() {
      rs = await this.axios.post(
        "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
        {
          ds_para: JSON.stringify({
            MA_GD: this.$refs.txtMaGD.value,
            MA_KH: this.$refs.txtMaKH.value,
            HDKH_ID: 0,
          }),
        }
      );

      let rs_data = JSON.parse(rs.data.data);
      console.log(rs_data, "rs_data");
      if (rs_data && rs_data.ERROR_CODE && rs_data.ERROR_CODE == 1)
        return rs_data.RESULT.DS_THUEBAO;
      else return [];
    },
    async LayDsThueBaoDaDKy() {
      rs = await this.axios.post(
        "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
        {
          ds_para: JSON.stringify({
            MA_GD: this.$refs.txtMaGD.value,
            MA_KH: this.$refs.txtMaKH.value,
            HDKH_ID: this.hdkh_id,
          }),
        }
      );

      let rs_data = JSON.parse(rs.data.data);

      if (rs_data && rs_data.ERROR_CODE && rs_data.ERROR_CODE == 1)
        return rs_data.RESULT.DS_THUEBAO_DK;
      else return [];
    },
    KiemTraDL_KhachHang() {
      if (this.$refs.txtMaGD.value != "") {
        if (
          laphd.Kiemtra_makh_mtt(
            this.$refs.txtMaGD.value,
            3,
            LoaiHopDong.DANGKY_GOI_DADV
          ) == 0
        ) {
          Message_Box.ShowWarning(
            "Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!"
          );
          this.$refs.txtMaGD.focus();
          return false;
        }
      }
      return true;
    },

    async CapNhat() {
      try {
        if (!this.KiemTraDL_KhachHang()) return;
        let selected = this.$refs.dgrList.getSelectedRecords();
        if (selected.length == 0) {
          this.$toast.error("Không có thuê bao nào trong danh sách thuê bao thuộc gói! ");
          return;
        }
        if (selected[0]["vgoi_id"] != this.$refs.cboTenGoi_DV.value) {
          this.$toast.error("Không được thay đổi tên gói! ");
          return;
        }
        if (selected.length > 1) {
          let hl = this.$refs.cboHieuLuc.value;
          if (hl == 2) {
            hl = Number(this.$refs.txtSoThang.value);
          }
          if (Number(dgrList.SelectedRows[0].Cells["vthang_huong"].value) != hl) {
            this.$toast.error("Không được thay đổi hiệu lực! ");
            return;
          }
        }

        //kt_hieuluc = laphd.Kt_goi_dadv_hieuluc(goi_id, thuebao_id, hdtb_id);
        //if (kt_hieuluc != "1")
        //{
        //    Message_Box.ShowWarning(kt_hieuluc);
        //    return;
        //}

        //string
        TaoDuLieu_HDTB_GOI_DADV(false);
        goida_dv.CapNhat_HopDong(dsHDTBGOI);
        SetButton(3);
        this.$toast.error(
          "Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!"
        );
        // HienThiTTHopDongKH(tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.DAT_MOI, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0));
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    ///// Tạo dữ liệu cho Dataset
    TaoDuLieu(themmoi) {
      this.TaoDuLieu_HDKH(themmoi);
      this.TaoDuLieu_HDTB(themmoi);
    },
    //end///

    /////  Tao du lieu cho bang HD_KHACHHANG
    async TaoDuLieu_HDKH(themmoi) {
      this.dsHDKH = [];
      if (themmoi == true) {
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
          keyname: "HD_KHACHHANG",
        });
        this.hdkh_id = rs.data.data;
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
          keyname: "NHOMTB",
        });
        this.nhomtb_id = rs.data.data;
      }
      let rowHDKH = {};
      rowHDKH.HDKH_ID = this.hdkh_id;
      if (themmoi == true) {
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/sinh_magd", {
          loaihd_id: LoaiHopDong.DANGKY_GOI_DADV,
        });
        this.$refs.txtMaGD.value = rs.data.data;
        rowHDKH.MA_GD = this.$refs.txtMaGD.value;
      } else {
        rowHDKH.MA_GD = this.$refs.txtMaGD.value;
      }
      rowHDKH.MA_KH = this.$refs.txtMaKH.value;
      if (this.khachhang_id > 0) rowHDKH.KHACHHANG_ID = this.khachhang_id;
      rowHDKH.KHLON_ID = this.khlon_id;
      rowHDKH.TEN_KH = this.$refs.txtTenKH.value;
      rowHDKH.DIACHI_KH = this.$refs.txtDiaChiKH.value;
      rowHDKH.NGAYLAP_HD = this.$refs.dtpNgayLHD.value;
      rowHDKH.NGAY_YC = this.$refs.dtpNgayYeuCau.value;
      rowHDKH.GHICHU = this.$refs.txtGhiChu.value;
      rowHDKH.DONVI_ID = this.$root.token.getDonViID();
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID();
      rowHDKH.LOAIHD_ID = LoaiHopDong.DANGKY_GOI_DADV;
      this.dsHDKH.push(rowHDKH);
    },
    //end///

    ///// Tao du lieu cho bang HD_THUEBAO2
    async TaoDuLieu_HDTB2(themmoi) {
      return new Promise(async (resolve, reject) => {
        let k = 0;
        this.dsHDTB = [];
        if (themmoi == true) {
          rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
            keyname: "HD_THUEBAO",
          });
          this.hdtb_id = rs.data.data;
        }
        let rowHDTB = {};
        rowHDTB.HDTB_ID = this.hdtb_id;
        rowHDTB.HDKH_ID = this.hdkh_id;
        rowHDTB.THUEBAO_ID = Number(this.dtThueBao[k]["thuebao_id"]);
        rowHDTB.MA_TB = this.dtThueBao[k]["ma_tb"];
        rowHDTB.TEN_TB = this.dtThueBao[k]["ten_tb"];
        rowHDTB.DIACHI_LD = this.dtThueBao[k]["diachi_ld"];

        rowHDTB.KIEULD_ID = Number(this.$refs.cboKieuLD.value);
        rowHDTB.DOITUONG_ID = Number(this.dtThueBao[k]["DOITUONG_ID"]);
        rowHDTB.TTHD_ID = TrangThaiHD.MOI;
        rowHDTB.LOAITB_ID = Number(this.dtThueBao[k]["LOAITB_ID"]);
        rowHDTB.DICHVUVT_ID = Number(this.dtThueBao[k]["DICHVUVT_ID"]);
        rowHDTB.DONVI_ID = Number(this.dtThueBao[k]["DONVI_ID"]);
        rowHDTB.NGUOI_CN = this.$root.token.getMaNhanVien();
        rowHDTB.NGAY_CN = moment().format("DD/MM/YYYY");
        rowHDTB.MAY_CN = await this.$root.token.getMachine();
        // vinhpv lay huonggiao_id
        let rs = await this.axios.post("web-hopdong/hopdong/lay_huonggiao_tiepnhan", {
          kieuLdId: this.$refs.cboKieuLD.value,
          loaiHdId: LoaiHopDong.DANGKY_GOI_DADV,
          loaiTbId: 0,
          mucCuocId: 0,
          tocDoId: 0,
        });
        let dset = rs.data.data;
        if (dset && dset.length > 0) {
          this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
          this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
        }
        rowHDTB.QUYTRINH_ID = this.quytrinh_id;
        this.dsHDTB.push(rowHDTB);
        resolve(true);
      });
    },
    //end///

    ///// Tao du lieu cho bang HD_THUEBAO
    async TaoDuLieu_HDTB(themmoi) {
      let k = 0;
      let dtListTB = this.$refs.dgrListKH.getSelectedRecords();
      this.dsHDTB = [];
      if (themmoi == true) {
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
          keyname: "HD_THUEBAO",
        });
        this.hdtb_id = rs.data.data;
      }

      let rowHDTB = {};
      rowHDTB.HDTB_ID = this.hdtb_id;
      rowHDTB.HDKH_ID = this.hdkh_id;
      if (this.thuebao_id > 0) rowHDTB.THUEBAO_ID = this.thuebao_id;
      rowHDTB.MA_TB = this.$refs.txtMaTB.value;
      rowHDTB.TEN_TB = this.$refs.txtTenTB.value;
      rowHDTB.DIACHI_LD = dtListTB[k]["diachi_ld"];

      rowHDTB.KIEULD_ID = Number(this.$refs.cboKieuLD.value);
      rowHDTB.DOITUONG_ID = Number(dtListTB[k]["DOITUONG_ID"]);
      rowHDTB.TTHD_ID = TrangThaiHD.MOI;
      rowHDTB.LOAITB_ID = Number(dtListTB[k]["LOAITB_ID"]);
      rowHDTB.DICHVUVT_ID = Number(dtListTB[k]["DICHVUVT_ID"]);
      rowHDTB.DONVI_ID = Number(dtListTB[k]["DONVI_ID"]);
      rowHDTB.NGUOI_CN = this.$root.token.getMaNhanVien();
      rowHDTB.NGAY_CN = moment().format("DD/MM/YYYY");
      rowHDTB.MAY_CN = await this.$root.token.getMachine();
      // vinhpv lay huonggiao_id
      let rs = await this.axios.post("web-hopdong/hopdong/lay_huonggiao_tiepnhan", {
        kieuLdId: this.$refs.cboKieuLD.value,
        loaiHdId: LoaiHopDong.DANGKY_GOI_DADV,
        loaiTbId: 0,
        mucCuocId: 0,
        tocDoId: 0,
      });
      let dset = rs.data.data;
      if (dset.length > 0) {
        this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
        this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
      }
      rowHDTB.QUYTRINH_ID = quytrinh_id;
      this.dsHDTB.push(rowHDTB);
    },
    //end///

    ///// Tao du lieu cho bang HDTB_GOI_DADV
    async TaoDuLieu_HDTB_GOI_DADV(themmoi) {
      this.dsHDTBGOI = [];
      let rowGOI = {};
      rowGOI.GOI_ID = Number(this.$refs.cboTenGoi_DV.value);
      //Sinh nhomtb_id
      await this.Tao_nhomtb();
      rowGOI.NHOMTB_ID = this.nhomtb_id;
      rowGOI.CHUNHOM = this.$refs.chkChuNhom.checked ? 1 : 0;
      rowGOI.HDTB_ID = this.hdtb_id;
      let thang_huong = Number(this.$refs.cboHieuLuc.value);
      if (thang_huong == 2) thang_huong = Number(this.$refs.txtSoThang.value);
      rowGOI.THANG_HUONG = thang_huong;
      this.dsHDTBGOI.push(rowGOI);
      // vinhpv lay huonggiao_id
      let rs = await this.axios.post("web-hopdong/hopdong/lay_huonggiao_tiepnhan", {
        kieuLdId: this.$refs.cboKieuLD.value,
        loaiHdId: LoaiHopDong.DANGKY_GOI_DADV,
        loaiTbId: 0,
        mucCuocId: 0,
        tocDoId: 0,
      });
      let dset = rs.data.data;
      if (dset.length > 0) {
        this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
        this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
      }
    },
    //end///

    ///// Tao du lieu cho bang HDTB_GOI_DADV2
    async TaoDuLieu_HDTB_GOI_DADV2(themmoi) {
      return new Promise(async (resolve,rj) => {
      this.dsHDTBGOI = [];
      let rowGOI = {};
      rowGOI.GOI_ID = Number(this.$refs.cboTenGoi_DV.value);
      rowGOI.NHOMTB_ID = this.nhomtb_id;
      rowGOI.CHUNHOM = 0;
      rowGOI.HDTB_ID = this.hdtb_id;
      let thang_huong = Number(this.$refs.cboHieuLuc.value);
      if (thang_huong == 2) thang_huong = Number(this.$refs.txtSoThang.value);
      rowGOI.THANG_HUONG = thang_huong;
      this.dsHDTBGOI.push(rowGOI);
      // vinhpv lay huonggiao_id
      let rs = await this.axios.post("web-hopdong/hopdong/lay_huonggiao_tiepnhan", {
        kieuLdId: this.$refs.cboKieuLD.value,
        loaiHdId: LoaiHopDong.DANGKY_GOI_DADV,
        loaiTbId: 0,
        mucCuocId: 0,
        tocDoId: 0,
      });
      let dset = rs.data.data;
      if (dset && dset.length > 0) {
        this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
        this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
      }
      resolve(true);
    }) 
    },
    //end///

    ///// Tao du lieu 2
    async TaoDuLieu_2(themmoi, nhomtb) {
      this.dsHDTB = [];
      this.dsHDTBGOI = [];
      var s = this.dtListTB.filter((item) => (item.CHK = 1));
      this.dtListTB.forEach(async (dr) => {
        if (dr["chk"] == "1") {
          this.hdtb_id = Number(dr["hdtb_id"]);
          if (this.hdtb_id == 0) {
            if (themmoi == true) {
              rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
                keyname: "HD_THUEBAO",
              });
              this.hdtb_id = rs.data.data;
            }
            let rowHDTB = {};
            rowHDTB.HDTB_ID = this.hdtb_id;
            rowHDTB.HDKH_ID = this.hdkh_id;
            if (this.thuebao_id > 0) rowHDTB.THUEBAO_ID = this.thuebao_id;
            rowHDTB.MA_TB = dr["ma_tb"];
            rowHDTB.TEN_TB = dr["ten_tb"];
            rowHDTB.DIACHI_LD = dr["diachi_ld"];
            rowHDTB.KIEULD_ID = Number(this.$refs.cboKieuLD.value);
            rowHDTB.DOITUONG_ID = Number(dr["DOITUONG_ID"]);
            rowHDTB.TTHD_ID = TrangThaiHD.MOI;
            rowHDTB.LOAITB_ID = Number(dr["LOAITB_ID"]);
            rowHDTB.DICHVUVT_ID = Number(dr["DICHVUVT_ID"]);
            rowHDTB.DONVI_ID = Number(dr["DONVI_ID"]);
            rowHDTB.NGUOI_CN = this.$root.token.getMaNhanVien();
            rowHDTB.NGAY_CN = moment().format("DD/MM/YYYY");
            rowHDTB.MAY_CN = await this.$root.token.getMachine();
            // vinhpv lay huonggiao_id
            let rs = await this.axios.post("web-hopdong/hopdong/lay_huonggiao_tiepnhan", {
              kieuLdId: this.$refs.cboKieuLD.value,
              loaiHdId: LoaiHopDong.DANGKY_GOI_DADV,
              loaiTbId: 0,
              mucCuocId: 0,
              tocDoId: 0,
            });
            let dset = rs.data.data;
            if (dset.length > 0) {
              this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
              this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
            }
            rowHDTB.QUYTRINH_ID = this.quytrinh_id;
            this.dsHDTB.push(rowHDTB);
          }
          let rowGOI = {};
          rowGOI.GOI_ID = Number(this.$refs.cboTenGoi_DV.value);
          //Sinh nhomtb_id
          //Tao_nhomtb();
          rowGOI.NHOMTB_ID = this.nhomtb_id;
          rowGOI.CHUNHOM = Number(dr["chknhom"]);
          rowGOI.HDTB_ID = hdtb_id;
          let thang_huong = Number(this.$refs.cboHieuLuc.value);
          if (thang_huong == 2) thang_huong = Number(this.$refs.txtSoThang.value);
          rowGOI.THANG_HUONG = thang_huong;
          this.dsHDTBGOI.push(rowGOI);
        }
      });
      //// vinhpv lay huonggiao_id
      //dset = bangts.LayHuongGiao_TiepNhan(LoaiHopDong.DANGKY_GOI_DADV, Number(cboKieuLD.value));
      //if (dset.length > 0)
      //{
      //    huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
      //    quytrinh_id = Number(dset[0]["quytrinh_id"]);
      //}
    },
    //end///

    ///// Tao du lieu 3
    async TaoDuLieu_3(themmoi, dr) {
      this.dsHDTB = [];
      this.dsHDTBGOI = [];
      this.hdtb_id = Number(dr["hdtb_id"]);
      if (dr["thuebao_id"] != "") this.thuebao_id = Number(dr["thuebao_id"]);
      else this.thuebao_id = 0;
      if (this.hdtb_id == 0) {
        let hdtb = bangts.get_hdtb_id(hdkh_id, thuebao_id);
        if (hdtb == 0) {
          if (themmoi == true) {
            rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
              keyname: "HD_THUEBAO",
            });
          }
          hdtb_id = getkey.GetKey("HD_THUEBAO");
          let rowHDTB = {};
          rowHDTB.HDTB_ID = hdtb_id;
          rowHDTB.HDKH_ID = hdkh_id;
          if (thuebao_id > 0) rowHDTB.THUEBAO_ID = thuebao_id;
          rowHDTB.MA_TB = dr["ma_tb"];
          rowHDTB.TEN_TB = dr["ten_tb"];
          rowHDTB.DIACHI_LD = dr["diachi_ld"];
          rowHDTB.KIEULD_ID = Number(cboKieuLD.value);
          rowHDTB.DOITUONG_ID = Number(dr["DOITUONG_ID"]);
          rowHDTB.TTHD_ID = TrangThaiHD.MOI;
          rowHDTB.LOAITB_ID = Number(dr["LOAITB_ID"]);
          rowHDTB.DICHVUVT_ID = Number(dr["DICHVUVT_ID"]);
          rowHDTB.DONVI_ID = Number(dr["DONVI_ID"]);
          rowHDTB.NGUOI_CN = tt_nd.ma_nd;
          rowHDTB.NGAY_CN = tt_nd.ngay_cn;
          rowHDTB.MAY_CN = tt_nd.may_cn;
          // vinhpv lay huonggiao_id
          let rs = await this.axios.post("web-hopdong/hopdong/lay_huonggiao_tiepnhan", {
            kieuLdId: this.$refs.cboKieuLD.value,
            loaiHdId: LoaiHopDong.DANGKY_GOI_DADV,
            loaiTbId: 0,
            mucCuocId: 0,
            tocDoId: 0,
          });
          let dset = rs.data.data;
          if (dset.length > 0) {
            this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
            this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
          }
          rowHDTB.QUYTRINH_ID = this.quytrinh_id;
          this.dsHDTB.push(rowHDTB);
        } else hdtb_id = hdtb;
      }
      let rowGOI = {};
      rowGOI.GOI_ID = Number(this.$refs.cboTenGoi_DV.value);
      //Sinh nhomtb_id
      //Tao_nhomtb();
      rowGOI.NHOMTB_ID = this.nhomtb_id;
      rowGOI.CHUNHOM = Number(dr["chknhom"]);
      rowGOI.HDTB_ID = this.hdtb_id;
      let thang_huong = Number(this.$refs.cboHieuLuc.value);
      if (thang_huong == 2) thang_huong = Number(this.$refs.txtSoThang.value);
      rowGOI.THANG_HUONG = thang_huong;
      this.dsHDTBGOI.push(rowGOI);
      //// vinhpv lay huonggiao_id
      //dset = bangts.LayHuongGiao_TiepNhan(LoaiHopDong.DANGKY_GOI_DADV, Number(cboKieuLD.value));
      //if (dset.length > 0)
      //{
      //    huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
      //    quytrinh_id = Number(dset[0]["quytrinh_id"]);
      //}
    },
    //end///

    async Tao_nhomtb() {
      this.nhomtb_id = 0;
      let k = 0,
        ktNhom = 0;
      for (let i = 0; i < this.dtList.length; i++) {
        if (this.dtList[i] && this.dtList[i]["chk"] == "1") {
          if (this.nhomtb_id != Number(dtList[i]["nhomtb_id"])) ktNhom++;
          this.nhomtb_id = Number(dtList[i]["nhomtb_id"]);
          let g = Number(dtList[i]["goi_id"]);
          if (g != Number(this.$refs.cboTenGoi_DV.value)) {
            this.$toast.error("Gói không trùng với nhóm thuê bao cần ghép!");
            return false;
          }
        }
      }
      if (ktNhom > 1) {
        this.$toast.error("Bạn chọn quá 2 nhóm cần ghép!");
        // cboTenGoi_DV.focus();
        return false;
      } else if (ktNhom == 1) return true;
      else {
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
          keyname: "HD_THUEBAO",
        });
        this.nhomtb_id = rs.data.data;
      }
      return true;
    },
    async KiemTraDangKyGoiDadv(ma_tb, vgoi_id, vthuebao_id, vchunhom, vnhomtb_id) {
      //            --> Kiem tra xem thue bao co dang su dung Goi da dich vu trung voi goi cu ko ??
      let sql = "";
      sql +=
        " select * from " +
        DatabaseConstants.DB2 +
        ".bd_goi_dadv a, " +
        DatabaseConstants.DB2 +
        ".goi_dadv b ";
      sql +=
        " where a.goi_id = b.goi_id and a.trangthai = 1 and a.thuebao_id = " +
        vthuebao_id;
      sql += " and a.thuebao_id = " + vthuebao_id;
      sql += " and b.goi_id =  " + vgoi_id;
      rs = await this.axios.post(
        "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
        {
          ds_para: sql,
        }
      );
      var dt = rs.data.data;

      if (dt.count > 0) {
        this.$toast.error(
          "Thuê bao " + ma_tb + " đang sử dụng gói đa dịch vụ này rồi, không thể đăng ký!"
        );
        return false;
      }

      //    -->> Kiem tra xem thue bao co dang ky Hop dong Goi da dv nao khac khong ?
      sql = "";
      sql += " select a.hdtb_id ";
      sql +=
        " from " +
        DatabaseConstants.DB2 +
        ".hd_thuebao a, " +
        DatabaseConstants.DB2 +
        ".hd_khachhang b ";
      sql += " where a.hdkh_id = b.hdkh_id ";
      sql += " and a.hdkh_id <> " + hdkh_id;
      sql += " and b.loaihd_id in (27,28) and a.tthd_id = 1 ";
      sql += " and a.thuebao_id = " + vthuebao_id;
      rs = await this.axios.post(
        "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
        {
          ds_para: sql,
        }
      );
      var dt = rs.data.data;
      if (dt.count > 0) {
        this.$toast.error(
          "Thuê bao " + ma_tb + " đã đăng ký hợp đồng đa dịch vụ khác rồi!"
        );
        return false;
      }
      //            --> Lay thong tin ve thue bao
      sql = "";
      sql +=
        "Select a.loaitb_id, a.thanhtoan_id, a.khachhang_id, a.doituong_id, nvl(b.tocdo_id,-1) tocdo_id,";
      sql +=
        "       nvl(b.muccuoc_id,0) muccuoc_id, round(MONTHS_BETWEEN(sysdate, ngay_sd)/12) nam_sd, a.trangthaitb_id";
      sql +=
        " from " +
        DatabaseConstants.DB2 +
        ".db_thuebao a, " +
        DatabaseConstants.DB2 +
        ".db_adsl b";
      sql += " where a.thuebao_id = b.thuebao_id (+)";
      sql += "    and a.thuebao_id = " + vthuebao_id;
      rs = await this.axios.post(
        "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
        {
          ds_para: sql,
        }
      );
      var dtThuebao = rs.data.data;
      if (dtThuebao.count > 0) {
        sql = "";
        sql += "select a.goi_id, a.ngay_bd, a.ngay_kt, a.trangthai, b.doituong_id,";
        sql +=
          " c.loaitb_id, c.tocdo_id, c.muccuoc_id, c.tg_tu, c.tg_den, c.sl_tu, c.sl_den, c.chunhom, a.ma_ghep";
        sql +=
          " from " +
          DatabaseConstants.DB2 +
          ".goi_dadv a, " +
          DatabaseConstants.DB2 +
          ".goi_dadv_dt b, " +
          DatabaseConstants.DB2 +
          ".goi_dadv_lhtb c";
        sql +=
          " where a.goi_id = b.goi_id and a.goi_id = c.goi_id and a.goi_id = " + vgoi_id;
        rs = await this.axios.post(
          "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
          {
            ds_para: sql,
          }
        );
        var dtGoi = rs.data.data;
        let vkt = 0,
          vkt1 = 0,
          vkt2 = 0,
          vkt3 = 0,
          vkt4 = 0,
          vkt5 = 0,
          vkt6 = 0;
        dtGoi.forEach((d) => {
          if (d["ngay_kt"] != "")
            if (
              moment(d["ngay_kt"], "DD/MM/YYYY HH:mm:ss") <
              moment().format("DD/MM/YYYY HH:mm:ss")
            ) {
              this.$toast.error("Gói đã hết hạn để đăng ký!");
              return false;
            }
          if (d["trangthai"] != "1") {
            this.$toast.error("Gói đã hết hiệu lực để đăng ký!");
            return false;
          }
          if (d["loaitb_id"] == dtThuebao[0]["loaitb_id"]) {
            vkt1 = 1;
            if (d["doituong_id"] == dtThuebao[0]["doituong_id"]) vkt2 = 1;
            if (d["tocdo_id"] == dtThuebao[0]["tocdo_id"]) vkt3 = 1;
            if (d["muccuoc_id"] == dtThuebao[0]["muccuoc_id"]) vkt4 = 1;
            //Kif (d["tocdo_id"] == dtThuebao[0]["tocdo_id"])
            vkt5 = 1;
            if (d["chunhom"] == vchunhom) vkt6 = 1;
          }
        });
        if (vkt1 != 1) {
          this.$toast.error(
            "Thuê bao " + ma_tb + ": Gói cước không áp dụng cho loại hình thuê bao này!"
          );
          return false;
        }
        if (vkt1 != 1) {
          this.$toast.error(
            "Thuê bao " + ma_tb + ": Gói cước không áp dụng cho loại hình thuê bao này!"
          );
          return false;
        }
        if (vkt2 != 1) {
          this.$toast.error(
            "Thuê bao " + ma_tb + ": Gói cước không áp dụng cho đối tượng thuê bao này!"
          );
          return false;
        }
        if (vkt3 != 1) {
          this.$toast.error(
            "Thuê bao " + ma_tb + ": Gói cước không áp dụng cho tốc độ thuê bao này!"
          );
          return false;
        }
        if (vkt4 != 1) {
          this.$toast.error(
            "Thuê bao " + ma_tb + ": Gói cước không áp dụng cho mức cước thuê bao này!"
          );
          return false;
        }
        if (vkt5 != 1) {
          this.$toast.error(
            "Thuê bao " + ma_tb + ": Thời gian không đủ điều kiện để tham gia gói!"
          );
          return false;
        }
        if (vkt6 != 1) {
          this.$toast.error(
            "Thuê bao " +
              ma_tb +
              ": Gói cước áp dụng chủ nhóm cho loại hình thuê bao này!"
          );
          return false;
        }
      }
      sql = "";
      sql += "select b.sl_tu, b.sl_den, b.chunhom, a.ma_ghep";
      sql +=
        " from " +
        DatabaseConstants.DB2 +
        ".goi_dadv a, " +
        DatabaseConstants.DB2 +
        ".goi_dadv_lhtb b";
      sql +=
        " where a.goi_id = b.goi_id and a.goi_id = " +
        vgoi_id +
        " and b.loaitb_id = " +
        dtThuebao[0]["loaitb_id"];
      rs = await this.axios.post(
        "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
        {
          ds_para: sql,
        }
      );
      var dtgoilh = rs.data.data;
      // Kiểm tra số lượng loại hình thuê bao
      let sl_den = Number(dtgoilh[0]["sl_den"]);
      let sl1 = this.dtListTB.filter(
        (item) => item.chk == 1 && item.loaitb_id == dtThuebao[0]["loaitb_id"]
      ).length;
      let sl2 = 0;
      if (vnhomtb_id != 0) {
        sql = "";
        sql += " select nvl(count(*),0) ";
        sql +=
          " from " +
          DatabaseConstants.DB2 +
          ".hd_thuebao a, " +
          DatabaseConstants.DB2 +
          ".hd_khachhang b," +
          DatabaseConstants.DB2 +
          ".hdtb_goi_dadv c";
        sql += " where a.hdkh_id = b.hdkh_id and a.tthd_id = 1 and a.hdtb_id = c.hdtb_id";
        sql += " and a.loaitb_id = " + dtThuebao[0]["loaitb_id"];
        sql += " and b.hdkh_id = " + hdkh_id;
        sql += " and c.goi_id = " + vgoi_id;
        rs = await this.axios.post(
          "web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm",
          {
            ds_para: sql,
          }
        );
        var dtsl2 = rs.data.data;
        sl2 = Number(dtsl2[0][0]);
      }
      if (sl1 + sl2 > sl_den) {
        this.$toast.error(
          "Số lượng loại hình thuê bao cùng loại hình với " +
            ma_tb +
            " không được vượt quá " +
            sl_den
        );
        return false;
      }
      return true;
    },

    tsbtnHuyBo_Click() {
      this.SetButton(0);
      this.$refs.dgrList.dataSource = [];
    },

    async tsbtnXoa_Click() {
      this.$confirm(
        "Bạn thật sự muốn xóa không ?" + this.$refs.txtMaTB.value + " không?",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      ).then(async () => {
        try {
          let data = await this.axios.post(
            "web-hopdong/lapdatmoi/fn_xoahdtb_dk_goidadvlm",
            {
              ds_para: this.hdkh_id,
            }
          );

          try {
            let object = JSON.parse(data.data.data);
            this.$toast.error(object.MESSAGE);
          } catch (err) {
            this.$toast.success(data.data.data);
          }

          this.$refs.dgrList.dataSource = [];
          this.SetButton(0);
        } catch (err) {
          console.log(err);
          this.$toast.error("Có lỗi: \n" + err.message);
        }
      });
    },

    tsbtnThemTB_Click() {
      try {
        this.ThemHDThueBao();
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: \n" + err.message);
      }
    },

    CheckThueBaoDiDong(dsTB) {
      return new Promise(async (rs, rj) => {
        try {
          for (let i = 0; i < dsTB.length; i++) {
            let matb = dsTB[i]["MA_TB"];
            if (dsTB[i]["LOAITB_ID"] == LoaiHinhTB.DIDONGTRASAU) {
              let checkDD = await this.$axios.get(
                "/tichhop/mnp/checkParticipant?sdt=" + matb
              );
              if (checkDD.data.data != "01") {
                this.$toast.error(
                  "Thuê bao " +
                    matb +
                    " không thuộc quản lý của Vinaphone Vui lòng kiểm tra lại!"
                );
                rs(false);
              }
            }
          }
        } catch (e) {
          console.log("Lỗi SPS");
          console.log(e);
        }

        rs(true);
      });
    },

    ///// Them HD thuê bao
    async ThemHDThueBao() {
      let listTB = this.$refs.dgrList.getSelectedRecords();

      if ((await this.CheckThueBaoDiDong(listTB)) == false) return;

      try {
        let listTB_KH = this.$refs.dgrListKH.getSelectedRecords();

        let data = {
          LIST_TB_DK: listTB_KH.map((item) => ({
            ...item,
            CHK: 1,
          })),
          GOIDV_ID: this.$refs.cboTenGoi_DV.value,
          KHACHHANG_ID: this.khachhang_id,
          LIST_TB: listTB.map((item) => ({
            ...item,
            CHK: 1,
          })),
          KIEULD_ID: this.$refs.cboKieuLD.value,
          HDKH_ID: this.hdkh_id,
          NGUOI_CN: this.$root.token.getUserName(),
          MAY_CN: await this.$root.token.getMachine(),
          HIEULUC: this.$refs.cboHieuLuc.value,
          SOTHANG: this.$refs.txtSoThang.value,
        };

        rs = await this.axios.post(
          "web-hopdong/lapdatmoi/fn_themhdthuebao_dk_goidadvlm",
          {
            ds_para: JSON.stringify(data),
          }
        );

        if (rs.data && rs.data.error_code && rs.data.error_code == "BSS-00000000") {
          let check = JSON.parse(rs.data.data);
          if (check && check.ERROR_CODE && check.ERROR_CODE == 1) {
            this.SetButton(3);
            this.$toast.success(
              "Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!"
            );
            rs = await this.axios.post("web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd", {
              in_ma_gd: this.$refs.txtMaGD.value,
              in_loaihd_id: LoaiHopDong.DAT_MOI,
              in_donvi_id: this.$root.token.getDonViID(),
              in_tthd_id: TrangThaiHD.MOI,
              in_nhanvien_id: this.$root.token.getNhanVienID(),
              in_donvi_dl_id: 0,
            });
            this.HienThiTTHopDongKH(rs.data.data);
          } else {
            this.$toast.error(check.MESSAGE);
          }
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: \n" + err.message);
      }
    },
    //end///

    ///// Them HD thuê bao
    async ThemHDThueBao2() {
      try {
        try {
          if(this.$refs.cboDichVuVT.value == 2) {
            let checkDD = await this.axios.get(
              "/tichhop/mnp/checkParticipant?sdt=" + this.$refs.txtMaTB.value
            );
            if (checkDD.data.data != "01") {
              this.$toast.error(
                "Thuê bao không thuộc quản lý của Vinaphone Vui lòng kiểm tra lại!"
              );
              return;
            }
          }
        } catch (e) {
          console.log("Lỗi SPS");
          console.log(e);
        }

        if (this.dtThueBao.length == 0 || this.dtThueBao == null) {
          await this.TaoDuLieu_HDTB_GOI_DADV2();
          let temp = this.dtList.map((item) => ({
                  ...item,
                  CHK: 1,
                }))
                let dsTB = [{
                  CHK : 1,
                  MA_TB : this.$refs.txtMaTB.value,
                  TEN_TB : this.$refs.txtTenTB.value,
                  DIACHI_LD : this.$refs.txtDiaChiTB.value,
                  DOITUONG_ID : this.doituong_id,
                  LOAITB_ID : this.loaitb_id,
                  DICHVUVT_ID : this.$refs.cboDichVuVT.value,
                  DONVI_ID : this.$root.token.getDonViID(),
                  HDTB_ID : 0,
                  THUEBAO_ID : this.thuebao_id,
                  CHKNHOM : this.$refs.chkChuNhom.checked ? 1 : 0,
                }]

          rs = await this.axios.post(
            "web-hopdong/lapdatmoi/fn_themtbngoaikh_dk_goidadvlm",
            {
              ds_para: JSON.stringify({
                DICHVUVT_ID: this.$refs.cboDichVuVT.value,
                MA_TB: this.$refs.txtMaTB.value,
                HIEULUC: this.$refs.cboHieuLuc.value,
                LIST_TB_DK: temp,
                DS_THUEBAO: dsTB,
                CHK_CHUNHOM: this.$refs.chkChuNhom.checked,
                HDKH_ID: this.hdkh_id,
                GOIDV_ID: this.$refs.cboTenGoi_DV.value,
                THUEBAO_ID: this.thuebao_id,
                KIEULD_ID: this.$refs.cboKieuLD.value,
                NGUOI_CN: this.$root.token.getUserName(),
                MAY_CN: await this.$root.token.getMachine(),
              }),
            }
          );
          if (rs.data && rs.data.error_code == "BSS-00000000") {
            let rs_result = JSON.parse(rs.data.data);
            if (rs_result && rs_result.ERROR_CODE && rs_result.ERROR_CODE == 1) {
              this.SetButton(3);
              this.$toast.success(
                "Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!"
              );
            } else {
              this.$toast.error(rs_result.MESSAGE);
            }
          }
        } else {
          let hl = this.$refs.cboHieuLuc.SelectedValue;
          if (hl == 2) {
            if (this.$refs.txtSoThang.value == "") {
              this.$toast.error(
                "Khi chọn hiệu lực khác bạn phải nhập số tháng sau tháng đăng ký có hiệu lực!"
              );
              return;
            }
          }

          await this.TaoDuLieu_HDTB2();
          await this.TaoDuLieu_HDTB_GOI_DADV2();

          let dtThueBao_upper = this.dtThueBao.map((item) => {
            console.log(item);
            return Object.entries(item).reduce((a, [key, value]) => {
              a[key.toUpperCase()] = value;
              return a;
            }, {});
          });

          rs = await this.axios.post(
            "web-hopdong/lapdatmoi/fn_themtbngoaikh_dk_goidadvlm",
            {
              ds_para: JSON.stringify({
                DICHVUVT_ID: this.$refs.cboDichVuVT.value,
                MA_TB: this.$refs.txtMaTB.value,
                HIEULUC: this.$refs.cboHieuLuc.value,
                LIST_TB_DK: this.dtList.map((item) => ({
                  ...item,
                  CHK: 1,
                })),
                DS_THUEBAO: dtThueBao_upper,
                CHK_CHUNHOM: this.$refs.chkChuNhom.checked,
                HDKH_ID: this.hdkh_id,
                THUEBAO_ID: this.thuebao_id,
                KIEULD_ID: this.$refs.cboKieuLD.value,
                NGUOI_CN: this.$root.token.getUserName(),
                MAY_CN: await this.$root.token.getMachine()
              }),
            }
          );
          if (rs.data && rs.data.error_code == "BSS-00000000") {
            let rs_result = JSON.parse(rs.data.data);
            if (rs_result && rs_result.ERROR_CODE && rs_result.ERROR_CODE == 1) {
              this.SetButton(3);
              this.$toast.success(
                "Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!"
              );
            } else {
              this.$toast.error(rs_result.MESSAGE);
            }
          }
        }

        rs = await this.axios.post("web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd", {
          in_ma_gd: this.$refs.txtMaGD.value,
          in_loaihd_id: LoaiHopDong.DAT_MOI,
          in_donvi_id: this.$root.token.getDonViID(),
          in_tthd_id: TrangThaiHD.MOI,
          in_nhanvien_id: this.$root.token.getNhanVienID(),
          in_donvi_dl_id: 0,
        });
        this.HienThiTTHopDongKH(rs.data.data);
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: \n" + err.message);
      }
    },
    //end///

    tsbtnXoaTB_Click() {
      try {
        this.XoaHDTB();
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    /// XoaHDTB
    XoaHDTB() {
      try {
        //if (dgrList.SelectedRows.count <= 0) return;
        var s = this.$refs.dgrList.getSelectedRecords();
        if (s.length == 0) {
          this.$toast.error("Bạn chưa chọn thuê bao để xóa!");
          return;
        }

        this.$confirm("Bạn thật sự muốn xóa thuê bao không ?", {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Hủy",
          type: "success",
        }).then(async () => {
          await this.axios.post("web-hopdong/lapdatmoi/fn_xoahdtb_dk_goidadvlm", {
            ds_para: JSON.stringify({
              LIST_TB_DK: s.map((item) => ({
                ...item,
                CHK: 1,
              })),
              KIEULD_ID: this.$refs.cboKieuLD.value,
            }),
          });

          this.HienThiDanhSacHDTB();
        });
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: \n" + err.message);
      }
    },
    //end///

    /// <summary>
    /// Kích hoạt K+ Add-on
    /// </summary>
    // private bool KichHoat_Kplus_Addon(mytvAcc)
    // {
    //     try
    //     {
    //         VASCService vasc = new VASCService(tt_nd.user_vasc, tt_nd.pass_vasc, tt_nd.diemgiaodich_vasc);
    //         IPTVAccount = CommonFunction.ChuanHoa_XML(mytvAcc);
    //         dskplus = new TCHopDongFacade2().lay_tt_dangky_k_addon(hdtb_id);

    //         if (dskplus != null && dskplus.length > 0)
    //         {
    //             if (dskplus[0][0] == "1")
    //             {
    //                 object[] resultRegistKplus = vasc.RegistKplus(IPTVAccount, 1);
    //                 if (Number(resultRegistKplus[0]) != 1)
    //                 {
    //                     try
    //                     {
    //                         tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDVLM", "vkey_parameter",
    //                         "ma_tb=" + IPTVAccount + ",KQ=" + resultRegistKplus[0] + ",Errordesc=" + resultRegistKplus[1], "vserver_name", tt_nd.ip, "vnote", " Ln:1124: Lỗi kích hoạt");
    //                     }
    //                     catch (Exception)
    //                     {
    //                     }

    //                     Message_Box.ShowWarning("Có lỗi khi kích hoạt K+ Add-on lên VASC");
    //                     return false;
    //                 }
    //                 else
    //                 {
    //                     try
    //                     {
    //                         tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDVLM", "vkey_parameter",
    //                         "ma_tb=" + IPTVAccount + ",KQ=" + Number(resultRegistKplus[0]) + ",Errordesc=" + resultRegistKplus[1], "vserver_name", tt_nd.ip, "vnote", " Ln:1138: Kich hoạt cập nhật dl dưới tỉnh thành công");
    //                     }
    //                     catch (Exception)
    //                     {
    //                     }
    //                 }
    //             }
    //             else
    //             {
    //                 try
    //                 {
    //                     tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDVLM", "vkey_parameter",
    //                     "ma_tb=" + IPTVAccount + ",hdtb_id=" + hdtb_id, "vserver_name", tt_nd.ip, "vnote", " Ln:1150: dskplus[0][0]<>1");
    //                 }
    //                 catch (Exception)
    //                 {
    //                 }
    //             }
    //             return true;
    //         }
    //         else
    //         {
    //             try
    //             {
    //                 tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDVLM", "vkey_parameter",
    //                 "ma_tb=" + IPTVAccount + ",hdtb_id=" + hdtb_id, "vserver_name", tt_nd.ip, "vnote", " Ln:1163: dskplus.length <= 0");
    //             }
    //             catch (Exception)
    //             {
    //             }
    //         }
    //         return true;
    //     }
    //     catch (err)
    //     {
    //         Message_Box.Show(err.message);
    //         try
    //         {
    //             tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDVLM", "vkey_parameter",
    //             "ma_tb=" + mytvAcc + ",hdtb_id=" + hdtb_id, "vserver_name", tt_nd.ip, "vnote", " Ln:1177: " + err.message);
    //         }
    //         catch (Exception)
    //         {
    //         }
    //         return false;
    //     }
    // }

    tsbtnCapNhatDB_Click() {
      try {
        magoi_vdc = "";
        magoi_vasc = "";
        matocdo_vasc = "";
        if (hdkh_id != 0) {
          kt = "";
          kt = hoanthien.KIEMTRA_TB_DK_HTHS_GOI_DADV(hdkh_id);
          if (kt != "1") {
            this.$toast.error(kt);
            return;
          }
          //Thực hiện kích hoạt thuê bao Mega/MyTV lên công ty dọc
          //Trong trường hợp gói đa dịch vụ có thuê bao cần được đồng bộ
          let kichhoat = true;
          for (let i = 0; i < dtList.count; i++) {
            if (dtList[i]["status"] == TRANGTHAI_DONGBO.DONGBO_CM) continue;
            loaitb = dtList[i]["loaitb_id"];
            thuebaoid = dtList[i]["thuebao_id"];
            // hoangpkn : 16/04/2014
            let vtocdo_id = -1;
            let vmuccuoc_id = 0;
            if (
              loaitb == LoaiHinhTB.INTERNET_ADSL ||
              loaitb == LoaiHinhTB.INTERNET_FTTH ||
              loaitb == LoaiHinhTB.WIFI_FIBER ||
              loaitb == LoaiHinhTB.INTERNET_MYTV
            ) {
              vtocdo_id = Number(
                checkdata.MAP_ID(
                  "tocdo_id",
                  DatabaseConstants.DB2 + ".db_adsl",
                  "where thuebao_id = " + Number(thuebaoid)
                )
              );
              vmuccuoc_id = Number(
                checkdata.MAP_ID(
                  "muccuoc_id",
                  DatabaseConstants.DB2 + ".db_adsl",
                  "where thuebao_id = " + Number(thuebaoid)
                )
              );
            }
            // end hoang
            magoi_vdc = "";
            magoi_vasc = "";
            matocdo_vasc = "";
            dongbo = checkdata.MAP_ID(
              "dongbo_dl",
              DatabaseConstants.DB2 + ".goi_dadv_lhtb",
              "where goi_id = " +
                goi_id +
                " and loaitb_id = '" +
                loaitb +
                "' and tocdo_id = " +
                vtocdo_id +
                " and muccuoc_id = " +
                vmuccuoc_id
            );
            if (dongbo == "0" || dongbo == "-1") continue;
            else {
              if (
                loaitb == LoaiHinhTB.INTERNET_ADSL ||
                loaitb == LoaiHinhTB.INTERNET_FTTH ||
                loaitb == LoaiHinhTB.WIFI_FIBER
              ) {
                magoi_vdc = checkdata.MAP_ID(
                  "goi_vdc_id",
                  DatabaseConstants.DB2 + ".goi_dadv",
                  "where goi_id = " + goi_id
                );
                if (magoi_vdc == "-1") {
                  this.$toast.error(
                    "Thông tin gói chưa được đồng bộ lên VDC. Bạn hãy kiểm tra lại !"
                  );
                  return;
                }
                if (KichHoatVDC(dtList[i]["ma_tb"], magoi_vdc, ""))
                  laphd.CAPNHAT_STATUS_HDTB(
                    Number(dtList[i]["hdtb_id"]),
                    TRANGTHAI_DONGBO.DONGBO_CM
                  );
                else {
                  Message_Box.ShowWarning(
                    "Có lỗi trong quá trình kích hoạt gói tích hợp trên VDC"
                  );
                  kichhoat = false;
                }
              } else if (loaitb == LoaiHinhTB.INTERNET_MYTV) {
                let vasc = new VASCService(
                  tt_nd.user_vasc,
                  tt_nd.pass_vasc,
                  tt_nd.diemgiaodich_vasc
                );
                dskplus = new TCHopDongFacade2().lay_tt_dangky_k_addon(
                  Number(dtList[i]["hdtb_id"])
                );

                if (dskplus != null && dskplus.length > 0) {
                  if (dskplus[0][0] == "1") {
                    ///// Kích gói K+ addon
                    if (KichHoat_Kplus_Addon(dtList[i]["ma_tb"])) {
                      laphd.CAPNHAT_STATUS_HDTB(
                        Number(dtList[i]["hdtb_id"]),
                        TRANGTHAI_DONGBO.DONGBO_CM
                      );
                    } else {
                      //Message_Box.ShowWarning("Có lỗi trong quá trình kích hoạt gói tích hợp trên VASC");
                      kichhoat = false;
                    }
                    //end///
                  } else {
                    ///// Kích gói bình thường
                    //magoi_vasc = checkdata.MAP_ID("goi_vasc_id", DatabaseConstants.DB2 + ".goi_dadv", "where goi_id = " + goi_id);
                    magoi_vasc = laphd.Lay_goivasc_goi_dadv(
                      goi_id,
                      vtocdo_id,
                      vmuccuoc_id
                    );
                    if (magoi_vasc == "-1") {
                      this.$toast.error(
                        "Thông tin gói chưa được đồng bộ lên VASC. Bạn hãy kiểm tra lại !"
                      );
                      return;
                    }
                    //Lấy ra mã tốc độ VASC
                    matocdo_vasc = checkdata.MAP_ID(
                      "matocdo",
                      DatabaseConstants.DB2 + ".tocdo_adsl",
                      "where tocdo_id in (select tocdo_id from " +
                        DatabaseConstants.DB2 +
                        ".db_adsl where thuebao_id = '" +
                        thuebaoid +
                        "')"
                    );
                    if (matocdo_vasc == "-1") {
                      this.$toast.error(
                        "Thông tin về tốc độ chưa được đồng bộ lên VASC. Bạn hãy kiểm tra lại !"
                      );
                      return;
                    }
                    if (KichHoatVASC(dtList[i]["ma_tb"], matocdo_vasc, magoi_vasc, ""))
                      laphd.CAPNHAT_STATUS_HDTB(
                        Number(dtList[i]["hdtb_id"]),
                        TRANGTHAI_DONGBO.DONGBO_CM
                      );
                    else {
                      Message_Box.ShowWarning(
                        "Có lỗi trong quá trình kích hoạt gói tích hợp trên VASC"
                      );
                      kichhoat = false;
                    }
                    //end///
                  }
                } else {
                  ///// Kích gói bình thường
                  //magoi_vasc = checkdata.MAP_ID("goi_vasc_id", DatabaseConstants.DB2 + ".goi_dadv", "where goi_id = " + goi_id);
                  magoi_vasc = laphd.Lay_goivasc_goi_dadv(goi_id, vtocdo_id, vmuccuoc_id);
                  if (magoi_vasc == "-1") {
                    this.$toast.error(
                      "Thông tin gói chưa được đồng bộ lên VASC. Bạn hãy kiểm tra lại !"
                    );
                    return;
                  }
                  //Lấy ra mã tốc độ VASC
                  matocdo_vasc = checkdata.MAP_ID(
                    "matocdo",
                    DatabaseConstants.DB2 + ".tocdo_adsl",
                    "where tocdo_id in (select tocdo_id from " +
                      DatabaseConstants.DB2 +
                      ".db_adsl where thuebao_id = '" +
                      thuebaoid +
                      "')"
                  );
                  if (matocdo_vasc == "-1") {
                    this.$toast.error(
                      "Thông tin về tốc độ chưa được đồng bộ lên VASC. Bạn hãy kiểm tra lại !"
                    );
                    return;
                  }
                  if (KichHoatVASC(dtList[i]["ma_tb"], matocdo_vasc, magoi_vasc, ""))
                    laphd.CAPNHAT_STATUS_HDTB(
                      Number(dtList[i]["hdtb_id"]),
                      TRANGTHAI_DONGBO.DONGBO_CM
                    );
                  else {
                    Message_Box.ShowWarning(
                      "Có lỗi trong quá trình kích hoạt gói tích hợp trên VASC"
                    );
                    kichhoat = false;
                  }
                  //end///
                }
              }
            }
          }
          //End
          if (!kichhoat) {
            this.$toast.error(
              "Bạn phải kích hoạt thuê bao đăng ký gói lên VDC/VASC trước khi hoàn thiện !"
            );
            return;
          }
          hoanthien.HOANTHIEN_HS_DK_GOI_DADV(hdkh_id);
          this.$toast.error("Hoàn thiện đăng ký gói đa dịch vụ thành công !");
          dgrList.dataSource = null;
          SetButton(0);
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: \n" + err.message);
      }
    },

    tsbtnThoat_Click() {
      this.Close();
    },

    ///// Set button
    SetButton(kieu) {
      this.$refs.tsbtnNhapMoi.classList.add("disabled");
      //tsbtnGhiLai.classList.add("disabled");
      //tsbtnXoa.classList.add("disabled");
      this.$refs.tsbtnHuyBo.classList.add("disabled");
      this.$refs.tsbtnThemTB.classList.add("disabled");
      this.$refs.tsbtnXoaTB.classList.add("disabled");
      //tsbtnCapNhatDB.classList.add("disabled");
      if (kieu == -1) {
        //Bat dau
        //txtMaTB.focus();
        //tsbtnGhiLai.classList.remove("disabled");
        this.$refs.tsbtnHuyBo.classList.remove("disabled");
      }

      if (kieu == 0) {
        //Bat dau
        //txtMaTB.focus();
        this.$refs.tsbtnNhapMoi.classList.remove("disabled");
        this.Clear();
      }

      if (kieu == 1) {
        //Them moi
        //txtMaTB.focus();
        //tsbtnGhiLai.classList.remove("disabled");
        this.$refs.tsbtnHuyBo.classList.remove("disabled");
        this.Clear();
      }

      if (kieu == 2) {
        //Huy
        this.$refs.txtMaGD.focus();
        this.$refs.tsbtnNhapMoi.classList.remove("disabled");
        //tsbtnXoa.classList.remove("disabled");
        this.Clear();
      }

      if (kieu == 3) {
        //Edit
        this.$refs.txtMaGD.focus();

        this.$refs.tsbtnNhapMoi.classList.remove("disabled");
        //tsbtnXoa.classList.remove("disabled");
        //tsbtnGhiLai.classList.remove("disabled");
        this.$refs.tsbtnHuyBo.classList.remove("disabled");
        this.$refs.tsbtnXoaTB.classList.remove("disabled");
        this.$refs.tsbtnThemTB.classList.remove("disabled");
        //tsbtnCapNhatDB.classList.remove("disabled");
        if (this.$refs.dgrList && this.$refs.dgrList.dataSource.length > 0)
          this.$refs.tsbtnXoaTB.classList.remove("disabled");
        else this.$refs.tsbtnXoaTB.classList.add("disabled");
      }
    },
    //end///

    ///// Clear
    Clear() {
      try {
        this.hdkh_id = 0;
        this.nhomtb_id = 0;
        this.$refs.txtMaGD.value = "";
        this.$refs.txtMaKH.value = "";
        this.khachhang_id = 0;
        this.$refs.txtTenKH.value = "";
        this.$refs.txtDiaChiKH.value = "";
        this.$refs.dtpNgayYeuCau.value = moment().format("DD/MM/YYYY HH:mm:ss");
        this.khlon_id = 0;
        this.hdtb_id = 0;
        this.thuebao_id = 0;
        //cboKieuLD.SelectedIndex = 0;

        //txtMaTB.focus();
        this.$refs.txtGhiChu.value = "";
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },
    //end///

    txtMaTB_KeyPress() {
      try {
        if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
          //if (txtMaTB.value != "")
          //{
          //    ds = tchopdong.LAY_DS_HOPDONG_THEO_MA_TB(txtMaTB.value, LoaiHopDong.DANGKY_GOI_DADV, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0, dichvuvt_id);
          //    if (ds.length > 0)
          //    {
          //        //if (dgrList.count > 0) dgrList.dataSource = null;
          //        //thuebao_id = Number(ds[0]["thuebao_id"]);
          //        // Bổ sung hàm kiểm tra thuê bao có đang lập phụ lục khác không
          //        HienThiTTHopDongKH(ds);
          //    }
          //    else
          //    {
          //        HienThiTT_DanhBa(txtMaTB.value);
          //    }
          //}
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    ///// HienThiTTHopDongKH
    HienThiTTHopDongKH(ds) {
      try {
        if (ds && ds.length > 0) {
          this.hdkh_id = Number(ds[0]["hdkh_id"]);
          this.$refs.txtMaGD.value = ds[0]["ma_gd"];
          this.$refs.txtMaKH.value = ds[0]["ma_kh"];
          if (ds[0]["khachhang_id"] != "")
            this.khachhang_id = Number(ds[0]["khachhang_id"]);
          this.khlon_id = Number(ds[0]["khlon_id"]);
          this.$refs.txtTenKH.value = ds[0]["ten_kh"];
          this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
          if (ds[0]["ngay_yc"] != "") this.$refs.dtpNgayYeuCau.value = ds[0]["ngay_yc"];
          this.$refs.txtGhiChu.value = ds[0]["ghichu"];
          this.SetButton(3);
          this.HienThiDanhSacHDTB();
        } else {
          this.$refs.dgrList.dataSource = [];
          this.SetButton(0);
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },
    //end///

    ///// HienThiDanhSacHDTB
    async HienThiDanhSacHDTB() {
      try {
        this.dtListTB = await this.LayDsThueBao();
        this.$refs.dgrListKH.dataSource = this.dtListTB;
        //dtList = tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id);
        this.dtList = await this.LayDsThueBaoDaDKy();
        this.$refs.dgrList.dataSource = this.dtList;
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },
    //end///

    ///// HienThiTT_DanhBa
    async HienThiTT_DanhBa(ma_tb) {
      this.dichvuvt_id = Number(this.$refs.cboDichVuVT.value);
      rs = await this.axios.post("web-hopdong/hopdong/lay_danhba_theo_matb", {
        maTb: ma_tb,
        dichVuVtId: this.dichvuvt_id,
        donViDlId: 0,
      });
      let ds = rs.data.data; //Lấy ra bất kỳ thuê bao nào
      this.dtThueBao = [ds];
      if (ds && ds.ma_tb) {
        ds = [ds];
        if (ds[0]["ma_kh"] == this.$refs.txtMaKH.value) {
          this.$toast.error("Thuê bao cùng mã khách hàng: " + this.$refs.txtMaKH.value);
          return;
        }
        this.thuebao_id = Number(ds[0]["thuebao_id"]);
        //Thông tin khách hàng
        this.$refs.txtTenTB.value = ds[0]["ten_tb"];
        this.$refs.txtDiaChiTB.value = ds[0]["diachi_tb"];
        this.ten_tb = ds[0]["ten_tb"];
        this.diachi_ld = ds[0]["diachi_ld"];
        this.loaitb_id = Number(ds[0]["loaitb_id"]);
        this.donvi_id = Number(ds[0]["donviql_id"]);
        this.doituong_id = Number(ds[0]["doituong_id"]);
        this.khlon_id = Number(ds[0]["khlon_id"]);
        this.hdtb_id = 0;
      } else {
        this.$toast.error(
          "Không tìm thấy thông tin về mã thuê bao " +
            this.$refs.txtMaTB.value +
            " đối với dịch vụ " +
            this.$refs.cboDichVuVT.value
        );
      }
    },
    //end///

    cboDichVuVT_valueChanged() {},

    bntLayMaKh_Click() {
      // frmTraCuuDanhBa f = new frmTraCuuDanhBa(0, 1);
      // f.ShowDialog();
      // if (f.chapnhan)
      // {
      //     //txtMaTB.value = f.ma_tb;
      //     //txtMaTB.focus();
      // }
    },

    btnLayMaGD_Click() {
      try {
        this.$refs.searchContractModal.clearData();
        this.modalSearch.loaihd_id = LoaiHopDong.DAT_MOI;
        this.modalSearch.tthd_id = TrangThaiHD.MOI;
        this.$refs.searchContractModal.loadPopup();
        this.$refs.popupFrmTraCuuHopDong.show();
      } catch (err) {
        this.$toast.error("" + ex);
      }
    },
    async acceptSearchContract(item) {
      this.$refs.popupFrmTraCuuHopDong.hide();
      this.$refs.txtMaGD.value = item.hopdong.ma_gd ? item.hopdong.ma_gd : "";
      this.$refs.txtMaGD.focus();
      if (this.$refs.txtMaGD.value) {
        this.txtMaGD_KeyPress();
      }
    },

    btnTenGoi_Click() {
      try {
        this.$refs.ql_congvan.l_goi_id = this.$refs.cboTenGoi_DV.value;
        setTimeout(() => {
          this.$refs.ql_congvan.showModal();
        }, 100);
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    ///// "Kiem tra thue bao da co trong luoi chua"
    //Hàm kiểm tra mã thuê bao nhập vào đã có trong ds hợp đồng thuê bao chưa
    //Lam thêm ngày 26/08/2010
    KiemTra_TonTai_HD(matb, dichvuvt, nhomtb) {
      //if (dgrList.count > 0)
      //{
      //    for (int i = 0; i < dtList.count; i++)
      //    {
      //        if (matb == dtList[0]["ma_tb"])
      //        {
      //            return true;
      //        }
      //    }
      //}
      if (bangts.KiemTraTB_TonTai_HD_GOIDAV(matb, dichvuvt, nhomtb)) {
        return true;
      }
      return false;
    },
    //end///

    dgrList_SelectionChanged() {
      try {
        if (this.$refs.dgrList.getSelectedRecords().length <= 0) return;
        if (this.$refs.dgrList.getSelectedRecords().length > 0) {
          let dtList = this.$refs.dgrList.getSelectedRecords();
          let k = 0;
          this.hdtb_id = Number(dtList[k]["HDTB_ID"]);
          //txtMaTB.value = dtList[k]["ma_tb"];
          this.$refs.cboTenGoi_DV.value = Number(dtList[k]["GOI_ID"]);
          if (Number(dtList[k]["CHUNHOM"]) == 1) this.$refs.chkChuNhom.checked = true;
          else this.$refs.chkChuNhom.checked = false;
          let thang_huong = dtList[k]["THANG_HUONG"];
          if (thang_huong == 0 || thang_huong == 1) {
            this.$refs.cboHieuLuc.value = thang_huong;
          } else {
            this.$refs.cboHieuLuc.value = 2;
            this.$refs.txtSoThang.value = thang_huong;
          }

          this.SetButton(3);
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    async txtMaGD_KeyPress() {
      try {
        if (this.$refs.txtMaGD.value != "") {
          rs = await this.axios.post("web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd", {
            in_ma_gd: this.$refs.txtMaGD.value,
            in_loaihd_id: 0,
            in_donvi_id: this.$root.token.getDonViID(),
            in_tthd_id: TrangThaiHD.MOI,
            in_nhanvien_id: this.$root.token.getNhanVienID(),
            in_donvi_dl_id: 0,
          });
          this.HienThiTTHopDongKH(rs.data.data);
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    cboHieuLuc_SelectedIndexChanged() {
      if (cboHieuLuc.value == null) return;
      if (cboHieuLuc.value == "System.Data.DataRowView") return;
      lbSoThang.Visible = txtSoThang.Visible = cboHieuLuc.value == "2";
    },

    // ,dgrList_CellContentClick()
    // {
    //     try
    //     {
    //         int nIndex = e.RowIndex;
    //         ma_tb = dgrList.CurrentRow.Cells["vnhomtb_id"].value;
    //         int i = 0;
    //         if (e.ColumnIndex == 0)
    //         {
    //             if (Number(dgrList.CurrentRow.Cells["chk2"].value) == 1)
    //             {
    //                 dgrList.CurrentRow.Cells["chk2"].value = false;
    //                 for (i = nIndex - 1; i >= 0; i--)
    //                 {
    //                     if (dgrList[i].Cells["vnhomtb_id"].value == ma_tb)
    //                     {
    //                         dgrList[i].Cells["chk2"].value = false;
    //                         foreach (DataGridViewColumn column in dgrList.Columns)
    //                             dgrList[column.Index, i].Style.ForeColor = Color.Black;
    //                     }
    //                     else
    //                         break;
    //                 }
    //                 for (i = nIndex; i < dgrList.count; i++)
    //                 {
    //                     if (dgrList[i].Cells["vnhomtb_id"].value == ma_tb)
    //                     {
    //                         dgrList[i].Cells["chk2"].value = false;
    //                         foreach (DataGridViewColumn column in dgrList.Columns)
    //                             dgrList[column.Index, i].Style.ForeColor = Color.Black;
    //                     }
    //                     else
    //                         break;
    //                 }
    //             }
    //             else
    //             {
    //                 dgrList.CurrentRow.Cells["chk2"].value = true;
    //                 for (i = nIndex - 1; i >= 0; i--)
    //                 {
    //                     if (dgrList[i].Cells["vnhomtb_id"].value == ma_tb)
    //                     {
    //                         dgrList[i].Cells["chk2"].value = true;
    //                         foreach (DataGridViewColumn column in dgrList.Columns)
    //                             dgrList[column.Index, i].Style.ForeColor = Color.Red;
    //                     }
    //                     else
    //                         break;
    //                 }
    //                 for (i = nIndex; i < dgrList.count; i++)
    //                 {
    //                     if (dgrList[i].Cells["vnhomtb_id"].value == ma_tb)
    //                     {
    //                         dgrList[i].Cells["chk2"].value = true;
    //                         foreach (DataGridViewColumn column in dgrList.Columns)
    //                             dgrList[column.Index, i].Style.ForeColor = Color.Red;
    //                     }
    //                     else
    //                         break;
    //                 }
    //             }
    //         }
    //     }
    //     catch (err)
    //     {
    //         throw ex;
    //     }
    // }

    btnTKMaTB_Click() {
      this.popupComponent = () => import("@/modules/search/subscriber/SearchAccount");
      this.popupComponentName = "SearchAccount";
      try {
        this.popupComponent.data.isPopup = true;
        this.popupComponent.data.vdichvuvt_id = 0;
      } catch (ex) {
        if (this.debug) console.error(ex);
      }
      this.Popup("popupComponents_frmDKGoiDaDV");
      // frmTraCuuDanhBa f = new frmTraCuuDanhBa(0, 1);
      // f.ShowDialog();
      // if (f.chapnhan)
      // {
      //     this.txtMaTB.value = f.ma_tb;
      //     this.txtMaTB.focus();
      // }
    },

    Popup(modalId) {
      this.$bvModal.show(modalId);
    },

    txtMaTB_KeyPress_1() {
      try {
        if (this.$refs.txtMaTB.value != "") {
          this.HienThiTT_DanhBa(this.$refs.txtMaTB.value);
        }
      } catch (err) {
        this.$toast.error("" + ex);
      }
    },

    btnThemTBNgoaiKH_Click() {
      try {
        if (this.$refs.txtMaTB.value != "") {
          this.ThemHDThueBao2();
        }
      } catch (err) {
        this.$toast.error("" + ex);
      }
    },

    btnTraTruocV2_click: function () {
      try {
        // this.popupComponent.data.isPopup = true;
        this.popupComponentData = {
          isPopup: true,
          tag: "1",
          hdkh_id: this.hdkh_id,
          khachhang_id: this.khachhang_id,
          dichvu_vt: this.dichvuvt_id,
          loaitb_id: this.loaitb_id,
          ma_gd: this.$refs.txtMaGD.value,
        };
        this.popupComponent = () =>
          import("@/modules/contract/setup/PrepaidDepositeV2/index.js");
        this.popupComponentName = "popupDK_TraTruoc";
        this.Popup("popupComponents_frmDKGoiDaDV");
      } catch (ex) {
        this.$toast.error("" + ex);
        if (this.debug) console.error(ex);
      }
    },

    readOnlySomay() {
      return true;
    },

    cboTenGoi_DV_EditValueChanged() {
      let selected = this.$refs.cboTenGoi_DV.dataSource.filter(
        (item) => item.goi_id == this.$refs.cboTenGoi_DV.value
      );
      if (selected.length > 0) {
        this.$refs.txtMoTa.value = selected[0].mota;
        if (this.$refs.cboTenGoi_DV.value == 3)
          this.$refs.btnThemTBNgoaiKH.classList.remove("disabled");
      }
    },
  },
};
</script>
<style>
#frmDangKyGoiDaDVLM .info-row .value {
  vertical-align: bottom !important;
}

.modal-content .page-content {
  position: unset;
}

.modal-content .main-wrapper {
  position: unset;
}

.modal .modal-huge {
  max-width: 95% !important;
  width: 100% !important;
}
</style>
