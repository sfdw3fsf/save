<!-- Popup đọc file thêm cho Danh sách thuê bao không khóa mở -->
<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDocFileDSKhoaMo"
        :header='input.title'
        :animationSettings='animationSettings'
        showCloseIcon=true width='60%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
        <div class="">
            <div class="list-actions-top">
                <ul class="list">
                <li>
                    <a href="javascript: void(0)" @click="onReadFile" title="Hiển thị danh sách">
                        <span class="icon design_bullet-list-67 nc-icon-glyph"></span> Danh sách
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onAccept" title="Chấp thuận">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onExcelExport" title="Xuất excel">
                        <span class="icon one-excel"></span> Xuất Excel
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="close" title="Thoát">
                        <span class="icon one-export"></span> Thoát
                    </a>
                </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row note text-main">
                        <div class="key w180">
                            <span class="one-alert f20 text-warning"></span> File excel gồm 2 trường:
                        </div>
                        <div class="value" style="vertical-align: bottom;">
                            <ejs-radiobutton label='THUEBAO_ID, MA_TB' name='default' :htmlAttributes="{ value: 1 }" v-model="params.inputType"></ejs-radiobutton>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <ejs-radiobutton label='MA_TB, LYDO' name='default' checked=true :htmlAttributes="{ value: 2 }" v-model="params.inputType"></ejs-radiobutton>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w180">Dịch vụ</div>
                        <div class="value">
                            <div>
                                    <select2
                                        :options = "params.dichVu_Options"
                                        v-model = "params.dichVu_Selected"
                                    />
                                </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w180">Tên file</div>
                        <div class="value">
                        <div class="input-more-button">
                            <label for="upload" class="btn">
                                <span class="-ap icon-more_horiz"></span>
                                <input ref="fileInput" type="file" id="upload" style="display: none" @change="onChangeFile"
                                    accept=".xls, .xlsx" @click="$refs.fileInput.value=null" />
                            </label>
                            <input type="text" class="form-control highlight" v-model="params.filePath" @change="onInputText($event.target.value)">
                        </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title" :class="[tableTitleClass]" style="font-weight: 600!important">
                        <div class="row">
                            <div class="col-sm-6 col-12">{{ input.title }}</div>
                            <div class="col-sm-6 red tleft col-12">
                                {{ processTime }}
                            </div>
                        </div>
                    </div>
                    <DataGrid v-bind:columns="[
                        {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-left'},
                        {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái TB', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'trangthai-col'},
                        {fieldName: 'LYDO', headerText: 'Lý do', allowFiltering: true, allowSorting: false, width: 150},
                        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 120}
                    ]"
                        v-bind:dataSource="thueBao_Items"
                        dataKeyField="MA_TB"
                        :showFilter="true"
                        :allowPaging="true"
                        :enablePagingServer="false"
                        :allowExcelExport="true"
                        :aggregates="aggregatesColumns"
                        @queryCellInfo="onDSTBQueryCellInfo"
                        ref="thueBaoGridThemFile"
                    >
                    </DataGrid>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<style scoped src="../../MoMayTheoDanhSach/style.scss"></style>
<style>
    .key.w180 {
        width: 180px !important;
    }
    .e-label {
        color: red !important;
    }
</style>
<script>
import xlsx from "xlsx";
import API from '../../api/DSThueBaoKhongKhoaMoAPI.js'
import { mapActions, mapGetters } from 'vuex'
import moment from 'moment'
import { RadioButtonPlugin } from "@syncfusion/ej2-vue-buttons";
import { enableRipple } from '@syncfusion/ej2-base';

enableRipple(true);
Vue.use(RadioButtonPlugin);
import Vue from 'vue'

export default {
    components: {},
    name: "PopupDocFileDSKhoaMo",

    props: {
        input: Object
    },

    async created() {
        if (this.danhSachDichVuVienThongGetter == null || this.danhSachDichVuVienThongGetter.length === 0)
            await this.getDanhSachDichVuVienThong();
        this.danhSachDichVuVienThongGetter.forEach(item => {
            this.params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
        });
        if (this.params.dichVu_Options.length > 0) {
            this.params.dichVu_Options.sort(this.compare);
            this.params.dichVu_Selected = this.params.dichVu_Options[0].id;
        }

        await this.getDanhSachThamSoMD(0);
    },

    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
        aggregatesColumns () {
            return [{ field: 'MA_TB', type: 'Count', format: 'N0', footerTemplate: this.countTemplate }]
        },
    },

    data() {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            thamSo_Items: [],
            thueBao_Items: [],
            tableTitleClass: 'text-main',
            processTime: '00:00:00',
            params: {
                dichVu_Options: [],
                dichVu_Selected: null,
                filePath: '',
                inputType: '2',
                files: {}
            },
            commons_params: {
                kiemTraQuyen: false,
                keyRDS_ID: 0
            },
            countTemplate: function() {
                return { template: Vue.component('countTemplate', {
                        template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
                    })
                }
            }
        };
    },
    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),

        compare(a, b) {
            if (a.text < b.text)
                return -1;
            if (a.text > b.text)
                return 1;

            return 0;
        },

        onDSTBQueryCellInfo(args) {
            if (args.column.field === 'TRANGTHAI') {
                if (args.data.TRANGTHAI !== 'OK')
                    args.cell.classList.add('hd-khac');
                else
                    args.cell.classList.remove('hd-khac');
            }
        },

        openDialog () {
            this.$refs.dlgDocFileDSKhoaMo.show();
        },

        async onReadFile() {
            if (this.params.filePath.trim() === '') {
                return this.$toast.error("Chưa chọn file để mở!");
            }
            try {
                await this.$bvModal.msgBoxConfirm('Bạn muốn mở file?', {
                "title": 'Thông báo',
                "size": 'lg',
                "buttonSize": 'lg',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy bỏ',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        if (this.params.files !== {})
                            await this.onChangeFile(this.params.files);
                        else
                            this.$toast.error("Vui lòng chọn lại file!");
                    }
                })
                .catch(err => {
                    // An error occurred
                    console.log(err);
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!");
            }
        },

        async onChangeFile(e) {
            let beginTime = new Date();
            this.processTime = '00:00:00';
            this.thueBao_Items = [];
            this.params.files = e;
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
                    const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);

                    //Validate input file:
                    if (Object.keys(ws[0]).length !== 2) {
                        this.$toast.error('Số lượng cột phải = 2');
                        return;
                    }

                    if (this.params.inputType === '1') {
                        if (!ws[0].hasOwnProperty("THUEBAO_ID")) {
                            this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng: THUEBAO_ID');
                            return;
                        }
                        if (!ws[0].hasOwnProperty("MA_TB")) {
                            this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng: MA_TB');
                            return;
                        }
                    } else {
                        if (!ws[0].hasOwnProperty("MA_TB")) {
                            this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng: MA_TB');
                            return;
                        }
                        if (!ws[0].hasOwnProperty("LYDO")) {
                            this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng: LYDO');
                            return;
                        }
                    }

                    if (ws.length > 0) {
                        let dsData = [];
                        ws.forEach(item => {
                            let data = {
                                ma_tt: item.MA_TB,
                                giatri: this.params.inputType === '1' ? item.THUEBAO_ID : this.params.dichVu_Selected,
                                ghichu: this.params.inputType !== '1' ? item.LYDO : null
                            }
                            dsData.push(data);
                        })
                        await this.postLocDanhSachTuFileThem(dsData, this.input.loaiDSId);

                        if (this.thueBao_Items.length <= 0) {
                            return this.$toast.error("Không tìm thấy dữ liệu!");
                        } else {
                            this.tableTitleClass = 'red';
                        }

                        let timeSpan = new Date() - beginTime;
                        let seconds = timeSpan / 1000;
                        let hours = parseInt( seconds / 3600 );
                        seconds = seconds % 3600;
                        let minutes = parseInt( seconds / 60 );
                        seconds = Math.round(seconds % 60);
                        this.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
                    }
                } catch (e) {
                    console.log(e)
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
            };
            fileReader.readAsBinaryString(files[0]);
            this.params.filePath = files[0].name;
        },

        async onAccept() {
            let dsAccept = this.$refs.thueBaoGridThemFile.filteredDataSource || []
            let accept = false;
            if (dsAccept.length <= 0)
                return this.$toast.error('Chưa có dữ liệu để nạp!');
            if (dsAccept.filter(item => item.TRANGTHAI !== 'OK').length > 0)
                return this.$toast.error('Trạng thái không thỏa mãn điều kiện!');
            if (this.input.loaiDSId === 0 && dsAccept.filter(item => item.TRANGTHAITB_ID.toString() === '1').length > 0)
                return this.$toast.error('Không áp dụng cho thuê bao hoạt động bình thường với trường hợp chêm khống chế');

            try {
                await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn cập nhật dữ liệu không?', {
                "title": 'Thông báo',
                "size": 'lg',
                "buttonSize": 'lg',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Không đồng ý',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        let ds = [];
                        for (let item of dsAccept) {
                            await this.getKey('RDS_ID');
                            let data = {
                                rds_id: this.commons_params.keyRDS_ID,
                                thuebao_id: item.THUEBAO_ID,
                                thanhtoan_id: item.THANHTOAN_ID,
                                dichvuvt_id: item.DICHVUVT_ID,
                                loaitb_id: item.LOAITB_ID,
                                loaids: this.input.loaiDSId,
                                lydo: item.LYDO !== '' ? item.LYDO : this.input.lyDo,
                                ngay_th: moment(this.input.tuNgay).format('DD/MM/YYYY'),
                                ghichu: 'Nạp file',
                                may_cn: "-",
                                ngay_cn: moment(new Date()).format('DD/MM/YYYY'),
                                ip_cn: null,
                                nguoi_cn: this.$auth.getUserName() !== -1 ? this.$auth.getUserName() : 'admin',
                                nhanvien_id: this.$auth.getNguoiDungID() !== -1 ? this.$auth.getNguoiDungID() : 3231,
                                ngay_th_den: this.input.denNgay_Checked ? moment(this.input.denNgay).format('DD/MM/YYYY') : null,
                                ma_tb: item.MA_TB
                            };
                            ds.push(data);
                        }

                        let result = await this.postDanhSachKhongXuLyTuFileThem(ds);
                        if (result) {
                            accept = true;
                            this.$toast.success("Cập nhật dữ liệu thành công!");
                        }
                    }
                })
                .catch(err => {
                    // An error occurred
                    console.log(err);
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }

            if (accept) {
                this.$emit('acceptChange', true);
                this.close();
            }
        },

        close() {
            this.params.filePath = '';
            this.params.inputType = '2';
            this.params.dichVu_Selected = this.params.dichVu_Options[0].id;
            this.params.files = {};
            this.thueBao_Items = [];
            this.tableTitleClass = 'text-main',
            this.processTime = '00:00:00',
            this.$refs.dlgDocFileDSKhoaMo.hide();
        },

        onExcelExport() {
            if (this.thueBao_Items.length <= 0)
                this.$toast.error('Chưa có dữ liệu để xuất excel!');
            else {
                let excelExportProperties = {
                    fileName:"DocFileDSKhoaMo.xlsx",
                    dataSource: this.thueBao_Items
                };
                this.$refs.thueBaoGridThemFile.excelExport(excelExportProperties);
            }
        },

        dialogOpen() {
        },

        dialogClose() {
        },

        async getDanhSachThamSoMD(id) {
            try {
                this.loading(true);
                this.commons_params.kiemTraQuyen = false;
                let response = await API.getDanhSachThamSoMD(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    response.data.data.forEach(item => {
                        if (item.MA_TS === 'DOCFILE_DSTB_KHONGNHANHONG' && item.TEN_TS.trim() === '1') {
                            this.commons_params.kiemTraQuyen = true;
                        }
                    })
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getKey(key) {
            try {
                this.loading(true);
                this.commons_params.keyRDS_ID = 0;
                let response = await API.getKey(this.axios, key);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.commons_params.keyRDS_ID = response.data.data[0];
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        /* ds: [{ ma_tt: String, giatri: Number, ghichu: String }] */
        async postLocDanhSachTuFileThem(ds, kieu) {
            try {
                this.loading(true);
                this.thueBao_Items = [];
                let apiBody = {
                    pKyCuoc: this.input.kyHoaDon,
                    pKieuId: kieu,
                    pData: ds
                }
                let response = await API.postLocDanhSachTuFileThem(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.thueBao_Items = response.data.data;
                }
            }
            catch (error) {
                this.$toast.error(error.response.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        },

        async postDanhSachKhongXuLyTuFileThem(ds) {
            try {
                this.loading(true);
                let apiBody = {
                    pPhanVungId: this.$auth.getPhanVungID() !== -1 ? this.$auth.getPhanVungID() : 26,
                    pData: ds
                }
                let response = await API.postDanhSachKhongXuLyTuFileThem(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000')
                    return true;
                else {
                    this.$toast.error(response.data.message_detail);
                    return false;
                }
            }
            catch (error) {
                this.$toast.error(error.response.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    }
}
</script>
