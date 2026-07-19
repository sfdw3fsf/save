<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgTreoNoTheoFile"
    :header='"Đọc file danh sách các thuê bao treo nợ"'
    showCloseIcon=true
    width='1100'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <input id="treono_tb_theofile" ref="treono_tb_theofile" @change="uploadFile" type="file" hidden>
      <div class="list-actions-top">
          <ul class="list">

              <li>
                <a href="#" v-on:click.prevent="doSearch()"><span class="icon one-search"></span> Danh sách</a>
              </li>
              <li>
                <a href="#" v-on:click.prevent="doAccept()"><span class="icon one-edit"></span> Chấp nhận</a>
              </li>
              <li>
                <a href="#" v-on:click.prevent="doExportExcel()"><span class="icon -ap one-excel"></span> Xuất Excel</a>
              </li>
          </ul>
          <div class="more">
              <a href="#">
                  <span class="icon -ap  icon-dots-three-vertical"></span>
              </a>
          </div>
      </div>
      <div class="popup-body">
          <div class="box-form">
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w80">
                    <span class="name">Dịch vụ</span>
                  </div>
                  <div class="value">
                    <ejs-dropdownlist popupWidth=400 :filtering='onFilteringDichVu' :allowFiltering="true" :dataSource='params.p_dichvu_options' :fields="config.fieldsDichVu" v-model = "params.p_dichvu"></ejs-dropdownlist>
                  </div>
                </div>
              </div>
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w50">
                      <span class="-ap icon-warning3 text-warning f20 mar10"></span>
                  </div>
                  <div class="value text-main fw6">
                      File excel gồm 4 trường: MA_TB, TIENTREO, NOIDUNG, GHICHU
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="info-row">
                  <div class="key w80">Đường dẫn</div>
                  <div class="value">
                      <div class="input-more-button">
                          <button class="btn" @click="browserFile">
                              <span class="-ap icon-more_horiz"></span>
                          </button>
                          <input type="text" class="form-control" v-model="params.p_file_path">
                      </div>
                  </div>
              </div>
            </div>
          </div>
          <div class="box-form">
              <div class="legend-title">
                  <div class="pull-left">
                      {{lblMessage}}
                  </div>
                  <div class="clearfix"></div>
              </div>
              <div class="table-content" style="height:300px">
                <DataGrid
                  v-bind:columns="[
                    {fieldName: 'DICHVUVT_ID', visible: false},
                    {fieldName: 'MA_TB', headerText: 'Mã TB', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'TIENTREO', headerText: 'Tiền treo', textAlign: 'Right', type: 'number', format:'N0', allowFiltering: true},
                    {fieldName: 'NOIDUNG', headerText: 'Nội dung', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true},
                    ]"
                  v-bind:dataSource="results"
                  :enablePagingServer="false"
                  :showFilter="false"
                  :allowPaging="true"
                  :totalRecords="pageinfo.totalElement"
                  :pageSize="pageinfo.maxSize"
                  :pageIndex="pageinfo.page"
                  :allowExcelExport="true"
                  height="100%"
                  ref="results"
                  >
                </DataGrid>
              </div>
          </div>
      </div>
  </div>
  </ejs-dialog>
</template>

<script>
import Vue from "vue"
import { mapActions, mapGetters, mapState } from 'vuex'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import { Query } from '@syncfusion/ej2-data'
import TreoNoAPI from '../../api/TreoNo'
import moment from 'moment'

import XLSX from 'xlsx'

Vue.use(DialogPlugin)

export default Vue.extend({
  props: {
  },
  async created (){
    if (this.danhSachDichVuVienThongGetter === undefined || this.danhSachDichVuVienThongGetter.length === 0) {
      await this.getDanhSachDichVuVienThong();
    }
    this.params.p_dichvu_options = this.danhSachDichVuVienThongGetter

    await this.getKyHoaDonHienTai()
    if (this.kyHoaDonHienTai !== undefined) {
      this.params.p_kyhoadon = this.kyHoaDonHienTai.KYCUOC
    } else
    {
      this.params.p_kyhoadon = moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD')
    }
  },
  computed : {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
    ...mapState("qltnCommon", [
      "kyHoaDonHienTai"
    ]),
  },
  data () {
    return {
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
      },
      params: {
        p_dichvu: null,
        p_dichvu_options: [],
        p_file_path: null,
        p_kyhoadon: null
      },
      f_OK: false,
      lblMessage: '',
      results: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
      uploadResults: [],
      pageinfoUpload: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
      totalItemsUpload: 0,
      uploadErrors: [],
      pageinfoError: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
      totalItemsError: 0,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods : {
    ...mapActions("qltnCommon", [
        "getDanhSachDichVuVienThong",
        "getKyHoaDonHienTai"
      ]),
    openDialog() {
      this.$refs.dlgTreoNoTheoFile.show()
    },
    closeDialog() {
      this.clearForm()
      this.clearResult()
      this.$refs.dlgTreoNoTheoFile.hide()
    },
    onDialogClose() {
      this.clearForm()
      this.clearResult()
    },
    onDialogOpen() {
    },
    clearForm() {
      this.params.p_file_path = null
      this.params.p_dichvu = null
    },
    clearResult() {

      this.results = []

      this.uploadResults = []
      this.pageinfoUpload.totalElement = 0
      this.pageinfoUpload.totalPages = 0
      this.pageinfoUpload.page = 0

      //this.totalItemsUpload = 0
      this.uploadErrors = []
      this.pageinfoError.totalElement = 0
      this.pageinfoError.totalPages = 0
      this.pageinfoError.page = 0
      //this.totalItemsError = 0
    },
    browserFile: function(){

      if(this.params.p_dichvu === null)
      {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return false
      }
      document.getElementById("treono_tb_theofile").click()
      return false
    },
    uploadFile: function(e){
      var files = e.target.files, f = files[0];
      if (!files.length) {
          return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return  this.$root.toastWarning("File excel không đúng định dạng!");
      }
      this.params.p_file_path = f.name
      var reader = new FileReader();
      const vm = this

      vm.clearResult()
      reader.onload = function(e) {
        var data = new Uint8Array(e.target.result);
        var workbook = XLSX.read(data, {type: 'array'});
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        //console.log(workbook);
        let worksheet = workbook.Sheets[sheetName];
        let exceldata = XLSX.utils.sheet_to_json(worksheet)
        console.log(exceldata)

        if(exceldata.length <= 0)
        {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('treono_tb_theofile').value= null;
          return false
        }

        if(!exceldata[0].hasOwnProperty('MA_TB')
          || !exceldata[0].hasOwnProperty('TIENTREO')
          || !exceldata[0].hasOwnProperty('NOIDUNG')
          || !exceldata[0].hasOwnProperty('GHICHU')
        )
        {
          vm.$toast.error('File gồm có 4 cột: MA_TB, TIENTRO, NOIDUNG, GHICHU')
          return false
        }
        exceldata.forEach((item, index) => {
          //console.log(item)
          if(item.MA_TB === undefined || item.MA_TB === null || ('' + item.MA_TB).trim() === '')
          {
            let r = item
            r.TRANGTHAI = "Mã thuê bao null"
            vm.uploadErrors.push(r)
          }
          else if(item.TIENTREO === undefined || item.TIENTREO === null || item.TIENTREO === '')
          {
            let r = item
            r.TRANGTHAI = "Tiền treo không hợp lệ"
            vm.uploadErrors.push(r)
          } else
          {
            try{
              parseInt(item.TIENTREO)
            } catch (e) {
              let r = item
              r.TRANGTHAI = "Tiền treo không hợp lệ"
              vm.uploadErrors.push(r)
            }
            vm.uploadResults.push({
              NGUOIDUNG_ID: vm.$auth.getNguoiDungID(),
              MA_TB: item.MA_TB,
              GHICHU: item.GHICHU,
              NOIDUNG: item.NOIDUNG,
              DICHVUVT_ID: vm.params.p_dichvu,
              TIENTREO: item.TIENTREO
            })
          }




        })
        //vm.totalItemsUpload = vm.uploadResults.length
        //vm.totalItemsError = vm.uploadErrors.length
        //vm.dsGachNoDanhSach = [...exceldata]
        //vm.dsGachNoDanhSachTotals = vm.dsGachNoDanhSach.length
        vm.pageinfoUpload.totalElement = vm.uploadResults.length
        vm.pageinfoError.totalElement = vm.uploadErrors.length

        if(vm.uploadResults.length > 0)
        {
          let dsTreo = []
          vm.uploadResults.forEach((item) => {
            dsTreo.push({
              ma_tb: item.MA_TB,
              dichvuvt_id: item.DICHVUVT_ID,
              tientreo: item.TIENTREO,
              noidung: item.NOIDUNG,
              ghichu: item.GHICHU
            })
          })
          let postData = {
            "pNguoiDungId": vm.$auth.getNguoiDungID(),
            "pDSTreoNo": dsTreo
          }
          vm.loading(true)
          TreoNoAPI.getTreoNoTuFile(vm.axios, postData)
            .then(
              (res) => {
                if(res.data.error_code === 'BSS-00000000'
                  && res.data.data !== undefined
                  && res.data.data.length > 0)
                  {
                    vm.results = res.data.data
                    vm.pageinfo.totalElements = res.data.data.length
                    if(res.data.data[0].TRANGTHAI_ID === 1)
                    {
                      vm.f_OK = false
                      vm.lblMessage = "Danh sách thuê bao lỗi"
                    }
                    else
                    {
                      vm.f_OK = true
                      vm.lblMessage = "Danh sách thuê bao khóa mở"
                    }

                  }
                else
                {

                }
              }
            )
            .finally(() => {vm.loading(false)})
        }
      };
      reader.readAsArrayBuffer(f);
      document.getElementById("treono_tb_theofile").value = "";
    },
    doGachNo(){
    },
    doAccept(){
      if(!this.f_OK)
      {
        this.$toast.error('Danh sách trong file đang lỗi, hãy kiểm tra lại file đầu vào!')
      } else
      {
        let postData = {
          "pKyCuoc": this.params.p_kyhoadon,
          "pNguoiDungId": this.$auth.getNguoiDungID(),
          "pNguoiCN": this.$auth.getUserName(),
          "pMayCN": "-"
        }

        this.loading(true)

        TreoNoAPI.capnhatTreoNoTuFile(this.axios, postData)
          .then( (res) => {
            if(res.data.error_code === 'BSS-00000000')
            {
              this.$emit('accept', true);
              this.$refs.dlgTreoNoTheoFile.hide()
            }

          })
          .catch(error => { this.$toast.error(error.data.message_detail)})
          .finally(() => { this.loading(false)})

      }
    },
    doExportExcel(){
      let excelExportProperties = {
          dataSource: this.results
      };
      this.$refs.results.excelExport(excelExportProperties)
    },
    onFilteringDichVu(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query;
      e.updateData(this.params.p_dichvu_options, query);
    },
  }
})
</script>
