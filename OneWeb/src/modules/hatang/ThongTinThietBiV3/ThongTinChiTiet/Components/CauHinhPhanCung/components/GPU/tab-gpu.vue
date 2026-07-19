<template src="./tab-gpu.html"></template>
<script>
import EditGPUModal from './edit-gpu-modal.vue'
export default {
  components: {
    EditGPUModal
  },
  props: {
    thietBiCurrent: {
      type: Object,
      required: true
    },
    dsGPU: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      localDsGPU: this.dsGPU
    }
  },
  computed: {
    grdItems: function() {
      // Dữ liệu mẫu – người sau chỉ cần thay thế bằng API
    }
  },
  methods: {
    // ---------------- CPU Action Column -----------------
    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `
            <div class="d-flex align-items-center justify-content-center">
              <b-button size="sm" variant="outline-success" class="ml-1" @click="editItem()" title="Sửa" >
                <i class="fa fa-edit"></i>
              </b-button>
              <b-button size="sm" variant="outline-danger" class="ml-1" @click="deleteItem()" title="Xóa">
                <i class="fa fa-trash"></i>
              </b-button>
            </div>`,
            methods: {
              deleteItem() {
                parent.deleteGPU(this.data)
              },
              editItem() {
                parent.editGPU(this.data)
              }
            }
          }
        }
      }
    },
    deleteGPU: async function(data) {
      // Xử lý xóa GPU
      let flag = await this.$confirm(`Bạn có chắc chắn muốn xóa không?`, 'Xác nhận gỡ', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Hủy',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })

      if (flag) {
        try {
          this.$root.showLoading(true)
          if (data.id) {
            await this.apiXoaGpu(data.id)
          }
          this.localDsGPU = this.localDsGPU.filter((gpu) => gpu.STT !== data.STT)
          this.$toast.success('Xóa GPU thành công')
        } catch (error) {
          console.error('Error deleting RAM:', error)
          this.$toast.error('Có lỗi xảy ra khi xóa GPU')
        } finally {
          this.$root.showLoading(false)
        }
        await this.loadThongTinDaCam()
      }
    },
    editGPU: async function(data) {
      await this.$refs.editGPUModal.openModal(data);
    },
    onGenerateGPU: async function(data) {
      const msg = 'Bạn có chắc chắn muốn sinh tự động cổng dữ liệu này không?'
      //chưa có nghiệp vụ
      // this.dsCPU.length === 0 && this.thietBiCurrent.SL_CPU_DACAM === 0
      //   ? 'Bạn có chắc chắn muốn sinh tự động cổng dữ liệu này không?'
      //   : this.dsCPU.length > 0 && this.thietBiCurrent.SL_CPU_DACAM != 0
      //   ? 'Việc sinh cổng dữ liệu tự động sẽ xóa toàn bộ cổng dữ liệu hiện có. Bạn có chắc chắn muốn tiếp tục?'
      //   : 'Tồn tại cổng dữ liệu đã sử dụng. Bạn có chắc chắn muốn sinh số cổng còn lại không?'

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
      const newItems = []
      const existingItems = this.localDsGPU || []
      // --- MODE 1: CÓ PORT ĐÃ SỬ DỤNG -> GEN TIẾP, KHÔNG XOÁ CŨ ---
      let stt = 0
      if (existingItems.length > 0) {
        // soItem lúc này hiểu là SỐ CỔNG MUỐN THÊM
        // tìm số thứ tự lớn nhất hiện có để gen tiếp
        let lastOrder = existingItems.reduce(
          (max, p) => {
            const so = Number(p.STT)
            return !isNaN(so) && so > max ? so : max
          },
          0
        )
        stt = lastOrder + 1
      } else {
        stt = 1
      }
      newItems.push({
        STT: stt,
        MODEL: null,
        SERIAL: null,
        TRANGTHAIVH_ID: 10,
        TRANGTHAI_VH: 'Đang hoạt động',
        THIETBISB_ID: this.thietBiCurrent.THIETBISB_ID
      })
      this.localDsGPU = [...existingItems, ...newItems]
      await this.loadThongTinDaCam()
    },
    loadThongTinDaCam: async function() {
      this.thietBiCurrent.SL_GPU = this.localDsGPU.length
    },
    apiXoaGpu: async function(id) {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/xoa-gpu`, {
          id: id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
      return result
    }
  }
}
</script>
