<template>
    <div>
        <div class="main-wrapper">
            <div class="breadcrumb-top">
                <div class="main-title">Cập nhật liên hệ thanh toán theo File</div>
                <ul class="breadcrumb">
                    <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                    <li class="breadcrumb-item"><a href="#">Quản lý DB</a></li>
                    <li class="breadcrumb-item active">Cập nhật danh bạ thanh toán theo File</li>
                </ul>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a class="hand_click" @click.prevent="ghiLai" :class="{'disabled': disble_nut.chapnhan}">
                            <span class="icon one-save"></span>Ghi lại
                        </a>
                    </li>
                    <li>
                        <a class="hand_click" @click.prevent="xuatFile" :class="{'disabled': disble_nut.xuatexcel}">
                            <span class="icon one-excel"></span>Xuất Excel
                        </a>
                    </li>
                </ul>
            </div>
            <div class="page-content">
                <div class="box-form">
                    <div class="text-main">
                        <div class="inline note marb10 fw6"> <span class="one-alert text-warning f20 inline vcenter"></span> File excel gồm các trường: MA_TT, SDTTT, EMAIL</div> <br>
                        <div class="info-row">
                            <div class="key w90" style="color: black;">
                                Đường dẫn
                            </div>
                            <div class="value">
                                <div class="input-more-button">                   
                                    <button class="btn" @click="uploadFile">
                                        <span class="-ap icon-more_horiz"></span>
                                        <input ref="fileInput" type="file" id="upload" style="display:none" @change="importExcel"
                                        accept=".xls, .xlsx" @click="$refs.fileInput.value=null" />
                                    </button>                 
                                    <input type="text" v-model="tenFile" class="form-control ">
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- <b-tabs>
                        <b-tab :active="activeTabs"> 
                            <template #title>
                                <span class="icon nc-icon-glyph design_bullet-list-67"></span> Danh sách cần cập nhật
                            </template>           
                            <DataGrid v-bind:columns="danhsach.headers"
                                v-bind:dataSource="danhsach.data"
                                :showColumnCheckbox="false"
                                :showFilter="true"
                                :allowPaging="true"
                                :enablePagingServer="false">
                            </DataGrid>              
                        </b-tab>
                        <b-tab :active="!activeTabs">
                            <template #title>
                                <span class="one-alert text-warning inline vcenter"></span> Lỗi
                            </template>
                            <DataGrid v-bind:columns="danhsach_loi.headers"
                                v-bind:dataSource="danhsach_loi.data"
                                :showColumnCheckbox="false"
                                :showFilter="true"
                                :allowPaging="true"
                                :enablePagingServer="false">
                            </DataGrid>  
                        </b-tab>
                    </b-tabs> -->
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        <span class="icon nc-icon-glyph design_bullet-list-67"></span> Danh sách cần cập nhật
                    </div>
                    <DataGrid v-bind:columns="danhsach.headers"
                        v-bind:dataSource="danhsach.data"
                        :showColumnCheckbox="false"
                        :showFilter="true"
                        :allowPaging="true"
                        :enablePagingServer="false">
                    </DataGrid>
                </div>
            </div>
        </div>
        <ModalXuatDuLieu id="modal-xuat-dulieu" :thuocTinh="dsThuocTinh" :data="danhsach.data"/>
        <b-modal ref="popupErrorImport" size="xl" body-class="p-0" hide-header hide-header-close hide-footer>
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-notepad"></span>Danh sách lỗi</div>
                    <div class="close -ap icon-close" @click="hideModal"></div>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <DataGrid v-bind:columns="danhsach_loi.headers" 
                            v-bind:dataSource="danhsach_loi.data"
                            :showColumnCheckbox="false"
                            :showFilter="true"
                            :allowPaging="true"
                            :enablePagingServer="false">
                        </DataGrid>  
                    </div>
                </div>
            </div>
        </b-modal>
    </div>
</template>
<style scoped>
.hand_click { cursor: pointer; }
</style>
<script>
import xlsx from "xlsx";
import api from "./api";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import ModalXuatDuLieu from "../../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
export default {
    components: { breadcrumb, gridview, ModalXuatDuLieu },
    name: "",
    data() {
        return {
            tenFile: '',
            //activeTabs: true,
            danhsach: {
                headers: [
                    {
                        fieldName: "MA_TT",
                        headerText: "Mã TT",
                        allowFiltering: true,
                        textAlign: "Center",
                        //width: 100,
                    },
                    {
                        fieldName: "SDTTT",
                        headerText: "SDTTT",
                        allowFiltering: true,
                        textAlign: "Center",
                        //width: 100,
                    },
                    {
                        fieldName: "EMAIL",
                        headerText: "Email",
                        allowFiltering: true,
                        textAlign: "Center",
                        //width: 100,
                    }
                ],
                data: []
            },
            danhsach_loi: {
                headers: [
                    {
                        fieldName: "STT",
                        headerText: "STT",
                        allowFiltering: true,
                        textAlign: "Center",
                        width: 30
                    },
                    {
                        fieldName: "MA_TT",
                        headerText: "Mã TT",
                        allowFiltering: true,
                        textAlign: "Center",
                        width: 100,
                    },
                    {
                        fieldName: "SDTTT",
                        headerText: "SDTTT",
                        allowFiltering: true,
                        textAlign: "Center",
                        width: 100,
                    },
                    {
                        fieldName: "EMAIL",
                        headerText: "Email",
                        allowFiltering: true,
                        textAlign: "Center",
                        width: 100,
                    },
                    {
                        fieldName: "LYDO_LOI",
                        headerText: "Lý do lỗi",
                        textAlign: "Center",
                        allowFiltering: true
                    }
                ],
                data: []
            },
            disble_nut: {
                chapnhan: false,
                xuatexcel: false
            }
        }
    },
    computed: {
        dsThuocTinh() {
            return this.danhsach.headers.map((x) => x.fieldName);
        }
    },
    methods: {
        async importExcel(e) {
            const files = e.target.files;
            console.log(e);
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$root.toastError("File excel không đúng định dạng!");
            }
            if( files[0].name ) {
                this.tenFile = files[0].name
            }
            this.readFile(files);
        },
        setButton(kieu) {
            if (kieu == 4) {
                this.disble_nut.chapnhan = true;
                this.disble_nut.xuatexcel = true;
            }

            if (kieu == 5) {
                this.disble_nut.chapnhan = false;
                this.disble_nut.xuatexcel = false;
            }
        },
        kTraCapNhatTTLH_TT: async function() {
            let result = [];
            await api.checkCapNhatTTLH_TT(this.axios).then(res => {
                let ds = res.data.data ? res.data.data : [];
                if (ds.length > 0) {
                    for (let i=0; i<ds.length; i++) {
                        let obj = {
                            STT: ds[i].stt,
                            MA_TT: ds[i].ma_tt,
                            SDTTT: ds[i].sdttt,
                            EMAIL: ds[i].email,
                            LYDO_LOI: ds[i].lydo_loi
                        };
                        result.push(obj);
                    }
                }
            });
            return result;
        },
        docFileCapNhatTTLH: async function() {
            let result = [];
            await api.spDocFileCapNhatTTLH(this.axios, {
                "p_nguoidung_id": this.$root.token.getNguoiDungID(),
                "p_kieu_id": 42
            }).then(res => {
                let ds = res.data.data ? res.data.data : [];
                if (ds.length > 0) {
                    for (let i=0; i<ds.length; i++) {
                        let obj = {
                            MA_TT: ds[i].ma_tt,
                            SDTTT: ds[i].sdttt,
                            EMAIL: ds[i].email,
                        };
                        result.push(obj);
                    }
                }
            })
            return result;
        },
        docFile: async function() {
            // DoWork
            this.danhsach.data = await this.docFileCapNhatTTLH();
            this.setButton(5);
        },
        kiemTraFile: async function() {
            // DoWork
            this.danhsach_loi.data = await this.kTraCapNhatTTLH_TT();
            this.setButton(5);

            // RunWorkerCompleted
            if (this.danhsach_loi.data.length > 0) {
                //this.activeTabs = false;
                this.showModal();
                return;
            } else {
                //this.activeTabs = true;
                this.setButton(4);
                await this.docFile();
                return;
            }
        },
        readFile(files) {
            const fileReader = new FileReader();
            fileReader.onload = async (ev) => {
                try {
                    // Khai báo biến read excel
                    const data = ev.target.result;
                    const XLSX = xlsx;
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    });
                    const wsname = workbook.SheetNames[0];
                    console.log(wsname);
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
                    const excellist = [];
                    if (ws.length > 0) {
                        for (var i = 0; i < ws.length; i++) {
                            ws[i].STT = i+1;
                            excellist.push(ws[i]);
                        }
                    } else {
                        this.$root.toastError("File không có data!");
                    }

                    // Kiểm tra dữ liệu xem có đúng form theo mẫu
                    this.fieldsRender = this.fields;
                    if (
                        !excellist[0].hasOwnProperty("MA_TT") ||
                        !excellist[0].hasOwnProperty("SDTTT") /*||
                        !excellist[0].hasOwnProperty("EMAIL") // Dòng này bỏ vì ĐHSXKD cũ cho phép Email*/
                    ) {
                        this.$root.toastError("Tên cột trong file không đúng định dạng: MA_TT, SDTTT");
                        return;
                    }

                    console.log(excellist);
                    this.danhsach_loi.data = [];
                    this.danhsach.data = [];

                    // Kiểm tra NULL
                    for (let k = 0; k < excellist.length; k++) {
                        let item = excellist[k];
                        let obj = {
                            STT: k+1,
                            MA_TT: item.MA_TT,
                            SDTTT: item.SDTTT,
                            EMAIL: item.EMAIL
                        };
                        if (!item.MA_TT || item.MA_TT.toString().trim().length == 0) {
                            obj.LYDO_LOI = "Mã TT null"
                            this.danhsach_loi.data.push(obj);
                        } else {
                            obj.LYDO_LOI = "";
                        }
                    }
                    if (this.danhsach_loi.data.length > 0) { // Có danh sách lỗi thì hiển thị ra và không làm gì tiếp theo nữa
                        //this.activeTabs = false;
                        this.showModal();
                        return;
                    }

                    // Kiểm tra lặp
                    const lookup = excellist.reduce((a, e) => {
                        a[e.MA_TT] = ++a[e.MA_TT] || 0;
                        a[e.SDTTT] = ++a[e.SDTTT] || 0;
                        a[e.EMAIL] = ++a[e.EMAIL] || 0;
                        return a;
                    }, {});
                    let ds_loi = excellist.filter(e => lookup[e.MA_TT] && lookup[e.SDTTT] && lookup[e.EMAIL]);
                    if (ds_loi.length > 0) { // Có danh sách lỗi thì hiển thị ra và không làm gì tiếp theo nữa
                        for (let i = 0; i < ds_loi.length; i++) {
                            let obj = {
                                STT: ds_loi[i].STT,//i+1,
                                MA_TT: ds_loi[i].MA_TT,
                                SDTTT: ds_loi[i].SDTTT,
                                EMAIL: ds_loi[i].EMAIL,
                                LYDO_LOI: "MA_TT, SDTTT, EMAIL bị lặp lại"
                            };
                            this.danhsach_loi.data.push(obj);
                        }
                        //this.activeTabs = false;
                        this.showModal();
                        return;
                    } 

                    // Nếu dữ liệu file OK rồi thì cập nhật thông tin liên hệ
                    await api.updateThongTinLienHe(this.axios, {
                        "p_ds_ttlh": JSON.stringify(excellist),
                        "p_kieu_id": 42,
                        "p_nguoidung_id": Number(this.$root.token.getNguoiDungID())
                    }).then((res) => {
                        if (res.data && res.data.error_code == "BSS-00000000") {
                            this.$root.toastSuccess("Cập nhật thông tin liên hệ thành công!");
                        } else {
                            this.$root.toastError(res.data.error_code + ' ' + res.data.message);
                            return;
                        }
                        
                    });
                    this.setButton(4);
                    await this.kiemTraFile();
                } catch (e) {
                    return this.$root.toastError("Read failure! " + e);
                }
            };
            fileReader.readAsBinaryString(files[0]);
        },
        uploadFile() {
            this.$refs.fileInput.click();
        },
        xuatFile() {
            if (this.danhsach.data.length == 0) {
                this.$root.toastError("Không có dữ liệu để xuất File!");
                return;
            }
            this.$bvModal.show("modal-xuat-dulieu");
        },
        ghiLai: async function() {
            if (this.danhsach.data.length == 0) {
                this.$root.toastError("Chưa có dữ liệu để cập nhật!");
                return;
            }
            let self = this;
            await api.updateTTLHThanhToan(this.axios).then((res) => {
                if (res.data && res.data.error_code == "BSS-00000000") {
                    //this.$root.toastSuccess("Gán đơn vị chăm sóc khách hàng lớn thành công!");
                    this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
                } else {
                    this.$root.toastError(res.data.message_detail);
                }
            }).catch(err => {
                self.$root.toastError("Có lỗi: " + err.message);
            });
            this.setButton(5);
        },
        showModal() {
            this.$refs['popupErrorImport'].show();
        },
        hideModal() {
            this.$refs['popupErrorImport'].hide();
        },
    }
}
</script>
