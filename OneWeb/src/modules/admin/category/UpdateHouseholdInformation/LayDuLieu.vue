<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogLayDuLieuBC" :position="position" :header="'Lấy dữ liệu báo cáo'" showCloseIcon="true" width="98%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <download-excel
                    class="button"
                    :data="ds_temp"  key=""
                    type="xls"
                    name="BieuMau_CapNhat_ThonTin_HoDan.xls"
                    :meta = "json_meta"
                  >
                    <span class="icon one-xlsx-import" ></span> Xuất File
                  </download-excel>
              </li>
              <li>
                  <a href="javascript:;" v-on:click="tsbtnXemDL_Click">
                      <span class="icon nc-icon-glyph files_refresh"></span> Xem dữ liệu
                  </a>
              </li>
          </ul>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="box-form">
                <div class="legend-title">Cấu trúc bảng dữ liệu</div>
                <div class="table-content">
                      <DataGrid
                        ref="gv_header"
                        v-bind:columns="DsHeader.headers"
                        v-bind:dataSource="DsHeader.data"
                        :showColumnCheckbox="true"
                        :enable-paging-server="false"
                        :allowPaging="false"
                        :showFilter="false"
                        :enabledSelectFirstRow="false"
                        >
                        <e-column headerText="Commands" width="150" ></e-column>
                      </DataGrid>
                </div>
              </div>
          </div>
          <div class="col-sm-8 col-12">
             <div class="box-form">
                <div class="legend-title">Xem trước dữ liệu
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="state.checkbox_xuatkhongdau" :disabled="false">
                    <span class="name text-main">Xuất tiếng Việt không dấu</span>
                  </div>
                </div>
                <div class="table-content">
                      <DataGrid ref="gv_DsThongTinHoDan"
                        v-bind:columns="DsThongTinHoDan.headers"
                        v-bind:dataSource="DsThongTinHoDan.data"
                        :toolbarOptions="DsThongTinHoDan.toolbarOptions"
                        :showColumnCheckbox="false"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="false">
                      </DataGrid>
                </div>
              </div>
          </div>
        </div>
        </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import XLSX from 'xlsx'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
// import api2 from './PopUpApi'
import downloadexcel from "vue-json-excel"

export const deleteTemplate = function () {
  return {
    template: Vue.component('deleteTemplate', {
      template: '<ejs-button class="btn btn-default" @click="deleteItem">Remove</ejs-button>',
      data: function () {
        return {
          data: {}
        }
      },
      methods: {
        deleteItem () {
          console.log('deleteItem', this.data)
        }
      }
    })
  }
}

export default {
  components: {
    XLSX,
    moment,
    DatePicker,
    // api2,
    downloadexcel
  },
  name: 'LayDuLieuBC',
  data () {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: '10', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      json_meta: [
        [{
          "key": "charset",
          "value": "utf-8"
        }]
      ],
      state: {
        checkbox_xuatkhongdau: false
      },
      controls: {
        btnGiaoPhieuPH: { enabled: false }
      },
      DsThongTinHoDan: {
        headers: [],
        data: [],
        toolbarOptions: ['ExcelExport']
      },
      DsHeader: {
        headers: [],
        data: []
      },
      dsHoDan: [],
      ds_temp: [],
      PhieuId: 0,
      HdTbId: 0,
      ThueBaoId: 0,
      BhId: 0,
      tag_hdbh: -1,
      Tag: ''
    }
  },
  created: async function () {

  },
  mounted: function () {
    this.pageLoad()
  },
  watch: {

  },
  methods: {
    dialogClose () {},
    openDialog (dsHoDan) {
      if (!dsHoDan || dsHoDan.length <= 0) {
        this.$toast.error("Không có dữ liệu để xuất báo cáo")
        return
      }
      this.dsHoDan = dsHoDan
      this.DsThongTinHoDan.data = this.dsHoDan
      this.ds_temp = this.dsHoDan
      var keyNames = Object.keys(this.dsHoDan[0])
      // keyNames.map(name => {
      //   name
      // })
      // console.log("keyNames =====>", keyNames)
      this.ds_temp = []
      var ds_cols = keyNames
      this.dsHoDan.forEach((item) => {
        if (item) {
          var row = {}
          ds_cols.forEach((col) => {
            if (col) {
              row[col.toUpperCase()] = item[col.toLowerCase()]
              row[col] = item[col]
            }
          })
          if (row && Object.keys(row).length > 0) {
            this.ds_temp.push(row)
          }
        }
      })
      this.DsThongTinHoDan.headers = keyNames.map((x) => ({
        fieldName: x,
        // headerText: x.toUpperCase(),
        headerText: this.getHeaderText(x),
        allowFiltering: true,
        allowSorting: true
      }))

      this.DsHeader.headers = [{fieldName: 'column_name', headerText: 'Tên cột', allowFiltering: false, allowSorting: false}]
      this.DsHeader.data = keyNames.map((x) => ({ column_name: this.getHeaderText(x) }))
      this.$refs.dialogLayDuLieuBC.show()
    },
    async pageLoad () {

    },
    getHeaderText (data) {
      if (data === "LY_DO_LOI") {
        return 'Lý Do Lỗi'
      }
      if (data === "MA_HODAN") {
        return 'Mã Hộ Dân'
      }
      if (data === "THANHVIEN_ID") {
        return 'ID Của Thành Viên'
      }
      if (data === "TEN_QUAN") {
        return 'Tên Quận'
      }
      if (data === "TEN_PHUONG") {
        return 'Tên Phường'
      }
      if (data === "TEN_PHO") {
        return 'Tên Phố'
      }
      if (data === "TEN_AP") {
        return 'Tên Ấp'
      }
      if (data === "TEN_KHU") {
        return 'Tên Khu'
      }
      if (data === "SONHA") {
        return 'Số Nhà'
      }
      if (data === "DIACHI") {
        return 'Địa Chỉ'
      }
      if (data === "TEN_TV") {
        return 'Tên Thành Viên'
      }
      if (data === "CHUHO") {
        return 'Chủ Hộ'
      }
      if (data === "NGAYSINH") {
        return 'Ngày Sinh'
      }
      if (data === "GIOITINH") {
        return 'Giới Tính'
      }
      if (data === "NGHENGHIEP") {
        return 'Nghề Nghiệp'
      }
      if (data === "EMAIL") {
        return 'Email'
      }
      if (data === "SO_DD") {
        return 'Số Điện Thoại Chính'
      }
      if (data === "LOAITB_ID_DD") {
        return 'Loại Hình Thuê Bao Của Số Di Động'
      }
      if (data === "SO_DT_KHAC") {
        return 'Số Điện Thoại Khác'
      }
      if (data === "LOAITB_ID_DD_KHAC") {
        return 'Loại Hình Thuê Bao Của Số Di Động'
      }
      if (data === "MUCCUOC_ID") {
        return 'ID Của Mức Cước Sử Dụng'
      }
      if (data === "CHUQUAN_INT_ID") {
        return 'Chủ Quản Cung Cấp Internet'
      }
      if (data === "LOAITB_ID_INT") {
        return 'Loại Hình Thuê Bao Internet'
      }
      if (data === "THOIHAN_CK_INT") {
        return 'Thời Hạn Cam Kết Internet'
      }
      if (data === "MUCCUOC_INT_ID") {
        return 'ID Của Mức Cước Sử Dụng'
      }
      if (data === "CHUQUAN_TV_ID") {
        return 'Chủ Quản Cung Cấp Dịch Vụ Truyền hình'
      }
      if (data === "SOLUONG_TV") {
        return 'Số Lượng TV Của Hộ Gia Đình'
      }
      if (data === "THOIHAN_CK_TV") {
        return 'Thời Hạn Cam Kết Dịch Vụ Truyền Hình'
      }
      if (data === "MUCCUOC_TV_ID") {
        return 'ID Của Mức Cước Sử Dụng'
      }
      if (data === "SOMAY_CD") {
        return 'Số Máy Cố Định Nếu Có'
      }
      if (data === "CHUQUAN_CD_ID") {
        return 'Chủ Quản Cung Cấp Dịch Vụ Truyền Hình'
      }
      if (data === "DICHVUVT_KHAC_ID") {
        return 'ID Dịch Vụ Viễn Thông Khác Nếu Có'
      }
      if (data === "LOAITB_KHAC_ID") {
        return 'Loại Hình Thuê Bao Khác Nếu Có'
      }
      if (data === "SO_DD_PT") {
        return 'Số Máy Di Động Phát Triển Được'
      }
      if (data === "DICHVUVT_PT_ID") {
        return 'ID Của Dịch Vụ Viễn Thông Phát Triển Được'
      }
      if (data === "LOAITB_PT_ID") {
        return 'Loại Hình Thuê Bao Phát Triển Được'
      }
      if (data === "MA_CTV") {
        return 'MA_CTV'
      }
    },
    tsbtnXuatExcel_Click () {
      // console.log('tsbtnXuatExcel_Click')
      // if(this.ds_temp.length <= 0){
      //   this.$toast.error("Không có dữ liệu xuất báo cáo.");
      //   return;
      // }
      // this.$refs.gv_DsThongTinHoDan.excelExport();
    },
    tsbtnXemDL_Click () {
      var grid = this.$refs.gv_header
      var seletedRows = grid.getSelectedRecords()
      var ds_cols = seletedRows.map((x) => x.column_name)
      if (ds_cols.length <= 0) {
        this.$toast.error("Vui lòng chọn cột dữ liệu cần xuất báo cáo.")
        return
      }
      this.ds_temp = []
      this.dsHoDan.forEach((item) => {
        if (item) {
          var row = {}
          ds_cols.forEach((col) => {
            if (col) {
              row[col.toUpperCase()] = item[col.toLowerCase()]
              row[col] = item[col]
              if (this.state.checkbox_xuatkhongdau == true) {
                row[col.toUpperCase()] = this.bodauTiengViet(row[col.toUpperCase()])
              }
            }
          })
          if (row && Object.keys(row).length > 0) { this.ds_temp.push(row) }
        }
      })
      if (this.ds_temp.length <= 0) {
        this.$toast.error("Không có dữ liệu để xuất báo cáo")
        return
      }
      this.DsThongTinHoDan.headers = Object.keys(this.ds_temp[0]).map((x) => ({
        fieldName: x,
        headerText: x.toUpperCase(),
        allowFiltering: true,
        allowSorting: true
      }))
      this.DsThongTinHoDan.data = this.ds_temp
      this.DsThongTinHoDan.headers = Object.keys(this.ds_temp[0]).map((x) => {
        console.log("x == >", x)
      })
    },
    bodauTiengViet (str) {
      if (typeof (str) === 'string') {
        str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a")
        str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e")
        str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i")
        str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o")
        str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u")
        str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y")
        str = str.replace(/đ/g, "d")
        str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A")
        str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E")
        str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I")
        str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O")
        str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U")
        str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y")
        str = str.replace(/Đ/g, "D")
        // Some system encode vietnamese combining accent as individual utf-8 characters
        // Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
        str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, "") // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
        str = str.replace(/\u02C6|\u0306|\u031B/g, "") // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
        // Remove extra spaces
        // Bỏ các khoảng trắng liền nhau
        str = str.replace(/ + /g, " ")
        str = str.trim()
        // Remove punctuations
        // Bỏ dấu câu, kí tự đặc biệt
        str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g, " ")
        return str
      } else {
        return str
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
