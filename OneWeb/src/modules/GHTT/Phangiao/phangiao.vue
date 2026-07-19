<template src="./phangiao.html"></template>

<!-- <template>
    <div id="test">
        <div v-show="isShow"> 
            <h2>"TTKD VINAPHONE TP.HCM"</h2>
            <input type="text" v-model="name">
            <input type="checkbox" v-model="acc"> dieu khoan
            <div>
                {{ name }}
            </div>
        </div>   
        <button @click="isShow = ! isShow">An/Hien</button> 
    </div>

</template>  
-->
<script>
import breadcrumb from '@/components/breadcrumb'
import { DatePickerComponent } from "@syncfusion/ej2-vue-calendars"
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
import api from "./api_PhanGiao.js"
import PopupDocFilePhangiao from './PopupDocFilePhangiao.vue'
import { GridComponent, ColumnDirective, ColumnsDirective, GridPlugin, Page, Sort, Filter, Edit } from "@syncfusion/ej2-vue-grids";

MultiSelect.Inject(CheckBoxSelection);

export default {
    name: 'PhanGiaoGHTT',
    components: {
        breadcrumb,
        'ejs-grid': GridComponent,
        'e-column': ColumnDirective,
        'e-columns': ColumnsDirective,
        'e-columns': GridPlugin,
        'e-columns': Page,
        'e-columns': Sort,
        'e-columns': Filter,
        //  'ejs-datepicker': DatePickerComponent,
        // PopupDocFilePhangiao
    },

    data() {
        return {
            btnStatus: {
                btnLayttEnabled: true,
                btnCapnhatEnabled: true,
                btnPhangiaoEnabled: true,
                btnExportphangiaoEnabled: false,
                btnImportphangiaoEnabled: false,
                txtTungayEnabled: false,
                txtDenngayEnabled: false,
            },

            header: {
                title: 'PHÂN GIAO',
            },
            thangnam: {
                value: new Date(),
                start: 'Year',
                depth: 'Year',
                format: 'MM/yyyy'
            },
            ngaythangnam: {
                value: new Date(),
                start: 'Year',
                depth: 'Year',
                format: 'dd/MM/yyyy'
            },
            cboPhongbhT4: {
                dataSource: [],
                value: 0,
            },
            cboDichVuVT: {
                dataSource: [],
                value: 0,
            },
            cboDonViTo: {
                dataSource: [],
                value: 0,
            },
            cboDonViToT2: {
                dataSource: [],
                value: 0,
            },
            cboDonViToT4: {
                dataSource: [],
                value: 0,
            },
            cboDsNhanVien: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            cboDsNhanVienT2: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            cboDsNhanVienT4: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            cboDsTo: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            DsPhangiaoDukien: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            DsThongtinPhangiao: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            DsThongtingiaoNv: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            DsNhanVienTiepnhan: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            DsNhanVienChuyen: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            DsTbNvTiepNhan: {
                dataSource: [],
                value: '',
                selectedDate: "",
            },
            gridDieuChinh: {
                header: [
                    {
                        fieldName: "ma_tb",
                        headerText: "Mã thuê bao",
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: "ten_tb",
                        headerText: "Tên thuê bao",
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: "ten_nv_xly",
                        headerText: "Nhân viên xử lý",
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: "diachi_ld",
                        headerText: "Địa chỉ lắp đặt",
                        allowFiltering: true,
                        allowSorting: false
                    },
                ],
                list: [],
                value: {},
                isEnabled: true,
                checked: [],
            },
            gridTiepNhan: {
                header: [
                    {
                        fieldName: "ma_tb",
                        headerText: "Mã thuê bao",
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: "ten_tb",
                        headerText: "Tên thuê bao",
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: "ten_nv_xly",
                        headerText: "Nhân viên xử lý",
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: "diachi_ld",
                        headerText: "Địa chỉ lắp đặt",
                        allowFiltering: true,
                        allowSorting: false
                    },
                ],
                list: [],
                value: {},
                isEnabled: true,
                checked: [],
            },

            dsHeso: '',
            ds: '',
            chuongtrinhData: [
                { "Id": "1", "Chuongtrinh": "Gia hạn trả trước BRCĐ" },
                { "Id": "2", "Chuongtrinh": "Quản trị TB DĐ" },
            ],
            chuongtrinhFields: { text: "Chuongtrinh", value: "Id" },
            chuongtrinhWaterMark: "Chọn...",

            ketquaData: [
                { "Id": "1", "Ketqua": "1.Đồng ý nhận" },
                { "Id": "2", "Ketqua": "2.Từ chối nhận" }
            ],
            ketquaFields: { text: "Ketqua", value: "Id" },
            ketquaWaterMark: "Chọn...",
            cboKetqua: "1",
            ChuongtrinhT1_Id: "1",
            ChuongtrinhT2_Id: "1",
            ChuongtrinhT4_Id: "1",
            pLyDoTuChoi: "",
            txtSlMatt: "",
            txtSlMatb: "",
            txtSlQuydoi: "",

            pageSettings: { pageSize: 5 },
            grdTab5: {
                dataSource: [],
                value: 0,
            },

            selected_Nhanvien_Count: 0,
            currentTab: 'tab1',
            txtthangnam: new Date(),
            txtthangnam2: new Date(),
            txtDate: new Date(),
            uutien: false,
            data: [],
            // loading: true,
            editSettings: { allowEditing: true },
            pageSettings: { pageSize: 5 },
        }
    },
    provide: {
        grid: [Page, Sort, Filter, Edit]
    },
    components: {
        'ejs-datepicker': DatePickerComponent,
        PopupDocFilePhangiao
    },
    created() {
        this.getdsDVVT()
        this.getdsDonViTo()
        this.getGrdTab5()
        this.getHeSo1()
        this.getdsPhongBH()
        this.getDsNhanvienTiepnhan()
        this.getDsNhanvienYeucauChuyen()

        window.addEventListener('keyup', (e) => {
            if (e.key === 'F2') {
                this.switchTab('tab1')
            } else if (e.key === 'F3') {
                this.switchTab('tab2')
            } else if (e.key === 'F4') {
                this.switchTab('tab3')
            } else if (e.key === 'F5') {
                this.switchTab('tab4')
            } else if (e.key === 'F6') {
                this.switchTab('tab5')
            }
        });
    },
    watch: {
        'cboDsNhanVien.value': {
            handler(newValue) {
                this.selected_Nhanvien_Count = newValue.length;
            },
            deep: true,
        },
        'cboDonViTo.value': {
            handler(newValue) {
                // Reset giá trị của cboDsNhanVien khi chọn giá trị mới trong cboTo
                this.cboDsNhanVien.value = [];
                // Cập nhật số lượng item đã chọn
                this.selected_Nhanvien_Count = 0;
            },
        },
    },
    methods: {
        switchTab(tab) {
            this.currentTab = tab;
            console.log(tab);
            if (tab == 'tab1') {
                this.btnStatus.btnLayttEnabled = true;
                this.btnStatus.btnCapnhatEnabled = true;
                this.btnStatus.btnPhangiaoEnabled = true;
                this.btnStatus.btnExportphangiaoEnabled = false;
                this.btnStatus.btnImportphangiaoEnabled = false;
            } else if (tab == 'tab2') {
                this.btnStatus.btnLayttEnabled = true;
                this.btnStatus.btnCapnhatEnabled = false;
                this.btnStatus.btnPhangiaoEnabled = false;
                this.btnStatus.btnExportphangiaoEnabled = false;
                this.btnStatus.btnImportphangiaoEnabled = false;
            } else if (tab == 'tab3') {
                this.btnStatus.btnLayttEnabled = false;
                this.btnStatus.btnCapnhatEnabled = true;
                this.btnStatus.btnPhangiaoEnabled = false;
                this.btnStatus.btnExportphangiaoEnabled = false;
                this.btnStatus.btnImportphangiaoEnabled = false;
            } else if (tab == 'tab4') {
                this.btnStatus.btnLayttEnabled = true;
                this.btnStatus.btnCapnhatEnabled = true;
                this.btnStatus.btnPhangiaoEnabled = false;
                this.btnStatus.btnExportphangiaoEnabled = false;
                this.btnStatus.btnImportphangiaoEnabled = true;
                this.resetDieuchinh ();
            } else if (tab == 'tab5') {
                this.btnStatus.btnLayttEnabled = true;
                this.btnStatus.btnCapnhatEnabled = true;
                this.btnStatus.btnPhangiaoEnabled = false;
                this.btnStatus.btnExportphangiaoEnabled = false;
                this.btnStatus.btnImportphangiaoEnabled = false;
            }
        },
        openDialog() {
            this.$refs.dlgDocFilePhangiao.openDialog();

        },
        formattedThangNam() {
            const year = this.txtthangnam.getFullYear();
            const month = (this.txtthangnam.getMonth() + 1).toString().padStart(2, '0');
            return `${year}${month}`;
        },
        formattedThangNamTab2() {
            const year = this.txtthangnam2.getFullYear();
            const month = (this.txtthangnam2.getMonth() + 1).toString().padStart(2, '0');
            return `${year}${month}`;
        },
        formattedDate() {
            const year = this.txtDate.getFullYear();
            const month = (this.txtDate.getMonth() + 1).toString().padStart(2, '0');
            return `${year}${month}`;
        },
        async onActionBegin(args) {
            if (args.requestType === 'save' && args.action === 'edit') {
                const modifiedData = args.data; // Dữ liệu đã thay đổi trong grid
                //const allData = this.$refs.cboDsNhanVien.getCurrentViewRecords(); // Lấy toàn bộ dữ liệu trong grid

                let req = {
                    dinhmuc: modifiedData.dinhmuc,
                    donvi_id: this.cboDonViToT2.value,
                    nhanvien_id: modifiedData.nhanvien_id,
                    thang_kt: modifiedData.thang_kt,
                    uutien: "1",
                    chuongtrinh_id: this.ChuongtrinhT2_Id
                }

                let result = null;

                result = await api.postCapnhatCauhinhDinhmuc(this.axios, req);

                if (result.data.data == null) {
                    modifiedData.dinhmuc_nv = '';
                    this.$root.$toast.error('Vui lòng nhập lại !')
                } else if (result.data.data == '2') {
                    this.$root.$toast.warning('cập nhật không thành công, vì nhân viên đã thực hiện phân giao rồi')
                }

            }
        },

        async getNVDinhmucT2() {
            this.getdsNhanVien('2');
        },

        async listData() {
            if (this.currentTab === 'tab1') {
                // List dữ liệu từ form trong tab1
                if (this.cboDonViTo.value == 0) {
                    this.$root.$toast.warning('Bạn chưa chọn Tổ !')
                    return
                } else {
                    let namthang1 = this.formattedThangNam();
                    console.log('List dữ liệu từ form trong tab1 = ' + this.cboDonViTo.value + ',' + namthang1 + ',' + this.ChuongtrinhT1_Id);
                    this.getDsPhangiaoDukien(this.cboDonViTo.value, namthang1, this.ChuongtrinhT1_Id);
                    this.getDsThongtingiaoNv(this.cboDonViTo.value, namthang1, this.ChuongtrinhT1_Id);
                    this.getDsThongtinPhangiao(this.ChuongtrinhT1_Id, namthang1, this.cboDonViTo.value);
                }
            } else if (this.currentTab === 'tab2') {
                // Lưu dữ liệu từ form trong tab2
                //const allData = this.$refs.cboDsNhanVien.currentViewData; // Lấy toàn bộ dữ liệu trong grid
                if (this.cboDonViToT2.value == 0) {
                    this.$root.$toast.warning('Chưa chon phong!')
                    return
                }
                if (this.ChuongtrinhT2_Id == null) {
                    this.$root.$toast.warning('Chưa chon chuong chinh!')
                    return
                }
                this.loading(true)
                console.log('List dữ liệu từ form trong tab2 = ' + this.ChuongtrinhT2_Id + ',' + this.formattedThangNamTab2() + ',' + this.cboDonViTo.value + ',' + this.txtSlMatt);
                this.getDinhMuc(this.cboDonViToT2.value, this.formattedThangNamTab2(), this.ChuongtrinhT2_Id);

            } else if (this.currentTab === 'tab4') {
                // Lấy thông tin thuê bao của nhân viên cần điều chỉnh -->form  tab4
                console.log('List dữ liệu từ form trong tab4 = ' + this.ChuongtrinhT4_Id + ',' + this.formattedThangNam() + ',' + this.cboDsNhanVienT4.value);
                this.loadDsDieuChinh(this.ChuongtrinhT4_Id, this.formattedThangNam(), this.cboDsNhanVienT4.value);
            } else if (this.currentTab === 'tab5') {
                // Lưu dữ liệu trong form tab5
                //const allData = this.$refs.cboDsNhanVien.currentViewData; // Lấy toàn bộ dữ liệu trong grid
                // this.loading(true)
                console.log('List dữ liệu từ form trong tab5 = ' + this.DsNhanVienChuyen.value + ',' + this.formattedThangNam());
                this.getDsTbNvTiepNhan(this.DsNhanVienChuyen.value, this.formattedThangNam());
            }
        },
        async saveData() {
            if (this.currentTab === 'tab1') {
                // Lưu dữ liệu từ form trong tab1
                let namthang1 = this.formattedThangNam();
                let req = {
                    donvi_id: this.cboDonViTo.value,
                    nhanvien_id: this.cboDsNhanVien.value,
                    thang_kt: namthang1,
                    uutien: this.uutien == true ? 1 : 0,
                    chuongtrinh_id: this.ChuongtrinhT1_Id
                }

                let result = null;
                result = await api.postCapnhatCapNhatDsNvGiaoDuKien(this.axios, req);
                console.log(result.data.data[0].kq)
                if (result.data.data[0].kq == '1') {
                    this.$root.$toast.success('Cap nhat ds NV giao du kien thanh cong !')
                } else {
                    this.$root.$toast.warning('cập nhật không thành công')
                }

                //console.log('Lưu dữ liệu từ form trong tab1 = ' + this.ChuongtrinhT1_Id + ',' + this.cboDsNhanVien.value + ',' + namthang1 + ',' + this.cboDonViTo.value + ',' + this.uutien + ',' + this.selected_Nhanvien_Count);

            } else if (this.currentTab === 'tab2') {
                // Lưu dữ liệu từ form trong tab2
                //const allData = this.$refs.cboDsNhanVien.currentViewData; // Lấy toàn bộ dữ liệu trong grid
                console.log('Lưu dữ liệu từ form trong tab2 = ' + this.ChuongtrinhT2_Id + ',' + this.formattedThangNamTab2() + ',' + this.cboDonViTo.value);
            } else if (this.currentTab === 'tab3') {
                let jsonData = [];

                // Lặp qua từng phần tử trong danh sách dsHeso
                this.dsHeso.forEach(hs => {
                    // Lấy giá trị của input text từ mỗi đối tượng hs
                    
                    let hsid = hs.heso_id;
                    let hesoValue = hs.heso;
                    let tenHesoValue = hs.ten_heso;

                    // Tạo một đối tượng JSON mới với giá trị từ input text
                    let jsonItem = {
                        heso_id: hsid,
                        heso: hesoValue,
                        ten_heso: tenHesoValue
                    };

                    // Thêm đối tượng JSON vào mảng
                    jsonData.push(jsonItem);
                });

                console.log(jsonData)
                // let hsData = [];
                // Hs.forEach(item => {
                //     let data = {
                //         heso_id: item.heso_id,
                //         heso: item.heso,
                //         ten_heso: item.ten_heso,
                //     }
                //     hsData.push(data);
                // })		

                let result = null;
                result = await api.postCapnhatCauhinhHeso(this.axios, jsonData);
                //console.log(result)

                if (result.data.data[0].kq == '1') {
                      this.$root.$toast.success('Cập nhật thành công !')
                  } else {
                      this.$root.$toast.warning('cập nhật không thành công')
                  }
            } else if (this.currentTab === 'tab4') { // Lưu dữ liệu từ form trong tab4
                if (this.cboPhongbhT4.value == 0) {
                    this.$root.$toast.warning('Chưa chọn phòng tiếp nhận!')
                    return
                }
                if (this.cboDsTo.value == 0) {
                    this.$root.$toast.warning('Chưa chọn tổ tiếp nhận!')
                    return
                }
                if (this.DsNhanVienTiepnhan.value == 0) {
                    this.$root.$toast.warning('Chưa chọn nhân viên tiếp nhận!')
                    return
                }
                let tiepnhan = this.gridTiepNhan.list
                let nhanvien_nhan_id = this.cboDsNhanVienT4.value
                let nhanvien_chuyen_id = this.DsNhanVienTiepnhan.value
                let dsData = [];
                tiepnhan.forEach(item => {
                    let data = {
                        ghtt_id: item.ghtt_id,
                        ma_tb: item.ma_tb,
                        thang_kt: item.thang_kt,
                        nhanvien_nhan_id: nhanvien_nhan_id,
                        nhanvien_chuyen_id: nhanvien_chuyen_id
                    }

                    dsData.push(data);

                })
                if (dsData == 0) {
                    this.$root.$toast.warning('Không có số liệu thuê bao tiếp nhận !')
                    return
                } else {
                    let result = null;
                    result = await api.postluu_dsThuebao_chuyen_thucong(this.axios, dsData);
                    console.log(dsData)
                    if (result.data.data[0].kq == "1") {
                        this.gridTiepNhan.list = [];
                        this.$root.$toast.success('Tiếp nhận thành công !')
                        return
                    } else {
                        this.$root.$toast.error('Tiếp nhận không thành công bạn hãy xem lại dữ liệu!')
                        return
                    }
                }
            } else if (this.currentTab === 'tab5') { // Lưu dữ liệu từ form trong tab5
                let nhanvienchuyenId = this.DsNhanVienChuyen.value
                let thangKt = this.formattedThangNam()
                let ketqua = this.cboKetqua
                let lydo = this.pLyDoTuChoi
                console.log(this.cboKetqua);


                let data = {
                    nhanvien_chuyen_id: nhanvienchuyenId,
                    thang_kt: thangKt,
                    ketqua: ketqua,
                    lydo: lydo

                }
                let result = null;
                result = await api.postXacNhanDieuChinh(this.axios, data);
                if (result.data.data[0].kq == '1') {
                    this.$root.$toast.success('Xác nhận điều chỉnh thành công !')
                } else {
                    this.$root.$toast.warning('Có lỗi trong xác nhận điều chỉnh !')
                }
            }
        },

        async Phangiao() {
            if (this.currentTab === 'tab1') {
                if (this.formattedThangNam() > this.formattedDate()) {
                    let result = null;
                    let namthang1 = '{"thang_kt":' + this.formattedThangNam() + '}';
                    this.loading(true)
                    result = await api.postDsthuchienPhangiao(this.axios, namthang1);
                    if (result.data.data[0].kq == '1') {
                        this.$root.$toast.success('Thực hiện phân giao thành công !')
                    } else {
                        this.$root.$toast.warning('Không thực hiện được phân giao!')
                    }
                    this.loading(false)
                } else {
                    this.$root.$toast.warning('Tháng kết thúc phải lớn hơn tháng hiện tại !')
                }
            }
        },

        resetDinhmuc() {
            this.txtSlMatt = ""
            this.txtSlMatb = ""
            this.txtSlQuydoi = ""
            this.cboDsNhanVienT2.dataSource = []
        },
        resetDieuchinh() {
            this.cboDonViTo.dataSource =[];
            this.cboDsNhanVienT4.dataSource =[];
            this.cboPhongbhT4.dataSource=[];
            this.cboDsTo.dataSource=[];
            this.DsNhanVienTiepnhan.dataSource=[];
            this.gridDieuChinh.list = [];
            this.gridTiepNhan.list = [];
            this.getdsDonViTo();
            this.getdsPhongBH();
        },


        async getdsPhongBH() {
            try {
                let response = await api.getcboPhongBH(this.axios);
                this.cboPhongbhT4.dataSource = response.data.data
            } catch (e) {
                console.log(e)
            }
        },
        async getdsDVVT() {
            try {
                let response = await api.cboDichVuVT(this.axios);
                this.cboDonViTo.dataSource = response.data.data
            } catch (e) {
                console.log(e)
            }
        },
        async getdsDonViTo() {
            try {
                let response = await api.getcboDonViTo(this.axios);
                this.cboDonViTo.dataSource = response.data.data
            } catch (e) {
                console.log(e)
            }
        },
        async getdsNhanVien(tab) {
            try {

                if (tab == '1') {
                    this.DsPhangiaoDukien.dataSource = [];
                    this.DsThongtingiaoNv.dataSource = [];
                    this.DsThongtinPhangiao.dataSource = [];
                    let vdonviId = this.cboDonViTo.value;
                    let response = await api.getdsNvPhanGiaoDuKien(this.axios, vdonviId, this.formattedThangNam(), this.ChuongtrinhT2_Id);
                    this.cboDsNhanVien.dataSource = response.data.data
                } else if (tab == '2') {
                    let vdonviId = this.cboDonViToT2.value;
                    let response = await api.getcboNhanVienDinhmuc(this.axios, vdonviId, this.formattedThangNamTab2(), this.ChuongtrinhT2_Id);
                    this.cboDsNhanVienT2.dataSource = response.data.data
                    console.log(response.data.data)
                    //this.processNhanvienData(response.data.data)
                }
                else if (tab == '4') {
                    let vdonviId = this.cboDonViToT4.value;
                    //let response = await api.getcboNhanVien(this.axios, vdonviId);
                    let response = await api.getcboDsNhanvienChuyen(this.axios, this.ChuongtrinhT4_Id, this.formattedThangNam(), vdonviId);
                    console.log(response.data.data)
                    this.cboDsNhanVienT4.dataSource = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
        },
        processNhanvienData(data) {//khong dung
            // Thêm thuộc tính mới cho mỗi đối tượng trong mảng
            this.cboDsNhanVienT2.dataSource = data.map(item => ({ ...item, thangdinhmuc: this.formattedThangNamTab2(), dinhmuc_nv: '' }));
            console.log(this.cboDsNhanVien.dataSource)
        },
        async getGrdTab5() {
            try {
                let response = await api.getcboDichVuVT(this.axios);
                this.grdTab5.dataSource = response.data.data
                console.log(response.data)
            } catch (e) {
                console.log(e)
            }
        },
        async getHeSo1() {
            try {
                let response = await api.getcboHeSoQuyDoi(this.axios);
                this.dsHeso = response.data.data
                console.log(response.data.data)
            } catch (e) {
                console.log(e)
            }
        },
        async getDinhMuc(donviId, thangKt, cTrinh) {
            try {
                let response = await api.getcboDinhMuc(this.axios, donviId, thangKt, cTrinh);
                console.log(response.data.data)
                const items = response.data.data
                const item = items[0]
                this.txtSlMatt = item.sl_mtt
                this.txtSlMatb = item.sl_matb
                this.txtSlQuydoi = item.sl_quydoi
                this.loading(false)
                // this.txtSlMatt = response.data.data[0].sl_mtt
                // this.txtSlMatb=response.data.data[0].sl_matb
                // this.txtSlQuydoi=response.data.data[0].sl_quydoi
            } catch (e) {
                console.log(e)
            }
        },
        async getDsPhangiaoDukien(donviId, thangKt, cTrinh) {
            try {
                this.loading(true)
                let response = await api.getChiSoPhanGiaoDuKien(this.axios, donviId, thangKt, cTrinh);
                this.DsPhangiaoDukien.dataSource = response.data.data
                console.log(response.data.data)
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }

        },
        async getDsThongtingiaoNv(donviId, thangKt, cTrinh) {
            try {
                let response = await api.getThongTinDuLieuGiaoNV(this.axios, donviId, thangKt, cTrinh);
                this.DsThongtingiaoNv.dataSource = response.data.data
                console.log(response.data.data)
            } catch (e) {
                console.log(e)
            }
        },
        async getDsThongtinPhangiao(cTrinh, thangKt, donviId) {
            try {
                let response = await api.getcboThongTinThuebaoPhanGiaoNV(this.axios, cTrinh, thangKt, donviId);
                this.DsThongtinPhangiao.dataSource = response.data.data
                console.log(response.data.data)
            } catch (e) {
                console.log(e)
            }
        },
        async getDsTo() {
            this.cboDsTo.dataSource = [];
            this.DsNhanVienTiepnhan.dataSource = [];
            let zdonviId = this.cboPhongbhT4.value;
            let response = await api.getcboDsTo(this.axios, zdonviId);
            this.cboDsTo.dataSource = response.data.data
            console.log(response.data.data)
        },
        async getDsNhanvienTiepnhan() {
            this.DsNhanVienTiepnhan.dataSource = [];
            let vdonviId = this.cboDsTo.value;
            let response = await api.getcboNhanVien(this.axios, vdonviId);
            console.log(response.data.data)
            this.DsNhanVienTiepnhan.dataSource = response.data.data
        },
        async loadDsDieuChinh(cTrinh, thangKt, nhanvienId) {
            let response = await api.getcboDsTbNvCanDieuChinh(this.axios, cTrinh, thangKt, nhanvienId);
            this.gridDieuChinh.list = response.data.data
        },
        // tab5
        async getDsNhanvienYeucauChuyen() {
            let thangId = this.formattedThangNam();
            let response = await api.getcboDsNhanvienYeucauChuyen(this.axios, thangId);
            this.DsNhanVienChuyen.dataSource = response.data.data
            console.log(response.data.data)
        },
        async getDsTbNvTiepNhan(nhanvienId, thangGiao) {
            let response = await api.getcboDsTbNvTiepNhan(this.axios, nhanvienId, thangGiao);
            this.DsTbNvTiepNhan.dataSource = response.data.data
            console.log(response.data.data)
        },

        gan() {
            //this.loading(true);
            var dagan = this.gridDieuChinh.list;
            var chuagan = this.gridTiepNhan.list;
            var checkedChuaGan = this.dataCheckedsTiepNhan;
            if (checkedChuaGan.length == 0) {
                this.loading(false);
                return;
            }
            for (var i = 0; i < checkedChuaGan.length; i++) {
                dagan.push(checkedChuaGan[i]);
                const index = chuagan.findIndex((x) => x == checkedChuaGan[i]);
                if (index > -1) {
                    chuagan.splice(index, 1);
                }
            }

            setTimeout(() => {
                this.gridDieuChinh.list = [];
                this.gridTiepNhan.list = [];
                this.dataCheckedsDieuChinh = [];
                this.dataCheckedsTiepNhan = [];
                this.gridDieuChinh.list = dagan;
                this.gridTiepNhan.list = chuagan;
                this.loading(false);
            }, 200);
        },
        boGan() {
            //this.loading(true);
            var dagan = this.gridDieuChinh.list;
            var chuagan = this.gridTiepNhan.list;
            var checkedGan = this.dataCheckedsDieuChinh;
            if (checkedGan.length == 0) {
                this.loading(false);
                return;
            }
            for (var i = 0; i < checkedGan.length; i++) {
                chuagan.push(checkedGan[i]);
                const index = dagan.findIndex((x) => x == checkedGan[i]);
                console.log(checkedGan);

                if (index > -1) {
                    dagan.splice(index, 1);
                }
            }

            setTimeout(() => {
                this.gridDieuChinh.list = [];
                this.gridTiepNhan.list = [];
                this.dataCheckedsDieuChinh = dagan;
                this.gridDieuChinh.list = dagan;
                this.gridTiepNhan.list = chuagan;
                this.loading(false);
                this.dataCheckedsDieuChinh =[];
            }, 200);

        },

        dataCheckedsGan(data) {
            this.dataCheckedsDieuChinh = data;
        },

        dataCheckedsChuaGan(data) {
            this.dataCheckedsTiepNhan = data;
        },
    }
}

</script>

<style>