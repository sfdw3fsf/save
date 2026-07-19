<template>
  <div>
    <!-- <div class="list-actions-top">
      <ul class="list">
        <li v-if="btnXemTbi.visible" :class="{ disabledInput: !btnXemTbi.enabled }" @click="btnXemTbi_ItemClick">
          <a> <span class="icon one-reload1"></span>Xem thiết bị</a>
        </li>
        <li @click="btnFilter_Click">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Lấy Serial </a>
        </li>
      </ul>
    </div> -->
    <div class="popup-body">
      <DataGrid ref="grcDanhSachThietBiIDC" v-bind:columns="grcDanhSachThietBiIDC.cols" v-bind:dataSource="grcDanhSachThietBiIDC.list" :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" />
    </div>
  </div>
</template>
<script>
import api from './api'
export default {
  // emits: ['onBtnLaySerialClicked'],
  props: {
    thuebao_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    tinh_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    hdtb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  data() {
    return {
      localLoading: 0,
      btnXemTbi: { visible: true, enabled: true },
      grcDanhSachThietBiIDC: {
        cols: [
          { fieldName: 'ma_tb_ct', headerText: 'Mã TB (ĐHSX)', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_phu_ct', headerText: 'ID Thiết bị', width: 150, allowFiltering: true, allowSorting: true },
          { fieldName: 'website_ct', headerText: 'Tên thiết bị', width: 250, allowFiltering: true, allowSorting: true },
          { fieldName: 'parent_field', headerText: 'Loại tài sản/thiết bị', width: 250, allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_chuan_ct', headerText: 'Serial number', allowFiltering: true, allowSorting: true },
          { fieldName: 'ip_ct', headerText: 'IP', allowFiltering: true, allowSorting: true },
          { fieldName: 'congsuat_ct', headerText: 'Công suất', allowFiltering: true, allowSorting: true },
          { fieldName: 'khonggian_ct', headerText: 'Không gian', allowFiltering: true, allowSorting: true },
          { fieldName: 'domain_ct', headerText: 'Tầng', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_ld', headerText: 'Nơi đặt (IDC)', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_tb_ct', headerText: 'Địa chỉ RACK', allowFiltering: true, allowSorting: true },
          { fieldName: 'vitri_duoi_ct', headerText: 'U dưới', allowFiltering: true, allowSorting: true },
          { fieldName: 'vitri_tren_ct', headerText: 'U trên', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_tiepnhan_ct', headerText: 'Ngày tiếp nhận (IDC)', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_sudung_ct', headerText: 'Ngày sử dụng (IDC)', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_nghiemthu_ct', headerText: 'Ngày IDC nghiệm thu', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_ct', headerText: 'Trạng thái HĐ', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_gd_ct', headerText: 'Mã GD (ĐHSX)', allowFiltering: true, allowSorting: true },
          { fieldName: 'id_number', headerText: 'Số PYC backend IDC', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: []
      }
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        console.log('result')
        console.log(result.data.data)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async frmDanhSachThietBI_IDC_Load() {
      if (this.thuebao_id != 0) {
        let dt = await this.callApiWrapper(api.sp_bancheo_lay_ds_thietbi_dbtb_idc, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vtinh_id: this.tinh_id,
          vthuebao_id: this.thuebao_id
        })
        this.grcDanhSachThietBiIDC.list = dt
      }
      if (this.hdtb_id != 0) {
        let dt = await this.callApiWrapper(api.lay_ds_thietbi_hdtb_idc, {
          vphanvung_id: this.tinh_id,
          vhdtb_id: this.hdtb_id
        })
        this.grcDanhSachThietBiIDC.list = dt
      }
    }
  },
  async mounted() {
    // await this.frmDanhSachThietBI_IDC_Load()
    console.log('frmDanhSachThietBI_IDC_Load')
    console.log(this.grcDanhSachThietBiIDC.list)
    await this.frmDanhSachThietBI_IDC_Load()
  }
}
</script>
<style scoped></style>
