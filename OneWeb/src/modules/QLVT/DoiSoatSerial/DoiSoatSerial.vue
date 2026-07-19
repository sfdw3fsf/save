<template src='./DoiSoatSerial.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/doisoatserial";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "DoiSoatSerial",

  data() {
    return {
      vchungtu_id : 0,
      DSSerial : [],
      serialSelectRow : {},
      DSScan : [],
      txtSerial: "",

      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',

    };
  },
  
  computed: {
    ...mapState("doisoatserial", statePropertyName),
    ...mapGetters("doisoatserial", getterName),
   
  },
  watch: {
    
  },
  mounted: async function () {
     
  },
  methods: {
    ...mapActions("doisoatserial", actionName),
    ...mapMutations("doisoatserial", mutationName),

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    async openDoiSoatSerial(){
      try{
        this.loading(true)
        await this.lay_ds_serial()
        this.$refs.popupDoiSoatSerial.show();
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async closeDoiSoatSerial(){
      this.$refs.popupDoiSoatSerial.hide();
    },
    async lay_ds_serial(){
      this.DSSerial = await this.getDSSerialChuaDoiSoat({
        kieu: 0,
        chungTuId: this.vchungtu_id ,
        dieuKien: "0"
      })
      console.log(this.DSSerial , " = DSSerial")
      console.log(this.vchungtu_id , " = vchungtu_id")
    },
    async tsbtnLammoi_Click(){
      try{
        this.loading(true)
        await this.lay_ds_serial()
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    gridSerialSelectRow(row){
      if(row){
        this.serialSelectRow =  row
      }
    },
    async tsbtnHuySerial(){
      try{
        if(this.DSSerial.length == 0){
          this.$toast.error("Không có dữ liệu vật tư !");
          return
        }
        if(this.serialSelectRow == {}){
          this.$toast.error("Bạn chưa focus dòng serial nào !");
          return
        }
        this.loading(true)
        var vserial = this.serialSelectRow.SERIAL
        var ds_serial =  await this.getDSSerialChuaDoiSoat({
          kieu: 1,
          chungTuId: this.vchungtu_id ,
          dieuKien: vserial
        })
  
        if(ds_serial.length == 0){
          this.$toast.error("Không tìm thấy chứng từ (" + this.vchungtu_id + ")");
          this.loading(false)
          return
        }
        if(ds_serial[0].TTPH_ID == 4){
          this.$toast.error("Chứng từ đã thay đổi trạng thái, hãy lấy lại TT chứng từ ! (4)");
          this.loading(false)
          return
        }
        if(ds_serial[0].TRANGTHAITBI_ID == 1){
          this.$toast.error("Serial đã đối soát, không thể xóa !");
          this.loading(false)
          return
        }

        var capnhat_ = await this.capNhatSerialDoiSoat({
          kieu: 1,
          chungTuId: this.vchungtu_id,
          serial: vserial,
          ctctId: this.serialSelectRow.CTCT_ID,
          thietBiId: this.serialSelectRow.THIETBI_ID
        })
        if(capnhat_ == "1"){
          this.$toast.success("Hủy serial: " + vserial + " thành công !");
          await this.lay_ds_serial()
        }else{
          this.$toast.error(capnhat_);
        }
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async txtSerial_KeyPress(){
      try{
        var vserial = this.txtSerial.trim()
        if(this.DSSerial.length == 0){
          this.$toast.error("Không có dữ liệu vật tư để đối soát !");
          return
        }
        if(vserial == ""){
          this.$toast.error("Bạn phải nhập serial !");
          return
        }
        this.loading(true)
        var ds_serial =  await this.getDSSerialChuaDoiSoat({
          kieu: 1,
          chungTuId: this.vchungtu_id ,
          dieuKien: vserial
        })
        if(ds_serial.length == 0){
          var err = "Serial không có trong chứng từ !"
          this.$toast.error(err);
          this.DSScan.push({
            SERIAL : vserial,
            ERROR : err,
            NGAY_CN : this.f_DateToString(new Date())
          })
          this.txtSerial = ""
          this.loading(false)
          return
        }
        if(ds_serial[0].TTPH_ID == 4){
          this.$toast.error("Chứng từ đã thay đổi trạng thái, hãy lấy lại TT chứng từ ! (4)");
          this.loading(false)
          return
        }
        if(ds_serial[0].TRANGTHAITBI_ID == 1){
          var err = "Serial đã đối soát, không thể đối soát tiếp !"
          this.$toast.error(err);
          this.DSScan.push({
            SERIAL : vserial,
            ERROR : err,
            NGAY_CN : this.f_DateToString(new Date())
          })
          this.loading(false)
          return
        }
        if(ds_serial[0].TRANGTHAITBI_ID == 0){
          var capnhat_ = await this.capNhatSerialDoiSoat({
            kieu: 0,
            chungTuId: this.vchungtu_id,
            serial: vserial,
            ctctId: 0,
            thietBiId: 0
          })
          if(capnhat_ == "1"){
            this.$toast.success("Đối soát thành công serial: " + vserial );
            this.DSSerial.forEach(e=>{
              if(e.SERIAL == vserial){
                e.TRANGTHAITBI_ID = 1
              }
            })
            this.$refs.gridSerial.grid.refreshColumns();
          }
        }
        this.txtSerial = ""
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    lay_ds_loi(){
      
    }
  },
 

  created: async function () {
   
  },
  
};
</script>
