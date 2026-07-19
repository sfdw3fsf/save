import DanhSachPhieuKhaoSatOutboundApi from '../../api/DanhSachPhieuKhaoSatOutboundApi'
export const actions = {
    async getdsLoaiNhomKhaoSat({ commit, state }) {
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsLoaiNhomKhaoSat(this._vm.axios)
        let ds = []
        let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.forEach(element => {
                    ds.push({
                        value: element.nhomId,
                        text: element.tenNhom
                    })
                })
                ds = ds.sort((a,b) => a.value > b.value ? 1 : -1)
            } else {
                // this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setdsLoaiNhomKhaoSat', ds)
    },
    async getdsTrangThaiLuotGiaoKhaoSat({ commit, state }) {
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsTrangThaiLuotGiaoKhaoSat(this._vm.axios)
        let ds = []
        let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.forEach(element => {
                    ds.push({
                        value: element.ttLuotId,
                        text: element.trangThaiLuot
                    })
                })
            } else {
                // this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setdsTrangThaiLuotGiaoKhaoSat', ds)
    },
    async getdsTrangThaiPhieu({ commit, state }) {
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsTrangThaiPhieu(this._vm.axios)
        let ds = []
        let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.forEach(element => {
                    ds.push({
                        value: element.ttPhieuId,
                        text: element.trangThaiPhieu
                    })
                })
            } else {
                // this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setdsTrangThaiPhieu', ds)
    },
    async clearData({ commit, state }, data) {
        commit('clearData', data)
    },
    async getdsLuotGiaoNhanKhaoSat({ commit, state }, data) {
        let ds = []
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsLuotGiaoNhanKhaoSat(this._vm.axios, data.pageNo, data.pageSize, data);
        commit('setdsPhieuDaGiaoKhaoSat', [])
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data !== undefined && response.data.data.data !== undefined) {
            ds = response.data.data.data
            ds.forEach((element, index) => element.STT = index + 1)
          } else {
            // this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
          }
        } else {
          this.$root.toastError(respone.data.message);
        }
        commit('setDsLuotGiaoNhanKhaoSat', ds)
      },
      async getdsPhieuDaGiaoKhaoSat({ commit, state }, data) {
        let ds = []
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsPhieuDaGiaoKhaoSat(this._vm.axios, data.pageNo, data.pageSize, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data !== undefined && response.data.data.data !== undefined) {
            ds = response.data.data.data
            ds.forEach((element, index) => element.STT = index + 1)
          } else {
            // this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
          }
        } else {
          this.$root.toastError(respone.data.message);
        }
        commit('setdsPhieuDaGiaoKhaoSat', ds)
      },
      async getdsThongTinCSKHThueBao({ commit, state }, data) {
        let ds = []
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsThongTinCSKHThueBao(this._vm.axios, data);
        console.log('response', response)
        if (response.data.error_code === "BSS-00000000") {
          ds = response.data.data.data || []
          ds.forEach((element, index) => element.STT = index + 1)
          console.log(ds)
        } else {
          this.$root.toastError(respone.data.message);
        }
        commit('setdsThongTinCSKHThueBao', ds)
      },
      async getdsNhanVienKyThuatDiaBan({ commit, state }, data) {
        let ds = []
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsNhanVienDiaBan(this._vm.axios, data.pageNo, data.pageSize, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data !== undefined && response.data.data.data !== undefined) {
            ds = response.data.data.data
            ds.forEach((element, index) => element.STT = index + 1)
          } else {
            // this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
          }
        } else {
          this.$root.toastError(respone.data.message);
        }
        commit('setdsNhanVienKyThuatDiaBan', ds)
      },
      async getdsNhanVienKinhDoanhDiaBan({ commit, state }, data) {
        let ds = []
        let response = await DanhSachPhieuKhaoSatOutboundApi.getdsNhanVienDiaBan(this._vm.axios, data.pageNo, data.pageSize, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data !== undefined && response.data.data.data !== undefined) {
            ds = response.data.data.data
            ds.forEach((element, index) => element.STT = index + 1)
          } else {
            // this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
          }
        } else {
          this.$root.toastError(respone.data.message);
        }
        commit('setdsNhanVienKinhDoanhDiaBan', ds)
      },
}