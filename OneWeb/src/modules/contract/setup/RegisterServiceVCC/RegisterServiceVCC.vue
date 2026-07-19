<template src="./RegisterServiceVCC.html"></template>
<style src="./RegisterServiceVCC.scss" scoped></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import DataGrid from "@/components/Controls/DataGrid";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from "./API.js";
import {
  LoaiHopDong,
  TrangThaiHD,
  TrangThaiTB,
  DichVuVienThong,
  TRANGBI,
  KieuLapDat,
  KHOANMUC_TT,
} from "@/modules/admin/category/MultiServicePackage/enum.js";
import frmGoiVCC from "./frmGoiVCC.vue"
import DanhSachThueBaoCungMaModal from "@/modules/contract/setup/ChangeSubsType/popups/DanhSachThueBaoCungMaModal.vue";

export default {
  components: {
    breadcrumb,
    DataGrid,
    DatePicker,
    frmGoiVCC,
    DanhSachThueBaoCungMaModal,
  },
  name: "RegisterServiceVCC",
  data() {
    return {
      tab: 1,
      huonggiao_id: 5601,
      dichvuvt_id: DichVuVienThong.DI_DONG,
      dateFormat: "DD/MM/YYYY",
      today: moment(new Date()).format("DD/MM/YYYY"),
      hdtb_id: 0,
      header: {
        title: "Đăng ký dịch vụ Cloud Contact Center (VCC)",
        list: [
          // { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          // { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          // { name: "Đổi tốc độ - Loại hình thuê bao", link: { name: "Ui.cards" } },
          {
            name: "Đăng ký dịch vụ Cloud Contact Center (VCC)",
            link: { name: "Ui.cards" },
            active: true,
          },
        ],
      },
      button: {
        btnDangky: { Text: "Đăng ký", Enabled: false },
        btnHuy: { Text: "Hủy", Enabled: false },
        btnTamngung: { Text: "Tạm ngưng", Enabled: false },
        btnKichhoatlai: { Text: "Kích hoạt lại", Enabled: false },
        btnCapnhatgoi: { Text: "Cập nhật gói", Enabled: false },
        bntLayGoi: { Text: "Lấy gói", Enabled: false },
        btnThemmoi3: { Text: "Thêm mới", Enabled: false },
        btnHuy3: { Text: "Hủy", Enabled: false },
        btnCapnhat3: { Text: "Cập nhật", Enabled: false },
      },      
      tt_nd: {
        USER_NEO: "",
        ma_tinh: "",
        phanvung_id: "",
        donvi_id: "",
        nhanvien_id: "",
        ip_cn: "",
        may_cn: "",
        nguoi_cn: "",
      },
      dsTBCungMa: [],
      tracuu_ccbs: [],
      loi_sinh_magd: false,
      //tab1
      dtLoai: [
        {
          id: 1,
          text: "Hòa mạng",
        },
        {
          id: 2,
          text: "Chuyển loại",
        },
      ],
      dtLoaiVCC: [],
      dtgiacuoc_kt: [],
      dtvcc_chinh: [],
      dtvcc_le: [],
      dtvcc_user: [],
      dtpt_thanhtoan: [],
      dtkieu_baocuoc: [],
      dtNganHang: [],
      dtloaikh: [],
      dtNguoi_GT: [],
      dulieu: {},
      tab_dkVCC: {
        txtSoMay: {value: ""},
        cboLoaiKT: {value: 1},
        cboLoaiNhom: {value: 0},
        txtGoicuocvcc: {value: "", Enabled: false},
        txtUser: { value: "", Enabled: false},
        txtMaylevcc: { value: "", Enabled: false},
        cboGiamgia: { value: 0, Enabled: false},
        cboVCCchinh: { value: 0, Enabled: false},
        cboVCCle: { value: 0, Enabled: false},
        cboUser: { value: 0, Enabled: false},
        txtNganhnghe: {value: ""},
        txtGPKD: {value: ""},
        txtNoicap: {value: ""},
        dtpTungay: {value: ""},
        txtFax: {value: ""},
        txtEmail: {value: ""},
        txtDiachi: {value: ""},
        txtWebsite: {value: ""},
        txtNguoidaidien: {value: ""},
        txtTencoquan: {value: ""},
        txtDienthoai: {value: ""},
        txtMst: {value: ""},
        txtDiachiTK: {value: ""},
        txtDiachiGD: {value: ""},
        cboPhuongthuc: {value: 0},
        cbokieuthongbao: {value: 0},
        cboNganhang: {value: 0},
        txtTaikhoanNH: {value: ""},
        cboGioithieu: {value: 0},
        cboLoaihinhDN: {value: 0},
        txtGhichu: {value: ""},
      },
      formQuanTri: {
        textQuantri: {value: ""},
        txtDienthoaiQT: {value: ""},
        txtEmailQT: {value: ""},
        txtChucdanhQT: {value: ""},
      },
      grdVCC: {
        list: [],
        header: [
          {
            fieldName: "CUSTID",
            headerText: "STT",
            allowFiltering: true,
          },
          {
            fieldName: "SO_TB",
            headerText: "Số VCC",
            allowFiltering: true,
          },
          {
            fieldName: "TENCOQUAN",
            headerText: "Tên khách hàng",
            allowFiltering: true,
          },
          {
            fieldName: "DIACHITT",
            headerText: "Địa chỉ thanh toán",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_DK",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      //tab2
      dtnoimang2: [],
      dtngoaimang2: [],
      tab_cnVCC: {
        txtSoMay2: {value: ""},
        cboNoiMang2: {value: 0},
        cboNgoaiMang2: {value: 0},
      },
      grdGoiNoiMang2: {
        list: [],
        header: [          
          {
            fieldName: "ID",
            headerText: "MÃ GÓI",
            allowFiltering: true,
          },
          {
            fieldName: "NAMEPKG",
            headerText: "GÓI NỘI MẠNG",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grdGoiNgoaiMang2: {
        list: [],
        header: [
          {
            fieldName: "ID",
            headerText: "MÃ GÓI",
            allowFiltering: true,
          },
          {
            fieldName: "NAMEPKG",
            headerText: "GÓI NGOẠi MẠNG",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grdGoidadk: {
        list: [],
        header: [
          {
            fieldName: "ID",
            headerText: "ID",
            allowFiltering: true,
          },
          {
            fieldName: "PACKAGE_ID",
            headerText: "ID Gói",
            allowFiltering: true,
          },
          {
            fieldName: "PACKAGE_NAME",
            headerText: "Tên gói",
            allowFiltering: true,
          },
          {
            fieldName: "STATUS",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
          {
            fieldName: "TIME_START",
            headerText: "Ngày đăng ký gói",
            allowFiltering: true,
          },
          {
            fieldName: "TIME_END",
            headerText: "Ngày hết hạn",
            allowFiltering: true,
          },
          {
            fieldName: "action",
            headerText: "Xóa",
            textAlign: "center",
            width: 70,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <button type="button" class="btn btn-primary" @click="onbtnHuyGiao_Click">
                        <span class="fa fa-remove f20 inline vcenter"></span>
                      </button>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  methods: {
                    onbtnHuyGiao_Click() {
                      this.$parent.$parent.$parent.btnHuyGiao_Click(this.data);
                    },
                  },
                },
              };
            },
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      //tab3
      dtdichvubs: [],
      dtKycuoc: [
        {
          VALUE: 0,
          NAME: "Hàng tháng",
        },
        {
          VALUE: 1,
          NAME: "1 lần",
        },
      ],
      tab_qldv: {
        txtSoMay3: {value: ""},
        cboDanhmucbs: {value: 0},
        txtTienVat: {value: "", Enabled: false},
        txtKhoiLuong: { value: "", Enabled: false},
        cboKycuoc: { value: 0, Enabled: false},
        txtVNPT: { value: "", Enabled: false},
        txtDoitac: { value: "", Enabled: false},
        textGhichu3: { value: "", Enabled: false},
      },
      check_click: 0,
      gg_goinoimang: "100",
      gg_goingoaimang: "100",
      grdDVBS: {
        list: [],
        header: [
          {
            fieldName: "ID",
            headerText: "ID",
            allowFiltering: true,
          },
          {
            fieldName: "SO_TB",
            headerText: "Số hotline",
            allowFiltering: true,
          },
          {
            fieldName: "GIATIEN",
            headerText: "Giá tiền",
            allowFiltering: true,
          },
          {
            fieldName: "KHOILUONG",
            headerText: "Khối lượng",
            allowFiltering: true,
          },
          {
            fieldName: "KYCUOC",
            headerText: "Kỳ cước",
            allowFiltering: true,
          },
          {
            fieldName: "TYLEPHANCHIA",
            headerText: "Tỷ lệ phân chia",
            allowFiltering: true,
          },
          {
            fieldName: "STATUS_NAME",
            headerText: "Hiệu lực",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOITH",
            headerText: "Người thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "NGAYTH",
            headerText: "Ngày thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "DANHMUC",
            headerText: "Danh mục",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds: [],
      ds2: [],
      ds3: [],
      dsHDKH: [],
      dsHDTB: [],
    };
  },
  methods: {
    Lay_Ma_GD: async function (loaihd_id) {
      try {
        let ma_gd = this.GetData(await api.post_sinh_magd(this.axios, loaihd_id));
        return ma_gd
      } catch (e) {
        return ""
      }      
    },
    ts_vcc_ds_thuebao: async function () {
      try {
        let data = this.GetData(await api.ts_vcc_ds_thuebao(this.axios));
        this.grdVCC.list = data;
      } catch (e) {

      }      
    },
    ts_vcc_ds_goi_tb: async function (somay) {
      try {
        var input = {
          so_tb: somay
        }
        let data = this.GetData(await api.ts_vcc_ds_goi_tb(this.axios, input));
        return data;
      } catch (e) {

      }      
    },
    ts_vcc_ds_tb_dichvubs: async function (somay) {
      try {
        var input = {
          so_tb: somay
        }
        let data = this.GetData(await api.ts_vcc_ds_tb_dichvubs(this.axios, input));
        return data;
      } catch (e) {

      }
    },
    ts_vcc_ds_loaivcc: async function () {
      try {
        let data = this.GetData(await api.ts_vcc_ds_loaivcc(this.axios));
        this.dtLoaiVCC = data && data.length > 0 ? data.map((e) => ({
          id: e.ID,
          text: e.VCC_PACKAGE_NAME,
        })) : [];
      } catch (e) {

      }      
    },
    ts_vcc_ds_muc_giamgia: async function (type_id) {
      try {
        var input = {
          type_id: type_id,
        };
        let data = this.GetData(
          await api.ts_vcc_ds_muc_giamgia(this.axios, input)
        );
        return data;
      } catch (e) {

      }      
    },
    ts_dichvu_pt_thanhtoan: async function () {
      try {
        let data = this.GetData(await api.ts_dichvu_pt_thanhtoan(this.axios));
        this.dtpt_thanhtoan = data && data.length > 0 ? data.map((e) => ({
          id: e.PAYMETHOD_ID,
          text: e.PAYMETHOD_NAME,
        })) : [];
      } catch (e) {

      }      
    },
    ts_dichvu_kieu_baocuoc: async function () {
      try {
        let data = this.GetData(await api.ts_dichvu_kieu_baocuoc(this.axios));
        this.dtkieu_baocuoc = data && data.length > 0 ? data.map((e) => ({
          id: e.METHOD_ID,
          text: e.METHOD_NAME,
        })) : [];
      } catch (e) {

      }      
    },
    ts_dm_nganhang_ds: async function () {
      try {
        let data = this.GetData(await api.ts_dm_nganhang_ds(this.axios));
        this.dtNganHang = data && data.length > 0 ? data.map((e) => ({ id: e.NGANHANG_ID, text: e.TEN })) : [];
      } catch (e) {

      }      
    },
    ts_ds_loaikh: async function () {
      try {
        let data = this.GetData(await api.ts_ds_loaikh(this.axios));
        this.dtloaikh = data && data.length > 0 ? data.map((e) => ({ id: e.LOAIKH_ID, text: e.LOAI_KH })) : [];
      } catch (e) {

      }
    },
    siptrungking_dichvu_nguoi_gt: async function () {
      try {
        var donviql_id = await this.$root.token.getDonViID();
        var input = {
          donviql_id: donviql_id,
        };
        let data = this.GetData(
          await api.siptrungking_dichvu_nguoi_gt(this.axios, input)
        );
        this.dtNguoi_GT = data && data.length > 0 ? data.map((e) => ({ id: e.MA_NGUOI, text: e.ORDS })) : [];
      } catch (e) {
        this.dtNguoi_GT = []
      }   
    },
    ts_vcc_ds_goicuoc: async function (type_id) {
      try {
        var input = {
          type: type_id,
        };
        let data = this.GetData(await api.ts_vcc_ds_goicuoc(this.axios, input));
        return data;
      } catch (e) {
        return []
      }     
    },
    ts_vcc_ds_dichvubs: async function () {
      try {
        let data = this.GetData(await api.ts_vcc_ds_dichvubs(this.axios));
        this.dtdichvubs = data;
      } catch (e) {

      }      
    },
    ts_vcc_lay_tttb: async function (so_tb) {
      try {
        var input = {
          so_tb: so_tb,
        };
        let data = this.GetData(await api.ts_vcc_lay_tttb(this.axios, input));
        return data;
      } catch (e) {

      }      
    },
    LAY_DANHBA_THEO_MATB: async function (ma_tb, dichvuvt_id, donvi_dl_id) {
      try {
        var input = {
          in_ma_tb: ma_tb,
          in_dichvuvt_id: dichvuvt_id,
          in_donvi_dl_id: donvi_dl_id,
        };
        let data = this.GetData(
          await api.lay_danhba_theo_matb(this.axios, input)
        );
        return data;
      } catch (e) {

      }      
    },
    KIEMTRA_MA_TB_LAP_HOPDONG_KHAC: async function (
      ma_tb,
      ma_gd,
      dichvuvt_id,
      loaihd_id
    ) {
      try {
        var input = {
          ma_tb: ma_tb,
          ma_gd: ma_gd,
          dichvuvt_id: dichvuvt_id,
          loaihd_id: loaihd_id,
        };
        let data = this.GetData(
          await api.kiemtra_ma_tb_lap_hopdong_khac(this.axios, input)
        );
        if (data == "OK") return true;
        else return false;
      } catch (e) {
        return false
      }      
    },
    Loadthuoctinh_3() {
      this.tab_qldv.txtTienVat.value = "11000";
      this.tab_qldv.txtVNPT.value = "50";
      this.tab_qldv.txtDoitac.value = "50";
      this.tab_qldv.txtKhoiLuong.value = "";

      this.tab_qldv.cboKycuoc.Enabled = false;
      this.tab_qldv.txtTienVat.Enabled = false;
      this.tab_qldv.txtVNPT.Enabled = false;
      this.tab_qldv.txtDoitac.Enabled = false;
      this.tab_qldv.txtKhoiLuong.Enabled = true;
    },
    ts_vcc_dangky_dichvu: async function (tungay, nguoi_gt) {
      try {
        var input = {
          "dia_chi": this.tab_dkVCC.txtDiachi.value.toString(),
          "diachi_tk": this.tab_dkVCC.txtDiachiTK.value.toString(),
          "diachi_tt": this.tab_dkVCC.txtDiachiGD.value.toString(),
          "dienthoai_lh": this.tab_dkVCC.txtDienthoai.value.toString(),
          "ds_goicuoc": this.tab_dkVCC.txtGoicuocvcc.value.toString(),
          "email": this.tab_dkVCC.txtEmail.value.toString(),
          "fax": this.tab_dkVCC.txtFax.value.toString(),
          "ghi_chu": this.tab_dkVCC.txtGhichu.value.toString(),
          "giam_kt": this.tab_dkVCC.cboGiamgia.value.toString(),
          "giam_mayle": this.tab_dkVCC.cboVCCle.value.toString(),
          "giam_ngoaimg": this.gg_goingoaimang.toString(),
          "giam_noimg": this.gg_goinoimang.toString(),
          "giam_user": this.tab_dkVCC.cboUser.value.toString(),
          "giam_vccchinh": this.tab_dkVCC.cboVCCchinh.value.toString(),
          "gpkd": this.tab_dkVCC.txtGPKD.value.toString(),
          "kieu_tbc": this.tab_dkVCC.cbokieuthongbao.value.toString(),
          "linhvuc": this.tab_dkVCC.txtNganhnghe.value.toString(),
          "loai_kh": this.tab_dkVCC.cboLoaihinhDN.value.toString(),
          "loai_kt": this.tab_dkVCC.cboLoaiKT.value.toString(),
          "loai_vcc": this.tab_dkVCC.cboLoaiNhom.value.toString(),
          "ma_so_thue": this.tab_dkVCC.txtMst.value.toString(),
          "nganhang_id": this.tab_dkVCC.cboNganhang.value.toString(),
          "ngay_cap": tungay.toString(),
          "nguoi_dd": this.tab_dkVCC.txtNguoidaidien.value.toString(),
          "nguoi_gt": nguoi_gt.toString(),
          "noi_cap": this.tab_dkVCC.txtNoicap.value.toString(),
          "phuongthuc_tt": this.tab_dkVCC.cboPhuongthuc.value.toString(),
          "quantri_chucdanh": this.formQuanTri.txtChucdanhQT.value.toString(),
          "quantri_email": this.formQuanTri.txtEmailQT.value.toString(),
          "quantri_tel": this.formQuanTri.txtDienthoaiQT.value.toString(),
          "quantri_ten": this.formQuanTri.textQuantri.value.toString(),
          "so_tb": "84" + this.tab_dkVCC.txtSoMay.value.toString(),
          "soluong_exten": this.tab_dkVCC.txtMaylevcc.value,
          "soluong_user": this.tab_dkVCC.txtUser.value.toString(),
          "tai_khoan": this.tab_dkVCC.txtTaikhoanNH.value.toString(),
          "ten_cq": this.tab_dkVCC.txtTencoquan.value.toString(),
          "website": this.tab_dkVCC.txtWebsite.value.toString()
        };
        var response = await api.ts_vcc_dangky_dichvu(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess(this.button.btnDangky.Text + " dịch vụ VCC cho thuê bao " + this.tab_dkVCC.txtSoMay.value + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
    ts_vcc_huy_dichvu: async function (somay, user_neo, ip, ma_tinh) {
      try {
        var input = {
          so_tb: somay
        };
        var response = await api.ts_vcc_huy_dichvu(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess("Hủy dịch vụ VCC cho thuê bao " + somay + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
	  ts_vcc_tamngung_dichvu: async function (somay, user_neo, ip, ma_tinh) {
      try {
        var input = {
          so_tb: somay
        };
        var response = await api.ts_vcc_tamngung_dichvu(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess("Tạm dừng dịch vụ VCC cho thuê bao " + somay + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
    ts_vcc_kichhoat_dichvu: async function (somay, user_neo, ip, ma_tinh) {
      try {
        var input = {
          so_tb: somay
        };
        var response = await api.ts_vcc_kichhoat_dichvu(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess("Kích hoạt lại dịch vụ VCC cho thuê bao " + somay + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
    ts_vcc_capnhat_goi: async function (item) {
      try {
        var input = {
          ds_goi: item.ds_goi,
          so_tb: item.so_tb,
          check_giamnoimg: item.check_giamnoimg,
          check_giamngoaimg: item.check_giamngoaimg,
        };        
        var response = await api.ts_vcc_capnhat_goi(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess("Cập nhật gói VCC cho thuê bao " + somay + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
    ts_vcc_huy_goi: async function (ma_tinh, somay, goi_huy_id, goi_huy, user_neo) {
      try {
        var input = {
          so_tb: somay,
          id: goi_huy_id,
          package_id: goi_huy
        };
        var response = await api.ts_vcc_huy_goi(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess("Hủy gói " + goi_huy + " VCC cho thuê bao " + somay + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
    ts_vcc_dichvubs_dangky: async function (input, themmoi) {
      try {
        var response = await api.ts_vcc_dichvubs_dangky(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess((themmoi == 0 ? "Đăng ký" : "Cập nhật") + " danh mục bổ sung cho thuê bao " + input.so_tb + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
    ts_vcc_dichvubs_huy: async function (input) {
      try {
        var response = await api.ts_vcc_dichvubs_huy(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {
          if (response.data.data.IssSuccessed)
            this.ShowSuccess("Hủy danh mục bổ sung cho thuê bao " + input.so_tb + " thành công!");          
        } else {
          this.ShowError("Lỗi CCBS: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log(e)
      }      
    },
    ThemMoi_HopDong_siptrunking: async function (themmoi, dsHDKH, dsHDTB, huonggiao_id, loaihd_id) {
      try {
        var input = {
          HUONGGIAO_ID: huonggiao_id,
          HDTB_ID: this.hdtb_id,
          THEMMOI: themmoi,
          LOAIHD_ID: loaihd_id,
          HD_KHACHHANG: this.UpperCasePropertyList(dsHDKH),
          HD_THUEBAO: this.UpperCasePropertyList(dsHDTB),
        };
        var response = await api.fn_frmdangky_dv_vcc_ttqt_dangky(this.axios, input);
        if (response.data.error == 200 || response.data.error == "200") {   
          this.ShowError("Thêm mới HĐ siptrunking thành công!");
        } else {
          this.ShowError("Lỗi thêm mới HĐ siptrunking: " + response.data.message);
          return;
        }
      } catch (e) {
        console.log("ThemMoi_HopDong_siptrunking")
        console.log(e)
      }
    },
    load_tab1: async function () {
      try {
        await Promise.all([
          this.ts_vcc_ds_thuebao(),
          this.ts_vcc_ds_loaivcc(),
          this.ts_dichvu_pt_thanhtoan(),
          this.ts_dichvu_kieu_baocuoc(),
          this.ts_dm_nganhang_ds(),
          this.ts_ds_loaikh(),
          this.siptrungking_dichvu_nguoi_gt(),
        ]);
        var dtgiacuoc_kt = await this.ts_vcc_ds_muc_giamgia("1");
        if (dtgiacuoc_kt && dtgiacuoc_kt.length > 0) {
          this.dtgiacuoc_kt = dtgiacuoc_kt.map((e) => ({
            id: e.MAX_VALUE,
            text: e.NAME,
          }));
        }        
        var dtvcc_chinh = await this.ts_vcc_ds_muc_giamgia("2");
        if (dtvcc_chinh && dtvcc_chinh.length > 0) {
          this.dtvcc_chinh = dtvcc_chinh.map((e) => ({
            id: e.RATING_,
            text: e.NAME,
          }));
        }        

        var dtvcc_le = await this.ts_vcc_ds_muc_giamgia("4");
        if (dtvcc_le && dtvcc_le.length > 0) {
          this.dtvcc_le = dtvcc_le.map((e) => ({ id: e.RATING_, text: e.NAME }));
        }        

        var dtvcc_user = await this.ts_vcc_ds_muc_giamgia("5");
        if (dtvcc_user && dtvcc_user.length > 0) {
          this.dtvcc_user = dtvcc_user.map((e) => ({
            id: e.RATING_,
            text: e.NAME,
          }));
        }        

        this.tab_dkVCC.cboLoaiNhom.value = this.dtLoaiVCC && this.dtLoaiVCC.length > 0 ? this.dtLoaiVCC[0].id : "";
        this.tab_dkVCC.cboGiamgia.value = this.dtgiacuoc_kt && this.dtgiacuoc_kt.length > 0 ? this.dtgiacuoc_kt[0].id : 0;
        this.tab_dkVCC.cboVCCchinh.value = this.dtvcc_chinh && this.dtvcc_chinh.length > 0 ? this.dtvcc_chinh[0].id : 0;
        this.tab_dkVCC.cboVCCle.value = this.dtvcc_le && this.dtvcc_le.length > 0 ? this.dtvcc_le[0].id : 0;
        this.tab_dkVCC.cboUser.value = this.dtvcc_user && this.dtvcc_user.length > 0 ? this.dtvcc_user[0].id : 0;
        this.tab_dkVCC.cboPhuongthuc.value = this.dtpt_thanhtoan && this.dtpt_thanhtoan.length > 0 ? this.dtpt_thanhtoan[0].id : "";
        this.tab_dkVCC.cbokieuthongbao.value = this.dtkieu_baocuoc && this.dtkieu_baocuoc.length > 0 ? this.dtkieu_baocuoc[0].id : "";
        this.tab_dkVCC.cboNganhang.value = this.dtNganHang && this.dtNganHang.length > 0 ? this.dtNganHang[0].id : 0;
        this.tab_dkVCC.cboLoaihinhDN.value = this.dtloaikh && this.dtloaikh.length > 0 ? this.dtloaikh[0].id : 0;
        this.tab_dkVCC.cboGioithieu.value = this.dtNguoi_GT && this.dtNguoi_GT.length > 0 ? this.dtNguoi_GT[0].id : "";
      } catch (e) {
        console.log("load_tab1")
        console.log(e)
      }      
    },
    load_tab2: async function () {
      try {     
        this.grdGoiNoiMang2.list = await this.ts_vcc_ds_goicuoc("1");
        if (this.grdGoiNoiMang2.list && this.grdGoiNoiMang2.list.length > 0) {
          for (var i = 0; i < this.grdGoiNoiMang2.list.length; i++) {
            this.grdGoiNoiMang2.list[i].ISCHECK = false
          }
        }

        this.grdGoiNgoaiMang2.list = await this.ts_vcc_ds_goicuoc("2");
        if (this.grdGoiNgoaiMang2.list && this.grdGoiNgoaiMang2.list.length > 0) {
          for (var i = 0; i < this.grdGoiNgoaiMang2.list.length; i++) {
            this.grdGoiNgoaiMang2.list[i].ISCHECK = false
          }
        }

        this.dtnoimang2 = await this.ts_vcc_ds_muc_giamgia("3");
        this.dtngoaimang2 = await this.ts_vcc_ds_muc_giamgia("3");

        this.tab_cnVCC.cboNoiMang2.value =
          this.dtnoimang2.length > 0 ? this.dtnoimang2[0].RATING_ : 0;
        this.tab_cnVCC.cboNgoaiMang2.value =
          this.dtngoaimang2.length > 0 ? this.dtngoaimang2[0].RATING_ : 0;
      } catch (e) {
        console.log("load_tab2")
        console.log(e)
      }
    },
    load_tab3: async function () {
      try {
        await Promise.all([this.ts_vcc_ds_dichvubs()]);
        this.tab_qldv.cboDanhmucbs.value = this.dtdichvubs[0].ID;
        this.Loadthuoctinh_3();
      } catch (e) {
        console.log("load_tab3")
        console.log(e)
      }
    },
    Load_trangthai(themmoi) {
      this.tab_dkVCC.txtGoicuocvcc.Enabled = themmoi;
      this.tab_dkVCC.cboGiamgia.Enabled = themmoi;

      this.tab_dkVCC.txtUser.Enabled = themmoi;
      this.tab_dkVCC.txtMaylevcc.Enabled = themmoi;

      this.tab_dkVCC.cboVCCchinh.Enabled = themmoi;
      this.tab_dkVCC.cboVCCle.Enabled = themmoi;
      this.tab_dkVCC.cboUser.Enabled = themmoi;
    },
    Load_view4() {
      try {
        this.button.btnThemmoi3.Enabled = false;
        this.button.btnHuy3.Enabled = false;
        this.button.btnCapnhat3.Enabled = false;
        this.grdDVBS.list = [];
        this.tab_qldv.cboDanhmucbs.value = "5";
        this.tab_qldv.cboKycuoc.value = this.dtKycuoc[0].VALUE;
        this.Loadthuoctinh_3();
      } catch (e) {
        console.log("Load_view4")
        console.log(e)
      }      
    },
    Clear() {
      // Clear phần text
      this.tab_dkVCC.txtGoicuocvcc.value = "";
      this.tab_dkVCC.txtNganhnghe.value = "";
      this.tab_dkVCC.dtpTungay.value = "";
      this.tab_dkVCC.txtFax.value = "";
      this.tab_dkVCC.txtDiachi.value = "";
      this.tab_dkVCC.txtWebsite.value = "";
      this.tab_dkVCC.txtTencoquan.value = "";
      this.tab_dkVCC.txtDiachiTK.value = "";
      this.tab_dkVCC.txtDiachiGD.value = "";
      this.tab_dkVCC.txtMst.value = "";
      this.tab_dkVCC.txtGhichu.value = "";
      this.tab_dkVCC.txtGPKD.value = "";
      this.tab_dkVCC.txtNoicap.value = "";
      this.tab_dkVCC.txtEmail.value = "";
      this.tab_dkVCC.txtNguoidaidien.value = "";
      this.tab_dkVCC.txtDienthoai.value = "";
      this.tab_dkVCC.txtTaikhoanNH.value = "";
      this.tab_dkVCC.txtUser.value = "1";
      this.tab_dkVCC.txtMaylevcc.value = "3";

      // clear combo
      this.Load_trangthai(false);

      // Form quản trị
      this.formQuanTri.textQuantri.value = "";
      this.formQuanTri.txtEmailQT.value = "";
      this.formQuanTri.txtDienthoaiQT.value = "";
      this.formQuanTri.txtChucdanhQT.value = "";

      this.button.bntLayGoi.Enabled = false;
      this.tab_dkVCC.cboLoaiKT.value = this.dtLoai.length > 0 ? this.dtLoai[0].id : 0;
      this.tab_dkVCC.cboLoaiNhom.value =
        this.dtLoaiVCC.length > 0 ? this.dtLoaiVCC[0].id : "";
      this.gg_goinoimang = "100";
      this.gg_goingoaimang = "100";
      this.tab_dkVCC.cboGiamgia.value =
        this.dtgiacuoc_kt.length > 0 ? this.dtgiacuoc_kt[0].id : 0;
      this.tab_dkVCC.cboVCCchinh.value =
        this.dtvcc_chinh.length > 0 ? this.dtvcc_chinh[0].id : 0;
      this.tab_dkVCC.cboVCCle.value =
        this.dtvcc_le.length > 0 ? this.dtvcc_le[0].id : 0;
      this.tab_dkVCC.cboUser.value =
        this.dtvcc_user.length > 0 ? this.dtvcc_user[0].id : 0;
      this.tab_dkVCC.cboPhuongthuc.value =
        this.dtpt_thanhtoan.length > 0 ? this.dtpt_thanhtoan[0].id : "";
      this.tab_dkVCC.cbokieuthongbao.value =
        this.dtkieu_baocuoc.length > 0 ? this.dtkieu_baocuoc[0].id : "";
      this.tab_dkVCC.cboNganhang.value =
        this.dtNganHang.length > 0 ? this.dtNganHang[0].id : 0;
      this.tab_dkVCC.cboLoaihinhDN.value =
        this.dtloaikh.length > 0 ? this.dtloaikh[0].id : 0;
      this.tab_dkVCC.cboGioithieu.value =
        this.dtNguoi_GT.length > 0 ? this.dtNguoi_GT[0].id : "";

      // sheet 2
      this.grdGoidadk.list = [];
      // grvGoiNoiMang2.CloseEditor();
      // grvGoiNgoaiMang2.CloseEditor();
      this.tab_cnVCC.cboNoiMang2.value =
        this.dtnoimang2.length > 0 ? this.dtnoimang2[0].RATING_ : 0;
      this.tab_cnVCC.cboNgoaiMang2.value =
        this.dtngoaimang2.length > 0 ? this.dtngoaimang2[0].RATING_ : 0;

      if (this.grdGoiNoiMang2.list && this.grdGoiNoiMang2.list.length > 0) {
        for (var i = 0; i < this.grdGoiNoiMang2.list.length; i++) {
          this.grdGoiNoiMang2.list[i].ISCHECK = false
        }
      }

      if (this.grdGoiNgoaiMang2.list && this.grdGoiNgoaiMang2.list.length > 0) {
        for (var i = 0; i < this.grdGoiNgoaiMang2.list.length; i++) {
          this.grdGoiNgoaiMang2.list[i].ISCHECK = false
        }
      }

      //sheet 3
      this.button.btnThemmoi3.Enabled = false;
      this.button.btnCapnhat3.Enabled = false;
      this.button.btnHuy3.Enabled = false;
      this.ds = [];
      this.ds2 = [];
      this.ds3 = [];
    },
    HienThiTT_DanhBa: async function (ma_tb) {
      try {     
        this.tracuu_ccbs = [];
        this.check_click = 0;
        var promise_tracuuCCBS = this.ts_vcc_lay_tttb("84" + this.tab_dkVCC.txtSoMay.value);
        this.tracuu_ccbs = await promise_tracuuCCBS;

        var promise_dbtb = this.LAY_DANHBA_THEO_MATB(ma_tb, this.dichvuvt_id, 0); //Lấy ra bất kỳ thuê bao nào
        var ds = await promise_dbtb;
        this.dsTBCungMa = [];
        if (ds.length > 0) {
          // Thuê bao phai co trong danh ba
          if (ds.length > 1) {
            this.dsTBCungMa = ds;
            this.$refs.danhSachThueBaoCungMaModal.showModal();
          } else {
            if (
              Number(ds[0]["trangthaitb_id"].toString()) == TrangThaiTB.THANHLY ||
              Number(ds[0]["trangthaitb_id"].toString()) ==
                TrangThaiTB.THANHLY_NO ||
              Number(ds[0]["trangthaitb_id"].toString()) == TrangThaiTB.DOISO
            ) {
              this.ShowError(
                "Thuê bao có mã: " +
                  ds[0]["ma_tb"].toString() +
                  " đang ở trạng thái: " +
                  ds[0]["trangthai_tb"].toString() +
                  "\nBạn không thể thực hiện cập nhật TT thuê bao này"
              );
              return;
            }

            // Đổ dữ liệu tra cứu lên lưới hiển thị:
            if (this.tracuu_ccbs.length > 0) {
              if (
                this.tracuu_ccbs[0]["CUSTID"] ||
                this.tracuu_ccbs[0]["CUSTID"].toString() != ""
              ) {
                this.check_click = 1;
                this.tab_dkVCC.cboLoaiNhom.value = this.tracuu_ccbs[0]["GOICUOC"];
                this.tab_dkVCC.txtGoicuocvcc.value = this.tracuu_ccbs[0]["PACKAGE_ID"];
                this.tab_dkVCC.cboLoaiKT.value = this.tracuu_ccbs[0]["LOAI"]; // check thêm
                this.tab_dkVCC.txtUser.value = this.tracuu_ccbs[0]["USERNUMBER"];
                this.tab_dkVCC.txtMaylevcc.value = this.tracuu_ccbs[0]["EXTNUMBER"];
                this.tab_dkVCC.cboGiamgia.value = this.tracuu_ccbs[0]["CUOC_KHOITAO"];
                this.tab_dkVCC.cboVCCchinh.value = this.tracuu_ccbs[0]["RATING_CUOCTB"];
                this.tab_dkVCC.cboVCCle.value = this.tracuu_ccbs[0]["RATING_MAYLE"];
                this.tab_dkVCC.cboUser.value = this.tracuu_ccbs[0]["RATING_USER"];
                this.tab_dkVCC.cboPhuongthuc.value = this.tracuu_ccbs[0]["HINHTHUCTT"];
                this.tab_dkVCC.cbokieuthongbao.value =
                  this.tracuu_ccbs[0]["KIEUBAOCUOC"];
                this.tab_dkVCC.cboLoaihinhDN.value = this.tracuu_ccbs[0]["LOAIKH_ID"];
                this.tab_dkVCC.cboNganhang.value = this.tracuu_ccbs[0]["NGANHANG_ID"];

                for (var i = 0; i < this.dtNguoi_GT; i++) {
                  if (
                    this.dtNguoi_GT[i]["MA_NGUOI"].toString() ==
                    this.tracuu_ccbs[0]["MA_NGUOI_GT"].toString()
                  ) {
                    this.tab_dkVCC.cboGioithieu.value =
                      this.dtNguoi_GT[i]["MA_NGUOI"].toString();
                    break;
                  }
                }
                // Trang thai nut
                if (
                  this.tracuu_ccbs[0]["VCC_STATUS"].toString() == "1" ||
                  this.tracuu_ccbs[0]["VCC_STATUS"].toString() == "4"
                ) {
                  // hoạt động bt và cập nhật gói
                  this.button.btnDangky.Text = "Cập nhật";
                  this.button.btnDangky.Enabled = true;
                  this.button.btnHuy.Enabled = true;
                  this.button.btnKichhoatlai.Enabled = false;
                  this.button.btnTamngung.Enabled = true;
                }
                if (this.tracuu_ccbs[0]["VCC_STATUS"].toString() == "5") {
                  // tạm ngưng
                  this.button.btnDangky.Text = "Cập nhật";
                  this.button.btnDangky.Enabled = false;
                  this.button.btnHuy.Enabled = true;
                  this.button.btnKichhoatlai.Enabled = true;
                  this.button.btnTamngung.Enabled = false;
                }
              } else {
                this.button.btnDangky.Text = "Đăng ký";
                this.button.btnDangky.Enabled = true;
                if (this.tab_dkVCC.cboLoaiNhom.value.toString().trim() != "VCC_DEMO")
                  this.button.bntLayGoi.Enabled = true;
              }
              this.tab_dkVCC.txtNganhnghe.value = this.tracuu_ccbs[0]["NGHENGHIEP"];
              this.tab_dkVCC.dtpTungay.value = this.tracuu_ccbs[0]["NGAYCAP_DKKD"]
                ? moment(this.tracuu_ccbs[0]["NGAYCAP_DKKD"]).format("DD/MM/YYYY")
                : "";
              this.tab_dkVCC.txtFax.value = this.tracuu_ccbs[0]["FAX"];
              this.tab_dkVCC.txtDiachi.value = this.tracuu_ccbs[0]["DIA_CHI"];
              this.tab_dkVCC.txtWebsite.value = this.tracuu_ccbs[0]["WEBSITE"];
              this.tab_dkVCC.txtTencoquan.value = this.tracuu_ccbs[0]["TENCOQUAN"];
              this.tab_dkVCC.txtDiachiTK.value = this.tracuu_ccbs[0]["DIA_CHI_LD"];
              this.tab_dkVCC.txtDiachiGD.value = this.tracuu_ccbs[0]["DIACHITT"];
              this.tab_dkVCC.txtMst.value = this.tracuu_ccbs[0]["MA_SO_THUE"];
              this.tab_dkVCC.txtGPKD.value = this.tracuu_ccbs[0]["GIAY_DKKD"];
              this.tab_dkVCC.txtNoicap.value = this.tracuu_ccbs[0]["NOICAP"];
              this.tab_dkVCC.txtEmail.value = this.tracuu_ccbs[0]["EMAIL"];
              this.tab_dkVCC.txtNguoidaidien.value =
                this.tracuu_ccbs[0]["NGUOIDAIDIEN"];
              this.tab_dkVCC.txtDienthoai.value = this.tracuu_ccbs[0]["SDT_LIENHE"];
              this.tab_dkVCC.txtTaikhoanNH.value = this.tracuu_ccbs[0]["TAI_KHOAN"];
              this.tab_dkVCC.txtGhichu.value = "";
              // Thong tin quản trị
              this.formQuanTri.textQuantri.value = this.tracuu_ccbs[0]["AMDMIN_TEN"];
              this.formQuanTri.txtEmailQT.value = this.tracuu_ccbs[0]["AMDMIN_EMAIL"];
              this.formQuanTri.txtDienthoaiQT.value = this.tracuu_ccbs[0]["AMDMIN_SDT"];
              this.formQuanTri.txtChucdanhQT.value =
                this.tracuu_ccbs[0]["AMDMIN_CHUCDANH"];
            } else {
              this.button.btnDangky.Text = "Đăng ký";
              this.SetButton(0);
              this.ShowError("Thuê bao không đủ điều kiện đăng ký dịch vụ VCC!");
              return;
            }
          }
          this.ds = ds
        } else {
          this.button.btnDangky.Text = "Đăng ký";
          this.SetButton(0);
          this.ShowError(
            "Không tìm thấy thông tin danh bạ thuê bao 84" +
              this.tab_dkVCC.txtSoMay.value.toString().trim()
          );
          return;
        }
      } catch (e) {}
    },
    acceptThueBaoCungMa: function (tb) {
      if (
        Number(tb["trangthaitb_id"].toString()) == TrangThaiTB.THANHLY ||
        Number(tb["trangthaitb_id"].toString()) == TrangThaiTB.THANHLY_NO ||
        Number(tb["trangthaitb_id"].toString()) == TrangThaiTB.DOISO
      ) {
        this.ShowError(
          "Thuê bao có mã: " +
            tb["ma_tb"].toString() +
            " đang ở trạng thái: " +
            tb["trangthai_tb"].toString() +
            "\nBạn không thể thực hiện cập nhật TT thuê bao này"
        );
        return;
      }

      // Đổ dữ liệu tra cứu lên lưới hiển thị:
      if (this.tracuu_ccbs && this.tracuu_ccbs.length > 0) {
        if (
          this.tracuu_ccbs[0]["CUSTID"] ||
          this.tracuu_ccbs[0]["CUSTID"].toString() != ""
        ) {
          this.check_click = 1;
          this.tab_dkVCC.cboLoaiNhom.value = this.tracuu_ccbs[0]["GOICUOC"];
          this.tab_dkVCC.txtGoicuocvcc.value = this.tracuu_ccbs[0]["PACKAGE_ID"];
          this.tab_dkVCC.cboLoaiKT.value = this.tracuu_ccbs[0]["LOAI"]; // check thêm
          this.tab_dkVCC.txtUser.value = this.tracuu_ccbs[0]["USERNUMBER"];
          this.tab_dkVCC.txtMaylevcc.value = this.tracuu_ccbs[0]["EXTNUMBER"];
          this.tab_dkVCC.cboGiamgia.value = this.tracuu_ccbs[0]["CUOC_KHOITAO"];
          this.tab_dkVCC.cboVCCchinh.value = this.tracuu_ccbs[0]["RATING_CUOCTB"];
          this.tab_dkVCC.cboVCCle.value = this.tracuu_ccbs[0]["RATING_MAYLE"];
          this.tab_dkVCC.cboUser.value = this.tracuu_ccbs[0]["RATING_USER"];
          this.tab_dkVCC.cboPhuongthuc.value = this.tracuu_ccbs[0]["HINHTHUCTT"];
          this.tab_dkVCC.cbokieuthongbao.value = this.tracuu_ccbs[0]["KIEUBAOCUOC"];
          this.tab_dkVCC.cboLoaihinhDN.value = this.tracuu_ccbs[0]["LOAIKH_ID"];
          this.tab_dkVCC.cboNganhang.value = this.tracuu_ccbs[0]["NGANHANG_ID"];

          if (this.tracuu_ccbs[0]["MA_NGUOI_GT"]) {
            for (var i = 0; i < this.dtNguoi_GT; i++) {
              if (
                this.dtNguoi_GT[i]["MA_NGUOI"].toString() ==
                this.tracuu_ccbs[0]["MA_NGUOI_GT"].toString()
              ) {
                this.tab_dkVCC.cboGioithieu.value =
                  this.dtNguoi_GT[i]["MA_NGUOI"].toString();
                break;
              }
            }
          } else {
            this.tab_dkVCC.cboGioithieu.value = ""
          }
          
          // Trang thai nut
          if (
            this.tracuu_ccbs[0]["VCC_STATUS"].toString() == "1" ||
            this.tracuu_ccbs[0]["VCC_STATUS"].toString() == "4"
          ) {
            // hoạt động bt và cập nhật gói
            this.button.btnDangky.Text = "Cập nhật";
            this.button.btnDangky.Enabled = true;
            this.button.btnHuy.Enabled = true;
            this.button.btnKichhoatlai.Enabled = false;
            this.button.btnTamngung.Enabled = true;
          }
          if (this.tracuu_ccbs[0]["VCC_STATUS"].toString() == "5") {
            // tạm ngưng
            this.button.btnDangky.Text = "Cập nhật";
            this.button.btnDangky.Enabled = false;
            this.button.btnHuy.Enabled = true;
            this.button.btnKichhoatlai.Enabled = true;
            this.button.btnTamngung.Enabled = false;
          }
        } else {
          this.button.btnDangky.Text = "Đăng ký";
          this.button.btnDangky.Enabled = true;
          if (this.tab_dkVCC.cboLoaiNhom.value.toString().trim() != "VCC_DEMO")
            this.button.bntLayGoi.Enabled = true;
        }
        this.tab_dkVCC.txtNganhnghe.value = this.tracuu_ccbs[0]["NGHENGHIEP"];
        this.tab_dkVCC.dtpTungay.value = this.tracuu_ccbs[0]["NGAYCAP_DKKD"]
          ? moment(this.tracuu_ccbs[0]["NGAYCAP_DKKD"]).format("DD/MM/YYYY")
          : "";
        this.tab_dkVCC.txtFax.value = this.tracuu_ccbs[0]["FAX"];
        this.tab_dkVCC.txtDiachi.value = this.tracuu_ccbs[0]["DIA_CHI"];
        this.tab_dkVCC.txtWebsite.value = this.tracuu_ccbs[0]["WEBSITE"];
        this.tab_dkVCC.txtTencoquan.value = this.tracuu_ccbs[0]["TENCOQUAN"];
        this.tab_dkVCC.txtDiachiTK.value = this.tracuu_ccbs[0]["DIA_CHI_LD"];
        this.tab_dkVCC.txtDiachiGD.value = this.tracuu_ccbs[0]["DIACHITT"];
        this.tab_dkVCC.txtMst.value = this.tracuu_ccbs[0]["MA_SO_THUE"];
        this.tab_dkVCC.txtGPKD.value = this.tracuu_ccbs[0]["GIAY_DKKD"];
        this.tab_dkVCC.txtNoicap.value = this.tracuu_ccbs[0]["NOICAP"];
        this.tab_dkVCC.txtEmail.value = this.tracuu_ccbs[0]["EMAIL"];
        this.tab_dkVCC.txtNguoidaidien.value = this.tracuu_ccbs[0]["NGUOIDAIDIEN"];
        this.tab_dkVCC.txtDienthoai.value = this.tracuu_ccbs[0]["SDT_LIENHE"];
        this.tab_dkVCC.txtTaikhoanNH.value = this.tracuu_ccbs[0]["TAI_KHOAN"];
        this.tab_dkVCC.txtGhichu.value = "";
        // Thong tin quản trị
        this.formQuanTri.textQuantri.value = this.tracuu_ccbs[0]["AMDMIN_TEN"];
        this.formQuanTri.txtEmailQT.value = this.tracuu_ccbs[0]["AMDMIN_EMAIL"];
        this.formQuanTri.txtDienthoaiQT.value = this.tracuu_ccbs[0]["AMDMIN_SDT"];
        this.formQuanTri.txtChucdanhQT.value = this.tracuu_ccbs[0]["AMDMIN_CHUCDANH"];
      } else {
        this.button.btnDangky.Text = "Đăng ký";
        this.SetButton(0);
        this.ShowError("Thuê bao không đủ điều kiện đăng ký dịch vụ VCC!");
        return;
      }
    },
    onChange_cboLoaiNhom: function (value) {
      if (value.toString().trim() == "VCC_DEMO") {
        this.tab_dkVCC.txtUser.value = "1";
        this.Load_trangthai(false);
        this.button.bntLayGoi.Enabled = false;
      } else if (
        value.toString().trim() == "VCC_CALL" ||
        value.toString().trim() == "VCC_SIPCONNECT"
      ) {
        this.Load_trangthai(true);
        this.tab_dkVCC.txtUser.value = "0";
        this.tab_dkVCC.txtUser.Enabled = false;
        if (this.check_click != 1) this.button.bntLayGoi.Enabled = true;
      } //
      else {
        this.tab_dkVCC.txtUser.value = "0";
        this.Load_trangthai(true);
        if (this.check_click != 1) this.button.bntLayGoi.Enabled = true;
      }
    },
    onChange_cboDanhmucbs: function (value) {
      if (value && value.toString().trim() == "5") {
        this.Loadthuoctinh_3();
      } else {
        this.tab_qldv.txtTienVat.value = "";
        this.tab_qldv.txtVNPT.value = "50";
        this.tab_qldv.txtDoitac.value = "50";
        this.tab_qldv.txtKhoiLuong.value = "";

        this.tab_qldv.cboKycuoc.Enabled = true;
        this.tab_qldv.txtTienVat.Enabled = true;
        this.tab_qldv.txtVNPT.Enabled = true;
        this.tab_qldv.txtDoitac.Enabled = true;        
        this.tab_qldv.txtKhoiLuong.Enabled = false;
      }
    },
    changeNgayBD: function (value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.tab_dkVCC.dtpTungay.value = "01/01/2000";
      } else {
        this.tab_dkVCC.dtpTungay.value = value_;
      }
    },
    selectedgrdVCC_click: function (item) {
      // console.log(item);
    },
    selectedGoiNoiMang_click: function (item) {
      if (item && item.length > 0) {        
        let set = new Set();
        for(let i = 0; i < item.length; i++)
          set.add(item[i]['ID'])
        for(let i = 0; i < this.grdGoiNoiMang2.list.length; i++) {
          if(set.has(this.grdGoiNoiMang2.list[i]['ID']))
            this.grdGoiNoiMang2.list[i]['ISCHECK'] = true
          else this.grdGoiNoiMang2.list[i]['ISCHECK'] = false;
        }
      } else {
        for(let i = 0; i < this.grdGoiNoiMang2.list.length; i++) {
          this.grdGoiNoiMang2.list[i]['ISCHECK'] = false;
        }
      }
    },
    selectedGoiNgoaiMang_click: function (item) {
      if (item && item.length > 0) {        
        let set = new Set();
        for(let i = 0; i < item.length; i++)
          set.add(item[i]['ID'])
        for(let i = 0; i < this.grdGoiNgoaiMang2.list.length; i++) {
          if(set.has(this.grdGoiNgoaiMang2.list[i]['ID']))
            this.grdGoiNgoaiMang2.list[i]['ISCHECK'] = true
          else this.grdGoiNgoaiMang2.list[i]['ISCHECK'] = false;
        }
      } else {
        for(let i = 0; i < this.grdGoiNgoaiMang2.list.length; i++) {
          this.grdGoiNgoaiMang2.list[i]['ISCHECK'] = false;
        }
      }
    },
    grvDVBS_SelectionChanged: function (item) {
      if (!this.load_v3 && item && item.length > 0) {
        this.tab_qldv.cboDanhmucbs.value = item[0]["DANHMUC"].toString();
        this.tab_qldv.txtTienVat.value = item[0]["GIATIEN"].toString();
        this.tab_qldv.txtKhoiLuong.value = item[0]["KHOILUONG"].toString();
        this.tab_qldv.txtVNPT.value = item[0]["TYLEVNP"].toString();
        this.tab_qldv.txtDoitac.value = item[0]["TYLEDOITAC"].toString();
        this.tab_qldv.cboKycuoc.value = item[0]["KYCUOC2"].toString();
        //   grvDVBS.ClearSelection();
        this.button.btnCapnhat3.Enabled = true;
        this.button.btnHuy3.Enabled = true;
        this.button.btnThemmoi3.Enabled = false;
      }
    },
    btnNhapMoi_Click: function () {
      this.SetButton(0);
    },
    btnDangky_Click() {
      try {
        var tungay = "";
        var nguoi_gt = "";
        // Combo check du lieu
        if (
          !this.tab_dkVCC.txtNganhnghe.value ||
          this.tab_dkVCC.txtNganhnghe.value.toString().trim() == ""
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtNganhnghe.focus();
          return;
        }

        if (
          !this.tab_dkVCC.txtTencoquan.value ||
          this.tab_dkVCC.txtTencoquan.value.toString().trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtTencoquan.focus();
          return;
        }

        if (         
          !this.tab_dkVCC.txtDiachiTK.value ||
          this.tab_dkVCC.txtDiachiTK.value.toString().trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtDiachiTK.focus();
          return;
        }

        if (
          !this.tab_dkVCC.txtDiachiGD.value ||
          this.tab_dkVCC.txtDiachiGD.value.toString().trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtDiachiGD.focus();
          return;
        }

        if (          
          !this.tab_dkVCC.txtGPKD.value ||
          this.tab_dkVCC.txtGPKD.value.toString().trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtGPKD.focus();
          return;
        }

        if (         
          !this.tab_dkVCC.txtNoicap.value ||
          this.tab_dkVCC.txtNoicap.value.toString().trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtNoicap.focus();
          return;
        }

        if (         
          !this.tab_dkVCC.txtEmail.value ||
          this.tab_dkVCC.txtEmail.value.toString().trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtEmail.focus();
          return;
        }

        if (          
          !this.tab_dkVCC.txtNguoidaidien.value ||
          this.tab_dkVCC.txtNguoidaidien.value.toString().trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtNguoidaidien.focus();
          return;
        }

        if (          
          !this.tab_dkVCC.txtMst.value ||
          this.tab_dkVCC.txtMst.value.toString().trim() == ""
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin các trường có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtMst.focus();
          return;
        }

        if (!this.tab_dkVCC.dtpTungay.value || this.tab_dkVCC.dtpTungay.value.toString().trim() == "") {
          this.ShowError("Chưa nhập ngày cấp GPKD của doanh nghiệp!");
          return;
        }

        if (!this.tab_dkVCC.cboPhuongthuc.value || this.tab_dkVCC.cboPhuongthuc.value.toString() == "0") {
          this.ShowError("Hãy chọn phương thức thanh toán!");
          return;
        }

        if (!this.tab_dkVCC.cboLoaihinhDN.value || this.tab_dkVCC.cboLoaihinhDN.value.toString() == "0") {
          this.ShowError("Hãy chọn loại hình doanh nghiệp!");
          return;
        }

        if (!this.tab_dkVCC.cbokieuthongbao.value || this.tab_dkVCC.cbokieuthongbao.value.toString() == "0") {
          this.ShowError("Hãy chọn kiểu thông báo cước!");
          return;
        }

        if (
          !this.formQuanTri.textQuantri.value ||
          this.formQuanTri.textQuantri.value.trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin người quản trị có dấu (*) theo yêu cầu!"
          );
          this.$refs.textQuantri.focus();
          return;
        }

        if (          
          !this.formQuanTri.txtEmailQT.value ||
          this.formQuanTri.txtEmailQT.value.trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin người quản trị có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtEmailQT.focus();
          return;
        }

        if (          
          !this.formQuanTri.txtDienthoaiQT.value ||
          this.formQuanTri.txtDienthoaiQT.value.trim() == "" 
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin người quản trị có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtDienthoaiQT.focus();
          return;
        }

        if (          
          !this.formQuanTri.txtChucdanhQT.value ||
          this.formQuanTri.txtChucdanhQT.value.trim() == ""
        ) {
          this.ShowError(
            "Chưa nhập đủ thông tin người quản trị có dấu (*) theo yêu cầu!"
          );
          this.$refs.txtChucdanhQT.focus();
          return;
        }
      
        if (
          this.tab_dkVCC.cboLoaiNhom.value.toString().trim() == "VCC_CALL" &&
          Number(this.tab_dkVCC.txtMaylevcc.value) == 0
        ) {
          this.ShowError("Số lượng máy lẻ không hợp lệ!");
          return;
        }

        if (
          this.tab_dkVCC.cboLoaiNhom.value.toString().trim() == "VCC_SIPCONNECT" &&
          Number(this.tab_dkVCC.txtMaylevcc.value) == 0
        ) {
          this.ShowError("Số lượng máy lẻ không hợp lệ!");
          return;
        }

        if (
          this.tab_dkVCC.cboLoaiNhom.value.toString().trim() == "VCC_CALLCENTER" &&
          Number(this.tab_dkVCC.txtUser.value) == 0
        ) {
          this.ShowError("Số lượng user không hợp lệ!");
          this.$refs.txtUser.focus();
          return;
        }

        if (
          this.tab_dkVCC.cboLoaiNhom.value.toString().trim() == "VCC_CONTACT_CENTER" &&
          Number(this.tab_dkVCC.txtUser.value) == 0
        ) {
          this.ShowError("Số lượng user không hợp lệ!");
          this.$refs.txtUser.focus();
          return;
        }

        if (
          this.tab_dkVCC.dtpTungay.value &&
          this.tab_dkVCC.dtpTungay.value.toString().length > 9
        ) {
          tungay = this.tab_dkVCC.dtpTungay.value.toString().substring(0, 10);
        }

        if (this.tab_dkVCC.cboLoaiNhom.value.toString().trim() == "VCC_DEMO") {
          this.tab_dkVCC.txtGoicuocvcc.value = "";
          this.gg_goinoimang = "100";
          this.gg_goingoaimang = "100";
        }

        if (!this.tab_dkVCC.cboGioithieu.value || this.tab_dkVCC.cboGioithieu.value.toString().trim() == "0") {
          nguoi_gt = "";
        } else {
          nguoi_gt = this.tab_dkVCC.cboGioithieu.value.toString().trim();
        }

        // Đăng ký thành công thì tạo dữ liệu.
        this.$bvModal
          .msgBoxConfirm(
            "Bạn thật sự muốn " +
              this.button.btnDangky.Text +
              " dịch vụ VCC của thuê bao 84" +
              this.tab_dkVCC.txtSoMay.value +
              "?",
            {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              await this.ts_vcc_dangky_dichvu(tungay, nguoi_gt);              
              if (this.ds && this.ds.length > 0) {
                await this.TaoDuLieu(true, LoaiHopDong.DD_DKVCC);
                await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_DKVCC);
              //     bangts.ExeSQL(
              //       "update " +
              //         DatabaseConstants.DB2 +
              //         ".giaophieu set ttph_id = 4 where hdtb_id =" +
              //         hdtb_id
              //     );
              }
              this.SetButton(0);
              this.loading(false);
            }
          })
          .catch((e) => {
            this.ShowError(e);
            this.loading(false);
          });
      } catch (ex) {
        this.ShowError("Có lỗi: " + ex.toString());
        this.loading(false);
      }
    },
    btnHuy_Click()
    {
      try {
        this.$bvModal
          .msgBoxConfirm("Bạn thật sự hủy dịch vụ VCC của thuê bao 84" + this.tab_dkVCC.txtSoMay.value + "?",
            {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.loading(true);
              await this.ts_vcc_huy_dichvu("84" + this.tab_dkVCC.txtSoMay.value, this.tt_nd.USER_NEO, this.tt_nd.ip_cn, this.tt_nd.ma_tinh);
              
              if (this.ds && this.ds.length > 0) {
                await this.TaoDuLieu(true, LoaiHopDong.DD_HUYVCC);
                await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_HUYVCC);
                // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
              }
              this.SetButton(0);
              this.loading(false);
            }
          })
          .catch((e) => {
            this.ShowError(e);
            this.loading(false);
          });       
      } catch (ex) {
        console.log(ex)
        this.loading(false);
      }
    },
    btnTamngung_Click () {
      try {               
        this.$bvModal
          .msgBoxConfirm("Bạn thật sự muốn tạm dừng dịch vụ VCC của thuê bao 84" + this.tab_dkVCC.txtSoMay.value + "?",
            {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.loading(true);
              await this.ts_vcc_tamngung_dichvu("84" + this.tab_dkVCC.txtSoMay.value, this.tt_nd.USER_NEO, this.tt_nd.ip_cn, this.tt_nd.ma_tinh);
              
              if (this.ds && this.ds.length > 0) {
                await this.TaoDuLieu(true, LoaiHopDong.DD_TAMDUNGVCC);
                await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_TAMDUNGVCC);
                // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
              }
              this.SetButton(0);
              this.loading(false);
            }
          })
          .catch((e) => {
            this.ShowError(e);
            this.loading(false);
          });  
      }
      catch (ex)
      {
        this.ShowError(ex);
        this.loading(false);
      }
    },
    btnKichhoatlai_Click () {
      try {        
        this.$bvModal
          .msgBoxConfirm("Bạn thật sự muốn kích hoạt lại dịch vụ VCC của thuê bao 84" + this.tab_dkVCC.txtSoMay.value + "?",
            {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.loading(true);
              await this.ts_vcc_kichhoat_dichvu("84" + this.tab_dkVCC.txtSoMay.value, this.tt_nd.USER_NEO, this.tt_nd.ip_cn, this.tt_nd.ma_tinh);
              
              if (this.ds && this.ds.length > 0) {
                await this.TaoDuLieu(true, LoaiHopDong.DD_KICHVCC);
                await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_KICHVCC);
                // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
              }
              this.SetButton(0);
              this.loading(false);
            }
          })
          .catch((e) => {
            this.ShowError(e);
            this.loading(false);
          });  
      } catch (ex) {
        this.ShowError(ex);
        this.loading(false);
      }
    },
    bntLayGoi_Click () {
      this.dulieu = {
        goicuoc_vcc: this.tab_dkVCC.txtGoicuocvcc.value,
        giamgia_noimang: this.gg_goinoimang,
        giamgia_ngoaimang: this.gg_goingoaimang
      }
      this.$refs.frmGoiVCC.showModal();
    },
    frmGoiVCC_xacnhan(data) {
      this.tab_dkVCC.txtGoicuocvcc.value = data.goicuoc_vcc
      this.gg_goinoimang = data.giamgia_noimang
      this.gg_goingoaimang = data.giamgia_ngoaimang
    },
    btnHuyGiao_Click(item) {
      try {
        // grvGoidadk.CloseEditor();
        // var check_huy = "";
        var goi_huy = item.PACKAGE_ID;
        var goi_huy_id = item.ID;

        if (this.grdGoidadk.list && this.grdGoidadk.list.length > 0) {
        //   for (var i = 0; i < this.grdGoidadk.list.length; i++) {
        //     var is_check = this.grdGoidadk.list[i]["ISCHECK"];
        //     if (is_check) {
        //       check_huy = "1";
        //       goi_huy = this.grdGoidadk.list[i]["PACKAGE_ID"].toString();
        //       goi_huy_id = this.grdGoidadk.list[i]["ID"].toString();
        //       break;
        //     }
        //   }
        //   if (check_huy == "") {
        //     this.ShowError("Bạn chưa chọn gói để hủy!");
        //     return;
        //   }

          this.$bvModal
            .msgBoxConfirm("Bạn có muốn hủy gói " + goi_huy + " của thuê bao 84" + this.tab_cnVCC.txtSoMay2.value + " không?",
              {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              }
            )
            .then(async (v) => {
              if (v) {
                this.loading(true);
                await this.ts_vcc_huy_goi(this.tt_nd.ma_tinh, "84" + this.tab_cnVCC.txtSoMay2.value, goi_huy_id, goi_huy, this.tt_nd.USER_NEO);

                if (this.ds2 && this.ds2.length > 0)
                {
                  await this.TaoDuLieu2(true, LoaiHopDong.DD_XOAGOI);
                  await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_XOAGOI);
                  // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
                }

                // Load lại sau khi hủy
                this.grdGoidadk.list = [];
                var tracuu_goi = await this.ts_vcc_ds_goi_tb("84" + this.tab_cnVCC.txtSoMay2.value);
                if (tracuu_goi && tracuu_goi.length > 0)
                {
                  this.grdGoidadk.list = tracuu_goi;
                }
                this.loading(false);
              } 
            })
            .catch((e) => {
              this.ShowError(e);
              this.loading(false);
            });               
        }
      } catch (ex) {
        this.ShowError(ex);
      }
    },
    btnCapnhatgoi_Click: async function () {
      try {
        //Kiểm tra điều kiện
        // grvGoiNoiMang2.CloseEditor();
        // grvGoiNgoaiMang2.CloseEditor();
        var check_dk1 = "";
        var check_dk2 = "";
        var check_giamnoimg = "";
        var check_giamngoaimg = "";
        var goi_noi2_id = "";
        var goi_ngoai2_id = "";
        var goi2_id = "";

        var tracuu_tb = await this.ts_vcc_lay_tttb("84" + this.tab_cnVCC.txtSoMay2.value);
        if (!tracuu_tb || !tracuu_tb[0]["CUSTID"]) {
          this.ShowError("Thuê bao chưa đăng ký gói VCC!");
          return;
        }

        for (var i = 0; i < this.grdGoiNoiMang2.list.length; i++) {
          var is_check1 = this.grdGoiNoiMang2.list[i]["ISCHECK"];
          if (is_check1) {
            check_dk1 = "1";
            var noi2_id = this.grdGoiNoiMang2.list[i]["ID"];
            goi_noi2_id = goi_noi2_id + noi2_id + ",";            
          }
        }        

        for (var i = 0; i < this.grdGoiNgoaiMang2.list.length; i++) {
          var is_check2 = this.grdGoiNgoaiMang2.list[i]["ISCHECK"];
          if (is_check2) {
            check_dk2 = "1";
            var ngoai2_id = this.grdGoiNgoaiMang2.list[i]["ID"];
            goi_ngoai2_id = goi_ngoai2_id + ngoai2_id + ",";
          }
        }

        if (check_dk1 == "1" && this.tab_cnVCC.cboNoiMang2.value.toString() == "0") {
          this.ShowError("Bạn chưa chọn giảm giá gói nội mạng!");
          return;
        }
        if (check_dk2 == "1" && this.tab_cnVCC.cboNgoaiMang2.value.toString() == "0") {
          this.ShowError("Bạn chưa chọn giảm giá gói ngoại mạng!");
          return;
        }

        if (check_dk1 == "" && check_dk2 == "") {
          this.ShowError("Hãy chọn gói để thực hiện nghiệp vụ!");
          return;
        }
        if (check_dk1 == "1") {
          check_giamnoimg = this.tab_cnVCC.cboNoiMang2.value.toString();
        } else {
          check_giamnoimg = "100";
        }

        if (check_dk2 == "1") {
          check_giamngoaimg = this.tab_cnVCC.cboNgoaiMang2.value.toString();
        } else {
          check_giamngoaimg = "100";
        }

        // Thực hiện gọi hàm
        goi2_id = goi_noi2_id + goi_ngoai2_id;
        goi2_id = goi2_id.slice(0, -1);

        this.$bvModal
          .msgBoxConfirm("Bạn có muốn cập nhật gói VCC cho thuê bao 84" + this.tab_cnVCC.txtSoMay2.value + " không?",
            {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.loading(true);
              var item = {
                ds_goi: goi2_id,        
                so_tb: "84" + this.tab_cnVCC.txtSoMay2.value,
                check_giamnoimg: check_giamnoimg,
                check_giamngoaimg: check_giamngoaimg,
              };      
              await this.ts_vcc_capnhat_goi(item);
              
              if (this.ds2 && this.ds2.length > 0) {
                await this.TaoDuLieu2(true, LoaiHopDong.DD_CAPNHATGOI);
                await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_CAPNHATGOI);
                // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
              }

              // Load lại sau khi cập nhật
              this.grdGoidadk.list = [];
              var tracuu_goi = await this.ts_vcc_ds_goi_tb("84" + this.tab_cnVCC.txtSoMay2.value);
              if (tracuu_goi && tracuu_goi.length > 0) {
                this.grdGoidadk.list = tracuu_goi;
              }

              this.tab_cnVCC.cboNoiMang2.value = this.dtnoimang2.length > 0 ? this.dtnoimang2[0].RATING_ : 0;
              this.tab_cnVCC.cboNgoaiMang2.value = this.dtngoaimang2.length > 0 ? this.dtngoaimang2[0].RATING_ : 0;

              for (var i = 0; i < this.grdGoiNoiMang2.list.length; i++) {
                this.grdGoiNoiMang2.list[i]["ISCHECK"] = false;
              }

              for (var i = 0; i < this.grdGoiNgoaiMang2.list.length; i++) {
                this.grdGoiNgoaiMang2.list[i]["ISCHECK"] = false;
              }

              this.button.btnCapnhatgoi.Enabled = false;
              this.loading(false);
            } 
          })
          .catch((e) => {
            this.ShowError(e);
            this.loading(false);
          });         
      } catch (ex) {
        this.ShowError(ex);
      }
    },
    btnThemmoi3_Click: async function () {
      try {
        if (!this.kiemtra_v3()) return;
        var tracuu_v3 = await this.ts_vcc_lay_tttb("84" + this.tab_qldv.txtSoMay3.value);
        if (tracuu_v3 && tracuu_v3.length > 0) {
          this.$bvModal
            .msgBoxConfirm("Bạn có muốn đăng ký danh mục bổ sung cho thuê bao 84" + this.tab_qldv.txtSoMay3.value + " không?",
              {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              }
            )
            .then(async (v) => {
              if (v) {
                this.loading(true);
                var themmoi = 1;
                var input = {
                  "so_tb": "84" + this.tab_qldv.txtSoMay3.value,
                  "ghi_chu": this.tab_qldv.textGhichu3.value,
                  "gia_tien": this.tab_qldv.txtTienVat.value,
                  "khoi_luong": this.tab_qldv.txtKhoiLuong.value,
                  "ky_cuoc": this.tab_qldv.cboKycuoc.value,
                  "ma_danhhmuc": this.tab_qldv.cboDanhmucbs.value,                  
                  "tyle_doitac": this.tab_qldv.txtDoitac.value,
                  "tyle_vnp": this.tab_qldv.txtVNPT.value
                }
                await this.ts_vcc_dichvubs_dangky(input, themmoi);

                if (this.ds3 && this.ds3.length > 0) {
                  await this.TaoDuLieu3(true, LoaiHopDong.DD_CAPNHATDATA);
                  await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_CAPNHATDATA);
                  // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
                }
                this.SetButton(0);
                this.loading(false);
              } 
            })
            .catch((e) => {
              this.ShowError(e);
              this.loading(false);
            });               
        } else {
          this.ShowError("Thuê bao chưa đăng ký dịch vụ VCC!");
          return;
        }       
      } catch (ex) {
        this.ShowError(ex);
      }
    },
    btnCapnhat3_Click() {
      try {
        if (!this.kiemtra_v3()) return;

        if (("84" + this.tab_qldv.txtSoMay3.value) != this.grdDVBS.value["SO_TB"]) {
          this.ShowError("Số thuê bao không hợp lệ, hãy kiểm tra lại!");
          return;
        }

        if (this.tab_qldv.cboDanhmucbs.value.toString() == this.grdDVBS.value["DANHMUC"].toString() &&
          this.tab_qldv.txtTienVat.value.toString() == this.grdDVBS.value["GIATIEN"].toString() &&
          this.tab_qldv.txtKhoiLuong.value.toString() == this.grdDVBS.value["KHOILUONG"].toString() &&
          this.tab_qldv.txtVNPT.value.toString() == this.grdDVBS.value["TYLEVNP"].toString() &&
          this.tab_qldv.txtDoitac.value.toString() == this.grdDVBS.value["TYLEDOITAC"].toString() &&
          this.tab_qldv.cboKycuoc.value.toString() == this.grdDVBS.value["KYCUOC2"].toString())
        {
          this.ShowError("Không có sự thay đổi nào, bạn không cần cập nhật!");
          return;
        }

        if (this.grdDVBS.list.length > 0) {
          this.$bvModal
            .msgBoxConfirm("Bạn có muốn cập nhật danh mục bổ sung cho thuê bao 84" + this.tab_qldv.txtSoMay3.value + " không?",
              {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              }
            )
            .then(async (v) => {
              if (v) {
                this.loading(true);
                var themmoi = 0
                var input = {
                  "so_tb": "84" + this.tab_qldv.txtSoMay3.value,
                  "ghi_chu": this.tab_qldv.textGhichu3.value,
                  "gia_tien": this.tab_qldv.txtTienVat.value,
                  "khoi_luong": this.tab_qldv.txtKhoiLuong.value,
                  "ky_cuoc": this.tab_qldv.cboKycuoc.value,
                  "ma_danhhmuc": this.tab_qldv.cboDanhmucbs.value,                  
                  "tyle_doitac": this.tab_qldv.txtDoitac.value,
                  "tyle_vnp": this.tab_qldv.txtVNPT.value
                }
                await this.ts_vcc_dichvubs_dangky(input, themmoi);

                if (this.ds3 && this.ds3.length > 0) {
                  await this.TaoDuLieu3(true, LoaiHopDong.DD_CAPNHATDATA);
                  await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_CAPNHATDATA);
                  // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
                }
                this.SetButton(0);
                this.loading(false);
              } 
            })
            .catch((e) => {
              this.ShowError(e);
              this.loading(false);
            });           
        } else {
          this.ShowError("Thuê bao chưa đăng ký dịch vụ bổ sung, bạn không thể cập nhật!");
          return;
        }        
      } catch (ex) {
        this.ShowError(ex);
      }
    },
    btnHuy3_Click() {
      try {
        if (!this.kiemtra_v3()) return;

        if (("84" + this.tab_qldv.txtSoMay3.value) != this.grdDVBS.value["SO_TB"].toString()) {
          this.ShowError("Số thuê bao không hợp lệ, hãy kiểm tra lại!");
          return;
        }

        if (this.tab_qldv.cboDanhmucbs.value.toString() != this.grdDVBS.value["DANHMUC"].toString() ||
          this.tab_qldv.txtTienVat.value.toString() != this.grdDVBS.value["GIATIEN"].toString() ||
          this.tab_qldv.txtKhoiLuong.value.toString() != this.grdDVBS.value["KHOILUONG"].toString() ||
          this.tab_qldv.txtVNPT.value.toString() != this.grdDVBS.value["TYLEVNP"].toString() ||
          this.tab_qldv.txtDoitac.value.toString() != this.grdDVBS.value["TYLEDOITAC"].toString() ||
          this.tab_qldv.cboKycuoc.value.toString() != this.grdDVBS.value["KYCUOC2"].toString())
        {
          this.ShowError("Có sự thay đổi về giá trị, bạn không thể xóa dịch vụ!");
          return;
        }

        if (this.grdDVBS.list && this.grdDVBS.list.length > 0) {
          this.$bvModal
            .msgBoxConfirm("Bạn có muốn hủy danh mục bổ sung cho thuê bao 84" + this.tab_qldv.txtSoMay3.value + " không?",
              {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              }
            )
            .then(async (v) => {
              if (v) {
                this.loading(true);
                var input = {
                  "so_tb": "84" + this.tab_qldv.txtSoMay3.value,
                  "ghi_chu": this.tab_qldv.textGhichu3.value,
                  "gia_tien": this.tab_qldv.txtTienVat.value,
                  "khoi_luong": this.tab_qldv.txtKhoiLuong.value,
                  "ky_cuoc": this.tab_qldv.cboKycuoc.value,
                  "ma_danhhmuc": this.tab_qldv.cboDanhmucbs.value,                  
                  "tyle_doitac": this.tab_qldv.txtDoitac.value,
                  "tyle_vnp": this.tab_qldv.txtVNPT.value
                }
                await this.ts_vcc_dichvubs_huy(input);

                if (this.ds3 && this.ds3.length > 0) {
                  await this.TaoDuLieu3(true, LoaiHopDong.DD_XOADATA);
                  await this.ThemMoi_HopDong_siptrunking(true, this.dsHDKH, this.dsHDTB, this.huonggiao_id, LoaiHopDong.DD_XOADATA);
                    // bangts.ExeSQL("update " + DatabaseConstants.DB2 + ".giaophieu set ttph_id = 4 where hdtb_id =" + hdtb_id);
                }
                this.SetButton(0);
                this.loading(false);
              } 
            })
            .catch((e) => {
              this.ShowError(e);
              this.loading(false);
            });             
        } else {
          this.ShowError("Thuê bao chưa đăng ký dịch vụ bổ sung, bạn không thể xóa!");
          return;
        }          
      } catch (ex) {
        this.ShowError(ex);
      }
    },
    TaoDuLieu: async function (themmoi, loaihd_id) {
      await this.TaoDuLieu_HDKH(themmoi, loaihd_id);
      if (this.loi_sinh_magd) return;
      await this.TaoDuLieu_HDTB(themmoi);
    },
    TaoDuLieu_HDKH: async function (themmoi, loaihd_id) {
      try {
        var vmagd_temp = "";
        var dsHDKH = [];
        var rowHDKH = {};
        if (themmoi == true) rowHDKH.HDKH_ID = 0;
        if (themmoi == true) {
          vmagd_temp = await this.Lay_Ma_GD(loaihd_id);
          if (vmagd_temp.includes("ERROR:")) {
            this.ShowError(vmagd_temp.split(':')[1]);
            this.loi_sinh_magd = true;
            return
          }
          rowHDKH.MA_GD = vmagd_temp;
          rowHDKH.MA_HD = "";
        }
        rowHDKH.MA_KH = this.ds[0]["ma_kh"].toString();
        rowHDKH.KHACHHANG_ID = Number(this.ds[0]["khachhang_id"].toString());
        rowHDKH.TEN_KH = this.ds[0]["ten_kh"].toString();
        rowHDKH.DIACHI_KH = this.ds[0]["diachi_kh"].toString();
        rowHDKH.NGAYLAP_HD = this.tt_nd.ngay_cn;
        rowHDKH.NGAY_YC = this.tt_nd.ngay_cn;
        rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
        rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;
        rowHDKH.LOAIHD_ID = loaihd_id;
        rowHDKH.GHICHU = this.tab_dkVCC.txtGhichu.value ? this.tab_dkVCC.txtGhichu.value : "";
        rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
        rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;
        rowHDKH.MAY_CN = this.tt_nd.may_cn;

        dsHDKH.push(rowHDKH);
        this.dsHDKH = dsHDKH;
      } catch (e) {
        console.log("TaoDuLieu_HDKH")
        console.log(e)
      }
    },
    TaoDuLieu_HDTB: async function (themmoi) {
      try {      
        var dsHDTB = []
        var rowHDTB = {}
        if (themmoi == true) rowHDTB.HDTB_ID = 0;
        rowHDTB.HDKH_ID = this.dsHDKH[0].HDKH_ID;
        rowHDTB.THUEBAO_ID = Number(this.ds[0]["thuebao_id"].toString());
        rowHDTB.MA_TB = this.tab_dkVCC.txtSoMay.value;
        rowHDTB.TEN_TB = this.ds[0]["ten_tb"].toString();
        rowHDTB.DIACHI_TB = this.ds[0]["diachi_tb"].toString();
        rowHDTB.KIEULD_ID = KieuLapDat.THAYDOI_DVGT_DD;
        rowHDTB.DOITUONG_ID = Number(this.ds[0]["doituong_id"]);
        rowHDTB.TTHD_ID = TrangThaiHD.HOAN_THANH;
        rowHDTB.LOAITB_ID = Number(ds[0]["loaitb_id"].toString());
        rowHDTB.DICHVUVT_ID = DichVuVienThong.DI_DONG;
        rowHDTB.DONVI_ID = Number(this.ds[0]["donvi_id"].toString());
        rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
        rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
        rowHDTB.MAY_CN = this.tt_nd.may_cn;
        rowHDTB.IP_CN = this.tt_nd.ip_cn;
        rowHDTB.GHICHU = this.tab_dkVCC.txtGhichu.value ? this.tab_dkVCC.txtGhichu.value : "";
        rowHDTB.STATUS = "5";

        dsHDTB.push(rowHDTB);
        this.dsHDTB = dsHDTB;
      } catch (e) {
        console.log("TaoDuLieu_HDTB")
        console.log(e)
      }
    },
    TaoDuLieu2: async function (themmoi, loaihd_id) {
      await this.TaoDuLieu2_HDKH(themmoi, loaihd_id);
      if (this.loi_sinh_magd) return;
      await this.TaoDuLieu2_HDTB(themmoi);
    },
    TaoDuLieu2_HDKH: async function (themmoi, loaihd_id) {
      try {
        var vmagd_temp = "";
        var dsHDKH = [];
        var rowHDKH = {};
        if (themmoi == true) rowHDKH.HDKH_ID = 0;               
        if (themmoi == true) {
          vmagd_temp = await this.Lay_Ma_GD(loaihd_id);
          if (vmagd_temp.includes("ERROR:")) {
            this.ShowError(vmagd_temp.split(':')[1]);
            this.loi_sinh_magd = true;
          }
          rowHDKH.MA_GD = vmagd_temp;
          rowHDKH.MA_HD = "";
        }
        rowHDKH.MA_KH = this.ds2[0]["ma_kh"].toString();
        rowHDKH.KHACHHANG_ID = Number(this.ds2[0]["khachhang_id"].toString());
        rowHDKH.TEN_KH = this.ds2[0]["ten_kh"].toString();
        rowHDKH.DIACHI_KH = this.ds2[0]["diachi_kh"].toString();
        rowHDKH.NGAYLAP_HD = this.tt_nd.ngay_cn;
        rowHDKH.NGAY_YC = this.tt_nd.ngay_cn;
        rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
        rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;
        rowHDKH.LOAIHD_ID = loaihd_id;
        rowHDKH.GHICHU = "Cập nhật gói từ ĐHSX!";
        rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
        rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;
        rowHDKH.MAY_CN = this.tt_nd.may_cn;

        dsHDKH.push(rowHDKH);
        this.dsHDKH = dsHDKH;
      } catch (e) {
        console.log("TaoDuLieu2_HDKH")
        console.log(e)
      }      
    },
    TaoDuLieu2_HDTB: async function (themmoi) {
      try {
        var dsHDTB = [];
        var rowHDTB = {};
        if (themmoi == true) rowHDTB.HDTB_ID = 0;
        rowHDTB.HDKH_ID = this.dsHDKH[0].HDKH_ID;
        rowHDTB.THUEBAO_ID = Number(this.ds2[0]["thuebao_id"].toString());
        rowHDTB.MA_TB = this.tab_dkVCC.txtSoMay.value;
        rowHDTB.TEN_TB = this.ds2[0]["ten_tb"].toString();
        rowHDTB.DIACHI_TB = this.ds2[0]["diachi_tb"].toString();
        rowHDTB.KIEULD_ID = KieuLapDat.THAYDOI_DVGT_DD;
        rowHDTB.DOITUONG_ID = Number(this.ds2[0]["doituong_id"]);
        rowHDTB.TTHD_ID = TrangThaiHD.HOAN_THANH;
        rowHDTB.LOAITB_ID = Number(this.ds2[0]["loaitb_id"].toString());
        rowHDTB.DICHVUVT_ID = DichVuVienThong.DI_DONG;
        rowHDTB.DONVI_ID = Number(this.ds2[0]["donvi_id"].toString());
        rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
        rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
        rowHDTB.MAY_CN = this.tt_nd.may_cn;
        rowHDTB.IP_CN = this.tt_nd.ip_cn;
        rowHDTB.GHICHU = "Cập nhật gói từ ĐHSX!";
        rowHDTB.STATUS = "5";

        dsHDTB.push(rowHDTB);
        this.dsHDTB = dsHDTB;
      } catch (e) {
        console.log("TaoDuLieu2_HDTB")
        console.log(e)
      }
    },
    TaoDuLieu3: async function (themmoi, loaihd_id) {
      await this.TaoDuLieu3_HDKH(themmoi, loaihd_id);
      if (this.loi_sinh_magd) return;
      await this.TaoDuLieu3_HDTB(themmoi);
    },
    TaoDuLieu3_HDKH: async function (themmoi, loaihd_id) {
      try {
        var vmagd_temp = "";
        var dsHDKH = [];
        var rowHDKH = {};
        if (themmoi == true) rowHDKH.HDKH_ID = 0;
        if (themmoi == true) {
          vmagd_temp = await this.Lay_Ma_GD(loaihd_id);
          if (vmagd_temp.includes("ERROR:")) {
            this.ShowError(vmagd_temp.split(':')[1]);
            this.loi_sinh_magd = true;
          }
          rowHDKH.MA_GD = vmagd_temp;
          rowHDKH.MA_HD = "";
        }
        rowHDKH.MA_KH = this.ds3[0]["ma_kh"].toString();
        rowHDKH.KHACHHANG_ID = Number(this.ds3[0]["khachhang_id"].toString());
        rowHDKH.TEN_KH = this.ds3[0]["ten_kh"].toString();
        rowHDKH.DIACHI_KH = this.ds3[0]["diachi_kh"].toString();
        rowHDKH.NGAYLAP_HD = this.tt_nd.ngay_cn;
        rowHDKH.NGAY_YC = this.tt_nd.ngay_cn;
        rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
        rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;
        rowHDKH.LOAIHD_ID = loaihd_id;
        rowHDKH.GHICHU = this.tab_qldv.textGhichu3.value;
        rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
        rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;
        rowHDKH.MAY_CN = this.tt_nd.may_cn;

        dsHDKH.push(rowHDKH);
        this.dsHDKH = dsHDKH;
      } catch (e) {
        console.log("TaoDuLieu3_HDKH")
        this.ShowError(e)
      }      
    },
    TaoDuLieu3_HDTB: async function (themmoi) {
      try {
        var dsHDTB = [];
        var rowHDTB = {}
        if (themmoi == true) rowHDTB.HDTB_ID = 0;
        rowHDTB.HDKH_ID = this.dsHDKH[0].HDKH_ID;
        rowHDTB.THUEBAO_ID = Number(this.ds3[0]["thuebao_id"].toString());
        rowHDTB.MA_TB = this.tab_dkVCC.txtSoMay.value;
        rowHDTB.TEN_TB = this.ds3[0]["ten_tb"].toString();
        rowHDTB.DIACHI_TB = this.ds3[0]["diachi_tb"].toString();
        rowHDTB.KIEULD_ID = KieuLapDat.THAYDOI_DVGT_DD;
        rowHDTB.DOITUONG_ID = Number(this.ds3[0]["doituong_id"]);
        rowHDTB.TTHD_ID = TrangThaiHD.HOAN_THANH;
        rowHDTB.LOAITB_ID = Number(this.ds3[0]["loaitb_id"].toString());
        rowHDTB.DICHVUVT_ID = DichVuVienThong.DI_DONG;
        rowHDTB.DONVI_ID = Number(this.ds3[0]["donvi_id"].toString());
        rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
        rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
        rowHDTB.MAY_CN = this.tt_nd.may_cn;
        rowHDTB.IP_CN = this.tt_nd.ip_cn;
        rowHDTB.GHICHU = this.tab_qldv.textGhichu3.value;
        rowHDTB.STATUS = "5";

        dsHDTB.push(rowHDTB);
        this.dsHDTB = dsHDTB;
      } catch (e) {
        console.log("TaoDuLieu3_HDTB")
        this.ShowError(e)
      }        
    },
    kiemtra_v3() {
      if (this.tab_qldv.cboDanhmucbs.value && this.tab_qldv.cboDanhmucbs.value.toString() == "5") {
        if (!this.tab_qldv.txtKhoiLuong.value || this.tab_qldv.txtKhoiLuong.value == "") {
          this.ShowError("Hãy nhập khối lượng (số tháng lưu trữ dữ liệu)!");
          this.$refs.txtKhoiLuong.focus();
          return false;
        }

        if (Number(this.tab_qldv.txtKhoiLuong.value) == 0) {         
          this.ShowError("Số tháng lưu trữ dữ liệu không hợp lệ!");
          this.$refs.txtKhoiLuong.focus();
          return false;
        }

        if (Number(this.tab_qldv.txtKhoiLuong.value) > 24) {
          this.ShowError("Số tháng lưu trữ dữ liệu không hợp lệ!");
          this.$refs.txtKhoiLuong.focus();
          return false;
        }
      } else {
        if (!this.tab_qldv.txtTienVat.value || this.tab_qldv.txtTienVat.value.toString().trim() == "") {
          this.ShowError("Hãy nhập danh mục giá tiền!");
          this.$refs.txtTienVat.focus();
          return false;
        }

        if (!this.tab_qldv.txtVNPT.value || this.tab_qldv.txtVNPT.value.toString().trim() == "") {
          this.ShowError("Hãy nhập danh mục tỷ lệ VNPT!");
          this.$refs.txtVNPT.focus();
          return false;
        }

        if (!this.tab_qldv.txtDoitac.value || this.tab_qldv.txtDoitac.value.toString().trim() == "") {
          this.ShowError("Hãy nhập danh mục tỷ lệ đối tác!");
          this.$refs.txtDoitac.focus();
          return false;
        }

        if (Number(this.tab_qldv.txtVNPT.value) + Number(this.tab_qldv.txtDoitac.value) != 100) {
          this.ShowError("Tỷ lệ phân chia không đủ 100% !");
          this.$refs.txtVNPT.focus();
          return false;
        }
      }
      return true;
    },
    txtSomay_KeyPress: async function () {
      try {
        this.loading(true);
        var promise_check = this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(
          this.tab_dkVCC.txtSoMay.value.toString().trim(),
          "0",
          DichVuVienThong.DI_DONG,
          LoaiHopDong.DD_SIPTRUNKING
        );
        var check = await promise_check;
        if (!check) {
          this.loading(false);
          return
        };
        this.SetButton(0);
        await this.HienThiTT_DanhBa(this.tab_dkVCC.txtSoMay.value.toString().trim());
        this.loading(false);
      } catch (ex) {
        this.ShowError("Có lỗi: " + ex.toString());
        this.loading(false);
      }
    },
    txtSoMay2_KeyPress: async function () {
      try {
        this.loading(true);
        this.ds2 = await this.LAY_DANHBA_THEO_MATB(this.tab_cnVCC.txtSoMay2.value, this.dichvuvt_id, 0);//Lấy ra bất kỳ thuê bao nào
        if (this.ds2 && this.ds2.length > 0) {
          this.button.btnCapnhatgoi.Enabled = true;
          var tracuu_goi = await this.ts_vcc_ds_goi_tb("84" + this.tab_cnVCC.txtSoMay2.value);
          if (tracuu_goi && tracuu_goi.length > 0) {
            for (var i = 0; i < tracuu_goi.length; i++) {
              tracuu_goi[i].ISCHECK = false
            }
            this.grdGoidadk.list = tracuu_goi;
          }
        } else {
          this.ShowError("Thuê bao không có trong danh bạ, liên hệ admin kiểm tra lại!");
          this.loading(false);
          return;
        }
        this.loading(false);
      }
      catch (ex) {
        this.ShowError(ex);
        this.loading(false);
      }
    },
    txtSoMay3_KeyPress: async function () {
      try {
        this.loading(true);
        this.Load_view4()
        this.load_v3 = "";
        this.ds3 = await this.LAY_DANHBA_THEO_MATB(this.tab_qldv.txtSoMay3.value, this.dichvuvt_id, 0);//Lấy ra bất kỳ thuê bao nào
        if (this.ds3 && this.ds3.length > 0) {
          var tracuu_bs = await this.ts_vcc_ds_tb_dichvubs("84" + this.tab_qldv.txtSoMay3.value);
          if (tracuu_bs && tracuu_bs.length > 0) {
            this.load_v3 = "1";
            this.grdDVBS.list = tracuu_bs;

            this.$refs.txtKhoiLuong.focus();
            this.button.btnThemmoi3.Enabled = true;
            this.button.btnCapnhat3.Enabled = false;
            this.button.btnHuy3.Enabled = false;
            this.tab_qldv.cboDanhmucbs.value = "5";
            this.load_v3 = "";
          } else {
            this.load_v3 = "";
            this.grdDVBS.list = [];
            this.button.btnThemmoi3.Enabled = true;
          }
        } else {
          this.load_v3 = "";
          this.button.btnThemmoi3.Enabled = false;
          this.grdDVBS.list = [];
          this.ShowError("Thuê bao không có trong danh bạ, liên hệ admin kiểm tra lại!");
          this.loading(false);
          return;        
        }
        this.loading(false);
      } catch (ex) {
        this.ShowError(ex);
        this.loading(false);
      }
    },
    SetButton(status) {
      switch (status) {
        case 0: // nhap moi
          this.button.btnDangky.Enabled = false;
          this.button.btnHuy.Enabled = false;
          this.button.btnTamngung.Enabled = false;
          this.button.btnKichhoatlai.Enabled = false;
          this.button.btnCapnhatgoi.Enabled = false;
          this.Load_view4();
          this.Clear();
          break;
      }
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    GetData: function (response) {
      if (response.data.error == 200 || response.data.error == "200") {
        return response.data.data;
      } else if (response.data.error == 204 || response.data.error == "204") {
        return [];
      } else {
        this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
  },
  async mounted() {
    this.loading(true);
    try {
      this.tt_nd = {
        ma_tinh: await this.$root.token.getMaTinh(),
        ma_nd: (await this.$root.token.getThongTinNguoiDung()).ma_nd,
        USER_NEO: (await this.$root.token.getThongTinNguoiDung()).user_neo,
        phanvung_id: Number(await this.$root.token.getPhanVungID()),
        donvi_id: Number(await this.$root.token.getDonViID()),
        nhanvien_id: Number(await this.$root.token.getNhanVienID()),
        ip_cn: await this.$root.token.getIP(),
        may_cn: await this.$root.token.getMachine(),
        nguoi_cn: await this.$root.token.getUserName(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY")
      };
      await Promise.all([
        this.SetButton(0),
        this.load_tab1(),
        this.load_tab2(),
        this.load_tab3(),
      ]);
      
      this.loading(false);
    } catch (ex) {
      this.loading(false);
      console.log(ex)
    }
  },
  watch: {
    "tab_dkVCC.cboLoaiNhom.value"(val) {
      if (val) this.onChange_cboLoaiNhom(val);
    },
    "tab_qldv.cboDanhmucbs.value"(val) {
      if (val) {
        if (val && val.toString().trim() == "5") {
          this.tab_qldv.cboKycuoc.Enabled = false;
          this.tab_qldv.txtTienVat.Enabled = false;
          this.tab_qldv.txtVNPT.Enabled = false;
          this.tab_qldv.txtDoitac.Enabled = false;
          this.tab_qldv.txtKhoiLuong.Enabled = true;
        } else {
          this.tab_qldv.cboKycuoc.Enabled = true;
          this.tab_qldv.txtTienVat.Enabled = true;
          this.tab_qldv.txtVNPT.Enabled = true;
          this.tab_qldv.txtDoitac.Enabled = true;
          this.tab_qldv.txtKhoiLuong.Enabled = false;
        }
      }
    }
  }
};
</script>
