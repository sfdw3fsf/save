<template src="./index.template.html"></template>
<script>
import { colsCongNguon, colsPowerCard, colsPortPower, PHANLOAI_CONG, LOAICONG_ID, TRANGTHAI_KN } from '../../config.js'
import { validationMixin, ValidationRulesFactory } from './../../../../validation.js'
import ThongTinCongNguon from '../thongtin-cong-nguon/index.vue'
import api from '../../api'
import ModalChonThietBi from '../modal-chon-thietbi/index.vue'

export default {
  name: 'TabCongNguon',
  components: {
    ThongTinCongNguon,
    ModalChonThietBi
  },
  mixins: [validationMixin],
  inject: ['onView', 'isNumber', 'handlePasteNumber'],
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
      return colsCongNguon(this)
    },
    getDataPowerCard() {
      return colsPowerCard(this)
    },
    getDataPortPowerCard() {
      return colsPortPower(this)
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
      listCongNguon: [],
      listPowerCard: [],
      isUsedPort: false,
      dataKheCam: null,
      dataPortCurrent: null,
      allPorts: []
    }
  },
  methods: {
    showDialogCongNguon() {
      this.$refs.ThongTinCongNguon.showDialogCongNguon()
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
    // async onGeneratePortPower() {
    //   try {
    //     this.isUsedPort = this.$parent.isUsedPort
    //     const msg =
    //       this.listCongNguon.length == 0 && this.isUsedPort === false
    //         ? 'Bạn có chắc chắn muốn sinh tự động cổng nguồn này không?'
    //         : this.listCongNguon.length > 0 && this.isUsedPort === false
    //         ? 'Việc sinh cổng nguồn tự động sẽ xóa toàn bộ cổng nguồn hiện có. Bạn có chắc chắn muốn tiếp tục?'
    //         : 'Tồn tại cổng nguồn đã sử dụng. Bạn có chắc chắn muốn sinh số cổng còn lại không?'
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
    //           phanLoai: PHANLOAI_CONG.CONG_NGUON,
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
    //Sinh Port tự động cho Thiết bị -- Lưu tạm trên FE
    async onGeneratePortPower() {
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
          this.listCongNguon.length === 0 && this.isUsedPort === false
            ? 'Bạn có chắc chắn muốn sinh tự động cổng nguồn này không?'
            : this.listCongNguon.length > 0 && this.isUsedPort === false
            ? 'Việc sinh cổng nguồn tự động sẽ xóa toàn bộ cổng nguồn hiện có. Bạn có chắc chắn muốn tiếp tục?'
            : 'Tồn tại cổng nguồn đã sử dụng. Bạn có chắc chắn muốn sinh số cổng còn lại không?'

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
              PORT_ID: `TMP_CN${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              CHUANDN_ID: null,
              CHUAN_DN: null,
              TT_PORT_ID: 0, // Chưa sử dụng
              TRANGTHAI_PORT: 'Chưa sử dụng',
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              DIACHI_IP: null,
              LOAI_NDIEN_ID: null,
              LOAI_NDIEN: null,
              DA_DINHDANH: null,
              DD_DINHDANH: null,
              THIETBI_ID: this.currentItem.id,
              LOAICONG_ID: LOAICONG_ID.CONG_NGUON
            })
          }
          // Giữ nguyên danh sách cũ + append thêm
          this.listCongNguon = [...existingPorts, ...newPorts]
          this.allPorts = [...this.listCongNguon]
        } else {
          // --- MODE 2: KHÔNG CÓ PORT ĐÃ SỬ DỤNG -> GEN LẠI TỪ ĐẦU ---
          for (let i = 0; i < soCong; i++) {
            const stt = batDauTu + i
            const tenPort = this.$parent.formatPortName(dinhDangTen, stt)
            newPorts.push({
              PORT_ID: `TMP_CN${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              CHUANDN_ID: null,
              CHUAN_DN: null,
              TT_PORT_ID: 0, // Chưa sử dụng
              TRANGTHAI_PORT: 'Chưa sử dụng',
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              DIACHI_IP: null,
              LOAI_NDIEN_ID: null,
              LOAI_NDIEN: null,
              DA_DINHDANH: null,
              DD_DINHDANH: null,
              THIETBI_ID: this.currentItem.id,
              LOAICONG_ID: LOAICONG_ID.CONG_NGUON
            })
          }
          this.listCongNguon = newPorts
          this.allPorts = [...this.listCongNguon]
        }
        const totalPorts = this.listCongNguon.length
        const usedCount = this.listCongNguon.filter((p) => Number(p.TT_PORT_ID) === 1).length

        // Ở đây mình cho so_cong = tổng số port hiện có
        this.currentItem.so_cong = totalPorts
        this.currentItem.da_su_dung = usedCount
        this.currentItem.con_trong = totalPorts - usedCount

        // --- CẬP NHẬT arr_cong_dulieu ĐỂ SAU NÀY SAVE XUỐNG DB ---
        this.currentItem.arr_cong_nguon = {
          thietBiId: this.currentItem.id,
          phanLoai: PHANLOAI_CONG.CONG_NGUON,
          soCong: this.currentItem.so_cong,
          batDauTu: batDauTu,
          dinhDangTen: dinhDangTen,
          ports: this.allPorts
        }
        this.$toast.success('Sinh cổng nguồn thành công')
      }
    },
    // Sinh Khe cắm cho Thiết bị
    // async onGenerateKheCam() {
    //   try {
    //     this.isUsedPort = this.$parent.isUsedPort
    //     const msg =
    //       this.listCongNguon.length == 0 && this.isUsedPort === false
    //         ? 'Bạn có chắc chắn muốn sinh tự động power card này không?'
    //         : this.listCongNguon.length > 0 && this.isUsedPort === false
    //         ? 'Việc sinh power card tự động sẽ xóa toàn bộ power card hiện có. Bạn có chắc chắn muốn tiếp tục?'
    //         : 'Tồn tại power card đã sử dụng. Bạn có chắc chắn muốn sinh power card còn lại không?'
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
          this.listPowerCard.length === 0 && this.isUsedPort === false
            ? 'Bạn có chắc chắn muốn sinh tự động power card này không?'
            : this.listPowerCard.length > 0 && this.isUsedPort === false
            ? 'Việc sinh power card tự động sẽ xóa toàn bộ power card hiện có. Bạn có chắc chắn muốn tiếp tục?'
            : 'Tồn tại power card đã sử dụng. Bạn có chắc chắn muốn sinh số power card còn lại không?'

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
        const existingPorts = this.listPowerCard || []
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
              PORT_ID: `TMP_PC${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              THIETBI_ID: this.currentItem.id,
              LOAICONG_ID: LOAICONG_ID.KHE_CAM_NGUON
            })
          }
          // Giữ nguyên danh sách cũ + append thêm
          this.listPowerCard = [...existingPorts, ...newPorts]
        } else {
          // --- MODE 2: KHÔNG CÓ PORT ĐÃ SỬ DỤNG -> GEN LẠI TỪ ĐẦU ---
          for (let i = 0; i < soCong; i++) {
            const stt = batDauTu + i
            const tenPort = this.$parent.formatPortName(dinhDangTen, stt)
            newPorts.push({
              PORT_ID: `TMP_PC${baseTime}_${i}`,
              SOTHUTU: stt,
              TEN: tenPort,
              TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
              TRANGTHAI_KN: 'Chưa kết nối',
              THIETBIKN_ID: null,
              THIETBIKN: null,
              THIETBI_ID: this.currentItem.id,
              LOAICONG_ID: LOAICONG_ID.KHE_CAM_NGUON
            })
          }
          this.listPowerCard = newPorts
        }
        const totalPorts = this.listPowerCard.length
        const usedCount = this.listPowerCard.filter((p) => Number(p.TT_PORT_ID) === 1).length

        // Ở đây mình cho so_cong = tổng số port hiện có
        this.currentItem.so_khe_cam = totalPorts
        this.currentItem.khe_cam_da_dung = usedCount
        this.currentItem.khe_cam_con_lai = totalPorts - usedCount

        // --- CẬP NHẬT arr_cong_dulieu ĐỂ SAU NÀY SAVE XUỐNG DB ---
        this.currentItem.arr_power_card = {
          thietBiId: this.currentItem.id,
          phanLoai: PHANLOAI_CONG.CONG_NGUON,
          soCong: this.currentItem.so_khe_cam,
          batDauTu: batDauTu,
          dinhDangTen: dinhDangTen,
          ports: this.listPowerCard
        }
        console.log('this.currentItem.arr_power_card', this.currentItem.arr_power_card)
        this.$toast.success('Sinh power card thành công')
      }
    },
    // Danh sách port của card gắn trên khe cắm (thiết bị mạng)
    async loadListPortByCard() {
      try {
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
        this.listCongNguon = this.allPorts.filter((p) => p.THIETBI_ID == this.currentItem.id || p.THIETBI_ID == cardId)
        // Update arr_cong_dulieu để save xuống BE
        this.currentItem.arr_cong_nguon = {
          ...(this.currentItem.arr_cong_nguon || {}),
          thietBiId: this.currentItem.id,
          phanLoai: PHANLOAI_CONG.CONG_NGUON,
          ports: this.allPorts
        }
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.listCongNguon = []
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
          this.listCongNguon = (this.allPorts || []).filter(
            (p) => p.THIETBI_ID == this.currentItem.id || p.THIETBI_ID == cardId
          )
        } else {
          // Chưa gắn card nào => chỉ hiển thị port của TB cha
          this.listCongNguon = (this.allPorts || []).filter((p) => p.THIETBI_ID == this.currentItem.id)
        }
      } else {
        // Khi chọn dòng port trong grid
        this.dataPortCurrent = item
        this.$refs.ThongTinCongNguon.thongTinCong.id = item.PORT_ID
        this.$refs.ThongTinCongNguon.thongTinCong.dong_dinh_danh = item.DD_DINHDANH
        this.$refs.ThongTinCongNguon.thongTinCong.dien_ap_dinh_danh = item.DA_DINHDANH
        this.$refs.ThongTinCongNguon.thongTinCong.loaiNguonDienId = item.LOAI_NDIEN_ID
        this.$refs.ThongTinCongNguon.thongTinCong.chuanDauNoiId = item.CHUANDN_ID
        this.$refs.ThongTinCongNguon.thongTinCong.trangThaiPortId = item.TT_PORT_ID
        this.$refs.ThongTinCongNguon.thongTinCong.trangThaiKetNoiId = item.TRANGTHAIKN_ID
        this.$refs.ThongTinCongNguon.thongTinCong.thietBiNoiDenId = item.THIETBIKN_ID
        this.$refs.ThongTinCongNguon.thongTinCong.thiBiGocId = item.THIETBI_ID
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
      this.$refs.ModalChonThietBi.show(data, PHANLOAI_CONG.CONG_NGUON)
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
            const arr = !isPort ? this.listPowerCard : this.listCongNguon || []
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
                  LOAICONG_ID: LOAICONG_ID.CONG_NGUON,
                  LOAICONG: 'Cổng nguồn',
                  LOAI_NDIEN_ID: null,
                  LOAI_NDIEN: null,
                  DA_DINHDANH: null,
                  DD_DINHDANH: null,
                  CHUANDN_ID: null,
                  CHUAN_DN: null,
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
                  LOAICONG_ID: LOAICONG_ID.KHE_CAM_NGUON
                }
            this.$set(isPort ? this.listCongNguon : this.listPowerCard, idx, resetPort)
            console.log(this.listCongNguon)
            // cập nhật lại arr_cong_dulieu trên currentItem (dùng lúc save xuống BE)
            if (this.currentItem && this.currentItem.arr_cong_nguon && isPort) {
              this.currentItem.arr_cong_nguon.ports = this.listCongNguon
            } else if (this.currentItem && this.currentItem.arr_power_card && !isPort) {
              this.currentItem.arr_power_card.ports = this.listPowerCard
            }
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
