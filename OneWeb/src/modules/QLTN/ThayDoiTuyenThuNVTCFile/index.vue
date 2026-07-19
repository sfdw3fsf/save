<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from 'moment'
import xlsx from 'xlsx'
import API from '../api/ThayDoiTuyenThuNVTCFile'

export default {
  components: {},
  data () {
    return {
      current_tab: 1,
      checkTucThi: 1,
      errorPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      errorResults: [],
      duongDan: '',
      source: [],
      kieuFile: 0,
      kyCuoc:
        moment(new Date())
          .subtract(1, 'months')
          .endOf('month')
          .format('YYYYMM') + '01',
      fields: [
        {
          fieldName: 'STT',
          headerText: 'STT',
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: 'maTT',
          headerText: 'Mã thanh toán',
          allowFiltering: true
        //   width: 350,
        //   isSum: true,
        //   sumTotal: true,
        },
        {
          fieldName: 'maNVTC',
          headerText: 'Mã NVTC (Excel)',
          allowFiltering: true
        },
        {
          fieldName: 'maNVTC_HIENTAI',
          headerText: 'Mã NVTC (Hiện tại)',
          allowFiltering: true
        },
        {
          fieldName: 'maTuyen_HIENTAI',
          headerText: 'Tuyến thu (hiện tại)',
          allowFiltering: true
        }
      ]
    }
  },
  validations: {},
  watch: {},
  created () {},
  mounted () {
    this.$refs.duongDan.focus()
  },
  methods: {
    async GhiLai () {
      if (this.source.length <= 0) {
        this.$root.toastError('Chưa có danh sách cập nhật!')
        return
      }
      const data = {
        // dsTuyenThu: [
        //   {
        //     maTT: "HPG-30-114333",
        //     maNVTC: "H_KTHU_10",
        //     maTuyen: "H_KTHU_10",
        //   },
        // ],
        dsTuyenThu: this.source,
        kieuDS: this.kieuFile,
        maVNP: 0,
        kyCuoc: this.kyCuoc, // 20210101
        tucThi: this.checkTucThi
      }
      this.loading(true)
      try {
        const response = await API.capNhatDanhSach(this.axios, data)
        if (response.data.error_code === 'BSS-00000000') {
          if (response.data.data !== null && response.data.data.length > 0) {
            this.$toast.error('Đã phát sinh lỗi')
            this.current_tab = 2
            this.errorResults = response.data.data
          } else this.$root.toastSuccess('Cập nhật dữ liệu thành công!')
        }
      } catch (error) {
        // if (error.data.error_code === "BSS-00009490") {
        //   this.$root.toastError("Mã thanh toán bị trùng")
        // } else if (error.data.error_code === "BSS-00009491") {
        //   this.$root.toastError("Cập nhập dữ liệu không thành công!")
        // }
        this.$root.toastError(error.data.message_detail)
      } finally {
        this.loading(false)
      }
    },
    async onChangeFile (e) {
      this.errorResults = []
      this.current_tab = 1
      const files = e.target.files
      this.duongDan = e.target.files[0].name
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError('File excel không đúng định dạng!')
      }
      const fileReader = new FileReader()
      let $this = this
      fileReader.onload = async (ev) => {
        try {
          this.loading(true)
          this.source = []
          this.kieuFile = 0
          const data = ev.target.result
          const XLSX = xlsx
          const workbook = XLSX.read(data, {
            type: 'binary'
          })
          const wsname = workbook.SheetNames[0]
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
          const excellist = []
          let dsDanhSach = []

          if (ws.length <= 0) {
            $this.$toast.error('Không tìm thấy dữ liệu trong file, đơn vị vui lòng kiểm tra lại.')
            $this.loading(false)
            return
          }

          if (ws.length > 3000) {
            $this.$toast.error('File dữ liệu vượt quá 3000 bản ghi, đơn vị vui lòng điều chỉnh lại.')
            $this.loading(false)
            return
          }
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i])
          }
          // let propCount = 0;
          // if (excellist && excellist.length > 0) {
          //   for (let prop in excellist[0]) {
          //     if (excellist[0].hasOwnProperty(prop)) propCount++;
          //   }
          // }
          const arrayCol = Object.keys(excellist[0])
          if (
            arrayCol[0] === 'MA_TT' &&
            arrayCol[1] === 'MA_NVTC'
          ) {
            this.kieuFile = 1
          }
          if (
            arrayCol[0] === 'MA_TT' &&
            arrayCol[1] === 'MA_TUYEN'
          ) {
            this.kieuFile = 2
          }
          if (this.kieuFile == 0) {
            this.$root.toastError(
              'Tên cột trong file không đúng định dạng: MA_TT, MA_NVTC hoặc MA_TT, MA_TUYEN!'
            )
            this.loading(false)
            return
          }
          if (this.kieuFile == 1) {
            this.fields[2] = {
              fieldName: 'maNVTC',
              headerText: 'Mã NVTC (Excel)',
              allowFiltering: true
            }
            const dataPost = {
              kieu: 1,
              ds: ws
            }
            const response = await API.getThongTinMaTT(this.axios, dataPost)
            if (response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
              dsDanhSach = response.data.data.map(item => ({ maTT: item.MA_TT, maNVTC: item.MA_NVTC, maNVTC_HIENTAI: item.MA_NVTC_HIENTAI, maTuyen_HIENTAI: item.MA_TUYEN_HIENTAI }))
            }
            // for (let i = 0; i < ws.length; i++) {
            //   let result = {};
            //   const params = {
            //     pKyCuoc: this.kyCuoc,
            //     pMaTT: ws[i].MA_TT,
            //   };
            //   const response = await API.getThongTinDaiLy(this.axios, params);
            //   if (
            //     response.data.error_code === "BSS-00000000" &&
            //     response.data.data.length > 0
            //   ) {
            //     result = response.data.data[0];
            //   }
            //   dsDanhSach.push({
            //     maTT: ws[i].MA_TT,
            //     maNVTC: ws[i].MA_NVTC,
            //     maNVTC_HIENTAI: result.MANV_TC,
            //     maTuyen_HIENTAI: result.MA_TUYENTHU,
            //   });
            // }
          } else {
            this.fields[2] = {
              fieldName: 'maTuyen',
              headerText: 'Mã tuyến (Excel)',
              allowFiltering: true
            }
            const dataPost = {
              kieu: 2,
              ds: ws
            }
            const response = await API.getThongTinMaTT(this.axios, dataPost)
            if (response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
              dsDanhSach = response.data.data.map(item => ({ maTT: item.MA_TT, maTuyen: item.MA_TUYEN, maNVTC_HIENTAI: item.MA_NVTC_HIENTAI, maTuyen_HIENTAI: item.MA_TUYEN_HIENTAI }))
            }
            // for (let i = 0; i < ws.length; i++) {
            //   let result = {};
            //   const params = {
            //     pKyCuoc: this.kyCuoc,
            //     pMaTT: ws[i].MA_TT,
            //   };
            //   const response = await API.getThongTinDaiLy(this.axios, params);
            //   if (
            //     response.data.error_code === "BSS-00000000" &&
            //     response.data.data.length > 0
            //   ) {
            //     result = response.data.data[0];
            //   }
            //   dsDanhSach.push({
            //     maTT: ws[i].MA_TT,
            //     maTuyen: ws[i].MA_TUYEN,
            //     maNVTC_HIENTAI: result.MANV_TC,
            //     maTuyen_HIENTAI: result.MA_TUYENTHU,
            //   });
            // }
          }
          this.source = dsDanhSach.map((item, index) => ({
            ...item,
            STT: ++index
          }))
          this.loading(false)
        } catch (error) {
          this.loading(false)
          return this.$root.toastError('Không tìm thấy dữ liệu từ file Excel!')
        }
      }
      fileReader.readAsBinaryString(files[0])
    },
    TaiFileMau () {
      this.loading(true)
      try {
        const temp = [{ MA_TT: '', MA_NVTC: '' }]
        var dsach = xlsx.utils.json_to_sheet(temp)
        var wb = xlsx.utils.book_new() // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, 'DS') // sheetAName is name of Worksheet
        // export Excel file
        xlsx.writeFile(wb, 'Bieumau_thaydoituyenthu.xlsx')
      } catch (error) {
      } finally {
        this.loading(false)
      }
    }
  },
  computed: {}
}
</script>
