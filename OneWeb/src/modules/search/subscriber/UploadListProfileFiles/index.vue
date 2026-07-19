<template src='./index.html'></template>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from "./api/index.js";
import moment from "moment";
import gridview from '@/components/Shared/gridview'

import UploadEprofile from "@/modules/contract/profile/UploadEProfile"
import searchEProfile from "@/modules/search/subscriber/SearchEProfile"
let dsFile = [];
let dsFileChon = [];
let tableData = [];
export default {
    components: { breadcrumb, moment, gridview, UploadEprofile, searchEProfile },
    name: "frmUpFileTB_TheoDS",
    data: function () {
        return {
            header: {
                title: "Upload danh sách file hồ sơ",
                list: [{
                    name: "Phát triển thuê bao",
                    link: "",
                }, {
                    name: "Upload danh sách file hồ sơ",
                    link: "",
                },],
            },
            dataSendKHS: {
                MA_GD: "",
                MA_TB: "",
                MA_KH: "",
            },
            txtMaGD: "",
            dsURLFile: [],
            dsGirdChon: [],

            show: true,
            combo_selected: "1",
            ttUpfile: "",
            position: { X: 'center', Y: 'top' },
            isDisabled: true,
            maGiaoDich: "",
            nhomFile: [],
            nhomFileChon: "",
            loaiFile: [],
            loaiFileChon: "",
            thuMuc: [],
            thuMucChon: "",
            khoLuu: [],
            khoLuuChon: -1,
            kieuFile: [],
            kieuFileChon: "",
            timTheo: [],
            timTheoChon: "",
            gvDanhSach: [],
            fileFromDir: [],
            hoSoId: "",
            loai_kh_tree: [],
            value: -1,
            tableConfig: {
                tableCollumns: [
                    {
                        fieldName: "MA_TB",
                        headerText: "Số máy/Acc",
                        allowFiltering: true,
                        allowSorting: false,
                        textAlign: "",
                    },
                    {
                        fieldName: "SO_GT",
                        headerText: "Số giấy tờ",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "FILE_HS_NEW",
                        headerText: "Chọn file hồ sơ",
                        allowFiltering: true,
                        allowSorting: false,
                        template: this.getColumnCreate(this)
                    },
                    {
                        fieldName: "TEN_LOAIHD",
                        headerText: "Loại HĐ",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "NGAY_YC",
                        headerText: "Ngày YC",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "MA_GD",
                        headerText: "Mã GD",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "TEN_TB",
                        headerText: "Tên thuê bao",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "DIACHI_LD",
                        headerText: "Địa chỉ lắp đặt",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "TEN_DVVT",
                        headerText: "Dịch vụ",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "LOAIHINH_TB",
                        headerText: "Loại hình",
                        allowFiltering: true,
                        allowSorting: false,
                    },
                    {
                        fieldName: "TIENTRINH",
                        headerText: "Tiến trình",
                        allowFiltering: true,
                        allowSorting: false,
                    }
                ],
                tableData: [],
            }
        };
    },

    computed: {

    },

    async created() {
        if (this.$route.query.vma_gd) {
            this.maGiaoDich = this.$route.query.vma_gd
        }

        let dataSend = {
            p_phanvung_id: this.$root.token.getPhanVungID(),
            p_nguoidung_id: this.$root.token.getNguoiDungID()
        };

        let res = await api.loadForm(this.axios, dataSend);
        if (res.data.data) {
            let obj = res.data.data
            this.nhomFile = obj.nhom_file
            this.loaiFile = obj.loai_file
            this.kieuFile = obj.kieu_file
            if (this.kieuFile.length > 0) {
                this.kieuFileChon = this.kieuFile[0].kieufile_id
            }

            this.timTheo = obj.kieu_tk
            if (this.timTheo.length > 0) {
                this.timTheoChon = this.timTheo[0].kieutk_id
            }

            this.loai_kh_tree = this.list2treeNew(obj.hoso)
        }

    },

    watch: {
    },
    methods: {
        getColumnCreate(parent) {
            return function () {
                return {
                    template: {
                        template: `
                        <div class="select-custom" >
                           <SelectExt :value="parent.tableConfig.tableData[data.fileChon.indexData].fileChon.index"  @change="changeFileHS($event)" :allowFiltering="true"
                                        dataTextField="name" dataValueField="index"
                                        :dataSource="dataSources">
                            </SelectExt>
                        </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                dataSources: [],
                                //fileChon : "",
                                data: {}
                            }
                        },
                        read: () => {
                        },
                        watch: {
                            "data.index": function (newVal) {
                                let temp = this.parent.tableConfig.tableData.find((ele) => ele.MA_TB == this.data.MA_TB)
                                let check = true;
                                temp.FILE_HS_NEW = newVal
                                if (dsFileChon.length > 0) {
                                    let flag = dsFileChon.find((el) => el.thuebao_id = temp.thuebao_id)
                                    if (flag) {
                                        check = false
                                    }
                                    if (check) dsFileChon.push(temp)
                                } else {
                                    dsFileChon.push(temp)
                                }

                            },
                            "dataSources": function (newVal) {
                                dsFile = newVal
                            },
                            "parent.fileFromDir": function (newVal) {
                                dsFile = newVal
                                this.dataSources = newVal
                            }
                        },
                        methods: {
                            changeFileHS(value) {
                                this.parent.changeFileHS(this.data, value)
                            }
                        },
                        mounted() {
                            this.dataSources = dsFile //dsKetQua
                            // this.data.index = -1
                        },
                    }
                }
            }
        },
        changeFileHS(item, value) {
            this.tableConfig.tableData[item.fileChon.indexData].fileChon.index = value
        },
        async tsbtnNapLai_Click() {
            await this.txtMaGD_KeyDown();
        },

        showPopupTraCuu() {
            this.$refs.popupTraCuuRef.show();
        },

        uploadFolder() {
            this.$refs.inputFoderRef.click();
        },

        async importFolder(e) {
            this.loading(true)
            this.dsURLFile = [];
            const files = e.target.files;
            this.fileFromDir = [];
            this.ttUpfile = "";
            files.forEach((ele, index) => {
                let obj = {
                    index: index,
                    name: ele.name
                }
                this.fileFromDir.push(obj)
            })
            dsFile = this.fileFromDir;
            this.ttUpfile = "Đã tải lên " + this.fileFromDir.length + " files"
            let formData = new FormData();
            for (var file of files) {
                formData.append('files', file);
            }
            let res = await api.upload_file(this.axios, formData, {
                headers: { "Content-Type": "multipart/form-data" },
            })
            if (res.data.error_code == "BSS-00000000") {
                let listID = res.data.data.join(",")
                let dataGetImg = {
                    list_file: listID
                }
                let res1 = await api.layAnhTheoId(this.axios, dataGetImg)
                this.dsURLFile = res1.data.data
            }
            this.loading(false)
            //this.tableConfig.tableData
            await this.khoiTaoIndexFileHoSo()

        },
        async khoiTaoIndexFileHoSo() {
            for (let i = 0; i < this.tableConfig.tableData.length; i++) {
                const index = this.fileFromDir.findIndex(x => x.name.toString().toLowerCase().includes(this.tableConfig.tableData[i][this.timTheoChon].toString().toLowerCase()))
                if (index > -1) {
                    this.tableConfig.tableData[i].fileChon = {
                        indexData: i,
                        index: this.fileFromDir[index].index,
                    }
                } else {
                    this.tableConfig.tableData[i].fileChon = {
                        indexData: i,
                        index: -1,
                    }
                }
            }
            this.tableConfig.tableData = [].concat(this.tableConfig.tableData)
        },
        changeKieuTK() {
            this.khoiTaoIndexFileHoSo()
        },
        createTreeHoSo(danhSachHoSo) {
            const trees = []
            const donViChaArray = danhSachHoSo.map(item => item['hoso_cha_id'])
            const donViArray = danhSachHoSo.map(item => item['hoso_id'])
            danhSachHoSo.forEach(item => {
                if (donViChaArray.includes(item['hoso_id'])) {
                    if (!donViArray.includes(item['hoso_cha_id'])) {
                        delete item.donvi_cha_id
                    }
                    trees.push({ ...item, hasChild: true, expanded: false })
                } else {
                    trees.push(item)
                }
            })
            const usrDonViIdx = trees.findIndex(item => item.hoso_id === this.hoSoId)
            if (usrDonViIdx !== -1) {
                trees[usrDonViIdx].isSelected = true
                trees[usrDonViIdx].expanded = true
            }
            return trees
        },



        loaikhChanged() {

        },

        list2treeNew(list) {
            let rootList = list.filter(x => (x.hoso_cha_id == null));
            let openList = [];
            rootList.forEach(item => {
                item.id = item.hoso_id
                item.label = item.ma_hs_doituong ? item.ma_hs_doituong : "Null"
                item.level = 0;
                openList.push(item);
            });

            while (openList.length) {
                let item = openList.shift();
                let children = list.filter(x => x.hoso_cha_id == item.hoso_id);
                if (children.length !== 0) {
                    item.children = children;
                    children.forEach(childItem => {
                        childItem.level = item.level + 1;
                        childItem.id = childItem.hoso_id
                        childItem.label = childItem.ma_hs_doituong ? childItem.ma_hs_doituong : "Null"
                        openList.push(childItem);
                    });
                }
            }
            return rootList;
        },

        async tsbtnGhiLai_Click() {
            let rowSelects = this.$refs.gridDS.getSelectedRecords()
            if (rowSelects.length == 0) {
                this.$toast.error("Chưa chọn danh sách thuê bao!");
                return
            }

            if (this.loaiFileChon == "") {
                this.$toast.error("Chưa chọn loại file!");
                return;
            }

            if (this.khoLuuChon == "") {
                this.$toast.error("Chưa chọn kho lưu!");
                return;
            }

            for (let i = 0; i < rowSelects.length; i++) {
                if (rowSelects[i] && rowSelects[i].TIENTRINH == 'Xong') {
                    continue
                }
                if (rowSelects[i].fileChon.index == -1) {
                    continue
                }
                let fileName = this.fileFromDir[rowSelects[i].fileChon.index].name
                const index = this.dsURLFile.findIndex(x => x.file_name.toString().toLowerCase().includes(fileName.toString().toLowerCase()))
                if (index < 0) {
                    continue
                }
                // xử lý cắt chuỗi file url, bỏ đoạn sau dấu ?
                let fileURL = this.dsURLFile[index].file_url
                let indexS = fileURL.indexOf(fileName);
                let indexD = fileURL.indexOf('/DHSX/');
                let url = fileURL.slice(indexD + 1, indexS + fileName.length)
                let dataSend = {
                    loaifile_id: this.loaiFileChon,
                    db_id: rowSelects[i].hdtb_id,
                    loaidb_id: 0,
                    hoso_id: this.khoLuuChon,
                    file_name: fileName,
                    file_url: decodeURIComponent(url),
                    ghichu: "UPLOAD DANH SÁCH FILE HỒ SƠ"
                }

                let resUpdate = await this.capNhat(dataSend);
                if (resUpdate == 'OK') {
                    this.tableConfig.tableData[rowSelects[i].fileChon.indexData].TIENTRINH = 'Xong'
                } else {
                    this.tableConfig.tableData[rowSelects[i].fileChon.indexData].TIENTRINH = 'Lỗi khi lưu: ' + resUpdate
                }
            }
            this.tableConfig.tableData = [].concat(this.tableConfig.tableData)
            this.$toast.success("Cập nhật thành công!")
        },
        async capNhat(data) {
            try {
                this.loading(true)
                let response = await api.capNhat(this.axios, data)
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return 'OK'
                } else {
                    return response.data.message
                }
            } catch (e) {
                this.loading(false)
                if (e.data && e.data.message) {
                    return e.data.message
                } else if (e.response && e.response.data && e.response.data.message) {
                    return e.response.data.message
                } else {
                    return 'Đã xảy ra lỗi'
                }
            }
        },

        showPopUpKhoLuuHoSo() {
            this.dataSendKHS.MA_GD = this.maGiaoDich
            this.txtMaGD = this.maGiaoDich
            this.$refs.popupKhoHoSoRef.show()
        },

        async txtMaGD_KeyDown() {
            this.tableConfig.tableData = []
            tableData = []
            let dataSend = {
                "ma_gd": this.maGiaoDich
            }
            this.loading(true)
            let res = await api.layThongTinTheoMaGD(this.axios, dataSend);
            this.loading(false)
            if (res.data.data && res.data.data.length > 0) {
                res.data.data.forEach((ele, index) => {
                    let obj = {
                        MA_TB: ele.ma_tb,
                        SO_GT: ele.so_gt,
                        FILE_HS_NEW: ele.file_hs_new,
                        TEN_LOAIHD: ele.ten_loaihd,
                        NGAY_YC: ele.ngay_yc,
                        MA_GD: ele.ma_gd,
                        TEN_TB: ele.ten_tb,
                        DIACHI_LD: ele.diachi_ld,
                        TEN_DVVT: ele.ten_dvvt,
                        LOAIHINH_TB: ele.loaihinh_tb,
                        TIENTRINH: ele.tientrinh,
                        hdtb_id: ele.hdtb_id,
                        fileChon: {
                            indexData: index,
                            index: -1,
                        }
                    }
                    this.tableConfig.tableData.push(obj)
                    tableData.push(obj)
                });
            } else {
                this.$toast.error("Không tìm thấy thông tin theo mã GD");
                return;
            }

        },

        checkGhiLai() {

        },

        gridDS_selectedRowChanged(data) {
            this.dsGirdChon = data
        }

    },
};
</script>
<style>
.disabled {
    /* pointer-events: none; */
    cursor: no-drop;
    opacity: 0.7;
}

.disabled-color {
    color: gray !important;
}
</style>
