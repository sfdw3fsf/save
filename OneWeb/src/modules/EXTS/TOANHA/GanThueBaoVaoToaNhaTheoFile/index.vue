<template src="./index.html"></template>
<script>
import moment from "moment"
import XLSX from "xlsx"
export default {
    data() {
        return {
            btnEnable: false,
            isCheck: false,
            chuKy: moment(new Date()).subtract(1, "months").endOf("months").toDate(),
            duongDan: "",
            dgvDanhSach: [],
            dsKeyDuocChon: [],
            vds: [],
            checkAll: false,
        }
    },
    methods: {
        duongDan_click: function (args) {
            this.$refs.fileInput.click();
        },
        getColumnTemplateChonNhieu(parent) {
            return function () {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input
                                    :id="data.key"
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dsKeyDuocChon"
                                    :value="data.key"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        }
                    }
                }
            }
        },
        getHeaderTemplateChonNhieu(parent) {
            return function () {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent
                            }
                        }
                    }
                }
            }
        },
        async kiemTraChuKy(chuKy) {
            try {
                let response = await this.$root.context.get(`web-toanha/tb-toanha-theofile/kiemtra_chuky?vchuky=${chuKy}`)
                if (response.data == "OK") return true
                else {
                    this.$root.toastError(response.data)
                    return false
                }
            } catch (err) {
                this.$root.toastError("Có lỗi xảy ra")
                return false
            } finally {

            }
        },
        async kiemTraTonTaiBang(chuKy) {
            try {
                let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/kiemtra_tontai_bang?vchuky=${chuKy}`)
                if (response.error_code === 'BSS-00000000' && response.data != 0) {
                    return true
                } else {
                    return false
                }
            } catch (err) {
                return false
            }
        },
        async dsQuyenTBToaNha(ds) {
            try {
                let body = {
                    vds: ds,
                    vkieu: this.isCheck ? 2 : 1
                }
                let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/ds_quyen_tb_toanha_2`, body)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                } else {
                    return []
                }
            } catch (err) {
                return []
            } finally {

            }
        },
        // async capNhatTBToaNha(body) {
        //     try {
        //         let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/thuebao_toanha`, body)
        //         if (response.error_code === 'BSS-00000000' && response.data != null) {
        //             return response.data
        //         }
        //     } catch(err) {
        //         return err
        //     }
        // },
        // async ghiLog(body) {
        //     try {
        //         let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/ghi_log_tb_toanha`, body)
        //         if (response.error_code === 'BSS-00000000' && response.data != null) {
        //             return response.data
        //         }
        //     } catch(err) {

        //     }
        // },
        async ktQuyenToaNha() {
            try {
                let body = {
                    vchucnang: "TB_TOANHA_FILE",
                    vkieu: this.isCheck ? 2 : 1,
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
                    vchuky: this.chuKy && moment(this.chuKy).format("yyyyMM") + "01",
                    vds: ds
                }
                let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/sp_kiemtra_tb_toanha_file`, body)
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
                    vkieu: this.isCheck ? 3 : 2,
                    vchuky: this.chuKy && moment(this.chuKy).format("yyyyMM") + "01",
                    vds: ds
                }
                let response = await this.$root.context.post(`web-toanha/tb-toanha-theofile/fn_capnhat_tb_toanha_file`, body)
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
                    this.loading(true)
                    this.btnEnable = false;
                    const data = e.target.result
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    })
                    const wsname = workbook.SheetNames[0]
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
                    let excellist = []
                    this.vds = []
                    this.dgvDanhSach = []

                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                        this.vds.push({
                            MA_TB: ws[i].MA_TB.toString(), MA_TOANHA: ws[i].MA_TOANHA, DAUCUOI_ID: ws[i].DAUCUOI_ID.toString()
                        })
                    }

                    if (excellist != null && excellist.length > 0) {
                        if (!excellist[0].hasOwnProperty("MA_TB") || !excellist[0].hasOwnProperty("MA_TOANHA") || !excellist[0].hasOwnProperty("DAUCUOI_ID")) {
                            this.$root.toastError("Tên cột trong file không đúng định dạng: MA_TB, MA_TOANHA, DAUCUOI_ID")
                            return 
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
                } catch (err) {
                    console.log(err)
                    this.$root.toastError("Có lỗi xảy ra")
                } finally {
                    this.loading(false)
                }
            }
            reader.readAsBinaryString(path)
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
                    MA_TB: "fiber_1",
                    MA_TOANHA: "00000001TN",
                    DAUCUOI_ID: "2"
                },
                {
                    MA_TB: "tsl_diemdau",
                    MA_TOANHA: "00000001TN",
                    DAUCUOI_ID: "1"
                },
                {
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
            let kt = await this.ktQuyenToaNha()
            if (!kt) {
                return false
            }
            if (this.isCheck) {
                let vchuky = this.chuKy && moment(this.chuKy).format("yyyyMM") + "01"
                let rs = await this.kiemTraTonTaiBang(vchuky)
                if (!rs) {
                    return false
                }

                let response = await this.kiemTraChuKy(parseInt(vchuky))
                if (!response) {
                    return false
                }
            }
            return true
        },
        gridViewControll_QueryCellInfo(args) {
            if (args.column.field === "trangthai") {
                if (args.data.trangthai == '0') {
                    args.cell.classList.value += " one-alert text-warning"
                    args.cell.style.textAlign = "center"
                }
                args.cell.innerText = null
            }
        },
        async ghiLai() {
            // this.loading(true)
            if (!this.kiemTra) return
            let msg = ""
            if (this.isCheck) {
                msg = "Bạn đang cập nhật dữ liệu tòa nhà của kỳ cước: " + moment(this.chuKy).format("MM/yyyy") + ". Bạn có muốn tiếp tục?"
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
                        this.loading(true)
                        let temp = this.dgvDanhSach.filter(e => e.chon == 's1')
                        if (temp.length <= 0) {
                            this.loading(false)
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
                        if (this.isCheck) {
                            let dt_kiemtra = await this.ktTBToaNhaFile(vds_upload)
                            let temp_2 = dt_kiemtra.filter(e => e.trangthai == '0')
                            if (temp_2.length > 0) {
                                this.loading(false)
                                this.dgvDanhSach = dt_kiemtra
                                await this.$bvModal.msgBoxConfirm(`Tồn tại thuê bao đang thuộc tòa nhà (Kỳ cước: ${moment(this.chuKy).format("yyyyMM") + "01"}). 
                                                                    Nếu tiếp tục tòa nhà cũ sẽ bị gỡ và thuê bao sẽ được gán tòa nhà mới.
                                                                    Đồng ý thực hiện?`, {
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
                                        this.loading(false)
                                        return
                                    }
                                    else {
                                        this.loading(true)
                                    }
                                })
                            }
                        }
                        if (!confirm) return
                        let kq = await this.capNhatTBToaNhaTheoFile(vds_upload)
                        if (!kq.status) {
                            this.dgvDanhSach.forEach(e => {
                                if (e.chon == 's1') {
                                    e.trangthai = '0'
                                    e.ghichu = 'ERROR'
                                } else {
                                    e.trangthai = '-1'
                                    e.ghichu = ''
                                }
                            })
                            // this.$root.toastError("Có lỗi")
                            this.$alert(kq.message, 'Thông báo', {
                                confirmButtonText: 'Đóng',
                                type: 'Error'
                            })
                            return
                        }
                        this.dgvDanhSach.forEach(e => {
                            if (e.chon == 's1') {
                                e.trangthai = '1'
                                e.ghichu = 'OK'
                            } else {
                                e.trangthai = '-1'
                                e.ghichu = ''
                            }
                        })
                        this.$alert("Cập nhật dữ liệu thành công!", 'Thông báo', {
                                confirmButtonText: 'Đóng',
                                type: 'Success'
                            })
                        // this.$root.toastSuccess("Cập nhật dữ liệu thành công!")
                    }
                })
            } catch (err) {

            } finally {
                this.loading(false)
            }


            // let kt = true
            // let kq = ""
            // for (let i = 0; i < this.dgvDanhSach.length; i++) {
            //     if (this.dgvDanhSach[i].chon.toString() == 's0') {
            //         this.dgvDanhSach[i].trangthai = -1
            //         this.dgvDanhSach[i].ghichu = ""
            //         continue
            //     }
            //     let ss = ''
            //     let body = {
            //         vdichvuvt_id: parseInt(this.dgvDanhSach[i].dichvuvt_id.toString()),
            //         vthuebao_id: parseInt(this.dgvDanhSach[i].thuebao_id.toString()),
            //         vtoanha_id: parseInt(this.dgvDanhSach[i].toanha_id.toString()),
            //         vhdtb_id: 0,
            //         vkieu: 0
            //     }

            //     if (this.isCheck) {
            //         body.vkieu = 3
            //         body.vhdtb_id = parseInt(vchuky)
            //         kq = await this.capNhatTBToaNha(body)
            //         ss = "File - [" + body.vhdtb_id + "] " + kq.toString()
            //     } else {
            //         body.vkieu = 2
            //         body.vhdtb_id = 0
            //         kq = await this.capNhatTBToaNha(body)
            //         ss = "File - " + kq.toString()
            //     }
            //     //Thực hiện ghi log gán thuê bao tòa nhà
            //     let bodyGhiLog = {
            //         vthuebao_id: parseInt(this.dgvDanhSach[i].thuebao_id.toString()),
            //         vtoanha_id: parseInt(this.dgvDanhSach[i].toanha_id.toString()),
            //         vhdtb_id: 0,
            //         vngay_cn: moment(new Date()).format("DD/MM/yyyy HH:mm:ss"),
            //         vghichu: ss
            //     }
            //     await this.ghiLog(bodyGhiLog)

            //     if (kq != "OK") {
            //         kt = false
            //         this.dgvDanhSach[i].trangthai = 0
            //         if (kq.toString().split("-")[0] = "NG") {
            //             this.dgvDanhSach[i].ghichu = `Không áp dụng cho dịch vụ: ${kq.toString().split("-")[1]}`
            //         } else {
            //             this.dgvDanhSach[i].ghichu = kq
            //         }
            //     } else {
            //         this.dgvDanhSach[i].trangthai = 1
            //         this.dgvDanhSach[i].ghichu = kq
            //     }
            // }
            // if (kt) {
            //     this.$root.toastSuccess("Cập nhật dữ liệu thành công!")
            // } else {
            //     this.$root.toastError("Cập nhật dữ liệu không đầy đủ!")
            // }
            // this.loading(false)
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
                if (!this.isCheck) {
                    this.$root.toastError("Bạn phải chọn Kỳ cước cần Tạo lại báo cáo doanh thu");
                    return false;
                }
                this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn cập nhật lại báo cáo này không ?", {
                    title: "Thông báo",
                    size: "md",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                    centered: true,
                }).then(async (v) => {
                    if (v) {
                        let vchuky = this.chuKy && moment(this.chuKy).format("yyyyMM") + "01";
                        let response = await this.$root.context.post('web-toanha/thuebao-toanha/doanhThuToaNhaBC', { chuKy: vchuky });
                        if (response.error_code === 'BSS-00000000') {
                            return this.$root.toastSuccess('Cập nhật báo cáo thành công.');
                        }
                        else {
                            return this.$root.toastInfo("Cập nhật báo cáo thất bại\r\n" + response.data.message_detail)
                        }
                    }
                    // else{
                    //     return this.$root.toastInfo("Tiến trình đã bị dừng bởi người dùng.");
                    // }
                });
            }
            catch (err) {
                this.$root.toastError("Cập nhật báo cáo thất bại\r\n" + err);
            }
        },
    },
    watch: {
        checkAll(newVal) {
            if (newVal) {
                this.dsKeyDuocChon = this.dgvDanhSach.map(item => item.key)
                return
            }
            if (this.dsKeyDuocChon.length > 0 && this.dsKeyDuocChon.length === this.dgvDanhSach.length) {
                this.dsKeyDuocChon = []
            }
        },
        dsKeyDuocChon(newVal) {
            this.checkAll = newVal.length > 0 && newVal.length === this.dgvDanhSach.length
        },
        dgvDanhSach() {
            if (this.dgvDanhSach.length > 0) {
                this.checkAll = true
                this.dsKeyDuocChon = this.dgvDanhSach.map(item => item.key)
            } else {
                this.checkAll = false
                this.dsKeyDuocChon = []
                this.btnEnable = false
            }
        }
    },
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