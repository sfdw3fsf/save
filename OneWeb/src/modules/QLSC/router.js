import MainLayout from "@/layouts/MainLayout";
import Home from "./Home";
import ViPhamCoSoHaTang from "./components/ViPhamCoSoHaTang";
import TraCuuChiTietSuCo from "./tracuu-chitiet-suco";
import TienTrinhXuLySuCo from "./TienTrinhXuLySuCo";
import CapNhatKetQuaXuLySuCo from "./capnhat-ketqua-xuly-suco";
import CauTrucMangBangThongRong from "./CauTrucMangBangThongRong";
import QuanLyDieuHanhTuDong from "./QuanLyDieuHanhTuDong";
import NghiemThuSuCo from "./nghiemthu-suco";
import GiaoPhieu from "./giao-phieu";
import TiepNhanSuCo from "./tiepnhan-suco";
import DanhMucHeThongThietBi from "./DanhMucHeThongThietBi";
import TtsGiaoPhieu from "./frmGiaoPhieu_NET";
import TraCuuDSThueBaoAH from "./TraCuuDSThueBaoAH";
import MaloiDonvinhan from './MaloiDonvinhan';
export default [
  {
    path: "/qlsc",
    name: "qlsc",
    component: MainLayout,
    redirect: "/qlsc/home",
    children: [
      {
        path: "home",
        name: "QLSC home page",
        component: Home,
        meta: {
          title: "Trang chủ"
        }
      },
      {
        path: "vi-pham-co-so-ha-tang",
        name: "ViPhamCoSoHaTang",
        component: ViPhamCoSoHaTang
      },
      {
        path: "tracuu-chitiet-suco",
        name: "TraCuuChiTietSuCo",
        component: TraCuuChiTietSuCo,
        meta: {
          title: "TRA CỨU SỰ CỐ MẠNG VIỄN THÔNG - CÔNG NGHỆ THÔNG TIN"
        }
      },
      {
        path: "tientrinh-xuly-suco",
        name: "TienTrinhXuLySuCo",
        component: TienTrinhXuLySuCo,
        meta: {
          title: "TIẾN TRÌNH XỬ LÝ Sự Cố mạng viễn thông - Công nghệ thông tin"
        }
      },
      {
        path: "capnhat-ketqua-xuly-suco",
        name: "CapNhatKetQuaXuLySuCo",
        component: CapNhatKetQuaXuLySuCo,
        meta: {
          title:
            "Cập Nhật kết quả xử lý sự cố mạng viễn thông - công nghệ thông tin"
        }
      },
      {
        path: "cautruc-mang-bangthong-rong",
        name: "CauTrucMangBangThongRong",
        component: CauTrucMangBangThongRong,
        meta: {
          title: "Cấu trúc mạng băng rộng"
        }
      },
      {
        path: "quanly-dieuhanh-tudong",
        name: "QuanLyDieuHanhTuDong",
        component: QuanLyDieuHanhTuDong
        // meta: {
        //     title: 'QUẢN LÝ NHÂN VIÊN NHẬN SMS'
        // }
      },
      {
        path: "nghiemthu-suco",
        name: "NghiemThuSuCo",
        component: NghiemThuSuCo,
        meta: {
          title: "Nghiệm thu sự cố mạng viễn thông - công nghệ thông tin"
        }
      },
      {
        path: "giao-phieu",
        name: "GiaoPhieu",
        component: GiaoPhieu,
        meta: {
          title: "GIAO PHIẾU SỰ CỐ MẠNG VIỄN THÔNG - CÔNG NGHỆ THÔNG TIN"
        }
      },
      {
        path: "tiepnhan-suco",
        name: "TiepNhanSuCo",
        component: TiepNhanSuCo,
        meta: {
          title: "Tiếp nhận sự cố mạng viễn thông - công nghệ thông tin"
        }
      },
      {
        path: "DanhMucHeThongThietBi",
        name: "DanhMucHeThongThietBi",
        component: DanhMucHeThongThietBi,
        meta: {
          title: "Danh mục hệ thống, thiết bị"
        }
      },
      {
        path: "tts-giao-phieu",
        name: "TtsGiaoPhieu",
        component: TtsGiaoPhieu,
        meta: {
          title: "GIAO PHIẾU SỰ CỐ MẠNG VIỄN THÔNG - CÔNG NGHỆ THÔNG TIN"
        }
      },
      {
        path: "tracuu-danhsach-thuebao-anhhuong",
        name: "TraCuuDSThueBaoAH",
        component: TraCuuDSThueBaoAH,
        meta: {
          title: "Tra cứu chi tiết thuê bao ảnh hưởng theo sự cố"
        }
      },
	  {
        path: "maloi-donvinhan",
        name: "MaloiDonvinhan",
        component: MaloiDonvinhan,
        meta: {
          title: "Mã lỗi đơn vị nhận"
        }
      }
    ]
  }
];
