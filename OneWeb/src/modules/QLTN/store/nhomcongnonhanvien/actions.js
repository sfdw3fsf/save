import API from '../../api/NhomCongNoNhanVien'
export const actions = {
    async getDanhSachCongNo ({commit, state}) {
        let response = await API.getDanhSachCongNo(this._vm.axios)
        try {
            commit("setDanhSachCongNo", response.data.data)
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhSachNhanVienCongNo ({commit, state}, data) {
        let response = await API.getDanhSachNhanVienCongNo(this._vm.axios, data)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            response.data.data.forEach(element => {
                ds.push({
                    NHOMCNO_ID: data.nhomCNoId,
                    TEN_NHOM: data.tenNhom,
                    NHANVIEN_ID: element.NHANVIEN_ID,
                    MA_NV: element.MA_NV,
                    TEN_NV: element.TEN_NV,
                    TEN_DV: element.TEN_DV,
                    LOAI_NV: element.LOAI_NV,
                    LOAINV: element.LOAI_NV == 1 ? true : false,
                })
            })
        }
        try {
            if (data.daGan == 1) {
                commit("setDanhSachNhanVienDaGan", ds)
            } else {
                commit("setDanhSachNhanVienChuaGan", ds)
            }
        } catch (error) {
            throw error
        }
    },
    async setDanhSachNhanVien ({commit, state}, data) {
        if (data.setGan == 1) {
            commit('add', { source: state.dsNhanVienDaGan, data: data.itemArr })
            commit('remove', { source: state.dsNhanVienChuaGan, data: data.itemArr, field: data.field })
        }
        else {
            commit('remove', { source: state.dsNhanVienDaGan, data: data.itemArr, field: data.field })
            commit('add', { source: state.dsNhanVienChuaGan, data: data.itemArr })
        }
    },
    async clearGrid({ commit, state }, data) {
        commit('clearGrid', data)
    },
    async capNhapDanhSachNhomCongNo ({commit, state}, data) {
        try {
            let response = await API.capNhapDanhSachNhomCongNo(this._vm.axios, data)
            this._vm.$toast.success("Cập nhật dữ liệu thành công!")
        } catch (error) {
            this._vm.$toast.error(error.response.data.message)
        }
    },
    async getAllNhanVienDaGan ({commit, state}, data) {
        commit("setAllNhanVienDaGan", [])
        data.forEach(async (element) => {
            let ds = []
            let tmp = {
                nhomCNoId: element.NHOMCNO_ID,
                tenNhom: element.TEN_NHOM,
                daGan: 1
            }
            let response = await API.getDanhSachNhanVienCongNo(this._vm.axios, tmp)
            if (response.data.data.length > 0) {
                response.data.data.forEach(item => {
                    ds.push({
                        NHANVIEN_ID: item.NHANVIEN_ID,
                        MA_NV: item.MA_NV,
                        TEN_NV: item.TEN_NV,
                        TEN_DV: item.TEN_DV,
                        LOAI_NV: item.LOAI_NV,
                        NHOMCNO_ID: tmp.nhomCNoId,
                        TEN_NHOM: tmp.tenNhom,
                    })
                })
                commit("add", {source: state.dsAllNhanVienDaGan, data: ds })
            }
        })
    },
    async getDanhSachNhanVien ({commit, state}, data) {
        commit("setDanhSachNhanVienChuaGan", data)
    },
    async getDanhSachNhanVienDaGan ({commit, state}, data) {
        commit("setDanhSachNhanVienDaGan", data)
    }
}
