<template src="./DongBoLapMoiVina.html"></template>

<style>
 .model-css{
      min-height: 700px !important;
  }
</style>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import gridview from "@/components/Shared/gridview";
import uploadEProfile from "@/modules/contract/profile/UploadEProfile";
import giaophieunhanvien from "../../setup/DeclareLandline/popup/giaophieunhanvien";
import ModalChonDiaChi from "../../setup/InstallNewSubs/Popups/popupChonDiaChi";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import ThongTinLienHe from "../../setup/ChangeContractOwner/popup/ThongTinLienHe.vue";
import traphieu from "../../setup/ReturnInstallTicket/components/traphieu.vue";
import ChuyenTo from "../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";
import TonPhieu from "../../setup/Payment/components/TonPhieu.vue";
import SearchEProfile from "../../../search/subscriber/SearchEProfile";
import VatTuThueBao from "@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue";
import PopupThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
import api from "./api";
import fn from "./functions";
import ccbs from "./ccbsFunctions";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import GuiCatMoModal from "./popups/popupGuiCatMo/GuiCatMoModal.vue";
import ChonAnhHoSoModal from "./popups/popupChonAnhHoSo/ChonAnhHoSoModal.vue";
import UploadAnhModal from "./popups/popupUploadAnh/UploadAnhModal.vue";
import UpdatePostPaid from "@/modules/contract/setup/UpdatePostPaid/UpdatePostPaid.vue";


import {
  LOAI_HOPDONG,
  DICHVU_VIENTHONG,
  TRANGTHAI_HOPDONG,
  LOAIHINH_THUEBAO,
  TRANGTHAI_DONGBO,
  KIEU_LAPDAT,
  DICHVU_GIATANG
} from "@/constants";
import { KIEU_HD, LoaiHopDong, LoaiHinhTB, KieuLapDat } from "@/const/enums";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
import { required, minValue, maxValue } from "vuelidate/lib/validators";

//
export default {
  components: {
    breadcrumb,
    gridview,
    uploadEProfile,
    giaophieunhanvien,
    ModalChonDiaChi,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
    ThongTinLienHe,
    traphieu,
    ChuyenTo,
    TonPhieu,
    SearchEProfile,
    VatTuThueBao,
    PopupThongTinHenKhachHang,
    GuiCatMoModal,
    ChonAnhHoSoModal,
    UpdatePostPaid,
    UploadAnhModal
  },
  name: "CompleteProfile",
  props: ["Tag", "isPopup"],
  validations: {
    diachiLD: {
      DIACHI: {
        required,
      },
    },
  },
  mounted() {
    if(!this.isPopup || this.isPopup == false)
      this.pageLoad();
  },

  data() {
    // WinUI.WinUILapHopDong.frmDongBoLapMoiVina,PTTB.exe

    return {
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close':this.popupClosed,
        'acceptChangeCTV':this.popupClosed,
        'acceptChangeNGT':this.popupClosed,
        'PopUpCongTacVien_Thoat':this.popupClosed,
      },
      header: {
        title: 'OMC/Vip/Tổ CM- Thay đổi dịch vụ',
        list: [
          {name: 'Lập hợp đồng/Đổi tốc độ', link: {name: 'Ui.cards'}},
          {
            name: 'Loại hình thuê bao/OMC/Vip/Tổ CM- Thay đổi dịch vụ',
            link: {name: 'Ui.buttons'}
          }
        ]
      },

      selectionOptions: { type: "Multiple" },
      target: "#app",
      dateFormat: "DD/MM/YYYY",
      datetimeFormat: "DD/MM/YYYY HH:mm",
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: "",
        ip_cn: "",
        ngay_cn: moment(new Date()).format("DD/MM/YYYY"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY"
        ),
      },

      state: {
        selectedPhieu: {
          phieu_id: 0,
          ma_tb: ""
        },
        OPEN_SYNC_VINA_TRASAU: true,
      },
      radioStatus: "rdoChuaKH",
      controls: {
        /* #region */
        tsbtnDongBoHD: {
          visible: false,
          enabled: true,
          type: "menu_button",
        },
        tsbtnUploadAnh: {
          visible: true,
          enabled: false,
          type: "menu_button",
        },
        tsbtnHoanThien: {
          visible: true,
          enabled: false,
          type: "menu_button",
        },
        cboLoaiHD: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },
        cboLoaiTB: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },
        cboKieuLD: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },
        rdoCatMo: {
          visible: false,
          enabled: true,
          type: "radio",
        },
        gridviewThueBao: {
          headers: [
            {
              fieldName: 'ma_tb',
              headerText: 'Số máy',
              allowFiltering: true,
              allowSorting: false,
              width: 100,
            },
            {
              fieldName: 'simcard',
              headerText: 'SimCard',
              allowFiltering: true,
              allowSorting: true,
              width: 100,
            },
            {
              fieldName: 'ten_tb',
              headerText: 'Tên thuê bao',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'diachi_tb',
              headerText: 'Địa chỉ thuê bao',
              allowFiltering: true,
              allowSorting: true,
              width: 400,
            },
            {
              fieldName: 'loaihinh_tb',
              headerText: 'Loại hình',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'trangthai_tb',
              headerText: 'Trạng thái',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'goi_cuoc',
              headerText: 'Gói cước',
              allowFiltering: true,
              allowSorting: true
            },
          ],
          dataSource: [],
          type: "gridview",
        },
        grcDichVuGT: {
          headers: [
            {
              fieldName: 'ten_dvgt',
              headerText: 'Tên dịch vụ',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ma_dvgt',
              headerText: 'Mã dịch vụ',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ten_kieuyc',
              headerText: 'Kiểu Y/C',
              allowFiltering: true,
              allowSorting: true
            },
          ],
          dataSource: [],
          type: "gridview",
        },
        /* #endregion */
      },

      dtAnhThueBao: [],
      dtAnhHopDong_CaNhan: [],
      dtAnhHopDong_DoanhNghiep: [],
      dtAnhThueBao_HopDong_CaNhan: [],
      dtAnhThueBao_HopDong_DoanhNghiep: [],
    };
  },
  computed: {
    rdoCatMo_Checked: {
      get() {
        return this.radioStatus == "rdoCatMo" ? true : false;
      },
    },
    rdoChuaKH_Checked: {
      get() {
        return this.radioStatus == "rdoChuaKH" ? true : false;
      },
    },
    rdoDaKH_Checked: {
      get() {
        return this.radioStatus == "rdoDaKH" ? true : false;
      },
    },
    CurrentLoaiHD_ID: {
      get() {
        //if (!this.controls.cboLoaiHD.value == null) return -1;
        return this.controls.cboLoaiHD.value * 1;
      },
    },
    CurrentLoaiTB_ID: {
      get() {
        //if (!this.controls.cboLoaiTB.value == null) return -1;
        return this.controls.cboLoaiTB.value * 1;
      },
    },
    CurrentKieuLD_ID: {
      /* #region  */
      get() {
        //if (!this.controls.cboKieuLD.value == null) return -1;
        return this.controls.cboKieuLD.value * 1;
      },
      /* #endregion */
    },
    CurrentTTHD_ID: {
      /* #region CurrentTTHD_ID */
      get() {
        let loaiHD = this.controls.cboLoaiHD.value;
        let loaiTB = this.controls.cboLoaiTB.value;
        console.log("loaiHD: ", loaiHD);
        console.log("loaiTB: ", loaiTB);
        if (this.rdoChuaKH_Checked) {
          if (
            loaiHD &&
            (loaiHD == LOAI_HOPDONG.CHAMDUT_SD ||
              loaiHD == LOAI_HOPDONG.CHUYENDOI_LH)
          )
            // vuth update 15/12 thêm cdlh
            return TRANGTHAI_HOPDONG.MOI;
          else
            return (loaiTB == LOAIHINH_THUEBAO.DIDONGTRATRUOC &&
              loaiHD == LOAI_HOPDONG.DAT_MOI) ||
              (loaiTB == LOAIHINH_THUEBAO.DIDONGTRASAU &&
                loaiHD == LOAI_HOPDONG.CHUYEN_QUYEN) ||
              (loaiTB == LOAIHINH_THUEBAO.DIDONGTRASAU &&
                loaiHD == LOAI_HOPDONG.TACHNHAP_TB)
              ? TRANGTHAI_HOPDONG.DA_THI_CONG_XONG
              : TRANGTHAI_HOPDONG.THANH_TOAN;
        }
        return (loaiTB == LOAIHINH_THUEBAO.DIDONGTRATRUOC &&
          loaiHD == LOAI_HOPDONG.DAT_MOI) ||
          (loaiTB == LOAIHINH_THUEBAO.DIDONGTRASAU &&
            (loaiHD == LOAI_HOPDONG.CHUYEN_QUYEN ||
              loaiHD == LOAI_HOPDONG.TACHNHAP_TB))
          ? TRANGTHAI_HOPDONG.DA_THI_CONG_XONG
          : TRANGTHAI_HOPDONG.THANH_TOAN;
      },
      /* #endregion */
    },
    CurrentStatus: {
      /* #region CurrentStatus */
      get() {
        console.log('this.CurrentStatus');
        console.log('this.CurrentLoaiHD_ID', this.CurrentLoaiHD_ID);
        console.log('this.CurrentKieuLD_ID', this.CurrentKieuLD_ID);

        if (this.rdoChuaKH_Checked) {
          if (this.CurrentLoaiTB_ID == LOAIHINH_THUEBAO.DIDONGTRASAU) {
            if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.CHAMDUT_SD /*4*/ || this.CurrentLoaiHD_ID == LOAI_HOPDONG.CHUYENDOI_LH /*6*/)
              // vuth update 15/12 thêm cdlh
              return TRANGTHAI_DONGBO.DONGBO_LHD;
            else if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.DAT_MOI) {
              if (this.CurrentKieuLD_ID == KIEU_LAPDAT.DM_DIDONG_CHUYEN_TINH /*217*/)
                return TRANGTHAI_DONGBO.DONGBO_LHD;
              else
                return TRANGTHAI_DONGBO.DONGBO_CM;
            }
            //Nếu là hđ hòa mạng mới thì lấy các thuê bao đã gửi cắt mở
            else if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.CHUYEN_QUYEN || this.CurrentLoaiHD_ID == LOAI_HOPDONG.TACHNHAP_TB)
              // vuth update 19/12
              return TRANGTHAI_DONGBO.DONGBO_TT;
              // vuth update 22/10/2019
            else
              return TRANGTHAI_DONGBO.CHUADONGBO;
          }
          else
            return TRANGTHAI_DONGBO.CHUADONGBO;
        } else if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.CHUYENDOI_LH && this.CurrentLoaiTB_ID == LOAIHINH_THUEBAO.DIDONGTRASAU) {
          // vuth update 16/12
          return TRANGTHAI_DONGBO.DONGBO_HT;
        }
        return TRANGTHAI_DONGBO.DONGBO_SERVICE;
      },
      /* #endregion */
    },
  },
  validations: {},
  methods: {
    async pageLoad() {
      try{
        this.loading(true)

        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
        let thongtin_nguoidung2 = await commonFn.thongtin_nguoidung2(this.axios);
        Object.assign(this.tt_nd, thongtin_nguoidung2);

        console.log("tt_nd", this.tt_nd);
        this.state.OPEN_SYNC_VINA_TRASAU = await fn.KIEMTRA_OPEN_SYNC_VINA(this, "OPEN_SYNC_VINA_TRASAU");
        console.log("OPEN_SYNC_VINA_TRASAU", this.state.OPEN_SYNC_VINA_TRASAU);

        //====cboLoaiHD
        /* #region cboLoaiHD */
        let dsLoaihd = [
          LOAI_HOPDONG.DAT_MOI,
          LOAI_HOPDONG.CHUYEN_QUYEN,
          LOAI_HOPDONG.TACHNHAP_TB,
          LOAI_HOPDONG.THAY_DOI_DV,
          LOAI_HOPDONG.CHAMDUT_SD,
          LOAI_HOPDONG.CHUYENDOI_LH,
        ];

        this.controls.cboLoaiHD.list = (await fn.CSS_LOAI_HD(this.axios))
          .filter(function (item) {
            return dsLoaihd.includes(item.LOAIHD_ID * 1);
          })
          .map((x) => ({
            id: x.LOAIHD_ID,
            text: x.TEN_LOAIHD,
          }));

        if (
          this.controls.cboLoaiHD.list &&
          this.controls.cboLoaiHD.list.length > 0
        ) {
          this.controls.cboLoaiHD.value = this.controls.cboLoaiHD.list[0].id;
        }
        /* #endregion  */

        //====cboLoaiTB
        /* #region cboLoaiTB */
        this.controls.cboLoaiTB.list = (await fn.CSS_LOAIHINH_TB(this.axios))
          .filter(function (item) {
            return item.DICHVUVT_ID == DICHVU_VIENTHONG.DI_DONG;
          })
          .map((x) => ({
            id: x.LOAITB_ID,
            text: x.LOAIHINH_TB,
          }));

        if (
          this.controls.cboLoaiTB.list &&
          this.controls.cboLoaiTB.list.length > 0
        ) {
          this.controls.cboLoaiTB.value = this.controls.cboLoaiTB.list[0].id;
        }
        /* #endregion */
        await this.cboLoaiHD_EditValueChanged()

        // await this.Lay_DuLieu_HDTB(
        //   this.CurrentLoaiHD_ID,
        //   this.CurrentTTHD_ID,
        //   this.CurrentStatus,
        //   this.CurrentLoaiTB_ID
        // );

        this.controls.tsbtnDongBoHD.enabled = this.rdoChuaKH_Checked;
        await this.layDS_Anh_CCBS();
      }catch(ex){
        console.error(ex);
        commonFn.showException(this, ex);
      }
      this.loading(false);
    },
    async cboLoaiHD_EditValueChanged() {
      console.log('cboLoaiHD_EditValueChanged');
      await this.cboLoaiTB_EditValueChanged();
    },
    async cboLoaiTB_EditValueChanged() {
      try{
        this.loading(true)
        console.log('cboLoaiTB_EditValueChanged');
        let data = await commonFn.LAY_DS_KIEU_LD(this.axios, this.CurrentLoaiHD_ID, this.CurrentLoaiTB_ID);
        if(data && data.length > 0){
          this.controls.cboKieuLD.list = data.map((x) => ({
            id: x.kieuld_id,
            text: x.ten_kieuld,
          }));
          this.controls.cboKieuLD.value = this.controls.cboKieuLD.list[0].id;
        }
        if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.THAY_DOI_DV)
            this.controls.cboKieuLD.enabled = false;
        else
            this.controls.cboKieuLD.enabled = true;

        if (this.controls.cboLoaiTB.value*1 == LOAIHINH_THUEBAO.DIDONGTRATRUOC*1 && this.controls.cboLoaiHD.value*1 == LOAI_HOPDONG.DAT_MOI*1) // vuth update fix 1909
        {
            this.controls.tsbtnDongBoHD.visible = false;
            this.controls.tsbtnUploadAnh.visible = false;
            this.controls.tsbtnHoanThien.enabled = true;

        }
        else
        {
            this.controls.tsbtnDongBoHD.visible = true;
            this.controls.tsbtnUploadAnh.visible = true;
            this.controls.tsbtnHoanThien.enabled = true;
        }

        await this.dtKieuLD_EditValueChanged();

      }catch(ex){
        console.error(ex);
        commonFn.showException(this, ex);
      }
      this.loading(false)

    },
    async dtKieuLD_EditValueChanged() {
      this.controls.rdoCatMo.visible = this.CurrentLoaiTB_ID == LOAIHINH_THUEBAO.DIDONGTRASAU && this.CurrentLoaiHD_ID == LOAI_HOPDONG.DAT_MOI && this.CurrentKieuLD_ID != KIEU_LAPDAT.DM_DIDONG_CHUYEN_TINH;
      await this.Lay_DuLieu_HDTB(this.CurrentLoaiHD_ID, this.CurrentTTHD_ID, this.CurrentStatus, this.CurrentLoaiTB_ID);
    },
    async Lay_DuLieu_HDTB(loaihdId, tthdId, status, loaitb_id) {
      console.log("this.CurrentLoaiHD_ID", this.CurrentLoaiHD_ID);
      console.log("this.CurrentTTHD_ID", this.CurrentTTHD_ID);
      console.log("this.CurrentStatus", this.CurrentStatus);
      console.log("this.CurrentLoaiTB_ID", this.CurrentLoaiTB_ID);

      this.loading(true)
      let loaitb_id_in_hd =
        loaitb_id == LOAIHINH_THUEBAO.DIDONGTRASAU &&
        loaihdId == LOAI_HOPDONG.CHUYENDOI_LH
          ? LOAIHINH_THUEBAO.DIDONGTRATRUOC
          : loaitb_id;
      this.controls.gridviewThueBao.dataSource = await fn.LAY_DS_HDTB_DIDONG(
        this.axios,
        loaihdId,
        status,
        tthdId,
        loaitb_id_in_hd,
        this.tt_nd.nhanvien_id,
        this.tt_nd.phanvung_id
      );
      this.loading(false)

    },
    async tsbtnCapNhat_Click() {
      var grid = this.$refs.gridviewThueBao;
      var selectedRows = grid.getSelectedRecords();
      if(!selectedRows || selectedRows.length == 0){
        this.$toast.warning("Hãy tick chọn danh sách phiếu cần đồng bộ.");
        return;
      }
      if (!this.tt_nd.user_neo || this.tt_nd.user_neo == "")
      {
          this.$toast.error("Người dùng chưa được gán user tác động hệ thống CCBS.");
          return;
      }
      let req = {};
      let ssCount = 0;
      let checkCount = 0
      this.loading(true);

      try{

        //#region
        if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.DAT_MOI)
        {
          let i=0;
          for(let i =0; i < selectedRows.length; i++) {
            let row = selectedRows[i];
            console.log(row);
            let hdtb_id = Number(row.hdtb_id);
            let somay_dd = row.ma_tb;
            checkCount++;

            if (this.CurrentLoaiTB_ID == LOAIHINH_THUEBAO.DIDONGTRATRUOC){
              if (!await this.DongBoAnhChup(hdtb_id, somay_dd)) return;  //continue;
            }

            if (!await fn.checkDaUploadAnhTraSau(this.axios, hdtb_id))
            {
                this.$toast.warning("Phải upload ảnh cho thuê bao trước khi thực hiện đồng bộ CCBS");
                break;
            }

            req = {
              "hdtb_id": hdtb_id,
              "ip_cn": this.tt_nd.ip_cn,
              "loaihd_id": this.CurrentLoaiHD_ID,
              "ma_tb": somay_dd,
              "nguoi_cn": this.tt_nd.nguoi_cn,
              "phanvung_id": this.tt_nd.phanvung_id,
              "step": ""
            }

            if (await fn.checkMNP(this.axios, hdtb_id.toString(), LOAIHINH_THUEBAO.DIDONGTRASAU))
            {
                req.step = "MNP"
                await fn.fn_frmdongbovina_capnhat(this.axios, req);
                return; //continue;
            }

            if (await this.DongBoThaydoiDichVu(hdtb_id, somay_dd))
            {
              if(await this.DongBoDatMoi(row))
              {
                await fn.fn_frmdongbovina_capnhat(this.axios, req);
                req = {
                  "hdtb_id": hdtb_id,
                  "status": TRANGTHAI_DONGBO.DONGBO_SERVICE
                }
                this.GetData(await api.capnhat_ngaykh_ngay_ht_v2(this.axios, req))
                ssCount++;
              }
              i++;
            }
          }
        }
        //#endregion

        //#region
        if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.THAY_DOI_DV)
        {
          for(let i =0; i < selectedRows.length; i++) {
            let row = selectedRows[i];
            let hdtb_id = Number(row.hdtb_id);
            let somay_dd = row.ma_tb;
            checkCount++;

            req = {
              "hdtb_id": hdtb_id,
              "ip_cn": this.tt_nd.ip_cn,
              "loaihd_id": this.CurrentLoaiHD_ID,
              "ma_tb": somay_dd,
              "nguoi_cn": this.tt_nd.nguoi_cn,
              "phanvung_id": this.tt_nd.phanvung_id,
              "step": ""
            }

            console.log("DongBoThaydoiDichVu => LAY_DS_DANGKY_DVGT hdtb_id: ", hdtb_id)

            if(await this.DongBoThaydoiDichVu(hdtb_id, somay_dd)) {
              await  fn.fn_frmdongbovina_capnhat(this.axios, req);
              req = {
                "hdtb_id": hdtb_id,
                "status": TRANGTHAI_DONGBO.DONGBO_SERVICE
              }
              this.GetData(await api.capnhat_ngaykh_ngay_ht_v2(this.axios, req))
              ssCount++;
            }
          }
        }
        //#endregion

        //#region
        if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.CHAMDUT_SD || this.CurrentLoaiHD_ID == LOAI_HOPDONG.CHUYENDOI_LH){
          for(let i =0; i < selectedRows.length; i++) {
            let row = selectedRows[i];
            let hdtb_id = Number(row.hdtb_id);
            let somay_dd = row.ma_tb;
            checkCount++;
            await this.DongBoThanhLy_GuiCatMo(hdtb_id, somay_dd)
            await this.DongBoThanhLy_HoanThien(hdtb_id, somay_dd)
            req = {
              "hdtb_id": hdtb_id,
              "ip_cn": this.tt_nd.ip_cn,
              "loaihd_id": this.CurrentLoaiHD_ID,
              "ma_tb": somay_dd,
              "nguoi_cn": this.tt_nd.nguoi_cn,
              "phanvung_id": this.tt_nd.phanvung_id,
              "step": ""
            }
            await fn.fn_frmdongbovina_capnhat(this.axios, req);

            var trangthai_dongbo = this.CurrentLoaiHD_ID == LoaiHopDong.CHAMDUT_SD ? TRANGTHAI_DONGBO.DONGBO_SERVICE : TRANGTHAI_DONGBO.DONGBO_HT;
            req = {
              "hdtb_id": hdtb_id,
              "status": trangthai_dongbo
            }
            this.GetData(await api.capnhat_ngaykh_ngay_ht_v2(this.axios, req))
            ssCount++;
          }
        }
        //#endregion

        //#region
        if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.CHUYEN_QUYEN || this.CurrentLoaiHD_ID == LOAI_HOPDONG.TACHNHAP_TB)
        {
          for(let i =0; i < selectedRows.length; i++) {
            let row = selectedRows[i];
            let hdtb_id = Number(row.hdtb_id);
            let somay_dd = row.ma_tb;
            let ma_gd = row.ma_gd;;
            checkCount++;

            if (await this.DongBoCQSD_TGTB(hdtb_id, somay_dd, ma_gd))
            {
              req = {
                "hdtb_id": hdtb_id,
                "ip_cn": this.tt_nd.ip_cn,
                "loaihd_id": this.CurrentLoaiHD_ID,
                "ma_tb": somay_dd,
                "nguoi_cn": this.tt_nd.nguoi_cn,
                "phanvung_id": this.tt_nd.phanvung_id,
                "step": ""
              }
              await  fn.fn_frmdongbovina_capnhat(this.axios, req);

              req = {
                "hdtb_id": hdtb_id,
                "status": TRANGTHAI_DONGBO.DONGBO_SERVICE
              }
              this.GetData(await api.capnhat_ngaykh_ngay_ht_v2(this.axios, req))
              ssCount++;
            }
          }
        }
        //#endregion

        this.$toast.success(`Đồng bộ thành công ${ssCount.toString()}/${checkCount.toString()} phiếu!`);
        await this.Lay_DuLieu_HDTB(this.CurrentLoaiHD_ID, this.CurrentTTHD_ID, this.CurrentStatus, this.CurrentLoaiTB_ID);

        this.loading(false);

      }catch(ex){
        console.error(ex);
        commonFn.showException(this, ex);
      }finally{
        this.loading(false);
      }
    },
    async UpdateToolBar(){
      console.log("UpdateToolBar");
      if (!this.state.selectedPhieu || this.state.selectedPhieu.phieu_id == 0)
      {
          this.controls.tsbtnUploadAnh.enabled = false;
          this.controls.tsbtnDongBoHD.enabled = false;
          this.controls.tsbtnHoanThien.enabled = false;
      }
      else
      {
          let hdtb_id = this.state.selectedPhieu.hdtb_id*1;
          this.HienThiDVGT(await commonFn.LAY_DS_DANGKY_DVGT(this.axios, hdtb_id));
          if (this.rdoChuaKH_Checked)
          {
              console.log("UpdateToolBar rdoChuaKH_Checked");
              this.controls.tsbtnUploadAnh.enabled = true;
              this.controls.tsbtnDongBoHD.enabled = true;
              if (this.controls.cboLoaiTB.value*1 == LOAIHINH_THUEBAO.DIDONGTRATRUOC*1) // vuth update fix 1909 -- from here
                  this.controls.tsbtnHoanThien.enabled = true;
              else
                  this.controls.tsbtnHoanThien.enabled = false; // vuth update fix 1909 -- to here
          }
          else if (this.rdoDaKH_Checked)
          {
              console.log("UpdateToolBar rdoDaKH_Checked");
              this.controls.tsbtnUploadAnh.enabled = false;
              this.controls.tsbtnDongBoHD.enabled = false;
              this.controls.tsbtnHoanThien.enabled = true;
          }
      }
    },
    async gridviewThueBao_FocusedRowChanged(e){
      this.state.selectedPhieu = e;
      console.log("selectedPhieu", this.state.selectedPhieu);
      this.UpdateToolBar();

      if (this.controls.cboLoaiTB.value == LOAIHINH_THUEBAO.DIDONGTRATRUOC)
      {
          //TODO:
          // for (int i = 0; i < gridviewThueBao.RowCount; i++)
          // {
          //     gridviewThueBao.SetRowCellValue(i, "CHON", "s0");
          //     //gridviewThueBao
          // }
          // gridviewThueBao.SetFocusedRowCellValue(gridviewThueBao.Columns["CHON"], "s1");
          // //gridviewThueBao.ClearSelection();
      }
    },
    async radioStatus_changed() {
      if(this.rdoCatMo_Checked == true){
        this.$refs.frmGuiCatMo.openDialog(null);
        this.$refs.rdoChuaKH.checked = true;
        return false;
      }
      if(this.rdoDaKH_Checked == true){
        this.UpdateToolBar();
      }

      await this.Lay_DuLieu_HDTB(
        this.CurrentLoaiHD_ID,
        this.CurrentTTHD_ID,
        this.CurrentStatus,
        this.CurrentLoaiTB_ID
      );
    },
    async HienThiDVGT(ds){
        this.controls.grcDichVuGT.dataSource = [];
        if(ds && ds.length > 0){
          ds = ds.map((x) => {
            let x1 = x;
            x1.ten_kieuyc = x["kieu_yc"].toString() == "1" ? "Đăng ký": "Hủy";
            return x1;
          })
        }
        this.controls.grcDichVuGT.dataSource = ds;
    },
    async tlstpRefresh_Click(){
      this.loading(true)
      await this.Lay_DuLieu_HDTB(this.CurrentLoaiHD_ID, this.CurrentTTHD_ID, this.CurrentStatus, this.CurrentLoaiTB_ID);
      this.loading(false)
    },
    async tsbtnDongBoHDHT_Click(){
      try{
        var grid = this.$refs.gridviewThueBao;
        var selectedRows = grid.getSelectedRecords();
        if(!selectedRows || selectedRows.length == 0){
          this.$toast.error("Hãy tick chọn danh sách phiếu cần thực hiện. !");
          return;
        }
        this.loading(true);

        let tthd_id = 1; //code cũ fix = 1
        let quytrinh_id = this.state.selectedPhieu.quytrinh_id*1;
        let data = await commonFn.lay_huonggiao_theo_quytrinh(this.axios, quytrinh_id, tthd_id);
        let huonggiao_id = -1;
        if (data && data.length > 0)
        {
            huonggiao_id = data[0]["huonggiao_id"]*1;
        }
        else
        {
            this.$toast.error("Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại");
            huonggiao_id = -1;
            this.loading(false);
            return;
        }

        for(let i =0; i < selectedRows.length; i++) {
            let row = selectedRows[i];
            await this.hoanThien(row);
        }

        this.$toast.success("Hoàn thiện thành công!");
        await this.Lay_DuLieu_HDTB(this.CurrentLoaiHD_ID, this.CurrentTTHD_ID, this.CurrentStatus, this.CurrentLoaiTB_ID);
        this.loading(false);
      }catch(ex){
        console.error("tsbtnDongBoHDHT_Click", ex);
        commonFn.showException(this, ex);
      }finally{
        this.loading(false);
      }
    },
    async hoanThien(item){
          let loaihd_id = this.CurrentLoaiHD_ID;
          let loaitb_id = this.CurrentLoaiTB_ID;
          let hdtb_id = item.hdtb_id;
          let hdtt_id = item.hdtt_id;
          let currentkieuld_id =  item.kieuld_id;
          let phieu_id = item.phieu_id*1;
          let ma_tb = item.ma_tb;
          let quytrinh_id = this.state.selectedPhieu.quytrinh_id*1;

          let ret = await fn.fn_frmdongbovina_hoanthien(this.axios, phieu_id, quytrinh_id, hdtb_id, loaihd_id, loaitb_id, this.tt_nd.nhanvien_id, this.tt_nd.donvi_id, this.tt_nd.phanvung_id, this.tt_nd.may_cn, this.tt_nd.nguoi_cn, this.tt_nd.ip_cn);
          if (loaitb_id == LoaiHinhTB.DIDONGTRASAU)
          {
              //start map ma kh ccbs 3006/2020
              //var kieuld_id = await commonFn.MAP_ID(this.axios, "kieuld_id", "css.hd_thuebao", " where hdtb_id =" + hdtb_id);
              var kieuld_id = this.GetData(await api.fn_tt_hd_thuebao(this.axios, { type: 6, param: hdtb_id }));
              kieuld_id = kieuld_id*1;
              if (kieuld_id == -1)
                  return;
              //var ma_tt = await commonFn.MAP_ID(this.axios, "ma_tt", "css.hd_thanhtoan", "where hdtt_id =" + hdtt_id);
              var ma_tt = this.GetData(await api.fn_tt_hd_thanhtoan(this.axios, { type: 2, param: hdtt_id }));
              if (kieuld_id == KIEU_LAPDAT.DM_DIDONG_CHUYEN_TINH)
              {
                  var ma_tt_neo = await this.LayMaKhCcbs_ChuyenTinh("84" + ma_tb);
                  if (ma_tt_neo == null)
                      return;

                  await this.MapMaKhCcbs(ma_tt, ma_tt_neo);
              }
              else
              {
                  //var ma_tt_neo = await commonFn.MAP_ID(this.axios, "ma_tt_neo", "CSS.GHEP_MATT", "where ma_tt ='" + ma_tt + "'");
                  var ma_tt_neo = this.GetData(await api.fn_tt_ghep_matt(this.axios, { type: 1, param: ma_tt }));
                  if (ma_tt_neo == "-1")
                      await  this.MapMaKhCcbs(ma_tt, ma_tt); // chưa map thì đẩy vào bảng map.
              }

              //end map makh ccbs 3006/2020
              // HTH
              if (this.state.OPEN_SYNC_VINA_TRASAU)
                await this.Dongbo_goi_dadv_dd(hdtb_id); // HTH 21/02/2020
          }
          else
          {
            this.popupComponent = () => import("@/modules/contract/setup/UpdatePostPaid/UpdatePostPaid.vue");
            this.popupComponentName = "frmCapNhatTraTruoc_VNP";
            this.popupComponentAttr = {ma_gd: ma_tb, isPopup: true};
            this.Popup("popupComponents");
            return;
          }
    },
    async tsbtnUploadAnh_Click(){ //toolStripButton1_Click

      // let data = {
      //   vma_tb: "911839588",
      //   vma_kh: "ma_kh_neo",
      //   vma_hd: "ma_hd_neo",
      //   vloai_kh: "loaikh_id_neo",
      //   dsIdLoaiAnh: [],
      //   loaitb_id: LOAIHINH_THUEBAO.DIDONGTRATRUOC,
      //   hdtb_id: 0
      // }
      // this.$refs.frmUploadAnh.openDialog(data);

      // return;

      var grid = this.$refs.gridviewThueBao;
      var selectedRows = grid.getSelectedRecords();
      if(!selectedRows || selectedRows.length == 0){
        this.$toast.warning("Hãy tick chọn danh sách phiếu cần upload ảnh. !");
        return;
      }

      if (!this.state.OPEN_SYNC_VINA_TRASAU)
      {
          this.popupComponent = () => import("@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue");
          this.popupComponentName = "popupUploadHoSo";
          this.popupComponentAttr = {tag: 2};
          console.log("tsbtnUploadAnh_Click", this.popupComponentAttr);
          this.Popup("popupComponents");
          return;
      }
      else{
        if (this.CurrentLoaiHD_ID == LOAI_HOPDONG.DAT_MOI)
        {
          try{
            for(let i=0;i<selectedRows.length;i++){
              let row = selectedRows[i];
              await this.openUploadAnh(row)
            }
            //await this.openUploadAnh(selectedRows[0]);

            this.loading(false);
          }catch(ex){
            console.error("tsbtnDongBoHDHT_Click", ex);
            commonFn.showException(this, ex);
          }finally{
            this.loading(false);
          }
        }else{
          this.$toast.warning("Không cần upload ảnh. !");
        }
      }
    },

    async openUploadAnh(item){
        let hdtb_id = item.hdtb_id;
        let loaitb_id =  item.loaitb_id;
        let phieu_id = item.phieu_id*1;
        let loaikh_id = item.loaikh_id;
        var hdkh_id = item.hdkh_id;
        var somay_dd = item.ma_tb;

        //var ma_tt = new CheckDataFacade().MAP_ID("ma_tt", DatabaseConstants.DB2 + ".hd_thanhtoan", "where hdtt_id =" + gridviewThueBao.GetRowCellValue(i, "HDTT_ID").ToString());
        var ma_tt = this.GetData(await api.fn_tt_hd_thanhtoan(this.axios, { type: 2, param: item.hdtt_id }));

        //var ma_kh_neo = new CheckDataFacade().MAP_ID("ma_tt_neo", DatabaseConstants.DB2 + ".GHEP_MATT", "where ma_tt ='" + ma_tt + "'");
        var ma_kh_neo = this.GetData(await api.fn_tt_ghep_matt(this.axios, { type: 1, param: ma_tt }));

        if (ma_kh_neo == "-1")
            ma_kh_neo = ma_tt;
        /// vuth update fix 07/05/2020   --- end here 04
        var ma_hd_neo = item.ma_gd;

        //let loaikh_id_neo = await commonFn.MAP_ID(this.axios, "loaikh_id_neo", "css.loai_kh", "where loaikh_id =" + loaikh_id);
        var loaikh_id_neo = this.GetData(await api.fn_tt_loai_kh(this.axios, { type: 2, param: loaikh_id }));
        if (loaikh_id_neo == "-1")
        {
            this.$toast.warning("Loại khách hàng chưa đồng bộ với CCBS, hãy kiểm tra lại");
            return;
        }

        //var kh_canhan_neo = await commonFn.MAP_ID(this.axios, "loai", "css.loai_kh_neo", "where loaikh_id =" + loaikh_id_neo);
        var kh_canhan_neo = this.GetData(await api.fn_tt_loai_kh_neo(this.axios, { type: 1, param: loaikh_id_neo }));
        if (kh_canhan_neo == "-1")
        {
            this.$toast.warning("Loại khách hàng chưa đồng bộ với CCBS, hãy kiểm tra lại");
            return;
        }

        var dsAnhUpload = await this.LayDS_LoaiAnhCanUpload_TheoHD(hdtb_id, kh_canhan_neo);
        let data = {
          matb: somay_dd,
          vma_kh: ma_kh_neo,
          vma_hd: ma_hd_neo,
          vloai_kh: loaikh_id_neo,
          dsIdLoaiAnh: dsAnhUpload,
          loaitb_id: LOAIHINH_THUEBAO.DIDONGTRASAU,
          hdtb_id: hdtb_id,
          OPEN_SYNC_VINA_TRASAU: this.state.OPEN_SYNC_VINA_TRASAU
        }

        this.$refs.frmUploadAnh.openDialog(data);
    },

    async onUploadSuccess(data){
      console.log("onUploadSuccess", data);
        await fn.sp_frmdongbovina_update_uploadanh(this.axios, data.hdtb_id, data.loaitb_id);
    },
    async LayDS_LoaiAnhCanUpload_TheoHD(hdtb_id, kh_canhan)
    {
        // neu MNP thi chi can anh thue bao
        if (await fn.checkMNP(this.axios, hdtb_id))
            return this.dtAnhThueBao;
        // kiem tra da co hdtb nao upload thanh cong chua, neu co roi thi khong phải upload anh khachhang
        // neu chua thi hdtb phai upload ca anh hopdong.
        if (kh_canhan == "1")
            return this.dtAnhThueBao_HopDong_CaNhan;
        else
            return this.dtAnhThueBao_HopDong_DoanhNghiep;
    },
    async layDS_Anh_CCBS(){
      if (!this.state.OPEN_SYNC_VINA_TRASAU)
          return;
      try
      {
          let dtAnhThueBao = await ccbs.ts_laphd_hoamangmoi_ds_loaianh(this.axios);

          if (!dtAnhThueBao && dtAnhThueBao.length > 0 && !dtAnhThueBao[0]["loai_anh"])
          {
            dtAnhThueBao = dtAnhThueBao.map((x) => {
              let x1 = x;
              x1.loai_anh = 1
              return x1;
            });
          }

          let dtAnhHopDong_CaNhan = await ccbs.ts_hoanthien_hd_ds_loaianh(this.axios, "1");
          if (!dtAnhHopDong_CaNhan && dtAnhHopDong_CaNhan.length > 0 && !dtAnhHopDong_CaNhan[0]["loai_anh"])
          {
            dtAnhHopDong_CaNhan = dtAnhHopDong_CaNhan.map((x) => {
              let x1 = x;
              x1.loai_anh = 2
              return x1;
            });
          }

          this.dtAnhHopDong_DoanhNghiep = await ccbs.ts_hoanthien_hd_ds_loaianh(this.axios, "2");
          if (!this.dtAnhHopDong_DoanhNghiep && this.dtAnhHopDong_DoanhNghiep.length > 0 && !this.dtAnhHopDong_DoanhNghiep[0]["loai_anh"])
          {
            this.dtAnhHopDong_DoanhNghiep = this.dtAnhHopDong_DoanhNghiep.map((x) => {
              let x1 = x;
              x1.loai_anh = 3
              return x1;
            });
          }

          this.dtAnhThueBao = dtAnhThueBao;

          this.dtAnhThueBao_HopDong_CaNhan.push(dtAnhThueBao);
          this.dtAnhThueBao_HopDong_CaNhan.push(dtAnhHopDong_CaNhan);
          this.dtAnhThueBao_HopDong_DoanhNghiep.push(dtAnhThueBao);
          this.dtAnhThueBao_HopDong_DoanhNghiep.push(this.dtAnhHopDong_DoanhNghiep);
      }
      catch (ex)
      {
        commonFn.showException(this, ex);
      }
    },
    async DongBoAnhChup(hdtb_id, somay_dd){
      //TODO
      this.state.somay_dd = somay_dd;
      this.$refs.frmChonAnhHoSo.openDialog({id: hdtb_id, kieu: 0});
      return false;
    },
    async onSelectedFiles(selectedFiles){
      console.log('onSelectedFiles', selectedFiles);
      if(!selectedFiles || selectedFiles.length <= 0) return;
      try{
        let img1, img2, img3, img4;
        img1 = selectedFiles[0].url;
        img2 = selectedFiles.length > 1 ? selectedFiles[1].url : selectedFiles[0].url;
        img3 = selectedFiles.length > 2 ? selectedFiles[2].url : selectedFiles[0].url;
        img4 = selectedFiles.length > 3 ? selectedFiles[3].url : selectedFiles[0].url;

        // var rs = await ccbs.upload_img_dktt(this.axios, this.state.somay_dd,
        //                                   img1, this.tt_nd.ngay_cn,
        //                                   img2, this.tt_nd.ngay_cn,
        //                                   img3,
        //                                   img4, this.tt_nd.ngay_cn);

        let lydo = this.state.somay_dd;
        var rs = await ccbs.ts_upload_img_tt_khcn(this.axios,
                                          this.state.somay_dd,
                                          lydo,
                                          img1,
                                          img2,
                                          img3,
                                          img4);

        if (rs.IssSuccessed)
        {
            this.$toast.error("Đồng bộ Ảnh thành công");
            return true;
        }

        this.$toast.error("Có lỗi khi upload ảnh: " + rs.Message);
      }catch(ex){
        console.log(ex);
        commonFn.showException(this, ex);
      }
    },
    async DongBoThanhLy_GuiCatMo(hdtb_id, somay_dd){
      // try{
        this.loading(true)
        //let ma_hd_thanhly_neo = await commonFn.MAP_ID(this.axios, "ma_gd_neo", "css.hdtb_dd", "where hdtb_id =" + hdtb_id);
        var ma_hd_thanhly_neo = this.GetData(await api.fn_tt_hdtb_dd(this.axios, { type: 1, param: hdtb_id }));
        if (ma_hd_thanhly_neo == "-1")
        {
          throw  "Không tìm thấy ma_gd_neo trong bảng hdtb_dd "; // vuth update 16/12
        }

        let result = await ccbs.ts_laphd_thanhly_gui_catmo(this.axios, this.tt_nd.tentat_ccbs, ma_hd_thanhly_neo, this.tt_nd.USER_NEO);
        if (result && result.IssSuccessed)
            return true;
        else
        {
            throw "Có lỗi khi gửi cắt mở thanh lý : " + result.Message;
        }
      // }
      // catch(e){
      //   commonFn.showException(this, e)
      // }
      // finally{
      //   this.loading(false)
      // }
    },
    async DongBoThanhLy_HoanThien(hdtb_id, somay_dd){
      //let ma_hd_thanhly_neo = await commonFn.MAP_ID(this.axios, "ma_gd_neo", "css.hdtb_dd", "where hdtb_id =" + hdtb_id);
      var ma_hd_thanhly_neo = this.GetData(await api.fn_tt_hdtb_dd(this.axios, { type: 1, param: hdtb_id }));
      if (ma_hd_thanhly_neo == "-1")
      {
        throw "Không tìm thấy ma_gd_neo trong bảng hdtb_dd "; // vuth update 16/12
        return false;
      }

      let result = await ccbs.ts_hoanthien_hd_thanhly_danhsach_hoanthanh(this.axios, this.tt_nd.tentat_ccbs, ma_hd_thanhly_neo, this.tt_nd.ngay_cn, this.tt_nd.user_neo);
      if (result && result.IssSuccessed)
          return true;
      else
      {
        throw "Có lỗi khi gửi cắt mở thanh lý : " + result.Message;
      }
    },
    async DongBoCQSD_TGTB(hdtb_id, somay_dd, ma_gd){
      let dsThuebao = await ccbs.ts_laphd_chuyenquyen_sudung_ds_thuebao(this.axios, this.tt_nd.tentat_ccbs, ma_gd);
      let dsSomay = "";
      if (dsThuebao)
      {
        dsThuebao = commonFn.LowerCasePropertyList(dsThuebao)
        dsSomay = dsThuebao.map((x) => {
           return x.ma_tb;
        }).join();
      }
      if (!dsSomay || dsSomay == "")
      {
          this.$toast.error("Không lấy được ds thuê bao trong hợp đồng");
          return false;
      }

      await ccbs.ts_laphd_chuyenquyen_sudung_hoanthienhd(this.axios, this.tt_nd.ngay_cn, "", this.tt_nd.tentat_ccbs, "", this.tt_nd.tentat_ccbs, ma_gd, dsSomay, this.tt_nd.user_neo);
      return true;
    },
    async DongBoThaydoiDichVu(hdtb_id, somay_dd){
      let rsDichVu = await commonFn.LAY_DS_DANGKY_DVGT(this.axios, hdtb_id);
      if(!rsDichVu || rsDichVu.length == 0) return true;
      let result = false;
      for(const row of rsDichVu){
        let dichvugt_id = Number(row["dichvugt_id"]);
        let kieu_yc = row["kieu_yc"];
        //string ma_neo = row["ma_neo"].ToString();
        let ma_neo = row["ma_dvgt"];
        let ghichu_ccbs = row.ghichu ? row.replaceAll("&", "và") : "";

        if (dichvugt_id == DICHVU_GIATANG.KHOAMOTCHIEU_DIDONG)
        {
            if (kieu_yc == "1")
                ma_neo = "SPS_PRODUCT_HLR_OC_DEACTIVE";
            else
                ma_neo = "SPS_PRODUCT_HLR_OC";
            await ccbs.khoamo_ic_oc(this.axios, "84" + somay_dd, ma_neo, "ĐHSX - " + ghichu_ccbs, this.tt_nd.user_neo, this.tt_nd.ip, "", this.tt_nd.tentat_ccbs, hdtb_id);
        }
        else if (dichvugt_id == DICHVU_GIATANG.KHOAHAICHIEU_DIDONG)
        {
            if (kieu_yc == "1")
                ma_neo = "SPS_PRODUCT_HLR_IC_DEACTIVE";
            else
                ma_neo = "SPS_PRODUCT_HLR_IC";

            await ccbs.khoamo_ic_oc(this.axios, "84" + somay_dd, ma_neo, "ĐHSX - " + ghichu_ccbs, this.tt_nd.user_neo, this.tt_nd.ip, "", this.tt_nd.tentat_ccbs, hdtb_id);
            if (kieu_yc == "1")
                ma_neo = "SPS_PRODUCT_HLR_OC_DEACTIVE";
            else
                ma_neo = "SPS_PRODUCT_HLR_OC";
            await ccbs.khoamo_ic_oc(this.axios, "84" + somay_dd, ma_neo, "ĐHSX - " + ghichu_ccbs, this.tt_nd.user_neo, this.tt_nd.ip, "", this.tt_nd.tentat_ccbs, hdtb_id);
        }
        else //Khóa mở dịch vụ bình thường
        {
            if (ma_neo == "") return;
            await ccbs.dong_mo_dv(this.axios, "84" + somay_dd, ma_neo, "ĐHSX - " + ghichu_ccbs, this.tt_nd.user_neo, this.tt_nd.ip_cn, this.tt_nd.tentat_ccbs, hdtb_id);
        }
        result = true;
      }

      return result;
    },
    async DongBoDatMoi(row){
      if (!this.tt_nd.user_neo || this.tt_nd.user_neo == "")
      {
          this.$toast.error("Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
          return false;
      }

      try{
        console.log("DongBoDatMoi row", row);
        var ma_tb = row.ma_tb;
        var ma_gd = row.ma_gd;
        var hdtt_id = row.hdtt_id;
        //var ma_tt = await commonFn.MAP_ID(this.axios, "ma_tt", "css.hd_thanhtoan", "where hdtt_id =" + hdtt_id);
        var ma_tt = this.GetData(await api.fn_tt_hd_thanhtoan(this.axios, { type: 2, param: hdtt_id }));

        //var ma_tt_neo = await commonFn.MAP_ID(this.axios, "ma_tt_neo", "css.GHEP_MATT", "where ma_tt ='" + ma_tt + "'");
        var ma_tt_neo = this.GetData(await api.fn_tt_ghep_matt(this.axios, { type: 1, param: ma_tt }));

        if (ma_tt_neo != null && ma_tt_neo != "-1")
            ma_tt = ma_tt_neo;
        var ten_kh = row.ten_kh;
        var ngay_sinh = row.ngay_sn && row.ngay_sn.length > 0 ? moment(row.ngay_sn, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY") : ""; //1990-07-25 17:00:00
        var cmt = row.so_gt;
        var passport = row.so_gt;
        var loai_gt = row.loaigt_id;

        if (loai_gt == "3")
          cmt = "";//loai_gt=3: Hộ chiếu
        else
          passport = "";

        var customer_type = "";
        var diachi_kh = row.diachi_kh;
        var congty = "";
        var nghenghiep = row.tennganhnghe;
        var gioitinh = row.gioitinh == "0" ? "0" : "1";

        // var quoctich = await commonFn.MAP_ID(this.axios, "quoctich_neo_id", "css.QUOCTICH", "where QUOCTICH_ID =" + row.quoctich_id);
        var quoctich = this.GetData(await api.fn_tt_quoctich(this.axios, { type: 1, param: row.quoctich_id }));
        if (quoctich == "-1")
        {
            this.$toast.error("Thông tin về quốc tịch của khách hàng " + row.ten_kh + " chưa đồng bộ với dữ liệu VinaPhone.\nBạn không thể tiếp tục cập nhật dữ liệu.");
            return false;
        }
        // let quoctich = "VietNam"

        var email = row.email;
        var noicap = row.noicap;
        var loai_kh = "";
        var ghichu = "";
        var nam_so_sim = "";
        var msisdn = "";
        var province = this.tt_nd.tentat_ccbs;
        var sub_type = "";
        var diachi_giaodich = "";
        var so_daily = "";
        var contract_type = "";
        //var loaikh_id_neo = await commonFn.MAP_ID(this.axios, "loaikh_id_neo", "css.loai_kh", "where loaikh_id =" + row.loaikh_id);
        var loaikh_id_neo = this.GetData(await api.fn_tt_loai_kh(this.axios, { type: 2, param: row.loaikh_id }));

        var loaitb_id = row.loaitb_id * 1;
        if (loaitb_id == LOAIHINH_THUEBAO.DIDONGTRATRUOC)
        {
            //Đồng bộ trả trước
            //Gọi hàm đẩy thông tin đăng ký lên CCBS
            //TODO:
            await ccbs.tt_3_dktt(this.axios,
                ma_tb, ten_kh, ngay_sinh, cmt, passport,
                customer_type, diachi_kh, congty, nghenghiep,
                gioitinh, quoctich, email, loai_gt, noicap,
                ghichu, ngay_cap, nam_so_sim, msisdn,
                province, sub_type, diachi_giaodich, so_daily, contract_type);
        }
        else if (loaitb_id == LOAIHINH_THUEBAO.DIDONGTRASAU)
        {
          //Đồng bộ trả sau
          //Gọi hàm hoàn thiện trên CCBS
          var THUEBAO_ID_NEO = "";

          if (this.CurrentKieuLD_ID == KIEU_LAPDAT.DM_DIDONG_CHUYEN_TINH)
          {
              //Hoàn thiện hòa mạng mới
              // var dsTB_CCBS = new CCBSFacade().TS_CHUYENTINH_THUEBAO_CTS_QUERY("84" + ma_tb);
              var dsTB_CCBS = await ccbs.ts_laphd_chuyentinh_laytt_tb(this.axios, "1", this.tt_nd.tentat_ccbs, "84" + ma_tb, "");
              if (!dsTB_CCBS || dsTB_CCBS.length == 0)
              {
                  this.$toast.error("Không có thuê bao chờ hoàn thiện đối với số máy " + ma_tb + " trên CCBS!");
                  return false;
              }
              console.log("dsTB_CCBS", dsTB_CCBS);
              dsTB_CCBS = commonFn.LowerCasePropertyList(dsTB_CCBS)
              THUEBAO_ID_NEO =  dsTB_CCBS[0]["thuebao_id"];
              if(!THUEBAO_ID_NEO || THUEBAO_ID_NEO == ""){
                this.$toast.error("Không tìm thấy thuê bao " + ma_tb + " trên CCBS!");
                return false;
              }
              let rs = await ccbs.ts_laphd_chuyentinh_hoanthanh(this.axios, this.tt_nd.tentat_ccbs, THUEBAO_ID_NEO, this.tt_nd.USER_NEO, this.tt_nd.ip_cn);
              if (rs.IssSuccessed)
                return true;
              else
              {
                  this.$toast.error(rs.Message);
                  return false;
              }
          }
          else
          {
              //Hoàn thiện hòa mạng mới
              var dsTB_CCBS = await ccbs.ts_hoanthien_hd_hoamang_ds_tb(this.axios, this.tt_nd.tentat_ccbs, ma_gd);
              console.log("dsTB_CCBS", dsTB_CCBS);
              if (!dsTB_CCBS == null || dsTB_CCBS.length == 0)
              {
                  this.$toast.error("Không có thuê bao chờ hoàn thiện đối với hợp đồng " + ma_gd + " trên CCBS!");
                  return false;
              }
              //Lấy thuebao_neo_id từ dữ liệu của CCBS
              /// vuth update fix 07/05/2020   --- start here 01
              var ds_thuebaoid_daisodoanhnghiep = "";
              var ds_matb_daisodoanhnghiep = "";
              /// vuth update fix 07/05/2020   --- end here 01
              dsTB_CCBS = commonFn.LowerCasePropertyList(dsTB_CCBS);
              for (const dr of dsTB_CCBS)
              {
                  if (dr["ma_tb"] == "84" + ma_tb)
                  {
                      THUEBAO_ID_NEO = dr["thuebao_id"];
                      //break;
                  }
                  /// vuth update fix 07/05/2020   --- start here 02
                  //ds_thuebaoid_daisodoanhnghiep += dr["thuebao_id"].toString() + ",";
                  //ds_matb_daisodoanhnghiep += "'" + dr["ma_tb"].toString().substring(2) + "',";
              }
              ds_thuebaoid_daisodoanhnghiep = dsTB_CCBS.map((x) => { return x.thuebao_id}).join();
              ds_matb_daisodoanhnghiep = dsTB_CCBS.map((x) => { return  `'${x.ma_tb.substring(2)}'`}).join();
              /// vuth update fix 07/05/2020   --- end here 02
              if (THUEBAO_ID_NEO == "")
              {
                  this.$toast.error("Không tìm thấy thuê bao " + ma_tb + " trên CCBS");
                  return false;
              }
              if (loaikh_id_neo == "-1")
              {
                  this.$toast.error("Không lấy được loaikh_id_neo");
                  return false;
              }
              var loaikhachhang_1_2 = "";
              if (loaikh_id_neo == "4")
                  loaikhachhang_1_2 = "1";
              else
                  loaikhachhang_1_2 = "2";
              var GHI_CHU = "";
              var lydokhonganh = "0";
              var ma_nv = "4";
              var ds_thuebao_id = [THUEBAO_ID_NEO];
              //ma_tinh, ngay_ht, ghi_chu, ma_hd, userid, ds_thuebao_id, ma_nv, loaikhachhang_1_2, ma_kh, lydo_khonganh
              var rs = await ccbs.ts_hoanthien_hd_hoamang_hoanthanh(this.axios,
                  this.tt_nd.tentat_ccbs, this.tt_nd.ngay_cn, GHI_CHU, ma_gd, this.tt_nd.user_neo, ds_thuebao_id
                  , ma_nv, loaikhachhang_1_2, ma_tt, lydokhonganh
              );
              if (rs.IssSuccessed){
                return true;
              }
              /// vuth update fix 07/05/2020   --- start here  03
              /// neu la dai so doanh nghiep thi phai dong bo cung 1 luc
              else if (rs.Message && rs.Message.indexOf("Phải hoàn thiện tất cả các thuê bao thuộc dải số doanh nghiệp") >= 0)
              {
                  //ma_tinh, ngay_ht, ghi_chu, ma_hd, userid, ds_thuebao_id, ma_nv, loaikhachhang_1_2, ma_kh, lydo_khonganh
                  ds_thuebao_id = ds_thuebaoid_daisodoanhnghiep;
                  var rs_daisoDN = await ccbs.ts_hoanthien_hd_hoamang_hoanthanh(this.axios,
                      this.tt_nd.tentat_ccbs, this.tt_nd.ngay_cn, GHI_CHU, ma_gd, this.tt_nd.user_neo, ds_thuebao_id
                      , ma_nv, loaikhachhang_1_2, ma_tt, lydokhonganh
                  );

                  if (rs_daisoDN.IssSuccessed)
                  {
                      let req = {
                        "tthd_id": 2,
                        "loaitb_id": 20,
                        "ds_matb_daisodoanhnghiep": ds_matb_daisodoanhnghiep,
                        "ttdb_id": TRANGTHAI_DONGBO.DONGBO_SERVICE
                      }
                      await fn.update_hd_thuebao_status(this.axios, req);
                      return true;
                  }
                  else
                  {
                      this.$toast.error(rs_daisoDN.Message);
                      return false;
                  }
              }
              /// vuth update fix 07/05/2020   --- end here 03
              else
              {
                  this.$toast.error(rs.Message);
                  return false;
              }
          }
        }
        else
        {
            this.$toast.error("Thuê bao " + ma_tb + " không phải là thuê bao di động.");
            return false;
        }
        return false;
      }
      catch(ex){
        console.error(ex);
        commonFn.showException(this, ex);
        return false;
      }
      finally{
        this.loading(false);
      }
    },
    async LayMaKhCcbs_ChuyenTinh(ma_tb){
      let thongtin_ct =  await ccbs.ts_chuyentinh_thuebao_cts_query(this.axios, ma_tb);
      if (thongtin_ct && thongtin_ct.length > 0)
      {
          thongtin_ct = commonFn.LowerCasePropertyList(thongtin_ct);
          let ma_kh = thongtin_ct[0].ma_kh;
          if (ma_kh)
          {
            return thongtin_ct[0].ma_tinh == this.tt_nd.tentat_ccbs ? thongtin_ct[0].ma_kh : null;
          }
          else
          {
              throw "kết quả trả về từ ccbs không đúng, không có cột MA_KH";
          }
      }
      else
      {
          throw "Có lỗi lấy thông tin mã khách hàng từ ccbs: ";
      }
    },
    async MapMaKhCcbs(ma_tt_dhsx, ma_kh_ccbs){
      try
      {
        let req = {
          "ma_kh_ccbs": ma_kh_ccbs,
          "ma_tt_dhsx": ma_tt_dhsx,
          "phanvung_id": this.tt_nd.phanvung_id
        };
        this.GetData(await api.fn_frmdongbovina_ghepma(this.axios, req))
        return true;
      }
      catch (ex)
      {
          console.error(ex)
          return false;
      }
    },
    async Dongbo_goi_dadv_dd(hdtb_id){
      try{
        let req = {
          "hdtb_id": hdtb_id,
          "phanvung_id": this.tt_nd.phanvung_id
        };
        let tt_dk_goi = await fn.sp_frmdongbovina_lay_goidadv(this.axios, req);
        if(!tt_dk_goi || tt_dk_goi.length <=0) return;

        let _dt_goi_ccbs = null;
        let kq = "";

        for(const row of tt_dk_goi){
          // Chỉ đăng ký với các gói di động truyền thống
          if (row.goi_neo_id && row.goi_neo_id != "")
            return;

          let chunhom = "";
          if (!row.goi_vnp_id && row.goi_vnp_id.length > 0){
            let req = { "vgoi_id": row.goi_vnp_id };
            _dt_goi_ccbs = await fn.lay_ds_goi_cv_vnp(this.axios, req);
          }

          chunhom = row.chunhom && row.chunhom != "0" ? row.ma_tb : "";

          if (!row.cv_vnp_id && row.cv_vnp_id  != ""  && !row.goi_vnp_id && row.goi_vnp_id  != "" && !row.ma_tb && row.ma_tb != ""){
            kq = await ccbs.ts_thongtin_dangky_goicuoc_tb(this.axios, row.cv_vnp_id, row.goi_vnp_id  + ":84" + row.ma_tb, this.tt_nd.tentat_ccbs, chunhom, "Đăng ký gói cước từ OneBSS", this.tt_nd.ngay_cn, this.tt_nd.user_neo);
          }

          let nhomtb_id_neo_in = "";
          this.$toast.success("Đăng ký gói đa dịch vụ " + row.ten_goi + " thành công!");
          let _dt_nhomtb = await ccbs.ts_thongtin_danhmuc_goicuoc_dk_tb(this.axios, this.tt_nd.tentat_ccbs, row.cv_vnp_id, "84" + row.ma_tb);
          if (_dt_nhomtb != null && _dt_nhomtb.length > 0 && _dt_nhomtb[0].id)
          {
              nhomtb_id_neo_in = _dt_nhomtb[0].id;
          }
          else
          {
              this.$toast.error("Không lấy được nhóm thuê bao CCBS!");
          }

          //tạo gói đa dịch vụ
          req = {
            "goi_id": row.goi_id,
            "hdtb_id": hdtb_id,
            "ma_tb": row.ma_tb,
            "nhomtb_id": row.nhomtb_id,
            "nhomtb_id_neo_in": nhomtb_id_neo_in
          }
          await fn.sp_frmdongbovina_tao_goidadv(this.axios, req);
        }

      } catch(ex){
        //commonFn.showException(this, ex);
        console.error(ex);
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    async popupClosed(val) {
      console.log("popupClosed");
      if(!val) {
        this.popupRetVal = {};
        this.ClosePopup('popupComponents');
        return;
      }
      this.ClosePopup('popupComponents');
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
    },
  },

};
</script>
