<template>
  <div class="modal-content popup-box">
    <!-- <div class="popup-header">
      <div class="title">
        <span class="icon one-notepad"></span> Thông tin cấp vật tư cho thuê bao 
      </div>
      <div class="close -ap icon-close" @click="hideModal"></div>
    </div> -->
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="btnXuatFile"> <span class="icon one-xlsx-import"></span>Xuất Excel </a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin phiếu</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">KQ xử lý</div>
              <div class="value">
                <input type="text" v-model="txtKQTH" class="form-control " />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Lý do TH</div>
              <div class="value">
                <input type="text" v-model="txtLyDo" class="form-control " />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Mục đích cấp</div>
              <div class="value">
                <input type="text" v-model="txtLyDo" class="form-control " />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">Danh sách vật tư</div>
        <DataGrid v-bind:columns="grvheaders" v-bind:dataSource="grvdatalist" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabled-select-first-row="true"></DataGrid>
      </div>
    </div>
  </div>
</template>
<script>
import API from '../API'
import XLSX from 'xlsx'
export default {
  name: 'TraCuuVatTuHDTB',
  props: ['hdtb_id', 'baohong_id'],

  components: {},
  data() {
    return {
      txtKQTH: '',
      txtLyDo: '',
      txtLyDo: '',
      grvdatalist: [],
      grvheaders: [
        { fieldName: 'loai_tbi', headerText: 'Tên vật tư' },
        { fieldName: 'seri', headerText: 'Seri' },
        { fieldName: 'ma_tbi', headerText: 'Mã tbị' },
        { fieldName: 'kieu_tbi', headerText: 'Kiểu thiết bị' },
        { fieldName: 'soluong', headerText: 'Số lượng' },
        { fieldName: 'dongia', headerText: 'Đơn giá' },
        { fieldName: 'lohang', headerText: 'Lô hàng' },
        { fieldName: 'mota', headerText: 'Mô tả' },
        { fieldName: 'trangbi', headerText: 'Trang bị' },
        { fieldName: 'ngay_th', headerText: 'Ngày cấp' },
        { fieldName: 'nguoi_cn', headerText: 'Người CN' },
        { fieldName: 'tt_tbi', headerText: 'Tình trạng' },
        { fieldName: 'mucdich_cap', headerText: 'Lý do cấp' },
        { fieldName: 'ten_dv', headerText: 'Đơn vị' },
        { fieldName: 'ten_kho', headerText: 'Kho cấp' },
        { fieldName: 'so_phieu', headerText: 'Số phiếu' },
        { fieldName: 'trangthai', headerText: 'Trạng thái' }
      ]
    }
  },
  watch: {
    hdtb_id: function() {
      // watch it
      //console.log('Prop hdtb_id changed: ', newVal, ' | was: ', oldVal)
      // if(this.hdtb_id!=0)
      //   console.log('hdtb_id')

      if (this.hdtb_id) {
        var data = { hdtb_id: this.hdtb_id }
        //var data = {"hdtb_id":this.hdtb_id};

        API.sp_lay_kqxl_huonggiao_thuhoi(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.txtKQTH = response.data.data[0].ketqua
            this.txtLyDo = response.data.data[0].lydo
          } else {
            this.txtKQTH = ''
            this.txtLyDo = ''
          }
        })

        //data = {"hdtb_id":2314957};
        API.sp_lay_ds_vattu_hdtb(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.grvdatalist = response.data.data
            this.txtMucDichCap = response.data.data[0].lydoto
          } else {
            this.grvdatalist = []
            this.txtMucDichCap = ''
          }
        })
      }
    },
    baohong_id: function() {
      if (this.baohong_id) {
        data = { baohong_id: this.baohong_id }
        this.txtKQTH = ''
        this.txtLyDo = ''
        this.txtMucDichCap = ''
        API.lay_ds_vattu_baohong(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.grvdatalist = response.data.data
          } else {
            this.grvdatalist = []
          }
        })
      }
    }
  },
  methods: {
    getData: function() {},

    btnXuatFile: function() {
      this.$root.showLoading(true)
      try {
        if (this.grvdatalist) {
          let dsXuatExcel = [...this.grvdatalist]
          dsXuatExcel.forEach((item) => {
            delete item.THUEBAO_ID
          })
          let ds = XLSX.utils.json_to_sheet(dsXuatExcel)
          let wb = XLSX.utils.book_new() // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, ds, 'VATTU_THUEBAO') // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, 'ds_vattu_thuebao.xlsx')
        } else {
          this.$toast.warning('Danh sách không có dữ liệu để xuất!')
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    }
  }
}
</script>