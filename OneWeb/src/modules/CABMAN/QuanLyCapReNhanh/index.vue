<template src="./index.html"></template>
<script>
import QuanLyCapReNhanh from "./QuanLyCapReNhanh"
export default {
  components:{QuanLyCapReNhanh},
  name: "index",
  data: function (){
    return {
      midspanObject: {
        MIDSPAN_ID:null,
        CAP_ID:null,
        KETCUOI_N_ID:null,
        KETCUOI_D_ID:null,
        KYHIEU_KETCUOI_N:null,
        KYHIEU_KETCUOI_D:null,
        DONVI_ID:null,
        DONVI_TD_ID:null,
        TEN_DV:null,
        TEN_DV_TD:null,
        LOPMC_ID:null,
        LOP_MC:null,
        KIEUDC_ID:null,
        KIEUCAP_ID:null,
        KIEU_CAP:null,
        KYHIEU:"",
        CHIEUDAI:null,
        DS_SOI:"",
      },
    }
  },
  methods:{
    btnShowQLCapReNhanh(){
      this.clearMidspan()
      let isInsert = false
      if (isInsert){
        // let ID = "_KETCUOI_110669"
        // let doituong_id = ID.split("_"), nguon_id = "_KETCUOI_77383_92675".split("_")
        // if (nguon_id[1] !== doituong_id[1] || nguon_id[2] !== doituong_id[2]){
        //   let dich_id = doituong_id
          let donvi_id = null
          let ketcuoi_n_id = 77383
          let ketcuoi_d_id = 110669
          let donvi_td_id = null
          let cap_id = 92675
          // if (nguon_id[1] === "TRAM")
          //   donvi_id = nguon_id[2];
          // else
          //   ketcuoi_n_id = nguon_id[2];
          //
          // if (dich_id[1] === "TRAM")
          //   donvi_td_id = dich_id[2];
          // else
          //   ketcuoi_d_id = dich_id[2];
          this.midspanObject.CAP_ID = cap_id
          this.midspanObject.DONVI_ID = donvi_id
          this.midspanObject.DONVI_TD_ID = donvi_td_id
          this.midspanObject.KETCUOI_N_ID = ketcuoi_n_id
          this.midspanObject.KETCUOI_D_ID = ketcuoi_d_id
          this.midspanObject.KIEUCAP_ID = 0
        // }
      }else {
        this.midspanObject.MIDSPAN_ID = 5
      }
      this.$bvModal.show('popupQuanLyCapReNhanh')
    },
    clearMidspan(){
      this.midspanObject.MIDSPAN_ID = null
      this.midspanObject.CAP_ID = null
      this.midspanObject.KETCUOI_N_ID = null
      this.midspanObject.KETCUOI_D_ID = null
      this.midspanObject.KYHIEU_KETCUOI_N = null
      this.midspanObject.KYHIEU_KETCUOI_D = null
      this.midspanObject.DONVI_ID = null
      this.midspanObject.DONVI_TD_ID = null
      this.midspanObject.TEN_DV = null
      this.midspanObject.TEN_DV_TD = null
      this.midspanObject.LOPMC_ID = null
      this.midspanObject.LOP_MC = null
      this.midspanObject.KIEUDC_ID = null
      this.midspanObject.KIEUCAP_ID = null
      this.midspanObject.KIEU_CAP = null
      this.midspanObject.KYHIEU = ""
      this.midspanObject.CHIEUDAI = null
      this.midspanObject.DS_SOI = ""
    },
  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'Quản lý cáp rẽ nhánh (Mid-Span)'
      })
    } catch (err) {
      console.log(err)
    }
  }
}
</script>

<style scoped>

</style>
