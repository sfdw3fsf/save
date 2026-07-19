<template src="./index.html"></template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import ThongTinCongViec from '@/modules/tainguyen-idc/components/ThongTinCongViec/index.vue';
import CapPhatRackModal from './modals/capphat-rack-modal.vue';
import ChuyenXuLy from './modals/ChuyenXuLy.vue';
import TraPhieu from './modals/TraPhieu.vue';
import axios from 'axios';
export default {
  components: {
    DataGrid,
    ThongTinCongViec,
    CapPhatRackModal,
    ChuyenXuLy,
    TraPhieu,
  },
  props: {
  },
  data: function () {
    return {
      phieutc: null,
      //maThueBao: null,
      isViewOnly: false,
      //hdtbId: null,
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
      //trangThaiId: null,
      //nhanvienThId: null,
      //giaTriKhaiBao: null,
      isLoadingDetail: false, // Flag để kiểm soát gọi API
      currentTab: 1, // Tab hiện tại, mặc định là tab 1
      historyRackList: [],
      dataGrid2Items: [],
      dataGrid2Columns: [
        {fieldName: 'RACK_ID', headerText: 'ID', width: 80, textAlign: 'Center'},
        {fieldName: 'TEN_RACK', headerText: 'Tên Rack', textAlign: 'Left'},
        {fieldName: 'TEN_LOAIRACK', headerText: 'Loại Rack', textAlign: 'Left'},
        {fieldName: 'MA_TS', headerText: 'Mã tài sản', width: 100, textAlign: 'Left'},
        {fieldName: 'TRANGTHAI_VH', headerText: 'Trạng thái vận hành', textAlign: 'Left'},
        {fieldName: 'TEN_QUYHOACH', headerText: 'Trạng thái theo quy hoạch', textAlign: 'Left'},
        {fieldName: 'TEN_CCDV', headerText: 'Trạng thái rack CCDV', textAlign: 'Left'},
        {fieldName: 'TEN_IDC', headerText: 'IDC', textAlign: 'Left'},
        {fieldName: 'TEN_MATSAN', headerText: 'Mặt sàn', textAlign: 'Left'},
        {fieldName: 'TEN_PHONG', headerText: 'Phòng/Zone', textAlign: 'Left'},
        {fieldName: 'VITRI_RACK', headerText: 'Vị trí Rack', width: 100, textAlign: 'Left'},
        {fieldName: 'SOHUU_RACK', headerText: 'Sở hữu', width: 100, textAlign: 'Left'},
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

  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.phieutc = this.$route.query.phieutc ? parseInt(this.$route.query.phieutc) : null;
      await this.getHistoryList();
      console.log('gia tri khaibao', this.phieuThiCongDetail.GIATRI_KHAIBAO);
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
          this.isViewOnly =
            !(newDetail.TRANGTHAI_ID == 81 || newDetail.TRANGTHAI_ID == 21 || newDetail.TRANGTHAI_ID == 22) ||
            (newDetail.TRANGTHAI_ID == 21 ? newDetail.NHANVIEN_TAO_ID != this.$root.token.getNhanVienID() : false) ||
            (newDetail.TRANGTHAI_ID == 81 ? newDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID() : false) ||
            (newDetail.TRANGTHAI_ID == 22 ? newDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID() : false);
        }
      },
      deep: true,
      immediate: true
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
        console.log('So', this.dataGrid2Items.length, this.phieuThiCongDetail.GIATRI_KHAIBAO);
        // Nếu cần reload history, gọi lại getHistoryList() ở đây
        this.isLoadingDetail = false;
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
          thamSo2:this.phieutc,
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
    loadAllocatedRacks: async function () {
      try {
        let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-rack/ds-racks-da-capphat/${this.phieutc}`);
        if (response && response.data) {
          this.dataGrid2Items = response.data;
          //this.rackId = this.dataGrid2Items.length > 0 ? this.dataGrid2Items[0].RACK_ID : null;
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
        this.$refs.popupChuyenXuly.show(this.phieuThiCongDetail,'ADD', this.phieutc );
      } catch (error) {
        console.error('Lỗi khi chuyển xử lý:', error);
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
          response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-rack/hoan-thanh/${this.phieutc}`);
          rs = response.data;
          if (response || rs) {
            this.$toast.success('Hoàn thành phiếu thành công');
            this.$router.push({
              path: '/tainguyen-idc/ketqua-capphat-rack'
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
    capPhatRack() {
      if (this.$refs.capPhatRackModal) {
        this.$refs.capPhatRackModal.dialogOpen();
      }
    },
    onRackAllocated(rackData) {
      this.loadAllocatedRacks();
    },
    xoaCapPhatRack: async function (data) {
      if (!this.phieutc) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }

      let flag = await this.$confirm(
        `Bạn có chắc chắn muốn gỡ cấp phát rack "${data.TEN_RACK}" không?`,
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
          const requestBody = {
            idPhieuThiCong: this.phieutc,
            idRack: data.RACK_ID
          };

          let response = await this.$root.context.post('/web-ecms/idc/thi-cong/cap-phat-rack/xoa-capphat-rack', requestBody);

          if (response && response.error && response.error == "200") {
            this.$toast.success('Gỡ cấp phát Rack thành công');
            await this.loadAllocatedRacks();
          } else {
            this.$toast.error('Gỡ cấp phát Rack thất bại');
          }
        } catch (error) {
          console.error('Error deleting rack allocation:', error);
          this.$toast.error('Có lỗi xảy ra khi gỡ cấp phát Rack');
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
               <button class="btn btn-sm btn-outline-danger ml-1" title="Gỡ cấp phát" @click="deleteItem()" :disabled="isViewOnly">
                  <i class="fa fa-trash"></i> Gỡ
                </button>
              </div>`,
            computed: {
              isViewOnly() {
                return  !(parent.phieuThiCongDetail.TRANGTHAI_ID == 81 || parent.phieuThiCongDetail.TRANGTHAI_ID == 22); // Đảm bảo parent.isViewOnly được truy cập đúng
              }
            },
            methods: {
              deleteItem() {
                parent.xoaCapPhatRack(this.data)

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
