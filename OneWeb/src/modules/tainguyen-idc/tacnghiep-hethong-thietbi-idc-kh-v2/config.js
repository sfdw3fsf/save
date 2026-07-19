import { LOAI_CONG_VIEC, TRANG_THAI_PHIEU } from './const'
/**
 * Configuration file cho màn hình Tác nghiệp hệ thống thiết bị IDC/Khách hàng
 *
 * Chứa toàn bộ logic điều kiện hiển thị buttons và cấu hình DataGrid columns
 * dựa trên:
 * - Role: người tạo vs người thực hiện
 * - Trạng thái phiếu: 81 (Nhiệm vụ mới), 22 (Đang thực hiện), 21 (Tiếp nhận)
 * - Loại công việc: 50 (Lắp đặt), 23 (Tháo dỡ), 52 (Thay đổi)
 * - Loại khách hàng: KH vs IDC
 */

/**
 * Cấu hình cho buttons và permissions
 * @param {Object} params - Tham số đầu vào
 * @param {number} params.currentUserId - ID user hiện tại
 * @param {boolean} params.isNguoiTao - Có phải là người tạo không
 * @param {boolean} params.isThucHien - Có phải là người thực hiện không
 * @param {boolean} params.isViewOnly - Chế độ chỉ xem
 * @param {number} params.trangThaiId - ID trạng thái phiếu
 * @param {number} params.loaiCvId - ID loại công việc
 * @param {boolean} params.isKhachHang - Là khách hàng hay IDC
 * @param {boolean} params.thiCongHeThongSelected - Đã chọn hệ thống thi công
 * @param {boolean} params.thietBiKhachHangSelected - Đã chọn thiết bị
 * @returns {Object} Configuration object cho buttons
 */
export function createButtonConfig({
  currentUserId,
  isThucHien,
  isNguoiTao,
  isViewOnly,
  trangThaiId,
  loaiCvId,
  isKhachHang,
  thiCongHeThongSelected,
  thietBiKhachHangSelected
}) {
  const isThaoTacHeThong =
    isNguoiTao &&
    trangThaiId == TRANG_THAI_PHIEU.TIEP_NHAN && !isKhachHang &&
    (!isKhachHang || loaiCvId === LOAI_CONG_VIEC.LAP_DAT)

    const onlyAddNewHeThongKhachHang =
    isNguoiTao &&
    isKhachHang &&
    !thiCongHeThongSelected &&
    trangThaiId == TRANG_THAI_PHIEU.NHIEM_VU_MOI

  const isThaoTacThietBi =
    isNguoiTao &&
    !!thiCongHeThongSelected &&
    !thietBiKhachHangSelected &&
    (!isKhachHang || loaiCvId === LOAI_CONG_VIEC.LAP_DAT) &&
    trangThaiId == TRANG_THAI_PHIEU.TIEP_NHAN && !isKhachHang

    const onlyAddNewThietBiKhachHang =
    isNguoiTao &&
    !!thiCongHeThongSelected &&
    !thietBiKhachHangSelected &&
    trangThaiId == TRANG_THAI_PHIEU.NHIEM_VU_MOI

  return {
    // Buttons ở top actions
    topActions: {
      ghiLai: {
        visible: trangThaiId == TRANG_THAI_PHIEU.TIEP_NHAN,
        enabled: !isViewOnly && isNguoiTao
      },
      chuyenThucHien: {
        visible: trangThaiId == TRANG_THAI_PHIEU.TIEP_NHAN,
        enabled: !isViewOnly && isNguoiTao
      },
      chuyenXuLy: {
        visible: trangThaiId == TRANG_THAI_PHIEU.NHIEM_VU_MOI || trangThaiId == TRANG_THAI_PHIEU.TIEP_NHAN,
        enabled: !isViewOnly && isThucHien
      },
      traPhieu: {
        visible: trangThaiId == TRANG_THAI_PHIEU.DANG_THUC_HIEN,
        enabled: !isViewOnly && isThucHien
      },
      xacNhanThucHien: {
        visible: trangThaiId == TRANG_THAI_PHIEU.NHIEM_VU_MOI,
        enabled: !isViewOnly && isThucHien
      },
      hoanThanh: {
        visible: trangThaiId == TRANG_THAI_PHIEU.DANG_THUC_HIEN,
        enabled: !isViewOnly && isThucHien
      }
    },

    // Buttons cho hệ thống thiết bị
    heThongActions: {
      themHeThong: {
        visible: loaiCvId == LOAI_CONG_VIEC.LAP_DAT && !isViewOnly,
        enabled: onlyAddNewHeThongKhachHang ? true : isThaoTacHeThong
      },
      chonHeThong: {
        visible: !isViewOnly,
        enabled: isThaoTacHeThong
      }
    },

    // Buttons cho thiết bị
    thietBiActions: {
      themThietBi: {
        visible: loaiCvId == LOAI_CONG_VIEC.LAP_DAT && !isViewOnly,
        enabled: onlyAddNewThietBiKhachHang ? true : isThaoTacThietBi
      },
      chonThietBi: {
        visible: !isViewOnly,
        enabled: isThaoTacThietBi
      }
    }
  }
}

/**
 * Columns cơ bản cho hệ thống (khách hàng)
 */
export const BASE_SYSTEM_COLUMNS_KH = [
  { fieldName: 'HETHONGTBI_ID', headerText: 'ID Hệ thống', textAlign: 'Left', width: 120 },
  { fieldName: 'TEN', headerText: 'Tên hệ thống', textAlign: 'Left', width: 250 },
  { fieldName: 'IDC', headerText: 'IDC', textAlign: 'Left', width: 120 },
  { fieldName: 'TEN_KH', headerText: 'Khách hàng', textAlign: 'Left', width: 180 },
  { fieldName: 'MA_TB', headerText: 'Mã thuê bao', textAlign: 'Left', width: 150 },
  { fieldName: 'TRANG_THAI_VAN_HANH', headerText: 'Trạng thái vận hành', textAlign: 'Left', width: 150 }
]

/**
 * Columns cơ bản cho hệ thống (IDC)
 */
export const BASE_SYSTEM_COLUMNS_IDC = [
  { fieldName: 'HETHONGTBI_ID', headerText: 'ID Hệ thống', textAlign: 'Left', width: 120 },
  { fieldName: 'TEN', headerText: 'Tên hệ thống', textAlign: 'Left', width: 250 },
  { fieldName: 'IDC', headerText: 'IDC', textAlign: 'Left', width: 120 },
  { fieldName: 'DONVI_QL', headerText: 'Đơn vị quản lý', textAlign: 'Left', width: 180 },
  { fieldName: 'NHANVIEN_QL', headerText: 'Nhân viên quản lý', textAlign: 'Left', width: 150 },
  { fieldName: 'TRANG_THAI_VAN_HANH', headerText: 'Trạng thái vận hành', textAlign: 'Left', width: 150 }
]

/**
 * Columns cơ bản cho thiết bị
 */
export const BASE_DEVICE_COLUMNS = [
  { fieldName: 'THIETBI_ID', headerText: 'ID Thiết bị', textAlign: 'Left', width: 120 },
  { fieldName: 'TEN_THIET_BI', headerText: 'Tên thiết bị', textAlign: 'Left' },
  { fieldName: 'SERIAL_NUMBER', headerText: 'Serial', textAlign: 'Left' },
  { fieldName: 'CS_DINHDANH', headerText: 'CS Thiết kế', textAlign: 'Left' },
  { fieldName: 'TOA_NHA', headerText: 'Tòa nhà', textAlign: 'Left', width: 120 },
  { fieldName: 'MATSAN', headerText: 'Mặt sàn', textAlign: 'Left', width: 120 },
  { fieldName: 'PHONG', headerText: 'Phòng/Zone', textAlign: 'Left' },
  { fieldName: 'RACK', headerText: 'Rack', textAlign: 'Left', width: 120 },
  { fieldName: 'U', headerText: 'U', textAlign: 'Center', width: 120 },
  { fieldName: 'TRANG_THAI_VAN_HANH', headerText: 'Trạng thái vận hành', textAlign: 'Left' }
]

/**
 * Tạo action column dựa trên loại CV và view mode
 * @param {Object} params - Tham số đầu vào
 * @param {string} params.type - Loại column ('system' hoặc 'device')
 * @param {boolean} params.isView - Chế độ xem
 * @param {number} params.loaiCvId - ID loại công việc
 * @param {Object} params.componentContext - Context của component để gọi methods
 * @param {boolean} params.anCotThaoTac - Ẩn cột thao tác
 * @returns {Object|null} Action column configuration
 */
export function createActionColumn({ type, isView = false, loaiCvId, componentContext, anCotThaoTac }) {
  // map loaiCvId -> action key for template
  const mapLoaiCvToAction = (id) => {
    switch (id) {
      case LOAI_CONG_VIEC.LAP_DAT:
        return 'lapDat'
      case LOAI_CONG_VIEC.THAO_DO:
        return 'thaoDo'
      case LOAI_CONG_VIEC.THAY_DOI:
        return 'thayDoi'
      default:
        return null
    }
  }

  if (isView) {
    return {
      fieldName: 'ACTIONS',
      headerText: 'Thao tác',
      textAlign: 'Center',
      template: createTemplateActionColumn(componentContext, type, 'view')
    }
  }

  const action = mapLoaiCvToAction(loaiCvId)
  if (!action || anCotThaoTac) return null

  return {
    fieldName: 'ACTIONS',
    headerText: 'Thao tác',
    textAlign: 'Center',
    template: createTemplateActionColumn(componentContext, type, action)
  }
}

/**
 * Cấu hình cho DataGrid columns và điều kiện hiển thị
 * @param {Object} params - Tham số đầu vào
 * @param {number} params.trangThaiId - ID trạng thái phiếu
 * @param {number} params.loaiCvId - ID loại công việc
 * @param {boolean} params.isKhachHang - Là khách hàng hay IDC
 * @param {boolean} params.isViewOnly - Chế độ chỉ xem
 * @param {Object} params.componentContext - Context của component
 * @param {boolean} params.anCotThaoTac - Ẩn cột thao tác
 * @returns {Object} Configuration object cho DataGrid
 */
export function createDataGridConfig({
  trangThaiId,
  loaiCvId,
  isKhachHang,
  isViewOnly,
  componentContext,
  anCotThaoTac
}) {
  // Tạo action columns
  const systemActionColumn = createActionColumn({
    type: 'system',
    isView: isViewOnly,
    loaiCvId,
    componentContext,
    anCotThaoTac
  })

  const deviceActionColumn = createActionColumn({
    type: 'device',
    isView: isViewOnly,
    loaiCvId,
    componentContext,
    anCotThaoTac
  })
  return {
    // Cấu hình cho hệ thống
    heThongColumns: {
      khachHang: isKhachHang ? [...BASE_SYSTEM_COLUMNS_KH, systemActionColumn].filter(Boolean) : null,
      idc: !isKhachHang ? [...BASE_SYSTEM_COLUMNS_IDC, systemActionColumn].filter(Boolean) : null
    },

    // Cấu hình cho thiết bị
    thietBiColumns: [...BASE_DEVICE_COLUMNS, deviceActionColumn].filter(Boolean),

    // Điều kiện hiển thị cho từng grid
    shouldShowGrid: {
      heThongKH_LapDat: isKhachHang && loaiCvId == LOAI_CONG_VIEC.LAP_DAT && !isViewOnly,
      heThongKH_ThaoDo: isKhachHang && loaiCvId == LOAI_CONG_VIEC.THAO_DO && !isViewOnly,
      heThongKH_ThayDoi: isKhachHang && loaiCvId == LOAI_CONG_VIEC.THAY_DOI && !isViewOnly,
      heThongKH_View: isKhachHang && isViewOnly,

      heThongIDC_LapDat: !isKhachHang && loaiCvId == LOAI_CONG_VIEC.LAP_DAT && !isViewOnly,
      heThongIDC_ThaoDo: !isKhachHang && loaiCvId == LOAI_CONG_VIEC.THAO_DO && !isViewOnly,
      heThongIDC_ThayDoi: !isKhachHang && loaiCvId == LOAI_CONG_VIEC.THAY_DOI && !isViewOnly,
      heThongIDC_View: !isKhachHang && isViewOnly,

      thietBiKH_LapDat: loaiCvId == LOAI_CONG_VIEC.LAP_DAT && isKhachHang && !isViewOnly,
      thietBiIDC_LapDat: loaiCvId == LOAI_CONG_VIEC.LAP_DAT && !isKhachHang && !isViewOnly,

      thietBiKH_ThaoDo: loaiCvId == LOAI_CONG_VIEC.THAO_DO && isKhachHang && !isViewOnly,
      thietBiIDC_ThaoDo: loaiCvId == LOAI_CONG_VIEC.THAO_DO && !isKhachHang && !isViewOnly,

      thietBiKH_ThayDoi: loaiCvId == LOAI_CONG_VIEC.THAY_DOI && isKhachHang && !isViewOnly,
      thietBiIDC_ThayDoi: loaiCvId == LOAI_CONG_VIEC.THAY_DOI && !isKhachHang && !isViewOnly,
      thietBi_View: isViewOnly
    }
  }
}

export function createTemplateActionColumn(parent, type, action) {
  const config = {
    device: {
      lapDat: {
        label: 'Lắp đặt thiết bị',
        icon: 'fa fa-cogs',
        btnClass: 'btn-outline-primary',
        handler: 'lapDatThietBi'
      },
      thaoDo: {
        label: 'Tháo dỡ thiết bị',
        icon: 'fa fa-cogs',
        btnClass: 'btn-outline-danger',
        handler: 'thaoDoThietBi'
      },
      thayDoi: {
        label: 'Thay đổi lắp đặt',
        icon: 'fa fa-cogs',
        btnClass: 'btn-outline-success',
        handler: 'thayDoiThietBi'
      },
      view: {
        label: 'Xem thông tin lắp đặt',
        icon: 'fa fa-cogs',
        btnClass: 'btn-outline-primary',
        handler: 'viewThietBi'
      }
    },
    system: {
      lapDat: {
        label: 'Lắp đặt hệ thống',
        icon: 'fa fa-cogs',
        btnClass: 'btn-outline-primary',
        handler: 'lapDatHeThong'
      },
      thaoDo: {
        label: 'Tháo dỡ hệ thống',
        icon: 'fa fa-cogs',
        btnClass: 'btn-outline-danger',
        handler: 'thaoDoHeThong'
      },
      thayDoi: {
        label: 'Thay đổi thông tin',
        icon: 'fa fa-cogs',
        btnClass: 'btn-outline-success',
        handler: 'thayDoiHeThong'
      },
      view: {
        label: 'Xem thông tin hệ thống',
        icon: 'fa fa-eye',
        btnClass: 'btn-outline-info',
        handler: 'viewHeThong'
      }
    }
  }

  const { label, icon, btnClass, handler } = config[type][action]

  return function() {
    return {
      template: {
        data() {
          return {
            label,
            icon,
            btnClass,
            handler,
            action,
            type
          }
        },
        template: `
        <div class="action-buttons">
          <span v-if="isThaoDo" class="badge badge-danger">Đã tháo dỡ</span>
          <span v-else-if="isLapDat" class="badge badge-success">Đã lắp đặt</span>
          <button v-else
            class="btn btn-sm" 
            :class="btnClass" 
            :title="label" 
            @click="handleClick"
          >
            <i :class="icon"></i> {{ label }}
          </button>
        </div>
      `,
        methods: {
          handleClick() {
            parent[handler](this.data)
          },
          isMode(action, type) {
            return this.action === action && this.type === type
          }
        },
        computed: {
          isThaoDo() {
            return (
              (this.isMode('thaoDo', 'system') && this.data.NGAY_THAOGO) ||
              (this.isMode('thaoDo', 'device') && this.data.MATSAN == null)
            )
          },
          isLapDat() {
            return (
              (this.isMode('lapDat', 'system') &&
                this.data.NGAY_THAOGO == null &&
                this.data.CANCU_YC != null &&
                this.data.NGAY_LD != null) ||
              (this.isMode('lapDat', 'device') && this.data.MATSAN != null)
            )
          }
        }
      }
    }
  }
}
