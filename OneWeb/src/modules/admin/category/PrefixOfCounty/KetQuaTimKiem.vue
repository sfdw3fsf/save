<template>
  <div class="box-form">
    <div class="legend-title">Kết quả tìm kiếm</div>
    <k-table :columns="columns" :dataSources="dataSources" :allowFilter="true"/>
  </div>
</template>
<script>
import KTable from '@/components/kylq_components/KTable.vue'
export default {
    name:'KetQuaTimKiem',
    props:{
      dataSources:{
        type:Array,
        default: []
      }
    },
    data(){
        return {
            columns:[
              {
                field: "stt",
                label: "STT",
                allowFilter: true
              },
              {
                field: "TEN_TAIKHOAN",
                label: "Tên tài khoản",
                allowFilter: true
              },
              {
                field: "TEN_DANGKY",
                label: "Tên đăng ký",
                allowFilter: true
              },
              {
                field: "DIACHI",
                label: "Địa chỉ",
                allowFilter: true
              },
              {
                field: "LOAI_TAIKHOAN",
                label: "Loại tài khoản",
                allowFilter: true
              },
              {
                field: "SDT",
                label: "Số điện thoại",
                allowFilter: true
              },
              {
                field: "THU_DIENTU",
                label: "Thư điện tử",
                allowFilter: true
              },
              {
                field: "TRANG_THAI",
                label: "Trạng thái",
                allowFilter: true
              },
              {
                field: "NGAY_TAO",
                label: "Ngày tạo",
                allowFilter: true
              },

            ]
        }
    },
    components:{
        KTable
    }
}
</script>