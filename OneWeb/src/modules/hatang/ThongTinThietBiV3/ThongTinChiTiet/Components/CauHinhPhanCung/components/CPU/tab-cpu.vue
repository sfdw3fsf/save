<template src="./tab-cpu.html"></template>

<script>
import EditCPUModal from './edit-cpu-modal.vue'
export default {
  props: {
    thietBiCurrent: {
      type: Object,
      required: true
    },
    dsCPU: {
      type: Array,
      required: true
    },
  },
  components: {
    EditCPUModal
  },
  data() {
    return {
      batDauTu: null,
      dinhDangTen: '',
      localDsCpu: this.dsCPU,
    }
  },
  computed: {
    grdItems: function() {
      // Dữ liệu mẫu – người sau chỉ cần thay thế bằng API
    },
  },
  methods: {
    // ---------------- CPU Action Column -----------------
    cpuActionColumn(parent) {
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
                parent.deleteCPU(this.data)
              },
              editItem() {
                parent.editCPU(this.data)
              }
            }
          }
        }
      }
    },
    deleteCPU: async function(data) {
      // Xử lý xóa CPU
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
            await this.apiXoaCpu(data.id)
          }
          this.localDsCpu = this.localDsCpu.filter(cpu => cpu.STT !== data.STT);
          this.$toast.success('Xóa CPU thành công')
        } catch (error) {
          console.error('Error deleting CPU:', error)
          this.$toast.error('Có lỗi xảy ra khi xóa CPU')
        } finally {
          this.$root.showLoading(false)
        }
        await this.loadThongTinDaCam();
      }
    },
    editCPU: async function(data) {
      await this.$refs.editCPUModal.openModal(data)
    },
    validate() {
      let msg = ''
      if (this.thietBiCurrent.SL_KHECPU == null) {
        msg = 'Số khe cắm không được để trống.'
      } else if (isNaN(this.thietBiCurrent.SL_KHECPU) || Number(this.thietBiCurrent.SL_KHECPU) <= 0) {
        msg = 'Số khe cắm phải là số lớn hơn 0.'
      } else if (this.batDauTu == null) {
        msg = 'Bắt đầu từ không được để trống.'
      } else if (isNaN(this.batDauTu) || Number(this.batDauTu) < 0) {
        msg = 'Bắt đầu từ phải là số lớn hơn hoặc bằng 0.'
      }else if (Number(this.batDauTu) > Number(this.thietBiCurrent.SL_KHECPU)) {
        msg = 'Bắt đầu phải nhỏ hơn số khe cắm.'
      }
       else if (!this.dinhDangTen || this.dinhDangTen.trim() == '') {
        msg = 'Định dạng tên không được để trống.'
      }

      if (msg == '') return true
      this.$root.toastError(msg)
      return false
    },
    onGenerateCPU: async function(data) {
      const soItem = Number(this.thietBiCurrent.SL_KHECPU)
      const batDauTu = Number(this.batDauTu)
      const dinhDangTen = (this.dinhDangTen || '').trim()
      
      
      if (this.validate()) {
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
        const existingItems = this.localDsCpu || []
        const usedItems = existingItems.filter((p) => p.ID != null) // Lọc các port đã sử dụng (có ID)
        // --- MODE 1: CÓ PORT ĐÃ SỬ DỤNG -> GEN TIẾP, KHÔNG XOÁ CŨ ---
        if (usedItems.length > 0) {
          // soItem lúc này hiểu là SỐ CỔNG MUỐN THÊM
          // tìm số thứ tự lớn nhất hiện có để gen tiếp
          let lastOrder = existingItems.reduce(
            (max, p) => {
              const so = Number(p.STT)
              return !isNaN(so) && so > max ? so : max
            },
            batDauTu > 0 ? batDauTu - 1 : 0
          )

          for (let i = 1; i <= soItem; i++) {
            const stt = lastOrder + i
            const ten = this.$parent.formatName(dinhDangTen, stt)
            
            newItems.push({
              STT: stt,
              TEN_VITRI: ten,
              MODEL: null,
              SL_CORES: 0,
              SL_THREADS: 0,
              TRANGTHAIVH_ID: 10,
              TRANGTHAI_VH: 'Đang hoạt động',
              THIETBISB_ID: this.thietBiCurrent.THIETBISB_ID
            })
          }
          // Giữ nguyên danh sách cũ + append thêm
          this.localDsCpu = [...existingItems, ...newItems]
        } else {
          // --- MODE 2: KHÔNG CÓ PORT ĐÃ SỬ DỤNG -> GEN LẠI TỪ ĐẦU ---
          for (let i = 0; i < soItem; i++) {
            const stt = batDauTu + i
            const ten = this.$parent.formatName(dinhDangTen, stt)
            newItems.push({
              STT: stt,
              TEN_VITRI: ten,
              MODEL: null,
              SL_CORES: 0,
              SL_THREADS: 0,
              TRANGTHAIVH_ID: 10,
              TRANGTHAI_VH: 'Đang hoạt động',
              THIETBISB_ID: this.thietBiCurrent.THIETBISB_ID
            })
          }
          this.localDsCpu = newItems
        }
        await this.loadThongTinDaCam();
      }
    },
    loadThongTinDaCam: async function() {
      this.thietBiCurrent.SL_CPU_DACAM = this.localDsCpu.length
      const totalCPUCores = this.localDsCpu.reduce((sum, cpu) => {
        return sum + Number(cpu.SL_CORES || 0)
      }, 0)
      const totalCPUTheads = this.localDsCpu.reduce((sum, cpu) => {
        return sum + Number(cpu.SL_THREADS || 0)
      }, 0)
      this.thietBiCurrent.TONG_CORE_CPU = totalCPUCores + ' Cores / ' + totalCPUTheads + ' Threads'
    },

    apiXoaCpu: async function(id) {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/xoa-cpu`, {
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
<style>
.hardware-monitor-container {
  padding: 20px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.resource-card {
  border: 1px solid #ddd;
  border-radius: 12px;
  padding: 20px;
  text-align: center;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.2s ease;
}

.resource-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.resource-card .icon {
  font-size: 2rem;
  margin-bottom: 12px;
  color: #0d6efd;
}

.resource-card .label {
  font-size: 0.9rem;
  color: #6c757d;
  margin-bottom: 4px;
}

.resource-card .value {
  font-size: 1.5rem;
  font-weight: bold;
  color: #212529;
}

.resource-card .progress {
  height: 6px;
  border-radius: 3px;
  margin-top: 10px;
  background-color: #e9ecef;
}

.resource-card .title {
  font-size: 1rem;
  color: #495057;
  margin-top: 8px;
}

/* Đảm bảo input trong box-form căn chỉnh đẹp */
.info-row .key {
  min-width: 150px;
}
</style>
