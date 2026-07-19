<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './store'
import Modal from './Modal'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import popupChonThueBao from './chon-thuebao'
import ModalChonToaDoExt from '@/modules/CABMAN/Modal/ChonToaDo/index.vue'
import ModalLichSuCap from '@/modules/CABMAN/Modal/LichSuCap/index.vue'
import ModalThueBaoCungDoiCap from '@/modules/CABMAN/Modal/ThueBaoCungDoiCap/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'

export default {
  components: { BssErrorMarker, BssRequiredMarker, Modal, popupChonThueBao, ModalChonToaDoExt, ModalLichSuCap, ModalThueBaoCungDoiCap, SearchAccount },
  name: 'DauNoiThueBao',
  props: {
    object: Object
  },
  validations: {
    ViTri1_New: {
      required
    },
    ViTri2_New: {
      required
    },
    KyHieuKC_New: {
      required
    },
    CuLy_New: {
      required,
      minValue: minValue(1),
      maxValue: maxValue(9999)
    }

  },
  computed: {
    ...mapState('cabman/daunoiThuebao', statePropertyName),
    ...mapState('cabman', ['daunoiThuebao']),
    ...mapGetters('cabman/daunoiThuebao', getterName),
    getDiachi () {
      return this.thuebao.DIACHI_LD
    },
    getPosition () {
      if (Object.keys(this.thuebao).length > 0 && this.thuebao.TOADO_LD !== null) {
        let p = { lat: parseFloat(this.thuebao.TOADO_LD.split(';')[0]), lng: parseFloat(this.thuebao.TOADO_LD.split(';')[1]) }
        return p
      } else {
        return { lat: 0, lng: 0 }
      }
    },
    getToaDo () {
      return this.thuebao.TOADO_LD
    },
    getMessage () {
      return this.updateGhiChu ? this.message.save : this.message.new
    },
    getDanDoCapCu () {
      if (!this.hideDaynhaytrong) {
        return this.DanDoCap.dando_rutgon
      } else return this.DanDoCap.dando_rutgon.filter(e => e.KYHIEU !== 'Dây nhảy trong')
    },
    getDanDoCapNew () {
      if (!this.hideDaynhaytrong) {
        return this.DanDoCapNew.dando_rutgon
      } else return this.DanDoCapNew.dando_rutgon.filter(e => e.KYHIEU !== 'Dây nhảy trong')
    },
    chkThueBaoDichChuyen () {
      var CurrentThueBao = this.thuebao
      if (CurrentThueBao === null) return false
      if (CurrentThueBao.IS_THUEBAO === 1) {
        return false
      } else {
        if (CurrentThueBao.LOAIHD_ID === null) return true
        if (CurrentThueBao.LOAIHD_ID === 3) return true
        return false
      }
    },
    disablebtnGhiLai () {
      return Object.keys(this.thuebao).length === 0
    },
    disablebtnGiaiPhongCap () {
      return Object.keys(this.thuebao).length === 0
    },
    disablebtnLayCapCungDuongTruyen () {
      return Object.keys(this.thuebao).length === 0
    },
    disablebtnDichChuyenGiuCap () {
      return Object.keys(this.thuebao).length === 0 || !this.chkThueBaoDichChuyen
    },
    disablebtnInLyLich () {
      return Object.keys(this.thuebao).length === 0
    },
    disablebtnLichSuCap () {
      return Object.keys(this.thuebao).length === 0 || this.kieu1 === 0
    }
  },
  data () {
    return {
      // ---
      obj: {
        // CloseAfterModifed: false,
        // choPhepDoiTramTbi: false,
        // tramTbiId: 138,
        // chonTramTbiTheoTB: false,
        // thueBaoId: 59312906,
        // kieu: 1
        CloseAfterModifed: false,
        choPhepDoiTramTbi: true,
        tramTbiId: -1,
        chonTramTbiTheoTB: true,
        thueBaoId: -1,
        kieu: 0,
        //
        ketCuoiId: -1,
        viTri: null,
        mat: null
      },
      // ---
      tmp_thuebao: {},
      thuebao: {},
      message: {
        new: 'Nhập ghi chú mới',
        save: 'Lưu ghi chú'
      },
      CurrentKetCuoiNew: null,
      messageCapNhatGhiChu: null,
      pageSettings: { pageSize: 10 },
      isAON: false,
      soi: 1,
      soi_new: 1,
      updateGhiChu: false,
      customAttributes: {class: 'customcss'},
      kieu1: 1,
      kieu2: 0,
      ma_tb: '',
      hideDaynhaytrong: false,
      KyHieuKC_New: null,
      ViTri1_New: '',
      ViTri2_New: '',
      KyHieuCapGoc_New: null,
      SoiGoc1_New: null,
      SoiGoc2_New: null,
      CuLy_New: 100,
      modalShow: false,
      LockEvents: {
        cboKyHieuKC_New: false,
        cboViTri1_New: false,
        cboViTri2_New: false
      },
      ReadOnly: {
        cboKyHieuKC_New: false,
        cboViTri1_New: false,
        cboViTri2_New: false
      },
      IsFilled: false
    }
  },
  methods: {
    ...mapActions('cabman/daunoiThuebao', actionName),
    queryCellInfo1 (args) {
      var data = args.data
      var grid = document.getElementById('grid1').getElementsByClassName('e-grid')[0].ej2_instances[0]
      if (grid) {
        var currentdata = grid.currentViewData
        if (args.column.field === 'TEN_CHA' && data[args.column.field] != null) {
          var value = currentdata.filter((m) => { return m[args.column.field] === data[args.column.field] })
          args.rowSpan = value.length
        }
      }
    },
    validateData () {
      this.$v.$touch()

      if (this.$v.$invalid && !this.$v.KyHieuKC_New.required) {
        this.$root.$toast.error('Chưa có thông tin kết cuối !')
        return false
      }

      if (this.$v.$invalid && !this.$v.ViTri1_New.required) {
        this.$root.$toast.error('Chưa nhập vị trí trên kết cuối cần đấu nối thuê bao !')
        return false
      }
      if (this.isAON) {
        if (this.$v.$invalid && !this.$v.ViTri2_New.required) {
          this.$root.$toast.error('Chưa nhập vị trí trên kết cuối cần đấu nối thuê bao !')
          return false
        }
      }

      if (this.$v.$invalid && !this.$v.CuLy_New.required) {
        this.$root.$toast.error('Chưa nhập cự ly !')
        return false
      }

      if (this.$v.$invalid && !this.$v.CuLy_New.minValue) {
        this.$root.$toast.error('Cự ly không được nhập nhỏ hơn 1')
        return false
      }

      if (this.$v.$invalid && !this.$v.CuLy_New.maxValue) {
        this.$root.$toast.error('Cự ly không được nhập lớn hơn 9999')
        return false
      }

      return true
    },
    queryCellInfo2 (args) {
      var data = args.data
      var grid = document.getElementById('grid2').getElementsByClassName('e-grid')[0].ej2_instances[0]
      if (grid) {
        var currentdata = grid.currentViewData
        if (args.column.field === 'TEN_CHA' && data[args.column.field] != null) {
          var value = currentdata.filter((m) => { return m[args.column.field] === data[args.column.field] })
          args.rowSpan = value.length
        }
      }
    },
    async GIU_CAP () {
      if (this.disablebtnDichChuyenGiuCap) return false
      if (!this.KIEMTRA_TRUOCKHI_LUU(false)) return false
      if (this.thuebao === null) return false
      var vis_thuebao = this.thuebao.IS_THUEBAO === 1
      var vdb_id = vis_thuebao ? this.thuebao.THUEBAO_ID : this.thuebao.HDTB_ID
      var vdaucuoi_id = this.thuebao.DAUCUOI_ID

      if (vis_thuebao) {
        this.$toast.error('Giữ cáp chỉ áp dụng cho thuê bao đang dịch chuyển.')
        return
      }

      try {
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_THUEBAO_ID: vdb_id, P_DAUCUOI_ID: vdaucuoi_id, PROCEDURE_NAME: 'CABMAN.PKG_DAUNOI_TB.LAY_THONGTIN_THUEBAO_GIUCAP'}
        )
        if (rs.data !== null && rs.data.length > 0) {
          console.log('GIU_CAP', rs.data)
          var OrigionThueBao = rs.data[0]
          if (OrigionThueBao.KETCUOI_ID === null) {
            this.$toast.error('Không thể lấy thông tin cáp do thuê bao chưa từng được nhập cáp.')
            return
          }
          if (OrigionThueBao.VITRI === null || OrigionThueBao.VITRI === 0) {
            this.$toast.error('Thuê bao đã được nhập vào kết cuối nhưng chưa chọn vị trí trên kết cuối, không thể lấy thông tin cáp.')
            return
          }
          this.$confirm(`Bạn có chắc muốn dịch chuyển giữ nguyên thông tin cáp cũ ?`,
            'Dịch chuyển giữ cáp',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => {
            try {
              var vketcuoi_id = OrigionThueBao.KETCUOI_ID
              var vvitri = OrigionThueBao.VITRI
              var vmat = 2
              var vvitri2 = OrigionThueBao.VITRI_2 === null ? 0 : OrigionThueBao.VITRI_2
              var vmat2 = 2
              var vculy = OrigionThueBao.CULY
              var giucap = {
                kieu: vis_thuebao ? 0 : 1,
                db_id: vdb_id,
                ketcuoi_id: vketcuoi_id,
                vitri: vvitri,
                mat: vmat,
                vitri2: vvitri2,
                mat2: vmat2,
                culy: vculy,
                lap_tb_cung_cap: 1,
                daucuoi_id: vdaucuoi_id,
                checkquyen: 0
              }
              console.log('GIU_CAP_THUEBAO', giucap)
              this.loading(true)
              this.api_saveNhapCapThueBao(giucap).then((response) => {
                console.log('GIU_CAP_THUEBAO success', response)
                if (response.error_code === 'BSS-00000000') {
                  const h = this.$createElement
                  this.$msgbox({title: 'Giữ cáp cho thuê bao',
                    message: h('p', null, [
                      h('i', { style: 'color: black' }, 'Giữ cáp thành công !')
                    ]),
                    showCancelButton: false,
                    confirmButtonText: 'Đóng'}).then(action => {
                    this.onEnterMaTB()
                  })
                }
                this.loading(false)
              }).catch((error) => {
                this.loading(false)
                console.log('GIU_CAP_THUEBAO error', error)
                const h = this.$createElement
                this.$msgbox({title: 'Giữ cáp cho thuê bao',
                  type: 'error',
                  message: h('p', null, [
                    h('i', { style: 'color: red' }, error.BSS.message_detail)
                  ]),
                  showCancelButton: false,
                  confirmButtonText: 'Đóng'})
              })
            } catch (error) {
              console.log('GIU_CAP_THUEBAO error', error)
            }
          }).catch(() => {})
        } else {
          this.loading(false)
          this.$toast.error('Không tìm thấy thông tin danh bạ')
        }
      } catch (error) {
        console.log(error)
        this.loading(false)
        this.$toast.error('Không tìm thấy thông tin danh bạ')
      }
      this.loading(false)
    },
    inLyLich () {
      if (this.disablebtnInLyLich) return
      alert('Tham chiếu đến form In lý lịch')
    },
    chonThueBaoTuDanhBa () {
      this.$refs.popupSearchAccount.show()
    },
    searchAccount_Close: async function (args) {
      this.ma_tb = args.ma_tb
      if (
        !(
          this.ma_tb === null ||
          this.ma_tb === undefined ||
          this.ma_tb === ''
        )
      ) { await this.onEnterMaTB() }
      this.$refs.popupSearchAccount.hide()
    },
    luuToaDo () {
      console.log('luuToaDo', this.thuebao.TOADO_LD)
      this.$confirm(`Cập nhật tọa độ thuê bao vào danh bạ ?`,
        'Cập nhật tọa độ thuê bao',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => {
        var vis_thuebao = this.thuebao.IS_THUEBAO === 1
        var vdb_id = vis_thuebao ? this.thuebao.THUEBAO_ID : this.thuebao.HDTB_ID
        var vdaucuoi_id = this.thuebao.DAUCUOI_ID
        var vdichvuvt_id = this.thuebao.DICHVUVT_ID
        var lat = -1, lng = -1
        try {
          lat = parseFloat(this.thuebao.TOADO_LD.split(';')[0])
          lng = parseFloat(this.thuebao.TOADO_LD.split(';')[1])
        } catch (error) {
          this.$root.$toast.error('Cấu trúc tọa độ không đúng định dạng.')
          return false
        }
        var data = {
          kieu: vis_thuebao ? 0 : 1,
          tb_id: vdb_id,
          latitude: lat,
          longtitude: lng,
          update_tb_cung_cap: 1
        }
        this.$root.context.post(
          '/web-cabman/danhmuc/toadothuebao/capnhat', data
        ).then((result) => {
          console.log('result', result)
          if (result.data !== null && result.data[0].KETQUA) {
            this.$toast.success('Cập nhật tọa độ thuê bao thành công !')
          }
        })
      }).catch(() => {})
    },
    dataBound: function () {
      try {
        if (this.$refs.grid1.ej2Instances.getRows().length > 2) this.$refs.grid1.autoFitColumns()
        if (this.$refs.grid2.ej2Instances.getRows().length > 2) this.$refs.grid2.autoFitColumns()
      } catch (error) {

      }
    },
    lichSuCap () {
      if (this.disablebtnLichSuCap) return
      console.log('lichSuCap', this.thuebao)
      this.$refs.ModalLichSuCap.show(this.thuebao).then((result) => {
        if (result.ok) {
        }
      })
    },
    thueBaoCungDoiCap () {
      if (this.disablebtnLayCapCungDuongTruyen) return
      console.log('thueBaoCungDoiCap', this.thuebao)
      this.$refs.ModalThueBaoCungDoiCap.show(this.thuebao).then((result) => {
        if (result.ok) {
          console.log('thueBaoCungDoiCap', result.data)
          if (result.data) {
            var msg = null
            if (result.data.KETCUOI_ID === null) {
              msg = 'Không thể lấy thông tin cáp do thuê bao ' + result.data.MA_TB + ' chưa nhập cáp.'
            }
            if (result.data.VITRI === 0 || result.data.VITRI === null) {
              msg = 'Thuê bao ' + result.data.MA_TB + ' đã được nhập vào kết cuối nhưng chưa chọn vị trí trên kết cuối, không thể lấy thông tin cáp.'
            }
            const h = this.$createElement
            if (msg !== null) {
              this.$msgbox({title: 'Lấy cáp cùng đường truyền',
                type: 'error',
                message: h('p', null, [
                  h('i', { style: 'color: red' }, msg)
                ]),
                showCancelButton: false,
                confirmButtonText: 'Đóng'})
              return false
            }
          }
          msg = 'Bạn có chắc muốn cập nhật thông tin cáp cho thuê bao hiện tại theo thuê bao ' + result.data.MA_TB + ' ?'
          this.$confirm(msg,
            'Cấp cáp cho thuê bao',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => {
            if (!this.KIEMTRA_TRUOCKHI_LUU(false)) return

            var vketcuoi_id = result.data.KETCUOI_ID
            var vvitri = result.data.VITRI
            var vmat = 2
            var vvitri2 = result.data.VITRI_2 === null ? 0 : result.data.VITRI_2
            var vmat2 = 2
            var vculy = result.data.CULY
            var vis_thuebao = this.thuebao.IS_THUEBAO === 1
            var vdb_id = vis_thuebao ? this.thuebao.THUEBAO_ID : this.thuebao.HDTB_ID
            var vdaucuoi_id = this.thuebao.DAUCUOI_ID

            var nhapcap = {
              'kieu': vis_thuebao ? 0 : 1,
              'db_id': vdb_id,
              'ketcuoi_id': vketcuoi_id,
              'vitri': vvitri,
              'mat': vmat,
              'vitri2': vvitri2,
              'mat2': vmat2,
              'culy': vculy,
              'lap_tb_cung_cap': 1,
              'daucuoi_id': vdaucuoi_id,
              'checkquyen': 0
            }
            console.log('nhapcap', nhapcap)
            this.THUCHIEN_NHAP_CAP_THUEBAO(nhapcap)
          }).catch(() => {})
        }
      })
    },
    async chonToaDo () {
      let p = null
      try {
        p = { lat: parseFloat(this.thuebao.TOADO_LD.split(';')[0]), lng: parseFloat(this.thuebao.TOADO_LD.split(';')[1]) }
      } catch (error) {
        p = { lat: 0, lng: 0 }
      }
      this.$refs.ModalChonToaDoExt.show(p, this.thuebao.DIACHI_LD).then((result) => {
        if (result.ok) {
          this.thuebao.TOADO_LD = result.data.lat + ';' + result.data.lng
          this.luuToaDo()
        }
      })
    },
    async chonThueBao () {
      this.thuebao = {...this.tmp_thuebao}
      console.log('chonThueBao', this.thuebao)
      if (this.thuebao.THUEBAO_ID || this.thuebao.HDTB_ID) {
        this.LAY_THONGTIN_THUEBAO()
      }
    },
    async LAY_THONGTIN_THUEBAO () {
      try {
        this.loading(true)
        this.$v.$reset()
        await this.api_getThongTinCap({thuebao_id: this.thuebao.IS_THUEBAO === 1 ? this.thuebao.THUEBAO_ID : this.thuebao.HDTB_ID, daucuoi_id: this.thuebao.DAUCUOI_ID, is_thuebao: this.thuebao.IS_THUEBAO})
        console.log('ThongTinCap', this.ThongTinCap)
        if (this.ThongTinCap.KYHIEU_KC) {
          let kyhieu = this.ThongTinCap.KYHIEU_KC
          let mat = 2
          let kieu = 1
          let vitri = this.soi.toString() === '1' ? parseInt(this.ThongTinCap.VITRI) || 0 : parseInt(this.ThongTinCap.VITRI_2) || 0
          let data = { 'kieu': kieu,
            'kyhieu': kyhieu,
            'mat': mat,
            'vitri': vitri}
          console.log('api_getDanDoCap', data)
          await this.api_getDanDoCap(data)
          console.log('DanDoCap', this.DanDoCap)
          this.$v.$reset()
        } else {
          // this.$toast.warning('Không tìm thấy thông tin cáp của thuê bao ' + this.ma_tb)
        }
        this.loading(false)
      } catch (error) {
        this.loading(false)
        console.log('LAY_THONGTIN_THUEBAO', error)
      }
    },
    popupChonThueBao_selectedRowChanged (dataItem) {
      console.log('popupChonThueBao_selectedRowChanged', dataItem)
      this.tmp_thuebao = dataItem
    },
    async selected (rowData) {
      console.log('rowData', rowData)
      this.setKetCuoiNew(rowData)
      console.log('KetCuoiNew', this.KetCuoiNew)
      this.KyHieuKC_New = this.KetCuoiNew.KETCUOI_ID
      if (this.KyHieuKC_New) {
        await this.HIEN_DS_PORT_KETCUOI_ROI()
        await this.HIEN_DANDO_CAP_NEW()
      }
    },

    closeModal: function () {
      this.modalShow = !this.modalShow
    },
    focusInputGhiChu () {
      this.$refs['ghichu'].focus()
    },
    onClickGhiChu () {
      if (this.updateGhiChu) {
        console.log('onClickGhiChu save')
        this.api_saveGhiChuCap({ kieu: this.thuebao.IS_THUEBAO === 1 ? 0 : 1, db_id: this.thuebao.THUEBAO_ID, ghichu: this.thuebao.GHICHU, daucuoi_id: this.thuebao.DAUCUOI_ID }).then(() => { })
      } else {
        this.focusInputGhiChu()
      }
      this.updateGhiChu = !this.updateGhiChu
    },
    async onClickKyHieuKC_New () {
    },
    setProps: function (flat, newVal) {
      console.log("setProps")
      console.log(flat)
      console.log(newVal)
      for (var i in flat) {
        if ((typeof flat[i] === 'object') && !(flat[i] instanceof Array)) {
          this.setProps(flat[i], newVal)
          return
        } else {
          flat[i] = newVal
        }
      }
    },
    onClickOption1 () {
      this.kieu1 = 1
      this.kieu2 = 0
    },
    onClickOption2 () {
      this.kieu1 = 0
      this.kieu2 = 1
    },
    async onClickViTri1_New () {
      console.log('onClickViTri1_New')
      await this.HIEN_DANDO_CAP_NEW()
    },
    async onClickViTri2_New () {
      console.log('onClickViTri2_New')
      await this.HIEN_DANDO_CAP_NEW()
    },
    async _getDanDoCapCu () {
      try {
        if (this.ThongTinCap.KYHIEU_KC) {
          let kyhieu = this.ThongTinCap.KYHIEU_KC
          let mat = 2
          let kieu = 1
          let vitri = this.soi.toString() === '1' ? parseInt(this.ThongTinCap.VITRI) || 0 : parseInt(this.ThongTinCap.VITRI_2) || 0
          await this.api_getDanDoCap({ 'kieu': kieu,
            'kyhieu': kyhieu,
            'mat': mat,
            'vitri': vitri}).then(() => {
          })
        }
      } catch (error) {
      } finally {
      }
    },
    init () {
      this.clearState()
      // this.KyHieuKC_New = ''
      // this.ViTri1_New = ''
      // this.ViTri2_New = ''
      this.thuebao = {}
      this.tmp_thuebao = {}
      this.updateGhiChu = false
      // this.KyHieuCapGoc_New = null
      // this.SoiGoc1_New = null
      // this.SoiGoc2_New = null
      this.CuLy_New = 100
      this.$v.$reset()
    },
    async onEnterMaTB () {
      this.init()
      // this.ma_tb = this.ma_tb.trim()
      if (!this.ma_tb) {
        this.$toast.error('Vui lòng nhập Số máy/Account !')
        this.$refs.ma_tb.focus()
        return
      }
      try {
        this.loading(true)
        await this.api_getDsThueBaoTheoMaTB({ma_tb: this.ma_tb, kieu: this.kieu1 === 1 ? 0 : 1, dichvuvt_id: -1})
        console.log('DsThueBao', this.DsThueBao)
        if (this.DsThueBao.length === 0 || this.DsThueBao.length > 1) {
          this.tmp_thuebao = this.DsThueBao[0]
          this.$bvModal.show('modal-ChonThueBao')
        } else if (this.DsThueBao && this.DsThueBao.length === 1) {
          this.thuebao = {...this.DsThueBao[0]}
          console.log('this.thuebao', this.thuebao)
          this.ma_tb = this.thuebao.MA_TB
          if (this.thuebao.THUEBAO_ID || this.thuebao.HDTB_ID) {
            await this.LAY_THONGTIN_THUEBAO()
          }
        } else {
          this.$toast.error('Không tìm thấy thông tin thuê bao ' + this.ma_tb)
        }
      } catch (e) {
        this.loading(false)
        this.$toast.error(e.message)
      }
      this.loading(false)
    },
    async HIEN_DS_PORT_KETCUOI_ROI () {
      await this.api_getPortKetCuoiRoi({ketcuoi_id: this.KetCuoiNew.KETCUOI_ID, mat: 2})
      console.log('PortKetCuoiRoi', this.PortKetCuoiRoi)
      if (this.PortKetCuoiRoi.length > 0) {
        this.ViTri1_New = this.PortKetCuoiRoi[0].ID
      }
    },
    KIEMTRA_TRUOCKHI_LUU (isDauNoi) {
      var currentThueBao = this.thuebao
      if (currentThueBao === null) {
        this.$toast.error('Chưa có thông tin thuê bao !')
        return false
      } else {
        if (isDauNoi && !this.validateData()) return false
        console.log('isDauNoi', isDauNoi)
        console.log('this.KetCuoiNew', this.KetCuoiNew)
        if (isDauNoi && Object.entries(this.KetCuoiNew).length === 0) {
          if (this.ViTri1_New === null || this.ViTri1_New === '') {
            this.$toast.error('Chưa nhập vị trí trên kết cuối cần đấu nối thuê bao !')
            return false
          } else {
            this.$toast.error('Chưa có thông tin kết cuối !')
            return false
          }
        }
        if (isDauNoi) {
          if (this.CuLy_New < 1) {
            this.$toast.error('Chưa nhập cự ly !')
            return false
          }
        }
        return true
      }
    },
    async THUCHIEN_NHAP_CAP_THUEBAO (data) {
      try {
        this.loading(true)
        console.log('THUCHIEN_NHAP_CAP_THUEBAO', data)
        await this.api_saveNhapCapThueBao(data).then((response) => {
          console.log('THUCHIEN_NHAP_CAP_THUEBAO success', response)
          if (response.error_code === 'BSS-00000000') {
            const h = this.$createElement
            this.loading(false)
            this.$msgbox({title: 'Cấp cáp cho thuê bao',
              message: h('p', null, [
                h('i', { style: 'color: black' }, 'Cấp cáp thành công !')
              ]),
              showCancelButton: false,
              confirmButtonText: 'Đóng'}).then(action => {
              this.onEnterMaTB()
              this.$emit('NHAP_CAP_THUEBAO', response.data)
            })
          }
        }).catch((error) => {
          this.loading(false)
          console.log('THUCHIEN_NHAP_CAP_THUEBAO error', error)
          const h = this.$createElement
          this.$msgbox({title: 'Cấp cáp cho thuê bao',
            type: 'error',
            message: h('p', null, [
              h('i', { style: 'color: red' }, error.BSS.message_detail)
            ]),
            showCancelButton: false,
            confirmButtonText: 'Đóng'})
        })
      } catch (e) {
        this.loading(false)
        this.$toast.error(e.message)
      }
      this.loading(false)
    },
    async THUCHIEN_GIAIPHONG_CAP_THUEBAO (data) {
      try {
        this.loading(true)
        console.log('THUCHIEN_GIAIPHONG_CAP_THUEBAO', data)
        await this.api_saveGiaiPhongCapThueBao(data).then((response) => {
          console.log('THUCHIEN_GIAIPHONG_CAP_THUEBAO success', response)
          if (response.error_code === 'BSS-00000000') {
            // this.$toast.success('OK')
            const h = this.$createElement
            this.$msgbox({title: 'Giải phóng cáp thuê bao',
              message: h('p', null, [
              // h('span', null, error.BSS.message),
              // h('br'),
                h('i', { style: 'color: black' }, 'Giải phóng cáp thuê bao thành công !')
              ]),
              showCancelButton: false,
              confirmButtonText: 'Đóng'}).then(action => {
              this.onEnterMaTB()
            })
          }
        }).catch((error) => {
          console.log('THUCHIEN_GIAIPHONG_CAP_THUEBAO error', error)
          const h = this.$createElement
          this.$msgbox({title: 'Giải phóng cáp thuê bao',
            type: 'error',
            message: h('p', null, [
              // h('span', null, error.BSS.message),
              // h('br'),
              h('i', { style: 'color: red' }, error.BSS.message_detail)
            ]),
            showCancelButton: false,
            confirmButtonText: 'Đóng'})
        })
        this.loading(false)
      } catch (e) {
        this.loading(false)
        this.$toast.error(e.message)
      }
      this.loading(false)
    },
    async GIAIPHONG_CAP_THUEBAO () {
      if (this.disablebtnGiaiPhongCap) return
      if (!this.KIEMTRA_TRUOCKHI_LUU(false)) return
      if (this.thuebao !== null) {
        console.log('Giai phong cap ThueBao', this.thuebao)
        var currentThueBao = this.thuebao
        console.log('Giai phong currentThueBao', currentThueBao)
        var vis_thuebao = currentThueBao.IS_THUEBAO.toString() === '1'
        var vdb_id = vis_thuebao ? currentThueBao.THUEBAO_ID : currentThueBao.HDTB_ID
        let xoacap = {
          'kieu': vis_thuebao ? 0 : 1,
          'db_id': vdb_id,
          // 'ketcuoi_id': this.KetCuoiNew.KETCUOI_ID,
          // 'vitri': this.ViTri1_New,
          // 'mat': 2,
          // 'vitri2': this.ViTri2_New === '' ? 0 : this.ViTri2_New,
          // 'mat2': 2,
          // 'culy': this.CuLy_New,
          // 'lap_tb_cung_cap': 1,
          'daucuoi_id': currentThueBao.DAUCUOI_ID,
          'checkquyen': 0
        }
        this.$confirm(`Bạn có chắc muốn giải phóng thuê bao khỏi tuyến cáp ?`,
          'Giải phóng cáp',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
            // type: 'warning'
          }).then(() => {
          this.THUCHIEN_GIAIPHONG_CAP_THUEBAO(xoacap)
        }).catch(() => {})
      }
    },
    async NHAP_CAP_THUEBAO () {
      if (this.disablebtnGhiLai) return
      if (!this.KIEMTRA_TRUOCKHI_LUU(true)) return
      if (this.thuebao !== null) {
        console.log('Nhap cap ThueBao', this.thuebao)
        var currentThueBao = this.thuebao
        console.log('Nhap currentThueBao', currentThueBao)
        var vis_thuebao = currentThueBao.IS_THUEBAO.toString() === '1'
        var vdb_id = vis_thuebao ? currentThueBao.THUEBAO_ID : currentThueBao.HDTB_ID
        let nhapcap = {
          'kieu': vis_thuebao ? 0 : 1,
          'db_id': vdb_id,
          'ketcuoi_id': this.KetCuoiNew.KETCUOI_ID,
          'vitri': this.ViTri1_New,
          'mat': 2,
          'vitri2': this.ViTri2_New === '' ? 0 : this.ViTri2_New,
          'mat2': 2,
          'culy': this.CuLy_New,
          'lap_tb_cung_cap': 1,
          'daucuoi_id': currentThueBao.DAUCUOI_ID,
          'checkquyen': 0
        }
        this.$confirm(`Bạn có chắc muốn nhập cáp cho thuê bao ?`,
          'Nhập cáp',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
            // type: 'warning'
          }).then(() => {
          this.THUCHIEN_NHAP_CAP_THUEBAO(nhapcap)
        }).catch(() => {})
      }
    },
    isEmptyObj (Obj) {
      if (Obj === undefined) return true
      var isEmptyObj = !Object.keys(Obj).length
      return isEmptyObj
    },
    async NAP_TT_THEO_KETCUOI_ID (ketCuoiId, viTri, mat) {
      try {
        this.IsFilled = true
        var data = {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          P_KETCUOI_ID: ketCuoiId,
          PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.LAY_KETCUOI_THEO_ID'
        }
        console.log('🚀 ~ file: index.vue ~ line 787 ~ NAP_TT_THEO_KETCUOI_ID ~ data', data)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', data
        )
        if (rs.data.length === 0) {
          await this.$alert('Không tìm thấy thông tin kết cuối', 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        } else {
          this.CurrentKetCuoiNew = rs.data[0]
          this.setKetCuoiNew(this.CurrentKetCuoiNew)
          this.LockEvents.cboKyHieuKC_New = true
          this.LockEvents.cboViTri1_New = true
          this.LockEvents.cboViTri2_New = true

          this.KyHieuKC_New = this.CurrentKetCuoiNew.KETCUOI_ID
          console.log('🚀 ~ file: index.vue ~ line 814 ~ NAP_TT_THEO_KETCUOI_ID ~ this.KyHieuKC_New', this.KyHieuKC_New)
          this.setPortKetCuoiRoi([
            {
              ID: parseInt(viTri),
              MOTA: viTri.toString()
            }
          ])
          this.ViTri1_New = parseInt(viTri)
          console.log('🚀 ~ file: index.vue ~ line 816 ~ NAP_TT_THEO_KETCUOI_ID ~ this.ViTri1_New', this.ViTri1_New)

          this.ReadOnly.cboKyHieuKC_New = true
          this.ReadOnly.cboViTri1_New = true
          this.ReadOnly.cboViTri2_New = true

          await this.HIEN_TT_CAP_NEW()
        }
      } catch (error) {
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    async HIEN_TT_CAP_NEW () {
      if (this.KyHieuKC_New === null) return
      if (!this.IsFilled) {
        await this.HIEN_DS_PORT_KETCUOI_ROI()
      }
      await this.HIEN_DANDO_CAP_NEW()
    },

    async HIEN_DANDO_CAP_NEW () {
      console.log('HIEN_DANDO_CAP_NEW', this.KetCuoiNew)
      this.SoiGoc1_New = ''
      this.SoiGoc2_New = ''
      this.KyHieuCapGoc_New = ''
      this.clearStateDanDoCapNew()
      if (this.KyHieuKC_New) {
        this.loading(true)
        if (this.ViTri1_New === '' || this.ViTri1_New === null) {
          await this.HIEN_DS_PORT_KETCUOI_ROI()
        }
        let vkyhieu = this.KetCuoiNew.KYHIEU
        let vmat = 2
        let vkieu = 1
        console.log('this.api_getCapGocTheoKetCuoiID this.ViTri1_New', this.ViTri1_New)
        await this.api_getCapGocTheoKetCuoiID({ketcuoi_id: this.KetCuoiNew.KETCUOI_ID, vitri: parseInt(this.ViTri1_New) || 0, mat: vmat}).then((response) => {
          if (response.data) {
            this.KyHieuCapGoc_New = response.data.CURSOR1[0].KYHIEU
            this.SoiGoc1_New = response.data.CURSOR1[0].SOI
          }
        })
        if (this.ViTri2_New !== null && this.ViTri2_New !== '') {
          console.log('this.api_getCapGocTheoKetCuoiID this.ViTri2_New', this.ViTri2_New)
          await this.api_getCapGocTheoKetCuoiID({ketcuoi_id: this.KetCuoiNew.KETCUOI_ID, vitri: parseInt(this.ViTri2_New) || 0, mat: vmat}).then((response) => {
            if (response.data) {
              this.SoiGoc2_New = response.data.CURSOR1[0].SOI
            }
          })
        }
        let vvitri = 0
        console.log('this.soi_new', this.soi_new)
        if (this.soi_new.toString() === '1') {
          console.log('this.soi_new a', this.soi_new)
          vvitri = this.ViTri1_New === '' ? 0 : this.ViTri1_New
        }
        if (this.soi_new.toString() === '2') {
          console.log('this.soi_new b', this.soi_new)
          vvitri = this.ViTri2_New === '' ? 0 : this.ViTri2_New
        }
        try {
          await this.api_getDanDoCapNew({ 'kieu': vkieu,
            'kyhieu': vkyhieu,
            'mat': vmat,
            'vitri': vvitri })
          console.log('DanDoCapNew', this.DanDoCapNew)
        } catch (e) {
          this.loading(false)
          this.$toast.error(e.message)
        }
        this.loading(false)
      } else {

      }
    }
  },
  watch: {
    message: function (val) {
      console.log('watch message', val)
    },
    soi: function (val) {
      console.log('watch soi', val)
      this.loading(true)
      this._getDanDoCapCu().then(() => {
        this.loading(false)
      })
    },
    soi_new: function (val) {
      console.log('watch soi', val)
      this.loading(true)
      this.HIEN_DANDO_CAP_NEW()
    },
    isAON: function (val) {
      console.log('watch isAON', val)
      if (!val) {
        this.ViTri2_New = ''
      }
    },
    kieu1: function (val) {
      console.log('watch kieu1', val)
      if (val === 1) this.onEnterMaTB()
    },
    kieu2: function (val) {
      console.log('watch kieu2', val)
      if (val === 1) this.onEnterMaTB()
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
  },
  // provide: {
  //   grid: [Page, Filter, Resize, Freeze]
  // },

  async mounted () {
    this.$refs.ma_tb.focus()
    if (!this.isEmptyObj(this.object)) {
      this.obj = {...this.obj, ...this.object}
    }
    console.log('obj', this.obj)
    if (this.obj.thueBaoId !== -1) {
      if (this.obj.kieu === 0) {
        this.ma_tb = 'THUEBAO_ID|' + this.obj.thueBaoId
        this.kieu1 = 1
        this.kieu2 = 0
      } else {
        this.ma_tb = 'HDTB_ID|' + this.obj.thueBaoId
        this.kieu1 = 0
        this.kieu2 = 1
      }
      await this.onEnterMaTB()
    }
    if (this.obj.ketCuoiId !== -1) {
      await this.NAP_TT_THEO_KETCUOI_ID(this.obj.ketCuoiId, this.obj.viTri, this.obj.mat)
    }
  }
}
</script>
<style scoped src="./index.scss">
</style>
