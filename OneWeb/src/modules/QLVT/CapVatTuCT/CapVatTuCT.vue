<template src='./CapVatTuCT.html'></template>
<style scoped src='./CapVatTuCT.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import ClickOutside from "vue-click-outside";
import moment from "moment";
import { previewPrint } from "../../../utils/util";
import xlsx from "xlsx";
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
          this.parent.btnXoaLSVT_ButtonClick(data);
        },
      },
    },
  };
};
export default {
  components: { breadcrumb },
  name: "CapVatTuCT",
  mounted() {},
  watch: {
    rdioCKb: {
      handler: async function (data) {
        await this.fetchDSCongTrinhCapVT();
        await this.NAP_DS_TON_VT();
      },
    },
    cboKieuTb: {
      handler: async function (data) {
        console.log("cboKieuTb = ", data);
        this.tsbtnExcelMau_Visible = false;
        this.tsbtnNhapExcel_Visible = false;
        this.btnCapNhat_Visible = false;
        this.btnNhapMoi_Visible = false;
        this.pnlThuHoi = false;
        this.btnHuyBo_Visible = false;
        if (data.toString() == "1") {
          this.xtraTabPage1 = true;
          this.xtraTabPage2 = false;
          this.btnCapNhat_Visible = true;
        } else {
          this.tsbtnExcelMau_Visible = true;
          this.tsbtnNhapExcel_Visible = true;
          this.HUYBO();
          this.xtraTabPage1 = false;
          this.xtraTabPage2 = true;
          this.btnNhapMoi_Visible = true;
        }
        await this.NAP_DS_CAPTHUVT();
      },
    },
    cboKho: {
      handler: async function (data) {
        try {
          this.loading(true);
          await this.NAP_DS_TON_VT();
        } catch (e) {
          this.$toast.error(e.data.message);
        } finally {
          this.loading(false);
        }
      },
    },

    grcDS_VT_Giao_Checked: function (value) {
      this.grcDS_VT_Giao_Checked = value;
      for (const i in this.grcDS_VT_Giao) {
        if (
          this.grcDS_VT_Giao_Checked.includes(this.grcDS_VT_Giao[i].TUVT_ID) &&
          this.grcDS_VT_Giao[i].LUONGCAP == 0
        ) {
          this.grcDS_VT_Giao[i].LUONGCAP = this.grcDS_VT_Giao[i].SL_TON;
        } else if (
          !this.grcDS_VT_Giao_Checked.includes(this.grcDS_VT_Giao[i].TUVT_ID)
        ) {
          this.grcDS_VT_Giao[i].LUONGCAP = 0;
        }
      }
      this.$refs.DSVatTuTrongKho.grid.refreshColumns();
    },
  },
  data() {
    return {
      
      templateXoaLSVT: xoaLSVTTemplate,
      gridVatTuSDTemplate: function () {
        return {
          template: {
            template: `
                    <div class="">
                        <input
                          type="checkbox"
                          class="uncheck"
                          v-model="parent.grcDS_VT_Giao_Checked"
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

      tempThuHoi: function () {
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
                this.parent.btnThuHoi_ButtonClick(data);
              },
            },
          },
        };
      },
      header: {
        title: "Cấp vật tư cho công trình",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Cấp vật tư cho công trình",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      grcDS_VT_Giao_Checked: [],
      editSettings: { allowEditing: true, mode: "Batch" },
      //Nhóm CT
      cboNhomCT: [],
      nhom_ct_value: "",
      nhom_ct_text: "",
      statusNhomCT: false,
      //Loại TBi
      cboChungLoai: [],
      chungloai: "",
      chungloai_text: "",
      statusChungLoaiTB: false,
      //Loại CT
      cboLoaiCT: [],
      loaiCT_value: "",
      //Từ ngày
      tuNgay: new Date(),
      showTuNgay: false,
      //Đến ngày
      denNgay: new Date(),
      //Trạng thái
      listTrangThai: [
        {
          value: 0,
          text: "Tất cả",
        },
        {
          value: 1,
          text: "Đã có vật tư",
        },
        {
          value: 2,
          text: "Chưa có vật tư",
        },
      ],
      cboTrangThai: 0,
      //Kho
      listKho: [],
      cboKho: "",
      //Ngày thực hiện
      ngayTH: new Date(),
      //Kiểu Thiết bị
      cboKieuTb: "",
      listCboKieuTb: [],
      //Mục đích cấp
      listMucDichCap: [],
      droplydocap: "",
      //Lý do cấp
      listLyDoCap: [],
      cboLyDoCap: "",
      //Tình trạng thiết bị CẤP VT
      listTinhTrangTBCap: [],
      cboTinhTrangCap: "",
      //Tình trạng
      listTinhTrang: [],
      cboKieuTrangBi: "",
      //Tên thiết bị
      listTenThietBi: [],
      cboVatTu: "",
      statusTenTB: false,
      text_cboVatTu: "",
      //Số lượng
      soluong_cu: 0,
      visible_VatTuThuHoi: true,
      //Tình trạnh thiết bị Thu Hồi
      listTinhTrangTBTH: [],
      cboTinhTrangTbi: "",
      //Ghi chú
      txtGhiChu: "",
      txtGhiChu_TH: "",
      //Serial
      txtSerialCu: "",
      //Thông tin công trình
      TTCT: {
        txtTenCT: "",
        txtDiaChiTC: "",
        txtGiaTri: "",
        txtSoHieu: "",
      },
      rdioCKb: 1,
      //Đóng - Mở
      expandsTTTTCT: true,
      expandsDSCT: true,
      expendsDSVTCCCT: true,
      //Mã CT
      txtMaCT: "",
      //Danh sách Công trình cấp VT
      grcDS_CongTrinh: [],
      grcDS_CongTrinh_temp: [],
      //Danh sách cấp vật tư cho công trình
      gridLichSuVatTu: [],
      // Status Thông tin vật tư thu hồi
      visible_TenTbi: false,
      visible_SoLuong: true,
      visible_SerialCu: false,
      visible_TinhTrangTbi: true,
      visible_GhiChu: true,
      xtraTabPage1: true,
      xtraTabPage2: false,
      //Danh sách vật tư trong kho
      grcDS_VT_Giao: [],
      //Lọc Kho
      ckbLocVT: true,
      //Danh sách vật tư thuê bao sử dụng
      gridVatTuSD: [],
      DSLichSuVatTu_checked: [],
      value_KiemTraDuLieu: true,
      value_ins_vattu_congtrinh_v2: "",
      tsbtnExcelMau_Visible: false,
      tsbtnNhapExcel_Visible: false,
      btnCapNhat_Visible: true,
      btnCapNhat_Enabled: true,
      btnNhapMoi_Visible: true,
      btnNhapMoi_Enabled: true,
      pnlThuHoi: false,
      btnHuyBo_Visible: false,
      value_kiemtra_vt_thuhoi: "",
      value_capnhat_sdvt_th: "",
      params: {
        vcongtrinh_id_luoi: 0,
        dt_congtrinh: [],
        KiemTraTB_SDVT: [],
        dt_tbidacap: [],
        ktsl: [],
        kt_sl: [],
      },
      local: {
        vsdvt_id: 0,
        value_KiemTraDuLieu_Th: false,
        loaitbi_id: 0,
        sl_goc: 0,
        vphieuvt_id: 0,
        seri: 0,
        matbi: 0,
      },
      listFileMau: [
        {
          MA_VT: "",
          SERIAL: "",
          GHICHU: "",
          TINHTRANGTBI_ID: "H: Hỏng, SDD: Sử dụng được",
        },
      ],
      FileLoi: [],
    };
  },
  methods: {
    ...mapActions("capvattuthuebao", [
      "getDMLyDoCap",
      "getDMTinhTrangTBCap",
      "getDMMucDichLyDo",
      "getDMTinhTrangVTThuHoi",
    ]),
    ...mapActions("capvattuct", [
      "getNhomCT",
      "getLoaiTBi",
      "getLoaiCT",
      "getKhofrmCVTCT",
      "getKieuThietBi",
      "getTinhTrang",
      "getDanhMucVT_DVT",
      "getDSCongTrinhCapVT",
      "getChungLoaiTB",
      "getds_LSVT",
      "getDSVatTuTrongKho_CT",
      "getDSVatTuSD_CT",
      "ins_vattu_congtrinh_v2",
      "kiemtra_vt_thuhoi",
      "getThongTinThueBaoTheoSerial",
      "KiemTraTB_SDVT",
      "getThietBi_DaCap",
      "capnhat_sdvt_th",
      "getSL_DD",
      "getSL_HT",
      "CAPNHAT_PHIEUVT_THUHOI",
      "kiemtra_serial",
      "kiemtra_serial_file",
      "kiemtra_serial_thuhoi",
      "LAY_ID_VATTU_THEO_MA_VT",
      "LAY_TT_VATTU_THEO_SERIAL",
      "thuHoiVatTuCongTrinh",
      "GiaoPhieuCongTrinh",
      "HuyPhieuCongTrinh",
      "XoaPhieuVatTu",
      "ThuHoiVatTu",
      "getTinhTrangThuHoi",
    ]),
    async fetch_CAPNHAT_PHIEUVT_THUHOI(_kieu) {
      this.loading(true);
      let data = {
        sdVtId: this.local.vsdvt_id,
        insert: 1,
        phieuVtId: 0,
        hdTbId: 0,
        baoHongId: 0,
        phieuId: 1,
        thueBaoId: this.params.vcongtrinh_id_luoi,
        donViId: this.$root.token.getDonViID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "",
        ipCn: "",
        tinhTrangTbiId: this.cboTinhTrangTbi,
        serial: "CUONG1094",
        ngayTh: moment(new Date()).format("DD/MM/yyyy HH:mm:ss"),
        vatTuId: this.cboVatTu,
        thietBiId: 0,
        soLuong: this.soluong_cu,
        lyDoCapId: this.droplydocap,
        lyDoCapTbiId: this.cboLyDoCap,
        trangBiId: this.cboKieuTrangBi,
        kieu: _kieu,
        ghiChu: this.txtGhiChu_TH,
        mdHdIms: "",
      };
      const response = await this.CAPNHAT_PHIEUVT_THUHOI(data);
      if (response.error_code === "BSS-00000000") {
        this.params.ktsl = response.data;
        this.$root.toastError("Thu hồi thành công");
      }
      this.loading(false);
    },

    async fetch_getSL_HT(_sdVtId) {
      this.loading(true);
      let data = {
        sdVtId: _sdVtId,
      };
      const response = await this.getSL_HT(data);
      if (response.error_code === "BSS-00000000") {
        this.local.kt_sl = response.data;
      }
      this.loading(false);
    },

    async fetch_getSL_DD(_sdVtId) {
      this.loading(true);
      let data = {
        sdVtId: _sdVtId,
      };
      const response = await this.getSL_DD(data);
      if (response.error_code === "BSS-00000000") {
        this.local.ktsl = response.data;
      }
      this.loading(false);
    },

    async fetch_capnhat_sdvt_th(
      _vatTuId,
      _xoaVt,
      _kieuPvt,
      _serial,
      _serialNew
    ) {
      this.loading(true);
      let data = {
        kieu: 2,
        sdVtId: this.params.vsdvt_id,
        vatTuId: _vatTuId,
        thueBaoId: this.params.vcongtrinh_id_luoi,
        xoaVt: _xoaVt,
        kieuPvt: _kieuPvt,
        lyDoCapTbiId: this.cboLyDoCap,
        tinhTrangTbiId: this.cboTinhTrangTbi,
        hdTbId: 0,
        baoHongId: 0,
        donViId: this.$root.token.getDonViID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        soLuong: this.soluong_cu,
        trangBiId: this.cboKieuTrangBi,
        phieuId: this.droplydocap,
        ngayTh: moment(new Date()).format("DD/MM/yyyy HH:mm:ss"),
        ghiChu: this.txtGhiChu,
        serial: _serial,
        serialNew: _serialNew,
        nguoiCn: this.$root.token.getUserName(),
        mayCn: "",
        ipCn: "",
        maHdIms: "test",
      };
      const response = await this.capnhat_sdvt_th(data);
      if (response.error_code === "BSS-00000000") {
        this.value_capnhat_sdvt_th = response.data;
      }
      this.loading(false);
    },

    async fetch_ThietBi_DaCap() {
      this.loading(true);
      let data = {
        serial: this.txtSerialCu,
      };
      const response = await this.getThietBi_DaCap(data);
      if (response.error_code === "BSS-00000000") {
        this.params.dt_tbidacap = response.data;
      }
      this.loading(false);
    },

    async fetch_KiemTraTB_SDVT() {
      this.loading(true);
      let data = {
        serial: this.txtSerialCu,
      };
      const response = await this.KiemTraTB_SDVT(data);
      if (response.error_code === "BSS-00000000") {
        this.params.KiemTraTB_SDVT = response.data;
      }
      this.loading(false);
    },

    async fetchThongTinThueBaoTheoSerial() {
      this.loading(true);
      let data = {
        serial: this.txtSerialCu,
      };
      const response = await this.getThongTinThueBaoTheoSerial(data);
      if (response.error_code === "BSS-00000000") {
        this.params.dt_congtrinh = response.data;
      }
      this.loading(false);
    },

    async fetch_kiemtra_vt_thuhoi() {
      this.loading(true);
      let data = {
        hdTbId: this.params.vcongtrinh_id_luoi,
        baoHongId: this.params.vcongtrinh_id_luoi,
        serial: this.txtSerialCu,
      };
      const response = await this.kiemtra_vt_thuhoi(data);
      console.log("response kiemtra_vt_thuhoi", response);
      if (response.error_code === "BSS-00000000") {
        this.value_kiemtra_vt_thuhoi = response.data[0];
      }
      this.loading(false);
    },
    async tsbtnGiaoPhieu_Click() {
      try {
        if (this.gridLichSuVatTu.length == 0) {
          this.$toast.error("Không có dữ liệu để giao phiếu");
          return;
        }
        if (this.DSLichSuVatTu_checked.length == 0) {
          this.$toast.error("Bạn chưa chọn danh sách vật tư cần giao đi");
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn giao danh sách vật tư đã chọn không?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true);
        let data = [];
        for (let e of this.DSLichSuVatTu_checked) {
          data.push({ PHIEUVT_ID: e.PHIEUVT_ID });
        }
        let ketQua = await this.GiaoPhieuCongTrinh({
          data: data,
          noiDung: "",
        });
        this.$toast.success("Đã giao vật tư thành công");
        await this.NAP_DS_CAPTHUVT();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async tsbtnTraLai_Click() {
      try {
        if (this.gridLichSuVatTu.length == 0) {
          this.$toast.error("Không có dữ liệu để hủy phiếu");
          return;
        }
        if (this.DSLichSuVatTu_checked.length == 0) {
          this.$toast.error("Bạn chưa chọn danh sách vật tư cần hủy");
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn hủy danh sách vật tư đã chọn không?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true);
        let data = [];
        for (let e of this.DSLichSuVatTu_checked) {
          data.push({ PHIEUVT_ID: e.PHIEUVT_ID });
        }
        let ketQua = await this.HuyPhieuCongTrinh({
          data: data,
          noiDung: "",
        });
        this.gridLichSuVatTu = []
        this.grcDS_VT_Giao = []
        this.$toast.success("Đã hủy giao vật tư thành công");
        await this.NAP_DS_CAPTHUVT();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetch_ins_vattu_congtrinh_v2() {
      let dsCongTrinh_check = [];
      for (let e of this.grcDS_VT_Giao) {
        if (this.grcDS_VT_Giao_Checked.includes(e.TUVT_ID)) {
          dsCongTrinh_check.push({
            TUVT_ID: e.TUVT_ID,
            THIETBI_ID: e.THIETBI_ID,
            SERIAL: e.SERIAL,
            MA_TBI: e.MA_TBI,
            LOHANG: e.LOHANG,
            VATTU_ID: e.VATTU_ID,
            LUONGCAP: e.LUONGCAP,
          });
        }
      }
      let data = {
        congTrinhId: this.params.vcongtrinh_id_luoi,
        mucDichId: this.droplydocap,
        lyDoCapId: this.cboLyDoCap,
        tinhTrangId: this.cboTinhTrangCap,
        trangBiId: this.cboKieuTrangBi,
        nhanVienId: this.$root.token.getNhanVienID(),
        donViId: this.$root.token.getDonViID(),
        nguoiCn: this.$root.token.getUserName(),
        ipCn: "10.35.87.36",
        mayCn: "TEST",
        ngayCap: moment(new Date()).format("DD/MM/yyyy HH:mm:ss"),
        ghiChu: this.txtGhiChu,
        dsCongTrinh: dsCongTrinh_check,
      };
      const response = await this.ins_vattu_congtrinh_v2(data);
      console.log("response ins_vattu_congtrinh_v2", response);
      if (response.error_code === "BSS-00000000") {
        this.value_ins_vattu_congtrinh_v2 = response.data;
      }
    },
    async fetchGetNhomCT() {
      const response = await this.getNhomCT();
      if (response.error_code === "BSS-00000000") {
        this.cboNhomCT = response.data;
      }
      if (this.cboNhomCT) {
        this.nhom_ct_value = this.cboNhomCT[0].mucDichId;
        this.nhom_ct_text = this.cboNhomCT[0].mucDich;
      }
    },
    async fetchChungLoaiTB() {
      const response = await this.getChungLoaiTB();
      if (response.error_code === "BSS-00000000") {
        this.cboChungLoai = response.data;
      }
      if (this.cboChungLoai) {
        this.chungloai = this.cboChungLoai[0].chungLoaiTbiId;
        this.chungloai_text = this.cboChungLoai[0].tenClTbi;
      }
    },
    async fetchGetLoaiCT() {
      const response = await this.getLoaiCT();
      if (response.error_code === "BSS-00000000") {
        this.cboLoaiCT = response.data;
      }
      if (this.cboLoaiCT) {
        this.loaiCT_value = this.cboLoaiCT[0].loaiCtId;
      }
    },
    async fetchKhofrmCVTCT() {
      let data = {
        nhanVienId: this.$root.token.getNhanVienID(),
      };
      const response = await this.getKhofrmCVTCT(data);
      if (response.error_code === "BSS-00000000") {
        this.listKho = response.data;
      }
      if (this.listKho) {
        this.cboKho = this.listKho[0].khoID;
      }
    },
    async fetchKieuThietBi() {
      const response = await this.getKieuThietBi();
      if (response.error_code === "BSS-00000000") {
        this.listCboKieuTb = response.data;
      }
      if (this.listCboKieuTb) {
        this.cboKieuTb = 1;
      }
    },
    async fetchDMLyDoCap() {
      let data = { nhomMdIdArr: [7, 8] };
      const response = await this.getDMLyDoCap(data);
      if (response.error_code === "BSS-00000000") {
        this.listLyDoCap = response.data;
      }
      if (this.listLyDoCap.length > 0) {
        this.cboLyDoCap = this.listLyDoCap[0].mucDichId;
      }
    },
    async fetchMucDichCap() {
      let data = {
        nhomMdId: 17,
      };
      const response = await this.getDMMucDichLyDo(data);
      if (response.error_code === "BSS-00000000") {
        this.listMucDichCap = response.data;
      }
      if (this.listMucDichCap.length > 0) {
        this.droplydocap = this.listMucDichCap[0].MUCDICH_ID;
      }
    },
    async fetchDMTinhTrangTBCap() {
      const response = await this.getDMTinhTrangTBCap();
      if (response.error_code === "BSS-00000000") {
        this.listTinhTrangTBCap = response.data;
      }
      if (this.listTinhTrangTBCap.length > 0) {
        this.cboTinhTrangCap = this.listTinhTrangTBCap[0].tinhTrangTBiId;
      }
    },
    async fetchDMTinhTrangVTThuHoi() {
      this.listTinhTrangTBTH = await this.getTinhTrangThuHoi();
    },
    async fetchTinhTrang() {
      const response = await this.getTinhTrang();
      if (response.error_code === "BSS-00000000") {
        this.listTinhTrang = response.data;
        this.cboKieuTrangBi = 4; // VIENTHONG_TRANGBI
      }
    },
    async fetchDanhMucVT_DVT() {
      const response = await this.getDanhMucVT_DVT();
      if (response.error_code === "BSS-00000000") {
        this.listTenThietBi = response.data;
      }
    },
    hideCboTenTB() {
      this.statusTenTB = false;
    },
    
    gridTenTB_selectedRowChanged(data) {
      this.cboVatTu = data.vatTuId;
      this.text_cboVatTu = data.tenVatTu;
    },
    async fetchDSCongTrinhCapVT() {
      let data = {
        ttPhId: 1,
        maCt:
          this.txtMaCT == "" || this.txtMaCT == undefined ? 0 : this.txtMaCT,
        nhomCtId: this.nhom_ct_value,
        loaiCtId: this.loaiCT_value,
        loaiTbId: this.chungloai,
        trangThaiId: this.cboTrangThai,
        timNgay: this.showTuNgay ? 1 : 0,
        tuNgay: moment(this.tuNgay).format("DD/MM/yyyy").toString(),
        denNgay: moment(this.denNgay).format("DD/MM/yyyy").toString(),
        statusVatTu: this.rdioCKb,
        nhanVienId: this.$root.token.getNhanVienID(),
      };
      this.grcDS_CongTrinh = await this.getDSCongTrinhCapVT(data);
    },
    async LayTT() {
      try {
        this.loading(true);
        if (this.rdioCKb != 0) {
          await this.fetchDSCongTrinhCapVT();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async grcDS_CongTrinh_selectedRowChanged(data) {
      console.log("DS Công trình", data);
      if (data) {
        this.params.vcongtrinh_id_luoi = data.CONGTRINH_ID;
        await this.NAP_DS_CAPTHUVT();
        // this.grcDS_CongTrinh_temp.push(data);
        await this.NAP_DS_TON_VT();
        await this.fetchDSVatTuSD_CT();
        this.TTCT.txtTenCT = data.TEN_CT;
        this.TTCT.txtDiaChiTC = data.DIACHI_TC;
        this.TTCT.txtGiaTri = data.GT_DUTOAN;
        this.TTCT.txtSoHieu = data.SOHIEU_CT;
      }
    },
    async runFistTime() {
      await this.fetchGetNhomCT();
      await this.fetchChungLoaiTB();
      await this.fetchGetLoaiCT();
      await this.fetchKhofrmCVTCT();
      await this.fetchKieuThietBi();
      await this.fetchDMLyDoCap();
      await this.fetchMucDichCap();
      await this.fetchDMTinhTrangTBCap();
      await this.fetchTinhTrang();
      await this.fetchDanhMucVT_DVT();
      await this.fetchDMTinhTrangVTThuHoi();
    },
    hideCbxLoaiTbi: function () {
      this.statusChungLoaiTB = false;
    },
    gridLoaiTbi_selectedRowChanged(data) {
      this.chungloai = data.chungLoaiTbiId;
      this.chungloai_text = data.tenClTbi;
    },
    hideCbxNhomCT: function () {
      this.statusNhomCT = false;
    },
    gridNhomCT_selectedRowChanged(data) {
      this.nhom_ct_value = data.mucDichId;
      this.nhom_ct_text = data.mucDich;
    },
    async NAP_DS_CAPTHUVT() {
      this.loading(true);
      let data = {
        congTrinhId: this.params.vcongtrinh_id_luoi,
        kieuTbiId: this.cboKieuTb,
        status: this.rdioCKb,
        nhanVienId: this.$root.token.getNhanVienID(),
      };
      const response = await this.getds_LSVT(data);
      if (response.error_code === "BSS-00000000") {
        this.gridLichSuVatTu = response.data;
      }
      this.loading(false);
    },
    DSLichSuVatTu_selectedRowChanged(data) {
      console.log("gridLichSuVatTu = ", data);
    },
    gridLichSuVatTu_checkbox(data) {
      this.DSLichSuVatTu_checked = data;
    },
    async fetchDSVatTuTrongKho_CT(_khoId) {

      let data = {
        khoId: _khoId,
        congTrinhId: this.ckbLocVT ? this.params.vcongtrinh_id_luoi : 0,
      };
      const response = await this.getDSVatTuTrongKho_CT(data);
      console.log("response DS Vật tư trong Kho", response);
      if (response.error_code === "BSS-00000000") {
        let grcDS_VT_Giao = response.data;
        grcDS_VT_Giao.forEach(e=>{
          e.SL_TON = e.SL_TON == null || e.SL_TON == "" ? 0 : parseFloat(e.SL_TON)
          e.LUONGCAP = e.LUONGCAP == null || e.LUONGCAP == "" ? 0 : parseFloat(e.LUONGCAP)
        })
        this.grcDS_VT_Giao = grcDS_VT_Giao
      }
    },
    grcDS_VT_Giao_selectedRowChanged(data) {
      console.log("Records grcDS_VT_Giao = ", data);
    },
    async NAP_DS_TON_VT() {   
      if (this.cboKho == null || this.cboKho == "") {
        this.$root.toastWarning("Bạn chưa được gán kho");
        return;
      }
      if (this.params.vcongtrinh_id_luoi == 0) {
        return;
      }
      await this.fetchDSVatTuTrongKho_CT(this.cboKho);      
    },
    async fetchDSVatTuSD_CT() {
      this.loading(true);
      let data = {
        congTrinhId: this.params.vcongtrinh_id_luoi,
      };
      const response = await this.getDSVatTuSD_CT(data);
      console.log("response DS Vật tư thuê bao trong Kho = ", response);
      if (response.error_code === "BSS-00000000") {
        this.gridVatTuSD = response.data;
      }
      this.loading(false);
    },
    btnThuHoi_ButtonClick(data) {
      console.log(data , " = data")
      this.NHAPMOI()
      this.local.sl_goc = 0;
      this.local.seri = data.SERIAL;
      this.local.matbi = data.MA_TBI;
      this.btnNhapMoi_Enabled = false;
      if ((this.local.seri == null || this.local.seri == "") && (this.local.matbi == null || this.local.matbi == "")) {
        this.txtSerialCu = "";
        this.visible_SoLuong = false;
        this.visible_TenTbi = true;
        this.visible_SerialCu = true;
      } else if (!(this.local.seri == null || this.local.seri == "") && (this.local.matbi == null || this.local.matbi == "")) {
        this.txtSerialCu = data.SERIAL
        this.visible_SoLuong = true;
        this.visible_TenTbi = true;
        this.visible_SerialCu = true;
      } else if ((this.local.seri == null || this.local.seri == "") && !(this.local.matbi == null || this.local.matbi == "")) {
        this.txtSerialCu = data.MA_TBI
        this.visible_SoLuong = false;
        this.visible_TenTbi = true;
        this.visible_SerialCu = false;
      }
      
      this.cboVatTu = data.VATTU_ID;
      let TTVatTu = this.listTenThietBi.filter(a=> a.vatTuId == data.VATTU_ID)
     
      this.text_cboVatTu = TTVatTu.length == 0 ? "" : TTVatTu[0].tenVatTu;     
      let a = data.SOLUONG == null || data.SOLUONG == "" ? 0 : parseFloat(data.SOLUONG);
      this.soluong_cu = a;
      this.local.sl_goc = a;
      this.local.vsdvt_id = data.SDVT_ID;
      this.local.vphieuvt_id = data.PHIEUVT_ID;
    },
    async btnXoaLSVT_ButtonClick(data) {
      try{
      
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có thực sự muốn xóa phiếu vật tư đang chọn không?",
            {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true)
        if (this.cboKieuTb == "1") {
          let ketQua = await this.XoaPhieuVatTu({
            congTrinhId: data.CONGTRINH_ID,
            phieuVTId: data.PHIEUVT_ID,
            tuVTId: data.TUVT_ID,
            soLuong: data.SOLUONG,
          });
          this.$toast.success("Xóa phiếu vật tư thành công");     
        } else if (this.cboKieuTb == "3") {
          let ketQua = await this.ThuHoiVatTu({
            serial: "",
            sdVtId: data.SDVT_ID,
            kieu: 2,
            phieuVtId: data.PHIEUVT_ID,
            nguoiCn: this.$auth.getUserName(),
            mayCn: "test",
            ipCn: "test",
          });
          this.$toast.success("Xóa phiếu vật tư thành công");
        }
        await this.NAP_DS_CAPTHUVT();
        await this.NAP_DS_TON_VT();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnBC_BB29_Click() {
      if (
        this.params.vcongtrinh_id_luoi == 0 ||
        this.params.vcongtrinh_id_luoi == null
      ) {
        this.$toast.error("Bạn phải chọn 1 công trình để in");
        return;
      }
      this.loading(true);
      const baseUrl = process.env.API;
      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-cong-trinh?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          congTrinhId: this.params.vcongtrinh_id_luoi,
          maBc: "CTBBHT",
          loai: 4,
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async btnBC_BB30_Click() {
      if (
        this.params.vcongtrinh_id_luoi == 0 ||
        this.params.vcongtrinh_id_luoi == null
      ) {
        this.$toast.error("Bạn phải chọn 1 công trình để in");
        return;
      }
      this.loading(true);
      const baseUrl = process.env.API;
      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-cong-trinh?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          congTrinhId: this.params.vcongtrinh_id_luoi,
          maBc: "CTBBSC",
          loai: 5,
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async b32BienBanPhatSinhToolStripMenuItem_Click() {
      if (
        this.params.vcongtrinh_id_luoi == 0 ||
        this.params.vcongtrinh_id_luoi == null
      ) {
        this.$toast.error("Bạn phải chọn 1 công trình để in");
        return;
      }
      this.loading(true);
      const baseUrl = process.env.API;
      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-cong-trinh?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          congTrinhId: this.params.vcongtrinh_id_luoi,
          maBc: "CTBBPS",
          loai: 6,
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    async b33HoSoHoanCongToolStripMenuItem_Click() {
      if (
        this.params.vcongtrinh_id_luoi == 0 ||
        this.params.vcongtrinh_id_luoi == null
      ) {
        this.$toast.error("Bạn phải chọn 1 công trình để in");
        return;
      }
      this.loading(true);
      const baseUrl = process.env.API;
      await this.axios({
        url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-cong-trinh?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          congTrinhId: this.params.vcongtrinh_id_luoi,
          maBc: "CTHSHC",
          loai: 7,
        },
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false);
    },
    removeElementInArray(array,element){
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array
    },
    async editGridVatTuDaCap(arg) {
      let f = this.$refs.DSVatTuTrongKho;
      let changes = f.getBatchChanges();
      if (changes.changedRecords.length == 0) {
        return;
      }
   
      if (arg.columnName == "LUONGCAP") {
        if (
          parseFloat(changes.changedRecords[0].LUONGCAP) > parseFloat(changes.changedRecords[0].SL_TON)
        ) {
          if(!this.grcDS_VT_Giao_Checked.includes(changes.changedRecords[0].TUVT_ID)){
            this.grcDS_VT_Giao_Checked.push(changes.changedRecords[0].TUVT_ID)
          }
          changes.changedRecords[0].LUONGCAP = parseFloat(changes.changedRecords[0].SL_TON)
          this.$toast.error( "Số lượng cấp không được lớn hơn số lượng hiện tại!"  );
        } else if (parseFloat(changes.changedRecords[0].LUONGCAP) <= 0) {          
          changes.changedRecords[0].LUONGCAP = 0;
          this.grcDS_VT_Giao_Checked = this.removeElementInArray(
            this.grcDS_VT_Giao_Checked,
            changes.changedRecords[0].TUVT_ID
          );          
        }else {         
          if (!this.grcDS_VT_Giao_Checked.includes( changes.changedRecords[0].TUVT_ID)) {
            this.grcDS_VT_Giao_Checked.push(changes.changedRecords[0].TUVT_ID);            
          }          
        }
      }
      f.batchUpdate(changes)
      this.$refs.DSVatTuTrongKho.grid.refreshColumns()
      console.log("this.grcDS_VT_Giao = ", this.grcDS_VT_Giao);
    },
    async btnCapNhat_Click() {
      try {
        if (this.cboKieuTb.toString() == "1") {
          this.INS_VATTU_CT();
        } else if (this.cboKieuTb.toString() == "3") {
          // this.THUHOI_VT_CT();
          this.loading(true);
          let congtrinh_id =
            this.params.vcongtrinh_id_luoi == null ||
            this.params.vcongtrinh_id_luoi == ""
              ? 0
              : this.params.vcongtrinh_id_luoi;
          let ketQua = await this.thuHoiVatTuCongTrinh({
            congtrinh_id: congtrinh_id,
            sdvt_id: this.local.vsdvt_id,
            vattu_id: this.cboVatTu,
            serial: this.txtSerialCu,
            soluong_th: this.soluong_cu,
            lydocaptbi_id: this.cboLyDoCap,
            tinhtrangtbi_id: this.cboTinhTrangTbi,
            trangbi_id: this.cboKieuTrangBi,
            ngay_th: moment(this.ngayTH).format("DD/MM/yyyy HH:mm:ss"),
            mucdich_cap: this.droplydocap,
            ghichu: this.txtGhiChu,
          });
          this.$toast.success("Thu hồi thành công!");
          this.NHAPMOI();
          await this.NAP_DS_CAPTHUVT();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    KiemTraDuLieu() {
      if (
        this.cboTinhTrangCap == null ||
        this.cboTinhTrangCap.toString() == ""
      ) {
        this.$root.toastError("Bạn chưa chọn tình trạng thiết bị");
        return (this.value_KiemTraDuLieu = false);
      }

      if (this.params.vcongtrinh_id_luoi == 0) {
        this.$root.toastError("Bạn chưa chọn công trình");
        return (this.value_KiemTraDuLieu = false);
      }

      if (this.rdioCKb.toString() == "2") {
        this.$root.toastError("Bạn không được cấp vật tư ở trạng thái này");
        return (this.value_KiemTraDuLieu = false);
      }

      if (this.grcDS_VT_Giao.length == 0) {
        //&& sodaymoi.EditValue.toString() == "0")
        this.$root.toastError("Không có vật tư để cấp");
        return (this.value_KiemTraDuLieu = false);
      }

      if (this.grcDS_VT_Giao_Checked.length < 0) {
        this.$root.toastError("Bạn chưa chọn vật tư để cấp");
        return (this.value_KiemTraDuLieu = false);
      }

      return (this.value_KiemTraDuLieu = true);
    },
    async INS_VATTU_CT() {
      try {
        await this.KiemTraDuLieu();
        if (!this.value_KiemTraDuLieu) {
          return;
        }
        if (this.grcDS_VT_Giao.length > 0) {
          let isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có chắc chắn cập nhật vật tư? ", {
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
          this.loading(true);
          await this.fetch_ins_vattu_congtrinh_v2();
          if (this.value_ins_vattu_congtrinh_v2 == null) {
            this.$root.toastError("Không lấy được kết quả trả về");
            return;
          }
          if (this.value_ins_vattu_congtrinh_v2.toString() == "1") {
            this.$root.toastSuccess("Đã cấp thành công vật tư cho công trình");
          } else {
            this.$root.toastError(
              "Lỗi khi cấp " + this.value_ins_vattu_congtrinh_v2
            );
            return;
          }
          await this.NAP_DS_TON_VT();
          await this.NAP_DS_CAPTHUVT();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    KiemTraDuLieu_Th() {
      if (this.params.vcongtrinh_id_luoi == 0) {
        this.$root.toastError("Bạn chưa chọn công trình");
        return (this.local.value_KiemTraDuLieu_Th = false);
      }
      if (this.cboVatTu == "") {
        this.$root.toastError("Bạn phải chọn vật tư");
        return (this.local.value_KiemTraDuLieu_Th = false);
      }
      if (this.soluong_cu == 0) {
        this.$root.toastError("Bạn phải nhập số lượng");
        return (this.local.value_KiemTraDuLieu_Th = false);
      }
      if (this.cboTinhTrangTbi == null || this.cboTinhTrangTbi == "") {
        this.$root.toastError("Bạn chưa chọn tình trạng thiết bị");
        return (this.local.value_KiemTraDuLieu_Th = false);
      }

      this.local.loaitbi_id = 0;
      var r = [];
      this.listTenThietBi.forEach((el) => {
        if (this.cboVatTu == el.vatTuId) {
          r.push(el);
        }
      });
      if (r.length > 0) {
        this.local.loaitbi_id = parseInt(r[0].loaiThietBiId);
        if (r[0].loaiThietBiId.toString().trim() == "2") {
          if (this.txtGhiChu_TH.trim() == "") {
            this.$root.toastError(
              "Loại vật tư này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị"
            );
            this.$refs.refTxtSerialCu.focus();
            return (this.local.value_KiemTraDuLieu_Th = false);
          }
          if (this.txtGhiChu_TH.trim() != "") {
            if (this.soluong_cu != 1) {
              this.$root.toastError(
                "Vật tư này có seri, số lượng phải nhập = 1"
              );
              this.$refs.refSoluong_cu.focus();
              return (this.local.value_KiemTraDuLieu_Th = false);
            }
            // String serialPatterns = check_serial;
            // if (
            //   check_serial != "0" &&
            //   !Regex.IsMatch(this.txtGhiChu_TH, serialPatterns)
            // ) {
            //   this.$root.toastError(
            //     "Serial có ký tự không hợp lệ. Các ký tự cho phép [A-Z,0-9]"
            //   );

            //   return (this.local.value_KiemTraDuLieu_Th = false);
            // }
          }
        } else if (r[0].loaiThietBiId.toString().trim() == "3") {
          return (this.local.value_KiemTraDuLieu_Th = true);
        } else {
          if (this.txtGhiChu_TH.trim() != "") {
            this.$root.toastError(
              "Loại vật tư này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial"
            );
            this.$refs.refTxtSerialCu.focus();
            this.$refs.refTxtSerialCu.select();
            return (this.local.value_KiemTraDuLieu_Th = false);
          }
        }
      }

      return (this.local.value_KiemTraDuLieu_Th = true);
    },
    async THUHOI_VT_CT() {
      // try
      // {

      let vseria_goc = this.txtSerialCu;
      let vserial = "";

      let vvattu_id = 0;
      let kieu_pvt = 0;
      let xoavt = 0;

      if (!this.btnNhapMoi_Enabled) {
        await this.KiemTraDuLieu_Th();
        if (!this.local.value_KiemTraDuLieu_Th) {
          // Đoạn này kiểm tra các điều kiện cần thiết
          return;
        }
        if (this.local.loaitbi_id == 2) {
          if (this.txtGhiChu_TH != "") {
            await this.fetch_kiemtra_vt_thuhoi();
            if (this.value_kiemtra_vt_thuhoi.toString() != "1") {
              this.$root.toastError(this.value_kiemtra_vt_thuhoi.toString());
              return;
            }
            await this.fetchThongTinThueBaoTheoSerial();
            await this.fetch_KiemTraTB_SDVT();
            await this.fetch_ThietBi_DaCap();
            if (this.params.dt_congtrinh.length > 0) {
              // Kiểm tra xem đã từng có ai thu hồi serial này chưa
              this.$root.toastError(
                "Serial " +
                  this.txtSerialCu +
                  " đã được cấp\thu hồi vào  ngày " +
                  this.params.dt_tbidacap[0].NgayCap +
                  " bởi user " +
                  this.params.dt_tbidacap[0].NguoiCn +
                  ""
              );
              return;
            }
            if (
              this.params.KiemTraTB_SDVT.length > 1 ||
              this.params.dt_congtrinh.length > 1
            ) {
              // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
              let tong =
                this.params.dt_congtrinh.length +
                this.params.KiemTraTB_SDVT.length;
              this.$root.toastError(
                "Bạn hãy kiểm tra lại " +
                  this.txtSerialCu +
                  ". Hiện đang có " +
                  tong +
                  " thuê bao/công trình đang sử dụng."
              );
              return;
            }
            if (this.params.KiemTraTB_SDVT.length == 1) {
              // Nếu có 1 thuê bao thì kiểm tra tiếp
              this.$root.toastError(
                "Serial " +
                  this.txtSerialCu +
                  " đã được sử dụng bởi thuê bao " +
                  this.params.KiemTraTB_SDVT[0].maThueBao +
                  ". Bạn không có quyền được thu hồi"
              );
              return;
            }
            if (this.params.dt_congtrinh.length == 1) {
              if (
                parseInt(this.params.dt_congtrinh[0].congTrinhId.toString()) !=
                this.params.vcongtrinh_id_luoi
              ) {
                this.$root.toastError(
                  "Serial " +
                    this.txtGhiChu_TH +
                    " đã được sử dụng bởi công trình " +
                    this.params.dt_congtrinh[0].maCt +
                    ". Bạn không có quyền được thu hồi"
                );
                return;
              }
              this.local.vsdvt_id = parseInt(
                this.params.dt_congtrinh[0].sdVtId.toString()
              );
              vserial = this.txtGhiChu_TH.toUpperCase();
              kieu_pvt = 4; //Không đổi
            }
            if (
              this.params.KiemTraTB_SDVT.length <= 0 &&
              this.params.dt_congtrinh.length <= 0
            ) {
              kieu_pvt = 3; //Khong tim thay cua thue bao nao
              this.local.vsdvt_id = 0;
              vvattu_id = parseInt(cboVatTu.EditValue.toString());
              vserial = this.txtGhiChu_TH.toUpperCase();
            }
            await this.fetch_capnhat_sdvt_th(
              vvattu_id,
              xoavt,
              kieu_pvt,
              vseria_goc,
              vserial
            );
            if (this.value_capnhat_sdvt_th != "1") this.$root.toastError(s);
            else {
              this.NHAPMOI();
              await this.NAP_DS_CAPTHUVT();
            }
          }
        }
        if (this.local.loaitbi_id == 3) {
          //Nếu là cuộn cáp
          if (this.local.vsdvt_id > 0) {
            await this.fetch_getSL_DD(this.local.vsdvt_id);
            if (this.params.ktsl > 0) {
              if (this.params.ktsl.toString() != "0") {
                let sl_treo = parseFloat(this.params.ktsl.toString());
                if (
                  sl_goc - sl_treo <= parseFloat(soluong_cu.EditValue) ||
                  sl_goc - sl_treo <= 0
                ) {
                  this.$root.toastError(
                    "Hiện số lượng cấp thu hồi của thuê chưa QT là " +
                      sl_treo +
                      "/" +
                      sl_goc +
                      ". Bạn hãy kiểm tra lại số lượng thu hồi"
                  );
                  return;
                }
              } else {
                await this.fetch_getSL_HT(this.local.vsdvt_id);
                if (
                  parseFloat(kt_sl.Rows[0][0].toString()) <
                  parseFloat(soluong_cu.EditValue)
                ) {
                  this.$root.toastError(
                    "Bạn thu hồi quá số lượng hiện tại của thuê bao"
                  );
                  return;
                }
              }
              // Insert PVT
            }
            if (!this.btnNhapMoi_Enabled) {
              await this.fetch_CAPNHAT_PHIEUVT_THUHOI(1);
              this.NHAPMOI();
              await this.NAP_DS_CAPTHUVT();
            }
          } // Nhap cap khong co trong kho
          else {
            if (!this.btnNhapMoi_Enabled) {
              await this.fetch_CAPNHAT_PHIEUVT_THUHOI(3);
              this.NHAPMOI();
              await this.NAP_DS_CAPTHUVT();
            }
          }
        }
        if (this.local.loaitbi_id != 3 && this.local.loaitbi_id != 2) {
          if (this.local.vsdvt_id > 0) {
            await this.fetch_getSL_DD(this.local.vsdvt_id);
            if (this.params.ktsl > 0) {
              if (this.params.ktsl.toString() != "0") {
                let sl_treo = parseFloat(this.params.ktsl.toString());
                if (
                  sl_goc - sl_treo <= parseFloat(soluong_cu.EditValue) ||
                  sl_goc - sl_treo <= 0
                ) {
                  this.$root.toastError(
                    "Hiện số lượng cấp thu hồi của thuê chưa QT là " +
                      sl_treo +
                      "/" +
                      sl_goc +
                      ". Bạn hãy kiểm tra lại số lượng thu hồi"
                  );
                  return;
                }
              }
              // Insert PVT
            }
            if (!this.btnNhapMoi_Enabled) {
              await this.fetch_CAPNHAT_PHIEUVT_THUHOI(1);
              this.NHAPMOI();
              await this.NAP_DS_CAPTHUVT();
            }
          } else {
            if (!this.btnNhapMoi_Enabled) {
              await this.fetch_CAPNHAT_PHIEUVT_THUHOI(1);
              this.NHAPMOI();
              await this.NAP_DS_CAPTHUVT();
            }
          }
        }
      }
      // }
      // catch (Exception exx)
      // {
      //     this.$root.toastError("Có lỗi khi thu hồi "+exx);
      // }
    },
    HUYBO() {
      this.pnlThuHoi = false;
      this.Clear();
      this.btnCapNhat_Visible = false;
      this.btnCapNhat_Enabled = true;
      this.btnNhapMoi_Visible = true;
      this.btnNhapMoi_Enabled = true;
      this.btnHuyBo_Visible = false;
      this.txtGhiChu_TH = "";
      this.visible_SoLuong = false;
      this.visible_TenTbi = false;
      this.visible_GhiChu = false;
    },
    Clear() {
      this.text_cboVatTu = "";
      this.cboVatTu = "";
      this.cboTinhTrangTbi = "";
      this.txtGhiChu_TH = "";
      this.soluong_cu = 0;
      this.txtGhiChu_TH = "";
      this.txtGhiChu = "";
      this.local.vsdvt_id = 0;
    },
    NHAPMOI() {
      this.pnlThuHoi = true;
      this.Clear();
      this.btnCapNhat_Visible = true;
      this.btnCapNhat_Enabled = true;
      this.btnNhapMoi_Visible = true;
      this.btnNhapMoi_Enabled = false;
      this.btnHuyBo_Visible = true;
      this.txtSerialCu = "";
      this.visible_SoLuong = false;
      this.visible_TenTbi = false;
      this.visible_SerialCu = false;
    },
    XuatFileMau() {
      console.log("File mẫu", this.listFileMau.length);
      if (this.listFileMau.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.listFileMau);
        var wb = xlsx.utils.book_new();
        xlsx.utils.book_append_sheet(wb, dsach, "ThietBi");
        xlsx.writeFile(wb, "fileNhapTbi.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async NhapExcel(e) {
      try {
        if (this.cboKieuTb == 1) {
          this.$root.toastError("Chỉ có thể đọc từ excel vào nếu nhập thu hồi");
          return;
        }
        if (this.params.vcongtrinh_id_luoi == 0) {
          this.$root.toastError("Bạn chưa chọn công trình để có thể thu hồi");
          return;
        }

        const files = e.target.files;
        if (!files.length) {
          return;
        } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return this.$root.toastWarning("File excel không đúng định dạng!");
        }
        await this.DocFile(files);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    DocFile(file) {
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const dt = ev.target.result;
          const workbook = xlsx.read(dt, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];

          for (var i = 0; i < ws.length; i++) {
            excellist.push({
              MA_VT: ws[i].MA_VT,
              SERIAL: ws[i].SERIAL ? ws[i].SERIAL : null,
              GHICHU: ws[i].GHICHU,
              TINHTRANGTBI_ID: ws[i].TINHTRANGTBI_ID,
            });
          }
          console.log(excellist);

          excellist.forEach((element) => {
            if (element.SERIAL != null) {
              element.SERIAL = element.SERIAL.toUpperCase();
            }
          });

          //Kiểm tra dữ liệu
          let tmp = excellist.filter((e) => {
            return e.SERIAL == null || e.SERIAL == "";
          });
          if (tmp.length > 0) {
            this.$root.toastError("Bạn phải nhập serial");
            return;
          }
          tmp = excellist.filter((e) => {
            return e.TINHTRANGTBI_ID != "H" && e.TINHTRANGTBI_ID != "SDD";
          });
          if (tmp.length > 0) {
            this.$root.toastError("Bạn chưa nhập đúng cấu trúc tình trạng");
            return;
          }

          let data = {
            data: JSON.stringify(excellist),
          };

          let t_kt = await this.kiemtra_serial(data);
          if (t_kt.data != "1") {
            this.$root.toastError(t_kt.data);
            return;
          }

          let kt = await this.kiemtra_serial_file(data);
          if (kt.data.length > 0) {
            this.$root.toastError(
              `Có ${kt.data.length} bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!`
            );
            return;
          }

          let check_serial = {
            id: this.params.vcongtrinh_id_luoi,
            dt: {
              data: JSON.stringify(excellist),
            },
          };
          kt = await this.kiemtra_serial_thuhoi(check_serial);
          if (kt.data.length > 0) {
            this.$root.toastError(
              `Có ${kt.data.length} bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!`
            );
            return;
          }

          this.NHAPKHO(excellist);
        } catch (error) {
          return alert("Read failure! " + error);
        }
      };
      fileReader.readAsBinaryString(file[0]);
    },
    XUATFILELOI() {
      if (this.FileLoi.length > 0) {
        this.$bvModal
          .msgBoxConfirm(
            "Có danh sách serial lỗi không thể thu hồi bạn có muốn lấy không?",
            {
              size: "sm",
              okTitle: "Có",
              cancelTitle: "Không",
            }
          )
          .then((rs) => {
            if (!rs) return;
            var dsach = xlsx.utils.json_to_sheet(this.FileLoi);
            var wb = xlsx.utils.book_new();
            xlsx.utils.book_append_sheet(wb, dsach, "ThietBi");
            xlsx.writeFile(wb, "fileSaiDuLieu.xlsx");
          });
      }
      this.NHAPMOI();
      this.NAP_DS_CAPTHUVT();
    },
    NHAPKHO(json) {
      try {
        this.loading(true);
        this.FileLoi = [];
        json.forEach(async (element) => {
          let vattu_id_ = 0;
          let vsdvt_id_ = 0;
          let vserial_ = "";
          let ghichu = "";
          let kieu_pvt_ = 0;
          let sl = 1;
          let tinhtrangtbi_id = 0;
          let dtkiemtra = await this.LAY_TT_VATTU_THEO_SERIAL(element.SERIAL);
          if (dtkiemtra.data.length > 0) {
            vattu_id_ = dtkiemtra.data.VATTU_ID;
          } else {
            let vattu = await this.LAY_ID_VATTU_THEO_MA_VT(element.MA_VT);
            if (vattu.data.length > 0) {
              vattu_id_ = vattu.data[0];
            } else {
              this.FileLoi.push({
                SERIAL: element.SERIAL,
                LOI: `Không tìm thấy mã vật tư`,
              });
            }
          }
          if (element.TINHTRANGTBI_ID == "H") {
            tinhtrangtbi_id = 5;
          } else {
            tinhtrangtbi_id = 6;
          }
          ghichu = element.GHICHU;

          //Thu hồi Serial
          let data_thuhoi = {
            hdTbId: this.params.vcongtrinh_id_luoi,
            baoHongId: this.params.vcongtrinh_id_luoi,
            serial: element.SERIAL,
          };
          let checklogic = await this.kiemtra_vt_thuhoi(data_thuhoi);
          if (checklogic.data != "1") {
            this.FileLoi.push({
              SERIAL: element.SERIAL,
              LOI: checklogic.data,
            });
          }
          let data_serial = {
            serial: element.SERIAL,
          };
          let dt_congtrinh = await this.getThongTinThueBaoTheoSerial(
            data_serial
          );
          let dzt = await this.KiemTraTB_SDVT(data_serial);
          let dt_tbidacap = await this.getThietBi_DaCap(data_serial);

          // Kiểm tra xem đã từng có ai thu hồi serial này chưa
          if (dt_tbidacap.data.length > 0) {
            this.FileLoi.push({
              SERIAL: element.SERIAL,
              LOI: `Serial ${element.SERIAL} đã được cấp thu hồi vào ngày ${dt_tbidacap.data[0].NgayCap} bởi user ${dt_tbidacap.data[0].NguoiCn}`,
            });
          }

          // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
          if (dzt.data.length > 1 || dt_congtrinh.data.length > 1) {
            let tongz = dzt.data.length + dt_congtrinh.data.length;
            this.FileLoi.push({
              SERIAL: element.SERIAL,
              LOI: `Bạn hãy kiểm tra lại ${element.SERIAL}. Hiện đang có ${tongz} thuê bao/công trình đang sử dụng.`,
            });
          }

          // Nếu có 1 thuê bao thì kiểm tra tiếp
          if (dzt.data.length == 1) {
            this.FileLoi.push({
              SERIAL: element.SERIAL,
              LOI: `Serial ${element.SERIAL} đã được sử dụng bởi thuê bao ${dzt.data[0].maThueBao}. Bạn không có quyền được thu hồi`,
            });
          }

          if (dt_congtrinh.data.length == 1) {
            if (
              dt_congtrinh.data[0].CONGTRINH_ID !=
              this.params.vcongtrinh_id_luoi
            ) {
              this.FileLoi.push({
                SERIAL: element.SERIAL,
                LOI: `Serial ${element.SERIAL} đã được sử dụng bởi công trình ${dt_congtrinh.data[0].maCt}. Bạn không có quyền được thu hồi`,
              });
            }
            vsdvt_id_ = dt_congtrinh.data[0].sdVtId;
            vserial_ = element.SERIAL;
            kieu_pvt_ = 4; //Không đổi
          }

          if (dzt.data.length <= 0 && dt_congtrinh.data.length <= 0) {
            kieu_pvt_ = 3; //Khong tim thay cua thue bao nao
            vsdvt_id_ = 0;
            vserial_ = element.SERIAL;
          }
          console.log(this.listTinhTrangTBTH);
          let data_capnhat = {
            kieu: 2,
            sdVtId: vsdvt_id_,
            vatTuId: vattu_id_,
            thueBaoId: this.params.vcongtrinh_id_luoi,
            xoaVt: 0,
            kieuPvt: kieu_pvt_,
            lyDoCapTbiId: this.cboLyDoCap,
            tinhTrangTbiId: tinhtrangtbi_id,
            hdTbId: 0,
            baoHongId: 0,
            donViId: this.$root.token.getDonViID(),
            nhanVienId: this.$root.token.getNhanVienID(),
            soLuong: 1,
            trangBiId: this.cboKieuTrangBi,
            phieuId: this.droplydocap,
            ngayTh: moment(new Date()).format("DD/MM/yyyy HH:mm:ss"),
            ghiChu: ghichu,
            serial: vserial_,
            serialNew: vserial_,
            nguoiCn: this.$root.token.getUserName(),
            mayCn: "",
            ipCn: "",
          };
          let s = this.capnhat_sdvt_th(data_capnhat);
          if (s.data != "1") {
            this.FileLoi.push({
              SERIAL: element.SERIAL,
              LOI: s.data,
            });
          }
        });
        this.loading(false);
        this.XUATFILELOI();
      } catch (error) {
        this.$root.toastError("Có lỗi khi thực hiện");
      } finally {
        this.loading(false);
      }
    },
    async ckbLocVT_CheckedChanged() {
      await this.NAP_DS_TON_VT();
    },
    async HUYGIAO() {
      if (this.gridLichSuVatTu.length == 0) {
        this.$root.toastError("Không có dữ liệu để giao phiếu ");
        return;
      }
      // ds.AcceptChanges();
      // var t = ds.Select("ISCHECK='1'");
      // if (t.Length == 0)
      // {
      //     this.$root.toastError("Bạn chưa chọn danh sách vật tư cần giao đi");
      //     return;
      // }
      // if (DialogResult.No == Message_Box.ShowQuestion("Bạn có muốn hủy giao danh sách vật tư đã chọn không?"))
      //     return;
      // var dt_kt = t.CopyToDataTable();
      // for (int i = dt_kt.Columns.Count - 1; i >= 0; i--)
      // {
      //     if (dt_kt.Columns[i].ToString() != "PHIEUVT_ID")
      //         dt_kt.Columns.Remove(dt_kt.Columns[i]);
      // }
      // dt_kt.AcceptChanges();
      // string json_list = Newtonsoft.Json.JsonConvert.SerializeObject(dt_kt);
      // var kq = tdan.GET_DATA_SQL(@"Select count(*) from {?DB12}.phieu_vt where phieuvt_id in
      //                           ( select  phieuvt_id
      //                                     from json_table(:vdanh_sach,'$[*]'
      //                                                     columns
      //                                                             PHIEUVT_ID number PATH '$.PHIEUVT_ID'
      //                                                     ) ) and status <>2 ", "vdanh_sach", json_list);
      // if (kq.Rows[0][0].ToString() != "0")
      // {
      //     this.$root.toastError("Trong danh sách có vật tư có trạng thái bị thay đổi.");
      //     return;
      // }
      // var sl = tdan.EXECUTE_SQL(@" Update {?DB12}.phieu_vt set status=1,nd_traphieu=null where phieuvt_id in ( select  phieuvt_id
      //                                     from json_table(:vdanh_sach,'$[*]'
      //                                                     columns
      //                                                             PHIEUVT_ID number PATH '$.PHIEUVT_ID'
      //                                                     )) and status=2", "vdanh_sach", json_list);
      // if (sl == 0)
      // {
      //     this.$root.toastError("Hủy giao vật tư không thành công bạn hãy kiểm tra lại danh sách");
      //     return;
      // }
      // this.$root.toastSuccess("Đã hủy giao vật tư thành công");
      // await this.NAP_DS_CAPTHUVT();
    },
  },
  directives: {
    ClickOutside,
  },
  created() {
    try {
      this.loading(true);
      this.runFistTime();
    } catch (e) {
      this.$toast.error(e.data.message);
    } finally {
      this.loading(false);
    }
  },
};
</script>