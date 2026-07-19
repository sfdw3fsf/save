<template src="./UpdateHouseholdInformation.html"></template>
<script>
import { mapActions } from 'vuex'
import XLSX from 'xlsx'
import moment from 'moment'
import LayDuLieu from './LayDuLieu.vue'

export default {
  name: 'UpdateHouseholdInformation',
  components: {
    LayDuLieu
  },
  created: async function() {
    this.nguoiDungID = this.$root.token.getNguoiDungID()
  },
  data: () => ({
    nguoiDungID: 0,
    tabActive: 1,
    ghi_chu: '',
    DSThongTinHoDan: {
      headers: [
        {
          fieldName: 'MA_HODAN',
          headerText: 'Mã Hộ Dân',
          allowFiltering: true
        },
        {
          fieldName: 'THANHVIEN_ID',
          headerText: 'ID Của Thành Viên',
          allowFiltering: true
        },
        {
          fieldName: 'TEN_QUAN',
          headerText: 'Tên Quận',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_PHUONG',
          headerText: 'Tên Phường',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_PHO',
          headerText: 'Tên Phố',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_AP',
          headerText: 'Tên Ấp',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_KHU',
          headerText: 'Tên Khu',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SONHA',
          headerText: 'Số Nhà',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'DIACHI',
          headerText: 'Địa Chỉ',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_TV',
          headerText: 'Tên Thành Viên',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUHO',
          headerText: 'Chủ Hộ',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'NGAYSINH',
          headerText: 'Ngày Sinh',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'GIOITINH',
          headerText: 'Giới Tính',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'NGHENGHIEP',
          headerText: 'Nghề Nghiệp',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'EMAIL',
          headerText: 'Email',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SO_DD',
          headerText: 'Số Điện Thoại Chính',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_DD',
          headerText: 'Loại Hình Thuê Bao Của Số Di Động',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SO_DT_KHAC',
          headerText: 'Số Điện Thoại Khác',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_DD_KHAC',
          headerText: 'Loại Hình Thuê Bao Của Số Di Động Khác',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_DD_KHAC',
          headerText: 'Loại Hình Thuê Bao Của Số Di Động Khác',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MUCCUOC_ID',
          headerText: 'ID Của Mức Cước Sử Dụng',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUQUAN_INT_ID',
          headerText: 'Chủ Quản Cung Cấp Internet',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_INT',
          headerText: 'Loại Hình Thuê Bao Internet',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'THOIHAN_CK_INT',
          headerText: 'Thời Hạn Cam Kết Internet',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MUCCUOC_INT_ID',
          headerText: 'ID Của Mức Cước Sử Dụng',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUQUAN_TV_ID',
          headerText: 'Chủ Quản Cung Cấp Dịch Vụ Truyền hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SOLUONG_TV',
          headerText: 'Số Lượng TV Của Hộ Gia Đình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'THOIHAN_CK_TV',
          headerText: 'Thời Hạn Cam Kết Dịch Vụ Truyền Hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MUCCUOC_TV_ID',
          headerText: 'ID Của Mức Cước Sử Dụng',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SOMAY_CD',
          headerText: 'Số Máy Cố Định Nếu Có',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'THOIHAN_CK_TV',
          headerText: 'Thời Hạn Cam Kết Dịch Vụ Truyền Hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUQUAN_CD_ID',
          headerText: 'Chủ Quản Cung Cấp Dịch Vụ Truyền Hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'DICHVUVT_KHAC_ID',
          headerText: 'ID Dịch Vụ Viễn Thông Khác Nếu Có',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_KHAC_ID',
          headerText: 'Loại Hình Thuê Bao Khác Nếu Có',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SO_DD_PT',
          headerText: 'Số Máy Di Động Phát Triển Được',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'DICHVUVT_PT_ID',
          headerText: 'ID Của Dịch Vụ Viễn Thông Phát Triển Được',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_PT_ID',
          headerText: 'Loại Hình Thuê Bao Phát Triển Được',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MA_CTV',
          headerText: 'MA_CTV',
          allowFiltering: true,
          allowHtml: true
        }
      ],
      data: []
    },
    DSThongTinHoDanBiLoi: {
      headers: [
        {
          fieldName: 'LY_DO_LOI',
          headerText: 'Lý do lỗi',
          allowFiltering: true
        },
        {
          fieldName: 'MA_HODAN',
          headerText: 'Mã Hộ Dân',
          allowFiltering: true
        },
        {
          fieldName: 'THANHVIEN_ID',
          headerText: 'ID Của Thành Viên',
          allowFiltering: true
        },
        {
          fieldName: 'TEN_QUAN',
          headerText: 'Tên Quận',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_PHUONG',
          headerText: 'Tên Phường',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_PHO',
          headerText: 'Tên Phố',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_AP',
          headerText: 'Tên Ấp',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_KHU',
          headerText: 'Tên Khu',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SONHA',
          headerText: 'Số Nhà',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'DIACHI',
          headerText: 'Địa Chỉ',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'TEN_TV',
          headerText: 'Tên Thành Viên',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUHO',
          headerText: 'Chủ Hộ',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'NGAYSINH',
          headerText: 'Ngày Sinh',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'GIOITINH',
          headerText: 'Giới Tính',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'NGHENGHIEP',
          headerText: 'Nghề Nghiệp',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'EMAIL',
          headerText: 'Email',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SO_DD',
          headerText: 'Số Điện Thoại Chính',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_DD',
          headerText: 'Loại Hình Thuê Bao Của Số Di Động',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SO_DT_KHAC',
          headerText: 'Số Điện Thoại Khác',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_DD_KHAC',
          headerText: 'Loại Hình Thuê Bao Của Số Di Động Khác',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_DD_KHAC',
          headerText: 'Loại Hình Thuê Bao Của Số Di Động Khác',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MUCCUOC_ID',
          headerText: 'ID Của Mức Cước Sử Dụng',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUQUAN_INT_ID',
          headerText: 'Chủ Quản Cung Cấp Internet',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_ID_INT',
          headerText: 'Loại Hình Thuê Bao Internet',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'THOIHAN_CK_INT',
          headerText: 'Thời Hạn Cam Kết Internet',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MUCCUOC_INT_ID',
          headerText: 'ID Của Mức Cước Sử Dụng',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUQUAN_TV_ID',
          headerText: 'Chủ Quản Cung Cấp Dịch Vụ Truyền hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SOLUONG_TV',
          headerText: 'Số Lượng TV Của Hộ Gia Đình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'THOIHAN_CK_TV',
          headerText: 'Thời Hạn Cam Kết Dịch Vụ Truyền Hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MUCCUOC_TV_ID',
          headerText: 'ID Của Mức Cước Sử Dụng',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SOMAY_CD',
          headerText: 'Số Máy Cố Định Nếu Có',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'THOIHAN_CK_TV',
          headerText: 'Thời Hạn Cam Kết Dịch Vụ Truyền Hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'CHUQUAN_CD_ID',
          headerText: 'Chủ Quản Cung Cấp Dịch Vụ Truyền Hình',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'DICHVUVT_KHAC_ID',
          headerText: 'ID Dịch Vụ Viễn Thông Khác Nếu Có',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_KHAC_ID',
          headerText: 'Loại Hình Thuê Bao Khác Nếu Có',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'SO_DD_PT',
          headerText: 'Số Máy Di Động Phát Triển Được',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'DICHVUVT_PT_ID',
          headerText: 'ID Của Dịch Vụ Viễn Thông Phát Triển Được',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'LOAITB_PT_ID',
          headerText: 'Loại Hình Thuê Bao Phát Triển Được',
          allowFiltering: true,
          allowHtml: true
        },
        {
          fieldName: 'MA_CTV',
          headerText: 'MA_CTV',
          allowFiltering: true,
          allowHtml: true
        }
    ],
      data: []
    },
    name_file: '',
    /* nhập excel */
    listImportExcel: [],
    // listExcelImport: [],
    /* data final to import */
    /* data error to import */
    isCheckData: true,
    showDialog: false,
    momentFormat: {
      stringify: (date) => {
        return date ? moment(date).format('MM/YYYY') : ''
      },
      parse: (value) => {
        return value ? moment(value, 'MM/YYYY').toDate() : null
      }
    }
  }),
  methods: {
    ...mapActions('UpdateHouseholdInformation', ['updateThongTinHoDan', 'kiemtra_capnhat_hodan']),
    ShowAlertSuccess(mesage) {
      this.$toast.success(mesage)
    },
    ShowAlertError(mesage) {
      this.$toast.error(mesage)
    },

    /* 2. check xem đủ tên cột hay chưa */
    checkColumnExcel(dataNameTable) {
      if (dataNameTable) {
        const check =
          dataNameTable.includes('MA_HODAN') &&
          dataNameTable.includes('THANHVIEN_ID') &&
          dataNameTable.includes('TEN_QUAN') &&
          dataNameTable.includes('TEN_PHUONG') &&
          dataNameTable.includes('TEN_PHO') &&
          dataNameTable.includes('TEN_AP') &&
          dataNameTable.includes('TEN_KHU') &&
          dataNameTable.includes('SONHA') &&
          dataNameTable.includes('DIACHI') &&
          dataNameTable.includes('TEN_TV') &&
          dataNameTable.includes('CHUHO') &&
          dataNameTable.includes('NGAYSINH') &&
          dataNameTable.includes('GIOITINH') &&
          dataNameTable.includes('NGHENGHIEP') &&
          dataNameTable.includes('EMAIL') &&
          dataNameTable.includes('SO_DD') &&
          dataNameTable.includes('LOAITB_ID_DD') &&
          dataNameTable.includes('MUCCUOC_ID') &&
          dataNameTable.includes('CHUQUAN_INT_ID') &&
          dataNameTable.includes('LOAITB_ID_INT') &&
          dataNameTable.includes('THOIHAN_CK_INT') &&
          dataNameTable.includes('MUCCUOC_INT_ID') &&
          dataNameTable.includes('CHUQUAN_TV_ID') &&
          dataNameTable.includes('SOLUONG_TV') &&
          dataNameTable.includes('THOIHAN_CK_TV') &&
          dataNameTable.includes('MUCCUOC_TV_ID') &&
          dataNameTable.includes('SOMAY_CD') &&
          dataNameTable.includes('CHUQUAN_CD_ID') &&
          dataNameTable.includes('DICHVUVT_KHAC_ID') &&
          dataNameTable.includes('LOAITB_KHAC_ID') &&
          dataNameTable.includes('SO_DD_PT') &&
          dataNameTable.includes('DICHVUVT_PT_ID') &&
          dataNameTable.includes('LOAITB_PT_ID') &&
          dataNameTable.includes('MA_CTV')
        if (check) {
          return true
        } else {
          return false
        }
      } else {
        return false
      }
    },
    convertKeysToLowerCase(obj) {
      var output = {}
      for (var i in obj) {
        if (Object.prototype.toString.apply(obj[i]) === '[object Object]') {
          output[i.toLowerCase()] = this.convertKeysToLowerCase(obj[i])
        } else if (Object.prototype.toString.apply(obj[i]) === '[object Array]') {
          output[i.toLowerCase()] = []
          output[i.toLowerCase()].push(this.convertKeysToLowerCase(obj[i][0]))
        } else {
          output[i.toLowerCase()] = obj[i]
        }
      }
      return output
    },
    /* 3. gắn key vào value map cho đúng thành array */
    async handleImportExcel(data) {
      for (let index = 0; index < data.length - 1; index++) {
        let row = {}
        let keys = data[0]
        let values = data[index + 1]
        for (let i = 0; i < keys.length; i++) {
          if (values[i]) {
            row[keys[i]] = values[i]
          } else {
            row[keys[i]] = ''
          }
        }
        this.listImportExcel.push(row)
      }
      for (let index = 0; index < this.listImportExcel.length; index++) {
        let element = this.listImportExcel[index]
        const checkdata = this.checkDataImportExcelForm(element)
        if (this.isCheckData && !!checkdata) {
          this.DSThongTinHoDan.data.push(element)
        } else {
          this.DSThongTinHoDanBiLoi.data.push({ LY_DO_LOI: checkdata, ...element })
        }
      }
      console.log('this.DSThongTinHoDan.data')
      console.log(this.DSThongTinHoDan.data)
      console.log(this.convertKeysToLowerCase(this.DSThongTinHoDan.data))
      console.log('this.DSThongTinHoDanBiLoi.data')
      console.log(this.DSThongTinHoDanBiLoi.data)
      console.log(this.convertKeysToLowerCase(this.DSThongTinHoDanBiLoi.data))

      // const arrayDanhsachHopLeToString = JSON.stringify(this.convertKeysToLowerCase(this.DSThongTinHoDan.data)) // convert array to string
      // const arrayDanhsachLoiToString = JSON.stringify(this.convertKeysToLowerCase(this.DSThongTinHoDanBiLoi.data)) // convert array to string

      const arrayDanhsachHopLeToString = JSON.stringify(this.DSThongTinHoDan.data)
      // convert array to string
      const arrayDanhsachLoiToString = JSON.stringify(this.DSThongTinHoDanBiLoi.data)

      const jsonHople = JSON.parse(arrayDanhsachHopLeToString)
      console.log('jsonHople1')
      console.log(jsonHople)
      for (var i = 0; i < jsonHople.length; i++) {
        jsonHople[i].ma_hodan = this.DSThongTinHoDan.data[i].MA_HODAN
      }
      console.log('jsonHople')
      console.log(jsonHople)
      const stringHople = JSON.stringify(jsonHople)
      console.log('stringHople')
      console.log(stringHople)

      this.kiemtra_capnhat_hodan({ danhsach_HopLe: arrayDanhsachHopLeToString, danhsach_Loi: arrayDanhsachLoiToString })
    },
    /* 4. check data ExcelForm */
    checkDataImportExcelForm(listImportExcel) {
      // Kiểm tra dữ liệu trống
      if (listImportExcel.MA_HODAN === '') {
        this.isCheckData = false
        return 'Mã hộ dân (Ma_hodan) không được để trống!'
      }
      if (listImportExcel.THANHVIEN_ID === '') {
        console.log('listImportExcel.THANHVIEN_ID')
        console.log(listImportExcel.THANHVIEN_ID)
        this.isCheckData = false
        return 'THANHVIEN_ID (THANHVIEN_ID) không được để trống!'
      }
      // End Kiểm tra dữ liệu trống
      if (listImportExcel.NGAYSINH.length < 8 || moment(listImportExcel.NGAYSINH, 'yyyyMMdd').isValid()) {
        this.isCheckData = false
        return 'Ngày sinh không đúng định dạng, định dạng chuẩn là yyyyMMdd'
      }
      if (listImportExcel.THOIHAN_CK_INT.length < 8 || moment(listImportExcel.THOIHAN_CK_INT, 'yyyyMMdd').isValid()) {
        this.isCheckData = false
        return 'Thời hạn cam kết internet không đúng định dạng, định dạng chuẩn là yyyyMMdd'
      }
      if (listImportExcel.THOIHAN_CK_TV.length < 8 || moment(listImportExcel.THOIHAN_CK_TV, 'yyyyMMdd').isValid()) {
        this.isCheckData = false
        return 'Thời hạn cam kết TV không đúng định dạng, định dạng chuẩn là yyyyMMdd'
      }
      this.isCheckData = true
      return true
    },
    checkDate(ngay) {
      let valueMonth = 0
      let valueYear = 0

      if (ngay && ngay.length) {
        valueMonth = ngay.slice(0, 2)
        valueYear = ngay.slice(3, 7)
      }

      if (valueMonth > 0 && valueYear > 0 && valueMonth.toString().length === 2 && valueYear.toString().length === 4) {
        return true
      }

      return false
    },
    changeFileUpload(event) {
      const file = event.target.files ? event.target.files[0] : null
      this.name_file = file.name
      if (file) {
        const reader = new FileReader()
        reader.onload = (e) => {
          /* Parse data */
          const bstr = e.target.result
          const wb = XLSX.read(bstr, { type: 'binary' })
          /* Get first worksheet */
          const wsname = wb.SheetNames[0]
          const ws = wb.Sheets[wsname]
          /* Convert array of arrays */
          const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
          // check thiếu hay sai tên cột
          if (!this.checkColumnExcel(data[0])) {
            this.$toast.error('File không đúng định dạng, vui lòng tải lại file mẫu!')
          } else {
            this.handleImportExcel(data)
          }
        }
        reader.readAsBinaryString(file)
      }
    },
    handleExportFile() {
      if (this.tabActive == 1) {
        this.$refs.popupLayDuLieuBC.openDialog(this.DSThongTinHoDan.data)
      }
      if (this.tabActive == 2) {
        this.$refs.popupLayDuLieuBC.openDialog(this.DSThongTinHoDanBiLoi.data)
      }
    },
    async handleUpdateData() {
      const params = {
        vnguoidung_id_loi: this.nguoiDungID,
        vghichu_loi: this.ghi_chu
      }
      try {
        await this.updateThongTinHoDan(params)
      } catch (error) {
        this.$toast.error('Update dữ liệu bị lỗi!' + error)
        console.error(error)
      }
    }
  }
}
</script>
