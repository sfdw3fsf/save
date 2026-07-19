<!--ĐỌC FILE CHO FORM TẠO DANH SÁCH NÂNG/HẠ GÓI -->
<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDocFileKhoaMo"
        :header='"Đọc file khóa mở thuê bao"'
        :animationSettings='animationSettings'
        showCloseIcon=true width='50%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >  
        <div class="">
            <div class="list-actions-top">
                <ul class="list">
                <li>
                    <a href="javascript: void(0)" @click="onReadFile" title="Hiển thị danh sách">
                        <span class="icon design_bullet-list-67 nc-icon-glyph"></span> Danh sách
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onAccept" title="Chấp nhận">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onExcelExport" title="Xuất excel">
                        <span class="icon one-excel"></span> Xuất Excel
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
                    <span class="one-alert f20 text-warning"></span> File excel gồm 1
                    trường: MA_TB
                </div>
                <div class="info-row">
                    <div class="key w90">Tên file</div>
                    <div class="value">
                    <div class="input-more-button" title="Đọc file khóa/mở máy">                           
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
                    <div class="legend-title" style="font-weight: 600!important">
                        <div class="row">
                            <div class="col-sm-6 col-12" :class="[tableTitleClass]">{{ tableTitle }}</div>                        
                            <div class="col-sm-6 red tleft col-12">
                                {{ processTime }}
                            </div>
                        </div>
                    </div>                    
                    <DataGrid v-bind:columns="[
                        {fieldName: 'MA_TB', headerText: 'Mã TB', allowFiltering: true, allowSorting: false, width: 120},
                        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 200}                        
                    ]"
                        v-bind:dataSource="items"
                        dataKeyField="MA_TB"
                        :showFilter="true"                    
                        :allowPaging="true"
                        :allowExcelExport="true"
                        :enablePagingServer="false"                        
                        ref="docFileGrid"                        
                    >
                    </DataGrid>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<style></style>
<script>
import API from '../../api/NangHaGoiAPI.js'
import xlsx from 'xlsx'
export default {
  components: {},
  name: "PopupDocFileKhoaMoThueBao",
  props: {
      kyCuoc: String,
      dichVuId: Number
  },
  data() {
    return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        status: false,
        filePath: '',
        files: {},       
        items: [],
        dsLoi: [],
        processTime: '00:00:00',
        tableTitle: 'Danh sách thuê bao',
        tableTitleClass: 'text-main'        
    };
  },
  methods: {
    openDialog () {
        this.$refs.dlgDocFileKhoaMo.show();
    },

    onAccept() {
        if (this.items.length <= 0)
            return this.$toast.error('Chưa có dữ liệu để nạp');
        if (!this.status)
            return this.$toast.error('Không được phép cập nhật!');

        this.$emit('acceptChange', this.items);
        this.close();
    },

    async onReadFile() {
            if (this.filePath.trim() === '') {
                return this.$toast.error("Chưa chọn file để mở!");
            }
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
    },

    close() {
        this.status = false;
        this.filePath = '';
        this.files = {};        
        this.items = [];
        this.tableTitle = 'Danh sách thuê bao hạ gói',
        this.tableTitleClass = 'text-main',
        this.processTime = '00:00:00';
        this.$refs.dlgDocFileKhoaMo.hide();
    },

    onExcelExport() {
        if (this.items.length <= 0)
            this.$toast.error("Chưa có dữ liệu để xuất excel!");
        else {            
            let excelExportProperties = {
                fileName:"DocFileKhoaMoThueBao.xlsx",
                dataSource: this.items
            };
            this.$refs.docFileGrid.excelExport(excelExportProperties);
        }
    },

    dialogOpen() {
    },

    dialogClose() {
    },    

    async checkValidData(items) {
        //Check duplicate data:
        let dupItems = items.filter((s => v => s.has(v.MA_TB) || !s.add(v.MA_TB))(new Set));
        if (dupItems.length > 0) {
            dupItems.forEach(item => {
                this.items.push({ MA_TB: item.MA_TB, TRANGTHAI: 'Thuê bao bị lặp lại' });
            });
            return false;
        }

        let ds = items.map(item => item.MA_TB);
        await this.getKiemTraDSTuFile(ds);        
        if (this.dsLoi === null || this.dsLoi.length <= 0) {            
            items.forEach(item => {
                this.items.push({ MA_TB: item.MA_TB, TRANGTHAI: 'ok' });
            });
            return true;
        } else {            
            this.dsLoi.forEach(item => {
                this.items.push({ MA_TB: item.MA_TB, TRANGTHAI: item.LOI });
            });
            return false;
        }
    },

    async onChangeFile(e) {
        let beginTime = new Date();
        let isError = false;
        this.processTime = '00:00:00';
        this.items = [];
        this.files = e;
        const files = e.target.files;            
        if (!files.length) {
            this.status = false;
            return;
        } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
            this.status = false;
            return this.$toast.error("File excel không đúng định dạng!");
        }            
        const fileReader = new FileReader();            
        fileReader.onload = async (ev) => {
            try {
                const data = ev.target.result;                    
                const workbook = xlsx.read(data, { type: "binary" });
                const wsname = workbook.SheetNames[0];
                const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);              

                //Validate input file:
                if (Object.keys(ws[0]).length !== 1) {        
                    this.status = false;
                    return this.$toast.error('File phải có 1 cột: MA_TB');
                }
                if (!ws[0].hasOwnProperty("MA_TB")) {
                    this.status = false;
                    return this.$toast.error('Tên cột trong file không đúng định dạng: MA_TB');                    
                }
                     
                if (ws.length > 0) {
                    let rs = await this.checkValidData(ws);
                    if (!rs) {
                        isError = true;
                        this.status = false;
                    } else 
                        this.status = true;
                }                
            } catch (e) {
                console.log(e)
                this.status = false;
                this.$toast.error("Tên trường trong file không đúng định dạng: MA_TB");
            } finally {
                if (isError) {                    
                    this.tableTitle = 'Danh sách thuê bao lỗi';
                    this.tableTitleClass = 'red';
                } else {                    
                    this.tableTitle = 'Danh sách thuê bao hạ gói';
                    this.tableTitleClass = 'text-main';
                }
                let timeSpan = new Date() - beginTime;
                let seconds = timeSpan / 1000;
                let hours = parseInt( seconds / 3600 ); 
                seconds = seconds % 3600;            
                let minutes = parseInt( seconds / 60 );            
                seconds = Math.round(seconds % 60);
                this.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
            }
        };
        fileReader.readAsBinaryString(files[0]);
        this.filePath = files[0].name;        
    },

    async getKiemTraDSTuFile(ds) {
        try {
            this.loading(true);
            this.dsLoi = [];
            let apiParams = {
                dichVuVTId: this.dichVuId,
                kieu: 7,                    //Kieu = 7: Kiem tra theo MA_TB
                kyCuoc: this.kyCuoc,
                maTT: ds
            };
            let response = await API.getKiemTraDSTuFile(this.axios, apiParams);
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
               this.dsLoi = response.data.data;
            }
        }
        catch (error) {
            console.log(error);
        }
        finally {
            this.loading(false);
        }
    },
  }
}
</script>
