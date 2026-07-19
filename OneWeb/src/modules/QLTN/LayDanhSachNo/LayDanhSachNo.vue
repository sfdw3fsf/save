
<template src='./LayDanhSachNo.html'></template>
<style src='./LayDanhSachNo.scss' scoped></style>
<script>
import xlsx from 'xlsx'
import { mapActions, mapState } from 'vuex'
import KyCuoc from '../components/KyCuoc.vue'
import moment from 'moment'
import ModalXuatDuLieu from '../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue'

export default {
  components: { appKyCuoc: KyCuoc, appModalXuatDuLieu: ModalXuatDuLieu },
  name: 'LayDanhSachNo',
  mounted () {},
  computed: {
    ...mapState('laydanhsachno', ['danhsachThueBao']),
    dsThuocTinh () {
      return this.fields.map((x) => x.fieldName)
    }
  },
  data () {
    return {
      pKyCuoc: '',
      typeFile: '1',
      keyDSTB: 0,
      tuKy: '',
      f_refresh: false,
      fields: [
        { fieldName: 'MANV_TC', headerText: 'Mã NVTC', allowFiltering: true },
        { fieldName: 'MA_KH_NEO', headerText: 'Mã KH Neo', allowFiltering: true },
        { fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true },
        { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true },
        { fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true },
        { fieldName: 'DIACHI_TT', headerText: 'Địa chỉ TT', allowFiltering: true }
      ],
      fieldsBK: [
        { fieldName: 'MANV_TC', headerText: 'Mã NVTC', allowFiltering: true },
        { fieldName: 'MA_KH_NEO', headerText: 'Mã KH Neo', allowFiltering: true },
        { fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true },
        { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true },
        { fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true },
        { fieldName: 'DIACHI_TT', headerText: 'Địa chỉ TT', allowFiltering: true }
      ],
      fieldsRender: [],
      pDanhSach: [],
      pThang: '',
      kyHoaDon: ''
    }
  },
  methods: {
    ...mapActions('laydanhsachno', ['getDanhSachThueBao', 'clearData']),
    handleKyHoaDon (e) {
      this.pKyCuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      this.kyHoaDon = moment(e.p_kyhoadon).format('YYYYMM')
    },
    handleTuKy (e) {
      this.tuKy = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      this.pThang = moment(e.p_kyhoadon).format('YYYYMM')
    },
    async importExcel (e) {
      const files = e.target.files
      this.clearData()
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError('File excel không đúng định dạng!')
      }
      this.readFile1(files)
    },
    readFile1 (files) {
      const fileReader = new FileReader()
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result
          const XLSX = xlsx
          const workbook = XLSX.read(data, {
            type: 'binary'
          })
          const wsname = workbook.SheetNames[0]
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
          const excellist = []
          let dsDanhSach = []
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i])
            if (this.typeFile === '1') {
              dsDanhSach.push({
                ma_tt: ws[i].MA_TT,
                ma_tb: ws[i].MA_TB
              })
            } else if (this.typeFile === '2') {
              dsDanhSach.push({
                ma_tt: ws[i].MA_TT,
                ma_tb: null
              })
            } else {
              // dsDanhSach.push(ws[i].MA_KH_NEO)
              // dsDanhSach.push(ws[i].MA_TB)
              dsDanhSach.push({
                ma_tt: ws[i].MA_KH_NEO,
                ma_tb: ws[i].MA_TB
              })
            }
          }

          var propCount = 0
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++
            }
          }
          if (this.typeFile === '2') {
            if (propCount !== 1) {
              this.$root.toastError(
                'Số lượng cột phải = 1 cột, hiện đang có ' + propCount + ' cột!'
              )
              return
            }
          } else {
            if (propCount !== 2) {
              this.$root.toastError(
                'Số lượng cột phải = 2 cột, hiện đang có ' + propCount + ' cột!'
              )
              return
            }
          }

          if (this.typeFile === '1') {
            if (
              !excellist[0].hasOwnProperty('MA_TT') ||
              !excellist[0].hasOwnProperty('MA_TB')
            ) {
              this.$root.toastError('File cần có chính xác 2 cột MA_TT, MA_TB!')
              return
            }
          } else if (this.typeFile === '2') {
            if (!excellist[0].hasOwnProperty('MA_TT')) {
              this.$root.toastError('File cần có chính xác 1 cột MA_TT!')
              return
            }
          } else {
            if (
              !excellist[0].hasOwnProperty('MA_TB') ||
              !excellist[0].hasOwnProperty('MA_KH_NEO')
            ) {
              this.$root.toastError(
                'File cần có chính xác 2 cột MA_KH_NEO, MA_TB!'
              )
              return
            }
          }

          this.pDanhSach = dsDanhSach
          this.fieldsRender = this.fields
          this.$root.toastSuccess('File hợp lệ!')
          this.LayTT()
          // console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
        } catch (e) {
          return alert('Read failure!' + e)
        }
      }
      fileReader.readAsBinaryString(files[0])
    },
    async LayTT () {
      if (this.tuKy === '') {
        this.$root.toastError('Chưa có thông tin về chu kỳ tìm kiếm!')
        return
      }
      if (this.pDanhSach.length <= 0) {
        this.$root.toastError('Hãy chọn đường dẫn đọc file!')
        return
      }
      this.loading(true)
      const data = {
        pOpt: this.typeFile,
        pDS: this.pDanhSach,
        pTuKyCuoc: this.tuKy
      }
      this.updateColumns()
      const response = await this.getDanhSachThueBao(data)
      this.f_refresh = true
      if (response.data.error_code === 'BSS-00000000') {
        this.keyDSTB++
        this.loading(false)
        if (response.data.data !== undefined && response.data.data.length > 0) {
          // this.$refs.danhSach.setCurrentSelectedRow(0)
        } else {
          this.$root.toastError('Không tìm thấy dữ liệu thỏa điều kiện!')
        }
      } else {
        this.loading(false)
        this.$root.toastError(response.message_detail)
      }
      return response
    },
    ThemCot () {
      // const fieldsAdd = [
      //   { fieldName: 'DICHVUVT_ID', headerText: 'DICHVUVT_ID', allowFiltering: true, cellCssClass: 'text-right' },
      //   { fieldName: 'TENNV_QL', headerText: 'TENNV_QL', allowFiltering: true },
      //   { fieldName: 'TO_QL', headerText: 'TO_QL', allowFiltering: true },
      //   { fieldName: 'PHONG_BH', headerText: 'PHONG_BH', allowFiltering: true },
      //   { fieldName: 'TEN_LOAIKH', headerText: 'TEN_LOAIKH', allowFiltering: true },
      //   { fieldName: 'NV_NGHIEPVU', headerText: 'NV_NGHIEPVU', allowFiltering: true },
      //   { fieldName: 'TRUNGTAM_VT', headerText: 'TRUNGTAM_VT', allowFiltering: true },
      //   { fieldName: 'TEN_NVVT', headerText: 'TEN_NVVT', allowFiltering: true },
      //   { fieldName: 'TO_QLDB', headerText: 'TO_QLDB', allowFiltering: true },
      //   { fieldName: 'NV_QLDB', headerText: 'NV_QLDB', allowFiltering: true },
      //   { fieldName: 'DICHVUVT', headerText: 'DICHVUVT', allowFiltering: true },
      //   { fieldName: 'TRANGTHAI_TB', headerText: 'TRANGTHAI_TB', allowFiltering: true },
      //   { fieldName: 'NGAY_CAT', headerText: 'NGAY_CAT', allowFiltering: true, cssClass: 'text-right' },
      //   { fieldName: 'NGAY_TD', headerText: 'NGAY_TD', allowFiltering: true, cssClass: 'text-right' },
      //   { fieldName: 'CHIEUKHOA', headerText: 'CHIEUKHOA', allowFiltering: true },
      //   { fieldName: 'DIEMTINNHIEM', headerText: 'DIEMTINNHIEM', allowFiltering: true },
      //   { fieldName: 'NO_HIENTAI', headerText: 'NO_HIENTAI', allowFiltering: true, type: 'number', format: 'N0' },
      //   { fieldName: `NO_${this.pThang}`, headerText: `NO_${this.pThang}`, allowFiltering: true, type: 'number', format: 'N0' },
      //   { fieldName: `NOTRUOC_${this.pThang}`, headerText: `NOTRUOC_${this.pThang}`, allowFiltering: true, type: 'number', format: 'N0' },
      //   { fieldName: `NOPS_${this.kyHoaDon}`, headerText: `NOPS_${this.kyHoaDon}`, allowFiltering: true, type: 'number', format: 'N0' },

      // ]
      // const fieldsAdd2 = [
      //   {
      //     fieldName: 'DICHVUVT_ID',
      //     headerText: 'DICHVUVT_ID',
      //     allowFiltering: true,
      //     cellCssClass: 'text-right'
      //   },
      //   {
      //     fieldName: `NOTRUOC_${this.pThang}`,
      //     headerText: `NOTRUOC_${this.pThang}`,
      //     allowFiltering: true,
      //     type: 'number',
      //     format: 'N0'
      //   },
      //   {
      //     fieldName: `NO_${this.pThang}`,
      //     headerText: `NO_${this.pThang}`,
      //     allowFiltering: true,
      //     type: 'number',
      //     format: 'N0'
      //   },
      //   {
      //     fieldName: `NOPS_${this.kyHoaDon}`,
      //     headerText: `NOPS_${this.kyHoaDon}`,
      //     allowFiltering: true,
      //     type: 'number',
      //     format: 'N0'
      //   },
      //   {
      //     fieldName: 'NO_HIENTAI',
      //     headerText: 'NO_HIENTAI',
      //     allowFiltering: true,
      //     type: 'number',
      //     format: 'N0'
      //   },
      //   {
      //     fieldName: 'CHIEUKHOA',
      //     headerText: 'CHIEUKHOA',
      //     allowFiltering: true
      //   },
      //   {
      //     fieldName: 'DICHVUVT',
      //     headerText: 'DICHVUVT',
      //     allowFiltering: true
      //   },
      //   {
      //     fieldName: 'TRANGTHAI_TB',
      //     headerText: 'TRANGTHAI_TB',
      //     allowFiltering: true
      //   },
      //   {
      //     fieldName: 'NGAY_CAT',
      //     headerText: 'NGAY_CAT',
      //     allowFiltering: true
      //     // cellCssClass: "text-right",
      //   },
      //   {
      //     fieldName: 'NGAY_TD',
      //     headerText: 'NGAY_TD',
      //     allowFiltering: true
      //     // cellCssClass: "text-right",
      //   }
      // ]
      // if (this.typeFile === '2') {
      //   this.fieldsRender = [...this.fields, ...fieldsAdd2]
      // } else {
      //   this.fieldsRender = [...this.fields, ...fieldsAdd]
      // }
      // let columns = []
      this.updateColumns()
    },
    XuatFile () {
      // this.$bvModal.show('modal-xuat-dulieu')
      if (this.danhsachThueBao.length <= 0) {
        this.$toast.error('Chưa có dữ liệu để xuất excel!')
        return
      }

      if (this.typeFile === '1') {
        let dataGridObj = this.$refs.danhSach1.$refs.grid.ej2Instances
        let columns = dataGridObj.columnModel.slice(1)

        let excelExportProperties = {
          columns: columns,
          fileName: 'DSThueBao.xlsx',
          dataSource: this.danhsachThueBao
        }
        this.$refs.danhSach1.excelExport(excelExportProperties)
      } else if (this.typeFile === '2') {
        let dataGridObj = this.$refs.danhSach2.$refs.grid.ej2Instances
        let columns = dataGridObj.columnModel.slice(1)

        let excelExportProperties = {
          columns: columns,
          fileName: 'DSThueBao.xlsx',
          dataSource: this.danhsachThueBao
        }
        this.$refs.danhSach2.excelExport(excelExportProperties)
      } else if (this.typeFile === '3') {
        let dataGridObj = this.$refs.danhSach3.$refs.grid.ej2Instances
        let columns = dataGridObj.columnModel.slice(1)
        
        let excelExportProperties = {
          columns: columns,
          fileName: 'DSThueBao.xlsx',
          dataSource: this.danhsachThueBao
        }
        this.$refs.danhSach3.excelExport(excelExportProperties)
      }
    },
    XuatFile2 () {
      if (this.danhsachThueBao.length <= 0) {
        this.$root.toastError('Chưa có danh sách để xuất Excel!')
        return
      }
      this.loading(true)
      try {
        var dsach = xlsx.utils.json_to_sheet(this.danhsachThueBao)
        var wb = xlsx.utils.book_new() // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, 'DS') // sheetAName is name of Worksheet
        xlsx.writeFile(wb, 'mau.xlsx')
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    databound () {
      // console.log('databound')
      // if (this.f_refresh) this.updateColumns()
    },
    updateColumns () {
      const fieldsAdd = [
        { fieldName: 'DICHVUVT_ID', headerText: 'DICHVUVT_ID', allowFiltering: true, cellCssClass: 'text-right' },
        { fieldName: 'TENNV_QL', headerText: 'TENNV_QL', allowFiltering: true },
        { fieldName: 'TO_QL', headerText: 'TO_QL', allowFiltering: true },
        { fieldName: 'PHONG_BH', headerText: 'PHONG_BH', allowFiltering: true },
        { fieldName: 'TEN_LOAIKH', headerText: 'TEN_LOAIKH', allowFiltering: true },
        { fieldName: 'NV_NGHIEPVU', headerText: 'NV_NGHIEPVU', allowFiltering: true },
        { fieldName: 'TRUNGTAM_VT', headerText: 'TRUNGTAM_VT', allowFiltering: true },
        { fieldName: 'TEN_NVVT', headerText: 'TEN_NVVT', allowFiltering: true },
        { fieldName: 'TO_QLDB', headerText: 'TO_QLDB', allowFiltering: true },
        { fieldName: 'NV_QLDB', headerText: 'NV_QLDB', allowFiltering: true },
        { fieldName: 'DICHVUVT', headerText: 'DICHVUVT', allowFiltering: true },
        { fieldName: 'TRANGTHAI_TB', headerText: 'TRANGTHAI_TB', allowFiltering: true },
        { fieldName: 'NGAY_CAT', headerText: 'NGAY_CAT', allowFiltering: true, cssClass: 'text-right' },
        { fieldName: 'NGAY_TD', headerText: 'NGAY_TD', allowFiltering: true, cssClass: 'text-right' },
        { fieldName: 'CHIEUKHOA', headerText: 'CHIEUKHOA', allowFiltering: true },
        { fieldName: 'DIEMTINNHIEM', headerText: 'DIEMTINNHIEM', allowFiltering: true },
        { fieldName: 'NO_HIENTAI', headerText: 'NO_HIENTAI', allowFiltering: true, type: 'number', format: 'N0' }
      ]
      const fieldsAdd2 = [
        {
          fieldName: 'DICHVUVT_ID',
          headerText: 'DICHVUVT_ID',
          allowFiltering: true,
          cellCssClass: 'text-right'
        },
        {
          fieldName: 'CHIEUKHOA',
          headerText: 'CHIEUKHOA',
          allowFiltering: true
        },
        {
          fieldName: 'DICHVUVT',
          headerText: 'DICHVUVT',
          allowFiltering: true
        },
        {
          fieldName: 'TRANGTHAI_TB',
          headerText: 'TRANGTHAI_TB',
          allowFiltering: true
        },
        {
          fieldName: 'NGAY_CAT',
          headerText: 'NGAY_CAT',
          allowFiltering: true
        },
        {
          fieldName: 'NGAY_TD',
          headerText: 'NGAY_TD',
          allowFiltering: true
        },
        { fieldName: 'NO_HIENTAI', headerText: 'NO_HIENTAI', allowFiltering: true, type: 'number', format: 'N0' }
      ]
      let startDate = moment(this.pThang, 'YYYYMM')
      var endDate = moment(this.pKyCuoc, 'YYYYMMDD')
      var betweenMonths = []

      if (startDate < endDate) {
        var date = startDate.startOf('month')

        while (date < endDate.endOf('month')) {
          betweenMonths.push(date.format('YYYYMM'))
          date.add(1, 'months')
        }
      }

      let obj = this.$refs.danhSach1.$refs.grid.ej2Instances
      // let columns = obj.columnModel
      // console.log(columns)
      // let thang = moment(this.pThang, 'YYYYMM').format('MM/YYYY')
      // console.log(columns, this.config[this.kieuId])
      let columns = []

      let fields = []
      if (this.typeFile === '1' || this.typeFile === '3') {
        fields = [...this.fieldsBK, ...fieldsAdd]
        // console.log(betweenMonths)
        if (betweenMonths.length >= 2) {
          for (let i = betweenMonths.length - 1; i >= 0; i--) {
            let month = moment(betweenMonths[i], 'YYYYMM').format('MM-YYYY')
            if (i === betweenMonths.length - 1) {
              columns.push({
                fieldName: 'NOPS_' + betweenMonths[betweenMonths.length - 1],
                headerText: 'Nợ PS tháng ' + month,
                allowFiltering: true,
                type: 'number',
                format: 'N0'
              })
            } else {
              columns.push(
                {
                  fieldName: 'NO_' + betweenMonths[i],
                  headerText: 'Tháng ' + month,
                  allowFiltering: true,
                  type: 'number',
                  format: 'N0'
                }
              )
            }
          }
          columns.push(
            {
              fieldName: 'NOTRUOC_' + betweenMonths[0],
              headerText: 'Nợ trước tháng ' + moment(betweenMonths[0], 'YYYYMM').format('MM-YYYY'),
              allowFiltering: true,
              type: 'number',
              format: 'N0'
            }
          )
          columns.push({ fieldName: 'TEN_PLKH', headerText: 'TEN_PLKH', allowFiltering: true })
          fields = [...fields, ...columns]
        } else {
          columns.push({
            fieldName: 'NOPS_' + startDate.format('YYYYMM'),
            headerText: 'Nợ PS tháng ' + startDate.format('MM-YYYY'),
            allowFiltering: true,
            type: 'number',
            format: 'N0'
          })
          columns.push(
            {
              fieldName: 'NOTRUOC_' + startDate.format('YYYYMM'),
              headerText: 'Nợ trước tháng ' + startDate.format('MM-YYYY'),
              allowFiltering: true,
              type: 'number',
              format: 'N0'
            }
          )
          columns.push({ fieldName: 'TEN_PLKH', headerText: 'TEN_PLKH', allowFiltering: true })
          fields = [...fields, ...columns]
        }
        this.fields = [...fields]

        obj.refreshColumns()
        this.f_refresh = false
      } else {
        fields = [...this.fieldsBK, ...fieldsAdd2]
        if (betweenMonths.length > 1) {
          for (let i = betweenMonths.length - 1; i >= 0; i--) {
            let month = moment(betweenMonths[i], 'YYYYMM').format('MM-YYYY')
            if (i === betweenMonths.length - 1) {
              columns.push({
                fieldName: 'NOPS_' + betweenMonths[betweenMonths.length - 1],
                headerText: 'Nợ PS tháng ' + month,
                allowFiltering: true,
                type: 'number',
                format: 'N0'
              })
            } else {
              columns.push(
                {
                  fieldName: 'NO_' + betweenMonths[i],
                  headerText: 'Tháng ' + month,
                  allowFiltering: true,
                  type: 'number',
                  format: 'N0'
                }
              )
            }
          }
          columns.push(
            {
              fieldName: 'NOTRUOC_' + betweenMonths[0],
              headerText: 'Nợ trước tháng ' + moment(betweenMonths[0], 'YYYYMM').format('MM-YYYY'),
              allowFiltering: true,
              type: 'number',
              format: 'N0'
            }
          )
          fields = [...fields, ...columns]
        } else {
          columns.push({
            fieldName: 'NOPS_' + startDate.format('YYYYMM'),
            headerText: 'Nợ PS tháng ' + startDate.format('MM-YYYY'),
            allowFiltering: true,
            type: 'number',
            format: 'N0'
          })
          columns.push(
            {
              fieldName: 'NOTRUOC_' + startDate.format('YYYYMM'),
              headerText: 'Nợ trước tháng ' + startDate.format('MM-YYYY'),
              allowFiltering: true,
              type: 'number',
              format: 'N0'
            }
          )
          fields = [...fields, ...columns]
        }
        this.fields = [...fields]

        obj.refreshColumns()
        this.f_refresh = false
      }
    }
  },
  created () {
    this.fieldsRender = this.fields
    this.clearData()
  }
}
</script>
