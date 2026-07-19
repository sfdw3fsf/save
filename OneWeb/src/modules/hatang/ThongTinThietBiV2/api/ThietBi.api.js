import { LOAI_THIET_BI } from '../const.js'

export default {
  getDanhSachThietBi: async (axios) => {
    try {
      const rs = await axios.get('/web-ecms/thong-tin-thiet-bi')
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  getDanhSachThietBiWithSearch: async (axios, searchParams = {}) => {
    try {
      // Build clean payload from search parameters (remove null/undefined/empty)
      const payload = Object.keys(searchParams).reduce((acc, key) => {
        const value = searchParams[key]
        if (value !== null && value !== undefined && value !== '') {
          acc[key] = value
        }
        return acc
      }, {})

      // Use POST mapping for search
      const rs = await axios.post('/web-ecms/thong-tin-thiet-bi/search', payload)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getThietBiById(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}`)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  async getThietBiLichSu(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-lich-su`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getThietBiDauNoi(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-dau-noi`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getThietBiInfra(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-ha-tang`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getVM(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/vm`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getNFS(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/nfs`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getController(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/ha-tang/bo-dieu-khien?tb_id=${id}`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getKhayDia(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/khaydia-tudia/get-by-thietbi-id?id=${id}`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  // Port-related API methods
  async getThietBiPortKhaiBao(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/port-khai-bao`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getThietBiPort(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-port`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getDanhSachIP(axios, id = 0) {
    try {
      const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-ip`)
      return rs && rs.data ? rs.data.filter((item) => item.DA_GAN_MAYAO !== 1 && item.DA_GAN_THIETBI_KHAC !== 1 && item.DA_GAN_HATANG_BACKUP !== 1 && item.DA_GAN_HATANG_LUU_TRU !== 1) : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getDanhSachKheCam(axios, id = 0) {
    try {
      // const rs = await axios.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-khe-cam`)
      // return rs && rs.data ? rs.data : []
      return  [
        {
          ma_khe: 'SLOT-001',
          ten_khe: 'Khe cắm 1',
          loai_khe: 'Interface Card',
          trang_thai: 1, // 1: Đã sử dụng, 0: Trống
          card_info: 'CARD-001'
        },
        {
          ma_khe: 'SLOT-002',
          ten_khe: 'Khe cắm 2',
          loai_khe: 'Interface Card',
          trang_thai: 1,
          card_info: 'CARD-002'
        },
        {
          ma_khe: 'SLOT-003',
          ten_khe: 'Khe cắm 3',
          loai_khe: 'Interface Card',
          trang_thai: 0,
          card_info: ''
        },
        {
          ma_khe: 'SLOT-004',
          ten_khe: 'Khe cắm 4',
          loai_khe: 'Power Module',
          trang_thai: 1,
          card_info: 'CARD-004'
        },
        {
          ma_khe: 'SLOT-005',
          ten_khe: 'Khe cắm 5',
          loai_khe: 'Power Module',
          trang_thai: 1,
          card_info: 'CARD-005'
          
        },
        {
          ma_khe: 'SLOT-006',
          ten_khe: 'Khe cắm 6',
          loai_khe: 'Console Card',
          trang_thai: 1,
          card_info: 'CARD-006'
        },
        {
          ma_khe: 'SLOT-007',
          ten_khe: 'Khe cắm 7',
          loai_khe: 'Interface Card',
          trang_thai: 0,
        },
        {
          ma_khe: 'SLOT-008',
          ten_khe: 'Khe cắm 8',
          loai_khe: 'Interface Card',
          trang_thai: 0,
        }
      ]
    } catch (error) {
      handleApiError(error)
    }
  },
  async capnhatThietBi(axios, data) {
    try {
      let payload = {
        ...data,
        thietbicha_id: data.thietbicha_id != data.id ? data.thietbicha_id : null,
        vaitrothietbi_ids: data.vaitrothietbi_ids ? data.vaitrothietbi_ids : []
      }
      delete payload.column
      const sanitizedPayload = sanitizePayloadByDeviceFlags(payload)
      const rs = await axios.post(`/web-ecms/thong-tin-thiet-bi`, sanitizedPayload)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  }
}

const sanitizePayloadByDeviceFlags = (payload) => {
  let cleaned = { ...payload }
  const deviceClass = cleaned.phanloai || null

  const clearLoaiThietBiTuDia = () => {
    cleaned.loaitudia_id = null
    cleaned.bodieukhien_qty = null
    cleaned.bodieukhien_dau = null
    cleaned.bodieukhien_fname = null
    cleaned.khaydia_qty = null
    cleaned.khaydia_dau = null
    cleaned.khaydia_fname = null
    cleaned.hdd_tong = null
    cleaned.ssd_tong = null
    cleaned.iops_tong = null
    cleaned.hdd_nangluc = null
    cleaned.ssd_nangluc = null
    cleaned.iops_nangluc = null
    cleaned.hdd_capphat = null
    cleaned.ssd_capphat = null
    cleaned.iops_capphat = null
    cleaned.mem_cache = null
    cleaned.flash_cache = null
  }

  const clearLoaiThietBiMayChu = () => {
    cleaned.hsm = null
    cleaned.vaitrothietbi_ids = []
    cleaned.cpu_sokhe = null
    cleaned.cpu_dacam = null
    cleaned.cpu_total = null
    cleaned.hdd_sokhe = null
    cleaned.hdd_dacam = null
    cleaned.hdd_total = null
    cleaned.ram_sokhe = null
    cleaned.ram_dacam = null
    cleaned.ram_total = null
  }

  const clearLoaiThietBiMang = () => {
    cleaned.chungloaithietbi_id = null
    cleaned.vaitrothietbi_ids = []
  }
  const clearLoaiThietBiKHAC = () => {
    cleaned.cauhinh = null
    cleaned.psu_qty_in = null
    cleaned.psu_qty_out = null
  }

  switch (deviceClass) {
    case LOAI_THIET_BI.IDC: {
      clearLoaiThietBiTuDia()
      clearLoaiThietBiMayChu()
      clearLoaiThietBiMang()
      clearLoaiThietBiKHAC()
      break
    }
    case LOAI_THIET_BI.TU_DIA: {
      clearLoaiThietBiMayChu()
      clearLoaiThietBiMang()
      clearLoaiThietBiKHAC()
      break
    }
    case LOAI_THIET_BI.MANG: {
      clearLoaiThietBiTuDia()
      clearLoaiThietBiMayChu()
      clearLoaiThietBiKHAC()
      break
    }
    case LOAI_THIET_BI.MAY_CHU: {
      clearLoaiThietBiMang()
      clearLoaiThietBiTuDia()
      clearLoaiThietBiKHAC()
      break
    }
    case LOAI_THIET_BI.KHAC:
    default: {
      clearLoaiThietBiTuDia()
      clearLoaiThietBiMayChu()
      clearLoaiThietBiMang()
      break
    }
  }

  return cleaned
}

const handleApiError = (e) => {
  if (e?.response?.data?.message_detail) {
    throw new Error(e.response.data.message_detail)
  } else {
    throw e
  }
}
