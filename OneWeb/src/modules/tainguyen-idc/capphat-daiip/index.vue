<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import ModalCapIP from './ModalCapIP/index.vue'
import ModalCapDaiIP from './ModalCapDaiIP/index.vue'
import ModalCapThietBiVaPort from './ModalCapThietBiVaPort/index.vue'
import ModalChuyenXuly from './ModalChuyenXuLy/index.vue'
import ModalTraPhieu from './ModalTraPhieu/index.vue'
import ModalXemChiTiet from './ModalXemChiTiet/index.vue'
import ThongTinCongViec from '../components/ThongTinCongViec/index.vue'
import TabHistory from '../components/Tabs/TabHistory/index.vue'
import api from './api.js'

export default {
  name: 'CapPhatDaiIP',
  components: {
    ModalCapIP,
    ModalCapDaiIP,
    ModalCapThietBiVaPort,
    ModalChuyenXuly,
    ModalTraPhieu,
    ModalXemChiTiet,
    ThongTinCongViec,
    TabHistory,
  },
  data() {
    return {
      isCapPhatDaiIP: true,
      thongTinCongViec: {},
      danhSachDaiIP: [],
      selectedIPData: null,
      tab_index: 1,
      userInfo: {
        phanVungId: 0,
        nhanVienId: 0,
        nhanVienTen: '',
        donViId: 0,
        donViTen: '',
        donViChaId: 0,
        donViChaTen: ''
      },

      childGridDaiIP: {
        dataSource: [],
        queryString: 'THICONGIPSTORE_ID_CHA',
        columns: [
          { field: 'STT', headerText: 'STT', textAlign: 'Center', width: 80 },
          { field: 'ID_IP', headerText: 'ID IP', textAlign: 'Left', width: 150 },
          { field: 'DIACHI_IP', headerText: 'Địa chỉ IP', textAlign: 'Left', width: 180 },
          { field: 'LOAI_IP', headerText: 'Loại IP', textAlign: 'Left', width: 150 },
          { field: 'TEN_THIETBI', headerText: 'Thiết bị', textAlign: 'Left' },
          { field: 'TEN_PORT', headerText: 'Port', textAlign: 'Center', width: 150 },
          { field: 'TRANGTHAICAPPHAT', headerText: 'Trạng thái CCDV', textAlign: 'Center', width: 170 },
          { field: 'ACTIONS', headerText: 'Thao tác', textAlign: 'Center', template: this.actionColumn(), width: 120 }
        ],
        load: function () {
          const parentId = this.parentDetails.parentRowData["THICONGIPSTORE_ID"];
          this.parentDetails.parentKeyFieldValue = parentId;
          
          const filteredData = this.dataSource.filter(item => 
            item.THICONGIPSTORE_ID_CHA === parentId
          );
          
          this.dataSource = filteredData;
        }
      },
    }
  },
  computed: {
    isChonIPDisabled() {
      const soLuongIPYeuCau = parseInt(this.thongTinCongViec.SOLUONG_IP) || 0;
      if (soLuongIPYeuCau === 0) return false;

      const soLuongIPDaCapPhat = this.childGridDaiIP.dataSource.length;      
      return soLuongIPDaCapPhat >= soLuongIPYeuCau;
    },
    phieutc() {
      return Number(this.$route.query.phieutc)
    },
    // Thêm computed property để kiểm tra điều kiện ẩn button
    shouldHideButtons() {
      const viewOnly = this.$route.query.viewOnly;
      const trangThaiThucHien = this.thongTinCongViec.TRANGTHAI_THUCHIEN;

      // nếu ở trạng thái trả phiếu thì cũng chỉ cho xem
      if (trangThaiThucHien && trangThaiThucHien.toLowerCase().includes('trả phiếu')) {
        return true;
      }

      // nếu user login khác với người thực hiện thì cũng chỉ xem
      if (this.$root.token.getNhanVienID() != this.thongTinCongViec.NHANVIEN_TH_ID && this.thongTinCongViec.TRANGTHAI_ID_PHIEU_YC != 21) {
        return true;
      }
      
      // Kiểm tra viewOnly = 1
      if (viewOnly === '1' || viewOnly === 1) {
        return true;
      }
      
      // Kiểm tra trạng thái thực hiện có chứa "hoàn thành"
      if (trangThaiThucHien && trangThaiThucHien.toLowerCase().includes('hoàn thành')) {
        return true;
      }

      // Kiểm tra isEditMode - nếu không phải edit mode thì chỉ cho xem
      if (!this.isEditMode) {
        return true;
      }

      // Nếu đang ở trạng thái tiếp nhận (ID = 21) mà user khác user đang login
      const trangThaiIdPhieuYc = this.thongTinCongViec.TRANGTHAI_ID_PHIEU_YC;
      const nhanVienTaoId = this.thongTinCongViec.NHANVIEN_TAO_ID;
      const currentUserId = this.$root.token.getNhanVienID();
      if (trangThaiIdPhieuYc == 21 && nhanVienTaoId && currentUserId != nhanVienTaoId) {
        return true;
      }
      
      return false;
    },
    isNhiemVuMoi() {
      if (!this.thongTinCongViec.TRANGTHAI_THUCHIEN) {
        return false;
      }
      return this.thongTinCongViec.TRANGTHAI_THUCHIEN.toLowerCase().includes('mới');
    },
    // computed property để kiểm tra data grid có rỗng không
    isDataGridEmpty() {
      return !this.danhSachDaiIP || this.danhSachDaiIP.length === 0;
    },
    isDataChildGridEmpty() {
      // Nếu không có data thì return true
      if (!this.childGridDaiIP.dataSource || this.childGridDaiIP.dataSource.length === 0) {
        return true;
      }
      // Kiểm tra xem tất cả các IP có đều ở trạng thái "chờ cấp phát" không
      return this.childGridDaiIP.dataSource.every(item => {
        const trangThai = item.TRANGTHAICAPPHAT;
        return trangThai && trangThai.toLowerCase().includes('chờ cấp phát');
      });
    },
    // computed property để disable button Chuyển xử lý và Trả phiếu
    isChuyenXuLyDisabled() {
      return !this.isDataGridEmpty;
    },
    // computed property để disable button Hoàn thành
    isHoanThanhDisabled() {
      return this.isDataGridEmpty || this.isDataChildGridEmpty;
    },
    isEditMode() {
      const isEditMode = this.$route.query.isEditMode;
      return isEditMode === 'true' || isEditMode === true;
    },
  },
  async mounted() {
    await this.loadUserInfo();
    await this.loadThongTinCongViec();
    await this.loadDanhSachDaiIP();
  },
  methods: {
    onChangeTab: function(index) {
      this.tab_index = index

      // Gọi refreshData khi click vào tab Lịch sử tác động (tab_index = 2)
      if (index === 2 && this.$refs.tabHistory) {
        this.$refs.tabHistory.refreshData();
      }
    },
    
    async loadThongTinCongViec() {
      try {
        this.$root.showLoading(true);
        const phieutcId = this.$route.params.phieutc || this.$route.query.phieutc;
        const response = await api.getDanhSachCongViec(this.$root.context, phieutcId);
        if (response.data.length > 0) {
          this.thongTinCongViec = response.data[0];
          this.isCapPhatDaiIP = response.data[0].LOAI_CV.toLowerCase().includes('dải ip');
        } else {
          console.error('API trả về dữ liệu không hợp lệ:', response.data);
          this.$toast.error('Không thể tải thông tin công việc');
          if (response.data && response.data.message) {
            this.$toast.error(response.data.message);
          }
        }
      } catch (error) {
        console.error('Lỗi khi gọi API load thông tin công việc:', error);
        if (error.response) {
          const status = error.response.status;
          const message = error.response.data?.message || 'Lỗi server';
          this.$toast.error(`Lỗi ${status}: ${message}`);
        } else if (error.request) {
          this.$toast.error('Không thể kết nối đến server');
        }
      } finally {
        this.$root.showLoading(false)
      }
    },

    openModalCapPhat() {
      if (this.isChonIPDisabled) {
        this.$toast.warning('Đã cấp phát đủ số lượng IP yêu cầu');
        return;
      }
      
      if (this.isCapPhatDaiIP) {
        this.$refs.ModalCapDaiIP.openDialog();
      } else {
        this.$refs.ModalCapIP.openDialog();
      }
    },

    actionColumnDaiIP() {
      const self = this;
      return function () {
        // Kiểm tra điều kiện ẩn button
        if (self.shouldHideButtons) {
          return {
            template: {
              template: `<div></div>`
            }
          };
        }

        if (self.isNhiemVuMoi) {
          return `<div></div>`;
        }
        
        return {
          template: {
              template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-danger ml-1" title="Xóa dải IP" @click="onXoaDaiIP()">
                  <i class="fa fa-trash"></i> Gỡ
                </button>
              </div>`,
              methods: {
                onXoaDaiIP: function () {
                  const rowData = this.dataItem || this.rowData || this.data || this;
                  self.xoaDaiIP(rowData);
                }
              }
          },
        }
      };
    },

    actionColumn() {
      const self = this;
      return function () {

        if (self.isNhiemVuMoi) {
          return `<div></div>`;
        }
        
        if (self.shouldHideButtons) {
            return {
              template: {
                template: `<div>
                  <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="xemChiTiet()">
                    <i class="fa fa-eye"></i> Chi tiết
                  </button>
                </div>`,
                methods: {
                  xemChiTiet() {
                    const rowData = this.dataItem || this.rowData || this.data || this;
                    const parentRowData = this.parentDetails?.parentRowData || this.parentRowData || {};
                    self.selectedIPData = {
                      ...rowData,
                      parentData: parentRowData
                    };
                    self.openModalXemChiTiet();
                  }
                }
              },
            };
          }
          
          return {
              template: {
                  template: `<div class="action-buttons gap-1">
                    <button class="btn btn-sm btn-outline-success" title="Cấp phát thiết bị" @click="onCapPhatThietBi()">
                      <i class="fa fa-cogs"></i> Cấp phát
                    </button>
                  </div>`,
                  methods: {
                    onCapPhatThietBi: function () {
                      const rowData = this.dataItem || this.rowData || this.data || this;
                      const parentRowData = this.parentDetails?.parentRowData || this.parentRowData || {};

                      self.selectedIPData = {
                        ...rowData,
                        parentData: parentRowData
                      };
                      self.openModalCapPhatThietBiVaPort();
                    }
                  }
              },
          };
      };
    },

    openModalCapPhatThietBiVaPort() {
      if (!this.selectedIPData) {
        this.$toast.warning('Vui lòng chọn IP để cấp phát thiết bị');
        return;
      }
      this.$refs.ModalCapThietBiVaPort.openDialog();
    },

    openModalXemChiTiet() {
      if (!this.selectedIPData) {
        this.$toast.warning('Vui lòng chọn IP để xem chi tiết');
        return;
      }
      const currentThietBiId = this.selectedIPData.THIETBI_ID || null;
      const currentCongId = this.selectedIPData.CONG_ID || null;
      this.$refs.ModalXemChiTiet.openDialog({
        currentThietBiId: currentThietBiId,
        currentCongId: currentCongId,
        thongtinIP: this.selectedIPData,
      });
    },

    async xoaDaiIP(rowData) {
      try {
        const confirmed = await this.$confirm(
          'Bạn có chắc chắn muốn xóa dải IP này không?',
          'Xác nhận xóa',
          {
            confirmButtonText: 'Xóa',
            cancelButtonText: 'Hủy',
            type: 'warning'
          }
        );

        if (!confirmed) return;
        this.$root.showLoading(true);
        await api.deleteDaiIP(this.$root.context, rowData.THICONGIPSTORE_ID);
        await this.loadDanhSachDaiIP();
      } catch (error) {
        console.error('Lỗi khi xóa dải IP:', error);
        if (error.response) {
          const status = error.response.status;
          const message = error.response.data?.message || 'Lỗi server';
          this.$toast.error(`Lỗi ${status}: ${message}`);
        } else if (error.request) {
          this.$toast.error('Không thể kết nối đến server');
        }
      } finally {
        this.$root.showLoading(false);
      }
    },

    async loadDanhSachDaiIP() {
      try {
        await this.loadThongTinCongViec();
        this.$root.showLoading(true);
        const phieutcId = this.$route.params.phieutc || this.$route.query.phieutc;
        const response = await api.getDanhSachDaiIPDaThiCong(this.$root.context, phieutcId);
        const danhSachIPChiTiet = [];
        
        this.danhSachDaiIP = [];
        this.childGridDaiIP.dataSource = [];
        if (response.data && response.data.length > 0) {
          this.danhSachDaiIP = response.data;
          for (const item of response.data) {
            try {
              const chiTietResponse = await api.getDanhSachIPTheoDaiIPDaThiCong(this.$root.context, item.THICONGIPSTORE_ID);
              if (chiTietResponse.data && Array.isArray(chiTietResponse.data)) {
                chiTietResponse.data.forEach(ip => {
                  const childItem = {
                    ...ip,
                    THICONGIPSTORE_ID_CHA: item.THICONGIPSTORE_ID
                  };
                  danhSachIPChiTiet.push(childItem);
                });
              }
            } catch (error) {
              console.error(`Lỗi khi load chi tiết IP cho dải IP ${item.THICONGIPSTORE_ID}:`, error);
            }
          }
          this.childGridDaiIP.dataSource = danhSachIPChiTiet;
        } else {
          this.danhSachDaiIP = [];
          this.childGridDaiIP.dataSource = [];
        }
      } catch (error) {
        console.error('Lỗi khi gọi API load danh sách dải IP:', error);
        this.danhSachDaiIP = [];
        this.childGridDaiIP.dataSource = [];
      } finally {
        this.$root.showLoading(false);
      }
    },

    detailDataBound(args) {
      const parentId = args.data.THICONGIPSTORE_ID;
      const childData = this.childGridDaiIP.dataSource.filter(item => 
        item.THICONGIPSTORE_ID_CHA === parentId
      );
      
      const detailGrid = args.detailElement.querySelector('.e-grid');
      if (detailGrid) {
        // Thêm style cho child grid
        detailGrid.style.border = '1px solid #e0e0e0';
        detailGrid.style.borderRadius = '4px';
        detailGrid.style.margin = '10px';
        detailGrid.style.backgroundColor = '#fafafa';
        
        // Set data source cho child grid
        const gridInstance = detailGrid.ej2_instances[0];
        if (gridInstance) {
          gridInstance.dataSource = childData;
          gridInstance.rowSelected = (childArgs) => {
            console.log('Child row selected:', childArgs);
          };
        }
      }
      
      // Thêm header cho child grid
      const parentData = args.data;
      if (parentData) {
        // Đếm số IP con dựa trên dataSource của childGrid
        const childCount = this.childGridDaiIP.dataSource.filter(child => 
          child.THICONGIPSTORE_ID_CHA === parentData.THICONGIPSTORE_ID
        ).length;
        
        if (childCount > 0) {
          const headerElement = args.detailElement.querySelector('.e-gridheader');
          if (headerElement) {
            const customHeader = document.createElement('div');
            customHeader.innerHTML = `
              <div style="padding: 10px; background-color: #f5f5f5; border-bottom: 1px solid #ddd; font-weight: bold; color: #333;">
                <i class="fa fa-server" style="margin-right: 5px;"></i>
                Chi tiết IP trong dải: ${parentData.DAI_IP_SUBNET || parentData.TEN_DAI_IP || 'N/A'} (${childCount} IP con)
              </div>
            `;
            args.detailElement.insertBefore(customHeader, args.detailElement.firstChild);
          }
        }
      }
    },

    async loadUserInfo() {
      try {
        this.userInfo.nhanVienId = await this.$root.token.getNhanVienID()
        this.userInfo.nhanVienTen = this.$root.context.user.getProperty('ten_nv')
        this.userInfo.donViId = this.$root.context.user.getProperty("donvi_id");
        this.userInfo.donViTen = this.$root.context.user.getProperty('ten_dv')
        this.userInfo.donViChaId = this.userInfo.donViId
        this.userInfo.donViChaTen = this.userInfo.donViTen

        let ds = await this.apiGetDonViCha(this.userInfo.donViId)
        if (ds != null && ds.length > 0) {
          this.userInfo.donViChaId = ds[0].DONVI_ID
          this.userInfo.donViChaTen = ds[0].TEN_DV
        }
      } catch (error) {
        console.error('Lỗi khi load thông tin user:', error);
      }
    },

    apiGetDonViCha: async function (pId) {
      let result = []
      if (pId == null || pId < 1) {
        return
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/don-vi-cha', { donViId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    navigateWithQuery(path, queryParams = {}) {
      this.$router.push({
        path: path,
        query: queryParams
      });
    },

    onGhiLai: function() {
      this.$toast.success('Đã ghi lại thông tin');
      this.navigateWithQuery('/tainguyen-idc/kq-capphat-ip', {
        loaicv: this.thongTinCongViec.LOAICV_ID
      });
    },

    onChuyenXuLy: function() {
      const phieutcId = this.$route.params.phieutc || this.$route.query.phieutc;
      this.$refs.ModalChuyenXuly.show(this.thongTinCongViec, 'ADD', phieutcId);
    },

    chuyenXylySuccess: async function() {
      this.navigateWithQuery('/tainguyen-idc/kq-capphat-ip', {
        loaicv: this.thongTinCongViec.LOAICV_ID
      });
    },
    
    async onHoanThanh() {
      try {
        let flag = await this.$confirm(
          "Bạn có muốn hoàn thành công việc này không ?",
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        ).then(async() => {
            this.$root.showLoading(true);
            await api.setHoanThanhPhieu(this.$root.context, this.phieutc);
            this.navigateWithQuery('/tainguyen-idc/kq-capphat-ip', {
              loaicv: this.thongTinCongViec.LOAICV_ID
            });
          })
          .catch((e) => {
            this.$root.toastError("Lưu thất bại");
            this.$root.showLoading(false);
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            this.$root.toastSuccess("Lưu thành công");
          } finally {
            this.$root.showLoading(false);
          }
        }
      } catch (error) {
        console.error('Lỗi khi hoàn thành:', error);
        this.$toast.error('Có lỗi xảy ra khi hoàn thành công việc');
      }
    },

    async onTraPhieu() {
      try {
        this.$refs.ModalTraPhieu.show(this.thongTinCongViec, 'ADD', this.phieutc);
      } catch (error) {
        console.error('Lỗi khi trả phiếu:', error);
        this.$toast.error('Có lỗi xảy ra khi trả phiếu');
      }
    },

    traPhieuSuccess: async function() {
      this.navigateWithQuery('/tainguyen-idc/kq-capphat-ip', {
        loaicv: this.thongTinCongViec.LOAICV_ID
      });
    },

    onBackToResult: function() {
      this.navigateWithQuery('/tainguyen-idc/kq-capphat-ip', {
        loaicv: this.thongTinCongViec.LOAICV_ID
      });
    },

    onSaveTTCongViec: async function() {
      let rs = await this.$refs.thongTinCongViec.onSave();
      if (rs) {
        await this.loadThongTinCongViec();
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
          await this.loadThongTinCongViec();
        }
      })
    },

    onXacNhanThucHien() {
      this.$confirm('Xác nhận thực hiện phiếu thi công này?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        let rs = await this.$refs.thongTinCongViec.xacNhanThucHien()
        if (rs) {
          await this.loadThongTinCongViec();
        }
      })
    },

  }
}
</script>
