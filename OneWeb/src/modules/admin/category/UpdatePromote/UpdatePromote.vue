<template src="./UpdatePromote.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from "moment";
import xlsx from "xlsx";
import api from "./api/index.js";
import DataGridCustom from "./components/DataGrid";
import collums from "./define/collums.js";

export default {
    name:'UpdateProtomote',
    components:{
        breadcrumb,
        DataGridCustom
    },
    data() {
        return {
            header: {
                title: 'Khóa thuê bao theo file',
                list: [
                    { name: 'Quản trị hệ thống', link: { name: 'Ui.cards' } },
                    { name: 'Khóa thuê bao theo file', link: { name: 'Ui.cards' } },
                ]
            },
            ...collums,
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            ngay_thuc_hien: "",
            kieu_thuc_hien: "3",
            loai_hd: "1",
            dtKMDT: [],
            dtKMDT_Selected: [],
            lstIndexExecute: [],
            dataImport: [],
            dataError: [],
            fieldnames: {
                MA_TB: "MA_TB",
                SERIAL: "SERIAL",
                STB_TYPE: "STB_TYPE",
            },
            listFileMau: [
                {
                    MA_TB: "mytv123",
                    SERIAL: "00123123123",
                    STB_TYPE: "3",
                },
            ],
            fieldnamesDTKM: {
                MA_TB: "MA_TB",
                SERIAL: "SERIAL",
                STB_TYPE: "STB_TYPE",
                THONG_BAO_LOI: "LOI",
                TRANG_THAI: "STATUS"
            },
            fieldnamesError: {
                MA_TB: "MA_TB",
                SERIAL: "SERIAL",
                STB_TYPE: "STB_TYPE",
                GHICHU_LOI: "GHICHU_LOI",
            },
        }
    },
    computed: {

    },
    methods: {
        init() {
            this.ngay_thuc_hien = this.ngay_ht;
            this.kieu_thuc_hien = "0";
            this.loai_hd = "2";
            this.btnLamMoi_click();
        },
        grid_SelectRow(val) {
            for (let j=0; j<val.length; j++) {
                for (let i=0; i<this.dtKMDT.length; i++) {          
                  if (this.dtKMDT[i].MA_TB == val[j].MA_TB) {
                        this.dtKMDT_Selected.push(val[j])
                    }
                }
            }     
        },
        btnLamMoi_click() {
            this.dtKMDT = [];
            this.dtKMDT_Selected = [];
            this.dataImport = [];
            this.dataError = [];
            this.lstIndexExecute = [];
        },
        btnExport_click() {
            if (this.dtKMDT == undefined || this.dtKMDT == null || this.dtKMDT.length == 0) {
                this.$toast.error("Không có dữ liệu để xuất")
                return
            }
            $('#export').click();
        },
        btnImportExcel_click() {
            this.btnLamMoi_click();
            document.getElementById("uploadFile").click()
        },
        async importExcel(e) {
            const files = e.target.files
            if (!files.length) {
                return
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                this.$toast.error("File excel không đúng định dạng!")
                return
            }
            this.readFile(files)
        },
        readFile(file) {
            const fileReader = new FileReader()
            fileReader.onload = (ev) => {
                try {
                    const data = ev.target.result
                    const XLSX = xlsx
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    })
                    const wsname = workbook.SheetNames[0]
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
                    const excellist = [];

                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                    }

                    this.dataImport = excellist
                    this.kiemtra_dulieu();
                } catch (error) {
                    this.$toast.error("Lỗi khi đọc file! " + error)
                }
            }

            fileReader.readAsBinaryString(file[0])
        },
        kiemtra_dulieu() {       
            let numRow = 1;  
            this.dataError = [];
            for (var i = 0; i < this.dataImport.length; i++) {
                try {               
                    if (this.dataImport[i].MA_TB == undefined || this.dataImport[i].SERIAL == undefined || this.dataImport[i].STB_TYPE == undefined ||
                        String(this.dataImport[i].MA_TB).trim() == "" || String(this.dataImport[i].SERIAL).trim() == "" || String(this.dataImport[i].STB_TYPE).trim()=="") {
                        var rsai = {};
                        rsai.MA_TB = this.dataImport[i].MA_TB != undefined ? String(this.dataImport[i].MA_TB).trim() : "";
                        rsai.SERIAL = this.dataImport[i].SERIAL != undefined ? String(this.dataImport[i].SERIAL).trim() : "";
                        rsai.STB_TYPE = this.dataImport[i].STB_TYPE != undefined ? String(this.dataImport[i].STB_TYPE).trim() : "";
                        rsai.GHICHU_LOI = "Mã mytv, số serial và stb type không thể trống";
                        this.dataError.push(rsai);
                        continue;
                    }

                    if (String(this.dataImport[i].STB_TYPE).trim() != "1" && String(this.dataImport[i].STB_TYPE).trim() != "2" && String(this.dataImport[i].STB_TYPE).trim() != "3") {
                        var rsai = {};
                        rsai.MA_TB = String(this.dataImport[i].MA_TB).trim();
                        rsai.SERIAL = String(this.dataImport[i].SERIAL).trim();
                        rsai.STB_TYPE = String(this.dataImport[i].STB_TYPE).trim();
                        rsai.GHICHU_LOI = "Stb type chỉ có thể là 1,2,3";
                        this.dataError.push(rsai);
                        continue;
                    }

                    const serialPatterns = "^[a-zA-Z0-9]*$";
                    if (!(String(this.dataImport[i].SERIAL).trim()).match(serialPatterns)) {
                        var rsai = {};
                        rsai.MA_TB = String(this.dataImport[i].MA_TB).trim();
                        rsai.SERIAL = String(this.dataImport[i].SERIAL).trim();
                        rsai.STB_TYPE = String(this.dataImport[i].STB_TYPE).trim();
                        rsai.GHICHU_LOI = "Số serial chỉ được A-Z,0-9";
                        this.dataError.push(rsai);
                        continue;
                    }

                    let count = 0;
                    for (let ex of this.dataImport) {
                        if (ex.MA_TB && String(ex.MA_TB).trim() == String(this.dataImport[i].MA_TB).trim()) {
                            count++;
                        }
                    }
                    if (count > 1) {
                        var rsai = {};
                        rsai.MA_TB = String(this.dataImport[i].MA_TB).trim();
                        rsai.SERIAL = String(this.dataImport[i].SERIAL).trim();
                        rsai.STB_TYPE = String(this.dataImport[i].STB_TYPE).trim();
                        rsai.GHICHU_LOI = "Trùng bản ghi";
                        this.dataError.push(rsai);
                    } else if (count == 1) {
                        var rData = {};
                        rData.STT = numRow++;
                        rData.MA_TB = String(this.dataImport[i].MA_TB).trim();
                        rData.SERIAL = String(this.dataImport[i].SERIAL).trim();
                        rData.STB_TYPE = String(this.dataImport[i].STB_TYPE).trim();
                        rData.GHICHU_LOI = "";
                        rData.STATUS = "0";
                        rData.STATUS_IMG = "<div class='image'><img style='mix-blend-mode: multiply; width: 20px !important; height: 20px !important' src='" + document.getElementById("imgWarning").src + "' /></div>";
                        rData.LOI = "";
                        this.dtKMDT.push(rData);
                    } else {
                        var rsai = {};
                        rsai.MA_TB = String(this.dataImport[i].MA_TB).trim();
                        rsai.SERIAL = String(this.dataImport[i].SERIAL).trim();
                        rsai.STB_TYPE = String(this.dataImport[i].STB_TYPE).trim();
                        rsai.GHICHU_LOI = "Không tìm thấy thuê bao";
                        this.dataError.push(rsai);
                    }
                } catch (e) {
                    this.$toast.error(e.message)
                }
            }
        
            if (this.dataError.length > 0) {
                this.dtKMDT = [];
                this.$bvModal.msgBoxConfirm("Có " + this.dataError.length + " bản ghi sai dữ liệu! Bạn có muốn xuất file excel không?", {
                    title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        document.getElementById("error").click();
                    }
                }); 
            }
        },
        btnXoaTB_click() {
            if (this.dtKMDT == undefined || this.dtKMDT == null) {
                this.$toast.error("Không lấy được dữ liệu danh sách thuê bao");
                return;
            }
            if (this.dtKMDT.length == 0) {
                this.$toast.error("Không có dữ liệu danh sách thuê bao");
                return;
            }
            if (this.dtKMDT_Selected.length == 0) {
                this.$toast.error("Bạn phải chọn thuê bao cần xóa");
                return;
            }
            
            for (let row of this.dtKMDT_Selected) {
                this.dtKMDT.splice(this.dtKMDT.findIndex(e => e.MA_TB == row.MA_TB), 1);
            }
            this.dtKMDT_Selected = []
        },
        async btnThucHien_click() {
            if (this.dtKMDT == undefined || this.dtKMDT == null) {
                this.$toast.error("Không lấy được dữ liệu danh sách thuê bao");
                return;
            }
            if (this.dtKMDT.length == 0) {
                this.$toast.error("Không có dữ liệu danh sách thuê bao");
                return;
            }
            if (this.dtKMDT_Selected == undefined || this.dtKMDT_Selected.length == 0) {
                this.$toast.error("Bạn phải chọn thuê bao để thực hiện");
                return;
            }

            let desc1 = this.kieu_thuc_hien == "2" ? "Kích hoạt" : (this.kieu_thuc_hien == "3" ? "Hoàn thiện" : "Kích hoạt và hoàn thiện");
            let desc2 = this.loai_hd == "1" ? "Dùng thử" : "Khuyến mại";
            this.$bvModal.msgBoxConfirm('Bạn có chắc chắn thực hiện ' + desc1 + ' cho hợp đồng ' + desc2 + ' không?', {
                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
            }).then(async v => {
                if (v) {
                    await this.THUCHIEN();
                }
            });        
        },
        async sp_kichhoat_hoanthien_kmstb(lstExecute) {
            let result = [];
            try {   
                const params = {
                    loaiMsin: Number(this.kieu_thuc_hien),
                    loaiHD: Number(this.loai_hd),
                    dsHD: lstExecute,
                }
                let response = await api.ur_001_sp_kichhoat_hoanthien_kmstb(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }                             
            } catch (e) {
                console.log(e);
            }
            return result;
        },
        async THUCHIEN() {
            this.loading(true);
            try {          
                let lstExecute = [];
                this.lstIndexExecute = [];
                for (let inx=0; inx<this.dtKMDT_Selected.length; inx++) {
                    let row = {};
                    row.STT = this.dtKMDT_Selected[inx].STT;
                    row.MA_TB = this.dtKMDT_Selected[inx].MA_TB;
                    row.SERIAL = this.dtKMDT_Selected[inx].SERIAL;
                    row.STB_TYPE = this.dtKMDT_Selected[inx].STB_TYPE;
                    lstExecute.push(row);
                    this.lstIndexExecute.push(Number(row.STT)-1);
                }

                let result = await this.sp_kichhoat_hoanthien_kmstb(lstExecute);
                for (let row of this.dtKMDT) {
                    for (let res of result) {
                        if (Number(row.STT) == Number(res.STT)) {
                            row.LOI = res.LOI;
                            row.STATUS = res.STATUS;
                            if (row.STATUS == "0") {
                                row.STATUS_IMG = "<div class='image'><img style='mix-blend-mode: multiply; width: 20px !important; height: 20px !important' src='" + document.getElementById("imgError").src + "' /></div>";
                            } else {
                                row.STATUS_IMG = "<div class='image'><img style='mix-blend-mode: multiply; width: 20px !important; height: 20px !important' src='" + document.getElementById("imgSuccess").src + "' /></div>";
                            }
                        }
                    }
                }
                
                this.$toast.success("Đã thực hiện xong");      
                this.refreshGrid();     
            } catch (ex) {
                this.$toast.error("Có lỗi [ERROR]" + ex);
            } finally {
                this.loading(false);
            }
        },
        refreshGrid() {
            let temp = this.dtKMDT[this.dtKMDT.length-1];
            this.dtKMDT.pop();
            this.dtKMDT.push(temp);        
        },
        grid_ActionComplete() {
            this.$refs.dtKMDT.$refs.grid.selectRows(this.lstIndexExecute);
        },
        tsbtnExcel_Click() {
            let worksheet = xlsx.utils.json_to_sheet(this.listFileMau)
            let workbook = xlsx.utils.book_new()
            xlsx.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            xlsx.writeFile(workbook, "fileDSTB.xlsx")
        },
    },
    mounted() {
        this.init();
    },
}
</script>