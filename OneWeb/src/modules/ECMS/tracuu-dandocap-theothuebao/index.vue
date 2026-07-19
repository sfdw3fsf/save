<template src="./index.html">
</template>

<script>

import ModalLichSuCap from './ModalLichSuCap'
import ModalChonThueBao from './ModalChonThueBao'
import InLyLichDayMay from './InLyLichDayMay'
import report from './report'
import ToaDo from './ToaDo/Modal'
// import SearchAccount from '../../EXTS/HOPDONG/ThayDoiThongTinKhuyenMai/Popups/SearchAccount/SearchAccount'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'

export default {
  components: {
    ModalLichSuCap,
    ModalChonThueBao,
    InLyLichDayMay,
    report,
    ToaDo,
    SearchAccount
  },
  data () {
    return {
      thueBaoId: null,
      hdtbId: null,
      soMay: '',
      loaiTimKiem: 0,
      tenThueBao: '',
      tt_capnhat: '',
      loaiHinh: '',
      trangThai: '',
      diaChi: '',
      toaDo: '',
      toQL: '',
      tramTB: '',
      ghichu: '',
      ketCuoi: '',
      cap: '',
      soi: '',
      vitri1: '',
      vitri2: '',
      culy: '',
      isThueBao: '',
      daucuoi_id: '',
      viDo_LD: '',
      kinhDo_LD: '',
      soDoTuyenCap: [],
      tempSoDoTuyenCap: [],
      pageTraCuu: true,
      modalShowLichSuCap: false,
      modalShowInLyLich: false,
      dsThueBao: [],
      isDisabled: true,
      isAnDayNhayTrong: false,
      thongTinTBTraCuu: {},
      thongTinGianDo: [],
      thongTinBaoHong: [],
      doRenderPDF: false,
      showPanelReport: false
    }
  },
  computed: {
    objToaDo: function () {
      return {
        diachi: '',
        toado: this.viDo_LD + ',' + this.kinhDo_LD
      }
    }
  },
  methods: {
    getDSThongTinThueBao: async function () {
      return await this.$root.context.post('/web-ecms/tracuu/dodancaptheosomayao', {so_may: this.soMay, loai_tra_cuu: this.loaiTimKiem})
    },
    getDSThongTinCap: async function (id, loai_tra_cuu, dau_cuoi_id) {
      return await this.$root.context.post('/web-ecms/tracuu/thongtincap', {id, loai_tra_cuu, dau_cuoi_id})
    },
    setThongTinThueBao: function (tttb) {
      this.thueBaoId = tttb.THUEBAO_ID
      this.hdtbId = tttb.HDTB_ID
      this.tenThueBao = tttb.TEN_TB
      this.loaiHinh = tttb.LOAIHINH_TB
      this.trangThai = tttb.TRANGTHAI_TB
      this.diaChi = tttb.DIACHI_LD
      this.toaDo = tttb.TOADO_LD
      this.toQL = tttb.TEN_TOQL
      this.tramTB = tttb.TEN_TRAMTBI
      this.ghichu = tttb.GHICHU
      this.isThueBao = tttb.IS_THUEBAO
      this.daucuoi_id = tttb.DAUCUOI_ID
      this.viDo_LD = tttb.VIDO_LD
      this.kinhDo_LD = tttb.KINHDO_LD
    },
    timKiemThueBao: async function () {
      this.soMay = this.soMay.trim()
      if (this.soMay) {
        try {
          this.loading(true)
          var rsThueBao = await this.getDSThongTinThueBao()
          if (rsThueBao.data.length > 0) {
            var tttb = rsThueBao.data[0]
            this.setThongTinThueBao(tttb)
            var rsCap = await this.getDSThongTinCap(this.isThueBao ? this.thueBaoId : this.hdtbId, this.loaiTimKiem, this.daucuoi_id)
            if (rsCap.data.TTCAP.length > 0) {
              var ttcap = rsCap.data.TTCAP[0]
              this.ketCuoi = ttcap.KYHIEU_KC
              this.cap = ttcap.KYHIEU_CAPGOC
              this.soi = ttcap.DOICAP
              this.vitri1 = ttcap.VITRI
              this.vitri2 = ttcap.VITRI_2
              this.culy = ttcap.CULY
              this.soDoTuyenCap = rsCap.data.TTSDCAP
              this.tt_capnhat = ttcap.TT_CN
              if (this.isAnDayNhayTrong) {
                this.tempSoDoTuyenCap = this.soDoTuyenCap.filter(x => x.KYHIEU != 'Dây nhảy trong' && x.LOAI_DT != 'Dây nhảy')
              } else {
                this.tempSoDoTuyenCap = this.soDoTuyenCap
              }
              this.getRowSpanArray(this.tempSoDoTuyenCap)
            } else {
              this.tempSoDoTuyenCap = []
              this.ketCuoi = null
              this.cap = null
              this.soi = null
              this.vitri1 = null
              this.vitri2 = null
              this.culy = null
              this.soDoTuyenCap = []
              this.tt_capnhat = null
            }
          } else {
            this.setThongTinThueBao({})
            this.tempSoDoTuyenCap = []
            this.ketCuoi = null
            this.cap = null
            this.soi = null
            this.vitri1 = null
            this.vitri2 = null
            this.culy = null
            this.soDoTuyenCap = []
            this.tt_capnhat = null
            this.$root.toastError('Không tìm thấy thông tin thuê bao')
          }
        } catch (error) {
          this.$toast.error('Xảy ra lỗi')
        } finally {
          this.loading(false)
        }
      }
    },
    closeModalLichSuCap: function () {
      this.modalShowLichSuCap = !this.modalShowLichSuCap
    },
    closeModalInLyLich: function () {
      this.modalShowInLyLich = !this.modalShowInLyLich
    },
    grid_selectedRowChanged: function () {

    },
    ModalChonThueBao_selectedRowChanged: function (dataItem) {
      this.setThongTinThueBao(dataItem)
    },
    showModalChonThueBao: async function () {
      // this.soMay = this.soMay.trim();
      // if(this.soMay) {
      //     try {
      //         this.loading(true);
      //         var rs = await this.getDSThongTinThueBao();
      //         this.dsThueBao = rs.data;
      //         this.$bvModal.show("modal-ChonThueBao");
      //     } catch (error) {
      //         this.$toast.error('Xảy ra lỗi');
      //     } finally {
      //         this.loading(false);
      //     }
      // }
      // else {
      //     this.$root.toastError('Chưa nhập thuê bao tìm kiếm');
      // }
      this.$bvModal.show('popupComponents')
    },
    chonThueBao: async function () {
      try {
        this.loading(true)
        var rsCap = await this.getDSThongTinCap(this.isThueBao ? this.thueBaoId : this.hdtbId, this.loaiTimKiem, this.daucuoi_id)
        if (rsCap.data.TTCAP.length > 0) {
          var ttcap = rsCap.data.TTCAP[0]
          this.ketCuoi = ttcap.KYHIEU_KC
          this.cap = ttcap.KYHIEU_CAPGOC
          this.soi = ttcap.DOICAP
          this.vitri1 = ttcap.VITRI
          this.vitri2 = ttcap.VITRI_2
          this.culy = ttcap.CULY
          this.soDoTuyenCap = rsCap.data.TTSDCAP
        }
      } catch (error) {
        this.$toast.error('Xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    nhapGhiChu: function () {
      if (this.thueBaoId || this.hdtbId) {
        if (this.isDisabled) {
          this.isDisabled = !this.isDisabled
        } else {
          if (this.ghichu) {
            try {
              this.loading(true)
              this.$root.context.post('/web-ecms/tracuu/capnhatghichu', {
                id: this.isThueBao ? this.thueBaoId : this.hdtbId,
                loai_tra_cuu: this.loaiTimKiem,
                ghichu: this.ghichu,
                dau_cuoi_id: this.daucuoi_id
              })
              this.$root.toastSuccess('Cập nhật ghi chú thành công')
            } catch (error) {
              this.$toast.error('Lỗi cập nhật ghi chú')
            } finally {
              this.loading(false)
            }
            this.isDisabled = !this.isDisabled
          } else {
            this.$toast.error('Bạn chưa nhập ghi chú')
          }
        }
      } else {
        this.$toast.error('Chưa chọn thuê bao')
      }
    },
    getLyLichDayMay: async function (thueBaoID) {
      let rs = await this.$root.context.post(
        '/web-cabman/tracuu/traCuuLyLichDayMay',
        {
          id: thueBaoID
        }
      )
      return rs.data
    },
    getThongTinBaoHong: async function (thueBaoID) {
      // thueBaoID = 1680477
      let rs = await this.$root.context.post(
        '/web-cabman/tracuu/traCuuLichSuBaoHongThueBao',
        {
          id: thueBaoID
        }
      )
      return rs.data
    },
    showReport: async function () {
      this.modalShowInLyLich = true
      try {
        this.loading(true)
        if (this.thueBaoId) {
          let rs = await this.getLyLichDayMay(this.thueBaoId)
          this.thongTinTBTraCuu = rs.ThongTinThueBao[0]
          this.thongTinGianDo = rs.DanDoCap

          let rsBaoHong = await this.getThongTinBaoHong(this.thueBaoId)
          this.thongTinBaoHong = rsBaoHong

          this.doRenderPDF = !this.doRenderPDF
          this.showPanelReport = true
        }
      } catch (error) {

      } finally {
        this.loading(false)
      }
    },
    traCuu: async function (inputThueBao, inputLoaiTimkiem) {
      try {
        this.loading(true)
        this.soMay = inputThueBao
        this.loaiTimKiem = inputLoaiTimkiem
        var rsThueBao = await this.getDSThongTinThueBao()
        if (rsThueBao.data.length > 0) {
          var tttb = rsThueBao.data[0]
          this.thueBaoId = tttb.THUEBAO_ID
          await this.showReport()
        } else {
          this.$root.toastError('Không tìm thấy thông tin thuê bao')
        }
      } catch (error) {

      } finally {
        this.loading(false)
      }
    },
    getToaDo: function (data) {
      this.toaDo = data
    },
    showMap: function () {
      this.$bvModal.show('popupToaDo')
    },
    capNhatToaDoThueBao: function () {
      this.$bvModal.msgBoxConfirm('Cập nhật tọa độ thuê bao vào danh bạ?', {
        size: 'sm',
        centered: true,
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy'
      })
        .then(async val => {
          if (val) {
            if (this.thueBaoId) {
              var lat
              var lng
              if (/^(\-?\d+(\.\d+)?),\s*(\-?\d+(\.\d+)?)$/.test(this.toaDo)) {
                var pair = this.toaDo.split(',')
                lat = pair[0]
                lng = pair[1]
              } else if (/^(\-?\d+(\.\d+)?);\s*(\-?\d+(\.\d+)?)$/.test(this.toaDo)) {
                var pair = this.toaDo.split(';')
                lat = pair[0]
                lng = pair[1]
              } else {
                this.$root.toastError('Cấu trúc tọa độ không đúng định dạng')
                return
              }
              var id = this.isThueBao ? this.thueBaoId : this.hdtbId
              try {
                this.loading(true)
                var rs = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/capnhat', {
                  kieu: this.isThueBao ? 0 : 1,
                  tb_id: id,
                  latitude: lat,
                  longtitude: lng,
                  update_tb_cung_cap: 1,
                  force_update: 1,
                  daucuoi_id: this.daucuoi_id
                })
                if (rs.data[0].KETQUA) {
                  this.viDo_LD = lat
                  this.kinhDo_LD = lng
                  this.$toast.success('Cập nhật tọa độ thuê bao thành công')
                } else {
                  this.$toast.error('Cập nhật tọa độ thuê bao thất bại')
                }
              } catch (error) {
                this.$toast.error('Cập nhật tọa độ thuê bao thất bại')
              } finally {
                this.loading(false)
              }
            } else {
              this.$root.toastError('Chưa nhập thuê bao tìm kiếm')
            }
          }
        })
        .catch(err => {
          this.$toast.error('Xảy ra lỗi')
        })
    },
    popupClosed: function (data) {
      if (data.ma_tb) {
        this.soMay = data.ma_tb
        this.$refs.ref_somay_input.focus()
        this.timKiemThueBao()
      }
      this.$bvModal.hide('popupComponents')
    },
    getRowSpanArray: function (array) {
      this.rowSpanningArray = []
      if (!array || array.length == 0) {
        return
      }
      let filterTenCha = array.filter(v => v.TEN_CHA != null && v.TEN_CHA != '')
      let value = filterTenCha.length > 0 ? filterTenCha[0].TEN_CHA : ''
      let stt = 1
      let count = 1
      for (let i = 1; i < filterTenCha.length; i++) {
        if (value != filterTenCha[i].TEN_CHA) {
          this.rowSpanningArray.push({'STT': stt, 'SIZE': count})
          stt = filterTenCha[i].STT
          value = filterTenCha[i].TEN_CHA
          count = 1
        } else {
          count++
        }
      }
      this.rowSpanningArray.push({'STT': stt, 'SIZE': count})
      console.log(this.rowSpanningArray)
    },
    queryCellInfo: function (args) {
      if (args.data.LOAI_DT == 'Cáp') {
        args.cell.style.fontWeight = 'bold'
      }
      if (args.column.field == 'TEN_CHA') {
        this.rowSpanningArray.forEach(element => {
          if (element.STT == args.data.STT) {
            args.rowSpan = element.SIZE
          }
        })
      }
    }
  },
  mounted: function () {
    try {
      this.loading(true)
      this.$refs.ref_somay_input.focus()
    } catch (error) {

    } finally {
      this.loading(false)
    }
    // this.tempSoDoTuyenCap = [
    //     {
    //         "STT": 1,
    //         "KIEU": 1,
    //         "KYHIEU": "S2-NAHA06Q02",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 5,
    //         "MAT": "Mặt sau",
    //         "TEN_CHA": "S2-NAHA06Q02 | Trong Ngõ 210 Cát Linh",
    //         "DUNGLUONG": 16,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 2,
    //         "KIEU": 1,
    //         "KYHIEU": "Dây nhảy trong",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Dây nhảy",
    //         "VITRI": null,
    //         "MAT": null,
    //         "TEN_CHA": "S2-NAHA06Q02 | Trong Ngõ 210 Cát Linh",
    //         "DUNGLUONG": null,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 3,
    //         "KIEU": 0,
    //         "KYHIEU": "S2-NAHA06Q02",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 1,
    //         "MAT": "Mặt trước",
    //         "TEN_CHA": "S2-NAHA06Q02 | Trong Ngõ 210 Cát Linh",
    //         "DUNGLUONG": 16,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 4,
    //         "KIEU": 0,
    //         "KYHIEU": "C-NAHA06Q01_Q02",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Cáp",
    //         "VITRI": 1,
    //         "MAT": null,
    //         "TEN_CHA": null,
    //         "DUNGLUONG": 8,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 5,
    //         "KIEU": 1,
    //         "KYHIEU": "S-NAHA06Q01",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 17,
    //         "MAT": "Mặt sau",
    //         "TEN_CHA": "S-NAHA06Q01 | Đầu Ngõ 210 Cát Linh",
    //         "DUNGLUONG": 19,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 6,
    //         "KIEU": 1,
    //         "KYHIEU": "Dây nhảy trong",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Dây nhảy",
    //         "VITRI": null,
    //         "MAT": null,
    //         "TEN_CHA": "S-NAHA06Q01 | Đầu Ngõ 210 Cát Linh",
    //         "DUNGLUONG": null,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 7,
    //         "KIEU": 0,
    //         "KYHIEU": "S-NAHA06Q01",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 1,
    //         "MAT": "Mặt trước",
    //         "TEN_CHA": "S-NAHA06Q01 | Đầu Ngõ 210 Cát Linh",
    //         "DUNGLUONG": 19,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 8,
    //         "KIEU": 0,
    //         "KYHIEU": "C-NAHA06P01_Q01",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Cáp",
    //         "VITRI": 1,
    //         "MAT": null,
    //         "TEN_CHA": null,
    //         "DUNGLUONG": 1,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 9,
    //         "KIEU": 1,
    //         "KYHIEU": "S-NAHA06P01",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 20,
    //         "MAT": "Mặt sau",
    //         "TEN_CHA": "S-NAHA06P01 | Đầu Ngõ 200 Cát Linh",
    //         "DUNGLUONG": 22,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 10,
    //         "KIEU": 1,
    //         "KYHIEU": "Dây nhảy trong",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Dây nhảy",
    //         "VITRI": null,
    //         "MAT": null,
    //         "TEN_CHA": "S-NAHA06P01 | Đầu Ngõ 200 Cát Linh",
    //         "DUNGLUONG": null,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 11,
    //         "KIEU": 0,
    //         "KYHIEU": "S-NAHA06P01",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 20,
    //         "MAT": "Mặt trước",
    //         "TEN_CHA": "S-NAHA06P01 | Đầu Ngõ 200 Cát Linh",
    //         "DUNGLUONG": 22,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 12,
    //         "KIEU": 0,
    //         "KYHIEU": "C-NAHA06X01_P01",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Cáp",
    //         "VITRI": 2,
    //         "MAT": null,
    //         "TEN_CHA": null,
    //         "DUNGLUONG": 2,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 13,
    //         "KIEU": 1,
    //         "KYHIEU": "S-NAHA06X01",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 22,
    //         "MAT": "Mặt sau",
    //         "TEN_CHA": "S-NAHA06X01 | Sn 25 Cát Linh- Ngã 3 Chùa",
    //         "DUNGLUONG": 24,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 14,
    //         "KIEU": 1,
    //         "KYHIEU": "Dây nhảy trong",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Dây nhảy",
    //         "VITRI": null,
    //         "MAT": null,
    //         "TEN_CHA": "S-NAHA06X01 | Sn 25 Cát Linh- Ngã 3 Chùa",
    //         "DUNGLUONG": null,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 15,
    //         "KIEU": 0,
    //         "KYHIEU": "S-NAHA06X01",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 22,
    //         "MAT": "Mặt trước",
    //         "TEN_CHA": "S-NAHA06X01 | Sn 25 Cát Linh- Ngã 3 Chùa",
    //         "DUNGLUONG": 24,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 16,
    //         "KIEU": 0,
    //         "KYHIEU": "C-NAHA06N03_X01",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Cáp",
    //         "VITRI": 2,
    //         "MAT": null,
    //         "TEN_CHA": null,
    //         "DUNGLUONG": 8,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 17,
    //         "KIEU": 1,
    //         "KYHIEU": "S2-NAHA06N03",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 22,
    //         "MAT": "Mặt sau",
    //         "TEN_CHA": "S2-NAHA06N03 | Đầu Ngõ 104 Cát Linh",
    //         "DUNGLUONG": 24,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 18,
    //         "KIEU": 1,
    //         "KYHIEU": "Dây nhảy trong",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Dây nhảy",
    //         "VITRI": null,
    //         "MAT": null,
    //         "TEN_CHA": "S2-NAHA06N03 | Đầu Ngõ 104 Cát Linh",
    //         "DUNGLUONG": null,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 19,
    //         "KIEU": 0,
    //         "KYHIEU": "S2-NAHA06N03",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Splitter",
    //         "VITRI": 22,
    //         "MAT": "Mặt trước",
    //         "TEN_CHA": "S2-NAHA06N03 | Đầu Ngõ 104 Cát Linh",
    //         "DUNGLUONG": 24,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 20,
    //         "KIEU": 0,
    //         "KYHIEU": "C-NAHA06_N03",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Cáp",
    //         "VITRI": 2,
    //         "MAT": null,
    //         "TEN_CHA": null,
    //         "DUNGLUONG": 8,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 21,
    //         "KIEU": 1,
    //         "KYHIEU": "O-NAHA06",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Khay ODF",
    //         "VITRI": 98,
    //         "MAT": "Mặt sau",
    //         "TEN_CHA": "O-NAHA06 | ODF 96fo Cầu Cát Linh - Tràng Cát Trong TĐ Nam Hải",
    //         "DUNGLUONG": 107,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 22,
    //         "KIEU": 1,
    //         "KYHIEU": "Dây nhảy trong",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Dây nhảy",
    //         "VITRI": null,
    //         "MAT": null,
    //         "TEN_CHA": "O-NAHA06 | ODF 96fo Cầu Cát Linh - Tràng Cát Trong TĐ Nam Hải",
    //         "DUNGLUONG": null,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 23,
    //         "KIEU": 0,
    //         "KYHIEU": "O-NAHA06",
    //         "LOAITBI_ID": 25,
    //         "LOAI_DT": "Khay ODF",
    //         "VITRI": 98,
    //         "MAT": "Mặt trước",
    //         "TEN_CHA": "O-NAHA06 | ODF 96fo Cầu Cát Linh - Tràng Cát Trong TĐ Nam Hải",
    //         "DUNGLUONG": 107,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 24,
    //         "KIEU": 0,
    //         "KYHIEU": "N-NAHA06",
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": "Cáp",
    //         "VITRI": 98,
    //         "MAT": null,
    //         "TEN_CHA": null,
    //         "DUNGLUONG": 120,
    //         "VONG": 0
    //     },
    //     {
    //         "STT": 25,
    //         "KIEU": 0,
    //         "KYHIEU": null,
    //         "LOAITBI_ID": null,
    //         "LOAI_DT": null,
    //         "VITRI": null,
    //         "MAT": "Mặt sau",
    //         "TEN_CHA": null,
    //         "DUNGLUONG": null,
    //         "VONG": 0
    //     }
    // ]
    // this.getRowSpanArray(this.tempSoDoTuyenCap);
  },
  watch: {
    isAnDayNhayTrong: function (newVal) {
      if (newVal) {
        this.tempSoDoTuyenCap = this.soDoTuyenCap.filter(x => x.KYHIEU != 'Dây nhảy trong' && x.LOAI_DT != 'Dây nhảy')
      } else {
        this.tempSoDoTuyenCap = this.soDoTuyenCap
      }
      this.getRowSpanArray(this.tempSoDoTuyenCap)
    }
  }
}
</script>

<style scoped>
.check-action .check_xacnhan {
    position: relative;
    display: block;
    padding-left: 22px;
    min-height: 20px;
    background: #0d6efd;
}

.check-action .check_xacnhan:hover {
    cursor: pointer;
    opacity: 0.7;
}

.check-action .check_xacnhan::before {
    content: '';
    left: 6px;
    position: absolute;
    border-width: 0 2px 2px 0;
    border-color: white;
    border-style: solid;
    width: 10px;
    height: 16px;
    transform: rotate(45deg);
}
</style>
<style>
.dandocap-theothuebao .page-content {
  position: unset !important;
}
</style>
