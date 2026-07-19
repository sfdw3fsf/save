import GiamThinhPhieuKhaoSatApi from '../../api/GiamThinhPhieuKhaoSatApi'
export const actions = {
    getdsLoaiPhieuKhaoSat({ commit, state }) {
        const data1 = [
            { value: '0', text: 'Thuê bao không khảo sát Lắp mới' },
            { value: '1', text: 'Thuê bao không khảo sát Báo hỏng' },
            { value: '2', text: 'Thuê bao không khảo sát Báo hỏng' },
        ]
        commit('setdsLoaiPhieuKhaoSat', data1)
    },
    async getdsDonViTrungTamVienThong({ commit, state }) {
        console.log('getdsDonViTrungTamVienThong')
        let response = await GiamThinhPhieuKhaoSatApi.getdsDonViTrungTamVienThong(this._vm.axios)
        let ds = []
        let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.forEach(element => {
                    ds.push({
                        value: element.DONVI_ID,
                        text: element.TEN_DV
                    })
                })
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setdsDonViTrungTamVienThong', ds)
    },
    async getdsDienThoaiVien({ commit, state }) {
        let response = await GiamThinhPhieuKhaoSatApi.getdsDienThoaiVien(this._vm.axios)
        let ds = []
        let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                ds.push({ text: '', value: '' })
                response.data.data.forEach(element => {
                    ds.push({
                        value: element.NHANVIEN_ID,
                        text: element.TEN_NV
                    })
                })
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setdsDienThoaiVien', ds)
    },
    async getdsPhanLoaiKhachHang({ commit, state }) {
        let response = await GiamThinhPhieuKhaoSatApi.getdsPhanLoaiKhachHang(this._vm.axios)
        let ds = []
        let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.forEach(element => {
                    ds.push({
                        value: element.MA_PLKH,
                        text: element.TEN_PLKH
                    })
                })
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setdsPhanLoaiKhachHang', ds)
    },
    async getdsLoaiKQGiamThinh({ commit, state }) {
        let response = await GiamThinhPhieuKhaoSatApi.getdsLoaiKQGiamThinh(this._vm.axios)
        let ds = []
        let page = {}
        if (response.data.error_code === 'BSS-00000000') {
            if (response.data.data !== undefined) {
                response.data.data.forEach(element => {
                    ds.push({
                        value: element.TRALOI_ID,
                        text: element.NOIDUNG
                    })
                })
            } else {
                this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
            }
        } else {
            this._vm.$root.toastError(respone.data.message)
        }
        commit('setdsLoaiKQGiamThinh', ds)
    },
    async getDsThueBao({ commit, state }, data) {
        try {
            let response = await GiamThinhPhieuKhaoSatApi.getdsPhieuGiamThinh(
                this._vm.axios,
                data.pageNo,
                data.pageSize,
                data
            );
            console.log(response)
            let ds = response.data.data.data || []
            commit("setDsThueBao", ds);
            return response;
        } catch (error) {
            commit("setDsThueBao", []);
            return error.response.data
        }
    },
    async getNoiDungKS({ commit, state }, data) {
        try {
            let response = await GiamThinhPhieuKhaoSatApi.getChiTietTraLoiKhaoSat(
                this._vm.axios,
                data
              );
            let noiDung = response.data.data.data || []
            let ds = []
            noiDung.forEach((element, index) => {
                element.STT = index + 1
                ds.push(element)
            })

            commit("setNoiDungKS", ds);
            return response;
        } catch (error) {
            commit("setNoiDungKS", []);
            return error.response.data
        }
    },
    clearData({ commit, state }, data)
    {
        commit("clearData")
    }
}