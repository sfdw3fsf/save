<template src="./index.template.html"></template>

<script>
import GianDo from './GianDoV2'
import ModalTreocap from './ChuiCapQuaOngCong/Modal'
import ModalPhanChiaDoDai from './PhanChiaDoDaiDoanCongChenKetCuoi/Modal'
import ModalQLBe from './QLBe/Modal'
import ModalQLDoanCong from './QLDoanCong/Modal'
import ModalQLDoanCongVaOngCong from './QLDoanCongVaCacOngCongNamTrongDoanCong/Modal'
import ModalDsBe from './DanhSachBe/Modal'
import ModalDsTuyenCB from './DanhSachTuyenCongBe/Modal'
import { GridPlugin, Page, Filter, CommandColumn, Resize } from '@syncfusion/ej2-vue-grids'
import MapControl from './MapControl'
import DiagramLib from '../../../utils/DiagramLib'
import { Diagram } from '@syncfusion/ej2-diagrams'
import BanDo from './BanDo'
import ModalTracnghiemExt from '../../CABMAN/BanDoMangCap/modal/TracNghiemExt'
import EventBus from '@/utils/eventBus'
import TuyenCongBe from '../TuyenCongBe'
import { debounce } from './debounce'
export default {
  provide: {
    grid: [Filter, Page, CommandColumn, Resize]
  },

  components: { GridPlugin, ModalPhanChiaDoDai, ModalTracnghiemExt, ModalTreocap, ModalDsTuyenCB, ModalQLBe, ModalQLDoanCong, ModalQLDoanCongVaOngCong, ModalDsBe, MapControl, GianDo, DiagramLib, BanDo, TuyenCongBe },

  data: function() {
    return {
      objNodeId: null,
      debouncedtxtTimKiem: '',
      isLoadingTimKiem: false,
      dsDvTheoNhanVien: [],
      tuyenCongBeId: null,
      isTaoDoanCong: false,
      showDataSearch: false,
      dsTTVT: [],
      dsToQL: [],
      dsTuyenCB: [],
      dsDataTem: [],
      dataSelected: {
        ttvt: 0,
        toql: 0,
        timKiem: 1
      },
      dsBeCap: [],
      dsDoanCong: [],
      dsBeChuaCoToaDo: [],
      dsBeToaDo: [],
      dsSearch: [],
      cboTimKiem: [
        { id: 1, name: 'Cống/Bể' },
        { id: 5, name: 'Bể' },
        { id: 6, name: 'Cống' }
      ],
      dataSend: {},
      isSuccess: true,
      tuyenCongId: null,
      tuyenCongIdDoi: null,
      beId: null,
      doanCongId: null,
      doanCongIdThaoTac: null,
      beIdThaoTac: null,
      dataBeThaoTac: {},
      beMoiId: null,
      beSuaId: null,
      showBeChuaTD: false,
      txttimkiem: '',
      selected: false,
      url: '#/cabman/tuyen-cong-be?id=',
      countAction: 0,
      customAttributes: { class: 'table' },
      isSearch: 0,
      isClose: false,
      ok: false,
      loaiBeDoi: 1, // 1:A,2:B
      loaiThaoTac: null, // 1:doi be, 2:tao cong
      stateBtn: {
        isDisableBtnTaoCot: true
      },
      commandTc: [
        {
          type: 'SuaTC',
          buttonOption: {
            content: '...',
            cssClass: 'btn btn-second pad3 lh14 button-not-bg'
          }
        }
      ],
      customAttributes: { class: 'customcss' },
      search: {
        ma_tc: '',
        sl_cot: '',
        ten_tc: ''
      },
      stateBtn: {
        isDisableBtnTaoBe: true
      },
      commands: [{ type: 'SuaBeCap', buttonOption: { content: '...', cssClass: 'btn btn-second pad3 lh14 button-not-bg' } }],
      commands1: [{ type: 'GhiToaDo', buttonOption: { content: '>', cssClass: 'btn btn-second pad3 lh14 button-not-bg' } }],
      // #region "Giản đồ"
      dsNodes: [],
      dsConnectors: [],
      gianDoRefresh: false,
      beChenId: null,
      zoomFactor: 1
      // #endregion
    }
  },

  created: async function() {
    await this.getTTVT()
  },

  mounted() {},

  watch: {
    debouncedtxtTimKiem(val) {
      let txttimkiem = val.trim()
      if (txttimkiem.length > 3) {
        var typeList = '5,6'
        if (this.dataSelected.timKiem !== 1) {
          typeList = this.dataSelected.timKiem.toString()
        }
        this.queryDT(txttimkiem, typeList)
      }
    },
    txttimkiem: debounce(function(newVal) {
      this.debouncedtxtTimKiem = newVal
      if (newVal != '') {
        this.showDataSearch = ''
      }
    }, 500),
    tuyenCongId: function(newvalue, oldvalue) {
      if (newvalue != null) {
        this.setDisableBtn(false)
        this.dataSend.tuyenCongId = newvalue
      } else {
        this.setDisableBtn(true)
      }
    },
    dsBeCap: function(value) {
      this.veGianDo()
    }
  },

  computed: {},

  methods: {
    queryDT: async function(txttimkiem, typeList) {
      try {
        this.isLoadingTimKiem = true
        this.showDataSearch = !this.showDataSearch
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/goiNhacTimKiem', { searchQuery: txttimkiem.toLowerCase(), types: typeList })
        this.dsSearch = rs.data.filter((e) => e.IS_EXACTLY === 0)
        console.log('🚀 ~ file: index.vue:148 ~ this.dsSearch:', this.dsSearch)
        this.showDataSearch = !this.showDataSearch
      } catch (error) {
        console.log('🚀 ~ file: index.vue:474 ~ error:', error)
        this.dsSearch = []
      } finally {
        this.showDataSearch = !this.showDataSearch
        this.isLoadingTimKiem = false
      }
    },
    keyDown: function(event) {
      if (this.isTaoDoanCong) {
        if (event.key == 'Escape') {
          this.isTaoDoanCong = false
          this.beIdThaoTac = null
          this.$toast.success('Đã hủy thao tác tạo cống')
        }
      }
    },
    zoomIn: function() {
      this.$refs.giando.ZoomIn()
    },
    zoomOut: function() {
      this.$refs.giando.ZoomOut()
    },
    zoomReset: function() {
      this.$refs.giando.ZoomReset()
    },
    setDisableBtn: function(isTaoBe) {
      this.stateBtn.isDisableBtnTaoBe = isTaoBe
    },

    getTTVT: async function() {
      try {
        var rs = await this.$root.context.get('/web-cabman/BanDoTuyenCong/ds-ttvt-theo-nhanvien')
        this.dsTTVT = rs.data
        if (this.dsTTVT.length > 0) {
          this.dataSelected.ttvt = this.dsTTVT[0].DONVI_ID
          await this.onChangeTTVT()
        }
      } catch (error) {
      } finally {
      }
    },

    getToQL: async function() {
      try {
        var rs = await this.$root.context.get('/web-cabman/BanDoTuyenCong/ds-to-kythuat-theo-nhanvien/' + this.dataSelected.ttvt)
        this.dsToQL = rs.data
        if (this.dsToQL.length > 0) {
          this.dataSelected.toql = this.dsToQL[0].DONVI_ID
          await this.onChangeToQL()
          this.dataSend.dvql = {
            id: this.dsToQL[0].DONVI_ID,
            name: this.dsToQL[0].TEN_DV
          }
          console.log('🚀 ~ file: index.vue:264 ~ this.dataSend.dvql:', this.dataSend.dvql)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:209 ~ error:', error)
      } finally {
      }
    },

    getdsTuyenCong: async function() {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get('/web-cabman/BanDoTuyenCong/ds-tuyen-cong/' + this.dataSelected.toql)
        this.dsTuyenCB = rs.data
        if (this.dsTuyenCB != null && this.dsTuyenCB.length > 0) {
          this.tuyenCongId = this.dsTuyenCB[0].TUYENCB_ID
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    commandClick: function(name, args) {
      console.log(args)
      if (name === 'SuaBe') {
        this.dataSend.beCapId = args.BECAP_ID
        this.$bvModal.show('popupQLBe')
      }
      if (name == 'GhiToaDo') {
        this.$root.toastError('Nhấn chuột trái lên vị trí bản đồ để ghim tọa độ bể.')
        EventBus.$emit('ghiToaDo', args)
      }
    },

    commandTCClick: function(name, args) {
      if (name == 'SuaTC') {
        this.tuyenCongBeId = args.TUYENCB_ID
        this.$bvModal.show('modal-TuyenCongBe')
      }
    },
    modalTuyenCongBe_Show: async function() {
      await this.$refs.tuyenCongBe.initData(this.tuyenCongBeId)
    },
    modalTuyenCongBe_Ok: function() {
      this.$bvModal.hide('modal-TuyenCongBe')
    },
    tcDoubleClick: async function(rowIndex, dataItem) {
      this.tuyenCongId = dataItem.TUYENCB_ID
      this.isClose = true
      await this.getGianDoCong()
    },

    onChangeTTVT: async function() {
      await this.getToQL()
    },

    onChangeToQL: async function() {
      try {
        await this.getdsTuyenCong()
      } catch (error) {
        console.log('🚀 ~ file: index.vue:268 ~ dataSend.dvql error:', error)
      }
    },

    onChangeCboTimKiem: async function() {},

    toQLSelectEvent: function(event) {
      console.log('🚀 ~ file: index.vue:269 ~ event:', event)
      this.dataSend.dvql = {
        id: event.id,
        name: event.text
      }
    },

    getGianDoCong: async function() {
      this.$root.showLoading(true)
      try {
        let rs = await this.$root.context.get('/web-cabman/BanDoTuyenCong/gian-do-cong/' + this.tuyenCongId)
        rs.data.dsBeCap.sort(function(a, b) {
          return a.BECAP_ID < b.BECAP_ID ? -1 : 1
        })
        this.dsBeCap = rs.data.dsBeCap
        this.dsBeChuaCoToaDo = this.dsBeCap.filter((item) => item.TUYENKHAC == 0 && (item.TOADO == null || item.TOADO == '0,0'))
        this.dsBeToaDo = this.dsBeCap.filter((item) => item.TOADO != null && item.TOADO != '0,0')
        this.dsBeCap = rs.data.dsBeCap
        this.dsDoanCong = rs.data.dsDoanCong
        this.dsSearch = []
        this.dataSelected.timKiem = 1
        this.sapXepGianDo()
        this.refreshGianDo()
        this.veGianDo()
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    print() {
      if (!this.showcotChuaTD) {
        let diagramInstance = Diagram
        let diagramObj = document.getElementById('diagram')
        diagramInstance = diagramObj.ej2_instances[0]
        let printOptions = {}
        printOptions.mode = 'Data'
        printOptions.region = 'PageSettings'
        printOptions.multiplePage = true
        printOptions.pageHeight = 400
        printOptions.pageWidth = 400
        diagramInstance.print(printOptions)
      } else {
        let map = document.getElementById('container')
        map.ej2_instances[0].print()
      }
    },

    Refesh: function() {
      this.$confirm(`Bạn có chắc muốn nạp lại giản đồ/bản đồ?`, 'Giản đồ tuyến cống', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      })
        .then(async () => {
          try {
            this.$root.showLoading(true)
            if (!this.showcotChuaTD) {
              this.refreshGianDo()
              await this.getGianDoCong()
            } else {
              await this.getGianDoCong()
            }
          } catch (er) {
          } finally {
            this.$root.showLoading(false)
          }
        })
        .catch(() => {})
    },

    xacnhanBe: function(obj) {
      console.log('xacnhanbe', obj)
      this.dataSend.beCapId = null
      if (obj != null) {
        this.beIdThaoTac = obj.beId
        this.getGianDoCong()
        if (this.loaiThaoTac == 3) {
          this.chenBeTaoMoi(obj)
        }
        if (obj.type == 'MENU_ITEM_THUOC_TINH') {
          this.dataSend.beCapId = obj.beId
          this.$bvModal.show('popupQLBe')
        }
      }
    },
    _showBeChuaTD(value) {
      this.showBeChuaTD = value
      this.$refs.bando.$refs.map.updateSized()
    },
    ctrlLuuToaDoBanDo() {
      this.$refs.bando.$refs.map.ctrlLuuToaDoBanDo()
    },
    ctrlDuyChuyenDoiTuong() {
      this.$refs.bando.$refs.map.ctrlDuyChuyenDoiTuong()
    },
    ctrlThayDoiNguonDuLieu() {
      this.$refs.bando.$refs.map.ctrlThayDoiNguonDuLieu()
    },
    thayDoiNguonDuLieu_changed: function(args) {
      this.napLaiGianDo()
    },
    xacnhanCong: function(obj) {
      if (obj != null) {
        this.doanCongIdThaoTac = obj.doanCongId
        if (obj.type == 'MENU_ITEM_XOA_CONG') {
        }

        if (obj.type == 'MENU_ITEM_DOI_BE_A') {
          this.loaiBeDoi = 1
          this.loaiThaoTac = 1
        }

        if (obj.type == 'MENU_ITEM_DOI_BE_B') {
          this.loaiBeDoi = 2
          this.loaiThaoTac = 1
        }
      }
    },

    taoDoanCongTuIcon: function() {
      this.isTaoDoanCong = true
    },

    taoDoanCong: function(obj) {
      try {
        if (obj != null) {
          if (obj.type == 'MENU_ITEM_LK_BE_TT_HUY') {
            this.isTaoDoanCong = false
            this.dataBeThaoTac = null
          }

          if (obj.type == 'MENU_ITEM_LK_BE_TT') {
            this.dataBeThaoTac = obj.dataBe
          }

          if (obj.type == 'MENU_ITEM_LK_BE_TK') {
            this.loaiThaoTac = 2
            let tracnghiem = {
              title: 'Chọn chiều ',
              caption: 'Chọn chiều đi của đoạn cống muốn tạo',
              options: ['Đi từ bể hiện tại sang 1 bể thuộc tuyến khác', 'Đi từ bể thuộc tuyến khác sang bể hiện tại'],
              data: obj.dataBe
            }
            this.$refs.ModalTracnghiemExt.show(tracnghiem).then((result) => {
              if (result.ok) {
                console.log('Tạo cống', result)
                if (result.option === 0) {
                  this.dataSend.dataBeA = obj.dataBe
                  this.loaiBeDoi = 1
                } else {
                  this.dataSend.dataBeB = obj.dataBe
                  this.loaiBeDoi = 2
                }
                this.$bvModal.show('popupDsBe')
              } else {
                console.log('Cancel')
              }
            })
          }

          if (obj.type == 'MENU_ITEM_LK_BE_TT_CHON') {
            this.isTaoDoanCong = false
            this.dataSend.dataBeB = obj.dataBe
            this.dataSend.dataBeA = this.dataBeThaoTac
            this.dataSend.doanCongId = null
            console.log('🚀 ~ file: index.vue:431 ~ this.dataSend:', this.dataSend)
            this.$bvModal.show('popupQLDoanCong')
          }
        }
      } catch (e) {
        console.log(e)
        this.$toast.error('lỗi bất thường')
      }
    },

    chenBe: function(obj) {
      try {
        if (obj != null) {
          if (obj.type == 'MENU_ITEM_THEM_BE') {
            this.doanCongId = obj.doanCongId
            this.loaiThaoTac = 3
            let tracnghiem = {
              title: 'Chèn bể cáp',
              caption: 'Bước 1: Chọn bể muốn chèn',
              options: ['Chọn bể có sẵn', 'Tạo bể mới'],
              data: obj.dataBe
            }
            this.$refs.ModalTracnghiemExt.show(tracnghiem).then((result) => {
              if (result.ok) {
                this.loaiThaoTac = 3
                if (result.option === 0) {
                  this.$bvModal.show('popupDsBe')
                } else {
                  this.dataSelected.beCapId = null
                  this.$bvModal.show('popupQLBe')
                }
              } else {
                console.log('Cancel')
              }
            })
          }

          if (obj.type == 'MENU_ITEM_LK_BE_TT_CHON') {
            this.dataSend.doanCongId = null
            this.dataSend.dataBeB = obj.dataBe
            this.dataSend.dataBeA = this.dataBeThaoTac
            console.log('🚀 ~ file: index.vue:472 ~ this.dataSend:', this.dataSend)
            this.$bvModal.show('popupQLDoanCong')
          }
        }
      } catch (e) {
        console.log(e)
        this.$toast.error('lỗi bất thường')
      }
    },

    napLaiGianDo: function() {
      this.getGianDoCong()
    },

    xacNhanDoanCong: function(obj) {
      if (obj != null) {
        if (obj.type == 'MENU_ITEM_THUOC_TINH_DC') {
          this.dataSend.doanCongId = obj.doanCongId
          this.$bvModal.show('popupQLDoanCongVaOngCong')
        }

        if (obj.type == 'MENU_ITEM_LK_DAOCHIEU') {
          this.doanCongIdThaoTac = obj.doanCongId
          this.daoChieuDoanCong()
        }
      }
    },

    xoaCong: async function() {
      try {
        if (this.doanCongIdThaoTac != null) {
          let rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/xoa-doan-cong', {
            doanCongId: this.doanCongIdThaoTac
          })
          if (rs.error_code === 'BSS-00000000') {
            this.$toast.success('Xóa cống: ' + this.doanCongIdThaoTac + ' thành công')
            if (!this.showBeChuaTD) {
              this.getGianDoCong()
            }
          } else {
            this.$toast.error('Xóa cống: ' + this.doanCongIdThaoTac + ' thất bại')
          }
        }
      } catch (error) {
        this.$toast.error('Xóa cống: ' + this.doanCongIdThaoTac + ' thất bại')
      } finally {
      }
    },

    xoaBe: async function() {
      try {
        if (this.beIdThaoTac != null) {
          let rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/xoa-be-cap', {
            beCapId: this.beIdThaoTac
          })
          if (rs.error_code === 'BSS-00000000') {
            this.$toast.success('Xóa bể thành công')
            if (!this.showBeChuaTD) {
              this.getGianDoCong()
            }
          } else {
            this.$toast.error('Xóa bể thất bại')
          }
        }
      } catch (error) {
        this.$toast.error('Xóa bể thất bại')
      } finally {
      }
    },

    thayDoiBe: function(data) {
      this.beIdThaoTac = data.BECAP_ID
      if (this.loaiBeDoi == 1) {
        this.doiBeCapA()
      }

      if (this.loaiBeDoi == 2) {
        this.doiBeCapB()
      }
    },

    xacNhanDsBe: function(data) {
      switch (this.loaiThaoTac) {
        case 1:
          this.thayDoiBe(data)
          break
        case 2:
          this.taoCongDenBeKhactuyen(data)
          break
        case 3:
          this.chenBeCoSan(data)
          break
      }
    },

    chenBeTaoMoi: function(data) {
      this.dataSend.beCapId = data.beId
      this.dataSend.chieuDai = this.dsDoanCong.find((item) => item.DOANCONG_ID == this.doanCongId).CHIEUDAI
      this.dataSend.doanCongId = this.doanCongId
      this.$bvModal.show('popupPhanChiaDoDai')
    },

    chenBeCoSan: function(data) {
      this.dataSend.beCapId = data.BECAP_ID
      this.dataSend.chieuDai = this.dsDoanCong.find((item) => item.DOANCONG_ID == this.doanCongId).CHIEUDAI
      this.dataSend.doanCongId = this.doanCongId
      this.$bvModal.show('popupPhanChiaDoDai')
    },

    taoCongDenBeKhactuyen: function(data) {
      this.dataSend.doanCongId = null
      if (this.loaiBeDoi == 1) {
        this.dataSend.dataBeB = data
      } else {
        this.dataSend.dataBeA = data
      }
      console.log('🚀 ~ file: index.vue:592 ~ this.dataSend:', this.dataSend)
      this.$bvModal.show('popupQLDoanCong')
    },

    thayDoiTuyen: function(data) {
      this.tuyenCongIdDoi = data.TUYENCB_ID
      this.chuyenTuyenBeCap()
    },

    daoChieuDoanCong: async function() {
      try {
        let rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/doa-chieu-doan-cong/', {
          doanCongId: this.doanCongIdThaoTac
        })
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Đảo chiều thành công')
          if (!this.showBeChuaTD) {
            this.getGianDoCong()
          }
        } else {
          this.$toast.error('Đảo chiều thất bại')
        }
      } catch (error) {
      } finally {
      }
    },

    doiBeCapA: async function() {
      try {
        let rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/thay-doi-be-cap-a/', {
          beCapId: this.beIdThaoTac,
          doanCongId: this.doanCongIdThaoTac
        })
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('đổi bể cáp a thành công')
          if (!this.showBeChuaTD) {
            this.getGianDoCong()
          }
        } else {
          this.$toast.error('đổi bể cáp a thất bại')
        }
      } catch (error) {
      } finally {
      }
    },

    doiBeCapB: async function() {
      try {
        let rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/thay-doi-be-cap-b/', {
          beCapId: this.beIdThaoTac,
          doanCongId: this.doanCongIdThaoTac
        })
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('đổi bể cáp b thành công')
          if (!this.showBeChuaTD) {
            this.getGianDoCong()
          }
        } else {
          this.$toast.error('đổi bể cáp b thất bại')
        }
      } catch (error) {
      } finally {
      }
    },

    chuyenTuyenBeCap: async function() {
      try {
        var rs = await this.$root.context.post('/web-cabman/BanDoTuyenCong/chuyen-tuyen-be-cap/', {
          beCapId: this.beIdThaoTac,
          tuyenCongBeId: this.tuyenCongIdDoi
        })
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('chuyển tuyến bể cáp thành công')
          if (!this.showBeChuaTD) {
            this.getGianDoCong()
          }
        } else {
          this.$toast.error('chuyển tuyến bể cáp thất bại')
        }
      } catch (error) {
      } finally {
      }
    },
    async setTxtTimKiem(value, searchquery, loai) {
      console.log('🚀 ~ file: index.vue:715 ~ setTxtTimKiem ~ value, searchquery, loai:', value, searchquery, loai)
      try {
        this.txttimkiem = value
        await this.TIMKIEM_DOITUONG(searchquery, loai)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:719 ~ setTxtTimKiem ~ error:', error)
      }
    },
    getCongBe: async function(data) {
      this.beId = null
      this.doanCongId = null
      this.beId = data.BECAP_ID != null ? data.BECAP_ID : this.beId
      this.doanCongId = data.DOANCONG_ID != null ? data.DOANCONG_ID : this.doanCongId
      if (this.doanCongId != null && this.beId == null) {
        this.beId = this.dsDoanCong.find((x) => x.DOANCONG_ID == this.doanCongId).BECAP_A_ID
      }
      this.txttimkiem = data.KYHIEU
      this.showDataSearch = false
      this.$refs.giando.selectedBe(this.beId)
    },

    // #region "Hàm Giãn đồ"
    sapXepGianDo: function() {
      try {
        let len = this.dsDoanCong.length
        let level = 0
        for (let i = 0; i < len; i++) {
          for (let j = i + 1; j < len; j++) {
            if (this.dsDoanCong[j].BECAP_B_ID === this.dsDoanCong[i].BECAP_A_ID && this.dsDoanCong[j].BECAP_A_ID !== this.dsDoanCong[i].BECAP_B_ID) {
              this.dsDoanCong.splice(i, 0, this.dsDoanCong[j])
              this.dsDoanCong.splice(j + 1, 1)
            }
          }
        }
        for (let i = 0; i < len; i++) {
          this.dsDoanCong[i].level = level
          this.dsDoanCong[i].diemDau = 0
          if (i < len - 1) {
            if (this.dsDoanCong[i].BECAP_B_ID !== this.dsDoanCong[i + 1].BECAP_A_ID) {
              level++
              this.dsDoanCong[i].diemDau = 1
            }
          }
        }
      } catch (e) {
        console.log('🚀 ~ file: index.vue:709 ~ e:', e)
      }
    },
    veGianDo: function() {
      try {
        let nodes = []
        let connectors = []
        let slDoanCong = this.dsDoanCong.length
        let index = 0
        let dem = 0
        for (let i = 0; i < slDoanCong; i++) {
          let nodeN
          let nodeD
          let doanCong = this.dsDoanCong[i]
          let beCapA = this.dsBeCap.filter((x) => x.BECAP_ID === doanCong.BECAP_A_ID)
          let beCapB = this.dsBeCap.filter((x) => x.BECAP_ID === doanCong.BECAP_B_ID)

          beCapA[0].level = doanCong.level
          beCapB[0].level = doanCong.level

          nodeN = this.TaoDuLieuNode(beCapA[0], false, dem++, 0)
          let checkN = nodes.find((x) => x.id === nodeN.id)
          if (checkN == null) {
            nodes.push(nodeN)
          }
          nodeD = this.TaoDuLieuNode(beCapB[0], false, dem++, 0)
          let checkD = nodes.find((x) => x.id === nodeD.id)
          if (checkD == null) {
            nodes.push(nodeD)
          }
          if (beCapA == null || beCapB == null) {
            continue
          }
          let _doanCong = this.taoKetNoi('DC_' + doanCong.DOANCONG_ID.toString(), nodeN, nodeD, doanCong.TEN_DC, nodeN.ports[1].id, nodeD.ports[0].id)
          // fix 2 doan cong noi 2 be gan nhau
          let f = connectors.filter((x) => x.targetPortID === _doanCong.targetPortID)
          if (f && f.length > 0) {
            let _targetPortID = f[0].targetPortID
            // let _e = _targetPortID.charAt(_targetPortID.length - 1)
            // let _f = Number(_e) + 1
            _targetPortID = _targetPortID.substring(0, _targetPortID.length - 1) + '3'
            console.log('🚀 ~ file: index.vue:762 ~ _targetPortID:', _targetPortID)
            _doanCong.targetPortID = _targetPortID
          }
          connectors.push(_doanCong)
        }
        let dsChuaLienKet = this.dsBeCap.filter((x) => x.DOANCONG_ID == null)
        dsChuaLienKet.forEach((item) => {
          nodes.push(this.TaoDuLieuNode(item, true, index++, 0))
        })
        this.dsNodes = nodes
        console.log('🚀 ~ file: index.vue:760 ~ connectors:', connectors)
        this.dsConnectors = connectors
      } catch (err) {
        console.log('🚀 ~ file: index.vue:821 ~ err:', err)
      }
    },
    taoNode: function(nodeID, text, stringStyle) {
      if (text == null) text = 'null'
      text = '<div class="label-box"><span style="font-size: 12px; white-space: nowrap; width: 160px;">' + text + '</span>' +'<div class="tooltip">'+ text+ '</div></div>'
      let ports = []
      ports.push(DiagramLib.taoPort(nodeID + '_port_1', DiagramLib.PORT_TRAI))
      ports.push(DiagramLib.taoPort(nodeID + '_port_2', DiagramLib.PORT_PHAI))
      ports.push(DiagramLib.taoPort(nodeID + '_port_3', DiagramLib.PORT_TREN))
      ports.push(DiagramLib.taoPort(nodeID + '_port_4', DiagramLib.PORT_DUOI))
      let annotations = []
      annotations.push({ template: text, style: { textWrapping: 'NoWrap' } })
      let node = DiagramLib.taoNodeTheoStyle(stringStyle, nodeID, ports, annotations)
      return node
    },
    taoKetNoi: function(id, nodeSource, nodeTarget, text, portN, portD) {
      let sourceID = nodeSource.id
      let sourcePortID = portN
      let targetID = nodeTarget.id
      let targetPortID = portD
      let annotations = [
        {
          height: 20,
          width: 50,
          content: text,
          margin: { top: 20 },
          style: {
            textWrapping: 'NoWrap',
            fill: '#F5F5F5',
            strokeWidth: 1,
            strokeColor: '#E0E0E0'
          }
        }
      ]
      let ketNoi = DiagramLib.taoKetNoiTheoStyle('0-0-4-0', id, sourceID, targetID, sourcePortID, targetPortID, annotations)
      ketNoi.targetDecorator = {
        shape: 'None'
      }
      ketNoi.type = 'Orthogonal'
      return ketNoi
    },
    refreshGianDo: function() {
      this.gianDoRefresh = !this.gianDoRefresh
    },
    TaoDuLieuNode(data, khongLienKet, i, count) {
      try {
        let BECAP_ID = 'BC_' + data.BECAP_ID.toString()
        let icon = data.ICON_STYLE
        let ten = `<b class="text-ellipsis">${data.KYHIEU}</b><br><div class="text-ellipsis">${data.DIACHI}</div>` // data.TEN_BC
        let level = data.level
        let node = this.taoNode(BECAP_ID, ten, icon, data)
        if (!khongLienKet) {
          node.offsetX = 300 * (i + 1)
          node.offsetY = 250 + level * 150
        } else {
          node.offsetX = 300 * (i + 1)
          node.offsetY = 150
        }
        node.dataBe = data
        return node
      } catch (e) {
        console.log(e)
        this.$toast.error('lỗi bất thường')
      }
    },
    onGianDoDoubleClick(args) {
      if (args != null && args.id != null) {
        let idArr = args.id.split('_')
        if (args.type === 'nodes') {
          this.dataSend.beCapId = idArr[1]
          this.$bvModal.show('popupQLBe')
        } else if (args.type === 'connectors') {
          this.dataSend.doanCongId = idArr[1]
          console.log('🚀 ~ file: index.vue:880 ~ onGianDoDoubleClick ~ this.dataSend:', this.dataSend)
          this.$bvModal.show('popupQLDoanCongVaOngCong')
        }
      }
    },
    ds_donvi: async function() {
      try {
        var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-sach-dv-nv')
        return rs.data
      } catch (error) {
        return []
      } finally {
      }
    },
    async TIMKIEM_DOITUONG(searchquery, loai) {
      console.log('🚀 ~ file: index.vue:901 ~ TIMKIEM_DOITUONG ~ searchquery, loai:', searchquery, loai)
      var query = searchquery + '|NGUOIDUNG_ID=' + this.$root.token.getNguoiDungID()
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/timkiem-doituong-v2', { query: query })
        if (rs && rs.data && rs.data.dsDOITUONG.length === 0) {
          this.$root.toastError('Không tìm thấy tuyến cáp của đối tượng')
          return
        } else {
          // Kiểm tra quyền thao tác với tuyến của tổ kỹ thuật khác
          if (this.dsDvTheoNhanVien.length === 0) {
            this.dsDvTheoNhanVien = await this.ds_donvi()
          }
          if (rs && rs.data && rs.data.dsTOKT.length > 0 && this.dsDvTheoNhanVien.length > 0) {
            var tmp = rs.data.dsTOKT.filter((e) => {
              return e.SELECTED === 1
            })
            if (tmp.length > 0) {
              var isPermitted = false
              for (let index = 0; index < tmp.length; index++) {
                const DONVI_ID = tmp[index].ID
                const idx = this.dsDvTheoNhanVien.findIndex((e) => e.DONVI_ID === DONVI_ID)
                if (idx > -1) {
                  isPermitted = true
                  break
                }
              }
              if (!isPermitted) {
                await this.$alert('Đối tượng thuộc quản lý của tổ kỹ thuật ' + tmp[0].NAME + ', bạn không có quyền thao tác.', 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  type: 'error'
                })
                return
              }
            }
          }
          // End Kiểm tra quyền thao tác với tuyến của tổ kỹ thuật khác
          this.dsTTVT = rs.data.dsTTVTS.map((i) => ({ TEN_DV: i.NAME, DONVI_ID: i.ID }))
          let ttvt = rs.data.dsTTVTS.find((x) => x.SELECTED == 1).ID
          if (ttvt) {
            this.dataSelected.ttvt = ttvt
          }
          this.dsToQL = rs.data.dsTOKT.map((i) => ({ TEN_DV: i.NAME, DONVI_ID: i.ID }))
          let tokt = rs.data.dsTOKT.find((x) => x.SELECTED == 1).ID
          if (tokt) {
            this.dataSelected.toql = tokt
          }
          await this.getdsTuyenCong()
          let doituong = rs.data.dsDOITUONG[0]
          let tuyencbIdx = this.dsTuyenCB.findIndex((x) => x.TUYENCB_ID == doituong.TUYENCB_ID)
          if (tuyencbIdx >= 0) {
            // Load giản đồ của đối tượng
            await this.tcDoubleClick(tuyencbIdx, this.dsTuyenCB[tuyencbIdx])
            await this.$refs.gridTC.setCurrentSelectedRow(tuyencbIdx)
            switch (doituong.LOAIDT) {
              case 'BECAP':
                let objNodeId = doituong.ID
                if (doituong.PARENT_ID) {
                  objNodeId = doituong.PARENT_ID
                }
                if (objNodeId) {
                  this.objNodeId = objNodeId
                  console.log('🚀 ~ file: index.vue:961 ~ TIMKIEM_DOITUONG ~ this.objNodeId:', this.objNodeId)
                  this.$refs.giando.selectedBe(objNodeId)
                  this.$refs.bando.selectedBe(objNodeId)
                }
                break
              case 'DOANCONG':
                let objEdgeId = doituong.ID
                let doanCong = this.$refs.giando.connectorsData.find((x) => x.id == 'DC_' + objEdgeId)
                if (doanCong) {
                  let becap = this.$refs.giando.nodesData.find((x) => x.id == doanCong.sourceID)
                  if (!becap) {
                    becap = this.$refs.giando.nodesData.find((x) => x.id == doanCong.targetID)
                  }
                  if (becap) {
                    this.objNodeId = becap.dataBe.BECAP_ID
                    this.$refs.giando.selectedBe(becap.dataBe.BECAP_ID)
                    this.$refs.bando.selectedBe(becap.dataBe.BECAP_ID)
                  }
                }
                break
            }
            this.txttimkiem = ''
          }
        }
      } catch (error) {
        console.log('TIMKIEM_DOITUONG', error)
        this.$alert(error.response.data.message_detail, error.response.data.message, {
          confirmButtonText: 'Đóng',
          type: 'error'
        })
      }
    }
    // #endregion
  }
}
</script>
<style>
.grid-stack-box {
  background-color: transparent;
}

.disabled a {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.disabled a:hover {
  cursor: default;
  color: #6c757d;
}
.wrapper {
  display: flex;
  margin: 0 auto;
  position: relative;
}
.label-box {
  padding: 5px 7px;
  border: 1px solid #b5b5b5;
  background-color: #efece7;
  border-radius: 5px;
  text-align: center;
  height: 49px;
  width: 170px;
  top: -50px;
  left: -120%;
  position: relative;
  transform: rotate(0deg);
  visibility: visible;
  opacity: 1;
  .text-ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  &:hover{
    .tooltip {
      visibility: visible;
      opacity: 1;
    }
  }
  .tooltip {
    visibility: hidden;
    background-color: #FFF5DC;
    color: black;
    text-align: center;
    border-radius: 5px;
    padding: 5px;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    transform: translateX(-50%);
    opacity: 0;
    transition: opacity 0.3s;
    &::after{
      content: "";
      position: absolute;
      top: 100%; 
      left: 50%;
      margin-left: -5px;
      border-width: 5px;
      border-style: solid;
      border-color: #FFF5DC transparent transparent transparent;
    }
  }
}
.sitemap-chart-canvas .dropdown-search.dropdown-icon .icon {
  margin: 0 !important;
  top: 50% !important;
  transform: translateY(-50%) !important;
}
</style>
