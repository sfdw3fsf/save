// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    
    clearData (state, data) {
        // state.ds_VT = []
        // state.ds_nv = []
        // state.ds_ccdc = []
        // state.ds_capphat = []
        // state.ds_DV = []
        state.DSTinhTrangTBi = []
        // state.ds_serial = []
    },
    
    // setdsVT_NVCCDC (state, data) {
    //     state.ds_VT = data
    // },
    
    // setDanhMucDonVi (state, data) {
    //     state.ds_DV = data
    // },
    
    setDSTinhTrangTBi (state, data) {
        state.DSTinhTrangTBi = data
    },
    // setDSCapPhat (state, data) {
    //     state.ds_capphat = data
    // },
    // setdsSerial_NVCCDC (state, data) {
    //     state.ds_serial = data
    // },
    // setds_luuCP_NVCCDC (state, data) {
    //     state.ds_luuCP = data
    // },
    
    // setData_NVCCDC (state, data) {
    //     state.capnv = data.capnv
    //     state.dtpNgayDK = data.dtpNgayDK
    // },    
  }
  