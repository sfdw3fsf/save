<style scope src="./NhomCongNoNhanVien.scss"></style>
<template src="./NhomCongNoNhanVien.html"></template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import API from "../../api/NhomCongNoNhanVien.js"
import xlsx from "xlsx";
import downloadexcel from "vue-json-excel";
import ModalXuatDuLieu from '../../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'
import { cloneArray } from '@/utils/util'

export default {
    name: "NhomCongNoNhanVien",
    components: {
        downloadexcel,
        xlsx,
        ModalXuatDuLieu,
    },
    computed: {
        ...mapState("nhomcongnonhanvien", [
            "dsCongNo",
            "dsNhanVienDaGan",
            "dsNhanVienChuaGan",
            "dsAllNhanVienDaGan"
        ]),
        ...mapGetters("nhomcongnonhanvien", [
            "dsNhanVienDaGanGetter",
            "dsNhanVienChuaGanGetter",
        ])
    },
    created: async function () {},
    async mounted() {
        // this.clearGrid()
        this.$root.loading(true)
        this.fetchData()
        this.$root.loading(false)
    },
    data() {
        return {
            editSettings: { allowEditing: true, mode: "Batch" },
            fieldsNhomCongNo: [
                {
                    fieldName: "NHOMCNO_ID",
                    headerText: "ID",
                    allowFiltering: true,
                    cellCssClass: 'text-right',
                    width: 70
                },
                {
                    fieldName: "TEN_NHOM",
                    headerText: "Nhóm công nợ",
                    allowFiltering: true,
                },
            ],
            fieldsNhanVienDaGan: [
                {
                    fieldName: "NHANVIEN_ID",
                    headerText: "ID",
                    allowFiltering: true,
                    cellCssClass: 'text-right',
                    width: 70
                },
                {
                    fieldName: "MA_NV",
                    headerText: "Mã NV",
                    allowFiltering: true,
                },
                {
                    fieldName: "TEN_NV",
                    headerText: "Tên NV",
                    allowFiltering: true,
                },
                {
                    fieldName: "LOAI_NV",
                    headerText: "Trưởng nhóm",
                    allowFiltering: true,
                    cellCssClass: "center",
                    allowEditing: true
                },
                {
                    fieldName: "TEN_DV",
                    headerText: "Đơn vị",
                    allowFiltering: true,
                },
            ],
            fieldsNhanVienChuaGan: [
                {
                    fieldName: "NHANVIEN_ID",
                    headerText: "ID",
                    allowFiltering: true,
                    cellCssClass: 'text-right',
                },
                {
                    fieldName: "MA_NV",
                    headerText: "Mã NV",
                    allowFiltering: true,
                },
                {
                    fieldName: "TEN_NV",
                    headerText: "Tên NV",
                    allowFiltering: true,
                },
            ],
            dsThuocTinh: ["TEN_NHOM", "MA_NV", "TEN_NV", "LOAI_NV", "NHOMCNO_ID", "NHANVIEN_ID"],
            exportExcelFields: {
                "TEN_NHOM": "TEN_NHOM",
                "MA_NV": "MA_NV",
                "TEN_NV": "TEN_NV",
                "LOAI_NV": {
                    field: "LOAI_NV",
                    callback: (value) => {
                        return value == 1 ? "1" : "0";
                    },
                },
                "NHOMCNO_ID": "NHOMCNO_ID",
                "NHANVIEN_ID": "NHANVIEN_ID"
            },
            exportFileMau: {
                "NHOMCNO_ID": "NHOMCNO_ID",
                "MA_NV": "MA_NV",
                "LOAI_NV": "LOAI_NV",
            },
            dsFileMau: [{
                NHOMCNO_ID: null,
                MA_NV: null,
                LOAI_NV: null
            }],
            gridNhanVienDaGanValues: [],
            gridNhanVienChuaGanValues: [],
            data: {
                nhomCNoId: "",
                dsNhanVien: []
            },
            isShowModal: false,
            dataExcel: {
                nhomCNoId: "",
                dsNhanVien: []
            },
            exportData: [],
            danhSachNhanVien: [],
        }
    },
    methods: {
        ...mapActions("nhomcongnonhanvien", [
            "getDanhSachCongNo",
            "getDanhSachNhanVienCongNo",
            "setDanhSachNhanVien",
            "clearGrid",
            "capNhapDanhSachNhomCongNo",
            "getAllNhanVienDaGan",
            "getDanhSachNhanVien",
            "getDanhSachNhanVienDaGan"
        ]),
        mouseOver() {
            $(".neo").addClass("show")
        },
        mouseLeave() {
            $(".neo").removeClass("show")
        },
        async fetchData () {
            await this.getDanhSachCongNo()
            let daGan = {
                nhomCNoId: this.dsCongNo[0].NHOMCNO_ID,
                tenNhom: this.dsCongNo[0].TEN_NHOM,
                daGan: 1
            }
            let chuaGan = {
                nhomCNoId: this.dsCongNo[0].NHOMCNO_ID,
                daGan: 0
            }
            await this.getDanhSachNhanVienCongNo(daGan)
            await this.getDanhSachNhanVienCongNo(chuaGan)

            this.loadDanhSach()
        },
        async loadDanhSach() {
            for (let i = 0; i < this.dsNhanVienDaGan.length; i++) {
                this.danhSachNhanVien.push(this.dsNhanVienDaGan[i])
            }
            for (let i = 0; i < this.dsNhanVienChuaGan.length; i++) {
                this.danhSachNhanVien.push(this.dsNhanVienChuaGan[i])
            }
            // console.log(this.danhSachNhanVien)
        },
        async rowClicked(dataItem) {
            let item = dataItem
            console.log(item)
            this.data.nhomCNoId = item.NHOMCNO_ID
            this.data.dsNhanVien = []
            this.$root.loading(true)
            let daGan = {
                nhomCNoId: item.NHOMCNO_ID,
                tenNhom: item.TEN_NHOM,
                daGan: 1
            }
            // let chuaGan = {
            //     nhomCNoId: item.NHOMCNO_ID,
            //     daGan: 0
            // }

            await this.getDanhSachNhanVienCongNo(daGan)

            // await this.getDanhSachNhanVienCongNo(chuaGan)

            let ds = []
            for (let i = 0; i < this.danhSachNhanVien.length; i++) {
                ds.push(this.danhSachNhanVien[i])
            }
            for (let i = 0; i < this.dsNhanVienDaGan.length; i++) {
                let index = 0
                ds.find((item, key) => {
                    if (item.MA_NV == this.dsNhanVienDaGan[i].MA_NV) index = key
                })
                ds.splice(index, 1)
            }
            await this.getDanhSachNhanVien(ds)
            this.$refs.gridNhanVienChuaGan.setCurrentSelectedRow(0)
            this.$root.loading(false)
        },
        thayDoiTrangThaiNhanVien(setGan) {
            let dsNhanVienSelected = []
            let dsNhanVienKhongSelected = []
            let data = {}

            dsNhanVienKhongSelected = this.getListItem(
                this.dsNhanVienChuaGanGetter,
                "NHANVIEN_ID",
                this.gridNhanVienChuaGanValues
            )
            dsNhanVienSelected = this.getListItem(
                this.dsNhanVienDaGanGetter,
                "NHANVIEN_ID",
                this.gridNhanVienDaGanValues
            )
            data = {
                field: "NHANVIEN_ID",
                setGan: setGan,
                itemArr: setGan == 1 ? dsNhanVienKhongSelected : dsNhanVienSelected,
            }
            if (setGan == 1) this.$refs.gridNhanVienChuaGan.selectAllItem = false
            else this.$refs.gridNhanVienDaGan.selectAllItem = false

            this.setDanhSachNhanVien(data)
        },
        getListItem(arr, id, ids) {
            return arr.filter((x) => ids.includes(x[id]));
        },
        async ghiLai() {
            this.data.dsNhanVien = []
            console.log(this.dsNhanVienDaGanGetter)
            this.dsNhanVienDaGanGetter.forEach(element => {
                this.data.dsNhanVien.push({
                    maNV: element.MA_NV,
                    loaiNVId: element.LOAI_NV
                })
            })
            console.log("data select: ", this.data)
            let count = 0
            this.data.dsNhanVien.forEach(element => {
                if (element.loaiNVId == 1) {
                    count++
                }
            })
            if (this.data.dsNhanVien.length > 0) {
                if (count == 0) {
                    this.$root.toastError("Chưa có trưởng nhóm!")
                    return
                }
                if (count > 1) {
                    this.$root.toastError("Không được chọn >1 nhân viên làm trưởng nhóm!")
                    return
                }
            } else {
                this.data.dsNhanVien.push({
                    maNV: "",
                    loaiNVId: 0
                })
            }

            await this.capNhapDanhSachNhomCongNo(this.data)
        },
        async xuatAllFile() {
            this.exportData = []
            this.$root.loading(true)
            await this.getAllNhanVienDaGan(this.dsCongNo)
            setTimeout(() => {
                this.exportData = this.dsAllNhanVienDaGan
                this.$root.loading(false)
                this.$bvModal.show("modal-xuat-dulieu")
            }, 30000)
        },
        xuatTheoNhom() {
            this.exportData = []
            this.exportData = this.dsNhanVienDaGan
            if (this.exportData.length === 0){
                this.$root.toastError("Không có dữ liệu để xuất")
                return
            }
            this.$bvModal.show("modal-xuat-dulieu")
        },
        async importExcel(e) {
            this.$root.loading(true)
            const files = e.target.files;
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                this.$root.toastError("File không đúng định dạng xls hoặc xlsx!");
                this.$root.loading(false)
                return
            }
            this.readFile(files)
        },
        readFile(file) {
            const fileReader = new FileReader()
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
                    let danhSach = []

                    let ds = []

                    if (ws.length == 0) {
                        this.$root.toastError("File excel không có dữ liệu!")
                        this.$root.loading(false)
                        return
                    }

                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                        ds.push(ws[i].NHOMCNO_ID)
                    }

                    if (
                        !excellist[0].hasOwnProperty("NHOMCNO_ID") ||
                        !excellist[0].hasOwnProperty("MA_NV") ||
                        !excellist[0].hasOwnProperty("LOAI_NV")
                    ) {
                        this.$root.toastError("File cần có chính xác 3 cột NHOMCNO_ID, MA_NV, LOAI_NV!");
                        this.$root.loading(false)
                        return;
                    }

                    let sortarr = ds.slice().sort()
                    for (let i = 0; i < sortarr.length - 1; i++) {
                        if (sortarr[i + 1] != sortarr[i]) {
                            danhSach.push({
                                nhomCNoId: sortarr[i],
                                dsNhanVien: []
                            })
                        }
                    }
                    danhSach.push({
                        nhomCNoId: sortarr[sortarr.length - 1],
                        dsNhanVien: []
                    })

                    danhSach.forEach(item => {
                        for (var i = 0; i < ws.length; i++) {
                            if (ws[i].NHOMCNO_ID == item.nhomCNoId) {
                                item.dsNhanVien.push({
                                    maNV: ws[i].MA_NV,
                                    loaiNVId: ws[i].LOAI_NV
                                })
                            }
                        }
                    })
                    console.log("danh sách: ", danhSach)

                    //check NHOMCNO_ID không tồn tại
                    for (let i = 0; i < danhSach.length; i++) {
                        let arr = this.dsCongNo.filter(val => val.NHOMCNO_ID === danhSach[i].nhomCNoId)
                        // console.log(arr)
                        if (arr.length !== 1) {
                            this.$root.toastError("Trong file excel có dữ liệu NHOMCNO_ID không đúng")
                            this.$root.loading(false)
                            return
                        }
                    }

                    //check LOAI_NV khác 0 và 1
                    for (let i = 0; i < danhSach.length; i++) {
                        for (let j = 0; j < danhSach[i].dsNhanVien.length; j++) {
                            if (danhSach[i].dsNhanVien[j].loaiNVId != 0 && danhSach[i].dsNhanVien[j].loaiNVId != 1) {
                                this.$root.toastError("Trong file excel có dữ liệu LOAI_NV khác 0 và 1")
                                this.$root.loading(false)
                                return
                            }
                        }
                    }

                    // await Promise.all(danhSach.map(async (item) => {
                        // let daGan = {
                        //     nhomCNoId: item.nhomCNoId,
                        //     daGan: 1
                        // }

                        // await API.getDanhSachNhanVienCongNo(this.axios, daGan).then(response => {
                        //     response.data.data.map(element => {
                        //         item.dsNhanVien.push({
                        //             maNV: element.MA_NV,
                        //             loaiNVId: element.LOAI_NV ? 1 : 0
                        //         })
                        //     })

                        // })
                    // }))

                    //check MA_NV không tồn tại
                    let error_maNV = []
                    for (let k = 0; k < danhSach.length; k++) {
                        danhSach[k].dsNhanVien.forEach(element => {
                            let arr_maNV = this.danhSachNhanVien.filter(val => val.MA_NV == element.maNV)
                            if (arr_maNV.length != 1) {
                                error_maNV.push(element.maNV)
                            }
                        })
                    }

                    if (error_maNV.length > 0) {
                        let arr_error = error_maNV.filter((element, index) => {
                            return error_maNV.indexOf(element) === index;
                        })
                        this.$root.toastError("Mã nhân viên: " + arr_error.join(', ') + " không tồn tại!");
                        this.$root.loading(false);
                        return;
                    }

                    let count = 0
                    for (let i = 0; i < danhSach.length; i++) {
                        count = 0
                        let arr = []
                        danhSach[i].dsNhanVien.forEach(element => {
                            if (element.loaiNVId == 1) {
                                count++
                            }
                            arr.push(element.maNV)
                        })

                        //check nhóm trưởng
                        if (count > 1) {
                            this.$toast.error('Không được có nhiều hơn 1 trưởng nhóm trong cùng 1 nhóm!')
                            this.$root.loading(false)
                            return
                        }
                        if (count == 0) {
                            this.$toast.error('Trong file excel có NHOMCNO_ID chưa có trưởng nhóm (LOAI_NV=1)')
                            this.$root.loading(false)
                            return
                        }
                        //check MA_NV đã tồn tại
                        // let sort = arr.sort()
                        // for (let j = 0; j < sort.length - 1; j++) {
                        //     if (sort[j + 1] == sort[j]) {
                        //         this.$root.toastError("Trong file excel có dữ liệu NHOMCNO_ID và NHANVIEN_ID đã tồn tại");
                        //         this.$root.loading(false);
                        //         return;
                        //     }
                        // }
                    }

                    this.dataExcel = danhSach
                    console.log("excel: ", this.dataExcel)
                    this.isShowModal = true
                    this.$root.loading(false)

                } catch (error) {
                    this.$root.loading(false)
                    return alert("Read failure!" + error);
                }
            }
            fileReader.readAsBinaryString(file[0])
        },
        async docFile() {
            this.$root.loading(true)
            // console.log("excel: ", this.dataExcel)
            // let index
            // this.dsCongNo.forEach(x => {
            //     if (x.NHOMCNO_ID === this.dataExcel[0].nhomCNoId) {
            //         index = x.STT
            //     }
            // })
            // console.log(index)
            this.$refs.gridNhomCongNo.setCurrentSelectedRow(0)
            // this.$refs.gridNhanVienDaGan.setCurrentSelectedRow(0)

            for (let i = 0; i < this.dataExcel.length; i++) {
                let success = false
                let response = await API.capNhapDanhSachNhomCongNo(this.axios, this.dataExcel[i])
                if (response.data.error_code === "BSS-00000000") {
                    success = true
                }

                if (i == this.dataExcel.length-1) {
                    if (success) {
                        this.$toast.success('Cập nhật dữ liệu thành công!')
                        let daGan = {
                            nhomCNoId: this.dsCongNo[0].NHOMCNO_ID,
                            daGan: 1
                        }
                        let chuaGan = {
                            nhomCNoId: this.dsCongNo[0].NHOMCNO_ID,
                            daGan: 0
                        }
                        await this.getDanhSachNhanVienCongNo(daGan)
                        await this.getDanhSachNhanVienCongNo(chuaGan)
                    } else {
                        this.$toast.error('Cập nhật dữ liệu thất bại!')
                    }
                }
            }

            console.log("data update: ", this.dataExcel)
            this.$root.loading(false)
        },
        xuatFileMau() {
            const XLSX = xlsx
            // for (let i=0; i<10; i++) {
            //     this.dsFileMau.push({
            //         NHOMCNO_ID: Math.floor(Math.random()*(600-350)+350),
            //         MA_NV: "VNP012233",
            //         LOAI_NV: Math.floor(Math.random()*2)
            //     })
            // }
            let data = XLSX.utils.json_to_sheet(this.dsFileMau)
            data.C1.c = []
            data.C1.c.hidden = true
            data.C1.c.push({t:"Điền 0 hoặc 1 (1: trưởng nhóm, 0: không phải trưởng nhóm)"});
            let wb = XLSX.utils.book_new() // make Workbook of Excel
            XLSX.utils.book_append_sheet(wb, data, 'fileMau') // sheetAName is name of Worksheet
            // export Excel file
            XLSX.writeFile(wb, 'fileMau.xlsx')
        },
        async close() {
            this.$bvModal.hide('popup-nhomcongnonhanvien')
            if (this.dsCongNo.length > 0) {
                let daGan = {
                nhomCNoId: this.dsCongNo[0].NHOMCNO_ID,
                tenNhom: this.dsCongNo[0].TEN_NHOM,
                daGan: 1
                }
                let chuaGan = {
                    nhomCNoId: this.dsCongNo[0].NHOMCNO_ID,
                    daGan: 0,
                }
                await this.getDanhSachNhanVienCongNo(daGan)
                await this.getDanhSachNhanVienCongNo(chuaGan)
            }
        },
        onModalShown: async function(){
            console.log('test');
        },
        async editGridSave(arg){
            let KEY = arg.rowData.NHANVIEN_ID;
            if (arg.columnName == "LOAI_NV") {
                let data = cloneArray(this.dsNhanVienDaGanGetter)
                if (arg.value) {
                    data.forEach((element) => {
                        if (element.NHANVIEN_ID == KEY) {
                            element.LOAI_NV = arg.value;
                            return;
                        }
                    });
                }
                this.getDanhSachNhanVienDaGan(cloneArray(data))
            }
        }
    },
}
</script>
