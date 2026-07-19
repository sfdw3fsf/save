<template>
    <b-modal
        id="modal-docfile"
        size="lg"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        @hide="resetData"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Đọc file in danh sách xử lý nợ
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="close()"
                ></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li @click="DocFile2()">
                        <a href="javascript:;">
                            <span
                                class="icon design_bullet-list-67 nc-icon-glyph"
                            ></span>
                            Danh sách
                        </a>
                    </li>
                    <li>
                        <a
                            href="javascript:void(0)"
                            @click="ChapNhan()"
                            :class="{
                                disabled: checkLoi || dsLoi.length === 0
                            }"
                        >
                            <span
                                class="icon ui-1_check-circle-08 nc-icon-glyph"
                            ></span>
                            Chấp nhận
                        </a>
                    </li>
                    <li @click="XuatExcel()">
                        <a href="javascript:;">
                            <span class="icon one-excel"></span> Xuất Excel
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="note text-main">
                        <span class="one-alert f20 text-warning"></span> File excel gồm 2 trường: STT, MA_XLN. Viết hoa, không dấu, bỏ dòng tiêu đề
                    </div>
                    <!-- <div class="info-row">
                        <div class="key w90">Dịch vụ</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control" disabled>
                                    <option value="dichVu.id">{{
                                        dichVu.name
                                    }}</option>
                                </select>
                            </div>
                        </div>
                    </div> -->
                    <div class="info-row">
                        <div class="key w90">Đường dẫn</div>
                        <div class="value">
                            <div class="input-more-button">
                                <label  class="btn">
                                    <span class="-ap icon-more_horiz"></span>
                                    <input
                                        ref="fileInput"
                                        type="file"
                                        id="upload"
                                        style="display: none"
                                        @change="onLoadFile2"
                                        accept=".xls, .xlsx"
                                        @click="$refs.fileInput.value = null"
                                    />
                                </label>
                                <input
                                    autofocus
                                    ref="duongDan"
                                    v-model="duongDan"
                                    type="text"
                                    class="form-control highlight"
                                />
                            </div>
                        </div>
                    </div>
                </div>
                <!-- @click="selectedTab('tabPhieuChuaGiao')" -->
                <div class="box-form">

                     <div class="nav tabs tab-over">
                    <a href="#tabDanhSach"  data-toggle="tab" @click="selectedTab('tabDanhSach')" class="active">Danh sách</a>
                    <a href="#tabLoi"  data-toggle="tab" @click="selectedTab('tabLoi')">Lỗi</a>
                    <a href="#tabNhanVien"  data-toggle="tab" @click="selectedTab('tabNhanVien')">Nhân viên</a>
                </div>

                    <div class="tab-content">
                        <div class="tab-pane active" id="tabDanhSach">
                            <DataGrid
                                :columns="colDanhSachImport"      
                                v-bind:dataSource="dsFile"
                            ></DataGrid>
                        </div>
                        <div class="tab-pane " id="tabLoi">
                            <DataGrid
                                :columns="colDanhSachLoi"                                                        
                                v-bind:dataSource="dsLoi"
                            ></DataGrid>
                        </div>
                        <div class="tab-pane " id="tabNhanVien">
                            <DataGrid
                                v-bind:columns="colDanhSachNhanVien"
                                v-bind:dataSource="dsNhanVien"
                            ></DataGrid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";

import { danhSachNhanVien,danhSachImport,danhSachLoi } from "./common";

export default {
    name: "PopupDocFile",
    mounted() {},  
   props: ["kieu","kycuoc"],
    data() {
        return {            
            checkLoi: false,
            colDanhSachNhanVien:danhSachNhanVien,
            colDanhSachImport:danhSachImport,
            colDanhSachLoi:danhSachLoi,
            sourceFile :null,
            duongDan :'',
            dsLoi : [],
            dsFile:[],
            dsNhanVien:[],
        };
    },    
    methods: {
       
        close() {
            this.$bvModal.hide("modal-docfile");
        },

        XuatExcel() {
            if (this.dsLoi.length <= 0) {
                this.$root.toastError("Chưa có danh sách để xuất Excel!");
                return;
            }
            this.loading(true);
            try {
                var dsach = xlsx.utils.json_to_sheet(this.dsLoi);
                var wb = xlsx.utils.book_new(); // make Workbook of Excel
                xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
                // export Excel file
                xlsx.writeFile(wb, "mau.xlsx");
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },

        DocFile2() {
            if (this.duongDan === "") {
                this.$root.toastError("Hãy chọn đường dẫn!");
                return;
            }
            this.readFile(this.sourceFile);
        },

        onLoadFile2(e) {
            this.sourceFile = e;
            this.duongDan = e.target.files[0].name;
            this.dsLoi = [];
            this.DocFile2();
        },
        async ChapNhan() {
            if (this.dsFile.length>0)
                if (this.dsLoi.length!=0)
                    await this.$bvModal
                            .msgBoxConfirm('File excel đang có lỗi, chương trình sẽ thao tác trên các row dữ liệu đúng. Bạn có thật sự muốn chấp nhận?', {
                                title: "Thông báo",
                                size: "lg",
                                buttonSize: "lg",
                                okVariant: "primary",
                                okTitle: "Đồng ý",
                                cancelTitle: "Hủy bỏ",
                                hideHeaderClose: false,
                                centered: true,
                                "modal-class": ["f18", "text-center"],
                            })
                            .then(async (value) => {
                                this.$emit("dsTheoFile", this.dsFile);            
                                this.$bvModal.hide("modal-docfile");
                            })
                            .catch((err) => {
                                console.log(err);
                            });
                else
                {
                    this.$emit("dsTheoFile", this.dsFile);            
                    this.$bvModal.hide("modal-docfile");
                }          
            else
               return this.$root.toastError("Bạn chưa chọn dữ liệu");
                        

        },
        async readFile(e) {
                const files = e.target.files;
                this.dsFile=[];
                this.dsLoi=[];

                if (!files.length) {
                    return;
                } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                    return this.$root.toastError("File excel không đúng định dạng!");
                }
                const fileReader = new FileReader();
                fileReader.onload = async (ev) => {
                    try {
                    const data = ev.target.result;
                    const XLSX = xlsx;
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    });
                    const wsname = workbook.SheetNames[0];
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
                    const excellist = [];
                    let dsMaXLN = [];     
                    let dsDuplicateMaXLN=[];    
                    let kiemtra=false;
                    // Edit data
                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                        dsMaXLN.push(ws[i].MA_XLN);
                    }
                    let dsLoiDB = await this.kiemtra_maxln(excellist);                    
                    var propCount = 0;
                    if (excellist && excellist.length > 0) {
                        for (var prop in excellist[0]) {
                        if (excellist[0].hasOwnProperty(prop)) propCount++;
                        }
                    }

                    if (propCount != 2) {
                        this.$root.toastError(
                        "Số lượng cột phải = 2 cột, hiện đang có " + propCount + " cột!"
                        );
                        return;
                    }

                    if (!excellist[0].hasOwnProperty("MA_XLN")) {
                        this.$root.toastError("File cần có chính xác có cột MA_XLN!");
                        return;
                    }
                    dsMaXLN.forEach((element) => {
                        if (
                        dsMaXLN.indexOf(element) != dsMaXLN.lastIndexOf(element) 
                        && !dsDuplicateMaXLN.includes(element)
                        )                        
                        {
                            dsDuplicateMaXLN.push(element);
                            
                        } 
                        });
                    
                    excellist.forEach((element)=>{
                        if (dsDuplicateMaXLN.includes(element.MA_XLN))
                            this.dsLoi.push({"STT":element.STT,"MA_XLN":element.MA_XLN,"TRANGTHAI":"Trùng dữ liệu"})
                        else
                        {
                            kiemtra=false;
                            dsLoiDB.forEach(item=>{
                                if (item.MA_XLN==element.MA_XLN)
                                    kiemtra=true;                                
                            })
                            if (!kiemtra)
                                this.dsFile.push(element)                        

                        }
                            
                        })
                    dsLoiDB.forEach(item=>{
                        this.dsLoi.push({"STT":item.STT,"MA_XLN":item.MA_XLN,"TRANGTHAI":item.TRANGTHAI})
                        })
                    //this.dsLoi=[this.dsLoi,dsLoiDB.map(item=>{return {"STT":item.STT,"MA_XLN":item.MA_XLN,"TRANGTHAI":item.TRANGTHAI}})]
                    await this.layDSNhanVien(this.dsFile);
                    } catch (e) {
                     this.$root.toastError("Kiểm tra dữ liệu file đầu vào!")
                     return;
                    }
                };
                
                fileReader.readAsBinaryString(files[0]);
        },
        selectedTab(value) {
            switch (value) {
                case "tabDanhSach":                    
                    break;
                case "tabLoi":                    
                    break;
                case "tabNhanVien":                    
                    break;                    
            }
        },
        layDSNhanVien: async function(data){
            let dsPhieuFile=[];
                data.forEach(item=>{
                    dsPhieuFile.push(item.MA_XLN);
                });
            let param={dsMaTT:dsPhieuFile}
               const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/quan-ly-dai-ly/ds-nhan-vien-theo-ds-ma-tt`,param 
                );
                if (rs.data.length>0)                            
                    this.dsNhanVien=rs.data;   
                //console.log(this.dsNhanVien)     
        },
        kiemtra_maxln : async function(data){
            let ds=[];
            
            data.forEach((item)=>{                
                ds.push({"maXLN":item.MA_XLN,"stt": item.STT});
                        });            
             try {
                 let param={                     
                        kyCuoc: this.kycuoc,
                        kieuXL: 1,
                        file: ds            
                 };
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/xu-ly-no/kiem-tra-xu-ly-no`,param 
                );
                if (rs.data.length>0)                            
                    return rs.data;                    
                else
                return [];    
            } catch (error) {}
            },
        resetData() {
            this.duongDan = "";
            this.dsLoi = [];
        }
    }
};
</script>
