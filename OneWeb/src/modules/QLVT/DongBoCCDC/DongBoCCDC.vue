<template src='./DongBoCCDC.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
import DongBoCCDCAPI from "../api/DongBoCCDCAPI";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/dongboccdc";


export default {
  components: { },
  name: "DongBoCCDC",
  mounted() {},
 
  computed: {
    ...mapState("dongboccdc", statePropertyName),
    ...mapGetters("dongboccdc", getterName),
   
  },
  data() {
    return {
      DSVatTu: [],
      sophieu: "",
      dtpNgayDK : null,
      chungtu_id: null,
      DSVatTuChecked: [],
      dongbo_qlts :false,
      txtXuatXu: "",
      Enabled: {
        tsbtnDongBoQLTS: true
      },

      checkAll: false,
      CheckALLCCDC: function () {
        return {
          template: {
            template: `     
                            <div class="check-action">                 
                                <input type="checkbox" v-model="parent.checkAll" class="uncheck"/>                               
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },            
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      listCCDC: [],
      CheckCCDC: function () {
        return {
          template: {
            template: `
                            <div class="check-action" >
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.listCCDC"
                                    :value="data.KEY" 
                                    disabled />
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {                
                return this.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
    };
  },
  methods: {
    ...mapActions("dongboccdc", actionName),
    ...mapMutations("dongboccdc", mutationName),
    async frmDongBo_CCDC_Load(){
      await this.LOAD_DS_TBI_CHUNGTU()
    },  
  
    grvDSVatTu_CheckedRow(dataItem) {
      this.DSVatTuChecked = dataItem   
    },
    btnXuatXu_Click(){
      if(this.txtXuatXu.trim() == ""){
        this.$toast.error("Bạn phải nhập xuất xứ!")
        return
      }
      if(this.DSVatTu.length == 0){
        this.$toast.error("Không có dữ liệu để gán!")
        return
      }
      if(this.DSVatTuChecked.length == 0){
        this.$toast.error("Bạn chưa tích chọn vật tư để gán xuất xứ!")
        return
      }
      for(let e of this.DSVatTu){
        if(this.DSVatTuChecked.includes(e.KEY)){
          e.XUATXU = this.txtXuatXu.trim()
        }
      }
      this.$refs.DSVatTu.grid.refreshColumns()
    },
    async LOAD_DS_TBI_CHUNGTU(){
      try{
        this.loading(true)
        let DSVatTu = await this.getDSThietBiCCDC(this.chungtu_id)
        for(let i in DSVatTu){
          DSVatTu[i].KEY = i
        }
        this.DSVatTu = DSVatTu
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    async tsbtnLayTT(){
      await this.LOAD_DS_TBI_CHUNGTU()
    },
    async openDialog(){
      await this.frmDongBo_CCDC_Load()
      this.$refs.popupDongBoCCDC.show()
    },
    btnChon_Click(){      
      for(let e of this.DSVatTuChecked){
        if(!this.listCCDC.includes(e)){
          this.listCCDC.push(e)
        }
      }
      for(let e of this.DSVatTu){
        e.PHANLOAITBI_ID = this.listCCDC.includes(e.KEY) ? "1" : "0"
      }
    },
    btnChonBo_Click(){  
      let newList = []   
      for(let e of this.listCCDC){
        if(!this.DSVatTuChecked.includes(e)){
          newList.push(e)
        }
      }
      this.listCCDC = newList
      for(let e of this.DSVatTu){
        e.PHANLOAITBI_ID = this.listCCDC.includes(e.KEY) ? "1" : "0"
      }
    },
    async tsbtnSave(){
      try{
        if(this.DSVatTu.length == 0){
          this.$toast.error("Không có dữ liệu để gán!")
          return
        }
        let checkVT = this.DSVatTu.filter(e=> (e.XUATXU == null || e.XUATXU == "") && e.PHANLOAITBI_ID == "1")
        if(checkVT.length > 0){
          this.$toast.error("Trong danh sách có xuất xứ bị trống bạn phải nhập thông tin xuất xứ!")
          return
        }

        let kiemTra = await this.kiemTraCapNhapQLTS(this.chungtu_id)
        
        if(kiemTra > 0){
          this.$toast.error("Chứng từ đã đồng bộ QLTS không thể cập nhật!")
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật thông tin CCDC không?", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } 
        });

        if (!isBoxConfirm) {
          return;
        }

        let ketQua = await this.capNhatThietBiQLTS(this.DSVatTu)
        this.$toast.success("Đã cập nhật thành công")
        await this.LOAD_DS_TBI_CHUNGTU()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnDongBoQLTS_Click(){
      try{
        let kiemTra = await this.kiemTraDongBoCCDC({
          chungTuId: this.chungtu_id,
          kieu: 1
        })
        if(kiemTra != "OK"){
          this.$toast.error(kiemTra)
          return
        }
        
        let DSVatTu = await this.getDSThietBiCCDC(this.chungtu_id)
        if(DSVatTu.length == 0){
          this.$toast.error("Không có dữ liệu không thể đồng bộ")
          return
        }

        let lay_ds_qlts = DSVatTu.filter(e=> e.PHANLOAITBI_ID == "1")
        if(lay_ds_qlts.length == 0){
          this.$toast.error("Không có dữ liệu CCDC của thiết bị không thể đồng bộ")
          return
        }
        let data = []
        for(let e of lay_ds_qlts){
          let row = {
            stt: 1, 
            ma_phieu: this.sophieu, 
            ngay_quyet_toan: this.f_DateToString(dtpNgayDK), 
            ma_dvi: this.$auth.getMaTinh().toString() + "000000", 
            ten_dvi: "", 
            ma_vt_hh_dv_6: e.MA_VT, 
            ten_vt_hh_dv_6: e.TEN_VT, 
            ma_vt_hh_dv_5: e.MA_NHOM, 
            ten_vt_hh_dv_5: e.TEN_NHOM, 
            ten_viet_tat: e.TEN_TAT_2CAP, 
            so_luong: e.SOLUONG, 
            don_vi_tinh: e.DVI_TINH, 
            gia_tri: e.GIATRI, 
            nghiep_vu: "Lắp mới", 
            serial: e.SERIAL, 
            ma_so: e.ID_CHITIET, 
            hang_sx: e.TEN_HANGSX, 
            xuatxu: e.XUATXU, 
            thoigian_khauhao: "", 
            ngay_bd_khauhao: "", 
            ma_dvi_ql: "", 
            ma_dvi_sd: ""
          }
          data.push(row)
        }
        let ketQua = await this.dongBoCCDC(data)
        if(kq == "OK"){
          this.$toast.success("Đã đồng bộ thành công")
          this.dongbo_qlts = true
          this.Enabled.tsbtnDongBoQLTS = false
        }

      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
  },
  created: async function () {},
  watch: {
    // listCCDC: function (value) {
    //   this.listCCDC = value; 
    // },
    // checkAll: function (value) {
    //   this.listCCDC = []
    //   if (value) {
    //     for (const e of this.DSVatTu) {
    //       this.listCCDC.push(e.THIETBI_ID);
    //     }
    //   }   
    // },
  },
  destroyed() {},
};
</script>
<style>
 .disabledInput {
    pointer-events: none;
    color: #666 !important; 
    border-color: #9999 !important;   
  }

  .disabledInput > a{   
    color: #666 !important; 
  }
</style>