// các hàm dùng để gọi API, gọi hàm khác trong mutations để thay đổi state của của đối tượng trong store
import SiteAreaAPI from '../SiteAreaAPI'
export const actions = {
  async getAreaListFollowDividePoint ({ commit, state }, data) {
    const res = await SiteAreaAPI.lay_ds_khuvuc_theo_diemchia(data)
    if (res.data.data) {
      return commit('getAreaListFollowDividePointSuccess', res.data.data)
    }
    return commit('getAreaListFollowDividePointSuccess', [])
  },
  async getParentAreaList ({ commit, state }) {
    const res = await SiteAreaAPI.sp_lay_danh_sach_khu_vuc_cha({})
    if (res.data.data) {
      return commit('getParentAreaListSuccess', res.data.data)
    }
    return commit('getParentAreaListSuccess', [])
  },
  async getAssignedArea ({ commit, state }, data) {
    const res = await SiteAreaAPI.sp_lay_danh_sach_loai_khu_vuc(data)
    if (res.data.data) {
      return commit('getAssignedAreaSuccess', res.data.data)
    }
    return commit('getAssignedAreaSuccess', [])
  },
  async getUnassignedArea ({ commit, state }, data) {
    const res = await SiteAreaAPI.sp_lay_danh_sach_loai_khu_vuc(data)
    if (res.data.data) {
      return commit('getUnassignedAreaSuccess', res.data.data)
    }
    return commit('getUnassignedAreaSuccess', [])
  },
  async getDistricts ({ commit, state }, data) {
    const res = await SiteAreaAPI.lay_ds_quan_huyen()
    if (res.data.data) {
      return commit('getDistrictsSuccess', res.data.data)
    }
    return commit('getDistrictsSuccess', [])
  },
  async getWards ({ commit, state }, data) {
    const res = await SiteAreaAPI.lay_ds_phuong_xa(data)
    if (res.data.data) {
      return commit('getWardsSuccess', res.data.data)
    }
    return commit('getWardsSuccess', [])
  },
  async getTowns ({ commit, state }, phuongId) {
    const res = await SiteAreaAPI.sp_lay_ds_pho({phuong_id: phuongId, nhom_pho_id: 1})
    if (res.data.data) {
      return commit('getTownsSuccess', res.data.data)
    }
    return commit('getTownsSuccess', [])
  },
  async getHamlets ({ commit, state }, phuongId) {
    const res = await SiteAreaAPI.sp_lay_ds_pho({phuong_id: phuongId, nhom_pho_id: 2})
    if (res.data.data) {
      return commit('getHamletssSuccess', res.data.data)
    }
    return commit('getHamletsSuccess', [])
  },
  async getAreas ({ commit, state }, phuongId) {
    const res = await SiteAreaAPI.sp_lay_ds_pho({phuong_id: phuongId, nhom_pho_id: 3})
    if (res.data.data) {
      return commit('getAreasSuccess', res.data.data)
    }
    return commit('getAreasSuccess', [])
  },
  async getLoadData ({ commit, state }, data) {
    const res = await SiteAreaAPI.lay_ds_phuong_xa(data)
    if (res.data.data) {
      return commit('getLoadDataSuccess', res.data.data)
    }
    return commit('getLoadDataSuccess', [])
  },
  async clearAssignData ({ commit, state }) {
    commit('getTownsSuccess', [])
    commit('getHamletsSuccess', [])
    return commit('getAreasSuccess', [])
  }
}
