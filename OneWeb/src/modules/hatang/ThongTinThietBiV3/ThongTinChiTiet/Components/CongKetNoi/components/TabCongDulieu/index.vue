<template src="./index.template.html"></template>
<script>
import {
  colsCongDuLieu,
  colsLineCardCongDuLieu,
  colsPortByCardDL,
  PHANLOAI_CONG,
  LOAICONG_ID,
  TRANGTHAI_KN
} from '../../config.js'
import { validationMixin, ValidationRulesFactory } from './../../../../validation.js'
import ThongTinCongDuLieu from '../thongtin-cong-dulieu/index.vue'
import ModalChonThietBi from '../modal-chon-thietbi/index.vue'
import api from '../../api'

export default {
  name: 'TabCongDulieu',
  components: {
    ThongTinCongDuLieu,
    ModalChonThietBi
  },
  inject: ['onView', 'isNumber', 'handlePasteNumber'],
  mixins: [validationMixin],
  props: {
    deviceTypeList: {
      type: Array,
      default: () => []
    },
    customerTypeList: {
      type: Array,
      default: () => []
    },
    IPAddressList: {
      type: Array,
      default: () => []
    },
    parentDeviceList: {
      type: Array,
      default: () => []
    },
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    deviceTypeInfo: {
      type: Object,
      default: () => ({
        phanLoai: null,
        ten: null,
        id: null,
        isServer: false,
        isNetwork: false,
        isServerBlade: false,
        isBlade: false,
        isTuDia: false,
        isBoDieuKhien: false,
        isKhayDia: false,
        isLinecard: false,
        isPowercard: false,
        isConsolecard: false
      })
    }
  },
  watch: {},
  computed: {
    getDataCols() {
      return colsCongDuLieu(this)
    },
    getDataColsLineCard() {
      return colsLineCardCongDuLieu(this)
    },
    getColPortByCard() {
      return colsPortByCardDL(this)
    },
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  data() {
    return {
      validationRules: {
        txt_ten: ValidationRulesFactory.createTextRule('Tên thiết bị không được để trống'),
        txt_mathietbi_infra: ValidationRulesFactory.createTextRule('Mã thiết bị không được để trống'),
        select_loaithietbi_id: ValidationRulesFactory.createSelectRule('Loại thiết bị không được để trống')
      },
      listCongDuLieu: [],
      listLineCard: [],
      isUsedPort: false,
      dataKheCam: null,
      dataPortCurrent: null,
      allPorts: []
    }
  },
  mounted() {
    // if (this.currentItem && this.currentItem.id) {
    //   this.loadListPort()
    // }
  },
  methods: {
    showThongTinCong() {
      this.$refs.ThongTinCongDuLieu.showDialog()
    },
    getValueForControl(controlKey) {
      const valueMap = {
        txt_ten: this.currentItem.ten,
        txt_mathietbi_infra: this.currentItem.mathietbi_infra,
        select_loaithietbi_id: this.currentItem.loaithietbi_id
      }

      return valueMap[controlKey]
    },
    normalizer(node) {
      return {
        id: node.ID,
        label: node.IP
      }
    },
    //Sinh Port tự động cho Thiết bị -- Lưu tạm trên FE
    async onGenerateDataPort() {
      const soCong = Number(this.currentItem.so_cong)
      const batDauTu = Number(this.currentItem.bat_dau_cong)
      const dinhDangTen = (this.currentItem.dinh_dang_ten || '').trim()
      const arrPortOld = this.allPorts.filter((p) => p.PORT_ID && !p.PORT_ID.toString().startsWith('TMP_')) // ds port đã lưu
      if (arrPortOld.length > 0 && soCong < arrPortOld.length) {
        const check = this.$parent.checkSoCong(soCong, arrPortOld)
        if (!check.isValid) {
          this.$root.toastError(check.message)
          return
        }
      }
      if (this.$parent.validatePort(true)) {
        this.isUsedPort = this.$parent.isUsedPort
        const msg =
          this.listCongDuLieu.length === 0 && this.isUsedPort === false
            ? 'Bạn có chắc chắn muốn sinh tự động cổng dữ liệu này không?'
            : this.listCongDuLieu.length > 0 && this.isUsedPort === false
            ? 'Việc sinh cổng dữ liệu tự động sẽ xóa toàn bộ cổng dữ liệu hiện có. Bạn có chắc chắn muốn tiếp tục?'
            : 'Tồn tại cổng dữ liệu đã sử dụng. Bạn có chắc chắn muốn sinh số cổng còn lại không?'

        const flag = await this.$bvModal.msgBoxConfirm(msg, {
          title: 'Thông báo',
          size: 'md',
          okTitle: 'Xác nhận',
          cancelTitle: 'Hủy',
          centered: true
        })
        if (!flag) {
          return
        }
        // Sinh danh sách cổng tạm trên FE
        const newPorts = []
        const baseTime = Date.now()
        const existingPorts = arrPortOld || []
        // const usedPorts = existingPorts.filter((p) => Number(p.TT_PORT_ID) === 1)
        // --- MODE 1: CÓ PORT ĐÃ SỬ DỤNG -> GEN TIẾP, KHÔNG XOÁ CŨ ---
        if (this.isUsedPort) {
          // soCong lúc này hiểu là SỐ CỔNG MUỐN THÊM
          // tìm số thứ tự lớn nhất hiện có để gen tiếp
          let lastOrder = existingPorts.reduce(
            (max, p) => {
              const so = Number(p.SOTHUTU)
              return !isNaN(so) && so > max ? so : max
            },
            batDauTu > 0 ? batDauTu - 1 : 0
          )
          const so_cong_new = soCong - arrPortOld.length
          for (let i = 1; i <= so_cong_new; i++) {
            const stt = lastOrder + i
            const tenPort = this.$parent.formatPortName(dinhDangTen, stt)

            newPorts.push({
              PORT_ID: `TMP_DL${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              MUCDICHSD_ID: null,
              MUCDICHSD: null,
              TT_PORT_ID: 0, // Chưa sử dụng
              TRANGTHAI_PORT: 'Chưa sử dụng',
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              DIACHI_IP: null,
              DIACHI_IP_ID: null,
              LOAICONG_ID: LOAICONG_ID.CONG_DU_LIEU,
              LOAICONG: 'Cổng dữ liệu',
              MODULE_ID: null,
              MODULE: null,
              BANGTHONG_TN: null,
              THIETBI_ID: this.currentItem.id
            })
          }
          // Giữ nguyên danh sách cũ + append thêm
          this.listCongDuLieu = [...existingPorts, ...newPorts]
          this.allPorts = [...this.listCongDuLieu]
        } else {
          // --- MODE 2: KHÔNG CÓ PORT ĐÃ SỬ DỤNG -> GEN LẠI TỪ ĐẦU ---
          for (let i = 0; i < soCong; i++) {
            const stt = batDauTu + i
            const tenPort = this.$parent.formatPortName(dinhDangTen, stt)
            newPorts.push({
              PORT_ID: `TMP_DL${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              MUCDICHSD_ID: null,
              MUCDICHSD: null,
              TT_PORT_ID: 0, // Chưa sử dụng
              TRANGTHAI_PORT: 'Chưa sử dụng',
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              DIACHI_IP: null,
              DIACHI_IP_ID: null,
              LOAICONG_ID: LOAICONG_ID.CONG_DU_LIEU,
              LOAICONG: 'Cổng dữ liệu',
              MODULE_ID: null,
              MODULE: null,
              BANGTHONG_TN: null,
              THIETBI_ID: this.currentItem.id
            })
          }
          this.listCongDuLieu = newPorts
          this.allPorts = [...this.listCongDuLieu]
        }
        const totalPorts = this.listCongDuLieu.length
        const usedCount = this.listCongDuLieu.filter((p) => Number(p.TT_PORT_ID) === 1).length

        // Ở đây mình cho so_cong = tổng số port hiện có
        this.currentItem.so_cong = totalPorts
        this.currentItem.da_su_dung = usedCount
        this.currentItem.con_trong = totalPorts - usedCount

        // --- CẬP NHẬT arr_cong_dulieu ĐỂ SAU NÀY SAVE XUỐNG DB ---
        this.currentItem.arr_cong_dulieu = {
          thietBiId: this.currentItem.id,
          phanLoai: PHANLOAI_CONG.CONG_DU_LIEU,
          soCong: this.currentItem.so_cong,
          batDauTu: batDauTu,
          dinhDangTen: dinhDangTen,
          ports: this.allPorts
        }
        console.log('this.currentItem.arr_cong_dulieu', this.currentItem.arr_cong_dulieu)
        this.$toast.success('Sinh cổng dữ liệu thành công')
      }
    },
    //Sinh Port tự động cho Thiết bị -- hàm lưu thẳng xuống DB
    // async onGenerateDataPort() {
    //   try {
    //     this.isUsedPort = this.$parent.isUsedPort
    //     const msg =
    //       this.listCongDuLieu.length == 0 && this.isUsedPort === false
    //         ? 'Bạn có chắc chắn muốn sinh tự động cổng dữ liệu này không?'
    //         : this.listCongDuLieu.length > 0 && this.isUsedPort === false
    //         ? 'Việc sinh cổng dữ liệu tự động sẽ xóa toàn bộ cổng dữ liệu hiện có. Bạn có chắc chắn muốn tiếp tục?'
    //         : 'Tồn tại cổng dữ liệu đã sử dụng. Bạn có chắc chắn muốn sinh số cổng còn lại không?'
    //     const flag = await this.$bvModal.msgBoxConfirm(msg, {
    //       title: 'Thông báo',
    //       size: 'md',
    //       okTitle: 'Xác nhận',
    //       cancelTitle: 'Hủy',
    //       centered: true
    //     })
    //     if (flag) {
    //       try {
    //         this.$root.showLoading(true)
    //         const params = {
    //           thietBiId: this.currentItem.id,
    //           phanLoai: PHANLOAI_CONG.CONG_DU_LIEU,
    //           soLuong: Number(this.currentItem.so_cong),
    //           ten: this.currentItem.dinh_dang_ten,
    //           batDauTu: Number(this.currentItem.bat_dau_cong)
    //         }
    //         let response = await this.sinhCongTuDong(params)

    //         if (response && response.error_code === 'BSS-00000000') {
    //           this.$toast.success('Sinh cổng thành công')
    //           await this.$parent.loadListPort()
    //         } else {
    //           this.$toast.error(response.message_detail)
    //         }
    //       } finally {
    //         this.$root.showLoading(false)
    //       }
    //     }
    //   } catch (error) {
    //     console.error('delete error:', error)

    //     this.$toast.error('Có lỗi xảy ra trong quá trình thực hiện')
    //   }
    // },
    // Sinh Khe cắm cho Thiết bị
    // async onGenerateKheCam() {
    //   try {
    //     this.isUsedPort = this.$parent.isUsedPort
    //     const msg =
    //       this.listLineCard.length == 0 && this.isUsedPort === false
    //         ? 'Bạn có chắc chắn muốn sinh tự động line card này không?'
    //         : this.listLineCard.length > 0 && this.isUsedPort === false
    //         ? 'Việc sinh line card tự động sẽ xóa toàn bộ line card hiện có. Bạn có chắc chắn muốn tiếp tục?'
    //         : 'Tồn tại line card đã sử dụng. Bạn có chắc chắn muốn sinh line card còn lại không?'
    //     const flag = await this.$bvModal.msgBoxConfirm(msg, {
    //       title: 'Thông báo',
    //       size: 'md',
    //       okTitle: 'Xác nhận',
    //       cancelTitle: 'Hủy',
    //       centered: true
    //     })
    //     if (flag) {
    //       try {
    //         this.$root.showLoading(true)
    //         const params = {
    //           thietBiId: this.currentItem.id,
    //           phanLoai: PHANLOAI_CONG.KHE_CAM,
    //           soLuong: Number(this.currentItem.so_khe_cam),
    //           ten: this.currentItem.khe_cam_dinh_dang_ten,
    //           batDauTu: Number(this.currentItem.khe_cam_bat_dau)
    //         }
    //         let response = await this.sinhCongTuDong(params)

    //         if (response && response.error_code === 'BSS-00000000') {
    //           this.$toast.success('Sinh cổng thành công')
    //           await this.$parent.loadListKheCam()
    //         } else {
    //           this.$toast.error(response.message_detail)
    //         }
    //       } finally {
    //         this.$root.showLoading(false)
    //       }
    //     }
    //   } catch (error) {
    //     console.error('delete error:', error)

    //     this.$toast.error('Có lỗi xảy ra trong quá trình thực hiện')
    //   }
    // },
    async onGenerateKheCam() {
      const soCong = Number(this.currentItem.so_khe_cam)
      const batDauTu = Number(this.currentItem.khe_cam_bat_dau)
      const dinhDangTen = (this.currentItem.khe_cam_dinh_dang_ten || '').trim()
      if (this.$parent.validatePort(false)) {
        this.isUsedPort = this.$parent.isUsedPort
        const msg =
          this.listLineCard.length === 0 && this.isUsedPort === false
            ? 'Bạn có chắc chắn muốn sinh tự động line card này không?'
            : this.listLineCard.length > 0 && this.isUsedPort === false
            ? 'Việc sinh line card tự động sẽ xóa toàn bộ line card hiện có. Bạn có chắc chắn muốn tiếp tục?'
            : 'Tồn tại line card đã sử dụng. Bạn có chắc chắn muốn sinh số line card còn lại không?'

        const flag = await this.$bvModal.msgBoxConfirm(msg, {
          title: 'Thông báo',
          size: 'md',
          okTitle: 'Xác nhận',
          cancelTitle: 'Hủy',
          centered: true
        })
        if (!flag) {
          return
        }
        // Sinh danh sách cổng tạm trên FE
        const newPorts = []
        const baseTime = Date.now()
        const existingPorts = this.listLineCard || []
        const usedPorts = existingPorts.filter((p) => Number(p.TT_PORT_ID) === 1)
        if (usedPorts.length > 0) {
          let lastOrder = existingPorts.reduce(
            (max, p) => {
              const so = Number(p.SOTHUTU)
              return !isNaN(so) && so > max ? so : max
            },
            batDauTu > 0 ? batDauTu - 1 : 0
          )

          for (let i = 1; i <= soCong; i++) {
            const stt = lastOrder + i
            const tenPort = this.$parent.formatPortName(dinhDangTen, stt)

            newPorts.push({
              PORT_ID: `TMP_LC${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              THIETBI_ID: this.currentItem.id,
              LOAICONG_ID: LOAICONG_ID.KHE_CAM_DL
            })
          }
          // Giữ nguyên danh sách cũ + append thêm
          this.listLineCard = [...existingPorts, ...newPorts]
        } else {
          // --- MODE 2: KHÔNG CÓ PORT ĐÃ SỬ DỤNG -> GEN LẠI TỪ ĐẦU ---
          for (let i = 0; i < soCong; i++) {
            const stt = batDauTu + i
            const tenPort = this.$parent.formatPortName(dinhDangTen, stt)
            newPorts.push({
              PORT_ID: `TMP_LC${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              THIETBI_ID: this.currentItem.id,
              LOAICONG_ID: LOAICONG_ID.KHE_CAM_DL
            })
          }
          this.listLineCard = newPorts
        }
        const totalPorts = this.listLineCard.length
        const usedCount = this.listLineCard.filter((p) => Number(p.TT_PORT_ID) === 1).length

        // Ở đây mình cho so_cong = tổng số port hiện có
        this.currentItem.so_khe_cam = totalPorts
        this.currentItem.khe_cam_da_dung = usedCount
        this.currentItem.khe_cam_con_lai = totalPorts - usedCount

        // --- CẬP NHẬT arr_cong_dulieu ĐỂ SAU NÀY SAVE XUỐNG DB ---
        this.currentItem.arr_line_card = {
          thietBiId: this.currentItem.id,
          phanLoai: PHANLOAI_CONG.CONG_DULIEU,
          soCong: this.currentItem.so_khe_cam,
          batDauTu: batDauTu,
          dinhDangTen: dinhDangTen,
          ports: this.listLineCard
        }
        // Nếu gen lại khe cắm thì xóa toàn bộ port của TB kết nối gắn vào khe cắm cũ
        if (!this.isUsedPort) {
          this.listCongDuLieu = this.listCongDuLieu.filter((p) => p.THIETBI_ID == this.currentItem.id)
        }
        this.$toast.success('Sinh line card thành công')
      }
    },
    // Danh sách port của card gắn trên khe cắm (thiết bị mạng)
    async loadListPortByCard() {
      try {
        console.log('this.dataKheCam', this.dataKheCam)
        const cardId = this.dataKheCam.THIETBIKN_ID
        // Lấy tất cả port ngoại trừ port của card đang chọn (để replace lại)
        const otherPorts = (this.allPorts || []).filter((p) => p.THIETBI_ID != cardId)

        const data = {
          thietBiId: cardId,
          phanLoai: this.$parent.phanLoai,
          kheCam: 0
        }
        const result = (await this.$parent.getListPort(data)) || []

        // đảm bảo port con có THIETBI_ID = cardId để phân biệt
        const mapped = result.map((p) => ({
          ...p,
          THIETBI_ID: cardId
        }))

        // Cập nhật master
        this.allPorts = [...otherPorts, ...mapped]

        // View cho grid: chỉ hiển thị port của TB cha + card đang chọn
        this.listCongDuLieu = this.allPorts.filter((p) => p.THIETBI_ID == this.currentItem.id || p.THIETBI_ID == cardId)
        console.log('this.listCongDuLieu', this.listCongDuLieu, this.allPorts)
        // Update arr_cong_dulieu để save xuống BE
        this.currentItem.arr_cong_dulieu = {
          ...(this.currentItem.arr_cong_dulieu || {}),
          thietBiId: this.currentItem.id,
          phanLoai: PHANLOAI_CONG.CONG_DU_LIEU,
          ports: this.allPorts
        }
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.listCongDuLieu = []
          this.$root.toastError(e.response.data.message_detail)
        }
      }
    },
    sinhCongTuDong: async function(data) {
      let result = []
      try {
        this.loading(true)
        const response = await api.addPort(this.$root.context, data)
        result = response || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    grdItems_RowSelected: async function(args, isKheCam) {
      try {
        //this.$root.showLoading(true);
        this.setCurrentItem(args.data, isKheCam)
      } finally {
        this.$root.showLoading(false)
      }
    },
    setCurrentItem: async function(item, isKheCam) {
      if (isKheCam) {
        this.dataKheCam = item
        const cardId = item.THIETBIKN_ID
        if (cardId) {
          // Kiểm tra xem allPorts đã có port của card này chưa
          const hasPortsForCard = (this.allPorts || []).some((p) => p.THIETBI_ID == cardId)
          if (!hasPortsForCard) {
            // Chưa có -> gọi API lấy danh sách port của card
            await this.loadListPortByCard()
          }

          // Hiển thị port của TB cha + card đang chọn
          this.listCongDuLieu = (this.allPorts || []).filter(
            (p) => p.THIETBI_ID == this.currentItem.id || p.THIETBI_ID == cardId
          )
        } else {
          // Chưa gắn card nào => chỉ hiển thị port của TB cha
          this.listCongDuLieu = (this.allPorts || []).filter((p) => p.THIETBI_ID == this.currentItem.id)
        }
      } else {
        // Khi chọn dòng port trong grid
        this.dataPortCurrent = item
        this.$refs.ThongTinCongDuLieu.interfacePortType.id = item.PORT_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.mucDichId = item.MUCDICHSD_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.trangThaiPortId = item.TT_PORT_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.trangThaiKetNoiId = item.TRANGTHAIKN_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.thietBiNoiDenId = item.THIETBIKN_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.diaChiIP = item.DIACHI_IP_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.loaiCongId = item.LOAICONG_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.thongTinModuleId = item.MODULE_ID
        this.$refs.ThongTinCongDuLieu.interfacePortType.bangThong = item.BANGTHONG_TN
        this.$refs.ThongTinCongDuLieu.interfacePortType.thietBiGocId = item.THIETBI_ID
      }
    },

    reLoadListPort: async function() {
      await this.$parent.loadListPort()
    },
    reLoadListKheCam: async function() {
      await this.$parent.loadListKheCam()
    },
    onTTKetNoi(data) {
      data = {
        ...data,
        loaithietbi_id: data.LOAITB_KN_ID,
        id: data.THIETBIKN_ID
      }
      this.onView(data)
    },
    onChonCard(data) {
      this.$refs.ModalChonThietBi.show(data, PHANLOAI_CONG.CONG_DU_LIEU)
    },
    onXoaDuLieuPort: function(isPort) {
      this.$confirm('Bạn có chắc chắn muốn xóa các dữ liệu của cổng này không?', 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ'
      })
        .then(() => {
          try {
            const itemCheck = !isPort ? this.dataKheCam : this.dataPortCurrent
            if (!itemCheck) {
              this.$toast.error('Vui lòng chọn cổng dữ liệu cần xoá thông tin')
              return
            }
            const portId = itemCheck.PORT_ID
            const arr = !isPort ? this.listLineCard : this.listCongDuLieu || []
            const idx = arr.findIndex((p) => p.PORT_ID == portId)
            if (idx === -1) {
              this.$toast.error('Không tìm thấy cổng dữ liệu cần xoá thông tin')
              return
            }
            const oldPort = arr[idx]
            // Reset về trạng thái giống lúc mới gen
            const resetPort = isPort
              ? {
                  ...oldPort,
                  MUCDICHSD_ID: null,
                  MUCDICHSD: null,
                  TT_PORT_ID: 0, // Chưa sử dụng
                  TRANGTHAI_PORT: 'Chưa sử dụng',
                  TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
                  TRANGTHAI_KN: 'Chưa kết nối',
                  THIETBIKN_ID: null,
                  THIETBIKN: null,
                  DIACHI_IP: null,
                  DIACHI_IP_ID: null,
                  LOAICONG_ID: LOAICONG_ID.CONG_DU_LIEU,
                  LOAICONG: 'Cổng dữ liệu',
                  MODULE_ID: null,
                  MODULE: null,
                  BANGTHONG_TN: null,
                  isEdited: true
                }
              : {
                  ...oldPort,
                  TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
                  TRANGTHAI_KN: 'Chưa kết nối',
                  THIETBIKN_ID: null,
                  THIETBIKN: null,
                  SR_TB_KN: null,
                  IDC_TB_KN: null,
                  TOANHA_TB_KN: null,
                  RACK_TB_KN: null,
                  TTVH_TB_KN: null,
                  CQ_TB_KN: null,
                  isEdited: true,
                  LOAICONG_ID: LOAICONG_ID.KHE_CAM_DL
                }
            this.$set(isPort ? this.listCongDuLieu : this.listLineCard, idx, resetPort)

            // cập nhật lại arr_cong_dulieu trên currentItem (dùng lúc save xuống BE)
            if (this.currentItem && this.currentItem.arr_cong_dulieu && isPort) {
              this.currentItem.arr_cong_dulieu.ports = this.listCongDuLieu
            } else if (this.currentItem && this.currentItem.arr_line_card && !isPort) {
              this.currentItem.arr_line_card.ports = this.listLineCard
            }
            // console.log('this.listCongDuLieu after reset:', this.listCongDuLieu)
            // console.log('this.listLineCard after reset:', this.listLineCard)
            this.$toast.success('Xoá dữ liệu cấu hình của cổng thành công!')
          } catch (err) {
            console.error(err)
            this.$toast.error('Có lỗi xảy ra khi xoá dữ liệu cổng!')
          }
        })
        .catch(() => {
          //
        })
    }
  }
}
</script>
<style scoped>
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}
</style>
