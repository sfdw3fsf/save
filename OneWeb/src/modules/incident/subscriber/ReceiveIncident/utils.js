export default {
  BAOHONG_TMP_DATA() {
    return {
      RSC_ID: null,
      THUEBAO_ID: null,
      DICHVUVT_ID: null,
      LOAITB_ID: null,
      NGAY_BH: null,
      NGUOI_BH: '',
      DIENTHOAI_LH: '',
      GHICHU_HONG: '',
      DONVI_ID: null,
      NGAY_CN: null,
      NGUOI_CN: '',
      NHANVIEN_ID: null,
      HENSUATU: null,
      HENSUADEN: null,
      DAIKV_ID: null,
      TRAMVT_ID: null,
      MAY_CN: '',
      MA_LT: '',
    }
  },

  BAOHONG_DATA() {
    return {
      BAOHONG_ID: -1,
      THUEBAO_ID: -1,
      DICHVUVT_ID: -1,
      LOAITB_ID: -1,
      MA_TB: null,
      NGAY_BH: null,
      NGUOI_BH: '',
      DIENTHOAI_LH: '',
      GHICHU_HONG: '',
      TTBH_ID: -1,
      DONVI_ID: -1,
      MAY_CN: '',
      NGAY_CN: null,
      NGUOI_CN: '',
      NHANVIEN_ID: -1,
      HENSUATU: null,
      HENSUADEN: null,
      NGAY_HT: null,
      QUYTRINH_ID: -1,
      GOIKT_ID: -1,
      CHITIEU_TG: -1,
      MA_LT: '',
      HT_PORT: '',
      MA_BH: '',
      KENHTN_ID: -1,
      BAOHONG_ID_HNI: -1,
      DIENTHOAI_BH: '',
      TEN_TB: '',
      DIACHI_LD: '',
      NGUOI_TAO: ''
    }
  },

  BAOHONG_DV_DATA() {
    return {
      BAOHONG_ID: -1,
      DONVI_ID: -1,
      LOAIDV_ID: -1,
      KIEUDV_ID: -1,
    }
  },

  TINHTRANG_BH_DATA() {
    return {
      BAOHONG_ID: -1,
      TINHTRANG_ID: -1,
    }
  },

  NGUYENNHAN_BH_DATA() {
    return {
      CTHONG_ID: -1,
      PHIEU_ID: -1,
      HANGSX_ID: -1,
      HANG_SX: -1,
      HARDWARE_VERSION: -1,
      FIRMWARE_VERSION: -1,
    }
  },

  BAOHONG_TON_DATA() {
    return {
      BAOHONG_ID: -1,
      CTTON_ID: -1
    }
  },

  TO_KHONG_DAU(str) {
    str = str.replaceAll(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replaceAll(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replaceAll(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replaceAll(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replaceAll(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replaceAll(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replaceAll(/đ/g, "d");
    str = str.replaceAll(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
    str = str.replaceAll(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
    str = str.replaceAll(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
    str = str.replaceAll(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
    str = str.replaceAll(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
    str = str.replaceAll(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
    str = str.replaceAll(/Đ/g, "D");
    // Some system encode vietnamese combining accent as individual utf-8 characters
    // Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
    str = str.replaceAll(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
    str = str.replaceAll(/\u02C6|\u0306|\u031B/g, ""); // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
    // Remove extra spaces
    // Bỏ các khoảng trắng liền nhau
    str = str.replaceAll(/ + /g, " ");
    str = str.trim();
    // Remove punctuations
    // Bỏ dấu câu, kí tự đặc biệt
    // str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
    return str;
  }
}
