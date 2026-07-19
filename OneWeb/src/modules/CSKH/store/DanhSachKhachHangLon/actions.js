import API from '../../api/DanhSachKhachHangLon'

export const actions = {
    async getdsQuan ({commit, state}) {
        let response = await API.getDanhSachQuan(this._vm.axios)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                ds = response.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        console.log("Quận: ", ds)
        commit('setDSQuan', ds)
    },
    async getdsLoaiKHL ({commit, state}) {
        let response = await API.getDanhSachLoaiKHL(this._vm.axios)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                ds = response.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSLoaiKHL', ds)
    },
    async getdsLoaiKH ({commit, state}) {
        let response = await API.getDanhSachLoaiKH(this._vm.axios)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                ds = response.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSLoaiKH', ds)
    },
    async getdsLanTao ({commit, state}, data) {
        let response = await API.getDanhSachLanTao(this._vm.axios, data)
        let ds = []
        console.log("response: ", response)
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                ds = response.data.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSLanTao', ds)
    },
    async getdsChuKyNo ({commit, state}) {
        let response = await API.getDanhSachChuKyNo(this._vm.axios)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                ds = response.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSChuKyNo', ds)
    },
    async getdsKhachHang({commit, state},data) {
        let response = await API.getDanhSachKhachHang(this._vm.axios, data.pageNum, data.pageSize,data.totalRow, data)
        let ds = []
        let dataExcel = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.data.forEach(element => {
                    dataExcel.push({
                        MA_KH: element.MA_KH,
                        TEN_KH: element.TEN_KH,
                        DIACHI_KH: element.DIACHI_KH,
                        BINHQUAN_DT: element.BINHQUAN_DT
                    })
                })
                commit('setDataExcel', dataExcel)
                commit("setTotalElements",response.data.data.totalElement)
                ds = response.data.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSKhachHang', ds)
    },
    async taodsKHL({commit, state}, data) {
        let response = await API.taoDanhSachKHL(this._vm.axios, data)
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data) {
                // this._vm.$toast.success('Tạo danh sách khách hàng lớn thành công')
            } else {
                this._vm.$toast.error('Tạo danh sách khách hàng lớn thất bại')
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
    },
    async clearData({ commit, state }, data) {
        commit('clearData', data)
    },
    async chiTietdsLanTao({commit, state}, data) {
        let response = await API.chiTietDanhSachLanTao(this._vm.axios, data.pageNo, data.pageSize, data.data)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.data.forEach(element => {
                    ds.push({
                        MA_KH: element.MA_KH,
                        TEN_KH: element.TEN_KH,
                        DIACHI_KH: element.DIACHI_KH,
                        BINHQUAN_DT: element.BINHQUAN_DT
                    })
                })
                // ds = response.data.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSChiTietKhachHang', ds)
    },
    async getThongTinNo({commit, state}, data) {
        let response = await API.getThongTinNoTheoKhachHang(this._vm.axios, data)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                ds = response.data.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit('setDSThongTinNoTheoKhachHang', ds)
    },
    async clearDSThongTinNo({ commit, state }, data) {
        commit('cleanDSThongTinNo', data)
    },
}