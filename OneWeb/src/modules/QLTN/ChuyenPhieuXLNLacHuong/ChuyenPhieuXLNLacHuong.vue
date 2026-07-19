<template src="./ChuyenPhieuXLNLacHuong.html"></template>
<script>
import PopupTimKiemNhanVienThuCuoc from '../components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc'
import PopupDanhSachLoiChuyenPhieuLH from '../components/PopupDanhSachLoiChuyenPhieuLH/PopupDanhSachLoiChuyenPhieuLH'
import ComboboxGrid from "../components/ComboboxGrid"
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns"
import { Query } from '@syncfusion/ej2-data'
import API from '../api/ChuyenPhieuXLNLacHuongAPI.js'
import API1 from '../api/ThayDoiKenhThuAPI.js'
import { mapActions, mapState, mapGetters } from 'vuex'
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import xlsx from "xlsx"
import Vue from 'vue'

Vue.use(DropDownListPlugin)

export default {
    components: {
        appKyCuoc: KyCuoc,
        PopupTimKiemNhanVienThuCuoc,
        PopupDanhSachLoiChuyenPhieuLH,
        appCombobox: ComboboxGrid
    },
    name: 'ChuyenPhieuXLNLacHuong',
    async created() {
        await this.initForm();
    },

    destroyed() {
        EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    },

    mounted() {
        this.$refs.maXLN.focus();
    },

    computed: {
        ...mapGetters('qltnCommon', ['danhMucQuanHuyenGetter'])
    },

    data() {
        return {
            thongtinKyHoaDon: {
                kyHoaDonSelected: null,
                chuKySelected: null
            },
            params: {
                quanHuyen_Options: [],
                quanHuyen_Selected: null,
                quanHuyen_Fields: { text: 'TEN_QUAN', value: 'QUAN_ID' },
                nhanVienXLMoi_Options: [],
                nhanVienXLMoi_Selected: null,
                maXLN: '',
                tenKH: '',
                diaChi: '',
                NVXuLyCu: '',
            },
            common_params: {
                nhanVienXLCu_Id: 0,
                nhanVienXLMoi_Id: 0,
                maNVTCMoi: '',
                isReadFile: false,
                isNotWatch: false,
                nhanVienXLMoi_Index: -1 //Selected index on ComboboxGrid
            },
            dsLoi: [],
            processTime: '00:00:00',
            phieuXLN_grid: {
                items: [],
                checked: [],
                selected: {},
                key: 1
            },
            dataInput: {},
            quanHuyenTemplate: function () {
                return {
                    template: Vue.component("itemTemplate", {
                        template: `<span style='display: table;width: 100%;margin: auto;'>
                        <span style='display: table-cell;vertical-align: middle;width: 50%; text-align: right; padding-right: 10px;'>{{data.QUAN_ID}}</span>
                        <span style='display: table-cell;vertical-align: middle;width: 50%; border-left-style: solid; border-width: 1px;'>{{data.TEN_QUAN}}</span></span>`,
                        data() {
                            return {
                                data: {}
                            };
                        }
                    })
                }
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', ['getDanhMucQuanHuyen']),

        async initForm() {
            EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);
            if (this.danhMucQuanHuyenGetter === undefined || this.danhMucQuanHuyenGetter.length === 0) {
                await this.getDanhMucQuanHuyen();
            }
            if (this.danhMucQuanHuyenGetter.length > 0) {
                this.params.quanHuyen_Options = this.danhMucQuanHuyenGetter.slice().sort(function (a, b) {
                    return Number(a.QUAN_ID) - Number(b.QUAN_ID);
                });
                this.params.quanHuyen_Selected = this.params.quanHuyen_Options[0].QUAN_ID;
            }
        },

        quanFiltering(e) {
            if (e.text == '') e.updateData(this.params.quanHuyen_Options);
            else {
                var query = new Query().select(["QUAN_ID", "TEN_QUAN"]);
                query = (e.text !== '') ? query.where('TEN_QUAN', 'contains', e.text, true) : query;
                e.updateData(this.params.quanHuyen_Options, query);
            }
        },

        NVXLMoi_selectedChanged(args) {
            if (args) {
                this.params.nhanVienXLMoi_Selected = args.NHANVIEN_ID;
                this.common_params.maNVTCMoi = args.MA_NV;
            }
        },

        NVXLMoi_dataBound() {
            if (this.common_params.nhanVien_Index !== -1)
                this.$refs.cbbNhanVienXLMoi.$refs.grid.setCurrentSelectedRow(this.common_params.nhanVien_Index);
        },

        async onKeyPress() {
            this.params.maXLN = this.params.maXLN.trim();
            if (this.params.maXLN === '')
                this.$toast.error('Hãy nhập mã XLN để tìm kiếm!');
            else {
                await this.getDanhSachXLNChuyenPhieu(this.params.maXLN);
                if (this.phieuXLN_grid.items.length > 0) {
                    this.common_params.nhanVienXLCu_Id = Number(this.phieuXLN_grid.items[0].NVTC_ID);
                    this.common_params.isReadFile = false;
                } else
                    this.$toast.error('Không có dữ liệu để hiển thị!');
            }
        },

        async onChangeFile(e) {
            const files = e.target.files;
            if (!files.length)
                return;
            else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase()))
                return this.$toast.error("File excel không đúng định dạng!");
            const fileReader = new FileReader();
            fileReader.onload = async (ev) => {
                try {
                    const data = ev.target.result;
                    const workbook = xlsx.read(data, { type: "binary" });
                    const wsname = workbook.SheetNames[0];
                    const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);

                    //Validate input file:
                    if (Object.keys(ws[0]).length !== 2)
                        return this.$toast.error('File phải có 2 cột: MA_XLN, MA_NVTC');
                    if (!ws[0].hasOwnProperty("MA_XLN") || !ws[0].hasOwnProperty("MA_NVTC"))
                        return this.$toast.error('Tên cột trong file không đúng định dạng: MA_XLN, MA_NVTC');

                    if (ws.length > 0) {
                        await this.onReadFile(ws);
                    }
                } catch (e) {
                    console.log(e)
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
            };
            fileReader.readAsBinaryString(files[0]);
            this.params.maXLN = files[0].name;
            this.common_params.isReadFile = true;
        },

        async onReadFile(dsMaXLN) {
            let dsLoiNV = [];
            let files = {
                file: []
            };
            for (let item of dsMaXLN) {
                let rs = await this.mapID(item.MA_NVTC);
                if (rs === '-1' || rs === null)    //Sai mã nhân viên
                    dsLoiNV.push(item.MA_XLN);
                else {
                    let newFile = {
                        maXLN: item.MA_XLN,
                        nhanVienMoiId: rs,
                        maNVTCMoi: item.MA_NVTC
                    };
                    files.file.push(newFile);
                }
            }
            if (files.file.length > 0)
                await this.kiemTraChuyenPhieuXLN(files);

            dsLoiNV.forEach(item => {
                this.dsLoi.push({ MA_XLN: item, LYDO: 'Mã NVTC trống hoặc Mã NVTC không đúng, không tìm được ID' });
            })
            if (this.dsLoi.length > 0)
                this.$refs.dlgDanhSachLoiChuyenPhieuLH.openDialog();

            //Filter invalid Ma_XLN:                    
            this.dsLoi.forEach(item => {
                let index = dsMaXLN.findIndex(e => e.MA_XLN === item.MA_XLN);
                dsMaXLN.splice(index, 1);
            });

            if (dsMaXLN.length > 0)
                await this.getDanhSachXLNChuyenPhieuTheoFile(dsMaXLN.map(a => a.MA_XLN));
            else
                return this.$toast.error('Không có dữ liệu để hiển thị!');
        },

        async onSave() {
            if (!this.common_params.isReadFile) {
                if (this.params.nhanVienXLMoi_Selected === null)
                    return this.$toast.error('Chưa chọn nhân viên thu cước mới!');
                if (this.params.nhanVienXLMoi_Selected === this.common_params.nhanVienXLCu_Id)
                    return this.$toast.error('Nhân viên mới không được trùng với nhân viên cũ!');
            }

            if (this.phieuXLN_grid.items.length <= 0 || this.phieuXLN_grid.checked.length <= 0)
                return this.$toast.error('Không có phiếu lạc hướng. Bạn hãy kiểm tra lại!');

            try {
                await this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn cập nhật thông tin?', {
                    "title": 'Thông báo',
                    "size": 'lg',
                    "buttonSize": 'lg',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Hủy bỏ',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18", "text-center"],
                })
                    .then(async (value) => {
                        if (value) {
                            let files = {
                                file: []
                            };
                            let beginTime = new Date();
                            this.phieuXLN_grid.checked.forEach(item => {
                                let newFile = {
                                    maNVTCMoi: this.common_params.maNVTCMoi,
                                    maXLN: item,
                                    nhanVienMoiId: this.params.nhanVienXLMoi_Selected
                                };
                                files.file.push(newFile);
                            });

                            if (files.file.length > 0) {
                                if (!this.common_params.isReadFile) { //Nếu không phải đọc file --> Kiểm tra (vì khi đọc file đã kiểm tra rồi)
                                    await this.kiemTraChuyenPhieuXLN(files);
                                    if (this.dsLoi.length > 0) {
                                        this.$refs.dlgDanhSachLoiChuyenPhieuLH.openDialog();

                                        //Filter invalid Ma_XLN:
                                        this.dsLoi.forEach(item => {
                                            let index = files.findIndex(e => e.MA_XLN === item.MA_XLN);
                                            files.splice(index, 1);
                                        });
                                    }
                                }
                            }

                            if (files.file.length > 0) {
                                let result = await this.updatePhieuXLNLacHuong(files.file);
                                if (result) {
                                    this.$toast.success('Chuyển phiếu lạc hướng thành công!');
                                    this.onCancel();
                                }
                            }

                            let timeSpan = new Date() - beginTime;
                            let seconds = timeSpan / 1000;
                            let hours = parseInt(seconds / 3600);
                            seconds = seconds % 3600;
                            let minutes = parseInt(seconds / 60);
                            seconds = Math.round(seconds % 60);
                            this.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
                        }
                    })
                    .catch(err => {
                        // An error occurred
                        console.log(err);
                    })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }
        },

        onCancel() {
            this.phieuXLN_grid.items = [];
            this.params.maXLN = '';
            this.params.tenKH = '';
            this.params.diaChi = '';
            this.params.NVXuLyCu = '';
        },

        onTimKiemNVTC() {
            this.dataInput.quan_Id = this.params.quanHuyen_Selected;
            this.$refs.dlgTimKiemNVTC.openDialog();
        },

        onPhieuXLNChecked(e) {
            this.phieuXLN_grid.checked = e;
        },

        onPhieuXLNSelected(args) {
            this.phieuXLN_grid.selected = args;
            if (args && Object.keys(args).length > 0) {
                this.params.tenKH = args.TEN_KH;
                this.params.diaChi = args.DIACHI_KH;
                this.params.NVXuLyCu = args.MANV_TC;
            }
        },

        /* value: { quanId: String, nhanVienTC_Id: String, maNVTC: String } */
        async getNVTCProcess(value) {
            if (Object.keys(value).length > 0) {
                this.common_params.maNVTCMoi = value.maNVTC;
                if (value.quanId) {
                    //Chọn quận khác:
                    if (Number(value.quanId) !== this.params.quanHuyen_Selected) {
                        this.common_params.isNotWatch = true;
                        await this.getDanhSachNhanVienXLNTheoQuan(value.quanId);
                        this.common_params.isNotWatch = true;
                        this.params.quanHuyen_Selected = Number(value.quanId);
                        if (this.params.nhanVienXLMoi_Options.length > 0 && value.nhanVienTC_Id) {
                            let idx = this.params.nhanVienXLMoi_Options.findIndex(e => e.NHANVIEN_ID === Number(value.nhanVienTC_Id));
                            if (idx !== -1)
                                this.common_params.nhanVien_Index = idx;
                        }
                    } else {
                        //Cùng quận, chỉ thay đổi nhân viên xử lý mới:
                        if (this.params.nhanVienXLMoi_Options.length > 0 && value.nhanVienTC_Id) {
                            let idx = this.params.nhanVienXLMoi_Options.findIndex(e => e.NHANVIEN_ID === Number(value.nhanVienTC_Id));
                            if (idx !== -1)
                                this.$refs.cbbNhanVienXLMoi.$refs.grid.setCurrentSelectedRow(idx);
                        }
                    }
                }
            }
        },

        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected = moment(e.p_kyhoadon).format('YYYYMM') + '01'
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no
        },

        async getDanhSachNhanVienXLNTheoQuan(quanId) {
            try {
                this.loading(true);
                this.common_params.nhanVien_Index = -1; //Reset selected index
                let apiParams = {
                    quanId: quanId
                }
                let response = await API.getDanhSachNhanVienXLNTheoQuan(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.params.nhanVienXLMoi_Options = response.data.data;
                } else this.params.nhanVienXLMoi_Options = [];
            }
            catch (error) {
                this.params.nhanVienXLMoi_Options = [];
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async kiemTraChuyenPhieuXLN(files) {
            try {
                this.loading(true);
                this.dsLoi = [];
                let response = await API.postKiemTraChuyenPhieuXLN(this.axios, files);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.dsLoi = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
                this.$toast.error(error.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhSachXLNChuyenPhieu(maXLN) {
            try {
                this.loading(true);
                this.phieuXLN_grid.key++;
                this.phieuXLN_grid.items = [];
                let apiParams = {
                    kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
                    maXLN: maXLN
                }
                let response = await API.getDanhSachXLNChuyenPhieu(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.phieuXLN_grid.items = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDanhSachXLNChuyenPhieuTheoFile(dsMaXLN) {
            try {
                this.loading(true);
                let apiParams = {
                    kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
                    ds: dsMaXLN
                }
                let response = await API.getDanhSachXLNChuyenPhieuTheoFile(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.phieuXLN_grid.items = response.data.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        /* Params: file: [{ maNVTCMoi: String, maXLN: String, nhanVienMoiId: 0 }] */
        /* Return: true/false = successful/failed */
        async updatePhieuXLNLacHuong(file) {
            try {
                this.loading(true);
                let apiBody = {
                    nguoiCN: '-',
                    mayCN: '-',
                    file: file
                }
                let response = await API.updatePhieuXLNLacHuong(this.axios, apiBody);
                return response.data.error_code === 'BSS-00000000' ? true : false;
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async mapID(id) {
            try {
                this.loading(true);
                let apiBody = {
                    maNV: id
                };
                let response = await API.getNhanVienIDTheoMaNV(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    return response.data.data[0].KETQUA;
                } else return '-1'
            }
            catch (error) {
                return '-1'
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.quanHuyen_Selected': async function (newVal, oldVal) {
            if (this.common_params.isNotWatch)
                this.common_params.isNotWatch = false;
            else {
                await this.getDanhSachNhanVienXLNTheoQuan(newVal);
            }
        }
    }
}
</script>