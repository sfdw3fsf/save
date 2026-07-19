<template src="./tab-ocung.html"></template>
<script>

import EditOCungModal from './edit-soluong-ocung-modal';
export default {
  components: {
    EditOCungModal
  },
  props: {
    thietBiCurrent: {
      type: Object,
      required: true
    },
    dsOCung: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      batDauTu: null,
      dinhDangTen: '',
      localDsOCung: this.dsOCung,
    }
  },
  computed: {
    grdItems: function () {
      // Dữ liệu mẫu – người sau chỉ cần thay thế bằng API

    },
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
                parent.deleteOCung(this.data)
              },
              editItem() {
                parent.editOCung(this.data)
              }
            }
          }
        }
      }
    },
    deleteOCung: async function(data) {
      // Xử lý xóa Ổ cứng
      let flag = await this.$confirm(
        `Bạn có chắc chắn muốn xóa không?`,
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
          if (data.id) {
            await this.apiXoaOCung(data.id)
          }
          this.localDsOCung = this.localDsOCung.filter(ocung => ocung.STT !== data.STT);
          this.$toast.success('Xóa ổ cứng thành công');
        } catch (error) {
          console.error('Error deleting ổ cứng:', error);
          this.$toast.error('Có lỗi xảy ra khi xóa ổ cứng');
        } finally {
          this.$root.showLoading(false);
        }
        await this.loadThongTinDaCam();
      }
    },
    editOCung: async function (data) {
      await this.$refs.editOCungModal.openModal(data);
    },
    
    validate() {
      let msg = ''
      if (this.thietBiCurrent.SL_KHE_OCUNG == null) {
        msg = 'Số khe cắm không được để trống.'
      } else if (isNaN(this.thietBiCurrent.SL_KHE_OCUNG) || Number(this.thietBiCurrent.SL_KHE_OCUNG) <= 0) {
        msg = 'Số khe cắm phải là số lớn hơn 0.'
      } else if (this.batDauTu == null) {
        msg = 'Bắt đầu từ không được để trống.'
      } else if (isNaN(this.batDauTu) || Number(this.batDauTu) < 0) {
        msg = 'Bắt đầu từ phải là số lớn hơn hoặc bằng 0.'
      }else if (Number(this.batDauTu) > Number(this.thietBiCurrent.SL_KHE_OCUNG)) {
        msg = 'Bắt đầu phải nhỏ hơn số khe cắm.'
      }
       else if (!this.dinhDangTen || this.dinhDangTen.trim() == '') {
        msg = 'Định dạng tên không được để trống.'
      }

      if (msg == '') return true
      this.$root.toastError(msg)
      return false
    },

    onGenerateDSOCung: async function (data) {
      const soItem = Number(this.thietBiCurrent.SL_KHE_OCUNG)
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
        const existingItems = this.localDsOCung || []
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
              TEN_OCUNG: null,
              KYHIEU: null,
              DUNGLUONG: 0,
              LOAIOCUNG_ID : null,
              LOAIOCUNG : null,
              TOCDO: 0,
              KICHTHUOC: 0,
              DUNGLUONG: 0,
              PART_NUMBER: null,
              TRANGTHAIVH_ID: 10,
              TRANGTHAI_VH: 'Đang hoạt động',
              THIETBISB_ID: this.thietBiCurrent.THIETBISB_ID
            })
          }
          // Giữ nguyên danh sách cũ + append thêm
          this.localDsOCung = [...existingItems, ...newItems]
        } else {
          // --- MODE 2: KHÔNG CÓ PORT ĐÃ SỬ DỤNG -> GEN LẠI TỪ ĐẦU ---
          for (let i = 0; i < soItem; i++) {
            const stt = batDauTu + i
            const ten = this.$parent.formatName(dinhDangTen, stt)
            newItems.push({
              STT: stt,
              TEN_VITRI: ten,
              TEN_OCUNG: null,
              KYHIEU: null,
              DUNGLUONG: 0,
              LOAIOCUNG_ID : null,
              LOAIOCUNG : null,
              TOCDO: 0,
              KICHTHUOC: 0,
              DUNGLUONG: 0,
              PART_NUMBER: null,
              TRANGTHAIVH_ID: 10,
              TRANGTHAI_VH: 'Đang hoạt động',
              THIETBISB_ID: this.thietBiCurrent.THIETBISB_ID
            })
          }
          this.localDsOCung = newItems
        }
        await this.loadThongTinDaCam();
      }
    },
    loadThongTinDaCam: async function() {
      this.thietBiCurrent.SL_OCUNG_DACAM = this.localDsOCung.length
      const totalOCung = this.localDsOCung.reduce((sum, OCung) => {
        return sum + Number(OCung.DUNGLUONG || 0)
      }, 0)
      this.thietBiCurrent.TONG_DL_O_CUNG = totalOCung
    },
    apiXoaOCung: async function(id) {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/xoa-ocung`, {
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
