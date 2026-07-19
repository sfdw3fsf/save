<template src="./index.html"></template>
<style scoped src="./style.scss"></style>
<script>

import KyCuoc2 from '@/components/KyCuoc2'
import breadcrumb from "@/components/breadcrumb";
import { export_json_to_excel } from "../TraCuuDichVu/Export2Excel";
import moment from "moment";
import Vue from "vue";
const isNumeric = (str) => {
  if (!isNaN(str)) return true;
  if (typeof str != "string") return false;
  return !isNaN(str) && !isNaN(parseFloat(str));
};

const Space = (len) => {
  return "".padStart(len, " ");
};

const CatChuoi = (sChuoi, iDoDai) => {
  if (!!sChuoi) {
    sChuoi = sChuoi.trim();
    if (sChuoi.length > iDoDai) {
      sChuoi = sChuoi.substring(0, iDoDai);
    }
  }
  return sChuoi;
};

var DOCSO = (function () {
  var t = ["không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"],
    r = function (r, n) {
      var o = "",
        a = Math.floor(r / 10),
        e = r % 10;
      return (
        a > 1 ? ((o = " " + t[a] + " mươi"), 1 == e && (o += " mốt")) : 1 == a ? ((o = " mười"), 1 == e && (o += " một")) : n && e > 0 && (o = " lẻ"),
        5 == e && a >= 1 ? (o += " lăm") : 4 == e && a >= 1 ? (o += " tư") : (e > 1 || (1 == e && 0 == a)) && (o += " " + t[e]),
        o
      );
    },
    n = function (n, o) {
      var a = "",
        e = Math.floor(n / 100),
        n = n % 100;
      return o || e > 0 ? ((a = " " + t[e] + " trăm"), (a += r(n, !0))) : (a = r(n, !1)), a;
    },
    o = function (t, r) {
      var o = "",
        a = Math.floor(t / 1e6),
        t = t % 1e6;
      a > 0 && ((o = n(a, r) + " triệu"), (r = !0));
      var e = Math.floor(t / 1e3),
        t = t % 1e3;
      return e > 0 && ((o += n(e, r) + " ngàn"), (r = !0)), t > 0 && (o += n(t, r)), o;
    };
  return {
    doc: function (r) {
      if (0 == r) return t[0];
      var n = "",
        a = "",
        ty = "";
      do (ty = r % 1e9), (r = Math.floor(r / 1e9)), (n = r > 0 ? o(ty, !0) + a + n : o(ty, !1) + a + n), (a = " tỷ");
      while (r > 0);
      return n.trim();
    },
  };
})();

export default {
  components: { breadcrumb, AppKyCuoc: KyCuoc2 },
  data: function () {
    return {
      header: {
        title: "IN HÓA ĐƠN CHUYỂN KHOẢN",
        list: [
          { name: "Quản lý đại lý", link: { name: "Ui.cards" } },
          {
            name: "In hóa đơn chuyển khoản",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      kyCuoc: "",
      kyHoaDon: "",
      chuKyNo: "",
      ngayTra: new Date(),
      title: "",
      dsNhanVien: [],
      htThanhToan: [],
      nhanVienSelected: {},
      hinhThucSelected: {},
      dsPhieuChuaIn: [],
      dsPhieuDaIn: [],
      dsPhieuInChon: [],
      currentTab: 1,
      mauInId: "",
      dsMauIn: [],
      modalForm: {
        seri: 0,
        soQuyen: 0,
        soSeri: 0,
      },
      colums: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_TT",
          headerText: "Ngày trả",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "SOPHIEU",
          headerText: "Số phiếu",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LANTRA",
          headerText: "Lần trả",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TIENTRA",
          headerText: "Tiền trả",
          allowFiltering: true,
          allowSorting: false,
          type:'number',
          format:'N0'
        },
        {
          fieldName: "SOHOADON",
          headerText: "Số HĐ",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "CHUNGTU",
          headerText: "Số CT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NHANVIEN_TC",
          headerText: "Nhân viên TC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DTCC",
          headerText: "ĐTCC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAYNGANHANG",
          headerText: "Ngày NH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_NH",
          headerText: "Tên NH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_TN",
          headerText: "Thu ngân",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "HDDT",
          headerText: "HĐĐT",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      thangNam: "",
      inTTCbk: false,
       sumTemplate: function() {
        return { template: Vue.component('sumTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      }     
    };
  },
   computed: {
    aggregatesColumns () {
      return [       
        { field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ]
    },
     aggregatesColumns2 () {
      return [       
        { field: 'LANTRA', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TIENTRA', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ]
    },
  },

  validations: {},
  watch: {},
  created: async function () {
    this.getDsNhanVien();
    this.loadThongTinSeri();
  },
  async mounted() {},
  async updated() {},
  methods: {
    setCurrentTab(tab) {
      this.currentTab = tab;
      this.dsPhieuInChon = [];
    },
    onClickDanhSach() {
       if  (moment(this.ngayTra).subtract(1, "months").endOf("month").format("YYYYMM")===this.kyHoaDon)
            this.getDsNhanVien();
      else
        this.$toast.error('Ngày trả không thuộc kỳ hóa đơn');
    },
    onChangeNgayTra() {
       if  (moment(this.ngayTra).subtract(1, "months").endOf("month").format("YYYYMM")===this.kyHoaDon)
            this.getDsNhanVien();
      else
        this.$toast.error('Ngày trả không thuộc kỳ hóa đơn');
    },
    onClickRowNhanVien(value) {
      if(value) {
        this.nhanVienSelected = value;
        this.nhanVienSelected && this.getDsHtThanhToan();
      }
    },
    onClickRowHinhThuc(value) {
      this.hinhThucSelected = value;
      if (this.hinhThucSelected) {
        this.getDSPhieuChuaIn();
        this.getDsPhieuDaIn();
      }
    },
    onClickBtnInHd() {
      if (this.dsPhieuInChon.length == 0) {
        this.$toast.error("Bạn chưa chọn phiếu!");
      } else {
        this.getDsMauIn();
        this.loadThongTinSeri();
        this.$bvModal.show("modal-inPT");
      }
    },
    onClickHideModal() {
      this.$bvModal.hide("modal-inPT");
    },
    onClickExportExcel() {
      this.exportExcel();
    },
    onChoseRow(value) {
      this.dsPhieuInChon = value;
    },
    onClickCapNhatSoSeri() {
      if (this.modalForm.soSeri.trim().length == 0) {
        this.$toast.error("Bạn chưa nhập Số Seri");
        return false;
      }
      if (!isNumeric(this.modalForm.soSeri)) {
        this.$toast.error("Dữ liệu Số seri phải là kiểu số");
        return false;
      }
      this.capNhatSeri();
    },
    handleKyHoaDon(e) {
      this.thangNam = moment(e.p_kyhoadon).format("MM/YYYY");
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
      this.kyHoaDon = moment(e.p_kyhoadon).format("YYYYMM");
      this.chuKyNo = e.p_chuky_no;
      const monthYear = moment(e.p_kyhoadon).format("MM/YYYY");
      const numberDays = moment(monthYear, "MM/YYYY").daysInMonth();
      this.title = `Kỳ cước ${this.kyHoaDon}, chu kỳ ${this.chuKyNo} tính từ ngày 01/${monthYear} - đến ngày ${numberDays}/${monthYear}`;
    },
     sum_cot:function(tencot,data){
      let _sum=0;
      data.forEach(item=>{
        if (tencot==='LANTRA')
          _sum=_sum+item.LANTRA;
        if (tencot==='TIENTRA')
          _sum=_sum+item.TIENTRA;  
      })
      return _sum;
    },
    exportExcel() {
      let exportData = [],     
        tong_lantra=0,
        tong_tra=0,
        fileName=''
        ;       

      if (this.currentTab == 1) {
        if (this.dsPhieuChuaIn.length > 0) {
          exportData = JSON.parse(JSON.stringify(this.dsPhieuChuaIn));      
            tong_lantra=this.sum_cot('LANTRA',this.dsPhieuChuaIn);
            tong_tra=this.sum_cot('TIENTRA',this.dsPhieuChuaIn);
            fileName='Danh_sach_phieu_thanh_toan_chua_in'
        } else {
          this.$toast.error("Không có dữ liệu để xuất Excel !");
        }
      } else {
        if (this.dsPhieuChuaIn.length > 0) {
          exportData = JSON.parse(JSON.stringify(this.dsPhieuDaIn));
          tong_lantra=this.sum_cot('LANTRA',this.dsPhieuDaIn);
          tong_tra=this.sum_cot('TIENTRA',this.dsPhieuDaIn);
          fileName='Danh_sach_phieu_thanh_toan_da_in'
        } else {
          this.$toast.error("Không có dữ liệu để xuất Excel !");
        }
      }
      const tHeader = this.colums.map((item) => item.headerText);
      const filterVal = this.colums.map((item) => item.fieldName);
      const data = this.formatJson(filterVal, exportData);
      
      let tmp=[];
       for (let i = 1; i < 18; i++) 
       {
          if (i===6)
            tmp.push(tong_lantra);
          else
          if (i===7)
            tmp.push(tong_tra);
          else
          if (i===1)
            tmp.push("Tổng Cộng");
          else
            tmp.push("");  
       }
      data.push(tmp)   ;      
      export_json_to_excel({
        header: tHeader,
        data,
        filename: fileName,
        sheetname: 'Danh sách',
        bookType: "xlsx",
      });
      
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j];
        })
      );
    },
    async onClickInPhieuThu() {
      this.makeStringToPrint();       

    },
    async makeStringToPrint() {
      const iDongMayIn = 33;
      const sChuoiCanIn = [];
      const iDC = -1;
      const Align = -4;
      var dem = 0;
      for (let i = 0; i < iDongMayIn * this.dsPhieuInChon.length; i++)
      {
          sChuoiCanIn.push("");
      }
      for (let i = 0; i < this.dsPhieuInChon.length; i++) {
        const item = this.dsPhieuInChon[i];
        let ttKH = await this.getTtThanhToan(item);
        const tongNo = await this.getDsTongNo(item);
        if (ttKH && tongNo) {
          let dbCongThueVAT = tongNo.THUE;
          let dbTongCong = tongNo.CONGTRA;
          let dbCuocVienThong_KhongThue = tongNo.CONG;
          let strDongtieude = "".padStart(24, " ") + "KyHD: ";
          strDongtieude = strDongtieude.substring(0, strDongtieude.length - 1);
          let s = strDongtieude;
          let s1 = "";
          let s2 = "";
          s1 = s;

          let iViTri = s.indexOf(",", 0);
          while (iViTri > 0) {
            if (iViTri > 55) {
              s1 = s.substring(0, iViTri);
              s2 = s.substring(iViTri);
              break;
            }
            iViTri = s.indexOf(",", iViTri + 1);
          }
          let _tdong = 33;
          s = " ";
          let _mst = ttKH["MST"] ? ttKH["MST"].toString() : "";
          let vmst = "";
          if (this.inTTCbk) {
            for (let m = 0; m < _mst.length; m++) {
              if (m <= 1) {
                vmst += _mst[m] + " ";
              } else if (m <= 8) {
                if (m == 2) vmst += " " + _mst[m];
                else vmst += " " + _mst[m];
              } else {
                if (m == 9 || m == 10 || m == 13) vmst += " " + _mst[m];
                else vmst += " " + _mst[m];
              }
            }

            sChuoiCanIn[7 + _tdong * i + iDC] = Space(49 + Align) + vmst;
            sChuoiCanIn[8 + _tdong * i + iDC] = Space(15 + Align) + CatChuoi(ttKH["TEN_TT"], 65);
            sChuoiCanIn[9 + _tdong * i + iDC] = Space(23 + Align) + CatChuoi(ttKH["DIACHI_TT"], 46);
            sChuoiCanIn[10 + _tdong * i + iDC] = Space(28 + Align) + ttKH["SODAIDIEN"] + Space(20 + Align) + CatChuoi(ttKH["MA_TT"], 46);
            sChuoiCanIn[12 + _tdong * i + iDC] = Space(40 + Align) + ttKH["HINHTHUC_TT"];
            let _dvsudung = "Cước DVVT Tháng " + this.thangNam;
            const tongTra = await this.getTongTra(ttKH.MA_TT);
            if (tongTra) {
              const tragoc = tongTra.TRAGOC.toLocaleString("en-En");
              const trathue = tongTra.TRATHUE.toLocaleString("en-En");
              const tongtien = tongTra.TONGTIEN.toLocaleString("en-En");

              sChuoiCanIn[17 + _tdong * i + iDC] = Space(17 + Align) + _dvsudung + Space(32 + Align) + tragoc;
              sChuoiCanIn[20 + _tdong * i + iDC] = Space(20 + Align) + Space(52 + Align) + tragoc;
              sChuoiCanIn[21 + _tdong * i + iDC] = Space(20 + Align) + Space(52 + Align) + trathue;
              sChuoiCanIn[22 + _tdong * i + iDC] = Space(20 + Align) + Space(52 + Align) + tongtien;
            }
            if (dbTongCong < 0) {
              dbTongCong = Number(dbTongCong) * -1;
            }
            s = DOCSO.doc(dbTongCong) + " đồng";
            s1 = "";
            s2 = "";
            s1 = s;

            iViTri = s.indexOf(" ", 0);
            while (iViTri > 0) {
              if (iViTri > 45) {
                s1 = s.substring(0, iViTri);
                s2 = s.substring(iViTri).trimStart();
                break;
              }
              iViTri = s.indexOf(" ", iViTri + 1);
            }
            sChuoiCanIn[23 + _tdong * i + iDC] = Space(30 + Align) + s1;
            let cach = 78;
            if (s2.length < cach) {
              if (s2.length == 0) {
                for (let k = 0; k < cach - s2.length; k++) {
                  s2 += " ";
                }
              } else {
                for (let k = 0; k < cach - s2.length; k++) {
                  s2 += " ";
                }
              }
            }
            const currentDate = moment(new Date());
            sChuoiCanIn[24 + _tdong * i + iDC] =
              Space(12 + Align) +
              s2 +
              currentDate.format("DD") +
              Space(10 + Align) +
              currentDate.format("MM") +
              Space(10 + Align) +
              currentDate.format("YYYY");
            sChuoiCanIn[30 + _tdong * i + iDC] = Space(59 + Align) + this.$auth.getUserName();
            dem = 30 + _tdong * i + iDC;
          } else {
            for (let m = 0; m < _mst.length; m++) {
              if (m <= 1) {
                vmst += _mst[m] + " ";
              } else if (m <= 8) {
                if (m == 2) vmst += " " + _mst[m];
                else vmst += " " + _mst[m];
              } else {
                if (m == 9 || m == 10 || m == 13) vmst += " " + _mst[m];
                else vmst += " " + _mst[m];
              }
            }
            sChuoiCanIn[7 + _tdong * i + iDC] = Space(55 + Align) + vmst;
            sChuoiCanIn[8 + _tdong * i + iDC] = Space(28 + Align) + CatChuoi(ttKH["TEN_TT"], 65);
            sChuoiCanIn[9 + _tdong * i + iDC] = Space(18 + Align) + CatChuoi(ttKH["DIACHI_TT"], 46);
            sChuoiCanIn[10 + _tdong * i + iDC] = Space(28 + Align) + ttKH["SODAIDIEN"] + Space(55 + Align) + CatChuoi(ttKH["MA_TT"], 46);
            sChuoiCanIn[11 + iDongMayIn * i + iDC] = Space(40 + Align) + ttKH["HINHTHUC_TT"];

            let _dvsudung = "Cước DVVT Tháng " + this.thangNam;
            const tongTra = await this.getTongTra(ttKH.MA_TT);
            if (tongTra) {
              const tragoc = tongTra.TRAGOC.toLocaleString("en-En");
              const trathue = tongTra.TRATHUE.toLocaleString("en-En");
              const tongtien = tongTra.TONGTIEN.toLocaleString("en-En");
              // console.log(tragoc, trathue, tongtien);
              sChuoiCanIn[13 + _tdong * i + iDC] = Space(20 + Align) + _dvsudung + Space(70 + Align) + tragoc;
              sChuoiCanIn[16 + _tdong * i + iDC] = Space(20 + Align) + Space(113 + Align) + tragoc;
              sChuoiCanIn[17 + _tdong * i + iDC] = Space(20 + Align) + Space(115 + Align) + trathue;
              sChuoiCanIn[18 + _tdong * i + iDC] = Space(20 + Align) + Space(113 + Align) + tongtien;
            }
            if (dbTongCong < 0) {
              dbTongCong = Number(dbTongCong) * -1;
            }
            s = DOCSO.doc(dbTongCong) + " đồng";
            s1 = "";
            s2 = "";
            s1 = s;
            iViTri = s.indexOf(" ", 0);
            while (iViTri > 0) {
              if (iViTri > 45) {
                s1 = s.substring(0, iViTri);
                s2 = s.substring(iViTri).trimStart();
                break;
              }
              iViTri = s.indexOf(" ", iViTri + 1);
            }

            sChuoiCanIn[19 + _tdong * i + iDC] = Space(26 + Align) + s1;
            sChuoiCanIn[20 + _tdong * i + iDC] = Space(10 + Align) + s2;
            dem = 20 + _tdong * i + iDC;
            // this.download("In", sChuoiCanIn);
          }
        }
      };
      // console.log('Final', sChuoiCanIn);
      if(this.inTTCbk) {
        this.print(sChuoiCanIn);
      } else {
        this.download("In", sChuoiCanIn);
      }
      if (this.modalForm.soSeri.trim().length == 0) {
        this.$toast.error("Bạn chưa nhập Số Seri");
        return false;
      }
      if (!isNumeric(this.modalForm.soSeri)) {
        this.$toast.error("Dữ liệu Số seri phải là kiểu số");
        return false;
      }
      this.capNhatSeri();
    },
    download(fileName, text) {
      var element = document.createElement("a");
      let data = "";
      text.forEach((item) => {
        data += item + "\n";
      });
      element.setAttribute("href", "data:text/plain;charset=utf-8," + encodeURIComponent(data));
      element.setAttribute("download", fileName);

      element.style.display = "none";
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    },
    print(text) {
      var iframe = document.getElementById("textfile").contentWindow;
      let data = "";
      text.forEach((item) => {
        data += item + "<br>";
      });
      data = `<pre>${data}</pre>`;
      iframe.document.write(data);
      iframe.document.close(); //important!
      iframe.focus(); //IE fix
      iframe.print();
    },
    getDsNhanVien: async function () {
      const param = {
        ngayTT: moment(this.ngayTra).format("DD/MM/YYYY"),
        kieu: 1,
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/quan-ly-dai-ly/ds-hoa-don-chuyen-khoan-hien-hanh", param);
        this.dsNhanVien = rs.data;
      } catch (error) {
        this.$root.toastError(error.response.data.message_detail);
      } finally {
      }
    },
    getDsHtThanhToan: async function () {
      const param = {
        ngayTT: moment(this.ngayTra).format("DD/MM/YYYY"),
        kieu: 2,
        maNVTC: this.nhanVienSelected.MA_NV,
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/quan-ly-dai-ly/ds-hoa-don-chuyen-khoan-hien-hanh", param);
        this.htThanhToan = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getDSPhieuChuaIn: async function () {
      const param = {
        ngayTT: moment(this.ngayTra).format("DD/MM/YYYY"),
        kieu: 3,
        maNVTC: this.nhanVienSelected.MA_NV,
        htttId: this.hinhThucSelected.HTTT_ID,
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/quan-ly-dai-ly/ds-hoa-don-chuyen-khoan-hien-hanh", param);
        this.dsPhieuChuaIn = rs.data.map((item) => ({
          ...item,
        //   NGAY_TT: moment(item.NGAY_TT).format("DD/MM/YYYY"),
        //   NGAYNGANHANG: moment(item.NGAY_TT).format("DD/MM/YYYY"),
        }));
      } catch (error) {
      } finally {
      }
    },
    getDsPhieuDaIn: async function () {
      const param = {
        ngayTT: moment(this.ngayTra).format("DD/MM/YYYY"),
        kieu: 4,
        maNVTC: this.nhanVienSelected.MA_NV,
        htttId: this.hinhThucSelected.HTTT_ID,
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/quan-ly-dai-ly/ds-hoa-don-chuyen-khoan-hien-hanh", param);
        this.dsPhieuDaIn = rs.data.map((item) => ({
          ...item,
        //   NGAY_TT: moment(item.NGAY_TT).format("DD/MM/YYYY"),
        //   NGAYNGANHANG: moment(item.NGAY_TT).format("DD/MM/YYYY"),
        }));
      } catch (error) {
      } finally {
      }
    },
    getDsMauIn: async function () {
      const param = {
        loaiHD: 0,
        nhomBCId: 24, //16
        dsBaoCao: "",
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/common/mau-in", param);
        this.dsMauIn = rs.data;
        if (rs.data.length > 0) {
          this.mauInId = rs.data[0].BAOCAO_ID;
        }
      } catch (error) {
      } finally {
      }
    },
    getTtThanhToan: async function (phieuId) {
      const param = {
        kyCuoc: this.kyCuoc,
        kieu: 0,
        phieuTTId: phieuId,
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/tra-cuu/thong-tin-thanh-toan-theo-phieu", param);
        return rs.data && rs.data.length > 0 ? rs.data[0] : null;
      } catch (error) {
        return null;
      } finally {
      }
    },
    getDsTongNo: async function (phieuId) {
      const param = {
        kyCuoc: this.kyCuoc,
        phieuTTId: phieuId,
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/tra-cuu/tong-tra-theo-phieu", param);
        return rs.data && rs.data.length > 0 ? rs.data[0] : null;
      } catch (error) {
        return null;
      } finally {
      }
    },
    getTongTra: async function (maTT) {
      const param = {
        kyCuoc: this.kyCuoc,
        maTT: maTT,
      };
      try {
        var rs = await this.$root.context.get("/web-thuno/api/thu-no/tra-cuu/tong-tra-theo-thanh-toan", param);
        return rs.data && rs.data.length > 0 ? rs.data[0] : null;
      } catch (error) {
        return null;
      } finally {
      }
    },
    capNhatSeri: async function () {
      const { seri, soQuyen, soSeri } = this.modalForm;
      const body = {
        dsPhieu: this.dsPhieuInChon,
        seri: seri,
        soQuyen: soQuyen,
        soSeri: soSeri,
      };
      console.log(body);
      try {
        const { data } = await this.axios.put("web-thuno/api/thu-no/quan-ly-dai-ly/seri-hoa-don-hien-hanh", body);
        if (data && data.error == "200") {
              this.modalForm.soSeri = Number(soSeri) + this.dsPhieuInChon.length;
              this.setDataToStorage("KEY_SERI", seri)
              this.setDataToStorage("KEY_SOQUYEN", soQuyen)
              this.setDataToStorage("KEY_SOSERI", this.modalForm.soSeri)

          this.$toast.success("Cập nhật thông tin thành công!");
          if (this.currentTab==1)
                  {
                      let dstmp=[];      
                      this.dsPhieuChuaIn.forEach((x)=>{        
                        if (!this.dsPhieuInChon.includes(x.PHIEU_ID))            
                          dstmp.push(x);
                      })
                      this.dsPhieuChuaIn=[...dstmp];      
                  }
        } else {
          this.$toast.error("Cập nhật thông tin thất bại!");
        }
      } catch (error) {
        this.$toast.error("Cập nhật thông tin thất bại!");
      }
    },
    capNhatSauIn: async function () {
      const { seri, soQuyen, soSeri } = this.modalForm;
      const body = {
        dsPhieu: this.dsPhieuInChon,
        seri: seri,
        soQuyen: soQuyen,
        soSeri: soSeri,
      };
      console.log(body);
      try {
        const { data } = await this.axios.put("web-thuno/api/thu-no/quan-ly-dai-ly/seri-hoa-don-hien-hanh", body);
        if (data && data.error == "200") {
          this.getDSPhieuChuaIn();
          this.getDsPhieuDaIn();
          this.$bvModal.hide("modal-inPT");
          // this.$toast.success("Cập nhật thông tin thành công!");
        } else {
          // this.$toast.error("Cập nhật thông tin thất bại!");
        }
      } catch (error) {
        // this.$toast.error("Cập nhật thông tin thất bại!");
      }
    },
    loadThongTinSeri(){
        let s = this.getDataFromStorage("KEY_SERI")        
        this.modalForm.seri = s === null ? '' : s
        s = this.getDataFromStorage("KEY_SOQUYEN")
        this.modalForm.soQuyen = s === null ? 0: s
        s = this.getDataFromStorage("KEY_SOSERI")        
        this.modalForm.soSeri = s === null ? 0 : s
      },
        getDataFromStorage(key)
      {
        try{
          //console.log(localStorage.getItem(key))
          return localStorage.getItem(key)

        }catch(error)
        {
          //console.log(error)
          return null
        }

      },
      setDataToStorage(key, value)
      {
        try {
          localStorage.setItem(key, value);
          return true;
        } catch (error) {
          return false;
        }
      }
  },
};
</script>
