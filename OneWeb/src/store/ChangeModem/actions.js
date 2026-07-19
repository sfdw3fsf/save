// // các hàm dùng để gọi API, gọi hàm khác trong mutations để thay đổi state của của đối tượng trong store
// import api from '@/modules/contract/setup/ChangeModem/api'
//
// export const actions = {
//   async layDanhSachDichVu ({commit, state}) {
//     let response = await api.layDanhSachDichVu(this._vm.axios)
//     try {
//       commit('GET_DANH_SACH_DICH_VU', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layLoaiHinh ({commit, state}) {
//     let response = await api.layLoaiHinh(this._vm.axios)
//     try {
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layHopDongTheoMatb ({commit, state}, data) {
//     let response = await api.layHopDongTheoMatb(this._vm.axios, data)
//     try {
//       commit('GET_HOP_DONG_THEO_TB', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layDanhLoaiThietBi ({commit, state}, data) {
//     let response = await api.layDanhLoaiThietBi(this._vm.axios, data)
//     try {
//       commit('GET_DANH_SACH_LOAI_THIET_BI', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//
//   async layDanhSachYeuCau ({commit, state}, data) {
//     let response = await api.layDanhSachYeuCau(this._vm.axios, data)
//     try {
//       commit('GET_DANH_SACH_YEU_CAU', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layDanhBaTheoMaThueBao ({commit, state}, data) {
//     let response = await api.layDanhBaTheoMaThueBao(this._vm.axios, data)
//     console.log('response.data',[response.data.data]);
//     try {
//       commit('GET_DANH_BA_THEO_MTB', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layDanhSachThaytheVatTu ({commit, state}, data) {
//     let response = await api.layDanhSachThaytheVatTu(this._vm.axios, data)
//     try {
//       commit('GET_DANH_THAY_THE_VT', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layKeyThemMoi ({commit, state}, data) {
//     let response = await api.layKeyThemMoi(this._vm.axios, data)
//     try {
//       commit('GET_KEY_THEM_MOI', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layMaGiaoDich ({commit, state}, data) {
//     let response = await api.layMaGiaoDich(this._vm.axios, data)
//     try {
//       // commit('GET_KEY_THEM_MOI', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layhdtbdv ({commit, state}, data) {
//     let response = await api.layhdtbdv(this._vm.axios, data)
//     try {
//       // commit('GET_KEY_THEM_MOI', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layDanhSachThayTheHD ({commit, state}, data) {
//     let response = await api.layDanhSachThayTheHD(this._vm.axios, data)
//     try {
//       console.log('response.data.data', response.data.data)
//       // const data = [{hdtb_id: 2324772,
//       //   ma_chung: 'HPG-TL/00054349',
//       //   ma_tb: null,
//       //   ngay_yc: '2017-05-11 18:35:45',
//       //   ten_dv: 'An Dương 7',
//       //   ten_kh: 'Bùi Công Trúc',
//       //   ten_loaihd: 'Chấm dứt sử dụng',
//       //   thuebao_id: 63500,
//       //   trangthai: 'Đã hoàn thành'},
//       // {hdtb_id: 2324772,
//       //   ma_chung: 'HPG-TL/000543491',
//       //   ma_tb: null,
//       //   ngay_yc: '2017-05-11 18:35:45',
//       //   ten_dv: 'An Dương 7',
//       //   ten_kh: 'Bùi Công Trúc',
//       //   ten_loaihd: 'Chấm dứt sử dụng',
//       //   thuebao_id: 63500,
//       //   trangthai: 'Đã hoàn thành'}
//       // ]
//       commit('GET_DANH_SACH_THAY_THE_HOP_DONG', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   },
//   async layHopDongTheoMaGD ({commit, state}, data) {
//     let response = await api.lay_ds_hopdong_theo_ma_gd(this._vm.axios, data)
//     try {
//       commit('GET_HOP_DONG_THEO_TB', response.data.data)
//     } catch (err) {
//       throw err
//     }
//     return response
//   }
//
// }
