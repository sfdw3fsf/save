<template src="./index.html"></template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import ThongTinCongViec from '@/modules/tainguyen-idc/components/ThongTinCongViec/index.vue';
import ThuHoiUModal from './modals/thuhoi-u-modal.vue';
import TraPhieu from './modals/TraPhieu.vue';
import ChuyenXuLy from './modals/ChuyenXuLy.vue';
import TabHistory from '@/modules/tainguyen-idc/components/Tabs/TabHistory/index.vue';

export default {
  components: {
    DataGrid,
    ThongTinCongViec,
    ThuHoiUModal,
    TraPhieu,
    ChuyenXuLy,
    TabHistory,
  },
  props: {
  },
  data: function () {
    return {
      phieutc: null,
      phieuThiCongDetail: null,
      selectedItems: [],
      isLoadingDetail: false,
      apiCache: {},
      isViewOnly: false,
      showHistoryModal: false,
      historyData: {
        tenBang: 'IDC_RACKUNIT',
        tenCot: null,
        khoaDuLieu: null
      },
      rackDataGridItems: [],
      rackDataGridColumns: [
        {fieldName: 'ID_RACK', headerText: 'ID Rack', textAlign: 'Left', width: 80},
        {fieldName: 'TEN_RACK', headerText: 'Tên Rack', textAlign: 'Left', width: 120},
        {fieldName: 'TEN_LOAIRACK', headerText: 'Loại Rack', textAlign: 'Left', width: 120},
        {fieldName: 'TEN_IDC', headerText: 'IDC', textAlign: 'Left', width: 100},
        {fieldName: 'TEN_MATSAN', headerText: 'Mặt sàn', textAlign: 'Left', width: 120},
        {fieldName: 'TEN_PHONG', headerText: 'Phòng/Zone', textAlign: 'Left', width: 120},
        {fieldName: 'VITRI_RACK', headerText: 'Vị trí Rack', textAlign: 'Left', width: 120}
      ],
      dataGrid2Items: [],
      dataGrid2Columns: [
        {fieldName: 'ID_U', headerText: 'ID U', textAlign: 'Left', width: 80},
        {fieldName: 'TEN_U', headerText: 'Tên U', textAlign: 'Left', width: 120},
        {fieldName: 'VITRI_U', headerText: 'Vị trí U', textAlign: 'Left', width: 120},
        {fieldName: 'TRANGTHAI_SD', headerText: 'Trạng thái SD', textAlign: 'Left', width: 130},
        {fieldName: 'MOTA', headerText: 'Mô tả', textAlign: 'Left', width: 200},
        {fieldName: 'thaoTac', headerText: 'Thao tác', textAlign: 'Center', template: this.actionColumn(this), width: 150, allowFiltering: false}
      ]
    };
  },
  computed: {
    grdRack: function () {
      return this.$refs.grdRack;
    },
    grd2: function () {
      return this.$refs.grd2;
    },
  },
  created: async function () {
    this.phieutc = this.$route.query.phieutc ? parseInt(this.$route.query.phieutc) : null;
    if (this.phieutc) {
      await this.loadRackData();
      await this.loadAllocatedUs();
    }
  },
  methods: {
    loadRackData: async function () {
      try {
        let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/ds-rack-trongphieu-capphat-thuhoi-u/${this.phieutc}`);
        if (response && response.data) {
          this.rackDataGridItems = response.data;
        }
      } catch (error) {
        console.error('Error loading rack data:', error);
        this.rackDataGridItems = [];
      }
    },
    loadAllocatedUs: async function () {
      try {
        let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/ds-u-trong-phieutc-thuhoi/${this.phieutc}`);
        if (response && response.data) {
          this.dataGrid2Items = response.data;
        }
      } catch (error) {
        console.error('Error loading allocated Us:', error);
        this.dataGrid2Items = [];
      }
    },
    grdRack_RowSelected: function (args) {
      console.log('Rack DataGrid row selected:', args.data);
    },
    grd2_RowSelected: function (args) {
      console.log('DataGrid 2 row selected:', args.data);
      
      // Handle both single item and array (Select All case)
      const itemsToAdd = Array.isArray(args.data) ? args.data : [args.data];
      
      itemsToAdd.forEach(item => {
        // Add to selected list if not already present
        const existingIndex = this.selectedItems.findIndex(existingItem => existingItem.ID_U === item.ID_U);
        if (existingIndex === -1) {
          this.selectedItems.push(item);
        }
      });
      
      console.log('Selected items:', this.selectedItems);
    },
    grd2_RowDeselected: function (args) {
      console.log('DataGrid 2 row deselected:', args.data);
      
      // Handle both single item and array (Deselect All case)
      const itemsToRemove = Array.isArray(args.data) ? args.data : [args.data];
      
      itemsToRemove.forEach(item => {
        // Remove from selected list
        const index = this.selectedItems.findIndex(existingItem => existingItem.ID_U === item.ID_U);
        if (index > -1) {
          this.selectedItems.splice(index, 1);
        }
      });
      
      console.log('Selected items after deselection:', this.selectedItems);
    },
    onSave: async function () {
      let rs = await this.$refs.thongTinCongViec.onSave()
      if (rs) {
        await this.loadPhieuThiCongDetail(this.phieutc);
      }
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
    onTransfer: async function () {
      try {
        this.$refs.popupChuyenXuly.show(this.phieuThiCongDetail,'ADD', this.phieutc );
      } catch (error) {
        console.error('Lỗi khi chuyển xử lý:', error);
        this.$toast.error('Có lỗi xảy ra khi chuyển xử lý');
      }
    },
    async loadPhieuThiCongDetail(phieutcId) {
      if (this.isLoadingDetail || this.apiCache[phieutcId]) return;
      this.isLoadingDetail = true;
      try {
        this.$root.showLoading(true)
        const rs = await this.$root.context.post('/web-ecms/idc/danhsach-congviec/chitiet/' + phieutcId)
        this.phieuThiCongDetail = rs.data[0] || {}
        console.log('API response:', this.phieuThiCongDetail);

        this.isViewOnly =
          !(this.phieuThiCongDetail.TRANGTHAI_ID == 81 || this.phieuThiCongDetail.TRANGTHAI_ID == 21 || this.phieuThiCongDetail.TRANGTHAI_ID == 22) ||
          this.$route.query.viewOnly === '1' ||
          (this.phieuThiCongDetail.TRANGTHAI_ID == 21 ? this.phieuThiCongDetail.NHANVIEN_TAO_ID != this.$root.token.getNhanVienID() : false) ||
          (this.phieuThiCongDetail.TRANGTHAI_ID == 81 ? this.phieuThiCongDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID() : false)
          console.log('Computed isViewOnly:', this.isViewOnly);
      } catch (error) {
        this.$root.toastError(error?.message_detail || 'Có lỗi xảy ra khi lấy thông tin phiếu thi công')
      } finally {
        this.$root.showLoading(false);
        this.isLoadingDetail = false;
      }
    },
    onReturn: async function () {
      try {
        this.$refs.popupTraPhieu.show(this.phieutc);
      } catch (error) {
        console.error('Lỗi khi trả phiếu:', error);
        this.$toast.error('Có lỗi xảy ra khi trả phiếu');
      }
    },
    returnTicket: async function () {
      if (this.dataGrid2Items.length > 0) {
        this.$toast.warning('Không thể trả phiếu khi còn U đang thu hồi.');
        return;
      }
      
      if (!this.phieutc) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }

      let flag = await this.$confirm(
        "Bạn có chắc chắn muốn trả phiếu không?",
        "Xác nhận trả phiếu",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Huỷ",
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
          let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/tra-phieu/${this.phieutc}`);
          
          if (response && response.error && response.error == "200") {
            this.$toast.success('Trả phiếu thành công');
         
          } else {
            this.$toast.error('Trả phiếu thất bại');
          }
        } catch (error) {
          console.error('Error returning ticket:', error);
          this.$toast.error('Có lỗi xảy ra khi trả phiếu');
        }
      }
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
    onComplete: async function () {
      if (!this.phieutc) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }

      let flag = await this.$confirm(
        "Bạn có chắc chắn muốn hoàn thành phiếu này không?",
        "Xác nhận hoàn thành",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Huỷ",
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
          let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/hoan-thanh-thuhoi/${this.phieutc}`);
          
          if (response && response.error && response.error == "200") {
            this.$toast.success('Hoàn thành phiếu thành công');
            this.$router.push({ 
              path: '/tainguyen-idc/ketqua-thuhoi-u'
            })
          } else {
            this.$toast.error('Hoàn thành phiếu thất bại');
          }
        } catch (error) {
          if (error && error.response && error.response.data && error.response.data.message_detail) {
            this.$toast.error(error.response.data.message_detail);
          } else {
            this.$toast.error('Có lỗi xảy ra khi hoàn thành phiếu');
          }
        }
      }
    },
    thuHoiU() {
      if (this.$refs.thuHoiUModal) {
        this.$refs.thuHoiUModal.dialogOpen();
      }
    },
    onUAllocated(uData) {
      this.loadRackData();
      this.loadAllocatedUs();
    },
    onPhieuThiCongLoaded(phieuThiCongDetail) {
      this.phieuThiCongDetail = phieuThiCongDetail;
      console.log('Phieu thi cong detail loaded:', this.phieuThiCongDetail);
    },
    xoaThuHoiU: async function (data) {
      if (!this.phieutc) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }

      let flag = await this.$confirm(
        `Bạn có chắc chắn muốn xoá thu hồi U "${data.TEN_U}" không?`,
        "Xác nhận xoá",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Huỷ",
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
          const requestBody = {
            idPhieuThiCong: this.phieutc,
            listOfIdUnit: data.ID_U.toString()
          };

          let response = await this.$root.context.post('/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/xoa-thuhoi-u', requestBody);
          
          if (response && response.error && response.error == "200") {
            this.$toast.success('Xoá thu hồi U thành công');
            await this.loadRackData();
            await this.loadAllocatedUs();
            this.selectedItems = [];
          } else {
            this.$toast.error('Xoá thu hồi U thất bại');
          }
        } catch (error) {
          console.error('Error deleting U allocation:', error);
          this.$toast.error('Có lỗi xảy ra khi thu hồi U');
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    xoaThuHoiUNhieuItem: async function () {
      if (!this.phieutc) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }

      if (this.selectedItems.length === 0) {
        this.$toast.warning('Vui lòng chọn ít nhất một item để xóa');
        return;
      }

      const selectedNames = this.selectedItems.map(item => item.TEN_U).join(', ');
      let flag = await this.$confirm(
        `Bạn có chắc chắn muốn xóa thu hồi ${this.selectedItems.length} U đã chọn không?<br/><strong>Danh sách:</strong> ${selectedNames}`,
        "Xác nhận xóa thu hồi U",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Huỷ",
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
          const idUList = this.selectedItems.map(item => item.ID_U).join(',');
          const requestBody = {
            idPhieuThiCong: this.phieutc,
            listOfIdUnit: idUList
          };

          let response = await this.$root.context.post('/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/xoa-thuhoi-u', requestBody);
          
          if (response && response.error && response.error == "200") {
            this.$toast.success(`Xóa thu hồi U thành công`);
            await this.loadRackData();
            await this.loadAllocatedUs();
            this.selectedItems = [];
          } else {
            this.$toast.error('Xóa thu hồi U thất bại');
          }
        } catch (error) {
          console.error('Error deleting multiple U allocations:', error);
          this.$toast.error('Có lỗi xảy ra khi xóa thu hồi U');
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    viewHistory(item) {
      this.historyData.khoaDuLieu = item.ID_U;
      this.showHistoryModal = true;
    },
    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                <button class="btn btn-sm btn-outline-info" title="Xem lịch sử" @click="viewHistory()">
                  <i class="fa fa-history"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger ml-1" title="Xóa" @click="deleteItem()" :disabled="isDeleteDisabled">
                  <i class="fa fa-trash"></i>
                </button>
              </div>`,
            data() {
              return {
                parentComponent: parent
              }
            },
            computed: {
              isDeleteDisabled() {
                return this.parentComponent.phieuThiCongDetail &&
                       (this.parentComponent.phieuThiCongDetail.TRANGTHAI_ID != 81 &&
                        this.parentComponent.phieuThiCongDetail.TRANGTHAI_ID != 22)
              }
            },
            methods: {
              viewHistory() {
                parent.viewHistory(this.data)
              },
              deleteItem() {
                parent.xoaThuHoiU(this.data)
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