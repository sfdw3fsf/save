<template src="./DanhSachLoaiKhachHang.html">
</template>

<script>

export default {  
  props: {
    loaikh_id:0,
    isShow: Boolean,
  },
  components : {
   },
  created: async function () {
            this.getNhomLKH();              
    },
  data : function() {
    return {
      
      param: {
        kieu_id: 0,
        nhomlkh_id:0,
      },
      dskieu: [
        { 
          KIEU_ID: 0,
          TEN_KIEU : 'Cá nhân'
        },
        { 
          KIEU_ID: 1,
          TEN_KIEU : 'Doanh nghiệp'
        }
      ],
      dsNhomLKH: [],
      tmp_dsNhomLKH: [],
      dsLoaiKhachHang: [],
      indexFocus: 0,
      loaikhid:0,
      ten_loaikh:'',
      dsColumn: [
        {
        fieldName: "MA_LOAIKH",
        headerText: "Mã Loại KH",
        allowFiltering: true,
        width: 100
        },
        {
        fieldName: "TEN_LOAIKH",
        headerText: "Tên Loại KH",
        allowFiltering: true
        }
      ]
    }
  },  
  methods : {    
    getNhomLKH: async function () {
            try {
                const rs = await this.$root.context.get(
                `/web-quantri/danhmuc-chung/CSS_NHOM_LKH`
                );
                this.tmp_dsNhomLKH = rs.data;
                this.dsNhomLKH=this.tmp_dsNhomLKH.filter((item)=>item.LOAI===0);
                this.param.nhomlkh_id=this.dsNhomLKH[0].NHOMLKH_ID;   
                //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
            } catch (error) {}
            },
    
  //   chapNhan () {
  //     this.$emit("chapNhan");   
  //  }
   onChange(...data) {                               
               this.loaikhid=data[0].LOAIKH_ID; 
               this.ten_loaikh=data[0].TEN_LOAIKH;              
        },
 chapNhan: async function() {
            this.onCloseModalLoaiKH({isUpdated: true,loaikh_id:this.loaikhid,ten_loaikh:this.ten_loaikh});        
  },
  onCloseModalLoaiKH: function(data = {isUpdated: false}) {
            console.log(data.isUpdated)
            this.$emit('onCloseModalLoaiKH', data); }
  },
  watch : {
    'param.kieu_id': function(newVal, oldVal) {
      this.dsNhomLKH=this.tmp_dsNhomLKH.filter((item)=>item.LOAI===newVal);
      this.param.nhomlkh_id=this.dsNhomLKH[0].NHOMLKH_ID;           
      },
    'param.nhomlkh_id': async function(newVal, oldVal) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-loai-khach-hang/${newVal}`
        );
        this.dsLoaiKhachHang = rs.data;        
      } catch (error) {}
      },

  }
};
</script>
<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

#table-scroll {
  height:410px;
  overflow:auto;  
  margin-top:20px;
}
.btn-info {
  color: #0176ff;
  background-color: #bae7ff;
  border-color: #ffffff;
}

.icon {
  font-size: 12px;
}
</style>