// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
         setDsKetQuaKN(state, data) {
           state.dsKetQuaKN = data
         },
         setDsLoaiKH(state, data) {
           state.dsLoaiKH = data
         },
         setDsDichVuVT(state, data) {
           state.dsDichVuVT = data
         },
         setDsHinhThucKN(state, data) {
           state.dsHinhThucKN = data
         },
         setDsChuDeKhieuNai(state, data) {
           state.dsChuDeKN = data
         },
         setDsLoaiHinhKN(state, data) {
           state.dsLoaiHinhKN = data
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
