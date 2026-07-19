<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="pull-left">Danh sách thuê bao thay đổi tốc độ</div>
      <div class="pull-right">
        <div class="list-checks">

          <div class="item">
            <div class="check-action">
              <input type="radio" class="check" name="loaiphieu" value="1" checked @change="changeLoaiPhieu($event)" />
              <span class="name">Phiếu mới</span>
            </div>
          </div>
          <div class="item">
            <div class="check-action">
              <input type="radio" class="check" name="loaiphieu" value="2" @change="changeLoaiPhieu($event)"/>
              <span class="name">Phiếu trả lại</span>
            </div>
          </div>

          <div class="item red">Tổng số : {{dataSources.length}} thuê bao</div>

        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <k-table :columns="columns" :dataSources="dataSources" :allowFilter="true"/>
  </div>
</template>
<script>
import KTable from '@/components/kylq_components/KTable.vue'
export default {
  name: "DanhSachThueBao",
  components:{
      KTable
  },
  data(){
      return{
        columns:[
            {
                field: "ma_gd",
                label: "Mã GD",
                allowFilter: true
            },
            {
                field: "account",
                label: "Account",
                allowFilter: true
            },
            {
                field: "ten_tb",
                label: "Tên thuê bao",
                allowFilter: true
            },
            {
                field: "diachild",
                label: "Địa chỉ LĐ",
                allowFilter: true
            },
            {
                field: "trangthaiin",
                label: "Trạng thái in",
                allowFilter: true
            },
            {
                field: "ngay_kh",
                label: "Ngày KH",
                allowFilter: true
            },
            {
                field: "ngay_ht",
                label: "Ngày HT",
                allowFilter: true
            },
            {
                field: "loai_hinh",
                label: "Loại hình",
                allowFilter: true
            },
            {
                field: "ip",
                label: "IP",
                allowFilter: true
            },
            {
                field: "kieu",
                label: "Kiểu",
                allowFilter: true
            },
            {
                field: "kichhoat_vasc",
                label: "Kích hoạt Vasc",
                allowFilter: true
            },
            {
                field: "kieutra",
                label: "Kiểu trả",
                allowFilter: true
            },
            {
                field: "thangtra",
                label: "Tháng trả",
                allowFilter: true
            },
            {
                field: "ngaytra",
                label: "Ngày trả",
                allowFilter: true
            },
            {
                field: "donvigiao",
                label: "Đơn vị giao",
                allowFilter: true
            },
            {
                field: "ghichu",
                label: "Ghi chú",
                allowFilter: true
            },
        ],
        dataSources:[
            
        ],
        loaiphieu:1
      }
  },
  methods:{
      changeLoaiPhieu(event){
        this.loaiphieu=event.target.value 
      }
  }

}

</script>