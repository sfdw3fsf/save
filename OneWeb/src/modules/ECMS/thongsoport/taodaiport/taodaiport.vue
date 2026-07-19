<template src="./taodaiport.html"></template>
<script>

import BssErrorMarker from '@/components/BssErrorMarker'
import { numeric, required } from 'vuelidate/lib/validators'

export default {
  components: { BssErrorMarker },
  name: 'TaoDaiPort',
  props: {
    // modalShowTaoDaiport: Boolean,
    dslamId: Number,
    inforPort: {},
    tramTbiId: '',
    system: ''
  },
  data: function () {
    return {
      DONVIQL_LD: 3,
      TRAM_VT: 5,
      DONVI_ID: 0,
      GPON: 3,
      DSLAM: 1,
      MANE: 17,
      SWITCH: 2,
      TRAM_PORT: 39,
      TT_PORT: '',
      allItems: 0,
      ktra_port_dslam: false,
      socard_max: 0,
      sub_slot: 0,
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
      dsLoaiHinhChuaGan: [],
      dsBras: [],
      dslamVdc: [],
      dsCard: [],
      dsPort: [],
      dsDichVu: [],
      dslamChitiet: [],
      dsLoaiTbId: '',
      fixport: 0,
      thamso: 0,
      dsSelectedDaGan: [],
      dsSelectedChuaGan: [],
      disableSelfid01: false,
      disableSelfid02: false,
      disableSelfid03: false,
      disableSelfid04: false,
      disableCard: false,
      isDisableBtnNhapMoi: false,
      isDisableBtnCapNhat: false,
      isDisableBtnHuy: false,
      isDisableBtnXoa: false,
      portMdf: '',
      slotBd: '',
      slotKt: '',
      portBd: '',
      portKt: '',
      rack: '',
      shelf: '',
      VpiSvlanBD: '',
      VpiCvlanBD: '',
      VpiSvlanKT: '',
      VpiCvlanKT: '',
      Sel1: null,
      Sel2: null,
      Sel3Bd: null,
      Sel3Kt: null,
      Sel4Bd: null,
      Sel4Kt: null,
      modalShowPortLogic: false,
      isFirst: false,
      //isFirstBras: false,
      //  modalShowTaoDaiport: false,
      dataSelected: {
        dichVuId: null,
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
        loaiTBiId: null,
        cardId: null,
        portId: null,
      }, detailcard: {
        vitri: null
      }
    }
  },

  watch: {
    'dataSelected.tramThiCong': async function (val) {

      await this.getTramTb();
    },
    'dataSelected.tramThietBi': async function (val) {
      await this.getDslamTinhTheoTramTbi()
      await this.checkDsLam()
    }, 'dataSelected.dslamTinh': async function (val) {
      let item_select = null;
      this.dsDsLamTinh.forEach(item => {
        if (item.DSLAM_ID == val) {
          item_select = item
        }
      });

      await this.getMaxPortMdf()

      if (item_select) {
        this.dataSelected.loaiTBiId = item_select.LOAITBI_ID
        await this.getTTDslam()

        if (this.dataSelected.loaiTBiId === this.GPON || this.dataSelected.loaiTBiId === this.DSLAM || this.dataSelected.loaiTBiId === this.MANE || this.dataSelected.loaiTBiId === this.SWITCH) {
          await this.getDsCard(this.dataSelected.dslamTinh, this.dataSelected.loaiTBiId)
        } else {
          this.dsCard = []
        }
      }
    }, 'dataSelected.bras': async function (val) {
      if (val) {
        await this.getDslamVdc(val)
      }
    }

  },
  methods: {
    donViQuanLy_changed: async function (args) {
      await this.getTramTc();
    },
    async onModalShown() {
      this.isFirst = true
      this.dsPort = []
      this.dsLoaiHinhDaGan = []
      try {
        this.$root.showLoading(true);
        await this.getDonViQl()
        await this.getTramTc()
        await this.getTramTb()
        await this.getDslamTinhTheoTramTbi()
        await this.getDichVu()
        await this.getTTDslam()
        await this.getMaxPortMdf()

        await this.getRackShelf()

        await this.getBrasAdsl()
        await this.getDslamVdc(this.dataSelected.bras)
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },

    hideModal: function () {
      this.portMdf = ''
      this.slotBd = ''
      this.slotKt = ''
      this.portBd = ''
      this.portMdf = ''
      this.VpiSvlanBD = ''
      this.VpiSvlanKT = ''
      this.VpiCvlanKT = ''
      this.VpiCvlanBD = ''

      this.$bvModal.hide('taodaiport')
      this.$emit('hideModal')
    }, selectedRowChanged: function () {

    }, getLHChuaGanV2: async function (dichVuId, dsLoaiTbId) {
      try {
        let rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayLoaiHinhChuaGanV2?dichVuId=${dichVuId}&dsloaitbId=`
        )

        if (rs.data.length > 0) {
          this.dsLoaiHinhChuaGan = rs.data

        } else {
          this.dsLoaiHinhChuaGan = []
        }

      } catch (error) {
        console.log('loadData error', error)
      }

    }, addToDsChuaGan: function () {
      var arr = [];
      this.dsSelectedDaGan.map(val => {
        var idx = this.dsLoaiHinhDaGan.findIndex(v => v.LOAITB_ID == val);
        if (idx >= 0) {
          arr.push(this.dsLoaiHinhDaGan[idx]);
          this.dsLoaiHinhDaGan = this.dsLoaiHinhDaGan.filter(v => v.LOAITB_ID != val);
        }
      })
      this.dsLoaiHinhChuaGan = this.dsLoaiHinhChuaGan.concat(arr);
      this.dsSelectedDaGan = [];
    },
    addToDsDaGan: function () {
      var arr = [];
      this.dsSelectedChuaGan.map(val => {
        var idx = this.dsLoaiHinhChuaGan.findIndex(v => v.LOAITB_ID == val);
        if (idx >= 0) {
          arr.push(this.dsLoaiHinhChuaGan[idx]);
          this.dsLoaiHinhChuaGan = this.dsLoaiHinhChuaGan.filter(v => v.LOAITB_ID != val);
        }
      })
      this.dsLoaiHinhDaGan = this.dsLoaiHinhDaGan.concat(arr);
      this.dsSelectedChuaGan = [];
    },
    selectedDsDaGan: function (data) {
      this.dsSelectedDaGan = data
    },
    selectedDsChuaGan: function (data) {
      this.dsSelectedChuaGan = data
    }, getDonViQl: async function () {
      try {
        var rs = await this.$root.context.post(
          `/web-ecms/thongsoport/dsdonviquanly?donviId=${this.DONVI_ID}&loaidvId=${this.DONVIQL_LD}`
          // `/web-ecms/thongsoport/dsdonviquanly?donviId=0&loaidvId=3`
        )
        if (rs.data.length > 0) {
          this.dsDonViQuanLy = rs.data
          if (this.inforPort.DONVI_ID) {
            this.dataSelected.donViQuanLy = this.inforPort.DONVI_ID
          } else {
            this.dataSelected.donViQuanLy = rs.data[0].DONVI_ID
          }
        } else {
          this.dataSelected.donViQuanLy = 0
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, dslamTinhChange: async function (selectedIndex) {
      this.dataSelected.loaiTBiId = this.dsDsLamTinh[selectedIndex].LOAITBI_ID
      await this.getTTDslam()

      if (this.dataSelected.loaiTBiId === this.GPON || this.dataSelected.loaiTBiId === this.DSLAM || this.dataSelected.loaiTBiId === this.MANE || this.dataSelected.loaiTBiId === this.SWITCH) {
        await this.getDsCard(this.dataSelected.dslamTinh, this.dataSelected.loaiTBiId)

      } else {
        this.dsCard = []
      }

    }, rackShelfChange: async function (selectedIndex) {
      this.dataSelected.rackShelfId = this.dsRackShelf[selectedIndex].RACK_ID
      this.dataSelected.rackShelfValue = this.dsRackShelf[selectedIndex].RACK_SHELF
      if (this.dataSelected.rackShelfValue) {
        this.rack = this.dataSelected.rackShelfValue.split(' - ')[0]
        this.shelf = this.dataSelected.rackShelfValue.split(' - ')[1]
      }
    }, dichVuChange: async function (selectedIndex) {
      await this.getLHChuaGanV2(this.dataSelected.dichVuId)
    }, dsCardChange: async function (selectedIndex) {
      this.dataSelected.cardId = this.dsCard[selectedIndex].CARD_ID
      this.slotBd = this.dsCard[selectedIndex].VITRI
      this.slotKt = this.dsCard[selectedIndex].VITRI
      this.portBd = this.dsCard[selectedIndex].PORT_BD
      this.portKt = this.dsCard[selectedIndex].SO_PORT

    }, getTramTc: async function () {
      try {
        var rs = await this.$root.context.get("/web-ecms/thongsoport/dsLayTramTc", {
          donviId: this.dataSelected.donViQuanLy,
          loaidvId: this.TRAM_VT
          }
        )
        this.dsTramThiCong = rs.data
        if (rs.data.length > 0) {
          //  await this.getTramTb()
          if (this.inforPort.TRAM_TC_ID && this.isFirst) {
            this.dataSelected.tramThiCong = this.inforPort.TRAM_TC_ID
          } else {
            this.dataSelected.tramThiCong = rs.data[0].DONVI_ID
          }
        } else {
          this.dataSelected.tramThiCong = 0
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, checkDsLam: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayDslam?donviId=${this.dataSelected.tramThietBi}`
        )
        if (rs.data.length > 0) {
          this.dsCheckDsLam = rs.data
        } else {
          this.dsCheckDsLam = []
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, getTramTb: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayTramTb?donviId=${this.dataSelected.tramThiCong}&loaidvId=${this.TRAM_PORT}`
        )
        this.dsTramTB = rs.data
        if (rs.data.length > 0) {
          if (this.inforPort.TRAM_TB_ID && this.isFirst) {
            this.dataSelected.tramThietBi = this.inforPort.TRAM_TB_ID
          } else {
            this.dataSelected.tramThietBi = rs.data[0].DONVI_ID
          }
        } else {
          this.dataSelected.tramThietBi = 0
          this.dsDsLamTinh = []
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, getBrasAdsl: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-ecms/thongsoport/dslaybrasadsl'
        )
        if (rs.data.length > 0) {
          this.dsBras = rs.data
          if (this.inforPort.BRAS_ID) {
            this.dataSelected.bras = this.inforPort.BRAS_ID
          } else {
            this.dataSelected.bras = rs.data[0].BRAS_ID
          }
        } else {
          this.dsBras = []
          this.dataSelected.bras = 0
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, getDsCard: async function (dslamId, loaiTbId) {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayDsCard?dslamId=${dslamId}&loaiTbId=${loaiTbId}`
        )
        if (rs.data.length > 0) {
          this.dsCard = rs.data
          this.dataSelected.cardId = rs.data[0].CARD_ID
          this.slotBd = this.dsCard[0].VITRI
          this.slotKt = this.dsCard[0].VITRI
          this.portBd = this.dsCard[0].PORT_BD
          this.portKt = this.dsCard[0].SO_PORT
        } else {
          this.dsCard = []
          this.dataSelected.cardId = null
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, getRackShelf: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-ecms/thongsoport/dslayrackshelf'
        )
        this.dsRackShelf = rs.data
        this.dsRackShelf1 = rs.data

        if (rs.data) {

          if (this.inforPort.RACK_ID) {
            this.dataSelected.rackShelfId = this.inforPort.RACK_ID
            this.dataSelected.rackShelfValue = rs.data.filter(item => item.RACK_ID === this.inforPort.RACK_ID)[0].RACK_SHELF
          } else {
            this.dataSelected.rackShelfId = rs.data[0].RACK_ID
            this.dataSelected.rackShelfValue = rs.data[0].RACK_SHELF
          }
          if (this.dataSelected.rackShelfValue) {
            this.rack = this.dataSelected.rackShelfValue.split(' - ')[0]
            this.shelf = this.dataSelected.rackShelfValue.split(' - ')[1]
          }
        } else {
          this.dataSelected.rackShelfId = 0
        }

      } catch (error) {
        console.log('loadData error', error)

      }

    }, getDslamVdc: async function (brasId) {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayDsLamVdc?brasId=${brasId}&kieu=2`
        )
        if (rs.data.length > 0) {
          this.dslamVdc = rs.data
          if (this.inforPort.DSLAM_VDC) {

            this.dataSelected.dslamVdc = this.inforPort.DSLAM_VDC
          } else {
            this.dataSelected.dslamVdc = rs.data[0].DSLAM_ID
          }
        } else {
          this.dslamVdc = []
          this.dataSelected.dslamVdc = 0
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, getDslamTinhTheoTramTbi: async function () {
      try {
        let rs = await this.$root.context.post(
          `/web-ecms/thongsoport/dsLayDsLamTinhTheoTramTbi?donViId=${this.dataSelected.tramThietBi}`
        );
        if (rs.data.length > 0) {
          this.dsDsLamTinh = rs.data
          if (this.inforPort.DSLAM_TINH_ID && this.isFirst) {
            this.dataSelected.dslamTinh = this.inforPort.DSLAM_TINH_ID
          } else {
            this.dataSelected.dslamTinh = rs.data[0].DSLAM_ID
          }
          if (this.dataSelected.loaiTBiId === this.GPON || this.dataSelected.loaiTBiId === this.DSLAM || this.dataSelected.loaiTBiId === this.MANE || this.dataSelected.loaiTBiId === this.SWITCH) {
            await this.getDsCard(this.dataSelected.dslamTinh, this.dataSelected.loaiTBiId)
          } else {
            this.dsCard = []
          }
          await this.getTTDslam()
        } else {
          this.dsDsLamTinh = []
          this.dataSelected.dslamTinh = 0
        }

      } catch (error) {
        console.log('loadData error', error)

      } finally {
        this.isFirst = false
      }
    }, getDichVu: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-ecms/thongsoport/dsLayDichVuV2'
        )
        if (rs.data) {
          this.dsDichVu = rs.data
          this.dataSelected.dichVuId = rs.data[0].DICHVUVT_ID
          await this.getLHChuaGanV2(this.dataSelected.dichVuId)
        } else {
          this.dsDichVu = []
          this.dataSelected.dichVuId = null
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, kiemTraDuLieu: function () {

    }, btnDanhSach: async function () {
      this.$v.$reset()
      if (this.validateDanhSach()) {
        return
      }
      try {
        this.$root.showLoading(true);
        await this.updatePageTotal()
        await this.layDsPort(1, 10)

      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    }, btnTaoDs: async function () {
      // if(this.dsLoaiHinhDaGan.length <=0 ){
      //   if(this.fixport !== 0){
      //     this.$toast.error('Hãy gán loại hình thuê bao tạo port.')
      //     return
      //   }
      // }
      var arr = this.dsLoaiHinhDaGan.map(val => val.LOAITB_ID)
      console.log(arr)
      if (this.validateData(true)) {
        return
      }
      this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn tạo port không ?',
        {
          title: 'Xác nhận hành động',
          centered: true,
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        }).then(async (value) => {
          if (value) {
            await this.TaoDaiPort(arr)
          }
        })

    }, btnXoa: async function () {
      if (this.validateData(false)) {
        return
      }
      this.$bvModal.msgBoxConfirm('Bạn có muốn xóa port không ?',
        {
          title: 'Xác nhận hành động',
          centered: true,
          size: 'sm'
        }).then(async (value) => {
          if (value) {
            await this.XoaDaiPort()
          }
        })
    }, getMaxPortMdf: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayMaxPortMdfHcm?dslamId=${this.dataSelected.dslamTinh}`
        )
        if (rs.data[0].PORT_MDF_MAX > 0) {
          this.portMdf = parseInt(rs.data[0].PORT_MDF_MAX) + 1
        } else {
          this.portMdf = parseInt(rs.data[0].PORT_MDF_MAX)
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, getTTDslam: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayTTDslam?dslamId=${this.dataSelected.dslamTinh}`
        )
        if (rs.data) {
          this.dslamChitiet = rs.data[0]
          this.fixport = rs.data[0].FIXPORT
          this.thamso = rs.data[0].THAMSO
          if (rs.data[0].BRAS_ID) {
            this.dataSelected.bras = rs.data[0].BRAS_ID
          }
          if (rs.data[0].DSLAM_CHA_ID) {
            this.dataSelected.dslamVdc = rs.data[0].DSLAM_CHA_ID
          }
          if (rs.data[0].SUB_SLOT) {
            this.dataSelected.dslamVdc = rs.data[0].DSLAM_CHA_ID
          }
          this.socard_max = rs.data[0].SO_CARD
          this.thamso = rs.data[0].THAMSO
          this.system = rs.data[0].SYSTEM

          this.VpiCvlanBD = rs.data[0].VCI_BD
          this.VpiSvlanBD = rs.data[0].SVLAN
          this.VpiSvlanKT = rs.data[0].SVLAN
        } else {
          this.dslamChitiet = []
          this.thamso = ''
          this.fixport = ''
        }

      } catch (error) {
        console.log('loadData error', error)

      }
    }, async danhSachPortChange(rowIndex, dataItem) {

      // // this.slotBd ==dataItem.,
      this.slotKt = dataItem.SLOT,
        this.slotBd = dataItem.SLOT,
        this.portBd = dataItem.PORT,
        this.portKt = dataItem.PORT,
        this.portMdf = dataItem.PORT_MDF,
        this.VpiCvlanBD = dataItem.VCI,
        this.VpiCvlanKT = dataItem.VCI,
        this.VpiSvlanBD = dataItem.VPI,
        this.VpiSvlanKT = dataItem.VPI
    }, grid_PageChanged: async function (args) {
      try {
        this.$root.showLoading(true);
        await this.layDsPort(args.pageIndex, args.pageSize);

      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    }, layDsPort: async function (pageIndex, pageSize) {
      try {
        let data = {
          dslamId: this.dataSelected.dslamTinh,
          portBd: this.portBd,
          portKt: this.portKt,
          rackId: this.dataSelected.rackShelfId,
          slotBd: this.slotBd,
          slotKt: this.slotKt,
          pageIndex: pageIndex,
          pageSize: pageSize
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/layDsDaiPort', data
        )


        if (rs.data) {
          this.dsPort = rs.data.pageItems
        } else {
          this.dsPort = []
        }
      } catch (error) {
        this.$toast.error('Lấy danh sách thất bại!', error)
        console.log(error)
        // let message_detail = error.response.data.message_detail
        // if(message_detail){
        //   this.$toast.error(message_detail)
        // }else {
        // }
      }
    }, updatePageTotal: async function () {
      try {
        let data = {
          dslamId: this.dataSelected.dslamTinh,
          portBd: this.portBd,
          portKt: this.portKt,
          rackId: this.dataSelected.rackShelfId,
          slotBd: this.slotBd,
          slotKt: this.slotKt,
          pageIndex: 0,
          pageSize: 1
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/layDsDaiPort', data
        );
        if (rs.data.allItems != -9999) {
          if (this.allItems == rs.data.allItems) this.$refs.gridDsPort.reloadCurrentPage()
          this.allItems = rs.data.allItems;
        } else {
          let total = 0
          if (this.allItems == total) this.$refs.gridDsPort.reloadCurrentPage()
          this.allItems = total;
          this.dsPort = []
        }
      } catch (e) {

      }
    }, defaultValue: function () {
      // if (this.Sel1 === "" || !this.Sel1 )
      //   this.Sel1 = "0";
      // if (this.Sel2 === "" || !this.Sel2 )
      //   this.Sel2 = "0";
      if (this.Sel3Bd === "" || !this.Sel3Bd)
        this.Sel3Bd = "0";
      if (this.Sel3Kt === "" || !this.Sel3Kt)
        this.Sel3Kt = "0";
      if (this.Sel4Bd === "" || !this.Sel4Bd)
        this.Sel4Bd = "0";
      if (this.Sel4Kt === "" || !this.Sel4Kt)
        this.Sel4Kt = "0";

    }, TaoDaiPort: async function (dsLoaiThietBi) {

      try {
        this.$root.showLoading(true);
        this.defaultValue()

        let data = {
          card_id: this.dataSelected.cardId === null ? 0 : this.dataSelected.cardId,
          dslam_id: this.dataSelected.dslamTinh,
          dslam_vdc_id: this.dataSelected.dslamVdc,
          dsloaitb_id: dsLoaiThietBi,
          port_bd: this.portBd,
          port_kt: this.portKt,
          port_mdf: this.portMdf,
          rack_id: this.dataSelected.rackShelfId,
          selfid01: this.Sel1,
          selfid02: this.Sel2,
          selfid03_bd: this.Sel3Bd,
          selfid03_kt: this.Sel3Kt,
          selfid04_bd: this.Sel4Bd,
          selfid04_kt: this.Sel4Kt,
          slot_bd: this.slotBd,
          slot_kt: this.slotKt,
          vci_bd: this.VpiCvlanBD,
          vci_kt: this.VpiCvlanKT,
          vpi_bd: this.VpiSvlanBD,
          vpi_kt: this.VpiSvlanKT
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/TaoDsDaiPort', data
        )
        await this.updatePageTotal()
        await this.layDsPort(1, 10)
        this.$toast.success('Tạo dải thành công')
      } catch (error) {
        let message_detail = error.response.data.message_detail
        if (message_detail) {
          this.$toast.error(message_detail)
        } else {
          this.$toast.error('Tạo dải thất bại')
        }
      } finally {
        this.$root.showLoading(false);
      }
    }, validateDanhSach() {
      // this.$v.portKt.$touch()
      // this.$v.portBd.$touch()
      this.$v.slotKt.$touch()
      this.$v.slotBd.$touch()

      let errorMessage = []

      if (!this.slotKt) {
        this.$refs.slotKt.focus()
        errorMessage.unshift('Trường slot kết thúc bắt buộc nhập dữ liệu.')
      }

      if (!this.slotBd) {
        this.$refs.slotBd.focus()
        errorMessage.unshift('Trường slot bắt đầu bắt buộc nhập dữ liệu.')
      }

      if (!this.dataSelected.dslamTinh) {
        errorMessage.unshift('Dslam tỉnh không tồn tại.')
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    }
    , validateData(create) {
      this.$v.$reset()
      this.$v.$touch()


      let errorMessage = []

      if (!this.dsDsLamTinh) {
        errorMessage.unshift('Dslam tỉnh không tồn tại.')
      }
      if (create) {
        if (this.dsLoaiHinhDaGan.length <= 0) {
          errorMessage.push('Hãy chọn loại hình cho port.')
        }
      }

      if (this.dsTramThiCong.length <= 0) {
        errorMessage.push('Hãy chọn trạm thi công.')
      }

      if (this.dsTramTB.length <= 0) {
        errorMessage.push('Hãy chọn trạm thiết bị.')
      }

      if (this.VpiSvlanKT == null || this.VpiSvlanKT === '') {
        this.$refs.VpiSvlanKT.focus()
        errorMessage.unshift('Trường vpi kết thúc bắt buộc nhập dữ liệu.')
      }

      if (this.VpiSvlanBD == null || this.VpiSvlanBD === '') {
        this.$refs.VpiSvlanBD.focus()
        errorMessage.unshift('Trường vpi bắt đầu bắt buộc nhập dữ liệu.')
      }

      if (this.VpiCvlanKT == null || this.VpiCvlanKT === '') {
        this.$refs.VpiCvlanKT.focus()

        errorMessage.unshift('Trường vci kết thúc bắt buộc nhập dữ liệu.')
      }

      if (this.VpiCvlanBD == null || this.VpiCvlanBD === '') {
        this.$refs.VpiCvlanBD.focus()

        errorMessage.unshift('Trường vci bắt đầu bắt buộc nhập dữ liệu.')
      }

      if (this.portKt == null || this.portKt === '') {
        this.$refs.portKt.focus()

        errorMessage.unshift('Trường port kết thúc bắt buộc nhập dữ liệu.')
      }

      if (this.portBd == null || this.portBd === '') {
        this.$refs.portBd.focus()
        errorMessage.unshift('Trường port bắt đầu bắt buộc nhập dữ liệu.')
      }

      if (this.slotKt == null || this.slotKt === '') {
        this.$refs.slotKt.focus()
        errorMessage.unshift('Trường slot kết thúc bắt buộc nhập dữ liệu.')
      }

      if (this.slotBd == null || this.slotBd === '') {
        this.$refs.slotBd.focus()
        errorMessage.unshift('Trường slot bắt đầu bắt buộc nhập dữ liệu.')
      }

      if (this.slotBd !== this.slotKt) {
        this.$refs.slotBd.focus()
        errorMessage.unshift('Trường slot bắt đầu phải bằng trường slot kết thúc.')
      }

      if (!this.portMdf) {
        this.$refs.portMdf.focus()
        errorMessage.unshift('Trường port mdf bắt buộc nhập dữ liệu.')
      }


      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    }
    , XoaDaiPort: async function () {

      try {
        this.$root.showLoading(true);
        this.defaultValue()
        let data = {

          dslam_id: this.dataSelected.dslamTinh,
          port_bd: this.portBd,
          port_kt: this.portKt,
          rack_id: this.dataSelected.rackShelfId,
          selfid01: this.Sel1,
          selfid02: this.Sel2,
          selfid03_bd: this.Sel3Bd,
          selfid03_kt: this.Sel3Kt,
          selfid04_bd: this.Sel4Bd,
          selfid04_kt: this.Sel4Kt,
          slot_bd: this.slotBd,
          slot_kt: this.slotKt,
          thamso: this.thamso,
          vci_bd: this.VpiCvlanBD,
          vci_kt: this.VpiCvlanKT,
          vpi_bd: this.VpiSvlanBD,
          vpi_kt: this.VpiSvlanKT
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/XoaDaiPort', data
        )
        if (rs.message === 'Thành công') {
          this.$toast.success('Xoá dải thành công')
          await this.updatePageTotal()
          await this.layDsPort(1, 10)
        } else {
          this.$toast.error('Xoá dải thất bại')
        }
      } catch (error) {
        let message_detail = error.response.data.message_detail
        if (message_detail) {
          this.$toast.error(message_detail)
        } else {
          this.$toast.error('Xoá dải thất bại')
        }
      } finally {
        this.$root.showLoading(false);
      }
    }

  },
  validations: {
    portMdf: {
      required
    }
    ,
    slotBd: {
      required
    },
    slotKt: {
      required
    },
    portBd: {
      required
    },
    portKt: {
      required
    },
    VpiSvlanBD: {
      required
    },
    VpiSvlanKT: {
      required
    },
    VpiCvlanBD: {
      required
    },
    VpiCvlanKT: {
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

.modal-xxl {
  max-width: 80% !important;
}

.modal-fullscreen .modal-dialog {
  max-width: 100%;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100vh;
  display: flex;
  position: fixed;
  z-index: 100000;
}
</style>

