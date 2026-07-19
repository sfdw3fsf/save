import PhanQuyenNhanVienNhanKSApi from '@/modules/CSKH/api/PhanQuyenNhanVienNhanKSApi'

export const actions = {
  async getDsNhanVienTheoDonVi({ commit, state }, data) {
    let response = await PhanQuyenNhanVienNhanKSApi.getNhanVienTheoDonVi(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.MA_NV.localeCompare(b.MA_NV));
        ds.forEach((element, index) => element.STT = index + 1)
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsNhanVienTheoDonVi', ds)
  },
  async getDsLoaiHinhThueBaoKSChuaGan({ commit, state }, data) {
    data.kieu = 1
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsLoaiHinhThueBaoKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.LOAIHINH_TB.localeCompare(b.LOAIHINH_TB));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsLoaiHinhThueBaoKSChuaGan', ds)
  },
  async getDsLoaiHinhThueBaoKSDaGan({ commit, state }, data) {
    data.kieu = 2
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsLoaiHinhThueBaoKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.LOAIHINH_TB.localeCompare(b.LOAIHINH_TB));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsLoaiHinhThueBaoKSDaGan', ds)
  },
  async getDsHopDongThueBaoKSChuaGan({ commit, state }, data) {
    data.kieu = 1
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsHopDongThueBaoKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_LOAIHD.localeCompare(b.TEN_LOAIHD));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsHopDongThueBaoKSChuaGan', ds)
  },
  async getDsHopDongThueBaoKSDaGan({ commit, state }, data) {
    data.kieu = 2
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsHopDongThueBaoKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_LOAIHD.localeCompare(b.TEN_LOAIHD));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsHopDongThueBaoKSDaGan', ds)
  },
  async getDsPhanLoaiKhachHangKSChuaGan({ commit, state }, data) {
    data.kieu = 1
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsPhanLoaiKhachHangKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_PLKH.localeCompare(b.TEN_PLKH));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsPhanLoaiKhachHangKSChuaGan', ds)
  },
  async getDsPhanLoaiKhachHangKSDaGan({ commit, state }, data) {
    data.kieu = 2
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsPhanLoaiKhachHangKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_PLKH.localeCompare(b.TEN_PLKH));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsPhanLoaiKhachHangKSDaGan', ds)
  },
  //setGan = 1 => set gán | setGan = 2 => set chưa gán
  setDanhSachGanChuaGan({ commit, state }, data) {
    switch (data.typeDanhSach) {
      case 'dsLoaiHinhThueBaoKSDaGan':
        if (data.setGan == 1) {
          commit('add', { source: state.dsLoaiHinhThueBaoKSDaGan, data: data.itemArr })
          commit('remove', { source: state.dsLoaiHinhThueBaoKSChuaGan, data: data.itemArr, field: data.field })
        }
        else {
          commit('remove', { source: state.dsLoaiHinhThueBaoKSDaGan, data: data.itemArr, field: data.field })
          commit('add', { source: state.dsLoaiHinhThueBaoKSChuaGan, data: data.itemArr })
        }
        break;
      case 'dsHopDongThueBaoKSDaGan':
        if (data.setGan == 1) {
          commit('add', { source: state.dsHopDongThueBaoKSDaGan, data: data.itemArr })
          commit('remove', { source: state.dsHopDongThueBaoKSChuaGan, data: data.itemArr, field: data.field })
        }
        else {
          commit('remove', { source: state.dsHopDongThueBaoKSDaGan, data: data.itemArr, field: data.field })
          commit('add', { source: state.dsHopDongThueBaoKSChuaGan, data: data.itemArr })
        }
        break;
      case 'dsPhanLoaiKhachHangKSDaGan':
        if (data.setGan == 1) {
          commit('add', { source: state.dsPhanLoaiKhachHangKSDaGan, data: data.itemArr })
          commit('remove', { source: state.dsPhanLoaiKhachHangKSChuaGan, data: data.itemArr, field: data.field })
        }
        else {
          commit('remove', { source: state.dsPhanLoaiKhachHangKSDaGan, data: data.itemArr, field: data.field })
          commit('add', { source: state.dsPhanLoaiKhachHangKSChuaGan, data: data.itemArr })
        }
        break;
      case 'dsLoaiKhachHangKSDaGan':
        console.log('data.itemarr', data.itemArr)
        if (data.setGan == 1) {
          commit('add', { source: state.dsLoaiKhachHangKSDaGan, data: data.itemArr })
          commit('remove', { source: state.dsLoaiKhachHangKSChuaGan, data: data.itemArr, field: data.field })
        }
        else {
          commit('remove', { source: state.dsLoaiKhachHangKSDaGan, data: data.itemArr, field: data.field })
          commit('add', { source: state.dsLoaiKhachHangKSChuaGan, data: data.itemArr })
        }
        break
        case 'dsDonViKSDaGan':
          console.log('data.itemarr', data.itemArr)
          if (data.setGan == 1) {
            commit('add', { source: state.dsDonViKSDaGan, data: data.itemArr })
            commit('remove', { source: state.dsDonViKSChuaGan, data: data.itemArr, field: data.field })
          }
          else {
            commit('remove', { source: state.dsDonViKSDaGan, data: data.itemArr, field: data.field })
            commit('add', { source: state.dsDonViKSChuaGan, data: data.itemArr })
          }
          break
    }

  },
  async getDsDonViKSChuaGan({ commit, state }, data) {
    data.kieu = 1
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsDonViKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsDonViKSChuaGan', ds)
  },
  async getDsDonViKSDaGan({ commit, state }, data) {
    data.kieu = 2
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsDonViKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsDonViKSDaGan', ds)
  },
  async getDsLoaiKhachHangKSChuaGan({ commit, state }, data) {
    data.kieu = 1
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsLoaiKhachHangKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_LOAIKH.localeCompare(b.TEN_LOAIKH));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsLoaiKhachHangKSChuaGan', ds)
  },
  async getDsLoaiKhachHangKSDaGan({ commit, state }, data) {
    data.kieu = 2
    let ds = []
    let response = await PhanQuyenNhanVienNhanKSApi.getDsLoaiKhachHangKS(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds = ds.sort((a, b) => a.TEN_LOAIKH.localeCompare(b.TEN_LOAIKH));
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsLoaiKhachHangKSDaGan', ds)
  },
  async clearData({ commit, state }, data) {
    commit('clearData', data)
  },
  async clearGrid({ commit, state }, data) {
    commit('clearGrid', data)
  },
  async clearPopupGrid({ commit, state }, data) {
    commit('clearPopupGrid', data)
  },
}