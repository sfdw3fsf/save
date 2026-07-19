<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDocFileKhoaMo"
        :header='"Đọc file khóa mở thuê bao"'
        :animationSettings='animationSettings'
        showCloseIcon=true width='80%'
        :target='target'
        :open="dialogOpen"
        :close="dialogClose"
        :isModal="true"
    >  
        <div class="">
            <div class="list-actions-top">
                <ul class="list">
                <li>
                    <a href="javascript: void(0)" @click="onReadFile">
                        <span class="icon design_bullet-list-67 nc-icon-glyph"></span> Danh sách
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onAccept">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onExcelExport">
                        <span class="icon one-excel"></span> Xuất Excel
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="close">
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
                    <div class="legend-title" :class="[tableTitleClass]">{{ tableTitle }}</div>          
                    <rtable
                        :fields="fields"
                        :items="items"                        
                    >
                    </rtable>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import rtable from '../../components/RTable/RTable.vue'
import API from '../../api/MoMayTheoDSAPI.js'

export default {
  components: { rtable },
  name: "PopupDocFileKhoaMo",

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
        default: '1'
    }
  },

  mounted() {
  },
  data() {
    return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        filePath: '',
        status: 1,          //0: Cho phep cap nhat; 1: Khong cho phep cap nhat
        thamSo_Items: [],
        khoaMo_Items: [],
        items: [],
        tableTitle: 'Danh sách thuê bao',
        tableTitleClass: 'text-main',
        fields: [
            {
                key: "MA_TB",
                label: "Mã TB"                
            },
            {
                key: "TRANGTHAI",
                label: "Trạng thái"                
            },
        ],
    };
  },
  methods: {
    openDialog () {
        this.$refs.dlgDocFileKhoaMo.show();
    },

    onAccept() {
        let returnItems = [];
        if (this.items.length <= 0) {
            this.$toast.error('Chưa có dữ liệu để nạp!');
        } else if (this.status === 1) {
            this.$toast.error('Không được phép cập nhật do dữ liệu lỗi!');
        } else {
            this.items.forEach(item => {
                returnItems.push({ MA_TB: item.MA_TB });
            });
        }

        this.$emit('acceptChange', returnItems);
        this.close();
    },

    close() {
        this.filePath = '';        
        this.items = [];
        this.$refs.dlgDocFileKhoaMo.hide();
    },

    onExcelExport() {
        if (this.items.length <= 0) {
            this.$toast.error('Chưa có dữ liệu để xuất excel!');
            return;
        }

        this.loading(true);
        try {            
            var dsach = xlsx.utils.json_to_sheet(this.items);
            var wb = xlsx.utils.book_new(); // make Workbook of Excel
            xlsx.utils.book_append_sheet(wb, dsach, "Danh sách thuê bao"); // sheetAName is name of Worksheet
            // export Excel file
            xlsx.writeFile(wb, "DocFileThueBao.xlsx");
        } 
        catch (error) {
        } 
        finally {
            this.loading(false);
        }
    },    

    dialogOpen() {
    },

    dialogClose() {        
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

    async onChangeFile(e) {
        const files = e.target.files;            
        if (!files.length) {
            return;
        } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
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

                //Validate input file:
                if (Object.keys(ws[0]).length !== 1) {                        
                    this.$toast.error('File phải có 1 cột: MA_TB');
                    this.status = 1;
                    return;
                }
                if (!ws[0].hasOwnProperty("MA_TB")) {
                    this.$toast.error('Tên cột trong file không đúng định dạng: MA_TB');
                    this.status = 1;
                    return;
                }
                     
                if (ws.length > 0) {
                    await this.getDSThamSoMDMATS();
                    let dsKhongXuLy = -1;
                    if (this.thamSo_Items.length > 0) {
                        dsKhongXuLy = Number(this.thamSo_Items[0].TEN_TS);
                    }

                    let kieu = dsKhongXuLy === 1 ? 1 : 2;
                    let dsData = [];
                    ws.forEach(item => {
                        let data = {
                            ma_tb: item.MA_TB,
                            dichvuvt_id: this.dichVuId,
                            loaitb_id: null,
                            ghichu: ''
                        }
                        dsData.push(data);
                    })
                    await this.getDSKhoaMoFileTMPKXL(dsData, kieu);

                    if (this.khoaMo_Items.length <= 0) {
                        this.status = 1;
                        return this.$toast.error("Không tìm thấy dữ liệu!");
                    } else {                    
                        if (this.khoaMo_Items[0].TRANGTHAI_ID === '1') {
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
                return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
            }
        };
        fileReader.readAsBinaryString(files[0]);
        this.filePath = files[0].name;        
    },

    async getDSThamSoMDMATS() {
        try {
            this.loading(true);
            this.thamSo_Items = [];
            let apiParams = {
                pMaThamSo: 'KHOAMAY_TB_KHONG_XULY'
            }
            let response = await API.getDSThamSoMDMATS(this.axios, apiParams);
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                this.thamSo_Items = response.data.data;
            }
        }
        catch (error) {
            console.log(error);
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
            }
            let response = await API.getDSKhoaMoFileTMPKXL(this.axios, apiBody);
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                this.khoaMo_Items = response.data.data.data;
            }
        }
        catch (error) {
            console.log(error);
        }
        finally {
            this.loading(false);
        }
    }
  }
}
</script>
