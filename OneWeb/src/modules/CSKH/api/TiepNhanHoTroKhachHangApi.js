import axios from 'axios'

export default {
  getDSDichVu: (axios) => axios.get('/web-cskh/api/danh-muc/dich-vu-vt'),
  getDSLoaiHinh: (axios,dichVuVTId) => axios.get(`/web-cskh/api/danh-sach-loai-hinh?dichVuVTId=${dichVuVTId}`),
  getDSDanhMuc: (axios,loaiDanhMuc) => axios.get(`/web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=${loaiDanhMuc}`), 
  getDSDanhMucChuDe: (axios,data) => axios.get(`/web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=${data.loaiDanhMuc}&thamSo=${data.loaiHoTro}`), 
  getDSChuDeKhieuNai: (axios) => axios.get('/web-cskh/api/danh-muc/chu-de-khieu-nai'),
  getDSMucDoHaiLong: (axios) => axios.get('/web-cskh/api/danh-muc/loai-ket-qua-giam-thinh'),
  getDSHinhThuc: (axios) => axios.get('/web-cskh/api/danh-muc/hinh-thuc-khieu-nai'),
  getDSPhieuHoTro: (axios,data) => axios.get(`web-cskh/api/tiep-nhan-khach-hang/danh-sach?donViId=${data.donViId}&tthtId=${data.tthtId}`),
  GhiLaiPhieuTiepNhan: (axios,data) => axios.post(`web-cskh/api/tiep-nhan-khach-hang/ghi-lai`,data),
  xoaPhieuHoTro: (axios,htkhId) => axios.delete(`web-cskh/api/tiep-nhan-khach-hang/${htkhId}`),
  getDSLoaiKH: (axios) => axios.get('/web-cskh/api/danh-muc/loai-khach-hang'),
  // getThongTinThueBao: (axios,maThueBao) => axios.get(`/web-cskh/api/thue-bao-khong-khao-sat?maThueBao=${maThueBao}`),
  getThongTinThueBao: (axios,data) => axios.get(`/web-cskh/api/common/thong-tin-thue-bao/?loaiId=${data.loai}&giaTri=${data.thueBao}`),
  getThongTinKhachHang: (axios,data) => axios.get(`/web-cskh/api/common/thong-tin-khach-hang/?loaiId=${data.loaiId}&giaTri=${data.thongTinKH}`),

  getDSThueBao: (axios,khachHang_Id) => axios.get(`web-cskh/api/tiep-nhan-khach-hang/danh-sach-thue-bao?khachHang_Id=${khachHang_Id}`),
  getLichSuBaoHong: (axios,thueBaoId) => axios.get(`/web-cskh/api/khao-sat-thue-bao/lich-su-bao-hong?thueBaoId=${thueBaoId}`),
  getLichSuCKSH: (axios,khachHang_Id) => axios.get(`web-cskh/api/tiep-nhan-khach-hang/lich-su-cskh?khachHang_Id=${khachHang_Id}`),
  getLichSuKhieuNai: (axios,khachHang_Id) => axios.get(`web-cskh/api/tiep-nhan-khach-hang/lich-su-khieu-nai?khachHang_Id=${khachHang_Id}`),
  getLichSuDonHang: (axios,khachHang_Id) => axios.get(`web-cskh/api/tiep-nhan-khach-hang/lich-su-don-hang?khachHang_Id=${khachHang_Id}`),
  getLichSuHoTro: (axios,khachHang_Id) => axios.get(`web-cskh/api/tiep-nhan-khach-hang/lich-su-ho-tro?khachHang_Id=${khachHang_Id}`),
  khoaPhieuHoTroKhachHang: (axios,data) => axios.put(`/web-cskh/api/tiep-nhan-khach-hang/khoa-phieu`,data),
  getDSDonViXL: (axios,data) => axios.get(`/web-cskh/api/tiep-nhan-khach-hang/don-vi-xu-ly?chuDe_Id=${data.chuDe_Id}&dichVu_Id=${data.dichVu_Id}`),
  kiemTraChuDeGiaoPhieu: (axios,chuDeHtId) => axios.get(`web-cskh/api/tiep-nhan-khach-hang/check-giao-phieu?chuDeHtId=${chuDeHtId}`),
}
