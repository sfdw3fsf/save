<template src="./CapPhatCongSuatDien.html"></template>
<style src="../style.scss" scoped></style>
<script>
const ST = {
  TIEP_NHAN: 21,
  DANG_TH: 22,
  TRA_PHIEU: 23,
  HOAN_THANH: 24,
  NHIEM_VU_MOI: 81
}
import Vue from 'vue'
import ThongTinCongViec from '../../components/ThongTinCongViec/index.vue'
import api from '../api.js'
import CapPhatDienPopup from '../components/CapPhatDienPopup.vue'
import ChuyenXuLyPopup from '../components/ChuyenXuLyPopup.vue'
import TraPhieuPopup from '../components/TraPhieuPopup.vue'
import TabHistory from '../../components/Tabs/TabHistory/index.vue'
import SelectThietBiPopup from '../components/SelectThietBiPopup.vue'

export default {
  components: {
    popupCapPhatDien: CapPhatDienPopup,
    popupChuyenXuLy: ChuyenXuLyPopup,
    popupTraPhieu: TraPhieuPopup,
    'thong-tin-cong-viec': ThongTinCongViec,
    'tab-history': TabHistory,
    'select-thiet-bi-popup': SelectThietBiPopup
  },
  data() {
    return {
      tab_index: 1,
      tsbtnGhiLai: false,
      tsbtnChuyenXuLy: false,
      tsbtnTraPhieu: false,
      tsbtnHoanThanh: false,
      selectedThietBi: null,
      congviec: {
        phieuTcId: null,
        maCongViec: '',
        idLoaiCv: null,
        tenLoaiCv: '',
        hangMucTrienKhai: '',
        canCuPhieuYC: '',
        maCongViecCha: '',
        tenKhachHang: '',
        maThueBao: '',
        donViThucHien: '',
        tenNguoiThucHien: '',
        idNguoiThucHien: null,
        tenPhoiHopThucHien: '',
        idPhoiHopThucHien: null,
        ngayTao: '',
        trangThaiThucHien: '',
        ngayHoanThanh: ''
      },
      dsKetQuaThietBiCapPhat: []
    }
  }, // end of data
  async mounted() {
    const phieutc_id = this.$route.query.phieutc
    await this.loadData(phieutc_id)
  },
  computed: {
    currentUserId() {
      return this.$root.token.getNhanVienID()
    },
    currentDonViId() {
      return this.$root.token.getDonViID()
    },
    isEditMode() {
      const q = this.$route.query.isEditMode
      return q === 'true' || q === true || q === '1' || q === 1
    },
    ctx() {
      const cv = this.congviec
      return {
        editMode: this.isEditMode,
        status: cv.idTrangThaiThucHien,
        creatorId: cv.idNguoiTaoPhieu,
        assigneeId: cv.idNguoiThucHien,
        unitOk: cv.donViThucHienId == this.currentDonViId,
        userId: this.currentUserId
      }
    },
    actions() {
      const c = this.ctx
      return {
        save: this.can('save', c),
        transferExecute: this.can('transferExecute', c),
        confirmExecute: this.can('confirmExecute', c),
        transfer: this.can('transfer', c),
        returnTicket: this.can('return', c),
        complete: this.can('complete', c),
        powerAllocate: this.can('powerAllocate', c)
      }
    }
  },
  methods: {
    // kiểm tra quyền thực hiện action
    can(action, c) {
      // chặn toàn cục khi không ở edit-mode cho các action “ghi/chuyển/trả/hoàn thành”
      const needEditActions = ['save', 'transferExecute', 'transfer', 'return', 'complete', 'powerAllocate']
      if (needEditActions.includes(action) && !c.editMode) return false

      switch (action) {
        case 'save':
          // “Ghi lại” chỉ khi tiếp nhận và là người tạo
          return c.status === ST.TIEP_NHAN && c.creatorId == c.userId

        case 'transferExecute':
          // “Chuyển thực hiện” chỉ khi tiếp nhận và là người tạo
          return c.status === ST.TIEP_NHAN && c.creatorId == c.userId

        case 'confirmExecute':
          // “Xác nhận thực hiện” khi nhiệm vụ mới và là người được giao
          return c.status === ST.NHIEM_VU_MOI && c.assigneeId == c.userId

        case 'transfer':
          // “Chuyển xử lý” khi Đang thực hiện hoặc Nhiệm vụ mới, là người thực hiện
          return (c.status === ST.DANG_TH || c.status === ST.NHIEM_VU_MOI) && c.assigneeId == c.userId

        case 'return':
          // “Trả phiếu” khi Đang thực hiện hoặc Nhiệm vụ mới, là người thực hiện
          return (c.status === ST.DANG_TH || c.status === ST.NHIEM_VU_MOI) && c.assigneeId == c.userId

        case 'complete':
          // “Hoàn thành” chỉ khi đang thực hiện, đúng đơn vị, đúng assignee
          return (
            c.status === ST.DANG_TH &&
            c.unitOk &&
            c.assigneeId == c.userId 
          )

        case 'powerAllocate':
          // nút “Cấp phát công suất điện” (ở phần bảng)
          return c.editMode && c.status === ST.DANG_TH && c.unitOk && c.assigneeId == c.userId

        default:
          return false
      }
    },
    async loadData(phieutc_id) {
      try {
        this.$root.loading(true)
        // gọi API lấy thông tin công việc
        const resCv = await api.getCongViecCongSuatDien(this.$root.context, phieutc_id)
        if (resCv.data.idLoaiCv != 29) {
          this.$toast.error('Công việc không phải công việc cấp phát công suất điện')
          return this.$router.push('/tainguyen-idc/danhsach-congviec?loaicv=29')
        }
        const resKetQua = await api.getKetQuaCapPhat(this.$root.context, phieutc_id)
        this.congviec = resCv.data
        // check quyền ở đây
        if (this.congviec.donViThucHienId != this.currentDonViId) {
          this.$toast.warning('Công việc không thuộc đơn vị của bạn')
          return this.$router.push('/tainguyen-idc/danhsach-congviec?loaicv=29')
        }
        if (
          this.ctx.editMode &&
          this.congviec.idNguoiThucHien != this.currentUserId &&
          this.congviec.idNguoiTaoPhieu != this.currentUserId
        ) {
          this.$toast.warning('Bạn không phải người thực hiện công việc này')
        }
        // convert trạng thái ổ cắm
        this.dsKetQuaThietBiCapPhat = (resKetQua.data || []).map((item) => ({
          ...item,
          trangThaiOcamText: item.trangThaiOcam == 1 ? 'Đã cắm nguồn' : item.trangThaiOcam == 2 ? 'Chưa cắm nguồn' : '',
          trangThaiThanhNguonText: item.trangThaiThanhNguon == 1 ? 'ON' : item.trangThaiThanhNguon == 2 ? 'OFF' : ''
        }))
      } catch (error) {
        this.$toast.error('Lỗi tải dữ liệu: ' + error.response?.data?.message || error.message)
      } finally {
        this.$root.loading(false)
      }
    },
    async handleCapPhatDien(result) {
      try {
        const payload = {
          phieuTcId: this.congviec.phieuTcId,
          ocamId: result.idOCam,
          nhanvienId: this.congviec.idNguoiThucHien,
          thietbiId: result.idThietBi,
          thanhnguonId: result.idThanhNguon,
          noiDung: result.noiDung,
          trangthaiId: this.congviec.idTrangThaiThucHien
        }
        await api.capPhatDien(this.$root.context, payload)
        this.$toast.success('Cấp phát công suất điện thành công')
        if (this.congviec.idTrangThaiThucHien == 81) {
          //neu la moi tiep nhan thi update ve tiep nhan dang thuc hien
          await api.updateTrangThaiPhieuTC(this.$root.context, {
            trangthaiId: 22, // 22 : tiep nhan,dang thuc hien
            phieuTcId: this.congviec.phieuTcId
          })
        }
        // reload lại danh sách kết quả thiết bị đã cấp phát
        await this.loadData(this.congviec.phieuTcId)
        this.$refs.dlgCapPhatDien.onDialogClose()
      } catch (e) {
        if (e.response && e.response.data) {
          this.$toast.error('Lỗi cấp phát công suất điện: ' + e.response.data.message)
        } else {
          this.$toast.error('Lỗi cấp phát công suất điện: ' + e.message)
        }
      }
    },
    async handleChuyenXuLy(result) {
      const payload = {
        phieuTcId: this.congviec.phieuTcId,
        nguoiThucHienId: result.idNguoiThucHien,
        noiDung: result.noiDung,
        trangthaiId: 22 // 22 : tiep nhan,dang thuc hien
      }
      try {
        await api.updateTrangThaiPhieuTC(this.$root.context, payload)
        this.$toast.success('Chuyển xử lý công việc thành công')
        this.$refs.dlgChuyenXuLy.closeDialog()
        this.$router.push('/tainguyen-idc/ketqua-congsuatdien?loaicv=29')
      } catch (e) {
        if (e.response && e.response.data) {
          this.$toast.error('Lỗi chuyển xử lý công việc: ' + e.response.data.message)
        } else {
          this.$toast.error('Lỗi chuyển xử lý công việc: ' + e.message)
        }
      }
    },
    onCapPhatDien() {
      this.$refs.dlgCapPhatDien.phieuTcId = this.congviec.phieuTcId
      this.$refs.dlgCapPhatDien.selectedThietBi = null
      this.$nextTick(() => {
        this.$refs.dlgCapPhatDien.openDialog()
      })
    },
    onChangeTab: function(index) {
      this.tab_index = index

      // Gọi refreshData khi click vào tab Lịch sử tác động (tab_index = 2)
      if (index === 2 && this.$refs.tabHistory) {
        this.$refs.tabHistory.refreshData()
      }
    },
    async deleteSelected() {
      this.$toast.info('Chức năng đang phát triển')
    },
    onSave: async function() {
      let rs = await this.$refs.thongTinCongViec.onSave()
      if (rs) {
        await this.loadPhieuThiCongDetail()
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
          await this.loadData(this.congviec.phieuTcId)
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
          await this.loadData(this.congviec.phieuTcId)
        }
      })
    },
    async tsbtnChuyenXuLy_Click() {
      this.$refs.dlgChuyenXuLy.openDialog()
    },
    async tsbtnTraPhieu_Click() {
      this.$refs.dlgTraPhieu.openDialog()
    },
    async tsbtnHoanThanh_Click() {
      if (this.dsKetQuaThietBiCapPhat < 1) {
        this.$toast.error('Vui lòng cấp phát điện cho ít nhất một thiết bị trước khi hoàn thành công việc')
        return
      }
      let flag = await this.$confirm('Có chắc bạn muốn hoàn thành công việc?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
      if (flag) {
        try {
          const payload = {
            phieuTcId: this.congviec.phieuTcId,
            trangthaiId: 24 // 24 : trang thai hoan thanh
          }
          await api.updateTrangThaiPhieuTC(this.$root.context, payload)
          this.$toast.success('Hoàn thành công việc thành công')
          await this.loadData(this.congviec.phieuTcId)
        } catch (e) {
          if (e.response && e.response.data) {
            this.$toast.error('Lỗi hoàn thành công việc: ' + e.response.data.message)
          } else {
            this.$toast.error('Lỗi hoàn thành công việc: ' + e.message)
          }
        }
      }
      this.$router.push('/tainguyen-idc/ketqua-congsuatdien?loaicv=29')
    },
    actionColumn(parent) {
      const self = this
      return function() {
        return {
          template: {
            template: `
            <div class="action-buttons gap-1">
              <button
                v-if="!data.idOCam"
                class="btn btn-sm btn-outline-primary ml-1"
                @click="onCapPhat()">
                <i class="fa fa-plug"></i> Cấp phát
              </button>
              <button
                class="btn btn-sm btn-outline-danger ml-1"
                @click="onDelete()">
                <i class="fa fa-trash"></i> Gỡ
              </button>
            </div>
          `,
            methods: {
              async onCapPhat() {
                self.$refs.dlgCapPhatDien.phieuTcId = null
                self.$refs.dlgCapPhatDien.selectedThietBi = this.data
                self.$nextTick(() => {
                  self.$refs.dlgCapPhatDien.openDialog()
                })
              },
              async onDelete() {
                const payload = {
                  thiCongDienId: this.data.idThiCongDien,
                  loai: 'CAPPHAT',
                  thietBiId: this.data.idThietBi,
                  oCamId: this.data.idOCam
                }
                try {
                  await self.$confirm('Bạn có chắc muốn xoá thiết bị thi công này không?', 'Xác nhận xoá', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không',
                    type: 'warning'
                  })
                  await api.deleteKetQuaCongSuatDien(self.$root.context, payload)
                  self.$toast.success('Xoá thiết bị thi công thành công')
                  await self.loadData(self.congviec.phieuTcId)
                } catch (e) {
                  if (e.response?.data?.message) {
                    self.$toast.error('Lỗi xoá thiết bị thi công: ' + e.response.data.message)
                  } else {
                    self.$toast.error('Lỗi xoá thiết bị thi công: ' + e.message)
                  }
                }
              }
            }
          }
        }
      }
    },
    onBackToResult() {
      this.$router.push('/tainguyen-idc/ketqua-congsuatdien?loaicv=29')
    }
  } // end of methods
}
</script>
