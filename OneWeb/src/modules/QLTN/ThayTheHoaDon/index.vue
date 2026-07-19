
<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import API from "../api/HoaDonDienTuAPI";
import { convertMoney } from "@/utils/convertMoney";
import xml2js from 'xml2js'
import moment from "moment"

export default {
  components: {},
  filters: {
    formatInvNo: function (value) {
      return !value ? "00000000" : value.toString().padStart(8, 0);
    },
  },
  data() {
    return {
      header: {
        title: "THAY THẾ HÓA ĐƠN GIÁ TRỊ GIA TĂNG",
        list: [
          { name: "Hóa đơn", link: { name: "Ui.cards" } },
          {
            name: "Hóa đơn điện tử",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Thay thế hóa đơn",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dsMauSo: [],
      dsKyHieu: [],
      dsKyHieu_moi: [],
      sohoadon: "",
      pattern: "",
      seri: "",
      pattern_moi: "",
      seri_moi: "",
      invToken: "",
      fkey: "",
      fkey_new: "",
      actionCapNhat: false,
      objHoaDonDienTu: {
        tenHD: "",
        mauHoaDon: "",
        kyHieu: "",
        soHoaDon: 1,
        MST: "",
        tenDV: "",
        diaChiDV: "",
        tenKH: "",
        diaChiKH: "",
        MSTKH: "",
        soDT: "",
        maKH: "",
        HTTT: "",
        kyHD: "",
        tiLeThue: 0,
        exTra: "",
        products: [],
        tongTien: 0,
        tongThue: 0,
        tongTra: 0,
        tienChu: "",
        fileName: "",
        ghiChu: "",
        nganHang: "",
        soTaiKhoan: "",
        maKhachHang: "",
        maNVBH: ""
      },
      first: true,
      editSettings: { allowEditing: true, mode: "Batch" },
      attachFile: "",
      mauSo_Cu: "",
      mauSo_Moi: "",
      tt_nd: null,
      OldobjHoaDonDienTu: null,
      NewobjHoaDonDienTu: null,
      dsBDHDDT: [],
      json_update:{},
      the_NVBH: false,
      the_makh: false
    };
  },
  validations: {},
  watch: {
    pattern: {
      handler: async function (value) {
        if (value) {
          this.fetchKyHieu(value);
          const selectPattern = this.dsMauSo.find(
            (item) => item.PATTERN_ID == value
          );
          if (selectPattern) {
            this.mauSo_Cu = selectPattern.PATTERN;
          }
        } else {
          this.dsKyHieu = [];
          this.seri = "";
        }
      },
    },
    pattern_moi: {
      handler: async function (value) {
        if (value) {
          this.fetchKyHieu_Moi(value);
          const selectPattern_Moi = this.dsMauSo.find(
            (item) => item.PATTERN_ID == value
          );
          if (selectPattern_Moi) {
            this.mauSo_Moi = selectPattern_Moi.PATTERN;
          }
        } else {
          this.dsKyHieu_moi = [];
          this.seri_moi = "";
        }
      },
    },
    "objHoaDonDienTu.tiLeThue": {
      handler: async function (value) {
        if (this.first) return;
        let newValue = 0;
        if (value) newValue = value;
        this.objHoaDonDienTu.tongThue = Math.round(
          (parseInt(this.objHoaDonDienTu.tongTien) * parseInt(newValue)) / 100
        );
        this.objHoaDonDienTu.tongTra =
          parseInt(this.objHoaDonDienTu.tongThue) +
          parseInt(this.objHoaDonDienTu.tongTien);
        this.objHoaDonDienTu.tienChu = convertMoney(
          this.objHoaDonDienTu.tongTra
        );
      },
    },
    "objHoaDonDienTu.tongTien": {
      handler: async function (value) {
        if (this.first) return;
        let newValue = 0;
        if (value) newValue = value;
        this.objHoaDonDienTu.tongThue = Math.round(
          (newValue * parseInt(this.objHoaDonDienTu.tiLeThue)) / 100
        );
        this.objHoaDonDienTu.tongTra =
          parseInt(this.objHoaDonDienTu.tongThue) + parseInt(newValue);
        this.objHoaDonDienTu.tienChu = convertMoney(
          this.objHoaDonDienTu.tongTra
        );
      },
    },
    "objHoaDonDienTu.tongThue": {
      handler: async function (value) {
        if (this.first) return;
        let newValue = 0;
        if (value) newValue = value;
        let tmpTongTien = this.objHoaDonDienTu.tongTien || 0;
        this.objHoaDonDienTu.tongTra =
          parseInt(newValue) + parseInt(tmpTongTien);
        this.objHoaDonDienTu.tienChu = convertMoney(
          this.objHoaDonDienTu.tongTra
        );
      },
    },
    "objHoaDonDienTu.tongTra": {
      handler: async function (value) {
        if (this.first) return;
        this.objHoaDonDienTu.tienChu = convertMoney(value);
      },
    },
    "objHoaDonDienTu.products": {
      handler: async function (value) {
        if (this.first) return;
        this.objHoaDonDienTu.tongTien = value.reduce((count, item) => {
          return count + parseInt(item.Total || 0);
        }, 0);
        // this.objHoaDonDienTu.tongTra = value.reduce((count, item) => {
        //   return count + parseInt(item.Amount || 0);
        // }, 0);
      },
    },
  },
  created() {
    this.fetchMauSo();
    this.fetchTTND();
    //load tham so md
    this.fetchThamSoMD("HDDT_THAYTHE_MA_NVBH")
    this.fetchThamSoMD_MAKH("INV_THE_MA_KH")
  },
  mounted() {},
  methods: {
    async fetchThamSoMD(ts){
        const res = await API.lay_ds_thamso_md_ds_mats(this.axios, ts);
        if (res.data.error_code === 'BSS-00000000' && res.data.data !== undefined && res.data.data.length > 0) {
            let ts = parseInt(res.data.data[0].GIATRI)
            if (ts === 1) this.the_NVBH = true
                else this.the_NVBH = false
        } else {
            this.the_NVBH = false
        }
    },
    async fetchThamSoMD_MAKH(ts){
        const res = await API.lay_ds_thamso_md_ds_mats(this.axios, ts);
        if (res.data.error_code === 'BSS-00000000' && res.data.data !== undefined && res.data.data.length > 0) {
            let ts = parseInt(res.data.data[0].GIATRI)
            if (ts === 1) this.the_makh = true
                else this.the_makh = false
        } else {
            this.the_makh = false
        }
    },
    async fetchTTND(){
        try {
            this.tt_nd = await this.$root.token.getThongTinNguoiDung();
        } catch (error) {

        }
    },
    onDeleteRowHandler(e) {
      this.$confirm(`Bạn thật sự muốn xóa sản phẩm này?`, "Xác nhận", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy",
      })
        .then(() => {
          this.xoaSanPham(e.STT);
        })
        .catch(() => {});
    },
    xoaSanPham(i) {
      this.objHoaDonDienTu.products = this.objHoaDonDienTu.products.filter(
        (item) => item.STT !== i
      );
    },
    async fetchMauSo() {
      this.loading(true);
      try {
        const response = await API.getDsPattern(this.axios);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsMauSo = response.data.data;
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchKyHieu(value) {
      this.loading(true);
      try {
        const response = await API.getDsSerial(this.axios, value);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsKyHieu = response.data.data;
          this.seri = ""; //response.data.data[0].SERI;
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchKyHieu_Moi(value) {
      this.loading(true);
      try {
        const response = await API.getDsSerial(this.axios, value);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsKyHieu_moi = response.data.data;
          this.seri_moi = ""; //response.data.data[0].SERI;
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchChiTietHoaDon(token) {
      const data = {
        invToken: token,
        phanvung_id: this.$auth.getPhanVungID(),
      };
      this.loading(true);
      try {
        const response = await API.getChiTietHoaDon(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.hasOwnProperty("Invoice")) {
            const result = response.data.data.Invoice.Content;
            this.objHoaDonDienTu.tenHD = result.InvoiceName;
            this.objHoaDonDienTu.mauHoaDon = result.InvoicePattern;
            this.objHoaDonDienTu.kyHieu = result.SerialNo;
            this.objHoaDonDienTu.soHoaDon = result.InvoiceNo;
            this.objHoaDonDienTu.MST = result.ComTaxCode;
            this.objHoaDonDienTu.tenDV = result.ComName;
            this.objHoaDonDienTu.diaChiDV = result.ComAddress;
            this.objHoaDonDienTu.tenKH = result.CusName;
            this.objHoaDonDienTu.diaChiKH = result.CusAddress;
            this.objHoaDonDienTu.MSTKH = result.CusTaxCode || "";
            this.objHoaDonDienTu.soDT = result.CusPhone;
            this.objHoaDonDienTu.maKH = result.CusCode;
            this.objHoaDonDienTu.nganHang = result.CusBankName || "";
            this.objHoaDonDienTu.soTaiKhoan = result.CusBankNo || "";
            this.objHoaDonDienTu.HTTT = result.Kind_of_Payment;
            this.objHoaDonDienTu.kyHD = result.KindOfService;
            this.objHoaDonDienTu.tiLeThue = result.VAT_Rate;
            this.objHoaDonDienTu.exTra = result.Extra;
            if(!Array.isArray(result.Products.Product)){
                this.objHoaDonDienTu.products = []
                let prd = {...result.Products.Product,STT:1}
                this.objHoaDonDienTu.products[0] = prd
            } else {
                this.objHoaDonDienTu.products = result.Products.Product.map((item, index) => ({ ...item, STT: ++index }));
            }
            this.objHoaDonDienTu.tongTien = result.Total;
            this.objHoaDonDienTu.tongThue = result.VAT_Amount;
            this.objHoaDonDienTu.tongTra = result.Amount;
            let tienBangChu = convertMoney(this.objHoaDonDienTu.tongTra);
            this.objHoaDonDienTu.tienChu = tienBangChu;

            this.OldobjHoaDonDienTu = this.formatHoaDonDienTu(this.objHoaDonDienTu)
          } else if(response.data.data.hasOwnProperty("DLHDon")) {
            const result = response.data.data.DLHDon;
            this.objHoaDonDienTu.tenHD = result.TTChung.THDon;
            this.objHoaDonDienTu.mauHoaDon = result.TTChung.KHMSHDon;
            this.objHoaDonDienTu.kyHieu = result.TTChung.KHHDon;
            this.objHoaDonDienTu.soHoaDon = result.TTChung.SHDon;
            this.objHoaDonDienTu.MST = result.NDHDon.NBan.MST;
            this.objHoaDonDienTu.tenDV = result.NDHDon.NBan.Ten;
            this.objHoaDonDienTu.diaChiDV = result.NDHDon.NBan.DChi;
            this.objHoaDonDienTu.tenKH = result.NDHDon.NMua.Ten;
            this.objHoaDonDienTu.diaChiKH = result.NDHDon.NMua.DChi;
            this.objHoaDonDienTu.MSTKH = result.NDHDon.NMua.MST || "";
            this.objHoaDonDienTu.soDT = result.NDHDon.NMua.SDThoai || "";
            this.objHoaDonDienTu.maKH = result.NDHDon.NMua.MKHang;
            this.objHoaDonDienTu.maKhachHang = result.NDHDon.NMua.TTKhac && result.NDHDon.NMua.TTKhac.TTin && result.NDHDon.NMua.TTKhac.TTin.TTruong == 'MaKhachHang' ? result.NDHDon.NMua.TTKhac.TTin.DLieu : "";
            this.objHoaDonDienTu.nganHang = result.NDHDon.NMua.TNHang || "";
            this.objHoaDonDienTu.soTaiKhoan = result.NDHDon.NMua.STKNHang || "";
            this.objHoaDonDienTu.HTTT = result.TTChung.HTTToan;
            if(Array.isArray(result.NDHDon.NBan.TTKhac)){
                if(Array.isArray(result.NDHDon.NBan.TTKhac[0].TTin)){
                    this.objHoaDonDienTu.kyHD = result.NDHDon.NBan.TTKhac && result.NDHDon.NBan.TTKhac[0].TTin && result.NDHDon.NBan.TTKhac[0].TTin[0].TTruong == 'KindOfService' ? result.NDHDon.NBan.TTKhac[0].TTin[0].DLieu : "";
                    this.objHoaDonDienTu.maNVBH = result.NDHDon.NBan.TTKhac && result.NDHDon.NBan.TTKhac[0].TTin && result.NDHDon.NBan.TTKhac[0].TTin[1].TTruong == 'Teller' ? result.NDHDon.NBan.TTKhac[0].TTin[1].DLieu : "";
                } else {
                    this.objHoaDonDienTu.kyHD = result.NDHDon.NBan.TTKhac && result.NDHDon.NBan.TTKhac[0].TTin && result.NDHDon.NBan.TTKhac[0].TTin.TTruong == 'KindOfService' ? result.NDHDon.NBan.TTKhac[0].TTin.DLieu : "";
                }
            } else this.objHoaDonDienTu.kyHD = result.NDHDon.NBan.TTKhac && result.NDHDon.NBan.TTKhac.TTin && result.NDHDon.NBan.TTKhac.TTin[0].TTruong == 'KindOfService' ? result.NDHDon.NBan.TTKhac.TTin[0].DLieu : "";

            try {
              const tSuat = result.NDHDon.TToan.THTTLTSuat.LTSuat[0].TSuat || "";
              if (tSuat == "KKKNT") this.objHoaDonDienTu.tiLeThue = -2;
              else if (tSuat == "KCT") this.objHoaDonDienTu.tiLeThue = -1;
            //   else if (tSuat.includes("KHAC:1")) this.objHoaDonDienTu.tiLeThue = -1;
            //   else if (tSuat.includes("KHAC:2")) this.objHoaDonDienTu.tiLeThue = -2;
              else if (tSuat.includes("KHAC:")) this.objHoaDonDienTu.tiLeThue = 10;
              else this.objHoaDonDienTu.tiLeThue = tSuat.substring(0,tSuat.length - 1);
            } catch (error) {
              this.objHoaDonDienTu.tiLeThue = 0;
            }
            this.objHoaDonDienTu.exTra = result.TTChung.TTKhac ? result.TTChung.TTKhac.TTin.DLieu : "";
            this.objHoaDonDienTu.tongTien = result.NDHDon.TToan.TgTCThue;
            this.objHoaDonDienTu.tongThue = result.NDHDon.TToan.TgTThue;
            this.objHoaDonDienTu.tongTra = result.NDHDon.TToan.TgTTTBSo;
            // let tienBangChu = convertMoney(this.objHoaDonDienTu.tongTra);
            // this.objHoaDonDienTu.tienChu = tienBangChu;
            this.objHoaDonDienTu.tienChu = result.NDHDon.TToan.TgTTTBChu

            let ar_product = [];
            let vat_amout = "";
            let tan_suat = "";
            let product = {};
            let count_t = result.NDHDon.DSHHDVu.HHDVu;
            for (let i = 0; i < count_t.length; i++) {
              product.Amount = count_t[i].TTKhac.TTin[0].DLieu;
              product.Code = count_t[i].MHHDVu ? count_t[i].MHHDVu : "";
              product.Discount = 0;
              product.DiscountAmount = 0;
              product.ProdName = count_t[i].THHDVu;
              product.ProdPrice = count_t[i].DGia;
              product.ProdQuantity = count_t[i].SLuong;
              product.ProdUnit = count_t[i].DVTinh ? count_t[i].DVTinh : "";
              product.Total = count_t[i].ThTien;
              product.STT = count_t[i].STT;

              vat_amout = count_t[i].TTKhac.TTin;
              if (vat_amout.length > 1) {
                product.VATAmount = count_t[i].TTKhac.TTin[1].DLieu;
              } else {
                product.VATAmount = 0;
              }

              tan_suat = count_t[i].TSuat;
              if (tan_suat == "KKKNT") product.VATRate = -2;
              else if (tan_suat == "KCT") product.VATRate = -1;
              else if (tan_suat.includes("KHAC:")) product.VATRate = 10;
              else product.VATRate = tan_suat.substring(0, tan_suat.length - 1);

              ar_product.push({
                STT: product.STT,
                Code: product.Code,
                Remark: "",
                Total: product.Total,
                ProdName: product.ProdName,
                ProdUnit: product.ProdUnit,
                ProdQuantity: product.ProdQuantity,
                ProdPrice: Number(product.ProdPrice),
                Discount: product.Discount,
                DiscountAmount: product.DiscountAmount,
                VATRate: Number(product.VATRate),
                VATAmount: product.VATAmount,
                Amount: Number(product.Amount),
              });
            }
            this.objHoaDonDienTu.products = ar_product;
            this.OldobjHoaDonDienTu = this.formatHoaDonDienTu(this.objHoaDonDienTu)

          } else {
            this.$root.toastError("Không có thông tin hóa đơn!");
            return;
          }
          this.actionCapNhat = true;
          this.first = await false;
        }
      } catch (error) {
        this.$root.toastError(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchThayTheHD() {
      const products = this.objHoaDonDienTu.products.map((item) => ({
        code: item.Code,
        remark: item.Remark,
        total: item.Total,
        prodName: item.ProdName,
        prodUnit: item.ProdUnit,
        prodQuantity: item.ProdQuantity,
        prodPrice: item.ProdPrice,
        discount: item.Discount,
        discountAmount: item.DiscountAmount,
        vat_rate: item.VATRate,
        vat_amount: item.VATAmount,
        amount: item.Amount,
      }));
      this.loading(true);
      try {
        const data = {
          phanvung_id: this.$auth.getPhanVungID(),
          pattern: this.mauSo_Moi,
          serial: this.seri_moi,
          fkey: this.fkey,
          attachFile: this.attachFile,
          convert: 0,
          type: "",
          fkey_new: new Date().getTime().toString(),
          cusCode: this.objHoaDonDienTu.maKH,
          cusName: this.objHoaDonDienTu.tenKH,
          cusAddress: this.objHoaDonDienTu.diaChiKH,
          cusPhone: this.objHoaDonDienTu.soDT,
          cusTaxCode: this.objHoaDonDienTu.MSTKH,
          kind_of_Payment: this.objHoaDonDienTu.HTTT,
          kindOfService: this.objHoaDonDienTu.kyHD,
          vthem_extra: this.objHoaDonDienTu.exTra.trim() == "" ? 0 : 1,
          total: this.objHoaDonDienTu.tongTien,
          vatrate: this.objHoaDonDienTu.tiLeThue,
          vatamount: this.objHoaDonDienTu.tongThue,
          amount: this.objHoaDonDienTu.tongTra,
          amount_words: this.objHoaDonDienTu.tienChu,
          ma_nv: this.$auth.getMaNhanVien(),
          products: products,
        };
        const response = await API.fetchReplaceInvAction(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess(
            "Thay thế hóa đơn trên hệ thống hóa đơn điện tử thành công !"
          );
          this.resetThongTin();
        }
      } catch (error) {
        this.$root.toastError("Có lỗi trong quá trình thay thế hóa đơn: " + error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async editGridSanPham(arg) {
      let KEY = arg.rowData.STT;
      if (arg.columnName == "STT") {
        if (arg.value) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.STT = arg.value;
              return;
            }
          });
        }
      }
      if (arg.columnName == "ProdName") {
        if (arg.value) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.ProdName = arg.value;
              return;
            }
          });
        }
      }
      if (arg.columnName == "ProdUnit") {
        if (arg.value) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.ProdUnit = arg.value;
              return;
            }
          });
        }
      }
      if (arg.columnName == "ProdQuantity") {
        if (arg.value) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.ProdQuantity = arg.value;
              element.Total =
                parseInt(arg.value) * parseInt(element.ProdPrice);
              element.VATAmount = Math.round(parseInt(arg.value) * parseInt(element.ProdPrice) * parseInt(element.VATRate) / 100 )
              element.Amount =
                (parseInt(arg.value) * parseInt(element.ProdPrice)) + parseInt(element.VATAmount);
              return;
            }
          });
        }
      }
      if (arg.columnName == "ProdPrice") {
        if (arg.value) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.ProdPrice = arg.value;
              element.Total =
                parseInt(arg.value) * parseInt(element.ProdQuantity);
              element.VATAmount = Math.round(parseInt(arg.value) * parseInt(element.ProdQuantity) * parseInt(element.VATRate) / 100 )
              element.Amount =
                (parseInt(arg.value) * parseInt(element.ProdQuantity)) + parseInt(element.VATAmount);
              return;
            }
          });
        }
      }
      if (arg.columnName == "Total") {
        if (arg.value) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.Total = arg.value;
              return;
            }
          });
        }
      }
      if (arg.columnName == "VATRate") {
        if (arg.value || arg.value == 0) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.VATRate = arg.value;
              if(arg.value < 0 && arg.value != -1 && arg.value != -2) element.VATRate = 0
              if(arg.value <= 0){
                element.VATAmount = 0
              } else {
                element.VATAmount = Math.round(parseInt(arg.value) * parseInt(element.ProdQuantity) * parseInt(element.ProdPrice) / 100 )
              }
              element.Amount =
                (parseInt(element.ProdPrice) * parseInt(element.ProdQuantity)) + parseInt(element.VATAmount);
              return;
            }
          });
        }
      }
      if (arg.columnName == "VATAmount") {
        if (arg.value || arg.value == 0) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.VATAmount = arg.value;
              element.Amount =
                (parseInt(element.ProdPrice) * parseInt(element.ProdQuantity)) + parseInt(arg.value);
              return;
            }
          });
        }
      }
      if (arg.columnName == "Amount") {
        if (arg.value) {
          this.objHoaDonDienTu.products.forEach((element) => {
            if (element.STT == KEY) {
              element.Amount = arg.value;
              return;
            }
          });
        }
      }

      this.objHoaDonDienTu.products = [...this.objHoaDonDienTu.products];
    },
    TraCuu() {
      this.first = true;
      this.objHoaDonDienTu.ghiChu = "";
      this.objHoaDonDienTu.fileName = "";
      if (this.pattern == "") {
        this.$root.toastError("Chưa chọn mẫu hóa đơn !");
        return;
      }
      if (this.seri == "") {
        this.$root.toastError("Chưa chọn ký hiệu !");
        return;
      }
      if (this.sohoadon == "0" || this.sohoadon == "") {
        this.$root.toastError("Chưa nhập số hóa đơn !");
        return;
      }
      if (this.pattern_moi == "") {
        this.$root.toastError("Chưa chọn mẫu hóa đơn mới !");
        return;
      }
      if (this.seri_moi == "") {
        this.$root.toastError("Chưa chọn ký hiệu mới !");
        return;
      }
      this.invToken = `${this.mauSo_Cu};${this.seri};${parseInt(this.sohoadon)}`;
      if (this.sohoadon == "0" || this.sohoadon == "") {
        this.invToken = `${this.mauSo_Cu};${this.seri};0`;
      }
      // fetch hóa đơn
      this.fetchChiTietHoaDon(this.invToken);
    },
    async api_CapNhat(json)
    {
        if(this.dsBDHDDT.length == 0)
        {
            this.json_update.js_biendong = {}
        }

        const res = await API.CapNhat(this.axios, {
            "p_ds_para": JSON.stringify(json)
        })
        let dsach = res.data.data ? res.data.data : [];
        let danhsach = JSON.parse(dsach.js_returnds)
        return danhsach
    },
    async ThayTheHD() {
      if (this.tt_nd && !this.tt_nd.user_hddt_td) {
        this.$root.toastError("Người dùng chưa được cấp tài khoản thay thế hóa đơn !");
        return;
      }
      const check = await this.kiemTraDuLieu();
      if (check) {
        // this.fetchThayTheHD();
        this.loading(true)
        this.NewobjHoaDonDienTu = this.formatHoaDonDienTu(this.objHoaDonDienTu)
        let kq = ""
        kq = await this.ReplaceInvoiceAction(this.tt_nd.pass_hddt_td,this.tt_nd.user_hddt_td,"11",this.fkey,this.mauSo_Moi,this.seri_moi,this.GetInvoiceXml_ThayThe(this.objHoaDonDienTu))
        let result = kq.split(':');
        if(result[0] == "OK"){
            this.loading(false)
            this.$root.toastSuccess("Thay thế hóa đơn trên hệ thống hóa đơn điện tử thành công !");
            let result1 = result[1].split(';');
            this.NewobjHoaDonDienTu.InvoicePattern = result1[0]
            this.NewobjHoaDonDienTu.SerialNo = result1[1]

            let result3 = result1[2].split('_')
            this.NewobjHoaDonDienTu.InvoiceNo = result3[1]
            this.fkey_new = result3[0]

            await this.TaoDuLieuBD(this.OldobjHoaDonDienTu,this.NewobjHoaDonDienTu,0)

            this.json_update.js_biendong = this.dsBDHDDT
            this.json_update.js_ct_biendong = []
            // this.json_update.js_ct_biendong = ""
            // this.json_update.hddt_fkey = this.fkey_new

            let abc = await this.api_CapNhat(this.json_update)
            if(abc.ERROR_CODE == 1)
            {
            this.$root.toastSuccess("Cập nhật thông tin trên dữ liệu tỉnh thành công  !");
            this.resetThongTin();
            }
        } else {
            this.loading(false)
            this.$root.toastError("Có lỗi trong quá trình thay thế hóa đơn: " + kq)
            return
        }
      }
    },
    async ReplaceInvoiceAction(acPass,account,attachFile,fkey,pattern,serial,xmlInvData)
    {
        const res = await API.replaceInvoiceAction(this.axios, {
            "acPass": acPass,
            "account": account,
            "attachFile": attachFile,
            "convert": 0,
            "fkey": fkey,
            "pattern": pattern,
            "serial": serial,
            "xmlInvData": xmlInvData
        })
        let kq =  res.data.data ? res.data.data : res.data.faultString
        return kq
    },
    GetInvoiceXml_ThayThe(obj)
    {
        this.fkey_new = new Date().getTime().toString()
        let xml = '<ReplaceInv>'
        xml += `<key>${this.fkey_new}</key>`
        xml += `<CusCode>${obj.maKH}</CusCode>`
        if(this.the_makh) {
            xml += `<MaKhachHang>${obj.maKhachHang}</MaKhachHang>`
        }
        xml += `<CusName><![CDATA[${obj.tenKH}]]></CusName>`
        xml += `<CusAddress><![CDATA[${obj.diaChiKH}]]></CusAddress>`
        xml += `<CusPhone><![CDATA[${obj.soDT}]]></CusPhone>`
        if(obj.MSTKH)
        {
            xml += `<CusTaxCode><![CDATA[${obj.MSTKH}]]></CusTaxCode>`
        }
        else
        {
            xml += `<CusTaxCode></CusTaxCode>`
        }
        if(obj.nganHang) xml += `<CusBankName><![CDATA[${obj.nganHang}]]></CusBankName>`
        if(obj.soTaiKhoan) xml += `<CusBankNo><![CDATA[${obj.soTaiKhoan}]]></CusBankNo>`
        xml += `<PaymentMethod>${obj.HTTT}</PaymentMethod>`
        xml += `<KindOfService>${obj.kyHD}</KindOfService>`

        // if (this.vthem_extra)
        //     xml += `<Extra>${this.fkey_new}</Extra>`
        xml += '<Products>'
        let Products = obj.products
        for(let i =0;i < Products.length;i++)
        {
            xml += '<Product>'
            xml += `<ProdNo>${Products[i].STT}</ProdNo>`
            xml += `<IsSum>${Products[i].STT == 0 ? 4 : 0}</IsSum>`
            xml += `<ProdName><![CDATA[${Products[i].ProdName}]]></ProdName>`
            xml += `<ProdUnit>${Products[i].ProdUnit}</ProdUnit>`
            xml += `<ProdQuantity>${Products[i].ProdQuantity}</ProdQuantity>`
            if(Products[i].ProdPrice != null)
            {
                xml += `<ProdPrice>${Math.abs(Number(Products[i].ProdPrice))}</ProdPrice>`
            }
            else
            {
                xml += `<ProdPrice>${Products[i].ProdPrice}</ProdPrice>`
            }

            if(Products[i].Amount != null)
            {
                xml += `<Amount>${Math.abs(Number(Products[i].Amount))}</Amount>`
            }
            else
            {
                xml += `<Amount>${Products[i].Amount}</Amount>`
            }

            if(Products[i].Total != null)
            {
                if (Products[i].Total != 0)
                    xml += `<Total>${Math.abs(Number(Products[i].Total))}</Total>`
                else
                    xml += '<Total>0</Total>'
            }
            else
            {
                xml += `<Total>${Products[i].Total}</Total>`
            }


            if(Products[i].VATRate != null)
            {
                if (Products[i].VATRate != 0)
                    xml += `<VATRate>${Number(Products[i].VATRate)}</VATRate>`
                else
                    xml += '<VATRate>0</VATRate>'
            }
            else
            {
                xml += `<VATRate>${Products[i].VATRate}</VATRate>`
            }


            if(Products[i].VATAmount != null)
            {
                if (Products[i].VATAmount != 0)
                    xml += `<VATAmount>${Math.abs(Number(Products[i].VATAmount))}</VATAmount>`
                else
                    xml += '<VATAmount>0</VATAmount>'
            }
            else
            {
                xml += `<VATAmount>${Products[i].VATAmount}</VATAmount>`
            }

            xml +='</Product>'
        }

        xml +='</Products>'

        if(obj.tongTien != null)
        {
            xml += `<Total>${Math.abs(Number(obj.tongTien))}</Total>`
        }
        else
        {
            xml += `<Total>${obj.tongTien}</Total>`
        }

        xml += `<VATRate>${obj.tiLeThue}</VATRate>`

        if(obj.tongThue != null)
        {
            xml += `<VATAmount>${Math.abs(Number(obj.tongThue))}</VATAmount>`
        }
        else
        {
            xml += `<VATAmount>${obj.tongThue}</VATAmount>`
        }

        if(obj.tongTra != null)
        {
            xml += `<Amount>${Math.abs(Number(obj.tongTra))}</Amount>`
        }
        else
        {
            xml += `<Amount>${obj.tongTra}</Amount>`
        }

        xml += `<AmountInWords>${obj.tienChu}</AmountInWords>`

        if(obj.tiLeThue == -2) {
            xml += `<GrossValue_NonTax>${Math.abs(Number(obj.tongTien))}</GrossValue_NonTax>`
        }

        if(obj.tiLeThue == -1) {
            xml += `<GrossValue>${Math.abs(Number(obj.tongTien))}</GrossValue>`
        }

        if(obj.tiLeThue == 0) {
            xml += `<GrossValue0>${Math.abs(Number(obj.tongTien))}</GrossValue0>`
            xml += `<VatAmount0>${Math.abs(Number(obj.tongThue))}</VatAmount0>`
        }

        if(obj.tiLeThue == 5) {
            xml += `<GrossValue5>${Math.abs(Number(obj.tongTien))}</GrossValue5>`
            xml += `<VatAmount5>${Math.abs(Number(obj.tongThue))}</VatAmount5>`
        }

        if(obj.tiLeThue == 8) {
            xml += `<GrossValue8>${Math.abs(Number(obj.tongTien))}</GrossValue8>`
            xml += `<VatAmount8>${Math.abs(Number(obj.tongThue))}</VatAmount8>`
        }

        if(obj.tiLeThue == 10) {
            xml += `<GrossValue10>${Math.abs(Number(obj.tongTien))}</GrossValue10>`
            xml += `<VatAmount10>${Math.abs(Number(obj.tongThue))}</VatAmount10>`
        }

        xml +='<PaymentStatus>1</PaymentStatus>'
        if(this.the_NVBH){
            xml += `<Teller><![CDATA[${obj.maNVBH}]]></Teller>`
        }
        xml +='</ReplaceInv>'

        return xml
    },
    resetThongTin() {
      this.pattern = "";
      this.seri = "";
      this.sohoadon = 0;
      this.actionCapNhat = false;
      this.invToken = "";
      this.fkey = "";
      this.fkey_new = "";
      this.pattern_moi = "";
      this.seri_moi = "";
    },
    async kiemTraDuLieu() {
      const data = {
        cusCode: this.objHoaDonDienTu.maKH,
        fromDate: "",
        toDate: "",
        phanvung_id: this.$auth.getPhanVungID(),
      };
      try {
        const response = await API.getDanhSachHoaDon(this.axios, data);
        if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data.length > 0
        ) {
            const objKetQua = response.data.data.find((item) => item.invToken == this.invToken);
            this.fkey = objKetQua ? objKetQua.fkey : "";
            if (objKetQua && Number(objKetQua.status) != 1) {
            this.$root.toastError(
                "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể thay thế !"
            );
            return false;
            }
        }
      } catch (error) {
            this.$root.toastError(error.data.message);
            return false;
      }

      if (this.objHoaDonDienTu.maKH.toString().trim() == "") {
        this.$root.toastError("Mã khách hàng không để để trống");
        return false;
      }
      if (this.objHoaDonDienTu.tenKH.toString().trim() == "") {
        this.$root.toastError("Tên khách hàng không được để trống !");
        this.$refs.tenKH.focus();
        return false;
      }
      if (this.objHoaDonDienTu.diaChiKH.toString().trim() == "") {
        this.$root.toastError("Địa chỉ khách hàng không được để trống !");
        this.$refs.diaChiKH.focus();
        return false;
      }
      if (this.objHoaDonDienTu.products.length <= 0) {
        this.$root.toastError("Không có thông tin sản phẩm");
        return false;
      }
      return true;
    },
    async onChangeFile(e) {
      //   const files = e.target.files;
      this.objHoaDonDienTu.fileName = e.target.files[0].name;
      this.getBase64(e.target.files[0]);
    },
    getBase64(file) {
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.attachFile = reader.result;
      };
      reader.onerror = function (error) {
        console.log("Error: ", error);
      };
    },
    gridSanPhamCommand(arg) {
      this.onDeleteRowHandler(arg.rowData);
    },
    addSanPham() {
      const spTemp = {
        STT: this.objHoaDonDienTu.products.length + 1,
        Code: "",
        Remark: "",
        Total: 0,
        ProdName: "",
        ProdUnit: "",
        ProdQuantity: 0,
        ProdPrice: 0,
        Discount: 0,
        DiscountAmount: 0,
        VATRate: 0,
        VATAmount: 0,
        Amount: 0,
      };
      this.objHoaDonDienTu.products = [
        ...this.objHoaDonDienTu.products,
        spTemp,
      ];
    },
    formatHoaDonDienTu(objHoaDonDienTu){
      return {
        InvoiceName: objHoaDonDienTu.tenHD,
        InvoicePattern: objHoaDonDienTu.mauHoaDon,
        SerialNo: objHoaDonDienTu.kyHieu,
        InvoiceNo: objHoaDonDienTu.soHoaDon,
        ComTaxCode: objHoaDonDienTu.MST,
        ComName: objHoaDonDienTu.tenDV,
        ComAddress: objHoaDonDienTu.diaChiDV,
        CusName: objHoaDonDienTu.tenKH,
        CusAddress: objHoaDonDienTu.diaChiKH,
        CusTaxCode: objHoaDonDienTu.MSTKH,
        CusPhone: objHoaDonDienTu.soDT,
        CusCode: objHoaDonDienTu.maKH,
        CusBankName: objHoaDonDienTu.nganHang,
        CusBankNo: objHoaDonDienTu.soTaiKhoan,
        Kind_of_Payment: objHoaDonDienTu.HTTT,
        KindOfService: objHoaDonDienTu.kyHD,
        VAT_Rate: objHoaDonDienTu.tiLeThue,
        Extra: objHoaDonDienTu.exTra,
        Total: objHoaDonDienTu.tongTien,
        VAT_Amount: objHoaDonDienTu.tongThue,
        Amount: objHoaDonDienTu.tongTra,
        Products: objHoaDonDienTu.products,
        Amount_words: objHoaDonDienTu.tienChu
      }
    },
    GetXMLFromObject(objHoaDonDienTu) {
        let builder = new xml2js.Builder();
        let xml = builder.buildObject(objHoaDonDienTu);
        return xml;
    },
    BD_HDDT_DATA() {
        return {
            THANHTOAN_LOI: 0,
            SERI_MOI: '',
            SERI_CU: '',
            LOAIHOADON_MOI: '',
            LOAIHOADON_CU: '',
            TONGTIEN: 0,
            PHIEUTT_ID: 0,
            HDKH_ID: 0,
            FILE_DINHKEM: null,
            SO_HD_CU: '',
            GHICHU: '',
            NGAY_CN: null,
            NGUOI_CN: '',
            THONGTINHD_MOI: '',
            THONGTINHD_CU: '',
            KIEU_BD: 0,
            MA_TT: '',
            BDHDDT_ID: 0,
            TIENBANGCHU: '',
            MAY_CN: '',
            TIENGOC: 0,
            SO_HD_MOI: '',
            EMAIL: '',
            HINHTHUC_TT: '',
            LOAIHOADON_MOI_ID: 0,
            NHANVIEN_ID: 0,
            DONVI_ID: 0,
            FKEY: '',
            BDHDDT_CHA_ID: 0,
            SDT: '',
            TIEN: 0,
            MST: '',
            TEN_TT: '',
            KIEU_ID: 0,
            SERI_MOI_ID: 0,
            TYLE_VAT: 0,
            TRANGTHAI: 0,
            NGUOI_TD: '',
            NGAY_TD: null,
            DIACHI_TT: '',
            VAT: 0,
        }
    },
    async POST_GET_KEYS(keyname)
    {
        let key=''
        await API.POST_GET_KEYS(this.axios, {
            "keyname":keyname
        })
        .then((res) => {
            key = res.data.data ? res.data.data : [];
        })
        return key
    },
    async TaoDuLieuBD(objOld, objNew, _seri_id) {
      this.dsBDHDDT = [];
      let row = this.BD_HDDT_DATA();
      let BDHDDT_ID = await this.POST_GET_KEYS("BDHDDT_ID");
      row.BDHDDT_ID = BDHDDT_ID;
      row.MA_TT = objNew.CusCode;
      row.KIEU_BD = 2;  // Thay thế hóa đơn
      row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
      row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
      row.NGUOI_CN = await this.$root.token.getUserName();
      row.MAY_CN = await this.$root.token.getMachine();
      row.NGAY_CN = moment(new Date()).format("DD/MM/YYYY");
      row.GHICHU = this.objHoaDonDienTu.ghiChu

    //   if (this.txtBienBanDinhKem != "") {
    //     row.FILE_DINHKEM = this.array_byte;
    //   }

    //   row.DONVI_ID = this.$root.token.getDonViID();
    //   row.NHANVIEN_ID = this.$root.token.getNhanVienID();
    //   row.TEN_TT = this.txtTenKhachHang;
    //   row.DIACHI_TT = this.txtDiaChiKH;
    //   row.MST = this.txtMaSoThue;
    //   if (this.txtMaSoThue == null) {
    //     row.MST = "";
    //   }
    //   row.EMAIL = this.txtEmail;
    //   row.SDT = this.txtSoDienThoai;
    //   row.HINHTHUC_TT = this.txtHinhThucThanhToan;
    //   row.TIEN = this.txtCongTienDV;
    //   row.VAT = this.txtTienTHueGTGT;
    //   row.TYLE_VAT = this.txtTHueGTGT;
    //   row.TIENBANGCHU = this.txtTienVietBangChu;

       // Thay thế hóa đơn
        // row.KIEU_BD = 2;
        // row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
        // row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
        // row.TRANGTHAI = 1; // Đang sử dụng
        // row.BDHDDT_CHA_ID = this.bdhddt_cha_id;

    //   row.LOAIHOADON_CU = objOld.InvoicePattern;
    //   row.LOAIHOADON_MOI = objNew.InvoicePattern;
    //   row.SERI_CU = objOld.SerialNo;
    //   row.SERI_MOI = objNew.SerialNo;
    //   row.SO_HD_CU = objOld.InvoiceNo;
    //   row.SO_HD_MOI = objNew.InvoiceNo;

    //   row.HDKH_ID = this.vhdkh_id;
    //   row.SERI_MOI_ID = _seri_id;
    //   row.LOAIHOADON_MOI_ID = this.ddlMauSo;
    //   row.PHIEUTT_ID = this.vphieutt_id;

      this.dsBDHDDT.push(row);
    },
  },
  computed: {},
};
</script>
