<template>
    <vue-card>
        <vue-card :noborder="true" label="Thông tin tìm kiếm">
            <b-row>
                <b-col sm="4">
                    <vue-input labelWidth="100" v-model="thongtintimkiem.somay" label="Số máy/Acc"></vue-input>
                </b-col>
                <b-col sm="3">
                    <vue-date labelWidth="100" ref="ngaygiao" required format="DD/MM/YYYY" type="date" v-model="thongtintimkiem.NGAYGIAO" label="Ngày giao"></vue-date>
                </b-col>
                <b-col sm="5">
                    <vue-select labelWidth="100" ref="trangthai" :validate="['required']" required v-model="thongtintimkiem.TTHD_ID" :options="options.trangthai" valueField="trangthai_id" labelField="trangthai"  label="Trạng thái"></vue-select>
                </b-col>
            </b-row>
        </vue-card>
        <vue-card :noborder="true" label="Thông tin giao phiếu">
            <b-row>
                <b-col sm="4">
                    <vue-select labelWidth="100" v-model="thongtintimkiem.HUONGGIAO" :options="options.huonggiao" labelField="huonggiao" valueField="huonggiao_id" label="Hướng giao"></vue-select>
                </b-col>
                <b-col sm="3">
                    <vue-input v-model="thongtintimkiem.TEN_TTYC" labelWidth="100" label="Công văn giao"></vue-input>
                </b-col>
                <b-col sm="2">
                    <vue-input v-model="thongtintimkiem.MA_TB" labelWidth="100" label="Số máy/Acc"></vue-input>
                </b-col>
                <b-col sm="3">
                    <vue-date labelWidth="100" v-model="thongtintimkiem.NGAYGIAO"  label="Ngày giao"></vue-date>
                </b-col>
            </b-row>
            <b-row>
                <b-col sm="4">
                    <vue-input labelWidth="100" v-model="thongtintimkiem.ND_GIAO" label="Nội dung giao"></vue-input>
                </b-col>
                <b-col sm="8">
                    <vue-select labelWidth="100" v-model="thongtintimkiem.DONVINHAN_ID" :options="options.donvi" labelField="NAME" valueField="ID" label="Đơn vị XL"></vue-select>
                </b-col>
            </b-row>
        </vue-card>
    </vue-card>
</template>
<script>
import api from '../api/index'
import TraCuuTheBaoKhuyenNai from '@/modules/complaint/SearchComplainDetail/components/TraCuuTheBaoKhuyenNai'
export default {
    data() {
        return {
            thongtintimkiem: {
                NGAYGIAO: new Date().toLocaleString('en-GB', { dateStyle: 'short' }),
                TTHD_ID: null
            },
            options: {
                trangthai: [],
                huonggiao: [],
                donvi: []
            },
            error: []
        }
    },
    props: {
        value: Object
    },
    components: {
        TraCuuTheBaoKhuyenNai
    },
    watch: {
        thongtintimkiem: {
            handler: function(value) {
                this.$emit('input', value)
                this.getThamSo(value.TTHD_ID)
            },
            deep: true
        }
    },
    created() {
        this.getDanhSachTrangThaiPhieu()
        this.getDanhSachTrangHuongGiao()
        this.getDanhSachDonViXl()
    },
    methods: {
        checkValidate() {
            this.error = []
            for(let item in this.$refs) {
                this.$refs[item].checkValidate()
                if(this.$refs[item].error.length > 0) {
                    this.error[item] = this.$refs[item].error
                }
            }
        },
        getDanhSachTrangThaiPhieu() {
            api.getDanhSachTrangThaiPhieu(this.axios).then(res => {
                this.options.trangthai = res.data.data ? res.data.data : []
                this.thongtintimkiem.TTHD_ID = this.options.trangthai[0] ? this.options.trangthai[0].trangthai_id : null
            })
        },
        getDanhSachTrangHuongGiao() {
            api.getDanhSachTrangHuongGiao(this.axios).then(res => {
                this.options.huonggiao = res.data.data ? res.data.data : []
            })
        },
        getDanhSachDonViXl() {
            api.getDanhSachDonViXl(this.axios).then(res => {
                this.options.donvi = res.data.data ? res.data.data : []
            })
        },
        getThamSo(vkieu_id) {
          api.getThamSo(this.axios, {vkieu_id}).then(res => {
            console.log(res.data.data)
          })
        }
    }
}
</script>
