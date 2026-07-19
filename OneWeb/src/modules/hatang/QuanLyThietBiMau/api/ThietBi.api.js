// Mock data API for QuanLyThietBiMau (Device Template Management)

// Mock data for device templates
const mockDeviceTemplates = [
  {
    id: 1,
    maChungLoaiTB: 'SA-001',
    tenChungLoaiTB: 'Server Dell PowerEdge R740',
    congSuatDinhDanh: '750W',
    hangSanXuat: 'Dell',
    mangThietBi: 'Server',
    loaiThietBi: 'Physical Server',
    phanLoaiThietBi: 'Rack Server'
  },
  {
    id: 2,
    maChungLoaiTB: 'SA-002',
    tenChungLoaiTB: 'Server HP ProLiant DL380',
    congSuatDinhDanh: '800W',
    hangSanXuat: 'HP',
    mangThietBi: 'Server',
    loaiThietBi: 'Physical Server',
    phanLoaiThietBi: 'Rack Server'
  },
  {
    id: 3,
    maChungLoaiTB: 'NW-001',
    tenChungLoaiTB: 'Switch Cisco Catalyst 9300',
    congSuatDinhDanh: '150W',
    hangSanXuat: 'Cisco',
    mangThietBi: 'Network',
    loaiThietBi: 'Network Switch',
    phanLoaiThietBi: 'Access Switch'
  },
  {
    id: 4,
    maChungLoaiTB: 'ST-001',
    tenChungLoaiTB: 'Storage NetApp FAS8200',
    congSuatDinhDanh: '1200W',
    hangSanXuat: 'NetApp',
    mangThietBi: 'Storage',
    loaiThietBi: 'Storage Array',
    phanLoaiThietBi: 'NAS'
  },
  {
    id: 5,
    maChungLoaiTB: 'FW-001',
    tenChungLoaiTB: 'Firewall Fortinet FortiGate 600E',
    congSuatDinhDanh: '60W',
    hangSanXuat: 'Fortinet',
    mangThietBi: 'Network',
    loaiThietBi: 'Firewall',
    phanLoaiThietBi: 'UTM'
  },
  {
    id: 6,
    maChungLoaiTB: 'BL-001',
    tenChungLoaiTB: 'Blade Server Dell PowerEdge M640',
    congSuatDinhDanh: '300W',
    hangSanXuat: 'Dell',
    mangThietBi: 'Server',
    loaiThietBi: 'Blade Server',
    phanLoaiThietBi: 'Half-height Blade'
  },
  {
    id: 7,
    maChungLoaiTB: 'RT-001',
    tenChungLoaiTB: 'Router Cisco ASR 1000',
    congSuatDinhDanh: '200W',
    hangSanXuat: 'Cisco',
    mangThietBi: 'Network',
    loaiThietBi: 'Router',
    phanLoaiThietBi: 'Edge Router'
  },
  {
    id: 8,
    maChungLoaiTB: 'LB-001',
    tenChungLoaiTB: 'Load Balancer F5 BIG-IP i2600',
    congSuatDinhDanh: '180W',
    hangSanXuat: 'F5 Networks',
    mangThietBi: 'Network',
    loaiThietBi: 'Load Balancer',
    phanLoaiThietBi: 'Application Delivery Controller'
  }
]

export default {
  /**
   * Lấy danh sách thiết bị mẫu với các tham số tìm kiếm
   * @param {Object} searchParams - Các tham số tìm kiếm
   * @returns {Promise<Array>} Danh sách thiết bị mẫu
   */
  async getDanhSachThietBiMau(searchParams = {}) {
    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 300))

    let results = [...mockDeviceTemplates]

    // Apply filters
    if (searchParams.maChungLoaiTB) {
      const searchTerm = searchParams.maChungLoaiTB.toLowerCase()
      results = results.filter(item =>
        item.maChungLoaiTB.toLowerCase().includes(searchTerm)
      )
    }

    if (searchParams.tenChungLoaiTB) {
      const searchTerm = searchParams.tenChungLoaiTB.toLowerCase()
      results = results.filter(item =>
        item.tenChungLoaiTB.toLowerCase().includes(searchTerm)
      )
    }

    if (searchParams.congSuatDinhDanh) {
      const searchTerm = searchParams.congSuatDinhDanh.toLowerCase()
      results = results.filter(item =>
        item.congSuatDinhDanh.toLowerCase().includes(searchTerm)
      )
    }

    if (searchParams.hangSanXuat) {
      const searchTerm = searchParams.hangSanXuat.toLowerCase()
      results = results.filter(item =>
        item.hangSanXuat.toLowerCase().includes(searchTerm)
      )
    }

    if (searchParams.mangThietBi) {
      const searchTerm = searchParams.mangThietBi.toLowerCase()
      results = results.filter(item =>
        item.mangThietBi.toLowerCase().includes(searchTerm)
      )
    }

    if (searchParams.loaiThietBi) {
      const searchTerm = searchParams.loaiThietBi.toLowerCase()
      results = results.filter(item =>
        item.loaiThietBi.toLowerCase().includes(searchTerm)
      )
    }

    if (searchParams.phanLoaiThietBi) {
      const searchTerm = searchParams.phanLoaiThietBi.toLowerCase()
      results = results.filter(item =>
        item.phanLoaiThietBi.toLowerCase().includes(searchTerm)
      )
    }

    return results
  },

  /**
   * Lấy thông tin chi tiết thiết bị mẫu theo ID
   * @param {Number} id - ID thiết bị mẫu
   * @returns {Promise<Object>} Thông tin thiết bị mẫu
   */
  async getThietBiMauById(id) {
    await new Promise(resolve => setTimeout(resolve, 200))
    return mockDeviceTemplates.find(item => item.id === id) || null
  },

  /**
   * Tạo mới thiết bị mẫu
   * @param {Object} data - Dữ liệu thiết bị mẫu
   * @returns {Promise<Object>} Thiết bị mẫu đã tạo
   */
  async createThietBiMau(data) {
    await new Promise(resolve => setTimeout(resolve, 300))
    const newId = Math.max(...mockDeviceTemplates.map(item => item.id)) + 1
    const newItem = {
      id: newId,
      ...data
    }
    mockDeviceTemplates.push(newItem)
    return newItem
  },

  /**
   * Cập nhật thiết bị mẫu
   * @param {Number} id - ID thiết bị mẫu
   * @param {Object} data - Dữ liệu cập nhật
   * @returns {Promise<Object>} Thiết bị mẫu đã cập nhật
   */
  async updateThietBiMau(id, data) {
    await new Promise(resolve => setTimeout(resolve, 300))
    const index = mockDeviceTemplates.findIndex(item => item.id === id)
    if (index !== -1) {
      mockDeviceTemplates[index] = { ...mockDeviceTemplates[index], ...data }
      return mockDeviceTemplates[index]
    }
    throw new Error('Không tìm thấy thiết bị mẫu')
  },

  /**
   * Xóa thiết bị mẫu
   * @param {Number} id - ID thiết bị mẫu
   * @returns {Promise<Boolean>} Kết quả xóa
   */
  async deleteThietBiMau(id) {
    await new Promise(resolve => setTimeout(resolve, 300))
    const index = mockDeviceTemplates.findIndex(item => item.id === id)
    if (index !== -1) {
      mockDeviceTemplates.splice(index, 1)
      return true
    }
    throw new Error('Không tìm thấy thiết bị mẫu')
  }
}
