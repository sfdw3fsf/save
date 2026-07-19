<template src="./XacNhanPhanHoiNVKT.html"></template>
<style src="./XacNhanPhanHoiNVKT.scss"></style>
<script>
import { mapActions, mapState } from "vuex";
import select2 from "@/components/Select2.vue";
import downloadexcel from "vue-json-excel";
import API from "../api/XacNhanPhanHoiNVKTApi";
import NoiDungKhaoSat from "./popup/NoiDungKhaoSat.vue";
import NhanVienXuLy from "./popup/NhanVienXuLy.vue";
import CauHoi from "../utils/CauHoi.vue";
import XuatFile from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
import cauHoiKS from "../KhaoSat/frmCauHoiKS2.vue";
import Vue from "vue";
import frmNhacViecBH from "../../incident/subscriber/SearchIncidentUpdateAppointment/components/frmNhacViecBH.vue";

const TRANGTHAI_PH_KS = {
  MOI: 1,
  HOANTHANH: 6,
};
export default {
  components: {
    downloadexcel,
    appSelect2: select2,
    frmNhacViecBH,
    appNoiDungKhaoSat: NoiDungKhaoSat,
    appNhanVienXuLy: NhanVienXuLy,
    appCauHoi: CauHoi,
    appXuatFile: XuatFile,
    cauHoiKS,
  },
  provide: {
    multiselect: [CheckBoxSelection],
  },
  name: "XacNhanPhanHoiNVKT",
  data() {
    return {
      header: {
        title: "XÁC NHẬN KHẢO SÁT CHẤT LƯỢNG NHÂN VIÊN KỸ THUẬT",
        list: [
          { name: "Chăm sóc khách hàng", link: "/cskh" },
          {
            name: "Xác nhận khảo sát chất lượng nhân viên kỹ thuật",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      today: new Date(),

      pTxtMaGD: "",
      pTxtNgayYC: "",
      pTxtLoaiHD: "",
      pTxtNgayKS: "",
      pTxtTenKH: "",
      pTxtDienThoaiLH: "",
      pTxtDiaChiKH: "",
      pTxtTenNguoiTL: "",
      pTxtSoLH: "",
      pTxtGhiChu: "",
      pTxtYKienXN: "",
      pTxtNDThucHien: "",
      pCboDonViLoiSelected: -1,
      pCboNVLoi: -1,
      pCboNhomNN: -1,
      pCboNguyenNhan: [],
      pCboDaXacNhan: false,
      pCboDongY: true,
      pCboPhieuKS: true,

      //public long tbtl_id, thuebao_id, baohong_id = 0, khieunai_id = 0, hdkh_id = 0, hdtb_id = 0, giaophieu_ks_id = 0;
      //  private long tbtl_cha_id = 0;
      p_HDKH_ID: 0,
      p_BAOHONG_ID: 0,
      p_TBTL_ID: 0,
      p_KHIEUNAI_ID: 0,
      p_PHIEU_ID: 0,
      p_HDTB_ID: 0,

      frmNhacViecBHInput: {},

      labelMaGD: "Mã TB",
      labelNgayYC: "Ngày BH",
      labelLoaiHD: "Loại hình",
      labelTenKH: "Tên TB",
      labelDiaChiKH: "Địa chỉ TB",

      pKhaoSat: {
        dsCauHoiChinh: [],
        dsCauHoiPhu: [],
        dsCauTraLoi: [],
      },
      pChiTietTraLoi: {},
      styles: {
        dstb: {},
      },
      fieldsXuatFile: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "FILENAME",
          headerText: "Audio",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRANGTHAI_TB",
          headerText: "Trạng thái TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_GIAO",
          headerText: "Người giao",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DV_NHAN",
          headerText: "ĐV nhận",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LOAIPHIEU",
          headerText: "Loại phiếu",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "MA_GD",
          headerText: "Mã giao dịch",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ND_GIAO",
          headerText: "ND Giao",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày CN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      srcAudio:'',
      urlTemp: '',
      audioTemplate: function () {
        return {
          template: {
            template: `
                <div class="e-unboundcelldiv" style="display: inline-block;width: 100%;text-align: center;" v-if="data.FILENAME == 1">
                    <a href="javascript: void(0)" @click="playAudio">
                    <i class="nc-icon-glyph files_audio" style="zoom: 2"></i>
                    </a>
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
            methods: {
              async playAudio(){
                await this.$parent.$parent.$parent.handleAudio(this.data,1)
                // this.$nextTick(()=> this.$parent.$parent.$parent.srcAudio = this.data.URL_FILE)
              },
            },
          },
        };
      },
      downloadTemplate: function () {
        return {
          template: {
            template: `
                <div class="e-unboundcelldiv" style="display: inline-block;width: 100%;text-align: center;" v-if="data.FILENAME == 1">
                    <a href="javascript: void(0)" @click="downloadFile(data)">
                    <i class="nc-icon-glyph arrows-2_file-download-87" style="zoom: 2"></i>
                    </a>
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
            methods: {
                async downloadFile(obj){
                    await this.$parent.$parent.$parent.handleAudio(obj,2)
                    // var a = document.createElement('a')
                    // a.href = obj.URL_FILE
                    // a.download = obj.FILENAME;
                    // a.click();
                }
            },
          },
        };
      },
    };
  },
  computed: {
    ...mapState("xacnhanphanhoinvkt", [
      "DsDonViLoiXacNhan",
      "DsNhomNguyenNhan",
      "DsNguyenNhan",
      "DsPhieuLH",
      "DsNhanVienLoi",
    ]),
    dsThuocTinh(){
        return this.fieldsXuatFile.map(item=>item.fieldName)
    }
  },
  created() {},
  async mounted() {
    this.clearData();
    this.$nextTick(() => this.$refs.txtTenNguoiTL.focus());
    try {
      this.loading(true);
      await Promise.all([
        this.getDsDonViLoiXacNhan({
          nhanVienId: this.$auth.getNhanVienID(),
          loaiDvId: 5,
        }),
        this.getDsNhomNguyenNhan({
          ttknId: 0,
          pageNo: 1,
          pageSize: 10000,
        }),
        // //@fakedata
        // this.getDsPhieuDieuLaiXacNhan({
        //   //nguoiDungId: this.$auth.getNhanVienID(),

        //   pageNo: 1,
        //   pageSize: 10000
        // })
      ]);

      await this.hienThiThongTinTB();

      this.$nextTick(() => this.matchHeight());
    } catch (e) {
      console.log(e);
    } finally {
      this.loading(false);
    }
  },
  methods: {
    ...mapActions("xacnhanphanhoinvkt", [
      "clearData",
      "clearDsPhieuLH",
      "getDsDonViLoiXacNhan",
      "getDsNhomNguyenNhan",
      "getDsNguyenNhan",
      "getDsPhieuDieuLaiXacNhan",
      "getDsPhieuXacNhanKSKhachHang",
      "getDsNhanVienLoi",
    ]),
    async layThongTinTheoHDBH(data) {
      try {
        this.loading(true);
        let response = await API.getThongTinPhieuKSTheoHDBH(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          let ds = response.data.data.data || [];
          if (ds.length > 0) return ds[0];
        }
        return null;
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    async layThongTinPhieuPhanHoiKH(data) {
      try {
        this.loading(true);
        let response = await API.getThongTinPhieuKSPhanHoi(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          let ds = response.data.data.data || [];
          if (ds.length > 0) return ds[0];
        }
        return null;
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    async layTBTraLoi(data) {
      try {
        this.loading(true);
        let response = await API.getDsThueBaoTraLoi(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          let ds = response.data.data.data || [];
          if (ds.length > 0) return ds[0];
        }
        return null;
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    async hienThiThongTinTB() {
      this.clearDsPhieuLH();
      //phiếu khảo sát
      if (this.pCboPhieuKS) {
        this.pTxtMaGD = "";
        this.pTxtTenKH = "";
        this.pTxtDiaChiKH = "";
        this.pTxtDienThoaiLH = "";
        this.pTxtSoLH = "";
        this.pTxtTenNguoiTL = "";
        this.pTxtLoaiHD = "";
        this.pTxtNgayYC = "";
        this.pTxtTenNguoiTL = "";
        this.pTxtSoLH = "";
        this.pTxtGhiChu = "";

        try {
          this.loading(true);
          //@fakedata
          let data = {
            pageNo: 1,
            pageSize: 10000,
            nguoiDungId: this.$auth.getNguoiDungID(),
            ttphId: this.pCboDaXacNhan
              ? TRANGTHAI_PH_KS.HOANTHANH
              : TRANGTHAI_PH_KS.MOI,
          };

          await this.getDsPhieuDieuLaiXacNhan(data);
          this.p_HDKH_ID =
            this.DsPhieuLH.length > 0 ? this.DsPhieuLH[0].HDKH_ID : 0;
          this.p_BAOHONG_ID =
            this.DsPhieuLH.length > 0 ? this.DsPhieuLH[0].BAOHONG_ID : 0;
          if (this.p_HDKH_ID && this.p_HDKH_ID != 0) {
            this.labelMaGD = "Mã GD";
            this.labelNgayYC = "Ngày YC";
            this.labelLoaiHD = "Loại HĐ";
            this.labelTenKH = "Tên KH";
            this.labelDiaChiKH = "Địa chỉ KH";
            let dataThongTin = {
              id: this.p_HDKH_ID,
              loai: 1,
            };
            let record = await this.layThongTinTheoHDBH(dataThongTin);
            if (record) {
              // Thông tin thuê bao
              this.pTxtMaGD = record.MA_GD;
              this.pTxtTenKH = record.TEN_KH;
              this.pTxtDiaChiKH = record.DIACHI_KH;
              this.pTxtDienThoaiLH = record.SO_DT_KH;
              this.pTxtSoLH = record.SO_DT_KH;
              this.pTxtTenNguoiTL = record.TEN_KH;
              // Lấy tốc độ loại hình
              this.pTxtLoaiHD = record.TEN_LOAIHD;
              this.pTxtNgayYC = record.NGAY_YC;
              // End thông tin thuê bao

              let tb = await this.layTBTraLoi(dataThongTin);

              if (tb) {
                if (this.p_TBTL_ID == 0) this.p_TBTL_ID = tb.TBTL_ID;
                this.pTxtTenNguoiTL = tb.NGUOI_TL;
                this.pTxtSoLH = tb.DIENTHOAI_LH;
                this.pTxtGhiChu = tb.GHICHU;
                this.pTxtNgayKS = tb.NGAY_HOI;
              }
            }
          }
          if (this.p_BAOHONG_ID && this.p_BAOHONG_ID != 0) {
            this.labelMaGD = "Mã TB";
            this.labelNgayYC = "Ngày BH";
            this.labelLoaiHD = "Loại hình";
            this.labelTenKH = "Tên TB";
            this.labelDiaChiKH = "Địa chỉ TB";
            let dataThongTin = {
              id: this.p_BAOHONG_ID,
              loai: 2,
            };
            let record = await this.layThongTinTheoHDBH(dataThongTin);
            if (record) {
              // Thông tin thuê bao
              this.pTxtMaGD = record.MA_GD;
              this.pTxtTenKH = record.TEN_KH;
              this.pTxtDiaChiKH = record.DIACHI_KH;
              this.pTxtDienThoaiLH = record.SO_DT_KH;
              this.pTxtSoLH = record.SO_DT_KH;
              this.pTxtTenNguoiTL = record.TEN_KH;
              // Lấy tốc độ loại hình
              this.pTxtLoaiHD = record.LOAIHINH_TB;
              this.pTxtNgayYC = record.NGAY_BH;
              // End thông tin thuê bao

              this.pTxtGhiChu =
                "Nội dung phản hồi: " +
                (record.ND_KHIEUNAI ? record.ND_KHIEUNAI : "");
              this.pTxtGhiChu +=
                " - Trạm xử lý: " +
                (record.NOIDUNG_GQ ? record.NOIDUNG_GQ : "");
            }
          }
        } catch (e) {
          console.log(e);
        } finally {
          this.loading(false);
        }
      }
      //phiếu phản hồi (ko thấy trong URD)
      else {
        this.pTxtMaGD = "";
        this.pTxtTenKH = "";
        this.pTxtDiaChiKH = "";
        this.pTxtDienThoaiLH = "";
        this.pTxtSoLH = "";
        this.pTxtTenNguoiTL = "";
        this.pTxtLoaiHD = "";
        this.pTxtNgayYC = "";
        this.pTxtTenNguoiTL = "";
        this.pTxtSoLH = "";
        this.pTxtGhiChu = "";
        try {
          this.loading(true);
          //@fakedata
          let data = {
            pageNo: 1,
            pageSize: 10000,
            nguoiDungId: this.$auth.getNhanVienID(),
            ttphId: this.pCboDaXacNhan
              ? TRANGTHAI_PH_KS.HOANTHANH
              : TRANGTHAI_PH_KS.MOI,
          };

          await this.getDsPhieuXacNhanKSKhachHang(data);
          this.p_KHIEUNAI_ID =
            this.DsPhieuLH.length > 0 ? this.DsPhieuLH[0].KHIEUNAI_ID : 0;
          if (this.p_KHIEUNAI_ID && this.p_KHIEUNAI_ID != 0) {
            this.labelMaGD = "Mã TB";
            this.labelNgayYC = "Ngày PH";
            this.labelLoaiHD = "Loại hình";
            this.labelTenKH = "Tên TB";
            this.labelDiaChiKH = "Địa chỉ TB";

            let dataThongTin = {
              khieuNaiId: this.p_KHIEUNAI_ID,
            };
            let record = await this.layThongTinPhieuPhanHoiKH(dataThongTin);
            if (record) {
              // Thông tin thuê bao
              this.pTxtMaGD = record.MA_TB;
              this.pTxtTenKH = record.TEN_TB;
              this.pTxtDiaChiKH = record.DIACHI_LD;
              this.pTxtDienThoaiLH = record.SO_DT_KH;
              this.pTxtSoLH = record.DIENTHOAI_LH;
              this.pTxtTenNguoiTL = record.TEN_TB;
              // Lấy tốc độ loại hình
              this.pTxtLoaiHD = record.LOAIHINH_TB;
              this.pTxtNgayYC = record.NGAY_YC;
              // End thông tin thuê bao

              if (!this.pCboDaXacNhan) this.pTxtYKienXN = "";
              else this.pTxtYKienXN = this.DsPhieuLH[0].KHIEUNAI_ID;
            }
          }
        } catch (e) {
          console.log(e);
        } finally {
          this.loading(false);
        }
      }
    },
    async hienThiThongTinTBTheoRow(dataItem) {
      //phiếu khảo sát
      if (this.pCboPhieuKS) {
        this.pTxtMaGD = "";
        this.pTxtTenKH = "";
        this.pTxtDiaChiKH = "";
        this.pTxtDienThoaiLH = "";
        this.pTxtSoLH = "";
        this.pTxtTenNguoiTL = "";
        this.pTxtLoaiHD = "";
        this.pTxtNgayYC = "";
        this.pTxtTenNguoiTL = "";
        this.pTxtSoLH = "";
        this.pTxtGhiChu = "";

        try {
          this.loading(true);
          if (this.p_HDKH_ID && this.p_HDKH_ID != 0) {
            this.labelMaGD = "Mã GD";
            this.labelNgayYC = "Ngày YC";
            this.labelLoaiHD = "Loại HĐ";
            this.labelTenKH = "Tên KH";
            this.labelDiaChiKH = "Địa chỉ KH";
            let dataThongTin = {
              id: this.p_HDKH_ID,
              loai: 1,
            };
            let record = await this.layThongTinTheoHDBH(dataThongTin);
            if (record) {
              // Thông tin thuê bao
              this.pTxtMaGD = record.MA_GD;
              this.pTxtTenKH = record.TEN_KH;
              this.pTxtDiaChiKH = record.DIACHI_KH;
              this.pTxtDienThoaiLH = record.SO_DT_KH;
              this.pTxtSoLH = record.SO_DT_KH;
              this.pTxtTenNguoiTL = record.TEN_KH;
              // Lấy tốc độ loại hình
              this.pTxtLoaiHD = record.TEN_LOAIHD;
              this.pTxtNgayYC = record.NGAY_YC;
              // End thông tin thuê bao

              let tb = await this.layTBTraLoi(dataThongTin);

              if (tb) {
                if (this.p_TBTL_ID == 0) this.p_TBTL_ID = tb.TBTL_ID;
                this.pTxtTenNguoiTL = tb.NGUOI_TL;
                this.pTxtSoLH = tb.DIENTHOAI_LH;
                this.pTxtGhiChu = tb.GHICHU;
                this.pTxtNgayKS = tb.NGAY_HOI;
              }
            }

            if (this.pCboDaXacNhan) {
              this.pCboDonViLoiSelected = dataItem.DONVI_LOI_ID;
              this.pCboNVLoi = dataItem.NHANVIEN_LOI_ID;
              this.pTxtNDThucHien = dataItem.ND_THUCHIEN;
              this.pTxtYKienXN = dataItem.GHICHU;
              this.pCboDongY = dataItem.TRANGTHAI_XN == 1;
            } else {
              this.pCboDonViLoiSelected = dataItem.DONVI_NHAN_ID;
              this.pTxtNDThucHien = "";
              this.pTxtYKienXN = "";
              this.pCboDongY = true;
            }
          }

          if (this.p_BAOHONG_ID && this.p_BAOHONG_ID != 0) {
            this.labelMaGD = "Mã TB";
            this.labelNgayYC = "Ngày BH";
            this.labelLoaiHD = "Loại hình";
            this.labelTenKH = "Tên TB";
            this.labelDiaChiKH = "Địa chỉ TB";
            let dataThongTin = {
              id: this.p_BAOHONG_ID,
              loai: 2,
            };
            let record = await this.layThongTinTheoHDBH(dataThongTin);
            if (record) {
              // Thông tin thuê bao
              this.pTxtMaGD = record.MA_TB;
              this.pTxtTenKH = record.TEN_TB;
              this.pTxtDiaChiKH = record.DIACHI_LD;
              this.pTxtDienThoaiLH = record.SO_DT_KH;
              this.pTxtSoLH = record.DIENTHOAI_LH;
              this.pTxtTenNguoiTL = record.TEN_TB;
              // Lấy tốc độ loại hình
              this.pTxtLoaiHD = record.LOAIHINH_TB;
              this.pTxtNgayYC = record.NGAY_BH;
              // End thông tin thuê bao

              this.pTxtGhiChu =
                "Nội dung phản hồi: " +
                (record.ND_KHIEUNAI ? record.ND_KHIEUNAI : "");
              this.pTxtGhiChu +=
                " - Trạm xử lý: " +
                (record.NOIDUNG_GQ ? record.NOIDUNG_GQ : "");
            }

            if (this.pCboDaXacNhan) {
              this.pCboDonViLoiSelected = dataItem.DONVI_LOI_ID;
              this.pCboNVLoi = dataItem.NHANVIEN_LOI_ID;
              this.pTxtNDThucHien = dataItem.ND_THUCHIEN;
              this.pTxtYKienXN = dataItem.GHICHU;
              this.pCboDongY = dataItem.TRANGTHAI_XN == 1;
            } else {
              this.pCboDonViLoiSelected = dataItem.DONVI_NHAN_ID;
              this.pTxtNDThucHien = "";
              this.pTxtYKienXN = "";
              this.pCboDongY = true;
            }
          }

          if (this.p_KHIEUNAI_ID && this.p_KHIEUNAI_ID != 0) {
            this.labelMaGD = "Mã TB";
            this.labelNgayYC = "Ngày PH";
            this.labelLoaiHD = "Loại hình";
            this.labelTenKH = "Tên TB";
            this.labelDiaChiKH = "Địa chỉ TB";

            let dataThongTin = {
              khieuNaiId: this.p_KHIEUNAI_ID,
            };
            let record = await this.layThongTinPhieuPhanHoiKH(dataThongTin);
            if (record) {
              // Thông tin thuê bao
              this.pTxtMaGD = record.MA_TB;
              this.pTxtTenKH = record.TEN_TB;
              this.pTxtDiaChiKH = record.DIACHI_LD;
              this.pTxtDienThoaiLH = record.SO_DT_KH;
              this.pTxtSoLH = record.DIENTHOAI_LH;
              this.pTxtTenNguoiTL = record.TEN_TB;
              // Lấy tốc độ loại hình
              this.pTxtLoaiHD = record.LOAIHINH_TB;
              this.pTxtNgayYC = record.NGAY_YC;
              // End thông tin thuê bao

              if (!this.pCboDaXacNhan) this.pTxtYKienXN = "";
              else this.pTxtYKienXN = this.DsPhieuLH[0].KHIEUNAI_ID;
            }
          }
        } catch (e) {
          console.log(e);
        } finally {
          this.loading(false);
        }
      }
    },
    async gridPhieuDieuLaiXacNhan_selectedRowChanged(dataItem) {
      if (dataItem) {
        this.p_TBTL_ID = dataItem.TBTL_ID;
        this.p_PHIEU_ID = dataItem.PHIEU_ID;
        this.p_HDKH_ID = dataItem.HDKH_ID;
        this.p_BAOHONG_ID = dataItem.BAOHONG_ID;
        this.p_KHIEUNAI_ID = dataItem.KHIEUNAI_ID;
        this.urlTemp = dataItem.URL_FILE;
        await this.hienThiThongTinTBTheoRow(dataItem);

        this.$refs.cauHoiKS.tbtlId = dataItem.TBTL_ID;
        await this.$refs.cauHoiKS.loadData();
        // await this.hienThiCH();
      }
    },
    async hienThiCH() {
      if (false && this.pCboPhieuKS) {
        try {
          this.$root.showLoading(true);
          this.pKhaoSat.dsCauHoiChinh = [];
          this.pKhaoSat.dsCauHoiPhu = [];
          this.pKhaoSat.dsCauTraLoi = [];
          this.pChiTietTraLoi = [];
          await API.getDsChiTietTraLoi(this.axios, {
            tbtlId: this.p_TBTL_ID,
          })
            .then((response) => {
              if (response.data.error_code === "BSS-00000000") {
                this.pChiTietTraLoi = response.data.data || [];
              }
            })
            .catch((reject) => {
              //this.$root.toastError("Không lấy được danh sách câu hỏi!");
            });
          await API.getDsCauHoiTheoLoaiNV(this.axios, {
            nhanVienId: 6,
            hinhThuc: 0,
          })
            .then(async (response) => {
              if (response.data.error_code === "BSS-00000000") {
                this.pKhaoSat.dsCauHoiChinh = response.data.data || [];
                this.pKhaoSat.dsCauHoiChinh.forEach(async (cauhoi, index) => {
                  try {
                    await API.getDsCauHoiPhu(this.axios, {
                      cauHoiId: cauhoi.CAUHOI_ID,
                    })
                      .then((response2) => {
                        if (response2.data.error_code === "BSS-00000000") {
                          // let dsCauHoiPhu = response2.data.data || []
                          let cauhoi2 = cauhoi;
                          cauhoi2.cauHoiPhu = response2.data.data || [];
                          cauhoi2.cauHoiPhu.forEach((element) => {
                            let tl = this.pChiTietTraLoi.filter(
                              (x) =>
                                x.CAUHOI_ID == cauhoi.CAUHOI_ID &&
                                x.TRALOI_ID == element.TRALOI_ID
                            );
                            //console.log('tl', tl)
                            if (tl && tl.length > 0) {
                              element.Checked = true;
                              console.log("tl", tl);
                              element.TxtNoiDung = tl.NOIDUNG;
                            } else {
                              element.Checked = false;
                              element.TxtNoiDung = "";
                            }
                          });
                          this.$set(
                            this.pKhaoSat.dsCauHoiChinh,
                            index,
                            cauhoi2
                          );
                          // cauhoi.cauHoiPhu.forEach(element => element.Checked = false)
                        }
                      })
                      .catch(() => {});
                  } catch (e) {
                    console.log(e);
                  }
                });
              }
            })
            .catch((reject) => {
              this.$root.toastError("Không lấy được danh sách câu hỏi!");
            });

          await API.getDsChiTietTraLoi(this.axios, {
            tbtlId: this.p_TBTL_ID,
          }).then((response) => {
            if (response.data.error_code === "BSS-00000000") {
              this.pKhaoSat.dsCauTraLoi = response.data.data || [];
            }
          });
        } catch (e) {
          console.log(e);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    async onNhomNguyenNhanChanged() {
      try {
        this.loading(true);
        this.pCboNguyenNhan = [];
        await this.getDsNguyenNhan({
          nhomNnId: this.pCboNhomNN,
          pageNo: 1,
          pageSize: 1000,
        });
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    onTraLoiChecked(index, indexPhu) {
      for (
        let i = 0;
        i < this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu.length;
        i++
      ) {
        // let cauHoiPhu = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[i]
        // cauHoiPhu.Checked = !cauHoiPhu.Checked
        //   this.$set(this.pKhaoSat.dsCauHoiChinh[index], indexPhu, cauHoiPhu)
        let ch = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[i];
        ch.Checked = i == indexPhu;
        ch.TxtNoiDung = "";
        this.$set(this.pKhaoSat.dsCauHoiChinh[index], i, ch);
      }
      // let cauHoiPhu = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu]
      // cauHoiPhu.Checked = !cauHoiPhu.Checked
      // this.$set(this.pKhaoSat.dsCauHoiChinh[index], indexPhu, cauHoiPhu)
      // console.log(index, indexPhu)
    },
    async onXacNhan() {
      try {
        this.$root.showLoading(true);
        // let dsCauHoiId = [];
        // let dsTraLoiId = [];
        // let dsYKien = [];
        // this.pKhaoSat.dsCauHoiChinh.forEach(cauhoi => {
        //   if (cauhoi.cauHoiPhu) {
        //     cauhoi.cauHoiPhu.forEach((cauhoiphu, index) => {
        //       if (cauhoiphu && cauhoiphu.Checked) {
        //         dsCauHoiId.push(cauhoi.CAUHOI_ID);
        //         dsTraLoiId.push(cauhoiphu.TRALOI_ID);
        //         dsYKien.push(cauhoiphu.TxtNoiDung);
        //       }
        //     });
        //   }
        // });

        let data = {
          tbtlId: this.p_TBTL_ID,
          baoHongId: this.p_BAOHONG_ID,
          hdkhId: this.p_HDKH_ID,
          hdtbId: this.p_HDTB,
          thueBaoId: 0, //???
          nguoiTl: this.pTxtTenNguoiTL,
          dienThoaiLh: this.pTxtSoLH,
          ghiChu: this.pTxtGhiChu,
          dsCauHoiId: this.$refs.cauHoiKS.ketQuaKS
            .map((x) => x.CAUHOI_ID)
            .join("#"),
          dsTraLoiId: this.$refs.cauHoiKS.ketQuaKS
            .map((x) => x.TRALOI_ID)
            .join("#"),
          dsNoiDung: this.$refs.cauHoiKS.ketQuaKS
            .map((x) => x.NOIDUNG)
            .join("#"),
          phieuId: this.p_PHIEU_ID,
          dsNguyenNhanId: this.pCboNguyenNhan.join(),
          yKienXn: this.pTxtYKienXN,
          ndThucHien: "test nội dung",
          nhanVienId: this.$root.token.getNhanVienID(),
          donViId: this.$root.token.getDonViID(),
          donViLoiId: this.pCboDonViLoiSelected,
          nhanVienLoiId: this.pCboNVLoi,
          trangThaiXn: this.pCboDongY ? 1 : 0,
        };

        let response = await API.capNhapThongTinKhaoSat(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Cập nhật thành công!");
          await this.hienThiThongTinTB();
        } else {
          this.$root.toastError("Cập nhật thất bại!");
        }
      } catch (e) {
        this.$root.toastError("Có lỗi: " + e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    matchHeight() {
      var heightTTDKS = this.$refs.boxTTDKS.clientHeight + 2 + "px";
      Vue.set(this.styles.dstb, "height", heightTTDKS);
    },
    onXuatFileClicked() {
      if (this.DsPhieuLH.length <= 0) {
        this.$root.toastError("Không có dữ liệu để xuất!");
        return;
      }
      this.$bvModal.show("modal-xuat-dulieu");
    },
    onNhacViecClicked() {
      this.frmNhacViecBHInput.loaidv_nhan_id = "0";
      this.frmNhacViecBHInput.thuebao_id = this.thuebao_id;
      this.frmNhacViecBHInput.ma_tb = this.ma_tb;
      this.$refs.reffrmNhacViecBH2.openDialog();
    },
    playAudio2(){
      const data = {
        URL_FILE: this.urlTemp,
        MA_TB: this.urlTemp
      }
      this.handleAudio(data,1)
    },
    async handleAudio(data,type){
      const urlFile = data.URL_FILE
      const matb = data.MA_TB
      try {
            this.loading(true);
            const baseUrl = process.env.API;
            await this.axios({
            url: `${baseUrl}/web-cskh/api/xac-nhan-phan-hoi-chat-luong/download`,
            method: "POST",
            responseType: "blob",
            data: {
                tbtlId: data.TBTL_ID
            },
            }).then((response) => {
                let blob = new Blob([response.data], { type: "audio/wav" });
                let blobUrl = URL.createObjectURL(blob);
                if(type == 1){
                    this.$nextTick(()=> this.srcAudio = blobUrl)
                    this.$bvModal.show("popup-audio");
                } else {
                    let a = document.createElement('a')
                    a.href = blobUrl
                    a.download = matb;
                    a.click();
                }
            });
        } catch (error) {
            this.$root.toastError("Có lỗi xảy ra. Mời thử lại!");
        } finally {
            this.loading(false);
        }
    }
  },
  watch: {
    pCboDaXacNhan: {
      handler: async function (value) {
        await this.hienThiThongTinTB();
      },
    },
    pCboDonViLoiSelected: {
      handler: async function (value) {
        try {
          this.$root.showLoading(true);
          let data = {
            dsLoaiNvId: 6,
            donViId: value,
          };
          await this.getDsNhanVienLoi(data);
          this.$nextTick(() => {
            if (this.DsNhanVienLoi && this.DsNhanVienLoi.length > 0)
              this.pCboNVLoi = this.DsNhanVienLoi[0].id;
            else this.pCboNVLoi = -1;
          });
        } catch (e) {
          console.log(e);
        } finally {
          this.$root.showLoading(false);
        }
      },
    },
  },
};
</script>
