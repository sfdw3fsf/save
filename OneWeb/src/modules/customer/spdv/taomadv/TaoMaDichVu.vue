<template src='./TaoMaDichVu.html'>

</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import CapNhat from './popups/capnhat/CapNhatDichVu'

export default {
  name: 'TaoMaDichVu',
  components: {
    breadcrumb,
    CapNhat
  },
  data() {
    return {
      header: {
        title: 'Tạo mã dịch vụ',
        list: [
          { name: 'Sản phẩm dịch vụ', link: { name: 'Ui.cards' } },
          { name: 'Tạo mã dịch vụ', link: { name: 'Ui.buttons' } }
        ]
      },
      initialData: {
        NHOM_SPDV: [],
        DONVI: [],
        PHUONGTHUC_PT: [],
        DM_VONGDOI_SPDV: [],
        DM_DICHVU_CLVT: [],
        TRANGTHAI: []
      },
      tag: 1, //Đề nghị cấp mã  dịch vụ
      frmCapNhatCom: null,
      dv_clvt_id: null,
      nhomspdv_id: null,
      phuongthucpt_id: null,
      donvi_id: null,
      trangthai_id: null,
      ma_dt: null,
      ten_chitieu: null,
      dsDichVu: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          // {
          //   fieldName: 'nhomgiaiphap',
          //   headerText: 'Nhóm giải pháp'
          // },
          {
            fieldName: 'dv_chuquan',
            headerText: 'Đơn vị chủ quản'
          },
          {
            fieldName: 'ma_dt',
            headerText: 'Mã định danh'
          },
          {
            fieldName: 'ten_thuongmai',
            headerText: 'Tên thương mại'
          },
          {
            fieldName: 'ten_viettat',
            headerText: 'Tên viết tắt'
          },
          {
            fieldName: 'trangthai_spdv',
            headerText: 'Trạng thái'
          }
        ]
      },
      animationSettings: { effect: 'None' },
      selectedItem: null,
      dsChuaGan: [],
      dsDaGan: [],
      history: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'thaotac',
            headerText: 'Thao tác'
          }
        ]
      }
    }
  },
  async mounted() {
    await this.initData();
    await this.timkiem();
  },
  methods: {
    async initData() {
      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_lay_danhmuc_chung', {})
      if (rs.data.data) {
        this.initialData = rs.data.data
        // this.dv_clvt_id = this.initialData.DM_DICHVU_CLVT[0].dv_clvt_id
        // this.nhomspdv_id = this.initialData.NHOM_SPDV[0].nhomspdv_id
        // this.phuongthucpt_id = this.initialData.PHUONGTHUC_PT[0].phuongthuc_id
        // this.donvi_id = this.initialData.DONVI[0].donvi_id
        // this.trangthai_id = this.initialData.TRANGTHAI[0].trangthai_id
      }
      await this.timkiem();
    },
    selectedRowChanged(item) {
      this.selectedItem = item
    },
    async timkiem() {
      try {
        this.loading(true)
        let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_ds_dichvu', {
          tag: 1,
          nhomspdv_id: this.nhomspdv_id,
          phuongthuc_id: this.phuongthucpt_id,
          donvi_id: this.donvi_id,
          trangthai_id: this.trangthai_id,
          ma_dt: this.ma_dt,
          ten_chitieu: this.ten_chitieu
        })

        if (rs.data.data) {
          this.dsDichVu.data = rs.data.data.map((item, index) => ({
            stt: index + 1,
            ...item
          }))
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async nhapmoi() {
      this.selectedItem = null
      this.$refs.popupEdit.openDialog(this.selectedItem)
    },
    async sua() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ để sửa')
        return
      }
      this.$refs.popupEdit.openDialog(this.selectedItem)
    },
    async xoa() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }

      if (!confirm('Bạn có chắc chắn muốn xóa dịch vụ không?')) return

      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/fn_xoa_dichvu', { madt_id: this.selectedItem.madt_id })
      if (rs.data.error === '200' && rs.data.data === 'ok') {
        this.$toast.success('Xóa dịch vụ thành công')
        await this.timkiem()
      } else {
        this.$toast.error(rs.data.message)
      }
    },
    async lichsu() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }
      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_lay_ls_dichvu', { madt_id: this.selectedItem.madt_id })
      if (rs.data.data) {
        this.history.data = rs.data.data.map((item, index) => (
          {
            stt: index + 1,
            ...item
          }
        ))

        this.$refs.popupHistory.show()
      }
    },
    async guiPheDuyet() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }

      if (!confirm('Bạn có chắc chắn muốn gửi phê duyệt dịch vụ không?')) return

      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/fn_gui_duyet_dichvu_td', { madt_id: this.selectedItem.madt_id })
      if (rs.data.error === '200' && rs.data.data === 'ok') {
        this.$toast.success('Gửi phê duyệt dịch vụ thành công')
        this.$refs.popupEdit.hideDialog()
        await this.timkiem()
      } else {
        this.$toast.error(rs.data.message)
      }
    }
  }
}
</script>

<style scoped>

</style>
