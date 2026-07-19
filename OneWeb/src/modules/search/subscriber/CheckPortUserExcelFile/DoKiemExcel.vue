<template>
  <div>
    <breadcrumb :header="header" />

    <div class="page-content">
        <div class="box-form">
        <div class="legend-title">Thông tin </div>
        <div class="row">
         
            <div class="info-row">
              <div class="key w70">Chọn file</div>

              <div class="input-group" style="width: 70% !important">
                <div class="input-text" style="width: 80% !important">
                  <input type="text" v-model="tenFile" class="form-control" @click="$refs.fileInput.click()"/>
                </div>
             
                
                <div class="input-addon">
                  <button class="btn" @click="$refs.fileInput.click()">
                    <span class="-ap icon-more_horiz"></span>
                    <input ref="fileInput" type="file" id="upload" style="display: none" @change="importExcel" accept=".xls, .xlsx" @click="$refs.fileInput.value = null"  hidden/>
                  </button>
                  <button class="btn" @click.prevent="exportFileMau">
                   
                   <span class="icon one-download"></span> Download File mẫu
                  </button>
                  <div>
                   
                </div>
                </div>
              </div>
            
          </div>
        </div>
        <div class="row">

      
        <!-- <div class="info-row btn-row">
          <div class="value">
            <button class="btn btn-primary" style="min-width: 100px" @click="btnNapLai_Click"><span class="icon one-reload1"></span> Nạp lại</button>
          </div>
        </div> -->
    </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Dữ liệu excel</div>
        <div class="table-content">
            <table  class="table" v-if="listImportExcel.data.length > 0" >
               
                    <th>Mã TB</th>
           
                    <tr v-for="item in listImportExcel.data" :key="item.MATB">
                        <CheckPortUserElement 
                            :maTB="item.MATB"
                        />
                    </tr>
              
                    
         
                </table>
               
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import CheckPortAPI from './api.js'
import XLSX from 'xlsx'
import CheckPortUserElement from './CheckPortUserElement.vue'
export default {
  components: { breadcrumb, gridview, CheckPortUserElement },
  name: 'CheckPortExcel',
  data() {
    return {
      header: {
        title: 'Kiểm tra port thuê bao',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      tenFile: '',
      enableBtnThuchi: false,
      listImportExcel:{
        headers:[

        {
            fieldName: "MATB",
            headerText: "Mã TB",
            allowSorting: false,
            allowFiltering: false,

          },
        

        ],
        data:[]
      }
    }
  },
  methods: {
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
                // const item  = this.checkDataExcel(r);
               
                this.listImportExcel.data.push(r);
                console.log(this.listImportExcel.data);
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
    },
    exportFileMau() {
      window.location.href = '/static/files/MauFile_Import_Do_Kiem.xlsx'
    },
     /* 2. check xem đủ tên cột hay chưa */
     checkColumnExcel(dataNameTable) {
      if (dataNameTable) {
        return (
          dataNameTable.includes('MATB') 
        )
      }
      return false
    },
    DoKiem() {
      switch (this.kieu) {
        case 0:
          //Gọi test port
          break
      }
    }
  }
}
</script>