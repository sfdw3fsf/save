import { yValidation as y } from '../../validation'

export const thongTinChungSubnetSchema = y.object({
  tenSubnet: y.string().required('Tên subnet không được để trống'),
  loaiIp: y.required('Loại IP không được để trống'),
  phanLoaiIp: y.required('Phân loại IP không được để trống'),
  // ccdv: y.string().required('Trạng thái CCDV không được để trống'),
  subnetMask: y.string().required('Subnet Mask không được để trống'),
  subnet: y.netWorkAddress('Subnet không hợp lệ'),
  // idc: y.string().required('IDC không được để trống'),
  gateWay: y.string().required('Gateway không được để trống'),
  htm: y.string().required('Hạ tầng mạng không được để trống'),
  subnet: y.required().networkAddress(),

  // noiBo: y.string().required('Nội bộ/ khách hàng không được để trống')
})
