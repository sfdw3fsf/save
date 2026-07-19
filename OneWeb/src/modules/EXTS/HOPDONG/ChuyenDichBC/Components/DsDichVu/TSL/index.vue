<template src="./index.html"></template>
<script>
import ModalChonDiaChi from "@/modules/EXTS/HOPDONG/ChuanHoaDLKhachHang/popupChonDiaChi";
import frmDiaChiBC from "@/modules/EXTS/HOPDONG/ChuyenDichBC/Components/Popups/frmDiaChiBC/frmDiaChiBC.vue";
import ChonDonVi from "@/modules/EXTS/HOPDONG/DichChuyenNew/components/ThongTinKhachHang/ChonDonVi";
import Enum from "../../../Enum.js";
import DateTimeLib from "@/utils/DateTimeLib";
export default {
  props: ["parent"],
  components: {
    ModalChonDiaChi,
    ChonDonVi,
    frmDiaChiBC,
  },
  data() {
    return {
      DichVuVienThong: {
        TSL: 9,
      },
      cboTocDoKenhTSL: {
        DataSource: [],
        Value: null,
      },
      cboTrangBi: {
        DataSource: [],
        Value: null,
      },
      cboChuQuan: {
        DataSource: [],
        Value: null,
      },
      txtMaHD_TTKD_TSL: {
        Text: "",
      },
      txtSoAoTSL_Dau: {
        Text: "",
      },
      txtSoAoTSL_Cuoi: {
        Text: null,
      },
      cboLoaiTBTSL_Dau: {
        DataSource: [],
        Value: null,
      },
      cboLoaiTBTSL_Cuoi: {
        DataSource: [],
        Value: null,
      },
      txtCuocTK_TSL: {
        Text: 0,
      },
      txtCuocTC_TSL: {
        Text: 0,
      },
      chkDoiTac_TSL: {
        Checked: false,
      },
      cboDoiTac_TSL: {
        DataSource: [],
        Value: null,
        Enabled: false,
      },
      txtCuocTKDT_TSL: {
        Text: 0,
        Enabled: false,
      },
      txtCuocHT_TSL: {
        Text: 0,
      },
      txtMa_HDDT_TSL: {
        Text: "",
        Enabled: false,
      },
      txtCuocTCDT_TSL: {
        Text: 0,
        Enabled: false,
      },
      txtCuocTBI_TSL: {
        Text: 0,
      },
      chkDC_Dau: {
        Checked: false,
      },
      chkDC_Cuoi: {
        Checked: false,
      },
      txtDiaChiDau: {
        Text: "",
        Enabled: false,
      },
      txtDiaChiCuoi: {
        Text: "",
        Enabled: false,
      },
      cboTramVTDau: {
        DataSource: [],
        Value: null,
        Enabled: false,
      },
      cboTramVTCuoi: {
        DataSource: [],
        Value: null,
        Enabled: false,
      },
      cboLoaiCap_Dau: {
        DataSource: [],
        Value: null,
      },
      cboLoaiCap_Cuoi: {
        DataSource: [],
        Value: null,
      },
      btnChonDCDau: {
        Enabled: false,
      },
      btnChonDCCuoi: {
        Enabled: false,
      },
      diaChiDau: {
        tieuDe: "Địa chỉ",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        so_nha: "",
        diachi: null,
      },
      diaChiDauDuocChon: null,
      diaChiCuoi: {
        tieuDe: "Địa chỉ",
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        so_nha: "",
        diachi: null,
      },
      diaChiCuoiDuocChon: null,
      dsComboDonVi: [],
    };
  },
  computed: {
    idDichVu() {
      return parseInt(this.parent.parent.modelCbo.cboDichVuVT);
    },
    isActive() {
      return this.idDichVu == this.DichVuVienThong.TSL;
    },
    idLoaiHinhTb() {
      return parseInt(this.parent.parent.modelCbo.cboLoaihinhTB);
    },
    idKieuLd() {
      return parseInt(this.parent.parent.modelCbo.cboKieuLD);
    },
    vloaikenh_id() {
      return this.parent.parent.vloaikenh_id;
    },
    idDaiVt() {
      return this.parent.parent.modelCbo.cboDaiVT;
    },
  },
  async mounted() {
    this.cboTocDoKenhTSL.DataSource = await this.lay_ds_tocdo();
    this.cboTocDoKenhTSL.Value = this.cboTocDoKenhTSL.DataSource[0].tocdo_id;

    this.cboTrangBi.DataSource = await this.lay_ds_trangbi();
    this.cboTrangBi.Value = this.cboTrangBi.DataSource[0].trangbi_id;

    this.cboChuQuan.DataSource = await this.lay_ds_chuquan();
    this.cboChuQuan.Value = this.cboChuQuan.DataSource[0].chuquan_id;

    let dsLoaiThietBi = await this.lay_ds_loai_tb();
    this.cboLoaiTBTSL_Dau.DataSource = dsLoaiThietBi.map((item) => {
      return { ...item };
    });
    this.cboLoaiTBTSL_Dau.Value = this.cboLoaiTBTSL_Dau.DataSource[0].thietbidc_id;

    this.cboLoaiTBTSL_Cuoi.DataSource = dsLoaiThietBi.map((item) => {
      return { ...item };
    });
    this.cboLoaiTBTSL_Cuoi.Value = this.cboLoaiTBTSL_Cuoi.DataSource[0].thietbidc_id;

    this.cboDoiTac_TSL.DataSource = await this.lay_ds_doitac();
    this.cboDoiTac_TSL.Value = this.cboDoiTac_TSL.DataSource[0].doitac_id;

    let dsLoaiCap = await this.lay_ds_loai_cap();
    this.cboLoaiCap_Dau.DataSource = dsLoaiCap.map((item) => {
      return { ...item };
    });
    this.cboLoaiCap_Dau.Value = this.cboLoaiCap_Dau.DataSource[0].loaicap_id;

    this.cboLoaiCap_Cuoi.DataSource = dsLoaiCap.map((item) => {
      return { ...item };
    });
    this.cboLoaiCap_Cuoi.Value = this.cboLoaiCap_Cuoi.DataSource[0].loaicap_id;
  },
  watch: {
    idLoaiHinhTb: async function () {
      if (this.isActive) {
        await this.HT_COMBO_DONVI();
      }
    },
    "cboTocDoKenhTSL.Value": function () {
      this.cboTocDoKenhTSL_SelectedValueChanged();
    },
    "chkDC_Cuoi.Checked": function () {
      this.chkDC_Cuoi_CheckedChanged();
    },
    "chkDC_Dau.Checked": function () {
      this.chkDC_Dau_CheckedChanged();
    },
    "chkDoiTac_TSL.Checked": function () {
      this.chkDoiTac_TSL_CheckedChanged();
    },
    "cboTrangBi.Value": function () {
      this.cboTrangBi_SelectedValueChanged();
    },
  },
  methods: {
    async TaoDuLieu_HDBC_TSL(themmoi, vdaucuoi_id, dsHDTB_TSL) {
      let dsHDBCTSL = [];
      let ds = [];
      let kenh_trang = this.parent.parent.kenh_trang;
      let thongso_tc_dau = this.parent.parent.thongso_tc_dau;
      console.log(
        "🚀 ~ file: index.vue:223 ~ TaoDuLieu_HDBC_TSL ~ thongso_tc_dau:",
        thongso_tc_dau
      );
      let thongso_tc_cuoi = this.parent.parent.thongso_tc_cuoi;
      console.log(
        "🚀 ~ file: index.vue:225 ~ TaoDuLieu_HDBC_TSL ~ thongso_tc_cuoi:",
        thongso_tc_cuoi
      );
      if (kenh_trang) {
        ds = dsHDTB_TSL;
        for (let i = 0; i < ds.length; i++) {
          let rowData = { ...ds[i] };
          let rowHDBCTSL = {};
          Object.keys(rowData).forEach(
            (key) => (rowHDBCTSL[key.toUpperCase()] = rowData[key])
          );
          rowHDBCTSL.HDTB_ID = this.parent.parent.hdtb_id;
          rowHDBCTSL.TINH_DC_ID = vdaucuoi_id;
          if (vdaucuoi_id == 1) {
            rowHDBCTSL.CHUQUAN_ID = thongso_tc_dau.chuquan_id;
          } else {
            rowHDBCTSL.CHUQUAN_ID = thongso_tc_cuoi.chuquan_id;
          }
          if (rowHDBCTSL.DAUCUOI_ID == 1) {
            rowHDBCTSL.TOCDO_ID = thongso_tc_dau.tocdo_id;
            rowHDBCTSL.TRANGBI_ID = thongso_tc_dau.trangbi_id;
          } else {
            rowHDBCTSL.TOCDO_ID = thongso_tc_cuoi.tocdo_id;
            rowHDBCTSL.TRANGBI_ID = thongso_tc_cuoi.trangbi_id;
          }
          rowHDBCTSL.MA_PL = this.txtMaHD_TTKD_TSL.Text;
          let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID);
          if (KetCuoi_ID > 0) {
            rowHDBCTSL.KETCUOI_ID = KetCuoi_ID;
          }
          if (this.parent.parent.modelCkeckBox.chkCongVan) {
            rowHDBCTSL.MA_CV = this.parent.parent.modelInput.txtCongVan;
            rowHDBCTSL.NGAY_CV = DateTimeLib.toDateDisplay(
              this.parent.parent.modelDateTime.dtpNgayCV
            );
          } else {
            rowHDBCTSL.MA_CV = null;
            rowHDBCTSL.NGAY_CV = null;
          }

          if (!this.txtCuocTC_TSL.Text) {
            rowHDBCTSL.CUOC_TC = 0;
          } else {
            rowHDBCTSL.CUOC_TC = parseFloat(this.txtCuocTC_TSL.Text);
          }

          if (!this.txtCuocTK_TSL.Text) {
            rowHDBCTSL.CUOC_TK = 0;
          } else {
            rowHDBCTSL.CUOC_TK = parseFloat(this.txtCuocTK_TSL.Text);
          }

          if (this.txtCuocHT_TSL.Text) {
            rowHDBCTSL.CUOC_HT = 0;
          } else {
            rowHDBCTSL.CUOC_HT = parseFloat(this.txtCuocHT_TSL.Text);
          }

          if (this.txtCuocTBI_TSL.Text) {
            rowHDBCTSL.CUOC_TBI = 0;
          } else {
            rowHDBCTSL.CUOC_TBI = parseFloat(this.txtCuocTBI_TSL.Text);
          }

          if (this.chkDoiTac_TSL.Checked) {
            rowHDBCTSL.DOITAC_ID = parseInt(this.cboDoiTac_TSL.Value);

            if (this.txtCuocTKDT_TSL.Text) {
              rowHDBCTSL.CUOC_TKDT = parseFloat(this.txtCuocTKDT_TSL.Text);
            } else {
              rowHDBCTSL.CUOC_TKDT = 0;
            }

            if (this.txtCuocTCDT_TSL.Text) {
              rowHDBCTSL.CUOC_TCDT = parseFloat(this.txtCuocTCDT_TSL.Text);
            } else {
              rowHDBCTSL.CUOC_TCDT = 0;
            }

            rowHDBCTSL.MAHD_DT = this.txtMa_HDDT_TSL.Text;
          }

          rowHDBCTSL.DC_DAUCUOI = rowHDBCTSL.DAUCUOI_ID;
          rowHDBCTSL.MA_PL = this.txtMaHD_TTKD_TSL.Text;

          if (rowHDBCTSL.DAUCUOI_ID == 1) {
            rowHDBCTSL.DIACHI_ID = this.parent.parent.diachidau_id;
            rowHDBCTSL.MA_LT = this.parent.parent.malt_dau;
            rowHDBCTSL.MA_TB_SUB = this.parent.parent.ma_tb_dau;
            rowHDBCTSL.TINH_ID = this.parent.parent.tinh_dau_id;
          } else {
            rowHDBCTSL.DIACHI_ID = this.parent.parent.diachicuoi_id;
            rowHDBCTSL.MA_LT = this.parent.parent.malt_cuoi;
            rowHDBCTSL.MA_TB_SUB = this.parent.parent.ma_tb_cuoi;
            rowHDBCTSL.TINH_ID = this.parent.parent.tinh_cuoi_id;
          }

          rowHDBCTSL.KINHDO_LD = this.parent.parent.Lng_DD;
          rowHDBCTSL.VIDO_LD = this.parent.parent.Lat_DD;
          rowHDBCTSL.ID_ERP = this.parent.parent.modelInput.txtMaERP;
          rowHDBCTSL.MA_BSS = this.parent.parent.modelInput.txtMaBSS;

          dsHDBCTSL.push(rowHDBCTSL);
        }
      } else {
        let dt_tc = [];

        if (vdaucuoi_id == 1) {
          let tinh_id = this.parent.parent.tinh_dau.tinhthicong_id;
          let thueBaoId = this.parent.parent.thuebao_id_dau;
          dt_tc = await this.BANCHEO_LAY_DS_DB_TSL_THEO_TBID({ tinh_id, thueBaoId });
        } else {
          let tinh_id = this.parent.parent.tinh_cuoi.tinhthicong_id;
          let thueBaoId = this.parent.parent.thuebao_id_cuoi;
          dt_tc = await this.BANCHEO_LAY_DS_DB_TSL_THEO_TBID({ tinh_id, thueBaoId });
        }

        for (let i = 0; i < dt_tc.length; i++) {
          let rowData = { ...dt_tc[i] };
          let rowHDBCTSL = {};
          Object.keys(rowData).forEach(
            (key) => (rowHDBCTSL[key.toUpperCase()] = rowData[key])
          );

          rowHDBCTSL.HDTB_ID = this.parent.parent.hdtb_id;
          rowHDBCTSL.DAUCUOI_ID = parseInt(dt_tc[i]["daucuoi_id"]);
          rowHDBCTSL.TOCDO_ID = parseInt(dt_tc[i]["tocdo_id"]);
          rowHDBCTSL.TRANGBI_ID = parseInt(dt_tc[i]["trangbi_id"]);
          rowHDBCTSL.CHUQUAN_ID = parseInt(dt_tc[i]["chuquan_id"]);
          rowHDBCTSL.MA_PL = this.txtMaHD_TTKD_TSL.Text;
          rowHDBCTSL.DC_DAUCUOI = vdaucuoi_id;
          rowHDBCTSL.ID_ERP = this.parent.parent.modelInput.txtMaERP;
          rowHDBCTSL.MA_BSS = this.parent.parent.modelInput.txtMaBSS;

          let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID);

          if (KetCuoi_ID > 0) {
            rowHDBCTSL.KETCUOI_ID = KetCuoi_ID;
          }

          if (this.parent.parent.modelCkeckBox.chkCongVan) {
            rowHDBCTSL.MA_CV = this.parent.parent.modelInput.txtCongVan;
            rowHDBCTSL.NGAY_CV = DateTimeLib.toDateDisplay(
              this.parent.parent.modelDateTime.dtpNgayCV
            );
          } else {
            rowHDBCTSL.MA_CV = null;
            rowHDBCTSL.NGAY_CV = null;
          }

          if (!this.txtCuocTC_TSL.Text) {
            rowHDBCTSL.CUOC_TC = 0;
          } else {
            rowHDBCTSL.CUOC_TC = parseFloat(this.txtCuocTC_TSL.Text);
          }

          if (!this.txtCuocTK_TSL.Text) {
            rowHDBCTSL.CUOC_TK = 0;
          } else {
            rowHDBCTSL.CUOC_TK = parseFloat(this.txtCuocTK_TSL.Text);
          }

          if (this.txtCuocHT_TSL.Text) {
            rowHDBCTSL.CUOC_HT = 0;
          } else {
            rowHDBCTSL.CUOC_HT = parseFloat(this.txtCuocHT_TSL.Text);
          }

          if (this.txtCuocTBI_TSL.Text) {
            rowHDBCTSL.CUOC_TBI = 0;
          } else {
            rowHDBCTSL.CUOC_TBI = parseFloat(this.txtCuocTBI_TSL.Text);
          }

          if (this.chkDoiTac_TSL.Checked) {
            rowHDBCTSL.DOITAC_ID = parseInt(this.cboDoiTac_TSL.Value);

            if (this.txtCuocTKDT_TSL.Text) {
              rowHDBCTSL.CUOC_TKDT = parseFloat(this.txtCuocTKDT_TSL.Text);
            } else {
              rowHDBCTSL.CUOC_TKDT = 0;
            }

            if (this.txtCuocTCDT_TSL.Text) {
              rowHDBCTSL.CUOC_TCDT = parseFloat(this.txtCuocTCDT_TSL.Text);
            } else {
              rowHDBCTSL.CUOC_TCDT = 0;
            }

            rowHDBCTSL.MAHD_DT = this.txtMa_HDDT_TSL.Text;
          }

          if (rowHDBCTSL.DAUCUOI_ID == 1) {
            rowHDBCTSL.DIACHI_ID = this.parent.parent.diachidau_id;
            rowHDBCTSL.MA_LT = this.parent.parent.malt_dau;
            rowHDBCTSL.MA_TB_SUB = this.parent.parent.ma_tb_dau;
            rowHDBCTSL.TINH_ID = this.parent.parent.tinh_dau_id;

            let tinh_id = this.parent.parent.tinh_dau.tinhthicong_id;
            let thueBaoId = this.parent.parent.thuebao_id_dau;
            ds = await this.BANCHEO_LAY_DS_DB_TSL_THEO_TBID({ tinh_id, thueBaoId });
          } else {
            rowHDBCTSL.DIACHI_ID = this.parent.parent.diachicuoi_id;
            rowHDBCTSL.MA_LT = this.parent.parent.malt_cuoi;
            rowHDBCTSL.MA_TB_SUB = this.parent.parent.ma_tb_cuoi;
            rowHDBCTSL.TINH_ID = this.parent.parent.tinh_cuoi_id;

            let tinh_id = this.parent.parent.tinh_cuoi.tinhthicong_id;
            let thueBaoId = this.parent.parent.thuebao_id_cuoi;
            ds = await this.BANCHEO_LAY_DS_DB_TSL_THEO_TBID({ tinh_id, thueBaoId });
          }

          rowHDBCTSL.TINH_DC_ID = vdaucuoi_id;
          rowHDBCTSL.KINHDO_LD = this.parent.parent.Lng_DD;
          rowHDBCTSL.VIDO_LD = this.parent.parent.Lat_DD;
          rowHDBCTSL.THIETBIDC_ID = parseInt(dt_tc[i]["thietbidc_id"]);
          rowHDBCTSL.LOAIKENH_ID = parseInt(dt_tc[i]["loaikenh_id"]);

          if (ds.length > 0) {
            rowHDBCTSL.LOAICAP_ID = parseInt(ds[0]["loaicap_id"]);
            rowHDBCTSL.TEN_TBI = ds[0]["ten_tbi"];
            rowHDBCTSL.SERIAL = ds[0]["serial"];
            rowHDBCTSL.IP_WAN = ds[0]["ip_wan"];
            rowHDBCTSL.LINK = ds[0]["link"];
            rowHDBCTSL.TIMESLOT = ds[0]["timeslot"];

            let l_doicap_ngon = -1;
            let l_doicap_goc = -1;
            let l_loaicap_id = 0;
            let l_culy = 0;

            if (ds[0]["doicap_p"]) {
              l_doicap_ngon = parseInt(ds[0]["doicap_p"]);
            }

            if (ds[0]["doicap_goc"]) {
              l_doicap_goc = parseInt(ds[0]["doicap_goc"]);
            }

            if (ds[0]["loaicap_id"]) {
              l_loaicap_id = parseInt(ds[0]["loaicap_id"]);
            }

            if (ds[0]["culy"]) {
              l_culy = parseInt(ds[0]["culy"]);
            }

            rowHDBCTSL.TUYENCAP = ds[0]["tuyencap"];
            rowHDBCTSL.LIENTU = ds[0]["lientu"];
            rowHDBCTSL.HOPCAP_P = ds[0]["hopcap_p"];
            rowHDBCTSL.TUCAP_P = ds[0]["tucap_p"];
            rowHDBCTSL.CAP_GOC = ds[0]["cap_goc"];
            rowHDBCTSL.CAP_P = ds[0]["cap_p"];
            rowHDBCTSL.TUCAP_GOC = ds[0]["tucap_goc"];
            rowHDBCTSL.DOICAP_P = l_doicap_ngon;
            rowHDBCTSL.LOAICAP_ID = l_loaicap_id;
            rowHDBCTSL.DOICAP_GOC = l_doicap_goc;
            rowHDBCTSL.CULY = l_culy;

            if (ds[0]["tramtb_id"]) {
              rowHDBCTSL.TRAMTB_ID = parseInt(ds[0]["tramtb_id"]);
            }

            if (ds[0]["bras_id"]) {
              rowHDBCTSL.BRAS_ID = parseInt(ds[0]["bras_id"]);
            }

            if (ds[0]["dslam_id"]) {
              rowHDBCTSL.DSLAM_ID = parseInt(ds[0]["dslam_id"]);
            }

            if (ds[0]["port_id"]) {
              rowHDBCTSL.PORT_ID = parseInt(ds[0]["port_id"]);
            }

            if (ds[0]["vci_vpi_id"]) {
              rowHDBCTSL.VCI_VPI_ID = parseInt(ds[0]["vci_vpi_id"]);
            }
          }

          dsHDBCTSL.push(rowHDBCTSL);
        }
      }
      console.log('dsHDBCTSL', dsHDBCTSL)
      return { dsHDBCTSL };
    },
    async TaoDuLieu_HDTB_TSL(themmoi) {
      this.parent.parent.dc_daucuoi = 0;

      if (this.chkDC_Dau.Checked && this.chkDC_Cuoi.Checked) {
        this.parent.parent.dc_daucuoi = 3;
      } else {
        if (this.chkDC_Dau.Checked) {
          this.parent.parent.dc_daucuoi = 1;
        }

        if (this.chkDC_Cuoi.Checked) {
          this.parent.parent.dc_daucuoi = 2;
        }
      }

      let ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.parent.parent.thuebao_id);
      let dsHDTB_TSL = [];
      let dsHDTBDV = [];
      for (let i = 0; i < 2; i++) {
        let rowHDTB_TSL = {
          HDTB_ID: this.parent.parent.hdtb_id,
          DAUCUOI_ID: i + 1,
          TOCDO_ID: this.parent.parent._tocdo_id,
          TRANGBI_ID: parseInt(this.cboTrangBi.Value),
          CHUQUAN_ID: parseInt(this.cboChuQuan.Value),
          MA_PL: this.txtMaHD_TTKD_TSL.Text,
          DC_DAUCUOI: this.parent.parent.dc_daucuoi,
          ID_ERP: this.parent.parent.modelInput.txtMaERP,
          MA_BSS: this.parent.parent.modelInput.txtMaBSS,
        };

        let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID);

        if (KetCuoi_ID > 0) {
          rowHDTB_TSL.KETCUOI_ID = KetCuoi_ID;
        }

        if (this.parent.parent.modelCkeckBox.chkCongVan) {
          rowHDTB_TSL.MA_CV = this.parent.parent.modelInput.txtCongVan;
          rowHDTB_TSL.NGAY_CV = DateTimeLib.toDateDisplay(
            this.parent.parent.modelDateTime.dtpNgayCV
          );
        } else {
          rowHDTB_TSL.MA_CV = null;
          rowHDTB_TSL.NGAY_CV = null;
        }

        if (!this.txtCuocTC_TSL.Text) {
          rowHDTB_TSL.CUOC_TC = 0;
        } else {
          rowHDTB_TSL.CUOC_TC = parseFloat(this.txtCuocTC_TSL.Text);
        }

        if (!this.txtCuocTK_TSL.Text) {
          rowHDTB_TSL.CUOC_TK = 0;
        } else {
          rowHDTB_TSL.CUOC_TK = parseFloat(this.txtCuocTK_TSL.Text);
        }

        if (this.txtCuocHT_TSL.Text) {
          rowHDTB_TSL.CUOC_HT = 0;
        } else {
          rowHDTB_TSL.CUOC_HT = parseFloat(this.txtCuocHT_TSL.Text);
        }

        if (this.txtCuocTBI_TSL.Text) {
          rowHDTB_TSL.CUOC_TBI = 0;
        } else {
          rowHDTB_TSL.CUOC_TBI = parseFloat(this.txtCuocTBI_TSL.Text);
        }

        if (this.chkDoiTac_TSL.Checked) {
          rowHDTB_TSL.DOITAC_ID = parseInt(this.cboDoiTac_TSL.Value);

          if (this.txtCuocTKDT_TSL.Text) {
            rowHDTB_TSL.CUOC_TKDT = parseFloat(this.txtCuocTKDT_TSL.Text);
          } else {
            rowHDTB_TSL.CUOC_TKDT = 0;
          }

          if (this.txtCuocTCDT_TSL.Text) {
            rowHDTB_TSL.CUOC_TCDT = parseFloat(this.txtCuocTCDT_TSL.Text);
          } else {
            rowHDTB_TSL.CUOC_TCDT = 0;
          }

          rowHDTB_TSL.MAHD_DT = this.txtMa_HDDT_TSL.Text;
        }

        let row = {
          HDTB_ID: this.parent.parent.hdtb_id,
          LOAIDV_ID: Enum.LOAI_DV.TRAM_VT,
          KIEUDV_ID: i + 1,
        };

        if (i == 0) {
          // Nếu là điểm đầu
          rowHDTB_TSL.DIACHI = this.txtDiaChiDau.Text;
          rowHDTB_TSL.DIACHI_ID = this.parent.parent.diachidau_id;
          rowHDTB_TSL.KINHDO_LD = this.parent.parent.Lng_DD;
          rowHDTB_TSL.VIDO_LD = this.parent.parent.Lat_DD;
          rowHDTB_TSL.MA_TB_SUB = this.parent.parent.ma_tb_dau;
          rowHDTB_TSL.MA_LT = this.parent.parent.malt_dau;
          rowHDTB_TSL.THIETBIDC_ID = parseInt(this.cboLoaiTBTSL_Dau.Value);
          row.DONVI_ID = parseInt(this.cboTramVTDau.Value);

          if (ds.length > 0) {
            if (ds[0]["daucuoi_id"] == 1) {
              // điểm đầu
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[0]["loaikenh_id"]);
              rowHDTB_TSL.TINH_DC_ID = 1;
              rowHDTB_TSL.TINH_ID = this.parent.parent.tinh_dau.tinhthicong_id;

              if (this.parent.parent.dc_daucuoi == 2) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[0]["loaicap_id"]);
                rowHDTB_TSL.TEN_TBI = ds[0]["ten_tbi"];
                rowHDTB_TSL.SERIAL = ds[0]["serial"];
                rowHDTB_TSL.IP_WAN = ds[0]["ip_wan"];
                rowHDTB_TSL.LINK = ds[0]["link"];
                rowHDTB_TSL.TIMESLOT = ds[0]["timeslot"];

                let l_doicap_ngon = -1;
                let l_doicap_goc = -1;
                let l_loaicap_id = 0;
                let l_culy = 0;

                if (ds[0]["doicap_p"] != "") {
                  l_doicap_ngon = parseInt(ds[0]["doicap_p"]);
                }

                if (ds[0]["doicap_goc"] != "") {
                  l_doicap_goc = parseInt(ds[0]["doicap_goc"]);
                }

                if (ds[0]["loaicap_id"] != "") {
                  l_loaicap_id = parseInt(ds[0]["loaicap_id"]);
                }

                if (ds[0]["culy"] != "") {
                  l_culy = parseInt(ds[0]["culy"]);
                }

                rowHDTB_TSL.TUYENCAP = ds[0]["tuyencap"];
                rowHDTB_TSL.LIENTU = ds[0]["lientu"];
                rowHDTB_TSL.HOPCAP_P = ds[0]["hopcap_p"];
                rowHDTB_TSL.TUCAP_P = ds[0]["tucap_p"];
                rowHDTB_TSL.CAP_GOC = ds[0]["cap_goc"];
                rowHDTB_TSL.CAP_P = ds[0]["cap_p"];
                rowHDTB_TSL.TUCAP_GOC = ds[0]["tucap_goc"];
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                rowHDTB_TSL.CULY = l_culy;

                if (ds[0]["tramtb_id"] != "") {
                  rowHDTB_TSL.TRAMTB_ID = parseInt(ds[0]["tramtb_id"]);
                }

                if (ds[0]["bras_id"] != "") {
                  rowHDTB_TSL.BRAS_ID = parseInt(ds[0]["bras_id"]);
                }

                if (ds[0]["dslam_id"] != "") {
                  rowHDTB_TSL.DSLAM_ID = parseInt(ds[0]["dslam_id"]);
                }

                if (ds[0]["port_id"] != "") {
                  rowHDTB_TSL.PORT_ID = parseInt(ds[0]["port_id"]);
                }

                if (ds[0]["vci_vpi_id"] != "") {
                  rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[0]["vci_vpi_id"]);
                }
              } else {
                rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Dau.Value);
              }
            } else {
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[1]["loaikenh_id"]);

              if (this.parent.parent.dc_daucuoi == 2) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[1]["loaicap_id"]);
                rowHDTB_TSL.TEN_TBI = ds[1]["ten_tbi"];
                rowHDTB_TSL.SERIAL = ds[1]["serial"];
                rowHDTB_TSL.IP_WAN = ds[1]["ip_wan"];
                rowHDTB_TSL.LINK = ds[1]["link"];
                rowHDTB_TSL.TIMESLOT = ds[1]["timeslot"];

                let l_doicap_ngon = -1;
                let l_doicap_goc = -1;
                let l_loaicap_id = 0;
                let l_culy = 0;

                if (ds[1]["doicap_p"] != "") {
                  l_doicap_ngon = parseInt(ds[1]["doicap_p"]);
                }

                if (ds[1]["doicap_goc"] != "") {
                  l_doicap_goc = parseInt(ds[1]["doicap_goc"]);
                }

                if (ds[1]["loaicap_id"] != "") {
                  l_loaicap_id = parseInt(ds[1]["loaicap_id"]);
                }

                if (ds[1]["culy"] != "") {
                  l_culy = parseInt(ds[1]["culy"]);
                }

                rowHDTB_TSL.TUYENCAP = ds[1]["tuyencap"];
                rowHDTB_TSL.LIENTU = ds[1]["lientu"];
                rowHDTB_TSL.HOPCAP_P = ds[1]["hopcap_p"];
                rowHDTB_TSL.TUCAP_P = ds[1]["tucap_p"];
                rowHDTB_TSL.CAP_GOC = ds[1]["cap_goc"];
                rowHDTB_TSL.CAP_P = ds[1]["cap_p"];
                rowHDTB_TSL.TUCAP_GOC = ds[1]["tucap_goc"];
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                rowHDTB_TSL.CULY = l_culy;

                if (ds[1]["tramtb_id"] != "") {
                  rowHDTB_TSL.TRAMTB_ID = parseInt(ds[1]["tramtb_id"]);
                }

                if (ds[1]["bras_id"] != "") {
                  rowHDTB_TSL.BRAS_ID = parseInt(ds[1]["bras_id"]);
                }

                if (ds[1]["dslam_id"] != "") {
                  rowHDTB_TSL.DSLAM_ID = parseInt(ds[1]["dslam_id"]);
                }

                if (ds[1]["port_id"] != "") {
                  rowHDTB_TSL.PORT_ID = parseInt(ds[1]["port_id"]);
                }

                if (ds[1]["vci_vpi_id"] != "") {
                  rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[1]["vci_vpi_id"]);
                }
              } else {
                rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Dau.Value);
              }
            }
          }
        } // Điểm cuối
        else {
          rowHDTB_TSL.DIACHI = this.txtDiaChiCuoi.Text;
          rowHDTB_TSL.DIACHI_ID = this.parent.parent.diachicuoi_id;
          rowHDTB_TSL.KINHDO_LD = this.parent.parent.Lng_DC;
          rowHDTB_TSL.VIDO_LD = this.parent.parent.Lat_DC;
          rowHDTB_TSL.MA_TB_SUB = this.parent.parent.ma_tb_cuoi;
          rowHDTB_TSL.MA_LT = this.parent.parent.malt_cuoi;
          rowHDTB_TSL.THIETBIDC_ID = parseInt(this.cboLoaiTBTSL_Cuoi.Value);
          row.DONVI_ID = parseInt(this.cboTramVTCuoi.Value);

          if (ds.length > 0) {
            if (parseInt(ds[0]["daucuoi_id"]) == 2) {
              // điểm cuối
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[0]["loaikenh_id"]);
              rowHDTB_TSL.TINH_DC_ID = 2;
              rowHDTB_TSL.TINH_ID = this.parent.parent.tinh_cuoi.tinhthicong_id;

              if (this.parent.parent.dc_daucuoi == 1) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[0]["loaicap_id"]);
                rowHDTB_TSL.TEN_TBI = ds[0]["ten_tbi"];
                rowHDTB_TSL.SERIAL = ds[0]["serial"];
                rowHDTB_TSL.IP_WAN = ds[0]["ip_wan"];
                rowHDTB_TSL.LINK = ds[0]["link"];
                rowHDTB_TSL.TIMESLOT = ds[0]["timeslot"];

                let l_doicap_ngon = -1;
                let l_doicap_goc = -1;
                let l_loaicap_id = 0;
                let l_culy = 0;

                if (ds[0]["doicap_p"] != "") {
                  l_doicap_ngon = parseInt(ds[0]["doicap_p"]);
                }

                if (ds[0]["doicap_goc"] != "") {
                  l_doicap_goc = parseInt(ds[0]["doicap_goc"]);
                }

                if (ds[0]["loaicap_id"] != "") {
                  l_loaicap_id = parseInt(ds[0]["loaicap_id"]);
                }

                if (ds[0]["culy"] != "") {
                  l_culy = parseInt(ds[0]["culy"]);
                }

                rowHDTB_TSL.TUYENCAP = ds[0]["tuyencap"];
                rowHDTB_TSL.LIENTU = ds[0]["lientu"];
                rowHDTB_TSL.HOPCAP_P = ds[0]["hopcap_p"];
                rowHDTB_TSL.TUCAP_P = ds[0]["tucap_p"];
                rowHDTB_TSL.CAP_GOC = ds[0]["cap_goc"];
                rowHDTB_TSL.CAP_P = ds[0]["cap_p"];
                rowHDTB_TSL.TUCAP_GOC = ds[0]["tucap_goc"];
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                rowHDTB_TSL.CULY = l_culy;

                if (ds[0]["tramtb_id"] != "") {
                  rowHDTB_TSL.TRAMTB_ID = parseInt(ds[0]["tramtb_id"]);
                }

                if (ds[0]["bras_id"] != "") {
                  rowHDTB_TSL.BRAS_ID = parseInt(ds[0]["bras_id"]);
                }

                if (ds[0]["dslam_id"] != "") {
                  rowHDTB_TSL.DSLAM_ID = parseInt(ds[0]["dslam_id"]);
                }

                if (ds[0]["port_id"] != "") {
                  rowHDTB_TSL.PORT_ID = parseInt(ds[0]["port_id"]);
                }

                if (ds[0]["vci_vpi_id"] != "") {
                  rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[0]["vci_vpi_id"]);
                }
              } else {
                rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Cuoi.Value);
              }
            } else {
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[1]["loaikenh_id"]);

              if (this.parent.parent.dc_daucuoi == 1) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[1]["loaicap_id"]);
                rowHDTB_TSL.TEN_TBI = ds[1]["ten_tbi"];
                rowHDTB_TSL.SERIAL = ds[1]["serial"];
                rowHDTB_TSL.IP_WAN = ds[1]["ip_wan"];
                rowHDTB_TSL.LINK = ds[1]["link"];
                rowHDTB_TSL.TIMESLOT = ds[1]["timeslot"];

                let l_doicap_ngon = -1;
                let l_doicap_goc = -1;
                let l_loaicap_id = 0;
                let l_culy = 0;

                if (ds[1]["doicap_p"] != "") {
                  l_doicap_ngon = parseInt(ds[1]["doicap_p"]);
                }

                if (ds[1]["doicap_goc"] != "") {
                  l_doicap_goc = parseInt(ds[1]["doicap_goc"]);
                }

                if (ds[1]["loaicap_id"] != "") {
                  l_loaicap_id = parseInt(ds[1]["loaicap_id"]);
                }

                if (ds[1]["culy"] != "") {
                  l_culy = parseInt(ds[1]["culy"]);
                }

                rowHDTB_TSL.TUYENCAP = ds[1]["tuyencap"];
                rowHDTB_TSL.LIENTU = ds[1]["lientu"];
                rowHDTB_TSL.HOPCAP_P = ds[1]["hopcap_p"];
                rowHDTB_TSL.TUCAP_P = ds[1]["tucap_p"];
                rowHDTB_TSL.CAP_GOC = ds[1]["cap_goc"];
                rowHDTB_TSL.CAP_P = ds[1]["cap_p"];
                rowHDTB_TSL.TUCAP_GOC = ds[1]["tucap_goc"];
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon;
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id;
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc;
                rowHDTB_TSL.CULY = l_culy;

                if (ds[1]["tramtb_id"] != "") {
                  rowHDTB_TSL.TRAMTB_ID = parseInt(ds[1]["tramtb_id"]);
                }

                if (ds[1]["bras_id"] != "") {
                  rowHDTB_TSL.BRAS_ID = parseInt(ds[1]["bras_id"]);
                }

                if (ds[1]["dslam_id"] != "") {
                  rowHDTB_TSL.DSLAM_ID = parseInt(ds[1]["dslam_id"]);
                }

                if (ds[1]["port_id"] != "") {
                  rowHDTB_TSL.PORT_ID = parseInt(ds[1]["port_id"]);
                }

                if (ds[1]["vci_vpi_id"] != "") {
                  rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[1]["vci_vpi_id"]);
                }
              } else {
                rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Cuoi.Value);
              }
            }
          }
        }

        rowHDTB_TSL.DAUCUOI_ID = i + 1;

        dsHDTB_TSL.push(rowHDTB_TSL);
        dsHDTBDV.push(row);
      }

      // Tạo cho bảng hdtb_dv
      // Với Truyền số liệu thì tự thêm các dòng # trạm viễn thông
      for (let i = 0; i < this.dsComboDonVi.length; i++) {
        let item = this.dsComboDonVi[i];

        if (parseInt(item.loaiDonVi) != Enum.LOAI_DV.TRAM_VT) {
          let row = {
            HDTB_ID: this.parent.parent.hdtb_id,
            LOAIDV_ID: parseInt(item.loaiDonVi),
            DONVI_ID: parseInt(this.$refs[item.key][0].value),
            KIEUDV_ID: 2,
          };
          dsHDTBDV.push(row);
        }
      }

      return { dsHDTB_TSL, dsHDTBDV };
    },
    async BANCHEO_LAY_DS_DB_TSL_THEO_TBID(data) {
      try {
        data = { tinhthicong_id: data.tinh_id, thuebao_id: data.thueBaoId };
        var response = await this.$root.context.post(
          "/web-bancheo/tracuu/lay_ds_db_tsl_theo_tbid",
          data
        );
        return !response.data ? [] : response.data;
      } catch (error) {
        return [];
      }
    },
    async LAY_DS_DB_TSL_THEO_TBID(thuebao_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_ds_db_tsl_theo_tbid?thuebao_id=${thuebao_id}`
        );
        return !response.data ? [] : response.data;
      } catch (err) {
        return [];
      }
    },
    async LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_ds_hdtb_tsl_theo_hdtb_id?hdtb_id=${hdtb_id}`
        );
        return !response.data ? [] : response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_tt_donvi_cua_hdtb(data) {
      try {
        let response = await this.$root.context.post(
          `/web-hopdong/chuyendich-tsl/lay_tt_donvi_cua_hdtb`,
          data
        );
        return response.data;
      } catch (err) {
        return null;
      }
    },
    async lay_tt_hd_thuebao(hdtb_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_tt_hd_thuebao?hdtb_id=${hdtb_id}`
        );
        return response.data;
      } catch (err) {
        return null;
      }
    },
    closePopupDiaChiBC_Dau() {
      this.$refs.frmDiaChiBC_Dau.hide();
    },
    closePopupDiaChiBC_Cuoi() {
      this.$refs.frmDiaChiBC_Cuoi.hide();
    },
    getDiaChiBC_Dau() {},
    getDiaChiBC_Cuoi() {},
    async HienThiTTMoRongHD(hdtb_id) {
      let ds = await this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id);
      console.log("🚀 ~ file: index.vue:800 ~ HienThiTTMoRongHD ~ ds:", ds);

      if (ds.length <= 0) {
        return;
      }

      this.cboTocDoKenhTSL.Value = parseInt(ds[0]["tocdo_id"]);
      this.parent.parent._tocdo_id = parseInt(ds[0]["tocdo_id"]);
      this.parent.parent.vloaikenh_id = parseInt(ds[0]["loaikenh_id"]);
      this.cboTrangBi.Value = parseInt(ds[0]["trangbi_id"]) + "";
      this.cboChuQuan.Value = parseInt(ds[0]["chuquan_id"]) + "";
      this.txtMaHD_TTKD_TSL.Text = ds[0]["ma_pl"];

      this.parent.parent.modelCkeckBox.chkdisableCongVan = false;

      if (ds[0]["ma_cv"]) {
        this.parent.parent.modelCkeckBox.chkCongVan = true;
        this.parent.parent.modelInput.txtCongVan = ds[0]["ma_cv"];
        this.parent.parent.modelDateTime.dtpNgayCV = new Date(ds[0]["ngay_cv"]);
      }

      this.txtCuocTK_TSL.Text = ds[0]["cuoc_tk"];
      this.txtCuocTC_TSL.Text = ds[0]["cuoc_tc"];
      this.txtCuocHT_TSL.Text = ds[0]["cuoc_ht"];
      this.txtCuocTBI_TSL.Text = ds[0]["cuoc_tbi"];

      this.parent.parent.modelInput.txtMaERP = ds[0]["id_erp"];
      this.parent.parent.modelInput.txtMaBSS = ds[0]["ma_bss"];

      this.parent.parent.dc_daucuoi = parseInt(ds[0]["dc_daucuoi"]);

      if (this.parent.parent.dc_daucuoi == 1) {
        this.chkDC_Dau.Checked = true;
        this.btnChonDCDau.Enabled = true;
        this.txtDiaChiDau.Enabled = true;
        this.cboTramVTDau.Enabled = true;
      }

      if (this.parent.parent.dc_daucuoi == 2) {
        this.chkDC_Cuoi.Checked = true;
        this.btnChonDCCuoi.Enabled = true;
        this.txtDiaChiCuoi.Enabled = true;
        this.cboTramVTCuoi.Enabled = true;
      }

      if (this.parent.parent.dc_daucuoi == 3) {
        this.chkDC_Dau.Checked = true;
        this.btnChonDCDau.Enabled = true;
        this.txtDiaChiDau.Enabled = true;
        this.cboTramVTDau.Enabled = true;

        this.chkDC_Cuoi.Checked = true;
        this.btnChonDCCuoi.Enabled = true;
        this.txtDiaChiCuoi.Enabled = true;
        this.cboTramVTCuoi.Enabled = true;
      }

      if (ds[0]["doitac_id"]) {
        this.chkDoiTac_TSL.Checked = true;
        this.cboDoiTac_TSL.Value = parseInt(ds[0]["doitac_id"]);

        if (ds[0]["cuoc_tcdt"]) {
          this.txtCuocTCDT_TSL.Text = parseFloat(ds[0]["cuoc_tcdt"]);
        } else {
          this.txtCuocTCDT_TSL.Text = "0";
        }

        if (ds[0]["cuoc_tkdt"]) {
          this.txtCuocTKDT_TSL.Text = parseFloat(ds[0]["cuoc_tkdt"]);
        } else {
          this.txtCuocTKDT_TSL.Text = "0";
        }

        this.txtMa_HDDT_TSL.Text = ds[0]["mahd_dt"];
      } else {
        this.chkDoiTac_TSL.Checked = false;
        this.txtCuocTCDT_TSL.Text = "0";
        this.txtCuocTKDT_TSL.Text = "0";
        this.txtMa_HDDT_TSL.Text = "";
      }

      for (let i = 0; i < ds.length; i++) {
        if (parseInt(ds[i]["daucuoi_id"]) == 1) {
          // Nếu là điểm đầu
          this.parent.parent.diachidau_id = ds[i]["diachi_id"];

          if (ds[i]["kinhdo_ld"]) {
            this.parent.parent.Lng_DD = ds[i]["kinhdo_ld"];
          } else {
            this.parent.parent.Lng_DD = 0;
          }

          if (ds[i]["vido_ld"]) {
            this.parent.parent.Lat_DD = ds[i]["vido_ld"];
          } else {
            this.parent.parent.Lat_DD = 0;
          }

          let thueBaoInfo = await this.lay_tt_hd_thuebao(hdtb_id);
          console.log(
            "🚀 ~ file: index.vue:900 ~ HienThiTTMoRongHD ~ thueBaoInfo:",
            thueBaoInfo
          );
          this.donvi_dau_id = thueBaoInfo.donvi_dau_id;

          this.cboTramVTDau.DataSource = await this.lay_donvi_loaidv({
            tinhthicong_id: this.parent.parent.tinh_dau.tinhthicong_id,
            donvi_id: this.donvi_dau_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
          });
          console.log(
            "🚀 ~ file: index.vue:903 ~ HienThiTTMoRongHD ~ this.cboTramVTDau:",
            this.cboTramVTDau
          );

          this.txtDiaChiDau.Text = ds[i]["diachi"];
          this.parent.parent.ma_tb_dau = ds[i]["ma_tb_sub"];
          this.cboLoaiTBTSL_Dau.Value = parseInt(ds[i]["thietbidc_id"]) + "";
          this.txtSoAoTSL_Dau.Text = this.parent.parent.malt_dau = ds[i]["ma_lt"];

          let donViInfo = await this.lay_tt_donvi_cua_hdtb({
            hdtb_id: hdtb_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 1,
          });
          console.log(
            "🚀 ~ file: index.vue:917 ~ HienThiTTMoRongHD ~ donViInfo:",
            donViInfo
          );

          if (donViInfo) {
            this.cboTramVTDau.Value = donViInfo["donvi_id"] + "";
          }

          this.cboLoaiCap_Dau.Value = parseInt(ds[i]["loaicap_id"]) + "";
        } else {
          this.parent.parent.diachicuoi_id = parseInt(ds[i]["diachi_id"]);

          if (ds[i]["kinhdo_ld"]) {
            this.parent.parent.Lng_DC = ds[i]["kinhdo_ld"];
          } else {
            this.parent.parent.Lng_DC = 0;
          }

          if (ds[i]["vido_ld"]) {
            this.parent.parent.Lat_DC = ds[i]["vido_ld"];
          } else {
            this.parent.parent.Lat_DC = 0;
          }

          let thueBaoInfo = await this.lay_tt_hd_thuebao(hdtb_id);
          console.log(
            "🚀 ~ file: index.vue:942 ~ HienThiTTMoRongHD ~ thueBaoInfo:",
            thueBaoInfo
          );
          this.donvi_cuoi_id = thueBaoInfo.donvi_cuoi_id;

          this.cboTramVTCuoi.DataSource = await this.lay_donvi_loaidv({
            tinhthicong_id: this.parent.parent.tinh_cuoi.tinhthicong_id,
            donvi_id: this.donvi_cuoi_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
          });
          console.log(
            "🚀 ~ file: index.vue:943 ~ HienThiTTMoRongHD ~ this.cboTramVTCuoi:",
            this.cboTramVTCuoi
          );
          this.txtDiaChiCuoi.Text = ds[i]["diachi"];
          this.parent.parent.ma_tb_cuoi = ds[i]["ma_tb_sub"];
          this.cboLoaiTBTSL_Cuoi.Value = parseInt(ds[i]["thietbidc_id"]) + "";
          this.txtSoAoTSL_Cuoi.Text = this.parent.parent.malt_cuoi = ds[i]["ma_lt"];

          let donViInfo = await this.lay_tt_donvi_cua_hdtb({
            hdtb_id: hdtb_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 2,
          });

          if (donViInfo) {
            this.cboTramVTCuoi.Value = donViInfo["donvi_id"] + "";
          }

          this.cboLoaiCap_Cuoi.Value = parseInt(ds[i]["loaicap_id"]) + "";
        }
      }

      await this.HienThiTTDiaChi_TSL();

      await this.HT_COMBO_DONVI();

      let dsDonVi = await this.lay_ds_donvi_cua_hopdong(hdtb_id);

      setTimeout(() => {
        if (dsDonVi.length > 0) {
          for (let i = 0; i < this.dsComboDonVi.length; i++) {
            let cbo = this.dsComboDonVi[i];
            let donVi = dsDonVi.find((item) => item.loaidv_id == parseInt(cbo.loaiDonVi));

            if (donVi) {
              this.$refs[cbo.key][0].value = donVi["donvi_id"];
            }
          }
        }
      }, 300);
    },
    async lay_ds_donvi_cua_hopdong(hdtb_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_hopdong`,
          { hdtb_id }
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async HienThiTTMoRongTB(thuebao_id) {
      let ds = await this.LAY_DS_DB_TSL_THEO_TBID(thuebao_id);

      if (ds.length <= 0) {
        return;
      }

      this.cboTocDoKenhTSL.Value = ds[0]["tocdo_id"] + "";
      this.parent.parent._tocdo_id = ds[0]["tocdo_id"];
      this.parent.parent.vloaikenh_id = ds[0]["loaikenh_id"];
      this.cboTrangBi.Value = ds[0]["trangbi_id"] + "";
      this.cboChuQuan.Value = ds[0]["chuquan_id"] + "";
      this.txtMaHD_TTKD_TSL.Text = ds[0]["ma_pl"];
      this.parent.parent.modelCkeckBox.chkdisableCongVan = false;
      this.parent.parent.modelDateTime.dtpNgayCV = new Date();
      this.txtCuocTK_TSL.Text = ds[0]["cuoc_tk"];
      this.txtCuocTC_TSL.Text = ds[0]["cuoc_tc"];
      this.txtCuocHT_TSL.Text = ds[0]["cuoc_ht"];
      this.txtCuocTBI_TSL.Text = ds[0]["cuoc_tbi"];

      this.parent.parent.modelInput.txtMaERP = ds[0]["id_erp"];
      this.parent.parent.modelInput.txtMaBSS = ds[0]["ma_bss"];

      if (ds[0]["doitac_id"]) {
        this.chkDoiTac_TSL.Checked = true;
        this.cboDoiTac_TSL.Value = ds[0]["doitac_id"] + "";

        if (ds[0]["cuoc_tcdt"]) {
          this.txtCuocTCDT_TSL.Text = parseFloat(ds[0]["cuoc_tcdt"]);
        } else {
          this.txtCuocTCDT_TSL.Text = "0";
        }

        if (ds[0]["cuoc_tkdt"]) {
          this.txtCuocTKDT_TSL.Text = parseFloat(ds[0]["cuoc_tkdt"]);
        } else {
          this.txtCuocTKDT_TSL.Text = "0";
        }

        this.txtMa_HDDT_TSL.Text = ds[0]["mahd_dt"];
      } else {
        this.chkDoiTac_TSL.Checked = false;
        this.txtCuocTCDT_TSL.Text = "0";
        this.txtCuocTKDT_TSL.Text = "0";
        this.txtMa_HDDT_TSL.Text = "";
      }

      for (let i = 0; i < ds.length; i++) {
        if (parseInt(ds[i]["daucuoi_id"]) == 1) {
          // Nếu là điểm đầu
          this.parent.parent.diachidau_id = ds[i]["diachi_id"];

          if (ds[i]["kinhdo_ld"]) {
            this.parent.parent.Lng_DD = ds[i]["kinhdo_ld"];
          } else {
            this.parent.parent.Lng_DD = 0;
          }

          if (ds[i]["vido_ld"]) {
            this.parent.parent.Lat_DD = ds[i]["vido_ld"];
          } else {
            this.parent.parent.Lat_DD = 0;
          }

          this.cboLoaiTBTSL_Dau.Value = ds[i]["thietbidc_id"] + "";
          this.txtSoAoTSL_Dau.Text = this.parent.parent.malt_dau = ds[i]["ma_lt"];
          this.cboLoaiCap_Dau.Value = ds[i]["loaicap_id"] + "";
        } else {
          this.parent.parent.diachicuoi_id = ds[i]["diachi_id"];

          if (ds[i]["kinhdo_ld"]) {
            this.parent.parent.Lng_DC = ds[i]["kinhdo_ld"];
          } else {
            this.parent.parent.Lng_DC = 0;
          }

          if (ds[i]["vido_ld"]) {
            this.parent.parent.Lat_DC = ds[i]["vido_ld"];
          } else {
            this.parent.parent.Lat_DC = 0;
          }

          this.cboLoaiTBTSL_Cuoi.Value = ds[i]["thietbidc_id"];
          this.txtSoAoTSL_Cuoi.Text = this.parent.parent.malt_cuoi = ds[i]["ma_lt"];
          this.cboLoaiCap_Cuoi.Value = ds[i]["loaicap_id"];
        }
      }

      await this.HienThiTTDiaChi_TSL();

      for (let i = 0; i < ds.length; i++) {
        if (ds[i]["daucuoi_id"] == 1) {
          // Nếu là điểm đầu
          let thueBaoInfo = await this.lay_tt_thuebao(thuebao_id);
          this.parent.parent.donvi_dau_id = thueBaoInfo.donvi_dau_id;

          this.cboTramVTDau.DataSource = await this.lay_donvi_loaidv({
            tinhthicong_id: this.parent.parent.tinh_dau.tinhthicong_id,
            donvi_id: this.parent.parent.donvi_dau_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
          });

          if (this.cboTramVTDau.DataSource.length > 0) {
            this.cboTramVTDau.Value = this.cboTramVTDau.DataSource[0].donvi_id + "";
          }

          this.txtDiaChiDau.Text = ds[i]["diachi"];
          this.parent.parent.ma_tb_dau = ds[i]["ma_tb_sub"];

          let donViInfo = await this.lay_tt_donvi_cua_tbao({
            thuebao_id: thuebao_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 1,
          });

          if (donViInfo) {
            this.cboTramVTDau.Value = donViInfo["donvi_id"] + "";
          }
        } else {
          let thueBaoInfo = await this.lay_tt_thuebao(thuebao_id);
          this.parent.parent.donvi_cuoi_id = thueBaoInfo.donvi_cuoi_id;

          this.cboTramVTCuoi.DataSource = await this.lay_donvi_loaidv({
            tinhthicong_id: this.parent.parent.tinh_cuoi.tinhthicong_id,
            donvi_id: this.parent.parent.donvi_cuoi_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
          });

          if (this.cboTramVTCuoi.DataSource.length > 0) {
            this.cboTramVTCuoi.Value = this.cboTramVTCuoi.DataSource[0].donvi_id + "";
          }

          this.txtDiaChiCuoi.Text = ds[i]["diachi"];
          this.parent.parent.ma_tb_cuoi = ds[i]["ma_tb_sub"];

          let donViInfo = await this.lay_tt_donvi_cua_tbao({
            thuebao_id: thuebao_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 2,
          });

          if (donViInfo) {
            this.cboTramVTCuoi.Value = donViInfo["donvi_id"] + "";
          }
        }
      }

      await this.HT_COMBO_DONVI();

      // Lấy ra các đơn vị được chọn
      let dsDonVi = await this.lay_ds_donvi_cua_tbao(thuebao_id);

      setTimeout(() => {
        if (dsDonVi.length > 0) {
          for (let i = 0; i < this.dsComboDonVi.length; i++) {
            let cbo = this.dsComboDonVi[i];
            let donVi = dsDonVi.find((item) => item.loaidv_id == parseInt(cbo.loaiDonVi));

            if (donVi) {
              this.$refs[cbo.key][0].value = donVi["donvi_id"];
            }
          }
        }
      }, 300);
    },
    async lay_ds_donvi_cua_tbao(thuebao_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_tbao`,
          { thuebao_id }
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_tt_donvi_cua_tbao(data) {
      try {
        let response = await this.$root.context.post(
          `/web-hopdong/chuyendich-tsl/lay_tt_donvi_cua_tbao`,
          data
        );
        return response.data;
      } catch (err) {
        return null;
      }
    },
    async lay_tt_thuebao(thuebao_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_tt_thuebao?thuebao_id=${thuebao_id}`
        );
        return response.data;
      } catch (err) {
        return null;
      }
    },
    async HienThiTTDiaChi_TSL() {
      let ds = await this.LAY_THONGTIN_DIACHI(this.parent.parent.diachidau_id);

      if (ds) {
        this.parent.parent.diachidau_id = ds["diachi_id"];
        this.parent.parent.tinhdau_id = ds["tinh_id"];
        this.parent.parent.quandau_id = ds["quan_id"];
        this.parent.parent.phuongdau_id = ds["phuong_id"];

        if (ds["pho_id"]) {
          this.parent.parent.phodau_id = ds["pho_id"];
        } else {
          this.parent.parent.phodau_id = 0;
        }

        if (ds["ap_id"]) {
          this.parent.parent.apdau_id = ds["ap_id"];
        } else {
          this.parent.parent.apdau_id = 0;
        }

        if (ds["khu_id"]) {
          this.parent.parent.khudau_id = ds["khu_id"];
        } else {
          this.parent.parent.khudau_id = 0;
        }

        this.parent.parent.sonhadau = ds["sonha"];
      }

      ds = await this.LAY_THONGTIN_DIACHI(this.parent.parent.diachicuoi_id);

      if (ds) {
        this.parent.parent.diachicuoi_id = ds["diachi_id"];
        this.parent.parent.tinhcuoi_id = ds["tinh_id"];
        this.parent.parent.quancuoi_id = ds["quan_id"];
        this.parent.parent.phuongcuoi_id = ds["phuong_id"];

        if (ds["pho_id"]) {
          this.parent.parent.phocuoi_id = ds["pho_id"];
        } else {
          this.parent.parent.phocuoi_id = 0;
        }

        if (ds["ap_id"]) {
          this.parent.parent.apcuoi_id = ds["ap_id"];
        } else {
          this.parent.parent.apcuoi_id = 0;
        }

        if (ds["khu_id"]) {
          this.parent.parent.khucuoi_id = ds["khu_id"];
        } else {
          this.parent.parent.khucuoi_id = 0;
        }

        this.parent.parent.sonhacuoi = ds["sonha"];
      }
    },
    async LAY_THONGTIN_DIACHI(diachi_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_thongtin_diachi?diachi_id=${diachi_id}`
        );
        return response.data;
      } catch (err) {
        return null;
      }
    },
    async lay_donvi_loaidv(data) {
      try {
        data.tinhthicong_id = !data.tinhthicong_id
          ? this.$root.token.getPhanVungID()
          : data.tinhthicong_id;
        data.donvi_id = !data.donvi_id ? 0 : parseInt(data.donvi_id);
        let response = await this.$root.context.post(
          `/web-bancheo/chuyendich-bancheo-dichvu/lay_donvi_loaidv`,
          data
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    chkDC_Cuoi_CheckedChanged() {
      if (this.chkDC_Cuoi.Checked) {
        this.txtDiaChiCuoi.Enabled = true;
        this.btnChonDCCuoi.Enabled = true;
        this.cboTramVTCuoi.Enabled = true;
      } else {
        this.txtDiaChiCuoi.Enabled = false;
        this.btnChonDCCuoi.Enabled = false;
        this.cboTramVTCuoi.Enabled = false;
      }
    },
    chkDC_Dau_CheckedChanged() {
      if (this.chkDC_Dau.Checked) {
        this.txtDiaChiDau.Enabled = true;
        this.btnChonDCDau.Enabled = true;
        this.cboTramVTDau.Enabled = true;
      } else {
        this.txtDiaChiDau.Enabled = false;
        this.btnChonDCDau.Enabled = false;
        this.cboTramVTDau.Enabled = false;
      }
    },
    chkDoiTac_TSL_CheckedChanged() {
      if (this.chkDoiTac_TSL.Checked) {
        this.cboDoiTac_TSL.Enabled = true;
        this.txtCuocTKDT_TSL.Enabled = true;
        this.txtCuocTCDT_TSL.Enabled = true;
        this.txtMa_HDDT_TSL.Enabled = true;
      } else {
        this.cboDoiTac_TSL.Enabled = false;
        this.txtCuocTKDT_TSL.Enabled = false;
        this.txtCuocTCDT_TSL.Enabled = false;
        this.txtMa_HDDT_TSL.Enabled = false;
        this.txtCuocTKDT_TSL.Text = "0";
        this.txtCuocTCDT_TSL.Text = "0";
      }
    },
    cboTrangBi_SelectedValueChanged() {
      if (this.cboTrangBi.Value) {
        this.$emit("cboTrangBi_SelectedValueChanged", {
          trangbi_id: parseInt(this.cboTrangBi.Value),
        });
      }
    },
    async cboTocDoKenhTSL_SelectedValueChanged() {
      if (this.cboTocDoKenhTSL.Value) {
        await this.HT_COMBO_DONVI();
        this.$emit("cboTocDoKenhTSL_SelectedValueChanged", {
          tocdo_id: parseInt(this.cboTocDoKenhTSL.Value),
        });
      }
    },
    async HT_COMBO_DONVI() {
      if (!this.idLoaiHinhTb && !this.idKieuLd) {
        this.dsComboDonVi = [];
        return;
      }

      let dsLoaiDonVi = await this.lay_ds_loai_donvi({
        dichvu_id: this.idDichVu,
        loaihinh_tb: this.idLoaiHinhTb,
        kieu_ld: this.idKieuLd,
        tocdotn_id: this.cboTocDoKenhTSL.Value,
        loaikenh_id: this.vloaikenh_id,
      });

      let dsComboDonVi = [];

      for (let i = 0; i < dsLoaiDonVi.length; i++) {
        let loaiDonVi = dsLoaiDonVi[i];

        if (loaiDonVi["loaidv_dich_id"] == Enum.LOAI_DV.TRAM_VT) {
          loaiDonVi.dsDonVi = await this.lay_ds_donvi_tramtc({
            loaidv_id: loaiDonVi["loaidv_dich_id"],
            donvi_ql_id: this.idDaiVt,
          });
        } else {
          loaiDonVi.dsDonVi = await this.lay_ds_donvi(loaiDonVi["loaidv_dich_id"]);
        }

        dsComboDonVi.push(this.newComboDonVi(loaiDonVi));
      }

      this.dsComboDonVi = dsComboDonVi;
    },
    async Lay_ds_donviql_v2(data) {
      try {
        let response = await this.$root.context.post(
          `/web-hopdong/tachnhapthuebao/lay_ds_donviql_v2`,
          data
        );
        return !response.data ? [] : response.data;
      } catch (err) {
        return [];
      }
    },
    async LAY_DS_DONVIQL(data) {
      try {
        let response = await this.$root.context.post(
          `/web-bancheo/chuyendich-bancheo-dichvu/lay_ds_donviql`,
          data
        );
        return !response.data ? [] : response.data;
      } catch (err) {
        return [];
      }
    },
    btnChonDCCuoi_Click() {
      if (this.diaChiCuoiDuocChon) {
        this.diaChiCuoi = {
          tieuDe: "Địa chỉ",
          tinh_id: this.diaChiCuoiDuocChon.tinh_id,
          quan_id: this.diaChiCuoiDuocChon.quan_id,
          phuong_id: this.diaChiCuoiDuocChon.phuong_id,
          pho_id: this.diaChiCuoiDuocChon.pho_id,
          ap_id: this.diaChiCuoiDuocChon.ap_id,
          khu_id: this.diaChiCuoiDuocChon.khu_id,
          so_nha: this.diaChiCuoiDuocChon.so_nha,
          diachi: this.diaChiCuoiDuocChon.diachimoi,
        };
      }

      this.$bvModal.show("frm-chuyendich-bc-tsl-modal-chon-diachi-cuoi");
    },
    async onChonDiaChiCuoi(data) {
      if (!data) return;

      this.diaChiCuoiDuocChon = data;

      if (parseInt(data.quan_id) > 0 && parseInt(data.phuong_id) > 0) {
        let ds = [];

        if (this.parent.parent.DONVIQL_HTTT == 1) {
          ds = await this.Lay_ds_donviql_v2({
            phuong_id: data.phuong_id,
            pho_id: !data.pho_id ? 0 : data.pho_id,
            ap_id: !data.ap_id ? 0 : data.ap_id,
            khu_id: !data.khu_id ? 0 : data.khu_id,
            kieu: 2,
            loaikh_id: 0,
            httt_id: 0,
          });
        } else {
          ds = await this.LAY_DS_DONVIQL({
            tinhthicong_id: this.parent.parent.tinh_cuoi.tinhthicong_id,
            phuong_id: data.phuong_id,
            pho_id: !data.pho_id ? 0 : data.pho_id,
            ap_id: !data.ap_id ? 0 : data.ap_id,
            khu_id: !data.khu_id ? 0 : data.khu_id,
            kieu: 2,
            loaikh_id: 0,
          });
        }

        if (ds.length <= 0) {
          this.$root.toastError(
            "Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!"
          );
          return;
        } else if (ds.length == 1) {
          this.parent.parent.donvi_cuoi_id = parseInt(ds[0]["donvi_id"]);
        } else {
          let data = await this.$refs.popupChonDonVi.show(
            null,
            null,
            this.parent.parent.donvi_cuoi_id,
            null,
            ds
          );

          if (!data.ok) return;

          this.parent.parent.donvi_cuoi_id = data.data.donviql_id;
        }

        let donViPxInfo = await this.lay_tt_donvi_px({
          phuong_id: data.phuong_id,
          pho_id: data.pho_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT,
        });

        let vetinh_id = !donViPxInfo ? null : donViPxInfo.donvi_id;

        this.cboTramVTCuoi.DataSource = await this.lay_donvi_loaidv({
          tinhthicong_id: this.parent.parent.tinh_cuoi.tinhthicong_id,
          donvi_id: this.parent.parent.donvi_cuoi_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT,
        });

        if (this.cboTramVTCuoi.DataSource.length > 0) {
          let hasExisted = this.cboTramVTCuoi.DataSource.some(
            (item) => item.donvi_id == vetinh_id
          );
          this.cboTramVTCuoi.Value = !hasExisted ? null : vetinh_id;
        } else {
          this.cboTramVTCuoi.Value = null;
        }
      }

      this.parent.parent.tinhcuoi_id = data.tinh_id;
      this.parent.parent.quancuoi_id = data.quan_id;
      this.parent.parent.phuongcuoi_id = data.phuong_id;
      this.parent.parent.phocuoi_id = data.pho_id;
      this.parent.parent.apcuoi_id = data.ap_id;
      this.parent.parent.khucuoi_id = data.khu_id;
      this.parent.parent.Lat_DC = data.lat;
      this.parent.parent.Lng_DC = data.lng;
      this.parent.parent.sonhacuoi = data.sonha;
      this.txtDiaChiCuoi.Text = data.diachimoi;

      setTimeout(() => this.$refs["txt-diachi-cuoi"].focus(), 300);
    },
    async lay_tt_donvi_px(data) {
      try {
        let response = await this.$root.context.post(
          `/web-hopdong/chuyendich-tsl/lay_tt_donvi_px`,
          data
        );
        return !response.data ? null : response.data[0];
      } catch (err) {
        return null;
      }
    },
    async onChonDiaChiDau(data) {
      if (!data) return;

      this.diaChiDauDuocChon = data;

      if (parseInt(data.quan_id) > 0 && parseInt(data.phuong_id) > 0) {
        let ds = [];

        if (this.parent.parent.DONVIQL_HTTT == 1) {
          ds = await this.Lay_ds_donviql_v2({
            phuong_id: data.phuong_id,
            pho_id: !data.pho_id ? 0 : data.pho_id,
            ap_id: !data.ap_id ? 0 : data.ap_id,
            khu_id: !data.khu_id ? 0 : data.khu_id,
            kieu: 2,
            loaikh_id: 0,
            httt_id: 0,
          });
        } else {
          ds = await this.LAY_DS_DONVIQL({
            tinhthicong_id: this.parent.parent.tinh_dau.tinhthicong_id,
            phuong_id: data.phuong_id,
            pho_id: !data.pho_id ? 0 : data.pho_id,
            ap_id: !data.ap_id ? 0 : data.ap_id,
            khu_id: !data.khu_id ? 0 : data.khu_id,
            kieu: 2,
            loaikh_id: 0,
          });
        }

        if (ds.length <= 0) {
          this.$root.toastError(
            "Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!"
          );
          return;
        } else if (ds.length == 1) {
          this.parent.parent.donvi_dau_id = parseInt(ds[0]["donvi_id"]);
        } else {
          let data = await this.$refs.popupChonDonVi.show(
            null,
            null,
            this.parent.parent.donvi_dau_id,
            null,
            ds
          );

          if (!data.ok) return;

          this.parent.parent.donvi_dau_id = data.data.donviql_id;
        }

        let donViPxInfo = await this.lay_tt_donvi_px({
          phuong_id: data.phuong_id,
          pho_id: data.pho_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT,
        });

        let vetinh_id = !donViPxInfo ? null : donViPxInfo.donvi_id;

        this.cboTramVTDau.DataSource = await this.lay_donvi_loaidv({
          tinhthicong_id: this.parent.parent.tinh_dau.tinhthicong_id,
          donvi_id: this.parent.parent.donvi_dau_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT,
        });

        if (this.cboTramVTDau.DataSource.length > 0) {
          let hasExisted = this.cboTramVTDau.DataSource.some(
            (item) => item.donvi_id == vetinh_id
          );
          this.cboTramVTDau.Value = !hasExisted ? null : vetinh_id;
        } else {
          this.cboTramVTDau.Value = null;
        }
      }

      this.txtDiaChiDau.Text = data.diachimoi;
      this.parent.parent.tinhdau_id = data.tinh_id;
      this.parent.parent.quandau_id = data.quan_id;
      this.parent.parent.phuongdau_id = data.phuong_id;
      this.parent.parent.phodau_id = data.pho_id;
      this.parent.parent.apdau_id = data.ap_id;
      this.parent.parent.khudau_id = data.khu_id;
      this.parent.parent.Lat_DD = data.lat;
      this.parent.parent.Lng_DD = data.lng;
      this.parent.parent.sonhadau = data.so_nha;

      setTimeout(() => this.$refs["txt-diachi-dau"].focus(), 300);
    },
    btnChonDCDau_Click() {
      if (this.diaChiDauDuocChon) {
        this.diaChiDau = {
          tieuDe: "Địa chỉ",
          tinh_id: this.diaChiDauDuocChon.tinh_id,
          quan_id: this.diaChiDauDuocChon.quan_id,
          phuong_id: this.diaChiDauDuocChon.phuong_id,
          pho_id: this.diaChiDauDuocChon.pho_id,
          ap_id: this.diaChiDauDuocChon.ap_id,
          khu_id: this.diaChiDauDuocChon.khu_id,
          so_nha: this.diaChiDauDuocChon.so_nha,
          diachi: this.diaChiDauDuocChon.diachimoi,
        };
      }

      this.$bvModal.show("frm-chuyendich-bc-tsl-modal-chon-diachi-dau");
    },
    // btnChonDCDau_Click () {
    //   if (this.diaChiDauDuocChon) {
    //     console.log('🚀 ~ file: index.vue:1547 ~ btnChonDCDau_Click ~ this.diaChiDauDuocChon:', this.diaChiDauDuocChon)
    //     this.diaChiDau = {
    //       tieuDe: 'Địa chỉ',
    //       tinh_id: this.diaChiDauDuocChon.tinh_id,
    //       quan_id: this.diaChiDauDuocChon.quan_id,
    //       phuong_id: this.diaChiDauDuocChon.phuong_id,
    //       pho_id: this.diaChiDauDuocChon.pho_id,
    //       ap_id: this.diaChiDauDuocChon.ap_id,
    //       khu_id: this.diaChiDauDuocChon.khu_id,
    //       so_nha: this.diaChiDauDuocChon.so_nha,
    //       diachi: this.diaChiDauDuocChon.diachimoi
    //     }
    //   }
    //   this.$refs.frmDiaChiBC_Dau.show()
    // },
    newComboDonVi(loaiDonVi) {
      return {
        key: `combo-id-${loaiDonVi.loaidv_dich_id}`,
        loaiDonVi: loaiDonVi.loaidv_dich_id,
        template: `
                    <div class="info-row">
                        <div class="key">${loaiDonVi.ten_loaidv}</div>
                        <div class="value">
                            <SelectExt
                                v-model="value"
                                :dataSource="dataSource"
                                dataValueField="donvi_id"
                                dataTextField="ten_dv" />
                        </div>
                    </div>
                `,
        data() {
          return {
            value: null,
            dataSource: [],
          };
        },
        mounted() {
          if (loaiDonVi.dsDonVi.length > 0) {
            this.dataSource = [...loaiDonVi.dsDonVi];
            this.value = this.dataSource[0].donvi_id;
          }
        },
      };
    },
    async lay_ds_loai_tb() {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-bancheo-dichvu/lay_loai_tb_theo_dichvu?dichvu_id=9`
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_trangbi() {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-codinh/lay_ds_trangbi`
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_loai_cap() {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_ds_loai_cap`
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_doitac() {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_ds_doitac`
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_tocdo() {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_ds_tocdo`
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_chuquan() {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-tsl/lay_ds_chuquan`
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_loai_donvi(data) {
      try {
        let response = await this.$root.context.post(
          `/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_loai_donvi_1`,
          data
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_donvi_tramtc(data) {
      try {
        let response = await this.$root.context.post(
          `/web-hopdong/chuyendich-codinh/lay_ds_donvi_tramtc`,
          data
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
    async lay_ds_donvi(loaidv_id) {
      try {
        let response = await this.$root.context.get(
          `/web-hopdong/chuyendich-codinh/lay_ds_donvi?loaidv_id=${loaidv_id}`
        );
        return response.data;
      } catch (err) {
        return [];
      }
    },
  },
};
</script>
