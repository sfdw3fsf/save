<template src='./TraCuuTTDongBo.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tracuuttdongbo";

export default {
  components: { breadcrumb },
  name: "TraCuuTTDongBo",

  data() {
    return {
     
    
      txtSoPhieu: "",
      vchungtu_id: 0,
      DSTT:[],
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      
    };
  },
  
  computed: {
    // ...mapState("tracuuttdongbo", statePropertyName),
    // ...mapGetters("tracuuttdongbo", getterName),
   
  },
  watch: {
   
  },
  mounted: async function () {
     
  },
  methods: {
    ...mapActions("tracuuttdongbo", actionName),
    // ...mapMutations("tracuuttdongbo", mutationName),

    async OpenDialog(){
      await this.tsbtnTimKiem_Click()
      this.$refs.popupTraCuuTTDongBo.show()
    },
    async tsbtnTimKiem_Click(){
      try{
        this.loading(true)
        var area = ""
        if(this.$auth.getMaTinh() == "BCN"){
          area = "BKN"
        }else if(this.$auth.getMaTinh() == "VTU"){
          area = "BRA"
        }else if(this.$auth.getMaTinh() == "HAG"){
          area = "HGI"
        }else if(this.$auth.getMaTinh() == "DAN"){
          area = "DNO"
        }else{
          area = this.$auth.getMaTinh()
        }

        let thongTin = await this.getSoIDFMIS({
          so_phieu: this.txtSoPhieu
        })
        console.log(thongTin , " = thongTin")
        let so_id = thongTin.length > 0 && "so_id" in thongTin[0] ? thongTin[0].so_id : ""
        console.log(so_id , " = so_id")
        var dongbo = await this.kiemTraTrangThaiIMS({
          soPhieu: this.txtSoPhieu + "|" + area,
          iMode: "0",
          
        })
      
        this.DSTT = await  this.getDSTTDongBo({
          soPhieu: this.txtSoPhieu,
          kqIms: dongbo,
          kqFmis: so_id
        })   
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    }
  },
 
  created: async function () {
   
  },
  
};
</script>
