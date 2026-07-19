<template src="./ImportDongTienVnptPay.html"></template>
<style src="./ImportDongTienVnptPay.scss"></style>

<script>
import Vue from "vue"
import XLSX from 'xlsx'
import moment from 'moment'
import ImportVNPTPayAPI from '../api/ImportVNPTPay'

export default{
  created (){

  },
  computed :{

  },
  data ()
  {
    return {
      config: {
        max_date: new Date()
      },
      params: {
        1 : {
          p_thangnam : new Date(),
          p_filepath : null,
        },
        2 : {
          p_thangnam : new Date(),
          p_filepath : null,
        },
        3 : {
          p_thangnam : new Date(),
          p_filepath : null,
        },
        4 : {
          p_thangnam : new Date(),
          p_filepath : null,
        },
        5 : {
          p_thangnam : new Date(),
          p_filepath : null,
        },
      },
      active_page: 1,
      active_tab: 1,

      tabData : {
        1: {
          success: [],
          error: [],
          dsImport: [],
          successPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
          errorPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
        },
        2: {
          success: [],
          error: [],
          dsImport: [],
          successPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
          errorPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
        },
        3: {
          success: [],
          error: [],
          dsImport: [],
          successPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
          errorPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
        },
        4: {
          success: [],
          error: [],
          dsImport: [],
          successPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
          errorPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
        },
        5: {
          success: [],
          error: [],
          dsImport: [],
          successPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
          errorPage: {
            pageIndex: 0,
            pageSize: 10,
            totalElement: 0
          },
        },
      },
    }
  },
  methods: {
    browserFile()
    {
      document.getElementById("file-browser").click()
      return false
    },

    clearResult()
    {
      this.tabData[this.active_page].dsImport = []
      this.tabData[this.active_page].success = []
      this.tabData[this.active_page].error = []
      this.tabData[this.active_page].successPage.totalElement = 0
      this.tabData[this.active_page].successPage.page = 0
    },

    uploadFile(e)
    {
      var files = e.target.files, f = files[0];

      // clear grid trước khi xử lý
      this.clearResult()

      if (!files.length) {
          return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return  this.$toast.error("File excel không đúng định dạng!");
      }
      this.params[this.active_page].p_filepath = f.name
      var reader = new FileReader();
      const vm = this

      reader.onload = function(e) {
        var data = new Uint8Array(e.target.result);
        var workbook = XLSX.read(data, {type: 'array'});
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        //console.log(workbook);
        let worksheet = workbook.Sheets[sheetName];
        let exceldata = XLSX.utils.sheet_to_json(worksheet)
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })

        let dataColumns = {
          1 : ['STT', 'TTKD', 'SDT_VI_NV', 'TEN_NV', 'MA_NV', 'SDT_DANGKY_VI_END_USER', 'THOIGIAN_DANGKY_VI', 'LOAI_CHI_CTKM', 'VINAPHONE_CHITRA', 'MEDIA_CHITRA', 'NGAY_DK_VI', 'LIENKET_NGANHANG', 'GIAODICH_TRONGTHANG', 'DINHDANH_VI', 'DINHDANH_KHACHHANG'],
          2 : ['TT', 'TINHTHANH', 'DICHVU', 'MA_GD', 'SODT_VI', 'HOADON_MATT', 'THOIGIAN_TT', 'Ma_GD_VI', 'SOTIEN_HD', 'SOTIEN_TRUVI'],
          3 : ['STT', 'KENHTHANHTOAN', 'NGUONTIEN_THANHTOAN', 'MA_GD', 'MA_GD_VI', 'DICHVU', 'SOTIEN', 'NGAY_GD', 'TRANGTHAI', 'TINHTHANH', 'MA_THANHTOAN', 'HINHTHUC_GACHNO', 'MA_GD_APP_BANHANG'],
          4 : ['STT', 'MA_GIAODICH', 'THOIGIAN_GIAODICH', 'THOIGIAN_XULY', 'DICHVU', 'SOVI', 'HOTEN', 'USER_VI', 'TTKD', 'SOTIEN', 'SOTHUHUONG', 'MOTA', 'TRANGTHAI'],
          5 : ['STT', 'STB', 'PHONG', 'DONVI_ID', 'LOAIMAY', 'TIEUDUNG', 'TKC_HIENTAI', 'THANG', 'GOI_MUCTIEU', 'NGAY_DUYET'],
        }
        let requiredColumns = {
          1 : ['SDT_VI_NV', 'TEN_NV', 'MA_NV', 'SDT_DANGKY_VI_END_USER', 'THOIGIAN_DANGKY_VI', 'NGAY_DK_VI', 'LIENKET_NGANHANG', 'GIAODICH_TRONGTHANG'],
          2 : ['MA_GD', 'SODT_VI', 'HOADON_MATT', 'THOIGIAN_TT', 'Ma_GD_VI', 'SOTIEN_HD', 'SOTIEN_TRUVI'],
          3 : ['MA_GD', 'MA_GD_VI', 'NGAY_GD', 'TRANGTHAI', 'MA_THANHTOAN', 'MA_GD_APP_BANHANG', 'SOTIEN'],
          4 : ['MA_GIAODICH', 'THOIGIAN_GIAODICH', 'THOIGIAN_XULY', 'DICHVU', 'SOVI', 'HOTEN', 'USER_VI', 'TTKD', 'SOTIEN', 'SOTHUHUONG', 'MOTA', 'TRANGTHAI'],
          5 : ['STB', 'PHONG', 'DONVI_ID', 'THANG', 'NGAY_DUYET'],
        }
        console.log(exceldata)

        if(exceldata.length <= 0)
        {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('file-browser').value = null;
          return false
        }
        //console.log(dataColumns[vm.active_page])
        let ret = vm.checkDataColumns(exceldata1, dataColumns[vm.active_page])
        if(ret === false)
        {
          vm.$toast.error('Dữ liệu không đúng định dạng')
          document.getElementById('file-browser').value = null
        } else if(ret.length > 0)
        {
          vm.$toast.error('File gồm có các cột: ' + dataColumns[vm.active_page].join(','))
          document.getElementById('file-browser').value = null
          return false
        }

        vm.checkDataUpload(exceldata, requiredColumns[vm.active_page])

        //vm.keyUploadResults++

        //vm.dsGachNoDanhSach = [...exceldata]
        //vm.dsGachNoDanhSachTotals = vm.dsGachNoDanhSach.length


        if(vm.tabData[vm.active_page].error.length > 0)
        {
          vm.$toast.error('Có ' + vm.tabData[vm.active_page].error.length + ' bản ghi import lỗi !')
          vm.active_tab = 2
        } else
        {
          if(vm.active_page === 1)
            vm.moiMoVi()
          else if(vm.active_page === 2)
            vm.themThanhToanCuocTraSauVNPTPay()
          else if(vm.active_page === 3)
            vm.themThanhToanCaNhan()
          else if(vm.active_page === 4)
            vm.themDanhSachNapThe()
          else if(vm.active_page === 5)
            vm.themDanhSachMucTieuBanGoi()
        }
        document.getElementById('file-browser').value = null;
      };
      reader.readAsArrayBuffer(f);
    },

    checkDataColumns(data, requiredColumns)
    {
      console.log(requiredColumns)
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
        let r = {...item}
        requiredColumns.forEach(c => {
          if(item[c] === undefined || item[c] === null || (item[c] !== null && ('' + item[c]).trim() === ''))
          {
            messages.push(c + " không được để trống!")
          }

          if(this.active_page === 1)
          {
            
            if(item[c] !== undefined && c === 'NGAY_DK_VI' && !moment(item[c], 'DD/MM/YYYY', true).isValid())
            {
              messages.push(c + " sai định dạng DD/MM/YYYY")
            }
            else if(item[c] !== undefined && c === 'THOIGIAN_DANGKY_VI' && !moment(item[c], 'DD/MM/YYYY HH:mm:ss', true).isValid())
              messages.push(c + " sai định dạng DD/MM/YYYY HH:mm:ss")
            else if(item[c] !== undefined && c === 'THOIGIAN_DANGKY_VI' && moment(item[c], 'DD/MM/YYYY HH:mm:ss').format('YYYYMM') !== moment(this.params[1].p_thangnam).format('YYYYMM'))
              messages.push('THOIGIAN_DANGKY_VI phải thuộc tháng ' + moment(this.params[1].p_thangnam).format('MM/YYYY'))
          } else if(this.active_page === 2)
          {
            if(item[c] !== undefined && c === 'THOIGIAN_TT' && !moment(item[c], 'DD/MM/YYYY', true).isValid())
            {
              messages.push(c + " sai định dạng DD/MM/YYYY")
            } else if(c === 'SOTIEN_HD')
            {
              let t = parseInt(item[c])
              if(isNaN(t))
                messages.push(c + " không đúng định dạng số.")
            } else if(c === 'SOTIEN_TRUVI')
            {
              let t = parseInt(item[c])
              if(isNaN(t))
                messages.push(c + " không đúng định dạng số.")
            }
          } else if(this.active_page === 3)
          {
            console.log(c)
            if(item[c] !== undefined && c === 'TRANGTHAI' && item[c].toLowerCase() !== 'thành công')
              messages.push(c + " Trạng thái không đúng")
            else if(item[c] !== undefined && c === 'NGAY_GD' && !moment(item[c], 'DD/MM/YYYY', true).isValid())
            {
              messages.push(c + " sai định dạng DD/MM/YYYY")
            } else if(c === 'SOTIEN')
            {
              if(item[c] === undefined || isNaN(parseInt(item[c])))
                messages.push(c + " không đúng định dạng số.")
            }
          } else if(this.active_page === 4)
          {
            if(item[c] !== undefined && c === 'THOIGIAN_GIAODICH' && !moment(item[c], 'DD/MM/YYYY', true).isValid())
            {
              messages.push(c + " sai định dạng DD/MM/YYYY")
            } else if(item[c] !== undefined && c === 'THOIGIAN_XULY' && !moment(item[c], 'DD/MM/YYYY', true).isValid())
            {
              messages.push(c + " sai định dạng DD/MM/YYYY")
            } else if(c === 'SOTIEN')
            {
              let t = parseInt(item[c])
              if(isNaN(t))
                messages.push(c + " không đúng định dạng số.")
            }
          } else if(this.active_page === 5)
          {
            if(c === 'STB')
            {
              if(item[c] === undefined || isNaN(item[c]))
                messages.push(c + " không đúng định dạng số.")
            }else if(c === 'TKC_HIENTAI')
            {
              if(item[c] === undefined || isNaN(item[c]))
                messages.push(c + " không đúng định dạng số.")
            }else if(c === 'TIEUDUNG')
            {
              if(item[c] === undefined || isNaN(item[c]))
                messages.push(c + " không đúng định dạng số.")
            }else if(c === 'DONVI_ID')
            {
              if(item[c] === undefined || isNaN(item[c]))
                messages.push(c + " không đúng định dạng số.")
            }else if(item[c] !== undefined && c === 'NGAY_DUYET' && !moment(item[c], 'DD/MM/YYYY', true).isValid())
            {
              messages.push(c + " sai định dạng DD/MM/YYYY")
            }
          }
        })


        

        if(messages.length > 0)
        {
          r.ERROR_MSG = messages.join(',')
          error.push(r)
        } else
          ok.push(item)
      })

      this.tabData[this.active_page].success = [...ok]
      this.tabData[this.active_page].error = [...error]

      this.tabData[this.active_page].successPage.totalElement = ok.length
      this.tabData[this.active_page].errorPage.totalElement = error.length
    },

    moiMoVi()
    {
      let postData = {
        "thangNam": moment(this.params[1].p_thangnam).format('YYYYMM'),
        "ds": []
      }

      this.loading(false)

      this.tabData[this.active_page].success.forEach(item => {
        postData.ds.push(
          {
            "VINAPHONE_CHITRA": item.VINAPHONE_CHITRA,
            "LIENKET_NGANHANG": item.LIENKET_NGANHANG,
            "DINHDANH_KHACHHANG": item.DINHDANH_KHACHHANG,
            "SDT_DANGKY_VI_END_USER": item.DINHDANH_KHACHHANG,
            "THOIGIAN_DANGKY_VI": item.THOIGIAN_DANGKY_VI,
            "GIAODICH_TRONGTHANG": item.GIAODICH_TRONGTHANG,
            "STT": item.STT,
            "TTKD": item.TTKD,
            "SDT_VI_NV": item.SDT_VI_NV,
            "TEN_NV": item.TEN_NV,
            "MA_NV": item.MA_NV,
            "LOAI_CHI_CTKM": item.LOAI_CHI_CTKM,
            "DINHDANH_VI": item.DINHDANH_VI,
            "NGAY_DK_VI": item.NGAY_DK_VI,
            "MEDIA_CHITRA": item.MEDIA_CHITRA
          }
        )
      })

      ImportVNPTPayAPI.moiMoVi(this.axios, postData)
        .then((res) => {
          if(
            res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.tabData[this.active_page].dsImport = [...res.data.data]
            this.$toast.success('Import thành công '  +  res.data.data.length +  ' bản ghi!')
            this.active_tab = 1
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    themThanhToanCuocTraSauVNPTPay()
    {
      this.loadDSThanhToanCuocTraSauVNPTPay()
        .then(res => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined)
          {
            let ds = res.data.data
            let postData = {
              "ds": []
            }
            this.tabData[this.active_page].success.forEach(item => {
              postData.ds.push({
                "SOTIEN_TRUVI": item.SOTIEN_TRUVI,
                "TINHTHANH": item.TINHTHANH,
                "DICHVU": item.DICHVU,
                "HOADON_MATT": item.HOADON_MATT,
                "TT": item.TT,
                "SOTIEN_HD": item.SOTIEN_HD,
                "MA_GD": item.MA_GD,
                "SODT_VI": item.SODT_VI,
                "THOIGIAN_TT": item.THOIGIAN_TT,
                "MA_GD_VI": item.Ma_GD_VI
              })
            })
            ImportVNPTPayAPI.themSoViCaNhan(this.axios, postData)
              .then(response => {
                if(response.data.error_code === 'BSS-00000000'
                  //&& response.data.data !== undefined
                  //&& response.data.data.length > 0
                  )
                {
                  this.tabData[this.active_page].dsImport = [...this.tabData[this.active_page].success, ...ds]
                  if(this.tabData[this.active_page].dsImport.length > 0)
                    this.$toast.success('Import thành công ' + this.tabData[this.active_page].dsImport.length + ' bản ghi!')
                  this.active_tab = 1
                }
              })
          }
        })
    },
    themThanhToanCaNhan()
    {

      let postData = {
        ds: []
      }

      this.loading(false)

      this.tabData[this.active_page].success.forEach(item => {
        postData.ds.push(
          {
            "NGUONTIEN_THANHTOAN": item.NGUONTIEN_THANHTOAN,
            "MA_GD": item.MA_GD,
            "DICHVU": item.DICHVU,
            "HINHTHUC_GACHNO": item.HINHTHUC_GACHNO,
            "MA_GD_VI": item.MA_GD_VI,
            "NGAY_GD": item.NGAY_GD,
            "TINHTHANH": item.TINHTHANH,
            "MA_THANHTOAN": item.MA_THANHTOAN,
            "TRANGTHAI": item.TRANGTHAI,
            "STT": item.STT,
            "KENHTHANHTOAN": item.KENHTHANHTOAN,
            "SOTIEN": item.SOTIEN,
            "MA_GD_APP_BANHANG": item.MA_GD_APP_BANHANG
          }
        )
      })

      ImportVNPTPayAPI.themThanhToanCaNhan(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            //this.$toast.success(res.data.data)
            this.tabData[this.active_page].dsImport = [...this.tabData[this.active_page].success]
            this.$toast.success('Đã import thành công ' + this.tabData[this.active_page].dsImport.length + ' bản ghi!')
            this.active_tab = 1
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    themDanhSachNapThe()
    {

      let postData = {
        thangNam: moment(this.params[this.active_page].p_thangnam).format('YYYYMM'),
        ds: []
      }

      this.loading(false)

      this.tabData[this.active_page].success.forEach(item => {
        postData.ds.push(
          {
            "THOIGIAN_GIAODICH": item.THOIGIAN_GIAODICH,
            "STT": item.STT,
            "USER_VI": item.USER_VI,
            "THOIGIAN_XULY": item.THOIGIAN_XULY,
            "HOTEN": item.HOTEN,
            "SOVI": item.SOVI,
            "TTKD": item.TTKD,
            "MA_GIAODICH": item.MA_GIAODICH,
            "DICHVU": item.DICHVU
          }
        )
      })

      ImportVNPTPayAPI.themDanhSachNapThe(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            //this.$toast.success(res.data.data)
            this.tabData[this.active_page].dsImport = [...this.tabData[this.active_page].success]
            this.$toast.success('Đã import thành công ' + this.tabData[this.active_page].dsImport.length + ' bản ghi!')
            this.active_tab = 1
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    themDanhSachMucTieuBanGoi()
    {

      let postData = {
        thangNam: moment(this.params[this.active_page].p_thangnam).format('YYYYMM'),
        ds: []
      }

      this.loading(false)

      this.tabData[this.active_page].success.forEach(item => {
        postData.ds.push(
          {
            "STT": item.STT,
            "STB": item.STB,
            "PHONG": item.PHONG,
            "DONVI_ID": item.DONVI_ID,
            "LOAIMAY": item.LOAIMAY,
            "TIEUDUNG": item.TIEUDUNG,
            "TKC_HIENTAI": item.TKC_HIENTAI,
            "GOI_MUCTIEU": item.GOI_MUCTIEU,
            "NGAY_DUYET": item.NGAY_DUYET
          }
        )
      })

      ImportVNPTPayAPI.themDanhSachMucTieuBanGoi(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            //this.$toast.success(res.data.data)
            this.tabData[this.active_page].dsImport = [...this.tabData[this.active_page].success]
            this.$toast.success('Đã import thành công ' + this.tabData[this.active_page].dsImport.length + ' bản ghi!')
            this.active_tab = 1
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    async loadDSThanhToanCuocTraSauVNPTPay()
    {
      const res = await ImportVNPTPayAPI.dsThanhToanCuocTraSauVNPTPay(this.axios, moment(this.params[this.active_page].p_thangnam).format('YYYYMM'))
      return res
    },
    removeCssRule: function(file, rule) {
        var s = document.querySelector(`link[href = "${file}"]`).sheet;
        var i = [].slice.call(s.cssRules).findIndex((v) => v.selectorText == rule);
        if (i > -1) s.deleteRule(i)
    },
    addRecord: function() {
        this.$refs.grid.$refs.grid.addRecord();
    }
  }
}
</script>
