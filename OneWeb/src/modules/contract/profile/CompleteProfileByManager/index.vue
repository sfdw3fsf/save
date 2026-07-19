<template src="./index.html"></template>

<script>
import define from "./define/index.js";
import api from "./api/index.js";
import * as moment from 'moment';
import Vue from "vue";
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Page, Group } from "@syncfusion/ej2-vue-grids";
import { DataManager, Query, Predicate } from '@syncfusion/ej2-data';
import DataGridCustom from './components/DataGrid/index.vue';
import SaveFileModal from "./components/SaveFileModal.vue";
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts';

Vue.use(GridPlugin);
L10n.load({
    'vi': {
        // 'grids': {
        //     'Item': 'Artikel',
        //     'Items': 'Artikel'
        // },
        'pager': {
            'currentPageInfo': 'Bản ghi từ {1} đến số {2} trong tổng số {0}',
            'pagerDropDown': 'Bản ghi trên trang'
        }
    }
});
// import TTKHLeft from './components/TTKHLeft.vue';
// import TTKHRight from './components/TTKHRight.vue';
// import MuaTBI from './components/MuaTBI.vue';
// import ChonTBI from './components/ChonTBI.vue';
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import ModalNormalizeCAinformation from "@/modules/contract/setup/NormalizeCAinformation/ModalNormalizeCAinformation.vue";
import ModalBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices.vue";
import ModalTienKM from "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue";
import PayMent from "@/modules/contract/setup/Payment/Payment.vue";
import ModalChooseDevices from './popup/popupChooseDevices.vue';
import ModalChonToaDo from "@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo";

Vue.use(SplitterPlugin);
export default {
    provide: {
        grid: [Filter, Page, Group]
    },
    components: {
        api,
        SearchContractModal,
        ModalNormalizeCAinformation,
        ModalBuyDevices,
        ModalTienKM,
        PayMent,
        ModalChooseDevices,
        ModalChonToaDo,
        DataGridCustom,
        SaveFileModal,
    },
    props: ['Tag'],
    created() {
        if (this.$route.query.tag && this.$route.query.tag.length) {
                this.Tag = this.$route.query.tag
        }
        // debugger
        // console.log('xxx ', this.$route, this.$route.query.tag);
        // Gan vao theo tham so cua nghiep vu cua form
        if (this.Tag && this.Tag.length > 0) {
            let arr = this.Tag.split('-')
            // debugger
            if (arr.length >= 1) this.tths_id = Number(arr[0]);
            if (arr.length >= 2) this.quytrinh_id = Number(arr[1]);
            // if (arr.length >= 3) this.initDVVTArr = arr[2].split(',')
        }
        // console.log('xxx ', this.tths_id, this.quytrinh_id);

        this.initData();
    },
    data() {
        return {
            ...define,
            filterBarMode: { filterBarMode: "Immediate" },
            currentRows: [],
            selectionOptions: { checkboxMode: 'ResetOnRowClick' },
            formatOptions: { type: 'date', format: 'dd/MM/yyyy' },
            hasFreezeLeft: true,
            groupOptions: { columns: ['loai_tb'], showDropArea: false},
            $self: this,
            hoancong_click:false,
            vhoso_id:-1,
            upfileTemplate: function () {
                return {
                    template: Vue.component('columnTemplate', {
                        template: ` <div>
                                        <span class="text-main f20 nc-icon-outline files_single-content-02"></span>
                                    </div>`,
                    })
                }
            },
            xnTemplate: function () {
                return {
                    template: Vue.component('columnTemplate', {
                        template: ` <div>
                                        <span class="text-main f20 nc-icon-outline ui-1_check-circle-07"></span>
                                    </div>`,
                    })
                }
            },
            config: {
                thuebao: [
                    {
                        headerText: 'Nhóm',
                        allowSorting: true,
                        fieldName: 'loai_hd',
                        isGroupedColumn:true
                    },
                    {
                        headerText: 'Số máy/Acc',
                        allowSorting: true,
                        fieldName: 'ma_tb'
                    },
                    {
                        headerText: 'Loại hình',
                        allowSorting: true,
                        fieldName: 'loaihinh_tb'
                    },
                    {
                        headerText: 'Trạng thái',
                        allowSorting: true,
                        fieldName: 'trangthai_tb'
                    },
                    {
                        headerText: 'Ngày báo hỏng',
                        allowSorting: true,
                        fieldName: 'ngay_bh'
                    },
                    {
                        headerText: 'Người báo hỏng',
                        allowSorting: true,
                        fieldName: 'nguoi_bh'
                    },
                    {
                        headerText: 'Ngày nghiệm thu',
                        allowSorting: true,
                        fieldName: 'ngay_ht'
                    }
                ],
                hoso: [
                    {
                        headerText: 'STT',
                        allowSorting: true,
                        fieldName: 'stt',
                        width: '70',
                        freeze: 'Left'
                    },
                    {
                        headerText: 'Mã GD',
                        allowSorting: true,
                        fieldName: 'ma_gd'
                        , width: '180'
                        , freeze: 'Left'
                    },
                    {
                        headerText: 'Mã KH',
                        allowSorting: true,
                        fieldName: 'ma_kh'
                        , width: '50'
                    },
                    {
                        headerText: 'Tên khách hàng',
                        allowSorting: true,
                        fieldName: 'ten_kh'
                        , width: '150'
                    },
                    {
                        headerText: 'Địa chỉ KH',
                        allowSorting: true,
                        fieldName: 'diachi_kh'
                        , width: '150'
                    },
                    {
                        headerText: 'Ngày giao',
                        allowSorting: true,
                        fieldName: 'ngaygiao'
                        , width: '150'
                    },
                    {
                        headerText: 'Số lo',
                        allowSorting: true,
                        fieldName: 'malo_hs'
                        , width: '150'
                    },
                    {
                        headerText: 'Đơn vị giao',
                        allowSorting: true,
                        fieldName: 'dv_giao'
                        , width: '150'
                    },
                    {
                        headerText: 'Mã bộ HS',
                        allowSorting: true,
                        fieldName: 'ma_bhs'
                        , width: '150'
                    },
                    {
                        headerText: 'Đơn vị nhận',
                        allowSorting: true,
                        fieldName: 'dv_nhan'
                        , width: '150'
                    },
                    {
                        headerText: 'Trạng thái HS',
                        allowSorting: true,
                        fieldName: 'trangthai_hs'
                        , width: '150'
                    },
                    {
                        headerText: 'Người tạo',
                        allowSorting: true,
                        fieldName: 'nguoitao'
                        , width: '150'
                    },
                    {
                        headerText: 'Tuyến thu',
                        allowSorting: true,
                        fieldName: 'tuyenthutt'
                        , width: '150'
                        , freeze: 'Right'
                    },
                    {
                        headerText: 'UpFile',
                        allowSorting: true,
                        fieldName: 'upfile'
                        , width: '80',
                        template: ` <div style="text-align:center;">
                                        <span class="text-main f20 nc-icon-outline files_single-content-02"></span>
                                    </div>`
                        ,freeze: 'Right'
                    },
                    {
                        textAlign:"center",
                        headerText: 'Đã xác nhận',
                        fieldName: 'da_xn',
                        allowSorting: true                        
                        , width: '120'                        
                        ,freeze: 'Right'
                        ,cssClass:'daxacnhan'
                    }
                ],
                ct_hoso: [
                    
                    {
                        headerText: 'Số máy/Acc',
                        allowSorting: true,
                        fieldName: 'ma_tb'
                        // freeze: 'Left'
                    },
                    {
                        headerText: 'Mã GD',
                        allowSorting: true,
                        fieldName: 'ma_gd'
                    },
                    {
                        headerText: 'Nhóm file',
                        fieldName: 'loai_hd',
                        // isGroupedColumn: true,
                        isGroupedColumn:true
                    },
                    {
                        headerText: 'Loại file',
                        allowSorting: true,
                        fieldName: 'nhom_file'
                    },
                    {
                        headerText: 'Tên file',
                        allowSorting: true,
                        fieldName: 'ten_file'
                    },
                    {
        textAlign:"left",
        fieldName:"loai_hd",
        headerText:"Loại HĐ/PL",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"left",
        fieldName:"ngay_yc",
        headerText:"Ngày YC",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"left",
        fieldName:"nguoi_cn",
        headerText:"Người Upload",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"left",
        fieldName:"ngay_cn",
        headerText:"Ngày Upload",
        allowSorting:"true",
        allowFiltering:"true",
    },


    {
    fieldName: '',
    headerText: '',
    allowFiltering: true,
    allowSorting: false,
    textAlign: 'Center',
    width: 180,
        template: function () {
            return {
                template: Vue.component('columnTemplate', {
                    template: `<row><a class="btn btn-huylydo lh14" @click="openFile"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                    <span class="one-search lh14"></span></a>
                                <a class="btn btn-huylydo lh14" @click="onDownLoad"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                    <span class="fa fa-angle-down lh14"></span></a></row>             
                    `, 
                    data () {
                    return {
                        data: {},
                        
                    }
                    },
                    computed: {
                        parent () {
                            return this.$parent.$parent.$parent
                        }
                    },
                    methods: {
                        onDownLoad()
                        {
                            this.$parent.$parent.$parent.url= this.data.url;
                            console.log('xxx', this, this.$parent.$parent.$parent.url);
                            this.$parent.$parent.$parent.$refs.saveFileModal.showModal()
                        },
                        openFile(){
                            // console.log(this.data.url, $self);
                            if(this.data.url){
                                window.open(this.data.url, '_blank');
                            }else{
                                alert('Không có file để mở !!')
                            }
                        }
                    }
                })
            }
        }
    },
                ],
            },
            isEnableFileds: false,
            button: { 
                themfile: true,
            },
            url:"",
            dsQuyen: [],
            nguoidung_id: 999999,
            nhom_nd_id: 0,
            files: [],
            fileName: ''
        }
    },
    methods: {
        async txtMaGD_KeyPress(e){
            try {
                // alert(e.target.value)
                if(!e.target.value) return;
                let vmagd = e.target.value;
                const TRANGTHAI_HT = 6;
                if(this.tths_id != TRANGTHAI_HT){
                    this.TIMKIEM(vmagd, 1)
                }

            
            } catch (error) {
                console.log(error);
            }
        },
        download(value){
            console.log(value);
            
            // Create a new link
            let anchor = document.createElement('a');
            anchor.href = value._url;
            anchor.download = value.file_name;

            // Append to the DOM
            anchor.style.display = 'none';
            document.body.appendChild(anchor);

            // Trigger `click` event
            anchor.click();

            // Remove element from DOM
            document.body.removeChild(anchor);
            //tạo ds
            // let danhsach=[]
            // let array=this.columns.map(x=>x.field)
            // this.dataSourcesPreview.forEach(item=>{
            //     const object={}
            //     for (const name of array) {
            //         object[name] = item[name]
            //     }
            //     danhsach.push(object)
            // })
            // //xuất file
            // if(value.extension=='.xlsx'){
            //     let data = XLSX.utils.json_to_sheet(danhsach);
            //     let wb = XLSX.utils.book_new(); // make Workbook of Excel
            //     XLSX.utils.book_append_sheet(wb, data, this.sheetAName); // sheetAName is name of Worksheet
            //     // export Excel file
            //     let fileFullName=value.file_name+value.extension
            //     XLSX.writeFile(wb, fileFullName);
            // }else if(value.extension=='.txt'){
            //     var data=''
            //     danhsach.forEach((item)=>{
            //         let temp=[]
            //         this.columns.forEach((column)=>{
            //             temp.push(item[column.field])
            //         })
            //         let result=temp.join(';')+'\n'
            //         data+=result
            //     })
            //     console.log('dataa',data)
            //     let fileFullName=value.file_name+value.extension

            //     const blob = new Blob([data], { type: 'text/plain;charset=utf-8' })
            //     const link = document.createElement('a')
            //     link.href = URL.createObjectURL(blob)
            //     link.download = fileFullName
            //     link.click()
            //     URL.revokeObjectURL(link.href)

            // }else if(value.extension=='.dbf'){
            //     this.$toast.error('Tính năng tạm thời không sử dụng trên nền tảng web')
            // }
        },
        tvDonVi_FocusedNodeChanged(e){            
            console.log(e.nodeData.id);
            if (e.nodeData.id)
                this.vhoso_id = e.nodeData.id
        },
        refreshFile(){
            this.tt_file.loai_file = -1;
            this.tt_file.nhom_file = -1;
            this.tt_file.duong_dan = "";
            this.tt_file.ghichu = '';
        },
        vbtnClose_Click(){
            this.isEnableFileds = false;
            this.button.themfile = true;
        },
        getPhieuID(){
            if(this.model.hoso['phieu_id'])
                return this.model.hoso['phieu_id'];
            return -1;
        },
        getMaGD(){
            if(this.model.hoso['ma_gd'])
                return this.model.hoso['ma_gd'];
            return "";
        },
        async CAPNHAT_FILE(){
            try {
                // let formData = new FormData();
                // for(var file of this.$refs.files.files) {
                //     formData.append('files', file);
                // }
                // // this.kich_thuoc_file=parseFloat((this.$refs.files.files[0].size)/1024).toFixed(2) + " MB";
                // api.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                //     console.log(response);
                //     // if(response.data.error_code === 'BSS-00000000') {
                //     // this.listbox.ds_file.value.url = response.data.data.join('|');
                //     // this.isUploaded = true;
                //     // this.$refs.files.value=null;
                //     // }
                //     }).catch(function(){
                //         console.log('File upload failed!');
                //     });

                // let formData = new FormData();
                //     for(let file of this.$refs.files.files) {
                //         formData.append('files', file);
                //     }
                //     let res_upload_files = await this.axios.post('/web-quantri/upload', formData, {
                //         headers: {
                //             "Content-Type": "multipart/form-data",
                //         },
                //     });
                //     console.log(res_upload_files);
                    
                if(this.options.hoso.length ==0)
                {
                    this.$toast.error("Không tìm thấy bộ hồ sơ để thêm file.");
                    return;
                }
                if (this.tt_file.loai_file == -1 || !this.tt_file.loai_file)
                {
                    this.$toast.error("Bạn chưa chọn loại file.");
                    return;
                }
                if (!this.tt_file.duong_dan)
                {
                    this.$toast.error("Bạn chưa nhập đường dẫn file");
                    return;
                }
                
                let res_kt = await this.axios.post('/web-hopdong/giao_cho_qlk/fn_lay_soluong_phieu_bohs', {
                    phieu_id: this.getPhieuID()
                });
                let kt = res_kt.data.data;
                if(!kt || (kt.toString() != "0" && !this.dsQuyen.includes(2090)))//Qyền thao cập nhật file sau khi đã giao; HOSO_LC.QUYEN_THEMSUA_FILE_DG)))
                {
                    this.$toast.warning("Phiếu " + this.getMaGD() + " trạng thái bị thay đổi. Bạn hãy kiểm tra lại");
                    return;
                }
                this.$confirm("Bạn có muốn thêm file cho mã GD: " + this.getMaGD(),'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(async ()=> {
                    let dtHSThueBao = GET_HS_THUEBAO();
                    if (dtHSThueBao.length == 0)
                    {
                        this.$toast.warning("Bạn bắt buộc phải gán thuê bao với loại file " + this.tt_file.loai_file);
                        return;
                    }
                    if (!this.tt_file.duong_dan) return;
                    // web-hopdong/hopdong/upload_file_hoso
                    
                    api.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                        console.log(response);
                        if(response.data.error_code === 'BSS-00000000') {
                            let file_url = response.data.data['success_files'];
                            this.fu_UploadCompleted(file_url);
                        }else{
                            this.$toast.warning('File upload failed!')
                        }
                    }).catch(function(){
                        console.log('File upload failed!');
                    });
                }).catch(()=> { 
                    console.log('Dừng lại !!');
                    return;
                })
            } catch (error) {
                console.log(error);
            }
            
        },
        getHoso_id(){
            if(this.model.thuebao['hoso_id'])
                return this.model.thuebao['hoso_id'];
            return "";
        },
        async fu_UploadCompleted(file_url){
            try {
                // if (!file_url)
                // {
                //     this.$toast.warning("Không thể upload file lên server! Bạn hãy thử lại");
                //     return;
                // }
                // sDuongDanDinhKemFTP = file_url;
                let res_kq = await this.axios.post('/web-hopdong/hopdong/capnhat_file_hoso', {
                    bohoso_id: this.vbohs_id, //truyen vao
                    file_id: 0,
                    file_url: file_url,
                    files: [],
                    ghichu: this.tt_file.ghichu, //truyen vao
                    hoso_id: this.getHoso_id, //truyen vao
                    kieu: 6, //fix
                    loaifile_id: this.tt_file.loai_file //truyen vao
                });
                // let kq = res.data.data;
                    
                    if(res_kq.data.error_code == 'BSS-00000000'){
                        this.$toast.success("Thêm mới file hồ sơ thành công!");
                        // LOADFILE_HS();
                        this.tt_file.loai_file = -1;
                        this.tt_file.nhom_file = -1;
                        this.tt_file.duong_dan = "";
                        this.tt_file.ghichu = "";
                        // ChucNang.FocusGridViewRow(grvFileHS, "FILE_ID", file_id);
                    }else{
                        this.$toast.warning("Thêm mới file hồ sơ thất bại!");
                    }
                
            } catch (error) {
                console.log(error);
            }
        },
        GET_HS_THUEBAO()
        {
            let dtHSThueBao = [];
            let newTB = {
                thuebao_id: null,
                thanhtoan_id: null,
                ma_tb: null,
                hdtb_id: null,
                ma_kh: null,
                baohong_id: null,
                hdkh_id: null
            }
            let ds = this.options.thuebao; // gridThueBao.DataSource;
            if(!ds || !ds.length)
                return;

            let kt_baohong = false;
            for (let j = 0; j < ds.length; j++)
            {
                if(Object.keys(ds[i].includes('baohong_id'))){
                    kt_baohong = true;
                    break;
                }
            }

            for (let i = 0; i < ds.length; i++)
            {
                let sCheck = "0";
                if (ds[i]["chk"])
                {
                    sCheck = ds[i]["chk"].toString();
                }
                if (sCheck == "1")
                {
                    let dr = {...newTB}; //dtHSThueBao.NewRow();
                    dr["thuebao_id"] = ds[i]["thuebao_id"].toString();
                    dr["thanhtoan_id"] = ds[i]["thanhtoan_id"].toString();
                    dr["ma_tb"] = ds[i]["ma_tb"].toString();
                    dr["hdtb_id"] = ds[i]["hdtb_id"].toString();
                    dr["ma_kh"] = ds[i]["ma_kh"].toString() != "0" ? ds[i]["ma_kh"].toString() : "0";
                    if (kt_baohong)
                        dr["baohong_id"] = ds[i]["baohong_id"].toString() != "0" ? ds[i]["baohong_id"].toString() : "0";
                    else
                        dr["baohong_id"] = 0;
                    dr["baohong_id"] = 0;
                    dr["hdkh_id"] = ds[i]["hdkh_id"].toString() != "0" ? ds[i]["hdkh_id"].toString() : "0";
                    dtHSThueBao.push(dr);
                }
            }
            return dtHSThueBao;
        },
        async changeDataSource(data) {
            // console.log(data);
            let tem = await this.createTreeDonVi(data);
            // console.log(tem);
            this.tt_doituong_hs.fieldsDonVi = {
                    dataSource: tem, id: 'hoso_id', text: 'ma_hs_doituong',
                    parentID: 'hoso_cha_id', hasChildren: 'hasChild'
            }
        },
        //Filtering the TreeNodes
        searchNodes(args) {
            // console.log(args);
            let _text = document.getElementById('txtFilter').value // mask.element.value;
            // console.log(_text);
            let predicats = [], _array = [], _filter = [];
            if (_text == "") {
                // alert()
                this.changeDataSource([...this.dsDonVi]);
            }
            else {
                let root = this.dsDonVi[0];
                let dsTem = [...this.dsDonVi];
                let predicate = new Predicate('ma_hs_doituong', 'contains', _text, true);
                let filteredList = new DataManager(dsTem).executeLocal(new Query().where(predicate));
                this.dsDVTem = [root,...filteredList];
                // console.log(filteredList);
                this.changeDataSource(this.dsDVTem);
            }
        },
        sortTreeView(){
            try {
                let root = this.dsDVTem[0];
                let dsTem = this.dsDVTem.slice(1);
                dsTem.sort(function(a, b) {
                    const nameA = a.ma_hs_doituong.toUpperCase(); // ignore upper and lowercase
                    const nameB = b.ma_hs_doituong.toUpperCase(); // ignore upper and lowercase
                    if (nameA < nameB) {
                        return -1;
                    }
                    if (nameA > nameB) {
                        return 1;
                    }

                    // names must be equal
                    return 0;
                    });
                this.changeDataSource([root,...dsTem]);
            } catch (error) {
                console.log(error);
            }
        },

        UploadFileHoSo(e) {
            try {
                if(this.$refs.files && this.$refs.files.files.length){
                    // this.files = [];
                    let fileupload = this.$refs.files.files;
                    this.tt_file.duong_dan = e.target.files[0].name;
                    this.files = fileupload;
                    // console.log(fileupload);
                    // const formData = new FormData();
                    // formData.append('files', fileupload);
                    // this.files = formData;
                    // alert(this.tt_file.duong_dan);
                    // let nhanvien_id = this.nhanvien_id;
                    // let nguoi_cn = this.nguoi_cn;

                }
                // if(this.form.duong_dan){
                //     this.docFile = false;
                //     var reader = new FileReader();
                //     reader.onload = async (e) => {
                //         //Read the Excel File data in binary
                //         var workbook = XLSX.read(e.target.result, {
                //             type: 'binary'
                //         });

                //         //get the name of First Sheet.
                //         var Sheet = workbook.SheetNames[0];

                //         //Read all rows from First Sheet into an JSON array.
                //         var excelRows = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[Sheet]);
                //         // console.log(excelRows);
                //         if (excelRows && excelRows.length > 0) {
                //             if (!Object.keys(excelRows[0]).includes('MA_GD')) {
                //                 this.$toast.warning("Tên cột trong file không đúng định dạng: MA_GD");
                //                 return;
                //             }
                //             let d = new Date().toLocaleString('vi-VN', { dateStyle: 'short' }).toString();
                //             let rows = [...excelRows];
                //             let ls = [];
                //             rows.forEach(row => {
                //                 row["LOI"] = "0";
                //                 row["LYDO_LOI"] = "";
                //                 row.NHANVIEN_ID = this.nhanvien_id;
                //                 row.NGUOI_CN = this.nguoi_cn;
                //                 row.NGAY_CN = d;
                //                 row.IP_CN = this.ip_cn;
                //                 ls.push(row);


                //                 // console.log(rows);
                //             });
                //             // cap nhat
                //             await this.updateDataCocFromFileTemp(ls);
                //             let kiemTraDatCocTmp = await api.kiemTraDatCocTmp(this.axios, {
                //                 vnhanvien_id: this.nhanvien_id,
                //             });
                //             // this.$refs.files = [];

                //             // kiemtra
                //             let dsS = kiemTraDatCocTmp.data.data ? kiemTraDatCocTmp.data.data : [];
                //             if (dsS.length > 0) {
                //                 dsS.forEach(i => {
                //                     if (i.KQ_LOI) {
                //                         this.$toast.warning(i.MA_GD + ": " + i.KQ_LOI);
                //                     }
                //                 });
                //                 return;
                //             }

                //             let dsHD = await api.getDsHopDongTuFile(this.axios, {
                //                 vnhanvien_id: this.nhanvien_id,
                //                 vphanvung_id: this.phanvung_id,
                //             });
                //             this.options.hopdong = dsHD.data.data;
                //             this.docFile = true;
                //             await this.focusItemHD(dsHD.data.data[0])
                //         }
                //     };
                //     reader.readAsBinaryString(fileupload);
                // }
            }
            catch (error) {
                console.log(error);
            }
        },
        gridviewDanhSach_FocusedRowChanged() {
            try {
                console.log('x1 ');
                if (this.options.hoso.length) {
                    this.CLEAR();
                    this.NAP_DS_LUOI();
                }
            } catch (e) {
                console.log(e)
            }
        },
        NAP_DS_LUOI() {
            console.log('x2 ', this.model.hoso);
            this.tt_bo_hs.magd = this.model.hoso["ma_gd"];
            this.tt_bo_hs.tenkh = this.model.hoso["ten_kh"];
            this.tt_bo_hs.makh = this.model.hoso["ma_kh"];
            if (this.model.hoso["malo_hs"]) {
                this.tt_bo_hs.solohs = this.model.hoso["malo_hs"];
            }
            this.tt_bo_hs.trangthai = this.model.hoso["trangthai_hs"];
            this.tt_bo_hs.dvgiao = this.model.hoso["dv_giao"];
            if (this.model.hoso["nd_tra_con"]) {
                this.tt_bo_hs.nd_tra = this.model.hoso["nd_tra_con"];
            }
            this.LOADFILE_HS();
            if (!this.button.themfile) {
                this.NAP_DS_HOPDONGTB(this.vhdkh_id_luoi());
            }
        },
        async LOADFILE_HS() {
            try {
                let res = await this.axios.post('/web-quantri/hoso/layds_file_trong_hoso', {
                    hoso_id: this.model.hoso['bohs_id']
                });
                this.options.ct_hoso = res.data.data;
                // return res.data.data;
            } catch (e) {
                console.log(e)
            }
        },
        async TT_HUONGGIAO(){
            try {
                // LẤY THÔNG TIN HƯỚNG GIAO
                let dt_hg = await this.axios.post('/web-ccdv/giao_cho_hths/sp_lay_ds_huonggiao_theo_quytrinh',
                {
                    quytrinh_id: this.quytrinh_id ? this.quytrinh_id : 550,
                    tths_id: this.tthd_id ? this.tthd_id : 4,
                });
                return dt_hg.data.data ? dt_hg.data.data : [];
            } catch (error) {
                console.log(error);
            }
        },
        async NAP_COMB_GIAOPHIEU() {
            try {
                let dt_hg = await this.TT_HUONGGIAO();
                console.log(dt_hg);
                // debugger
                this.huonggiao_id = dt_hg.length ? dt_hg[0]['huonggiao_id'] : 0;                
                // let res_thaotac = await this.axios.post('/web-baohong/giao_cho_hths/sp_lay_ds_thaotac', {
                //     quytrinh_id: this.quytrinh_id,
                //     huonggiao_id: this.huonggiao_id,
                // });
                this.nuthc = this.PHAILAM("HOANCONG_LC");
                this.nutgp = this.PHAILAM("GIAOPHIEU_LC");
                // this.thaotac = res_thaotac.data.data;
                // LẤY KIÊU GIAO
                let res_dt_kieugiao = await this.axios.get('/web-tracuu/danhmuc/kieu_giao_hoso');
                this.cbKieuGiao = res_dt_kieugiao.data.data ? res_dt_kieugiao.data.data : [];

                // LẤY ĐƠN VỊ GIAO
                let res_dt_dvgiao = await this.axios.get('/web-tracuu/danhmuc/donvi_giao_hoso');
                this.cbdvGiao = res_dt_dvgiao.data.data ? res_dt_dvgiao.data.data : [];
                // LẤY TRẠNG THANG_ID
                let res_dt_trangthai = await this.axios.get('/web-tracuu/danhmuc/trangthai_ph');
                this.cbTrangThai = res_dt_trangthai.data.data ? res_dt_trangthai.data.data : [];

                // LẤY DANH SÁCH THAO TÁC
                this.Thietlap_thaotac(this.huonggiao_id);

            } catch (error) {
                console.log(error);
            }
        },
        sortTreeView() {

            // console.log(this.donViTree.sort());
        },
        filterViewTree(e) {
            console.log(e.target.value);
            // if(e.target.value){
            //     let temList = this
            // }
        },
        focusItemCTHS() {

        },
        focusItemDSHS(e) {
            // alert('')
            if (e.length) {
                console.log(e[e.length - 1]);
                let row = e[e.length - 1];
                this.model.hoso = row;
                this.gridviewDanhSach_FocusedRowChanged();
            }
        },
        
        focusItemTB(e) {
            if(e.length) {
                this.model.thuebao = e[e.length -1];
            }
        },
        async getDsDonViTheoNV() {
            try {
                let res = await this.axios.get(`/web-hopdong/phattrienthuebao/lay_ds_treeview_doituong_v2?tuNgay=01/01/2021&denNgay=16/07/2021&nhomNdId=${this.nhom_nd_id}`);
                if(res.data.error_code == 'BSS-00000000'){
                    this.dsDonVi = res.data.data;
                }else{
                    this.$toast.error(res.data.message_detail);
                }
            } catch (error) {
                console.log(error);
            }
        },
        async createTreeDonVi(dsDonVi) {
            if(dsDonVi){
                let tree = []
                let donviChaArray = dsDonVi.map(item => (item['hoso_cha_id']))
                let donviArray = dsDonVi.map(item => (item['hoso_id']))
                dsDonVi.forEach(item => {
                    //check item la parent node
                    if (donviChaArray.includes(item['hoso_id'])) {
                        //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
                        if (!donviArray.includes(item['hoso_cha_id'])) {
                            delete item.hoso_cha_id;
                        }
                        tree.push({ ...item, hasChild: true, expanded: item.muc_id < 2 })
                    } else {
                        tree.push(item)
                    }
                })
                return tree
            }
        },
        async initData() {
            try {
                const date = new Date();
                this.tt_bo_hs.giao_tungay = moment(new Date(Date.now() - (3600*24*10*1000))).format('DD/MM/YYYY');
                this.tt_bo_hs.denngay = moment(new Date()).format('DD/MM/YYYY');
                this.nhanvien_id = this.$root.token.getNhanVienID();
                await this.getDsDonViTheoNV();
                // console.log(this.dsDonVi);
                this.donViTree = await this.createTreeDonVi(this.dsDonVi);
                this.tt_doituong_hs.fieldsDonVi = {
                    dataSource: this.donViTree, id: 'hoso_id', text: 'ma_hs_doituong',
                    parentID: 'hoso_cha_id', hasChildren: 'hasChild'
                }
                // thong tin nguoi dung
                let res_tt_nd = await this.axios.post('/quantri/user/thongtin_nguoidung2',{});
                console.log(res_tt_nd);
                if(res_tt_nd.data && res_tt_nd.data.error_code == 'BSS-00000000'){
                    this.nguoidung_id = res_tt_nd.data.data['nguoidung_id'];
                    this.nhom_nd_id = res_tt_nd.data.data['nhom_nd_id'];
                }
                // load danh sach nhom quyen
                let res_quyen = await this.axios.get('/web-quantri/quyen/ds_quyen_nguoidung');
                this.dsQuyen = res_quyen.data.data ? res_quyen.data.data : [];

                await this.NAP_COMB_GIAOPHIEU();

                this.tsbtnLayTT_Click();
            } catch (error) {
                console.log(error);
            }
        },
        clickButton(key) {
            // if (!this.button[key]) {
            //     return false;
            // }
            if (key == "laytt") {
                // this.$refs.popupBuyDevices.show();
                this.tsbtnLayTT_Click();
            }
            if (key == "traphieu") {
                this.tsbtnTraPhieu_Click();
            }
            if (key == "xacnhan") {
                this.tsbtnXacNhan_Click();
            }
            if (key == "hoancong") {
                this.tsbtnHoanCong_Click();
            }
            if (key == "themfile") {
                this.btnThemFile_Click();
            }
            if (key == "huongdan") {
                this.btnHuongDan_Click();
            }
        },
        tsbtnLayTT_Click() {
            this.TIMKIEM("", 1);
        },
        tsbtnTraPhieu_Click() {
            if (!this.KIEMTRA_QUYEN(1)) return;
            this.TRAPHIEU();
        },
        TRAPHIEU() {
            try {
                // if (!this.KIEMTRA_PHIEU(0)) return;
                this.$prompt("Nhập nội dung trả phiếu", {
                    confirmButtonText: 'Có',
                    cancelButtonText: "Không"
                }).then(r => {
                    console.log(r.value);
                    let dt = [...this.options.hoso];
                    let huonggiao_ht = 0;
                    let ds = [];                    
                    for (let j = 0; j < dt.length; j++) {
                        let tem = {
                            bhs_id: "",
                            dvgiao_id: "",
                            dvnext_id: "",
                            dvtt_id: "",
                            lhs_id: "",
                            malhs: "",
                            nv_nhan: "",
                            phieu_id: "",
                            phieu_id_cha: "",
                            tthu_id: ""
                        }
                        let i = dt[j]
                        tem.bhs_id = i['bohs_id']?i['bohs_id'].toString():'';
                        tem.dvgiao_id = i['donvi_giao_id']?i['donvi_giao_id'].toString():'';//i['dvgiao_id'].toString();
                        tem.dvnext_id = i['dvnext_id']?i['dvnext_id'].toString():'';//i['dvnext_id'].toString();
                        tem.dvtt_id = i['dvtt_id']?i['dvtt_id'].toString():'';//i['dvtt_id'].toString();
                        tem.lhs_id = i['lohs_id']?i['lohs_id'].toString():'';//i['lohs_id'].toString();
                        tem.malhs = i['malo_hs']?i['malo_hs'].toString():'';//i['malo_hs'].toString();
                        tem.nv_nhan = i['nhanvien_nhan_id']?i['nhanvien_nhan_id'].toString():'';//i['nv_nhan'].toString();
                        tem.phieu_id = i['phieu_id']?i['phieu_id'].toString():'';//i['phieu_id'].toString();
                        tem.phieu_id_cha = i['phieu_cha_id']?i['phieu_cha_id'].toString():'';//i['phieu_cha_id'].toString();
                        tem.tthu_id = i['tuyenthu_id']?i['tuyenthu_id'].toString():'';//i['tthu_id'].toString();
                        ds.push(tem);
                        huonggiao_ht = parseInt(i["huonggiao_id"].toString());
                    }
                    this.axios.post("/web-quantri/hoso/traphieu_bohoso", {
                        danhsach: ds,
                        huonggiao_id: huonggiao_ht,
                        kieu: 2,
                        noidung: r.value,
                        xoalohs: 0
                    }).then(res => {
                        if (!res.data.data)
                            this.$toast.warning("Không có thông tin trả về khi thực hiện trả phiếu");

                        if (res.data.data.toString() != "1")
                            this.$toast.warning(res.data.data);
                        else
                            this.$toast.success('Thực hiện trả phiếu thành công');
                    })
                }).catch((ex) => {
                    console.log(ex)
                    alert(ex)
                })

            } catch (error) {
                console.log(error);
            }
        },
        vbohs_id() {
            if (this.model.hoso['bohs_id'])
                return Number(this.model.hoso['bohs_id']);
            return -1;
        },
        async btnThemFile_Click() {
            try {
                this.loading(true);
                // nhom file
                
                if (!this.KIEMTRA_QUYEN(0)){
                    this.loading(false);
                    return;
                } 
                let vbohs_id = this.vbohs_id();
                if (vbohs_id == -1) {
                    this.$toast.warning("Bạn chưa chọn bộ hồ sơ để thêm file");
                    return;
                }
                this.NAP_DS_HOPDONGTB(this.vhdkh_id_luoi());
                this.isEnableFileds = true;
                // 
                let res_nhomfile = await this.axios.get('/web-quantri/danhmuc/hoso/nhom_file');
                this.cbNhomFile = res_nhomfile.data.data ? res_nhomfile.data.data : [];
                // debugger
                // loai file
                let res_loaiFile = await this.axios.get('/web-quantri/danhmuc/hoso/loai_file');
                this.cbLoaiFile = res_loaiFile.data.data;

                this.button.themfile = false;
                this.loading(false);
            } catch (error) {
                this.loading(false);
                console.log(error);
            }
            
        },
        vhdkh_id_luoi() {
            if (this.model.hoso['hdkh_id'])
                return Number(this.model.hoso['hdkh_id'])
            return -1
        },
        async NAP_DS_HOPDONGTB(vhdkh_id_luoi) {
            try {
                this.loading(true)
                this.tt_file.loai_file = -1; // CboLoaiFile.EditValue = -1;
                this.tt_file.nhom_file = -1;//cboNhomFile.EditValue = -1;
                this.tt_file.duong_dan = ""; // txtDuongDan.Text = "";
                this.tt_file.ghichu = ""; // txtGhiChuFile.Text = "";
                // var myDataTables = tdan.GET_DATA_PROC("{?DB1}.Lay_ds_hd_tb_theo_hdkh_id", "vhdkh_id", hdkh_id);
                let res = await this.axios.post("/web-quantri/danhmuc/lay_ds_hd_tb_theo_hdkh_id", {
                    vhdkh_id: vhdkh_id_luoi,
                });
                let myDataTables = res.data.data ? res.data.data : []
                this.options.thuebao = []; // gridThueBao.DataSource = null;
                if (myDataTables.length) {
                    this.options.thuebao = myDataTables; // gridThueBao.DataSource = myDataTables;
                    // grvThueBao.BestFitColumns();
                    // this.LOC_LUOI_TB();
                }
                    this.loading(false)
            }
            catch (ex) {
                this.loading(false)
                this.$toast.error("Có lỗi khi lấy thông tin hợp đồng " + ex);
            }
        },
        LOC_LUOI_TB() {
            try {

                // var a = gridviewDanhSach.ActiveFilter;
                // a.Remove(gridviewDanhSach.Columns["MALO_HS"]);

                // // #region Nếu ko phải bắt buộc
                // if (cboDVGIAO.EditValue.ToString() != "-1") {
                //     var viewFilterInfo = new ViewColumnFilterInfo(gridviewDanhSach.Columns["MALO_HS"],
                //         new ColumnFilterInfo(
                //             "[DONVI_GIAO_ID]=" + cboDVGIAO.EditValue + " ", ""));
                //     gridviewDanhSach.ActiveFilter.Add(viewFilterInfo);


                // }
                // if (cboLOHS.EditValue.ToString() != "0") {
                //     var viewFilterInfo = new ViewColumnFilterInfo(gridviewDanhSach.Columns["MALO_HS"],
                //         new ColumnFilterInfo(
                //             "[LOHS_ID]=" + cboLOHS.EditValue + " ", ""));
                //     gridviewDanhSach.ActiveFilter.Add(viewFilterInfo);
                // }
                // gridviewDanhSach.BestFitColumns();
            } catch (error) {

            }
        },
        tsbtnHoanCong_Click() {
            this.hoancong_click = true
            this.GIAOPHIEU();
        },
        GIAOPHIEU() {
            // TẠo dữ liệu phiếu
            try {
                if (!this.KIEMTRA_PHIEU(1)) return;
                this.$confirm('Bạn có muốn lưu danh sách bộ hồ sơ không ?', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {                    
                    let dt = this.$refs.options_hoso.getSelectedRecords()
                    let huonggiao_ht = 0;
                    let ds = [];

                    for (let j = 0; j < dt.length; j++) {
                        let i = dt[j]
                        let tem = {
                            bhs_id: "",
                            dvgiao_id: "",
                            dvnext_id: "",
                            dvtt_id: "",
                            lhs_id: "",
                            malhs: "",
                            nv_nhan: "",
                            phieu_id: "",                            
                            tthu_id: ""
                        }
                        tem.bhs_id = i['bohs_id']?i['bohs_id'].toString():'';
                        tem.dvgiao_id = i['donvi_giao_id']?i['donvi_giao_id'].toString():'';
                        tem.dvnext_id = i['donvi_nhan_id']?i['donvi_nhan_id'].toString():'';                       
                        tem.lhs_id = i['lohs_id']?i['lohs_id'].toString():'';
                        tem.malhs = i['malo_hs']?i['malo_hs'].toString():'';                  
                        tem.phieu_id = i['phieu_id']?i['phieu_id'].toString():'';
                        tem.tthu_id = i['tuyenthutt_id']?i['tuyenthutt_id'].toString():'';     
                        tem.dvtt_id = i['donvi_id_tt']?i['donvi_id_tt'].toString():'';     
                        tem.nv_nhan = i['nhanvien_nhan_id']?i['nhanvien_nhan_id'].toString():'';                       
                        ds.push(tem);                        
                        huonggiao_ht = Number(i["huonggiao_id"].toString());
                    }
                    let input ={
                        danhsach: JSON.stringify(ds),
                        huonggiaoId: huonggiao_ht,
                        quytrinhId: this.quytrinh_id,
                        kieu: this.hoancong_click?4:2,
                        noidung: "",
                        xn_gv: 0,
                        hosoId: this.hoancong_click?this.vhoso_id:0 
                    }                    
                    this.axios.post("/web-hopdong/phattrienthuebao/giaobhs_donvi_tieptheo",input ).then(res => {
                        if (res.data.error!=200){
                            this.$toast.warning("Không có thông tin trả về khi thực hiện hoàn công");
                            this.hoancong_click = false
                            return
                        }

                        if (res.data.message != "Thành công")
                            this.$toast.warning(res.data.message);
                        else {
                            this.$toast.success('Thực hiện lưu kho thành công');
                            this.TIMKIEM("", 0);
                        }
                        this.hoancong_click = false
                    }).catch(e => {
                        this.hoancong_click = false
                        console.log(e);
                    })

                }).catch(() => {
                    this.hoancong_click = false
                    return;
                });
            } catch (error) {
                this.hoancong_click = false
                console.log(error);
            }
            
        },
        
        async tsbtnXacNhan_Click() {
            let tem = await this.KIEMTRA_QUYEN(1);
            console.log(tem);
            if(!tem) return;  
            this.XACNHAN_DS();
        },
        async getDsQuyenND() {
            try {
                let res = await this.axios.get('/web-quantri/quyen/ds_quyen_nguoidung');
                return res.data.data;
            } catch (error) {
                console.log(error);
            }
        },
        async KIEMTRA_QUYEN(kieu) {
            //kieu=0 la them file
            let tem = this.PHAILAM("BOSUNG_FILEHS");
            console.log(tem);
            if (tem)
            {
                let dsQuyen = await this.getDsQuyenND();
                if (kieu == 0) {
                    if (!dsQuyen.includes(2082)) // HOSO_LC.QUYEN_THEMFILE
                    {
                        this.$toast.warning("Bạn không có quyền thêm file hồ sơ (QID: " + 2082 + ")");
                        return false;
                    }
                }
                if (kieu == 1) {
                    if (!dsQuyen.includes(2083)) // HOSO_LC.QUYEN_THAOTAC))
                    {
                        this.$toast.warning("Bạn không có quyền thao tác trên bộ hồ sơ(QID: " + 2083 + ")");
                        return false;
                    }
                }
            }else{
                return true;
            }
        },
        // UR2.9.003_000 -> UR2.11.008_006
        async Thietlap_thaotac(huonggiao_id)
        {
            try {
                // string str = "";
                // str += " select a.thaotac_id, b.thaotac, a.enable, b.code";
                // str += " from " +
                //     DatabaseConstants.DB2 + ".luong_thaotac a, " +
                //     DatabaseConstants.DB2 + ".thaotac b, " +
                //     DatabaseConstants.DB2 + ".huonggiao_ldv c ";
                // str += " where a.thaotac_id = b.thaotac_id";
                // str += " and a.luong_id = c.luong_id";
                // str += " and c.huonggiao_id = " + huonggiao_id;
                // str += " and c.quytrinh_id = " + quytrinh_id;

                // dtThaoTac = tdan.GET_DATA_SQL(str);
                let res = await this.axios.post('/web-baohong/giao_cho_hths/sp_lay_ds_thaotac', {
                     "quytrinh_id": this.quytrinh_id,
                    "huonggiao_id": huonggiao_id
                });
                if(res.data.data && res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
                    this.thaotac = res.data.data;
                }
            } catch (error) {
                console.log(error);
            }
        },
        PHAILAM(code) {
            let dr = this.thaotac.filter(i => i.enable == 1 && i.code == code);
            console.log('x1: ', this.thaotac, 'x2: ', dr);
            if (dr.length > 0)
                return true
            return false;
        },
        async XACNHAN_DS() {
            try {
                this.$refs.ma_gd.focus();
                // gridviewDanhSach.CloseEditor();
                if (!this.KIEMTRA_PHIEU(0)) return;
                let cf = await this.$confirm('Bạn chắc chắn muốn xác nhận danh sách bộ hồ sơ không ?', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                });
                console.log(typeof cf);
                if(cf == "confirm"){                    
                    let dt = this.$refs.options_hoso.getSelectedRecords()                    
                    let huonggiao_ht = 0;
                    let ds = [];
    
                    for (let j = 0; j < dt.length; j++) {
                        let i = dt[j]
                        let tem = {
                            bhs_id: "",
                            dvgiao_id: "",
                            dvnext_id: "",                            
                            lhs_id: "",
                            malhs: "",                            
                            phieu_id: "",
                            phieu_id_cha: "",                            
                        }
                        tem.bhs_id = i['bohs_id']?i['bohs_id'].toString():'';
                        tem.dvgiao_id = i['donvi_giao_id']?i['donvi_giao_id'].toString():'';
                        tem.dvnext_id = i['donvi_nhan_id']?i['donvi_nhan_id'].toString():'';                       
                        tem.lhs_id = i['lohs_id']?i['lohs_id'].toString():'';
                        tem.malhs = i['malo_hs']?i['malo_hs'].toString():'';                  
                        tem.phieu_id = i['phieu_id']?i['phieu_id'].toString():'';
                        tem.phieu_id_cha = i['phieu_cha_id']?i['phieu_cha_id'].toString():'';                        
                        ds.push(tem);
                        huonggiao_ht = Number(i.huonggiao_id?i.huonggiao_id.toString():'0');
                    }
                    this.axios.post("/web-quantri/hoso/xacnhan_bohoso", {
                        danhsach: ds,
                        huonggiao_id: huonggiao_ht,
                        kieu: 2,
                        noidung: "",
                        xn_gv: 0
                    }).then(res => {
                        if (!res.data.data)
                            this.$toast.warning("Không có thông tin trả về khi thực hiện trả phiếu");
    
                        if (res.data.data.toString() != "1")
                            this.$toast.warning(res.data.data);
                        else {
                            this.$toast.success('Thực hiện xác nhận thành công');
                            this.TIMKIEM("", 0);
                        }
                    })
                }else{
                     // this.TIMKIEM("", 0);
                    // }).catch(() => {
                        this.$toast.warning("Tiến trình đã bị dừng bởi người dùng")
                        return;
                    // });
                }
            } catch (error) {
                console.log(error);
            }
        },
        async TIMKIEM(ma_gd, thongbao) {
            try {
                this.loading(true)
                this.$refs.ma_gd.focus();
                this.CLEAR();
                this.options.hoso = []; // gridDanhSach.DataSource = null;
                if (ma_gd == "")
                    ma_gd = "0";
                let res = await this.axios.post('/web-quantri/hoso/layds_bohoso_chuagiao_theo_makh_magd', {
                    ma_gd: ma_gd,
                    tths_id: this.tths_id,
                // nhanvien_id: this.nhanvien_id,
                    phieu_id: 0,
                    huonggiao_id: 0,
                    quytrinh_id: this.quytrinh_id,
                    kieugiao: this.tt_bo_hs.kieu_giao,
                    ttph_id: this.tt_bo_hs.trang_thai,
                    timngay: this.tt_bo_hs.ischkTungay ? 1 : 0,
                    tungay: this.tt_bo_hs.giao_tungay,
                    denngay: this.tt_bo_hs.denngay
                });

                if ((!res.data.data || !res.data.data.length) && thongbao == 1){
                    this.button.themfile = false;
                    this.$toast.warning("Không tìm thấy thông tin phiếu giao đến");
                }
                else {
                    this.options.hoso = res.data.data;
                    for(var i in this.options.hoso) this.options.hoso[i].da_xn=(this.options.hoso[i].ckb_xacnhan == '1'?'V':'')                    
                    this.NAP_COMBO_TIMKIEM();
                    if (this.nuthc)
                        this.NAP_DS_DL_DOITUONG();
                }
                this.loading(false)
            }
            catch (ex) {
                console.log('line 1348: ', ex);
                this.loading(false)
                // DROP_COMMAND(gid);
                this.$toast.warning("Có lỗi khi tìm kiếm giao phiếu " + ex);
            }
        },
        NAP_COMBO_TIMKIEM() {
            let dt = [...this.options.hoso];
            console.log(dt);
            if (dt && dt.length != 0) {
                if (dt.length > 0) {
                    let lhs_id = dt.map(e => {
                        if (e["lohs_id"] && e["malo_hs"]) {
                            return {lohs_id: e.lohs_id, malo_hs: e.malo_hs};
                        }
                    })
                    var dv = [...lhs_id]
                    this.cbloHS = dv;
                    
                    
                    if (this.cbloHS && this.cbloHS.length > 0) {
                        this.cbloHS = this.cbloHS.filter(x=>x != null)
                        this.cbloHS = Object.values(this.cbloHS.reduce((o, a) => { o[a.lohs_id] = a; return o}, {}));
                    }
                    
                }

                // ĐÃ LẤY DS TỪ BAN ĐẦU XEM XÉT CÓ NÊN CMT
                // var view_dvi = [...dt];
                // var dv_id = view_dvi.map(e => {
                //     if(e["donvi_giao_id"] && e["dv_giao"])
                //         return e
                // });
                // this.cbdvGiao = dv_id;
            }
        },
        CLEAR() {
            this.options.ct_hoso = [];
            this.tt_bo_hs.solohs = '';
            this.tt_bo_hs.trangthai = "";
            this.tt_bo_hs.dvgiao = "";
            this.tt_bo_hs.nd_tra = "";
            this.tt_bo_hs.makh = "";
            this.tt_bo_hs.tenkh = "";
            this.tt_bo_hs.magd = "";
            this.tt_bo_hs.cbdvGiao = [];
            this.tt_bo_hs.cbloHS = [];
        },
        KIEMTRA_PHIEU(kieu) {
            try {
                var dt = this.options.hoso

                if (dt == null || dt.length == 0) {
                    this.$toast.warning("Không có danh sách phiếu để thực hiện");
                    return false;
                }
                // dt.AcceptChanges();
                if (Object.keys(this.model.hoso).length == 0) {
                    this.$toast.warning("Bạn chưa chọn phiếu để thực hiện");
                    return false;
                }

                if (dt.filter(e => e['ckb_xacnhan'] == 0 && e['ischeck'] == 1).length > 0 && kieu == 1) {
                    this.$toast.warning("Trong danh sách có phiếu chưa được xác nhận bạn không thể thực hiện");
                    return false;
                }
                if (this.hoancong_click && this.vhoso_id == -1)
                {
                    this.$toast.warning("Bạn chưa chọn kho giá để lưu hồ sơ");
                    return false;
                }

                if (kieu != 1) return true;
                return true;
            }
            catch (ex) {
                console.log(ex)
                this.$toast.warning("Có lỗi khi kiểm tra danh sách phiếu " + ex);
                return false;
            }
        },

        currencyFormater(c) {
            try {
                // Create our number formatter.
                var formatter = new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD',

                    // These options are needed to round to whole numbers if that's what you want.
                    //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                    //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
                });
                return formatter.format(c);
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
    },
    mounted() {
        // this.handleTag()
        // this.gridviewDanhSach_FocusedRowChanged();
        // console.log(this.$root.token.getMaTinh());
    }
}
</script>

<style>
.e-grid td.e-selectionbackground {
    background-color: #F9E1A9 !important;
}

.e-grid tr:hover {
    background-color: #FFF9EB !important;
}

.e-checkbox-wrapper .e-check::before,
.e-css.e-checkbox-wrapper .e-check::before {
    content: "\e718";
    background-color: transparent;
    line-height: 16px;
}

.e-checkbox-wrapper .e-stop::before,
.e-css.e-checkbox-wrapper .e-stop::before {
    content: "\e7a5" !important
}
.daxacnhan {
    font-weight: bolder !important;
    color: blue  !important;
    font-size: 23px !important;
}
</style>
