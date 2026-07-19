<template src="./index.template.html"></template>

<script>
//import Vue from 'vue';
import XLSX from "xlsx";

export default {
    name: 'ToaDoMatSan',
    components: {
        
    },
    data() {
        return {
            dsIdc: [],
            dsToanha: [],
            dsToanhaFilter: [],
            dsMatsan: [],
            dsMatsanFilter: [],
            isError: false,
            // selectedIDC: null,
            // selectedToaNha: null,
            dsToaDoMatSan: [],
            selectedRows: [],
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: false,
            isDisableBtnImport: false,
            isDisableBtnExport: false,
            isDisableBtnEdit: false,
            form: {
                pIDCId: 0,
                pIDCTen: "",
                pToanhaId: 0,
                pToanhaTen: "",
                pMatsanId: 0,
                pMatsanTen: "",
                pToadoMatsanId: 0,
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
            rs = await this.$root.context.post('web-ecms/idc/ToadoMatsan/DanhSachIDC');
            this.dsIdc = rs.data;
            rs = await this.$root.context.post('web-ecms/idc/ToadoMatsan/DanhSachToaNha');
            this.dsToanha = rs.data;
            rs = await this.$root.context.post('web-ecms/idc/ToadoMatsan/DanhSachMatSan');
            this.dsMatsan = rs.data;
            var rs = await this.$root.context.post('web-ecms/idc/ToadoMatsan/DanhSachToaDoMatSan');
            this.dsToaDoMatSan = rs.data;
            // await this.loadIdcBuildings()
            // let rawList = await this.getList()
        } catch (error) {
            this.$root.toastError('Xảy ra lỗi')
        } finally {
            this.loading(false);
        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isEdit, isXoa, isImport, isExport) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnEdit = isEdit;
            this.isDisableBtnXoa = isXoa;
            this.isDisableBtnImport = isImport;
            this.isDisableBtnExport = isExport;
        },
        onSelectMatsan(selected) {
            this.form.pMatsanId = selected.id;
            this.form.pMatsanTen = selected.text;
            // this.form.pIDCId = selected.idc_id;
            // this.form.pIDCTen = selected.idc_ten;
            // this.form.pToadoMatsanTen = this.form.pIDCTen + '_' + this.form.pMatsanTen + '_' + this.form.pTruc_Doc + '_' + this.form.pTruc_Ngang;
        },
        onSelectRowChanged(selected) {
            // console.log(selected);
            this.isDisableSave = true;
            this.isDisableUpdate = false;
            this.form.pIDCId = selected.data.IDC_ID;
            this.form.pIDCTen = selected.data.IDC_TEN;
            this.form.pToanhaId = selected.data.TOANHA_ID;
            this.form.pToanhaTen = selected.data.TOANHA_TEN;
            this.form.pMatsanId = selected.data.MATSAN_ID;
            this.form.pMatsanTen = selected.data.MATSAN_TEN;
            this.form.pToadoMatsanId = selected.data.TOADO_MATSAN_ID;
            this.form.pToadoMatsanTen = selected.data.TEN;
            this.form.pTruc_Ngang = selected.data.TRUC_NGANG;
            this.form.pTruc_Doc = selected.data.TRUC_DOC;
            this.form.pGhiChu = selected.data.GHI_CHU;
            this.form.pHieuLuc = selected.data.HIEU_LUC_TEXT == "Hiệu lực" ? 1 : 0;
            if(this.form.pIDCId) {
                this.dsToanhaFilter = this.dsToanha.filter( (x) => x.IDC_ID == this.form.pIDCId )
            }
            if(this.form.pToanhaId) {
                this.dsMatsanFilter = this.dsMatsan.filter( (x) => x.TOANHA_ID == this.form.pToanhaId )
            }
            this.setDisableBtn(false, true, true, false, false, false, false);
        },
        onSelectRowCheckbox(selectedItems) {
            this.selectedRows = selectedItems || []
            this.isDisableBtnXoa = this.selectedRows.length > 0
            // console.log(this.selectedRows)
            // console.log(this.isDisableBtnXoa)
        },
        checkForm: function() {
            // console.log(this.form)
            if(this.form.pIDCId == 0) {
                this.$root.toastError('Vui lòng chọn IDC.');
                return false;
            }
            if(this.form.pToanhaId == 0) {
                this.$root.toastError('Vui lòng chọn Toà nhà.');
                return false;
            }
            if(this.form.pMatsanId == 0) {
                this.$root.toastError('Vui lòng chọn mặt sàn.');
                return false;
            }
            if(this.form.pTruc_Doc == 0) {
                this.$root.toastError('Vui lòng Nhập Tọa độ trục dọc.');
                return false;
            }
            if(this.form.pTruc_Ngang == 0) {
                this.$root.toastError('Vui lòng Nhập Tọa độ trục ngang.');
                return false;
            }

            this.form.pToadoMatsanTen = this.form.pIDCTen + '_' + this.form.pMatsanTen + '_' + this.form.pTruc_Doc + '_' + this.form.pTruc_Ngang;
            if(this.form.pToadoMatsanId == 0) {
                const row = this.dsToaDoMatSan.filter(item => item.TEN.toUpperCase() == this.form.pToadoMatsanTen.toUpperCase())
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
                pToadoMatsanId: 0,
                pToadoMatsanTen: "",
                pTruc_Ngang: "",
                pTruc_Doc: "",
                pGhiChu: "",
                pHieuLuc: 1
            };
            this.setDisableBtn(true, false, false, true, true, true, true);
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
                if(this.form.pToadoMatsanId != 0) {
                    try {
                        var rs = await this.$root.context.post('web-ecms/idc/ToadoMatsan/CapNhat', payload);
                        const idx = this.dsToaDoMatSan.findIndex(r => r.TOADO_MATSAN_ID === rs.data.TOADO_MATSAN_ID);
                        if (idx >= 0) {
                            this.$set(this.dsToaDoMatSan, idx, rs.data);
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
                        var rs = await this.$root.context.post('web-ecms/idc/ToadoMatsan/Them', payload);
                        if (rs && rs.data) {
                            this.dsToaDoMatSan = [rs.data, ...this.dsToaDoMatSan];
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
        edit: function() {
            if(this.isDisableBtnEdit) {
                return;
            }
            if(this.form.pToadoMatsanId == 0) {
                this.$root.toastError('Vui lòng chọn Tọa độ mặt sàn để sửa');
                return;
            }
            this.setDisableBtn(true, false, false, true, true, true, true);
        },
        xoa: function() {
            if(this.isDisableBtnXoa) {
                return;
            }
            this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords()

            if (this.selectedRows.length === 0) {
                this.$toast.warning('Vui lòng chọn ít nhất một bản ghi để xóa')
                return
            }
            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu đã chọn?', {
                size: 'sm',
                centered: true,
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy'
            })
            .then(async val => {
                if (!val) return;
                try {
                    this.$root.showLoading(true)
                    let successCount = 0
                    let errorCount = 0
                    for (let i = 0; i < this.selectedRows.length; i++) {
                        const id = this.selectedRows[i].TOADO_MATSAN_ID
                        var rs1 = await this.$root.context.post(`web-ecms/idc/ToadoMatsan/Xoa/${id}`);
                        // console.log(rs1);
                        // console.log(rs1.error);
                        if (rs1.error == '200') {
                            successCount++
                        } else {
                            errorCount++
                        }
                    }
                    // console.log(successCount, errorCount)

                    if (successCount > 0) this.$root.toastSuccess(`Xóa thành công ${successCount} Tọa độ mặt sàn`)
                    if (errorCount > 0) this.$root.toastError(`Xóa thất bại ${errorCount} Tọa độ mặt sàn`)

                    //await this.load()
                    var rs2 = await this.$root.context.post('web-ecms/idc/ToadoMatsan/DanhSachToaDoMatSan');
                    this.dsToaDoMatSan = rs2.data;
                    this.isError = false;
                    this.form = {
                        pIDCId: 0,
                        pIDCTen: "",
                        pMatsanId: 0,
                        pMatsanTen: "",
                        pToadoMatsanId: 0,
                        pToadoMatsanTen: "",
                        pTruc_Ngang: "",
                        pTruc_Doc: "",
                        pGhiChu: "",
                        pHieuLuc: 1
                    };
                    this.huy();
                } catch (e) {
                    // this.handleErrorResponse(e)
                } finally {
                    this.$root.showLoading(false)
                }
                })
            .catch(err => {
                this.$root.toastError('Xảy ra lỗi');
            })


            // .then(async val => {
            //     if(val) {
            //         if(this.form.pToadoMatsanId != "") {
            //             try {
            //                 var rs = await this.$root.context.post(`/ToadoMatsan/Xoa/${this.form.pToadoMatsanId}`);
            //                 if(rs.data.result) {
            //                     this.dsToaDoMatSan = this.dsToaDoMatSan.filter(item => item.TOADO_MATSAN_ID != this.form.pToadoMatsanId);
            //                     this.$toast.success('XÃ³a thÃ nh cÃ´ng');
            //                     this.huy();
            //                 }
            //             } catch (error) {
            //                 this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'KhÃ´ng thá»ƒ xÃ³a báº£n ghi nÃ y');
            //             }
            //         }
            //         else {
            //             this.$root.toastError('Vui lÃ²ng chá»n Tá»a Ä‘á»™ máº·t sÃ n Ä‘á»ƒ xÃ³a');
            //         }
            //     }
            // })
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            }
            this.form = {
                pToadoMatsanId: 0,
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
            this.setDisableBtn(false, true, true, false, false, false, false);
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
                    var rs = await this.$root.context.post('web-ecms/idc/ToadoMatsan/Import', this.parsedData);
                    if(rs.data) {
                        // console.log(rs.data);
                        // this.dsToaDoMatSan = this.dsToaDoMatSan.filter(item => item.TOADO_MATSAN_ID != this.form.pToadoMatsanId);
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
                if (this.dsToaDoMatSan.length == 0) {
                    this.$toast.error("Không có dữ liệu để xuất Excel !");
                    return;
                }
                this.$refs.gridItems.excelExport()
            } catch (ex) {
                this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex);
            }
        },
        onIDCChange(idcId) {
            this.form.pIDCId = idcId
            this.form.pIDCTen = this.dsIdc.find(x => x.IDC_ID == idcId)?.IDC_TEN || ''
            // console.log(this.form.pIDCTen)
            this.dsToanhaFilter = this.dsToanha.filter(
                (x) => x.IDC_ID == idcId
            )
            // this.currentItem.toanha_id = 0
        },
        onToaNhaChange(toanhaId) {
            this.form.pToanhaId = toanhaId
            this.dsMatsanFilter = this.dsMatsan.filter(
                (x) => x.TOANHA_ID == toanhaId
            )
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
