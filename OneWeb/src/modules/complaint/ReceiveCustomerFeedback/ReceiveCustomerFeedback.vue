<template src="./ReceiveCustomerFeedback.html">

</template>

<script>
import breadcrumb from "@/components/breadcrumb";
import '@/assets/vendor/jquery/split'
import moment from "moment";
import Config from './config';
import {
  DBConstants,
  HINHTHUC_KN,
  LOAI_DV,
  Loai_NV,
  NHOM_QT,
  TRANGTHAI_KHIEU_NAI,
  TrangThaiHD
} from "../../../const/enums";
import FormTraCuuTB from '@/modules/incident/subscriber/ReceiveIncident/popups/frmTraCuuThueBao';
import SearchAccount from '@/modules/search/subscriber/SearchAccount';
import ModalChonDiaChi from "../../contract/setup/InstallNewSubs/Popups/popupChonDiaChi/ModalChonDiaChi";
import ThongSoKyThuat from "../../incident/subscriber/SearchIncidentTicket/PopUps/ThongSoKyThuat";
import APIHandle from "./APIHandle";
import {TrangThaiHD as TRANGTHAI_PH} from "../../../utils/Enum";

export default {
  name: "ReceiveCustomerFeedback",
  components: {
    breadcrumb,
    FormTraCuuTB,
    SearchAccount,
    ModalChonDiaChi,
    ThongSoKyThuat
  },
  data() {
    return {
      header: {
        title: "Tiếp nhận xử lý phản hồi khách hàng",
        list: [
          {name: "Xử lý khiếu nại", link: {name: "Ui.cards"}},
          {name: "Tiếp nhận xử lý phản hồi KH", link: {name: "Ui.buttons"}},
        ],
      },
      ma_tt: "",
      ma_tb: "",
      thuebao_id: -1,
      thanhtoan_id: -1,
      khieunai_id: -1,
      donvi_id: -1,
      nhanvien_id: -1,
      daily_id: -1,
      congvanxl: "",
      ma_kn: "",
      loaitb_id: -1,
      dichvuvt_id: -1,
      kyhoadon: "",
      tthd_id: 0,
      huonggiao_id: 0,
      quytrinh_id: 0,
      huonggiao_tn: 0,
      vloai_dv_dich: 0,
      kt_load: true,
      ky_kn: "",
      vma_tb_link: "",
      KHONG_BAOHONG_PHANHOI_DONGTHOI: -1,

      // tttb: {
      cboDichVu_VT: -1,
      cboDichVu_VT_DataSource: [],
      cboLoaiHinh: -1,
      cboLoaiHinh_DataSource: [],
      txtMaTB: '',
      dtpNgayTN: moment().format('DD/MM/YYYY HH:mm:ss'),
      txtTenTB: '',
      cboLoaiKH: -1,
      cboLoaiKH_DataSource: [],
      txtDiaChiTB: '',
      txtTrangThaiTB: '',
      cboDoi_Tuong: -1,
      cboDoi_Tuong_DataSource: [],
      txtDiaChiTT: '',
      txtDiaChiBC: '',

      gridviewNo: {
        data: [],
        cols: Config.colsChiTietNo
      },

      // ttnph: {
      txtNguoiKN: '',
      txtDienThoaiLH: '',
      txtCMT: '',
      txtNgayCap: '',
      txtNoiCap: '',
      cboHinhThuc_KN: -1,
      cboHinhThuc_KN_DataSource: [],
      txtDiaChi: '',
      txtEmail: '',
      chkHenTL: false,
      dtpTuNgay: moment().format('DD/MM/YYYY'),
      dtpDenNgay: moment().add(2, 'days').format('DD/MM/YYYY'),
      txtMaKN: '',

      gridview_ChukyNo: {
        data: [],
        cols: Config.colsChuKyNo
      },

      // ttph: {
      cboLoai_CD: -1,
      cboLoai_CD_DataSource: [],
      cboChuDe_KN2: -1,
      cboChuDe_KN2_DataSource: [],
      txtNoiDungKN: '',

      // kqxl: {
      chkXuLy: 2,
      txtLyDo: '',
      dtpNgayXL: moment().format('DD/MM/YYYY HH:mm:ss'),
      cboNhanVien: -1,
      cboNhanVien_DataSource: [],
      txtNoiDungXL: '',
      txtNguyenNhan: '',
      cboLoi: -1,
      cboLoi_DataSource: [],
      txtGiamCuoc_DT: '',
      txtGiamCuoc_CP: '',
      cboThangGC: -1,
      cboThangGC_DataSource: [],
      cboThangApDung: -1,
      cboThangApDung_DataSource: [],
      txtTienDT: '',
      txtVatDT: '',
      txtTienCP: '',
      txtVatCP: '',
      //

      grvTienGC: {
        data: [],
        cols: Config.colsTienGC
      },

      gridviewDS_KHIEUNAI: {
        data: [],
        cols: Config.colsDsKhieuNai
      },

      // btnEnabled: {
      tsbtnNhapMoi: true,
      tsbtnGhiLai: true,
      tsbtnXoa: true,
      tsbtnHuyBo: true,
      tsbtnKhoaPhieu: true,
      tsbtnGiaoPhieu: false,
      // }

      dsThueBao: [],
      dtpKyHD: '',
      dtpKyHD_DataSource: [],
      cboChuKy: '',
      cboChuKy_DataSource: [],

      itemKhieuNaiFocused: null,
      dt_gc: [],
      isLoaiHinhLoaded: false,
      allLoaiHinh: [],

      dsKhieuNai: [],
      dskhieunai_dv: [],
      phieu_id: 0,
      may_cn: ''
    }
  },
  async mounted() {
    this.may_cn = await this.$root.token.getMachine();
    await this.frmTiepNhan_PHKH_Load();
  },
  computed: {
    gridViewDSKhieuNaiLength() {
      return this.gridviewDS_KHIEUNAI.data ? this.gridviewDS_KHIEUNAI.data.length : 0
    },

    nhanVienId() {
      return this.$root.token.getNhanVienID();
    }
  },
  watch: {
    'cboDichVu_VT': function (val) {
      this.cboDichVu_VT_EditValueChanged();
    },

    'cboLoai_CD': function (val) {
      this.cboLoai_CD_EditValueChanged();
    }
  },
  methods: {
    togglePanel(panelName) {
      let obj = `.i-${panelName}`;
      if ($(obj).hasClass('fa-minus-square-o')) {
        $(`.${panelName}`).hide();
        $(obj).removeClass('fa-minus-square-o');
        $(obj).addClass('fa-plus-square-o');
      } else {
        $(`.${panelName}`).show();
        $(obj).removeClass('fa-plus-square-o');
        $(obj).addClass('fa-minus-square-o');
      }
    },

    xuLyTag() {
      //host: 1+1
      let Tag = '';
      if (this.$route.query.tag != null && this.$route.query.tag !== '' && this.$route.query.tag.length > 0) {
        Tag = this.$route.query.tag;
      } else if (this.$route.params.tag != null && this.$route.params.tag !== '' && this.$route.params.tag.length > 0) {
        Tag = this.$route.params.tag;
      }

      try {
        let str = "";
        if (this.tthd_id === 0)
          if (Tag != "") {
            str = Tag.toString();
            this.tthd_id = Number(str.substr(0, str.indexOf("+")));
            this.huonggiao_id = Number(str.substr(str.indexOf("+") + 1));
          } else {
            this.tthd_id = 0;
            this.huonggiao_id = 0;
          }
      } catch (e) {
        this.tthd_id = TrangThaiHD.DANG_THI_CONG;
      }
    },

    SetButton(kieu) {
      this.tsbtnNhapMoi = false;
      this.tsbtnGhiLai = false;
      this.tsbtnXoa = false;
      this.tsbtnHuyBo = false;
      if (kieu === -1)//Bat dau
      {
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
      }
      if (kieu === 0)//Bat dau
      {
        $('#txtMaTB').focus();
        this.tsbtnNhapMoi = true;
      }
      if (kieu === 1)//Them moi
      {
        $('#txtMaTB').focus();
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
        // if (!string.IsNullOrEmpty(cm.RegRead("loai_chu_de", "")) && cm.RegRead("loai_chu_de", "") != "0")
        //   cboLoai_CD.ItemIndex = cboLoai_CD.Properties.GetDataSourceRowIndex("LOAI_ID", int.Parse(cm.RegRead("loai_chu_de", "")));
        // else
        //   cboLoai_CD.ItemIndex = 0;
        //
        // if (!string.IsNullOrEmpty(cm.RegRead("hinhthuc_kn", "")) && cm.RegRead("hinhthuc_kn", "") != "0")
        //   cboHinhThuc_KN.ItemIndex = cboHinhThuc_KN.Properties.GetDataSourceRowIndex("HTKN_ID", int.Parse(cm.RegRead("hinhthuc_kn", "")));
        // else
        //   cboHinhThuc_KN.ItemIndex = 0;
        this.Clear();
      }
      if (kieu === 2)//Huy
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
      }
      if (kieu === 3)//Edit
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
      }
    },

    Clear() {
      this.txtGiamCuoc_DT = "0";
      this.txtGiamCuoc_CP = "0";
      this.dtpTuNgay = moment().format('DD/MM/YYYY');
      this.dtpDenNgay = moment().add(2, 'days').format('DD/MM/YYYY');
      this.txtTenTB = "";
      this.txtDiaChiTB = "";
      this.txtTrangThaiTB = "";
      this.gridviewNo.data = [];
      this.gridview_ChukyNo.data = [];
      this.txtNoiDungKN = "";
      this.txtNoiDungXL = "";
      this.txtGhiChu = "";
      this.txtNguoiKN = "";
      this.txtDienThoaiLH = "";
      this.chkHenTL = false;
      this.dtpNgayTN = moment().format('DD/MM/YYYY HH:mm:ss');
      this.dtpNgayXL = moment().format('DD/MM/YYYY HH:mm:ss');
      this.txtMaTB = "";
      this.txtCMT = "";
      this.txtNgayCap = "";
      this.txtNoiCap = "";
      this.txtDiaChi = "";
      this.txtLyDo = "";
      this.daily_id = -1;
      this.thuebao_id = 0;
      this.thanhtoan_id = 0;
      this.txtMaKN = "";
      this.txtDiaChiTT = "";
      this.txtEmail = "";
      this.txtDiaChiBC = "";
      this.ky_kn = "";
    },

    async frmTiepNhan_PHKH_Load() {
      this.loading(true);
      try {
        this.dt_gc = await APIHandle.sp_ds_khieunai_gc_theo_khieunai_id(this.axios, -1);
        this.kyhoadon = await APIHandle.getKyHoaDon(this.axios);
        if (this.kyhoadon == null) {
          this.$toast.error('Không có thông tin kỳ cước');
          return;
        }

        let momentKyHD = moment(this.kyhoadon, 'YYYYMMDD');
        this.kyhoadon = momentKyHD.format('DDMMYYYY');

        // #region Combo chu kỳ nợ
        this.cboChuKy = await APIHandle.lay_chukyno_theo_kyhoadon(this.axios, momentKyHD.format('YYYYMM'));
        if (this.cboChuKy) {
          this.cboChuKy_DataSource = [
            {id: this.cboChuKy, value: this.cboChuKy}
          ];
        } else {
          this.$toast.error("Không có thông tin về chu kỳ!");
          return;
        }
        // #endregion
        //
        this.dtpKyHD = this.kyhoadon.substr(2, 2) + "/" + this.kyhoadon.substr(4, 4);
        this.dtpKyHD_DataSource = [
          {id: this.dtpKyHD, value: this.dtpKyHD}
        ]
        this.xuLyTag();

        // #region Hiển thị combo dịch vụ
        this.cboDichVu_VT_DataSource = await APIHandle.CSS_DICHVU_VT(this.axios);
        if (this.cboDichVu_VT === -1 && this.cboDichVu_VT_DataSource.length > 0) {
          this.cboDichVu_VT = this.cboDichVu_VT_DataSource[0].DICHVUVT_ID;
        }

        // #region Hiển thị combo đối tượng
        this.cboDoi_Tuong_DataSource = await APIHandle.CSS_DOITUONG(this.axios);
        if (this.cboDoi_Tuong === -1 && this.cboDoi_Tuong_DataSource.length > 0) {
          this.cboDoi_Tuong = this.cboDoi_Tuong_DataSource[0].DOITUONG_ID;
        }

        // #region Hiển thị combo loại chủ đề KN
        this.cboLoai_CD_DataSource = await APIHandle.sp_ds_loai_chude_khieunai(this.axios);
        if (this.cboLoai_CD === -1 && this.cboLoai_CD_DataSource.length > 0) {
          this.cboLoai_CD = this.cboLoai_CD_DataSource[0].loai_id;
        }

        // #region Hiển thị combo nhân viên thực hiện
        this.cboNhanVien_DataSource = await APIHandle.sp_lay_nhanvien_theo_nhanvien_id(this.axios, this.nhanVienId);
        if (this.cboNhanVien === -1 && this.cboNhanVien_DataSource.length > 0) {
          this.cboNhanVien = this.cboNhanVien_DataSource[0].nhanvien_id;
        }

        // #region Hiển thị combo hình thức kn
        this.cboHinhThuc_KN_DataSource = await APIHandle.QLTN_HINHTHUC_KN(this.axios);
        if (this.cboHinhThuc_KN === -1 && this.cboHinhThuc_KN_DataSource.length > 0) {
          this.cboHinhThuc_KN = this.cboHinhThuc_KN_DataSource[0].HTKN_ID;
        }

        // #region Hiển thị combo loại khách hàng
        this.cboLoaiKH_DataSource = await APIHandle.CSS_LOAI_KH(this.axios);
        if (this.cboLoaiKH === -1 && this.cboLoaiKH_DataSource.length > 0) {
          this.cboLoaiKH = this.cboLoaiKH_DataSource[0].LOAIKH_ID;
        }

        // #region cbo LỖI
        this.cboLoi_DataSource = await APIHandle.QLTN_KETQUA_KN(this.axios);
        if (this.cboLoi === -1 && this.cboLoi_DataSource.length > 0) {
          this.cboLoi = this.cboLoi_DataSource[0].KETQUAKN_ID;
        }

        if (this.cboLoi_DataSource.length <= 0) {
          this.$toast.error("Chưa có dữ liệu kết quả lỗi. Liên hệ admin để xử lý !");
          return;
        }

        $('#txtMaTB').focus();
        this.kt_load = false;
        this.SetButton(3);

        let ds_kt1 = await APIHandle.LAY_DS_THAMSO_MD(this.axios);
        if (ds_kt1.length > 0) {
          let str_vtemp = "KHONG_BAOHONG_PHANHOI_DONGTHOI";
          let det = ds_kt1.filter(item => {
            return item.ma_ts === str_vtemp
          })

          if (det.length > 0) {
            let row = det[0];
            if (row["ten_ts"] == "1")
              this.KHONG_BAOHONG_PHANHOI_DONGTHOI = 1;
          }
        }

        await this.HT_DS_KHIEUNAI();

        //-> HCM: load thông tin từ tiếp nhận hỏng
        this.HienThiTT_Tu_BH();
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },

    async HT_DS_KHIEUNAI() {
      try {
        if (!this.kt_load) {
          let ds = await APIHandle.Lay_ds_tb_phkh(this.axios,
            this.$root.token.getDonViID(),
            this.nhanVienId,
            TRANGTHAI_KHIEU_NAI.KHIEU_NAI_MOI,
            "0",
            moment(this.kyhoadon, 'DDMMYYYY').format('YYYYMMDD')
          );
          if (ds.length > 0) {
            this.gridviewDS_KHIEUNAI.data = [...ds];
            this.SetButton(3);
          } else {
            this.gridviewDS_KHIEUNAI.data = [];
            this.SetButton(1);
          }
        }
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      }
    },

    HienThiTT_Tu_BH() {
      if (this.vma_tb_link !== "") {
        this.SetButton(1);
        this.txtMaTB = "";
        this.cboHinhThuc_KN = 2;
        this.chkXuLy = 2;
        this.cboNhanVien = this.nhanVienId;

        this.HienThiTT_Theo_Ma_TB(this.vma_tb_link);
      }
    },

    async HienThiTT_Theo_Ma_TB(_ma_tb) {
      this.txtMaTB = _ma_tb;

      if (this.txtMaTB === "") {
        $('#txtMaTB').focus();
        return;
      }

      let ds = await APIHandle.Lay_thongtin_tt_theo_matb_ht(this.axios, this.txtMaTB, 0);
      await this.handleHienThi(ds);
    },

    async handleHienThi(ds) {
      if (ds.length > 0) {
        if (ds.length === 1)
          await this.HienThiTTThueBao(ds, 1, 0);
        else {
          this.dsThueBao = [...ds];
          let rs = await this.$refs.formTraCuuTB.showModal();
          if (rs.index != -1) {
            let index = rs.index;
            await this.HienThiTTThueBao(ds, 1, index);
          }
        }
      }
    },

    async HienThiTTThueBao(ds, kieu, index) {
      try {
        this.thanhtoan_id = Number(ds[index]["thanhtoan_id"]);
        this.daily_id = Number(ds[index]["daily_id"]);
        this.ma_tt = ds[index]["ma_tt"];
        this.ma_tb = ds[index]["ma_tb"];
        this.txtDiaChiTT = ds[index]["diachi_tt"];
        this.txtDiaChiBC = ds[index]["diachi_bc"];

        this.thuebao_id = Number(ds[index]["thuebao_id"]);
        this.txtMaTB = ds[index]["ma_tb"];
        this.txtTenTB = ds[index]["ten_tb"];
        this.txtDiaChiTB = ds[index]["diachi_tb"];
        this.txtTrangThaiTB = ds[index]["trangthai_tb"];

        this.dichvuvt_id = Number(ds[index]["dichvuvt_id"]);
        this.cboDichVu_VT = Number(ds[index]["dichvuvt_id"]);

        this.cboLoaiHinh = Number(ds[index]["loaitb_id"]);
        this.cboDoi_Tuong = Number(ds[index]["doituong_id"]);

        this.NAPCOMBO_TGC_KGC(this.ma_tt);

        this.loaitb_id = Number(ds[index]["loaitb_id"]);
        let vkhachhang_id = Number(ds[index]["khachhang_id"]);
        //thieu api
        let dskh = await APIHandle.LAY_DS_DBKH_THEO_KHID(this.axios, vkhachhang_id);
        if (dskh.length > 0) {
          this.cboLoaiKH = dskh[0]["loaikh_id"];
          if (kieu === 1) {
            this.txtNguoiKN = dskh[0]["ten_kh"];
            this.txtDienThoaiLH = dskh[0]["so_dt"];
            this.txtDiaChi = dskh[0]["diachi_kh"];

            if (dskh[0]["loaigt_id"] != "") {
              if (Number(dskh[0]["loaigt_id"]) === 1) {
                this.txtCMT = dskh[0]["so_gt"];
                this.txtNgayCap = dskh[0]["ngaycap"];
                this.txtNoiCap = dskh[0]["noicap"];
                this.txtEmail = dskh[0]["EMAIL"];
              } else {
                this.txtCMT = "";
                this.txtNgayCap = "";
                this.txtNoiCap = "";
                this.txtEmail = "";
              }
            }
          }
        }

        this.HienThiTTNo(1);
        this.HienThi_LichSu_KhieuNai(this.thuebao_id);
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },

    async NAPCOMBO_TGC_KGC(vma_tt) {
      if (vma_tt === "")
        return;
      this.cboThangApDung_DataSource = null;

      let kyhd = "01" + this.dtpKyHD.substr(0, 2) + this.dtpKyHD.substr(3, 4); // kỳ xuôi
      let kynext = moment(kyhd, 'DDMMYYYY').add(1, 'month');
      this.ky_kn = "";
      if (!this.tsbtnNhapMoi) {
        kyhd = "01" + this.dtpKyHD.substr(0, 2) + this.dtpKyHD.substr(3, 4);
        kynext = moment(kyhd, 'DDMMYYYY').add(1, 'month');
        this.ky_kn = this.dtpKyHD.substr(3, 4) + this.dtpKyHD.substr(0, 2) + "01"; // ky ngược
      } else {
        let ky = this.itemKhieuNaiFocused ? this.itemKhieuNaiFocused.ky_kn : null;
        if (ky != null) {
          this.ky_kn = ky;
          kyhd = "01" + this.ky_kn.substr(4, 2) + this.ky_kn.substr(0, 4);
          kynext = moment(kyhd, "DDMMYYYY").add(1, 'month');
        } else {
          kyhd = "01" + this.dtpKyHD.substr(0, 2) + this.dtpKyHD.substr(3, 4);
          kynext = moment(kyhd, 'DDMMYYYY').add(1, 'month');
          this.ky_kn = this.dtpKyHD.substr(3, 4) + this.dtpKyHD.substr(0, 2) + "01";
        }
      }
      let kyhd_next = kynext.format('YYYYMM') + "01";
      let dsthang = await APIHandle.sp_dsthang_tiepnhan_phkh(this.axios, kyhd, vma_tt, this.ma_tb, kyhd_next, this.ky_kn);
      if (dsthang.length === 0) {
        this.cboThangGC_DataSource = null;
      } else {
        this.cboThangGC_DataSource = dsthang;
        // cboThangGC.View.Columns[0].Visible = false;
      }

      let dskyap = await APIHandle.sp_dskyap_tiepnhan_phkh(this.axios, this.ky_kn, kyhd_next);
      if (dskyap.length === 0) {
        this.cboThangApDung_DataSource = null;
      } else {
        this.cboThangApDung_DataSource = dskyap;
      }
    },

    HienThiTTNo(kieu) {
      try {
        let vcacchukyno = "(";
        let vcacmatb = "(";
        let vsochukylchon = -1;
        let ds_ctno_tam = APIHandle.Lay_chukyno_theotb(this.axios, this.kyhoadon, this.txtMaTB, this.ma_tt, Number(this.cboDichVu_VT));
        if (ds_ctno_tam.length > 0) {
          for (let i = 0; i < ds_ctno_tam.length; i++) {
            vcacchukyno = vcacchukyno + ds_ctno_tam[i]["chukyno"] + ",";
            vcacmatb = vcacmatb + "'" + this.txtMaTB + "'" + ",";
          }
          vcacchukyno = vcacchukyno.substr(0, vcacchukyno.length - 1);
          vcacmatb = vcacmatb.substr(0, vcacmatb.length - 1);
          vcacmatb = vcacmatb + ")";
          vcacchukyno = vcacchukyno + ")";
          vsochukylchon = ds_ctno_tam.length;

          ds_ctno_tam = [];
          //thieu api
          //tcthuno.Lay_chitietno_theo_khoanmuc(this.kyhoadon, this.ma_tt, "0", 1, vcacchukyno, vcacmatb, vsochukylchon, 0, 0, "");
          this.HienThi_ChitietNo_TheoKhoanMuc(ds_ctno_tam, kieu);
        } else {
          this.gridviewNo.data = null;
        }

      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },

    HienThi_ChitietNo_TheoKhoanMuc(ds, kieu) {
      try {
        if (ds && ds.length > 0) {
          let ds_ctno = [...ds];
          // ds_ctno.Tables[0].Rows.Add(ds.Tables[0].Rows[0]["noton"].ToString().Trim(), ds.Tables[0].Rows[0]["ma_tt"].ToString().Trim(),
          //   "", "0", ds.Tables[0].Rows[0]["bccdk"].ToString().Trim(), "1", ds.Tables[0].Rows[0]["bccps"].ToString().Trim(),
          //   "0", ds.Tables[0].Rows[0]["loaitien_id"].ToString().Trim(), "Tổng cộng"
          //   , CongTong(ds, "NOGOCDK", 0), CongTong(ds, "NOTHUEDK", 0), CongTong(ds, "TONGNODK", 0), CongTong(ds, "NOGOCPS", 0), CongTong(ds, "NOTHUEPS", 0)
          //   , CongTong(ds, "TONGNOPS", 0), CongTong(ds, "HHONGNODK", 0), CongTong(ds, "HHONGNOPS", 0)
          //   , CongTong(ds, "TONGNOHH", 0), CongTong(ds, "TONGNOTHUCTE", 0), CongTong(ds, "SETRADK", 0), CongTong(ds, "SETRATHUEDK", 0)
          //   , CongTong(ds, "TONGSETRADK", 0), CongTong(ds, "SECHIHHDK", 0), CongTong(ds, "SETRAPS", 0)
          //   , CongTong(ds, "SETRATHUEPS", 0), CongTong(ds, "TONGSETRAPS", 0), CongTong(ds, "SECHIHHPS", 0), CongTong(ds, "TONGSETRA", 0),
          //   CongTong(ds, "TONGSECHIHH", 0), CongTong(ds, "TONGSEPHAITRA", 0), CongTong(ds, "DATRADK", 0), CongTong(ds, "DATRAPS", 0)
          //   , CongTong(ds, "CONGTRA", 0), CongTong(ds, "TONGDACHIHH", 0), CongTong(ds, "TONGTHUCTRA", 0), CongTong(ds, "CONGTRAQD", 0)
          //   , CongTong(ds, "TONGDACHIHHQD", 0), CongTong(ds, "TONGTHUCTRAQD", 0), CongTong(ds, "CONGDC", 0), CongTong(ds, "TONGDADCHH", 0), CongTong(ds, "TONGTHUCDC", 0));
          this.gridviewNo.data = [...ds_ctno];
        } else {
          this.gridviewNo.data = null;
        }
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },

    CongTong(ds, column, kieu) {
      let kq = 0;
      for (let i = 0; i < ds.length - kieu; i++) {
        kq = kq + Number(ds[i]["" + column + ""]);
      }
      return kq;
    },

    async HienThi_LichSu_KhieuNai(vthuebao_id) {
      try {
        let dsls = await APIHandle.LAY_DS_KHIEUNAI_HT(this.axios, vthuebao_id, 0);
        if (dsls.length > 0) {
          this.gridview_ChukyNo.data = [...dsls];
        } else
          this.gridview_ChukyNo.data = [];
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },

    //LOAD DU LIEU TREN LUOI
    async NAP_DS_TIENGC() {
      try {
        this.grvTienGC.data = null;
        if (!this.tsbtnNhapMoi) {
          this.grvTienGC.data = [...this.dt_gc];
        } else {
          this.grvTienGC.data = await APIHandle.sp_ds_khieunai_gc_theo_khieunai_id(this.axios, this.khieunai_id)
        }
      } catch (ex) {
        this.$toast.error("Có lỗi khi nạp danh sách giảm cước " + ex);
      }
    },

    //EVENT
    async txtMaTB_KeyPress() {
      try {
        if (this.txtMaTB == "") {
          $('#txtMaTB').focus();
          return;
        }
        let ds = await APIHandle.Lay_thongtin_tt_theo_matb_ht(this.axios, this.txtMaTB.trim().toLowerCase(), 0);
        if (ds.length > 0) {
          await this.handleHienThi(ds);
        } else {
          ds = await APIHandle.LAY_THONGTIN_TT_TB_THEO_MATT(
            this.axios,
            moment(this.kyhoadon, 'DDMMYYYY').format('YYYYMMDD'),
            this.txtMaTB.trim().toUpperCase()
          );
          if (ds.length > 0) {
            await this.handleHienThi(ds);
          } else {
            this.Clear();
            this.$toast.error("Không tìm thấy thông tin thuê bao " + this.txtMaTB);
          }
        }
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },

    async cboDichVu_VT_EditValueChanged() {
      if (this.cboDichVu_VT !== -1) {
        //#region Hiển thị combo loại hình TB
        if (!this.isLoaiHinhLoaded) {
          this.allLoaiHinh = await APIHandle.CSS_LOAIHINH_TB(this.axios);
          this.isLoaiHinhLoaded = true;
        }

        let ds = this.allLoaiHinh.filter(item => {
          return Number(item.DICHVUVT_ID) === Number(this.cboDichVu_VT);
        });

        this.cboLoaiHinh_DataSource = ds;
        if (this.cboLoaiHinh === -1 && ds.length > 0) {
          this.cboLoaiHinh = ds[0].LOAITB_ID;
        }
      }
    },

    async cboLoai_CD_EditValueChanged() {
      if (this.cboLoai_CD !== -1) {
        //#region Hiển thị combo chủ đề khiếu nại
        this.cboChuDe_KN2_DataSource = await APIHandle.Lay_ds_chude_kn(this.axios);
        if (this.cboChuDe_KN2_DataSource.length > 0) {
          this.cboChuDe_KN2 = this.cboChuDe_KN2_DataSource[0].chudekn_id;
        }
      }
    },

    async gridviewDS_KHIEUNAI_FocusedRowChanged(args) {
      try {
        this.loading(true);
        let item = args.data;
        let index = Number(args.rowIndex);
        this.itemKhieuNaiFocused = item;
        let vchudekn_id = Number(item.chudenkn_id);

        this.khieunai_id = Number(item.khieunai_id);
        this.ma_tt = item.ma_tt;
        this.ma_tb = item.ma_tb;
        this.NAP_DS_TIENGC();
        this.NAPCOMBO_TGC_KGC(this.ma_tt);
        this.donvi_id = Number(item.donvi_id);
        this.nhanvien_id = Number(item.nhanvien_id);
        this.thuebao_id = Number(item.thuebao_id);
        this.thanhtoan_id = Number(item.thanhtoan_id);
        this.txtNguoiKN = item.nguoi_kn;
        this.txtDienThoaiLH = item.dienthoai_lh;
        this.txtNoiDungKN = item.nd_khieunai;

        this.cboDichVu_VT = Number(item.dichvuvt_id);
        this.cboLoaiHinh = Number(item.loaitb_id);
        this.loaitb_id = Number(item.loaitb_id);

        let vchudeid = Number(item.chudekn_id);
        let vloaiid = Number(await APIHandle.mapData(this.axios,
          "loai_id",
          DBConstants.DB3 + ".chude_kn",
          "where chudekn_id = " + vchudeid)
        );

        this.cboLoai_CD = vloaiid;
        this.cboChuDe_KN2 = vchudeid;

        this.txtGhiChu = item.ghichu_kn;
        this.dtpNgayTN = moment(item.ngay_tn, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
        this.congvanxl = item.congvan_xl
        this.ma_kn = this.txtMaKN = item.ma_kn;
        this.dtpNgayXL = moment().format('DD/MM/YYYY HH:mm:ss');
        this.txtLyDo = item.lydo_ton
        this.txtMaTB = item.ma_tb

        // hoangpkn : 21/03/2016
        if (item.giamcuoc_dt)
          this.txtTienDT = item.giamcuoc_dt;
        else
          this.txtTienDT = "0";

        if (item.giamcuoc_cp)
          this.txtTienCP = item.giamcuoc_cp
        else
          this.txtTienCP = "0";
        // end hoàng

        if (item.noidung_gq) {
          this.chkXuLy = 1;
          this.tsbtnGiaoPhieu = false;
          this.tsbtnKhoaPhieu = true;
        } else {
          this.chkXuLy = 2;
          this.tsbtnKhoaPhieu = false;
          this.tsbtnGiaoPhieu = true;
        }

        this.txtNoiDungXL = item.noidung_gq
        this.txtCMT = item.cmt
        this.txtEmail = item.email
        this.txtNoiCap = item.noicap
        this.txtDiaChi = item.diachi_lh

        this.cboHinhThuc_KN = Number(item.htkn_id)
        this.txtNgayCap = item.ngaycapcmt

        if (item.hengq_tu) {
          this.chkHenTL = true;
          this.dtpTuNgay = item.hengq_tu;
          this.dtpDenNgay = item.hengq_den;
        } else {
          this.chkHenTL = false;
          this.dtpTuNgay = moment().format('DD/MM/YYYY');
          this.dtpDenNgay = moment().add(2, 'days').format('DD/MM/YYYY');
        }
        let ds = await APIHandle.Lay_thongtin_tt_theo_matb_ht(this.axios, this.txtMaTB, item.dichvuvt_id);
        if (ds.length > 0)
          this.HienThiTTThueBao(ds, 2, 0);
        else {
          this.Clear();
          this.$toast.error("Không tìm thấy thông tin thuê bao " + this.txtMaTB);
        }
        this.SetButton(3);
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      } finally {
        this.loading(false);
      }
    },

    //BUTTON CLICK EVENT
    tsbtnGhiLai_Click() {
      this.CapNhat();
    },

    async CapNhat() {
      try {
        this.loading(true);
        if (!await this.KiemTraDuLieu()) return;
        let dset = await APIHandle.LayHuongGiao_TiepNhan_XLKN_DD(this.axios, Number(this.cboChuDe_KN2), this.loaitb_id);

        this.quytrinh_id = 0;
        if (dset.length <= 0) {
          this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
          return;
        }
        this.huonggiao_tn = Number(dset[0]["huonggiao_id"]);
        this.quytrinh_id = Number(dset[0]["quytrinh_id"]);

        //Kiểm tra khiếu nại trùng quy trình chưa hoàn thành thì không cho làm
        let dskt = await APIHandle.lay_ds_khieunai_cungqt_chuaht(this.axios, this.thuebao_id, this.quytrinh_id);
        if (dskt.length > 0) {
          this.$toast.error("Đang có thông tin phản hồi chưa xử lý xong, bạn không thể tiếp nhận mới!");
          return;
        }

        if (!this.tsbtnNhapMoi) {
          await this.TaoDuLieu(true);
          if (!await this.TaoDuLieu_HDTBDV()) return;
          await APIHandle.fn_capnhat_tiepnhan_phkh(this.axios, this.dsKhieuNai, this.dskhieunai_dv, this.huonggiao_id, this.tsbtnNhapMoi);
          if (this.chkXuLy === 2) {
            await APIHandle.fn_capnhat_rdokhongxlduoc_tiepnhan_phkh(this.axios,
              this.khieunai_id,
              this.itemKhieuNaiFocused.phieukn_id,
              this.tsbtnNhapMoi,
              1,
              this.txtLyDo,
              0,
              0,
              0,
              0
            );
          } else {
            await this.TAODULIEU_TIENGC();
            await APIHandle.fn_capnhat_rdokhongxlduoc_tiepnhan_phkh(this.axios,
              this.khieunai_id,
              this.itemKhieuNaiFocused.phieukn_id,
              this.tsbtnNhapMoi,
              0,
              '',
              this.txtGiamCuoc_DT ? Number(this.txtGiamCuoc_DT) : 0,
              this.txtVatDT ? Number(this.txtVatDT) : 0,
              this.txtGiamCuoc_CP ? Number(this.txtGiamCuoc_CP) : 0,
              this.txtVatCP ? Number(this.txtVatCP) : 0,
            );
          }
          let kt = await APIHandle.sp_update_khieunai_email(this.axios, this.khieunai_id, this.txtEmail);
          this.$toast.success("Thêm mới phản hồi thành công!");
        } else {
          await this.TaoDuLieu(false);
          if (!await this.TaoDuLieu_HDTBDV()) return;
          await APIHandle.fn_capnhat_tiepnhan_phkh(this.axios, this.dsKhieuNai, this.dskhieunai_dv, this.huonggiao_id, this.tsbtnNhapMoi);
          if (this.chkXuLy === 2) {
            await APIHandle.fn_capnhat_rdokhongxlduoc_tiepnhan_phkh(this.axios,
              this.khieunai_id,
              this.itemKhieuNaiFocused.phieukn_id,
              this.tsbtnNhapMoi,
              1,
              this.txtLyDo,
              0,
              0,
              0,
              0
            );
          } else {
            await this.TAODULIEU_TIENGC();
            await APIHandle.fn_capnhat_rdokhongxlduoc_tiepnhan_phkh(this.axios,
              this.khieunai_id,
              this.itemKhieuNaiFocused.phieukn_id,
              this.tsbtnNhapMoi,
              0,
              '',
              this.txtGiamCuoc_DT ? Number(this.txtGiamCuoc_DT) : 0,
              this.txtVatDT ? Number(this.txtVatDT) : 0,
              this.txtGiamCuoc_CP ? Number(this.txtGiamCuoc_CP) : 0,
              this.txtVatCP ? Number(this.txtVatCP) : 0,
            );
          }
          if (this.txtEmail) {
            let kt = await APIHandle.sp_update_khieunai_email(this.axios, this.khieunai_id, this.txtEmail);
          }
          this.$toast.error("Cập nhật phản hồi thành công !");
        }

        if (await this.PHAILAM("TUDONG_GIAOPHIEU_HC")) {
          //Tự động giao phiếu
          let tb = await APIHandle.mapData(this.axios,
            "huonggiao_id",
            DBConstants.DB2 + ".huonggiao_ldv",
            "where quytrinh_id = " + this.quytrinh_id + " and thutu_id=2");
          let hg = 0;
          if (tb != "-1")
            hg = Number(tb);
          else
            return;

          let dsGiaoPhieu = [];
          let dsPHKH = await APIHandle.Lay_ds_phanhoi_chuagiao(this.axios, this.khieunai_id, this.huonggiao_tn);
          if (dsPHKH.length > 0) {
            dsGiaoPhieu = await this.AddphieuTH(
              this.khieunai_id,
              this.$root.token.getDonViID(),
              Number(dsPHKH[0]["donvi_nhan_id"]),
              hg,
              0,
              Number(dsPHKH[0]["phieu_cha_id"]),
              Number(dsPHKH[0]["ttkn_id"])
            );
          }

          if (dsGiaoPhieu.length > 0) {
            APIHandle.GiaoPhieu_KhieuNai(this.axios, dsGiaoPhieu);
            this.$toast.success("Giao phiếu cho bộ phận tiếp theo thành công!");
          }

          APIHandle.Tudong_giao_nv_phkh(this.axios, this.khieunai_id, this.phieu_id, this.nhanVienId, "Kiểm tra và xử lý");
        }

        let temp = this.khieunai_id;
        await this.HT_DS_KHIEUNAI();
        await this.setDsKhieuNaiFocused('khieunai_id', temp);
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      } finally {
        this.loading(false);
      }
    },

    async KiemTraDuLieu() {
      if (this.KHONG_BAOHONG_PHANHOI_DONGTHOI === 1) {
        let dtKT = await APIHandle.sp_ds_baohong_thuebao_id_ttbh_id(this.axios, this.thuebao_id);
        if (dtKT.length > 0) {
          this.$toast.error("Thuê bao đang có báo hỏng chưa hoàn thiện. Không thể nhập phản hồi khách hàng!");
          return false;
        }
      }

      if (this.txtNoiDungKN.trim() === "") {
        this.$toast.error("Bạn chưa nhập nội dung khiếu nại !");
        $('#txtNoiDungKN').focus();
        return false;
      }
      if (this.txtNguoiKN.trim() === "") {
        this.$toast.error("Bạn chưa nhập người khiếu nại !");
        $('#txtNguoiKN').focus();
        return false;
      }
      if (this.txtDienThoaiLH.trim() === "") {
        this.$toast.error("Bạn chưa nhập điện thoại liên hệ !");
        $('#txtDienThoaiLH').focus();
        return false;
      }
      if (this.txtDienThoaiLH.trim() !== "") {

        if (!Number.isInteger(this.txtDienThoaiLH.trim())) {
          this.$toast.error("Số điện thoại phải là kiểu số");
          $('#txtDienThoaiLH').focus();
          return false;
        }
      }

      if (Number(this.cboChuDe_KN2) === -1) {
        this.$toast.error("Chưa có chủ đề khiếu nại cho loại hình thuê bao: " + this.cboLoaiHinh + ".\n Liên hệ với admin để tạo chủ đề khiếu nại mới !");
        $('#cboChuDe_KN2').focus();
        return false;
      }

      let ds = await APIHandle.LAY_THONGTIN_TT_TB_THEO_TB(
        this.axios,
        moment(this.kyhoadon, 'DDMMYYYY').format('YYYYMMDD'),
        this.txtMaTB.trim()
      );

      if (ds.length <= 0) {
        //this.$toast.error("Không tìm thấy thông tin thuê bao : " + txtMaTB);
        //return false;
      } else {
        if (this.tsbtnNhapMoi) {
          if (this.gridviewDS_KHIEUNAI.data <= 0) {
            this.$toast.error("Bạn không thể cập nhật dữ liệu do thuê bao : " + this.txtMaTB + " chưa có khiếu nại");
            return false;
          }

          let vthuebao_id = Number(this.itemKhieuNaiFocused.thuebao_id);
          if (vthuebao_id !== Number(ds[0]["thuebao_id"])) {
            this.$toast.error("Bạn không thể cập nhật thuê bao '" + this.txtMaTB + "' cho khiếu nại '" + this.itemKhieuNaiFocused.ma_kn + "' !");
            return false;
          }
        }
      }
      if (this.chkHenTL) {

        if (moment(this.dtpTuNgay, 'DD/MM/YYYY').isAfter(moment(this.dtpDenNgay, 'DD/MM/YYYY'))) {
          this.$toast.error("Từ ngày hẹn trả lời không được lớn hơn đến ngày hẹn trả lời !");
          $('#dtpTuNgay').focus();
          return false;
        }
      }
      if (!this.tsbtnNhapMoi) {
        let ds_kt = await APIHandle.KIEMTRA_TT_MaTB(this.axios, this.thuebao_id);

        if (ds_kt.length > 0) {
          let confirm = await this.$confirm('Thuê bao này đã được tiếp nhận. Bạn có muốn thêm mới không?',
            'Thông báo',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => true).catch(() => false);
          if (!confirm)
            return false;
        }
      }

      if (this.chkXuLy === 2) {
        //if (txtLyDo.trim() === "")
        //{
        //    this.$toast.error("Bạn phải nhập lý do không xử lý được !");
        //    txtLyDo.focus();
        //    return false;
        //}
      }
      if (this.chkXuLy === 1) {
        if (this.txtNoiDungXL.trim() === "") {
          this.$toast.error("Bạn phải nhập nội dung xử lý !");
          return false;
        }
      }

      return true;
    },

    async TaoDuLieu(themmoi) {
      let dsKhieuNai = [];
      let row = Config.KHIEUNAI_ROW();
      if (themmoi) {
        let donvi_dl_id = this.$root.token.getDonViDuLieuID();
        this.khieunai_id = await APIHandle.POST_GET_KEYS("khieunai_id");
        this.ma_kn = await this.TaoMaKN();
        row.KY_KN = this.dtpKyHD.substr(3, 4) + '' + this.dtpKyHD.substr(0, 2) + "01";
      } else {
        row.KY_KN = this.ky_kn;
      }
      row.KHIEUNAI_ID = this.khieunai_id;
      row.MA_TB = this.txtMaTB;
      row.CONGVAN_XL = this.congvanxl;
      row.MA_KN = this.ma_kn;
      row.THUEBAO_ID = this.thuebao_id;
      row.THANHTOAN_ID = this.thanhtoan_id;
      row.DICHVUVT_ID = Number(this.cboDichVu_VT);
      row.LOAITB_ID = Number(this.cboLoaiHinh);
      row.NGAY_TN = moment().format('DD/MM/YYYY HH:mm:ss');
      row.NGUOI_KN = this.txtNguoiKN;
      row.DIENTHOAI_LH = this.txtDienThoaiLH;
      row.TTKN_ID = TRANGTHAI_KHIEU_NAI.KHIEU_NAI_MOI;
      row.DONVI_ID = Number(this.$root.token.getDonViID());
      row.NHANVIEN_ID = Number(this.nhanVienId);
      row.CHUDEKN_ID = Number(this.cboChuDe_KN2);
      row.GHICHU_KN = this.txtGhiChu;
      row.ND_KHIEUNAI = this.txtNoiDungKN;
      row.QUYTRINH_ID = this.quytrinh_id;
      if (this.chkXuLy === 2)
        row.NOIDUNG_GQ = "";
      else
        row.NOIDUNG_GQ = this.txtNoiDungXL;

      if (this.chkHenTL) {
        row.HENGQ_TU = this.dtpTuNgay;
        row.HENGQ_DEN = this.dtpDenNgay;
      }
      row.MAY_CN = await this.$root.token.getMachine();
      row.NGUOI_CN = this.$root.token.getUserName();
      row.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss');
      row.CMT = this.txtCMT;
      row.NGAYCAP = this.txtNgayCap;
      row.NOICAP = this.txtNoiCap;
      row.DIACHI_LH = this.txtDiaChi;
      row.HTKN_ID = Number(this.cboHinhThuc_KN);

      dsKhieuNai.push(row);
      if (!themmoi) {
      }

      this.dsKhieuNai = [...dsKhieuNai];
      return dsKhieuNai;
    },

    async TaoMaKN() {
      let vmakn = "";
      let id = "";
      let ma_dv = "";
      let ma_tinh = "";
      let nam = "";
      id = await APIHandle.POST_GET_KEYS(this.axios, "ma_kn");
      ma_tinh = await APIHandle.mapData(this.axios, "tentat", DBConstants.DB2 + ".tinh",
        "where tinh_id = " + this.$root.token.getPhanVungID());
      ma_dv = await APIHandle.mapData(this.axios, "ma_dv", DBConstants.DB1 + ".donvi",
        "where donvi_id = " + this.$root.token.getDonViID());
      nam = moment().format('YYYY');
      vmakn = id + "/" + ma_dv + "/" + nam + "/" + ma_tinh;
      return vmakn;
    },

    async TaoDuLieu_HDTBDV() {
      let dskhieunai_dv = [];
      let dt_tmp = await APIHandle.sp_lay_huonggiao_ldv_quytrinh_id(this.axios, this.quytrinh_id);
      let inloaidv_id = 0;
      let indonvi_id = 0;
      for (let i = 0; i < dt_tmp.length; i++) {
        inloaidv_id = Number(dt_tmp[i]["loaidv_dich_id"]);

        if (inloaidv_id === LOAI_DV.DAILY_XLKN) {
          let ds_donvi = await APIHandle.LAY_DONVI_NGUOIDUNG_THEOBANG(this.axios, "NHANVIEN_TC", this.daily_id, LOAI_DV.DAILY_XLKN, Loai_NV.NHANVIEN_QLDL);
          if (ds_donvi.length > 0)
            indonvi_id = Number(ds_donvi[0]["donvi_id"]);
          else {
            this.$toast.error("Không tìm thấy đại lý để giao xử lý khiếu nại. Liên hệ admin để giải quyết !");
            return false;
          }
        } else {
          let str_donvi = await APIHandle.FN_HDTBDV_TIEPNHAN_PHKH(this.axios, this.thuebao_id, inloaidv_id, this.daily_id);
          if (str_donvi === '') {
            this.$toast.error("Không tìm thấy đại lý để giao xử lý khiếu nại. Liên hệ admin để giải quyết !");
            return false;
          }

          let arr = str_donvi.split('~');
          if (arr[0] != '1') {
            this.$toast.error(arr[1]);
            return false;
          }

          indonvi_id = arr[1];
        }

        let row = Config.KHIEUNAI_DV();
        row.KHIEUNAI_ID = this.khieunai_id;
        row.LOAIDV_ID = inloaidv_id;
        row.DONVI_ID = indonvi_id;
        dskhieunai_dv.push(row);

        this.dskhieunai_dv = [...dskhieunai_dv];
      }

      return true;
    },

    async TAODULIEU_TIENGC() {
      try {
        let dt = [...this.grvTienGC.data];
        if (dt == null)
          return;

        if (dt.length > 0) {
          let vds = [];
          for (let j = 0; j < dt.length; j++) {
            let row = {
              "TGC": dt[j]["thang_td"],
              "TIENGC": dt[j]["tien_gc"],
              "VAT": dt[j]["vat_gc"],
              "KYGC": dt[j]["ky_gc"]
            }
            vds.push(row);
          }
          await APIHandle.sp_taodulieu_tiengc(this.axios, this.khieunai_id, JSON.stringify(vds), 0);
        } else {
          await APIHandle.sp_taodulieu_tiengc(this.axios, this.khieunai_id, null, 1);
        }
      } catch (ex) {
        this.$toast.error("Không cập nhật được giữ liệu tháng giảm cước " + ex);
      }
    },

    PHAILAM(code) {
      return APIHandle.PHAILAM(this.axios, this.tthd_id, this.quytrinh_id, code);
    },

    async AddphieuTH(khieunai_id, donvi_id, donvi_nhan_id, huonggiao_id, tudong, phieu_cha_id, ttkn_id) {
      let dsGiaoPhieu = [];

      this.phieu_id = await APIHandle.POST_GET_KEYS("PHIEUKN_ID");
      let rowdata = Config.GIAOPHIEU_KN();
      rowdata.PHIEUKN_ID = this.phieu_id;
      rowdata.KHIEUNAI_ID = khieunai_id;
      rowdata.DONVI_GIAO_ID = donvi_id;
      rowdata.NHANVIEN_GIAO_ID = this.nhanVienId;
      rowdata.DONVI_NHAN_ID = donvi_nhan_id;
      rowdata.TTKN_ID = 1;
      rowdata.HUONGGIAO_ID = huonggiao_id;
      rowdata.NGAYGIAO = moment().format('DD/MM/YYYY HH:mm:ss');
      rowdata.ND_GIAO = "Tự động giao phiếu phản hồi KH";
      rowdata.TTPH_ID = TRANGTHAI_PH.MOI;
      rowdata.PHIEU_CHA_ID = phieu_cha_id;
      rowdata.TTKN_ID = ttkn_id;
      rowdata.NGUOI_CN = this.$root.token.getUserName();
      rowdata.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss');
      rowdata.MAY_CN = this.may_cn;
      rowdata.SOCONGVAN = "";

      dsGiaoPhieu.push(rowdata);

      return dsGiaoPhieu;
    },

    setDsKhieuNaiFocused(key, value) {
      let ids = [];
      if (this.gridviewDS_KHIEUNAI.data.length > 0) {
        this.gridviewDS_KHIEUNAI.data.forEach((item, index) => {
          if (item[key] === value) {
            ids.push(index);
          }
        });

        this.$refs.gridviewDS_KHIEUNAI.selectRows(ids);
      }

    },

    tsbtnXoa_Click() {
      try {
        if (this.gridviewDS_KHIEUNAI.data.length <= 0) {
          this.$toast.error("Danh sách trống. Bạn không thể thực hiện xóa!");
          return;
        }

        let rowsSelected = this.$refs.gridviewDS_KHIEUNAI.getSelectedRecords();
        if (rowsSelected.length <= 0) {
          this.$toast.error("Bạn chưa chọn phản hồi để xóa!");
          return;
        }

        this.$confirm('Bạn thật sự muốn xóa phản hồi không?',
          'Thông báo',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          })
          .then(async () => {
            try {
              this.loading(true);
              let rowsSelected = this.$refs.gridviewDS_KHIEUNAI.getSelectedRecords();
              for (const row of rowsSelected) {
                await APIHandle.xoa_khieunai(this.axios, row.khieunai_id);
              }
              this.$toast.success("Xóa dữ liệu trên viễn thông tỉnh thành công!");
              await this.HT_DS_KHIEUNAI();
            } catch (e) {
              console.log(e);
            } finally {

              this.loading(false);
            }
          });
      } catch (ex) {
        this.$toast.error("Không thể xóa bản ghi này," + ex);
      }
    },

    showFormTraCuuDanhBa() {
      this.$refs['popupSearchAccount'].show()
    },

    acceptSearchAccount(item) {
      this.txtMaTB = item.ma_tb;
      $('#txtMaTB').focus();
      this.$refs['popupSearchAccount'].hide();
    },

    async tsbtnKhoaPhieu_Click() {
      try {
        if (this.gridviewDS_KHIEUNAI.data.length <= 0) {
          this.$toast.error("Chưa có khiếu nại trong danh sách. Bạn không thể khóa phiếu !");
          return;
        }

        if (this.itemKhieuNaiFocused == null) {
          this.$toast.error("Chưa chọn phiếu khiếu nại để thực hiện khóa phiếu !");
          return;
        }

        if (!await this.KiemTraDuLieu_KhoaPhieu()) return;

        this.khieunai_id = this.itemKhieuNaiFocused.khieunai_id;
        let phieukn_id = this.itemKhieuNaiFocused.phieukn_id;
        let vquytrinh_id = this.itemKhieuNaiFocused.quytrinh_id;
        let hg_phieu_id = 0;
        let kt = await APIHandle.fn_khoaphieu_tiepnhan_phkh(this.axios,
          this.khieunai_id,
          phieukn_id,
          vquytrinh_id,
          this.$root.token.getDonVi(),
          this.nhanVienId,
          this.$root.token.getUserName(),
          this.may_cn,
          this.dtpNgayXL,
          this.cboNhanVien,
          this.txtNoiDungXL,
          this.cboLoi,
          this.txtNguyenNhan,
          this.txtTienDT.toString().trim() === '' ? 0 : Number(this.txtTienDT.toString().trim()),
          this.txtVatDT.toString().trim() === '' ? 0 : Number(this.txtVatDT.toString().trim()),
          this.txtGiamCuoc_CP.toString().trim() === '' ? 0 : Number(this.txtGiamCuoc_CP.toString().trim()),
          this.txtVatCP.toString().trim() === '' ? 0 : Number(this.txtVatCP.toString().trim()),
        );

        if (kt === '1') {
          this.$toast.success("Bạn đã khóa phiếu thành công !");
          await this.HT_DS_KHIEUNAI();
        } else {
          this.$toast.error(kt);
        }
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      }
    },

    async KiemTraDuLieu_KhoaPhieu() {
      if (Number(this.cboNhanVien) === -1) {
        this.$toast.error("Bạn chưa nhập người xử lý khiếu nại !");
        $('#cboNhanVien').focus();
        return false;
      }
      if (this.txtNoiDungXL.toString().trim() === "") {
        this.$toast.error("Bạn chưa nhập nội dung xử lý khiếu nại !");
        $('#txtNoiDungXL').focus();
        return false;
      }

      if (this.txtGiamCuoc_DT.toString().trim() === "") {
        this.$toast.error("Hãy nhập tiền giảm cước doanh thu!");
        // txtGiamCuoc_DT.focus();
        return false;
      }

      if (Number(this.cboLoi) === -1) {
        this.$toast.error("Hãy chọn kết quả lỗi!");
        $('#cboLoi').focus();
        return false;
      }

      if (this.txtGiamCuoc_CP.toString().trim() === "") {
        this.$toast.error("Hãy nhập tiền giảm cước chi phí!");
        // txtGiamCuoc_CP.focus();
        return false;
      }

      let vhinhthuckn_id = Number(await APIHandle.mapData(this.axios,
        "htkn_id", DBConstants.DB3 + ".khieunai",
        "where khieunai_id = " + this.khieunai_id));

      if (vhinhthuckn_id === HINHTHUC_KN.VAN_BAN) {
        let hoso = await APIHandle.mapData(this.axios,
          "hosokn_id", DBConstants.DB3 + ".hoso_kn",
          "where khieunai_id = " + this.khieunai_id);

        if (hoso == "-1") {
          this.$toast.error("Chưa Upload file văn bản. Bạn không thể khóa phiếu !");
          return false;
        }
      }

      return true;
    },

    async tsbtnGiaoPhieu_Click() {
      try {
        if (this.gridviewDS_KHIEUNAI.data.length > 0) {

          let vhinhthuckn_id = Number(await APIHandle.mapData(this.axios,
            "htkn_id", DBConstants.DB3 + ".khieunai", "where khieunai_id = " + this.khieunai_id));
          if (vhinhthuckn_id === HINHTHUC_KN.VAN_BAN) {
            let hoso = awaitAPIHandle.mapData(this.axios, "hosokn_id", DBConstants.DB3 + ".hoso_kn", "where khieunai_id = " + this.khieunai_id);
            if (hoso == "-1") {
              this.$toast.error("Chưa Upload file văn bản. Bạn không thể giao phiếu !");
              return;
            }
          }

          let quytrinh_id = Number(await APIHandle.mapData(this.axios, "quytrinh_id", DBConstants.DB3 + ".khieunai",
            "where khieunai_id = " + this.khieunai_id));

          let vhuonggiao_id = await APIHandle.Lay_HuongGiao_QuyTrinh(this.axios, quytrinh_id, 2);
          let vquyen_huonggiao = await APIHandle.Lay_DK_Cho_Bang(this.axios,
            this.$root.token.getNguoiDungID(),
            this.$root.token.getPhanVungID(),
            'HUONGGIAO'
          );
          if (vquyen_huonggiao === '') vquyen_huonggiao = "-1";

          let dt_hg = await APIHandle.LAY_DS_HUONGGIAO(this.axios, NHOM_QT.GIAIQUYET_KN, 0, 0, 0, vquyen_huonggiao);
          let filter_hg = dt_hg.filter(item => {
            return Number(item.huonggiao_id) === Number(vhuonggiao_id);
          });

          if (filter_hg.length > 0) {
            if (vhuonggiao_id != 0) {
              this.$toast.error('Gọi frmGiaoPhieu_PHKH - Chờ khảo sát');
              // frmGiaoPhieu_PHKH
              // f = new frmGiaoPhieu_PHKH(1, vhuonggiao_id, txtMaTB.Text.Trim(), 1, "0");
              // f.MdiParent = this.MdiParent;
              // f.Show();
            }
          } else {
            this.$toast.error('Gọi frmGiaoPhieu_PHKH - Chờ khảo sát');
            // frmGiaoPhieu_PHKH
            // f = new frmGiaoPhieu_PHKH();
            // f.MdiParent = this.MdiParent;
            // f.Show();
          }

          // this.HT_DS_KHIEUNAI();
        }
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      }
    },

    getDiaChi: function (data) {
      this.txtDiaChi = data.diachimoi
    },

    tsbtnThongSoKT_Click() {
      this.$refs.frmThongSoKyThuat.openDialog(this.thuebao_id);
    }
  }
}
</script>

<style scoped>
/deep/ .modal-body .page-content {
  position: inherit;
}
</style>

<style>
.kyhd .select2-selection__rendered {
  color: red !important;
  font-weight: bold !important;
}
</style>
