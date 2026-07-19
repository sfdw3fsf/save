<template src='./CapNhatDichVu.html'>

</template>

<script>
import '@/assets/vendor/jquery/split'

export default {
  name: 'CapNhat',
  data() {
    return {
      item: {
        ten_viettat: null,
        ghichu_ng: null,
        phuongthuc_id: null,
        madt_id: null,
        ten_thuongmai: null,
        tg_trienkhai: null,
        dv_phattrien_id: null,
        ghichu: null,
        dv_chuquan: null,
        ten_tienganh: null,
        thongtin_sla: null,
        loai_dv: 0,
        ma_dt: null,
        ten_chitieu: null,
        nhomspdv_id: null,
        dv_chuquan_id: null,
        sanpham_data: null,
      },
      isEdit: true,
      SPDV_SM_ID: null,
      dsChuaGan: [],
      columnsChuaGan: [
        {
          fieldName: 'ma_spdv',
          headerText: 'Mã',
          width: 150
        },
        {
          fieldName: 'ten_spdv',
          headerText: 'Sản phẩm chưa gắn'
        }
      ],
      dsDaGan: [],
      columnsDaGan: [
        {
          fieldName: 'ma_spdv',
          headerText: 'Mã',
          width: 150
        },
        {
          fieldName: 'ten_spdv',
          headerText: 'Sản phẩm đã gắn'
        },
        {
          fieldName: 'checked',
          headerText: 'Chọn làm mã định danh',
          textAlign: 'center',
          template: this.columnChonDinhDanh(this)
        }
      ],
      selectedDaGan: null,
      selectedChuaGan: null,
      comboboxData: {
        NHOM_SPDV: [],
        DONVI: [],
        PHUONGTHUC_PT: [],
        DM_VONGDOI_SPDV: [],
        DM_DICHVU_CLVT: [],
        TRANGTHAI: []
      },
      showModal: false
    }
  },
  watch: {
    async showModal(newValue) {
      if (newValue) {
        await this.loadCombobox()
      }
    }
  },
  methods: {
    columnChonDinhDanh(parent) {
      return function() {
        return {
          template: {
            template: `
              <input type='radio' :checked='isChecked' name='SPDV_SM_ID' :value='data.spdv_id' @change='changeCheck'>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            },
            computed: {
              isChecked() {
                return this.data.checked
              }
            },
            methods: {
              changeCheck(value) {
                this.parent.changeCheck(this.data)
              }
            }
          }
        }
      }
    },
    changeCheck(item) {
      this.item = {
        ...this.item,
        ma_dt: item.ma_spdv,
        ten_chitieu: item.ten_spdv,
        ten_thuongmai: item.ten_thuongmai,
        ten_tienganh: item.ten_tienganh,
        ten_viettat: item.ten_viettat,
        nhomspdv_id: item.nhomspdv_id,
        phuongthuc_id: item.phuongthuc_id,
        ghichu: item.ghichu,
        ghichu_ng: item.ghichu_ng
      }
      this.SPDV_SM_ID = item.spdv_id
    },
    hideDialog() {
      this.$refs.dialogCapNhat.hide()
    },
    async openDialog(data) {
      if (data) {
        this.item = data
      } else {
        this.item = {
          ten_viettat: null,
          ghichu_ng: null,
          phuongthuc_id: null,
          madt_id: null,
          ten_thuongmai: null,
          tg_trienkhai: null,
          dv_phattrien_id: null,
          ghichu: null,
          dv_chuquan: null,
          ten_tienganh: null,
          thongtin_sla: null,
          loai_dv: 0,
          ma_dt: null,
          ten_chitieu: null,
          nhomspdv_id: null,
          dv_chuquan_id: null,
          sanpham_data: null,
        }
      }

      await this.loadSpdv()
      this.$refs.dialogCapNhat.show()
    },
    async loadCombobox() {
      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_lay_danhmuc_chung', {})
      if (rs.data.data) {
        this.comboboxData = rs.data.data
      }
    },
    async loadSpdv() {
      try {
        let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_lay_ds_sanpham_dagan', {
          madt_id: this.item.madt_id
        })

        this.dsDaGan = rs.data.data.map((item, index) => ({
          ...item,
          checked: item.spdv_sm_id == item.spdv_id
        }))
      } catch (e) {
        this.dsDaGan = []
      }

      try {
        let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_lay_ds_sanpham_chuagan', {
          madt_id: this.item.madt_id
        })

        this.dsChuaGan = rs.data.data
      } catch (e) {
        this.dsChuaGan = []

      }
    },
    moveRight() {
      let selectedItems = this.$refs.dsDaGan.getSelectedRecords()
      if (selectedItems.length === 0) {
        this.$toast.error(`Vui lòng chọn dịch vụ`)
        return
      }
      let movedList = []
      for (const item of selectedItems) {
        this.dsChuaGan.unshift(item)
        movedList.push(item)
      }

      this.dsDaGan = this.dsDaGan.filter((x) => movedList.filter((y) => y.spdv_id == x.spdv_id).length == 0)
    },
    moveLeft() {
      let selectedItems = this.$refs.dsChuaGan.getSelectedRecords()
      if (selectedItems.length === 0) {
        this.$toast.error(`Vui lòng chọn dịch vụ`)
        return
      }
      let movedList = []
      for (const item of selectedItems) {
        this.dsDaGan.unshift(item)
        movedList.push(item)
      }

      this.dsChuaGan = this.dsChuaGan.filter((x) => movedList.filter((y) => y.spdv_id == x.spdv_id).length == 0)
    },
    async ghiLai() {
      if (this.item.ma_dt == '' || this.item.ma_dt == null) {
        this.$toast.error('Vui lòng nhập mã định danh')
        $('#ma_dt').focus()
        return
      }

      if (this.item.ten_chitieu == '' || this.item.ten_chitieu == null) {
        this.$toast.error('Vui lòng nhập tên dịch vụ')
        $('#ten_chitieu').focus()
        return
      }

      if (this.item.phuongthuc_id == '' || this.item.phuongthuc_id == null) {
        this.$toast.error('Vui lòng chọn phương thức phát triển')
        $('#phuongthuc_id').focus()
        return
      }

      if (this.item.nhomspdv_id == '' || this.item.nhomspdv_id == null) {
        this.$toast.error('Vui lòng chọn nhóm SPDV')
        $('#nhomspdv_id').focus()
        return
      }

      let api = '/web-qlspdv/ql_dichvu/fn_themmoi_dichvu'
      if (this.item.madt_id != null) {
        api = '/web-qlspdv/ql_dichvu/fn_capnhat_dichvu'
      }

      if (this.dsDaGan.length > 0) {
        let sanpham_data = [];
        for (const tmp of this.dsDaGan) {
          sanpham_data.push({SPDV_ID: tmp.spdv_id, SPDV_SM_ID: this.SPDV_SM_ID});
        }
        this.item.sanpham_data = JSON.stringify(sanpham_data)
      }

      let rs = await this.axios.post(api, this.item)
      if (rs.data.error === '200' && rs.data.data === 'ok') {
        this.$toast.success('Cập nhật dịch vụ thành công')
        this.hideDialog()
        this.$emit('reload')
      } else {
        this.$toast.error(rs.data.message)
      }
    },
    guiPheDuyet() {
      this.$emit('guiPheDuyet')
    }
  }
}
</script>

<style scoped>
/deep/ .modal-xl {
  max-width: 100% !important;
}
</style>
