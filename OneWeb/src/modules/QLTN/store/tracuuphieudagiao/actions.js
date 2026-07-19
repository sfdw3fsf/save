import CommonAPI from '@/modules/QLTN/api/Commons'
import TraCuuAPI from '@/modules/QLTN/api/TraCuuAPI'
import { post } from 'jquery'

export const actions = {
  async getDanhSachNhanVienTheoLoai({ commit, state}, data) {
    let response = await CommonAPI.getNhanVienTheoLoai(this._vm.axios, DataTransferItemList)
    try {
      let dsNV = []
      if (response.data.message === 'BSS-00000000') {
        let items = []
        if (response.data.data.body !== undefined) {
          response.data.data.body.forEach( function (item) {
            items.push({ id : item.NHANVIEN_ID, text : item.TEN_HIENTHI })
          })
          dsNV = items
        }
      }
      commit('dsNhanVienTheoLoai', dsNV)
    } catch (error) {
      throw error
    }
    return response
  },
  async getFormSearchResult({ commit, getters }, data) {
    /*
    let postData = {
      kyCuoc : getters.getKyHoaDon.kyHoaDonSelected,
      buocXLNId : 20,
      loaiNVId : 94,
      tuNgay : getters.formDataGetter.p_chk_ngaygiao === '1' ? getters.formDataGetter.p_ngaygiao_tungay : '0',
      denNgay : getters.formDataGetter.p_chk_ngaygiao === '1' ? getters.formDataGetter.p_ngaygiao_denngay : '0',
      lanTaoId : getters.formDataGetter.p_chk_lantao === '1' ? getters.formDataGetter.p_lantao_xyly_no : '0',
      nhanVienXLId : getters.formDataGetter.p_chk_nhanvien === '1' ? getters.formDataGetter.p_nhanvien : '0',
      giaoPhieuId : getters.formDataGetter.p_chk_luotgiao === '1' ? getters.formDataGetter.p_luotgiao : '0',
      page : getters.pageInfoGetter.page,
      size : getters.pageInfoGetter.maxSize
    }
    */
    let postData = {
      kyCuoc : 20210301,
      buocXLNId:20,
      loaiNVId:94,
      tuNgay:'01/11/2016',
      denNgay:'16/11/2016',
      lanTaoId:18,
      nhanVienXLId:4529,
      giaoPhieuId:32504,
      page : getters.pageInfoGetter.page,
      size : getters.pageInfoGetter.maxSize
    }
    this._vm.loading(true)
    let response = await TraCuuAPI.getTraCuuXuLyNoPhieuDaGiao(this._vm.axios, postData)
    let ds = []
    let page = {}
    try {

      if (response.data.error_code === 'BSS-00000000') {
        if (response.data.data.body !== undefined
          && response.data.data.body.data !== undefined
          && response.data.data.body.data.length > 0) {
          ds = response.data.data.body.data
          page = {
            page : response.data.data.body.page,
            maxSize: response.data.data.body.maxSize,
            totalElement: response.data.data.body.totalElement,
            totalPages: response.data.data.body.totalPages,
            sort: response.data.data.body.sort,
            propertiesSort: response.data.data.body.propertiesSort
          }
          commit('setPageInfo', page)
        }else {
          this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
        }
      } else {
        this._vm.$toast.error(respone.data.message)
      }
      commit('setFormSearchResult', ds)
    } catch (error) {
      // throw error
      this._vm.$toast.error(error)
    } finally {
      this._vm.loading(false)
    }
    return response
  },
  setBuocXuLyNo ({ commit, state}, data) {
    commit('setBuocXuLyNo', data);
  },
  setCheckNgayGiao ({ commit, state}, data) {
    commit('setCheckNgayGiao', data);
  },
  setNgayGiaoTuNgay ({ commit, state}, data) {
    commit('setNgayGiaoTuNgay', data);
  },
  setNgayGiaoDenNgay ({ commit, state}, data) {
    commit('setNgayGiaoDenNgay', data);
  },
  setCheckLanTao({ commit, state}, data) {
    commit('setCheckLanTao', data);
  },
  setCheckLoaiNhanVien({ commit, state}, data) {
    commit('setCheckLoaiNhanVien', data);
  },
  setLanTaoXuLyNo({ commit, state}, datat) {
    commit('setLanTaoXuLyNo', data)
  },
  setLoaiNhanVien({ commit, state}, datat) {
    commit('setLoaiNhanVien', data)
  },
  setCheckNhanVien({ commit, state}, datat) {
    commit('setCheckNhanVien', data)
  },
  setNhanVien({ commit, state}, datat) {
    commit('setNhanVien', data)
  },
  setCheckLuotGiao({ commit, state}, datat) {
    commit('setCheckLuotGiao', data)
  },
  setLuotGiao({ commit, state}, datat) {
    commit('setLuotGiao', data)
  },
  setPageInfo({ commit, state}, data) {
    commit('setPageInfo', data)
  },
  setCurrentPage({ commit, state}, data) {
    commit('setCurrentPage', data)
  },
  clearResult ({commit, sate}, data) {
    commit('clearResult', data)
  }
}
