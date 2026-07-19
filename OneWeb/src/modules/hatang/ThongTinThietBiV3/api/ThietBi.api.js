import { getDeviceTypeFlags } from '../const.js'
import { mapNangLucResponseToCurrentItem } from '../ThongTinChiTiet/Components/NangLucLuuTru/helper.js'
import { FIELD_VISIBILITY_CONFIG, checkFieldVisibility } from '../ThongTinChiTiet/Components/ThongTinChung/fieldVisibility.js'

export default {
  getDanhSachThietBi: async (axios, searchParams = {}) => {
    try {
      const payload = Object.keys(searchParams).reduce((acc, key) => {
        const value = searchParams[key]
        if (value !== null && value !== undefined && value !== '') {
          acc[key] = value
        }
        return acc
      }, {})

      const rs = await axios.post('/web-ecms/thietbi-vatly-v2', payload)
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
  async getThongTinChungById(axios, id) {
    try {
      const rs = await axios.get(`/web-ecms/thietbi-vatly-v2/${id}/thong-tin-chung`)
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
  async getLichSuTacDong(axios, khoaDuLieu) {
    try {
      const rs = await axios.post(`/web-ecms/idc/lichsu-tacdong/danhsach`, {
        tenBang: "THIETBI",
        tenCot: "",
        khoaDuLieu: khoaDuLieu
      });

      return rs?.data ?? [];
    } catch (error) {
      handleApiError(error);
      return [];
    }
  },
  async getLichSuCongSuat(axios, deviceId) {
    try {
      const rs = await axios.post(`/web-ecms/idc/lichsu-tacdong/congsuat`, {
        khoaDuLieu: deviceId
      });

      return rs?.data ?? [];
    } catch (error) {
      handleApiError(error);
      return [];
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
  },
  async upsertThongTinChung(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/thietbi-vatly-v2/thong-tin-chung', payload)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  async upsertCaNhanQuanLy (axios, payload) { 
    try {
      const rs = await axios.post('/web-ecms/hatang/taisan-thietbi/nhan-vien/batch', payload)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  async upsertThongTinTaiSan(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/hatang/taisan-thietbi', payload)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  async getCaNhanQuanLy (axios, thietbiId) {
    try {
      const rs = await axios.get(`/web-ecms/hatang/taisan-thietbi/nhan-vien/${thietbiId}`)
      if (rs && rs.data) {
        return {
          nguoiqly_id : rs.data
        }
      } 
      return []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getThongTinTaiSan (axios, thietbiId) {
    try {
      const rs = await axios.get(`/web-ecms/hatang/taisan-thietbi/${thietbiId}`)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
   async upsertNangLucLuuTru (axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/nang-luc-luu-tru/upsert', payload)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
    }
  },
  async getNangLucLuuTru(axios, thietbiId) {
    try {
      const rs = await axios.get(`/web-ecms/nang-luc-luu-tru/get/${thietbiId}`)
      return rs && rs.data[0] ? mapNangLucResponseToCurrentItem(rs.data[0]) : null
    } catch (error) {
      handleApiError(error)
    }
  },
}

const sanitizePayloadByDeviceFlags = (payload) => {
  let cleaned = { ...payload }
  const phanLoai = cleaned.phanloai || null
  
  // Lấy device type flags từ phanloai
  const deviceTypeFlags = getDeviceTypeFlags(phanLoai)
  
  // Mapping các field trong FIELD_VISIBILITY_CONFIG sang giá trị mặc định cần clear
  const fieldDefaultValues = {
    thietbicha_id: null,
    mathietbi_infra: '',
    ip_id: null,
    loaikhachhang: 0,
    full_depth: 0,
    loai_card: null,
    loaitudia_id: null,
    cs_dinhdanh: 0,
    vaitrothietbi_id: null,
    cs_thucte: 0,
    hedieuhanh_id: null,
    khong_gian_lap_dat: 0
  }
  
  // Duyệt qua tất cả các field trong FIELD_VISIBILITY_CONFIG
  // Nếu field không visible với device flags hiện tại thì clear nó
  Object.keys(FIELD_VISIBILITY_CONFIG).forEach(fieldKey => {
    const condition = FIELD_VISIBILITY_CONFIG[fieldKey]
    const isVisible = checkFieldVisibility(deviceTypeFlags, condition)
    
    if (!isVisible && fieldDefaultValues.hasOwnProperty(fieldKey)) {
      cleaned[fieldKey] = fieldDefaultValues[fieldKey]
    }
  })
  
  return cleaned
}

const handleApiError = (e) => {
  if (e?.response?.data?.message_detail) {
    throw new Error(e.response.data.message_detail)
  } else {
    throw e
  }
}
