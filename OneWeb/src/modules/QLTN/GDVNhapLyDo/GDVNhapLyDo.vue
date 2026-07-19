<template src="./GDVNhapLyDo.html"></template>

<script>
import Vue from 'vue'
import XLSX from 'xlsx'
import GDVNhapLyDoAPI from '../api/GDVNhapLyDo'

export default Vue.extend({
  created () {
  },
  data () {
    return {
      configs :{

      },
      params: {
        p_file_path: null
      },
      current_tab: 1,
      is_error: false,
      lbl_message: '',
      successResults: [],
      errorResults: [],
      successPageInfo: {
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
    onXacNhan()
    {
      if(this.is_error)
      {
        this.$toast.error('Có thông tin lỗi')
        return
      }
      if(this.successResults.length <= 0)
      {
        this.$toast.error('Danh sách trống, không có dữ liệu để cập nhật!')
        return
      }

      this.capnhatDLNhacNoGDV()
    },
    onExportExcel()
    {
      let excelExportProperties = {
        dataSource:  []
      };
      if(this.current_tab === 1)
      {
        if(this.successResults.length <= 0)
        {
          this.$toast.error('Danh sách tải thành công , không có dữ liệu để xuất file!')
          return
        }
        excelExportProperties.dataSource = this.successResults
        this.$refs.dsSuccessResults.excelExport(excelExportProperties)
      } else
      {
        if(this.errorResults.length <= 0)
        {
          this.$toast.error('Danh sách lỗi , không có dữ liệu để xuất file!')
          return
        }
        excelExportProperties.dataSource = this.errorResults
        this.$refs.dsErrorResults.excelExport(excelExportProperties)
      }

    },
    clearResult()
    {
      this.successResults = []
      this.errorResults = []
    },
    browserFile()
    {
      document.getElementById("nhaplydo_theofile").click()
      return false
    },
    uploadFile(e)
    {
      var files = e.target.files, f = files[0];

      // clear grid trước khi xử lý
      this.clearResult()

      if (!files.length) {
          return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        document.getElementById('nhaplydo_theofile').value= null;
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
          document.getElementById('nhaplydo_theofile').value= null;
          return false
        }

        if(!exceldata1[0].includes('MA_TT')
          || !exceldata1[0].includes('LYDO_ID')
          || !exceldata1[0].includes('GHICHU')
        )
        {
          vm.$toast.error('File gồm có 3 cột: MA_TT, LYDO_ID, GHICHU')
          document.getElementById('nhaplydo_theofile').value= null;
          return false
        }


        let arrOK = []
        let arrErr = []

        exceldata.forEach((item, index) => {
          //console.log(item)
          if(item.MA_TT === undefined || item.MA_TT === null || (item.MA_TT !== null && ('' + item.MA_TT).trim() === ''))
          {
            let r = item
            r.TRANGTHAI = "Mã TT (MA_TT) không được để trống!"
            arrErr.push(r)
          }
          else if(item.LYDO_ID === undefined || item.LYDO_ID === null || ('' + item.LYDO_ID).trim() === '')
          {
            let r = item
            r.TRANGTHAI = "Lý do ID (LYDO_ID) không được để trống!"
            arrErr.push(r)
          } else if(item.GHICHU === undefined || item.GHICHU === null || ('' + item.GHICHU).trim() === '')
          {
            let r = item
            r.TRANGTHAI = "Ghi chú không được để trống!"
            arrErr.push(r)
          }
          else
          {
            arrOK.push(item)
          }

        })  // end of forEach

        if(arrErr.length > 0)
        {
          vm.is_error = true
          vm.errorResults = [...arrErr]
          vm.lbl_message = 'Có thông tin lỗi'
          vm.current_tab = 2

        } else
        {
          let err = vm.checkDuplicateMaTT(arrOK)
          if(err.length > 0)
          {
            vm.is_error = true
            vm.errorResults = [...err]
            vm.lbl_message = 'Có thông tin lỗi'
            vm.current_tab = 2
          } else
          {
            vm.is_error = false
            vm.successResults = [...arrOK]
            vm.current_tab = 1
          }

        }

        document.getElementById('nhaplydo_theofile').value = null;
      };
      reader.readAsArrayBuffer(f);
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
            item.TRANGTHAI = 'Mã Thanh toán bị trùng'
            ret.push(item)
          }
      return ret
    },

    capnhatDLNhacNoGDV()
    {

      let postData = {
        "ds": []
      }

      this.successResults.forEach((item) => {
        postData.ds.push({
          "maTT": ('' + item.MA_TT).trim(),
          "lyDoId": ('' + item.LYDO_ID).trim(),
          "ghiChu": ('' + item.GHICHU).trim()
        })
      })

      this.loading(true)

      GDVNhapLyDoAPI.capnhatDLNhacNoGDV(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Cập nhật kết quả thành công!')
            this.clearResult()
            this.params.p_file_path = null
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    }
  }
})

</script>
