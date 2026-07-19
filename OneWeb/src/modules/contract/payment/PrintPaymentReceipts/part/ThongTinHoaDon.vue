<template>
    <vue-card :noborder="true" label="Thông tin hóa đơn">
        <b-row>
            <b-col sm="7">
                <vue-select labelWidth="90" valueField="loaihoadon_id" labelField="tentat" :options="options.danhsachhoadon"  label="Loại hóa đơn"></vue-select>
            </b-col>
            <b-col sm="5">
                <vue-select v-model="thongtinhoadon.nhanvien_id" valueField="nhanvien_id" labelField="ten_nv" :options="options.danhsachthungan" labelWidth="65" label="Thu ngân"></vue-select>
            </b-col>
        </b-row>
        <b-row>
            <b-col sm="4">
                <vue-select labelWidth="90" valueField="seri_id" labelField="seri" :options="options.danhsachseri" label="Seri"></vue-select>
            </b-col>
            <b-col sm="3">
                <vue-date labelWidth="50" label="Hoá đơn"></vue-date>
            </b-col>
            <b-col sm="5">
                <b-row>
                    <b-col>
                        <vue-select :options="options.danhsachloaitien" valueField="loaitien_id" labelField="ma_loaitien" labelWidth="65" label="Loại tiền"></vue-select>
                    </b-col>
                    <b-col>
                        <vue-input labelWidth="45" label="Tỷ giá"></vue-input>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>
    </vue-card>
</template>
<script>
import api from '../api/index.js'
export default {
    data() {
        return {
            options: {
                danhsachhoadon: [],
                danhsachthungan: [],
                danhsachseri: [],
                danhsachloaitien: []
            },
            thongtinhoadon: {
                nhanvien_id: '0'
            }
        }
    },
    created() {
        this.getDanhSachLoaiHoaDon()
        this.getDanhSachThuNgan()
        this.getDanhSachSeri()
        this.getDanhSachLoaiTien()
    },
    watch: {
        thongtinhoadon(value) {
            this.$emit('input', value)
        }
    },
    props: {
        value: Object
    },
    methods: {
        getDanhSachLoaiHoaDon() {
            api.getDanhSachLoaiHoaDon(this.axios, {
                'kieu': 1
            }).then(res => {
                this.options.danhsachhoadon = res.data.data ? res.data.data : []
            })
        },
        getDanhSachThuNgan() {
            api.getDanhSachThuNgan(this.axios, {
                "nhanvien_id": 452,
                "phanvung_id": 26
            }).then(res => {
                this.options.danhsachthungan = res.data.data ? res.data.data : []
            })
        },
        getDanhSachSeri() {
            api.getDanhSachSeri(this.axios, {
                "kieu": 1,
                "phanvung_id": 26
            }).then(res => {
                this.options.danhsachseri = res.data.data ? res.data.data : []
            })
        },
        getDanhSachLoaiTien() {
            api.getDanhSachLoaiTien(this.axios, {
                "kieu": 1,
                "phanvung_id": 26
            }).then(res => {
                this.options.danhsachloaitien = res.data.data ? res.data.data : []
            })
        }
    }
}
</script>