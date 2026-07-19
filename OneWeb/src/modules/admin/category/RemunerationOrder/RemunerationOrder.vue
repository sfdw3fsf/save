<template src="./RemunerationOrder.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import xlsx from "xlsx";
import api from "./RemunerationOrder.js";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import DanhSachChiTiet from "./DanhSachChiTiet.vue";
export default {
    name:'RemunerationOrder',
    components:{
        breadcrumb,
        DataGridCustom,
        DanhSachChiTiet
    },
    data() {
        return {
            header: {
                title: 'Lập lệnh chi trả thù lao',
                list: [
                    { name: 'Quản trị hệ thống', link: { name: 'Ui.cards' } },
                    { name: 'Lập lệnh chi trả thù lao', link: { name: 'Ui.cards' } },
                ]
            },
            ...columns,
            ds_import: [],
            ds_importError: [],
            ds_luotlenh: [],
            fieldnames: {
                MA_NV: "MA_NV",
                SO_VI: "SO_VI",
                SO_TIEN: "SO_TIEN",
                NOI_DUNG: "NOI_DUNG",
            },
            listFileMau: [
                {
                    MA_NV: "",
                    SO_VI: "",
                    SO_TIEN: "",
                    NOI_DUNG: ""
                },
            ],
            noidung: "",
            txtFilePath: "",
            dataImport: [],
            ds_chitiet: [],
        }
    },
    mounted() {
        this.btnLayDS_Click()
    },
    methods: {
        async btnLayDS_Click() {
            try {
                this.loading(true)
                await this.layds_luot_cttl_dachuyen();
            } catch (e) {
                console.log(e)
                this.$toast.error("Xảy ra lỗi: " + e)
            } finally {
                this.loading(false)
            }         
        },
        async layds_luot_cttl_dachuyen() {
            this.ds_luotlenh = [];
            try {   
                let response = await api.ur_11100_001_layds_luot_cttl_dachuyen(this.axios)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_luotlenh = response.data.data
                    for (let i=0; i<this.ds_luotlenh.length; i++) {
                        let id = "onViewCT_" + this.ds_luotlenh[i].luotcttl_id
                        this.ds_luotlenh[i].view_ct = "<button id='"+id+"' class='btn btn-main lh14 pad4'><span class='-ap icon-more_horiz'></span></button>"
                    }
                }                             
            } catch (e) {
                console.log(e);
            }
        },
        gridDSLL_actionComplete() {
            if (this.ds_luotlenh.length > 0) {
                for (let i=0; i<this.ds_luotlenh.length; i++) {
                    let id = "onViewCT_" + this.ds_luotlenh[i].luotcttl_id
                    var button = document.getElementById(id);
                    if (button != null && button != undefined) {
                        button.addEventListener("click", onViewCT)
                    }               
                }
            }
        },
        resetData() {
            this.dataImport = []
            this.ds_import = []
            this.ds_importError = []
            this.txtFilePath = ""
        },
        btnImportExcel_click() {
            this.resetData()
            document.getElementById("uploadFile").click()
        },
        importExcel(e) {
            const files = e.target.files
            if (!files.length) {
                return
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                this.$toast.error("File không đúng định dạng, vui lòng tải lại file mẫu")
                return
            }

            this.txtFilePath = files[0].name
            this.readFile(files)        
        },
        readFile(file) {   
            const fileReader = new FileReader()
            fileReader.onload = (ev) => {
                try {
                    this.loading(true)
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
                    this.fn_kiemtra_dl_laplenh_chithulao(this.dataImport)
                } catch (error) {
                    this.$toast.error("Lỗi khi đọc file! " + error)
                } finally {
                    this.loading(false)
                }  
            }

            fileReader.readAsBinaryString(file[0])
        },
        async fn_kiemtra_dl_laplenh_chithulao(dataImport) {
            try {   
                let params = {
                    "js_taplenh_data": JSON.stringify(dataImport)
                }
                let response = await api.ur_11100_002_fn_kiemtra_dl_laplenh_chithulao(this.axios, params)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    let result = JSON.parse(response.data.data)
                    if (result.ERROR_CODE == 1) {
                        this.ds_import = result.RESULT.DS_HOPLE
                        this.ds_importError = result.RESULT.DS_LOI
                    }                 
                }                             
            } catch (e) {
                console.log(e);
            }
        },
        async CHUYEN_DUYET_LENH_CHITHULAO(ghichu, ds_import) {
            let result = ""
            try {   
                let params = {
                    "ghichu": ghichu,
                    "js_taplenh_data": JSON.stringify(ds_import)
                }
                let response = await api.ur_11100_003_fn_chuyen_duyet_lenh_chithulao(this.axios, params)
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = (response.data.data != undefined && response.data.data != "") ? response.data.data : response.data.message
                }                           
            } catch (e) {
                console.log(e);
            }
            return result
        },
        async tsbtnXacNhan_Click() {
            try {
                this.loading(true)
                if (this.ds_import.length <= 0) {
                    this.$toast.error("Danh sách trống, không có dữ liệu để cập nhật!");
                    return;
                }

                if (this.noidung == null || this.noidung == "") {
                    this.$toast.error("Chưa nhập nội dung");
                    return;
                }

                let vkq = await this.CHUYEN_DUYET_LENH_CHITHULAO(this.noidung, this.ds_import);
                if (vkq == "OK") {
                    this.$toast.success("Chuyển duyệt thành công!");
                    this.resetData()            
                    this.noidung = ""
                    await this.layds_luot_cttl_dachuyen();
                } else {
                    this.$toast.error(vkq);
                }
            } catch (e) {
                console.log(e)
                this.$toast.error("Lỗi khi cập nhật kết quả: " + e)
            } finally {
                this.loading(false)
            }
        },
        async onViewChiTiet() {
            try {
                this.loading(true)
                await this.lay_chitiet_luot_cttl($("#ct_luotlenh").val())
                this.$refs.danhsachchitiet.show();
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async lay_chitiet_luot_cttl(luotcttl_id) {
            this.ds_chitiet = []
            try {   
                let params = {
                    "luotcttl_id": luotcttl_id
                }
                let response = await api.ur_11100_008_lay_chitiet_luot_cttl(this.axios, params)
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_chitiet = response.data.data.ds2
                }                        
            } catch (e) {
                console.log(e);
            }
        },
        tsbtnExcel_Click() {
            let worksheet = xlsx.utils.json_to_sheet(this.listFileMau)
            let workbook = xlsx.utils.book_new()
            xlsx.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            xlsx.writeFile(workbook, "Bieumau_chitra_thulao.xlsx")
        },
    },
}

function onViewCT(e) {
    let ma_luot = ""
    for (let i=0; i<e.path.length; i++) {
        if (e.path[i].id != undefined && e.path[i].id != null && e.path[i].id != "") {
            ma_luot = e.path[i].id.split("_")[1]
            break
        }
    }
    $("#ct_luotlenh").val(ma_luot)
    document.getElementById("viewct").click()
}

</script>