import MainLayout from "@/layouts/MainLayout";
import Home from "./Home";
import SuaDoiTramThiCongDanhBa from "./suadoitramthicongdanhba"
import CapNhatDanhBa from "./CapNhatDanhBa"
import ThayDoiThongSoInternet from "./ThayDoiThongSoInternet"
import ThamSoQuanLyThuNo from "./ThamSoQuanLyThuNo";
export default [
  {
    path: "/ext-quantri",
    name: "ext-quantri",
    component: MainLayout,
    redirect: "/ext-quantri/home",
    children: [
      {
        path: "home",
        name: "Quản trị hệ thống",
        component: Home,
        meta: {
          title: "Trang chủ: Quản trị hệ thống"
        }
      },
      {
        path: "suadoi-tram-thicong-danhba",
        name: "Sửa đổi trạm thi công",
        component: SuaDoiTramThiCongDanhBa,
        meta: {
          title: "SỬA ĐỔI TRẠM THI CÔNG"
        }
      },
      {
        path: "capnhat-danhba",
        name: "CapNhatDanhBa",
        component: CapNhatDanhBa,
        meta: {
          title: "Cập nhật danh bạ"
        }
      },      
      {
         path: "thaydoi-thongso-internet",
         name: "ThayDoiThongSoInternet",
         component: ThayDoiThongSoInternet,
         meta: {
           title: "Thay đổi thông số Internet, IMS"
         }
      },
      {
        path: "thamso-quanly-thuno",
        name: "ThamSoQuanLyThuNo",
        component: ThamSoQuanLyThuNo,
        meta: {
          title: "Cấu hình tham số quản lý thu nợ",
          dialog: true
        }
      },
    ]
  }
];
