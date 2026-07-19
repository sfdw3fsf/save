<template src="./portlogic.html"></template>
<script>
import { required } from 'vuelidate/lib/validators'
export default {
  components: {},
  name: 'PortLogic',
  props: {
    modalShowPortLogic: Boolean,
    dslamId: '',
    tramTbiId: ''
  },
  data: function () {
    return {
      dsPort: [],
      allItems: 0,
      portId: '',
      dsDichVu: [],
      dsSpliter: [],
      dsTrangThai: [],
      dsLoaiHinhDaGan: [],
      dsLoaiHinhChuaGan: [],
      dsSelectedDaGan: [],
      dsSelectedPort: [],
      dsSelectedChuaGan: [],
      isDisableBtnNhapMoi: false,
      isDisableBtnSua: false,
      isDisableBtnGhiLai: false,
      isDisableBtnHuyBo: false,
      isDisableBtnXoa: false,
      isDisableTenTuyenInput: false,
      isDisableCboLoaiTuyenTD: false,
      dataSelected: {
        portId: null,
        dichVuId: null,
        spliter: null,
        trangThaiPortID: null,
        loaiHinhDaGan: null,
        loaiHinhChuaGan: null,
        rpId: null,
      },
      portIdFilter: ""
    }
  },


  watch: {
    'dataSelected.spliter': async function (val) {

    }

  },
  created: async function (args) {
  },
  methods: {

    async onModalShown() {
      try {
        this.$root.showLoading(true);
        await this.getDichVu();
        await this.getTrangThaiPort();
        if (this.dslamId) {
          //await this.updateDsPort()
          await this.getDsPort(0, 10)
        }
        if (this.dataSelected.rpId) {
          await this.getDsLHDaGan(this.dataSelected.rpId)
          await this.getDsLHChuaGan(this.dataSelected.rpId, this.dataSelected.dichVuId)
        }
        if (this.tramTbiId) {
          await this.getSplitters(this.tramTbiId)
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },

    hideModal: function () {
      this.$emit('hideModal')
    }, grid_PageChanged: async function (args) {
      try {
        this.$root.showLoading(true);
        await this.getDsPort(args.pageIndex, args.pageSize);
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    }, getDsPort: async function (pageIndex, pageSize) {
      try {
        let rs = await this.$root.context.post(
          '/web-ecms/thongsoport/layDsPortV3', {
          dslamId: this.dslamId,
          portIds: this.portIdFilter,
          pageIndex: pageIndex,
          pageSize: pageSize
        });
        if (rs.data.pageItems) {
          this.allItems = rs.data.allItems;
          this.dsPort = rs.data.pageItems
          this.dataSelected.portId = rs.data.pageItems[0].PORT_ID
          this.dataSelected.rpId = rs.data.pageItems[0].RP_ID
        } else {
          this.allItems = 0;
          this.dsPort = []
          this.dataSelected.portId = null
        }

      } catch (error) {

      }
    }, updateDsPort: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/layDsPortV3', {
          dslamId: this.dslamId,
          portIds: this.portIdFilter,
          pageIndex: 0,
          pageSize: 1,
        }
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

      } finally {
        this.loading(false)

      }

      // this.totalItems = rs.data.allItems;
    }, getDichVu: async function () {
      try {
        var rs = await this.$root.context.get(
            '/web-ecms/thongsoport/layDichVu'
          )
          if (rs.data) {
            this.dsDichVu = rs.data
            this.dataSelected.dichVuId = rs.data[0].DICHVUVT_ID
          } else {
            this.dsDichVu = []
            this.dataSelected.dichVuId = null
          }
      } catch (error) {

      }
    }, dichVuChange: async function (selectedIndex) {
      this.dataSelected.dichVuId = this.dsDichVu[selectedIndex].DICHVUVT_ID
      if (this.dataSelected.rpId || this.dataSelected.dichVuId) {
        try {
          this.$root.showLoading(true);
          await this.getDsLHChuaGan(this.dataSelected.rpId, this.dataSelected.dichVuId)
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      }
    }, getTrangThaiPort: async function () {
      try {
        var rs = await this.$root.context.get(
            '/web-ecms/thongsoport/dslaytrangthaiport'
          )
          if (rs.data) {
            this.dsTrangThai = rs.data
            this.dataSelected.trangThaiPortID = rs.data[0].TT_PORT_ID
          } else {
            this.dsTrangThai = [];
            this.dataSelected.trangThaiPortID = ""
          }
      } catch (error) {

      }
    }, trangThaiChange: async function (selectedIndex) {

    }
    , async danhSachPortChange(rowIndex, dataItem) {
      try {
        this.$root.showLoading(true);
        if (dataItem) {
          this.dataSelected.rpId = dataItem.RP_ID
          this.dataSelected.trangThaiPortID = dataItem.TRANGTHAI
          this.dataSelected.spliter = dataItem.KETCUOI_ID

          await this.getDsLHDaGan(dataItem.RP_ID)
          await this.getDsLHChuaGan(dataItem.RP_ID, this.dataSelected.dichVuId)
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    }, selectedRowChanged: function (args) {

    },
    selectedPort: function (data) {
      this.dsSelectedPort = data
    },
    selectedDsDaGan: function (data) {
      this.dsSelectedDaGan = data;
    },
    selectedDsChuaGan: function (data) {
      this.dsSelectedChuaGan = data;
    },
    addToDsChuaGan: function () {
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
    }, getSplitters: async function (tramTbiId) {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/laySpliterTheoTram?tramtbi=${tramTbiId}`
        )
        this.dsSpliter = rs.data
        if (rs.data) {
          this.dataSelected.spliter = rs.data[0].KETCUOI_ID
        } else {
          this.dataSelected.spliter = ""
        }

      } catch (error) {
      }
    }, btnDanhSach: async function () {
      //await this.updateDsPort()
      try {
        this.$root.showLoading(true);
        await this.getDsPort(0, 10)
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
      
    },
    btnCapNhat: async function () {
      var arr = this.dsSelectedPort.map(val => val);
      if (this.dsSelectedPort.length <= 0) {
        this.$toast.error("Bạn chưa chọn port.");
        return
      }
      try {
        this.$root.showLoading(true);
        await this.capNhatTTport(arr, this.dataSelected.trangThaiPortID);
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnGanSplitter: async function () {
      var arr = this.dsSelectedPort.map(val => val);
      if (this.dsSelectedPort.length <= 0) {
        this.$toast.error("Bạn chưa chọn port.");
        return
      }
      if (!this.dataSelected.spliter) {
        this.$toast.error("Spliter không được trống.");
        return
      }
      try {
        this.$root.showLoading(true);
        await this.ganSplitter(arr, this.dataSelected.spliter);
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }

    },
    btnGanLH: async function () {
      if (this.dsSelectedPort.length <= 0) {
        this.$toast.error("Bạn chưa chọn port.");
        return
      }
      if (this.dsLoaiHinhDaGan.length <= 0) {
        this.$toast.error("Bạn chưa chọn loại hình.");
        return
      }
      if (this.dataSelected.rpId) {
        var arr = this.dsLoaiHinhDaGan.map(val => val.LOAITB_ID);
        var arrId = this.dsSelectedPort.map(val => val);
        try {
          this.$root.showLoading(true);
          await this.ganLhChoPort(arrId, arr);
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }

      }
    }, getDsLHDaGan: async function (vrp_id) {
      try {
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

      }
    }, getDsLHChuaGan: async function (vrpId, dichVuId) {
      try {
        var rs = await this.$root.context.get(
          `/web-ecms/thongsoport/dsLayLoaiHinhChuaGan?vrpId=${vrpId}&dichVuId=${dichVuId}`
        )
        if (rs.data.length > 0) {
          this.dsLoaiHinhChuaGan = rs.data
          this.dataSelected.loaiHinhChuaGan = rs.data[0].LOAITB_ID
        } else {
          this.dsLoaiHinhChuaGan = []
          this.dataSelected.loaiHinhChuaGan = null
        }
      } catch (error) {
      }
    }, capNhatTTport: async function (vrpId, trangThaiId) {
      try {
        let data = {
          vrpId: vrpId,
          trangThaiId: trangThaiId
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/capNhatTrangThaiPortV2', data
        )
        if (rs.data) {
          //await this.updateDsPort()
          await this.getDsPort(0, 10)
          this.$toast.success("Cập nhật thành công");
        } else {
          this.$toast.error("Có lỗi xảy ra");
        }

      } catch (error) {
        this.$toast.error(error.message);

      }
    }, ganSplitter: async function (vrpId, ketCuoiID) {
      try {
        let data = {
          vrpId: vrpId,
          ketCuoiId: ketCuoiID
        }
        if (!vrpId) {
          this.$toast.error("Chưa chọn port");
          return
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/ganSplitterTheoPort', data
        )
        if (rs.data) {
          //await this.updateDsPort()
          await this.getDsPort(0, 10)
          this.$toast.success("gán Splitter thành công");
        } else {
          this.$toast.error("Có lỗi xảy ra");
        }

      } catch (error) {
        this.$toast.error(error.message);

      }
    }, ganLhChoPort: async function (vrpId, loaiTbId) {
      try {
        if (!vrpId) {
          this.$toast.error("Chưa chọn port");
          return
        }
        let data = {
          vrpId: vrpId,
          loaiTbId: loaiTbId
        }
        var rs = await this.$root.context.post(
          '/web-ecms/thongsoport/ganLhChoPort', data
        )
        if (rs.data) {
          //await this.updateDsPort()
          await this.getDsPort(0, 10)
          this.$toast.success("Gán loại hình thành công");
        } else {
          this.$toast.error("Có lỗi xảy ra");
        }

      } catch (error) {
        this.$toast.error(error.message);

      }
    }
  }
}
</script>
<style>
.modal-xxl {
  max-width: 90% !important;
}

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

