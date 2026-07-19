<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="tsbtnCapNhan_Click">
            <span class="icon one-save"></span>Chấp nhận
          </a>
        </li>
      </ul>

    </div>
    <div class="page-content">
      <div class="table-content">
        <DataGrid
          v-bind:dataSource="grcTruyNhap.list"
          v-bind:columns="grcTruyNhap.cols"
          ref="grcTruyNhap"
          :panelDataHeight= "'150'"
          :showFilter="true"
          :enablePagingServer="false"
          :enabledSelectFirstRow="true"
          :allowPaging="true"
          @rowSelected="rowGridTBSelected"

        />

      </div>

    </div>
  </div>
</template>

<script>
import api from './api'
import '@/assets/vendor/jquery/split'
import Vue from 'vue'
import moment from 'moment'
import {DialogPlugin} from '@syncfusion/ej2-vue-popups'
import {LoaiHopDong, DichVuVienThong, TrangThaiHD, LOAI_DV, TrangThaiTB, LoaiHinhTB} from '@/const/enums'
Vue.use(DialogPlugin)
export default {
  name: 'DSTBCungDoiCapBC',
  props: {
    khachhang_id: {
      type: Number,
      default: 0
    },
    tinhtc: {
      type: Number,
      default: 0
    },
    kieuld_id: {
      type: Number,
      default: 0
    }
  },
  data: function () {
    return {
      loaitb_id: 0,
      KieuLapDat: {
        THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
        THAYDOI_MUCCUOC_ADSL_TK: 13081,
        THAYDOI_KIEUTRA_MYTV: 270,
        THAYDOI_TOCDO_ADSL_TK: 13080,
        THAYDOI_TOCDO_MUCCUOC_ADSL_TK: 13082,
        THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,
        THAYDOI_TOCDOTHUC_ADSL: 655,
        DOIGOI_FSECURE: 668
      },
      dataResonse: {
        'ma_tn': '',
        'ma_doicap': '',
        'loaitb_id': 0,
        'thuebao_id': 0
      },
      grcTruyNhap: {
        list: [],
        cols: [
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'

          },
          {
            fieldName: 'loaihinh',
            headerText: 'Loại hình TB',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'

          },
          {
            fieldName: 'trangthaitb_id',
            headerText: 'Trạng thái',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'

          },
          {
            fieldName: 'matb_tn',
            headerText: 'Mã truy nhập',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'

          },
          {
            fieldName: 'madoicap',
            headerText: 'Mã đôi cáp',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'

          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ LĐ',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'

          }
        ]
      }
    }
  },
  methods: {
    async frmDSTBCungDoiCapBC_Load () {
      try {
        let response = await api.lay_ds_tb_cungdoicap_theo_khid(this.axios, {
          'tinhthicong_id': this.tinhtc,
          'khachhang_id': this.khachhang_id,
          'kieuld_id': this.kieuld_id
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.grcTruyNhap.list = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
    },
    rowGridTBSelected (args) {

    },
    async loadData () {
      await this.frmDSTBCungDoiCapBC_Load()
    },
    async tsbtnCapNhan_Click () {
      let vdvvt_id = -1
      let input1 = {}
      input1.type = 2
      input1.param = this.loaitb_id
      let response = await api.fn_tt_loaihinh_tb(this.axios, input1)
      if (response.data.error_code === 'BSS-00000000' && response.data.data) {
        vdvvt_id = response.data.data
      }
      let indexes = this.$refs.grvTruyNhap.getSelectedRowIndexes()
      let item = this.grcTruyNhap.list[indexes[0]]

      if (vdvvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || vdvvt_id == DichVuVienThong.DICHVU_CNTT ||
        vdvvt_id == DichVuVienThong.TRUNGTAM_DULIEU || vdvvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
        this.dataResponse.ma_tn = item.ma_tb
        this.dataResponse.ma_doicap = item.madoicap
        this.dataResponse.loaitb_id = item.loaitb_id
        this.dataResponse.thuebao_id = item.thuebao_id

        if (item.trangbitb_id != 1) {
          this.$toast.error('Mã thuê bao ' + item.ma_tb + ' đang ở trạng thái ' +
            item.trangthai_tb)
        }
        this.$emit('accept', this.dataResponse)
      } else {
        let indexes = this.$refs.grvTruyNhap.getSelectedRowIndexes()
        let item = this.grcTruyNhap.list[indexes[0]]
        let check = 0 // tcbancheo.KIEMTRA_MATB_TRUYNHAP(tinh, Convert.ToInt64(grvTruyNhap.GetFocusedRowCellValue("MADOICAP")), loaitb_id, KIEULAP.LAP_LINE_SAN)

        if (check == 0 || this.kieuld_id == this.KieuLapDat.TACH_VLAN) {
          this.dataResponse.ma_tn = item.ma_tb
          this.dataResponse.ma_doicap = item.madoicap
          this.dataResponse.loaitb_id = item.loaitb_id
          this.dataResponse.thuebao_id = item.thuebao_id

          if (item.trangbitb_id != 1) {
            this.$toast.error('Mã thuê bao ' + item.ma_tb + ' đang ở trạng thái ' +
              item.trangthai_tb)
          }
          this.$emit('accept', this.dataResponse)
        } else {
          if (this.kieuld_id != this.KieuLapDat.TACH_VLAN) {
            let indexes = this.$refs.grvTruyNhap.getSelectedRowIndexes()
            let item = this.grcTruyNhap.list[indexes[0]]

            let thongbao = ''
            thongbao += 'Mã thuê bao ' + item.ma_tb +
              ' đang có cùng đôi cáp với thuê bao khác. Bạn có muốn chọn không?'

            await this.$confirm(thongbao, {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning'
            }).then(async () => {
              this.dataResponse.ma_tn = item.ma_tb
              this.dataResponse.ma_doicap = item.madoicap
              this.dataResponse.loaitb_id = item.loaitb_id
              this.dataResponse.thuebao_id = item.thuebao_id
              this.$emit('accept', this.dataResponse)
            }).catch(async () => {

            })
          }
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
