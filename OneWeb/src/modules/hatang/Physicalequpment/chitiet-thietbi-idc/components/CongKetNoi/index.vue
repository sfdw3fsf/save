<template src="./index.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
import api from './api.js'
// frmTTMucCuocKhoanMuc.vue
export default {
    name: 'PortInfo',
    components: {
        breadcrumb,
        KDataGrid
    },
    provide() {
        return {
            portInfoForm: this
        }
    },
    props: {
        currentItem: {
            type: Object,
            required: true
        },
        currentMode: {
            type: String,
            required: true
        },
        controls: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            selectionOptions: {
                enableToggle: false, type: 'Single', checkboxMode: 'ResetOnRowClick', checkboxOnly: true
            },

            header: {
                title: 'Thông tin cổng kết nối',
                list: []
            },
            trangthai_port: {
                list: [{
                    id: '0',
                    text: 'Chưa hoạt động'
                },
                {
                    id: '1',
                    text: 'Hoạt động'
                },
                {
                    id: '2',
                    text: 'Hỏng'
                }
                ],
                value: 0
            },
            trangthai_ket_noi: {
                list: [{
                    id: '0',
                    text: 'Không kết nối'
                },
                {
                    id: '1',
                    text: 'Kết nối'
                }
                ],
                value: null
            },
            gridPort: {
                list: [],
                commands: [{
                    name: 'view',
                    title: 'xem',
                    cssClass: 'pad3 mar5 lh14 icon -ap one-eye btn btn-primary',
                    iconCss: 'fa fa-times',
                    width: 100
                },
                {
                    name: 'edit',
                    title: 'Sửa',
                    cssClass: 'pad3 mar5 lh14 icon -ap one-edit btn btn-primary',
                    iconCss: 'fa fa-times',
                    width: 100
                },
                {
                    name: 'delete',
                    title: 'Xóa',
                    cssClass: 'pad3 mar5 lh14 icon -ap one-trash btn btn-primary',
                    iconCss: 'fa fa-times',
                    width: 100
                },
                {
                    name: 'tt_dau_noi',
                    text: 'TT đấu nối',
                    title: 'TT đấu nối',
                    cssClass: 'btn btn-primary',
                    iconCss: 'fa fa-times',
                    width: 100
                }
                ],
                header: [{
                    fieldName: 'stt',
                    headerText: 'STT',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100
                },
                {
                    fieldName: 'TEN',
                    headerText: 'Tên đầu vào',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100,
                    template: () => {
                        return {
                            template: {
                                template: `<div>
                                <input type="text" class="form-control text" 
                                 @change="onChangeText"
                                :value="inp_TEN" 
                                :disabled="!data.IS_EDIT">
                                </div>`,
                                inject: ['portInfoForm'],

                                data: function () {
                                    return {
                                        data: {}
                                    }
                                },
                                computed: {
                                    parent() {
                                        return this.$parent.$parent.$parent.$parent.$parent
                                    },
                                    inp_TEN: {
                                        get() {
                                            var TEN = this.data.TEN || this.data.TEN == 0 ? this.numberWithCommas(this.data.TEN) : ''
                                            return TEN
                                        },
                                        set(value) {
                                            this.data.TEN = value
                                        }
                                    }
                                },
                                methods: {
                                    onChangeText: function (e) {
                                        this.data.TEN = e.target.value ? e.target.value : ''
                                        this.portInfoForm.onChangeText(this.data, 'TEN')
                                    },
                                    numberWithCommas(n) {
                                        var parts = n.toString().split('.')
                                        return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '')
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    fieldName: 'DA_DINHDANH',
                    headerText: 'Điện áp định danh (kV)',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100,
                    template: () => {
                        return {
                            template: {
                                template: `<div>
                                <input type="text" class="form-control text" 
                                 @change="onChangeText"
                                :value="inp_DA_DINHDANH" 
                                :disabled="!data.IS_EDIT">
                                </div>`,
                                inject: ['portInfoForm'],

                                data: function () {
                                    return {
                                        data: {}
                                    }
                                },
                                computed: {
                                    parent() {
                                        return this.$parent.$parent.$parent.$parent.$parent
                                    },
                                    inp_DA_DINHDANH: {
                                        get() {
                                            var DA_DINHDANH = this.data.DA_DINHDANH || this.data.DA_DINHDANH == 0 ? this.numberWithCommas(this.data.DA_DINHDANH) : ''
                                            return DA_DINHDANH
                                        },
                                        set(value) {
                                            this.data.DA_DINHDANH = value
                                        }
                                    }
                                },
                                methods: {
                                    onChangeText: function (e) {
                                        this.data.DA_DINHDANH = e.target.value ? e.target.value : ''
                                        this.portInfoForm.onChangeText(this.data, 'DA_DINHDANH')
                                    },
                                    numberWithCommas(n) {
                                        var parts = n.toString().split('.')
                                        return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '')
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    fieldName: 'DD_DINHDANH',
                    headerText: 'Dòng điện định danh (A)',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100,
                    template: () => {
                        return {
                            template: {
                                template: `<div>
                                <input type="text" class="form-control text" 
                                 @change="onChangeText"
                                :value="inp_DD_DINHDANH" 
                                :disabled="!data.IS_EDIT">
                                </div>`,
                                inject: ['portInfoForm'],

                                data: function () {
                                    return {
                                        data: {}
                                    }
                                },
                                computed: {
                                    parent() {
                                        return this.$parent.$parent.$parent.$parent.$parent
                                    },
                                    inp_DD_DINHDANH: {
                                        get() {
                                            var DD_DINHDANH = this.data.DD_DINHDANH || this.data.DD_DINHDANH == 0 ? this.numberWithCommas(this.data.DD_DINHDANH) : ''
                                            return DD_DINHDANH
                                        },
                                        set(value) {
                                            this.data.DD_DINHDANH = value
                                        }
                                    }
                                },
                                methods: {
                                    onChangeText: function (e) {
                                        this.data.DD_DINHDANH = e.target.value ? e.target.value : ''
                                        this.portInfoForm.onChangeText(this.data, 'DD_DINHDANH')
                                    },
                                    numberWithCommas(n) {
                                        var parts = n.toString().split('.')
                                        return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '')
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    fieldName: 'LOAI_NDIEN',
                    headerText: 'Loại nguồn điện',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100,
                    template: () => {
                        return {
                            template: {
                                template: `<div>
                                <input type="text" class="form-control text" 
                                 @change="onChangeText"
                                :value="inp_LOAI_NDIEN" 
                                :disabled="!data.IS_EDIT">
                                </div>`,
                                inject: ['portInfoForm'],

                                data: function () {
                                    return {
                                        data: {}
                                    }
                                },
                                computed: {
                                    parent() {
                                        return this.$parent.$parent.$parent.$parent.$parent
                                    },
                                    inp_LOAI_NDIEN: {
                                        get() {
                                            var LOAI_NDIEN = this.data.LOAI_NDIEN || this.data.LOAI_NDIEN == 0 ? this.numberWithCommas(this.data.LOAI_NDIEN) : ''
                                            return LOAI_NDIEN
                                        },
                                        set(value) {
                                            this.data.LOAI_NDIEN = value
                                        }
                                    }
                                },
                                methods: {
                                    onChangeText: function (e) {
                                        this.data.LOAI_NDIEN = e.target.value ? e.target.value : ''
                                        this.portInfoForm.onChangeText(this.data, 'LOAI_NDIEN')
                                    },
                                    numberWithCommas(n) {
                                        var parts = n.toString().split('.')
                                        return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '')
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    fieldName: 'TRANGTHAI_SD',
                    headerText: 'Trạng thái port',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100,
                    template: () => {
                        return {
                            template: {
                                template: `<div>
                                    <select2 :z-index="2000" class="select2" v-model="data.TRANGTHAI_SD"
                                        :options="trangthai_port.list" @change="onChangeSelect(data, 'TRANGTHAI_SD')" :disabled="!data.IS_EDIT">
                                    </select2>
                                    </div>`,
                                inject: ['portInfoForm'],

                                data: function () {
                                    return {
                                        data: {},
                                        trangthai_port: {
                                            list: [{
                                                id: '0',
                                                text: 'Chưa hoạt động'
                                            },
                                            {
                                                id: '1',
                                                text: 'Hoạt động'
                                            },
                                            {
                                                id: '2',
                                                text: 'Hỏng'
                                            }
                                            ],
                                            value: 0
                                        }
                                    }
                                },
                                computed: {
                                    parent() {
                                        return this.$parent.$parent.$parent.$parent.$parent
                                    }
                                },
                                methods: {
                                    onChangeSelect: function (val, field) {
                                        this.portInfoForm.onChangeSelect(val, field)
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    fieldName: 'KETNOI',
                    headerText: 'Trạng thái kết nối',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100,
                    template: () => {
                        return {
                            template: {
                                template: `<div>
                        <select2 :z-index="2000" class="select2" v-model="data.KETNOI"
                            :options="trangthai_ket_noi.list" @change="onChangeSelect(data, 'KETNOI')" :disabled="true">
                        </select2>
                        </div>`,
                                inject: ['portInfoForm'],
                                data: function () {
                                    return {
                                        data: {},
                                        trangthai_ket_noi: {
                                            list: [{
                                                id: '0',
                                                text: 'Không kết nối'
                                            },
                                            {
                                                id: '1',
                                                text: 'Kết nối'
                                            }
                                            ],
                                            value: null
                                        }
                                    }
                                },
                                computed: {
                                    parent() {
                                        return this.$parent.$parent.$parent.$parent.$parent
                                    }
                                },
                                methods: {
                                    onChangeSelect: function (val, field) {
                                        this.portInfoForm.onChangeSelect(val, field)
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    fieldName: 'TEN_NGUON',
                    headerText: 'Thiết bị kết nối',
                    allowFiltering: true,
                    textAlign: 'center',
                    width: 100
                }
                ],
                value: {},
                isEnabled: true,
                checked: [],
                key: 1
            },
            list_gridPortSelected: [],
            thietBiId: '',
            loaiCongId: '1',
            port_tong: 0,
            port_da_dung: 0,
            port_con_lai: 0,
            txt_ten: 'Tên đầu vào',
            txt_dinhdangten: '',
            txt_batdautu: '',
        }
    },

    computed: {
        cal_port_tong() {
            const filtered = this.gridPort.list
            this.port_tong = filtered.length
            return filtered.length
        },
        cal_port_da_dung() {
            const filtered = this.gridPort.list.filter((x) => x.TRANGTHAI_SD == '1')
            this.port_da_dung = filtered.length
            return filtered.length
        },
        cal_port_con_lai() {
            const port_hong = this.gridPort.list.filter((x) => x.TRANGTHAI_SD == '2')
            this.port_con_lai = this.cal_port_tong - this.cal_port_da_dung - port_hong.length
            return this.port_con_lai
        },
        tenHeader() {
            return this.loaiCongId === '1' ? 'Tên đầu vào' : 'Tên đầu ra'
        }
    },
    methods: {
        loadInitialData: async function () {
            this.thietBiId = this.currentItem.id
            console.log("currentItem: ")
            console.log(this.currentItem)
            console.log("currentMode: ")
            console.log(this.currentMode)

            await this.get_port_theo_loai_cong_id()
        },
        chooseTab: async function (id) {
            if (id === '1') {
                this.loaiCongId = '1'
            } else {
                this.loaiCongId = '2'
            }
            await this.get_port_theo_loai_cong_id()
            const col = this.gridPort.header.find(x => x.fieldName === 'TEN')
            if (col) {
                col.headerText = this.loaiCongId === '1' ? 'Tên đầu vào' : 'Tên đầu ra'
            }
            this.list_gridPortSelected = []
        },
        get_port_theo_loai_cong_id: async function () {
            this.loading(true)
            try {
                let res = await api.get_port_theo_loai_cong_id(this.axios, {
                    loaiCongId: this.loaiCongId,
                    thietBiId: this.thietBiId
                })
                console.log(res)
                this.gridPort.list = Array.isArray(res.data.data) ?
                    res.data.data.map((item, index) => ({
                        stt: index + 1,
                        CODE: Math.random().toString(36).slice(2, 7),
                        IS_EDIT: false,
                        ...item
                    })) : []
            } catch (error) {
                console.error('Lỗi khi load danh sách cổng kết nối:', error)
            } finally {
                this.loading(false)
            }
        },
        onNhapMoi_Click: async function () {
            await this.get_port_theo_loai_cong_id()

        },
        validateCongKetNoi: function () {
            if (this.list_gridPortSelected.length == 0) {
                this.$root.toastError('Bạn chưa chọn dòng port cần lưu');
                return false;
            }
            return true;
        },
        onSaveCongKetNoi: async function () {
            this.$root.showLoading(true);
             try {
                for (const row of this.list_gridPortSelected) {
                    console.log(row)
                    let res = await api.add_or_update_thiet_bi_port(this.axios, {
                        portId: row?.ID ?? null, // <-- FIX CHUẨN
                        thietBiId: this.thietBiId,
                        ten: row.TEN,
                        dienApDinhDanh: row.DA_DINHDANH,
                        ddDinhDanh: row.DD_DINHDANH,
                        trangThaiSD: row.TRANGTHAI_SD,
                        trangThaiKetNoi: row.KETNOI,
                        loaiCongId: this.loaiCongId,
                    })
                    if (res.data.message !== 'fail') {
                        this.$root.toastSuccess('Lưu thành công')
                        await this.get_port_theo_loai_cong_id()
                    } else {
                        this.$root.toastError(res.data.data.message)
                    }
                }
            } catch (error) {
                console.error("API ERROR:", error);
                if (error.response) {
                    // server trả về lỗi HTTP
                    const msg = error.response.data?.message || "Yêu cầu không hợp lệ (400)";
                    this.$root.toastError(msg);
                } else {
                    // lỗi mạng hoặc không kết nối server
                    this.$root.toastError("Không thể kết nối server");
                }
                console.error('Lỗi khi load danh sách cổng kết nối:', error)
            } finally {
                this.$root.showLoading(false)
            }
        },
        onLuu_Click: async function () {
            this.loading(true)
            if (this.list_gridPortSelected.length == 0) {
                this.$root.toastError('Bạn chưa chọn dòng port cần lưu')
                this.loading(false)
                return;
            }
            try {
                for (const row of this.list_gridPortSelected) {
                    console.log(row)
                    let res = await api.add_or_update_thiet_bi_port(this.axios, {
                        portId: row?.ID ?? null, // <-- FIX CHUẨN
                        thietBiId: this.thietBiId,
                        ten: row.TEN,
                        dienApDinhDanh: row.DA_DINHDANH,
                        ddDinhDanh: row.DD_DINHDANH,
                        trangThaiSD: row.TRANGTHAI_SD,
                        trangThaiKetNoi: row.KETNOI,
                        loaiCongId: this.loaiCongId,
                    })
                    if (res.data.message !== 'fail') {
                        this.$root.toastSuccess('Lưu thành công')
                        await this.get_port_theo_loai_cong_id()
                    } else {
                        this.$root.toastError(res.data.data.message)
                    }
                }
            } catch (error) {
                console.error("API ERROR:", error);
                if (error.response) {
                    // server trả về lỗi HTTP
                    const msg = error.response.data?.message || "Yêu cầu không hợp lệ (400)";
                    this.$root.toastError(msg);
                } else {
                    // lỗi mạng hoặc không kết nối server
                    this.$root.toastError("Không thể kết nối server");
                }
                console.error('Lỗi khi load danh sách cổng kết nối:', error)
            } finally {
                this.loading(false)
            }
        },

        onXoa_Click: async function () {
            this.loading(true)
            if (this.list_gridPortSelected.length == 0) {
                this.$root.toastError('Bạn chưa chọn dòng port cần lưu')
                this.loading(false)
                return;
            }
            try {
                for (const row of this.list_gridPortSelected) {
                    console.log(row)
                    let res = await api.delete_thiet_bi_port(this.axios, {
                        portId: row?.ID ?? null,
                    })
                    if (res.data.message !== 'fail') {
                        this.$root.toastSuccess('Xóa thành công')
                        await this.get_port_theo_loai_cong_id()
                    } else {
                        this.$root.toastError(res.data.data.message)
                    }
                }
            } catch (error) {
                console.error("API ERROR:", error);
                if (error.response) {
                    // server trả về lỗi HTTP
                    const msg = error.response.data?.message || "Yêu cầu không hợp lệ (400)";
                    this.$root.toastError(msg);
                } else {
                    // lỗi mạng hoặc không kết nối server
                    this.$root.toastError("Không thể kết nối server");
                }
                console.error('Lỗi khi load danh sách cổng kết nối:', error)
            } finally {
                this.loading(false)
            }
        },

        gridCommand_In: async function (type, dataItem) {
            if (type == 'edit') {
                for (let item of this.gridPort.list) {
                    if (item.CODE == dataItem.CODE) {
                        item.IS_EDIT = !item.IS_EDIT
                    }
                }
            } else if (type == 'delete') {
                this.$bvModal.msgBoxConfirm('Bạn có muốn xoá port?', {
                    title: 'Xác nhận xoá', size: 'md', okTitle: 'Đồng ý', centered: true, cancelTitle: 'Không đồng ý',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)
                            var data = this.gridPort.list.filter((e) => e.CODE === dataItem.CODE)
                            for (const row of data) {
                                if (row?.ID == null) {
                                    this.gridPort.list = this.gridPort.list.filter(e => e.CODE != dataItem.CODE)
                                } else {
                                    let res = await api.delete_thiet_bi_port(this.axios, {
                                        portId: row?.ID ?? null,
                                    })
                                    if (res.data.message !== 'fail') {
                                        this.$root.toastSuccess('Xóa thành công')
                                        await this.get_port_theo_loai_cong_id()
                                    } else {
                                        this.$root.toastError(res.data.data.message)
                                    }
                                }
                            }
                        } catch (error) {
                            console.error("API ERROR:", error);
                            if (error.response) {
                                // server trả về lỗi HTTP
                                const msg = error.response.data?.message || "Yêu cầu không hợp lệ (400)";
                                this.$root.toastError(msg);
                            } else {
                                // lỗi mạng hoặc không kết nối server
                                this.$root.toastError("Không thể kết nối server");
                            }
                            console.error('Lỗi khi load danh sách cổng kết nối:', error)
                        } finally {
                            this.loading(false)
                        }
                    }
                });
            }
            this.gridPort.key++
        },

        btnThemPortIn_Click: function () {
            const dinhdangten = this.txt_dinhdangten
            var batdautu = this.txt_batdautu
            var port_exist = this.gridPort.list.length
            console.log(port_exist)
            var port_tong = this.port_tong
            console.log(port_tong)
            var length_port_add = port_tong - port_exist
            this.$root.toastSuccess(`Hệ thống đã tạo thêm ${length_port_add} dòng port phát sinh`)
            for (var i = 0; i < length_port_add; i++) {
                let row = {}
                row.IS_EDIT = true;
                (row.CODE = Math.random().toString(36).slice(2, 7)),
                    row.TEN = (dinhdangten === null || dinhdangten === '') ? '' : `${dinhdangten}_${batdautu}`
                row.DA_DINHDANH = ''
                row.DD_DINHDANH = ''
                row.LOAI_NDIEN = ''
                row.TRANGTHAI_SD = '0'
                row.KETNOI = this.currentItem.hieuLuc
                row.TEN_NGUON = this.currentItem.tenThietBi
                let ds = [row]
                ds = ds.concat(this.gridPort.list)
                this.gridPort.list = ds
                this.gridPort.key++
                batdautu++
            }
        },
        onChangePortTong_Change: function (e) {
            const input_nhap = Number(e.target.value)
            if (input_nhap < this.port_da_dung) {
                e.target.value = this.cal_port_tong
                this.$root.toastError('Tổng số lượng port không được nhỏ số lượng port hiện có')
                return
            }
            this.port_tong = input_nhap
        },

        onChangeText: function (dataItem, field) {
            for (let item of this.gridPort.list) {
                if (item.CODE == dataItem.CODE) {
                    item[field] = dataItem[field]
                }
            }
        },
        onChangeSelect: function (dataItem, field) {
            // if (field == 'TRANGTHAI_SD') {
            //     console.log(dataItem)
            //     if(dataItem.KETNOI == '0'){
            //         this.$root.toastError('Không thể chuyển trạng thái port sang trạng thái hỏng khi trạng thái kết nối đang là không kết nối!')
            //         return
            //     }
            // }
            for (let item of this.gridPort.list) {
                if (item.CODE == dataItem.CODE) {
                    item[field] = dataItem[field]
                }
            }
        },

        grid_RowSelected(item) {
            let rows = [];

            // Nếu là object → chuyển thành mảng 1 phần tử
            if (item.data && !Array.isArray(item.data)) {
                rows = [item.data];
            }
            // Nếu là array → dùng luôn
            else if (Array.isArray(item.data)) {
                rows = item.data;
            }

            rows.forEach(row => {
                if (!this.list_gridPortSelected.some(x => x.CODE === row.CODE)) {
                    var res = this.gridPort.list.filter(x => x.CODE === row.CODE)
                    this.list_gridPortSelected.push(res[0]); // chỉ push object đầu tiên
                }
            });

            console.log("Selected:", this.list_gridPortSelected);
        },

        grid_RowDeselected(item) {
            let rows = [];

            if (item.data && !Array.isArray(item.data)) {
                rows = [item.data];
            } else if (Array.isArray(item.data)) {
                rows = item.data;
            }

            rows.forEach(row => {
                this.list_gridPortSelected = this.list_gridPortSelected.filter(
                    x => x.CODE !== row.CODE // ⬅️ GIỜ x là object nên chạy đúng
                );
            });

            console.log("After deselect:", this.list_gridPortSelected);
        }
    }
}
</script>
