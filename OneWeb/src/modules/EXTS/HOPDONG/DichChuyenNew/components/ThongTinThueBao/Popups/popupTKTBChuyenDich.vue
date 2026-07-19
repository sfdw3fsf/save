<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>CHỌN SỐ MÁY / ACCOUNT THỰC HIỆN DI CHUYỂN</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="chapnhan">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">

                <div class="box-form">
                  <div class="legend-title">Danh sách thuê bao</div>
                  <div class="table-content">
                    <DataGrid ref="grid" v-bind:columns="[
                      {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowSorting: true, width: 120},
                      {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true, width: 130},
                      {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true},
                      {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowSorting: true, width: 130},
                      {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true, width: 160},
                      {fieldName: 'TINHTRANG_TB', headerText: 'Tình Trạng', allowFiltering: true, allowSorting: true, width: 160}
                      ]"
                          v-bind:dataSource="dtList"
                          :showFilter="true"
                          @recordDoubleClick="recordDoubleClick"
                          @selectedRowChanged="grid_selectedRowChanged">
                    </DataGrid>
                  </div>
                </div>
            </div>
</div>
</template>
<style scoped>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
</style>
<script>
import {
  THUESO
} from '../Enums'
export default {
  name: 'popupTKTBChuyenDich',
  props: [ 'dulieu' ],
  watch: {

  },
  data () {
    return {
      dtList: [],
      row: null
    }
  },
  async mounted () {
    this.loading(true)
    var data = await this.LAY_DS_DB_HDTB_THEO_KHID_MAGD(this.dulieu.khachhang_id, this.dulieu.ma_gd)
    data.forEach((element, index) => {
      if (element.THUOC_PL === 1) {
        element.TINHTRANG_TB = 'Đang chuyển dịch'
      }
    })
    this.dtList = data
    this.loading(false)
  },
  computed: {

  },
  methods: {
    recordDoubleClick () {
      if (this.row.THUOC_PL === 1) {
        this.$root.$toast.error(this.row.MA_TB + ' đang thực hiện chuyển dịch, Bạn hãy chọn thuê bao khác !')
      } else {
        this.chapnhan()
      }
    },
    CanhBaoTrangThaiTB (row) {
      if (row.TRANGTHAITB_ID !== 1) {
        this.$root.$toast.warning('Thuê bao này đang ở trạng thái ' + row.TRANGTHAI_TB + '.')
        return false
      }
      return true
    },
    async LAY_DS_DB_HDTB_THEO_KHID_MAGD (khachhang_id, ma_gd) {
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/chuyendich/lay_ds_db_hdtb_theo_khid_magd?khachhang_id=' + khachhang_id + '&ma_gd=' + ma_gd)
        console.log('🚀 ~ file: popupTKTBChuyenDich.vue ~ line 180 ~ LAY_DS_DB_HDTB_THEO_KHID_MAGD ~ rs.data', rs.data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    grid_selectedRowChanged (row) {
      this.row = row
      console.log('🚀 ~ file: popupTKTBChuyenDich.vue ~ line 181 ~ grid_selectedRowChanged ~ this.row', this.row)
    },
    closeForm () {
      this.$emit('form-close', null)
    },
    chapnhan: function () {
      if (this.row) {
        var retData = {MaThueBao: this.row.MA_TB, dichvuvt_id: this.row.DICHVUVT_ID}
        this.$emit('form-close', retData)
        this.$bvModal.hide(this.modalId)
      }
    }
  }
}
</script>
