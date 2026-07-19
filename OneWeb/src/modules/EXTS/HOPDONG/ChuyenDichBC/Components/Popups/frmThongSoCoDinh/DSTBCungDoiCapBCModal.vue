<template>
    <b-modal
    ref="popupDSTBCungDoiCap"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn mã truy nhập
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-col box-form">
            <KDataGrid
              :columns="columns"
              :dataSource="dataSources"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              :showColumnCheckbox="false"
              :enabledSelectFirstRow="true"
              @selectedItemsChanged="gridDanhSachChanged"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import {DichVuVienThong} from './ThamSo'
export default {
  name: 'DSTBCungDoiCapBCModal',
  components: {
    ActionTop,
    KDataGrid
  },
  props: {
    tinh_tc: {
      type: Number,
      default: 0
    },
    khachhang_id: {
      type: Number,
      default: 0
    },
    kieuld_id: {
      type: Number,
      default: 0
    },
    ma_tn: {
      type: String,
      default: ''
    },
    ma_doicap: {
      type: String,
      default: ''
    },
    loaitb_id: {
      type: Number,
      default: 0
    },
    thuebao_id: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      actions: [
        {
          id: 'chapnhan',
          name: 'Chấp nhận',
          active: true,
          icon_class: 'ui-1_check-circle-08 nc-icon-glyph'
        }
      ],
      columns: [
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc',
          allowFiltering: true,
          width: 70
        },
        {
          fieldName: 'loaihinh',
          headerText: 'Loại hình TB',
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: 'trangthai_tb',
          headerText: 'Trạng thái',
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: 'matb_tn',
          headerText: 'Mã truy nhập',
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: 'madoicap',
          headerText: 'Mã đôi cáp',
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ LĐ',
          allowFiltering: true,
          width: 120
        }
      ],
      dataSources: [],
      rowSelected: null
    }
  },
  methods: {
    showModal () {
      this.$refs['popupDSTBCungDoiCap'].show()
    },
    hideModal () {
      this.$refs['popupDSTBCungDoiCap'].hide()
    },

    async onActionClick (action) {
      if (action.id == 'chapnhan') {
        // calback
        if (!this.rowSelected) {
          this.$toast.error('Hãy chọn mã thuê bao!')
          return
        }
        // Kiểm tra xem có cùng đôi cáp hay không?
        let vdvvt_id = Number(await this.fn_map_loaihinh_tb(this.loaitb_id, 2, '-1'))
        if (vdvvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || vdvvt_id == DichVuVienThong.DICHVU_CNTT ||
                    vdvvt_id == DichVuVienThong.TRUNGTAM_DULIEU || vdvvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
          let ma_tn = this.rowSelected.ma_tb ? this.rowSelected.ma_tb : ''
          let ma_doicap = this.rowSelected.madoicap ? this.rowSelected.madoicap : ''
          let loaitb_id = this.rowSelected.loaitb_id ? this.rowSelected.loaitb_id : 0
          let thuebao_id = this.rowSelected.thuebao_id ? this.rowSelected.thuebao_id : 0

          if (this.rowSelected.trangthaitb_id != 1) {
            this.$toast.warning('Mã thuê bao ' + ma_tn + ' đang ở trạng thái ' + this.rowSelected.trangthai_tb)
          }
          this.$emit('accept', {
            ma_tn: ma_tn,
            ma_doicap: ma_doicap,
            loaitb_id: loaitb_id,
            thuebao_id: thuebao_id
          })
          this.hideModal()
        } else {
          // kiemtra_matb_truynhap
          let kt = await this.kiemtra_matb_truynhap({
            tinhthicong_id: this.tinh_tc,
            madoicap: this.rowSelected.madoicap ? this.rowSelected.madoicap : '',
            loaitb_id: this.rowSelected.loaitb_id ? this.rowSelected.loaitb_id : 0,
            kieu_id: 2 // KIEULAP.LAP_LINE_SAN=2
          })
          // KieuLapDat.TACH_VLAN=581
          if (kt == 0 || this.kieuld_id == 581) {
            let ma_tn = this.rowSelected.ma_tb ? this.rowSelected.ma_tb : ''
            let ma_doicap = this.rowSelected.madoicap ? this.rowSelected.madoicap : ''
            let loaitb_id = this.rowSelected.loaitb_id ? this.rowSelected.loaitb_id : 0
            let thuebao_id = this.rowSelected.thuebao_id ? this.rowSelected.thuebao_id : 0

            if (this.rowSelected.trangthaitb_id != 1) {
              this.$toast.warning('Mã thuê bao ' + ma_tn + ' đang ở trạng thái ' + this.rowSelected.trangthai_tb)
            }
            this.$emit('accept', {
              ma_tn: ma_tn,
              ma_doicap: ma_doicap,
              loaitb_id: loaitb_id,
              thuebao_id: thuebao_id
            })
            this.hideModal()
          } else {
            if (this.kieuld_id != 581) {
              let resultConfirm = await this.confirm('Mã thuê bao ' + this.rowSelected.ma_tb + ' đang có cùng đôi cáp với thuê bao khác. Bạn có muốn chọn không?', 'Thông báo')
              if (resultConfirm == 0) {
                return
              }
              let ma_tn = this.rowSelected.ma_tb ? this.rowSelected.ma_tb : ''
              let ma_doicap = this.rowSelected.madoicap ? this.rowSelected.madoicap : ''
              let loaitb_id = this.rowSelected.loaitb_id ? this.rowSelected.loaitb_id : 0
              let thuebao_id = this.rowSelected.thuebao_id ? this.rowSelected.thuebao_id : 0
              this.$emit('accept', {
                ma_tn: ma_tn,
                ma_doicap: ma_doicap,
                loaitb_id: loaitb_id,
                thuebao_id: thuebao_id
              })
              this.hideModal()
            }
          }
        }
      }
    },
    async handleShowModal () {
      this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = false
      this.dataSources = []
      this.rowSelected = null

      setTimeout(() => {
        this.initDuLieu()
      }, 500)
    },
    async confirm (message, title) {
      try {
        let result = await this.$confirm(message, title, {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        })
        return 1
      } catch (e) {
        return 0
      }
    },
    gridDanhSachChanged (data) {
      if (this.dataSources.length <= 0 || data.length <= 0) {
        this.rowSelected = null
        return
      }
      this.rowSelected = data[0]
    },
    async initDuLieu () {
      this.dataSources = await this.lay_ds_tb_cungdoicap_theo_khid({
        tinhthicong_id: this.tinh_tc,
        khachhang_id: this.khachhang_id,
        kieuld_id: this.kieuld_id
      })
    },
    async lay_ds_tb_cungdoicap_theo_khid (data) {
      try {
        this.loading(true)
        let response = await this.axios.post('web-bancheo/tracuu/lay_ds_tb_cungdoicap_theo_khid', data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async fn_map_loaihinh_tb (loaitb_id, type, defaultValue) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb', {
          param: loaitb_id,
          type: type
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return defaultValue
        }
      } catch (e) {
        this.loading(false)
        return defaultValue
      }
    },
    async kiemtra_matb_truynhap (data) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/tracuu/kiemtra_matb_truynhap', data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return Number(response.data.data)
        } else {
          return 0
        }
      } catch (e) {
        this.loading(false)
        return 0
      }
    }
  },
  watch: {
    rowSelected (val) {
      if (val != null) {
        this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = true
      } else {
        this.actions[this.actions.findIndex(x => x.id == 'chapnhan')].active = false
      }
    }
  }
}
</script>
