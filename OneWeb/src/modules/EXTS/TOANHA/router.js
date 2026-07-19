import MainLayout from "@/layouts/MainLayout";
import Home from "./Home";
import DanhMucToChucQLDA from './DanhMucToChucQLDA'
import DanhMucDoiTac from './DanhMucDoiTac'
import DanhMucDuAn from "./DanhMucDuAn";
import CapNhatDoanhThuBanCheo from "./CapNhatDoanhThuBanCheo";
import DanhMucHopDongDauTu from "./DanhMucHopDongDauTu";
import TienTrinhDauTu from "./TienTrinhDauTu";
import ChiTietHDDTU from "./ChiTietHDDTU";
import GanKetCuoiVoiToaNhaTheoFile from "./GanKetCuoiVoiToaNhaTheoFile";
import BangGiaKenhTSl from "./BangGiaKenhTSL/main";
import GanThueBaoVaoToaNhaTheoFile from "./GanThueBaoVaoToaNhaTheoFile"
import GanThueBaoVaoToaNha from "./GanThueBaoVaoToaNha";
import GanDuAnIMS from "./GanDuAnIMS";
import GachNoDoiTacChuDauTu from "./GachNoDoiTacChuDauTu";
import PhieuHuy from "./PhieuHuy"
import GanThueBaoVaoToaNhaTheoFileV2 from "./gan-thuebao-vao-toanha-theo-file"
export default [
  {
    path: "/ext-toanha",
    name: "ext-toanha",
    component: MainLayout,
    redirect: "/ext-toanha/home",
    children: [
      {
        path: "home",
        name: "Quản lý tòa nhà",
        component: Home,
        meta: {
          title: "Trang chủ: Quản lý tòa nhà"
        }
      },
      {
        path: "danhmuc-tochuc-qlda",
        name: "Danh mục tổ chức QLDA",
        component: DanhMucToChucQLDA,
        meta: {
          title: "Danh mục tổ chức QLDA"
        }
      },
      {
        path: 'danhmuc-doitac',
        name: 'DanhMucDoiTac',
        component: DanhMucDoiTac,
        meta: {
            title: 'Danh mục đối tác'
        }
      },
      {
        path: "danhmuc-duan",
        name: "Danh mục dự án",
        component: DanhMucDuAn,
        meta: {
          title: "Danh mục dự án"
        }
      },
      {
        path: "capnhat-doanhthu-bancheo",
        name: "CapNhatDoanhThuBanCheo",
        component: CapNhatDoanhThuBanCheo,
        meta: {
          title: "CẬP NHẬT DOANH THU KÊNH BÁN CHÉO"
        }
      },
      // {
      //   path: "demo",
      //   name: "demo",
      //   component: demo,
      //   meta: {
      //     title: "demo"
      //   }
      // },
      {
        path: "danhmuc-hopdong-dautu",
        name: "DanhMucHopDongDauTu",
        component: DanhMucHopDongDauTu,
        meta: {
          title: "Danh mục hợp đồng đầu tư"
        }
      },
      {
        path: "tientrinh-dautu",
        name: "TienTrinhDauTu",
        component: TienTrinhDauTu,
        meta: {
          title: "Tiến trình đầu tư hợp đồng"
        }
      },
      {
        path: "chitiet-hddtu",
        name: "ChiTietHDDTU",
        component: ChiTietHDDTU,
        meta: {
          title: "Chi tiết hợp đồng đầu tư"
        }
      },
      {
        path: "gan-ketcuoi-vao-toa-nha-theo-file",
        name: "GanKetCuoiVoiToaNhaTheoFile",
        component: GanKetCuoiVoiToaNhaTheoFile,
        meta: {
          title: "Gán Spliter vào tòa nhà theo file"
        }
      },
      {
        path: "banggiakenh-tsl",
        name: "BangGiaKenhTSl",
        component: BangGiaKenhTSl,
        meta: {
          title: "Danh Sách Bảng Giá Kênh TSL Tính Phí Cho TC QLDA"
        }
      },
      {
        path: "gan-tb-vao-toa-nha-theo-file",
        name: "GanThueBaoVaoToaNhaTheoFile",
        component: GanThueBaoVaoToaNhaTheoFile,
        meta: {
          title: "Gán thuê bao vào toà nhà theo file"
        }
      },
      {
        path: "gan-thuebao-toanha",
        name: "GanThueBaoVaoToaNha",
        component: GanThueBaoVaoToaNha,
        meta: {
          title: "Popup Gán thuê bao vào tòa nhà"
        }
      },
      {
        path: "gan-duan-ims",
        name: "GanDuAnIMS",
        component: GanDuAnIMS,
        meta: {
          title: "Popup Gán dự án IMS"
        }
      },
      {
        path: "gachno-doitac-chudautu",
        name: "GachNoDoiTacChuDauTu",
        component: GachNoDoiTacChuDauTu,
        meta: {
          title: "Gạch nợ đối tác - chủ đầu tư"
        }
      },
      {
        path: "phieu-huy",
        name: "PhieuHuy",
        component: PhieuHuy,
        meta: {
          title: "Popup Phiếu hủy"
        }
      },
      {
        path: "gan-thuebao-vao-toanha-theo-file",
        name: "GanThueBaoVaoToaNhaTheoFileV2",
        component: GanThueBaoVaoToaNhaTheoFileV2,
        meta: {
          title: "Gán thuê bao vào toà nhà theo file"
        }
      },
    ]
  }
];
