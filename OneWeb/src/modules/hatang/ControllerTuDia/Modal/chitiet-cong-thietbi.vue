<template src="./chitiet-cong-thietbi.html"></template>

<script>
export default {
  // props: {
  //   chiTietCong: {
  //     type: Object
  //   }
  // },
  data() {
    return {
      //id: this.$route.params.id,
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      id: 1,
      connectorCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false, width: 100 },
        { fieldName: 'MATHIETBI', headerText: 'Mã thiết bị', allowFiltering: true },
        { fieldName: 'TENTHIETBI', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Loại thiết bị', allowFiltering: true },
        { fieldName: 'CHI_TIET', headerText: 'Chi tiết', allowFiltering: false, template: this.getColumnTemplate(this) }
      ],
      historyList: [],
      historyCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false, width: 100 },
        { fieldName: 'TACDONG', headerText: 'Tên tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'NGAY_CN', headerText: 'Thời điểm', allowFiltering: true }
      ],
      portPurposeData: [],
      portTypeData: [],
      portBandwidthData: [],
      moduleInfoData: [],
      trangthaiSuDungData: [],
      connectorData: [],
      currentTab: 2, // 1: Thong tin đầu nối | 2: Lịch sử
      currentItem: {
        id: 0,
        parentDevice: '',
        deviceId: 0,
        no: 0,
        portName: '',
        portClassification: '',
        portPurpose: 0,
        namePortPurpose: '',
        validity: 0,
        portType: 0,
        namePortType: '',
        portBandWidth: 0,
        namePortBandWidth: '',
        moduleInfo: 0,
        nameModuleInfo: '',
        connection: 0,
        nameConnection: '',
        usingState: 0,
        nameUsingState: '',
        note: ''
      },
      selectedList: [],
      controls: {
        comboboxPortPurpose: {
          enabled: true
        },
        checkboxValidity: {
          enable: true
        },
        comboboxPortType: {
          enable: true
        },
        comboboxPortBandWidth: {
          enable: true
        },
        comboboxModuleInfo: {
          enable: true
        },
        comboboxConnection: {
          enable: true
        },
        comboboxUsingStatus: {
          enable: true
        },
        textNote: {
          enable: true
        }
      },
      buttons: {
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        }
      }
    }
  },
  computed: {},
  watch: {
    // chiTietCong: {
    //   immediate: true, // ✅ Gọi lần đầu ngay cả khi giá trị chưa thay đổi
    //   handler(newValue) {
    //     //console.log('Dữ liệu mới:', newValue);
    //   }
    // },
    // portBandwidthData: {
    //   handler(newValue) {
    //     this.portBandwidthData = [...this.portBandwidthData];
    //   }
    // },
    "currentItem.portPurpose": function (newID) {
      const selected = this.portPurposeData.find(item => item.ID == newID);
      this.currentItem.namePortPurpose = selected ? selected.TEN : "";
    },
    "currentItem.portType": function (newID) {
      const selected = this.portTypeData.find(item => item.ID == newID);
      this.currentItem.namePortType = selected ? selected.TEN : "";
    },
    "currentItem.portBandWidth": function (newID) {
      const selected = this.portBandwidthData.find(item => item.ID == newID);
      this.currentItem.namePortBandWidth = selected ? selected.TEN : "";
    },
    "currentItem.portClassification": function (newID) {
      this.portBandwidthData = [...this.portBandwidthData];
    },
    "currentItem.moduleInfo": function (newID) {
      const selected = this.moduleInfoData.find(item => item.ID == newID);
      this.currentItem.nameModuleInfo = selected ? selected.TEN : "";
    },
    "currentItem.connection": function (newID) {
      const selected = this.connectorData.find(item => item.ID == newID);
      this.currentItem.nameConnection = selected ? selected.TEN : "";
    },
    "currentItem.usingState": function (newID) {
      const selected = this.trangthaiSuDungData.find(item => item.ID == newID);
      this.currentItem.nameUsingState = selected ? selected.TEN : "";
    }
  },
  async created() {
    //this.setCurrentItem(this.chiTietCong);
    await this.load()
  },
  methods: {
    getColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleDetailCLick(data)">Click</button>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    handleDetailCLick(data) {
      console.log(`🚀 handleDetailCLick ~ data`, data)
    },
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    async dialogOpen(data) {
      try {
        this.loading(true);  // Bắt đầu trạng thái loading

        // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
        if (this.$refs.dialogChiTietCongThietBi) {
          console.log('chiTietCong', data)
          this.$refs.dialogChiTietCongThietBi.show(); // Hiển thị dialog

          this.setCurrentItem(data);
          await this.getHistory();
        } else {
          this.$toast.error("Dialog không tồn tại hoặc không được tham chiếu đúng");
        }
      } catch (e) {
        // Hiển thị thông báo lỗi nếu có vấn đề
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message);
        } else {
          this.$toast.error("Đã xảy ra lỗi khi mở dialog");
        }
      } finally {
        this.loading(false); // Tắt trạng thái loading
      }
    },

    closeDialog() {
      this.$refs.dialogChiTietCongThietBi.hide();
    },

    setCurrentItem(item) {
      if (item != null) {
        this.currentItem.id = item.ID
        this.currentItem.parentDevice = item.MATHIETBI
        this.currentItem.deviceId = item.THIETBI_ID
        this.currentItem.no = item.SOTHUTU
        this.currentItem.portName = item.TEN
        this.currentItem.portClassification = item.PHANLOAI
        this.currentItem.namePortClassification = item.TEN_PHANLOAI
        this.currentItem.portPurpose = item.MUCDICHSUDUNG_CONG_ID
        this.currentItem.namePortPurpose = item.TEN_MUCDICHSUDUNG_CONG
        this.currentItem.validity = item.HIEU_LUC
        this.currentItem.portType = item.LOAICONG_ID
        this.currentItem.namePortType = item.TEN_LOAICONG
        this.currentItem.portBandWidth = item.BANGTHONGCONG_ID
        this.currentItem.namePortBandWidth = item.TEN_BANGTHONGCONG
        this.currentItem.moduleInfo = item.THONGTINMODULE_ID
        this.nameModuleInfo = item.TEN_THONGTINMODULE
        this.currentItem.connection = item.KETNOI
        this.currentItem.nameConnection = item.TEN_KETNOI
        this.currentItem.usingState = item.TRANGTHAI_SD
        this.currentItem.nameUsingState = item.TEN_TRANGTHAI_SD
        this.currentItem.note = item.GHI_CHU
        console.log('item', this.currentItem)
      }
    },
    validateControls() { },
    getHieuLucText(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    async load() {
      try {
        this.$root.showLoading(true)
        await Promise.all([this.getPortType(), this.getPortPurpose(), this.getDmBangThongCong(), this.getDmThongTinModule(), this.getDmKetNoi(), this.getDmTrangThaiSuDung()])
      } finally {
        this.$root.showLoading(false)
      }
    },
    async getPortType() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/loai-cong/get-items`)
        this.portTypeData = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getPortPurpose() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/mucdich-sudung-cong/get-items`)
        this.portPurposeData = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    // async getPortBandwidth() {
    //   try {
    //     let rs = await this.$root.context.post(`/web-ecms/hatang/bangthong-cong/get-items`)
    //     this.portBandwidthData = rs.data
    //   } catch (error) {
    //     this.handleErrorResponse(error)
    //   }
    // },
    // async getModuleInfo() {
    //   try {
    //     let rs = await this.$root.context.post(`/web-ecms/danhmuc/thong-tin-module/danhsach`)
    //     this.moduleInfoData = rs.data
    //   } catch (error) {
    //     this.handleErrorResponse(error)
    //   }
    // },
    // async getConnector() {
    //   const id = this.id
    //   try {
    //     let rs = await this.$root.context.get(`/web-ecms/hatang/cong-thietbi/thiet-bi-noi-cong/${id}`)
    //     this.connectorList = rs.data
    //   } catch (error) {
    //     this.handleErrorResponse(error)
    //   }
    // },
    getDmThongTinModule: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_THONGTIN_MODULE",
          thamSo1: null,
          thamSo2: null
        });
        this.moduleInfoData = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
    },
    getDmBangThongCong: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
          loaiDanhMuc: "HATANG_BANGTHONG_CONG",
          thamSo1: null,
          thamSo2: null
        });
        this.portBandwidthData = rs.data;

      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
    },
    getDmKetNoi: async function () {
        try {
            let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                loaiDanhMuc: "HATANG_KETNOI",
                thamSo1: null,
                thamSo2: null
            });
            this.connectorData = rs.data;
        } catch (e) {
            if (!(e.response == null || e.response == undefined)) {
                if (!(e.response.data == null || e.response.data == undefined)) {
                    this.$root.toastError(e.response.data.message_detail);
                }
            }
        }
    },
    getDmTrangThaiSuDung: async function () {
        try {
            let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                loaiDanhMuc: "TRANGTHAI_SUDUNG",
                thamSo1: null,
                thamSo2: null
            });
            console.log('trangthaiSuDungData', rs.data)
            this.trangthaiSuDungData = rs.data;
        } catch (e) {
            if (!(e.response == null || e.response == undefined)) {
                if (!(e.response.data == null || e.response.data == undefined)) {
                    this.$root.toastError(e.response.data.message_detail);
                }
            }
        }
    },

    async getHistory() {
      const id = this.currentItem.deviceId
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/cong-thietbi/lich-su/${id}`)
        this.historyList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },

    async handleSaveClick() {
      this.closeDialog();
      this.$emit("chitietCongThietBiTraVe", this.currentItem);
    }
  }
}
</script>
<style scoped></style>
