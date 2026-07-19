<template>
  <div class="box-form">
    <div class="table-content">
      <DataGrid
        v-bind:dataSource="dataHistory.list"
        v-bind:columns="dataHistory.cols"
        ref="dataHistory"
        :panelDataHeight= "'150'"
        :showFilter="true"
        :enablePagingServer="false"
        :enabledSelectFirstRow="true"
        :allowPaging="true"
      />
    </div>

  </div>
</template>

<script>
import api from '../../API'
export default {
  name: 'LichSuSanPham',
  data:function(){
    return{
      dataHistory:{
        list:[

        ],
        cols: [
          {
            fieldName: "",
            headerText: "STT",
            width:150,
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ma_spdv",
            headerText: "Ngày sửa",
            width:150,
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "ten_spdv",
            headerText: "Người sửa",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "thaotac",
            headerText: "Hành dộng",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "trangthai_spdv",
            headerText: "Trạng thái",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "nhomspdv_id",
            headerText: "Nhóm SPDV",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "phuongthuc_id",
            headerText: "PT Phát triển",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "dv_chuquan",
            headerText: "Đơn vị chủ quản",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "",
            headerText: "Đơn vị phát triển",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "ma_spdv",
            headerText: "Mã sản phẩm",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "ten_spdv",
            headerText: "Tên nội bộ",
            allowFilter: true,
            allowSorting:false
          }
        ]
      },

    }
  },
  methods:{
    async load_data(spdv_id){
      if(spdv_id == null){
        this.dataHistory.list = null;
      }
      else{
        try{
          this.loading(true)
          let response = await api.sp_lay_ls_sanpham(this.axios, {spdv_id:spdv_id})
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.dataHistory.list=response.data.data
          }else{
            this.dataHistory.list=[]
          }
        }catch(e){
          console.log(e);
          this.$alert('Đã xảy ra lỗi', '', {
            dangerouslyUseHTMLString: true,
            confirmButtonText: 'OK',
            type: 'error'
          })
        }finally{
          this.loading(false)
        }
      }
    }
  },
}
</script>

<style scoped>

</style>
