<template src="./IssuanceElectronicInvoices.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import DsLuotPhatHanh from "./DSLuotPhatHanh";
import DsKhachHang from './DSKhachHang';
import DsHoaDon from './DSHoaDon';
import ConstKH from "./DSKhachHang/const";
import ConstHD from "./DSHoaDon/const";
import Utils from "./utils";
import API from "./API";
import moment from "moment";
import xml2js from 'xml2js';

export default {
  name: 'IssuanceElectronicInvoices',
  components: {
    breadcrumb,
    DsLuotPhatHanh,
    DsKhachHang,
    DsHoaDon
  },
  data() {
    return {
      header: {
        title: 'PHÁT HÀNH HOÁ ĐƠN ĐIỆN TỬ THEO FILE',
        list: [
          {name: 'Thanh toán', link: {name: 'Ui.cards'}},
          {name: 'Phát hành hóa đơn điện tử - file', link: {name: 'Ui.cards'}},
        ]
      },
      classLeft: 'col-sm-3',
      classRight: 'col-sm-9',
      btnVisible: {
        btnLayTT: false,
        btnPhatHanh: false,
        btnCapNhatKH: false,
        btnPhatHanhLai: false,
      },
      khachHangData: {
        dsKhachHang: [],
        dsError: [],
        dsKHTab: 1,
        dsErrorTab: 2,
        currentTab: 1,
        isShowBtnUpload: true,
        dsKHFilePath: '',
      },
      hoaDonData: {
        dsHoaDon: [],
        dsError: [],
        dsHDTab: 1,
        dsErrorTab: 2,
        currentTab: 1,
        isShowBtnUpload: true,
        dsHDFilePath: '',
      },
      Fkey: '',
      dsBDHDDT: [],
      dsBDHDDTCT: [],
      dtCT_HD: [],
      tongtien: 0,
      tong_vat: 0,
      tylethue: 0,
      tienchu: '',
      BDHDDT_ID: 0,
      user_hddt_td: '',
      pass_hddt_td: '',
      user_hddt: '',
      pass_hddt: '',
    }
  },
  mounted() {
    this.classLeft = 'hidden';
    this.classRight = 'col-sm-12';
    this.hideCheckboxHeader();

    this.getTTNguoiDung();
  },
  computed: {
    pattern() {
      return this.$refs.dsKhachHang.getCboPatternText();
    },
    serial() {
      return this.$refs.dsKhachHang.getCboSerialText()
    }
  },
  methods: {
    btnNhapMoi() {
      this.hideLeft();
      this.clear();
    },

    btnLichSu() {
      this.showLeft();
    },

    btnLayTT() {
    },

    btnPhatHanh() {
      this.$confirm(`Bạn có muốn phát hành hóa đơn?`,
        'Thông báo',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }
      ).then(async () => {
        this.loading(true);
        try {
          let vcount_id = await this.SinhMa();
          await this.phatHanhHoaDon(vcount_id);
          console.log('Bat dau');
          await this.Xuathoadon_GachNo(vcount_id);

          console.log('Ket thuc');
          await this.$refs.dsPH.HienThi_MucLuc();
          await this.HienThi_LichSu(vcount_id);

          this.btnVisible.btnPhatHanh = false;
        } catch (e) {
          console.log(e);
        } finally {
          this.loading(false);
        }

        this.$toast.success('Phát hành hóa đơn thành công', 'Thông báo');
      }).catch((e) => {
        console.log(e);
      })
    },

    async btnCapNhatKH() {
      this.loading(true);

      this.PhatHanh_KhachHang().then(
        () => {
          let HT_btnKH = false;
          for (let kh of this.khachHangData.dsKhachHang) {
            if (kh.trangthai !== "s1") {
              HT_btnKH = true;
              break;
            }
          }
          this.btnVisible.btnCapNhatKH = HT_btnKH;
          this.loading(false);
          this.$toast.success("Cập nhật khách hàng thành công", 'Thông báo');
        }
      );
    },

    btnPhatHanhLai() {
      this.$confirm(`Bạn có muốn phát hành lại hóa đơn?`,
        'Thông báo',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }
      ).then(async () => {
        this.loading(true);
        await this.PhatHanh_KhachHang();
        await this.PhatHanh_HoaDon();

        await this.HienThi_LichSu(this.vcount_id);

        this.loading(false);
        this.$toast.success('Phát hành lại hóa đơn thành công', 'Thông báo').then(r => {
        });
      }).catch((e) => {
      });
    },

    hideLeft() {
      this.classLeft = 'hidden';
      this.classRight = 'col-sm-12';
      this.khachHangData.isShowBtnUpload = true;
      this.hoaDonData.isShowBtnUpload = true;
    },

    showLeft() {
      this.classLeft = 'col-sm-3';
      this.classRight = 'col-sm-9';
      this.khachHangData.isShowBtnUpload = false;
      this.hoaDonData.isShowBtnUpload = false;
    },

    clear() {
      this.btnVisible = {
        ...this.btnVisible,
        btnPhatHanh: false,
        btnCapNhatKH: false,
        btnPhatHanhLai: false
      }

      this.khachHangData = {
        ...this.khachHangData,
        dsKhachHang: [],
        dsError: [],
        isShowBtnUpload: true,
        dsKHFilePath: '',
        currentTab: 1,
      };

      this.hoaDonData = {
        ...this.hoaDonData,
        dsHoaDon: [],
        dsError: [],
        isShowBtnUpload: true,
        dsHDFilePath: '',
        currentTab: 1,
      };

      this.hideCheckboxHeader();
    },

    readExcel(objData) {
      let {file, type} = objData;
      this.setPath(type, file.name);

      if (!Utils.isFileExcel(file)) {
        return;
      }

      let reader = new FileReader();
      reader.onload = (e) => {
        try {
          this.loading(true);
          let data = Utils.readFile(e.target.result, this.getCols(type));
          this.processDataImport(data, type);
        } catch (ex) {
          this.$toast.error(ex);
        } finally {
          this.loading(false);
        }
      };

      reader.onerror = function (ex) {
        console.log(ex);
      };


      reader.readAsBinaryString(file);
    },

    setPath(type, path) {
      if (type === 1) {
        this.clear();
        this.khachHangData.dsKHFilePath = path;
      } else {
        this.hoaDonData.dsHDFilePath = path;
      }
    },

    getCols(type) {
      return type === 1 ? ConstKH.colsExcel : ConstHD.colsExcel;
    },

    async processDataImport(data, type) {
      if (data.length <= 0) {
        this.$root.$toast.error('Vui lòng nhập đầy đủ dữ liệu trong file Excel');

      } else {
        if (type === 1) {
          await this.importKH(data);
        } else if (type === 2) {
          await this.importHD(data);
        }

        this.hideCheckboxHeader();
      }
    },

    async importKH(data) {
      //kiem tra null
      let dsError = [];
      let stt = 1;
      for (const item of data) {
        if (Utils.checkNullInObject(item, 'ma_kh') ||
          Utils.checkNullInObject(item, 'ten_kh') ||
          Utils.checkNullInObject(item, 'diachi') ||
          Utils.checkNullInObject(item, 'sdt')) {
          let obj = {
            ...item,
            stt: stt++,
            lydo: Utils.checkNullInObject(item, 'ma_kh') ? 'Mã KH null' :
              Utils.checkNullInObject(item, 'ten_kh') ? 'Tên KH null' :
                Utils.checkNullInObject(item, 'diachi') ? 'Địa chỉ null' :
                  Utils.checkNullInObject(item, 'sdt') ? 'Số ĐT null' : ''
          }

          dsError.push(obj)
        }
      }

      if (dsError.length > 0) {
        this.khachHangData = {
          ...this.khachHangData,
          dsError,
          dsKhachHang: [],
          currentTab: this.khachHangData.dsErrorTab
        }
        return;
      }

      //kiem tra lap du lieu
      stt = 1;
      let dsMaKH = [];
      for (const item of data) {
        let {ma_kh} = item;
        if (dsMaKH.includes(ma_kh)) {
          dsError.push({
            stt: stt++,
            ma_kh,
            lydo: 'Mã KH bị lặp lại'
          });
        } else {
          dsMaKH.push(ma_kh);
        }
      }

      if (dsError.length > 0) {
        this.khachHangData = {
          ...this.khachHangData,
          dsError,
          dsKhachHang: [],
          currentTab: this.khachHangData.dsErrorTab
        }
        return;
      }

      let dsKhachHang = data.map(item => {
        return {
          ...item,
          'status': '',
          chon: 's1',
        }
      });
      this.khachHangData = {
        ...this.khachHangData,
        dsError: [],
        dsKhachHang,
        currentTab: this.khachHangData.dsKHTab
      }
    },

    async importHD(data) {
      this.hoaDonData = {
        ...this.hoaDonData,
        dsHoaDon: [],
        dsError: [],
        currentTab: this.hoaDonData.dsHDTab
      }

      let dsError = [];
      let stt = 1;

      //kiem tra null
      for (const item of data) {
        if (Utils.checkNullInObject(item, 'ma_kh') ||
          Utils.checkNullInObject(item, 'stt_hd') ||
          Utils.checkNullInObject(item, 'ten_sp') ||
          Utils.checkNullInObject(item, 'donvitinh') ||
          Utils.checkNullInObject(item, 'soluong') ||
          Utils.checkNullInObject(item, 'dongia') ||
          Utils.checkNullInObject(item, 'tyle_vat')) {
          let obj = {
            ...item,
            stt: stt++,
            lydo: Utils.checkNullInObject(item, 'ma_kh') ? 'Mã KH null' :
              Utils.checkNullInObject(item, 'stt_hd') ? 'STT_HD null' :
                Utils.checkNullInObject(item, 'ten_sp') ? 'Tên SP null' :
                  Utils.checkNullInObject(item, 'donvitinh') ? 'Đơn vị tính null' :
                    Utils.checkNullInObject(item, 'soluong') ? 'Số lượng null' :
                      Utils.checkNullInObject(item, 'dongia') ? 'Đơn giá null' :
                        Utils.checkNullInObject(item, 'tyle_vat') ? 'Tỷ lệ VAT null' : ''
          }

          dsError.push(obj)
        }
      }

      if (dsError.length > 0) {
        this.hoaDonData = {
          ...this.hoaDonData,
          dsError,
          dsHoaDon: [],
          currentTab: this.hoaDonData.dsErrorTab
        }
        return;
      }

      //kiem tra lap
      stt = 1;
      let dsLap = [];
      for (const item of data) {
        let {ma_kh, stt_hd, ten_sp} = item;
        let key = ma_kh + ':' + stt_hd + ':' + ten_sp;
        if (dsLap.includes(key)) {
          dsError.push({
            stt: stt++,
            ma_kh,
            stt_hd,
            ten_sp,
            lydo: 'Tên SP bị lặp lại'
          });
        } else {
          dsLap.push(key);
        }
      }

      if (dsError.length > 0) {
        this.hoaDonData = {
          ...this.hoaDonData,
          dsError,
          dsHoaDon: [],
          currentTab: this.hoaDonData.dsErrorTab
        }
        return;
      }

      //kiem tra du lieu khach hang
      let dsMaKH = [];
      for (const item of data) {
        let {ma_kh} = item;
        if (!dsMaKH.includes(ma_kh)) {
          dsMaKH.push(ma_kh);
        }
      }

      for (const maKH of dsMaKH) {
        if (!this.ktkh(maKH)) {
          this.$root.$toast.error(`Mã KH: ${maKH} không tồn tại`);
          return;
        }
      }

      //kiem tra ty le hoa don
      let dsTyle = [];
      for (const item of data) {
        let {ma_kh, stt_hd, tyle_vat} = item;

        for (const tyleItem of dsTyle) {
          let arr = tyleItem.split('$');
          if (arr[0] == ma_kh && arr[1] == stt_hd && arr[2] != tyle_vat) {
            this.$root.$toast.error('Sử dụng chung một tỷ lệ VAT trong cùng một hóa đơn. Vui lòng kiểm tra lại!' +
              '\nMã KH: ' + ma_kh +
              '\nSTT_HD: ' + stt_hd);
            return;
          }
        }

        let key = ma_kh + '$' + stt_hd + '$' + tyle_vat;
        dsTyle.push(key);
      }

      //hien thi du lieu
      let ds = [];
      for (const item of data) {
        try {
          let tongtien = parseInt(item.soluong) * parseInt(item.dongia);
          ds.push({
            ...item,
            chon: 's1',
            tongtien,
            tongtien_vat: tongtien + Math.round(tongtien * item.tyle_vat / 100)
          });
        } catch (e) {
          this.$root.$toast.error('Lỗi dữ liệu: số lượng, đơn giá');
          return;
        }
      }

      ds = ds.map(item => {
        return {
          ...item,
          soluong: Number(item.soluong),
          dongia: Number(item.dongia),
          tongtien: Number(item.tongtien),
          tongtien_vat: Number(item.tongtien_vat),
        };
      })

      this.hoaDonData.dsHoaDon = ds;
      if (this.khachHangData.dsKhachHang.length > 0 && ds.length > 0) {
        this.btnVisible.btnPhatHanh = true;
      }
    },

    ktkh(maKH) {
      for (const kh of this.khachHangData.dsKhachHang) {
        if (kh.ma_kh == maKH) {
          return true;
        }
      }

      return false;
    },

    async gridPH_selectedRowChanged(item) {
      this.loading(true);
      try {
        let vcount_id = item.COUNT_ID;
        this.vcount_id = vcount_id;
        await this.HienThi_LichSu(vcount_id);
        this.hideCheckboxHeader();

        this.btnVisible.btnPhatHanh = false;
        for (const kh of this.khachHangData.dsKhachHang) {
          if (kh.trangthai !== "s1") {
            this.btnVisible.btnCapNhatKH = true;
            break;
          }
        }

        this.btnVisible.btnPhatHanhLai = false;
        for (const hd of this.hoaDonData.dsHoaDon) {
          if (hd.phathanh_hd !== 's1' || hd.thanhtoan_hd !== 's1') {
            this.btnVisible.btnPhatHanhLai = true;
            break;
          }
        }
      } catch (e) {
        this.clear();
      } finally {
        this.loading(false);
      }
    },

    async HienThi_LichSu(vcount_id) {
      try {
        this.khachHangData = {
          ...this.khachHangData,
          dsError: [],
          dsKhachHang: [],
          currentTab: this.khachHangData.dsKHTab
        }

        this.hoaDonData = {
          ...this.hoaDonData,
          dsError: [],
          dsHoaDon: [],
          currentTab: this.hoaDonData.dsHDTab
        }

        let luotph = {luotph: vcount_id}
        let params = {p_ds_para: JSON.stringify(luotph)};
        let rs = await API.hienThiLichSu(this.axios, params);
        let {js_returnds} = rs.data.data;
        js_returnds = JSON.parse(js_returnds);
        let {DS_HDDT_HOADON, DS_HDDT_KHACHHANG} = js_returnds;

        if (DS_HDDT_KHACHHANG != null) {
          let dataKH = [];
          for (const kh of DS_HDDT_KHACHHANG) {
            let tmp = Utils.lowercaseKeys(kh);
            tmp.chon = tmp.trangthai === 's1' ? 's0' : 's1';
            dataKH.push(tmp);
          }

          this.khachHangData.dsKhachHang = dataKH;
        }

        if (DS_HDDT_HOADON != null) {
          let dataHD = [];
          for (const hd of DS_HDDT_HOADON) {
            let tmp = Utils.lowercaseKeys(hd);
            tmp.chon = tmp.phathanh_hd === 's1' && tmp.thanhtoan_hd === 's1' ? 's0' : 's1';
            tmp.soluong = Number(tmp.soluong);
            tmp.dongia = Number(tmp.dongia);
            tmp.tongtien = Number(tmp.soluong) * Number(tmp.dongia);
            tmp.tongtien_vat = Number(tmp.tongtien + Math.round(Number(tmp.tongtien) * tmp.tyle_vat / 100));
            dataHD.push(tmp);
          }

          this.hoaDonData.dsHoaDon = dataHD;
        }
      } catch (e) {
        console.log(e);
      }
    },

    hideCheckboxHeader() {
      $('.column-check-selected .e-headerchkcelldiv').addClass('hidden');
    },

    async SinhMa() {
      let str = "";
      let dt;
      try {
        let rs = await this.axios.post('/web-hopdong/laydulieu/sp_tt_hddt_hoadon', {
          "p_param": moment().format('DD/MM/YYYY'),
          "p_type": 1
        });
        dt = rs.data.data || [];
      } catch (e) {
        console.log("SinhMa: " + e);
      }

      if (dt.length > 0) {
        for (let tmp of dt) {
          if (tmp.count_id != null) {
            str = tmp.count_id;
            break;
          }
        }

        str = (parseInt(str) + 1).toString();
      } else {
        str = moment().format("YYMMDD") + "001";
      }

      return str;
    },

    async phatHanhHoaDon(vcount_id) {
      try {
        let dataKH = this.CapNhat_KhachHang(vcount_id);
        let dataHD = this.CapNhat_HoaDon(vcount_id);

        let data = {
          ngay_cn: new Date(),
          js_khachhang: dataKH,
          js_hoadon: dataHD
        };

        await API.phatHanhHoaDon(this.axios, {p_ds_para: JSON.stringify(data)});
      } catch (e) {
        console.log(e);
      }
    },

    CapNhat_KhachHang(count_id) {
      let data = [];
      try {
        for (const kh of this.khachHangData.dsKhachHang) {
          let kieu = 1; //1: Thêm mới, 2 sửa
          let {ma_kh, ten_kh, mst, diachi, email, sdt} = kh;
          let trangthai = '0';

          data.push({
            kieu,
            ma_kh: ma_kh || '',
            ten_kh: ten_kh || '',
            mst: mst || '',
            diachi: diachi || '',
            email: email || '',
            sdt: sdt || '',
            "nguoi_cn": this.$root.token.getUserName(),
            "ngay_cn": new Date(),
            "may_cn": 'localhost',
            "ip_cn": '127.0.0.1',
            count_id,
            trangthai,
          });
        }
      } catch (exp) {
        console.log(exp);
      }

      return data;
    },

    CapNhat_HoaDon(count_id) {
      let data = [];
      try {
        for (const hd of this.hoaDonData.dsHoaDon) {
          let kieu = 1; // 1 Thêm mới, 2 Sửa
          let {ma_kh, stt_hd, ten_sp, donvitinh, soluong, dongia, tyle_vat} = hd;
          let phathanh_hd = "0";
          let thanhtoan_hd = "0";
          let fkey = "";
          let invoice_pattern = "";
          let serial_no = "";
          let invoice_no = "";

          data.push({
            count_id,
            kieu,
            ma_kh: ma_kh || '',
            stt_hd,
            ten_sp: ten_sp || '',
            donvitinh,
            soluong,
            dongia,
            tyle_vat,
            phathanh_hd,
            thanhtoan_hd,
            "nguoi_cn": this.$root.token.getUserName(),
            "ngay_cn": new Date(),
            "may_cn": 'localhost',
            "ip_cn": '127.0.0.1',
            trangthai: '',
            fkey,
            invoice_pattern,
            serial_no,
            invoice_no
          });
        }
      } catch (exp) {
        console.log(exp);
      }

      return data;
    },

    async Xuathoadon_GachNo(vcount_id) {
      try {
        for (let kh of this.khachHangData.dsKhachHang) {
          if (kh.chon === 's1') {
            let {ma_kh, ten_kh, mst, diachi, email, sdt, httt} = kh;

            let isOK = await this.main_update_cus(kh, vcount_id);
            if (!isOK) {
              continue;
            }

            //phai lay theo ma kh va stt hoa don
            let dtTemp = this.hoaDonData.dsHoaDon.filter(hd => {
              return hd.ma_kh === ma_kh;
            });

            if (dtTemp && dtTemp.length > 0) {
              let stt_hd_temp = '';
              for (let hd of dtTemp) {
                if (hd.chon === "s1") {
                  let {stt_hd} = hd;
                  if (stt_hd_temp === stt_hd) {
                    continue;
                  }

                  stt_hd_temp = stt_hd;
                  let vhttt = "Tiền mặt/Chuyển khoản";
                  if (httt) {
                    vhttt = httt;
                  }

                  let xmlInvoice = await this.GetInvoiceXml(ma_kh, ten_kh, diachi, sdt, mst, vhttt, stt_hd);
                  let kq = await this.invoice_ImportAndPublishInv_V2(
                    this.user_hddt_td,
                    this.pass_hddt_td,
                    xmlInvoice,
                    this.user_hddt,
                    this.pass_hddt,
                    this.pattern.toString(),
                    this.serial.toString(),
                    0);

                  let result = kq.split(':');
                  if (result[0] == "OK") {
                    let objHoaDonDienTuEdit = Utils.newContent();
                    let result1 = result[1].split(';');
                    let InvoicePattern1 = result1[0];
                    objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1;

                    let result2 = result1[1].split('-');
                    let SerialNo1 = result2[0];
                    objHoaDonDienTuEdit.SerialNo = SerialNo1;

                    let result3 = result2[1].split('_');
                    let InvoiceNo1 = result3[1];
                    objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1;

                    this.ChinhSua_HoaDon(//Có phát hành, Chưa thanh toán
                      2,
                      vcount_id,
                      ma_kh,
                      stt_hd,
                      "",
                      "",
                      0,
                      0,
                      0,
                      "s1",
                      "0",
                      this.Fkey,
                      InvoicePattern1,
                      SerialNo1,
                      InvoiceNo1
                    );

                    let kq2 = await this.invoice_confirmPaymentFkey_V2(
                      this.Fkey,
                      InvoicePattern1,
                      SerialNo1
                    );

                    if (kq2 != "OK:") {
                      this.ChinhSua_HoaDon(//Có phát hành, Lỗi thanh toán
                        2,
                        vcount_id,
                        ma_kh,
                        stt_hd,
                        "",
                        "",
                        0,
                        0,
                        0,
                        "s1",
                        "s0",
                        this.Fkey,
                        InvoicePattern1,
                        SerialNo1,
                        InvoiceNo1
                      );

                      this.CapNhat_BienDong(
                        ma_kh,
                        ten_kh,
                        diachi,
                        sdt,
                        mst,
                        email,
                        5,
                        objHoaDonDienTuEdit,
                        this.serial,
                        1
                      );
                      continue;
                    }

                    this.ChinhSua_HoaDon(//Có phát hành, Có thanh toán
                      2,
                      vcount_id,
                      ma_kh,
                      stt_hd,
                      "",
                      "",
                      0,
                      0,
                      0,
                      "s1",
                      "s1",
                      this.Fkey,
                      InvoicePattern1,
                      SerialNo1,
                      InvoiceNo1
                    );

                    this.CapNhat_BienDong(
                      ma_kh,
                      ten_kh,
                      diachi,
                      sdt,
                      mst,
                      email,
                      5,
                      objHoaDonDienTuEdit,
                      this.serial,
                      0
                    );
                  } else {
                    this.ChinhSua_HoaDon(//Lỗi phát hành, Lỗi thanh toán
                      2,
                      vcount_id,
                      ma_kh,
                      stt_hd,
                      "",
                      "",
                      0,
                      0,
                      0,
                      "s0",
                      "s0",
                      this.Fkey,
                      '',
                      '',
                      ''
                    );
                  }
                }
              }
            }
          }
        }
      } catch (ex) {
        console.log(ex);
      }
    },

    GetInvoiceXml_Cus(ma_kh, ten_kh, mst, diachi, email, sdt) {
      mst = mst || '';

      let str = '';
      str = str.concat("<Customers>");
      str = str.concat("<Customer>");
      str = str.concat("<Name>" + (ten_kh ? "<![CDATA[" + ten_kh + "]]>" : "") + "</Name>");
      str = str.concat("<Code>" + (ma_kh ? "<![CDATA[" + ma_kh + "]]>" : "") + "</Code>");
      str = str.concat("<TaxCode><![CDATA[" + (mst || '') + "]]></TaxCode>");
      str = str.concat("<Address><![CDATA[" + (diachi || '') + "]]></Address>");
      str = str.concat("<BankAccountName></BankAccountName>");
      str = str.concat("<BankName></BankName>");
      str = str.concat("<BankNumber></BankNumber>");
      str = str.concat("<Email>" + (email ? "<![CDATA[" + email + "]]>" : "") + "</Email>");
      str = str.concat("<Fax></Fax>");
      str = str.concat("<Phone>" + (sdt ? "<![CDATA[" + sdt + "]]>" : "") + "</Phone>");
      str = str.concat("<ContactPerson></ContactPerson>");
      str = str.concat("<RepresentPerson></RepresentPerson>");
      str = str.concat("<CusType>0</CusType>");
      str = str.concat("</Customer>");
      str = str.concat("</Customers>");
      return str.toString();
    },

    async GetInvoiceXml(ma_kh, ten_kh, diachi, sdt, mst, vhttt, stt_hd) {
      mst = mst || '';

      this.Fkey = await this.GET_FKEY();
      let str = '';
      str = str.concat("<Invoices>");
      str = str.concat("<Inv>");
      str = str.concat("<key>" + this.Fkey + "</key>");
      str = str.concat("<Invoice>");
      str = str.concat("<CusCode>" + ma_kh + "</CusCode>");
      str = str.concat("<CusName><![CDATA[" + ten_kh + "]]></CusName>");
      str = str.concat("<CusAddress><![CDATA[" + diachi + "]]></CusAddress>");
      str = str.concat("<CusPhone><![CDATA[" + sdt + "]]></CusPhone>");
      str = str.concat("<CusTaxCode>" + mst + "</CusTaxCode>");
      str = str.concat("<PaymentMethod>" + vhttt + "</PaymentMethod>");
      str = str.concat("<KindOfService></KindOfService>");
      str = str.concat("<Products>");

      let dtTemp = this.hoaDonData.dsHoaDon.filter(item => {
        return item.ma_kh === ma_kh && item.stt_hd === stt_hd;
      });

      this.tongtien = 0;
      this.tong_vat = 0;
      this.tylethue = 0;
      this.tienchu = "";

      if (dtTemp && dtTemp.length > 0) {
        this.dtCT_HD = [...dtTemp];
        this.tylethue = parseInt(this.dtCT_HD[0].tyle_vat);
        for (let ct_hd of this.dtCT_HD) {
          let {ten_sp, tongtien, donvitinh, soluong, tyle_vat, dongia, tongtien_vat} = ct_hd
          if (ten_sp != "") {
            this.tongtien += Math.abs(Math.round(parseFloat(tongtien)));
            this.tong_vat += Math.abs(Math.round(parseFloat(tongtien) * parseFloat(tyle_vat) / 100));

            tongtien = Math.abs(Number(tongtien));

            str = str.concat("<Product>");
            str = str.concat("<ProdName>" + ten_sp + "</ProdName>");
            str = str.concat("<ProdUnit>" + donvitinh + "</ProdUnit>");
            str = str.concat("<ProdQuantity>" + soluong + "</ProdQuantity>");
            str = str.concat("<ProdPrice>" + Math.abs(parseFloat(dongia)) + "</ProdPrice>");
            //tt78
            str = str.concat("<VATRate>" + tyle_vat + "</VATRate>");
            str = str.concat("<VATAmount>" + Math.round((tongtien * tyle_vat) / 100) + "</VATAmount>");
            str = str.concat("<Total>" + tongtien + "</Total>");
            str = str.concat("<Amount>" + Math.abs(Number(tongtien_vat)) + "</Amount>");
            str = str.concat("<IsSum>1</IsSum>");
            //end tt78
            str = str.concat("</Product>");
          }
        }
      }
      this.tienchu = await this.doctien_str((this.tongtien + this.tong_vat).toString());

      str = str.concat("</Products>");
      str = str.concat("<Total>" + this.tongtien + "</Total>");
      str = str.concat("<DiscountAmount></DiscountAmount>");
      str = str.concat("<VATRate>" + this.tylethue + "</VATRate>");
      str = str.concat("<VATAmount>" + this.tong_vat + "</VATAmount>");
      str = str.concat("<Amount>" + (this.tongtien + this.tong_vat) + "</Amount>");
      str = str.concat("<AmountInWords>" + this.tienchu + "</AmountInWords>");

      //tt78
      str = str.concat("<GrossValue_NonTax>0</GrossValue_NonTax>");
      str = str.concat("<GrossValue>0</GrossValue>");
      str = str.concat("<GrossValue0>0</GrossValue0>");
      str = str.concat("<VatAmount0>0</VatAmount0>");
      str = str.concat("<GrossValue5>0</GrossValue5>");
      str = str.concat("<VatAmount5>0</VatAmount5>");
      str = str.concat("<GrossValue10>" + (this.tongtien + this.tong_vat) + "</GrossValue10>");
      str = str.concat("<VatAmount10>" + this.tong_vat + "</VatAmount10>");
      //end tt78

      str = str.concat("<Teller>" + this.$root.token.getMaNhanVien() + "</Teller>");
      str = str.concat("</Invoice>");
      str = str.concat("</Inv>");
      str = str.concat("</Invoices>");

      return str;
    },

    ChinhSua_KhachHang(kieu, count_id, ma_kh, ten_kh, mst, diachi, email, sdt, trangthai) {
      try {
        let params = {
          kieu,
          ma_kh,
          ten_kh,
          mst,
          diachi,
          email,
          sdt,
          "nguoi_cn": this.$root.token.getUserName(),
          "ngay_cn": new Date(),
          "may_cn": 'localhost',
          "ip_cn": '127.0.0.1',
          count_id,
          trangthai
        }

        API.capNhatKH(this.axios, {
          p_ds_para: JSON.stringify(params)
        });
      } catch (e) {
        console.log(e);
      }
    },

    ChinhSua_HoaDon(vkieu, vcount_id, vma_kh, vstt_hd, vten_sp,
                    vdonvitinh, vsoluong, vdongia, vtyle_vat, vphathanh_hd, vthanhtoan_hd,
                    vfkey, vinvoice_pattern, vserial_no, vinvoice_no) {
      try {
        API.capNhatHoaDon(this.axios, {
          vkieu,
          vma_kh,
          vstt_hd,
          vten_sp,
          vdonvitinh,
          vsoluong,
          vdongia,
          vtyle_vat,
          vphathanh_hd,
          vthanhtoan_hd,
          vcount_id,
          vfkey,
          vinvoice_pattern,
          vserial_no,
          vinvoice_no,
        });
      } catch (e) {
        console.log(e);
      }
    },

    async TaoDuLieuBD(vma_kh, ten_tt, diachi_tt, sdt, mst, email, kieu_bd, objHoaDonDienTu, _seri_id, loi_thanhtoan) {
      this.dsBDHDDT = [];
      let row = Utils.BD_HDDT_DATA();
      this.BDHDDT_ID = await this.POST_GET_KEYS("BDHDDT_ID");
      row.BDHDDT_ID = this.BDHDDT_ID;
      row.MA_TT = vma_kh;
      row.NHANVIEN_ID = this.$root.token.getNhanVienID();
      row.DONVI_ID = this.$root.token.getDonViID();

      row.KIEU_ID = 1; // Hóa đơn tự quản TTKD
      row.TRANGTHAI = 1; //Hóa đơn đang sử dụng
      row.TEN_TT = ten_tt;
      row.DIACHI_TT = diachi_tt;
      row.SDT = sdt;
      row.MST = mst;
      row.EMAIL = email;
      row.FKEY = this.Fkey;

      row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
      row.TIEN = this.tongtien;
      row.VAT = this.tong_vat;
      row.TYLE_VAT = this.tylethue;
      row.TIENBANGCHU = this.tienchu;

      row.KIEU_BD = kieu_bd; //4: Xuất hóa đơn | 5:  Xuất + Gạch nợ

      // row.THONGTINHD_CU = this.GetXMLFromObject(objHoaDonDienTu);
      row.THONGTINHD_CU = '';
      let dt = [];
      try {
        let rs = await this.axios.post('/web-hopdong/laydulieu/sp_tt_bd_hddt', {
          "p_param": vma_kh,
          "p_param1": this.Fkey,
          "p_type": 1
        });
        dt = rs.data.data || [];
      } catch (e) {
        console.log('TaoDuLieuBD: ' + e);
      }
      if (dt.length > 0)
        row.BDHDDT_CHA_ID = dt[0][0];
      else
        row.BDHDDT_CHA_ID = 0;

      // row.THONGTINHD_MOI = this.GetXMLFromObject(objHoaDonDienTu);
      row.THONGTINHD_MOI = '';
      row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
      row.LOAIHOADON_MOI = objHoaDonDienTu.InvoicePattern;
      row.SERI_CU = objHoaDonDienTu.SerialNo;
      row.SERI_MOI = objHoaDonDienTu.SerialNo;
      row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
      row.SO_HD_MOI = objHoaDonDienTu.InvoiceNo;

      row.TONGTIEN = this.tongtien + this.tong_vat;
      row.NGUOI_CN = this.$root.token.getUserName();
      row.MAY_CN = 'localhost';
      row.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss');
      row.SERI_MOI_ID = _seri_id;
      if (loi_thanhtoan != 0)
        row.THANHTOAN_LOI = loi_thanhtoan;
      this.dsBDHDDT.push(row);
    },


    TaoDuLieuBDCT() {
      this.dsBDHDDTCT = [];
      let i = 1;
      for (let ct_hd of this.dtCT_HD) {

        let {ten_sp, donvitinh, soluong, dongia, tongtien} = ct_hd;
        if (ten_sp != "") {
          let row = Utils.BD_HDDT_CT_DATA();
          row.BDHDDT_ID = this.BDHDDT_ID;
          row.DONGIA = Math.abs(dongia);
          row.DONVITINH = donvitinh;
          row.NOIDUNG = ten_sp;
          row.SOLUONG = soluong;
          row.STT = i;
          row.THANHTIEN = Math.abs(tongtien);

          this.dsBDHDDTCT.push(row);
        }
      }
    },

    async POST_GET_KEYS(keyname) {
      let key = ''
      await API.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          key = response.data.data
        }
      })
      return key
    },

    async LAY_DS_THAMSO_MD_ALL_V2(listCols, order, schema, tableName, where) {
      let ds = []
      let request = {
        'list_of_cols': listCols,
        'order': order,
        'schema': schema,
        'table_name': tableName,
        'where': where
      };

      await API.LAY_DS_THAMSO_MD_ALL_V2(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      });

      return ds
    },

    async GET_FKEY() {
      let key = '';
      await API.GET_FKEY(this.axios)
        .then(response => {
          if (response && response.data && response.data.data && response.data.error && response.data.error === '0') {
            let data = response.data.data
            key = data[0].fkey;
          }
        })
      return key
    },

    GetXMLFromObject(objHoaDonDienTu) {
      let builder = new xml2js.Builder();
      let xml = builder.buildObject(objHoaDonDienTu);
      console.log(xml);
      return xml;
    },

    async doctien_str(money) {
      let str = '';
      await API.DOC_SO_THANH_CHU(this.axios, {money})
        .then(response => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            str = response.data.data
          }
        })
      return str;
    },

    async CapNhat_BienDong(vma_kh, ten_tt, diachi_tt, sdt, mst, email, kieu_bd, objHoaDonDienTu, _seri_id, loi_thanhtoan) {
      await this.TaoDuLieuBD(
        vma_kh,
        ten_tt,
        diachi_tt,
        sdt,
        mst,
        email,
        kieu_bd,
        objHoaDonDienTu,
        _seri_id,
        loi_thanhtoan
      );

      this.TaoDuLieuBDCT();

      let params = {
        js_biendong: this.dsBDHDDT,
        js_ct_biendong: this.dsBDHDDTCT,
      }

      API.CapNhat_BienDong(this.axios, {
        p_ds_para: JSON.stringify(params)
      })
    },

    async invoice_update_cus(khachhang, convert) {
      try {
        let {ma_kh, ten_kh, mst, diachi, email, sdt} = khachhang;

        let rs = await API.invoice_updateCustomer(this.axios, {
          "convert": convert,
          "xmlCusData": this.GetInvoiceXml_Cus(ma_kh, ten_kh, mst, diachi, email, sdt)
        });

        return rs.data.errorCode === 0 ? 'ok' : '-1';
      } catch (e) {

      }

      return '-1';
    },

    async invoice_ImportAndPublishInv_V2(Account, ACpass, xmlInvData, username, pass, pattern, serial, convert) {
      try {

        let rs = await API.invoice_importAndPublishInv(this.axios, {
          "acPass": ACpass,
          "account": Account,
          "convert": convert,
          "pattern": pattern,
          "serial": serial,
          "xmlInvData": xmlInvData
        });

        return rs.data.errorCode === 0 ? rs.data.data : rs.data.faultString;
      } catch (e) {

      }

      return '-1';
    },

    async invoice_confirmPaymentFkey_V2(Fkey, pattern, serial) {
      try {

        let rs = await API.invoice_confirmPaymentFkey(this.axios, {
          "fKey": Fkey,
          "pattern": pattern,
          "serial": serial
        });

        return rs.data.errorCode === 0 ? 'OK:' : rs.data.faultString;
      } catch (e) {

      }

      return '-1';
    },

    async PhatHanh_KhachHang() {
      try {
        let reload = false;
        for (let kh of this.khachHangData.dsKhachHang) {
          if (kh.chon === "s1") {
            let isOK = await this.main_update_cus(kh, this.vcount_id);
            if (!isOK) {
              continue;
            }
            reload = true;
          }
        }

        if (reload)
          await this.HienThi_LichSu(this.vcount_id);
      } catch (ex) {
        console.log(ex);
      }
    },

    async main_update_cus(khachhang, vcount_id) {
      let {ma_kh, ten_kh, mst, diachi, email, sdt} = khachhang;

      let kq1 = await this.invoice_update_cus(khachhang, 0);

      if (kq1 != "ok") {
        this.ChinhSua_KhachHang(2, vcount_id, ma_kh, ten_kh, mst, diachi, email, sdt, "s0");
        return false;
      } else {
        this.ChinhSua_KhachHang(2, vcount_id, ma_kh, ten_kh, mst, diachi, email, sdt, "s1");
        return true;
      }
    },

    async PhatHanh_HoaDon() {
      try {
        let vcount_id = this.vcount_id;
        for (let kh of this.khachHangData.dsKhachHang) {
          if (kh.trangthai === "s1") {
            let {ma_kh, ten_kh, mst, diachi, email, sdt, httt} = kh;

            //phai lay theo ma kh va stt hoa don
            let dtTemp = this.hoaDonData.dsHoaDon.filter(hd => {
              return hd.ma_kh === ma_kh;
            });

            if (dtTemp && dtTemp.length > 0) {
              let stt_hd_temp = '';
              for (let hd of dtTemp) {
                if (hd.chon === "s1") {
                  let {stt_hd, phathanh_hd, thanhtoan_hd, fkey} = hd;
                  if (stt_hd_temp === stt_hd) {
                    continue;
                  }

                  stt_hd_temp = stt_hd;
                  let vhttt = "Tiền mặt/Chuyển khoản";
                  if (httt) {
                    vhttt = httt;
                  }

                  if (phathanh_hd !== "s1") {//Hóa đơn chưa phát hành, chưa thanh toán
                    let xmlInvoice = await this.GetInvoiceXml(ma_kh, ten_kh, diachi, sdt, mst, vhttt, stt_hd);
                    let kq = await this.invoice_ImportAndPublishInv_V2(
                      this.user_hddt_td,
                      this.pass_hddt_td,
                      xmlInvoice,
                      this.user_hddt,
                      this.pass_hddt,
                      this.pattern.toString(),
                      this.serial.toString(),
                      0);

                    let result = kq.split(':');
                    if (result[0] == "OK") {
                      let objHoaDonDienTuEdit = Utils.newContent();
                      let result1 = result[1].split(';');
                      let InvoicePattern1 = result1[0];
                      objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1;

                      let result2 = result1[1].split('-');
                      let SerialNo1 = result2[0];
                      objHoaDonDienTuEdit.SerialNo = SerialNo1;

                      let result3 = result2[1].split('_');
                      let InvoiceNo1 = result3[1];
                      objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1;

                      this.ChinhSua_HoaDon(//Có phát hành, Chưa thanh toán
                        2,
                        vcount_id,
                        ma_kh,
                        stt_hd,
                        "",
                        "",
                        0,
                        0,
                        0,
                        "s1",
                        "0",
                        this.Fkey,
                        InvoicePattern1,
                        SerialNo1,
                        InvoiceNo1
                      );

                      let kq2 = await this.invoice_confirmPaymentFkey_V2(
                        this.Fkey,
                        InvoicePattern1,
                        SerialNo1
                      );

                      if (kq2 != "OK:") {
                        this.ChinhSua_HoaDon(//Có phát hành, Lỗi thanh toán
                          2,
                          vcount_id,
                          ma_kh,
                          stt_hd,
                          "",
                          "",
                          0,
                          0,
                          0,
                          "s1",
                          "s0",
                          this.Fkey,
                          InvoicePattern1,
                          SerialNo1,
                          InvoiceNo1
                        );

                        this.CapNhat_BienDong(
                          ma_kh,
                          ten_kh,
                          diachi,
                          sdt,
                          mst,
                          email,
                          5,
                          objHoaDonDienTuEdit,
                          this.serial,
                          1
                        );
                        continue;
                      }

                      this.ChinhSua_HoaDon(//Có phát hành, Có thanh toán
                        2,
                        vcount_id,
                        ma_kh,
                        stt_hd,
                        "",
                        "",
                        0,
                        0,
                        0,
                        "s1",
                        "s1",
                        this.Fkey,
                        InvoicePattern1,
                        SerialNo1,
                        InvoiceNo1
                      );

                      this.CapNhat_BienDong(
                        ma_kh,
                        ten_kh,
                        diachi,
                        sdt,
                        mst,
                        email,
                        5,
                        objHoaDonDienTuEdit,
                        this.serial,
                        0
                      );
                      this.Fkey = "";
                    } else {
                      this.ChinhSua_HoaDon(//Lỗi phát hành, Lỗi thanh toán
                        2,
                        vcount_id,
                        ma_kh,
                        stt_hd,
                        "",
                        "",
                        0,
                        0,
                        0,
                        "s0",
                        "s0",
                        this.Fkey,
                        '',
                        '',
                        ''
                      );
                    }
                  } else if (thanhtoan_hd !== "s1") {//Đã phát hành, chưa thanh toán
                    this.Fkey = fkey;
                    let {invoice_pattern, serial_no, invoice_no} = hd;
                    let objHoaDonDienTuEdit = Utils.newContent();
                    objHoaDonDienTuEdit.InvoicePattern = invoice_pattern;
                    objHoaDonDienTuEdit.SerialNo = serial_no;
                    objHoaDonDienTuEdit.InvoiceNo = invoice_no;

                    let kq2 = await this.invoice_confirmPaymentFkey_V2(
                      this.Fkey,
                      invoice_pattern,
                      serial_no
                    );

                    if (kq2 != "OK:") {
                      this.ChinhSua_HoaDon(//Có phát hành, Lỗi thanh toán
                        2,
                        this.vcount_id,
                        ma_kh,
                        stt_hd,
                        "",
                        "",
                        0,
                        0,
                        0,
                        "s1",
                        "s0",
                        this.Fkey,
                        invoice_pattern,
                        serial_no,
                        invoice_no
                      );
                      continue;
                    }

                    this.ChinhSua_HoaDon(//Có phát hành, Có thanh toán
                      2,
                      this.vcount_id,
                      ma_kh,
                      stt_hd,
                      "",
                      "",
                      0,
                      0,
                      0,
                      "s1",
                      "s1",
                      this.Fkey,
                      invoice_pattern,
                      serial_no,
                      invoice_no
                    );
                  }
                }
              }
            }
          }
        }
      } catch (ex) {
        console.log(ex);
      }
    },

    async getTTNguoiDung() {
      try {
        let rs = await API.thongtin_nguoidung2(this.axios);
        let {data} = rs.data;

        this.user_hddt_td = data.user_hddt_td;
        this.pass_hddt_td = data.pass_hddt_td;
        this.user_hddt = data.user_hddt;
        this.pass_hddt = data.pass_hddt;
      } catch (e) {

      }
    }
  },
};
</script>
