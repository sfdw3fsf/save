<template>
    <vue-card :noborder="true" label="Thông tin tìm kiếm">
        <b-row>
            <b-col sm="6">
                <vue-input labelWidth="90" v-model="thongtintimkiem.ma_gd" label="Mã giao dịch"></vue-input>
            </b-col>
            <b-col sm="6">
                <vue-date label="Ngày thoái trả"></vue-date>
            </b-col>
            <b-col sm="6">
                <vue-date labelWidth="90" v-model="thongtintimkiem.ngay_bd" label="Từ ngày"></vue-date>
            </b-col>
            <b-col sm="6">
                <vue-date v-model="thongtintimkiem.ngay_kt" label="Đến ngày"></vue-date>
            </b-col>
        </b-row>
    </vue-card>
</template>
<script>
export default {
    data() {
        return {
            thongtintimkiem: {
                ma_gd: '',
                ngay_bd: '',
                ngay_kt: ''
            }
        }
    },
    props: {
        value: Object
    },
    watch: {
        thongtintimkiem: {
            handler: function(value) {
                this.$emit('input', value)
            },
            deep: true
        }
    }
}
</script>