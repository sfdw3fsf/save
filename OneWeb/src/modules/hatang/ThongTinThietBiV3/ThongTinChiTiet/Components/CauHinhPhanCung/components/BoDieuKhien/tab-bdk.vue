<template src="./tab-bdk.html"></template>
<script>
import EditBDKModal from './edit-bdk-modal.vue';
import DetailBDKModal from './detail-bdk-modal.vue';
export default {
  components: {
    EditBDKModal,
    DetailBDKModal
  },
  props: {
    thietBiCurrent: {
      type: Object,
      required: true
    },
    dsBodKh: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      localDsBodKh: this.dsBodKh,
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
    deleteBDK: async function(data) {
      // Xử lý xóa Bộ điều khiển
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
          this.localDsBodKh = this.localDsBodKh.filter(bodKh => bodKh.STT !== data.STT);
        } catch (error) {
          console.error('Error deleting BDK:', error);
          this.$toast.error('Có lỗi xảy ra khi xóa Bộ điều khiển');
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    viewItem: async function (data) {
      await this.$refs.detailBDKModal.openModal(data, true);
    },
    onGenerateBDK: async function () {
      await this.$refs.editBDKModal.openModal();
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
