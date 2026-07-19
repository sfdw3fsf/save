// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsNhanVienTheoDonVi(state, data) {
        state.dsNhanVienTheoDonVi = data
    },
    setDsLoaiHinhThueBaoKSDaGan(state, data) {
        state.dsLoaiHinhThueBaoKSDaGan = data
    },
    setDsLoaiHinhThueBaoKSChuaGan(state, data) {
        state.dsLoaiHinhThueBaoKSChuaGan = data
    },
    setDsHopDongThueBaoKSDaGan(state, data) {
        state.dsHopDongThueBaoKSDaGan = data
    },
    setDsHopDongThueBaoKSChuaGan(state, data) {
        state.dsHopDongThueBaoKSChuaGan = data
    },
    setDsPhanLoaiKhachHangKSChuaGan(state, data) {
        state.dsPhanLoaiKhachHangKSChuaGan = data
    },
    setDsPhanLoaiKhachHangKSDaGan(state, data) {
        state.dsPhanLoaiKhachHangKSDaGan = data
    },
    setDsDonViKSDaGan(state, data) {
        state.dsDonViKSDaGan = data
    },
    setDsDonViKSChuaGan(state, data) {
        state.dsDonViKSChuaGan = data
    },
    setDsLoaiKhachHangKSChuaGan(state, data) {
        state.dsLoaiKhachHangKSChuaGan = data
    },
    setDsLoaiKhachHangKSDaGan(state, data) {
        state.dsLoaiKhachHangKSDaGan = data
    },
    add(state, data) {
        data.data.forEach(element => data.source.push(element))
    },
    remove(state, data) {
        data.data.forEach(element => {
            let index = data.source.indexOf(element)
            data.source.splice(index, 1)
        })
    },
    clearData(state, data) {
        state.dsNhanVienTheoDonVi = []
        state.dsLoaiHinhThueBaoKSDaGan = []
        state.dsLoaiHinhThueBaoKSChuaGan = []
        state.dsHopDongThueBaoKSDaGan = []
        state.dsHopDongThueBaoKSChuaGan = []
        state.dsPhanLoaiKhachHangKSDaGan = []
        state.dsPhanLoaiKhachHangKSChuaGan = []
        state.dsDonViKSDaGan = []
        state.dsDonViKSChuaGan = []
        state.dsLoaiKhachHangKSChuaGan = []
        state.dsLoaiKhachHangKSDaGan = []
    },
    clearGrid(state, data) {
        state.dsLoaiHinhThueBaoKSDaGan = []
        state.dsLoaiHinhThueBaoKSChuaGan = []
        state.dsHopDongThueBaoKSDaGan = []
        state.dsHopDongThueBaoKSChuaGan = []
        state.dsPhanLoaiKhachHangKSDaGan = []
        state.dsPhanLoaiKhachHangKSChuaGan = []
    },
    clearPopupGrid(state, data) {
        state.dsDonViKSDaGan = []
        state.dsDonViKSChuaGan = []
        state.dsLoaiKhachHangKSChuaGan = []
        state.dsLoaiKhachHangKSDaGan = []
    }
}
