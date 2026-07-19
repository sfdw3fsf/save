<template src='./GanThuocTinh.html'></template>
<style scoped src='./GanThuocTinh.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/ganthuoctinh";
import moment from "moment";
import Vue from "vue";
import {
  GridPlugin,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'

Vue.use(DatePickerPlugin)

Vue.use(GridPlugin);

export default {
  components: { breadcrumb },
  name: "NghiemThuDonHang",

  data() {
    return {
      header: {
        title: "GÁN THUỘC TÍNH",
      },

      params: {
        vatTuId: 0,
        thietBiId: 0
      },
 
      dataResponse:{
        DSThuocTinh : []
      },
      ChapNhan : false,
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      editSettings:{ allowEditing: true, mode:'Batch'},
    };
  },

  computed: {
    ...mapState("ganthuoctinh", statePropertyName),
    ...mapGetters("ganthuoctinh", getterName),
  },

  methods: {
    ...mapActions("ganthuoctinh", actionName),
    ...mapMutations("ganthuoctinh", mutationName),

    async onClickXacNhan(){
      console.log(this.dataResponse.DSThuocTinh , " = DSThuocTinh")
      var ds = []
      this.dataResponse.DSThuocTinh.forEach(e=>{
        ds.push({
          tbi: this.params.thietBiId,
          gt: e.GIATRI,
          tt: e.THUOCTINH_ID,
        })
      })
      var KetQua = await this.capNhatThuocTinh({ ds: JSON.stringify(ds)})
      if(KetQua != "1"){
        this.$toast.error(ketQua);
        return
      }
      this.ChapNhan = true
      this.$refs.popupGanThuocTinh.hide();
    },
    async dialogOpen() {
     this.dataResponse.DSThuocTinh = await this.getDSThuocTinh({
       vatTuId : this.params.vatTuId,
       thietBiId: this.params.thietBiId
     })
    },
    Open(){
      this.$refs.popupGanThuocTinh.show();
    },
    async Close() {
      this.ChapNhan = false
      this.$refs.popupGanThuocTinh.hide();
    },
    editGridThuocTinh(arg){
      let gridThuocTinh = this.$refs.gridThuocTinh
      let changes = gridThuocTinh.getBatchChanges() 
      gridThuocTinh.batchUpdate(changes)
    }
  },
 

  created: async function () {
    
  },
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
};
</script>
