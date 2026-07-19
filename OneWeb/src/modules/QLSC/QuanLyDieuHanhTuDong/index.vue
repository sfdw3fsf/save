<template src='./index.html'>
</template>

<script>
import QuanLyNhanVienNhanSMS from './QuanLyNhanVienNhanSMS';
import QuanLyMucDoSuCo from './QuanLyMucDoSuCo';
import QuanLyChuKyNhanTin from './QuanLyChuKyNhanTin';

export default {
    components: {
        QuanLyNhanVienNhanSMS,
        QuanLyMucDoSuCo,
        QuanLyChuKyNhanTin,
    },
    data() {
        return {
            tab1: true,
            tab2: false,
            tab3: false,
            title: 'QUẢN LÝ NHÂN VIÊN NHẬN SMS'
        }
    },
    methods: {
        setTab: function(isTab1, isTab2, isTab3) {
            this.tab1 = isTab1;
            this.tab2 = isTab2;
            this.tab3 = isTab3;
        },
        clickTab: function(data) {
            if(data == 1){
                this.title = 'QUẢN LÝ NHÂN VIÊN NHẬN SMS';
                this.setTab(true, false, false);
            }
            else if(data == 2){
                this.title = 'QUẢN LÝ MỨC ĐỘ SỰ CỐ';
                this.setTab(false, true, false);
            }
            else {
                this.title = 'QUẢN LÝ CHU KỲ NHẮN TIN'
                this.setTab(false, false, true);
            }    
        }
    }
}
</script>
