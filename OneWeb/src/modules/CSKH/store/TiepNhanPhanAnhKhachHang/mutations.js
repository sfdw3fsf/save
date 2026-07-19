// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsNganhNghe(state, data) {
        state.dsNganhNghe = data
    },
    setDsLoaiKH(state, data) {
        state.dsLoaiKH = data
    },
    setDsLoaiKHLon(state, data) {
        state.dsLoaiKHLon = data
    },
    setDsHinhThucKN(state, data) {
        state.dsHinhThucKN = data
    },
    setDsNoiDungPA(state, data) {
        state.dsNoiDungPA = data
    },
    setDsPAMoiTiepNhanTheoNV(state, data) {
        state.dsPAMoiTiepNhanTheoNV = data
    },
    clearData(state) {
        state.dsNganhNghe = []
        state.dsLoaiKH = []
        state.dsLoaiKHLon = []
        state.dsHinhThucKN = []
        state.dsNoiDungPA = []
        state.dsPAMoiTiepNhanTheoNV = []
    }
}
