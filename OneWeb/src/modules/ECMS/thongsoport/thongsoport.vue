<template src="./thongsoport.html"></template>
<script>

import { required } from 'vuelidate/lib/validators'
import PortLogic from './portlogic/portlogic'
import TaoDaiPort from './taodaiport/taodaiport'
import modal from './../vci-vpi/modal/index'
import BssErrorMarker from '@/components/BssErrorMarker'


export default {
  name: 'ThongSoPort',
  components: { BssErrorMarker, PortLogic, TaoDaiPort, modal },
  data: function () {
    return {
      DONVIQL_LD: 3,
      TRAM_VT: 5,
      DONVI_ID: 0,
      GPON: 3,
      DSLAM: 1,
      MANE: 17,
      TRAM_PORT: 39,
      TT_PORT: '',
      dsDonViQuanLy: [],
      dsTrangThaiPort: [],
      dsRackShelf: [],
      dsRackShelf1: [],
      dsTramThiCong: [],
      dsTramTB: [],
      dsCheckDsLam: [],
      dsDsLamTinh: [],
      dsVciDaGan: [],
      dsvciChuaGan: [],
      dsLoaiHinhDaGan: [],
      dsBras: [],
      dslamVdc: [],
      dsCard: [],
      dsPort: [],
      dataSinhPass: [],
      dsPortSelected: [],
      dsPortSelectedWithTT: [],
      dsVciDaGanSelected: [],
      dsVciChuaGanSelected: [],
      disableSelfid01: false,
      disableSelfid02: false,
      disableSelfid03: false,
      disableSelfid04: false,
      disableCard: false,
      isDisableBtnNhapMoi: false,
      isDisableBtnCapNhat: false,
      isDisableBtnHuy: false,
      isDisableBtnXoa: false,
      selfid01: '',
      selfid02: '',
      selfid03: '',
      selfid04: '',
      system: '',
      portMdf: '',
      vlanInt: '',
      slot: '',
      port: '',
      rack: '',
      shelf: '',
      ghiChu: '',
      ketQuaSinhOnu: '',
      modalShowPortLogic: false,
      modalShowTaoDaiport: false,
      inforPort: {},
      dataSelected: {
        dslamThamSo: null,
        vdcThamSo: null,
        donViQuanLy: null,
        trangthai: null,
        rackShelfId: null,
        rackShelfValue: null,
        tramThiCong: null,
        tramThietBi: null,
        dslamTinh: null,
        vciDaGan: null,
        vciChuaGan: null,
        loaiHinhDaGan: null,
        bras: null,
        dslamVdc: null,
        tenDslamVdc: null,
        loaiTBiId: null,
        cardId: null,
        portId: null
      }
    }
  },
  created: function () {
    // this.getDonViQl()
  },
  methods: {
    getDonViQl: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.post(
          `/web-ecms/thongsoport/dsdonviquanly?donviId=${this.DONVI_ID}&loaidvId=${this.DONVIQL_LD}`
          // `/web-ecms/thongsoport/dsdonviquanly?donviId=0&loaidvId=3`
        )
        if (rs.data.length > 0) {
          this.dsDonViQuanLy = rs.data
          this.dataSelected.donViQuanLy = rs.data[0].DONVI_ID
          await this.getTramTc()
        } else {
          this.dataSelected.donViQuanLy = 0
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getTramTc: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayTramTc?donviId=${this.dataSelected.donViQuanLy}&loaidvId=${this.TRAM_VT}`
        )
        this.dsTramThiCong = rs.data
        if (rs.data.length > 0) {
          this.dataSelected.tramThiCong = rs.data[0].DONVI_ID
          await this.getTramTb()
        } else {
          this.dataSelected.tramThiCong = 0
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getTramTb: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayTramTb?donviId=${this.dataSelected.tramThiCong}&loaidvId=${this.TRAM_PORT}`
        )
        this.dsTramTB = rs.data
        if (rs.data.length > 0) {
          this.dataSelected.tramThietBi = rs.data[0].DONVI_ID
        } else {
          this.dataSelected.tramThietBi = 0
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getTrangThaiPort: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          '/web-ecms/thongsoport/dslaytrangthaiport'
        )
        this.dsTrangThaiPort = rs.data
        if (rs.data) {
          this.dataSelected.trangthai = rs.data[0].TT_PORT_ID
        } else {
          this.dataSelected.trangthai = 0
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getRackShelf: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          '/web-ecms/thongsoport/dslayrackshelf'
        )
        this.dsRackShelf = rs.data
        this.dsRackShelf1 = rs.data

        if (rs.data) {
          this.dataSelected.rackShelfId = rs.data[0].RACK_ID
        } else {
          this.dataSelected.rackShelfId = 0
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }

    }, getBrasAdsl: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          '/web-ecms/thongsoport/dslaybrasadsl'
        )
        if (rs.data.length > 0) {
          this.dsBras = rs.data
          this.dataSelected.bras = rs.data[0].BRAS_ID
        } else {
          this.dsBras = []
          this.dataSelected.bras = 0
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }

    }, getDslamTinhTheoTramTbi: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.post(
          `/web-ecms/thongsoport/dsLayDsLamTinhTheoTramTbi?donViId=${this.dataSelected.tramThietBi}`
        )
        if (rs.data.length > 0) {
          this.dsDsLamTinh = rs.data
          this.dataSelected.dslamTinh = rs.data[0].DSLAM_ID
          this.dataSelected.dslamThamSo = rs.data[0].THAMSO
          this.system = rs.data[0].SYSTEM
          await this.hienThiDanhSach()
          if (this.dataSelected.loaiTBiId === this.GPON || this.dataSelected.loaiTBiId === this.DSLAM || this.dataSelected.loaiTBiId === this.MANE) {
            await this.getDsCard(this.dataSelected.dslamTinh, this.dataSelected.loaiTBiId)
          } else {
            this.dsCard = []
          }
        } else {
          this.dsPort = []
          this.dsDsLamTinh = []
          this.dsLoaiHinhDaGan = []
          this.dsVciDaGan = []
          this.dsCard = []
          this.dataSelected.dslamTinh = 0
          this.dataSelected.dslamThamSo = 0

        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, checkDsLam: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayDslam?donviId=${this.dataSelected.tramThietBi}`
        )
        if (rs.data.length > 0) {
          this.dsCheckDsLam = rs.data
        } else {
          this.dsCheckDsLam = []
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, hienThiDanhSachVciVpi: async function (portId) {
      if (this.dataSelected.dslamTinh) {
        await this.getVciChuaGan(this.dataSelected.dslamTinh, portId)
        await this.getVciDaGan(this.dataSelected.dslamTinh, portId)
      }

    },
    addAllToDsChuaGan: async function () {
      if (!this.dataSelected.portId) {
        this.$root.toastError("Bạn chưa chọn port.")
        return
      }
      let input = this.dsVciDaGan.map(val => val.RP_ID)
      if (input) {
        if ((await this.xoaVciVpiPort(input)) === 1) {
          this.dsvciChuaGan = this.dsvciChuaGan.concat(this.dsVciDaGan);
          this.dsVciDaGan = [];
          this.dsVciDaGanSelected = [];
        }
      }

    },
    addAllToDsDaGan: async function () {
      if (!this.dataSelected.portId) {
        this.$root.toastError("Bạn chưa chọn port.")
        return
      }
      if (!this.portMdf) {
        this.$root.toastError("Port mdf không được để trống.")
        return
      }
      let data = [];

      for (let i = 0; i < this.dsvciChuaGan.length; i++) {
        let vciVpi = this.dsvciChuaGan[i];

        let obj = {
          p_port_id: this.dataSelected.portId,
          p_vci_vpi_id: vciVpi.VCI_VPI_ID,
          p_trangthai: 1,
          p_port_mdf: this.portMdf
        }
        data.push(obj)

      }

      if ((await this.themVciVpiPort(data)) === 1) {
        this.dsVciDaGan = this.dsVciDaGan.concat(this.dsvciChuaGan);
        this.dsvciChuaGan = [];
        this.dsVciChuaGanSelected = [];

      }


    },
    addToDsChuaGan: async function () {
      var arr = [];
      if (!this.dataSelected.portId) {
        this.$root.toastError("Bạn chưa chọn port.")
        return
      }
      this.dsVciDaGanSelected.map(val => {
        var idx = this.dsVciDaGan.findIndex(v => v.VCI_VPI_ID == val);
        if (idx >= 0) {
          arr.push(this.dsVciDaGan[idx]);
          this.dsVciDaGan = this.dsVciDaGan.filter(v => v.VCI_VPI_ID != val);
        }
      })
      this.dsvciChuaGan = this.dsvciChuaGan.concat(arr);
      var data = this.dsVciDaGanSelected.map(val => val.VCI_VPI_ID)
      console.log(this.dsVciDaGanSelected)
      await this.xoaVciVpiPort(this.dsVciDaGanSelected)
    },
    addToDsDaGan: async function () {
      var arr = [];
      if (!this.dataSelected.portId) {
        this.$root.toastError("Bạn chưa chọn port.")
        return
      }
      if (!this.portMdf) {
        this.$root.toastError("Port mdf không được để trống.")
        return
      }
      this.dsVciChuaGanSelected.map(val => {
        var idx = this.dsvciChuaGan.findIndex(v => v.VCI_VPI_ID == val);
        if (idx >= 0) {
          arr.push(this.dsvciChuaGan[idx]);
          this.dsvciChuaGan = this.dsvciChuaGan.filter(v => v.VCI_VPI_ID != val);
        }
      })
      this.dsVciDaGan = this.dsVciDaGan.concat(arr);
      let data = [];

      for (let i = 0; i < arr.length; i++) {
        let vciVpi = arr[i];

        let obj = {
          p_port_id: this.dataSelected.portId,
          p_vci_vpi_id: vciVpi.VCI_VPI_ID,
          p_trangthai: 1,
          p_port_mdf: this.portMdf
        }
        data.push(obj)

      }

      await this.themVciVpiPort(data)
    }, themVciVpiPort: async function (data) {

      let kq = 0

      try {
        this.loading(true)
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/themVciVpiChoPort', data
        )
        if (rs.data) {
          await this.hienThiDanhSachVciVpi(this.dataSelected.portId)
          kq = 1
        }

      } catch (error) {

      } finally {
        this.loading(false)
        this.dsVciChuaGanSelected = []
        return kq
      }
    }, xoaVciVpiPort: async function (data) {
      let kq = 0

      try {
        this.loading(true)
        var rs = await this.$root.context.post(
          `/web-ecms/thongsoport/xoaVciVpiChoPort?portIds=${data}`
        )
        if (rs.data) {

          await this.hienThiDanhSachVciVpi(this.dataSelected.portId)
          kq = 1
        }

      } catch (error) {

      } finally {
        this.loading(false)
        this.dsVciDaGanSelected = [];
        return kq
      }
    }
    , getVciDaGan: async function (dslamID, portId) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayVciVpiDaGan?dslamId=${dslamID}&portId=${portId}`
        )

        if (rs.data.length > 0) {
          this.dsVciDaGan = rs.data
          this.dataSelected.vciDaGan = rs.data[0].VCI_VPI_ID
          await this.getDsLoaiHinhDaGan(rs.data[0].RP_ID)
        } else {
          this.dsVciDaGan = []
          this.dataSelected.vciDaGan = 0
          //  this.getDsLoaiHinhDaGan(0)
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getVciChuaGan: async function (dslamID, portId) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayVciVpiChuaGan?dslamId=${dslamID}&portId=${portId}`
        )
        if (rs.data.length > 0) {
          this.dsvciChuaGan = rs.data
          this.dataSelected.vciChuaGan = rs.data[0].VCI_VPI_ID
        } else {
          this.dsvciChuaGan = []
          this.dataSelected.vciChuaGan = null
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getDsLoaiHinhDaGan: async function (vrp_id) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayLoaiHinhDaGan?vrpId=${vrp_id}`
        )
        if (rs.data.length > 0) {
          this.dsLoaiHinhDaGan = rs.data
          this.dataSelected.loaiHinhDaGan = rs.data[0].LOAITB_ID
        } else {
          this.dsLoaiHinhDaGan = []
          this.dataSelected.loaiHinhDaGan = null
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, selectedRowChanged: function () {

    }, VciDaGanChange: async function (index, dataItem) {
      if (dataItem) {
        this.dataSelected.vciDaGan = dataItem.VCI_VPI_ID
        this.portMdf = dataItem.PORT_MDF
        if (dataItem.RP_ID) {
          await this.getDsLoaiHinhDaGan(dataItem.RP_ID)
        }
      }
    },
    selectedPort: async function (data) {
      console.log(" vô lần 1 selectport", data)
      this.dsPortSelected = data;
      this.dsPortSelectedWithTT = this.dsPort.filter((item) =>
        this.dsPortSelected.includes(item.PORT_ID)
      ).map(item =>
        item.TT_PORT_ID
      )
      for (const item of this.dsPort) {
        if (item.PORT_ID == data) {
          await this.danhSachPortChange(0, item)
        }
      }

    }, selectVciDaGan: function (data) {
      this.dsVciDaGanSelected = data;
      this.dataSelected.vciDaGan = data[0]
    }, selectVciChuaGan: function (data) {
      this.dsVciChuaGanSelected = data;
    }, async danhSachPortChange(rowIndex, dataItem) {
      console.log(dataItem.PORT_ID)
      console.log("danhSachPortChange", dataItem)
      if (dataItem) {
        this.port = dataItem.PORT
        this.system = dataItem.SYSTEM
        this.slot = dataItem.SLOT
        this.selfid01 = dataItem.SELFID01
        this.selfid02 = dataItem.SELFID02
        this.selfid03 = dataItem.SELFID03
        this.selfid04 = dataItem.SELFID04
        this.ghiChu = dataItem.GHICHU
        this.rack = dataItem.RACK
        this.shelf = dataItem.SHELF
        this.dataSelected.portId = dataItem.PORT_ID
        this.dataSelected.rackShelfId = dataItem.RACK_ID
        this.vlanInt = dataItem.VLAN_INT
        this.dataSelected.rackShelf = dataItem.RACK_ID
        this.dataSelected.trangthai = dataItem.TT_PORT_ID
        this.dataSelected.dslamVdc = dataItem.DSLAM_VDC_ID
        if (dataItem.PORT_ID) {
          await this.hienThiDanhSachVciVpi(dataItem.PORT_ID)
        }

      }

    }, getDsCard: async function (dslamId, loaiTbId) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayDsCard?dslamId=${dslamId}&loaiTbId=${loaiTbId}`
        )
        if (rs.data.length > 0) {
          this.dsCard = rs.data
          this.dataSelected.cardId = rs.data[0].CARD_ID
        } else {
          this.dsCard = []
          this.dataSelected.cardId = null
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, hienThiDanhSach: async function () {
      await this.getDsPort(this.dataSelected.tramThietBi, this.TT_PORT, this.dataSelected.dslamTinh)
      if (this.dsPort.length <= 0) {
        await this.hienThiDanhSachVciVpi(0)
      }

      var rs = await this.$root.context.get(
        `/web-ecms/thongsoport/dsLayTTDslam?dslamId=${this.dataSelected.dslamTinh}`
      )
      if (rs.data) {
        if (rs.data[0].BRAS_ID) {
          this.dataSelected.bras = rs.data[0].BRAS_ID
        }
      }

    }, getDsPort: async function (donviId, trangthaiPort, dslamId) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayDsPort?donviId=${donviId}&portId=${trangthaiPort}&dslamId=${dslamId}`
          // `/web-ecms/thongsoport/dsLayDsPort?donviId=${835}&portId=${trangthaiPort}&dslamId=${40288}`
        )
        if (rs.data) {
          this.dsPort = rs.data
          this.dataSelected.portId = rs.data[0].PORT_ID
          if (this.dataSelected.portId) {
            await this.hienThiDanhSachVciVpi(this.dataSelected.portId)
          }
        } else {
          this.dsPort = []
          this.dataSelected.portId = null
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getDslamVdc: async function (brasId) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayDsLamVdc?brasId=${brasId}&kieu=2`
        )
        if (rs.data.length > 0) {
          this.dslamVdc = rs.data
          this.dataSelected.dslamVdc = rs.data[0].DSLAM_ID
          rs.data.forEach((item) => {
            if (item.THAMSO === this.dataSelected.dslamThamSo) {
              this.dataSelected.dslamVdc = item.DSLAM_ID
              return
            }
          })
        } else {
          this.dslamVdc = []
          this.dataSelected.dslamVdc = 0
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, sinhPassOnu: async function (portId) {
      try {
        var rs = await this.$root.context.post(
          `/web-ecms/thongsoport/sinhPassOnuPortId?portId=${portId}`
        ).then((res) => {
          this.dataSinhPass.push(true)
          console.log('res', res)
          this.$toast.success('Sinh Passonu thành công.')
        }).catch((err) => {
          this.$toast.error('Sinh Passonu thất bại.')
          this.dataSinhPass.push(false)
        })

      } catch (error) {
        this.$toast.error('Sinh Passonu thất bại1.')
        this.dataSinhPass.push(false)
      } finally {
        this.loading(false)
      }

    }, capNhatPortMdf: async function () {
      try {
        var rs = await this.$root.context.post(
          `/web-ecms/thongsoport/updatePortMdf?vrpDaGan=${this.dataSelected.vciDaGan}&portMdf=${this.portMdf}`
        )
        if (rs.data) {
          this.$toast.success('Cập nhật port mdf thành công.')
        }
        await this.getVciChuaGan(this.dataSelected.dslamTinh, this.dataSelected.portId)
        await this.getVciDaGan(this.dataSelected.dslamTinh, this.dataSelected.portId)
      } catch (error) {
        this.$toast.error('Cập nhật port mdf thất bại.' + error)
      } finally {
        this.loading(false)
      }

    }, kiemTraThongTinDanhBa: async function (dsPortID) {
      try {
        let data = {
          p_port: dsPortID
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/kiemTraTTDanhBa', data
        )
        if (rs.data.length > 0) {
          this.$toast.warning('Đang có thuê bao ' + rs.data[0].MA_TB + ' trong danh bạ sử dụng port . Bạn không được sửa thông tin port  ')
          return 1
        }
      } catch (error) {
        console.log(error)
        return 1
      } finally {
        this.loading(false)
      }

    }, kiemTraThongTinHopDong: async function (dsPortID) {
      try {
        let data = {
          p_port: dsPortID
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/kiemTraTTHopDong', data
        )
        if (rs.data.length > 0) {
          this.$toast.warning('Đang có thuê bao ' + rs.data[0].MA_TB + ' trong  hợp đồng sử dụng port. Bạn không được sửa thông tin port.')
          return 1
        }
      } catch (error) {
        console.log(error)
        return 1
      } finally {
        this.loading(false)
      }

    }, updateTTChuaSDv2: async function (dsPortID) {
      try {
        let ketqua = '0'
        if (await this.kiemTraThongTinDanhBa(this.dsPortSelected) === 1) {
          ketqua = '1'
        }

        if (await this.kiemTraThongTinHopDong(this.dsPortSelected) === 1) {
          ketqua = '1'
        }

        //return;
        if (ketqua === '0') {
          let data = {
          p_port: dsPortID
        }
          var rs = await this.$root.context.post(
            '/web-ecms/thongsoport/capNhatTrangThaiPortV3', {
              ids: dsPortID,
              trangThaiID: this.dataSelected.trangthai
            }
          )
          if (rs.data) {
            this.$toast.success('Cập nhật trạng thái port thành công !')
            await this.hienThiDanhSach()
          }
        }

      } catch (error) {
        this.$toast.error(error)
      } finally {
        this.loading(false)
      }

    },

    updateTTChuaSD: async function (dsPortID) {

      try {
        let ketqua = '0'
        if (await this.kiemTraThongTinDanhBa(this.dsPortSelected) === 1) {
          ketqua = '1'
        }

        if (await this.kiemTraThongTinHopDong(this.dsPortSelected) === 1) {
          ketqua = '1'
        }

        let data = {
          p_port: dsPortID
        }
        //return;
        if (ketqua === '0') {
          var rs = await this.$root.context.post(
            '/web-ecms/thongsoport/updateTTportForm', data
          )
          if (rs.data) {
            this.$toast.success('Cập nhật trạng thái port thành công !')
            await this.hienThiDanhSach()
          }
        }

      } catch (error) {
        this.$toast.error(error)
      } finally {
        this.loading(false)
      }

    }, updateVcdId: async function (dsPortID) {

      try {
        let data = {
          dslamVcdId: this.dataSelected.dslamVdc,
          dsPortId: dsPortID
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/updateVcdIdPort', data
        )
        if (rs.data) {
          this.$toast.success('Đã cập nhật port theo Dslam Visa thành công!')
          await this.hienThiDanhSach()
        }

      } catch (error) {
        this.$toast.error(error)
      } finally {
        this.loading(false)
      }

    },
    statusBtn(nhapMoi, ghiLai, huyBo, xoa) {
      this.isDisableBtnNhapMoi = nhapMoi
      this.isDisableBtnCapNhat = ghiLai
      this.isDisableBtnHuy = huyBo
      // this.isDisableBtnXoa = xoa
    }, async btnNhapMoi() {
      if (!this.isDisableBtnNhapMoi) {
        this.statusBtn(true, false, false, true)
        this.isDisableBtnXoa = true
        await this.hienThiDanhSachVciVpi(0)
        await this.getDsLoaiHinhDaGan(0)
        this.port = ''
        this.slot = ''
        this.portMdf = ''
      }

    }, btnCapNhat() {
      if (!this.isDisableBtnCapNhat) {
        this.saveAs()
      }
    }, btnHuy() {
      if (!this.isDisableBtnHuy) {
        this.statusBtn(false, true, true, true)
        this.slot = ''
        this.port = ''
        this.portMdf = ''
      }
    }, btnXoa: async function () {
      if (!this.isDisableBtnXoa) {
        if (this.dsPortSelected.length <= 0) {
          this.$toast.error('Bạn chưa chọn port trong danh sách')
          return
        }

        if (this.dsPortSelectedWithTT.includes(2) || this.dsPortSelectedWithTT.includes(3)) {
          this.$toast.error('Port đang được sử dụng. Bạn không được phép xóa!')
          return
        }

        try {
          this.statusBtn(false, false, false, false)
          this.$bvModal.msgBoxConfirm('Bạn có muốn xóa dữ liệu không ?',
            {
              title: 'Xác nhận hành động',
              centered: true,
              size: 'sm'
            }).then(async (value) => {
              if (value) {
                try {
                  this.loading(true)
                  // if (!this.dataSelected.portId) {
                  //   this.$toast.error('Không tồn tại Port')
                  //   return
                  // }
                  let rs = await this.$root.context.post(
                    //`/web-ecms/thongsoport/xoaPort?portId=${this.dsPortSelected.toString()}`
                    "/web-ecms/thongsoport/xoaPortV2",
                    { ids: this.dsPortSelected }
                  ).then(async (rs) => {
                    if (rs.error_code === 'BSS-00000000') {
                      this.$toast.success('Xoá thành công!')
                      await this.hienThiDanhSach()
                    } else {
                      this.$toast.error('Có lỗi xảy ra')
                    }
                  })
                } catch (error) {
                  this.$toast.error('Có lỗi xảy ra');
                } finally {
                  this.loading(false)
                }
              }
            })
        } catch (error) {

        } finally {
        }

      }
    }, saveAs: async function () {
      if (this.validateData()) {
        return
      }
      if (this.isDisableBtnNhapMoi) {
        await this.themMoi()
      } else {
        await this.capNhat()
      }
      // string s = pvi.Sinh_Pass_Onu_Port_id(port_id);
      // Message_Box.ShowTB("Cập nhật dữ liệu thành công !");
      // HienThiDanhSach(Convert.ToInt32(cboTramTB.SelectedValue), tt_port, dslam_id);

    }, themMoi: async function () {
      let port = {
        p_port: this.port,
        p_slot: this.slot,
        p_dslam_id: this.dataSelected.dslamTinh,
        p_rack_id: this.dataSelected.rackShelfId,
        p_tt_port_id: this.dataSelected.trangthai,
        p_selfid01: this.selfid01,
        p_selfid02: this.selfid02,
        p_selfid03: this.selfid03,
        p_selfid04: this.selfid04,
        p_vlan_int: this.vlanInt,
        p_dslam_vdc_id: this.dataSelected.dslamVdc,
        p_ghichu: this.ghiChu
      }

      try {
        this.loading(true)
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/themPort', port
        )
        await this.hienThiDanhSach()
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Thêm mới thành công!')
          this.statusBtn(false, false, false, false)

        } else {
          this.statusBtn(true, false, false, false)
          this.$toast.error('Có lỗi xảy ra')
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }

    }, capNhat: async function () {
      try {
        var rs
        var lengthPortSelected = this.dsPortSelected.length
        var countPortUpdateSuccess = this.dsPortSelected.length
        this.loading(true)
        for (const item of this.dsPort) {
          for (const portId of this.dsPortSelected) {
            if (item.PORT_ID == portId) {
              // this.port = item.PORT
              // this.system = item.SYSTEM
              // this.slot = item.SLOT
              // this.selfid01 = item.SELFID01
              // this.selfid02 = item.SELFID02
              // this.selfid03 = item.SELFID03
              // this.selfid04 = item.SELFID04
              // this.ghiChu = item.GHICHU
              // this.rack = item.RACK
              // this.shelf = item.SHELF
              this.dataSelected.portId = item.PORT_ID
              // this.dataSelected.rackShelfId = item.RACK_ID
              // // this.vlanInt = item.VLAN_INT
              // this.dataSelected.rackShelf = item.RACK_ID
              // this.dataSelected.trangthai = item.TT_PORT_ID
              // this.dataSelected.dslamVdc = item.DSLAM_VDC_ID
              let port = {
                p_port_id: this.dataSelected.portId,
                p_port: this.port,
                p_slot: this.slot,
                p_dslam_id: this.dataSelected.dslamTinh,
                p_rack_id: this.dataSelected.rackShelfId,
                // p_tt_port_id: this.dataSelected.trangthai,
                p_selfid01: this.selfid01,
                p_selfid02: this.selfid02,
                p_selfid03: this.selfid03,
                p_selfid04: this.selfid04,
                p_vlan_int: this.vlanInt,
                p_dslam_vdc_id: this.dataSelected.dslamVdc,
                p_ghichu: this.ghiChu
              }
              rs = await this.$root.context.post(
                '/web-ecms/thongsoport/capnhatPort', port
              )
              if (rs.error_code !== 'BSS-00000000') {
                countPortUpdateSuccess = countPortUpdateSuccess - 1
              }
            }
          }
        }
        // this.loading(true)
        //
        // let portMdf = {
        //   p_port_id: this.dataSelected.portId,
        //   p_port_mdf: this.portMdf,
        //   P_VCI_VPI_ID: this.,
        // }
        // var rsPort = await this.$root.context.post(
        //   '/web-ecms/thongsoport/updatePortMdf', portMdf
        // )
        await this.hienThiDanhSach()
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success(`Đã cập nhật ${countPortUpdateSuccess}/${lengthPortSelected} port thành công!`)
          this.statusBtn(false, false, false, false)
        } else {
          this.statusBtn(true, false, false, false)
          Object.keys(rs.data).forEach(item => {
            this.$toast.error(`${rs.data[`${item}`]}`)
          })
        }

      } catch (error) {
        // if (rs.error_code !== 'BSS-00000000') {
        //   this.statusBtn(true, false, false, false)
        //   Object.keys(rs.data).forEach(item=>{
        //     this.$toast.error(`${rs.data[item]}`)
        //   })
        // }
        // console.log(error.error);
        // this.statusBtn(true, false, false, false)
        this.$toast.error('Có lỗi xảy ra')
      } finally {
        this.loading(false)
      }

    }, validateData() {
      this.$v.$touch()
      let errorMessage = []

      if (this.$v.slot.$error) {


        if (!this.slot.trim()) {
          errorMessage.unshift('Trường slot bắt buộc nhập dữ liệu.')
        }
      }

      if (this.$v.port.$error) {

        if (!this.port.trim()) {
          errorMessage.unshift('Trường port bắt buộc nhập dữ liệu.')
        }
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    }, rackShelfChange: async function (selectedIndex) {
      this.dataSelected.rackShelfId = this.dsRackShelf[selectedIndex].RACK_ID
      this.dataSelected.rackShelfValue = this.dsRackShelf[selectedIndex].RACK_SHELF
      if (this.dataSelected.rackShelfValue) {
        this.rack = this.dataSelected.rackShelfValue.split(' - ')[0]
        this.shelf = this.dataSelected.rackShelfValue.split(' - ')[1]
      }
    }, btnPortLogic: function () {
      // console.log(this.dataSelected.dslamTinh)            
      let a = this.$refs.grid_dsPort.getSelectedRecords();
      let b = [];
      for (let i = 0; i < a.length; i++) {
        const element = a[i];
        b.push(element.PORT_ID);
      }
      this.$refs.popupPortLogic.portIdFilter = b.join(";");

      if (this.dataSelected.dslamTinh) {
        this.modalShowPortLogic = !this.modalShowPortLogic
      } else {
        this.$toast.error('Dslam Tỉnh trống')
      }
      this.dslamId = this.dataSelected.dslamTinh
    }, btnTaoDaiPort: function () {
      this.inforPort = {
        DONVI_ID: this.dataSelected.donViQuanLy,
        TRAM_TC_ID: this.dataSelected.tramThiCong,
        TRAM_TB_ID: this.dataSelected.tramThietBi,
        DSLAM_TINH_ID: this.dataSelected.dslamTinh,
        BRAS_ID: this.dataSelected.bras,
        DSLAM_VDC: this.dataSelected.dslamVdc,
        CARD_ID: this.dataSelected.cardId,
        RACK_ID: this.dataSelected.rackShelfId
      }
      this.$bvModal.show('taodaiport');

      // this.modalShowTaoDaiport = !this.modalShowTaoDaiport
    }, btnShowVpiVci: function () {
      this.inforPort = {
        DONVI_ID: this.dataSelected.donViQuanLy,
        TRAM_TC_ID: this.dataSelected.tramThiCong,
        TRAM_TB_ID: this.dataSelected.tramThietBi,
        DSLAM_TINH_ID: this.dataSelected.dslamTinh,
        BRAS_ID: this.dataSelected.bras,
        DSLAM_VDC: this.dataSelected.dslamVdc,
        CARD_ID: this.dataSelected.cardId,
        RACK_ID: this.dataSelected.rackShelfId
      }
      this.$bvModal.show('popupTCTTTB');
      // this.modalShowTaoDaiport = !this.modalShowTaoDaiport
    }, btnSinhPortOnu: async function () {
      if (this.dsPortSelected.length <= 0) {
        this.$toast.warning('Bạn chưa chọn port trong danh sách.')
        return
      }
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn sinh Pass cho các port đã chọn không ?',
        {
          title: 'Xác nhận hành động',
          centered: true,
          size: 'sm'
        }).then(async (value) => {
          if (value) {
            this.dataSinhPass = []
            for (const value1 of this.dsPortSelected) {
            }

            await this.sinhPassOnu(this.dsPortSelected[this.dsPortSelected.length - 1])

            // console.log(this.dataSinhPass)
            // console.log(this.dataSinhPass.includes(false))
            // console.log(!this.dataSinhPass.includes(false))
            console.log('this.dsPortSelected', this.dsPortSelected);
            // if(!this.dataSinhPass.includes(false)){
            //   this.$toast.error('Sinh Passonu thất bại!')
            // }else {
            //   this.$toast.success('Sinh Passonu thành công.')
            // }
            await this.hienThiDanhSach()
          }
        })
    }, btnCapNhatTT: async function () {
      if (this.dsPortSelected.length <= 0) {
        this.$toast.warning('Bạn chưa chọn port trong danh sách.')
        return
      }
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn cập nhật trạng thái port ?',
        {
          title: 'Xác nhận hành động',
          centered: true,
          size: 'sm'
        }).then(async (value) => {
          if (value) {
            await this.updateTTChuaSDv2(this.dsPortSelected)

          }
        })

    }, btnCapNhatVcd: async function () {
      if (this.dsPortSelected.length <= 0) {
        this.$toast.warning('Bạn chưa chọn port trong danh sách.')
        return
      }
      if (!this.dataSelected.dslamVdc) {
        this.$toast.warning('Bạn chưa chọn dslam Vdc id.')
        return
      }
      if (this.dataSelected.dslamVdc) {
        window.dslamVdc = this.dslamVdc
        window.dslamVdcdataSelected = this.dataSelected.dslamVdc
        this.dataSelected.tenDslamVdc = this.dslamVdc.filter(item => item.DSLAM_ID === parseInt(this.dataSelected.dslamVdc))[0].TENDSLAM
      }
      this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật danh sách port theo Dslam Visa: ' + this.dataSelected.tenDslamVdc,
        {
          title: 'Xác nhận hành động',
          centered: true,
          size: 'sm'
        }).then(async (value) => {
          if (value) {
            await this.updateVcdId(this.dsPortSelected)

          }
        })

    }, btnPortMdf: async function () {
      if (!this.dataSelected.vciDaGan) {
        this.$toast.warning('Không tồn tại Vci Vpi đã gán.')
        return
      }
      if (!this.portMdf) {
        this.$toast.warning('Hãy điền port mdf.')
        return
      }
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn cập nhật port mdf không?',
        {
          title: 'Xác nhận hành động',
          centered: true,
          size: 'sm'
        }).then(async (value) => {
          if (value) {
            await this.capNhatPortMdf()
          }
        })
    }, dslamTinhChange: async function (selectedIndex, value) {
      console.log("dslamTinhChange.selectedIndex", selectedIndex);
      console.log("dslamTinhChange.value", value);
      if (this.dsDsLamTinh[selectedIndex]) {
        this.dataSelected.loaiTBiId = this.dsDsLamTinh[selectedIndex].LOAITBI_ID
        this.system = this.dsDsLamTinh[selectedIndex].SYSTEM
        this.dataSelected.dslamThamSo = this.dsDsLamTinh[selectedIndex].THAMSO
        console.log('tham so ', this.dataSelected.dslamThamSo)
      } else {
        this.dataSelected.loaiTBiId = 0
        this.dataSelected.dslamThamSo = 0
      }
      console.log('this.dsDsLamTinh', this.dsDsLamTinh[selectedIndex]);
      await this.hienThiDanhSach()
      if (this.dataSelected.loaiTBiId === this.GPON || this.dataSelected.loaiTBiId === this.DSLAM || this.dataSelected.loaiTBiId === this.MANE) {
        await this.getDsCard(this.dataSelected.dslamTinh, this.dataSelected.loaiTBiId)
      } else {
        this.dsCard = []
      }

      if (this.dataSelected.dslamTinh) {

        let thamso = this.dsDsLamTinh[selectedIndex].THAMSO
        window.thamso = thamso
        if (thamso === 9) {
          this.disableSelfid01 = false
          this.disableSelfid02 = false
          this.disableSelfid03 = false
          this.disableSelfid04 = false
        } else {
          this.disableSelfid01 = true
          this.disableSelfid02 = true
          this.disableSelfid03 = true
          this.disableSelfid04 = true
        }
        this.selfid01 = ''
        this.selfid02 = ''
        this.selfid03 = ''
        this.selfid04 = ''

      }
      if (this.dslamVdc) {
        this.dataSelected.dslamVdc = this.dslamVdc[0].DSLAM_ID
        this.dslamVdc.forEach((item) => {
          if (item.THAMSO === this.dataSelected.dslamThamSo) {
            this.dataSelected.dslamVdc = item.DSLAM_ID
            return
          }
        })
      }

    }, dslamTinh_Changed: async function () {

      let currentDSLAM = this.dsDsLamTinh.find(x => x.DSLAM_ID == this.dataSelected.dslamTinh);
      if (currentDSLAM) {
        this.dataSelected.loaiTBiId = currentDSLAM.LOAITBI_ID
        this.system = currentDSLAM.SYSTEM
        this.dataSelected.dslamThamSo = currentDSLAM.THAMSO
      } else {
        this.dataSelected.loaiTBiId = 0
        this.dataSelected.dslamThamSo = 0
      }
      await this.hienThiDanhSach()
      if (this.dataSelected.loaiTBiId === this.GPON || this.dataSelected.loaiTBiId === this.DSLAM || this.dataSelected.loaiTBiId === this.MANE) {
        await this.getDsCard(this.dataSelected.dslamTinh, this.dataSelected.loaiTBiId)
      } else {
        this.dsCard = []
      }

      if (this.dataSelected.dslamTinh) {

        let thamso = currentDSLAM.THAMSO
        window.thamso = thamso
        if (thamso === 9) {
          this.disableSelfid01 = false
          this.disableSelfid02 = false
          this.disableSelfid03 = false
          this.disableSelfid04 = false
        } else {
          this.disableSelfid01 = true
          this.disableSelfid02 = true
          this.disableSelfid03 = true
          this.disableSelfid04 = true
        }
        this.selfid01 = ''
        this.selfid02 = ''
        this.selfid03 = ''
        this.selfid04 = ''

      }
      if (this.dslamVdc) {
        this.dataSelected.dslamVdc = this.dslamVdc[0].DSLAM_ID
        this.dslamVdc.forEach((item) => {
          if (item.THAMSO === this.dataSelected.dslamThamSo) {
            this.dataSelected.dslamVdc = item.DSLAM_ID
            return
          }
        })
      }

    }

  }
  ,
  watch: {
    'dataSelected.donViQuanLy':

      async function (val) {
        await this.getTramTc()
      }

    ,
    'dataSelected.tramThiCong':

      async function (val) {

        await this.getTramTb()
      }

    ,
    'dataSelected.tramThietBi':

      async function (val) {

        await this.getDslamTinhTheoTramTbi()
        await this.checkDsLam()
        if (this.dsCheckDsLam.length <= 0) {
          await this.hienThiDanhSachVciVpi(0)
        }
      }

    ,
    'dataSelected.dslamTinh':

      async function (val) {
        //
        // if (val) {
        //   this.dataSelected.loaiTBiId = this.dsDsLamTinh.filter(item => item.DSLAM_ID = val)[0].LOAITBI_ID
        // } else {
        //   this.dataSelected.loaiTBiId = ''
        // }
        // if (this.dataSelected.loaiTBiId === this.GPON || this.dataSelected.loaiTBiId === this.DSLAM || this.dataSelected.loaiTBiId === this.MANE) {
        //   this.getDsCard(val, this.dataSelected.loaiTBiId)
        // } else {
        //   this.dsCard = []
        // }
        // this.hienThiDanhSach()
        //
        // if (val) {
        //
        //   let thamso = this.dsDsLamTinh.filter(item => item.DSLAM_ID = val)[0].THAMSO
        //   window.thamso = thamso
        //   if (thamso === 9) {
        //     this.disableSelfid01 = false
        //     this.disableSelfid02 = false
        //     this.disableSelfid03 = false
        //     this.disableSelfid04 = false
        //   } else {
        //     this.disableSelfid01 = true
        //     this.disableSelfid02 = true
        //     this.disableSelfid03 = true
        //     this.disableSelfid04 = true
        //   }
        //   this.selfid01 = ''
        //   this.selfid02 = ''
        //   this.selfid03 = ''
        //   this.selfid04 = ''
        //
        //
        // }
      }

    ,
    'dataSelected.bras':

      async function (val) {
        if (val) {
          await this.getDslamVdc(val)
        }
      }

    ,
    'dataSelected.rackShelfId':

      function (val) {
      }

    ,
    'dataSelected.portId':

      function (val) {
        if (this.dataSelected.portId) {
          this.isDisableBtnXoa = false
        } else {
          this.isDisableBtnXoa = true

        }
      }
  }
  ,
  mounted: async function () {
    await this.getDonViQl()
    await this.getTrangThaiPort()
    await this.getRackShelf()

    await this.getBrasAdsl()
  }
  ,
  validations: {
    slot: {
      required
    }
    ,
    port: {
      required
    }
  }

}
</script>
<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>

