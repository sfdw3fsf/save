
<template src="./ChannelCategoryList.html"></template>

<style src="./ChannelCategoryList.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './API'
import Vue from 'vue'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import {
    getDanhSachViTriTheoNhanVien
} from '../../admin/category/Staff/api'
//
export default {
    components: {
        breadcrumb,
        VueFlatPickr,
        API,
        DatePicker
    },
    name: 'ChannelCategoryList',
    mounted() {},
    data() {
        return {
            header: {
                title: 'Danh mục hạng kênh',
                list: [{
                        name: 'vnp08',
                        link: {
                            name: 'Ui.cards'
                        }
                    },
                    {
                        name: 'Danh mục hạng kênh',
                        link: {
                            name: 'Ui.buttons'
                        }
                    }
                ]
            },
            settingSelect2: {
                language: 'vi'
            },
            listTable: {
                hang_kenh_table: {
                    list: [],
                    header: [{
                            fieldName: 'hangkenh_id',
                            headerText: 'Hạng kênh ID',
                            allowFiltering: true,
                            width: 'auto'
                        },
                        {
                            fieldName: 'tenhangkenh',
                            headerText: 'Tên hạng kênh',
                            allowFiltering: true,
                            width: 'auto'
                        },
                        {
                            fieldName: 'mota',
                            headerText: 'Mô tả hạng kênh',
                            allowFiltering: true,
                            width: 'auto'
                        },
                        {
                            fieldName: 'trangthai',
                            headerText: 'Trạng thái hạng kênh',
                            allowFiltering: true,
                            width: 'auto'
                        }
                    ],
                    value: {},
                    isEnabled: true,
                },
            },
            cboTrangThai: {
                list: [{
                        'id': 0,
                        'text': 'Không hoạt động'
                    },
                    {
                        'id': 1,
                        'text': 'Hoạt động'
                    },
                ],
                value: 0,
                disable: false,
            },
            ma_hang_kenh: '',
            ten_hang_kenh: '',
            mo_ta: '',
            trang_thai: 0,
            is_insert: 0
        }
    },
    computed: {},
    mounted() {
        this.getListHangkenh()
    },
    methods: {
        ShowAlert(mesage) {
            this.$toast.error(mesage);
        },

        ShowSuccess(mesage) {
            this.$toast.success(mesage);
        },

        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },

        async getListHangkenh() {
            let input = {}

            let data = this.GetData(await API.sp_lay_ds_hang_kenh(this.axios, input))

            if (data && data.length > 0) {
                data = data.map(item => {
                    if (item.trangthai_id == 1) {
                        item.trang_thai = 'Active'
                    } else {
                        item.trang_thai = 'Deactive'
                    }
                    return item
                })
                this.listTable.hang_kenh_table.list = data
                this.channel_SelectionChanged(0, this.listTable.hang_kenh_table.list[0])
            }
        },

        async NhapMoi() {
            this.ten_hang_kenh = ''
            this.mo_ta = ''
            this.cboTrangThai.value = 0
            this.is_insert = 1
        },

        async capNhat() {
            if (this.ten_hang_kenh.trim() == '') {
                this.ShowAlert('Tên hạng kênh không được để trống')
                return
            }
            if (this.is_insert == 1) {
                let input = {
                    "p_mahangkenh": this.ma_hang_kenh,
                    "p_tenhangkenh": this.ten_hang_kenh,
                    "p_trangthai": this.cboTrangThai.value,
                    "p_mota": this.mo_ta
                }
                let rs = await API.insert_hangkenh(this.axios, input)
                if (rs) {
                    if (rs.data.data == 'ok') {
                        this.ShowSuccess('Thêm mới hạng kênh thành công')
                        this.getListHangkenh()
                    } else {
                        this.ShowAlert('Có lỗi khi thêm mới hạng kênh: ' + rs.data.message)
                    }
                }
                this.is_insert = 0
            } else {
                let input = {
                    "p_id_hangkenh": this.hangkenh_id,
                    "p_mahangkenh": this.ma_hang_kenh,
                    "p_tenhangkenh": this.ten_hang_kenh,
                    "p_trangthai": this.cboTrangThai.value,
                    "p_mota": this.mo_ta
                }

                let rs = await API.update_hangkenh(this.axios, input)
                if (rs) {
                    if (rs.data.data == 'ok') {
                        this.ShowSuccess('Cập nhật hạng kênh thành công')
                        this.getListHangkenh()
                    } else {
                        this.ShowAlert('Có lỗi khi cập nhật hạng kênh: ' + rs.data.message)
                    }
                }
            }
        },
        onLayTT(){
            this.getListHangkenh()
        },
        async xoaHangKenh() {
            this.$bvModal.msgBoxConfirm("Bạn có muốn xóa hạng kênh này không?", {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Không đồng ý',
            }).then(async v => {
                if (v) {
                    let input = {
                        "p_id_hangkenh": this.hangkenh_id
                    }
                    this.loading(true)
                    let kq = await API.delete_hangkenh(this.axios, input)
                    if (kq.data.error_code == 'BSS-00000000'&&kq.data.error == '200') {
                            this.ShowSuccess('Xóa hạng kênh thành công');
                            this.getListHangkenh();
                    }else{
                        this.showAlert(kq.data.message)
                    }
                    this.loading(false)
                }
            });
        },

        channel_SelectionChanged(idx, obj) {
            if (this.listTable.hang_kenh_table.list.length <= 0) return
            if (this.listTable.hang_kenh_table.list.length > 0) {
                if (obj) {
                    this.hangkenh_id = obj.hangkenh_id
                    this.ten_hang_kenh = obj.tenhangkenh
                    this.mo_ta = obj.mota
                    this.cboTrangThai.value = obj.trangthai_id
                }
            }
        }
    }
};
</script>
<style>
.info-row .value{
    padding-left: 0px !important;
}
.pd-l0{
    padding-left: 0px !important;
}
.select2-container .select2-selection--single{
    border: 1px solid #ced4da !important
}
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
</style>
