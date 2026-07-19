<template src="./CapVatTuThueBao.html"></template>
<style src="./CapVatTuThueBao.scss">
</style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import ClickOutside from "vue-click-outside";
import moment from "moment";
import xlsx from "xlsx";
import frmTestPortV2 from "@/modules/search/subscriber/CheckPort/";
import SearchEProfile from "@/modules/search/subscriber/SearchEProfile";
import UploadEProfile from "@/modules/contract/profile/UploadEProfile";
import { previewPrint } from "../../../utils/util";
import DoiSerial from "./DoiSerial/DoiSerial.vue";
import { Query } from "@syncfusion/ej2-data";
let suaVTTemplate = function () {
  return {
    template: {
      template: `
                    <div class="">
                        <button v-if="check_Phieu" class="btn btn-main pad4 lh14 w60 btn-primary" @click="suaDoi(value)">
                              <span class="-ap icon-close"></span>
                          </button>
                    </div>
                `,
      data() {
        return {
          data: {},
        };
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent;
        },
        value() {
          return this.data;
        },
        check_Phieu() {
          if (this.data.PHIEUVT_ID == "0" && this.data.SERIAL != "") {
            return true;
          } else {
            return false;
          }
        },
      },
      methods: {
        suaDoi(data) {
          this.parent.pSuaDoi(data);
        },
      },
    },
  };
};

let thuHoiTemplate = function () {
  return {
    template: {
      template: `
                    <div class="">
                        <button class="btn btn-main pad3 lh14" @click="thuHoi(value)">
                              <span class="-ap icon-plus"></span>
                          </button>
                    </div>
                `,
      data() {
        return {
          data: {},
        };
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent;
        },
        value() {
          return this.data;
        },
      },
      methods: {
        thuHoi(data) {
          this.parent.pthuHoi(data);
        },
      },
    },
  };
};

let huyTemplate = function () {
  return {
    template: {
      template: `
                    <div class="">
                        <button class="btn btn-main pad3 lh14" @click="xoa(value)">
                              <span class="-ap icon-close"></span>
                          </button>
                    </div>
                `,
      data() {
        return {
          data: {},
        };
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent;
        },
        value() {
          return this.data.MA_TB;
        },
      },
      methods: {
        xoa(data) {
          console.log("xoa parent == ", this.parent);
          this.parent.xoa(data);
        },
      },
    },
  };
};
let xoaLSVTTemplate = function () {
  return {
    template: {
      template: `
                    <div class="">
                        <button class="btn btn-main pad3 lh14" @click="xoa(value)">
                              <span class="-ap icon-close"></span>
                          </button>
                    </div>
                `,
      data() {
        return {
          data: {},
        };
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent;
        },
        value() {
          return this.data;
        },
      },
      methods: {
        xoa(data) {
          // console.log("this.parent = ");
          this.parent.xoaLSVT(data);
        },
      },
    },
  };
};
export default {
  components: {
    breadcrumb,
    frmTestPortV2,
    SearchEProfile,
    UploadEProfile,
    DoiSerial,
  },
  name: "CapVatTuThueBao",
  mounted() {},
  watch: {
    loaiNhomSelected: {
      handler: async function (data) {
        if (data.toString() == "1") {
          await this.fetchDMMucDichLyDo(6);
          this.colKenhTn_Visible = false;
          this.colTinhTrang_Visible = false;
        } else {
          await this.fetchDMMucDichLyDo(5);
          this.colKenhTn_Visible = true;
          this.colTinhTrang_Visible = true;
        }
        this.listLoaiHDvaPL = [];
        await this.fetchLoaiHDvaPL(data);
        await this.LoadThongTinThueBao("");
        this.params.p_id = data        
      },
    },
    kqThuHoiSelected: {
      handler: function (data) {
        this.listLyDoTon = [];
        this.getDataLyDoTon(data);
      },
    },
    ckbDaCap: {
      handler: function (data) {
        if (data == false) {
          this.tinhTrangVTTHSelected = "";
          this.statusCkbTKTinhtrangVT = false;
        }
      },
    },
    rdioCKb: {
      handler: async function (data) {
        this.params.p_id = this.loaiNhomSelected == 1 ? 1 : 2;
        await this.LoadThongTinThueBao("");
        if (data.toString() != "6") {
          this.statusCkbDaCap = true;
          this.statusGrcHuy = false;
          this.templateXoaLSVT_show = true;
        } else {
          this.templateXoaLSVT_show = false;
        }
        this.colCheckbox = this.TSMD.capvt_nhieudong && data == "2"        
      },
    },
    value_KhoSelected: {
      handler: async function () {
        this.listVatTuTrongKho = [];
        if (this.params.thueBaoId !== 0 || this.params.thueBaoId != null) {
          if (
            this.params.loaitb_id != 0 &&
            this.kieuTBSelected.toString() != "3"
          ) {
            await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
          } else {
            this.listVatTuTrongKho = [];
          }
        }
      },
    },
    ckbLocVT: {
      handler: async function () {
        this.listVatTuTrongKho = [];
        if (this.params.thueBaoId !== 0 || this.params.thueBaoId != null) {
          if (this.params.loaitb_id != 0) {
            await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
          } else {
            this.listVatTuTrongKho = [];
          }
        }
      },
    },
    btnNhapMoi_Enabled: {
      handler: async function (data) {
        console.log("btnNhapMoi_Enabled =", data);
      },
    },
    kieuTBSelected: {
      handler: async function (data) {
        if (data.toString() == "1") {
          this.params.pnlThuHoi = false;
          this.statusBtnTuDongCap = true;
          this.show_ckbLocVT = true;
          this.btnNhapMoi_Visible = false;

          this.readonlyTxtSerial = false;
          this.xtraTabPage1 = true;
          this.xtraTabPage2 = false;
          this.btnNhapMoi_Enabled = false;
          this.statusBtnHuy = false;
          await this.fetchDMTinhTrangTBCap();
          this.listTinhTrangVTTH = this.listTinhTrangTBCap;          
          this.Clear();
        } else {
          this.show_ckbLocVT = false;
          this.statusBtnTuDongCa = false;
          this.params.pnlThuHoi = true;

          await this.fetchDSVatTuThueBaoSuDung(this.params.vthuebao_id);
          this.readonlyTxtSerial = true;
          this.xtraTabPage1 = false;
          this.xtraTabPage2 = true;
          this.btnNhapMoi_Enabled = false;
          this.btnNhapMoi_Visible = true;
          this.txtSerialCu = "";
          this.statusSoluong_cu = false;
          this.readonlyTenTbSelected = false;
          this.statusBtnHuy = true;
          this.statusTxtSerialCu = false;
          await this.fetchDMTinhTrangVTThuHoi();
        }
      },
    },
    loaiHDSelected: {
      handler: async function (data) {
        console.log("loaiHDSelected = ", data);
        this.listVatTuTrongKho = [];
        if (this.params.thueBaoId !== 0 || this.params.thueBaoId != null) {
          if (this.params.loaitb_id != 0) {
            await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
          } else {
            this.listVatTuTrongKho = [];
          }
        }
      },
    },
    listVatTuTrongKho_Checked: function (value) {
      this.listVatTuTrongKho_Checked = value;
      console.log("listVatTuTrongKho_Checked == ", value);
      for (const i in this.listVatTuTrongKho) {
        if (
          this.listVatTuTrongKho_Checked.includes(
            this.listVatTuTrongKho[i].TUVT_ID
          ) &&
          this.listVatTuTrongKho[i].LUONGCAP == 0
        ) {
          this.listVatTuTrongKho[i].LUONGCAP = this.listVatTuTrongKho[i].SL_TON;
        } else if (
          !this.listVatTuTrongKho_Checked.includes(
            this.listVatTuTrongKho[i].TUVT_ID
          )
        ) {
          this.listVatTuTrongKho[i].LUONGCAP = 0;
        }
      }
      if (this.listVatTuTrongKho_Checked.length > 0) {
        this.$refs.DSVatTuTrongKho.grid.refreshColumns();
      }
    },
    listVatTuTrongKho: function (value) {
      this.listVatTuTrongKho_Checked = [];
    },
    listThueBaoCapVT: function (value) {
      this.colMaGD_Visible = this.$auth.getPhanVungID() == 21 ? true : false;
      this.checkAllThueBao = false
      this.rowSelectedThueBao = [];
      if (!value) {
        this.listVatTuDaCap = [];
        this.listVatTuTBDaSuDung = [];
        this.listVatTuTrongKho = [];
      }
    },
    listMucDichCap: function (val) {   
      if (val.length > 0 ) {
        this.mucDichCapSelected = val[0].value;
      }
    },
    mucDichCapSelected: {
      handler: async function (data) {        
      },
    },

    checkAllThueBao: async function (value) {     
      this.rowSelectedThueBao = [];
      if (value) {
        for (const e of this.$refs.DSThueBaoCapVT.filteredDataSource) {
          this.rowSelectedThueBao.push(e.PHIEU_ID);
        }
      }                  
    },
    rowSelectedThueBao: async function (value) {     
      this.rowSelectedThueBao = value;           
    },
    
  },
  data() {
    return {
      template: huyTemplate,
      tempThuHoi: thuHoiTemplate,
      suaTemplate: suaVTTemplate,
      templateXoaLSVT: xoaLSVTTemplate,
      templateFile: function () {
        return {
          template: {
            template: `
                          <div class="">
                              <button class="btn btn-main pad3 lh14" @click="UploadFile(value)">
                                <span class="-ap one-upload"></span>
                              </button>
                              <button class="btn btn-main pad3 lh14" @click="ShowFile(value)">
                                <span class="-ap one-file-check"></span>
                              </button>
                              <button class="btn btn-main pad3 lh14" @click="DeleteFile(value)">
                                <span class="-ap icon-close"></span>
                              </button>
                          </div>
                      `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent.$parent;
              },
              value() {
                return this.data;
              },
            },
            methods: {
              UploadFile(data) {                
                this.parent.UploadFile(data);
              },
              ShowFile(data) {                
                this.parent.ShowFile(data);
              },
              DeleteFile(data) {                
                this.parent.DeleteFile(data);
              },
            },
          },
        };
      },
      // templateSelected: gridVatTuSDTemplate,
      gridVatTuSDTemplate: function () {
        return {
          template: {
            template: `
                    <div class="">
                        <input
                          type="checkbox"
                          class="uncheck"
                          v-model="parent.listVatTuTrongKho_Checked"
                          :value="data.TUVT_ID"
                        />
                    </div>
                `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
            methods: {},
          },
        };
      },
      header: {
        title: "Cấp vật tư cho thuê bao (TT)",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Vật tư thuê bao",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      listLoaiNhom: [
        {
          text: "Thi công",
          value: 1,
        },
        {
          text: "Báo hỏng",
          value: 2,
        },
      ],
      loaiNhomSelected: 1,
      listLoaiHDvaPL: [],
      loaiHDSelected: "",
      //Dịch vụ
      listDichVu: [],
      value_DichVuSelected: "",
      text_DichVuSelected: "",
      statusDichVu: false,
      //Số máy/Acc
      soMaySelected: "",
      ThueBaoFocus: {},
      //Từ ngày
      tuNgay: new Date(),
      showTuNgay: false,
      //Đến ngày
      denNgay: new Date(),
      //Nhân viên
      listNhanVien: [],
      value_NhanVienSelected: "",
      text_NhanVienSelected: "",
      statusCbxNhanVien: false,
      //Kho
      listKho: [],
      value_KhoSelected: "",
      text_KhoSelected: "",
      statusCbxKho: false,
      //Kiểu thiết bị
      listKieuTB: [],
      kieuTBSelected: "",
      //Ngày thực hiện
      ngayTH: new Date(),
      NDChuyenTo: "",
      //Kết quả thu hồi
      ketQuaThuHoi: [
        {
          text: "",
          value: 0,
        },
        {
          text: "Thu hồi được",
          value: 1,
        },
        {
          text: "Không thu hồi được",
          value: 3,
        },
      ],
      kqThuHoiSelected: 0,
      //Tình trạng thiết bị
      listTinhTrangTBCap: [],
      tinhTrangTBCapSelected: "",
      tinhTrangTBSelected: "",
      //Lý do tồn
      listLyDoTon: [],
      lyDoTonSelected: "",
      statusLyDoTon: true,
      //Lý do cấp
      listLyDoCap: [],
      lyDoCapSelected_value: "",
      lyDoCapSelected_text: "",
      listMucDichCap: [],
      mucDichCapSelected: "",
      ds_lydoTC: [],
      ds_lydoBH: [],
      statusLyDoCap: false,
      //Trang bị
      trangBiSelected: "",
      listTrangBi: [],
      //Tên thiết bị
      listTenTb: [],
      value_tenTbSelected: "",
      text_tenTbSelected: "",
      disabledTenTb: false,
      statusTenTb: false,
      readonlyTenTbSelected: true,
      //Danh sách thuê bao hide/show
      status_TTTCCT: true,
      //Quyền người dùng
      quyenNguoiDung: [],
      //Radio button
      rdioCKb: 0,
      //Checkbox Đã cấp vật tư
      ckbDaCap: false,
      statusCkbDaCap: true,
      // Dánh sách thuê bao
      listThueBaoCapVT: [],
      //Button Cập nhật
      statusBtnCapNhap: true,
      //Button Nhập mới
      btnNhapMoi_Enabled: false,
      btnNhapMoi_Visible: false,
      //Button Hoàn Thanh
      statusBtnHoanThanh: true,
      params: {
        demHC: 0,
        kieugoi_id: 0,
        Tag: "",
        boqua: false,
        boqua_baohong: false,
        p_id: 0,
        t_hoi: 0,
        vsdvt_id: 0,
        thueBaoId: 0,
        hdtb_id: 0,
        ok: true,
        v_phieuvt_id: 0,
        baohong_id: 0,
        phieu_id: 0,
        phieu_cha_id: 0,
        pnlThuHoi: false,
        phvt_id: 0,
        sl_goc: 0,
        vphieuvt_id: 0,
        loaitb_id: 0,
        vtuvt_id: 0,
        vsoluong: 0,
        sdvt_xoa: 0,
        ktbi: 0,
        ngay_ht: "",
        loaihd_id: 0,
        vt_dccap: [],
        vdichvuvt_id: "",
        vthuebao_id: 0,
        kt_th: false,
        loaitbi_id: 0,
        check_MA_TB: "",
        ma_tb_cur: "",
        ngay_hc: "",
        dtthuebao: [],
        ds_error: [],
        tentb: "",
        checkModal: 0,
      },
      colCheckbox: false,
      TSMD: {
        det: [],
        check_serial: "",
        capvt_nhieudong: false,
        UPDATE_CTS: false,
        chan_tkdu: false,
        canhbao_sl_thuhoi_vt: false,
        capvt_nhieudong: false
      },
      //DM thao tác
      listThaoTac: [],
      //Checkbox + Combobox Tình trạng VT
      ckbTKTinhtrangVT: false,
      cboTKTinhTrangVT: [],
      cboTKTinhTrangVTSelected: "",
      // Tình trạng vật tư thu hồi
      listTinhTrangVTTH: [],
      tinhTrangVTTHSelected: "",
      statusCkbTKTinhtrangVT: false,
      //Đếm số lượng phiếu
      count: {
        daGiao: 0,
        choDuyet: 0,
        traLai: 0,
        boSung: 0,
      },
      //Ghi chú
      txtGhiChu: "",
      //Số lượng
      soluong_cu: 0,
      statusSoluong_cu: true,
      //Serial
      txtSerialCu: "",
      statusTxtSerialCu: true,
      //Danh sách thuê bao
      dsTB: {
        txtTenTB: "",
        txtDiaChiLD: "",
        txtKieuLD: "",
        txtSoAo: "",
      },
      //Nút Hủy
      statusBtnHuy: false,
      // Danh sách Vât tư đã cấp
      listVatTuDaCap: [],
      //Danh sách thuê bao đã sử dụng
      listVatTuTBDaSuDung: [],
      //Checkbox Chưa dùng
      ckbLocVT: false,
      show_ckbLocVT: true,
      //Danh sách vật tư trong kho
      listVatTuTrongKho: [],
      listVatTuTrongKho_Checked: [],
      xtraTabPage1: true,
      xtraTabPage2: false,
      //Ngày bắt đầu dùng thử
      dateDungThu: "",
      // Trạng thái nút HỦY template
      statusGrcHuy: false,
      // Danh sách nhân viên
      listNhanVienTheoPhieuID: [],
      editSettings: { allowEditing: true, mode: "Batch" },
      tinhTrangHongVT: "",
      tinhTrangDinhMuc: "",
      tinhTrangKIEMTRA_VTCAPRA_V2: "",
      tinhTrang_KIEMTRA_VTDACAPTB: "",
      statusThemMoiPhieuCapVT: "",
      listThamSoMacDinh: [],
      thamSoMacDinh: "",
      //Checkbox Tìm theo Serial
      ckbSerial: false,
      statusKiemTraVatTuThuHoi: [],
      thuHoi: {
        dt: [],
        dt_tbidacap: [],
        ds: [],
        dt_sd: [],
        dt_tbidacap_sd: [],
        slvt: [],
        ktsl: [],
        kt_sl: [],
      },
      //Cập nhật sử dụng vật tư thu hồi
      statusCapNhatSuDungVT_TH: "",
      //Cập nhật phiếu vật tư thu hồi
      statusCAPNHAT_PHIEUVT_THUHOI: "",
      //Button Tự động cấp
      statusBtnTuDongCap: false,
      //MAP_ID
      MAP_ID: {
        getMapID: "",
        vuser_alt: "",
        vpass_alt: "",
        vuser_xtest: "",
        vpass_xtest: "",
      },
      //Đo kiểm
      statusDoKiem: "",
      //Kiểm tra mã thuê bao có tồn tại
      statusKTMaTBTonTai: "",
      //LAY_PORT_THEO_TBID
      dtPort: [],
      ds_ss: "",
      ds_ss_ONT: "",
      tamung: [],
      //ht_phieu_kkhongvt
      value_ht_phieu_kkhongvt: "",
      //Danh mục thao tác
      danhMucThaoTac_value: [],
      //Serial (Danh sách vật tư trong kho)
      readonlyTxtSerial: false,
      txtSerial: "",
      //Kiểm tra thuê bao đã thu hồi vật tư
      value_KT_TB_DA_ThuHoiVT: [],
      //Kiểm tra thuê bao báo hỏng đã thu hồi vật tư
      value_KT_TB_BaoHong_DA_ThuHoiVT: [],
      //Giá trị đo kiểm
      giaTriDoKiem: "",
      //Thông tin phiếu cấp vật tư thuê bao
      thongTinPhieuCapVTTB: "",
      //Thông tin phiếu cấp vật tư thuê bao báo hỏng
      thongTinPhieuCapVTTB_BaoHong: "",
      //Kiểm tra SR GPON
      value_KT_SR_GPON: "",
      value_CapNhatThongTinThietBi: "",
      //Xóa lý do
      value_deleteLyDo: "",
      //Thông tin modem CTS
      thongTinModemCTS: [],
      //Giao phiếu truyền dẫn, tạo phiếu quy trình vật tư
      value_GIAOPHIEU_TD_TAOPHIEU_QLVT: "",
      frmTestPortV2_ma_tb: "",
      frmTestPortV2_vthuebao_id: "",
      frmTestPortV2_kieu: 0,
      selectedMaGD: "",
      value_Update_CTS: "",
      value_updateKQGiaoPhieu: "",
      value_themMoiDSPhieuTon: "",
      value_kt_thuhoi_khongvt: "",
      value_KT_hoanthanh_khong_VT: false,
      value_KiemTraCanhBaoSLThuHoi: "",
      value_DSTheoSerialOrSoAcc: {},
      doiSerial: {
        vatTuId: "",
        serialCu: "",
        checkActions: 0,
      },
      isShowInBB: false,
      isShowXuatExcel: false,
      listLoaiHDMucDich: [],
      colMaGD_Visible: true,
      colKenhTn_Visible: true,
      colTinhTrang_Visible: true,
      checkPortDialog: {
        isVisiable: false,
        input: {
          vma_tb: null,
          vthuebao_id: null,
          vkieu: null,
        },
      },
      list_pthuHoi: [],
      check_pthuHoi: false,
      templateXoaLSVT_show: false,
      checkAllThueBao: false,
      colCheckboxAllThueBao: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllThueBao" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      rowSelectedThueBao : [],
      colCheckboxThueBao: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowSelectedThueBao"
                                    :value="data.PHIEU_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
    };
  },
  methods: {
    ...mapActions("thuhoivattuthuebao", [
      "KiemTraVatTuThuHoi",
      "getKiemTraCanhBaoSLThuHoi",
    ]),
    ...mapActions("capvattuthuebao", [
      "getLoaiHDvaPL",
      "getDMDichVu",
      "getDMNhanVien",
      "getDMKho",
      "getDMKieuTB",
      "getDMTinhTrangTBCap",
      "getDMLyDoTon",
      "getDMLyDoCap",
      "getDMTrangBi",
      "getDMMucDichLyDo",
      "getDSLoaiHopDongMucDich",
      "getDMTenThietBi",
      "getQuyenNguoiDungCVTTB",
      "getDSThueBaoCapVTTruyenSoLieu",
      "getDSThueBaoCapVT",
      "getDMThaoTac",
      "getDMTinhTrangVTThuHoi",
      "getDSVatTuDaCap",
      "getDSVatTuThueBaoSuDung",
      "getDSVatTuTrongKho",
      "kiemTraHuyPhieuGiao",
      "getNgayBatDauDungThu",
      "themMoiPhieuCapVT",
      "getKeyDKVTMuaSam",
      "getDSNhanVienTheoID",
      "deleteLichSuVT",
      "getKTTTHongVT",
      "KiemTraDinhMuc",
      "KIEMTRA_VTCAPRA_V2",
      "KIEMTRA_VTDACAPTB",
      "getDSThamSoMacDinh",
      "getDSTBSuDungVT",
      "getThongTinTBDaCapTheoSerial",
      "KTSoLuongVTTBDangSD",
      "KTSoLuongVTTBDangSD_v2",
      "CapNhatSuDungVT_TH",
      "getSL_DD",
      "getSL_HT",
      "CAPNHAT_PHIEUVT_THUHOI",
      "getThamSoMacDinh",
      "getMAP_ID",
      "KTMaTBCoTonTai",
      "LAY_PORT_THEO_TBID",
      "LAY_DS_PORT_THEOHD_TB",
      "testPort",
      "getInfoTamUngVT",
      "ht_phieu_kkhongvt",
      "KT_TB_DA_ThuHoiVT",
      "KT_TB_BaoHong_DA_ThuHoiVT",
      "getGiaTriDoKiem",
      "getThongTinPhieuCapVTTB",
      "getThongTinPhieuCapVTTB_BaoHong",
      "KT_SR_GPON",
      "get_TT_ONT_Theo_Account",
      "deleteLyDo",
      "getThongTinModemCTS",
      "GIAOPHIEU_TD_TAOPHIEU_QLVT",
      "updateCTS",
      "taoDL_Ton_Khong_ThuHoi_Duoc",
      "updateKQGiaoPhieu",
      "themMoiDSPhieuTon",
      "kt_thuhoi_khongvt",
      "getDSTheoSerialOrSoAcc",
      "getParamsBaoCao",
      "btnCapVatTu",
      "capVatTuV2",
      "hoanCongCapVatTuTB",
      "hoanCongDanhSach",
      "ChuyenToThiCong",
      "getHinhAnh",
      "downloadFile",
      "getLinkFile",
      "checkFileExist",
      "deleteFile",
      "updateHinhAnh",
      "uploadFile"

    ]),
    async UploadFile(data) {                
      this.$refs["ref-choose-file"].$el.querySelector("input").click();
    },
    async ShowFile(data) {                
    
      let img = await this.getHinhAnh(data.PHIEUVT_ID) //data.PHIEUVT_ID
      if(img == null) {
        this.$toast.error("Không có hình ảnh!")
        return
      }      
      var checkFile = await this.checkFileExist({fileSource : img})        
      var linkFile = ""
      if(checkFile.message == "Success" && checkFile.data.data){
        let getLink = await this.getLinkFile({fileSource : img}) 
        linkFile = getLink.data.name                 
      }else{  
        linkFile  = "http://10.70.52.164:8081/FILE/" + this.$auth.getMaTinh() + "_VT/" + img    
      }
      const downloadLink = document.createElement("a");      
      downloadLink.href = linkFile;    
      downloadLink.download = img;    
      downloadLink.target = "_blank";
      downloadLink.click();   
    },
    async DeleteFile(data) {                
      
      let img = await this.getHinhAnh(data.PHIEUVT_ID) //data.PHIEUVT_ID
      if(img != null) {
        var deleteFile = await this.deleteFile({ fileSource: img });
      }      
      var ketQua = await this.updateHinhAnh({
        linkFile : "",
        phieuVtId : data.PHIEUVT_ID
      })     
      this.$toast.success("Xóa thành công!")      
    },
    async onChangeFile() {
      try{
        let fileChooser = this.$refs["ref-choose-file"];
        let choosedFiles = fileChooser.$el.querySelector("input").files;
        if (choosedFiles.length == 0) {
          fileChooser.reset();
          return;
        }
        choosedFiles = [...choosedFiles];
        fileChooser.reset();

        this.loading(true);
        let formData = new FormData();
        for (let i = 0; i < choosedFiles.length; i++) {          
          let item = choosedFiles[i];         
          formData.append("file", item);
        }        
        let paths = await this.uploadFile(formData);

        if (paths.length == 0) {
          this.$toast.error("Upload file thất bại!");
          this.loading(false);
          return;
        }
        let linkFile = ""
        for (let i = 0; i < paths.length; i++) {
          if (paths[i].status) {            
            linkFile = paths[i].message.relativeUrl
            break
          }
        }
        var ketQua = await this.updateHinhAnh({
          linkFile : linkFile,
          phieuVtId : this.params.vphieuvt_id
        })     
        this.$toast.success("Upload file thành công!");        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },
    onFilteringKho(e) {
      var query = new Query();
      query =
        e.text !== ""
          ? query.where("TEN_K_NEW", "contains", e.text, true)
          : query;
      e.updateData(this.listKho, query);
    },
    async CapVatTuTheoPhieuFocus(_dsVt) {
      if (this.ThueBaoFocus == {}) {
        this.$toast.error("Chưa chọn thuê bao!");
        return;
      }
      console.log(this.ThueBaoFocus, " = ThueBaoFocus");
      this.loading(true);
      let data = {
        nhom_id: this.loaiNhomSelected,
        phieu_id: this.ThueBaoFocus.PHIEU_ID,
        ngay_cap: moment(this.ngayTH).format("DD/MM/YYYY HH:mm:ss"),
        kieutbi_id: this.kieuTBSelected,
        trangbi_id: this.trangBiSelected,
        lydocap_id: this.lyDoCapSelected_value,
        mucdich_id: this.mucDichCapSelected,
        tinhtrang_tbi: this.tinhTrangTBCapSelected,
        kho_id: this.value_KhoSelected,
        nhanvien_id: this.$root.token.getNhanVienID(),
        ds_vattu: _dsVt,
        loaiphieu: this.rdioCKb,
      };
      try {
        this.loading(true);
        const response = await this.capVatTuV2(data);
        if (response.error_code === "BSS-00000000") {
          this.$toast.success("Thành công!");
          await this.reloadLSVT();
          if (this.listKho.length > 0) {
            await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
          }
          this.listVatTuTrongKho_Checked = [];
        }
      } catch (ex) {
        this.$toast.error(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetch_BtnCapVatTu(_dsVt) {
      this.loading(true);
      let data = {
        nhom_id: this.loaiNhomSelected,
        phieu_id: this.params.phieu_id,
        ngay_cap: moment(this.ngayTH).format("DD/MM/YYYY HH:mm:ss"),
        kieutbi_id: this.kieuTBSelected,
        trangbi_id: this.trangBiSelected,
        lydocap_id: this.lyDoCapSelected_value,
        mucdich_id: this.mucDichCapSelected,
        tinhtrang_tbi: this.tinhTrangTBCapSelected,
        kho_id: this.value_KhoSelected,
        nhanvien_id: this.$root.token.getNhanVienID(),
        ds_vattu: _dsVt,
      };
      try {
        this.loading(true);
        const response = await this.btnCapVatTu(data);
        if (response.error_code === "BSS-00000000") {
          this.$toast.success("Thành công!");
          await this.reloadLSVT();
          if (this.listKho.length > 0) {
            await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
          }
          this.listVatTuTrongKho_Checked = [];
        }
      } catch (ex) {
        this.$toast.error(ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    async fetch_DSTheoSerialOrSoAcc(_serial) {
      this.loading(true);
      let data = {
        serial: _serial,
      };
      const response = await this.getDSTheoSerialOrSoAcc(data);
      // console.log("fetch_DSTheoSerialOrSoAcc = ", response.data);
      if (response.error_code === "BSS-00000000") {
        this.value_DSTheoSerialOrSoAcc = response.data;
      }
      this.loading(false);
    },
    async fetch_KiemTraCanhBaoSLThuHoi() {
      this.loading(true);
      let data = {
        hdTbId: this.params.hdtb_id,
        baoHongId: this.params.baohong_id,
      };
      const response = await this.getKiemTraCanhBaoSLThuHoi(data);
      if (response.error_code === "BSS-00000000") {
        this.value_KiemTraCanhBaoSLThuHoi = response.data;
      }
      this.loading(false);
    },

    async fetch_kt_thuhoi_khongvt() {
      this.loading(true);
      let data = {
        phieuId: this.params.phieu_id,
        hdTbId: this.params.hdtb_id,
        baoHongId: this.params.baohong_id,
        loaiNhom: this.loaiNhomSelected,
        kieuTbi: this.kieuTBSelected,
        kqxl: this.kqThuHoiSelected,
        quyenGiamDoc: this.quyenNguoiDung.includes(2506).toString(),
      };
      const response = await this.kt_thuhoi_khongvt(data);
      if (response.error_code === "BSS-00000000") {
        this.value_kt_thuhoi_khongvt = response.data;
      }
      this.loading(false);
    },

    async fetch_themMoiDSPhieuTon(_id, _kieu) {
      try {
        this.loading(true);
        let data = {
          phieuId: this.params.phieu_id,
          lyDoTonId: this.lyDoTonSelected,
          nhanVienBtId: this.$root.token.getNhanVienID(),
          donViBtId: this.$root.token.getDonViID(),
          nguoiCn: this.$root.token.getUserName(),
          ngayCn: moment(new Date()).format("DD/MM/yyyy HH:mm:ss"),
          mayCn: "",
          ipCn: "",
          ghiChu: "",
        };
        const response = await this.themMoiDSPhieuTon(data);
        if (response.error_code === "BSS-00000000") {
          this.value_themMoiDSPhieuTon = response.data;
        }
      } catch (ex) {
        console.log(ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    async fetch_updateKQGiaoPhieu(_id, _kieu) {
      this.loading(true);
      let data = {
        phieuId: this.params.phieu_id,
      };
      const response = await this.updateKQGiaoPhieu(data);
      if (response.error_code === "BSS-00000000") {
        this.value_updateKQGiaoPhieu = response.data;
      }
      this.loading(false);
    },

    async fetch_taoDL_Ton_Khong_ThuHoi_Duoc(_id, _kieu) {
      this.loading(true);
      let data = {
        phieuId: this.params.phieu_id,
        lyDoTonId: this.lyDoTonSelected,
        nhanVienBtId: this.$root.token.getNhanVienID(),
        donViBtId: this.$root.token.getDonViID(),
        nguoiCn: this.$root.token.getUserName(),
        ngayCn: moment(new Date()).format("DD/MM/yyyy HH:mm:ss"),
        mayCn: "",
        ipCn: "",
        ghiChu: "",
      };
      const response = await this.taoDL_Ton_Khong_ThuHoi_Duoc(data);
      if (response.error_code === "BSS-00000000") {
        this.value_taoDL_Ton_Khong_ThuHoi_Duoc = response.data;
      }
      this.loading(false);
    },

    async fetch_UpdateCTS(_id, _kieu) {
      this.loading(true);
      let data = {
        id:
          this.params.hdtb_id > 0
            ? this.params.hdtb_id
            : this.params.baohong_id,
        kieu: this.params.hdtb_id > 0 ? 1 : 0,
        ngayHt: moment(this.params.ngay_hc).format("DD/MM/yyyy").toString(),
      };
      const response = await this.updateCTS(data);
      if (response.error_code === "BSS-00000000") {
        this.value_Update_CTS = response.data;
      }
      this.loading(false);
    },

    async fetch_GIAOPHIEU_TD_TAOPHIEU_QLVT(_kieu) {
      this.loading(true);
      try {
        let data = {
          phieuId: this.params.phieu_con_id,
          hdTbId: this.params.hdtb_id,
          baoHongId: this.params.baohong_id,
          donViGiaoId: this.$root.token.getDonViID(),
          kieu: _kieu,
          nhanVienId: this.$root.token.getNhanVienID(),
          nguoiCn: this.$root.token.getUserName(),
          mayCn: "",
          ipCn: "",
          kieuGoiId: this.params.kieugoi_id,
        };
        const response = await this.GIAOPHIEU_TD_TAOPHIEU_QLVT(data);
        // console.log("GIAOPHIEU_TD_TAOPHIEU_QLVT_checkkkkk = ", response);
        if (response.error_code === "BSS-00000000") {
          this.value_GIAOPHIEU_TD_TAOPHIEU_QLVT = response.data;
          this.params.demHC += 1;
        }
        // console.log("Cộngthis.params.demHC = ", this.params.demHC);
      } catch (ex) {
        this.$root.toastError("Có lỗi xảy ra khi Hoàn công");
      } finally {
        this.loading(false);
      }
    },

    async fetchThongTinModemCTS() {
      this.loading(true);
      let data = {
        id:
          this.params.hdtb_id > 0
            ? this.params.hdtb_id
            : this.params.baohong_id,
        kieu: this.params.hdtb_id > 0 ? 1 : 0,
      };
      const response = await this.getThongTinModemCTS(data);
      if (response.error_code === "BSS-00000000") {
        this.thongTinModemCTS = response.data;
      }
      this.loading(false);
    },

    async fetch_deleteLyDo(_hdTbId, _baoHongId) {
      this.loading(true);
      let data = {
        phieuId: this.params.phieu_id,
        hdTbId: _hdTbId,
        baoHongId: _baoHongId,
        mucDichId: this.mucDichCapSelected,
      };
      const response = await this.deleteLyDo(data);
      if (response.error_code === "BSS-00000000") {
        this.value_deleteLyDo = response.data;
      }
      this.loading(false);
    },

    async fetch_KT_SR_GPON(_serialGp) {
      this.loading(true);
      let data = {
        phieuId: this.params.phieu_id,
        hdTbId: this.params.hdtb_id,
        baoHongId: this.params.baohong_id,
        serialGp: _serialGp,
      };
      const response = await this.KT_SR_GPON(data);
      if (response.error_code === "BSS-00000000") {
        this.value_KT_SR_GPON = response.data;
      }
      this.loading(false);
    },

    async fetch_getThongTinPhieuCapVTTB_BaoHong() {
      this.loading(true);
      let data = {
        baoHongId: this.params.baohong_id,
      };
      const response = await this.getThongTinPhieuCapVTTB_BaoHong(data);
      if (response.error_code === "BSS-00000000") {
        this.thongTinPhieuCapVTTB_BaoHong = response.data;
      }
      this.loading(false);
    },

    async fetch_getThongTinPhieuCapVTTB() {
      this.loading(true);
      let data = {
        hdTbId: this.params.hdtb_id,
      };
      const response = await this.getThongTinPhieuCapVTTB(data);
      if (response.error_code === "BSS-00000000") {
        this.thongTinPhieuCapVTTB = response.data;
      }
      this.loading(false);
    },

    async fetch_GiaTriDoKiem() {
      this.loading(true);
      const response = await this.getGiaTriDoKiem();
      if (response.error_code === "BSS-00000000") {
        this.giaTriDoKiem = response.data;
      }
      this.loading(false);
    },

    async fetch_KT_TB_BaoHong_DA_ThuHoiVT() {
      this.loading(true);
      let data = {
        baoHongId: this.params.baohong_id,
        phieuId: this.params.phieu_id,
      };
      const response = await this.KT_TB_BaoHong_DA_ThuHoiVT(data);
      if (response.error_code === "BSS-00000000") {
        this.value_KT_TB_BaoHong_DA_ThuHoiVT = response.data;
      }
      this.loading(false);
    },

    async fetch_KT_TB_DA_ThuHoiVT() {
      this.loading(true);
      let data = {
        hdTbId: this.params.hdtb_id,
        phieuId: this.params.phieu_id,
      };
      const response = await this.KT_TB_DA_ThuHoiVT(data);
      if (response.error_code === "BSS-00000000") {
        this.value_KT_TB_DA_ThuHoiVT = response.data;
      }
      this.loading(false);
    },

    async fetch_ht_phieu_kkhongvt() {
      this.loading(true);
      let data = {
        phieuId: this.params.phieu_id,
        hdTbId: this.params.hdtb_id,
        baoHongId: this.params.baohong_id,
        nguoiCn: this.$root.token.getUserName(),
      };
      const response = await this.ht_phieu_kkhongvt(data);
      if (response.error_code === "BSS-00000000") {
        this.value_ht_phieu_kkhongvt = response.data;
      }
      this.loading(false);
    },

    async fetch_getInfoTamUngVT(_serial) {
      this.loading(true);
      let data = {
        nhanVienId: this.$root.token.getNhanVienID(),
        serial: _serial.toUpperCase(),
      };
      const response = await this.getInfoTamUngVT(data);
      if (response.error_code === "BSS-00000000") {
        this.tamung = response.data;
      }
      this.loading(false);
    },

    async fetch_TT_ONT_Theo_Account(
      _userName,
      _passwordPort,
      _inputPort,
      _iServerDk,
      _ip,
      _rack,
      _shelf,
      _slot,
      _sport,
      _onuId
    ) {
      this.loading(true);
      let data = {
        userName: _userName,
        passwordPort: _passwordPort,
        inputPort: _inputPort,
        mac: "0",
        ip: _ip,
        rack: _rack,
        shelf: _shelf,
        slot: _slot,
        sport: _sport,
        onuId: _onuId,
        iServerDk: _iServerDk,
      };
      const response = await this.get_TT_ONT_Theo_Account(data);
      if (response.error_code === "BSS-00000000") {
        this.ds_ss_ONT = response.data;
      }
      this.loading(false);
    },

    async fetchTestPort(
      _userName,
      _passwordPort,
      _inputPort,
      _iServerDk,
      _ip,
      _rack,
      _shelf,
      _slot,
      _sport,
      _onuId,
      _loaiTbiId,
      _thamSo
    ) {
      this.loading(true);
      let data = {
        userName: _userName,
        passwordPort: _passwordPort,
        inputPort: _inputPort,
        mac: "0",
        ip: _ip,
        rack: _rack,
        shelf: _shelf,
        slot: _slot,
        sport: _sport,
        onuId: _onuId,
        iServerDk: _iServerDk,
        loaiTbiId: _loaiTbiId,
        thamSo: _thamSo,
      };
      const response = await this.testPort(data);
      if (response.error_code === "BSS-00000000") {
        this.ds_ss = response.data;
      }
      this.loading(false);
    },

    async fetch_LAY_DS_PORT_THEOHD_TB(_hdtbId, _thueBaoId) {
      this.loading(true);
      let data = {
        hdTbId: _hdtbId,
        thueBaoId: _thueBaoId,
      };
      const response = await this.LAY_DS_PORT_THEOHD_TB(data);
      // console.log("LAY_DS_PORT_THEOHD_TB response = ", response.data[0]);
      if (response.error_code === "BSS-00000000") {
        if (response.data.length > 0) {
          this.dtPort = response.data;
        } else {
          this.dtPort = [];
        }
      }
      this.loading(false);
    },

    async fetch_LAY_PORT_THEO_TBID(_hdtbId, _thueBaoId) {
      this.loading(true);
      let data = {
        hdtbId: _hdtbId,
        thueBaoId: _thueBaoId,
      };
      const response = await this.LAY_PORT_THEO_TBID(data);
      if (response.error_code === "BSS-00000000") {
        this.dtPort = response.data;
      }
      this.loading(false);
    },

    async fetchKTMaTBCoTonTai() {
      this.loading(true);
      let data = {
        maThueBao: this.params.ma_tb_cur,
      };
      const response = await this.KTMaTBCoTonTai(data);
      if (response.error_code === "BSS-00000000") {
        this.statusKTMaTBTonTai = response.data;
      }
      this.loading(false);
    },

    async fetch_MAP_ID() {
      this.loading(true);
      const response = await this.getMAP_ID();
      if (response.error_code === "BSS-00000000") {
        this.getMapID = response.data;
      }
      this.loading(false);
    },

    async fetchThamSoMacDinh() {
      this.loading(true);
      let data = {
        maTs: "SERVER_DK",
        nhanvienId: this.$root.token.getNhanVienID(),
      };
      const response = await this.getThamSoMacDinh(data);
      if (response.error_code === "BSS-00000000") {
        this.thamSoMacDinh = response.data;
      }
      this.loading(false);
    },

    async fetch_CAPNHAT_PHIEUVT_THUHOI(
      _insert,
      _phieuVtId,
      _serial,
      _thietBiId,
      _kieu
    ) {
      this.loading(true);
      let data = {
        sdVtId: this.params.vsdvt_id,
        insert: _insert,
        phieuVtId: _phieuVtId,
        hdTbId: this.params.hdtb_id,
        baoHongId: this.params.baohong_id,
        phieuId: this.params.phieu_id,
        thueBaoId: this.params.thueBaoId,
        donViId: this.$root.token.getDonViID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "",
        ipCn: "",
        tinhTrangTbiId: this.tinhTrangTBSelected,
        serial: _serial,
        ngayTh: moment(this.ngayTH).format("DD/MM/yyyy HH:mm:ss").toString(),
        vatTuId: this.value_tenTbSelected,
        thietBiId: _thietBiId,
        soLuong: parseFloat(this.soluong_cu),
        lyDoCapId: this.mucDichCapSelected,
        lyDoCapTbiId: this.lyDoCapSelected_value,
        trangBiId: this.trangBiSelected,
        kieu: _kieu,
        ghiChu: this.txtGhiChu,
      };
      const response = await this.CAPNHAT_PHIEUVT_THUHOI(data);
      if (response.error_code === "BSS-00000000") {
        this.statusCAPNHAT_PHIEUVT_THUHOI = response.data;
      }
      this.loading(false);
    },

    async fetch_getSL_HT() {
      this.loading(true);
      let data = {
        sdVtId: this.params.vsdvt_id,
      };
      const response = await this.getSL_HT(data);
      if (response.error_code === "BSS-00000000") {
        this.thuHoi.kt_sl = response.data;
      }
      this.loading(false);
    },

    async fetch_getSL_DD() {
      this.loading(true);
      let data = {
        sdVtId: this.params.vsdvt_id,
      };
      const response = await this.getSL_DD(data);
      if (response.error_code === "BSS-00000000") {
        this.thuHoi.ktsl = response.data;
      }
      this.loading(false);
    },

    async fetchCapNhatSuDungVT_TH(
      _kieu,
      _vatTuId,
      _xoaVt,
      _kieuPvt,
      _serial,
      _serialNew
    ) {
      this.loading(true);
      let data = {
        kieu: _kieu,
        sdVtId: this.params.vsdvt_id,
        vatTuId: _vatTuId,
        thueBaoId: this.params.vthuebao_id,
        xoaVt: _xoaVt,
        kieuPvt: _kieuPvt,
        lyDoCapTbiId: this.lyDoCapSelected_value,
        tinhTrangTbiId: this.tinhTrangTBSelected,
        hdTbId: this.params.hdtb_id,
        baoHongId: this.params.baohong_id,
        donViId: this.$root.token.getDonViID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        soLuong: this.soluong_cu,
        trangBiId: this.kieuTBSelected,
        phieuId: this.params.phieu_id,
        ngayTh: moment(this.ngayTH).format("DD/MM/yyyy HH:mm:ss").toString(),
        ghiChu: this.txtGhiChu,
        serial: _serial,
        serialNew: _serialNew,
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "",
        ipCn: "",
      };
      const response = await this.CapNhatSuDungVT_TH(data);
      // console.log("response Cập nhật sử dụng VT Thu hồi = ", response);
      if (response.error_code === "BSS-00000000") {
        this.statusCapNhatSuDungVT_TH = response.data;
      }
      this.loading(false);
    },

    async fetchDSThamSoMacDinh() {
      this.loading(true);
      let data = {
        kieuId: 0,
      };
      const response = await this.getDSThamSoMacDinh(data);
      // console.log("response Danh sách tham số mặc định = ", response);
      if (response.error_code === "BSS-00000000") {
        this.listThamSoMacDinh = response.data;
      }
      this.loading(false);
    },

    async fetchKTSoLuongVTTBDangSD(_vatTuId) {
      this.loading(true);
      let data = {
        vatTuId: _vatTuId,
        thueBaoId: this.params.vthuebao_id,
      };
      const response = await this.KTSoLuongVTTBDangSD(data);
      console.log("response Kiểm tra số lượng VTTB đang sử dụng = ", response);
      if (response.error_code === "BSS-00000000") {
        this.thuHoi.slvt = response;
      }
      this.loading(false);
    },
    async fetchKTSoLuongVTTBDangSD_v2(_vatTuId) {
      this.loading(true);
      let data = {
        vatTuId: _vatTuId,
        thueBaoId: this.params.vthuebao_id,
        hdtb_id: this.params.hdtb_id,
      };
      const response = await this.KTSoLuongVTTBDangSD_v2(data);
      console.log("response Kiểm tra số lượng VTTB đang sử dụng = ", response);
      if (response.error_code === "BSS-00000000") {
        this.thuHoi.slvt = response;
      }
      this.loading(false);
    },
    async fetchThongTinTBDaCapTheoSerial(_serial) {
      this.loading(true);
      let data = {
        serial: _serial,
      };
      const response = await this.getThongTinTBDaCapTheoSerial(data);
      // console.log(
      //   "response Thông tin thiết bị đã cấp theo serial = ",
      //   response
      // );
      if (response.error_code === "BSS-00000000") {
        this.thuHoi.dt_tbidacap = response.data;
      }
      this.loading(false);
    },
    async fetchDSTBSuDungVT(_serial) {
      this.loading(true);
      let data = {
        serial: _serial,
      };
      const response = await this.getDSTBSuDungVT(data);
      console.log("response DS Thuê Bao sử dụng Vật tư = ", response);
      if (response.error_code === "BSS-00000000") {
        this.thuHoi.dt = response.data;
      }
      this.loading(false);
    },
    async fetchKiemTraVatTuThuHoi() {
      try {
        this.loading(true);
        let data = {
          hdTbId: this.params.hdtb_id,
          baoHongId: this.params.baohong_id,
          serial: this.txtSerialCu.trim().toUpperCase(),
        };
        const response = await this.KiemTraVatTuThuHoi(data);
        this.statusKiemTraVatTuThuHoi = response;
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchKIEMTRA_VTDACAPTB(_tuVtId) {
      this.loading(true);
      let data = {
        phieuId: this.params.phieu_id,
        hdTbId: this.params.hdtb_id,
        baoHongId: this.params.baohong_id,
        tuVtId: _tuVtId,
      };
      const response = await this.KIEMTRA_VTDACAPTB(data);
      // console.log("response KIEMTRA_VTDACAPTB", response);
      if (response.error_code === "BSS-00000000") {
        this.tinhTrang_KIEMTRA_VTDACAPTB = response.data;
      }
      this.loading(false);
    },
    async fetchKIEMTRA_VTCAPRA_V2(_kieu, _tempHdBhId, _tuVtId, _dichVuVtId) {
      this.loading(true);
      let data = {
        kieu: _kieu,
        tempHdBhId: _tempHdBhId,
        kieuCap: 1,
        tuVtId: _tuVtId,
        phieuId: this.params.phieu_id,
        dichVuVtId: _dichVuVtId,
      };
      try {
        const response = await this.KIEMTRA_VTCAPRA_V2(data);
        console.log("response KIEMTRA_VTCAPRA_V2", response);
        // if (response.error_code === "BSS-00000000") {
        this.tinhTrangKIEMTRA_VTCAPRA_V2 = response.data;
        // }
      } catch (er) {
        this.$toast.error(er.data.message)
      } finally {
        this.loading(false);
      }
    },
    async fetchKiemTraTinhTrangHong(_vatTuId, _soLuong) {
      this.loading(true);
      let data = {
        hdTbId: this.params.thueBaoId,
        baoHongId: this.params.baohong_id,
        vatTuId: _vatTuId,
        soLuong: _soLuong,
        khoNhanId: this.value_KhoSelected,
      };
      const response = await this.KiemTraDinhMuc(data);
      // console.log("response Tình trạng định mức", response);
      if (response.error_code === "BSS-00000000") {
        this.tinhTrangDinhMuc = response.data[0];
      }
      this.loading(false);
    },
    async fetchGetKeyDKVTMuaSam() {
      this.loading(true);
      let data = {
        phanVungId: this.$root.token.getPhanVungID(),
        keyName: "phieuvt_id",
        numBlockSize: 0,
        numRetry: 0,
      };
      const response = await this.getKeyDKVTMuaSam(data);
      // console.log("response fetchGetKeyDKVTMuaSam", response);
      if (response.error_code === "BSS-00000000") {
        this.params.v_phieuvt_id = response.data;
      }
      this.loading(false);
    },
    //Get danh mục Hợp Đồng PL
    async fetchLoaiHDvaPL(_nhomMdId) {
      try {
        this.loading(true);
        let data = {
          nhomMdId: _nhomMdId,
        };
        const response = await this.getLoaiHDvaPL(data);
        // console.log("response Loại HĐ => ", response);
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((ele) => {
            this.listLoaiHDvaPL.push({
              value: ele.loaiHdId,
              text: ele.tenLoaiHd,
            });
          });
        }
        if (this.listLoaiHDvaPL.length > 0) {
          this.loaiHDSelected = this.listLoaiHDvaPL[0].value;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    // get danh mục DỊch vụ
    async fetchDMDichVu() {
      this.loading(true);
      const response = await this.getDMDichVu();
      // console.log("response Dịch vụ => ", response);
      if (response.error_code === "BSS-00000000") {
        this.listDichVu = response.data;
      }
      if (this.listDichVu.length > 0) {
        this.value_DichVuSelected = this.listDichVu[0].dichVuVTId;
        this.text_DichVuSelected = this.listDichVu[0].tenDVVT;
      }
      this.loading(false);
    },
    gridDichVu_selectedRowChanged(data) {
      this.value_DichVuSelected = data.dichVuVTId;
      this.text_DichVuSelected = data.tenDVVT;
    },
    hideCbxDichVu: function () {
      this.statusDichVu = false;
    },
    async fetchDMNhanVien() {
      this.loading(true);
      let data = {
        donViId: this.$root.token.getDonViID(),
        nhanVienId: this.$root.token.getNhanVienID(),
      };
      const response = await this.getDMNhanVien(data);
      // console.log("response Nhân viên => ", response);
      if (response.error_code === "BSS-00000000") {
        this.listNhanVien = response.data;
      }
      if (this.listNhanVien.length > 0) {
        this.value_NhanVienSelected = this.listNhanVien[0].NHANVIEN_ID;
        this.text_NhanVienSelected = this.listNhanVien[0].TEN_NEW;
      }
      this.loading(false);
    },
    hideCbxNhanVien: function () {
      this.statusCbxNhanVien = false;
    },
    hideLyDoCap: function () {
      this.statusLyDoCap = false;
    },
    gridNhanVien_selectedRowChanged(data) {
      this.value_NhanVienSelected = data.NHANVIEN_ID;
      this.text_NhanVienSelected = data.TEN_NEW;
    },
    async fetchDMKho() {
      try {
        this.loading(true);
        let data = {
          donViId: this.$root.token.getDonViID(),
          nhanVienId: this.$root.token.getNhanVienID(),
        };
        const response = await this.getDMKho(data);
        // console.log("response Kho => ", response);
        if (response.error_code === "BSS-00000000") {
          this.listKho = response.data;
        }
        if (this.listKho.length > 0) {
          this.value_KhoSelected = this.listKho[0].KHO_ID;
          this.text_KhoSelected = this.listKho[0].TEN_K_NEW;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    gridKho_selectedRowChanged(data) {
      if (data) {
        this.value_KhoSelected = data.KHO_ID;
        this.text_KhoSelected = data.TEN_K_NEW;
      }
    },
    hideCbxKho: function () {
      this.statusCbxKho = false;
    },
    async fetchDMKieuTB() {     
      const response = await this.getDMKieuTB();
      // console.log("response Kiểu Thiết bị => ", response);
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((el) => {
          this.listKieuTB.push({
            value: el.kieuThietBiId,
            text: el.kieuTbi,
          });
        });
      }
      if (this.listKieuTB.length > 0) {
        this.kieuTBSelected = 1;
      }      
    },
    async fetchDMTinhTrangTBCap() {
      
      const response = await this.getDMTinhTrangTBCap();
      this.listTinhTrangTBCap = [];
      // console.log("response Tình trạng thiết bị => ", response);
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((el) => {
          this.listTinhTrangTBCap.push({
            value: el.tinhTrangTBiId,
            text: el.tinhTrangTBi,
          });
        });
      }
      if (this.listTinhTrangTBCap.length > 0) {
        this.tinhTrangTBCapSelected = this.listTinhTrangTBCap[0].value;
        this.tinhTrangTBSelected = this.listTinhTrangTBCap[0].value;
      }
     
    },
    async fetchDMLyDoTon(_nhomTonId) {
      this.loading(true);
      let data = { nhomTonId: _nhomTonId };
      const response = await this.getDMLyDoTon(data);
      // console.log("response Lý do tồn => ", response);
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((el) => {
          this.listLyDoTon.push({
            value: el.lyDoTonTcId,
            text: el.lyDoTonTc == null ? "" : el.lyDoTonTc,
          });
        });
      }
      if (this.listLyDoTon.length > 0) {
        this.lyDoTonSelected = this.listLyDoTon[0].value;
      }
      this.loading(false);
    },
    async getDataLyDoTon(data) {
      var vnhom_ton = 0;
      if (data == null) {
        return;
      } else {
        if (data == 2) {
          this.statusLyDoTon = false;
        } else if (data == 3) {
          vnhom_ton = 8;
          this.statusLyDoTon = true;
        } else {
          this.statusLyDoTon = false;
        }
      }
      await this.fetchDMLyDoTon(vnhom_ton);
    },
    async fetchDMLyDoCap() {
      this.loading(true);
      let data = { nhomMdIdArr: [7, 8] };
      const response = await this.getDMLyDoCap(data);
      // console.log("response Lý do cấp => ", response);
      if (response.error_code === "BSS-00000000") {
        this.listLyDoCap = response.data;
        if (this.listLyDoCap.length > 0) {
          this.lyDoCapSelected_value = this.listLyDoCap[0].mucDichId;
          this.lyDoCapSelected_text = this.listLyDoCap[0].mucDich;
        }
      }
      this.loading(false);
    },
    gridDSLyDoCap_selectedRowChanged(data) {      
      if(data){
        this.lyDoCapSelected_value = data.mucDichId;
        this.lyDoCapSelected_text = data.mucDich;     
      }
      
    },
    async fetchDMTrangBi() {
      this.loading(true);
      const response = await this.getDMTrangBi();
      // console.log("response Trang bị => ", response);
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((el) => {
          this.listTrangBi.push({
            value: el.trangBiId,
            text: el.tenTrangBi,
          });
        });
      }
      if (this.listTrangBi.length > 0) {
        this.trangBiSelected = this.listTrangBi[0].value;
      }
      this.loading(false);
    },
    async fetchDSLoaiHopDongMucDich() {
      this.loading(true);
      const response = await this.getDSLoaiHopDongMucDich();
      // console.log("response Mục đích cấp => ", response);
      if (response.error_code === "BSS-00000000") {
        this.listLoaiHDMucDich = response.data;
      }
      this.loading(false);
    },
    async fetchDMMucDichLyDo(_nhomMdId) {
      this.loading(true);
      this.ds_lydoTC = [];
      this.ds_lydoBH = [];
      this.listMucDichCap = [];
      let data = {
        nhomMdId: _nhomMdId,
      };
      const response = await this.getDMMucDichLyDo(data);
      // console.log("response Mục đích cấp => ", response);
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((el) => {
          this.listMucDichCap.push({
            value: el.MUCDICH_ID,
            text: el.MUCDICH,
          });
        });
        if (this.listMucDichCap.length > 0) {
          this.mucDichCapSelected = this.listMucDichCap[0].value;
        }
        // if (this.loaiNhomSelected.toString() == "1") {
        //   this.ds_lydoTC = response.data;
        // } else {
        //   this.ds_lydoBH = response.data;
        // }
      }
      if (this.listMucDichCap.length > 0) {
        this.mucDichCapSelected = this.listMucDichCap[0].value;
      }
      this.loading(false);
    },
    async fetchDMTenThietBi() {
      try {
        this.loading(true);
        const response = await this.getDMTenThietBi();
        // console.log("response Tên thiết bị => ", response);
        if (response.error_code === "BSS-00000000") {
          this.listTenTb = response.data;
        }
        if (this.listTenTb.length > 0) {
          this.value_tenTbSelected = this.listTenTb[0].vatTuId;
          this.text_tenTbSelected = this.listTenTb[0].tenVatTu;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    gridTenTb_selectedRowChanged(data) {
      this.value_tenTbSelected = data.vatTuId;
      this.text_tenTbSelected = data.tenVatTu;
    },
    hideCbxTenTb: function () {
      this.statusTenTb = false;
    },
    async getQuyenNguoiDungCVTTB() {
      try {
        this.loading(true);
        let data = {
          nguoiDungId: this.$root.token.getNguoiDungID(),
        };
        const response = await this.getDMTenThietBi(data);
        // console.log("response Quyền người dùng => ", response);
        if (response.error_code === "BSS-00000000") {
          this.quyenNguoiDung = response.data;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSThueBaoCapVT(sSomay, sDATE, eDATE, _kieu) {
      try {
        this.listThueBaoCapVT = [];
        this.loading(true);
        let data = {
          nhomId:
            this.loaiNhomSelected == null || this.loaiNhomSelected == ""
              ? 1
              : this.loaiNhomSelected,
          dichVuVtId: this.value_DichVuSelected,
          loaiHdId: this.loaiHDSelected,
          maThueBao: sSomay,
          tuNgay: sDATE,
          denNgay: eDATE,
          nhanVienId: this.$root.token.getNhanVienID(),
          kieu: _kieu,
          timKiem: 1,
          tkiemVatTu: this.statusCkbTKTinhtrangVT == true ? 1 : 0, //0,
          tinhTrangVatTu: this.tinhTrangVTTHSelected, //0,
          ckbDaCap: this.ckbDaCap,
        };
        const response = await this.getDSThueBaoCapVT(data);
        // console.log("response DS Thuê Bao Cấp VT => ", response);
        if (response.error_code === "BSS-00000000") {
          this.listThueBaoCapVT = response.data;
          if (this.rdioCKb == 2) {
            this.count.choDuyet = response.data.length;
          } else if (this.rdioCKb == 3) {
            this.count.traLai = response.data.length;
          } else if (this.rdioCKb == 4) {
            this.count.boSung = response.data.length;
          } else {
            this.count.daGiao = response.data.length;
          }
          
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    
    async fetchDSThueBaoCapVTTruyenSoLieu(sSomay, sDATE, eDATE) {
      try {
        this.listThueBaoCapVT = [];
        this.loading(true);
        let data = {
          nhomId:
            this.loaiNhomSelected == null || this.loaiNhomSelected == ""
              ? 1
              : this.loaiNhomSelected,
          dichVuVtId: this.value_DichVuSelected,
          loaiHdId: this.loaiHDSelected,
          maThueBao: sSomay,
          tuNgay: sDATE,
          denNgay: eDATE,
          nhanVienId: this.$root.token.getNhanVienID(),
          kieu: this.rdioCKb,
          timKiem: 1,
          tkiemVatTu: this.statusCkbTKTinhtrangVT == true ? 1 : 0,
          tinhTrangVatTu: this.tinhTrangVTTHSelected,
          ckbDaCap: this.ckbDaCap,
        };
        const response = await this.getDSThueBaoCapVT(data);
        // console.log("response DS Thuê Bao Cấp VT Truyền dữ liệu => ", response);
        if (response.error_code === "BSS-00000000") {
          this.listThueBaoCapVT = response.data;
          if (this.rdioCKb == 2) {
            this.count.choDuyet = response.data.length;
          } else if (this.rdioCKb == 3) {
            this.count.traLai = response.data.length;
          } else if (this.rdioCKb == 4) {
            this.count.boSung = response.data.length;
          } else {
            this.count.daGiao = response.data.length;
          }
        }
        // console.log(
        //   "this.listThueBaoCapVT Truyền dữ liệu",
        //   this.listThueBaoCapVT
        // );
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchDMThaoTac(_code) {
      this.loading(true);
      this.listThaoTac = [];
      let data = {
        code: _code,
      };
      const response = await this.getDMThaoTac(data);
      // console.log("response DS Thao tác => ", response);
      if (response.error_code === "BSS-00000000") {
        this.listThaoTac = response.data;
      }
      this.loading(false);
    },
    async LoadThongTinThueBao(ten) {
      try {
        // int dv = parseInt(cboDichVuVT.EditValue); value_DichVuSelected
        if (this.rdioCKb == null || this.rdioCKb == 0) {
          return;
        }
        let sSomay = "";
        if (!!ten) {
          sSomay = this.soMaySelected;
        }
        if (sSomay == "") {
          sSomay = "0";
        }
        let sDATE = "0";
        let eDATE = "0";
        if (this.showTuNgay == true) {
          sDATE = moment(this.tuNgay).format("DD/MM/yyyy").toString();
          eDATE = moment(this.denNgay).format("DD/MM/yyyy").toString();
        }

        // grcHuy.Visible = false; //Nút hủy form
        // XOA.Visible = true;
        // if(this.kieuTBSelected.toString() == "3"){
        //   tsbtnHuyBo.Visible = true
        // }else{
        //   tsbtnHuyBo.Visible = false
        // }

        this.btnNhapMoi_Visible =
          this.kieuTBSelected.toString() == "3" ? true : false;
        this.statusBtnCapNhap = true;
        this.statusBtnHoanThanh = true;
        if (this.rdioCKb.toString() == "6") {
          // this.statusCkbDaCap = false;
          // XOA.Visible = false;
          this.ckbDaCap = false;
          this.statusGrcHuy = true;
          this.statusBtnCapNhap = false;
          this.statusBtnHoanThanh = false;
          this.btnNhapMoi_Visible = false;
        }
        if (this.params.kieugoi_id == 0 || this.params.kieugoi_id == 1) {
          await this.fetchDSThueBaoCapVT(sSomay, sDATE, eDATE, this.rdioCKb);
        } else {
          this.rdioCKb.toString() == "6"
            ? await this.fetchDSThueBaoCapVTTruyenSoLieu(sSomay, sDATE, eDATE)
            : this.ckbDaCap == true
            ? await this.fetchDSThueBaoCapVT(sSomay, sDATE, eDATE)
            : await this.fetchDSThueBaoCapVTTruyenSoLieu(sSomay, sDATE, eDATE);
        }
        //Datagrid Danh sách vật tư đã cấp cho thuê bao
        // gridVatTuSD.DataSource = null;
        // gridLichSuVatTu.DataSource = null;
      } catch (ex) {
        this.listThueBaoCapVT = [];
        this.$root.toastError(ex.data.message);
      }
    },
    // DSThueBaoCapVT_SelectRowCheckbox(data) {
    //   console.log("Chọn item đầu DSThueBaoCapVT = ", data[data.length - 1]);
    //   if (data.length == 0) {
    //     console.log("-1");
    //     this.dsTB.txtTenTB = "";
    //     this.dsTB.txtDiaChiLD = "";
    //     this.dsTB.txtKieuLD = "";
    //     this.dsTB.txtSoAo = "";
    //     this.params.thueBaoId = 0;
    //   } else if (data.length == 1) {
    //     console.log(1);
    //     console.log("1. data", data);
    //     console.log("CKB", this.rdioCKb);
    //     this.params.thueBaoId = 0;
    //     this.listThueBaoCapVT.forEach(async (el) => {
    //       if (data[0] === el.MA_TB) {
    //         console.log("2. data", el);
    //         this.dsTB.txtTenTB = el.TEN_TB;
    //         this.dsTB.txtDiaChiLD = el.DIACHI_LD;
    //         this.dsTB.txtKieuLD = el.TEN_KIEULD;
    //         this.dsTB.txtSoAo = el.MA_LT;
    //         this.params.thueBaoId = el.THUEBAO_ID;
    //         var temp =
    //           el.THUEBAO_ID == null || el.THUEBAO_ID == "" ? 0 : el.THUEBAO_ID;
    //         await this.fetchDSVatTuDaCap(el.PHIEU_ID, el.BH_ID, el.ID);
    //         await this.fetchDSVatTuTrongKho(el.THUEBAO_ID);
    //         await this.fetchDSVatTuThueBaoSuDung(temp);
    //       }
    //     });
    //   } else {
    //     console.log(3);
    //     console.log("3. data", data);
    //     this.params.thueBaoId = 0;
    //     this.listThueBaoCapVT.forEach(async (el) => {
    //       if (data[data.length - 1] === el.MA_TB) {
    //         console.log(4);
    //         console.log("4. data", el);
    //         this.dsTB.txtTenTB = el.TEN_TB;
    //         this.dsTB.txtDiaChiLD = el.DIACHI_LD;
    //         this.dsTB.txtKieuLD = el.TEN_KIEULD;
    //         this.dsTB.txtSoAo = el.MA_LT;
    //         this.params.thueBaoId = el.THUEBAO_ID;
    //         var temp =
    //           el.THUEBAO_ID == null || el.THUEBAO_ID == "" ? 0 : el.THUEBAO_ID;
    //         await this.fetchDSVatTuDaCap(el.PHIEU_ID, el.BH_ID, el.ID);
    //         await this.fetchDSVatTuTrongKho(el.THUEBAO_ID);
    //         await this.fetchDSVatTuThueBaoSuDung(temp);
    //       }
    //     });
    //   }
    // },
    async DSThueBaoCapVT_selectedRowChanged(data) {
      this.params.hdtb_id = 0;
      this.params.baohong_id = 0;
      this.params.phieu_id = 0;
      this.params.vdichvuvt_id = 0;
      this.btnNhapMoi_Enabled = true;
      this.params.ma_tb_cur = "";
      this.params.phieu_con_id = 0;
      console.log("Records DSThueBaoCapVT", data);
      this.check_MA_TB = "";
      if (data) {
        this.params.phieu_con_id = data.PHIEU_ID;
        this.selectedMaGD = data.MA_GD;
        this.ThueBaoFocus = data;
        this.params.dtthuebao = [];
        this.params.dtthuebao.push(data);
        this.params.ma_tb_cur = data.MA_TB;
        this.dsTB.txtTenTB = data.TEN_TB;
        this.check_MA_TB = data.MA_TB;
        this.dsTB.txtDiaChiLD = data.DIACHI_LD;
        this.dsTB.txtKieuLD = data.TEN_KIEULD;
        this.dsTB.txtSoAo = data.MA_LT;
        this.params.hdtb_id = data.ID;
        this.params.baohong_id = data.BH_ID;
        this.params.loaitb_id = data.LOAITB_ID;
        this.params.loaihd_id = data.LOAIHD_ID;
        this.params.vdichvuvt_id = data.DICHVUVT_ID;
        this.params.thueBaoId = data.ID;
        this.params.vthuebao_id =
          data.THUEBAO_ID == null || data.THUEBAO_ID == ""
            ? 0
            : data.THUEBAO_ID;
        this.params.phieu_cha_id = data.PHIEU_CHA_ID;
        if (this.rdioCKb.toString() == "1" || this.rdioCKb.toString() == "4") {
          this.params.phieu_id = data.PHIEU_ID;
        } else if (this.rdioCKb.toString() == "2") {
          this.params.phieu_id = data.PHIEU_CHA_ID;
        } else if (this.rdioCKb.toString() != "6") {
          if (
            data.HUONGGIAO_ID == "3004" ||
            data.HUONGGIAO_ID == "3007" ||
            data.HUONGGIAO_ID == "20008" ||
            data.HUONGGIAO_ID == "20010"
          ) {
            this.params.phieu_id = data.PHIEU_CHA_ID;
          } else {
            this.params.phieu_id = data.PHIEU_ID;
          }
        } else if (this.rdioCKb.toString() == "6") {
          this.params.phieu_id = data.PHIEU_ID;
        }
        console.log("DSThueBaoCapVT phieu_id = ", this.params.phieu_id);
        if (data.NGAY_HT != null && data.NGAY_HT != "") {
          this.params.ngay_ht =
            data.NGAY_HT != null ? data.NGAY_HT : data.NGAY_YC;
        } else if (data.NGAY_YC != null && data.NGAY_YC != "") {
          this.params.ngay_ht = data.NGAY_YC;
        } else {
          this.params.ngay_ht = new Date();
        }
        await this.fetchDSVatTuDaCap(
          this.params.phieu_id,
          this.params.baohong_id,
          this.params.hdtb_id
        );
        if (this.listKho.length > 0 && this.kieuTBSelected.toString() != "3") {
          await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
        }
        if (this.kieuTBSelected.toString() != "1") {
          await this.fetchDSVatTuThueBaoSuDung(this.params.vthuebao_id);
        }
        this.fetchDSNhanVienTheoID(this.params.phieu_id);
        // console.log("this.listLoaiHDMucDich= ", this.listLoaiHDMucDich);
        let temp_listMucDichCap = [];
        await this.changeMucDichCap();
        for (const el of this.listMucDichCap) {
          for (const i of this.listLoaiHDMucDich) {
            if (
              el.value == i.mucDichId &&
              i.loaiHdId == this.params.loaihd_id
            ) {
              temp_listMucDichCap.push(el);
            }
          }
        }
        console.log("temp_listMucDichCap = ", temp_listMucDichCap);
        if (temp_listMucDichCap.length > 0) {
          this.listMucDichCap = [];
          this.listMucDichCap = temp_listMucDichCap;
        }
      } else {
        this.ThueBaoFocus = {};
      }
    },
    DSNhanVien_selectedRowChanged() {},
    async fetchDSNhanVienTheoID(_phieuId) {
      this.loading(true);
      let data = {
        phieuId: _phieuId,
        nhanVienId: this.$root.token.getNhanVienID(),
        dkieuId: 2,
      };
      const response = await this.getDSNhanVienTheoID(data);
      // console.log("response Nhân viên theo Phiếu giao ID => ", response);
      if (response.error_code === "BSS-00000000") {
        this.listNhanVienTheoPhieuID = response.data;
      }
      this.loading(false);
    },
    DSVatTuDaCap_selectedRowChanged(data) {
      if (data) {        
        if (this.kieuTBSelected == 3 && data.KIEUTBI_ID == 3) {
          console.log(1);
          this.btnNhapMoi_Enabled = true;
          // this.params.pnlThuHoi = this.kieuTBSelected == 3 ? true : false;
          this.params.pnlThuHoi = true;
          this.params.phvt_id = parseInt(data.PHIEUVT_ID.toString());
          if (
            (data.SERIAL == null || data.SERIAL == "") &&
            (data.MA_TBI == null || data.MA_TBI == "")
          ) {
            this.txtSerialCu = "";
            this.statusSoluong_cu = false;
            this.disabledTenTb = true;
            this.statusTxtSerialCu = true;
          } else if (
            !!data.SERIAL &&
            (data.MA_TBI == null || data.MA_TBI == "")
          ) {
            this.txtSerialCu = data.GHICHU;
            this.statusSoluong_cu = true;
            this.disabledTenTb = true;
            this.statusTxtSerialCu = true;
          } else if (
            (data.SERIAL == null || data.SERIAL == "") &&
            !!data.MA_TBI
          ) {
            this.txtSerialCu = data.MA_TBI;
            this.statusSoluong_cu = false;
            this.disabledTenTb = true;
            this.statusTxtSerialCu = false;
          }

          this.text_tenTbSelected = data.TEN_VT;
          this.value_tenTbSelected = data.VATTU_ID;
          this.soluong_cu = data.SOLUONG;
          this.tinhTrangTBSelected = data.TINHTRANGTBI_ID;
          this.lyDoCapSelected_value = data.MUCDICH_ID;          
          this.lyDoCapSelected_text = data.MUCDICH;         
          this.txtGhiChu = data.GHICHU;
        } else {
          console.log(2);
          this.Clear();
        }
        this.params.vphieuvt_id = data.PHIEUVT_ID;
        this.params.vtuvt_id = data.TUVT_ID;
        this.params.vsoluong = data.SOLUONG;
        this.params.sdvt_xoa = data.SDVT_ID;
        this.params.ktbi = data.KIEUTBI_ID;
      }
    },
    async fetchKiemTraHuyPhieuGiao(
      _tb,
      _phieuId,
      _phieuChaId,
      _hdTbId,
      _baoHongId
    ) {
      this.loading(true);
      let data = {
        phieuId: _phieuId,
        phieuChaId: _phieuChaId,
        hdTbId: _hdTbId,
        baoHongId: _baoHongId,
      };
      const response = await this.kiemTraHuyPhieuGiao(data);
      if (response.error_code === "BSS-00000000") {
        if (response.data == "1") {
          this.$root.toastSuccess(`Hủy giao phiếu ${_tb} thành công`);
          await this.LoadThongTinThueBao("");
        } else {
          this.$root.toastError(response.message);
          await this.LoadThongTinThueBao("");
        }
      }
      this.loading(false);
    },
    xoa(data) {
      // console.log("xóa", data);
      var tb = "";
      this.listThueBaoCapVT.forEach(async (el) => {
        if (data == el.MA_TB) {
          if (!!el.PHIEU_CHA_ID && !!el.PHIEU_ID) {
            tb = el.MA_TB ? el.MA_TB : el.MA_LT;
            this.$bvModal
              .msgBoxConfirm(
                "Bạn có muốn hủy giao phiếu của thuê bao " + tb + " không?",
                {
                  size: "sm",
                  okTitle: "Có",
                  cancelTitle: "Không",
                }
              )
              .then(async (v) => {
                if (v) {
                  await this.fetchKiemTraHuyPhieuGiao(
                    tb,
                    el.PHIEU_ID,
                    el.PHIEU_CHA_ID,
                    el.ID,
                    el.BH_ID
                  );
                }
              })
              .catch((err) => {
                this.$toast.error(err.data.message);
                this.loading(false);
                return;
              });
          }
        }
      });
    },

    async pthuHoi(data) {
      console.log("pthuHoi = ", data);
      this.list_pthuHoi = [];
      this.check_pthuHoi = true;
      this.btnNhapMoi_Enabled = false;
      // await this.DSVatTuTBDaSuDung_selectedRowChanged();
      // for (const el of this.listVatTuTBDaSuDung) {
      //   if (data == el.THIETBI_ID) this.list_pthuHoi.push(el);
      // }
      this.params.sl_goc = 0;
      // console.log(this.list_pthuHoi);
      if (
        (data.SERIAL == null || data.SERIAL == "") &&
        (data.MA_TBI == null || data.MA_TBI == "")
      ) {
        this.txtSerialCu = "";
        this.statusSoluong_cu = false;
        this.readonlyTenTbSelected = false;
        this.statusTenTb = false;
        this.statusTxtSerialCu = false;
      } else if (
        (data.SERIAL != null || data.SERIAL != "") &&
        (data.MA_TBI == null || data.MA_TBI == "")
      ) {
        this.txtSerialCu = data.SERIAL;
        this.statusSoluong_cu = true;
        this.readonlyTenTbSelected = true;
        this.statusTxtSerialCu = true;
      } else if (
        (data.SERIAL == null || data.SERIAL == "") &&
        (data.MA_TBI != null || data.MA_TBI != "")
      ) {
        this.txtSerialCu = data.MA_TBI;
        this.statusSoluong_cu = false;
        this.readonlyTenTbSelected = false;
        this.statusTenTb = false;
        this.statusTxtSerialCu = false;
      }
      this.tinhTrangTBSelected = data.TINHTRANGTBI_ID;
      this.text_tenTbSelected = data.TEN_VT_NEW;
      this.value_tenTbSelected = data.VATTU_ID;
      this.txtGhiChu = data.GHICHU;
      this.soluong_cu = data.SOLUONG;
      this.params.sl_goc = data.SOLUONG;
      this.params.vsdvt_id = data.SDVT_ID;
      this.params.vphieuvt_id = data.PHIEUVT_ID;
    },
    async pSuaDoi(data_row) {
      console.log("pSuaDoi = ", data);
      this.pthuHoi(data_row);
      let data = data_row.SERIAL;
      await this.fetchDSTBSuDungVT(data);
      await this.fetchThongTinTBDaCapTheoSerial(data);

      var vvattu_id_new = "0";

      // Kiểm tra xem đã từng có ai thu hồi serial này chưa
      if (this.thuHoi.dt_tbidacap.length > 0) {
        this.$root.toastError(
          "Serial " +
            this.txtSerialCu +
            " đã được thu hồi vào  ngày " +
            this.thuHoi.dt_tbidacap[0].NgayCap.toString() +
            " bởi user " +
            this.thuHoi.dt_tbidacap[0].NguoiCn.toString() +
            ""
        );
        return;
      }
      if (this.thuHoi.dt.length > 1) {
        this.$confirm(
          `Hiện đang có ${this.thuHoi.dt.length} thuê bao đang sử dụng ${data}. Bạn có muốn cập nhật lại seri cho thuê bao này không?`,
          "Thông báo",
          {
            confirmButtonText: "Đồng ý",
          }
        )
          .then(() => {})
          .catch(() => {
            return;
          });
      }
      if (this.thuHoi.dt.length == 1) {
        // Nếu có 1 thuê bao thì kiểm tra tiếp
        //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
        if (
          parseInt(this.thuHoi.dt[0].thueBaoId.toString()) !=
          this.params.vthuebao_id
        ) {
          if (parseInt(this.thuHoi.dt[0].phieuVatTuId.toString()) != 0) {
            // Kiểm tra được phép sửa không
            this.$root.toastWarning(
              "Serial " +
                data +
                " đã được sử dụng bởi thuê bao " +
                this.thuHoi.dt[0].maThueBao +
                ". Bạn không có quyền được thu hồi"
            );
            return;
          }
          this.$confirm(
            `Serial ${data} đã được sử dụng bởi thuê bao ${this.thuHoi.dt[0].maThueBao}. Bạn có chắc chắn muốn tiếp tục sửa không?`,
            "Thông báo",
            {
              confirmButtonText: "Đồng ý",
            }
          )
            .then(() => {})
            .catch(() => {
              return;
            });
        }
        //Tiếp tục thu hồi thì sẽ có 2 trường hợp
        this.$bvModal
          .msgBoxConfirm(
            "Serial " +
              this.txtSerialCu +
              " đã được sử dụng bởi thuê bao " +
              this.thuHoi.dt[0].maThueBao +
              ". Bạn có chắc chắn muốn tiếp tục thu hồi không?",
            {
              size: "sm",
              okTitle: "Có",
              cancelTitle: "Không",
            }
          )
          .then(async (v) => {
            if (v) {
              this.params.checkModal = 2;
              this.$bvModal.show("modal-doiSerial");
            }
          })
          .catch((err) => {
            return;
          });
        // nếu thay đổi serial
        // if (this.doiSerial.checkActions == 1) {
        //   vvattu_id_new = this.doiSerial.vatTuId;
        //   if (this.doiSerial.serialCu == data) {
        //     this.$root.toastWarning("Seri cần thay đổi trùng với seri gốc");
        //     this.$bvModal.show("modal-doiSerial");
        //   }
        //   await this.fetchDSTBSuDungVT(this.doiSerial.serialCu);
        //   if (this.thuHoi.dt.length > 1) {
        //     // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
        //     this.$confirm(
        //       `Hiện đang có ${this.thuHoi.dt} thuê bao đang sử dụng ${this.doiSerial.serialCu}. Bạn có muốn cập nhật lại seri cho thuê bao này không?`,
        //       "Thông báo",
        //       {
        //         confirmButtonText: "Đồng ý",
        //       }
        //     )
        //       .then(() => {})
        //       .catch(() => {
        //         return;
        //       });
        //   }
        //   if (this.thuHoi.dt.length == 1) {
        //     // Nếu có 1 thuê bao thì kiểm tra tiếp
        //     //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
        //     if (
        //       parseInt(this.thuHoi.dt[0].thueBaoId.toString()) !=
        //       this.params.vthuebao_id
        //     ) {
        //       if (parseInt(this.thuHoi.dt[0].phieuVatTuId.toString()) != 0) {
        //         // Kiểm tra duoc phep sua khong
        //         this.$root.toastWarning(
        //           "Serial " +
        //             this.doiSerial.serialCu +
        //             " đã được sử dụng bởi thuê bao " +
        //             this.thuHoi.dt[0].maThueBao +
        //             ". Bạn không có quyền được thu hồi"
        //         );
        //         frm.Close();
        //         return;
        //       }
        //       this.$confirm(
        //         `Serial ${this.doiSerial.serialCu} đã được sử dụng bởi thuê bao ${this.thuHoi.dt[0].maThueBao}. Bạn có chắc chắn muốn tiếp tục sửa không?`,
        //         "Thông báo",
        //         {
        //           confirmButtonText: "Đồng ý",
        //         }
        //       )
        //         .then(() => {})
        //         .catch(() => {
        //           return;
        //         });
        //     }
        //   }
        //   await this.fetchCapNhatSuDungVT_TH(
        //     1,
        //     this.doiSerial.vatTuId,
        //     0,
        //     0,
        //     this.doiSerial.serialCu,
        //     this.txtSerialCu
        //   );
        //   if (this.statusCapNhatSuDungVT_TH != "1") {
        //     this.$root.toastWarning(this.statusCapNhatSuDungVT_TH);
        //   } else {
        //     this.$root.toastSuccess("Thu hồi thành công!");
        //   }
        //   await this.reloadLSVT();
        // }
      }
    },
    chonVT(data) {
      // console.log("Chọn thu hồi", data);
    },

    async xoaLSVT(data) {
      try{
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có thực sự muốn xóa phiếu vật tư đang chọn không?", {
            title: "Thông báo",
            centered: true,
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } 
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true)
        await this.xoaLichSuVatTu(
          "",
          data.SDVT_ID,
          data.KIEUTBI_ID,
          data.PHIEUVT_ID
        );
        this.reloadLSVT();
        if (this.listKho.length > 0) {
          await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
        }
        let q = this.tinhTrangTBSelected;
        this.Clear();
        if (q != null && q != "") {
          this.tinhTrangTBSelected = q;
        }
        this.btnNhapMoi_Enabled = false;
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async xoaLichSuVatTu(_serial, _sdVtId, _kieu, _phieuVtId) {
      this.loading(true);
      let data = {
        serial: _serial,
        sdVtId: _sdVtId,
        kieu: _kieu.toString() == "1" ? 3 : 2,
        phieuVtId: _phieuVtId,
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "",
        ipCn: "",
      };
      const response = await this.deleteLichSuVT(data);
      if (response.error_code === "BSS-00000000") {
        this.$root.toastSuccess("Xóa phiếu vật tư thành công");
        this.loading(false);
      } else {
        this.$root.toastSuccess(
          "Lỗi xóa phiếu (" + response.message_detail + ")"
        );
        this.loading(false);
      }
    },
    async load_Form() {
      try {
        if (this.params.kieugoi_id == 0) {
          if (this.params.Tag.toString() != "") {
            if (parseInt(this.params.Tag) > 0) {
              this.params.kieugoi_id = parseInt(this.params.Tag.toString());
            }
          } else {
            this.params.kieugoi_id = 0;
          }
        }
      } catch (ex) {
        // console.log(ex);
        this.params.kieugoi_id = 0;
      }

      await this.fetchDMThaoTac("BATBUOC_TCTHUHOI");
      if (this.listThaoTac.length > 0) {
        this.params.boqua = true;
      }

      await this.fetchDMThaoTac("BATBUOC_BHTHUHOI");
      if (this.listThaoTac.length > 0) {
        this.params.boqua_baohong = true;
      }
      await this.fetchDSThamSoMacDinh();
      for (const el of this.listThamSoMacDinh) {
        if (el.maTS == "CHAN_TKDU") {
          this.params.chan_tkdu = true;
        }
      }
     
      if (this.listThamSoMacDinh.length > 0) {
        this.TSMD.check_serial = this.setThamSo("KIEMTRA_KYTU_SERIAL", this.listThamSoMacDinh) ? "1" : "0"
          
        this.TSMD.UPDATE_CTS = this.setThamSo("CAPNHAT_CTS_MODEM", this.listThamSoMacDinh)           
    
        this.TSMD.chan_tkdu = this.setThamSo("CHAN_TKDU", this.listThamSoMacDinh)
          
        this.TSMD.canhbao_sl_thuhoi_vt = this.setThamSo("CANHBAO_SL_THUHOI_VT", this.listThamSoMacDinh)
           
        this.TSMD.capvt_nhieudong = this.setThamSo("CAPVT_TB_NHIEUDONG", this.listThamSoMacDinh)
        
      }

      await this.fetch_MAP_ID();
      this.MAP_ID.vuser_alt = this.getMapID[0].USER_ALT;
      if (
        this.MAP_ID.vuser_alt == "" ||
        this.MAP_ID.vuser_alt == "-1" ||
        this.MAP_ID.vuser_alt == null
      ) {
        this.MAP_ID.vuser_alt = "";
      }
      this.MAP_ID.vpass_alt = this.getMapID[0].PASS_ALT;
      if (
        this.MAP_ID.vpass_alt == "" ||
        this.MAP_ID.vpass_alt == "-1" ||
        this.MAP_ID.vpass_alt == null
      ) {
        this.MAP_ID.vpass_alt = "";
      }
      this.MAP_ID.vuser_xtest = this.getMapID[0].USER_XTEST;
      if (
        this.MAP_ID.vuser_xtest == "" ||
        this.MAP_ID.vuser_xtest == "-1" ||
        this.MAP_ID.vuser_xtest == null
      ) {
        this.MAP_ID.vuser_xtest = "";
      }
      this.MAP_ID.vpass_xtest = this.getMapID[0].PASS_XTEST;
      if (
        this.MAP_ID.vpass_xtest == "" ||
        this.MAP_ID.vpass_xtest == "-1" ||
        this.MAP_ID.vpass_xtest == null
      ) {
        this.MAP_ID.vpass_xtest = "";
      }

      await this.fetchDMTinhTrangVTThuHoi();
    },
    setThamSo(str_vtemp, ds) {      
      let kiemtra = ds.filter(
        (a) => a.maTS.toUpperCase() == str_vtemp.toUpperCase()
      );
      if (kiemtra.length && kiemtra[0].tenTS.trim() == "1") {        
        return true;        
      }
      return false;
    },
    async fetchDMTinhTrangVTThuHoi() {
      this.loading(true);
      const response = await this.getDMTinhTrangVTThuHoi();
      // console.log("response Tình trạng vật tư thu hồi => ", response);
      this.listTinhTrangVTTH = [];
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((el) => {
          this.listTinhTrangVTTH.push({
            value: el.tinhTrangTBiId,
            text: el.tinhTrangTBi,
          });
        });
      }
      this.loading(false);
    },
    async btnLayTT() {
      if (this.rdioCKb == 0) {
        return;
      }
      if (this.loaiNhomSelected == 1) {
        this.params.p_id = 1;
      } else {
        this.params.p_id = 2;
      }
      if (
        (this.loaiNhomSelected == 1 && this.loaiHDSelected.toString() == "8") ||
        this.loaiNhomSelected == 2
      ) {
        this.params.t_hoi = 1;
      } else {
        this.params.t_hoi = 0;
      }

      if (this.kieuTBSelected.toString() == "1") {
        this.params.pnlThuHoi = false;
        this.readonlyTxtSerial = false;
        this.xtraTabPage1 = true; // Tab Danh sách vật tư trong kho
        this.xtraTabPage2 = false; // Tab Thông tin thuê bao
        this.btnNhapMoi_Visible = false;
        this.statusBtnHuy = false;
        this.Clear();
      } else {
        this.params.pnlThuHoi = false;
        this.readonlyTxtSerial = true;
        this.xtraTabPage1 = false; // Tab Danh sách vật tư trong kho
        this.xtraTabPage2 = true; // Tab Thông tin thuê bao
        this.txtSerialCu = "";
        this.btnNhapMoi_Visible = true;
        this.btnNhapMoi_Enabled = false;
        this.statusSoluong_cu = false;
        this.readonlyTenTbSelected = false;
        this.statusBtnHuy = true;
        this.statusTxtSerialCu = false;
        this.Clear();
      }

      // if (cboKho.EditValue == null || cboKho.EditValue == "") {
      //   this.$root.toastWarning("Không có dữ liệu kho được quản lý");
      //   return;
      // }
      await this.LoadThongTinThueBao("");
    },
    Clear() {
      this.text_tenTbSelected = "";
      this.tinhTrangTBSelected = "";
      this.txtSerialCu = "";
      this.soluong_cu = 0;
      this.txtGhiChu = "";
      this.params.phvt_id = 0;
      this.params.vsdvt_id = 0;
    },
    async fetchDSVatTuDaCap(_phieuId, _baoHongId, _hdThueBao) {
      this.loading(true);
      let data = {
        phieuId:
          this.rdioCKb.toString() == "6" ? this.params.phieu_cha_id : _phieuId,
        baoHongId: _baoHongId,
        hdThueBao: _hdThueBao,
        kieuThietBi: 0,
        status: 0,
      };
      try {
        const response = await this.getDSVatTuDaCap(data);
        console.log("response Vật tư đã cấp => ", response);
        if (response.error_code === "BSS-00000000") {
          this.listVatTuDaCap = response.data;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSVatTuThueBaoSuDung(_thueBaoId) {
      this.loading(true);
      let data = {
        thueBaoId: _thueBaoId,
      };
      const response = await this.getDSVatTuThueBaoSuDung(data);
      // console.log("response Vật tư đã cấp => ", response);
      if (response.error_code === "BSS-00000000") {
        this.listVatTuTBDaSuDung = response.data;
      }
      this.loading(false);
    },
    async fetchDSVatTuTrongKho(_loaiTbId) {
      try {
        this.loading(true);
        let data = {
          khoId: this.value_KhoSelected,
          loaiTbId: _loaiTbId,
          loaiHdId: this.loaiHDSelected,
          chkLocVt: this.ckbLocVT,
        };
        const response = await this.getDSVatTuTrongKho(data);
        // console.log("response Vật tư trong kho => ", response);
        if (response.error_code == "BSS-00000000") {
          this.listVatTuTrongKho = response.data;
        }
      } catch (ex) {
        this.listVatTuTrongKho = [];
        console.log(ex.data);
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    DSVatTuTrongKho_selectedRowChanged(data) {
      console.log(data);
    },
    async fetchNgayBatDauDungThu(_hdtbId) {
      this.loading(true);
      let data = {
        hdtbId: _hdtbId,
      };
      const response = await this.getNgayBatDauDungThu(data);
      // console.log("response Ngày dùng thử => ", response);
      if (response.error_code === "BSS-00000000") {
        this.dateDungThu = response.data;
      }
      this.loading(false);
    },
    //Kiểm tra dữ liệu
    async KiemTraDuLieu() {
      this.params.vt_dccap = [];
      if (this.listTinhTrangTBCap == null || this.listTinhTrangTBCap == "") {
        this.$root.toastError("Bạn chưa chọn tình trạng thiết bị");
        return (this.params.ok = false);
      }
      if (this.dsTB.txtTenTB == null || this.dsTB.txtTenTB == "") {
        this.$root.toastError("Bạn chưa chọn thuê bao");
        return (this.params.ok = false);
      }
      let r = [];
      this.listLyDoCap.forEach((el) => {
        if (el.mucDichId == this.lyDoCapSelected_value) {
          r.push(el);
        }
      });
      if (this.lyDoCapSelected_value) {
        if (this.params.hdtb_id != 0) {
          await this.fetchNgayBatDauDungThu(this.params.hdtb_id);
          if (this.dateDungThu.length > 0) {
            if (
              this.diffDateTime(
                this.ngayTH,
                moment(this.dateDungThu[0]).format("DD/MM/yyyy HH:mm")
              ) == false
            ) {
              this.$root.toastWarning(
                "Ngày cấp phải lớn hơn hoặc bằng ngày bắt đầu dùng thử " +
                  moment(this.dateDungThu[0])
                    .format("DD/MM/yyyy: HH:mm")
                    .toString()
              );
              return (this.params.ok = false);
            }
          } else {
            if (
              this.diffDateTime(this.ngayTH, this.params.ngay_ht) == false &&
              this.params.loaihd_id != 13
            ) {
              this.$root.toastWarning(
                "Ngày cấp phải lớn hơn hoặc bằng ngày hoàn thành"
              );
              return (this.params.ok = false);
            }
          }
        } else {
          if (
            this.diffDateTime(this.ngayTH, this.params.ngay_ht) == false &&
            this.params.loaihd_id != 13
          ) {
            this.$root.toastWarning(
              "Ngày cấp phải lớn hơn hoặc bằng ngày hoàn thành"
            );
            return (this.params.ok = false);
          }
        }
        if (this.listVatTuTrongKho.length == 0) {
          this.$root.toastError("Không có vật tư để cấp");
          return (this.params.ok = false);
        }

        if (this.listVatTuTrongKho) {
          this.listVatTuTrongKho.forEach((el) => {
            this.listVatTuTrongKho_Checked.forEach((temp) => {
              if (temp == el.TUVT_ID) {
                this.params.vt_dccap.push(el);
              }
            });
          });
          if (this.params.vt_dccap.length == 0) {
            this.$root.toastError("Bạn chưa chọn vật tư để cấp");
            this.params.vt_dccap = [];
            return (this.params.ok = false);
          }
          let q_1 = [];
          this.params.vt_dccap.forEach((el) => {
            if (el.MA_VT != "" && el.MA_VT.toString().startsWith("19")) {
              q_1.push(el);
            }
          });
          if (q_1.length != 0 && r[0].maMd.toString() == "LD.3.1") {
            this.$root.toastError(
              "Lý do bạn chọn không được cấp thiết bị đầu cuối"
            );
            return (this.params.ok = false);
          }
          let json = [];
          this.params.vt_dccap.forEach((el) => {
            json.push({ TINHTRANGTBI_ID: el.TINHTRANGTBI_ID });
          });
          await this.fetchKTTTHongVT(json);
          if (this.tinhTrangHongVT.toString() == "0") {
            this.$root.toastError(
              "Danh sách chọn có vật tư đang có tình trạng hỏng! Không thể cấp cho khách hàng!"
            );
            return (this.params.ok = false);
          }
        }
      }
      return (this.params.ok = true);
    },
    async CapVatTu_Click() {
      let kt = 0;
      let tc = 0;
      let loi = "";
      let loi_cap = "";
      let nhanvien_id_temp = this.$root.token.getNhanVienID();
      if (this.value_NhanVienSelected != this.$root.token.getNhanVienID()) {
        nhanvien_id_temp = this.value_NhanVienSelected;
      }
      await this.KiemTraDuLieu();

      if (this.params.ok) {
        let g = 0;
        let i = 0;
        let j = 0;
        let soluong = 0;
        let thongbao = "";

        let result = {};
        this.params.vt_dccap.forEach(async (e) => {
          if (e.VATTU_ID in result) {
            result[e.VATTU_ID] = result[e.VATTU_ID] + parseInt(e.LUONGCAP);
          } else {
            result[e.VATTU_ID] = parseInt(e.LUONGCAP);
          }
        });
        for (var VATTU_ID in result) {
          await this.fetchKiemTraTinhTrangHong(VATTU_ID, result[VATTU_ID]);
          if (this.tinhTrangDinhMuc.toString() != "1") {
            thongbao = thongbao + t + " \r\n";
          }
        }

        if (thongbao != "") {
          this.$root.toastError("Cảnh báo định mức \r\n " + thongbao);
          return;
        }
        console.log("this.params.loaitb_id = ", this.params.loaitb_id);
        if (this.params.loaitb_id == 210) {
          const list_Temp = this.params.vt_dccap.filter(function (number) {
            return number.LOAITBI_ID == 2;
          });
          if (list_Temp.length == 0) {
          } else if (list_Temp.length > 1) {
            this.$root.toastWarning("Mỗi account chỉ được cấp ra 01 thiết bị");
            return;
          } else {
            try {
              this.loading(true);
              console.log("list_Temp = ", list_Temp);
              var rs = await this.$root.context.get(
                `/web-qlvt/api/cap-vat-tu-thue-bao/verify-device?serial=${list_Temp[0].SERIAL}`
              );
              console.log("rs = ", rs);
              if (rs.data.errorCode == 404) {
                this.$root.toastWarning(
                  "Không tìm thấy thiết bị ở hệ thống ONE Mesh"
                );
                return;
              }

              if (
                (rs.data.errorCode == 200 &&
                  rs.data.activationStatus != true) ||
                rs.data.errorCode == 500
              ) {
                this.$root.toastWarning(
                  "Thiết bị chưa được kích hoạt. Kiểm tra lại chất lượng đường truyền của thiết bị"
                );
                return;
              }

              if (
                !(rs.data.errorCode == 200 && rs.data.activationStatus == true)
              ) {
                this.$root.toastWarning(
                  "Thiết bị chưa được kích hoạt. Kiểm tra lại chất lượng đường truyền của thiết bị"
                );
                return;
              }
            } catch (error) {
              this.$toast.error("Đã xảy ra lỗi!");
              return;
            } finally {
              this.loading(false);
            }
          }
        }
        for (var el of this.params.vt_dccap) {
          let temp_hdbh_id = 0;
          let vk = 0;
          if (this.params.p_id == 1) {
            temp_hdbh_id = this.params.thueBaoId;
            vk = 1;
          } else {
            temp_hdbh_id = this.params.baohong_id;
            vk = 2;
          }
          try {
            //DichVuVienThong.TSL == 9
            if (this.params.vdichvuvt_id == 9) {
              await this.fetchKIEMTRA_VTCAPRA_V2(
                vk,
                temp_hdbh_id,
                el.TUVT_ID,
                9
              );
              if (
                this.tinhTrangKIEMTRA_VTCAPRA_V2.toString() != "1" ||
                this.tinhTrangKIEMTRA_VTCAPRA_V2 == null
              ) {
                this.$root.toastError(
                  this.tinhTrangKIEMTRA_VTCAPRA_V2.toString()
                );
                return;
              }
            } else {
              await this.fetchKIEMTRA_VTCAPRA_V2(
                vk,
                temp_hdbh_id,
                el.TUVT_ID,
                this.params.vdichvuvt_id
              );
              if (
                this.tinhTrangKIEMTRA_VTCAPRA_V2.toString() != "1" ||
                this.tinhTrangKIEMTRA_VTCAPRA_V2 == null
              ) {
                this.$root.toastError(
                  this.tinhTrangKIEMTRA_VTCAPRA_V2.toString()
                );
                return;
              }
            }
          } catch (ex) {
            this.$root.toastError(" Có lỗi (" + ex + ")");
            return;
          }
          console.log("Cập nhật el.SERIAL = ", el.SERIAL);
          if (!el.SERIAL.toString()) {
            console.log("K SERIAL");
            await this.fetchKIEMTRA_VTDACAPTB(el.TUVT_ID);
            if (this.tinhTrang_KIEMTRA_VTDACAPTB.length != 0) {
              loi +=
                el.TEN_VT.toString() +
                " đã được cấp bạn ko thể cấp tiếp đc \r\n";
            }
            kt += 1;
            await this.fetchGetKeyDKVTMuaSam();
            let v_phieuvt_id = this.params.v_phieuvt_id;
            console.log(this.ngayTH, "  = this.ngayTH");
            let v_ngay_cap = moment(this.ngayTH).format("DD/MM/YYYY HH:mm:ss");
            console.log(v_ngay_cap, "  = v_ngay_cap");
            let v_kieutbi = this.kieuTBSelected;
            let v_trangbi = this.trangBiSelected;
            let v_tuvt_id = el.TUVT_ID.toString();
            let v_thietbi_id = el.THIETBI_ID.toString();
            let v_soluong = el.LUONGCAP.toString();
            let serial = "";
            if (el.SERIAL.toString()) {
              serial = el.SERIAL.toString();
            }
            let ma_tbi = "";
            if (el.MA_TBI.toString()) {
              ma_tbi = el.MA_TBI.toString();
            }
            let lydocap = this.lyDoCapSelected_value;
            let tinhtrang_tbi = this.tinhTrangTBCapSelected;
            if (this.params.p_id == 1) {
              await this.fetchThemMoiPhieuCapVT(
                v_phieuvt_id,
                this.params.phieu_id,
                v_ngay_cap,
                v_kieutbi,
                v_trangbi,
                v_tuvt_id,
                v_thietbi_id,
                v_soluong,
                serial,
                ma_tbi,
                lydocap,
                this.mucDichCapSelected,
                this.params.thueBaoId,
                0,
                tinhtrang_tbi,
                this.params.vthuebao_id
              );
              loi_cap = this.statusThemMoiPhieuCapVT;
            } else {
              await this.fetchThemMoiPhieuCapVT(
                v_phieuvt_id,
                this.params.phieu_id,
                v_ngay_cap,
                v_kieutbi,
                v_trangbi,
                v_tuvt_id,
                v_thietbi_id,
                v_soluong,
                serial,
                ma_tbi,
                lydocap,
                this.mucDichCapSelected,
                0,
                this.params.baohong_id,
                tinhtrang_tbi,
                this.params.vthuebao_id
              );
              loi_cap = this.statusThemMoiPhieuCapVT;
            }
            if (loi_cap != null && loi_cap != "1" && loi_cap != "") {
              this.$root.toastWarning(loi_cap);
              return;
            }
          }
          if (el.SERIAL.toString()) {
            console.log("Có SERIAL");
            kt += 1;
            await this.fetchGetKeyDKVTMuaSam();
            let v_phieuvt_id = this.params.v_phieuvt_id;
            let v_ngay_cap = moment(this.ngayTH).format("DD/MM/YYYY HH:mm:ss");
            let v_kieutbi = this.kieuTBSelected;
            let v_trangbi = this.trangBiSelected;
            let v_tuvt_id = el.TUVT_ID.toString();
            let v_thietbi_id = el.THIETBI_ID.toString();
            let v_soluong = el.LUONGCAP.toString();
            let serial = "";
            if (el.SERIAL.toString()) {
              serial = el.SERIAL.toString();
            }
            let ma_tbi = "";
            if (el.MA_TBI.toString()) {
              ma_tbi = el.MA_TBI.toString();
            }
            let lydocap = this.lyDoCapSelected_value;
            let tinhtrang_tbi = this.tinhTrangTBCapSelected;
            if (this.params.p_id == 1) {
              await this.fetchThemMoiPhieuCapVT(
                v_phieuvt_id,
                this.params.phieu_id,
                v_ngay_cap,
                v_kieutbi,
                v_trangbi,
                v_tuvt_id,
                v_thietbi_id,
                v_soluong,
                serial,
                ma_tbi,
                lydocap,
                this.mucDichCapSelected,
                this.params.thueBaoId,
                0,
                tinhtrang_tbi,
                this.params.vthuebao_id
              );
              loi_cap = this.statusThemMoiPhieuCapVT;
            } else {
              await this.fetchThemMoiPhieuCapVT(
                v_phieuvt_id,
                this.params.phieu_id,
                v_ngay_cap,
                v_kieutbi,
                v_trangbi,
                v_tuvt_id,
                v_thietbi_id,
                v_soluong,
                serial,
                ma_tbi,
                lydocap,
                this.mucDichCapSelected,
                0,
                this.params.baohong_id,
                tinhtrang_tbi,
                this.params.vthuebao_id
              );
              loi_cap = this.statusThemMoiPhieuCapVT;
            }
            if (loi_cap != "1") {
              this.$root.toastError(loi_cap);
              return;
            }
            //Nếu kích hoạt Mesh thành công, thực hiện insert Serial, Account, Thời gian khấu hao (còn lại), Giá trị còn lại
            if (this.params.loaitb_id == 210) {
              try {
                this.loading(true);
                let data = {
                  hdtbId: this.params.hdtb_id,
                  loaiTd: 1,
                  listSerial: [],
                  nguoiCn: this.$root.token.getUserName(),
                  mayCn: "",
                };
                var rs = await this.$root.context.post(
                  "/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-tinh-trang-tbi-mess",
                  data
                );
                console.log("cap-nhat-tinh-trang-tbi-mess ---  rs = ", rs);
              } catch (error) {
                this.$toast.error("Đã xảy ra lỗi!");
                return;
              } finally {
                this.loading(false);
              }
            }
          }
        }
        if (kt == 0) {
          this.$root.toastError("Cấp thiết bị không thành công!");
        } else {
          this.$root.toastSuccess(
            "Cấp " + kt + " thiết bị thành công \r\n" + loi
          );
        }
        await this.reloadLSVT();
        if (this.listKho.length > 0) {
          await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
        }
        this.params.vt_dccap = [];
        this.listVatTuTrongKho_Checked = [];
        // this.$refs.DSVatTuTrongKho.grid.refreshColumns();
      }
    },
    async btnCapNhat_Click() {
      try{
        if (this.loaiNhomSelected == 1) {
          this.params.p_id = 1;
        } else {
          this.params.p_id = 2;
        }
        console.log("this.kieuTBSelected = ", this.kieuTBSelected);
        if (this.kieuTBSelected.toString() == "1") {
          console.log(1);
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có chắc chắn cập nhật vật tư?", {
              size: "sm",
              okTitle: "Có",
              cancelTitle: "Không",
            })
            .then(async (v) => {
              if (v) {
                isBoxConfirm = true;
              }
            })
            .catch((err) => {});
          if (isBoxConfirm) {
            await this.CapVatTu_Click2();
          }
        } else {
          console.log(2);
          await this.ThuHoiVatTu_V2();
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async fetchThemMoiPhieuCapVT(
      _phieuVtId,
      _phieuId,
      _ngayCap,
      _kieuTbiId,
      _trangBiId,
      _tuVtId,
      _thietBiId,
      _soLuong,
      _serial,
      _maTbi,
      _lyDoCapId,
      _lyDoCapTo,
      _hdTbId,
      _baoHongId,
      _ttTbi,
      _thueBaoId
    ) {
      try {
        this.loading(true);
        console.log(_ngayCap, " = _ngayCap");
        let data = {
          phieuVtId: _phieuVtId,
          phieuId: _phieuId,
          ngayCap: _ngayCap,
          kieuTbiId: _kieuTbiId,
          trangBiId: _trangBiId,
          tuVtId: _tuVtId,
          thietBiId: _thietBiId,
          soLuong: _soLuong,
          serial: _serial,
          maTbi: _maTbi,
          nhanVienId: this.$root.token.getNhanVienID(),
          donViId: this.$root.token.getDonViID(),
          nguoiCn: this.$root.token.getUserName(),
          mayCn: "",
          ipCn: "",
          ngayCn: moment(this.tuNgay).format("DD/MM/yyyy"),
          lyDoCapId: _lyDoCapId,
          lyDoCapTo: _lyDoCapTo,
          hdTbId: _hdTbId,
          baoHongId: _baoHongId,
          ttPk: 1,
          ttTbi: _ttTbi,
          ghiChu: "",
          thueBaoId: _thueBaoId,
        };
        const response = await this.themMoiPhieuCapVT(data);
        if (response.error_code === "BSS-00000000") {
          this.statusThemMoiPhieuCapVT = response.data;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async KiemTraDuLieu_Th() {      
      if (this.value_tenTbSelected == null || this.value_tenTbSelected == "") {
        this.$root.toastError("Bạn phải chọn vật tư");
        return false;
      }
      if (this.soluong_cu == 0) {
        this.$root.toastError("Bạn phải nhập số lượng");
        return false;
      }
     
      if (this.tinhTrangTBSelected == null || this.tinhTrangTBSelected == "") {
        this.$root.toastError("Bạn chưa chọn tình trạng thiết bị");
        return false;
      }
      console.log("1. this.params.ngay_ht = ", this.params.ngay_ht);
      if (this.diffDateTime(this.ngayTH, this.params.ngay_ht) == false) {
        this.$root.toastError("Ngày thu hồi phải lớn hơn ngày hoàn thành");
        return false;
      }

      this.params.loaitbi_id = 0;
      let r = [];
    
      for (const el of this.listTenTb) {
        // this.listTenTb.forEach((el) => {
        if (el.vatTuId == this.value_tenTbSelected) {
          r.push(el);
        }
      }
    
      if (r.length > 0) {
        
        this.params.loaitbi_id = r[0].loaiThietBiId;
        //loaiThietBiId == 2 là "Loại thiết bị có Serial"
        if (r[0].loaiThietBiId.toString().trim() == "2") {
          
          if (this.txtSerialCu.trim() == "") {
          
            this.$root.toastError(
              "Loại vật tư này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị"
            );
            this.$refs.serial_th.focus();
            return false;
          }

          if (this.txtSerialCu.trim() != "") {
           
            if (this.soluong_cu != 1) {
              this.$root.toastError(
                "Vật tư này có seri, số lượng phải nhập = 1"
              );
              soluong_cu.focus();
              return false;
            }
            let serialPatterns = this.TSMD.check_serial;
            // "^[a-zA-Z0-9]*$"
            if (
              this.TSMD.check_serial != "0" &&
              /serialPatterns/.test(this.txtSerialCu) == true
            ) {
              this.$root.toastError(
                "Serial có ký tự không hợp lệ. Các ký tự cho phép " +
                  this.TSMD.check_serial +
                  ""
              );
              this.$refs.serial_th.focus();
              return false;
            }
          }
        } else if (r[0].loaiThietBi.toString().trim() == "3") {
     
          return true;
        } else {
         
          if (this.txtSerialCu.trim() != "") {
            this.$root.toastError(
              "Loại vật tư này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial"
            );
            this.$refs.serial_th.focus();
            return false;
          }
        }
      } else {
        console.log("KTTH 4");
        this.$root.toastError(
          "Không lấy được vật tư thu hồi ! (loaitbi_id:" +
            this.params.loaitbi_id +
            ")"
        );
        return false;
      }
      return true;
    },
    async KiemTra(vserial) {
      console.log("KIỂM TRA");
      this.thuHoi.dt_sd = [];
      this.thuHoi.dt_tbidacap_sd = [];
      await this.fetchDSTBSuDungVT(vserial);
      this.thuHoi.dt_sd = this.thuHoi.dt;
      await this.fetchThongTinTBDaCapTheoSerial(vserial);
      this.thuHoi.dt_tbidacap_sd = this.thuHoi.dt_tbidacap;
      console.log("this.thuHoi.dt_tbidacap_sd = ", this.thuHoi.dt_tbidacap_sd);
      if (this.thuHoi.dt_tbidacap_sd.length > 0) {
        // Kiểm tra xem đã từng có ai thu hồi serial này chưa
        this.$root.toastError(
          "Serial " +
            vserial +
            " đã được thu hồi vào  ngày " +
            this.thuHoi.dt_tbidacap_sd[0].NgayCap.toString() +
            " bởi user " +
            this.thuHoi.dt_tbidacap_sd[0].NguoiCn.toString() +
            ""
        );
        return true;
      }
      if (this.thuHoi.dt_sd.length > 1) {
        // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
        this.$root.toastError(
          "Bạn hãy kiểm tra lại " +
            vserial +
            ". Hiện đang có " +
            this.thuHoi.dt_sd.length +
            " thuê bao đang sử dụng."
        );
        return true;
      }
      if (this.thuHoi.dt_sd.length == 1) {
        // Nếu có 1 thuê bao thì kiểm tra tiếp
        // Kiemes tra duoc phep sua khong
        if (this.thuHoi.dt[0].thueBaoId.toString() != this.params.vthuebao_id) {
          this.$root.toastWarning(
            "Serial " +
              vserial +
              " đã được sử dụng bởi thuê bao " +
              this.thuHoi.dt_sd[0].maThueBao +
              ". Bạn hãy chọn serial khác"
          );
          return true;
        }
      }
      return false;
    },
    async KiemTraVT(vvattu_id, xoavt, kieu_pvt, vseria_goc, vserial) {
      // await this.fetchKTSoLuongVTTBDangSD(vvattu_id);
      await this.fetchKTSoLuongVTTBDangSD_v2(vvattu_id);
      if (this.thuHoi.slvt.data.length > 0 && vserial != "" && kieu_pvt == 1) {
        // console.log("KiemTraVT 1");
        //Nếu là trường hợp đổi serial thì không cho
        this.$root.toastWarning(
          "Mã vật tư bạn chọn thuê bao đang sử nhiều hơn 1"
        );
        return true;
      } else if (this.thuHoi.slvt.data.length > 1 && kieu_pvt == 4) {
        console.log("KiemTraVT 2");
        this.$bvModal
          .msgBoxConfirm(
            "Thuê bao này đang sử dụng hơn 2 thiết bị cùng mã vật tư. Bạn có chắc chắn muốn thu hồi cả hai không?. Nếu bạn chọn không thì sẽ xóa vật tư còn lại.",
            {
              size: "sm",
              okTitle: "Có",
              cancelTitle: "Không",
            }
          )
          .then(async (v) => {
            if (v) {
              xoavt = 0;
            } else {
              xoavt = 1;
              await this.ThuHoi_VT(
                vvattu_id,
                xoavt,
                kieu_pvt,
                vseria_goc,
                vserial
              );
              return true;
            }
          })
          .catch((err) => {
            this.$root.toastError(err);
            return false;
          });
      }
      console.log("KiemTraVT 3");
      return false;
    },
    async ThuHoi_VT(vvattu_id, xoavt, kieu_pvt, vseria_goc, vserial) {
      try {
        await this.fetchCapNhatSuDungVT_TH(
          2,
          vvattu_id,
          xoavt,
          kieu_pvt,
          vseria_goc,
          vserial
        );
        if (this.statusCapNhatSuDungVT_TH != "1") {
          this.$root.toastWarning(this.statusCapNhatSuDungVT_TH);
        } else {
          this.$root.toastSuccess("Thu hồi thành công!");
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      }
      await this.reloadLSVT();
    },
    async ThuHoiVatTu_V2() {
      let vseria_goc = this.txtSerialCu;
      let vserial = "";
      let vvattu_id = 0;
      let kieu_pvt = 0;
      let phieu_vt_id = this.params.phvt_id;
      let xoavt = 0;
      let nhanvien_id_temp = this.$root.token.getNhanVienID();
      if (this.value_NhanVienSelected.toString() != nhanvien_id_temp) {
        nhanvien_id_temp = this.value_NhanVienSelected;
      }

      if (this.btnNhapMoi_Enabled == false) {
        console.log(1);
        let kt_th = await this.KiemTraDuLieu_Th();
        if (kt_th) {
          
          if (this.params.loaitbi_id == 2) {
           
            if (this.txtSerialCu != "") {
              //Nếu là serial
              await this.fetchKiemTraVatTuThuHoi();
             
              if (this.statusKiemTraVatTuThuHoi != "1") {
                this.$toast.warning(this.statusKiemTraVatTuThuHoi);
                return;
              }
             
              await this.fetchDSTBSuDungVT(
                this.txtSerialCu.trim().toUpperCase()
              );
            
              await this.fetchThongTinTBDaCapTheoSerial(
                this.txtSerialCu.trim().toUpperCase()
              );
              
              if (this.thuHoi.dt_tbidacap.length > 0) {
                // Kiểm tra xem đã từng có ai thu hồi serial này chưa
                this.$root.toastError(
                  "Serial " +
                    this.txtSerialCu +
                    " đã được thu hồi vào  ngày " +
                    this.thuHoi.dt_tbidacap[0].NgayCap +
                    " bởi user " +
                    this.thuHoi.dt_tbidacap[0].NguoiCn +
                    ""
                );
                return;
              }
              console.log("1.2.3.7 = ", this.thuHoi.dt);
              if (this.thuHoi.dt.length > 1) {
                // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
                this.$root.toastError(
                  "Bạn hãy kiểm tra lại " +
                    this.txtSerialCu +
                    ". Hiện đang có " +
                    this.thuHoi.dt.length +
                    " thuê bao đang sử dụng."
                );
                return;
              }
              if (this.thuHoi.dt.length == 1) {
                
                // Nếu có 1 thuê bao thì kiểm tra tiếp
                //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
                if (
                  this.thuHoi.dt[0].thueBaoId.toString() !=
                  this.params.vthuebao_id
                ) {
                  if (this.thuHoi.dt[0].phieuVatTuId.toString() != 0) {
                    // Kiểm tra duoc phep sua khong
                    this.$root.toastWarning(
                      "Serial " +
                        this.txtSerialCu +
                        " đã được sử dụng bởi thuê bao " +
                        this.thuHoi.dt[0].maThueBao +
                        ". Bạn không có quyền được thu hồi"
                    );
                    return;
                  }
                  this.$bvModal
                    .msgBoxConfirm(
                      "Serial " +
                        this.txtSerialCu +
                        " đã được sử dụng bởi thuê bao " +
                        this.thuHoi.dt[0].maThueBao +
                        ". Bạn có chắc chắn muốn tiếp tục thu hồi không?",
                      {
                        size: "sm",
                        okTitle: "Có",
                        cancelTitle: "Không",
                      }
                    )
                    .then(async (v) => {
                      if (v) {
                        this.params.checkModal = 1;
                        this.$bvModal.show("modal-doiSerial");
                      }
                    })
                    .catch((err) => {
                      return;
                    });
                }
                this.params.vsdvt_id = this.thuHoi.dt[0].suDungVtId;
                vserial = this.txtSerialCu.toUpperCase();
                kieu_pvt = 4; //Không đổi
                let v_KiemTraVT = await this.KiemTraVT(
                  vvattu_id,
                  xoavt,
                  kieu_pvt,
                  vseria_goc,
                  vserial
                );
               
                if (v_KiemTraVT) {
                  return;
                } else {
                  await this.ThuHoi_VT(
                    vvattu_id,
                    xoavt,
                    kieu_pvt,
                    vseria_goc,
                    vserial
                  );
                }
              }
              if (this.thuHoi.dt.length <= 0) {            
                kieu_pvt = 3; //Khong tim thay cua thue bao nao
                this.params.vsdvt_id = 0;
                vvattu_id = this.value_tenTbSelected.toString();
                vserial = this.txtSerialCu.toUpperCase();
                let v_KiemTraVT = await this.KiemTraVT(
                  vvattu_id,
                  xoavt,
                  kieu_pvt,
                  vseria_goc,
                  vserial
                );
                if (v_KiemTraVT) {
                  return;
                } else {
                  await this.ThuHoi_VT(
                    vvattu_id,
                    xoavt,
                    kieu_pvt,
                    vseria_goc,
                    vserial
                  );
                }
              }
            }
          }
          if (this.params.loaitbi_id == 3) {
            console.log("1.2.4");
            if (this.params.vsdvt_id > 0) {
              await this.fetch_getSL_DD();
              if (this.thuHoi.ktsl.length > 0) {
                if (this.thuHoi.ktsl.toString() != "0") {
                  let sl_treo = parseFloat(this.thuHoi.ktsl);
                  if (
                    this.params.sl_goc - sl_treo <=
                      parseFloat(this.soluong_cu) ||
                    this.params.sl_goc - sl_treo <= 0
                  ) {
                    this.$root.toastWarning(
                      "Hiện số lượng cấp thu hồi của thuê chưa QT là " +
                        sl_treo +
                        "/" +
                        this.params.sl_goc +
                        ". Bạn hãy kiểm tra lại số lượng thu hồi"
                    );
                    return;
                  }
                } else {
                  await this.fetch_getSL_HT();
                  if (
                    parseFloat(this.thuHoi.kt_sl.toString()) <
                    parseFloat(this.soluong_cu)
                  ) {
                    this.$root.toastWarning(
                      "Bạn thu hồi quá số lượng hiện tại của thuê bao"
                    );
                    return;
                  }
                }
              }
              if (!this.btnNhapMoi_Enabled) {
                await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
                  1,
                  0,
                  "CUONG1094",
                  0,
                  1
                );
                if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                  this.$root.toastSuccess("Thu hồi thành công");
                }
              }
              if (this.btnNhapMoi_Enabled) {
                this.$bvModal
                  .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                    size: "sm",
                    okTitle: "Có",
                    cancelTitle: "Không",
                  })
                  .then(async (v) => {
                    if (v) {
                      await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
                        2,
                        phieu_vt_id,
                        "CUONG1094",
                        0,
                        1
                      );
                      if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                        this.$root.toastSuccess("Cập nhật dữ liệu thành công");
                      }
                    }
                  })
                  .catch((err) => {
                    return;
                  });
              }
            } // Nhap cap khong co trong kho
            else {
              if (!this.btnNhapMoi_Enabled) {
                await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
                  1,
                  0,
                  "CUONG1094",
                  0,
                  3
                );
                if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                  this.$root.toastSuccess("Thu hồi thành công");
                }
              }
              if (this.btnNhapMoi_Enabled) {
                this.$bvModal
                  .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                    size: "sm",
                    okTitle: "Có",
                    cancelTitle: "Không",
                  })
                  .then(async (v) => {
                    if (v) {
                      await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
                        2,
                        phieu_vt_id,
                        0,
                        3
                      );
                      if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                        this.$root.toastSuccess("Cập nhật dữ liệu thành công");
                      }
                    }
                  })
                  .catch((err) => {
                    return;
                  });
              }
            }
          }
          if (this.params.loaitbi_id != 3 && this.params.loaitbi_id != 2) {
           
            if (this.params.vsdvt_id > 0) {             
              await this.fetch_getSL_DD();          
              if (this.thuHoi.ktsl.length > 0) {
                if (this.thuHoi.ktsl.toString() != "0") {
                  let sl_treo = parseFloat(this.thuHoi.ktsl.toString());
                  if (
                    this.params.sl_goc - sl_treo <=
                      parseFloat(this.soluong_cu) ||
                    this.params.sl_goc - sl_treo <= 0
                  ) {
                    this.$root.toastWarning(
                      "Hiện số lượng cấp thu hồi của thuê chưa QT là " +
                        sl_treo +
                        "/" +
                        this.params.sl_goc +
                        ". Bạn hãy kiểm tra lại số lượng thu hồi"
                    );
                    return;
                  }
                }
                // Insert PVT
              }
              if (!this.btnNhapMoi_Enabled) {
                await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
                  1,
                  phieu_vt_id,
                  "DAYTB",
                  0,
                  1
                );
                if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                  this.$root.toastSuccess("Thu hồi thành công");
                }
              }
              if (this.btnNhapMoi_Enabled) {
                this.$bvModal
                  .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                    size: "sm",
                    okTitle: "Có",
                    cancelTitle: "Không",
                  })
                  .then(async (v) => {
                    if (v) {
                      await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
                        2,
                        phieu_vt_id,
                        "DAYTB",
                        0,
                        1
                      );
                      if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                        this.$root.toastSuccess("Cập nhật dữ liệu thành công");
                      }
                    }
                  })
                  .catch((err) => {
                    return;
                  });
              }
            } else {              
              if (this.btnNhapMoi_Enabled) {
                this.$bvModal
                  .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                    size: "sm",
                    okTitle: "Có",
                    cancelTitle: "Không",
                  })
                  .then(async (v) => {
                    if (v) {
                      await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
                        2,
                        phieu_vt_id,
                        "DAYTB",
                        0,
                        1
                      );
                      if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                        this.$root.toastSuccess("Cập nhật dữ liệu thành công");
                      }
                    }
                  })
                  .catch((err) => {
                    return;
                  });
              }
              if (!this.btnNhapMoi_Enabled) {
                await this.fetch_CAPNHAT_PHIEUVT_THUHOI(1, 0, "DAYTB", 0, 1);
                if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
                  this.$root.toastSuccess("Cập nhật dữ liệu thành công");
                }
              }
            }
            await this.reloadLSVT();
            await this.fetchDSVatTuThueBaoSuDung(this.params.vthuebao_id);
          }
        }
      } else {
        console.log(2);
        if (this.btnNhapMoi_Enabled) {
          
          if (phieu_vt_id == 0) {
            this.$root.toastError("Chưa có thông tin về phiếu vật tư! ");
            return;
          }
        }
        let kt_th = await this.KiemTraDuLieu_Th();
        if (kt_th) {          
          // Đoạn này kiểm tra các điều kiện cần thiết
          if (this.params.loaitbi_id == 2) {
            await this.fetch_CAPNHAT_PHIEUVT_THUHOI(
              2,
              phieu_vt_id,
              vseria_goc,
              0,
              1
            );
            if (this.statusCAPNHAT_PHIEUVT_THUHOI == "1") {
              this.$root.toastSuccess("Cập nhật dữ liệu thành công");
            }
          }
        }
      }
    },
    DSVatTuTBDaSuDung_selectedRowChanged(data) {
      if (this.check_pthuHoi == false) {
        console.log(data);
      } else {
        this.check_pthuHoi = false;
      }
      // console.log("list_pthuHoi = ", this.list_pthuHoi);
      // if (this.list_pthuHoi) {
      //   console.log("Vào 1");
      //   this.btnNhapMoi_Enabled = false;
      //   if (data) {
      //     this.params.sl_goc = 0;
      //     if (
      //       (data.SERIAL == null || data.SERIAL == "") &&
      //       (data.MA_TBI == null || data.MA_TBI == "")
      //     ) {
      //       this.txtSerialCu = "";
      //       this.statusSoluong_cu = false;
      //       this.readonlyTenTbSelected = false;
      //       this.statusTenTb = false;
      //       this.statusTxtSerialCu = false;
      //     } else if (
      //       (data.SERIAL != null || data.SERIAL != "") &&
      //       (data.MA_TBI == null || data.MA_TBI == "")
      //     ) {
      //       this.txtSerialCu = data.SERIAL;
      //       this.statusSoluong_cu = true;
      //       this.readonlyTenTbSelected = true;
      //       this.statusTxtSerialCu = true;
      //     } else if (
      //       (data.SERIAL == null || data.SERIAL == "") &&
      //       (data.MA_TBI != null || data.MA_TBI != "")
      //     ) {
      //       this.txtSerialCu = data.MA_TBI;
      //       this.statusSoluong_cu = false;
      //       this.readonlyTenTbSelected = false;
      //       this.statusTenTb = false;
      //       this.statusTxtSerialCu = false;
      //     }
      //     this.tinhTrangTBSelected = data.TINHTRANGTBI_ID;
      //     this.text_tenTbSelected = data.TEN_VT_NEW;
      //     this.value_tenTbSelected = data.VATTU_ID;
      //     this.txtGhiChu = data.GHICHU;
      //     this.soluong_cu = data.SOLUONG;
      //     this.params.sl_goc = data.SOLUONG;
      //     this.params.vsdvt_id = data.SDVT_ID;
      //     this.params.vphieuvt_id = data.PHIEUVT_ID;
      //   }
      // } else {
      //   console.log("Vào 2");
      //   this.params.pnlThuHoi = false;
      //   this.list_pthuHoi = false;
      // }
    },
    async removeVatTu() {
      this.$bvModal
        .msgBoxConfirm(
          "Bạn có thực sự muốn xóa phiếu vật tư đang chọn không?",
          {
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then(async (v) => {
          if (v) {
            try {
              this.loading(true);
              //event
              alert("Xóa");
            } catch (error) {
            } finally {
              this.loading(false);
            }
          }
        })
        .catch((err) => {
          this.$toast.error("Xóa phiếu vật tư lỗi");
        });
    },
    async reloadLSVT() {
      await this.fetchDSVatTuDaCap(
        this.params.phieu_id,
        this.params.baohong_id,
        this.params.hdtb_id
      );
    },
    async editGridVatTuDaCap(arg) {
      let f = this.$refs.DSVatTuTrongKho;
      let changes = f.getBatchChanges();
      if (changes.changedRecords.length == 0) {
        return;
      }
      if (arg.columnName == "LUONGCAP") {
        if (
          parseFloat(changes.changedRecords[0].LUONGCAP) >
          parseFloat(changes.changedRecords[0].SL_TON)
        ) {
          for (var e of this.listVatTuTrongKho) {
            if (e.TUVT_ID == changes.changedRecords[0].TUVT_ID) {
              e.LUONGCAP = 0;
            }
          }
          // changes.changedRecords[0].LUONGCAP = 0;
          this.listVatTuTrongKho_Checked = this.removeElementInArray(
            this.listVatTuTrongKho_Checked,
            changes.changedRecords[0].TUVT_ID
          );
          this.$toast.error(
            "Số lượng cấp không được lớn hơn số lượng hiện tại!"
          );
        } else if (parseFloat(changes.changedRecords[0].LUONGCAP) < 0) {
          for (var e of this.listVatTuTrongKho) {
            if (e.TUVT_ID == changes.changedRecords[0].TUVT_ID) {
              e.LUONGCAP = 0;
            }
          }
          // changes.changedRecords[0].LUONGCAP = 0;
          this.listVatTuTrongKho_Checked = this.removeElementInArray(
            this.listVatTuTrongKho_Checked,
            changes.changedRecords[0].TUVT_ID
          );
          this.$toast.error("Số lượng cấp không được âm!");
        } else if (parseFloat(changes.changedRecords[0].LUONGCAP) == 0) {
          for (var e of this.listVatTuTrongKho) {
            if (e.TUVT_ID == changes.changedRecords[0].TUVT_ID) {
              e.LUONGCAP = 0;
            }
          }
          this.listVatTuTrongKho_Checked = this.removeElementInArray(
            this.listVatTuTrongKho_Checked,
            changes.changedRecords[0].TUVT_ID
          );
        } else {
          for (var e of this.listVatTuTrongKho) {
            if (e.TUVT_ID == changes.changedRecords[0].TUVT_ID) {
              e.LUONGCAP = changes.changedRecords[0].LUONGCAP;
            }
          }
          if (
            !this.listVatTuTrongKho_Checked.includes(
              changes.changedRecords[0].TUVT_ID
            )
          ) {
            this.listVatTuTrongKho_Checked.push(
              changes.changedRecords[0].TUVT_ID
            );
          }
        }
      }
      // f.batchUpdate(changes)
      // this.$refs.DSVatTuTrongKho.grid.refreshColumns()
    },
    removeElementInArray(array, element) {
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array;
    },
    async fetchKTTTHongVT(_listTinhTrang) {
      this.loading(true);
      let data = {
        listTinhTrang: _listTinhTrang,
      };
      const response = await this.getKTTTHongVT(data);
      // console.log("response Kiểm tra tình tràng Hỏng VT => ", response);
      if (response.error_code === "BSS-00000000") {
        this.tinhTrangHongVT = response.data;
      }
      this.loading(false);
    },
    btnNhapMoi_Click() {
      this.Clear();
      this.btnNhapMoi_Enabled = false;
      this.statusBtnCapNhap = true;
      this.statusTxtSerialCu = true;
      this.txtSerialCu = "";
      this.statusSoluong_cu = false;
      this.readonlyTenTbSelected = false;
      this.statusTxtSerialCu = false;
      this.btnNhapMoi_Visible = true;
    },
    tsbtnHuyBo_Click() {
      this.Clear();
      // txtSerial.Properties.ReadOnly = true;
      this.btnNhapMoi_Enabled = true;
      this.btnNhapMoi_Visible = true;
      this.btnNhapMoi_Enabled = false;
      this.txtSerialCu = "";
      this.statusSoluong_cu = false;
      // cboVatTu.Properties.ReadOnly = false;
      this.readonlyTenTbSelected = true;
      this.statusTxtSerialCu = false;
    },
    async DoKiem() {
      await this.fetchThamSoMacDinh();
      if (
        (this.MAP_ID.vuser_alt == "" ||
          this.MAP_ID.vpass_alt == "" ||
          this.MAP_ID.vuser_alt == null) &&
        this.thamSoMacDinh == 1
      ) {
        return (this.statusDoKiem = "OK");
      }
      if (
        (this.MAP_ID.vuser_xtest == "" ||
          this.MAP_ID.vpass_xtest == "" ||
          this.MAP_ID.vpass_xtest == null) &&
        this.thamSoMacDinh == 2
      ) {
        return (this.statusDoKiem = "OK");
      }
      //Đo kiểm trước khi nghiệm thu
      // DichVuVienThong.ADSL = 4
      if (this.params.vdichvuvt_id != 4) {
        return (this.statusDoKiem = "OK");
      }

      //       BNMService bnm = new BNMService();
      let vInput = this.params.ma_tb_cur;
      let shelf = "";
      let slot = "";
      let portvl = "";
      let port_id = "";
      let port = "";
      let rack = "";
      let vip = "";
      let vport_visa = "";
      let vonu_id = "";
      let vport_adsl = "";
      let vloai_tbi = "";
      let thamso = "";
      let vIP_xtest = "";
      let vport_bnm = "";
      let vloai_gpon = "";
      let vInputXtest = "";
      if (this.loaiNhomSelected.toString() == "1") {
        await this.fetchKTMaTBCoTonTai();
        if (
          this.statusKTMaTBTonTai.toString() != "0" &&
          this.statusKTMaTBTonTai.toString() != "1"
        ) {
          if (this.params.vthuebao_id != 0) {
            await this.fetch_LAY_PORT_THEO_TBID(-1, this.params.vthuebao_id);
          }
        } else {
          await this.fetch_LAY_DS_PORT_THEOHD_TB(this.params.hdtb_id, -1);
        }
      } else {
        await this.fetch_LAY_DS_PORT_THEOHD_TB(-1, this.params.vthuebao_id);
      }
      if (this.dtPort.length > 0) {
        console.log("DK 1 = ", this.dtPort);
        if (this.dtPort[0].PORT_ID.toString() == "") {
          console.log("DK 1.1 PORT_ID = ", this.dtPort[0].PORT_ID);
          return (this.statusDoKiem = "Thuê bao chưa được cấp port (ERROR)");
        }
        if (
          this.dtPort[0].THAMSO.toString() == "4" &&
          this.$root.token.getMaTinh() != "HNI"
        ) {
          console.log("DK 1.2 THAMSO = ", this.dtPort[0].THAMSO);
          return (this.statusDoKiem = "OK");
        }
        if (this.dtPort[0].LOAITBI_ID.toString() != "3") {
          console.log("DK 1.3 LOAITBI_ID = ", this.dtPort[0].LOAITBI_ID);
          return (this.statusDoKiem = "OK");
        }
        if (this.dtPort.length > 0) {
          vip = this.dtPort[0].IP.toString();
          vport_adsl = this.dtPort[0].PORT_ADSL.toString();
          vport_visa = this.dtPort[0].PORT_VISA.toString();
          vonu_id = this.dtPort[0].ONU_ID.toString();
          vInput = vip + ":" + vport_visa;
          vloai_tbi = this.dtPort[0].LOAITBI_ID.toString();
          thamso = this.dtPort[0].THAMSO.toString();
          vIP_xtest = this.dtPort[0].PORTINFO.toString();
          if (vonu_id != "") {
            vInput += "/" + vonu_id;
          }
          if (vip == "") {
            return (this.statusDoKiem = "Thuê bao chưa được cấp port");
          }
          if (this.thamSoMacDinh == 3 && this.dtPort[0].includes("LOAI_GPON")) {
            vloai_gpon = this.dtPort[0].LOAI_GPON.toString();
            vport_bnm = this.dtPort[0].PORT_BNM.toString();
            vInputXtest =
              vip +
              ":" +
              vport_bnm +
              ":" +
              vloai_gpon +
              ":" +
              this.dtPort[0].LOAITBI_ID.toString();
          }
        } else {
          return (this.statusDoKiem = "Thuê bao chưa được cấp port");
        }
      } else {
        return (this.statusDoKiem = "Thuê bao chưa được cấp port (ERROR)");
      }
      //       Decimal dAttUp = 0, dAttDown = 0, dSNRUp = 0, dSNRDown = 0;
      //       DataSet ds;

      await this.fetch_MAP_ID();

      this.MAP_ID.vuser_alt = this.getMapID[0].USER_ALT;
      if (
        this.MAP_ID.vuser_alt == "" ||
        this.MAP_ID.vuser_alt == "-1" ||
        this.MAP_ID.vuser_alt == null
      ) {
        this.MAP_ID.vuser_alt = "";
      }
      this.MAP_ID.vpass_alt = this.getMapID[0].PASS_ALT;
      if (
        this.MAP_ID.vpass_alt == "" ||
        this.MAP_ID.vpass_alt == "-1" ||
        this.MAP_ID.vpass_alt == null
      ) {
        this.MAP_ID.vpass_alt = "";
      }
      this.MAP_ID.vuser_xtest = this.getMapID[0].USER_XTEST;
      if (
        this.MAP_ID.vuser_xtest == "" ||
        this.MAP_ID.vuser_xtest == "-1" ||
        this.MAP_ID.vuser_xtest == null
      ) {
        this.MAP_ID.vuser_xtest = "";
      }
      this.MAP_ID.vpass_xtest = this.getMapID[0].PASS_XTEST;
      if (
        this.MAP_ID.vpass_xtest == "" ||
        this.MAP_ID.vpass_xtest == "-1" ||
        this.MAP_ID.vpass_xtest == null
      ) {
        this.MAP_ID.vpass_xtest = "";
      }

      let vKq = "";
      if (this.$root.token.getMaTinh() != "HNI") {
        try {
          await this.fetchTestPort(
            this.thamSoMacDinh == 1
              ? this.MAP_ID.vuser_alt
              : this.MAP_ID.vuser_xtest,
            this.thamSoMacDinh == 1
              ? this.MAP_ID.vpass_alt
              : this.MAP_ID.vpass_xtest,
            this.thamSoMacDinh == 1 ? vInput : vIP_xtest,
            this.thamSoMacDinh,
            "",
            "",
            "",
            "",
            "",
            "",
            vloai_tbi,
            thamso
          );
          // if (this.ds_ss != "") {
          return (this.statusDoKiem = this.ds_ss);
          // } else {
          //   return (this.statusDoKiem = "Không đọc được thiết bị (ERROR)");
          // }
        } catch (ex) {
          return (this.statusDoKiem = "BNM-TESTPORT-ERROR | " + ex.data.message);
        }
      } else {
        try {
          await this.fetchTestPort(
            this.thamSoMacDinh == 1
              ? this.MAP_ID.vuser_alt
              : this.MAP_ID.vuser_xtest,
            this.thamSoMacDinh == 1
              ? this.MAP_ID.vpass_alt
              : this.MAP_ID.vpass_xtest,
            this.thamSoMacDinh == 1 ? vInput : vIP_xtest,
            this.thamSoMacDinh,
            this.dtPort[0].IP,
            this.dtPort[0].RACK,
            this.dtPort[0].SHELF,
            this.dtPort[0].SLOT,
            this.dtPort[0].SPORT,
            this.dtPort[0].ONU_ID,
            null,
            null
          );
          // if (this.ds_ss != "") {
          return (this.statusDoKiem = this.ds_ss);
          // } else {
          //   return (this.statusDoKiem = "Không đọc được thiết bị (ERROR)");
          // }
        } catch (ex) {
          return (this.statusDoKiem = "BNM-TESTPORT-ERROR | " + ex);
        }
      }
      // return (this.statusDoKiem = "OK");
    },
    async tsbtnAutoCap_Click() {
      try{
        if (this.rdioCKb == null || this.rdioCKb == 0) {
          this.$root.toastWarning("Chưa chọn kiểu thuê bao");
          return;
        }
        if (this.check_MA_TB == "" || this.check_MA_TB == null) {
          this.$root.toastWarning("Chưa chọn thuê bao");
          return;
        }
        await this.DoKiem();
        console.log("this.statusDoKiem = ", this.statusDoKiem);
        if (this.statusDoKiem == "OK") {
          this.$root.toastError("Thuê bao không đo kiểm được");
          return
        }

        
        if (this.statusDoKiem.includes("ERROR")) {
          this.$root.toastWarning(this.statusDoKiem);
          return;
        }

        if (
          this.statusDoKiem.includes("VNPT") ||
          this.statusDoKiem.includes("ZTE")
        ) {
          await this.fetch_getInfoTamUngVT(this.statusDoKiem);          
          if (this.tamung.length > 0) {
            let temp_hdbh_id = 0;
            let vk = 0;            
            if (this.params.p_id == 1) {
              temp_hdbh_id = this.params.thueBaoId;
              vk = 1;
            } else {
              temp_hdbh_id = this.params.baohong_id;
              vk = 2;
            }              
           
            if (this.params.vdichvuvt_id == 9) {
              await this.fetchKIEMTRA_VTCAPRA_V2(
                vk,
                temp_hdbh_id,
                this.tamung[0].tuVtId,
                this.params.vdichvuvt_id
              );
              if (
                this.tinhTrangKIEMTRA_VTCAPRA_V2.toString() != "1" ||
                this.tinhTrangKIEMTRA_VTCAPRA_V2 == null
              ) {
                this.$root.toastError(
                  this.tinhTrangKIEMTRA_VTCAPRA_V2.toString()
                );
                return;
              }
            } else {
              await this.fetchKIEMTRA_VTCAPRA_V2(
                vk,
                temp_hdbh_id,
                this.tamung[0].tuVtId,
                this.params.vdichvuvt_id
              );
              if (
                this.tinhTrangKIEMTRA_VTCAPRA_V2.toString() != "1" ||
                this.tinhTrangKIEMTRA_VTCAPRA_V2 == null
              ) {
                this.$root.toastError(
                  this.tinhTrangKIEMTRA_VTCAPRA_V2.toString()
                );
                return;
              }
            }
            
            await this.fetchGetKeyDKVTMuaSam();
            let v_phieuvt_id = this.params.v_phieuvt_id;
            let v_ngay_cap = moment(this.ngayTH).format("DD/MM/YYYY HH:mm:ss");
            let v_kieutbi = this.kieuTBSelected;
            let v_trangbi = this.trangBiSelected;
            let v_tuvt_id = this.tamung[0].tuVtId.toString();
            let v_thietbi_id = this.tamung[0].thietBiId.toString();
            let v_soluong = 1;
            let serial = "";
            if (
              this.tamung[0].serial.toString() != "" ||
              this.tamung[0].serial.toString() != null
            ) {
              serial = this.tamung[0].serial.toString();
            }
            let ma_tbi = "";
            if (this.tamung[0].maTbi != null) {
              ma_tbi = this.tamung[0].maTbi.toString();
            }
            let mucDichId = parseInt(this.mucDichCapSelected.toString());
            let lydocap = this.lyDoCapSelected_value
            let tinhtrang_tbi = parseInt(
              this.tinhTrangTBCapSelected.toString()
            );
            if (this.params.p_id == 1) {
              await this.fetchThemMoiPhieuCapVT(
                v_phieuvt_id,
                this.params.phieu_id,
                v_ngay_cap,
                v_kieutbi,
                v_trangbi,
                v_tuvt_id,
                v_thietbi_id,
                v_soluong,
                serial,
                ma_tbi,
                lydocap,
                mucDichId,
                this.params.thueBaoId,
                0,
                tinhtrang_tbi,
                this.params.vthuebao_id
              );
            } else {
              await this.fetchThemMoiPhieuCapVT(
                v_phieuvt_id,
                this.params.phieu_id,
                v_ngay_cap,
                v_kieutbi,
                v_trangbi,
                v_tuvt_id,
                v_thietbi_id,
                v_soluong,
                serial,
                ma_tbi,
                lydocap,
                mucDichId,
                0,
                this.params.baohong_id,
                tinhtrang_tbi,
                this.params.vthuebao_id
              );
            }
            if (this.statusThemMoiPhieuCapVT != "1") {
              this.$root.toastError(this.statusThemMoiPhieuCapVT);
              return;
            } else {
              this.$root.toastSuccess("Đã cấp thành công");
              this.reloadLSVT();
            }
          } else {
            this.$root.toastError(
              "Không tìm thấy serial GP " + this.statusDoKiem + " trong kho"
            );
            return
          }
        } else {
          this.$root.toastError(
            "Không đúng cấp trúc VNPT hoặc ZTE không thể tự động cấp ( " +
              this.statusDoKiem +
              " )"
          );          
        }
        await this.fetchDSVatTuTrongKho(this.params.loaitb_id);
        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    exportExcelLSVT() {
      this.$bvModal
        .msgBoxConfirm("Bạn có muốn xuất tất cả vật tư đã cấp cho thuê bao?", {
          size: "sm",
          okTitle: "Có",
          cancelTitle: "Không",
        })
        .then(async (v) => {
          if (v) {
            if (this.listVatTuDaCap.length == 0) {
              this.$root.toastError(
                "Danh sách vật tư không có dữ liệu để xuất Excel!"
              );
            } else {
              this.loading(true);
              try {
                var dsach = xlsx.utils.json_to_sheet(this.listVatTuDaCap);
                var wb = xlsx.utils.book_new();
                xlsx.utils.book_append_sheet(wb, dsach, "DS");
                xlsx.writeFile(wb, "DanhSachVatTu.xlsx");

                this.$root.toastSuccess(
                  "Xuất dữ liệu Danh sách vật tư thành công!"
                );
              } catch (error) {
                this.$root.toastError("Xuất dữ liệu không thành công!");
              } finally {
                this.loading(false);
              }
            }
          }
        })
        .catch((err) => {
          this.$toast.error("Hủy thất bại");
        });
    },
    exportExcelDSTB() {
      this.$bvModal
        .msgBoxConfirm("Bạn có muốn xuất tất cả vật tư đã cấp cho thuê bao?", {
          size: "sm",
          okTitle: "Có",
          cancelTitle: "Không",
        })
        .then(async (v) => {
          if (v) {
            if (this.listThueBaoCapVT.length == 0) {
              this.$root.toastError(
                "Danh sách thuê bao không có dữ liệu để xuất Excel!"
              );
            } else {
              this.loading(true);
              try {
                var dsach = xlsx.utils.json_to_sheet(this.listThueBaoCapVT);
                var wb = xlsx.utils.book_new();
                xlsx.utils.book_append_sheet(wb, dsach, "DS");
                xlsx.writeFile(wb, "DanhSachThueBao.xlsx");

                this.$root.toastSuccess(
                  "Xuất dữ liệu Danh sách thuê bao thành công!"
                );
              } catch (error) {
                this.$root.toastError("Xuất dữ liệu không thành công!");
              } finally {
                this.loading(false);
              }
            }
          }
        })
        .catch((err) => {
          this.$toast.error("Hủy thất bại");
        });
    },
    async CapNhatThongTinThietBi() {
      let ts_capnhat_ont = 0;
      await this.fetchDSThamSoMacDinh();      
      if (this.listThamSoMacDinh.length > 0) {
        // await this.fetchDSThamSoMacDinh();
        ts_capnhat_ont = this.setThamSo("CAPNHAT_THONGTIN_ONT", this.listThamSoMacDinh) ? 1 : 0     
      }      

      if (ts_capnhat_ont == 0) {
        return (this.value_CapNhatThongTinThietBi = "OK");
      }
      let hangsx = "";
      let hardware = "";
      let firmware = "";
      let sTrangThai = "NA";
      try {
        this.thamSoMacDinh = 1;
        try {
          await this.fetchThamSoMacDinh();
        } catch (ex) {
          this.thamSoMacDinh = 1;
        }
        //     //Nếu không được  gán user hệ thống xtesst, alinetest thì return ok
        if (
          (this.MAP_ID.vuser_alt == "" ||
            this.MAP_ID.vpass_alt == "" ||
            this.MAP_ID.vuser_alt == null) &&
          this.thamSoMacDinh == 1
        ) {
          return (this.value_CapNhatThongTinThietBi = "OK");
        }
        if (
          (this.MAP_ID.vuser_xtest == "" ||
            this.MAP_ID.vpass_xtest == "" ||
            this.MAP_ID.vpass_xtest == null) &&
          this.thamSoMacDinh == 2
        ) {
          return (this.value_CapNhatThongTinThietBi = "OK");
        }

        let vInput = this.params.ma_tb_cur;

        console.log("this.thamSoMacDinh = ", this.thamSoMacDinh);
        if (this.thamSoMacDinh == 1) {
          await this.fetch_TT_ONT_Theo_Account(
            this.MAP_ID.vuser_alt,
            this.MAP_ID.vpass_alt,
            vInput,
            this.thamSoMacDinh,
            "",
            "",
            "",
            "",
            "",
            ""
          );
        } else {
          await this.fetch_TT_ONT_Theo_Account(
            this.MAP_ID.vuser_xtest,
            this.MAP_ID.vpass_xtest,
            vInput,
            this.thamSoMacDinh,
            "",
            "",
            "",
            "",
            "",
            ""
          );
        }
        console.log("this.ds_ss_ONT = ", this.ds_ss_ONT);
        if (this.ds_ss_ONT.length > 0) {
          hangsx = this.ds_ss_ONT[0].HANG_SX.toString();
          hardware = this.ds_ss_ONT[0].HARDWARE_VERSION.toString();
          firmware = this.ds_ss_ONT[0].FIRMWARE_VERSION.toString();
          // hoancong.cap_nhat_thongtin_ont(hdtb_id, vthuebao_id, hangsx, hardware, firmware, 1);
          return (this.value_CapNhatThongTinThietBi = "OK");
        } else {
          return (this.value_CapNhatThongTinThietBi =
            "Không thể lấy thông tin thiết bị !");
        }
      } catch (ex) {
        return (this.value_CapNhatThongTinThietBi =
          "Không thể lấy thông tin thiết bị!\r\n" + ex);
      }
    },
    async hoancongv2() {
      console.log("HC 1.1.3");
      let error = "";
      // await this.reloadLSVT();
      if (this.listVatTuDaCap.length == 0) {
        console.log("HC 1.1.3.1");
        await this.fetch_ht_phieu_kkhongvt();
        if (
          this.value_ht_phieu_kkhongvt == null ||
          this.value_ht_phieu_kkhongvt == ""
        ) {
          error =
            "Hoàn thiện phiếu " +
            this.params.ma_tb_cur +
            " nội dung không cấp hoặc thu hồi vật tư có lỗi";
          this.params.ds_error.push(error);
        }
        if (this.value_ht_phieu_kkhongvt.toString() != "1") {
          error =
            "Không hoàn thiện được phiếu " +
            this.params.ma_tb_cur +
            " do" +
            this.value_ht_phieu_kkhongvt;
          this.params.ds_error.push(error);
        }
        error = "";
        await this.LoadThongTinThueBao("");
        return;
      }
      let kt = 0;
      if (this.loaiNhomSelected.toString() == "1") {
        console.log(
          "HC 1.1.3.2 =>>> this.params.loaihd_id = ",
          this.params.loaihd_id
        );
        console.log("this.params.boqua = ", this.params.boqua);
        // Nếu là thi công
        if (this.params.loaihd_id != 1) {
          // trừu HD mới
          if (this.params.loaihd_id == 6 && !this.params.boqua) {
            console.log("HC 1.1.3.2.1 = ", this.kqThuHoiSelected);
            //chuyển đổi và bỏ qua thu hồi
            if (this.kqThuHoiSelected.toString() == "0") {
              //Nếu chưa chọn hoặc chon thu hồi đc
              error =
                "Phiếu " +
                this.params.ma_tb_cur +
                " chưa lựa chọn kết quả thu hồi!";
              this.params.ds_error.push(error);
            }
            if (this.kqThuHoiSelected.toString() == "1") {
              //Nếu chưa chọn hoặc chon thu hồi đc
              await this.fetch_KT_TB_DA_ThuHoiVT();
              if (this.value_KT_TB_DA_ThuHoiVT.length == 0) {
                // Nếu ko có vật tư thu hồi kiểm tra tiếp
                error =
                  "Thuê bao " +
                  this.params.ma_tb_cur +
                  " chưa đc thu hồi vật tư không thể chọn kết quả thu hồi được!";
                this.params.ds_error.push(error);
              }
            }
            if (this.kqThuHoiSelected.toString() == "3") {
              //Nếu ko thu hồi đc
              await this.fetch_KT_TB_DA_ThuHoiVT();
              if (this.value_KT_TB_DA_ThuHoiVT.length > 0) {
                // Nếu ko có vật tư thu hồi kiểm tra tiếp
                error =
                  "Thuê bao " +
                  this.params.ma_tb_cur +
                  " vẫn còn vật tư thu hồi. Bạn không thể chọn không thu hồi đc!";
                this.params.ds_error.push(error);
              }
              if (parseInt(this.lyDoTonSelected) == 0) {
                error =
                  "Phiếu " + this.params.ma_tb_cur + " chưa chọn lý do tồn!";
                this.params.ds_error.push(error);
              }
              kt = 1;
            }
          } else if (this.params.boqua) {
            console.log("HC 1.1.3.2.2 = ", this.kqThuHoiSelected);
            // nếu bat buoc
            if (this.kqThuHoiSelected.toString() == "0") {
              //Nếu chưa chọn hoặc chon thu hồi đc
              error =
                "Phiếu " + this.params.ma_tb_cur + " chưa chọn kết quả thu hồi";
              this.params.ds_error.push(error);
            }
            if (this.kqThuHoiSelected.toString() == "1") {
              //Nếu chưa chọn hoặc chon thu hồi đc
              await this.fetch_KT_TB_DA_ThuHoiVT();
              if (this.value_KT_TB_DA_ThuHoiVT.length == 0) {
                // Nếu ko có vật tư thu hồi kiểm tra tiếp
                error =
                  "Thuê bao " +
                  this.params.ma_tb_cur +
                  " chưa đc thu hồi vật tư không thể chọn kết quả thu hồi được!";
                this.params.ds_error.push(error);
              }
            }
            if (this.kqThuHoiSelected.toString() == "3") {
              //Nếu ko thu hồi đc

              await this.fetch_KT_TB_DA_ThuHoiVT();
              if (this.value_KT_TB_DA_ThuHoiVT.length > 0) {
                // Nếu ko có vật tư thu hồi kiểm tra tiếp
                error =
                  "Thuê bao " +
                  this.params.ma_tb_cur +
                  " vẫn còn vật tư thu hồi. Bạn không thể chọn không thu hồi đc!";
                this.params.ds_error.push(error);
              }
              if (parseInt(this.lyDoTonSelected) == 0) {
                error =
                  "Phiếu " + this.params.ma_tb_cur + " chưa chọn lý do tồn";
                this.params.ds_error.push(error);
              }
              kt = 1;
            }
          }
        }
      } else {
        console.log("HC 1.1.3.3");
        if (this.params.boqua_baohong) {
          console.log("HC 1.1.3.3.1 = ", this.kqThuHoiSelected);
          // nếu bat buoc
          if (this.kqThuHoiSelected.toString() == "0") {
            //Nếu chưa chọn hoặc chon thu hồi đc
            error =
              "Phiếu " + this.params.ma_tb_cur + " chưa chọn kết quả thu hồi!";
            this.params.ds_error.push(error);
          }
          if (this.kqThuHoiSelected.toString() == "1") {
            //Nếu chưa chọn hoặc chon thu hồi đc
            await this.fetch_KT_TB_BaoHong_DA_ThuHoiVT();
            if (this.value_KT_TB_BaoHong_DA_ThuHoiVT.length == 0) {
              // Nếu ko có vật tư thu hồi kiểm tra tiếp
              error =
                "Thuê bao " +
                this.params.ma_tb_cur +
                " chưa được thu hồi vật tư không thể chọn kết quả thu hồi được!";
              this.params.ds_error.push(error);
            }
          }
          if (this.kqThuHoiSelected.toString() == "3") {
            //Nếu ko thu hồi đc
            await this.fetch_KT_TB_BaoHong_DA_ThuHoiVT();
            if (this.value_KT_TB_BaoHong_DA_ThuHoiVT.length > 0) {
              // Nếu ko có vật tư thu hồi kiểm tra tiếp
              error =
                "Thuê bao " +
                this.params.ma_tb_cur +
                " vẫn còn vật tư thu hồi. Bạn không thể chọn không thu hồi được!";
              this.params.ds_error.push(error);
            }
            if (parseInt(this.lyDoTonSelected) == 0) {
              error =
                "Phiếu " + this.params.ma_tb_cur + " chưa chọn lý do tồn!";
              this.params.ds_error.push(error);
            }
            kt = 1;
          }
        }
      }
      let boqua_dk = true;
      let boqua_hd = 0;
      let boqua_bh = 0;
      console.log("HC 1.1.3.4");
      await this.fetch_GiaTriDoKiem();
      console.log("this.giaTriDoKiem = ", this.giaTriDoKiem);
      if (this.giaTriDoKiem == null || this.giaTriDoKiem == "") {
        boqua_dk = true;
      } else {
        if (this.giaTriDoKiem.toString() == "1") {
          boqua_dk = false;
        } else {
          boqua_dk = true;
        }
      }

      console.log("boqua_dk = ", boqua_dk);
      if (!boqua_dk) {
        console.log("HC 1.1.3.5 = ", this.params.hdtb_id);
        if (this.params.hdtb_id > 0) {
          await this.fetch_getThongTinPhieuCapVTTB();
          if (parseInt(this.thongTinPhieuCapVTTB) > 0) {
            boqua_hd = 1;
          }
        }
        if (this.params.baohong_id > 0) {
          await this.fetch_getThongTinPhieuCapVTTB_BaoHong();
          if (parseInt(this.thongTinPhieuCapVTTB_BaoHong) > 0) {
            boqua_bh = 1;
          }
        }
        if (boqua_bh + boqua_hd > 0) {
          await this.DoKiem();
          if (this.statusDoKiem != "OK") {
            if (this.statusDoKiem.includes("ERROR")) {
              error =
                "Phiếu " + this.params.ma_tb_cur + ": " + this.statusDoKiem;
              this.params.ds_error.push(error);
            }
            if (
              this.statusDoKiem.includes("VNPT") ||
              this.statusDoKiem.includes("ZTE")
            ) {
              await this.fetch_KT_SR_GPON(this.statusDoKiem.toString());
              if (this.value_KT_SR_GPON != "1") {
                // error =
                //   "Phiếu " + this.params.ma_tb_cur + ": " + this.statusDoKiem;
                this.params.ds_error.push(this.value_KT_SR_GPON);
              }
            }
          }
        }
      } else {        
        if (this.params.hdtb_id > 0) {
          await this.fetch_getThongTinPhieuCapVTTB();
          if (parseInt(this.thongTinPhieuCapVTTB) > 0) {
            boqua_hd = 1;
          }
        }       
        if (this.params.baohong_id > 0) {
          await this.fetch_getThongTinPhieuCapVTTB_BaoHong();
          if (parseInt(this.thongTinPhieuCapVTTB_BaoHong) > 0) {
            boqua_bh = 1;
          }
        }
      }
      if (boqua_bh + boqua_hd > 0) {
        console.log("HC 1.1.3.7");
        await this.CapNhatThongTinThietBi();
        if (this.value_CapNhatThongTinThietBi != "OK") {
          error = "Phiếu " + this.params.ma_tb_cur;
          this.params.ds_error.push(error);
          return;
        }
        console.log("HC 1.1.3.7.1 = ", this.params.ds_error);
      }
      let mucDichCapSelected = this.listMucDichCap.filter(
        (e) => e.value == this.mucDichCapSelected
      );
      let tenMucDich =
        mucDichCapSelected.length > 0 ? mucDichCapSelected[0].text : "";

      await this.$confirm(
        `Bạn có muốn hoàn thành phiếu này không với mục đích cấp là ${tenMucDich} ?`,
        "Thông báo",
        {
          confirmButtonText: "Đồng ý",
        }
      )
        .then((v) => {
          if (v) {
            console.log("Vào rồi nè");
          }
        })
        .catch(() => {
          return;
        });
      console.log("this.params.ds_error = ", this.params.ds_error);
      if (this.loaiNhomSelected.toString() == "1") {
        console.log("HC 1.1.3.8");
        await this.fetch_deleteLyDo(this.params.hdtb_id, 0);
      }
      if (this.loaiNhomSelected.toString() == "2") {
        console.log("HC 1.1.3.9");
        await this.fetch_deleteLyDo(0, this.params.baohong_id);
      }
      let ckb = "";
      // // Nếu là thi công và kiểu quy trình 3 và bao hỏng
      // //CÒn các TH khác thz
      let vkieu = 0;
      if (this.rdioCKb.toString() == "4") {
        console.log("1.1.3.10");
        vkieu = 1;
      }
      console.log("this.TSMD.UPDATE_CTS = ", this.TSMD.UPDATE_CTS);
      if (this.TSMD.UPDATE_CTS) {
        console.log("HC 1.1.3.11");
        await this.fetch_UpdateCTS(
          this.params.hdtb_id > 0
            ? this.params.hdtb_id
            : this.params.baohong_id,
          this.params.hdtb_id > 0 ? 1 : 0
        );
        this.$root.toastWarning(this.value_Update_CTS);
        return;
      }
      console.log("kieugoi_id = ", this.params.kieugoi_id);
      if (this.params.kieugoi_id == 0 || this.params.kieugoi_id == 1) {
        console.log("HC 1.1.3.12");
        await this.fetch_GIAOPHIEU_TD_TAOPHIEU_QLVT(vkieu);
        if (kt == 1 && this.loaiNhomSelected.toString() == "1") {
          console.log("HC 1.1.3.12.1");
          await this.fetch_taoDL_Ton_Khong_ThuHoi_Duoc();
          await this.fetch_updateKQGiaoPhieu();
        } else if (kt == 1 && this.loaiNhomSelected.toString() == "2") {
          console.log("HC 1.1.3.12.2");
          await this.fetch_themMoiDSPhieuTon();
          await this.fetch_updateKQGiaoPhieu();
        }
      } else {
        console.log("HC 1.1.3.13");
        await this.fetch_GIAOPHIEU_TD_TAOPHIEU_QLVT(vkieu);
      }
      await this.LoadThongTinThueBao("");
    },
    ExportExcel_V3(ds_error) {
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(ds_error);
        var wb = xlsx.utils.book_new();
        xlsx.utils.book_append_sheet(wb, dsach, "Sheet 1");
        xlsx.writeFile(wb, "TrangThaiHoanCong.xlsx");
        this.$root.toastSuccess("Xuất dữ liệu thành công!");
      } catch (error) {
        this.$root.toastError("Đã có lỗi khi hoàn thành!");
      } finally {
        this.loading(false);
      }
    },
    async KT_hoanthanh_khong_VT() {
      await this.fetch_kt_thuhoi_khongvt();
      if (this.value_kt_thuhoi_khongvt.toString() != "1") {
        this.$root.toastError(kq_result.toString());
        return (this.value_KT_hoanthanh_khong_VT = false);
      }
      return (this.value_KT_hoanthanh_khong_VT = true);
    },
    async btnHoanCong_Click() {
      try{     
        
        if(this.listVatTuDaCap.length == 0){
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có muốn hoàn thành phiếu của số máy/acc: " + this.params.ma_tb_cur + " với nội dung không cấp hoặc thu hồi vật tư không? ", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } 
            });

          if (!isBoxConfirm) {              
            return;
          }
        }
        this.loading(true);
        if(this.colCheckbox){
          if(this.rowSelectedThueBao.length == 0){
            this.$root.toastError("Chưa chọn thuê bao cần hoàn thành!");
            return
          }
          let data = []
          let ThueBaoSelected = this.listThueBaoCapVT.filter(e=>this.rowSelectedThueBao.includes(e.PHIEU_ID))
          for(const e of ThueBaoSelected){
            data.push({         
              loainhom: this.loaiNhomSelected,
              loaiphieu: this.rdioCKb,
              phieu_id: e.PHIEU_ID,
              ketquaxl: this.kqThuHoiSelected,
              lydoton: this.lyDoTonSelected,
              mucdichcap: this.mucDichCapSelected,         
              kieugoi_id:  this.params.kieugoi_id,
              somay: e.MA_TB         
            });
          }              
          let ketQua = await this.hoanCongDanhSach(data)          
          let dsach = xlsx.utils.json_to_sheet(ketQua);
          let wb = xlsx.utils.book_new();
          xlsx.utils.book_append_sheet(wb, dsach, "DS");
          xlsx.writeFile(wb, "DanhSachHoanCong.xlsx");             
        }else{          
          let ketQua = await this.hoanCongCapVatTuTB({         
            loainhom: this.loaiNhomSelected,
            loaiphieu: this.rdioCKb,
            phieu_id: this.ThueBaoFocus.PHIEU_ID,
            ketquaxl: this.kqThuHoiSelected,
            lydoton: this.lyDoTonSelected,
            mucdichcap: this.mucDichCapSelected,         
            kieugoi_id:  this.params.kieugoi_id         
          })
          this.$root.toastSuccess("Hoàn thiện phiếu thành công");
        }        

        await this.LoadThongTinThueBao("");
        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    showPort() {
   
      this.checkPortDialog.input.vma_tb = this.params.ma_tb_cur;
      this.checkPortDialog.input.vkieu = 0;
      this.checkPortDialog.isVisiable = true;
      this.$refs.popupTestPortV2.show();
    },

    tsbtnXemHSDT_Click() {
      if (this.listThueBaoCapVT.length == 0) {
        this.$toast.error("Không có thuê bao!");
        return;
      }
      if (this.ThueBaoFocus == {}) {
        this.$toast.error("Vui lòng chọn thuê bao!");
        return;
      }

      if (this.params.ma_tb_cur == "") {
        this.$toast.error(
          "Không có Số máy/Acc ! Không thể tra cứu hồ sơ điện tử !"
        );
        return;
      }

      let f = this.$refs.frmTraCuuHSDT;
      f.MaTb = this.params.ma_tb_cur;
      this.$refs.popupTraCuuHSDT.show();
    },

    btnUpLoadHS() {
      this.$refs.popupUploadHoSo.show();
    },

    async inBBTWifiToolStripMenuItem_Click() {
      const baseUrl = process.env.API;
      var hdtb_id = 0;
      var url = "";
      var _maBC = "";

      // let data = {
      //   maBc: "WIFIBB",
      // };
      // const test = await this.getParamsBaoCao(data);
      // console.log("Test WIFIBB = ", test);

      if (this.loaiNhomSelected == "1") {
        hdtb_id = this.params.hdtb_id;
        // url = `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`;
        url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-in-bien-ban-tbi-wf?extend=pdf`;
        _maBC = "WIFIBB";
      } else if (this.loaiNhomSelected == "2") {
        hdtb_id = this.params.baohong_id;
        url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-xac-nhan-wifi?extend=pdf`;
        _maBC = "WIFIBB_BH";
      }

      if (hdtb_id == 0 || hdtb_id == null || url == "") {
        this.$toast.error("Không có dữ liệu in phiếu!");
        return;
      }

      this.loading(true);
      await this.axios({
        url: url,
        method: "POST",
        responseType: "blob",
        data: {
          hdTbId: hdtb_id,
          nhanVienId: this.$auth.getNhanVienID(),
        },
        // data: {
        //   ma_bc: _maBC,
        //   ds_tieuchi: [
        //     {
        //       name: "in_phanvung_id",
        //       value: this.$auth.getPhanVungID(),
        //     },
        //     {
        //       name: "vhdtb_id",
        //       value: hdtb_id,
        //     },
        //     {
        //       name: "vnhanvien_id",
        //       value: this.$auth.getNhanVienID(),
        //     },
        //   ],
        // },
      }).then((response) => {
        previewPrint(response.data);
        this.loading(false);
      });
      this.loading(false);
    },
    async inBBToolStripMenuItem_Click() {
      const baseUrl = process.env.API;
      var hdtb_id = 0;
      var url = "";
      // let data = {
      //   maBc: "BBGVT",
      // };
      // const test = await this.getParamsBaoCao(data);
      // console.log("Test BBGVT = ", test);
      // return;
      if (this.$auth.getMaTinh() == "DBN") {
        hdtb_id = await this.getHopDongKhachHangId(this.this.params.hdtb_id);
      } else {
        hdtb_id = this.params.hdtb_id;
      }
      url = `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`;

      if (hdtb_id == 0 || hdtb_id == null || url == "") {
        this.$toast.error("Không có dữ liệu in phiếu!");
        return;
      }

      this.loading(true);
      await this.axios({
        url: url,
        method: "POST",
        responseType: "blob",
        data: {
          ma_bc: "BBGVT",
          ds_tieuchi: [
            {
              name: "vphanvung_id",
              value: this.$auth.getPhanVungID(),
            },
            {
              name: "vhdtb_id",
              value: hdtb_id,
            },
            {
              name: "vnhanvien_id",
              value: this.$auth.getNhanVienID(),
            },
          ],
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async inBBToolStripMenuItem_Click_BH() {
      const baseUrl = process.env.API;
      var url = "";
      var vbaoHongId = 0;
      vbaoHongId = this.params.baohong_id;
      url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-in-bien-ban-vt-bh?extend=pdf`;
      if (vbaoHongId == 0 || vbaoHongId == null || url == "") {
        this.$toast.error("Không có dữ liệu in phiếu!");
        return;
      }

      this.loading(true);
      await this.axios({
        url: url,
        method: "POST",
        responseType: "blob",
        data: {
          baoHongId: vbaoHongId,
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    txtSoThueBao_KeyDown: async function () {
      if (this.rdioCKb == null || this.rdioCKb.toString() == "0") {
        return;
      }
      this.params.tentb = "";
      this.params.tentb = this.soMaySelected;
      if (this.soMaySelected != "" && this.ckbSerial) {
        await this.fetch_DSTheoSerialOrSoAcc(this.params.tentb);
        if (Object.keys(this.value_DSTheoSerialOrSoAcc).length > 0) {
          this.params.tentb = this.value_DSTheoSerialOrSoAcc.ma_tb;
          this.loaiNhomSelected = this.value_DSTheoSerialOrSoAcc.loai_nhom;
        }
      }
      if (this.params.tentb != "" && !this.ckbSerial) {
        this.LoadThongTinThueBao(this.params.tentb.toString());
      }
    },
    async updateSerialCu(data) {
      console.log("updateSerialCu = ", data);
      this.doiSerial.vatTuId = data.CboOutPut;
      this.doiSerial.serialCu = data.Output;
      this.doiSerial.checkActions = data.Kieu_OutPut;
      let vseria_goc = this.txtSerialCu;
      if (this.params.checkModal == 1) {
        console.log(1);
        if (this.doiSerial.checkActions == 1) {
          // nếu thay đổi serial
          this.params.vsdvt_id = 0;
          this.txtSerialCu = data.Output;
          let v_KiemTra = await this.KiemTra(this.doiSerial.serialCu);
          if (v_KiemTra) {
            return;
          } else {
            let v_KiemTraVT = await this.KiemTraVT(
              this.doiSerial.vatTuId,
              0,
              1,
              vseria_goc,
              this.doiSerial.serialCu
            );
            if (v_KiemTraVT) {
              return;
            } else {
              await this.ThuHoi_VT(
                this.doiSerial.vatTuId,
                0,
                1,
                vseria_goc,
                this.doiSerial.serialCu
              );
            }
          }
        } else {
          this.params.vsdvt_id = 0;
          let v_KiemTraVT = await this.KiemTraVT(
            this.doiSerial.vatTuId,
            0,
            2,
            vseria_goc,
            this.txtSerialCu
          );
          if (v_KiemTraVT) {
            return;
          } else {
            await this.ThuHoi_VT(
              this.doiSerial.vatTuId,
              0,
              2,
              vseria_goc,
              this.txtSerialCu
            );
          }
        }
      } else if (this.params.checkModal == 2) {
        console.log(2);
        if (this.doiSerial.checkActions == 1) {
          if (this.doiSerial.serialCu == data) {
            this.$root.toastWarning("Seri cần thay đổi trùng với seri gốc");
            this.$bvModal.show("modal-doiSerial");
          }
          await this.fetchDSTBSuDungVT(this.doiSerial.serialCu);
          if (this.thuHoi.dt.length > 1) {
            // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
            this.$confirm(
              `Hiện đang có ${this.thuHoi.dt} thuê bao đang sử dụng ${this.doiSerial.serialCu}. Bạn có muốn cập nhật lại seri cho thuê bao này không?`,
              "Thông báo",
              {
                confirmButtonText: "Đồng ý",
              }
            )
              .then(() => {})
              .catch(() => {
                return;
              });
          }
          if (this.thuHoi.dt.length == 1) {
            // Nếu có 1 thuê bao thì kiểm tra tiếp
            //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
            if (
              parseInt(this.thuHoi.dt[0].thueBaoId.toString()) !=
              this.params.vthuebao_id
            ) {
              if (parseInt(this.thuHoi.dt[0].phieuVatTuId.toString()) != 0) {
                // Kiểm tra duoc phep sua khong
                this.$root.toastWarning(
                  "Serial " +
                    this.doiSerial.serialCu +
                    " đã được sử dụng bởi thuê bao " +
                    this.thuHoi.dt[0].maThueBao +
                    ". Bạn không có quyền được thu hồi"
                );
                frm.Close();
                return;
              }
              this.$confirm(
                `Serial ${this.doiSerial.serialCu} đã được sử dụng bởi thuê bao ${this.thuHoi.dt[0].maThueBao}. Bạn có chắc chắn muốn tiếp tục sửa không?`,
                "Thông báo",
                {
                  confirmButtonText: "Đồng ý",
                }
              )
                .then(() => {})
                .catch(() => {
                  return;
                });
            }
          }
          try {
            await this.fetchCapNhatSuDungVT_TH(
              1,
              this.doiSerial.vatTuId,
              0,
              0,
              this.txtSerialCu,
              this.doiSerial.serialCu
            );
            if (this.statusCapNhatSuDungVT_TH != "1") {
              this.$root.toastWarning(this.statusCapNhatSuDungVT_TH);
            } else {
              this.$root.toastSuccess("Thu hồi thành công!");
            }
          } catch (ex) {
            this.$root.toastError(ex.data.message);
          }
          // await this.reloadLSVT();
          await this.fetchDSVatTuThueBaoSuDung(this.params.vthuebao_id);
        }
      }
      this.$bvModal.hide("modal-doiSerial");
      this.params.checkModal = 0;
    },
    huyDoiSerial(data) {
      this.doiSerial.vatTuId = data.CboOutPut;
      this.doiSerial.serialCu = data.Output;
      this.doiSerial.checkActions = data.Kieu_OutPut;
      this.$bvModal.hide("modal-doiSerial");
    },
    changeNgayTH(data) {
      // var aaaaaaa = moment(this.ngayTH).format("DD/MM/yyyy HH:mm:ss");
    },
    diffDateTime(date1, date2) {
      console.log("date1 = ", date1);
      console.log("date2 = ", date2);
      let temp = date1.toString().split(" ");
      var value = date1;
      for (const i of temp) {
        if (
          i == "Mon" ||
          i == "Tue" ||
          i == "Wed" ||
          i == "Thu" ||
          i == "Fri" ||
          i == "Sat" ||
          i == "Sun"
        ) {
          date1 = moment(value).format("DD/MM/yyyy HH:mm");
        }
      }
      //Ngày thực hiện
      var ArrNgayTH = date1.toString().split(" ");
      // console.log("ArrNgayTH = ", ArrNgayTH);

      var ArrNgayTH_Date = ArrNgayTH[0].split("/");
      // console.log("ArrNgayTH_Date = ", ArrNgayTH_Date);
      var ngayTH_Year = ArrNgayTH_Date[2];
      var ngayTH_Month = ArrNgayTH_Date[1];
      var ngayTH_Day = ArrNgayTH_Date[0];

      var ArrNgayTH_Time = ArrNgayTH[1].split(":");
      // console.log("ArrNgayTH_Time = ", ArrNgayTH_Time);
      var ngayTH_Hour = ArrNgayTH_Time[0];
      var ngayTH_Minute = ArrNgayTH_Time[1];

      //Ngày hoàn thành
      var ArrNgayHoanThanh = date2.toString().split(" ");
      // console.log("ArrNgayHoanThanh = ", ArrNgayHoanThanh);

      var ArrNgayHoanThanh_Date = ArrNgayHoanThanh[0].split("/");
      // console.log("ArrNgayHoanThanh_Date = ", ArrNgayHoanThanh_Date);
      var ngayHoanThanh_Year = ArrNgayHoanThanh_Date[2];
      var ngayHoanThanh_Month = ArrNgayHoanThanh_Date[1];
      var ngayHoanThanh_Day = ArrNgayHoanThanh_Date[0];

      var ArrNgayHoanThanh_Time = ArrNgayHoanThanh[1].split(":");
      // console.log("ArrNgayHoanThanh_Time = ", ArrNgayHoanThanh_Time);
      var ngayHoanThanh_Hour = ArrNgayHoanThanh_Time[0];
      var ngayHoanThanh_Minute = ArrNgayHoanThanh_Time[1];

      var check1 = parseInt(
        ngayTH_Year + ngayTH_Month + ngayTH_Day + ngayTH_Hour + ngayTH_Minute
      );

      var check2 = parseInt(
        ngayHoanThanh_Year +
          ngayHoanThanh_Month +
          ngayHoanThanh_Day +
          ngayHoanThanh_Hour +
          ngayHoanThanh_Minute
      );

      if (check1 == check2) {
        return true;
      } else if (check1 > check2) {
        return true;
      } else {
        return false;
      }
    },
    async changeMucDichCap() {
      if (this.loaiNhomSelected.toString() == "1") {
        await this.fetchDMMucDichLyDo(6);
      } else {
        await this.fetchDMMucDichLyDo(5);
      }
    },
    async CapVatTu_Click2() {
      let kt = 0;
      let tc = 0;
      let loi = "";
      let loi_cap = "";
      let nhanvien_id_temp = this.$root.token.getNhanVienID();
      if (this.value_NhanVienSelected != this.$root.token.getNhanVienID()) {
        nhanvien_id_temp = this.value_NhanVienSelected;
      }
      console.log(
        "this.listVatTuTrongKho_Checked = ",
        this.listVatTuTrongKho_Checked
      );

      var result = [];
      for (const el of this.listVatTuTrongKho_Checked) {
        for (const temp of this.listVatTuTrongKho) {
          if (el == temp.TUVT_ID) {
            result.push({
              tuvt_id: temp.TUVT_ID,
              soluong: temp.LUONGCAP,
            });
          }
        }
      }
      console.log("result = ", result);
      // await this.fetch_BtnCapVatTu(result);
      await this.CapVatTuTheoPhieuFocus(result);
    },
    chuyenTo(){
      console.log(this.ThueBaoFocus , " = this.ThueBaoFocus")
      if (!("PHIEU_ID" in this.ThueBaoFocus) || this.ThueBaoFocus.PHIEU_ID === null || this.ThueBaoFocus.PHIEU_ID === "") {
        this.$toast.error("Chưa chọn thuê bao!");
        return;
      }
      this.$bvModal.show("popupNoiDung");
    },
    async XacNhanChuyenTo(){
      try{
        if(this.NDChuyenTo.trim() == ""){
          this.$toast.error("Chưa nhập nội dung!")
          return
        }
        this.$bvModal.hide("popupNoiDung");

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn chuyển tổ không ( đơn vị chuẩn lấy từ đơn vị khu vực ) ?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } 
          });

        if (!isBoxConfirm) {
          return;
        }

        let KetQua = await this.ChuyenToThiCong({
          phieu_id : this.ThueBaoFocus.PHIEU_ID,
          hdtb_id: this.params.hdtb_id,
          nhanvien_id: this.$auth.getNhanVienID(),
          noiDung: this.NDChuyenTo
        })
        this.NDChuyenTo = ""
        if(KetQua == "OK"){
          await this.btnLayTT()
          this.$toast.success("Chuyển tổ thành công!")
        }else{
          this.$toast.success(KetQua)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    }
  },
  directives: {
    ClickOutside,
  },
  async created() {
    try{
      this.params.Tag =
        this.$route.query.Tag == undefined
          ? "1"
          : this.$route.query.Tag == 1
          ? "1"
          : "2";
      await this.fetchDMKho();
      await this.fetchLoaiHDvaPL(this.loaiNhomSelected);
      this.getQuyenNguoiDungCVTTB();
      await this.fetchDMDichVu();
      this.fetchDMNhanVien();
      this.fetchDMKieuTB();
      await this.fetchDMTinhTrangTBCap();
      this.getDataLyDoTon(this.kqThuHoiSelected);
      this.fetchDMLyDoCap();
      this.fetchDMTrangBi();
      await this.changeMucDichCap();
      await this.fetchDSLoaiHopDongMucDich();
      this.fetchDMTenThietBi();
      await this.load_Form();
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
};
</script>
