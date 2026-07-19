<template src="./tab-blade.html"></template>
<script>
import EditBladeModal from './edit-blade-modal.vue'
export default {
  components: {
    EditBladeModal
  },
  props: {
    thietBiCurrent: {
      type: Object,
      required: true
    },
    dsBlade: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      batDauTu: null,
      dinhDangTen: '',
      localDsBlade: this.dsBlade
    }
  },
  computed: {
    grdItems: function() {
      // Dữ liệu mẫu – người sau chỉ cần thay thế bằng API
    }
  },
  methods: {
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
                parent.deleteBlade(this.data)
              },
              editItem() {
                parent.editBlade(this.data)
              }
            }
          }
        }
      }
    },
    deleteBlade: async function(data) {
      // Xử lý xóa Blade
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
            await this.apiXoaBlade(data.id)
          }
          this.localDsBlade = this.localDsBlade.filter((blade) => blade.STT !== data.STT)
          await this.loadThongTinDaCam()
        } catch (error) {
          console.error('Error deleting Blade:', error)
          this.$toast.error('Có lỗi xảy ra khi xóa Blade')
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    editBlade: async function(data) {
      await this.$refs.editBladeModal.openModal(data)
    },
    onGenerateBlade: async function(data) {
      const soItem = Number(this.thietBiCurrent.SL_KHEBLADE)
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
        const existingItems = this.localDsRAM || []
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
              THIETBI_ID: null,
              TEN: null,
              SERIAL_NUMBER: null,
              TRANGTHAIVH_ID: 10,
              TRANGTHAI_VH: 'Đang hoạt động'
            })
          }
          // Giữ nguyên danh sách cũ + append thêm
          this.localDsBlade = [...existingItems, ...newItems]
        } else {
          // --- MODE 2: KHÔNG CÓ PORT ĐÃ SỬ DỤNG -> GEN LẠI TỪ ĐẦU ---
          for (let i = 0; i < soItem; i++) {
            const stt = batDauTu + i
            const ten = this.$parent.formatName(dinhDangTen, stt)
            newItems.push({
              STT: stt,
              TEN_VITRI: ten,
              THIETBI_ID: null,
              TEN: null,
              SERIAL_NUMBER: null,
              TRANGTHAIVH_ID: 10,
              TRANGTHAI_VH: 'Đang hoạt động'
            })
          }
          this.localDsBlade = newItems
        }
        await this.loadThongTinDaCam()
      }
    },
    validate() {
      let msg = ''
      if (this.thietBiCurrent.SL_KHEBLADE == null) {
        msg = 'Số khe cắm không được để trống.'
      } else if (isNaN(this.thietBiCurrent.SL_KHEBLADE) || Number(this.thietBiCurrent.SL_KHEBLADE) <= 0) {
        msg = 'Số khe cắm phải là số lớn hơn 0.'
      } else if (this.batDauTu == null) {
        msg = 'Bắt đầu từ không được để trống.'
      } else if (isNaN(this.batDauTu) || Number(this.batDauTu) < 0) {
        msg = 'Bắt đầu từ phải là số lớn hơn hoặc bằng 0.'
      } else if (Number(this.batDauTu) > Number(this.thietBiCurrent.SL_KHEBLADE)) {
        msg = 'Bắt đầu phải nhỏ hơn số khe cắm.'
      } else if (!this.dinhDangTen || this.dinhDangTen.trim() == '') {
        msg = 'Định dạng tên không được để trống.'
      }

      if (msg == '') return true
      this.$root.toastError(msg)
      return false
    },
    loadThongTinDaCam: async function() {
      this.thietBiCurrent.SL_BLADE_DACAM = this.localDsBlade.length
      // const totalRam = this.localDsBlade.reduce((sum, ram) => {
      //   return sum + Number(ram.DUNGLUONG || 0)
      // }, 0)
      // this.thietBiCurrent.TONG_DL_RAM = totalRam
    },
    apiXoaBlade: async function(id) {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/xoa-blade`, {
          id: id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
      return result
    },
    updateBlade: async function(data) {
      this.localDsBlade = this.localDsBlade.map(blade => {
        if (blade.STT === data.STT) {
          return { ...data };
        }
        return blade;
      });
    },
  }
}
</script>
