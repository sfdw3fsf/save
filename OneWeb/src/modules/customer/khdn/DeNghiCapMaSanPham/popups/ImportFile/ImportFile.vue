<template>
  <div>
    <div class='list-actions-top'>
      <ul class='list'>
        <li>
          <a @click='tsbtnGhiLai'>
            <span class='icon one-save'></span>Ghi lại
          </a>
        </li>
        <li>
         <a href="/static/files/TEMPLATE_IMPORT_SANPHAM.xlsx">
            <span class="one-file-excel"></span> Tải file mẫu
         </a>
        </li>

      </ul>
    </div>
    <div class='page-content'>
      <div class='box-form'>
        <div class='legend-title'>File import</div>
        <div class='info-row'>
          <div class='key w80'>
            Đường dẫn
          </div>
          <div class='value'>
            <div class='input-more-button'>
              <button class='btn'
                      @click='openDialog'>
                <span class='-ap icon-more_horiz'></span>
              </button>
              <input type='text' class='form-control ' id='dsFilePath' v-model='txtDuongDan' />
              <input type='file'
                     @change='onChangeFile'
                     id='dsFileDialog'
                     class='hidden'
                     accept='application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' />
            </div>
          </div>
        </div>

      </div>
      <div class='box-form'>
        <div class="legend-title">Danh sách</div>
        <div class='table-content'>
          <DataGrid :dataSource="dsSanPham.list"
                    :columns='dsSanPham.dsCols'
                    ref='dsThueBao'
                    :allowPaging='true'
                    :enablePagingServer='false'
                    :allowExcelExport="true"
                    @queryCellInfo='queryCellInfo'
                    :panelDataHeight=200
          />

        </div>
      </div>
    </div>
    </div>
</template>

<script>
import XLSX from 'xlsx'
import moment from 'moment'
import api from '../../API'

export default {
  name: 'ImportFile',
  data() {
    return {
      vds_upload:{},
      txtDuongDan : "",
      dsSanPham: {
        list:[],
        dsCols:[
          {
            fieldName: "err_msg",
            headerText: "Mã lối",
            allowFilter: true,
            allowSorting:false,
            customAttributes:{class: 'color'}
          },
          {
            fieldName: "loi",
            headerText: "Lỗi",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "spdv_id",
            headerText: "SPDV",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "spdv_cha_id",
            headerText: "SPDV cha",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_spdv_cha",
            headerText: "Mã SPDV cha",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_nhomspdv",
            headerText: "Mã nhóm SPDV",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "phuongthuc_id",
            headerText: "Phương thức",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_phuongthuc",
            headerText: "Mã phương thức",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_spdv",
            headerText: "Tên nội bộ",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_viettat",
            headerText: "Tên viết tắt",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_tienganh",
            headerText: "Tên tiếng anh",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_thuongmai",
            headerText: "Tên thương mại",
            allowFilter: true,
            allowSorting:false,
          },

          {
            fieldName: "ma_dv_chuquan",
            headerText: "Mã DV Chủ quản",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_dv_phattrien",
            headerText: "Mã đơn vị phát triển",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "trangthai_gtm",
            headerText: "Trạng thái GTM",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ngay_gtm",
            headerText: "Ngày GTM",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "nhomspdv_id",
            headerText: "Nhóm SPDV",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "phienban",
            headerText: "Phiên bản",
            allowFilter: true,
            allowSorting:false,
          },

          {
            fieldName: "socu_trienkhai",
            headerText: "Sở cứ triển khai",
            allowFilter: true,
            allowSorting:false,
          },

          {
            fieldName: "tailieu",
            headerText: "Tài liệu",
            allowFilter: true,
            allowSorting:false,
          },

          {
            fieldName: "thongtin_khac",
            headerText: "Thông tin khác",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "tt_thitruong",
            headerText: "Thông tin thị trường",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "yeucau_kt",
            headerText: "Yêu cầu kỹ thuật",
            allowFilter: true,
            allowSorting:false,
          },
        ],
      },
    }
  },
  methods:{
    openDialog() {
      $('#dsFileDialog').click();
    },
    async tsbtnGhiLai(){
      try{
        if(this.dsSanPham.list.length <= 0){
          this.$toast.error("Không có thông tin");
          return;
        }
        if(this.dsSanPham.list.find(item=>item.loi == 1) != null){
          this.$toast.error("Kiểm tra lại bản ghi lỗi");
          return;
        }
        this.loading(true)
        let data = this.dsSanPham.list.map(function (item) {
          for (var key in item) {
            var upper = key.toUpperCase()
            // check if it already wasn't uppercase
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
        let response = await api.fn_them_sanpham_import(this.axios, {sanpham_data : JSON.stringify(data),});
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.$toast.success(response.data.data);
          this.hideModal()
        }else{
          this.$toast.error(response.data.message_detail);
        }
      }catch(e){
        this.$toast.error(e)
      }finally{
        this.loading(false)
      }
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
            data = data.map(function (item) {
              for (var key in item) {
                var upper = key.toUpperCase()
                // check if it already wasn't uppercase
                if (upper !== key) {
                  item[upper] = item[key]
                  delete item[key]
                }
              }
              return item
            })
            let dataReq = {
              sanpham_data : JSON.stringify(data),
            }
            this.vds_upload = dataReq
            console.log(this.vds_upload);
            await this.sp_check_sanpham_import((this.vds_upload));

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
    async sp_check_sanpham_import(data){
      try{

        this.loading(true)
        let response = await api.sp_check_sanpham_import(this.axios, data);
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          console.log(response.data.data);
          this.dsSanPham.list = response.data.data;
        }else{
          this.dsSanPham.list = [];
        }
      }catch(e){
        console.log(e);
        this.$toast.error('Không lấy được thông tin')
      }finally{
        this.loading(false)
      }
    },
    queryCellInfo(args) {
      switch (args.column.field) {
        case 'err_msg':
          args.cell.style.color = 'red'
          break;
      }
    },

    readFile(dataBinary) {
      let workbook = XLSX.read(dataBinary, {
        type: 'binary'
      });

      let sheet = workbook.Sheets[workbook.SheetNames[0]];
      let headers = this.getHeaderRow(sheet);
      if (headers.length < 0) {
        throw 'Dữ liệu file excel chưa đúng số lượng cột';
      }


      return XLSX.utils.sheet_to_json(sheet);
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

    hideModal(){
      this.$emit("accept", {});
    },


  },
}
</script>

<style scoped>
  color{
    color:red;
  }
</style>
