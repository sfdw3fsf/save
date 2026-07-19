<template>
  <b-modal ref="ModalImportCA" size="xl" body-class="p-0" hide-header hide-header-close no-close-on-backdrop @show="handleShowModal" hide-footer>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Import Chứng thư số</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="exportFileMau"> <span class="icon one-download"></span> Download File mẫu </a>
          </li>
        </ul>
      </div>

      <div class="box-form">
        <div class="legend-title">Thông tin </div>
        <div class="row">
         
            <div class="info-row">
              <div class="key w70">Chọn file</div>

              <div class="input-group" style="width: 70% !important">
                <div class="input-text" style="width: 90% !important">
                  <input type="text" v-model="tenFile" class="form-control" @click="$refs.fileInput.click()"/>
                </div>
                <div class="input-addon">
                  <button class="btn" @click="$refs.fileInput.click()">
                    <span class="-ap icon-more_horiz"></span>
                    <input ref="fileInput" type="file" id="upload" style="display: none" @change="importExcel" accept=".xls, .xlsx" @click="$refs.fileInput.value = null"  hidden/>
                  </button>
                </div>
              </div>
            
          </div>
        </div>
        <div class="row">

      
        <div class="info-row btn-row">
          <div class="value">
            <button class="btn btn-primary" style="min-width: 100px" @click="btnNapLai_Click"><span class="icon one-reload1"></span> Nạp lại</button>
            <button class="btn btn-primary" style="min-width: 100px" @click="btnThucThi_Click" :disabled="!enableBtnThuchi"><span class="icon fa fa-play-circle-o"></span> Thực thi</button>
            <!-- <button class="btn btn-second" style="min-width: 100px" @click="btnXuatExcel_Click" :disabled="!enableBtnDisXuatEx"><span class="icon one-download"></span> Xuất Excel</button> -->
          </div>
          <span>
            <b>*Ghi chú:</b>
            <span> - Chương trình chỉ đọc dữ liệu từ sheet có tên "Data" <br /></span>

            <span class="ml-5 padl19">- Chọn một thuê bao ở danh sách thuê bao để làm thuê bao gốc </span>
          </span>
          <span> </span>
        </div>
    </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Dữ liệu excel</div>
        <div class="table-content">
          <DataGrid     :columns="listImportExcel.headers"
                        :dataSource="listImportExcel.data"
                        :enable-paging-server="false"
                        panelDataHeight="200"
                        :showFilter="true"
                        :allowPaging="true"
                        :showColumnCheckbox="false"
                        :enabledSelectFirstRow="true"
                        >
                </DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>
  <script>
import XLSX from 'xlsx'
import api from './api.js'
export default {
  name: 'ModalImportCA',
  props: {
    hdkh_id: {
      type: Number,
      default: 0,
    },
    ma_gd:""
  },
  components: {},
  data() {
    return {
      tenFile: '',
      enableBtnThuchi: false,
      enableBtnDisXuatEx: false,
      checkDataImport: true,
      listImportExcel:{
        headers:[

        {
            fieldName: "KET_QUA",
            headerText: "Kết Quả",
            allowSorting: false,
            allowFiltering: false,
            width: 60,
            template:
              '<div class="text-light"> ${if(KET_QUA == 1)} <span class="bg-success">Sẵn Sàng </span> ${else} <span class="bg-danger">KT Dữ liệu</span> ${/if} </div>',

          },
        {
            fieldName: "TEN_TB",
            headerText: "Tên TB",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(TEN_TB != "" && TEN_TB != undefined && TEN_TB != "undefined")} <span >${TEN_TB}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "SDT",
            headerText: "SĐT",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(SDT != "" && SDT != undefined && SDT != "undefined")} <span >${SDT}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "EMAIL",
            headerText: "Email",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(EMAIL != "" && EMAIL != undefined && EMAIL != "undefined")} <span >${EMAIL}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "LOAIGT_ID",
            headerText: "Loại GT",
            allowSorting: false,
            allowFiltering: false,
            width: 40,
            template:
              ' ${if(LOAIGT_ID != "" && LOAIGT_ID != undefined && LOAIGT_ID != "undefined")} <span >${LOAIGT_ID}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "SOGT",
            headerText: "Số GT",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(SOGT != "" && SOGT != undefined && SOGT != "undefined")} <span >${SOGT}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "CHUCDANH",
            headerText: "Chức Danh",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(CHUCDANH != "" && CHUCDANH != undefined && CHUCDANH != "undefined")} <span >${CHUCDANH}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "TENTINH",
            headerText: "Tên Tỉnh",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(TENTINH != "" && TENTINH != undefined && TENTINH != "undefined")} <span >${TENTINH}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "TENQUAN",
            headerText: "Tên Quận",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(TENQUAN != "" && TENQUAN != undefined && TENQUAN != "undefined")} <span >${TENQUAN}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "TOCHUC",
            headerText: "Tổ Chức",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(TOCHUC != "" && TOCHUC != undefined && TOCHUC != "undefined")} <span >${TOCHUC}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },
          {
            fieldName: "PHONGBAN",
            headerText: "Phòng Ban",
            allowSorting: false,
            allowFiltering: false,
            width: 100,
            template:
              ' ${if(PHONGBAN != "" && PHONGBAN != undefined && PHONGBAN != "undefined")} <span >${PHONGBAN}</span> ${else} <div class="bg-danger" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> ${/if} </div>',
          },

        ],
        data:[]
      }
    }
  },
  created() {},
  computed: {},

  methods: {
    showModal() {
        if(this.hdkh_id &&this.ma_gd ){
            this.$refs['ModalImportCA'].show()
        }
     
    },
    hideModal() {
      this.btnNapLai_Click();
      this.$refs['ModalImportCA'].hide();
     
    },

    handleShowModal(){
            console.log("khach hang ID=>", this.hdkh_id);
            console.log("ma dg=>", this.ma_gd);
    },

    importExcel(event) {
        const file = event.target.files ? event.target.files[0] : null
       
        if (file) {

        this.$root.loading(true);
        this.tenFile = file.name;
        const reader = new FileReader();

        reader.onload = async (e) => {
          /* Parse data */
          const bstr = e.target.result
          const wb = XLSX.read(bstr, { type: 'binary' })
          /* Get first worksheet */
          const wsname = wb.SheetNames[0]
          const ws = wb.Sheets[wsname]
          /* Convert array of arrays */
          const data = XLSX.utils.sheet_to_json(ws, { header: 1 })

          if (!this.checkColumnExcel(data[0])) {
              this.$root.loading(false)
              return this.$toast.error('Lỗi định dạng file excel. Vui lòng kiểm tra lại file excel!')
            }
  
            this.$root.loading(true)
            this.listImportExcel.data = [];
            this.checkDataImport = true;
            for (let index = 0; index < data.length - 1; index++) {
                let r = {}
                let keys = data[0]
                let values = data[index + 1]
                for (let i = 0; i < keys.length; i++) {
                r[keys[i]] = values[i]
               
                }
                const item  = this.checkDataExcel(r);
               
                this.listImportExcel.data.push(item);
            }

            //enable button thu thi 
            if(this.listImportExcel.data.length > 0 && this.checkDataImport){
                this.enableBtnThuchi = true;
            }
            this.$root.loading(false)
        }
       

        reader.readAsBinaryString(file)
      } else {
        return
      }
    },

    btnNapLai_Click() {
        //clear data
        this.listImportExcel.data = [];
        this.tenFile = "";
        this.enableBtnThuchi = false;
        this.checkDataImport = true;
    },

    btnThucThi_Click() {
        let params = {};
        params.vdata = JSON.stringify(this.listImportExcel.data);
        params.vhdkh_id = this.hdkh_id
        params.vma_gd = this.ma_gd
        console.log(JSON.stringify(params));
        this.$root.loading(true);
        api.postImport(this.axios, params).then((response) => {
          this.$root.loading(false);
          if (
            response &&
            response.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.$toast.success("Import CA thành công!");
            this.btnNapLai_Click()
          } else {
            this.$toast.error(response.data.message);
          }
        })
        .catch((e) => {
            this.$toast.error("Import CA thất bại. Vui lòng liên hệ Admin!");
          this.$root.loading(false);
        });

    },
    // btnXuatExcel_Click() {},

    exportFileMau() {
      window.location.href = '/static/files/MauFile_Import_CA.xlsx'
    },
     /* 2. check xem đủ tên cột hay chưa */
     checkColumnExcel(dataNameTable) {
      if (dataNameTable) {
        return (
          dataNameTable.includes('TEN_TB') &&
          dataNameTable.includes('SDT') &&
          dataNameTable.includes('EMAIL') &&
          dataNameTable.includes('LOAIGT_ID') &&
          dataNameTable.includes('SOGT') &&
          dataNameTable.includes('CHUCDANH') &&
          dataNameTable.includes('TENTINH') &&
          dataNameTable.includes('TOCHUC') &&
          dataNameTable.includes('PHONGBAN')
        )
      }
      return false
    },
    checkDataExcel(item) {
      const isEmpty = (val) => {
      
        return (val === undefined || val == null || val.length <= 0 || val === ""  ) ? true : false;
      }
      item["KET_QUA"] = 1;
      if ((
        isEmpty(item['TEN_TB']) || 
        isEmpty(item['SDT']) ||
        isEmpty(item['EMAIL']) ||
        isEmpty(item['LOAIGT_ID']) ||
        isEmpty(item['SOGT']) ||
        isEmpty(item['CHUCDANH']) ||
        isEmpty(item['TENTINH']) ||
        isEmpty(item['TOCHUC']) ||
        isEmpty(item['PHONGBAN'])
      )){
        item["KET_QUA"] = 0;
        this.checkDataImport = false;
      }
      return item;
    },
    
  
  }
}
</script>

  <style scoped>
  .btn-row {
    margin-top: 5px;

  }
  </style>
  