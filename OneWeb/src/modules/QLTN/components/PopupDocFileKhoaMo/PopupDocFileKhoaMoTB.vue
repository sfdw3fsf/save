<!--ĐỌC FILE CHO FORM MỞ MÁY THEO DANH SÁCH -->
<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDocFileKhoaMoTB"
        :header='"Đọc file khóa mở thuê bao"'
        :animationSettings='animationSettings'
        showCloseIcon=true width='60%'
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
                    trường: MA_TB. Chỉ mở với thuê bao đang khóa 1 chiều, 2 chiều do nợ cước (khóa cưỡng bức)
                </div>
                <div class="info-row">
                    <div class="key w90">Tên file</div>
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
                    <div class="legend-title" :class="[tableTitleClass]" style="font-weight: 600!important">{{ tableTitle }}</div>                    
                    <DataGrid v-bind:columns="[
                        {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-left'},                        
                        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 200}                            
                    ]"
                        v-bind:dataSource="items"
                        dataKeyField="MA_TB"
                        :showFilter="true"                    
                        :allowPaging="true"
                        :enablePagingServer="false"
                        :allowExcelExport="true"
                        ref="thueBaoGrid"
                    >
                    </DataGrid>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<style></style>
<script>
import xlsx from "xlsx"
import API from '../../api/MoMayTheoDSAPI.js'

export default {
  components: { },
  name: "dlgDocFileKhoaMoTB",

  props: {
    kyCuoc: {
        type: String,
        default: ''
    },
    chieuKhoa: {
        type: Number,
        default: 1      //1: 1 chieu; 2: 2 chieu
    },
    khoaMo: {
        type: Number,
        default: 1      //0: Mo may; 1: Khoa may
    },
    dichVuId: {
        type: String,
        default: '0'
    },
    loaiTBId: {
        type: String,
        default: ''
    },
  },
  
  async created () {
      let rs = await this.getDSThamSoMDMATS('DOCFILE_KHOAMAY_GHICHU');
      if (rs.length > 0)
        this.common_params.docFile_KhoaMay_GhiChu = Number(rs[0].TEN_TS);
  },
  
  data() {
    return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        files: {},
        filePath: '',
        status: 1,          //0: Cho phep cap nhat; 1: Khong cho phep cap nhat
        khoaMo_Items: [],
        common_params: {
            docFile_KhoaMay_GhiChu: -1,
            ds_Khong_XL: -1
        },
        items: [],
        tableTitle: 'Danh sách thuê bao',
        tableTitleClass: 'text-main'
    };
  },
  methods: {
    openDialog () {
        this.$refs.dlgDocFileKhoaMoTB.show();
    },

    onAccept() {
        let returnItems = [];
        if (this.items.length <= 0)
            this.$toast.error('Chưa có dữ liệu để nạp!');
        else if (this.status === 1)
            return this.$toast.error('Danh sách trong file đang lỗi, hãy kiểm tra lại file đầu vào!');
        else {
            this.items.forEach(item => {
                returnItems.push({ MA_TB: item.MA_TB });
            });
            this.$toast.success('Đọc file thành công!');
        }

        this.$emit('acceptChange', returnItems);
        this.close();
    },

    close() {
        this.filePath = '';
        this.files = {};
        this.items = [];
        this.tableTitle = 'Danh sách thuê bao khóa mở';
        this.tableTitleClass = 'text-main';
        this.$refs.dlgDocFileKhoaMoTB.hide();
    },

    onExcelExport() {
        if (this.items.length <= 0)
            this.$toast.error('Không có dữ liệu để xuất ra file!');
        else {
            let excelExportProperties = {
                fileName:"Danh-sach-thue-bao.xlsx",
                dataSource: this.items
            };
            this.$refs.thueBaoGrid.excelExport(excelExportProperties);
        }
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
                this.maTB_Items = [];
                const data = ev.target.result;                    
                const workbook = xlsx.read(data, { type: "binary" });
                const wsname = workbook.SheetNames[0];
                const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);
                
                if (this.common_params.docFile_KhoaMay_GhiChu !== 1) {                    
                    if (Object.keys(ws[0]).length !== 1) {                        
                        this.$toast.error('File phải có 1 cột: MA_TB');
                        this.status = 1;
                        return;
                    }
                    console.log(ws)
                    if (!ws[0].hasOwnProperty("MA_TB")) {
                        this.$toast.error('Tên cột trong file không đúng định dạng: MA_TB');
                        this.status = 1;
                        return;
                    }
                } else {
                    if (Object.keys(ws[0]).length !== 2) {                        
                        this.$toast.error('File phải có 2 cột: MA_TB, GHICHU');
                        this.status = 1;
                        return;
                    }
                    console.log(ws)
                    if (!ws[0].hasOwnProperty("MA_TB") || !ws[0].hasOwnProperty("GHICHU")) {
                        this.$toast.error('Tên cột trong file không đúng định dạng: MA_TB, GHICHU');
                        this.status = 1;
                        return;
                    }
                }                
                     
                if (ws.length > 0) {
                    if (!this.checkDuplicateFilter(ws)) {
                        this.status = 1;
                        this.tableTitle = 'Danh sách thuê bao lỗi';
                        this.tableTitleClass = 'red';
                        return;
                    }
                    
                    let rs = await this.getDSThamSoMDMATS('KHOAMAY_TB_KHONG_XULY');
                    this.common_params.ds_Khong_XL = -1;
                    if (rs.length > 0)
                        this.common_params.ds_Khong_XL = Number(rs[0].TEN_TS);

                    let kieu = this.common_params.ds_Khong_XL === 1 ? 1 : 2;
                    let dsData = [];
                    ws.forEach(item => {
                        let data = {
                            ma_tb: item.MA_TB,
                            dichvuvt_id: this.dichVuId,
                            loaitb_id: this.loaiTBId,
                            ghichu: ''
                        }
                        dsData.push(data);
                    })
                    await this.getDSKhoaMoFileTMPKXL(dsData, kieu);

                    if (this.khoaMo_Items.length <= 0) {
                        this.status = 1;
                        return this.$toast.error("Không tìm thấy dữ liệu!");
                    } else {                    
                        if (this.khoaMo_Items[0].TRANGTHAI_ID === 1) {
                            this.status = 1;
                            this.tableTitle = 'Danh sách thuê bao lỗi';
                            this.tableTitleClass = 'red';
                        } else {
                            this.status = 0;
                            this.tableTitle = 'Danh sách thuê bao khóa mở';
                            this.tableTitleClass = 'text-main';
                        }
                        this.items = this.khoaMo_Items;
                    }                                                            
                }
            } catch (e) {
                this.status = 1;
                console.log(e);
                return this.$toast.error("File không có dữ liệu!");
            }
        };
        fileReader.readAsBinaryString(files[0]);
        this.filePath = files[0].name;        
    },

    checkDuplicateFilter(items) {
        let dupItems = items.filter((s => v => s.has(v.MA_TB) || !s.add(v.MA_TB))(new Set));
        if (dupItems.length > 0) {
            dupItems.forEach(item => {
                this.items.push({ MA_TB: item.MA_TB, TRANGTHAI: 'Thuê bao bị lặp lại' });
            });
            return false;
        } else 
            return true;
    },

    async getDSThamSoMDMATS(ts) {
        try {
            this.loading(true);
            let apiParams = {
                pMaThamSo: ts
            }
            let response = await API.getDSThamSoMDMATS(this.axios, apiParams);
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                return response.data.data;
            } else return [];
        }
        catch (error) {
            console.log(error);
            return [];
        }
        finally {
            this.loading(false);
        }
    },

    /* ds: [{ ma_tb: String, dichvuvt_id: Number, loaitb_id: Number, ghichu: String }] */
    async getDSKhoaMoFileTMPKXL(ds, kieu) {
        try {
            this.loading(true);
            this.khoaMo_Items = [];
            let apiBody = {
                pKyCuoc: this.kyCuoc,
                pChieuKhoa: this.chieuKhoa,
                pKhoaMo: this.khoaMo,
                pKieu: kieu, 
                pDSData: ds
            };
            let response = await API.getDSKhoaMoFileTMPKXL(this.axios, apiBody);
            if (response.data.error_code === 'BSS-00000000' && response.data !== undefined) {
                this.khoaMo_Items = response.data.data;
            }
        }
        catch (error) {
            this.$toast.error(error.data.message_detail);
            console.log(error);            
        }
        finally {
            this.loading(false);
        }
    }
  }
}
</script>
