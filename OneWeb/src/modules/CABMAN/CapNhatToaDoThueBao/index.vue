<template src='./index.html'>
</template>

<script>
import ModalLichSuToaDo from './ModalLichSuToaDo'
import ModalChonThueBao from './ModalChonThueBao'
import ToaDo from './ToaDo/Modal'
// import BanDo from './components/BanDo'
import BanDoVNPT from './components/BanDoVNPT'
import GraphNode from './structures/GraphNode'
import GraphPolyline from './structures/GraphPolyline'
import InLyLichDayMay from './InLyLichDayMay'
import ModalChonDiaChi from './ModalChonDiaChi'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
export default {
  components: {
    ModalLichSuToaDo,
    ModalChonThueBao,
    ToaDo,
    // BanDo,
    InLyLichDayMay,
    ModalChonDiaChi,
    SearchAccount,
    BanDoVNPT
  },
  data () {
    return {
      ma_tb: '',
      loaiTimKiem: 0,
      toaDo: null,
      toaDoTruoc: null,
      viDo_LD: null,
      kinhDo_LD: null,
      viDo_KC: null,
      kinhDo_KC: null,
      ttThueBao: null,
      dsThueBao: [],
      tsktThueBao: [],
      lsThueBao: [],
      modalShowLichSuThueBao: false,
      modalShowInLyLich: false,
      modalConfirm: false,
      showThongSoKyThuat: false,
      nodes: [],
      polylines: [],
      isDisableBtnInLyLich: true,
      thongTinTBTraCuu: {},
      thongTinGianDo: [],
      thongTinBaoHong: [],
      doRenderPDF: false,
      showPanelReport: false,
      zoomDefault: 13,
      mapTypeId: 'ROADMAP',
      markers: [],
      polyline: [],
      center: {
        lat: 20.8468135,
        lng: 106.663727
      },
      popupToaDo: 'popupToaDo',
      query: null,
      isShowDiaChi: false,
      tinh_id: null,
      quan_id: null,
      phuong_id: null,
      pho_id: null,
      ap_id: null,
      khu_id: null,
      dacdiem_id: null,
      sonha: null
      // popupComponent: SearchAccount,
      // popupComponentData: null,
      // popupComponentName: '',
      // popupComponentEvents: {
      //     'form-close':'popupClosed',
      //     'acceptChangeCTV':'popupClosed',
      //     'acceptChangeNGT':'popupClosed',
      // },
    }
  },
  computed: {
    objToaDo: function () {
      return {
        diachi: '',
        toadoLD: this.viDo_LD + ',' + this.kinhDo_LD,
        toadoKC: this.viDo_KC + ',' + this.kinhDo_KC
      }
    },
    getMarkers: function () {
      return this.markers
    },
    getPolylines: function () {
      return this.polylines
    },
    getChiTietDiaChi: function () {
      return {
        tinhThanh_id: this.tinh_id,
        quanHuyen_id: this.quan_id,
        phuongXa_id: this.phuong_id,
        duongPho_id: this.pho_id,
        ap_id: this.ap_id,
        khu_id: this.khu_id,
        dacDiem_id: this.dacdiem_id,
        soNha: this.sonha
      }
    }
  },

  methods: {
    getDsThueBao: async function () {
      var url
      if (this.loaiTimKiem == 0) {
        url = '/web-cabman/danhmuc/toadothuebao/danhba'
      } else {
        url = '/web-cabman/danhmuc/toadothuebao/hopdong'
      }
      this.showThongSoKyThuat = false
      var rs = await this.$root.context.post(url, {ma_tb: this.ma_tb})
      return rs
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
    traCuuThueBao: async function () {
      if (this.ma_tb) {
        try {
          this.loading(true)
          await this.showModalChonThueBao()
          // var rsTC = await this.getDsThueBao();
          // if(rsTC.data.length > 0) {
          //     this.ttThueBao = rsTC.data[0];
          //     var rsTS = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/thongsokythuat', {
          //         db_id: this.ttThueBao.THUEBAO_ID,
          //         kieu: this.loaiTimKiem
          //     })

          //     if(this.ttThueBao.TOADO_LD) {
          //         this.tsktThueBao = rsTS.data;
          //         this.viDo_LD = this.ttThueBao.VIDO_LD;
          //         this.kinhDo_LD = this.ttThueBao.KINHDO_LD;
          //         this.toaDo = this.ttThueBao.TOADO_LD;
          //         this.toaDoTruoc = this.toaDo;
          //         this.viDo_KC = this.ttThueBao.VIDO_KC
          //         this.kinhDo_KC = this.ttThueBao.KINHDO_KC
          //         this.showThongSoKyThuat = true;
          //         this.TaoToaDoVaLienKet(this.ttThueBao);
          //     }
          //     else {
          //         this.$toast.error("Thuê bao chưa có tọa độ");
          //     }
          //     this.isDisableBtnInLyLich = false;
          // }
          // else {
          //     this.$toast.error("Không tìm thấy thuê bao");
          //     this.tsktThueBao = null;
          //     this.viDo_LD = null;
          //     this.kinhDo_LD = null;
          //     this.toaDo = null;
          //     this.toaDoTruoc = null;
          //     this.ttThueBao = null;
          //     this.TaoToaDoVaLienKet(this.ttThueBao);
          // }
        } catch (error) {
          this.$toast.error('Tra cứu lỗi')
        } finally {
          this.loading(false)
        }
      } else {
        this.$refs.maTbInp.focus()
        this.$toast.error('Chưa nhập thông tin tra cứu hoặc chọn loại tra cứu')
      }
    },
    lichSuCapNhat: async function () {
      if (this.ttThueBao) {
        try {
          this.loading(true)
          var rs = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/lichsu', {tb_id: this.ttThueBao.THUEBAO_ID})
          this.lsThueBao = rs.data
          this.modalShowLichSuThueBao = !this.modalShowLichSuThueBao
        } catch (error) {

        } finally {
          this.loading(false)
        }
      } else {
        this.$refs.maTbInp.focus()
        this.$toast.error('Chưa nhập thuê bao tìm kiếm')
      }
    },
    capNhatToaDoThueBao: async function () {
      if (this.ttThueBao) {
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
          this.$toast.error('Cấu trúc tọa độ không đúng định dạng')
          return
        }
        var id = this.ttThueBao.IS_THUEBAO ? this.ttThueBao.THUEBAO_ID : this.ttThueBao.HDTB_ID
        try {
          this.loading(true)
          var rs = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/capnhat', {
            kieu: this.ttThueBao.IS_THUEBAO ? 0 : 1,
            tb_id: id,
            latitude: lat,
            longtitude: lng,
            update_tb_cung_cap: 1,
            force_update: 1,
            daucuoi_id: this.ttThueBao.DAUCUOI_ID
          })
          if (rs.data[0].KETQUA) {
            this.viDo_LD = lat
            this.kinhDo_LD = lng
            this.ttThueBao.VIDO_LD = parseFloat(lat)
            this.ttThueBao.KINHDO_LD = parseFloat(lng)
            this.$toast.success('Cập nhật tọa độ thuê bao thành công')
            this.TaoToaDoVaLienKet(this.ttThueBao)
          } else {
            this.toaDo = this.toaDoTruoc
            this.$toast.error('Cập nhật tọa độ thuê bao thất bại')
          }
        } catch (error) {
          this.$toast.error('Cập nhật tọa độ thuê bao thất bại')
        } finally {
          this.loading(false)
        }
      } else {
        this.$refs.maTbInp.focus()
        this.$toast.error('Chưa nhập thuê bao tìm kiếm')
      }
    },
    ModalChonThueBao_selectedRowChanged: function (dataItem) {
      this.ttThueBao = dataItem
    },
    closeModalInLyLich: function () {
      this.modalShowInLyLich = !this.modalShowInLyLich
    },
    showReport: async function () {
      if (this.isDisableBtnInLyLich) {
        return
      }
      this.modalShowInLyLich = true
      try {
        this.loading(true)
        if (this.ttThueBao.THUEBAO_ID) {
          let rs = await this.getLyLichDayMay(this.ttThueBao.THUEBAO_ID)
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
        this.ma_tb = inputThueBao
        this.loaiTimKiem = inputLoaiTimkiem
        var rsThueBao = await this.getDsThueBao()
        if (rsThueBao.data.length > 0) {
          this.ttThueBao = rsThueBao.data[0]
          await this.showReport()
        } else {
          this.$root.toastError('Không tìm thấy thông tin thuê bao')
        }
      } catch (error) {

      } finally {
        this.loading(false)
      }
    },
    showModalChonThueBao: async function () {
      this.ma_tb = this.ma_tb.trim()
      if (this.ma_tb) {
        try {
          this.$root.loading(true)
          var ds = await this.getDsThueBao()
          this.dsThueBao = ds.data
          if (this.dsThueBao.length == 0) {
            this.$toast.error('Không tìm thấy thuê bao')
            this.tsktThueBao = null
            this.viDo_LD = null
            this.kinhDo_LD = null
            this.toaDo = null
            this.toaDoTruoc = null
            this.ttThueBao = null
          }
          this.$bvModal.show('modal-ChonThueBao')
        } catch (error) {
          this.$toast.error('Tra cứu lỗi')
        } finally {
          this.$root.loading(false)
        }
      } else {
        this.$refs.maTbInp.focus()
        this.$toast.error('Chưa nhập thuê bao tìm kiếm')
      }
    },
    chonThueBao: async function () {
      if (this.ttThueBao) {
        try {
          this.loading(true)
          var rsTS = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/thongsokythuat', {
            db_id: this.ttThueBao.THUEBAO_ID,
            kieu: this.loaiTimKiem
          })
          this.tsktThueBao = rsTS.data
          this.viDo_LD = this.ttThueBao.VIDO_LD
          this.kinhDo_LD = this.ttThueBao.KINHDO_LD
          this.viDo_KC = this.ttThueBao.VIDO_KC
          this.kinhDo_KC = this.ttThueBao.KINHDO_KC
          this.toaDo = this.ttThueBao.TOADO_LD
          this.toaDoTruoc = this.toaDo
          this.showThongSoKyThuat = true
          if (!this.viDo_LD && !this.kinhDo_LD) {
            this.$root.toastError('Thuê bao chưa có tọa độ.')
          }
          this.TaoToaDoVaLienKet(this.ttThueBao)
          this.isDisableBtnInLyLich = false
        } catch (error) {
          console.log(error)
          this.$toast.error('Lấy thông số kỹ thuật lỗi', error)
        } finally {
          this.loading(false)
        }
      } else {
        this.$toast.error('Không tìm thấy thuê bao')
        this.tsktThueBao = null
        this.viDo_LD = null
        this.kinhDo_LD = null
        this.toaDo = null
        this.toaDoTruoc = null
        this.ttThueBao = null
      }
    },
    getToaDo: function (data) {
      this.toaDo = data
      this.capNhatToaDoThueBao()
    },
    taoDuLieuNode: function (id, toado, diachi, icon) {
      let node = new GraphNode()
      try {
        if (toado != null) {
          node.id = id
          if (diachi != '') { node.tooltipText = diachi }
          node.setPosition(toado)
          node.setIcon(icon)
          node.draggable = true
        }
        return node
      } catch (e) {
        return node
      }
    },
    TaoToaDoVaLienKet: function (tttb) {
      this.$refs.mapVNPTCapNhatToaDo.clear()
      if (!tttb) {
        return
      }
      let polylines = []
      this.markers = []
      this.polylines = []
      this.markers.push(
        {
          VIDO: parseFloat(tttb.VIDO_LD),
          KINHDO: parseFloat(tttb.KINHDO_LD),
          LOAI: 1,
          tooltipText: tttb.TEN_TB,
          id: 'THUEBAO_ID_' + tttb.THUEBAO_ID
        },
        {
          VIDO: parseFloat(tttb.VIDO_KC),
          KINHDO: parseFloat(tttb.KINHDO_KC),
          LOAI: 2,
          tooltipText: tttb.KYHIEU_KC,
          id: 'KETCUOI_ID_' + tttb.KETCUOI_ID
        }
      )

      var polyline = new GraphPolyline()
      polyline.path.push({id: tttb.THUEBAO_ID, lat: tttb.VIDO_LD, lng: tttb.KINHDO_LD})
      polyline.path.push({id: tttb.KETCUOI_ID, lat: tttb.VIDO_KC, lng: tttb.KINHDO_KC})
      polyline.id = 'LINK_' + tttb.THUEBAO_ID + '_' + tttb.KETCUOI_ID
      polyline.label = tttb.TEN_TB
      polyline.source = tttb.THUEBAO_ID
      polyline.dest = tttb.KETCUOI_ID
      polyline.style = '0-1'
      polylines.push(polyline)
      this.polylines = polylines
    },
    changetypeMap: function () {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    dblclickMap (event) {
      console.log(event)
    },
    showPopupToaDo: function () {
      if (!this.ttThueBao) {
        this.$root.toastError('Chưa có thông tin thuê bao')
      } else {
        this.$bvModal.show(this.popupToaDo)
      }
    },
    taoToaDoVaKC: function (tttb) {
      this.$refs.map.nodes = []
      this.markers = []
      this.polylines = []
      this.markers.push(
        {
          VIDO: parseFloat(tttb.VIDO_LD),
          KINHDO: parseFloat(tttb.KINHDO_LD),
          LOAI: 1
        },
        {
          VIDO: parseFloat(tttb.VIDO_KC),
          KINHDO: parseFloat(tttb.KINHDO_KC),
          LOAI: 2
        }
      )
      let polyline = {
        path: [
          {id: tttb.THUEBAO_ID, lat: tttb.VIDO_LD, lng: tttb.KINHDO_LD},
          {id: tttb.KETCUOI_ID, lat: tttb.VIDO_KC, lng: tttb.KINHDO_KC}
        ],
        id: 'LINK_' + tttb.THUEBAO_ID + '_' + tttb.KETCUOI_ID,
        label: tttb.TEN_TB == null ? '0 m' : tttb.TEN_TB + ' m',
        source: tttb.THUEBAO_ID,
        dest: tttb.KETCUOI_ID,
        style: '0-1'
      }
      this.polylines.push(polyline)
    },
    timKiem: function () {
      try {
        this.loading(true)
        this.$refs.mapVNPTCapNhatToaDo.searchLocation(this.query)
      } catch (error) {
        this.$root.toastError('Lỗi phát sinh: ' + error)
      } finally {
        this.loading(false)
      }
    },
    closeModalDiaChi: function (data) {
      this.isShowDiaChi = !this.isShowDiaChi
      if (data.isUpdated) {
        console.log(data.chiTietDiaChi)
        this.query = data.diaChi
        this.tinh_id = data.chiTietDiaChi.tinhThanh_id
        this.quan_id = data.chiTietDiaChi.quanHuyen_id
        this.phuong_id = data.chiTietDiaChi.phuongXa_id
        this.pho_id = data.chiTietDiaChi.duongPho_id
        this.ap_id = data.chiTietDiaChi.ap_id
        this.khu_id = data.chiTietDiaChi.khu_id
        this.dacdiem_id = data.chiTietDiaChi.dacDiem_id
        this.sonha = data.chiTietDiaChi.soNha
        this.timKiem()
      }
    },
    simpleButton1_Click: function () {
      // this.popupComponentName = "popup-SearchAccount";
      this.$bvModal.show('popupComponents')
    },
    popupClosed: function (data) {
      if (data) {
        this.ma_tb = data.ma_tb
        this.traCuuThueBao()
      }
      this.$bvModal.hide('popupComponents')
    }
  },
  watch: {
  },
  mounted: function () {
    this.$refs.maTbInp.focus()
  }
}
</script>

<style>
a.disable-btn-capnhat-toado-tb {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.capnhat-toado-thuebao .page-content {
  position: unset !important;
}

</style>
