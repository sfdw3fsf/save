<!--ĐỌC FILE CHO FORM PHÂN GIAO GHTT -->
<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDocFilePhangiao"
        :header='"Điều chỉnh phân giao theo file"'
        :animationSettings='animationSettings'
        showCloseIcon=true width='90%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
        >  
        <div class="">
            <div class="list-actions-top">
                <ul class="list">                   
                    <li>
                        <a href="javascript: void(0)" @click="btnGhiLai_OnClick" :class="btnGhiLai.Enabled ? '' : 'disabled'">
                            <span class="icon one-save"></span> Ghi lại
                        </a>
                    </li>
                    
                    <li>               
                        <a href="/static/files/MauFile_Import_phangiao.xlsx" dowload>
                            <span class="icon one-download"></span>Tải file excel mẫu
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="close" title="Thoát">
                            <span class="icon one-export"></span> Thoát
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                <div class="note text-main">
                    <span class="one-alert f20 text-warning"></span>File
                            excel gồm các trường: STT, THANG_GIAO, MA_TB, MA_NVC, MA_NVN
                </div>
                <div class="info-row">
                    <div class="key w90">Đường dẫn</div>
                    <div class="value">
                    <div class="input-more-button">                           
                        <label for="upload" class="btn">
                            <span class="-ap icon-more_horiz"></span>
                            <input ref="fileInput" type="file" id="upload" style="display: none" @change="onChangeFile"
                                accept=".xls, .xlsx" @click="$refs.fileInput.value=null" />                                    
                        </label>
                        <input type="text" class="form-control highlight" v-model="filePath" @change="onInputText($event.target.value)">
                    </div>
                    </div>
                </div>
                </div>
                <div class="box-form">
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab1_ds">
                            <DataGrid v-bind:columns="[
                                {fieldName: 'stt', headerText: 'STT', allowFiltering: false, allowSorting: false, width: 80, cssClass: 'text-center'},  
                                {fieldName: 'thang_giao', headerText: 'Tháng giao', allowFiltering: false, allowSorting: false, width: 100, cssClass: 'text-left'},                        
                                {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: false, allowSorting: false, width: 100, cssClass: 'text-left'},                                              
                                {fieldName: 'ma_nvc', headerText: 'Mã NV chuyển', allowFiltering: false, allowSorting: false, width: 100},
                                {fieldName: 'ten_nvc', headerText: 'Tên NV chuyển', allowFiltering: false, allowSorting: false, width: 150,cssClass: 'text-left'},
                                {fieldName: 'ma_nvn', headerText: 'Mã NV nhận', allowFiltering: false, allowSorting: false, width: 100,cssClass: 'text-left'}, 
                                {fieldName: 'ten_nvn', headerText: 'Tên NV nhận', allowFiltering: false, allowSorting: false, width: 150,cssClass: 'text-left'}, 
                                {fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 200,cssClass: 'text-left'}                         
                            ]"
                                v-bind:dataSource="Phangiao_Items"
                                :showFilter="false"
                                :panelDataWidth="900"                    
                                :allowPaging="true"
                                :enablePagingServer="false"
                                :allowExcelExport="true"
                                ref="thueBaoGrid"
                            >
                            </DataGrid>
                        </div>
                    </div> 
                    
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>

<script>
import xlsx from "xlsx"
import API from './api_PhanGiao.js'
export default {
  
  name: "dlgDocFilePhangiao",

  data() {
    return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        files: {},
        filePath: '',
        status: 1,          //0: Cho phep cap nhat; 1: Khong cho phep cap nhat
        Phangiao_Items: [],
        thanggiao: '',
        common_params: {
            docFile_Phangiao_GhiChu: -1,
            ds_Khong_XL: -1
        },

        btnGhiLai: {Enabled: true},
           
        items: [],
        tableTitle: 'Danh sách Phân giao GHTT',
        tableTitleClass: 'text-main'
    };
  },
   
  methods: {
    openDialog () {
        this.$refs.dlgDocFilePhangiao.show();
    },

    async btnGhiLai_OnClick (){
                try {
                     if (this.Phangiao_Items.length <= 0) {
                         this.$toast.error('Chưa có dữ liệu để ghi !');
                         this.loading(false)
                             
                     }
                     else {
                        this.loading(true)
                        let thang_giao = '{"thang_giao":'+ this.thanggiao +'}'; //'{"thang_giao":"202401"}';
                       // alert(this.thanggiao)
                        let response = await API.ghiDSphangiaoFile(this.axios,thang_giao);
                        
                        if (response.data.error_code === 'BSS-00000000' && response.data !== undefined) {
                        console.log(response.data.data)
                       
                        return this.$toast.success("Ghi dữ liệu file thành công !");                                  
                        
                        }
                    }
                } catch (e) {
                    
                    console.log(e)
                }
                 finally {
                     this.loading(false)
                 }
            },
    
    close() {
        this.filePath = '';
        this.files = {};
        this.items = [];
        this.Phangiao_Items = [];
        this.btnGhiLai.Enabled = false
        this.tableTitle = 'Danh sách Phân giao GHTT';
        this.tableTitleClass = 'text-main';
        this.$refs.dlgDocFilePhangiao.hide();
    },

    dialogOpen() {
    },

    dialogClose() {        
    },

    async onReadFile() {
        if (this.filePath.trim() === '')
            this.$toast.error("Chưa chọn file để mở!");
        else {
            try {                
                await this.$bvModal.msgBoxConfirm('Bạn muốn mở file?', {
                "title": 'Thông báo',
                "size": 'lg',
                "buttonSize": 'lg',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy bỏ',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        if (this.files !== {})
                                await this.onChangeFile(this.files);
                            else 
                                this.$toast.error("Vui lòng chọn lại file!");
                    }
                })
                .catch(err => {
                    // An error occurred
                    console.log(err);
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!");
            }
        }
    },

    async onChangeFile(e) {
         this.files = e;
         this.items = [];
         const files = e.target.files;            
         if (!files.length) return;
         else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
             this.status = 1;
             return this.$toast.error("File excel không đúng định dạng!");
          }            
         const fileReader = new FileReader();            
         fileReader.onload = async (ev) => {
             try {                    
                 this.loading(true)
                 const data = ev.target.result;                    
                 const workbook = xlsx.read(data, { type: "binary" });
                 const wsname = workbook.SheetNames[0];
                 const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);
                 
                 if (this.common_params.docFile_Phangiao_GhiChu !== 5) {                    
                     if (Object.keys(ws[0]).length !== 5) {                        
                         this.$toast.error('File phải theo định dạng có 5 cột: STT, THANG_GIAO, MA_TB, MA_NVC, MA_NVN');
                         this.status = 1;
                         return;
                     }
                     console.log(ws)
                     if (!ws[0].hasOwnProperty("STT")) {
                         this.$toast.error('Tên cột 1 trong file không đúng định dạng: STT');
                         this.status = 1;
                        return;
                     }
                     if (!ws[0].hasOwnProperty("THANG_GIAO")) {
                         this.$toast.error('Tên cột 2 trong file không đúng định dạng: THANG_GIAO');
                         this.status = 1;
                        return;
                     }
                     if (!ws[0].hasOwnProperty("MA_TB")) {
                         this.$toast.error('Tên cột 3 trong file không đúng định dạng: MA_TB');
                         this.status = 1;
                        return;
                     }
                     if (!ws[0].hasOwnProperty("MA_NVC")) {
                         this.$toast.error('Tên cột 4 trong file không đúng định dạng: MA_NVC');
                         this.status = 1;
                        return;
                     }
                     
                     if (!ws[0].hasOwnProperty("MA_NVN")) {
                         this.$toast.error('Tên cột 5 trong file không đúng định dạng: MA_NVN');
                         this.status = 1;
                        return;
                     }
                     
                  } 
                    
                    let dsData = [];
                        ws.forEach(item => {
                         let data = {
                             stt: item.STT,
                             thang_giao: item.THANG_GIAO,
                             ma_tb: item.MA_TB,
                             ma_nvc: item.MA_NVC,
                             ma_nvn: item.MA_NVN  
                         }
                         
                         dsData.push(data);
                        
                        })
 
                     console.log(dsData)
                    let response = await API.getDSphangiaoFile(this.axios,dsData);
                    if (response.data.error_code === 'BSS-00000000' && response.data !== undefined) {
                        console.log(response.data.data)
                        
                        this.Phangiao_Items = response.data.data;

                        this.thanggiao = response.data.data[0].thang_giao
                        //alert(this.thanggiao)

                        var trangthai = ''
                        trangthai = response.data.data[0].trangthai
                        if (trangthai) {
                            this.btnGhiLai.Enabled = false
                            return this.$toast.error("Dữ liệu file import lỗi !"); 
                            
                        } 
                        else {                   
                            this.btnGhiLai.Enabled = true
                            return this.$toast.success("Import dữ liệu file thành công !");
                            
                        }
                        
                        if (this.Phangiao_Items.length <= 0) {
                            this.status = 1;
                            return this.$toast.error("Không tìm thấy dữ liệu!");
                        }                     
                            else {
                                this.status = 0;
                                this.tableTitle = 'Danh sách phân giao GHTT';
                                this.tableTitleClass = 'text-main';
                            }       
                        
                    }
                    
                }
                catch (error) {
                    //this.$toast.error(error.data.message_detail);
                    console.log(error); 
                              
                }
                finally {
                    this.loading(false);
                }
            }
            fileReader.readAsBinaryString(files[0]);
            this.filePath = files[0].name; 
        },      
    }
}
</script>     
       