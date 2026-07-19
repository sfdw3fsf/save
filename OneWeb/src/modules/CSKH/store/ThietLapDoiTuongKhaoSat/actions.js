import API from '../../api/ThietLapDoiTuongKhaoSat'

export const actions = {
    async getdsDichVu ({commit, state}, data) {
        let response = await API.getDanhSachDichVu(this._vm.axios, data, 0, 1000)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data.data !== undefined) {
                ds = response.data.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        if (data === 2) {
            commit('setDSDichVuKS', ds)
        } else {
            commit('setDSDichVuKhongKS', ds)
        }
    },
    async getdsKieuLD ({commit, state}, data) {
        let response = await API.getDanhSachKieuLD(this._vm.axios, data, 0, 1000)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data.data !== undefined) {
                ds = response.data.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        if (data === 2) {
            commit('setDSKieuLDKS', ds)
        } else {
            commit('setDSKieuLDKhongKS', ds)
        }
    },
    async getdsTinhTrangHong ({commit, state}, data) {
        let response = await API.getDanhSachTinhTrangHong(this._vm.axios, data, 0, 1000)
        let ds = []
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data.data !== undefined) {
                ds = response.data.data.data
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$toast.error(response.data.message)
        }
        if (data === 2) {
            commit('setDSTinhTrangHongKS', ds)
        } else {
            commit('setDSTinhTrangHongKhongKS', ds)
        }
    },
    //setGan = 1 => set gán | setGan = 2 => set chưa gán
    setDanhSachKSKhongKS ({commit, state}, data) {
        switch (data.typeDanhSach) {
            case 'dsDichVu':
                if (data.setGan == 1) {
                    commit('add', { source: state.dsDichVuKS, data: data.itemArr })
                    commit('remove', { source: state.dsDichVuKhongKS, data: data.itemArr, field: data.field })
                }
                else {
                    commit('remove', { source: state.dsDichVuKS, data: data.itemArr, field: data.field })
                    commit('add', { source: state.dsDichVuKhongKS, data: data.itemArr })
                }
                break;
            case 'dsKieuLD':
                if (data.setGan == 1) {
                    commit('add', { source: state.dsKieuLDKS, data: data.itemArr })
                    commit('remove', { source: state.dsKieuLDKhongKS, data: data.itemArr, field: data.field })
                }
                else {
                    commit('remove', { source: state.dsKieuLDKS, data: data.itemArr, field: data.field })
                    commit('add', { source: state.dsKieuLDKhongKS, data: data.itemArr })
                }
                break;
            case 'dsTinhTrangHong': 
                if (data.setGan == 1) {
                    commit('add', { source: state.dsTinhTrangHongKS, data: data.itemArr })
                    commit('remove', { source: state.dsTinhTrangHongKhongKS, data: data.itemArr, field: data.field })
                }
                else {
                    commit('remove', { source: state.dsTinhTrangHongKS, data: data.itemArr, field: data.field })
                    commit('add', { source: state.dsTinhTrangHongKhongKS, data: data.itemArr })
                }
                break;
        }
    },
    async capNhapThietLap ({commit, state}, data) {
        API.capNhapThongTinThietLap(this._vm.axios, data).then((response) => {
            if (response.data.error_code === "BSS-00000000") {
                this._vm.$toast.success("Cập nhập thành công !");
            } else {
                this._vm.$toast.error(response.data.message);
            }
        }).catch((reject) => {
            if (reject.response && reject.response.data && reject.response.data.message)
            this._vm.$toast.error(reject.response.data.message);
        else this._vm.$toast.error("Có lỗi xảy ra!");
        })
    }
}