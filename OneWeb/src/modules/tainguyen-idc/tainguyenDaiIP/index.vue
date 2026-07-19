<template src="./index.template.html"></template>

<script>
//import Vue from 'vue';
import XLSX from "xlsx";

export default {
    name: 'TaiNguyenDaiIP',
    components: {
        
    },
    data() {
        return {
            dsMatsan: [],
            isError: false,
            dsTaiNguyenDaiIP: [],
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isDisableBtnImport: false,
            isDisableBtnExport: false,
            form: {
                pIDCId: 0,
                pIDCTen: "",
                pMatsanId: 0,
                pMatsanTen: "",
                pToadoMatsanId: "",
                pToadoMatsanTen: "",
                pTruc_Ngang: "",
                pTruc_Doc: 0,
                pGhiChu: "",
                pHieuLuc: 1
            },
            selectionOptions: {
                type: 'Single',      // chỉ chọn 1 dòng
                mode: 'Row',         // chọn theo dòng
                checkboxOnly: false, // không cần checkbox
            },
            showImportDialog: false,
            selectedFile: null,
            importError: "",
            importSuccess: "",
            fileError: "",
            dsMatsanError: []
        }
    },
    created: async function () {
        this.isError = false;   
        try {
            this.loading(true);
            rs = await this.$root.context.post('/ToadoMatsan/DanhSachMatSan');
            this.dsMatsan = rs.data;
            var rs = await this.$root.context.post('/ToadoMatsan/DanhSach');
            this.dsTaiNguyenDaiIP = rs.data;
        } catch (error) {
            this.$root.toastError('Xảy ra lỗi')
        } finally {
            this.loading(false);
        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa, isImport, isExport) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
            this.isDisableBtnImport = isImport;
            this.isDisableBtnExport = isExport;
        },
        onSelectMatsan(selected) {
            this.form.pMatsanId = selected.id;
            this.form.pMatsanTen = selected.text;
            this.form.pIDCId = selected.idc_id;
            this.form.pIDCTen = selected.idc_ten;
            // this.form.pToadoMatsanTen = this.form.pIDCTen + '_' + this.form.pMatsanTen + '_' + this.form.pTruc_Doc + '_' + this.form.pTruc_Ngang;
        },
        onSelectRowChanged(selected) {
            console.log(selected);
            this.isDisableSave = true;
            this.isDisableUpdate = false;
            this.form.pIDCId = selected.data.IDC_ID;
            this.form.pIDCTen = selected.data.IDC_TEN;
            this.form.pMatsanId = selected.data.MATSAN_ID;
            this.form.pMatsanTen = selected.data.MATSAN_TEN;
            this.form.pToadoMatsanId = selected.data.TOADO_MATSAN_ID;
            this.form.pToadoMatsanTen = selected.data.TEN;
            this.form.pTruc_Ngang = selected.data.TRUC_NGANG;
            this.form.pTruc_Doc = selected.data.TRUC_DOC;
            this.form.pGhiChu = selected.data.GHI_CHU;
            this.form.pHieuLuc = selected.data.HIEU_LUC_TEXT == "Hiệu lực" ? 1 : 0;
            this.setDisableBtn(true, false, false, false, true, true);
        },
        checkForm: function() {
            if(this.form.pMatsanId == 0) {
                this.$root.toastError('Vui lòng chọn mặt sàn.');
                return false;
            }
            if(this.form.pTruc_Ngang == 0) {
                this.$root.toastError('Vui lòng Nhập Tọa độ trục ngang.');
                return false;
            }
            if(this.form.pTruc_Doc == 0) {
                this.$root.toastError('Vui lòng Nhập Tọa độ trục dọc.');
                return false;
            }
            this.form.pToadoMatsanTen = this.form.pIDCTen + '_' + this.form.pMatsanTen + '_' + this.form.pTruc_Doc + '_' + this.form.pTruc_Ngang;
            if(this.form.pToadoMatsanId == "") {
                const row = this.dsTaiNguyenDaiIP.filter(item => item.TEN.toUpperCase() == this.form.pToadoMatsanTen.toUpperCase())
                if (row.length > 0) {
                    this.$toast.error('Xảy ra lỗi: Tên đã tồn tại (' + this.form.pToadoMatsanTen + ')')
                    return false
                }
            }
            return true;
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.isError = false;
            this.form = {
                pIDCId: 0,
                pIDCTen: "",
                pMatsanId: 0,
                pMatsanTen: "",
                pToadoMatsanId: "",
                pToadoMatsanTen: "",
                pTruc_Ngang: "",
                pTruc_Doc: "",
                pGhiChu: "",
                pHieuLuc: 1
            };
            this.setDisableBtn(true, false, false, true, true, true);
        },
        ghiLai: async function() {
            if(this.isDisableBtnGhiLai) {
                return;
            }
            if(this.checkForm()) {
                const payload = {
                    matsanId: this.form.pMatsanId,
                    matsanTen: this.form.pMatsanTen,
                    toadoMatsanId: this.form.pToadoMatsanId,
                    toadoMatsanTen: this.form.pToadoMatsanTen,
                    trucNgang: this.form.pTruc_Ngang,
                    trucDoc: this.form.pTruc_Doc,
                    ghiChu: this.form.pGhiChu,
                    hieuLuc: this.form.pHieuLuc ? 1 : 0
                };
                if(this.form.pToadoMatsanId != "") {
                    try {
                        var rs = await this.$root.context.post('/ToadoMatsan/CapNhat', payload);
                        const idx = this.dsTaiNguyenDaiIP.findIndex(r => r.TOADO_MATSAN_ID === rs.data.TOADO_MATSAN_ID);
                        if (idx >= 0) {
                            this.$set(this.dsTaiNguyenDaiIP, idx, rs.data);
                        }
                        this.$toast.success('Cập nhật thành công');
                        this.isError = false;
                    } catch (error) {
                        const detail =
                            error?.response?.data?.message_detail ||
                            error?.response?.data?.message ||
                            error?.message ||
                            'Có lỗi xảy ra';
                        this.$root.toastError('Xảy ra lỗi: ' + detail);
                        return;
                    }
                }
                else {
                    try {
                        var rs = await this.$root.context.post('/ToadoMatsan/Them', payload);
                        if (rs && rs.data) {
                            this.dsTaiNguyenDaiIP = [rs.data, ...this.dsTaiNguyenDaiIP];
                        }
                        this.$toast.success('Thêm mới thành công');
                        this.isError = false;
                    } catch (error) {
                        const detail =
                            error?.response?.data?.message_detail ||
                            error?.response?.data?.message ||
                            error?.message ||
                            'Có lỗi xảy ra';
                        this.$root.toastError('Xảy ra lỗi: ' + detail);
                        return;
                    }
                }
                this.huy();
            }
            // this.setDisableBtn(false, true, true, false, false, false);
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
                size: 'sm',
                centered: true,
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy'
            })
            .then(async val => {
                if(val) {
                    if(this.form.pToadoMatsanId != "") {
                        try {
                            var rs = await this.$root.context.post(`/ToadoMatsan/Xoa/${this.form.pToadoMatsanId}`);
                            if(rs.data.result) {
                                this.dsTaiNguyenDaiIP = this.dsTaiNguyenDaiIP.filter(item => item.TOADO_MATSAN_ID != this.form.pToadoMatsanId);
                                this.$toast.success('Xóa thành công');
                                this.huy();
                            }
                        } catch (error) {
                            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Không thể xóa bản ghi này');
                        }
                    }
                    else {
                        this.$root.toastError('Vui lòng chọn Tọa độ mặt sàn để xóa');
                    }
                }
            })
            .catch(err => {
                this.$root.toastError('Xảy ra lỗi');
            })
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            this.form = {
                pToadoMatsanId: "",
                pToadoMatsanTen: "",
                pMatsanId: 0,
                pMatsanTen: "",
                pTenToaDo: "",
                pTruc_Ngang: "",
                pTruc_Doc: "",
                pGhiChu: "",
                pHieuLuc: 0
            };
            this.isError = false;
            this.setDisableBtn(false, true, true, true, false, false);
        },
        taiFileMau() {
            window.location.href = 'static/files/Mau_file_Import_Toa_do_mat_san.xlsx';
        },
        importFile() {
            this.importError = "";
            this.importSuccess = '';
            this.fileError = "";
            this.selectedFile = null;
            this.showImportDialog = true;
        },
        async doImport() {
            if (!this.selectedFile) {
                this.importError = "Vui lòng chọn file Excel!";
                this.importSuccess = '';
                this.fileError = "";
                this.dsMatsanError = [];
                return;
            }
            const allowedTypes = [
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-excel"
            ];
            if (!allowedTypes.includes(this.selectedFile.type)) {
                this.importError = "Định dạng file không hợp lệ. Chỉ chấp nhận .xls, .xlsx";
                this.importSuccess = '';
                this.fileError = "";
                return;
            }
            try {
                const data = await this.readFileAsArrayBuffer(this.selectedFile);
                const workbook = XLSX.read(new Uint8Array(data), { type: "array" });
                const sheetName = workbook.SheetNames[0];
                const sheet = workbook.Sheets[sheetName];
                const jsonData = XLSX.utils.sheet_to_json(sheet, { defval: "" });
                if (jsonData.length === 0) {
                    this.importError = "File không có dữ liệu hoặc thiếu header!";
                    this.importSuccess = '';
                    this.fileError = "";
                    return;
                }

                const headerMap = {
                    "Tên Mặt sàn (từ Danh mục Mặt sàn)": "matsanTen",
                    "Tọa độ trục dọc (Từ 00 đến 99 Giới hạn 2 ký tự)": "trucDoc",
                    "Tọa độ trục ngang (AA,AB,AC,… Giới hạn 2 ký tự)": "trucNgang",
                    "Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)": "hieuLuc"
                };
                const mappedData = jsonData.map(row => {
                    const obj = {};
                    const hasError = false;

                    for (const key in row) {
                        const normalizedKey = key.replace(/\s+/g, " ").trim();  
                        if (headerMap[normalizedKey]) {
                            obj[headerMap[normalizedKey]] = row[key];
                        }
                        else {
                            hasError = true;
                        }
                    }
                    if(hasError) 
                        return null;
                    return obj;
                });

                if(!mappedData) {
                    this.importError = "Dữ liệu không hợp lệ!";
                    this.importSuccess = '';
                    this.fileError = "";
                    return;
                }
                this.parsedData = mappedData;
                // console.log(this.parsedData);
                try {
                    var rs = await this.$root.context.post('/ToadoMatsan/Import', this.parsedData);
                    if(rs.data) {
                        // console.log(rs.data);
                        // this.dsTaiNguyenDaiIP = this.dsTaiNguyenDaiIP.filter(item => item.TOADO_MATSAN_ID != this.form.pToadoMatsanId);
                        this.importSuccess = 'Số dòng import thành công: ' + rs.data.success;
                        if(rs.data.failed > 0){
                            this.importError = 'Số dòng import lỗi: ' + rs.data.failed;
                            this.dsMatsanError = rs.data.errors;
                            this.fileError = "true";
                        }
                        return;
                        // this.huy();
                    }
                } catch (error) {
                    // this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Không thể xóa bản ghi này');
                }
                // await this.$axios.post("/api/toado-matsan/import", this.parsedData);
                this.showImportDialog = false;
            } catch (err) {
                this.importError = "File Excel không hợp lệ!";
                this.importSuccess = '';
                this.fileError = "";
            }
        },
        readFileAsArrayBuffer(file) {
            return new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.onload = e => resolve(e.target.result);
                reader.onerror = e => reject(e);
                reader.readAsArrayBuffer(file);
            });
        },
        xuatExcel: function () {
            try {
                if (this.dsTaiNguyenDaiIP.length == 0) {
                    this.$toast.error("Không có dữ liệu để xuất Excel !");
                    return;
                }

                var datasource = this.dsTaiNguyenDaiIP.map(item => {
                    return {
                    "Tên Mặt sàn": item.MATSAN_TEN,
                    "Tên Tọa độ Mặt sàn": item.TEN,
                    "Tọa độ trục dọc": item.TRUC_DOC,
                    "Tọa độ trục ngang": item.TRUC_NGANG,
                    "Hiệu lực": item.HIEU_LUC_TEXT
                    }
                })

                var toado_matsan = XLSX.utils.json_to_sheet(datasource);
                var wb = XLSX.utils.book_new(); // make Workbook of Excel
                XLSX.utils.book_append_sheet(wb, toado_matsan, "Tọa độ mặt sàn");
                toado_matsan['!cols'] = [
                  { wch: 30 }, { wch: 60 }, { wch: 20 }, { wch: 20 }, { wch: 20 }
                ];
                XLSX.writeFile(wb, "Toa do mat san.xlsx");
                
            } catch (ex) {
                this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex);
            }
        },
        taiFileError: function () {
            try {

                var datasource = this.dsMatsanError.map(item => {
                    return {
                    "Tên Mặt sàn (từ Danh mục Mặt sàn)": item.input.matsanTen,
                    "Tọa độ trục dọc (Từ 00 đến 99 Giới hạn 2 ký tự)": item.input.trucDoc,
                    "Tọa độ trục ngang (AA,AB,AC,… Giới hạn 2 ký tự)": item.input.trucNgang,
                    "Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)": item.input.hieuLuc,
                    "Nội dung lỗi": item.errorMessage,
                    }
                })

                var toado_matsan = XLSX.utils.json_to_sheet(datasource);
                var wb = XLSX.utils.book_new(); // make Workbook of Excel
                XLSX.utils.book_append_sheet(wb, toado_matsan, "Tọa độ mặt sàn");
                toado_matsan['!cols'] = [
                  { wch: 30 }, { wch: 30 }, { wch: 30 }, { wch: 30 }, { wch: 50 }
                ];
                XLSX.writeFile(wb, "Toa do mat san Loi.xlsx");
                
            } catch (ex) {
                this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex);
            }
        },
    },
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

</style>
