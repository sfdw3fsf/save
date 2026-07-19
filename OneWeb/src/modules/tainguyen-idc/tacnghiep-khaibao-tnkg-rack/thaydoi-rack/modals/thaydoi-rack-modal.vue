<template src="./thaydoi-rack-modal.html"></template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import ThongTinRackModal from '../../components/thongtin-rack-modal';
import  DanhSachThanhNguon from '/src/modules/tainguyen-idc/ThongTinRack/Modal/DanhSachThanhNguon';
import axios from 'axios';
export default {
  components: {
      DataGrid,
      ThongTinRackModal,
      DanhSachThanhNguon,
  },
  props: {
      phieuThiCongId: {
          type: Number,
          default: null,
      },
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },

      dataItem: {},
      selectedRack: null,
      rackDataSource: [],
    }
  },
  computed: {
    rackListItems: function () {
      return this.$refs.rackListItems;
    },
  },
  methods: {
    getRackList: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: 'DS-SS-THAYDOI-RACK',
          thamSo1: this.phieuThiCongId
        });
        result = rs.data || [];
        dataItem = rs;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    clearAll() {
      this.dataItem = {};
    },
    closeDialog() {
      //this.clearAll();
      this.$refs.dialogThayDoiRack.hide();
    },
    async dialogOpen() {
      try {
        this.$root.showLoading(true);
        this.clearAll();
        this.rackDataSource = await this.getRackList();
        this.$refs.dialogThayDoiRack.show();

      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },

    grd1_RowSelected: function (args) {
      this.dataItem= args.data;
      console.log('DataGrid 1 row selected:', this.dataItem.RACK_ID);
    },

    grid_onCommandClicked: function (name, dataItem) {
      if (dataItem.RACK_ID > 0 && name == "THANHNGUON") {
        this.$refs.popupDanhSachThanhNguon.showModal(dataItem.RACK_ID);
      } else {
        this.$toast.warning("Vui lòng chọn Rack trước khi xem!");
      }
    },
    async onThayDoiRack() {
       if (!this.dataItem) {
          this.$toast.warning('Vui lòng chọn rack để thay đổi');
          return;
      }
      if (!this.phieuThiCongId) {
          this.$toast.warning('Không có thông tin phiếu thi công');
          return;
      }
      let flag = await this.$confirm(
          "Bạn có muốn thay đổi rack này không ?",
          "Xác nhận",
          {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "info",
              dangerouslyUseHTMLString: true,
          }
      )
      .then((s) => true)
      .catch((e) => {
          return false;
      });

      if (flag) {
        try {
          this.$root.showLoading(true);
          const requestBody = {
              phieuTCId: this.phieuThiCongId,
              rackId: this.dataItem.RACK_ID
          };
          let response = null;
          response = await this.$root.context.post('/web-ecms/thicong-rack/upsert-thicong-rack', requestBody);

          if (response || response.data) {
            this.$root.toastSuccess("Chọn rack để thay đổi thành công");
            this.$emit('rackAllocated');
            this.closeDialog();
          } else {
            if (response && response.message_detail) {
                this.$root.toastError(response.message_detail);
            } else {
                this.$root.toastError("Chọn rack để thay đổi thất bại");
            }
          }
        } catch (err) {
          if (err && err.response && err.response.data && err.response.data.message_detail) {
            this.$root.toastError(err.response.data.message_detail);
          } else {
            this.$root.toastError("Chọn rack để thay đổi thất bại");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onRackRowDoubleClick: async function (args) {
      this.dataItem = args.rowData;
      this.$refs.popupChiTietRack.dialogOpen(this.dataItem.RACK_ID);
    },
    onRackAllocated(rack) {
      this.$emit('rackAllocated', rack); // truyền tiếp lên index.vue
    },
    onCloseParentDialog() {
      this.closeDialog();
    },
    onOverlayClick: function() {
      this.$refs.dialogThayDoiRack.hide();  // hoặc xử lý khác
    },


  },
}
</script>
<style scoped>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
}
.e-dialog {
  position: fixed !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
}
.e-dlg-overlay {
  z-index: 0 !important;
}
</style>
