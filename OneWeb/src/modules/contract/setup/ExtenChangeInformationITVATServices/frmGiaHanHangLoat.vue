<template>
    <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmImportCA" :position="position" :header="'Import CA hàng loạt'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
      <div class="">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" @click="this.thuchien">
                <span class="icon one-thicong"></span> Thực hiện
              </a>
            </li>
            <li>
              <a href="javascript:;" @click="this.lammoi">
                <span class="icon one-file-refresh"></span> Làm mới
              </a>
            </li>
            <li>
              <a href="https://docs.google.com/spreadsheets/d/1Lp0zVEWOf9p92zu8YgbFwvgjhuKyckN-/edit?usp=sharing&ouid=101475120132984744688&rtpof=true&sd=true" target="_blank">
                <span class="icon downlone-clipart-download"></span> File Mẫu
              </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
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
          <DataGrid ref="grcThueBao" class="table-result table-gachle"
                    v-bind:columns="controls.grcThueBao.headers"
                    v-bind:dataSource="listImportExcel"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true">
          </DataGrid>
        </div>
      </div>
    </ejs-dialog>
</template>

<script>
import XLSX from 'xlsx';
import axios from 'axios';
import api from './API'
export default {
  name: 'frmGiaHanHangLoat',
  data() {
    return {
      enableBtnThuchi: false,
      tenFile: '',
      ma_gd: '',
      hdkh_id: 0,
      checkDataImport: false,
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      listImportExcel: [],
      controls: {
        grcThueBao: {
          headers: [
            { fieldName: 'MA_TB', headerText: 'Mã thuê bao', allowFiltering: true },
            { fieldName: 'SDT', headerText: 'Số điện thoại', allowFiltering: true },
            { fieldName: 'EMAIL', headerText: 'Email', allowFiltering: true },
            { fieldName: 'LOAIGT_ID', headerText: 'Loại giấy tờ', allowFiltering: true },
            { fieldName: 'SOGT', headerText: 'Số giáy tờ', allowFiltering: true },
            { fieldName: 'CHUCDANH', headerText: 'Chức danh', allowFiltering: true },
            { fieldName: 'TENTINH', headerText: 'Tên tỉnh', allowFiltering: true },
            { fieldName: 'TENQUAN', headerText: 'Tên quận', allowFiltering: true },
            { fieldName: 'TOCHUC', headerText: 'Tổ chức', allowFiltering: true },
            { fieldName: 'PHONGBAN', headerText: 'Phòng ban', allowFiltering: true },
          ],
          list: []
        }
      }
    }
  },
  methods: {
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
    openDialog(data)
    {
      this.hdkh_id = data.hdkh_id;
      this.ma_gd = data.ma_gd;
      this.lammoi();
      this.$refs.frmImportCA.show();
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
          this.listImportExcel = [];
          this.checkDataImport = true;
          for (let index = 0; index < data.length - 1; index++) {
            let r = {}
            let keys = data[0]
            let values = data[index + 1]
            for (let i = 0; i < keys.length; i++) {
              r[keys[i]] = values[i]

            }
            //const item  = this.checkDataExcel(r);
            if(!r['MA_TB']) continue;
            this.listImportExcel.push(r);
          }

          //enable button thu thi
          if(this.listImportExcel.length > 0){

            this.enableBtnThuchi = true;

          }
          console.log(this.listImportExcel);
          this.$root.loading(false)
        }


        reader.readAsBinaryString(file)
      } else {
        return
      }
    },
    lammoi()
    {
      this.listImportExcel = [];
    },
    async thuchien()
    {
      let self = this;
      let data = {
        vhdkh_id: self.hdkh_id,
        vma_gd: self.ma_gd,
        vdata: JSON.stringify(self.listImportExcel)
      }
      let kq = await api.GIAHAN_HANGLOAT_CA_V2(axios, data);
      console.log(kq);
      if (kq.data.error_code === "BSS-00000000")
      {
        let kq_db = JSON.parse(kq.data.data);
        if(kq_db.error_code == 200)
        {
          this.$toast.success(kq_db.error_message);
          return;
        }
        else
        {
          this.$toast.error(kq_db.error_message);
          return;
        }
      }
      else
      {
        this.$toast.error(kq.data.message);
      }

    }
  }
}
</script>

<style scoped>

</style>
