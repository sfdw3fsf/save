<template src="./index.html"></template>
<script>
import QLCap from './QLCap'
import QuanLyDayNhay from './QLCapTD'
export default {
  components:{QLCap, QuanLyDayNhay},
  name: "index",
  data: function (){
    return {
      // capId:530580,
      //CASE UPD
      taomoi:false,
      editable:false,
      capObj:{
        CAP_ID:530580,
      },
      captdObj:{
        CAPTD_ID:1028,
      }
    }
  },
  created() {
    // this.TAO_TRAM_TB_KETCUOI();
  },
  methods:{
    // Thêm cáp từ trạm đến kết cuối (Lớp mạng cáp 1: Cáp gốc)
    TAO_TRAM_TB_KETCUOI(){
      this.taomoi = true
      this.editable = false
      this.capObj = {
        KETCUOI_N_ID:-1,
        KETCUOI_D_ID:536268, //e.Link.B.Id;

        DONVI_ID:10385,  //e.Link.A.Id;
        TOQL_ID:-1,
        CAPGOC_ID:-1,
        NHOMLC_ID:1,
        LOPMC_ID:1,
        KIEUCAP_ID:1,
        DONVI_TD_ID:-1 // tự bổ sung thêm để dễ kiểm tra
      }
    },
    // Thêm cáp từ kết cuối đến kết cuối (Lớp mạng cáp 2: Cáp trung gian)
    TAO_KETCUOI_KETCUOI(){
      this.taomoi = true
      this.editable = false
      this.capObj = {
        KETCUOI_N_ID:536267,//e.Link.A.Id;
        KETCUOI_D_ID:536268, //e.Link.B.Id;

        DONVI_ID:-1,
        TOQL_ID:-1,
        CAPGOC_ID:-1,
        NHOMLC_ID:1,
        LOPMC_ID:2,
        KIEUCAP_ID:1,
        DONVI_TD_ID:-1 // tự bổ sung thêm để dễ kiểm tra
      }
    },
    // Thêm cáp từ kết cuối đến trạm (Lớp mạng cáp 3: Cáp truyền dẫn)
    TAO_KETCUOI_TRAM_TB(){
      this.taomoi = true
      this.editable = false
      this.capObj = {
        KETCUOI_N_ID:536267,//e.Link.A.Id;
        KETCUOI_D_ID:-1,

        DONVI_ID:-1,
        TOQL_ID:-1,
        CAPGOC_ID:-1,
        NHOMLC_ID:1,
        LOPMC_ID:3,
        KIEUCAP_ID:1,
        DONVI_TD_ID: 10385,//e.Link.B.Id;
      }
    },
    // Thêm cáp từ trạm 1 đến trạm 2 (Lớp mạng cáp 4: Cáp gốc liên trạm)
    TAO_TRAM_TB_TRAM_TB(){
      this.taomoi = true
      this.editable = false
      this.capObj = {
        KETCUOI_N_ID:-1,
        KETCUOI_D_ID:-1,

        DONVI_ID:10386,//e.Link.A.Id;
        TOQL_ID:-1,
        CAPGOC_ID:-1,
        NHOMLC_ID:1,
        LOPMC_ID:4,
        KIEUCAP_ID:1,
        DONVI_TD_ID: 10385,//e.Link.B.Id;
      }
    },
  }
}
</script>

<style scoped>

</style>
