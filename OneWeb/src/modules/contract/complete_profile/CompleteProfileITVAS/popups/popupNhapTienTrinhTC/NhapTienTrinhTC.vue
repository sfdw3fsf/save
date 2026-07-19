<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogNhapTienTrinhTC" :position="position" :header="'Nhập mới nội dung tiến trình thi công'" showCloseIcon="true" width="60%" target="#app .main-wrapper">
    <div class=""> 
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" v-on:click="btnGhiLai_Click">
                <span class="icon one-save"></span> Ghi lại
              </a>
            </li>

          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <textarea name="" class="form-control" v-model="txtNoiDung" style="height: 400px;resize: none;"></textarea>
          </div>
        </div>
    </div>
  </ejs-dialog>
</template>
<script>
import XLSX from 'xlsx'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api2 from './PopUpApi' 
import downloadexcel from "vue-json-excel";

export const deleteTemplate = function () {
  return {
    template: Vue.component('deleteTemplate', {
      template: '<ejs-button class="btn btn-default" @click="deleteItem">Remove</ejs-button>',
      data: function () {
        return {
          data: {}
        }
      },
      methods: {
        deleteItem () {
          console.log('deleteItem', this.data)
        }
      }
    })
  }
}

export default {
  components: {
    XLSX,
    moment,
    DatePicker, 
    api2,
    downloadexcel
  },
  name: 'NhapTienTrinhTC',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: {},
      txtNoiDung: "",
    }
  },
  created: async function () {
     
  },
  mounted: function () { 
    this.pageLoad();
  },
  watch: {
     
  },
  methods: {

    
    dialogClose() {},
    openDialog(data) { 
      if(!data){
        return;
      }

      this.state.hdtb_id = data.hdtb_id;
      this.state.phanvung_bancheo_id = data.phanvung_bancheo_id;
      this.state.tientrinhtc_id = data.tientrinhtc_id;
      this.state.nguoidung_id = data.nguoidung_id;
      this.state.nhanvien_id = data.nhanvien_id;
      this.txtNoiDung = data.noidung;
      this.$refs.dialogNhapTienTrinhTC.show()
    },
    async pageLoad(){
     
    }, 
    async  btnGhiLai_Click(){
      if(!this.txtNoiDung || this.txtNoiDung.toString().trim() == ""){
        this.$toast.error('Bạn chưa nhập nội dung!');
        return;
      }

      if(this.txtNoiDung.trim().length > 2000){
        this.$toast.error('Nội dung không được vượt quá 2000 ký tự');
        this.$refs.txtNoiDung.focus();
        return;
      }
 
      let input = { "tinhthicong_id": this.state.phanvung_bancheo_id, "hdtb_id": this.state.hdtb_id, "tientrinhtc_id": 0, "noidung": this.txtNoiDung.trim() }
      let data = this.GetData(await api2.ghilai_noidung_tientrinh_thicong(this.axios, input));
      if(data == "OK"){
        this.$toast.success("Lưu thông tin thành công!");
        this.$refs.dialogNhapTienTrinhTC.hide()
      }
      else{
        this.$toast.error(data);
      } 
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
    },
    
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }, 
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>