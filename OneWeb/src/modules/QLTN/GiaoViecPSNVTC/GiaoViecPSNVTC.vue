<template src="./GiaoViecPSNVTC.html"></template>

<script>
import Vue from "vue"
import GiaoViecAPI from '../api/GiaoViecPSNVTC'
import XLSX from 'xlsx'
import moment from 'moment'

import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { CheckBoxPlugin } from "@syncfusion/ej2-vue-buttons"


Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)

export default{
  components : {
  },
  created () {
    this.getDSTacNghiep()
  },
  mounted () {
  },
  destroyed () {
  },
  provide: {
    multiselect: [CheckBoxSelection]
  },
  computed : {
    p_thang_th : {
      get() {
        return this.params.p_thang_th
      },
      set(value) {
        return this.params.p_thang_th = value
      }
    },
  },
  data() {
    return {
      configs : {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        tacnghiep: {
          fields : { text: 'LOAI_NC', value: 'LOAINC_ID'},
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh',
          allowFiltering: true
        },
        fieldsTacNghiep: {text: 'LOAI_NC', value: 'LOAINC_ID' },
      },
      params: {
        p_thang_th: new Date(),
        p_tacnghiep_options: [],
        p_tacnghiep: [],
        p_file_path: null
      },
      dsUploaded: [],
      dsErrors: [],
      keyUploadResults: 0,
      keyErrorResults: 0,
      currentTab: 0,
      uploadPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      errorPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
    }
  },
  methods : {
    clearResult()
    {
      this.dsUploaded = []
      this.dsErrors = []
    },
    // click button Cập nhật
    doUpdate()
    {

      if(this.dsUploaded.length <= 0)
      {
        this.$toast.error('Danh sách trống, không có dữ liệu để cập nhật!')
        return false
      }

      if(this.params.p_tacnghiep.length <= 0)
      {
        this.$toast.error('Hãy chọn tác nghiệp!')
        return false
      }

      let postData = {
        "dsLoaiNCId": this.params.p_tacnghiep,
        "ds": []
      }

      this.dsUploaded.forEach((item) => {
        let data = {
          "phuong_ID": item.PHUONG_ID,
          "pho_ID": item.PHO_ID,
          "ma_GD": item.MA_GD,
          "diachi_KH": item.DIACHI_KH,
          "ten_KH": item.TEN_KH,
          "ma_KH": item.MA_KH,
          "dienthoai_LH": item.DIENTHOAI_LH,
          "ma_NVTC": item.MA_NVTC,
          "quan_ID": item.QUAN_ID,
          "dotgiao_ID": item.DOTGIAO_ID,
          "khachhang_ID": null,
          "thang_TH": moment(this.params.p_thang_th).format('YYYYMM'),
          "dacdiem_ID": item.DACDIEM_ID,
          "sonha": item.SO_NHA,
          "thutu_IN": item.THUTU_IN,
          "hdkh_ID": null,
          "sdkh_ID": item.SDKH_ID
        }
        postData.ds.push(data)
      })


      this.loading(true)

      GiaoViecAPI.capnhatGiaoPhieu(this.axios, postData)
        .then((res) => {
          this.$toast.success('Cập nhật kết quả thành công!')
          this.clearResult()
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {this.loading(false)})

    },
    // click button Xuất Excel
    doExportExcel()
    {
      let excelExportProperties = {
        dataSource: []
      };

      if(this.currentTab === 0)
      {
        if(this.dsUploaded.length <= 0)
        {
          this.$toast.error('Không có dữ liệu để xuất Excel.')
          return
        }
        excelExportProperties.dataSource = this.dsUploaded
        this.$refs.dsUploaded.excelExport(excelExportProperties)
      } else
      {
        if(this.dsErrors.length <= 0)
        {
          this.$toast.error('Không có dữ liệu để xuất Excel.')
          return
        }
        excelExportProperties.dataSource = this.dsErrors
        this.$refs.dsErrors.excelExport(excelExportProperties)
      }


    },

    // click button Đường dẫn chọn file
    browserFile()
    {
      document.getElementById("giaoviec_theofile").click()
      return false
    },

    checkDataColumns(data, requiredColumns)
    {
      let error = []
      if(data.length <= 0)
        return false
      if(requiredColumns.length <= 0)
        return false

      requiredColumns.forEach((item, index) => {
        if(!data[0].includes(item))
          error.push(item)
      })
      console.log(error)
      return error
    },

    checkDataUpload(data, requiredColumns)
    {
      if(data.length <= 0)
        return false
      let ok = []
      let error = []
      data.forEach(item => {
        let messages = []
        let r = item
        requiredColumns.forEach(c => {
          if(item[c] === undefined || item[c] === null || (item[c] !== null && ('' + item[c]).trim() === ''))
          {
            messages.push(c + " không được để trống!")
          }
        })

        if(messages.length > 0)
        {
          r.TRANGTHAI = messages.join(',')
          error.push(r)
        } else
          ok.push(item)
      })

      this.dsUploaded = [...ok]
      this.dsErrors = [...error]
    },

    // handle đọc file
    doUploadFile(e)
    {
      var files = e.target.files, f = files[0];

      // clear grid trước khi xử lý
      this.clearResult()

      if (!files.length) {
          return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return  this.$toast.error("File excel không đúng định dạng!");
      }
      this.params.p_file_path = f.name
      var reader = new FileReader();
      const vm = this
      let uploadErrors = []
      let uploadOK = []

      reader.onload = function(e) {
        var data = new Uint8Array(e.target.result);
        var workbook = XLSX.read(data, {type: 'array'});
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        //console.log(workbook);
        let worksheet = workbook.Sheets[sheetName];
        let exceldata = XLSX.utils.sheet_to_json(worksheet)
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        let dataColumns = ['MA_GD', 'MA_KH',	'TEN_KH',	'DIACHI_KH',	'DIENTHOAI_LH',	'MA_NVTC',	'QUAN_ID',	'PHUONG_ID',	'PHO_ID',	'DACDIEM_ID',	'SO_NHA',	'SDKH_ID', 'DOTGIAO_ID', 'THUTU_IN', 'NGAY_CN']
        let requiredColumns = ['TEN_KH',	'DIACHI_KH',	'DIENTHOAI_LH',	'MA_NVTC',	'QUAN_ID',	'PHUONG_ID',	'PHO_ID',	'DACDIEM_ID',	'DOTGIAO_ID', 'THUTU_IN']
        console.log(exceldata)

        if(exceldata.length <= 0)
        {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('giaoviec_theofile').value = null;
          return false
        }
        let ret = vm.checkDataColumns(exceldata1, dataColumns)
        if(ret === false)
        {
          vm.$toast.error('Dữ liệu không đúng định dạng')
          document.getElementById('giaoviec_theofile').value = null
        } else if(ret.length > 0)
        {
          vm.$toast.error('File gồm có các cột: ' + ret.join(','))
          document.getElementById('giaoviec_theofile').value = null
          return false
        }

        vm.checkDataUpload(exceldata, requiredColumns)

        //vm.keyUploadResults++

        //vm.dsGachNoDanhSach = [...exceldata]
        //vm.dsGachNoDanhSachTotals = vm.dsGachNoDanhSach.length



        if(vm.dsErrors.length > 0)
        {
          vm.thong_bao = "Danh sách thuê bao không thỏa mãn"
        }
        document.getElementById('giaoviec_theofile').value = null;
      };
      reader.readAsArrayBuffer(f);
    },

    trimDataUpload()
    {
    },

    getDSTacNghiep: function(){
      GiaoViecAPI.getDSTacNghiep(this.axios)
      .then(
        (res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.params.p_tacnghiep_options = res.data.data
          }
        }
      )
      .catch((error) => {
        this.$toast.error(error.data.message_detail)
      })
    },
    /* Handlers*/

    onFilteringTacNghiep(e){
      var query = new Query();
      let keyword = e.text.trim()
      query = (e.text !== '') ? query.where('LOAI_NC', 'contains', keyword, true) : query;
      e.updateData(this.params.p_tacnghiep_options, query);
    },
  }

}

</script>
