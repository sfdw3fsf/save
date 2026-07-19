<template src="./index.html"></template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import ThongTinCongViec from '@/modules/tainguyen-idc/components/ThongTinCongViec/index.vue';
import LapDatRackIDCModal from './modals/LapDatRack.vue';
import ChonRackIDCModal from './modals/DanhSachRack.vue';
import ThiCongViTriRackModal from './modals/thicong-vitri-rack.vue';
import ThongTinRackModal from '../components/thongtin-rack-modal.vue';
import ChuyenXuLyModal from './modals/ChuyenXuLy.vue';
import TraPhieuModal from './modals/TraPhieu.vue';
import axios from 'axios';
export default {
  components: {
    DataGrid,
    ThongTinCongViec,
    LapDatRackIDCModal,
    ChonRackIDCModal,
    ThiCongViTriRackModal,
    ThongTinRackModal,
    ChuyenXuLyModal,
    TraPhieuModal,
  },
  props: {
  },
  data: function () {
    return {
      phieutc: null,
      isViewOnly: false,
      rackId: null,
      phieuThiCongDetail: {
        TRANGTHAI_ID: null,
        LOAICV_ID: null,
        NHANVIEN_TH_ID: null,
        NHANVIEN_TAO_ID: null,
        HDTB_ID: null,
        MA_TB: null,
        GIATRI_KHAIBAO: null,
      },
      isLoadingDetail: false, // Flag để kiểm soát gọi API
      currentTab: 1, // Tab hiện tại, mặc định là tab 1
      historyRackList: [],
      dataGrid2Items: [],
      processedRackIds: [], // Lưu danh sách RACK_ID đã được xử lý
      dataGrid2Columns: [
        {fieldName: 'RACK_ID', headerText: 'ID', width: 80, textAlign: 'Center'},
        {fieldName: 'TEN_RACK', headerText: 'Tên Rack', width: 150, textAlign: 'Left'},
        {fieldName: 'TEN_LOAIRACK', headerText: 'Loại Rack', width: 150, textAlign: 'Left'},
        {fieldName: 'TEN_HANGSX', headerText: 'Hãng sản xuất', width: 130, textAlign: 'Left'},
        {fieldName: 'TRANGTHAI_VH', headerText: 'Trạng thái vận hành', width: 130, textAlign: 'Left'},
        {fieldName: 'TEN_QUYHOACH', headerText: 'Trạng thái theo quy hoạch', width: 150, textAlign: 'Left'},
        {fieldName: 'TEN_CCDV', headerText: 'Trạng thái rack CCDV', width: 130, textAlign: 'Left'},
        {fieldName: 'TEN_IDC', headerText: 'IDC', width: 130, textAlign: 'Left'},
        {fieldName: 'TEN_PHONG', headerText: 'Phòng/Zone', width: 150, textAlign: 'Left'},
        {fieldName: 'VITRI_RACK', headerText: 'Vị trí Rack', width: 100, textAlign: 'Left'},
        {fieldName: 'thaoTac', headerText: 'Thao tác', textAlign: 'Center', template: this.actionColumn(this), width: 120, allowFiltering: false}

      ]
    };
  },
  computed: {
    grd2: function () {
      return this.$refs.grd2;
    },
    isCapPhatRackDisabled() {
      if (!this.phieuThiCongDetail) return false;
      if (this.phieuThiCongDetail.HDTB_ID == null) {
        return this.dataGrid2Items.length == this.phieuThiCongDetail.GIATRI_KHAIBAO;
      } else {
        return this.dataGrid2Items.length > 0;
      }
    },
    allRowsProcessed() {
      return this.dataGrid2Items.length > 0 && this.dataGrid2Items.every(item => item.isProcessed);
    },
    isEditMode() {
      const isEditMode = this.$route.query.isEditMode;
      return isEditMode === 'true' || isEditMode === true;
    },

  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.phieutc = this.$route.query.phieutc ? parseInt(this.$route.query.phieutc) : null;
      await this.getHistoryList();
    } finally {
      this.$root.showLoading(false);
      this.isLoadingDetail = false; // Reset flag
    }
  },
  mounted: function () {
    // Lắng nghe sự kiện từ component con ThongTinCongViec
    this.$refs.thongTinCongViec.$on('phieuThiCongLoaded', this.onPhieuThiCongLoaded);
  },
  beforeDestroy: function () {
    // Hủy listener để tránh memory leak
    this.$refs.thongTinCongViec.$off('phieuThiCongLoaded', this.onPhieuThiCongLoaded);
  },
  watch: {
    dataGrid2Items: {
      handler(newItems) {
        console.log('dataGrid2Items.length:', newItems.length);
      },
      deep: true
    },
    phieuThiCongDetail: {
      handler(newDetail) {
        if (newDetail) {
          // Cập nhật isViewOnly khi detail thay đổi
          let isViewOnly = true;
          // Nếu đang ở chế độ edit thì cho phép thao tác
          if (this.isEditMode) {
            isViewOnly = false;
          } else if ([21, 22, 81].includes(newDetail.TRANGTHAI_ID)) {
            if (newDetail.TRANGTHAI_ID == 21) {
              isViewOnly = newDetail.NHANVIEN_TAO_ID != this.$root.token.getNhanVienID();
            } else if ([22, 81].includes(newDetail.TRANGTHAI_ID)) {
              isViewOnly = newDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID();
            } else {
              isViewOnly = false;
            }
          } else {
            isViewOnly = true;
          }

          this.isViewOnly = isViewOnly;
        }
      },
      deep: true,
      immediate: true
    },
    phieutc(newValue, oldValue) {
      if (newValue !== oldValue) {
        this.processedRackIds = []; // Reset khi chuyển phiếu
        this.loadAllocatedRacks();
      }
    }
  },
  methods: {
    // Hàm nhận event từ con và xử lý dữ liệu
    onPhieuThiCongLoaded(phieuThiCongDetail) {
      if (!this.isLoadingDetail) {
        this.isLoadingDetail = true;
        this.phieuThiCongDetail = phieuThiCongDetail || null;
        // Gọi các hàm phụ thuộc sau khi có detail từ con
        this.loadAllocatedRacks();
        this.isLoadingDetail = false;
      }
    },
    async loadPhieuThiCongDetail(phieutcId) {
      this.isLoadingDetail = true;
      try {
        this.$root.showLoading(true)
        const rs = await this.$root.context.post('/web-ecms/idc/danhsach-congviec/chitiet/' + phieutcId)
        this.phieuThiCongDetail = rs.data[0] || {};
      } catch (error) {
        this.$root.toastError(error?.message_detail || 'Có lỗi xảy ra khi lấy thông tin phiếu thi công')
      } finally {
        this.$root.showLoading(false);
        this.isLoadingDetail = false;
      }
    },
    getHistoryList: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "LSTD-RACK-THEO-PHIEUTC",
          thamSo1:null,
          thamSo2:this.phieutc
        });
        this.historyRackList = rs.data || [];
        this.historyRackList.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    loadHistoryList: async function () {
      await this.getHistoryList();
    },
    //http://localhost:10990
    loadAllocatedRacks: async function () {
      try {
        let response = await this.$root.context.post(`/web-ecms/thicong-rack/ds-rack-trong-phieutc/${this.phieutc}`);
        if (response && response.data) {
          // Thêm trường isProcessed cho mỗi item trong danh sách
          this.dataGrid2Items = response.data.map(item => ({
            ...item,
            isProcessed: this.processedRackIds.includes(item.RACK_ID) // Giữ trạng thái isProcessed
          }));
        }
      } catch (error) {
        this.dataGrid2Items = [];
      }
    },
    grd2_RowSelected: function (args) {
      console.log('DataGrid 2 row selected:', args.data);
    },
    onChuyenThucHien() {
      this.$confirm('Xác nhận chuyển phiếu thi công sang trạng thái Đang thực hiện?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        let rs = await this.$refs.thongTinCongViec.chuyenThucHien()
        if (rs) {
          await this.loadPhieuThiCongDetail(this.phieutc);
        }
      })
    },
    onXacNhanThucHien() {
      this.$confirm('Xác nhận đã thực hiện phiếu thi công này?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        let rs = await this.$refs.thongTinCongViec.xacNhanThucHien()
        if (rs) {
          await this.loadPhieuThiCongDetail(this.phieutc);
        }
      })
    },
    onSave: async function () {
      let rs = await this.$refs.thongTinCongViec.onSave()
      if (rs) {
        await this.loadPhieuThiCongDetail(this.phieutc);
      }
    },
    onTransfer: async function () {
      try {
        console.log('NVID:', this.$root.token.getNhanVienID());
        this.$refs.popupChuyenXuly.dialogOpen(this.phieuThiCongDetail,  this.phieutc);
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi chuyển xử lý');
      }
    },
    onReturn: async function () {
      try {
        this.$refs.popupTraPhieu.dialogOpen(this.phieuThiCongDetail,  this.phieutc);
      } catch (error) {
        console.error('Lỗi khi trả phiếu:', error);
        this.$toast.error('Có lỗi xảy ra khi trả phiếu');
      }
    },
    onComplete: async function () {
      if (!this.phieutc) {
        this.$toast.warning('Không có phiếu thi công để hoàn thành');
        return;
      }

      let flag = await this.$confirm(
        "Bạn có chắc chắn muốn hoàn thành phiếu này không?",
        "Xác nhận hoàn thành",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Hủy",
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
          let rs = false;
          let response = null;
          response = await this.$root.context.post(`/web-ecms/thicong-rack/hoanthanh-lapdat-rack-idc/${this.phieutc}`);

          rs = response.data;
          if (response || rs) {
            this.$toast.success('Hoàn thành phiếu thành công');
            this.$router.push({
              path: '/tainguyen-idc/ketqua-lapdat-rack'
            })
          } else {
            this.$toast.error('Hoàn thành phiếu thất bại');
          }
        } catch (error) {
          if (error && error.response && error.response.data && error.response.data.message_detail) {
            this.$toast.error(error.response.data.message_detail);
          } else {
            console.log(error);
            this.$toast.error('Có lỗi xảy ra khi hoàn thành phiếu');
          }
        }
      }
    },
    themMoiRackIDC() {
      if (this.$refs.popupLapPhatRack) {
        this.$refs.popupLapPhatRack.dialogOpen();
      }
    },
    chonRackIDC(){
      if (this.$refs.popupChonRackIDC) {
        this.$refs.popupChonRackIDC.dialogOpen();
      }
    },
    thiCongViTriRack(rackId) {
      if (this.$refs.popupThiCongViTriRack) {
        this.$refs.popupThiCongViTriRack.dialogOpen(rackId);
      }
    },
    onRackAllocated(rackData) {
      // Thêm RACK_ID vào processedRackIds nếu chưa có
      if (rackData && rackData.RACK_ID && !this.processedRackIds.includes(rackData.RACK_ID)) {
        this.processedRackIds.push(rackData.RACK_ID);
      }
      // Reload danh sách rack
      this.loadAllocatedRacks();
    },
    onRackRowDoubleClick: async function (args) {
      this.rackId = args.rowData.RACK_ID;
      this.$refs.popupChiTietRack.dialogOpen(this.rackId);
    },
    xoaLapDatRack: async function (data) {
      if (!this.phieutc) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }

      let flag = await this.$confirm(
        `Bạn có chắc chắn muốn gỡ lắp đặt rack "${data.TEN_RACK}" không?`,
        "Xác nhận gỡ",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Hủy",
          type: "warning",
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
          let response = await this.$root.context.post(`/web-ecms/thicong-rack/delete-thicong-rack/${data.RACK_ID}`);

          if (response && response.error && response.error == "200") {
            this.$toast.success('Gỡ lắp đặt Rack thành công');
            await this.loadAllocatedRacks();
          } else {
            this.$toast.error('Gỡ lắp đặt Rack thất bại');
          }
        } catch (error) {
          console.error('Error deleting rack allocation:', error);
          this.$toast.error('Có lỗi xảy ra khi gỡ lắp đặt Rack');
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
               <button v-if="onRemoveRack" class="btn btn-sm btn-outline-danger ml-1" title="Gỡ lắp đặt" @click="deleteItem()">
                  <i class="fa fa-trash"></i> Gỡ
                </button>
                <button v-if="onProcessRack && !data.isProcessed" class="btn btn-sm btn-outline-success" title="Xử lý" @click="xuLyItem()" >
                  <i class="fa fa-cogs"></i> Lắp đặt
                </button>
                <button v-if="data.isProcessed && onProcessRack" class="btn btn-sm btn-success" title="Đã lắp đặt" disabled>
                  <i class="fa fa-check"></i> Đã lắp đặt
                </button>
                <button v-if="onOnlyView" class="btn btn-sm btn-outline-primary" title="Chỉ xem" :disabled="true">
                  <i class="fa fa-ban"></i> Chỉ xem
                </button>
              </div>`,
            computed: {
              onRemoveRack() {
                return parent.phieuThiCongDetail.TRANGTHAI_ID == 21;
              },
              onProcessRack() {
                return  parent.phieuThiCongDetail.TRANGTHAI_ID == 22 &&
                 parent.phieuThiCongDetail.NHANVIEN_TH_ID == this.$root.token.getNhanVienID();
              },
              onOnlyView() {
                return  parent.phieuThiCongDetail.TRANGTHAI_ID == 23 ||
                 parent.phieuThiCongDetail.TRANGTHAI_ID == 24 ||
                 (parent.phieuThiCongDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID() && parent.phieuThiCongDetail.TRANGTHAI_ID != 21);
              },
            },
            methods: {
              deleteItem() {
                parent.xoaLapDatRack(this.data)
              },
              xuLyItem() {
                parent.rackId = this.data.RACK_ID;
                if (parent.rackId != null) {
                  parent.thiCongViTriRack(parent.rackId);
                }
              }
            }
          }
        }
      }
    },
  }
};
</script>

<style scoped>
.form-section {
  margin: 20px 0;
}

.datagrid-section {
  margin: 20px 0;
}
</style>
