<template src="./index.template.html"></template>
<script>
import breadcrumb from "@/components/breadcrumb"
import GNAPI from '../api/GachNoAPI.js'
import CommonAPI from '../api/Commons.js'
import { mapActions, mapGetters } from "vuex"
import XuatFile from "../../CSKH/utils/XuatFile.vue"
import moment from 'moment'
import xlsx from "xlsx"

export default {
    components: { breadcrumb, appModalXuatDuLieu: XuatFile },

    async created() {
        await this.initForm();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhSachNhanVienThuNganGetter', 
            'danhSachNhanVienThuCuocGetter']),
    },

    data() {
        return {
            header: {
                title: 'XUẤT FILE GẠCH NỢ VINAPHONE',                
                list: [
                    { name: 'Gạch nợ', link: { name: 'Ui.cards' } },
                    { name: 'Xuất file gạch nợ Vinaphone', link: { name: 'Ui.buttons' } },
                ]
            },

            params: {
                thuNgan_Checked: false,
                thuNgan_Label: 'Người gạch',
                thuNgan_Visible: true,                
                thuNgan_Options: [],
                thuNgan_Selected: null,
                kyHoaDon_Enable: true,
                kyHoaDon_Selected: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
                kyHoaDon_Max: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
                kyHoaDon_Value: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
                today: new Date(),
                tuNgay: new Date(),
                denNgay: new Date(),
                NVHT_Label: 'Hình thức',
                NVHTLabel_Visible: true,
                NVHTSelectList_Visible: true,
                NVHT_Options: [],
                NVHT_Selected: null,
                /* 0-Tiền mặt; 1-Đại lý; 2-Chuyển khoản; 3-Thanh lý; 4-Điều chỉnh; 5-Khác */
                HTTT_Selected: -1,
                kieuNo_Visible: false,
                kieuNo_Enable: true,
                kieuNo_Selected: 1,
                nhapExcel_Visible: false,
                nhapExcel_Checked: false,
                /* 1-Trích thường; 2-Không lấy trích thường; 3-Tất cả; 4-Trả trước di động */
                tieuChi_Selected: null,
                /* 1-Gạch nợ theo chu kỳ */
                hinhThucGachNo_Selected: 1,
                tapKH_Selected: 0
            },

            common_params: {
                quayThu_Id: 0,
                quyenThietLap: [],
                userNeo: ''
            },
            
            gachNo_grid: {
                items: [],
                checked: [],
                fields: [
                    {
                        fieldName: 'MA_KH',
                        headerText: 'MA_KH',
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'MA_TB',
                        headerText: 'MA_TB',
                        allowFiltering: true,
                        isPrimaryKey: true,
                        width: 120
                    },
                    {
                        fieldName: 'SOTIEN',
                        headerText: 'SOTIEN',
                        cellCssClass: "text-right",
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'HTTT_ID',
                        headerText: 'HTTT_ID',
                        cellCssClass: "text-right",
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'CHUKYNO',
                        headerText: 'CHUKYNO',
                        cellCssClass: "text-right",
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'DONVI',
                        headerText: 'DONVI',
                        cellCssClass: "text-right",
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'THUNGAN',
                        headerText: 'THUNGAN',                    
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'NGAY_TT',
                        headerText: 'NGAY_TT',
                        cellCssClass: "text-right",
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'MA_NV',
                        headerText: 'MA_NV',                    
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'MA_T',
                        headerText: 'MA_T',                    
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'CHUNGTU',
                        headerText: 'CHUNGTU',
                        cellCssClass: "text-right",
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'NGANHANG_ID',
                        headerText: 'NGANHANG_ID',                    
                        allowFiltering: true,                    
                        width: 120
                    },
                    {
                        fieldName: 'GOMHOADON',
                        headerText: 'GOMHOADON',                    
                        allowFiltering: true,                    
                        width: 120
                    },
                ]
            }            
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhSachNhanVienThuNgan', 
            'getDanhSachNhanVienThuCuoc']),

        async initForm() {
            await this.getUserNeo();
            if (this.common_params.userNeo === '' || this.common_params.userNeo === undefined)
            {
                this.$toast.error('User của bạn chưa có User Neo, bạn liên hệ số 01215 243 172 để hỗ trợ');
                this.$router.push({ name: 'Home' });
            }

            this.params.thuNgan_Visible = this.$auth.getUserName().toLowerCase() === 'admin' ? true : false;
            await this.getThongTinChuKyNo();
            await this.getQuyen();        
            if (this.$auth.getUserName().toLowerCase() === 'admin') {
                await this.getDSNhanVienThuNgan(true);
                this.params.kyHoaDon_Enable = true;
            }
            if (this.common_params.quyenThietLap.length > 0)
                this.params.kyHoaDon_Enable = true;            

            this.params.HTTT_Selected = 0;
            this.params.tieuChi_Selected = 3;
        },

        selectedItemsChanged(e) {
        },

        async onLayDL() {
            let ds = [];
            await this.getDSGachNoVinaphone(ds);
        },

        async onChangeFile(e) {
            this.params.nhapExcel_Checked = false;
            const files = e.target.files;
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {                
                return this.$toast.error("File excel không đúng định dạng!");
            }            
            const fileReader = new FileReader();            
            fileReader.onload = async (ev) => {
                try {
                    const data = ev.target.result;                    
                    const workbook = xlsx.read(data, { type: "binary" });
                    const wsname = workbook.SheetNames[0];
                    const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname], { raw: false });
                    
                    if (!ws[0].hasOwnProperty("MA_TT") || !ws[0].hasOwnProperty("KIEUNO") || !ws[0].hasOwnProperty("NGAY_TT"))
                        return this.$toast.error('File excel không đúng, sai tên cột, bạn hãy đặt lại tên cột là: MA_TT, KIEUNO, NGAY_TT');
                        
                    if (ws.length > 0) {
                        for (let i = 0; i < ws.length; i++) {
                            if (ws[i].MA_TT.trim() === '')
                                continue;                            
                            if (ws[i].NGAY_TT.toString().length < 10)
                                return this.$toast.error(`Dữ liệu ngày thanh toán tại dòng ${i} không đúng!`);
                            
                            let isDate = moment(ws[i].NGAY_TT, 'DD/MM/YYYY').isValid();
                            console.log(isDate)
                            if (!isDate)
                                return this.$toast.error(`Dữ liệu ngày thanh toán tại dòng ${i} không đúng!`);
                            if (ws[i].KIEUNO !== '0' && ws[i].KIEUNO !== '1' && ws[i].KIEUNO !== '5')
                                return this.$toast.error(`Dữ liệu kiểu nợ tại dòng ${i} không đúng!`);
                        }
                        let ds = [];
                        ws.forEach(item => {
                            ds.push({ kieuNo: Number(item.KIEUNO), maTT: item.MA_TT, ngayTT: item.NGAY_TT });
                        })
                        await this.getDSGachNoVinaphone(ds);
                    }
                } catch (e) {
                    console.log(e);
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
            };
            fileReader.readAsBinaryString(files[0]);
        },

        onXuatFile() {
            if (this.gachNo_grid.items.length <= 0)
                this.$toast.error('Chưa có bản ghi nào để xuất!');
            else {
                let str = "";
                let sTen = this.params.HTTT_Selected === 1 ? this.params.NVHT_Selected + ' ' : 'DSGN_';
                let sNgay = moment(this.params.tuNgay).format('DD') + '_' + moment(this.params.denNgay).format('DD-MM');
                let displayedData = JSON.parse(JSON.stringify(this.gachNo_grid.items));
                displayedData.forEach(element => {
                    for (var key of Object.keys(element)) {
                        str += `${element[key]},`;                        
                    }
                    str = str.slice(0, -1);  //Remove last common of each line
                    str += `\r\n`;           //New line
                });

                let fileName = sTen + sNgay + '.txt';
                var FileSaver = require("file-saver");
                var file = new File([str], fileName, {
                    type: "text/plain;charset=utf-8"
                });
                FileSaver.saveAs(file);
                this.$toast.success('Lưu thành công!');
            }
        },

        onXuatExcel() {
            if (this.gachNo_grid.items.length <= 0)
                this.$toast.error('Chưa có bản ghi nào để xuất!');
            else {                
                this.$bvModal.show("modal-xuat-file");
            }
        },

        async onKyHoaDonChange(args) {      
            if(args.value === null)
                return this.$toast.error('Vui lòng chọn Kỳ hóa đơn');
            
            await this.getThongTinChuKyNo();
        },        
        
        string2Date(str) {
            let s = str.split('/');            
            if (s.length === 3)
                return new Date(s[2], Number(s[1]) - 1, s[0]);
            else 
                this.$toast.error('Không đúng định dạng ngày tháng năm!');
        },

        validateDate() {
            if (this.params.tuNgay === null) {
                this.$toast.error('Vui lòng chọn từ ngày hợp lệ!');
                return false;
            }
            if (this.params.denNgay === null) {
                this.$toast.error('Vui lòng chọn đến ngày hợp lệ!');
                return false;
            }
            return true;
        },

        async getQuyen() {
            try {
                this.loading(true);
                this.common_params.quyenThietLap = [];
                let response = await CommonAPI.getDSQuyenGachNoL(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {  
                    this.common_params.quyenThietLap = response.data.data.filter(e => e.QUYEN_ID === '50');                    
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getThongTinChuKyNo() {
            try {
                this.loading(true);
                let kyCuoc = moment(this.params.kyHoaDon_Selected).format('YYYYMM') + '01';
                this.params.tuNgay = this.params.today;
                this.params.denNgay = this.params.today;
                let response = await CommonAPI.getThongTinChuKyNo(this.axios, kyCuoc);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {  
                    this.params.tuNgay = this.string2Date(response.data.data[0].NGAY_BD);
                    this.params.denNgay = this.string2Date(response.data.data[0].NGAY_KT);
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getUserNeo() {
            try {
                this.loading(true);
                this.common_params.userNeo = '';
                let response = await CommonAPI.getUserNeo(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {  
                    this.common_params.userNeo = response.data.data[0].USER_NEO;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },
        
        /* isAdmin: if user = admin */
        async getDSNhanVienThuNgan(isAdmin) {
            try {
                this.loading(true);
                this.params.thuNgan_Options = [];
                let response = isAdmin ? await CommonAPI.getDSNguoiDung(this.axios) : await GNAPI.getDSThuNgan(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {                   
                    response.data.data.forEach(item => {
                        this.params.thuNgan_Options.push({ id: item.NHANVIEN_ID, text: item.TEN_NV, quay: item.DONVI_ID  })
                    });
                    let nd = this.params.thuNgan_Options.findIndex(e => e.NHANVIEN_ID === this.$auth.getNhanVienID());                    
                    this.params.thuNgan_Selected = nd !== -1 ? this.params.thuNgan_Options[nd].id : this.params.thuNgan_Options[0].id;
                    this.common_params.quayThu_Id = isAdmin ? '-1' : (nd !== -1 ? this.params.thuNgan_Options[nd].quay : this.params.thuNgan_Options[0].quay);
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getHinhThucThanhToanNo() {
            try {
                this.loading(true);
                this.params.NVHT_Options = [];                
                let response = await CommonAPI.getHinhThucThanhToan(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {                   
                    response.data.data.forEach(item => {
                        this.params.NVHT_Options.push({ id: item.HTTT_ID, text: item.HTTT });
                    });
                    this.params.NVHT_Selected = this.params.NVHT_Options[0].id;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDSGachNoVinaphone(ds) {
            if (!this.validateDate())
                return;
            try {
                this.loading(true);
                this.gachNo_grid.items = [];
                let htttId = -1;
                let maNVTC = '';
                let kyCuoc = Number(moment(this.params.kyHoaDon_Selected).format('YYYYMM') + '01');
                switch (this.params.HTTT_Selected) {
                    case 0:
                        htttId = this.params.NVHT_Selected;
                        break;
                    case 1:
                        htttId = 46;
                        maNVTC = this.params.NVHT_Selected.toString();
                        break;
                    case 2:
                        htttId = 6;
                        break;
                    case 3:
                        htttId = 84;
                        break;
                    case 4:
                        htttId = 83;
                        break;
                    default:
                        htttId = -1;
                        break;
                };
                
                let apiBody = {
                    chuyenKhoan: this.params.HTTT_Selected === 2 ? 1 : 0,
                    denNgay: moment(this.params.denNgay).format('DD/MM/YYYY'),
                    ds: ds,
                    htttId: htttId,
                    kieu: this.params.hinhThucGachNo_Selected,
                    kyCuoc: kyCuoc,
                    maNVTC: maNVTC,
                    maQuay: this.common_params.quayThu_Id.toString(),
                    tapKH: this.params.tapKH_Selected.toString(),
                    tieuChi: this.params.tieuChi_Selected,
                    tuNgay: moment(this.params.tuNgay).format('DD/MM/YYYY')
                };
                let response = await GNAPI.getDSGachNoVinaphone(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {                   
                    this.gachNo_grid.items = response.data.data.map((item) => ({
                        ...item,
                        SOTIEN: Number(item.SOTIEN)
                    }));
                }
            }
            catch (error) {
                console.log(error);
                this.$toast.error(error.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.thuNgan_Checked': async function(newVal, oldVal) {
            if (newVal) {
                this.params.thuNgan_Visible = true;
                this.params.thuNgan_Label = 'Thu ngân';
                await this.getDSNhanVienThuNgan(false);
            } else {
                if (this.$auth.getUserName().toLowerCase() === 'admin')
                {
                    this.params.thuNgan_Label = 'Người gạch';
                    this.params.thuNgan_Visible = true;
                } else {
                    this.params.thuNgan_Visible = false;
                }
            }
        },

        'params.HTTT_Selected': async function(newVal, oldVal) {
            this.params.NVHTLabel_Visible = newVal === 0 || newVal === 1 ? true : false;
            this.params.NVHTSelectList_Visible = newVal === 0 || newVal === 1 ? true : false;
            this.params.kieuNo_Visible = newVal === 2 ? true : false;
            this.params.nhapExcel_Visible = newVal === 2 ? true : false;

            if (newVal === 0) {
                this.params.NVHT_Label = 'Hình thức';
                await this.getHinhThucThanhToanNo();
            } else if (newVal === 1) {
                this.params.NVHT_Label = 'Nhân viên TC';                
                if (this.danhSachNhanVienThuCuocGetter === undefined || this.danhSachNhanVienThuCuocGetter.length <= 0)
                    await this.getDanhSachNhanVienThuCuoc();

                this.params.NVHT_Options = [];
                this.danhSachNhanVienThuCuocGetter.forEach(item => {
                    this.params.NVHT_Options.push({ id: item.NHANVIEN_ID, text: item.TEN_NV });
                });
                this.params.NVHT_Selected = this.params.NVHT_Options[0].id;
            }
        },

        'params.nhapExcel_Checked': async function(newVal, oldVal) {
            this.params.kieuNo_Enable = !newVal;            
            if (newVal) {                
                this.$refs.fileInput.click();
            }
        }
    }
}
</script>
