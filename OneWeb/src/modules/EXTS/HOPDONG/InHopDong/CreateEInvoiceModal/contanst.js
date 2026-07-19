const customerInformation = {
  customerCode: '',
  customerName: '',
  birthDay: 'yyyy-mm-dd',
  gender: 0, //1,2,3
  quocTich: '',
  citizenIdentificationNumber: '', //CMT
  dateOfIssuanceOfCid: 'yyy-mm-dd', //ngay cap
  placeOfIssuanceOfCid: '',
  address: '',
  cluster: '',
  wardId: '',
  districtId: '',
  provinceId: '',
  phoneNumber: '',
  email: '',
  otherContactInformation: '',
  visa: ''
}
const businessCustomerInformation = {
  daiDien: {
    customerCode: '',
    ten: '',
    chucVu: '',
    ngaySinh: '',
    cmnd: '',
    ngayCap: '',
    noiCap: '',
    tinhId: '',
    huyenId: '',
    xaId: '',
    soNha: '',
    duong: '',
    sdt: '',
    email: '',
    giayUyQuyenSo: '',
    ngayCapUyQuyen: ''
  },
  toChuc: {
    tenToChuc: '',
    sdt: '',
    email: '',
    soDkdn: '',
    ngayCapSoDkdn: '',
    noiCapSoDkdn: '',
    diaChiTruSoTinhId: '',
    diaChiTruSoHuyenId: '',
    diaChiTruSoXaId: '',
    diaChiTruSoSoNha: '',
    diaChiTruSoDuong: '',
    diaChiTruSoTinhId: '',
    maSoThue: '',
    nganHangGiaoDich: '',
    soTaiKhoanGiaoDich: '',
    subMail: '',
    fax: ''
  }
}

const paramCreateEContract = {
  customerInformation: customerInformation,
  businessCustomerInformation: businessCustomerInformation,
  areaCode: '',
  orderCode: '',
  transactionCode: '',
  subscriptionCode: '',
  unitCode: '',
  hrmCodeOfCreator: '', //Mã HRM người tạo hợp đồng
  hrmCodeOfSigner: '', //Mã HRM của lãnh đạo ký hợp đồng
  signForm: '',
  contractTemplateId: '' //Mã hợp đồng mẫu
}

export default {
  customerInformation,
  businessCustomerInformation,
  paramCreateEContract
}

// const paramCreateEContract = {
//     customerInformation: {
//         customerCode: "",
//         customerName: "",
//         birthDay: "yyyy-mm-dd",
//         gender: 0, //1,2,3
//         quocTich: "",
//         citizenIdentificationNumber: "", //CMT
//         dateOfIssuanceOfCid: "yyy-mm-dd", //ngay cap
//         placeOfIssuanceOfCid: "",
//         address: "",
//         cluster: "",
//         wardId: "",
//         districtId: "",
//         provinceId: "",
//         phoneNumber: "",
//         email: "",
//         otherContactInformation: "",
//         visa: "",
//     },
//     businessCustomerInformation: {

//         daiDien: {
//             customerCode: "",
//             ten: "",
//             chucVu: "",
//             ngaySinh: "",
//             cmnd: "",
//             ngayCap: "",
//             noiCap: "",
//             tinhId: "",
//             huyenId: "",
//             xaId: "",
//             soNha: "",
//             duong: "",
//             sdt: "",
//             email: "",
//             giayUyQuyenSo: "",
//             ngayCapUyQuyen: "",
//         },
//         toChuc: {
//             tenToChuc: "",
//             sdt: "",
//             email: "",
//             soDkdn: "",
//             ngayCapSoDkdn: "",
//             noiCapSoDkdn: "",
//             diaChiTruSoTinhId: "",
//             diaChiTruSoHuyenId: "",
//             diaChiTruSoXaId: "",
//             diaChiTruSoSoNha: "",
//             diaChiTruSoDuong: "",
//             diaChiTruSoTinhId: "",
//             maSoThue: "",
//             nganHangGiaoDich: "",
//             soTaiKhoanGiaoDich: "",
//             subMail: "",
//             fax: "",
//         }

//     },
//     areaCode: "",
//     orderCode: "",
//     transactionCode: "",
//     subscriptionCode: "",
//     unitCode: "",
//     hrmCodeOfCreator: "", //Mã HRM người tạo hợp đồng
//     hrmCodeOfSigner: "", //Mã HRM của lãnh đạo ký hợp đồng
//     signForm: "",
//     contractTemplateId: "", //Mã hợp đồng mẫu
// }
let datatest = {
  customerInformation: {
    customerCode: 'Test customerCode',
    customerName: 'Test customerName',
    birthDay: '01/01/1980',
    gender: 1,
    nationality: 'Việt Nam',
    citizenIdentificationNumber: 187763009,
    dateOfIssuanceOfCid: '22/07/2019',
    placeOfIssuanceOfCid: 'Cục quản lý xuất nhập cảnh',
    address: 'Test address',
    cluster: 'Test to dia chi',
    wardId: 8232449,
    districtId: 83051,
    provinceId: 887,
    phoneNumber: '0944139607',
    email: 'haphan01@yopmail.com'
  },
  businessCustomerInformation: {
    daiDien: {
      customerCode: 'Test customerCode KHDN',
      ten: 'Test KHDN',
      chucVu: 'Test chucvu KHDN'
    },
    toChuc: {
      tenToChuc: 'Test KHDN',
      sdt: '0123456789',
      email: 'testkhdn@yopmail.com',
      soDkdn: 123456789,
      ngayCapSoDkdn: '01/01/2020',
      noiCapSoDkdn: 'DN',
      diaChiTruSoTinhId: 887,
      diaChiTruSoHuyenId: 83051,
      diaChiTruSoXaId: 8232449,
      maSoThue: '0123159555'
    }
  },
  dataRender: {
    soHopDong: 'HDHPG0835982214',
    tenKhachHang: 'Công Ty TNHH Một Thành Viên Thương Mại TiKi',
    diaChiKhachHang: 'Tổ 12a, Phường Bắc Sơn, Quận Kiến An1Phường Bắc Sơn - Quận Kiến An - Hải Phòng - ',
    sdtKhachHang: '0915685697',
    faxKhachHang: 2309853252,
    emailGiaoDichKH: 'haiphong@yopmail.com',
    mstToChucKH: 2209202216,
    taiKhoanSoKH: 2352535,
    taiNganHangKH: 'VCB',
    quocTichKH: 'Việt Nam',
    soGiayToKH: 'C7891135',
    noiCapGiayToKH: 'Hải Phòng',
    ngayCapGiayToKH: '22/07/2019',
    nguoiDaiDienKH: 'nguyen văn A',
    chucVuNguoiDaiDien: 'C7891135',
    giayUyQuyenSoKH: 'C7891135',
    ngayCapGiayUyQuyenKH: '22/07/2019',
    cmndNguoiDaiDienKH: 'C7891135',
    noiCapCmndKH: 'Hải Phòng',
    ngayCapCmndKH: '22/07/2019',
    thongTinKhacKH: 'C7891135',
    sdtSuDungCTS: '0915685697',
    diaChiEmailCTS: 'haiphong@yopmail.com',
    thsdThang: 6,
    tongTienBangChu: 'Một triệu',
    tenCTS: 'Ngo',
    chucVuCTS: 'C7891135',
    cmndCTS: 'C7891135',
    ngayCapCmndCTS: '22/07/2019',
    noiCapCmndCTS: 'Hải Phòng',
    diaDanh: 'Hải Phòng',
    ngay: 22,
    thang: '09',
    dsTong1: 1000000,
    dsTong2: 0,
    dsTong3: 1000000,
    noiKy: 'Hải Phòng',
    ds: [
      {
        tt: 1,
        dichvu: 'SmartCA_1608, SmartCA Cá nhân Cơ bản 1 năm',
        dvt: 'Gói',
        soluong: 1,
        dongia: 1000000,
        thanhtien: 1000000,
        tienthue: 1000000,
        cong: 1000000
      }
    ],
    htccdv: 2,
    dtkh: [1, 2, 3],
    thietbisddv: [1, 2, 3],
    smartCA: 1,
    thsd: [1, 2, 3, 4],
    goiDVTC: [1, 2],
    goiDVCN: [1, 2],
    goiDV1Lan: 1,
    benTaoKhoa: [],
    vnptCA: []
  },
  orderCode: 'SmartCaaaaax',
  areaCode: 3000,
  transactionCode: 'Test transactionCode',
  subscriptionCode: 'Test subscriptionCode',
  unitCode: 'Test unitCode',
  hrmCodeOfCreator: 'IT02066',
  signForm: 'OTP',
  contractTemplateId: '63f481315736ebd2a3e661d4'
}
