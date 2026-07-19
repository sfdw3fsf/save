<template src="./index.html"></template>

<script>
import XLSX from "xlsx"
export default {
    data() {
        return {
            btnEnable: false,
            duongDan: "",
            dgvDanhSach: [],
            vds: []
        }
    },
    methods: {
        checkIsOnline: function () {
            if (!(this.dgvDanhSach == null || this.dgvDanhSach == undefined || this.dgvDanhSach.length == 0)) {
                return this.isNullOrUndefined(this.dgvDanhSach[0].chuky);
            }
            return true;
        },
        duongDan_click: function (args) {
            this.$refs.fileInput.click();
        },
        async dsQuyenTBToaNha(ds) {
            try {
                let vds = [];
                ds.forEach(e => {
                    vds.push({
                        CHUKY: e.CHUKY + '01',
                        MA_TB: e.MA_TB,
                        MA_TOANHA: e.MA_TOANHA,
                        DAUCUOI_ID: e.DAUCUOI_ID
                    })
                });
                let body = {
                    vds: vds,
                    vkieu: this.checkIsOnline() ? 1 : 2
                }
                let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/ds_quyen_thuebao_toanha_v2`, body)
                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    let rs = [];
                    response.data.forEach(e => {
                        rs.push({
                            chon: false,
                            chuky: e.chuky.toString().substring(0, 6),
                            thuebao_id: e.thuebao_id,
                            ma_tb: e.ma_tb,
                            daucuoi_id: e.daucuoi_id,
                            dichvuvt_id: e.dichvuvt_id,
                            toanha_id: e.toanha_id,
                            ma_toanha: e.ma_toanha,
                            ten_dvvt: e.ten_dvvt,
                            loai: e.loai,
                            ghichu: e.ghichu,
                            trangthai: e.trangthai
                        })
                    });
                    return rs;
                } else {
                    return []
                }
            } catch (err) {
                return []
            } finally {

            }
        },
        async ktQuyenToaNha() {
            try {
                let body = {
                    vchucnang: "TB_TOANHA_FILE",
                    vkieu: 0,
                    vid: 0,
                    vttvt_id: 0
                }
                let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/kt_quyen_toanha_2`, body)
                if (response.error_code === 'BSS-00000000' && response.data === 'OK') {
                    return true
                } else {
                    this.$root.toastError(response.data)
                    return false
                }
            } catch (err) {
                return false
            }
        },
        async ktTBToaNhaFile(ds) {
            try {
                let body = {
                    vkieu: 3,
                    vds: ds
                }
                let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/sp_kiemtra_tb_toanha_file_v2`, body)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }
            } catch (err) {

            }
        },
        async capNhatTBToaNhaTheoFile(ds) {
            let resultAPI = {
                status: false,
                message: "",
            }
            try {
                let body = {
                    vkieu: !this.checkIsOnline() ? 3 : 2,
                    vds: ds
                }
                let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/fn_capnhat_tb_toanha_file_v2`, body)
                if (response.error_code === 'BSS-00000000' && response.data === 'OK') {
                    resultAPI.status = true;
                    return resultAPI;
                    // return true
                }
                else {
                    resultAPI.status = false;
                    resultAPI.message = response.data;
                    return resultAPI
                    //  return false
                }
            } catch (err) {
                return resultAPI;
                //  return false
            }
        },
        async onChangeFile(e) {
            const files = e.target.files;
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$root.toastError("File excel không đúng định dạng!");
            }
            this.duongDan = e.target.files[0].name
            await this.readFile(e.target.files[0])
        },
        async readFile(path) {
            let reader = new FileReader()
            reader.onload = async (e) => {
                try {
                    this.$root.showLoading(true)
                    this.btnEnable = false;
                    const data = e.target.result
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    })
                    const wsname = workbook.SheetNames[0]
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
                    this.vds = []
                    this.dgvDanhSach = []

                    for (var i = 0; i < ws.length; i++) {
                        if (!(this.isNullOrUndefined(ws[i].MA_TB) || this.isNullOrUndefined(ws[i].MA_TOANHA) || this.isNullOrUndefined(ws[i].DAUCUOI_ID)))
                            this.vds.push({
                                CHUKY: ws[i].CHUKY ?? null, MA_TB: ws[i].MA_TB.toString(), MA_TOANHA: ws[i].MA_TOANHA, DAUCUOI_ID: ws[i].DAUCUOI_ID
                            })
                    }

                    if (!(this.vds == null || this.vds.length == 0)) {
                        let ck = this.isNullOrUndefined(this.vds[0].CHUKY);
                        for (let i = 0; i < this.vds.length; i++) {
                            const e = this.vds[i];
                            if (ck != this.isNullOrUndefined(e.CHUKY)) {
                                this.$root.toastError("Dữ liệu trên cột CHUKY không đồng nhất, cột này bỏ trống hết hoặc phải điền hết")
                                return;
                            }
                        }
                        let dtquyen = await this.dsQuyenTBToaNha(this.vds)
                        this.dgvDanhSach = dtquyen
                        this.dgvDanhSach.forEach((item, index) => {
                            item.key = index + 1
                        })
                        let dtChuanHoa = dtquyen.filter(x => x.trangthai == "0")
                        if (dtChuanHoa.length > 0) {
                            this.$root.toastError("Vui lòng kiểm tra lại dữ liệu")
                            return
                        }

                        if (this.dgvDanhSach.length <= 0) {
                            this.$root.toastError("Không có dữ liệu!")
                            return
                        }
                        this.btnEnable = true
                    }
                    else {
                        this.$root.toastError("Vui lòng kiểm tra lại dữ liệu trên file")
                    }
                } catch (err) {
                    console.log(err)
                    this.$root.toastError("Có lỗi xảy ra")
                } finally {
                    this.$root.showLoading(false)
                }
            }
            reader.readAsBinaryString(path)
        },
        isNullOrUndefined(value) {
            return value === undefined || value === null;
        },
        onXuatFile() {
            if (this.dgvDanhSach.length <= 0) {
                this.$toast.error("Chưa có danh sách để xuất excel!");
            } else {
                let excelExportProperties = {
                    fileName: "Danh-sach-thue-bao-toa-nha.xlsx",
                    dataSource: this.dgvDanhSach
                };
                this.$refs.dgvDanhSachGrid.excelExport(excelExportProperties);
            }
        },
        onXuatFileMau() {
            let ds = [
                {
                    CHUKY: 202311,
                    MA_TB: "fiber_1",
                    MA_TOANHA: "00000001TN",
                    DAUCUOI_ID: "2"
                },
                {
                    CHUKY: 202312,
                    MA_TB: "tsl_diemdau",
                    MA_TOANHA: "00000001TN",
                    DAUCUOI_ID: "1"
                },
                {
                    CHUKY: 202311,
                    MA_TB: "tsl_diemcuoi",
                    MA_TOANHA: "00000001TN",
                    DAUCUOI_ID: "2"
                }
            ]
            let fileMau = XLSX.utils.json_to_sheet(ds);
            let wb = XLSX.utils.book_new(); // make Workbook of Excel
            XLSX.utils.book_append_sheet(wb, fileMau, "DS_TB_TOANHA_TEMP"); // sheetAName is name of Worksheet
            // export Excel file
            XLSX.writeFile(wb, "DS_TB_TOANHA_TEMP.xlsx");
        },
        async kiemTra() {
            return await this.ktQuyenToaNha()
        },
        gridViewControll_QueryCellInfo(args) {
            if (args.column.field === "trangthai") {
                if (args.data.trangthai == '0') {
                    args.cell.classList.value += " one-alert text-warning"
                    args.cell.style.textAlign = "center"
                }
                else if (args.data.trangthai == '1') {
                    args.cell.classList.value += " one-save text-success"
                    args.cell.style.textAlign = "center"
                }
                args.cell.innerText = null
            }
        },
        async ghiLai() {
            // this.$root.showLoading(true)
            if (!(await this.kiemTra())) {
                this.$root.toastInfo("Bạn không có quyền truy cập chức năng này");
                return
            }
            let msg = ""
            if (!this.checkIsOnline()) {
                msg = "Bạn đang cập nhật dữ liệu tòa nhà theo tháng chốt. Bạn có muốn tiếp tục?"
            } else {
                msg = "Bạn đang cập nhật dữ liệu tòa nhà Online. Bạn có muốn tiếp tục?"
            }
            try {
                await this.$bvModal.msgBoxConfirm(`${msg}`, {
                    "title": 'Thông báo',
                    "size": 'md',
                    "buttonSize": 'md',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Không đồng ý',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18", "text-center"],
                }).then(async (result) => {
                    if (result) {
                        this.$root.showLoading(true)
                        let temp = this.$refs.dgvDanhSachGrid.getSelectedRecords();
                        if (temp.length <= 0) {
                            this.$root.showLoading(false)
                            this.$alert('Không có dữ liệu cập nhật!', 'Thông báo', {
                                confirmButtonText: 'Đóng',
                                type: 'Error'
                            })
                            // this.$root.toastError("Không có dữ liệu cập nhật!")
                            return
                        }

                        let vds_upload = []
                        temp.forEach(element => {
                            vds_upload.push({
                                CHON: element.chon,
                                CHUKY: element.chuky+ '01',
                                THUEBAO_ID: element.thuebao_id,
                                MA_TB: element.ma_tb,
                                DAUCUOI_ID: element.daucuoi_id,
                                DICHVUVT_ID: element.dichvuvt_id,
                                TOANHA_ID: element.toanha_id,
                                MA_TOANHA: element.ma_toanha,
                                TEN_DVVT: element.ten_dvvt,
                                LOAI: element.loai,
                                GHICHU: element.ghichu,
                                TRANGTHAI: element.trangthai
                            })
                        })
                        let confirm = true
                        if (!this.checkIsOnline()) {
                            let dt_kiemtra = await this.ktTBToaNhaFile(vds_upload)
                            let temp_2 = dt_kiemtra.filter(e => e.trangthai == '0')
                            if (temp_2.length > 0) {
                                this.$root.showLoading(false)
                                let s = "";
                                temp_2.forEach(e => {
                                    s += MA_TB + "(" + MA_TOANHA + ", " + CHUKY + ") ";
                                })
                                this.dgvDanhSach = dt_kiemtra
                                await this.$bvModal.msgBoxConfirm("Tồn tại thuê bao đang thuộc tòa nhà " + s.trim() + ". Nếu tiếp tục tòa nhà cũ sẽ bị gỡ và thuê bao sẽ được gán tòa nhà mới. Đồng ý thực hiện?", {
                                    "title": 'Thông báo',
                                    "size": 'md',
                                    "buttonSize": 'md',
                                    "okVariant": 'primary',
                                    "okTitle": 'Đồng ý',
                                    "cancelTitle": 'Không đồng ý',
                                    "hideHeaderClose": false,
                                    "centered": true,
                                    "modal-class": ["f18", "text-center"],
                                }).then(rs => {
                                    if (!rs) {
                                        confirm = rs
                                        this.$root.showLoading(false)
                                        return
                                    }
                                    else {
                                        this.$root.showLoading(true)
                                    }
                                })
                            }
                        }
                        if (!confirm) return
                        let kq = await this.capNhatTBToaNhaTheoFile(vds_upload)
                        if (!kq.status) {
                            temp.forEach(e => {
                                e.trangthai = '0'
                                e.ghichu = 'ERROR'
                            });
                            this.$refs.dgvDanhSachGrid.refresh();
                            this.$alert(kq.message, 'Thông báo', {
                                confirmButtonText: 'Đóng',
                                type: 'Error'
                            })
                            return
                        }
                        temp.forEach(e => {
                            e.trangthai = '1'
                            e.ghichu = 'OK'
                        });
                        this.$refs.dgvDanhSachGrid.refresh();
                        this.$alert("Cập nhật dữ liệu thành công!", 'Thông báo', {
                            confirmButtonText: 'Đóng',
                            type: 'Success'
                        })
                    }
                })
            } catch (err) {

            } finally {
                this.$root.showLoading(false)
            }

        },
        huy() {
            this.$refs.fileInput.value = null
            this.duongDan = null
            this.dgvDanhSach = []
            this.btnEnable = false
            this.vds = []
        },
        async onChaylaiBC() {
            try {
                this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn cập nhật lại báo cáo này không ?", {
                    title: "Thông báo",
                    size: "md",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                    centered: true,
                }).then(async (v) => {
                    if (v) {
                        //let vchuky = this.chuKy && moment(this.chuKy).format("yyyyMM") + "01";
                        let vchuky = "0";
                        let response = await this.$root.context.post('web-toanha/thuebao-toanha/doanhThuToaNhaBC', { chuKy: vchuky });
                        if (response.error_code === 'BSS-00000000') {
                            return this.$root.toastSuccess('Cập nhật báo cáo thành công.');
                        }
                        else {
                            return this.$root.toastInfo("Cập nhật báo cáo thất bại\r\n" + response.data.message_detail)
                        }
                    }
                });
            }
            catch (err) {
                this.$root.toastError("Cập nhật báo cáo thất bại\r\n" + err);
            }
        },
    }
}
</script>

<style>
a.disabled {
    color: lightgrey !important;
    pointer-events: none;
    cursor: default;
    text-decoration: none;
}

.tb-toanha .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.tb-toanha .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"]>div {
    padding: 0 !important;
}
</style>