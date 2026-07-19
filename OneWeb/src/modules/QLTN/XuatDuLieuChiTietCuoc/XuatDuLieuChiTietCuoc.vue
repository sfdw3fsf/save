<template src="./XuatDuLieuChiTietCuoc.html"></template>
<style src="./XuatDuLieuChiTietCuoc.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import API from '../api/XuatDuLieuChiTietCuoc.js'
import XLSX from 'xlsx'
import { required, maxLength } from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { currency } from "@/filters/currency";
export default {
    components: { 
        breadcrumb,
        XLSX,
        BssRequiredMarker,
    },
    data() {
        return {
            header: {
                title: "XUẤT DỮ LIỆU CHI TIẾT CƯỚC",
                list: [
                    { name: "Hoá đơn", link: { name: "Ui.cards" } },
                    {
                        name: "Xuất dữ liệu chi tiết cước", 
                        link: { name: "Ui.cards" }
                    }
                ]
            },
            fields: [
                {
                    fieldName: 'MA_TT',
                    headerText: 'Mã TT',
                    allowFiltering: true,
                },
                {
                    fieldName: 'CHUGOI',
                    headerText: 'Chủ gọi',
                    allowFiltering: true,
                },
                {
                    fieldName: 'BIGOI',
                    headerText: 'Bị gọi',
                    allowFiltering: true,
                },
                {
                    fieldName: 'GIO_BD',
                    headerText: 'Giờ bắt đầu',
                    allowFiltering: true,
                },
                {
                    fieldName: 'TG_GOI',
                    headerText: 'Thời gian gọi',
                    allowFiltering: true,
                    cssClass: 'text-right'
                },
                {
                    fieldName: 'TIEN',
                    headerText: 'Tiền',
                    allowFiltering: true,
                    cssClass: 'text-red',
                    isCurrency: true,
                },
                {
                    fieldName: 'TENHUONG',
                    headerText: 'Tên hướng',
                    allowFiltering: true,
                },
            ],
            isGetFile: false,
            isGrid: true,
            kyHD: null,
            maTT: "",
            dataSelect: {
                kyCuoc: "",
                verId: 0,
                invId: 33,
                params: [],
            },
            dataGrid: [],
            dataExport: [],
            checkFile: true,
        }
    },
    methods: {
        layDuLieu() {
            this.dataGrid = []
            this.dataExport = []
            if (this.kyHD == null) {
                this.$toast.error("Chưa có kỳ hoá đơn!")
                return
            }
            if (!this.isGetFile) {
                if (this.maTT == "") {
                    this.$toast.error("Chưa có mã thanh toán!")
                    return
                } else {
                    this.dataSelect.params = []
                    this.dataSelect.params.push({
                        maTT: this.maTT
                    })
                }
            } else if (this.$refs.fileInput.value == "" || this.checkFile == false) {
                this.$toast.error("Chưa chọn file!")
                return
            }
            this.dataSelect.kyCuoc = moment(this.kyHD).format("yyyyMM") + "01"
            this.$root.loading(true)
            API.getDanhSachChiTiet(this.axios, this.dataSelect).then(response => {
                // this.dataExport = response.data.data
                for (let i=0; i < response.data.data.length; i++) {
                    let element = response.data.data[i]
                    this.dataExport.push({
                        MA_TT: element.MA_TT,
                        CHUGOI: element.CHUGOI,
                        BIGOI: element.BIGOI,
                        GIO_BD: element.GIO_BD,
                        TG_GOI: element.TG_GOI != 0 ? element.TG_GOI : null,
                        TIEN: currency(element.TIEN),
                        TENHUONG: element.TENHUONG
                    })
                }
                if (this.dataExport != null && this.dataExport.length > 0) {
                    // let data = XLSX.utils.json_to_sheet(this.dataExport)
                    // let wb = XLSX.utils.book_new() // make Workbook of Excel
                    // XLSX.utils.book_append_sheet(wb, data, 'ds-chitiet-cuoc') // sheetAName is name of Worksheet
                    // export Excel file
                    // XLSX.writeFile(wb, 'ds-chitiet-cuoc.xlsx')

                    let excelExportProperties = {
                        fileName:"ds-chitiet-cuoc.xlsx",
                        dataSource: this.dataExport,
                    }
                    this.$refs.chiTietCuoc.excelExport(excelExportProperties);
                } else this.$toast.error('Không có dữ liệu!')
                
                if (this.isGrid) {
                    this.dataGrid = response.data.data
                }
                this.$root.loading(false)
            }).catch(error => {
                console.log(error)
                this.$toast.error('Không có dữ liệu!')
                this.$root.loading(false)
            })    
        },
        gridViewControll_ExcelQueryCellInfo(args) {
            if (args.column.field === "TIEN") {
                args.style = {fontColor: 'FF0000', bold: true};
            }
        },
        gridViewControll_QueryCellInfo(args) {
            if (args.column.field === "TIEN") {
                args.cell.innerText = currency(args.data.TIEN);
            }
            if (args.column.field === "TG_GOI" && args.data.TG_GOI == 0) {
                args.data.TG_GOI = null
            }
        },
        importExcel(e) {
            const files = e.target.files;
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                this.checkFile = false
                this.dataGrid = []
                this.dataExport = []
                return  this.$root.toastError("File không đúng định dạng xls hoặc xlsx!");
            }
            this.readFile(files)
        },
        readFile(file) {
            const fileReader = new FileReader()
            fileReader.onload = (ev) => {
                try {
                    const data = ev.target.result;
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    });
                    const wsname = workbook.SheetNames[0];
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
                    const excellist = []
                    let ds = []

                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i])
                        ds.push({
                            maTT: ws[i].MA_TT,
                        })
                    }
                    if (excellist.length == 0 || !excellist[0].hasOwnProperty("MA_TT")) {
                        this.$root.toastError("File cần có chính xác 1 cột MA_TT!");
                        this.dataGrid = []
                        this.dataExport = []
                        this.checkFile = false
                        return;
                    }

                    this.checkFile = true
                    this.dataSelect.params = ds
                } catch (error) {
                    this.checkFile = false
                    return alert("Read failure! " + error)
                }
            }
            fileReader.readAsBinaryString(file[0])
        },
        TaiFileMau() {
            this.loading(true);
            try {
                const temp = [{ MA_TT: "" }];
                var dsach = XLSX.utils.json_to_sheet(temp);
                var wb = XLSX.utils.book_new(); // make Workbook of Excel
                XLSX.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
                // export Excel file
                XLSX.writeFile(wb, "Bieumau_xuatdulieuchitietcuoc.xlsx");
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
    },
}
</script>
