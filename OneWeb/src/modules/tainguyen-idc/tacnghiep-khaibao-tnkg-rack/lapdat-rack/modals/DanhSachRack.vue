<template src="./DanhSachRack.html"></template>

<script>
import ThongTinRackModal from '../../components/thongtin-rack-modal'
export default {
  components: {
    ThongTinRackModal
  },
  props: {
    phieuThiCongId: {
      type: Number,
      default: null
    }
  },
  data: function() {
    return {
      dataItem: {},
      selectedRack: null,
      rackDataSource: [],
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  watch: {

  },

  computed: {
    rackListItems: function () {
      return this.$refs.rackListItems;
    },
  },
  created: async function () {

  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function (value) {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return value.getHieuLucText(this.data.SUDUNG);
              },
            },
          },
        };
      };
    },
    getRackList: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: 'CHON-RACK-LAPDAT',
          thamSo1: this.phieuThiCongId
        });
        result = rs.data || [];
        dataItem = rs;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    clearAll() {
      this.dataItem = {};
    },
    closeDialog() {
      this.clearAll();
      this.$refs.ModalDanhSachRack.hide();
    },
    async dialogOpen() {
      try {
        this.$root.showLoading(true);
        this.clearAll();
        this.rackDataSource = await this.getRackList();
        this.$refs.ModalDanhSachRack.show();

      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },
    onRowSelected: function(args) {
      this.selectedRack = args.data;
      console.log('Selected Rack ID:', args);
    },

    async onChonRack() {

      if (!this.selectedRack) {
        this.$toast.warning('Vui lòng chọn rack để lắp đặt')
        return
      }

      if (!this.phieuThiCongId) {
        this.$toast.warning('Không có thông tin phiếu thi công')
        return
      }

      let flag = await this.$confirm('Bạn có muốn chọn lắp đặt rack này không ?', 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })

      if (flag) {
        try {
          this.$root.showLoading(true)
          const requestBody = {
            idPhieuThiCong: this.phieuThiCongId,
            idRack: this.selectedRack.RACK_ID
          }

          let response = null
          response = await this.$root.context.post('/web-ecms/thicong-rack/chon-rack-thaodo', requestBody)

          if (response || response.data) {
            this.$root.toastSuccess('Chọn rack thành công')
            this.$emit('rackAllocated')
            this.closeDialog()
          } else {
            if (response && response.message_detail) {
              this.$root.toastError(response.message_detail)
            } else {
              this.$root.toastError('Chọn rack thất bại')
            }
          }
        } catch (err) {
          if (err && err.response && err.response.data && err.response.data.message_detail) {
            this.$root.toastError(err.response.data.message_detail)
          } else {
            this.$root.toastError('Chọn rack thất bại')
          }
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    onRackRowDoubleClick: async function(args) {
      this.dataItem = args.rowData
      this.$refs.popupChiTietRack.dialogOpen(this.dataItem.RACK_ID)
    },


    onOverlayClick: function() {
      this.$refs.ModalDanhSachRack.hide();  // hoặc xử lý khác
    },
  }
}
</script>

