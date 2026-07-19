export const LOAI_THIETBI = {
  LINECARD: 1621,
  POWERCARD: 1622,
  CONSOLECARD: 1623,
  CARD: 9201
}
export const PHANLOAI_CONG = {
  CONG_DU_LIEU: 1,
  CONG_NGUON: 2,
  CONG_DIEU_KHIEN: 3,
  KHE_CAM: 4
}
export const LOAICONG_ID = {
  CONG_DU_LIEU: 504,
  CONG_NGUON: 505,
  CONG_DIEU_KHIEN: 506,
  KHE_CAM_DL: 501,
  KHE_CAM_NGUON: 502,
  KHE_CAM_DIEU_KHIEN: 503
}
export const TRANGTHAI_KN = {
  DA_KET_NOI: 142,
  CHUA_KET_NOI: 141
}
export const TRANGTHAI_PORT = {
  CHUA_SU_DUNG: 0,
  DANG_SU_DUNG: 1,
  HONG: 2
}
export const colsCongDuLieu = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  { fieldName: 'LOAICONG', headerText: 'Loại cổng', allowFiltering: true },
  { fieldName: 'BANGTHONG_TN', headerText: 'Băng thông', allowFiltering: true, width: 150, textAlign: 'Left' },
  { fieldName: 'MODULE', headerText: 'Module', allowFiltering: true, width: 180, textAlign: 'Left' },
  { fieldName: 'MUCDICHSD', headerText: 'Mục đích sử dụng', allowFiltering: true },
  { fieldName: 'DIACHI_IP', headerText: 'Địa chỉ IP', allowFiltering: true, width: 150, textAlign: 'Left' },
  {
    fieldName: 'TRANGTHAI_PORT',
    headerText: 'Trạng thái Port',
    allowFiltering: true,
    width: 150,
    textAlign: 'Left'
  },
  {
    fieldName: 'TRANGTHAI_KN',
    headerText: 'Trạng thái kết nối',
    allowFiltering: true,
    width: 150,
    textAlign: 'Left'
  },
  { fieldName: 'THIETBIKN', headerText: 'Thiết bị kết nối', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsDuLieu(parent)
  }
]
export const getColumnActionsDuLieu = (parent) => {
  return () => {
    return {
      template: {
        template: `
          <div class="d-flex align-items-center justify-content-center">
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onEdit()" title="Sửa" v-if="data.TRANGTHAIKN_ID !== 142">
              <i class="fa fa-edit"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onInfo()" title="Thông tin TB đấu nối" v-if="data.THIETBIKN_ID" >
              <i class="fa fa-plug"></i>
            </b-button>
            <b-button size="sm" variant="outline-danger" class="ml-1" @click="onDelete()" title="Xóa" v-if="data.TRANGTHAIKN_ID !== 142">
              <i class="fa fa-trash"></i>
            </b-button>
          </div>
        `,
        methods: {
          onInfo() {
            parent.onTTKetNoi(this.data)
          },
          onEdit() {
            console.log('onEdit CongDuLieu', this.data)
            parent.showThongTinCong(this.data)
          },
          onDelete() {
            parent.onXoaDuLieuPort(true)
          }
        }
      }
    }
  }
}
export const colsCongNguon = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  {
    fieldName: 'DD_DINHDANH',
    headerText: 'Dòng định danh (A)',
    allowFiltering: true,
    width: 150,
    textAlign: 'Left'
  },
  {
    fieldName: 'DA_DINHDANH',
    headerText: 'Điện áp định danh',
    allowFiltering: true,
    width: 150,
    textAlign: 'Left'
  },
  { fieldName: 'LOAI_NDIEN', headerText: 'Loại nguồn điện', allowFiltering: true },
  { fieldName: 'CHUAN_DN', headerText: 'Chuẩn đấu nối', allowFiltering: true },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái port', allowFiltering: true },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Thiết bị kết nối', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsNguon(parent)
  }
]
export const getColumnActionsNguon = (parent) => {
  return () => {
    return {
      template: {
        template: `
          <div class="d-flex align-items-center justify-content-center">
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onEditNguon()" title="Sửa" v-if="data.TRANGTHAIKN_ID !== 142">
              <i class="fa fa-edit"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onInfoNguon()" title="Thông tin TB đấu nối" v-if="data.THIETBIKN_ID">
              <i class="fa fa-plug"></i>
            </b-button>
            <b-button size="sm" variant="outline-danger" class="ml-1" @click="onDeleteNguon()" title="Xóa" v-if="data.TRANGTHAIKN_ID !== 142">
              <i class="fa fa-trash"></i>
            </b-button>
          </div>
        `,
        methods: {
          onInfoNguon() {
            parent.onTTKetNoi(this.data)
          },
          onEditNguon() {
            parent.showDialogCongNguon(this.data)
          },
          onDeleteNguon() {
            parent.onXoaDuLieuPort(true)
          }
        }
      }
    }
  }
}
export const colsCongDieuKhien = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  { fieldName: 'LOAICONG', headerText: 'Loại cổng', allowFiltering: true },
  { fieldName: 'MUCDICHSD', headerText: 'Mục đích sử dụng', allowFiltering: true },
  { fieldName: 'DIACHI_IP', headerText: 'Địa chỉ IP', allowFiltering: true, width: 150, textAlign: 'Left' },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái Port', allowFiltering: true },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Thiết bị kết nối', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsDieuKhien(parent)
  }
]
export const getColumnActionsDieuKhien = (parent) => {
  return () => {
    return {
      template: {
        template: `
          <div class="d-flex align-items-center justify-content-center">
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onEditDieuKhien()" title="Sửa" v-if="data.TRANGTHAIKN_ID !== 142">
              <i class="fa fa-edit"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onInfoDieuKhien()" title="Thông tin TB đấu nối" v-if="data.THIETBIKN_ID">
              <i class="fa fa-plug"></i>
            </b-button>
            <b-button size="sm" variant="outline-danger" class="ml-1" @click="onDeleteDieuKhien()" title="Xóa" v-if="data.TRANGTHAIKN_ID !== 142">
              <i class="fa fa-trash"></i>
            </b-button>
          </div>
        `,
        methods: {
          onInfoDieuKhien() {
            parent.onTTKetNoi(this.data)
          },
          onEditDieuKhien() {
            parent.showDialogCongDieuKhien(this.data)
          },
          onDeleteDieuKhien() {
            parent.onXoaDuLieuPort(true)
          }
        }
      }
    }
  }
}
export const colsLineCardCongDuLieu = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên line card', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Tên thiết bị', allowFiltering: true },
  { fieldName: 'KYHIEU_TB_KN', headerText: 'Ký hiệu', allowFiltering: true },
  { fieldName: 'SR_TB_KN', headerText: 'Serial', allowFiltering: true },
  { fieldName: 'CQ_TB_KN', headerText: 'Sở hữu', allowFiltering: true },
  { fieldName: 'IDC_TB_KN', headerText: 'IDC', allowFiltering: true },
  { fieldName: 'TOANHA_TB_KN', headerText: 'Tòa nhà', allowFiltering: true },
  { fieldName: 'RACK_TB_KN', headerText: 'Rack', allowFiltering: true },
  { fieldName: 'TTVH_TB_KN', headerText: 'Trạng thái vận hành', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsCardDuLieu(parent)
  }
]
export const getColumnActionsCardDuLieu = (parent) => {
  return () => {
    return {
      template: {
        template: `
          <div class="d-flex align-items-center justify-content-center">
           <b-button size="sm" variant="outline-danger" class="ml-1" @click="onViewCard()" title="Xem chi tiết card">
              <i class="fa fa-eye"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onEditCard()" title="Chọn card" >
              <i class="fa fa-edit"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onDeleteCard()" title="Tháo gỡ card" >
              <i class="fa fa-trash"></i>
            </b-button>
          </div>
        `,
        methods: {
          onViewCard() {
            parent.onTTKetNoi(this.data)
          },
          onEditCard() {
            parent.onChonCard(this.data)
          },
          onDeleteCard() {
            parent.onXoaDuLieuPort(false)
          }
        }
      }
    }
  }
}
export const colsPortByCardDL = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  { fieldName: 'LOAICONG', headerText: 'Loại cổng', allowFiltering: true },
  { fieldName: 'BANGTHONG_TN', headerText: 'Băng thông', allowFiltering: true },
  { fieldName: 'MODULE', headerText: 'Module', allowFiltering: true },
  { fieldName: 'DIACHI_IP', headerText: 'Địa chỉ IP', allowFiltering: true },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái Port', allowFiltering: true },
  { fieldName: 'ID_CARD', headerText: 'ID Card', allowFiltering: true },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Thiết bị kết nối', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsDuLieu(parent)
  }
]

export const colsPowerCard = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên power card', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Tên thiết bị', allowFiltering: true },
  { fieldName: 'KYHIEU_TB_KN', headerText: 'Ký hiệu', allowFiltering: true },
  { fieldName: 'SR_TB_KN', headerText: 'Serial', allowFiltering: true },
  { fieldName: 'CQ_TB_KN', headerText: 'Sở hữu', allowFiltering: true },
  { fieldName: 'IDC_TB_KN', headerText: 'IDC', allowFiltering: true },
  { fieldName: 'TOANHA_TB_KN', headerText: 'Tòa nhà', allowFiltering: true },
  { fieldName: 'RACK_TB_KN', headerText: 'Rack', allowFiltering: true },
  { fieldName: 'TTVH_TB_KN', headerText: 'Trạng thái vận hành', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsPowerCard(parent)
  }
]
export const getColumnActionsPowerCard = (parent) => {
  return () => {
    return {
      template: {
        template: `
          <div class="d-flex align-items-center justify-content-center">
           <b-button size="sm" variant="outline-danger" class="ml-1" @click="onViewCard()" title="Xem chi tiết card">
              <i class="fa fa-eye"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onEditCard()" title="Sửa" >
              <i class="fa fa-edit"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onDeleteCard()" title="Xóa" >
              <i class="fa fa-trash"></i>
            </b-button>
          </div>
        `,
        methods: {
          onViewCard() {
            parent.onTTKetNoi(this.data)
          },
          onEditCard() {
            parent.onChonCard(this.data)
          },
          onDeleteCard() {
            // parent.onDelete(this.data)
          }
        }
      }
    }
  }
}
export const colsPortPower = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  {
    fieldName: 'DD_DINHDANH',
    headerText: 'Dòng định danh (A)',
    allowFiltering: true,
    width: 150,
    textAlign: 'Left'
  },
  {
    fieldName: 'DA_DINHDANH',
    headerText: 'Điện áp định danh',
    allowFiltering: true,
    width: 150,
    textAlign: 'Left'
  },
  { fieldName: 'LOAI_NDIEN', headerText: 'Loại nguồn điện', allowFiltering: true },
  { fieldName: 'CHUAN_DN', headerText: 'Chuẩn đấu nối', allowFiltering: true },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái port', allowFiltering: true },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Thiết bị kết nối', allowFiltering: true },
  { fieldName: 'ID_CARD', headerText: 'ID Card', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsNguon(parent)
  }
]
export const colsControlCard = (parent) => [
  { fieldName: 'TEN', headerText: 'Mã TB', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Tên thiết bị', allowFiltering: true },
  { fieldName: 'KYHIEU_TB_KN', headerText: 'Ký hiệu', allowFiltering: true },
  { fieldName: 'SR_TB_KN', headerText: 'Serial', allowFiltering: true },
  { fieldName: 'CQ_TB_KN', headerText: 'Sở hữu', allowFiltering: true },
  { fieldName: 'IDC_TB_KN', headerText: 'IDC', allowFiltering: true },
  { fieldName: 'TOANHA_TB_KN', headerText: 'Tòa nhà', allowFiltering: true },
  { fieldName: 'RACK_TB_KN', headerText: 'Rack', allowFiltering: true },
  { fieldName: 'TTVH_TB_KN', headerText: 'Trạng thái vận hành', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsPowerCard(parent)
  }
]
export const colsPortControl = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  { fieldName: 'LOAICONG', headerText: 'Loại cổng', allowFiltering: true },
  { fieldName: 'MUCDICHSD', headerText: 'Mục đích sử dụng', allowFiltering: true },
  { fieldName: 'DIACHI_IP', headerText: 'Địa chỉ IP', allowFiltering: true },
  { fieldName: 'ID_CARD', headerText: 'ID Card', allowFiltering: true },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái Port', allowFiltering: true },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true },
  { fieldName: 'THIETBIKN', headerText: 'Thiết bị kết nối', allowFiltering: true },
  {
    fieldName: 'actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsDieuKhien(parent)
  }
]
