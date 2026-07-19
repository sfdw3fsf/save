<template src="./DSPhieuGiao.html"></template>

<script>
import moment from "moment";
import API from "../api/DSPhieuGiao.js";
import Vue from "vue";
import { TreeGridPlugin, Page } from "@syncfusion/ej2-vue-treegrid";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import frmTraPhieuVT from "../frmTraPhieuVT";
import xlsx from "xlsx";
Vue.use(DatePickerPlugin);
Vue.use(TreeGridPlugin);
export default {
  name: "DSPhieuGiao",
  data() {
    return {
      gParams: {
        txtMa: "",
        cboTrangThai: "",
        txtDonViGiao: "",
        txtNhanVienGiao: "",
        dNgayGiao: new Date(),
        txtDonViNhan: "",
        cboNhiemVu: "",
        txtNDGiao: "",
        txtMaOTPChon: "",
        cboDonVi: "",
        cboNhanVienXL: "",
        textEdit1: "",
        dTuNgay: new Date((new Date()).getTime() - 7*24*60*60*1000),
        dDenNgay: new Date(),
        rdoPhieuSelected: "1",
        huonggiao_tieptheo_id: 0,
        vdonvi_id: 0,
        vchungtu_id: 0,
        huonggiao_id: 0,
        vquyettoan_id: 0,
        vloaiphieu_id: 0,
        treeRowSelected: {},
        dtDanhSachRowChanged: {},
        dtFileRowChanged: {},
        taikhoan_ks: "",
        matkhau_ks: ""
      },
      isShowPass: false,
      cboTrangThai: [],
      cboNhiemVu: [],
      cboDonVi: [],
      cboNhanVienXL: [],
      dtFiles: [],
      dtDanhSach: [],
      dtChiTietCT: [],
      treeLoaiPhieu: [],
      dtDVNV_GP: [],
      dsTP: [],
      dsBC: [],
      parameters: [],
      dtThaoTac: [],
      visibled: {
        colSoPhieu: false,
        colKhoGiao: false,
        colKhoNhan: false,
        colTongTien: false,
        colNgay_CT: false,
        tsbtnXacNhan: true,
        tsbtnGiaoPhieu: true,
        tsbtnTraPhieu: true,
        tsbtnHuyGiao: true,
        colNdTra: false,
        colNgayTra: false,
        colLyDoTra: false,
        colNdTH: false,
        colNgayTH: false,
        tsbtnXemPhieu: true,
      },
      enabled: {
        txtMaOTPChon: true,
        btnLayLaiOTP: true,
        cboNhanVienXL: true,
        cboDonVi: true,
        textEdit1: true,
        tsbtnHuyGiao: true,
        tsbtnXemPhieu: false,
      },
      selectionOptions: {
        type: "Multiple",
      },
      isXacNhan: false,
      tabIndex: 0,
      TAB: {
        TABDANHSACH: 0,
        TABCHUNGTU: 1,
      },
      PhieuChecked: [],
      checkAllPhieu: false,
      URL: 'http://10.70.52.164:8081/FILE/',
      LOAIPHIEU_DENGHI_VATTU: {
        PHIEU_DANGKY_NHUCAU: 1,
        PHIEU_DENGHI_NHAPKHO_TT: 2,
        PHIEU_DENGHI_MUALE: 3,
        PHIEU_NHAPKHO: 4,
        PHIEU_DENGHI_CHUYENKHO: 5,
        PHIEU_CHUYENKHO: 6,
        PHIEU_DENGHI_XUATKHO: 7,
        PHIEU_XUATKHO: 8,
        PHIEU_DENGHI_QUYETTOAN: 9,
        PHIEU_DENGHI_THUHOI_TB: 10,
        PHIEU_DENGHI_THUHOI_CT: 11,
      },
    };
  },
  components: {
    frmTraPhieuVT: frmTraPhieuVT,
  },
  computed: {
    getDSColumn() {
      return [
        { fieldName: '', 
          headerText: '', 
          allowSorting:false , 
          allowFiltering:false, 
          textAlign:'Center', 
          allowEditing:false , 
          width: 40, 
          headerTemplate: function () {
            return {
              template: {
                template: `
                                <div class="check-action">
                                    <input type="checkbox" v-model="parent.checkAllPhieu" class="uncheck"/>
                                    <span class="name"></span>
                                </div>
                            `,
                data: function () {
                  return {};
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent.$parent.$parent;
                  },
                },
              },
            };
          }, 
          template: function () {
            return {
              template: {
                template: `
                                <div class="check-action">
                                    <input
                                        type="checkbox"
                                        class="uncheck"                                    
                                        v-model="parent.PhieuChecked"
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
                    return this.$parent.$parent.$parent.$parent.$parent;
                  },
                },
              },
            };
          },
          freeze: 'Left'
        },
        {
          fieldName: "MA_PHIEU",
          headerText: "Mã phiếu",
          allowFiltering: true,
          allowSorting: true,
          freeze: "Left",
          width: 130,
        },
        {
          fieldName: "SOPHIEU",
          headerText: "Số phiếu",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colSoPhieu,
        },
        {
          fieldName: "NGAY_CT",
          headerText: "Ngày CT",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colNgay_CT,
        },
        {
          fieldName: "LOAI_PHIEU",
          headerText: "Loại phiếu",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "THUKHO",
          headerText: "Thủ kho",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "DONVI_GIAO",
          headerText: "Đơn vị giao",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "NHANVIEN_GIAO",
          headerText: "Nhân viên giao",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "DONVI_NHAN",
          headerText: "Đơn vị nhận",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "NGAY_GIAO",
          headerText: "Ngày giao",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "TRANGTHAI_PH",
          headerText: "Trạng thái PH",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
        },
        {
          fieldName: "LYDOTRA",
          headerText: "Lý do trả",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colLyDoTra,
        },
        {
          fieldName: "ND_TRA",
          headerText: "Nội dung trả",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colNdTra,
        },
        {
          fieldName: "NGAY_TRA",
          headerText: "Ngày trả",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colNgayTra,
        },
        {
          fieldName: "ND_THUCHIEN",
          headerText: "Nội dung TH",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colNdTH,
        },
        {
          fieldName: "NGAY_TH",
          headerText: "Ngày TH",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colNgayTH,
        },
        {
          fieldName: "KHO_GIAO",
          headerText: "Kho giao",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colKhoGiao,
        },
        {
          fieldName: "KHO_NHAN",
          headerText: "Kho nhận",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colKhoNhan,
        },
        {
          fieldName: "TONGTIEN",
          headerText: "Tổng tiền",
          allowFiltering: true,
          allowSorting: true,
          width: 130,
          visible: this.visibled.colTongTien,
        },
        { fieldName: 'PHIEU_ID', 
          headerText:'', 
          visible:false, 
          isPrimaryKey:true
        }       
      ];
    },
    getTreeDataSource() {
      return [
        {
          LOAIPHIEU: this.treeLoaiPhieu.length
            ? this.treeLoaiPhieu[0].LOAIPHIEU
            : "",
          children: this.treeLoaiPhieu.filter((e) => e.DEM),
        },
      ];
    },
    vurl_luoi: {
      get() {
        return !this.gParams.dtFileRowChanged.hasOwnProperty("filename") ||
          !this.gParams.dtFileRowChanged["filename"]
          ? ""
          : parseInt(this.gParams.dtFileRowChanged["filename"]);
      },
    },
    vname: {
      get() {
        return !this.gParams.dtFileRowChanged.hasOwnProperty("filename") ||
          !this.gParams.dtFileRowChanged["filename"]
          ? "TEMP"
          : parseInt(this.gParams.dtFileRowChanged["filename"]);
      },
    },
  },
  methods: {
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    dateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    stringToDatetime(value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value, format).toDate();
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    cboTrangThaiChanged(value) {},

    PHAILAM(code) {
      if (this.dtThaoTac.length) {
        let result = this.dtThaoTac.filter(
          (e) => e.ENABLE == 1 && e.CODE == code
        );
        if (result.length) {
          return true;
        }
      }
      return false;
    },
    TONTAI(code) {
      if (this.dtThaoTac.length) {
        let result = this.dtThaoTac.filter((e) => e.CODE == code);
        if (result.length) {
          return true;
        }
      }
      return false;
    },
    clearThongTinPhieu(){
      this.gParams.huonggiao_tieptheo_id = 0;
      this.gParams.txtMa = "";
      this.gParams.cboTrangThai = "";
      this.gParams.txtDonViGiao = "";
      this.gParams.txtDonViNhan = "";
      this.gParams.txtNhanVienGiao = "";
      this.gParams.dNgayGiao = new Date();
      this.gParams.cboNhiemVu = "";      
      this.gParams.txtNDGiao = "";      
      this.gParams.txtMaOTPChon = ""
      this.dtFiles = [];
      this.gParams.cboNhanVienXL = "";
      this.gParams.cboDonVi = "";
      this.cboDonVi = [];
      this.cboNhanVienXL = [];
      this.gParams.dtDanhSachRowChanged = {}
    },
    async Load_DanhSach_DL() {
      try {
        this.loading(true);
        this.gParams.huonggiao_tieptheo_id = 0;
        this.gParams.cboNhiemVu = "";
        this.gParams.cboTrangThai = "";
        this.gParams.txtNhanVienGiao = "";
        this.gParams.txtDonViGiao = "";
        this.gParams.txtDonViNhan = "";
        this.gParams.txtNDGiao = "";
        this.gParams.txtMa = "";
        this.gParams.dNgayGiao = new Date();
        this.dtFiles = [];
        this.gParams.cboNhanVienXL = "";
        this.gParams.cboDonVi = "";
        this.cboDonVi = [];
        this.cboNhanVienXL = [];
        this.visibled.colSoPhieu = false;
        this.visibled.colKhoGiao = false;
        this.visibled.colKhoNhan = false;
        this.visibled.colTongTien = false;
        this.visibled.colNgay_CT = true;
        this.tabIndex = 0;
        let vtungay = this.dateToString(this.gParams.dTuNgay);
        let vdenngay = this.dateToString(this.gParams.dDenNgay);
        let dtDSPhieu = (
          await API.getDSGPNVQLDN(this.axios, {
            loaiPhieuId: this.gParams.treeRowSelected.LOAIPHIEU_ID,
            nhiemVuId: -1,
            ttphId: this.gParams.rdoPhieuSelected,
            tuNgay: vtungay,
            denNgay: vdenngay,
          })
        ).data.data;
        this.dtDanhSach = [];    
        this.gParams.dtDanhSachRowChanged = {}
        this.PhieuChecked = []
        this.checkAllPhieu = false
        this.gParams.huonggiao_tieptheo_id = 0;
        if (dtDSPhieu.length) {
          this.gParams.huonggiao_id = parseInt(dtDSPhieu[0].HUONGGIAO_ID);
          this.tabIndex = 0;
          this.dtThaoTac = await (await API.getThaoTacHG(this.axios, {
            huongGiaoId: this.gParams.huonggiao_id,
          })).data.data
          
          if (this.TONTAI("DENGHI_CHUNGTU")) {
            this.visibled.colSoPhieu = true;
            this.visibled.colKhoGiao = true;
            this.visibled.colKhoNhan = true;
            this.visibled.colTongTien = true;
            this.visibled.colNgay_CT = true;
          } else {
            this.visibled.colSoPhieu = false;
            this.visibled.colNgay_CT = true;
          }
          this.dtDanhSach = dtDSPhieu;
          let ds = (
            await API.getDSHuongGiaoTiep(this.axios, {
              "huong-giao-id": this.gParams.huonggiao_id,
            })
          ).data.data;
          if (ds.length > 0) {
            this.gParams.huonggiao_tieptheo_id = parseInt(
              ds[0]["HUONGGIAO_ID"]
            );
          }
        }
       
        if (this.gParams.huonggiao_tieptheo_id > 0 && !this.gParams.dtDanhSachRowChanged) {
          let dr = this.gParams.dtDanhSachRowChanged;
        
          let vphieu_id = dr["PHIEU_ID"] == null || dr["PHIEU_ID"] == "" ? 0 : dr["PHIEU_ID"];
          this.dtDVNV_GP = (
            await API.getDSDonViNVGP(this.axios, {
              phieuId: vphieu_id,
              huongGiaoTiepTheoId: this.gParams.huonggiao_tieptheo_id,
            })
          ).data.data;
          if (this.dtDVNV_GP.length == 0) {
            return this.$toast.error(
              "Không xác định được đơn vị giao phiếu tiếp theo"
            );
          }
          this.gParams.vdonvi_id = parseInt(this.dtDVNV_GP[0]["DONVI_ID"]);
          let dsDV = await API.getDSDonViGiaoPhieu(this.axios, {
            huongGiaoId: this.gParams.huonggiao_tieptheo_id,
            nhanVienId: this.$auth.getNhanVienID(),
            donViId: this.gParams.vdonvi_id,
          });
          this.cboDonVi = dsDV.data.data;
          
          this.gParams.cboDonVi = this.dtDVNV_GP[0]["DONVI_ID"] == null ? "" : this.dtDVNV_GP[0]["DONVI_ID"].toString();

          let CheckDonVi = this.cboDonVi.filter(e=> e.DONVI_ID == this.gParams.cboDonVi)
          if(CheckDonVi.length > 0) { // Đơn vị GP có trong Đơn Vị XL mới load lại
            await this.cboDonViChanged() // call lại tránh trường hợp đổi nhân viên k đổi đơn vị sinh bug
          }        
       
        }
        this.visibled.tsbtnXacNhan =
          this.gParams.rdoPhieuSelected == "1" &&
          this.gParams.huonggiao_tieptheo_id == 0;

        this.visibled.tsbtnGiaoPhieu =
          this.gParams.rdoPhieuSelected == "1" &&
          this.gParams.huonggiao_tieptheo_id != 0;

        this.visibled.tsbtnTraPhieu =
          this.gParams.rdoPhieuSelected == "1" ||
          this.gParams.rdoPhieuSelected == "4";

        this.visibled.tsbtnHuyGiao = this.enabled.tsbtnHuyGiao =
          this.gParams.rdoPhieuSelected == "2";

        this.enabled.txtMaOTPChon = this.gParams.rdoPhieuSelected == "1";
        this.enabled.btnLayLaiOTP = this.gParams.rdoPhieuSelected == "1";
        this.enabled.cboNhanVienXL = this.gParams.huonggiao_tieptheo_id != "0";
        this.enabled.cboDonVi = this.gParams.huonggiao_tieptheo_id != "0";
        this.enabled.textEdit1 = this.gParams.huonggiao_tieptheo_id != "0";
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async Load_DanhMuc() {
      try {
        this.loading(true);
        let ds = (
          await API.getDanhMucQLDN(this.axios, {
            nhanVienId: this.$auth.getNhanVienID(),
            kieu: 3,
            param1: this.gParams.treeRowSelected.LOAIPHIEU_ID,
            param2: "",
          })
        ).data.data;
        if (ds.length) {
          this.cboTrangThai = ds;
        }
        let dsNV = (
          await API.getDanhMucQLDN(this.axios, {
            nhanVienId: this.$auth.getNhanVienID(),
            kieu: 2,
            param1: this.gParams.treeRowSelected.LOAIPHIEU_ID,
            param2: "",
          })
        ).data.data;
        this.cboNhiemVu = dsNV;
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    ReloadGrid(){      
      this.$refs.gridChiTietCT.grid.refreshColumns()
    },
    async Load_DL_CT() {
      try {
        this.loading(true);
        if (!this.dtDanhSach || !this.gParams.dtDanhSachRowChanged) {
          return;
        }
        let dr = this.gParams.dtDanhSachRowChanged;
        this.dtChiTietCT = [];
       
        if (dr["CHUNGTU_ID"]) {
          this.dtChiTietCT = (
            await API.getDSVTChungTu(this.axios, {
              chungTuId: dr["CHUNGTU_ID"],
            })
          ).data.data;
        }
        
     
        this.gParams.cboNhiemVu = parseInt(dr["NHIEMVU_ID"]);
        this.gParams.cboTrangThai = parseInt(dr["TRANGTHAI_ID"]);
        this.gParams.txtNhanVienGiao = dr["NHANVIEN_GIAO"];
        this.gParams.txtDonViGiao = dr["DONVI_GIAO"];

        this.gParams.txtDonViNhan = dr["DONVI_NHAN"];
        this.gParams.txtNDGiao = dr["GHICHU"];
        this.gParams.txtMa = dr["MA_PHIEU"];
        this.gParams.huonggiao_id = parseInt(dr["HUONGGIAO_ID"]);
        this.gParams.dNgayGiao = this.stringToDatetime(dr["NGAY_GIAO"]);
        let vphieu_id = dr["PHIEU_ID"] == "" || dr["PHIEU_ID"] == null ? 0 : dr["PHIEU_ID"];
        let vmota = dr["MOTA"];
        if (this.PHAILAM("DENGHI_CHUNGTU")) {
          vmota = dr["MOTA_CUOI"];
        }
        if (vmota) {
          let list_mota = JSON.parse(vmota);
          if (
            dr["CHUNGTU_ID"] &&
            (await this.KT_BOQUA_GIAO_LANHDAO(this.gParams.vloaiphieu_id))
          ) {
            let dsDeNghiQT = (
              await API.getDeNghiQTTheoChungTu(this.axios, {
                chungTuId: dr["CHUNGTU_ID"],
                ttPhId: this.gParams.rdoPhieuSelected == "1" ? 1 : 0,
              })
            ).data.data;
            if (dsDeNghiQT.length) {
              let vmotadn = dsDeNghiQT[0]["MOTA"];
              let list_motadn = JSON.parse(vmotadn);
              list_mota.push(...vmotadn);
            }
          }
          list_mota.forEach((element) => {
            element.filename = element.url.substring(
              element.url.lastIndexOf("/") + 1
            );
          });
          this.dtFiles = list_mota;
        }
        this.gParams.huonggiao_tieptheo_id = 0;
        let ds = (
          await API.getDSHuongGiaoTiep(this.axios, {
            "huong-giao-id": this.gParams.huonggiao_id,
          })
        ).data.data;
        if (ds.length > 0) {
          this.gParams.huonggiao_tieptheo_id = parseInt(ds[0]["HUONGGIAO_ID"]);
        }
        if (this.gParams.huonggiao_tieptheo_id > 0) {
          this.dtDVNV_GP = (
            await API.getDSDonViNVGP(this.axios, {
              phieuId: vphieu_id,
              huongGiaoTiepTheoId: this.gParams.huonggiao_tieptheo_id,
            })
          ).data.data;
          if (this.dtDVNV_GP == null || this.dtDVNV_GP.length == 0) {
            return this.$toast.error(
              "Không xác định được đơn vị giao phiếu tiếp theo"
            );
          }
          this.cboDonVi = []
          this.gParams.vdonvi_id = parseInt(this.dtDVNV_GP[0]["DONVI_ID"]);
          let dsDV = await API.getDSDonViGiaoPhieu(this.axios, {
            huongGiaoId: this.gParams.huonggiao_tieptheo_id,
            nhanVienId: this.$auth.getNhanVienID(),
            donViId: this.gParams.vdonvi_id,
          });
          this.cboDonVi = dsDV.data.data;
          
          this.gParams.cboDonVi = this.setFirstValue(this.cboDonVi, "DONVI_ID");
          if (this.cboDonVi.length && this.dtDVNV_GP && this.dtDVNV_GP.length) {
            this.gParams.cboDonVi = this.dtDVNV_GP[0]["DONVI_ID"] == null ? "" : this.dtDVNV_GP[0]["DONVI_ID"].toString()            
          }
          let CheckDonVi = this.cboDonVi.filter(e=> e.DONVI_ID == this.gParams.cboDonVi)

          if(CheckDonVi.length > 0) { // Đơn vị GP có trong Đơn Vị XL mới load lại
            await this.cboDonViChanged() // call lại tránh trường hợp đổi nhân viên k đổi đơn vị sinh bug
          }
          
        }
        this.visibled.tsbtnXacNhan =
          this.gParams.rdoPhieuSelected == "1" &&
          this.gParams.huonggiao_tieptheo_id == 0;

        this.visibled.tsbtnGiaoPhieu =
          this.gParams.rdoPhieuSelected == "1" &&
          this.gParams.huonggiao_tieptheo_id != 0;

        this.visibled.tsbtnTraPhieu =
          this.gParams.rdoPhieuSelected == "1" ||
          this.gParams.rdoPhieuSelected == "4";

        this.visibled.tsbtnHuyGiao = this.enabled.tsbtnHuyGiao =
          this.gParams.rdoPhieuSelected == "2";

        this.enabled.txtMaOTPChon = this.gParams.rdoPhieuSelected == "1";
        this.enabled.btnLayLaiOTP = this.gParams.rdoPhieuSelected == "1";
        this.enabled.cboNhanVienXL = this.gParams.huonggiao_tieptheo_id != "0";
        this.enabled.cboDonVi = this.gParams.huonggiao_tieptheo_id != "0";
        this.enabled.textEdit1 = this.gParams.huonggiao_tieptheo_id != "0";
        if (
          this.gParams.vloaiphieu_id ==
            this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_QUYETTOAN ||
          this.gParams.vloaiphieu_id ==
            this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_THUHOI_TB
        ) {
          this.gParams.vquyettoan_id = parseInt(dr["QUYETTOAN_ID"]);
        } else this.gParams.vquyettoan_id = 0;
      } catch (error) {   
        this.$toast.error(error.data.message)
      } finally {
        this.loading(false);
      }
    },
    async KT_BOQUA_GIAO_LANHDAO(vloaiphieu_id) {
      let dsTS = (
        await API.getDSThamSoMacDinh(this.axios, {
          tsmd: "QLDN_BOQUA_GIAO_LANHDAO",
        })
      ).data.data;
      if (dsTS && dsTS.length && dsTS[0]["TEN_TS"] == "1") {
        let arr = dsTS[0]["MACDINH"].split(";");
        if (Array.isArray(arr) && arr.some((e) => e == vloaiphieu_id)) {
          return true;
        }
      }
      return false;
    },
    Load_GridColumn() {
      this.visibled.colNdTra = false;
      this.visibled.colNgayTra = false;
      this.visibled.colLyDoTra = false;
      this.visibled.colNdTH = false;
      this.visibled.colNgayTH = false;
      if (this.gParams.rdoPhieuSelected == 3) {
        this.visibled.colNdTra = true;
        this.visibled.colNgayTra = true;
        this.visibled.colLyDoTra = true;
      }
      if (
        this.gParams.rdoPhieuSelected == 4 ||
        this.gParams.rdoPhieuSelected == 2
      ) {
        this.visibled.colNdTH = true;
        this.visibled.colNgayTH = true;
      }
    },
    async rdoPhieuChanged(value) {
      this.dtDanhSach = []
      this.clearThongTinPhieu()
      await this.Load_DanhSach_DL();
      this.Load_GridColumn();
    },
    async onRowSelectingTreeGrid(value) {
      try{
        this.loading(true)
        this.clearThongTinPhieu()
        this.dtDanhSach = []
        if (value) {
          this.gParams.treeRowSelected = value.data;
          this.gParams.vloaiphieu_id = this.gParams.treeRowSelected.LOAIPHIEU_ID;
          if (
            this.gParams.vloaiphieu_id ==
              this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_QUYETTOAN ||
            this.gParams.vloaiphieu_id ==
              this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_THUHOI_TB
          ) {
            this.enabled.tsbtnXemPhieu = true;
          } else {
            this.enabled.tsbtnXemPhieu = false;
          }
          await this.Load_DanhMuc();
          await this.Load_DanhSach_DL();
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    treeCellInfor(args) {
      // console.log(args);
      switch (args.column.field) {
        case "DEM":
          if (args.data.DEM && args.data.DEM > 0) {
            args.cell.setAttribute("style", "color: Salmon;");
          }
          break;
      }
    },
    async onGridDanhSachRowChanged(value) {
      try{
        this.loading(true)
        this.gParams.dtDanhSachRowChanged = value;
        if (value) {
          await this.Load_DL_CT();
        } else {
          this.gParams.cboNhiemVu = "";
          this.gParams.cboTrangThai = "";
          this.gParams.txtNhanVienGiao = "";
          this.gParams.txtDonViGiao = "";
          this.gParams.txtDonViNhan = "";
          this.gParams.txtNDGiao = "";
          this.gParams.txtMa = "";
          this.dtFiles = [];
          this.gParams.dNgayGiao = new Date();
          this.dtChiTietCT = [];
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnLamMoi() {
      try {       
        this.loading(true);
        let ds = await (
          await API.getDanhMucQLDN(this.axios, {
            nhanVienId: this.$auth.getNhanVienID(),
            kieu: 6,
            param1: "",
            param2: "",
          })
        ).data.data;
        this.treeLoaiPhieu = ds;
        await this.Load_DanhSach_DL();
      } catch (error) {
        console.log(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async tsbtnTraPhieu() {     
      if (!this.gParams.dtDanhSachRowChanged) {
        return this.$toast.error("Chưa chọn phiếu");
      }
      let dr = this.gParams.dtDanhSachRowChanged;
      this.$refs.frmTraPhieuVT.phieuId = parseInt(dr["PHIEU_ID"]);
      this.$refs.frmTraPhieuVT.openDialog();
    },
    async frmTraPhieuVTClosed(value) {
      if (value) {
        let vloaiphieu_id = this.gParams.treeRowSelected.LOAIPHIEU_ID;
        this.treeLoaiPhieu = await (
          await API.getDsLoaiPhieu(this.axios, {
            nhanVienId: this.$auth.getNhanVienID(),
            kieu: 6,
            param1: "",
            param2: "",
          })
        ).data.data;
        await this.Load_DanhSach_DL();
      }
    },

    lay_denghi_id_chon() {
      let dt  = this.dtDanhSach.filter(e=> this.PhieuChecked.includes(e.PHIEU_ID))
      let vphieu_ids = dt.map((e) => e.DENGHI_ID).join(",");
      if (!vphieu_ids) {
        return null;
      }
      return vphieu_ids;
    },
    lay_phieu_chon() {
      let dt  = this.dtDanhSach.filter(e=> this.PhieuChecked.includes(e.PHIEU_ID))
      let vphieu_ids = dt.map((e) => e.PHIEU_ID).join(";");
      if (!vphieu_ids) {
        return null;
      }
      return vphieu_ids;
    },
    lay_chungtu_phieu_chon() {
      let dt  = this.dtDanhSach.filter(e=> this.PhieuChecked.includes(e.PHIEU_ID))
      let vphieu_ids = dt.map((e) => e.CHUNGTU_ID).join(";");
      if (!vphieu_ids) {
        return null;
      }
      return vphieu_ids;
    },
    lay_phieu_chon_list() {
      let dt  = this.dtDanhSach.filter(e=> this.PhieuChecked.includes(e.PHIEU_ID))
      if (dt.length) {
        return dt.map((e) => e.PHIEU_ID);
      }
      return null;
    },
    async btnLayLaiOTP() {
      let vphieu_ids = this.lay_phieu_chon();
      if (!vphieu_ids) {
        return this.$toast.error("Chưa chọn phiếu");
      }
      await API.postSendOTPV2(this.axios, { phieuIds: vphieu_ids });
      this.$toast.success("Đã gửi OTP đến số máy người dùng");
    },
    async tsbtnXacNhan() {
      try {
        let kiemTraKhacThuKho = await this.KiemTraThuKho()
        if(kiemTraKhacThuKho){
          return 
        }

        this.isXacNhan = true
        let vphieu_ids = this.lay_phieu_chon();
        if (this.isNullOrEmpty(vphieu_ids)) {
          return this.$toast.error("Chưa chọn phiếu");
        }
        
        if (this.PHAILAM("QLVT_XACTHUC_KYSO")) {          
          if(this.PHAILAM("KT_HOANTHANH_CT")){
            let vdenghi_ids = this.lay_denghi_id_chon()
            let DS_ERROR = await ( await API.kiemTraChungTuKySo(this.axios, {
                data: vdenghi_ids
              })
            ).data.data;
            if(DS_ERROR.length > 0){
              var NameFile = "QLVT - Lỗi chứng từ ký số.xls"
              var data = xlsx.utils.json_to_sheet(DS_ERROR);  
              var wb = xlsx.utils.book_new();
              xlsx.utils.book_append_sheet(wb, data, "Sheet1");
              xlsx.writeFile(wb, NameFile);
              return this.$toast.error("Có lỗi khi xác nhận!");
            }
          }
          this.gParams.taikhoan_ks = await ( await API.getTaiKhoanKySo(this.axios,[])).data.data;        
          this.$bvModal.show('popupNhapUser')
        }else{
          if(!this.gParams.txtMaOTPChon){
            return this.$toast.error("Chưa nhập mã OTP");
          }          
          this.loading(true);
          let vkq = await (
            await API.postXacNhanOtpDeNghiV2(this.axios, {
              phieuId: vphieu_ids,
              otp: this.gParams.txtMaOTPChon.trim(),
              nhanVienNhanId: 0,
              donViNhanId: 0,
              noiDung: this.gParams.textEdit1,
            })
          ).data.data;
          if (vkq == "OK") {
            this.$toast.success("Xác nhận thành công!");
            let vloaiphieu_id = this.gParams.treeRowSelected.LOAIPHIEU_ID;
            this.treeLoaiPhieu = await (
              await API.getDsLoaiPhieu(this.axios, {
                nhanVienId: this.$auth.getNhanVienID(),
                kieu: 6,
                param1: "",
                param2: "",
              })
            ).data.data;
            await this.Load_DanhSach_DL();
          } else {
            this.$toast.error(vkq);
          }            
        }
      }catch (error) {
        this.$toast.error(error.data.message)
      } finally {
        this.loading(false);
      }
    },
    async xacNhanKySoSmartCA(){
      this.$bvModal.hide('popupNhapUser')
      try{
        this.loading(true);
        let vphieu_ids = this.lay_phieu_chon();
        let vkq = await ( await API.xacNhanKySoSmartCA(this.axios, {
            taiKhoanKs: this.gParams.taikhoan_ks,
            matKhauKs: this.gParams.matkhau_ks,
            phieuIds: vphieu_ids,
            nhanVienNhanId: this.isXacNhan ? 0 : this.gParams.cboNhanVienXL,
            donViNhanId: this.isXacNhan ? 0 : this.gParams.cboDonVi,
            ndGiao: this.isXacNhan ? "" :  this.gParams.textEdit1
          })
        ).data.data;
        if (vkq == "OK") {
          this.$toast.success("Xác nhận thành công!");
          this.treeLoaiPhieu = await (await API.getDsLoaiPhieu(this.axios, {
            nhanVienId: this.$auth.getNhanVienID(),
            kieu: 6,
            param1: "",
            param2: "",
          })).data.data;          
        } else {
          this.$toast.error(vkq);
        }
      }catch (error) {
        this.$toast.error(error.data.message)
      } finally {
        await this.Load_DanhSach_DL();
        this.loading(false);
      } 
    },
    removeDuplicates(arr) {
        return arr.filter((item,
            index) => arr.indexOf(item) === index);
    },
    async KiemTraThuKho() {
      let DSPhieuGiao = this.dtDanhSach.filter(e=>this.PhieuChecked.includes(e.PHIEU_ID));
      let DSThuKhoGiao = []      
      for(let e of DSPhieuGiao){
        if(e.THUKHO != null && !DSThuKhoGiao.includes(e.THUKHO)){
          DSThuKhoGiao.push(e.THUKHO)
        }
      }
     
      if(DSThuKhoGiao.length == 0){
        return false
      }     
      if(DSThuKhoGiao.length > 1){
        this.$toast.error("Trong danh sách có thủ kho khác nhau!");
        return true
      }

      let NhanVienXL = this.cboNhanVienXL.filter(e=> e.NHANVIEN_ID == this.gParams.cboNhanVienXL)
      if(NhanVienXL.length == 0){
        this.$toast.error("Chưa chọn nhân viên xử lý!");
        return true
      }
      for(let THUKHO of DSThuKhoGiao){        
        if(NhanVienXL[0].TEN_NV != THUKHO){
          this.$toast.error("Nhân viên xử lý khác với thủ kho!");
          return true
        }
      }
      return false
      
    },
    async tsbtnGiaoPhieu() {
      try {
        
        this.isXacNhan = false // false là giao phiếu

        let kiemTraKhacThuKho = await this.KiemTraThuKho()
        if(kiemTraKhacThuKho){
          return 
        }

        let vphieu_ids = this.lay_phieu_chon();
        if (this.isNullOrEmpty(vphieu_ids)) {
          return this.$toast.error("Chưa chọn phiếu");
        }
        
        if (!this.PHAILAM("QLVT_XACTHUC_KYSO") && !this.gParams.txtMaOTPChon) {
          return this.$toast.error("Chưa nhập mã OTP");
        }
        if (this.isNullOrEmpty(this.gParams.cboDonVi)) {
          return this.$toast.error("Chưa chọn đơn vị nhận");
        }
        if (this.isNullOrEmpty(this.gParams.cboNhanVienXL)) {
          return this.$toast.error("Chưa chọn nhân viên nhận");
        }
        if(this.PHAILAM("QLVT_XACTHUC_KYSO")){
          this.gParams.taikhoan_ks = await ( await API.getTaiKhoanKySo(this.axios,[])).data.data;
          this.$bvModal.show('popupNhapUser')
        }else{          
          this.loading(true);
          let vnhanvien_nhan_id = parseInt(this.gParams.cboNhanVienXL);
          let vdonvi_nhan_id = parseInt(this.gParams.cboDonVi);
          let vnd_giao = this.gParams.textEdit1;
          let vkq = await (
            await API.postXacNhanOtpDeNghiV2(this.axios, {
              phieuId: vphieu_ids,
              otp: this.gParams.txtMaOTPChon.trim(),
              nhanVienNhanId: vnhanvien_nhan_id,
              donViNhanId: vdonvi_nhan_id,
              noiDung: vnd_giao,
            })
          ).data.data;
          if (vkq == "OK") {
          
            this.$toast.success("Xác nhận thành công!");

            this.treeLoaiPhieu = await (
              await API.getDsLoaiPhieu(this.axios, {
                nhanVienId: this.$auth.getNhanVienID(),
                kieu: 6,
                param1: "",
                param2: "",
              })
            ).data.data;
            await this.Load_DanhSach_DL();
          } else {
            this.$toast.error(vkq);
          }
        }
      } catch (error) {
        this.$toast.error(error.data.message)
      } finally {
        this.loading(false);
      }
    },
    async Giao_Ky_PhieuDeNghiQuyetToan(chungtu_ids) {
      let chungTuIds = chungtu_ids.split(";");
      chungTuIds.forEach(async (vct_id) => {
        let vhuonggiao_id_phieu = 0;
        let dsDeNghiQT = (
          await API.getDeNghiQTTheoChungTu(this.axios, {
            chungTuId: parseInt(vct_id),
            ttPhId: 1,
          })
        ).data.data;
        let drDN = dsDeNghiQT[0];
        let vquyettoan_id = parseInt(drDN["QUYETTOAN_ID"]);
        let vgiaophieu_id = parseInt(drDN["PHIEU_ID"]);
        let vloaiphieu_id = parseInt(drDN["LOAIPHIEU_ID"]);
        let vmaDNQT = drDN["MA_QT"];
        let vOTPGiao = drDN["OTP"];
        vhuonggiao_id_phieu = parseInt(drDN["HUONGGIAO_ID"]);
        let d = (
          await API.getDSHuongGiaoTiep(this.axios, {
            "huong-giao-id": vhuonggiao_id_phieu,
          })
        ).data.data;
        if (d.length) {
          let huonggiao_tieptheo_id = parseInt(d[0]["HUONGGIAO_ID"]);
          let vkq = await this.GiaoPhieu_KS(
            vOTPGiao,
            vgiaophieu_id,
            vhuonggiao_id_phieu,
            huonggiao_tieptheo_id,
            "Giao phiếu tự động",
            "Giao phiếu tự động",
            this.$auth.getDonViID(),
            this.$auth.getNhanVienID(),
            "",
            vloaiphieu_id,
            0,
            0,
            vquyettoan_id,
            vmaDNQT,
            vmaDNQT
          );
          let vOTPKy = await (
            await API.getOtpKyTuDong(this.axios, {
              kieu: 1,
              kieuId: vquyettoan_id,
              huongGiaoId: huonggiao_tieptheo_id,
            })
          ).data.data;
          if (vkq == "OK") {
            let vphieu_id_new = await (
              await API.getPhieuIdTheoQuyetToan(this.axios, {
                quyetToanId: vquyettoan_id,
              })
            ).data.data;
            let vkqKy = await (
              await API.postXacNhanOtpDeNghiV2(this.axios, {
                phieuId: vphieu_id_new,
                otp: vOTPKy,
                nhanVienNhanId: 0,
                donViNhanId: 0,
                noiDung: "[Ký cùng phiếu chứng từ]",
              })
            ).data.data;
          }
        }
      });
    },
    async GiaoPhieu_KS(
      ma_otp,
      giaophieu_id,
      huonggiao_id_phieu,
      huonggiao_id,
      noidung_giao,
      ghi_chu,
      donvi_id,
      nhanvien_nhan_id,
      fileJson,
      p_loaiphieu_id,
      denghi_id,
      chungtu_id,
      quyentoan_id,
      maphieu,
      sophieu
    ) {
      let result = await API.postGiaoPhieu(this.axios, {
        phieuGiaoId: giaophieu_id,
        huongGiaoId: huonggiao_id,
        ndGiao: noidung_giao,
        ghiChu: ghi_chu,
        otp: ma_otp,
        nhanVienId: this.$auth.getNhanVienID(),
        donViId: this.$auth.getDonViID(),
        donViNhanId: donvi_id,
        nhanVienNhanId: nhanvien_nhan_id,
        moTa: fileJson,
        themCks: 0,
      });
      return result.data.data;
    },
    async cboDonViChanged(value) {
      if (!this.gParams.cboDonVi) return;      
      this.cboNhanVienXL = []
      let donviId = this.gParams.cboDonVi;
      let dsNvTT = await (
        await API.getDSNVGiaoPhieuThaoTac(this.axios, {
          donViId: donviId,
          huongGiaoId: this.gParams.huonggiao_tieptheo_id,
        })
      ).data.data;
      this.cboNhanVienXL = dsNvTT;      
      if(this.cboNhanVienXL.length > 0){
        this.gParams.cboNhanVienXL = this.setFirstValue(
          this.cboNhanVienXL,
          "NHANVIEN_ID"
        );
      }
    
      if (this.dtDVNV_GP.length > 0 && this.dtDVNV_GP[0].NHANVIEN_ID != null) {
        this.gParams.cboNhanVienXL = parseInt(this.dtDVNV_GP[0].NHANVIEN_ID)
      }
      
    },
    async tsbtnHuyGiao() {
      try{
        if (this.gParams.rdoPhieuSelected != "2") {
          return this.$toast.error("Trạng thái phiếu không phù hợp để hủy giao!");
        }
        if (!this.gParams.dtDanhSachRowChanged) {
          return this.$toast.error("Bạn chưa chọn phiếu để hủy");
        }
        let isConfirm = await this.$bvModal.msgBoxConfirm(
          "Bạn có chắc chắn muốn hủy giao!",
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        );
        if (!isConfirm) {
          return;
        }
        this.loading(true)
        let dr = this.gParams.dtDanhSachRowChanged;
        let phieu_id = parseInt(dr["PHIEU_ID"]);
        let list_mota = [];
        if (this.PHAILAM("XOA_FILE")) {
          let mota_phieutrc = await (
            await API.getMota(this.axios, { phieuId: phieu_id })
          ).data.data;
          if (mota_phieutrc) {
            list_mota = JSON.parse(mota_phieutrc);
            list_mota.forEach((item) => {
              item.filename = item.url.substring(item.url.lastIndexOf("/") + 1);
            });
          }
        }
        let kq = await (
          await API.postHuyPhieu(this.axios, {
            phieuId: phieu_id,
          })
        ).data.data;
        if (kq != "OK") {
          return this.$toast.error(kq);
        }
        
        if (list_mota.length) {
          for(let e of list_mota){
            await API.deleteFileObjectStorage(this.axios, {
              fileSource: e.url,
            });
          }          
        }        
        this.$toast.success("Đã hủy giao phiếu thành công!");
        await this.Load_DanhSach_DL();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbnMenuTongHop() {
      let ma_bc = "";
      let swPhieu = parseInt(this.gParams.vloaiphieu_id);
      let url = "";
      switch (swPhieu) {
        case 9:
          ma_bc = "04-QLVT";
          url = `api/bao-cao/bao-cao-de-nghi-thanh-qt-vt?extend=pdf&quyetToanId=${this.gParams.vquyettoan_id}`;
          break;
        case 10:
          ma_bc = "02-QLVT-TH";
          url = `api/de-nghi-quyet-toan/bc-de-nghi-nhap-kho-qt?extend=pdf&quyetToanId=${this.gParams.vquyettoan_id}`;
          break;
      }
      if (ma_bc) {
        await this.getPdf(url, "");
      } else {
        return this.$toast.error(`Phiếu ${swPhieu} không có mẫu báo cáo!`);
      }
    },
    async tsbnMenuChiTiet() {
      let ma_bc = "";
      let swPhieu = parseInt(this.gParams.vloaiphieu_id);
      let url = "";
      switch (swPhieu) {
        case 9:
          ma_bc = "13-QLVT";
          url = `api/bao-cao/bao-cao-tong-hop-qt-vt-theo-dv?extend=pdf&quyetToanId=${this.gParams.vquyettoan_id}`;
          break;
        case 10:
          ma_bc = "05-QLVT";
          url = `api/de-nghi-quyet-toan/bc-chi-tiet-thu-hoi-nhap-kho?extend=pdf&quyetToanId=${this.gParams.vquyettoan_id}`;
          break;
      }
      if (ma_bc) {
        await this.getPdf(url, "");
      } else {
        return this.$toast.error(`Phiếu ${swPhieu} không có mẫu báo cáo!`);
      }
    },
    async getPdf(url, body) {
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/${url}`,
          method: "POST",
          responseType: "blob",
          data: body,
        }).then((response) => {
          this.previewPrint(response.data);
        });
      } catch (error) {
        let res = await JSON.parse(await error.data.text());
        this.$toast.error(res.message);
      } finally {
        this.loading(false);
      }
    },
    previewPrint(data) {
      var blobURL = window.URL.createObjectURL(
        new Blob([data], { type: "application/pdf" })
      );
      var iframe = document.createElement("iframe");
      document.body.appendChild(iframe);

      iframe.style.display = "none";
      iframe.src = blobURL;
      iframe.onload = function () {
        setTimeout(function () {
          iframe.focus();
          iframe.contentWindow.print();
        }, 1);
      };
    },
    onGridFilesRowChanged(value) {
      this.gParams.dtFileRowChanged = value;
    },
    async onGridFilesCommand(args) {
      var checkFile = (await API.checkFileExist(this.axios,{fileSource : args.rowData.url}))
      let linkFile = ""
      if(checkFile.data.message == "Success" && checkFile.data.data.data){  
        // Download file minio        
        var getLink = (await API.getLinkFile(this.axios,{fileSource : args.rowData.url}))        
        linkFile = getLink.data.data.name   
        // await this.onDownloadFileHandler(args.rowData.url)        
      }else{
        //Download file DHSX
        linkFile = this.URL + args.rowData.url             
      }
      const downloadLink = document.createElement("a");      
      downloadLink.href = linkFile;
      const filePath = args.rowData.url.split('/');
      const fileName = filePath[filePath.length - 1];
      downloadLink.download = fileName;    
      downloadLink.target = "_blank";
      downloadLink.click(); 
    },
   
    async onDownloadFileHandler(value) {
      if(value) {
        const data = (await API.downloadFile(this.axios,value)).data;
        if(data) {         
          const filePath= value.split('/');
          const linkSource = `data:application/octet-stream;base64,${data.data.body.data}`;
          const downloadLink = document.createElement("a");
          const fileName = filePath[filePath.length - 1];
          downloadLink.href = linkSource;
          downloadLink.download = fileName;   
          downloadLink.click();
        }
      }
    },
    async DOWLOAD_OPEN(isOpen) {
      let url = this.vurl_luoi;
      console.log(url , " = url")
      // var filename = this.vname;
      // try {
      //   this.loading(true);
      //   const baseUrl = process.env.API;
      //   await this.axios({
      //     url: `${baseUrl}/web-qlvt/api/minioStorage/downloadFile?fileSource=${url}`,
      //     method: "GET",
      //     responseType: "blob",
      //     data: {},
      //   }).then((response) => {
      //     this.previewPrint(response.data);
      //   });
      // } catch (error) {
      //   let res = await JSON.parse(await error.data.text());
      //   this.$toast.error(res.message);
      // } finally {
      //   this.loading(false);
      // }
    },
  },
  watch: {
    PhieuChecked: function (value) {   
      let dt  = this.dtDanhSach.filter(e=> this.PhieuChecked.includes(e.PHIEU_ID))  
      this.PhieuChecked = value      
    },
    checkAllPhieu: function (value) {    
      this.PhieuChecked = []
      if(value){
        for(let e of this.$refs.gridDanhSach.filteredDataSource){
          this.PhieuChecked.push(e.PHIEU_ID)
        }
      }
    }
  },
  created: async function () {
    try{      
      this.loading(true)
      this.visibled.colSoPhieu = false;
      this.visibled.colKhoGiao = false;
      this.visibled.colKhoNhan = false;
      this.visibled.colTongTien = false;
      this.visibled.colNgay_CT = true;
      this.treeLoaiPhieu = (
        await API.getDsLoaiPhieu(this.axios, {
          nhanVienId: this.$auth.getNhanVienID(),
          kieu: 6,
          param1: "",
          param2: "",
        })
      ).data.data;
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
  async mounted() {
    
  },
};
</script>

<style src="./DSPhieuGiao.scss">
  
</style>