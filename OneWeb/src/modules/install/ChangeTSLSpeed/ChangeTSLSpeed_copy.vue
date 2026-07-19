<template src="./template.html"></template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import frnGiaoPhieuNV from "@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue";
import frmThongTinKT_2 from "@/modules/contract/setup/DeclareLandline/popups/frmThongTinKT_2.vue";
import frmTraPhieuTC from "@/modules/contract/setup/ReturnInstallTicket/frmTraPhieuTC.vue";
import DuAnHDTB from "@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB";
import DoKiemModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue";
import SearchContractModal from "@/modules/search/subscriber/SearchContract/SearchContract.vue";
import InBienBan from "@/modules/print/InBienBan/InBienBan";
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";

import moment from "moment";
import {
  TrangThaiHD,
  DichVuVienThong,
  LoaiHopDong,
  KIEU_HD,
  TRANGTHAI_DONGBO,
  TRANGTHAI_PH,
  LoaiHinhTB,
  LOAI_DV,
  DatabaseConstants,
} from "@/const/enums";
export default {
  components: {
    breadcrumb,
    Select2Ext,
    VueDateExt,
    DataGridCustom,
    frnGiaoPhieuNV,
    frmThongTinKT_2,
    frmTraPhieuTC,
    DuAnHDTB,
    DoKiemModal,
    SearchContractModal,
    InBienBan,
  },
  data() {
    return {
      current_row: 0,
      modelIn: {},
      labelFunctionTitle: "",
      modalSearch: {
        loai_hd_id: 0,
        trangthai_hd: 0,
      },
      target: ".main-wrapper",
      columns_nv: [
        {
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "so_dt",
          headerText: "Số điện thoại",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "nhiemvu",
          headerText: "Nhiệm vụ",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
      ],
      columns: [
        {
          fieldName: "ngay_yc",
          headerText: "Ngày YC",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ngaygiao",
          headerText: "Ngày giao",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ma_gd",
          headerText: "Mã GD",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ma_tb",
          headerText: "Account",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ma_lt",
          headerText: "Số ảo",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "diachi_tb",
          headerText: "Địa chỉ thuê bao",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "trangthai_in",
          headerText: "Trạng thái in",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ngay_ht",
          headerText: "Ngày HT",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
      ],
      //region Khai bao
      tthd_id: 0,
      huonggiao_id: 0,
      hdtb_id: null,
      hdkh_id: null,
      phieu_id: null,
      dtList: null,
      inDex_load: 0,
      bangts: null,
      hoanthienhs: null,
      tt_nd: null,
      thamso_md: null,
      tchopdong: null,
      lhd: null,
      hoancong: null,
      nhanvien: null,
      giaophieutc: null,
      tdan: null,

      dsnhanviengp: null,
      objgiaophieu: null,
      checkdata: null,

      luong_id: 0,
      dsloaihd_id: "",
      dsdichvuvt_id: "",
      quytrinh_id: 0,
      dichvuvt_id: 0,
      thuebao_id: 0,

      dtThaoTac: [],
      dtControl: [],
      ktra_load: true,
      _vquytrinh_id: 0,
      _khdn: null,

      nhanvien_tc_id: -1,
      congviec_th: "",
      ngay_cn: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
      dialogComponent: null,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
      },
      dialogEvts: {
        close: this.dlgClose,
      },
      modelIn: {},
      modalTitle: "",
      position: { X: "center", Y: "top" },
      //:::::::::::::::::::::::::::::::::::::::
      //endregion
    };
  },
  async mounted() {
    this.tt_nd = await this.$root.token.getThongTinNguoiDung();
    console.log(this.$root.token.getDonViID());
    this.frmThayDoiTocDo_TSL_Load();
  },
  methods: {
    //region Load
    frmThayDoiTocDo_TSL_Load() {
      let str = "";
      try {
        try {
          this.$refs.dtpNgayGV.value = moment();
          this.$refs.dtpNgayHT.value = moment();
          let Tag = this.$route.query.tag;
          if (Tag != "") {
            str = Tag;
            let words = str.split(" ");
            if (!words || words.length == 1) {
              words = str.split("+");
            }
            if (words.length >= 1) this.tthd_id = Number(words[0]);
            if (words.length >= 2) {
              this.dsloaihd_id = words[1];
              this.dsloaihd_id = this.dsloaihd_id.replaceAll(";", ",");
            }
            if (words.length >= 3) {
              this.dsdichvuvt_id = words[2];
              this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(";", ",");
            }
          } else {
            this.tthd_id = TrangThaiHD.DANG_THI_CONG;
            this.dsloaihd_id = "1";
            this.dsdichvuvt_id = "9";
          }
        } catch (err) {
          console.log(err);
          this.tthd_id = TrangThaiHD.DANG_THI_CONG;
          this.dsloaihd_id = "1";
          this.dsdichvuvt_id = "9";
        }
        this.load();
      } catch (err) {
        console.log(err);
      }
    },
    overlayClick() {
      this.$refs.generalDialog.hide();
    },
    async popupClosed(val) {
      console.log("popupClosed");
      if (!val) {
        this.popupRetVal = {};
        this.ClosePopup("modalComponentsITVAS");
        return;
      }

      this.ClosePopup("modalComponentsITVAS");
    },
    async dlgClose(e) {
      console.log("dlgClose", e);
    },

    async load() {
      let rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_DICHVU_VT");
      this.$refs.cboDichVuVT.HT_COMBOBOX(
        rs.data.data.filter((item) =>
          this.dsdichvuvt_id.includes(item.DICHVUVT_ID)
        ),
        "TEN_DVVT",
        "DICHVUVT_ID"
      );

      await this.cboDichVuVT_SelectedValueChanged();

      rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_TOCDO_KENH");
      this.$refs.cboTocDoMoi.HT_COMBOBOX(rs.data.data, "TOCDO", "TOCDO_ID");
      this.$refs.cboTDCu.HT_COMBOBOX(rs.data.data, "TOCDO", "TOCDO_ID");

      rs = await this.axios.post("web-hopdong/thanhly/sp_lay_ds_tatca_vetinh", {
        loaidv_id: LOAI_DV.TRAM_VT,
      });
      this.$refs.cboDonViCuoi.HT_COMBOBOX(rs.data.data, "ten_dv", "donvi_id");

      this.$refs.cboDonViDau.HT_COMBOBOX(rs.data.data, "ten_dv", "donvi_id");

      rs = await this.axios.post(
        "web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_donvi",
        {
          donvi_id: this.$root.token.getDonViID(),
        }
      );
      this.$refs.cboNguoiGV.dataSource = rs.data.data;
      this.$refs.cboNguoiGV.dataTextField = "ten_nv";
      this.$refs.cboNguoiGV.dataValueField = "nhanvien_id";
      this.$refs.cboNguoiGV.value = this.$root.token.getNhanVienID();

      rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_LOAI_CAP");
      this.$refs.cboLoaiCap_Dau.HT_COMBOBOX(
        rs.data.data,
        "LOAICAP",
        "LOAICAP_ID"
      );
      this.$refs.cboLoaiCap_Cuoi.HT_COMBOBOX(
        rs.data.data,
        "LOAICAP",
        "LOAICAP_ID"
      );

      rs = await this.axios.get(
        "web-hopdong/danhmuc/ds_loaitb/" + this.$refs.cboDichVuVT.value
      );
      this.$refs.cboThietBiDau.HT_COMBOBOX(
        rs.data.data,
        "loaihinh_tb",
        "loaitb_id"
      );
      this.$refs.cboThietBiCuoi.HT_COMBOBOX(
        rs.data.data,
        "loaihinh_tb",
        "loaitb_id"
      );

      await this.HT_DANHSACH();
      this.ktra_load = false;
    },
    btnLayMaGD_Click() {
      this.$refs.popupFrmTraCuuHopDong.$children[0].clearData();
      this.modalSearch = {
        loai_hd_id: LoaiHopDong.THAY_DOI_TOCDO_TSL,
        trangthai_hd: this.tthd_id,
      };
      this.$refs.popupFrmTraCuuHopDong.$children[0].loadPopup();
      this.$refs.popupFrmTraCuuHopDong.show();
    },
    acceptSearchContract(item) {
      console.log(item);
      this.$refs.txtMaGD.value = item.hopdong.ma_gd ? item.hopdong.ma_gd : "";
      this.$refs.txtMaTB.value = item.thuebao.ma_tb ? item.thuebao.ma_tb : "";
      this.$refs.popupFrmTraCuuHopDong.hide();
      this.HT_DANHSACH();
    },

    async HT_DANHSACH() {
      return new Promise(async (resolve, reject) => {
        try {
          this.loading(true);
          let vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
          if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0;
          else vloai_id = 1;
          let rs = await this.axios.post(
            "web-ccdv/khaibaotsl/lay_ds_phieu_giaoden_tsl",
            {
              dichvuvt_id: this.dichvuvt_id,
              tthd_id: this.tthd_id,
              huonggiao_id: this.huonggiao_id,
              nhanvien_id: this.tt_nd.nhanvien_id,
              ma_gd: "0",
              phieu_id: 0,
              loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_TSL,
              loai_id: vloai_id,
            }
          );
          let dtList = rs.data && rs.data.data ? rs.data.data : [];
          if (dtList.length > 0) {
            dtList = dtList.map((item) => ({
              ...item,
              ngay_yc:
                item.ngay_yc && !item.ngay_yc.includes("/")
                  ? moment(item.ngay_yc).format("DD/MM/YYYY HH:mm A")
                  : item.ngay_yc,
              ngay_ht:
                item.ngay_ht && !item.ngay_ht.includes("/")
                  ? moment(item.ngay_ht).format("DD/MM/YYYY HH:mm A")
                  : item.ngay_ht,
            }));
            this.$refs.dtgDanhSach.dataSource = dtList;
            this.$refs.tsbtnHoanThienHS.classList.remove("disabled");
            this.$refs.btnPhieuGiaosai.classList.remove("disabled");
            this.$refs.tsbtnGiaoPhieu.classList.remove("disabled");
          } else {
            this.Clear();
            this.$refs.dtgDanhSach.dataSource = [];
            this.$refs.tsbtnHoanThienHS.classList.add("disabled");
            this.$refs.tsbtnHoanThanh.classList.add("disabled");
            this.$refs.tsbtnGiaoPhieu.classList.add("disabled");
            this.$refs.btnPhieuGiaosai.classList.add("disabled");
          }

          this.$refs.dtgDanhSach.$refs.DataGridCustom.setCurrentSelectedRow(
            this.current_row
          );
          this.$refs.dtgDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes =
            [];
          this.$refs.dtgDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes.push(
            this.current_row
          );

          this.loading(false);
        } catch (err) {
          console.log(err);
          this.$toast.error("Có lỗi : " + err.message);
        }
        resolve(true);
      });
    },
    //endregion

    //region Clear
    Clear() {
      this.hdkh_id = 0;
      this.hdtb_id = 0;
      this.thuebao_id = 0;
      this.phieu_id = 0;
      this.$refs.txtMaGD.value = "";
      this.$refs.txtMaTB.value = "";
      this.$refs.txtTenTB.value = "";
      this.$refs.txtDiaChiTB.value = "";
      this.$refs.txtNDGiao.value = "";
      this.$refs.txtThongTinTra.value = "";
      this.$refs.tsbtnHoanThienHS.classList.add("disabled");
      this.$refs.chkNgayBG.checked = false;
      this.$refs.chkNgayGV.checked = false;
      this.chkNgayBG_CheckedChanged();
      this.chkNgayGV_CheckedChanged();
      this.$refs.dtpNgayGV.value = this.ngay_cn;
      this.$refs.dtpNgayHT.value = this.ngay_cn;
      this.$refs.dgvNhanVien.dataSource = null;
      this.$refs.txtMaTB.focus();
      this.$refs.tsbtnKichHoat.classList.add("disabled");
      this.$refs.cboNguoiGV.value = this.$root.token.getNhanVienID();

      this.$refs.txtSvlan_Dau.value = "";
      this.$refs.txtCvlan_Dau.value = "";
      this.$refs.txtNasport_id_Dau.value = "";
      this.$refs.txtIpDslam_Dau.value = "";
      this.$refs.txtAdsl_cap_Dau.value = "";
      this.$refs.txtAdsl_port_Dau.value = "";
      this.$refs.txtVrf_Dau.value = "";
      this.$refs.txtIpwan_Dau.value = "";

      this.$refs.txtSvlan_Cuoi.value = "";
      this.$refs.txtCvlan_Cuoi.value = "";
      this.$refs.txtNasport_id_Cuoi.value = "";
      this.$refs.txtIpDslam_Cuoi.value = "";
      this.$refs.txtAdsl_cap_Cuoi.value = "";
      this.$refs.txtAdsl_port_Cuoi.value = "";
      this.$refs.txtVrf_Cuoi.value = "";
      this.$refs.txtIpwan_Cuoi.value = "";

      this.$refs.txtTimeSlot_Dau.value = "";
      this.$refs.txtTimeSlot_Cuoi.value = "";
      this.$refs.txtTBi_Dau.value = "";
      this.$refs.txtTbi_Cuoi.value = "";

      this.$refs.txtTuCapGoc_Dau.value = "";
      this.$refs.txtDoiCapGoc_Dau.value = "";
      this.$refs.txtCapGoc_Dau.value = "";
      this.$refs.txtHopCapNgon_Dau.value = "";
      this.$refs.txtTuCapNgon_Dau.value = "";
      this.$refs.txtDoiCapNgon_Dau.value = "";
      this.$refs.txtCapNgon_Dau.value = "";
      this.$refs.txtTuyenCap_Dau.value = "";
      this.$refs.txtLienTu_Dau.value = "";
      this.$refs.txtSoMetDay_Dau.value = "0";
      this.$refs.txtLink_Dau.value = "";
      this.$refs.txtSerial_Dau.value = "";

      this.$refs.txtTuCapGoc_Cuoi.value = "";
      this.$refs.txtDoiCapGoc_Cuoi.value = "";
      this.$refs.txtCapGoc_Cuoi.value = "";
      this.$refs.txtHopCapNgon_Cuoi.value = "";
      this.$refs.txtTuCapNgon_Cuoi.value = "";
      this.$refs.txtDoiCapNgon_Cuoi.value = "";
      this.$refs.txtCapNgon_Cuoi.value = "";
      this.$refs.txtTuyenCap_Cuoi.value = "";
      this.$refs.txtLienTu_Cuoi.value = "";
      this.$refs.txtSoMetDay_Cuoi.value = "0";
      this.$refs.txtLink_Cuoi.value = "";
      this.$refs.txtSerial_Cuoi.value = "";

      this.$refs.txtDiaChiDau.value = "";
      this.$refs.txtDiaChiCuoi.value = "";
    },
    PHAILAM(code) {
      console.log("VÀo hàm phải làm");
      return this.axios
        .post("/web-hopdong/thaydoi_thongso_megawan/phailam", {
          luong_id: this.luong_id,
          code: code,
        })
        .then((response) => {
          return response.data && response.data.data == "1" ? true : false;
        });
    },
    formatDate(value_, format = "DD/MM/YYYY HH:mm:ss") {
      if (value_ && value_.includes("/")) {
        return value_;
      } else {
        if (value_) return moment(value_).format(format);
        else return this.ngay_cn;
      }
    },
    //endregion

    //region Click
    tsbtnThongTinKT_Click() {
      this.$refs.frmThongTinKT_2.thuebao_id = this.thuebao_id;
      this.$refs.frmThongTinKT_2.ma_tb = this.$refs.txtMaTB.value;
      this.$refs.frmThongTinKT_2.dichvuvt_id = DichVuVienThong.TSL;
      this.$refs.frmThongTinKT_2.kieu = 1;
      this.$refs.frmThongTinKT_2.show();
    },
    async tsbtnChuyenDB_Click() {
      try {
        let rs;
        if (!this.KiemTra_DL()) return;

        //region Kiểm tra hoàn công
        // let vkq = checkdata.GET_VALUE_FUNC("{?DB2}.kiemtra_hoancong", "vhdtb_id", hdtb_id)
        // if (vkq != "OK")
        // {
        //     this.$toast.error(vkq);
        //     return;
        // }
        //endregion

        let selected = this.$refs.dtgDanhSach.getSelectedRecords();
        let dtList = selected;

        if (
          dtList[0] &&
          dtList[0]["ngay_ht_gp"] &&
          dtList[0]["ngay_ht_gp"] == ""
        ) {
          this.$toast.error(
            "Bạn phải cập nhật ngày hoàn thành trước khi hoàn công!"
          );
          return;
        }

        if (await this.PHAILAM("CAPNHAT_NGAYHT_HC")) {
          //let thangsau = tt_nd.ngay_cn.AddMonths(1).toString("yyyyMM");
          console.log(
            moment(this.$refs.dtpNgayHT.value, "DD/MM/YYYY HH:mm:ss")
          );

          let thangsau = moment(
            this.$refs.dtpNgayHT.value,
            "DD/MM/YYYY HH:mm:ss"
          )
            .add(1, "months")
            .format("yyyyMM");
          let rs = await this.axios.post(
            "web-hopdong/dongbo_tb_codinh_lentongdai/kt_dbthang_dachot",
            { vngay_cn: thangsau }
          );
          let dbt = rs.data.data;
          if (dbt != "0") {
            this.$toast.error(dbt);
            return;
          }
          //lhd.CAPNHAT_NGAYHT(hdtb_id, tt_nd.ngay_cn);
          await this.axios.post("/web-hopdong/hopdong/fn_capnhat_ngayht", {
            hdtb_id: this.hdtb_id,
            ngay_ht: this.$refs.dtpNgayHT.value,
          });
        }

        ///TuanNA: 11.06.2013 : Thêm code kiểm tra tạo bảng trước khi hoàn thiện hồ sơ
        let vngay_ht = "";
        let kt_taobang = 0;
        rs = await this.axios.post(
          "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao",
          { param: this.hdtb_id, type: 8 }
        );
        vngay_ht = rs.data.data;
        if (vngay_ht != "" && vngay_ht != "-1") {
          
          rs = await this.axios.post(
            "web-hopdong/thaydoiloaihinhtb/kt_taodb_thang",
            { kyhoadon: vngay_ht }
          );
          kt_taobang = rs.data.data;
          if (kt_taobang == 0) {
            this.$toast.error(
              "Danh bạ tháng chưa được chốt. Liên hệ Admin để xử lý !"
            );
            return;
          }

          let thangsau = moment(new Date()).add(1, "months").format("yyyyMM");
          rs = await this.axios.post(
            "web-hopdong/dongbo_tb_codinh_lentongdai/kt_dbthang_dachot",
            { vngay_cn: thangsau }
          );
          kt_taobang = rs.data.data;
          if (kt_taobang != 0) {
            this.$toast.error(
              "Danh bạ tháng " +
                moment(thangsau, "YYYYMM").format("MM/YYYY") +
                " đã được chốt. Liên hệ Admin để xử lý !"
            );
            return;
          }
        } else {
          this.$toast.error(
            "Thuê bao chưa được cập nhật ngày hoàn thành, Bạn hãy kiểm tra lại!"
          );
          return;
        }
        await this.axios.post(
          "web-hopdong/thaydoiloaihinhtb/hoanthien_hs_thaydoi_tocdo_tsl",
          { hdtb_id: this.hdtb_id }
        );
        if (await this.PHAILAM("KT_HOANCONG_HD_BANCHEO")) {
          try {
            this.hoangcong_hopdong_bancheo(this.hdtb_id); //nhớ làm sau
          } catch (err) {
            console.log(err);
          }
        }
        this.$toast.success("Hoàn thiện hồ sơ vào danh bạ tỉnh thành công! ");
        this.HT_DANHSACH();
      } catch (err) {
        console.log(err);
      }
    },

    tsbtnHoanThanh_Click() {
      this.HoanThanh();
    },

    async HoanThanh() {
      try {
        let rs;
        if (this.$refs.dtgDanhSach.dataSource.length <= 0) {
          this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
          return;
        }
        //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko

        let ngay_sys;
        let ngay_tt;
        let ngay_ht;
        let dtList = this.$refs.dtgDanhSach.getSelectedRecords();
        ngay_sys = this.formatDate(
          this.tt_nd.ngay_cn,
          "DD/MM/YYYY HH:mm:ss",
          "dd/MM/yyyy"
        );
        //ngay_tt = this.formatDate(dtList[0]["ngay_yc"].trim());
        ngay_ht = this.formatDate(
          this.$refs.dtpNgayHT.value,
          "DD/MM/YYYY HH:mm:ss",
          "dd/MM/yyyy"
        );
        if (!dtList[0]["ngay_ht_gp"]) {
          this.$toast.error("Hãy cập nhật ngày hoàn thành!");
          this.$refs.chkNgayBG.focus();
          return;
        }
        // hoangpkn : 28/06/2017. Sửa theo yc HCM (c An) cho phép nhập ngày hoàn thành lùi trong tháng
        //if (!KiemTraDK_HoanThanh(ngay_tt, ngay_ht))//Nếu ngày thanh toán > ngày hoàn thành
        //{
        //    this.$toast.error(" Thuê bao " + dtList[0]["ma_tb"].trim() + " có ngày lập phụ lục : " + ngay_tt + " \n Ngày hoàn thành không được phép nhỏ hơn ngày lập phụ lục");
        //    return;
        //}
        if (
          Number(
            this.formatDate(
              this.$refs.dtpNgayHT.value,
              "DD/MM/YYYY HH:mm:ss",
              "yyyyMM"
            )
          ) <
          Number(this.formatDate(this.ngay_cn, "DD/MM/YYYY HH:mm:ss", "yyyyMM"))
        ) {
          this.$toast.error(
            "Tháng hoàn thành không được nhỏ hơn tháng hiện tại !"
          );
          return;
        }
        // end hoang
        if (
          Number(this.formatDate(ngay_ht, "DD/MM/YYYY HH:mm:ss", "yyyyMM")) <
          Number(this.formatDate(ngay_sys, "DD/MM/YYYY HH:mm:ss", "yyyyMM"))
        ) {
          //Nếu ngày thanh toán > ngày hoàn thành
          this.$toast.error(
            "Bạn không được phép chọn ngày chấm dứt lớn hơn ngày hiện tại"
          );
          return;
        }

        if (this.phieu_id == 0) {
          this.$toast.error("Chưa chọn phiếu để thực hiện!");
          return;
        }

        let dsphieu = [];
        let hg_phieu_id = 0;
        let donvi_nhan_id = 0;
        let tthd_phieu_id = 0;
        rs = await this.axios.post(
          "web-thicong/hths-thaydoi-tocdo/sp_lay_donvi_giaophieu",
          {
            hdtb_id: this.hdtb_id,
            huonggiao_id: this.huonggiao_id,
          }
        );
        dsphieu = rs.data ? rs.data.data : [];
        if (dsphieu.length > 0) {
          this.hg_phieu_id = Number(dsphieu[0]["huonggiao_id"]);
          this.donvi_nhan_id = Number(dsphieu[0]["donvi_id"]);
          this.tthd_phieu_id = Number(dsphieu[0]["tthd_id"]);
        } else {
          this.$toast.error(
            "Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !"
          );
          return;
        }

        await this.axios.post("web-ccdv/khaibaotsl/capnhat_tt_phieugiao", {
          phieu_id: this.phieu_id,
          ttph_id: TRANGTHAI_PH.DATHUCHIEN,
          ngay_th: this.ngay_cn,
        });

        let ds = [];
        //ds = bangts.Ktra_TTPH_TSL(hdtb_id, phieu_id);
        rs = await this.axios.post(
          "web-hopdong/thaydoiloaihinhtb/ktra_ttph_tsl",
          {
            hdtb_id: this.hdtb_id,
            phieu_id: this.phieu_id,
          }
        );
        ds = rs.data ? rs.data.data : [];
        if (ds.length == 1) {
          if (await this.PHAILAM("CAPNHAT_NGAYHT_HC")) {
            //let thangsau = tt_nd.ngay_cn.AddMonths(1).toString("yyyyMM");
            let thangsau = moment(
              this.$refs.dtpNgayHT.value,
              "DD/MM/YYYY HH:mm:ss"
            )
              .add(1, "months")
              .format("yyyyMM");
            rs = await this.axios.post(
              "web-hopdong/dongbo_tb_codinh_lentongdai/kt_dbthang_dachot",
              {
                vngay_cn: thangsau,
              }
            );
            let dbt = rs.data.data;
            if (dbt != "0") {
              this.$toast.error(dbt);
              return;
            }
            //lhd.CAPNHAT_NGAYHT(hdtb_id, tt_nd.ngay_cn);
            await this.axios.post("web-hopdong/hopdong/fn_capnhat_ngayht", {
              hdtb_id: this.hdtb_id,
              ngay_ht: this.$refs.dtpNgayHT.value,
            });
          }
          if (await this.PHAILAM("CAPNHAT_NGAYKH_HC")) {
            await this.axios.post("web-hopdong/hopdong/fn_capnhat_ngayht", {
              hdtb_id: this.hdtb_id,
              ngay_ht: this.ngay_cn,
            });
          }

          //region "Đẩy thông tin qua webservice lên HNI hoangpkn : 27/03/2018"
          /*if (await this.PHAILAM(luong_id, "DAY_DL_HNI"))
          {*/
          rs = await this.axios.post(
            "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao",
            { param: this.hdtb_id, type: 9 }
          );
          let vhdtb_id_hni = rs.data.data;
          if (vhdtb_id_hni != "-1") {
            // var _obj = _khdn.ThucHienHoanCongHopDong(Number(vhdtb_id_hni), tt_nd.ngay_cn.toString("dd/MM/yyyy HH:mm"), tt_nd.ten_nd, txtMaTB.value, "", 1,
            //   1, "Thực hiện đẩy dữ liệu ", "", "pttb", "pttb321");
            // if (_obj.IsError == true) {
            //   this.$toast.error("webservice_hni : " + _obj.Message);
            //   return;
            // }
          }
          // }
          //endregion

          await this.axios.post(
            "web-ccdv/hths-thaydoi-tocdo/capnhat_tt_phieu_hc",
            {
              vhdtb_id: this.hdtb_id,
              vphanvung_id: this.$root.token.getPhanVungID(),
              vttph_id: TRANGTHAI_PH.DAGIAO,
            }
          );

          await this.axios.post(
            "/web-ccdv/dongbo_tb_codinh_lentongdai/hoanthanh_hoancong",
            {
              hdtb_id: this.hdtb_id,
              phieu_id: this.phieu_id,
              tthd_dich_id: this.tthd_phieu_id,
            }
          );

          if (await this.PHAILAM("TUDONG_HTHS")) {
            await this.axios.post(
              "/web-ccdv/dongbo_tb_codinh_lentongdai/giaophieu_td_khi_hc",
              {
                vdonvi_giao_id: this.$root.token.getDonViID(),
                vdonvi_nhan_id: this.$root.token.getDonViID(),
                vhdtb_id: this.hdtb_id,
                vhuonggiao_id: this.hg_phieu_id,
                vmay_cn: this.$root.token.getMaNhanVien(),
                vnguoi_cn: this.$root.token.getMaNhanVien(),
                vnhanvien_id: this.$root.token.getNhanVienID(),
                vphanvung_id: this.$root.token.getPhanVungID(),
                vphieu_id: this.phieu_id,
              }
            );

            // hoanthienhs.HOANTHIEN_HS_THAYDOI_TOCDO_TSL(hdtb_id);
            await this.axios.post(
              "web-hopdong/thaydoiloaihinhtb/hoanthien_hs_thaydoi_tocdo_tsl",
              {
                hdtb_id: this.hdtb_id,
              }
            );
            //bangts.CapNhat_TTPH_ID(hdtb_id);
            //bangts.CapNhat_TT_Phieu_HC(hdtb_id, TRANGTHAI_PH.DAGIAO);
            // bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
            await this.axios.post(
              "web-ccdv/dongbo_tb_codinh_lentongdai/sp_capnhat_tt_phieu_hths",
              {
                hdtb_id: this.hdtb_id,
              }
            );
            if (await this.PHAILAM("KT_HOANCONG_HD_BANCHEO")) {
              // try {
              this.hoangcong_hopdong_bancheo(this.hdtb_id);
              // }
              // catch (err) {
              //   tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action",
              //     "vuser_name", tt_nd.ma_nd,
              //     "vcomputer_name", tt_nd.may_cn,
              //     "vaction_name", "frmHTHSChamDutSD",
              //     "vkey_parameter", "hdtb_id=" + hdtb_id + ",ngay_cn=" + tt_nd.ngay_cn + ", Err : " + err.message,
              //     "vserver_name", tt_nd.ip,
              //     "vnote", " Hoàn công thanh lý");
              // }
            }
            this.$toast.success("Hoàn công và Hoàn thiện hồ sơ thành công !");
          } else {
            await this.axios.post(
              "/web-ccdv/dongbo_tb_codinh_lentongdai/giaophieu_td_khi_hc",
              {
                vdonvi_giao_id: this.$root.token.getDonViID(),
                vdonvi_nhan_id: this.$root.token.getDonViID(),
                vhdtb_id: this.hdtb_id,
                vhuonggiao_id: this.hg_phieu_id,
                vmay_cn: this.$root.token.getMaNhanVien(),
                vnguoi_cn: this.$root.token.getMaNhanVien(),
                vnhanvien_id: this.$root.token.getNhanVienID(),
                vphanvung_id: this.$root.token.getPhanVungID(),
                vphieu_id: this.phieu_id,
              }
            );
            this.$toast.success("Hoàn công thành công !");
          }
        }

        // end hoang
        this.HT_DANHSACH();
        // this.$toast.success("Đã hoàn thành phiếu thành công!");
      } catch (err) {
        console.log(err);
      }
    },
    async hoangcong_hopdong_bancheo(in_hdtb_id) {
      try {
        let ds = [];
        let tinh;
        let tinhtc_id = 0;
        let rs = await this.axios.get(
          "web-ccdv/dongbo-visa-vasc/hoancong_thicong_2/" + in_hdtb_id
        );
        ds = rs.data.data;

        if (Number(ds[0]["hdtb_ban"].toString()) > 0) {
          // bên thi công hoàn công update dữ liệu bên bán
          await this.axios.post("web-thicong/danhmuc/hoancong_thicong_v3", {
            vhdtb_ban: ds[0].hdtb_ban,
            vkhid_tc: ds[0].khachhang_tc_id,
            vtbid_tc: ds[0].thuebao_tc_id,
            vtinh_tc: ds[0].tinhban,
            vttid_tc: ds[0].tinhtc,
          });
        } else {
          //bên bán hoàn thiện
          rs = await this.axios.post(
            "web-thicong/danhmuc/sp_lay_thongtin_bancheo",
            {
              vhdtb_id: in_hdtb_id,
            }
          );
          let ds2 = rs.data.data;
          if (ds2.length != 0) {
            //anhnt thêm điều kiện thuebao_id_thicong != -1 cho chỉ giao net
            if (
              ds2[0]["loaihd_id"].toString() ==
                LoaiHopDong.DAT_MOI.toString() &&
              ds2[0]["thuebao_id_thicong"].toString() != "-1"
            ) {
              await this.axios.post("web-thicong/danhmuc/sp_them_db_bancheo", {
                p_dichuvt_id: ds2[0].dichvuvt_id,
                p_js_thongtin: JSON.stringify(ds2),
                p_thuebao_id: ds2[0].thuebao_id_thicong,
                p_thuebao_kn_id: ds2[0].thuebao_id_ban,
                p_tinh_kn_id: s2[0].tinh_ban,
              });
            }
          }
        }
      } catch (err) {
        console.log(err);
      }
    },

    btnPhieuGiaosai_Click() {
      this.TraPhieu();
    },

    async tsbtnGiaoPhieu_Click() {
      let rs = await this.axios.post(
        "web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id",
        { luong_id: this.luong_id }
      );
      let dtTemp = rs.data.data;
      if (dtTemp.length > 0) {
        let data = {
          trangthaiphieu: 1,
          loaihd: 16,
          madv: 9,
          quytrinh: this.quytrinh_id,
          huonggiao: dtTemp[0].huonggiao_id,
          magd: this.$refs.txtMaGD.value,
        };
        console.log(data);
        this.modalTitle = "Giao phiếu điều hành thi công";
        // this.$refs.handoverTicket.showPopup(data);
        // this.$refs.popupHandoverTicket.show();
        this.dialogComponent = () =>
          import(
            "@/modules/contract/setup/HandoverTicket/HandoverTicket_content.vue"
          );
        this.popupComponentName = "popupHandoverTicket";
        await this.dialogComponent().then((x) => {});
        this.$nextTick(() => {
          this.$refs.generalComponent.showPopup(data);
          this.$refs.generalDialog.show();
        });

        // this.$router.push("/contract/handoverticket?magd=" + this.$refs.txtMaGD.value + "&huonggiao_id=" + dtTemp[0].huonggiao_id);
      } else {
        this.$toast.error("Chưa có hướng giao được gán");
      }
    },
    async btnTest() {
      this.dialogComponent = () =>
        import("@/modules/contract/setup/InstallInternet/index.vue");
      this.popupComponentName = "popupInstallInternet";
      this.popupComponentAttr = {
        isPopup: true,
        Tag : "",
        _matb_link: "fiber_11102022",
        _vloaihd_id: 1,
        _vquytrinh_id: 38067
      }
      this.modalTitle = "Hoàn công Internet";
      await this.dialogComponent().then((x) => {
      })
        this.$nextTick(() => {
          this.$refs.generalDialog.show();
        })
    },
    tsbtnChapNhan_Click() {
      this.Capnhat();
    },

    tsbtnGiaoViec_Click() {
      this.GiaoViec();
    },
    //endregion

    //region this.$refs.dtgDanhSach_SelectionChanged
    async dtgDanhSach_SelectionChanged() {
      try {
        if (this.$refs.dtgDanhSach.dataSource.length <= 0) return;
        let dtList = this.$refs.dtgDanhSach.getSelectedRecords();
        if (dtList.length > 0) {
          this.loading(true);
          this.phieu_id = Number(dtList[0]["phieu_id"]);
          this.hdtb_id = Number(dtList[0]["hdtb_id"]);
          this.hdkh_id = Number(dtList[0]["hdkh_id"]);
          this.thuebao_id = Number(dtList[0]["thuebao_id"]);
          this.$refs.cboDichVuVT.value = dtList[0]["dichvuvt_id"];
          this.$refs.txtMaGD.value = dtList[0]["ma_gd"];
          this.$refs.txtMaTB.value = dtList[0]["ma_tb"];
          this.$refs.txtTenTB.value = dtList[0]["ten_tb"];
          this.$refs.txtDiaChiTB.value = dtList[0]["diachi_tb"];
          this.$refs.txtNoiDungTH.value = dtList[0]["nd_thuchien"];

          let daset = [];
          let rs = await this.axios.get(
            "web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls",
            {
              params: { hdtbId: this.hdtb_id },
            }
          );
          daset = rs.data && rs.data.data ? rs.data.data : [];
          this.$refs.cboTocDoMoi.value =
            daset.length > 0 ? Number(daset[0]["tocdo_id"]) : null;
          // hoangpkn : 06/07/2015
          if (daset.length > 0) {
            for (let i = 0; i < daset.length; i++) {
              if (Number(daset[i]["daucuoi_id"]) == 1) {
                //Nếu là điểm đầu
                this.$refs.txtSvlan_Dau.value = daset[i]["vpi"];
                this.$refs.txtCvlan_Dau.value = daset[i]["vci"];
                this.$refs.txtAdsl_cap_Dau.value = daset[i]["port_mdf"];
                this.$refs.txtAdsl_port_Dau.value = daset[i]["adsl_port"];

                if (
                  daset[i]["selfid01"] != "" &&
                  daset[i]["selfid02"] != "" &&
                  daset[i]["selfid03"] != "" &&
                  daset[i]["selfid04"] != ""
                )
                  this.$refs.txtNasport_id_Dau.value = daset[i]["system"]
                    ? daset[i]["system"]
                    : "" + "/" + daset[i]["selfid01"]
                    ? daset[i]["selfid01"]
                    : "" + "/" + daset[i]["selfid02"]
                    ? daset[i]["selfid02"]
                    : "" + "/" + daset[i]["selfid03"]
                    ? daset[i]["selfid03"]
                    : "" + "/" + daset[i]["selfid04"]
                    ? daset[i]["selfid04"]
                    : "";
                else this.$refs.txtNasport_id_Dau.value = daset[i]["system"];

                this.$refs.txtIpDslam_Dau.value = daset[i]["ip"];
                this.$refs.txtVrf_Dau.value = daset[i]["vrf"];
                this.$refs.txtIpwan_Dau.value = daset[i]["ip_wan"];

                this.$refs.txtTimeSlot_Dau.value = daset[i]["timeslot"];
                this.$refs.txtTBi_Dau.value = daset[i]["ten_tbi"];
                this.$refs.txtSoAoDau.value = daset[i]["ma_lt"];
                this.$refs.txtLink_Dau.value = daset[i]["link"];
                this.$refs.txtSerial_Dau.value = daset[i]["serial"];

                // dữ liệu mới
                this.$refs.txtTuyenCap_Dau.value = daset[i]["tuyencap"];
                this.$refs.txtLienTu_Dau.value = daset[i]["lientu"];
                this.$refs.txtTuCapNgon_Dau.value = daset[i]["tucap_p"];
                this.$refs.txtDoiCapNgon_Dau.value = daset[i]["doicap_p"];
                this.$refs.txtHopCapNgon_Dau.value = daset[i]["hopcap_p"];
                if (daset[i]["loaicap_id"] != "")
                  this.$refs.cboLoaiCap_Dau.value = Number(
                    daset[i]["loaicap_id"]
                  );
                this.$refs.txtCapGoc_Dau.value = daset[i]["cap_goc"];
                this.$refs.txtCapNgon_Dau.value = daset[i]["cap_p"];
                this.$refs.txtDoiCapGoc_Dau.value = daset[i]["doicap_goc"];
                this.$refs.txtTuCapGoc_Dau.value = daset[i]["tucap_goc"];
                this.$refs.txtSoMetDay_Dau.value = daset[i]["culy"];
                // end du lieu moi

                this.$refs.cboThietBiDau.value = Number(
                  daset[i]["thietbidc_id"]
                );

                this.$refs.txtDiaChiDau.value = daset[i]["diachi"];

                rs = await this.axios.post(
                  "web-ccdv/khaibaotsl/sp_lay_ds_hdtb_dv_kieudv",
                  {
                    hdtb_id: this.hdtb_id,
                    kieudv_id: 1,
                    loaidv_id: LOAI_DV.TRAM_VT,
                  }
                );
                let dtTemp = rs.data.data;
                if (dtTemp.length > 0) {
                  this.$refs.cboDonViDau.value = dtTemp[0]["donvi_id"];
                }

                //Lấy thông tin liên hệ lắp đặt thuê bao điểm đầu
                rs = await this.axios.post(
                  "web-ccdv/khaibaotsl/sp_lay_thongtin_lh_lapdat_tsl",
                  {
                    daucuoi_id: 1,
                    hdkh_id: this.hdkh_id,
                    hdtb_id: this.hdtb_id,
                  }
                );
                this.$refs.lblLienHe_Dau.innerHtml =
                  "Liên hệ : " + rs.data.data;
                //
              } else {
                this.$refs.txtSvlan_Cuoi.value = daset[i]["vpi"];
                this.$refs.txtCvlan_Cuoi.value = daset[i]["vci"];
                this.$refs.txtAdsl_cap_Cuoi.value = daset[i]["port_mdf"];
                this.$refs.txtAdsl_port_Cuoi.value = daset[i]["adsl_port"];

                if (
                  daset[i]["selfid01"] != "" &&
                  daset[i]["selfid02"] != "" &&
                  daset[i]["selfid03"] != "" &&
                  daset[i]["selfid04"] != ""
                )
                  this.$refs.txtNasport_id_Cuoi.value = daset[i]["system"]
                    ? daset[i]["system"]
                    : "" + "/" + daset[i]["selfid01"]
                    ? daset[i]["selfid01"]
                    : "" + "/" + daset[i]["selfid02"]
                    ? daset[i]["selfid02"]
                    : "" + "/" + daset[i]["selfid03"]
                    ? daset[i]["selfid03"]
                    : "" + "/" + daset[i]["selfid04"]
                    ? daset[i]["selfid04"]
                    : "";
                else txtNasport_id_Cuoi.value = daset[i]["system"];

                this.$refs.txtIpDslam_Cuoi.value = daset[i]["ip"];

                this.$refs.txtVrf_Cuoi.value = daset[i]["vrf"];
                this.$refs.txtIpwan_Cuoi.value = daset[i]["ip_wan"];

                this.$refs.txtTimeSlot_Cuoi.value = daset[i]["timeslot"];
                this.$refs.txtTbi_Cuoi.value = daset[i]["ten_tbi"];
                this.$refs.txtSoAoCuoi.value = daset[i]["ma_lt"];
                this.$refs.txtLink_Cuoi.value = daset[i]["link"];
                this.$refs.txtSerial_Cuoi.value = daset[i]["serial"];

                // dữ liệu mới
                this.$refs.txtTuyenCap_Cuoi.value = daset[i]["tuyencap"];
                this.$refs.txtLienTu_Cuoi.value = daset[i]["lientu"];
                this.$refs.txtTuCapNgon_Cuoi.value = daset[i]["tucap_p"];
                this.$refs.txtDoiCapNgon_Cuoi.value = daset[i]["doicap_p"];
                this.$refs.txtHopCapNgon_Cuoi.value = daset[i]["hopcap_p"];
                if (daset[i]["loaicap_id"] != "")
                  this.$refs.cboLoaiCap_Cuoi.value = Number(
                    daset[i]["loaicap_id"]
                  );
                this.$refs.txtCapGoc_Cuoi.value = daset[i]["cap_goc"];
                this.$refs.txtCapNgon_Cuoi.value = daset[i]["cap_p"];
                this.$refs.txtDoiCapGoc_Cuoi.value = daset[i]["doicap_goc"];
                this.$refs.txtTuCapGoc_Cuoi.value = daset[i]["tucap_goc"];
                this.$refs.txtSoMetDay_Cuoi.value = daset[i]["culy"];
                // end du lieu moi

                this.$refs.cboThietBiCuoi.value = Number(
                  daset[i]["thietbidc_id"]
                );

                this.$refs.txtDiaChiCuoi.value = daset[i]["diachi"];
                rs = await this.axios.post(
                  "web-ccdv/khaibaotsl/sp_lay_ds_hdtb_dv_kieudv",
                  {
                    hdtb_id: this.hdtb_id,
                    kieudv_id: 2,
                    loaidv_id: LOAI_DV.TRAM_VT,
                  }
                );
                let dtTemp = rs.data ? rs.data.data : [];
                if (dtTemp.length > 0) {
                  this.$refs.cboDonViCuoi.value = dtTemp[0]["donvi_id"];
                }

                //Lấy thông tin liên hệ lắp đặt thuê bao điểm đầu
                rs = await this.axios.post(
                  "web-ccdv/khaibaotsl/sp_lay_thongtin_lh_lapdat_tsl",
                  {
                    daucuoi_id: 2,
                    hdkh_id: this.hdkh_id,
                    hdtb_id: this.hdtb_id,
                  }
                );
                this.$refs.lblLienHe_Cuoi.innerHtml =
                  "Liên hệ : " + rs.data.data;
                //
              }
            }

            this.HienThi_Panel();
          }
          // end hoang

          this.$refs.txtNDGiao.value = dtList[0]["nd_giao"];
          let dstra = [];
          rs = await this.axios.post(
            "web-thicong/hths-thaydoi-tocdo/sp_ht_phieu_tra",
            {
              phieu_id: this.phieu_id,
            }
          );
          dstra = rs.data ? rs.data.data : [];
          if (dstra.length > 0)
            this.$refs.txtThongTinTra.value = dstra[0]["nd_tra"];
          else this.$refs.txtThongTinTra.value = "";

          let dsbd = [];
          rs = await this.axios.get(
            "web-hopdong/hopdong/lay_biendong_thongtin_thuebao",
            {
              params: {
                hdtbId: this.hdtb_id,
              },
            }
          );
          dsbd = (rs.data && rs.data.data) ? rs.data.data : [];
          this.$refs.cboTDCu.value = dsbd.length > 0 ? Number(dsbd[0]["tdtsl"]) : "";

          //for (let i = 0; i < daset.length; i++)
          //{
          //    if (Number(daset[i]["daucuoi_id"]) == 1)//Nếu là điểm đầu
          //        txtDiaChiDau.value = (daset[i]["diachi"]);
          //    else
          //        txtDiaChiCuoi.value = (daset[i]["diachi"]);
          //}
          let kt_status = dtList[0]["status"];
          //Nếu phải kích hoạt tổng đài
          if (await this.PHAILAM("KICHHOAT_TD")) {
            if (dtList[0]["ngay_ht_gp"] != "") {
              this.$refs.dtpNgayHT.disabled = false;
              this.$refs.dtpNgayHT.value = this.formatDate(
                dtList[0]["ngay_ht_gp"]
              );
              this.$refs.chkNgayBG.checked = true;

              if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM) {
                if (await this.PHAILAM("GIAOPHIEU")) {
                  this.$refs.tsbtnGiaoPhieu.classList.remove("disabled");
                  this.$refs.tsbtnKichHoat.classList.add("disabled");
                } else {
                  if (await this.PHAILAM("HOANTHANH_PHIEU")) {
                    this.$refs.tsbtnHoanThanh.classList.remove("disabled");
                    this.$refs.tsbtnHoanThienHS.classList.add("disabled");
                  }
                }
                this.$refs.tsbtnKichHoat.classList.add("disabled");
              } else {
                this.$refs.tsbtnGiaoPhieu.classList.add("disabled");
                this.$refs.tsbtnKichHoat.classList.remove("disabled");
                this.$refs.tsbtnHoanThanh.classList.add("disabled");
                this.$refs.tsbtnHoanThienHS.classList.add("disabled");
              }
            } else {
              if (!(await this.PHAILAM("GIAOVIEC"))) {
                this.$refs.chkNgayBG.checked = true;
                this.$refs.dtpNgayHT.disabled = false;
              } else {
                this.$refs.chkNgayBG.checked = false;
                this.$refs.dtpNgayHT.disabled = true;
              }
              this.$refs.dtpNgayHT.value = moment(
                this.ngay_cn,
                "DD/MM/YYYY HH:mm:ss"
              )
                .add(30, "seconds")
                .format("DD/MM/YYYY HH:mm:ss");
              this.$refs.tsbtnGiaoPhieu.classList.add("disabled");
              this.$refs.tsbtnHoanThanh.classList.add("disabled");
              this.$refs.tsbtnKichHoat.classList.add("disabled");
            }
            this.chkNgayBG_CheckedChanged();
            this.chkNgayGV_CheckedChanged();
          } else {
            if (await this.PHAILAM("GIAOPHIEU")) {
              //Nếu phải giao phiếu
              console.log("phải làm GIAOPHIEU");
              if (dtList[0]["ngay_ht_gp"] != "") {
                this.$refs.dtpNgayHT.disabled = false;
                this.$refs.dtpNgayHT.value = this.formatDate(
                  dtList[0]["ngay_ht_gp"]
                );
                this.$refs.chkNgayBG.checked = true;
                this.$refs.tsbtnGiaoPhieu.classList.remove("disabled");
                this.$refs.tsbtnHoanThanh.classList.add("disabled");
              } else {
                if (!(await this.PHAILAM("GIAOVIEC"))) {
                  this.$refs.chkNgayBG.checked = true;
                  this.$refs.dtpNgayHT.disabled = false;
                } else {
                  this.$refs.chkNgayBG.checked = false;
                  this.$refs.dtpNgayHT.disabled = true;
                }
                this.$refs.dtpNgayHT.value = moment(
                  this.ngay_cn,
                  "DD/MM/YYYY HH:mm:ss"
                )
                  .add(30, "seconds")
                  .format("DD/MM/YYYY HH:mm:ss");
                this.$refs.tsbtnGiaoPhieu.classList.add("disabled");
                this.$refs.tsbtnHoanThanh.classList.add("disabled");
              }
            } // Nếu không phải giao phiếu
            else {
              console.log("Không phải giao phiếu");
              if (await this.PHAILAM("HOANTHANH_PHIEU")) {
                //Nếu phải kích hoạt tổng đài
                console.log("phải làm HOANTHANH_PHIEU");
                if (dtList[0]["ngay_ht_gp"] != "") {
                  this.$refs.dtpNgayHT.disabled = false;
                  this.$refs.dtpNgayHT.value = this.formatDate(
                    dtList[0]["ngay_ht_gp"]
                  );
                  this.$refs.chkNgayBG.checked = true;
                  this.$refs.tsbtnHoanThanh.classList.remove("disabled");
                } else {
                  if (!(await this.PHAILAM("GIAOVIEC"))) {
                    this.$refs.chkNgayBG.checked = true;
                    this.$refs.dtpNgayHT.disabled = false;
                  } else {
                    this.$refs.chkNgayBG.checked = false;
                    this.$refs.dtpNgayHT.disabled = true;
                  }
                  this.$refs.dtpNgayHT.value = moment(
                    this.ngay_cn,
                    "DD/MM/YYYY HH:mm:ss"
                  )
                    .add(30, "seconds")
                    .format("DD/MM/YYYY HH:mm:ss");
                  this.$refs.tsbtnHoanThanh.classList.add("disabled");
                }
              }
            }
            this.chkNgayBG_CheckedChanged();
            this.chkNgayGV_CheckedChanged();
          }
        }

        if (await this.PHAILAM("HOANTHIENHOSO")) {
          console.log("HOANTHIENHOSO");
          if (dtList[0]["ngay_ht_gp"] != "") {
            this.$refs.dtpNgayHT.disabled = false;
            this.$refs.dtpNgayHT.value = this.formatDate(
              dtList[0]["ngay_ht_gp"]
            );
            this.$refs.chkNgayBG.checked = true;
            this.$refs.tsbtnHoanThienHS.classList.remove("disabled");
          } else {
            if (!(await this.PHAILAM("GIAOVIEC"))) {
              this.$refs.chkNgayBG.checked = true;
              this.$refs.dtpNgayHT.disabled = false;
            } else {
              this.$refs.chkNgayBG.checked = false;
              this.$refs.dtpNgayHT.disabled = true;
            }
            this.$refs.dtpNgayHT.value = moment(
              this.ngay_cn,
              "DD/MM/YYYY HH:mm:ss"
            )
              .add(30, "seconds")
              .format("DD/MM/YYYY HH:mm:ss");
            this.$refs.tsbtnHoanThienHS.classList.add("disabled");
          }
        }

        if (!(await this.PHAILAM("GIAOVIEC"))) {
          console.log("GIAOVIEC");
          this.$refs.chkNgayBG.checked = true;
          this.$refs.dtpNgayHT.disabled = true;
          this.$refs.chkNgayBG.disabled = true;

          this.$refs.chkNgayGV.checked = true;
          this.$refs.dtpNgayGV.disabled = true;
          this.$refs.chkNgayGV.disabled = true;
        }
        if (this.phieu_id) {
          let rs = await this.axios.post(
            "web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id",
            {
              kieu_id: 2,
              nhanvien_id: this.$root.token.getNhanVienID(),
              phanvung_id: this.$root.token.getPhanVungID(),
              phieu_id: this.phieu_id,
            }
          );
          this.HienThiDSNV(rs.data.data);
        }
        this.chkNgayBG_CheckedChanged();
        this.chkNgayGV_CheckedChanged();
        this.loading(false);
      } catch (err) {
        console.log(err);
      }
    },
    //endregion

    //region KeyPress
    async txtMaGD_KeyPress() {
      let dtList = [];
      let vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.$refs.rdoPhieuMoi.checked) {
        vloai_id = 0;
      } else {
        vloai_id = 1;
      }
      //if (tthd_id == TrangThaiHD.DANG_THI_CONG)
      //    dtList = tchopdong.LAY_DS_HOPDONG_HUONGGIAO(DichVuVienThong.TSL, HuongGiao.THAYDOI_TOCDO_TSL, LoaiHopDong.THAY_DOI_TOCDO_TSL, tthd_id, tt_nd.nhanvien_id, txtMaGD.value);
      if (this.tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
        rs = await this.axios.post(
          "web-ccdv/hths-thaydoi-tocdo/lay_ds_hopdong_huonggiao",
          {
            in_dichvuvt_id: DichVuVienThong.TSL,
            in_huonggiao_id: 0,
            in_loai_id: vloai_id,
            in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_TSL,
            in_ma_tb: this.$refs.txtMaGD.value,
            in_nhanvien_id: this.$root.token.getNhanVienID(),
            in_tthd_id: this.tthd_id,
            phanvung_id: this.$root.token.getPhanVungID(),
          }
        );
        let dtList = rs.data ? rs.data.data : [];
      }
      if (dtList.length > 0) {
        this.$refs.dtgDanhSach.dataSource = dtList;
      } else {
        this.Clear();
        this.$refs.dtgDanhSach.dataSource = null;
      }
    },

    async txtMaTB_KeyPress() {
      let vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.$refs.rdoPhieuMoi.checked) {
        vloai_id = 0;
      } else {
        vloai_id = 1;
      }
      let dtList = [];
      //if (tthd_id == TrangThaiHD.DANG_THI_CONG)
      //    dtList = tchopdong.LAY_DS_HOPDONG_HUONGGIAO(DichVuVienThong.TSL, HuongGiao.THAYDOI_TOCDO_TSL, LoaiHopDong.THAY_DOI_TOCDO_TSL, tthd_id, tt_nd.nhanvien_id, txtMaTB.value);
      if (this.tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
        rs = await this.axios.post(
          "web-ccdv/hths-thaydoi-tocdo/lay_ds_hopdong_huonggiao",
          {
            in_dichvuvt_id: DichVuVienThong.TSL,
            in_huonggiao_id: 0,
            in_loai_id: vloai_id,
            in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_TSL,
            in_ma_tb: this.$refs.txtMaTB.value,
            in_nhanvien_id: this.$root.token.getNhanVienID(),
            in_tthd_id: this.tthd_id,
            phanvung_id: this.$root.token.getPhanVungID(),
          }
        );
        dtList = rs.data ? rs.data.data : [];
      }
      if (dtList.length > 0) {
        this.$refs.dtgDanhSach.dataSource = dtList;
      } else {
        this.Clear();
        this.$refs.dtgDanhSach.dataSource = null;
      }
    },
    //endregion

    chkNgayBG_CheckedChanged() {
      this.$refs.dtpNgayHT.disabled = !this.$refs.chkNgayBG.checked;
    },

    btnLayTTMoi_Click() {
      this.HT_DANHSACH();
    },

    //region Kiểm tra điều kiện hoàn thành
    KiemTraDK_HoanThanh(thangnam_tt, thangnam_sys) {
      let result = false;
      if (
        Number(thangnam_tt.substr(6, thangnam_tt.length - 6)) <
        Number(thangnam_sys.substr(6, thangnam_sys.length - 6))
      )
        //So sánh năm
        return true;
      //Nếu năm thanh toán < năm hoàn thành thì true;
      else if (
        Number(thangnam_tt.substr(6, thangnam_tt.length - 6)) ==
        Number(thangnam_sys.substr(6, thangnam_sys.length - 6))
      ) {
        //So sánh năm
        //Nêu năm = nhau thì kiểm tra tiếp
        //Nếu tháng tt < thang ht -> true
        if (
          Number(thangnam_tt.substr(3, 2)) < Number(thangnam_sys.substr(3, 2))
        )
          result = true;
        else if (
          Number(thangnam_tt.substr(3, 2)) == Number(thangnam_sys.substr(3, 2))
        )
          if (
            Number(thangnam_tt.substr(0, 2)) <=
            Number(thangnam_sys.substr(0, 2))
          )
            //Nếu tháng & năm = nhau thì kiểm tra ngày
            return true;
          else return false;
        else result = false; //tháng tt lớn hơn thang ht
      } else result = false;

      return result;
    },

    KiemTraDK_HoanThanh_1(
      thangnam_tt,
      thangnam_sys //thangnam_sys= ngay_ht
    ) {
      let result = false;
      if (
        Number(thangnam_tt.substr(6, 14).replaceAll("/", "")) <
        Number(thangnam_sys.substr(6, 14).replaceAll("/", ""))
      )
        //So sánh năm
        return true;
      //Nếu năm thanh toán < năm hoàn thành thì true;
      else if (
        Number(thangnam_tt.substr(6, 14).replaceAll("/", "")) ==
        Number(thangnam_sys.substr(6, 14).replaceAll("/", ""))
      ) {
        //So sánh năm
        //Nêu năm = nhau thì kiểm tra tiếp
        //Nếu tháng tt < thang ht -> true
        console.log(thangnam_tt);
        console.log(thangnam_tt.substr(3, 2).replaceAll("/", ""));
        if (
          Number(thangnam_tt.substr(3, 2).replaceAll("/", "")) <
          Number(thangnam_sys.substr(3, 2).replaceAll("/", ""))
        ) {
          result = true;
        } else if (
          Number(thangnam_tt.substr(3, 2).replaceAll("/", "")) ==
          Number(thangnam_sys.substr(3, 2).replaceAll("/", ""))
        ) {
          //Nếu tháng & năm = nhau thì kiểm tra ngày
          console.log("Bằng nhau thôi");
          if (
            Number(thangnam_tt.substr(0, 2).replaceAll("/", "")) <=
            Number(thangnam_sys.substr(0, 2).replaceAll("/", ""))
          ) {
            console.log("adhaksjhdkjashdkja");
            return true;
          } else return false;
        } else result = false; //tháng tt lớn hơn thang ht
      } else result = false;

      return result;
    },
    //endregion

    labelFunctionTitle_Click() {},

    GiaoViec() {
      if (this.$refs.dtgDanhSach.dataSource.length <= 0) {
        this.$toast.error("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }
      if (
        this.$refs.cboNguoiGV.dataSource.length <= 0 ||
        this.$refs.cboNguoiGV.value == ""
      ) {
        this.$toast.error("Hãy nhập nhân viên giao việc!");
        this.$refs.cboNguoiGV.focus();
        return;
      }
      if (!this.$refs.chkNgayGV.checked) {
        this.$toast.error("Hãy nhập ngày giao việc!");
        this.$refs.chkNgayGV.focus();
        return;
      }
      let vnhanvien_giao_id = 0;
      let vngaygiao;
      vnhanvien_giao_id = Number(this.$refs.cboNguoiGV.value);
      vngaygiao = this.$refs.dtpNgayGV.value;

      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(
        this.phieu_id,
        this.$root.token.getDonViID(),
        vnhanvien_giao_id,
        vngaygiao
      );
      this.$refs.frnGiaoPhieuNV.vma_tb = this.$refs.txtMaTB.value;
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load();
        this.$refs.popupfrnGiaoPhieuNV.show();
      });
      // frnGiaoPhieuNV gp = new frnGiaoPhieuNV(phieu_id, tt_nd.donvi_id, vnhanvien_giao_id, vngaygiao);
      // gp.vma_tb = txtMaTB.value.trim();
      // gp.ShowDialog();
      // //Hiển thị danh sách nhân viên
      // let ds = [];
      // ds = tchopdong.LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id, tt_nd.nhanvien_id, 2);
      // HienThiDSNV(ds);
    },
    async giaoviec_success() {
      let rs = await this.axios.post(
        "web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id",
        {
          kieu_id: 2,
          nhanvien_id: this.$root.token.getNhanVienID(),
          phanvung_id: this.$root.token.getPhanVungID(),
          phieu_id: this.phieu_id,
        }
      );
      this.HienThiDSNV(rs.data.data);
    },

    //region HienThiDSNV
    async HienThiDSNV(ds) {
      //lvwNhanVien.Items.Clear();
      let myDataTables = ds;
      if (myDataTables && myDataTables.length > 0) {
        this.$refs.chkNgayGV.checked = true;
        this.$refs.dtpNgayGV.disabled = false;
        this.$refs.dtpNgayGV.value = this.formatDate(ds[0]["ngaygiao"]);
        this.$refs.cboNguoiGV.value = ds[0]["nhanvien_giao_id"];
        this.$refs.dgvNhanVien.dataSource = ds;
      } else {
        this.$refs.dgvNhanVien.dataSource = null;

        if (!(await this.PHAILAM("GIAOVIEC")))
          this.$refs.chkNgayGV.checked = true;
        else this.$refs.chkNgayGV.checked = false;

        this.$refs.dtpNgayGV.value = this.ngay_cn;
        //this.$refs.cboNguoiGV.value = "";
        //this.$refs.cboNguoiGV.SelectedIndex = 0;
        this.$refs.dtpNgayGV.disabled = true;
      }
    },
    //endregion

    async KiemTra_DL() {
      if (this.$refs.dtgDanhSach.dataSource.length <= 0) {
        this.$toast.error("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
        return false;
      }
      if (this.$refs.cboNguoiGV.value == "") {
        this.$toast.error("Hãy nhập người giao việc");
        this.$refs.cboNguoiGV.focus();
        return false;
      }
      if (!this.$refs.chkNgayGV.checked) {
        this.$toast.error("Hãy nhập ngày giao việc!");
        this.$refs.chkNgayGV.focus();
        return false;
      }
      if (!this.$refs.chkNgayBG.checked) {
        this.$toast.error("Hãy nhập ngày hoàn thành!");
        this.$refs.chkNgayBG.focus();
        return false;
      }
      if (await this.PHAILAM("GIAOVIEC")) {
        if (this.$refs.dgvNhanVien.length <= 0) {
          this.$toast.error("Hãy nhập nhân viên thực hiện  !");
          return false;
        }
      }

      //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
      let ngay_sys;
      let ngay_tt;
      let ngay_ht;
      ngay_sys = this.ngay_cn;
      ngay_ht = this.$refs.dtpNgayHT.value;
      // ngay_tt = this.formatDate(dtList[0]["thangnam_tt"]);
      /*if (!KiemTraDK_HoanThanh_1(ngay_tt, ngay_ht))//Nếu ngày thanh toán > ngày hoàn thành
      {
          this.$toast.error("Bạn không được phép chọn ngày hoàn thành nhỏ hơn ngày thanh toán");
          return false;
      }*/
      if (
        Number(
          moment(this.$refs.dtpNgayHT.value, "DD/MM/YYYY HH:mm:ss").format(
            "YYYYMM"
          )
        ) < Number(moment(this.ngay_cn, "DD/MM/YYYY HH:mm:ss").format("YYYYMM"))
      ) {
        this.$toast.error(
          "Tháng hoàn thành không được nhỏ hơn tháng hiện tại !"
        );
        return false;
      }
      if (
        Number(moment(ngay_ht, "DD/MM/YYYY HH:mm:ss").format("YYYYMMDD")) >
        Number(moment(ngay_sys, "DD/MM/YYYY HH:mm:ss").format("YYYYMMDD"))
      ) {
        //Nếu ngày thanh toán > ngày hoàn thành
        this.$toast.error(
          "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại"
        );
        return false;
      }
      let ngay_gv = "";
      ngay_gv = this.formatDate(
        this.$refs.dtpNgayGV.value,
        "DD/MM/YYYY HH:mm:ss",
        "yyyyMM"
      );
      if (
        Number(moment(ngay_gv, "DD/MM/YYYY HH:mm:ss").format("YYYYMMDD")) >
        Number(moment(ngay_sys, "DD/MM/YYYY HH:mm:ss").format("YYYYMMDD"))
      ) {
        //Nếu ngày giao viêc > ngày hiện tại
        this.$toast.error(
          "Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại"
        );
        return false;
      }
      //end
      return true;
    },

    async Capnhat() {
      try {
        let vnhanvien_giao_id = 0;
        let vngaygiao;
        vnhanvien_giao_id = Number(this.$refs.cboNguoiGV.value);
        console.log(this.$refs.dtpNgayHT.value, "this.$refs.dtpNgayHT.value");
        vngaygiao = this.$refs.dtpNgayHT.value
          ? this.$refs.dtpNgayHT.value
          : moment().format("DD/MM/YYYY HH:mm:ss");
        if (!(await this.KiemTra_DL())) return;
        if (!(await this.PHAILAM("GIAOVIEC"))) {
          await this.axios.get(
            "web-ccdv/tiepnhanhopdong/xoa_phieu/" + this.phieu_id
          );
          this.TaoDuLieu_NVTH();
          await this.axios.post(
            "/web-ccdv/tiepnhanhopdong/insert_giaophieu_nv",
            {
              json_giaophieu_nv: JSON.stringify(this.dsnhanviengp),
            }
          );
        }
        if (await this.PHAILAM("UPDATE_DONVI")) {
          await this.axios.post(
            "web-hopdomh/tiepnhanhopdong/update_tramtc_tsl",
            {
              p_donvi_id: this.$root.token.getDonViID(),
              p_hdtb_id: this.hdtb_id,
              p_loaidv_id: 1,
              p_daucuoi: Number(this.$refs.cboDonViDau.value),
            }
          );

          await this.axios.post(
            "web-hopdomh/tiepnhanhopdong/update_tramtc_tsl",
            {
              p_donvi_id: this.$root.token.getDonViID(),
              p_hdtb_id: this.hdtb_id,
              p_loaidv_id: 2,
              p_daucuoi: Number(this.$refs.cboDonViCuoi.value),
            }
          );
          //bangts.Update_TramTC_TSL(hdtb_id, 1, Number(cboDonViDau.value));
          //bangts.Update_TramTC_TSL(hdtb_id, 2, Number(cboDonViCuoi.value));
        }

        await this.axios.post("web-ccdv/khaibaoims/sp_update_tt_giaoviec", {
          nhanvien_hc_id: this.$root.token.getNhanVienID(),
          ngay_ht: vngaygiao,
          hdtb_id: this.hdtb_id,
          phieu_id: this.phieu_id,
          nhanvien_gv_id: this.$refs.cboNguoiGV.value,
        });

        this.HT_DANHSACH();
        //if (inDex_load != 0)
        //{
        //    this.$refs.dtgDanhSach.CurrentCell = this.$refs.dtgDanhSach[inDex_load]["clma_tb"];
        //    this.$refs.dtgDanhSach.CurrentRow.Selected = false;
        //    this.$refs.dtgDanhSach[inDex_load].Selected = true;
        //}
        //this.$toast.error("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");

        this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
        let iphieu_id = this.phieu_id;
        this.dtgDanhSach_SelectionChanged();

        // for (let i = 0; i < this.$refs.dtgDanhSach.dataSource.length; i++)
        // {
        //     if (this.$refs.dtgDanhSach[i]["cl_phieu_id"] == iphieu_id)
        //     {
        //         this.$refs.dtgDanhSach.CurrentCell = this.$refs.dtgDanhSach[i][0];
        //         this.$refs.dtgDanhSach.CurrentRow.Selected = false;
        //         this.$refs.dtgDanhSach[i].Selected = true;
        //         break;
        //     }
        // }
      } catch (err) {
        this.$toast.error("Có lỗi : " + err.message);
        console.log(err);
      }
    },

    TaoDuLieu_NVTH() {
      this.dsnhanviengp = [];
      let row = {};
      row.PHIEU_ID = this.phieu_id;
      row.NHANVIEN_TH_ID = this.$root.token.getNhanVienID();
      row.SO_DT = "";
      row.NHIEMVU = "Hoàn thiện hồ sơ";
      row.GHICHU = "";
      row.NHANVIEN_GIAO_ID = this.$root.token.getNhanVienID();
      row.NGAYGIAO = this.$refs.dtpNgayGV.value;
      this.dsnhanviengp.push(row);
    },

    chkNgayGV_CheckedChanged() {
      this.$refs.dtpNgayGV.disabled = !this.$refs.chkNgayGV.checked;
    },

    TraPhieu() {
      try {
        this.$refs.frmTraPhieuTC.frmTraPhieuTC(
          this.$refs.txtMaGD.value,
          LoaiHopDong.THAY_DOI_TOCDO_TSL,
          DichVuVienThong.TSL,
          this.$root.token.getDonViID(),
          this.$refs.txtMaTB.value,
          "0",
          "0",
          1
        );
        this.$refs.popupFrmTraPhieuTC.show();
        // frmTraPhieuTC frm = new frmTraPhieuTC(txtMaGD.value.trim(), LoaiHopDong.THAY_DOI_TOCDO_TSL, DichVuVienThong.TSL, tt_nd.donvi_id,
        //                                         txtMaTB.value.trim(), "0", "0", 1);
        // frm.ShowDialog();
        // HT_DANHSACH();
      } catch (err) {
        this.$toast.error("" + err.message);
      }
    },

    dtgDanhSach_CellClick() {
      try {
        // inDex_load = this.$refs.dtgDanhSach.CurrentRow.Index;
      } catch (err) {
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    async cboQuyTrinh_SelectedValueChanged() {
      try {
        if (this.$refs.cboQuyTrinh.dataSource.length > 0) {
          if (this.$refs.cboQuyTrinh.value == null) return;
          this.quytrinh_id = Number(this.$refs.cboQuyTrinh.value);

          let table, result, where, groupby;
          table =
            "" +
            DatabaseConstants.DB2 +
            ".huonggiao a," +
            DatabaseConstants.DB2 +
            ".huonggiao_ldv b";
          result = " b.luong_id, a.huonggiao_id, a.huonggiao ";
          where =
            "  a.quytrinh_id = b.quytrinh_id and a.huonggiao_id = b.huonggiao_id ";
          where +=
            "  and a.quytrinh_id = " +
            this.quytrinh_id +
            " and  a.tthd_id = " +
            this.tthd_id;
          groupby = "";
          let ds = [];

          let rs = await this.axios.get(
            "web-hopdong/danhmuc/ds_huonggiao/" +
              this.quytrinh_id +
              "/" +
              this.tthd_id
          );

          ds = rs.data.data;
          if (ds.length > 0) {
            this.luong_id = Number(ds[0]["luong_id"]);
            this.huonggiao_id = Number(ds[0]["huonggiao_id"]);
            let tenhg = ds[0]["huonggiao"];
            this.labelFunctionTitle = tenhg.toUpperCase();
          } else {
            this.$toast.error(
              "Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại"
            );
            this.huonggiao_id = -1;
            if (ktra_load == false) this.HT_DANHSACH();
            return;
          }
          this.HienThiGiaoDien(this.luong_id);
          if (this.ktra_load == false) this.HT_DANHSACH();

          if (!(await this.PHAILAM("GIAOVIEC"))) {
            this.$refs.chkNgayBG.checked = true;
            this.$refs.chkNgayGV.checked = true;
            // this.$refs.dtpNgayHT.value = this.ngay_cn.AddSeconds(30);
            this.$refs.dtpNgayHT.value = this.ngay_cn;
            this.$refs.dtpNgayGV.value = this.ngay_cn;
          }
          this.chkNgayBG_CheckedChanged();
          this.chkNgayGV_CheckedChanged();
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    async HienThiGiaoDien(luong_id) {
      console.log("Hiển thị giao diện");
      this.$refs.tsbtnKQ_DoKiem.style.display = "none";
      this.$refs.tsbtnDuAn.style.display = "none";
      this.$refs.tsbtnHoanThienHS.style.display = "none";
      this.$refs.tsbtnGiaoPhieu.style.display = "none";
      this.$refs.tsbtnHoanThanh.style.display = "none";
      this.$refs.tsbtnGiaoViec.style.display = "none";
      this.$refs.tsbtnKichHoat.style.display = "none";
      this.$refs.tsbtnKichHoat.classList.add("disabled");
      try {
        let rs = await this.axios.post(
          "web-thicong/hths-thaydoi-tocdo/sp_lay_thaotac",
          {
            luong_id: luong_id,
          }
        );

        this.dtThaoTac = rs.data.data;
        if (this.dtThaoTac.length > 0) {
          for (let i = 0; i < this.dtThaoTac.length; i++) {
            rs = await this.axios.post(
              "web-thicong/hths-thaydoi-tocdo/sp_lay_dulieu_thaotac_control_theo_thaotac_id",
              {
                thaotac_id: this.dtThaoTac[i]["thaotac_id"],
              }
            );
            this.dtControl = rs.data.data;
            if (this.dtControl.length > 0) {
              for (let j = 0; j < this.dtControl.length; j++) {
                let control_name = this.dtControl[j]["control_name"];
                console.log(this.dtControl[j]["control_name"]);
                let ctrl = this.$refs[control_name];
                if (ctrl) {
                  switch (ctrl.nodeName) {
                    case "BUTTON":
                      if (this.dtThaoTac[i].enable == "1")
                        ctrl.disabled = false;
                      else ctrl.disabled = true;
                      break;
                    case "INPUT":
                      if (this.dtThaoTac[i].enable == "1") {
                        ctrl.readOnly = false;
                        ctrl.style.background = "#FFF9EB";
                        ctrl.style.color = "brow";
                      } else ctrl.readOnly = true;
                      break;
                    case "A":
                      if (this.dtThaoTac[i].enable == "1") {
                        ctrl.style.display = null;
                      } else ctrl.style.display = "none";
                      break;
                    default:
                      break;
                  }
                } else {
                  console.log(control_name);
                }
              }
            }
          }
        }
      } catch (err) {
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    // PHAILAM(CODE) {
    //   let dr = this.dtThaoTac.filter(item => item.ENABLE == 1 && item.CODE == CODE);
    //   if (dr.length > 0)
    //     return true;
    //   return false;
    // },

    async cboDichVuVT_SelectedValueChanged() {
      return new Promise(async (resolve, reject) => {
        if (this.$refs.cboDichVuVT.dataSource.length > 0) {
          if (!this.$refs.cboDichVuVT.value) return;
          this.dichvuvt_id = Number(this.$refs.cboDichVuVT.value);
          let rs = await this.axios.post(
            "web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2",
            {
              dsloaihd_id: this.dsloaihd_id,
              dichvuvt_id: this.dichvuvt_id,
              tthd_id: this.tthd_id,
              kieu: 0,
            }
          );
          let ds = [];
          ds = rs.data.data;
          if (ds.length > 0) {
            this.$refs.cboQuyTrinh.dataSource = ds;
            this.$refs.cboQuyTrinh.dataValueField = "quytrinh_id";
            this.$refs.cboQuyTrinh.dataTextField = "quytrinh";
            if (this._vquytrinh_id)
              this.$refs.cboQuyTrinh.value = this._vquytrinh_id;
            else
              this.$refs.cboQuyTrinh.value =
                this.$refs.cboQuyTrinh.dataSource[0].quytrinh_id;
            this.cboQuyTrinh_SelectedValueChanged();
          }
        }
        resolve(true);
      });
    },

    async tsbtnKichHoat_Click() {
      let rs;
      this.axios.post("web-hopdong/hopdong/capnhat_status_hopdong_thuebao", {
        hdtbId: this.hdtb_id,
        status: TRANGTHAI_DONGBO.DONGBO_CM,
      });
      if (await this.PHAILAM("CAPNHAT_NGAYHT_KH")) {
        let thangsau = moment(new Date()).add(1, "months").format("yyyyMM");
        rs = await this.axios.post(
          "web-hopdong/dongbo_tb_codinh_lentongdai/kt_dbthang_dachot",
          {
            vngay_cn: thangsau,
          }
        );
        let dbt = rs.data.data;
        if (dbt != "0") {
          this.$toast.error(dbt);
          return;
        }
        await this.axios.post("/web-hopdong/hopdong/fn_capnhat_ngayht", {
          hdtb_id: this.hdtb_id,
          ngay_ht: this.ngay_cn,
        });
      }
      if (await this.PHAILAM("CAPNHAT_NGAYKH_KH")) {
        await this.axios.post("/web-hopdong/hopdong/fn_capnhat_ngayht", {
          hdtb_id: this.hdtb_id,
          ngay_ht: this.ngay_cn,
        });
      }
      this.$toast.success("Kích hoạt thành công!");
      this.HT_DANHSACH();
    },

    rdoPhieuMoi_CheckedChanged() {
      this.HT_DANHSACH();
    },
    rdoPhieuTra_CheckedChanged() {
      this.HT_DANHSACH();
    },
    rowClicked(index, data) {
      this.current_row = index;
    },

    async btnNDTH_Click() {
      try {
        if (this.phieu_id == 0) return;
        await this.axios.post(
          "web-thicong/khoiphuccong/sp_update_noidung_thuchien",
          {
            nd_thuchien: this.$refs.txtNoiDungTH.value.trim(),
            phieu_id: this.phieu_id,
          }
        );
        this.$toast.success("Cập nhật thành công!");
      } catch (err) {
        this.$toast.error("Có lỗi : " + err.message);
      }
    },

    HienThi_Panel() {
      this.$refs.txtLink_Cuoi.readOnly = true;
      this.$refs.txtLink_Dau.readOnly = true;
      this.$refs.txtSerial_Dau.readOnly = true;
      this.$refs.txtSerial_Cuoi.readOnly = true;
      //txtPort_Cn.readOnly = true;
      //txtSlot_Cn.readOnly = true;
      //txtTimeSlot_Cn.readOnly = true;
      //txtMapping_Cn.readOnly = true;

      this.$refs.txtTuCapGoc_Dau.readOnly = true;
      this.$refs.txtDoiCapGoc_Dau.readOnly = true;
      this.$refs.txtCapGoc_Dau.readOnly = true;
      this.$refs.txtHopCapNgon_Dau.readOnly = true;
      this.$refs.txtTuCapNgon_Dau.readOnly = true;
      this.$refs.txtDoiCapNgon_Dau.readOnly = true;
      this.$refs.txtCapNgon_Dau.readOnly = true;
      this.$refs.txtTuyenCap_Dau.readOnly = true;
      this.$refs.txtLienTu_Dau.readOnly = true;
      this.$refs.txtSoMetDay_Dau.readOnly = true;

      this.$refs.txtSvlan_Dau.readOnly = true;
      this.$refs.txtCvlan_Dau.readOnly = true;
      this.$refs.txtNasport_id_Dau.readOnly = true;
      this.$refs.txtIpDslam_Dau.readOnly = true;
      this.$refs.txtAdsl_cap_Dau.readOnly = true;
      this.$refs.txtAdsl_port_Dau.readOnly = true;
      this.$refs.txtVrf_Dau.readOnly = true;
      this.$refs.txtIpwan_Dau.readOnly = true;

      this.$refs.txtTimeSlot_Dau.readOnly = true;
      this.$refs.txtTBi_Dau.readOnly = true;

      //Panel cuối
      this.$refs.txtTuCapGoc_Cuoi.readOnly = true;
      this.$refs.txtDoiCapGoc_Cuoi.readOnly = true;
      this.$refs.txtCapGoc_Cuoi.readOnly = true;
      this.$refs.txtHopCapNgon_Cuoi.readOnly = true;
      this.$refs.txtTuCapNgon_Cuoi.readOnly = true;
      this.$refs.txtDoiCapNgon_Cuoi.readOnly = true;
      this.$refs.txtCapNgon_Cuoi.readOnly = true;
      this.$refs.txtTuyenCap_Cuoi.readOnly = true;
      this.$refs.txtLienTu_Cuoi.readOnly = true;
      this.$refs.txtSoMetDay_Cuoi.readOnly = true;

      this.$refs.txtSvlan_Cuoi.readOnly = true;
      this.$refs.txtCvlan_Cuoi.readOnly = true;
      this.$refs.txtNasport_id_Cuoi.readOnly = true;
      this.$refs.txtIpDslam_Cuoi.readOnly = true;
      this.$refs.txtAdsl_cap_Cuoi.readOnly = true;
      this.$refs.txtAdsl_port_Cuoi.readOnly = true;
      this.$refs.txtVrf_Cuoi.readOnly = true;
      this.$refs.txtIpwan_Cuoi.readOnly = true;

      this.$refs.txtTimeSlot_Cuoi.readOnly = true;
      this.$refs.txtTbi_Cuoi.readOnly = true;
    },

    //nhapt 03072015 Thực hiện thêm phiếu in
    btnBienBanNT_Click() {
      try {
        this.InPhieu();
        this.HT_DANHSACH(); //nhapt thêm 18/06/2015
      } catch (err) {
        console.log(err);
      }
    },
    //region In Phiếu NT

    async InPhieu() {
      try {
        let rs = await this.axios.post(
          "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao",
          { param: this.hdtb_id, type: 1 }
        );
        let vhdkh_id = rs.data.data;

        if (vhdkh_id == "-1") {
          this.$toast.error(
            "Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!"
          );
          return;
        }

        // this.modelIn.nvth_id = this.nhanvien_tc_id
        // this.modelIn.cv_thuchien = this.congviec_th
        // this.modelIn.hdkh_id = this.hdkh_id
        // this.modelIn.phieu_id = this.phieu_id
        // this.modelIn.hdtb_id = this.hdtb_id
        // this.modelIn.huonggiao_id = this.huonggiao_id
        // this.modelIn.tentram_tb = ""
        let nhdkhid = [];
        let nhdtbid = [];
        let nphieuid = [];
        nhdkhid = this.$refs.dtgDanhSach
          .getSelectedRecords()
          .map((item) => item.hdkh_id);
        nhdtbid = this.$refs.dtgDanhSach
          .getSelectedRecords()
          .map((item) => item.hdtb_id);
        nphieuid = this.$refs.dtgDanhSach
          .getSelectedRecords()
          .map((item) => item.phieu_id);
        this.modelIn.n_phieu_id = nphieuid.join(",");
        this.modelIn.ma_gd = this.$refs.txtMaGD.value.trim();
        this.modelIn.n_hdkh_id = nhdkhid.join(",");
        this.modelIn.n_hdtb_id = nhdtbid.join(",");
        this.modelIn.nvth_id = this.nhanvien_tc_id;
        this.modelIn.huonggiao_id = this.huonggiao_id;
        this.modelIn.tentram_tb = "";
        this.$refs.popupInBB.showModal();
      } catch (err) {
        this.$toast.error("" + err.message);
      }
    },
    //endregion
    HienThiAnhTrangThai() {
      this.$refs.dtgDanhSach.dataSource.forEach((dr) => {
        //Thực hiện hiển thị các phiếu đã in
        if (dr["cl_luu_in"] != "") {
          // ((TextAndImageCell)dr["in_status"]).Image = lblDaIn.Image;
          dr["in_status"] = "Đã in";
        }
        //if (new TCHopDongFacade().KIEM_TRA_LUU_BIEN_BAN(Number(dr["vphieu_id"].value)).length > 0)
        //{
        //    ((TextAndImageCell)dr["in_status"]).Image = lblDaIn.Image;
        //    ((TextAndImageCell)dr["in_status"]).value = "Đã in";

        //}
      });
    },

    dtgDanhSach_RowsAdded() {
      this.HienThiAnhTrangThai();
    },

    dtgDanhSach_Sorted() {
      this.HienThiAnhTrangThai();
    },

    tsbtnDuAn_Click() {
      try {
        this.$refs.popupDuAnHDTB.showModal();
        // frmDuAn_HDTB f_duan = new frmDuAn_HDTB();
        // f_duan.vhdtb_id = hdtb_id;
        // f_duan.ShowDialog();
      } catch (err) {
        this.$toast.error("" + err.message);
      }
    },

    tsbtnKQ_DoKiem_Click() {
      if (this.hdtb_id != 0) {
        this.$refs.doKiem.showModal();
      } else {
        this.$toast.error("Bạn chưa chọn phiếu!");
      }
    },
  },
};
</script>
<style>
a.disabled {
  color: #d3d3d3 !important;
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.6;
}
</style>
<style scoped>
input.form-control {
  text-align: right;
}

input.form-control.left {
  text-align: left;
}

.list-actions-top .list {
  overflow: visible !important;
}

.list-actions-top a,
.list-actions-top li {
  cursor: pointer !important;
}

input[type="text"]:disabled.highlight {
  background-color: #fff9eb !important;
}
</style>