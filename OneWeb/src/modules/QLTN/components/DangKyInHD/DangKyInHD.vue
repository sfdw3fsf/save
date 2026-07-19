<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDangKyInHD"
        header="Đăng ký in hóa đơn"
        :animationSettings='animationSettings'
        showCloseIcon=true width='50%'
        :target='target'
        :open="dialogOpen"
        :close="dialogClose"
        :isModal="true"
    >
      <div class="">
        <div class="list-actions-top">
            <ul class="list">

                <li>
                    <a href="javascript: void(0)" @click="onSave">
                        <span class="icon one-print"></span> Ghi lại
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="closeDialog">
                        <span class="icon one-export"></span> Thoát
                    </a>
                </li>


            </ul>
        </div>
        <div class="popup-body">
          <div class="info-row">
              <div class="key w80"></div>
              <div class="key w20">

              </div>
              <div class="value">
                  <div class="mart5 marb5">
                      <span class="red fw6">Chọn file dữ liệu Excel</span>
                      <span class="one-alert text-warning f20"></span>
                      <span class="text-main fw6">File excel gồm 4 trường: MA_TT, HTTT_ID, MST, STK</span>
                  </div>
              </div>
          </div>
          <div class="info-row">
              <div class="key w80">Đường dẫn</div>

              <div class="key w20">
                  <span class="text-main f20 one-network"></span>
              </div>

              <div class="value">
                  <div class="value">
                    <div class="input-more-button">
                        <button class="btn" @click="browserFile">
                            <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input type="text" class="form-control " v-model="params.p_file_path">
                        <input id="dangky_theofile" ref="dangky_theofile" @change="uploadFile" type="file" hidden>
                    </div>
                </div>
              </div>
          </div>
          <div class="box-form" v-show="!is_error">
              <div class="legend-title">
                  <div class="pull-left">
                      Danh sách đăng ký in hóa đơn
                  </div>
                  <div class="pull-right red">

                  </div>
                  <div class="clearfix"></div>
              </div>
              <div class="table-content">
                <DataGrid
                  v-bind:columns="[
                    {fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Right', allowFiltering: true},
                    {fieldName: 'HTTT_ID', headerText: 'HTTT_ID', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'MST', headerText: 'Mã số thuế', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'STK', headerText: 'Số tài khoản', textAlign: 'Left', allowFiltering: true}
                    ]"
                  v-bind:dataSource="successResults"
                  :enablePagingServer="false"
                  :showFilter="false"
                  :allowPaging="false"
                  :allowExcelExport="true"
                  ref="dsSuccessResults"
                  >
                </DataGrid>
              </div>
          </div>
          <div class="box-form" v-show="is_error">
              <div class="legend-title">
                  <div class="pull-left">
                      Thông tin lỗi từ file Excel
                  </div>
                  <div class="pull-right red">

                  </div>
                  <div class="clearfix"></div>
              </div>
              <div class="table-content">
                <DataGrid
                  v-bind:columns="[
                    {fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Right', allowFiltering: true},
                    {fieldName: 'HTTT_ID', headerText: 'HTTT_ID', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'MST', headerText: 'Mã số thuế', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'STK', headerText: 'Số tài khoản', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'TRANG_THAI', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true, width: 250},
                    ]"
                  v-bind:dataSource="errorResults"
                  :enablePagingServer="false"
                  :showFilter="false"
                  :allowPaging="false"
                  :allowExcelExport="true"
                  ref="dsErrorResults"
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
import { mapActions, mapState } from "vuex"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import XLSX from 'xlsx'
import moment from 'moment'

import DangKyInHDAPI from '../../api/DangKyInHD.js'

export default {
    props: {
    },
    async created () {
      await this.getKyHoaDonHienTai()
      if (this.kyHoaDonHienTai !== undefined) {
        this.params.p_kyhoadon = this.kyHoaDonHienTai.KYCUOC
      }
    },
    computed: {
      ...mapState("qltnCommon", [
        "kyHoaDonHienTai"
      ]),
    },
    data () {
        return {
          target: '.main-wrapper',
          animationSettings: { effect: 'Zoom' },
          params:{
            p_file_path: null,
            p_kyhoadon: null
          },
          successResults: [],
          errorResults: [],
          is_error: false
        }
    },
    methods: {
      ...mapActions("qltnCommon", [
        "getKyHoaDonHienTai",
      ]),

      dialogOpen() {
      },

      dialogClose() {
      },

      openDialog () {
        this.$refs.dlgDangKyInHD.show();
      },

      closeDialog() {
        this.$refs.dlgDangKyInHD.hide();
      },
      onSave() {
        this.capnhatFileDangKy()


      },

      browserFile()
      {
        document.getElementById("dangky_theofile").click()
        return false
      },

      clearResult()
      {
        this.successResults = []
        this.errorResults = []
      },

      checkDuplicateMaTT(arr)
      {
        let ret = []
        let item = null
        if(arr.length <= 0) return []
        for(let i = 0; i < arr.length-1; i++)
          for(let j = i + 1; j < arr.length; j++)
            if(arr[i].MA_TT === arr[j].MA_TT)
            {
              item = arr[j]
              item.TRANG_THAI = 'Mã Thanh toán bị trùng'
              ret.push(item)
            }
        return ret
      },

      uploadFile(e)
      {
        var files = e.target.files, f = files[0];

        // clear grid trước khi xử lý
        this.clearResult()

        if (!files.length) {
            return;
        } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
            return  this.$root.toastWarning("File excel không đúng định dạng!");
        }
        this.params.p_file_path = f.name
        var reader = new FileReader();
        const vm = this
        reader.onload = function(e) {
        var data = new Uint8Array(e.target.result);
        var workbook = XLSX.read(data, {type: 'array'});
        let sheetName = workbook.SheetNames[0]

        let worksheet = workbook.Sheets[sheetName];
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        let exceldata = XLSX.utils.sheet_to_json(worksheet)


        if(exceldata.length <= 0)
        {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('dangky_theofile').value= null;
          return false
        }

        if(!exceldata1[0].includes('MA_TT')
          || !exceldata1[0].includes('HTTT_ID')
          || !exceldata1[0].includes('MST')
          || !exceldata1[0].includes('STK')
        )
        {
          vm.$toast.error('File gồm có 4 cột: MA_TT, HTTT_ID, MST, STK')
          document.getElementById('dangky_theofile').value= null;
          return false
        }


        let arrOK = []
        let arrErr = []

        exceldata.forEach((item, index) => {
          //console.log(item)
          if(item.MA_TT === undefined || item.MA_TT === null || (item.MA_TT !== null && ('' + item.MA_TT).trim() === ''))
          {
            let r = item
            r.TRANG_THAI = "Mã thanh toán null"
            arrErr.push(r)
          }
          else if(item.HTTT_ID === undefined || item.HTTT_ID === null || ('' + item.HTTT_ID).trim() === '')
          {
            let r = item
            r.TRANG_THAI = "Hình thức thanh toán null"
            arrErr.push(r)
          }
          else
          {
            if(item.HTTT_ID == '3')
            {
              if(
                  item.MST === undefined || item.MST === null || ('' + item.MST).trim() === ''
                  || item.STK === undefined || item.STK === null || ('' + item.STK).trim() === ''
              )
              {
                let r = item
                r.TRANG_THAI = "Mã số thuế và số tài khoản không được để trống"
                arrErr.push(r)
              } else
                arrOK.push(item)
            } else
              arrOK.push(item)
          }

        })  // end of forEach

        if(arrErr.length > 0)
        {
          vm.is_error = true
          vm.errorResults = [...arrErr]
        } else
        {
          let err = vm.checkDuplicateMaTT(arrOK)
          if(err.length > 0)
          {
            vm.is_error = true
            vm.errorResults = [...err]
          } else
          {
            vm.is_error = false
            vm.successResults = [...arrOK]

            vm.kiemtraFileDangKy()
          }

        }

        document.getElementById('dangky_theofile').value = null;
      };
      reader.readAsArrayBuffer(f);
      },

      kiemtraFileDangKy()
      {
        let postData = {
          "kyCuoc": this.params.p_kyhoadon,
          "ds": []
        }

        this.successResults.forEach((item) => {
          postData.ds.push({
            "maTT": ('' + item.MA_TT).trim(),
            "htttId": ('' + item.HTTT_ID).trim(),
            "mst": item.MST === undefined  || item.MST === null ? null : ('' + item.MST).trim(),
            "stk": item.STK === undefined || item.STK === null ? null : ('' + item.STK).trim(),
          })
        })


        this.loading(true)

        DangKyInHDAPI.kiemtraFileDangKy(this.axios, postData)
          .then((res) => {
            if(res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0)
              {
                let arrOK = []
                let arrErr = []

                res.data.data.forEach((item) => {
                  if(item.TRANG_THAI === null)
                    arrOK.push(item)
                  else
                    arrErr.push(item)
                })

                this.successResults = [...arrOK]
                this.errorResults = [...arrErr]

                if(arrErr.length > 0)
                {
                  this.is_error = true
                }
              }
            else
            {

            }
          })
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => { this.loading(false)})
      },

      capnhatFileDangKy()
      {

        if(this.errorResults.length > 0)
        {
          this.$toast.error('Có lỗi từ file excel upload. Vui lòng kiểm tra lại. ')
          return false
        }
        let postData = {
          "kyCuoc": this.params.p_kyhoadon,
          "ds": []
        }

        this.successResults.forEach((item) => {
          postData.ds.push({
            "maTT": ('' + item.MA_TT).trim(),
            "htttId": ('' + item.HTTT_ID).trim(),
            "mst": item.MST === undefined  || item.MST === null ? null : ('' + item.MST).trim(),
            "stk": item.STK === undefined || item.STK === null ? null : ('' + item.STK).trim(),
          })
        })


        this.loading(true)

        DangKyInHDAPI.capnhatFileDangKy(this.axios, postData)
          .then((res) => {
            if(res.data.error_code === 'BSS-00000000')
            {
              this.$toast.success(res.data.data)
              this.closeDialog()
            }

          })
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => { this.loading(false)})
      }
    }
}
</script>
