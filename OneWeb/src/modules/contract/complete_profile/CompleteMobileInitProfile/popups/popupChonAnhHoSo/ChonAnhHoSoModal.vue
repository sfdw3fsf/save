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
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogChonAnhHoSo" :position="position" :header="'Chọn ảnh hồ sơ'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class=""> 
        <div class="list-actions-top">
          <ul class="list"> 
            <li>
                <a href="javascript:;" @click="tsbtnTraCuu_Click">
                    <span class="icon one-reload"></span>Nạp lại
                </a>
            </li>
            <li>
              <a href="javascript:;" v-on:click="tsbtnChapNhan_Click">
                <span class="icon one-save"></span> Chấp nhận
              </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
              <!-- <div class="legend-title">Thông tin thuê bao</div> 
              <div class="row">
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w110">Dự án <span class="red">(*)</span></div>
                          <div class="value">
                              <div class="select-custom">
                                  <select name="" id="" class="form-control">
                                      <option value=""></option>
                                  </select>
                              </div>
                          </div>
                      </div>
                  </div> 
              </div> -->
              
              <div class="legend-title">File Ảnh</div> 
              <div class="info-row">
                    <DataGrid ref="lvFile" class="table-result table-gachle" v-bind:columns="[
                            {fieldName: 'url', headerText: 'Đường dẫn', allowFiltering: true},
                            {fieldName: 'loai_file', headerText: 'Loại File', allowFiltering: true , allowHtml : false}, 
                        ]"
                        v-bind:dataSource="controls.lvFile.dataSource" :showColumnCheckbox="true"
                        :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                        :enabled-select-first-row="false" 
                        > 
                    </DataGrid>
              </div> 
          </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script> 
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api from './PopUpApi' 
  
export default {
  components: { 
    moment,
    DatePicker, 
    api, 
  },

  name: 'ChonAnhHoSoModal',
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
      controls: {
        lvFile: {
          dataSource: [],
          type: "gridview",
        }
      }
    }
  },
  created: async function () {
     
  },
  mounted: function () { 
    
  },
  watch: {
     
  },
  methods: {
 
    dialogClose() {},
    openDialog(data) { 
      if(!data){
        return;
      }

      if (data.kieu == 0){
         this.state.hdtb_id = id;
         this.state.thuebao_id = -1;
      }          
      else
      {
        this.state.hdtb_id = -1;
        this.state.thuebao_id = id;
      }
  
      this.pageLoad();
      this.$refs.dialogChonAnhHoSo.show()
    },
    async pageLoad(){
      let data = {
        HDTB_ID: this.state.hdtb_id,
        THUEBAO_ID: this.state.thuebao_id,
      }
      let req = {
        "ds_para": JSON.stringify(data)
      }
      let dt = this.GetData(await api.fn_chonanhhoso_load(this.axios, req))
      this.controls.lvFile.dataSource = dt;
      //api
      // this.controls.lvFile.dataSource = [
      //       {
      //         "ischeck": "0",
      //         "url": "DHSXKD/APP-BANHANG/BPC/20211210_100831_PORTRAIT_IMAGE.jpg",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "CCBS/20211223_092450_scaled_image_picker5902593471121514023.jpg",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "DHSXKD/APP-BANHANG/BPC/20211208_110518_front_preview.jpg",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "DHSXKD/APP-BANHANG/BPC/20211210_135508_rear_preview.jpg",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "CCBS/20211223_092714_scaled_image_picker7244866448402576032.jpg",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "http://10.70.52.164:8081/FILES/BPC/HOSO/trung tam giao duc nghe nghiep_637695472682110998.pdf",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376,
      //         "hoso_id": 127
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "http://10.70.52.164:8081/FILES/BPC/HOSO/H?_637695592581137497.pdf",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376,
      //         "hoso_id": 123
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "http://10.70.52.164:8081/FILES/BPC/HOSO/cmnd a sy.pdf",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376,
      //         "hoso_id": 129
      //       },
      //       {
      //         "ischeck": "0",
      //         "url": "http://10.70.52.164:8081/FILES/BPC/HOSO/PYC_637697394329080014.pdf",
      //         "loai_file": "Khách hàng ký xác nhận",
      //         "hdtb_id": 1655332,
      //         "thuebao_id": 0,
      //         "file_id": 21376,
      //         "hoso_id": 123
      //       }
      //     ];
    }, 
    async tsbtnChapNhan_Click(){
      var grid = this.$refs.lvFile;
      var selectedRows = grid.getSelectedRecords(); 

      if (selectedRows.length==0)
      { 
          this.$toast.warning("Chưa chọn ảnh");
          return;
      }

      console.log('onSelectedFiles', selectedRows)
      this.$emit("onSelectedFiles", selectedRows);
    },
    async tsbtnTraCuu_Click(){
      this.loading(true);
      await this.pageLoad();
      this.loading(false);
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