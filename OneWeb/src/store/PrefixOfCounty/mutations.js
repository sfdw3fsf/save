// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  GET_DANH_SACH_SO_NE (state, data) {
    state.danhSachSoNe = data
    // state.itemNe = data && data.length > 0 ? data[0] : {}
  },
  GET_DANH_SACH_LOAI_SO (state, data) {
    state.danhSachCacLoaiSo = data
  },
  GET_DANH_SACH_PREFIX_CHUA_CHON (state, data) {
    state.danhSachPrefixChuaChon = data  
    for(let i = 0 ; i< state.danhSachPrefixDaChon.length ; i++){
      for(let j=0;j<state.danhSachPrefixChuaChon.length ;j++){
        if(state.danhSachPrefixChuaChon[j].prefix == state.danhSachPrefixDaChon[i].prefix){
          state.danhSachPrefixChuaChon.splice(j,1);  
          j++;        
        }
      }
    }
  },
  GET_DANH_SACH_PREFIX_DA_CHON (state, data) {
    state.danhSachPrefixDaChon = data    
    for(let i = 0 ; i< state.danhSachPrefixDaChon.length ; i++){
      for(let j=0;j<state.danhSachPrefixChuaChon.length ;j++){
        if(state.danhSachPrefixChuaChon[j].prefix == state.danhSachPrefixDaChon[i].prefix){
          state.danhSachPrefixChuaChon.splice(j,1);  
          j++;        
        }
      }
    }
  },
  SET_IS_LOADING_NOT_SELECT (state, data) {
    state.isLoadingNotSelect = data
  },
  SET_IS_LOADING_SELECT (state, data) {
    state.isLoadingSelect = data
  },
  GET_DANH_SACH_CAC_DON_VI (state, data) {
    state.danhSachCacDonVi = data
  },
  GET_DANH_SACH_QUAN_HUYEN (state, data) {
    state.danhSachQuanHuyen = data
  },
  GET_DANH_SACH_PHUONG_XA (state, data) {
    state.danhSachPhuongXa = data
  },
  GET_DANH_SACH_TONG_DAI (state, data) {
    state.danhSachTongDai = data
  },
  SET_DATA_NOT_SELECT (state, data) {
    state.dataNotSelect = data
  },
  SET_DANH_SACH_PREFIX_DA_CHON (state, data) {
    state.danhSachPrefixDaChon = data
  },
  SET_DANH_SACH_PREFIX_CHUA_CHON (state, data) {
    state.danhSachPrefixChuaChon = data
  },
  GET_DANH_SACH_DAI_CO_DINH (state, data) {
    state.danhSachDaiCoDinh = data
  },
  GET_DANH_SACH_DICH_VU (state, data) {
    state.danhSachDichVu = data
  },
  SET_SHOW_MODAL (state, data) {
    state.isShowModal = data
  },
  SET_DATA_SELECT (state, data) {

    state.dataSelect = data
  },
  SET_PARAM_FIXED (state, data) {
    state.paramFixedNumber = data
  },
  SET_DANH_ITEM_SELECT (state, data) {
    state.dataItem = data
  },
  SET_SELECT_NE (state, data) {
    state.selectNe = data
  },
  SET_DON_VI_ID (state, data) {
    state.donViID = data
  }
}
