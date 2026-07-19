import XLSX from "xlsx";

export default {
  getColsString(cols) {
    if (cols != null && cols.length > 0) {
      let txt = '';
      for (let col of cols) {
        txt += col.toUpperCase() + ", ";
      }

      if (txt.length > 0) {
        txt = txt.substring(0, txt.length - 2);
        return txt;
      }
    }

    return '';
  },

  isFileExcel(file) {
    try {
      if (file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ||
        file.type === 'application/vnd.ms-excel') {
        return true;
      }
    } catch (e) {

    }

    this.$toast.error('Vui lòng chọn file có định dạng .xls hoặc .xlsx');
    return false;
  },

  readFile(dataBinary, colsRequired) {
    let workbook = XLSX.read(dataBinary, {
      type: 'binary'
    });

    let sheet = workbook.Sheets[workbook.SheetNames[0]];
    let headers = this.getHeaderRow(sheet);
    if (headers.length <= 0) {
      throw 'Tên cột trong file không đúng định dạng: ' + this.getColsString(colsRequired);
    }

    for (let header of headers) {
      if (!colsRequired.includes(header)) {
        throw 'Tên cột trong file không đúng định dạng: ' + this.getColsString(colsRequired);
      }
    }

    return XLSX.utils.sheet_to_json(sheet, {
      header: colsRequired, range: 1
    });
  },

  getHeaderRow(sheet) {
    let headers = [];
    let range = XLSX.utils.decode_range(sheet['!ref']);
    let C, R = range.s.r; /* start in the first row */
    /* walk every column in the range */
    for (C = range.s.c; C <= range.e.c; ++C) {
      let cell = sheet[XLSX.utils.encode_cell({c: C, r: R})] /* find the cell in the first row */

      let hdr = "UNKNOWN " + C; // <-- replace with your desired default
      if (cell && cell.t) hdr = XLSX.utils.format_cell(cell);

      headers.push(hdr.toLowerCase());
    }
    return headers;
  },

  checkNullInObject(obj, key) {
    if (obj[key] === undefined || obj[key] === null || obj[key] === '') {
      return true;
    }

    let val = obj[key];
    return val.toString().trim() === '';
  },

  lowercaseKeys(obj) {
    return Object.keys(obj).reduce((acc, key) => {
      acc[key.toLowerCase()] = obj[key];
      return acc;
    }, {});
  },

  newContent() {
    return {
      ArisingDate: '',
      InvoiceName: '',
      InvoicePattern: '',
      SerialNo: '',
      InvoiceNo: '',
      Kind_of_Payment: '',
      ComName: '',
      ComTaxCode: '',
      ComAddress: '',
      ComPhone: '',
      ComBankNo: '',
      CusCode: '',
      CusName: '',
      CusTaxCode: '',
      CusPhone: '',
      CusAddress: '',
      CusBankName: '',
      Total: '',
      VAT_Amount: '',
      Amount: '',
      Amount_words: '',
      Buyer: '',
      KindOfService: '',
      VAT_Rate: '',
      Discount_Rate: '',
      Discount_Amount: '',
      EmailDeliver: '',
      Extra: '',
      SignDate: '',
      Products: null,
      MaThanhToan: '',
    }
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

  BD_HDDT_CT_DATA() {
    return {
      THANHTIEN: '',
      SOLUONG: '',
      DONVITINH: '',
      NOIDUNG: '',
      STT: '',
      BDHDDT_ID: '',
      DONGIA: '',
    }
  }
}
