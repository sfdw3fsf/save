<template src='./index.html'></template>
<script>
import Vue from 'vue'
import moment from 'moment'
import { TabPlugin, TabComponent } from '@syncfusion/ej2-vue-navigations'
import tem0 from './components/template0'
import tem1 from './components/template1'
import tem2 from './components/template2'
import popupNgayTongLuc from './components/template3/popupNgayTongLuc'
import store from './store/index'
import XLSX from 'xlsx'

Vue.use(TabPlugin)
export default {
    provide: {},
    components: {
        moment,
        popupNgayTongLuc
    },
    // props: ['tag'],
    async created() {
        // this.handleTag();
        // await this.frmHoanCong_DangKyGoi_DaDV_Load();

        await this.initData()
    },
    data() {
        return {
            disabledXuatDsTB: true,
            chkTinhTrang: false,
            chkTuDen: false,
            doiTuongPhanGiao: {
                list: [],
                selected: 1
            },
            phangiaoTuDong: 0,
            disabledExportError: true,
            currentTab: 0,
            header: {
                title: 'QUẢN LÝ THÔNG TIN THUÊ BAO CHIẾN DỊCH',
                list: [
                    { name: 'Chiến  dịch  B2A', link: { name: 'Ui.cards' } },
                    {
                        name: 'Quản  lý  chiến dịch',
                        link: { name: 'Ui.buttons' }
                    }
                ]
            },
            disabledCreate: false,
            vnhanvien_id: 0,
            dateFormat: 'DD/MM/YYYY',
            tt_tim_kiem: {
                selectedChienDich: 0,
                chien_dich: [],
                ThoiHanTu: '',
                Den: '',
                selectedTinhTrang: "",
                cboTinhTrang: []
            },
            tt_chien_dich: {
                cd_id: null,
                ma_cd: '',
                ten_cd: '',
                ngayApdung: '',
                ngayDen: '',
                kenhtiepcan: [],
                selectedKenhTiepCan: '',
                loaichiendich: [],
                selectedLoaiChienDich: '',
                douutien: [
                    {
                        ten: '1',
                        id: 1
                    },
                    {
                        ten: '2',
                        id: 2
                    },
                    {
                        ten: '3',
                        id: 3
                    },
                    {
                        ten: '4',
                        id: 4
                    },
                    {
                        ten_do_ut: '5',
                        do_ut_id: 5
                    }
                ],
                selectedDoUuTien: '',
                tinh_trang: [],
                selectedTinhTrang: '',
                noidung: '',
                gannhan: '',
                linkcv: '',
                soSrv: '',
                soEoffice: '',
                ghichu: ''
            },
            ds_chien_dich: {
                list: [],
                columns: [
                    {
                        headerText: 'Tên Tập',
                        allowSorting: true,
                        fieldName: 'tap',
                        isGroupedColumn: true
                    },
                    {
                        headerText: 'Số máy/Acc',
                        allowSorting: true,
                        fieldName: 'ma_tb'
                    },
                    {
                        headerText: 'Tên TB',
                        allowSorting: true,
                        fieldName: 'ten_tb'
                    },
                    {
                        headerText: 'Loại hình',
                        allowSorting: true,
                        fieldName: 'loaihinh_tb',
                        width: '100'
                    },
                    {
                        headerText: 'SRV',
                        allowSorting: true,
                        fieldName: 'srv',
                        width: '100'
                    },
                    {
                        headerText: 'Mã TT',
                        allowSorting: true,
                        fieldName: 'ma_tt',
                        width: '120'
                    },
                    {
                        headerText: 'Mã TB',
                        allowSorting: true,
                        fieldName: 'ma_kh',
                        width: '120'
                    },
                    {
                        headerText: 'Địa chỉ LĐ',
                        allowSorting: true,
                        fieldName: 'diachi_ld'
                    },
                    {
                        headerText: 'Tên TT',
                        allowSorting: true,
                        fieldName: 'ten_tt'
                    },
                    {
                        headerText: 'Địa chỉ TT',
                        allowSorting: true,
                        fieldName: 'diachi_tt'
                    },
                    {
                        headerText: 'Tên KH',
                        allowSorting: true,
                        fieldName: 'ten_kh'
                    },
                    {
                        headerText: 'Số điện thoại',
                        allowSorting: true,
                        fieldName: 'so_dt',
                        width: '120'
                    },
                    {
                        headerText: 'Mã NVTC',
                        allowSorting: true,
                        fieldName: 'manv_tc',
                        width: '120'
                    },
                    {
                        headerText: 'Doanh thu',
                        allowSorting: true,
                        fieldName: 'doanhthu'
                    },
                    {
                        headerText: 'Trung tâm',
                        allowSorting: true,
                        fieldName: 'trungtam'
                    },
                    {
                        headerText: 'Phòng',
                        allowSorting: true,
                        fieldName: 'phong'
                    },
                    {
                        headerText: 'Tổ QL',
                        allowSorting: true,
                        fieldName: 'to_ql'
                    },
                    {
                        headerText: 'Khu vực',
                        allowSorting: true,
                        fieldName: 'khuvuc'
                    },
                    {
                        headerText: 'Mã NV HRM',
                        allowSorting: true,
                        fieldName: 'ma_nv'
                    },
                    {
                        headerText: 'Tên NV',
                        allowSorting: true,
                        fieldName: 'ten_nv'
                    },
                    {
                        headerText: 'Người CN',
                        allowSorting: true,
                        fieldName: 'nguoi_cn'
                    },
                    {
                        headerText: 'Ngày CN',
                        allowSorting: true,
                        fieldName: 'ngay_cn'
                    },
                    {
                        headerText: 'Tập',
                        allowSorting: true,
                        fieldName: 'tap'
                    },
                    {
                        headerText: 'Mã phiếu',
                        allowSorting: true,
                        fieldName: 'ma_phieu'
                    }
                ]
            },
            file_excel: {
                duong_dan: '',
                ten_tap: '',
                ma_tap: '',
                list: [],
                columns: [
                    {
                        headerText: 'Số TB',
                        allowSorting: true,
                        fieldName: 'ma_tb',
                        width: '100'
                    },
                    {
                        headerText: 'Loại TB',
                        allowSorting: true,
                        fieldName: 'loaitb_id',
                        width: '100'
                    },
                    {
                        headerText: 'Thông tin lỗi',
                        allowSorting: true,
                        fieldName: 'noidung',
                        width: '100'
                    },
                    // {
                    //     headerText: 'Trạng thái',
                    //     allowSorting: true,
                    //     fieldName: 'trang_thai',
                    //     width: "100",
                    // },
                    {
                        cellCssClass: 'text-danger',
                        textAlign: 'center',
                        headerText: 'Trạng thái',
                        allowSorting: true,
                        fieldName: 'trang_thai',
                        template: function () {
                            return {
                                template: Vue.component('columnTemplate', {
                                    template: `
                                    <row @click='del'>
                                        <a>
                                            <span v-if="data.trangthai_id =='-1'" class='fa fa-warning'></span>
                                            <span v-else class='fa fa-check'></span>
                                        </a>
                                    </row>
                                    `,
                                    data() {
                                        return {
                                            data: {}
                                        }
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent
                                        }
                                    },
                                    methods: {
                                        async del() {
                                            console.log(this.data)
                                            // this.$parent.$parent.$parent.del(this.data);
                                            // this.$eventHub.$emit("detail", this.data);
                                        }
                                    }
                                }),
                                watch: {
                                    data: function () {
                                        console.log('abc: ', this.data)
                                    }
                                }
                            }
                        },
                        width: '50'
                    }
                ]
            },
            // TABS
            headerText0: { text: 'Đơn vị áp dụng' },
            headerText1: { text: 'Loại hình TB' },
            headerText2: { text: 'Thành viên kênh' },
            content0: function () {
                return {
                    template: Vue.component('template0', tem0)
                }
            },
            content1: function () {
                return {
                    template: Vue.component('template1', tem1)
                }
            },
            content2: function () {
                return {
                    template: Vue.component('template2', tem2)
                }
            },
            tabSelectedIndex: 0,
            js_tinh: [],
            js_lhtb: [],
            js_kenh: []
        }
    },
    methods: {
        SelectionChanged(item){
            // console.log('xxxx');
            // if (item && item.length > 0) {
                
            // }   
        },
        onPhanGiaoDaGiaoExportClicked() {
            this.exportExcelByCols(this.ds_chien_dich.list, this.ds_chien_dich.columns)
        },
        exportExcelByCols(inputArray, arrayFields) {
            let exportData = []
            exportData = inputArray.map((e) => {
                let exportEntity = {}
                arrayFields.map((x) => {
                    exportEntity[x.fieldName] = e[x.fieldName]
                })
                return exportEntity
            })
            if (exportData.length > 0) {
                let worksheet = XLSX.utils.json_to_sheet(exportData)
                let workbook = XLSX.utils.book_new()
                XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
                XLSX.writeFile(workbook, 'export.xlsx')
            } else {
                this.$toast.warning('Không có dữ liệu để xuất file!')
            }
        },
        async xuatExcel() {
            try {
                document.getElementById('exportexcel').click()
            } catch (ex) {
                this.$toast.error('Có lỗi trong quá trình xuất file Excel !' + ex)
            }
        },
        onChangeCD(e) {
            console.log(this.tt_tim_kiem.selectedChienDich, e)
        },
        uploadFile() {
            console.log('x1')
            // kiem tra nu chua chon chien dich
            if (!this.tt_tim_kiem.selectedChienDich) {
                this.$toast.warning('Bạn chưa chọn chiến dịch')
                return
            }
            if (!this.file_excel.ma_tap) {
                this.$toast.warning('Bạn chưa nhập mã tập')
                return
            }
            if (!this.file_excel.ten_tap) {
                this.$toast.warning('Bạn phải nhập thông tin tên tệp')
                return
            }
            this.$refs.fileInput.click()
        },
        async importExcel(e) {
            console.log('x2')


            // ket thuc kiem tra
            const files = e.target.files
            console.log(e, this.$refs.fileInput)
            if (!files.length) {
                return
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$root.toastError('File excel không đúng định dạng!')
            }
            if (files[0].name) {
                this.file_excel.duong_dan = files[0].name
                // this.file_excel.ten_tap = files[0].name
            }
            this.readFile(files)
        },
        readFile(files) {
            const fileReader = new FileReader()
            fileReader.onload = async (ev) => {
                try {
                    // Khai báo biến read excel
                    const data = ev.target.result
                    // const XLSX = xlsx
                    const workbook = XLSX.read(data, {
                        type: 'binary'
                    })
                    const wsname = workbook.SheetNames[0]
                    console.log(wsname, workbook)
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
                    let excellist = []
                    if (ws.length > 0) {
                        for (var i = 0; i < ws.length; i++) {
                            // ws[i].STT = i+1;
                            if(!ws[i]['MA_HRM']){
                                ws[i]['MA_HRM'] = "";
                            }
                            excellist.push(ws[i])
                        }
                    } else {
                        this.$root.toastError('File không có data!')
                    }

                    // Kiểm tra dữ liệu xem có đúng form theo mẫu
                    this.fieldsRender = this.fields
                    if (
                        !excellist[0].hasOwnProperty('MA_TB') ||
                        !excellist[0].hasOwnProperty('LOAITB_ID')
                    ) {
                        this.$root.toastError('Tên cột trong file không đúng định dạng: MA_TB, LOAITB_ID')
                        return
                    }

                    // console.log('xxxx: ',excellist);
                    let danhsach_loi = []
                    // this.danhsach.data = [];
                    let tem = []

                    // Kiểm tra NULL
                    for (let k = 0; k < excellist.length; k++) {
                        let item = excellist[k]
                        let obj = {
                            MA_TB: item.MA_TB,
                            LOAITB_ID: item.LOAITB_ID,
                            MA_HRM: item.MA_HRM
                        }
                        if (!item.MA_TB || item.MA_TB.toString().trim().length == 0) {
                            obj.LYDO_LOI = 'Mã TB null'
                            danhsach_loi.push(obj)
                        }
                        if (!item.LOAITB_ID || item.LOAITB_ID.toString().trim().length == 0) {
                            obj.LYDO_LOI = 'LOẠI TB ID null'
                            danhsach_loi.push(obj)
                        }
                    }
                    if (danhsach_loi.length > 0) { // Có danh sách lỗi thì hiển thị ra và không làm gì tiếp theo nữa
                        //this.activeTabs = false;
                        // this.showModal();
                        this.$toast.warning('Dữ liệu file excel chưa đúng số lượng cột')
                        return
                    }
                    // Kiểm tra lặp
                    const lookup = excellist.reduce((a, e) => {
                        a[e.MA_TB] = ++a[e.MA_TB] || 0
                        a[e.LOAITB_ID] = ++a[e.LOAITB_ID] || 0
                        return a
                    }, {})
                    // console.log(lookup)
                    let ds_loi = excellist.filter(e => lookup[e.MA_TB] && lookup[e.LOAITB_ID])
                    if (ds_loi.length > 0) { // Có danh sách lỗi thì hiển thị ra và không làm gì tiếp theo nữa
                        for (let i = 0; i < ds_loi.length; i++) {
                            let obj = {
                                MA_TB: ds_loi[i].MA_TB,
                                LOAITB_ID: ds_loi[i].LOAITB_ID,
                                LYDO_LOI: 'MA_TB, LOAITB_ID bị lặp lại'
                            }
                            danhsach_loi.push(obj)
                        }
                        //this.activeTabs = false;
                        // this.showModal();
                        this.$toast.warning('Dữ liệu file excel bị trùng lặp')
                        // this.disabledExportError = false;
                        return
                    }

                    // kiểm tra dịch vụ
                    const ds_dv_id = store.state.JS_LHTB.map(e => e.ID)
                    console.log(ds_dv_id)
                    const index_ds_loi_dv = excellist.filter(e => !ds_dv_id.includes(+e.LOAITB_ID))
                    console.log(index_ds_loi_dv)
                    if (index_ds_loi_dv.length > 0) { // Có danh sách lỗi thì hiển thị ra và không làm gì tiếp theo nữa
                        for (let i = 0; i < index_ds_loi_dv.length; i++) {
                            let obj = {
                                MA_TB: index_ds_loi_dv[i].MA_TB,
                                LOAITB_ID: index_ds_loi_dv[i].LOAITB_ID,
                                LYDO_LOI: 'Loại hình không được gán trong chiến dịch'
                            }
                            danhsach_loi.push(obj)
                        }
                        //this.activeTabs = false;
                        // this.showModal();
                        this.$toast.warning('Loại hình không được gán trong chiến dịch')
                        // this.disabledExportError = false;
                        return
                    }
                    // Nếu dữ liệu file OK rồi thì cập nhật thông tin liên hệ
                    await this.updateThueBaoTheoCD(excellist)
                    // this.setButton(4);
                    // await this.kiemTraFile();
                } catch (e) {
                    console.log('Read failure!' + e)
                    this.$toast.warning('Đọc dữ liệu file excel lỗi')
                }
            }
            fileReader.readAsBinaryString(files[0])
        },
        async updateThueBaoTheoCD(excellist) {
            try {
                this.$root.showLoading(true)
                this.$bvModal.msgBoxConfirm(
                    'Bạn có muốn import danh sách file vào chiến dich ' + this.tt_chien_dich.ma_cd + ' ?',
                    {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý'
                    }).then(async v => {
                        // console.log('ccc', v)
                        if (v) {
                            // this.xuatExcel();
                            this.$root.showLoading(true)
                            let res = await this.axios.post('/web-hopdong/b2a/sp_ins_ds_tb_phangiao_matap', {
                                'p_ma_srv': this.tt_chien_dich.ma_cd,
                                'p_ma_tap': this.file_excel.ma_tap,
                                'p_ten_tap': this.file_excel.ten_tap,
                                'p_ds_tb': JSON.stringify(excellist),
                                'p_phangiao': this.phangiaoTuDong ? 1 : 0,
                                'p_dt_phangiao': this.doiTuongPhanGiao.selected
                            })
                            this.$root.showLoading(false)
                            if (res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
                                this.file_excel.list = res.data.data //.filter(e => e.trangthai_id == "-1");
                                let countError = res.data.data.filter(e => e.trangthai_id == '-1')
                                if (countError.length) {
                                    // countError = res.data.data.filter(e => e.trangthai_id == "-1");
                                    this.disabledExportError = false
                                    this.$bvModal.msgBoxConfirm(
                                        'Có ' +
                                        countError.length + ' bản ghi sai dữ liệu! Bạn có muốn xuất file excel không?',
                                        {
                                            title: '',
                                            size: 'sm',
                                            okTitle: 'Đồng ý',
                                            cancelTitle: 'Hủy'
                                        }).then(async v => {
                                            // console.log('ccc', v)
                                            if (v) {
                                                this.xuatExcel()
                                            }
                                        })
                                }
                            } else {
                                this.$toast.error(res.data.message_detail)
                            }

                            await this.HIENTHI_DS_TB_PHANTAP()
                        }
                    })
                this.$root.showLoading(false)
            } catch (error) {
                console.log(error)
                this.$root.showLoading(false)
            }
        },
        //
        changeTab(tab) {
            if (this.currentTab == tab) {
                return
            }
            this.currentTab = tab
        },
        // start handle tabs
        selected: function (args) {
            console.log('selected ', args)
            this.tabSelectedIndex = args.selectedIndex
            // this.getInteractionDetail(args.isInteracted);
        },
        async setDsDonViApDung(js_tinh) {
            // this.js_tinh = js_tinh;
            await store.commit('SET_JS_TINH', js_tinh)
            // await store.commit('SET_JS_TINH_BK', js_tinh);
        },
        async setDsLoaHinhTB(js_lhtb) {
            // this.js_lhtb = js_lhtb;
            await store.commit('SET_JS_LHTB', js_lhtb)
            // await store.commit('SET_JS_LHTB_BK', js_lhtb);
        },
        async setDsThanhVienKenh(js_kenh) {
            // this.js_kenh = js_kenh;
            await store.commit('SET_JS_KENH', js_kenh)
            // await store.commit('SET_JS_KENH_BK', js_kenh);
        },
        // end handle tabs
        async frm_Load() {
            try {
                await this.initData()
            } catch (error) {
                console.log(error)
                // this.$toast.error(error)
            }
        },
        async loadKenhTC() {
            try {
                let res = await this.axios.post('/web-ccdv/quantri_chiendich/b2a_danhmuc', {
                    vkieu: 19,
                    vparam1: '',
                    vparam2: ''
                })
                this.tt_chien_dich.kenhtiepcan = res.data.data ? res.data.data : []
            } catch (error) {
                console.log(error)
            }
        },
        async loadTinhTrang() {
            try {
                let res = await this.axios.post('/web-ccdv/quantri_chiendich/b2a_danhmuc', {
                    vkieu: 20,
                    vparam1: '',
                    vparam2: ''
                })
                this.tt_chien_dich.tinh_trang = res.data.data ? res.data.data : [];
                this.tt_tim_kiem.cboTinhTrang = res.data.data ? res.data.data : [];
            } catch (error) {
                console.log(error)
            }
        },
        loadLoaiCD() {
            this.tt_chien_dich.loaichiendich = [
                {
                    'ten': 'CSKH',
                    'id': 1
                },
                {
                    'ten': 'Bán hàng',
                    'id': 2
                },
                {
                    'ten': 'CSKH & Bán hàng',
                    'id': 3
                }
            ]
        },
        loadDoUT() {
            this.tt_chien_dich.douutien = [
                {
                    'ten': '1',
                    'id': 1
                },
                {
                    'ten': '2',
                    'id': 2
                },
                {
                    'ten': '3',
                    'id': 3
                },
                {
                    'ten': '4',
                    'id': 4
                },
                {
                    'ten': '5',
                    'id': 5
                }
            ]
        },
        async loadChienDich() {
            try {
                let p1 = (this.tt_tim_kiem.ThoiHanTu ? this.tt_tim_kiem.ThoiHanTu : '') + (this.tt_tim_kiem.Den ? ('|' + this.tt_tim_kiem.Den) : '');
                let p2 = this.tt_tim_kiem.selectedTinhTrang;
                let res = await this.axios.post('/web-ccdv/quantri_chiendich/b2a_danhmuc', {
                    vkieu: 21,
                    vparam1: p1,
                    vparam2: p2
                })
                let tem = res.data.data.map(e => {
                    // console.log(typeof (e.id))
                    if (e.id) {
                        let ten_cd = e.ma_cd + ' - ' + e.ten_cd + ' - ' + e.tinhtrang
                        return {
                            chien_dich: ten_cd,
                            // chien_dich_id: e.id
                            chien_dich_id: e.id
                        }
                    }
                })
                // console.log(tem)
                this.tt_tim_kiem.chien_dich = tem.filter(e => e)
            } catch (error) {
                console.log(error)
            }
        },
        async initData() {
            try {
                this.$root.showLoading(true)
                this.vnhanvien_id = this.$root.token.getNhanVienID()
                console.log(this.vnhanvien_id)
                await this.loadChienDich()
                await this.loadKenhTC()
                this.loadLoaiCD()
                this.loadDoUT()
                await this.loadTinhTrang()
                this.$root.showLoading(false)
                //
                this.doiTuongPhanGiao.list = [
                    { name: 'Nhân viên kinh doanh địa bàn', value: 1 },
                    { name: 'Nhân viên kỹ thuật địa bàn', value: 2 }
                ]

            } catch (error) {
                // this.$toast.error(error)
                console.log(error)
                this.$root.showLoading(false)
            }
        },
        async HIENTHI_DS_TB_PHANTAP() {
            try {
                this.$root.showLoading(true)
                let res = await this.axios.post('/web-ccdv/quantri_chiendich/sp_lay_ds_phantap_kh', {
                    'p_ma_srv': this.tt_chien_dich.ma_cd
                })
                // if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                this.ds_chien_dich.list = res.data.data
                if (res.data.data.length){
                    this.disabledXuatDsTB = false;
                }
                // }
                this.$root.showLoading(false)
            } catch (error) {
                console.log(error)
                this.$toast.error('Có lỗi khi lấy ds phân tập ' + e)
                this.$root.showLoading(false)
            }
        },
        selectedCD(e) {
        },
        async HienThiCD(e) {
            try {
                this.tt_chien_dich.cd_id = e.cd_id,
                    this.tt_chien_dich.ma_cd = e.ma_cd,
                    this.tt_chien_dich.ten_cd = e.ten_cd,
                    this.tt_chien_dich.ngayApdung = e.tu_ngay,
                    this.tt_chien_dich.ngayDen = e.den_ngay,
                    this.tt_chien_dich.selectedKenhTiepCan = e.kenhtc_id,
                    this.tt_chien_dich.selectedLoaiChienDich = e.loai,
                    this.tt_chien_dich.selectedDoUuTien = e.mucdo_ut,
                    this.tt_chien_dich.selectedTinhTrang = e.tinhtrang_id,
                    this.tt_chien_dich.noidung = e.noidung_cd,
                    this.tt_chien_dich.gannhan = e.nhan
                this.tt_chien_dich.linkcv = e.link_cv,
                    this.tt_chien_dich.soSrv = e.ma_srv,
                    this.tt_chien_dich.soEoffice = e.so_eoffice,
                    this.tt_chien_dich.ghichu = e.ghichu

                // lưu ds đơn vị áp dụng, loại hình tb, tv kênh
                await this.setDsDonViApDung(JSON.parse(e.js_tinh))
                await this.setDsLoaHinhTB(JSON.parse(e.js_lhtb))
                await this.setDsThanhVienKenh(JSON.parse(e.js_kenh))
            } catch (error) {
                console.log(error)
            }
        },
        clickButton(key) {
            if (key == 'load') {
                this.NapLai_Click()
            }
            if (key == 'download') {
                alert('download')
            }
            // if (key == "save") {
            //     this.GhiLai_Click();
            // }
            // if (key == "create") {
            //     this.TaoMoi_Click();
            // }
            // if (key == "cancel") {
            //     this.Huy_Click();
            // }
            // if (key == "delete") {
            //     this.Xoa_Click();
            // }
        },
        NapLai_Click() {
            this.HIENTHI_DS_TB_PHANTAP()
        },
        async GhiLai_Click() {
            try {
                this.validateForm()
                let JS_CHIENDICH_B2A = {}
                if (this.disabledCreate) {
                    // tao moi
                    JS_CHIENDICH_B2A = [
                        {
                            CD_ID: null,
                            MA_CD: tt_chien_dich.ma_cd,
                            TEN_CD: tt_chien_dich.ten_cd,
                            TU_NGAY: tt_chien_dich.ngayApdung,
                            DEN_NGAY: tt_chien_dich.ngayDen,
                            MUCDO_UT: tt_chien_dich.selectedDoUuTien,
                            LOAI: tt_chien_dich.selectedLoaiChienDich,
                            TINHTRANG_ID: tt_chien_dich.selectedLoaiChienDich,
                            KENHTC_ID: tt_chien_dich.selectedKenhTiepCan,
                            NOIDUNG_CD: tt_chien_dich.noidung,
                            NHAN: tt_chien_dich.gannhan,
                            MA_SRV: tt_chien_dich.soSrv,
                            SO_EOFFICE: tt_chien_dich.soEoffice,
                            LINK_CV: tt_chien_dich.linkcv,
                            GHICHU: tt_chien_dich.ghichu,
                            JS_DS_TINH: store.state.JS_TINH,
                            JS_DS_LHTB: store.state.JS_LHTB,
                            JS_DS_KENH: store.state.JS_KENH
                        }
                    ]
                } else {
                    JS_CHIENDICH_B2A = [
                        {
                            CD_ID: tt_chien_dich.cd_id,
                            MA_CD: tt_chien_dich.ma_cd,
                            TEN_CD: tt_chien_dich.ten_cd,
                            TU_NGAY: tt_chien_dich.ngayApdung,
                            DEN_NGAY: tt_chien_dich.ngayDen,
                            MUCDO_UT: tt_chien_dich.selectedDoUuTien,
                            LOAI: tt_chien_dich.selectedLoaiChienDich,
                            TINHTRANG_ID: tt_chien_dich.selectedLoaiChienDich,
                            KENHTC_ID: tt_chien_dich.selectedKenhTiepCan,
                            NOIDUNG_CD: tt_chien_dich.noidung,
                            NHAN: tt_chien_dich.gannhan,
                            MA_SRV: tt_chien_dich.soSrv,
                            SO_EOFFICE: tt_chien_dich.soEoffice,
                            LINK_CV: tt_chien_dich.linkcv,
                            GHICHU: tt_chien_dich.ghichu,
                            JS_DS_TINH: store.state.JS_TINH,
                            JS_DS_LHTB: store.state.JS_LHTB,
                            JS_DS_KENH: store.state.JS_KENH
                        }
                    ]
                }
                let res = await this.axios.post('/web-quantri/chien-dich-b2a/capnhat_chien_dich_b2a', {
                    js_chiendich_b2a: JS_CHIENDICH_B2A
                })
                if (res.data.error_code == 'BSS-00000000' && res.data.error == '200') {
                    this.$toast.success('Cập nhật thành công !!')
                } else {
                    this.$toast.error('Có lỗi trong quá trình cập nhật')
                }
            } catch (error) {
                console.log(error)
            }
        },
        TaoMoi_Click() {
            try {
                this.disabledCreate = true
                this.Clear()
            } catch (error) {
                console.log(error)
            }
        },
        Huy_Click() {
            this.Clear()
        },
        async Xoa_Click() {
            try {
                let res = await this.axios.post('/web-quantri/chien-dich-b2a/xoa_chien_dich_b2a', {
                    'vcd_id': this.tt_chien_dich.cd_id,
                    'vnhanvien_id': this.vnhanvien_id
                })
                if (res.data.error == '200' && this.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Xoá chiến dịch thành công!!')
                } else {
                    this.$toast.error('Xoá chiến dịch thất bại')
                }

                await this.HIENTHI_DS_TB_PHANTAP()
            } catch (error) {
                console.log(error)
            }
        },
        handleTag() {
            // Lay gia trị tag truyen tu url
            if (this.$route.query.tag != null &&
                this.$route.params.tag != '' &&
                this.$route.query.tag.length > 0) {
                this.tag = this.$route.query.tag
            }

            // Gan vao theo tham so cua form
            if (this.tag != null && this.Tag.length > 0) {
                let arr = this.tag.split('+')
                console.log(arr)
                if (arr.length >= 1) this.tthd_id = parseInt(arr[0])
                if (arr.length >= 2) this.dsloaihd_id = arr[1].replace(';', ',')
                if (arr.length >= 3) this.dsdichvuvt_id = arr[2].replace(';', ',')
            } else {
                this.dsdichvuvt_id = '2'
                this.dsloaihd_id = '17'
                this.tthd_id = this.TrangThaiHD.DANG_THI_CONG
            }
        },

        Clear() {
            tt_chien_dich.ma_cd = '',
                tt_chien_dich.ten_cd = '',
                tt_chien_dich.ngayApdung = '',
                tt_chien_dich.ngayDen = '',
                tt_chien_dich.selectedKenhTiepCan = '',
                tt_chien_dich.selectedLoaiChienDich = '',
                tt_chien_dich.selectedDoUuTien = '',
                tt_chien_dich.selectedTinhTrang = '',
                tt_chien_dich.noidung = '',
                tt_chien_dich.gannhan = e.nhan
            tt_chien_dich.linkcv = '',
                tt_chien_dich.soSrv = '',
                tt_chien_dich.soEoffice = '',
                tt_chien_dich.ghichu = ''
        },
        validateForm() {
            if (!this.tt_chien_dich.ten_cd) {
                this.$toast.warning('Chưa nhập tên chiến dịch!')
                return
            }
            if (!this.compareDates(this.tt_chien_dich.ngayApdung, moment().format('DD/MM/YYYY'))) {
                this.$toast.warning('Ngày áp dụng phải lớn hớn ngày hiện tại!')
                return
            }
            if (!this.tt_chien_dich.selectedKenhTiepCan) {
                this.$toast.warning('Chưa chọn kênh tiếp cận!')
                return
            }
            if (!this.tt_chien_dich.selectedLoaiChienDich) {
                this.$toast.warning('Chưa chọn loại chiến dịch!')
                return
            }
            if (!this.tt_chien_dich.selectedDoUuTien) {
                this.$toast.warning('Chưa chọn độ ưu tiên!')
                return
            }
            if (!this.tt_chien_dich.selectedTinhTrang) {
                this.$toast.warning('Chưa chọn tình trạng!')
                return
            }
            if (!this.tt_chien_dich.noidung) {
                this.$toast.warning('Chưa nhập nội dung chiến dịch!')
                return
            }
        },
        // xxx(){
        //     // let t = moment(this.tt_chien_dich.ngayApdung);

        //     // console.log(new Date(t).getDay() - new Date().getDay() >= 1 ? 'ngày chọn phải lớn hơn' : 'ngày chọn đã ok');
        //     // console.log(new Date(t).getDay());
        //     // console.log(new Date().getDay());
        //     // console.log(new Date(t).getDay() - new Date().getDay());
        //     this.compareDates(this.tt_chien_dich.ngayApdung, moment().format('DD/MM/YYYY'))

        // },
        compareDates(d1, d2) {
            let date1 = new Date(d1).getTime()
            let date2 = new Date(d2).getTime()

            if (date1 < date2) {
                // console.log(`${d1} is less than ${d2}`);
                return true
            } else {
                // console.log(`Both dates are equal`);
                return false
            }
        },
        btnNgayTongDongLuc_Click: function () {
            this.$refs.popupNgayTongDongLuc.showModal(this.tt_chien_dich.cd_id, this.tt_chien_dich.soSrv, this.ds_chien_dich.list)
        },
        async exportDataGrid() {
            try {
                this.loading(true)
                let excelExportProperties = {
                    fileName: "TTTBCD_" + moment(new Date()).format("YYYYMMDD") + ".xlsx",
                    dataSource: this.ds_chien_dich.list,
                };
                await this.$refs.gridReport.excelExport(excelExportProperties);
                this.$toast.success("Tải file Excel thành công !");

            } catch (e) {
                console.log('exportExcel: ', e);
                this.$toast.error("Lỗi xuất Excel: " + e.message);
            } finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'phangiaoTuDong': function () {
            // console.log('xxxxx', this.phangiaoTuDong);
            if (this.phangiaoTuDong) {
                this.$toast.info('Bạn đã chọn quy trình phân giao tự động cho đối tượng NVKD/KT')
            }
        },
        'js_tinh': function () {
            console.log('js_tinh change')
        },
        'tt_tim_kiem.selectedChienDich': async function () {
            try {
                this.$root.showLoading(true)
                let res = await this.axios.post('/web-ccdv/quantri_chiendich/sp_lay_ds_chiendich_b2a_theo_cd_id', {
                    'p_cd_id': this.tt_tim_kiem.selectedChienDich
                })
                if (res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
                    let row = res.data.data[0]
                    this.HienThiCD(row)
                } else {

                }

                // hiển thị ds thuê bao phân tập
                await this.HIENTHI_DS_TB_PHANTAP()
                this.$root.showLoading(false)
            } catch (error) {
                this.$root.showLoading(false)
            }
        },
        'tt_tim_kiem.selectedTinhTrang'(n, o) {
            if(chkTinhTrang){
                this.loadChienDich();
            }
        },
        'tt_tim_kiem.ThoiHanTu'(n, o) {
            this.loadChienDich();
            console.log("a2")
            this.tt_tim_kiem.selectedChienDich = '';
        },
        'tt_tim_kiem.Den'(n, o) {
            this.loadChienDich();
            console.log("a3")
            this.tt_tim_kiem.selectedChienDich = '';
        },
        'chkTuDen'(n,o){
            if(!this.chkTuDen){
                this.tt_tim_kiem.ThoiHanTu = '';
                this.tt_tim_kiem.Den = '';
            }
        },
        'chkTinhTrang'(n,o){
            if(!chkTinhTrang){
                this.tt_tim_kiem.selectedTinhTrang = '';
            }
        }
    }
}
</script>

<style>
.list a {
    cursor: pointer;
}

/* .e-grid td.e-selectionbackground {
    background-color: #F9E1A9!important;
}
.e-grid tr:hover {
    background-color: #FFF9EB !important;
}
.e-checkbox-wrapper .e-check::before, .e-css.e-checkbox-wrapper .e-check::before {
    content: "\e718";
    background-color: transparent;
    line-height: 16px;
}
.e-checkbox-wrapper .e-stop::before, .e-css.e-checkbox-wrapper .e-stop::before {
    content:  "\e7a5" !important
} */
</style>
