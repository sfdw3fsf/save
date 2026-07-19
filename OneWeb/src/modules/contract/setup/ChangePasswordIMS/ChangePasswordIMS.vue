<template src="./ChangePasswordIMS.html"></template>
<style src="./ChangePasswordIMS.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './API'
import Vue from 'vue'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import xlsx from "xlsx";
import { JsonAdaptor } from '@syncfusion/ej2-data'
//
export default {
  components: {breadcrumb,VueFlatPickr,API , DatePicker},
  name: 'ChangePasswordIMS',
  mounted () {
    this.Load_DanhSachTinh()
    this.Load_DanhSachThueBao()
  },
  data () {
    return {
      header: {
        title: 'ĐỔI MẬT KHẨU IMS',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      defaultDataExcel : [
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323828628',
          'LINE2' : '842323855156',
          'SERIAL_NUM' : 'VNPT01a1b0ab',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323910460',
          'LINE2' : '842323855123',
          'SERIAL_NUM' : 'VNPT01a14d3f',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323866333',
          'LINE2' : '',
          'SERIAL_NUM' : 'VNPT01a0b2d7',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323820487',
          'LINE2' : '',
          'SERIAL_NUM' : 'VNPT01a1333b',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323882305',
          'LINE2' : '',
          'SERIAL_NUM' : 'VNPT01a125d9',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323837782',
          'LINE2' : '',
          'SERIAL_NUM' : 'VNPT01a1022d',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323770211',
          'LINE2' : '',
          'SERIAL_NUM' : 'VNPT01a100e9',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323953298',
          'LINE2' : '',
          'SERIAL_NUM' : 'VNPT01a100ef',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323835447	',
          'LINE2' : '',
          'SERIAL_NUM' : 'VNPT01a12f39',
          'MA_LHTB' : 'SIP'
        },
        {
          'MATINH' : 'QBH',
          'MA_VUNG' : '232',
          'LINE1' : '842323872233',
          'LINE2' : '842323914338',
          'SERIAL_NUM' : 'VNPT01a0b871',
          'MA_LHTB' : 'SIP'
        },
      ],
      listTable : {
        danh_sach_thue_bao : {
          list : [],
          header: [ 
            {fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'matinh', headerText: 'Mã tỉnh', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'ma_vung', headerText: 'Mã vùng', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'line1', headerText: 'LINE1', allowFiltering: true, width : 'auto'},
            {fieldName: 'pass_line1', headerText: 'PASS LINE1', allowFiltering: true, width : 'auto'},
            {fieldName: 'line2', headerText: 'LINE2', allowFiltering: true, width : 'auto'},
            {fieldName: 'pass_line2', headerText: 'PASS LINE2', allowFiltering: true, width : 'auto'},
            {fieldName: 'serial_num', headerText: 'SERIAL_NUM', allowFiltering: true, width : 'auto'},
            {fieldName: 'ma_lhtb', headerText: 'Loại hình TB', allowFiltering: true, width : 'auto'},
            // {fieldName: 'update_serial', headerText: 'UPDATE_SERIAL', allowFiltering: true, width : 'auto'},
            {fieldName: 'update_ont', headerText: 'UPDATE_ONT', allowFiltering: true, width : 'auto'},
            {fieldName: 'update_core', headerText: 'UPDATE_CORE', allowFiltering: true, width : 'auto'},
            {fieldName: 'update_dhsx', headerText: 'UPDATE_DHSX', allowFiltering: true, width : 'auto'},
            // {fieldName: 'id', headerText: 'ID', allowFiltering: true, width : 'auto'},
            {fieldName: 'response', headerText: 'RESPONSE', allowFiltering: true, width : 'auto'},
            {fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, width : 'auto'},
            {fieldName: 'may_cn', headerText: 'Máy CN', allowFiltering: true, width : 'auto'},
            {fieldName: 'ngay_cn', headerText: 'Ngày import', allowFiltering: true, width : 'auto'},
            {fieldName: 'ngay_th', headerText: 'Ngày thực hiện', allowFiltering: true, width : 'auto'},
          ],
          value: {},
          isEnabled: true,
        },
      },
      settingSelect2: {
        language: 'vi'
      },

      cboDanhSachTinh : {
          list : [],
          value : '',
          disable: false,
      },
      cboKieuDuLieu : {
          list : [
            {
              'id' : 0,
              'text' : 'Tất cả'
            },
            {
              'id' : 1,
              'text' : 'Chưa thực hiện'
            },
            {
              'id' : 2,
              'text' : 'Đã thực hiện'
            }
          ],
          value : 0,
          disable: false,
      },

      danhsach_excel : [],
      danhsachloi_docfile : [],
      danhsach_thoaman : [],
      file : null
    }
  },
  computed: {
  },
  methods : {
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },

    ShowSuccess(mesage) {
      this.$toast.success(mesage);
    },


    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    async Load_DanhSachTinh() {
      let input = {}

      let data = this.GetData(await API.sp_lay_ds_tinh(this.axios, input))

      if (data && data.length > 0) {
        this.cboDanhSachTinh.list = data.map(item => {
          return {
            id : item.tinh_id,
            text : item.tentinh
          }
        })
        this.cboDanhSachTinh.value = this.cboDanhSachTinh.list[0].id
      }

      this.Load_DanhSachThueBao()
    },

    tscboTinh_SelectedIndexChanged() {
      this.Load_DanhSachThueBao()
    },

    btnTimKiem_Click() {
      this.Load_DanhSachThueBao()
    },

    async Load_DanhSachThueBao() {
      let input = {
        'vtype' : this.cboKieuDuLieu.value,
        'vlist' : '',
        'vphanvung_id' : this.cboDanhSachTinh.value
      }

      let data = this.GetData(await API.sp_lay_ds_thuebao_ims_sync(this.axios, input))

      if (data && data.length > 0) {
        this.listTable.danh_sach_thue_bao.list = data.map((x, index)=>{
          x.id='id_'+(index+1)
          x.ischeck='0'
          return x
        })
      } else {
        this.listTable.danh_sach_thue_bao.list = []
      }
    },

    tsbtnExportExcel_Click() {
      let data = xlsx.utils.json_to_sheet(this.defaultDataExcel)
      let wb = xlsx.utils.book_new() // make Workbook of Excel
      xlsx.utils.book_append_sheet(wb, data, 'mau_file_import_ims_accounts') // sheetAName is name of Worksheet
      // export Excel file
      xlsx.writeFile(wb, 'mau_file_import_ims_accounts.xls')
    },
    onChangeFile(event) {
      this.file=event.target.files[0]
      console.log('this.file is ',this.file)
      this.readFileExcel()
    },
    checkfile() {
      if (this.file == null) {
          this.ShowAlert('Chưa chọn file excel')
          return false
      }
      if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
        this.ShowAlert('File import không đúng định dạng xlsx!')
        return false
      }
      return true
    },
    kiemTraDuLieu(columns) {
      if (columns == null) {
          this.ShowAlert('File import có cấu trúc không hợp lệ!')
          return false
      }
      return true
    },
    async btnThucThi_Click() {
      let temp = this.listTable.danh_sach_thue_bao.list
      let dataTemp = temp.map( ( item ) => {
        console.log(this.toUpperCase1(item))
        return this.toUpperCase1(item)
      });
      let input = {
        'vjson' : JSON.stringify(dataTemp)
      }

      let res = await this.axios.post("/web-hopdong/change_password_ims/sp_insert_json_gw240_ims_account", input)

      if (res && res.data) {
        if (res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
          this.ShowSuccess('Đã thực hiện insert dữ liệu dữ liệu import vào bảng')
        } else {
          this.ShowAlert('Có lỗi khi cập nhật dữ liệu')
        }
      } else {
        this.ShowAlert('Có lỗi khi cập nhật dữ liệu')
      }


    },

    toUpperCase1(object) {
      return Object.fromEntries(
        Object.entries(object).map(([k, v]) => [k.toUpperCase(), v])
      );
    },

    upperCaseKeys(data) {
      let gridData = [];
      if (data != null && data != undefined && data.length > 0) {
        let keys = Object.keys(data[0]);
        let upperCaseKey = [];

        for (let j = 0; j < data.length; j++) {
          upperCaseKey = [];
          for (let i = 0; i < keys.length; i++) {
            this.set(upperCaseKey, keys[i].toUpperCase(), data[j][keys[i]]);
          }
          upperCaseKey.push(...upperCaseKey);

          gridData.push(Object.assign({}, upperCaseKey));
        }
      }
    },
    set(obj, prop, value) {
      obj[prop] = value
    },

    async tsbtnThucHien_Click() {
      //let chooseListThueBao = this.$refs.danhSachThueBaoRef.getSelectedRecords()
      let chooseListThueBao=this.listTable.danh_sach_thue_bao.list.filter(x=>x.ischeck=='1')
      
      let input = {
        'ma_tinh_convert' : '',
        'lstSelected' : chooseListThueBao.map(x=>{
          delete x['id']
          delete x['ischeck']
          return x
        })
      }
      console.log('input', input)
      this.loading(true)
      let res = await this.axios.post('/web-hopdong/change_password_ims/dongbo', input)
      if (res && res.data) {
        if (res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
          this.listTable.danh_sach_thue_bao.list = res.data.data.map((x, index)=>{
            x.id='id_'+(index+1)
            x.ischeck='0'
            return x
          })
          this.ShowSuccess('Đồng bộ thành công')
          this.loading(false)
        } else {
          this.ShowAlert('Có lỗi xảy ra : ' + res.data.message)
          this.loading(false)
        }
      }

    },

    async deleteRecord() {
      //let selectRecords = this.$refs.danhSachThueBaoRef.getSelectedRecords()
      let selectRecords = this.listTable.danh_sach_thue_bao.list.filter(x=>x.ischeck=='1')
      if (selectRecords.length > 0) {
        let vserial_num = selectRecords.map((item) => item.serial_num).join(',')
        let res = await this.axios.post('/web-hopdong/change_password_ims/sp_delete_gw240_ims_account', {
          'vserial_num' : vserial_num
        })
        if (res && res.data) {
          if (res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
            this.ShowSuccess('Xóa bản ghi thành công')
            this.Load_DanhSachThueBao()
          } else {
            this.ShowAlert('Có lỗi xảy ra : ' + res.data.message)
          }
        }
      } else {
        this.ShowAlert('Bạn chưa chọn bản ghi nào để xóa. Vui lòng chọn bản ghi!')
      }
    },

    download(){
      //xuất file
      let data = xlsx.utils.json_to_sheet(this.listTable.danh_sach_thue_bao.list)
      let wb = xlsx.utils.book_new() // make Workbook of Excel
      xlsx.utils.book_append_sheet(wb, data, 'Danh sach thue bao') // sheetAName is name of Worksheet
      // export Excel file
      let fileFullName= 'Danh_sach_thue_bao.xlsx'
      xlsx.writeFile(wb, fileFullName)
  },
  grid_RowSelected(args){
    if (!args.isHeaderCheckboxClicked && args.isInteracted) {
      // this.updateDSKhoaSelected(args.data)
      console.log('gridChuaXuLy_RowSelected', args)
      if(Array.isArray(args.data)){
        for(let i=0;i<args.data.length;i++){
          const index = this.listTable.danh_sach_thue_bao.list.findIndex(x => x.id == args.data[i].id)
          this.listTable.danh_sach_thue_bao.list[index].ischeck='1'
        }
      }else {
        const index = this.listTable.danh_sach_thue_bao.list.findIndex(x => x.id == args.data.id)
        this.listTable.danh_sach_thue_bao.list[index].ischeck='1'
      }
      
    }
  },
  dataBound(args){
    let obj = this.$refs.danhSachThueBaoRef.$refs.grid.ej2Instances
    // console.log(obj)
    if (obj.currentViewData.length > 0) {
      let data = obj.currentViewData
      let selectedCurrentPage = []
      data.forEach((value, index) => {
        if (value.ischeck=='1') {
          selectedCurrentPage.push(index)
        }
      })
      obj.selectRows(selectedCurrentPage)
    }
  },
  checkBoxChange(args){
    if (args.target.className=='e-checkselectall') { // click check/uncheck all
      
      let dsFilter = this.$refs.danhSachThueBaoRef.filteredDataSource || []
      console.log('checkBoxChange checkselectall', dsFilter)
      for(let i=0;i<dsFilter.length;i++){
        const index=this.listTable.danh_sach_thue_bao.list.findIndex(x=>x.id==dsFilter[i].id)
        if(index>-1){
          if(args.checked){
            this.listTable.danh_sach_thue_bao.list[index].ischeck='1'
          }else{
            this.listTable.danh_sach_thue_bao.list[index].ischeck='0'
          }
        }else{
          this.listTable.danh_sach_thue_bao.list[index].ischeck='0'
        }
      }
    }
  },
  rowDeselected(args){
    if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
      const index = this.listTable.danh_sach_thue_bao.list.findIndex(x => x.id == args.data.id)
      this.listTable.danh_sach_thue_bao.list[index].ischeck='0'
    }
  },
  
    readFileExcel() {
      let columns
      this.listTable.danh_sach_thue_bao.list = []
      try {
        // this.loading(true)
        if (this.checkfile()) {
            const reader = new FileReader()
            console.log('reader is ', reader)
            reader.onload = (e) => {
                /* Parse data */
                console.log('run in this')
                const bstr = e.target.result
                const wb = xlsx.read(bstr, { type: "binary" })
                /* Get first worksheet */
                const wsname = wb.SheetNames[0];
                const ws = wb.Sheets[wsname];
                /* Convert array of arrays */
                const data = xlsx.utils.sheet_to_json(ws, { header: 1 });
                console.log('data is ', data)
                columns=data[0]
                if(this.kiemTraDuLieu(columns)){
                  //Check dữ liệu
                  for(var i=1;i<data.length;i++){
                    this.listTable.danh_sach_thue_bao.list.push({
                      'id':'id_'+i,
                      'ischeck':'0',
                      'matinh' : data[i][0] ? data[i][0] : '',
                      'ma_vung' : data[i][1] ? data[i][1] : '',
                      'line1' : data[i][2] ? data[i][2] : '',
                      'line2' : data[i][3] ? data[i][3] : '',
                      'serial_num' : data[i][4] ? data[i][4] : '',
                      'ma_lhtb' : data[i][5] ? data[i][5] : ''
                    })
                  }
                  //Check dữ liệu
                }
                
            }
            reader.readAsBinaryString(this.file)
        }
    } catch (error) {
        console.log(error)
        // this.$alert('Có lỗi xảy ra khi đọc file', '', {
        //     dangerouslyUseHTMLString: true,
        //     confirmButtonText: 'OK',
        //     type: 'error'
        // })
        this.ShowAlert('Có lỗi xảy ra khi đọc file')
    } finally {
        // this.loading(false)
    }
    }
  }
};

</script>