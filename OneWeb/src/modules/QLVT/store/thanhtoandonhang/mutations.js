// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {

    // clearData (state, data) {
    //     state.GridDS_ThanhToan = []
    //     state.GridDS_VT_TT = []
    //     state.GridDS_VT_DonHang = []
    //     state.dvi_id = "" //frmTraCuuHopDong
    //     state.cboDonHang = []
    //     state.cboHinhThucTra = []
    //     state.cboNVThuNgan = []
    //     state.cboDonHang_Filter = []
    //     state.cboHinhThucTra_Filter = []
    //     state.cboNVThuNgan_Filter = []
    //     state.DSDonViTinh_Filter = []
    // },
    // ClearGrid(state, data){
    //     state.GridDS_VT_TT = [],
    //     state.GridDS_VT_DonHang = []
    // },
    // setCboDonHang (state, data) {
    //     state.cboDonHang = data
    // },
    // setCboHinhThucTra (state, data) {
    //     state.cboHinhThucTra = data
    // },
    // setCboNVThuNgan (state, data) {
    //     state.cboNVThuNgan = data
    // },
    // setDSThanhToanDK (state, data) {
    //     if (data.length){
    //         data.forEach(a => {
    //             let maDH = state.cboDonHang_Filter.find(b=>b.DONHANG_ID.toString() == a.DONHANG_ID.toString());
    //             maDH ? a['MA_DH_NEW'] = maDH.MA_DH_NEW : "";
    //             let htTra = state.cboHinhThucTra_Filter.find(b=>b.HT_TRA_ID.toString() == a.HT_TRA_ID.toString());
    //             htTra ? a['HT_TRA'] = htTra.HT_TRA : "";
    //             let nv = state.cboNVThuNgan_Filter.find(b=>b.NHANVIEN_ID.toString() == a.NHANVIEN_ID.toString());
    //             if (nv){
    //                 let arrayTenNv = nv.TEN_NV.split('_')
    //                 arrayTenNv.length > 2 ? arrayTenNv.splice(2,1) : ""
    //                 a['TEN_NV'] = arrayTenNv.join("-")
    //             }
    //             // nv ? a['TEN_NV'] = nv.TEN_NV : "";
    //         });
    //     }
    //     state.GridDS_ThanhToan = data
    // },
    // setDSVatTuThanhToan (state, data) {
    //     if (data.length){
    //         data.forEach(a => {
    //             let vatTu = state.cboVatTu_Filter.find(b=>b && b.vatTuID.toString() == a.VATTU_ID.toString());
    //             vatTu ? a['MA_VT'] = vatTu.maVatTu : "";

    //             if (a.DVITINH_ID != null && a.DVITINH_ID != ''){
    //                 let dvt = state.DSDonViTinh_Filter.find(b=>b && b.dviTinhId.toString() == a.DVITINH_ID.toString());
    //                 dvt ? a['DVI_TINH'] = dvt.dviTinh : "";
    //             }

    //             a.SL_THANHTOAN.toString() ? a.SL_THANHTOAN = parseInt(a.SL_THANHTOAN) : ""
    //             a.DONGIA.toString() ? a.DONGIA = parseInt(a.DONGIA) : ""
    //             a.TIEN.toString() ? a.TIEN = parseInt(a.TIEN) : ""
    //             a.VAT.toString() ? a.VAT = parseInt(a.VAT) : ""

                
    //         });
    //     }
    //     state.GridDS_VT_TT = data
    // },
    // setDSVatTuTheoDH (state, data) {
    //     if (data.length){
    //         data.forEach(a => {
    //             let vatTu = state.cboVatTu_Filter.find(b=>b && b.vatTuID.toString() == a.VATTU_ID.toString());
    //             vatTu ? a['MA_VT'] = vatTu.maVatTu : "";

    //             if (a.DVITINH_ID != null && a.DVITINH_ID != ''){
    //                 let dvt = state.DSDonViTinh_Filter.find(b=>b && b.dviTinhId.toString() == a.DVITINH_ID.toString());
    //                 dvt ? a['DVI_TINH'] = dvt.dviTinh : "";
    //             }
                

    //             a.SL_THANHTOAN.toString() ? a.SL_THANHTOAN = parseInt(a.SL_THANHTOAN) : ""
    //             a.SL_CHUAN.toString() ? a.SL_CHUAN = parseInt(a.SL_CHUAN) : ""
    //             a.SLTTCL.toString() ? a.SLTTCL = parseInt(a.SLTTCL) : ""
    //             if (a.hasOwnProperty('SL_DAT')){
    //                 a.SL_DAT.toString() ? a.SL_DAT = parseInt(a.SL_DAT) || 0 : ""
    //             }
    //             else{
    //                 a.SL_DAT = 0
    //             }
    //         });
    //     }
    //     state.GridDS_VT_DonHang = data
    // },
    // setCboDonHang_Filter (state, data) {
    //     state.cboDonHang_Filter = data
    // },
    // setCboHinhThucTra_Filter (state, data) {
    //     state.cboHinhThucTra_Filter = data
    // },
    // setCboNVThuNgan_Filter (state, data) {
    //     state.cboNVThuNgan_Filter = data
    // },
    // setCboVatTu_Filter (state, data) {
    //     state.cboVatTu_Filter = data
    // },
    // setDSDonViTinh (state, data) {
    //     state.DSDonViTinh_Filter = data
    // },
    
  }
    