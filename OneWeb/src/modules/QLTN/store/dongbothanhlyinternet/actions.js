import API from '../../api/DongBoThanhLyInternet'

export const actions = {
    async getdsDichVu({commit, state}) {
        let response = await API.getDanhSachDichVu(this._vm.axios)
        response.data.data.sort((a, b) => {
            let item1 = a.TEN_DVVT.toUpperCase()
            let item2 = b.TEN_DVVT.toUpperCase()
            if (item1 < item2) {
                return -1;
            }
            if (item1 > item2) {
                return 1;
            }
            
            return 0;
        })
        console.log(response.data.data)
        try {
            commit("setDanhSachDichVu", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsQuyTrinh({commit, state}) {
        let response = await API.getDanhSachQuyTrinh(this._vm.axios)
        try {
            commit("setDanhSachQuyTrinh", response.data.data)
        } catch (error) {
            throw error
        }
    },
    async getdsLoaiHD({commit, state}) {
        let response = await API.getDanhSachLoaiHD(this._vm.axios)
        response.data.data.sort((a, b) => {
            let item1 = a.TEN_LOAIHD.toUpperCase()
            let item2 = b.TEN_LOAIHD.toUpperCase()
            if (item1 < item2) {
                return -1;
            }
            if (item1 > item2) {
                return 1;
            }
            
            return 0;
        })
        console.log(response.data.data)
        try {
            commit("setDanhSachLoaiHD", response.data.data)
        } catch (error) {
            throw error
        }
    }
}