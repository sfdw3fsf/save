import API from '../../api/CapNhapHoSoCongNo'

export const actions = {
    async getdsDonVi({commit, state}, data) {
        let response = await API.getDanhSachDonVi(this._vm.axios)
        try {
            commit("setDanhSachDonVi", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsNhanVienQL({commit, state}, data) {
        let response = await API.getDanhSachNhanVienQL(this._vm.axios, data)
        try {
            commit("setDanhSachNhanVienQL", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsNhanVienThuCuoc({commit, state}, data) {
        let response = await API.getDanhSachNhanVienThuCuoc(this._vm.axios, data)
        console.log("nhân viên thu cước: ", response)
        try {
            commit('setDanhSachNhanVienThuCuoc', response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getDanhSachLoaiKhachHang({ commit, state }) {
        try {
            let response = await API.getDanhSachLoaiKhachHang(this._vm.axios)
            commit('setDanhSachLoaiKhachHang', response.data.data)
            return response
        } catch (error) {
            throw error
        }
    },
    async getdsPhuongAnXL({commit, state})  {
        let response = await API.getDanhSachPhuongAnXL(this._vm.axios)
        try {
            commit("setDanhSachPhuongAnXL", response.data.data)
        } catch (error) {
            throw error
        }
        return response
    },
    async getdsKyHD({commit, state}) {
        let response = await API.getDanhSachKyHD(this._vm.axios)
        try {
            commit("setDanhSachKyHD", response.data.data)
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhMucHoSo({commit, state}) {
        let response = await API.getDanhMucHoSo(this._vm.axios)
        try {
            commit("setDanhMucHoSo", response.data.data)
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhSachPhanLoaiKH({commit, state}, data) {
        let response = await API.getDanhSachPhanLoaiKhachHang(this._vm.axios, data)
        try {
            commit("setDanhSachPhanLoaiKH", response.data.data)
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhSachCongNo({commit, state}, data) {
        let response = await API.getDanhSachCongNo(this._vm.axios, data)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data.length !== 0) {
                ds = response.data.data
            } else {
                this._vm.$toast.error('Không có dữ liệu, vui lòng thử lại!');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        commit("setDanhSachCongNo", ds)
    },
    async updateHoSoCongNo({commit, state}, data) {
        API.updateHoSoCongNo(this._vm.axios, data).then(async (response) => {
            if (response.data.error_code === 'BSS-00000000') {
                this._vm.$toast.success(response.data.data)
            } else {
                this._vm.$toast.error(response.data.message)
            }
        }).catch(reject => {
            console.log("reject: ", reject)
            this._vm.$toast.error(reject.data.message)
        })
        // let response = await API.updateHoSoCongNo(this._vm.axios, data)
        // if (response.data.error_code === 'BSS-00000000') {
        //     this._vm.$toast.success(response.data.data)
        // } else {
        //     this._vm.$toast.error(response.data.message)
        // }
    },
    async clearGrid({ commit, state }, data) {
        commit('clearGrid', data)
    },
}