<template>
    <vue-card noborder label="Danh sách phiếu thoái trả">
        <vue-table :loading="loading" :options="options" :config="config" />
    </vue-card>
</template>
<script>
import api from '../api/index.js'
export default {
    data() {
        return {
            loading: false,
            options: [],
            config: [
                {
                    label: 'Mã giao dịch',
                    filterAble: true,
                    key: 'ma_gd'
                },
                {
                    label: 'Tên khách hàng',
                    filterAble: true,
                    key: 'ten_kh'
                },
                {
                    label: 'Địa chỉ',
                    filterAble: true,
                    key: 'diachi_kh'
                },
                {
                    label: 'Hình thức trả',
                    filterAble: true,
                    key: 'ht_tra'
                },
                {
                    label: 'Tổng tiền',
                    filterAble: true,
                    key: 'tongtien'
                },
                {
                    label: 'Ngày thoái',
                    filterAble: true
                },
            ]

        }
    },
    props: {
        thongtintimkiem: Object
    },
    methods: {
        getDanhSachThoaiTra() {
            this.loading = true
            api.getDanhSachThoaiTra(this.axios, {
                "ma_gd": this.thongtintimkiem.ma_gd,
                "ngay_bd": this.thongtintimkiem.ngay_bd,
                "ngay_kt": this.thongtintimkiem.ngay_kt,
                "nhanvien_id": 1832,
                "phanvung_id": 26
            }).then((res) => {
                this.options = res.data.data ? res.data.data : []
                this.loading = false
            })
        }
    }
}
</script>