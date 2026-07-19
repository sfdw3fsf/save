export default {
  getDanhMuc: async (axios, data) => axios.post('/web-ecms/idc/danh-muc/common', data),
  getListIpAddress: async (axios, data) => axios.post('/web-ecms/hatang/quanly-diachiip/danh-sach', data),
  // getListIpAddress: async (axios, data) => axios.post('http://localhost:10991/hatang/quanly-diachiip/danh-sach', data),
  getIpAddressDetail: async (axios, data) => axios.post('/web-ecms/hatang/quanly-diachiip/chitiet', data),
  // getIpAddressDetail: async (axios, data) => axios.post('http://localhost:10991/hatang/quanly-diachiip/chitiet', data),
  getDanhMucHaTang: async (axios, data) => axios.post('/web-ecms/danhmuc/hatang_mang', data),
  getSubnetListByIpAddress: async (axios, ipAddressId) =>
    axios.post('/web-ecms/idc/quanly-ip-address/subnet/danh-sach', { ipAddressId: ipAddressId }),
  getIpStoreListBySubnet: async (axios, subnetId) =>
    axios.post('/web-ecms/idc/quanly-ip-address/ip-store/danh-sach', { subnetId: subnetId }),
  getHaTangListByType: async (axios, data) => axios.post('/web-ecms/idc/quanly-ip-address/hatang/danh-sach', data),
  saveIP: async (axios, data) => axios.post('/web-ecms/hatang/quanly-diachiip/save', data),
  getListSubnet: async (axios, data) => axios.post('/web-ecms/hatang/quanly-diachiip/ds-subnet', data)
}
