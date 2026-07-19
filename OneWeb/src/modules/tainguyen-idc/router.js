import MainLayout from '@/layouts/MainLayout'
import Home from './Home/index.vue'
import QuanLyThietBiIDC from './quanly-thietbi-idc'
import DanhMucLoaiRack from './danhmuc-loairack'
// import KhaiBaoTaiNguyenRack from './khaibao-tainguyen-rack'
import DanhSachHeThongThietBi from './danhsach-hethong-thietbi/index.vue'
import TacNghiepHeThongThietBi from './tacnghiep-hethong-thietbi/index.vue'
import ThongTinPhongZone from './thongtin-phong-zone'
import DanhMucMatSan from './DanhMucMatSan/index.vue'
import LoaiVatLieuKetNoi from './loai-vatlieu-ketnoi'
import QuanLyThietBiKhachHang from './quanly-thietbi-khachhang/index.vue'
import DanhSachHeThongThietBiKhachHang from './danhsach-hethong-thietbi-khachhang/index.vue'
import TacNghiepHeThongThietBiKhachHang from './tacnghiep-hethong-thietbi-khachhang/index.vue'
import DanhMucLoaiThongSoTB from './danhmuc-loai-thongso-tb'
import DanhMucIDC from './danhmuc-idc'
import ThongTinU from './thongtin-u'
import LoaiThanhNguon from './loai-thanhnguon'
import DanhMucToaNha from './danhmuc-toanha'
import DanhMucMucDichSuDung from './danhmuc-mucdich-sd'
import DanhMucChuanDauDauNoi from './danhmuc-chuandau-daunoi'
import DanhMucLoaiOcam from './danhmuc-loai-ocam'
import DanhMucChungLoaiTuDia from './danhmuc-chungloai-tudia'
import DanhMucChungLoaiThietBiMang from './danhmuc-chungloai-thietbimang'
import DanhMucLoaiThietBiHeThong from './DanhMucLoaiThietBiHeThong/index.vue'
import DanhMucThanhPhanThietBi from './danhmuc-thanhphan-thietbi/index.vue'
import CapPhatCongSuatDien from './cong-suat-dien/capphat-congsuatdien/CapPhatCongSuatDien.vue'
import DanhMucThanhNguonTrenRack from './danhmuc-thanhnguon-trenrack'
import DanhMucLoaiIP from './DanhMucLoaiIP'
import DanhMucLopKN from './DanhMucLopKetNoi'
import DanhMucLoaiOCung from './DanhMucLoaiOCung'
import ThongTinRack from './ThongTinRack'
import DanhMucOCamTrenThanhNguon from './danhmuc-ocam-tren-thanhnguon'
import IdcBangThongMang from './tainguyen-bangthong-mang'
import DanhSachCongViec from './danhsach-congviec'
import CapPhatDaiIP from './capphat-daiip'
import TiepNhanThiCong from './tiepnhan-thicong'
import TaoKetNoi from './ketnoi-dien'
import DanhSachKetNoi from './danhsach-ketnoi'
import ToaDoMatSan from './toadomatsan'
import KhaiBaoDaiIP from './KhaiBaoDaiIP'
import LapDatRack from './tacnghiep-khaibao-tnkg-rack/lapdat-rack'
import ThaoDoRack from './tacnghiep-khaibao-tnkg-rack/thaodo-rack'
import ThayDoiRack from './tacnghiep-khaibao-tnkg-rack/thaydoi-rack'
import KetQuaLapDatRack from './tacnghiep-khaibao-tnkg-rack/lapdat-rack/ds-ketqua-lapdat-rack'
import KetQuaThaoDoRack from './tacnghiep-khaibao-tnkg-rack/thaodo-rack/ds-ketqua-thaodo-rack'
import KetQuaThayDoiRack from './tacnghiep-khaibao-tnkg-rack/thaydoi-rack/ds-ketqua-thaydoi-rack'
import CapPhatRack from './thuchien-capphat-thuhoi-tnkg/capphat-rack'
import ThuHoiRack from './thuchien-capphat-thuhoi-tnkg/thuhoi-rack'
import KetQuaCapPhatRack from './thuchien-capphat-thuhoi-tnkg/capphat-rack/ds-ketqua-capphat-rack'
import KetQuaThuHoiRack from './thuchien-capphat-thuhoi-tnkg/thuhoi-rack/ds-ketqua-thuhoi-rack'
import CapPhatU from './thuchien-capphat-thuhoi-tnkg/capphat-u'
import ThuHoiU from './thuchien-capphat-thuhoi-tnkg/thuhoi-u'
import KetQuaCapPhatU from './thuchien-capphat-thuhoi-tnkg/capphat-u/ds-ketqua-capphat-u'
import KetQuaThuHoiU from './thuchien-capphat-thuhoi-tnkg/thuhoi-u/ds-ketqua-thuhoi-u'
import TacNghiepHeThongThietBiIDCKH from './tacnghiep-hethong-thietbi-idc-kh-v2'
import ThuHoiCongSuatDien from './cong-suat-dien/thuhoi-congsuatdien/ThuHoiCongSuatDien.vue'
import KetQuaThiCongLapDat from './ketqua-thicong-lapdat/index.vue'
import KetQuaThiCongThaoDo from './ketqua-thicong-thaodo/index.vue'
import KetQuaThiCongThayDoi from './ketqua-thicong-thaydoi/index.vue'
import KetQuaCapPhatBtm from './tainguyen-bangthong-mang/ketqua-capphat-btm/index.vue'
import KetQuaThuHoiBtm from './tainguyen-bangthong-mang/ketqua-thuhoi-btm/index.vue'
import KetQuaCapPhatDaiIP from './ketqua-capphat-daiip/index.vue'
import ThuHoiDaiIP from './thuhoi-daiip'
import ChuyenDoiCongSuatDien from './cong-suat-dien/chuyendoi-congsuatdien/ChuyenDoiCongSuatDien.vue'
import KetQuaCongSuatDien from './ketqua-congsuatdien/index.vue'
// import MapIDC from './Map'
import ThongTinChiTietToanha from './thongtin-chitiet-toanha/index.vue'
import SodoToanhaTrongIdc from './sodo-toanha-trong-idc/index.vue'
import ThongTinLongQuay from './thongtin-longquay/index.vue'
import SoDoMatSanDetail from './SoDoMatSanDetail/index.vue'
import SoDoMatSan from './SoDoMatSan/index.vue'
import SoDoMatSan2 from './sodo-matsan/index.vue'
import SoDoPhongZone from './sodo-phong-zone/index.vue'
import QuanLyTaiNguyen from './quanly-tainguyen/index.vue'
import DanhMucTrangThaiVanHanh from './danhmuc-trangthai-vh'
import DanhMucTrangThaiQuyHoachTBI from './danhmuc-trangthai-quyhoach-tbi/index.vue'
// import DanhMucChungLoaiServer from './danhmuc-chungloai-server/index.vue'
// import ChiTietKetNoiDien from './chitiet-ketnoi-dien/index.vue'
// import SoDoDauNoiMang from './sodo-daunoi-mang/index.vue'
import QuanLyKhoHaTangMang from './QuanLyKhoHaTangMang/index.vue'
export default [
  {
    path: '/tainguyen-idc',
    name: 'Tài nguyên IDC',
    component: MainLayout,
    redirect: '/tainguyen-idc/home',
    children: [
      {
        path: 'home',
        name: 'Tài nguyên IDC',
        component: Home,
        meta: {
          title: 'Trang chủ: Tài nguyên IDC'
        }
      },
      {
        path: 'sodo-phong-zone',
        name: 'Sơ đồ phòng/zone',
        component: SoDoPhongZone,
        meta: {
          title: 'Sơ đồ phòng/zone'
        }
      },
      {
        path: 'sodo-matsan',
        name: 'Sơ đồ mặt sàn',
        component: SoDoMatSan2,
        meta: {
          title: 'Sơ đồ mặt sàn'
        }
      },
      {
        path: 'quanly-tainguyen',
        name: 'Quản lý tài nguyên',
        component: QuanLyTaiNguyen,
        meta: {
          title: 'Quản lý tài nguyên'
        }
      },
      {
        path: 'quanly-thietbi-idc',
        name: 'Quản lý thiết bị IDC',
        component: QuanLyThietBiIDC,
        meta: {
          title: 'Quản lý thiết bị IDC'
        }
      },
      {
        path: 'loai-rack',
        name: 'Danh mục loại rack',
        component: DanhMucLoaiRack,
        meta: {
          title: 'Danh mục loại rack'
        }
      },
      {
        path: 'danhsach-hethong-thietbi',
        name: 'Danh sách hệ thống thiết bị',
        component: DanhSachHeThongThietBi,
        meta: {
          title: 'Danh sách hệ thống thiết bị'
        }
      },
      {
        path: 'tacnghiep-hethong-thietbi',
        name: 'Tác nghiệp hệ thống thiết bị',
        component: TacNghiepHeThongThietBi
      },
      // {
      //   path: 'khaibao-rack',
      //   name: 'Khai báo tài nguyên Rack',
      //   component: KhaiBaoTaiNguyenRack,
      //   meta: {
      //     title: 'Khai báo tài nguyên Rack'
      //   }
      // },
      {
        path: 'capphat-rack',
        name: 'Cấp phát Rack',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 57 } },
        meta: {
          title: 'Cấp phát Rack'
        }
      },
      {
        path: 'ketqua-capphat-rack',
        name: 'Kết quả Cấp phát Rack',
        component: KetQuaCapPhatRack,
        meta: {
          title: 'Kết quả Cấp phát Rack'
        }
      },
      {
        path: 'thuhoi-rack',
        name: 'Thu hồi Rack',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 62 } },
        meta: {
          title: 'Thu hồi Rack'
        }
      },
      {
        path: 'ketqua-thuhoi-rack',
        name: 'Kết quả Thu hồi Rack',
        component: KetQuaThuHoiRack,
        meta: {
          title: 'Kết quả Thu hồi Rack'
        }
      },
      {
        path: 'lapdat-rack',
        name: 'Lắp đặt Rack',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 31 } },
        meta: {
          title: 'Lắp đặt Rack'
        }
      },
      {
        path: 'ketqua-lapdat-rack',
        name: 'Kết quả Lắp đặt Rack',
        component: KetQuaLapDatRack,
        meta: {
          title: 'Kết quả Lắp đặt Rack'
        }
      },
      {
        path: 'thaodo-rack',
        name: 'Tháo dỡ Rack',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 32 } },
        meta: {
          title: 'Tháo dỡ Rack'
        }
      },
      {
        path: 'ketqua-thaodo-rack',
        name: 'Kết quả Tháo dỡ Rack',
        component: KetQuaThaoDoRack,
        meta: {
          title: 'Kết quả Tháo dỡ Rack'
        }
      },
      {
        path: 'thaydoi-rack',
        name: 'Thay đổi Rack',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 63 } },
        meta: {
          title: 'Thay đổi Rack'
        }
      },
      {
        path: 'ketqua-thaydoi-rack',
        name: 'Kết quả Thay đổi Rack',
        component: KetQuaThayDoiRack,
        meta: {
          title: 'Kết quả Thay đổi Rack'
        }
      },
      {
        path: 'capphat-u',
        name: 'Cấp phát U',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 36 } },
        meta: {
          title: 'Cấp phát U'
        }
      },
      {
        path: 'ketqua-capphat-u',
        name: 'Kết quả Cấp phát U',
        component: KetQuaCapPhatU,
        meta: {
          title: 'Kết quả Cấp phát U'
        }
      },
      {
        path: 'thuhoi-u',
        name: 'Thu hồi U',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 42 } },
        meta: {
          title: 'Thu hồi U'
        }
      },
      {
        path: 'ketqua-thuhoi-u',
        name: 'Kết quả Cấp phát U',
        component: KetQuaThuHoiU,
        meta: {
          title: 'Kết quả Thu hồi U'
        }
      },
      {
        path: 'capphat-rack-form',
        name: 'Cấp phát Rack - Thực hiện',
        component: CapPhatRack,
        meta: {
          title: 'Cấp phát Rack - Thực hiện'
        }
      },
      {
        path: 'thuhoi-rack-form',
        name: 'Thu hồi Rack - Thực hiện',
        component: ThuHoiRack,
        meta: {
          title: 'Thu hồi Rack - Thực hiện'
        }
      },
      {
        path: 'lapdat-rack-form',
        name: 'Lắp đặt Rack - Thực hiện',
        component: LapDatRack,
        meta: {
          title: 'Lắp đặt Rack - Thực hiện'
        }
      },
      {
        path: 'thaodo-rack-form',
        name: 'Tháo dỡ Rack - Thực hiện',
        component: ThaoDoRack,
        meta: {
          title: 'Tháo dỡ Rack - Thực hiện'
        }
      },
      {
        path: 'thaydoi-rack-form',
        name: 'Thay đổi Rack - Thực hiện',
        component: ThayDoiRack,
        meta: {
          title: 'Thay đổi Rack - Thực hiện'
        }
      },
      {
        path: 'capphat-u-form',
        name: 'Cấp phát U - Thực hiện',
        component: CapPhatU,
        meta: {
          title: 'Cấp phát U - Thực hiện'
        }
      },
      {
        path: 'thuhoi-u-form',
        name: 'Thu hồi U - Thực hiện',
        component: ThuHoiU,
        meta: {
          title: 'Thu hồi U - Thực hiện'
        }
      },
      {
        path: 'thongtin-phong-zone',
        name: 'Danh mục thông tin Phòng/Zone',
        component: ThongTinPhongZone,
        meta: {
          title: 'Danh mục thông tin Phòng/Zone'
        }
      },
      {
        path: 'mucdich-sd',
        name: 'Danh mục mục đích sử dụng',
        component: DanhMucMucDichSuDung,
        meta: {
          title: 'Danh mục mục đích sử dụng'
        }
      },
      {
        path: 'chuandau-daunoi',
        name: 'Danh mục chuẩn đầu đấu nối',
        component: DanhMucChuanDauDauNoi,
        meta: {
          title: 'Danh mục chuẩn đầu đấu nối'
        }
      },
      {
        path: 'loai-ocam',
        name: 'Danh mục loại ổ cắm',
        component: DanhMucLoaiOcam,
        meta: {
          title: 'Danh mục loại ổ cắm'
        }
      },
      {
        path: 'chungloai-tudia',
        name: 'Danh mục chủng loại tủ đĩa',
        component: DanhMucChungLoaiTuDia,
        meta: {
          title: 'Danh mục chủng loại tủ đĩa'
        }
      },
      {
        path: 'chungloai-tbimang',
        name: 'Danh mục chủng loại thiết bị mạng',
        component: DanhMucChungLoaiThietBiMang,
        meta: {
          title: 'Danh mục chủng loại thiết bị mạng'
        }
      },
      {
        path: 'danhmuc-matsan',
        name: 'Danh mục mặt sàn',
        component: DanhMucMatSan,
        meta: {
          title: 'Danh mục mặt sàn'
        }
      },
      {
        path: 'loai-vatlieu-ketnoi',
        name: 'LoaiVatLieuKetNoi',
        component: LoaiVatLieuKetNoi,
        meta: {
          title: 'Danh mục loại vật liệu kết nối'
        }
      },
      {
        path: 'quanly-thietbi-khachhang',
        name: 'Quản lý thiết bị khách hàng',
        component: QuanLyThietBiKhachHang,
        meta: {
          title: 'Quản lý thiết bị khách hàng'
        }
      },
      {
        path: 'danhsach-hethong-thietbi-khachhang',
        name: 'Danh sách hệ thống thiết bị khách hàng',
        component: DanhSachHeThongThietBiKhachHang,
        meta: {
          title: 'Danh sách hệ thống thiết bị khách hàng'
        }
      },
      {
        path: 'tacnghiep-hethong-thietbi-khachhang',
        name: 'Tác nghiệp hệ thống thiết bị khách hàng',
        component: TacNghiepHeThongThietBiKhachHang
      },
      {
        path: 'danhmuc-loai-thongso-thietbi',
        name: 'Danh mục loại thông số thiết bị',
        component: DanhMucLoaiThongSoTB,
        meta: {
          title: 'Danh mục loại thông số thiết bị'
        }
      },
      {
        path: 'danhmuc-idc',
        name: 'Danh mục IDC',
        component: DanhMucIDC,
        meta: {
          title: 'Danh mục IDC'
        }
      },
      {
        path: 'thongtin-u',
        name: 'Thông tin U',
        component: ThongTinU,
        meta: {
          title: 'Thông Tin U'
        }
      },
      {
        path: 'loai-thanhnguon',
        name: 'Danh mục loại thanh nguồn',
        component: LoaiThanhNguon,
        meta: {
          title: 'Danh mục loại thanh nguồn'
        }
      },
      {
        path: 'danhmuc-toanha',
        name: 'Danh mục tòa nhà',
        component: DanhMucToaNha,
        meta: {
          title: 'Danh mục Tòa nhà'
        }
      },
      {
        path: 'capphat-congsuatdien/:phieutc?',
        name: 'Cấp phát công suất điện',
        component: CapPhatCongSuatDien,
        meta: {
          title: 'Cấp phát công suất điện'
        }
      },
      {
        path: 'danhmuc-loai-thiet-bi-he-thong',
        name: 'Danh mục loại thiết bị hệ thống',
        component: DanhMucLoaiThietBiHeThong,
        meta: {
          title: 'Danh mục loại thiết bị hệ thống'
        }
      },
      {
        path: 'thanhphan-thietbi',
        name: 'Danh mục thành phần thiết bị',
        component: DanhMucThanhPhanThietBi,
        meta: {
          title: 'Danh mục thành phần thiết bị'
        }
      },
      {
        path: 'danhmuc-thanhnguon-trenrack',
        name: 'Danh mục thanh nguồn trên rack',
        component: DanhMucThanhNguonTrenRack,
        meta: {
          title: 'Danh mục thanh nguồn trên rack'
        }
      },
      {
        path: 'danhmuc-loai-ip',
        name: 'Danh mục Loại IP',
        component: DanhMucLoaiIP,
        meta: {
          title: 'Danh mục Loại IP'
        }
      },
      {
        path: 'danhmuc-lop-ketnoi',
        name: 'Danh mục Lớp kết nối',
        component: DanhMucLopKN,
        meta: {
          title: 'Danh mục Lớp kết nối'
        }
      },
      {
        path: 'danhmuc-loai-ocung',
        name: 'Danh mục Loại ổ cứng',
        component: DanhMucLoaiOCung,
        meta: {
          title: 'Danh mục Loại ổ cứng'
        }
      },
      {
        path: 'thongtin-rack',
        name: 'Thông tin Rack',
        component: ThongTinRack,
        meta: {
          title: 'Thông tin Rack'
        }
      },
      {
        path: 'idc-bangthong-mang',
        name: 'Cấp phát/thu hồi Băng thông mạng',
        component: IdcBangThongMang,
        meta: {
          title: 'Cấp phát/thu hồi Băng thông mạng'
        }
      },
      {
        path: 'danhmuc-ocam-tren-thanhnguon',
        name: 'Danh mục ổ cắm trên thanh nguồn',
        component: DanhMucOCamTrenThanhNguon,
        meta: {
          title: 'Danh mục ổ cắm trên thanh nguồn'
        }
      },
      {
        path: 'danhsach-congviec',
        name: 'Danh sách công việc',
        component: DanhSachCongViec,
        meta: {
          title: 'Danh sách công việc'
        }
      },
      {
        path: 'tiepnhan-thicong',
        name: 'Tiếp nhận thi công',
        component: TiepNhanThiCong,
        meta: {
          title: 'Tiếp nhận thi công'
        }
      },
      {
        path: 'tacnghiep-hethong-thietbi-idc-kh/:phieutc?',
        name: 'Tác nghiệp hệ thống thiết bị khách hàng',
        component: TacNghiepHeThongThietBiIDCKH,
        meta: {
          title: 'Tác nghiệp hệ thống thiết bị khách hàng'
        }
      },
      // {
      //   path: 'danhsach-congviec?loaicv=30',
      //   name: 'Danh sách thi công điện',
      //   component: DanhSachCongViec,
      //   meta: {
      //     title: 'Danh sách thi công điện'
      //   }
      // },
      {
        path: 'ketnoi/:phieutc?',
        name: 'Kết nối',
        component: TaoKetNoi,
        meta: {
          title: 'Kết nối'
        }
      },
      // {
      //   path: 'danhsach-ketnoi',
      //   name: 'Danh sách kết nối',
      //   component: DanhSachKetNoi,
      //   meta: {
      //     title: 'Danh sách kết nối'
      //   }
      // },
      {
        path: 'toadomatsan',
        name: 'Tọa độ mặt sàn',
        component: ToaDoMatSan,
        meta: {
          title: 'Tọa độ mặt sàn'
        }
      },
      {
        path: 'KhaiBaoDaiIP',
        name: 'Khai báo Dải IP',
        component: KhaiBaoDaiIP,
        meta: {
          title: 'Khai báo Dải IP'
        }
      },
      {
        path: 'ketqua-thicong-lapdat',
        name: 'Kết quả thi công lắp đặt',
        component: KetQuaThiCongLapDat,
        meta: {
          title: 'Quản lý thi công hệ thống thiết bị'
        }
      },
      {
        path: 'ketqua-thicong-lapdat',
        name: 'Kết quả thi công lắp đặt',
        component: KetQuaThiCongLapDat,
        meta: {
          title: 'Kết quả thi công lắp đặt'
        }
      },
      {
        path: 'ketqua-thicong-thaodo',
        name: 'Kết quả thi công tháo dỡ',
        component: KetQuaThiCongThaoDo,
        meta: {
          title: 'Kết quả thi công tháo dỡ'
        }
      },
      {
        path: 'ketqua-thicong-thaydoi',
        name: 'Kết quả thi công thay đổi',
        component: KetQuaThiCongThayDoi,
        meta: {
          title: 'Kết quả thi công thay đổi thông tin lắp đặt'
        }
      },
      {
        path: 'ketqua-capphat-btm',
        name: 'Kết quả cấp phát băng thông mạng',
        component: KetQuaCapPhatBtm,
        meta: {
          title: 'Kết quả cấp phát băng thông mạng'
        }
      },
      {
        path: 'ketqua-thuhoi-btm',
        name: 'Kết quả thu hồi băng thông mạng',
        component: KetQuaThuHoiBtm,
        meta: {
          title: 'Kết quả thu hồi băng thông mạng'
        }
      },
      {
        path: 'lapdat-ketnoi-dien/:type',
        name: 'lapdat-ketnoi-dien',
        component: DanhSachKetNoi,
        props: (route) => ({
          loaicv: 30, // cố định
          type: route.params.type // type: 'khachhang' hoặc 'idc'
        }),
        meta: { title: 'Lắp đặt kết nối điện' }
      },
      {
        path: 'thaogo-ketnoi-dien/:type',
        name: 'thaogo-ketnoi-dien',
        component: DanhSachKetNoi,
        props: (route) => ({
          loaicv: 65,
          type: route.params.type
        }),
        meta: { title: 'Tháo gỡ kết nối điện' }
      },
      {
        path: 'thaydoi-tt-ketnoi-dien/:type',
        name: 'thaydoi-tt-ketnoi-dien',
        component: DanhSachKetNoi,
        props: (route) => ({
          loaicv: 66,
          type: route.params.type
        }),
        meta: { title: 'Thay đổi thông tin kết nối điện' }
      },
      {
        path: 'lapdat-ketnoi-mang/:type',
        name: 'lapdat-ketnoi-mang',
        component: DanhSachKetNoi,
        props: (route) => ({
          loaicv: 56,
          type: route.params.type
        }),
        meta: { title: 'Lắp đặt kết nối mạng' }
      },
      {
        path: 'thaogo-ketnoi-mang/:type',
        name: 'thaogo-ketnoi-mang',
        component: DanhSachKetNoi,
        props: (route) => ({
          loaicv: 68,
          type: route.params.type
        }),
        meta: { title: 'Tháo gỡ kết nối mạng' }
      },
      {
        path: 'thaydoi-tt-ketnoi-mang/:type',
        name: 'thaydoi-tt-ketnoi-mang',
        component: DanhSachKetNoi,
        props: (route) => ({
          loaicv: 67,
          type: route.params.type
        }),
        meta: { title: 'Thay đổi thông tin kết nối mạng' }
      },
      // Danh sách - Thi công - Kết quả CẤP PHÁT cho dải IP và IP
      {
        path: 'capphat-dai-ip',
        name: 'Cấp phát dải IP',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 39 } },
        meta: {
          title: 'Cấp phát dải IP'
        }
      },
      {
        path: 'capphat-ip',
        name: 'Cấp phát IP',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 40 } },
        meta: {
          title: 'Cấp phát IP'
        }
      },
      {
        path: 'tc-capphat-ip/:phieutc?',
        name: 'Thi công cấp phát Dải IP - IP',
        component: CapPhatDaiIP,
        meta: {
          title: 'Thi công cấp phát Dải IP - IP'
        }
      },
      {
        path: 'kq-capphat-ip/:loaicv?',
        name: 'Kết quả cấp phát Dải IP - IP',
        component: KetQuaCapPhatDaiIP,
        meta: {
          title: 'Kết quả cấp phát Dải IP - IP'
        }
      },
      // Danh sách - Thi công - Kết quả THU HỒI cho IP hoặc dải IP
      {
        path: 'thuhoi-dai-ip',
        name: 'Thu hồi dải IP',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 45 } },
        meta: {
          title: 'Thu hồi dải IP'
        }
      },
      {
        path: 'thuhoi-ip',
        name: 'Thu hồi IP',
        redirect: { path: '/tainguyen-idc/danhsach-congviec', query: { loaicv: 46 } },
        meta: {
          title: 'Thu hồi IP'
        }
      },
      {
        path: 'tc-thuhoi-ip/:phieutc?',
        name: 'Thi công thu hồi Dải IP - IP',
        component: ThuHoiDaiIP,
        meta: {
          title: 'Thi công thu hồi Dải IP - IP'
        }
      },
      {
        path: 'kq-thuhoi-ip/:loaicv?',
        name: 'Kết quả thu hồi Dải IP - IP',
        component: KetQuaCapPhatDaiIP,
        meta: {
          title: 'Kết quả thu hồi Dải IP - IP'
        }
      },
      {
        path: 'thuhoi-congsuatdien/:phieutc?',
        name: 'Thu hồi công suất điện',
        component: ThuHoiCongSuatDien,
        meta: {
          title: 'Thu hồi công suất điện'
        }
      },
      {
        path: 'chuyendoi-congsuatdien/:phieutc?',
        name: 'Chuyển đổi công suất điện',
        component: ChuyenDoiCongSuatDien,
        meta: {
          title: 'Chuyển đổi công suất điện'
        }
      },
      {
        path: 'ketqua-congsuatdien/:loaicv?',
        name: 'Kết quả công suất điện',
        component: KetQuaCongSuatDien,
        meta: {
          title: 'Kết quả công suất điện'
        }
      },
      // {
      //   path: 'map',
      //   name: 'MapIDC',
      //   component: MapIDC,
      //   meta: {
      //     title: 'MapIDC'
      //   }
      // },
      {
        path: 'thongtin-chitiet-toanha/:toanha?',
        name: 'Thông tin chi tiết tòa nhà IDC',
        component: ThongTinChiTietToanha,
        meta: {
          title: 'Thông tin chi tiết tòa nhà IDC'
        }
      },
      {
        path: 'sodo-toanha-trong-idc/:idc?',
        name: 'Sơ đồ tòa nhà trong IDC',
        component: SodoToanhaTrongIdc,
        meta: {
          title: 'Sơ đồ tòa nhà trong IDC'
        }
      },
      {
        path: 'thongtin-longquay',
        name: 'Thông tin Lồng Quây',
        component: ThongTinLongQuay,
        meta: {
          title: 'Thông tin Lồng Quây'
        }
      },
      // SO DO MAT SAN
      {
        path: 'chi-tiet-mat-san/:matSanId?',
        name: 'Chi tiết mặt sàn',
        component: SoDoMatSanDetail,
        meta: {
          title: 'Chi tiết mặt sàn'
        }
      },
      {
        path: 'so-do-mat-san/:toaNhaId?',
        name: 'Danh sách mặt sàn',
        component: SoDoMatSan,
        meta: {
          title: 'Tài nguyên IDC: Sơ đồ mặt sàn toà nhà'
        }
      },
      {
        path: 'danhmuc-trangthai-vh',
        name: 'Danh mục trạng thái vận hành',
        component: DanhMucTrangThaiVanHanh,
        meta: {
          title: 'Danh mục trạng thái vận hành'
        }
      },
      {
        path: 'danhmuc-trangthai-quyhoach-tbi',
        name: 'Danh mục trạng thái quy hoạch thiết bị',
        component: DanhMucTrangThaiQuyHoachTBI,
        meta: {
          title: 'Danh mục trạng thái quy hoạch thiết bị'
        }
      },
      {
        path: 'donvi-sohuu',
        name: 'Đơn Vị Sở Hữu',
        component: () => import('./danh-muc-don-vi/index.vue'),
        meta: {
          title: "Đơn Vị Sở Hữu"
        }
      },
      {
        path: 'quanly-vlan',
        name: 'Quản Lý VLAN',
        component: () => import('./quanly-vlan-v2/index.vue'),
        meta: {
          title: "Quản Lý VLAN"
        }
      },
      // {
      //   path: 'chi-tiet-ketnoi-dien',
      //   name: 'Chi tiết kết nối điện',
      //   component: ChiTietKetNoiDien,
      //   meta: {
      //     title: 'Chi tiết kết nối điện'
      //   }
      // },
      // {
      //   path: 'sodo-daunoi-mang',
      //   name: 'Sơ đồ đầu nối mạng',
      //   component: SoDoDauNoiMang,
      //   meta: {
      //     title: 'Sơ đồ đầu nối mạng'
      //   }
      // },
      // {
      //   path: 'danhmuc-chungloai-server',
      //   name: 'Danh mục chủng loại server',
      //   component: DanhMucChungLoaiServer,
      //   meta: {
      //     title: 'Danh mục chủng loại server'
      //   }
      // }
      {
        path: 'quan-ly-kho-ha-tang-mang',
        name: 'Quản lý kho hạ tầng mạng',
        component: QuanLyKhoHaTangMang,
        meta: {
          title: 'Quản lý kho hạ tầng mạng'
        }
      }
    ]
  }
]
