<template src='./SearchBuildingByList.html'>

</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import XLSX from 'xlsx'
import Utils from '../../../Payment/IssuanceElectronicInvoices/utils'
import Api from './API'
import api from '../../../search/subscriber/SearchContract/api'
import moment from 'moment'
export default {
  name: 'SearchBuildingByList.vue',
  components: {
    breadcrumb,
  },
  data() {
    return {
      header: {
        title: 'TRA CỨU THÔNG TIN THUÊ BAO TÒA NHÀ',
        list: [
          {name: 'Quản lý tòa nhà', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu thuê bao tòa nhà theo danh sách', link: {name: 'Ui.cards'}},
        ]
      },
      vds_upload:{},
      dsThueBaoID: {
        list:[],
        dsCols:[
          {
            fieldName: "ma_duan",
            headerText: "Mã dự án",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_duan",
            headerText: "Tên dự án",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_toanha",
            headerText: "Mã Tòa nhà",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_toanha",
            headerText: "Tên tòa nhà",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "diachi_ld",
            headerText: "Địa chỉ",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_khu_dcld",
            headerText: "Tên khu ĐCLĐ",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_chudtu",
            headerText: "Mã CĐT",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_chudtu",
            headerText: "Chủ đầu tư",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ngayky",
            headerText: "Ngày ký",
            format:"dd/MM/yyyy HH:mm:ss",
            type: 'date',
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_tb",
            headerText: "Mã thuê bao",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "trangthai_tb",
            headerText: "Trạng thái",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_dvvt",
            headerText: "Dịch vụ",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "loaihinh_tb",
            headerText: "Loại hình",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "so_hd",
            headerText: "Số HĐ",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "goicuoc_adsl",
            headerText: "Gói cước",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "loai_kenh",
            headerText: "Loại kênh",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "goicuoc_kenh",
            headerText: "Gói cước kênh",
            allowFilter: true,
            allowSorting:false,
          },

          {
            fieldName: "donvi_ql",
            headerText: "Đơn vị quản lý",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "tenchuquan",
            headerText: "Chủ quản",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ngay_sd",
            headerText: "Ngày sử dụng",
            format:"dd/MM/yyyy HH:mm:ss",
            type: 'date',
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ngay_cat",
            headerText: "Ngày cắt",
            format:"dd/MM/yyyy HH:mm:ss",
            type: 'date',
            allowFilter: true,
            allowSorting:false,
          },

        ],
      },
      txtDuongDan : "",
    }
  },
  methods:{
    openDialog() {
      $('#dsFileDialog').click();
    },

    onChangeFile(event) {
      let files = event.target.files;
      if (files.length > 0) {
        let file = files[0];
        this.vds_upload = {},
        $('#dsFileDialog').val('');
        this.txtDuongDan = file.name;
        if (!this.isFileExcel(file)) {
          return;
        }
        let reader =  new FileReader();
        reader.onload = async (e) => {
          try {
            this.loading(true);
            let data = this.readFile(e.target.result);
            let dataReq = {
              vds : JSON.stringify(data),
            }
            this.vds_upload = dataReq
            await this.TRACUU_TB_TOANHA_FILE((this.vds_upload));

          } catch (ex) {
            console.log(ex);
            //this.$toast.error(ex);
          } finally {
            this.loading(false);
          }
        };
        reader.onerror = function (ex) {
          console.log(ex);
        };

        reader.readAsBinaryString(file);
      } else {
        this.txtDuongDan = '';
      }
    },

    async tsbtnXem_Click(){
      await this.TRACUU_TB_TOANHA_FILE(this.vds_upload);
    },

    async TRACUU_TB_TOANHA_FILE(data){
      try{
        this.loading(true)
        let response = await Api.TRACUU_TB_TOANHA_FILE(this.axios, data);
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          console.log(response.data.data);
          this.dsThueBaoID.list = response.data.data;
        }else{
          this.dsThueBaoID.list = [];
        }
      }catch(e){
        this.$toast.error('Không lấy được thông tin')
      }finally{
        this.loading(false)
      }
    },

    readFile(dataBinary) {
      let workbook = XLSX.read(dataBinary, {
        type: 'binary'
      });

      let sheet = workbook.Sheets[workbook.SheetNames[0]];
      let headers = this.getHeaderRow(sheet);
      if (headers.length <= 0 || headers.length > 1) {
        throw 'Dữ liệu file excel chưa đúng số lượng cột';
      }

      for (let header of headers) {
        if (header != "MA_TB") {
          throw 'File không đúng cấu trúc: MA_TB';
        }
      }

      return XLSX.utils.sheet_to_json(sheet, {
        header: ['MA_TB'], range: 1
      });
    },

    isFileExcel(file) {
      try {
        if (file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ||
          file.type === 'application/vnd.ms-excel') {
          return true;
        }
      } catch (e) {

      }

      this.$toast.error('Vui lòng chọn file có định dạng .xls hoặc .xlsx');
      return false;
    },

    getHeaderRow(sheet) {
      let headers = [];
      let range = XLSX.utils.decode_range(sheet['!ref']);
      let C, R = range.s.r; /* start in the first row */
      /* walk every column in the range */
      for (C = range.s.c; C <= range.e.c; ++C) {
        let cell = sheet[XLSX.utils.encode_cell({c: C, r: R})] /* find the cell in the first row */

        let hdr = "UNKNOWN " + C; // <-- replace with your desired default
        if (cell && cell.t) hdr = XLSX.utils.format_cell(cell);

        headers.push(hdr.toUpperCase());
      }
      return headers;
    },
    exportExcel() {
      if (this.dsThueBaoID.list.length <= 0) {
        this.$root.$toast.error('Không có dữ liệu để xuất Excel!');
        return;
      }

      let cols = this.$refs.dsThueBao.grid.getColumns();
      let excelExportProperties = {
        dataSource: this.dsThueBaoID.list,
        enableFilter: true,
        fileName: `ThongTinToaNha_${moment().format('DDMMYYYY')}.xlsx`
      };
      this.$refs.dsThueBao.excelExport(excelExportProperties)
    }
  },
}
</script>

