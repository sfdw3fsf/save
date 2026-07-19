<template>
    <div class="box-form">
        <div class="legend-title">
            Thông tin Tra Cứu
        </div>
        
     
    </div>
</template>

<script>

import selectqt from '@/components/qt_components/select/index.vue';
import { Icon } from '@iconify/vue2'
import moment from 'moment'
import api from '../../API';
import apiHelper from '../../api.helper';
export default {
    name: 'ThongTinTraCuu',
    components: {
        selectqt,
        Icon,
    },
    props: {
       
    },

    data() {
        return {
            tbThongTinKH: {
                columns: [
                    { fieldName: 'ma_kh', headerText: 'Mã KH', width: '100px' },
                    { fieldName: 'ten_kh', headerText: 'Tên KH', width: '150px' },
                    { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', width: '150px' },
                    { fieldName: 'thang_bd', headerText: 'Tháng BDDC', width: '120px' },
                    { fieldName: 'thang_kt', headerText: 'Tháng KTDC', width: '120px' },
                    { fieldName: 'pbh_th', headerText: 'Tên PBH TH', width: '120px' },
                    { fieldName: 'nv_th', headerText: 'Tên NV TH', width: '120px' }
                ],
                data: []
            },
            cbbTTKetNoi: {
                options: [],
                value: null
            },
            cbbKetQuaOB: {
                options: [],
                value: null
            },
            cbbTinhTrangOB: {
                options: [],
                value: [-1]
            },

            txtNote: "",
          

        }
    },
    methods: {
        
    },
    watch: {

    }

}
</script>