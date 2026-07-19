<template src="./tab-khaydia.html"></template>
<script>
import EditKhayDiaModal from './edit-khaydia-modal.vue';
import DetailBDKModal from '../BoDieuKhien/detail-bdk-modal.vue';
export default {
  components: {
    EditKhayDiaModal,
    DetailBDKModal
  },
  props: {
    thietBiCurrent: {
      type: Object,
      required: true
    },
    dsKhayDia: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      localDsKhayDia: this.dsKhayDia,
    }
  },
  computed: {
    grdItems: function () {
      // Dữ liệu mẫu – người sau chỉ cần thay thế bằng API

    },
  },
  methods: {

    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `
            <div class="d-flex align-items-center justify-content-center">
              <b-button size="sm" variant="outline-success" class="ml-1" @click="viewItem()" title="Chi tiết" >
                <i class="fa fa-eye"></i>
              </b-button>
              <b-button size="sm" variant="outline-danger" class="ml-1" @click="deleteItem()" title="Xóa">
                <i class="fa fa-trash"></i>
              </b-button>
            </div>`,
            methods: {
              deleteItem() {
                parent.deleteBDK(this.data)
              },
              viewItem() {
                parent.viewItem(this.data)
              }
            }
          }
        }
      }
    },
    deleteKhayDia: async function(data) {
      // Xử lý xóa Khay Đĩa
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
            await this.apiXoa(data.id)
          }
          this.localDsKhayDia = this.localDsKhayDia.filter(khayDia => khayDia.STT !== data.STT);
        } catch (error) {
          console.error('Error deleting Khay Đĩa:', error);
          this.$toast.error('Có lỗi xảy ra khi xóa Khay Đĩa');
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    viewItem: async function (data) {
      await this.$refs.detailKHDModal.openModal(data, false);
    },
    onGenerateKhayDia: async function (data) {
      await this.$refs.editKHDModal.openModal();
    },
    
    apiXoa: async function(id) {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/xoa-tuadia-pc`, {
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
